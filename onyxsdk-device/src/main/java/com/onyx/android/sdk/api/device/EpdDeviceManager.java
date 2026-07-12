package com.onyx.android.sdk.api.device;

import android.content.Context;
import android.view.View;
import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.api.device.epd.UpdateMode;
import com.onyx.android.sdk.device.Device;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/api/device/EpdDeviceManager.class */
public class EpdDeviceManager {
    private static final String TAG = "EpdDeviceManager";
    private static int gcInterval;
    private static int refreshCount;
    private static boolean inFastUpdateMode = false;
    private static EpdDevice epdDevice;

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/api/device/EpdDeviceManager$a.class */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[Device.DeviceIndex.values().length];
            a = iArr;
            try {
                iArr[Device.DeviceIndex.SDM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[Device.DeviceIndex.imx6.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[Device.DeviceIndex.Rk31xx.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[Device.DeviceIndex.Rk32xx.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[Device.DeviceIndex.Rk3026.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private static void initEpdDevice() {
        switch (a.a[Device.currentDeviceIndex().ordinal()]) {
            case 1:
                epdDevice = new EpdSDM();
                break;
            case 2:
                epdDevice = new EpdImx6();
                break;
            case 3:
                epdDevice = new EpdRk31xx();
                break;
            case 4:
                epdDevice = new EpdRk32xx();
                break;
            case 5:
                epdDevice = new EpdRk3026();
                break;
            default:
                epdDevice = new EpdDevice();
                break;
        }
    }

    public static void enterAnimationUpdate(boolean clear) {
        if (inFastUpdateMode) {
            return;
        }
        EpdController.applyTransientUpdate(UpdateMode.ANIMATION_QUALITY);
        inFastUpdateMode = true;
    }

    public static void exitAnimationUpdate(boolean clear) {
        if (inFastUpdateMode) {
            EpdController.clearTransientUpdate(clear);
            inFastUpdateMode = false;
        }
    }

    public static void startScreenHandWriting(View view) {
        EpdController.setScreenHandWritingPenState(view, 1);
    }

    public static void stopScreenHandWriting(View view) {
        EpdController.setScreenHandWritingPenState(view, 0);
    }

    public static void prepareInitialUpdate(int interval) {
        int i = interval - 1;
        gcInterval = i;
        refreshCount = i;
    }

    public static int getGcInterval() {
        return gcInterval;
    }

    public static void setGcInterval(int interval) {
        gcInterval = interval - 1;
        refreshCount = 0;
    }

    public static void applyWithGCInterval(View view, boolean isTextPage) {
        if (isUsingRegal(view.getContext())) {
            applyWithGCIntervalWitRegal(view, isTextPage);
        } else {
            applyWithGCIntervalWithoutRegal(view);
        }
    }

    public static boolean isUsingRegal(Context context) {
        return EpdController.supportRegal();
    }

    public static void enableScreenUpdate(View view, boolean enable) {
        epdDevice.enableScreenUpdate(view, enable);
    }

    public static void refreshScreenWithGCInterval(View view, boolean isTextPage) {
        enableScreenUpdate(view, true);
        if (isTextPage && EpdController.supportRegal()) {
            refreshScreenWithGCIntervalWithRegal(view);
        } else {
            refreshScreenWithGCIntervalWithoutRegal(view);
        }
    }

    public static void refreshScreenWithGCIntervalWithRegal(View view) {
        int i = refreshCount;
        refreshCount = i + 1;
        if (i < gcInterval) {
            epdDevice.refreshScreen(view, UpdateMode.REGAL);
        } else {
            refreshCount = 0;
            epdDevice.refreshScreen(view, UpdateMode.GC);
        }
    }

    public static void refreshScreenWithGCIntervalWithoutRegal(View view) {
        int i = refreshCount;
        refreshCount = i + 1;
        if (i < gcInterval) {
            epdDevice.refreshScreen(view, UpdateMode.GU);
        } else {
            refreshCount = 0;
            epdDevice.refreshScreen(view, UpdateMode.GC);
        }
    }

    public static void applyWithGCIntervalWitRegal(View view, boolean textOnly) {
        int i = refreshCount;
        refreshCount = i + 1;
        if (i < gcInterval) {
            epdDevice.applyRegalUpdate(view);
        } else {
            refreshCount = 0;
            epdDevice.applyGCUpdate(view);
        }
    }

    public static void applyWithGCIntervalWithoutRegal(View view) {
        int i = refreshCount;
        refreshCount = i + 1;
        if (i < gcInterval) {
            epdDevice.resetUpdate(view);
        } else {
            refreshCount = 0;
            epdDevice.applyGCUpdate(view);
        }
    }

    public static void applyGCUpdate(View view) {
        epdDevice.applyGCUpdate(view);
    }

    public static void setUpdateMode(View view, UpdateMode mode) {
        epdDevice.setUpdateMode(view, mode);
    }

    public static void resetUpdateMode(View view) {
        epdDevice.resetUpdate(view);
    }

    public static void cleanUpdateMode(View view) {
        epdDevice.cleanUpdate(view);
    }

    static {
        initEpdDevice();
    }
}
