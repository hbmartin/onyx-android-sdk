// 
// 

package com.onyx.android.sdk.extension;

import com.onyx.android.sdk.utils.Debug;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\u000e\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0002\b\u0003\u0018\u00010\u0002\u001a\u0010\u0010\u0003\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u0002H\u0002¨\u0006\u0004" }, d2 = { "getCompatSimpleName", "", "Ljava/lang/Class;", "stripPackageName", "onyxsdk-base_release" })
public final class ClassKt
{
    private ClassKt() {
    }

    @NotNull
    public static final String getCompatSimpleName(@Nullable final Class<?> $this$getCompatSimpleName) {
        if ($this$getCompatSimpleName == null) {
            return "null cls";
        }
        final String simpleName;
        if ((simpleName = $this$getCompatSimpleName.getSimpleName()) == null || simpleName.length() == 0) {
            return a($this$getCompatSimpleName);
        }
        final String simpleName2 = $this$getCompatSimpleName.getSimpleName();
        Intrinsics.checkNotNullExpressionValue((Object)simpleName2, "simpleName");
        return simpleName2;
    }
    
    private static final String a(Class<?> $this$stripPackageName) {
        String name = $this$stripPackageName.getName();
        try {
            int separator = name.lastIndexOf('.');
            if (separator > 0) {
                return name.substring(separator + 1);
            }
        } catch (Exception failure) {
            Debug.e(failure);
        }
        return name;
    }
}
