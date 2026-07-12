package com.onyx.android.sdk.base.lite.extension;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv = {1, 6, 0}, k = 2, xi = 48, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002*\u0016\u0010\u0003\"\b\u0012\u0004\u0012\u00020\u00050\u00042\b\u0012\u0004\u0012\u00020\u00050\u0004*(\u0010\u0006\u001a\u0004\b\u0000\u0010\u0007\"\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u00020\u00050\b2\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u00020\u00050\b¨\u0006\t"}, d2 = {"toIntList", "Lcom/onyx/android/sdk/base/lite/extension/IntList;", "", "NoArgCallbackFunc", "Lkotlin/Function0;", "", "SingleArgCallbackFunc", "T", "Lkotlin/Function1;", "sdk-baselite_release"})
public final class TypeExtKt {
    @NotNull
    public static final IntList toIntList(@Nullable int[] $this$toIntList) {
        IntList $this$toIntList_u24lambda_u2d0 = new IntList();
        if ($this$toIntList != null) {
            $this$toIntList_u24lambda_u2d0.addAll(ArraysKt.toList($this$toIntList));
        }
        return $this$toIntList_u24lambda_u2d0;
    }
}
