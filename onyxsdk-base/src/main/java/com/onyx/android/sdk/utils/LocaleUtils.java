package com.onyx.android.sdk.utils;

import android.content.Context;
import android.os.LocaleList;
import androidx.core.text.TextUtilsCompat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/LocaleUtils.class */
public class LocaleUtils {
    public static String LOCALE_SPLIT_CHAR = "_";
    public static String LOCALE_RUSSIAN_LANGUAGE = "ru";
    public static String LOCALE_TURKEY_LANGUAGE = "tr";
    public static String LOCALE_RUSSIAN_COUNTRY = "RU";
    public static String LOCALE_TURKEY_COUNTRY = "TR";
    public static String LOCALE_HONGKONG = "HK";
    public static final int AUTO = -1;
    public static final int CP437 = 437;
    public static final int CP850 = 850;
    public static final int CP855 = 855;
    public static final int CP860 = 860;
    public static final int CP861 = 861;
    public static final int CP863 = 863;
    public static final int CP865 = 865;
    public static final int CP866 = 866;
    public static final int CP874 = 874;
    public static final int CP932 = 932;
    public static final int CP936 = 936;
    public static final int CP949 = 949;
    public static final int CP950 = 950;
    public static final int CP1200 = 1200;
    public static final int CP1201 = 1201;
    public static final int CP1250 = 1250;
    public static final int CP1251 = 1251;
    public static final int CP1252 = 1252;
    public static final int CP1253 = 1253;
    public static final int CP1254 = 1254;
    public static final int CP1255 = 1255;
    public static final int CP1256 = 1256;
    public static final int CP1257 = 1257;
    public static final int CP1258 = 1258;
    public static final int CP10000 = 10000;
    public static final int CP10007 = 10007;
    public static final int CP10017 = 10017;
    public static final int CP10079 = 10079;
    public static final int CP20127 = 20127;
    public static final int CP20866 = 20866;
    public static final int CP21866 = 21866;
    public static final int CP28591 = 28591;
    public static final int CP28592 = 28592;
    public static final int CP28595 = 28595;
    public static final int CP28605 = 28605;
    public static final int CP65001 = 65001;

    public static boolean isChinese() {
        return Locale.getDefault().getDisplayLanguage().equals(Locale.CHINESE.getDisplayLanguage());
    }

    public static boolean isSimplifiedChinese() {
        return Locale.getDefault().getDisplayLanguage().equals(Locale.SIMPLIFIED_CHINESE.getDisplayLanguage()) && Locale.getDefault().getCountry().equals(Locale.SIMPLIFIED_CHINESE.getCountry());
    }

    public static boolean isTraditionalChinese() {
        return isChinese() && !isSimplifiedChinese();
    }

    public static boolean isEnglish() {
        return Locale.getDefault().getDisplayLanguage().equals(Locale.ENGLISH.getDisplayLanguage());
    }

    public static boolean isRussian() {
        return Locale.getDefault().getLanguage().equals(LOCALE_RUSSIAN_LANGUAGE);
    }

    public static boolean isTurkey() {
        return Locale.getDefault().getLanguage().equals(LOCALE_TURKEY_LANGUAGE);
    }

    public static int getLocaleDefaultCodePage() {
        String language = Locale.getDefault().getLanguage();
        language.hashCode();
        switch (language) {
            case "ja":
                return CP932;
            case "ko":
                return CP949;
            case "ru":
                return CP1251;
            case "zh":
                return CP936;
            default:
                return CP65001;
        }
    }

    public static boolean isSameLocale(Locale sourceLocal, Locale compareLocal) {
        return sourceLocal.getLanguage().equalsIgnoreCase(compareLocal.getLanguage()) && sourceLocal.getCountry().equalsIgnoreCase(compareLocal.getCountry());
    }

    public static boolean isSameLanguage(Locale sourceLocal, Locale compareLocal) {
        return sourceLocal.getLanguage().equalsIgnoreCase(compareLocal.getLanguage());
    }

    public static Locale getCurrentLocale(Context context) {
        if (CompatibilityUtil.apiLevelCheck(24)) {
            LocaleList locales = context.getResources().getConfiguration().getLocales();
            if (locales.size() > 0) {
                return locales.get(0);
            }
        }
        return context.getResources().getConfiguration().locale;
    }

    public static boolean contains(Locale targetLocale, Collection<Locale> localeCollection) {
        Iterator<Locale> it = localeCollection.iterator();
        while (it.hasNext()) {
            if (isSameLocale(targetLocale, it.next())) {
                return true;
            }
        }
        return false;
    }

    public static String getCurrentLocaleStr() {
        return getLocaleStr(getLocaleByLang(Locale.getDefault().getLanguage()));
    }

    public static String getLocaleStr(Locale locale) {
        String language = locale.getLanguage();
        String country = locale.getCountry();
        String variant = locale.getVariant();
        StringBuilder sb = new StringBuilder();
        sb.append(language);
        if (StringUtils.isNotBlank(country)) {
            sb.append(LOCALE_SPLIT_CHAR);
            sb.append(country);
        }
        if (!StringUtils.isNullOrEmpty(variant)) {
            sb.append(LOCALE_SPLIT_CHAR);
            sb.append(variant);
        }
        return sb.toString();
    }

    public static Locale getLocal(String localStr) {
        String[] strArrSplit = localStr.split(LOCALE_SPLIT_CHAR);
        if (strArrSplit.length < 2) {
            return null;
        }
        if (strArrSplit.length > 2) {
            return new Locale(strArrSplit[0], strArrSplit[1], strArrSplit[2]);
        }
        return new Locale(strArrSplit[0], strArrSplit[1]);
    }

    public static Locale getLocalSafely(String localStr) {
        String[] strArrSplit = localStr.split(LOCALE_SPLIT_CHAR);
        if (strArrSplit.length < 2) {
            return new Locale(localStr);
        }
        if (strArrSplit.length > 2) {
            return new Locale(strArrSplit[0], strArrSplit[1], strArrSplit[2]);
        }
        return new Locale(strArrSplit[0], strArrSplit[1]);
    }

    public static String getDisplayFullNameSafely(Locale locale, String name) {
        if (!isTWHKLocale(locale)) {
            return getDisplayNameSafely(locale, name);
        }
        if (StringUtils.isBlank(name)) {
            name = locale.getDisplayLanguage();
        }
        return name + " (" + getDisplayCountrySafely(locale) + ")";
    }

    public static String getDisplayNameSafely(Locale locale, String name) {
        return StringUtils.isBlank(name) ? locale.getDisplayName() : name + " (" + locale.getDisplayCountry() + ")";
    }

    public static String getDisplayCountrySafely(Locale locale) {
        if (!isTWHKLocale(locale)) {
            return locale.getDisplayCountry();
        }
        StringBuilder sb = new StringBuilder(Locale.CHINA.getDisplayCountry());
        if (StringUtils.isAlpha(Locale.CHINA.getDisplayCountry())) {
            sb.append(" ");
        }
        sb.append(locale.getDisplayCountry());
        return sb.toString();
    }

    public static boolean isTWHKLocale(Locale locale) {
        return locale.equals(Locale.TAIWAN) || locale.getCountry().equals(LOCALE_HONGKONG);
    }

    public static boolean isChinaLocale(Locale locale) {
        return isSameLocale(locale, Locale.CHINA);
    }

    public static boolean isChineLanguage(Locale locale) {
        return isSameLanguage(locale, Locale.CHINA);
    }

    public static boolean isCJK() {
        Locale locale = Locale.getDefault();
        return isSameLanguage(locale, Locale.CHINESE) || isSameLanguage(locale, Locale.JAPANESE) || isSameLanguage(locale, Locale.KOREAN);
    }

    public static boolean isCurrentLayoutDirectionRTL() {
        return TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
    }

    public static boolean isCurrentLayoutDirectionVerticalLR() {
        return Locale.getDefault().getLanguage().equals("mn");
    }

    public static Locale getLocaleByLang(String language) {
        return com.onyx.android.sdk.api.utils.LocaleUtils.getLocaleByLang(language);
    }

    public static boolean isSameLanguage(Locale targetLocale, Collection<Locale> localeCollection) {
        if (targetLocale == null) {
            return false;
        }
        Iterator<Locale> it = localeCollection.iterator();
        while (it.hasNext()) {
            if (isSameLanguage(targetLocale, it.next())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSameLanguage(String language, Locale locale) {
        return StringUtils.isEquals(language, locale.getLanguage());
    }
}
