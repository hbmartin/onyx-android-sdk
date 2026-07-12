package com.onyx.android.sdk.data;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.utils.ActivityUtil;
import com.onyx.android.sdk.utils.ApplicationUtil;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.DPMUtils;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.LauncherAppsManager;
import com.onyx.android.sdk.utils.ResManager;
import com.onyx.android.sdk.utils.StringUtils;
import java.io.Serializable;
import java.util.List;

public class AppDataInfo extends AppBaseInfo implements Serializable, Cloneable {
   public String action;
   public String packageName;
   public String activityClassName;
   public String labelName;
   public String customName;
   public long lastUpdatedTime;
   public boolean isSystemApp;
   public boolean isCustomizedApp;
   public String iconDrawableName;
   @JSONField(deserialize = false, serialize = false)
   public Intent intent;
   public boolean isAutoFreeze = false;
   public boolean isEnable;
   public boolean isEACEnabled = true;
   public boolean preInstall;
   public int userId = 0;
   public boolean isWork = false;

   public AppDataInfo() {
      this.setType(AppBaseInfo.Type.APP);
   }

   private void a(Context context) {
      PackageInfo var2;
      if ((var2 = ApplicationUtil.getPackageInfoFromPackageName(context, this.packageName)) == null) {
         Debug.i(this.getClass(), "getPackageInfo fail for:" + this.packageName, new Object[0]);
      }

      ApplicationUtil.appDataFromApplicationInfo(var2, context.getPackageManager(), LauncherAppsManager.getInstance().getLaunchResolveInfoList(), this);
   }

   private void b(Context context) {
      this.intent = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setComponent(this.getComponentName());
      UserHandle var2;
      if ((var2 = this.getUserHandle()) == null) {
         Debug.w(this.getClass(), "can't get userHandle for:" + this.userId, new Object[0]);
      } else {
         List var3;
         if (CollectionUtils.isNonBlank(var3 = context.getPackageManager().queryIntentActivities(this.intent, 512))) {
            ApplicationUtil.appDataFromActivityInfoAsUser(context, this, ((ResolveInfo)var3.get(0)).activityInfo, var2);
         } else {
            LauncherActivityInfo var4;
            if ((var4 = LauncherAppsManager.getInstance().getActivityInfo(this.intent, var2)) == null) {
               Debug.w(this.getClass(), "can't parse work profile app:" + this.getComponentName(), new Object[0]);
            } else {
               ApplicationUtil.appDataFromActivityInfo(context, this, var4);
            }
         }
      }
   }

   public AppDataInfo clone() {
      try {
         return (AppDataInfo) super.clone();
      } catch (CloneNotSupportedException var2) {
         var2.printStackTrace();
         return null;
      }
   }

   @JSONField(serialize = false, deserialize = false)
   @Override
   public String getName() {
      String var1;
      if (StringUtils.isNullOrEmpty(var1 = ResManager.getStringSafely(StringUtils.safelyGetStr(this.labelName))) && StringUtils.isNotBlank(this.customName)) {
         var1 = ResManager.getStringSafely(this.customName);
      }

      String var2;
      if (StringUtils.isNullOrEmpty(var1)) {
         if (StringUtils.isNotBlank(this.labelName)) {
            var2 = this.labelName;
         } else {
            var2 = this.customName;
         }
      } else {
         var2 = var1;
      }

      return var2;
   }

   @TargetApi(25)
   @Override
   public void open(Activity activity) {
      Debug.d(this.getClass(), "open app with user:" + this.userId, new Object[0]);
      if (this.isWork) {
         LauncherAppsManager var10000 = LauncherAppsManager.getInstance();
         Intent var4 = this.intent;
         var10000.startActivity(var4, this.getUserHandle());
      } else {
         Intent var2 = this.intent;
         if (this.intent == null || !ActivityUtil.startActivitySafely(activity, var2, true, true)) {
            StringBuilder var5 = new StringBuilder().append("cached launchIntent for package: ");
            String var3;
            if (TextUtils.isEmpty(this.packageName)) {
               var3 = "empty";
            } else {
               var3 = this.packageName;
            }

            Debug.i(var5.append(var3).append(" missing, use pkg name launch.").toString());
            ActivityUtil.startActivitySafelyFromLauncher(activity, this.packageName);
         }
      }
   }

   @TargetApi(25)
   @Override
   public void parseInfo(Context context) {
      if (this.isCustomizedApp) {
         ApplicationUtil.updateCustomAppDataInfo(context, this);
      } else if (this.isWork) {
         this.b(context);
      } else {
         this.a(context);
      }
   }

   @Nullable
   @TargetApi(25)
   public ComponentName getComponentName() {
      String var1;
      if (StringUtils.isNullOrEmpty(var1 = this.getPackageName())) {
         this.activityClassName = var1 = ApplicationUtil.getAppInfoLaunchName(this);
      }

      return !StringUtils.isNullOrEmpty(var1) && !StringUtils.isNullOrEmpty(this.activityClassName) ? new ComponentName(var1, this.activityClassName) : null;
   }

   @Override
   public String getPackageName() {
      return this.packageName;
   }

   public String getActivityClassName() {
      return this.activityClassName;
   }

   @Override
   public String getLaunchName() {
      String var1 = this.getPackageName();
      if (this.preInstall) {
         return var1;
      }

      if (this.isCustomizedApp) {
         if (StringUtils.isNotBlank(this.action)) {
            var1 = this.action;
         } else if (StringUtils.isNotBlank(this.activityClassName)) {
            var1 = this.activityClassName;
         }
      } else if (this.isWork && StringUtils.isNotBlank(this.activityClassName)) {
         var1 = this.activityClassName;
      }

      return var1;
   }

   @JSONField(serialize = false, deserialize = false)
   public UserHandle getUserHandle() {
      return DPMUtils.getUserForId(ResManager.getAppContext(), this.userId);
   }

   @Override
   public String toString() {
      return "packageName:"
         + this.packageName
         + " activityClassName:"
         + this.activityClassName
         + " action:"
         + this.action
         + " labelName:"
         + this.labelName
         + " iconDrawableName:"
         + this.iconDrawableName
         + " customName:"
         + this.customName
         + " isSystemApp:"
         + this.isSystemApp
         + " isAutoFreeze:"
         + this.isAutoFreeze
         + " isEACEnabled:"
         + this.isEACEnabled
         + " isCustomizedApp:"
         + this.isCustomizedApp
         + " isEnable:"
         + this.isEnable
         + " userId:"
         + this.userId;
   }
}
