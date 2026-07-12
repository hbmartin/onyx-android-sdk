// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.device;

import android.content.ComponentName;
import androidx.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import android.graphics.Rect;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import com.onyx.android.sdk.api.device.epd.EPDMode;
import java.io.File;
import android.annotation.TargetApi;
import com.onyx.android.sdk.api.device.epd.UpdatePolicy;
import com.onyx.android.sdk.api.device.epd.UpdateMode;
import com.onyx.android.sdk.api.device.epd.UpdateScheme;
import com.onyx.android.sdk.utils.Debug;
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

public class RK33XXDevice extends BaseDevice
{
    private static final String p;
    private static RK33XXDevice q;
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
    private static final int C = 0;
    private static final int D = 1;
    private static final int E = 2;
    private static final String F = "/sys/class/backlight/white/brightness";
    private static final String G = "/sys/class/backlight/warm/brightness";
    private static Method H;
    private static Method I;
    private static Method J;
    private static Method K;
    private static Method L;
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
    static final /* synthetic */ boolean o2;
    
    private RK33XXDevice() {
    }
    
    public static RK33XXDevice createDevice() {
        final RK33XXDevice q;
        if ((q = RK33XXDevice.q) == null) {
            RK33XXDevice.q = new RK33XXDevice();
            final Class<View> clazz = View.class;
            final Class<?> classForName;
            final Class<?> clazz2 = classForName = ReflectUtil.classForName("android.onyx.ViewUpdateHelper");
            final int staticIntFieldSafely = ReflectUtil.getStaticIntFieldSafely(clazz2, "EINK_ONYX_AUTO_MASK");
            final int staticIntFieldSafely2 = ReflectUtil.getStaticIntFieldSafely(clazz2, "EINK_ONYX_GC_MASK");
            final int staticIntFieldSafely3 = ReflectUtil.getStaticIntFieldSafely(clazz2, "EINK_AUTO_MODE_REGIONAL");
            final int staticIntFieldSafely4 = ReflectUtil.getStaticIntFieldSafely(clazz2, "EINK_WAIT_MODE_NOWAIT");
            final int staticIntFieldSafely5 = ReflectUtil.getStaticIntFieldSafely(clazz2, "EINK_WAIT_MODE_WAIT");
            final int staticIntFieldSafely6 = ReflectUtil.getStaticIntFieldSafely(clazz2, "EINK_WAVEFORM_MODE_DU");
            final int staticIntFieldSafely7 = ReflectUtil.getStaticIntFieldSafely(clazz2, "EINK_WAVEFORM_MODE_ANIM");
            final int staticIntFieldSafely8 = ReflectUtil.getStaticIntFieldSafely(clazz2, "EINK_WAVEFORM_MODE_GC4");
            final int staticIntFieldSafely9 = ReflectUtil.getStaticIntFieldSafely(clazz2, "EINK_WAVEFORM_MODE_GC16");
            final int staticIntFieldSafely10 = ReflectUtil.getStaticIntFieldSafely(clazz2, "EINK_WAVEFORM_MODE_REAGL");
            final int staticIntFieldSafely11 = ReflectUtil.getStaticIntFieldSafely(clazz2, "EINK_REAGL_MODE_REAGLD");
            final int staticIntFieldSafely12 = ReflectUtil.getStaticIntFieldSafely(clazz2, "EINK_UPDATE_MODE_PARTIAL");
            final int staticIntFieldSafely13 = ReflectUtil.getStaticIntFieldSafely(clazz2, "EINK_UPDATE_MODE_FULL");
            final int staticIntFieldSafely14 = ReflectUtil.getStaticIntFieldSafely(clazz2, "UI_DEFAULT_MODE");
            final int n = staticIntFieldSafely3;
            final int n2 = staticIntFieldSafely4;
            final int s = staticIntFieldSafely2;
            RK33XXDevice.r = staticIntFieldSafely;
            RK33XXDevice.s = s;
            final int n4;
            final int n3 = n4 = (n | n2);
            final int n5 = staticIntFieldSafely11;
            final int n6 = n4;
            final int n7 = staticIntFieldSafely10;
            final int n8 = n4;
            final int n9 = staticIntFieldSafely8;
            final Class<?> cls = classForName;
            final int n10 = n4;
            final int n11 = staticIntFieldSafely7;
            final int n12 = staticIntFieldSafely3;
            final int n13 = staticIntFieldSafely5;
            final int n14 = n4;
            final int n15 = staticIntFieldSafely9;
            RK33XXDevice.t = (n4 | staticIntFieldSafely6 | staticIntFieldSafely12);
            RK33XXDevice.u = (n14 | n15 | staticIntFieldSafely12);
            RK33XXDevice.v = (n12 | n13 | staticIntFieldSafely9 | staticIntFieldSafely13);
            RK33XXDevice.w = (n10 | n11 | staticIntFieldSafely12);
            RK33XXDevice.x = ReflectUtil.getStaticIntFieldSafely(cls, "UI_A2_QUALITY_MODE");
            RK33XXDevice.y = (n8 | n9 | staticIntFieldSafely12);
            RK33XXDevice.z = (n6 | n7 | staticIntFieldSafely12);
            RK33XXDevice.A = (n3 | n5 | staticIntFieldSafely10 | staticIntFieldSafely12);
            RK33XXDevice.B = staticIntFieldSafely14;
            final Class<?> classForName2;
            final Class<?> clazz3 = classForName2 = ReflectUtil.classForName("android.onyx.hardware.DeviceController");
            final Class<View> clazz4 = clazz;
            final Class<?> clazz5 = classForName2;
            final Class<View> cls2 = clazz;
            final Class<?> clazz6 = classForName2;
            RK33XXDevice.T0 = ReflectUtil.getMethodSafely(clazz6, "openFrontLight", Context.class);
            RK33XXDevice.U0 = ReflectUtil.getMethodSafely(clazz6, "closeFrontLight", Context.class);
            RK33XXDevice.V0 = ReflectUtil.getMethodSafely(clazz6, "getFrontLightValue", Context.class);
            final Class[] parameterTypes;
            final Class[] array = parameterTypes = new Class[2];
            array[0] = Context.class;
            final Class<Integer> type = Integer.TYPE;
            array[1] = type;
            RK33XXDevice.W0 = ReflectUtil.getMethodSafely(clazz6, "setFrontLightValue", (Class<?>[])parameterTypes);
            RK33XXDevice.X0 = ReflectUtil.getMethodSafely(clazz6, "getFrontLightConfigValue", Context.class);
            final Class[] parameterTypes2;
            final Class[] array2 = parameterTypes2 = new Class[2];
            array2[0] = Context.class;
            array2[1] = type;
            RK33XXDevice.Y0 = ReflectUtil.getMethodSafely(clazz6, "setFrontLightConfigValue", (Class<?>[])parameterTypes2);
            final Class[] parameterTypes3 = { null };
            final Class<Boolean> type2 = Boolean.TYPE;
            parameterTypes3[0] = type2;
            RK33XXDevice.i1 = ReflectUtil.getMethodSafely(clazz6, "useBigPen", (Class<?>[])parameterTypes3);
            RK33XXDevice.j1 = ReflectUtil.getMethodSafely(clazz6, "stopTpd", (Class<?>[])new Class[0]);
            RK33XXDevice.k1 = ReflectUtil.getMethodSafely(clazz6, "startTpd", (Class<?>[])new Class[0]);
            final Class[] parameterTypes4;
            final Class[] array3 = parameterTypes4 = new Class[2];
            array3[0] = Context.class;
            array3[1] = Long.TYPE;
            RK33XXDevice.h1 = ReflectUtil.getMethodSafely(clazz6, "gotoSleep", (Class<?>[])parameterTypes4);
            RK33XXDevice.D1 = ReflectUtil.getMethodSafely(clazz6, "isTouchable", Context.class);
            RK33XXDevice.E1 = ReflectUtil.getMethodSafely(clazz6, "getTouchType", Context.class);
            RK33XXDevice.F1 = ReflectUtil.getMethodSafely(clazz6, "hasWifi", Context.class);
            RK33XXDevice.G1 = ReflectUtil.getMethodSafely(clazz6, "hasAudio", Context.class);
            RK33XXDevice.H1 = ReflectUtil.getMethodSafely(clazz6, "hasTTS", (Class<?>[])new Class[0]);
            RK33XXDevice.I1 = ReflectUtil.getMethodSafely(clazz6, "hasFLBrightness", Context.class);
            RK33XXDevice.J1 = ReflectUtil.getMethodSafely(clazz6, "hasBluetooth", Context.class);
            RK33XXDevice.K1 = ReflectUtil.getMethodSafely(clazz6, "hasCTMBrightness", Context.class);
            RK33XXDevice.L1 = ReflectUtil.getMethodSafely(clazz6, "supportExternalSD", Context.class);
            final Class[] parameterTypes5;
            final Class[] array4 = parameterTypes5 = new Class[2];
            array4[0] = String.class;
            array4[1] = type;
            RK33XXDevice.Y1 = ReflectUtil.getDeclaredMethodSafely(clazz6, "writeValueToFile", (Class<?>[])parameterTypes5);
            RK33XXDevice.l1 = ReflectUtil.getMethodSafely(cls2, "enableOnyxTpd", type);
            RK33XXDevice.Z0 = ReflectUtil.getMethodSafely(clazz5, "led", type2);
            final Class[] parameterTypes6;
            final Class[] array5 = parameterTypes6 = new Class[2];
            array5[0] = String.class;
            array5[1] = type;
            RK33XXDevice.a1 = ReflectUtil.getMethodSafely(clazz5, "setLedColor", (Class<?>[])parameterTypes6);
            RK33XXDevice.z0 = ReflectUtil.getMethodSafely(clazz4, "postInvalidate", type);
            RK33XXDevice.H = ReflectUtil.getMethodSafely(clazz4, "refreshScreen", type);
            final Class[] parameterTypes7;
            final Class[] array6 = parameterTypes7 = new Class[5];
            array6[0] = type;
            array6[2] = (array6[1] = type);
            array6[4] = (array6[3] = type);
            RK33XXDevice.I = ReflectUtil.getMethodSafely(clazz4, "refreshScreen", (Class<?>[])parameterTypes7);
            final Class[] parameterTypes8;
            final Class[] array7 = parameterTypes8 = new Class[2];
            array7[0] = type;
            array7[1] = String.class;
            RK33XXDevice.J = ReflectUtil.getMethodSafely(clazz4, "screenshot", (Class<?>[])parameterTypes8);
            RK33XXDevice.u0 = ReflectUtil.getMethodSafely(clazz4, "byPass", type);
            RK33XXDevice.O = ReflectUtil.getMethodSafely(clazz4, "setStrokeColor", type);
            RK33XXDevice.P = ReflectUtil.getMethodSafely(clazz4, "setStrokeStyle", type);
            final Class[] parameterTypes9 = { null };
            final Class<Float> type3 = Float.TYPE;
            parameterTypes9[0] = type3;
            RK33XXDevice.Q = ReflectUtil.getMethodSafely(clazz4, "setLineWidth", (Class<?>[])parameterTypes9);
            final Class[] parameterTypes10;
            final Class[] array8 = parameterTypes10 = new Class[4];
            array8[0] = type2;
            array8[1] = Paint.Style.class;
            array8[2] = Paint.Join.class;
            array8[3] = Paint.Cap.class;
            RK33XXDevice.R = ReflectUtil.getMethodSafely(clazz4, "setPainterStyle", (Class<?>[])parameterTypes10);
            RK33XXDevice.K = ReflectUtil.getMethodSafely(clazz4, "supportRegal", (Class<?>[])new Class[0]);
            RK33XXDevice.L = ReflectUtil.getMethodSafely(clazz4, "enableRegal", type2);
            final Class[] parameterTypes11;
            final Class[] array9 = parameterTypes11 = new Class[3];
            array9[0] = type3;
            array9[2] = (array9[1] = type3);
            RK33XXDevice.M = ReflectUtil.getMethodSafely(clazz4, "moveTo", (Class<?>[])parameterTypes11);
            final Class[] parameterTypes12;
            final Class[] array10 = parameterTypes12 = new Class[3];
            array10[1] = (array10[0] = type3);
            array10[2] = type;
            RK33XXDevice.S = ReflectUtil.getMethodSafely(clazz4, "lineTo", (Class<?>[])parameterTypes12);
            final Class[] parameterTypes13;
            final Class[] array11 = parameterTypes13 = new Class[3];
            array11[1] = (array11[0] = type3);
            array11[2] = type;
            RK33XXDevice.U = ReflectUtil.getMethodSafely(clazz4, "quadTo", (Class<?>[])parameterTypes13);
            final Class[] parameterTypes14;
            final Class[] array12 = parameterTypes14 = new Class[4];
            array12[0] = View.class;
            array12[1] = type3;
            array12[3] = (array12[2] = type3);
            RK33XXDevice.N = ReflectUtil.getMethodSafely(clazz4, "moveTo", (Class<?>[])parameterTypes14);
            final Class[] parameterTypes15;
            final Class[] array13 = parameterTypes15 = new Class[4];
            array13[0] = View.class;
            array13[2] = (array13[1] = type3);
            array13[3] = type;
            RK33XXDevice.T = ReflectUtil.getMethodSafely(clazz4, "lineTo", (Class<?>[])parameterTypes15);
            final Class[] parameterTypes16;
            final Class[] array14 = parameterTypes16 = new Class[4];
            array14[0] = View.class;
            array14[2] = (array14[1] = type3);
            array14[3] = type;
            RK33XXDevice.V = ReflectUtil.getMethodSafely(clazz4, "quadTo", (Class<?>[])parameterTypes16);
            RK33XXDevice.W = ReflectUtil.getMethodSafely(clazz4, "getTouchWidth", (Class<?>[])new Class[0]);
            RK33XXDevice.X = ReflectUtil.getMethodSafely(clazz4, "getTouchHeight", (Class<?>[])new Class[0]);
            RK33XXDevice.Y = ReflectUtil.getMethodSafely(clazz4, "getMaxTouchPressure", (Class<?>[])new Class[0]);
            RK33XXDevice.Z = ReflectUtil.getMethodSafely(clazz4, "getEpdWidth", (Class<?>[])new Class[0]);
            RK33XXDevice.a0 = ReflectUtil.getMethodSafely(clazz4, "getEpdHeight", (Class<?>[])new Class[0]);
            final Class[] parameterTypes17;
            final Class[] array15 = parameterTypes17 = new Class[3];
            array15[0] = View.class;
            array15[2] = (array15[1] = float[].class);
            RK33XXDevice.b0 = ReflectUtil.getMethodSafely(clazz4, "mapToView", (Class<?>[])parameterTypes17);
            final Class[] parameterTypes18;
            final Class[] array16 = parameterTypes18 = new Class[3];
            array16[0] = View.class;
            array16[2] = (array16[1] = float[].class);
            RK33XXDevice.c0 = ReflectUtil.getMethodSafely(clazz4, "mapToEpd", (Class<?>[])parameterTypes18);
            final Class[] parameterTypes19;
            final Class[] array17 = parameterTypes19 = new Class[3];
            array17[0] = View.class;
            array17[2] = (array17[1] = float[].class);
            RK33XXDevice.d0 = ReflectUtil.getMethodSafely(clazz4, "mapFromRawTouchPoint", (Class<?>[])parameterTypes19);
            final Class[] parameterTypes20;
            final Class[] array18 = parameterTypes20 = new Class[3];
            array18[0] = View.class;
            array18[2] = (array18[1] = float[].class);
            RK33XXDevice.e0 = ReflectUtil.getMethodSafely(clazz4, "mapToRawTouchPoint", (Class<?>[])parameterTypes20);
            RK33XXDevice.f0 = ReflectUtil.getMethodSafely(clazz4, "enablePost", type);
            RK33XXDevice.g0 = ReflectUtil.getMethodSafely(clazz4, "resetEpdPost", (Class<?>[])new Class[0]);
            RK33XXDevice.h0 = ReflectUtil.getMethodSafely(clazz4, "isValidPenState", (Class<?>[])new Class[0]);
            RK33XXDevice.i0 = ReflectUtil.getMethodSafely(clazz4, "getPenState", (Class<?>[])new Class[0]);
            RK33XXDevice.j0 = ReflectUtil.getMethodSafely(clazz4, "setScreenHandWritingPenState", type);
            final Class[] parameterTypes21;
            final Class[] array19 = parameterTypes21 = new Class[2];
            array19[0] = View.class;
            array19[1] = int[].class;
            RK33XXDevice.l0 = ReflectUtil.getMethodSafely(clazz4, "setScreenHandWritingRegionLimit", (Class<?>[])parameterTypes21);
            RK33XXDevice.k0 = ReflectUtil.getMethodSafely(clazz4, "setScreenHandWritingRegionMode", type);
            final Class[] parameterTypes22;
            final Class[] array20 = parameterTypes22 = new Class[2];
            array20[0] = View.class;
            array20[1] = int[].class;
            RK33XXDevice.m0 = ReflectUtil.getMethodSafely(clazz4, "setScreenHandWritingRegionExclude", (Class<?>[])parameterTypes22);
            final Class[] parameterTypes23;
            final Class[] array21 = parameterTypes23 = new Class[2];
            array21[0] = type2;
            array21[1] = type;
            RK33XXDevice.n0 = ReflectUtil.getMethodSafely(clazz4, "applyGammaCorrection", (Class<?>[])parameterTypes23);
            RK33XXDevice.o0 = ReflectUtil.getMethodSafely(clazz4, "applyColorFilter", type);
            final Class[] parameterTypes24;
            final Class[] array22 = parameterTypes24 = new Class[6];
            array22[1] = (array22[0] = type3);
            array22[3] = (array22[2] = type3);
            array22[5] = (array22[4] = type3);
            RK33XXDevice.p0 = ReflectUtil.getMethodSafely(clazz4, "startStroke", (Class<?>[])parameterTypes24);
            final Class[] parameterTypes25;
            final Class[] array23 = parameterTypes25 = new Class[6];
            array23[1] = (array23[0] = type3);
            array23[3] = (array23[2] = type3);
            array23[5] = (array23[4] = type3);
            RK33XXDevice.q0 = ReflectUtil.getMethodSafely(clazz4, "addStrokePoint", (Class<?>[])parameterTypes25);
            final Class[] parameterTypes26;
            final Class[] array24 = parameterTypes26 = new Class[6];
            array24[1] = (array24[0] = type3);
            array24[3] = (array24[2] = type3);
            array24[5] = (array24[4] = type3);
            RK33XXDevice.r0 = ReflectUtil.getMethodSafely(clazz4, "finishStroke", (Class<?>[])parameterTypes26);
            RK33XXDevice.A0 = ReflectUtil.getMethodSafely(clazz4, "invalidate", type);
            final Class[] parameterTypes27;
            final Class[] array25 = parameterTypes27 = new Class[5];
            array25[0] = type;
            array25[2] = (array25[1] = type);
            array25[4] = (array25[3] = type);
            RK33XXDevice.B0 = ReflectUtil.getMethodSafely(clazz4, "invalidate", (Class<?>[])parameterTypes27);
            RK33XXDevice.E0 = ReflectUtil.getMethodSafely(clazz4, "setDefaultUpdateMode", type);
            RK33XXDevice.C0 = ReflectUtil.getMethodSafely(clazz4, "getDefaultUpdateMode", (Class<?>[])new Class[0]);
            RK33XXDevice.D0 = ReflectUtil.getMethodSafely(clazz4, "resetUpdateMode", (Class<?>[])new Class[0]);
            RK33XXDevice.F0 = ReflectUtil.getMethodSafely(clazz4, "getGlobalUpdateMode", (Class<?>[])new Class[0]);
            RK33XXDevice.G0 = ReflectUtil.getMethodSafely(clazz4, "setGlobalUpdateMode", type);
            RK33XXDevice.H0 = ReflectUtil.getMethodSafely(clazz4, "setFirstDrawUpdateMode", type);
            final Class[] parameterTypes28;
            final Class[] array26 = parameterTypes28 = new Class[3];
            array26[0] = type;
            array26[2] = (array26[1] = type);
            RK33XXDevice.I0 = ReflectUtil.getMethodSafely(clazz4, "setWaveformAndScheme", (Class<?>[])parameterTypes28);
            RK33XXDevice.M0 = ReflectUtil.getMethodSafely(clazz4, "resetWaveformAndScheme", (Class<?>[])new Class[0]);
            final Class[] parameterTypes29;
            final Class[] array27 = parameterTypes29 = new Class[3];
            array27[0] = String.class;
            array27[2] = (array27[1] = type2);
            RK33XXDevice.J0 = ReflectUtil.getMethodSafely(clazz4, "applyApplicationFastMode", (Class<?>[])parameterTypes29);
            final Class[] parameterTypes30;
            final Class[] array28 = parameterTypes30 = new Class[5];
            array28[0] = String.class;
            array28[2] = (array28[1] = type2);
            array28[4] = (array28[3] = type);
            RK33XXDevice.K0 = ReflectUtil.getMethodSafely(clazz4, "applyApplicationFastMode", (Class<?>[])parameterTypes30);
            RK33XXDevice.L0 = ReflectUtil.getMethodSafely(clazz4, "clearApplicationFastMode", (Class<?>[])new Class[0]);
            RK33XXDevice.N0 = ReflectUtil.getMethodSafely(clazz4, "enableScreenUpdate", type2);
            RK33XXDevice.O0 = ReflectUtil.getMethodSafely(clazz4, "setDisplayScheme", type);
            RK33XXDevice.P0 = ReflectUtil.getMethodSafely(clazz4, "waitForUpdateFinished", (Class<?>[])new Class[0]);
            RK33XXDevice.s0 = ReflectUtil.getMethodSafely(clazz4, "setUpdListSize", type);
            RK33XXDevice.t0 = ReflectUtil.getMethodSafely(clazz4, "inSystemFastMode", (Class<?>[])new Class[0]);
            final Class[] parameterTypes31;
            final Class[] array29 = parameterTypes31 = new Class[3];
            array29[0] = type;
            array29[2] = (array29[1] = type);
            RK33XXDevice.m1 = ReflectUtil.getMethodSafely(clazz4, "setQRShowConfig", (Class<?>[])parameterTypes31);
            final Class[] parameterTypes32;
            final Class[] array30 = parameterTypes32 = new Class[3];
            array30[0] = type;
            array30[2] = (array30[1] = type);
            RK33XXDevice.n1 = ReflectUtil.getMethodSafely(clazz4, "setInfoShowConfig", (Class<?>[])parameterTypes32);
            RK33XXDevice.Q0 = ReflectUtil.getMethodSafely(clazz4, "repaintEverything", type);
            RK33XXDevice.R0 = ReflectUtil.getMethodSafely(clazz4, "switchToA2Mode", (Class<?>[])new Class[0]);
            RK33XXDevice.S0 = ReflectUtil.getMethodSafely(clazz4, "applySFDebug", type2);
            final Class[] parameterTypes33;
            final Class[] array31 = parameterTypes33 = new Class[3];
            array31[0] = Context.class;
            array31[1] = type;
            array31[2] = String.class;
            RK33XXDevice.b1 = ReflectUtil.getMethodSafely(clazz3, "setVCom", (Class<?>[])parameterTypes33);
            RK33XXDevice.c1 = ReflectUtil.getMethodSafely(clazz3, "getVCom", String.class);
            final Class[] parameterTypes34;
            final Class[] array32 = parameterTypes34 = new Class[2];
            array32[1] = (array32[0] = String.class);
            RK33XXDevice.d1 = ReflectUtil.getMethodSafely(clazz3, "updateWaveform", (Class<?>[])parameterTypes34);
            RK33XXDevice.e1 = ReflectUtil.getMethodSafely(clazz3, "readSystemConfig", String.class);
            final Class[] parameterTypes35;
            final Class[] array33 = parameterTypes35 = new Class[2];
            array33[1] = (array33[0] = String.class);
            RK33XXDevice.f1 = ReflectUtil.getMethodSafely(clazz3, "saveSystemConfig", (Class<?>[])parameterTypes35);
            final Class[] parameterTypes36;
            final Class[] array34 = parameterTypes36 = new Class[2];
            array34[1] = (array34[0] = String.class);
            RK33XXDevice.g1 = ReflectUtil.getMethodSafely(clazz3, "updateMetadataDB", (Class<?>[])parameterTypes36);
            RK33XXDevice.o1 = ReflectUtil.getMethodSafely(clazz3, "powerCTP", type2);
            RK33XXDevice.p1 = ReflectUtil.getMethodSafely(clazz3, "powerEMTP", type2);
            RK33XXDevice.q1 = ReflectUtil.getMethodSafely(clazz3, "isCTPPowerOn", (Class<?>[])new Class[0]);
            RK33XXDevice.r1 = ReflectUtil.getMethodSafely(clazz3, "isEMTPPowerOn", (Class<?>[])new Class[0]);
            RK33XXDevice.s1 = ReflectUtil.getMethodSafely(clazz3, "setPwm1Output", type);
            RK33XXDevice.i2 = ReflectUtil.getMethodSafely(clazz3, "getCTMBrightnessValues", Context.class);
            final Class[] parameterTypes37;
            final Class[] array35 = parameterTypes37 = new Class[2];
            array35[0] = Context.class;
            array35[1] = type;
            RK33XXDevice.g2 = ReflectUtil.getMethodSafely(clazz3, "setWarmLightDeviceValue", (Class<?>[])parameterTypes37);
            final Class[] parameterTypes38;
            final Class[] array36 = parameterTypes38 = new Class[2];
            array36[0] = Context.class;
            array36[1] = type;
            RK33XXDevice.f2 = ReflectUtil.getMethodSafely(clazz3, "setColdLightDeviceValue", (Class<?>[])parameterTypes38);
            final Class[] parameterTypes39;
            final Class[] array37 = parameterTypes39 = new Class[2];
            array37[0] = Context.class;
            array37[1] = type;
            RK33XXDevice.h2 = ReflectUtil.getMethodSafely(clazz3, "getBrightnessConfig", (Class<?>[])parameterTypes39);
            final Class[] parameterTypes40;
            final Class[] array38 = parameterTypes40 = new Class[2];
            array38[0] = Context.class;
            array38[1] = type;
            RK33XXDevice.j2 = ReflectUtil.getMethodSafely(clazz3, "increaseBrightness", (Class<?>[])parameterTypes40);
            final Class[] parameterTypes41;
            final Class[] array39 = parameterTypes41 = new Class[2];
            array39[0] = Context.class;
            array39[1] = type;
            RK33XXDevice.k2 = ReflectUtil.getMethodSafely(clazz3, "decreaseBrightness", (Class<?>[])parameterTypes41);
            RK33XXDevice.l2 = ReflectUtil.getMethodSafely(clazz3, "isBrightnessOn", Context.class);
            RK33XXDevice.l2 = ReflectUtil.getMethodSafely(ReflectUtil.classForName("android.onyx.brightness.BrightnessController"), "isBrightnessOn", Context.class);
            if (Build.VERSION.SDK_INT >= 16) {
                final Class[] parameterTypes42;
                final Class[] array40 = parameterTypes42 = new Class[2];
                array40[1] = (array40[0] = int[].class);
                RK33XXDevice.t1 = ReflectUtil.getMethodSafely(InputManager.class, "setAppCTPDisableRegion", (Class<?>[])parameterTypes42);
                RK33XXDevice.v1 = ReflectUtil.getMethodSafely(InputManager.class, "isCTPDisableRegion", (Class<?>[])new Class[0]);
                RK33XXDevice.u1 = ReflectUtil.getMethodSafely(InputManager.class, "appResetCTPDisableRegion", (Class<?>[])new Class[0]);
                RK33XXDevice.b2 = ReflectUtil.getMethodSafely(InputManager.class, "isEMTPDisabled", (Class<?>[])new Class[0]);
                RK33XXDevice.c2 = ReflectUtil.getMethodSafely(InputManager.class, "isKeyboardDisabled", (Class<?>[])new Class[0]);
                RK33XXDevice.d2 = ReflectUtil.getMethodSafely(InputManager.class, "setEMTPDisabled", type2);
                RK33XXDevice.e2 = ReflectUtil.getMethodSafely(InputManager.class, "setKeyboardDisabled", type2);
            }
            final Class<?> clazz7 = classForName2;
            final Class<View> cls3 = clazz;
            RK33XXDevice.v0 = ReflectUtil.getMethodSafely(cls3, "enableA2", (Class<?>[])new Class[0]);
            RK33XXDevice.w0 = ReflectUtil.getMethodSafely(cls3, "disableA2", (Class<?>[])new Class[0]);
            RK33XXDevice.y1 = ReflectUtil.getMethodSafely(cls3, "switchToA2Mode", (Class<?>[])new Class[0]);
            RK33XXDevice.x0 = ReflectUtil.getMethodSafely(Environment.class, "getStorageRootDirectory", (Class<?>[])new Class[0]);
            RK33XXDevice.y0 = ReflectUtil.getMethodSafely(Environment.class, "getRemovableSDCardDirectory", (Class<?>[])new Class[0]);
            RK33XXDevice.w1 = ReflectUtil.getMethodSafely(WebView.class, "setCssInjectEnabled", type2);
            RK33XXDevice.x1 = ReflectUtil.getMethodSafely(cls3, "applyGCOnce", (Class<?>[])new Class[0]);
            RK33XXDevice.M1 = ReflectUtil.getMethodSafely(cls3, "setTrigger", type);
            final Class<?> classForName3 = ReflectUtil.classForName("android.onyx.optimization.EInkHelper");
            RK33XXDevice.z1 = ReflectUtil.getMethodSafely(classForName3, "removeAppConfig", String.class);
            final Class[] parameterTypes43;
            final Class[] array41 = parameterTypes43 = new Class[2];
            array41[1] = (array41[0] = String.class);
            RK33XXDevice.A1 = ReflectUtil.getMethodSafely(classForName3, "setAppConfig", (Class<?>[])parameterTypes43);
            final Class[] parameterTypes44;
            final Class[] array42 = parameterTypes44 = new Class[2];
            array42[1] = (array42[0] = type2);
            RK33XXDevice.B1 = ReflectUtil.getMethodSafely(classForName3, "setEACServiceConfig", (Class<?>[])parameterTypes44);
            RK33XXDevice.n2 = ReflectUtil.getMethodSafely(classForName3, "getEACAppConfigStringByPkgName", String.class);
            RK33XXDevice.C1 = ReflectUtil.getMethodSafely(ActivityManager.class, "forceStopPackageWithoutPermissionCheck", String.class);
            final Class<?> classForName4 = ReflectUtil.classForName("android.onyx.AndroidSettingsHelper");
            RK33XXDevice.N1 = ReflectUtil.getMethodSafely(classForName4, "getDefaultIpAddresses", Context.class);
            RK33XXDevice.O1 = ReflectUtil.getMethodSafely(classForName4, "isPowerSavedMode", Context.class);
            final Class[] parameterTypes45;
            final Class[] array43 = parameterTypes45 = new Class[2];
            array43[0] = Context.class;
            array43[1] = type2;
            RK33XXDevice.P1 = ReflectUtil.getMethodSafely(classForName4, "enablePowerSavedMode", (Class<?>[])parameterTypes45);
            RK33XXDevice.Z1 = ReflectUtil.getMethodSafely(classForName4, "loadCACertificate", (Class<?>[])new Class[0]);
            RK33XXDevice.a2 = ReflectUtil.getMethodSafely(classForName4, "loadUserCertificate", (Class<?>[])new Class[0]);
            RK33XXDevice.Q1 = ReflectUtil.getMethodSafely(clazz7, "isHallControlEnable", (Class<?>[])new Class[0]);
            RK33XXDevice.R1 = ReflectUtil.getMethodSafely(clazz7, "enableHallControl", type2);
            final Class<?> classForName5 = ReflectUtil.classForName("android.onyx.utils.ApplicationFreezeHelper");
            final Class[] parameterTypes46;
            final Class[] array44 = parameterTypes46 = new Class[2];
            array44[0] = Context.class;
            array44[1] = String.class;
            RK33XXDevice.S1 = ReflectUtil.getMethodSafely(classForName5, "disableAppByPkgName", (Class<?>[])parameterTypes46);
            final Class[] parameterTypes47;
            final Class[] array45 = parameterTypes47 = new Class[2];
            array45[0] = Context.class;
            array45[1] = String.class;
            RK33XXDevice.T1 = ReflectUtil.getMethodSafely(classForName5, "enableAppByPkgName", (Class<?>[])parameterTypes47);
            RK33XXDevice.U1 = ReflectUtil.getMethodSafely(classForName5, "disableGooglePlay", Context.class);
            RK33XXDevice.V1 = ReflectUtil.getMethodSafely(classForName5, "enableGooglePlay", Context.class);
            RK33XXDevice.W1 = ReflectUtil.getMethodSafely(classForName5, "isGoogleAppsEnabled", Context.class);
            RK33XXDevice.X1 = ReflectUtil.getMethodSafely(classForName5, "isGoogleAppsExists", Context.class);
            RK33XXDevice.m2 = ReflectUtil.getMethodSafely(ReflectUtil.classForName("android.onyx.utils.ActivityManagerHelper"), "getCurrentTopComponent", Context.class);
            Debug.d(RK33XXDevice.class, "init device finished.", new Object[0]);
            return RK33XXDevice.q;
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
        if ((n2 = RK33XXDevice$a.c[scheme.ordinal()]) != 1) {
            if (n2 != 2) {
                if (n2 != 3) {
                    if (!RK33XXDevice.o2) {
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
        if (value == RK33XXDevice.t) {
            return UpdateMode.DU;
        }
        if (value == RK33XXDevice.u) {
            return UpdateMode.GU;
        }
        if (value == RK33XXDevice.v) {
            return UpdateMode.GC;
        }
        return UpdateMode.GC;
    }
    
    private static int a(final UpdatePolicy policy) {
        int u = RK33XXDevice.u;
        final int n;
        if ((n = RK33XXDevice$a.d[policy.ordinal()]) != 1) {
            if (n != 2) {
                if (!RK33XXDevice.o2) {
                    throw new AssertionError();
                }
            }
            else {
                u |= RK33XXDevice.s;
            }
        }
        else {
            u |= RK33XXDevice.r;
        }
        return u;
    }
    
    @TargetApi(21)
    private Object b(final Context context) {
        return context.getSystemService("input");
    }
    
    private Object a(final Context context) {
        return context.getSystemService("activity");
    }
    
    static {
        o2 = (RK33XXDevice.class.desiredAssertionStatus() ^ true);
        p = RK33XXDevice.class.getSimpleName();
        RK33XXDevice.q = null;
        RK33XXDevice.r = 0;
        RK33XXDevice.s = 0;
        RK33XXDevice.t = 0;
        RK33XXDevice.u = 0;
        RK33XXDevice.v = 0;
        RK33XXDevice.w = 0;
        RK33XXDevice.x = 0;
        RK33XXDevice.y = 0;
        RK33XXDevice.z = 0;
        RK33XXDevice.A = 0;
        RK33XXDevice.B = 0;
        RK33XXDevice.H = null;
        RK33XXDevice.I = null;
        RK33XXDevice.J = null;
        RK33XXDevice.K = null;
        RK33XXDevice.L = null;
        RK33XXDevice.M = null;
        RK33XXDevice.N = null;
        RK33XXDevice.O = null;
        RK33XXDevice.P = null;
        RK33XXDevice.Q = null;
        RK33XXDevice.R = null;
        RK33XXDevice.S = null;
        RK33XXDevice.T = null;
        RK33XXDevice.U = null;
        RK33XXDevice.V = null;
        RK33XXDevice.W = null;
        RK33XXDevice.X = null;
        RK33XXDevice.Y = null;
        RK33XXDevice.Z = null;
        RK33XXDevice.a0 = null;
        RK33XXDevice.b0 = null;
        RK33XXDevice.c0 = null;
        RK33XXDevice.d0 = null;
        RK33XXDevice.e0 = null;
        RK33XXDevice.f0 = null;
        RK33XXDevice.g0 = null;
        RK33XXDevice.h0 = null;
        RK33XXDevice.i0 = null;
        RK33XXDevice.j0 = null;
        RK33XXDevice.k0 = null;
        RK33XXDevice.l0 = null;
        RK33XXDevice.m0 = null;
        RK33XXDevice.n0 = null;
        RK33XXDevice.o0 = null;
        RK33XXDevice.p0 = null;
        RK33XXDevice.q0 = null;
        RK33XXDevice.r0 = null;
        RK33XXDevice.s0 = null;
        RK33XXDevice.t0 = null;
        RK33XXDevice.u0 = null;
        RK33XXDevice.z0 = null;
        RK33XXDevice.A0 = null;
        RK33XXDevice.B0 = null;
        RK33XXDevice.C0 = null;
        RK33XXDevice.D0 = null;
        RK33XXDevice.E0 = null;
        RK33XXDevice.F0 = null;
        RK33XXDevice.G0 = null;
        RK33XXDevice.H0 = null;
        RK33XXDevice.I0 = null;
        RK33XXDevice.J0 = null;
        RK33XXDevice.K0 = null;
        RK33XXDevice.L0 = null;
        RK33XXDevice.M0 = null;
        RK33XXDevice.N0 = null;
        RK33XXDevice.O0 = null;
        RK33XXDevice.P0 = null;
        RK33XXDevice.Q0 = null;
        RK33XXDevice.R0 = null;
        RK33XXDevice.S0 = null;
    }
    
    @Override
    public File getStorageRootDirectory() {
        return (File)ReflectUtil.invokeMethodSafely(RK33XXDevice.x0, null, new Object[0]);
    }
    
    @Override
    public File getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory();
    }
    
    @Override
    public File getRemovableSDCardDirectory() {
        return (File)ReflectUtil.invokeMethodSafely(RK33XXDevice.y0, null, new Object[0]);
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
            if (!RK33XXDevice.o2 && RK33XXDevice.A0 == null) {
                throw new AssertionError();
            }
            RK33XXDevice.A0.invoke(view, a);
        }
        catch (final InvocationTargetException ex) {}
        catch (final IllegalAccessException ex2) {}
        catch (final IllegalArgumentException ex3) {}
    }
    
    @Override
    public void invalidate(final View view, final int left, final int top, final int right, final int bottom, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            if (!RK33XXDevice.o2 && RK33XXDevice.B0 == null) {
                throw new AssertionError();
            }
            final Method b0 = RK33XXDevice.B0;
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
            b0.invoke(view, args);
        }
        catch (final InvocationTargetException ex) {}
        catch (final IllegalAccessException ex2) {}
        catch (final IllegalArgumentException ex3) {}
    }
    
    @Override
    public void postInvalidate(final View view, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            if (!RK33XXDevice.o2 && RK33XXDevice.z0 == null) {
                throw new AssertionError();
            }
            Log.d(RK33XXDevice.p, "dst mode: " + a);
            RK33XXDevice.z0.invoke(view, a);
        }
        catch (final InvocationTargetException ex) {}
        catch (final IllegalAccessException ex2) {}
        catch (final IllegalArgumentException ex3) {}
    }
    
    @Override
    public void refreshScreen(final View view, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            if (!RK33XXDevice.o2 && RK33XXDevice.H == null) {
                throw new AssertionError();
            }
            RK33XXDevice.H.invoke(view, a);
        }
        catch (final InvocationTargetException ex) {}
        catch (final IllegalAccessException ex2) {}
        catch (final IllegalArgumentException ex3) {}
    }
    
    @Override
    public void byPass(final int count) {
        try {
            final Method u0 = RK33XXDevice.u0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(u0, receiver, count);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void refreshScreenRegion(final View view, final int left, final int top, final int width, final int height, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            if (!RK33XXDevice.o2 && RK33XXDevice.I == null) {
                throw new AssertionError();
            }
            final Method i = RK33XXDevice.I;
            final Object[] array;
            final Object[] args = array = new Object[5];
            final int j = a;
            final Object[] array2 = array;
            final Object[] array3 = array;
            final Object[] array4 = array;
            array[0] = left;
            array4[1] = top;
            array3[2] = width;
            array2[3] = height;
            args[4] = j;
            i.invoke(view, args);
        }
        catch (final Exception ex) {}
    }
    
    @Override
    public void screenshot(final View view, final int rotation, final String path) {
        try {
            ReflectUtil.invokeMethodSafely(RK33XXDevice.J, view, rotation, path);
        }
        catch (final Exception ex) {}
    }
    
    @Override
    public void setStrokeColor(final int color) {
        try {
            final Method o = RK33XXDevice.O;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(o, receiver, color);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void setStrokeStyle(final int style) {
        try {
            final Method p = RK33XXDevice.P;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(p, receiver, style);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void setPainterStyle(final boolean antiAlias, final Paint.Style strokeStyle, final Paint.Join joinStyle, final Paint.Cap capStyle) {
        try {
            final Method r = RK33XXDevice.R;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(r, receiver, antiAlias, strokeStyle, joinStyle, capStyle);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void setStrokeWidth(final float width) {
        try {
            final Method q = RK33XXDevice.Q;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(q, receiver, width);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void moveTo(final float x, final float y, final float width) {
        try {
            final Method m = RK33XXDevice.M;
            final Object receiver = null;
            try {
                final Object[] args = new Object[3];
                final Object[] array;
                (array = args)[0] = x;
                array[1] = y;
                args[2] = width;
                ReflectUtil.invokeMethodSafely(m, receiver, args);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void moveTo(final View view, final float x, final float y, final float width) {
        try {
            final Method n = RK33XXDevice.N;
            final Object receiver = null;
            try {
                final Object[] args = new Object[4];
                final Object[] array2;
                final Object[] array;
                (array = (array2 = args))[0] = view;
                array[1] = x;
                array2[2] = y;
                args[3] = width;
                ReflectUtil.invokeMethodSafely(n, receiver, args);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public boolean supportDFB() {
        return RK33XXDevice.S != null;
    }
    
    @Override
    public boolean supportRegal() {
        final Method k;
        final Boolean b;
        return (k = RK33XXDevice.K) != null && (b = (Boolean)ReflectUtil.invokeMethodSafely(k, null, new Object[0])) != null && b;
    }
    
    @Override
    public void enableRegal(final boolean enable) {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.L, null, enable);
    }
    
    @Override
    public void lineTo(final float x, final float y, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            final Method s = RK33XXDevice.S;
            final Object receiver = null;
            try {
                final Object[] array;
                final Object[] args = array = new Object[3];
                final int i = a;
                final Object[] array2 = array;
                array[0] = x;
                array2[1] = y;
                args[2] = i;
                ReflectUtil.invokeMethodSafely(s, receiver, args);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void lineTo(final View view, final float x, final float y, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            final Method t = RK33XXDevice.T;
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
                ReflectUtil.invokeMethodSafely(t, receiver, args);
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
            final Method u = RK33XXDevice.U;
            final Object receiver = null;
            try {
                final Object[] array;
                final Object[] args = array = new Object[3];
                final int i = a;
                final Object[] array2 = array;
                array[0] = x;
                array2[1] = y;
                args[2] = i;
                ReflectUtil.invokeMethodSafely(u, receiver, args);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void quadTo(final View view, final float x, final float y, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            final Method v = RK33XXDevice.V;
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
                ReflectUtil.invokeMethodSafely(v, receiver, args);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public float getTouchWidth() {
        try {
            final Method w = RK33XXDevice.W;
            final Object receiver = null;
            try {
                return (float)ReflectUtil.invokeMethodSafely(w, receiver, new Object[0]);
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
            final Method a0 = RK33XXDevice.a0;
            final Object receiver = null;
            try {
                return (float)ReflectUtil.invokeMethodSafely(a0, receiver, new Object[0]);
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
            final Method z = RK33XXDevice.Z;
            final Object receiver = null;
            try {
                return (float)ReflectUtil.invokeMethodSafely(z, receiver, new Object[0]);
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
            final Method c0 = RK33XXDevice.c0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(c0, receiver, view, src, dst);
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
            final Method d0 = RK33XXDevice.d0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(d0, receiver, view, src, dst);
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
            final Method e0 = RK33XXDevice.e0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(e0, receiver, view, src, dst);
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
            final Method b0 = RK33XXDevice.b0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(b0, receiver, view, src, dst);
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
            final Method x = RK33XXDevice.X;
            final Object receiver = null;
            try {
                return (float)ReflectUtil.invokeMethodSafely(x, receiver, new Object[0]);
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
            final Method y = RK33XXDevice.Y;
            final Object receiver = null;
            try {
                return (float)ReflectUtil.invokeMethodSafely(y, receiver, new Object[0]);
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
            final Method p0 = RK33XXDevice.p0;
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
                return (float)ReflectUtil.invokeMethodSafely(p0, receiver, args);
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
            final Method q0 = RK33XXDevice.q0;
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
                return (float)ReflectUtil.invokeMethodSafely(q0, receiver, args);
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
            final Method r0 = RK33XXDevice.r0;
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
                return (float)ReflectUtil.invokeMethodSafely(r0, receiver, args);
            }
            catch (final Exception ex) {
                return baseWidth;
            }
        }
        catch (final Exception ex2) {}
        return baseWidth;
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
            ReflectUtil.invokeMethodSafely(RK33XXDevice.f0, view, enable);
        }
        catch (final Exception ex) {}
    }
    
    @Override
    public void enablePost(final int enable) {
        try {
            final Method f0 = RK33XXDevice.f0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(f0, receiver, enable);
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
            final Method g0 = RK33XXDevice.g0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(g0, receiver, new Object[0]);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public boolean isValidPenState() {
        try {
            final Method h0 = RK33XXDevice.h0;
            final Object receiver = null;
            try {
                return (boolean)ReflectUtil.invokeMethodSafely(h0, receiver, new Object[0]);
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
            final Method i0 = RK33XXDevice.i0;
            final Object receiver = null;
            try {
                return (int)ReflectUtil.invokeMethodSafely(i0, receiver, new Object[0]);
            }
            catch (final Exception ex) {
                return 0;
            }
        }
        catch (final Exception ex2) {}
        return 0;
    }
    
    public boolean supportScreenHandWriting() {
        return RK33XXDevice.j0 != null;
    }
    
    @Override
    public void setScreenHandWritingPenState(final View view, final int penState) {
        try {
            ReflectUtil.invokeMethodSafely(RK33XXDevice.j0, view, penState);
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
            ReflectUtil.invokeMethodSafely(RK33XXDevice.l0, view, view, array);
        }
        catch (final Exception ex) {}
    }
    
    @Override
    public void setScreenHandWritingRegionMode(final View view, final int mode) {
        try {
            ReflectUtil.invokeMethodSafely(RK33XXDevice.k0, view, mode);
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
            ReflectUtil.invokeMethodSafely(RK33XXDevice.m0, view, view, array);
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
            RK33XXDevice.N0.invoke(view, enable);
        }
        catch (final Exception ex) {}
        return true;
    }
    
    @Override
    public boolean setDisplayScheme(final int scheme) {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.O0, null, scheme);
        return true;
    }
    
    @Override
    public void waitForUpdateFinished() {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.P0, null, new Object[0]);
    }
    
    @Override
    public void useBigPen(final boolean use) {
        this.a(null, RK33XXDevice.i1, use);
    }
    
    @Override
    public void stopTpd() {
        this.a(null, RK33XXDevice.j1, new Object[0]);
    }
    
    @Override
    public void startTpd() {
        this.a(null, RK33XXDevice.k1, new Object[0]);
    }
    
    @Override
    public void enableTpd(final boolean enable) {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.l1, null, enable ? 1 : 0);
    }
    
    @Override
    public UpdateMode getViewDefaultUpdateMode(final View view) {
        final Integer n;
        if ((n = (Integer)ReflectUtil.invokeMethodSafely(RK33XXDevice.C0, view, new Object[0])) == null) {
            return UpdateMode.GU;
        }
        return this.b(n);
    }
    
    @Override
    public void resetViewUpdateMode(final View view) {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.D0, view, new Object[0]);
    }
    
    @Override
    public boolean setViewDefaultUpdateMode(final View view, final UpdateMode mode) {
        return ReflectUtil.invokeMethodSafely(RK33XXDevice.E0, view, this.a(mode)) != null;
    }
    
    @Override
    public UpdateMode getSystemDefaultUpdateMode() {
        final Integer n;
        if ((n = (Integer)ReflectUtil.invokeMethodSafely(RK33XXDevice.F0, null, new Object[0])) == null) {
            return UpdateMode.GU;
        }
        return this.b(n);
    }
    
    @Override
    public boolean setSystemDefaultUpdateMode(final UpdateMode mode) {
        return ReflectUtil.invokeMethodSafely(RK33XXDevice.G0, null, this.a(mode)) != null;
    }
    
    public boolean setFirstDrawUpdateMode(final UpdateMode mode) {
        return ReflectUtil.invokeMethodSafely(RK33XXDevice.H0, null, this.a(mode)) != null;
    }
    
    @Override
    public boolean applySysScopeUpdate(final UpdateMode mode, final UpdateScheme scheme, final int count) {
        final Method i0 = RK33XXDevice.I0;
        final Object[] array = new Object[3];
        final Object[] array2;
        final Object[] args = array2 = array;
        args[0] = this.a(mode);
        array2[1] = this.a(scheme);
        array[2] = count;
        return ReflectUtil.invokeMethodSafely(i0, null, args) != null;
    }
    
    @Override
    public boolean clearSysScopeUpdate() {
        return ReflectUtil.invokeMethodSafely(RK33XXDevice.M0, null, new Object[0]) != null;
    }
    
    @Override
    public boolean applyAppScopeUpdate(final String application, final boolean enable, final boolean clear) {
        final Method j0 = RK33XXDevice.J0;
        final Object[] array = new Object[3];
        final Object[] array2;
        final Object[] args = array2 = array;
        args[0] = application;
        array2[1] = enable;
        array[2] = clear;
        return ReflectUtil.invokeMethodSafely(j0, null, args) != null;
    }
    
    @Override
    public boolean applyAppScopeUpdate(final String application, final boolean enable, final boolean clear, final UpdateMode repeatMode, final int repeatLimit) {
        final Method k0 = RK33XXDevice.K0;
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
        return ReflectUtil.invokeMethodSafely(k0, null, args) != null;
    }
    
    @Override
    public boolean clearAppScopeUpdate() {
        return ReflectUtil.invokeMethodSafely(RK33XXDevice.L0, null, new Object[0]) != null;
    }
    
    @Override
    public boolean clearAppScopeUpdate(final boolean clear) {
        return this.clearAppScopeUpdate();
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
        return (b = (Boolean)this.a(context, RK33XXDevice.T0, context)) != null && b;
    }
    
    @Override
    public boolean closeFrontLight(final Context context) {
        final Boolean b;
        return (b = (Boolean)this.a(context, RK33XXDevice.U0, context)) != null && b;
    }
    
    @Override
    public int getFrontLightDeviceValue(final Context context) {
        final Integer n;
        if ((n = (Integer)this.a(context, RK33XXDevice.V0, context)) == null) {
            return 0;
        }
        return n;
    }
    
    @Override
    public boolean setFrontLightDeviceValue(final Context context, final int value) {
        final Method w0 = RK33XXDevice.W0;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = value;
        return this.a(context, w0, args) != null;
    }
    
    @Override
    public int getFrontLightConfigValue(final Context context) {
        return ReflectUtil.getSafely((Integer)this.a(context, RK33XXDevice.X0, context));
    }
    
    @Override
    public boolean setFrontLightConfigValue(final Context context, final int value) {
        final Method y0 = RK33XXDevice.Y0;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = value;
        this.a(context, y0, args);
        return true;
    }
    
    @Override
    public List<Integer> getFrontLightValueList(final Context context) {
        return Arrays.asList(this.getColdLightValues(context));
    }
    
    @Override
    public void led(final Context context, final boolean on) {
        this.a(context, RK33XXDevice.Z0, on);
    }
    
    @Override
    public boolean setLedColor(final String color, final int on) {
        final Method a1 = RK33XXDevice.a1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = color;
        array[1] = on;
        this.a(null, a1, args);
        return true;
    }
    
    @Override
    public void setVCom(final Context context, final int value, final String path) {
        final Method b1 = RK33XXDevice.b1;
        final Object[] args;
        final Object[] array = args = new Object[3];
        args[0] = context;
        array[1] = value;
        array[2] = path;
        this.a(context, b1, args);
    }
    
    @Override
    public int getVCom(final Context context, final String path) {
        final Integer n;
        if ((n = (Integer)this.a(context, RK33XXDevice.c1, path)) == null) {
            return 0;
        }
        return n;
    }
    
    @Override
    public void updateWaveform(final Context context, final String path, final String target) {
        final Method d1 = RK33XXDevice.d1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = path;
        array[1] = target;
        this.a(context, d1, args);
    }
    
    @Override
    public String readSystemConfig(final Context context, final String key) {
        final Object a;
        if ((a = this.a(context, RK33XXDevice.e1, key)) != null && !a.equals("")) {
            return a.toString();
        }
        return "";
    }
    
    @Override
    public boolean saveSystemConfig(final Context context, final String key, final String value) {
        final Method f1 = RK33XXDevice.f1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = key;
        array[1] = value;
        return ReflectUtil.getSafely((Boolean)this.a(context, f1, args));
    }
    
    @Override
    public void updateMetadataDB(final Context context, final String path, final String target) {
        final Method g1 = RK33XXDevice.g1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = path;
        array[1] = target;
        this.a(context, g1, args);
    }
    
    UpdateMode a(final EPDMode mode) {
        switch (RK33XXDevice$a.a[mode.ordinal()]) {
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
        switch (RK33XXDevice$a.b[mode.ordinal()]) {
            default: {
                n = RK33XXDevice.B;
                break;
            }
            case 9: {
                if ((n = RK33XXDevice.A) != 0) {
                    break;
                }
                n = RK33XXDevice.u;
                break;
            }
            case 8: {
                if ((n = RK33XXDevice.z) != 0) {
                    break;
                }
                n = RK33XXDevice.u;
                break;
            }
            case 7: {
                n = RK33XXDevice.y;
                break;
            }
            case 6: {
                n = RK33XXDevice.x;
                break;
            }
            case 5: {
                n = RK33XXDevice.w;
                break;
            }
            case 4: {
                n = RK33XXDevice.v;
                break;
            }
            case 3: {
                n = RK33XXDevice.u;
                break;
            }
            case 1:
            case 2: {
                n = RK33XXDevice.t;
                break;
            }
        }
        return n;
    }
    
    @Override
    public void disableA2ForSpecificView(final View view) {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.w0, view, new Object[0]);
    }
    
    @Override
    public void enableA2ForSpecificView(final View view) {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.v0, view, new Object[0]);
    }
    
    @Override
    public void setWebViewContrastOptimize(final WebView view, final boolean enabled) {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.w1, view, enabled);
    }
    
    @Override
    public void gotoSleep(final Context context) {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.h1, context, System.currentTimeMillis());
    }
    
    @Override
    public void setUpdListSize(final int size) {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.s0, null, size);
    }
    
    @Override
    public boolean inSystemFastMode() {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(RK33XXDevice.t0, null, new Object[0])) != null && b;
    }
    
    @Override
    public void setQRShowConfig(final int orientation, final int startX, final int startY) {
        final Method m1 = RK33XXDevice.m1;
        final Object[] array = new Object[3];
        final Object[] array2;
        final Object[] args = array2 = array;
        args[0] = orientation;
        array2[1] = startX;
        array[2] = startY;
        ReflectUtil.invokeMethodSafely(m1, null, args);
    }
    
    @Override
    public void setInfoShowConfig(final int orientation, final int startX, final int startY) {
        final Method n1 = RK33XXDevice.n1;
        final Object[] array = new Object[3];
        final Object[] array2;
        final Object[] args = array2 = array;
        args[0] = orientation;
        array2[1] = startX;
        array[2] = startY;
        ReflectUtil.invokeMethodSafely(n1, null, args);
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
        this.a(null, RK33XXDevice.o1, on);
    }
    
    @Override
    public void powerEMTP(final boolean on) {
        this.a(null, RK33XXDevice.p1, on);
    }
    
    @Override
    public boolean isCTPPowerOn() {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(RK33XXDevice.q1, null, new Object[0])) != null && b;
    }
    
    @Override
    public boolean isEMTPPowerOn() {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(RK33XXDevice.r1, null, new Object[0])) != null && b;
    }
    
    @Override
    public boolean isCTPDisableRegion(final Context context) {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(RK33XXDevice.v1, this.b(context), new Object[0])) != null && b;
    }
    
    @Override
    public void appResetCTPDisableRegion(final Context context) {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.u1, this.b(context), new Object[0]);
    }
    
    @Override
    public void setAppCTPDisableRegion(final Context context, final int[] disableRegionArray, @Nullable final int[] excludeRegionArray) {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.t1, this.b(context), disableRegionArray, excludeRegionArray);
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
    public void removeAppConfig(final String pkgName) {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.z1, null, pkgName);
    }
    
    @Override
    public void setEACServiceConfig(final boolean enable, final boolean debug) {
        final Method b1 = RK33XXDevice.B1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = enable;
        array[1] = debug;
        ReflectUtil.invokeMethodSafely(b1, null, args);
    }
    
    @Override
    public void setEACAppConfig(final String pkgName, final String jsonString) {
        final Method a1 = RK33XXDevice.A1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = pkgName;
        array[1] = jsonString;
        ReflectUtil.invokeMethodSafely(a1, null, args);
    }
    
    @Override
    public void switchToA2Mode() {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.y1, null, new Object[0]);
    }
    
    @Override
    public void applyGammaCorrection(final boolean apply, final int value) {
        final Method n0 = RK33XXDevice.n0;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = apply;
        array[1] = value;
        ReflectUtil.invokeMethodSafely(n0, null, args);
    }
    
    @Override
    public void applyColorFilter(final int value) {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.o0, null, value);
    }
    
    @Override
    public void applyGCOnce() {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.x1, null, new Object[0]);
    }
    
    @Override
    public String getCTPInfo() {
        return com.onyx.android.sdk.device.a.a(new File("/sys/onyx_misc/captp_fwver"));
    }
    
    @Override
    public boolean hasWifi(final Context context) {
        return ReflectUtil.getSafely((Boolean)this.a(context, RK33XXDevice.F1, context));
    }
    
    @Override
    public boolean hasAudio(final Context context) {
        return ReflectUtil.getSafely((Boolean)this.a(context, RK33XXDevice.G1, context));
    }
    
    @Override
    public boolean hasFLBrightness(final Context context) {
        return ReflectUtil.getSafely((Boolean)this.a(context, RK33XXDevice.I1, context));
    }
    
    @Override
    public boolean hasBluetooth(final Context context) {
        return ReflectUtil.getSafely((Boolean)this.a(context, RK33XXDevice.J1, context));
    }
    
    @Override
    public boolean hasCTMBrightness(final Context context) {
        return ReflectUtil.getSafely((Boolean)this.a(context, RK33XXDevice.K1, context));
    }
    
    @Override
    public boolean supportExternalSD(final Context context) {
        return ReflectUtil.getSafely((Boolean)this.a(context, RK33XXDevice.L1, context));
    }
    
    @Override
    public void setTrigger(final int count) {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.M1, null, count);
    }
    
    @Override
    public void forceStopApplication(final Context context, final String pkgName) {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.C1, this.a(context), pkgName);
    }
    
    @Nullable
    @Override
    public String getIPAddress(final Context context) {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(RK33XXDevice.N1, null, context)) instanceof String) {
            return (String)invokeMethodSafely;
        }
        return null;
    }
    
    @Override
    public boolean isPowerSavedMode(final Context context) {
        return ReflectUtil.getSafely((Boolean)ReflectUtil.invokeMethodSafely(RK33XXDevice.O1, null, context));
    }
    
    @Override
    public void enablePowerSavedMode(final Context context, final boolean enable) {
        final Method p1 = RK33XXDevice.P1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = enable;
        ReflectUtil.invokeMethodSafely(p1, null, args);
    }
    
    @Override
    public boolean isHallControlEnable(final Context context) {
        return (boolean)this.a(context, RK33XXDevice.Q1, new Object[0]);
    }
    
    @Override
    public void enableHallControl(final Context context, final boolean enable) {
        this.a(context, RK33XXDevice.R1, enable);
    }
    
    @Override
    public boolean isGooglePlayEnabled(final Context context) {
        return ReflectUtil.getSafely((Boolean)ReflectUtil.invokeMethodSafely(RK33XXDevice.W1, null, context));
    }
    
    @Override
    public boolean isGoogleAppsExists(final Context context) {
        return ReflectUtil.getSafely((Boolean)ReflectUtil.invokeMethodSafely(RK33XXDevice.X1, null, context));
    }
    
    @Override
    public void freezeApplication(final Context context, final String pkgName, final int userId) {
        final Method s1 = RK33XXDevice.S1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = context;
        array[1] = pkgName;
        ReflectUtil.invokeMethodSafely(s1, null, args);
    }
    
    @Override
    public void unfreezeApplication(final Context context, final String pkgName, final int userId) {
        final Method t1 = RK33XXDevice.T1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = context;
        array[1] = pkgName;
        ReflectUtil.invokeMethodSafely(t1, null, args);
    }
    
    @Override
    public void freezeGooglePlay(final Context context) {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.U1, null, context);
    }
    
    @Override
    public void unfreezeGooglePlay(final Context context) {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.V1, null, context);
    }
    
    @Override
    public void repaintEveryThing(final UpdateMode mode) {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.Q0, null, this.a(mode));
    }
    
    @Override
    public void applySystemFastMode(final boolean enable) {
        if (enable) {
            ReflectUtil.invokeMethodSafely(RK33XXDevice.R0, null, new Object[0]);
        }
        else {
            this.clearSysScopeUpdate();
        }
    }
    
    @Override
    public void setCTMBrightnessValue(final int type, final int value) {
        if (type != 0) {
            if (type == 1) {
                final Method y1 = RK33XXDevice.Y1;
                final Object[] args;
                final Object[] array = args = new Object[2];
                args[0] = "/sys/class/backlight/warm/brightness";
                array[1] = value;
                ReflectUtil.invokeMethodSafely(y1, null, args);
            }
        }
        else {
            final Method y2 = RK33XXDevice.Y1;
            final Object[] args2;
            final Object[] array2 = args2 = new Object[2];
            args2[0] = "/sys/class/backlight/white/brightness";
            array2[1] = value;
            ReflectUtil.invokeMethodSafely(y2, null, args2);
        }
    }
    
    @Override
    public String[] loadCACertificate() {
        return (String[])ReflectUtil.invokeMethodSafely(RK33XXDevice.Z1, null, new Object[0]);
    }
    
    @Override
    public String[] loadUserCertificate() {
        return (String[])ReflectUtil.invokeMethodSafely(RK33XXDevice.a2, null, new Object[0]);
    }
    
    @Override
    public void applySFDebug(final boolean enableDebug) {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.S0, null, enableDebug);
    }
    
    @Override
    public boolean isEMTPDisabled(final Context context) {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(RK33XXDevice.b2, this.b(context), new Object[0])) != null && b;
    }
    
    @Override
    public boolean isKeyboardDisabled(final Context context) {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(RK33XXDevice.c2, this.b(context), new Object[0])) != null && b;
    }
    
    @Override
    public void setEMTPDisabled(final Context context, final boolean disabled) {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.d2, this.b(context), disabled);
    }
    
    @Override
    public void setKeyboardDisabled(final Context context, final boolean disabled) {
        ReflectUtil.invokeMethodSafely(RK33XXDevice.e2, this.b(context), disabled);
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
        if ((a = this.a(context, RK33XXDevice.i2, context)) != null && a instanceof Integer[][]) {
            return (Integer[][])a;
        }
        return null;
    }
    
    @Override
    public int getWarmLightConfigValue(final Context context) {
        final Method h2 = RK33XXDevice.h2;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = context;
        array[1] = 0;
        final Object a;
        if ((a = this.a(context, h2, args)) != null) {
            return (int)a;
        }
        return 0;
    }
    
    @Override
    public int getColdLightConfigValue(final Context context) {
        final Method h2 = RK33XXDevice.h2;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = context;
        array[1] = 1;
        final Object a;
        if ((a = this.a(context, h2, args)) != null) {
            return (int)a;
        }
        return 0;
    }
    
    @Override
    public boolean setWarmLightDeviceValue(final Context context, final int value) {
        final Method g2 = RK33XXDevice.g2;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = value;
        return this.a(context, g2, args) != null;
    }
    
    @Override
    public boolean setColdLightDeviceValue(final Context context, final int value) {
        final Method f2 = RK33XXDevice.f2;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = value;
        return this.a(context, f2, args) != null;
    }
    
    @Override
    public boolean increaseBrightness(final Context context, final int colorTemp) {
        final Method j2 = RK33XXDevice.j2;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = colorTemp;
        return ReflectUtil.getSafely((Boolean)this.a(context, j2, args));
    }
    
    @Override
    public boolean decreaseBrightness(final Context context, final int colorTemp) {
        final Method k2 = RK33XXDevice.k2;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = colorTemp;
        return ReflectUtil.getSafely((Boolean)this.a(context, k2, args));
    }
    
    @Nullable
    @Override
    public ComponentName getCurrentTopComponent(final Context context) {
        return (ComponentName)ReflectUtil.invokeMethodSafely(RK33XXDevice.m2, null, context);
    }
    
    @Nullable
    @Override
    public String getEACAppConfigByPkgName(final String pkgName) {
        final Object invokeMethodSafely;
        if (!((invokeMethodSafely = ReflectUtil.invokeMethodSafely(RK33XXDevice.n2, null, pkgName)) instanceof String)) {
            return "";
        }
        return (String)invokeMethodSafely;
    }
    
    @Override
    public boolean isBrightnessOn(final Context context) {
        final Boolean b;
        return (this.hasCTMBrightness(context) || this.hasFLBrightness(context)) && (b = (Boolean)ReflectUtil.invokeMethodSafely(RK33XXDevice.l2, null, context)) != null && b;
    }
    
    @Override
    public void setPwm1Output(final int value) {
        this.a(null, RK33XXDevice.s1, value);
    }
}
