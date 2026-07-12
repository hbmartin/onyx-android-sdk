package com.onyx.android.sdk.data.note;

import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.utils.CollectionUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PageInfo implements Serializable, Cloneable {
    private int a = 0;
    private List<LayerInfo> b;
    private int c;
    private int d;
    private String e;
    private long f;

    private int a() {
        if (CollectionUtils.isNullOrEmpty(this.b)) {
            return 0;
        }
        for (int i = 0; i < 5; i++) {
            boolean z = false;
            for (LayerInfo layerInfo : this.b) {
                if (layerInfo != null && layerInfo.getId() == i) {
                    z = true;
                    break;
                }
            }
            if (!z) {
                return i;
            }
        }
        return 0;
    }

    @Nullable
    @JSONField(serialize = false)
    private LayerInfo getLayerInfo(int layerId) {
        for (LayerInfo layerInfo : getLayerList()) {
            if (layerInfo.getId() == layerId) {
                return layerInfo;
            }
        }
        return null;
    }

    @JSONField(serialize = false)
    private boolean layerInfoExist(LayerInfo layerInfo) {
        return getLayerInfo(layerInfo.getId()) != null;
    }

    @JSONField(serialize = false)
    private void addLayerInfo(LayerInfo layerInfo) {
        if (layerInfoExist(layerInfo)) {
            return;
        }
        getLayerList().add(layerInfo);
    }

    public int getCurrentLayerId() {
        return this.a;
    }

    public PageInfo setCurrentLayerId(int currentLayerId) {
        this.a = currentLayerId;
        return this;
    }

    public List<LayerInfo> getLayerList() {
        if (this.b == null) {
            this.b = new ArrayList();
        }
        int size = CollectionUtils.getSize(this.b);
        for (int i = 0; i < size; i++) {
            if (this.b.get(i) == null) {
                this.b.set(i, new LayerInfo().setId(a()));
            }
        }
        if (this.b.isEmpty()) {
            this.b.add(new LayerInfo());
        }
        return this.b;
    }

    public void setLayerList(List<LayerInfo> layerList) {
        this.b = layerList;
    }

    @JSONField(serialize = false, deserialize = false)
    public int getLayerCount() {
        return getLayerList().size();
    }

    public int getHeight() {
        return this.d;
    }

    public PageInfo setHeight(int height) {
        this.d = height;
        return this;
    }

    public int getWidth() {
        return this.c;
    }

    public PageInfo setWidth(int width) {
        this.c = width;
        return this;
    }

    public PageInfo setPageRect(Rect rect) {
        setWidth(rect.width());
        setHeight(rect.height());
        return this;
    }

    public String getTitle() {
        return this.e;
    }

    public PageInfo setTitle(String title) {
        this.e = title;
        return this;
    }

    public void setLastModifyTime(long time) {
        this.f = time;
    }

    public long getLastModifyTime() {
        return this.f;
    }

    @JSONField(serialize = false)
    public void mergePageInfo(PageInfo mergeInfo) {
        if (mergeInfo == null) {
            return;
        }
        setWidth(mergeInfo.c);
        setHeight(mergeInfo.d);
        setTitle(mergeInfo.e);
        setCurrentLayerId(mergeInfo.getCurrentLayerId());
        for (LayerInfo layerInfo : mergeInfo.getLayerList()) {
            LayerInfo layerInfo2 = getLayerInfo(layerInfo.getId());
            if (layerInfo2 == null) {
                addLayerInfo(layerInfo);
            } else {
                layerInfo2.mergeLayerInfo(layerInfo);
            }
        }
    }

    @JSONField(serialize = false)
    public void updatePageInfo(PageInfo otherPageInfo) {
        if (otherPageInfo == null) {
            return;
        }
        List<LayerInfo> layerList = otherPageInfo.getLayerList();
        if (CollectionUtils.isNullOrEmpty(this.b)) {
            this.b = layerList;
            setCurrentLayerId(otherPageInfo.getCurrentLayerId());
        }
    }

    public String toString() {
        return "PageInfo{width=" + this.c + ", height=" + this.d + ", title='" + this.e + "'}";
    }

    @JSONField(serialize = false)
    public boolean layerInfoExist(int layerId) {
        return getLayerInfo(layerId) != null;
    }

    @NonNull
    public PageInfo clone() throws CloneNotSupportedException {
        PageInfo pageInfo = (PageInfo) super.clone();
        if (this.b != null) {
            ArrayList arrayList = new ArrayList();
            Iterator<LayerInfo> it = this.b.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().clone());
            }
            pageInfo.setLayerList(arrayList);
        }
        pageInfo.setTitle(this.e);
        return pageInfo;
    }
}
