
package com.onyx.android.sdk.utils;

import java.util.HashMap;
import java.nio.charset.StandardCharsets;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.Collection;
import java.util.Locale;
import java.util.Arrays;
import java.util.List;
import android.util.SparseArray;

public class TTFFont
{
    public static final String UNKNOWN_FONT_NAME = "";
    public static final String NAME_TABLE = "name";
    public static final String OS_2_TABLE = "OS/2";
    public static final String CHARSET_GBK = "GBK";
    public static final String CHARSET_SHIFT_JIS = "Shift-JIS";
    public static final String CHARSET_GB18030 = "GB18030";
    public static final String CHARSET_BIG5 = "Big5";
    private SparseArray<List<c>> a;
    private List<Integer> b;
    private boolean c;
    private boolean d;
    
    public TTFFont() {
        this.a = (SparseArray<List<c>>)new SparseArray();
        this.b = Arrays.asList(4, 1, 2);
        this.c = false;
        this.d = false;
    }
    
    private b a(final Locale locale, List<c> nameRecords) {
        if (CollectionUtils.isNullOrEmpty(nameRecords)) {
            nameRecords = CollectionUtils.ensureList(this.a, 4);
        }
        final String fontName = "";
        DeviceUtils.FontType fontType = DeviceUtils.FontType.ALL;
        if (CollectionUtils.isNullOrEmpty(nameRecords)) {
            this.log("getFontName fail");
            return new b(fontName, fontType, false);
        }
        final Iterator<c> iterator = nameRecords.iterator();
        while (iterator.hasNext()) {
            final c c;
            if (!Platform.supportPlatform((c = iterator.next()).a)) {
                continue;
            }
            if ((fontType = getFontType(c.a, c.c)) == DeviceUtils.FontType.CJK) {
                break;
            }
        }
        final Iterator<c> iterator2 = nameRecords.iterator();
        while (iterator2.hasNext()) {
            final c c2;
            if (Platform.supportPlatform((c2 = iterator2.next()).a) && LocaleUtils.isSameLanguage(locale, Platform.mapLocale(c2.a, c2.c))) {
                return new b(c2.g, fontType, true);
            }
        }
        return new b(nameRecords.get(0).g, fontType, false);
    }
    
    public static DeviceUtils.FontType getFontType(final int plateFormId, final int languageId) {
        return Platform.fontTypeMap(Platform.mapLocale(plateFormId, languageId));
    }
    
    public static DeviceUtils.FontType getFontNameFontType(final String fontName) {
        return StringUtils.isAlpha(fontName) ? DeviceUtils.FontType.ENGLISH : DeviceUtils.FontType.CJK;
    }
    
    private void b(final RandomAccessFile randomAccessFile, final e tableDirectory) throws IOException {
        randomAccessFile.seek(tableDirectory.c);
        if (randomAccessFile.readUnsignedShort() > 0) {
            randomAccessFile.seek(tableDirectory.c + 78);
            final long n = randomAccessFile.readInt();
            this.log("codePageRange1:" + n);
            this.c = Os2.supportChinese(n);
        }
    }
    
    private void a(final RandomAccessFile randomAccessFile, final e tableDirectory) throws IOException {
        randomAccessFile.seek(tableDirectory.c);
        final d tableHeader = new d();
        tableHeader.a = randomAccessFile.readShort();
        tableHeader.b = randomAccessFile.readShort();
        tableHeader.c = randomAccessFile.readShort();
        for (int i = 0; i < tableHeader.b; ++i) {
            final c nameRecord = new c();
            nameRecord.a = randomAccessFile.readShort();
            nameRecord.b = randomAccessFile.readShort();
            nameRecord.c = randomAccessFile.readShort();
            nameRecord.d = randomAccessFile.readShort();
            nameRecord.e = randomAccessFile.readShort();
            nameRecord.f = randomAccessFile.readShort();
            if (this.b.contains(nameRecord.d)) {
                final long filePointer = randomAccessFile.getFilePointer();
                final byte[] array = new byte[nameRecord.e];
                randomAccessFile.seek(tableDirectory.c + nameRecord.f + tableHeader.c);
                randomAccessFile.read(array);
                randomAccessFile.seek(filePointer);
                nameRecord.g = this.a(nameRecord.a, nameRecord.b, array);
                CollectionUtils.ensureList(this.a, nameRecord.d).add(nameRecord);
                this.log(nameRecord.toString());
            }
        }
        this.a();
    }
    
    private void a() {
        for (int i = 0; i < this.a.size(); ++i) {
            this.a((List<c>)this.a.valueAt(i));
        }
    }
    
    private void a(final List<c> nameRecordList) {
        if (CollectionUtils.isNullOrEmpty(nameRecordList)) {
            return;
        }
        Collections.sort(nameRecordList, new Comparator<c>() {
            public int compare(final c o1, final c o2) {
                final int n;
                if ((n = o2.a - o1.a) != 0) {
                    return n;
                }
                return o1.c - o2.c;
            }
        });
    }
    
    private String a(final int platformId, final int encodingID, final byte[] bf) {
        return new String(bf, Platform.mapCharset(platformId, encodingID));
    }
    
    public void log(final String msg) {
        if (this.d) {
            Debug.d((Class)this.getClass(), msg, new Object[0]);
        }
    }
    
    public void clear() {
        this.a.clear();
    }
    
    public String getFontName() {
        return this.getFontName(Locale.getDefault());
    }
    
    public String getFontName(final Locale locale) {
        List<c> list;
        if (CollectionUtils.isNullOrEmpty(list = CollectionUtils.ensureList(this.a, 1))) {
            list = CollectionUtils.ensureList(this.a, 4);
        }
        if (CollectionUtils.isNullOrEmpty(list)) {
            this.log("getFontName fail");
            return "";
        }
        final Iterator<c> iterator = list.iterator();
        while (iterator.hasNext()) {
            final c c;
            if (Platform.supportPlatform((c = iterator.next()).a) && LocaleUtils.isSameLanguage(locale, Platform.mapLocale(c.a, c.c))) {
                return c.g;
            }
        }
        return list.get(0).g;
    }
    
    public b getFontUniqueNameAndFontType() {
        return this.getFontNameAndFontType(Locale.ENGLISH);
    }
    
    public b getFontDisplayNameAndFontType() {
        final Locale default1;
        final Locale locale = default1 = Locale.getDefault();
        final List<c> ensureList = CollectionUtils.ensureList(this.a, 1);
        final List<c> ensureList2 = CollectionUtils.ensureList(this.a, 2);
        final List<c> ensureList3 = CollectionUtils.ensureList(this.a, 4);
        final b a = this.a(locale, ensureList);
        final b a2 = this.a(default1, ensureList2);
        final b a3 = this.a(default1, ensureList3);
        final DeviceUtils.FontType cjk;
        b b;
        if (a.b() == (cjk = DeviceUtils.FontType.CJK) && StringUtils.isNotBlank(a.a()) && StringUtils.isNotBlank(a2.a())) {
            b = a;
        }
        else {
            b = a3;
        }
        if (a3.b() == cjk && StringUtils.isNotBlank(a3.a()) && !StringUtils.safelyEquals(a.a(), a3.a())) {
            b = a3;
        }
        b b2;
        if (b.b() != cjk && this.c) {
            b2 = new b(b.a(), cjk, b.c);
        }
        else {
            b2 = b;
        }
        return b2;
    }
    
    public b getFontNameAndFontType(final Locale locale) {
        final List<c> ensureList = CollectionUtils.ensureList(this.a, 1);
        final List<c> ensureList2 = CollectionUtils.ensureList(this.a, 2);
        final List<c> ensureList3 = CollectionUtils.ensureList(this.a, 4);
        final b a = this.a(locale, ensureList);
        final b a2 = this.a(locale, ensureList2);
        final b a3 = this.a(locale, ensureList3);
        final DeviceUtils.FontType cjk;
        b b;
        if (a.b() == (cjk = DeviceUtils.FontType.CJK) && StringUtils.isNotBlank(a.a()) && StringUtils.isNotBlank(a2.a())) {
            b = a;
        }
        else {
            b = a3;
        }
        if (a3.b() == cjk && StringUtils.isNotBlank(a3.a()) && !StringUtils.safelyEquals(a.a(), a3.a())) {
            b = a3;
        }
        b b2;
        if (b.b() != cjk && this.c) {
            b2 = new b(b.a(), cjk, b.c);
        }
        else {
            b2 = b;
        }
        return b2;
    }
    
    public void parse(final String fileName) {
        log(fileName);
        clear();
        try (RandomAccessFile file = new RandomAccessFile(fileName, "r")) {
            parseInner(file);
        }
        catch (Throwable failure) {
            Debug.w(failure);
        }
    }
    
    public void parseInner(final RandomAccessFile randomAccessFile) throws IOException {
        final long filePointer = randomAccessFile.getFilePointer();
        randomAccessFile.readShort();
        randomAccessFile.readShort();
        final short short1 = randomAccessFile.readShort();
        randomAccessFile.seek(filePointer + 12);
        final byte[] array = new byte[4];
        final e e = new e();
        for (short n = 0; n < short1; ++n) {
            final e e2 = e;
            final e e3 = e;
            final e e4 = e;
            final e e5 = e;
            final e e6 = e;
            randomAccessFile.read(array);
            e6.a = new String(array);
            e5.b = randomAccessFile.readInt();
            e4.c = randomAccessFile.readInt();
            e3.d = randomAccessFile.readInt();
            this.log(e.a + " - checkSum:" + e.b + " - offset:" + e.c + " - length:" + e.d);
            final long filePointer2 = randomAccessFile.getFilePointer();
            final String a;
            final String s = a = e2.a;
            s.hashCode();
            if (!s.equals("OS/2")) {
                if (a.equals("name")) {
                    this.a(randomAccessFile, e);
                }
            }
            else {
                this.b(randomAccessFile, e);
            }
            randomAccessFile.seek(filePointer2);
        }
    }
    
    private static class c
    {
        int a;
        int b;
        int c;
        int d;
        int e;
        int f;
        String g;
        
        @Override
        public String toString() {
            return "NameRecord{platformID=" + this.a + ", encodingID=" + this.b + ", languageID=" + this.c + ", nameID=" + this.d + ", stringLength=" + this.e + ", stringOffset=" + this.f + ", name='" + this.g + '\'' + '}';
        }
    }
    
    private static class d
    {
        int a;
        int b;
        int c;
    }
    
    private static class e
    {
        String a;
        int b;
        int c;
        int d;
    }
    
    static class b
    {
        private String a;
        private DeviceUtils.FontType b;
        private boolean c;
        
        public b(final String fontName, final DeviceUtils.FontType fontType, final boolean isSameLange) {
            this.a = fontName;
            this.b = fontType;
            this.c = isSameLange;
        }
        
        public String a() {
            return this.a;
        }
        
        public DeviceUtils.FontType b() {
            return this.b;
        }
        
        public boolean c() {
            return this.c;
        }
    }
    
    public static class NameId
    {
        public static final int COPYRIGHT = 0;
        public static final int FAMILY_NAME = 1;
        public static final int FONT_SUBFAMILY_NAME = 2;
        public static final int UNIQUE_FONT_IDENTIFIER = 3;
        public static final int FULL_FONT_NAME = 4;
        public static final int VERSION = 5;
        public static final int POSTSCRIPT_NAME = 6;
        public static final int TRADEMARK = 7;
        public static final int MANUFACTURER = 8;
        public static final int DESIGNER = 9;
        public static final int DESCRIPTION = 10;
        public static final int URL_VENDOR = 11;
        public static final int URL_DESIGNER = 12;
        public static final int LICENSE_DESCRIPTION = 13;
        public static final int LICENSE_INFO_URL = 14;
    }
    
    public static class Os2
    {
        public static final int CODE_PAGE_RANGE_OFFSET = 78;
        
        public static boolean supportChinese(final long codePageRangeOne) {
            return ((codePageRangeOne & 0x20000L) | (codePageRangeOne & 0x40000L) | (codePageRangeOne & 0x100000L)) > 0L;
        }
        
        public static class CodePageRange
        {
            public static final int JIS_JAPAN = 17;
            public static final int SIMPLIFIED_CHINESE = 18;
            public static final int TRADITIONAL_CHINESE = 20;
        }
    }
    
    public static class Platform
    {
        public static final int ID_UNICODE = 0;
        public static final int ID_MACINTOSH = 1;
        public static final int ID_ISO = 2;
        public static final int ID_WINDOWS_ID = 3;
        public static final int ID_CUSTOM = 4;
        public static Map<String, DeviceUtils.FontType> fontTypeMap;
        
        public static Charset mapCharset(final int platformId, final int encodingId) {
            return (platformId != 1) ? ((platformId != 3) ? StandardCharsets.UTF_16 : Windows.Encoding.mapCharset(encodingId)) : Macintosh.Encoding.mapCharset(encodingId);
        }
        
        public static boolean supportPlatform(final int platformId) {
            return platformId == 3 || platformId == 1;
        }
        
        public static Locale mapLocale(final int platformId, final int languageId) {
            return (platformId != 1) ? ((platformId != 3) ? Locale.getDefault() : Windows.Language.mapLocale(languageId)) : Macintosh.Language.mapLocale(languageId);
        }
        
        public static DeviceUtils.FontType fontTypeMap(final Locale locale) {
            if (Platform.fontTypeMap == null) {
                (Platform.fontTypeMap = new HashMap<String, DeviceUtils.FontType>()).put(Locale.ENGLISH.getLanguage(), DeviceUtils.FontType.ENGLISH);
                final DeviceUtils.FontType cjk;
                Platform.fontTypeMap.put(Locale.TRADITIONAL_CHINESE.getLanguage(), cjk = DeviceUtils.FontType.CJK);
                Platform.fontTypeMap.put(Locale.SIMPLIFIED_CHINESE.getLanguage(), cjk);
            }
            DeviceUtils.FontType all;
            if ((all = Platform.fontTypeMap.get(locale.getLanguage())) == null) {
                all = DeviceUtils.FontType.ALL;
            }
            return all;
        }
        
        public static class Macintosh
        {
            public static class Language
            {
                public static final int English = 0;
                public static final int CHINESE_TRADITIONAL = 19;
                public static final int CHINESE_SIMPLIFIED = 33;
                public static final int Armenian = 51;
                
                public static Locale mapLocale(final int languageId) {
                    if (languageId != 0) {
                        if (languageId == 19) {
                            return Locale.TRADITIONAL_CHINESE;
                        }
                        if (languageId == 33) {
                            return Locale.SIMPLIFIED_CHINESE;
                        }
                        if (languageId != 51) {
                            return Locale.getDefault();
                        }
                    }
                    return Locale.ENGLISH;
                }
            }
            
            public static class Encoding
            {
                public static final int ROMAN = 0;
                public static final int JAPANESE = 1;
                public static final int CHINESE_TRADITIONAL = 2;
                public static final int ARMENIAN = 24;
                public static final int CHINESE_SIMPLIFIED = 25;
                
                public static Charset mapCharset(final int encodingId) {
                    if (encodingId == 0) {
                        return StandardCharsets.US_ASCII;
                    }
                    if (encodingId == 1) {
                        return Charset.forName("Shift-JIS");
                    }
                    if (encodingId == 2) {
                        return Charset.forName("Big5");
                    }
                    if (encodingId == 24) {
                        return StandardCharsets.UTF_16;
                    }
                    if (encodingId != 25) {
                        return StandardCharsets.UTF_16;
                    }
                    return Charset.forName("GBK");
                }
            }
        }
        
        public static class Windows
        {
            public static class Language
            {
                public static final int ARMENIAN_ARMENIA = 1067;
                public static final int CHINESE_HONG_KONG = 3076;
                public static final int CHINESE_MACAO = 5124;
                public static final int CHINESE_PEOPLE_REPUBLIC_CHINA = 2052;
                public static final int CHINESE_SINGAPORE = 4100;
                public static final int CHINESE_TAIWAN = 1028;
                public static final int ENGLISH_AUSTRALIA = 3081;
                public static final int ENGLISH_BELIZE = 10249;
                public static final int ENGLISH_CANADA = 4105;
                public static final int ENGLISH_CARIBBEAN = 9225;
                public static final int ENGLISH_INDIA = 16393;
                public static final int ENGLISH_IRELAND = 6153;
                public static final int ENGLISH_JAMAICA = 8201;
                public static final int ENGLISH_MALAYSIA = 17417;
                public static final int ENGLISH_NEW_ZEALAND = 5129;
                public static final int ENGLISH_REPUBLIC_PHILIPPINES = 13321;
                public static final int ENGLISH_SINGAPORE = 18441;
                public static final int ENGLISH_SOUTH_AFRICA = 7177;
                public static final int ENGLISH_TRINIDAD_TOBAGO = 11273;
                public static final int ENGLISH_UNITED_KINGDOM = 2057;
                public static final int ENGLISH_UNITED_STATES = 1033;
                public static final int ENGLISH_ZIMBABWE = 12297;
                
                public static Locale mapLocale(final int languageId) {
                    switch (languageId) {
                        default: {
                            return Locale.getDefault();
                        }
                        case 2052:
                        case 4100: {
                            return Locale.SIMPLIFIED_CHINESE;
                        }
                        case 1033:
                        case 1067:
                        case 2057:
                        case 3081:
                        case 4105:
                        case 5129:
                        case 6153:
                        case 7177:
                        case 8201:
                        case 9225:
                        case 10249:
                        case 11273:
                        case 12297:
                        case 13321:
                        case 16393:
                        case 17417:
                        case 18441: {
                            return Locale.ENGLISH;
                        }
                        case 1028:
                        case 3076:
                        case 5124: {
                            return Locale.TRADITIONAL_CHINESE;
                        }
                    }
                }
            }
            
            public static class Encoding
            {
                public static final int SYMBOL = 0;
                public static final int UNICODE_BMP = 1;
                public static final int SHIFT_JIS = 2;
                public static final int PRC = 3;
                public static final int BIG5 = 4;
                public static final int WAN_SUNG = 5;
                public static final int JOHAB = 6;
                
                public static Charset mapCharset(final int encodingId) {
                    switch (encodingId) {
                        default: {
                            return StandardCharsets.UTF_16;
                        }
                        case 4: {
                            return Charset.forName("Big5");
                        }
                        case 3: {
                            return Charset.forName("GB18030");
                        }
                        case 2: {
                            return Charset.forName("Shift-JIS");
                        }
                        case 1: {
                            return StandardCharsets.UTF_16;
                        }
                        case 0: {
                            return StandardCharsets.US_ASCII;
                        }
                    }
                }
            }
        }
    }
}
