package com.onyx.android.sdk.extension;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Editable;
import android.util.Size;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import com.onyx.android.sdk.data.KeyAction;
import com.onyx.android.sdk.data.ReaderTextStyle;
import com.onyx.android.sdk.data.im.Message;
import com.onyx.android.sdk.data.richtext.RichTextRun;
import com.onyx.android.sdk.utils.MultiWindowUtils;
import com.onyx.android.sdk.utils.ResManager;
import com.onyx.android.sdk.utils.TTFFont;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/extension/ViewKt.class */
@Metadata(mv = {1, 6, 0}, k = 2, xi = 48, d1 = {"\u0000°\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0004\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0005\u001a\n\u0010\b\u001a\u00020\t*\u00020\u0001\u001a\u0018\u0010\n\u001a\u0004\u0018\u00010\u000b*\u00020\u000b2\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\r\u001a\n\u0010\u000e\u001a\u00020\u000f*\u00020\u0001\u001a\n\u0010\u0010\u001a\u00020\u000f*\u00020\u0001\u001a\n\u0010\u0011\u001a\u00020\u000f*\u00020\u0001\u001a\n\u0010\u0012\u001a\u00020\u0013*\u00020\u0001\u001a\u0019\u0010\u0014\u001a\u0004\u0018\u0001H\u0015\"\u0004\b\u0000\u0010\u0015*\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0016\u001a\u001e\u0010\u0017\u001a\u00020\u0018*\u00020\u00012\b\b\u0002\u0010\u0019\u001a\u00020\u001a2\b\b\u0002\u0010\u001b\u001a\u00020\u001a\u001a\n\u0010\u001c\u001a\u00020\u001d*\u00020\u0001\u001a\n\u0010\u001e\u001a\u00020\u001f*\u00020\u0001\u001a\f\u0010 \u001a\u00020\t*\u0004\u0018\u00010\u0001\u001a\n\u0010!\u001a\u00020\u0005*\u00020\u0001\u001a\u0014\u0010\"\u001a\u00020\t*\u00020#2\b\u0010$\u001a\u0004\u0018\u00010%\u001a\n\u0010&\u001a\u00020\u001a*\u00020\u0001\u001a\f\u0010'\u001a\u00020\u001a*\u0004\u0018\u00010\u0001\u001a\"\u0010(\u001a\u00020\u001a*\u00020\u00012\u0006\u0010)\u001a\u00020\u00182\u0006\u0010*\u001a\u00020\u00182\u0006\u0010+\u001a\u00020\u0018\u001a\f\u0010,\u001a\u00020\u001a*\u0004\u0018\u00010\u0001\u001a\f\u0010-\u001a\u0004\u0018\u00010\u0001*\u00020.\u001a\u000e\u0010/\u001a\u0004\u0018\u000100*\u0004\u0018\u00010\u0001\u001a\u000e\u00101\u001a\u0004\u0018\u000100*\u0004\u0018\u00010\u0001\u001a\n\u00102\u001a\u00020\u001d*\u00020\u0001\u001a\n\u00103\u001a\u00020\u001f*\u00020\u0001\u001a\n\u00104\u001a\u00020\t*\u00020\u0001\u001a-\u00105\u001a\u00020\t\"\b\b\u0000\u0010\u0015*\u00020\u0001*\u0002H\u00152\u0012\u00106\u001a\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u00020\t07¢\u0006\u0002\u00108\u001a\u0010\u00109\u001a\b\u0012\u0004\u0012\u00020\u00010:*\u00020\u0001\u001a\u0014\u0010;\u001a\u00020\t*\u00020\u00012\b\b\u0002\u0010<\u001a\u00020=\u001a\n\u0010>\u001a\u00020\u001d*\u00020\u0001\u001a\f\u0010?\u001a\u00020\t*\u0004\u0018\u00010\u0001\u001a\f\u0010@\u001a\u00020\t*\u0004\u0018\u00010\u0001\u001a\n\u0010A\u001a\u00020\u001d*\u00020\u0001\u001a\u0016\u0010B\u001a\u00020\t*\u0004\u0018\u00010.2\b\u0010C\u001a\u0004\u0018\u00010\u0001\u001a\n\u0010D\u001a\u00020\u001d*\u00020\u0001\u001a\u0012\u0010E\u001a\u00020\t*\u00020\u00062\u0006\u0010F\u001a\u00020\u001a\u001a\u001e\u0010G\u001a\u00020\t*\u00020\u00012\u0010\b\u0002\u0010H\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010IH\u0007\u001a\u0016\u0010J\u001a\u00020\t*\u0004\u0018\u00010\u00062\b\u0010$\u001a\u0004\u0018\u00010%\u001a\u001e\u0010J\u001a\u00020\t*\u0004\u0018\u00010\u00062\b\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010F\u001a\u00020\u001a\u001a\u0012\u0010K\u001a\u00020\t*\u00020\u00012\u0006\u0010L\u001a\u00020\u0005\u001a#\u0010M\u001a\u00020\t*\u00020\u00012\u0017\u0010N\u001a\u0013\u0012\u0004\u0012\u00020O\u0012\u0004\u0012\u00020\t07¢\u0006\u0002\bP\u001a\u0012\u0010M\u001a\u00020\t*\u00020\u00012\u0006\u0010\u0007\u001a\u00020\u0005\u001a*\u0010M\u001a\u00020\t*\u00020\u00012\u0006\u0010Q\u001a\u00020\u00052\u0006\u0010R\u001a\u00020\u00052\u0006\u0010S\u001a\u00020\u00052\u0006\u0010T\u001a\u00020\u0005\u001a#\u0010U\u001a\u00020\t*\u00020\u00012\u0017\u0010N\u001a\u0013\u0012\u0004\u0012\u00020V\u0012\u0004\u0012\u00020\t07¢\u0006\u0002\bP\u001a\u001a\u0010U\u001a\u00020\t*\u00020\u00012\u0006\u0010W\u001a\u00020\u00052\u0006\u0010L\u001a\u00020\u0005\u001a\u0012\u0010X\u001a\u00020\t*\u00020\u00012\u0006\u0010W\u001a\u00020\u0005\u001a\u0012\u0010Y\u001a\u00020\t*\u00020\u00012\u0006\u0010Z\u001a\u00020\u0005\u001a\u0012\u0010[\u001a\u00020\t*\u00020\u00012\u0006\u0010\u0007\u001a\u00020\u0005\u001a*\u0010[\u001a\u00020\t*\u00020\u00012\u0006\u0010Q\u001a\u00020\u00052\u0006\u0010R\u001a\u00020\u00052\u0006\u0010S\u001a\u00020\u00052\u0006\u0010T\u001a\u00020\u0005\u001a\u0012\u0010\\\u001a\u00020\u0006*\u00020\u00062\u0006\u0010]\u001a\u00020\u0005\u001a\u0014\u0010^\u001a\u00020\t*\u0004\u0018\u00010\u00012\u0006\u0010_\u001a\u00020\u0005\u001a\u0016\u0010`\u001a\u00020\t*\u0004\u0018\u00010\u00012\b\u0010a\u001a\u0004\u0018\u00010%\u001a\u0014\u0010b\u001a\u00020\t*\u0004\u0018\u00010\u00012\u0006\u0010c\u001a\u00020\u001a\u001a\u0014\u0010d\u001a\u00020\t*\u0004\u0018\u00010\u00012\u0006\u0010c\u001a\u00020\u001a\u001a\f\u0010e\u001a\u00020\t*\u0004\u0018\u00010\u0001\u001a\n\u0010f\u001a\u00020\u001a*\u00020g\u001a\f\u0010h\u001a\u00020\t*\u0004\u0018\u00010\u0001\u001a\f\u0010i\u001a\u00020\t*\u0004\u0018\u00010\u0001\u001a\u0016\u0010j\u001a\u00020\u001a*\u0004\u0018\u00010\u00012\b\b\u0002\u0010k\u001a\u00020\u001a¨\u0006l"}, d2 = {"createEmptyView", "Landroid/view/View;", "context", "Landroid/content/Context;", "calculateFontHeight", TTFFont.UNKNOWN_FONT_NAME, "Landroid/widget/TextView;", "margin", "detachFromParent", TTFFont.UNKNOWN_FONT_NAME, "findParent", "Landroid/view/ViewParent;", "viewClass", "Ljava/lang/Class;", "getDrawingPosition", TTFFont.UNKNOWN_FONT_NAME, "getLocationInWindow", "getLocationOnScreen", "getMeasureSize", "Landroid/util/Size;", "getTagSafely", "T", "(Landroid/view/View;)Ljava/lang/Object;", "getTranslationX", TTFFont.UNKNOWN_FONT_NAME, "includeAncestors", TTFFont.UNKNOWN_FONT_NAME, "onlyNegativeValue", "globalVisibleRect", "Landroid/graphics/Rect;", "globalVisibleRectF", "Landroid/graphics/RectF;", "hideInputKeyboard", "indexInParent", "insertText", "Landroid/widget/EditText;", "str", TTFFont.UNKNOWN_FONT_NAME, "isInMultiWindow", "isInvalidSize", "isPointIn", "localX", "localY", "slop", "isVisible", "lastChild", "Landroid/view/ViewGroup;", "loadBitmap", "Landroid/graphics/Bitmap;", "loadBitmapFromDrawingCache", "localVisibleRect", "localVisibleRectF", "matchParent", "postSelf", KeyAction.KEY_ACTION_TAG, "Lkotlin/Function1;", "(Landroid/view/View;Lkotlin/jvm/functions/Function1;)V", "postSingle", "Lio/reactivex/Single;", "postToShowInputKeyboard", "delayMillis", TTFFont.UNKNOWN_FONT_NAME, "relativelyParentRect", "removeAllViews", "removeFromParent", "safeGlobalVisibleRect", "safelyAddView", "view", "screenVisibleRect", "setBold", RichTextRun.TextRunStyle.FONT_WEIGHT_BOLD, "setClearEditFocusTouchListener", "clearFocusListener", "Lkotlin/Function0;", "setFakeBoldText", "setLayoutHeight", "height", "setLayoutMargin", "block", "Landroid/view/ViewGroup$MarginLayoutParams;", "Lkotlin/ExtensionFunctionType;", "left", "top", "right", "bottom", "setLayoutParams", "Landroid/view/ViewGroup$LayoutParams;", "width", "setLayoutWidth", "setMarginBottom", "marginBottom", "setMargins", "setTextColorResource", "colorResId", "setTextGravity", "gravity", "setTextViewString", Message.TYPE_TEXT, "setViewVisibleOrGone", "show", "setViewVisibleOrInvisible", "showForcedInputKeyboard", "toggleCheckBox", "Landroid/widget/CompoundButton;", "toggleGone", "toggleInvisible", "toggleVisible", "gone", "onyxsdk-base_release"})
public final class ViewKt {
    private ViewKt() {
    }


    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/extension/ViewKt$a.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", TTFFont.UNKNOWN_FONT_NAME, "Landroid/view/ViewGroup$LayoutParams;", "invoke"})
    static final class a implements Function1<ViewGroup.LayoutParams, Unit> {
        final /* synthetic */ int a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(int $height) {
            this.a = $height;
        }

        public final Unit invoke(@NotNull ViewGroup.LayoutParams $this$setLayoutParams) {
            Intrinsics.checkNotNullParameter($this$setLayoutParams, "$this$setLayoutParams");
            $this$setLayoutParams.height = this.a;
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/extension/ViewKt$b.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", TTFFont.UNKNOWN_FONT_NAME, "Landroid/view/ViewGroup$MarginLayoutParams;", "invoke"})
    static final class b implements Function1<ViewGroup.MarginLayoutParams, Unit> {
        final /* synthetic */ View a;
        final /* synthetic */ int b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        b(View $receiver, int $margin) {
            this.a = $receiver;
            this.b = $margin;
        }

        public final Unit invoke(@NotNull ViewGroup.MarginLayoutParams $this$setLayoutMargin) {
            Intrinsics.checkNotNullParameter($this$setLayoutMargin, "$this$setLayoutMargin");
            ViewKt.setMargins(this.a, this.b);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/extension/ViewKt$c.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", TTFFont.UNKNOWN_FONT_NAME, "Landroid/view/ViewGroup$MarginLayoutParams;", "invoke"})
    static final class c implements Function1<ViewGroup.MarginLayoutParams, Unit> {
        final /* synthetic */ int a;
        final /* synthetic */ int b;
        final /* synthetic */ int c;
        final /* synthetic */ int d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        c(int $left, int $top, int $right, int $bottom) {
            this.a = $left;
            this.b = $top;
            this.c = $right;
            this.d = $bottom;
        }

        public final Unit invoke(@NotNull ViewGroup.MarginLayoutParams $this$setLayoutMargin) {
            Intrinsics.checkNotNullParameter($this$setLayoutMargin, "$this$setLayoutMargin");
            $this$setLayoutMargin.setMargins(this.a, this.b, this.c, this.d);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/extension/ViewKt$d.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", TTFFont.UNKNOWN_FONT_NAME, "Landroid/view/ViewGroup$LayoutParams;", "invoke"})
    static final class d implements Function1<ViewGroup.LayoutParams, Unit> {
        final /* synthetic */ Function1<? super ViewGroup.MarginLayoutParams, Unit> a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        d(Function1<? super ViewGroup.MarginLayoutParams, Unit> function1) {
            this.a = function1;
        }

        public final Unit invoke(@NotNull ViewGroup.LayoutParams $this$setLayoutParams) {
            Intrinsics.checkNotNullParameter($this$setLayoutParams, "$this$setLayoutParams");
            ViewGroup.MarginLayoutParams marginLayoutParams = $this$setLayoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) $this$setLayoutParams : null;
            if (marginLayoutParams == null) {
                return Unit.INSTANCE;
            }
            this.a.invoke(marginLayoutParams);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/extension/ViewKt$e.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", TTFFont.UNKNOWN_FONT_NAME, "Landroid/view/ViewGroup$LayoutParams;", "invoke"})
    static final class e implements Function1<ViewGroup.LayoutParams, Unit> {
        final /* synthetic */ int a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        e(int $width) {
            this.a = $width;
        }

        public final Unit invoke(@NotNull ViewGroup.LayoutParams $this$setLayoutParams) {
            Intrinsics.checkNotNullParameter($this$setLayoutParams, "$this$setLayoutParams");
            $this$setLayoutParams.width = this.a;
            return Unit.INSTANCE;
        }
    }

    @NotNull
    public static final int[] getLocationInWindow(@NotNull View $this$getLocationInWindow) {
        Intrinsics.checkNotNullParameter($this$getLocationInWindow, "<this>");
        int[] iArr = new int[2];
        $this$getLocationInWindow.getLocationInWindow(iArr);
        return iArr;
    }

    @NotNull
    public static final int[] getLocationOnScreen(@NotNull View $this$getLocationOnScreen) {
        Intrinsics.checkNotNullParameter($this$getLocationOnScreen, "<this>");
        int[] iArr = new int[2];
        $this$getLocationOnScreen.getLocationOnScreen(iArr);
        return iArr;
    }

    @NotNull
    public static final Rect screenVisibleRect(@NotNull View $this$screenVisibleRect) {
        Intrinsics.checkNotNullParameter($this$screenVisibleRect, "<this>");
        int[] locationOnScreen = getLocationOnScreen($this$screenVisibleRect);
        Rect rectLocalVisibleRect = localVisibleRect($this$screenVisibleRect);
        rectLocalVisibleRect.offset(locationOnScreen[0], locationOnScreen[1]);
        return rectLocalVisibleRect;
    }

    @NotNull
    public static final Rect globalVisibleRect(@NotNull View $this$globalVisibleRect) {
        Intrinsics.checkNotNullParameter($this$globalVisibleRect, "<this>");
        Rect rect = new Rect();
        $this$globalVisibleRect.getGlobalVisibleRect(rect);
        return rect;
    }

    @NotNull
    public static final Rect localVisibleRect(@NotNull View $this$localVisibleRect) {
        Intrinsics.checkNotNullParameter($this$localVisibleRect, "<this>");
        Rect rect = new Rect();
        $this$localVisibleRect.getLocalVisibleRect(rect);
        return rect;
    }

    @NotNull
    public static final RectF localVisibleRectF(@NotNull View $this$localVisibleRectF) {
        Intrinsics.checkNotNullParameter($this$localVisibleRectF, "<this>");
        Rect rect = new Rect();
        $this$localVisibleRectF.getLocalVisibleRect(rect);
        RectF rectF = RectKt.toRectF(rect);
        RectF rectF2 = rectF;
        if (rectF == null) {
            rectF2 = rectF;
            RectF rectF3 = new RectF();
        }
        return rectF2;
    }

    @NotNull
    public static final RectF globalVisibleRectF(@NotNull View $this$globalVisibleRectF) {
        Intrinsics.checkNotNullParameter($this$globalVisibleRectF, "<this>");
        RectF rectF = RectKt.toRectF(globalVisibleRect($this$globalVisibleRectF));
        RectF rectF2 = rectF;
        if (rectF == null) {
            rectF2 = rectF;
            RectF rectF3 = new RectF();
        }
        return rectF2;
    }

    @NotNull
    public static final Rect relativelyParentRect(@NotNull View $this$relativelyParentRect) {
        Intrinsics.checkNotNullParameter($this$relativelyParentRect, "<this>");
        return new Rect($this$relativelyParentRect.getLeft(), $this$relativelyParentRect.getTop(), $this$relativelyParentRect.getRight(), $this$relativelyParentRect.getBottom());
    }

    public static final void safelyAddView(@Nullable ViewGroup $this$safelyAddView, @Nullable View view) {
        if ($this$safelyAddView == null || view == null || view.getParent() == $this$safelyAddView) {
            return;
        }
        removeFromParent($this$safelyAddView);
        $this$safelyAddView.addView(view);
    }

    public static final void removeFromParent(@Nullable View $this$removeFromParent) {
        ViewParent parent = $this$removeFromParent == null ? null : $this$removeFromParent.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView($this$removeFromParent);
        }
    }

    public static final void removeAllViews(@Nullable View $this$removeAllViews) {
        if ($this$removeAllViews != null && ($this$removeAllViews instanceof ViewGroup)) {
            ((ViewGroup) $this$removeAllViews).removeAllViews();
        }
    }

    public static final void setViewVisibleOrGone(@Nullable View $this$setViewVisibleOrGone, boolean show) {
        if ($this$setViewVisibleOrGone == null) {
            return;
        }
        $this$setViewVisibleOrGone.setVisibility(show ? 0 : 8);
    }

    public static final void setViewVisibleOrInvisible(@Nullable View $this$setViewVisibleOrInvisible, boolean show) {
        if ($this$setViewVisibleOrInvisible == null) {
            return;
        }
        $this$setViewVisibleOrInvisible.setVisibility(show ? 0 : 4);
    }

    public static final boolean toggleVisible(@Nullable View $this$toggleVisible, boolean gone) {
        if (!isVisible($this$toggleVisible)) {
            setViewVisibleOrGone($this$toggleVisible, true);
            return true;
        }
        if (gone) {
            setViewVisibleOrGone($this$toggleVisible, false);
            return false;
        }
        setViewVisibleOrInvisible($this$toggleVisible, false);
        return false;
    }

    public static /* synthetic */ boolean toggleVisible$default(View view, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        return toggleVisible(view, z);
    }

    public static final boolean isVisible(@Nullable View $this$isVisible) {
        return $this$isVisible != null && $this$isVisible.getVisibility() == 0;
    }

    public static final void toggleGone(@Nullable View $this$toggleGone) {
        setViewVisibleOrGone($this$toggleGone, !isVisible($this$toggleGone));
    }

    public static final void toggleInvisible(@Nullable View $this$toggleInvisible) {
        setViewVisibleOrGone($this$toggleInvisible, !isVisible($this$toggleInvisible));
    }

    public static final boolean isPointIn(@NotNull View $this$isPointIn, float localX, float localY, float slop) {
        Intrinsics.checkNotNullParameter($this$isPointIn, "<this>");
        float f = -slop;
        return localX >= f && localY >= f && localX < ((float) $this$isPointIn.getWidth()) + slop && localY < ((float) $this$isPointIn.getHeight()) + slop;
    }

    public static final void insertText(@NotNull EditText $this$insertText, @Nullable String str) {
        Intrinsics.checkNotNullParameter($this$insertText, "<this>");
        int selectionStart = $this$insertText.getSelectionStart();
        int length = selectionStart;
        if (selectionStart < 0) {
            length = $this$insertText.length();
        }
        Editable text = $this$insertText.getText();
        if (text == null) {
            return;
        }
        if (str == null) {
            str = TTFFont.UNKNOWN_FONT_NAME;
        }
        text.insert(length, str);
    }

    public static final boolean toggleCheckBox(@NotNull CompoundButton $this$toggleCheckBox) {
        Intrinsics.checkNotNullParameter($this$toggleCheckBox, "<this>");
        $this$toggleCheckBox.setChecked(!$this$toggleCheckBox.isChecked());
        return $this$toggleCheckBox.isChecked();
    }

    @Nullable
    public static final <T> T getTagSafely(@Nullable View view) {
        Object tag = view == null ? null : view.getTag();
        if (tag == null) {
            return null;
        }
        return (T) tag;
    }

    public static final void setFakeBoldText(@Nullable TextView $this$setFakeBoldText, @Nullable String str) {
        setFakeBoldText($this$setFakeBoldText, str, true);
    }

    public static final void setBold(@NotNull TextView $this$setBold, boolean bold) {
        Intrinsics.checkNotNullParameter($this$setBold, "<this>");
        $this$setBold.getPaint().setFakeBoldText(bold);
    }

    @NotNull
    public static final Size getMeasureSize(@NotNull View $this$getMeasureSize) {
        Intrinsics.checkNotNullParameter($this$getMeasureSize, "<this>");
        $this$getMeasureSize.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        return new Size($this$getMeasureSize.getMeasuredWidth(), $this$getMeasureSize.getMeasuredHeight());
    }

    public static final void setLayoutHeight(@NotNull View $this$setLayoutHeight, int height) {
        Intrinsics.checkNotNullParameter($this$setLayoutHeight, "<this>");
        setLayoutParams($this$setLayoutHeight, new a(height));
    }

    public static final void setLayoutWidth(@NotNull View $this$setLayoutWidth, int width) {
        Intrinsics.checkNotNullParameter($this$setLayoutWidth, "<this>");
        setLayoutParams($this$setLayoutWidth, new e(width));
    }

    public static final void setLayoutMargin(@NotNull View $this$setLayoutMargin, int margin) {
        Intrinsics.checkNotNullParameter($this$setLayoutMargin, "<this>");
        setLayoutMargin($this$setLayoutMargin, new b($this$setLayoutMargin, margin));
    }

    public static final void setLayoutParams(@NotNull View $this$setLayoutParams, @NotNull Function1<? super ViewGroup.LayoutParams, Unit> function1) {
        Intrinsics.checkNotNullParameter($this$setLayoutParams, "<this>");
        Intrinsics.checkNotNullParameter(function1, "block");
        ViewGroup.LayoutParams layoutParams = $this$setLayoutParams.getLayoutParams();
        ViewGroup.LayoutParams layoutParams2 = layoutParams;
        if (layoutParams == null) {
            layoutParams2 = layoutParams;
            ViewGroup.LayoutParams layoutParams3 = new ViewGroup.LayoutParams(-2, -2);
        }
        function1.invoke(layoutParams2);
        $this$setLayoutParams.setLayoutParams(layoutParams2);
    }

    public static final void setTextGravity(@Nullable View $this$setTextGravity, int gravity) {
        if ($this$setTextGravity instanceof TextView) {
            ((TextView) $this$setTextGravity).setGravity(gravity);
        }
    }

    public static final void setTextViewString(@Nullable View $this$setTextViewString, @Nullable String text) {
        if ($this$setTextViewString instanceof TextView) {
            ((TextView) $this$setTextViewString).setText(text);
        }
    }

    public static final int calculateFontHeight(@NotNull TextView $this$calculateFontHeight, int margin) {
        Intrinsics.checkNotNullParameter($this$calculateFontHeight, "<this>");
        return (int) Math.ceil(($this$calculateFontHeight.getPaint().getFontMetrics().bottom - $this$calculateFontHeight.getPaint().getFontMetrics().top) - (margin * 2));
    }

    public static final boolean isInvalidSize(@Nullable View $this$isInvalidSize) {
        return $this$isInvalidSize == null || $this$isInvalidSize.getWidth() <= 0 || $this$isInvalidSize.getHeight() <= 0;
    }

    @Nullable
    public static final Bitmap loadBitmap(@Nullable View $this$loadBitmap) {
        if ($this$loadBitmap == null) {
            return null;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap($this$loadBitmap.getWidth(), $this$loadBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        $this$loadBitmap.draw(new Canvas(bitmapCreateBitmap));
        return bitmapCreateBitmap;
    }

    @Nullable
    public static final Bitmap loadBitmapFromDrawingCache(@Nullable View $this$loadBitmapFromDrawingCache) {
        if ($this$loadBitmapFromDrawingCache == null) {
            return null;
        }
        $this$loadBitmapFromDrawingCache.setDrawingCacheEnabled(true);
        $this$loadBitmapFromDrawingCache.destroyDrawingCache();
        return $this$loadBitmapFromDrawingCache.getDrawingCache(true);
    }

    public static final void hideInputKeyboard(@Nullable View $this$hideInputKeyboard) {
        InputMethodManager inputMethodManager;
        if ($this$hideInputKeyboard == null || (inputMethodManager = (InputMethodManager) $this$hideInputKeyboard.getContext().getSystemService("input_method")) == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow($this$hideInputKeyboard.getWindowToken(), 0);
    }

    public static final void showForcedInputKeyboard(@Nullable View $this$showForcedInputKeyboard) {
        if ($this$showForcedInputKeyboard == null) {
            return;
        }
        Object systemService = $this$showForcedInputKeyboard.getContext().getSystemService("input_method");
        if (systemService == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
        }
        ((InputMethodManager) systemService).showSoftInput($this$showForcedInputKeyboard, 2);
    }

    public static final void postToShowInputKeyboard(@NotNull View $this$postToShowInputKeyboard, long delayMillis) {
        Intrinsics.checkNotNullParameter($this$postToShowInputKeyboard, "<this>");
        $this$postToShowInputKeyboard.postDelayed(() -> {
            a($this$postToShowInputKeyboard);
        }, delayMillis);
    }

    public static /* synthetic */ void postToShowInputKeyboard$default(View view, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            j = 0;
        }
        postToShowInputKeyboard(view, j);
    }

    @Nullable
    public static final ViewParent findParent(@NotNull ViewParent $this$findParent, @NotNull Class<?> cls) {
        Intrinsics.checkNotNullParameter($this$findParent, "<this>");
        Intrinsics.checkNotNullParameter(cls, "viewClass");
        ViewParent parent = $this$findParent.getParent();
        if (parent == null) {
            return null;
        }
        return cls.isInstance(parent) ? parent : findParent(parent, cls);
    }

    @Nullable
    public static final View lastChild(@NotNull ViewGroup $this$lastChild) {
        Intrinsics.checkNotNullParameter($this$lastChild, "<this>");
        return $this$lastChild.getChildAt($this$lastChild.getChildCount() - 1);
    }

    @NotNull
    public static final int[] getDrawingPosition(@NotNull View $this$getDrawingPosition) {
        Intrinsics.checkNotNullParameter($this$getDrawingPosition, "<this>");
        int[] iArr = new int[2];
        $this$getDrawingPosition.getRootView().getLocationOnScreen(iArr);
        int[] iArr2 = new int[2];
        $this$getDrawingPosition.getLocationOnScreen(iArr2);
        return new int[]{iArr2[0] - iArr[0], iArr2[1] - iArr[1]};
    }

    public static final <T extends View> void postSelf(@NotNull T t, @NotNull Function1<? super T, Unit> function1) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(function1, KeyAction.KEY_ACTION_TAG);
        t.post(() -> {
            a(function1, t);
        });
    }

    public static final void detachFromParent(@NotNull View $this$detachFromParent) {
        Intrinsics.checkNotNullParameter($this$detachFromParent, "<this>");
        if ($this$detachFromParent.getParent() == null) {
            return;
        }
        ViewParent parent = $this$detachFromParent.getParent();
        if (parent == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.view.ViewGroup");
        }
        ((ViewGroup) parent).removeView($this$detachFromParent);
    }

    public static final int indexInParent(@NotNull View $this$indexInParent) {
        Intrinsics.checkNotNullParameter($this$indexInParent, "<this>");
        if ($this$indexInParent.getParent() == null) {
            return -1;
        }
        ViewParent parent = $this$indexInParent.getParent();
        if (parent != null) {
            return ((ViewGroup) parent).indexOfChild($this$indexInParent);
        }
        throw new NullPointerException("null cannot be cast to non-null type android.view.ViewGroup");
    }

    public static final void matchParent(@NotNull View $this$matchParent) {
        Intrinsics.checkNotNullParameter($this$matchParent, "<this>");
        setLayoutParams($this$matchParent, -1, -1);
    }

    @NotNull
    public static final View createEmptyView(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        View view = new View(context);
        matchParent(view);
        view.setBackgroundColor(-16777216);
        return view;
    }

    public static final float getTranslationX(@NotNull View $this$getTranslationX, boolean includeAncestors, boolean onlyNegativeValue) {
        Intrinsics.checkNotNullParameter($this$getTranslationX, "<this>");
        float translationX = $this$getTranslationX.getTranslationX();
        if (onlyNegativeValue && translationX > ReaderTextStyle.FONT_EMBOLDEN_NORMAL) {
            translationX = 0.0f;
        }
        if (!includeAncestors) {
            return translationX;
        }
        Object parent = $this$getTranslationX.getParent();
        View view = parent instanceof View ? (View) parent : null;
        return view == null ? translationX : translationX + getTranslationX(view, true, onlyNegativeValue);
    }

    public static /* synthetic */ float getTranslationX$default(View view, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        if ((i & 2) != 0) {
            z2 = false;
        }
        return getTranslationX(view, z, z2);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public static final void setClearEditFocusTouchListener(@NotNull View $this$setClearEditFocusTouchListener, @Nullable Function0<Unit> function0) {
        Intrinsics.checkNotNullParameter($this$setClearEditFocusTouchListener, "<this>");
        $this$setClearEditFocusTouchListener.setOnTouchListener((v1, v2) -> {
            return a(function0, v1, v2);
        });
    }

    public static /* synthetic */ void setClearEditFocusTouchListener$default(View view, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            function0 = null;
        }
        setClearEditFocusTouchListener(view, function0);
    }

    @NotNull
    public static final TextView setTextColorResource(@NotNull TextView $this$setTextColorResource, int colorResId) {
        Intrinsics.checkNotNullParameter($this$setTextColorResource, "<this>");
        $this$setTextColorResource.setTextColor(ResManager.getColor(colorResId));
        return $this$setTextColorResource;
    }

    public static final void setMargins(@NotNull View $this$setMargins, int margin) {
        Intrinsics.checkNotNullParameter($this$setMargins, "<this>");
        setMargins($this$setMargins, margin, margin, margin, margin);
    }

    public static final void setMarginBottom(@NotNull View $this$setMarginBottom, int marginBottom) {
        Intrinsics.checkNotNullParameter($this$setMarginBottom, "<this>");
        ViewGroup.LayoutParams layoutParams = $this$setMarginBottom.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
        if (marginLayoutParams == null) {
            return;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams2 = marginLayoutParams;
        marginLayoutParams2.setMargins(marginLayoutParams2.leftMargin, marginLayoutParams2.topMargin, marginLayoutParams2.rightMargin, marginBottom);
    }

    public static final boolean isInMultiWindow(@NotNull View $this$isInMultiWindow) {
        Intrinsics.checkNotNullParameter($this$isInMultiWindow, "<this>");
        Context context = $this$isInMultiWindow.getContext();
        Context context2 = context;
        if (context != null) {
            if (!(context2 instanceof Activity)) {
                context2 = null;
            }
            if (context2 != null) {
                return MultiWindowUtils.isInMultiWindowMode((Activity) context2);
            }
        }
        return false;
    }

    @NotNull
    public static final Single<View> postSingle(@NotNull View $this$postSingle) {
        Intrinsics.checkNotNullParameter($this$postSingle, "<this>");
        Single<View> singleObserveOn = Single.<View>create((v1) -> {
            a($this$postSingle, v1);
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread());
        Intrinsics.checkNotNullExpressionValue(singleObserveOn, "create<View> {\n        i…dSchedulers.mainThread())");
        return singleObserveOn;
    }

    @NotNull
    public static final Rect safeGlobalVisibleRect(@NotNull View $this$safeGlobalVisibleRect) {
        Intrinsics.checkNotNullParameter($this$safeGlobalVisibleRect, "<this>");
        Rect rect = new Rect();
        if ($this$safeGlobalVisibleRect.getWindowToken() == null || $this$safeGlobalVisibleRect.getParent() == null) {
            return rect;
        }
        $this$safeGlobalVisibleRect.getGlobalVisibleRect(rect);
        return rect;
    }

    private static final void a(View $this_postToShowInputKeyboard) {
        Intrinsics.checkNotNullParameter($this_postToShowInputKeyboard, "$this_postToShowInputKeyboard");
        $this_postToShowInputKeyboard.requestFocus();
        showForcedInputKeyboard($this_postToShowInputKeyboard);
    }

    public static final void setFakeBoldText(@Nullable TextView $this$setFakeBoldText, @Nullable String str, boolean bold) {
        if ($this$setFakeBoldText == null) {
            return;
        }
        $this$setFakeBoldText.getPaint().setFakeBoldText(bold);
        $this$setFakeBoldText.setText(str);
    }

    public static final void setLayoutMargin(@NotNull View $this$setLayoutMargin, int left, int top, int right, int bottom) {
        Intrinsics.checkNotNullParameter($this$setLayoutMargin, "<this>");
        setLayoutMargin($this$setLayoutMargin, new c(left, top, right, bottom));
    }

    public static final void setMargins(@NotNull View $this$setMargins, int left, int top, int right, int bottom) {
        Intrinsics.checkNotNullParameter($this$setMargins, "<this>");
        ViewGroup.LayoutParams layoutParams = $this$setMargins.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
        if (marginLayoutParams == null) {
            return;
        }
        marginLayoutParams.setMargins(left, top, right, bottom);
    }

    public static final void setLayoutMargin(@NotNull View $this$setLayoutMargin, @NotNull Function1<? super ViewGroup.MarginLayoutParams, Unit> function1) {
        Intrinsics.checkNotNullParameter($this$setLayoutMargin, "<this>");
        Intrinsics.checkNotNullParameter(function1, "block");
        setLayoutParams($this$setLayoutMargin, new d(function1));
    }

    private static final void a(Function1 $action, View $this_postSelf) {
        Intrinsics.checkNotNullParameter($action, "$action");
        Intrinsics.checkNotNullParameter($this_postSelf, "$this_postSelf");
        $action.invoke($this_postSelf);
    }

    public static final void setLayoutParams(@NotNull View $this$setLayoutParams, int width, int height) {
        Intrinsics.checkNotNullParameter($this$setLayoutParams, "<this>");
        if ($this$setLayoutParams.getLayoutParams() == null) {
            $this$setLayoutParams.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        } else {
            $this$setLayoutParams.getLayoutParams().width = width;
            $this$setLayoutParams.getLayoutParams().height = height;
        }
    }

    private static final boolean a(Function0 $clearFocusListener, View v, MotionEvent event) {
        int action = event.getAction();
        if (action != 1 && action != 3) {
            return false;
        }
        View viewFindFocus = v.findFocus();
        if (!(viewFindFocus instanceof EditText) || globalVisibleRect(viewFindFocus).contains((int) event.getRawX(), (int) event.getRawY())) {
            return false;
        }
        if ($clearFocusListener != null) {
            $clearFocusListener.invoke();
        }
        viewFindFocus.clearFocus();
        hideInputKeyboard(viewFindFocus);
        return false;
    }

    private static final void a(View $this_postSingle, SingleEmitter it) {
        Intrinsics.checkNotNullParameter($this_postSingle, "$this_postSingle");
        Intrinsics.checkNotNullParameter(it, "it");
        if ($this_postSingle.getWindowToken() != null && $this_postSingle.getParent() != null) {
            Runnable runnable = () -> {
                a(it, $this_postSingle);
            };
            $this_postSingle.post(runnable);
            it.setCancellable(() -> {
                a($this_postSingle, runnable);
            });
            return;
        }
        it.onSuccess($this_postSingle);
    }

    private static final void a(SingleEmitter $it, View $this_postSingle) {
        Intrinsics.checkNotNullParameter($it, "$it");
        Intrinsics.checkNotNullParameter($this_postSingle, "$this_postSingle");
        if ($it.isDisposed()) {
            return;
        }
        if ($this_postSingle.getWindowToken() != null && $this_postSingle.getParent() != null) {
            $it.onSuccess($this_postSingle);
        } else {
            $it.onSuccess($this_postSingle);
        }
    }

    private static final void a(View $this_postSingle, Runnable $layoutRunnable) {
        Intrinsics.checkNotNullParameter($this_postSingle, "$this_postSingle");
        Intrinsics.checkNotNullParameter($layoutRunnable, "$layoutRunnable");
        $this_postSingle.removeCallbacks($layoutRunnable);
    }
}
