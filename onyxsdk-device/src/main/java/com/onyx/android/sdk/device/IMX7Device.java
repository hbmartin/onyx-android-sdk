package com.onyx.android.sdk.device;

import android.content.Context;
import android.graphics.Paint;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import com.onyx.android.sdk.api.device.epd.EPDMode;
import com.onyx.android.sdk.api.device.epd.UpdateMode;
import com.onyx.android.sdk.api.device.epd.UpdatePolicy;
import com.onyx.android.sdk.api.device.epd.UpdateScheme;
import com.onyx.android.sdk.utils.ReflectUtil;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class IMX7Device extends BaseDevice {
    private static final int B = 0;
    private static final int C = 1;
    private static final int D = 2;
    private static Method b0;
    private static Method c0;
    private static Method d0;
    private static Method e0;
    private static Method u0;
    private static Method v0;
    private static Method w0;
    private static Method x0;
    private static Method y0;
    private static Method z0;
    private static Method A0;
    private static Method B0;
    private static Method C0;
    private static Method D0;
    private static Method E0;
    private static Method F0;
    private static Method G0;
    private static Method H0;
    private static Method I0;
    private static Method J0;
    private static Method K0;
    private static Method L0;
    private static Method M0;
    static final /* synthetic */ boolean N0 = !IMX7Device.class.desiredAssertionStatus();
    private static final String p = IMX7Device.class.getSimpleName();
    private static IMX7Device q = null;
    private static int r = 0;
    private static int s = 0;
    private static int t = 0;
    private static int u = 0;
    private static int v = 0;
    private static int w = 0;
    private static int x = 0;
    private static int y = 0;
    private static int z = 0;
    private static int A = 0;
    private static Method E = null;
    private static Method F = null;
    private static Method G = null;
    private static Method H = null;
    private static Method I = null;
    private static Method J = null;
    private static Method K = null;
    private static Method L = null;
    private static Method M = null;
    private static Method N = null;
    private static Method O = null;
    private static Method P = null;
    private static Method Q = null;
    private static Method R = null;
    private static Method S = null;
    private static Method T = null;
    private static Method U = null;
    private static Method V = null;
    private static Method W = null;
    private static Method X = null;
    private static Method Y = null;
    private static Method Z = null;
    private static Method a0 = null;
    private static Method f0 = null;
    private static Method g0 = null;
    private static Method h0 = null;
    private static Method i0 = null;
    private static Method j0 = null;
    private static Method k0 = null;
    private static Method l0 = null;
    private static Method m0 = null;
    private static Method n0 = null;
    private static Method o0 = null;
    private static Method p0 = null;
    private static Method q0 = null;
    private static Method r0 = null;
    private static Method s0 = null;
    private static Method t0 = null;

    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;
        static final /* synthetic */ int[] c;
        static final /* synthetic */ int[] d;

        static {
            int[] iArr = new int[UpdatePolicy.values().length];
            d = iArr;
            try {
                iArr[UpdatePolicy.Automatic.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                d[UpdatePolicy.GUIntervally.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[UpdateScheme.values().length];
            c = iArr2;
            try {
                iArr2[UpdateScheme.SNAPSHOT.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                c[UpdateScheme.QUEUE.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                c[UpdateScheme.QUEUE_AND_MERGE.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            int[] iArr3 = new int[UpdateMode.values().length];
            b = iArr3;
            try {
                iArr3[UpdateMode.GU_FAST.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                b[UpdateMode.DU.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                b[UpdateMode.GU.ordinal()] = 3;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                b[UpdateMode.GC.ordinal()] = 4;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                b[UpdateMode.ANIMATION.ordinal()] = 5;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                b[UpdateMode.ANIMATION_QUALITY.ordinal()] = 6;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                b[UpdateMode.GC4.ordinal()] = 7;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                b[UpdateMode.REGAL.ordinal()] = 8;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                b[UpdateMode.REGAL_D.ordinal()] = 9;
            } catch (NoSuchFieldError unused14) {
            }
            int[] iArr4 = new int[EPDMode.values().length];
            a = iArr4;
            try {
                iArr4[EPDMode.FULL.ordinal()] = 1;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                a[EPDMode.AUTO.ordinal()] = 2;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                a[EPDMode.TEXT.ordinal()] = 3;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                a[EPDMode.AUTO_PART.ordinal()] = 4;
            } catch (NoSuchFieldError unused18) {
            }
        }
    }

    private IMX7Device() {
    }

    public static IMX7Device createDevice() {
        IMX7Device iMX7Device = q;
        if (iMX7Device != null) {
            return iMX7Device;
        }
        q = new IMX7Device();
        E = ReflectUtil.getMethodSafely(View.class, "getWindowRotation", new Class[0]);
        Class cls = Integer.TYPE;
        Class cls2 = Boolean.TYPE;
        F = ReflectUtil.getMethodSafely(View.class, "setWindowRotation", cls, cls2, cls);
        Class<?> clsClassForName = ReflectUtil.classForName("android.onyx.ViewUpdateHelper");
        int staticIntFieldSafely = ReflectUtil.getStaticIntFieldSafely(clsClassForName, "EINK_ONYX_AUTO_MASK");
        int staticIntFieldSafely2 = ReflectUtil.getStaticIntFieldSafely(clsClassForName, "EINK_ONYX_GC_MASK");
        int staticIntFieldSafely3 = ReflectUtil.getStaticIntFieldSafely(clsClassForName, "EINK_AUTO_MODE_REGIONAL");
        int staticIntFieldSafely4 = ReflectUtil.getStaticIntFieldSafely(clsClassForName, "EINK_WAIT_MODE_NOWAIT");
        int staticIntFieldSafely5 = ReflectUtil.getStaticIntFieldSafely(clsClassForName, "EINK_WAIT_MODE_WAIT");
        int staticIntFieldSafely6 = ReflectUtil.getStaticIntFieldSafely(clsClassForName, "EINK_WAVEFORM_MODE_DU");
        int staticIntFieldSafely7 = ReflectUtil.getStaticIntFieldSafely(clsClassForName, "EINK_WAVEFORM_MODE_ANIM");
        int staticIntFieldSafely8 = ReflectUtil.getStaticIntFieldSafely(clsClassForName, "EINK_WAVEFORM_MODE_GC4");
        int staticIntFieldSafely9 = ReflectUtil.getStaticIntFieldSafely(clsClassForName, "EINK_WAVEFORM_MODE_GC16");
        int staticIntFieldSafely10 = ReflectUtil.getStaticIntFieldSafely(clsClassForName, "EINK_WAVEFORM_MODE_REAGL");
        int staticIntFieldSafely11 = ReflectUtil.getStaticIntFieldSafely(clsClassForName, "EINK_REAGL_MODE_REAGLD");
        int staticIntFieldSafely12 = ReflectUtil.getStaticIntFieldSafely(clsClassForName, "EINK_UPDATE_MODE_PARTIAL");
        int staticIntFieldSafely13 = ReflectUtil.getStaticIntFieldSafely(clsClassForName, "EINK_UPDATE_MODE_FULL");
        r = staticIntFieldSafely;
        s = staticIntFieldSafely2;
        int i = staticIntFieldSafely3 | staticIntFieldSafely4;
        t = i | staticIntFieldSafely6 | staticIntFieldSafely12;
        u = i | staticIntFieldSafely9 | staticIntFieldSafely12;
        v = staticIntFieldSafely3 | staticIntFieldSafely5 | staticIntFieldSafely9 | staticIntFieldSafely13;
        w = i | staticIntFieldSafely7 | staticIntFieldSafely12;
        x = ReflectUtil.getStaticIntFieldSafely(clsClassForName, "UI_A2_QUALITY_MODE");
        y = i | staticIntFieldSafely8 | staticIntFieldSafely12;
        z = i | staticIntFieldSafely10 | staticIntFieldSafely12;
        A = i | staticIntFieldSafely11 | staticIntFieldSafely10 | staticIntFieldSafely12;
        Class<?> clsClassForName2 = ReflectUtil.classForName("android.onyx.hardware.DeviceController");
        u0 = ReflectUtil.getMethodSafely(clsClassForName2, "openFrontLight", Context.class);
        v0 = ReflectUtil.getMethodSafely(clsClassForName2, "closeFrontLight", Context.class);
        w0 = ReflectUtil.getMethodSafely(clsClassForName2, "getFrontLightValue", Context.class);
        x0 = ReflectUtil.getMethodSafely(clsClassForName2, "setFrontLightValue", Context.class, cls);
        y0 = ReflectUtil.getMethodSafely(clsClassForName2, "getFrontLightConfigValue", Context.class);
        z0 = ReflectUtil.getMethodSafely(clsClassForName2, "setFrontLightConfigValue", Context.class, cls);
        J0 = ReflectUtil.getMethodSafely(clsClassForName2, "useBigPen", cls2);
        K0 = ReflectUtil.getMethodSafely(clsClassForName2, "stopTpd", new Class[0]);
        L0 = ReflectUtil.getMethodSafely(clsClassForName2, "startTpd", new Class[0]);
        I0 = ReflectUtil.getMethodSafely(clsClassForName2, "gotoSleep", Context.class, Long.TYPE);
        A0 = ReflectUtil.getMethodSafely(clsClassForName2, "led", cls2);
        B0 = ReflectUtil.getMethodSafely(clsClassForName2, "setLedColor", String.class, cls);
        M0 = ReflectUtil.getMethodSafely(View.class, "enableOnyxTpd", cls);
        G = ReflectUtil.getMethodSafely(View.class, "setUpdatePolicy", cls, cls);
        f0 = ReflectUtil.getMethodSafely(View.class, "postInvalidate", cls);
        H = ReflectUtil.getMethodSafely(View.class, "refreshScreen", cls);
        I = ReflectUtil.getMethodSafely(View.class, "refreshScreen", cls, cls, cls, cls, cls);
        J = ReflectUtil.getMethodSafely(View.class, "screenshot", cls, String.class);
        M = ReflectUtil.getStaticMethodSafely(View.class, "setStrokeColor", cls);
        N = ReflectUtil.getStaticMethodSafely(View.class, "setStrokeStyle", cls);
        Class cls3 = Float.TYPE;
        O = ReflectUtil.getStaticMethodSafely(View.class, "setStrokeWidth", cls3);
        P = ReflectUtil.getStaticMethodSafely(View.class, "setPainterStyle", cls2, Paint.Style.class, Paint.Join.class, Paint.Cap.class);
        K = ReflectUtil.getMethodSafely(View.class, "supportRegal", new Class[0]);
        L = ReflectUtil.getMethodSafely(View.class, "moveTo", cls3, cls3, cls3);
        Q = ReflectUtil.getMethodSafely(View.class, "lineTo", cls3, cls3, cls);
        R = ReflectUtil.getMethodSafely(View.class, "quadTo", cls3, cls3, cls);
        S = ReflectUtil.getMethodSafely(View.class, "getTouchWidth", new Class[0]);
        T = ReflectUtil.getMethodSafely(View.class, "getTouchHeight", new Class[0]);
        U = ReflectUtil.getMethodSafely(View.class, "enablePost", cls);
        V = ReflectUtil.getMethodSafely(View.class, "setScreenHandWritingPenState", cls);
        W = ReflectUtil.getMethodSafely(View.class, "setScreenHandWritingRegionLimit", cls, cls, cls, cls);
        X = ReflectUtil.getMethodSafely(View.class, "applyGammaCorrection", cls2, cls);
        Y = ReflectUtil.getStaticMethodSafely(View.class, "startStroke", cls3, cls3, cls3, cls3, cls3, cls3);
        Z = ReflectUtil.getStaticMethodSafely(View.class, "addStrokePoint", cls3, cls3, cls3, cls3, cls3, cls3);
        a0 = ReflectUtil.getStaticMethodSafely(View.class, "finishStroke", cls3, cls3, cls3, cls3, cls3, cls3);
        g0 = ReflectUtil.getMethodSafely(View.class, "invalidate", cls);
        h0 = ReflectUtil.getMethodSafely(View.class, "invalidate", cls, cls, cls, cls, cls);
        k0 = ReflectUtil.getMethodSafely(View.class, "setDefaultUpdateMode", cls);
        i0 = ReflectUtil.getMethodSafely(View.class, "getDefaultUpdateMode", new Class[0]);
        j0 = ReflectUtil.getMethodSafely(View.class, "resetUpdateMode", new Class[0]);
        l0 = ReflectUtil.getMethodSafely(View.class, "getGlobalUpdateMode", new Class[0]);
        m0 = ReflectUtil.getMethodSafely(View.class, "setGlobalUpdateMode", cls);
        n0 = ReflectUtil.getMethodSafely(View.class, "setFirstDrawUpdateMode", cls);
        o0 = ReflectUtil.getMethodSafely(View.class, "setWaveformAndScheme", cls, cls, cls);
        q0 = ReflectUtil.getMethodSafely(View.class, "resetWaveformAndScheme", new Class[0]);
        p0 = ReflectUtil.getMethodSafely(View.class, "applyApplicationFastMode", String.class, cls2, cls2);
        r0 = ReflectUtil.getMethodSafely(View.class, "enableScreenUpdate", cls2);
        s0 = ReflectUtil.getMethodSafely(View.class, "setDisplayScheme", cls);
        t0 = ReflectUtil.getMethodSafely(View.class, "waitForUpdateFinished", new Class[0]);
        C0 = ReflectUtil.getMethodSafely(clsClassForName2, "setVCom", Context.class, cls, String.class);
        D0 = ReflectUtil.getMethodSafely(clsClassForName2, "getVCom", String.class);
        E0 = ReflectUtil.getMethodSafely(clsClassForName2, "updateWaveform", String.class, String.class);
        F0 = ReflectUtil.getMethodSafely(clsClassForName2, "readSystemConfig", String.class);
        G0 = ReflectUtil.getMethodSafely(clsClassForName2, "saveSystemConfig", String.class, String.class);
        H0 = ReflectUtil.getMethodSafely(clsClassForName2, "updateMetadataDB", String.class, String.class);
        b0 = ReflectUtil.getMethodSafely(View.class, "enableA2", new Class[0]);
        c0 = ReflectUtil.getMethodSafely(View.class, "disableA2", new Class[0]);
        d0 = ReflectUtil.getMethodSafely(Environment.class, "getStorageRootDirectory", new Class[0]);
        e0 = ReflectUtil.getMethodSafely(Environment.class, "getRemovableSDCardDirectory", new Class[0]);
        Log.d(p, "init device EINK_ONYX_GC_MASK.");
        return q;
    }

    private Object a(Context context, Method method, Object... args) {
        if (method == null) {
            return null;
        }
        return ReflectUtil.invokeMethodSafely(method, null, args);
    }

    private UpdateMode b(int value) {
        if (value == t) {
            return UpdateMode.DU;
        }
        if (value == u) {
            return UpdateMode.GU;
        }
        return value == v ? UpdateMode.GC : UpdateMode.GC;
    }

    public int getWindowRotation() {
        Method method = E;
        if (method == null) {
            return 0;
        }
        try {
            return ((Integer) method.invoke(null, new Object[0])).intValue();
        } catch (IllegalAccessException unused) {
            return 0;
        } catch (IllegalArgumentException unused2) {
            return 0;
        } catch (InvocationTargetException unused3) {
            return 0;
        }
    }

    public boolean setWindowRotation(int rotation) {
        Method method = F;
        if (method == null) {
            return false;
        }
        try {
            Object[] objArr = new Object[3];
            objArr[0] = Integer.valueOf(rotation);
            objArr[1] = Boolean.TRUE;
            objArr[2] = 1;
            method.invoke(null, objArr);
            return true;
        } catch (IllegalAccessException unused) {
            return false;
        } catch (IllegalArgumentException unused2) {
            return false;
        } catch (InvocationTargetException unused3) {
            return false;
        }
    }

    public boolean setUpdatePolicy(View view, UpdatePolicy policy, int guInterval) {
        int iA = a(policy);
        try {
            if (!N0 && G == null) {
                throw new AssertionError();
            }
            Method method = G;
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(iA);
            objArr[1] = Integer.valueOf(guInterval);
            method.invoke(view, objArr);
            return true;
        } catch (IllegalAccessException unused) {
            return false;
        } catch (IllegalArgumentException unused2) {
            return false;
        } catch (InvocationTargetException unused3) {
            return false;
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public File getStorageRootDirectory() {
        return (File) ReflectUtil.invokeMethodSafely(d0, null, new Object[0]);
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public File getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory();
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public File getRemovableSDCardDirectory() {
        return (File) ReflectUtil.invokeMethodSafely(e0, null, new Object[0]);
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean isFileOnRemovableSDCard(File file) {
        return file.getAbsolutePath().startsWith(getRemovableSDCardDirectory().getAbsolutePath());
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public EPDMode getEpdMode() {
        return EPDMode.AUTO;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean setEpdMode(Context context, EPDMode mode) {
        applySysScopeUpdate(a(mode), UpdateScheme.QUEUE_AND_MERGE, Integer.MAX_VALUE);
        return false;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void invalidate(View view, UpdateMode mode) {
        int iA = a(mode);
        try {
            if (!N0 && g0 == null) {
                throw new AssertionError();
            }
            Method method = g0;
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(iA);
            method.invoke(view, objArr);
        } catch (IllegalAccessException unused) {
        } catch (IllegalArgumentException unused2) {
        } catch (InvocationTargetException unused3) {
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void postInvalidate(View view, UpdateMode mode) {
        int iA = a(mode);
        try {
            if (!N0 && f0 == null) {
                throw new AssertionError();
            }
            Log.d(p, "dst mode: " + iA);
            Method method = f0;
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(iA);
            method.invoke(view, objArr);
        } catch (IllegalAccessException unused) {
        } catch (IllegalArgumentException unused2) {
        } catch (InvocationTargetException unused3) {
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void refreshScreen(View view, UpdateMode mode) {
        int iA = a(mode);
        try {
            if (!N0 && H == null) {
                throw new AssertionError();
            }
            Method method = H;
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(iA);
            method.invoke(view, objArr);
        } catch (IllegalAccessException unused) {
        } catch (IllegalArgumentException unused2) {
        } catch (InvocationTargetException unused3) {
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void refreshScreenRegion(View view, int left, int top, int width, int height, UpdateMode mode) {
        int iA = a(mode);
        try {
            if (!N0 && I == null) {
                throw new AssertionError();
            }
            Method method = I;
            Object[] objArr = new Object[5];
            objArr[0] = Integer.valueOf(left);
            objArr[1] = Integer.valueOf(top);
            objArr[2] = Integer.valueOf(width);
            objArr[3] = Integer.valueOf(height);
            objArr[4] = Integer.valueOf(iA);
            method.invoke(view, objArr);
        } catch (Exception unused) {
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void screenshot(View view, int rotation, String path) {
        try {
            Method method = J;
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(rotation);
            objArr[1] = path;
            ReflectUtil.invokeMethodSafely(method, view, objArr);
        } catch (Exception unused) {
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void setStrokeColor(int color) {
        try {
            Method method = M;
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(color);
            ReflectUtil.invokeMethodSafely(method, null, objArr);
        } catch (Exception unused) {
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void setStrokeStyle(int style) {
        try {
            Method method = N;
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(style);
            ReflectUtil.invokeMethodSafely(method, null, objArr);
        } catch (Exception unused) {
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void setPainterStyle(boolean antiAlias, Paint.Style strokeStyle, Paint.Join joinStyle, Paint.Cap capStyle) {
        try {
            Method method = P;
            Object[] objArr = new Object[4];
            objArr[0] = Boolean.valueOf(antiAlias);
            objArr[1] = strokeStyle;
            objArr[2] = joinStyle;
            objArr[3] = capStyle;
            ReflectUtil.invokeMethodSafely(method, null, objArr);
        } catch (Exception unused) {
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void setStrokeWidth(float width) {
        try {
            Method method = O;
            Object[] objArr = new Object[1];
            objArr[0] = Float.valueOf(width);
            ReflectUtil.invokeMethodSafely(method, null, objArr);
        } catch (Exception unused) {
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void moveTo(float x2, float y2, float width) {
        try {
            Method method = L;
            Object[] objArr = new Object[3];
            objArr[0] = Float.valueOf(x2);
            objArr[1] = Float.valueOf(y2);
            objArr[2] = Float.valueOf(width);
            ReflectUtil.invokeMethodSafely(method, null, objArr);
        } catch (Exception unused) {
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean supportDFB() {
        return Q != null;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean supportRegal() {
        Boolean bool;
        Method method = K;
        if (method == null || (bool = (Boolean) ReflectUtil.invokeMethodSafely(method, null, new Object[0])) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void lineTo(float x2, float y2, UpdateMode mode) {
        int iA = a(mode);
        try {
            Method method = Q;
            Object[] objArr = new Object[3];
            objArr[0] = Float.valueOf(x2);
            objArr[1] = Float.valueOf(y2);
            objArr[2] = Integer.valueOf(iA);
            ReflectUtil.invokeMethodSafely(method, null, objArr);
        } catch (Exception unused) {
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void quadTo(float x2, float y2, UpdateMode mode) {
        int iA = a(mode);
        try {
            Method method = R;
            Object[] objArr = new Object[3];
            objArr[0] = Float.valueOf(x2);
            objArr[1] = Float.valueOf(y2);
            objArr[2] = Integer.valueOf(iA);
            ReflectUtil.invokeMethodSafely(method, null, objArr);
        } catch (Exception unused) {
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public float getTouchWidth() {
        try {
            return ((Float) ReflectUtil.invokeMethodSafely(S, null, new Object[0])).floatValue();
        } catch (Exception unused) {
            return 0.0f;
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public float getTouchHeight() {
        try {
            return ((Float) ReflectUtil.invokeMethodSafely(T, null, new Object[0])).floatValue();
        } catch (Exception unused) {
            return 0.0f;
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public float startStroke(float baseWidth, float x2, float y2, float pressure, float size, float time) {
        try {
            Method method = Y;
            Object[] objArr = new Object[6];
            objArr[0] = Float.valueOf(baseWidth);
            objArr[1] = Float.valueOf(x2);
            objArr[2] = Float.valueOf(y2);
            objArr[3] = Float.valueOf(pressure);
            objArr[4] = Float.valueOf(size);
            objArr[5] = Float.valueOf(time);
            return ((Float) ReflectUtil.invokeMethodSafely(method, null, objArr)).floatValue();
        } catch (Exception unused) {
            return baseWidth;
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public float addStrokePoint(float baseWidth, float x2, float y2, float pressure, float size, float time) {
        try {
            Method method = Z;
            Object[] objArr = new Object[6];
            objArr[0] = Float.valueOf(baseWidth);
            objArr[1] = Float.valueOf(x2);
            objArr[2] = Float.valueOf(y2);
            objArr[3] = Float.valueOf(pressure);
            objArr[4] = Float.valueOf(size);
            objArr[5] = Float.valueOf(time);
            return ((Float) ReflectUtil.invokeMethodSafely(method, null, objArr)).floatValue();
        } catch (Exception unused) {
            return baseWidth;
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public float finishStroke(float baseWidth, float x2, float y2, float pressure, float size, float time) {
        try {
            Method method = a0;
            Object[] objArr = new Object[6];
            objArr[0] = Float.valueOf(baseWidth);
            objArr[1] = Float.valueOf(x2);
            objArr[2] = Float.valueOf(y2);
            objArr[3] = Float.valueOf(pressure);
            objArr[4] = Float.valueOf(size);
            objArr[5] = Float.valueOf(time);
            return ((Float) ReflectUtil.invokeMethodSafely(method, null, objArr)).floatValue();
        } catch (Exception unused) {
            return baseWidth;
        }
    }

    @Override
    boolean hasStrokeStyleConfigurationCapability() {
        return ReflectUtil.isStaticMethodAvailable(M)
                && ReflectUtil.isStaticMethodAvailable(N)
                && ReflectUtil.isStaticMethodAvailable(O);
    }

    @Override
    boolean hasStrokeDataTransportCapability() {
        return ReflectUtil.isStaticMethodAvailable(Y)
                && ReflectUtil.isStaticMethodAvailable(Z)
                && ReflectUtil.isStaticMethodAvailable(a0);
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void enterScribbleMode(View view) {
        try {
            Method method = U;
            Object[] objArr = new Object[1];
            objArr[0] = 0;
            ReflectUtil.invokeMethodSafely(method, view, objArr);
        } catch (Exception unused) {
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void leaveScribbleMode(View view) {
        try {
            Method method = U;
            Object[] objArr = new Object[1];
            objArr[0] = 1;
            ReflectUtil.invokeMethodSafely(method, view, objArr);
        } catch (Exception unused) {
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void enablePost(View view, int enable) {
        try {
            Method method = U;
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(enable);
            ReflectUtil.invokeMethodSafely(method, view, objArr);
        } catch (Exception unused) {
        }
    }

    public boolean supportScreenHandWriting() {
        return V != null;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void setScreenHandWritingPenState(View view, int penState) {
        try {
            Method method = V;
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(penState);
            ReflectUtil.invokeMethodSafely(method, view, objArr);
        } catch (Exception unused) {
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void setScreenHandWritingRegionLimit(View view, int left, int top, int right, int bottom) {
        try {
            Method method = W;
            Object[] objArr = new Object[4];
            objArr[0] = Integer.valueOf(left);
            objArr[1] = Integer.valueOf(top);
            objArr[2] = Integer.valueOf(right);
            objArr[3] = Integer.valueOf(bottom);
            ReflectUtil.invokeMethodSafely(method, view, objArr);
        } catch (Exception unused) {
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void applyGammaCorrection(boolean apply, int value) {
        ReflectUtil.invokeMethodSafely(X, null, Boolean.valueOf(apply), Integer.valueOf(value));
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean enableScreenUpdate(View view, boolean enable) {
        try {
            Method method = r0;
            Object[] objArr = new Object[1];
            objArr[0] = Boolean.valueOf(enable);
            method.invoke(view, objArr);
            return true;
        } catch (Exception unused) {
            return true;
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean setDisplayScheme(int scheme) {
        ReflectUtil.invokeMethodSafely(s0, null, Integer.valueOf(scheme));
        return true;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void waitForUpdateFinished() {
        ReflectUtil.invokeMethodSafely(t0, null, new Object[0]);
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void useBigPen(boolean use) {
        a(null, J0, Boolean.valueOf(use));
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void stopTpd() {
        a(null, K0, new Object[0]);
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void startTpd() {
        a(null, L0, new Object[0]);
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void enableTpd(boolean z2) {
        ReflectUtil.invokeMethodSafely(M0, null, Integer.valueOf(z2 ? 1 : 0));
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public UpdateMode getViewDefaultUpdateMode(View view) {
        Integer num = (Integer) ReflectUtil.invokeMethodSafely(i0, view, new Object[0]);
        return num == null ? UpdateMode.GU : b(num.intValue());
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void resetViewUpdateMode(View view) {
        ReflectUtil.invokeMethodSafely(j0, view, new Object[0]);
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean setViewDefaultUpdateMode(View view, UpdateMode mode) {
        return ReflectUtil.invokeMethodSafely(k0, view, Integer.valueOf(a(mode))) != null;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public UpdateMode getSystemDefaultUpdateMode() {
        Integer num = (Integer) ReflectUtil.invokeMethodSafely(l0, null, new Object[0]);
        return num == null ? UpdateMode.GU : b(num.intValue());
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean setSystemDefaultUpdateMode(UpdateMode mode) {
        return ReflectUtil.invokeMethodSafely(m0, null, Integer.valueOf(a(mode))) != null;
    }

    public boolean setFirstDrawUpdateMode(UpdateMode mode) {
        return ReflectUtil.invokeMethodSafely(n0, null, Integer.valueOf(a(mode))) != null;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean applySysScopeUpdate(UpdateMode mode, UpdateScheme scheme, int count) {
        return ReflectUtil.invokeMethodSafely(o0, null, Integer.valueOf(a(mode)), Integer.valueOf(a(scheme)), Integer.valueOf(count)) != null;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean clearSysScopeUpdate() {
        return ReflectUtil.invokeMethodSafely(q0, null, new Object[0]) != null;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean applyAppScopeUpdate(String application, boolean enable, boolean clear) {
        return ReflectUtil.invokeMethodSafely(p0, null, application, Boolean.valueOf(enable), Boolean.valueOf(clear)) != null;
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
        Boolean bool = (Boolean) a(context, u0, context);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean closeFrontLight(Context context) {
        Boolean bool = (Boolean) a(context, v0, context);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public int getFrontLightDeviceValue(Context context) {
        Integer num = (Integer) a(context, w0, context);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean setFrontLightDeviceValue(Context context, int value) {
        return a(context, x0, context, Integer.valueOf(value)) != null;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public int getFrontLightConfigValue(Context context) {
        return ReflectUtil.getSafely((Integer) a(context, y0, context));
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean setFrontLightConfigValue(Context context, int value) {
        a(context, z0, context, Integer.valueOf(value));
        return true;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public List<Integer> getFrontLightValueList(Context context) {
        return Arrays.asList(0, 8, 16, 24, 32, 40, 48, 56, 64, 72, 80, 88, 96, 104, 112, 120, Integer.valueOf(BaseDevice.DITHER_NORMAL), 136, 144, 152, Integer.valueOf(BaseDevice.COLOR_DEVICE_DITHER_CONTRAST));
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void led(Context context, boolean on) {
        a(context, A0, Boolean.valueOf(on));
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean setLedColor(String color, int on) {
        a(null, B0, color, Integer.valueOf(on));
        return true;
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void setVCom(Context context, int value, String path) {
        a(context, C0, context, Integer.valueOf(value), path);
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public int getVCom(Context context, String path) {
        Integer num = (Integer) a(context, D0, path);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void updateWaveform(Context context, String path, String target) {
        a(context, E0, path, target);
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public String readSystemConfig(Context context, String key) {
        Object objA = a(context, F0, key);
        return (objA == null || objA.equals("")) ? "" : objA.toString();
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public boolean saveSystemConfig(Context context, String key, String value) {
        return ReflectUtil.getSafely((Boolean) a(context, G0, key, value));
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void updateMetadataDB(Context context, String path, String target) {
        a(context, H0, path, target);
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void disableA2ForSpecificView(View view) {
        ReflectUtil.invokeMethodSafely(c0, view, new Object[0]);
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void enableA2ForSpecificView(View view) {
        ReflectUtil.invokeMethodSafely(b0, view, new Object[0]);
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void gotoSleep(Context context) {
        ReflectUtil.invokeMethodSafely(I0, context, Long.valueOf(System.currentTimeMillis()));
    }

    UpdateMode a(EPDMode mode) {
        switch (a.a[mode.ordinal()]) {
            case 1:
                return UpdateMode.GC;
            case 2:
            case 3:
            case 4:
                return UpdateMode.GU;
            default:
                return UpdateMode.DU;
        }
    }

    @Override // com.onyx.android.sdk.device.BaseDevice
    public void invalidate(View view, int left, int top, int right, int bottom, UpdateMode mode) {
        int iA = a(mode);
        try {
            if (!N0 && h0 == null) {
                throw new AssertionError();
            }
            Method method = h0;
            Object[] objArr = new Object[5];
            objArr[0] = Integer.valueOf(left);
            objArr[1] = Integer.valueOf(top);
            objArr[2] = Integer.valueOf(right);
            objArr[3] = Integer.valueOf(bottom);
            objArr[4] = Integer.valueOf(iA);
            method.invoke(view, objArr);
        } catch (IllegalAccessException unused) {
        } catch (IllegalArgumentException unused2) {
        } catch (InvocationTargetException unused3) {
        }
    }

    int a(UpdateMode mode) {
        int i = v;
        switch (a.b[mode.ordinal()]) {
            case 1:
            case 2:
                i = t;
                break;
            case 3:
                i = u;
                break;
            case 4:
                i = v;
                break;
            case 5:
                i = w;
                break;
            case BaseDevice.LIGHT_TYPE_CTM_TEMPERATURE /* 6 */:
                i = x;
                break;
            case BaseDevice.LIGHT_TYPE_CTM_BRIGHTNESS /* 7 */:
                i = y;
                break;
            case 8:
                int i2 = z;
                i = i2;
                if (i2 == 0) {
                    i = u;
                }
                break;
            case 9:
                int i3 = A;
                i = i3;
                if (i3 == 0) {
                    i = u;
                }
                break;
            default:
                if (!N0) {
                    throw new AssertionError();
                }
                break;
        }
        return i;
    }

    private int a(UpdateScheme scheme) {
        int i = 1;
        int i2 = a.c[scheme.ordinal()];
        if (i2 == 1) {
            i = 0;
        } else if (i2 == 2) {
            i = 1;
        } else if (i2 == 3) {
            i = 2;
        } else if (!N0) {
            throw new AssertionError();
        }
        return i;
    }

    private static int a(UpdatePolicy policy) {
        int i = u;
        int i2 = a.d[policy.ordinal()];
        if (i2 == 1) {
            i |= r;
        } else if (i2 != 2) {
            if (!N0) {
                throw new AssertionError();
            }
        } else {
            i |= s;
        }
        return i;
    }
}
