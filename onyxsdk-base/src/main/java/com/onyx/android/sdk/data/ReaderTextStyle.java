package com.onyx.android.sdk.data;

import androidx.annotation.IntRange;
import com.onyx.android.sdk.data.note.NoteConstant;
import com.onyx.android.sdk.global.inversion.SystemInversionColorProvider;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.MathUtils;
import com.onyx.android.sdk.utils.StringUtils;
import com.onyx.android.sdk.utils.TTFFont;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kotlin.collections.CollectionsKt;

public class ReaderTextStyle {
    public static final String TITLE = "Title";
    public static final String AUTHOR = "Author";
    public static final String LANGUAGE = "Language";
    public static final int MAX_FONT_EMBOLDEN_LEVEL = 30;
    public static final int MIN_FONT_EMBOLDEN_LEVEL = 0;
    public static final int DEFAULT_FONT_EMBOLDEN_LEVEL = 0;
    public static final float FONT_EMBOLDEN_STEP = 0.1f;
    public static final int PAGING_MODE_SINGLE_COLUMN = 0;
    public static final int PAGING_MODE_TWO_COLUMN = 1;
    public static final int PAGING_MODE_DEFAULT = 0;
    public static final int PAGE_DIRECTION_LTR = 0;
    public static final int PAGE_DIRECTION_RTL = 1;
    public static final int PAGE_DIRECTION_DEFAULT = 0;
    public static final String LAST_FONT_FACE = "last_font_face";
    public static final String FONT_FACE_LANG_CN = "font_face_lang_cn";
    public static final String FONT_FACE_LANG_EN = "font_face_lang_en";
    public static final String FONT_FACE_LANG_IMPORT = "font_face_lang_import";
    public static final String FONT_FACE_LANG_SYSTEM = "font_face_lang_system";
    public static final boolean DEFAULT_ENABLE_HYPHEN = true;
    public static final boolean DEFAULT_INCLUDE_FONT_PADDING = false;
    public static final boolean DEFAULT_CHANGE_WRITING_MODE = false;
    public static final boolean DEFAULT_ENABLE_FONT_REPLACE_ALL = false;
    public static final boolean DEFAULT_ENABLE_REPLACE_BOOK_FONT = false;
    public static final boolean DEFAULT_ENABLE_NOT_FOUND_BOOK_FONT_HINT = true;
    public static final boolean DEFAULT_USE_SYSTEM_FONT_AT_NOT_FOUND_BOOK_FONT = true;
    public static final boolean DEFAULT_USE_TITLE_CONTENT_FONT = false;
    public static final boolean DEFAULT_ANTI_ALIAS = true;
    public static final int TEXT_COLOR_TYPE_RAW = 0;
    public static final int TEXT_COLOR_TYPE_THRESH_BINARY = 1;
    public static final int TEXT_COLOR_TYPE_THRESH_QUATERNARY = 2;
    public static final int TEXT_COLOR_TYPE_THRESH_OCTAL = 3;
    public static final int BACKGROUND_COLOR_TYPE_ORIGIN = 0;
    public static final int BACKGROUND_COLOR_TYPE_WHITE = 1;
    public static final int BACKGROUND_COLOR_TYPE_NIGHT_MODE = 2;
    public static final int BACKGROUND_COLOR_TYPE_BROWN = 3;
    public static final int BACKGROUND_COLOR_TYPE_YELLOW = 4;
    public static final int BACKGROUND_COLOR_TYPE_GREEN = 5;
    public static final int BACKGROUND_COLOR_TYPE_LIGHT_GREY = 6;
    public static final int BACKGROUND_COLOR_TYPE_MEDIUM_GREY = 7;
    public static final int BACKGROUND_COLOR_TYPE_DARK_GREY = 8;
    public static final int BACKGROUND_COLOR_TYPE_BLUE = 9;
    private Map<String, String> a = new HashMap();
    private Map<FontSpec, FontEntry> b = new HashMap();
    private Map<String, FontEntry> c = new HashMap();
    private Map<String, String> d = new HashMap();
    private List<String> e = new ArrayList();
    private SPUnit f = DEFAULT_FONT_SIZE;
    private Alignment g = DEFAULT_ALIGNMENT;
    private CharacterIndent h = DEFAULT_CHARACTER_INDENT;

    @Deprecated
    private Percentage i;

    @Deprecated
    private Percentage j;
    private Percentage k;
    private Percentage l;
    private PageMargin m;
    private int n;
    private TextSpacingTrim o;
    private int p;
    private boolean q;
    private boolean r;
    private boolean s;
    private boolean t;
    private boolean u;
    private boolean v;
    private boolean w;
    private boolean x;
    private float y;
    private float z;
    private int A;
    private int B;
    private int C;
    private boolean D;
    public static final List<String> TITLE_TAG = CollectionsKt.arrayListOf(new String[]{"h1", "h2", "h3", "h4", "h5", "h6"});
    public static final List<String> BODY_TAG = CollectionsKt.arrayListOf(new String[]{"div", "p", "span"});
    public static Alignment DEFAULT_ALIGNMENT = Alignment.ALIGNMENT_JUSTIFY;
    public static TextSpacingTrim DEFAULT_TEXT_SPACING = TextSpacingTrim.SPACE_ALL;
    public static CharacterIndent ONE_CHARACTER_INDENT = new CharacterIndent(1);
    public static CharacterIndent DEFAULT_CHARACTER_INDENT = new CharacterIndent(2);
    public static CharacterIndent NONE_CHARACTER_INDENT = new CharacterIndent(0);
    public static CharacterIndent MIN_CHARACTER_INDENT = new CharacterIndent(0);
    public static CharacterIndent MAX_CHARACTER_INDENT = new CharacterIndent(4);
    public static CharacterIndent ORIGIN_STYLE_CHARACTER_INDENT = new CharacterIndent(-1);
    public static int STEP_CHARACTER_INDENT = 1;
    public static Percentage LINE_SPACING_STEP = new Percentage(10);
    public static Percentage ORIGIN_CSS_LINE_SPACING = new Percentage(90);
    public static Percentage SMALL_LINE_SPACING = new Percentage(100);
    public static Percentage NORMAL_LINE_SPACING = new Percentage(NoteConstant.COMMON_PEN_RESUME_DELAY_TIME_MS);
    public static Percentage LARGE_LINE_SPACING = new Percentage(270);
    public static Percentage DEFAULT_LINE_SPACING = NORMAL_LINE_SPACING;
    public static Percentage PARAGRAPH_SPACING_STEP = new Percentage(10);
    public static Percentage ORIGIN_CSS_PARAGRAPH_SPACING = new Percentage(-10);
    public static Percentage SMALL_PARAGRAPH_SPACING = new Percentage(0);
    public static Percentage NORMAL_PARAGRAPH_SPACING = new Percentage(100);
    public static Percentage LARGE_PARAGRAPH_SPACING = new Percentage(200);
    public static Percentage DEFAULT_PARAGRAPH_SPACING = NORMAL_PARAGRAPH_SPACING;
    private static int E = 1;
    private static int F = -1;
    private static int G = 0;
    private static int H = 10;
    private static int I = 20;
    private static int J = 70;
    private static int K = 0;
    private static int L = 0;
    public static PageMargin PAGE_MARGIN_STEP = new PageMargin(Percentage.create(1), Percentage.create(E), Percentage.create(E), Percentage.create(E));
    public static PageMargin ORIGIN_CSS_PAGE_MARGIN = new PageMargin(Percentage.create(F), Percentage.create(F), Percentage.create(F), Percentage.create(F));
    public static PageMargin SMALL_PAGE_MARGIN = new PageMargin(Percentage.create(G), Percentage.create(G), Percentage.create(G), Percentage.create(G));
    public static PageMargin NORMAL_PAGE_MARGIN = new PageMargin(Percentage.create(H), Percentage.create(H), Percentage.create(H), Percentage.create(H));
    public static PageMargin LARGE_PAGE_MARGIN = new PageMargin(Percentage.create(J), Percentage.create(I), Percentage.create(J), Percentage.create(I));
    public static PageMargin DEFAULT_PAGE_MARGIN = new PageMargin(Percentage.create(H), Percentage.create(H), Percentage.create(H), Percentage.create(H));
    public static float MIN_LETTER_SPACING = -0.1f;
    public static float MAX_LETTER_SPACING = 0.1f;
    public static final float FONT_EMBOLDEN_NORMAL = 0.0f;
    public static float DEFAULT_LETTER_SPACING = FONT_EMBOLDEN_NORMAL;
    public static float ORIGIN_CSS_LETTER_SPACING = Float.MAX_VALUE;
    public static float ORIGIN_LETTER_SPACING = Float.MAX_VALUE;
    public static float STEP_LETTER_SPACING = 1.0f;
    public static float MIN_WORD_SPACING = -0.1f;
    public static float MAX_WORD_SPACING = 0.1f;
    public static float DEFAULT_WORD_SPACING = FONT_EMBOLDEN_NORMAL;
    public static float ORIGIN_CSS_WORD_SPACING = Float.MAX_VALUE;
    public static float ORIGIN_WORD_SPACING = Float.MAX_VALUE;
    public static float STEP_WORD_SPACING = 1.0f;
    public static SPUnit[] DEFAULT_FONT_SIZE_LIST = new SPUnit[0];
    public static SPUnit DEFAULT_FONT_SIZE = SPUnit.create(40.0f);
    public static SPUnit MAX_FONT_SIZE = SPUnit.create(96.0f);
    public static SPUnit MIN_FONT_SIZE = SPUnit.create(10.0f);
    public static SPUnit FONT_SIZE_STEP = SPUnit.create(4.0f);

    public enum Alignment {
        ALIGNMENT_NONE,
        ALIGNMENT_LEFT,
        ALIGNMENT_RIGHT,
        ALIGNMENT_JUSTIFY
    }

    public static class CharacterIndent {
        private int a;

        public CharacterIndent() {
        }

        public static CharacterIndent create(int indent) {
            return new CharacterIndent(indent);
        }

        public int getIndent() {
            return this.a;
        }

        public void setIndent(int indent) {
            this.a = indent;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            return o != null && getClass() == o.getClass() && this.a == ((CharacterIndent) o).a;
        }

        public int hashCode() {
            return this.a;
        }

        public CharacterIndent(int indent) {
            this.a = indent;
        }
    }

    public static class DPUnit {
        private int a;

        public DPUnit() {
        }

        public static DPUnit create(int value) {
            return new DPUnit(value);
        }

        public int getValue() {
            return this.a;
        }

        public void setValue(int value) {
            this.a = value;
        }

        public boolean equals(Object o) {
            return o != null && (o instanceof DPUnit) && getValue() == ((DPUnit) o).getValue();
        }

        public DPUnit(int value) {
            this.a = value;
        }
    }

    public static class FontEntry {
        private String a;
        private String b;
        private String c;
        private String d;
        private int e;
        private List<String> f = new ArrayList();
        private String g;

        public List<String> getRelateFonts() {
            return this.f;
        }

        public void setRelateFonts(List<String> relateFonts) {
            this.f = relateFonts;
        }

        public String getFontId() {
            return StringUtils.isNullOrEmpty(this.g) ? this.a : this.g;
        }

        public FontEntry setFontId(String fontId) {
            this.g = fontId;
            return this;
        }

        public String getFontPath() {
            return this.a;
        }

        public FontEntry setFontPath(String fontPath) {
            this.a = fontPath;
            return this;
        }

        public String getFontFamily() {
            return this.b;
        }

        public FontEntry setFontFamily(String fontFamily) {
            this.b = fontFamily;
            return this;
        }

        public String getFontStyle() {
            return this.c;
        }

        public FontEntry setFontStyle(String fontStyle) {
            this.c = fontStyle;
            return this;
        }

        public String getFontWeight() {
            return this.d;
        }

        public FontEntry setFontWeight(String fontWeight) {
            this.d = fontWeight;
            return this;
        }

        public int getFontIndex() {
            return this.e;
        }

        public FontEntry setFontIndex(int fontIndex) {
            this.e = fontIndex;
            return this;
        }

        public String toString() {
            return "FontEntry{fontPath='" + this.a + "', fontFamily='" + this.b + "', fontStyle='" + this.c + "', fontWeight='" + this.d + "', fontIndex=" + this.e + '}';
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            FontEntry fontEntry = (FontEntry) o;
            return this.e == fontEntry.e && Objects.equals(this.a, fontEntry.a) && Objects.equals(this.b, fontEntry.b) && Objects.equals(this.c, fontEntry.c) && Objects.equals(this.d, fontEntry.d);
        }

        public int hashCode() {
            return Objects.hash(this.a, this.b, this.c, this.d, Integer.valueOf(this.e));
        }
    }

    public enum Origin {
        AUTHOR,
        AGENT,
        USER
    }

    public static class PageMargin {
        private Percentage a;
        private Percentage b;
        private Percentage c;
        private Percentage d;

        public PageMargin() {
        }

        public static PageMargin copy(PageMargin pageMargin) {
            return new PageMargin(Percentage.create(pageMargin.getLeftMargin().getPercent()), Percentage.create(pageMargin.getBottomMargin().getPercent()), Percentage.create(pageMargin.getRightMargin().getPercent()), Percentage.create(pageMargin.getTopMargin().getPercent()));
        }

        public void increasePageMargin(PageMargin pageMargin) {
            Percentage percentage = this.a;
            percentage.setPercent(percentage.getPercent() + pageMargin.getTopMargin().getPercent());
            Percentage percentage2 = this.b;
            percentage2.setPercent(percentage2.getPercent() + pageMargin.getLeftMargin().getPercent());
            Percentage percentage3 = this.c;
            percentage3.setPercent(percentage3.getPercent() + pageMargin.getRightMargin().getPercent());
            Percentage percentage4 = this.d;
            percentage4.setPercent(percentage4.getPercent() + pageMargin.getBottomMargin().getPercent());
        }

        public void decreasePageMargin(PageMargin pageMargin) {
            Percentage percentage = this.a;
            percentage.setPercent(percentage.getPercent() - pageMargin.getTopMargin().getPercent());
            Percentage percentage2 = this.b;
            percentage2.setPercent(percentage2.getPercent() - pageMargin.getLeftMargin().getPercent());
            Percentage percentage3 = this.c;
            percentage3.setPercent(percentage3.getPercent() - pageMargin.getRightMargin().getPercent());
            Percentage percentage4 = this.d;
            percentage4.setPercent(percentage4.getPercent() - pageMargin.getBottomMargin().getPercent());
        }

        public Percentage getBottomMargin() {
            return this.d;
        }

        public void setBottomMargin(Percentage bottomMargin) {
            this.d = bottomMargin;
        }

        public Percentage getLeftMargin() {
            return this.b;
        }

        public void setLeftMargin(Percentage leftMargin) {
            this.b = leftMargin;
        }

        public Percentage getRightMargin() {
            return this.c;
        }

        public void setRightMargin(Percentage rightMargin) {
            this.c = rightMargin;
        }

        public Percentage getTopMargin() {
            return this.a;
        }

        public void setTopMargin(Percentage topMargin) {
            this.a = topMargin;
        }

        public boolean equals(Object o) {
            if (o == null || !(o instanceof PageMargin)) {
                return false;
            }
            PageMargin pageMargin = (PageMargin) o;
            return getBottomMargin().equals(pageMargin.getBottomMargin()) && getLeftMargin().equals(pageMargin.getLeftMargin()) && getTopMargin().equals(pageMargin.getTopMargin()) && getRightMargin().equals(pageMargin.getRightMargin());
        }

        public PageMargin(Percentage leftMargin, Percentage bottomMargin, Percentage rightMargin, Percentage topMargin) {
            this.d = bottomMargin;
            this.b = leftMargin;
            this.c = rightMargin;
            this.a = topMargin;
        }
    }

    public static class Percentage {
        private int a;

        public Percentage() {
        }

        public static Percentage create(int percent) {
            return new Percentage(percent);
        }

        public int getPercent() {
            return this.a;
        }

        public void setPercent(int percent) {
            this.a = percent;
        }

        public boolean equals(Object o) {
            return o != null && (o instanceof Percentage) && getPercent() == ((Percentage) o).getPercent();
        }

        public Percentage(int percent) {
            this.a = percent;
        }
    }

    public static class SPUnit {
        private float a;

        public SPUnit() {
        }

        public static SPUnit create(float value) {
            return new SPUnit(value);
        }

        public float getValue() {
            return this.a;
        }

        public void setValue(float value) {
            this.a = value;
        }

        public boolean equals(Object o) {
            return o != null && (o instanceof SPUnit) && getValue() == ((SPUnit) o).getValue();
        }

        public SPUnit increaseSPUnit(SPUnit step) {
            this.a = Math.min(this.a + step.getValue(), ReaderTextStyle.MAX_FONT_SIZE.getValue());
            return this;
        }

        public SPUnit decreaseSPUnit(SPUnit step) {
            this.a = Math.max(this.a - step.getValue(), ReaderTextStyle.MIN_FONT_SIZE.getValue());
            return this;
        }

        public SPUnit(float value) {
            this.a = value;
        }
    }

    public enum TextSpacingTrim {
        NORMAL,
        SPACE_ALL,
        SPACE_FIRST,
        TRIM_START;

        public static TextSpacingTrim valueOf(int value) {
            for (TextSpacingTrim textSpacingTrim : values()) {
                if (textSpacingTrim.ordinal() == value) {
                    return textSpacingTrim;
                }
            }
            return NORMAL;
        }
    }

    public static float limitFontSize(float newSize) {
        float value = MIN_FONT_SIZE.getValue();
        float value2 = MAX_FONT_SIZE.getValue();
        if (newSize < value) {
            value2 = value;
        } else if (newSize <= value2) {
            value2 = newSize;
        }
        return value2;
    }

    public static void setDefaultFontSizes(Float[] fontSizes) {
        if (fontSizes == null || fontSizes.length <= 0) {
            return;
        }
        DEFAULT_FONT_SIZE_LIST = new SPUnit[fontSizes.length];
        for (int i = 0; i < fontSizes.length; i++) {
            DEFAULT_FONT_SIZE_LIST[i] = SPUnit.create(fontSizes[i].floatValue());
        }
    }

    public static SPUnit getFontSizeByIndex(int index) {
        SPUnit[] sPUnitArr = DEFAULT_FONT_SIZE_LIST;
        return sPUnitArr.length > index ? sPUnitArr[index] : DEFAULT_FONT_SIZE;
    }

    public static Percentage getLineSpacingByIndex(int index) {
        Percentage percentage;
        int percent = (index * LINE_SPACING_STEP.getPercent()) + SMALL_LINE_SPACING.getPercent();
        if (percent < SMALL_LINE_SPACING.getPercent()) {
            percentage = SMALL_LINE_SPACING;
        } else if (percent > LARGE_LINE_SPACING.getPercent()) {
            percentage = LARGE_LINE_SPACING;
        } else {
            percentage = new Percentage(percent);
        }
        return percentage;
    }

    public static Percentage getParagraphSpacingByIndex(int index) {
        Percentage percentage;
        int percent = index * PARAGRAPH_SPACING_STEP.getPercent();
        if (percent < SMALL_PARAGRAPH_SPACING.getPercent()) {
            percentage = SMALL_PARAGRAPH_SPACING;
        } else if (percent > LARGE_PARAGRAPH_SPACING.getPercent()) {
            percentage = LARGE_PARAGRAPH_SPACING;
        } else {
            percentage = new Percentage(percent);
        }
        return percentage;
    }

    public static PageMargin getPageMarginByIndex(int index) {
        PageMargin pageMargin;
        if (index < G) {
            pageMargin = SMALL_PAGE_MARGIN;
        } else if (index > I) {
            pageMargin = LARGE_PAGE_MARGIN;
        } else {
            pageMargin = new PageMargin(Percentage.create(index), Percentage.create(index), Percentage.create(index), Percentage.create(index));
        }
        return pageMargin;
    }

    public ReaderTextStyle() {
        Percentage percentage = DEFAULT_LINE_SPACING;
        this.i = percentage;
        Percentage percentage2 = DEFAULT_PARAGRAPH_SPACING;
        this.j = percentage2;
        this.k = percentage;
        this.l = percentage2;
        this.m = DEFAULT_PAGE_MARGIN;
        this.n = 0;
        this.o = DEFAULT_TEXT_SPACING;
        this.p = 0;
        this.q = false;
        this.r = true;
        this.s = false;
        this.t = false;
        this.u = false;
        this.v = true;
        this.w = true;
        this.x = false;
        this.y = K;
        this.z = L;
        this.A = 0;
        this.B = 0;
        this.C = 0;
        this.D = true;
    }

    public static ReaderTextStyle defaultStyle() {
        return new ReaderTextStyle();
    }

    @Deprecated
    public static ReaderTextStyle create(Map<String, String> fontMapping, SPUnit fontSize, Percentage lineSpacing, Percentage paragraphSpacing, CharacterIndent indent, Percentage leftMargin, Percentage topMargin, Percentage rightMargin, Percentage bottomMargin, int pagingMode) {
        ReaderTextStyle readerTextStyle = new ReaderTextStyle();
        readerTextStyle.a = CollectionUtils.snapshot(fontMapping);
        readerTextStyle.f = fontSize;
        readerTextStyle.i = lineSpacing;
        readerTextStyle.j = paragraphSpacing;
        readerTextStyle.h = indent;
        readerTextStyle.m.setLeftMargin(leftMargin);
        readerTextStyle.m.setTopMargin(topMargin);
        readerTextStyle.m.setRightMargin(rightMargin);
        readerTextStyle.m.setBottomMargin(bottomMargin);
        readerTextStyle.n = pagingMode;
        return readerTextStyle;
    }

    public static ReaderTextStyle copy(ReaderTextStyle style) {
        if (style == null) {
            return null;
        }
        ReaderTextStyle readerTextStyle = new ReaderTextStyle();
        readerTextStyle.a = CollectionUtils.snapshot(style.a);
        readerTextStyle.b = CollectionUtils.snapshot(style.b);
        readerTextStyle.c = CollectionUtils.snapshot(style.c);
        readerTextStyle.e = new ArrayList(style.e);
        readerTextStyle.d = CollectionUtils.snapshot(style.d);
        readerTextStyle.v = style.v;
        readerTextStyle.u = style.u;
        readerTextStyle.w = style.w;
        readerTextStyle.f = SPUnit.create(style.f.getValue());
        readerTextStyle.g = style.g;
        readerTextStyle.o = style.o;
        readerTextStyle.h = CharacterIndent.create(style.h.getIndent());
        readerTextStyle.i = Percentage.create(style.i.getPercent());
        readerTextStyle.j = Percentage.create(style.j.getPercent());
        readerTextStyle.k = Percentage.create(style.k.getPercent());
        readerTextStyle.l = Percentage.create(style.l.getPercent());
        readerTextStyle.m = PageMargin.copy(style.m);
        readerTextStyle.n = style.n;
        readerTextStyle.p = style.p;
        readerTextStyle.setIncludeFontPadding(style.isIncludeFontPadding());
        readerTextStyle.setEnableHyphen(style.isEnableHyphen());
        readerTextStyle.setChangeWritingMode(style.isChangeWritingMode());
        readerTextStyle.setEnableFontReplaceAll(style.isEnableFontReplaceAll());
        readerTextStyle.y = style.y;
        readerTextStyle.z = style.z;
        readerTextStyle.B = style.B;
        readerTextStyle.C = style.C;
        readerTextStyle.D = style.D;
        readerTextStyle.x = style.x;
        readerTextStyle.setFontEmboldenLevel(style.getFontEmboldenLevel());
        return readerTextStyle;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReaderTextStyle readerTextStyle = (ReaderTextStyle) o;
        if (this.q != readerTextStyle.q || this.n != readerTextStyle.n || this.p != readerTextStyle.p || this.r != readerTextStyle.r || this.s != readerTextStyle.s || this.t != readerTextStyle.isEnableFontReplaceAll() || this.y != readerTextStyle.y || this.z != readerTextStyle.z || !this.a.equals(readerTextStyle.a) || !this.b.equals(readerTextStyle.b) || !this.c.equals(readerTextStyle.c) || !this.e.equals(readerTextStyle.e) || !this.d.equals(readerTextStyle.d)) {
            return false;
        }
        SPUnit sPUnit = this.f;
        if (sPUnit != null) {
            if (!sPUnit.equals(readerTextStyle.f)) {
                return false;
            }
        } else if (readerTextStyle.f != null) {
            return false;
        }
        if (this.g != readerTextStyle.g || this.o != readerTextStyle.o) {
            return false;
        }
        CharacterIndent characterIndent = this.h;
        if (characterIndent != null) {
            if (!characterIndent.equals(readerTextStyle.h)) {
                return false;
            }
        } else if (readerTextStyle.h != null) {
            return false;
        }
        if (!Objects.equals(this.k, readerTextStyle.k) || !Objects.equals(this.l, readerTextStyle.l) || !Objects.equals(this.i, readerTextStyle.i) || !Objects.equals(this.j, readerTextStyle.j) || this.x != readerTextStyle.isUseTitleContentFont()) {
            return false;
        }
        PageMargin pageMargin = this.m;
        if (pageMargin != null) {
            return pageMargin.equals(readerTextStyle.m);
        }
        return readerTextStyle.m == null;
    }

    public boolean equalsAllValue(ReaderTextStyle textStyle) {
        return equals(textStyle) && this.C == textStyle.getBackgroundColorType() && this.A == textStyle.getFontEmboldenLevel() && this.D == textStyle.isAntiAlias() && this.B == textStyle.getTextColorType();
    }

    public int hashCode() {
        int iHashCode = ((((((((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode()) * 31) + this.e.hashCode()) * 31) + this.d.hashCode()) * 31;
        SPUnit sPUnit = this.f;
        int iHashCode2 = (iHashCode + (sPUnit != null ? sPUnit.hashCode() : 0)) * 31;
        Alignment alignment = this.g;
        int iHashCode3 = (iHashCode2 + (alignment != null ? alignment.hashCode() : 0)) * 31;
        TextSpacingTrim textSpacingTrim = this.o;
        int iHashCode4 = (iHashCode3 + (textSpacingTrim != null ? textSpacingTrim.hashCode() : 0)) * 31;
        CharacterIndent characterIndent = this.h;
        int iHashCode5 = (iHashCode4 + (characterIndent != null ? characterIndent.hashCode() : 0)) * 31;
        Percentage percentage = this.k;
        int iHashCode6 = (iHashCode5 + (percentage != null ? percentage.hashCode() : 0)) * 31;
        Percentage percentage2 = this.l;
        int iHashCode7 = (iHashCode6 + (percentage2 != null ? percentage2.hashCode() : 0)) * 31;
        PageMargin pageMargin = this.m;
        return ((((((((((((((((((iHashCode7 + (pageMargin != null ? pageMargin.hashCode() : 0)) * 31) + this.n) * 31) + this.p) * 31) + (this.q ? 1 : 0)) * 31) + (this.r ? 1 : 0)) * 31) + (this.s ? 1 : 0)) * 31) + (this.t ? 1 : 0)) * 31) + (this.x ? 1 : 0)) * 31) + ((int) (this.y * 100.0f))) * 31) + ((int) (this.z * 100.0f));
    }

    @Deprecated
    public boolean isUseTitleContentFont() {
        return this.x;
    }

    @Deprecated
    public ReaderTextStyle setUseTitleContentFont(boolean useTitleContentFont) {
        this.x = useTitleContentFont;
        return this;
    }

    @Deprecated
    public String getFontFace() {
        return this.a.get(LAST_FONT_FACE);
    }

    public Map<String, String> getFontMapping() {
        return this.a;
    }

    public ReaderTextStyle setFontMapping(Map<String, String> fontMapping) {
        this.a = fontMapping;
        return this;
    }

    public Map<FontSpec, FontEntry> getStyleFontMapping() {
        return this.b;
    }

    public void setStyleFontMapping(Map<FontSpec, FontEntry> styleFontMapping) {
        this.b = styleFontMapping;
    }

    public Map<String, FontEntry> getTagFontMapping() {
        return this.c;
    }

    public void setTagFontMapping(Map<String, FontEntry> tagFontMapping) {
        this.c = tagFontMapping;
    }

    public List<String> getExternalFonts() {
        return this.e;
    }

    public ReaderTextStyle setExternalFonts(List<String> externalFonts) {
        this.e.clear();
        this.e.addAll(externalFonts);
        return this;
    }

    public Map<String, String> getBookReplaceFontPathMapping() {
        return this.d;
    }

    public ReaderTextStyle setBookReplaceFontPathMapping(Map<String, String> bookReplaceFontPathMapping) {
        this.d = bookReplaceFontPathMapping;
        return this;
    }

    public SPUnit getFontSize() {
        return this.f;
    }

    public void setFontSize(SPUnit fontSize) {
        this.f = fontSize;
    }

    public void increaseFontSize() {
        int i = 0;
        while (true) {
            int i2 = i;
            SPUnit[] sPUnitArr = DEFAULT_FONT_SIZE_LIST;
            if (i2 >= sPUnitArr.length - 1 || sPUnitArr[i].getValue() > this.f.getValue()) {
                break;
            } else {
                i++;
            }
        }
        this.f = DEFAULT_FONT_SIZE_LIST[i];
    }

    public void decreaseFontSize() {
        int length = DEFAULT_FONT_SIZE_LIST.length - 1;
        while (length > 0 && DEFAULT_FONT_SIZE_LIST[length].getValue() >= this.f.getValue()) {
            length--;
        }
        this.f = DEFAULT_FONT_SIZE_LIST[length];
    }

    public void increaseFontSizeByStep() {
        this.f = new SPUnit(MathUtils.clampFloat(this.f.getValue() + FONT_SIZE_STEP.getValue(), MIN_FONT_SIZE.getValue(), MAX_FONT_SIZE.getValue()));
    }

    public void decreaseFontSizeByStep() {
        this.f = new SPUnit(MathUtils.clampFloat(this.f.getValue() - FONT_SIZE_STEP.getValue(), MIN_FONT_SIZE.getValue(), MAX_FONT_SIZE.getValue()));
    }

    public Alignment getAlignment() {
        return this.g;
    }

    public void setAlignment(Alignment alignment) {
        this.g = alignment;
    }

    public TextSpacingTrim getTextSpacingTrim() {
        return this.o;
    }

    public ReaderTextStyle setTextSpacingTrim(TextSpacingTrim textSpacingTrim) {
        this.o = textSpacingTrim;
        return this;
    }

    public CharacterIndent getIndent() {
        return this.h;
    }

    public void setIndent(CharacterIndent indent) {
        this.h = indent;
    }

    @Deprecated
    public Percentage getLineSpacing() {
        return this.i;
    }

    @Deprecated
    public void setLineSpacing(Percentage lineSpacing) {
        this.i = lineSpacing;
    }

    @Deprecated
    public Percentage getParagraphSpacing() {
        return this.j;
    }

    @Deprecated
    public void setParagraphSpacing(Percentage paragraphSpacing) {
        this.j = paragraphSpacing;
    }

    public Percentage getAbsLineSpacing() {
        return this.k;
    }

    public void setAbsLineSpacing(Percentage absLineSpacing) {
        this.k = absLineSpacing;
    }

    public Percentage getAbsParagraphSpacing() {
        return this.l;
    }

    public void setAbsParagraphSpacing(Percentage absParagraphSpacing) {
        this.l = absParagraphSpacing;
    }

    public PageMargin getPageMargin() {
        return this.m;
    }

    public void setPageMargin(PageMargin pageMargin) {
        this.m = pageMargin;
    }

    public int getPagingMode() {
        return this.n;
    }

    public void setPagingMode(int pagingMode) {
        this.n = pagingMode;
    }

    public int getPageDirection() {
        return this.p;
    }

    public ReaderTextStyle setPageDirection(int pageDirection) {
        this.p = pageDirection;
        return this;
    }

    public boolean isIncludeFontPadding() {
        return this.q;
    }

    public ReaderTextStyle setIncludeFontPadding(boolean includeFontPadding) {
        this.q = includeFontPadding;
        return this;
    }

    public boolean isEnableHyphen() {
        return this.r;
    }

    public ReaderTextStyle setEnableHyphen(boolean enableHyphen) {
        this.r = enableHyphen;
        return this;
    }

    public boolean isChangeWritingMode() {
        return this.s;
    }

    public ReaderTextStyle setChangeWritingMode(boolean changeWritingMode) {
        this.s = changeWritingMode;
        return this;
    }

    public boolean isEnableFontReplaceAll() {
        return this.t;
    }

    public void setEnableFontReplaceAll(boolean enableFontReplaceAll) {
        this.t = enableFontReplaceAll;
    }

    public boolean isEnableReplaceBookFont() {
        return this.u;
    }

    public ReaderTextStyle setEnableReplaceBookFont(boolean enableReplaceBookFont) {
        this.u = enableReplaceBookFont;
        return this;
    }

    public boolean isEnableNotFoundBookFontHint() {
        return this.v;
    }

    public ReaderTextStyle setEnableNotFoundBookFontHint(boolean enableNotFoundBookFontHint) {
        this.v = enableNotFoundBookFontHint;
        return this;
    }

    public boolean isUseSystemFontAtNotFoundBookFont() {
        return this.w;
    }

    public ReaderTextStyle setUseSystemFontAtNotFoundBookFont(boolean useSystemFontAtNotFoundBookFont) {
        this.w = useSystemFontAtNotFoundBookFont;
        return this;
    }

    public float getLetterSpacing() {
        return this.y;
    }

    public void setLetterSpacing(float letterSpacing) {
        this.y = letterSpacing;
    }

    public float getWordSpacing() {
        return this.z;
    }

    public ReaderTextStyle setWordSpacing(float wordSpacing) {
        this.z = wordSpacing;
        return this;
    }

    public int getFontEmboldenLevel() {
        return this.A;
    }

    public ReaderTextStyle setFontEmboldenLevel(int fontEmboldenLevel) {
        this.A = fontEmboldenLevel;
        return this;
    }

    public int getTextColorType() {
        return this.B;
    }

    public ReaderTextStyle setTextColorType(int textColorType) {
        this.B = textColorType;
        return this;
    }

    public int getBackgroundColorType() {
        return this.C;
    }

    public ReaderTextStyle setBackgroundColorType(int backgroundColorType) {
        this.C = backgroundColorType;
        return this;
    }

    public int getApplyBackgroundColorType() {
        if (!SystemInversionColorProvider.isSystemInversionColor()) {
            return this.C;
        }
        int i = this.C;
        if (i == 1) {
            return 2;
        }
        if (i != 2) {
            return i;
        }
        return 1;
    }

    public boolean isApplyNightMode() {
        return getApplyBackgroundColorType() == 2;
    }

    public boolean isAntiAlias() {
        return this.D;
    }

    public ReaderTextStyle setAntiAlias(boolean antiAlias) {
        this.D = antiAlias;
        return this;
    }

    public boolean isOriginCssLetterSpacing() {
        return getLetterSpacing() == ORIGIN_LETTER_SPACING;
    }

    public boolean isOriginCssWordSpacing() {
        return getWordSpacing() == ORIGIN_LETTER_SPACING;
    }

    public boolean isOriginCssIndent() {
        return isOriginCssIndent(getIndent().getIndent());
    }

    public boolean isOriginCssParagraphSpacing() {
        return isOriginCssParagraphSpacing(getAbsParagraphSpacing().getPercent());
    }

    public boolean isOriginCssLineSpacing() {
        return isOriginCssLineSpacing(getAbsLineSpacing().getPercent());
    }

    public boolean isOriginCssLeftMargin() {
        return getPageMargin().getLeftMargin().getPercent() <= ORIGIN_CSS_PAGE_MARGIN.getLeftMargin().getPercent();
    }

    public boolean isOriginCssRightMargin() {
        return getPageMargin().getRightMargin().getPercent() <= ORIGIN_CSS_PAGE_MARGIN.getRightMargin().getPercent();
    }

    public boolean isOriginCssTopMargin() {
        return isOriginCssTopMargin(getPageMargin().getTopMargin().getPercent());
    }

    public boolean isOriginCssBottomMargin() {
        return isOriginCssBottomMargin(getPageMargin().getBottomMargin().getPercent());
    }

    public float getFontEmbolden() {
        return getFontEmbolden(getFontEmboldenLevel());
    }

    public void resetOriginCssStyle() {
        setFontSize(DEFAULT_FONT_SIZE);
        setLetterSpacing(ORIGIN_CSS_LETTER_SPACING);
        setWordSpacing(ORIGIN_CSS_WORD_SPACING);
        setAlignment(Alignment.ALIGNMENT_JUSTIFY);
        setTextSpacingTrim(TextSpacingTrim.SPACE_ALL);
        setPageDirection(0);
        setAbsLineSpacing(ORIGIN_CSS_LINE_SPACING);
        setAbsParagraphSpacing(ORIGIN_CSS_PARAGRAPH_SPACING);
        setPageMargin(ORIGIN_CSS_PAGE_MARGIN);
        setIndent(ORIGIN_STYLE_CHARACTER_INDENT);
        setFontEmboldenLevel(0);
        setIncludeFontPadding(false);
        setChangeWritingMode(false);
        setEnableFontReplaceAll(false);
    }

    public static boolean isOriginCssIndent(int indent) {
        return indent <= ORIGIN_STYLE_CHARACTER_INDENT.getIndent();
    }

    public static boolean isOriginCssTopMargin(float top) {
        return top <= ((float) ORIGIN_CSS_PAGE_MARGIN.getTopMargin().getPercent());
    }

    public static boolean isOriginCssBottomMargin(float bottom) {
        return bottom <= ((float) ORIGIN_CSS_PAGE_MARGIN.getBottomMargin().getPercent());
    }

    public static boolean isOriginCssLineSpacing(int percent) {
        return percent <= ORIGIN_CSS_LINE_SPACING.getPercent();
    }

    public static boolean isOriginCssParagraphSpacing(int percent) {
        return percent <= ORIGIN_CSS_PARAGRAPH_SPACING.getPercent();
    }

    public static float getFontEmbolden(@IntRange(from = 0, to = 30) int fontEmboldenLevel) {
        return Math.max(FONT_EMBOLDEN_NORMAL, (fontEmboldenLevel * 0.1f) + FONT_EMBOLDEN_NORMAL);
    }

    public static class FontSpec {
        private String a;
        private String b;
        private String c;
        private Origin d;

        public FontSpec() {
            this.a = TTFFont.UNKNOWN_FONT_NAME;
            this.b = TTFFont.UNKNOWN_FONT_NAME;
            this.c = TTFFont.UNKNOWN_FONT_NAME;
            this.d = Origin.USER;
        }

        public String getFamily() {
            return this.a;
        }

        public void setFamily(String family) {
            this.a = family;
        }

        public String getFontWeight() {
            return this.b;
        }

        public void setFontWeight(String fontWeight) {
            this.b = fontWeight;
        }

        public String getFontStyle() {
            return this.c;
        }

        public void setFontStyle(String fontStyle) {
            this.c = fontStyle;
        }

        public Origin getOrigin() {
            return this.d;
        }

        public void setOrigin(Origin origin) {
            this.d = origin;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            FontSpec fontSpec = (FontSpec) o;
            return Objects.equals(this.a, fontSpec.a) && Objects.equals(this.b, fontSpec.b) && Objects.equals(this.c, fontSpec.c) && this.d == fontSpec.d;
        }

        public int hashCode() {
            return Objects.hash(this.a, this.b, this.c, this.d);
        }

        public FontSpec(String family, String fontWeight, String fontStyle) {
            this(family, fontWeight, fontStyle, Origin.USER);
        }

        public FontSpec(String family, String fontWeight, String fontStyle, Origin origin) {
            this.a = TTFFont.UNKNOWN_FONT_NAME;
            this.b = TTFFont.UNKNOWN_FONT_NAME;
            this.c = TTFFont.UNKNOWN_FONT_NAME;
            this.d = Origin.USER;
            this.a = family;
            this.b = fontWeight;
            this.c = fontStyle;
            this.d = origin;
        }
    }

    public static boolean equals(ReaderTextStyle style1, ReaderTextStyle style2) {
        return style1 != null && style1.equals(style2);
    }
}
