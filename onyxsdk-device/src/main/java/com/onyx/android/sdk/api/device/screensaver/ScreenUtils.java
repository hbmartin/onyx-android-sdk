package com.onyx.android.sdk.api.device.screensaver;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.WindowManager;
import androidx.annotation.NonNull;

public class ScreenUtils {
    private ScreenUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static int getScreenWidth(@NonNull Context context) {
        return getScreenSize(context)[0];
    }

    public static int getScreenHeight(@NonNull Context context) {
        return getScreenSize(context)[1];
    }

    public static float getScreenXDpi() {
        return Resources.getSystem().getDisplayMetrics().xdpi;
    }

    public static int[] getScreenSize(@NonNull Context context) {
        WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService("window");
        if (windowManager == null) {
            return new int[]{context.getApplicationContext().getResources().getDisplayMetrics().widthPixels, context.getApplicationContext().getResources().getDisplayMetrics().heightPixels};
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            windowManager.getDefaultDisplay().getRealSize(point);
        } else {
            windowManager.getDefaultDisplay().getSize(point);
        }
        return new int[]{point.x, point.y};
    }
}
