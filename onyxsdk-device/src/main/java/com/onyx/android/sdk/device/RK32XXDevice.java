// 
// 

package com.onyx.android.sdk.device;

import java.util.Map;
import com.onyx.android.sdk.api.device.epd.UpdateOption;
import android.content.ComponentName;
import com.onyx.android.sdk.utils.LightUtils;
import androidx.annotation.Nullable;
import java.util.Arrays;
import android.graphics.Rect;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import com.onyx.android.sdk.api.device.epd.EPDMode;
import java.util.Collections;
import java.util.List;
import java.io.File;
import android.annotation.TargetApi;
import com.onyx.android.sdk.api.device.epd.UpdatePolicy;
import com.onyx.android.sdk.api.device.epd.UpdateMode;
import com.onyx.android.sdk.api.device.epd.UpdateScheme;
import com.onyx.android.sdk.utils.Debug;
import android.content.res.Configuration;
import android.app.Activity;
import android.app.ActivityManager;
import android.webkit.WebView;
import android.os.Environment;
import android.hardware.input.InputManager;
import android.os.Build;
import android.graphics.Paint;
import android.content.Context;
import com.onyx.android.sdk.utils.ReflectUtil;
import android.view.View;
import java.lang.reflect.Method;

public class RK32XXDevice extends BaseDevice
{
    private static final String p;
    private static RK32XXDevice q;
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
    private static final int H = 0;
    private static final int I = 1;
    private static final int J = 2;
    private static final String K = "/sys/class/backlight/white/brightness";
    private static final String L = "/sys/class/backlight/warm/brightness";
    private static Method M;
    private static Method N;
    private static Method O;
    private static Method P;
    private static Method Q;
    private static Method R;
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
    private static Class<?> H2;
    private static Method I2;
    private static Method J2;
    private static Method K2;
    private static Method L2;
    private static Method M2;
    private static Method N2;
    private static Method O2;
    private static Method P2;
    private static Method Q2;
    @Deprecated
    private static Method R2;
    @Deprecated
    private static Method S2;
    @Deprecated
    private static Method T2;
    @Deprecated
    private static Method U2;
    @Deprecated
    private static Method V2;
    static final /* synthetic */ boolean W2;
    
    private RK32XXDevice() {
    }
    
    public static RK32XXDevice createDevice() {
        final RK32XXDevice q;
        if ((q = RK32XXDevice.q) == null) {
            RK32XXDevice.q = new RK32XXDevice();
            final Class<View> clazz = View.class;
            final Class<?> classForName;
            final Class<?> cls = classForName = ReflectUtil.classForName("android.onyx.ViewUpdateHelper");
            final int staticIntFieldSafely = ReflectUtil.getStaticIntFieldSafely(cls, "EINK_ONYX_AUTO_MASK");
            final int staticIntFieldSafely2 = ReflectUtil.getStaticIntFieldSafely(cls, "EINK_ONYX_GC_MASK");
            final int staticIntFieldSafely3 = ReflectUtil.getStaticIntFieldSafely(cls, "EINK_AUTO_MODE_REGIONAL");
            final Class<?> cls2 = classForName;
            final int s = staticIntFieldSafely2;
            final int r = staticIntFieldSafely;
            final Class<?> cls3 = classForName;
            final int staticIntFieldSafely4 = ReflectUtil.getStaticIntFieldSafely(cls3, "EINK_WAIT_MODE_NOWAIT");
            final int staticIntFieldSafely5 = ReflectUtil.getStaticIntFieldSafely(cls3, "EINK_WAVEFORM_MODE_ANIM");
            final int staticIntFieldSafely6 = ReflectUtil.getStaticIntFieldSafely(cls3, "EINK_WAVEFORM_MODE_REAGL");
            final int staticIntFieldSafely7 = ReflectUtil.getStaticIntFieldSafely(cls3, "EINK_REAGL_MODE_REAGLD");
            final int staticIntFieldSafely8 = ReflectUtil.getStaticIntFieldSafely(cls3, "EINK_UPDATE_MODE_PARTIAL");
            RK32XXDevice.r = r;
            RK32XXDevice.s = s;
            RK32XXDevice.t = ReflectUtil.getStaticIntFieldSafely(cls2, "UI_DU_MODE");
            RK32XXDevice.u = ReflectUtil.getStaticIntFieldSafely(cls2, "UI_DU_QUALITY_MODE");
            RK32XXDevice.v = ReflectUtil.getStaticIntFieldSafely(cls2, "UI_GU_MODE");
            RK32XXDevice.w = ReflectUtil.getStaticIntFieldSafely(cls2, "UI_GC_MODE");
            RK32XXDevice.x = ReflectUtil.getStaticIntFieldSafely(cls2, "UI_DEEP_GC_MODE");
            final int n = staticIntFieldSafely3 | staticIntFieldSafely4;
            final int n2 = staticIntFieldSafely7;
            final Class<?> cls4 = classForName;
            RK32XXDevice.y = (n | staticIntFieldSafely5 | staticIntFieldSafely8);
            RK32XXDevice.z = ReflectUtil.getStaticIntFieldSafely(cls4, "UI_A2_QUALITY_MODE");
            RK32XXDevice.A = ReflectUtil.getStaticIntFieldSafely(cls4, "UI_MONO_A2_MODE");
            RK32XXDevice.B = ReflectUtil.getStaticIntFieldSafely(cls4, "UI_X_A2_MODE");
            RK32XXDevice.C = ReflectUtil.getStaticIntFieldSafely(cls4, "UI_GC4_MODE");
            RK32XXDevice.D = ReflectUtil.getStaticIntFieldSafely(cls4, "UI_REGAL_MODE");
            RK32XXDevice.E = (n | n2 | staticIntFieldSafely6 | staticIntFieldSafely8);
            RK32XXDevice.F = ReflectUtil.getStaticIntFieldSafely(cls, "UI_DEFAULT_MODE");
            RK32XXDevice.G = ReflectUtil.getStaticIntFieldSafely(cls, "HAND_WRITING_REPAINT_MODE");
            final Class<?> classForName2 = ReflectUtil.classForName("android.onyx.optimization.Constant");
            BaseDevice.UPDATE_MODE_DEFAULT = ReflectUtil.getStaticIntFieldSafely(classForName2, "UPDATE_MODE_DEFAULT");
            BaseDevice.UPDATE_MODE_DU = ReflectUtil.getStaticIntFieldSafely(classForName2, "UPDATE_MODE_DU");
            BaseDevice.UPDATE_MODE_A2 = ReflectUtil.getStaticIntFieldSafely(classForName2, "UPDATE_MODE_A2");
            BaseDevice.UPDATE_MODE_REGAL = ReflectUtil.getStaticIntFieldSafely(classForName2, "UPDATE_MODE_REGAL");
            BaseDevice.UPDATE_MODE_X = ReflectUtil.getStaticIntFieldSafely(classForName2, "UPDATE_MODE_X");
            final Class<?> classForName3;
            final Class<?> clazz2 = classForName3 = ReflectUtil.classForName("android.onyx.hardware.DeviceController");
            final Class<View> clazz3 = clazz;
            final Class<?> cls5 = classForName;
            final Class<View> clazz4 = clazz;
            final Class<?> cls6 = classForName;
            final Class<View> clazz5 = clazz;
            final Class<?> cls7 = classForName3;
            RK32XXDevice.i1 = ReflectUtil.getMethodSafely(cls7, "openFrontLight", Context.class);
            final Class[] parameterTypes = { null };
            final Class<Integer> type = Integer.TYPE;
            parameterTypes[0] = type;
            RK32XXDevice.j1 = ReflectUtil.getMethodSafely(cls7, "openFrontLight", (Class<?>[])parameterTypes);
            RK32XXDevice.k1 = ReflectUtil.getMethodSafely(cls7, "closeFrontLight", Context.class);
            RK32XXDevice.l1 = ReflectUtil.getMethodSafely(cls7, "closeFrontLight", type);
            RK32XXDevice.m1 = ReflectUtil.getMethodSafely(cls7, "getFrontLightValue", Context.class);
            final Class[] parameterTypes2;
            final Class[] array = parameterTypes2 = new Class[2];
            array[0] = Context.class;
            array[1] = type;
            RK32XXDevice.n1 = ReflectUtil.getMethodSafely(cls7, "setFrontLightValue", (Class<?>[])parameterTypes2);
            RK32XXDevice.o1 = ReflectUtil.getMethodSafely(cls7, "getFrontLightConfigValue", Context.class);
            final Class[] parameterTypes3;
            final Class[] array2 = parameterTypes3 = new Class[2];
            array2[0] = Context.class;
            array2[1] = type;
            RK32XXDevice.p1 = ReflectUtil.getMethodSafely(cls7, "setFrontLightConfigValue", (Class<?>[])parameterTypes3);
            RK32XXDevice.L1 = ReflectUtil.getMethodSafely(cls7, "hasFLBrightness", Context.class);
            RK32XXDevice.M1 = ReflectUtil.getMethodSafely(cls7, "hasCTMBrightness", Context.class);
            final Class[] parameterTypes4;
            final Class[] array3 = parameterTypes4 = new Class[2];
            array3[0] = String.class;
            array3[1] = type;
            RK32XXDevice.Z1 = ReflectUtil.getDeclaredMethodSafely(cls7, "writeValueToFile", (Class<?>[])parameterTypes4);
            final Class[] parameterTypes5 = { null };
            final Class<Boolean> type2 = Boolean.TYPE;
            parameterTypes5[0] = type2;
            RK32XXDevice.q1 = ReflectUtil.getMethodSafely(cls7, "led", (Class<?>[])parameterTypes5);
            final Class[] parameterTypes6;
            final Class[] array4 = parameterTypes6 = new Class[2];
            array4[0] = String.class;
            array4[1] = type;
            RK32XXDevice.r1 = ReflectUtil.getMethodSafely(cls7, "setLedColor", (Class<?>[])parameterTypes6);
            RK32XXDevice.H0 = ReflectUtil.getMethodSafely(clazz5, "postInvalidate", type);
            RK32XXDevice.M = ReflectUtil.getMethodSafely(clazz5, "refreshScreen", type);
            final Class[] parameterTypes7;
            final Class[] array5 = parameterTypes7 = new Class[5];
            array5[0] = type;
            array5[2] = (array5[1] = type);
            array5[4] = (array5[3] = type);
            RK32XXDevice.N = ReflectUtil.getMethodSafely(clazz5, "refreshScreen", (Class<?>[])parameterTypes7);
            final Class[] parameterTypes8;
            final Class[] array6 = parameterTypes8 = new Class[2];
            array6[0] = type;
            array6[1] = String.class;
            RK32XXDevice.O = ReflectUtil.getMethodSafely(clazz5, "screenshot", (Class<?>[])parameterTypes8);
            RK32XXDevice.A0 = ReflectUtil.getMethodSafely(clazz5, "byPass", type);
            RK32XXDevice.T = ReflectUtil.getStaticMethodSafely(clazz5, "setStrokeColor", type);
            RK32XXDevice.U = ReflectUtil.getStaticMethodSafely(clazz5, "setStrokeStyle", type);
            final Class[] parameterTypes9 = { null };
            final Class<Float> type3 = Float.TYPE;
            parameterTypes9[0] = type3;
            RK32XXDevice.V = ReflectUtil.getStaticMethodSafely(clazz5, "setLineWidth", (Class<?>[])parameterTypes9);
            final Class[] parameterTypes10;
            final Class[] array7 = parameterTypes10 = new Class[4];
            array7[0] = type2;
            array7[1] = Paint.Style.class;
            array7[2] = Paint.Join.class;
            array7[3] = Paint.Cap.class;
            RK32XXDevice.W = ReflectUtil.getStaticMethodSafely(clazz5, "setPainterStyle", (Class<?>[])parameterTypes10);
            RK32XXDevice.P = ReflectUtil.getMethodSafely(clazz5, "supportRegal", (Class<?>[])new Class[0]);
            RK32XXDevice.Q = ReflectUtil.getMethodSafely(clazz5, "enableRegal", type2);
            final Class[] parameterTypes11;
            final Class[] array8 = parameterTypes11 = new Class[3];
            array8[0] = type3;
            array8[2] = (array8[1] = type3);
            RK32XXDevice.R = ReflectUtil.getMethodSafely(clazz5, "moveTo", (Class<?>[])parameterTypes11);
            final Class[] parameterTypes12;
            final Class[] array9 = parameterTypes12 = new Class[3];
            array9[1] = (array9[0] = type3);
            array9[2] = type;
            RK32XXDevice.X = ReflectUtil.getMethodSafely(clazz5, "lineTo", (Class<?>[])parameterTypes12);
            final Class[] parameterTypes13;
            final Class[] array10 = parameterTypes13 = new Class[3];
            array10[1] = (array10[0] = type3);
            array10[2] = type;
            RK32XXDevice.Z = ReflectUtil.getMethodSafely(clazz5, "quadTo", (Class<?>[])parameterTypes13);
            RK32XXDevice.b0 = ReflectUtil.getMethodSafely(cls6, "penUp", (Class<?>[])new Class[0]);
            final Class[] parameterTypes14;
            final Class[] array11 = parameterTypes14 = new Class[4];
            array11[0] = View.class;
            array11[1] = type3;
            array11[3] = (array11[2] = type3);
            RK32XXDevice.S = ReflectUtil.getMethodSafely(clazz4, "moveTo", (Class<?>[])parameterTypes14);
            final Class[] parameterTypes15;
            final Class[] array12 = parameterTypes15 = new Class[4];
            array12[0] = View.class;
            array12[2] = (array12[1] = type3);
            array12[3] = type;
            RK32XXDevice.Y = ReflectUtil.getMethodSafely(clazz4, "lineTo", (Class<?>[])parameterTypes15);
            final Class[] parameterTypes16;
            final Class[] array13 = parameterTypes16 = new Class[4];
            array13[0] = View.class;
            array13[2] = (array13[1] = type3);
            array13[3] = type;
            RK32XXDevice.a0 = ReflectUtil.getMethodSafely(clazz4, "quadTo", (Class<?>[])parameterTypes16);
            RK32XXDevice.c0 = ReflectUtil.getMethodSafely(clazz4, "getTouchWidth", (Class<?>[])new Class[0]);
            RK32XXDevice.d0 = ReflectUtil.getMethodSafely(clazz4, "getTouchHeight", (Class<?>[])new Class[0]);
            RK32XXDevice.e0 = ReflectUtil.getMethodSafely(clazz4, "getMaxTouchPressure", (Class<?>[])new Class[0]);
            RK32XXDevice.f0 = ReflectUtil.getMethodSafely(clazz4, "getEpdWidth", (Class<?>[])new Class[0]);
            RK32XXDevice.g0 = ReflectUtil.getMethodSafely(clazz4, "getEpdHeight", (Class<?>[])new Class[0]);
            final Class[] parameterTypes17;
            final Class[] array14 = parameterTypes17 = new Class[3];
            array14[0] = View.class;
            array14[2] = (array14[1] = float[].class);
            RK32XXDevice.h0 = ReflectUtil.getMethodSafely(clazz4, "mapToView", (Class<?>[])parameterTypes17);
            final Class[] parameterTypes18;
            final Class[] array15 = parameterTypes18 = new Class[3];
            array15[0] = View.class;
            array15[2] = (array15[1] = float[].class);
            RK32XXDevice.i0 = ReflectUtil.getMethodSafely(clazz4, "mapToEpd", (Class<?>[])parameterTypes18);
            final Class[] parameterTypes19;
            final Class[] array16 = parameterTypes19 = new Class[3];
            array16[0] = View.class;
            array16[2] = (array16[1] = float[].class);
            RK32XXDevice.j0 = ReflectUtil.getMethodSafely(clazz4, "mapFromRawTouchPoint", (Class<?>[])parameterTypes19);
            final Class[] parameterTypes20;
            final Class[] array17 = parameterTypes20 = new Class[3];
            array17[0] = View.class;
            array17[2] = (array17[1] = float[].class);
            RK32XXDevice.k0 = ReflectUtil.getMethodSafely(clazz4, "mapToRawTouchPoint", (Class<?>[])parameterTypes20);
            RK32XXDevice.l0 = ReflectUtil.getMethodSafely(clazz4, "enablePost", type);
            RK32XXDevice.m0 = ReflectUtil.getMethodSafely(clazz4, "resetEpdPost", (Class<?>[])new Class[0]);
            RK32XXDevice.n0 = ReflectUtil.getMethodSafely(clazz4, "isValidPenState", (Class<?>[])new Class[0]);
            RK32XXDevice.o0 = ReflectUtil.getMethodSafely(clazz4, "getPenState", (Class<?>[])new Class[0]);
            RK32XXDevice.p0 = ReflectUtil.getMethodSafely(clazz4, "setScreenHandWritingPenState", type);
            final Class[] parameterTypes21;
            final Class[] array18 = parameterTypes21 = new Class[2];
            array18[0] = View.class;
            array18[1] = int[].class;
            RK32XXDevice.r0 = ReflectUtil.getMethodSafely(clazz4, "setScreenHandWritingRegionLimit", (Class<?>[])parameterTypes21);
            RK32XXDevice.q0 = ReflectUtil.getMethodSafely(clazz4, "setScreenHandWritingRegionMode", type);
            final Class[] parameterTypes22;
            final Class[] array19 = parameterTypes22 = new Class[2];
            array19[0] = View.class;
            array19[1] = int[].class;
            RK32XXDevice.s0 = ReflectUtil.getMethodSafely(clazz4, "setScreenHandWritingRegionExclude", (Class<?>[])parameterTypes22);
            final Class[] parameterTypes23;
            final Class[] array20 = parameterTypes23 = new Class[2];
            array20[0] = type2;
            array20[1] = type;
            RK32XXDevice.t0 = ReflectUtil.getMethodSafely(clazz4, "applyGammaCorrection", (Class<?>[])parameterTypes23);
            RK32XXDevice.u0 = ReflectUtil.getMethodSafely(clazz4, "applyColorFilter", type);
            final Class[] parameterTypes24;
            final Class[] array21 = parameterTypes24 = new Class[6];
            array21[1] = (array21[0] = type3);
            array21[3] = (array21[2] = type3);
            array21[5] = (array21[4] = type3);
            RK32XXDevice.v0 = ReflectUtil.getStaticMethodSafely(clazz4, "startStroke", (Class<?>[])parameterTypes24);
            final Class[] parameterTypes25;
            final Class[] array22 = parameterTypes25 = new Class[6];
            array22[1] = (array22[0] = type3);
            array22[3] = (array22[2] = type3);
            array22[5] = (array22[4] = type3);
            RK32XXDevice.w0 = ReflectUtil.getStaticMethodSafely(clazz4, "addStrokePoint", (Class<?>[])parameterTypes25);
            final Class[] parameterTypes26;
            final Class[] array23 = parameterTypes26 = new Class[6];
            array23[1] = (array23[0] = type3);
            array23[3] = (array23[2] = type3);
            array23[5] = (array23[4] = type3);
            RK32XXDevice.x0 = ReflectUtil.getStaticMethodSafely(clazz4, "finishStroke", (Class<?>[])parameterTypes26);
            RK32XXDevice.I0 = ReflectUtil.getMethodSafely(clazz4, "invalidate", type);
            final Class[] parameterTypes27;
            final Class[] array24 = parameterTypes27 = new Class[5];
            array24[0] = type;
            array24[2] = (array24[1] = type);
            array24[4] = (array24[3] = type);
            RK32XXDevice.J0 = ReflectUtil.getMethodSafely(clazz4, "invalidate", (Class<?>[])parameterTypes27);
            RK32XXDevice.M0 = ReflectUtil.getMethodSafely(clazz4, "setDefaultUpdateMode", type);
            RK32XXDevice.K0 = ReflectUtil.getMethodSafely(clazz4, "getDefaultUpdateMode", (Class<?>[])new Class[0]);
            RK32XXDevice.L0 = ReflectUtil.getMethodSafely(clazz4, "resetUpdateMode", (Class<?>[])new Class[0]);
            RK32XXDevice.N0 = ReflectUtil.getMethodSafely(clazz4, "getGlobalUpdateMode", (Class<?>[])new Class[0]);
            RK32XXDevice.O0 = ReflectUtil.getMethodSafely(clazz4, "setGlobalUpdateMode", type);
            RK32XXDevice.P0 = ReflectUtil.getMethodSafely(clazz4, "setFirstDrawUpdateMode", type);
            final Class[] parameterTypes28;
            final Class[] array25 = parameterTypes28 = new Class[3];
            array25[0] = type;
            array25[2] = (array25[1] = type);
            RK32XXDevice.Q0 = ReflectUtil.getMethodSafely(clazz4, "applySysScopeUpdate", (Class<?>[])parameterTypes28);
            RK32XXDevice.U0 = ReflectUtil.getMethodSafely(clazz4, "clearSysScopeUpdate", (Class<?>[])new Class[0]);
            final Class[] parameterTypes29;
            final Class[] array26 = parameterTypes29 = new Class[3];
            array26[0] = String.class;
            array26[2] = (array26[1] = type2);
            RK32XXDevice.R0 = ReflectUtil.getMethodSafely(clazz4, "applyAppScopeUpdate", (Class<?>[])parameterTypes29);
            final Class[] parameterTypes30;
            final Class[] array27 = parameterTypes30 = new Class[5];
            array27[0] = String.class;
            array27[2] = (array27[1] = type2);
            array27[4] = (array27[3] = type);
            RK32XXDevice.S0 = ReflectUtil.getMethodSafely(clazz4, "applyAppScopeUpdate", (Class<?>[])parameterTypes30);
            RK32XXDevice.T0 = ReflectUtil.getMethodSafely(clazz4, "clearAppScopeUpdate", type2);
            RK32XXDevice.Y0 = ReflectUtil.getMethodSafely(clazz4, "applyTransientUpdate", type);
            RK32XXDevice.Z0 = ReflectUtil.getMethodSafely(clazz4, "clearTransientUpdate", type2);
            RK32XXDevice.V0 = ReflectUtil.getMethodSafely(clazz4, "enableScreenUpdate", type2);
            RK32XXDevice.W0 = ReflectUtil.getMethodSafely(clazz4, "setDisplayScheme", type);
            RK32XXDevice.X0 = ReflectUtil.getMethodSafely(clazz4, "waitForUpdateFinished", (Class<?>[])new Class[0]);
            RK32XXDevice.y0 = ReflectUtil.getMethodSafely(clazz4, "setUpdListSize", type);
            RK32XXDevice.z0 = ReflectUtil.getMethodSafely(clazz4, "inSystemFastMode", (Class<?>[])new Class[0]);
            RK32XXDevice.a1 = ReflectUtil.getMethodSafely(clazz4, "repaintEverything", type);
            RK32XXDevice.b1 = ReflectUtil.getMethodSafely(cls5, "repaintEverything", (Class<?>[])new Class[0]);
            RK32XXDevice.c1 = ReflectUtil.getMethodSafely(clazz3, "switchToA2Mode", (Class<?>[])new Class[0]);
            RK32XXDevice.d1 = ReflectUtil.getMethodSafely(clazz3, "applySFDebug", type2);
            RK32XXDevice.e1 = ReflectUtil.getMethodSafely(clazz3, "useGCForNewSurface", type2);
            RK32XXDevice.f1 = ReflectUtil.getMethodSafely(clazz3, "isInSystemFastMode", (Class<?>[])new Class[0]);
            RK32XXDevice.g1 = ReflectUtil.getMethodSafely(clazz3, "isInAppFastMode", (Class<?>[])new Class[0]);
            RK32XXDevice.h1 = ReflectUtil.getMethodSafely(clazz3, "isInFastMode", (Class<?>[])new Class[0]);
            final Class[] parameterTypes31;
            final Class[] array28 = parameterTypes31 = new Class[3];
            array28[0] = Context.class;
            array28[1] = type;
            array28[2] = String.class;
            RK32XXDevice.s1 = ReflectUtil.getMethodSafely(clazz2, "setVCom", (Class<?>[])parameterTypes31);
            RK32XXDevice.t1 = ReflectUtil.getMethodSafely(clazz2, "getVCom", String.class);
            final Class[] parameterTypes32;
            final Class[] array29 = parameterTypes32 = new Class[2];
            array29[1] = (array29[0] = String.class);
            RK32XXDevice.u1 = ReflectUtil.getMethodSafely(clazz2, "updateWaveform", (Class<?>[])parameterTypes32);
            RK32XXDevice.v1 = ReflectUtil.getMethodSafely(clazz2, "readSystemConfig", String.class);
            final Class[] parameterTypes33;
            final Class[] array30 = parameterTypes33 = new Class[2];
            array30[1] = (array30[0] = String.class);
            RK32XXDevice.w1 = ReflectUtil.getMethodSafely(clazz2, "saveSystemConfig", (Class<?>[])parameterTypes33);
            final Class[] parameterTypes34;
            final Class[] array31 = parameterTypes34 = new Class[2];
            array31[1] = (array31[0] = String.class);
            RK32XXDevice.x1 = ReflectUtil.getMethodSafely(clazz2, "updateMetadataDB", (Class<?>[])parameterTypes34);
            RK32XXDevice.y1 = ReflectUtil.getMethodSafely(clazz2, "powerCTP", type2);
            RK32XXDevice.z1 = ReflectUtil.getMethodSafely(clazz2, "powerEMTP", type2);
            RK32XXDevice.A1 = ReflectUtil.getMethodSafely(clazz2, "isCTPPowerOn", (Class<?>[])new Class[0]);
            RK32XXDevice.B1 = ReflectUtil.getMethodSafely(clazz2, "isEMTPPowerOn", (Class<?>[])new Class[0]);
            RK32XXDevice.v2 = ReflectUtil.getMethodSafely(clazz2, "getSystemProperties", String.class);
            final Class[] parameterTypes35;
            final Class[] array32 = parameterTypes35 = new Class[2];
            array32[1] = (array32[0] = String.class);
            RK32XXDevice.u2 = ReflectUtil.getMethodSafely(clazz2, "setSystemProperties", (Class<?>[])parameterTypes35);
            RK32XXDevice.w2 = ReflectUtil.getMethodSafely(clazz2, "getStandbyTimeout", (Class<?>[])new Class[0]);
            RK32XXDevice.x2 = ReflectUtil.getMethodSafely(clazz2, "setStandbyTimeout", type);
            RK32XXDevice.y2 = ReflectUtil.getMethodSafely(clazz2, "getPowerOffTimeout", (Class<?>[])new Class[0]);
            RK32XXDevice.z2 = ReflectUtil.getMethodSafely(clazz2, "setPowerOffTimeout", type);
            RK32XXDevice.A2 = ReflectUtil.getMethodSafely(clazz2, "setTextShowPassword", type2);
            RK32XXDevice.B2 = ReflectUtil.getMethodSafely(clazz2, "isTextShowPasswordOn", (Class<?>[])new Class[0]);
            RK32XXDevice.C2 = ReflectUtil.getMethodSafely(clazz2, "isSupportWidecg", Context.class);
            RK32XXDevice.j2 = ReflectUtil.getMethodSafely(clazz2, "getCTMBrightnessValues", Context.class);
            RK32XXDevice.k2 = ReflectUtil.getMethodSafely(clazz2, "getFLBrightnessValues", Context.class);
            final Class[] parameterTypes36;
            final Class[] array33 = parameterTypes36 = new Class[2];
            array33[0] = Context.class;
            array33[1] = type;
            RK32XXDevice.h2 = ReflectUtil.getMethodSafely(clazz2, "setWarmLightDeviceValue", (Class<?>[])parameterTypes36);
            final Class[] parameterTypes37;
            final Class[] array34 = parameterTypes37 = new Class[2];
            array34[0] = Context.class;
            array34[1] = type;
            RK32XXDevice.g2 = ReflectUtil.getMethodSafely(clazz2, "setColdLightDeviceValue", (Class<?>[])parameterTypes37);
            final Class[] parameterTypes38;
            final Class[] array35 = parameterTypes38 = new Class[2];
            array35[0] = Context.class;
            array35[1] = type;
            RK32XXDevice.i2 = ReflectUtil.getMethodSafely(clazz2, "getBrightnessConfig", (Class<?>[])parameterTypes38);
            final Class[] parameterTypes39;
            final Class[] array36 = parameterTypes39 = new Class[2];
            array36[0] = Context.class;
            array36[1] = type;
            RK32XXDevice.l2 = ReflectUtil.getMethodSafely(clazz2, "increaseBrightness", (Class<?>[])parameterTypes39);
            final Class[] parameterTypes40;
            final Class[] array37 = parameterTypes40 = new Class[2];
            array37[0] = Context.class;
            array37[1] = type;
            RK32XXDevice.m2 = ReflectUtil.getMethodSafely(clazz2, "decreaseBrightness", (Class<?>[])parameterTypes40);
            RK32XXDevice.n2 = ReflectUtil.getMethodSafely(clazz2, "isLightOn", type);
            RK32XXDevice.o2 = ReflectUtil.getMethodSafely(clazz2, "getSystemConfigPrefix", Context.class);
            if (Build.VERSION.SDK_INT >= 16) {
                final Class[] parameterTypes41;
                final Class[] array38 = parameterTypes41 = new Class[2];
                array38[1] = (array38[0] = int[].class);
                RK32XXDevice.C1 = ReflectUtil.getMethodSafely(InputManager.class, "setAppCTPDisableRegion", (Class<?>[])parameterTypes41);
                RK32XXDevice.E1 = ReflectUtil.getMethodSafely(InputManager.class, "isCTPDisableRegion", (Class<?>[])new Class[0]);
                RK32XXDevice.D1 = ReflectUtil.getMethodSafely(InputManager.class, "appResetCTPDisableRegion", (Class<?>[])new Class[0]);
                RK32XXDevice.c2 = ReflectUtil.getMethodSafely(InputManager.class, "isEMTPDisabled", (Class<?>[])new Class[0]);
                RK32XXDevice.d2 = ReflectUtil.getMethodSafely(InputManager.class, "isKeyboardDisabled", (Class<?>[])new Class[0]);
                RK32XXDevice.e2 = ReflectUtil.getMethodSafely(InputManager.class, "setEMTPDisabled", type2);
                RK32XXDevice.f2 = ReflectUtil.getMethodSafely(InputManager.class, "setKeyboardDisabled", type2);
                RK32XXDevice.F1 = ReflectUtil.getMethodSafely(InputManager.class, "dumpInfo", (Class<?>[])new Class[0]);
            }
            final Class<View> cls8 = clazz;
            RK32XXDevice.B0 = ReflectUtil.getMethodSafely(cls8, "enableA2", (Class<?>[])new Class[0]);
            RK32XXDevice.C0 = ReflectUtil.getMethodSafely(cls8, "disableA2", (Class<?>[])new Class[0]);
            RK32XXDevice.I1 = ReflectUtil.getMethodSafely(cls8, "switchToA2Mode", (Class<?>[])new Class[0]);
            RK32XXDevice.J1 = ReflectUtil.getMethodSafely(cls8, "toggleA2Mode", (Class<?>[])new Class[0]);
            RK32XXDevice.D0 = ReflectUtil.getMethodSafely(Environment.class, "getStorageRootDirectory", (Class<?>[])new Class[0]);
            RK32XXDevice.E0 = ReflectUtil.getMethodSafely(Environment.class, "getRemovableSDCardDirectory", (Class<?>[])new Class[0]);
            RK32XXDevice.F0 = ReflectUtil.getMethodSafely(Environment.class, "getRemovableSDCardDirs", (Class<?>[])new Class[0]);
            RK32XXDevice.G1 = ReflectUtil.getMethodSafely(WebView.class, "setCssInjectEnabled", type2);
            RK32XXDevice.H1 = ReflectUtil.getMethodSafely(cls8, "applyGCOnce", (Class<?>[])new Class[0]);
            RK32XXDevice.N1 = ReflectUtil.getMethodSafely(cls8, "setTrigger", type);
            final Class<?> classForName4;
            final Class<?> clazz6 = classForName4 = ReflectUtil.classForName("android.onyx.optimization.EInkHelper");
            final Class<?> clazz7 = classForName3;
            final Class<?> clazz8 = classForName4;
            RK32XXDevice.r2 = ReflectUtil.getMethodSafely(clazz8, "getAppScopeRefreshMode", (Class<?>[])new Class[0]);
            RK32XXDevice.s2 = ReflectUtil.getMethodSafely(clazz8, "setAppScopeRefreshMode", type);
            RK32XXDevice.K1 = ReflectUtil.getMethodSafely(ActivityManager.class, "forceStopPackageWithoutPermissionCheck", String.class);
            final Class<?> classForName5 = ReflectUtil.classForName("android.onyx.AndroidSettingsHelper");
            RK32XXDevice.O1 = ReflectUtil.getMethodSafely(classForName5, "getDefaultIpAddresses", Context.class);
            RK32XXDevice.P1 = ReflectUtil.getMethodSafely(classForName5, "isPowerSavedMode", Context.class);
            final Class[] parameterTypes42;
            final Class[] array39 = parameterTypes42 = new Class[2];
            array39[0] = Context.class;
            array39[1] = type2;
            RK32XXDevice.Q1 = ReflectUtil.getMethodSafely(classForName5, "enablePowerSavedMode", (Class<?>[])parameterTypes42);
            RK32XXDevice.a2 = ReflectUtil.getMethodSafely(classForName5, "loadCACertificate", (Class<?>[])new Class[0]);
            RK32XXDevice.b2 = ReflectUtil.getMethodSafely(classForName5, "loadUserCertificate", (Class<?>[])new Class[0]);
            RK32XXDevice.R1 = ReflectUtil.getMethodSafely(clazz7, "isHallControlEnable", (Class<?>[])new Class[0]);
            RK32XXDevice.S1 = ReflectUtil.getMethodSafely(clazz7, "enableHallControl", type2);
            final Class<?> classForName6 = ReflectUtil.classForName("android.onyx.utils.ApplicationFreezeHelper");
            final Class[] parameterTypes43;
            final Class[] array40 = parameterTypes43 = new Class[2];
            array40[0] = Context.class;
            array40[1] = String.class;
            RK32XXDevice.T1 = ReflectUtil.getMethodSafely(classForName6, "disableAppByPkgName", (Class<?>[])parameterTypes43);
            final Class[] parameterTypes44;
            final Class[] array41 = parameterTypes44 = new Class[2];
            array41[0] = Context.class;
            array41[1] = String.class;
            RK32XXDevice.U1 = ReflectUtil.getMethodSafely(classForName6, "enableAppByPkgName", (Class<?>[])parameterTypes44);
            RK32XXDevice.V1 = ReflectUtil.getMethodSafely(classForName6, "disableGooglePlay", Context.class);
            RK32XXDevice.W1 = ReflectUtil.getMethodSafely(classForName6, "enableGooglePlay", Context.class);
            RK32XXDevice.X1 = ReflectUtil.getMethodSafely(classForName6, "isGoogleAppsEnabled", Context.class);
            RK32XXDevice.Y1 = ReflectUtil.getMethodSafely(classForName6, "isGoogleAppsExists", Context.class);
            RK32XXDevice.p2 = ReflectUtil.getMethodSafely(ReflectUtil.classForName("android.onyx.utils.ActivityManagerHelper"), "getCurrentTopComponent", Context.class);
            RK32XXDevice.q2 = ReflectUtil.getMethodSafely(ActivityManager.class, "getRunningTasksWithoutPermissionCheck", type);
            RK32XXDevice.t2 = ReflectUtil.getMethodSafely(ReflectUtil.classForName("android.onyx.utils.FontsUtils"), "getFontPathMap", (Class<?>[])new Class[0]);
            RK32XXDevice.D2 = ReflectUtil.getMethodSafely(ActivityManager.class, "isSystemInMultiWindowMode", (Class<?>[])new Class[0]);
            RK32XXDevice.E2 = ReflectUtil.getMethodSafely(ActivityManager.class, "getCurrentMultiScreenMode", (Class<?>[])new Class[0]);
            RK32XXDevice.F2 = ReflectUtil.getMethodSafely(Activity.class, "isInMultiWindowMode", (Class<?>[])new Class[0]);
            RK32XXDevice.G2 = ReflectUtil.getMethodSafely(ActivityManager.class, "isPrimaryTaskInMultiWindowMode", type);
            RK32XXDevice.I2 = ReflectUtil.getMethodSafely(RK32XXDevice.H2 = ReflectUtil.classForName("android.app.ActivityManagerNative"), "getDefault", (Class<?>[])new Class[0]);
            RK32XXDevice.K2 = ReflectUtil.getMethodSafely(RK32XXDevice.H2, "updatePersistentConfiguration", Configuration.class);
            RK32XXDevice.J2 = ReflectUtil.getMethodSafely(RK32XXDevice.H2, "getConfiguration", (Class<?>[])new Class[0]);
            RK32XXDevice.L2 = ReflectUtil.getStaticMethodSafely(View.class, "setEnablePenSideButton", type2);
            RK32XXDevice.M2 = ReflectUtil.getStaticMethodSafely(View.class, "setBrushRawDrawingEnabled", type2);
            RK32XXDevice.N2 = ReflectUtil.getStaticMethodSafely(View.class, "setEraserRawDrawingEnabled", type2);
            RK32XXDevice.O2 = ReflectUtil.getStaticMethodSafely(View.class, "getStrokeParameters", type);
            final Class[] parameterTypes45;
            final Class[] array42 = parameterTypes45 = new Class[2];
            array42[0] = type;
            array42[1] = float[].class;
            RK32XXDevice.P2 = ReflectUtil.getStaticMethodSafely(View.class, "setStrokeParameters", (Class<?>[])parameterTypes45);
            RK32XXDevice.Q2 = ReflectUtil.getMethodSafely(View.class, "getEpdToViewMatrix", (Class<?>[])new Class[0]);
            RK32XXDevice.G0 = ReflectUtil.getMethodSafely(ReflectUtil.classForName("android.onyx.pm.PMClientHelper"), "enableStandbyByPressPowerButton", type2);
            RK32XXDevice.R2 = ReflectUtil.getMethodSafely(clazz6, "getSystemRefreshMode", (Class<?>[])new Class[0]);
            RK32XXDevice.S2 = ReflectUtil.getMethodSafely(clazz6, "setSystemRefreshMode", type);
            final Class[] parameterTypes46;
            final Class[] array43 = parameterTypes46 = new Class[3];
            array43[0] = String.class;
            array43[2] = (array43[1] = type2);
            RK32XXDevice.T2 = ReflectUtil.getMethodSafely(cls8, "applyApplicationFastMode", (Class<?>[])parameterTypes46);
            final Class[] parameterTypes47;
            final Class[] array44 = parameterTypes47 = new Class[5];
            array44[0] = String.class;
            array44[2] = (array44[1] = type2);
            array44[4] = (array44[3] = type);
            RK32XXDevice.U2 = ReflectUtil.getMethodSafely(cls8, "applyApplicationFastMode", (Class<?>[])parameterTypes47);
            RK32XXDevice.V2 = ReflectUtil.getMethodSafely(cls8, "clearApplicationFastMode", type2);
            Debug.d(RK32XXDevice.class, "init device finished.", new Object[0]);
            return RK32XXDevice.q;
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
        if ((n2 = RK32XXDevice$a.c[scheme.ordinal()]) != 1) {
            if (n2 != 2) {
                if (n2 != 3) {
                    if (!RK32XXDevice.W2) {
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
        if (value == RK32XXDevice.t) {
            return UpdateMode.DU;
        }
        if (value == RK32XXDevice.v) {
            return UpdateMode.GU;
        }
        if (value == RK32XXDevice.w) {
            return UpdateMode.GC;
        }
        if (value == RK32XXDevice.u) {
            return UpdateMode.DU_QUALITY;
        }
        if (value == RK32XXDevice.x) {
            return UpdateMode.DEEP_GC;
        }
        return UpdateMode.GC;
    }
    
    private static int a(final UpdatePolicy policy) {
        int v = RK32XXDevice.v;
        final int n;
        if ((n = RK32XXDevice$a.d[policy.ordinal()]) != 1) {
            if (n != 2) {
                if (!RK32XXDevice.W2) {
                    throw new AssertionError();
                }
            }
            else {
                v |= RK32XXDevice.s;
            }
        }
        else {
            v |= RK32XXDevice.r;
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
        W2 = (RK32XXDevice.class.desiredAssertionStatus() ^ true);
        p = RK32XXDevice.class.getSimpleName();
        RK32XXDevice.q = null;
        RK32XXDevice.r = 0;
        RK32XXDevice.s = 0;
        RK32XXDevice.t = 0;
        RK32XXDevice.u = 0;
        RK32XXDevice.v = 0;
        RK32XXDevice.w = 0;
        RK32XXDevice.x = 0;
        RK32XXDevice.y = 0;
        RK32XXDevice.z = 0;
        RK32XXDevice.A = 0;
        RK32XXDevice.B = 0;
        RK32XXDevice.C = 0;
        RK32XXDevice.D = 0;
        RK32XXDevice.E = 0;
        RK32XXDevice.F = 0;
        RK32XXDevice.G = 0;
        RK32XXDevice.M = null;
        RK32XXDevice.N = null;
        RK32XXDevice.O = null;
        RK32XXDevice.P = null;
        RK32XXDevice.Q = null;
        RK32XXDevice.R = null;
        RK32XXDevice.S = null;
        RK32XXDevice.T = null;
        RK32XXDevice.U = null;
        RK32XXDevice.V = null;
        RK32XXDevice.W = null;
        RK32XXDevice.X = null;
        RK32XXDevice.Y = null;
        RK32XXDevice.Z = null;
        RK32XXDevice.a0 = null;
        RK32XXDevice.b0 = null;
        RK32XXDevice.c0 = null;
        RK32XXDevice.d0 = null;
        RK32XXDevice.e0 = null;
        RK32XXDevice.f0 = null;
        RK32XXDevice.g0 = null;
        RK32XXDevice.h0 = null;
        RK32XXDevice.i0 = null;
        RK32XXDevice.j0 = null;
        RK32XXDevice.k0 = null;
        RK32XXDevice.l0 = null;
        RK32XXDevice.m0 = null;
        RK32XXDevice.n0 = null;
        RK32XXDevice.o0 = null;
        RK32XXDevice.p0 = null;
        RK32XXDevice.q0 = null;
        RK32XXDevice.r0 = null;
        RK32XXDevice.s0 = null;
        RK32XXDevice.t0 = null;
        RK32XXDevice.u0 = null;
        RK32XXDevice.v0 = null;
        RK32XXDevice.w0 = null;
        RK32XXDevice.x0 = null;
        RK32XXDevice.y0 = null;
        RK32XXDevice.z0 = null;
        RK32XXDevice.A0 = null;
        RK32XXDevice.H0 = null;
        RK32XXDevice.I0 = null;
        RK32XXDevice.J0 = null;
        RK32XXDevice.K0 = null;
        RK32XXDevice.L0 = null;
        RK32XXDevice.M0 = null;
        RK32XXDevice.N0 = null;
        RK32XXDevice.O0 = null;
        RK32XXDevice.P0 = null;
        RK32XXDevice.Q0 = null;
        RK32XXDevice.R0 = null;
        RK32XXDevice.S0 = null;
        RK32XXDevice.T0 = null;
        RK32XXDevice.U0 = null;
        RK32XXDevice.V0 = null;
        RK32XXDevice.W0 = null;
        RK32XXDevice.X0 = null;
        RK32XXDevice.Y0 = null;
        RK32XXDevice.Z0 = null;
        RK32XXDevice.a1 = null;
        RK32XXDevice.b1 = null;
        RK32XXDevice.c1 = null;
        RK32XXDevice.d1 = null;
        RK32XXDevice.e1 = null;
        RK32XXDevice.f1 = null;
        RK32XXDevice.g1 = null;
        RK32XXDevice.h1 = null;
        RK32XXDevice.T2 = null;
        RK32XXDevice.U2 = null;
        RK32XXDevice.V2 = null;
    }
    
    @Override
    public File getStorageRootDirectory() {
        return (File)ReflectUtil.invokeMethodSafely(RK32XXDevice.D0, null, new Object[0]);
    }
    
    @Override
    public File getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory();
    }
    
    @Override
    public File getRemovableSDCardDirectory() {
        return (File)ReflectUtil.invokeMethodSafely(RK32XXDevice.E0, null, new Object[0]);
    }
    
    @Override
    public List<File> getRemovableSDCardDirs() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(RK32XXDevice.F0, null, new Object[0])) instanceof List) {
            return (List<File>)invokeMethodSafely;
        }
        return Collections.emptyList();
    }
    
    @Override
    public boolean isFileOnRemovableSDCard(final File file) {
        return file.getAbsolutePath().startsWith(this.getRemovableSDCardDirectory().getAbsolutePath());
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
        final int a = this.a(mode);
        try {
            if (!RK32XXDevice.W2 && RK32XXDevice.I0 == null) {
                throw new AssertionError();
            }
            RK32XXDevice.I0.invoke(view, a);
        }
        catch (final InvocationTargetException ex) {}
        catch (final IllegalAccessException ex2) {}
        catch (final IllegalArgumentException ex3) {}
    }
    
    @Override
    public void invalidate(final View view, final int left, final int top, final int right, final int bottom, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            if (!RK32XXDevice.W2 && RK32XXDevice.J0 == null) {
                throw new AssertionError();
            }
            final Method j0 = RK32XXDevice.J0;
            final Object[] array;
            final Object[] args = array = new Object[5];
            final int i = a;
            final Object[] array2 = array;
            final Object[] array3 = array;
            final Object[] array4 = array;
            array[0] = left;
            array4[1] = top;
            array3[2] = right;
            array2[3] = bottom;
            args[4] = i;
            j0.invoke(view, args);
        }
        catch (final InvocationTargetException ex) {}
        catch (final IllegalAccessException ex2) {}
        catch (final IllegalArgumentException ex3) {}
    }
    
    @Override
    public void postInvalidate(final View view, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            if (!RK32XXDevice.W2 && RK32XXDevice.H0 == null) {
                throw new AssertionError();
            }
            Log.d(RK32XXDevice.p, "dst mode: " + a);
            RK32XXDevice.H0.invoke(view, a);
        }
        catch (final InvocationTargetException ex) {}
        catch (final IllegalAccessException ex2) {}
        catch (final IllegalArgumentException ex3) {}
    }
    
    @Override
    public void refreshScreen(final View view, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            if (!RK32XXDevice.W2 && RK32XXDevice.M == null) {
                throw new AssertionError();
            }
            RK32XXDevice.M.invoke(view, a);
        }
        catch (final InvocationTargetException ex) {}
        catch (final IllegalAccessException ex2) {}
        catch (final IllegalArgumentException ex3) {}
    }
    
    @Override
    public void byPass(final int count) {
        try {
            final Method a0 = RK32XXDevice.A0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(a0, receiver, count);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void refreshScreenRegion(final View view, final int left, final int top, final int width, final int height, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            if (!RK32XXDevice.W2 && RK32XXDevice.N == null) {
                throw new AssertionError();
            }
            final Method n = RK32XXDevice.N;
            final Object[] array;
            final Object[] args = array = new Object[5];
            final int i = a;
            final Object[] array2 = array;
            final Object[] array3 = array;
            final Object[] array4 = array;
            array[0] = left;
            array4[1] = top;
            array3[2] = width;
            array2[3] = height;
            args[4] = i;
            n.invoke(view, args);
        }
        catch (final Exception ex) {}
    }
    
    @Override
    public void screenshot(final View view, final int rotation, final String path) {
        try {
            ReflectUtil.invokeMethodSafely(RK32XXDevice.O, view, rotation, path);
        }
        catch (final Exception ex) {}
    }
    
    @Override
    public void setStrokeColor(final int color) {
        try {
            final Method t = RK32XXDevice.T;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(t, receiver, color);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void setStrokeStyle(final int style) {
        try {
            final Method u = RK32XXDevice.U;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(u, receiver, style);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void setPainterStyle(final boolean antiAlias, final Paint.Style strokeStyle, final Paint.Join joinStyle, final Paint.Cap capStyle) {
        try {
            final Method w = RK32XXDevice.W;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(w, receiver, antiAlias, strokeStyle, joinStyle, capStyle);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void setStrokeWidth(final float width) {
        try {
            final Method v = RK32XXDevice.V;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(v, receiver, width);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void moveTo(final float x, final float y, final float width) {
        try {
            final Method r = RK32XXDevice.R;
            final Object receiver = null;
            try {
                final Object[] args = new Object[3];
                final Object[] array;
                (array = args)[0] = x;
                array[1] = y;
                args[2] = width;
                ReflectUtil.invokeMethodSafely(r, receiver, args);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void moveTo(final View view, final float x, final float y, final float width) {
        try {
            final Method s = RK32XXDevice.S;
            final Object receiver = null;
            try {
                final Object[] args = new Object[4];
                final Object[] array2;
                final Object[] array;
                (array = (array2 = args))[0] = view;
                array[1] = x;
                array2[2] = y;
                args[3] = width;
                ReflectUtil.invokeMethodSafely(s, receiver, args);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public boolean supportDFB() {
        return RK32XXDevice.X != null;
    }
    
    @Override
    public boolean supportRegal() {
        final Method p;
        final Boolean b;
        return (p = RK32XXDevice.P) != null && (b = (Boolean)ReflectUtil.invokeMethodSafely(p, null, new Object[0])) != null && b;
    }
    
    @Override
    public void enableRegal(final boolean enable) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.Q, null, enable);
    }
    
    @Override
    public void lineTo(final float x, final float y, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            final Method x2 = RK32XXDevice.X;
            final Object receiver = null;
            try {
                final Object[] array;
                final Object[] args = array = new Object[3];
                final int i = a;
                final Object[] array2 = array;
                array[0] = x;
                array2[1] = y;
                args[2] = i;
                ReflectUtil.invokeMethodSafely(x2, receiver, args);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void lineTo(final View view, final float x, final float y, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            final Method y2 = RK32XXDevice.Y;
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
                ReflectUtil.invokeMethodSafely(y2, receiver, args);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void quadTo(final float x, final float y, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            final Method z = RK32XXDevice.Z;
            final Object receiver = null;
            try {
                final Object[] array;
                final Object[] args = array = new Object[3];
                final int i = a;
                final Object[] array2 = array;
                array[0] = x;
                array2[1] = y;
                args[2] = i;
                ReflectUtil.invokeMethodSafely(z, receiver, args);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void quadTo(final View view, final float x, final float y, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            final Method a2 = RK32XXDevice.a0;
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
                ReflectUtil.invokeMethodSafely(a2, receiver, args);
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
            ReflectUtil.invokeMethodSafely(RK32XXDevice.b0, null, new Object[0]);
        }
        catch (final Exception exception) {
            exception.printStackTrace();
        }
    }
    
    @Override
    public float getTouchWidth() {
        try {
            final Method c0 = RK32XXDevice.c0;
            final Object receiver = null;
            try {
                return (float)ReflectUtil.invokeMethodSafely(c0, receiver, new Object[0]);
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
            final Method g0 = RK32XXDevice.g0;
            final Object receiver = null;
            try {
                return (float)ReflectUtil.invokeMethodSafely(g0, receiver, new Object[0]);
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
            final Method f0 = RK32XXDevice.f0;
            final Object receiver = null;
            try {
                return (float)ReflectUtil.invokeMethodSafely(f0, receiver, new Object[0]);
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
            final Method i0 = RK32XXDevice.i0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(i0, receiver, view, src, dst);
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
            final Method j0 = RK32XXDevice.j0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(j0, receiver, view, src, dst);
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
            final Method k0 = RK32XXDevice.k0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(k0, receiver, view, src, dst);
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
            final Method h0 = RK32XXDevice.h0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(h0, receiver, view, src, dst);
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
            final Method d0 = RK32XXDevice.d0;
            final Object receiver = null;
            try {
                return (float)ReflectUtil.invokeMethodSafely(d0, receiver, new Object[0]);
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
            final Method e0 = RK32XXDevice.e0;
            final Object receiver = null;
            try {
                return (float)ReflectUtil.invokeMethodSafely(e0, receiver, new Object[0]);
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
            final Method v0 = RK32XXDevice.v0;
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
                return (float)ReflectUtil.invokeMethodSafely(v0, receiver, args);
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
            final Method w0 = RK32XXDevice.w0;
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
                return (float)ReflectUtil.invokeMethodSafely(w0, receiver, args);
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
            final Method x2 = RK32XXDevice.x0;
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
                return (float)ReflectUtil.invokeMethodSafely(x2, receiver, args);
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
        return ReflectUtil.isStaticMethodAvailable(RK32XXDevice.T)
                && ReflectUtil.isStaticMethodAvailable(RK32XXDevice.U)
                && ReflectUtil.isStaticMethodAvailable(RK32XXDevice.V);
    }

    @Override
    boolean hasStrokeDataTransportCapability() {
        return ReflectUtil.isStaticMethodAvailable(RK32XXDevice.v0)
                && ReflectUtil.isStaticMethodAvailable(RK32XXDevice.w0)
                && ReflectUtil.isStaticMethodAvailable(RK32XXDevice.x0);
    }

    @Override
    boolean hasAdvancedStrokeConfigurationCapability() {
        return ReflectUtil.isStaticMethodAvailable(RK32XXDevice.L2)
                && ReflectUtil.isStaticMethodAvailable(RK32XXDevice.M2)
                && ReflectUtil.isStaticMethodAvailable(RK32XXDevice.N2)
                && ReflectUtil.isStaticMethodAvailable(RK32XXDevice.O2)
                && ReflectUtil.isStaticMethodAvailable(RK32XXDevice.P2);
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
            ReflectUtil.invokeMethodSafely(RK32XXDevice.l0, view, enable);
        }
        catch (final Exception ex) {}
    }
    
    @Override
    public void enablePost(final int enable) {
        try {
            final Method l0 = RK32XXDevice.l0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(l0, receiver, enable);
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
            ReflectUtil.invokeMethodSafely(RK32XXDevice.m0, null, new Object[0]);
        }
        catch (final Exception exception) {
            exception.printStackTrace();
        }
    }
    
    @Override
    public boolean isValidPenState() {
        try {
            final Method n0 = RK32XXDevice.n0;
            final Object receiver = null;
            try {
                return (boolean)ReflectUtil.invokeMethodSafely(n0, receiver, new Object[0]);
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
            final Method o0 = RK32XXDevice.o0;
            final Object receiver = null;
            try {
                return (int)ReflectUtil.invokeMethodSafely(o0, receiver, new Object[0]);
            }
            catch (final Exception ex) {
                return 0;
            }
        }
        catch (final Exception ex2) {}
        return 0;
    }
    
    public boolean supportScreenHandWriting() {
        return RK32XXDevice.p0 != null;
    }
    
    @Override
    public void setScreenHandWritingPenState(final View view, final int penState) {
        try {
            ReflectUtil.invokeMethodSafely(RK32XXDevice.p0, view, penState);
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
            ReflectUtil.invokeMethodSafely(RK32XXDevice.r0, view, view, array);
        }
        catch (final Exception ex) {}
    }
    
    @Override
    public void setScreenHandWritingRegionMode(final View view, final int mode) {
        try {
            ReflectUtil.invokeMethodSafely(RK32XXDevice.q0, view, mode);
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
            ReflectUtil.invokeMethodSafely(RK32XXDevice.s0, view, view, array);
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
        try {
            RK32XXDevice.V0.invoke(view, enable);
        }
        catch (final Exception ex) {}
        return true;
    }
    
    @Override
    public boolean setDisplayScheme(final int scheme) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.W0, null, scheme);
        return true;
    }
    
    @Override
    public void waitForUpdateFinished() {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.X0, null, new Object[0]);
    }
    
    @Override
    public UpdateMode getViewDefaultUpdateMode(final View view) {
        final Integer n;
        if ((n = (Integer)ReflectUtil.invokeMethodSafely(RK32XXDevice.K0, view, new Object[0])) == null) {
            return UpdateMode.GU;
        }
        return this.b(n);
    }
    
    @Override
    public void resetViewUpdateMode(final View view) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.L0, view, new Object[0]);
    }
    
    @Override
    public boolean setViewDefaultUpdateMode(final View view, final UpdateMode mode) {
        return ReflectUtil.invokeMethodSafely(RK32XXDevice.M0, view, this.a(mode)) != null;
    }
    
    @Override
    public UpdateMode getSystemDefaultUpdateMode() {
        final Integer n;
        if ((n = (Integer)ReflectUtil.invokeMethodSafely(RK32XXDevice.N0, null, new Object[0])) == null) {
            return UpdateMode.GU;
        }
        return this.b(n);
    }
    
    @Override
    public boolean setSystemDefaultUpdateMode(final UpdateMode mode) {
        return ReflectUtil.invokeMethodSafely(RK32XXDevice.O0, null, this.a(mode)) != null;
    }
    
    public boolean setFirstDrawUpdateMode(final UpdateMode mode) {
        return ReflectUtil.invokeMethodSafely(RK32XXDevice.P0, null, this.a(mode)) != null;
    }
    
    @Override
    public boolean applySysScopeUpdate(final UpdateMode mode, final UpdateScheme scheme, final int count) {
        final Method q0 = RK32XXDevice.Q0;
        final Object[] array = new Object[3];
        final Object[] array2;
        final Object[] args = array2 = array;
        args[0] = this.a(mode);
        array2[1] = this.a(scheme);
        array[2] = count;
        return ReflectUtil.invokeMethodSafely(q0, null, args) != null;
    }
    
    @Override
    public boolean clearSysScopeUpdate() {
        return ReflectUtil.invokeMethodSafely(RK32XXDevice.U0, null, new Object[0]) != null;
    }
    
    @Override
    public boolean applyAppScopeUpdate(final String application, final boolean enable, final boolean clear) {
        Method method;
        if ((method = RK32XXDevice.R0) == null) {
            method = RK32XXDevice.T2;
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
        if ((method = RK32XXDevice.S0) == null) {
            method = RK32XXDevice.U2;
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
        if ((method = RK32XXDevice.T0) == null) {
            method = RK32XXDevice.V2;
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
        return (b = (Boolean)this.a(context, RK32XXDevice.i1, context)) != null && b;
    }
    
    @Override
    public boolean closeFrontLight(final Context context) {
        final Boolean b;
        return (b = (Boolean)this.a(context, RK32XXDevice.k1, context)) != null && b;
    }
    
    @Override
    public boolean openFrontLight(final int type) {
        final Object invokeMethodSafely;
        return (invokeMethodSafely = ReflectUtil.invokeMethodSafely(RK32XXDevice.j1, null, type)) instanceof Boolean && (boolean)invokeMethodSafely;
    }
    
    @Override
    public boolean closeFrontLight(final int type) {
        final Object invokeMethodSafely;
        return (invokeMethodSafely = ReflectUtil.invokeMethodSafely(RK32XXDevice.l1, null, type)) instanceof Boolean && (boolean)invokeMethodSafely;
    }
    
    @Override
    public int getFrontLightDeviceValue(final Context context) {
        final Integer n;
        if ((n = (Integer)this.a(context, RK32XXDevice.m1, context)) == null) {
            return 0;
        }
        return n;
    }
    
    @Override
    public boolean setFrontLightDeviceValue(final Context context, final int value) {
        final Method n1 = RK32XXDevice.n1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = value;
        return this.a(context, n1, args) != null;
    }
    
    @Override
    public int getFrontLightConfigValue(final Context context) {
        return ReflectUtil.getSafely((Integer)this.a(context, RK32XXDevice.o1, context));
    }
    
    @Override
    public boolean setFrontLightConfigValue(final Context context, final int value) {
        final Method p1 = RK32XXDevice.p1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = value;
        this.a(context, p1, args);
        return true;
    }
    
    @Override
    public List<Integer> getFrontLightValueList(final Context context) {
        return Arrays.asList(this.getFLBrightnessValues(context));
    }
    
    @Override
    public void led(final Context context, final boolean on) {
        this.a(context, RK32XXDevice.q1, on);
    }
    
    @Override
    public boolean setLedColor(final String color, final int on) {
        final Method r1 = RK32XXDevice.r1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = color;
        array[1] = on;
        this.a(null, r1, args);
        return true;
    }
    
    @Override
    public void setVCom(final Context context, final int value, final String path) {
        final Method s1 = RK32XXDevice.s1;
        final Object[] args;
        final Object[] array = args = new Object[3];
        args[0] = context;
        array[1] = value;
        array[2] = path;
        this.a(context, s1, args);
    }
    
    @Override
    public int getVCom(final Context context, final String path) {
        final Integer n;
        if ((n = (Integer)this.a(context, RK32XXDevice.t1, path)) == null) {
            return 0;
        }
        return n;
    }
    
    @Override
    public void updateWaveform(final Context context, final String path, final String target) {
        final Method u1 = RK32XXDevice.u1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = path;
        array[1] = target;
        this.a(context, u1, args);
    }
    
    @Override
    public String readSystemConfig(final Context context, final String key) {
        final Object a;
        if ((a = this.a(context, RK32XXDevice.v1, key)) != null && !a.equals("")) {
            return a.toString();
        }
        return "";
    }
    
    @Override
    public boolean saveSystemConfig(final Context context, final String key, final String value) {
        final Method w1 = RK32XXDevice.w1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = key;
        array[1] = value;
        return ReflectUtil.getSafely((Boolean)this.a(context, w1, args));
    }
    
    @Override
    public void updateMetadataDB(final Context context, final String path, final String target) {
        final Method x1 = RK32XXDevice.x1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = path;
        array[1] = target;
        this.a(context, x1, args);
    }
    
    UpdateMode a(final EPDMode mode) {
        switch (RK32XXDevice$a.a[mode.ordinal()]) {
            default: {
                return UpdateMode.DU_QUALITY;
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
        switch (RK32XXDevice$a.b[mode.ordinal()]) {
            default: {
                n = RK32XXDevice.F;
                break;
            }
            case 14: {
                n = RK32XXDevice.G;
                break;
            }
            case 13: {
                if ((n = RK32XXDevice.E) != 0) {
                    break;
                }
                n = RK32XXDevice.v;
                break;
            }
            case 12: {
                if ((n = RK32XXDevice.D) != 0) {
                    break;
                }
                n = RK32XXDevice.v;
                break;
            }
            case 11: {
                n = RK32XXDevice.C;
                break;
            }
            case 10: {
                n = RK32XXDevice.B;
                break;
            }
            case 9: {
                n = RK32XXDevice.A;
                break;
            }
            case 8: {
                n = RK32XXDevice.z;
                break;
            }
            case 7: {
                n = RK32XXDevice.y;
                break;
            }
            case 6: {
                n = RK32XXDevice.x;
                break;
            }
            case 5: {
                n = RK32XXDevice.w;
                break;
            }
            case 4: {
                n = RK32XXDevice.v;
                break;
            }
            case 3: {
                n = RK32XXDevice.u;
                break;
            }
            case 1:
            case 2: {
                n = RK32XXDevice.t;
                break;
            }
        }
        return n;
    }
    
    @Override
    public void disableA2ForSpecificView(final View view) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.C0, view, new Object[0]);
    }
    
    @Override
    public void enableA2ForSpecificView(final View view) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.B0, view, new Object[0]);
    }
    
    @Override
    public void setWebViewContrastOptimize(final WebView view, final boolean enabled) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.G1, view, enabled);
    }
    
    @Override
    public void setUpdListSize(final int size) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.y0, null, size);
    }
    
    @Override
    public boolean inSystemFastMode() {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(RK32XXDevice.z0, null, new Object[0])) != null && b;
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
        this.a(null, RK32XXDevice.y1, on);
    }
    
    @Override
    public void powerEMTP(final boolean on) {
        this.a(null, RK32XXDevice.z1, on);
    }
    
    @Override
    public boolean isCTPPowerOn() {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(RK32XXDevice.A1, null, new Object[0])) != null && b;
    }
    
    @Override
    public boolean isEMTPPowerOn() {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(RK32XXDevice.B1, null, new Object[0])) != null && b;
    }
    
    @Override
    public boolean isCTPDisableRegion(final Context context) {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(RK32XXDevice.E1, this.b(context), new Object[0])) != null && b;
    }
    
    @Override
    public void appResetCTPDisableRegion(final Context context) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.D1, this.b(context), new Object[0]);
    }
    
    @Override
    public void setAppCTPDisableRegion(final Context context, final int[] disableRegionArray, @Nullable final int[] excludeRegionArray) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.C1, this.b(context), disableRegionArray, excludeRegionArray);
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
        ReflectUtil.invokeMethodSafely(RK32XXDevice.I1, null, new Object[0]);
    }
    
    @Override
    public void toggleA2Mode() {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.J1, null, new Object[0]);
    }
    
    @Override
    public void applyGammaCorrection(final boolean apply, final int value) {
        final Method t0 = RK32XXDevice.t0;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = apply;
        array[1] = value;
        ReflectUtil.invokeMethodSafely(t0, null, args);
    }
    
    @Override
    public void applyColorFilter(final int value) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.u0, null, value);
    }
    
    @Override
    public void applyGCOnce() {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.H1, null, new Object[0]);
    }
    
    @Override
    public String getCTPInfo() {
        return com.onyx.android.sdk.device.a.a(new File("/sys/onyx_misc/captp_fwver"));
    }
    
    @Override
    public boolean hasFLBrightness(final Context context) {
        final Boolean b;
        return (b = (Boolean)this.a(context, RK32XXDevice.L1, context)) != null && b;
    }
    
    @Override
    public boolean hasCTMBrightness(final Context context) {
        final Boolean b;
        return (b = (Boolean)this.a(context, RK32XXDevice.M1, context)) != null && b;
    }
    
    @Override
    public void setTrigger(final int count) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.N1, null, count);
    }
    
    @Override
    public void forceStopApplication(final Context context, final String pkgName) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.K1, this.a(context), pkgName);
    }
    
    @Nullable
    @Override
    public String getIPAddress(final Context context) {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(RK32XXDevice.O1, null, context)) instanceof String) {
            return (String)invokeMethodSafely;
        }
        return null;
    }
    
    @Override
    public boolean isPowerSavedMode(final Context context) {
        return ReflectUtil.getSafely((Boolean)ReflectUtil.invokeMethodSafely(RK32XXDevice.P1, null, context));
    }
    
    @Override
    public void enablePowerSavedMode(final Context context, final boolean enable) {
        final Method q1 = RK32XXDevice.Q1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = enable;
        ReflectUtil.invokeMethodSafely(q1, null, args);
    }
    
    @Override
    public boolean isHallControlEnable(final Context context) {
        return ReflectUtil.getSafely((Boolean)this.a(context, RK32XXDevice.R1, new Object[0]));
    }
    
    @Override
    public void enableHallControl(final Context context, final boolean enable) {
        this.a(context, RK32XXDevice.S1, enable);
    }
    
    @Override
    public boolean isGooglePlayEnabled(final Context context) {
        return ReflectUtil.getSafely((Boolean)ReflectUtil.invokeMethodSafely(RK32XXDevice.X1, null, context));
    }
    
    @Override
    public boolean isGoogleAppsExists(final Context context) {
        return ReflectUtil.getSafely((Boolean)ReflectUtil.invokeMethodSafely(RK32XXDevice.Y1, null, context));
    }
    
    @Override
    public void freezeApplication(final Context context, final String pkgName, final int userId) {
        final Method t1 = RK32XXDevice.T1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = context;
        array[1] = pkgName;
        ReflectUtil.invokeMethodSafely(t1, null, args);
    }
    
    @Override
    public void unfreezeApplication(final Context context, final String pkgName, final int userId) {
        final Method u1 = RK32XXDevice.U1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = context;
        array[1] = pkgName;
        ReflectUtil.invokeMethodSafely(u1, null, args);
    }
    
    @Override
    public void freezeGooglePlay(final Context context) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.V1, null, context);
    }
    
    @Override
    public void unfreezeGooglePlay(final Context context) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.W1, null, context);
    }
    
    @Override
    public void repaintEveryThing(final UpdateMode mode) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.a1, null, this.a(mode));
    }
    
    @Override
    public void repaintEveryThing() {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.b1, null, new Object[0]);
    }
    
    @Override
    public void useGCForNewSurface(final boolean apply) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.e1, null, apply);
    }
    
    @Override
    public void applySystemFastMode(final boolean enable) {
        if (enable) {
            ReflectUtil.invokeMethodSafely(RK32XXDevice.c1, null, new Object[0]);
        }
        else {
            this.clearSysScopeUpdate();
        }
    }
    
    @Override
    public void setCTMBrightnessValue(final int type, final int value) {
        if (type != 0) {
            if (type == 1) {
                final Method z1 = RK32XXDevice.Z1;
                final Object[] args;
                final Object[] array = args = new Object[2];
                args[0] = "/sys/class/backlight/warm/brightness";
                array[1] = value;
                ReflectUtil.invokeMethodSafely(z1, null, args);
            }
        }
        else {
            final Method z2 = RK32XXDevice.Z1;
            final Object[] args2;
            final Object[] array2 = args2 = new Object[2];
            args2[0] = "/sys/class/backlight/white/brightness";
            array2[1] = value;
            ReflectUtil.invokeMethodSafely(z2, null, args2);
        }
    }
    
    @Override
    public String[] loadCACertificate() {
        return (String[])ReflectUtil.invokeMethodSafely(RK32XXDevice.a2, null, new Object[0]);
    }
    
    @Override
    public String[] loadUserCertificate() {
        return (String[])ReflectUtil.invokeMethodSafely(RK32XXDevice.b2, null, new Object[0]);
    }
    
    @Override
    public void applySFDebug(final boolean enableDebug) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.d1, null, enableDebug);
    }
    
    @Override
    public boolean isEMTPDisabled(final Context context) {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(RK32XXDevice.c2, this.b(context), new Object[0])) != null && b;
    }
    
    @Override
    public boolean isKeyboardDisabled(final Context context) {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(RK32XXDevice.d2, this.b(context), new Object[0])) != null && b;
    }
    
    @Override
    public void setEMTPDisabled(final Context context, final boolean disabled) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.e2, this.b(context), disabled);
    }
    
    @Override
    public void setKeyboardDisabled(final Context context, final boolean disabled) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.f2, this.b(context), disabled);
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
    public Integer[][] getNaturalLightValues(final Context context) {
        final Object a;
        if ((a = this.a(context, RK32XXDevice.j2, context)) != null && a instanceof Integer[][]) {
            return (Integer[][])a;
        }
        return null;
    }
    
    @Override
    public Integer[] getFLBrightnessValues(final Context context) {
        final Object a;
        if ((a = this.a(context, RK32XXDevice.k2, context)) != null && a instanceof Integer[]) {
            return (Integer[])a;
        }
        return null;
    }
    
    @Override
    public int getWarmLightConfigValue(final Context context) {
        final Method i2 = RK32XXDevice.i2;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = context;
        array[1] = 2;
        final Object a;
        if ((a = this.a(context, i2, args)) != null) {
            return (int)a;
        }
        return 0;
    }
    
    @Override
    public int getColdLightConfigValue(final Context context) {
        final Method i2 = RK32XXDevice.i2;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = context;
        array[1] = 3;
        final Object a;
        if ((a = this.a(context, i2, args)) != null) {
            return (int)a;
        }
        return 0;
    }
    
    @Override
    public boolean setWarmLightDeviceValue(final Context context, final int value) {
        final Method h2 = RK32XXDevice.h2;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = value;
        return this.a(context, h2, args) != null;
    }
    
    @Override
    public boolean setColdLightDeviceValue(final Context context, final int value) {
        final Method g2 = RK32XXDevice.g2;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = value;
        return this.a(context, g2, args) != null;
    }
    
    @Override
    public boolean increaseBrightness(final Context context, final int colorTemp) {
        return LightUtils.adjustBrightness(context, colorTemp, true);
    }
    
    @Override
    public boolean decreaseBrightness(final Context context, final int colorTemp) {
        return LightUtils.adjustBrightness(context, colorTemp, false);
    }
    
    @Nullable
    @Override
    public ComponentName getCurrentTopComponent(final Context context) {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(RK32XXDevice.p2, null, context)) != null && invokeMethodSafely instanceof ComponentName) {
            return (ComponentName)invokeMethodSafely;
        }
        return null;
    }
    
    @Override
    public List<ActivityManager.RunningTaskInfo> getRunningTasksWithoutPermissionCheck(final Context context, final int maxNum) {
        try {
            return (List)ReflectUtil.invokeMethodSafely(RK32XXDevice.q2, this.a(context), maxNum);
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
        return (this.hasCTMBrightness(context) || this.hasFLBrightness(context)) && (b = (Boolean)ReflectUtil.invokeMethodSafely(RK32XXDevice.n2, null, type)) != null && b;
    }
    
    @Override
    public boolean isEnabledStartActivityInMultiWindowMode(final Context context) {
        return false;
    }
    
    @Override
    public String getSystemConfigPrefix(final Context context) {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(RK32XXDevice.o2, null, context)) instanceof String) {
            return (String)invokeMethodSafely;
        }
        return null;
    }
    
    @Override
    public void setAppScopeRefreshMode(final UpdateOption updateOption) {
        Method method;
        if ((method = RK32XXDevice.s2) == null) {
            method = RK32XXDevice.S2;
        }
        ReflectUtil.invokeMethodSafely(method, null, this.a(updateOption));
    }
    
    @Override
    public UpdateOption getAppScopeRefreshMode() {
        Method method;
        if ((method = RK32XXDevice.r2) == null) {
            method = RK32XXDevice.R2;
        }
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(method, null, new Object[0])) instanceof Integer) {
            return this.a((int)invokeMethodSafely);
        }
        return UpdateOption.NORMAL;
    }
    
    @Override
    public void setSystemProperties(final String key, final String value) {
        final Method u2 = RK32XXDevice.u2;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = key;
        array[1] = value;
        this.a(null, u2, args);
    }
    
    @Override
    public String getSystemProperties(final String key) {
        final Object a;
        if ((a = this.a(null, RK32XXDevice.v2, key)) != null && !a.equals("")) {
            return a.toString();
        }
        return "";
    }
    
    @Override
    public void dumpCTPInfo(final Context context) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.F1, this.b(context), new Object[0]);
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
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(RK32XXDevice.w2, null, new Object[0])) instanceof Integer) {
            return (int)invokeMethodSafely;
        }
        return 0;
    }
    
    @Override
    public void setStandbyTimeout(final int ms) {
        this.a(null, RK32XXDevice.x2, ms);
    }
    
    @Override
    public int getPowerOffTimeout() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(RK32XXDevice.y2, null, new Object[0])) instanceof Integer) {
            return (int)invokeMethodSafely;
        }
        return 0;
    }
    
    @Override
    public void setPowerOffTimeout(final int ms) {
        this.a(null, RK32XXDevice.z2, ms);
    }
    
    @Override
    public boolean isInMultiWindowMode(final Activity activity) {
        final Object invokeMethodSafely;
        return (invokeMethodSafely = ReflectUtil.invokeMethodSafely(RK32XXDevice.F2, activity, new Object[0])) instanceof Boolean && (boolean)invokeMethodSafely;
    }
    
    @Override
    public boolean isSystemInMultiWindowMode(final Context context) {
        return context != null && ReflectUtil.getSafely((Boolean)ReflectUtil.invokeMethodSafely(RK32XXDevice.D2, this.a(context), new Object[0]));
    }
    
    @Override
    public boolean isPrimaryTaskInMultiWindowMode(final Context context, final int taskId) {
        return ReflectUtil.getSafely((Boolean)ReflectUtil.invokeMethodSafely(RK32XXDevice.G2, this.a(context), taskId));
    }
    
    @Override
    public void setSystemFontSize(final Context context, final float scale) {
        if (Build.VERSION.SDK_INT >= 28) {
            super.setSystemFontSize(context, scale);
            return;
        }
        final Object invokeMethodSafely = ReflectUtil.invokeMethodSafely(RK32XXDevice.I2, RK32XXDevice.H2, new Object[0]);
        final Configuration configuration;
        if ((configuration = (Configuration)ReflectUtil.invokeMethodSafely(RK32XXDevice.J2, invokeMethodSafely, new Object[0])) == null) {
            return;
        }
        configuration.fontScale = scale;
        try {
            ReflectUtil.invokeMethodSafely(RK32XXDevice.K2, invokeMethodSafely, configuration);
        }
        catch (final Exception ex) {
            Debug.d(RK32XXDevice.class, "setSystemFontSize updatePersistentConfiguration exception.", new Object[0]);
        }
    }
    
    @Override
    public void setEnablePenSideButton(final boolean enable) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.L2, null, enable);
    }
    
    @Override
    public void setBrushRawDrawingEnabled(final boolean draw) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.M2, null, draw);
    }
    
    @Override
    public void setEraserRawDrawingEnabled(final boolean draw, final int eraserStyle) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.N2, null, draw);
    }
    
    @Override
    public float[] getStrokeParameters(final int strokeStyle) {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(RK32XXDevice.O2, null, strokeStyle)) != null && invokeMethodSafely instanceof float[]) {
            return (float[])invokeMethodSafely;
        }
        return new float[0];
    }
    
    @Override
    public void setStrokeParameters(final int strokeStyle, final float[] params) {
        final Method p2 = RK32XXDevice.P2;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = strokeStyle;
        array[1] = params;
        ReflectUtil.invokeMethodSafely(p2, null, args);
    }
    
    @Override
    protected float[] getEpdToViewMatrixHelper() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(RK32XXDevice.Q2, null, new Object[0])) != null && invokeMethodSafely instanceof float[]) {
            return (float[])invokeMethodSafely;
        }
        return null;
    }
    
    @Override
    public int getCurrentMultiScreenMode(final Context context) {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(RK32XXDevice.E2, this.a(context), new Object[0])) instanceof Integer) {
            return (int)invokeMethodSafely;
        }
        return 0;
    }
    
    @Override
    public boolean isLimitedMultiScreenMode(final Context context) {
        return this.isSystemInMultiWindowMode(context);
    }
    
    @Override
    public boolean isFullFunctionMultiScreenMode(final Context context) {
        return this.getCurrentMultiScreenMode(context) == 2;
    }
    
    @Override
    public void setTextShowPassword(final Context context, final boolean isShow) {
        this.a(context, RK32XXDevice.A2, isShow);
    }
    
    @Override
    public boolean isTextShowPasswordOn() {
        final Object a;
        return (a = this.a(null, RK32XXDevice.B2, new Object[0])) != null && (boolean)a;
    }
    
    @Override
    public boolean isSupportWidecg(final Context context) {
        final Object a;
        return (a = this.a(null, RK32XXDevice.C2, context)) != null && (boolean)a;
    }
    
    @Override
    public boolean isInFastMode() {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(RK32XXDevice.h1, null, new Object[0])) != null && b;
    }
    
    @Override
    public boolean isInAppFastMode() {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(RK32XXDevice.g1, null, new Object[0])) != null && b;
    }
    
    @Override
    public boolean isInSystemFastMode() {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(RK32XXDevice.f1, null, new Object[0])) != null && b;
    }
    
    @Override
    public String getCalibrationFilePath() {
        if (Build.VERSION.SDK_INT >= 28) {
            return "onyxconfig/pointercal";
        }
        return "vendor/pointercal";
    }
    
    @Override
    public boolean applyTransientUpdate(final UpdateMode updateMode) {
        return ReflectUtil.invokeMethodSafely(RK32XXDevice.Y0, null, this.a(updateMode)) != null;
    }
    
    @Override
    public boolean clearTransientUpdate(final boolean clear) {
        return ReflectUtil.invokeMethodSafely(RK32XXDevice.Z0, null, clear) != null;
    }
    
    @Override
    public Map<String, String> loadSystemFamilyPathMap() {
        final Object invokeMethodSafely;
        if (!((invokeMethodSafely = ReflectUtil.invokeMethodSafely(RK32XXDevice.t2, null, new Object[0])) instanceof Map)) {
            return super.loadSystemFamilyPathMap();
        }
        final Map map;
        if (!(map = (Map)invokeMethodSafely).isEmpty()) {
            return map;
        }
        return super.loadSystemFamilyPathMap();
    }
    
    @Override
    public void enableStandbyByPressPowerButton(final Context context, final boolean enable) {
        ReflectUtil.invokeMethodSafely(RK32XXDevice.G0, null, enable);
    }
    
    @Override
    public boolean useCustomRotationChangedBroadcast() {
        return true;
    }
}
