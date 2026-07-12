// 
// 

package com.onyx.android.sdk.device;

import android.telephony.SubscriptionManager;
import android.os.SystemClock;
import android.os.PowerManager;
import com.onyx.android.sdk.api.utils.StringUtils;
import java.util.Map;
import java.util.Locale;
import android.net.wifi.WifiManager;
import com.onyx.android.sdk.utils.DeviceBroadcastHelper;
import com.onyx.android.sdk.api.device.epd.UpdateOption;
import android.os.Build;
import android.annotation.SuppressLint;
import android.view.Window;
import android.content.ComponentName;
import com.onyx.android.sdk.utils.LightUtils;
import com.onyx.android.sdk.api.device.tp.UpgradeConfig;
import java.util.Arrays;
import android.graphics.Rect;
import android.util.Log;
import com.onyx.android.sdk.api.device.epd.EPDMode;
import java.util.Iterator;
import java.util.Collections;
import java.util.List;
import androidx.annotation.Nullable;
import java.io.File;
import android.annotation.TargetApi;
import com.onyx.android.sdk.api.device.epd.UpdatePolicy;
import com.onyx.android.sdk.api.device.epd.UpdateMode;
import com.onyx.android.sdk.api.device.epd.UpdateScheme;
import android.media.AudioManager;
import android.os.LocaleList;
import com.onyx.android.sdk.api.utils.CompatibilityUtil;
import com.onyx.android.sdk.utils.Debug;
import android.app.ActivityManager;
import android.webkit.WebView;
import android.os.Environment;
import android.hardware.input.InputManager;
import android.graphics.Paint;
import android.content.Context;
import com.onyx.android.sdk.utils.ReflectUtil;
import android.view.View;
import androidx.annotation.RequiresApi;
import java.lang.reflect.Method;

public class SDMDevice extends BaseDevice
{
    private static final String p;
    public static final int WINDOWING_MODE_UNDEFINED = 0;
    public static final int WINDOWING_MODE_FULLSCREEN = 1;
    public static final int WINDOWING_MODE_PINNED = 2;
    public static final int WINDOWING_MODE_SPLIT_SCREEN_PRIMARY = 3;
    public static final int WINDOWING_MODE_SPLIT_SCREEN_SECONDARY = 4;
    public static final int WINDOWING_MODE_FREEFORM = 5;
    private static SDMDevice q;
    private static int r;
    private static int s;
    private static int t;
    private static int u;
    private static int v;
    private static int w;
    private static int x;
    private static int y;
    private static int z;
    private static int A;
    private static int B;
    private static int C;
    private static int D;
    private static int E;
    private static int F;
    private static int G;
    private static int H;
    private static int I;
    private static int J;
    private static final int K = 0;
    private static final int L = 1;
    private static final int M = 2;
    private static final int N = 79;
    private static final int O = 80;
    private static final int P = 100;
    private static final int Q = 100;
    private static final String R = "/sys/class/power_supply/battery/charge_control_start_threshold";
    private static final String S = "/sys/class/power_supply/battery/charge_control_end_threshold";
    private static final String T = "/onyxconfig/charge_control_start_threshold";
    private static final String U = "/onyxconfig/charge_control_end_threshold";
    private static final String V = "/sys/class/backlight/white/brightness";
    private static final String W = "/sys/class/backlight/warm/brightness";
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
    private static Method N0;
    private static Method O0;
    private static Method P0;
    private static Method Q0;
    private static Method R0;
    private static Method S0;
    private static Method T0;
    private static Method U0;
    private static Method V0;
    private static Method W0;
    private static Method X0;
    private static Method Y0;
    private static Method Z0;
    private static Method a1;
    private static Method b1;
    private static Method c1;
    private static Method d1;
    private static Method e1;
    private static Method f1;
    private static Method g1;
    private static Method h1;
    private static Method i1;
    private static Method j1;
    private static Method k1;
    private static Method l1;
    private static Method m1;
    private static Method n1;
    private static Method o1;
    private static Method p1;
    private static Method q1;
    private static Method r1;
    private static Method s1;
    private static Method t1;
    private static Method u1;
    private static Method v1;
    private static Method w1;
    private static Method x1;
    private static Method y1;
    private static Method z1;
    private static Method A1;
    private static Method B1;
    private static Method C1;
    private static Method D1;
    private static Method E1;
    private static Method F1;
    private static Method G1;
    private static Method H1;
    private static Method I1;
    private static Method J1;
    private static Method K1;
    private static Method L1;
    private static Method M1;
    private static Method N1;
    private static Method O1;
    private static Method P1;
    private static Method Q1;
    private static Method R1;
    private static Method S1;
    private static Method T1;
    private static Method U1;
    private static Method V1;
    private static Method W1;
    private static Method X1;
    private static Method Y1;
    private static Method Z1;
    private static Method a2;
    private static Method b2;
    private static Method c2;
    private static Method d2;
    private static Method e2;
    private static Method f2;
    private static Method g2;
    private static Method h2;
    private static Method i2;
    private static Method j2;
    private static Method k2;
    private static Method l2;
    private static Method m2;
    private static Method n2;
    private static Method o2;
    private static Method p2;
    private static Method q2;
    private static Method r2;
    private static Method s2;
    private static Method t2;
    private static Method u2;
    private static Method v2;
    private static Method w2;
    private static Method x2;
    private static Method y2;
    private static Method z2;
    private static Method A2;
    private static Method B2;
    private static Method C2;
    private static Method D2;
    private static Method E2;
    private static Method F2;
    private static Method G2;
    private static Method H2;
    private static Method I2;
    private static Method J2;
    private static Method K2;
    private static Method L2;
    private static Method M2;
    private static Method N2;
    private static Method O2;
    private static Method P2;
    private static Method Q2;
    private static Method R2;
    private static Method S2;
    private static Method T2;
    private static Method U2;
    private static Method V2;
    private static Method W2;
    private static Method X2;
    private static Method Y2;
    private static Method Z2;
    private static Method a3;
    private static Method b3;
    private static Method c3;
    private static Method d3;
    private static Method e3;
    private static Method f3;
    private static Method g3;
    private static Method h3;
    private static Method i3;
    private static Method j3;
    private static Method k3;
    private static Method l3;
    private static Method m3;
    private static Method n3;
    private static Method o3;
    private static Method p3;
    private static Method q3;
    private static Method r3;
    private static Method s3;
    private static Method t3;
    private static Method u3;
    private static Method v3;
    private static Method w3;
    private static Method x3;
    private static Method y3;
    private static Method z3;
    private static Method A3;
    @RequiresApi(29)
    private static Method B3;
    @Deprecated
    private static Method C3;
    @Deprecated
    private static Method D3;
    @Deprecated
    private static Method E3;
    @Deprecated
    private static Method F3;
    @Deprecated
    private static Method G3;
    private static Method H3;
    private static Method I3;
    private static Method J3;
    private static Method K3;
    private static Method L3;
    private static Method M3;
    private static Method N3;
    private static Method O3;
    private static Method P3;
    private static Method Q3;
    private static Method R3;
    private static Method S3;
    private static Method T3;
    private static Method U3;
    private static Method V3;
    private static Method W3;
    private static Method X3;
    private static Method Y3;
    private static Method Z3;
    private static Method a4;
    private static Method b4;
    private static Method c4;
    private static Method d4;
    private static Method e4;
    private static Method f4;
    private static Method g4;
    private static Method h4;
    static final /* synthetic */ boolean i4;
    
    private SDMDevice() {
    }
    
    public static SDMDevice createDevice() {
        final SDMDevice q;
        if ((q = SDMDevice.q) == null) {
            SDMDevice.q = new SDMDevice();
            final Class<View> clazz;
            final Class<View> cls = clazz = View.class;
            final Class<?> classForName;
            final Class<?> clazz2 = classForName = ReflectUtil.classForName("android.onyx.ViewUpdateHelper");
            final int staticIntFieldSafely = ReflectUtil.getStaticIntFieldSafely(clazz2, "EINK_ONYX_AUTO_MASK");
            final int staticIntFieldSafely2 = ReflectUtil.getStaticIntFieldSafely(clazz2, "EINK_ONYX_GC_MASK");
            final int staticIntFieldSafely3 = ReflectUtil.getStaticIntFieldSafely(clazz2, "EINK_AUTO_MODE_REGIONAL");
            final Class<?> clazz3 = classForName;
            final int s = staticIntFieldSafely2;
            final int r = staticIntFieldSafely;
            final Class<?> cls2 = classForName;
            final int staticIntFieldSafely4 = ReflectUtil.getStaticIntFieldSafely(cls2, "EINK_WAIT_MODE_NOWAIT");
            final int staticIntFieldSafely5 = ReflectUtil.getStaticIntFieldSafely(cls2, "EINK_WAVEFORM_MODE_ANIM");
            final int staticIntFieldSafely6 = ReflectUtil.getStaticIntFieldSafely(cls2, "EINK_WAVEFORM_MODE_REAGL");
            final int staticIntFieldSafely7 = ReflectUtil.getStaticIntFieldSafely(cls2, "EINK_REAGL_MODE_REAGLD");
            final int staticIntFieldSafely8 = ReflectUtil.getStaticIntFieldSafely(cls2, "EINK_UPDATE_MODE_PARTIAL");
            final Class[] parameterTypes = { null };
            final Class<Boolean> type = Boolean.TYPE;
            parameterTypes[0] = type;
            SDMDevice.L0 = ReflectUtil.getMethodSafely(cls2, "enableColorCU", (Class<?>[])parameterTypes);
            SDMDevice.M0 = ReflectUtil.getMethodSafely(cls2, "enableNightMode", type);
            final Class[] parameterTypes2;
            final Class[] array = parameterTypes2 = new Class[2];
            array[1] = (array[0] = type);
            SDMDevice.N0 = ReflectUtil.getMethodSafely(cls2, "enableNightMode", (Class<?>[])parameterTypes2);
            SDMDevice.O0 = ReflectUtil.getMethodSafely(cls2, "supportNightMode", (Class<?>[])new Class[0]);
            SDMDevice.r = r;
            SDMDevice.s = s;
            SDMDevice.t = ReflectUtil.getStaticIntFieldSafely(clazz3, "UI_DU_MODE");
            SDMDevice.u = ReflectUtil.getStaticIntFieldSafely(clazz3, "UI_DU_QUALITY_MODE");
            SDMDevice.v = ReflectUtil.getStaticIntFieldSafely(clazz3, "UI_GU_MODE");
            SDMDevice.w = ReflectUtil.getStaticIntFieldSafely(clazz3, "UI_GC_MODE");
            SDMDevice.x = ReflectUtil.getStaticIntFieldSafely(clazz3, "UI_GCC_MODE");
            SDMDevice.y = ReflectUtil.getStaticIntFieldSafely(clazz3, "UI_DEEP_GC_MODE");
            final int n = staticIntFieldSafely3 | staticIntFieldSafely4;
            final int n2 = staticIntFieldSafely7;
            final Class<?> cls3 = classForName;
            SDMDevice.z = (n | staticIntFieldSafely5 | staticIntFieldSafely8);
            SDMDevice.A = ReflectUtil.getStaticIntFieldSafely(cls3, "UI_A2_QUALITY_MODE");
            SDMDevice.B = ReflectUtil.getStaticIntFieldSafely(cls3, "UI_MONO_A2_MODE");
            SDMDevice.C = ReflectUtil.getStaticIntFieldSafely(cls3, "UI_X_A2_MODE");
            SDMDevice.D = ReflectUtil.getStaticIntFieldSafely(cls3, "UI_GC4_MODE");
            SDMDevice.E = ReflectUtil.getStaticIntFieldSafely(cls3, "UI_REGAL_MODE");
            SDMDevice.F = (n | n2 | staticIntFieldSafely6 | staticIntFieldSafely8);
            SDMDevice.G = ReflectUtil.getStaticIntFieldSafely(clazz2, "UI_REGAL_PLUS_MODE");
            SDMDevice.H = ReflectUtil.getStaticIntFieldSafely(clazz2, "UI_DEFAULT_MODE");
            SDMDevice.I = ReflectUtil.getStaticIntFieldSafely(clazz2, "HAND_WRITING_REPAINT_MODE");
            SDMDevice.J = ReflectUtil.getStaticIntFieldSafely(clazz2, "UI_DU4_MODE");
            SDMDevice.q1 = ReflectUtil.getMethodSafely(clazz2, "repaintEverything", (Class<?>[])new Class[0]);
            final Class<?> classForName2 = ReflectUtil.classForName("android.onyx.optimization.Constant");
            BaseDevice.UPDATE_MODE_DEFAULT = ReflectUtil.getStaticIntFieldSafely(classForName2, "UPDATE_MODE_DEFAULT");
            BaseDevice.UPDATE_MODE_DU = ReflectUtil.getStaticIntFieldSafely(classForName2, "UPDATE_MODE_DU");
            BaseDevice.UPDATE_MODE_A2 = ReflectUtil.getStaticIntFieldSafely(classForName2, "UPDATE_MODE_A2");
            BaseDevice.UPDATE_MODE_REGAL = ReflectUtil.getStaticIntFieldSafely(classForName2, "UPDATE_MODE_REGAL");
            BaseDevice.UPDATE_MODE_X = ReflectUtil.getStaticIntFieldSafely(classForName2, "UPDATE_MODE_X");
            final Class<?> classForName3;
            final Class<?> cls4 = classForName3 = ReflectUtil.classForName("android.onyx.hardware.DeviceController");
            SDMDevice.y1 = ReflectUtil.getMethodSafely(cls4, "openFrontLight", Context.class);
            final Class[] parameterTypes3 = { null };
            final Class<Integer> type2 = Integer.TYPE;
            parameterTypes3[0] = type2;
            SDMDevice.z1 = ReflectUtil.getMethodSafely(cls4, "openFrontLight", (Class<?>[])parameterTypes3);
            SDMDevice.A1 = ReflectUtil.getMethodSafely(cls4, "closeFrontLight", Context.class);
            SDMDevice.B1 = ReflectUtil.getMethodSafely(cls4, "closeFrontLight", type2);
            SDMDevice.C1 = ReflectUtil.getMethodSafely(cls4, "getFrontLightValue", Context.class);
            final Class[] parameterTypes4;
            final Class[] array2 = parameterTypes4 = new Class[2];
            array2[0] = Context.class;
            array2[1] = type2;
            SDMDevice.D1 = ReflectUtil.getMethodSafely(cls4, "setFrontLightValue", (Class<?>[])parameterTypes4);
            SDMDevice.E1 = ReflectUtil.getMethodSafely(cls4, "getFrontLightConfigValue", Context.class);
            final Class[] parameterTypes5;
            final Class[] array3 = parameterTypes5 = new Class[2];
            array3[0] = Context.class;
            array3[1] = type2;
            SDMDevice.F1 = ReflectUtil.getMethodSafely(cls4, "setFrontLightConfigValue", (Class<?>[])parameterTypes5);
            SDMDevice.e2 = ReflectUtil.getMethodSafely(cls4, "hasFLBrightness", Context.class);
            SDMDevice.f2 = ReflectUtil.getMethodSafely(cls4, "hasCTMBrightness", Context.class);
            final Class[] parameterTypes6;
            final Class[] array4 = parameterTypes6 = new Class[2];
            array4[0] = String.class;
            array4[1] = type2;
            SDMDevice.s2 = ReflectUtil.getDeclaredMethodSafely(cls4, "writeValueToFile", (Class<?>[])parameterTypes6);
            SDMDevice.X1 = ReflectUtil.getDeclaredMethodSafely(cls4, "isTouchpadEnable", (Class<?>[])new Class[0]);
            SDMDevice.Y1 = ReflectUtil.getMethodSafely(cls4, "setTouchpadEnable", type);
            SDMDevice.G1 = ReflectUtil.getMethodSafely(cls4, "led", type);
            final Class[] parameterTypes7;
            final Class[] array5 = parameterTypes7 = new Class[2];
            array5[0] = String.class;
            array5[1] = type2;
            SDMDevice.H1 = ReflectUtil.getMethodSafely(cls4, "setLedColor", (Class<?>[])parameterTypes7);
            SDMDevice.q3 = ReflectUtil.getMethodSafely(cls4, "setTextShowPassword", type);
            SDMDevice.r3 = ReflectUtil.getMethodSafely(cls4, "isTextShowPasswordOn", (Class<?>[])new Class[0]);
            SDMDevice.s3 = ReflectUtil.getMethodSafely(cls4, "getMinPasswordLength", (Class<?>[])new Class[0]);
            SDMDevice.t3 = ReflectUtil.getMethodSafely(cls4, "getMaxPasswordLength", (Class<?>[])new Class[0]);
            SDMDevice.u3 = ReflectUtil.getMethodSafely(cls4, "getFsTypeByVolumeId", String.class);
            SDMDevice.v3 = ReflectUtil.getMethodSafely(cls4, "rebootFlashDeviceSn", String.class);
            if ((SDMDevice.Z0 = ReflectUtil.getMethodSafely(cls, "postInvalidate", type2)) == null) {
                SDMDevice.Z0 = ReflectUtil.getMethodSafely(clazz, "postInvalidateWithUpdateMode", type2);
            }
            final Class<View> cls5 = clazz;
            final Class<?> cls6 = classForName;
            final Class<View> clazz4 = clazz;
            SDMDevice.X = ReflectUtil.getMethodSafely(clazz4, "refreshScreen", type2);
            final Class[] parameterTypes8;
            final Class[] array6 = parameterTypes8 = new Class[5];
            array6[0] = type2;
            array6[2] = (array6[1] = type2);
            array6[4] = (array6[3] = type2);
            SDMDevice.Y = ReflectUtil.getMethodSafely(clazz4, "refreshScreen", (Class<?>[])parameterTypes8);
            final Class[] parameterTypes9;
            final Class[] array7 = parameterTypes9 = new Class[2];
            array7[0] = type2;
            array7[1] = String.class;
            SDMDevice.Z = ReflectUtil.getMethodSafely(cls6, "screenshot", (Class<?>[])parameterTypes9);
            SDMDevice.J0 = ReflectUtil.getMethodSafely(cls6, "byPass", type2);
            SDMDevice.d0 = ReflectUtil.getStaticMethodSafely(cls6, "setStrokeColor", type2);
            SDMDevice.e0 = ReflectUtil.getStaticMethodSafely(cls6, "setStrokeStyle", type2);
            final Class[] parameterTypes10 = { null };
            final Class<Float> type3 = Float.TYPE;
            parameterTypes10[0] = type3;
            SDMDevice.f0 = ReflectUtil.getStaticMethodSafely(cls6, "setStrokeWidth", (Class<?>[])parameterTypes10);
            final Class[] parameterTypes11;
            final Class[] array8 = parameterTypes11 = new Class[4];
            array8[0] = type;
            array8[1] = Paint.Style.class;
            array8[2] = Paint.Join.class;
            array8[3] = Paint.Cap.class;
            SDMDevice.g0 = ReflectUtil.getStaticMethodSafely(cls6, "setPainterStyle", (Class<?>[])parameterTypes11);
            SDMDevice.a0 = ReflectUtil.getMethodSafely(cls6, "supportRegal", (Class<?>[])new Class[0]);
            SDMDevice.b0 = ReflectUtil.getMethodSafely(cls6, "enableRegal", type);
            SDMDevice.j0 = ReflectUtil.getMethodSafely(cls6, "penUp", (Class<?>[])new Class[0]);
            final Class[] parameterTypes12;
            final Class[] array9 = parameterTypes12 = new Class[4];
            array9[0] = View.class;
            array9[1] = type3;
            array9[3] = (array9[2] = type3);
            SDMDevice.c0 = ReflectUtil.getMethodSafely(cls6, "moveTo", (Class<?>[])parameterTypes12);
            final Class[] parameterTypes13;
            final Class[] array10 = parameterTypes13 = new Class[4];
            array10[0] = View.class;
            array10[2] = (array10[1] = type3);
            array10[3] = type2;
            SDMDevice.h0 = ReflectUtil.getMethodSafely(cls6, "lineTo", (Class<?>[])parameterTypes13);
            final Class[] parameterTypes14;
            final Class[] array11 = parameterTypes14 = new Class[4];
            array11[0] = View.class;
            array11[2] = (array11[1] = type3);
            array11[3] = type2;
            SDMDevice.i0 = ReflectUtil.getMethodSafely(cls6, "quadTo", (Class<?>[])parameterTypes14);
            final Class[] parameterTypes15;
            final Class[] array12 = parameterTypes15 = new Class[5];
            array12[0] = View.class;
            array12[2] = (array12[1] = type3);
            array12[4] = (array12[3] = type3);
            SDMDevice.Z3 = ReflectUtil.getMethodSafely(cls6, "moveTo", (Class<?>[])parameterTypes15);
            final Class[] parameterTypes16;
            final Class[] array13 = parameterTypes16 = new Class[5];
            array13[0] = View.class;
            array13[2] = (array13[1] = type3);
            array13[3] = type2;
            array13[4] = type3;
            SDMDevice.a4 = ReflectUtil.getMethodSafely(cls6, "lineTo", (Class<?>[])parameterTypes16);
            final Class[] parameterTypes17;
            final Class[] array14 = parameterTypes17 = new Class[5];
            array14[0] = View.class;
            array14[2] = (array14[1] = type3);
            array14[3] = type2;
            array14[4] = type3;
            SDMDevice.b4 = ReflectUtil.getMethodSafely(cls6, "quadTo", (Class<?>[])parameterTypes17);
            SDMDevice.k0 = ReflectUtil.getMethodSafely(cls6, "getTouchWidth", (Class<?>[])new Class[0]);
            SDMDevice.l0 = ReflectUtil.getMethodSafely(cls6, "getTouchHeight", (Class<?>[])new Class[0]);
            SDMDevice.m0 = ReflectUtil.getMethodSafely(cls6, "getMaxTouchPressure", (Class<?>[])new Class[0]);
            SDMDevice.n0 = ReflectUtil.getMethodSafely(cls6, "getEpdWidth", (Class<?>[])new Class[0]);
            SDMDevice.o0 = ReflectUtil.getMethodSafely(cls6, "getEpdHeight", (Class<?>[])new Class[0]);
            final Class[] parameterTypes18;
            final Class[] array15 = parameterTypes18 = new Class[3];
            array15[0] = View.class;
            array15[2] = (array15[1] = float[].class);
            SDMDevice.p0 = ReflectUtil.getMethodSafely(cls6, "mapToView", (Class<?>[])parameterTypes18);
            final Class[] parameterTypes19;
            final Class[] array16 = parameterTypes19 = new Class[3];
            array16[0] = View.class;
            array16[2] = (array16[1] = float[].class);
            SDMDevice.q0 = ReflectUtil.getMethodSafely(cls6, "mapToEpd", (Class<?>[])parameterTypes19);
            final Class[] parameterTypes20;
            final Class[] array17 = parameterTypes20 = new Class[3];
            array17[0] = View.class;
            array17[2] = (array17[1] = float[].class);
            SDMDevice.r0 = ReflectUtil.getMethodSafely(cls6, "mapFromRawTouchPoint", (Class<?>[])parameterTypes20);
            final Class[] parameterTypes21;
            final Class[] array18 = parameterTypes21 = new Class[3];
            array18[0] = View.class;
            array18[2] = (array18[1] = float[].class);
            SDMDevice.s0 = ReflectUtil.getMethodSafely(cls6, "mapToRawTouchPoint", (Class<?>[])parameterTypes21);
            SDMDevice.t0 = ReflectUtil.getMethodSafely(cls6, "enablePost", type2);
            SDMDevice.u0 = ReflectUtil.getMethodSafely(cls6, "resetEpdPost", (Class<?>[])new Class[0]);
            SDMDevice.v0 = ReflectUtil.getMethodSafely(cls6, "isValidPenState", (Class<?>[])new Class[0]);
            SDMDevice.w0 = ReflectUtil.getMethodSafely(cls6, "getPenState", (Class<?>[])new Class[0]);
            SDMDevice.x0 = ReflectUtil.getMethodSafely(cls6, "setScreenHandWritingPenState", type2);
            final Class[] parameterTypes22;
            final Class[] array19 = parameterTypes22 = new Class[2];
            array19[0] = View.class;
            array19[1] = int[].class;
            SDMDevice.z0 = ReflectUtil.getMethodSafely(cls6, "setScreenHandWritingRegionLimit", (Class<?>[])parameterTypes22);
            SDMDevice.y0 = ReflectUtil.getMethodSafely(cls6, "setScreenHandWritingRegionMode", type2);
            final Class[] parameterTypes23;
            final Class[] array20 = parameterTypes23 = new Class[2];
            array20[0] = View.class;
            array20[1] = int[].class;
            SDMDevice.A0 = ReflectUtil.getMethodSafely(cls6, "setScreenHandWritingRegionExclude", (Class<?>[])parameterTypes23);
            final Class[] parameterTypes24;
            final Class[] array21 = parameterTypes24 = new Class[2];
            array21[0] = type;
            array21[1] = type2;
            SDMDevice.B0 = ReflectUtil.getMethodSafely(cls6, "applyGammaCorrection", (Class<?>[])parameterTypes24);
            SDMDevice.C0 = ReflectUtil.getMethodSafely(cls6, "applyMonoLevel", type2);
            SDMDevice.D0 = ReflectUtil.getMethodSafely(cls6, "applyColorFilter", type2);
            SDMDevice.K0 = ReflectUtil.getMethodSafely(cls6, "getColorType", (Class<?>[])new Class[0]);
            SDMDevice.d4 = ReflectUtil.getMethodSafely(cls6, "enableColorAdjust", type);
            SDMDevice.e4 = ReflectUtil.getMethodSafely(cls6, "applySaturationFactor", type3);
            SDMDevice.f4 = ReflectUtil.getMethodSafely(cls6, "applyNoiseStrength", type3);
            SDMDevice.g4 = ReflectUtil.getMethodSafely(cls6, "applyDitherFilterTolerance", type3);
            final Class[] parameterTypes25;
            final Class[] array22 = parameterTypes25 = new Class[6];
            array22[1] = (array22[0] = type3);
            array22[3] = (array22[2] = type3);
            array22[5] = (array22[4] = type3);
            SDMDevice.E0 = ReflectUtil.getStaticMethodSafely(cls6, "startStroke", (Class<?>[])parameterTypes25);
            final Class[] parameterTypes26;
            final Class[] array23 = parameterTypes26 = new Class[6];
            array23[1] = (array23[0] = type3);
            array23[3] = (array23[2] = type3);
            array23[5] = (array23[4] = type3);
            SDMDevice.F0 = ReflectUtil.getStaticMethodSafely(cls6, "addStrokePoint", (Class<?>[])parameterTypes26);
            final Class[] parameterTypes27;
            final Class[] array24 = parameterTypes27 = new Class[6];
            array24[1] = (array24[0] = type3);
            array24[3] = (array24[2] = type3);
            array24[5] = (array24[4] = type3);
            SDMDevice.G0 = ReflectUtil.getStaticMethodSafely(cls6, "finishStroke", (Class<?>[])parameterTypes27);
            if ((SDMDevice.a1 = ReflectUtil.getMethodSafely(cls5, "invalidate", type2)) == null) {
                SDMDevice.a1 = ReflectUtil.getMethodSafely(clazz, "invalidateWithUpdateMode", type2);
            }
            final Class<View> cls7 = clazz;
            final Class[] parameterTypes28;
            final Class[] array25 = parameterTypes28 = new Class[5];
            array25[0] = type2;
            array25[2] = (array25[1] = type2);
            array25[4] = (array25[3] = type2);
            if ((SDMDevice.b1 = ReflectUtil.getMethodSafely(cls7, "invalidate", (Class<?>[])parameterTypes28)) == null) {
                final Class<View> cls8 = clazz;
                final Class[] parameterTypes29;
                final Class[] array26 = parameterTypes29 = new Class[5];
                array26[0] = type2;
                array26[2] = (array26[1] = type2);
                array26[4] = (array26[3] = type2);
                SDMDevice.b1 = ReflectUtil.getMethodSafely(cls8, "invalidateWithUpdateMode", (Class<?>[])parameterTypes29);
            }
            final Class<?> cls9 = classForName;
            final Class<?> clazz5 = classForName3;
            final Class<?> clazz6 = classForName;
            final Class<View> clazz7 = clazz;
            final Class<?> clazz8 = classForName3;
            final Class<?> cls10 = classForName;
            final Class<View> cls11 = clazz;
            SDMDevice.e1 = ReflectUtil.getMethodSafely(cls11, "setDefaultUpdateMode", type2);
            SDMDevice.c1 = ReflectUtil.getMethodSafely(cls11, "getDefaultUpdateMode", (Class<?>[])new Class[0]);
            SDMDevice.d1 = ReflectUtil.getMethodSafely(cls11, "resetUpdateMode", (Class<?>[])new Class[0]);
            final Class[] parameterTypes30;
            final Class[] array27 = parameterTypes30 = new Class[3];
            array27[0] = type2;
            array27[2] = (array27[1] = type2);
            SDMDevice.f1 = ReflectUtil.getMethodSafely(cls10, "applySysScopeUpdate", (Class<?>[])parameterTypes30);
            SDMDevice.j1 = ReflectUtil.getMethodSafely(cls10, "clearSysScopeUpdate", (Class<?>[])new Class[0]);
            final Class[] parameterTypes31;
            final Class[] array28 = parameterTypes31 = new Class[3];
            array28[0] = String.class;
            array28[2] = (array28[1] = type);
            SDMDevice.g1 = ReflectUtil.getMethodSafely(cls10, "applyAppScopeUpdate", (Class<?>[])parameterTypes31);
            final Class[] parameterTypes32;
            final Class[] array29 = parameterTypes32 = new Class[5];
            array29[0] = String.class;
            array29[2] = (array29[1] = type);
            array29[4] = (array29[3] = type2);
            SDMDevice.h1 = ReflectUtil.getMethodSafely(cls10, "applyAppScopeUpdate", (Class<?>[])parameterTypes32);
            SDMDevice.i1 = ReflectUtil.getMethodSafely(cls10, "clearAppScopeUpdate", type);
            SDMDevice.n1 = ReflectUtil.getMethodSafely(cls10, "applyTransientUpdate", type2);
            SDMDevice.o1 = ReflectUtil.getMethodSafely(cls10, "clearTransientUpdate", type);
            SDMDevice.k1 = ReflectUtil.getMethodSafely(cls10, "enableScreenUpdate", type);
            SDMDevice.l1 = ReflectUtil.getMethodSafely(cls10, "setDisplayScheme", type2);
            SDMDevice.m1 = ReflectUtil.getMethodSafely(cls10, "waitForUpdateFinished", (Class<?>[])new Class[0]);
            SDMDevice.H0 = ReflectUtil.getMethodSafely(cls10, "setUpdListSize", type2);
            SDMDevice.I0 = ReflectUtil.getMethodSafely(cls10, "inSystemFastMode", (Class<?>[])new Class[0]);
            SDMDevice.p1 = ReflectUtil.getMethodSafely(cls10, "repaintEverything", type2);
            final Class[] parameterTypes33;
            final Class[] array30 = parameterTypes33 = new Class[2];
            array30[0] = type;
            array30[1] = type2;
            SDMDevice.u1 = ReflectUtil.getMethodSafely(cls10, "fillWhiteOnWakeup", (Class<?>[])parameterTypes33);
            SDMDevice.v1 = ReflectUtil.getMethodSafely(cls10, "useGCForNewSurface", type);
            SDMDevice.w1 = ReflectUtil.getMethodSafely(cls10, "setEpdTurbo", type2);
            SDMDevice.x1 = ReflectUtil.getMethodSafely(cls10, "setAutoSyncBufEnable", type);
            final Class[] parameterTypes34;
            final Class[] array31 = parameterTypes34 = new Class[5];
            array31[0] = View.class;
            array31[2] = (array31[1] = type2);
            array31[4] = (array31[3] = type2);
            SDMDevice.r1 = ReflectUtil.getMethodSafely(cls10, "handwritingRepaint", (Class<?>[])parameterTypes34);
            SDMDevice.s1 = ReflectUtil.getMethodSafely(cls10, "switchToA2Mode", (Class<?>[])new Class[0]);
            SDMDevice.t1 = ReflectUtil.getMethodSafely(cls10, "applySFDebug", type);
            SDMDevice.P0 = ReflectUtil.getMethodSafely(cls10, "isInSystemFastMode", (Class<?>[])new Class[0]);
            SDMDevice.Q0 = ReflectUtil.getMethodSafely(cls10, "isInAppFastMode", (Class<?>[])new Class[0]);
            SDMDevice.R0 = ReflectUtil.getMethodSafely(cls10, "isInFastMode", (Class<?>[])new Class[0]);
            final Class[] parameterTypes35;
            final Class[] array32 = parameterTypes35 = new Class[3];
            array32[0] = Context.class;
            array32[1] = type2;
            array32[2] = String.class;
            SDMDevice.I1 = ReflectUtil.getMethodSafely(clazz8, "setVCom", (Class<?>[])parameterTypes35);
            SDMDevice.J1 = ReflectUtil.getMethodSafely(clazz8, "getVCom", String.class);
            final Class[] parameterTypes36;
            final Class[] array33 = parameterTypes36 = new Class[2];
            array33[1] = (array33[0] = String.class);
            SDMDevice.K1 = ReflectUtil.getMethodSafely(clazz8, "updateWaveform", (Class<?>[])parameterTypes36);
            SDMDevice.L1 = ReflectUtil.getMethodSafely(clazz8, "readSystemConfig", String.class);
            final Class[] parameterTypes37;
            final Class[] array34 = parameterTypes37 = new Class[2];
            array34[1] = (array34[0] = String.class);
            SDMDevice.M1 = ReflectUtil.getMethodSafely(clazz8, "saveSystemConfig", (Class<?>[])parameterTypes37);
            SDMDevice.N1 = ReflectUtil.getMethodSafely(clazz8, "deleteSystemConfig", String.class);
            final Class[] parameterTypes38;
            final Class[] array35 = parameterTypes38 = new Class[2];
            array35[1] = (array35[0] = String.class);
            SDMDevice.O1 = ReflectUtil.getMethodSafely(clazz8, "updateMetadataDB", (Class<?>[])parameterTypes38);
            SDMDevice.P1 = ReflectUtil.getMethodSafely(clazz8, "powerCTP", type);
            SDMDevice.Q1 = ReflectUtil.getMethodSafely(clazz8, "powerEMTP", type);
            SDMDevice.R1 = ReflectUtil.getMethodSafely(clazz8, "isCTPPowerOn", (Class<?>[])new Class[0]);
            SDMDevice.S1 = ReflectUtil.getMethodSafely(clazz8, "isEMTPPowerOn", (Class<?>[])new Class[0]);
            SDMDevice.e3 = ReflectUtil.getMethodSafely(clazz8, "getSystemProperties", String.class);
            final Class[] parameterTypes39;
            final Class[] array36 = parameterTypes39 = new Class[2];
            array36[1] = (array36[0] = String.class);
            SDMDevice.d3 = ReflectUtil.getMethodSafely(clazz8, "setSystemProperties", (Class<?>[])parameterTypes39);
            SDMDevice.f3 = ReflectUtil.getMethodSafely(clazz8, "getStandbyTimeout", (Class<?>[])new Class[0]);
            SDMDevice.g3 = ReflectUtil.getMethodSafely(clazz8, "setStandbyTimeout", type2);
            SDMDevice.h3 = ReflectUtil.getMethodSafely(clazz8, "getPowerOffTimeout", (Class<?>[])new Class[0]);
            SDMDevice.i3 = ReflectUtil.getMethodSafely(clazz8, "setPowerOffTimeout", type2);
            SDMDevice.C2 = ReflectUtil.getMethodSafely(clazz8, "checkCTM", (Class<?>[])new Class[0]);
            SDMDevice.D2 = ReflectUtil.getMethodSafely(clazz8, "getLightValue", type2);
            SDMDevice.G2 = ReflectUtil.getMethodSafely(clazz8, "getMaxLightValue", type2);
            final Class[] parameterTypes40;
            final Class[] array37 = parameterTypes40 = new Class[3];
            array37[0] = type2;
            array37[2] = (array37[1] = type2);
            SDMDevice.E2 = ReflectUtil.getMethodSafely(clazz8, "setLightValue", (Class<?>[])parameterTypes40);
            final Class[] parameterTypes41;
            final Class[] array38 = parameterTypes41 = new Class[2];
            array38[1] = (array38[0] = type2);
            SDMDevice.F2 = ReflectUtil.getMethodSafely(clazz8, "setLightValue", (Class<?>[])parameterTypes41);
            SDMDevice.H2 = ReflectUtil.getMethodSafely(clazz8, "getCtmBrightnessDefaultValue", (Class<?>[])new Class[0]);
            SDMDevice.I2 = ReflectUtil.getMethodSafely(clazz8, "getCtmTemperatureDefaultValue", (Class<?>[])new Class[0]);
            SDMDevice.J2 = ReflectUtil.getMethodSafely(clazz8, "getCTMBrightnessValues", Context.class);
            SDMDevice.K2 = ReflectUtil.getMethodSafely(clazz8, "getFLBrightnessValues", Context.class);
            final Class[] parameterTypes42;
            final Class[] array39 = parameterTypes42 = new Class[2];
            array39[0] = Context.class;
            array39[1] = type2;
            SDMDevice.A2 = ReflectUtil.getMethodSafely(clazz8, "setWarmLightDeviceValue", (Class<?>[])parameterTypes42);
            final Class[] parameterTypes43;
            final Class[] array40 = parameterTypes43 = new Class[2];
            array40[0] = Context.class;
            array40[1] = type2;
            SDMDevice.z2 = ReflectUtil.getMethodSafely(clazz8, "setColdLightDeviceValue", (Class<?>[])parameterTypes43);
            final Class[] parameterTypes44;
            final Class[] array41 = parameterTypes44 = new Class[2];
            array41[0] = Context.class;
            array41[1] = type2;
            SDMDevice.B2 = ReflectUtil.getMethodSafely(clazz8, "getBrightnessConfig", (Class<?>[])parameterTypes44);
            SDMDevice.L2 = ReflectUtil.getMethodSafely(clazz8, "isLightOn", type2);
            SDMDevice.M2 = ReflectUtil.getMethodSafely(clazz8, "getSystemConfigPrefix", Context.class);
            final Class[] parameterTypes45;
            final Class[] array42 = parameterTypes45 = new Class[3];
            array42[0] = String.class;
            array42[2] = (array42[1] = type);
            SDMDevice.z3 = ReflectUtil.getMethodSafely(clazz8, "setSystemConfigFilePermission", (Class<?>[])parameterTypes45);
            final Class[] parameterTypes46;
            final Class[] array43 = parameterTypes46 = new Class[2];
            array43[1] = (array43[0] = int[].class);
            SDMDevice.T1 = ReflectUtil.getMethodSafely(InputManager.class, "setAppCTPDisableRegion", (Class<?>[])parameterTypes46);
            SDMDevice.V1 = ReflectUtil.getMethodSafely(InputManager.class, "isCTPDisableRegion", (Class<?>[])new Class[0]);
            SDMDevice.U1 = ReflectUtil.getMethodSafely(InputManager.class, "appResetCTPDisableRegion", (Class<?>[])new Class[0]);
            SDMDevice.v2 = ReflectUtil.getMethodSafely(InputManager.class, "isEMTPDisabled", (Class<?>[])new Class[0]);
            SDMDevice.w2 = ReflectUtil.getMethodSafely(InputManager.class, "isKeyboardDisabled", (Class<?>[])new Class[0]);
            SDMDevice.x2 = ReflectUtil.getMethodSafely(InputManager.class, "setEMTPDisabled", type);
            SDMDevice.y2 = ReflectUtil.getMethodSafely(InputManager.class, "setKeyboardDisabled", type);
            SDMDevice.W1 = ReflectUtil.getMethodSafely(InputManager.class, "dumpInfo", (Class<?>[])new Class[0]);
            SDMDevice.S0 = ReflectUtil.getMethodSafely(clazz7, "enableA2", (Class<?>[])new Class[0]);
            SDMDevice.T0 = ReflectUtil.getMethodSafely(clazz7, "disableA2", (Class<?>[])new Class[0]);
            SDMDevice.c2 = ReflectUtil.getMethodSafely(clazz6, "switchToA2Mode", (Class<?>[])new Class[0]);
            SDMDevice.b2 = ReflectUtil.getMethodSafely(clazz6, "toggleA2Mode", (Class<?>[])new Class[0]);
            SDMDevice.U0 = ReflectUtil.getMethodSafely(Environment.class, "getStorageRootDirectory", (Class<?>[])new Class[0]);
            SDMDevice.V0 = ReflectUtil.getMethodSafely(Environment.class, "getRemovableSDCardDirectory", (Class<?>[])new Class[0]);
            SDMDevice.W0 = ReflectUtil.getMethodSafely(Environment.class, "getRemovableSDCardDirs", (Class<?>[])new Class[0]);
            SDMDevice.Z1 = ReflectUtil.getMethodSafely(WebView.class, "setCssInjectEnabled", type);
            SDMDevice.a2 = ReflectUtil.getMethodSafely(clazz6, "applyGCOnce", (Class<?>[])new Class[0]);
            SDMDevice.g2 = ReflectUtil.getMethodSafely(clazz6, "setTrigger", type2);
            final Class<?> classForName4;
            final Class<?> cls12 = classForName4 = ReflectUtil.classForName("android.onyx.optimization.EInkHelper");
            SDMDevice.V2 = ReflectUtil.getMethodSafely(cls12, "getGlobalContrast", (Class<?>[])new Class[0]);
            SDMDevice.W2 = ReflectUtil.getMethodSafely(cls12, "setGlobalContrast", type2);
            SDMDevice.X2 = ReflectUtil.getMethodSafely(cls12, "getDitherThreshold", (Class<?>[])new Class[0]);
            SDMDevice.Y2 = ReflectUtil.getMethodSafely(cls12, "setDitherThreshold", type2);
            SDMDevice.Z2 = ReflectUtil.getMethodSafely(cls12, "getAppScopeRefreshMode", (Class<?>[])new Class[0]);
            SDMDevice.a3 = ReflectUtil.getMethodSafely(cls12, "setAppScopeRefreshMode", type2);
            SDMDevice.b3 = ReflectUtil.getMethodSafely(cls12, "setScrollingRefreshMode", type2);
            SDMDevice.c3 = ReflectUtil.getMethodSafely(cls12, "isSystemCTPEnable", (Class<?>[])new Class[0]);
            final Class[] parameterTypes47;
            final Class[] array44 = parameterTypes47 = new Class[2];
            array44[0] = Context.class;
            array44[1] = String.class;
            SDMDevice.y3 = ReflectUtil.getMethodSafely(cls12, "allowUseRegalMode", (Class<?>[])parameterTypes47);
            final Class<?> classForName5 = ReflectUtil.classForName("android.onyx.NotificationHelp");
            final Class[] parameterTypes48;
            final Class[] array45 = parameterTypes48 = new Class[3];
            array45[0] = String.class;
            array45[1] = type2;
            array45[2] = type;
            SDMDevice.j3 = ReflectUtil.getMethodSafely(classForName5, "notificationEnabled", (Class<?>[])parameterTypes48);
            SDMDevice.d2 = ReflectUtil.getMethodSafely(ActivityManager.class, "forceStopPackageWithoutPermissionCheck", String.class);
            final Class<?> classForName6 = ReflectUtil.classForName("android.onyx.AndroidSettingsHelper");
            SDMDevice.h2 = ReflectUtil.getMethodSafely(classForName6, "getDefaultIpAddresses", Context.class);
            SDMDevice.i2 = ReflectUtil.getMethodSafely(classForName6, "isPowerSavedMode", Context.class);
            final Class[] parameterTypes49;
            final Class[] array46 = parameterTypes49 = new Class[2];
            array46[0] = Context.class;
            array46[1] = type;
            SDMDevice.j2 = ReflectUtil.getMethodSafely(classForName6, "enablePowerSavedMode", (Class<?>[])parameterTypes49);
            SDMDevice.t2 = ReflectUtil.getMethodSafely(classForName6, "loadCACertificate", (Class<?>[])new Class[0]);
            SDMDevice.u2 = ReflectUtil.getMethodSafely(classForName6, "loadUserCertificate", (Class<?>[])new Class[0]);
            SDMDevice.k2 = ReflectUtil.getMethodSafely(clazz5, "isHallControlEnable", (Class<?>[])new Class[0]);
            SDMDevice.l2 = ReflectUtil.getMethodSafely(clazz5, "enableHallControl", type);
            final Class<?> classForName7 = ReflectUtil.classForName("android.onyx.utils.ApplicationFreezeHelper");
            final Class[] parameterTypes50;
            final Class[] array47 = parameterTypes50 = new Class[3];
            array47[0] = Context.class;
            array47[1] = String.class;
            array47[2] = type2;
            SDMDevice.m2 = ReflectUtil.getMethodSafely(classForName7, "disableAppByPkgNameAsUser", (Class<?>[])parameterTypes50);
            final Class[] parameterTypes51;
            final Class[] array48 = parameterTypes51 = new Class[3];
            array48[0] = Context.class;
            array48[1] = String.class;
            array48[2] = type2;
            SDMDevice.n2 = ReflectUtil.getMethodSafely(classForName7, "enableAppByPkgNameAsUser", (Class<?>[])parameterTypes51);
            SDMDevice.o2 = ReflectUtil.getMethodSafely(classForName7, "disableGooglePlay", Context.class);
            SDMDevice.p2 = ReflectUtil.getMethodSafely(classForName7, "enableGooglePlay", Context.class);
            SDMDevice.q2 = ReflectUtil.getMethodSafely(classForName7, "isGoogleAppsEnabled", Context.class);
            SDMDevice.r2 = ReflectUtil.getMethodSafely(classForName7, "isGoogleAppsExists", Context.class);
            SDMDevice.N2 = ReflectUtil.getMethodSafely(ReflectUtil.classForName("android.onyx.utils.ActivityManagerHelper"), "getCurrentTopComponent", Context.class);
            SDMDevice.O2 = ReflectUtil.getMethodSafely(ActivityManager.class, "getRunningTasksWithoutPermissionCheck", type2);
            SDMDevice.P2 = ReflectUtil.getMethodSafely(ActivityManager.class, "getTaskBounds", type2);
            SDMDevice.Q2 = ReflectUtil.getMethodSafely(ActivityManager.class, "getTaskWindowingMode", type2);
            SDMDevice.R2 = ReflectUtil.getMethodSafely(ActivityManager.class, "isSystemInMultiWindowMode", (Class<?>[])new Class[0]);
            SDMDevice.S2 = ReflectUtil.getMethodSafely(ActivityManager.class, "getCurrentMultiScreenMode", (Class<?>[])new Class[0]);
            SDMDevice.T2 = ReflectUtil.getMethodSafely(ActivityManager.class, "getCurrentFocusTaskBound", (Class<?>[])new Class[0]);
            final Class<?> classForName8 = ReflectUtil.classForName("com.android.internal.view.RotationPolicy");
            final Class[] parameterTypes52;
            final Class[] array49 = parameterTypes52 = new Class[3];
            array49[0] = Context.class;
            array49[1] = type;
            array49[2] = type2;
            SDMDevice.U2 = ReflectUtil.getMethodSafely(classForName8, "setRotationLockAtAngle", (Class<?>[])parameterTypes52);
            Debug.d(SDMDevice.class, "init device finished.", new Object[0]);
            SDMDevice.k3 = ReflectUtil.getStaticMethodSafely(cls9, "setEnablePenSideButton", type);
            SDMDevice.l3 = ReflectUtil.getStaticMethodSafely(cls9, "setBrushRawDrawingEnabled", type);
            final Class[] parameterTypes53;
            final Class[] array50 = parameterTypes53 = new Class[2];
            array50[0] = type;
            array50[1] = type2;
            if ((SDMDevice.m3 = ReflectUtil.getStaticMethodSafely(cls9, "setEraserRawDrawingEnabled", (Class<?>[])parameterTypes53)) == null) {
                SDMDevice.m3 = ReflectUtil.getStaticMethodSafely(classForName, "setEraserRawDrawingEnabled", type);
            }
            final Class<?> cls13 = classForName;
            SDMDevice.n3 = ReflectUtil.getStaticMethodSafely(cls13, "getStrokeParameters", type2);
            final Class[] parameterTypes54;
            final Class[] array51 = parameterTypes54 = new Class[2];
            array51[0] = type2;
            array51[1] = float[].class;
            SDMDevice.o3 = ReflectUtil.getStaticMethodSafely(cls13, "setStrokeParameters", (Class<?>[])parameterTypes54);
            SDMDevice.p3 = ReflectUtil.getMethodSafely(cls13, "getEpdToViewMatrix", (Class<?>[])new Class[0]);
            SDMDevice.X0 = ReflectUtil.getMethodSafely(ReflectUtil.classForName("android.onyx.utils.RotationUtil"), "convertRotation", type2);
            SDMDevice.Y0 = ReflectUtil.getMethodSafely(ReflectUtil.classForName("android.onyx.utils.FontsUtils"), "getFontPathMap", (Class<?>[])new Class[0]);
            if (CompatibilityUtil.isApiLevelSatisfied(29)) {
                SDMDevice.B3 = ReflectUtil.getMethodSafely(ActivityManager.class, "setDeviceLocales", LocaleList.class);
            }
            final Class<?> cls14 = classForName3;
            final Class<View> cls15 = clazz;
            final Class<?> clazz9 = classForName4;
            SDMDevice.w3 = ReflectUtil.getMethodSafely(AudioManager.class, "setRingerModeInternal", type2);
            SDMDevice.x3 = ReflectUtil.getMethodSafely(ReflectUtil.classForName("android.net.NetworkUtils"), "isWeaklyValidatedHostname", String.class);
            SDMDevice.C3 = ReflectUtil.getMethodSafely(clazz9, "getSystemRefreshMode", (Class<?>[])new Class[0]);
            SDMDevice.D3 = ReflectUtil.getMethodSafely(clazz9, "setSystemRefreshMode", type2);
            final Class[] parameterTypes55;
            final Class[] array52 = parameterTypes55 = new Class[3];
            array52[0] = String.class;
            array52[2] = (array52[1] = type);
            SDMDevice.E3 = ReflectUtil.getMethodSafely(cls15, "applyApplicationFastMode", (Class<?>[])parameterTypes55);
            final Class[] parameterTypes56;
            final Class[] array53 = parameterTypes56 = new Class[5];
            array53[0] = String.class;
            array53[2] = (array53[1] = type);
            array53[4] = (array53[3] = type2);
            SDMDevice.F3 = ReflectUtil.getMethodSafely(cls15, "applyApplicationFastMode", (Class<?>[])parameterTypes56);
            SDMDevice.G3 = ReflectUtil.getMethodSafely(cls15, "clearApplicationFastMode", type);
            SDMDevice.H3 = ReflectUtil.getMethodSafely(ReflectUtil.classForName("android.net.wifi.WifiManager"), "getFactoryMacAddresses", (Class<?>[])new Class[0]);
            SDMDevice.I3 = ReflectUtil.getMethodSafely(cls14, "supportActivePen", (Class<?>[])new Class[0]);
            SDMDevice.J3 = ReflectUtil.getMethodSafely(cls14, "getActivePenBatteryLevel", (Class<?>[])new Class[0]);
            SDMDevice.K3 = ReflectUtil.getMethodSafely(cls14, "getActivePenHapticType", (Class<?>[])new Class[0]);
            SDMDevice.L3 = ReflectUtil.getMethodSafely(cls14, "getActivePenHapticStrength", (Class<?>[])new Class[0]);
            SDMDevice.M3 = ReflectUtil.getMethodSafely(cls14, "setActivePenHapticType", type2);
            SDMDevice.N3 = ReflectUtil.getMethodSafely(cls14, "setActivePenHapticStrength", type2);
            SDMDevice.O3 = ReflectUtil.getMethodSafely(cls14, "getActivePenEnable", (Class<?>[])new Class[0]);
            SDMDevice.P3 = ReflectUtil.getMethodSafely(cls14, "setActivePenEnable", type);
            SDMDevice.Q3 = ReflectUtil.getMethodSafely(cls14, "getActivePenMacAddress", (Class<?>[])new Class[0]);
            SDMDevice.W3 = ReflectUtil.getMethodSafely(cls14, "isPenUIVisibilityEnable", (Class<?>[])new Class[0]);
            SDMDevice.R3 = ReflectUtil.getMethodSafely(cls14, "getWirelessChargingBatteryLevel", (Class<?>[])new Class[0]);
            SDMDevice.S3 = ReflectUtil.getMethodSafely(cls14, "getWirelessChargingState", (Class<?>[])new Class[0]);
            SDMDevice.T3 = ReflectUtil.getMethodSafely(cls14, "getWirelessChargingChipId", (Class<?>[])new Class[0]);
            SDMDevice.U3 = ReflectUtil.getMethodSafely(cls14, "getWirelessChargingChipVersion", (Class<?>[])new Class[0]);
            SDMDevice.V3 = ReflectUtil.getMethodSafely(cls14, "supportWirelessCharging", (Class<?>[])new Class[0]);
            SDMDevice.X3 = ReflectUtil.getMethodSafely(cls14, "isPenHapticEnabled", (Class<?>[])new Class[0]);
            SDMDevice.Y3 = ReflectUtil.getMethodSafely(cls14, "penHapticEnabled", type);
            SDMDevice.c4 = ReflectUtil.getMethodSafely(ReflectUtil.classForName("android.telephony.SubscriptionManager"), "setDefaultDataSubId", type2);
            final Class<?> classForName9 = ReflectUtil.classForName("android.os.PowerManager");
            final Class[] parameterTypes57;
            final Class[] array54 = parameterTypes57 = new Class[3];
            array54[0] = type;
            array54[1] = String.class;
            array54[2] = type;
            SDMDevice.h4 = ReflectUtil.getMethodSafely(classForName9, "shutdown", (Class<?>[])parameterTypes57);
            return SDMDevice.q;
        }
        return q;
    }
    
    private Object a(final Context context, final Method method, final Object... args) {
        if (method == null) {
            return null;
        }
        return ReflectUtil.invokeMethodSafely(method, null, args);
    }
    
    private int a(final UpdateScheme scheme) {
        int n = 1;
        final int n2;
        if ((n2 = SDMDevice$a.c[scheme.ordinal()]) != 1) {
            if (n2 != 2) {
                if (n2 != 3) {
                    if (!SDMDevice.i4) {
                        throw new AssertionError();
                    }
                }
                else {
                    n = 2;
                }
            }
            else {
                n = 1;
            }
        }
        else {
            n = 0;
        }
        return n;
    }
    
    private UpdateMode b(final int value) {
        if (value == SDMDevice.t) {
            return UpdateMode.DU;
        }
        if (value == SDMDevice.J) {
            return UpdateMode.DU4;
        }
        if (value == SDMDevice.v) {
            return UpdateMode.GU;
        }
        if (value == SDMDevice.w) {
            return UpdateMode.GC;
        }
        if (value == SDMDevice.x) {
            return UpdateMode.GCC;
        }
        if (value == SDMDevice.y) {
            return UpdateMode.DEEP_GC;
        }
        return UpdateMode.GC;
    }
    
    private static int a(final UpdatePolicy policy) {
        int v = SDMDevice.v;
        final int n;
        if ((n = SDMDevice$a.d[policy.ordinal()]) != 1) {
            if (n != 2) {
                if (!SDMDevice.i4) {
                    throw new AssertionError();
                }
            }
            else {
                v |= SDMDevice.s;
            }
        }
        else {
            v |= SDMDevice.r;
        }
        return v;
    }
    
    @TargetApi(21)
    private Object b(final Context context) {
        return context.getSystemService("input");
    }
    
    private Object a(final Context context) {
        return context.getSystemService("activity");
    }
    
    static {
        i4 = (SDMDevice.class.desiredAssertionStatus() ^ true);
        p = SDMDevice.class.getSimpleName();
        SDMDevice.q = null;
        SDMDevice.r = 0;
        SDMDevice.s = 0;
        SDMDevice.t = 0;
        SDMDevice.u = 0;
        SDMDevice.v = 0;
        SDMDevice.w = 0;
        SDMDevice.x = 0;
        SDMDevice.y = 0;
        SDMDevice.z = 0;
        SDMDevice.A = 0;
        SDMDevice.B = 0;
        SDMDevice.C = 0;
        SDMDevice.D = 0;
        SDMDevice.E = 0;
        SDMDevice.F = 0;
        SDMDevice.G = 0;
        SDMDevice.H = 0;
        SDMDevice.I = 0;
        SDMDevice.J = 0;
        SDMDevice.X = null;
        SDMDevice.Y = null;
        SDMDevice.Z = null;
        SDMDevice.a0 = null;
        SDMDevice.b0 = null;
        SDMDevice.c0 = null;
        SDMDevice.d0 = null;
        SDMDevice.e0 = null;
        SDMDevice.f0 = null;
        SDMDevice.g0 = null;
        SDMDevice.h0 = null;
        SDMDevice.i0 = null;
        SDMDevice.j0 = null;
        SDMDevice.k0 = null;
        SDMDevice.l0 = null;
        SDMDevice.m0 = null;
        SDMDevice.n0 = null;
        SDMDevice.o0 = null;
        SDMDevice.p0 = null;
        SDMDevice.q0 = null;
        SDMDevice.r0 = null;
        SDMDevice.s0 = null;
        SDMDevice.t0 = null;
        SDMDevice.u0 = null;
        SDMDevice.v0 = null;
        SDMDevice.w0 = null;
        SDMDevice.x0 = null;
        SDMDevice.y0 = null;
        SDMDevice.z0 = null;
        SDMDevice.A0 = null;
        SDMDevice.B0 = null;
        SDMDevice.C0 = null;
        SDMDevice.D0 = null;
        SDMDevice.E0 = null;
        SDMDevice.F0 = null;
        SDMDevice.G0 = null;
        SDMDevice.H0 = null;
        SDMDevice.I0 = null;
        SDMDevice.J0 = null;
        SDMDevice.K0 = null;
        SDMDevice.L0 = null;
        SDMDevice.M0 = null;
        SDMDevice.N0 = null;
        SDMDevice.O0 = null;
        SDMDevice.P0 = null;
        SDMDevice.Q0 = null;
        SDMDevice.R0 = null;
        SDMDevice.Z0 = null;
        SDMDevice.a1 = null;
        SDMDevice.b1 = null;
        SDMDevice.c1 = null;
        SDMDevice.d1 = null;
        SDMDevice.e1 = null;
        SDMDevice.f1 = null;
        SDMDevice.g1 = null;
        SDMDevice.h1 = null;
        SDMDevice.i1 = null;
        SDMDevice.j1 = null;
        SDMDevice.k1 = null;
        SDMDevice.l1 = null;
        SDMDevice.m1 = null;
        SDMDevice.n1 = null;
        SDMDevice.o1 = null;
        SDMDevice.p1 = null;
        SDMDevice.q1 = null;
        SDMDevice.r1 = null;
        SDMDevice.s1 = null;
        SDMDevice.t1 = null;
        SDMDevice.u1 = null;
        SDMDevice.v1 = null;
        SDMDevice.w1 = null;
        SDMDevice.x1 = null;
        SDMDevice.E3 = null;
        SDMDevice.F3 = null;
        SDMDevice.G3 = null;
        SDMDevice.H3 = null;
        SDMDevice.I3 = null;
        SDMDevice.J3 = null;
        SDMDevice.K3 = null;
        SDMDevice.L3 = null;
        SDMDevice.M3 = null;
        SDMDevice.N3 = null;
        SDMDevice.O3 = null;
        SDMDevice.P3 = null;
        SDMDevice.Q3 = null;
        SDMDevice.R3 = null;
        SDMDevice.S3 = null;
        SDMDevice.T3 = null;
        SDMDevice.U3 = null;
        SDMDevice.V3 = null;
        SDMDevice.W3 = null;
        SDMDevice.X3 = null;
        SDMDevice.Y3 = null;
        SDMDevice.Z3 = null;
        SDMDevice.a4 = null;
        SDMDevice.b4 = null;
        SDMDevice.c4 = null;
        SDMDevice.d4 = null;
        SDMDevice.e4 = null;
        SDMDevice.f4 = null;
        SDMDevice.g4 = null;
        SDMDevice.h4 = null;
    }
    
    @Override
    public File getStorageRootDirectory() {
        return (File)ReflectUtil.invokeMethodSafely(SDMDevice.U0, null, new Object[0]);
    }
    
    @Override
    public File getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory();
    }
    
    @Nullable
    @Override
    public File getRemovableSDCardDirectory() {
        return (File)ReflectUtil.invokeMethodSafely(SDMDevice.V0, null, new Object[0]);
    }
    
    @Override
    public List<File> getRemovableSDCardDirs() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.W0, null, new Object[0])) instanceof List) {
            return (List<File>)invokeMethodSafely;
        }
        return Collections.emptyList();
    }
    
    @Override
    public boolean isFileOnRemovableSDCard(final File file) {
        final Iterator<File> iterator = this.getRemovableSDCardDirs().iterator();
        while (iterator.hasNext()) {
            if (file.getAbsolutePath().startsWith(iterator.next().getAbsolutePath())) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public EPDMode getEpdMode() {
        return EPDMode.AUTO;
    }
    
    @Override
    public boolean setEpdMode(final Context context, final EPDMode mode) {
        this.applySysScopeUpdate(this.a(mode), UpdateScheme.QUEUE_AND_MERGE, Integer.MAX_VALUE);
        return false;
    }
    
    @Override
    public void invalidate(final View view, final UpdateMode mode) {
        if (!SDMDevice.i4 && SDMDevice.a1 == null) {
            throw new AssertionError();
        }
        ReflectUtil.invokeMethodSafely(SDMDevice.a1, view, this.a(mode));
    }
    
    @Override
    public void invalidate(final View view, final int left, final int top, final int right, final int bottom, final UpdateMode mode) {
        if (!SDMDevice.i4 && SDMDevice.b1 == null) {
            throw new AssertionError();
        }
        final Method b1 = SDMDevice.b1;
        final Object[] array = new Object[5];
        final Object[] array4;
        final Object[] array3;
        final Object[] array2;
        final Object[] args = array2 = (array3 = (array4 = array));
        args[0] = left;
        array2[1] = top;
        array3[2] = right;
        array4[3] = bottom;
        array[4] = this.a(mode);
        ReflectUtil.invokeMethodSafely(b1, view, args);
    }
    
    @Override
    public void postInvalidate(final View view, final UpdateMode mode) {
        if (!SDMDevice.i4 && SDMDevice.Z0 == null) {
            throw new AssertionError();
        }
        final int a = this.a(mode);
        Log.d(SDMDevice.p, "dst mode: " + a);
        ReflectUtil.invokeMethodSafely(SDMDevice.Z0, view, a);
    }
    
    @Override
    public void refreshScreen(final View view, final UpdateMode mode) {
        if (!SDMDevice.i4 && SDMDevice.X == null) {
            throw new AssertionError();
        }
        ReflectUtil.invokeMethodSafely(SDMDevice.X, view, this.a(mode));
    }
    
    @Override
    public void byPass(final int count) {
        try {
            final Method j0 = SDMDevice.J0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(j0, receiver, count);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void refreshScreenRegion(final View view, final int left, final int top, final int width, final int height, final UpdateMode mode) {
        try {
            if (!SDMDevice.i4 && SDMDevice.Y == null) {
                throw new AssertionError();
            }
            final Method y = SDMDevice.Y;
            final Object[] args = new Object[5];
            final Object[] array3;
            final Object[] array2;
            final Object[] array;
            (array = (array2 = (array3 = args)))[0] = left;
            array[1] = top;
            array2[2] = width;
            array3[3] = height;
            args[4] = this.a(mode);
            ReflectUtil.invokeMethodSafely(y, view, args);
        }
        catch (final Exception ex) {}
    }
    
    @Override
    public void screenshot(final View view, final int rotation, final String path) {
        try {
            ReflectUtil.invokeMethodSafely(SDMDevice.Z, view, rotation, path);
        }
        catch (final Exception ex) {}
    }
    
    @Override
    public void setStrokeColor(final int color) {
        try {
            final Method d0 = SDMDevice.d0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(d0, receiver, color);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void setStrokeStyle(final int style) {
        try {
            final Method e0 = SDMDevice.e0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(e0, receiver, style);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void setPainterStyle(final boolean antiAlias, final Paint.Style strokeStyle, final Paint.Join joinStyle, final Paint.Cap capStyle) {
        try {
            final Method g0 = SDMDevice.g0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(g0, receiver, antiAlias, strokeStyle, joinStyle, capStyle);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void setStrokeWidth(final float width) {
        try {
            final Method f0 = SDMDevice.f0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(f0, receiver, width);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void moveTo(final float x, final float y, final float width) {
        this.moveTo(null, x, y, width);
    }
    
    @Override
    public void moveTo(final View view, final float x, final float y, final float width) {
        try {
            final Method c0 = SDMDevice.c0;
            final Object receiver = null;
            try {
                final Object[] args = new Object[4];
                final Object[] array2;
                final Object[] array;
                (array = (array2 = args))[0] = view;
                array[1] = x;
                array2[2] = y;
                args[3] = width;
                ReflectUtil.invokeMethodSafely(c0, receiver, args);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void moveTo(final View view, final float x, final float y, final float width, final float pressure) {
        try {
            final Method z3 = SDMDevice.Z3;
            final Object receiver = null;
            try {
                final Object[] args = new Object[5];
                final Object[] array3;
                final Object[] array2;
                final Object[] array;
                (array = (array2 = (array3 = args)))[0] = view;
                array[1] = x;
                array2[2] = y;
                array3[3] = width;
                args[4] = pressure;
                ReflectUtil.invokeMethodSafely(z3, receiver, args);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public boolean supportDFB() {
        return SDMDevice.h0 != null;
    }
    
    @Override
    public boolean supportRegal() {
        final Method a0;
        final Boolean b;
        return (a0 = SDMDevice.a0) != null && (b = (Boolean)ReflectUtil.invokeMethodSafely(a0, null, new Object[0])) != null && b;
    }
    
    @Override
    public void enableRegal(final boolean enable) {
        ReflectUtil.invokeMethodSafely(SDMDevice.b0, null, enable);
    }
    
    @Override
    public void lineTo(final float x, final float y, final UpdateMode mode) {
        this.lineTo(null, x, y, mode);
    }
    
    @Override
    public void lineTo(final View view, final float x, final float y, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            final Method h0 = SDMDevice.h0;
            final Object receiver = null;
            try {
                final Object[] array;
                final Object[] args = array = new Object[4];
                final int i = a;
                final Object[] array2 = array;
                final Object[] array3 = array;
                array[0] = view;
                array3[1] = x;
                array2[2] = y;
                args[3] = i;
                ReflectUtil.invokeMethodSafely(h0, receiver, args);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void lineTo(final View view, final float x, final float y, final UpdateMode mode, final float pressure) {
        final int a = this.a(mode);
        try {
            final Method a2 = SDMDevice.a4;
            final Object receiver = null;
            try {
                final Object[] args = new Object[5];
                final Object[] array2;
                final Object[] array = array2 = args;
                final int i = a;
                final Object[] array3 = array;
                final Object[] array4 = array;
                array[0] = view;
                array4[1] = x;
                array3[2] = y;
                array2[3] = i;
                args[4] = pressure;
                ReflectUtil.invokeMethodSafely(a2, receiver, args);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void quadTo(final float x, final float y, final UpdateMode mode) {
        this.quadTo(null, x, y, mode);
    }
    
    @Override
    public void quadTo(final View view, final float x, final float y, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            final Method i0 = SDMDevice.i0;
            final Object receiver = null;
            try {
                final Object[] array;
                final Object[] args = array = new Object[4];
                final int j = a;
                final Object[] array2 = array;
                final Object[] array3 = array;
                array[0] = view;
                array3[1] = x;
                array2[2] = y;
                args[3] = j;
                ReflectUtil.invokeMethodSafely(i0, receiver, args);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void quadTo(final View view, final float x, final float y, final UpdateMode mode, final float pressure) {
        final int a = this.a(mode);
        try {
            final Method b4 = SDMDevice.b4;
            final Object receiver = null;
            try {
                final Object[] args = new Object[5];
                final Object[] array2;
                final Object[] array = array2 = args;
                final int i = a;
                final Object[] array3 = array;
                final Object[] array4 = array;
                array[0] = view;
                array4[1] = x;
                array3[2] = y;
                array2[3] = i;
                args[4] = pressure;
                ReflectUtil.invokeMethodSafely(b4, receiver, args);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void penUp() {
        try {
            ReflectUtil.invokeMethodSafely(SDMDevice.j0, null, new Object[0]);
        }
        catch (final Exception exception) {
            exception.printStackTrace();
        }
    }
    
    @Override
    public float getTouchWidth() {
        try {
            final Method k0 = SDMDevice.k0;
            final Object receiver = null;
            try {
                return (float)ReflectUtil.invokeMethodSafely(k0, receiver, new Object[0]);
            }
            catch (final Exception ex) {
                return 0.0f;
            }
        }
        catch (final Exception ex2) {}
        return 0.0f;
    }
    
    @Override
    public float getEpdHeight() {
        try {
            final Method o0 = SDMDevice.o0;
            final Object receiver = null;
            try {
                return (float)ReflectUtil.invokeMethodSafely(o0, receiver, new Object[0]);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
                return 0.0f;
            }
        }
        catch (final Exception ex2) {}
        return 0.0f;
    }
    
    @Override
    public float getEpdWidth() {
        try {
            final Method n0 = SDMDevice.n0;
            final Object receiver = null;
            try {
                return (float)ReflectUtil.invokeMethodSafely(n0, receiver, new Object[0]);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
                return 0.0f;
            }
        }
        catch (final Exception ex2) {}
        return 0.0f;
    }
    
    @Override
    public void mapToEpd(final View view, final float[] src, final float[] dst) {
        try {
            final Method q0 = SDMDevice.q0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(q0, receiver, view, src, dst);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void mapFromRawTouchPoint(final View view, final float[] src, final float[] dst) {
        try {
            final Method r0 = SDMDevice.r0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(r0, receiver, view, src, dst);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void mapToRawTouchPoint(final View view, final float[] src, final float[] dst) {
        try {
            final Method s0 = SDMDevice.s0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(s0, receiver, view, src, dst);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void mapToView(final View view, final float[] src, final float[] dst) {
        try {
            final Method p0 = SDMDevice.p0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(p0, receiver, view, src, dst);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public float getTouchHeight() {
        try {
            final Method l0 = SDMDevice.l0;
            final Object receiver = null;
            try {
                return (float)ReflectUtil.invokeMethodSafely(l0, receiver, new Object[0]);
            }
            catch (final Exception ex) {
                return 0.0f;
            }
        }
        catch (final Exception ex2) {}
        return 0.0f;
    }
    
    @Override
    public float getMaxTouchPressure() {
        try {
            final Method m0 = SDMDevice.m0;
            final Object receiver = null;
            try {
                return (float)ReflectUtil.invokeMethodSafely(m0, receiver, new Object[0]);
            }
            catch (final Exception ex) {
                return 2048.0f;
            }
        }
        catch (final Exception ex2) {}
        return 2048.0f;
    }
    
    @Override
    public float startStroke(final float baseWidth, final float x, final float y, final float pressure, final float size, final float time) {
        try {
            final Method e0 = SDMDevice.E0;
            final Object receiver = null;
            try {
                final Object[] args = new Object[6];
                final Object[] array4;
                final Object[] array3;
                final Object[] array2;
                final Object[] array;
                (array = (array2 = (array3 = (array4 = args))))[0] = baseWidth;
                array[1] = x;
                array2[2] = y;
                array3[3] = pressure;
                array4[4] = size;
                args[5] = time;
                return (float)ReflectUtil.invokeMethodSafely(e0, receiver, args);
            }
            catch (final Exception ex) {
                return baseWidth;
            }
        }
        catch (final Exception ex2) {}
        return baseWidth;
    }
    
    @Override
    public float addStrokePoint(final float baseWidth, final float x, final float y, final float pressure, final float size, final float time) {
        try {
            final Method f0 = SDMDevice.F0;
            final Object receiver = null;
            try {
                final Object[] args = new Object[6];
                final Object[] array4;
                final Object[] array3;
                final Object[] array2;
                final Object[] array;
                (array = (array2 = (array3 = (array4 = args))))[0] = baseWidth;
                array[1] = x;
                array2[2] = y;
                array3[3] = pressure;
                array4[4] = size;
                args[5] = time;
                return (float)ReflectUtil.invokeMethodSafely(f0, receiver, args);
            }
            catch (final Exception ex) {
                return baseWidth;
            }
        }
        catch (final Exception ex2) {}
        return baseWidth;
    }
    
    @Override
    public float finishStroke(final float baseWidth, final float x, final float y, final float pressure, final float size, final float time) {
        try {
            final Method g0 = SDMDevice.G0;
            final Object receiver = null;
            try {
                final Object[] args = new Object[6];
                final Object[] array4;
                final Object[] array3;
                final Object[] array2;
                final Object[] array;
                (array = (array2 = (array3 = (array4 = args))))[0] = baseWidth;
                array[1] = x;
                array2[2] = y;
                array3[3] = pressure;
                array4[4] = size;
                args[5] = time;
                return (float)ReflectUtil.invokeMethodSafely(g0, receiver, args);
            }
            catch (final Exception ex) {
                return baseWidth;
            }
        }
        catch (final Exception ex2) {}
        return baseWidth;
    }
    
    @Override
    boolean hasStrokeStyleConfigurationCapability() {
        return ReflectUtil.isStaticMethodAvailable(SDMDevice.d0)
                && ReflectUtil.isStaticMethodAvailable(SDMDevice.e0)
                && ReflectUtil.isStaticMethodAvailable(SDMDevice.f0);
    }

    @Override
    boolean hasStrokeDataTransportCapability() {
        return ReflectUtil.isStaticMethodAvailable(SDMDevice.E0)
                && ReflectUtil.isStaticMethodAvailable(SDMDevice.F0)
                && ReflectUtil.isStaticMethodAvailable(SDMDevice.G0);
    }

    @Override
    boolean hasAdvancedStrokeConfigurationCapability() {
        return ReflectUtil.isStaticMethodAvailable(SDMDevice.k3)
                && ReflectUtil.isStaticMethodAvailable(SDMDevice.l3)
                && ReflectUtil.isStaticMethodAvailable(SDMDevice.m3)
                && ReflectUtil.isStaticMethodAvailable(SDMDevice.n3)
                && ReflectUtil.isStaticMethodAvailable(SDMDevice.o3);
    }

    @Override
    public void enterScribbleMode(final View view) {
        this.enablePost(view, 0);
    }
    
    @Override
    public void leaveScribbleMode(final View view) {
        this.enablePost(view, 1);
    }
    
    @Override
    public void enablePost(final View view, final int enable) {
        try {
            ReflectUtil.invokeMethodSafely(SDMDevice.t0, view, enable);
        }
        catch (final Exception ex) {}
    }
    
    @Override
    public void enablePost(final int enable) {
        try {
            final Method t0 = SDMDevice.t0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(t0, receiver, enable);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void enterScribbleMode() {
        this.enablePost(0);
    }
    
    @Override
    public void leaveScribbleMode() {
        this.enablePost(1);
    }
    
    @Override
    public void resetEpdPost() {
        try {
            ReflectUtil.invokeMethodSafely(SDMDevice.u0, null, new Object[0]);
        }
        catch (final Exception exception) {
            exception.printStackTrace();
        }
    }
    
    @Override
    public boolean isValidPenState() {
        try {
            final Method v0 = SDMDevice.v0;
            final Object receiver = null;
            try {
                return (boolean)ReflectUtil.invokeMethodSafely(v0, receiver, new Object[0]);
            }
            catch (final Exception ex) {
                return false;
            }
        }
        catch (final Exception ex2) {}
        return false;
    }
    
    @Override
    public int getPenState() {
        try {
            final Method w0 = SDMDevice.w0;
            final Object receiver = null;
            try {
                return (int)ReflectUtil.invokeMethodSafely(w0, receiver, new Object[0]);
            }
            catch (final Exception ex) {
                return 0;
            }
        }
        catch (final Exception ex2) {}
        return 0;
    }
    
    public boolean supportScreenHandWriting() {
        return SDMDevice.x0 != null;
    }
    
    @Override
    public void setScreenHandWritingPenState(final View view, final int penState) {
        try {
            ReflectUtil.invokeMethodSafely(SDMDevice.x0, view, penState);
        }
        catch (final Exception ex) {}
    }
    
    @Override
    public void setScreenHandWritingRegionLimit(final View view) {
        if (view == null) {
            return;
        }
        this.setScreenHandWritingRegionLimit(view, 0, 0, view.getRight(), view.getBottom());
    }
    
    @Override
    public void setScreenHandWritingRegionLimit(final View view, final int left, final int top, final int right, final int bottom) {
        final int[] array2;
        final int[] array = array2 = new int[4];
        array[0] = left;
        array[1] = top;
        array[2] = right;
        array[3] = bottom;
        this.setScreenHandWritingRegionLimit(view, array2);
    }
    
    @Override
    public void setScreenHandWritingRegionLimit(final View view, final int[] array) {
        try {
            ReflectUtil.invokeMethodSafely(SDMDevice.z0, view, view, array);
        }
        catch (final Exception ex) {}
    }
    
    @Override
    public void setScreenHandWritingRegionMode(final View view, final int mode) {
        try {
            ReflectUtil.invokeMethodSafely(SDMDevice.y0, view, mode);
        }
        catch (final Exception ex) {}
    }
    
    @Override
    public void setScreenHandWritingRegionLimit(final View view, final Rect[] regions) {
        final int[] array = new int[regions.length * 4];
        for (int i = 0; i < regions.length; ++i) {
            final int[] array2 = array;
            final int n = i;
            final Rect rect2;
            final Rect rect = rect2 = regions[i];
            final int min = Math.min(rect.left, rect2.right);
            final int min2 = Math.min(rect.top, rect2.bottom);
            final int max = Math.max(rect.left, rect2.right);
            final int max2 = Math.max(rect.top, rect2.bottom);
            final int n3;
            final int n2 = n3 = n * 4;
            final int[] array3 = array;
            final int n4 = n3;
            final int[] array4 = array;
            final int n5 = n3;
            array[n3] = min;
            array4[n5 + 1] = min2;
            array3[n4 + 2] = max;
            array2[n2 + 3] = max2;
        }
        this.setScreenHandWritingRegionLimit(view, array);
    }
    
    @Override
    public void setScreenHandWritingRegionExclude(final View view, final int[] array) {
        try {
            ReflectUtil.invokeMethodSafely(SDMDevice.A0, view, view, array);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void setScreenHandWritingRegionExclude(final View view, final Rect[] regions) {
        final int[] array = new int[regions.length * 4];
        for (int i = 0; i < regions.length; ++i) {
            final int[] array2 = array;
            final int n = i;
            final Rect rect2;
            final Rect rect = rect2 = regions[i];
            final int min = Math.min(rect.left, rect2.right);
            final int min2 = Math.min(rect.top, rect2.bottom);
            final int max = Math.max(rect.left, rect2.right);
            final int max2 = Math.max(rect.top, rect2.bottom);
            final int n3;
            final int n2 = n3 = n * 4;
            final int[] array3 = array;
            final int n4 = n3;
            final int[] array4 = array;
            final int n5 = n3;
            array[n3] = min;
            array4[n5 + 1] = min2;
            array3[n4 + 2] = max;
            array2[n2 + 3] = max2;
        }
        this.setScreenHandWritingRegionExclude(view, array);
    }
    
    @Override
    public boolean enableScreenUpdate(final View view, final boolean enable) {
        ReflectUtil.invokeMethodSafely(SDMDevice.k1, view, enable);
        return true;
    }
    
    @Override
    public boolean setDisplayScheme(final int scheme) {
        ReflectUtil.invokeMethodSafely(SDMDevice.l1, null, scheme);
        return true;
    }
    
    @Override
    public void waitForUpdateFinished() {
        ReflectUtil.invokeMethodSafely(SDMDevice.m1, null, new Object[0]);
    }
    
    @Override
    public UpdateMode getViewDefaultUpdateMode(final View view) {
        final Integer n;
        if ((n = (Integer)ReflectUtil.invokeMethodSafely(SDMDevice.c1, view, new Object[0])) == null) {
            return UpdateMode.GU;
        }
        return this.b(n);
    }
    
    @Override
    public void resetViewUpdateMode(final View view) {
        ReflectUtil.invokeMethodSafely(SDMDevice.d1, view, new Object[0]);
    }
    
    @Override
    public boolean setViewDefaultUpdateMode(final View view, final UpdateMode mode) {
        return ReflectUtil.invokeMethodSafely(SDMDevice.e1, view, this.a(mode)) != null;
    }
    
    @Override
    public boolean applySysScopeUpdate(final UpdateMode mode, final UpdateScheme scheme, final int count) {
        final Method f1 = SDMDevice.f1;
        final Object[] array = new Object[3];
        final Object[] array2;
        final Object[] args = array2 = array;
        args[0] = this.a(mode);
        array2[1] = this.a(scheme);
        array[2] = count;
        return ReflectUtil.invokeMethodSafely(f1, null, args) != null;
    }
    
    @Override
    public boolean clearSysScopeUpdate() {
        return ReflectUtil.invokeMethodSafely(SDMDevice.j1, null, new Object[0]) != null;
    }
    
    @Override
    public boolean applyAppScopeUpdate(final String application, final boolean enable, final boolean clear) {
        Method method;
        if ((method = SDMDevice.g1) == null) {
            method = SDMDevice.E3;
        }
        final Method method2 = method;
        final Object[] array = new Object[3];
        final Object[] array2;
        final Object[] args = array2 = array;
        args[0] = application;
        array2[1] = enable;
        array[2] = clear;
        return ReflectUtil.invokeMethodSafely(method2, null, args) != null;
    }
    
    @Override
    public boolean applyAppScopeUpdate(final String application, final boolean enable, final boolean clear, final UpdateMode repeatMode, final int repeatLimit) {
        Method method;
        if ((method = SDMDevice.h1) == null) {
            method = SDMDevice.F3;
        }
        final Method method2 = method;
        final Object[] array = new Object[5];
        final Object[] array4;
        final Object[] array3;
        final Object[] array2;
        final Object[] args = array2 = (array3 = (array4 = array));
        args[0] = application;
        array2[1] = enable;
        array3[2] = clear;
        array4[3] = this.a(repeatMode);
        array[4] = repeatLimit;
        return ReflectUtil.invokeMethodSafely(method2, null, args) != null;
    }
    
    @Override
    public boolean clearAppScopeUpdate() {
        return this.clearAppScopeUpdate(true);
    }
    
    @Override
    public boolean clearAppScopeUpdate(final boolean clear) {
        Method method;
        if ((method = SDMDevice.i1) == null) {
            method = SDMDevice.G3;
        }
        return ReflectUtil.invokeMethodSafely(method, null, clear) != null;
    }
    
    @Override
    public int getFrontLightBrightnessMinimum(final Context context) {
        return 0;
    }
    
    @Override
    public int getFrontLightBrightnessMaximum(final Context context) {
        return this.getColdLightValues(context).length - 1;
    }
    
    @Override
    public boolean openFrontLight(final Context context) {
        final Boolean b;
        return (b = (Boolean)this.a(context, SDMDevice.y1, context)) != null && b;
    }
    
    @Override
    public boolean closeFrontLight(final Context context) {
        final Boolean b;
        return (b = (Boolean)this.a(context, SDMDevice.A1, context)) != null && b;
    }
    
    @Override
    public boolean openFrontLight(final int type) {
        final Object invokeMethodSafely;
        return (invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.z1, null, type)) instanceof Boolean && (boolean)invokeMethodSafely;
    }
    
    @Override
    public boolean closeFrontLight(final int type) {
        final Object invokeMethodSafely;
        return (invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.B1, null, type)) instanceof Boolean && (boolean)invokeMethodSafely;
    }
    
    @Override
    public int getFrontLightDeviceValue(final Context context) {
        final Integer n;
        if ((n = (Integer)this.a(context, SDMDevice.C1, context)) == null) {
            return 0;
        }
        return n;
    }
    
    @Override
    public boolean setFrontLightDeviceValue(final Context context, final int value) {
        final Method d1 = SDMDevice.D1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = value;
        return this.a(context, d1, args) != null;
    }
    
    @Override
    public int getFrontLightConfigValue(final Context context) {
        return ReflectUtil.getSafely((Integer)this.a(context, SDMDevice.E1, context));
    }
    
    @Override
    public boolean setFrontLightConfigValue(final Context context, final int value) {
        final Method f1 = SDMDevice.F1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = value;
        this.a(context, f1, args);
        return true;
    }
    
    @Override
    public List<Integer> getFrontLightValueList(final Context context) {
        final Integer[] flBrightnessValues;
        return ((flBrightnessValues = this.getFLBrightnessValues(context)) != null) ? Arrays.asList(flBrightnessValues) : Collections.emptyList();
    }
    
    @Override
    public void led(final Context context, final boolean on) {
        this.a(context, SDMDevice.G1, on);
    }
    
    @Override
    public boolean setLedColor(final String color, final int on) {
        final Method h1 = SDMDevice.H1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = color;
        array[1] = on;
        this.a(null, h1, args);
        return true;
    }
    
    @Override
    public void setVCom(final Context context, final int value, final String path) {
        final Method i1 = SDMDevice.I1;
        final Object[] args;
        final Object[] array = args = new Object[3];
        args[0] = context;
        array[1] = value;
        array[2] = path;
        this.a(context, i1, args);
    }
    
    @Override
    public int getVCom(final Context context, final String path) {
        final Integer n;
        if ((n = (Integer)this.a(context, SDMDevice.J1, path)) == null) {
            return 0;
        }
        return n;
    }
    
    @Override
    public void updateWaveform(final Context context, final String path, final String target) {
        final Method k1 = SDMDevice.K1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = path;
        array[1] = target;
        this.a(context, k1, args);
    }
    
    @Override
    public String readSystemConfig(final Context context, final String key) {
        final Object a;
        if ((a = this.a(context, SDMDevice.L1, key)) != null && !a.equals("")) {
            return a.toString();
        }
        return "";
    }
    
    @Override
    public boolean saveSystemConfig(final Context context, final String key, final String value) {
        final Method m1 = SDMDevice.M1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = key;
        array[1] = value;
        return ReflectUtil.getSafely((Boolean)this.a(context, m1, args));
    }
    
    @Override
    public boolean deleteSystemConfig(final Context context, final String key) {
        return ReflectUtil.getSafely((Boolean)this.a(context, SDMDevice.N1, key));
    }
    
    @Override
    public void updateMetadataDB(final Context context, final String path, final String target) {
        final Method o1 = SDMDevice.O1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = path;
        array[1] = target;
        this.a(context, o1, args);
    }
    
    UpdateMode a(final EPDMode mode) {
        switch (SDMDevice$a.a[mode.ordinal()]) {
            default: {
                return UpdateMode.DU;
            }
            case 2:
            case 3:
            case 4: {
                return UpdateMode.GU;
            }
            case 1: {
                return UpdateMode.GC;
            }
        }
    }
    
    int a(final UpdateMode mode) {
        int n = 0;
        switch (SDMDevice$a.b[mode.ordinal()]) {
            default: {
                n = SDMDevice.H;
                break;
            }
            case 17: {
                n = SDMDevice.I;
                break;
            }
            case 16: {
                final int n2;
                if ((n2 = SDMDevice.G) != 0) {
                    return n2;
                }
                n = SDMDevice.v;
                break;
            }
            case 15: {
                final int n2;
                if ((n2 = SDMDevice.F) != 0) {
                    return n2;
                }
                n = SDMDevice.v;
                break;
            }
            case 14: {
                final int n2;
                if ((n2 = SDMDevice.E) != 0) {
                    return n2;
                }
                n = SDMDevice.v;
                break;
            }
            case 13: {
                n = SDMDevice.D;
                break;
            }
            case 12: {
                n = SDMDevice.C;
                break;
            }
            case 11: {
                n = SDMDevice.B;
                break;
            }
            case 10: {
                n = SDMDevice.A;
                break;
            }
            case 9: {
                n = SDMDevice.z;
                break;
            }
            case 8: {
                n = SDMDevice.y;
                break;
            }
            case 7: {
                n = SDMDevice.x;
                break;
            }
            case 6: {
                n = SDMDevice.w;
                break;
            }
            case 5: {
                n = SDMDevice.v;
                break;
            }
            case 4: {
                n = SDMDevice.u;
                break;
            }
            case 3: {
                n = SDMDevice.J;
                break;
            }
            case 1:
            case 2: {
                n = SDMDevice.t;
                break;
            }
        }
        return n;
    }
    
    @Override
    public void disableA2ForSpecificView(final View view) {
        ReflectUtil.invokeMethodSafely(SDMDevice.T0, view, new Object[0]);
    }
    
    @Override
    public void enableA2ForSpecificView(final View view) {
        ReflectUtil.invokeMethodSafely(SDMDevice.S0, view, new Object[0]);
    }
    
    @Override
    public void setWebViewContrastOptimize(final WebView view, final boolean enabled) {
        ReflectUtil.invokeMethodSafely(SDMDevice.Z1, view, enabled);
    }
    
    @Override
    public void setUpdListSize(final int size) {
        ReflectUtil.invokeMethodSafely(SDMDevice.H0, null, size);
    }
    
    @Override
    public boolean inSystemFastMode() {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(SDMDevice.I0, null, new Object[0])) != null && b;
    }
    
    @Override
    public String getUpgradePackageName() {
        return "update.upx";
    }
    
    @Override
    public boolean shouldVerifyUpdateModel() {
        return false;
    }
    
    @Override
    public void powerCTP(final boolean on) {
        this.a(null, SDMDevice.P1, on);
    }
    
    @Override
    public void powerEMTP(final boolean on) {
        this.a(null, SDMDevice.Q1, on);
    }
    
    @Override
    public boolean isCTPPowerOn() {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(SDMDevice.R1, null, new Object[0])) != null && b;
    }
    
    @Override
    public boolean isEMTPPowerOn() {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(SDMDevice.S1, null, new Object[0])) != null && b;
    }
    
    @Override
    public boolean isCTPDisableRegion(final Context context) {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(SDMDevice.V1, this.b(context), new Object[0])) != null && b;
    }
    
    @Override
    public void appResetCTPDisableRegion(final Context context) {
        ReflectUtil.invokeMethodSafely(SDMDevice.U1, this.b(context), new Object[0]);
    }
    
    @Override
    public void setAppCTPDisableRegion(final Context context, final int[] disableRegionArray, @Nullable final int[] excludeRegionArray) {
        ReflectUtil.invokeMethodSafely(SDMDevice.T1, this.b(context), disableRegionArray, excludeRegionArray);
    }
    
    @Override
    public void setAppCTPDisableRegion(final Context context, final Rect[] disableRegions, @Nullable final Rect[] excludeRegions) {
        this.setAppCTPDisableRegion(context, this.convertRectArrayToIntArray(disableRegions), this.convertRectArrayToIntArray(excludeRegions));
    }
    
    @Override
    public void updateMtpDb(final Context context, final String filePath) {
        com.onyx.android.sdk.device.a.a(context, new File(filePath));
    }
    
    @Override
    public void updateMtpDb(final Context context, final File file) {
        com.onyx.android.sdk.device.a.a(context, file);
    }
    
    @Override
    public void switchToA2Mode() {
        ReflectUtil.invokeMethodSafely(SDMDevice.c2, null, new Object[0]);
    }
    
    @Override
    public void toggleA2Mode() {
        ReflectUtil.invokeMethodSafely(SDMDevice.b2, null, new Object[0]);
    }
    
    @Override
    public void applyGammaCorrection(final boolean apply, final int value) {
        final Method b0 = SDMDevice.B0;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = apply;
        array[1] = value;
        ReflectUtil.invokeMethodSafely(b0, null, args);
    }
    
    @Override
    public void applyMonoLevel(final int value) {
        ReflectUtil.invokeMethodSafely(SDMDevice.C0, null, value);
    }
    
    @Override
    public void applyColorFilter(final int value) {
        ReflectUtil.invokeMethodSafely(SDMDevice.D0, null, value);
    }
    
    @Override
    public void applyGCOnce() {
        ReflectUtil.invokeMethodSafely(SDMDevice.a2, null, new Object[0]);
    }
    
    @Override
    public String getCTPInfo() {
        return com.onyx.android.sdk.device.a.a(new File("/sys/onyx_misc/captp_fwver"));
    }
    
    @Override
    public String getTPDetailsInfo() {
        return com.onyx.android.sdk.device.a.a(new File("/sys/onyx_misc/onyx_tp_info"));
    }
    
    @Override
    public boolean upgradeTP(final UpgradeConfig config) {
        return com.onyx.android.sdk.device.a.a(config.toString(), new File("/sys/onyx_misc/onyx_tp_fw_update"));
    }
    
    @Override
    public boolean hasFLBrightness(final Context context) {
        return ReflectUtil.getSafely((Boolean)this.a(context, SDMDevice.e2, context));
    }
    
    @Override
    public boolean hasCTMBrightness(final Context context) {
        return ReflectUtil.getSafely((Boolean)this.a(context, SDMDevice.f2, context));
    }
    
    @Override
    public void setTrigger(final int count) {
        ReflectUtil.invokeMethodSafely(SDMDevice.g2, null, count);
    }
    
    @Override
    public void forceStopApplication(final Context context, final String pkgName) {
        ReflectUtil.invokeMethodSafely(SDMDevice.d2, this.a(context), pkgName);
    }
    
    @Nullable
    @Override
    public String getIPAddress(final Context context) {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.h2, null, context)) instanceof String) {
            return (String)invokeMethodSafely;
        }
        return null;
    }
    
    @Override
    public boolean isPowerSavedMode(final Context context) {
        return ReflectUtil.getSafely((Boolean)ReflectUtil.invokeMethodSafely(SDMDevice.i2, null, context));
    }
    
    @Override
    public void enablePowerSavedMode(final Context context, final boolean enable) {
        final Method j2 = SDMDevice.j2;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = enable;
        ReflectUtil.invokeMethodSafely(j2, null, args);
    }
    
    @Override
    public boolean isHallControlEnable(final Context context) {
        return ReflectUtil.getSafely((Boolean)this.a(context, SDMDevice.k2, new Object[0]));
    }
    
    @Override
    public void enableHallControl(final Context context, final boolean enable) {
        this.a(context, SDMDevice.l2, enable);
    }
    
    @Override
    public boolean isGooglePlayEnabled(final Context context) {
        return ReflectUtil.getSafely((Boolean)ReflectUtil.invokeMethodSafely(SDMDevice.q2, null, context));
    }
    
    @Override
    public boolean isGoogleAppsExists(final Context context) {
        return ReflectUtil.getSafely((Boolean)ReflectUtil.invokeMethodSafely(SDMDevice.r2, null, context));
    }
    
    @Override
    public void freezeApplication(final Context context, final String pkgName, final int userId) {
        final Method m2 = SDMDevice.m2;
        final Object[] array = new Object[3];
        final Object[] array2;
        final Object[] args = array2 = array;
        array2[0] = context;
        array2[1] = pkgName;
        array[2] = userId;
        ReflectUtil.invokeMethodSafely(m2, null, args);
    }
    
    @Override
    public void unfreezeApplication(final Context context, final String pkgName, final int userId) {
        final Method n2 = SDMDevice.n2;
        final Object[] array = new Object[3];
        final Object[] array2;
        final Object[] args = array2 = array;
        array2[0] = context;
        array2[1] = pkgName;
        array[2] = userId;
        ReflectUtil.invokeMethodSafely(n2, null, args);
    }
    
    @Override
    public void freezeGooglePlay(final Context context) {
        ReflectUtil.invokeMethodSafely(SDMDevice.o2, null, context);
    }
    
    @Override
    public void unfreezeGooglePlay(final Context context) {
        ReflectUtil.invokeMethodSafely(SDMDevice.p2, null, context);
    }
    
    @Override
    public void repaintEveryThing() {
        ReflectUtil.invokeMethodSafely(SDMDevice.q1, null, new Object[0]);
    }
    
    @Override
    public void repaintEveryThing(final UpdateMode mode) {
        ReflectUtil.invokeMethodSafely(SDMDevice.p1, null, this.a(mode));
    }
    
    @Override
    public void fillWhiteOnWakeup(final boolean enable, final UpdateMode repaintMode) {
        final Method u1 = SDMDevice.u1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = enable;
        array[1] = this.a(repaintMode);
        ReflectUtil.invokeMethodSafely(u1, null, args);
    }
    
    @Override
    public void useGCForNewSurface(final boolean apply) {
        ReflectUtil.invokeMethodSafely(SDMDevice.v1, null, apply);
    }
    
    @Override
    public void setAutoSyncBufEnable(final boolean enable) {
        ReflectUtil.invokeMethodSafely(SDMDevice.x1, null, enable);
    }
    
    @Override
    public void handwritingRepaint(final View view, final int left, final int top, final int right, final int bottom) {
        final Method r1 = SDMDevice.r1;
        final Object[] array = new Object[5];
        final Object[] array4;
        final Object[] array3;
        final Object[] array2;
        final Object[] args = array2 = (array3 = (array4 = array));
        args[0] = view;
        array2[1] = left;
        array3[2] = top;
        array4[3] = right;
        array[4] = bottom;
        ReflectUtil.invokeMethodSafely(r1, null, args);
    }
    
    @Override
    public void applySystemFastMode(final boolean enable) {
        if (enable) {
            ReflectUtil.invokeMethodSafely(SDMDevice.s1, null, new Object[0]);
        }
        else {
            this.clearSysScopeUpdate();
        }
    }
    
    @Override
    public void setCTMBrightnessValue(final int type, final int value) {
        if (type != 0) {
            if (type == 1) {
                final Method s2 = SDMDevice.s2;
                final Object[] args;
                final Object[] array = args = new Object[2];
                args[0] = "/sys/class/backlight/warm/brightness";
                array[1] = value;
                ReflectUtil.invokeMethodSafely(s2, null, args);
            }
        }
        else {
            final Method s3 = SDMDevice.s2;
            final Object[] args2;
            final Object[] array2 = args2 = new Object[2];
            args2[0] = "/sys/class/backlight/white/brightness";
            array2[1] = value;
            ReflectUtil.invokeMethodSafely(s3, null, args2);
        }
    }
    
    @Override
    public String[] loadCACertificate() {
        return (String[])ReflectUtil.invokeMethodSafely(SDMDevice.t2, null, new Object[0]);
    }
    
    @Override
    public String[] loadUserCertificate() {
        return (String[])ReflectUtil.invokeMethodSafely(SDMDevice.u2, null, new Object[0]);
    }
    
    @Override
    public void applySFDebug(final boolean enableDebug) {
        ReflectUtil.invokeMethodSafely(SDMDevice.t1, null, enableDebug);
    }
    
    @Override
    public boolean isEMTPDisabled(final Context context) {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(SDMDevice.v2, this.b(context), new Object[0])) != null && b;
    }
    
    @Override
    public boolean isKeyboardDisabled(final Context context) {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(SDMDevice.w2, this.b(context), new Object[0])) != null && b;
    }
    
    @Override
    public void setEMTPDisabled(final Context context, final boolean disabled) {
        ReflectUtil.invokeMethodSafely(SDMDevice.x2, this.b(context), disabled);
    }
    
    @Override
    public void setKeyboardDisabled(final Context context, final boolean disabled) {
        ReflectUtil.invokeMethodSafely(SDMDevice.y2, this.b(context), disabled);
    }
    
    @Override
    public Integer[] getWarmLightValues(final Context context) {
        final Integer[][] naturalLightValues;
        if ((naturalLightValues = this.getNaturalLightValues(context)) != null && naturalLightValues.length > 0) {
            return naturalLightValues[0];
        }
        return null;
    }
    
    @Override
    public Integer[] getColdLightValues(final Context context) {
        final Integer[][] naturalLightValues;
        if ((naturalLightValues = this.getNaturalLightValues(context)) != null && naturalLightValues.length > 1) {
            return naturalLightValues[1];
        }
        return null;
    }
    
    @Override
    public boolean checkCTM() {
        final Boolean b;
        return (b = (Boolean)this.a(null, SDMDevice.C2, new Object[0])) != null && b;
    }
    
    @Override
    public Integer getLightValues(final int tpye) {
        final Object a;
        if ((a = this.a(null, SDMDevice.D2, tpye)) != null && a instanceof Integer) {
            return (Integer)a;
        }
        return null;
    }
    
    @Override
    public Integer getMaxLightValues(final int tpye) {
        final Object a;
        if ((a = this.a(null, SDMDevice.G2, tpye)) != null && a instanceof Integer) {
            return (Integer)a;
        }
        return null;
    }
    
    @Override
    public boolean setLightValue(final int type, final int value) {
        Object o = null;
        final Method e2;
        if ((e2 = SDMDevice.E2) != null) {
            final Object[] args;
            final Object[] array = args = new Object[3];
            args[0] = type;
            array[1] = value;
            array[2] = 0;
            o = this.a(null, e2, args);
        }
        else {
            final Method f2;
            if ((f2 = SDMDevice.F2) != null) {
                final Object[] args2;
                final Object[] array2 = args2 = new Object[2];
                args2[0] = type;
                array2[1] = value;
                o = this.a(null, f2, args2);
            }
        }
        return o != null;
    }
    
    @Override
    public Integer getBrDefaultValue() {
        final Object a;
        if ((a = this.a(null, SDMDevice.H2, new Object[0])) != null && a instanceof Integer) {
            return (Integer)a;
        }
        return 0;
    }
    
    @Override
    public Integer getCtDefaultValue() {
        final Object a;
        if ((a = this.a(null, SDMDevice.I2, new Object[0])) != null && a instanceof Integer) {
            return (Integer)a;
        }
        return 0;
    }
    
    @Override
    public Integer[][] getNaturalLightValues(final Context context) {
        final Object a;
        if ((a = this.a(context, SDMDevice.J2, context)) != null && a instanceof Integer[][]) {
            return (Integer[][])a;
        }
        return null;
    }
    
    @Override
    public Integer[] getFLBrightnessValues(final Context context) {
        final Object a;
        if ((a = this.a(context, SDMDevice.K2, context)) != null && a instanceof Integer[]) {
            return (Integer[])a;
        }
        return null;
    }
    
    @Override
    public int getWarmLightConfigValue(final Context context) {
        final Method b2 = SDMDevice.B2;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = context;
        array[1] = 2;
        final Object a;
        if ((a = this.a(context, b2, args)) != null) {
            return (int)a;
        }
        return 0;
    }
    
    @Override
    public int getColdLightConfigValue(final Context context) {
        final Method b2 = SDMDevice.B2;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = context;
        array[1] = 3;
        final Object a;
        if ((a = this.a(context, b2, args)) != null) {
            return (int)a;
        }
        return 0;
    }
    
    @Override
    public boolean setWarmLightDeviceValue(final Context context, final int value) {
        final Method a2 = SDMDevice.A2;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = value;
        return this.a(context, a2, args) != null;
    }
    
    @Override
    public boolean setColdLightDeviceValue(final Context context, final int value) {
        final Method z2 = SDMDevice.z2;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = value;
        return this.a(context, z2, args) != null;
    }
    
    @Override
    public boolean increaseBrightness(final Context context, final int lightType) {
        return LightUtils.adjustBrightness(context, lightType, true);
    }
    
    @Override
    public boolean decreaseBrightness(final Context context, final int lightType) {
        return LightUtils.adjustBrightness(context, lightType, false);
    }
    
    @Nullable
    @Override
    public ComponentName getCurrentTopComponent(final Context context) {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.N2, null, context)) instanceof ComponentName) {
            return (ComponentName)invokeMethodSafely;
        }
        return null;
    }
    
    @Override
    public List<ActivityManager.RunningTaskInfo> getRunningTasksWithoutPermissionCheck(final Context context, final int maxNum) {
        try {
            return (List)ReflectUtil.invokeMethodSafely(SDMDevice.O2, this.a(context), maxNum);
        }
        catch (final Exception throwable) {
            Debug.w(throwable);
            return super.getRunningTasksWithoutPermissionCheck(context, maxNum);
        }
    }
    
    @Override
    public boolean isBrightnessOn(final Context context) {
        return this.isLightOn(context);
    }
    
    @Override
    public boolean isLightOn(final Context context) {
        return this.isLightOn(context, 4);
    }
    
    @Override
    public boolean isLightOn(final Context context, final int type) {
        final Boolean b;
        return (this.hasCTMBrightness(context) || this.hasFLBrightness(context)) && (b = (Boolean)ReflectUtil.invokeMethodSafely(SDMDevice.L2, null, type)) != null && b;
    }
    
    @SuppressLint({ "PrivateApi" })
    @RequiresApi(api = 23)
    @Override
    public void configLightStatusBar(final Window window) {
        this.configStatusBar(window, -1, true);
    }
    
    @SuppressLint({ "PrivateApi" })
    @RequiresApi(api = 23)
    @Override
    public void configStatusBar(final Window window, final int backgroundColor, final boolean useLight) {
        if (window == null) {
            Debug.w(this.getClass(), "window is null, can't config status bar", new Object[0]);
            return;
        }
        int systemUiVisibility;
        if (useLight) {
            systemUiVisibility = 8192;
            if (Build.VERSION.SDK_INT >= 26) {
                window.setNavigationBarColor(-1);
                systemUiVisibility = 8208;
            }
        }
        else {
            systemUiVisibility = 0;
        }
        final String clsName = "com.android.internal.policy.DecorView";
        final View decorView = window.getDecorView();
        String fieldName;
        if (Build.VERSION.SDK_INT >= 29) {
            fieldName = "mSemiTransparentBarColor";
        }
        else {
            fieldName = "mSemiTransparentStatusBarColor";
        }
        ReflectUtil.setDeclareIntFieldSafely(clsName, decorView, fieldName, backgroundColor);
        window.getDecorView().setSystemUiVisibility(systemUiVisibility);
    }
    
    @Override
    public void setRotationLockAtAngle(final Context context, final boolean enabled, final int rotation) {
        final Method u2 = SDMDevice.U2;
        final Object[] array = new Object[3];
        final Object[] array2;
        final Object[] args = array2 = array;
        args[0] = context;
        array2[1] = enabled;
        array[2] = rotation;
        ReflectUtil.invokeMethodSafely(u2, null, args);
    }
    
    @Override
    public Rect getTaskBounds(final Context context, final int taskId) {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.P2, this.a(context), taskId)) instanceof Rect) {
            return (Rect)invokeMethodSafely;
        }
        return new Rect();
    }
    
    @Override
    public boolean isPrimaryTaskInMultiWindowMode(final Context context, final int taskId) {
        return 3 == this.getTaskWindowingMode(context, taskId);
    }
    
    @Override
    public int getTaskWindowingMode(final Context context, final int taskId) {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.Q2, this.a(context), taskId)) instanceof Integer) {
            return (int)invokeMethodSafely;
        }
        return 3;
    }
    
    @Override
    public boolean isSystemInMultiWindowMode(final Context context) {
        return context != null && ReflectUtil.getSafely((Boolean)ReflectUtil.invokeMethodSafely(SDMDevice.R2, this.a(context), new Object[0]));
    }
    
    @Override
    public int getCurrentMultiScreenMode(final Context context) {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.S2, this.a(context), new Object[0])) instanceof Integer) {
            return (int)invokeMethodSafely;
        }
        return 0;
    }
    
    @Override
    public boolean isLimitedMultiScreenMode(final Context context) {
        return this.getCurrentMultiScreenMode(context) == 1;
    }
    
    @Override
    public boolean isFullFunctionMultiScreenMode(final Context context) {
        return this.getCurrentMultiScreenMode(context) == 2;
    }
    
    @Override
    public String getSystemConfigPrefix(final Context context) {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.M2, null, context)) instanceof String) {
            return (String)invokeMethodSafely;
        }
        return null;
    }
    
    @Override
    public boolean isTouchpadEnable() {
        return ReflectUtil.getSafely((Boolean)ReflectUtil.invokeMethodSafely(SDMDevice.X1, null, new Object[0]));
    }
    
    @Override
    public void setTouchpadEnable(final Context context, final boolean enable) {
        this.a(context, SDMDevice.Y1, enable);
    }
    
    @Override
    public int getGlobalContrast() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.V2, null, new Object[0])) instanceof Integer) {
            return (int)invokeMethodSafely;
        }
        return -1;
    }
    
    @Override
    public void setGlobalContrast(final int contrast) {
        ReflectUtil.invokeMethodSafely(SDMDevice.W2, null, contrast);
    }
    
    @Override
    public int getDitherThreshold() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.X2, null, new Object[0])) instanceof Integer) {
            return (int)invokeMethodSafely;
        }
        return -1;
    }
    
    @Override
    public void setDitherThreshold(final int value) {
        ReflectUtil.invokeMethodSafely(SDMDevice.Y2, null, value);
    }
    
    @Override
    public void setSystemProperties(final String key, final String value) {
        final Method d3 = SDMDevice.d3;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = key;
        array[1] = value;
        this.a(null, d3, args);
    }
    
    @Override
    public String getSystemProperties(final String key) {
        final Object a;
        if ((a = this.a(null, SDMDevice.e3, key)) != null && !a.equals("")) {
            return a.toString();
        }
        return "";
    }
    
    @Override
    public UpdateOption getAppScopeRefreshMode() {
        Method method;
        if ((method = SDMDevice.Z2) == null) {
            method = SDMDevice.C3;
        }
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(method, null, new Object[0])) instanceof Integer) {
            return this.a((int)invokeMethodSafely);
        }
        return UpdateOption.NORMAL;
    }
    
    @Override
    public void setAppScopeRefreshMode(final UpdateOption updateOption) {
        Method method;
        if ((method = SDMDevice.a3) == null) {
            method = SDMDevice.D3;
        }
        ReflectUtil.invokeMethodSafely(method, null, this.a(updateOption));
    }
    
    @Override
    public void dumpCTPInfo(final Context context) {
        ReflectUtil.invokeMethodSafely(SDMDevice.W1, this.b(context), new Object[0]);
    }
    
    @Override
    public boolean isInAppScopeRefreshModeDefault() {
        return this.a(this.getAppScopeRefreshMode()) == BaseDevice.UPDATE_MODE_DEFAULT;
    }
    
    @Override
    public boolean isInAppScopeRefreshModeX() {
        return this.a(this.getAppScopeRefreshMode()) == BaseDevice.UPDATE_MODE_X;
    }
    
    @Override
    public int getStandbyTimeout() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.f3, null, new Object[0])) instanceof Integer) {
            return (int)invokeMethodSafely;
        }
        return 0;
    }
    
    @Override
    public void setStandbyTimeout(final int ms) {
        this.a(null, SDMDevice.g3, ms);
    }
    
    @Override
    public int getPowerOffTimeout() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.h3, null, new Object[0])) instanceof Integer) {
            return (int)invokeMethodSafely;
        }
        return 0;
    }
    
    @Override
    public void setPowerOffTimeout(final int ms) {
        this.a(null, SDMDevice.i3, ms);
    }
    
    @Override
    public void setNotificationEnabled(final String pkgName, final int uid, final boolean enable) {
        final Method j3 = SDMDevice.j3;
        final Object[] array = new Object[3];
        final Object[] array2;
        final Object[] args = array2 = array;
        args[0] = pkgName;
        array2[1] = uid;
        array[2] = enable;
        ReflectUtil.invokeMethodSafely(j3, null, args);
    }
    
    @Override
    public int getColorType() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.K0, null, new Object[0])) instanceof Integer) {
            return (int)invokeMethodSafely;
        }
        return 0;
    }
    
    @Override
    public void enableColorCU(final boolean enable) {
        ReflectUtil.invokeMethodSafely(SDMDevice.L0, null, enable);
    }
    
    @Override
    public void enableNightMode(final boolean enable) {
        ReflectUtil.invokeMethodSafely(SDMDevice.M0, null, enable);
    }
    
    @Override
    public void enableNightMode(final boolean enable, final boolean clear) {
        final Method n0;
        if ((n0 = SDMDevice.N0) == null) {
            Debug.d(SDMDevice.p, "enableNightModeWithGC is not supported", new Object[0]);
            this.enableNightMode(enable);
            return;
        }
        final Method method = n0;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = enable;
        array[1] = clear;
        ReflectUtil.invokeMethodSafely(method, null, args);
    }
    
    @Override
    public boolean isSupportNightMode() {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(SDMDevice.O0, null, new Object[0])) != null && b;
    }
    
    @Override
    public void setEpdTurbo(final int value) {
        ReflectUtil.invokeMethodSafely(SDMDevice.w1, null, value);
    }
    
    @Override
    public void setEnablePenSideButton(final boolean enable) {
        ReflectUtil.invokeMethodSafely(SDMDevice.k3, null, enable);
    }
    
    @Override
    public void setBrushRawDrawingEnabled(final boolean enable) {
        ReflectUtil.invokeMethodSafely(SDMDevice.l3, null, enable);
    }
    
    @Override
    public void setEraserRawDrawingEnabled(final boolean enable, final int eraserStyle) {
        final Method m3;
        if ((m3 = SDMDevice.m3) != null && m3.getParameterTypes().length == 2) {
            final Method m4 = SDMDevice.m3;
            final Object[] args;
            final Object[] array = args = new Object[2];
            args[0] = enable;
            array[1] = eraserStyle;
            ReflectUtil.invokeMethodSafely(m4, null, args);
        }
        else {
            ReflectUtil.invokeMethodSafely(SDMDevice.m3, null, enable);
        }
    }
    
    @Override
    public float[] getStrokeParameters(final int strokeStyle) {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.n3, null, strokeStyle)) != null && invokeMethodSafely instanceof float[]) {
            return (float[])invokeMethodSafely;
        }
        return new float[0];
    }
    
    @Override
    public void setStrokeParameters(final int strokeStyle, final float[] params) {
        final Method o3 = SDMDevice.o3;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = strokeStyle;
        array[1] = params;
        ReflectUtil.invokeMethodSafely(o3, null, args);
    }
    
    @Override
    protected float[] getEpdToViewMatrixHelper() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.p3, null, new Object[0])) != null && invokeMethodSafely instanceof float[]) {
            return (float[])invokeMethodSafely;
        }
        return null;
    }
    
    @Override
    public void setLowWorkWifiTimeout(final Context context, final long timeout) {
        DeviceBroadcastHelper.setLowWorkWifiTimeout(context, timeout);
    }
    
    @RequiresApi(api = 23)
    @Override
    public String getFixedWifiMacAddress(final Context context) {
        if (!CompatibilityUtil.isApiLevelAbove(31)) {
            return null;
        }
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.H3, context.getSystemService((Class)WifiManager.class), new Object[0])) instanceof String[]) {
            final String[] array;
            return ((array = (String[])invokeMethodSafely).length > 0) ? array[0] : null;
        }
        return null;
    }
    
    @Override
    public void setLowWorkBluetoothTimeout(final Context context, final long timeout) {
        DeviceBroadcastHelper.setLowWorkBluetoothTimeout(context, timeout);
    }
    
    @Override
    public void setLowWorkAudioTimeout(final Context context, final long timeout) {
        DeviceBroadcastHelper.setLowWorkAudioTimeout(context, timeout);
    }
    
    @Override
    public void enableStandbyByPressPowerButton(final Context context, final boolean enable) {
        DeviceBroadcastHelper.enableStandbyByPressPowerButton(context, enable);
    }
    
    @Override
    public Rect getCurrentFocusTaskBound(final Context context) {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.T2, this.a(context), new Object[0])) instanceof Rect) {
            return (Rect)invokeMethodSafely;
        }
        return new Rect();
    }
    
    @Override
    public void setTextShowPassword(final Context context, final boolean isShow) {
        this.a(context, SDMDevice.q3, isShow);
    }
    
    @Override
    public boolean isTextShowPasswordOn() {
        final Object a;
        return (a = this.a(null, SDMDevice.r3, new Object[0])) != null && (boolean)a;
    }
    
    @Override
    public int getMinPasswordLength(final Context context) {
        final Object a;
        if ((a = this.a(null, SDMDevice.s3, new Object[0])) == null) {
            return super.getMinPasswordLength(context);
        }
        return (int)a;
    }
    
    @Override
    public int getMaxPasswordLength(final Context context) {
        final Object a;
        if ((a = this.a(null, SDMDevice.t3, new Object[0])) == null) {
            return super.getMaxPasswordLength(context);
        }
        return (int)a;
    }
    
    @Override
    public boolean isInFastMode() {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(SDMDevice.R0, null, new Object[0])) != null && b;
    }
    
    @Override
    public boolean isInAppFastMode() {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(SDMDevice.Q0, null, new Object[0])) != null && b;
    }
    
    @Override
    public boolean isInSystemFastMode() {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(SDMDevice.P0, null, new Object[0])) != null && b;
    }
    
    @Override
    public void updateLocale(final Context context, final Locale locale) {
        if (CompatibilityUtil.isApiLevelSatisfied(29)) {
            ReflectUtil.invokeMethodSafely(SDMDevice.B3, this.a(context), new LocaleList(new Locale[] { locale }));
        }
        else {
            super.updateLocale(context, locale);
        }
    }
    
    @Override
    public void setScrollingRefreshMode(final int mode) {
        ReflectUtil.invokeMethodSafely(SDMDevice.b3, null, mode);
    }
    
    @Override
    public int convertRotation(final int rotation) {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.X0, null, rotation)) instanceof Integer) {
            return (int)invokeMethodSafely;
        }
        return super.convertRotation(rotation);
    }
    
    @Override
    public String getCalibrationFilePath() {
        return "/onyxconfig/pointercal";
    }
    
    @Override
    public boolean applyTransientUpdate(final UpdateMode updateMode) {
        return ReflectUtil.invokeMethodSafely(SDMDevice.n1, null, this.a(updateMode)) != null;
    }
    
    @Override
    public boolean clearTransientUpdate(final boolean clear) {
        return ReflectUtil.invokeMethodSafely(SDMDevice.o1, null, clear) != null;
    }
    
    @Override
    public String getFsTypeByVolumeId(final String volId) {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.u3, null, volId)) != null && !invokeMethodSafely.equals("")) {
            return invokeMethodSafely.toString();
        }
        return "";
    }
    
    @Override
    public boolean useCustomRotationChangedBroadcast() {
        return true;
    }
    
    @Override
    public void rebootFlashDeviceSn(final String sn) {
        ReflectUtil.invokeMethodSafely(SDMDevice.v3, null, sn);
    }
    
    @Override
    public boolean isSystemCTPEnable() {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(SDMDevice.c3, null, new Object[0])) != null && b;
    }
    
    @Override
    public Map<String, String> loadSystemFamilyPathMap() {
        final Object invokeMethodSafely;
        if (!((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.Y0, null, new Object[0])) instanceof Map)) {
            return super.loadSystemFamilyPathMap();
        }
        final Map map;
        if (!(map = (Map)invokeMethodSafely).isEmpty()) {
            return map;
        }
        return super.loadSystemFamilyPathMap();
    }
    
    @Override
    public void setRingerModeInternal(final Context context, final int ringerMode) {
        ReflectUtil.invokeMethodSafely(SDMDevice.w3, context.getSystemService("audio"), ringerMode);
    }
    
    @Override
    public boolean isWeaklyValidatedHostname(final String hostname) {
        final Object invokeMethodSafely;
        return !StringUtils.isNullOrEmpty(hostname) && (!((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.x3, null, hostname)) instanceof Boolean) || (boolean)invokeMethodSafely);
    }
    
    @Override
    public void setSystemConfigFilePermission(final Context context, final String key, final boolean externalReadable, final boolean externalWriteable) {
        final Method z3 = SDMDevice.z3;
        final Object[] array = new Object[3];
        final Object[] array2;
        final Object[] args = array2 = array;
        args[0] = key;
        array2[1] = externalReadable;
        array[2] = externalWriteable;
        this.a(context, z3, args);
    }
    
    @Override
    public void gotoSleep(final Context context) {
        if (SDMDevice.A3 == null) {
            SDMDevice.A3 = ReflectUtil.getDeclaredMethodSafely(PowerManager.class, "goToSleep", Long.TYPE);
        }
        final PowerManager receiver;
        if ((receiver = (PowerManager)context.getSystemService("power")) == null) {
            return;
        }
        ReflectUtil.invokeMethodSafely(SDMDevice.A3, receiver, SystemClock.uptimeMillis());
    }
    
    @Override
    public boolean enableChargingControl(final boolean enable) {
        int n;
        if (enable) {
            n = 80;
        }
        else {
            n = 100;
        }
        int i;
        if (enable) {
            i = 79;
        }
        else {
            i = 100;
        }
        final int j = i;
        final int n2 = n;
        final boolean a = com.onyx.android.sdk.device.a.a(String.valueOf(n2), new File("/sys/class/power_supply/battery/charge_control_end_threshold"));
        com.onyx.android.sdk.device.a.a(String.valueOf(n2), new File("/onyxconfig/charge_control_end_threshold"));
        final boolean a2 = com.onyx.android.sdk.device.a.a(String.valueOf(j), new File("/sys/class/power_supply/battery/charge_control_start_threshold"));
        com.onyx.android.sdk.device.a.a(String.valueOf(i), new File("/onyxconfig/charge_control_start_threshold"));
        return a2 && a;
    }
    
    @Override
    public boolean isSupportChargingControl() {
        final File file = new File("/sys/class/power_supply/battery/charge_control_end_threshold");
        final File file2 = new File("/sys/class/power_supply/battery/charge_control_start_threshold");
        return file.exists() && file2.exists();
    }
    
    @Override
    public void setActivePenEnable(final boolean enable) {
        ReflectUtil.invokeMethodSafely(SDMDevice.P3, null, enable);
    }
    
    @Override
    public boolean getActivePenEnable() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.O3, null, new Object[0])) instanceof Boolean) {
            return (boolean)invokeMethodSafely;
        }
        return super.getActivePenEnable();
    }
    
    @Override
    public void setActivePenHapticStrength(final int strength) {
        ReflectUtil.invokeMethodSafely(SDMDevice.N3, null, strength);
    }
    
    @Override
    public int getActivePenHapticStrength() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.L3, null, new Object[0])) instanceof Integer) {
            return (int)invokeMethodSafely;
        }
        return super.getActivePenHapticStrength();
    }
    
    @Override
    public void setActivePenHapticType(final int type) {
        ReflectUtil.invokeMethodSafely(SDMDevice.M3, null, type);
    }
    
    @Override
    public int getActivePenHapticType() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.K3, null, new Object[0])) instanceof Integer) {
            return (int)invokeMethodSafely;
        }
        return super.getActivePenHapticType();
    }
    
    @Override
    public int getActivePenBatteryLevel() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.J3, null, new Object[0])) instanceof Integer) {
            return (int)invokeMethodSafely;
        }
        return super.getActivePenBatteryLevel();
    }
    
    @Override
    public boolean supportActivePen() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.I3, null, new Object[0])) instanceof Boolean) {
            return (boolean)invokeMethodSafely;
        }
        return super.supportActivePen();
    }
    
    @Override
    public boolean supportWirelessCharging() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.V3, null, new Object[0])) instanceof Boolean) {
            return (boolean)invokeMethodSafely;
        }
        return super.supportWirelessCharging();
    }
    
    @Override
    public String getWirelessChargingChipVersion() {
        final Object invokeMethodSafely;
        final String s;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.U3, null, new Object[0])) instanceof String && StringUtils.isNotBlank(s = (String)invokeMethodSafely)) {
            return s;
        }
        return super.getWirelessChargingChipVersion();
    }
    
    @Override
    public String getWirelessChargingChipId() {
        final Object invokeMethodSafely;
        final String s;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.T3, null, new Object[0])) instanceof String && StringUtils.isNotBlank(s = (String)invokeMethodSafely)) {
            return s;
        }
        return super.getWirelessChargingChipId();
    }
    
    @Override
    public int getWirelessChargingState() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.S3, null, new Object[0])) instanceof Integer) {
            return (int)invokeMethodSafely;
        }
        return super.getWirelessChargingState();
    }
    
    @Override
    public int getWirelessChargingBatteryLevel() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.R3, null, new Object[0])) instanceof Integer) {
            return (int)invokeMethodSafely;
        }
        return super.getWirelessChargingBatteryLevel();
    }
    
    @Override
    public String getActivePenMacAddress() {
        final Object invokeMethodSafely;
        final String s;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.Q3, null, new Object[0])) instanceof String && StringUtils.isNotBlank(s = (String)invokeMethodSafely)) {
            return s;
        }
        return super.getActivePenMacAddress();
    }
    
    @Override
    public boolean isPenUIVisibilityEnable() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.W3, null, new Object[0])) instanceof Boolean) {
            return (boolean)invokeMethodSafely;
        }
        return super.isPenUIVisibilityEnable();
    }
    
    @Override
    public void penHapticEnabled(final boolean enable) {
        ReflectUtil.invokeMethodSafely(SDMDevice.Y3, null, enable);
    }
    
    @Override
    public boolean isPenHapticEnabled() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(SDMDevice.X3, null, new Object[0])) instanceof Boolean) {
            return (boolean)invokeMethodSafely;
        }
        return super.isPenHapticEnabled();
    }
    
    @Override
    public void setDefaultDataSubId(final SubscriptionManager subscriptionManager, final int subId) {
        ReflectUtil.invokeMethodSafely(SDMDevice.c4, subscriptionManager, subId);
    }
    
    @Override
    public void shutdown(final PowerManager powerManager) {
        final Object[] args;
        final Object[] array = args = new Object[3];
        final Boolean falseValue = Boolean.FALSE;
        array[0] = falseValue;
        array[1] = "reboot_test";
        array[2] = falseValue;
        ReflectUtil.invokeMethodSafely(SDMDevice.h4, powerManager, args);
    }
    
    @Override
    public String getValidMacAddressRegex() {
        final String boardPlatform;
        final String s = boardPlatform = Device.getBoardPlatform();
        s.hashCode();
        int n = -1;
        switch (s) {
            case "msmnile": {
                n = 2;
                break;
            }
            case "lito": {
                n = 1;
                break;
            }
            case "bengal": {
                n = 0;
                break;
            }
            default:
                break;
        }
        switch (n) {
            default: {
                return "([A-Fa-f0-9]{2}:){5}[A-Fa-f0-9]{2}";
            }
            case 2: {
                return "(00:0[bB]:)([A-Fa-f0-9]{2}:){3}[A-Fa-f0-9]{2}";
            }
            case 1: {
                return "(00:0[cC]:)([A-Fa-f0-9]{2}:){3}[A-Fa-f0-9]{2}";
            }
            case 0: {
                return "(00:0[aA]:)([A-Fa-f0-9]{2}:){3}[A-Fa-f0-9]{2}";
            }
        }
    }
    
    @Override
    public void enableColorAdjust(final boolean enable) {
        ReflectUtil.invokeMethodSafely(SDMDevice.d4, null, enable);
    }
    
    @Override
    public void applySaturationFactor(final float value) {
        ReflectUtil.invokeMethodSafely(SDMDevice.e4, null, value);
    }
    
    @Override
    public void applyNoiseStrength(final float value) {
        ReflectUtil.invokeMethodSafely(SDMDevice.f4, null, value);
    }
    
    @Override
    public void applyDitherFilterTolerance(final float value) {
        ReflectUtil.invokeMethodSafely(SDMDevice.g4, null, value);
    }
}
