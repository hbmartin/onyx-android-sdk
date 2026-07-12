package com.onyx.android.sdk.utils;

import com.onyx.android.sdk.data.note.NoteConstant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/ZipExtUtils.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004J\u000e\u0010\u000b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004J\u0010\u0010\f\u001a\u00020\t2\b\u0010\r\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u000e\u001a\u00020\t2\b\u0010\r\u001a\u0004\u0018\u00010\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/onyx/android/sdk/utils/ZipExtUtils;", TTFFont.UNKNOWN_FONT_NAME, "()V", "DS_STORE_FILE", TTFFont.UNKNOWN_FONT_NAME, NoteConstant.NOTE_IMAGE_CAHCE_DIR_NAME, TTFFont.UNKNOWN_FONT_NAME, "medias", "isDSStoreFile", TTFFont.UNKNOWN_FONT_NAME, "zipPath", "isEncryptedFile", "isImage", "suffix", "isMedia", "onyxsdk-base_release"})
public final class ZipExtUtils {

    @NotNull
    public static final ZipExtUtils INSTANCE = new ZipExtUtils();

    @NotNull
    private static final String a = "DS_Store";

    @NotNull
    private static final List<String> b;

    @NotNull
    private static final List<String> c;

    private ZipExtUtils() {
    }

    static {
        ArrayList arrayList = new ArrayList();
        b = arrayList;
        ArrayList arrayList2 = new ArrayList();
        c = arrayList2;
        arrayList.add(".jpg");
        arrayList.add(".jpeg");
        arrayList.add(".png");
        arrayList.add(".gif");
        arrayList.add(".bmp");
        arrayList.add(".ttf");
        arrayList.add(".otf");
        arrayList2.add(".mp3");
        arrayList2.add(".mp4");
        arrayList2.add(".avi");
        arrayList2.add(".dvd");
        arrayList2.add(".flac");
        arrayList2.add(".flv");
        arrayList2.add(".ogg");
        arrayList2.add(".rmvb");
        arrayList2.add(".wav");
        arrayList2.add(".wmv");
        arrayList2.add(".3gp");
    }

    public final boolean isImage(@Nullable String suffix) {
        return CollectionsKt.contains(b, suffix);
    }

    public final boolean isMedia(@Nullable String suffix) {
        return CollectionsKt.contains(c, suffix);
    }

    public final boolean isEncryptedFile(@NotNull String zipPath) {
        Intrinsics.checkNotNullParameter(zipPath, "zipPath");
        if (isDSStoreFile(zipPath)) {
            return false;
        }
        Locale locale = Locale.getDefault();
        Intrinsics.checkNotNullExpressionValue(locale, "getDefault()");
        String lowerCase = zipPath.toLowerCase(locale);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(locale)");
        int iLastIndexOf$default = lowerCase.lastIndexOf(FileUtils.FILE_EXTENSION_CHAR);
        if (iLastIndexOf$default < 0) {
            return true;
        }
        String strSubstring = lowerCase.substring(iLastIndexOf$default);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String).substring(startIndex)");
        return (isImage(strSubstring) || isMedia(strSubstring)) ? false : true;
    }

    public final boolean isDSStoreFile(@NotNull String zipPath) {
        Intrinsics.checkNotNullParameter(zipPath, "zipPath");
        return zipPath.contains(a);
    }
}
