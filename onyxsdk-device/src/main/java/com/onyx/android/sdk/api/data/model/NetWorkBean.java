package com.onyx.android.sdk.api.data.model;

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
