package com.onyx.android.sdk.utils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Locale;

public class TTCFont {
    public static final String FONT_NAME_DELIMITER = " & ";
    private boolean a = false;
    private TTFFont b;
    private String c = "";
    private String d = "";
    private DeviceUtils.FontType e = DeviceUtils.FontType.ALL;
    private boolean f = false;

    public TTCFont() {}

    private void a() {
        c = "";
        d = "";
        e = DeviceUtils.FontType.ALL;
        f = false;
        if (b != null) b.clear();
    }

    private void a(RandomAccessFile input) throws IOException {
        byte[] signature = new byte[4];
        input.readFully(signature);
        log(new String(signature));
        input.readInt();
        int count = input.readInt();
        int[] offsets = new int[count];
        for (int i = 0; i < count; i++) offsets[i] = input.readInt();
        for (int offset : offsets) {
            input.seek(offset);
            if (b == null) b = new TTFFont();
            b.clear();
            b.parseInner(input);
            TTFFont.b display = b.getFontDisplayNameAndFontType();
            c = a(display.a(), c);
            a(display.b());
            d = a(b.getFontUniqueNameAndFontType().a(), d);
            f |= display.c();
        }
    }

    private String a(String parsed, String current) {
        if (StringUtils.isNullOrEmpty(parsed)) return current;
        if (StringUtils.isNullOrEmpty(current)) return parsed;
        return current + FONT_NAME_DELIMITER + parsed;
    }

    private void a(DeviceUtils.FontType type) {
        if (e != DeviceUtils.FontType.CJK) e = type;
    }

    public void log(String msg) {
        if (a) Debug.d(getClass(), msg, new Object[0]);
    }

    public TTCFont parse(String file) {
        log(file);
        a();
        try (RandomAccessFile input = new RandomAccessFile(file, "r")) {
            a(input);
        } catch (Exception failure) {
            Debug.w(failure);
        }
        return this;
    }

    public String getFontNameByTest(Locale locale) {
        return b == null ? null : b.getFontName(locale);
    }

    public TTCFont setTtfFont(TTFFont ttfFont) {
        b = ttfFont;
        return this;
    }

    public TTFFont.b getFontDisplayNameAndFontType() {
        return new TTFFont.b(c, e, f);
    }

    public String getFontUniqueName() {
        return d;
    }
}
