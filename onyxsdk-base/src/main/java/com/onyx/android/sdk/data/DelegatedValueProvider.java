package com.onyx.android.sdk.data;

import com.onyx.android.sdk.utils.TTFFont;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/DelegatedValueProvider.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\bæ\u0080\u0001\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003J\"\u0010\u0004\u001a\u00028\u00012\u0006\u0010\u0005\u001a\u00028\u00002\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007H\u0096\u0002¢\u0006\u0002\u0010\bJ\u0011\u0010\t\u001a\u00028\u0001*\u00028\u0000H&¢\u0006\u0002\u0010\n¨\u0006\u000b"}, d2 = {"Lcom/onyx/android/sdk/data/DelegatedValueProvider;", "CONTEXT", "RESULT", TTFFont.UNKNOWN_FONT_NAME, "getValue", "context", "property", "Lkotlin/reflect/KProperty;", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "provideValue", "(Ljava/lang/Object;)Ljava/lang/Object;", "onyxsdk-base_release"})
public interface DelegatedValueProvider<CONTEXT, RESULT> {

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/DelegatedValueProvider$DefaultImpls.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
        private DefaultImpls() {
        }

        public static <CONTEXT, RESULT> RESULT getValue(@NotNull DelegatedValueProvider<CONTEXT, RESULT> delegatedValueProvider, CONTEXT context, @NotNull KProperty<?> kProperty) {
            Intrinsics.checkNotNullParameter(delegatedValueProvider, "this");
            Intrinsics.checkNotNullParameter(kProperty, "property");
            return delegatedValueProvider.provideValue(context);
        }
    }

    RESULT provideValue(CONTEXT context);

    RESULT getValue(CONTEXT context, @NotNull KProperty<?> property);
}
