// 
// 

package com.onyx.android.sdk.api.device.eac;

import android.content.Context;
import com.alibaba.fastjson2.JSONObject;
import android.text.TextUtils;
import com.onyx.android.sdk.api.utils.StringUtils;
import com.onyx.android.sdk.utils.ReflectUtil;
import java.util.Collections;
import com.onyx.android.sdk.device.eac.v1.EACManagerV1;
import com.onyx.android.sdk.device.eac.v2.EACManagerV2;
import com.onyx.android.sdk.device.eac.v3.EACManagerV3;
import com.onyx.android.sdk.device.eac.BaseEACManager;
import com.onyx.android.sdk.utils.Singleton;

public class SimpleEACManage
{
    private static final String JSON_VERSION = "jsonVersion";
    private static final Singleton<SimpleEACManage> managerSingleton;
    private BaseEACManager baseEACManager;

    @SuppressWarnings("rawtypes")
    static final class a extends Singleton {
        protected SimpleEACManage a() {
            return new SimpleEACManage((a) null);
        }

        @Override
        protected Object create() {
            return a();
        }
    }
    
    public static SimpleEACManage getInstance() {
        return SimpleEACManage.managerSingleton.get();
    }
    
    private SimpleEACManage() {
        this.initEacManager();
    }

    SimpleEACManage(final a ignored) {
        this();
    }
    
    private void initEacManager() {
        final int deviceConfigVersionCode;
        if ((deviceConfigVersionCode = this.getDeviceConfigVersionCode()) >= 107) {
            this.baseEACManager = new EACManagerV3();
        }
        else if (deviceConfigVersionCode >= 100) {
            this.baseEACManager = new EACManagerV2();
        }
        else {
            this.baseEACManager = new EACManagerV1();
        }
    }
    
    private int getDeviceConfigVersionCode() {
        final String s;
        if (StringUtils.isNullOrEmpty(s = (String)ReflectUtil.invokeMethodSafely(EACReflectUtils.sMethodGetDeviceConfig, null, Collections.emptyList()))) {
            return this.getOldDeviceConfigVersionCode();
        }
        final String s2 = s;
        int intValue = 0;
        if (!TextUtils.isEmpty((CharSequence)s2)) {
            try {
                final JSONObject object;
                if ((object = JSONObject.parseObject(s)) != null && object.containsKey("jsonVersion")) {
                    intValue = object.getInteger("jsonVersion");
                }
                else {
                    intValue = 0;
                }
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        return intValue;
    }
    
    private int getOldDeviceConfigVersionCode() {
        return ReflectUtil.getSafely((Integer)ReflectUtil.invokeMethodSafely(EACReflectUtils.sMethodGetDeviceConfigVersionCode, null, new Object[0]));
    }
    
    static {
        managerSingleton = new a();
    }
    
    public boolean isHookEpdc(final String packageName) {
        return this.baseEACManager.isHookEpdc(packageName);
    }
    
    public boolean isAppEACEnabled(final String packageName) {
        return this.baseEACManager.isAppEACEnabled(packageName);
    }
    
    public void setAppEACEnable(final Context appContext, final boolean enable) {
        this.baseEACManager.setAppEACEnable(appContext, enable);
    }
    
    public boolean isSupportEAC(final String pkgName) {
        return this.baseEACManager.isSupportEAC(pkgName);
    }
    
    public void setSupportEAC(final Context appContext, final boolean support) {
        this.baseEACManager.setSupportEAC(appContext, support);
    }
    
    public boolean isEACRefreshConfigEnable(final String pkgName) {
        return this.baseEACManager.isEACRefreshConfigEnable(pkgName);
    }
    
    public void setEACRefreshConfigEnable(final Context appContext, final boolean enable) {
        this.baseEACManager.setEACRefreshConfigEnable(appContext, enable);
    }
    
    public boolean isFollowSystemRotation(final String pkgName) {
        return this.baseEACManager.isRotationConfigEnable(pkgName);
    }
    
    public void setFollowSystemRotation(final Context appContext, final boolean enable) {
        this.baseEACManager.setRotationConfigEnable(appContext, enable);
    }
}
