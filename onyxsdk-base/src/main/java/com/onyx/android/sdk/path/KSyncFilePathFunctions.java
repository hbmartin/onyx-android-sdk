package com.onyx.android.sdk.path;

import android.os.Build;
import com.onyx.android.sdk.device.EnvironmentUtil;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import kotlin.jvm.functions.Function0;

final class KSyncFilePaths$a implements Function0<List<String>> {
    public static final KSyncFilePaths$a a = new KSyncFilePaths$a();
    KSyncFilePaths$a() {}
    public final List<String> a() {
        KSyncFilePaths paths = KSyncFilePaths.INSTANCE;
        return Arrays.asList(paths.getKSyncExternalFilesPath(), paths.getKSyncAppFilesPath(), paths.getPhoneAppExternalFilesPath());
    }
    @Override public List<String> invoke() { return a(); }
}

final class KSyncFilePaths$b implements Function0<String> {
    public static final KSyncFilePaths$b a = new KSyncFilePaths$b();
    KSyncFilePaths$b() {}
    public final String a() {
        File directory = EnvironmentUtil.getExternalStorageAppFilesDirectory("com.onyx.android.ksync");
        return directory == null ? "" : directory.getAbsolutePath();
    }
    @Override public String invoke() { return a(); }
}

final class KSyncFilePaths$c implements Function0<String> {
    public static final KSyncFilePaths$c a = new KSyncFilePaths$c();
    KSyncFilePaths$c() {}
    public final String a() { return KSyncFilePaths.INSTANCE.getKSyncFilesPath() + File.separator + "cache"; }
    @Override public String invoke() { return a(); }
}

final class KSyncFilePaths$d implements Function0<String> {
    public static final KSyncFilePaths$d a = new KSyncFilePaths$d();
    KSyncFilePaths$d() {}
    public final String a() { return KSyncFilePaths.INSTANCE.getKSyncCacheDir() + File.separator + "data"; }
    @Override public String invoke() { return a(); }
}

final class KSyncFilePaths$e implements Function0<String> {
    public static final KSyncFilePaths$e a = new KSyncFilePaths$e();
    KSyncFilePaths$e() {}
    public final String a() { return EnvironmentUtil.getExternalStorageDirectory() + File.separator + ".ksync"; }
    @Override public String invoke() { return a(); }
}

final class KSyncFilePaths$f implements Function0<String> {
    public static final KSyncFilePaths$f a = new KSyncFilePaths$f();
    KSyncFilePaths$f() {}
    public final String a() {
        KSyncFilePaths paths = KSyncFilePaths.INSTANCE;
        return Build.VERSION.SDK_INT >= 30 ? paths.getKSyncExternalFilesPath() : paths.getKSyncAppFilesPath();
    }
    @Override public String invoke() { return a(); }
}

final class KSyncFilePaths$g implements Function0<List<String>> {
    public static final KSyncFilePaths$g a = new KSyncFilePaths$g();
    KSyncFilePaths$g() {}
    public final List<String> a() {
        KSyncFilePaths paths = KSyncFilePaths.INSTANCE;
        return Arrays.asList(paths.getKSyncExternalFilesPath(), paths.getKSyncAppFilesPath());
    }
    @Override public List<String> invoke() { return a(); }
}

final class KSyncFilePaths$h implements Function0<String> {
    public static final KSyncFilePaths$h a = new KSyncFilePaths$h();
    KSyncFilePaths$h() {}
    public final String a() {
        File directory = EnvironmentUtil.getExternalStorageAppFilesDirectory("com.boox_helper");
        return directory == null ? "" : directory.getAbsolutePath();
    }
    @Override public String invoke() { return a(); }
}
