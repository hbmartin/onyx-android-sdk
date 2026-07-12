// 
// 

package com.onyx.android.sdk.extension;

import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\f\n\u0002\b\u0005\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0004\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0007" }, d2 = { "isAlpha", "", "", "isChinese", "isChineseCharacter", "isJapaneseCharacter", "isKoreanCharacter", "onyxsdk-base_release" })
public final class CharKt
{
    private CharKt() {
    }

    public static final boolean isChinese(final char $this$isChinese) {
        return ('\u3000' <= $this$isChinese && $this$isChinese < '\ud800') || ('\uf900' <= $this$isChinese && $this$isChinese < '\ufb00') || ('\ufe30' <= $this$isChinese && $this$isChinese < '\ufe4f') || ('\uff01' <= $this$isChinese && $this$isChinese < '\uff20');
    }
    
    public static final boolean isAlpha(final char $this$isAlpha) {
        return ('A' <= $this$isAlpha && $this$isAlpha < '{') || ('\u00c0' <= $this$isAlpha && $this$isAlpha < '\u00d7') || ('\u00d8' <= $this$isAlpha && $this$isAlpha < '\u00f7') || ('\u00f8' <= $this$isAlpha && $this$isAlpha < '\u0100') || ('\u0100' <= $this$isAlpha && $this$isAlpha < '\u0180') || ('\u0180' <= $this$isAlpha && $this$isAlpha < '\u0250') || '\u0386' == $this$isAlpha || ('\u0388' <= $this$isAlpha && $this$isAlpha < '\u0400') || ('\u0400' <= $this$isAlpha && $this$isAlpha < '\u0482') || ('\u048a' <= $this$isAlpha && $this$isAlpha < '\u0500') || ('\u0500' <= $this$isAlpha && $this$isAlpha < '\u0530') || ('\u1e00' <= $this$isAlpha && $this$isAlpha < '\u1f00') || ('\u0600' <= $this$isAlpha && $this$isAlpha < '\u0700') || ('\u0750' <= $this$isAlpha && $this$isAlpha < '\u0780') || ('\ufb50' <= $this$isAlpha && $this$isAlpha < '\ufe00') || ('\ufe70' <= $this$isAlpha && $this$isAlpha < '\uff00');
    }
    
    public static final boolean isChineseCharacter(final char $this$isChineseCharacter) {
        return '\u4e00' <= $this$isChineseCharacter && $this$isChineseCharacter < '\ua000';
    }
    
    public static final boolean isKoreanCharacter(final char $this$isKoreanCharacter) {
        return ('\uac00' <= $this$isKoreanCharacter && $this$isKoreanCharacter < '\ud7a4') || ('\u1100' <= $this$isKoreanCharacter && $this$isKoreanCharacter < '\u1200') || ('\u3130' <= $this$isKoreanCharacter && $this$isKoreanCharacter < '\u3190');
    }
    
    public static final boolean isJapaneseCharacter(final char $this$isJapaneseCharacter) {
        return ('\u3040' <= $this$isJapaneseCharacter && $this$isJapaneseCharacter < '\u30a0') || ('\u30a0' <= $this$isJapaneseCharacter && $this$isJapaneseCharacter < '\u3100') || ('\u4e00' <= $this$isJapaneseCharacter && $this$isJapaneseCharacter < '\ua000');
    }
}
