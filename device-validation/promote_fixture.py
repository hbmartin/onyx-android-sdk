#!/usr/bin/env python3
"""Promote one results/ run into a sanitized, git-tracked fixture.

    python3 promote_fixture.py results/<run-dir> --name <fixture-name>

What is promoted:
  - result streams (*.jsonl) with host-volatile fields normalized
    (monotonicNs/wallClockMs -> 0, thread -> "") so fixtures diff cleanly;
    device-produced payloads (pen timestamps, pressures) are untouched
  - raw evdev traces, replay inputs/counts, operator verdicts, api-surface
    and operator notes
  - device metadata (getprop redacted, capabilities, display)
  - the neo-pen differential transcript

What is not: logcat dumps and am-start timings (non-deterministic bulk).

Identifying tokens (serial numbers, MACs, IMEI, ...) are collected from
getprop and scrubbed from every promoted text file. Comparisons are
REGENERATED from the sanitized streams with compare_results.py, so the pinned
comparison.json always agrees with the pinned inputs; tests/test_fixtures.py
enforces that invariant on every run.
"""

from __future__ import annotations

import argparse
import json
import re
import subprocess
import sys
from datetime import datetime, timezone
from pathlib import Path

ROOT = Path(__file__).resolve().parent

VOLATILE = {"monotonicNs": 0, "wallClockMs": 0, "thread": ""}
SECRET_PROP_RE = re.compile(
    r"serial|mac[_.]|[._]mac\b|bt_addr|btaddr|bluetooth_address|imei|meid|iccid|msisdn|oaid|uuid|token",
    re.IGNORECASE)
GETPROP_LINE_RE = re.compile(r"^\[([^\]]+)\]: \[(.*)\]$")

VERBATIM_PATTERNS = (
    "*-getevent.txt",
    "replay-event-count.txt",
    "operator-verdicts.jsonl",
    "operator-notes.md",
    "api-surface.json",
    "neo-pen-differential.txt",
)
DEVICE_FILES = ("getprop.txt", "getevent-capabilities.txt", "display.txt", "adb-shell-id.txt")


def collect_secrets(getprop: Path) -> set[str]:
    secrets = set()
    if not getprop.is_file():
        return secrets
    for line in getprop.read_text(encoding="utf-8", errors="replace").splitlines():
        match = GETPROP_LINE_RE.match(line.strip())
        if match and SECRET_PROP_RE.search(match.group(1)):
            value = match.group(2).strip()
            if len(value) >= 4:
                secrets.add(value)
    return secrets


def scrub(text: str, secrets: set[str]) -> str:
    for secret in sorted(secrets, key=len, reverse=True):
        text = text.replace(secret, "<redacted>")
    return text


def sanitize_jsonl(source: Path, destination: Path, secrets: set[str]) -> None:
    lines = []
    for raw in source.read_text(encoding="utf-8").splitlines():
        if not raw.strip():
            continue
        record = json.loads(raw)
        for key, neutral in VOLATILE.items():
            if key in record:
                record[key] = neutral
        lines.append(json.dumps(record, separators=(",", ":"), sort_keys=True))
    destination.write_text(scrub("\n".join(lines) + "\n", secrets), encoding="utf-8")


def copy_text(source: Path, destination: Path, secrets: set[str]) -> None:
    destination.write_text(
        scrub(source.read_text(encoding="utf-8", errors="replace"), secrets), encoding="utf-8")


def redact_getprop(source: Path, destination: Path, secrets: set[str]) -> None:
    lines = []
    for line in source.read_text(encoding="utf-8", errors="replace").splitlines():
        match = GETPROP_LINE_RE.match(line.strip())
        if match and SECRET_PROP_RE.search(match.group(1)) and match.group(2).strip():
            lines.append(f"[{match.group(1)}]: [<redacted>]")
        else:
            lines.append(line)
    destination.write_text(scrub("\n".join(lines) + "\n", secrets), encoding="utf-8")


def was_live(results_dir: Path, comparison_dir_name: str) -> bool:
    original = results_dir / comparison_dir_name / "comparison.json"
    if original.is_file():
        return bool(json.loads(original.read_text(encoding="utf-8")).get("liveComparison"))
    return False


def regenerate_comparison(reference: Path, recovered: Path,
                          output: Path, live: bool, api_surface: Path | None) -> None:
    command = [sys.executable, str(ROOT / "compare_results.py"), str(reference), str(recovered),
               "--output", str(output)]
    if live:
        command.append("--live")
    if api_surface is not None and api_surface.is_file():
        command.extend(["--api-surface", str(api_surface)])
    subprocess.run(command, check=True)


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__,
                                     formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("results_dir", type=Path)
    parser.add_argument("--name", required=True,
                        help="fixture directory name, e.g. automated-noteair4c-android13")
    parser.add_argument("--fixtures-root", type=Path, default=ROOT / "fixtures")
    args = parser.parse_args()

    results = args.results_dir.resolve()
    if not results.is_dir():
        raise SystemExit(f"not a results directory: {results}")
    fixture = args.fixtures_root / args.name
    if fixture.exists():
        raise SystemExit(f"fixture already exists: {fixture} (delete it first to re-promote)")
    fixture.mkdir(parents=True)

    secrets = collect_secrets(results / "device" / "getprop.txt")

    promoted = []
    for source in sorted(results.glob("*.jsonl")):
        sanitize_jsonl(source, fixture / source.name, secrets)
        promoted.append(source.name)
    for pattern in VERBATIM_PATTERNS:
        for source in sorted(results.glob(pattern)):
            if source.suffix == ".jsonl":
                continue  # already promoted sanitized above
            copy_text(source, fixture / source.name, secrets)
            promoted.append(source.name)
    device_dir = results / "device"
    if device_dir.is_dir():
        (fixture / "device").mkdir()
        for name in DEVICE_FILES:
            source = device_dir / name
            if not source.is_file():
                continue
            if name == "getprop.txt":
                redact_getprop(source, fixture / "device" / name, secrets)
            else:
                copy_text(source, fixture / "device" / name, secrets)
            promoted.append(f"device/{name}")

    # Regenerate every comparison from the sanitized streams.
    comparisons = []
    for reference in sorted(fixture.glob("*-reference.jsonl")):
        stem = reference.name[:-len("-reference.jsonl")]
        recovered = fixture / f"{stem}-recovered.jsonl"
        if recovered.is_file():
            output = fixture / f"{stem}-comparison"
            regenerate_comparison(reference, recovered, output,
                                  was_live(results, f"{stem}-comparison"), None)
            comparisons.append(output.name)
    if (fixture / "reference.jsonl").is_file() and (fixture / "recovered.jsonl").is_file():
        api = fixture / "api-surface.json"
        regenerate_comparison(fixture / "reference.jsonl", fixture / "recovered.jsonl",
                              fixture, was_live(results, "."), api if api.is_file() else None)
        comparisons.append(".")
    if (fixture / "reference-replay.jsonl").is_file() and (fixture / "recovered-replay.jsonl").is_file():
        regenerate_comparison(fixture / "reference-replay.jsonl",
                              fixture / "recovered-replay.jsonl",
                              fixture / "replay-comparison", False, None)
        comparisons.append("replay-comparison")

    (fixture / "metadata.json").write_text(json.dumps({
        "sourceRun": results.name,
        "promotedAt": datetime.now(timezone.utc).isoformat(timespec="seconds"),
        "sanitized": {"volatileFields": sorted(VOLATILE), "redactedTokens": len(secrets)},
        "promotedFiles": sorted(promoted),
        "comparisons": sorted(comparisons),
    }, indent=2, sort_keys=True) + "\n", encoding="utf-8")

    print(f"Promoted {len(promoted)} files into {fixture}")
    print("Review `git diff --stat` and commit the fixture; tests/test_fixtures.py will pin it.")


if __name__ == "__main__":
    main()
