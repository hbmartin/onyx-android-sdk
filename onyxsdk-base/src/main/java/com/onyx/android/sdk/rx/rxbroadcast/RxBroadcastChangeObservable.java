// 
// 

package com.onyx.android.sdk.rx.rxbroadcast;

import android.content.Context;
import androidx.annotation.NonNull;
import java.util.Iterator;
import com.onyx.android.sdk.utils.Debug;
import android.content.BroadcastReceiver;
import com.onyx.android.sdk.utils.ResManager;
import io.reactivex.disposables.Disposable;
import android.content.IntentFilter;
import io.reactivex.Observer;
import java.util.Collections;
import java.util.List;
import android.content.Intent;
import io.reactivex.Observable;

public class RxBroadcastChangeObservable extends Observable<Intent>
{
    private final List<String> a;
    private int b;
    
    public RxBroadcastChangeObservable(final String action) {
        this.b = 1;
        this.a = Collections.singletonList(action);
    }
    
    public RxBroadcastChangeObservable(final List<String> actions) {
        this.b = 1;
        this.a = actions;
    }
    
    public RxBroadcastChangeObservable setReceiverFlags(final int receiverFlags) {
        this.b = receiverFlags;
        return this;
    }
    
    protected void subscribeActual(final Observer<? super Intent> observer) {
        final IntentFilter intentFilter = new IntentFilter();
        final Iterator<String> iterator = this.a.iterator();
        while (iterator.hasNext()) {
            intentFilter.addAction((String)iterator.next());
        }
        try {
            final a listener;
            observer.onSubscribe((Disposable)(listener = this.createListener(observer)));
            ResManager.getAppContext().registerReceiver((BroadcastReceiver)listener, intentFilter, this.b);
        }
        catch (final Exception ex) {
            Debug.e((Throwable)ex);
        }
    }
    
    @NonNull
    protected a createListener(final Observer<? super Intent> observer) {
        return new a(observer, ResManager.getAppContext());
    }
    
    static class a extends BroadcastReceiver implements Disposable
    {
        private Observer<? super Intent> a;
        private final Context b;
        private boolean c;
        
        public a(final Observer<? super Intent> observer, final Context context) {
            this.a = observer;
            this.b = context;
        }
        
        public void onReceive(final Context context, final Intent intent) {
            this.a.onNext(intent);
        }
        
        public void dispose() {
            this.b.unregisterReceiver((BroadcastReceiver)this);
            this.c = true;
        }
        
        public boolean isDisposed() {
            return this.c;
        }
    }
}
