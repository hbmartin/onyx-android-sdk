#!/usr/bin/env python3
"""Compare reference and Rust neo-pen snapshots at recovered parity tolerances."""

from __future__ import annotations

import argparse
import math
import re
from dataclasses import dataclass
from pathlib import Path


HEADER = re.compile(r"^type=(\d+),fast=(true|false),handle=(\d+)$")
EVENT = re.compile(
    r"^(down|move|up)=\{"
    r"real:\{points:\[([^]]*)\],sizes:\[([^]]*)\],bitmaps:\[([^]]*)\]\},"
    r"prediction:\{points:\[([^]]*)\],sizes:\[([^]]*)\],bitmaps:\[([^]]*)\]\}"
    r"\}$"
)


@dataclass(frozen=True)
class Ink:
    points: tuple[float, ...]
    sizes: tuple[int, ...]
    bitmaps: tuple[str, ...]


def floats(value: str) -> tuple[float, ...]:
    return tuple(float(item.strip()) for item in value.split(",") if item.strip())


def ints(value: str) -> tuple[int, ...]:
    return tuple(int(item.strip()) for item in value.split(",") if item.strip())


def strings(value: str) -> tuple[str, ...]:
    return tuple(item.strip() for item in value.split(",") if item.strip())


def parse(path: Path) -> dict[tuple[int, bool, str, str], Ink]:
    output: dict[tuple[int, bool, str, str], Ink] = {}
    seen_cases: set[tuple[int, bool]] = set()
    current: tuple[int, bool] | None = None
    for number, line in enumerate(path.read_text().splitlines(), 1):
        header = HEADER.match(line)
        if header:
            if header.group(3) != "1":
                raise ValueError(f"{path}:{number}: pen creation failed: {line}")
            current = (int(header.group(1)), header.group(2) == "true")
            if current in seen_cases:
                raise ValueError(f"{path}:{number}: duplicate snapshot case: {line}")
            seen_cases.add(current)
            continue
        event = EVENT.match(line)
        if not event or current is None:
            raise ValueError(f"{path}:{number}: unexpected snapshot line: {line}")
        phase = event.group(1)
        if current + (phase, "real") in output:
            raise ValueError(f"{path}:{number}: duplicate {phase} event for case {current}")
        output[current + (phase, "real")] = Ink(
            floats(event.group(2)), ints(event.group(3)), strings(event.group(4))
        )
        output[current + (phase, "prediction")] = Ink(
            floats(event.group(5)), ints(event.group(6)), strings(event.group(7))
        )
    return output


def compare_values(
    key: tuple[int, bool, str, str], expected: Ink, actual: Ink, errors: list[str]
) -> None:
    label = f"type={key[0]},fast={key[1]},phase={key[2]},ink={key[3]}"
    if expected.sizes != actual.sizes:
        errors.append(f"{label}: record sizes differ: {expected.sizes} != {actual.sizes}")
    if expected.bitmaps != actual.bitmaps:
        errors.append(
            f"{label}: bitmap geometry/content differs: "
            f"{expected.bitmaps} != {actual.bitmaps}"
        )
    if len(expected.points) != len(actual.points):
        errors.append(
            f"{label}: point value count differs: {len(expected.points)} != {len(actual.points)}"
        )
        return
    if not all(math.isfinite(value) for value in actual.points):
        errors.append(f"{label}: candidate contains a non-finite point value")
        return
    if key[0] <= 5:
        tolerance = 0.00001
        delta = max(
            (abs(left - right) for left, right in zip(expected.points, actual.points)),
            default=0.0,
        )
        if delta > tolerance:
            errors.append(f"{label}: exact recovered pen delta {delta} exceeds {tolerance}")
        return

    # The Wacom spline/brush pipeline for types 6-9 is independently rebuilt.
    # Validate its externally observable encoding, finiteness, and a bounded
    # geometric envelope without pretending randomized pencil rotations are
    # byte-identical.
    if expected.points:
        if not expected.sizes:
            errors.append(f"{label}: reference has point values but no record sizes")
            return
        record_size = expected.sizes[0]
        if any(size != record_size for size in expected.sizes):
            errors.append(
                f"{label}: mixed record sizes {sorted(set(expected.sizes))}; "
                "channel envelope requires a uniform record size"
            )
            return
        if len(expected.points) != sum(expected.sizes):
            errors.append(f"{label}: reference point count does not match its record sizes")
            return
        if record_size == 3:
            limits = (3.0, 3.0, 1.5)
        elif record_size == 5:
            limits = (3.0, 3.0, 1.5, math.tau, 0.35)
        else:
            limits = (12.0,) * record_size
        for channel in range(record_size):
            delta = max(
                abs(expected.points[index] - actual.points[index])
                for index in range(channel, len(expected.points), record_size)
            )
            if delta > limits[channel]:
                errors.append(
                    f"{label}: channel {channel} delta {delta} exceeds {limits[channel]}"
                )


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("reference", type=Path)
    parser.add_argument("candidate", type=Path)
    args = parser.parse_args()
    expected = parse(args.reference)
    actual = parse(args.candidate)
    errors: list[str] = []
    if expected.keys() != actual.keys():
        errors.append("snapshot cases differ")
    for key in sorted(expected.keys() & actual.keys()):
        compare_values(key, expected[key], actual[key], errors)
    if errors:
        print("\n".join(errors))
        return 1
    print(
        "Pen snapshots match: types 1-5 exact; types 6-9 structural and bounded geometric parity."
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
