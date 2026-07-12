package com.onyx.android.sdk.utils;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/LongClickUtils.class */
public class LongClickUtils {

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/LongClickUtils$a.class */
    static class a implements View.OnTouchListener {
        private int a;
        private int b;
        private boolean c = false;
        final int d;
        private final Runnable e;
        final /* synthetic */ View f;
        final /* synthetic */ Handler g;
        final /* synthetic */ long h;
        final /* synthetic */ View.OnLongClickListener i;

        a(View view, Handler handler, long j, View.OnLongClickListener onLongClickListener) {
            this.f = view;
            this.g = handler;
            this.h = j;
            this.i = onLongClickListener;
            this.d = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
            this.e = () -> {
                this.c = true;
                if (onLongClickListener != null) {
                    onLongClickListener.onLongClick(view);
                }
            };
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View v, MotionEvent event) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            switch (event.getAction()) {
                case 0:
                    this.c = false;
                    this.g.removeCallbacks(this.e);
                    this.a = x;
                    this.b = y;
                    this.g.postDelayed(this.e, this.h);
                    return false;
                case 1:
                    break;
                case 2:
                    if (Math.abs(this.a - x) <= this.d && Math.abs(this.b - y) <= this.d) {
                        return false;
                    }
                    this.g.removeCallbacks(this.e);
                    return false;
                case 3:
                    this.c = false;
                    break;
                default:
                    return false;
            }
            this.g.removeCallbacks(this.e);
            return this.c;
        }
    }

    public static void setLongClick(View longClickView, long delayMillis, View.OnLongClickListener longClickListener) {
        longClickView.setOnTouchListener(new a(longClickView, new Handler(), delayMillis, longClickListener));
    }
}
