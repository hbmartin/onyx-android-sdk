/*
 * Decompiled with CFR 0.152.
 */
package com.onyx.android.sdk.pen.data;

public final class MirrorType
extends Enum<MirrorType> {
    public static final /* enum */ MirrorType XAxisMirror;
    public static final /* enum */ MirrorType YAxisMirror;
    private static final /* synthetic */ MirrorType[] a;

    public static MirrorType[] values() {
        return (MirrorType[])a.clone();
    }

    public static MirrorType valueOf(String name) {
        String string;
        return Enum.valueOf(MirrorType.class, string);
    }

    static {
        MirrorType mirrorType;
        MirrorType mirrorType2;
        MirrorType mirrorType3 = mirrorType2;
        XAxisMirror = new MirrorType();
        MirrorType mirrorType4 = mirrorType;
        YAxisMirror = new MirrorType();
        a = new MirrorType[]{mirrorType3, mirrorType4};
    }
}

