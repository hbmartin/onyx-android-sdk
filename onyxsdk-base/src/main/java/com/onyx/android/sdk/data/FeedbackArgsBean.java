package com.onyx.android.sdk.data;

import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.utils.CollectionUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/FeedbackArgsBean.class */
public class FeedbackArgsBean {
    private String a;
    private String b;
    private String c;
    private boolean d;
    private Collection<String> e;
    private ComplainInfo f;

    public String getDesc() {
        return this.a;
    }

    public FeedbackArgsBean setDesc(String desc) {
        this.a = desc;
        return this;
    }

    public String getEmail() {
        return this.b;
    }

    public FeedbackArgsBean setEmail(String email) {
        this.b = email;
        return this;
    }

    public boolean isSendLog() {
        return this.d;
    }

    public FeedbackArgsBean setSendLog(boolean sendLog) {
        this.d = sendLog;
        return this;
    }

    @JSONField(serialize = false, deserialize = false)
    public Collection<File> getFiles() {
        if (CollectionUtils.isNullOrEmpty(this.e)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = this.e.iterator();
        while (it.hasNext()) {
            arrayList.add(new File(it.next()));
        }
        return arrayList;
    }

    public FeedbackArgsBean setFilePaths(Collection<String> filePaths) {
        this.e = filePaths;
        return this;
    }

    public Collection<String> getFilePaths() {
        return this.e;
    }

    public String getSerialNumber() {
        return this.c;
    }

    public void setSerialNumber(String serialNumber) {
        this.c = serialNumber;
    }

    public ComplainInfo getComplainInfo() {
        return this.f;
    }

    public FeedbackArgsBean setComplainInfo(ComplainInfo complainInfo) {
        this.f = complainInfo;
        return this;
    }
}
