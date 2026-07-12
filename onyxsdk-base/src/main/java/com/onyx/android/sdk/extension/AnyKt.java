package com.onyx.android.sdk.extension;

import com.onyx.android.sdk.utils.TTFFont;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.text.CharsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/extension/AnyKt.class */
@Metadata(mv = {1, 6, 0}, k = 2, xi = 48, d1 = {"\u0000>\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\u001a)\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0004\u0018\u0001H\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004¢\u0006\u0002\u0010\u0005\u001a#\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0004\u0018\u0001H\u00012\u0006\u0010\u0003\u001a\u0002H\u0001¢\u0006\u0002\u0010\u0006\u001a-\u0010\u0007\u001a\u0004\u0018\u0001H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0004\u0018\u0001H\u00012\u000e\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00010\u0004¢\u0006\u0002\u0010\u0005\u001a\n\u0010\b\u001a\u00020\t*\u00020\u0002\u001a;\u0010\n\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0002H\u00012\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\r0\f2\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004¢\u0006\u0002\u0010\u000e\u001a/\u0010\u000f\u001a\u00020\r\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0004\u0018\u0001H\u0001H\u0086\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\b\u0003\u0010\u0000¢\u0006\u0002\u0010\u0010\u001a/\u0010\u0011\u001a\u00020\r\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0004\u0018\u0001H\u0001H\u0086\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\b\u0002\u0010\u0000¢\u0006\u0002\u0010\u0010\u001aG\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00140\u0013\"\u0004\b\u0000\u0010\u0001\"\u0004\b\u0001\u0010\u0014*\u0002H\u00012\b\b\u0002\u0010\u0015\u001a\u00020\r2\u0017\u0010\u0016\u001a\u0013\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00140\f¢\u0006\u0002\b\u0017ø\u0001\u0000¢\u0006\u0002\u0010\u0018\u001a\n\u0010\u0019\u001a\u00020\t*\u00020\u0002\u001a3\u0010\u001a\u001a\u0002H\u0014\"\u0004\b\u0000\u0010\u0001\"\b\b\u0001\u0010\u0014*\u00020\u0002*\u0002H\u00012\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00140\f¢\u0006\u0002\u0010\u001c\u001a/\u0010\u001d\u001a\u00020\u001e\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0004\u0018\u0001H\u00012\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u001e0\f¢\u0006\u0002\u0010 \u0082\u0002\u0004\n\u0002\b\u0019¨\u0006!"}, d2 = {"getOrElse", "T", TTFFont.UNKNOWN_FONT_NAME, "defaultValue", "Lkotlin/Function0;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "getOrElseNull", "hashCodeHexString", TTFFont.UNKNOWN_FONT_NAME, "ifEmpty", "emptyPredicate", "Lkotlin/Function1;", TTFFont.UNKNOWN_FONT_NAME, "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNotNull", "(Ljava/lang/Object;)Z", "isNull", "runCatching", "Lkotlin/Result;", "R", "log", "block", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/Object;ZLkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "toSimpleNameString", "transform", "map", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "whenNotNull", TTFFont.UNKNOWN_FONT_NAME, "callback", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "onyxsdk-base_release"})
public final class AnyKt {

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/extension/AnyKt$a.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "T", TTFFont.UNKNOWN_FONT_NAME, "invoke", "()Ljava/lang/Object;"})
    static final class a<T> implements Function0<T> {
        final /* synthetic */ T a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(T t) {
            this.a = t;
        }

        @NotNull
        public final T invoke() {
            return this.a;
        }
    }

    @NotNull
    public static final <T> T getOrElse(@Nullable T t, @NotNull Function0<? extends T> function0) {
        Intrinsics.checkNotNullParameter(function0, "defaultValue");
        if (t == null) {
            t = (T) function0.invoke();
        }
        return t;
    }

    @Nullable
    public static final <T> T getOrElseNull(@Nullable T t, @NotNull Function0<? extends T> function0) {
        Intrinsics.checkNotNullParameter(function0, "defaultValue");
        if (t == null) {
            t = (T) function0.invoke();
        }
        return t;
    }

    public static final <T> boolean isNull(@Nullable T t) {
        return t == null;
    }

    public static final <T> boolean isNotNull(@Nullable T t) {
        return t != null;
    }

    public static final <T> void whenNotNull(@Nullable T t, @NotNull Function1<? super T, Unit> function1) {
        Intrinsics.checkNotNullParameter(function1, "callback");
        if (t == null) {
            return;
        }
        function1.invoke(t);
    }

    @NotNull
    public static final <T, R> R transform(T t, @NotNull Function1<? super T, ? extends R> function1) {
        Intrinsics.checkNotNullParameter(function1, "map");
        return (R) function1.invoke(t);
    }

    @NotNull
    public static final String hashCodeHexString(@NotNull Object $this$hashCodeHexString) {
        Intrinsics.checkNotNullParameter($this$hashCodeHexString, "<this>");
        String string = Integer.toString($this$hashCodeHexString.hashCode(), CharsKt.checkRadix(16));
        Intrinsics.checkNotNullExpressionValue(string, "toString(this, checkRadix(radix))");
        return string;
    }

    @NotNull
    public static final String toSimpleNameString(@NotNull Object $this$toSimpleNameString) {
        Intrinsics.checkNotNullParameter($this$toSimpleNameString, "<this>");
        return ClassKt.getCompatSimpleName($this$toSimpleNameString.getClass()) + '@' + hashCodeHexString($this$toSimpleNameString);
    }

    @NotNull
    public static final <T, R> Object runCatching(T t, boolean log, @NotNull Function1<? super T, ? extends R> function1) {
        Intrinsics.checkNotNullParameter(function1, "block");
        try {
            return function1.invoke(t);
        } catch (Throwable failure) {
            if (log) {
                failure.printStackTrace();
            }
            return ResultKt.createFailure(failure);
        }
    }

    public static /* synthetic */ Object runCatching$default(Object obj, boolean z, Function1 function1, int i, Object obj2) {
        if ((i & 1) != 0) {
            z = true;
        }
        return runCatching(obj, z, function1);
    }

    @NotNull
    public static final <T> T ifEmpty(@NotNull T t, @NotNull Function1<? super T, Boolean> function1, @NotNull Function0<? extends T> function0) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(function1, "emptyPredicate");
        Intrinsics.checkNotNullParameter(function0, "defaultValue");
        return ((Boolean) function1.invoke(t)).booleanValue() ? (T) function0.invoke() : t;
    }

    @NotNull
    public static final <T> T getOrElse(@Nullable T t, @NotNull T t2) {
        Intrinsics.checkNotNullParameter(t2, "defaultValue");
        return (T) getOrElse((Object) t, (Function0) new a(t2));
    }
}
