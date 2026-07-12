package com.onyx.android.sdk.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringUtilsXmlTest {
    @Test
    public void xmlEscapeNumericEscapesC1Controls() {
        assertEquals("&#127;&#132;&#134;&#159;",
                StringUtils.XMLEscapeHandle("\u007f\u0084\u0086\u009f"));
    }

    @Test
    public void xmlEscapePreservesEntitiesAndValidCharacterBoundaries() {
        assertEquals("&amp;&lt;&gt;&quot;&apos;\t\n\r\ud7ff\ue000\ufffd\ud800\udc00",
                StringUtils.XMLEscapeHandle("&<>\"'\t\n\r\ud7ff\ue000\ufffd\ud800\udc00"));
    }

    @Test
    public void xmlEscapeDropsInvalidXmlCharacters() {
        assertEquals("valid", StringUtils.XMLEscapeHandle("\u0000\u0008\ud800valid"));
    }
}
