package com.onyx.android.sdk.common.base;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.alibaba.fastjson2.JSONWriter;
import com.onyx.android.sdk.common.MMKVBuilder;
import com.onyx.android.sdk.utils.JSONUtils;
import com.onyx.android.sdk.utils.TTFFont;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/common/base/BaseSearchHistoryHelper.class */
public class BaseSearchHistoryHelper {
    private static final String b = "search_history_key";
    private static final int c = 8;
    private MMKVBuilder a;

    protected BaseSearchHistoryHelper() {
    }

    public List<String> save(@NonNull String searchText) {
        List<String> searchHistory = getSearchHistory();
        List<String> list = searchHistory;
        int maxCount = getMaxCount();
        if (searchHistory == null) {
            list = new ArrayList<>(maxCount);
        }
        if (list.contains(searchText)) {
            list.remove(searchText);
        }
        List<String> list2 = list;
        list2.add(0, searchText);
        if (list2.size() > maxCount) {
            List<String> list3 = list;
            list3.removeAll(list3.subList(maxCount, list.size()));
        }
        List<String> list4 = list;
        save(list);
        return list4;
    }

    public List<String> getSearchHistory() {
        String string = this.a.getString(getSearchHistoryKey(), TTFFont.UNKNOWN_FONT_NAME);
        return TextUtils.isEmpty(string) ? new ArrayList() : JSONUtils.toList(string, String.class);
    }

    public void clear() {
        this.a.putString(getSearchHistoryKey(), TTFFont.UNKNOWN_FONT_NAME);
    }

    public int getMaxCount() {
        return 8;
    }

    protected String getSearchHistoryKey() {
        return b;
    }

    protected BaseSearchHistoryHelper(@NonNull MMKVBuilder mmkvBuilder) {
        this.a = mmkvBuilder;
    }

    public void save(List<String> list) {
        this.a.putString(getSearchHistoryKey(), JSONUtils.toJson(list, new JSONWriter.Feature[0]));
    }
}
