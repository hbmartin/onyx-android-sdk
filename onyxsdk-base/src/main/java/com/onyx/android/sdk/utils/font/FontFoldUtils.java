// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils.font;

import org.jetbrains.annotations.Nullable;
import java.util.Comparator;
import kotlin.jvm.functions.Function2;
import com.onyx.android.sdk.utils.ComparatorUtils;
import com.onyx.android.sdk.data.SortOrder;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.SequencesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import java.util.HashMap;
import kotlin.text.StringsKt;
import com.onyx.android.sdk.utils.FileUtils;
import com.onyx.android.sdk.utils.DeviceUtils;
import kotlin.collections.CollectionsKt;
import java.util.Iterator;
import java.util.Collection;
import com.onyx.android.sdk.extension.CollectionKt;
import kotlin.jvm.internal.Intrinsics;
import java.util.ArrayList;
import com.onyx.android.sdk.data.FontInfo;
import java.util.List;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001&B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u0010H\u0002J\u0016\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00052\u0006\u0010\u0014\u001a\u00020\u0013H\u0002J\u0010\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u0013H\u0002J\u0016\u0010\u0017\u001a\u00020\u00132\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0002J\u001c\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0007J\u001a\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005J\u0018\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u0006H\u0002J\u0014\u0010 \u001a\u00020\u000e2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005J\u0014\u0010!\u001a\u00020\u000e2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005J\u0012\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u0010*\u00020\u0006H\u0002J\f\u0010$\u001a\u00020#*\u00020\u0013H\u0002J\u001a\u0010%\u001a\u00020\u000e*\u00020\u00132\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082T¢\u0006\u0002\n\u0000¨\u0006'" }, d2 = { "Lcom/onyx/android/sdk/utils/font/FontFoldUtils;", "", "()V", "defaultPut", "Lkotlin/Function0;", "", "Lcom/onyx/android/sdk/data/FontInfo;", "getDefaultPut", "()Lkotlin/jvm/functions/Function0;", "fontNameDelimiters", "", "isDebug", "", "checkFold", "", "originList", "", "resultList", "collectFontListForSecond", "Lcom/onyx/android/sdk/utils/font/FontFoldUtils$TrieNode;", "dumpyFontTrie", "convertTrieToFontInfo", "fontTrie", "createFontTrie", "fontList", "distinctDisplayName", "list", "foldFontListByName", "sortByFontName", "", "f1", "f2", "sortImportFontList", "sortSystemFontList", "getFontSplitName", "", "getFontUniqueName", "loadTrieFontList", "TrieNode", "onyxsdk-base_release" })
public final class FontFoldUtils
{
    @NotNull
    public static final FontFoldUtils INSTANCE;
    private static final boolean a = false;
    private static final char b = ' ';
    @NotNull
    private static final Function0<List<FontInfo>> c;
    
    private FontFoldUtils() {
    }
    
    private final List<a> a(final a dumpyFontTrie) {
        final ArrayList<a> list = new ArrayList<>();
        final Collection<a> values = dumpyFontTrie.a().values();
        Intrinsics.checkNotNullExpressionValue((Object)values, "dumpyFontTrie.childMap.values");
        final Iterator<a> iterator = values.iterator();
        while (iterator.hasNext()) {
            final a a;
            if (CollectionKt.isNonBlank((a = iterator.next()).b())) {
                final ArrayList<a> list2 = list;
                Intrinsics.checkNotNullExpressionValue((Object)a, "it");
                list2.add(a);
            }
            else {
                final ArrayList<a> list3 = list;
                final Collection<a> values2 = a.a().values();
                Intrinsics.checkNotNullExpressionValue((Object)values2, "it.childMap.values");
                list3.addAll(values2);
            }
        }
        return list;
    }
    
    private final FontInfo b(final a fontTrie) {
        final ArrayList fontList = new ArrayList<FontInfo>();
        this.a(fontTrie, fontList);
        if (fontList.size() == 1) {
            return (FontInfo)CollectionsKt.first((List)fontList);
        }
        final FontInfo fontInfo = new FontInfo();
        final FontInfo fontInfo2;
        String s;
        if ((fontInfo2 = (FontInfo)CollectionsKt.firstOrNull((List)fontTrie.b())) == null) {
            s = null;
        }
        else {
            s = fontInfo2.getFontUniqueName();
        }
        if (s == null) {
            s = this.c(fontTrie);
        }
        final FontInfo fontInfo3 = fontInfo2;
        final FontInfo fontInfo4 = fontInfo;
        fontInfo4.setFontUniqueName(s);
        fontInfo4.setFontDisplayName(s);
        String filePath;
        if (fontInfo3 == null) {
            filePath = null;
        }
        else {
            filePath = fontInfo2.getFilePath();
        }
        if (filePath == null) {
            filePath = "";
        }
        final FontInfo fontInfo5 = fontInfo2;
        fontInfo.setFilePath(filePath);
        String id;
        if (fontInfo5 == null) {
            id = null;
        }
        else {
            id = fontInfo2.getId();
        }
        if (id == null) {
            id = "";
        }
        final FontInfo fontInfo6 = fontInfo2;
        fontInfo.setId(id);
        DeviceUtils.FontType fontType;
        if (fontInfo6 == null) {
            fontType = null;
        }
        else {
            fontType = fontInfo2.getFontType();
        }
        if (fontType == null) {
            fontType = DeviceUtils.FontType.ALL;
        }
        final ArrayList list = fontList;
        fontInfo.setFontType(fontType);
        boolean supportCurrentLang = false;
        Label_0226: {
            if (!list.isEmpty()) {
                final Iterator iterator = fontList.iterator();
                while (iterator.hasNext()) {
                    if (((FontInfo)iterator.next()).isSupportCurrentLang()) {
                        supportCurrentLang = true;
                        break Label_0226;
                    }
                }
            }
            supportCurrentLang = false;
        }
        final FontInfo fontInfo7 = fontInfo;
        fontInfo7.setSupportCurrentLang(supportCurrentLang);
        fontInfo7.getChildFontList().addAll(fontList);
        return fontInfo7;
    }
    
    private final void a(final a $this$loadTrieFontList, final List<FontInfo> fontList) {
        fontList.addAll($this$loadTrieFontList.b());
        final Collection<a> values = $this$loadTrieFontList.a().values();
        Intrinsics.checkNotNullExpressionValue((Object)values, "childMap.values");
        for (final a $this$loadTrieFontList2 : values) {
            final FontFoldUtils instance = FontFoldUtils.INSTANCE;
            Intrinsics.checkNotNullExpressionValue((Object)$this$loadTrieFontList2, "it");
            instance.a($this$loadTrieFontList2, fontList);
        }
    }
    
    private final String c(final a $this$getFontUniqueName) {
        final a d;
        final String c;
        String stringPlus;
        if ((d = $this$getFontUniqueName.d()) != null && (c = this.c(d)) != null) {
            stringPlus = Intrinsics.stringPlus(c, (Object)" ");
        }
        else {
            stringPlus = null;
        }
        if (stringPlus == null) {
            stringPlus = "";
        }
        return Intrinsics.stringPlus(stringPlus, (Object)$this$getFontUniqueName.c());
    }
    
    private final void a(final List<? extends FontInfo> originList, final List<? extends FontInfo> resultList) {
    }
    
    private final List<String> a(final FontInfo $this$getFontSplitName) {
        String s;
        if ((s = $this$getFontSplitName.getFontUniqueName()) == null && (s = FileUtils.getBaseName($this$getFontSplitName.getFilePath())) == null) {
            s = "";
        }
        final String s2;
        List list;
        if ((s2 = (String)CollectionsKt.firstOrNull(java.util.Arrays.asList(s.split(" & ", -1)))) == null) {
            list = null;
        }
        else {
            list = java.util.Arrays.asList(s2.split(" ", -1));
        }
        if (list == null) {
            list = CollectionsKt.emptyList();
        }
        return list;
    }
    
    private final a a(final List<FontInfo> fontList) {
        final a a = new a(null, "", new ArrayList<FontInfo>(), new java.util.HashMap<String, a>());
        final Iterator<FontInfo> iterator = fontList.iterator();
        while (iterator.hasNext()) {
            final FontInfo fontInfo;
            final Iterator<String> iterator2 = this.a(fontInfo = iterator.next()).iterator();
            a a2 = a;
            while (iterator2.hasNext()) {
                final a a3 = a2;
                final String key = iterator2.next();
                a a4;
                if ((a4 = a3.a().get(key)) == null) {
                    final a a5 = a2;
                    a4 = new a(a2, key, new ArrayList<FontInfo>(), new java.util.HashMap<String, a>());
                    a5.a().put(key, a4);
                }
                a2 = a4;
            }
            a2.b().add(fontInfo);
        }
        return a;
    }
    
    @JvmStatic
    @NotNull
    public static final List<FontInfo> distinctDisplayName(@NotNull final List<FontInfo> list) {
        Intrinsics.checkNotNullParameter((Object)list, "list");
        return SequencesKt.toMutableList(SequencesKt.distinctBy(CollectionsKt.asSequence((Iterable)list), (Function1)FontFoldUtils$c.a));
    }
    
    private final int a(final FontInfo f1, final FontInfo f2) {
        return ComparatorUtils.stringComparator(f1.getName(), f2.getName(), SortOrder.Asc);
    }
    
    private static final int b(final Function2 $tmp0, final FontInfo p0, final FontInfo p1) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, "$tmp0");
        return ((Number)$tmp0.invoke((Object)p0, (Object)p1)).intValue();
    }
    
    private static final int a(final Function2 $tmp0, final FontInfo p0, final FontInfo p1) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, "$tmp0");
        return ((Number)$tmp0.invoke((Object)p0, (Object)p1)).intValue();
    }

    public static final int access$sortByFontName(final FontFoldUtils $this, final FontInfo f1, final FontInfo f2) {
        return $this.a(f1, f2);
    }

    static {
        INSTANCE = new FontFoldUtils();
        c = (Function0)FontFoldUtils$b.a;
    }
    
    @NotNull
    public final Function0<List<FontInfo>> getDefaultPut() {
        return FontFoldUtils.c;
    }
    
    @NotNull
    public final List<FontInfo> foldFontListByName(@NotNull final List<FontInfo> fontList) {
        Intrinsics.checkNotNullParameter((Object)fontList, "fontList");
        final List<a> a = this.a(this.a(fontList));
        final ArrayList<FontInfo> resultList = new ArrayList<FontInfo>();
        final Iterator<a> iterator = a.iterator();
        while (iterator.hasNext()) {
            resultList.add(FontFoldUtils.INSTANCE.b(iterator.next()));
        }
        final ArrayList<FontInfo> list = resultList;
        this.a(fontList, resultList);
        return list;
    }
    
    public final void sortImportFontList(@NotNull final List<FontInfo> fontList) {
        Intrinsics.checkNotNullParameter((Object)fontList, "fontList");
        fontList.sort(this::a);
    }
    
    public final void sortSystemFontList(@NotNull final List<FontInfo> fontList) {
        Intrinsics.checkNotNullParameter((Object)fontList, "fontList");
        fontList.sort(this::a);
        final Iterator<FontInfo> iterator = fontList.iterator();
        while (iterator.hasNext()) {
            final FontInfo fontInfo;
            if (CollectionKt.isNonBlank((fontInfo = iterator.next()).getChildFontList())) {
                final List<FontInfo> childFontList = fontInfo.getChildFontList();
                Intrinsics.checkNotNullExpressionValue((Object)childFontList, "it.childFontList");
                childFontList.sort(this::a);
            }
        }
    }
    
    @Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\b\u0002\u0018\u00002\u00020\u0001BO\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012$\b\u0002\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00000\tj\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0000`\n¢\u0006\u0002\u0010\u000bR-\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00000\tj\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0000`\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR \u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0000¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u0016" }, d2 = { "Lcom/onyx/android/sdk/utils/font/FontFoldUtils$TrieNode;", "", "parent", "fontSplitName", "", "fontInfoList", "", "Lcom/onyx/android/sdk/data/FontInfo;", "childMap", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "(Lcom/onyx/android/sdk/utils/font/FontFoldUtils$TrieNode;Ljava/lang/String;Ljava/util/List;Ljava/util/HashMap;)V", "getChildMap", "()Ljava/util/HashMap;", "getFontInfoList", "()Ljava/util/List;", "setFontInfoList", "(Ljava/util/List;)V", "getFontSplitName", "()Ljava/lang/String;", "getParent", "()Lcom/onyx/android/sdk/utils/font/FontFoldUtils$TrieNode;", "onyxsdk-base_release" })
    private static final class a
    {
        @Nullable
        private final a a;
        @NotNull
        private final String b;
        @NotNull
        private List<FontInfo> c;
        @NotNull
        private final HashMap<String, a> d;
        
        public a(@Nullable final a parent, @NotNull final String fontSplitName, @NotNull final List<FontInfo> fontInfoList, @NotNull final HashMap<String, a> childMap) {
            Intrinsics.checkNotNullParameter((Object)fontSplitName, "fontSplitName");
            Intrinsics.checkNotNullParameter((Object)fontInfoList, "fontInfoList");
            Intrinsics.checkNotNullParameter((Object)childMap, "childMap");
            this.a = parent;
            this.b = fontSplitName;
            this.c = fontInfoList;
            this.d = childMap;
        }
        
        @Nullable
        public final a d() {
            return this.a;
        }
        
        @NotNull
        public final String c() {
            return this.b;
        }
        
        @NotNull
        public final List<FontInfo> b() {
            return this.c;
        }
        
        public final void a(@NotNull final List<FontInfo> value) {
            Intrinsics.checkNotNullParameter((Object)value, "<set-?>");
            this.c = value;
        }
        
        @NotNull
        public final HashMap<String, a> a() {
            return this.d;
        }
    }
}
