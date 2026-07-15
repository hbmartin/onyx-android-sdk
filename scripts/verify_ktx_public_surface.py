#!/usr/bin/env python3
"""Reject legacy and optional-framework types in the published KTX JVM surface."""

from __future__ import annotations

import json
import re
import sys
import tempfile
import zipfile
from pathlib import Path

from jvm_api_contracts import contract_for_jar, extract_classes
from module_registry import load_registry


ROOT = Path(__file__).resolve().parent.parent
FORBIDDEN = {
    "legacy Onyx pen SDK": re.compile(r"com[./]onyx[./]android[./]sdk[./]pen[./]"),
    "EventBus": re.compile(r"org[./]greenrobot[./]eventbus[./]"),
    "RxJava": re.compile(r"(?:io[./]reactivex|rx)[./]"),
    "AndroidX Fragment": re.compile(r"androidx[./]fragment[./]"),
    "Android Data Binding": re.compile(r"androidx?[./]databinding[./]"),
}

LEGACY_RENDERER_BYTECODE = (
    b"com/onyx/android/sdk/pen/NeoPenNative",
    b"com/onyx/android/sdk/pen/NeoPenConfig",
    b"com/onyx/android/sdk/pen/NeoPenResult",
    b"com/onyx/android/sdk/pen/NeoNativePen",
    b"com/onyx/android/sdk/pen/NeoBallpointInkPen",
    b"com/onyx/android/sdk/pen/NeoBrushPen",
    b"com/onyx/android/sdk/pen/NeoCharcoalPen",
    b"com/onyx/android/sdk/pen/NeoCharcoalPenV2",
    b"com/onyx/android/sdk/pen/NeoFountainPen",
    b"com/onyx/android/sdk/pen/NeoFountainPenV2",
    b"com/onyx/android/sdk/pen/NeoMarkerPen",
    b"com/onyx/android/sdk/pen/NeoPencilPen",
    b"com/onyx/android/sdk/pen/NeoSquarePen",
)


def main() -> int:
    root = Path(sys.argv[1]).resolve() if len(sys.argv) > 1 else ROOT
    registry = load_registry(root)
    module = next(item for item in registry.published_modules if item.artifact_id == "onyxsdk-ktx")
    aar = root / module.aar_relative_path
    with tempfile.TemporaryDirectory() as temporary:
        jar = extract_classes(aar, Path(temporary))
        contract = contract_for_jar(jar, module.artifact_id)
        with zipfile.ZipFile(jar) as classes:
            bytecode = b"".join(
                classes.read(name) for name in classes.namelist() if name.endswith(".class")
            )

    failures: list[str] = []
    referenced_legacy_renderers = [
        marker.decode("ascii") for marker in LEGACY_RENDERER_BYTECODE if marker in bytecode
    ]
    if referenced_legacy_renderers:
        failures.append(
            "KTX bytecode references legacy native renderer classes: "
            + ", ".join(referenced_legacy_renderers)
        )
    if b"com/onyx/android/sdk/pennative/NeoPenNative" not in bytecode:
        failures.append("KTX bytecode does not reference the modern pennative renderer")
    for class_name, class_payload in contract["classes"].items():
        values = {
            "class signature": class_payload.get("signature"),
            "superclass": class_payload.get("superclass"),
            "interfaces": class_payload.get("interfaces"),
        }
        for member in class_payload["members"]:
            member_label = f"{member['kind']} {member['name']}{member['descriptor']}"
            values[f"{member_label} descriptor"] = member["descriptor"]
            values[f"{member_label} signature"] = member.get("signature")
            values[f"{member_label} annotations"] = member.get("annotations")
        for location, value in values.items():
            rendered = json.dumps(value, sort_keys=True)
            for label, pattern in FORBIDDEN.items():
                if pattern.search(rendered):
                    failures.append(f"{class_name}: {location} exposes {label}: {rendered}")

    if failures:
        print("KTX public-surface violations:\n" + "\n".join(failures), file=sys.stderr)
        return 1
    print(f"Verified {len(contract['classes'])} KTX classes: no forbidden public signature types")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
