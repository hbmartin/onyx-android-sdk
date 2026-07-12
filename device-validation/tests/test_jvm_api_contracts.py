import sys
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT / "scripts"))
sys.path.insert(0, str(ROOT / "device-validation"))

import jvm_api_contracts as contracts
from classify_api_differences import ClassApi, Member


class JvmApiContractsTest(unittest.TestCase):
    def test_canonical_class_keeps_visible_surface_and_metadata(self):
        api = ClassApi(
            name="example.PublicApi",
            flags=frozenset({"ACC_PUBLIC", "ACC_FINAL"}),
            declaration="public final class example.PublicApi extends java.lang.Object",
            signature="Ljava/lang/Object;",
            kotlin_metadata="kotlin.Metadata(mv=[2,2,0])",
        )
        visible = Member(
            kind="method",
            name="value",
            descriptor="()Ljava/lang/String;",
            flags=frozenset({"ACC_PUBLIC", "ACC_FINAL"}),
            signature="()Ljava/lang/String;",
            annotations=("androidx.annotation.NonNull",),
        )
        private = Member(
            kind="field",
            name="hidden",
            descriptor="I",
            flags=frozenset({"ACC_PRIVATE"}),
        )
        api.members[visible.key] = visible
        api.members[private.key] = private

        payload = contracts.canonical_class(api)

        self.assertEqual(["ACC_FINAL", "ACC_PUBLIC"], payload["flags"])
        self.assertEqual("java.lang.Object", payload["superclass"])
        self.assertEqual(["value"], [member["name"] for member in payload["members"]])
        self.assertEqual(64, len(payload["kotlinMetadataSha256"]))

    def test_update_then_verify_is_deterministic(self):
        payload = {"formatVersion": 1, "module": "example", "classes": {}}
        with tempfile.TemporaryDirectory() as temporary:
            path = Path(temporary) / "example.json"
            self.assertIsNone(contracts.verify_or_update(path, payload, True))
            self.assertIsNone(contracts.verify_or_update(path, payload, False))
            changed = {**payload, "module": "changed"}
            self.assertIn("current build", contracts.verify_or_update(path, changed, False))


if __name__ == "__main__":
    unittest.main()
