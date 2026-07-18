import json
import sys
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT / "scripts"))

from module_registry import REGISTRY_PATH, RegistryError, load_registry


class ModuleRegistryTest(unittest.TestCase):
    def write_registry(self, root, payload):
        registry_path = Path(root) / REGISTRY_PATH
        registry_path.parent.mkdir(parents=True, exist_ok=True)
        registry_path.write_text(json.dumps(payload), encoding="utf-8")

    def test_checked_in_registry_has_expected_distribution_and_derived_paths(self):
        registry = load_registry(ROOT)

        self.assertEqual(1, json.loads((ROOT / REGISTRY_PATH).read_text())["schemaVersion"])
        self.assertEqual("io.github.hbmartin.onyx", registry.distribution["group"])
        self.assertEqual(19, len(registry.published_modules))
        self.assertEqual(
            Path("onyxsdk-base/support/onyxsdk-baselite/build/outputs/aar/onyxsdk-baselite-release.aar"),
            registry.module("baselite").aar_relative_path,
        )
        self.assertEqual(
            ":onyxsdk-base:support:onyxsdk-commons-io:assembleRelease",
            registry.module("commons-io").release_task,
        )
        self.assertEqual("Apache-2.0", registry.module("commons-io").license)
        self.assertEqual("LGPL-3.0-only", registry.module("base").license)
        self.assertNotIn("license", json.loads((ROOT / REGISTRY_PATH).read_text())["modules"][0])
        self.assertEqual(
            ("onyxsdk-base-1.8.5/classes.jar",),
            tuple(registry.module("base").device_validation["referenceCompileJars"]),
        )
        self.assertTrue(registry.module("baselite").device_validation["commonRecovered"])
        self.assertTrue(registry.module("pen-core").device_validation["commonRecovered"])
        self.assertIn(("api", "pen-core"), registry.module("pen").project_dependencies)
        self.assertEqual("platform", registry.module("recognition-bom").artifact_type)
        self.assertEqual(
            ":onyxsdk-recognition-bom:assemble",
            registry.module("recognition-bom").release_task,
        )

    def test_rejects_duplicate_coordinates_and_unknown_dependencies(self):
        payload = json.loads((ROOT / REGISTRY_PATH).read_text(encoding="utf-8"))
        with tempfile.TemporaryDirectory() as temporary:
            temp_root = Path(temporary)
            registry_path = temp_root / REGISTRY_PATH
            registry_path.parent.mkdir(parents=True)

            duplicate = json.loads(json.dumps(payload))
            duplicate["modules"][1]["artifactId"] = duplicate["modules"][0]["artifactId"]
            duplicate["modules"][1]["version"] = duplicate["modules"][0]["version"]
            registry_path.write_text(json.dumps(duplicate), encoding="utf-8")
            with self.assertRaisesRegex(RegistryError, "artifactId|coordinates"):
                load_registry(temp_root, validate_paths=False)

            duplicate_path = json.loads(json.dumps(payload))
            duplicate_path["modules"][1]["projectPath"] = duplicate_path["modules"][0]["projectPath"]
            registry_path.write_text(json.dumps(duplicate_path), encoding="utf-8")
            with self.assertRaisesRegex(RegistryError, "projectPath"):
                load_registry(temp_root, validate_paths=False)

            unknown = json.loads(json.dumps(payload))
            unknown["modules"][0]["projectDependencies"][0]["target"] = "missing"
            registry_path.write_text(json.dumps(unknown), encoding="utf-8")
            with self.assertRaisesRegex(RegistryError, "unknown module"):
                load_registry(temp_root, validate_paths=False)

            unsafe_reference = json.loads(json.dumps(payload))
            unsafe_reference["modules"][0]["deviceValidation"]["apiReferenceJars"] = [
                "../outside.jar"
            ]
            registry_path.write_text(json.dumps(unsafe_reference), encoding="utf-8")
            with self.assertRaisesRegex(RegistryError, "normalized relative path"):
                load_registry(temp_root, validate_paths=False)

    def test_rejects_project_directories_outside_repository(self):
        payload = json.loads((ROOT / REGISTRY_PATH).read_text(encoding="utf-8"))
        with tempfile.TemporaryDirectory() as temporary:
            temp_root = Path(temporary) / "repository"
            outside = Path(temporary) / "outside"
            outside.mkdir()

            parent_traversal = json.loads(json.dumps(payload))
            parent_traversal["modules"][0]["projectDir"] = "../outside"
            self.write_registry(temp_root, parent_traversal)
            with self.assertRaisesRegex(RegistryError, "remain within the repository"):
                load_registry(temp_root)

            absolute = json.loads(json.dumps(payload))
            absolute["modules"][0]["projectDir"] = str(outside)
            self.write_registry(temp_root, absolute)
            with self.assertRaisesRegex(RegistryError, "remain within the repository"):
                load_registry(temp_root)

            symlink = temp_root / "escaping-link"
            symlink.symlink_to(outside, target_is_directory=True)
            symlink_escape = json.loads(json.dumps(payload))
            symlink_escape["modules"][0]["projectDir"] = symlink.name
            self.write_registry(temp_root, symlink_escape)
            with self.assertRaisesRegex(RegistryError, "remain within the repository"):
                load_registry(temp_root)

    def test_non_published_modules_allow_disabled_device_validation(self):
        payload = json.loads((ROOT / REGISTRY_PATH).read_text(encoding="utf-8"))
        recovery_tests = next(
            module for module in payload["modules"] if module["id"] == "recovery-tests"
        )
        recovery_tests["deviceValidation"] = {
            "commonRecovered": False,
            "referenceCompileJars": [],
            "apiReferenceJars": [],
        }
        with tempfile.TemporaryDirectory() as temporary:
            self.write_registry(temporary, payload)
            registry = load_registry(Path(temporary), validate_paths=False)
        self.assertFalse(registry.module("recovery-tests").published)

    def test_unpublished_modules_cannot_supply_package_ownership(self):
        payload = json.loads((ROOT / REGISTRY_PATH).read_text(encoding="utf-8"))
        base = next(module for module in payload["modules"] if module["id"] == "base")
        recovery_tests = next(
            module for module in payload["modules"] if module["id"] == "recovery-tests"
        )
        base["legacyOwnedTypes"] = ["example.unpublished.Legacy"]
        recovery_tests["ownedPackages"] = ["example.unpublished"]
        with tempfile.TemporaryDirectory() as temporary:
            self.write_registry(temporary, payload)
            with self.assertRaisesRegex(RegistryError, "has no default package owner"):
                load_registry(Path(temporary), validate_paths=False)


if __name__ == "__main__":
    unittest.main()
