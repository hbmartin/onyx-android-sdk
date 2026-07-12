import hashlib
import json
import unittest
from pathlib import Path


FIXTURE = (
    Path(__file__).resolve().parent.parent
    / "app"
    / "src"
    / "main"
    / "assets"
    / "mmkv-1.0.19"
)


class MmkvFixtureTest(unittest.TestCase):
    def test_manifest_and_files_are_pinned(self):
        manifest = json.loads((FIXTURE / "manifest.json").read_text(encoding="utf-8"))
        self.assertEqual("com.tencent:mmkv:1.0.19", manifest["artifact"])
        self.assertEqual("onyx-mmkv-compat-1-0-19", manifest["storeId"])
        self.assertEqual(
            {
                "legacy_string": "onyx-mmkv-legacy",
                "legacy_int": -2_147_483_000,
                "legacy_boolean": True,
                "legacy_float": 123.5,
                "legacy_long": 9_876_543_210,
                "legacy_present": "present",
                "legacy_removed": None,
            },
            manifest["expected"],
        )
        self.assertEqual("NoteAir4C", manifest["device"]["model"])
        self.assertEqual(33, manifest["device"]["androidApi"])
        self.assertEqual("arm64-v8a", manifest["device"]["primaryAbi"])

        self.assertEqual(
            {manifest["storeId"], manifest["storeId"] + ".crc"},
            set(manifest["files"]),
        )
        for name, expected in manifest["files"].items():
            payload = (FIXTURE / name).read_bytes()
            self.assertEqual(expected["bytes"], len(payload), name)
            self.assertEqual(expected["sha256"], hashlib.sha256(payload).hexdigest(), name)


if __name__ == "__main__":
    unittest.main()
