// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import com.onyx.android.sdk.data.note.line.ShapeLineStyle;
import com.onyx.android.sdk.data.note.ShapeResource;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u001c\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R!\u0010 \u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\b\u0012\u0006\u0012\u0002\b\u00030\"0!¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$¨\u0006%" }, d2 = { "Lcom/onyx/android/sdk/utils/ShapeOptionsConstant;", "", "()V", "KEY_CUSTOM_ATTR", "", "KEY_DISPLAY_STROKE_WIDTH", "KEY_EXCERPT_COLLECTION_LIST", "KEY_IS_PLAIN_TEXT", "KEY_IS_SAVED", "KEY_IS_SELECTED", "KEY_IS_TRANSPARENT", "KEY_IS_VISIBLE", "KEY_OBJECT_ID", "KEY_PAGE_POSITION_V2", "KEY_PAGE_POSITION_V3", "KEY_PAINT_STYLE", "KEY_POSITION", "KEY_POSITION_INT", "KEY_READER_PAGE_UNIQUE_ID_V1", "KEY_READER_PAGE_UNIQUE_ID_V2", "KEY_REFLOW_MATRIX", "KEY_RELATIVE_MATRIX", "KEY_SCRIBBLE_TYPE", "KEY_SHAPE_LINT_STYLE", "KEY_SHAPE_RESOURCE", "KEY_SHAPE_STATUS", "KEY_SHAPE_STROKE_ID", "KEY_TAG_BEAN", "KEY_TAPE_STROKE_STYLE", "KEY_TEXT_TAG", "KEY_TRANSFORMING_MATRIX", "OPTIONS_REPO_NAME", "optionsTypeMap", "", "Ljava/lang/Class;", "getOptionsTypeMap", "()Ljava/util/Map;", "onyxsdk-base_release" })
public final class ShapeOptionsConstant
{
    @NotNull
    public static final ShapeOptionsConstant INSTANCE;
    @NotNull
    public static final String OPTIONS_REPO_NAME = "repo";
    @NotNull
    public static final String KEY_IS_TRANSPARENT = "KEY_IS_TRANSPARENT";
    @NotNull
    public static final String KEY_PAINT_STYLE = "KEY_PAINT_STYLE";
    @NotNull
    public static final String KEY_SHAPE_LINT_STYLE = "KEY_SHAPE_LINT_STYLE";
    @NotNull
    public static final String KEY_TRANSFORMING_MATRIX = "KEY_TRANSFORMING_MATRIX";
    @NotNull
    public static final String KEY_REFLOW_MATRIX = "KEY_REFLOW_MATRIX";
    @NotNull
    public static final String KEY_SHAPE_RESOURCE = "KEY_SHAPE_RESOURCE";
    @NotNull
    public static final String KEY_SHAPE_STROKE_ID = "KEY_SHAPE_STROKE_ID";
    @NotNull
    public static final String KEY_IS_VISIBLE = "KEY_IS_VISIBLE";
    @NotNull
    public static final String KEY_SHAPE_STATUS = "KEY_SHAPE_STATUS";
    @NotNull
    public static final String KEY_IS_SELECTED = "KEY_IS_SELECTED";
    @NotNull
    public static final String KEY_IS_SAVED = "KEY_IS_SAVED";
    @NotNull
    public static final String KEY_PAGE_POSITION_V2 = "KEY_PAGE_POSITION_V2";
    @NotNull
    public static final String KEY_PAGE_POSITION_V3 = "KEY_PAGE_POSITION_V3";
    @NotNull
    public static final String KEY_TAPE_STROKE_STYLE = "KEY_TAPE_STROKE_STYLE";
    @NotNull
    public static final String KEY_EXCERPT_COLLECTION_LIST = "key_excerpt_collection_list";
    @NotNull
    public static final String KEY_TAG_BEAN = "KEY_TAG_BEAN";
    @NotNull
    public static final String KEY_OBJECT_ID = "KEY_OBJECT_ID";
    @NotNull
    public static final String KEY_TEXT_TAG = "KEY_TEXT_TAG";
    @NotNull
    public static final String KEY_READER_PAGE_UNIQUE_ID_V1 = "KEY_READER_PAGE_UNIQUE_ID_V1";
    @NotNull
    public static final String KEY_READER_PAGE_UNIQUE_ID_V2 = "KEY_READER_PAGE_UNIQUE_ID_V2";
    @NotNull
    public static final String KEY_SCRIBBLE_TYPE = "KEY_SCRIBBLE_TYPE";
    @NotNull
    public static final String KEY_CUSTOM_ATTR = "KEY_CUSTOM_ATTR";
    @NotNull
    public static final String KEY_DISPLAY_STROKE_WIDTH = "KEY_DISPLAY_STROKE_WIDTH";
    @NotNull
    public static final String KEY_POSITION = "KEY_POSITION";
    @NotNull
    public static final String KEY_POSITION_INT = "KEY_POSITION_INT";
    @NotNull
    public static final String KEY_RELATIVE_MATRIX = "KEY_RELATIVE_MATRIX";
    @NotNull
    public static final String KEY_IS_PLAIN_TEXT = "KEY_IS_PLAIN_TEXT";
    @NotNull
    private static final Map<String, Class<?>> a;
    
    private ShapeOptionsConstant() {
    }
    
    static {
        INSTANCE = new ShapeOptionsConstant();
        final HashMap<String, Class<?>> a2 = new HashMap<>();
        a2.put("KEY_SHAPE_RESOURCE", ShapeResource.class);
        a2.put("KEY_SHAPE_LINT_STYLE", ShapeLineStyle.class);
        a = a2;
    }
    
    @NotNull
    public final Map<String, Class<?>> getOptionsTypeMap() {
        return ShapeOptionsConstant.a;
    }
}
