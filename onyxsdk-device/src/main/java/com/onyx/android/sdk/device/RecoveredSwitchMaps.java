package com.onyx.android.sdk.device;

import com.onyx.android.sdk.api.device.epd.EPDMode;
import com.onyx.android.sdk.api.device.epd.UpdateMode;
import com.onyx.android.sdk.api.device.epd.UpdatePolicy;
import com.onyx.android.sdk.api.device.epd.UpdateScheme;

/*
 * These top-level dollar names reproduce enum-switch classes present in the
 * original artifact. They are referenced by the recovered device classes.
 */
final class IMX6Device$a {
    static final int[] a = epdMode();
    static final int[] b = updateMode();
    static final int[] c = updateScheme();
    static final int[] d = updatePolicy();

    private static int[] epdMode() {
        int[] map = new int[EPDMode.values().length];
        map[EPDMode.FULL.ordinal()] = 1;
        map[EPDMode.AUTO.ordinal()] = 2;
        map[EPDMode.TEXT.ordinal()] = 3;
        map[EPDMode.AUTO_PART.ordinal()] = 4;
        return map;
    }

    private static int[] updateMode() {
        int[] map = new int[UpdateMode.values().length];
        map[UpdateMode.GU_FAST.ordinal()] = 1;
        map[UpdateMode.DU.ordinal()] = 2;
        map[UpdateMode.GU.ordinal()] = 3;
        map[UpdateMode.GC.ordinal()] = 4;
        map[UpdateMode.ANIMATION.ordinal()] = 5;
        map[UpdateMode.ANIMATION_QUALITY.ordinal()] = 6;
        map[UpdateMode.GC4.ordinal()] = 7;
        map[UpdateMode.REGAL.ordinal()] = 8;
        map[UpdateMode.REGAL_D.ordinal()] = 9;
        return map;
    }

    private static int[] updateScheme() {
        int[] map = new int[UpdateScheme.values().length];
        map[UpdateScheme.SNAPSHOT.ordinal()] = 1;
        map[UpdateScheme.QUEUE.ordinal()] = 2;
        map[UpdateScheme.QUEUE_AND_MERGE.ordinal()] = 3;
        return map;
    }

    private static int[] updatePolicy() {
        int[] map = new int[UpdatePolicy.values().length];
        map[UpdatePolicy.Automatic.ordinal()] = 1;
        map[UpdatePolicy.GUIntervally.ordinal()] = 2;
        return map;
    }
}

final class RK31XXDevice$a {
    static final int[] a = IMX6Device$a.a.clone();
    static final int[] b = IMX6Device$a.b.clone();
    static final int[] c = IMX6Device$a.c.clone();
    static final int[] d = IMX6Device$a.d.clone();
}

final class RK33XXDevice$a {
    static final int[] a = IMX6Device$a.a.clone();
    static final int[] b = IMX6Device$a.b.clone();
    static final int[] c = IMX6Device$a.c.clone();
    static final int[] d = IMX6Device$a.d.clone();
}

final class RK32XXDevice$a {
    static final int[] a = IMX6Device$a.a.clone();
    static final int[] b = updateMode();
    static final int[] c = IMX6Device$a.c.clone();
    static final int[] d = IMX6Device$a.d.clone();

    private static int[] updateMode() {
        int[] map = new int[UpdateMode.values().length];
        map[UpdateMode.GU_FAST.ordinal()] = 1;
        map[UpdateMode.DU.ordinal()] = 2;
        map[UpdateMode.DU_QUALITY.ordinal()] = 3;
        map[UpdateMode.GU.ordinal()] = 4;
        map[UpdateMode.GC.ordinal()] = 5;
        map[UpdateMode.DEEP_GC.ordinal()] = 6;
        map[UpdateMode.ANIMATION.ordinal()] = 7;
        map[UpdateMode.ANIMATION_QUALITY.ordinal()] = 8;
        map[UpdateMode.ANIMATION_MONO.ordinal()] = 9;
        map[UpdateMode.ANIMATION_X.ordinal()] = 10;
        map[UpdateMode.GC4.ordinal()] = 11;
        map[UpdateMode.REGAL.ordinal()] = 12;
        map[UpdateMode.REGAL_D.ordinal()] = 13;
        map[UpdateMode.HAND_WRITING_REPAINT_MODE.ordinal()] = 14;
        return map;
    }
}

final class SDMDevice$a {
    static final int[] a = IMX6Device$a.a.clone();
    static final int[] b = updateMode();
    static final int[] c = IMX6Device$a.c.clone();
    static final int[] d = IMX6Device$a.d.clone();

    private static int[] updateMode() {
        int[] map = new int[UpdateMode.values().length];
        map[UpdateMode.GU_FAST.ordinal()] = 1;
        map[UpdateMode.DU.ordinal()] = 2;
        map[UpdateMode.DU4.ordinal()] = 3;
        map[UpdateMode.DU_QUALITY.ordinal()] = 4;
        map[UpdateMode.GU.ordinal()] = 5;
        map[UpdateMode.GC.ordinal()] = 6;
        map[UpdateMode.GCC.ordinal()] = 7;
        map[UpdateMode.DEEP_GC.ordinal()] = 8;
        map[UpdateMode.ANIMATION.ordinal()] = 9;
        map[UpdateMode.ANIMATION_QUALITY.ordinal()] = 10;
        map[UpdateMode.ANIMATION_MONO.ordinal()] = 11;
        map[UpdateMode.ANIMATION_X.ordinal()] = 12;
        map[UpdateMode.GC4.ordinal()] = 13;
        map[UpdateMode.REGAL.ordinal()] = 14;
        map[UpdateMode.REGAL_D.ordinal()] = 15;
        map[UpdateMode.REGAL_PLUS.ordinal()] = 16;
        map[UpdateMode.HAND_WRITING_REPAINT_MODE.ordinal()] = 17;
        return map;
    }
}
