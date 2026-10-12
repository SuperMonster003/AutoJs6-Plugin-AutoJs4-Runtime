<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/master/app/src/main/res/mipmap-night/ic_launcher.png" />
      <img src="https://raw.githubusercontent.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/master/app/src/main/res/mipmap/ic_launcher.png" alt="AutoJs4 Runtime" border="0" width="128" />
    </picture>
  </p>

  <p>내장된 Auto.js 4.1.1 Alpha2 엔진으로 스크립트를 실행하여 AutoJs6 결과와 비교</p>

  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?color=A24232&label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/LICENSE"><img alt="GitHub license" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?color=534BAE&label=License"/></a>
  </p>
</div>

******

### 언어

******

현재 README.md는 다음 언어를 지원합니다:

- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-en.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ar.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-es.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-fr.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ja.md)
- 한국어 [ko] # 현재
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ru.md)
- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 香港 [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 台灣 [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hant-TW.md)

******

### 소개

******

AutoJs4 Runtime은 AutoJs6의 동작 기준인 Auto.js 4.1.1 Alpha2 복제본 [AutoJs4](https://github.com/SuperMonster003/AutoJs4)의 엔진을 내장합니다. 설치하면 AutoJs6 파일 관리자의 "AutoJs4로 실행" 항목이 `.js` 파일, `.auto` 기록 파일, 프로젝트 폴더를 이 플러그인 자체 프로세스에서 원본 Rhino 1.7.7.2 엔진으로 실행하고 콘솔 출력을 AutoJs6로 돌려보냅니다. AutoJs4를 따로 설치하지 않고도 한 기기에서 스크립트가 AutoJs4와 AutoJs6에서 다르게 동작하는지 확인할 수 있습니다.

******

### 설치 및 사용

******

1. Android 7.0 (API 24) 이상에서 AutoJs6 build 5334 (6.8.0) 이상을 사용합니다.
2. 기기 ABI에 맞는 release APK (대부분의 최신 휴대폰은 `arm64-v8a`, 32비트 기기는 `armeabi-v7a`, 에뮬레이터는 `x86_64` 또는 `x86`) 또는 용량이 더 큰 `universal` APK를 설치합니다.
3. AutoJs6 플러그인 센터를 열어 AutoJs4 Runtime을 사용 설정합니다. Android가 새로 설치된 패키지를 중지 상태로 두었다면 host의 `활성화` 동작을 사용합니다.
4. 플러그인 센터에서 플러그인 설정을 열고 스크립트에 필요한 저장공간 접근, 다른 앱 위에 표시, 접근성 서비스를 허용합니다. 이 권한은 AutoJs6가 아닌 플러그인 자체의 권한입니다.
5. AutoJs6 파일 관리자에서 `.js` 파일, `.auto` 파일 또는 프로젝트 폴더의 메뉴를 열고 `AutoJs4로 실행`을 선택합니다. 작업은 AutoJs6 작업 목록에 나타나며 거기서 중지할 수 있습니다.

> 플러그인은 AutoJs4를 변경하지 않습니다. 같은 Rhino 1.7.7.2, armeabi-v7a에서는 AutoJs4 원본 OpenCV 3.4.3 라이브러리, 64비트 기기에서는 같은 OpenCV 릴리스를 다시 빌드한 라이브러리, `targetSdk 28`, 같은 엔진 모듈입니다. 따라서 두 엔진 사이에서 관찰되는 차이는 AutoJs4와 AutoJs6의 차이이며 AutoJs6 issue 트래커에 보고할 수 있습니다.

******

### 빠른 시작

******

이 파일을 AutoJs6 작업 공간에 저장한 뒤 `AutoJs4로 실행`으로 실행합니다:

```javascript
console.log("engine package: " + context.getPackageName());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

AutoJs6 콘솔에 `[AutoJs4]` 접두어가 붙은 줄이 출력되며, 첫 줄에 스크립트를 실행한 플러그인 패키지 이름이 표시되어 AutoJs4 엔진이 실행했음을 확인할 수 있습니다.

******

### 호환성과 범위

******

- 엔진: AutoJs4 4.4.0 (Auto.js 4.1.1 Alpha2 복제본), Git commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443), Rhino 1.7.7.2, OpenCV 3.4.3. AutoJs4 소스는 Git submodule로 포함되며 수정 없이 컴파일됩니다. arm64-v8a와 x86_64의 OpenCV 라이브러리는 같은 릴리스에서 NDK 28.2.13676358로 다시 빌드했으며 원본 라이브러리의 모든 JNI 심볼을 내보내는지 검증했습니다.
- 플랫폼: Android 7.0 (API 24) 이상. 단일 ABI APK는 `arm64-v8a, armeabi-v7a, x86_64, x86`를 지원하고 `universal` APK는 네 가지를 모두 포함합니다. AutoJs4와 마찬가지로 `x86` 빌드에는 OpenCV가 없습니다.
- 스크립트: `.js` 파일, `.auto` root 기록 파일, `project.json`이 있는 프로젝트 폴더 (`main`과 `features`를 따릅니다). `"ui";` 스크립트는 플러그인 프로세스 안에서 AutoJs4 자체 스크립트 Activity를 엽니다.
- 권한: 저장공간 접근 (레거시 외부 저장공간, `targetSdk 28`), 다른 앱 위에 표시, AutoJs4 접근성 서비스는 AutoJs6와 별도로 플러그인에 부여합니다. Root 기능은 AutoJs4처럼 기기의 `su`를 사용합니다.
- 동시 실행: 최대 16개의 AutoJs4 스크립트를 동시에 실행합니다. AutoJs6에서 작업을 중지하면 AutoJs4 엔진이 중지되고, 플러그인 프로세스가 비정상 종료되면 실패한 작업으로 보고됩니다.

******

### 권한과 무결성

******

- Wake Activity와 두 개의 내보낸 서비스는 `org.autojs.permission.PLUGIN`을 요구합니다. 스크립트는 플러그인 UID로 `:autojs4_runtime` 프로세스에서 실행되며 AutoJs6 프로세스에는 들어가지 않습니다.
- 스크립트는 절대 경로로 지정됩니다. 플러그인은 사용자가 부여한 저장공간 권한으로만 파일을 읽고 쓰며, 절대 경로가 아니거나 읽을 수 없는 파일 요청은 거부합니다.
- 콘솔 출력은 최대 64 KiB 크기의 청크로 유계 oneway 콜백을 통해 AutoJs6로 스트리밍되며, 종료 이벤트는 상태, 오류 메시지, 스택 트레이스 요약만 전달합니다.
- AutoJs4 자체의 외부 진입점 (실행 인텐트, 바로가기, Tasker)은 내보내지 않습니다. 보호된 Binder 서비스를 통해 스크립트를 시작할 수 있는 것은 AutoJs6 host만입니다.

******

### 플러그인 인터페이스

******

AutoJs6 host와 플러그인 개발자를 위한 안정적인 식별자와 제한:

```text
application id: io.github.supermonster003.autojs6.plugin.autojs4.runtime
plugin id: autojs4-runtime
engine: autojs4
variant: default
service action: org.autojs.plugin.autojs4.RUNTIME
service category: autojs4-runtime
aidl interface: org.autojs.plugin.autojs4.runtime.api.IAutoJs4RuntimePlugin
contract version: 1
aidl methods: getInfo(), getRuntimeInfo(), runScript(request, callback), cancelScript(executionId)
minimum host build: 5334 (6.8.0)
embedded AutoJs4: 4.4.0 (3fb08269198ebd49a880ab3ca2621383eaa10443)
rhino: 1.7.7.2
supported abis: arm64-v8a, armeabi-v7a, x86_64, x86
max concurrent executions: 16
max log chunk: 64 KiB
```

`AutoJs4RuntimeService`는 action `org.autojs.plugin.autojs4.RUNTIME`과 category `autojs4-runtime`로 발견됩니다. `runScript`는 요청 Bundle (스크립트 경로, 작업 디렉터리, 선택적 프로젝트 폴더, 인수, 지연, 간격, 반복 횟수)을 검증하고 AutoJs4 실행을 시작한 뒤 수락 Bundle을 즉시 반환하며, 이후 `started`, `log`, `finished` 이벤트가 콜백으로 전달됩니다. `cancelScript`는 실행 중인 작업을 중지하거나 아직 디스패치되지 않은 실행에 대한 취소를 기억합니다. 전체 계약은 AutoJs6 저장소 ([protocol V1](https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-protocol-v1.md))에 문서화되어 있습니다.

16 KB 페이지 크기: arm64-v8a와 x86_64 네이티브 라이브러리 (NDK 28.2.13676358로 다시 빌드한 OpenCV 3.4.3와 AutoJs6 host가 다시 빌드한 jackpal 터미널 라이브러리)는 16 KB 최대 페이지 크기로 링크되며, 공유 정렬 게이트가 빌드 시 모든 ELF PT_LOAD 세그먼트를 검증합니다. 16 KB 페이지 기기에서의 실행은 아직 수행하지 않았습니다.

******

### 릴리스 기록

******

#### v1.0.1

_2026/10/12_

- `개선` 스크립트에 접근성 서비스가 필요한데 플러그인의 서비스가 켜져 있지 않으면, 시스템 접근성 설정을 여는 이유를 토스트로 안내

#### v1.0.0

_2026/10/12_

- `안내` armeabi-v7a는 AutoJs4 원본 OpenCV 3.4.3 네이티브 라이브러리를, 64비트 기기는 NDK 28.2로 다시 빌드한 같은 릴리스를 사용합니다. x86 빌드에는 AutoJs4와 마찬가지로 OpenCV가 없습니다
- `기능` AutoJs6 파일 관리자의 `AutoJs4로 실행` 메뉴 항목을 통해 내장된 AutoJs4 4.4.0 엔진 (Rhino 1.7.7.2, OpenCV 3.4.3)으로 `.js` 파일, `.auto` 기록 파일, 프로젝트 폴더 실행
- `기능` 버전 관리되는 `autojs4-runtime-api` AIDL 계약을 사용하는 보호된 런타임 서비스: 파일 경로 요청, 콘솔 줄 스트리밍, 시작과 완료 이벤트, 취소, 동시 실행
- `기능` 접근성 서비스, 다른 앱 위에 표시, 저장공간 접근, 배터리 최적화, Root 상태를 보여 주는 플러그인 설정 페이지와 `AutoJs4 콘솔 창 표시` 스위치
- `기능` `arm64-v8a`, `armeabi-v7a`, `x86_64`, `x86`, `universal` APK 제공, Wake 활성화, 완전한 PluginInfo 메타데이터, 10개 언어의 플러그인 센터 설명, README, changelog
- `개선` arm64-v8a와 x86_64의 OpenCV 3.4.3 및 jackpal 터미널 라이브러리를 16 KB 페이지 정렬로 빌드하고, 빌드 시 ELF 정렬과 원본 라이브러리 JNI 심볼의 완전한 포함을 검증
- `의존성` AutoJs4 4.4.0을 commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443)의 Git submodule로 내장

##### 더 많은 릴리스 기록

- [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/app/src/main/assets/doc/CHANGELOG-ko.md)

******

### 빌드와 검증

******

빌드에는 JDK 17 이상, Python 3, Android SDK 37이 필요합니다 (AutoJs4 엔진 모듈은 API 30으로 컴파일되며 Android SDK가 필요 시 다운로드합니다). `autojs4/`를 채우기 위해 `--recurse-submodules`로 클론하세요. 표준 검사는 생성 문서, JVM 테스트, debug APK, instrumentation 패키지, Android Lint를 검증합니다.

```powershell
git clone --recurse-submodules https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime.git
py .python\generate_markdown.py --check
py .python\generate_provisional_icon.py --check
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:lintDebug
```

******

### 현지화와 문서 생성

******

JSON과 Markdown 템플릿을 편집한 뒤 `py .python/generate_markdown.py`를 실행합니다. 생성된 README, changelog, 플러그인 설명 파일은 수동으로 편집하지 마세요. `--check` 모드는 파일을 쓰지 않고 언어 구조, 버전 일치, 현지화 리소스, 고아 파일, 생성 파일 드리프트를 검증합니다.

```text
.readme/common.json
.readme/lang_*.json
.readme/template_readme.md
.readme/template_plugin_instruction.md
.changelog/lang_*.json
.changelog/template_changelog.md
.python/generate_markdown.py
app/src/main/assets/doc/CHANGELOG-*.md
app/src/main/res/values*/strings.xml
app/src/main/res/raw*/plugin_instruction.md
```

아이콘은 관리자가 제공한 [PNG 원본](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/tree/master/.python/icons) (라이트 테마용과 다크 테마용 각 1장)과 원래 구도를 유지합니다. Icon Studio로 조정하고 `python .python/generate_icon_studio.py --check`로 레시피와 생성 리소스의 일치 여부를 확인하세요.

******

### 라이선스

******

플러그인 코드는 [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/LICENSE)으로 제공됩니다. 내장된 AutoJs4 소스는 AutoJs4 저장소의 비상업적 사용 조항이 추가된 MPL 2.0입니다. Rhino, OpenCV, jackpal 터미널 라이브러리와 기타 제3자 구성 요소는 각자의 라이선스를 유지합니다. [제3자 고지](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/THIRD_PARTY_NOTICES.md)를 참조하세요.

******

### 링크

******

- AutoJs6 프로젝트: https://github.com/SuperMonster003/AutoJs6
- AutoJs4 프로젝트: https://github.com/SuperMonster003/AutoJs4
- AutoJs4 문서: https://github.com/SuperMonster003/AutoJs4-Documentation
- AutoJs4 Runtime protocol V1: https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-protocol-v1.md
- AutoJs4 Runtime roadmap: https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-plugin-roadmap.md
- 제3자 고지: https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/THIRD_PARTY_NOTICES.md
