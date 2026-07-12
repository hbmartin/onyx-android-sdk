// 
// 

package com.onyx.android.sdk.utils;

import java.util.Optional;
import java.time.ZoneId;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Nullable;
import android.os.SystemClock;
import java.text.DecimalFormat;
import com.onyx.android.sdk.R;
import java.text.ParseException;
import java.util.TimeZone;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Calendar;
import java.util.Date;
import android.text.format.DateFormat;
import android.provider.Settings;
import android.content.Context;

public class DateTimeUtil
{
    public static final String STRING_FORMAT_YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String STRING_FORMAT_YYYYMMDD_HH_MM_SS = "yyyy-MM-dd_HH_mm_ss";
    public static final String STRING_FORMAT_YYYYMMDD_HHMMSS_2 = "yyyy/MM/dd HH:mm:ss";
    public static final String STRING_FORMAT_YYYYMMDD_HH_MM = "yyyy-MM-dd_HH_mm";
    public static final String STRING_FORMAT_YYYYMMDD_HHMM = "yyyy-MM-dd HH:mm";
    public static final String STRING_FORMAT_YYYYMMDD_HHMM_2 = "yyyy/MM/dd HH:mm";
    public static final String STRING_FORMAT_YYYYMMDD_HHMM_3 = "yyyy MM-dd HH:mm";
    public static final String STRING_FORMAT_YYYYMMDD = "dd/MM/yyyy";
    public static final String STRING_FORMAT_YYYYMMDD_2 = "yyyy-MM-dd";
    public static final String STRING_FORMAT_YYYYMMDD_3 = "dd/MM yyyy";
    public static final String STRING_FORMAT_YYYYMMDD_4 = "yyyyMMdd";
    public static final String STRING_FORMAT_YYYYMMDD_5 = "yyyy MM-dd";
    public static final String STRING_FORMAT_YYYYMMDD_T_HHMMSS_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String STRING_FORMAT_YYYYMMDD_T_HHMMSS_Z_2 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String STRING_FORMAT_YYYYMMDD_T_HHMMSS_Z_3 = "yyyyMMdd'T'HHmmss'Z'";
    public static final String STRING_FORMAT_YYYYMMDD_HHMMSS_FOR_FILE_NAME = "yyyy-MM-dd HH mm ss";
    public static final String STRING_FORMAT_YYYYMMDD_HHMMSS_FOR_AUDIO_FILE = "yyyy-MM-dd-HH-mm-ss";
    public static final String STRING_FORMAT_YYYY_MM_DD_HHMMSS = "yyyy_MM_dd_HHmmss";
    public static final String STRING_FORMAT_YYYYMMDD_HHMMSS_SSS = "yyyy-MM-dd HH:mm:ss:SSS";
    public static final String STRING_FORMAT_YYYYMMDD_T_HHMMSSSSSSS = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS";
    public static final String STRING_FORMAT_YYYYMMDD_T_HHMMSS = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String STRING_FORMAT_YYYYMM = "MM/yyyy";
    public static final String STRING_FORMAT_MMDD = "MM/dd";
    public static final String STRING_FORMAT_HH = "HH";
    public static final String STRING_FORMAT_HHMM = "HH:mm";
    public static final String STRING_FORMAT_DD = "dd";
    public static final String STRING_FORMAT_WEEK = "EEEE";
    public static final String STRING_FORMAT_HHMM_12 = "hh:mm";
    public static final String STRING_FORMAT_MM_SS = "mm_ss";
    public static final String STRING_FORMAT_FILE_NAME = "HHmmss";
    public static final String STRING_FORMAT_HH_12 = "hh";
    public static final String STRING_FORMAT_MM = "mm";
    public static final String TIME_ZONE_UTC = "UTC";
    public static final String HOURS_12 = "12";
    public static final String HOURS_24 = "24";
    public static final String ZERO_TIME_HH_MM_SS = "00:00:00";
    public static final int WEEK_DAYS = 7;
    public static final int ONE_SECOND_TIME_IN_MILL = 1000;
    public static final int ONE_HOUR_TIME_IN_MILL = 3600000;
    public static final int MINUTES_PER_HOUR = 60;
    public static final int ONE_MINUTE_TIME_IN_MILL = 60000;
    public static final long ONE_DAY_TIME_IN_MILL = 86400000L;
    public static final long ONE_WEEK_TIME_IN_MILL = 604800000L;
    public static final long ONE_YEAR_TIME_IN_MILL = 31536000000L;
    public static final int MILLIS_COUNT_OF_SECOND = 1000;
    public static final long NO_TIME_LIMIT = -1L;
    
    public static boolean isSystemHour24(final Context context) {
        return "24".equals(Settings.System.getString(context.getContentResolver(), "time_12_24"));
    }
    
    public static String getCurrentTimeString(final Context context) {
        return DateFormat.getTimeFormat(context).format(new Date());
    }
    
    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd hh:mm:ss:SSS");
    }
    
    public static String getCurrentTime24Format() {
        return getCurrentTime("yyyy-MM-dd HH:mm:ss:SSS");
    }
    
    public static String getCurrentTime(final String pattern) {
        return formatDate(Calendar.getInstance().getTime(), new SimpleDateFormat(pattern, Locale.getDefault()));
    }
    
    public static Date getMonthBegin() {
        return getMonthBegin(Calendar.getInstance());
    }
    
    public static Date getMonthBegin(final Calendar calendar) {
        calendar.set(5, calendar.getActualMinimum(5));
        return getDayBegin(calendar);
    }
    
    public static Date getMonthEnd() {
        return getMonthEnd(Calendar.getInstance());
    }
    
    public static SimpleDateFormat getWeekDateFormat() {
        return new SimpleDateFormat("EEEE", Locale.getDefault());
    }
    
    public static Date getMonthEnd(final Calendar calendar) {
        calendar.set(5, calendar.getActualMaximum(5));
        return getDayEnd(calendar);
    }
    
    public static Date getDayBegin() {
        return getDayBegin(Calendar.getInstance());
    }
    
    public static Date getDayBegin(final Calendar calendar) {
        calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), 0, 0, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }
    
    public static Date getDayBegin(final Date date) {
        final Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return getDayBegin(instance);
    }
    
    public static Date getDayBegin(final long time) {
        final Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(time);
        return getDayBegin(instance);
    }
    
    public static Date getDayEnd(final long time) {
        final Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(time);
        return getDayEnd(instance);
    }
    
    public static Date getDayEnd() {
        return getDayEnd(Calendar.getInstance());
    }
    
    public static Date getDayEnd(final Calendar calendar) {
        calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), 23, 59, 59);
        calendar.set(14, 0);
        return calendar.getTime();
    }
    
    public static Date getDayEnd(final Date date) {
        final Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return getDayEnd(instance);
    }
    
    public static Date getNextDayStartTimestamp(final long time) {
        final Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(time);
        return getNextDayStartTimestamp(instance);
    }
    
    public static Date getNextDayStartTimestamp(final Calendar calendar) {
        if (calendar.get(11) == 0 && calendar.get(12) == 0 && calendar.get(13) == 0 && calendar.get(14) == 0) {
            return calendar.getTime();
        }
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        calendar.add(5, 1);
        return calendar.getTime();
    }
    
    public static Date getPreviousDayStartTimestamp(final long time) {
        final Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(time);
        return getPreviousDayStartTimestamp(instance);
    }
    
    public static Date getPreviousDayStartTimestamp(final Calendar calendar) {
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        calendar.add(5, -1);
        return calendar.getTime();
    }
    
    public static String getCalendarDateString(final Calendar calendar) {
        final int n2;
        final int n = n2 = calendar.get(2) + 1;
        final int value = calendar.get(5);
        final StringBuilder append = new StringBuilder().append(calendar.get(1)).append("");
        Serializable obj;
        if (n < 10) {
            obj = "0" + n2;
        }
        else {
            obj = n2;
        }
        final int n3 = value;
        final StringBuilder append2 = append.append(obj).append("");
        Serializable obj2;
        if (n3 < 10) {
            obj2 = "0" + value;
        }
        else {
            obj2 = value;
        }
        return append2.append(obj2).toString();
    }
    
    public static String formatDate(final Date date) {
        if (date == null) {
            return "";
        }
        return formatDate(date, getDateFormatYYYYMMDD_HHMMSS());
    }
    
    public static String formatDate(final long time) {
        return formatDate(new Date(time));
    }
    
    public static String formatDate(final Date date, final SimpleDateFormat simpleDateFormat) {
        if (date == null) {
            return "";
        }
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(date);
    }
    
    public static String formatDate(final long time, final SimpleDateFormat simpleDateFormat, final String timeZone) {
        final Date date = new Date(time);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return simpleDateFormat.format(date);
    }
    
    public static long parse(final String dateString, final SimpleDateFormat simpleDateFormat) {
        return parse(dateString, simpleDateFormat, null);
    }
    
    public static long parse(String dateString, final SimpleDateFormat simpleDateFormat, final String timeZone) {
        if (!StringUtils.isNullOrEmpty(timeZone)) {
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        }
        Date result = null;
        try {
            result = simpleDateFormat.parse(dateString);
        }
        catch (final ParseException ex) {
            ex.printStackTrace();
        }
        return result == null ? 0L : result.getTime();
    }
    
    public static String formatTime(final Context context, final long allSecond) {
        final long n = allSecond / 3600L;
        final long n2 = allSecond % 3600L;
        final long lng = n2 / 60L;
        final long lng2 = n2 % 60L;
        final String s = " ";
        final String string = context.getResources().getString(R.string.hour_symbol);
        final String string2 = context.getResources().getString(R.string.minute_symbol);
        final String string3 = context.getResources().getString(R.string.second_symbol);
        if (n > 0L) {
            final StringBuilder sb = new java.lang.StringBuilder();
            final StringBuilder sb2 = sb;
            final long lng3 = n;
            new StringBuilder();
            return sb.append(lng3).append(string).append(s).append(lng).append(string2).toString();
        }
        if (lng > 0L) {
            final StringBuilder sb3 = new java.lang.StringBuilder();
            final StringBuilder sb4 = sb3;
            final long lng4 = lng;
            new StringBuilder();
            return sb3.append(lng4).append(string2).append(s).append(lng2).append(string3).toString();
        }
        final StringBuilder sb5 = new java.lang.StringBuilder();
        final StringBuilder sb6 = sb5;
        final long lng5 = lng2;
        new StringBuilder();
        return sb5.append(lng5).append(string3).toString();
    }
    
    public static String formatDurationDayHourMinute(long durationMilliseconds, final String daySymbol, final String hourSymbol, final String minuteSymbol) {
        final long n = durationMilliseconds / 86400000L;
        final long n2 = durationMilliseconds;
        durationMilliseconds = n2 % 86400000L / 3600000L;
        final long n3 = n2 % 3600000L;
        final long lng = n3 / 60000L;
        final long n4 = n3 % 60000L / 1000L;
        String str = "";
        if (n > 0L) {
            final StringBuilder sb = new java.lang.StringBuilder();
            final StringBuilder sb2 = sb;
            final long lng2 = durationMilliseconds;
            new StringBuilder();
            str = sb.append(lng2).append(daySymbol).toString();
        }
        if (durationMilliseconds > 0L) {
            str = str + durationMilliseconds + hourSymbol;
        }
        if (lng > 0L) {
            str = str + lng + minuteSymbol;
        }
        else if (n4 > 0L) {
            str = str + 1 + minuteSymbol;
        }
        return str;
    }
    
    public static String formatDuration(long durationMilliseconds, final String hourSymbol, final String minuteSymbol) {
        final long n = durationMilliseconds / 3600000L;
        final long n2 = durationMilliseconds % 3600000L;
        durationMilliseconds = n2 / 60000L;
        final long n3 = n2 % 60000L / 1000L;
        String s;
        if (n > 0L) {
            final StringBuilder sb = new java.lang.StringBuilder();
            final StringBuilder sb2 = sb;
            final long lng = n;
            new StringBuilder();
            s = sb.append(lng).append(hourSymbol).append(durationMilliseconds).append(minuteSymbol).toString();
        }
        else {
            if (n3 > 0L && durationMilliseconds <= 0L) {
                durationMilliseconds = 1L;
            }
            final StringBuilder sb3 = new java.lang.StringBuilder();
            final StringBuilder sb4 = sb3;
            final long lng2 = durationMilliseconds;
            new StringBuilder();
            s = sb3.append(lng2).append(minuteSymbol).toString();
        }
        return s;
    }
    
    public static String formatDurationHHMMSS(final long durationMilliseconds) {
        return formatDuration(durationMilliseconds, ResManager.getString(R.string.hour_symbol), ResManager.getString(R.string.minute_symbol), ResManager.getString(R.string.second_symbol));
    }
    
    public static String formatDuration(long durationMilliseconds, final String hourSymbol, final String minuteSymbol, final String secondSymbol) {
        final long n = durationMilliseconds = 3600000L;
        final long n2 = 60000L;
        final long l = durationMilliseconds / n;
        final long n3 = durationMilliseconds %= durationMilliseconds;
        final long n4 = n2;
        durationMilliseconds /= n2;
        final long n5 = n3 % n4 / 1000L;
        final Locale default1 = Locale.getDefault();
        final Object[] args;
        final Object[] array = args = new Object[3];
        final long i = n5;
        final Object[] array2 = args;
        final long j = durationMilliseconds;
        args[0] = l;
        array2[1] = j;
        array[2] = i;
        return String.format(default1, "%02d:%02d:%02d", args);
    }
    
    public static String formatDurationHHMMSSExact(long durationMilliseconds) {
        final long n = durationMilliseconds = 3600000L;
        final long n2 = 60000L;
        final long l = durationMilliseconds / n;
        final long n3 = durationMilliseconds %= durationMilliseconds;
        final long n4 = n2;
        durationMilliseconds /= n2;
        final String format = new DecimalFormat("00.0").format(n3 % n4 / 1000.0f);
        final StringBuilder sb = new StringBuilder();
        final Locale default1 = Locale.getDefault();
        final Object[] args;
        final Object[] array = args = new Object[2];
        final long i = durationMilliseconds;
        args[0] = l;
        array[1] = i;
        return sb.append(String.format(default1, "%02d:%02d:", args)).append(format).toString();
    }
    
    public static String formatDurationExact(long durationMilliseconds, final boolean exact) {
        final long n = 3600000L;
        final long n2 = 60000L;
        final long lng = durationMilliseconds / n;
        final long n3 = durationMilliseconds %= n;
        final long n4 = n2;
        durationMilliseconds /= n2;
        final float n5 = n3 % n4 / 1000.0f;
        String str = "";
        if (lng > 0L) {
            str = str + lng + ResManager.getString(R.string.hour_symbol);
        }
        if (durationMilliseconds > 0L) {
            str = str + durationMilliseconds + ResManager.getString(R.string.minute_symbol);
        }
        if (n5 > 0.0f) {
            String pattern;
            if (exact) {
                pattern = "0.0";
            }
            else {
                pattern = "0";
            }
            final DecimalFormat decimalFormat = new DecimalFormat(pattern);
            str = str + decimalFormat.format(n5) + ResManager.getString(R.string.second_symbol);
        }
        return str;
    }
    
    public static String formatDurationSecondRoundUp(final long durationMilliseconds) {
        return formatDurationSecondRoundUp(durationMilliseconds, true);
    }
    
    public static String formatDurationSecondRoundUp(final long durationMilliseconds, final boolean forceShowHour) {
        final int second = second(durationMilliseconds);
        final int i = second / 3600;
        final int n = second % 3600;
        final int j = n / 60;
        final int n2 = n % 60;
        if (!forceShowHour && i <= 0) {
            final Locale default1 = Locale.getDefault();
            final Object[] args;
            final Object[] array = args = new Object[2];
            final int k = n2;
            args[0] = j;
            array[1] = k;
            return String.format(default1, "%02d:%02d", args);
        }
        final Locale default2 = Locale.getDefault();
        final Object[] args2;
        final Object[] array2 = args2 = new Object[3];
        final int l = n2;
        final Object[] array3 = args2;
        final int m = j;
        args2[0] = i;
        array3[1] = m;
        array2[2] = l;
        return String.format(default2, "%02d:%02d:%02d", args2);
    }
    
    public static String formatDurationMS(long durationMilliseconds) {
        final long n2;
        final long n = n2 = (durationMilliseconds /= 1000L) / 60L;
        durationMilliseconds %= 60L;
        if (n == 0L) {
            return ResManager.getString(R.string.format_seconds, durationMilliseconds);
        }
        if (durationMilliseconds == 0L) {
            return ResManager.getString(R.string.format_minutes, n2);
        }
        return ResManager.getString(R.string.format_minutes_seconds, ResManager.getString(R.string.format_minutes, n2), ResManager.getString(R.string.format_seconds, durationMilliseconds));
    }
    
    public static String formatDurationMinuteSeconds(final long durationMilliseconds) {
        final int second = second(durationMilliseconds);
        final int i = second / 60;
        final int n = second % 60;
        final Locale default1 = Locale.getDefault();
        final Object[] args;
        final Object[] array = args = new Object[2];
        final int j = n;
        args[0] = i;
        array[1] = j;
        return String.format(default1, "%02d:%02d", args);
    }
    
    public static String formatDuration(final long durationMilliseconds) {
        return formatDuration(durationMilliseconds, ResManager.getString(R.string.hour_symbol), ResManager.getString(R.string.minute_symbol));
    }
    
    public static String formatDurationDayHourMinute(final long durationMilliseconds) {
        return formatDurationDayHourMinute(durationMilliseconds, ResManager.getString(R.string.day_symbol), ResManager.getString(R.string.hour_symbol), ResManager.getString(R.string.minute_symbol));
    }
    
    public static String format(final long time, final SimpleDateFormat format) {
        return format.format(time);
    }
    
    public static String format(final long time, final SimpleDateFormat format, final String timeZone) {
        if (!StringUtils.isNullOrEmpty(timeZone)) {
            format.setTimeZone(TimeZone.getTimeZone(timeZone));
        }
        return format(time, format);
    }
    
    public static String getBootUpTime() {
        long t;
        if ((t = SystemClock.elapsedRealtime() / 1000L) == 0L) {
            t = 1L;
        }
        return a(t);
    }
    
    private static String a(final int n) {
        if (n >= 10) {
            return String.valueOf(n);
        }
        return "0" + String.valueOf(n);
    }
    
    private static String a(final long t) {
        return (int)(t / 3600L) + ":" + a((int)(t / 60L % 60L)) + ":" + a((int)(t % 60L));
    }
    
    public static int compareDate(@Nullable final Date o1, @Nullable final Date o2) {
        if (o1 == null && o2 == null) {
            return 0;
        }
        if (o1 == null) {
            return -1;
        }
        if (o2 == null) {
            return 1;
        }
        return o1.compareTo(o2);
    }
    
    public static boolean laterThan(@Nullable final Date o1, @Nullable final Date o2) {
        return compareDate(o1, o2) > 0;
    }
    
    public static int hour(final long time) {
        return (int)Math.ceil((double)(time / 3600L / 1000L));
    }
    
    public static int minute(final long time) {
        return (int)Math.ceil(time / 1000.0f / 60.0f);
    }
    
    public static int second(final long time) {
        return (int)Math.ceil(time / 1000.0f);
    }
    
    public static long getDateTime(@Nullable final Date date) {
        return (date == null) ? 0L : date.getTime();
    }
    
    public static SimpleDateFormat getSimpleDateFormat(final String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault());
    }
    
    public static SimpleDateFormat getSimpleDateFormatWithTimeZone(final String pattern, final String timeZoneId) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        if (StringUtils.isNotBlank(timeZoneId)) {
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneId));
        }
        return simpleDateFormat;
    }
    
    public static Calendar getCalendar(final int year, final int month, final int day, final int hour, final int minute) {
        final Calendar instance = Calendar.getInstance();
        instance.set(1, year);
        instance.set(2, month - 1);
        instance.set(5, day);
        instance.set(11, hour);
        instance.set(12, minute);
        return instance;
    }
    
    public static void setCalendar(final Calendar c, final int hour, final int minute) {
        c.set(11, hour);
        c.set(12, minute);
    }
    
    public static List<Date> getDatesRangIn(final Date startDate, final Date endDate) {
        final Calendar instance;
        final Calendar calendar = instance = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        final Calendar instance2;
        final Calendar calendar2 = instance2 = Calendar.getInstance();
        calendar2.setTime(endDate);
        calendar2.set(11, 0);
        calendar2.set(12, 0);
        calendar2.set(13, 0);
        calendar2.set(14, 0);
        final ArrayList<Date> list = new ArrayList<>();
        while (!instance.after(instance2)) {
            final Calendar calendar3 = instance;
            list.add(instance.getTime());
            calendar3.add(5, 1);
        }
        return list;
    }
    
    public static int countDaysRangIn(final Date startDate, final Date endDate) {
        final Calendar instance;
        final Calendar calendar = instance = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        final Calendar instance2;
        final Calendar calendar2 = instance2 = Calendar.getInstance();
        calendar2.setTime(endDate);
        calendar2.set(11, 0);
        calendar2.set(12, 0);
        calendar2.set(13, 0);
        calendar2.set(14, 0);
        int n = 0;
        while (!instance.after(instance2)) {
            final Calendar calendar3 = instance;
            ++n;
            calendar3.add(5, 1);
        }
        return n;
    }
    
    public static Date getNowDate() {
        return new Date();
    }
    
    public static Date monthStart(final Date date) {
        final Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return getMonthBegin(instance);
    }
    
    public static Date monthEnd(final Date date) {
        final Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return getMonthEnd(instance);
    }
    
    public static Date getPreMonthDate(final Date date) {
        final Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(2, -1);
        return instance.getTime();
    }
    
    public static Date getNextMonthDate(final Date date) {
        final Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(2, 1);
        return instance.getTime();
    }
    
    public static Date weekStart(final Date date) {
        return weekStart(date, 1);
    }
    
    public static Date weekStart(final Date date, final int firstDayOfWeek) {
        final Calendar instance = Calendar.getInstance();
        instance.setFirstDayOfWeek(firstDayOfWeek);
        instance.setTime(date);
        instance.set(7, instance.getFirstDayOfWeek());
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        return instance.getTime();
    }
    
    public static Date weekEnd(final Date date) {
        return weekEnd(date, 1);
    }
    
    public static Date weekEnd(final Date date, final int firstDayOfWeek) {
        final Calendar instance = Calendar.getInstance();
        instance.setFirstDayOfWeek(firstDayOfWeek);
        instance.setTime(date);
        instance.set(7, instance.getFirstDayOfWeek() + 6);
        instance.set(11, 23);
        instance.set(12, 59);
        instance.set(13, 59);
        instance.set(14, 999);
        return instance.getTime();
    }
    
    public static Date getPreWeekDate(final Date date) {
        final Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(5, -7);
        return instance.getTime();
    }
    
    public static Date getNextWeekDate(final Date date) {
        final Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(5, 7);
        return instance.getTime();
    }
    
    public static Date getPreDayDate(final Date date) {
        final Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(5, -1);
        return instance.getTime();
    }
    
    public static List<Long> getPreDayHourPairDate(final Date date) {
        return getPrePairDate(date, 24, 1, 11);
    }
    
    public static List<Long> getPreMonthPairDate(final Date date) {
        return getPrePairDate(date, 30, 1, 5);
    }
    
    public static List<Long> getPreWeekPairDate(final Date date) {
        return getPrePairDate(date, 7, 1, 5);
    }
    
    public static List<Long> getPrePairDate(Date date, final int count, final int step, final int field) {
        final ArrayList list = new ArrayList();
        int n;
        for (int i = 0; i < count; i = n + step) {
            n = i;
            final Calendar instance;
            final Calendar calendar = instance = Calendar.getInstance();
            final ArrayList list2 = list;
            final Calendar calendar2 = instance;
            final ArrayList list3 = list;
            final Date date2 = date;
            final Calendar calendar3 = instance;
            instance.setTime(date);
            calendar3.add(field, step * -1);
            list3.add(date2.getTime());
            list2.add(calendar2.getTime().getTime());
            date = calendar.getTime();
        }
        Collections.sort(list, Long::compare);
        return list;
    }
    
    public static boolean isLaterThanToday(final String dateString) {
        final Date o1 = new Date(parse(dateString, getDateFormatYYYYMMDD_2()));
        final Calendar instance = Calendar.getInstance();
        instance.set(14, 0);
        return laterThan(o1, getDayBegin(instance));
    }
    
    public static LocalDate toLocalDate(final long time) {
        return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
    public static int getMonthDaysCount(final int year, final int month) {
        switch (month) {
            case 2:
                return isLeapYear(year) ? 29 : 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            default:
                throw new IllegalArgumentException("month must be between 1 and 12: " + month);
        }
    }
    
    public static boolean isLeapYear(final int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }
    
    public static long getDayTime(final long dayTime) {
        return MathUtils.clamp(dayTime, 0L, 86400000L);
    }
    
    public static long getWeekTime(final long weekTime) {
        return MathUtils.clamp(weekTime, 0L, 604800000L);
    }
    
    public static long safelyGetTime(final Date date) {
        if (date == null) {
            return 0L;
        }
        return date.getTime();
    }
    
    public static String getTimeStr(final int hour, final int minute) {
        final Locale default1 = Locale.getDefault();
        final Object[] args;
        final Object[] array = args = new Object[2];
        args[0] = hour;
        array[1] = minute;
        return String.format(default1, "%02d:%02d", args);
    }
    
    public static boolean isSameDay(final long firstDay, final long secondDay) {
        return isSameDay(new Date(firstDay), new Date(secondDay));
    }
    
    public static boolean isSameDay(final Date date1, final Date date2) {
        final SimpleDateFormat dateFormatYYYYMMDD_2 = getDateFormatYYYYMMDD_2();
        return dateFormatYYYYMMDD_2.format(date1).equals(dateFormatYYYYMMDD_2.format(date2));
    }
    
    public static Date convertStringToDate(final String dateStr, final String format) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(dateStr);
        }
        catch (final Exception ex) {
            return null;
        }
    }
    
    public static long clearSecondsAndMilliseconds(final long timestamp) {
        final Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(timestamp);
        instance.set(13, 0);
        instance.set(14, 0);
        return instance.getTimeInMillis();
    }
    
    public static long applyTimeToToday(final long originalTimestamp) {
        final Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(originalTimestamp);
        final int value = instance.get(11);
        final int value2 = instance.get(12);
        final Calendar instance2 = Calendar.getInstance();
        instance2.set(11, value);
        instance2.set(12, value2);
        instance2.set(13, 0);
        instance2.set(14, 0);
        return instance2.getTimeInMillis();
    }
    
    public static int minutesOfDay(final long timestamp) {
        final Calendar instance;
        final Calendar calendar = instance = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        return calendar.get(11) * 60 + instance.get(12);
    }
    
    public static boolean isCurrentWeek(final String dateStr, final String format, final int firstDayOfWeek) {
        return isCurrentWeek(convertStringToDate(dateStr, format), firstDayOfWeek);
    }
    
    public static boolean isCurrentWeek(Date date, final int firstDayOfWeek) {
        Date today = Calendar.getInstance().getTime();
        Date start = weekStart(today, firstDayOfWeek);
        Date end = weekEnd(today, firstDayOfWeek);
        return date != null && date.compareTo(start) >= 0 && date.compareTo(end) <= 0;
    }
    
    public static long ceilMinute(final long timeInMill) {
        return (long)Math.ceil((double)(timeInMill / 60000L));
    }
    
    public static SimpleDateFormat getDateFormatYYYYMMDD_HHMMSS() {
        return getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    public static SimpleDateFormat getDateFormatYYYYMMDD_HH_MM_SS() {
        return getSimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
    }
    
    public static SimpleDateFormat getDateFormatYYYYMMDD_HH_MM() {
        return getSimpleDateFormat("yyyy-MM-dd_HH_mm");
    }
    
    public static SimpleDateFormat getDateFormatYYYYMMDD_HHMM() {
        return getSimpleDateFormat("yyyy-MM-dd HH:mm");
    }
    
    public static SimpleDateFormat getDateFormatYYYYMMDD_HHMM_3() {
        return getSimpleDateFormat("yyyy MM-dd HH:mm");
    }
    
    public static SimpleDateFormat getDateFormatYYYYMMDD_2() {
        return getSimpleDateFormat("yyyy-MM-dd");
    }
    
    public static SimpleDateFormat getDateFormatYYYYMMDD_3() {
        return getSimpleDateFormat("dd/MM yyyy");
    }
    
    public static SimpleDateFormat getDateFormatYYYYMMDD_4() {
        return getSimpleDateFormat("yyyyMMdd");
    }
    
    public static SimpleDateFormat getDateFormatYYYYMMDD_5() {
        return getSimpleDateFormat("yyyy MM-dd");
    }
    
    public static SimpleDateFormat getDateFormatYYYYMMDD_T_HHMMSS_Z() {
        return getSimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }
    
    public static SimpleDateFormat getDateFormatYYYYMMDD_T_HHMMSS_Z_2() {
        return getSimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    }
    
    public static SimpleDateFormat getDateFormatYYYYMMDD_T_HHMMSS_Z_3() {
        return getSimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
    }
    
    public static SimpleDateFormat getDateFormatYYYYMMDD_HHMMSS_FOR_FILE_NAME() {
        return getSimpleDateFormat("yyyy-MM-dd HH mm ss");
    }
    
    public static SimpleDateFormat getDateFormatYYYY_MM_DD_HHMMSS() {
        return getSimpleDateFormat("yyyy_MM_dd_HHmmss");
    }
    
    public static SimpleDateFormat getDateFormatHH() {
        return getSimpleDateFormat("HH");
    }
    
    public static SimpleDateFormat getDateFormatHHMM() {
        return getSimpleDateFormat("HH:mm");
    }
    
    public static SimpleDateFormat getDataFormatDD() {
        return getSimpleDateFormat("dd");
    }
    
    public static SimpleDateFormat getDataFormatMM_SS() {
        return getSimpleDateFormat("mm_ss");
    }
    
    public static SimpleDateFormat getDataFormatHHMMSS_SSS() {
        return getSimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    }
    
    public static SimpleDateFormat getDateFormatYYYYMMDD_T_HHMMSSSSSSS() {
        return getSimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
    }
    
    public static SimpleDateFormat getDataFormatYYYYMM() {
        return getSimpleDateFormat("MM/yyyy");
    }
}
