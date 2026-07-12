package com.onyx.android.sdk.rx;

import androidx.annotation.WorkerThread;
import com.onyx.android.sdk.rx.RxRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/rx/RequestChain.class */
public class RequestChain<T extends RxRequest> extends RxRequest {
    private List<T> c = new ArrayList();

    @Override // com.onyx.android.sdk.rx.RxRequest
    public void execute() throws Exception {
        for (T t : this.c) {
            if (isAbort()) {
                return;
            }
            t.setContext(getContext());
            beforeExecute(t);
            t.execute();
            afterExecute(t);
        }
    }

    @Override // com.onyx.android.sdk.rx.RxRequest
    public void setAbort() {
        super.setAbort();
        Iterator<T> it = this.c.iterator();
        while (it.hasNext()) {
            it.next().setAbort();
        }
    }

    @WorkerThread
    public void beforeExecute(RxRequest request) throws Exception {
    }

    @WorkerThread
    public void afterExecute(RxRequest request) throws Exception {
    }

    public RequestChain addRequest(T request) {
        this.c.add(request);
        return this;
    }

    public RequestChain addRequestList(List<T> list) {
        this.c.addAll(list);
        return this;
    }

    public List<T> getRequestList() {
        return this.c;
    }
}
