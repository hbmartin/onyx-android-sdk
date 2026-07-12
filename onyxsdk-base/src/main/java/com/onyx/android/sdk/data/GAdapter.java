package com.onyx.android.sdk.data;

import androidx.core.util.Consumer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/GAdapter.class */
public class GAdapter {
    private ArrayList<GObject> a = new ArrayList<>();
    private GObject b = new GObject();
    private Map<String, Integer> c;
    private GObjStateRecordConfig d;

    public static GAdapter createFromStringList(Collection<String> list, String tag) {
        GAdapter gAdapter = new GAdapter();
        for (String str : list) {
            GObject gObject = new GObject();
            gObject.putString(tag, str);
            gAdapter.getList().add(gObject);
        }
        return gAdapter;
    }

    public static GAdapter createFromGObjectList(Collection<GObject> list) {
        GAdapter gAdapter = new GAdapter();
        gAdapter.a.addAll(list);
        return gAdapter;
    }

    public void addObject(GObject object) {
        this.a.add(object);
    }

    public void addObjects(Collection<GObject> object) {
        this.a.addAll(object);
    }

    public int getGObjectIndex(GObject object) {
        return this.a.indexOf(object);
    }

    public int getIndexByValue(Object value, String tag) {
        if (this.a == null) {
            return -1;
        }
        for (int i = 0; i < this.a.size(); i++) {
            if (this.a.get(i).getObject(tag).equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public void sortByKey(String key, boolean ascending) {
    }

    public ArrayList<GObject> searchByTag(String tag, Object pattern) {
        ArrayList<GObject> arrayList = new ArrayList<>();
        for (GObject gObject : this.a) {
            if (gObject.matches(tag, pattern)) {
                arrayList.add(gObject);
            }
        }
        return arrayList;
    }

    public GObject searchFirstByTag(String tag, Object pattern) {
        ArrayList<GObject> arrayListSearchByTag = searchByTag(tag, pattern);
        if (arrayListSearchByTag.size() <= 0) {
            return null;
        }
        return arrayListSearchByTag.get(0);
    }

    public void setMapping(Map<String, Integer> map) {
        this.c = map;
    }

    public final Map<String, Integer> getMapping() {
        return this.c;
    }

    public int size() {
        ArrayList<GObject> arrayList = this.a;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public GObject get(int index) {
        ArrayList<GObject> arrayList = this.a;
        if (arrayList == null || index < 0 || index >= arrayList.size()) {
            return null;
        }
        return this.a.get(index);
    }

    public boolean setObject(int index, GObject newObject) {
        ArrayList<GObject> arrayList = this.a;
        if (arrayList == null || index < 0 || index >= arrayList.size()) {
            return false;
        }
        this.a.set(index, newObject);
        return true;
    }

    public final ArrayList<GObject> getList() {
        return this.a;
    }

    public final GObject getOptions() {
        return this.b;
    }

    public boolean append(GAdapter another) {
        if (another == null || another.a == null) {
            return false;
        }
        ArrayList<GObject> arrayList = this.a;
        if (arrayList == null) {
            this.a = another.getList();
            return true;
        }
        arrayList.addAll(another.getList());
        return true;
    }

    public void shouldRecordState(Consumer<String> consumer) {
        GObjStateRecordConfig gObjStateRecordConfig = this.d;
        if (gObjStateRecordConfig != null) {
            gObjStateRecordConfig.shouldRecordState(consumer);
        }
    }

    public void setStateConfig(GObjStateRecordConfig stateConfig) {
        this.d = stateConfig;
    }

    public GObjStateRecordConfig getStateConfig() {
        return this.d;
    }

    public void addObject(int index, GObject object) {
        this.a.add(index, object);
    }

    public boolean append(int index, GAdapter another) {
        if (another == null || another.a == null) {
            return false;
        }
        ArrayList<GObject> arrayList = this.a;
        if (arrayList == null) {
            this.a = another.getList();
            return true;
        }
        arrayList.addAll(index, another.getList());
        return true;
    }
}
