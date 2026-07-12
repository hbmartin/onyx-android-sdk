// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import java.util.Locale;
import org.jetbrains.annotations.Nullable;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import java.util.regex.Matcher;
import kotlin.jvm.internal.Intrinsics;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0004H\u0002J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0004H\u0002J\u0010\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0011\u001a\u00020\u0004H\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\b\u0010\u0011\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0004H\u0002J\u0010\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u0011\u001a\u00020\u0004H\u0002J\u0010\u0010\u001a\u001a\u00020\u00142\b\u0010\u0011\u001a\u0004\u0018\u00010\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\bR\u000e\u0010\t\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b" }, d2 = { "Lcom/onyx/android/sdk/utils/LanguageDetector;", "", "()V", "HIRAGANA_REGEX", "", "JA_FUNCTION_WORDS_REGEX", "JA_UNIQUE_KANJI", "", "[Ljava/lang/String;", "KANJI_REGEX", "KATAKANA_REGEX", "ZH_FUNCTION_WORDS_REGEX", "regexMap", "Ljava/util/concurrent/ConcurrentHashMap;", "Ljava/util/regex/Pattern;", "calculateScriptRatio", "", "text", "regex", "containsScript", "", "containsUniqueKanji", "detect", "Ljava/util/Locale;", "getRegexPattern", "hasJapaneseVerbStructure", "isJapaneseSentence", "onyxsdk-base_release" })
public final class LanguageDetector
{
    @NotNull
    public static final LanguageDetector INSTANCE;
    @NotNull
    private static final String a = "[\u3040-\u309f]";
    @NotNull
    private static final String b = "[\u30a0-\u30ff]";
    @NotNull
    private static final String c = "[\u4e00-\u9fa5]";
    @NotNull
    private static final String d = "(\u3067\u3059|\u307e\u3059|\u304c|\u3092|\u306b|\u306f|\u306d|\u3088|\u304b)";
    @NotNull
    private static final String e = "(\u7684|\u4e86|\u5417|\u5462|\u5427|\u554a)";
    @NotNull
    private static final ConcurrentHashMap<String, Pattern> f;
    @NotNull
    private static final String[] g;
    
    private LanguageDetector() {
    }
    
    private final boolean b(final String text, final String regex) {
        return this.b(regex).matcher(text).find();
    }
    
    private final Pattern b(final String regex) {
        final Pattern computeIfAbsent = LanguageDetector.f.computeIfAbsent(regex, LanguageDetector::c);
        Intrinsics.checkNotNullExpressionValue((Object)computeIfAbsent, "regexMap.computeIfAbsent\u2026) { Pattern.compile(it) }");
        return computeIfAbsent;
    }
    
    private final double a(final String text, final String regex) {
        if (text.length() == 0) {
            return 0.0;
        }
        final Matcher matcher = this.b(regex).matcher(text);
        int n = 0;
        while (matcher.find()) {
            ++n;
        }
        return n / (double)text.length();
    }
    
    private final boolean a(final String text) {
        final String[] g = LanguageDetector.g;
        int i = 0;
        while (i < g.length) {
            final String s = g[i];
            ++i;
            if (text.contains(s)) {
                return true;
            }
        }
        return false;
    }
    
    private final boolean d(String text) {
        final int n;
        final String[] array2;
        final String[] array = array2 = new String[n = 12];
        array[0] = "\u308b";
        array[1] = "\u3046";
        array[2] = "\u304f";
        array[3] = "\u3059";
        array[4] = "\u3064";
        array[5] = "\u306c";
        array[6] = "\u3080";
        array[7] = "\u3050";
        array[8] = "\u3076";
        array[9] = "\u305f";
        array[10] = "\u3066";
        array[11] = "\u3060";
        final String s = text = new Regex("[\u3002\u3001.,!?]").replace((CharSequence)text, "");
        int i = 0;
        int n2 = s.length() - 1;
        int n3 = 0;
        while (i <= n2) {
            int n4;
            if (n3 == 0) {
                n4 = i;
            }
            else {
                n4 = n2;
            }
            final boolean b = Intrinsics.compare((int)text.charAt(n4), 32) <= 0;
            if (n3 == 0) {
                if (!b) {
                    n3 = 1;
                }
                else {
                    ++i;
                }
            }
            else {
                if (!b) {
                    break;
                }
                --n2;
            }
        }
        if ((text = text.subSequence(i, n2 + 1).toString()).length() == 0) {
            return false;
        }
        final String s2 = text;
        final char char1 = s2.charAt(s2.length() - 1);
        int j = 0;
        while (j < n) {
            final String s3 = array2[j];
            final char c = char1;
            ++j;
            if (s3.indexOf(c) >= 0) {
                return true;
            }
        }
        return false;
    }
    
    private static final Pattern c(final String it) {
        Intrinsics.checkNotNullParameter((Object)it, "it");
        return Pattern.compile(it);
    }
    
    static {
        INSTANCE = new LanguageDetector();
        f = new ConcurrentHashMap<String, Pattern>();
        g = new String[] { "\u4e3c", "\u51ea", "\u8fbc", "\u7551", "\u8fbb", "\u5ce0", "\u698a", "\u6803", "\u8ebe", "\u96eb" };
    }
    
    public final boolean isJapaneseSentence(@Nullable final String text) {
        return Intrinsics.areEqual((Object)this.detect(text), (Object)Locale.JAPANESE);
    }
    
    @NotNull
    public final Locale detect(@Nullable final String text) {
        if (text != null) {
            int i = 0;
            int n = text.length() - 1;
            int n2 = 0;
            while (i <= n) {
                int n3;
                if (n2 == 0) {
                    n3 = i;
                }
                else {
                    n3 = n;
                }
                final boolean b = Intrinsics.compare((int)text.charAt(n3), 32) <= 0;
                if (n2 == 0) {
                    if (!b) {
                        n2 = 1;
                    }
                    else {
                        ++i;
                    }
                }
                else {
                    if (!b) {
                        break;
                    }
                    --n;
                }
            }
            if (text.subSequence(i, n + 1).toString().length() != 0) {
                if (this.b(text, "[\u3040-\u309f]") || this.b(text, "[\u30a0-\u30ff]")) {
                    final Locale japanese = Locale.JAPANESE;
                    Intrinsics.checkNotNullExpressionValue((Object)japanese, "JAPANESE");
                    return japanese;
                }
                if (this.b(text, "(\u3067\u3059|\u307e\u3059|\u304c|\u3092|\u306b|\u306f|\u306d|\u3088|\u304b)")) {
                    final Locale japanese2 = Locale.JAPANESE;
                    Intrinsics.checkNotNullExpressionValue((Object)japanese2, "JAPANESE");
                    return japanese2;
                }
                if (this.b(text, "(\u7684|\u4e86|\u5417|\u5462|\u5427|\u554a)")) {
                    final Locale chinese = Locale.CHINESE;
                    Intrinsics.checkNotNullExpressionValue((Object)chinese, "CHINESE");
                    return chinese;
                }
                if (this.a(text, "[\u4e00-\u9fa5]") > 0.9) {
                    final Locale chinese2 = Locale.CHINESE;
                    Intrinsics.checkNotNullExpressionValue((Object)chinese2, "CHINESE");
                    return chinese2;
                }
                if (this.a(text)) {
                    final Locale japanese3 = Locale.JAPANESE;
                    Intrinsics.checkNotNullExpressionValue((Object)japanese3, "JAPANESE");
                    return japanese3;
                }
                if (this.d(text)) {
                    final Locale japanese4 = Locale.JAPANESE;
                    Intrinsics.checkNotNullExpressionValue((Object)japanese4, "JAPANESE");
                    return japanese4;
                }
                final Locale chinese3 = Locale.CHINESE;
                Intrinsics.checkNotNullExpressionValue((Object)chinese3, "CHINESE");
                return chinese3;
            }
        }
        final Locale chinese4 = Locale.CHINESE;
        Intrinsics.checkNotNullExpressionValue((Object)chinese4, "CHINESE");
        return chinese4;
    }
}
