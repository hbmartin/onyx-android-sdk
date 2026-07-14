#!/usr/bin/env python3
"""Create a read-only, serial-free BOOX firmware comparison manifest."""

from __future__ import annotations

import argparse
import datetime as dt
import hashlib
import json
import re
import shutil
import subprocess
from pathlib import Path


ROOT = Path(__file__).resolve().parent.parent
PROPERTIES = (
    "ro.product.manufacturer",
    "ro.product.model",
    "ro.product.device",
    "ro.product.board",
    "ro.build.version.sdk",
    "ro.build.fingerprint",
    "ro.build.display.id",
    "ro.hardware",
    "ro.product.cpu.abilist",
)
FRAMEWORK_FILES = (
    "/system/framework/framework.jar",
    "/system/framework/services.jar",
)
CLASS_NAME = "android.onyx.ViewUpdateHelper"


def run(command: list[str], *, capture: bool = True) -> str:
    result = subprocess.run(
        command,
        check=True,
        text=True,
        stdout=subprocess.PIPE if capture else None,
        stderr=subprocess.PIPE if capture else None,
    )
    return result.stdout.strip() if capture else ""


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(chunk)
    return digest.hexdigest()


def main() -> int:
    parser = argparse.ArgumentParser(
        description=(
            "Pull an allowlisted set of framework files and decompile ViewUpdateHelper. "
            "This command performs no Binder probing and changes no device state."
        ),
    )
    parser.add_argument("--adb", default=shutil.which("adb") or "adb")
    parser.add_argument("--serial", help="ADB selector; deliberately omitted from every output file")
    parser.add_argument("--jadx", default=shutil.which("jadx"))
    parser.add_argument("--output", type=Path)
    args = parser.parse_args()

    timestamp = dt.datetime.now(dt.timezone.utc).strftime("%Y%m%dT%H%M%SZ")
    output = (args.output or ROOT / "build" / "firmware-inspection" / timestamp).resolve()
    artifacts = output / "artifacts"
    artifacts.mkdir(parents=True, exist_ok=False)
    adb = [args.adb] + (["-s", args.serial] if args.serial else [])

    state = run(adb + ["get-state"])
    if state != "device":
        raise RuntimeError(f"ADB target is not ready: {state!r}")

    properties = {
        key: run(adb + ["shell", "getprop", key])
        for key in PROPERTIES
    }
    pulled: list[dict[str, object]] = []
    framework: Path | None = None
    for remote in FRAMEWORK_FILES:
        readable = run(adb + ["shell", f"if [ -r '{remote}' ]; then echo yes; fi"])
        if readable != "yes":
            continue
        local = artifacts / Path(remote).name
        with local.open("wb") as stream:
            subprocess.run(
                adb + ["exec-out", "cat", remote],
                check=True,
                stdout=stream,
                stderr=subprocess.PIPE,
            )
        pulled.append({
            "devicePath": remote,
            "localRelativePath": str(local.relative_to(output)),
            "sizeBytes": local.stat().st_size,
            "sha256": sha256(local),
        })
        if remote.endswith("/framework.jar"):
            framework = local

    source_path = output / "ViewUpdateHelper.java"
    decompiler = {"class": CLASS_NAME, "status": "unavailable", "tool": None}
    if framework is not None and args.jadx:
        command = [
            args.jadx,
            "--quiet",
            "--comments-level",
            "none",
            "--single-class",
            CLASS_NAME,
            "--single-class-output",
            str(source_path),
            str(framework),
        ]
        try:
            run(command)
            decompiler = {"class": CLASS_NAME, "status": "decompiled", "tool": "jadx"}
        except subprocess.CalledProcessError as error:
            decompiler = {
                "class": CLASS_NAME,
                "status": "failed",
                "tool": "jadx",
                "error": (error.stderr or "jadx failed")[-1000:],
            }

    constants: dict[str, int] = {}
    methods: list[str] = []
    if source_path.is_file():
        source = source_path.read_text(encoding="utf-8", errors="replace")
        constants = {
            name: int(value)
            for name, value in re.findall(
                r"public\s+static(?:\s+final)?\s+int\s+([A-Z][A-Z0-9_]*)\s*=\s*(-?\d+)\s*;",
                source,
            )
        }
        methods = sorted(set(re.findall(
            r"public\s+static\s+[\w<>\[\].?]+\s+([a-zA-Z_$][\w$]*)\s*\(",
            source,
        )))

    manifest = {
        "formatVersion": 1,
        "capturedAtUtc": timestamp,
        "safety": {
            "deviceMutations": False,
            "binderTransactions": False,
            "unknownCodeScanning": False,
            "serialRecorded": False,
        },
        "properties": properties,
        "frameworkFiles": pulled,
        "viewUpdateHelper": {
            **decompiler,
            "publicStaticIntSymbols": constants,
            "publicStaticMethods": methods,
        },
    }
    (output / "manifest.json").write_text(
        json.dumps(manifest, indent=2, sort_keys=True) + "\n",
        encoding="utf-8",
    )
    print(output)
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
