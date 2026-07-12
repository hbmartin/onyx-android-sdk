#!/usr/bin/env python3
"""Scenario catalog, gates, verdicts, and report for the guided pen protocol.

Subcommands (used by run-comparison.sh --suite guided):
  list                        print scenario ids in run order
  seconds <id>                print capture duration for a scenario
  skippable <id>              exit 0 if the scenario may be skipped, 1 otherwise
  brief <id>                  print the operator instruction block
  check <id> <results.jsonl>  run automated gates on one variant's capture
  verdict --output-dir D --scenario ID --verdict V [--notes N]
                              append one operator verdict record
  report --output-dir D       write report.md; exit non-zero on any failure
"""

from __future__ import annotations

import argparse
import json
import sys
from pathlib import Path
from typing import Any

import compare_results

SCENARIOS: list[dict[str, Any]] = [
    {
        "id": "du-refresh",
        "title": "DU refresh mode",
        "seconds": 30,
        "instruction": "The canvas refreshes in DU (direct update). Draw several strokes at a\n"
                       "normal pace inside the large guide box.",
        "confirm": "Ink appears near-instantly with a slightly grainy, binary look and no\n"
                   "full-screen flashing. Mild ghosting around strokes is expected in DU.",
    },
    {
        "id": "gu-refresh",
        "title": "GU refresh mode",
        "seconds": 30,
        "instruction": "The canvas refreshes in GU (grayscale update). Draw the same strokes\n"
                       "as the DU pass.",
        "confirm": "Strokes render with smoother gray edges than DU and still without\n"
                   "full-screen flashing; latency is slightly higher than DU.",
    },
    {
        "id": "gc-refresh",
        "title": "GC refresh mode",
        "seconds": 30,
        "instruction": "The canvas refreshes in GC (full grayscale clear). Draw a few strokes,\n"
                       "pausing between them.",
        "confirm": "Each refresh visibly flashes the region to black and back, and leaves\n"
                   "the cleanest ink of the three modes with no ghosting.",
    },
    {
        "id": "scribble-mode",
        "title": "Hardware scribble mode",
        "seconds": 30,
        "instruction": "Scribble mode is active: the EPD controller draws ink directly in\n"
                       "hardware. Write a few words inside the guide box.",
        "confirm": "Ink tracks the nib with the lowest latency of any scenario and the\n"
                   "software canvas may lag behind the hardware ink — that is expected.",
    },
    {
        "id": "region-refresh",
        "title": "Region-limited input",
        "seconds": 30,
        "instruction": "Input is limited to the large guide box. Draw strokes that start\n"
                       "inside the box and deliberately cross outside it, and draw one stroke\n"
                       "entirely outside the box.",
        "confirm": "Ink renders only inside the limit region; the stroke drawn fully\n"
                   "outside leaves no ink.",
    },
    {
        "id": "ghosting-cleanup",
        "title": "Ghosting and GC cleanup",
        "seconds": 45,
        "instruction": "Scribble heavily (dense back-and-forth hatching) for the first two\n"
                       "thirds of the capture, then lift the pen and watch.",
        "confirm": "DU ghosting accumulates while hatching; at the two-thirds mark the\n"
                   "canvas clears with a full GC flash and no residual ghost images remain.",
    },
    {
        "id": "pressure",
        "title": "Light and heavy pressure",
        "seconds": 30,
        "instruction": "Draw several strokes with the lightest touch that still leaves ink,\n"
                       "then several pressing firmly.",
        "confirm": "Both extremes leave continuous ink with no dropouts on light strokes.",
        "gates": ["pressure_spread"],
    },
    {
        "id": "tilt",
        "title": "Stylus tilt",
        "seconds": 30,
        "instruction": "Draw strokes holding the stylus near-vertical, then again at a\n"
                       "steep slant (like shading with a pencil), rolling the angle as you go.",
        "confirm": "Strokes stay continuous at every angle.",
        "gates": ["tilt_variation"],
    },
    {
        "id": "side-button-erase",
        "title": "Side-button erase",
        "seconds": 30,
        "instruction": "Draw a few strokes, then hold the stylus side button and rub over\n"
                       "them to erase.",
        "confirm": "With the button held, the tool acts as an eraser (erase marks render\n"
                   "red on the canvas) and released strokes draw normally again.",
        "gates": ["erase_events"],
    },
    {
        "id": "rear-eraser",
        "title": "Rear eraser",
        "seconds": 30,
        "skippable": True,
        "instruction": "If your stylus has a rear eraser: draw a few strokes, flip the stylus,\n"
                       "and rub them out with the rear end.",
        "confirm": "The rear end erases without any button held.",
        "gates": ["erase_events"],
    },
    {
        "id": "pause-resume",
        "title": "Pause and resume mid-capture",
        "seconds": 45,
        "instruction": "Draw continuously for the whole capture. The reader pauses itself at\n"
                       "one third and resumes at two thirds — keep the pen moving throughout.",
        "confirm": "Ink stops appearing during the middle third and resumes afterward\n"
                   "without a restart or crash.",
        "gates": ["scenario_actions:2"],
    },
    {
        "id": "restart",
        "title": "Reader restart mid-capture",
        "seconds": 45,
        "instruction": "Draw continuously for the whole capture. The reader fully quits and\n"
                       "restarts at the halfway mark — keep the pen moving throughout.",
        "confirm": "Ink pauses briefly at the halfway mark and then resumes; the app does\n"
                   "not crash and strokes after the restart render normally.",
        "gates": ["scenario_actions:1"],
    },
]

BY_ID = {scenario["id"]: scenario for scenario in SCENARIOS}
DEFECT_CLASSIFICATIONS = (
    "recovery_defect",
    "missing_in_recovered",
    "extra_in_recovered",
    "harness_error",
)


def load_raw(path: Path) -> list[dict[str, Any]]:
    records = compare_results.load(path)
    return [r.get("output", {}) for r in records if r.get("caseId") == "raw_jni"]


def gate_failures(scenario: dict[str, Any], path: Path) -> list[str]:
    records = compare_results.load(path)
    summary = compare_results.pen_summary(records)
    failures = []
    if summary["eventCount"] == 0:
        failures.append("no stylus events captured (raw trace is empty)")
    if summary["errors"]:
        failures.append(f"state grammar violations: {summary['errors']}")
    raw = load_raw(path)
    for gate in scenario.get("gates", []):
        if gate == "pressure_spread":
            low, high = summary["pressureMin"], summary["pressureMax"]
            if low is None or high - low < 1500:
                failures.append(f"pressure spread too small (min={low}, max={high}, need >= 1500); "
                                "draw both feather-light and firm strokes")
        elif gate == "tilt_variation":
            for axis in ("tiltX", "tiltY"):
                values = [value for e in raw
                          for value in (e.get(axis),) if isinstance(value, (int, float))]
                if values and max(values) - min(values) >= 10:
                    break
            else:
                failures.append("tilt did not vary (need >= 10 units of range on either axis); "
                                "roll the stylus between vertical and slanted")
        elif gate == "erase_events":
            erasing = sum(1 for e in raw if e.get("erasing") or e.get("shortcutErasing"))
            if erasing == 0:
                failures.append("no erase events captured; hold the side button / use the rear "
                                "eraser while rubbing over existing strokes")
        elif gate.startswith("scenario_actions:"):
            expected = int(gate.split(":", 1)[1])
            actions = sum(1 for r in records if r.get("caseId") == "scenario_action")
            if actions != expected:
                failures.append(f"expected {expected} scheduled scenario action(s), saw {actions}")
        else:
            failures.append(f"unknown gate: {gate}")
    return failures


def cmd_check(scenario_id: str, results: Path) -> int:
    scenario = BY_ID[scenario_id]
    failures = gate_failures(scenario, results)
    if failures:
        for failure in failures:
            print(f"GATE FAILED [{scenario_id}] {results.name}: {failure}", file=sys.stderr)
        return 1
    summary = compare_results.pen_summary(compare_results.load(results))
    print(f"gates passed [{scenario_id}] {results.name}: {summary['eventCount']} events, "
          f"pressure {summary['pressureMin']}–{summary['pressureMax']}, "
          f"{summary['erasingEvents']} erasing")
    return 0


def cmd_brief(scenario_id: str) -> None:
    scenario = BY_ID[scenario_id]
    bar = "=" * 72
    print(bar)
    print(f"SCENARIO: {scenario['title']}  ({scenario_id}, {scenario['seconds']}s per capture)")
    print(bar)
    print(scenario["instruction"])
    print()
    print("What to confirm:")
    print(scenario["confirm"])
    print(bar)


def cmd_verdict(output_dir: Path, scenario_id: str, verdict: str, notes: str) -> None:
    record = {"scenario": scenario_id, "verdict": verdict, "notes": notes}
    with (output_dir / "operator-verdicts.jsonl").open("a", encoding="utf-8") as sink:
        sink.write(json.dumps(record) + "\n")


def cmd_report(output_dir: Path) -> int:
    verdicts = {}
    verdict_file = output_dir / "operator-verdicts.jsonl"
    if verdict_file.is_file():
        for record in compare_results.load(verdict_file):
            verdicts[record["scenario"]] = record

    lines = ["# Guided pen validation", "",
             "Per scenario: both variants captured live with automated gates "
             "(non-empty raw trace, valid state grammar, scenario-specific checks), "
             "compared with `compare_results.py --live`, and confirmed by the operator.", "",
             "## Scenarios", ""]
    failures = []
    for scenario in SCENARIOS:
        sid = scenario["id"]
        verdict = verdicts.get(sid, {}).get("verdict", "missing")
        notes = verdicts.get(sid, {}).get("notes", "")
        comparison_file = output_dir / f"{sid}-comparison" / "comparison.json"
        if verdict == "skipped":
            lines.append(f"- `{sid}`: **skipped** — {notes or 'not testable with this hardware'}")
            if not scenario.get("skippable"):
                failures.append(sid)
            continue
        counts = "not compared"
        comparison_failed = not comparison_file.is_file()
        if not comparison_failed:
            comparison_counts = None
            comparison_error = None
            try:
                payload = json.loads(comparison_file.read_text(encoding="utf-8"))
                comparison_counts = payload["counts"]
                if not isinstance(comparison_counts, dict) or any(
                        isinstance(value, bool) or not isinstance(value, int)
                        for value in comparison_counts.values()):
                    raise ValueError("counts must map classifications to integers")
            except (OSError, json.JSONDecodeError, KeyError, TypeError, ValueError) as error:
                comparison_error = error
            if comparison_error is not None:
                counts = f"invalid comparison ({comparison_error})"
                comparison_failed = True
            else:
                assert comparison_counts is not None
                counts = ", ".join(f"{k}={v}" for k, v in comparison_counts.items())
                comparison_failed = any(comparison_counts.get(name, 0)
                                        for name in DEFECT_CLASSIFICATIONS)
        if verdict != "same" or comparison_failed:
            failures.append(sid)
        note_suffix = f" — {notes}" if notes else ""
        lines.append(f"- `{sid}`: operator verdict **{verdict}**; comparison: {counts}{note_suffix}")

    replay_file = output_dir / "replay-comparison" / "comparison.json"
    lines.extend(["", "## Combined replay parity", ""])
    if replay_file.is_file():
        payload = json.loads(replay_file.read_text(encoding="utf-8"))
        counts = payload["counts"]
        defects = sum(counts.get(k, 0) for k in DEFECT_CLASSIFICATIONS)
        parity = "exact" if defects == 0 else "BROKEN"
        if defects:
            failures.append("replay-parity")
        lines.append(f"Replaying the combined reference captures through both builds: **{parity}** "
                     f"({', '.join(f'{k}={v}' for k, v in counts.items())})")
    else:
        failures.append("replay-parity-missing")
        lines.append("Replay parity phase did not produce a comparison — treat this run as incomplete.")

    lines.extend(["", "## Outcome", ""])
    if failures:
        lines.append(f"**FAILED**: {', '.join(failures)}")
    else:
        lines.append("**PASSED**: every scenario matched, passed its gates, and was confirmed by the operator.")
    (output_dir / "report.md").write_text("\n".join(lines) + "\n", encoding="utf-8")
    print((output_dir / "report.md").read_text(encoding="utf-8"))
    return 1 if failures else 0


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    sub = parser.add_subparsers(dest="command", required=True)
    sub.add_parser("list")
    for name in ("seconds", "skippable", "brief"):
        command = sub.add_parser(name)
        command.add_argument("scenario", choices=sorted(BY_ID))
    check = sub.add_parser("check")
    check.add_argument("scenario", choices=sorted(BY_ID))
    check.add_argument("results", type=Path)
    verdict = sub.add_parser("verdict")
    verdict.add_argument("--output-dir", type=Path, required=True)
    verdict.add_argument("--scenario", choices=sorted(BY_ID), required=True)
    verdict.add_argument("--verdict", choices=("same", "different", "skipped"), required=True)
    verdict.add_argument("--notes", default="")
    report = sub.add_parser("report")
    report.add_argument("--output-dir", type=Path, required=True)
    args = parser.parse_args()

    if args.command == "list":
        print("\n".join(scenario["id"] for scenario in SCENARIOS))
    elif args.command == "seconds":
        print(BY_ID[args.scenario]["seconds"])
    elif args.command == "skippable":
        raise SystemExit(0 if BY_ID[args.scenario].get("skippable") else 1)
    elif args.command == "brief":
        cmd_brief(args.scenario)
    elif args.command == "check":
        raise SystemExit(cmd_check(args.scenario, args.results))
    elif args.command == "verdict":
        cmd_verdict(args.output_dir, args.scenario, args.verdict, args.notes)
    elif args.command == "report":
        raise SystemExit(cmd_report(args.output_dir))


if __name__ == "__main__":
    main()
