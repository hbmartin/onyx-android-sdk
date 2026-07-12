package com.onyx.android.sdk.device;

import android.content.Context;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.onyx.android.sdk.api.device.epd.EPDMode;
import com.onyx.android.sdk.api.device.epd.UpdateMode;
import com.onyx.android.sdk.utils.ReflectUtil;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class RK3026Device extends BaseDevice {
    private static final String s = "RK3026Device";
    private static RK3026Device t;
    private static final int u = 1;
    private static final String v = "1";
    private static final String w = "android.os.SystemProperties";
    private static final int G = 0;
    private static final int H = 1;
    private static final int I = 2;
    private static final int J = 3;
    private static final int K = 4;
    private static final int L = 16;
    private static final String M = "EPD_NULL";
    private static final String N = "EPD_AUTO";
    private static final String O = "EPD_FULL";
    private static final String P = "EPD_A2";
    private static final String Q = "EPD_PART";
    private static final String R = "EPD_REGLA";
    private static Method S;
    private static Method T;
    private static Method U;
    private static Method V;
    private static Method W;
    private static Method X;
    private static Method Y;
    private static Method Z;
    private static Method a0;
    private static Method b0;
    private static Method c0;
    private static Method d0;
    private static Method e0;
    private static Method f0;
    private static Method g0;
    private static Method h0;
    private static Method i0;
    private static Method j0;
    private static Method k0;
    private static Method l0;
    private static Method m0;
    private static Method n0;
    private static Method o0;
    private static Method p0;
    private static Method q0;
    private static Method r0;
    private static Method s0;
    private static Method t0;
    private static Method u0;
    private static Method v0;
    private static Method w0;
    private static Method x0;
    private static final String y0 = "unknown";
    private static final String z0 = "ro.deviceid";
    private Context p = null;
    private EPDMode q = EPDMode.AUTO;
    private UpdateMode r = UpdateMode.GU;
    static final /* synthetic */ boolean A0 = !RK3026Device.class.desiredAssertionStatus();
    private static Class<Enum> x = null;
    private static Method y = null;
    private static Method z = null;
    private static int A = 1;
    private static int B = 1;
    private static int C = 1;
    private static int D = 1;
    private static int E = 1;
    private static int F = 1;

    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;

        static {
            int[] iArr = new int[UpdateMode.values().length];
            b = iArr;
            try {
                iArr[UpdateMode.GU.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                b[UpdateMode.GU_FAST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                b[UpdateMode.GC.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                b[UpdateMode.DU.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                b[UpdateMode.REGAL.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            int[] iArr2 = new int[EPDMode.values().length];
            a = iArr2;
            try {
                iArr2[EPDMode.FULL.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[EPDMode.AUTO.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[EPDMode.TEXT.ordinal()] = 3;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                a[EPDMode.AUTO_PART.ordinal()] = 4;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                a[EPDMode.AUTO_BLACK_WHITE.ordinal()] = 5;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                a[EPDMode.AUTO_A2.ordinal()] = 6;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                a[EPDMode.EPD_REGLA.ordinal()] = 7;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    private RK3026Device() {
    }

    public static RK3026Device createDevice() {
        if (t == null) {
            t = new RK3026Device();
            try {
                Class cls = Class.forName("android.view.View$EINK_MODE");
                x = cls;
                Class[] clsArr = new Class[1];
                clsArr[0] = cls;
                y = View.class.getMethod("requestEpdMode", clsArr);
                Class[] clsArr2 = new Class[2];
                clsArr2[0] = x;
                Class cls2 = Boolean.TYPE;
                clsArr2[1] = cls2;
                z = View.class.getMethod("requestEpdMode", clsArr2);
                Enum[] enumConstants = x.getEnumConstants();
                Method declaredMethod = enumConstants[0].getClass().getDeclaredMethod("getValue", new Class[0]);
                A = ((Integer) declaredMethod.invoke(enumConstants[0], new Object[0])).intValue();
                D = ((Integer) declaredMethod.invoke(enumConstants[1], new Object[0])).intValue();
                B = ((Integer) declaredMethod.invoke(enumConstants[2], new Object[0])).intValue();
                C = ((Integer) declaredMethod.invoke(enumConstants[3], new Object[0])).intValue();
                int iIntValue = ((Integer) declaredMethod.invoke(enumConstants[4], new Object[0])).intValue();
                E = iIntValue;
                if (enumConstants.length > 16) {
                    F = ((Integer) declaredMethod.invoke(enumConstants[16], new Object[0])).intValue();
                } else {
                    F = iIntValue;
                }
                v0 = ReflectUtil.getMethodSafely(View.class, "supportRegal", new Class[0]);
                Class[] clsArr3 = new Class[3];
                clsArr3[0] = cls2;
                Class cls3 = Integer.TYPE;
                clsArr3[1] = cls3;
                clsArr3[2] = cls3;
                w0 = ReflectUtil.getMethodSafely(View.class, "holdDisplay", clsArr3);
                Class[] clsArr4 = new Class[1];
                clsArr4[0] = cls2;
                x0 = ReflectUtil.getMethodSafely(View.class, "enableRegal", clsArr4);
                Class<?> cls4 = Class.forName("android.hardware.DeviceController");
                S = ReflectUtil.getMethodSafely(cls4, "isTouchable", new Class[0]);
                T = ReflectUtil.getMethodSafely(cls4, "getTouchType", new Class[0]);
                U = ReflectUtil.getMethodSafely(cls4, "hasWifi", new Class[0]);
                V = ReflectUtil.getMethodSafely(cls4, "hasAudio", new Class[0]);
                W = ReflectUtil.getMethodSafely(cls4, "hasFrontLight", new Class[0]);
                h0 = ReflectUtil.getMethodSafely(cls4, "hasNaturalLight", new Class[0]);
                X = ReflectUtil.getMethodSafely(cls4, "hasBluetooth", new Class[0]);
                Class[] clsArr5 = new Class[1];
                clsArr5[0] = Context.class;
                Y = ReflectUtil.getMethodSafely(cls4, "openFrontLight", clsArr5);
                Class[] clsArr6 = new Class[1];
                clsArr6[0] = Context.class;
                Z = ReflectUtil.getMethodSafely(cls4, "closeFrontLight", clsArr6);
                Class[] clsArr7 = new Class[1];
                clsArr7[0] = Context.class;
                a0 = ReflectUtil.getMethodSafely(cls4, "getFrontLightValue", clsArr7);
                Class[] clsArr8 = new Class[2];
                clsArr8[0] = Context.class;
                clsArr8[1] = cls3;
                b0 = ReflectUtil.getMethodSafely(cls4, "setFrontLightValue", clsArr8);
                Class[] clsArr9 = new Class[1];
                clsArr9[0] = Context.class;
                c0 = ReflectUtil.getMethodSafely(cls4, "getFrontLightConfigValue", clsArr9);
                Class[] clsArr10 = new Class[2];
                clsArr10[0] = Context.class;
                clsArr10[1] = cls3;
                d0 = ReflectUtil.getMethodSafely(cls4, "setFrontLightConfigValue", clsArr10);
                Class[] clsArr11 = new Class[1];
                clsArr11[0] = Context.class;
                e0 = ReflectUtil.getMethodSafely(cls4, "getFrontLightValues", clsArr11);
                Class[] clsArr12 = new Class[1];
                clsArr12[0] = Context.class;
                i0 = ReflectUtil.getMethodSafely(cls4, "getWarmLightConfigValue", clsArr12);
                Class[] clsArr13 = new Class[1];
                clsArr13[0] = Context.class;
                j0 = ReflectUtil.getMethodSafely(cls4, "getColdLightConfigValue", clsArr13);
                Class[] clsArr14 = new Class[2];
                clsArr14[0] = Context.class;
                clsArr14[1] = cls3;
                k0 = ReflectUtil.getMethodSafely(cls4, "setWarmLightConfigValue", clsArr14);
                Class[] clsArr15 = new Class[2];
                clsArr15[0] = Context.class;
                clsArr15[1] = cls3;
                l0 = ReflectUtil.getMethodSafely(cls4, "setColdLightConfigValue", clsArr15);
                Class[] clsArr16 = new Class[2];
                clsArr16[0] = Context.class;
                clsArr16[1] = cls3;
                m0 = ReflectUtil.getMethodSafely(cls4, "setWarmLightValue", clsArr16);
                Class[] clsArr17 = new Class[2];
                clsArr17[0] = Context.class;
                clsArr17[1] = cls3;
                n0 = ReflectUtil.getMethodSafely(cls4, "setColdLightValue", clsArr17);
                Class[] clsArr18 = new Class[1];
                clsArr18[0] = Context.class;
                o0 = ReflectUtil.getMethodSafely(cls4, "increaseBrightness", clsArr18);
                Class[] clsArr19 = new Class[1];
                clsArr19[0] = Context.class;
                p0 = ReflectUtil.getMethodSafely(cls4, "decreaseBrightness", clsArr19);
                Class[] clsArr20 = new Class[1];
                clsArr20[0] = String.class;
                f0 = ReflectUtil.getMethodSafely(cls4, "readSystemConfig", clsArr20);
                Class[] clsArr21 = new Class[2];
                clsArr21[0] = String.class;
                clsArr21[1] = String.class;
                g0 = ReflectUtil.getMethodSafely(cls4, "saveSystemConfig", clsArr21);
                u0 = ReflectUtil.getMethodSafely(cls4, "systemIntegrityCheck", new Class[0]);
                q0 = ReflectUtil.getMethodSafely(View.class, "requestStopBootAnimation", new Class[0]);
                Class[] clsArr22 = new Class[1];
                clsArr22[0] = cls2;
                r0 = ReflectUtil.getMethodSafely(cls4, "led", clsArr22);
                s0 = ReflectUtil.getMethodSafely(View.class, "enableA2", new Class[0]);
                t0 = ReflectUtil.getMethodSafely(View.class, "disableA2", new Class[0]);
            } catch (ClassNotFoundException e) {
                Log.w(s, e);
            } catch (IllegalAccessException e2) {
                Log.w(s, e2);
            } catch (IllegalArgumentException e3) {
                Log.w(s, e3);
            } catch (NoSuchMethodException e4) {
                Log.w(s, e4);
            } catch (SecurityException e5) {
                Log.w(s, e5);
            } catch (InvocationTargetException e6) {
                Log.w(s, e6);
            }
        }
        return t;
    }

    private Object a(String enumName) {
        return Enum.valueOf(x, enumName);
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public File getStorageRootDirectory() {
        return new File("/mnt");
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public File getExternalStorageDirectory() {
        return new File("/mnt/sdcard");
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public File getRemovableSDCardDirectory() {
        return new File("/mnt/external_sd");
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean isFileOnRemovableSDCard(File file) {
        return file.getAbsolutePath().startsWith(getRemovableSDCardDirectory().getAbsolutePath());
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public PowerManager.WakeLock newWakeLock(Context context, String tag) {
        return ((PowerManager) context.getSystemService("power")).newWakeLock(536870913, tag);
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public String readSystemConfig(Context context, String key) {
        Object objA = a(context, f0, key);
        return (objA == null || objA.equals("")) ? "" : objA.toString();
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean saveSystemConfig(Context context, String key, String value) {
        return ReflectUtil.getSafely((Boolean) a(context, g0, key, value));
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public EPDMode getEpdMode() {
        return this.q;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean setEpdMode(Context context, EPDMode mode) {
        return false;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean setEpdMode(View view, EPDMode mode) {
        try {
            y.invoke(view, a(mode));
            return true;
        } catch (IllegalAccessException e) {
            Log.e(s, "exception", e);
            return false;
        } catch (IllegalArgumentException e2) {
            Log.e(s, "exception", e2);
            return false;
        } catch (InvocationTargetException e3) {
            Log.e(s, "exception", e3);
            return false;
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public UpdateMode getViewDefaultUpdateMode(View view) {
        return this.r;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean setViewDefaultUpdateMode(View view, UpdateMode mode) {
        if (ReflectUtil.invokeMethodSafely(y, view, a(mode)) == null) {
            return false;
        }
        this.r = mode;
        return true;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public int getFrontLightBrightnessMinimum(Context context) {
        return 0;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public int getFrontLightBrightnessMaximum(Context context) {
        return BaseDevice.DITHER_HIGH_CONTRAST;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean openFrontLight(Context context) {
        Boolean bool = (Boolean) a(context, Y, context);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean closeFrontLight(Context context) {
        Boolean bool = (Boolean) a(context, Z, context);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public int getFrontLightDeviceValue(Context context) {
        Integer num = (Integer) a(context, a0, context);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean setFrontLightDeviceValue(Context context, int value) {
        return a(context, b0, context, Integer.valueOf(value)) != null;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public int getFrontLightConfigValue(Context context) {
        return ReflectUtil.getSafely((Integer) a(context, c0, context));
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean setFrontLightConfigValue(Context context, int value) {
        a(context, d0, context, Integer.valueOf(value));
        return true;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public Integer[] getWarmLightValues(Context context) {
        List<Integer> frontLightValueList = getFrontLightValueList(context);
        return (Integer[]) frontLightValueList.toArray(new Integer[frontLightValueList.size()]);
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public Integer[] getColdLightValues(Context context) {
        List<Integer> frontLightValueList = getFrontLightValueList(context);
        return (Integer[]) frontLightValueList.toArray(new Integer[frontLightValueList.size()]);
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public int getWarmLightConfigValue(Context context) {
        Object objA = a(context, i0, context);
        if (objA != null) {
            return ((Integer) objA).intValue();
        }
        return 0;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public int getColdLightConfigValue(Context context) {
        Object objA = a(context, j0, context);
        if (objA != null) {
            return ((Integer) objA).intValue();
        }
        return 0;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean setWarmLightDeviceValue(Context context, int value) {
        a(context, k0, context, Integer.valueOf(value));
        a(context, m0, context, Integer.valueOf(value));
        return true;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean setColdLightDeviceValue(Context context, int value) {
        a(context, l0, context, Integer.valueOf(value));
        a(context, n0, context, Integer.valueOf(value));
        return true;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean increaseBrightness(Context context, int colorTemp) {
        if (hasFLBrightness(context)) {
            return ((Boolean) a(context, o0, context)) != null;
        }
        if (hasCTMBrightness(context)) {
            return a(context, colorTemp, getFrontLightValueList(context), true);
        }
        return false;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean decreaseBrightness(Context context, int colorTemp) {
        if (hasFLBrightness(context)) {
            return ((Boolean) a(context, p0, context)) != null;
        }
        if (hasCTMBrightness(context)) {
            return a(context, colorTemp, getFrontLightValueList(context), false);
        }
        return false;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public List<Integer> getFrontLightValueList(Context context) {
        return (List) a(context, e0, context);
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public UpdateMode getSystemDefaultUpdateMode() {
        return null;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean setSystemDefaultUpdateMode(UpdateMode mode) {
        return false;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void invalidate(View view, UpdateMode mode) {
        try {
            y.invoke(view, a(mode));
        } catch (IllegalAccessException e) {
            Log.e(s, "exception", e);
        } catch (IllegalArgumentException e2) {
            Log.e(s, "exception", e2);
        } catch (InvocationTargetException e3) {
            Log.e(s, "exception", e3);
        }
        view.invalidate();
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void postInvalidate(View view, UpdateMode mode) {
        try {
            y.invoke(view, a(mode));
        } catch (IllegalAccessException e) {
            Log.e(s, "exception", e);
        } catch (IllegalArgumentException e2) {
            Log.e(s, "exception", e2);
        } catch (InvocationTargetException e3) {
            Log.e(s, "exception", e3);
        }
        view.postInvalidate();
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public String getEncryptedDeviceID() {
        try {
            Class<?> cls = Class.forName(w);
            Log.i(s, "Class: android.os.SystemProperties found!");
            try {
                Method method = cls.getMethod("get", String.class, String.class);
                try {
                    return (String) method.invoke(null, z0, y0);
                } catch (IllegalAccessException error) {
                    error.printStackTrace();
                    Log.w(s, "invoke android.os.SystemProperties.get exception, illegal access!");
                    return null;
                } catch (IllegalArgumentException error) {
                    error.printStackTrace();
                    Log.w(s, "invoke android.os.SystemProperties.get exception, illegal argument!");
                    return null;
                } catch (InvocationTargetException error) {
                    error.printStackTrace();
                    Log.w(s, "invoke android.os.SystemProperties.get exception, invocation target exception!");
                    return null;
                }
            } catch (NoSuchMethodException unused4) {
                Log.w(s, "Method: get not found!");
                return null;
            }
        } catch (ClassNotFoundException unused5) {
            Log.w(s, "Class: android.os.SystemProperties not found!");
            return null;
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void stopBootAnimation() {
        a(null, q0, (Object[]) null);
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void led(Context context, boolean on) {
        a(context, r0, Boolean.valueOf(on));
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean supportRegal() {
        Object objInvokeMethodSafely = ReflectUtil.invokeMethodSafely(v0, null, new Object[0]);
        if (objInvokeMethodSafely != null) {
            return ((Boolean) objInvokeMethodSafely).booleanValue();
        }
        return false;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void holdDisplay(boolean hold, UpdateMode updateMode, int ignoreFrame) {
        ReflectUtil.invokeMethodSafely(w0, null, Boolean.valueOf(hold), Integer.valueOf(F), Integer.valueOf(ignoreFrame));
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public int getVCom(Context context, String path) {
        String strA = com.onyx.android.sdk.device.a.a(new File(path));
        if (TextUtils.isEmpty(strA)) {
            return Integer.MIN_VALUE;
        }
        return Integer.parseInt(strA);
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void setVCom(Context context, int value, String path) {
        com.onyx.android.sdk.device.a.a(String.valueOf(value), new File(path));
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void disableA2ForSpecificView(View view) {
        ReflectUtil.invokeMethodSafely(t0, view, new Object[0]);
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void enableA2ForSpecificView(View view) {
        ReflectUtil.invokeMethodSafely(s0, view, new Object[0]);
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean isLegalSystem(Context context) {
        Boolean bool = (Boolean) a(context, u0, new Object[0]);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean hasWifi(Context context) {
        return ReflectUtil.getSafely((Boolean) a(context, U, new Object[0]));
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean hasAudio(Context context) {
        return ReflectUtil.getSafely((Boolean) a(context, V, new Object[0]));
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean hasFLBrightness(Context context) {
        return ReflectUtil.getSafely((Boolean) a(context, W, new Object[0]));
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean hasCTMBrightness(Context context) {
        return ReflectUtil.getSafely((Boolean) a(context, h0, new Object[0]));
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean hasBluetooth(Context context) {
        return ReflectUtil.getSafely((Boolean) a(context, X, new Object[0]));
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean isTouchable(Context context) {
        return ReflectUtil.getSafely((Boolean) a(context, S, new Object[0]));
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void enableRegal(boolean enable) {
        ReflectUtil.invokeMethodSafely(x0, null, Boolean.valueOf(enable));
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public int getFrontLightTypeCTMWarm() {
        return 2;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public int getFrontLightTypeCTMCold() {
        return 3;
    }

    private Object a(EPDMode mode) {
        String str = M;
        switch (a.a[mode.ordinal()]) {
            case 1:
                str = O;
                break;
            case 2:
            case 3:
            case 4:
                str = Q;
                break;
            case 5:
            case BaseDevice.LIGHT_TYPE_CTM_TEMPERATURE /* 6 */:
                str = P;
                break;
            case BaseDevice.LIGHT_TYPE_CTM_BRIGHTNESS /* 7 */:
                str = R;
                break;
            default:
                if (!A0) {
                    throw new AssertionError();
                }
                break;
        }
        return a(str);
    }

    private Object a(UpdateMode mode) {
        String str = M;
        switch (a.b[mode.ordinal()]) {
            case 1:
            case 2:
                str = Q;
                break;
            case 3:
                str = O;
                break;
            case 4:
                str = P;
                break;
            case 5:
                str = R;
                break;
            default:
                if (!A0) {
                    throw new AssertionError();
                }
                break;
        }
        return a(str);
    }

    private Object a(Context context, Method method, Object... args) {
        if (method == null) {
            return null;
        }
        return ReflectUtil.invokeMethodSafely(method, null, args);
    }

    private boolean a(Context context, int colorTemp, List<Integer> steps, boolean progressive) {
        if (steps.size() == 0) {
            return false;
        }
        int warmLightConfigValue = 0;
        if (colorTemp == 3) {
            warmLightConfigValue = getColdLightConfigValue(context);
        } else if (colorTemp == 2) {
            warmLightConfigValue = getWarmLightConfigValue(context);
        }
        Integer[] numArr = (Integer[]) steps.toArray(new Integer[steps.size()]);
        int iAbs = Math.abs(warmLightConfigValue - numArr[0].intValue());
        int i = 0;
        int i2 = 0;
        while (i2 < numArr.length) {
            int i3 = iAbs;
            int iAbs2 = Math.abs(warmLightConfigValue - numArr[i2].intValue());
            int i4 = iAbs2;
            if (i3 >= iAbs2) {
                i = i2;
            } else {
                i4 = iAbs;
            }
            i2++;
            iAbs = i4;
        }
        int i5 = progressive ? i + 1 : i - 1;
        if (i5 < 0 || i5 >= numArr.length) {
            return true;
        }
        int iIntValue = numArr[i5].intValue();
        if (colorTemp == getFrontLightTypeCTMCold()) {
            setColdLightDeviceValue(context, iIntValue);
            return true;
        }
        if (colorTemp != getFrontLightTypeCTMWarm()) {
            return true;
        }
        setWarmLightDeviceValue(context, iIntValue);
        return true;
    }
}
