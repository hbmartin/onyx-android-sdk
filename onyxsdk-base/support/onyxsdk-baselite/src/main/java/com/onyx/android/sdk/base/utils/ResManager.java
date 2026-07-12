package com.onyx.android.sdk.base.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import com.onyx.android.sdk.base.data.Size;
import com.onyx.android.sdk.base.lite.extension.IntList;
import com.onyx.android.sdk.base.lite.extension.TypeExtKt;
import com.onyx.android.sdk.base.lite.utils.MathUtils;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;

/* JADX INFO: compiled from: ResManager.kt */
/* JADX INFO: loaded from: baselite.jar:com/onyx/android/sdk/base/utils/ResManager.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000¶\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u0011\n\u0002\b\u0007\n\u0002\u0010\u0015\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\n\u001a\u00020\u0007J\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0004J\u000e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0004J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0004J\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0011\u001a\u00020\u0004J\b\u0010\u0017\u001a\u0004\u0018\u00010\u0007J\u0006\u0010\u0018\u001a\u00020\u0004J\u0006\u0010\u0019\u001a\u00020\fJ\u0006\u0010\u001a\u001a\u00020\u0004J\u000e\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0004J\u000e\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0004J\u000e\u0010\u001d\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u0004J\u000e\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0004J\u0010\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010\u0014\u001a\u00020\u0004J\u0012\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010!\u001a\u0004\u0018\u00010\"J\"\u0010#\u001a\u0004\u0018\u00010 2\b\u0010$\u001a\u0004\u0018\u00010\"2\u0006\u0010%\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u0004J\u0010\u0010'\u001a\u00020\u00042\b\u0010(\u001a\u0004\u0018\u00010\"J\u0010\u0010)\u001a\u00020\u00042\b\u0010(\u001a\u0004\u0018\u00010\"J\u000e\u0010*\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u0004J\u001d\u0010+\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010,2\u0006\u0010\u0014\u001a\u00020\u0004¢\u0006\u0002\u0010-J\u000e\u0010.\u001a\u00020\f2\u0006\u0010/\u001a\u00020\u0004J\u000e\u00100\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u0004J\u001a\u00101\u001a\u00020\u00042\b\u0010!\u001a\u0004\u0018\u00010\"2\b\u00102\u001a\u0004\u0018\u00010\"J\u000e\u00103\u001a\u0002042\u0006\u0010\u0014\u001a\u00020\u0004J\u0018\u00105\u001a\u00020\u00042\b\u00106\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0014\u001a\u00020\u0004J\u000e\u00105\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0004J\u001d\u00107\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010,2\u0006\u0010\u0014\u001a\u00020\u0004¢\u0006\u0002\u00108J\u000e\u00109\u001a\u00020:2\u0006\u0010\u0014\u001a\u00020\u0004J\u0006\u0010;\u001a\u00020\u0004J\u0006\u0010<\u001a\u00020\fJ\u0006\u0010=\u001a\u00020\u0004J\u0018\u0010>\u001a\u0004\u0018\u00010\"2\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010?\u001a\u00020\u0004J5\u0010>\u001a\u0004\u0018\u00010\"2\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010?\u001a\u00020\u00042\u0016\u0010@\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010,\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010AJ\u0010\u0010B\u001a\u00020\u00042\b\u0010!\u001a\u0004\u0018\u00010\"J\u0010\u0010C\u001a\u00020\u00042\u0006\u00106\u001a\u00020\u0007H\u0007J\u0012\u0010D\u001a\u00020E2\b\u00106\u001a\u0004\u0018\u00010\u0007H\u0007J\u0010\u0010F\u001a\u0004\u0018\u00010\"2\u0006\u0010\u000f\u001a\u00020\u0004J\u0006\u0010G\u001a\u00020HJ\u0006\u0010I\u001a\u00020\u0004J\u0006\u0010J\u001a\u00020\u0004J\u0006\u0010K\u001a\u00020LJ\u0006\u0010M\u001a\u00020\fJ\u0006\u0010N\u001a\u00020OJ\u0006\u0010P\u001a\u00020LJ\u0006\u0010Q\u001a\u00020EJ\u0006\u0010R\u001a\u00020SJ\u0006\u0010T\u001a\u00020LJ\u0006\u0010U\u001a\u00020\fJ\u0006\u0010V\u001a\u00020\u0004J\u0006\u0010W\u001a\u00020\fJ\u001a\u0010X\u001a\u0004\u0018\u00010\"2\b\u00106\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0014\u001a\u00020\u0004J\u0010\u0010X\u001a\u0004\u0018\u00010\"2\u0006\u0010\u0014\u001a\u00020\u0004J+\u0010X\u001a\u00020\"2\u0006\u0010\u0014\u001a\u00020\u00042\u0016\u0010@\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010,\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010YJ\u0012\u0010X\u001a\u0004\u0018\u00010\"2\b\u0010(\u001a\u0004\u0018\u00010\"J\u0019\u0010Z\u001a\b\u0012\u0004\u0012\u00020\"0,2\u0006\u0010\u0014\u001a\u00020\u0004¢\u0006\u0002\u0010[J\u001f\u0010Z\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\"\u0018\u00010,2\b\u0010(\u001a\u0004\u0018\u00010\"¢\u0006\u0002\u0010\\J\u0018\u0010]\u001a\u00020\"2\b\u00106\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0014\u001a\u00020\u0004J\u001c\u0010]\u001a\u0004\u0018\u00010\"2\b\u00106\u001a\u0004\u0018\u00010\u00072\b\u0010(\u001a\u0004\u0018\u00010\"J\u000e\u0010]\u001a\u00020\"2\u0006\u0010\u0014\u001a\u00020\u0004J+\u0010]\u001a\u00020\"2\u0006\u0010\u0014\u001a\u00020\u00042\u0016\u0010@\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010,\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010YJ\u0012\u0010]\u001a\u0004\u0018\u00010\"2\b\u0010(\u001a\u0004\u0018\u00010\"J\u001c\u0010]\u001a\u0004\u0018\u00010\"2\b\u0010(\u001a\u0004\u0018\u00010\"2\b\b\u0002\u0010^\u001a\u00020\"J\u0010\u0010_\u001a\u0004\u0018\u00010`2\u0006\u0010\u0014\u001a\u00020\u0004J\u0010\u0010a\u001a\u0004\u0018\u00010\"2\u0006\u0010b\u001a\u00020\"J\u0010\u0010c\u001a\u0004\u0018\u00010\"2\u0006\u0010d\u001a\u00020\"J\u0006\u0010e\u001a\u00020\u0004J\u0010\u0010e\u001a\u00020\u00042\b\u00106\u001a\u0004\u0018\u00010\u0007J\u0006\u0010f\u001a\u00020EJ\u0010\u0010g\u001a\u00020h2\b\u00106\u001a\u0004\u0018\u00010\u0007J\u0006\u0010i\u001a\u00020\u0004J\u0010\u0010i\u001a\u00020\u00042\b\u00106\u001a\u0004\u0018\u00010\u0007J\u000e\u0010j\u001a\u00020k2\u0006\u00106\u001a\u00020\u0007J\u0010\u0010l\u001a\u00020k2\b\u0010m\u001a\u0004\u0018\u00010\u0007J\u0006\u0010n\u001a\u00020\u000eJ\u000e\u0010o\u001a\u00020\u000e2\u0006\u0010p\u001a\u00020\u0004J\u000e\u0010q\u001a\u00020\u000e2\u0006\u0010r\u001a\u00020\u0004J\u0006\u0010s\u001a\u00020\u000eJ\u000e\u0010t\u001a\u00020\u000e2\u0006\u0010r\u001a\u00020\u0004J\u0010\u0010u\u001a\u0004\u0018\u00010v2\u0006\u0010w\u001a\u00020\u0004J\u0010\u0010x\u001a\u0004\u0018\u00010y2\u0006\u0010\u000f\u001a\u00020\u0004J\u0012\u0010x\u001a\u0004\u0018\u00010y2\b\u0010!\u001a\u0004\u0018\u00010\"J\u000e\u0010z\u001a\u00020\u000e2\u0006\u0010{\u001a\u00020\u0004J\u0006\u0010|\u001a\u00020kJ\u001a\u0010}\u001a\u00020k2\b\u00106\u001a\u0004\u0018\u00010\u00072\u0006\u0010~\u001a\u00020hH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u007f"}, d2 = {"Lcom/onyx/android/sdk/base/utils/ResManager;", "", "()V", "INVALID_DIMENS", "", "INVALID_RESOURCE_ID", "appContext", "Landroid/content/Context;", "uiContextReference", "Ljava/lang/ref/WeakReference;", "getAppContext", "getAspectRatio", "", "getBoolean", "", "id", "getColor", "res", "getColorList", "Lcom/onyx/android/sdk/base/lite/extension/IntList;", "resId", "getColorStateList", "Landroid/content/res/ColorStateList;", "getContext", "getDPI", "getDensity", "getDensityDpi", "getDimens", "getDimensSafely", "getDimension", "getDimensionPixelSize", "getDrawable", "Landroid/graphics/drawable/Drawable;", "name", "", "getDrawableForDensity", "packageName", "resourceId", "density", "getDrawableResId", "resName", "getDrawableResIdSafely", "getFloat", "getFloatArray", "", "(I)[Ljava/lang/Float;", "getFloatValue", "floatDimen", "getFraction", "getIdentifier", "type", "getIntArray", "", "getInteger", "context", "getIntegerArray", "(I)[Ljava/lang/Integer;", "getLong", "", "getLongerRealScreenSize", "getLongerScreenSize", "getMinDragThreshold", "getQuantityString", "quantity", "formatArgs", "(II[Ljava/lang/Object;)Ljava/lang/String;", "getRawIdentifier", "getRealScreenHeight", "getRealScreenRect", "Landroid/graphics/Rect;", "getResourceEntryName", "getResources", "Landroid/content/res/Resources;", "getRotation", "getScreenArea", "getScreenContentSize", "Lcom/onyx/android/sdk/base/data/Size;", "getScreenHeight", "getScreenInchSize", "", "getScreenRealSize", "getScreenRect", "getScreenRectF", "Landroid/graphics/RectF;", "getScreenSize", "getScreenWidth", "getShorterRealScreenSize", "getShorterScreenSize", "getString", "(I[Ljava/lang/Object;)Ljava/lang/String;", "getStringArray", "(I)[Ljava/lang/String;", "(Ljava/lang/String;)[Ljava/lang/String;", "getStringSafely", "default", "getTypedArray", "Landroid/content/res/TypedArray;", "getUriOfAssets", "htmlName", "getUriOfRawName", "rawName", "getWindowDefaultHeight", "getWindowDefaultRect", "getWindowDefaultSize", "Landroid/graphics/Point;", "getWindowDefaultWidth", "init", "", "installUiContext", "uiContext", "isAppDebuggable", "isDeviceDpi", "targetDPI", "isLandscapeRotation", "rotation", "isLandscapeWindows", "isPortraitRotation", "loadXmlWithDom", "Lorg/w3c/dom/Document;", "xmlResId", "openRawResource", "Ljava/io/InputStream;", "shouldColorOnTopBeLight", "colorOnBottomInt", "uninstallUiContext", "updateWindowDefaultSizeInMultiWindowModeCompatM", "realSize", "sdk-baselite_release"})
public final class ResManager {
    public static final int INVALID_RESOURCE_ID = 0;
    public static final int INVALID_DIMENS = -1;
    private static Context appContext;

    @NotNull
    public static final ResManager INSTANCE = new ResManager();

    @NotNull
    private static WeakReference<Context> uiContextReference = new WeakReference<>(null);

    private ResManager() {
    }

    public final void init(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        appContext = context;
    }

    public final void installUiContext(@Nullable Context uiContext) {
        uiContextReference = new WeakReference<>(uiContext);
    }

    public final void uninstallUiContext() {
        uiContextReference.clear();
    }

    @Nullable
    public final Context getContext() {
        Context context = uiContextReference.get();
        if (context != null) {
            return context;
        }
        Context context2 = appContext;
        if (context2 != null) {
            return context2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("appContext");
        return null;
    }

    @Nullable
    public final String getString(int resId) {
        return getString(getContext(), resId);
    }

    @Nullable
    public final String getString(@Nullable Context context, int resId) {
        Intrinsics.checkNotNull(context);
        return context.getResources().getString(resId);
    }

    @NotNull
    public final String getStringSafely(int resId) {
        return getStringSafely(getContext(), resId);
    }

    @NotNull
    public final String getStringSafely(@Nullable Context context, int resId) {
        String str;
        try {
            String string = getString(context, resId);
            if (string == null) {
                string = "";
            }
            str = string;
        } catch (Exception e) {
            str = "";
        }
        return str;
    }

    @Nullable
    public final String getString(@Nullable String resName) {
        return getString(getIdentifier(resName, "string"));
    }

    @Nullable
    public final String getStringSafely(@Nullable String resName) {
        return getStringSafely(getContext(), resName);
    }

    public static /* synthetic */ String getStringSafely$default(ResManager resManager, String str, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = "";
        }
        return resManager.getStringSafely(str, str2);
    }

    @Nullable
    public final String getStringSafely(@Nullable String resName, @NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "default");
        int id = getIdentifier(resName, "string");
        if (id <= 0) {
            return str;
        }
        return getStringSafely(id);
    }

    @Nullable
    public final String getStringSafely(@Nullable Context context, @Nullable String resName) {
        int id = getIdentifier(resName, "string");
        if (id <= 0) {
            return "";
        }
        return getStringSafely(context, id);
    }

    public final int getRawIdentifier(@Nullable String name) {
        return getIdentifier(name, "raw");
    }

    @NotNull
    public final String getString(int resId, @NotNull Object... formatArgs) {
        Intrinsics.checkNotNullParameter(formatArgs, "formatArgs");
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        String string = context.getResources().getString(resId, Arrays.copyOf(formatArgs, formatArgs.length));
        Intrinsics.checkNotNullExpressionValue(string, "getContext()!!.resources…tring(resId, *formatArgs)");
        return string;
    }

    @NotNull
    public final String getStringSafely(int resId, @NotNull Object... formatArgs) {
        String string;
        Intrinsics.checkNotNullParameter(formatArgs, "formatArgs");
        try {
            string = getString(resId, Arrays.copyOf(formatArgs, formatArgs.length));
        } catch (Exception e) {
            e.printStackTrace();
            string = "";
        }
        return string;
    }

    public final int getInteger(int resId) {
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        return context.getResources().getInteger(resId);
    }

    public final int getInteger(@Nullable Context context, int resId) {
        Integer numValueOf;
        if (context == null) {
            numValueOf = null;
        } else {
            Resources resources = context.getResources();
            numValueOf = resources == null ? null : Integer.valueOf(resources.getInteger(resId));
        }
        return numValueOf == null ? getInteger(resId) : numValueOf.intValue();
    }

    public final long getLong(int resId) {
        return getInteger(resId);
    }

    public final float getFloat(int resId) {
        TypedValue outValue = new TypedValue();
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        context.getResources().getValue(resId, outValue, true);
        return outValue.getFloat();
    }

    public final float getFraction(int resId) {
        Context context = appContext;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appContext");
            context = null;
        }
        return context.getResources().getFraction(resId, 1, 0);
    }

    @NotNull
    public final String[] getStringArray(int resId) {
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        String[] stringArray = context.getResources().getStringArray(resId);
        Intrinsics.checkNotNullExpressionValue(stringArray, "getContext()!!.resources.getStringArray(resId)");
        return stringArray;
    }

    @Nullable
    public final String[] getStringArray(@Nullable String resName) {
        int id = getIdentifier(resName, "array");
        if (id <= 0) {
            return new String[0];
        }
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        return context.getResources().getStringArray(id);
    }

    @Nullable
    public final TypedArray getTypedArray(int resId) {
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        return context.getResources().obtainTypedArray(resId);
    }

    public final int getDimens(int resId) {
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        return context.getResources().getDimensionPixelSize(resId);
    }

    public final int getDimensSafely(int resId) {
        int dimens;
        try {
            dimens = getDimens(resId);
        } catch (Resources.NotFoundException e) {
            dimens = -1;
        }
        return dimens;
    }

    public final int getColor(int res) {
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        return context.getResources().getColor(res);
    }

    @NotNull
    public final IntList getColorList(int resId) {
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        return TypeExtKt.toIntList(context.getResources().getIntArray(resId));
    }

    @Nullable
    public final ColorStateList getColorStateList(int res) {
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        return context.getResources().getColorStateList(res);
    }

    @NotNull
    public final int[] getIntArray(int resId) {
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        int[] intArray = context.getResources().getIntArray(resId);
        Intrinsics.checkNotNullExpressionValue(intArray, "getContext()!!.resources.getIntArray(resId)");
        return intArray;
    }

    @Nullable
    public final Integer[] getIntegerArray(int resId) {
        int[] array = getIntArray(resId);
        Integer[] objectArray = new Integer[array.length];
        int i = 0;
        int length = array.length;
        while (i < length) {
            int ctr = i;
            i++;
            objectArray[ctr] = Integer.valueOf(array[ctr]);
        }
        return objectArray;
    }

    @Nullable
    public final Float[] getFloatArray(int resId) {
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        String[] array = context.getResources().getStringArray(resId);
        Intrinsics.checkNotNullExpressionValue(array, "getContext()!!.resources.getStringArray(resId)");
        Float[] objectArray = new Float[array.length];
        int i = 0;
        int length = array.length;
        while (i < length) {
            int ctr = i;
            i++;
            MathUtils mathUtils = MathUtils.INSTANCE;
            String str = array[ctr];
            Intrinsics.checkNotNullExpressionValue(str, "array[ctr]");
            objectArray[ctr] = Float.valueOf(mathUtils.parseFloat(str));
        }
        return objectArray;
    }

    @Nullable
    public final String getUriOfRawName(@NotNull String rawName) {
        Intrinsics.checkNotNullParameter(rawName, "rawName");
        return Intrinsics.stringPlus("file:///android_res/raw/", rawName);
    }

    @Nullable
    public final String getUriOfAssets(@NotNull String htmlName) {
        Intrinsics.checkNotNullParameter(htmlName, "htmlName");
        return Intrinsics.stringPlus("file:///android_asset/", htmlName);
    }

    public final float getDimension(int resId) {
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        return context.getResources().getDimension(resId);
    }

    public final int getDimensionPixelSize(int resId) {
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        return context.getResources().getDimensionPixelSize(resId);
    }

    public final float getFloatValue(int floatDimen) {
        TypedValue typedValue = new TypedValue();
        getResources().getValue(floatDimen, typedValue, true);
        return typedValue.getFloat();
    }

    @Nullable
    public final Drawable getDrawable(@Nullable String name) {
        int resId = getDrawableResIdSafely(name);
        if (resId <= 0) {
            return (Drawable) null;
        }
        return getDrawable(resId);
    }

    @Nullable
    public final Drawable getDrawable(int resId) {
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        return context.getResources().getDrawable(resId);
    }

    public final int getDrawableResId(@Nullable String resName) {
        return getIdentifier(resName, "drawable");
    }

    public final int getDrawableResIdSafely(@Nullable String resName) {
        int drawableResId;
        try {
            drawableResId = getDrawableResId(resName);
        } catch (Exception e) {
            drawableResId = 0;
        }
        return drawableResId;
    }

    public final float getScreenWidth() {
        Context context = appContext;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appContext");
            context = null;
        }
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public final float getScreenHeight() {
        Context context = appContext;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appContext");
            context = null;
        }
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public final float getLongerScreenSize() {
        return Math.max(getScreenWidth(), getScreenHeight());
    }

    public final float getShorterScreenSize() {
        return Math.min(getScreenWidth(), getScreenHeight());
    }

    public final int getLongerRealScreenSize() {
        Rect screenRect = getRealScreenRect(getAppContext());
        return Math.max(screenRect.width(), screenRect.height());
    }

    public final int getShorterRealScreenSize() {
        Rect screenRect = getRealScreenRect(getAppContext());
        return RangesKt.coerceAtMost(screenRect.width(), screenRect.height());
    }

    @NotNull
    public final Rect getScreenRect() {
        return new Rect(0, 0, (int) getShorterScreenSize(), (int) getLongerScreenSize());
    }

    @NotNull
    public final RectF getScreenRectF() {
        return new RectF(0.0f, 0.0f, getShorterScreenSize(), getLongerScreenSize());
    }

    @NotNull
    public final Size getScreenSize() {
        return new Size((int) getShorterScreenSize(), (int) getLongerScreenSize());
    }

    public final int getScreenArea() {
        Rect realScreenRect = getRealScreenRect(getAppContext());
        return realScreenRect.width() * realScreenRect.height();
    }

    @TargetApi(17)
    public final int getRealScreenHeight(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService("window");
        if (systemService == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.view.WindowManager");
        }
        WindowManager windowManager = (WindowManager) systemService;
        Point outSize = new Point(0, 0);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        if (defaultDisplay != null) {
            defaultDisplay.getRealSize(outSize);
        }
        return outSize.y;
    }

    @NotNull
    public final Size getScreenRealSize() {
        Context context = appContext;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appContext");
            context = null;
        }
        Rect $this$getScreenRealSize_u24lambda_u2d0 = getRealScreenRect(context);
        return new Size($this$getScreenRealSize_u24lambda_u2d0.width(), $this$getScreenRealSize_u24lambda_u2d0.height());
    }

    @NotNull
    public final Size getScreenContentSize() {
        return new Size((int) INSTANCE.getScreenWidth(), (int) INSTANCE.getScreenHeight());
    }

    @TargetApi(17)
    @NotNull
    public final Rect getRealScreenRect(@Nullable Context context) {
        Intrinsics.checkNotNull(context);
        Object systemService = context.getSystemService("window");
        if (systemService == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.view.WindowManager");
        }
        WindowManager windowManager = (WindowManager) systemService;
        Point outSize = new Point(0, 0);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        if (defaultDisplay != null) {
            defaultDisplay.getRealSize(outSize);
        }
        return new Rect(0, 0, outSize.x, outSize.y);
    }

    public final int getWindowDefaultWidth() {
        return getWindowDefaultWidth(getContext());
    }

    public final int getWindowDefaultWidth(@Nullable Context context) {
        return getWindowDefaultSize(context).x;
    }

    public final int getWindowDefaultHeight() {
        return getWindowDefaultHeight(getContext());
    }

    public final int getWindowDefaultHeight(@Nullable Context context) {
        return getWindowDefaultSize(context).y;
    }

    public final boolean isLandscapeWindows() {
        return getWindowDefaultWidth() > getWindowDefaultHeight();
    }

    @NotNull
    public final Point getWindowDefaultSize(@Nullable Context context) {
        Intrinsics.checkNotNull(context);
        Object systemService = context.getSystemService("window");
        if (systemService == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.view.WindowManager");
        }
        WindowManager windowManager = (WindowManager) systemService;
        Point outSize = new Point(0, 0);
        windowManager.getDefaultDisplay().getRealSize(outSize);
        updateWindowDefaultSizeInMultiWindowModeCompatM(context, outSize);
        return outSize;
    }

    @NotNull
    public final Rect getWindowDefaultRect() {
        Point point = getWindowDefaultSize(getContext());
        return new Rect(0, 0, point.x, point.y);
    }

    private final void updateWindowDefaultSizeInMultiWindowModeCompatM(Context context, Point realSize) {
    }

    public final float getAspectRatio() {
        return getScreenWidth() / getScreenHeight();
    }

    @NotNull
    public final Context getAppContext() {
        Context context = appContext;
        if (context != null) {
            return context;
        }
        Intrinsics.throwUninitializedPropertyAccessException("appContext");
        return null;
    }

    public final int getDPI() {
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    public final int getIdentifier(@Nullable String name, @Nullable String type) {
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        Resources resources = context.getResources();
        Context context2 = getContext();
        Intrinsics.checkNotNull(context2);
        return resources.getIdentifier(name, type, context2.getPackageName());
    }

    @Nullable
    public final InputStream openRawResource(@Nullable String name) {
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        return context.getResources().openRawResource(getIdentifier(name, "raw"));
    }

    @Nullable
    public final InputStream openRawResource(int id) {
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        return context.getResources().openRawResource(id);
    }

    @Nullable
    public final String getQuantityString(int resId, int quantity) {
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        return context.getResources().getQuantityString(resId, quantity);
    }

    @Nullable
    public final String getQuantityString(int resId, int quantity, @NotNull Object... formatArgs) {
        Intrinsics.checkNotNullParameter(formatArgs, "formatArgs");
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        return context.getResources().getQuantityString(resId, quantity, Arrays.copyOf(formatArgs, formatArgs.length));
    }

    public final boolean getBoolean(int id) {
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        return context.getResources().getBoolean(id);
    }

    @NotNull
    public final Resources getResources() {
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        Resources resources = context.getResources();
        Intrinsics.checkNotNullExpressionValue(resources, "getContext()!!.resources");
        return resources;
    }

    @Nullable
    public final String getResourceEntryName(int id) {
        String str;
        String resourceEntryName;
        try {
            if (id <= 0) {
                resourceEntryName = (String) null;
            } else {
                resourceEntryName = getResources().getResourceEntryName(id);
            }
            str = resourceEntryName;
        } catch (Exception e) {
            str = (String) null;
        }
        return str;
    }

    public final int getDensityDpi() {
        Context appContext2 = getAppContext();
        Intrinsics.checkNotNull(appContext2);
        return appContext2.getResources().getDisplayMetrics().densityDpi;
    }

    public final float getDensity() {
        Context appContext2 = getAppContext();
        Intrinsics.checkNotNull(appContext2);
        return appContext2.getResources().getDisplayMetrics().density;
    }

    public final boolean isDeviceDpi(int targetDPI) {
        return getDensityDpi() == targetDPI;
    }

    @Nullable
    public final Drawable getDrawableForDensity(@Nullable String packageName, int resourceId, int density) {
        Drawable drawableForDensity;
        if (resourceId <= 0) {
            return (Drawable) null;
        }
        try {
            Context context = getContext();
            Intrinsics.checkNotNull(context);
            PackageManager packageManager = context.getPackageManager();
            Intrinsics.checkNotNull(packageName);
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            Intrinsics.checkNotNullExpressionValue(packageInfo, "getContext()!!.packageMa…ageInfo(packageName!!, 0)");
            Context context2 = getContext();
            Intrinsics.checkNotNull(context2);
            Resources resources = context2.getPackageManager().getResourcesForApplication(packageInfo.applicationInfo);
            Intrinsics.checkNotNullExpressionValue(resources, "getContext()!!.packageMa…kageInfo.applicationInfo)");
            drawableForDensity = resources.getDrawableForDensity(resourceId, density);
        } catch (Exception e) {
            drawableForDensity = (Drawable) null;
        }
        return drawableForDensity;
    }

    public final boolean shouldColorOnTopBeLight(int colorOnBottomInt) {
        return 186.0d > (0.299d * ((double) Color.red(colorOnBottomInt))) + ((0.587d * ((double) Color.green(colorOnBottomInt))) + (0.114d * ((double) Color.blue(colorOnBottomInt))));
    }

    @Nullable
    public final Document loadXmlWithDom(int xmlResId) {
        try {
            InputStream inputStreamOpenRawResource = getResources().openRawResource(xmlResId);
            Throwable th = null;
            try {
                try {
                    InputStream inputStream = inputStreamOpenRawResource;
                    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                    Document document = documentBuilder.parse(inputStream);
                    CloseableKt.closeFinally(inputStreamOpenRawResource, (Throwable) null);
                    return document;
                } catch (Throwable th2) {
                    th = th2;
                    throw th2;
                }
            } catch (Throwable th3) {
                CloseableKt.closeFinally(inputStreamOpenRawResource, th);
                throw th3;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final int getMinDragThreshold() {
        Context context = appContext;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appContext");
            context = null;
        }
        return ViewConfiguration.get(context).getScaledTouchSlop() / 4;
    }

    public final int getRotation() {
        WindowManager windowManager = (WindowManager) getAppContext().getSystemService("window");
        if (windowManager == null) {
            return 0;
        }
        return windowManager.getDefaultDisplay().getRotation();
    }

    public final boolean isLandscapeRotation(int rotation) {
        switch (rotation) {
            case 1:
            case 3:
                return true;
            case 2:
            default:
                return false;
        }
    }

    public final boolean isPortraitRotation(int rotation) {
        switch (rotation) {
            case 0:
            case 2:
                return true;
            case 1:
            default:
                return false;
        }
    }

    public final double getScreenInchSize() {
        Context context = getContext();
        Object systemService = context == null ? null : context.getSystemService("window");
        if (systemService == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.view.WindowManager");
        }
        WindowManager windowManager = (WindowManager) systemService;
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getMetrics(metrics);
        if (Build.VERSION.SDK_INT >= 30) {
            if (display != null) {
                display.getRealSize(point);
            }
        } else {
            display.getSize(point);
        }
        double w = point.x / metrics.xdpi;
        double h = point.y / metrics.ydpi;
        double size = Math.sqrt((w * w) + (h * h));
        return size;
    }

    public final boolean isAppDebuggable() {
        return (getAppContext().getApplicationInfo().flags & 2) != 0;
    }
}
