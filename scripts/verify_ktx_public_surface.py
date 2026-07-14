#!/usr/bin/env python3
"""Reject legacy and optional-framework types in the published KTX JVM surface."""

from __future__ import annotations

import json
import re
import sys
import tempfile
from pathlib import Path

from jvm_api_contracts import contract_for_jar, extract_classes
from module_registry import load_registry


ROOT = Path(__file__).resolve().parent.parent
FORBIDDEN = {
    "legacy Onyx SDK": re.compile(r"com[./]onyx[./]android[./]sdk[./](?!ktx[./])"),
    "EventBus": re.compile(r"org[./]greenrobot[./]eventbus[./]"),
    "RxJava": re.compile(r"(?:io[./]reactivex|rx)[./]"),
    "AndroidX Fragment": re.compile(r"androidx[./]fragment[./]"),
    "Android Data Binding": re.compile(r"androidx?[./]databinding[./]"),
}


def main() -> int:
    root = Path(sys.argv[1]).resolve() if len(sys.argv) > 1 else ROOT
    registry = load_registry(root)
    module = next(item for item in registry.published_modules if item.artifact_id == "onyxsdk-ktx")
    aar = root / module.aar_relative_path
    with tempfile.TemporaryDirectory() as temporary:
        jar = extract_classes(aar, Path(temporary))
        contract = contract_for_jar(jar, module.artifact_id)

    failures: list[str] = []
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
