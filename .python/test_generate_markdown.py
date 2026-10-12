from __future__ import annotations

import importlib.util
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
MODULE_PATH = ROOT / ".python" / "generate_markdown.py"
SPEC = importlib.util.spec_from_file_location("generate_markdown", MODULE_PATH)
assert SPEC is not None and SPEC.loader is not None
generate_markdown = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(generate_markdown)


class MarkdownGenerationTest(unittest.TestCase):

    def write_android_compatibility_fixture(
        self,
        root: Path,
        *,
        manifest_api: int = 24,
        target_api: int = 28,
    ) -> dict[str, str]:
        (root / "version.properties").write_text(
            "MIN_SDK_VERSION=%d\nTARGET_SDK_VERSION=%d\n" % (manifest_api, target_api),
            encoding="utf-8",
        )
        return {
            "android_min_sdk": "24",
            "android_target_sdk": "28",
            "min_android_version": "7.0",
        }

    def test_expected_artifact_inventory_is_complete(self) -> None:
        artifacts = generate_markdown.build_artifacts(ROOT)
        self.assertEqual(generate_markdown.EXPECTED_ARTIFACT_COUNT, len(artifacts))
        self.assertIn(ROOT / "README.md", artifacts)
        self.assertIn(ROOT / "app/src/main/assets/doc/CHANGELOG.md", artifacts)
        self.assertIn(ROOT / "app/src/main/res/raw/plugin_instruction.md", artifacts)

    def test_checked_in_artifacts_match_sources(self) -> None:
        artifacts = generate_markdown.build_artifacts(ROOT)
        generate_markdown.check_artifacts(ROOT, artifacts)

    def test_duplicate_json_keys_are_rejected(self) -> None:
        with self.assertRaises(generate_markdown.MarkdownGenerationError):
            generate_markdown.reject_duplicate_pairs([("same", 1), ("same", 2)])

    def test_android_api_sources_are_aligned(self) -> None:
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            common = self.write_android_compatibility_fixture(root)
            generate_markdown.validate_android_compatibility_alignment(root, common)

    def test_android_target_api_rejects_documentation_drift(self) -> None:
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            common = self.write_android_compatibility_fixture(root, target_api=29)
            with self.assertRaisesRegex(generate_markdown.MarkdownGenerationError, "Android target API drift"):
                generate_markdown.validate_android_compatibility_alignment(root, common)

    def test_android_minimum_api_rejects_documentation_drift(self) -> None:
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            common = self.write_android_compatibility_fixture(root)
            common["android_min_sdk"] = "25"
            with self.assertRaisesRegex(generate_markdown.MarkdownGenerationError, "Android minimum API drift"):
                generate_markdown.validate_android_compatibility_alignment(root, common)

    def test_android_version_name_must_match_api_level(self) -> None:
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            common = self.write_android_compatibility_fixture(root)
            common["min_android_version"] = "8.0"
            with self.assertRaisesRegex(generate_markdown.MarkdownGenerationError, "maps to Android 7.0"):
                generate_markdown.validate_android_compatibility_alignment(root, common)


if __name__ == "__main__":
    unittest.main()
