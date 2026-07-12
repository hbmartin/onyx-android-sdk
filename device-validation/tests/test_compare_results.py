"""Pin the comparison taxonomy: equivalent() tolerance, keyed() exclusions,
classify() buckets, pen_summary() state grammar, and the end-to-end payload
(replay health, operator actions, --require-clean).

Run host-side, no device needed:  python3 -m unittest discover tests
"""

import io
import json
import subprocess
import sys
import tempfile
import unittest
from contextlib import redirect_stdout
from pathlib import Path
from unittest import mock

sys.path.insert(0, str(Path(__file__).resolve().parent.parent))

import compare_results
import guided_scenarios


def record(suite="pen", case="probe", phase="observation", status="match",
           output=None, sequence=0):
    return {
        "sequence": sequence, "variant": "test", "suite": suite, "caseId": case,
        "phase": phase, "status": status, "monotonicNs": 1, "wallClockMs": 2,
        "thread": "main", "inputs": None, "output": output,
    }


def raw_event(state, pressure=500, tilt_x=0, tilt_y=0, erasing=False, shortcut_erasing=False):
    return record(case="raw_jni", phase="event", output={
        "x": 1.0, "y": 2.0, "pressure": pressure, "tiltX": tilt_x, "tiltY": tilt_y,
        "erasing": erasing, "shortcutDrawing": False, "shortcutErasing": shortcut_erasing,
        "state": state, "timestamp": 1000,
    })


class EquivalentTest(unittest.TestCase):
    def test_floats_within_tolerance_match(self):
        self.assertTrue(compare_results.equivalent(1.0, 1.0009))
        self.assertFalse(compare_results.equivalent(1.0, 1.002))

    def test_bool_never_equals_number(self):
        self.assertFalse(compare_results.equivalent(True, 1))
        self.assertFalse(compare_results.equivalent(0, False))
        self.assertTrue(compare_results.equivalent(True, True))

    def test_volatile_keys_ignored_at_any_depth(self):
        left = {"output": {"value": 1, "monotonicNs": 111}, "wallClockMs": 1}
        right = {"output": {"value": 1, "monotonicNs": 999}, "wallClockMs": 2}
        self.assertTrue(compare_results.equivalent(left, right))


class KeyedTest(unittest.TestCase):
    def test_occurrence_indexing(self):
        records = [record(case="a"), record(case="a"), record(case="b")]
        keys = sorted(compare_results.keyed(records, live=False))
        self.assertEqual(keys, [("pen", "a", "observation", 0),
                                ("pen", "a", "observation", 1),
                                ("pen", "b", "observation", 0)])

    def test_live_excludes_events_and_operator_actions(self):
        records = [raw_event(0), record(case="mode", output="limit"),
                   record(case="live_started", output=True)]
        keys = compare_results.keyed(records, live=True)
        self.assertEqual(list(keys), [("pen", "live_started", "observation", 0)])

    def test_metrics_phase_never_compared(self):
        records = [record(case="replay_metrics", phase="metrics", output={"p95": 123})]
        self.assertEqual(compare_results.keyed(records, live=False), {})
        self.assertEqual(compare_results.keyed(records, live=True), {})


class ClassifyTest(unittest.TestCase):
    def test_one_sided_records_are_defects_not_harness_errors(self):
        # An extra or missing callback is precisely the divergence being
        # hunted; it must never be filed under harness_error.
        self.assertEqual(compare_results.classify(record(), None, False), "missing_in_recovered")
        self.assertEqual(compare_results.classify(None, record(), False), "extra_in_recovered")

    def test_symmetric_denial_is_benign(self):
        left = record(status="permission_denied")
        right = record(status="permission_denied")
        self.assertEqual(compare_results.classify(left, right, False), "permission_denied")

    def test_asymmetric_denial_is_a_defect(self):
        ok = record(status="match")
        denied = record(status="permission_denied")
        self.assertEqual(compare_results.classify(ok, denied, False), "recovery_defect")
        self.assertEqual(compare_results.classify(denied, ok, False), "recovery_defect")

    def test_asymmetric_unsupported_is_a_defect(self):
        ok = record(status="match")
        unsupported = record(status="unsupported_hardware")
        self.assertEqual(compare_results.classify(ok, unsupported, False), "recovery_defect")
        self.assertEqual(compare_results.classify(unsupported, unsupported, False),
                         "unsupported_hardware")

    def test_mixed_benign_statuses_are_a_defect(self):
        denied = record(status="permission_denied")
        unsupported = record(status="unsupported_hardware")
        self.assertEqual(compare_results.classify(denied, unsupported, False), "recovery_defect")

    def test_harness_error_still_wins_when_reported(self):
        broken = record(status="harness_error")
        self.assertEqual(compare_results.classify(broken, record(), True), "harness_error")

    def test_inventory_differences_are_platform_variation(self):
        left = record(suite="inventory")
        right = record(suite="inventory")
        self.assertEqual(compare_results.classify(left, right, False), "platform_variation")

    def test_plain_mismatch_is_a_defect(self):
        self.assertEqual(compare_results.classify(record(), record(), False), "recovery_defect")
        self.assertEqual(compare_results.classify(record(), record(), True), "match")


class PenSummaryTest(unittest.TestCase):
    def test_valid_stroke_has_no_errors(self):
        records = [raw_event(0), raw_event(1), raw_event(2)]
        summary = compare_results.pen_summary(records)
        self.assertEqual(summary["eventCount"], 3)
        self.assertEqual(summary["errors"], [])

    def test_grammar_violations_detected(self):
        self.assertIn("move_without_down",
                      compare_results.pen_summary([raw_event(1)])["errors"])
        self.assertIn("down_while_active",
                      compare_results.pen_summary([raw_event(0), raw_event(0)])["errors"])
        self.assertIn("unterminated_stroke",
                      compare_results.pen_summary([raw_event(0), raw_event(1)])["errors"])
        self.assertIn("unknown_state",
                      compare_results.pen_summary([raw_event(9), raw_event(9)])["errors"])
        self.assertIn("pressure_out_of_range",
                      compare_results.pen_summary([raw_event(0, pressure=5000), raw_event(2)])["errors"])


class MainPayloadTest(unittest.TestCase):
    def run_main(self, reference, recovered, *flags):
        out = Path(tempfile.mkdtemp())
        ref = out / "reference.jsonl"
        rec = out / "recovered.jsonl"
        ref.write_text("\n".join(json.dumps(r) for r in reference) + "\n", encoding="utf-8")
        rec.write_text("\n".join(json.dumps(r) for r in recovered) + "\n", encoding="utf-8")
        completed = subprocess.run(
            [sys.executable, str(Path(__file__).resolve().parent.parent / "compare_results.py"),
             str(ref), str(rec), "--output", str(out), *flags],
            capture_output=True, text=True)
        payload = json.loads((out / "comparison.json").read_text(encoding="utf-8")) \
            if (out / "comparison.json").is_file() else None
        return completed, payload

    def test_unhealthy_replay_is_a_defect_even_when_symmetric(self):
        unhealthy = record(case="replay_health",
                           output={"healthy": False, "delivered": 7, "rawRecorded": 6,
                                   "dropped": 1, "semanticCallbacks": 3})
        completed, payload = self.run_main([unhealthy], [unhealthy], "--require-clean")
        assert payload is not None
        self.assertEqual(payload["replayHealth"]["classification"], "recovery_defect")
        self.assertNotEqual(completed.returncode, 0)

    def test_healthy_replay_matches(self):
        healthy = record(case="replay_health",
                         output={"healthy": True, "delivered": 7, "rawRecorded": 7,
                                 "dropped": 0, "semanticCallbacks": 9})
        completed, payload = self.run_main([healthy], [healthy], "--require-clean")
        assert payload is not None
        self.assertEqual(payload["replayHealth"]["classification"], "match")
        self.assertEqual(completed.returncode, 0, completed.stderr)

    def test_null_replay_health_is_a_defect_instead_of_crashing(self):
        invalid = record(case="replay_health", status="harness_error", output=None)
        completed, payload = self.run_main([invalid], [invalid], "--require-clean")
        assert payload is not None
        self.assertEqual(payload["replayHealth"]["classification"], "recovery_defect")
        self.assertNotEqual(completed.returncode, 0)
        self.assertNotIn("AttributeError", completed.stderr)

    def test_missing_record_fails_require_clean(self):
        shared = record(case="shared", output=1)
        only_ref = record(case="only_ref", output=2)
        completed, payload = self.run_main([shared, only_ref], [shared], "--require-clean")
        assert payload is not None
        self.assertEqual(payload["counts"].get("missing_in_recovered"), 1)
        self.assertNotEqual(completed.returncode, 0)

    def test_operator_actions_bucketed_not_compared(self):
        # Live captures are two different human performances: a button press
        # on one side must not surface as a comparison defect.
        stroke = [raw_event(0), raw_event(1), raw_event(2)]
        shared = record(case="live_started", output=True)
        mode = record(case="mode", output="limit")
        completed, payload = self.run_main(stroke + [shared, mode], stroke + [shared],
                                           "--live", "--require-clean")
        assert payload is not None
        self.assertEqual(completed.returncode, 0, completed.stderr)
        self.assertEqual(payload["operatorActions"], {"reference": ["limit"], "recovered": []})


class GuidedGatesTest(unittest.TestCase):
    def check(self, scenario_id, records):
        path = Path(tempfile.mkdtemp()) / "results.jsonl"
        path.write_text("\n".join(json.dumps(r) for r in records) + "\n", encoding="utf-8")
        return guided_scenarios.gate_failures(guided_scenarios.BY_ID[scenario_id], path)

    def test_empty_capture_fails(self):
        failures = self.check("du-refresh", [record(case="live_started", output=True)])
        self.assertTrue(any("no stylus events" in f for f in failures))

    def test_valid_capture_passes(self):
        self.assertEqual(self.check("du-refresh", [raw_event(0), raw_event(1), raw_event(2)]), [])

    def test_pressure_spread_gate(self):
        flat = [raw_event(0, pressure=1000), raw_event(1, pressure=1100), raw_event(2, pressure=1000)]
        spread = [raw_event(0, pressure=200), raw_event(1, pressure=3900), raw_event(2, pressure=200)]
        self.assertTrue(any("pressure spread" in f for f in self.check("pressure", flat)))
        self.assertEqual(self.check("pressure", spread), [])

    def test_tilt_gate(self):
        still = [raw_event(0), raw_event(1), raw_event(2)]
        rolled = [raw_event(0, tilt_x=-20), raw_event(1, tilt_x=25), raw_event(2)]
        self.assertTrue(any("tilt" in f for f in self.check("tilt", still)))
        self.assertEqual(self.check("tilt", rolled), [])

    def test_erase_gate_accepts_either_erase_flag(self):
        none = [raw_event(0), raw_event(2)]
        side_button = [raw_event(0), raw_event(1, shortcut_erasing=True), raw_event(2)]
        self.assertTrue(any("erase" in f for f in self.check("side-button-erase", none)))
        self.assertEqual(self.check("side-button-erase", side_button), [])

    def test_scenario_action_count_gate(self):
        base = [raw_event(0), raw_event(2)]
        actions = base + [record(case="scenario_action", output=True),
                          record(case="scenario_action", output=True)]
        self.assertTrue(any("scenario action" in f for f in self.check("pause-resume", base)))
        self.assertEqual(self.check("pause-resume", actions), [])


class GuidedReportTest(unittest.TestCase):
    def run_report(self, scenario, verdict, comparison=...):
        output = Path(tempfile.mkdtemp())
        (output / "operator-verdicts.jsonl").write_text(
            json.dumps({"scenario": scenario["id"], "verdict": verdict, "notes": ""}) + "\n",
            encoding="utf-8",
        )
        if comparison is not ...:
            comparison_dir = output / f"{scenario['id']}-comparison"
            comparison_dir.mkdir()
            contents = comparison if isinstance(comparison, str) else json.dumps(comparison)
            (comparison_dir / "comparison.json").write_text(contents, encoding="utf-8")
        replay_dir = output / "replay-comparison"
        replay_dir.mkdir()
        (replay_dir / "comparison.json").write_text(
            json.dumps({"counts": {"match": 1}}), encoding="utf-8"
        )
        with mock.patch.object(guided_scenarios, "SCENARIOS", [scenario]):
            with redirect_stdout(io.StringIO()):
                status = guided_scenarios.cmd_report(output)
        return status, (output / "report.md").read_text(encoding="utf-8")

    def test_required_skip_fails_but_optional_skip_passes(self):
        required_status, _ = self.run_report({"id": "required"}, "skipped")
        optional_status, _ = self.run_report(
            {"id": "optional", "skippable": True}, "skipped"
        )
        self.assertEqual(required_status, 1)
        self.assertEqual(optional_status, 0)

    def test_missing_comparison_fails_same_verdict(self):
        status, report = self.run_report({"id": "required"}, "same")
        self.assertEqual(status, 1)
        self.assertIn("comparison: not compared", report)

    def test_malformed_comparison_fails_same_verdict(self):
        status, report = self.run_report({"id": "required"}, "same", "{not-json")
        self.assertEqual(status, 1)
        self.assertIn("invalid comparison", report)

    def test_recovery_defect_comparison_fails_same_verdict(self):
        status, _ = self.run_report(
            {"id": "required"},
            "same",
            {"counts": {"match": 1, "recovery_defect": 1}},
        )
        self.assertEqual(status, 1)


if __name__ == "__main__":
    unittest.main()
