package com.onyx.android.sdk.rx;

public abstract class RxAction<T extends RxRequest> {
   public abstract void execute(RxCallback<T> var1);
}
