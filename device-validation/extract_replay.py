#!/usr/bin/env python3
"""Extract raw JNI callback records from a reference capture."""

import argparse
import json
from pathlib import Path

parser = argparse.ArgumentParser()
parser.add_argument("source", type=Path)
parser.add_argument("destination", type=Path)
args = parser.parse_args()

count = 0
with args.destination.open("w", encoding="utf-8") as output:
    for line in args.source.read_text(encoding="utf-8").splitlines():
        if not line.strip():
            continue
        record = json.loads(line)
        if record.get("caseId") == "raw_jni":
            output.write(json.dumps(record, separators=(",", ":")) + "\n")
            count += 1
print(count)
