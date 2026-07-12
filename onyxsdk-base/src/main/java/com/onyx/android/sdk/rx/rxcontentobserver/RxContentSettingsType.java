package com.onyx.android.sdk.rx.rxcontentobserver;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.Settings;
import androidx.annotation.Nullable;
import com.onyx.android.sdk.data.ReaderTextStyle;
import com.onyx.android.sdk.utils.TTFFont;

public enum RxContentSettingsType {
    GLOBAL,
    SYSTEM,
    SECURE;

    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[RxContentSettingsType.values().length];
            a = iArr;
            try {
                iArr[RxContentSettingsType.GLOBAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[RxContentSettingsType.SECURE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[RxContentSettingsType.SYSTEM.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Nullable
    public static Uri getUri(RxContentSettingsType type, String key) {
        int i = a.a[type.ordinal()];
        if (i == 1) {
            return Settings.Global.getUriFor(key);
        }
        if (i == 2) {
            return Settings.Secure.getUriFor(key);
        }
        if (i != 3) {
            return null;
        }
        return Settings.System.getUriFor(key);
    }

    public static int getInt(ContentResolver resolver, RxContentSettingsType type, String key) throws Exception {
        return getInt(resolver, type, key, -1);
    }

    public static String getString(ContentResolver resolver, RxContentSettingsType type, String key) throws Exception {
        int i = a.a[type.ordinal()];
        if (i == 1) {
            return Settings.Global.getString(resolver, key);
        }
        if (i != 2) {
            return i != 3 ? TTFFont.UNKNOWN_FONT_NAME : Settings.System.getString(resolver, key);
        }
        return Settings.Secure.getString(resolver, key);
    }

    public static boolean putInt(ContentResolver resolver, RxContentSettingsType type, String key, int value) throws Exception {
        int i = a.a[type.ordinal()];
        if (i == 1) {
            return Settings.Global.putInt(resolver, key, value);
        }
        if (i == 2) {
            return Settings.Secure.putInt(resolver, key, value);
        }
        if (i != 3) {
            return false;
        }
        return Settings.System.putInt(resolver, key, value);
    }

    public static float getFloat(ContentResolver resolver, RxContentSettingsType type, String key) throws Exception {
        return getFloat(resolver, type, key, ReaderTextStyle.FONT_EMBOLDEN_NORMAL);
    }

    public static boolean putFloat(ContentResolver resolver, RxContentSettingsType type, String key, float value) throws Exception {
        int i = a.a[type.ordinal()];
        if (i == 1) {
            return Settings.Global.putFloat(resolver, key, value);
        }
        if (i == 2) {
            return Settings.Secure.putFloat(resolver, key, value);
        }
        if (i != 3) {
            return false;
        }
        return Settings.System.putFloat(resolver, key, value);
    }

    public static int getInt(ContentResolver resolver, RxContentSettingsType type, String key, int def) throws Exception {
        int i = a.a[type.ordinal()];
        if (i == 1) {
            return Settings.Global.getInt(resolver, key, def);
        }
        if (i != 2) {
            return i != 3 ? def : Settings.System.getInt(resolver, key, def);
        }
        return Settings.Secure.getInt(resolver, key, def);
    }

    public static float getFloat(ContentResolver resolver, RxContentSettingsType type, String key, float defValue) throws Exception {
        int i = a.a[type.ordinal()];
        if (i == 1) {
            return Settings.Global.getFloat(resolver, key, defValue);
        }
        if (i != 2) {
            return i != 3 ? defValue : Settings.System.getFloat(resolver, key, defValue);
        }
        return Settings.Secure.getFloat(resolver, key, defValue);
    }
}
