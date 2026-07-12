package com.onyx.android.sdk.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.TypeReference;
import com.onyx.android.sdk.data.DictionaryQuery;
import com.onyx.android.sdk.data.dict.DictionaryInfo;
import com.onyx.android.sdk.data.dict.DictionaryQueryResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.text.WordUtils;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/DictionaryUtil.class */
public class DictionaryUtil {
    private static final String a = "state";
    private static final String b = "resultFileFd";
    private static final int c = 5;
    private static final String d = "content://com.onyx.dict.DictionaryProvider";

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/DictionaryUtil$a.class */
    static class a extends TypeReference<List<DictionaryQueryResult>> {
        a() {
        }
    }

    public static DictionaryQuery queryKeyWord(Context context, String keyword) {
        return StringUtils.isNullOrEmpty(keyword) ? new DictionaryQuery(-1) : a(context, keyword);
    }

    private static DictionaryQuery a(Context context, String keyword) {
        Cursor cursor = null;
        try {
            try {
                Uri uri = Uri.parse(d);
                String strTrim = StringUtils.trim(keyword);
                String[] strArr = new String[4];
                strArr[0] = strTrim;
                strArr[1] = String.valueOf(5);
                strArr[2] = TTFFont.UNKNOWN_FONT_NAME;
                strArr[3] = "false";
                Cursor cursorQuery = context.getContentResolver().query(uri, null, null, strArr, null);
                if (cursorQuery == null) {
                    DictionaryQuery dictionaryQuery = new DictionaryQuery(-2);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return dictionaryQuery;
                }
                if (cursorQuery.getCount() == 0) {
                    DictionaryQuery dictionaryQuery2 = new DictionaryQuery(3);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return dictionaryQuery2;
                }
                if (!cursorQuery.moveToFirst()) {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return new DictionaryQuery(-2);
                }
                int i = cursorQuery.getInt(cursorQuery.getColumnIndex("state"));
                if (i != 0) {
                    DictionaryQuery dictionaryQuery3 = new DictionaryQuery(i);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return dictionaryQuery3;
                }
                DictionaryQuery dictionaryQuery4 = new DictionaryQuery(0);
                dictionaryQuery4.setList(a(cursorQuery, strTrim));
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return dictionaryQuery4;
            } catch (Exception unused) {
                DictionaryQuery dictionaryQuery5 = new DictionaryQuery(-2);
                if (0 != 0) {
                    cursor.close();
                }
                return dictionaryQuery5;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    public static List<String> getCandidates(String keyword) {
        ArrayList<String> arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        String lowerCaseLocaleInsensitive = StringUtils.toLowerCaseLocaleInsensitive(keyword.trim());
        arrayList.add(keyword.trim());
        arrayList.add(lowerCaseLocaleInsensitive);
        arrayList.add(lowerCaseLocaleInsensitive.toLowerCase());
        arrayList.add(lowerCaseLocaleInsensitive.toUpperCase());
        arrayList.add(WordUtils.capitalize(lowerCaseLocaleInsensitive));
        for (String str : arrayList) {
            if (!arrayList2.contains(str)) {
                arrayList2.add(str);
            }
        }
        return arrayList2;
    }

    private static List<DictionaryQuery.Dictionary> a(Cursor cursor, String keyword) {
        ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) cursor.getExtras().getParcelable(b);
        if (parcelFileDescriptor == null) {
            return Collections.emptyList();
        }
        String memoryFile = MemoryFileUtils.readMemoryFile(parcelFileDescriptor);
        if (StringUtils.isNullOrEmpty(memoryFile)) {
            return Collections.emptyList();
        }
        List<DictionaryQueryResult> list = (List<DictionaryQueryResult>) JSONUtils.parseObject(memoryFile, new a(), new JSONReader.Feature[0]);
        if (CollectionUtils.isNullOrEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(result -> {
            DictionaryInfo dictionaryInfo = result.dictionary;
            return new DictionaryQuery.Dictionary(0, dictionaryInfo == null ? TTFFont.UNKNOWN_FONT_NAME : dictionaryInfo.name, keyword, result.explanation);
        }).collect(Collectors.toList());
    }
}
