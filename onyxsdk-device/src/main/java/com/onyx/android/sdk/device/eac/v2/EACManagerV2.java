// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.device.eac.v2;

import android.content.Context;
import org.json.JSONObject;
import org.json.JSONException;
import androidx.annotation.WorkerThread;
import com.onyx.android.sdk.utils.Debug;
import java.util.Collection;
import com.onyx.android.sdk.api.utils.CollectionUtils;
import com.onyx.android.sdk.utils.ReflectUtil;
import java.util.Collections;
import com.onyx.android.sdk.api.device.eac.EACReflectUtils;
import java.util.List;
import java.lang.reflect.Method;
import com.onyx.android.sdk.device.eac.BaseEACManager;

public class EACManagerV2 extends BaseEACManager
{
    private static final Method a;
    
    private String c(final String pkgName) {
        final List list;
        if (CollectionUtils.isNullOrEmpty(list = (List)ReflectUtil.invokeMethodSafely(EACReflectUtils.sMethodGetAppConfigFromService, null, Collections.singletonList(pkgName)))) {
            Debug.e(this.getClass(), "getAppConfigFromService: invalid config result", new Object[0]);
            return "";
        }
        return (String)list.get(0);
    }
    
    @WorkerThread
    private void a(final String appConfig) {
        if (ReflectUtil.invokeMethodSafely(EACManagerV2.a, null, Collections.singletonList(appConfig)) == null) {
            Debug.e(this.getClass(), "applyAppConfigToService config failed", new Object[0]);
        }
    }
    
    @WorkerThread
    private void b(final String appConfig) {
        if (ReflectUtil.invokeMethodSafely(EACReflectUtils.sMethodApplyRefreshConfigToAll, null, appConfig) == null) {
            Debug.e(this.getClass(), "applyRefreshConfigToAll config failed", new Object[0]);
        }
    }
    
    private String d(String appConfig, final boolean support) {
        try {
            final JSONObject jsonObject = new JSONObject(appConfig);
            jsonObject.put("supportEAC", support);
            try {
                appConfig = jsonObject.toString();
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
        return appConfig;
    }
    
    private String c(String appConfig, final boolean enable) {
        try {
            try {
                final JSONObject appConfig2 = new JSONObject(appConfig);
                this.b(appConfig2).put("enable", enable);
                try {
                    appConfig = appConfig2.toString();
                }
                catch (final Exception ex) {
                    ex.printStackTrace();
                }
            }
            catch (final Exception ex2) {}
        }
        catch (final Exception ex3) {}
        return appConfig;
    }
    
    private String a(String appConfig, final boolean support) {
        try {
            final JSONObject jsonObject = new JSONObject(appConfig);
            jsonObject.put("enable", support);
            try {
                appConfig = jsonObject.toString();
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
        return appConfig;
    }
    
    private String b(String appConfig, final boolean enable) {
        try {
            try {
                final JSONObject appConfig2 = new JSONObject(appConfig);
                this.a(appConfig2).put("enable", enable);
                try {
                    appConfig = appConfig2.toString();
                }
                catch (final Exception ex) {
                    ex.printStackTrace();
                }
            }
            catch (final Exception ex2) {}
        }
        catch (final Exception ex3) {}
        return appConfig;
    }
    
    private JSONObject a(final JSONObject appConfig) throws JSONException {
        return appConfig.getJSONObject("globalActivityConfig").getJSONObject("refreshConfig");
    }
    
    private JSONObject b(final JSONObject appConfig) throws JSONException {
        return appConfig.getJSONObject("rotationConfig");
    }
    
    private boolean a(final String key, final String config) {
        boolean boolean1 = false;
        try {
            try {
                boolean1 = new JSONObject(config).getBoolean(key);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
        return boolean1;
    }
    
    static {
        a = ReflectUtil.getMethodSafely(EACReflectUtils.eInkHelperClass, "applyAppConfigToService", List.class);
    }
    
    @Override
    public boolean isHookEpdc(final String pkgName) {
        return this.isAppEACEnabled(pkgName);
    }
    
    @Override
    public boolean isAppEACEnabled(String pkgName) {
        pkgName = this.c(pkgName);
        return this.a("supportEAC", pkgName) && this.a("enable", pkgName);
    }
    
    @Override
    public void setAppEACEnable(final Context context, final boolean enable) {
        this.applyAppConfigToService(context, this.a(this.c(context.getPackageName()), enable));
    }
    
    @Override
    public void setSupportEAC(final Context context, final boolean support) {
        this.applyAppConfigToService(context, this.d(this.c(context.getPackageName()), support));
    }
    
    @Override
    public boolean isSupportEAC(final String pkgName) {
        return this.a("supportEAC", this.c(pkgName));
    }
    
    @Override
    public boolean isEACRefreshConfigEnable(String pkgName) {
        if (!this.isAppEACEnabled(pkgName)) {
            return false;
        }
        final String c = this.c(pkgName);
        boolean enabled = true;
        try {
            enabled = this.a(new JSONObject(c)).getBoolean("enable");
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
        return enabled;
    }
    
    @Override
    public void setEACRefreshConfigEnable(final Context context, final boolean enable) {
        this.applyAppConfigToService(context, this.b(this.c(context.getPackageName()), enable));
    }
    
    @Override
    public boolean isRotationConfigEnable(String pkgName) {
        pkgName = this.c(pkgName);
        if (this.a("supportEAC", pkgName) && this.a("enable", pkgName)) {
            boolean boolean1 = true;
            try {
                boolean1 = this.b(new JSONObject(pkgName)).getBoolean("enable");
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
            return boolean1;
        }
        return false;
    }
    
    @Override
    public void setRotationConfigEnable(final Context context, final boolean enable) {
        this.applyAppConfigToService(context, this.c(this.c(context.getPackageName()), enable));
    }
    
    protected void applyAppConfigToService(final Context context, final String config) {
        this.a(config);
        this.sendOECConfigChanged(context, true, 0);
    }
}
