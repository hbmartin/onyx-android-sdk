package com.onyx.android.sdk.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServerInfoResultBean implements Serializable {
    public String result_code;
    public String message;
    public Data data;

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
