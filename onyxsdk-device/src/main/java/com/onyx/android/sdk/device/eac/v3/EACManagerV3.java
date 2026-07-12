package com.onyx.android.sdk.device.eac.v3;

import android.content.Context;
import android.os.Bundle;
import com.onyx.android.sdk.api.device.eac.EACReflectUtils;
import com.onyx.android.sdk.device.eac.EACConstants;
import com.onyx.android.sdk.device.eac.v2.EACManagerV2;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.ReflectUtil;
import java.util.Collections;

public class EACManagerV3 extends EACManagerV2 {
    private Bundle a() {
        Bundle bundle = new Bundle();
        bundle.putInt(EACConstants.ARGS_OPERATION_FLAG, 0);
        return bundle;
    }

    @Override // com.onyx.android.sdk.device.eac.v2.EACManagerV2
    protected void applyAppConfigToService(Context context, String config) {
        if (ReflectUtil.invokeMethodSafely(EACReflectUtils.sMethodApplyAppConfigToService, null, Collections.singletonList(config), a()) == null) {
            Debug.e(getClass(), "applyAppConfigToService config failed", new Object[0]);
        }
    }
}
