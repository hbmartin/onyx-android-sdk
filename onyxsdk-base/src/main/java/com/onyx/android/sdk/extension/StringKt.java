// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.extension;

import java.util.ArrayList;
import android.util.Range;
import java.util.ListIterator;
import kotlin.collections.CollectionsKt;
import com.onyx.android.sdk.utils.LocaleUtils;
import java.util.Locale;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import android.text.style.ForegroundColorSpan;
import android.text.SpannableString;
import java.util.regex.Matcher;
import android.util.Patterns;
import java.util.List;
import kotlin.text.MatchResult;
import com.onyx.android.sdk.utils.NumberUtils;
import kotlin.text.Regex;
import kotlin.jvm.functions.Function0;
import android.graphics.BitmapFactory;
import android.util.Base64;
import kotlin.text.StringsKt;
import kotlin.jvm.internal.Intrinsics;
import android.graphics.Bitmap;
import kotlin.text.CharsKt;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000^\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\r\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b$\u001a0\u0010\u000b\u001a\u00020\f*\u0004\u0018\u00010\f2\b\u0010\r\u001a\u0004\u0018\u00010\u00042\b\u0010\u000e\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010\u001a\f\u0010\u0012\u001a\u00020\u0010*\u0004\u0018\u00010\u0004\u001a\f\u0010\u0013\u001a\u00020\u0014*\u0004\u0018\u00010\u0004\u001a\f\u0010\u0015\u001a\u00020\u0014*\u0004\u0018\u00010\u0004\u001a\f\u0010\u0016\u001a\u00020\u0014*\u0004\u0018\u00010\u0004\u001a\f\u0010\u0017\u001a\u00020\u0014*\u0004\u0018\u00010\u0004\u001a \u0010\u0018\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0004\u0012\u00020\u00100\u0019*\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u0010\u001a\f\u0010\u001b\u001a\u0004\u0018\u00010\u001c*\u00020\u0004\u001a\f\u0010\u001d\u001a\u00020\u0004*\u0004\u0018\u00010\u0004\u001a\u0016\u0010\u001e\u001a\u00020\u0014*\u0004\u0018\u00010\f2\b\u0010\u001f\u001a\u0004\u0018\u00010\f\u001a\n\u0010 \u001a\u00020\u0010*\u00020\u0004\u001a\u001e\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100#0\"*\u00020\u00042\u0006\u0010$\u001a\u00020\u0004\u001a\u000e\u0010%\u001a\u0004\u0018\u00010\u0004*\u0004\u0018\u00010\u0004\u001a\u0016\u0010&\u001a\u00020\u0010*\u0004\u0018\u00010\u00042\b\b\u0002\u0010'\u001a\u00020\u0010\u001a\f\u0010(\u001a\u00020\u0004*\u0004\u0018\u00010\u0004\u001a\f\u0010)\u001a\u00020\u0010*\u0004\u0018\u00010\u0004\u001a%\u0010*\u001a\u0004\u0018\u00010\u0004*\u00020\u00042\u0012\u0010+\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040,\"\u00020\u0004¢\u0006\u0002\u0010-\u001a\u0011\u0010.\u001a\u0004\u0018\u00010\u0010*\u00020\u0004¢\u0006\u0002\u0010/\u001a\f\u00100\u001a\u0004\u0018\u00010\u0004*\u00020\u0004\u001a\u001a\u00101\u001a\u00020\u0004*\u0004\u0018\u00010\u00042\f\u0010'\u001a\b\u0012\u0004\u0012\u00020\u000402\u001a\f\u00103\u001a\u00020\u0014*\u0004\u0018\u00010\u0004\u001a\f\u00104\u001a\u00020\u0014*\u0004\u0018\u00010\u0004\u001a\n\u00105\u001a\u00020\u0014*\u000206\u001a\f\u00105\u001a\u00020\u0014*\u0004\u0018\u00010\u0004\u001a\u001d\u00107\u001a\u00020\u0014*\u0004\u0018\u00010\f\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a\u001d\u00108\u001a\u00020\u0014*\u0004\u0018\u00010\f\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\b\u0003\u0010\u0000\u001a\f\u00109\u001a\u00020\u0014*\u0004\u0018\u00010\u0004\u001a\f\u0010:\u001a\u00020\u0014*\u0004\u0018\u00010\u0004\u001a\f\u0010;\u001a\u00020\u0014*\u0004\u0018\u00010\u0004\u001a\f\u0010<\u001a\u00020\u0014*\u0004\u0018\u00010\u0004\u001a\u0018\u0010=\u001a\u0004\u0018\u00010\u0004*\u0004\u0018\u00010\u00042\b\u0010>\u001a\u0004\u0018\u00010\u0004\u001a\u0012\u0010?\u001a\u00020\u0004*\u00020\u00042\u0006\u0010@\u001a\u00020\u0010\u001a\u0012\u0010A\u001a\u00020\u0014*\u00020\u00042\u0006\u0010B\u001a\u00020\u0004\u001a\u0014\u0010C\u001a\u00020\u0004*\u0004\u0018\u00010\u00042\u0006\u0010\u001a\u001a\u00020\u0010\u001a\n\u0010D\u001a\u00020\u0004*\u00020\u0004\u001a\f\u0010E\u001a\u00020\u0004*\u0004\u0018\u00010\u0004\u001a\f\u0010F\u001a\u00020\u0004*\u0004\u0018\u00010\u0004\u001a\n\u0010G\u001a\u00020\u0004*\u00020\u0004\u001a\u001a\u0010H\u001a\u00020\u0004*\u00020\u00042\u0006\u0010I\u001a\u00020\u00042\u0006\u0010J\u001a\u00020\u0004\u001a\u0018\u0010K\u001a\u0004\u0018\u00010\u0004*\u0004\u0018\u00010\u00042\b\u0010>\u001a\u0004\u0018\u00010\u0004\u001a\u0016\u0010L\u001a\u00020\u0014*\u0004\u0018\u00010\u00042\b\u0010M\u001a\u0004\u0018\u00010\u0004\u001a\u0016\u0010N\u001a\u00020\u0014*\u0004\u0018\u00010\u00042\b\u0010O\u001a\u0004\u0018\u00010\u0004\u001a\u0016\u0010P\u001a\u00020\u0014*\u0004\u0018\u00010\u00042\b\u0010O\u001a\u0004\u0018\u00010\u0004\u001a\u001b\u0010Q\u001a\u0004\u0018\u000106*\u0004\u0018\u00010\u00042\u0006\u0010R\u001a\u00020\u0010¢\u0006\u0002\u0010S\u001a\u0014\u0010T\u001a\u00020\u0014*\u0004\u0018\u00010\u00042\u0006\u0010U\u001a\u00020\u0004\u001a\u0012\u0010V\u001a\u00020\u0004*\u00020\u00042\u0006\u0010W\u001a\u00020\u0010\u001a\u000e\u0010X\u001a\u0004\u0018\u00010\f*\u0004\u0018\u00010\f\u001a\u000e\u0010X\u001a\u0004\u0018\u00010\u0004*\u0004\u0018\u00010\u0004\u001a\u000e\u0010Y\u001a\u0004\u0018\u00010\u0004*\u0004\u0018\u00010\u0004\"\u0016\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006Z" }, d2 = { "PHONE_NUMBER_PATTERN", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "REGEX_CONTAIN_CHAR", "", "REGEX_CONTAIN_CHAR_AND_NUMBER", "REGEX_CONTAIN_NUMBER", "REGEX_IS_EMAIL", "REGEX_SUFFIX_NUMBER", "UTF16LE", "punctuation", "buildSpanText", "", "prefixText", "suffixText", "prefixTextColor", "", "suffixTextColor", "calculateSpaceNumForString", "checkEmailFormat", "", "containCharAndNumber", "containCharacter", "containNumber", "countCharacterLanguageFrequency", "", "maxCount", "decodeBase64ToBitmap", "Landroid/graphics/Bitmap;", "ensureNotNull", "equals", "str", "extractTrailingNumbers", "findKeywordRangeList", "", "Landroid/util/Range;", "keyword", "fullTrim", "getLastIntNumber", "defaultValue", "getLastNumbers", "getLengthOrZero", "getMatchPrefix", "prefixes", "", "(Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/String;", "getSuffixNumber", "(Ljava/lang/String;)Ljava/lang/Integer;", "getUrlScheme", "ifEmpty", "Lkotlin/Function0;", "isAlpha", "isAlphaSentence", "isCJK", "", "isEmpty", "isNotBlank", "isNumberStr", "isPhoneNumber", "isPunctuationString", "isUrl", "leftTrim", "content", "limitSubString", "limitLength", "matchRegex", "regex", "parseTextLanguage", "preAppendUrlScheme", "removeNewlineSymbol", "removeSoftHyphenSymbol", "removeSuffixNumber", "replaceLast", "suffix", "newValue", "rightTrim", "safelyContains", "pattern", "safelyEquals", "secondStr", "safelyEqualsIgnoreCase", "safelyFindChar", "index", "(Ljava/lang/String;I)Ljava/lang/Character;", "startsWithIgnoreCase", "prefix", "subStartString", "start", "trim", "trimNewlineSymbol", "onyxsdk-base_release" })
public final class StringKt
{
    @NotNull
    public static final String UTF16LE = "UTF-16LE";
    private static final Pattern a;
    @NotNull
    public static final String punctuation = "[`~!@#$%^&*()+\u2212\u00d7\u00f7=|¦{}'\":;,\\[\\].<>\u3014\u3015/?¿¡·_\u02c7\uff01\uff20\uff03\uffe5\u2026\u2026\uff06\uff08\uff09\u2014\u2014\uff3f\uff5b\uff5d\u3010\u3011\u2018\u2019\uff1b\uff1a\u201d\u201c\u3002\uff0c\u3001\uff1f«»\u2039\u203a\u300a\u300b\u3008\u3009\uff1c\uff1e\u300e\u300f\u300c\u300d\uff3b\uff3d\uff5e\u301d\u301e\uff02\uff07´\ufe5d\ufe5e\uff5c\ufe35\ufe37\ufe39\ufe3f\ufe3d\ufe41\ufe43\ufe3b\ufe3c\ufe44\ufe42\ufe3e\ufe40\ufe3a\ufe38\ufe36\uff3c\uff0f]";
    @NotNull
    public static final String REGEX_IS_EMAIL = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    @NotNull
    public static final String REGEX_CONTAIN_CHAR = ".*[a-zA-z].*";
    @NotNull
    public static final String REGEX_CONTAIN_NUMBER = ".*[0-9].*";
    @NotNull
    public static final String REGEX_CONTAIN_CHAR_AND_NUMBER = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]$";
    @NotNull
    public static final String REGEX_SUFFIX_NUMBER = "\\d+$";
    
    public static final boolean equals(@Nullable final CharSequence $this$equals, @Nullable final CharSequence str) {
        return StringUtils.equals($this$equals, str);
    }
    
    public static final boolean isEmpty(@Nullable final CharSequence $this$isEmpty) {
        return $this$isEmpty == null || $this$isEmpty.length() == 0;
    }
    
    public static final boolean isNotBlank(@Nullable final CharSequence $this$isNotBlank) {
        return isEmpty($this$isNotBlank) ^ true;
    }
    
    @Nullable
    public static final CharSequence trim(@Nullable final CharSequence $this$trim) {
        if ($this$trim == null) {
            return null;
        }
        int i = 0;
        int n = $this$trim.length() - 1;
        boolean b = false;
        while (i <= n) {
            int n2;
            if (!b) {
                n2 = i;
            }
            else {
                n2 = n;
            }
            final boolean b2 = b;
            final boolean whitespace = CharsKt.isWhitespace($this$trim.charAt(n2));
            if (!b2) {
                if (!whitespace) {
                    b = true;
                }
                else {
                    ++i;
                }
            }
            else {
                if (!whitespace) {
                    break;
                }
                --n;
            }
        }
        return $this$trim.subSequence(i, n + 1);
    }
    
    @Nullable
    public static final String trim(@Nullable final String $this$trim) {
        return StringUtils.trim($this$trim);
    }
    
    @Nullable
    public static final Bitmap decodeBase64ToBitmap(@NotNull final String $this$decodeBase64ToBitmap) {
        Intrinsics.checkNotNullParameter((Object)$this$decodeBase64ToBitmap, "<this>");
        String[] parts = $this$decodeBase64ToBitmap.split(",", -1);
        String encoded = parts.length > 1 ? parts[1] : parts[0];
        byte[] decoded = Base64.decode(encoded, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
    }
    
    @NotNull
    public static final String ifEmpty(@Nullable String $this$ifEmpty, @NotNull final Function0<String> defaultValue) {
        Intrinsics.checkNotNullParameter((Object)defaultValue, "defaultValue");
        if (isEmpty($this$ifEmpty)) {
            $this$ifEmpty = (String)defaultValue.invoke();
        }
        return $this$ifEmpty;
    }
    
    @NotNull
    public static final String subStartString(@NotNull final String $this$subStartString, int start) {
        Intrinsics.checkNotNullParameter((Object)$this$subStartString, "<this>");
        if (start >= $this$subStartString.length()) {
            start = 0;
        }
        final String substring = $this$subStartString.substring(start);
        Intrinsics.checkNotNullExpressionValue((Object)substring, "this as java.lang.String).substring(startIndex)");
        return substring;
    }
    
    public static final int extractTrailingNumbers(@NotNull String $this$extractTrailingNumbers) {
        Intrinsics.checkNotNullParameter((Object)$this$extractTrailingNumbers, "<this>");
        Matcher matcher = Pattern.compile("(\\d+)$").matcher($this$extractTrailingNumbers);
        return matcher.find() ? NumberUtils.parseInt(matcher.group(1)) : NumberUtils.parseInt("");
    }
    
    public static final boolean safelyEqualsIgnoreCase(@Nullable final String $this$safelyEqualsIgnoreCase, @Nullable final String secondStr) {
        return ($this$safelyEqualsIgnoreCase == null && secondStr == null) || ($this$safelyEqualsIgnoreCase != null && secondStr != null && StringUtils.equalsIgnoreCase((CharSequence)$this$safelyEqualsIgnoreCase, (CharSequence)secondStr));
    }
    
    @Nullable
    public static final String fullTrim(@Nullable final String $this$fullTrim) {
        return rightTrim($this$fullTrim, leftTrim($this$fullTrim, $this$fullTrim));
    }
    
    @Nullable
    public static final String rightTrim(@Nullable final String $this$rightTrim, @Nullable final String content) {
        if (content == null || content.length() == 0) {
            return content;
        }
        final int beginIndex = 0;
        int index;
        int n;
        for (n = (index = content.length() - 1); index >= 0 && Intrinsics.compare((int)content.charAt(index), 32) <= 0; --index) {}
        if (index == n) {
            return content;
        }
        final String substring = content.substring(beginIndex, index + 1);
        Intrinsics.checkNotNullExpressionValue((Object)substring, "this as java.lang.String\u2026ing(startIndex, endIndex)");
        return substring;
    }
    
    @Nullable
    public static final String leftTrim(@Nullable final String $this$leftTrim, @Nullable final String content) {
        if (content == null || content.length() == 0) {
            return content;
        }
        int n;
        int n2;
        for (n = 0, n2 = content.length() - 1; n <= n2 && Intrinsics.compare((int)content.charAt(n), 32) <= 0; ++n) {}
        if (n == 0) {
            return content;
        }
        final String substring = content.substring(n, n2 + 1);
        Intrinsics.checkNotNullExpressionValue((Object)substring, "this as java.lang.String\u2026ing(startIndex, endIndex)");
        return substring;
    }
    
    @NotNull
    public static final String replaceLast(@NotNull String $this$replaceLast, @NotNull final String suffix, @NotNull final String newValue) {
        Intrinsics.checkNotNullParameter((Object)$this$replaceLast, "<this>");
        Intrinsics.checkNotNullParameter((Object)suffix, "suffix");
        Intrinsics.checkNotNullParameter((Object)newValue, "newValue");
        if (isNotBlank(suffix) && $this$replaceLast.endsWith(suffix)) {
            final String s = $this$replaceLast;
            final String substring = s.substring(0, s.length() - suffix.length());
            Intrinsics.checkNotNullExpressionValue((Object)substring, "this as java.lang.String\u2026ing(startIndex, endIndex)");
            $this$replaceLast = Intrinsics.stringPlus(substring, (Object)newValue);
        }
        return $this$replaceLast;
    }
    
    public static final boolean isPhoneNumber(@Nullable final String $this$isPhoneNumber) {
        return $this$isPhoneNumber != null && $this$isPhoneNumber.length() != 0 && StringKt.a.matcher($this$isPhoneNumber).matches();
    }
    
    public static final boolean isCJK(final char $this$isCJK) {
        final Character.UnicodeBlock of;
        return (of = Character.UnicodeBlock.of($this$isCJK)) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || of == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || of == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || of == Character.UnicodeBlock.GENERAL_PUNCTUATION || of == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || of == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }
    
    public static final boolean isCJK(@Nullable final String $this$isCJK) {
        if ($this$isCJK == null || $this$isCJK.length() == 0) {
            return false;
        }
        int i = 0;
        while (i < $this$isCJK.length()) {
            final char char1 = $this$isCJK.charAt(i);
            ++i;
            if (isCJK(char1)) {
                return true;
            }
        }
        return false;
    }
    
    @NotNull
    public static final String limitSubString(@NotNull String $this$limitSubString, final int limitLength) {
        Intrinsics.checkNotNullParameter((Object)$this$limitSubString, "<this>");
        if ($this$limitSubString.length() == 0) {
            return "";
        }
        if (limitLength < $this$limitSubString.length()) {
            Intrinsics.checkNotNullExpressionValue((Object)($this$limitSubString = $this$limitSubString.substring(0, limitLength)), "this as java.lang.String\u2026ing(startIndex, endIndex)");
        }
        return $this$limitSubString;
    }
    
    @Nullable
    public static final String trimNewlineSymbol(@Nullable final String $this$trimNewlineSymbol) {
        if (isNotBlank($this$trimNewlineSymbol)) {
            return StringUtils.replaceAll($this$trimNewlineSymbol, "\r\n", "\n");
        }
        return $this$trimNewlineSymbol;
    }
    
    public static final boolean isUrl(@Nullable final String $this$isUrl) {
        return isNotBlank($this$isUrl) && Patterns.WEB_URL.matcher($this$isUrl).matches();
    }
    
    public static final boolean matchRegex(@NotNull final String $this$matchRegex, @NotNull final String regex) {
        Intrinsics.checkNotNullParameter((Object)$this$matchRegex, "<this>");
        Intrinsics.checkNotNullParameter((Object)regex, "regex");
        return Pattern.compile(regex).matcher($this$matchRegex).matches();
    }
    
    public static final boolean checkEmailFormat(@Nullable final String $this$checkEmailFormat) {
        return $this$checkEmailFormat != null && matchRegex($this$checkEmailFormat, "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
    }
    
    public static final boolean containCharacter(@Nullable final String $this$containCharacter) {
        return $this$containCharacter != null && matchRegex($this$containCharacter, ".*[a-zA-z].*");
    }
    
    public static final boolean containNumber(@Nullable final String $this$containNumber) {
        return $this$containNumber != null && matchRegex($this$containNumber, ".*[0-9].*");
    }
    
    public static final boolean containCharAndNumber(@Nullable final String $this$containCharAndNumber) {
        return $this$containCharAndNumber != null && matchRegex($this$containCharAndNumber, "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]$");
    }
    
    @NotNull
    public static final String removeSuffixNumber(@NotNull String $this$removeSuffixNumber) {
        Intrinsics.checkNotNullParameter((Object)$this$removeSuffixNumber, "<this>");
        final Matcher matcher;
        if ((matcher = Pattern.compile("\\d+$").matcher($this$removeSuffixNumber)).find()) {
            Intrinsics.checkNotNullExpressionValue((Object)($this$removeSuffixNumber = $this$removeSuffixNumber.substring(0, matcher.start())), "this as java.lang.String\u2026ing(startIndex, endIndex)");
        }
        return $this$removeSuffixNumber;
    }
    
    @Nullable
    public static final Integer getSuffixNumber(@NotNull final String $this$getSuffixNumber) {
        Intrinsics.checkNotNullParameter((Object)$this$getSuffixNumber, "<this>");
        final Matcher matcher;
        if ((matcher = Pattern.compile("\\d+$").matcher($this$getSuffixNumber)).find()) {
            final String group = matcher.group();
            Intrinsics.checkNotNullExpressionValue((Object)group, "matcher.group()");
            return Integer.parseInt(group);
        }
        return null;
    }
    
    @NotNull
    public static final CharSequence buildSpanText(@Nullable CharSequence text, @Nullable String prefixText, @Nullable String suffixText, final int prefixTextColor, final int suffixTextColor) {
        if (text == null) text = "";
        if (prefixText == null) {
            prefixText = "";
        }
        if (suffixText == null) {
            suffixText = "";
        }
        final SpannableString spannableString = new SpannableString(prefixText + text + suffixText);
        if (prefixText.length() > 0) {
            spannableString.setSpan((Object)new ForegroundColorSpan(prefixTextColor), 0, prefixText.length(), 33);
        }
        if (suffixText.length() > 0) {
            final CharSequence charSequence = (CharSequence)spannableString;
            ((SpannableString)charSequence).setSpan((Object)new ForegroundColorSpan(suffixTextColor), ((SpannableString)charSequence).length() - suffixText.length(), ((SpannableString)charSequence).length(), 33);
        }
        return (CharSequence)spannableString;
    }
    
    @Nullable
    public static final Character safelyFindChar(@Nullable final String $this$safelyFindChar, final int index) {
        if ($this$safelyFindChar == null || $this$safelyFindChar.length() == 0) {
            return null;
        }
        return (index >= 0 && index < $this$safelyFindChar.length()) ? Character.valueOf($this$safelyFindChar.charAt(index)) : null;
    }
    
    @NotNull
    public static final String parseTextLanguage(@Nullable String $this$parseTextLanguage, int maxCount) {
        if ($this$parseTextLanguage == null || $this$parseTextLanguage.length() == 0) {
            return "";
        }
        final Map<String, Integer> countCharacterLanguageFrequency;
        if (CollectionKt.isNullOrEmpty(countCharacterLanguageFrequency = countCharacterLanguageFrequency($this$parseTextLanguage, maxCount))) {
            return "";
        }
        final Map<String, Integer> map = countCharacterLanguageFrequency;
        $this$parseTextLanguage = "";
        maxCount = 0;
        for (final Map.Entry entry : map.entrySet()) {
            String s = (String)entry.getKey();
            final int intValue;
            if ((intValue = ((Number)entry.getValue()).intValue()) > maxCount) {
                if (s == null) {
                    s = "";
                }
                final String s2 = s;
                maxCount = intValue;
                $this$parseTextLanguage = s2;
            }
        }
        return $this$parseTextLanguage;
    }
    
    @NotNull
    public static final String ensureNotNull(@Nullable String $this$ensureNotNull) {
        if ($this$ensureNotNull == null || $this$ensureNotNull.length() == 0 || $this$ensureNotNull.equals("null")) {
            $this$ensureNotNull = "";
        }
        return $this$ensureNotNull;
    }
    
    @NotNull
    public static final Map<String, Integer> countCharacterLanguageFrequency(@NotNull final String $this$countCharacterLanguageFrequency, final int maxCount) {
        Intrinsics.checkNotNullParameter((Object)$this$countCharacterLanguageFrequency, "<this>");
        final HashMap hashMap = new HashMap();
        String key = null;
        final char[] charArray;
        final char[] array = charArray = $this$countCharacterLanguageFrequency.toCharArray();
        Intrinsics.checkNotNullExpressionValue((Object)array, "this as java.lang.String).toCharArray()");
        int i = 0;
        while (i < array.length) {
            final char c = charArray[i];
            ++i;
            if (maxCount < 0) {
                break;
            }
            if (CharKt.isAlpha(c)) {
                key = Locale.ENGLISH.getLanguage();
            }
            else if (CharsKt.isWhitespace(c) && LocaleUtils.isSameLanguage(key, Locale.ENGLISH)) {
                hashMap.put(key, ((Number)hashMap.getOrDefault(key, 0)).intValue() + 1);
                key = null;
            }
            else {
                if (CharKt.isChineseCharacter(c)) {
                    key = Locale.CHINA.getLanguage();
                }
                else if (CharKt.isKoreanCharacter(c)) {
                    key = Locale.KOREAN.getLanguage();
                }
                else if (CharKt.isJapaneseCharacter(c)) {
                    key = Locale.JAPAN.getLanguage();
                }
                else {
                    key = null;
                }
                if (key == null) {
                    continue;
                }
                hashMap.put(key, ((Number)hashMap.getOrDefault(key, 0)).intValue() + 1);
            }
        }
        return hashMap;
    }
    
    public static final boolean isAlpha(@Nullable final String $this$isAlpha) {
        return $this$isAlpha != null && $this$isAlpha.length() != 0 && CharKt.isAlpha($this$isAlpha.charAt(0));
    }
    
    public static final int calculateSpaceNumForString(@Nullable final String $this$calculateSpaceNumForString) {
        if ($this$calculateSpaceNumForString == null || $this$calculateSpaceNumForString.length() == 0) {
            return 0;
        }
        if (!$this$calculateSpaceNumForString.contains(" ")) {
            return 0;
        }
        List list2 = null;
        Label_0120: {
            final List split;
            if (!(split = new Regex(" ").split((CharSequence)$this$calculateSpaceNumForString, 0)).isEmpty()) {
                final List list = split;
                final ListIterator listIterator = list.listIterator(list.size());
                while (listIterator.hasPrevious()) {
                    if (!isEmpty((CharSequence)listIterator.previous())) {
                        list2 = CollectionsKt.take((Iterable)split, listIterator.nextIndex() + 1);
                        break Label_0120;
                    }
                }
            }
            list2 = CollectionsKt.emptyList();
        }
        final Object[] array;
        if ((array = list2.toArray(new String[0])) != null) {
            final String[] array2;
            return ArraysKt.isNotEmpty(array2 = (String[])array) ? (array2.length - 1) : 0;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
    }
    
    public static final boolean isAlphaSentence(@Nullable final String $this$isAlphaSentence) {
        return isAlpha($this$isAlphaSentence) && calculateSpaceNumForString($this$isAlphaSentence) > 0;
    }
    
    public static final boolean isPunctuationString(@Nullable final String $this$isPunctuationString) {
        if ($this$isPunctuationString == null || $this$isPunctuationString.length() == 0) {
            return false;
        }
        int i = 0;
        while (i < $this$isPunctuationString.length()) {
            final char char1 = $this$isPunctuationString.charAt(i);
            ++i;
            if (!"[`~!@#$%^&*()+\u2212\u00d7\u00f7=|¦{}'\":;,\\[\\].<>\u3014\u3015/?¿¡·_\u02c7\uff01\uff20\uff03\uffe5\u2026\u2026\uff06\uff08\uff09\u2014\u2014\uff3f\uff5b\uff5d\u3010\u3011\u2018\u2019\uff1b\uff1a\u201d\u201c\u3002\uff0c\u3001\uff1f«»\u2039\u203a\u300a\u300b\u3008\u3009\uff1c\uff1e\u300e\u300f\u300c\u300d\uff3b\uff3d\uff5e\u301d\u301e\uff02\uff07´\ufe5d\ufe5e\uff5c\ufe35\ufe37\ufe39\ufe3f\ufe3d\ufe41\ufe43\ufe3b\ufe3c\ufe44\ufe42\ufe3e\ufe40\ufe3a\ufe38\ufe36\uff3c\uff0f]".contains(String.valueOf(char1))) {
                return false;
            }
        }
        return true;
    }
    
    @NotNull
    public static final String removeSoftHyphenSymbol(@Nullable final String $this$removeSoftHyphenSymbol) {
        if ($this$removeSoftHyphenSymbol == null || $this$removeSoftHyphenSymbol.length() == 0) {
            return "";
        }
        return new Regex("\u00ad").replace((CharSequence)$this$removeSoftHyphenSymbol, "");
    }
    
    @NotNull
    public static final String removeNewlineSymbol(@Nullable String $this$removeNewlineSymbol) {
        if ($this$removeNewlineSymbol == null || $this$removeNewlineSymbol.length() == 0) {
            return "";
        }
        $this$removeNewlineSymbol = new Regex("\r\n").replace((CharSequence)$this$removeNewlineSymbol, "");
        return new Regex("\n").replace((CharSequence)$this$removeNewlineSymbol, "");
    }
    
    public static final boolean isNumberStr(@Nullable final String $this$isNumberStr) {
        return $this$isNumberStr != null && $this$isNumberStr.length() != 0 && matchRegex($this$isNumberStr, ".*[0-9].*");
    }
    
    public static final boolean safelyEquals(@Nullable final String $this$safelyEquals, @Nullable final String secondStr) {
        return ($this$safelyEquals == null && secondStr == null) || ($this$safelyEquals != null && secondStr != null && StringUtils.equals((CharSequence)$this$safelyEquals, (CharSequence)secondStr));
    }
    
    public static final boolean safelyContains(@Nullable final String $this$safelyContains, @Nullable final String pattern) {
        boolean contains$default;
        if ($this$safelyContains != null && $this$safelyContains.length() != 0 && (pattern != null && pattern.length() != 0)) {
            Intrinsics.checkNotNull((Object)pattern);
            contains$default = $this$safelyContains.contains(pattern);
        }
        else {
            contains$default = false;
        }
        return contains$default;
    }
    
    @Nullable
    public static final String getMatchPrefix(@NotNull final String $this$getMatchPrefix, @NotNull final String... prefixes) {
        Intrinsics.checkNotNullParameter((Object)$this$getMatchPrefix, "<this>");
        Intrinsics.checkNotNullParameter((Object)prefixes, "prefixes");
        if (prefixes.length == 0) {
            return null;
        }
        for (int i = 0; i < prefixes.length; ++i) {
            final String s;
            if ($this$getMatchPrefix.startsWith(s = prefixes[i])) {
                return s;
            }
        }
        return null;
    }
    
    @Nullable
    public static final String getUrlScheme(@NotNull final String $this$getUrlScheme) {
        Intrinsics.checkNotNullParameter((Object)$this$getUrlScheme, "<this>");
        return getMatchPrefix($this$getUrlScheme, "http://", "https://");
    }
    
    @NotNull
    public static final String preAppendUrlScheme(@NotNull final String $this$preAppendUrlScheme) {
        Intrinsics.checkNotNullParameter((Object)$this$preAppendUrlScheme, "<this>");
        if (isNotBlank(getUrlScheme($this$preAppendUrlScheme))) {
            return $this$preAppendUrlScheme;
        }
        return Intrinsics.stringPlus("http://", (Object)$this$preAppendUrlScheme);
    }
    
    @NotNull
    public static final List<Range<Integer>> findKeywordRangeList(@NotNull final String $this$findKeywordRangeList, @NotNull final String keyword) {
        Intrinsics.checkNotNullParameter((Object)$this$findKeywordRangeList, "<this>");
        Intrinsics.checkNotNullParameter((Object)keyword, "keyword");
        if (StringsKt.isBlank((CharSequence)$this$findKeywordRangeList)) {
            return CollectionsKt.emptyList();
        }
        final ArrayList<Range<Integer>> list = new ArrayList<Range<Integer>>();
        for (int indexOf$default = 0; indexOf$default <= $this$findKeywordRangeList.length() - keyword.length() && (indexOf$default = $this$findKeywordRangeList.indexOf(keyword, indexOf$default)) != -1; ++indexOf$default) {
            final ArrayList<Range<Integer>> list2 = list;
            final Range create = Range.create((Comparable)indexOf$default, (Comparable)(indexOf$default + keyword.length()));
            Intrinsics.checkNotNullExpressionValue((Object)create, "create(foundIndex, foundIndex + keyword.length)");
            list2.add((Range<Integer>)create);
        }
        return list;
    }
    
    public static final boolean startsWithIgnoreCase(@Nullable final String $this$startsWithIgnoreCase, @NotNull final String prefix) {
        Intrinsics.checkNotNullParameter((Object)prefix, "prefix");
        return $this$startsWithIgnoreCase != null && $this$startsWithIgnoreCase.length() != 0 && StringsKt.startsWith($this$startsWithIgnoreCase, prefix, true);
    }
    
    public static final int getLengthOrZero(@Nullable String $this$getLengthOrZero) {
        if ($this$getLengthOrZero == null) {
            $this$getLengthOrZero = "";
        }
        return $this$getLengthOrZero.length();
    }
    
    @NotNull
    public static final String getLastNumbers(@Nullable String $this$getLastNumbers) {
        if ($this$getLastNumbers == null) {
            return "";
        }
        Matcher matcher = Pattern.compile("\\d+$").matcher($this$getLastNumbers);
        return matcher.find() ? matcher.group() : "";
    }
    
    public static final int getLastIntNumber(@Nullable final String $this$getLastIntNumber, int defaultValue) {
        final Integer intOrNull;
        if ((intOrNull = StringsKt.toIntOrNull(getLastNumbers($this$getLastIntNumber))) != null) {
            defaultValue = intOrNull;
        }
        return defaultValue;
    }
    
    static {
        a = Pattern.compile("^[0-9]{7,11}$");
    }
}
