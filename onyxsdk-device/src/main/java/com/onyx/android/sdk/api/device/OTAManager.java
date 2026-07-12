package com.onyx.android.sdk.api.device;

import android.content.Context;
import com.onyx.android.sdk.api.data.model.FirmwareBean;
import com.onyx.android.sdk.api.utils.LocaleUtils;
import com.onyx.android.sdk.api.utils.NetworkUtil;
import java.util.Locale;

public class OTAManager {
    public static final String TAG = "OTAManager";

    public static FirmwareBean getCurrentFirmware(Context context) {
        FirmwareBean firmwareBeanCurrentFirmware = FirmwareBean.currentFirmware();
        firmwareBeanCurrentFirmware.lang = LocaleUtils.getLocaleByLang(Locale.getDefault().getLanguage()).toString();
        firmwareBeanCurrentFirmware.deviceMAC = NetworkUtil.getMacAddress(context);
        return firmwareBeanCurrentFirmware;
    }
}
