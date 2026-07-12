package com.onyx.android.sdk.global.inversion;

import android.content.Context;
import androidx.annotation.MainThread;
import com.onyx.android.sdk.rx.rxcontentobserver.RxContentObserver;
import com.onyx.android.sdk.rx.rxcontentobserver.RxContentSettingsType;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.TTFFont;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/global/inversion/SystemInversionColorProvider.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0018B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0003J\b\u0010\u0011\u001a\u00020\u0012H\u0007J\u0010\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\tH\u0007J\u0010\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u0004H\u0003J\u0010\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\tH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/onyx/android/sdk/global/inversion/SystemInversionColorProvider;", TTFFont.UNKNOWN_FONT_NAME, "()V", "ENABLE_VALUE", TTFFont.UNKNOWN_FONT_NAME, "key", TTFFont.UNKNOWN_FONT_NAME, "notifyListeners", TTFFont.UNKNOWN_FONT_NAME, "Lcom/onyx/android/sdk/global/inversion/SystemInversionColorProvider$InversionColorNotifyListener;", "systemInversionState", "Lcom/onyx/android/sdk/global/inversion/SystemInversionState;", "init", TTFFont.UNKNOWN_FONT_NAME, "ctx", "Landroid/content/Context;", "initState", "isSystemInversionColor", TTFFont.UNKNOWN_FONT_NAME, "register", "listener", "setAndNotifySystemState", "value", "unregister", "InversionColorNotifyListener", "onyxsdk-base_release"})
public final class SystemInversionColorProvider {

    @NotNull
    private static final String a = "accessibility_display_inversion_enabled";
    private static final int b = 1;

    @NotNull
    public static final SystemInversionColorProvider INSTANCE = new SystemInversionColorProvider();

    @NotNull
    private static volatile SystemInversionState c = new SystemInversionState(false);

    @NotNull
    private static final Set<InversionColorNotifyListener> d = new LinkedHashSet();

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/global/inversion/SystemInversionColorProvider$InversionColorNotifyListener.class */
    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"}, d2 = {"Lcom/onyx/android/sdk/global/inversion/SystemInversionColorProvider$InversionColorNotifyListener;", TTFFont.UNKNOWN_FONT_NAME, "onChanged", TTFFont.UNKNOWN_FONT_NAME, "onyxsdk-base_release"})
    public interface InversionColorNotifyListener {
        void onChanged();
    }

    private SystemInversionColorProvider() {
    }

    @JvmStatic
    public static final void init(@NotNull Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        INSTANCE.a(ctx);
        new RxContentObserver().buildForInt(ctx, RxContentSettingsType.SECURE, a).doOnNext(SystemInversionColorProvider::a).subscribe();
    }

    @JvmStatic
    public static final boolean isSystemInversionColor() {
        return c.isInversion();
    }

    private static final void a(Integer it) {
        SystemInversionColorProvider systemInversionColorProvider = INSTANCE;
        Intrinsics.checkNotNullExpressionValue(it, "it");
        systemInversionColorProvider.a(it.intValue());
    }

    @MainThread
    public final void register(@NotNull InversionColorNotifyListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        d.add(listener);
    }

    @MainThread
    public final void unregister(@NotNull InversionColorNotifyListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        d.remove(listener);
    }

    @MainThread
    private final void a(Context ctx) {
        try {
            a(RxContentSettingsType.getInt(ctx.getContentResolver(), RxContentSettingsType.SECURE, a));
        } catch (Exception exception) {
            SystemInversionColorProvider.<RuntimeException>sneakyThrow(exception);
        }
    }

    @SuppressWarnings("unchecked")
    private static <E extends Throwable> void sneakyThrow(Throwable failure) throws E {
        throw (E) failure;
    }

    @MainThread
    private final void a(int value) {
        Debug.d(SystemInversionColorProvider.class, Intrinsics.stringPlus("setAndNotifySystemState ", Integer.valueOf(value)), new Object[0]);
        c = new SystemInversionState(value == 1);
        Iterator<InversionColorNotifyListener> it = d.iterator();
        while (it.hasNext()) {
            it.next().onChanged();
        }
    }
}
