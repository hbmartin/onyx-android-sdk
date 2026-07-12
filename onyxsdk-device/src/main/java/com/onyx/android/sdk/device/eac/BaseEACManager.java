package com.onyx.android.sdk.device.eac;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import androidx.annotation.WorkerThread;

public abstract class BaseEACManager {
    @WorkerThread
    public abstract boolean isHookEpdc(String str);

    @WorkerThread
    public abstract boolean isAppEACEnabled(String str);

    @WorkerThread
    public abstract void setAppEACEnable(Context context, boolean z);

    @WorkerThread
    public abstract boolean isSupportEAC(String str);

    @WorkerThread
    public abstract void setSupportEAC(Context context, boolean z);

    @WorkerThread
    public abstract boolean isEACRefreshConfigEnable(String str);

    @WorkerThread
    public abstract void setEACRefreshConfigEnable(Context context, boolean z);

    @WorkerThread
    public abstract boolean isRotationConfigEnable(String str);

    @WorkerThread
    public abstract void setRotationConfigEnable(Context context, boolean z);

    protected void sendOECConfigChanged(Context context, boolean isInstantUpdate, int operationFlag) {
        Intent intent = new Intent(EACConstants.OEC_CONFIG_CHANGE_ACTION);
        intent.putExtra(EACConstants.ARGS_PKG, context.getPackageName());
        String className = context instanceof Activity ? ((Activity) context).getComponentName().getClassName() : null;
        if (!TextUtils.isEmpty(className)) {
            intent.putExtra(EACConstants.ARGS_CLASS, className);
        }
        intent.putExtra(EACConstants.ARGS_IS_INSTANT_UPDATE, isInstantUpdate);
        intent.putExtra(EACConstants.ARGS_OPERATION_FLAG, operationFlag);
        context.getApplicationContext().sendBroadcast(intent);
    }
}
