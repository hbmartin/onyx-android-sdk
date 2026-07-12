package com.onyx.android.sdk.api.data.model;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/api/data/model/NetWorkBean.class */
public class NetWorkBean {
    public boolean connect;
    public boolean available;

    public NetWorkBean setConnect(boolean connect) {
        this.connect = connect;
        return this;
    }

    public NetWorkBean setAvailable(boolean available) {
        this.available = available;
        return this;
    }
}
