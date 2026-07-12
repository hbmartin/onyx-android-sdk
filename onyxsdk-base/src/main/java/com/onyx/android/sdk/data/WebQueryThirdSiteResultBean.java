package com.onyx.android.sdk.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/WebQueryThirdSiteResultBean.class */
public class WebQueryThirdSiteResultBean implements Serializable {
    private Data a;

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/WebQueryThirdSiteResultBean$Data.class */
    public static class Data {
        private List<WebQueryThirdSiteBean> a;

        public List<WebQueryThirdSiteBean> getResults() {
            return this.a;
        }

        public void setResults(List<WebQueryThirdSiteBean> r) {
            if (r == null) {
                r = new ArrayList<>();
            }
            this.a = r;
        }
    }

    public Data getData() {
        return this.a;
    }

    public void setData(Data data) {
        this.a = data;
    }
}
