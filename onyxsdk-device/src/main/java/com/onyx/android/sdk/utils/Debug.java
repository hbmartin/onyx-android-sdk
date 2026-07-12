package com.onyx.android.sdk.utils;

import android.text.TextUtils;
import android.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Pattern;

public class Debug {
    private static final String a = "Debug";
    private static boolean b = false;
    private static boolean c = false;
    private static boolean d = false;
    private static Pattern e = Pattern.compile("[a-z]+");

    public interface IMessage {
        String getMessage();
    }

    public static void setDebug(boolean debug) {
        b = debug;
    }

    public static boolean getDebug() {
        return b;
    }

    public static boolean isObfuscateLogEnabled() {
        return c;
    }

    public static void setObfuscateLogEnabled(boolean obfuscateLogEnabled) {
        c = obfuscateLogEnabled;
    }

    public static boolean isCompareObfuscateLogEnabled() {
        return d;
    }

    public static void setCompareObfuscateLogEnabled(boolean compareObfuscateLogEnabled) {
        d = compareObfuscateLogEnabled;
    }

    public static void d(String msg) {
        if (b) {
            Log.d(a, msg);
        }
    }

    public static void printStackTraceDebug(String msg) {
        printStackTraceDebug(a, msg);
    }

    public static void v(Class<?> cls, String msg, Object... args) {
        if (b) {
            Log.v(getVerifiedTag(cls), formatString(msg, args));
        }
    }

    public static void i(String msg) {
        Log.i(a, obfuscateLog(msg));
    }

    public static String obfuscateLog(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return "";
        }
        a(msg);
        return isObfuscateLogEnabled() ? b(msg) : msg;
    }

    private static String b(String msg) {
        return e.matcher(msg).replaceAll("");
    }

    private static void a(String msg) {
        if (isCompareObfuscateLogEnabled()) {
            String str = a;
            Log.i(str, "raw : = " + msg);
            Log.i(str, "obfuscate : = " + b(msg));
        }
    }

    public static void w(String msg) {
        Log.w(a, msg);
    }

    public static void e(String msg) {
        if (msg != null) {
            Log.e(a, msg);
        }
    }

    public static String formatString(String str, Object... args) {
        if (str == null) {
            return "";
        }
        if (args.length <= 0) {
            return str;
        }
        try {
            return String.format(null, str, args);
        } catch (Throwable unused) {
            return str;
        }
    }

    public static String getVerifiedTag(Class<?> cls) {
        return c(ClassUtils.getSimpleName(cls));
    }

    private static String c(String tag) {
        return (tag == null || TextUtils.isEmpty(tag.trim())) ? a : tag;
    }

    public static void printStackTraceDebug(String tag, String msg) {
        if (b) {
            Log.v(tag, "", new Exception("Printing detailed debug information.\n Message :" + msg));
        }
    }

    public static void i(String tag, String msg, Object... args) {
        Log.i(tag, obfuscateLog(formatString(msg, args)));
    }

    public static void w(Class<?> cls, String msg, Object... args) {
        Log.w(getVerifiedTag(cls), formatString(msg, args));
    }

    public static void e(String tag, String msg, Object... args) {
        Log.e(tag, formatString(msg, args));
    }

    public static void d(IMessage msg) {
        if (b) {
            Log.d(a, msg.getMessage());
        }
    }

    public static void v(String tag, String msg, Object... args) {
        if (b) {
            Log.v(tag, formatString(msg, args));
        }
    }

    public static void w(Throwable throwable) {
        Log.w(a, throwable);
    }

    public static void e(Class<?> cls, String msg, Object... args) {
        Log.e(getVerifiedTag(cls), formatString(msg, args));
    }

    public static void i(Class<?> cls, String msg, Object... args) {
        i(obfuscateLog(getVerifiedTag(cls)), msg, args);
    }

    public static void w(Class<?> cls, Throwable throwable) {
        Log.w(getVerifiedTag(cls), throwable);
    }

    public static void e(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        Log.e(a, stringWriter.toString());
    }

    public static void d(Throwable tr) {
        if (b) {
            Log.d(a, "", tr);
        }
    }

    public static void w(String tag, String msg, Object... args) {
        Log.w(tag, formatString(msg, args));
    }

    public static void d(Class<?> cls, String msg, Object... args) {
        if (b) {
            Log.d(getVerifiedTag(cls), formatString(msg, args));
        }
    }

    public static void e(Class<?> cls, Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        Log.e(getVerifiedTag(cls), stringWriter.toString());
    }

    public static void d(String tag, String msg, Object... args) {
        if (b) {
            Log.d(tag, formatString(msg, args));
        }
    }

    public static void e(Class<?> cls, String message, Throwable throwable) {
        Log.e(getVerifiedTag(cls), message, throwable);
    }
}
