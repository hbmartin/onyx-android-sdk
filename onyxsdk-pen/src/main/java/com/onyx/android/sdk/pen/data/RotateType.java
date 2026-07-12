/*
 * Decompiled with CFR 0.152.
 */
package com.onyx.android.sdk.pen.data;

public final class RotateType
extends Enum<RotateType> {
    public static final /* enum */ RotateType FREEDOM;
    public static final /* enum */ RotateType CW_90;
    public static final /* enum */ RotateType CWW_90;
    private static final /* synthetic */ RotateType[] b;
    private float a;

    public static RotateType[] values() {
        return (RotateType[])b.clone();
    }

    public static RotateType valueOf(String name) {
        String string;
        return Enum.valueOf(RotateType.class, string);
    }

    /*
     * WARNING - void declaration
     */
    private RotateType(float angle) {
        void var3_3;
        this.a = var3_3;
    }

    static {
        RotateType rotateType;
        RotateType rotateType2;
        RotateType rotateType3;
        RotateType rotateType4 = rotateType3;
        FREEDOM = new RotateType(-32768.0f);
        RotateType rotateType5 = rotateType2;
        CW_90 = new RotateType(90.0f);
        RotateType rotateType6 = rotateType;
        CWW_90 = new RotateType(-90.0f);
        b = new RotateType[]{rotateType4, rotateType5, rotateType6};
    }

    public float getAngle() {
        return this.a;
    }
}

