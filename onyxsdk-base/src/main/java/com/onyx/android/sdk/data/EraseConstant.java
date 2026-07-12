// 
// 

package com.onyx.android.sdk.data;

import java.util.HashMap;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.JvmField;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\nH\u0007J$\u0010\u000e\u001a\u001e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00040\u000fj\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0004`\u0011H\u0007R\u0012\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0012" }, d2 = { "Lcom/onyx/android/sdk/data/EraseConstant;", "", "()V", "DEFAULT_ERASER_MOVE_WIDTH", "", "DEFAULT_ERASER_MOVE_WIDTH_MM", "DEFAULT_ERASER_STROKE_WIDTH", "DEFAULT_ERASER_STROKE_WIDTH_MM", "DEFAULT_STROKE_ERASER_WIDTH", "ERASER_PREVIEW_MAX_SIZE_10_MM", "", "STROKE_ERASE_MM_WIDTH", "getDefaultEraserWidth", "eraseType", "getDefaultEraserWidthMap", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "onyxsdk-base_release" })
public final class EraseConstant
{
    @NotNull
    public static final EraseConstant INSTANCE;
    public static final float DEFAULT_STROKE_ERASER_WIDTH = 60.0f;
    public static final int ERASER_PREVIEW_MAX_SIZE_10_MM = 120;
    public static final float STROKE_ERASE_MM_WIDTH = 0.5f;
    @JvmField
    public static float DEFAULT_ERASER_MOVE_WIDTH;
    public static final float DEFAULT_ERASER_MOVE_WIDTH_MM = 5.0f;
    @JvmField
    public static float DEFAULT_ERASER_STROKE_WIDTH;
    public static final float DEFAULT_ERASER_STROKE_WIDTH_MM = 0.5f;
    
    private EraseConstant() {
    }
    
    @JvmStatic
    public static final float getDefaultEraserWidth(final int eraseType) {
        return (eraseType != 0) ? ((eraseType != 1) ? EraseConstant.DEFAULT_ERASER_STROKE_WIDTH : EraseConstant.DEFAULT_ERASER_MOVE_WIDTH) : EraseConstant.DEFAULT_ERASER_STROKE_WIDTH;
    }
    
    @JvmStatic
    @NotNull
    public static final HashMap<String, Float> getDefaultEraserWidthMap() {
        final HashMap hashMap = new HashMap();
        hashMap.put("1", EraseConstant.DEFAULT_ERASER_MOVE_WIDTH);
        hashMap.put("0", EraseConstant.DEFAULT_ERASER_STROKE_WIDTH);
        return hashMap;
    }
    
    static {
        INSTANCE = new EraseConstant();
        EraseConstant.DEFAULT_ERASER_MOVE_WIDTH = 59.0f;
        EraseConstant.DEFAULT_ERASER_STROKE_WIDTH = 5.9f;
    }
}
