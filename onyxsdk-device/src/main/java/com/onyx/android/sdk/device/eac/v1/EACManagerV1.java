package com.onyx.android.sdk.device.eac.v1;

import android.content.Context;
import androidx.annotation.WorkerThread;
import com.alibaba.fastjson2.JSON;
import com.onyx.android.sdk.api.device.eac.EACReflectUtils;
import com.onyx.android.sdk.api.device.eac.SimpleEACManage;
import com.onyx.android.sdk.api.device.eac.SimplyEACDeviceConfig;
import com.onyx.android.sdk.device.eac.BaseEACManager;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.ReflectUtil;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

public class EACManagerV1 extends BaseEACManager {
    private static final Method b;
    private static final Method c;
    private static final Method d;
    private SimplyEACDeviceConfig a;

    @WorkerThread
    private void a(Context appContext) {
        ReflectUtil.invokeMethodSafely(b, null, Collections.singletonList(appContext.getPackageName()));
    }

    @WorkerThread
    private void b(Context appContext) {
        ReflectUtil.invokeMethodSafely(c, null, Collections.singletonList(appContext.getPackageName()));
    }

    static {
        Class<?> cls = EACReflectUtils.eInkHelperClass;
        b = ReflectUtil.getMethodSafely(cls, "addEACWhiteApp", List.class);
        c = ReflectUtil.getMethodSafely(cls, "removeEACWhiteApp", List.class);
        d = ReflectUtil.getMethodSafely(cls, "getConfigFromService", new Class[0]);
    }

    @WorkerThread
    protected void fetchConfigFromService() {
        Object objInvokeMethodSafely = ReflectUtil.invokeMethodSafely(d, null, new Object[0]);
        if (!(objInvokeMethodSafely instanceof String)) {
            Debug.e((Class<?>) SimpleEACManage.class, "fetchConfigFromService: invalid config result", new Object[0]);
            this.a = SimplyEACDeviceConfig.createDummyConfig();
            return;
        }
        try {
            this.a = (SimplyEACDeviceConfig) JSON.parseObject((String) objInvokeMethodSafely, SimplyEACDeviceConfig.class);
        } catch (Exception error) {
            error.printStackTrace();
        }
        if (this.a == null) {
            Debug.e((Class<?>) SimpleEACManage.class, "empty remote device config", new Object[0]);
            this.a = SimplyEACDeviceConfig.createDummyConfig();
        }
    }

    @Override // com.onyx.android.sdk.device.eac.BaseEACManager
    public boolean isHookEpdc(String pkgName) {
        return isSupportEAC(pkgName);
    }

    @Override // com.onyx.android.sdk.device.eac.BaseEACManager
    public boolean isAppEACEnabled(String pkgName) {
        fetchConfigFromService();
        return !this.a.isEACWhiteApp(pkgName) && this.a.isAppEACEnable(pkgName);
    }

    @Override // com.onyx.android.sdk.device.eac.BaseEACManager
    public void setAppEACEnable(Context context, boolean enable) {
    }

    @Override // com.onyx.android.sdk.device.eac.BaseEACManager
    public boolean isSupportEAC(String pkgName) {
        fetchConfigFromService();
        return !this.a.isEACWhiteApp(pkgName);
    }

    @Override // com.onyx.android.sdk.device.eac.BaseEACManager
    public void setSupportEAC(Context context, boolean support) {
        if (support) {
            b(context);
        } else {
            a(context);
        }
    }

    @Override // com.onyx.android.sdk.device.eac.BaseEACManager
    public boolean isEACRefreshConfigEnable(String pkgName) {
        return true;
    }

    @Override // com.onyx.android.sdk.device.eac.BaseEACManager
    public void setEACRefreshConfigEnable(Context context, boolean enable) {
    }

    @Override // com.onyx.android.sdk.device.eac.BaseEACManager
    public boolean isRotationConfigEnable(String pkgName) {
        return false;
    }

    @Override // com.onyx.android.sdk.device.eac.BaseEACManager
    public void setRotationConfigEnable(Context context, boolean enable) {
    }
}
