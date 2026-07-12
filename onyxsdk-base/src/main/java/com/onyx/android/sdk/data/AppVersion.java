// 
// 

package com.onyx.android.sdk.data;

import java.util.List;
import com.onyx.android.sdk.utils.Debug;
import kotlin.jvm.functions.Function1;
import kotlin.collections.CollectionsKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u00042\b\u0010\t\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0004J\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u00042\b\u0010\b\u001a\u0004\u0018\u00010\u00042\b\u0010\t\u001a\u0004\u0018\u00010\u0004J\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u00042\b\u0010\b\u001a\u0004\u0018\u00010\u00042\b\u0010\t\u001a\u0004\u0018\u00010\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0012" }, d2 = { "Lcom/onyx/android/sdk/data/AppVersion;", "", "()V", "VERSION_4_1", "", "currentVersion", "compareVersion", "", "version1", "version2", "convertMultiDottedStringToFloat", "", "input", "isSupportVersion", "", "version", "mergeVersion", "minVersion", "onyxsdk-base_release" })
public final class AppVersion
{
    @NotNull
    public static final AppVersion INSTANCE;
    @NotNull
    public static final String VERSION_4_1 = "4.1";
    @NotNull
    public static final String currentVersion = "4.1";
    
    private AppVersion() {
    }
    
    static {
        INSTANCE = new AppVersion();
    }
    
    public final boolean isSupportVersion(@Nullable final String version) {
        return version == null || version.length() == 0 || this.compareVersion(version, "4.1") <= 0;
    }
    
    @Nullable
    public final String mergeVersion(@Nullable final String version1, @Nullable String version2) {
        if (this.compareVersion(version1, version2) > 0) {
            version2 = version1;
        }
        return version2;
    }
    
    @Nullable
    public final String minVersion(@Nullable final String version1, @Nullable String version2) {
        if (this.compareVersion(version1, version2) <= 0) {
            version2 = version1;
        }
        return version2;
    }
    
    public final int compareVersion(@Nullable final String version1, @Nullable final String version2) {
        return Float.compare(this.convertMultiDottedStringToFloat(version1), this.convertMultiDottedStringToFloat(version2));
    }
    
    public final float convertMultiDottedStringToFloat(@Nullable final String input) {
        if (input == null || input.length() == 0) {
            return 0.0f;
        }
        String[] parts = input.split("\\.");
        StringBuilder normalized = new StringBuilder(parts[0]);
        if (parts.length > 1) normalized.append('.');
        for (int i = 1; i < parts.length; i++) normalized.append(parts[i]);
        try {
            return Float.parseFloat(normalized.toString());
        } catch (Exception ex) {
            Debug.e((Throwable) ex);
            return 0.0f;
        }
    }
}
