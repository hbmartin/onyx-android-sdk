package com.onyx.android.sdk.api.device.eac;

import android.os.Bundle;
import com.onyx.android.sdk.utils.ReflectUtil;
import java.lang.reflect.Method;
import java.util.List;

public class EACReflectUtils {
    public static final String EINK_HELPER_CLS_NAME = "android.onyx.optimization.EInkHelper";
    public static final Class<?> eInkHelperClass;
    public static final Method sMethodRemoveAppConfig;
    public static final Method sMethodRemoveAllAppConfig;
    public static final Method sMethodApplyAppConfigToService;
    public static final Method sMethodSaveDefaultAppConfigs;
    public static final Method sMethodSaveDefaultThemeConfig;
    public static final Method sMethodApplyRefreshConfigToAll;
    public static final Method sMethodGetAppConfigFromService;
    public static final Method sMethodSetEACServiceConfig;
    public static final Method sMethodGetDeviceExtraConfig;
    public static final Method sMethodApplyDefaultAppConfigToService;
    public static final Method sMethodSaveAsGlobalDefaultThemeConfig;
    public static final Method sMethodSaveAsGlobalDefaultThemeConfigForOnyxApp;
    public static final Method sMethodSetAccessibilityTouchEventDelay;
    public static final Method sMethodSetSystemRefreshMode;
    public static final Method sMethodGetAutoFreezeApps;
    public static final Method sMethodGetDisallowedAutoFreezeApps;
    public static final Method sMethodGetDisallowedEACApps;
    public static final Method sMethodGetDeviceConfig;
    public static final Method sMethodSetDeviceConfigVersionCode;

    @Deprecated
    public static final Method sMethodGetDeviceConfigVersionCode;
    public static final Method sMethodSetUserMode;
    public static final Method sMethodGetUserMode;
    public static final Method sMethodSetSupportMagicCode;
    public static final Method sMethodApplyAppFreezeConfigsToService;
    public static final Method sMethodApplyAppNetworkingConfigsToService;
    public static final Method sMethodApplyAppSelfStartingConfigsToService;
    public static final Method sMethodGetAppNetworkingConfigs;
    public static final Method sMethodGetAppSelfStartingConfigs;

    static {
        Class<?> clsClassForName = ReflectUtil.classForName(EINK_HELPER_CLS_NAME);
        eInkHelperClass = clsClassForName;
        sMethodRemoveAppConfig = ReflectUtil.getMethodSafely(clsClassForName, "removeAppConfigFromService", List.class);
        sMethodRemoveAllAppConfig = ReflectUtil.getMethodSafely(clsClassForName, "removeAllAppConfigFromService", new Class[0]);
        sMethodApplyAppConfigToService = ReflectUtil.getMethodSafely(clsClassForName, "applyAppConfigToService", List.class, Bundle.class);
        sMethodSaveDefaultAppConfigs = ReflectUtil.getMethodSafely(clsClassForName, "saveDefaultAppConfigs", List.class);
        sMethodSaveDefaultThemeConfig = ReflectUtil.getMethodSafely(clsClassForName, "saveDefaultThemeConfig", String.class);
        sMethodApplyRefreshConfigToAll = ReflectUtil.getMethodSafely(clsClassForName, "applyRefreshConfigToAll", String.class);
        sMethodGetAppConfigFromService = ReflectUtil.getMethodSafely(clsClassForName, "getAppConfigFromService", List.class);
        Class cls = Boolean.TYPE;
        sMethodSetEACServiceConfig = ReflectUtil.getMethodSafely(clsClassForName, "setEACServiceConfig", cls, cls);
        sMethodGetDeviceExtraConfig = ReflectUtil.getMethodSafely(clsClassForName, "getDeviceExtraConfigString", new Class[0]);
        sMethodApplyDefaultAppConfigToService = ReflectUtil.getMethodSafely(clsClassForName, "applyDefaultAppConfigToService", String.class);
        sMethodSaveAsGlobalDefaultThemeConfig = ReflectUtil.getMethodSafely(clsClassForName, "saveAsGlobalDefaultThemeConfig", String.class);
        sMethodSaveAsGlobalDefaultThemeConfigForOnyxApp = ReflectUtil.getMethodSafely(clsClassForName, "saveAsGlobalDefaultThemeConfigForOnyxApp", String.class);
        Class cls2 = Integer.TYPE;
        sMethodSetAccessibilityTouchEventDelay = ReflectUtil.getMethodSafely(clsClassForName, "setAccessibilityTouchEventDelay", cls2);
        sMethodSetSystemRefreshMode = ReflectUtil.getMethodSafely(clsClassForName, "setSystemRefreshMode", cls2);
        sMethodGetAutoFreezeApps = ReflectUtil.getMethodSafely(clsClassForName, "getAutoFreezeApps", new Class[0]);
        sMethodGetDisallowedAutoFreezeApps = ReflectUtil.getMethodSafely(clsClassForName, "getDisallowedAutoFreezeApps", new Class[0]);
        sMethodGetDisallowedEACApps = ReflectUtil.getMethodSafely(clsClassForName, "getDisallowedEACApps", new Class[0]);
        sMethodGetDeviceConfig = ReflectUtil.getMethodSafely(clsClassForName, "getDeviceConfig", List.class);
        sMethodSetDeviceConfigVersionCode = ReflectUtil.getMethodSafely(clsClassForName, "setDeviceConfigVersionCode", cls2);
        sMethodGetDeviceConfigVersionCode = ReflectUtil.getMethodSafely(clsClassForName, "getDeviceConfigVersionCode", new Class[0]);
        sMethodSetUserMode = ReflectUtil.getMethodSafely(clsClassForName, "setUserMode", cls2);
        sMethodGetUserMode = ReflectUtil.getMethodSafely(clsClassForName, "getUserMode", new Class[0]);
        sMethodSetSupportMagicCode = ReflectUtil.getMethodSafely(clsClassForName, "setSupportMagicCode", cls);
        sMethodApplyAppFreezeConfigsToService = ReflectUtil.getMethodSafely(clsClassForName, "applyAppFreezeConfigsToService", List.class);
        sMethodApplyAppNetworkingConfigsToService = ReflectUtil.getMethodSafely(clsClassForName, "applyAppNetworkingConfigsToService", List.class);
        sMethodApplyAppSelfStartingConfigsToService = ReflectUtil.getMethodSafely(clsClassForName, "applyAppSelfStartingConfigsToService", List.class);
        sMethodGetAppNetworkingConfigs = ReflectUtil.getMethodSafely(clsClassForName, "getAppNetworkingConfigs", new Class[0]);
        sMethodGetAppSelfStartingConfigs = ReflectUtil.getMethodSafely(clsClassForName, "getAppSelfStartingConfigs", new Class[0]);
    }
}
