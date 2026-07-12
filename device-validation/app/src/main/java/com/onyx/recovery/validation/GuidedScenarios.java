package com.onyx.recovery.validation;

/**
 * Scenario identifiers for the guided operator protocol. The host runner
 * (run-comparison.sh --suite guided) passes one of these as the "scenario"
 * intent extra; {@link PenHarness} applies the matching reversible display
 * and reader configuration for the duration of the capture. Instructions,
 * gates, and operator verdicts live host-side in guided_scenarios.py.
 */
final class GuidedScenarios {
    static final String DU_REFRESH = "du-refresh";
    static final String GU_REFRESH = "gu-refresh";
    static final String GC_REFRESH = "gc-refresh";
    static final String SCRIBBLE_MODE = "scribble-mode";
    static final String REGION_REFRESH = "region-refresh";
    static final String GHOSTING_CLEANUP = "ghosting-cleanup";
    static final String PRESSURE = "pressure";
    static final String TILT = "tilt";
    static final String SIDE_BUTTON_ERASE = "side-button-erase";
    static final String REAR_ERASER = "rear-eraser";
    static final String PAUSE_RESUME = "pause-resume";
    static final String RESTART = "restart";

    private GuidedScenarios() {}
}
