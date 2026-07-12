package com.onyx.android.sdk.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultData<T> implements Serializable {
    public List<T> list = new ArrayList();
    public int count;
}
