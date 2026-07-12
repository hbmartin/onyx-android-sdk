// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import java.util.HashMap;
import java.util.Map;
import androidx.annotation.Nullable;
import java.text.Normalizer;
import java.util.Locale;
import org.apache.commons.text.StringEscapeUtils;
import java.net.URLEncoder;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import androidx.annotation.NonNull;
import android.graphics.Paint;
import com.onyx.android.sdk.api.utils.NetworkUtil;
import android.util.Patterns;
import java.util.regex.Matcher;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Iterator;
import android.util.Log;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class StringUtils
{
    public static final String UTF16LE = "UTF-16LE";
    public static final String UTF16BE = "UTF-16BE";
    public static final String UTF16 = "UTF-16";
    public static final String UTF8 = "UTF-8";
    public static String punctuation = "[`~!@#$%^&*()+\u2212\u00d7\u00f7=|¦{}'\":;,\\[\\].<>\u3014\u3015/?¿¡·_\u02c7\uff01\uff20\uff03\uffe5\u2026\u2026\uff06\uff08\uff09\u2014\u2014\uff3f\uff5b\uff5d\u3010\u3011\u2018\u2019\uff1b\uff1a\u201d\u201c\u3002\uff0c\u3001\uff1f«»\u2039\u203a\u300a\u300b\u3008\u3009\uff1c\uff1e\u300e\u300f\u300c\u300d\uff3b\uff3d\uff5e\u301d\u301e\uff02\uff07´\ufe5d\ufe5e\uff5c\ufe35\ufe37\ufe39\ufe3f\ufe3d\ufe41\ufe43\ufe3b\ufe3c\ufe44\ufe42\ufe3e\ufe40\ufe3a\ufe38\ufe36\uff3c\uff0f]";
    public static final char[] NUMBER_SPLITTERS;
    public static String END_WIDTH_NUMBER_REGEX = "\\d+$";
    public static final String PUNCTUATION_PLUS = "+";
    public static final int DEFAULT_GET_TEXT_LANGUAGE_MAX_COUNT = 1000;
    private static Pattern a;
    private static Pattern b;
    private static Pattern c;
    private static String d;
    public static final Character.UnicodeScript[] LETTER_SET;
    
    public static String toStringSafely(final Object obj) {
        return (obj == null) ? "" : obj.toString();
    }
    
    public static boolean isNullOrEmpty(final String string) {
        return string == null || string.trim().length() <= 0;
    }
    
    public static boolean isNotBlank(final String string) {
        return string != null && string.trim().length() > 0;
    }
    
    public static boolean isNotBlank(final CharSequence string) {
        return string != null && string.toString().trim().length() > 0;
    }
    
    public static boolean isBlank(final String string) {
        return isNotBlank(string) ^ true;
    }
    
    public static boolean isInteger(String string) {
        if (isNullOrEmpty(string)) {
            return false;
        }
        if (string.charAt(0) == '-') {
            if (string.length() <= 1) {
                return false;
            }
            final String s = string;
            string = s.substring(1, s.length() - 1);
        }
        for (int i = 0; i < string.length(); ++i) {
            if (!Character.isDigit(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static String utf8(final byte[] data) {
        final String s = "";
        if (data == null) {
            return s;
        }
        String s2;
        try {
            s2 = new String(data, StandardCharsets.UTF_8);
        }
        catch (final Exception ex) {
            Log.w("", (Throwable)ex);
            s2 = s;
        }
        return s2;
    }
    
    public static String utf16le(final byte[] data) {
        final String s = "";
        if (data == null) {
            return s;
        }
        String s2;
        try {
            s2 = new String(data, "UTF-16LE");
        }
        catch (final Exception ex) {
            Log.w("", (Throwable)ex);
            s2 = s;
        }
        return s2;
    }
    
    public static String utf16(final byte[] data) {
        final String s = "";
        String s2;
        try {
            s2 = new String(data, "UTF-16");
        }
        catch (final Exception ex) {
            s2 = s;
        }
        return s2;
    }
    
    public static byte[] utf16leBuffer(String text) {
        byte[] result = null;
        try {
            result = text.getBytes("UTF-16LE");
        }
        catch (final Exception ex) {}
        return result;
    }
    
    public static String bytesToString(final byte[] data, final String encoding) {
        final String s = "";
        String s2;
        try {
            s2 = new String(data, encoding);
        }
        catch (final Exception ex) {
            Log.e(StringUtils.class.getName(), encoding, (Throwable)ex);
            s2 = s;
        }
        return s2;
    }
    
    public static String join(final Iterable<?> elements, final String delimiter) {
        final StringBuilder sb = new StringBuilder();
        final Iterator<?> iterator = elements.iterator();
        while (iterator.hasNext()) {
            final StringBuilder sb2 = sb;
            final Object next = iterator.next();
            if (sb2.length() > 0) {
                sb.append(delimiter);
            }
            sb.append(next);
        }
        return sb.toString();
    }
    
    public static String joinWithUnderLine(final Iterable<?> elements) {
        return join(elements, "_");
    }
    
    public static List<String> split(String text, final HashSet<Character> splitter) {
        text = safelyGetStr(text);
        final ArrayList list = new ArrayList();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); ++i) {
            final char char1;
            sb.append(char1 = text.charAt(i));
            if (splitter.contains(char1)) {
                if (!isDigitSplitter(safelyFindChar(text, i - 1), char1, safelyFindChar(text, i + 1))) {
                    final StringBuilder sb2 = sb;
                    list.add(sb.toString());
                    sb2.delete(0, sb2.length());
                }
            }
        }
        if (sb.length() > 0) {
            list.add(sb.toString());
        }
        return list;
    }
    
    public static boolean isDigitSplitter(final Character first, final Character second, final Character third) {
        return isDigitChar(first) && isDigitChar(third) && isDigitSplitter(second);
    }
    
    public static boolean isDigitSplitter(final Character c) {
        return Arrays.binarySearch(StringUtils.NUMBER_SPLITTERS, c) != -1;
    }
    
    public static List<String> split(final String string, final String delimiter) {
        if (isNullOrEmpty(string)) {
            return new ArrayList<String>();
        }
        return Arrays.asList(string.split(delimiter));
    }
    
    public static List<String> splitLetterAndNumber(final String source) {
        final ArrayList list = new ArrayList();
        final Matcher matcher = Pattern.compile("([^0-9]*)([0-9]{0,10})").matcher(source);
        while (matcher.find()) {
            final String group;
            if (isNotBlank(group = matcher.group(1))) {
                list.add(group);
            }
            final String group2;
            if (isNotBlank(group2 = matcher.group(2))) {
                list.add(group2);
            }
        }
        return list;
    }
    
    public static List<String> splitByLength(final String string, final int length) {
        final ArrayList list = new ArrayList();
        if (getLength(string) <= length) {
            final ArrayList list2 = list;
            list2.add(string);
            return list2;
        }
        final StringBuilder sb = new StringBuilder();
        final StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < string.length(); ++i) {
            final char char1;
            if (isDigitChar(char1 = string.charAt(i))) {
                sb2.append(char1);
            }
            else if (isDigitSplitter(safelyFindChar(string, i - 1), char1, safelyFindChar(string, i + 1))) {
                sb2.append(char1);
            }
            else {
                if (sb.length() + sb2.length() < length) {
                    final StringBuilder sb3 = sb2;
                    sb.append(sb2.toString());
                    sb3.delete(0, sb3.length());
                }
                else {
                    final StringBuilder sb4 = sb2;
                    final StringBuilder sb5 = sb;
                    final StringBuilder sb6 = sb2;
                    final StringBuilder sb7 = sb;
                    list.add(sb.toString());
                    sb7.delete(0, sb7.length());
                    sb5.append(sb6.toString());
                    sb4.delete(0, sb4.length());
                }
                final StringBuilder sb8 = sb;
                sb8.append(char1);
                if (sb8.length() >= length) {
                    final StringBuilder sb9 = sb;
                    list.add(sb.toString());
                    sb9.delete(0, sb9.length());
                }
            }
        }
        if (sb.length() > 0) {
            final StringBuilder sb10 = sb;
            list.add(sb.toString());
            sb10.delete(0, sb10.length());
        }
        return list;
    }
    
    public static String subStartString(final String content, int start) {
        final int n = content.length() - 1;
        final int a = 0;
        if (start >= n) {
            start = 0;
        }
        return content.substring(Math.max(a, start));
    }
    
    private static String a(final String content, int end) {
        final int beginIndex = 0;
        final int length = content.length();
        if (end <= 0) {
            end = length;
        }
        return content.substring(beginIndex, Math.min(length, end));
    }
    
    public static String splitAndTrimBySpace(final String content, final boolean leftTrim) {
        if (isNullOrEmpty(content)) {
            return "";
        }
        String s;
        if (leftTrim) {
            final String leftTrim2 = leftTrim(content);
            s = subStartString(leftTrim2, leftTrim2.indexOf(32));
        }
        else {
            final String rightTrim = rightTrim(content);
            s = a(rightTrim, rightTrim.lastIndexOf(32));
        }
        return s;
    }
    
    public static String deleteNewlineSymbol(String content) {
        if (!isNullOrEmpty(content)) {
            content = content.replaceAll("\r\n", " ").replaceAll("\n", " ");
        }
        return content;
    }
    
    public static String trimNewlineSymbol(String content) {
        if (isNotBlank(content)) {
            content = content.replaceAll("\r\n", "\n");
        }
        return content;
    }
    
    public static String leftTrim(final String content) {
        if (isNullOrEmpty(content)) {
            return content;
        }
        int n;
        int n2;
        for (n = 0, n2 = content.length() - 1; n <= n2 && content.charAt(n) <= ' '; ++n) {}
        if (n == 0) {
            return content;
        }
        return content.substring(n, n2 + 1);
    }
    
    public static String rightTrim(final String content) {
        if (isNullOrEmpty(content)) {
            return content;
        }
        final int beginIndex = 0;
        int index;
        int n;
        for (n = (index = content.length() - 1); index >= 0 && content.charAt(index) <= ' '; --index) {}
        if (index == n) {
            return content;
        }
        return content.substring(beginIndex, index + 1);
    }
    
    public static String fullTrim(final String content) {
        return rightTrim(leftTrim(content));
    }
    
    public static String substring(final String content, int beginIndex, final int endIndex) {
        if (isNullOrEmpty(content)) {
            return "";
        }
        if (endIndex > content.codePointCount(0, content.length())) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        while (beginIndex < endIndex) {
            sb.appendCodePoint(content.codePointAt(beginIndex));
            ++beginIndex;
        }
        return sb.toString();
    }
    
    public static String trimBlankSpace(String input) {
        if (isNotBlank(input)) {
            input = input.trim().replaceAll(" ", "").replaceAll(" ", "");
        }
        return input;
    }
    
    public static String trim(String input) {
        if (isNotBlank(input)) {
            input = a(input.trim()).replace("\u0000", "").replace("\\u0000", "").replaceAll("\\u0000", "").replaceAll("\\\\u0000", "");
        }
        return input;
    }
    
    private static String a(String trim) {
        int length;
        int n;
        for (length = trim.length(), n = 0; n < length && trim.charAt(n) == '\u3000'; ++n) {}
        while (n < length && trim.charAt(length - 1) == '\u3000') {
            --length;
        }
        if (n > 0 || length < trim.length()) {
            trim = trim.substring(n, length);
        }
        return trim;
    }
    
    public static String trimPunctuation(String input) {
        if (isNullOrEmpty(input = trim(input))) {
            return input;
        }
        int n;
        for (n = 0; n < input.length() && StringUtils.punctuation.contains(String.valueOf(input.charAt(n))); ++n) {}
        if (n > input.length() - 1) {
            return "";
        }
        int index;
        for (index = input.length() - 1; index > n && StringUtils.punctuation.contains(String.valueOf(input.charAt(index))); --index) {}
        return input.substring(n, index + 1);
    }
    
    public static boolean containsPunctuation(final String text) {
        if (isNullOrEmpty(text)) {
            return false;
        }
        for (int i = 0; i < text.length(); ++i) {
            if (StringUtils.punctuation.contains(String.valueOf(text.charAt(i)))) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isPunctuationString(final String text) {
        if (isNullOrEmpty(text)) {
            return false;
        }
        for (int i = 0; i < text.length(); ++i) {
            if (!StringUtils.punctuation.contains(String.valueOf(text.charAt(i)))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isAlpha(final char ch) {
        return ('A' <= ch && ch <= 'z') || ('\u00c0' <= ch && ch <= '\u00d6') || ('\u00d8' <= ch && ch <= '\u00f6') || ('\u00f8' <= ch && ch <= '\u00ff') || ('\u0100' <= ch && ch <= '\u017f') || ('\u0180' <= ch && ch <= '\u024f') || '\u0386' == ch || ('\u0388' <= ch && ch <= '\u03ff') || ('\u0400' <= ch && ch <= '\u0481') || ('\u048a' <= ch && ch <= '\u04ff') || ('\u0500' <= ch && ch <= '\u052f') || ('\u1e00' <= ch && ch <= '\u1eff') || ('\u0600' <= ch && ch <= '\u06ff') || ('\u0750' <= ch && ch <= '\u077f') || ('\ufb50' <= ch && ch <= '\ufdff') || ('\ufe70' <= ch && ch <= '\ufeff');
    }
    
    public static boolean isLetter(final char ch) {
        try {
            return Arrays.asList(StringUtils.LETTER_SET).contains(Character.UnicodeScript.of(ch));
        }
        catch (final Exception ex) {
            return false;
        }
    }
    
    public static boolean isAsciiControl(final char ch) {
        return (ch >= '\0' && ch < ' ') || ch == '\u007f';
    }
    
    public static boolean isUrl(final String url) {
        return !isNullOrEmpty(url) && Patterns.WEB_URL.matcher(url).matches();
    }
    
    public static String removeHttpScheme(String url) {
        String[] schemeList;
        for (int length = (schemeList = NetworkUtil.schemeList).length, i = 0; i < length; ++i) {
            url = removePrefixIgnoreCase(url, schemeList[i], "");
        }
        return url;
    }
    
    public static String appendHttpScheme(final String url) {
        return appendPrefix(url, "http", "http://");
    }
    
    public static String appendPrefix(final String str, final String prefix) {
        return appendPrefix(str, prefix, prefix);
    }
    
    public static String appendPrefix(String str, final String prefix, final String replace) {
        if (isNotBlank(str) && !str.startsWith(prefix)) {
            str = replace + str;
        }
        return str;
    }
    
    public static String removePrefix(String str, String prefix, final String replace) {
        if (!safelyEquals(prefix = org.apache.commons.lang3.StringUtils.removeStart(str, prefix), str)) {
            str = replace + prefix;
        }
        return str;
    }
    
    public static String removePrefixIgnoreCase(String str, String prefix, final String replace) {
        if (!safelyEquals(prefix = org.apache.commons.lang3.StringUtils.removeStartIgnoreCase(str, prefix), str)) {
            str = replace + prefix;
        }
        return str;
    }
    
    public static String safelyTrim(final String origin) {
        if (isBlank(origin)) {
            return origin;
        }
        return origin.trim();
    }
    
    public static String safelyGetStr(final String origin) {
        return safelyGetStr(origin, "");
    }
    
    public static String safelyGetStr(final CharSequence origin) {
        if (origin == null) {
            return "";
        }
        return safelyGetStr(origin.toString());
    }
    
    public static String safelyGetStr(final String origin, String defaultValue) {
        if (!isNullOrEmpty(origin)) {
            defaultValue = origin;
        }
        return defaultValue;
    }
    
    public static boolean safelyEquals(final String firstStr, final String secondStr) {
        return (firstStr == null && secondStr == null) || (firstStr != null && secondStr != null && firstStr.equals(secondStr));
    }
    
    public static boolean safelyNotNullEqual(final String firstStr, final String secondStr) {
        return (firstStr != null || secondStr != null) && (firstStr != null && secondStr != null) && firstStr.equals(secondStr);
    }
    
    public static boolean isChanged(final String str1, final String str2) {
        return safelyEquals(str1, str2) ^ true;
    }
    
    public static boolean safelyContains(final String src, final String pattern) {
        return !isNullOrEmpty(src) && src.contains(pattern);
    }
    
    public static boolean containsIgnoreCase(final String src, final String pattern) {
        return !isNullOrEmpty(src) && pattern != null && org.apache.commons.lang3.StringUtils.containsIgnoreCase((CharSequence)src, (CharSequence)pattern);
    }
    
    public static boolean safelyEqualsIgnoreCase(final String firstStr, final String secondStr) {
        return (firstStr == null && secondStr == null) || (firstStr != null && secondStr != null && org.apache.commons.lang3.StringUtils.equalsIgnoreCase((CharSequence)firstStr, (CharSequence)secondStr));
    }
    
    public static int indexOfIgnoreCase(final String src, final String pattern) {
        if (src != null && pattern != null) {
            return org.apache.commons.lang3.StringUtils.indexOfIgnoreCase((CharSequence)src, (CharSequence)pattern);
        }
        return -1;
    }
    
    public static int getLength(final String origin) {
        if (isNullOrEmpty(origin)) {
            return 0;
        }
        return origin.length();
    }
    
    public static int getTextWidth(final Paint paint, final String str) {
        int n = 0;
        if (str != null && str.length() > 0) {
            final int length;
            final float[] array = new float[length = str.length()];
            paint.getTextWidths(str, array);
            for (int i = 0; i < length; ++i) {
                n += (int)Math.ceil(array[i]);
            }
        }
        return n;
    }
    
    public static boolean isCJK(final int codePoint) {
        return isCJK(Character.UnicodeBlock.of(codePoint));
    }
    
    public static boolean isCJK(final char c) {
        return isCJK(Character.UnicodeBlock.of(c));
    }
    
    public static boolean isCJK(final Character.UnicodeBlock ub) {
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || ub == Character.UnicodeBlock.HIRAGANA || ub == Character.UnicodeBlock.KATAKANA || ub == Character.UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS || ub == Character.UnicodeBlock.HANGUL_SYLLABLES || ub == Character.UnicodeBlock.HANGUL_JAMO || ub == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO || ub == Character.UnicodeBlock.HANGUL_JAMO_EXTENDED_B;
    }
    
    public static boolean isCJK(final String s) {
        if (isNullOrEmpty(s)) {
            return false;
        }
        for (int i = 0; i < s.length(); ++i) {
            if (isCJK(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isAlpha(final String s) {
        return !isNullOrEmpty(s) && isAlpha(s.charAt(0));
    }
    
    public static boolean isEquals(final String s1, final String s2) {
        return !isNullOrEmpty(s1) && !isNullOrEmpty(s2) && s1.equals(s2);
    }
    
    public static String getBlankStr(final String origin) {
        if (isNullOrEmpty(origin)) {
            return "";
        }
        return origin;
    }
    
    public static String getHtmlFormatString(final String content) {
        if (content == null) {
            return null;
        }
        return content.replaceAll("\\<.*?>|\\n", "");
    }
    
    public static boolean containsHTML(final String input) {
        return !isBlank(input) && Pattern.compile(".*\\<[^>]+>.*", 32).matcher(input).matches();
    }
    
    public static String changeToHttps(final String url) {
        if (url == null) {
            return null;
        }
        return url.replace("http://", "https://");
    }
    
    public static boolean isMatchCaseInsensitive(@NonNull final String string, @NonNull final String pattern) {
        return Pattern.compile("(?i)" + Pattern.quote(pattern)).matcher(string).find();
    }
    
    public static String readLine(final String filename) throws IOException {
        final BufferedReader bufferedReader2;
        final BufferedReader bufferedReader = bufferedReader2 = new BufferedReader(new FileReader(filename), 256);
        try {
            final String line = bufferedReader.readLine();
            bufferedReader2.close();
            return line;
        }
        finally {
            bufferedReader2.close();
        }
    }
    
    public static boolean isAlphaSentence(final String content) {
        return !isNullOrEmpty(content) && isAlpha(content) && calculateSpaceNumForString(content) > 0;
    }
    
    public static int calculateSpaceNumForString(final String str) {
        if (str == null) {
            return 0;
        }
        if (str.contains(" ")) {
            final String[] split;
            return ((split = str.split(" ")).length > 0) ? (split.length - 1) : 0;
        }
        return 0;
    }
    
    public static String nonBlankValue(final String value, final String fallbackValue) {
        if (isNotBlank(value)) {
            return value;
        }
        return fallbackValue;
    }
    
    public static boolean isPhoneNumber(final String phone) {
        return !isNullOrEmpty(phone) && Pattern.compile("^[0-9]{7,11}$").matcher(phone).matches();
    }
    
    public static boolean isEmail(final String email) {
        return !isNullOrEmpty(email) && Pattern.compile("^([a-zA-Z0-9_+\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9_\\-]+\\.)+))([a-zA-Z0-9_\\-]+)(\\]?)$").matcher(email).matches();
    }
    
    public static String hidePartPhone(final String phone) {
        if (isNullOrEmpty(phone)) {
            return "";
        }
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }
    
    public static String hidePartEmail(final String email) {
        if (isNullOrEmpty(email)) {
            return "";
        }
        return email.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4");
    }
    
    public static int lastNumberOffset(final String str) {
        if (isNullOrEmpty(str)) {
            return -1;
        }
        final Matcher matcher;
        if (!(matcher = Pattern.compile(StringUtils.END_WIDTH_NUMBER_REGEX).matcher(str)).find()) {
            return -1;
        }
        return matcher.start();
    }
    
    public static int findEndNumber(final String str) {
        if (isNullOrEmpty(str)) {
            return -1;
        }
        final int lastNumberOffset;
        if ((lastNumberOffset = lastNumberOffset(str)) >= 0) {
            return NumberUtils.parseInt(str.substring(lastNumberOffset, str.length()));
        }
        return -1;
    }
    
    public static boolean isNumberStr(final String str) {
        return !isNullOrEmpty(str) && StringUtils.c.matcher(str).matches();
    }
    
    public static boolean isMatched(final String regex, final String input) {
        return Pattern.compile(regex).matcher(input).find();
    }
    
    public static Character safelyFindChar(final String str, final int index) {
        if (isNullOrEmpty(str)) {
            return null;
        }
        return (index >= 0 && index < str.length()) ? Character.valueOf(str.charAt(index)) : null;
    }
    
    public static boolean isDigitChar(final Character ch) {
        return ch != null && Character.isDigit(ch);
    }
    
    public static boolean isAllDigit(final String str) {
        if (isNullOrEmpty(str)) {
            return false;
        }
        char[] charArray;
        for (int length = (charArray = str.toCharArray()).length, i = 0; i < length; ++i) {
            if (!isDigitChar(charArray[i])) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean parseBoolean(final String s) {
        try {
            return Boolean.parseBoolean(s);
        }
        catch (final Exception ex) {
            return false;
        }
    }
    
    public static String removeSoftHyphenSymbol(String content) {
        if (isNotBlank(content)) {
            content = content.replaceAll("\u00ad", "");
        }
        return content;
    }
    
    public static String removeNewlineSymbol(String content) {
        if (isNotBlank(content)) {
            content = content.replaceAll("\r\n", "").replaceAll("\n", "");
        }
        return content;
    }
    
    public static String encodeHeaderValue(String value) {
        if (isNullOrEmpty(value)) {
            return "";
        }
        final String s = value = value.replace("\n", "");
        try {
            value = URLEncoder.encode(s, "UTF-8");
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
        return value;
    }
    
    public static String subString(final String str, final int limitLength) {
        if (isNullOrEmpty(str)) {
            return "";
        }
        if (limitLength >= str.length()) {
            return str;
        }
        return substring(str, 0, limitLength);
    }
    
    public static String copyTitle(final String originTitle, final int titleIndex) {
        return originTitle + "(" + titleIndex + ")";
    }
    
    public static String getNewNameOnMove(final String originName, final int index) {
        final Matcher matcher;
        if ((matcher = Pattern.compile("\\d+(?=\\)$)").matcher(originName)).find()) {
            return matcher.replaceFirst(Integer.toString(index));
        }
        return copyTitle(originName, index);
    }
    
    public static void appendTextToLastItem(final List<String> list, final String text) {
        if (list.isEmpty()) {
            list.add(text);
            return;
        }
        list.set(list.size() - 1, list.get(list.size() - 1) + text);
    }
    
    public static String removeInvalidXMLCharacters(final String in) {
        if (isNullOrEmpty(in)) {
            return "";
        }
        return StringUtils.a.matcher(StringUtils.b.matcher(in).replaceAll("<br/>")).replaceAll("");
    }
    
    public static String XMLEscapeHandle(final String content) {
        return isNotBlank(content) ? StringEscapeUtils.escapeXml10(content) : "";
    }
    
    public static boolean startsWith(final String str, final String prefix) {
        return !isNullOrEmpty(str) && !isNullOrEmpty(prefix) && str.startsWith(prefix);
    }
    
    public static boolean startsWithIgnoreCase(final String str, final String prefix) {
        return !isNullOrEmpty(str) && !isNullOrEmpty(prefix) && prefix.regionMatches(true, 0, str, 0, prefix.length());
    }
    
    public static String toUpperCaseLocaleInsensitive(String text) {
        if (!isNullOrEmpty(text)) {
            text = text.toUpperCase(Locale.ROOT);
        }
        return text;
    }
    
    public static String toLowerCaseLocaleInsensitive(String text) {
        if (!isNullOrEmpty(text)) {
            text = text.toLowerCase(Locale.ROOT);
        }
        return text;
    }
    
    public static String percentageDefaultFormat(final float number) {
        return String.format(Locale.ROOT, StringUtils.d, number);
    }
    
    public static String safelyLastSubstring(final String text, final String lastStr) {
        if (!isNullOrEmpty(text) && text.contains(lastStr)) {
            return text.substring(0, text.lastIndexOf(lastStr));
        }
        return null;
    }
    
    public static String normalizeStringByNFKCForm(final CharSequence src) {
        return Normalizer.normalize(src, Normalizer.Form.NFKC);
    }
    
    public static boolean isWhiteSpace(final char ch) {
        return ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n' || ch == '\u3000';
    }
    
    public static String singleStripQuotes(final String text) {
        return "'" + text + "'";
    }
    
    public static String replaceFirst(String text, final String prefix, final String newValue) {
        text = safelyGetStr(text);
        if (isNotBlank(prefix) && text.startsWith(prefix)) {
            text = text.replaceFirst(prefix, newValue);
        }
        return text;
    }
    
    public static String replaceLast(String text, final String suffix, final String newValue) {
        text = safelyGetStr(text);
        if (isNotBlank(suffix) && text.endsWith(suffix)) {
            final String s = text;
            text = s.substring(0, s.length() - suffix.length());
            text += safelyGetStr(newValue);
        }
        return text;
    }
    
    public static String ensureNotNull(@Nullable String v) {
        if (isNullOrEmpty(v) || safelyEqualsIgnoreCase(v, "null")) {
            v = "";
        }
        return v;
    }
    
    @Nullable
    public static String getMatchPrefix(final String text, final String... prefixes) {
        if (!isNullOrEmpty(text) && !ArraysUtils.isNullOrEmpty(prefixes)) {
            for (int length = prefixes.length, i = 0; i < length; ++i) {
                final String s;
                if (text.startsWith(s = prefixes[i])) {
                    return s;
                }
            }
            return null;
        }
        return null;
    }
    
    public static String getUrlScheme(final String url) {
        return getMatchPrefix(url, "http://", "https://");
    }
    
    public static String toLowerCase(final String s) {
        if (isNullOrEmpty(s)) {
            return s;
        }
        return s.toLowerCase();
    }
    
    public static String toUpperCase(final String s) {
        if (isNullOrEmpty(s)) {
            return s;
        }
        return s.toUpperCase();
    }
    
    public static String parseTextLanguage(String text, int maxCount) {
        final Map<String, Integer> countCharacterLanguageFrequency;
        if (CollectionUtils.isNullOrEmpty(countCharacterLanguageFrequency = countCharacterLanguageFrequency(text, maxCount))) {
            return "";
        }
        final Map<String, Integer> map = countCharacterLanguageFrequency;
        text = "";
        maxCount = 0;
        final Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry entry;
            if ((int)(entry = iterator.next()).getValue() > maxCount) {
                final Map.Entry entry2 = entry;
                text = (String)entry2.getKey();
                maxCount = (int)entry2.getValue();
            }
        }
        return text;
    }
    
    public static Map<String, Integer> countCharacterLanguageFrequency(final String text, final int maxCount) {
        final HashMap hashMap = new HashMap();
        String key = null;
        char[] charArray;
        for (int length = (charArray = text.toCharArray()).length, i = 0; i < length; ++i) {
            final char c = charArray[i];
            if (maxCount < 0) {
                break;
            }
            if (isAlpha(c)) {
                key = Locale.ENGLISH.getLanguage();
            }
            else if (isWhiteSpace(c) && LocaleUtils.isSameLanguage(key, Locale.ENGLISH)) {
                hashMap.put(key, (int)hashMap.getOrDefault(key, 0) + 1);
                key = null;
            }
            else {
                if (isChineseCharacter(c)) {
                    key = Locale.CHINA.getLanguage();
                }
                else if (isKoreanCharacter(c)) {
                    key = Locale.KOREAN.getLanguage();
                }
                else if (isJapaneseCharacter(c)) {
                    key = Locale.JAPAN.getLanguage();
                }
                else {
                    key = null;
                }
                if (key != null) {
                    hashMap.put(key, (int)hashMap.getOrDefault(key, 0) + 1);
                }
            }
        }
        return hashMap;
    }
    
    public static boolean isChineseCharacter(final char c) {
        return c >= '\u4e00' && c <= '\u9fff';
    }
    
    public static boolean isKoreanCharacter(final char c) {
        return (c >= '\uac00' && c <= '\ud7a3') || (c >= '\u1100' && c <= '\u11ff') || (c >= '\u3130' && c <= '\u318f');
    }
    
    public static boolean isJapaneseCharacter(final char c) {
        return (c >= '\u3040' && c <= '\u309f') || (c >= '\u30a0' && c <= '\u30ff') || (c >= '\u4e00' && c <= '\u9fff');
    }
    
    public static String bytesToHexString(final byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        for (int length = bytes.length, i = 0; i < length; ++i) {
            sb.append(String.format("%02X", bytes[i]));
        }
        return sb.toString().trim();
    }
    
    public static byte[] hexStringToByteArray(final String hexString) {
        if (isNullOrEmpty(hexString)) {
            return new byte[0];
        }
        final int length;
        final byte[] array = new byte[(length = hexString.length()) / 2];
        for (int i = 0; i < length; i += 2) {
            final byte[] array2 = array;
            final int index = i;
            array2[index / 2] = (byte)((Character.digit(hexString.charAt(index), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return array;
    }
    
    static {
        NUMBER_SPLITTERS = new char[] { ',', '.' };
        StringUtils.a = Pattern.compile("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFF]+");
        StringUtils.b = Pattern.compile("\\u000D\\u000A|\\u000D|\\u000A");
        StringUtils.c = Pattern.compile("[0-9]*");
        StringUtils.d = "%.2f%%";
        LETTER_SET = new Character.UnicodeScript[] { Character.UnicodeScript.LATIN, Character.UnicodeScript.CYRILLIC, Character.UnicodeScript.HAN, Character.UnicodeScript.HIRAGANA, Character.UnicodeScript.KATAKANA, Character.UnicodeScript.HANGUL, Character.UnicodeScript.HEBREW };
    }
}
