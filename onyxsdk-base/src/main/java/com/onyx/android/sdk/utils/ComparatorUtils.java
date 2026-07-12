package com.onyx.android.sdk.utils;

import com.onyx.android.sdk.data.AppDataInfo;
import com.onyx.android.sdk.data.SortOrder;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/ComparatorUtils.class */
public class ComparatorUtils {
    private static Collator collator = Collator.getInstance(Locale.CHINESE);

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/ComparatorUtils$a.class */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[SortOrder.values().length];
            a = iArr;
            try {
                iArr[SortOrder.Desc.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[SortOrder.Asc.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static void updateCollator() {
        collator = Collator.getInstance(LocaleUtils.getCurrentLocale(ResManager.getAppContext()));
    }

    public static int numberStrComparator(String lhs, String rhs, SortOrder ascOrder) {
        boolean zIsNumberStr = StringUtils.isNumberStr(lhs);
        boolean zIsNumberStr2 = StringUtils.isNumberStr(rhs);
        int iLongComparator = longComparator(zIsNumberStr ? NumberUtils.parseLong(lhs) : Long.MAX_VALUE, zIsNumberStr2 ? NumberUtils.parseLong(rhs) : Long.MAX_VALUE, ascOrder);
        if (iLongComparator != 0) {
            return iLongComparator;
        }
        if (zIsNumberStr && zIsNumberStr2) {
            return 0;
        }
        return a.a[ascOrder.ordinal()] != 1 ? Collator.getInstance(Locale.CHINESE).compare(lhs, rhs) : Collator.getInstance(Locale.CHINESE).compare(rhs, lhs);
    }

    public static int stringComparator(String lhs, String rhs, SortOrder ascOrder) {
        return onyxStringComparator(lhs, rhs, ascOrder);
    }

    public static int onyxStringComparator(String lhs, String rhs, SortOrder ascOrder) {
        return onyxStringComparator(lhs, rhs, ascOrder, true);
    }

    private static int a(String string, int start) {
        while (start < string.length() && Character.isDigit(Character.valueOf(string.charAt(start)).charValue())) {
            start++;
        }
        return start;
    }

    public static int onyxStringComparatorAsc(String firstString, String secondString) {
        return onyxStringComparatorAsc(firstString, secondString, true);
    }

    public static int longComparator(long lhs, long rhs, SortOrder ascOrder) {
        return a.a[ascOrder.ordinal()] != 1 ? Long.compare(lhs, rhs) : Long.compare(rhs, lhs);
    }

    public static int dateComparator(Date lhs, Date rhs, SortOrder ascOrder) {
        return longComparator(lhs != null ? lhs.getTime() : 0L, rhs != null ? rhs.getTime() : 0L, ascOrder);
    }

    public static int integerComparator(int lhs, int rhs, SortOrder ascOrder) {
        return a.a[ascOrder.ordinal()] != 1 ? Integer.compare(lhs, rhs) : Integer.compare(rhs, lhs);
    }

    public static int booleanComparator(boolean lhs, boolean rhs, SortOrder ascOrder) {
        if (a.a[ascOrder.ordinal()] != 1) {
            if (lhs == rhs) {
                return 0;
            }
            return lhs ? 1 : -1;
        }
        if (lhs == rhs) {
            return 0;
        }
        return rhs ? 1 : -1;
    }

    public static int appDataInfoComparator(AppDataInfo lhs, AppDataInfo rhs, SortOrder sortOrder) {
        boolean z = lhs.isSystemApp;
        if (z && !rhs.isSystemApp) {
            return 1;
        }
        if (!z && rhs.isSystemApp) {
            return -1;
        }
        if (z == rhs.isSystemApp) {
            return longComparator(lhs.lastUpdatedTime, rhs.lastUpdatedTime, sortOrder);
        }
        return 0;
    }

    public static int onyxStringComparator(String lhs, String rhs, SortOrder ascOrder, boolean compareDigitOnce) {
        return a.a[ascOrder.ordinal()] != 2 ? -onyxStringComparatorAsc(lhs, rhs, compareDigitOnce) : onyxStringComparatorAsc(lhs, rhs, compareDigitOnce);
    }

    public static int onyxStringComparatorAsc(String firstString, String secondString, boolean compareDigitOnce) {
        String strSafelyGetStr = StringUtils.safelyGetStr(firstString);
        String strSafelyGetStr2 = StringUtils.safelyGetStr(secondString);
        int iA = 0;
        int i = 0;
        int length = strSafelyGetStr.length();
        int length2 = strSafelyGetStr2.length();
        while (iA < length && i < length2) {
            Character chValueOf = Character.valueOf(strSafelyGetStr.charAt(iA));
            Character chValueOf2 = Character.valueOf(strSafelyGetStr2.charAt(i));
            boolean zIsDigit = Character.isDigit(chValueOf.charValue());
            boolean zIsDigit2 = Character.isDigit(chValueOf2.charValue());
            boolean z = (zIsDigit || zIsDigit2) ? false : true;
            boolean z2 = z;
            int iCompare = collator.compare(chValueOf.toString(), chValueOf2.toString());
            if (!z2 || iCompare != 0) {
                if (z && iCompare != 0) {
                    return iCompare;
                }
                if (zIsDigit != zIsDigit2) {
                    return zIsDigit ? -1 : 1;
                }
                if (compareDigitOnce) {
                    int i2 = iA;
                    iA = a(strSafelyGetStr, iA);
                    int iA2 = a(strSafelyGetStr2, i);
                    int iCompareTo = a(strSafelyGetStr, i2, iA).compareTo(a(strSafelyGetStr2, i, iA2));
                    if (iCompareTo != 0) {
                        return iCompareTo;
                    }
                    i = iA2;
                } else {
                    int iCompareTo2 = chValueOf.compareTo(chValueOf2);
                    if (iCompareTo2 != 0) {
                        return iCompareTo2;
                    }
                }
            }
            iA++;
            i++;
        }
        return length - length2;
    }

    private static BigDecimal a(String string, int start, int end) {
        return new BigDecimal(string.substring(start, end));
    }
}
