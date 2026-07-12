"""Pin API-surface subprocess failure and classpath behavior."""

import os
import subprocess
import sys
import unittest
from pathlib import Path
from unittest import mock

sys.path.insert(0, str(Path(__file__).resolve().parent.parent))

import api_surface


class JavapTest(unittest.TestCase):
    @mock.patch.object(api_surface.subprocess, "run")
    def test_nonzero_exit_fails_even_when_all_classes_parse(self, run):
        run.return_value = subprocess.CompletedProcess(
            ["javap"], 1, "public class Example {\n}\n", "simulated failure"
        )

        with self.assertRaisesRegex(SystemExit, "exit code 1.*simulated failure"):
            api_surface.javap(Path("example.jar"), ["Example"], [])

    @mock.patch.object(api_surface.subprocess, "run")
    def test_classpath_uses_platform_separator(self, run):
        run.return_value = subprocess.CompletedProcess(
            ["javap"], 0, "public class Example {\n}\n", ""
        )

        api_surface.javap(
            Path("example.jar"), ["Example"], [Path("dependency.jar")]
        )

        command = run.call_args.args[0]
        classpath = command[command.index("-classpath") + 1]
        self.assertEqual(
            classpath,
            os.pathsep.join(("example.jar", "dependency.jar")),
        )

    @mock.patch.object(api_surface.subprocess, "run")
    def test_missing_disassembly_still_fails_on_zero_exit(self, run):
        run.return_value = subprocess.CompletedProcess(["javap"], 0, "", "")

        with self.assertRaisesRegex(SystemExit, "produced no output.*Example"):
            api_surface.javap(Path("example.jar"), ["Example"], [])


if __name__ == "__main__":
    unittest.main()
