package com.onyx.android.sdk.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.jvm.functions.Function1;

final class StorageTypeUtils$b implements Function1<String, String> {
    public static final StorageTypeUtils$b a = new StorageTypeUtils$b();
    StorageTypeUtils$b() {}
    public final String a(String path) {
        if (path.startsWith("/data/") || path.startsWith("/system/")) return "";
        if (path.startsWith("/sdcard/")) return "/sdcard";
        if (path.startsWith("/storage/sdcard0/")) return "/storage/sdcard0";
        if (path.startsWith("/storage/emulated/")) {
            String tail = path.substring("/storage/emulated/".length());
            int slash = tail.indexOf('/');
            return "/storage/emulated/" + (slash < 0 ? tail : tail.substring(0, slash));
        }
        return "";
    }
    @Override public String invoke(String path) { return a(path); }
}

final class StorageTypeUtils$c implements Function1<String, String> {
    public static final StorageTypeUtils$c a = new StorageTypeUtils$c();
    StorageTypeUtils$c() {}
    public final String a(String path) {
        Matcher matcher = Pattern.compile("(/mnt/media_rw/|/storage/)([a-f0-9-]+)").matcher(path);
        return matcher.find() ? matcher.group(1) + matcher.group(2) : "";
    }
    @Override public String invoke(String path) { return a(path); }
}

final class StorageTypeUtils$d implements Function1<String, String> {
    public static final StorageTypeUtils$d a = new StorageTypeUtils$d();
    StorageTypeUtils$d() {}
    public final String a(String path) {
        String keywords = "usbdrive|otg|usb_host|flashdrive|thumb|usb_disk|usbdisk|udisk|usb_otg|usbstorage|usb";
        Matcher matcher = Pattern.compile("(/mnt/media_rw/|/|/storage/)(" + keywords + ")", Pattern.CASE_INSENSITIVE).matcher(path);
        if (!matcher.find()) return "";
        String value = matcher.group();
        int end = matcher.start() + value.length();
        return path.substring(0, end).replaceAll("/+$", "");
    }
    @Override public String invoke(String path) { return a(path); }
}
