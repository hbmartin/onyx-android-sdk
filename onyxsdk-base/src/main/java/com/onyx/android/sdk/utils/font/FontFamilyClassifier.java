package com.onyx.android.sdk.utils.font;

import com.onyx.android.sdk.data.FontInfo;
import com.onyx.android.sdk.extension.CollectionKt;
import com.onyx.android.sdk.utils.LogUtils;
import com.onyx.android.sdk.utils.TTFFont;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/font/FontFamilyClassifier.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\u001aJ\u000e\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u0015J\u0014\u0010\u001d\u001a\u0004\u0018\u00010\u0018*\b\u0012\u0004\u0012\u00020\u00180\u0017H\u0002R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u001b\u0010\f\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000b\u001a\u0004\b\r\u0010\tR#\u0010\u000f\u001a\n \u0005*\u0004\u0018\u00010\u00040\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u000b\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00150\u0014X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/onyx/android/sdk/utils/font/FontFamilyClassifier;", TTFFont.UNKNOWN_FONT_NAME, "()V", "BASE_FONT_PATTERN", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "FONT_NAME_REPLACE_REGEX", "Lkotlin/text/Regex;", "getFONT_NAME_REPLACE_REGEX", "()Lkotlin/text/Regex;", "FONT_NAME_REPLACE_REGEX$delegate", "Lkotlin/Lazy;", "FONT_NORMAL_REGEX", "getFONT_NORMAL_REGEX", "FONT_NORMAL_REGEX$delegate", "FONT_WEIGHT_AND_STYLE_PATTERN", "getFONT_WEIGHT_AND_STYLE_PATTERN", "()Ljava/util/regex/Pattern;", "FONT_WEIGHT_AND_STYLE_PATTERN$delegate", "fontNameCache", TTFFont.UNKNOWN_FONT_NAME, TTFFont.UNKNOWN_FONT_NAME, "groupFonts", TTFFont.UNKNOWN_FONT_NAME, "Lcom/onyx/android/sdk/data/FontInfo;", "fonts", TTFFont.UNKNOWN_FONT_NAME, "parseBaseFontNameCached", "fullName", "createFamilyFont", "onyxsdk-base_release"})
public final class FontFamilyClassifier {

    @NotNull
    public static final FontFamilyClassifier INSTANCE;

    @NotNull
    private static final Lazy replaceRegexLazy;

    @NotNull
    private static final Lazy normalRegexLazy;

    @NotNull
    private static final Lazy stylePatternLazy;
    private static final Pattern BASE_FONT_PATTERN;

    @NotNull
    private static final Map<String, String> e;

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/font/FontFamilyClassifier$a.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Lkotlin/text/Regex;", "invoke"})
    static final class a implements Function0<Regex> {
        public static final a a = new a();

        a() {
        }

        @NotNull
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public final Regex invoke() {
            return new Regex("[-_]$");
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/font/FontFamilyClassifier$b.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Lkotlin/text/Regex;", "invoke"})
    static final class b implements Function0<Regex> {
        public static final b a = new b();

        b() {
        }

        @NotNull
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public final Regex invoke() {
            return new Regex("Regular|Normal", RegexOption.IGNORE_CASE);
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/font/FontFamilyClassifier$c.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "invoke"})
    static final class c implements Function0<Pattern> {
        public static final c a = new c();

        c() {
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public final Pattern invoke() {
            List<String> listListOf = CollectionsKt.listOf(new String[]{"Thin", "ExtraLight", "Extralight", "Light", "Regular", "Normal", "Medium", "SemiBold", "Demibold", "Bold", "ExtraBold", "Heavy", "Black"});
            String strJoinToString$default = String.join(LogUtils.ITEM_SEPARATOR, listListOf);
            List listListOf2 = CollectionsKt.listOf(new String[]{"Roman", "Italic"});
            String strJoinToString$default2 = String.join(LogUtils.ITEM_SEPARATOR, listListOf2);
            ArrayList arrayList = new ArrayList();
            for (String str : listListOf) {
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listListOf2, 10));
                Iterator it = listListOf2.iterator();
                while (it.hasNext()) {
                    arrayList2.add(str + "[\\s_\\-]?" + ((String) it.next()));
                }
                CollectionsKt.addAll(arrayList, arrayList2);
            }
            return Pattern.compile("\\b(?:" + strJoinToString$default + '|' + strJoinToString$default2 + '|' + String.join(LogUtils.ITEM_SEPARATOR, arrayList) + ")\\b", 2);
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/font/FontFamilyClassifier$d.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", TTFFont.UNKNOWN_FONT_NAME, "kotlin.jvm.PlatformType", "it", "Lcom/onyx/android/sdk/data/FontInfo;", "invoke"})
    static final class d implements Function1<FontInfo, String> {
        public static final d a = new d();

        d() {
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public final String invoke(@NotNull FontInfo it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return it.getFontUniqueName();
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/font/FontFamilyClassifier$e.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", TTFFont.UNKNOWN_FONT_NAME, "it", TTFFont.UNKNOWN_FONT_NAME, "Lcom/onyx/android/sdk/data/FontInfo;", "invoke", "(Ljava/util/List;)Ljava/lang/Boolean;"})
    static final class e implements Function1<List<? extends FontInfo>, Boolean> {
        public static final e a = new e();

        e() {
        }

        @NotNull
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public final Boolean invoke(@NotNull List<? extends FontInfo> list) {
            Intrinsics.checkNotNullParameter(list, "it");
            return Boolean.valueOf(list.size() > 1);
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/font/FontFamilyClassifier$f.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "Lcom/onyx/android/sdk/data/FontInfo;", "it", TTFFont.UNKNOWN_FONT_NAME, "invoke"})
    static final class f implements Function1<List<? extends FontInfo>, FontInfo> {
        public static final f a = new f();

        f() {
        }

        @Nullable
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public final FontInfo invoke(@NotNull List<? extends FontInfo> list) {
            Intrinsics.checkNotNullParameter(list, "it");
            return FontFamilyClassifier.INSTANCE.a(list);
        }
    }

    private FontFamilyClassifier() {
    }

    private final Regex b() {
        return (Regex) replaceRegexLazy.getValue();
    }

    private final Regex a() {
        return (Regex) normalRegexLazy.getValue();
    }

    private final Pattern c() {
        return (Pattern) stylePatternLazy.getValue();
    }

    static {
        FontFamilyClassifier fontFamilyClassifier = new FontFamilyClassifier();
        INSTANCE = fontFamilyClassifier;
        replaceRegexLazy = LazyKt.lazy(FontFamilyClassifier.b.a);
        normalRegexLazy = LazyKt.lazy(FontFamilyClassifier.a.a);
        stylePatternLazy = LazyKt.lazy(FontFamilyClassifier.c.a);
        BASE_FONT_PATTERN = Pattern.compile("^(.+?)(?:[_\\s-]*(" + fontFamilyClassifier.c().pattern() + "))*$", 6);
        e = new LinkedHashMap();
    }

    @NotNull
    public final List<FontInfo> groupFonts(@NotNull Collection<? extends FontInfo> fonts) {
        Intrinsics.checkNotNullParameter(fonts, "fonts");
        List<FontInfo> sorted = new ArrayList<>(fonts);
        sorted.sort(new FontFamilyClassifier$groupFonts$$inlined$sortedBy$1<FontInfo>());
        Map<String, List<FontInfo>> grouped = new LinkedHashMap<>();
        for (FontInfo font : sorted) {
            String fontUniqueName = font.getFontUniqueName();
            Intrinsics.checkNotNullExpressionValue(fontUniqueName, "it.fontUniqueName");
            String baseName = parseBaseFontNameCached(fontUniqueName);
            grouped.computeIfAbsent(baseName, ignored -> new ArrayList<>()).add(font);
        }
        List<FontInfo> result = new ArrayList<>();
        for (List<FontInfo> family : grouped.values()) {
            if (family.size() > 1) {
                FontInfo familyFont = a(family);
                if (familyFont != null) {
                    result.add(familyFont);
                }
            }
        }
        return result;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0053  */
    @NotNull
    public final String parseBaseFontNameCached(@NotNull String fullName) {
        String strGroup;
        String strReplace;
        Intrinsics.checkNotNullParameter(fullName, "fullName");
        Map<String, String> map = e;
        String str = map.get(fullName);
        String str2 = str;
        if (str == null) {
            Matcher matcher = BASE_FONT_PATTERN.matcher(fullName);
            if (!matcher.find() || (strGroup = matcher.group(1)) == null || (strReplace = INSTANCE.a().replace(strGroup, TTFFont.UNKNOWN_FONT_NAME)) == null) {
                str2 = fullName;
            } else {
                String string = StringsKt.trim(strReplace).toString();
                str2 = string;
                if (string == null) {
                    str2 = fullName;
                }
            }
            map.put(fullName, str2);
        }
        return str2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FontInfo a(List<? extends FontInfo> list) {
        Object obj;
        String fontUniqueName;
        FontInfo fontInfo = (FontInfo) CollectionKt.minBy(list, d.a);
        if (fontInfo == null) {
            return null;
        }
        Iterator<? extends FontInfo> it = list.iterator();
        do {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            FontInfo next = it.next();
            obj = next;
            fontUniqueName = ((FontInfo) next).getFontUniqueName();
            Intrinsics.checkNotNullExpressionValue(fontUniqueName, "it.fontUniqueName");
        } while (!INSTANCE.b().containsMatchIn(fontUniqueName));
        FontInfo fontInfo2 = (FontInfo) obj;
        String filePath = fontInfo2 == null ? null : fontInfo2.getFilePath();
        if (filePath == null) {
            filePath = fontInfo.getFilePath();
        }
        String id = fontInfo.getId();
        Iterator<? extends FontInfo> it2 = list.iterator();
        while (it2.hasNext()) {
            String fontUniqueName2 = ((FontInfo) it2.next()).getFontUniqueName();
            String str = fontUniqueName2;
            if (fontUniqueName2 == null) {
                str = TTFFont.UNKNOWN_FONT_NAME;
            }
            id = Intrinsics.stringPlus(id, str);
        }
        String str2 = filePath;
        FontInfo fontInfo3 = new FontInfo();
        fontInfo3.setFontUniqueName(fontInfo.getFontUniqueName());
        FontFamilyClassifier fontFamilyClassifier = INSTANCE;
        String fontDisplayName = fontInfo.getFontDisplayName();
        Intrinsics.checkNotNullExpressionValue(fontDisplayName, "baseFont.fontDisplayName");
        fontInfo3.setFontDisplayName(fontFamilyClassifier.parseBaseFontNameCached(fontDisplayName));
        if (str2 == null) {
            filePath = TTFFont.UNKNOWN_FONT_NAME;
        }
        fontInfo3.setFilePath(filePath);
        fontInfo3.setId(id);
        fontInfo3.setFontType(fontInfo.getFontType());
        fontInfo3.setFamily(true);
        fontInfo3.setSupportCurrentLang(fontInfo.isSupportCurrentLang());
        fontInfo3.getChildFontList().addAll(list);
        return fontInfo3;
    }
}
