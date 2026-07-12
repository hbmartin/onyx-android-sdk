// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.device;

import androidx.annotation.Nullable;
import android.content.Intent;
import java.util.Arrays;
import java.util.List;
import android.graphics.Rect;
import com.onyx.android.sdk.api.device.epd.EPDMode;
import android.os.Build;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import com.onyx.android.sdk.api.device.epd.UpdatePolicy;
import com.onyx.android.sdk.api.device.epd.UpdateMode;
import com.onyx.android.sdk.api.device.epd.UpdateScheme;
import android.util.Log;
import android.os.Environment;
import android.webkit.WebView;
import android.graphics.Paint;
import android.content.Context;
import com.onyx.android.sdk.utils.ReflectUtil;
import android.view.View;
import java.lang.reflect.Method;

public class IMX6Device extends BaseDevice
{
    private static final String p;
    private static IMX6Device q;
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
    private static Method E;
    private static Method F;
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
    private static final String i1 = "set_app_ctp_disable_region";
    private static final String j1 = "app_reset_ctp_disable_region";
    private static final String k1 = "check_ctp_status";
    private static final String l1 = "ctp_status";
    private static final String m1 = "status";
    private static final String n1 = "args_region_to_disable";
    private static final String o1 = "args_exclude_region";
    static final /* synthetic */ boolean p1;
    
    private IMX6Device() {
    }
    
    public static IMX6Device createDevice() {
        final IMX6Device q;
        if ((q = IMX6Device.q) == null) {
            IMX6Device.q = new IMX6Device();
            IMX6Device.E = ReflectUtil.getMethodSafely(View.class, "getWindowRotation", (Class<?>[])new Class[0]);
            final Class[] parameterTypes;
            final Class[] array = parameterTypes = new Class[3];
            final Class<Integer> type = Integer.TYPE;
            array[0] = type;
            final Class<Boolean> type2 = Boolean.TYPE;
            array[1] = type2;
            array[2] = type;
            IMX6Device.F = ReflectUtil.getMethodSafely(View.class, "setWindowRotation", (Class<?>[])parameterTypes);
            IMX6Device.x0 = ReflectUtil.getMethodSafely(View.class, "inSystemFastMode", (Class<?>[])new Class[0]);
            final int staticIntFieldSafely = ReflectUtil.getStaticIntFieldSafely(View.class, "EINK_ONYX_AUTO_MASK");
            final int staticIntFieldSafely2 = ReflectUtil.getStaticIntFieldSafely(View.class, "EINK_ONYX_GC_MASK");
            final int staticIntFieldSafely3 = ReflectUtil.getStaticIntFieldSafely(View.class, "EINK_AUTO_MODE_REGIONAL");
            final int s = staticIntFieldSafely2;
            final int r = staticIntFieldSafely;
            final int staticIntFieldSafely4 = ReflectUtil.getStaticIntFieldSafely(View.class, "EINK_WAIT_MODE_NOWAIT");
            final int staticIntFieldSafely5 = ReflectUtil.getStaticIntFieldSafely(View.class, "EINK_WAIT_MODE_WAIT");
            final int staticIntFieldSafely6 = ReflectUtil.getStaticIntFieldSafely(View.class, "EINK_WAVEFORM_MODE_DU");
            final int staticIntFieldSafely7 = ReflectUtil.getStaticIntFieldSafely(View.class, "EINK_WAVEFORM_MODE_ANIM");
            final int staticIntFieldSafely8 = ReflectUtil.getStaticIntFieldSafely(View.class, "EINK_WAVEFORM_MODE_GC4");
            final int staticIntFieldSafely9 = ReflectUtil.getStaticIntFieldSafely(View.class, "EINK_WAVEFORM_MODE_GC16");
            final int staticIntFieldSafely10 = ReflectUtil.getStaticIntFieldSafely(View.class, "EINK_WAVEFORM_MODE_REAGL");
            final int staticIntFieldSafely11 = ReflectUtil.getStaticIntFieldSafely(View.class, "EINK_REAGL_MODE_REAGLD");
            final int staticIntFieldSafely12 = ReflectUtil.getStaticIntFieldSafely(View.class, "EINK_UPDATE_MODE_PARTIAL");
            final int staticIntFieldSafely13 = ReflectUtil.getStaticIntFieldSafely(View.class, "EINK_UPDATE_MODE_FULL");
            IMX6Device.r = r;
            IMX6Device.s = s;
            final int n2;
            final int n = n2 = (staticIntFieldSafely3 | staticIntFieldSafely4);
            final int n3 = staticIntFieldSafely11;
            final int n4 = n2;
            final int n5 = staticIntFieldSafely10;
            final int n6 = n2;
            final int n7 = staticIntFieldSafely8;
            final int n8 = n2;
            final int n9 = staticIntFieldSafely7;
            final int n10 = staticIntFieldSafely3;
            final int n11 = staticIntFieldSafely5;
            final int n12 = n2;
            final int n13 = staticIntFieldSafely9;
            IMX6Device.t = (n2 | staticIntFieldSafely6 | staticIntFieldSafely12);
            IMX6Device.u = (n12 | n13 | staticIntFieldSafely12);
            IMX6Device.v = (n10 | n11 | staticIntFieldSafely9 | staticIntFieldSafely13);
            IMX6Device.w = (n8 | n9 | staticIntFieldSafely12);
            IMX6Device.x = ReflectUtil.getStaticIntFieldSafely(View.class, "UI_A2_QUALITY_MODE");
            IMX6Device.y = (n6 | n7 | staticIntFieldSafely12);
            IMX6Device.z = (n4 | n5 | staticIntFieldSafely12);
            IMX6Device.A = (n | n3 | staticIntFieldSafely10 | staticIntFieldSafely12);
            final Class<?> classForName = ReflectUtil.classForName("android.hardware.DeviceController");
            IMX6Device.K0 = ReflectUtil.getMethodSafely(classForName, "openFrontLight", Context.class);
            IMX6Device.L0 = ReflectUtil.getMethodSafely(classForName, "closeFrontLight", Context.class);
            IMX6Device.M0 = ReflectUtil.getMethodSafely(classForName, "getFrontLightValue", Context.class);
            final Class[] parameterTypes2;
            final Class[] array2 = parameterTypes2 = new Class[2];
            array2[0] = Context.class;
            array2[1] = type;
            IMX6Device.N0 = ReflectUtil.getMethodSafely(classForName, "setFrontLightValue", (Class<?>[])parameterTypes2);
            final Class[] parameterTypes3;
            final Class[] array3 = parameterTypes3 = new Class[2];
            array3[0] = Context.class;
            array3[1] = type;
            IMX6Device.O0 = ReflectUtil.getMethodSafely(classForName, "setNaturalLightValue", (Class<?>[])parameterTypes3);
            IMX6Device.P0 = ReflectUtil.getMethodSafely(classForName, "getFrontLightConfigValue", Context.class);
            final Class[] parameterTypes4;
            final Class[] array4 = parameterTypes4 = new Class[2];
            array4[0] = Context.class;
            array4[1] = type;
            IMX6Device.Q0 = ReflectUtil.getMethodSafely(classForName, "setFrontLightConfigValue", (Class<?>[])parameterTypes4);
            IMX6Device.a1 = ReflectUtil.getMethodSafely(classForName, "useBigPen", type2);
            IMX6Device.b1 = ReflectUtil.getMethodSafely(classForName, "stopTpd", (Class<?>[])new Class[0]);
            IMX6Device.c1 = ReflectUtil.getMethodSafely(classForName, "startTpd", (Class<?>[])new Class[0]);
            final Class[] parameterTypes5;
            final Class[] array5 = parameterTypes5 = new Class[2];
            array5[0] = Context.class;
            array5[1] = Long.TYPE;
            IMX6Device.Z0 = ReflectUtil.getMethodSafely(classForName, "gotoSleep", (Class<?>[])parameterTypes5);
            IMX6Device.R0 = ReflectUtil.getMethodSafely(classForName, "led", type2);
            final Class[] parameterTypes6;
            final Class[] array6 = parameterTypes6 = new Class[2];
            array6[0] = String.class;
            array6[1] = type;
            IMX6Device.S0 = ReflectUtil.getMethodSafely(classForName, "setLedColor", (Class<?>[])parameterTypes6);
            IMX6Device.d1 = ReflectUtil.getMethodSafely(View.class, "enableOnyxTpd", type);
            IMX6Device.e1 = ReflectUtil.getMethodSafely(classForName, "hasWifi", (Class<?>[])new Class[0]);
            final Class[] parameterTypes7;
            final Class[] array7 = parameterTypes7 = new Class[2];
            array7[1] = (array7[0] = type);
            IMX6Device.G = ReflectUtil.getMethodSafely(View.class, "setUpdatePolicy", (Class<?>[])parameterTypes7);
            IMX6Device.u0 = ReflectUtil.getMethodSafely(View.class, "postInvalidate", type);
            IMX6Device.H = ReflectUtil.getMethodSafely(View.class, "refreshScreen", type);
            final Class[] parameterTypes8;
            final Class[] array8 = parameterTypes8 = new Class[5];
            array8[0] = type;
            array8[2] = (array8[1] = type);
            array8[4] = (array8[3] = type);
            IMX6Device.I = ReflectUtil.getMethodSafely(View.class, "refreshScreen", (Class<?>[])parameterTypes8);
            final Class[] parameterTypes9;
            final Class[] array9 = parameterTypes9 = new Class[2];
            array9[0] = type;
            array9[1] = String.class;
            IMX6Device.J = ReflectUtil.getMethodSafely(View.class, "screenshot", (Class<?>[])parameterTypes9);
            IMX6Device.O = ReflectUtil.getMethodSafely(View.class, "setStrokeColor", type);
            IMX6Device.P = ReflectUtil.getMethodSafely(View.class, "setStrokeStyle", type);
            final Class[] parameterTypes10 = { null };
            final Class<Float> type3 = Float.TYPE;
            parameterTypes10[0] = type3;
            IMX6Device.Q = ReflectUtil.getMethodSafely(View.class, "setStrokeWidth", (Class<?>[])parameterTypes10);
            final Class[] parameterTypes11;
            final Class[] array10 = parameterTypes11 = new Class[4];
            array10[0] = type2;
            array10[1] = Paint.Style.class;
            array10[2] = Paint.Join.class;
            array10[3] = Paint.Cap.class;
            IMX6Device.R = ReflectUtil.getMethodSafely(View.class, "setPainterStyle", (Class<?>[])parameterTypes11);
            IMX6Device.K = ReflectUtil.getMethodSafely(View.class, "supportRegal", (Class<?>[])new Class[0]);
            IMX6Device.L = ReflectUtil.getMethodSafely(View.class, "enableRegal", type2);
            final Class[] parameterTypes12;
            final Class[] array11 = parameterTypes12 = new Class[3];
            array11[0] = type3;
            array11[2] = (array11[1] = type3);
            IMX6Device.M = ReflectUtil.getMethodSafely(View.class, "moveTo", (Class<?>[])parameterTypes12);
            final Class[] parameterTypes13;
            final Class[] array12 = parameterTypes13 = new Class[3];
            array12[1] = (array12[0] = type3);
            array12[2] = type;
            IMX6Device.S = ReflectUtil.getMethodSafely(View.class, "lineTo", (Class<?>[])parameterTypes13);
            final Class[] parameterTypes14;
            final Class[] array13 = parameterTypes14 = new Class[3];
            array13[1] = (array13[0] = type3);
            array13[2] = type;
            IMX6Device.U = ReflectUtil.getMethodSafely(View.class, "quadTo", (Class<?>[])parameterTypes14);
            final Class[] parameterTypes15;
            final Class[] array14 = parameterTypes15 = new Class[4];
            array14[0] = View.class;
            array14[1] = type3;
            array14[3] = (array14[2] = type3);
            IMX6Device.N = ReflectUtil.getMethodSafely(View.class, "moveTo", (Class<?>[])parameterTypes15);
            final Class[] parameterTypes16;
            final Class[] array15 = parameterTypes16 = new Class[4];
            array15[0] = View.class;
            array15[2] = (array15[1] = type3);
            array15[3] = type;
            IMX6Device.T = ReflectUtil.getMethodSafely(View.class, "lineTo", (Class<?>[])parameterTypes16);
            final Class[] parameterTypes17;
            final Class[] array16 = parameterTypes17 = new Class[4];
            array16[0] = View.class;
            array16[2] = (array16[1] = type3);
            array16[3] = type;
            IMX6Device.V = ReflectUtil.getMethodSafely(View.class, "quadTo", (Class<?>[])parameterTypes17);
            IMX6Device.W = ReflectUtil.getMethodSafely(View.class, "getTouchWidth", (Class<?>[])new Class[0]);
            IMX6Device.X = ReflectUtil.getMethodSafely(View.class, "getTouchHeight", (Class<?>[])new Class[0]);
            IMX6Device.Y = ReflectUtil.getMethodSafely(View.class, "getMaxTouchPressure", (Class<?>[])new Class[0]);
            IMX6Device.Z = ReflectUtil.getMethodSafely(View.class, "getEpdWidth", (Class<?>[])new Class[0]);
            IMX6Device.a0 = ReflectUtil.getMethodSafely(View.class, "getEpdHeight", (Class<?>[])new Class[0]);
            final Class[] parameterTypes18;
            final Class[] array17 = parameterTypes18 = new Class[3];
            array17[0] = View.class;
            array17[2] = (array17[1] = float[].class);
            IMX6Device.b0 = ReflectUtil.getMethodSafely(View.class, "mapToView", (Class<?>[])parameterTypes18);
            final Class[] parameterTypes19;
            final Class[] array18 = parameterTypes19 = new Class[3];
            array18[0] = View.class;
            array18[2] = (array18[1] = float[].class);
            IMX6Device.c0 = ReflectUtil.getMethodSafely(View.class, "mapToEpd", (Class<?>[])parameterTypes19);
            final Class[] parameterTypes20;
            final Class[] array19 = parameterTypes20 = new Class[3];
            array19[0] = View.class;
            array19[2] = (array19[1] = float[].class);
            IMX6Device.d0 = ReflectUtil.getMethodSafely(View.class, "mapFromRawTouchPoint", (Class<?>[])parameterTypes20);
            final Class[] parameterTypes21;
            final Class[] array20 = parameterTypes21 = new Class[3];
            array20[0] = View.class;
            array20[2] = (array20[1] = float[].class);
            IMX6Device.e0 = ReflectUtil.getMethodSafely(View.class, "mapToRawTouchPoint", (Class<?>[])parameterTypes21);
            IMX6Device.f0 = ReflectUtil.getMethodSafely(View.class, "enablePost", type);
            IMX6Device.g0 = ReflectUtil.getMethodSafely(View.class, "resetEpdPost", (Class<?>[])new Class[0]);
            IMX6Device.h0 = ReflectUtil.getMethodSafely(View.class, "setScreenHandWritingPenState", type);
            final Class[] parameterTypes22;
            final Class[] array21 = parameterTypes22 = new Class[2];
            array21[0] = View.class;
            array21[1] = int[].class;
            IMX6Device.i0 = ReflectUtil.getMethodSafely(View.class, "setScreenHandWritingRegionLimit", (Class<?>[])parameterTypes22);
            final Class[] parameterTypes23;
            final Class[] array22 = parameterTypes23 = new Class[2];
            array22[0] = View.class;
            array22[1] = int[].class;
            IMX6Device.j0 = ReflectUtil.getMethodSafely(View.class, "setScreenHandWritingRegionExclude", (Class<?>[])parameterTypes23);
            final Class[] parameterTypes24;
            final Class[] array23 = parameterTypes24 = new Class[2];
            array23[0] = type2;
            array23[1] = type;
            IMX6Device.k0 = ReflectUtil.getMethodSafely(View.class, "applyGammaCorrection", (Class<?>[])parameterTypes24);
            final Class[] parameterTypes25;
            final Class[] array24 = parameterTypes25 = new Class[6];
            array24[1] = (array24[0] = type3);
            array24[3] = (array24[2] = type3);
            array24[5] = (array24[4] = type3);
            IMX6Device.l0 = ReflectUtil.getMethodSafely(View.class, "startStroke", (Class<?>[])parameterTypes25);
            final Class[] parameterTypes26;
            final Class[] array25 = parameterTypes26 = new Class[6];
            array25[1] = (array25[0] = type3);
            array25[3] = (array25[2] = type3);
            array25[5] = (array25[4] = type3);
            IMX6Device.m0 = ReflectUtil.getMethodSafely(View.class, "addStrokePoint", (Class<?>[])parameterTypes26);
            final Class[] parameterTypes27;
            final Class[] array26 = parameterTypes27 = new Class[6];
            array26[1] = (array26[0] = type3);
            array26[3] = (array26[2] = type3);
            array26[5] = (array26[4] = type3);
            IMX6Device.n0 = ReflectUtil.getMethodSafely(View.class, "finishStroke", (Class<?>[])parameterTypes27);
            IMX6Device.v0 = ReflectUtil.getMethodSafely(View.class, "invalidate", type);
            final Class[] parameterTypes28;
            final Class[] array27 = parameterTypes28 = new Class[5];
            array27[0] = type;
            array27[2] = (array27[1] = type);
            array27[4] = (array27[3] = type);
            IMX6Device.w0 = ReflectUtil.getMethodSafely(View.class, "invalidate", (Class<?>[])parameterTypes28);
            IMX6Device.A0 = ReflectUtil.getMethodSafely(View.class, "setDefaultUpdateMode", type);
            IMX6Device.y0 = ReflectUtil.getMethodSafely(View.class, "getDefaultUpdateMode", (Class<?>[])new Class[0]);
            IMX6Device.z0 = ReflectUtil.getMethodSafely(View.class, "resetUpdateMode", (Class<?>[])new Class[0]);
            IMX6Device.B0 = ReflectUtil.getMethodSafely(View.class, "getGlobalUpdateMode", (Class<?>[])new Class[0]);
            IMX6Device.C0 = ReflectUtil.getMethodSafely(View.class, "setGlobalUpdateMode", type);
            IMX6Device.D0 = ReflectUtil.getMethodSafely(View.class, "setFirstDrawUpdateMode", type);
            final Class[] parameterTypes29;
            final Class[] array28 = parameterTypes29 = new Class[3];
            array28[0] = type;
            array28[2] = (array28[1] = type);
            IMX6Device.E0 = ReflectUtil.getMethodSafely(View.class, "setWaveformAndScheme", (Class<?>[])parameterTypes29);
            IMX6Device.G0 = ReflectUtil.getMethodSafely(View.class, "resetWaveformAndScheme", (Class<?>[])new Class[0]);
            final Class[] parameterTypes30;
            final Class[] array29 = parameterTypes30 = new Class[3];
            array29[0] = String.class;
            array29[2] = (array29[1] = type2);
            IMX6Device.F0 = ReflectUtil.getMethodSafely(View.class, "applyApplicationFastMode", (Class<?>[])parameterTypes30);
            IMX6Device.H0 = ReflectUtil.getMethodSafely(View.class, "enableScreenUpdate", type2);
            IMX6Device.I0 = ReflectUtil.getMethodSafely(View.class, "setDisplayScheme", type);
            IMX6Device.J0 = ReflectUtil.getMethodSafely(View.class, "waitForUpdateFinished", (Class<?>[])new Class[0]);
            final Class[] parameterTypes31;
            final Class[] array30 = parameterTypes31 = new Class[3];
            array30[0] = Context.class;
            array30[1] = type;
            array30[2] = String.class;
            IMX6Device.T0 = ReflectUtil.getMethodSafely(classForName, "setVCom", (Class<?>[])parameterTypes31);
            IMX6Device.U0 = ReflectUtil.getMethodSafely(classForName, "getVCom", String.class);
            final Class[] parameterTypes32;
            final Class[] array31 = parameterTypes32 = new Class[2];
            array31[1] = (array31[0] = String.class);
            IMX6Device.V0 = ReflectUtil.getMethodSafely(classForName, "updateWaveform", (Class<?>[])parameterTypes32);
            IMX6Device.W0 = ReflectUtil.getMethodSafely(classForName, "readSystemConfig", String.class);
            final Class[] parameterTypes33;
            final Class[] array32 = parameterTypes33 = new Class[2];
            array32[1] = (array32[0] = String.class);
            IMX6Device.X0 = ReflectUtil.getMethodSafely(classForName, "saveSystemConfig", (Class<?>[])parameterTypes33);
            final Class[] parameterTypes34;
            final Class[] array33 = parameterTypes34 = new Class[2];
            array33[1] = (array33[0] = String.class);
            IMX6Device.Y0 = ReflectUtil.getMethodSafely(classForName, "updateMetadataDB", (Class<?>[])parameterTypes34);
            IMX6Device.g1 = ReflectUtil.getMethodSafely(classForName, "getWarmLightConfigValue", Context.class);
            IMX6Device.h1 = ReflectUtil.getMethodSafely(classForName, "getColdLightConfigValue", Context.class);
            IMX6Device.o0 = ReflectUtil.getMethodSafely(View.class, "enableA2", (Class<?>[])new Class[0]);
            IMX6Device.p0 = ReflectUtil.getMethodSafely(View.class, "disableA2", (Class<?>[])new Class[0]);
            IMX6Device.q0 = ReflectUtil.getMethodSafely(WebView.class, "setCssInjectEnabled", type2);
            final Class[] parameterTypes35;
            final Class[] array34 = parameterTypes35 = new Class[3];
            array34[0] = type;
            array34[2] = (array34[1] = type);
            IMX6Device.r0 = ReflectUtil.getMethodSafely(View.class, "setQRShowConfig", (Class<?>[])parameterTypes35);
            final Class[] parameterTypes36;
            final Class[] array35 = parameterTypes36 = new Class[3];
            array35[0] = type;
            array35[2] = (array35[1] = type);
            IMX6Device.s0 = ReflectUtil.getMethodSafely(View.class, "setInfoShowConfig", (Class<?>[])parameterTypes36);
            IMX6Device.t0 = ReflectUtil.getMethodSafely(Environment.class, "getRemovableSDCardDirectory", (Class<?>[])new Class[0]);
            IMX6Device.f1 = ReflectUtil.getMethodSafely(View.class, "applyGCOnce", (Class<?>[])new Class[0]);
            Log.d(IMX6Device.p, "init device EINK_ONYX_GC_MASK.");
            return IMX6Device.q;
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
        if ((n2 = IMX6Device$a.c[scheme.ordinal()]) != 1) {
            if (n2 != 2) {
                if (n2 != 3) {
                    if (!IMX6Device.p1) {
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
        if (value == IMX6Device.t) {
            return UpdateMode.DU;
        }
        if (value == IMX6Device.u) {
            return UpdateMode.GU;
        }
        if (value == IMX6Device.v) {
            return UpdateMode.GC;
        }
        return UpdateMode.GC;
    }
    
    private static int a(final UpdatePolicy policy) {
        int u = IMX6Device.u;
        final int n;
        if ((n = IMX6Device$a.d[policy.ordinal()]) != 1) {
            if (n != 2) {
                if (!IMX6Device.p1) {
                    throw new AssertionError();
                }
            }
            else {
                u |= IMX6Device.s;
            }
        }
        else {
            u |= IMX6Device.r;
        }
        return u;
    }
    
    static {
        p1 = (IMX6Device.class.desiredAssertionStatus() ^ true);
        p = IMX6Device.class.getSimpleName();
        IMX6Device.q = null;
        IMX6Device.r = 0;
        IMX6Device.s = 0;
        IMX6Device.t = 0;
        IMX6Device.u = 0;
        IMX6Device.v = 0;
        IMX6Device.w = 0;
        IMX6Device.x = 0;
        IMX6Device.y = 0;
        IMX6Device.z = 0;
        IMX6Device.A = 0;
        IMX6Device.E = null;
        IMX6Device.F = null;
        IMX6Device.G = null;
        IMX6Device.H = null;
        IMX6Device.I = null;
        IMX6Device.J = null;
        IMX6Device.K = null;
        IMX6Device.L = null;
        IMX6Device.M = null;
        IMX6Device.N = null;
        IMX6Device.O = null;
        IMX6Device.P = null;
        IMX6Device.Q = null;
        IMX6Device.R = null;
        IMX6Device.S = null;
        IMX6Device.T = null;
        IMX6Device.U = null;
        IMX6Device.V = null;
        IMX6Device.W = null;
        IMX6Device.X = null;
        IMX6Device.Y = null;
        IMX6Device.Z = null;
        IMX6Device.a0 = null;
        IMX6Device.b0 = null;
        IMX6Device.c0 = null;
        IMX6Device.d0 = null;
        IMX6Device.e0 = null;
        IMX6Device.f0 = null;
        IMX6Device.g0 = null;
        IMX6Device.h0 = null;
        IMX6Device.i0 = null;
        IMX6Device.j0 = null;
        IMX6Device.k0 = null;
        IMX6Device.l0 = null;
        IMX6Device.m0 = null;
        IMX6Device.n0 = null;
        IMX6Device.u0 = null;
        IMX6Device.v0 = null;
        IMX6Device.w0 = null;
        IMX6Device.x0 = null;
        IMX6Device.y0 = null;
        IMX6Device.z0 = null;
        IMX6Device.A0 = null;
        IMX6Device.B0 = null;
        IMX6Device.C0 = null;
        IMX6Device.D0 = null;
        IMX6Device.E0 = null;
        IMX6Device.F0 = null;
        IMX6Device.G0 = null;
        IMX6Device.H0 = null;
        IMX6Device.I0 = null;
        IMX6Device.J0 = null;
    }
    
    public int getWindowRotation() {
        final Method e;
        if ((e = IMX6Device.E) != null) {
            final Method method = e;
            final Object obj = null;
            try {
                return (int)method.invoke(obj, new Object[0]);
            }
            catch (final InvocationTargetException ex) {
                Log.w(IMX6Device.p, (Throwable)ex);
            }
            catch (final IllegalAccessException ex2) {
                Log.w(IMX6Device.p, (Throwable)ex2);
            }
            catch (final IllegalArgumentException ex3) {
                Log.w(IMX6Device.p, (Throwable)ex3);
            }
        }
        return 0;
    }
    
    public boolean setWindowRotation(final int rotation) {
        if (IMX6Device.F != null) {
            try {
                IMX6Device.F.invoke(null, rotation, Boolean.TRUE, 1);
                return true;
            } catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException error) {
                Log.w(IMX6Device.p, error);
            }
        }
        return false;
    }
    
    public boolean setUpdatePolicy(final View view, final UpdatePolicy policy, final int guInterval) {
        final int a = a(policy);
        try {
            if (!IMX6Device.p1 && IMX6Device.G == null) {
                throw new AssertionError();
            }
            IMX6Device.G.invoke(view, a, guInterval);
            return true;
        }
        catch (final InvocationTargetException ex) {}
        catch (final IllegalAccessException ex2) {}
        catch (final IllegalArgumentException ex3) {}
        return false;
    }
    
    @Override
    public File getStorageRootDirectory() {
        return new File("/mnt");
    }
    
    @Override
    public File getExternalStorageDirectory() {
        if (Build.VERSION.SDK_INT >= 19) {
            return Environment.getExternalStorageDirectory();
        }
        return new File("/mnt/sdcard");
    }
    
    @Override
    public File getRemovableSDCardDirectory() {
        final File file;
        if (Build.VERSION.SDK_INT >= 19 && (file = (File)ReflectUtil.invokeMethodSafely(IMX6Device.t0, null, new Object[0])) != null) {
            return file;
        }
        return new File("/mnt/extsd");
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
            if (!IMX6Device.p1 && IMX6Device.v0 == null) {
                throw new AssertionError();
            }
            IMX6Device.v0.invoke(view, a);
        }
        catch (final InvocationTargetException ex) {}
        catch (final IllegalAccessException ex2) {}
        catch (final IllegalArgumentException ex3) {}
    }
    
    @Override
    public void invalidate(final View view, final int left, final int top, final int right, final int bottom, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            if (!IMX6Device.p1 && IMX6Device.w0 == null) {
                throw new AssertionError();
            }
            final Method w0 = IMX6Device.w0;
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
            w0.invoke(view, args);
        }
        catch (final InvocationTargetException ex) {}
        catch (final IllegalAccessException ex2) {}
        catch (final IllegalArgumentException ex3) {}
    }
    
    @Override
    public void postInvalidate(final View view, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            if (!IMX6Device.p1 && IMX6Device.u0 == null) {
                throw new AssertionError();
            }
            Log.d(IMX6Device.p, "dst mode: " + a);
            IMX6Device.u0.invoke(view, a);
        }
        catch (final InvocationTargetException ex) {}
        catch (final IllegalAccessException ex2) {}
        catch (final IllegalArgumentException ex3) {}
    }
    
    @Override
    public void refreshScreen(final View view, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            if (!IMX6Device.p1 && IMX6Device.H == null) {
                throw new AssertionError();
            }
            IMX6Device.H.invoke(view, a);
        }
        catch (final InvocationTargetException ex) {}
        catch (final IllegalAccessException ex2) {}
        catch (final IllegalArgumentException ex3) {}
    }
    
    @Override
    public void refreshScreenRegion(final View view, final int left, final int top, final int width, final int height, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            if (!IMX6Device.p1 && IMX6Device.I == null) {
                throw new AssertionError();
            }
            final Method i = IMX6Device.I;
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
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void screenshot(final View view, final int rotation, final String path) {
        try {
            ReflectUtil.invokeMethodSafely(IMX6Device.J, view, rotation, path);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void setStrokeColor(final int color) {
        try {
            final Method o = IMX6Device.O;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(o, receiver, color);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void setStrokeStyle(final int style) {
        try {
            final Method p = IMX6Device.P;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(p, receiver, style);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void setPainterStyle(final boolean antiAlias, final Paint.Style strokeStyle, final Paint.Join joinStyle, final Paint.Cap capStyle) {
        try {
            final Method r = IMX6Device.R;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(r, receiver, antiAlias, strokeStyle, joinStyle, capStyle);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void setStrokeWidth(final float width) {
        try {
            final Method q = IMX6Device.Q;
            final Object receiver = null;
            try {
                ReflectUtil.invokeMethodSafely(q, receiver, width);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void moveTo(final float x, final float y, final float width) {
        try {
            final Method m = IMX6Device.M;
            final Object receiver = null;
            try {
                final Object[] args = new Object[3];
                final Object[] array;
                (array = args)[0] = x;
                array[1] = y;
                args[2] = width;
                ReflectUtil.invokeMethodSafely(m, receiver, args);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void moveTo(final View view, final float x, final float y, final float width) {
        try {
            final Method n = IMX6Device.N;
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
        return IMX6Device.S != null;
    }
    
    @Override
    public boolean supportRegal() {
        final Method k;
        final Boolean b;
        return (k = IMX6Device.K) != null && (b = (Boolean)ReflectUtil.invokeMethodSafely(k, null, new Object[0])) != null && b;
    }
    
    @Override
    public void enableRegal(final boolean enable) {
        ReflectUtil.invokeMethodSafely(IMX6Device.L, null, enable);
    }
    
    @Override
    public void lineTo(final float x, final float y, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            final Method s = IMX6Device.S;
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
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void lineTo(final View view, final float x, final float y, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            final Method t = IMX6Device.T;
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
            final Method u = IMX6Device.U;
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
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
    }
    
    @Override
    public void quadTo(final View view, final float x, final float y, final UpdateMode mode) {
        final int a = this.a(mode);
        try {
            final Method v = IMX6Device.V;
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
            final Method w = IMX6Device.W;
            final Object receiver = null;
            try {
                return (float)ReflectUtil.invokeMethodSafely(w, receiver, new Object[0]);
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
    public float getTouchHeight() {
        try {
            final Method x = IMX6Device.X;
            final Object receiver = null;
            try {
                return (float)ReflectUtil.invokeMethodSafely(x, receiver, new Object[0]);
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
    public float getMaxTouchPressure() {
        try {
            final Method y = IMX6Device.Y;
            final Object receiver = null;
            try {
                return (float)ReflectUtil.invokeMethodSafely(y, receiver, new Object[0]);
            }
            catch (final Exception ex) {
                return 1024.0f;
            }
        }
        catch (final Exception ex2) {}
        return 1024.0f;
    }
    
    @Override
    public float getEpdWidth() {
        try {
            final Method z = IMX6Device.Z;
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
    public float getEpdHeight() {
        try {
            final Method a0 = IMX6Device.a0;
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
    public void mapToView(final View view, final float[] src, final float[] dst) {
        try {
            final Method b0 = IMX6Device.b0;
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
    public void mapToEpd(final View view, final float[] src, final float[] dst) {
        try {
            final Method c0 = IMX6Device.c0;
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
            final Method d0 = IMX6Device.d0;
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
            final Method e0 = IMX6Device.e0;
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
    public float startStroke(final float baseWidth, final float x, final float y, final float pressure, final float size, final float time) {
        try {
            final Method l0 = IMX6Device.l0;
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
                return (float)ReflectUtil.invokeMethodSafely(l0, receiver, args);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
                return baseWidth;
            }
        }
        catch (final Exception ex2) {}
        return baseWidth;
    }
    
    @Override
    public float addStrokePoint(final float baseWidth, final float x, final float y, final float pressure, final float size, final float time) {
        try {
            final Method m0 = IMX6Device.m0;
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
                return (float)ReflectUtil.invokeMethodSafely(m0, receiver, args);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
                return baseWidth;
            }
        }
        catch (final Exception ex2) {}
        return baseWidth;
    }
    
    @Override
    public float finishStroke(final float baseWidth, final float x, final float y, final float pressure, final float size, final float time) {
        try {
            final Method n0 = IMX6Device.n0;
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
                return (float)ReflectUtil.invokeMethodSafely(n0, receiver, args);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
                return baseWidth;
            }
        }
        catch (final Exception ex2) {}
        return baseWidth;
    }
    
    @Override
    public void enterScribbleMode(final View view) {
        try {
            ReflectUtil.invokeMethodSafely(IMX6Device.f0, view, 0);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void leaveScribbleMode(final View view) {
        try {
            ReflectUtil.invokeMethodSafely(IMX6Device.f0, view, 1);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void enablePost(final View view, final int enable) {
        try {
            ReflectUtil.invokeMethodSafely(IMX6Device.f0, view, enable);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void resetEpdPost() {
        try {
            ReflectUtil.invokeMethodSafely(IMX6Device.g0, null, new Object[0]);
        }
        catch (final Exception exception) {
            exception.printStackTrace();
        }
    }
    
    public boolean supportScreenHandWriting() {
        return IMX6Device.h0 != null;
    }
    
    @Override
    public void setScreenHandWritingPenState(final View view, final int penState) {
        try {
            ReflectUtil.invokeMethodSafely(IMX6Device.h0, view, penState);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
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
            ReflectUtil.invokeMethodSafely(IMX6Device.i0, view, view, array);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
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
            ReflectUtil.invokeMethodSafely(IMX6Device.j0, view, view, array);
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
        final Method k0 = IMX6Device.k0;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = apply;
        array[1] = value;
        ReflectUtil.invokeMethodSafely(k0, null, args);
    }
    
    @Override
    public boolean enableScreenUpdate(final View view, final boolean enable) {
        try {
            IMX6Device.H0.invoke(view, enable);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }
    
    @Override
    public boolean setDisplayScheme(final int scheme) {
        ReflectUtil.invokeMethodSafely(IMX6Device.I0, null, scheme);
        return true;
    }
    
    @Override
    public void waitForUpdateFinished() {
        ReflectUtil.invokeMethodSafely(IMX6Device.J0, null, new Object[0]);
    }
    
    @Override
    public void useBigPen(final boolean use) {
        this.a(null, IMX6Device.a1, use);
    }
    
    @Override
    public void stopTpd() {
        this.a(null, IMX6Device.b1, new Object[0]);
    }
    
    @Override
    public void startTpd() {
        this.a(null, IMX6Device.c1, new Object[0]);
    }
    
    @Override
    public void enableTpd(final boolean enable) {
        ReflectUtil.invokeMethodSafely(IMX6Device.d1, null, enable ? 1 : 0);
    }
    
    @Override
    public UpdateMode getViewDefaultUpdateMode(final View view) {
        final Integer n;
        if ((n = (Integer)ReflectUtil.invokeMethodSafely(IMX6Device.y0, view, new Object[0])) == null) {
            return UpdateMode.GU;
        }
        return this.b(n);
    }
    
    @Override
    public void resetViewUpdateMode(final View view) {
        ReflectUtil.invokeMethodSafely(IMX6Device.z0, view, new Object[0]);
    }
    
    @Override
    public boolean setViewDefaultUpdateMode(final View view, final UpdateMode mode) {
        return ReflectUtil.invokeMethodSafely(IMX6Device.A0, view, this.a(mode)) != null;
    }
    
    @Override
    public UpdateMode getSystemDefaultUpdateMode() {
        final Integer n;
        if ((n = (Integer)ReflectUtil.invokeMethodSafely(IMX6Device.B0, null, new Object[0])) == null) {
            return UpdateMode.GU;
        }
        return this.b(n);
    }
    
    @Override
    public boolean setSystemDefaultUpdateMode(final UpdateMode mode) {
        return ReflectUtil.invokeMethodSafely(IMX6Device.C0, null, this.a(mode)) != null;
    }
    
    public boolean setFirstDrawUpdateMode(final UpdateMode mode) {
        return ReflectUtil.invokeMethodSafely(IMX6Device.D0, null, this.a(mode)) != null;
    }
    
    @Override
    public boolean applySysScopeUpdate(final UpdateMode mode, final UpdateScheme scheme, final int count) {
        final Method e0 = IMX6Device.E0;
        final Object[] array = new Object[3];
        final Object[] array2;
        final Object[] args = array2 = array;
        args[0] = this.a(mode);
        array2[1] = this.a(scheme);
        array[2] = count;
        return ReflectUtil.invokeMethodSafely(e0, null, args) != null;
    }
    
    @Override
    public boolean clearSysScopeUpdate() {
        return ReflectUtil.invokeMethodSafely(IMX6Device.G0, null, new Object[0]) != null;
    }
    
    public boolean applyApplicationFastMode(final String application, final boolean enable, final boolean clear) {
        final Method f0 = IMX6Device.F0;
        final Object[] array = new Object[3];
        final Object[] array2;
        final Object[] args = array2 = array;
        args[0] = application;
        array2[1] = enable;
        array[2] = clear;
        return ReflectUtil.invokeMethodSafely(f0, null, args) != null;
    }
    
    @Override
    public int getFrontLightBrightnessMinimum(final Context context) {
        return 0;
    }
    
    @Override
    public int getFrontLightBrightnessMaximum(final Context context) {
        return 255;
    }
    
    @Override
    public boolean openFrontLight(final Context context) {
        final Boolean b;
        return (b = (Boolean)this.a(context, IMX6Device.K0, context)) != null && b;
    }
    
    @Override
    public boolean closeFrontLight(final Context context) {
        final Boolean b;
        return (b = (Boolean)this.a(context, IMX6Device.L0, context)) != null && b;
    }
    
    @Override
    public int getFrontLightDeviceValue(final Context context) {
        final Integer n;
        if ((n = (Integer)this.a(context, IMX6Device.M0, context)) == null) {
            return 0;
        }
        return n;
    }
    
    @Override
    public boolean setFrontLightDeviceValue(final Context context, final int value) {
        final Method n0 = IMX6Device.N0;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = value;
        return this.a(context, n0, args) != null;
    }
    
    @Override
    public boolean setNaturalLightConfigValue(final Context context, final int value) {
        final Method o0 = IMX6Device.O0;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = value;
        return this.a(context, o0, args) != null;
    }
    
    @Override
    public int getFrontLightConfigValue(final Context context) {
        return ReflectUtil.getSafely((Integer)this.a(context, IMX6Device.P0, context));
    }
    
    @Override
    public boolean setFrontLightConfigValue(final Context context, final int value) {
        final Method q0 = IMX6Device.Q0;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = context;
        array[1] = value;
        this.a(context, q0, args);
        return true;
    }
    
    @Override
    public List<Integer> getFrontLightValueList(final Context context) {
        return Arrays.asList(0, 8, 16, 24, 32, 40, 48, 56, 64, 72, 80, 88, 96, 104, 112, 120, 128, 136, 144, 152, 160);
    }
    
    @Override
    public void led(final Context context, final boolean on) {
        this.a(context, IMX6Device.R0, on);
    }
    
    @Override
    public boolean setLedColor(final String color, final int on) {
        final Method s0 = IMX6Device.S0;
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = color;
        array[1] = on;
        this.a(null, s0, args);
        return true;
    }
    
    @Override
    public void setVCom(final Context context, final int value, final String path) {
        final Method t0 = IMX6Device.T0;
        final Object[] args;
        final Object[] array = args = new Object[3];
        args[0] = context;
        array[1] = value;
        array[2] = path;
        this.a(context, t0, args);
    }
    
    @Override
    public int getVCom(final Context context, final String path) {
        final Integer n;
        if ((n = (Integer)this.a(context, IMX6Device.U0, path)) == null) {
            return 0;
        }
        return n;
    }
    
    @Override
    public void updateWaveform(final Context context, final String path, final String target) {
        final Method v0 = IMX6Device.V0;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = path;
        array[1] = target;
        this.a(context, v0, args);
    }
    
    @Override
    public String readSystemConfig(final Context context, final String key) {
        final Object a;
        if ((a = this.a(context, IMX6Device.W0, key)) != null && !a.equals("")) {
            return a.toString();
        }
        return "";
    }
    
    @Override
    public boolean saveSystemConfig(final Context context, final String key, final String value) {
        final Method x0 = IMX6Device.X0;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = key;
        array[1] = value;
        final Object a;
        return (a = this.a(context, x0, args)) != null && (boolean)a;
    }
    
    @Override
    public void updateMetadataDB(final Context context, final String path, final String target) {
        final Method y0 = IMX6Device.Y0;
        final Object[] args;
        final Object[] array = args = new Object[2];
        array[0] = path;
        array[1] = target;
        this.a(context, y0, args);
    }
    
    UpdateMode a(final EPDMode mode) {
        switch (IMX6Device$a.a[mode.ordinal()]) {
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
        int n = IMX6Device.v;
        switch (IMX6Device$a.b[mode.ordinal()]) {
            default: {
                if (IMX6Device.p1) {
                    break;
                }
                throw new AssertionError();
            }
            case 9: {
                if ((n = IMX6Device.A) != 0) {
                    break;
                }
                n = IMX6Device.u;
                break;
            }
            case 8: {
                if ((n = IMX6Device.z) != 0) {
                    break;
                }
                n = IMX6Device.u;
                break;
            }
            case 7: {
                n = IMX6Device.y;
                break;
            }
            case 6: {
                n = IMX6Device.x;
                break;
            }
            case 5: {
                n = IMX6Device.w;
                break;
            }
            case 4: {
                n = IMX6Device.v;
                break;
            }
            case 3: {
                n = IMX6Device.u;
                break;
            }
            case 1:
            case 2: {
                n = IMX6Device.t;
                break;
            }
        }
        return n;
    }
    
    @Override
    public void disableA2ForSpecificView(final View view) {
        ReflectUtil.invokeMethodSafely(IMX6Device.p0, view, new Object[0]);
    }
    
    @Override
    public void enableA2ForSpecificView(final View view) {
        ReflectUtil.invokeMethodSafely(IMX6Device.o0, view, new Object[0]);
    }
    
    @Override
    public void setWebViewContrastOptimize(final WebView view, final boolean enabled) {
        ReflectUtil.invokeMethodSafely(IMX6Device.q0, view, enabled);
    }
    
    @Override
    public void setQRShowConfig(final int orientation, final int startX, final int startY) {
        final Method r0 = IMX6Device.r0;
        final Object[] array = new Object[3];
        final Object[] array2;
        final Object[] args = array2 = array;
        args[0] = orientation;
        array2[1] = startX;
        array[2] = startY;
        ReflectUtil.invokeMethodSafely(r0, null, args);
    }
    
    @Override
    public void setInfoShowConfig(final int orientation, final int startX, final int startY) {
        final Method s0 = IMX6Device.s0;
        final Object[] array = new Object[3];
        final Object[] array2;
        final Object[] args = array2 = array;
        args[0] = orientation;
        array2[1] = startX;
        array[2] = startY;
        ReflectUtil.invokeMethodSafely(s0, null, args);
    }
    
    @Override
    public void gotoSleep(final Context context) {
        ReflectUtil.invokeMethodSafely(IMX6Device.Z0, context, System.currentTimeMillis());
    }
    
    @Override
    public boolean hasWifi(final Context context) {
        final Boolean b;
        return (b = (Boolean)this.a(context, IMX6Device.e1, new Object[0])) != null && b;
    }
    
    @Override
    public boolean inSystemFastMode() {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(IMX6Device.x0, null, new Object[0])) != null && b;
    }
    
    @Override
    public void applyGCOnce() {
        ReflectUtil.invokeMethodSafely(IMX6Device.f1, null, new Object[0]);
    }
    
    @Override
    public boolean isCTPDisableRegion(final Context context) {
        context.sendBroadcast(new Intent("app_reset_ctp_disable_region"));
        return false;
    }
    
    @Override
    public void appResetCTPDisableRegion(final Context context) {
        context.sendBroadcast(new Intent("app_reset_ctp_disable_region"));
    }
    
    @Override
    public void setAppCTPDisableRegion(final Context context, final int[] disableRegionArray, @Nullable final int[] excludeRegionArray) {
        final Intent intent2;
        final Intent intent = intent2 = new Intent("set_app_ctp_disable_region");
        intent.putExtra("args_region_to_disable", disableRegionArray);
        intent.putExtra("args_exclude_region", excludeRegionArray);
        context.sendBroadcast(intent2);
    }
    
    @Override
    public void setAppCTPDisableRegion(final Context context, final Rect[] disableRegions, @Nullable final Rect[] excludeRegions) {
        this.setAppCTPDisableRegion(context, this.convertRectArrayToIntArray(disableRegions), this.convertRectArrayToIntArray(excludeRegions));
    }
    
    @Override
    public int getColdLightConfigValue(final Context context) {
        return ReflectUtil.getSafely((Integer)this.a(context, IMX6Device.h1, context));
    }
    
    @Override
    public int getWarmLightConfigValue(final Context context) {
        return ReflectUtil.getSafely((Integer)this.a(context, IMX6Device.g1, context));
    }
}
