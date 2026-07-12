// 
// 

package com.onyx.android.sdk.utils;

import androidx.databinding.ViewDataBinding;
import com.onyx.android.sdk.rx.RxUtils;
import io.reactivex.functions.Consumer;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import java.util.Iterator;
import android.text.StaticLayout;
import android.text.Layout;
import android.text.TextPaint;
import androidx.core.view.ViewCompat;
import java.util.ArrayList;
import java.util.List;
import android.view.Menu;
import android.app.PendingIntent;
import android.content.Intent;
import android.widget.RemoteViews;
import android.content.Context;
import android.util.Size;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import android.graphics.Canvas;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.view.ViewParent;
import android.view.ViewGroup;
import android.graphics.RectF;
import android.graphics.Rect;
import io.reactivex.annotations.NonNull;
import android.graphics.Point;
import android.view.View;

public class ViewUtils
{
    public static int[] getLocationInWindow(final View view) {
        final int[] array = new int[2];
        view.getLocationInWindow(array);
        return array;
    }
    
    public static int[] getLocationOnScreen(final View view) {
        final int[] array = new int[2];
        view.getLocationOnScreen(array);
        return array;
    }
    
    public static void getLocationOnScreen(final View view, final Point point) {
        final int[] locationOnScreen = getLocationOnScreen(view);
        point.x = locationOnScreen[0];
        point.y = locationOnScreen[1];
    }
    
    public static Rect globalVisibleRect(@NonNull final View view) {
        final Rect rect = new android.graphics.Rect();
        final Rect rect2 = rect;
        new Rect();
        view.getGlobalVisibleRect(rect2);
        return rect;
    }
    
    public static Rect localVisibleRect(@NonNull final View view) {
        final Rect rect = new android.graphics.Rect();
        final Rect rect2 = rect;
        new Rect();
        view.getLocalVisibleRect(rect2);
        return rect;
    }
    
    public static RectF localVisibleRectF(@NonNull final View view) {
        final Rect source;
        view.getLocalVisibleRect(source = new Rect());
        return RectUtils.toRectF(source);
    }
    
    public static RectF globalVisibleRectF(@NonNull final View view) {
        return RectUtils.toRectF(globalVisibleRect(view));
    }
    
    public static Rect relativelyParentRect(@NonNull final View view) {
        return new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }
    
    public static void removeViewFromParent(final View view) {
        if (view == null) {
            return;
        }
        final ViewParent parent;
        if ((parent = view.getParent()) != null) {
            ((ViewGroup)parent).removeView(view);
        }
    }
    
    public static void safelyAddView(final ViewGroup parent, final View view) {
        if (view == null) {
            return;
        }
        if (parent == null) {
            return;
        }
        if (view.getParent() == parent) {
            return;
        }
        removeViewFromParent(view);
        parent.addView(view);
    }
    
    public static void safelyRemoveAllViews(final View view) {
        if (view == null) {
            return;
        }
        if (view instanceof ViewGroup) {
            ((ViewGroup)view).removeAllViews();
        }
    }
    
    public static void updateRelativeLayoutViewPosition(final View view, final int posX, final int poxY) {
        if (!(view.getLayoutParams() instanceof RelativeLayout.LayoutParams)) {
            return;
        }
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)view.getLayoutParams();
        layoutParams.setMarginStart(posX);
        layoutParams.topMargin = poxY;
        view.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }
    
    public static void updateFrameLayoutViewPosition(final View view, final int posX, final int poxY) {
        final FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
        layoutParams.setMarginStart(posX);
        layoutParams.topMargin = poxY;
        view.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }
    
    public static void unlockCanvasAndPost(final SurfaceView view, final Canvas canvas) {
        if (view != null && canvas != null) {
            view.getHolder().unlockCanvasAndPost(canvas);
        }
    }
    
    public static boolean isPointInView(final View v, final float localX, final float localY, final float slop) {
        final float n;
        return localX >= (n = -slop) && localY >= n && localX < v.getWidth() + slop && localY < v.getHeight() + slop;
    }
    
    public static void relayoutView(final View view, int width, final int height) {
        view.measure(View.MeasureSpec.makeMeasureSpec(width, 1073741824), View.MeasureSpec.makeMeasureSpec(height, 1073741824));
        final int measuredWidth = view.getMeasuredWidth();
        width = view.getMeasuredHeight();
        view.layout(0, 0, measuredWidth, width);
    }
    
    public static void setViewVisibleOrGone(@Nullable final View view, final boolean show) {
        if (view == null) {
            return;
        }
        int visibility;
        if (show) {
            visibility = 0;
        }
        else {
            visibility = 8;
        }
        view.setVisibility(visibility);
    }
    
    public static void updateViewTitle(final View view, final String title) {
        if (view == null) {
            return;
        }
        try {
            final WindowManager.LayoutParams layoutParams;
            (layoutParams = (WindowManager.LayoutParams)view.getLayoutParams()).setTitle((CharSequence)title);
            ((WindowManager)view.getContext().getSystemService("window")).updateViewLayout(view, (ViewGroup.LayoutParams)layoutParams);
        }
        catch (final Exception ex) {
            Debug.e((Throwable)ex);
        }
    }
    
    public static boolean isVisible(final View view) {
        return view != null && view.getVisibility() == 0;
    }
    
    public static void setViewVisibleOrInvisible(@Nullable final View view, final boolean show) {
        if (view == null) {
            return;
        }
        int visibility;
        if (show) {
            visibility = 0;
        }
        else {
            visibility = 4;
        }
        view.setVisibility(visibility);
    }
    
    public static void insertText(final EditText editText, final String text) {
        int n;
        if ((n = editText.getSelectionStart()) < 0) {
            n = editText.length();
        }
        if (editText.getText() != null) {
            editText.getText().insert(n, (CharSequence)text);
        }
    }
    
    public static void toggleCheckBox(final CheckBox checkBox) {
        checkBox.setChecked(checkBox.isChecked() ^ true);
    }
    
    public static <T> T getTagSafely(final View view) {
        final Object tag;
        if ((tag = view.getTag()) == null) {
            return null;
        }
        return (T)tag;
    }
    
    public static void setFakeBoldText(final TextView textView, final String text) {
        setFakeBoldText(textView, text, true);
    }
    
    public static void setFakeBoldText(final TextView textView, final String text, final boolean bold) {
        textView.getPaint().setFakeBoldText(bold);
        textView.setText((CharSequence)text);
    }
    
    public static boolean isInvalidSize(final View view) {
        return view == null || view.getWidth() <= 0 || view.getHeight() <= 0;
    }
    
    public static void enableLongClick(final View view, final boolean enable) {
        Object onLongClickListener;
        if (enable) {
            onLongClickListener = null;
        }
        else {
            onLongClickListener = new View.OnLongClickListener() {
                public boolean onLongClick(final View v) {
                    return true;
                }
            };
        }
        view.setOnLongClickListener((View.OnLongClickListener)onLongClickListener);
        view.setLongClickable(enable);
        view.setHapticFeedbackEnabled(enable);
    }
    
    public static Size getMeasureSize(@NonNull final View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        return new Size(view.getMeasuredWidth(), view.getMeasuredHeight());
    }
    
    public static void setBroadcastPendingIntent(final Context context, final RemoteViews views, final int id, final int requestCode, final String action) {
        views.setOnClickPendingIntent(id, PendingIntent.getBroadcast(context, requestCode, BroadcastHelper.addFlagsForAndroidO(new Intent(action)), 134217728));
    }
    
    public static void setActivityPendingIntent(final Context context, final RemoteViews views, final int id, final int requestCode, final Intent intent) {
        views.setOnClickPendingIntent(id, PendingIntent.getActivity(context, requestCode, intent, 134217728));
    }
    
    public static List<String> getMenuTitleList(final Menu menu) {
        final ArrayList list = new ArrayList();
        for (int i = 0; i < menu.size(); ++i) {
            list.add(StringUtils.safelyGetStr(menu.getItem(i).getTitle()));
        }
        return list;
    }
    
    public static boolean isLayoutRTLDirection(final View view) {
        return ViewCompat.getLayoutDirection(view) == 1;
    }
    
    public static void setLayoutHeight(final View view, final int height) {
        final ViewGroup.LayoutParams layoutParams;
        (layoutParams = view.getLayoutParams()).height = height;
        view.setLayoutParams(layoutParams);
    }
    
    public static void setBackgroundResource(final RemoteViews remoteViews, final int viewId, final int resId) {
        remoteViews.setInt(viewId, "setBackgroundResource", resId);
    }
    
    public static void setViewVisibleOrInvisible(final RemoteViews remoteViews, final int viewId, final boolean show) {
        int n;
        if (show) {
            n = 0;
        }
        else {
            n = 4;
        }
        remoteViews.setViewVisibility(viewId, n);
    }
    
    public static void setText(final TextView textView, final CharSequence text) {
        if (textView == null) {
            return;
        }
        textView.setText(text);
    }
    
    public static void setTextGravity(final TextView textView, final int gravity) {
        if (textView == null) {
            return;
        }
        textView.setGravity(gravity);
    }
    
    public static void setTextViewString(final View view, final String text) {
        if (!(view instanceof TextView)) {
            return;
        }
        ((TextView)view).setText((CharSequence)text);
    }
    
    public static int measureTextWidth(final String text, final int textSizeInPx) {
        final TextPaint textPaint = new android.text.TextPaint();
        textPaint.setTextSize((float)textSizeInPx);
        return (int)textPaint.measureText(text);
    }
    
    public static int measureTextHeight(final String text, final int textSizeInPx, final int textViewWidth) {
        final TextPaint textPaint = new android.text.TextPaint();
        final TextPaint textPaint2 = textPaint;
        new TextPaint();
        textPaint.setTextSize((float)textSizeInPx);
        return new StaticLayout((CharSequence)text, textPaint2, textViewWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true).getHeight();
    }
    
    public static int getMaxWordTextViewWidh(final TextView textView, final List<String> stringList) {
        final TextPaint paint = textView.getPaint();
        float max = 0.0f;
        final Iterator<String> iterator = stringList.iterator();
        while (iterator.hasNext()) {
            max = Math.max(max, paint.measureText((String)iterator.next()));
        }
        return (int)max;
    }
    
    public static void recycleImage(final ImageView imageView) {
        final Drawable drawable;
        if ((drawable = imageView.getDrawable()) != null && drawable instanceof BitmapDrawable) {
            final Bitmap bitmap;
            if ((bitmap = ((BitmapDrawable)drawable).getBitmap()) != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            imageView.setImageDrawable((Drawable)null);
        }
    }
    
    public static int[] getFixLocationInWindow(final View view) {
        final int[] locationOnScreen;
        final int[] array = locationOnScreen = getLocationOnScreen(view);
        final int windowDefaultWidth = ResManager.getWindowDefaultWidth();
        final int windowDefaultHeight = ResManager.getWindowDefaultHeight();
        if (array[0] >= windowDefaultWidth) {
            final int[] array2 = locationOnScreen;
            array2[0] -= windowDefaultWidth;
        }
        if (locationOnScreen[1] >= windowDefaultHeight) {
            final int[] array3 = locationOnScreen;
            array3[1] -= windowDefaultHeight;
        }
        return locationOnScreen;
    }
    
    public static int[] getLocationInActivity(final View view) {
        final int[] array2;
        final int[] array = array2 = new int[2];
        final int[] locationOnScreen = getLocationOnScreen(view);
        final int[] array3 = array2;
        final int[] locationOnScreen2 = getLocationOnScreen(ActivityUtil.getActivitySafety(view.getContext()).getWindow().getDecorView());
        array3[0] = locationOnScreen[0] - locationOnScreen2[0];
        array[1] = locationOnScreen[1] - locationOnScreen2[1];
        return array;
    }
    
    public static int getStart(final View v, final int parentWidth) {
        if (v == null) {
            return 0;
        }
        if (LocaleUtils.isCurrentLayoutDirectionRTL()) {
            return parentWidth - v.getRight();
        }
        return v.getLeft();
    }
    
    public static int getEnd(final View v, final int parentWidth) {
        if (v == null) {
            return 0;
        }
        if (LocaleUtils.isCurrentLayoutDirectionRTL()) {
            return parentWidth - v.getLeft();
        }
        return v.getRight();
    }
    
    public static void forEachChildView(final ViewGroup parent, final Consumer<View> childOnNext) {
        for (int i = 0; i < parent.getChildCount(); ++i) {
            RxUtils.acceptItemSafety(childOnNext, parent.getChildAt(i));
        }
    }
    
    public static <T extends View> T findViewById(final ViewDataBinding binding, final int viewId) {
        if (binding == null) {
            return null;
        }
        return (T)binding.getRoot().findViewById(viewId);
    }
}
