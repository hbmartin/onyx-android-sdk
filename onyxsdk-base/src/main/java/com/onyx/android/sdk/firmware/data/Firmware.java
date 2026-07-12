package com.onyx.android.sdk.firmware.data;

import android.os.Build;
import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.api.utils.FirmwareUtils;
import com.onyx.android.sdk.device.Device;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.StringUtils;
import com.onyx.android.sdk.utils.SystemPropertiesUtil;
import java.util.Date;
import java.util.List;

public class Firmware {
   public static final String BUILD_NUMBER_TAG = "buildNumber";
   public static final String RELEASE_TAG = "release";
   public static final String TESTING_TAG = "test";
   private static final String a = "ro.onyx.sub_model";
   public static final String OBJECT_ID = "_id";
   public long id = 0L;
   public String guid;
   @JSONField(name = "_id")
   public String idString;
   public Date createdAt;
   public Date updatedAt;
   public int buildNumber;
   public String buildType;
   public String fingerprint;
   public String buildDisplayId;
   public String lang;
   public String model;
   public String brand;
   public String fwType;
   public String deviceMAC;
   public String md5;
   public String submodel;
   public long size;
   public int widthPixels;
   public int heightPixels;
   public List<String> changeList;
   public List<String> downloadUrlList;

   public static Firmware currentFirmware() {
      Firmware var10000 = new Firmware();
      var10000.model = Build.MODEL;
      var10000.fingerprint = SystemPropertiesUtil.getFingerprint();
      var10000.submodel = Device.currentDevice().getSystemProperties("ro.onyx.sub_model");
      var10000.updateReleaseBuildParameters();
      return var10000;
   }

   public void updateReleaseBuildParameters() {
      this.buildNumber = FirmwareUtils.getBuildIdFromFingerprint(this.fingerprint);
      this.buildType = FirmwareUtils.getBuildTypeFromFingerprint(this.fingerprint);
      this.fwType = "release";
   }

   public void updateTestingBuildParameters() {
      this.buildNumber = FirmwareUtils.getBuildIdFromFingerprint(this.fingerprint);
      this.buildType = FirmwareUtils.getBuildTypeFromFingerprint(this.fingerprint);
      this.fwType = "test";
   }

   @JSONField(serialize = false)
   public String getChangeLog() {
      return !CollectionUtils.isNullOrEmpty(this.changeList) ? StringUtils.join(this.changeList, "\n") : "";
   }

   public String getFingerprint() {
      return this.fingerprint;
   }

   @JSONField(serialize = false)
   public String getUrl() {
      return this.downloadUrlList.size() > 0 ? this.downloadUrlList.get(0) : null;
   }
}
