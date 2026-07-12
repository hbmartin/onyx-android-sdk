package com.onyx.android.sdk.utils;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function0;

final class FileExtensionUtils$a implements Function0<List<String>> {
    public static final FileExtensionUtils$a a = new FileExtensionUtils$a();

    FileExtensionUtils$a() {}

    public final List<String> a() {
        ArrayList<String> extensions = new ArrayList<>();
        extensions.addAll(FileExtensionUtils.INSTANCE.getDocumentFormatExtension());
        extensions.addAll(FileExtensionUtils.INSTANCE.getPicExtension());
        return extensions;
    }

    @Override
    public List<String> invoke() {
        return a();
    }
}
