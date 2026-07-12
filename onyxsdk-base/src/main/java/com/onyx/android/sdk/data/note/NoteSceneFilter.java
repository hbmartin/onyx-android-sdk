package com.onyx.android.sdk.data.note;

import com.onyx.android.sdk.utils.CollectionUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/note/NoteSceneFilter.class */
public class NoteSceneFilter {
    private List<Integer> a = new ArrayList();

    public static NoteSceneFilter create(int filterScene) {
        return new NoteSceneFilter().setFilerScenes(Collections.singletonList(Integer.valueOf(filterScene)));
    }

    public NoteSceneFilter setFilerScenes(List<Integer> filerScenes) {
        this.a = filerScenes;
        return this;
    }

    public NoteSceneFilter addScene(int scene) {
        CollectionUtils.safeAdd(this.a, Integer.valueOf(scene));
        return this;
    }

    public List<Integer> getFilerScenes() {
        return this.a;
    }

    public boolean containsFilterScene(int scene) {
        return this.a.contains(Integer.valueOf(scene));
    }

    public static NoteSceneFilter create(List<Integer> filterScenes) {
        return new NoteSceneFilter().setFilerScenes(filterScenes);
    }
}
