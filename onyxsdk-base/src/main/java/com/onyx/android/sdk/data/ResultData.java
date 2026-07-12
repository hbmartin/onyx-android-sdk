package com.onyx.android.sdk.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/ResultData.class */
public class ResultData<T> implements Serializable {
    public List<T> list = new ArrayList();
    public int count;
}
