package com.onyx.android.sdk.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/ServerInfoResultBean.class */
public class ServerInfoResultBean implements Serializable {
    public String result_code;
    public String message;
    public Data data;

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/ServerInfoResultBean$Data.class */
    public static class Data {
        public int count;
        public List<ServerInfo> results;

        public List<ServerInfo> getResults() {
            return this.results;
        }

        public void setResults(List<ServerInfo> results) {
            if (results == null) {
                results = new ArrayList<>();
            }
            this.results = results;
        }
    }

    public Data getData() {
        return this.data;
    }

    public void setData(Data data) {
        if (data == null) {
            data = new Data();
        }
        this.data = data;
    }
}
