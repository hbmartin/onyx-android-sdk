"""Pin every promoted fixture: re-running compare_results.py on the fixture's
sanitized streams must reproduce exactly the classification counts committed
in its comparison.json. Catches both comparison-logic drift and hand-edited
fixture files. Host-side, no device needed.
"""

import json
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path

MODULE_ROOT = Path(__file__).resolve().parent.parent
FIXTURES = MODULE_ROOT / "fixtures"


def comparison_pairs(fixture: Path):
    """Yield (reference, recovered, pinned_comparison_dir, live, api_surface)."""
    for reference in sorted(fixture.glob("*-reference.jsonl")):
        stem = reference.name[:-len("-reference.jsonl")]
        recovered = fixture / f"{stem}-recovered.jsonl"
        pinned = fixture / f"{stem}-comparison"
        if recovered.is_file() and (pinned / "comparison.json").is_file():
            live = json.loads((pinned / "comparison.json").read_text())["liveComparison"]
            yield reference, recovered, pinned, live, None
    if (fixture / "reference.jsonl").is_file() and (fixture / "comparison.json").is_file():
        live = json.loads((fixture / "comparison.json").read_text())["liveComparison"]
        api = fixture / "api-surface.json"
        yield (fixture / "reference.jsonl", fixture / "recovered.jsonl", fixture, live,
               api if api.is_file() else None)
    if (fixture / "reference-replay.jsonl").is_file() \
            and (fixture / "replay-comparison" / "comparison.json").is_file():
        yield (fixture / "reference-replay.jsonl", fixture / "recovered-replay.jsonl",
               fixture / "replay-comparison", False, None)


class FixtureRegressionTest(unittest.TestCase):
    def test_every_fixture_comparison_reproduces(self):
        fixtures = sorted(path for path in FIXTURES.iterdir() if path.is_dir()) \
            if FIXTURES.is_dir() else []
        self.assertTrue(fixtures, "no fixtures are promoted; expected at least one")
        checked = 0
        for fixture in fixtures:
            for reference, recovered, pinned, live, api in comparison_pairs(fixture):
                pinned_counts = json.loads(
                    (pinned / "comparison.json").read_text(encoding="utf-8"))["counts"]
                out = Path(tempfile.mkdtemp())
                command = [sys.executable, str(MODULE_ROOT / "compare_results.py"),
                           str(reference), str(recovered), "--output", str(out)]
                if live:
                    command.append("--live")
                if api is not None:
                    command.extend(["--api-surface", str(api)])
                subprocess.run(command, check=True)
                fresh_counts = json.loads((out / "comparison.json").read_text(encoding="utf-8"))["counts"]
                self.assertEqual(pinned_counts, fresh_counts,
                                 f"{fixture.name}/{pinned.name} no longer reproduces")
                checked += 1
        self.assertGreater(checked, 0, "fixtures exist but none contained a comparison to pin")


if __name__ == "__main__":
    unittest.main()
