// 
// 

package com.onyx.android.sdk.utils;

import org.jetbrains.annotations.Nullable;
import kotlin.jvm.internal.Intrinsics;
import com.alibaba.fastjson2.JSONObject;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0004J\u0014\u0010\t\u001a\u0004\u0018\u00010\u0007*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\n" }, d2 = { "Lcom/onyx/android/sdk/utils/JSONUtilsKt;", "", "()V", "getNullableInt", "", "Lcom/alibaba/fastjson2/JSONObject;", "key", "", "default", "getNullableString", "onyxsdk-base_release" })
public final class JSONUtilsKt
{
    @NotNull
    public static final JSONUtilsKt INSTANCE;
    
    private JSONUtilsKt() {
    }
    
    static {
        INSTANCE = new JSONUtilsKt();
    }
    
    @Nullable
    public final String getNullableString(@NotNull final JSONObject $this$getNullableString, @NotNull final String key) {
        Intrinsics.checkNotNullParameter((Object)$this$getNullableString, "<this>");
        Intrinsics.checkNotNullParameter((Object)key, "key");
        return $this$getNullableString.containsKey(key) ? $this$getNullableString.getString(key) : null;
    }
    
    public final int getNullableInt(@NotNull final JSONObject $this$getNullableInt, @NotNull final String key, int defaultValue) {
        Intrinsics.checkNotNullParameter((Object)$this$getNullableInt, "<this>");
        Intrinsics.checkNotNullParameter((Object)key, "key");
        if ($this$getNullableInt.containsKey(key)) {
            final Integer integer = $this$getNullableInt.getInteger(key);
            Intrinsics.checkNotNullExpressionValue((Object)integer, "getInteger(key)");
            defaultValue = integer.intValue();
        }
        return defaultValue;
    }
}
