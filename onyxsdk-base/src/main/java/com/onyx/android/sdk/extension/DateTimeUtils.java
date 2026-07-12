// 
// 

package com.onyx.android.sdk.extension;

import kotlin.jvm.JvmStatic;
import com.onyx.android.sdk.utils.DateTimeUtil;
import org.jetbrains.annotations.Nullable;
import java.util.Date;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\f\u0010\u0007\u001a\u00020\u0006*\u0004\u0018\u00010\bJ\u000e\u0010\t\u001a\u00020\u0004*\u0004\u0018\u00010\bH\u0007¨\u0006\n" }, d2 = { "Lcom/onyx/android/sdk/extension/DateTimeUtils;", "", "()V", "getDateAndTimeString", "", "time", "", "getSafeTime", "Ljava/util/Date;", "getSafelyDateAndTimeString", "onyxsdk-base_release" })
public final class DateTimeUtils
{
    @NotNull
    public static final DateTimeUtils INSTANCE;
    
    private DateTimeUtils() {
    }
    
    @JvmStatic
    @NotNull
    public static final String getSafelyDateAndTimeString(@Nullable final Date $this$getSafelyDateAndTimeString) {
        return "time = " + DateTimeUtils.INSTANCE.getSafeTime($this$getSafelyDateAndTimeString) + ", date = " + (Object)DateTimeUtil.formatDate($this$getSafelyDateAndTimeString);
    }
    
    @JvmStatic
    @NotNull
    public static final String getDateAndTimeString(final long time) {
        return "time = " + time + ", date = " + (Object)DateTimeUtil.formatDate(new Date(time));
    }
    
    static {
        INSTANCE = new DateTimeUtils();
    }
    
    public final long getSafeTime(@Nullable final Date $this$getSafeTime) {
        return ($this$getSafeTime == null) ? 0L : $this$getSafeTime.getTime();
    }
}
