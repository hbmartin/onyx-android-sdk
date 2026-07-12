package com.onyx.android.sdk.pen;

import android.graphics.Bitmap;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/PenInk.class */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0019\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0014"}, d2 = {"Lcom/onyx/android/sdk/pen/PenInk;", "", "points", "", "pointSizeArray", "", "bitmaps", "", "Landroid/graphics/Bitmap;", "([F[I[Landroid/graphics/Bitmap;)V", "getBitmaps", "()[Landroid/graphics/Bitmap;", "[Landroid/graphics/Bitmap;", "getPointSizeArray", "()[I", "getPoints", "()[F", "toString", "", "Companion", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class PenInk {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private final float[] a;

    @NotNull
    private final int[] b;

    @NotNull
    private final Bitmap[] c;

    /* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/PenInk$Companion.class */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J+\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0007¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lcom/onyx/android/sdk/pen/PenInk$Companion;", "", "()V", "create", "Lcom/onyx/android/sdk/pen/PenInk;", "points", "", "pointSizeArray", "", "bitmap", "", "Landroid/graphics/Bitmap;", "([F[I[Landroid/graphics/Bitmap;)Lcom/onyx/android/sdk/pen/PenInk;", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0001: CONSTRUCTOR  A[MD:():void (m)] call: com.onyx.android.sdk.pen.PenInk.Companion.<init>():void type: THIS */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        @NotNull
        public final PenInk create(@NotNull float[] points, @NotNull int[] pointSizeArray, @NotNull Bitmap[] bitmap) {
            Intrinsics.checkNotNullParameter(points, "points");
            Intrinsics.checkNotNullParameter(pointSizeArray, "pointSizeArray");
            Intrinsics.checkNotNullParameter(bitmap, "bitmap");
            return new PenInk(points, pointSizeArray, bitmap);
        }
    }

    public PenInk(@NotNull float[] fArr, @NotNull int[] iArr, @NotNull Bitmap[] bitmapArr) {
        Intrinsics.checkNotNullParameter(fArr, "points");
        Intrinsics.checkNotNullParameter(iArr, "pointSizeArray");
        Intrinsics.checkNotNullParameter(bitmapArr, "bitmaps");
        this.a = fArr;
        this.b = iArr;
        this.c = bitmapArr;
    }

    @JvmStatic
    @NotNull
    public static final PenInk create(@NotNull float[] fArr, @NotNull int[] iArr, @NotNull Bitmap[] bitmapArr) {
        return Companion.create(fArr, iArr, bitmapArr);
    }

    @NotNull
    public final Bitmap[] getBitmaps() {
        return this.c;
    }

    @NotNull
    public final int[] getPointSizeArray() {
        return this.b;
    }

    @NotNull
    public final float[] getPoints() {
        return this.a;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PenInk: ");
        sb.append(this.a.length);
        sb.append(" -> [");
        float[] fArr = this.a;
        if (!(fArr.length == 0)) {
            sb.append(ArraysKt.first(fArr));
        }
        int length = this.a.length;
        for (int i = 1; i < length; i++) {
            sb.append(", ");
            sb.append(this.a[i]);
        }
        sb.append("]");
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "builder.toString()");
        return string;
    }
}

