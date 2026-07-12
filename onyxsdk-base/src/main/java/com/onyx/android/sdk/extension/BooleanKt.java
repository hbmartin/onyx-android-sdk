// 
// 

package com.onyx.android.sdk.extension;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.Nullable;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\u001a\u001f\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004¢\u0006\u0002\u0010\u0005\u001a\u001f\u0010\u0006\u001a\u00020\u0001*\u0004\u0018\u00010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004¢\u0006\u0002\u0010\u0005\u001a\u0011\u0010\u0007\u001a\u00020\b*\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\t\u001a\u0011\u0010\n\u001a\u00020\b*\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\t¨\u0006\u000b" }, d2 = { "takeIfFalse", "", "", "action", "Lkotlin/Function0;", "(Ljava/lang/Boolean;Lkotlin/jvm/functions/Function0;)V", "takeIfTrue", "toInteger", "", "(Ljava/lang/Boolean;)I", "toSignInteger", "onyxsdk-base_release" })
public final class BooleanKt
{
    private BooleanKt() {
    }

    public static final void takeIfTrue(@Nullable Boolean value, @NotNull final Function0<Unit> action) {
        Intrinsics.checkNotNullParameter((Object)action, "action");
        if (Boolean.TRUE.equals(value)) {
            action.invoke();
        }
    }
    
    public static final void takeIfFalse(@Nullable Boolean value, @NotNull final Function0<Unit> action) {
        Intrinsics.checkNotNullParameter((Object)action, "action");
        if (Boolean.FALSE.equals(value)) {
            action.invoke();
        }
    }
    
    public static final int toInteger(@Nullable final Boolean $this$toInteger) {
        return Intrinsics.areEqual((Object)$this$toInteger, (Object)Boolean.TRUE) ? 1 : 0;
    }
    
    public static final int toSignInteger(@Nullable final Boolean $this$toSignInteger) {
        return Intrinsics.areEqual((Object)$this$toSignInteger, (Object)Boolean.TRUE) ? 1 : -1;
    }
}
