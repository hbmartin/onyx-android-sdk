package com.onyx.android.sdk.data.note;

import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.data.ReaderTextStyle;
import com.onyx.android.sdk.data.note.line.ShapeLineStyle;
import com.onyx.android.sdk.utils.CollectionUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class NotePenInfo implements Serializable {
    public Map<Integer, Float> penWithMap;
    public Map<Integer, Float> eraseWidthMap;
    public Map<Integer, Float> penPressureSensitivityMap;
    public Map<Integer, Float> penSmoothLevelMap;
    private int a;
    private int b;
    private int c;
    private int d;
    private ShapeLineStyle e;
    private ShapeLineStyle f;
    private QuickPenList g;
    private PenAttrs h;

    public NotePenInfo() {
        this.penWithMap = new HashMap();
        this.eraseWidthMap = new HashMap();
        this.penPressureSensitivityMap = new HashMap();
        this.penSmoothLevelMap = new HashMap();
        this.a = 5;
        this.b = 7;
        this.c = -16777216;
        this.d = -16777216;
        this.e = new ShapeLineStyle();
        this.f = new ShapeLineStyle();
    }

    public int getNormalPenShapeType() {
        return this.a;
    }

    public NotePenInfo setNormalPenShapeType(int normalPenShapeType) {
        this.a = normalPenShapeType;
        return this;
    }

    public int getGraphicsShapeType() {
        return this.b;
    }

    public NotePenInfo setGraphicsShapeType(int graphicsShapeType) {
        this.b = graphicsShapeType;
        return this;
    }

    public int getGraphicsShapeColor() {
        return this.c;
    }

    public void setGraphicsShapeColor(int graphicsShapeColor) {
        this.c = graphicsShapeColor;
    }

    public void setFillColor(int fillColor) {
        this.d = fillColor;
    }

    public int getFillColor() {
        return this.d;
    }

    public void setShapeLineStyle(ShapeLineStyle shapeLineStyle) {
        this.e = shapeLineStyle;
    }

    public void setPenLineStyle(ShapeLineStyle penLineStyle) {
        this.f = penLineStyle;
    }

    public void setQuickPenList(QuickPenList list) {
        this.g = list;
    }

    public QuickPenList getQuickPenList() {
        return this.g;
    }

    public ShapeLineStyle getShapeLineStyle() {
        return this.e;
    }

    public ShapeLineStyle getPenLineStyle() {
        return this.f;
    }

    public ShapeLineStyle cloneShapeLineStyle() {
        return this.e.clone();
    }

    public void setPenAttrs(PenAttrs penAttrs) {
        this.h = penAttrs;
    }

    public PenAttrs getPenAttrs() {
        return this.h;
    }

    @JSONField(serialize = false, deserialize = false)
    public void setPenWidth(int shapeType, float strokeWidth) {
        Map<Integer, Float> map = this.penWithMap;
        if (map != null) {
            map.put(Integer.valueOf(shapeType), Float.valueOf(strokeWidth));
        }
    }

    @JSONField(serialize = false, deserialize = false)
    public void setEraseWidth(int eraseType, float eraseWidth) {
        if (this.eraseWidthMap == null) {
            this.eraseWidthMap = new HashMap();
        }
        this.eraseWidthMap.put(Integer.valueOf(eraseType), Float.valueOf(eraseWidth));
    }

    @JSONField(serialize = false, deserialize = false)
    public float getEraseWidth(int eraseType, float defaultValue) {
        Map<Integer, Float> map = this.eraseWidthMap;
        if (map == null) {
            return defaultValue;
        }
        Float f = map.get(Integer.valueOf(eraseType));
        return (f == null || f.floatValue() <= ReaderTextStyle.FONT_EMBOLDEN_NORMAL) ? defaultValue : f.floatValue();
    }

    @JSONField(serialize = false, deserialize = false)
    public void setPenPressureSensitivity(int shapeType, Float sensitivity) {
        if (this.penPressureSensitivityMap == null) {
            this.penPressureSensitivityMap = new HashMap();
        }
        this.penPressureSensitivityMap.put(Integer.valueOf(shapeType), sensitivity);
    }

    @JSONField(serialize = false, deserialize = false)
    public Float getPenPressureSensitivity(int shapeType, Float defaultValue) {
        if (CollectionUtils.isNullOrEmpty(this.penPressureSensitivityMap)) {
            return defaultValue;
        }
        Float f = this.penPressureSensitivityMap.get(Integer.valueOf(shapeType));
        return (f == null || Float.compare(f.floatValue(), ReaderTextStyle.FONT_EMBOLDEN_NORMAL) < 0) ? defaultValue : f;
    }

    @JSONField(serialize = false, deserialize = false)
    public void setPenSmoothLevel(int shapeType, Float smoothLevel) {
        if (this.penSmoothLevelMap == null) {
            this.penSmoothLevelMap = new HashMap();
        }
        this.penSmoothLevelMap.put(Integer.valueOf(shapeType), smoothLevel);
    }

    @JSONField(serialize = false, deserialize = false)
    public Float getPenSmoothLevel(int shapeType, Float defaultValue) {
        if (CollectionUtils.isNullOrEmpty(this.penSmoothLevelMap)) {
            return defaultValue;
        }
        Float f = this.penSmoothLevelMap.get(Integer.valueOf(shapeType));
        return (f == null || Float.compare(f.floatValue(), ReaderTextStyle.FONT_EMBOLDEN_NORMAL) < 0) ? defaultValue : f;
    }

    public NotePenInfo(Map<Integer, Float> penWithMap) {
        this.penWithMap = new HashMap();
        this.eraseWidthMap = new HashMap();
        this.penPressureSensitivityMap = new HashMap();
        this.penSmoothLevelMap = new HashMap();
        this.a = 5;
        this.b = 7;
        this.c = -16777216;
        this.d = -16777216;
        this.e = new ShapeLineStyle();
        this.f = new ShapeLineStyle();
        this.penWithMap = penWithMap;
    }
}
