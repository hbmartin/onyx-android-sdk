package com.onyx.android.sdk.path;

import kotlin.jvm.functions.Function0;

final class TemplatePaths$a implements Function0<String> {
    final String a;

    TemplatePaths$a(String relativePath) {
        this.a = relativePath;
    }

    @Override
    public String invoke() {
        return AppPaths.INSTANCE.filesDirAbsolutePath(a);
    }
}

final class TemplatePaths$b implements Function0<String> {
    final String a;
    final String b;

    TemplatePaths$b(String relativePath, String userId) {
        this.a = relativePath;
        this.b = userId;
    }

    @Override
    public String invoke() {
        return TemplatePaths.INSTANCE.templateResAbsolutePath(a, b);
    }
}
