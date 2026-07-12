package com.onyx.android.sdk.pen;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import com.onyx.android.sdk.base.data.TouchPoint;
import com.onyx.android.sdk.base.lite.extension.BitmapKt;
import com.onyx.android.sdk.base.lite.extension.CollectionKt;
import com.onyx.android.sdk.base.lite.extension.PathKt;
import com.onyx.android.sdk.base.lite.utils.MathUtils;
import com.onyx.android.sdk.base.utils.Debug;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/NeoPencilPen.class */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0002J'\u0010\u000b\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\r\u0012\u0006\u0012\u0004\u0018\u00010\r0\f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0010¢\u0006\u0002\b\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoPencilPen;", "Lcom/onyx/android/sdk/pen/NeoNativePen;", "penHandle", "", "maskGenerator", "Lcom/onyx/android/sdk/pen/NeoPencilPen$Companion$BrushMaskGenerator;", "(JLcom/onyx/android/sdk/pen/NeoPencilPen$Companion$BrushMaskGenerator;)V", "buildFromInkArray", "Lcom/onyx/android/sdk/pen/PenBrushResult;", "ink", "Lcom/onyx/android/sdk/pen/PenInk;", "buildPenResult", "Lkotlin/Pair;", "Lcom/onyx/android/sdk/pen/PenResult;", "result", "Lcom/onyx/android/sdk/pen/NeoPenResult;", "buildPenResult$sdk_pen_release", "Companion", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class NeoPencilPen extends NeoNativePen {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static final ConcurrentHashMap<Companion.MaskKey, Companion.BitmapHolder> c = new ConcurrentHashMap<>();

    @NotNull
    private static final ConcurrentHashMap<Integer, Bitmap> d = new ConcurrentHashMap<>();

    @NotNull
    private final Companion.BrushMaskGenerator b;

    /* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/NeoPencilPen$Companion.class */
    @Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0003$%&B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\rJ\b\u0010\u000e\u001a\u00020\tH\u0002J\u0006\u0010\u000f\u001a\u00020\rJ \u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J.\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u000b2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bH\u0002J\u0010\u0010\u001d\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\bH\u0002J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010\f\u001a\u00020\rH\u0007J\b\u0010!\u001a\u00020\u0011H\u0002J\u0014\u0010\"\u001a\u00020\t*\u00020\t2\u0006\u0010\u001e\u001a\u00020#H\u0002R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoPencilPen$Companion;", "", "()V", "maskBitmaps", "Ljava/util/concurrent/ConcurrentHashMap;", "Lcom/onyx/android/sdk/pen/NeoPencilPen$Companion$MaskKey;", "Lcom/onyx/android/sdk/pen/NeoPencilPen$Companion$BitmapHolder;", "rotatedBitmaps", "", "Landroid/graphics/Bitmap;", "create", "Lcom/onyx/android/sdk/pen/NeoPencilPen;", "config", "Lcom/onyx/android/sdk/pen/NeoPenConfig;", "decodePencilOriginBitmap", "defaultPenConfig", "drawPath", "", "canvas", "Landroid/graphics/Canvas;", "path", "Landroid/graphics/Path;", "paint", "Landroid/graphics/Paint;", "drawPoints", "neoPen", "points", "", "Lcom/onyx/android/sdk/base/data/TouchPoint;", "getRotatedBitmap", "angle", "prepareBrushMask", "Lcom/onyx/android/sdk/pen/NeoPencilPen$Companion$BrushMaskGenerator;", "prepareRotatedBitmaps", "rotate", "", "BitmapHolder", "BrushMaskGenerator", "MaskKey", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {

        /* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/NeoPencilPen$Companion$BitmapHolder.class */
        @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoPencilPen$Companion$BitmapHolder;", "", "bitmap", "Landroid/graphics/Bitmap;", "halfWidth", "", "(Landroid/graphics/Bitmap;F)V", "getBitmap", "()Landroid/graphics/Bitmap;", "getHalfWidth", "()F", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
        public static final /* data */ class BitmapHolder {

            /* JADX INFO: renamed from: a, reason: from toString */
            @NotNull
            private final Bitmap bitmap;

            /* JADX INFO: renamed from: b, reason: from toString */
            private final float halfWidth;

            public BitmapHolder(@NotNull Bitmap bitmap, float f) {
                Intrinsics.checkNotNullParameter(bitmap, "bitmap");
                this.bitmap = bitmap;
                this.halfWidth = f;
            }

            public static /* synthetic */ BitmapHolder copy$default(BitmapHolder bitmapHolder, Bitmap bitmap, float f, int i, Object obj) {
                if ((i & 1) != 0) {
                    bitmap = bitmapHolder.bitmap;
                }
                if ((i & 2) != 0) {
                    f = bitmapHolder.halfWidth;
                }
                return bitmapHolder.copy(bitmap, f);
            }

            @NotNull
            public final Bitmap component1() {
                return this.bitmap;
            }

            public final float component2() {
                return this.halfWidth;
            }

            @NotNull
            public final BitmapHolder copy(@NotNull Bitmap bitmap, float halfWidth) {
                Intrinsics.checkNotNullParameter(bitmap, "bitmap");
                return new BitmapHolder(bitmap, halfWidth);
            }

            public boolean equals(@Nullable Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof BitmapHolder)) {
                    return false;
                }
                BitmapHolder bitmapHolder = (BitmapHolder) other;
                return Intrinsics.areEqual(this.bitmap, bitmapHolder.bitmap) && Intrinsics.areEqual(Float.valueOf(this.halfWidth), Float.valueOf(bitmapHolder.halfWidth));
            }

            @NotNull
            public final Bitmap getBitmap() {
                return this.bitmap;
            }

            public final float getHalfWidth() {
                return this.halfWidth;
            }

            public int hashCode() {
                return (this.bitmap.hashCode() * 31) + Float.hashCode(this.halfWidth);
            }

            @NotNull
            public String toString() {
                return "BitmapHolder(bitmap=" + this.bitmap + ", halfWidth=" + this.halfWidth + ')';
            }
        }

        /* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/NeoPencilPen$Companion$BrushMaskGenerator.class */
        @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoPencilPen$Companion$BrushMaskGenerator;", "", "config", "Lcom/onyx/android/sdk/pen/NeoPenConfig;", "(Lcom/onyx/android/sdk/pen/NeoPenConfig;)V", "getConfig", "()Lcom/onyx/android/sdk/pen/NeoPenConfig;", "getMaskBitmap", "Lcom/onyx/android/sdk/pen/NeoPencilPen$Companion$BitmapHolder;", "key", "Lcom/onyx/android/sdk/pen/NeoPencilPen$Companion$MaskKey;", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
        public static final class BrushMaskGenerator {

            @NotNull
            private final NeoPenConfig a;

            public BrushMaskGenerator(@NotNull NeoPenConfig neoPenConfig) {
                Intrinsics.checkNotNullParameter(neoPenConfig, "config");
                this.a = neoPenConfig;
            }

            /* JADX INFO: Access modifiers changed from: private */
            private static final BitmapHolder a(MaskKey maskKey, MaskKey maskKey2) {
                Intrinsics.checkNotNullParameter(maskKey, "$newKey");
                Intrinsics.checkNotNullParameter(maskKey2, "it");
                Bitmap bitmapCopy = BitmapKt.createScaledBitmap(NeoPencilPen.Companion.c(maskKey.getAngle()), maskKey.getSize(), maskKey.getSize()).copy(Bitmap.Config.ALPHA_8, true);
                Intrinsics.checkNotNullExpressionValue(bitmapCopy, "bitmap");
                return new BitmapHolder(bitmapCopy, bitmapCopy.getWidth() / 2.0f);
            }

            public static BitmapHolder b(MaskKey maskKey, MaskKey maskKey2) {
                return a(maskKey, maskKey2);
            }

            @NotNull
            public final NeoPenConfig getConfig() {
                return this.a;
            }

            @NotNull
            public final BitmapHolder getMaskBitmap(@NotNull MaskKey key) {
                Intrinsics.checkNotNullParameter(key, "key");
                BitmapHolder bitmapHolder = (BitmapHolder) NeoPencilPen.c.get(key);
                if (bitmapHolder != null) {
                    return bitmapHolder;
                }
                final MaskKey maskKeyCopy$default = MaskKey.copy$default(key, 0, 0, 3, null);
                Object objComputeIfAbsent = NeoPencilPen.c.computeIfAbsent(maskKeyCopy$default,
                        ignored -> BrushMaskGenerator.b(maskKeyCopy$default, ignored));
                Intrinsics.checkNotNullExpressionValue(objComputeIfAbsent, "maskBitmaps.computeIfAbs…h / 2f)\n                }");
                return (BitmapHolder) objComputeIfAbsent;
            }
        }

        /* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/NeoPencilPen$Companion$MaskKey.class */
        @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\t¨\u0006\u0015"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoPencilPen$Companion$MaskKey;", "", "size", "", "angle", "(II)V", "getAngle", "()I", "setAngle", "(I)V", "getSize", "setSize", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
        public static final /* data */ class MaskKey {

            /* JADX INFO: renamed from: a, reason: from toString */
            private int size;

            /* JADX INFO: renamed from: b, reason: from toString */
            private int angle;

            public MaskKey(int i, int i2) {
                this.size = i;
                this.angle = i2;
            }

            public static /* synthetic */ MaskKey copy$default(MaskKey maskKey, int i, int i2, int i3, Object obj) {
                if ((i3 & 1) != 0) {
                    i = maskKey.size;
                }
                if ((i3 & 2) != 0) {
                    i2 = maskKey.angle;
                }
                return maskKey.copy(i, i2);
            }

            public final int component1() {
                return this.size;
            }

            public final int component2() {
                return this.angle;
            }

            @NotNull
            public final MaskKey copy(int size, int angle) {
                return new MaskKey(size, angle);
            }

            public boolean equals(@Nullable Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof MaskKey)) {
                    return false;
                }
                MaskKey maskKey = (MaskKey) other;
                return this.size == maskKey.size && this.angle == maskKey.angle;
            }

            public final int getAngle() {
                return this.angle;
            }

            public final int getSize() {
                return this.size;
            }

            public int hashCode() {
                return (Integer.hashCode(this.size) * 31) + Integer.hashCode(this.angle);
            }

            public final void setAngle(int i) {
                this.angle = i;
            }

            public final void setSize(int i) {
                this.size = i;
            }

            @NotNull
            public String toString() {
                return "MaskKey(size=" + this.size + ", angle=" + this.angle + ')';
            }
        }

        private Companion() {
        }

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0001: CONSTRUCTOR  A[MD:():void (m)] call: com.onyx.android.sdk.pen.NeoPencilPen.Companion.<init>():void type: THIS */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final Bitmap a() {
            final int size = 256;
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ALPHA_8);
            int[] pixels = new int[size * size];
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    int hash = (x * 0x45d9f3b) ^ (y * 0x119de1f3) ^ ((x + y) * 0x27d4eb2d);
                    hash ^= hash >>> 16;
                    float nx = (x - 127.5f) / 127.5f;
                    float ny = (y - 127.5f) / 127.5f;
                    float radial = Math.max(0.0f, 1.0f - ((nx * nx) + (ny * ny)));
                    int alpha = Math.min(255, Math.max(0, (int) (radial * (96 + (hash & 127)))));
                    pixels[(y * size) + x] = alpha << 24;
                }
            }
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size);
            return bitmap;
        }

        private final void b(Canvas canvas, Paint paint, NeoPencilPen neoPencilPen, List<? extends TouchPoint> list) {
            if (list.size() < 2) {
                return;
            }
            Iterator<? extends TouchPoint> it = list.iterator();
            while (it.hasNext()) {
                ((TouchPoint) it.next()).pressure = 1.0f;
            }
            TouchPoint touchPoint = (TouchPoint) CollectionsKt.first(list);
            TouchPoint touchPoint2 = (TouchPoint) CollectionsKt.last(list);
            List<? extends TouchPoint> listSafelySubList = CollectionKt.safelySubList(list, 1, CollectionKt.getSize(list) - 1);
            ArrayList arrayList = new ArrayList();
            arrayList.add(neoPencilPen.onPenDown(touchPoint, true));
            if (!(!listSafelySubList.isEmpty())) {
                listSafelySubList = null;
            }
            if (listSafelySubList != null) {
                arrayList.add(neoPencilPen.onPenMove(listSafelySubList, null, true));
            }
            arrayList.add(neoPencilPen.onPenUp(touchPoint2, true));
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                PenResult penResult = (PenResult) ((Pair) it2.next()).getFirst();
                if (penResult != null) {
                    penResult.draw(canvas, paint);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        private final Bitmap c(final int i) {
            Object objComputeIfAbsent = NeoPencilPen.d.computeIfAbsent(0, ignored -> Companion.d(ignored));
            Intrinsics.checkNotNullExpressionValue(objComputeIfAbsent, "rotatedBitmaps.computeIf…ginBitmap()\n            }");
            final Bitmap bitmap = (Bitmap) objComputeIfAbsent;
            Object objComputeIfAbsent2 = NeoPencilPen.d.computeIfAbsent(Integer.valueOf(i),
                    ignored -> NeoPencilPen.Companion.e(bitmap, i, ignored));
            Intrinsics.checkNotNullExpressionValue(objComputeIfAbsent2, "rotatedBitmaps.computeIf…ngle * 10f)\n            }");
            return (Bitmap) objComputeIfAbsent2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        private static final Bitmap d(Integer num) {
            Intrinsics.checkNotNullParameter(num, "it");
            return NeoPencilPen.Companion.a();
        }

        /* JADX INFO: Access modifiers changed from: private */
        private static final Bitmap e(Bitmap bitmap, int i, Integer num) {
            Intrinsics.checkNotNullParameter(bitmap, "$srcBitmap");
            Intrinsics.checkNotNullParameter(num, "it");
            return NeoPencilPen.Companion.k(bitmap, i * 10.0f);
        }

        public static Bitmap f(Integer num) {
            return d(num);
        }

        public static Bitmap g(Bitmap bitmap, int angle, Integer num) {
            return e(bitmap, angle, num);
        }

        public static Bitmap h(Bitmap bitmap, Integer num) {
            return j(bitmap, num);
        }

        private final void i() {
            synchronized (this) {
                if (NeoPencilPen.d.size() >= 36) {
                    return;
                }
                long jCurrentTimeMillis = System.currentTimeMillis();
                final Bitmap bitmapA = a();
                for (int i = 0; i < 36; i++) {
                    NeoPencilPen.d.computeIfAbsent(Integer.valueOf(i), ignored -> NeoPencilPen.Companion.j(bitmapA, ignored));
                }
                long jCurrentTimeMillis2 = System.currentTimeMillis();
                Debug.INSTANCE.d(getClass(), "prepareRotatedBitmaps --> " + (jCurrentTimeMillis2 - jCurrentTimeMillis) + "ms", new Object[0]);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        private static final Bitmap j(Bitmap bitmap, Integer num) {
            Intrinsics.checkNotNullParameter(bitmap, "$srcBitmap");
            Intrinsics.checkNotNullParameter(num, "it");
            return NeoPencilPen.Companion.k(bitmap, num.intValue() * 10.0f);
        }

        private final Bitmap k(Bitmap bitmap, float f) {
            Matrix matrix = new Matrix();
            matrix.postRotate(f, bitmap.getWidth() / 2.0f, bitmap.getHeight() / 2.0f);
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
            new Canvas(bitmapCreateBitmap).drawBitmap(bitmap, matrix, null);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "bitmap");
            return bitmapCreateBitmap;
        }

        @Nullable
        public final NeoPencilPen create(@NotNull NeoPenConfig config) {
            Intrinsics.checkNotNullParameter(config, "config");
            long jCreatePen = NeoPenNative.INSTANCE.createPen(7, config);
            if (jCreatePen == 0) {
                return null;
            }
            return new NeoPencilPen(jCreatePen, new BrushMaskGenerator(config), null);
        }

        @NotNull
        public final NeoPenConfig defaultPenConfig() {
            NeoPenConfig neoPenConfig = new NeoPenConfig();
            neoPenConfig.type = 7;
            neoPenConfig.minWidth = 1.0f;
            neoPenConfig.brushSpacing = 0.25f;
            neoPenConfig.pressureSensitivity = 0.3f;
            return neoPenConfig;
        }

        @TargetApi(29)
        public final void drawPath(@NotNull Canvas canvas, @NotNull Path path, @NotNull Paint paint) {
            Intrinsics.checkNotNullParameter(canvas, "canvas");
            Intrinsics.checkNotNullParameter(path, "path");
            Intrinsics.checkNotNullParameter(paint, "paint");
            NeoPencilPen neoPencilPen = null;
            try {
                NeoPenConfig neoPenConfigDefaultPenConfig = defaultPenConfig();
                neoPenConfigDefaultPenConfig.width = paint.getStrokeWidth();
                neoPenConfigDefaultPenConfig.color = paint.getColor();
                NeoPencilPen neoPencilPenCreate = create(neoPenConfigDefaultPenConfig);
                if (neoPencilPenCreate == null) {
                    return;
                }
                Iterator it = PathKt.resampleMultiSegmentPath(path, 2).iterator();
                while (it.hasNext()) {
                    neoPencilPen = neoPencilPenCreate;
                    NeoPencilPen.Companion.b(canvas, paint, neoPencilPenCreate, (List) it.next());
                }
                neoPencilPenCreate.destroy();
            } catch (Throwable th) {
                if (neoPencilPen != null) {
                    neoPencilPen.destroy();
                }
                throw th;
            }
        }

        @TargetApi(29)
        @NotNull
        public final BrushMaskGenerator prepareBrushMask(@NotNull NeoPenConfig config) {
            Intrinsics.checkNotNullParameter(config, "config");
            i();
            return new BrushMaskGenerator(config);
        }

        public static final Bitmap access$getRotatedBitmap(Companion companion, int angle) {
            return companion.c(angle);
        }
    }

    public static final ConcurrentHashMap access$getMaskBitmaps$cp() {
        return c;
    }

    public static final ConcurrentHashMap access$getRotatedBitmaps$cp() {
        return d;
    }

    private NeoPencilPen(long j, Companion.BrushMaskGenerator brushMaskGenerator) {
        super(j);
        this.b = brushMaskGenerator;
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0003: CONSTRUCTOR (r6v0 long), (r8v0 com.onyx.android.sdk.pen.NeoPencilPen$Companion$BrushMaskGenerator) A[MD:(long, com.onyx.android.sdk.pen.NeoPencilPen$Companion$BrushMaskGenerator):void (m)] call: com.onyx.android.sdk.pen.NeoPencilPen.<init>(long, com.onyx.android.sdk.pen.NeoPencilPen$Companion$BrushMaskGenerator):void type: THIS */
    public /* synthetic */ NeoPencilPen(long j, Companion.BrushMaskGenerator brushMaskGenerator, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, brushMaskGenerator);
    }

    /* JADX DEBUG: Move duplicate insns, count: 1 to block B:15:0x004e */
    private final PenBrushResult a(PenInk penInk) {
        RectF rectF;
        if (penInk == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, penInk.getPoints().length), 5);
        int first = intProgressionStep.getFirst();
        int last = intProgressionStep.getLast();
        int step = intProgressionStep.getStep();
        if ((step <= 0 || first > last) && (step >= 0 || last > first)) {
            rectF = null;
        } else {
            RectF rectF2 = null;
            while (true) {
                RectF rectF3 = rectF2;
                float f = penInk.getPoints()[first];
                float f2 = penInk.getPoints()[first + 1];
                float f3 = penInk.getPoints()[first + 2];
                float f4 = penInk.getPoints()[first + 3];
                int angle = RangesKt.coerceIn((int) (Math.toDegrees(f4) * 0.1), 0, 36);
                byte angle36 = (byte) (angle == 36 ? 0 : angle);
                arrayList.add(PenBrushInkAccess.create(
                        f,
                        f2,
                        (byte) RangesKt.coerceIn((int) ((f3 / this.b.getConfig().width) * 255.0f), 0, 255),
                        angle36,
                        (byte) RangesKt.coerceIn((int) (penInk.getPoints()[first + 4] * 255), 0, 255)));
                rectF = rectF3;
                if (rectF3 == null) {
                    rectF = new RectF(f, f2, f + f3, f2 + f3);
                }
                rectF.union(f, f2);
                rectF.union(f + f3, f2 + f3);
                if (first == last) {
                    break;
                }
                first += step;
                rectF2 = rectF;
            }
        }
        if (rectF == null) {
            return null;
        }
        return new PenBrushResult(arrayList, this.b, rectF);
    }

    @Override // com.onyx.android.sdk.pen.NeoNativePen
    @NotNull
    public Pair<PenResult, PenResult> buildPenResult$sdk_pen_release(@Nullable NeoPenResult result) {
        return new Pair<>(a(result == null ? null : result.getRealInk()), a(result == null ? null : result.getPredictionInk()));
    }
}
