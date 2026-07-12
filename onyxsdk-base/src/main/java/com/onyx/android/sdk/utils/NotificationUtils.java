package com.onyx.android.sdk.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.onyx.android.sdk.data.im.Message;

public class NotificationUtils {
    private static final String a = "android.title";
    private static final String b = "android.substName";
    private static final String c = "android.big_content_required";
    private static final String d = "android.use_big_temp_for_base_view";

    public static void startForegroundServiceNotification(Service service) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        String packageName = service.getPackageName();
        NotificationChannel notificationChannel = new NotificationChannel(packageName, "My Background Service", 0);
        notificationChannel.setLockscreenVisibility(0);
        NotificationManager notificationManager = (NotificationManager) service.getSystemService(Message.TYPE_NOTIFICATION);
        if (notificationManager == null) {
            Debug.w(service.getClass(), "null manager", new Object[0]);
            return;
        }
        notificationManager.createNotificationChannel(notificationChannel);
        service.startForeground(1, new NotificationCompat.Builder(service, packageName).setOngoing(true).setContentTitle("App is running in background").setPriority(1).setCategory("service").build());
        Debug.i(service.getClass(), "startForeground", new Object[0]);
    }

    public static void cancelNotification(int id) {
        NotificationManager notificationManager = (NotificationManager) ResManager.getAppContext().getSystemService(Message.TYPE_NOTIFICATION);
        if (notificationManager != null) {
            notificationManager.cancel(id);
        }
    }

    public static Bundle setNotificationTitle(Bundle bundle, String title) {
        if (bundle == null) {
            bundle = bundle;
            Bundle bundle2 = new Bundle();
        }
        if (!TextUtils.isEmpty(title)) {
            Bundle bundle3 = bundle;
            bundle3.putCharSequence(a, title);
            bundle3.putCharSequence(b, title);
        }
        return bundle;
    }

    public static Bundle setUseBigTempForBaseView(Bundle bundle) {
        if (bundle == null) {
            bundle = bundle;
            Bundle bundle2 = new Bundle();
        }
        Bundle bundle3 = bundle;
        bundle3.putBoolean(c, false);
        bundle3.putBoolean(d, true);
        return bundle3;
    }
}
