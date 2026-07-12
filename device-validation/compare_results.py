#!/usr/bin/env python3
"""Normalize and compare reference/recovered device-validation JSONL."""

from __future__ import annotations

import argparse
import json
import math
from collections import Counter, defaultdict
from pathlib import Path
from typing import Any

IGNORED_KEYS = {"variant", "sequence", "monotonicNs", "wallClockMs", "thread", "timestamp"}
FLOAT_TOLERANCE = 0.001


def load(path: Path) -> list[dict[str, Any]]:
    records = []
    for number, raw in enumerate(path.read_text(encoding="utf-8").splitlines(), 1):
        if not raw.strip():
            continue
        try:
            records.append(json.loads(raw))
        except json.JSONDecodeError as error:
            raise SystemExit(f"{path}:{number}: invalid JSON: {error}") from error
    return records


def equivalent(left: Any, right: Any) -> bool:
    if isinstance(left, dict) and isinstance(right, dict):
        left_keys = set(left) - IGNORED_KEYS
        right_keys = set(right) - IGNORED_KEYS
        return left_keys == right_keys and all(equivalent(left[key], right[key]) for key in left_keys)
    if isinstance(left, list) and isinstance(right, list):
        return len(left) == len(right) and all(equivalent(a, b) for a, b in zip(left, right))
    if isinstance(left, (int, float)) and isinstance(right, (int, float)):
        if isinstance(left, bool) or isinstance(right, bool):
            return left == right
        return math.isclose(float(left), float(right), abs_tol=FLOAT_TOLERANCE, rel_tol=0.0)
    return left == right


def keyed(records: list[dict[str, Any]], live: bool) -> dict[tuple[str, str, str, int], dict[str, Any]]:
    counts: Counter[tuple[str, str, str]] = Counter()
    result = {}
    for record in records:
        if live and record.get("phase") == "event":
            continue
        stem = (record.get("suite", ""), record.get("caseId", ""), record.get("phase", ""))
        index = counts[stem]
        counts[stem] += 1
        result[stem + (index,)] = record
    return result


def pen_summary(records: list[dict[str, Any]]) -> dict[str, Any]:
    raw = [r.get("output", {}) for r in records if r.get("caseId") == "raw_jni"]
    states = [event.get("state") for event in raw if isinstance(event, dict)]
    pressure = [event.get("pressure") for event in raw if isinstance(event, dict) and isinstance(event.get("pressure"), (int, float))]
    errors = []
    if any(state not in {0, 1, 2, 3, 4, 5, 6} for state in states):
        errors.append("unknown_state")
    if any(value < 0 or value > 4095 for value in pressure):
        errors.append("pressure_out_of_range")
    transitions = [state for state in states if state in {0, 1, 2, 3, 6}]
    active = False
    for state in transitions:
        if state == 0:
            if active:
                errors.append("down_while_active")
            active = True
        elif state == 1 and not active:
            errors.append("move_without_down")
        elif state in {2, 3, 6}:
            active = False
    if active:
        errors.append("unterminated_stroke")
    return {
        "eventCount": len(raw),
        "stateCounts": dict(sorted(Counter(states).items(), key=lambda item: str(item[0]))),
        "pressureMin": min(pressure) if pressure else None,
        "pressureMax": max(pressure) if pressure else None,
        "erasingEvents": sum(bool(e.get("erasing")) for e in raw if isinstance(e, dict)),
        "errors": sorted(set(errors)),
    }


def classify(reference: dict[str, Any] | None, recovered: dict[str, Any] | None, same: bool) -> str:
    if reference is None or recovered is None:
        return "harness_error"
    statuses = {reference.get("status"), recovered.get("status")}
    if "permission_denied" in statuses:
        return "permission_denied"
    if "unsupported_hardware" in statuses:
        return "unsupported_hardware"
    if "harness_error" in statuses:
        return "harness_error"
    if same:
        return "match"
    if reference.get("suite") == "inventory":
        return "platform_variation"
    return "recovery_defect"


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("reference", type=Path)
    parser.add_argument("recovered", type=Path)
    parser.add_argument("--output", type=Path, required=True)
    parser.add_argument("--live", action="store_true")
    parser.add_argument("--api-surface", type=Path)
    args = parser.parse_args()

    reference_records = load(args.reference)
    recovered_records = load(args.recovered)
    left = keyed(reference_records, args.live)
    right = keyed(recovered_records, args.live)
    comparisons = []
    counts: Counter[str] = Counter()
    for key in sorted(set(left) | set(right)):
        reference = left.get(key)
        recovered = right.get(key)
        same = reference is not None and recovered is not None and equivalent(reference, recovered)
        classification = classify(reference, recovered, same)
        counts[classification] += 1
        comparisons.append({
            "key": list(key),
            "classification": classification,
            "reference": reference,
            "recovered": recovered,
        })

    pen = None
    if args.live:
        ref_pen = pen_summary(reference_records)
        rec_pen = pen_summary(recovered_records)
        if ref_pen["eventCount"] == 0 and rec_pen["eventCount"] == 0:
            pen_classification = "harness_error"
            ref_pen["errors"].append("no_events_captured")
            rec_pen["errors"].append("no_events_captured")
        elif ref_pen["eventCount"] == 0 or rec_pen["eventCount"] == 0:
            pen_classification = "recovery_defect"
            if ref_pen["eventCount"] == 0:
                ref_pen["errors"].append("no_events_captured")
            if rec_pen["eventCount"] == 0:
                rec_pen["errors"].append("no_events_captured")
        else:
            pen_classification = "match" if not ref_pen["errors"] and not rec_pen["errors"] else "recovery_defect"
        counts[pen_classification] += 1
        pen = {"classification": pen_classification, "reference": ref_pen, "recovered": rec_pen}

    api_summary = None
    if args.api_surface:
        api = json.loads(args.api_surface.read_text(encoding="utf-8"))
        api_summary = {}
        for module, values in api.items():
            missing = len(values.get("missingClasses", []))
            changed = len(values.get("changedSignatures", []))
            extra = len(values.get("extraClasses", []))
            counts["recovery_defect"] += missing + changed
            counts["platform_variation"] += extra
            api_summary[module] = {
                "missingClasses": missing,
                "changedSignatures": changed,
                "recoveryExtensions": extra,
                "classification": "recovery_defect" if missing or changed else "match",
            }

    payload = {
        "floatTolerance": FLOAT_TOLERANCE,
        "liveComparison": args.live,
        "counts": dict(sorted(counts.items())),
        "penTrace": pen,
        "apiSurface": api_summary,
        "comparisons": comparisons,
    }
    args.output.mkdir(parents=True, exist_ok=True)
    (args.output / "comparison.json").write_text(json.dumps(payload, indent=2, sort_keys=True) + "\n", encoding="utf-8")

    lines = ["# Onyx SDK device comparison", "", f"Float tolerance: `{FLOAT_TOLERANCE}`", "", "## Classifications", ""]
    for name in ("match", "recovery_defect", "platform_variation", "permission_denied", "unsupported_hardware", "harness_error"):
        lines.append(f"- `{name}`: {counts[name]}")
    if pen is not None:
        lines.extend(["", "## Live pen traces", "", f"Classification: `{pen['classification']}`", "",
                      f"- Reference: `{json.dumps(pen['reference'], sort_keys=True)}`",
                      f"- Recovered: `{json.dumps(pen['recovered'], sort_keys=True)}`"])
    if api_summary is not None:
        lines.extend(["", "## Public API surface", ""])
        for module, summary in api_summary.items():
            lines.append(
                f"- `{module}`: `{summary['classification']}` — "
                f"{summary['missingClasses']} missing classes, "
                f"{summary['changedSignatures']} changed class signatures, "
                f"{summary['recoveryExtensions']} recovery extensions"
            )
        lines.extend(["", "Exact class names are recorded in `api-surface.json`."])
    mismatches = [item for item in comparisons if item["classification"] != "match"]
    lines.extend(["", "## Non-matching cases", ""])
    if not mismatches:
        lines.append("None.")
    else:
        for item in mismatches:
            lines.append(f"- `{item['classification']}` — `{'/'.join(map(str, item['key'][:3]))}` occurrence {item['key'][3]}")
    (args.output / "report.md").write_text("\n".join(lines) + "\n", encoding="utf-8")


if __name__ == "__main__":
    main()
