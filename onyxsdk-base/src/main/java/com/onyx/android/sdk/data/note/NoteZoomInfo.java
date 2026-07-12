// 
// 

package com.onyx.android.sdk.data.note;

import androidx.annotation.NonNull;
import android.graphics.RectF;
import com.alibaba.fastjson2.annotation.JSONField;
import androidx.annotation.Nullable;
import java.io.Serializable;

public class NoteZoomInfo implements Serializable, Cloneable
{
    private float a;
    private TouchPoint b;
    private float c;
    private float d;
    @Deprecated
    public boolean fitToScreen;
    private int e;
    
    public NoteZoomInfo() {
        this.a = 1.0f;
        this.b = new TouchPoint();
        this.fitToScreen = false;
        this.e = 0;
    }
    
    private void a() {
        if (this.fitToScreen && this.e == 0) {
            this.e = 1;
        }
        this.fitToScreen = false;
    }
    
    @Nullable
    public static NoteZoomInfo clone(@Nullable final NoteZoomInfo zoomInfo) {
        if (zoomInfo == null) {
            return null;
        }
        return zoomInfo.clone();
    }
    
    public float getViewportScale() {
        return this.a;
    }
    
    public void setViewportScale(final float viewportScale) {
        this.a = viewportScale;
    }
    
    public TouchPoint getViewPortPos() {
        return this.b;
    }
    
    public void setViewPortPos(final TouchPoint viewPortPos) {
        this.b = viewPortPos;
    }
    
    public float getViewPortWidth() {
        return this.c;
    }
    
    public void setViewPortWidth(final float viewPortWidth) {
        this.c = viewPortWidth;
    }
    
    public float getViewPortHeight() {
        return this.d;
    }
    
    public void setViewPortHeight(final float viewPortHeight) {
        this.d = viewPortHeight;
    }
    
    public int getScaleType() {
        this.a();
        return this.e;
    }
    
    @JSONField(serialize = false, deserialize = false)
    public boolean isFitScreenType() {
        return this.getScaleType() == 1;
    }
    
    public void setScaleType(final int scaleType) {
        this.e = scaleType;
    }
    
    public boolean isViewPortChange(final RectF viewPortRect) {
        return Math.round(this.c) != Math.round(viewPortRect.width()) || Math.round(this.d) != Math.round(viewPortRect.height());
    }
    
    @NonNull
    @Override
    protected NoteZoomInfo clone() {
        NoteZoomInfo noteZoomInfo = null;
        try {
            final NoteZoomInfo noteZoomInfo2 = (NoteZoomInfo)super.clone();
            final NoteZoomInfo noteZoomInfo3;
            noteZoomInfo = (noteZoomInfo3 = noteZoomInfo2);
            noteZoomInfo.setViewPortPos(this.b.clone());
            noteZoomInfo3.setViewPortWidth(this.c);
            noteZoomInfo2.setViewPortHeight(this.d);
        }
        catch (final CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
        return noteZoomInfo;
    }
}
