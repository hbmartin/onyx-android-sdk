package com.onyx.android.sdk.base.utils;

import android.text.TextUtils;
import android.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: Debug.kt */
/* JADX INFO: loaded from: baselite.jar:com/onyx/android/sdk/base/utils/Debug.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0011\bÆ\u0002\u0018\u00002\u00020\u0001:\u00012B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0004H\u0002J\u000e\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0018J9\u0010\u0017\u001a\u00020\u00152\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001a2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00042\u0016\u0010\u001b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u001c\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u001dJ\u0010\u0010\u0017\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0004J7\u0010\u0017\u001a\u00020\u00152\b\u0010\u001e\u001a\u0004\u0018\u00010\u00042\b\u0010\u0016\u001a\u0004\u0018\u00010\u00042\u0016\u0010\u001b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u001c\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u001fJ\u0010\u0010\u0017\u001a\u00020\u00152\b\u0010 \u001a\u0004\u0018\u00010!J9\u0010\u0017\u001a\u00020\u00152\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\"2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00042\u0016\u0010\u001b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u001c\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010#J9\u0010$\u001a\u00020\u00152\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001a2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00042\u0016\u0010\u001b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u001c\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u001dJ&\u0010$\u001a\u00020\u00152\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001a2\b\u0010%\u001a\u0004\u0018\u00010\u00042\b\u0010&\u001a\u0004\u0018\u00010!J\u001a\u0010$\u001a\u00020\u00152\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001a2\u0006\u0010&\u001a\u00020!J\u0010\u0010$\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0004J7\u0010$\u001a\u00020\u00152\b\u0010\u001e\u001a\u0004\u0018\u00010\u00042\b\u0010\u0016\u001a\u0004\u0018\u00010\u00042\u0016\u0010\u001b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u001c\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u001fJ\u000e\u0010$\u001a\u00020\u00152\u0006\u0010&\u001a\u00020!J9\u0010$\u001a\u00020\u00152\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\"2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00042\u0016\u0010\u001b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u001c\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010#J&\u0010$\u001a\u00020\u00152\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\"2\b\u0010%\u001a\u0004\u0018\u00010\u00042\b\u0010&\u001a\u0004\u0018\u00010!J\u001a\u0010$\u001a\u00020\u00152\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\"2\u0006\u0010&\u001a\u00020!J-\u0010'\u001a\u00020\u00042\b\u0010(\u001a\u0004\u0018\u00010\u00042\u0016\u0010\u001b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u001c\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010)J\u0012\u0010*\u001a\u00020\u00042\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001aJ9\u0010+\u001a\u00020\u00152\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001a2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00042\u0016\u0010\u001b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u001c\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u001dJ\u000e\u0010+\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0004J7\u0010+\u001a\u00020\u00152\b\u0010\u001e\u001a\u0004\u0018\u00010\u00042\b\u0010\u0016\u001a\u0004\u0018\u00010\u00042\u0016\u0010\u001b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u001c\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u001fJ9\u0010+\u001a\u00020\u00152\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\"2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00042\u0016\u0010\u001b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u001c\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010#J\u000e\u0010,\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0004J\u0010\u0010-\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0004H\u0002J\u000e\u0010.\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0004J\u0018\u0010.\u001a\u00020\u00152\b\u0010\u001e\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0016\u001a\u00020\u0004J9\u0010/\u001a\u00020\u00152\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001a2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00042\u0016\u0010\u001b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u001c\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u001dJ5\u0010/\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u00042\b\u0010\u0016\u001a\u0004\u0018\u00010\u00042\u0016\u0010\u001b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u001c\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u001fJ9\u0010/\u001a\u00020\u00152\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\"2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00042\u0016\u0010\u001b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u001c\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010#J\u0012\u00100\u001a\u00020\u00042\b\u0010\u001e\u001a\u0004\u0018\u00010\u0004H\u0002J9\u00101\u001a\u00020\u00152\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001a2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00042\u0016\u0010\u001b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u001c\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u001dJ\u001c\u00101\u001a\u00020\u00152\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001a2\b\u0010&\u001a\u0004\u0018\u00010!J/\u00101\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00042\u0012\u0010\u001b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u001c\"\u00020\u0001¢\u0006\u0002\u0010\u001fJ\u0010\u00101\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0004J\u0010\u00101\u001a\u00020\u00152\b\u0010&\u001a\u0004\u0018\u00010!J9\u00101\u001a\u00020\u00152\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\"2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00042\u0016\u0010\u001b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u001c\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010#J\u001c\u00101\u001a\u00020\u00152\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\"2\b\u0010&\u001a\u0004\u0018\u00010!R\u0019\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000b\"\u0004\b\u000f\u0010\rR\u001a\u0010\u0010\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u000b\"\u0004\b\u0011\u0010\rR\u0016\u0010\u0012\u001a\n \u0005*\u0004\u0018\u00010\u00130\u0013X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00063"}, d2 = {"Lcom/onyx/android/sdk/base/utils/Debug;", "", "()V", "TAG", "", "kotlin.jvm.PlatformType", "getTAG", "()Ljava/lang/String;", "debug", "", "getDebug", "()Z", "setDebug", "(Z)V", "isCompareObfuscateLogEnabled", "setCompareObfuscateLogEnabled", "isObfuscateLogEnabled", "setObfuscateLogEnabled", "obfuscatePattern", "Ljava/util/regex/Pattern;", "compareObfuscateLog", "", "msg", "d", "Lcom/onyx/android/sdk/base/utils/Debug$IMessage;", "cls", "Ljava/lang/Class;", "args", "", "(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)V", "tag", "(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V", "tr", "", "Lkotlin/reflect/KClass;", "(Lkotlin/reflect/KClass;Ljava/lang/String;[Ljava/lang/Object;)V", "e", "message", "throwable", "formatString", "str", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "getVerifiedTag", "i", "obfuscateLog", "obfuscateLogImpl", "printStackTraceDebug", "v", "verifyTag", "w", "IMessage", "sdk-baselite_release"})
public final class Debug {
    private static boolean debug;
    private static boolean isObfuscateLogEnabled;
    private static boolean isCompareObfuscateLogEnabled;

    @NotNull
    public static final Debug INSTANCE = new Debug();
    private static final String TAG = Debug.class.getSimpleName();
    private static final Pattern obfuscatePattern = Pattern.compile("[a-z]+");

    /* JADX INFO: compiled from: Debug.kt */
    /* JADX INFO: loaded from: baselite.jar:com/onyx/android/sdk/base/utils/Debug$IMessage.class */
    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"Lcom/onyx/android/sdk/base/utils/Debug$IMessage;", "", "message", "", "getMessage", "()Ljava/lang/String;", "sdk-baselite_release"})
    public interface IMessage {
        @Nullable
        String getMessage();
    }

    private Debug() {
    }

    public final String getTAG() {
        return TAG;
    }

    public final boolean getDebug() {
        return debug;
    }

    public final void setDebug(boolean z) {
        debug = z;
    }

    public final boolean isObfuscateLogEnabled() {
        return isObfuscateLogEnabled;
    }

    public final void setObfuscateLogEnabled(boolean z) {
        isObfuscateLogEnabled = z;
    }

    public final boolean isCompareObfuscateLogEnabled() {
        return isCompareObfuscateLogEnabled;
    }

    public final void setCompareObfuscateLogEnabled(boolean z) {
        isCompareObfuscateLogEnabled = z;
    }

    public final void d(@Nullable String msg) {
        if (debug) {
            String str = TAG;
            Intrinsics.checkNotNull(msg);
            Log.d(str, msg);
        }
    }

    public final void d(@NotNull IMessage msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (debug) {
            String str = TAG;
            String message = msg.getMessage();
            Intrinsics.checkNotNull(message);
            Log.d(str, message);
        }
    }

    public final void d(@Nullable Throwable tr) {
        if (debug) {
            Log.d(TAG, "", tr);
        }
    }

    public final void printStackTraceDebug(@NotNull String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        printStackTraceDebug(TAG, msg);
    }

    public final void printStackTraceDebug(@Nullable String tag, @NotNull String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (debug) {
            Log.v(tag, "", new Exception(Intrinsics.stringPlus("Printing detailed debug information.\n Message :", msg)));
        }
    }

    public final void v(@NotNull Class<?> cls, @Nullable String msg, @NotNull Object... args) {
        Intrinsics.checkNotNullParameter(cls, "cls");
        Intrinsics.checkNotNullParameter(args, "args");
        if (debug) {
            Log.v(getVerifiedTag(cls), formatString(msg, Arrays.copyOf(args, args.length)));
        }
    }

    public final void v(@NotNull KClass<?> cls, @Nullable String msg, @NotNull Object... args) {
        Intrinsics.checkNotNullParameter(cls, "cls");
        Intrinsics.checkNotNullParameter(args, "args");
        v(JvmClassMappingKt.getJavaClass(cls), msg, args);
    }

    public final void v(@NotNull String tag, @Nullable String msg, @NotNull Object... args) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(args, "args");
        if (debug) {
            Log.v(tag, formatString(msg, Arrays.copyOf(args, args.length)));
        }
    }

    public final void d(@NotNull Class<?> cls, @Nullable String msg, @NotNull Object... args) {
        Intrinsics.checkNotNullParameter(cls, "cls");
        Intrinsics.checkNotNullParameter(args, "args");
        if (debug) {
            Log.d(getVerifiedTag(cls), formatString(msg, Arrays.copyOf(args, args.length)));
        }
    }

    public final void d(@NotNull KClass<?> cls, @Nullable String msg, @NotNull Object... args) {
        Intrinsics.checkNotNullParameter(cls, "cls");
        Intrinsics.checkNotNullParameter(args, "args");
        d(JvmClassMappingKt.getJavaClass(cls), msg, args);
    }

    public final void d(@Nullable String tag, @Nullable String msg, @NotNull Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        if (debug) {
            Log.d(tag, formatString(msg, Arrays.copyOf(args, args.length)));
        }
    }

    public final void i(@NotNull String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        Log.i(TAG, obfuscateLog(msg));
    }

    public final void i(@Nullable String tag, @Nullable String msg, @NotNull Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        String str = obfuscateLog(formatString(msg, Arrays.copyOf(args, args.length)));
        Log.i(tag, str);
    }

    public final void i(@NotNull Class<?> cls, @Nullable String msg, @NotNull Object... args) {
        Intrinsics.checkNotNullParameter(cls, "cls");
        Intrinsics.checkNotNullParameter(args, "args");
        String tag = obfuscateLog(getVerifiedTag(cls));
        i(tag, msg, Arrays.copyOf(args, args.length));
    }

    public final void i(@NotNull KClass<?> cls, @Nullable String msg, @NotNull Object... args) {
        Intrinsics.checkNotNullParameter(cls, "cls");
        Intrinsics.checkNotNullParameter(args, "args");
        i(JvmClassMappingKt.getJavaClass(cls), msg, Arrays.copyOf(args, args.length));
    }

    @NotNull
    public final String obfuscateLog(@NotNull String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (TextUtils.isEmpty(msg)) {
            return "";
        }
        compareObfuscateLog(msg);
        if (isObfuscateLogEnabled) {
            return obfuscateLogImpl(msg);
        }
        return msg;
    }

    private final String obfuscateLogImpl(String msg) {
        String strReplaceAll = obfuscatePattern.matcher(msg).replaceAll("");
        Intrinsics.checkNotNullExpressionValue(strReplaceAll, "obfuscatePattern.matcher(msg).replaceAll(\"\")");
        return strReplaceAll;
    }

    private final void compareObfuscateLog(String msg) {
        if (isCompareObfuscateLogEnabled) {
            Log.i(TAG, Intrinsics.stringPlus("raw : = ", msg));
            Log.i(TAG, Intrinsics.stringPlus("obfuscate : = ", obfuscateLogImpl(msg)));
        }
    }

    public final void w(@Nullable String msg) {
        String str = TAG;
        Intrinsics.checkNotNull(msg);
        Log.w(str, msg);
    }

    public final void w(@NotNull Class<?> cls, @Nullable String msg, @NotNull Object... args) {
        Intrinsics.checkNotNullParameter(cls, "cls");
        Intrinsics.checkNotNullParameter(args, "args");
        Log.w(getVerifiedTag(cls), formatString(msg, Arrays.copyOf(args, args.length)));
    }

    public final void w(@NotNull KClass<?> cls, @Nullable String msg, @NotNull Object... args) {
        Intrinsics.checkNotNullParameter(cls, "cls");
        Intrinsics.checkNotNullParameter(args, "args");
        w(JvmClassMappingKt.getJavaClass(cls), msg, args);
    }

    public final void w(@Nullable Throwable throwable) {
        Log.w(TAG, throwable);
    }

    public final void w(@NotNull String tag, @NotNull String msg, @NotNull Object... args) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        Intrinsics.checkNotNullParameter(args, "args");
        Log.w(tag, formatString(msg, Arrays.copyOf(args, args.length)));
    }

    public final void w(@NotNull Class<?> cls, @Nullable Throwable throwable) {
        Intrinsics.checkNotNullParameter(cls, "cls");
        Log.w(getVerifiedTag(cls), throwable);
    }

    public final void w(@NotNull KClass<?> cls, @Nullable Throwable throwable) {
        Intrinsics.checkNotNullParameter(cls, "cls");
        w(JvmClassMappingKt.getJavaClass(cls), throwable);
    }

    public final void e(@Nullable String msg) {
        if (msg != null) {
            Log.e(TAG, msg);
        }
    }

    public final void e(@Nullable String tag, @Nullable String msg, @NotNull Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        Log.e(tag, formatString(msg, Arrays.copyOf(args, args.length)));
    }

    public final void e(@NotNull Class<?> cls, @Nullable String msg, @NotNull Object... args) {
        Intrinsics.checkNotNullParameter(cls, "cls");
        Intrinsics.checkNotNullParameter(args, "args");
        Log.e(getVerifiedTag(cls), formatString(msg, Arrays.copyOf(args, args.length)));
    }

    public final void e(@NotNull KClass<?> cls, @Nullable String msg, @NotNull Object... args) {
        Intrinsics.checkNotNullParameter(cls, "cls");
        Intrinsics.checkNotNullParameter(args, "args");
        e(JvmClassMappingKt.getJavaClass(cls), msg, args);
    }

    public final void e(@NotNull Throwable throwable) {
        Intrinsics.checkNotNullParameter(throwable, "throwable");
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        Log.e(TAG, sw.toString());
    }

    public final void e(@NotNull Class<?> cls, @NotNull Throwable throwable) {
        Intrinsics.checkNotNullParameter(cls, "cls");
        Intrinsics.checkNotNullParameter(throwable, "throwable");
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        Log.e(getVerifiedTag(cls), sw.toString());
    }

    public final void e(@NotNull KClass<?> cls, @NotNull Throwable throwable) {
        Intrinsics.checkNotNullParameter(cls, "cls");
        Intrinsics.checkNotNullParameter(throwable, "throwable");
        e(JvmClassMappingKt.getJavaClass(cls), throwable);
    }

    public final void e(@NotNull Class<?> cls, @Nullable String message, @Nullable Throwable throwable) {
        Intrinsics.checkNotNullParameter(cls, "cls");
        Log.e(getVerifiedTag(cls), message, throwable);
    }

    public final void e(@NotNull KClass<?> cls, @Nullable String message, @Nullable Throwable throwable) {
        Intrinsics.checkNotNullParameter(cls, "cls");
        e(JvmClassMappingKt.getJavaClass(cls), message, throwable);
    }

    @NotNull
    public final String formatString(@Nullable String str, @NotNull Object... args) {
        String str2;
        Intrinsics.checkNotNullParameter(args, "args");
        if (str == null) {
            return "";
        }
        if (args.length == 0) {
            return str;
        }
        try {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            Object[] objArrCopyOf = Arrays.copyOf(args, args.length);
            String str3 = String.format(null, str, Arrays.copyOf(objArrCopyOf, objArrCopyOf.length));
            Intrinsics.checkNotNullExpressionValue(str3, "format(locale, format, *args)");
            str2 = str3;
        } catch (Throwable th) {
            str2 = str;
        }
        return str2;
    }

    @NotNull
    public final String getVerifiedTag(@NotNull Class<?> cls) {
        Intrinsics.checkNotNullParameter(cls, "cls");
        return verifyTag(cls.getSimpleName());
    }

    private final String verifyTag(String tag) {
        if (tag != null) {
            String $this$trim$iv$iv = tag;
            int startIndex$iv$iv = 0;
            int endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
            boolean startFound$iv$iv = false;
            while (startIndex$iv$iv <= endIndex$iv$iv) {
                int index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
                char it = $this$trim$iv$iv.charAt(index$iv$iv);
                boolean match$iv$iv = Intrinsics.compare(it, 32) <= 0;
                if (startFound$iv$iv) {
                    if (!match$iv$iv) {
                        break;
                    }
                    endIndex$iv$iv--;
                } else if (match$iv$iv) {
                    startIndex$iv$iv++;
                } else {
                    startFound$iv$iv = true;
                }
            }
            if (!TextUtils.isEmpty($this$trim$iv$iv.subSequence(startIndex$iv$iv, endIndex$iv$iv + 1).toString())) {
                return tag;
            }
        }
        String str = TAG;
        Intrinsics.checkNotNullExpressionValue(str, "TAG");
        return str;
    }
}
