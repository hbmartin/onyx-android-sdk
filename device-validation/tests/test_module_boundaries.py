import io
import sys
import tempfile
import unittest
import zipfile
from dataclasses import replace
from pathlib import Path


ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT / "scripts"))

from module_registry import Module, Registry
from verify_module_boundaries import BoundaryError, verify_aars, verify_sources


LEGACY_UTILS = (
    "ClassUtils",
    "Debug",
    "DeviceBroadcastHelper",
    "LightUtils",
    "MagnifierUtils",
    "ReflectUtil",
    "Singleton",
    "SystemPropertiesUtil",
)


def module(module_id, packages=(), legacy=()):
    return Module(
        id=module_id,
        project_path=f":{module_id}",
        project_dir=module_id,
        artifact_id=module_id,
        version="1.0",
        name=module_id,
        description=module_id,
        published=True,
        license="LGPL-3.0-only",
        device_validation={},
        project_dependencies=(),
        owned_packages=tuple(packages),
        legacy_owned_types=tuple(legacy),
    )


def registry(root):
    return Registry(
        root=Path(root),
        distribution={"group": "example", "licenses": {}},
        modules=(
            module("base", ("com.onyx.android.sdk.utils", "example.base")),
            module("baselite", ("example.baselite",)),
            module("commons-io", ("example.commons",)),
            module(
                "device",
                ("example.device",),
                tuple(f"com.onyx.android.sdk.utils.{name}" for name in LEGACY_UTILS),
            ),
        ),
    )


def write_source(root, owner, package, type_name):
    path = Path(root) / owner.project_dir / "src/main/java" / Path(package.replace(".", "/")) / f"{type_name}.java"
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(f"package {package};\npublic class {type_name} {{}}\n", encoding="utf-8")
    return path


def write_aar(root, owner, classes):
    jar_bytes = io.BytesIO()
    with zipfile.ZipFile(jar_bytes, "w") as jar:
        for class_name in classes:
            jar.writestr(class_name.replace(".", "/") + ".class", b"class")
    aar = Path(root) / owner.aar_relative_path
    aar.parent.mkdir(parents=True, exist_ok=True)
    with zipfile.ZipFile(aar, "w") as archive:
        archive.writestr("classes.jar", jar_bytes.getvalue())


class ModuleBoundaryTest(unittest.TestCase):
    def test_rejects_ninth_device_utils_source(self):
        with tempfile.TemporaryDirectory() as temporary:
            data = registry(temporary)
            base = data.module("base")
            device = data.module("device")
            write_source(temporary, base, "com.onyx.android.sdk.utils", "BaseOnly")
            for name in LEGACY_UTILS:
                write_source(temporary, device, "com.onyx.android.sdk.utils", name)
            write_source(temporary, device, "com.onyx.android.sdk.utils", "NewDeviceUtil")

            with self.assertRaisesRegex(BoundaryError, "belongs to base"):
                verify_sources(data)

    def test_rejects_moved_legacy_exception_and_new_split_package(self):
        with tempfile.TemporaryDirectory() as temporary:
            data = registry(temporary)
            base = data.module("base")
            device = data.module("device")
            write_source(temporary, base, "com.onyx.android.sdk.utils", LEGACY_UTILS[0])
            for name in LEGACY_UTILS[1:]:
                write_source(temporary, device, "com.onyx.android.sdk.utils", name)
            write_source(temporary, device, "example.base", "SplitPackage")

            with self.assertRaises(BoundaryError) as raised:
                verify_sources(data)
            self.assertIn("legacy type", str(raised.exception))
            self.assertIn("package example.base belongs to base", str(raised.exception))

    def test_rejects_duplicate_compiled_class(self):
        with tempfile.TemporaryDirectory() as temporary:
            data = registry(temporary)
            for owner in data.published_modules:
                classes = [f"{owner.owned_packages[0]}.Only"]
                if owner.id in ("base", "device"):
                    classes.append("example.shared.Duplicate")
                write_aar(temporary, owner, classes)

            with self.assertRaisesRegex(BoundaryError, "Duplicate class example.shared.Duplicate"):
                verify_aars(data)

    def test_rejects_class_packaged_in_wrong_aar(self):
        with tempfile.TemporaryDirectory() as temporary:
            data = registry(temporary)
            for owner in data.published_modules:
                classes = [f"{owner.owned_packages[0]}.Only"]
                if owner.id == "device":
                    classes.append("example.base.Mispackaged")
                write_aar(temporary, owner, classes)

            with self.assertRaisesRegex(
                BoundaryError,
                "Class example.base.Mispackaged belongs to base, not device",
            ):
                verify_aars(data)

    def test_rejects_class_without_registry_owner(self):
        with tempfile.TemporaryDirectory() as temporary:
            data = registry(temporary)
            for owner in data.published_modules:
                classes = [f"{owner.owned_packages[0]}.Only"]
                if owner.id == "device":
                    classes.append("unowned.package.Unknown")
                write_aar(temporary, owner, classes)

            with self.assertRaisesRegex(
                BoundaryError,
                "Class unowned.package.Unknown has no registry owner",
            ):
                verify_aars(data)

    def test_nested_legacy_class_uses_top_level_owner(self):
        with tempfile.TemporaryDirectory() as temporary:
            data = registry(temporary)
            legacy = LEGACY_UTILS[0]
            for owner in data.published_modules:
                classes = [f"{owner.owned_packages[0]}.Only"]
                if owner.id == "device":
                    classes.append(f"com.onyx.android.sdk.utils.{legacy}$Nested")
                write_aar(temporary, owner, classes)

            counts = verify_aars(data)
            self.assertEqual(2, counts["device"])


if __name__ == "__main__":
    unittest.main()
