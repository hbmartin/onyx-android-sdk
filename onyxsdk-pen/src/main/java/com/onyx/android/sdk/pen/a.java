package com.onyx.android.sdk.pen;

import com.onyx.android.sdk.data.note.TouchPoint;
import java.util.function.Predicate;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/a.class */
public final /* synthetic */ class a implements Predicate {
    public static final a a = new a();

    private /* synthetic */ a() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((TouchPoint) obj).getPressure() > com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY;
    }
}
