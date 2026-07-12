package com.onyx.android.sdk.rx.rxcontentobserver;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class RxContentObserver {
    private Disposable a;

    class a extends ContentObserver {
        final /* synthetic */ BehaviorSubject a;

        a(Handler x0, BehaviorSubject behaviorSubject) {
            super(x0);
            this.a = behaviorSubject;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            this.a.onNext(Boolean.valueOf(selfChange));
        }
    }

    private Observable<Boolean> d(Context context, RxContentSettingsType type, String key) {
        ContentResolver contentResolver = context.getContentResolver();
        BehaviorSubject behaviorSubjectCreate = BehaviorSubject.create();
        Uri uri = RxContentSettingsType.getUri(type, key);
        a aVar = new a(null, behaviorSubjectCreate);
        if (uri != null) {
            contentResolver.registerContentObserver(uri, false, aVar);
        }
        return behaviorSubjectCreate.doOnDispose(() -> {
            contentResolver.unregisterContentObserver(aVar);
        });
    }

    public Observable<String> buildForString(Context context, RxContentSettingsType type, String key) {
        return Observable.just(context).observeOn(Schedulers.io()).flatMap(c -> {
            return d(context, type, key);
        }).map(b -> {
            return c(context, type, key);
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread()).doOnSubscribe(this::a);
    }

    public Observable<Integer> buildForInt(Context context, RxContentSettingsType type, String key) {
        return Observable.just(context).observeOn(Schedulers.io()).flatMap(c -> {
            return d(context, type, key);
        }).map(b -> {
            return Integer.valueOf(b(context, type, key));
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread()).doOnSubscribe(this::a);
    }

    public Observable<Float> buildForFloat(Context context, RxContentSettingsType type, String key) {
        return Observable.just(context).observeOn(Schedulers.io()).flatMap(c -> {
            return d(context, type, key);
        }).map(b -> {
            return Float.valueOf(a(context, type, key));
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread()).doOnSubscribe(this::a);
    }

    public void dispose() {
        Disposable disposable = this.a;
        if (disposable == null) {
            return;
        }
        disposable.dispose();
        this.a = null;
    }

    private String c(Context context, RxContentSettingsType type, String key) throws Exception {
        return RxContentSettingsType.getString(context.getContentResolver(), type, key);
    }

    private int b(Context context, RxContentSettingsType type, String key) throws Exception {
        return RxContentSettingsType.getInt(context.getContentResolver(), type, key);
    }

    private float a(Context context, RxContentSettingsType type, String key) throws Exception {
        return RxContentSettingsType.getFloat(context.getContentResolver(), type, key);
    }

    private void a(Disposable disposable) {
        this.a = disposable;
    }
}
