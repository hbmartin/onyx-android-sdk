// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import android.content.pm.IPackageDeleteObserver;
import java.util.Arrays;
import android.os.Parcelable;
import android.content.Intent;
import io.reactivex.functions.Predicate;
import android.content.pm.PackageManager;
import com.onyx.android.sdk.data.dpm.PackageDeleteObserver;
import android.content.pm.PackageInfo;
import java.util.Objects;
import android.os.PersistableBundle;
import android.content.ComponentName;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.onyx.android.sdk.data.UserInfo;
import android.os.UserHandle;
import android.os.Process;
import android.content.pm.PackageInstaller;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.app.admin.DevicePolicyManager;
import android.os.UserManager;
import android.content.Context;
import java.lang.reflect.Method;
import androidx.annotation.RequiresApi;

public class DPMUtils
{
    private static final String a = "DPMUtils";
    @RequiresApi(api = 17)
    private static Class<?> b;
    private static Class<?> c;
    @RequiresApi(api = 21)
    private static Class<?> d;
    private static Class<?> e;
    private static Class<?> f;
    @RequiresApi(api = 17)
    private static Method g;
    @RequiresApi(api = 21)
    private static Method h;
    @RequiresApi(api = 21)
    private static Method i;
    @RequiresApi(api = 21)
    private static Method j;
    @RequiresApi(api = 21)
    private static Method k;
    @RequiresApi(api = 21)
    private static Method l;
    @RequiresApi(api = 21)
    private static Method m;
    @RequiresApi(api = 21)
    private static Method n;
    
    @RequiresApi(api = 17)
    public static UserManager getUserManager(final Context context) {
        return (UserManager)context.getSystemService("user");
    }
    
    public static DevicePolicyManager getDevicePolicyManager(final Context context) {
        return (DevicePolicyManager)context.getSystemService("device_policy");
    }
    
    @RequiresApi(api = 17)
    private static List a(final Context context) {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(DPMUtils.g, (Object)getUserManager(context), new Object[0])) instanceof List) {
            return (List)invokeMethodSafely;
        }
        return Collections.EMPTY_LIST;
    }
    
    @RequiresApi(api = 17)
    public static List<String> getAllUserName(final Context context) {
        final ArrayList list = new ArrayList();
        final List a;
        if (CollectionUtils.isNonBlank(a = a(context))) {
            final Iterator iterator = a.iterator();
            while (iterator.hasNext()) {
                list.add(ReflectUtil.getDeclareStringFieldSafely((Class)DPMUtils.c, iterator.next(), "name"));
            }
        }
        return list;
    }
    
    @RequiresApi(api = 17)
    public static List<Integer> getAllUserID(final Context context) {
        final ArrayList list = new ArrayList();
        final List a;
        if (CollectionUtils.isNonBlank(a = a(context))) {
            final Iterator iterator = a.iterator();
            while (iterator.hasNext()) {
                list.add(ReflectUtil.getDeclareIntFieldSafely((Class)DPMUtils.c, iterator.next(), "id"));
            }
        }
        return list;
    }
    
    @RequiresApi(api = 17)
    public static List<Integer> getAllUserSerialNumber(final Context context) {
        final ArrayList list = new ArrayList();
        final List a;
        if (CollectionUtils.isNonBlank(a = a(context))) {
            final Iterator iterator = a.iterator();
            while (iterator.hasNext()) {
                list.add(ReflectUtil.getDeclareIntFieldSafely((Class)DPMUtils.c, iterator.next(), "serialNumber"));
            }
        }
        return list;
    }
    
    @RequiresApi(api = 21)
    public static int createSessionForTargetUser(final PackageInstaller packageInstaller, final PackageInstaller.SessionParams sessionParams, final int userID) {
        int intValue = -1;
        final Method h;
        if ((h = DPMUtils.h) == null) {
            try {
                final String a = DPMUtils.a;
                final String s = "create user package install session by reflect";
                try {
                    Debug.d(a, s, new Object[0]);
                    ReflectUtil.setDeclareFieldSafely((Class)PackageInstaller.class, (Object)packageInstaller, "mUserId", (Object)userID);
                    try {
                        return packageInstaller.createSession(sessionParams);
                    }
                    catch (final Exception ex) {
                        ex.printStackTrace();
                        return intValue;
                    }
                }
                catch (final Exception ex2) {}
            }
            catch (final Exception ex3) {}
        }
        final Method method = h;
        final Object[] array2;
        final Object[] array = array2 = new Object[2];
        array2[0] = sessionParams;
        array[1] = userID;
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(method, (Object)packageInstaller, array2)) instanceof Integer) {
            intValue = (int)invokeMethodSafely;
        }
        return intValue;
    }
    
    @RequiresApi(api = 17)
    public static int getMyUserId(final Context context) {
        return getIdForUser(context, Process.myUserHandle());
    }
    
    @RequiresApi(api = 17)
    public static int getIdForUser(final Context context, final UserHandle userHandle) {
        final List<UserInfo> userInfoList = getUserInfoList(context);
        final int serialNumberForUser = getSerialNumberForUser(context, userHandle);
        final Iterator<UserInfo> iterator = userInfoList.iterator();
        while (iterator.hasNext()) {
            final UserInfo userInfo;
            if ((userInfo = iterator.next()).serialNumber == serialNumberForUser) {
                return userInfo.userId;
            }
        }
        return serialNumberForUser;
    }
    
    @Nullable
    public static UserHandle getUserForId(final Context context, final int userId) {
        final Iterator<UserInfo> iterator = getUserInfoList(context).iterator();
        while (iterator.hasNext()) {
            final UserInfo userInfo;
            if ((userInfo = iterator.next()).userId == userId) {
                return getUserForSerialNumber(context, userInfo.serialNumber);
            }
        }
        return null;
    }
    
    @RequiresApi(api = 17)
    public static int getSerialNumberForUser(final Context context, final UserHandle userHandle) {
        return (int)getUserManager(context).getSerialNumberForUser(userHandle);
    }
    
    @RequiresApi(api = 17)
    public static UserHandle getUserForSerialNumber(final Context context, final long serialNumber) {
        return getUserManager(context).getUserForSerialNumber(serialNumber);
    }
    
    public static boolean isDeviceOwner(final Context context) {
        return !CompatibilityUtil.apiLevelCheck(21) || getDevicePolicyManager(context).isDeviceOwnerApp(context.getPackageName());
    }
    
    @Nullable
    public static UserHandle createUser(@NonNull final Context context, @NonNull final String name, @NonNull final ComponentName adminComponent) {
        if (CompatibilityUtil.apiLevelCheck(24)) {
            try {
                return getDevicePolicyManager(context).createAndManageUser(adminComponent, name, adminComponent, new PersistableBundle(), 17);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return null;
    }
    
    @Deprecated
    public static boolean startUser(final Context context, int userId) {
        final ShellUtils.CommandResult execCommand;
        if ((execCommand = ShellUtils.execCommand("am start-user " + userId, false)).result == 0) {
            userId = 1;
        }
        else {
            userId = 0;
        }
        if (userId == 0) {
            final ShellUtils.CommandResult commandResult = execCommand;
            final String a = DPMUtils.a;
            String s;
            if (StringUtils.isNotBlank(commandResult.errorMsg)) {
                s = execCommand.errorMsg;
            }
            else {
                s = execCommand.successMsg;
            }
            Debug.e(a, s, new Object[0]);
        }
        return userId != 0;
    }
    
    @RequiresApi(api = 21)
    public static void startUserInBackground(final Context context, final ComponentName admin, final int userId) {
        ReflectUtil.invokeMethodSafely(DPMUtils.k, (Object)getDevicePolicyManager(context), new Object[] { admin, getUserForId(context, userId) });
    }
    
    public static boolean switchUser(@NonNull final Context context, final long serialNum, @NonNull final ComponentName adminComponent) {
        if (CompatibilityUtil.apiLevelCheck(24)) {
            try {
                return getDevicePolicyManager(context).switchUser(adminComponent, getUserForSerialNumber(context, serialNum));
            }
            catch (final Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return false;
    }
    
    public static boolean switchUser(@NonNull final Context context, final UserHandle userHandle, @NonNull final ComponentName adminComponent) {
        if (CompatibilityUtil.apiLevelCheck(24)) {
            try {
                return getDevicePolicyManager(context).switchUser(adminComponent, userHandle);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return false;
    }
    
    @RequiresApi(api = 17)
    public static boolean isMasterUser(final Context context) {
        return getUserManager(context).getSerialNumberForUser(Process.myUserHandle()) == 0L;
    }
    
    public static boolean isMyUserHandle(final Context context, final int userId) {
        return getMyUserId(context) == userId;
    }
    
    public static boolean isMyUserHandle(final UserHandle userHandle) {
        return Objects.equals(Process.myUserHandle(), userHandle);
    }
    
    public static boolean isWorkUserHandle(final UserHandle userHandle) {
        return isMyUserHandle(userHandle) ^ true;
    }
    
    public static boolean hasWorkUserHandle(final Context context) {
        return getWorkUserProfile(context) != null;
    }
    
    @RequiresApi(api = 21)
    public static List<UserInfo> getUserInfoList(final Context context) {
        final ArrayList<UserInfo> list = new ArrayList<UserInfo>();
        final List a;
        if (CollectionUtils.isNonBlank(a = a(context))) {
            final Iterator iterator = a.iterator();
            while (iterator.hasNext()) {
                final ArrayList<UserInfo> list2 = list;
                final Object next = iterator.next();
                list2.add(new UserInfo(ReflectUtil.getDeclareIntFieldSafely((Class)DPMUtils.c, next, "id"), ReflectUtil.getDeclareStringFieldSafely((Class)DPMUtils.c, next, "name"), ReflectUtil.getDeclareIntFieldSafely((Class)DPMUtils.c, next, "serialNumber")));
            }
        }
        return list;
    }
    
    public static boolean hasUserRestriction(final Context context, final String key) {
        return getUserManager(context).hasUserRestriction(key);
    }
    
    public static boolean toggleUserRestriction(final Context context, final ComponentName admin, final String key) {
        final boolean hasUserRestriction;
        if (hasUserRestriction = hasUserRestriction(context, key)) {
            getDevicePolicyManager(context).clearUserRestriction(admin, key);
        }
        else {
            getDevicePolicyManager(context).addUserRestriction(admin, key);
        }
        return hasUserRestriction ^ true;
    }
    
    public static boolean toggleUserRestriction(final Context context, final ComponentName admin, final String key, final int targetUserID, final int callingUserID) {
        final boolean hasUserRestriction;
        if (hasUserRestriction = hasUserRestriction(context, key)) {
            clearUserRestrictionForTargetUser(context, admin, key, targetUserID, callingUserID);
        }
        else {
            addUserRestrictionForTargetUser(context, admin, key, targetUserID, callingUserID);
        }
        return hasUserRestriction ^ true;
    }
    
    @RequiresApi(api = 21)
    public static List<PackageInfo> getInstalledPackageAsUser(final Context context, final int flag, final int userID) {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(DPMUtils.i, (Object)context.getPackageManager(), new Object[] { flag, userID })) instanceof List) {
            return (List<PackageInfo>)invokeMethodSafely;
        }
        return Collections.EMPTY_LIST;
    }
    
    @RequiresApi(api = 21)
    public static void deletePackageAsUser(final Context context, final String packageName, final PackageDeleteObserver packageDeleteObserver, final int flag, final int userID) {
        final Method j = DPMUtils.j;
        final PackageManager packageManager = context.getPackageManager();
        final Object[] array = new Object[4];
        final Object[] array3;
        final Object[] array2 = array3 = array;
        array3[0] = packageName;
        array3[1] = packageDeleteObserver;
        array2[2] = flag;
        array[3] = userID;
        ReflectUtil.invokeMethodSafely(j, (Object)packageManager, array);
    }
    
    @RequiresApi(api = 21)
    public static void addUserRestrictionForTargetUser(final Context context, final ComponentName admin, final String key, final int targetUserID, final int callingUserID) {
        final Method l = DPMUtils.l;
        final DevicePolicyManager devicePolicyManager = getDevicePolicyManager(context);
        final Object[] array = new Object[4];
        final Object[] array3;
        final Object[] array2 = array3 = array;
        array3[0] = admin;
        array3[1] = key;
        array2[2] = targetUserID;
        array[3] = callingUserID;
        ReflectUtil.invokeMethodSafely(l, (Object)devicePolicyManager, array);
    }
    
    @RequiresApi(api = 21)
    public static void clearUserRestrictionForTargetUser(final Context context, final ComponentName admin, final String key, final int targetUserID, final int callingUserID) {
        final Method m = DPMUtils.m;
        final DevicePolicyManager devicePolicyManager = getDevicePolicyManager(context);
        final Object[] array = new Object[4];
        final Object[] array3;
        final Object[] array2 = array3 = array;
        array3[0] = admin;
        array3[1] = key;
        array2[2] = targetUserID;
        array[3] = callingUserID;
        ReflectUtil.invokeMethodSafely(m, (Object)devicePolicyManager, array);
    }
    
    @RequiresApi(api = 21)
    public static void removeThisUser(final Context context) {
        ReflectUtil.invokeMethodSafely(DPMUtils.n, (Object)getDevicePolicyManager(context), new Object[0]);
    }
    
    public static List<UserHandle> getUserProfiles(final Context context) {
        return getUserManager(context).getUserProfiles();
    }
    
    @Nullable
    public static UserHandle getWorkUserProfile(final Context context) {
        return CollectionUtils.findItem(getUserManager(context).getUserProfiles(), (io.reactivex.functions.Predicate<UserHandle>)DPMUtils::isWorkUserHandle);
    }
    
    public static int getWorkUserId(final Context context) {
        final UserHandle workUserProfile;
        if ((workUserProfile = getWorkUserProfile(context)) == null) {
            return -1;
        }
        return getIdForUser(context, workUserProfile);
    }
    
    public static boolean requestWorkProfileQuietModeEnabled(final Context context, final boolean enable) {
        final UserHandle workUserProfile;
        if ((workUserProfile = getWorkUserProfile(context)) == null) {
            Debug.i(DPMUtils.a, "requestWorkProfileQuietMode work handle null", new Object[0]);
            return true;
        }
        if (CompatibilityUtil.apiLevelCheck(28)) {
            final boolean requestQuietModeEnabled = getUserManager(context).requestQuietModeEnabled(enable, workUserProfile);
            Debug.i(DPMUtils.a, "requestWorkProfileQuietMode enabled:" + enable + ",result:" + requestQuietModeEnabled, new Object[0]);
            return requestQuietModeEnabled;
        }
        return true;
    }
    
    public static boolean isQuietModeEnabled(final Context context) {
        final UserHandle workUserProfile;
        if ((workUserProfile = getWorkUserProfile(context)) == null) {
            Debug.i(DPMUtils.a, "isProfileQuietModeEnabled work handle null", new Object[0]);
            return true;
        }
        if (CompatibilityUtil.apiLevelCheck(24)) {
            final boolean quietModeEnabled = getUserManager(context).isQuietModeEnabled(workUserProfile);
            Debug.i(DPMUtils.a, "isProfileQuietModeEnabled result:" + quietModeEnabled, new Object[0]);
            return quietModeEnabled;
        }
        return true;
    }
    
    public static boolean isDeployProvisionManagedProfile(final Context context) {
        return getProvisionManagedProfileIntent(context) != null;
    }
    
    @Nullable
    public static Intent getProvisionManagedProfileIntent(final Context context, final String deviceAdminReceiverClsName) {
        final Intent provisionManagedProfileIntent;
        if ((provisionManagedProfileIntent = getProvisionManagedProfileIntent(context)) != null) {
            return provisionManagedProfileIntent.putExtra("android.app.extra.PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME", (Parcelable)new ComponentName(context, deviceAdminReceiverClsName));
        }
        return null;
    }
    
    @Nullable
    public static Intent getProvisionManagedProfileIntent(final Context context) {
        final Intent intent = new android.content.Intent();
        final Intent intent2 = intent;
        new Intent("android.app.action.PROVISION_MANAGED_PROFILE");
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            return intent2;
        }
        Debug.i(DPMUtils.a, "provision managed profile is not enabled", new Object[0]);
        return null;
    }
    
    public static void setDeviceProfile(final Context context, final ComponentName adminComponentName, final String profileName) {
        final DevicePolicyManager devicePolicyManager = getDevicePolicyManager(context);
        devicePolicyManager.setProfileName(adminComponentName, profileName);
        devicePolicyManager.setProfileEnabled(adminComponentName);
        Debug.i(DPMUtils.a, "setDeviceProfile:" + adminComponentName + " profileName:" + profileName, new Object[0]);
    }
    
    public static boolean enableDeviceProfileApp(final Context context, final ComponentName adminComponentName, final String packageName, final boolean enabled) {
        try {
            final PackageManager packageManager = context.getPackageManager();
            final DevicePolicyManager devicePolicyManager = getDevicePolicyManager(context);
            CompatibilityUtil.apiLevelCheck(24);
            if ((packageManager.getApplicationInfo(packageName, 8192).flags & 0x800000) != 0x0) {
                return devicePolicyManager.setApplicationHidden(adminComponentName, packageName, !enabled);
            }
            if (enabled) {
                devicePolicyManager.enableSystemApp(adminComponentName, packageName);
                return true;
            }
            return false;
        } catch (PackageManager.NameNotFoundException exception) {
            exception.printStackTrace();
            return false;
        }
    }
    
    public static void clearUserRestriction(final Context context, final ComponentName adminComponentName, final String key) {
        getDevicePolicyManager(context).clearUserRestriction(adminComponentName, key);
    }
    
    public static boolean removeWorkProfileUser(final Context context) {
        final UserManager userManager = (UserManager)context.getSystemService("user");
        final int workUserId;
        final int n = workUserId = getWorkUserId(context);
        final String a = DPMUtils.a;
        Debug.i(a, "removeWorkProfileUser workUserId: " + workUserId, new Object[0]);
        final int myUserId = getMyUserId(context);
        Debug.i(a, "removeWorkProfileUser myUserId: " + myUserId, new Object[0]);
        final int[] profileIds = getProfileIds(context);
        int n2 = 0;
        Label_0130: {
            if (n == -1 && profileIds != null) {
                for (int length = profileIds.length, i = 0; i < length; ++i) {
                    if (myUserId != (n2 = profileIds[i])) {
                        break Label_0130;
                    }
                }
            }
            n2 = workUserId;
        }
        final Method declaredMethodSafely = ReflectUtil.getDeclaredMethodSafely((Class)userManager.getClass(), "removeUserEvenWhenDisallowed", new Class[] { Integer.TYPE });
        try {
            final Object invokeMethodSafely;
            if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(declaredMethodSafely, (Object)userManager, new Object[] { n2 })) instanceof Boolean) {
                final Object o = invokeMethodSafely;
                Debug.i(DPMUtils.a, "removeWorkProfileUser workUserId: " + n2 + ", result: " + invokeMethodSafely, new Object[0]);
                return (boolean)o;
            }
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public static int[] getProfileIds(final Context context) {
        final UserManager userManager = (UserManager)context.getSystemService("user");
        final int myUserId = getMyUserId(context);
        final UserManager userManager2 = userManager;
        final String a;
        Debug.i(a = DPMUtils.a, "getProfileIds myUserId: " + myUserId, new Object[0]);
        final Method declaredMethodSafely = ReflectUtil.getDeclaredMethodSafely((Class)userManager2.getClass(), "getProfileIdsWithDisabled", new Class[] { Integer.TYPE });
        try {
            final int[] a2 = (int[])ReflectUtil.invokeMethodSafely(declaredMethodSafely, (Object)userManager, new Object[] { myUserId });
            Debug.i(a, "getProfileIds userIds: " + Arrays.toString(a2), new Object[0]);
            return a2;
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return new int[0];
        }
    }
    
    static {
        DPMUtils.b = UserManager.class;
        DPMUtils.c = ReflectUtil.classForName("android.content.pm.UserInfo");
        DPMUtils.d = PackageInstaller.class;
        DPMUtils.e = PackageManager.class;
        DPMUtils.f = DevicePolicyManager.class;
        DPMUtils.g = ReflectUtil.getMethodSafely((Class)DPMUtils.b, "getUsers", new Class[0]);
        final Class<?> d = DPMUtils.d;
        final Class[] array2;
        final Class[] array = array2 = new Class[2];
        array[0] = PackageInstaller.SessionParams.class;
        final Class<Integer> type = Integer.TYPE;
        array[1] = type;
        DPMUtils.h = ReflectUtil.getMethodSafely((Class)d, "createSession", array2);
        final Class<?> e = DPMUtils.e;
        String s;
        if (CompatibilityUtil.apiLevelCheck(28)) {
            s = "getInstalledPackagesAsUser";
        }
        else {
            s = "getInstalledPackages";
        }
        final Class<?> clazz = e;
        final String s2 = s;
        final Class[] array4;
        final Class[] array3 = array4 = new Class[2];
        array3[1] = (array3[0] = type);
        DPMUtils.i = ReflectUtil.getMethodSafely((Class)clazz, s2, array4);
        final Class<?> e2 = DPMUtils.e;
        final Class[] array6;
        final Class[] array5 = array6 = new Class[4];
        array5[0] = String.class;
        array5[1] = IPackageDeleteObserver.class;
        array5[3] = (array5[2] = type);
        DPMUtils.j = ReflectUtil.getMethodSafely((Class)e2, "deletePackageAsUser", array6);
        final Class<?> f = DPMUtils.f;
        final Class[] array8;
        final Class[] array7 = array8 = new Class[2];
        array7[0] = ComponentName.class;
        array7[1] = UserHandle.class;
        DPMUtils.k = ReflectUtil.getMethodSafely((Class)f, "startUserInBackground", array8);
        final Class<?> f2 = DPMUtils.f;
        final Class[] array10;
        final Class[] array9 = array10 = new Class[4];
        array9[0] = ComponentName.class;
        array9[1] = String.class;
        array9[3] = (array9[2] = type);
        DPMUtils.l = ReflectUtil.getMethodSafely((Class)f2, "addUserRestrictionForTargetUser", array10);
        final Class<?> f3 = DPMUtils.f;
        final Class[] array12;
        final Class[] array11 = array12 = new Class[4];
        array11[0] = ComponentName.class;
        array11[1] = String.class;
        array11[3] = (array11[2] = type);
        DPMUtils.m = ReflectUtil.getMethodSafely((Class)f3, "clearUserRestrictionForTargetUser", array12);
        DPMUtils.n = ReflectUtil.getMethodSafely((Class)DPMUtils.f, "removeThisUser", new Class[0]);
    }
}
