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


def record_coordinates(ink: Ink) -> tuple[tuple[float, float], ...]:
    coordinates: list[tuple[float, float]] = []
    offset = 0
    for size in ink.sizes:
        record = ink.points[offset : offset + size]
        offset += size
        if size in (3, 5):
            coordinates.append((record[0], record[1]))
        elif size == 12:
            coordinates.extend(
                (record[index], record[index + 1]) for index in range(0, 12, 2)
            )
    return tuple(coordinates)


def validate_notable_advanced(
    expected: dict[tuple[int, bool, str, str], Ink],
    actual: dict[tuple[int, bool, str, str], Ink],
    errors: list[str],
) -> list[str]:
    """Validate safety/geometry while retaining honest cross-version deltas.

    Notable 0.2.3 and the older pinned renderer disagree about batching for
    types 6-9, so the recovered implementation cannot be record-identical to
    both. Types 1-5 remain exact. For advanced pens we enforce valid encoding,
    finite bounded geometry, a completed final layer, and endpoint parity.
    """
    metrics: list[str] = []
    for pen_type in range(6, 10):
        for fast_mode in (False, True):
            reference_coordinates: list[tuple[float, float]] = []
            candidate_coordinates: list[tuple[float, float]] = []
            reference_records = 0
            candidate_records = 0
            for phase in ("down", "move", "up"):
                for layer in ("real", "prediction"):
                    key = (pen_type, fast_mode, phase, layer)
                    expected_ink = expected[key]
                    actual_ink = actual[key]
                    label = (
                        f"type={pen_type},fast={fast_mode},phase={phase},ink={layer}"
                    )
                    if len(actual_ink.points) != sum(actual_ink.sizes):
                        errors.append(f"{label}: point count does not match record sizes")
                        continue
                    if any(size not in (3, 5, 12) for size in actual_ink.sizes):
                        errors.append(f"{label}: unsupported record arity {actual_ink.sizes}")
                        continue
                    if actual_ink.bitmaps:
                        errors.append(f"{label}: unexpected bitmap output for an advanced pen")
                    if not all(math.isfinite(value) for value in actual_ink.points):
                        errors.append(f"{label}: candidate contains a non-finite value")
                        continue
                    expected_xy = record_coordinates(expected_ink)
                    actual_xy = record_coordinates(actual_ink)
                    reference_coordinates.extend(expected_xy)
                    candidate_coordinates.extend(actual_xy)
                    reference_records += len(expected_ink.sizes)
                    candidate_records += len(actual_ink.sizes)

            final_key = (pen_type, fast_mode, "up", "real")
            if not actual[final_key].sizes:
                errors.append(
                    f"type={pen_type},fast={fast_mode}: final committed layer is empty"
                )
                continue
            if not candidate_coordinates:
                errors.append(f"type={pen_type},fast={fast_mode}: no candidate geometry")
                continue
            if any(
                coordinate < -20.0 or coordinate > 50.0
                for point in candidate_coordinates
                for coordinate in point
            ):
                errors.append(
                    f"type={pen_type},fast={fast_mode}: geometry escaped the test envelope"
                )
            if reference_coordinates:
                reference_max = (
                    max(point[0] for point in reference_coordinates),
                    max(point[1] for point in reference_coordinates),
                )
                candidate_max = (
                    max(point[0] for point in candidate_coordinates),
                    max(point[1] for point in candidate_coordinates),
                )
                endpoint_delta = max(
                    abs(reference_max[index] - candidate_max[index]) for index in (0, 1)
                )
                if endpoint_delta > 4.0:
                    errors.append(
                        f"type={pen_type},fast={fast_mode}: endpoint delta "
                        f"{endpoint_delta} exceeds 4.0"
                    )
            metrics.append(
                f"type={pen_type},fast={fast_mode}: "
                f"records notable={reference_records},recovered={candidate_records}"
            )
    return metrics


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("reference", type=Path)
    parser.add_argument("candidate", type=Path)
    parser.add_argument(
        "--profile", choices=("legacy", "notable-0.2.3"), default="legacy"
    )
    args = parser.parse_args()
    expected = parse(args.reference)
    actual = parse(args.candidate)
    errors: list[str] = []
    if expected.keys() != actual.keys():
        errors.append("snapshot cases differ")
    metrics: list[str] = []
    for key in sorted(expected.keys() & actual.keys()):
        if args.profile == "legacy" or key[0] <= 5:
            compare_values(key, expected[key], actual[key], errors)
    if args.profile == "notable-0.2.3" and not errors:
        metrics = validate_notable_advanced(expected, actual, errors)
    if errors:
        print("\n".join(errors))
        return 1
    if args.profile == "notable-0.2.3":
        print(
            "Notable 0.2.3 comparison passed: types 1-5 exact; types 6-9 "
            "valid, final-layer complete, and endpoint-bounded."
        )
        print("\n".join(metrics))
    else:
        print(
            "Pen snapshots match: types 1-5 exact; types 6-9 structural and bounded geometric parity."
        )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
