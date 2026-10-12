# -*- coding: utf-8 -*-
"""Rebuild AutoJs4's OpenCV 3.4.3 Java JNI library (`libopencv_java3.so`) for 64-bit ABIs with 16 KB page alignment.

AutoJs4 ships `libopencv_java3.so` only for armeabi-v7a. This script builds the same OpenCV release
(3.4.3, pinned by archive SHA-256) with NDK 28.2 for the 64-bit ABIs, verifies that every JNI symbol
of the original 32-bit library is exported by the new one (so the unchanged `opencv-3.4.3.aar` Java
classes keep working), checks the ELF PT_LOAD alignment and records provenance in
`native/opencv/opencv.lock.json`.

zh-CN: AutoJs4 只为 armeabi-v7a 提供 `libopencv_java3.so`. 本脚本用 NDK 28.2 以同一 OpenCV 3.4.3 源码
(归档 SHA-256 固定) 构建 64 位 ABI 的 JNI 库, 校验原 32 位库导出的全部 JNI 符号在新库中都存在 (保证未改动的
`opencv-3.4.3.aar` Java 类可用), 检查 ELF PT_LOAD 对齐, 并把来源记录到 `native/opencv/opencv.lock.json`.

Usage (repository root, Python 3, CMake 3.22.1 + Ninja from the Android SDK, Apache Ant, JDK):
  py tools/opencv/build_opencv_android.py --ndk E:/.android/sdk/ndk/28.2.13676358 ^
     --cmake-dir E:/.android/sdk/cmake/3.22.1/bin --ant C:/tools/apache-ant/bin/ant.bat
  py tools/opencv/build_opencv_android.py --check      # verify native/opencv against the lock file
"""
from __future__ import annotations

import argparse
import hashlib
import json
import os
import re
import shutil
import subprocess
import sys
import urllib.request
import zipfile
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
OPENCV_VERSION = "3.4.3"
ARCHIVE_URL = f"https://github.com/opencv/opencv/archive/refs/tags/{OPENCV_VERSION}.zip"
ARCHIVE_SHA256 = "37c7d8c3b9807902ad11b9181bbde61dcb3898a78a563130494752f46fe8cc5f"
NDK_VERSION = "28.2.13676358"
ANDROID_PLATFORM = "android-24"
LIBRARY_NAME = "libopencv_java3.so"
DEFAULT_ABIS = ["arm64-v8a", "x86_64"]
OUTPUT_DIR = ROOT / "native" / "opencv"
LOCK_FILE = OUTPUT_DIR / "opencv.lock.json"
REFERENCE_LIBRARY = ROOT / "autojs4" / "app" / "src" / "main" / "jniLibs" / "armeabi-v7a" / LIBRARY_NAME
PAGE_SIZE = 16384

# Mirrors the official OpenCV Android SDK configuration of the 3.4 line (platforms/android/build_sdk.py):
# static modules folded into one JNI library, no OpenCL/IPP/TBB, no Android example projects.
CMAKE_OPTIONS = [
    "-DCMAKE_BUILD_TYPE=Release",
    # The archive carries no Git metadata; without this CMake would run `git describe` in the enclosing
    # checkout and stamp an unrelated commit into Core.getBuildInformation().
    f"-DOPENCV_VCSVERSION={OPENCV_VERSION}",
    f"-DANDROID_PLATFORM={ANDROID_PLATFORM}",
    "-DANDROID_STL=c++_static",
    "-DBUILD_SHARED_LIBS=OFF",
    "-DBUILD_FAT_JAVA_LIB=ON",
    "-DBUILD_opencv_java=ON",
    "-DBUILD_ANDROID_PROJECTS=OFF",
    "-DBUILD_ANDROID_EXAMPLES=OFF",
    "-DBUILD_TESTS=OFF",
    "-DBUILD_PERF_TESTS=OFF",
    "-DBUILD_DOCS=OFF",
    "-DBUILD_EXAMPLES=OFF",
    "-DBUILD_opencv_apps=OFF",
    "-DBUILD_opencv_python2=OFF",
    "-DBUILD_opencv_python3=OFF",
    "-DBUILD_opencv_python_bindings_generator=OFF",
    "-DBUILD_opencv_js=OFF",
    "-DBUILD_opencv_ts=OFF",
    "-DWITH_CUDA=OFF",
    "-DWITH_OPENCL=OFF",
    "-DWITH_IPP=OFF",
    "-DWITH_TBB=OFF",
    "-DWITH_1394=OFF",
    "-DWITH_GSTREAMER=OFF",
    "-DWITH_V4L=OFF",
    "-DWITH_FFMPEG=OFF",
    "-DWITH_GTK=OFF",
    "-DWITH_QT=OFF",
    "-DWITH_OPENEXR=OFF",
    "-DWITH_OPENCL_SVM=OFF",
    # clang 16+ turns several legacy C diagnostics into errors; the 2018 third-party sources of OpenCV 3.4.3
    # (cpufeatures, libjpeg-turbo, libpng, zlib, protobuf) still rely on them. Downgrade them to warnings.
    "-DCMAKE_C_FLAGS=-Wno-error=implicit-function-declaration -Wno-error=implicit-int -Wno-error=int-conversion -Wno-error=incompatible-function-pointer-types -Wno-error=incompatible-pointer-types",
    "-DCMAKE_CXX_FLAGS=-Wno-error=enum-constexpr-conversion -Wno-error=register -Wno-error=deprecated-declarations -Wno-error=unused-but-set-variable",
    # 16 KB page alignment, stated explicitly instead of relying on NDK defaults (AutoJs6 16 KB roadmap D3).
    "-DCMAKE_SHARED_LINKER_FLAGS=-Wl,-z,max-page-size=16384 -Wl,-z,common-page-size=16384",
]


def sha256_of(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as handle:
        for chunk in iter(lambda: handle.read(1 << 20), b""):
            digest.update(chunk)
    return digest.hexdigest()


def run(command: list[str], cwd: Path | None = None, env: dict | None = None) -> None:
    print("+", " ".join(str(part) for part in command), flush=True)
    subprocess.run(command, cwd=cwd, env=env, check=True)


def tool(ndk: Path, name: str) -> Path:
    host = "windows-x86_64" if os.name == "nt" else ("darwin-x86_64" if sys.platform == "darwin" else "linux-x86_64")
    suffix = ".exe" if os.name == "nt" else ""
    path = ndk / "toolchains" / "llvm" / "prebuilt" / host / "bin" / f"{name}{suffix}"
    if not path.is_file():
        raise SystemExit(f"NDK tool not found: {path}")
    return path


def jni_symbols(nm: Path, library: Path) -> set[str]:
    output = subprocess.run([str(nm), "-D", "--defined-only", str(library)], capture_output=True, text=True, check=True).stdout
    return {line.split()[-1] for line in output.splitlines() if line.strip() and line.split()[-1].startswith("Java_")}


def load_alignments(readelf: Path, library: Path) -> list[int]:
    output = subprocess.run([str(readelf), "-lW", str(library)], capture_output=True, text=True, check=True).stdout
    alignments = []
    for line in output.splitlines():
        parts = line.split()
        if parts and parts[0] == "LOAD":
            alignments.append(int(parts[-1], 16))
    if not alignments:
        raise SystemExit(f"No PT_LOAD segments found in {library}")
    return alignments


def ensure_source(work: Path, source_zip: Path | None) -> Path:
    work.mkdir(parents=True, exist_ok=True)
    archive = source_zip or (work / f"opencv-{OPENCV_VERSION}.zip")
    if not archive.is_file():
        print(f"Downloading {ARCHIVE_URL}", flush=True)
        urllib.request.urlretrieve(ARCHIVE_URL, archive)
    actual = sha256_of(archive)
    if actual != ARCHIVE_SHA256:
        raise SystemExit(f"OpenCV archive SHA-256 mismatch: expected {ARCHIVE_SHA256}, got {actual}")
    source = work / "src" / f"opencv-{OPENCV_VERSION}"
    if not (source / "CMakeLists.txt").is_file():
        print("Extracting source", flush=True)
        with zipfile.ZipFile(archive) as bundle:
            bundle.extractall(work / "src")
    return source


def java_home_from_path() -> str | None:
    java = shutil.which("java")
    if java is None:
        return None
    output = subprocess.run([java, "-XshowSettings:properties", "-version"], capture_output=True, text=True).stderr
    match = re.search(r"java\.home = (.+)", output)
    return match.group(1).strip() if match else None


def build_abi(abi: str, source: Path, work: Path, ndk: Path, cmake_dir: Path, ant: Path, jobs: int, keep_going: bool = False) -> Path:
    build = work / f"build-{abi}"
    build.mkdir(parents=True, exist_ok=True)
    cmake = cmake_dir / ("cmake.exe" if os.name == "nt" else "cmake")
    ninja = cmake_dir / ("ninja.exe" if os.name == "nt" else "ninja")
    env = dict(os.environ)
    if "JAVA_HOME" not in env:
        java_home = java_home_from_path()
        if java_home:
            env["JAVA_HOME"] = java_home
    python = sys.executable
    configure = [
        str(cmake), "-G", "Ninja", f"-DCMAKE_MAKE_PROGRAM={ninja}",
        f"-DCMAKE_TOOLCHAIN_FILE={ndk / 'build' / 'cmake' / 'android.toolchain.cmake'}",
        f"-DANDROID_NDK={ndk}", f"-DANDROID_ABI={abi}",
        f"-DANT_EXECUTABLE={ant}", f"-DPYTHON_DEFAULT_EXECUTABLE={python}",
        *CMAKE_OPTIONS, str(source),
    ]
    run(configure, cwd=build, env=env)
    ninja_args = [str(ninja), f"-j{jobs}"] + (["-k", "0"] if keep_going else []) + ["opencv_java"]
    run(ninja_args, cwd=build, env=env)
    produced = build / "lib" / abi / LIBRARY_NAME
    if not produced.is_file():
        candidates = list(build.rglob(LIBRARY_NAME))
        if not candidates:
            raise SystemExit(f"{LIBRARY_NAME} was not produced for {abi}")
        produced = candidates[0]
    return produced


def verify_library(ndk: Path, library: Path, reference_symbols: set[str], abi: str) -> dict:
    alignments = load_alignments(tool(ndk, "llvm-readelf"), library)
    symbols = jni_symbols(tool(ndk, "llvm-nm"), library)
    missing = sorted(reference_symbols - symbols)
    is_64bit = abi in ("arm64-v8a", "x86_64")
    if is_64bit and min(alignments) < PAGE_SIZE:
        raise SystemExit(f"{library} is not 16 KB aligned: PT_LOAD alignments {alignments}")
    if missing:
        raise SystemExit(f"{library} lacks {len(missing)} JNI symbols of the AutoJs4 reference library, first: {missing[:5]}")
    return {
        "path": f"{abi}/{LIBRARY_NAME}",
        "sha256": sha256_of(library),
        "size": library.stat().st_size,
        "minLoadAlign": min(alignments),
        "jniSymbols": len(symbols),
        "referenceSymbolsCovered": len(reference_symbols),
    }


def write_lock(entries: list[dict], ndk: Path, reference_symbols: set[str]) -> None:
    # Merge with an existing lock so that ABIs can be rebuilt one at a time.
    if LOCK_FILE.is_file():
        previous = json.loads(LOCK_FILE.read_text(encoding="utf-8"))
        rebuilt = {entry["path"] for entry in entries}
        entries = sorted(
            [entry for entry in previous.get("libraries", []) if entry["path"] not in rebuilt] + entries,
            key=lambda entry: entry["path"],
        )
    lock = {
        "schemaVersion": 1,
        "opencvVersion": OPENCV_VERSION,
        "archiveUrl": ARCHIVE_URL,
        "archiveSha256": ARCHIVE_SHA256,
        "ndkVersion": NDK_VERSION,
        "androidPlatform": ANDROID_PLATFORM,
        "cmakeOptions": CMAKE_OPTIONS,
        "referenceLibrary": {
            "path": "autojs4/app/src/main/jniLibs/armeabi-v7a/" + LIBRARY_NAME,
            "sha256": sha256_of(REFERENCE_LIBRARY),
            "jniSymbols": len(reference_symbols),
        },
        "libraries": entries,
    }
    LOCK_FILE.write_text(json.dumps(lock, indent=2) + "\n", encoding="utf-8", newline="\n")
    print(f"Wrote {LOCK_FILE.relative_to(ROOT)}")


def check(ndk: Path | None) -> int:
    if not LOCK_FILE.is_file():
        print("OPENCV_ERROR lock file is missing")
        return 1
    lock = json.loads(LOCK_FILE.read_text(encoding="utf-8"))
    problems = []
    for entry in lock["libraries"]:
        library = OUTPUT_DIR / entry["path"]
        if not library.is_file():
            problems.append(f"missing {entry['path']}")
            continue
        if sha256_of(library) != entry["sha256"]:
            problems.append(f"sha256 drift {entry['path']}")
        if ndk is not None:
            alignments = load_alignments(tool(ndk, "llvm-readelf"), library)
            if min(alignments) < PAGE_SIZE:
                problems.append(f"alignment {entry['path']} = {min(alignments)}")
    if REFERENCE_LIBRARY.is_file() and sha256_of(REFERENCE_LIBRARY) != lock["referenceLibrary"]["sha256"]:
        problems.append("AutoJs4 reference library changed; rebuild and re-verify")
    if problems:
        print("OPENCV_ERROR " + "; ".join(problems))
        return 1
    print(f"OPENCV_OK libraries={len(lock['libraries'])}")
    return 0


def main(argv: list[str] | None = None) -> int:
    parser = argparse.ArgumentParser(description=__doc__.splitlines()[0])
    parser.add_argument("--ndk", type=Path, default=None)
    parser.add_argument("--cmake-dir", type=Path, default=None)
    parser.add_argument("--ant", type=Path, default=None)
    parser.add_argument("--abis", default=",".join(DEFAULT_ABIS))
    parser.add_argument("--work", type=Path, default=ROOT / "build" / "opencv")
    parser.add_argument("--source-zip", type=Path, default=None)
    parser.add_argument("--jobs", type=int, default=max(1, (os.cpu_count() or 4)))
    parser.add_argument("--check", action="store_true", help="verify native/opencv against the lock file")
    parser.add_argument("--keep-going", action="store_true", help="pass -k 0 to ninja to collect every compile error")
    arguments = parser.parse_args(argv)
    if arguments.check:
        return check(arguments.ndk)
    if arguments.ndk is None or arguments.cmake_dir is None or arguments.ant is None:
        parser.error("--ndk, --cmake-dir and --ant are required to build")
    if arguments.ndk.name != NDK_VERSION:
        print(f"WARNING: expected NDK {NDK_VERSION}, using {arguments.ndk.name}")
    if not REFERENCE_LIBRARY.is_file():
        raise SystemExit(f"AutoJs4 reference library is missing: {REFERENCE_LIBRARY} (initialise the submodule)")
    reference_symbols = jni_symbols(tool(arguments.ndk, "llvm-nm"), REFERENCE_LIBRARY)
    print(f"Reference JNI symbols: {len(reference_symbols)}")
    source = ensure_source(arguments.work, arguments.source_zip)
    entries = []
    for abi in [item.strip() for item in arguments.abis.split(",") if item.strip()]:
        produced = build_abi(abi, source, arguments.work, arguments.ndk, arguments.cmake_dir, arguments.ant, arguments.jobs, arguments.keep_going)
        target = OUTPUT_DIR / abi / LIBRARY_NAME
        target.parent.mkdir(parents=True, exist_ok=True)
        shutil.copyfile(produced, target)
        entries.append(verify_library(arguments.ndk, target, reference_symbols, abi))
        print(f"{abi}: {entries[-1]}")
    write_lock(entries, arguments.ndk, reference_symbols)
    return 0


if __name__ == "__main__":
    sys.exit(main())
