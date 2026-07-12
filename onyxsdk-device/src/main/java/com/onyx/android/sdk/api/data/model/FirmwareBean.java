package com.onyx.android.sdk.api.data.model;

import android.os.Build;
import com.onyx.android.sdk.api.utils.FirmwareUtils;
import com.onyx.android.sdk.utils.SystemPropertiesUtil;

public class FirmwareBean {
    public static final String RELEASE_TAG = "release";
    public static final String TESTING_TAG = "test";
    public int buildNumber;
    public String buildType;
    public String fingerprint;
    public String lang;
    public String model;
    public String fwType;
    public String deviceMAC;

    public static FirmwareBean currentFirmware() {
        FirmwareBean firmwareBean = new FirmwareBean();
        firmwareBean.model = Build.MODEL;
        firmwareBean.fingerprint = SystemPropertiesUtil.getFingerprint();
        firmwareBean.updateReleaseBuildParameters();
        return firmwareBean;
    }

    public void updateReleaseBuildParameters() {
        this.buildNumber = FirmwareUtils.getBuildIdFromFingerprint(this.fingerprint);
        this.buildType = FirmwareUtils.getBuildTypeFromFingerprint(this.fingerprint);
        this.fwType = "release";
    }

    public void updateTestingBuildParameters() {
        this.buildNumber = FirmwareUtils.getBuildIdFromFingerprint(this.fingerprint);
        this.buildType = FirmwareUtils.getBuildTypeFromFingerprint(this.fingerprint);
        this.fwType = TESTING_TAG;
    }

    public String getFingerprint() {
        return this.fingerprint;
    }
}
