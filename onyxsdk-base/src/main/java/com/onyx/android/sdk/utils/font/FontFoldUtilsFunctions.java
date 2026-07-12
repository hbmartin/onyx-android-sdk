package com.onyx.android.sdk.utils.font;

import com.onyx.android.sdk.data.FontInfo;
import com.onyx.android.sdk.utils.FileUtils;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/** Original Kotlin closure classes, retained under their binary names. */
final class FontFoldUtils$b implements Function0<List<FontInfo>> {
    static final FontFoldUtils$b a = new FontFoldUtils$b();

    private FontFoldUtils$b() {}

    @Override
    public List<FontInfo> invoke() {
        return new ArrayList<>();
    }
}

final class FontFoldUtils$c implements Function1<FontInfo, String> {
    static final FontFoldUtils$c a = new FontFoldUtils$c();

    private FontFoldUtils$c() {}

    @Override
    public String invoke(FontInfo fontInfo) {
        Intrinsics.checkNotNullParameter(fontInfo, "it");
        return FileUtils.getParent(fontInfo.getFilePath()) + fontInfo.getFontDisplayName();
    }
}
