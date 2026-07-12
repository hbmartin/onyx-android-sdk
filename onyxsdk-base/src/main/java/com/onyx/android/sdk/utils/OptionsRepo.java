package com.onyx.android.sdk.utils;

import android.graphics.Matrix;
import android.graphics.RectF;
import com.alibaba.fastjson2.annotation.JSONType;
import com.onyx.android.sdk.deserializer.OptionsRepoDeserializer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/OptionsRepo.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u001a\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\u000f\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u00020\u0000H\u0016J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0005J&\u0010\u000e\u001a\u0002H\u000f\"\u0006\b\u0000\u0010\u000f\u0018\u00012\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u0002H\u000fH\u0086\b¢\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\fJ\u0016\u0010\u0013\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0014J\u0016\u0010\u0015\u001a\u00020\u00162\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0016J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u0010\u001a\u00020\u0018J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u0010\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\r\u001a\u00020\u0005J\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u00052\u0006\u0010\r\u001a\u00020\u0005J \u0010\u001e\u001a\u0004\u0018\u0001H\u000f\"\u0006\b\u0000\u0010\u000f\u0018\u00012\u0006\u0010\r\u001a\u00020\u0005H\u0086\b¢\u0006\u0002\u0010\u001fJ*\u0010 \u001a\u0002H\u000f\"\n\b\u0000\u0010\u000f\u0018\u0001*\u00020\u00062\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u0002H\u000fH\u0086\b¢\u0006\u0002\u0010\u0011J\u000e\u0010!\u001a\u00020\u001c2\u0006\u0010\r\u001a\u00020\u0005J\u000e\u0010\"\u001a\u00020#2\u0006\u0010\r\u001a\u00020\u0005J\u0018\u0010$\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u0010\u001a\u00020\u0005J\u0014\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00050&2\u0006\u0010\r\u001a\u00020\u0005J'\u0010'\u001a\u00020(\"\b\b\u0000\u0010\u000f*\u00020\u00062\u0006\u0010\r\u001a\u00020\u00052\b\u0010)\u001a\u0004\u0018\u0001H\u000f¢\u0006\u0002\u0010*J\u0016\u0010+\u001a\u00020(2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\fJ\u0016\u0010,\u001a\u00020(2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u0014J\u0016\u0010-\u001a\u00020(2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u0016J\u0016\u0010.\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u0018J\u0016\u0010/\u001a\u00020(2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u001aJ\u0016\u00100\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u00052\u0006\u00101\u001a\u00020\u001cJ\u0016\u00102\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u00052\u0006\u00103\u001a\u00020#J\u0018\u00104\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u00052\b\u0010)\u001a\u0004\u0018\u00010\u0005J\u001c\u00105\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u00052\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00050&J\u000e\u00106\u001a\u00020(2\u0006\u0010\r\u001a\u00020\u0005R1\u0010\u0003\u001a\"\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004j\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u0006`\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u00067"}, d2 = {"Lcom/onyx/android/sdk/utils/OptionsRepo;", TTFFont.UNKNOWN_FONT_NAME, "()V", ShapeOptionsConstant.OPTIONS_REPO_NAME, "Ljava/util/HashMap;", TTFFont.UNKNOWN_FONT_NAME, TTFFont.UNKNOWN_FONT_NAME, "Lkotlin/collections/HashMap;", "getRepo", "()Ljava/util/HashMap;", "clone", "contains", TTFFont.UNKNOWN_FONT_NAME, "key", "get", "T", "defaultValue", "(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;", "getBoolean", "getDouble", TTFFont.UNKNOWN_FONT_NAME, "getFloat", TTFFont.UNKNOWN_FONT_NAME, "getInt", TTFFont.UNKNOWN_FONT_NAME, "getLong", TTFFont.UNKNOWN_FONT_NAME, "getMatrix", "Landroid/graphics/Matrix;", "getNullableString", "getOrNull", "(Ljava/lang/String;)Ljava/lang/Object;", "getOrPut", "getOrPutMatrix", "getRectF", "Landroid/graphics/RectF;", "getString", "getStringList", TTFFont.UNKNOWN_FONT_NAME, "put", TTFFont.UNKNOWN_FONT_NAME, "value", "(Ljava/lang/String;Ljava/lang/Object;)V", "putBoolean", "putDouble", "putFloat", "putInt", "putLong", "putMatrix", "matrix", "putRectF", "rect", "putString", "putStringList", "remove", "onyxsdk-base_release"})
@JSONType(deserializer = OptionsRepoDeserializer.class)
public final class OptionsRepo implements Cloneable {

    @NotNull
    private final HashMap<String, Object> a = new HashMap<>();

    public static /* synthetic */ int getInt$default(OptionsRepo optionsRepo, String str, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return optionsRepo.getInt(str, i);
    }

    public static /* synthetic */ long getLong$default(OptionsRepo optionsRepo, String str, long j, int i, Object obj) {
        if ((i & 2) != 0) {
            j = 0;
        }
        return optionsRepo.getLong(str, j);
    }

    public static /* synthetic */ String getString$default(OptionsRepo optionsRepo, String str, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = TTFFont.UNKNOWN_FONT_NAME;
        }
        return optionsRepo.getString(str, str2);
    }

    @NotNull
    public final HashMap<String, Object> getRepo() {
        return this.a;
    }

    public final /* synthetic */ <T> T get(String key, T defaultValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        if (getRepo().containsKey(key)) {
            Object obj = getRepo().get(key);
            Intrinsics.reifiedOperationMarker(3, "T");
            if (obj instanceof Object) {
                T t = (T) getRepo().get(key);
                Intrinsics.reifiedOperationMarker(1, "T");
                return t;
            }
        }
        return defaultValue;
    }

    public final /* synthetic */ <T> T getOrPut(String key, T defaultValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        if (getRepo().containsKey(key)) {
            Object obj = getRepo().get(key);
            Intrinsics.reifiedOperationMarker(3, "T");
            if (obj instanceof Object) {
                T t = (T) getRepo().get(key);
                Intrinsics.reifiedOperationMarker(1, "T");
                return t;
            }
        }
        getRepo().put(key, defaultValue);
        return defaultValue;
    }

    public final /* synthetic */ <T> T getOrNull(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        T t = (T) getRepo().get(key);
        Intrinsics.reifiedOperationMarker(2, "T");
        return t;
    }

    public final <T> void put(@NotNull String key, @Nullable T value) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.a.put(key, value);
    }

    public final void remove(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.a.remove(key);
    }

    public final boolean contains(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.a.containsKey(key);
    }

    public final boolean getBoolean(@NotNull String key, boolean defaultValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        Boolean boolValueOf = Boolean.valueOf(defaultValue);
        if (getRepo().containsKey(key) && (getRepo().get(key) instanceof Boolean)) {
            Object obj = getRepo().get(key);
            if (obj == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Boolean");
            }
            boolValueOf = (Boolean) obj;
        }
        return boolValueOf.booleanValue();
    }

    public final void putBoolean(@NotNull String key, boolean value) {
        Intrinsics.checkNotNullParameter(key, "key");
        put(key, Boolean.valueOf(value));
    }

    public final float getFloat(@NotNull String key, float defaultValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        Float fValueOf = Float.valueOf(defaultValue);
        if (getRepo().containsKey(key) && (getRepo().get(key) instanceof Float)) {
            Object obj = getRepo().get(key);
            if (obj == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Float");
            }
            fValueOf = (Float) obj;
        }
        return fValueOf.floatValue();
    }

    public final void putFloat(@NotNull String key, float value) {
        Intrinsics.checkNotNullParameter(key, "key");
        put(key, Float.valueOf(value));
    }

    public final double getDouble(@NotNull String key, double defaultValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        Double dValueOf = Double.valueOf(defaultValue);
        if (getRepo().containsKey(key) && (getRepo().get(key) instanceof Double)) {
            Object obj = getRepo().get(key);
            if (obj == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Double");
            }
            dValueOf = (Double) obj;
        }
        return dValueOf.doubleValue();
    }

    public final void putDouble(@NotNull String key, double value) {
        Intrinsics.checkNotNullParameter(key, "key");
        put(key, Double.valueOf(value));
    }

    public final int getInt(@NotNull String key, int defaultValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        Integer numValueOf = Integer.valueOf(defaultValue);
        if (getRepo().containsKey(key) && (getRepo().get(key) instanceof Integer)) {
            Object obj = getRepo().get(key);
            if (obj == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Int");
            }
            numValueOf = (Integer) obj;
        }
        return numValueOf.intValue();
    }

    @NotNull
    public final OptionsRepo putInt(@NotNull String key, int value) {
        Intrinsics.checkNotNullParameter(key, "key");
        put(key, Integer.valueOf(value));
        return this;
    }

    public final long getLong(@NotNull String key, long defaultValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        Long lValueOf = Long.valueOf(defaultValue);
        if (getRepo().containsKey(key) && (getRepo().get(key) instanceof Long)) {
            Object obj = getRepo().get(key);
            if (obj == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Long");
            }
            lValueOf = (Long) obj;
        }
        return lValueOf.longValue();
    }

    public final void putLong(@NotNull String key, long value) {
        Intrinsics.checkNotNullParameter(key, "key");
        put(key, Long.valueOf(value));
    }

    @NotNull
    public final String getString(@NotNull String key, @NotNull String defaultValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        if (getRepo().containsKey(key) && (getRepo().get(key) instanceof String)) {
            Object obj = getRepo().get(key);
            if (obj == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
            }
            defaultValue = (String) obj;
        }
        return defaultValue;
    }

    @Nullable
    public final String getNullableString(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        String str = null;
        if (getRepo().containsKey(key)) {
            Object obj = getRepo().get(key);
            if (obj != null ? obj instanceof String : true) {
                str = (String) getRepo().get(key);
            }
        }
        return str;
    }

    @NotNull
    public final OptionsRepo putString(@NotNull String key, @Nullable String value) {
        Intrinsics.checkNotNullParameter(key, "key");
        put(key, value);
        return this;
    }

    @NotNull
    public final Matrix getMatrix(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        Matrix matrix = new Matrix();
        if (getRepo().containsKey(key) && (getRepo().get(key) instanceof Matrix)) {
            Object obj = getRepo().get(key);
            if (obj == null) {
                throw new NullPointerException("null cannot be cast to non-null type android.graphics.Matrix");
            }
            matrix = (Matrix) obj;
        }
        return matrix;
    }

    @NotNull
    public final Matrix getOrPutMatrix(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        Matrix matrix = new Matrix();
        if (getRepo().containsKey(key) && (getRepo().get(key) instanceof Matrix)) {
            Object obj = getRepo().get(key);
            if (obj == null) {
                throw new NullPointerException("null cannot be cast to non-null type android.graphics.Matrix");
            }
            matrix = (Matrix) obj;
        } else {
            getRepo().put(key, matrix);
        }
        return matrix;
    }

    @NotNull
    public final OptionsRepo putMatrix(@NotNull String key, @NotNull Matrix matrix) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(matrix, "matrix");
        put(key, matrix);
        return this;
    }

    @NotNull
    public final RectF getRectF(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        RectF rectF = new RectF();
        if (getRepo().containsKey(key) && (getRepo().get(key) instanceof RectF)) {
            Object obj = getRepo().get(key);
            if (obj == null) {
                throw new NullPointerException("null cannot be cast to non-null type android.graphics.RectF");
            }
            rectF = (RectF) obj;
        }
        return rectF;
    }

    @NotNull
    public final OptionsRepo putRectF(@NotNull String key, @NotNull RectF rect) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(rect, "rect");
        put(key, rect);
        return this;
    }

    @NotNull
    public final List<String> getStringList(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        ArrayList arrayList = new ArrayList();
        if (getRepo().containsKey(key) && (getRepo().get(key) instanceof ArrayList)) {
            Object obj = getRepo().get(key);
            if (obj == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.util.ArrayList<kotlin.String>");
            }
            arrayList = (ArrayList) obj;
        }
        return arrayList;
    }

    @NotNull
    public final OptionsRepo putStringList(@NotNull String key, @NotNull List<String> value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        put(key, value);
        return this;
    }

    @NotNull
    public OptionsRepo clone() {
        OptionsRepo optionsRepo = new OptionsRepo();
        optionsRepo.a.putAll(this.a);
        return optionsRepo;
    }
}
