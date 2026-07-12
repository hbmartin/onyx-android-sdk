package com.onyx.android.sdk.api.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/api/utils/LocaleUtils.class */
public class LocaleUtils {
    private static Map<String, Locale> langLocaleMap;
    private static String LOCALE_RUSSIAN_LANGUAGE = "ru";
    private static String LOCALE_TURKEY_LANGUAGE = "tr";
    private static String LOCALE_RUSSIAN_COUNTRY = "RU";
    private static String LOCALE_TURKEY_COUNTRY = "TR";

    public static Locale getLocaleByLang(String language) {
        if (langLocaleMap == null) {
            HashMap map = new HashMap();
            langLocaleMap = map;
            map.put(Locale.CHINA.getLanguage(), Locale.CHINA);
            Map<String, Locale> map2 = langLocaleMap;
            Locale locale = Locale.US;
            map2.put(locale.getLanguage(), locale);
            langLocaleMap.put(Locale.FRANCE.getLanguage(), Locale.FRANCE);
            langLocaleMap.put(Locale.GERMANY.getLanguage(), Locale.GERMANY);
            langLocaleMap.put(Locale.ITALY.getLanguage(), Locale.ITALY);
            langLocaleMap.put(Locale.JAPAN.getLanguage(), Locale.JAPAN);
            langLocaleMap.put(Locale.KOREA.getLanguage(), Locale.KOREA);
            langLocaleMap.put(LOCALE_RUSSIAN_LANGUAGE, new Locale(LOCALE_RUSSIAN_LANGUAGE, LOCALE_RUSSIAN_COUNTRY));
            langLocaleMap.put(LOCALE_TURKEY_LANGUAGE, new Locale(LOCALE_TURKEY_LANGUAGE, LOCALE_TURKEY_COUNTRY));
        }
        Locale locale2 = langLocaleMap.get(language);
        Locale locale3 = locale2;
        if (locale2 == null) {
            locale3 = Locale.US;
        }
        return locale3;
    }
}
