package com.onyx.android.sdk.pen;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/NeoMarkerPenV2.class */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoMarkerPenV2;", "Lcom/onyx/android/sdk/pen/NeoSinglePathResultPen;", "penHandle", "", "(J)V", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class NeoMarkerPenV2 extends NeoSinglePathResultPen {
    public NeoMarkerPenV2() {
        this(0L, 1, null);
    }

    public NeoMarkerPenV2(long j) {
        super(j);
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x000a: CONSTRUCTOR 
      (wrap long:?: TERNARY null = ((wrap int:0x0002: ARITH (r7v0 int) & (1 int) A[WRAPPED]) != (0 int)) ? (0 long) : (r5v0 long))
     A[MD:(long):void (m)] call: com.onyx.android.sdk.pen.NeoMarkerPenV2.<init>(long):void type: THIS */
    public /* synthetic */ NeoMarkerPenV2(long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0L : j);
    }
}

