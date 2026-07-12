// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.calendar;

import android.content.ComponentName;
import android.content.Intent;
import android.os.IInterface;
import android.os.IBinder;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import android.content.Context;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;
import com.onyx.android.sdk.common.service.OnyxRemoteServiceConnection;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \t2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\tB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\bH\u0014¨\u0006\n" }, d2 = { "Lcom/onyx/android/sdk/calendar/CalendarRemoteServiceConnection;", "Lcom/onyx/android/sdk/common/service/OnyxRemoteServiceConnection;", "Lcom/onyx/android/sdk/calendar/ICalendarService;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "asInterface", "serviceBinder", "Landroid/os/IBinder;", "Companion", "onyxsdk-base_release" })
public final class CalendarRemoteServiceConnection extends OnyxRemoteServiceConnection<ICalendarService>
{
    @NotNull
    public static final Companion Companion;
    @NotNull
    private static final String f = "com.onyx.mail";
    @NotNull
    private static final String g = "com.onyx.mail.calendar.service.CalendarRemoteService";
    
    public CalendarRemoteServiceConnection(@NotNull final Context context) {
        super(context, CalendarRemoteServiceConnection.Companion.getServiceIntent());
        Intrinsics.checkNotNullParameter((Object)context, "context");
    }
    
    static {
        Companion = new Companion(null);
    }
    
    @NotNull
    @Override
    protected ICalendarService asInterface(@NotNull final IBinder serviceBinder) {
        Intrinsics.checkNotNullParameter((Object)serviceBinder, "serviceBinder");
        final ICalendarService interface1 = ICalendarService.Stub.asInterface(serviceBinder);
        Intrinsics.checkNotNullExpressionValue((Object)interface1, "asInterface(serviceBinder)");
        return interface1;
    }
    
    @Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0006\u001a\u00020\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\b" }, d2 = { "Lcom/onyx/android/sdk/calendar/CalendarRemoteServiceConnection$Companion;", "", "()V", "KMAIL_PACKAGE_NAME", "", "SERVICE_PACKAGE_NAME", "getServiceIntent", "Landroid/content/Intent;", "onyxsdk-base_release" })
    public static final class Companion
    {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker marker) {
            this();
        }
        
        @NotNull
        public final Intent getServiceIntent() {
            final Intent setComponent = new Intent().setComponent(new ComponentName("com.onyx.mail", "com.onyx.mail.calendar.service.CalendarRemoteService"));
            Intrinsics.checkNotNullExpressionValue((Object)setComponent, "Intent()\n               \u2026E, SERVICE_PACKAGE_NAME))");
            return setComponent;
        }
    }
}
