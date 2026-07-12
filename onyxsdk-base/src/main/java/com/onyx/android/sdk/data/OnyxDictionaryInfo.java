package com.onyx.android.sdk.data;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

public class OnyxDictionaryInfo {
    private static final OnyxDictionaryInfo[] a = {new OnyxDictionaryInfo("NeoDict", "Neo Dictionary", "com.onyx.dict", "com.onyx.dict.main.ui.DictMainActivity", "android.intent.action.SEARCH", 0), new OnyxDictionaryInfo("OnyxDict", "Onyx Dictionary", "com.onyx.android.dict", "com.onyx.android.dict.ui.DictActivity", "android.intent.action.SEARCH", 0), new OnyxDictionaryInfo("QuickDic", "QuickDic Dictionary", "com.hughes.android.dictionary", "com.hughes.android.dictionary.DictionaryManagerActivity", "android.intent.action.SEARCH", 0), new OnyxDictionaryInfo("ColorDict", "ColorDict", "com.socialnmobile.colordict", "com.socialnmobile.colordict.activity.Main", "android.intent.action.SEARCH", 0), new OnyxDictionaryInfo("Fora", "Fora Dictionary", "com.ngc.fora", "com.ngc.fora.ForaDictionary", "android.intent.action.SEARCH", 0), new OnyxDictionaryInfo("FreeDictionary.org", "Free Dictionary.org", "org.freedictionary", "org.freedictionary.MainActivity", "android.intent.action.VIEW", 0), new OnyxDictionaryInfo("Lingvo", "Lingvo", "com.abbyy.mobile.lingvo.market", "com.abbyy.mobile.lingvo.wordlist.WordListActivity", "android.intent.action.SEARCH", 0)};
    private static OnyxDictionaryInfo b = new OnyxDictionaryInfo("NeoDict", "Neo Dictionary", "com.onyx.dict", "com.onyx.dict.main.ui.DictMainCompatMMultiWindowActivity", "android.intent.action.SEARCH", 0);
    public static final String START_FROM_FULL_SCREEN = "start_from_full_screen";
    public static final String EXTRA_TAB = "tab";
    public static final String NEW_WORD_TAB = "NewWord";
    public static final String DICT_PATH = "dict_path";
    public final String id;
    public final String name;
    public final String packageName;
    public final String className;
    public final String action;
    public final Integer internal;
    public String dataKey = "query";

    private OnyxDictionaryInfo(String id, String name, String packageName, String className, String action, Integer internal) {
        this.id = id;
        this.name = name;
        this.packageName = packageName;
        this.className = className;
        this.action = action;
        this.internal = internal;
    }

    public static OnyxDictionaryInfo[] getDictionaryList() {
        return a;
    }

    public static OnyxDictionaryInfo findDict(String dictId) {
        for (OnyxDictionaryInfo onyxDictionaryInfo : a) {
            if (onyxDictionaryInfo.id.equalsIgnoreCase(dictId)) {
                return onyxDictionaryInfo;
            }
        }
        return null;
    }

    public static OnyxDictionaryInfo getDefaultDictionary() {
        return Build.VERSION.SDK_INT <= 23 ? b : a[0];
    }

    public static boolean isDictionaryAvailable(Context context, OnyxDictionaryInfo dict) {
        try {
            return context.getPackageManager().getActivityInfo(new ComponentName(dict.packageName, dict.className), 0) != null;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static boolean isDictionaryAvailable(Context context, String dictId) {
        OnyxDictionaryInfo onyxDictionaryInfoFindDict = findDict(dictId);
        if (onyxDictionaryInfoFindDict == null) {
            return false;
        }
        return isDictionaryAvailable(context, onyxDictionaryInfoFindDict);
    }
}
