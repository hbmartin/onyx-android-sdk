// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import android.net.ConnectivityManager;
import com.onyx.android.sdk.rx.RxRequest;
import androidx.annotation.Nullable;
import android.os.Bundle;
import com.alibaba.fastjson2.JSON;
import java.util.Collection;
import java.util.Iterator;
import android.os.Build;
import android.net.NetworkInfo;
import androidx.annotation.NonNull;
import com.onyx.android.sdk.rx.RxCallback;
import android.os.Parcelable;
import android.content.ComponentName;
import com.onyx.android.sdk.device.EnvironmentUtil;
import android.content.Intent;
import com.onyx.android.sdk.api.utils.NetworkUtil;
import android.content.Context;
import android.util.Log;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import com.onyx.android.sdk.rx.RxManager;
import android.content.IntentFilter;
import java.util.List;
import java.util.Map;
import android.os.FileObserver;
import android.content.BroadcastReceiver;

public class DeviceReceiver extends BroadcastReceiver
{
    private static final String F;
    public static final String TRIGGER = "trigger_notification";
    public static final String PARSE_PUSH_NOTIFICATION = "com.onyx.parsePushNotification";
    public static final String NO_SAVED_NETWORK_CONNECTED_ACTION = "action.no.saved.network.connected";
    public static final String START_ONYX_SETTINGS = "start_onyx_settings";
    public static final String OPEN_DOCUMENT_ACTION = "com.onyx.open";
    public static final String MTP_EVENT_ACTION = "com.onyx.action.MTP_EVENT_ACTION";
    public static final String ACTION_USB_STATE = "android.hardware.usb.action.USB_STATE";
    public static final String SYSTEM_UI_DIALOG_OPEN_ACTION = "com.android.systemui.SYSTEM_UI_DIALOG_OPEN_ACTION";
    public static final String SYSTEM_UI_DIALOG_CLOSE_ACTION = "com.android.systemui.SYSTEM_UI_DIALOG_CLOSE_ACTION";
    public static final String DIALOG_TYPE = "dialog_type";
    public static final String DIALOG_TYPE_BRIGHTNESS = "dialog_type_brightness";
    public static final String DIALOG_TYPE_USB_STORAGE_CONNECTION = "dialog_type_usb_storage_connection";
    public static final String DIALOG_TYPE_NOTIFICATION_PANEL = "dialog_type_notification_panel";
    public static final String DIALOG_TYPE_VOLUME = "dialog_type_volume";
    public static final String DIALOG_TYPE_LOW_BATTERY_WARNING = "dialog_type_low_battery_Warning";
    public static final String DIALOG_TYPE_POWER_OFF = "dialog_type_power_off";
    public static final String DIALOG_TYPE_SYSTEM_ERROR = "dialog_type_system_error";
    public static final String DIALOG_TYPE_ACTIVE_PEN = "dialog_type_active_pen";
    public static final String STATUS_BAR_ICON_REFRESH_START_ACTION = "com.android.systemui.STATUS_BAR_ICON_REFRESH_START_ACTION";
    public static final String STATUS_BAR_ICON_REFRESH_FINISH_ACTION = "com.android.systemui.STATUS_BAR_ICON_REFRESH_FINISH_ACTION";
    public static final String STATUS_BAR_SHOW_ACTION = "com.android.systemui.STATUS_BAR_SHOW_ACTION";
    public static final String STATUS_BAR_HIDE_ACTION = "com.android.systemui.STATUS_BAR_HIDE_ACTION";
    public static final String ONYX_KEYBOARD_SHOW = "com.onyx.ime.show";
    public static final String ONYX_KEYBOARD_HIDE = "com.onyx.ime.hide";
    public static final String SYSTEM_WAKE_UP = "com.android.system.WAKE_UP";
    public static final String SYSTEM_HOME = "com.android.systemui.HOME_BUTTON_CLICK";
    public static final String SYSTEM_UI_SCREEN_SHOT_START_ACTION = "com.android.systemui.SYSTEM_UI_SCREEN_SHOT_START_ACTION";
    public static final String SYSTEM_UI_SCREEN_SHOT_END_ACTION = "com.android.systemui.SYSTEM_UI_SCREEN_SHOT_END_ACTION";
    public static final String ICON_TYPE = "icon_type";
    public static final String ICON_TYPE_CTP_STATUS_ICON = "icon_type_ctp_status";
    public static final String ICON_TYPE_A2 = "icon_type_a2";
    public static final String TOAST_SHOW_ACTION = "com.android.systemui.TOAST_SHOW_ACTION";
    public static final String TOAST_HIDE_ACTION = "com.android.systemui.TOAST_HIDE_ACTION";
    public static final String ACTION_PRE_ENTER_SPLIT_SCREEN = "com.android.systemui.pre.enter.splitscreen";
    public static final String ACTION_POST_ENTER_SPLIT_SCREEN = "com.android.systemui.post.enter.splitscreen";
    public static final String SPLIT_SCREEN_PRE_EXCHANGE = "com.android.systemui.pre.position.exchanged";
    public static final String SPLIT_SCREEN_POST_EXCHANGE = "com.android.systemui.post.position.exchanged";
    public static final String ENTER_RECENT_INTERFACE_ACTION = "action.enter.recent.interface";
    public static final String ENTER_KEYGUARD_INTERFACE_ACTION = "action.enter.keyguard.interface";
    public static final String ONYX_RESET_PIN_CODE_ACTION = "action.onyx.reset.pincode";
    public static final String ONYX_FINGERPRINT_REMOVED_ACTION = "onyx.android.intent.action.FINGERPRINT_REMOVED_ACTION";
    public static final String ONYX_PIN_CODE_REMOVED_ACTION = "onyx.android.intent.action.ONYX_PIN_CODE_REMOVED_ACTION";
    public static final String FINGERPRINT_ACTION_LOCKOUT_RESET = "com.android.server.fingerprint.ACTION_LOCKOUT_RESET";
    public static final String ACTION_USB_DEVICE_ATTACHED = "android.hardware.usb.action.USB_DEVICE_ATTACHED";
    public static final String ACTION_USB_DEVICE_DETACHED = "android.hardware.usb.action.USB_DEVICE_DETACHED";
    public static final String ACTION_VOLUME_STATE_CHANGED = "android.os.storage.action.VOLUME_STATE_CHANGED";
    public static final String EXTRA_VOLUME_STATE = "android.os.storage.extra.VOLUME_STATE";
    public static final String NOTIFY_CALIBRATION_FINISH = "notify_calibration_finish";
    public static final String ACTION_SHOW_INPUT_METHOD_PICKER = "com.android.server.InputMethodManagerService.SHOW_INPUT_METHOD_PICKER";
    public static final String ONYX_WIFI_SETTINGS_ACTION = "onyx.settings.action.wifi";
    public static final String ONYX_NOTE_SAVE_QUIT_ACTION = "onyx.note.save.quit.action";
    public static final String ONYX_NOTE_EXPORT_ACTION = "onyx.note.export.action";
    public static final String ONYX_NOTE_OPEN_ACTION = "onyx.note.open.action";
    public static final String ONYX_NOTE_CREATE_ACTION = "onyx.note.create.action";
    public static final String ONYX_NOTE_IMPORT_ACTION = "onyx.note.import.action";
    public static final String NOTE_SETTING_CHANGED_ACTION = "note.setting.changed.action";
    public static final String ONYX_FEEDBACK_ACTION = "com.onyx.FEEDBACK_ACTION";
    public static final String ONYX_IM_MESSAGE_ACTION = "com.onyx.IM_MESSAGE_ACTION";
    public static final String ONYX_UNBIND_DEVICE_ACTION = "com.onyx.UNBIND_DEVICE_ACTION";
    public static final String ONYX_RESET_REFRESH_CONFIG_ACTION = "com.onyx.action.eink.RESET_REFRESH_CONFIG";
    public static final String ONYX_SHOW_REFRESH_SETTINGS_UI_ACTION = "com.onyx.action.eink.SHOW_REFRESH_SETTINGS_UI";
    public static final String SYNC_PROGRESS_UPDATE_ACTION = "sync.progress.update.action";
    public static final String PDF_DOCUMENT_SAVE_QUALITY_ACTION = "pdf.document.quality.action";
    public static final String ACTION_TOKEN_INVALID_ACTION = "token.invalid.action";
    public static final String SERVER_INFO_CHANGED_ACTION = "SERVER_INFO_CHANGED_ACTION";
    public static final String SHOW_LUNAR_CALENDAR_CHANGE_ACTION = "SHOW_LUNAR_CALENDAR_CHANGE_ACTION";
    public static final String USER_SPLIT_HANDLE_ACTION = "onyx.action.user.operate.split.handle";
    public static final String ONYX_MATERIAL_HOST_BASEURL_CHANGED_ACTION = "ONYX_MATERIAL_HOST_BASEURL_CHANGED_ACTION";
    private static FileObserver G;
    private Map<String, String> a;
    private BootCompleteListener b;
    private PushNotificationListener c;
    private NetworkConnectChangedListener d;
    private UmsStateListener e;
    private MediaStateListener f;
    private FileSystemListener g;
    private SettingsListener h;
    private OpenDocumentListener i;
    private LocaleChangedListener j;
    private BluetoothStateListener k;
    private MtpEventListener l;
    private UsbStateListener m;
    private SystemUIChangeListener n;
    private WiFiStateChangedListener o;
    private ResetPinCodeListener p;
    private SystemKeyguardChangedListener q;
    private UsbDeviceStateChangeListener r;
    private VolumeStateChangeListener s;
    private IMMessageListener t;
    private TokenInvalidListener u;
    private UnbindDeviceListener v;
    private CalibrationListener w;
    private StatisticsPushListener x;
    private ExtraIntentReceiver y;
    private List<IntentFilter> z;
    private boolean A;
    private List<String> B;
    private RxManager C;
    private TetherStateChangedListener D;
    private DebugAppCrashListener E;
    
    public DeviceReceiver() {
        this.a = new HashMap<String, String>();
        this.z = new ArrayList<IntentFilter>();
        this.B = Arrays.asList("dialog_type_volume", "dialog_type_active_pen");
    }
    
    private void h() {
        final FileObserver g;
        if ((g = DeviceReceiver.G) != null) {
            g.stopWatching();
        }
        (DeviceReceiver.G = new FileObserver("/mnt/sdcard/Books") {
            public void onEvent(final int i, final String s) {
                Log.i(DeviceReceiver.F, "Received file changed event: " + i + " " + s);
            }
        }).startWatching();
        Log.i(DeviceReceiver.F, "start file observer");
    }
    
    private void c(final Context context) {
        this.A = NetworkUtil.isWiFiConnected(context.getApplicationContext());
    }
    
    private void e(final Context context, final Intent intent) {
        final TetherStateChangedListener d;
        if ((d = this.D) != null) {
            d.onTetherStateChanged(context, intent);
        }
    }
    
    private void k(final Intent intent) {
        final CalibrationListener w;
        if ((w = this.w) != null) {
            w.onInputDeviceCalibrationFinish(intent);
        }
    }
    
    private void l(final Intent intent) {
        final UnbindDeviceListener v;
        if ((v = this.v) != null) {
            v.onUnbindDevice(intent);
        }
    }
    
    private void f(final Context context, final Intent intent) {
        final TokenInvalidListener u;
        if ((u = this.u) != null) {
            u.onTokenInvalid(context, intent);
        }
    }
    
    private void c(final Context context, final Intent intent) {
        final StatisticsPushListener x;
        if ((x = this.x) != null) {
            x.onStatisticsPush(context, intent);
        }
    }
    
    private void d(final boolean attached) {
        final UsbDeviceStateChangeListener r;
        if ((r = this.r) != null) {
            r.onUsbDeviceStateChange(attached);
        }
    }
    
    private void m(final Intent intent) {
        if (this.s != null) {
            this.s.onVolumeStateChange(intent.getIntExtra("android.os.storage.extra.VOLUME_STATE", 0));
        }
    }
    
    private void j(final Intent intent) {
        final IMMessageListener t;
        if ((t = this.t) != null) {
            t.onMessage(intent);
        }
    }
    
    private void b(final Context context, final Intent intent) {
        final ResetPinCodeListener p;
        if ((p = this.p) != null) {
            p.onResetPinCode(context, intent);
        }
    }
    
    private void d(final Context context, final Intent intent) {
        final SystemKeyguardChangedListener q;
        if ((q = this.q) != null) {
            q.onSystemKeyguardChanged(context, intent);
        }
    }
    
    private boolean a(final Context context, final Intent intent) {
        final ExtraIntentReceiver y;
        return (y = this.y) != null && y.onReceive(context, intent);
    }
    
    public static boolean isExternalStorageEvent(final Context context, final Intent intent) {
        return EnvironmentUtil.getExternalStorageDirectory().getAbsolutePath().contains(FileUtils.getRealFilePathFromUri(context, intent.getData()));
    }
    
    private void c(final Intent intent, final boolean open) {
        if (this.getSystemUIChangeListener() == null) {
            return;
        }
        final String stringExtra;
        if (StringUtils.isNotBlank(stringExtra = intent.getStringExtra("icon_type"))) {
            this.getSystemUIChangeListener().onSystemIconChanged(intent.getAction(), stringExtra, open);
        }
        else {
            this.getSystemUIChangeListener().onSystemUIChanged(intent.getAction(), open);
        }
    }
    
    private void a(final Intent intent, final boolean open) {
        if (this.getSystemUIChangeListener() == null) {
            return;
        }
        if (this.B.contains(intent.getStringExtra("dialog_type"))) {
            this.getSystemUIChangeListener().onNoFocusSystemDialogChanged(open);
        }
    }
    
    private void c(final Intent intent) {
        if (this.getSystemUIChangeListener() == null) {
            return;
        }
        this.getSystemUIChangeListener().onHomeClicked();
    }
    
    private void e(final Intent intent) {
        if (this.getSystemUIChangeListener() == null) {
            return;
        }
        this.getSystemUIChangeListener().onResetRefreshConfig();
    }
    
    private void g(final Intent intent) {
        if (this.getSystemUIChangeListener() == null) {
            return;
        }
        this.getSystemUIChangeListener().onShowRefreshSettingsUI();
    }
    
    private void f(final Intent intent) {
        if (this.getSystemUIChangeListener() == null) {
            return;
        }
        this.getSystemUIChangeListener().onRotationChanged(intent);
    }
    
    private void b(final Intent intent, final boolean end) {
        if (this.getSystemUIChangeListener() == null) {
            return;
        }
        this.getSystemUIChangeListener().onScreenShot(intent, end);
    }
    
    private void b(final boolean end) {
        if (this.getSystemUIChangeListener() == null) {
            return;
        }
        this.getSystemUIChangeListener().onRegionScreenShot(end);
    }
    
    private void d(final Intent intent) {
        if (this.getSystemUIChangeListener() == null) {
            return;
        }
        this.getSystemUIChangeListener().onLowBattery(intent);
    }
    
    private void h(final Intent intent) {
        if (this.getSystemUIChangeListener() == null) {
            return;
        }
        this.getSystemUIChangeListener().onShutDown(intent);
    }
    
    private void a(final boolean open) {
        if (this.getSystemUIChangeListener() == null) {
            return;
        }
        this.getSystemUIChangeListener().onOnyxKeyboardChanged(open);
    }
    
    private void a(final Intent intent) {
        if (this.getSystemUIChangeListener() == null) {
            return;
        }
        this.getSystemUIChangeListener().onFloatButtonChanged(intent.getBooleanExtra("floatbutton_status", false));
    }
    
    private void b(final Intent intent) {
        if (this.getSystemUIChangeListener() == null) {
            return;
        }
        this.getSystemUIChangeListener().onFloatButtonMenuStateChanged(intent.getBooleanExtra("floatbutton_menu_state", false));
    }
    
    private void c(final boolean show) {
        if (this.getSystemUIChangeListener() == null) {
            return;
        }
        this.getSystemUIChangeListener().onToastChanged(show);
    }
    
    private void i(final Intent intent) {
        if (this.getSystemUIChangeListener() == null) {
            return;
        }
        final boolean booleanExtra = intent.getBooleanExtra(BroadcastHelper.USER_SPLIT_HANDLE_ARGS_STATUS, false);
        final Parcelable parcelableExtra;
        final Parcelable parcelable = parcelableExtra = intent.getParcelableExtra(BroadcastHelper.USER_SPLIT_HANDLE_ARGS_COMPONENT);
        ComponentName focusedComponent = null;
        if (parcelable != null && parcelableExtra instanceof ComponentName) {
            focusedComponent = (ComponentName)parcelableExtra;
        }
        this.getSystemUIChangeListener().onUserSplitHandle(booleanExtra, focusedComponent);
    }
    
    private void g() {
        if (this.getSystemUIChangeListener() != null) {
            this.getSystemUIChangeListener().onPreExchangeSplitScreen();
        }
    }
    
    private void e() {
        if (this.getSystemUIChangeListener() != null) {
            this.getSystemUIChangeListener().onPostExchangeSplitScreen();
        }
    }
    
    private void f() {
        if (this.getSystemUIChangeListener() != null) {
            this.getSystemUIChangeListener().onPreEnterSplitScreen();
        }
    }
    
    private void d() {
        if (this.getSystemUIChangeListener() != null) {
            this.getSystemUIChangeListener().onPostEnterSplitScreen();
        }
    }
    
    private void b() {
        if (this.getSystemUIChangeListener() == null) {
            return;
        }
        this.getSystemUIChangeListener().onEnterRecent();
    }
    
    private void c() {
        if (this.getSystemUIChangeListener() == null) {
            return;
        }
        this.getSystemUIChangeListener().onReboot();
    }
    
    private void a(final Context context) {
        this.b(context);
        if (this.d == null) {
            return;
        }
        this.getRxManager().enqueue(new d(), new RxCallback<d>() {
            public void onNext(@NonNull final d getNetworkInfoRequest) {
                final boolean wiFiConnected = NetworkUtil.isWiFiConnected(getNetworkInfoRequest.c);
                final boolean b = DeviceReceiver.this.A ^ wiFiConnected;
                DeviceReceiver.this.A = wiFiConnected;
                if (b) {
                    DeviceReceiver.this.onNetworkConnectChanged(wiFiConnected, getNetworkInfoRequest.c);
                }
            }
        });
    }
    
    private void b(final Context context) {
        if (this.o == null) {
            return;
        }
        this.getRxManager().enqueue(new d(), new RxCallback<d>() {
            public void onNext(@NonNull final d getNetworkInfoRequest) {
                final NetworkInfo c;
                if ((c = getNetworkInfoRequest.c) != null) {
                    DeviceReceiver.this.onWiFiStateChanged(context, c);
                }
            }
        });
    }
    
    static {
        F = DeviceReceiver.class.getSimpleName();
    }
    
    public RxManager getRxManager() {
        if (this.C == null) {
            this.C = RxManager.Builder.sharedSingleThreadManager();
        }
        return this.C;
    }
    
    public void initReceiver(final Context context) {
        this.enable(context, true);
    }
    
    public IntentFilter fileFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.onyx.parsePushNotification");
        intentFilter.addAction("android.intent.action.UMS_CONNECTED");
        intentFilter.addAction("android.intent.action.UMS_DISCONNECTED");
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        intentFilter.addAction("android.intent.action.MEDIA_CHECKING");
        intentFilter.addAction("android.intent.action.MEDIA_SCANNER_STARTED");
        intentFilter.addAction("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intentFilter.addAction("android.intent.action.MEDIA_SCANNER_FINISHED");
        intentFilter.addAction("android.intent.action.MEDIA_BAD_REMOVAL");
        intentFilter.addAction("android.intent.action.MEDIA_MOUNTED");
        intentFilter.addAction("android.intent.action.MEDIA_UNMOUNTED");
        intentFilter.addAction("android.intent.action.MEDIA_REMOVED");
        intentFilter.addAction("android.intent.action.MEDIA_SHARED");
        intentFilter.addAction("com.onyx.action.MTP_EVENT_ACTION");
        intentFilter.addDataScheme("file");
        return intentFilter;
    }
    
    public IntentFilter systemFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter.addAction("com.onyx.parsePushNotification");
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        intentFilter.addAction("android.hardware.usb.action.USB_STATE");
        intentFilter.addAction("com.onyx.action.MTP_EVENT_ACTION");
        intentFilter.addAction("android.intent.action.REBOOT");
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        intentFilter.addAction("android.os.storage.action.VOLUME_STATE_CHANGED");
        intentFilter.addAction("notify_calibration_finish");
        return intentFilter;
    }
    
    public IntentFilter settingsFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("trigger_notification");
        intentFilter.addAction("start_onyx_settings");
        intentFilter.addAction("action.onyx.reset.pincode");
        intentFilter.addAction("onyx.android.intent.action.FINGERPRINT_REMOVED_ACTION");
        intentFilter.addAction("onyx.android.intent.action.ONYX_PIN_CODE_REMOVED_ACTION");
        intentFilter.addAction("token.invalid.action");
        return intentFilter;
    }
    
    public IntentFilter openDocumentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.onyx.open");
        return intentFilter;
    }
    
    public IntentFilter statisticsFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("onyx.action.action_push_statistics");
        return intentFilter;
    }
    
    public IntentFilter systemUIFilter() {
        final IntentFilter intentFilter2;
        final IntentFilter intentFilter = intentFilter2 = new IntentFilter();
        intentFilter.addAction("com.android.systemui.SYSTEM_UI_DIALOG_OPEN_ACTION");
        intentFilter.addAction("com.android.systemui.SYSTEM_UI_DIALOG_CLOSE_ACTION");
        String s;
        if (Build.VERSION.SDK_INT >= 19) {
            s = "android.intent.action.SCREEN_ON";
        }
        else {
            s = "com.android.system.WAKE_UP";
        }
        final IntentFilter intentFilter3 = intentFilter2;
        intentFilter3.addAction(s);
        intentFilter3.addAction("com.android.systemui.HOME_BUTTON_CLICK");
        intentFilter3.addAction("com.android.systemui.STATUS_BAR_ICON_REFRESH_START_ACTION");
        intentFilter3.addAction("com.android.systemui.STATUS_BAR_ICON_REFRESH_FINISH_ACTION");
        intentFilter3.addAction("com.android.systemui.SYSTEM_UI_SCREEN_SHOT_START_ACTION");
        intentFilter3.addAction("onyx.action.ROTATION_CHANGED");
        intentFilter3.addAction("com.android.systemui.SYSTEM_UI_SCREEN_SHOT_END_ACTION");
        intentFilter3.addAction("onyx.action.screenshot.region.select.start");
        intentFilter3.addAction("onyx.action.screenshot.region.select.end");
        intentFilter3.addAction("android.intent.action.BATTERY_LOW");
        intentFilter3.addAction("android.intent.action.ACTION_SHUTDOWN");
        intentFilter3.addAction("com.onyx.ime.show");
        intentFilter3.addAction("com.onyx.ime.hide");
        intentFilter3.addAction("com.onyx.floatingbutton.touch");
        intentFilter3.addAction("com.onyx.floatingbutton.menu_state_changed");
        intentFilter3.addAction("com.android.systemui.TOAST_SHOW_ACTION");
        intentFilter3.addAction("com.android.systemui.TOAST_HIDE_ACTION");
        intentFilter3.addAction("action.enter.recent.interface");
        intentFilter3.addAction("android.intent.action.USER_PRESENT");
        intentFilter3.addAction("action.enter.keyguard.interface");
        intentFilter3.addAction("com.android.systemui.STATUS_BAR_SHOW_ACTION");
        intentFilter3.addAction("com.android.systemui.STATUS_BAR_HIDE_ACTION");
        intentFilter3.addAction("com.onyx.action.eink.RESET_REFRESH_CONFIG");
        intentFilter3.addAction("com.onyx.action.eink.SHOW_REFRESH_SETTINGS_UI");
        intentFilter3.addAction("onyx.action.user.operate.split.handle");
        intentFilter3.addAction("com.android.systemui.pre.enter.splitscreen");
        intentFilter3.addAction("com.android.systemui.post.enter.splitscreen");
        intentFilter3.addAction("com.android.systemui.pre.position.exchanged");
        intentFilter3.addAction("com.android.systemui.post.position.exchanged");
        return intentFilter3;
    }
    
    public IntentFilter imFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.onyx.IM_MESSAGE_ACTION");
        intentFilter.addAction("com.onyx.UNBIND_DEVICE_ACTION");
        return intentFilter;
    }
    
    public IntentFilter debugFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.onyx.debug.app.crash");
        return intentFilter;
    }
    
    public void installPushCallback(final Context context) {
    }
    
    public void enable(final Context context, final boolean enable) {
        try {
            this.c(context);
            if (enable) {
                BroadcastHelper.ensureRegisterReceiver(context, this, this.fileFilter());
                BroadcastHelper.ensureRegisterReceiver(context, this, this.settingsFilter());
                BroadcastHelper.ensureRegisterReceiver(context, this, this.systemFilter());
                BroadcastHelper.ensureRegisterReceiver(context, this, this.openDocumentFilter());
                BroadcastHelper.ensureRegisterReceiver(context, this, this.systemUIFilter());
                BroadcastHelper.ensureRegisterReceiver(context, this, this.netWorkFilter());
                BroadcastHelper.ensureRegisterReceiver(context, this, this.imFilter());
                BroadcastHelper.ensureRegisterReceiver(context, this, this.statisticsFilter());
                BroadcastHelper.ensureRegisterReceiver(context, this, this.tetherFilter());
                BroadcastHelper.ensureRegisterReceiver(context, this, this.debugFilter());
                this.registerExtraIntent(context);
                this.installPushCallback(context);
            }
            else {
                BroadcastHelper.ensureUnregisterReceiver(context, this);
            }
        }
        catch (final Exception ex) {}
        this.a.clear();
    }
    
    public void registerExtraIntent(final Context context) {
        for (IntentFilter intentFilter : this.z) {
            if (intentFilter.countActions() == 0) {
                Debug.w((Class) getClass(), "intentFilter, no actions.", new Object[0]);
                continue;
            }
            try {
                BroadcastHelper.ensureRegisterReceiver(context, this, intentFilter);
            } catch (Exception exception) {
                Debug.e((Throwable) exception);
            }
        }
    }
    
    public void onReceive(final Context context, final Intent intent) {
        final String action;
        if ((action = intent.getAction()) == null) {
            return;
        }
        final String s = action;
        boolean b = true;
        s.hashCode();
        int n = -1;
        Label_1793: {
            int n2 = 0;
            switch (s.hashCode()) {
                default: {
                    break Label_1793;
                }
                case 2045140818: {
                    if (!action.equals("android.intent.action.MEDIA_BAD_REMOVAL")) {
                        break Label_1793;
                    }
                    n2 = 68;
                    break;
                }
                case 2039811242: {
                    if (!action.equals("android.intent.action.REBOOT")) {
                        break Label_1793;
                    }
                    n2 = 67;
                    break;
                }
                case 1964681210: {
                    if (!action.equals("android.intent.action.MEDIA_CHECKING")) {
                        break Label_1793;
                    }
                    n2 = 66;
                    break;
                }
                case 1947666138: {
                    if (!action.equals("android.intent.action.ACTION_SHUTDOWN")) {
                        break Label_1793;
                    }
                    n2 = 65;
                    break;
                }
                case 1884202961: {
                    if (!action.equals("onyx.android.intent.action.ONYX_PIN_CODE_REMOVED_ACTION")) {
                        break Label_1793;
                    }
                    n2 = 64;
                    break;
                }
                case 1856108963: {
                    if (!action.equals("onyx.action.screenshot.region.select.end")) {
                        break Label_1793;
                    }
                    n2 = 63;
                    break;
                }
                case 1822697253: {
                    if (!action.equals("onyx.android.intent.action.FINGERPRINT_REMOVED_ACTION")) {
                        break Label_1793;
                    }
                    n2 = 62;
                    break;
                }
                case 1767699139: {
                    if (!action.equals("com.android.system.WAKE_UP")) {
                        break Label_1793;
                    }
                    n2 = 61;
                    break;
                }
                case 1678578982: {
                    if (!action.equals("com.android.systemui.pre.enter.splitscreen")) {
                        break Label_1793;
                    }
                    n2 = 60;
                    break;
                }
                case 1568725119: {
                    if (!action.equals("action.enter.keyguard.interface")) {
                        break Label_1793;
                    }
                    n2 = 59;
                    break;
                }
                case 1531867068: {
                    if (!action.equals("com.android.systemui.HOME_BUTTON_CLICK")) {
                        break Label_1793;
                    }
                    n2 = 58;
                    break;
                }
                case 1412829408: {
                    if (!action.equals("android.intent.action.MEDIA_SCANNER_STARTED")) {
                        break Label_1793;
                    }
                    n2 = 57;
                    break;
                }
                case 1322394410: {
                    if (!action.equals("onyx.action.screenshot.region.select.start")) {
                        break Label_1793;
                    }
                    n2 = 56;
                    break;
                }
                case 1243091430: {
                    if (!action.equals("com.onyx.action.MTP_EVENT_ACTION")) {
                        break Label_1793;
                    }
                    n2 = 55;
                    break;
                }
                case 1154847506: {
                    if (!action.equals("com.android.systemui.STATUS_BAR_ICON_REFRESH_FINISH_ACTION")) {
                        break Label_1793;
                    }
                    n2 = 54;
                    break;
                }
                case 1019184907: {
                    if (!action.equals("android.intent.action.ACTION_POWER_CONNECTED")) {
                        break Label_1793;
                    }
                    n2 = 53;
                    break;
                }
                case 1008856558: {
                    if (!action.equals("com.android.systemui.SYSTEM_UI_DIALOG_OPEN_ACTION")) {
                        break Label_1793;
                    }
                    n2 = 52;
                    break;
                }
                case 994607835: {
                    if (!action.equals("onyx.action.ROTATION_CHANGED")) {
                        break Label_1793;
                    }
                    n2 = 51;
                    break;
                }
                case 956498534: {
                    if (!action.equals("com.android.systemui.SYSTEM_UI_SCREEN_SHOT_END_ACTION")) {
                        break Label_1793;
                    }
                    n2 = 50;
                    break;
                }
                case 872177156: {
                    if (!action.equals("android.intent.action.UMS_DISCONNECTED")) {
                        break Label_1793;
                    }
                    n2 = 49;
                    break;
                }
                case 831246713: {
                    if (!action.equals("com.onyx.floatingbutton.menu_state_changed")) {
                        break Label_1793;
                    }
                    n2 = 48;
                    break;
                }
                case 824240244: {
                    if (!action.equals("action.enter.recent.interface")) {
                        break Label_1793;
                    }
                    n2 = 47;
                    break;
                }
                case 823795052: {
                    if (!action.equals("android.intent.action.USER_PRESENT")) {
                        break Label_1793;
                    }
                    n2 = 46;
                    break;
                }
                case 798292259: {
                    if (!action.equals("android.intent.action.BOOT_COMPLETED")) {
                        break Label_1793;
                    }
                    n2 = 45;
                    break;
                }
                case 684479836: {
                    if (!action.equals("com.onyx.debug.app.crash")) {
                        break Label_1793;
                    }
                    n2 = 44;
                    break;
                }
                case 490310653: {
                    if (!action.equals("android.intent.action.BATTERY_LOW")) {
                        break Label_1793;
                    }
                    n2 = 43;
                    break;
                }
                case 468947303: {
                    if (!action.equals("onyx.action.action_push_statistics")) {
                        break Label_1793;
                    }
                    n2 = 42;
                    break;
                }
                case 282248062: {
                    if (!action.equals("com.android.systemui.STATUS_BAR_SHOW_ACTION")) {
                        break Label_1793;
                    }
                    n2 = 41;
                    break;
                }
                case 115270924: {
                    if (!action.equals("com.onyx.floatingbutton.touch")) {
                        break Label_1793;
                    }
                    n2 = 40;
                    break;
                }
                case 98406502: {
                    if (!action.equals("com.onyx.IM_MESSAGE_ACTION")) {
                        break Label_1793;
                    }
                    n2 = 39;
                    break;
                }
                case -19011148: {
                    if (!action.equals("android.intent.action.LOCALE_CHANGED")) {
                        break Label_1793;
                    }
                    n2 = 38;
                    break;
                }
                case -129854233: {
                    if (!action.equals("start_onyx_settings")) {
                        break Label_1793;
                    }
                    n2 = 37;
                    break;
                }
                case -214953535: {
                    if (!action.equals("com.android.systemui.TOAST_SHOW_ACTION")) {
                        break Label_1793;
                    }
                    n2 = 36;
                    break;
                }
                case -226322023: {
                    if (!action.equals("com.android.systemui.STATUS_BAR_HIDE_ACTION")) {
                        break Label_1793;
                    }
                    n2 = 35;
                    break;
                }
                case -337938496: {
                    if (!action.equals("android.intent.action.UMS_CONNECTED")) {
                        break Label_1793;
                    }
                    n2 = 34;
                    break;
                }
                case -343630553: {
                    if (!action.equals("android.net.wifi.STATE_CHANGE")) {
                        break Label_1793;
                    }
                    n2 = 33;
                    break;
                }
                case -391706099: {
                    if (!action.equals("com.onyx.ime.show")) {
                        break Label_1793;
                    }
                    n2 = 32;
                    break;
                }
                case -392033198: {
                    if (!action.equals("com.onyx.ime.hide")) {
                        break Label_1793;
                    }
                    n2 = 31;
                    break;
                }
                case -416304641: {
                    if (!action.equals("com.android.systemui.SYSTEM_UI_SCREEN_SHOT_START_ACTION")) {
                        break Label_1793;
                    }
                    n2 = 30;
                    break;
                }
                case -424842590: {
                    if (!action.equals("token.invalid.action")) {
                        break Label_1793;
                    }
                    n2 = 29;
                    break;
                }
                case -464392434: {
                    if (!action.equals("com.onyx.action.eink.RESET_REFRESH_CONFIG")) {
                        break Label_1793;
                    }
                    n2 = 28;
                    break;
                }
                case -494529457: {
                    if (!action.equals("android.hardware.usb.action.USB_STATE")) {
                        break Label_1793;
                    }
                    n2 = 27;
                    break;
                }
                case -627826909: {
                    if (!action.equals("com.android.systemui.STATUS_BAR_ICON_REFRESH_START_ACTION")) {
                        break Label_1793;
                    }
                    n2 = 26;
                    break;
                }
                case -723523620: {
                    if (!action.equals("com.android.systemui.TOAST_HIDE_ACTION")) {
                        break Label_1793;
                    }
                    n2 = 25;
                    break;
                }
                case -873315010: {
                    if (!action.equals("notify_calibration_finish")) {
                        break Label_1793;
                    }
                    n2 = 24;
                    break;
                }
                case -963871873: {
                    if (!action.equals("android.intent.action.MEDIA_UNMOUNTED")) {
                        break Label_1793;
                    }
                    n2 = 23;
                    break;
                }
                case -967977319: {
                    if (!action.equals("com.onyx.UNBIND_DEVICE_ACTION")) {
                        break Label_1793;
                    }
                    n2 = 22;
                    break;
                }
                case -1046155819: {
                    if (!action.equals("com.onyx.parsePushNotification")) {
                        break Label_1793;
                    }
                    n2 = 21;
                    break;
                }
                case -1117898111: {
                    if (!action.equals("com.android.systemui.post.enter.splitscreen")) {
                        break Label_1793;
                    }
                    n2 = 20;
                    break;
                }
                case -1142424621: {
                    if (!action.equals("android.intent.action.MEDIA_SCANNER_FINISHED")) {
                        break Label_1793;
                    }
                    n2 = 19;
                    break;
                }
                case -1168515733: {
                    if (!action.equals("action.onyx.reset.pincode")) {
                        break Label_1793;
                    }
                    n2 = 18;
                    break;
                }
                case -1172645946: {
                    if (!action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                        break Label_1793;
                    }
                    n2 = 17;
                    break;
                }
                case -1214206042: {
                    if (!action.equals("com.android.systemui.pre.position.exchanged")) {
                        break Label_1793;
                    }
                    n2 = 16;
                    break;
                }
                case -1353156318: {
                    if (!action.equals("com.onyx.action.eink.SHOW_REFRESH_SETTINGS_UI")) {
                        break Label_1793;
                    }
                    n2 = 15;
                    break;
                }
                case -1410684549: {
                    if (!action.equals("android.os.storage.action.VOLUME_STATE_CHANGED")) {
                        break Label_1793;
                    }
                    n2 = 14;
                    break;
                }
                case -1439570246: {
                    if (!action.equals("com.android.systemui.SYSTEM_UI_DIALOG_CLOSE_ACTION")) {
                        break Label_1793;
                    }
                    n2 = 13;
                    break;
                }
                case -1454123155: {
                    if (!action.equals("android.intent.action.SCREEN_ON")) {
                        break Label_1793;
                    }
                    n2 = 12;
                    break;
                }
                case -1514214344: {
                    if (!action.equals("android.intent.action.MEDIA_MOUNTED")) {
                        break Label_1793;
                    }
                    n2 = 11;
                    break;
                }
                case -1608292967: {
                    if (!action.equals("android.hardware.usb.action.USB_DEVICE_DETACHED")) {
                        break Label_1793;
                    }
                    n2 = 10;
                    break;
                }
                case -1665311200: {
                    if (!action.equals("android.intent.action.MEDIA_REMOVED")) {
                        break Label_1793;
                    }
                    n2 = 9;
                    break;
                }
                case -1710883635: {
                    if (!action.equals("com.onyx.open")) {
                        break Label_1793;
                    }
                    n2 = 8;
                    break;
                }
                case -1754841973: {
                    if (!action.equals("android.net.conn.TETHER_STATE_CHANGED")) {
                        break Label_1793;
                    }
                    n2 = 7;
                    break;
                }
                case -1823790459: {
                    if (!action.equals("android.intent.action.MEDIA_SHARED")) {
                        break Label_1793;
                    }
                    n2 = 6;
                    break;
                }
                case -1875733435: {
                    if (!action.equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                        break Label_1793;
                    }
                    n2 = 5;
                    break;
                }
                case -1886648615: {
                    if (!action.equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
                        break Label_1793;
                    }
                    n2 = 4;
                    break;
                }
                case -2002422483: {
                    if (!action.equals("action.no.saved.network.connected")) {
                        break Label_1793;
                    }
                    n2 = 3;
                    break;
                }
                case -2005650005: {
                    if (!action.equals("com.android.systemui.post.position.exchanged")) {
                        break Label_1793;
                    }
                    n2 = 2;
                    break;
                }
                case -2043172127: {
                    if (!action.equals("onyx.action.user.operate.split.handle")) {
                        break Label_1793;
                    }
                    n2 = 1;
                    break;
                }
                case -2114103349: {
                    if (!action.equals("android.hardware.usb.action.USB_DEVICE_ATTACHED")) {
                        break Label_1793;
                    }
                    n2 = 0;
                    break;
                }
            }
            n = n2;
        }
        switch (n) {
            default: {
                b = false;
                break;
            }
            case 68: {
                this.onMediaStateChanged(intent);
                this.onMediaBadRemoval(intent);
                break;
            }
            case 67: {
                this.c();
                break;
            }
            case 66: {
                this.onMediaChecking(intent);
                break;
            }
            case 65: {
                this.h(intent);
                break;
            }
            case 63: {
                this.b(true);
                break;
            }
            case 62:
            case 64: {
                this.d(context, intent);
                break;
            }
            case 60: {
                this.f();
                break;
            }
            case 59: {
                this.onLockScreenChanged(true);
                break;
            }
            case 58: {
                this.c(intent);
                break;
            }
            case 57: {
                this.onMediaScanStarted(intent);
                break;
            }
            case 56: {
                this.b(false);
                break;
            }
            case 55: {
                this.onMtpEvent(intent);
                break;
            }
            case 51: {
                this.f(intent);
                break;
            }
            case 50: {
                this.b(intent, true);
                break;
            }
            case 48: {
                this.b(intent);
                break;
            }
            case 47: {
                this.b();
                break;
            }
            case 46: {
                this.onLockScreenChanged(false);
                break;
            }
            case 45: {
                this.onReceiveBootComplete(intent);
                break;
            }
            case 44: {
                this.notifyDebugAppCrash(context, intent);
                break;
            }
            case 43: {
                this.d(intent);
                break;
            }
            case 42: {
                this.c(context, intent);
                break;
            }
            case 41: {
                this.onStatusBarChanged(true);
                break;
            }
            case 40: {
                this.a(intent);
                break;
            }
            case 39: {
                this.j(intent);
                break;
            }
            case 38: {
                this.onLocaleChanged(context, intent);
                break;
            }
            case 37: {
                this.onStartSettings(intent);
                break;
            }
            case 36: {
                this.c(true);
                break;
            }
            case 35: {
                this.onStatusBarChanged(false);
                break;
            }
            case 34:
            case 49: {
                this.onUmsStateChanged(intent);
                break;
            }
            case 32: {
                this.a(true);
                break;
            }
            case 31: {
                this.a(false);
                break;
            }
            case 30: {
                this.b(intent, false);
                break;
            }
            case 29: {
                this.f(context, intent);
                break;
            }
            case 28: {
                this.e(intent);
                break;
            }
            case 27: {
                this.onUsbStateChanged(intent);
                break;
            }
            case 52: {
                this.a(intent, true);
            }
            case 26: {
                this.c(intent, true);
                break;
            }
            case 25: {
                this.c(false);
                break;
            }
            case 24: {
                this.k(intent);
                break;
            }
            case 23: {
                this.onMediaUnmounted(intent);
                break;
            }
            case 22: {
                this.l(intent);
                break;
            }
            case 21: {
                this.onReceiveParsePushNotification(intent);
                break;
            }
            case 20: {
                this.d();
                break;
            }
            case 19: {
                this.onMediaScanFinished(intent);
                break;
            }
            case 18: {
                this.b(context, intent);
                break;
            }
            case 16: {
                this.g();
                break;
            }
            case 15: {
                this.g(intent);
                break;
            }
            case 14: {
                this.m(intent);
                break;
            }
            case 13: {
                this.a(intent, false);
            }
            case 12:
            case 54:
            case 61: {
                this.c(intent, false);
                break;
            }
            case 11: {
                this.onMediaStateChanged(intent);
                this.onMediaMounted(intent);
                break;
            }
            case 10: {
                this.d(false);
                break;
            }
            case 9: {
                this.onMediaStateChanged(intent);
                this.onMediaRemoved(intent);
                break;
            }
            case 8: {
                this.onOpenDocumentAction(intent);
                break;
            }
            case 7: {
                this.e(context, intent);
                break;
            }
            case 6: {
                this.onMediaShared(intent);
                break;
            }
            case 5:
            case 17:
            case 33: {
                this.a(context);
                break;
            }
            case 4:
            case 53: {
                this.onPowerStateChanged(intent);
                break;
            }
            case 3: {
                this.onUnableConnectNetworkEvent();
                break;
            }
            case 2: {
                this.e();
                break;
            }
            case 1: {
                this.i(intent);
                break;
            }
            case 0: {
                this.d(true);
                break;
            }
        }
        final boolean b2 = b;
        final boolean a = this.a(context, intent);
        if (b2 && a && Debug.getDebug()) {
            Debug.w((Class)this.getClass(), "action: " + action + " , handled in internal and external, please check action!", new Object[0]);
        }
    }
    
    public DeviceReceiver setExtraIntentReceiver(final ExtraIntentReceiver extraIntentReceiver) {
        this.y = extraIntentReceiver;
        this.z.clear();
        this.z.addAll(extraIntentReceiver.getIntentFilterList());
        return this;
    }
    
    public DeviceReceiver setWiFiStateChangedListener(final WiFiStateChangedListener wiFiStateChangedListener) {
        this.o = wiFiStateChangedListener;
        return this;
    }
    
    public void setBootCompleteListener(final BootCompleteListener l) {
        this.b = l;
    }
    
    public void setPushNotificationListener(final PushNotificationListener l) {
        this.c = l;
    }
    
    public void setNetworkConnectChangedListener(final NetworkConnectChangedListener l) {
        this.d = l;
    }
    
    public void setUmsStateListener(final UmsStateListener l) {
        this.e = l;
    }
    
    public void setMediaStateListener(final MediaStateListener l) {
        this.f = l;
    }
    
    public void setSettingsListener(final SettingsListener l) {
        this.h = l;
    }
    
    public void setOpenDocumentListener(final OpenDocumentListener l) {
        this.i = l;
    }
    
    public void setLocaleChangedListener(final LocaleChangedListener l) {
        this.j = l;
    }
    
    public void setBluetoothStateListener(final BluetoothStateListener l) {
        this.k = l;
    }
    
    public void setResetPinCodeListener(final ResetPinCodeListener l) {
        this.p = l;
    }
    
    public void setKeyguardChangedListener(final SystemKeyguardChangedListener l) {
        this.q = l;
    }
    
    public void setMtpEventListener(final MtpEventListener l) {
        this.l = l;
    }
    
    public void setUsbStateListener(final UsbStateListener l) {
        this.m = l;
    }
    
    public void setSystemUIChangeListener(final SystemUIChangeListener listener) {
        this.n = listener;
    }
    
    public void setUsbDeviceStateChangeListener(final UsbDeviceStateChangeListener usbDeviceStateChangeListener) {
        this.r = usbDeviceStateChangeListener;
    }
    
    public void setVolumeStateChangeListener(final VolumeStateChangeListener volumeStateChangeListener) {
        this.s = volumeStateChangeListener;
    }
    
    public void setImMessageListener(final IMMessageListener imMessageListener) {
        this.t = imMessageListener;
    }
    
    public void setTokenInvalidListener(final TokenInvalidListener tokenInvalidListener) {
        this.u = tokenInvalidListener;
    }
    
    public void setStatisticsPushListener(final StatisticsPushListener statisticsPushListener) {
        this.x = statisticsPushListener;
    }
    
    public void setUnbindDeviceListener(final UnbindDeviceListener unbindDeviceListener) {
        this.v = unbindDeviceListener;
    }
    
    public DeviceReceiver setCalibrationListener(final CalibrationListener calibrationListener) {
        this.w = calibrationListener;
        return this;
    }
    
    public SystemUIChangeListener getSystemUIChangeListener() {
        return this.n;
    }
    
    public void onReceiveBootComplete(final Intent intent) {
        final BootCompleteListener b;
        if ((b = this.b) != null) {
            b.onReceivedBootComplete(intent);
        }
    }
    
    public void onReceiveParsePushNotification(final Intent intent) {
        if (this.c == null) {
            return;
        }
        final Bundle extras;
        if ((extras = intent.getExtras()) == null) {
            Log.i(DeviceReceiver.F, "ignore null bundle");
        }
        Log.i(DeviceReceiver.F, "map: " + JSON.parseObject(extras.getString("com.parse.Data"), (Class)Map.class).toString());
        this.c.onReceivedPushNotification(intent);
    }
    
    public void onWiFiStateChanged(final Context context, @NonNull final NetworkInfo info) {
        final WiFiStateChangedListener o;
        if ((o = this.o) != null) {
            o.onWiFiStateChanged(context, info);
        }
    }
    
    public void onNetworkConnectChanged(final boolean currentConnected, @Nullable final NetworkInfo info) {
        final NetworkConnectChangedListener d;
        if ((d = this.d) != null) {
            d.onNetworkConnectChanged(currentConnected, info);
        }
    }
    
    public void onUnableConnectNetworkEvent() {
        final NetworkConnectChangedListener d;
        if ((d = this.d) != null) {
            d.onUnableConnectNetworkEvent();
        }
    }
    
    public void onUmsStateChanged(final Intent intent) {
        final UmsStateListener e;
        if ((e = this.e) != null) {
            e.onUmsStateChanged(intent);
        }
    }
    
    public void onPowerStateChanged(final Intent intent) {
    }
    
    public void onLockScreenChanged(final boolean open) {
        final SystemUIChangeListener n;
        if ((n = this.n) != null) {
            n.onLockScreenChanged(open);
        }
    }
    
    public void onStatusBarChanged(final boolean show) {
        final SystemUIChangeListener n;
        if ((n = this.n) != null) {
            n.onStatusBarChanged(show);
        }
    }
    
    public void onMediaBadRemoval(final Intent intent) {
        final MediaStateListener f;
        if ((f = this.f) != null) {
            f.onMediaBadRemoval(intent);
        }
    }
    
    public void onMediaMounted(final Intent intent) {
        final MediaStateListener f;
        if ((f = this.f) != null) {
            f.onMediaMounted(intent);
        }
    }
    
    public void onMediaRemoved(final Intent intent) {
        final MediaStateListener f;
        if ((f = this.f) != null) {
            f.onMediaRemoved(intent);
        }
    }
    
    public void onMediaUnmounted(final Intent intent) {
        final MediaStateListener f;
        if ((f = this.f) != null) {
            f.onMediaUnmounted(intent);
        }
    }
    
    public void onMediaChecking(final Intent intent) {
        final MediaStateListener f;
        if ((f = this.f) != null) {
            f.onMediaChecking(intent);
        }
    }
    
    public void onMediaShared(final Intent intent) {
        final MediaStateListener f;
        if ((f = this.f) != null) {
            f.onMediaShared(intent);
        }
    }
    
    public void onMediaScanStarted(final Intent intent) {
        final MediaStateListener f;
        if ((f = this.f) != null) {
            f.onMediaScanStarted(intent);
        }
    }
    
    public void onMediaScanFinished(final Intent intent) {
        final MediaStateListener f;
        if ((f = this.f) != null) {
            f.onMediaScanFinished(intent);
        }
    }
    
    public void onMediaStateChanged(final Intent intent) {
        final MediaStateListener f;
        if ((f = this.f) != null) {
            f.onMediaScanStarted(intent);
        }
    }
    
    public void onMtpEvent(final Intent intent) {
        final MtpEventListener l;
        if ((l = this.l) != null) {
            l.onMtpEvent(intent);
        }
    }
    
    public void onUsbStateChanged(final Intent intent) {
        final UsbStateListener m;
        if ((m = this.m) != null) {
            m.onUsbState(intent, intent.getExtras().getBoolean("connected"));
        }
    }
    
    public void setStorageState(final String mount, final String state) {
        this.a.put(mount, state);
    }
    
    public String getStorageState(final String mount) {
        final Map<String, String> a;
        if ((a = this.a) == null) {
            return null;
        }
        return a.get(mount);
    }
    
    public boolean isStorageShared(final String mount) {
        final String s;
        return !StringUtils.isNullOrEmpty(s = this.a.get(mount)) && s.equals("android.intent.action.MEDIA_SHARED");
    }
    
    public void onStartSettings(final Intent intent) {
        final SettingsListener h;
        if ((h = this.h) != null) {
            h.onSystemSettingsClicked(intent);
        }
    }
    
    public void onOpenDocumentAction(final Intent intent) {
        if (this.i != null) {
            this.i.onOpenDocumentAction(intent, intent.getStringExtra("path"));
        }
    }
    
    public void onLocaleChanged(final Context context, final Intent intent) {
        final LocaleChangedListener j;
        if ((j = this.j) != null) {
            j.onLocaleChanged(context, intent);
        }
    }
    
    public boolean isAnyStorageShared() {
        return this.isStorageShared(EnvironmentUtil.getExternalStorageDirectory().getAbsolutePath()) || this.isStorageShared(EnvironmentUtil.getRemovableSDCardDirectory().getAbsolutePath());
    }
    
    public void updateStorageState(final Context context, final Intent intent) {
        this.setStorageState(FileUtils.getRealFilePathFromUri(context, intent.getData()), intent.getAction());
    }
    
    public IntentFilter netWorkFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("action.no.saved.network.connected");
        return intentFilter;
    }
    
    public void setTetherStateChangedListener(final TetherStateChangedListener l) {
        this.D = l;
    }
    
    public IntentFilter tetherFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.TETHER_STATE_CHANGED");
        return intentFilter;
    }
    
    public void setDebugAppCrashListener(final DebugAppCrashListener l) {
        this.E = l;
    }
    
    public void notifyDebugAppCrash(final Context context, final Intent intent) {
        final DebugAppCrashListener e;
        if ((e = this.E) != null) {
            e.onDebugCrash(context, intent);
        }
    }
    
    public static class DebugAppCrashListener
    {
        public void onDebugCrash(final Context context, final Intent intent) {
        }
    }
    
    public static class TetherStateChangedListener
    {
        public void onTetherStateChanged(final Context context, final Intent intent) {
        }
    }
    
    public static class StatisticsPushListener
    {
        public void onStatisticsPush(final Context context, final Intent intent) {
        }
    }
    
    public static class TokenInvalidListener
    {
        public void onTokenInvalid(final Context context, final Intent intent) {
        }
    }
    
    public abstract static class SystemUIChangeListener
    {
        public void onSystemUIChanged(final String action, final boolean open) {
        }
        
        public void onSystemIconChanged(final String action, @NonNull final String iconType, final boolean open) {
        }
        
        public void onNoFocusSystemDialogChanged(final boolean open) {
        }
        
        public void onHomeClicked() {
        }
        
        public void onLowBattery(final Intent intent) {
        }
        
        public void onShutDown(final Intent intent) {
        }
        
        public void onReboot() {
        }
        
        public void onScreenShot(final Intent intent, final boolean end) {
        }
        
        public void onOnyxKeyboardChanged(final boolean open) {
        }
        
        public void onFloatButtonChanged(final boolean active) {
        }
        
        public void onFloatButtonMenuStateChanged(final boolean open) {
        }
        
        public void onToastChanged(final boolean show) {
        }
        
        public void onEnterRecent() {
        }
        
        public void onLockScreenChanged(final boolean open) {
        }
        
        public void onStatusBarChanged(final boolean show) {
        }
        
        public void onRegionScreenShot(final boolean end) {
        }
        
        public void onResetRefreshConfig() {
        }
        
        public void onShowRefreshSettingsUI() {
        }
        
        public void onRotationChanged(final Intent intent) {
        }
        
        public void onUserSplitHandle(final boolean isHandle, @Nullable final ComponentName focusedComponent) {
        }
        
        public void onPreEnterSplitScreen() {
        }
        
        public void onPostEnterSplitScreen() {
        }
        
        public void onPreExchangeSplitScreen() {
        }
        
        public void onPostExchangeSplitScreen() {
        }
    }
    
    public static class SystemKeyguardChangedListener
    {
        public void onSystemKeyguardChanged(final Context context, final Intent intent) {
        }
    }
    
    public static class ResetPinCodeListener
    {
        public void onResetPinCode(final Context context, final Intent intent) {
        }
    }
    
    public static class LocaleChangedListener
    {
        public void onLocaleChanged() {
        }
        
        public void onLocaleChanged(final Context context, final Intent intent) {
            this.onLocaleChanged();
        }
    }
    
    public static class OpenDocumentListener
    {
        public void onOpenDocumentAction(final Intent intent, final String path) {
        }
    }
    
    public static class SettingsListener
    {
        public void onSystemSettingsClicked(final Intent intent) {
        }
    }
    
    public static class FileSystemListener
    {
        public void onFileRemoved(final String path) {
        }
        
        public void onFileUpdated(final String path) {
        }
        
        public void onFileAdded(final String path) {
        }
        
        public void onFileMoved(final String path) {
        }
    }
    
    public static class CalibrationListener
    {
        public void onInputDeviceCalibrationFinish(final Intent intent) {
        }
    }
    
    public static class UnbindDeviceListener
    {
        public void onUnbindDevice(final Intent intent) {
        }
    }
    
    public static class IMMessageListener
    {
        public void onMessage(final Intent intent) {
        }
    }
    
    public static class UsbDeviceStateChangeListener
    {
        public void onUsbDeviceStateChange(final boolean attached) {
        }
    }
    
    public static class VolumeStateChangeListener
    {
        public void onVolumeStateChange(final int state) {
        }
    }
    
    public static class UsbStateListener
    {
        public void onUsbState(final Intent intent, final boolean connected) {
        }
    }
    
    public static class MtpEventListener
    {
        public void onMtpEvent(final Intent intent) {
        }
    }
    
    public static class MediaStateListener
    {
        public void onMediaBadRemoval(final Intent intent) {
        }
        
        public void onMediaMounted(final Intent intent) {
        }
        
        public void onMediaUnmounted(final Intent intent) {
        }
        
        public void onMediaRemoved(final Intent intent) {
        }
        
        public void onMediaChecking(final Intent intent) {
        }
        
        public void onMediaShared(final Intent intent) {
        }
        
        public void onMediaScanStarted(final Intent intent) {
        }
        
        public void onMediaScanFinished(final Intent intent) {
        }
    }
    
    public static class UmsStateListener
    {
        public void onUmsStateChanged(final Intent intent) {
        }
    }
    
    public static class BluetoothStateListener
    {
        public void onBluetoothStateChanged(final Intent intent) {
        }
    }
    
    public static class WiFiStateChangedListener
    {
        public void onWiFiStateChanged(final Context context, @NonNull final NetworkInfo info) {
        }
    }
    
    public static class NetworkConnectChangedListener
    {
        public void onNetworkConnectChanged(final boolean connected, @Nullable final NetworkInfo info) {
        }
        
        public void onUnableConnectNetworkEvent() {
        }
    }
    
    public static class PushNotificationListener
    {
        public void onReceivedPushNotification(final Intent intent) {
        }
    }
    
    public static class BootCompleteListener
    {
        public void onReceivedBootComplete(final Intent intent) {
        }
    }
    
    public static class ExtraIntentReceiver
    {
        @NonNull
        public List<IntentFilter> getIntentFilterList() {
            return new ArrayList<IntentFilter>();
        }
        
        public boolean onReceive(final Context context, final Intent intent) {
            return false;
        }
    }
    
    private static class d extends RxRequest
    {
        public NetworkInfo c;
        
        @Override
        public void execute() throws Exception {
            final ConnectivityManager connectivityManager;
            if ((connectivityManager = (ConnectivityManager)this.getContext().getApplicationContext().getSystemService("connectivity")) == null) {
                return;
            }
            this.c = connectivityManager.getActiveNetworkInfo();
        }
    }
}
