// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.device;

import android.content.ComponentName;
import androidx.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import android.graphics.Rect;
import com.onyx.android.sdk.api.device.epd.EPDMode;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import com.onyx.android.sdk.api.device.epd.UpdatePolicy;
import com.onyx.android.sdk.api.device.epd.UpdateMode;
import com.onyx.android.sdk.api.device.epd.UpdateScheme;
import android.util.Log;
import android.webkit.WebView;
import android.os.Environment;
import android.graphics.Paint;
import android.content.Context;
import com.onyx.android.sdk.utils.ReflectUtil;
import android.view.View;
import java.lang.reflect.Method;

public class RK31XXDevice extends BaseDevice
{
    private static final String p;
    private static RK31XXDevice q;
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
    private static final int B = 0;
    private static final int C = 1;
    private static final int D = 2;
    private static final String E = "/sys/class/backlight/white/brightness";
    private static final String F = "/sys/class/backlight/warm/brightness";
    private static Method G;
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
    static final /* synthetic */ boolean a2;
    
    private RK31XXDevice() {
    }
    
    public static RK31XXDevice createDevice() {
        final RK31XXDevice q;
        if ((q = RK31XXDevice.q) == null) {
            RK31XXDevice.q = new RK31XXDevice();
            RK31XXDevice.G = ReflectUtil.getMethodSafely(View.class, "getWindowRotation", (Class<?>[])new Class[0]);
            final Class[] parameterTypes;
            final Class[] array = parameterTypes = new Class[3];
            final Class<Integer> type = Integer.TYPE;
            array[0] = type;
            final Class<Boolean> type2 = Boolean.TYPE;
            array[1] = type2;
            array[2] = type;
            RK31XXDevice.H = ReflectUtil.getMethodSafely(View.class, "setWindowRotation", (Class<?>[])parameterTypes);
            final Class<?> classForName;
            final Class<?> cls = classForName = ReflectUtil.classForName("android.onyx.ViewUpdateHelper");
            final int staticIntFieldSafely = ReflectUtil.getStaticIntFieldSafely(cls, "EINK_ONYX_AUTO_MASK");
            final int staticIntFieldSafely2 = ReflectUtil.getStaticIntFieldSafely(cls, "EINK_ONYX_GC_MASK");
            final int staticIntFieldSafely3 = ReflectUtil.getStaticIntFieldSafely(cls, "EINK_AUTO_MODE_REGIONAL");
            final int s = staticIntFieldSafely2;
            final int r = staticIntFieldSafely;
            final Class<?> clazz = classForName;
            final int staticIntFieldSafely4 = ReflectUtil.getStaticIntFieldSafely(clazz, "EINK_WAIT_MODE_NOWAIT");
            final int staticIntFieldSafely5 = ReflectUtil.getStaticIntFieldSafely(clazz, "EINK_WAIT_MODE_WAIT");
            final int staticIntFieldSafely6 = ReflectUtil.getStaticIntFieldSafely(clazz, "EINK_WAVEFORM_MODE_DU");
            final int staticIntFieldSafely7 = ReflectUtil.getStaticIntFieldSafely(clazz, "EINK_WAVEFORM_MODE_ANIM");
            final int staticIntFieldSafely8 = ReflectUtil.getStaticIntFieldSafely(clazz, "EINK_WAVEFORM_MODE_GC4");
            final int staticIntFieldSafely9 = ReflectUtil.getStaticIntFieldSafely(clazz, "EINK_WAVEFORM_MODE_GC16");
            final int staticIntFieldSafely10 = ReflectUtil.getStaticIntFieldSafely(clazz, "EINK_WAVEFORM_MODE_REAGL");
            final int staticIntFieldSafely11 = ReflectUtil.getStaticIntFieldSafely(clazz, "EINK_REAGL_MODE_REAGLD");
            final int staticIntFieldSafely12 = ReflectUtil.getStaticIntFieldSafely(clazz, "EINK_UPDATE_MODE_PARTIAL");
            final int staticIntFieldSafely13 = ReflectUtil.getStaticIntFieldSafely(clazz, "EINK_UPDATE_MODE_FULL");
            RK31XXDevice.r = r;
            RK31XXDevice.s = s;
            final int n2;
            final int n = n2 = (staticIntFieldSafely3 | staticIntFieldSafely4);
            final int n3 = staticIntFieldSafely11;
            final int n4 = n2;
            final int n5 = staticIntFieldSafely10;
            final int n6 = n2;
            final int n7 = staticIntFieldSafely8;
            final Class<?> cls2 = classForName;
            final int n8 = n2;
            final int n9 = staticIntFieldSafely7;
            final int n10 = staticIntFieldSafely3;
            final int n11 = staticIntFieldSafely5;
            final int n12 = n2;
            final int n13 = staticIntFieldSafely9;
            RK31XXDevice.t = (n2 | staticIntFieldSafely6 | staticIntFieldSafely12);
            RK31XXDevice.u = (n12 | n13 | staticIntFieldSafely12);
            RK31XXDevice.v = (n10 | n11 | staticIntFieldSafely9 | staticIntFieldSafely13);
            RK31XXDevice.w = (n8 | n9 | staticIntFieldSafely12);
            RK31XXDevice.x = ReflectUtil.getStaticIntFieldSafely(cls2, "UI_A2_QUALITY_MODE");
            RK31XXDevice.y = (n6 | n7 | staticIntFieldSafely12);
            RK31XXDevice.z = (n4 | n5 | staticIntFieldSafely12);
            RK31XXDevice.A = (n | n3 | staticIntFieldSafely10 | staticIntFieldSafely12);
            final Class<?> classForName2 = ReflectUtil.classForName("android.onyx.hardware.DeviceController");
            RK31XXDevice.P0 = ReflectUtil.getMethodSafely(classForName2, "openFrontLight", Context.class);
            RK31XXDevice.Q0 = ReflectUtil.getMethodSafely(classForName2, "openFrontLight", type);
            RK31XXDevice.R0 = ReflectUtil.getMethodSafely(classForName2, "closeFrontLight", Context.class);
            RK31XXDevice.S0 = ReflectUtil.getMethodSafely(classForName2, "closeFrontLight", type);
            RK31XXDevice.T0 = ReflectUtil.getMethodSafely(classForName2, "getFrontLightValue", Context.class);
            final Class[] parameterTypes2;
            final Class[] array2 = parameterTypes2 = new Class[2];
            array2[0] = Context.class;
            array2[1] = type;
            RK31XXDevice.U0 = ReflectUtil.getMethodSafely(classForName2, "setFrontLightValue", (Class<?>[])parameterTypes2);
            RK31XXDevice.V0 = ReflectUtil.getMethodSafely(classForName2, "getFrontLightConfigValue", Context.class);
            final Class[] parameterTypes3;
            final Class[] array3 = parameterTypes3 = new Class[2];
            array3[0] = Context.class;
            array3[1] = type;
            RK31XXDevice.W0 = ReflectUtil.getMethodSafely(classForName2, "setFrontLightConfigValue", (Class<?>[])parameterTypes3);
            RK31XXDevice.g1 = ReflectUtil.getMethodSafely(classForName2, "useBigPen", type2);
            RK31XXDevice.h1 = ReflectUtil.getMethodSafely(classForName2, "stopTpd", (Class<?>[])new Class[0]);
            RK31XXDevice.i1 = ReflectUtil.getMethodSafely(classForName2, "startTpd", (Class<?>[])new Class[0]);
            final Class[] parameterTypes4;
            final Class[] array4 = parameterTypes4 = new Class[2];
            array4[0] = Context.class;
            array4[1] = Long.TYPE;
            RK31XXDevice.f1 = ReflectUtil.getMethodSafely(classForName2, "gotoSleep", (Class<?>[])parameterTypes4);
            RK31XXDevice.j1 = ReflectUtil.getMethodSafely(View.class, "enableOnyxTpd", type);
            RK31XXDevice.X0 = ReflectUtil.getMethodSafely(classForName2, "led", type2);
            final Class[] parameterTypes5;
            final Class[] array5 = parameterTypes5 = new Class[2];
            array5[0] = String.class;
            array5[1] = type;
            RK31XXDevice.Y0 = ReflectUtil.getMethodSafely(classForName2, "setLedColor", (Class<?>[])parameterTypes5);
            RK31XXDevice.I1 = ReflectUtil.getMethodSafely(classForName2, "isTouchable", Context.class);
            RK31XXDevice.J1 = ReflectUtil.getMethodSafely(classForName2, "getTouchType", Context.class);
            RK31XXDevice.K1 = ReflectUtil.getMethodSafely(classForName2, "hasWifi", Context.class);
            RK31XXDevice.L1 = ReflectUtil.getMethodSafely(classForName2, "hasAudio", Context.class);
            RK31XXDevice.M1 = ReflectUtil.getMethodSafely(classForName2, "hasFLBrightness", Context.class);
            RK31XXDevice.N1 = ReflectUtil.getMethodSafely(classForName2, "hasBluetooth", Context.class);
            RK31XXDevice.O1 = ReflectUtil.getMethodSafely(classForName2, "hasCTMBrightness", Context.class);
            RK31XXDevice.P1 = ReflectUtil.getMethodSafely(classForName2, "supportExternalSD", Context.class);
            final Class[] parameterTypes6;
            final Class[] array6 = parameterTypes6 = new Class[2];
            array6[0] = String.class;
            array6[1] = type;
            RK31XXDevice.Q1 = ReflectUtil.getDeclaredMethodSafely(classForName2, "writeValueToFile", (Class<?>[])parameterTypes6);
            final Class[] parameterTypes7;
            final Class[] array7 = parameterTypes7 = new Class[2];
            array7[1] = (array7[0] = type);
            RK31XXDevice.I = ReflectUtil.getMethodSafely(View.class, "setUpdatePolicy", (Class<?>[])parameterTypes7);
            RK31XXDevice.y0 = ReflectUtil.getMethodSafely(View.class, "postInvalidate", type);
            RK31XXDevice.J = ReflectUtil.getMethodSafely(View.class, "refreshScreen", type);
            final Class[] parameterTypes8;
            final Class[] array8 = parameterTypes8 = new Class[5];
            array8[0] = type;
            array8[2] = (array8[1] = type);
            array8[4] = (array8[3] = type);
            RK31XXDevice.K = ReflectUtil.getMethodSafely(View.class, "refreshScreen", (Class<?>[])parameterTypes8);
            final Class[] parameterTypes9;
            final Class[] array9 = parameterTypes9 = new Class[2];
            array9[0] = type;
            array9[1] = String.class;
            RK31XXDevice.L = ReflectUtil.getMethodSafely(View.class, "screenshot", (Class<?>[])parameterTypes9);
            RK31XXDevice.M = ReflectUtil.getMethodSafely(View.class, "byPass", type);
            RK31XXDevice.R = ReflectUtil.getMethodSafely(View.class, "setStrokeColor", type);
            RK31XXDevice.S = ReflectUtil.getMethodSafely(View.class, "setStrokeStyle", type);
            final Class[] parameterTypes10 = { null };
            final Class<Float> type3 = Float.TYPE;
            parameterTypes10[0] = type3;
            RK31XXDevice.T = ReflectUtil.getMethodSafely(View.class, "setStrokeWidth", (Class<?>[])parameterTypes10);
            final Class[] parameterTypes11;
            final Class[] array10 = parameterTypes11 = new Class[4];
            array10[0] = type2;
            array10[1] = Paint.Style.class;
            array10[2] = Paint.Join.class;
            array10[3] = Paint.Cap.class;
            RK31XXDevice.U = ReflectUtil.getMethodSafely(View.class, "setPainterStyle", (Class<?>[])parameterTypes11);
            RK31XXDevice.N = ReflectUtil.getMethodSafely(View.class, "supportRegal", (Class<?>[])new Class[0]);
            RK31XXDevice.O = ReflectUtil.getMethodSafely(View.class, "enableRegal", type2);
            final Class[] parameterTypes12;
            final Class[] array11 = parameterTypes12 = new Class[3];
            array11[0] = type3;
            array11[2] = (array11[1] = type3);
            RK31XXDevice.P = ReflectUtil.getMethodSafely(View.class, "moveTo", (Class<?>[])parameterTypes12);
            final Class[] parameterTypes13;
            final Class[] array12 = parameterTypes13 = new Class[3];
            array12[1] = (array12[0] = type3);
            array12[2] = type;
            RK31XXDevice.V = ReflectUtil.getMethodSafely(View.class, "lineTo", (Class<?>[])parameterTypes13);
            final Class[] parameterTypes14;
            final Class[] array13 = parameterTypes14 = new Class[3];
            array13[1] = (array13[0] = type3);
            array13[2] = type;
            RK31XXDevice.X = ReflectUtil.getMethodSafely(View.class, "quadTo", (Class<?>[])parameterTypes14);
            final Class[] parameterTypes15;
            final Class[] array14 = parameterTypes15 = new Class[4];
            array14[0] = View.class;
            array14[1] = type3;
            array14[3] = (array14[2] = type3);
            RK31XXDevice.Q = ReflectUtil.getMethodSafely(View.class, "moveTo", (Class<?>[])parameterTypes15);
            final Class[] parameterTypes16;
            final Class[] array15 = parameterTypes16 = new Class[4];
            array15[0] = View.class;
            array15[2] = (array15[1] = type3);
            array15[3] = type;
            RK31XXDevice.W = ReflectUtil.getMethodSafely(View.class, "lineTo", (Class<?>[])parameterTypes16);
            final Class[] parameterTypes17;
            final Class[] array16 = parameterTypes17 = new Class[4];
            array16[0] = View.class;
            array16[2] = (array16[1] = type3);
            array16[3] = type;
            RK31XXDevice.Y = ReflectUtil.getMethodSafely(View.class, "quadTo", (Class<?>[])parameterTypes17);
            RK31XXDevice.Z = ReflectUtil.getMethodSafely(View.class, "getTouchWidth", (Class<?>[])new Class[0]);
            RK31XXDevice.a0 = ReflectUtil.getMethodSafely(View.class, "getTouchHeight", (Class<?>[])new Class[0]);
            RK31XXDevice.b0 = ReflectUtil.getMethodSafely(View.class, "getEpdWidth", (Class<?>[])new Class[0]);
            RK31XXDevice.c0 = ReflectUtil.getMethodSafely(View.class, "getEpdHeight", (Class<?>[])new Class[0]);
            final Class[] parameterTypes18;
            final Class[] array17 = parameterTypes18 = new Class[3];
            array17[0] = View.class;
            array17[2] = (array17[1] = float[].class);
            RK31XXDevice.d0 = ReflectUtil.getMethodSafely(View.class, "mapToView", (Class<?>[])parameterTypes18);
            final Class[] parameterTypes19;
            final Class[] array18 = parameterTypes19 = new Class[3];
            array18[0] = View.class;
            array18[2] = (array18[1] = float[].class);
            RK31XXDevice.e0 = ReflectUtil.getMethodSafely(View.class, "mapToEpd", (Class<?>[])parameterTypes19);
            final Class[] parameterTypes20;
            final Class[] array19 = parameterTypes20 = new Class[3];
            array19[0] = View.class;
            array19[2] = (array19[1] = float[].class);
            RK31XXDevice.f0 = ReflectUtil.getMethodSafely(View.class, "mapFromRawTouchPoint", (Class<?>[])parameterTypes20);
            final Class[] parameterTypes21;
            final Class[] array20 = parameterTypes21 = new Class[3];
            array20[0] = View.class;
            array20[2] = (array20[1] = float[].class);
            RK31XXDevice.g0 = ReflectUtil.getMethodSafely(View.class, "mapToRawTouchPoint", (Class<?>[])parameterTypes21);
            RK31XXDevice.h0 = ReflectUtil.getMethodSafely(View.class, "enablePost", type);
            RK31XXDevice.i0 = ReflectUtil.getMethodSafely(View.class, "resetEpdPost", (Class<?>[])new Class[0]);
            RK31XXDevice.j0 = ReflectUtil.getMethodSafely(View.class, "setScreenHandWritingPenState", type);
            final Class[] parameterTypes22;
            final Class[] array21 = parameterTypes22 = new Class[2];
            array21[0] = View.class;
            array21[1] = int[].class;
            RK31XXDevice.k0 = ReflectUtil.getMethodSafely(View.class, "setScreenHandWritingRegionLimit", (Class<?>[])parameterTypes22);
            final Class[] parameterTypes23;
            final Class[] array22 = parameterTypes23 = new Class[2];
            array22[0] = View.class;
            array22[1] = int[].class;
            RK31XXDevice.l0 = ReflectUtil.getMethodSafely(View.class, "setScreenHandWritingRegionExclude", (Class<?>[])parameterTypes23);
            final Class[] parameterTypes24;
            final Class[] array23 = parameterTypes24 = new Class[2];
            array23[0] = type2;
            array23[1] = type;
            RK31XXDevice.m0 = ReflectUtil.getMethodSafely(View.class, "applyGammaCorrection", (Class<?>[])parameterTypes24);
            RK31XXDevice.n0 = ReflectUtil.getMethodSafely(View.class, "applyColorFilter", type);
            final Class[] parameterTypes25;
            final Class[] array24 = parameterTypes25 = new Class[6];
            array24[1] = (array24[0] = type3);
            array24[3] = (array24[2] = type3);
            array24[5] = (array24[4] = type3);
            RK31XXDevice.o0 = ReflectUtil.getMethodSafely(View.class, "startStroke", (Class<?>[])parameterTypes25);
            final Class[] parameterTypes26;
            final Class[] array25 = parameterTypes26 = new Class[6];
            array25[1] = (array25[0] = type3);
            array25[3] = (array25[2] = type3);
            array25[5] = (array25[4] = type3);
            RK31XXDevice.p0 = ReflectUtil.getMethodSafely(View.class, "addStrokePoint", (Class<?>[])parameterTypes26);
            final Class[] parameterTypes27;
            final Class[] array26 = parameterTypes27 = new Class[6];
            array26[1] = (array26[0] = type3);
            array26[3] = (array26[2] = type3);
            array26[5] = (array26[4] = type3);
            RK31XXDevice.q0 = ReflectUtil.getMethodSafely(View.class, "finishStroke", (Class<?>[])parameterTypes27);
            RK31XXDevice.z0 = ReflectUtil.getMethodSafely(View.class, "invalidate", type);
            final Class[] parameterTypes28;
            final Class[] array27 = parameterTypes28 = new Class[5];
            array27[0] = type;
            array27[2] = (array27[1] = type);
            array27[4] = (array27[3] = type);
            RK31XXDevice.A0 = ReflectUtil.getMethodSafely(View.class, "invalidate", (Class<?>[])parameterTypes28);
            RK31XXDevice.D0 = ReflectUtil.getMethodSafely(View.class, "setDefaultUpdateMode", type);
            RK31XXDevice.B0 = ReflectUtil.getMethodSafely(View.class, "getDefaultUpdateMode", (Class<?>[])new Class[0]);
            RK31XXDevice.C0 = ReflectUtil.getMethodSafely(View.class, "resetUpdateMode", (Class<?>[])new Class[0]);
            RK31XXDevice.E0 = ReflectUtil.getMethodSafely(View.class, "getGlobalUpdateMode", (Class<?>[])new Class[0]);
            RK31XXDevice.F0 = ReflectUtil.getMethodSafely(View.class, "setGlobalUpdateMode", type);
            RK31XXDevice.G0 = ReflectUtil.getMethodSafely(View.class, "setFirstDrawUpdateMode", type);
            final Class[] parameterTypes29;
            final Class[] array28 = parameterTypes29 = new Class[3];
            array28[0] = type;
            array28[2] = (array28[1] = type);
            RK31XXDevice.H0 = ReflectUtil.getMethodSafely(View.class, "setWaveformAndScheme", (Class<?>[])parameterTypes29);
            RK31XXDevice.L0 = ReflectUtil.getMethodSafely(View.class, "resetWaveformAndScheme", (Class<?>[])new Class[0]);
            final Class[] parameterTypes30;
            final Class[] array29 = parameterTypes30 = new Class[3];
            array29[0] = String.class;
            array29[2] = (array29[1] = type2);
            RK31XXDevice.I0 = ReflectUtil.getMethodSafely(View.class, "applyApplicationFastMode", (Class<?>[])parameterTypes30);
            final Class[] parameterTypes31;
            final Class[] array30 = parameterTypes31 = new Class[5];
            array30[0] = String.class;
            array30[2] = (array30[1] = type2);
            array30[4] = (array30[3] = type);
            RK31XXDevice.J0 = ReflectUtil.getMethodSafely(View.class, "applyApplicationFastMode", (Class<?>[])parameterTypes31);
            RK31XXDevice.K0 = ReflectUtil.getMethodSafely(View.class, "clearApplicationFastMode", (Class<?>[])new Class[0]);
            RK31XXDevice.M0 = ReflectUtil.getMethodSafely(View.class, "enableScreenUpdate", type2);
            RK31XXDevice.N0 = ReflectUtil.getMethodSafely(View.class, "setDisplayScheme", type);
            RK31XXDevice.O0 = ReflectUtil.getMethodSafely(View.class, "waitForUpdateFinished", (Class<?>[])new Class[0]);
            RK31XXDevice.r0 = ReflectUtil.getMethodSafely(View.class, "inSystemFastMode", (Class<?>[])new Class[0]);
            RK31XXDevice.s0 = ReflectUtil.getMethodSafely(View.class, "repaintEverything", type);
            RK31XXDevice.t0 = ReflectUtil.getMethodSafely(View.class, "switchToA2Mode", (Class<?>[])new Class[0]);
            final Class[] parameterTypes32;
            final Class[] array31 = parameterTypes32 = new Class[3];
            array31[0] = Context.class;
            array31[1] = type;
            array31[2] = String.class;
            RK31XXDevice.Z0 = ReflectUtil.getMethodSafely(classForName2, "setVCom", (Class<?>[])parameterTypes32);
            RK31XXDevice.a1 = ReflectUtil.getMethodSafely(classForName2, "getVCom", String.class);
            final Class[] parameterTypes33;
            final Class[] array32 = parameterTypes33 = new Class[2];
            array32[1] = (array32[0] = String.class);
            RK31XXDevice.b1 = ReflectUtil.getMethodSafely(classForName2, "updateWaveform", (Class<?>[])parameterTypes33);
            RK31XXDevice.c1 = ReflectUtil.getMethodSafely(classForName2, "readSystemConfig", String.class);
            final Class[] parameterTypes34;
            final Class[] array33 = parameterTypes34 = new Class[2];
            array33[1] = (array33[0] = String.class);
            RK31XXDevice.d1 = ReflectUtil.getMethodSafely(classForName2, "saveSystemConfig", (Class<?>[])parameterTypes34);
            final Class[] parameterTypes35;
            final Class[] array34 = parameterTypes35 = new Class[2];
            array34[1] = (array34[0] = String.class);
            RK31XXDevice.e1 = ReflectUtil.getMethodSafely(classForName2, "updateMetadataDB", (Class<?>[])parameterTypes35);
            RK31XXDevice.k1 = ReflectUtil.getMethodSafely(classForName2, "powerCTP", type2);
            RK31XXDevice.l1 = ReflectUtil.getMethodSafely(classForName2, "powerEMTP", type2);
            RK31XXDevice.m1 = ReflectUtil.getMethodSafely(classForName2, "isCTPPowerOn", (Class<?>[])new Class[0]);
            RK31XXDevice.n1 = ReflectUtil.getMethodSafely(classForName2, "isEMTPPowerOn", (Class<?>[])new Class[0]);
            final Class[] parameterTypes36;
            final Class[] array35 = parameterTypes36 = new Class[2];
            array35[0] = Context.class;
            array35[1] = type;
            RK31XXDevice.S1 = ReflectUtil.getMethodSafely(classForName2, "setWarmLightDeviceValue", (Class<?>[])parameterTypes36);
            final Class[] parameterTypes37;
            final Class[] array36 = parameterTypes37 = new Class[2];
            array36[0] = Context.class;
            array36[1] = type;
            RK31XXDevice.R1 = ReflectUtil.getMethodSafely(classForName2, "setColdLightDeviceValue", (Class<?>[])parameterTypes37);
            final Class[] parameterTypes38;
            final Class[] array37 = parameterTypes38 = new Class[2];
            array37[0] = Context.class;
            array37[1] = type;
            RK31XXDevice.T1 = ReflectUtil.getMethodSafely(classForName2, "getBrightnessConfig", (Class<?>[])parameterTypes38);
            RK31XXDevice.U1 = ReflectUtil.getMethodSafely(classForName2, "getCTMBrightnessValues", Context.class);
            final Class[] parameterTypes39;
            final Class[] array38 = parameterTypes39 = new Class[2];
            array38[0] = Context.class;
            array38[1] = type;
            RK31XXDevice.V1 = ReflectUtil.getMethodSafely(classForName2, "increaseBrightness", (Class<?>[])parameterTypes39);
            final Class[] parameterTypes40;
            final Class[] array39 = parameterTypes40 = new Class[2];
            array39[0] = Context.class;
            array39[1] = type;
            RK31XXDevice.W1 = ReflectUtil.getMethodSafely(classForName2, "decreaseBrightness", (Class<?>[])parameterTypes40);
            RK31XXDevice.X1 = ReflectUtil.getMethodSafely(classForName2, "isLightOn", type);
            RK31XXDevice.Y1 = ReflectUtil.getMethodSafely(classForName2, "isBrightnessOn", Context.class);
            RK31XXDevice.Y1 = ReflectUtil.getMethodSafely(ReflectUtil.classForName("android.onyx.brightness.BrightnessController"), "isBrightnessOn", Context.class);
            RK31XXDevice.u0 = ReflectUtil.getMethodSafely(View.class, "enableA2", (Class<?>[])new Class[0]);
            RK31XXDevice.v0 = ReflectUtil.getMethodSafely(View.class, "disableA2", (Class<?>[])new Class[0]);
            RK31XXDevice.w0 = ReflectUtil.getMethodSafely(Environment.class, "getStorageRootDirectory", (Class<?>[])new Class[0]);
            RK31XXDevice.x0 = ReflectUtil.getMethodSafely(Environment.class, "getRemovableSDCardDirectory", (Class<?>[])new Class[0]);
            RK31XXDevice.o1 = ReflectUtil.getMethodSafely(WebView.class, "setCssInjectEnabled", type2);
            RK31XXDevice.p1 = ReflectUtil.getMethodSafely(View.class, "applyGCOnce", (Class<?>[])new Class[0]);
            RK31XXDevice.q1 = ReflectUtil.getMethodSafely(View.class, "setTrigger", type);
            final Class<?> classForName3 = ReflectUtil.classForName("android.onyx.optimization.EInkHelper");
            RK31XXDevice.r1 = ReflectUtil.getMethodSafely(classForName3, "removeAppConfig", String.class);
            final Class[] parameterTypes41;
            final Class[] array40 = parameterTypes41 = new Class[2];
            array40[1] = (array40[0] = String.class);
            RK31XXDevice.s1 = ReflectUtil.getMethodSafely(classForName3, "setAppConfig", (Class<?>[])parameterTypes41);
            final Class[] parameterTypes42;
            final Class[] array41 = parameterTypes42 = new Class[2];
            array41[1] = (array41[0] = type2);
            RK31XXDevice.t1 = ReflectUtil.getMethodSafely(classForName3, "setEACServiceConfig", (Class<?>[])parameterTypes42);
            RK31XXDevice.u1 = ReflectUtil.getMethodSafely(classForName3, "getEACAppConfigStringByPkgName", String.class);
            final Class<?> classForName4 = ReflectUtil.classForName("android.onyx.AndroidSettingsHelper");
            RK31XXDevice.v1 = ReflectUtil.getMethodSafely(classForName4, "getDefaultIpAddresses", Context.class);
            RK31XXDevice.w1 = ReflectUtil.getMethodSafely(classForName4, "isPowerSavedMode", Context.class);
            final Class[] parameterTypes43;
            final Class[] array42 = parameterTypes43 = new Class[2];
            array42[0] = Context.class;
            array42[1] = type2;
            RK31XXDevice.x1 = ReflectUtil.getMethodSafely(classForName4, "enablePowerSavedMode", (Class<?>[])parameterTypes43);
            RK31XXDevice.A1 = ReflectUtil.getMethodSafely(classForName4, "loadCACertificate", (Class<?>[])new Class[0]);
            RK31XXDevice.B1 = ReflectUtil.getMethodSafely(classForName4, "loadUserCertificate", (Class<?>[])new Class[0]);
            RK31XXDevice.y1 = ReflectUtil.getMethodSafely(classForName2, "isHallControlEnable", (Class<?>[])new Class[0]);
            RK31XXDevice.z1 = ReflectUtil.getMethodSafely(classForName2, "enableHallControl", type2);
            final Class<?> classForName5 = ReflectUtil.classForName("android.onyx.utils.ApplicationFreezeHelper");
            final Class[] parameterTypes44;
            final Class[] array43 = parameterTypes44 = new Class[2];
            array43[0] = Context.class;
            array43[1] = String.class;
            RK31XXDevice.C1 = ReflectUtil.getMethodSafely(classForName5, "disableAppByPkgName", (Class<?>[])parameterTypes44);
            final Class[] parameterTypes45;
            final Class[] array44 = parameterTypes45 = new Class[2];
            array44[0] = Context.class;
            array44[1] = String.class;
            RK31XXDevice.D1 = ReflectUtil.getMethodSafely(classForName5, "enableAppByPkgName", (Class<?>[])parameterTypes45);
            RK31XXDevice.E1 = ReflectUtil.getMethodSafely(classForName5, "disableGooglePlay", Context.class);
            RK31XXDevice.F1 = ReflectUtil.getMethodSafely(classForName5, "enableGooglePlay", Context.class);
            RK31XXDevice.G1 = ReflectUtil.getMethodSafely(classForName5, "isGoogleAppsEnabled", Context.class);
            RK31XXDevice.H1 = ReflectUtil.getMethodSafely(classForName5, "isGoogleAppsExists", Context.class);
            RK31XXDevice.Z1 = ReflectUtil.getMethodSafely(ReflectUtil.classForName("android.onyx.utils.ActivityManagerHelper"), "getCurrentTopComponent", Context.class);
            Log.d(RK31XXDevice.p, "init device EINK_ONYX_GC_MASK.");
            return RK31XXDevice.q;
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
        if ((n2 = RK31XXDevice$a.c[scheme.ordinal()]) != 1) {
            if (n2 != 2) {
                if (n2 != 3) {
                    if (!RK31XXDevice.a2) {
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
        if (value == RK31XXDevice.t) {
            return UpdateMode.DU;
        }
        if (value == RK31XXDevice.u) {
            return UpdateMode.GU;
        }
        if (value == RK31XXDevice.v) {
            return UpdateMode.GC;
        }
        return UpdateMode.GC;
    }
    
    private static int a(final UpdatePolicy policy) {
        int u = RK31XXDevice.u;
        final int n;
        if ((n = RK31XXDevice$a.d[policy.ordinal()]) != 1) {
            if (n != 2) {
                if (!RK31XXDevice.a2) {
                    throw new AssertionError();
                }
            }
            else {
                u |= RK31XXDevice.s;
            }
        }
        else {
            u |= RK31XXDevice.r;
        }
        return u;
    }
    
    private Object a(final Context context) {
        return context.getSystemService("activity");
    }
    
    static {
        a2 = (RK31XXDevice.class.desiredAssertionStatus() ^ true);
        p = RK31XXDevice.class.getSimpleName();
        RK31XXDevice.q = null;
        RK31XXDevice.r = 0;
        RK31XXDevice.s = 0;
        RK31XXDevice.t = 0;
        RK31XXDevice.u = 0;
        RK31XXDevice.v = 0;
        RK31XXDevice.w = 0;
        RK31XXDevice.x = 0;
        RK31XXDevice.y = 0;
        RK31XXDevice.z = 0;
        RK31XXDevice.A = 0;
        RK31XXDevice.G = null;
        RK31XXDevice.H = null;
        RK31XXDevice.I = null;
        RK31XXDevice.J = null;
        RK31XXDevice.K = null;
        RK31XXDevice.L = null;
        RK31XXDevice.M = null;
        RK31XXDevice.N = null;
        RK31XXDevice.O = null;
        RK31XXDevice.P = null;
        RK31XXDevice.Q = null;
        RK31XXDevice.R = null;
        RK31XXDevice.S = null;
        RK31XXDevice.T = null;
        RK31XXDevice.U = null;
        RK31XXDevice.V = null;
        RK31XXDevice.W = null;
        RK31XXDevice.X = null;
        RK31XXDevice.Y = null;
        RK31XXDevice.Z = null;
        RK31XXDevice.a0 = null;
        RK31XXDevice.b0 = null;
        RK31XXDevice.c0 = null;
        RK31XXDevice.d0 = null;
        RK31XXDevice.e0 = null;
        RK31XXDevice.f0 = null;
        RK31XXDevice.g0 = null;
        RK31XXDevice.h0 = null;
        RK31XXDevice.i0 = null;
        RK31XXDevice.j0 = null;
        RK31XXDevice.k0 = null;
        RK31XXDevice.l0 = null;
        RK31XXDevice.m0 = null;
        RK31XXDevice.n0 = null;
        RK31XXDevice.o0 = null;
        RK31XXDevice.p0 = null;
        RK31XXDevice.q0 = null;
        RK31XXDevice.r0 = null;
        RK31XXDevice.s0 = null;
        RK31XXDevice.t0 = null;
        RK31XXDevice.y0 = null;
        RK31XXDevice.z0 = null;
        RK31XXDevice.A0 = null;
        RK31XXDevice.B0 = null;
        RK31XXDevice.C0 = null;
        RK31XXDevice.D0 = null;
        RK31XXDevice.E0 = null;
        RK31XXDevice.F0 = null;
        RK31XXDevice.G0 = null;
        RK31XXDevice.H0 = null;
        RK31XXDevice.I0 = null;
        RK31XXDevice.J0 = null;
        RK31XXDevice.K0 = null;
        RK31XXDevice.L0 = null;
        RK31XXDevice.M0 = null;
        RK31XXDevice.N0 = null;
        RK31XXDevice.O0 = null;
    }
    
    public int getWindowRotation() {
        final Method g;
        if ((g = RK31XXDevice.G) != null) {
            final Method method = g;
            final Object obj = null;
            try {
                return (int)method.invoke(obj, new Object[0]);
            }
            catch (final InvocationTargetException ex) {}
            catch (final IllegalAccessException ex2) {}
            catch (final IllegalArgumentException ex3) {}
        }
        return 0;
    }
    
    public boolean setWindowRotation(final int rotation) {
        if (RK31XXDevice.H != null) {
            try {
                RK31XXDevice.H.invoke(null, rotation, Boolean.TRUE, 1);
                return true;
            }
            catch (final InvocationTargetException | IllegalAccessException | IllegalArgumentException error) {}
        }
        return false;
    }
    
    public boolean setUpdatePolicy(final View view, final UpdatePolicy policy, final int guInterval) {
        final int a = a(policy);
        try {
            if (!RK31XXDevice.a2 && RK31XXDevice.I == null) {
                throw new AssertionError();
            }
            RK31XXDevice.I.invoke(view, a, guInterval);
            return true;
        }
        catch (final InvocationTargetException ex) {}
        catch (final IllegalAccessException ex2) {}
        catch (final IllegalArgumentException ex3) {}
        return false;
    }
    
    @Override
    public File getStorageRootDirectory() {
        return (File)ReflectUtil.invokeMethodSafely(RK31XXDevice.w0, null, new Object[0]);
    }
    
    @Override
    public File getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory();
    }
    
    @Override
    public File getRemovableSDCardDirectory() {
        return (File)ReflectUtil.invokeMethodSafely(RK31XXDevice.x0, null, new Object[0]);
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
            if (!RK31XXDevice.a2 && RK31XXDevice.z0 == null) {
                throw new AssertionError();
            }
            RK31XXDevice.z0.invoke(view, a);
        }
        catch (final InvocationTargetException ex) {}
        catch (final IllegalAccessException ex2) {}
        catch (final IllegalArgumentException ex3) {}
    }
    
    @Override
    public void invalidate(final View view, final int left, final int top, final int right, final int bottom, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            if (!RK31XXDevice.a2 && RK31XXDevice.A0 == null) {
                throw new AssertionError();
            }
            final Method a2 = RK31XXDevice.A0;
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
            a2.invoke(view, args);
        }
        catch (final InvocationTargetException ex) {}
        catch (final IllegalAccessException ex2) {}
        catch (final IllegalArgumentException ex3) {}
    }
    
    @Override
    public void postInvalidate(final View view, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            if (!RK31XXDevice.a2 && RK31XXDevice.y0 == null) {
                throw new AssertionError();
            }
            Log.d(RK31XXDevice.p, "dst mode: " + a);
            RK31XXDevice.y0.invoke(view, a);
        }
        catch (final InvocationTargetException ex) {}
        catch (final IllegalAccessException ex2) {}
        catch (final IllegalArgumentException ex3) {}
    }
    
    @Override
    public void refreshScreen(final View view, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            if (!RK31XXDevice.a2 && RK31XXDevice.J == null) {
                throw new AssertionError();
            }
            RK31XXDevice.J.invoke(view, a);
        }
        catch (final InvocationTargetException ex) {}
        catch (final IllegalAccessException ex2) {}
        catch (final IllegalArgumentException ex3) {}
    }
    
    @Override
    public void refreshScreenRegion(final View view, final int left, final int top, final int width, final int height, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            if (!RK31XXDevice.a2 && RK31XXDevice.K == null) {
                throw new AssertionError();
            }
            final Method k = RK31XXDevice.K;
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
            k.invoke(view, args);
        }
        catch (final Exception ex) {}
    }
    
    @Override
    public void screenshot(final View view, final int rotation, final String path) {
        try {
            ReflectUtil.invokeMethodSafely(RK31XXDevice.L, view, rotation, path);
        }
        catch (final Exception ex) {}
    }
    
    @Override
    public void setStrokeColor(final int color) {
        try {
            final Method r = RK31XXDevice.R;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(r, receiver, color);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void setStrokeStyle(final int style) {
        try {
            final Method s = RK31XXDevice.S;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(s, receiver, style);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void setPainterStyle(final boolean antiAlias, final Paint.Style strokeStyle, final Paint.Join joinStyle, final Paint.Cap capStyle) {
        try {
            final Method u = RK31XXDevice.U;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(u, receiver, antiAlias, strokeStyle, joinStyle, capStyle);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void setStrokeWidth(final float width) {
        try {
            final Method t = RK31XXDevice.T;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(t, receiver, width);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void moveTo(final float x, final float y, final float width) {
        try {
            final Method p = RK31XXDevice.P;
            final Object receiver = null;
            try {
                final Object[] args = new Object[3];
                final Object[] array;
                (array = args)[0] = x;
                array[1] = y;
                args[2] = width;
                ReflectUtil.invokeMethodSafely(p, receiver, args);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void moveTo(final View view, final float x, final float y, final float width) {
        try {
            final Method q = RK31XXDevice.Q;
            final Object receiver = null;
            try {
                final Object[] args = new Object[4];
                final Object[] array2;
                final Object[] array;
                (array = (array2 = args))[0] = view;
                array[1] = x;
                array2[2] = y;
                args[3] = width;
                ReflectUtil.invokeMethodSafely(q, receiver, args);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public boolean supportDFB() {
        return RK31XXDevice.V != null;
    }
    
    @Override
    public boolean supportRegal() {
        final Method n;
        final Boolean b;
        return (n = RK31XXDevice.N) != null && (b = (Boolean)ReflectUtil.invokeMethodSafely(n, null, new Object[0])) != null && b;
    }
    
    @Override
    public void enableRegal(final boolean enable) {
        ReflectUtil.invokeMethodSafely(RK31XXDevice.O, null, enable);
    }
    
    @Override
    public void lineTo(final float x, final float y, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            final Method v = RK31XXDevice.V;
            final Object receiver = null;
            try {
                final Object[] array;
                final Object[] args = array = new Object[3];
                final int i = a;
                final Object[] array2 = array;
                array[0] = x;
                array2[1] = y;
                args[2] = i;
                ReflectUtil.invokeMethodSafely(v, receiver, args);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void lineTo(final View view, final float x, final float y, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            final Method w = RK31XXDevice.W;
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
                ReflectUtil.invokeMethodSafely(w, receiver, args);
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
            final Method x2 = RK31XXDevice.X;
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
    public void quadTo(final View view, final float x, final float y, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            final Method y2 = RK31XXDevice.Y;
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
    public float getTouchWidth() {
        try {
            final Method z = RK31XXDevice.Z;
            final Object receiver = null;
            try {
                return (float)ReflectUtil.invokeMethodSafely(z, receiver, new Object[0]);
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
            final Method c0 = RK31XXDevice.c0;
            final Object receiver = null;
            try {
                return (float)ReflectUtil.invokeMethodSafely(c0, receiver, new Object[0]);
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
            final Method b0 = RK31XXDevice.b0;
            final Object receiver = null;
            try {
                return (float)ReflectUtil.invokeMethodSafely(b0, receiver, new Object[0]);
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
            final Method e0 = RK31XXDevice.e0;
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
    public void mapFromRawTouchPoint(final View view, final float[] src, final float[] dst) {
        try {
            final Method f0 = RK31XXDevice.f0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(f0, receiver, view, src, dst);
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
            final Method g0 = RK31XXDevice.g0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(g0, receiver, view, src, dst);
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
            final Method d0 = RK31XXDevice.d0;
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
    public float getTouchHeight() {
        try {
            final Method a0 = RK31XXDevice.a0;
            final Object receiver = null;
            try {
                return (float)ReflectUtil.invokeMethodSafely(a0, receiver, new Object[0]);
            }
            catch (final Exception ex) {
                return 0.0f;
            }
        }
        catch (final Exception ex2) {}
        return 0.0f;
    }
    
    @Override
    public float startStroke(final float baseWidth, final float x, final float y, final float pressure, final float size, final float time) {
        try {
            final Method o0 = RK31XXDevice.o0;
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
                return (float)ReflectUtil.invokeMethodSafely(o0, receiver, args);
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
            final Method p0 = RK31XXDevice.p0;
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
    public float finishStroke(final float baseWidth, final float x, final float y, final float pressure, final float size, final float time) {
        try {
            final Method q0 = RK31XXDevice.q0;
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
    public void enterScribbleMode(final View view) {
        try {
            ReflectUtil.invokeMethodSafely(RK31XXDevice.h0, view, 0);
        }
        catch (final Exception ex) {}
    }
    
    @Override
    public void leaveScribbleMode(final View view) {
        try {
            ReflectUtil.invokeMethodSafely(RK31XXDevice.h0, view, 1);
        }
        catch (final Exception ex) {}
    }
    
    @Override
    public void enablePost(final View view, final int enable) {
        try {
            ReflectUtil.invokeMethodSafely(RK31XXDevice.h0, view, enable);
        }
        catch (final Exception ex) {}
    }
    
    @Override
    public void resetEpdPost() {
        try {
            final Method i0 = RK31XXDevice.i0;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(i0, receiver, new Object[0]);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    public boolean supportScreenHandWriting() {
        return RK31XXDevice.j0 != null;
    }
    
    @Override
    public void setScreenHandWritingPenState(final View view, final int penState) {
        try {
            ReflectUtil.invokeMethodSafely(RK31XXDevice.j0, view, penState);
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
            ReflectUtil.invokeMethodSafely(RK31XXDevice.k0, view, view, array);
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
            ReflectUtil.invokeMethodSafely(RK31XXDevice.l0, view, view, array);
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
    public void applyGammaCorrection(final boolean apply, final int value) {
        final Method m0 = RK31XXDevice.m0;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = apply;
        array[1] = value;
        ReflectUtil.invokeMethodSafely(m0, null, args);
    }
    
    @Override
    public boolean enableScreenUpdate(final View view, final boolean enable) {
        try {
            RK31XXDevice.M0.invoke(view, enable);
        }
        catch (final Exception ex) {}
        return true;
    }
    
    @Override
    public boolean setDisplayScheme(final int scheme) {
        ReflectUtil.invokeMethodSafely(RK31XXDevice.N0, null, scheme);
        return true;
    }
    
    @Override
    public void waitForUpdateFinished() {
        ReflectUtil.invokeMethodSafely(RK31XXDevice.O0, null, new Object[0]);
    }
    
    @Override
    public void useBigPen(final boolean use) {
        this.a(null, RK31XXDevice.g1, use);
    }
    
    @Override
    public void stopTpd() {
        this.a(null, RK31XXDevice.h1, new Object[0]);
    }
    
    @Override
    public void startTpd() {
        this.a(null, RK31XXDevice.i1, new Object[0]);
    }
    
    @Override
    public void enableTpd(final boolean enable) {
        ReflectUtil.invokeMethodSafely(RK31XXDevice.j1, null, enable ? 1 : 0);
    }
    
    @Override
    public UpdateMode getViewDefaultUpdateMode(final View view) {
        final Integer n;
        if ((n = (Integer)ReflectUtil.invokeMethodSafely(RK31XXDevice.B0, view, new Object[0])) == null) {
            return UpdateMode.GU;
        }
        return this.b(n);
    }
    
    @Override
    public void resetViewUpdateMode(final View view) {
        ReflectUtil.invokeMethodSafely(RK31XXDevice.C0, view, new Object[0]);
    }
    
    @Override
    public boolean setViewDefaultUpdateMode(final View view, final UpdateMode mode) {
        return ReflectUtil.invokeMethodSafely(RK31XXDevice.D0, view, this.a(mode)) != null;
    }
    
    @Override
    public UpdateMode getSystemDefaultUpdateMode() {
        final Integer n;
        if ((n = (Integer)ReflectUtil.invokeMethodSafely(RK31XXDevice.E0, null, new Object[0])) == null) {
            return UpdateMode.GU;
        }
        return this.b(n);
    }
    
    @Override
    public boolean setSystemDefaultUpdateMode(final UpdateMode mode) {
        return ReflectUtil.invokeMethodSafely(RK31XXDevice.F0, null, this.a(mode)) != null;
    }
    
    public boolean setFirstDrawUpdateMode(final UpdateMode mode) {
        return ReflectUtil.invokeMethodSafely(RK31XXDevice.G0, null, this.a(mode)) != null;
    }
    
    @Override
    public boolean applySysScopeUpdate(final UpdateMode mode, final UpdateScheme scheme, final int count) {
        final Method h0 = RK31XXDevice.H0;
        final Object[] array = new Object[3];
        final Object[] array2;
        final Object[] args = array2 = array;
        args[0] = this.a(mode);
        array2[1] = this.a(scheme);
        array[2] = count;
        return ReflectUtil.invokeMethodSafely(h0, null, args) != null;
    }
    
    @Override
    public boolean clearSysScopeUpdate() {
        return ReflectUtil.invokeMethodSafely(RK31XXDevice.L0, null, new Object[0]) != null;
    }
    
    @Override
    public boolean applyAppScopeUpdate(final String application, final boolean enable, final boolean clear) {
        final Method i0 = RK31XXDevice.I0;
        final Object[] array = new Object[3];
        final Object[] array2;
        final Object[] args = array2 = array;
        args[0] = application;
        array2[1] = enable;
        array[2] = clear;
        return ReflectUtil.invokeMethodSafely(i0, null, args) != null;
    }
    
    @Override
    public boolean applyAppScopeUpdate(final String application, final boolean enable, final boolean clear, final UpdateMode repeatMode, final int repeatLimit) {
        final Method j0 = RK31XXDevice.J0;
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
        return ReflectUtil.invokeMethodSafely(j0, null, args) != null;
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
        return (b = (Boolean)this.a(context, RK31XXDevice.P0, context)) != null && b;
    }
    
    @Override
    public boolean closeFrontLight(final Context context) {
        final Boolean b;
        return (b = (Boolean)this.a(context, RK31XXDevice.R0, context)) != null && b;
    }
    
    @Override
    public boolean openFrontLight(final int type) {
        final Object invokeMethodSafely;
        return (invokeMethodSafely = ReflectUtil.invokeMethodSafely(RK31XXDevice.Q0, null, type)) instanceof Boolean && (boolean)invokeMethodSafely;
    }
    
    @Override
    public boolean closeFrontLight(final int type) {
        final Object invokeMethodSafely;
        return (invokeMethodSafely = ReflectUtil.invokeMethodSafely(RK31XXDevice.S0, null, type)) instanceof Boolean && (boolean)invokeMethodSafely;
    }
    
    @Override
    public int getFrontLightDeviceValue(final Context context) {
        return this.getColdLightConfigValue(context);
    }
    
    @Override
    public boolean setFrontLightDeviceValue(final Context context, final int value) {
        this.setColdLightDeviceValue(context, value);
        return true;
    }
    
    @Override
    public int getFrontLightConfigValue(final Context context) {
        return this.getColdLightConfigValue(context);
    }
    
    @Override
    public boolean setFrontLightConfigValue(final Context context, final int value) {
        this.setColdLightDeviceValue(context, value);
        return true;
    }
    
    @Override
    public List<Integer> getFrontLightValueList(final Context context) {
        return Arrays.asList(this.getColdLightValues(context));
    }
    
    @Override
    public void led(final Context context, final boolean on) {
        this.a(context, RK31XXDevice.X0, on);
    }
    
    @Override
    public boolean setLedColor(final String color, final int on) {
        final Method y0 = RK31XXDevice.Y0;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = color;
        array[1] = on;
        this.a(null, y0, args);
        return true;
    }
    
    @Override
    public void setVCom(final Context context, final int value, final String path) {
        final Method z0 = RK31XXDevice.Z0;
        final Object[] args;
        final Object[] array = args = new Object[3];
        args[0] = context;
        array[1] = value;
        array[2] = path;
        this.a(context, z0, args);
    }
    
    @Override
    public int getVCom(final Context context, final String path) {
        final Integer n;
        if ((n = (Integer)this.a(context, RK31XXDevice.a1, path)) == null) {
            return 0;
        }
        return n;
    }
    
    @Override
    public void updateWaveform(final Context context, final String path, final String target) {
        final Method b1 = RK31XXDevice.b1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = path;
        array[1] = target;
        this.a(context, b1, args);
    }
    
    @Override
    public String readSystemConfig(final Context context, final String key) {
        final Object a;
        if ((a = this.a(context, RK31XXDevice.c1, key)) != null && !a.equals("")) {
            return a.toString();
        }
        return "";
    }
    
    @Override
    public boolean saveSystemConfig(final Context context, final String key, final String value) {
        final Method d1 = RK31XXDevice.d1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = key;
        array[1] = value;
        return ReflectUtil.getSafely((Boolean)this.a(context, d1, args));
    }
    
    @Override
    public void updateMetadataDB(final Context context, final String path, final String target) {
        final Method e1 = RK31XXDevice.e1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = path;
        array[1] = target;
        this.a(context, e1, args);
    }
    
    UpdateMode a(final EPDMode mode) {
        switch (RK31XXDevice$a.a[mode.ordinal()]) {
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
        int n = RK31XXDevice.v;
        switch (RK31XXDevice$a.b[mode.ordinal()]) {
            default: {
                if (RK31XXDevice.a2) {
                    break;
                }
                throw new AssertionError();
            }
            case 9: {
                if ((n = RK31XXDevice.A) != 0) {
                    break;
                }
                n = RK31XXDevice.u;
                break;
            }
            case 8: {
                if ((n = RK31XXDevice.z) != 0) {
                    break;
                }
                n = RK31XXDevice.u;
                break;
            }
            case 7: {
                n = RK31XXDevice.y;
                break;
            }
            case 6: {
                n = RK31XXDevice.x;
                break;
            }
            case 5: {
                n = RK31XXDevice.w;
                break;
            }
            case 4: {
                n = RK31XXDevice.v;
                break;
            }
            case 3: {
                n = RK31XXDevice.u;
                break;
            }
            case 1:
            case 2: {
                n = RK31XXDevice.t;
                break;
            }
        }
        return n;
    }
    
    @Override
    public void disableA2ForSpecificView(final View view) {
        ReflectUtil.invokeMethodSafely(RK31XXDevice.v0, view, new Object[0]);
    }
    
    @Override
    public void enableA2ForSpecificView(final View view) {
        ReflectUtil.invokeMethodSafely(RK31XXDevice.u0, view, new Object[0]);
    }
    
    @Override
    public void setWebViewContrastOptimize(final WebView view, final boolean enabled) {
        ReflectUtil.invokeMethodSafely(RK31XXDevice.o1, view, enabled);
    }
    
    @Override
    public void gotoSleep(final Context context) {
        ReflectUtil.invokeMethodSafely(RK31XXDevice.f1, context, System.currentTimeMillis());
    }
    
    @Override
    public boolean inSystemFastMode() {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(RK31XXDevice.r0, null, new Object[0])) != null && b;
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
        this.a(null, RK31XXDevice.k1, on);
    }
    
    @Override
    public void powerEMTP(final boolean on) {
        this.a(null, RK31XXDevice.l1, on);
    }
    
    @Override
    public boolean isCTPPowerOn() {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(RK31XXDevice.m1, null, new Object[0])) != null && b;
    }
    
    @Override
    public boolean isEMTPPowerOn() {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(RK31XXDevice.n1, null, new Object[0])) != null && b;
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
    public void applyGCOnce() {
        ReflectUtil.invokeMethodSafely(RK31XXDevice.p1, null, new Object[0]);
    }
    
    @Override
    public String getCTPInfo() {
        return com.onyx.android.sdk.device.a.a(new File("/sys/onyx_misc/captp_fwver"));
    }
    
    @Override
    public boolean hasWifi(final Context context) {
        return ReflectUtil.getSafely((Boolean)this.a(context, RK31XXDevice.K1, context));
    }
    
    @Override
    public boolean hasAudio(final Context context) {
        return ReflectUtil.getSafely((Boolean)this.a(context, RK31XXDevice.L1, context));
    }
    
    @Override
    public boolean hasFLBrightness(final Context context) {
        return ReflectUtil.getSafely((Boolean)this.a(context, RK31XXDevice.M1, context));
    }
    
    @Override
    public boolean hasBluetooth(final Context context) {
        return ReflectUtil.getSafely((Boolean)this.a(context, RK31XXDevice.N1, context));
    }
    
    @Override
    public boolean hasCTMBrightness(final Context context) {
        return ReflectUtil.getSafely((Boolean)this.a(context, RK31XXDevice.O1, context));
    }
    
    @Override
    public boolean supportExternalSD(final Context context) {
        final Boolean b;
        return (b = (Boolean)this.a(context, RK31XXDevice.P1, context)) == null || b;
    }
    
    @Override
    public void setCTMBrightnessValue(final int type, final int value) {
        if (type != 0) {
            if (type == 1) {
                final Method q1 = RK31XXDevice.Q1;
                final Object[] args;
                final Object[] array = args = new Object[2];
                args[0] = "/sys/class/backlight/warm/brightness";
                array[1] = value;
                ReflectUtil.invokeMethodSafely(q1, null, args);
            }
        }
        else {
            final Method q2 = RK31XXDevice.Q1;
            final Object[] args2;
            final Object[] array2 = args2 = new Object[2];
            args2[0] = "/sys/class/backlight/white/brightness";
            array2[1] = value;
            ReflectUtil.invokeMethodSafely(q2, null, args2);
        }
    }
    
    @Override
    public void byPass(final int count) {
        try {
            final Method m = RK31XXDevice.M;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(m, receiver, count);
            }
            catch (final Exception ex) {}
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void applyColorFilter(final int value) {
        ReflectUtil.invokeMethodSafely(RK31XXDevice.n0, null, value);
    }
    
    @Override
    public void repaintEveryThing(final UpdateMode mode) {
        ReflectUtil.invokeMethodSafely(RK31XXDevice.s0, null, this.a(mode));
    }
    
    @Override
    public void applySystemFastMode(final boolean enable) {
        if (enable) {
            ReflectUtil.invokeMethodSafely(RK31XXDevice.t0, null, new Object[0]);
        }
        else {
            this.clearSysScopeUpdate();
        }
    }
    
    @Override
    public void setTrigger(final int count) {
        ReflectUtil.invokeMethodSafely(RK31XXDevice.q1, null, count);
    }
    
    @Override
    public void removeAppConfig(final String pkgName) {
        ReflectUtil.invokeMethodSafely(RK31XXDevice.r1, null, pkgName);
    }
    
    @Override
    public void setEACServiceConfig(final boolean enable, final boolean debug) {
        final Method t1 = RK31XXDevice.t1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = enable;
        array[1] = debug;
        ReflectUtil.invokeMethodSafely(t1, null, args);
    }
    
    @Override
    public void setEACAppConfig(final String pkgName, final String jsonString) {
        final Method s1 = RK31XXDevice.s1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = pkgName;
        array[1] = jsonString;
        ReflectUtil.invokeMethodSafely(s1, null, args);
    }
    
    @Nullable
    @Override
    public String getIPAddress(final Context context) {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(RK31XXDevice.v1, null, context)) instanceof String) {
            return (String)invokeMethodSafely;
        }
        return null;
    }
    
    @Override
    public boolean isPowerSavedMode(final Context context) {
        return ReflectUtil.getSafely((Boolean)ReflectUtil.invokeMethodSafely(RK31XXDevice.w1, null, context));
    }
    
    @Override
    public void enablePowerSavedMode(final Context context, final boolean enable) {
        final Method x1 = RK31XXDevice.x1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = enable;
        ReflectUtil.invokeMethodSafely(x1, null, args);
    }
    
    @Override
    public boolean isHallControlEnable(final Context context) {
        return (boolean)this.a(context, RK31XXDevice.y1, new Object[0]);
    }
    
    @Override
    public void enableHallControl(final Context context, final boolean enable) {
        this.a(context, RK31XXDevice.z1, enable);
    }
    
    @Override
    public String[] loadCACertificate() {
        return (String[])ReflectUtil.invokeMethodSafely(RK31XXDevice.A1, null, new Object[0]);
    }
    
    @Override
    public String[] loadUserCertificate() {
        return (String[])ReflectUtil.invokeMethodSafely(RK31XXDevice.B1, null, new Object[0]);
    }
    
    @Override
    public void freezeApplication(final Context context, final String pkgName, final int userId) {
        final Method c1 = RK31XXDevice.C1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = context;
        array[1] = pkgName;
        ReflectUtil.invokeMethodSafely(c1, null, args);
    }
    
    @Override
    public void unfreezeApplication(final Context context, final String pkgName, final int userId) {
        final Method d1 = RK31XXDevice.D1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = context;
        array[1] = pkgName;
        ReflectUtil.invokeMethodSafely(d1, null, args);
    }
    
    @Override
    public void freezeGooglePlay(final Context context) {
        ReflectUtil.invokeMethodSafely(RK31XXDevice.E1, null, context);
    }
    
    @Override
    public void unfreezeGooglePlay(final Context context) {
        ReflectUtil.invokeMethodSafely(RK31XXDevice.F1, null, context);
    }
    
    @Override
    public boolean isGooglePlayEnabled(final Context context) {
        return ReflectUtil.getSafely((Boolean)ReflectUtil.invokeMethodSafely(RK31XXDevice.G1, null, context));
    }
    
    @Override
    public boolean isGoogleAppsExists(final Context context) {
        return ReflectUtil.getSafely((Boolean)ReflectUtil.invokeMethodSafely(RK31XXDevice.H1, null, context));
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
        if ((naturalLightValues = this.getNaturalLightValues(context)) == null || naturalLightValues.length <= 0) {
            return null;
        }
        if (naturalLightValues.length == 1) {
            return naturalLightValues[0];
        }
        return naturalLightValues[1];
    }
    
    @Override
    public Integer[][] getNaturalLightValues(final Context context) {
        final Object a;
        if ((a = this.a(context, RK31XXDevice.U1, context)) != null && a instanceof Integer[][]) {
            return (Integer[][])a;
        }
        return null;
    }
    
    @Override
    public int getWarmLightConfigValue(final Context context) {
        final Method t1 = RK31XXDevice.T1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = context;
        array[1] = 2;
        final Object a;
        if ((a = this.a(context, t1, args)) != null) {
            return (int)a;
        }
        return 0;
    }
    
    @Override
    public int getColdLightConfigValue(final Context context) {
        final Method t1 = RK31XXDevice.T1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = context;
        array[1] = 3;
        final Object a;
        if ((a = this.a(context, t1, args)) != null) {
            return (int)a;
        }
        return 0;
    }
    
    @Override
    public boolean setWarmLightDeviceValue(final Context context, final int value) {
        final Method s1 = RK31XXDevice.S1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = value;
        return this.a(context, s1, args) != null;
    }
    
    @Override
    public boolean setColdLightDeviceValue(final Context context, final int value) {
        final Method r1 = RK31XXDevice.R1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = value;
        return this.a(context, r1, args) != null;
    }
    
    @Override
    public boolean increaseBrightness(final Context context, final int colorTemp) {
        final Method v1 = RK31XXDevice.V1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = colorTemp;
        return ReflectUtil.getSafely((Boolean)this.a(context, v1, args));
    }
    
    @Override
    public boolean decreaseBrightness(final Context context, final int colorTemp) {
        final Method w1 = RK31XXDevice.W1;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = colorTemp;
        return ReflectUtil.getSafely((Boolean)this.a(context, w1, args));
    }
    
    @Override
    public boolean isBrightnessOn(final Context context) {
        final Boolean b;
        return (this.hasCTMBrightness(context) || this.hasFLBrightness(context)) && (b = (Boolean)ReflectUtil.invokeMethodSafely(RK31XXDevice.Y1, null, context)) != null && b;
    }
    
    @Override
    public boolean isLightOn(final Context context) {
        return this.isLightOn(context, 4);
    }
    
    @Override
    public boolean isLightOn(final Context context, final int type) {
        final Boolean b;
        return (this.hasCTMBrightness(context) || this.hasFLBrightness(context)) && (b = (Boolean)ReflectUtil.invokeMethodSafely(RK31XXDevice.X1, null, type)) != null && b;
    }
    
    @Nullable
    @Override
    public ComponentName getCurrentTopComponent(final Context context) {
        return (ComponentName)ReflectUtil.invokeMethodSafely(RK31XXDevice.Z1, null, context);
    }
    
    @Nullable
    @Override
    public String getEACAppConfigByPkgName(final String pkgName) {
        final Object invokeMethodSafely;
        if (!((invokeMethodSafely = ReflectUtil.invokeMethodSafely(RK31XXDevice.u1, null, pkgName)) instanceof String)) {
            return "";
        }
        return (String)invokeMethodSafely;
    }
    
    @Override
    public boolean clearAppScopeUpdate() {
        return ReflectUtil.invokeMethodSafely(RK31XXDevice.K0, null, new Object[0]) != null;
    }
    
    @Override
    public boolean clearAppScopeUpdate(final boolean clear) {
        return this.clearAppScopeUpdate();
    }
}
