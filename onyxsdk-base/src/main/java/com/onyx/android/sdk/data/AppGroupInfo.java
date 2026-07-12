package com.onyx.android.sdk.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.rx.RxUtils;
import com.onyx.android.sdk.utils.ApplicationUtil;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.JSONUtils;
import com.onyx.android.sdk.utils.ResManager;
import com.onyx.android.sdk.utils.TestUtils;
import io.reactivex.functions.Function;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/AppGroupInfo.class */
public class AppGroupInfo extends AppBaseInfo implements Serializable {
    public String idString;
    public long time;
    public List<AppDataInfo> appInfoList;

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/AppGroupInfo$a.class */
    class a implements Function<AppDataInfo, String> {
        a() {
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public String apply(@NonNull AppDataInfo appDataInfo) throws Exception {
            return appDataInfo.getPackageName();
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/AppGroupInfo$b.class */
    class b implements Function<AppDataInfo, String> {
        b() {
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public String apply(@NonNull AppDataInfo appDataInfo) throws Exception {
            return ApplicationUtil.getAppInfoLaunchName(appDataInfo);
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/AppGroupInfo$c.class */
    class c implements Comparable<AppDataInfo> {
        final /* synthetic */ Set a;
        final /* synthetic */ Function b;

        c(Set set, Function function) {
            this.a = set;
            this.b = function;
        }

        @Override // java.lang.Comparable
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compareTo(@NonNull AppDataInfo appDataInfo) {
            return !CollectionUtils.safelyContains((Set<String>) this.a, (String) RxUtils.applyItemSafety(this.b, appDataInfo)) ? 1 : 0;
        }
    }

    public AppGroupInfo() {
        this.appInfoList = new ArrayList();
        setType(AppBaseInfo.Type.GROUP);
    }

    public static AppGroupInfo create(String groupName) {
        return init(new AppGroupInfo(groupName));
    }

    public static AppGroupInfo init(AppGroupInfo groupInfo) {
        groupInfo.idString = TestUtils.generateUniqueId();
        groupInfo.time = System.currentTimeMillis();
        return groupInfo;
    }

    @Nullable
    public static AppGroupInfo parse(String json, boolean parseAppInfo) {
        AppGroupInfo appGroupInfo = (AppGroupInfo) JSONUtils.parseObject(json, AppGroupInfo.class, new JSONReader.Feature[0]);
        if (appGroupInfo == null) {
            return null;
        }
        JSONArray jSONArray = JSON.parseObject(json).getJSONArray("appInfoList");
        if (jSONArray != null && !jSONArray.isEmpty()) {
            appGroupInfo.appInfoList.clear();
            for (int i = 0; i < jSONArray.size(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                AppDataInfo appDataInfo = AppBaseInfo.Type.isShortcutType((AppBaseInfo.Type) jSONObject.getObject(StringUtils.uncapitalize(AppBaseInfo.Type.class.getSimpleName()), AppBaseInfo.Type.class, new JSONReader.Feature[0])) ? (AppDataInfo) JSONUtils.parseObject(jSONObject.toJSONString(new JSONWriter.Feature[0]), AppShortcutInfo.class, new JSONReader.Feature[0]) : (AppDataInfo) JSONUtils.parseObject(jSONObject.toJSONString(new JSONWriter.Feature[0]), AppDataInfo.class, new JSONReader.Feature[0]);
                if (appDataInfo == null) {
                    Debug.w(AppGroupInfo.class, "can't parse appdataInfo json", new Object[0]);
                } else {
                    if (parseAppInfo) {
                        appDataInfo.parseInfo(ResManager.getAppContext());
                    }
                    CollectionUtils.safeAdd(appGroupInfo.appInfoList, appDataInfo);
                }
            }
        }
        return appGroupInfo;
    }

    @Nullable
    @JSONField(serialize = false, deserialize = false)
    public AppDataInfo getMatchPackageNameAppInfo(String packageName) {
        for (AppDataInfo appDataInfo : this.appInfoList) {
            if (com.onyx.android.sdk.utils.StringUtils.safelyEquals(ApplicationUtil.getAppInfoLaunchName(appDataInfo), packageName)) {
                return appDataInfo;
            }
        }
        return null;
    }

    @JSONField(serialize = false, deserialize = false)
    public List<AppBaseInfo> getMatchNameAppInfoList(String pattern) {
        ArrayList arrayList = new ArrayList();
        for (AppDataInfo appDataInfo : this.appInfoList) {
            if (com.onyx.android.sdk.utils.StringUtils.containsIgnoreCase(appDataInfo.getName(), pattern)) {
                arrayList.add(appDataInfo);
            }
        }
        return arrayList;
    }

    @JSONField(serialize = false, deserialize = false)
    public List<AppDataInfo> getMatchSourceAppInfoList(AppBaseInfo.Source source) {
        ArrayList arrayList = new ArrayList();
        for (AppDataInfo appDataInfo : this.appInfoList) {
            if (appDataInfo.source == source) {
                arrayList.add(appDataInfo);
            }
        }
        return arrayList;
    }

    public boolean updateAppDataInfo(AppDataInfo newAppInfo) {
        AppDataInfo matchPackageNameAppInfo;
        if (newAppInfo == null || (matchPackageNameAppInfo = getMatchPackageNameAppInfo(ApplicationUtil.getAppInfoLaunchName(newAppInfo))) == null) {
            return false;
        }
        matchPackageNameAppInfo.activityClassName = newAppInfo.activityClassName;
        matchPackageNameAppInfo.preInstall = newAppInfo.preInstall;
        matchPackageNameAppInfo.isCustomizedApp = newAppInfo.isCustomizedApp;
        return true;
    }

    public boolean replaceAppDataInfo(String launchName, AppDataInfo appDataInfo) {
        AppDataInfo matchPackageNameAppInfo = getMatchPackageNameAppInfo(launchName);
        if (matchPackageNameAppInfo == null) {
            return false;
        }
        List<AppDataInfo> list = this.appInfoList;
        list.set(list.indexOf(matchPackageNameAppInfo), appDataInfo);
        return true;
    }

    public boolean removeAppsByPkgName(Set<String> pkgNameList) {
        return remove(pkgNameList, new a());
    }

    public boolean removeAppsByLaunchName(Set<String> launchNameList) {
        return remove(launchNameList, new b());
    }

    public boolean remove(Set<String> mathQuerySet, Function<AppDataInfo, String> function) {
        return CollectionUtils.safelyRemove((Collection) this.appInfoList, (Comparable) new c(mathQuerySet, function), false);
    }

    @Override // com.onyx.android.sdk.data.AppBaseInfo
    public String getLaunchName() {
        return this.idString;
    }

    @Override // com.onyx.android.sdk.data.AppBaseInfo
    public String getPackageName() {
        return this.idString;
    }

    public boolean hasChildren() {
        return CollectionUtils.isNonBlank(this.appInfoList);
    }

    public AppGroupInfo(String name) {
        this();
        this.name = name;
    }
}
