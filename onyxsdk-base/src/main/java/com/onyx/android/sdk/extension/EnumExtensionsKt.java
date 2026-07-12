// 
// 

package com.onyx.android.sdk.extension;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\u0012\n\u0002\b\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a*\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\u0010\b\u0000\u0010\u0001\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0086\b¢\u0006\u0002\u0010\u0005¨\u0006\u0006" }, d2 = { "enumValueOfOrNull", "T", "", "name", "", "(Ljava/lang/String;)Ljava/lang/Enum;", "onyxsdk-base_release" })
public final class EnumExtensionsKt
{
    private EnumExtensionsKt() {
    }

    @Nullable
    public static final <T extends Enum<T>> T enumValueOfOrNull(@NotNull final String name) {
        Intrinsics.checkNotNullParameter((Object)name, "name");
        Enum value;
        try {
            Intrinsics.reifiedOperationMarker(5, "T");
            value = Enum.valueOf((Class)null, name);
        }
        catch (final IllegalArgumentException ex) {
            value = null;
        }
        return (T)value;
    }
}
