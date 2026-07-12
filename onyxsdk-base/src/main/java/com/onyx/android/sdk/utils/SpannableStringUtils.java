package com.onyx.android.sdk.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.onyx.android.sdk.data.span.FixVerticalImageSpan;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/SpannableStringUtils.class */
public class SpannableStringUtils {

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/SpannableStringUtils$a.class */
    static class a extends ClickableImageSpan {
        final /* synthetic */ ImageSpaceClickListener b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(Drawable b, ImageSpaceClickListener imageSpaceClickListener) {
            super(b);
            this.b = imageSpaceClickListener;
        }

        @Override // com.onyx.android.sdk.utils.ClickableImageSpan
        public void onClick(View view) {
            this.b.onClick();
        }
    }

    public static SpannableString boldText(String originText, String boldText) {
        int iIndexOf = originText.toLowerCase().indexOf(boldText.toLowerCase());
        return styleText(originText, iIndexOf, iIndexOf >= 0 ? iIndexOf + boldText.length() : iIndexOf, 1);
    }

    public static SpannableString styleText(String text, int start, int end, int style) {
        SpannableString spannableString = new SpannableString(text);
        if (start >= 0 && end > start) {
            spannableString.setSpan(new StyleSpan(style), start, end, 33);
        }
        return spannableString;
    }

    public static SpannableString setTextWithImage(String text, Drawable drawable, int imageWidth, int imageHeight, int drawableIndex, boolean fixVertical, ImageSpaceClickListener imageSpaceClickListener) {
        if (text == null || text.length() < drawableIndex) {
            return null;
        }
        String str = text.substring(0, drawableIndex) + "␣" + text.substring(drawableIndex);
        String str2 = str;
        if (StringUtils.isBlank(str)) {
            str2 = str2 + "␣";
        }
        SpannableString spannableString = new SpannableString(str2);
        drawable.setBounds(0, 0, imageWidth, imageHeight);
        if (imageSpaceClickListener == null) {
            spannableString.setSpan(new FixVerticalImageSpan(drawable).setFixVertical(fixVertical), drawableIndex, drawableIndex + 1, 1);
        } else {
            spannableString.setSpan(new a(drawable, imageSpaceClickListener).setFixVertical(fixVertical), drawableIndex, drawableIndex + 1, 1);
        }
        return spannableString;
    }

    public static SpannableString setClickableSpannableString(SpannableString spannableString, String clickableText, ClickableSpan span) {
        int iIndexOf = spannableString.toString().indexOf(clickableText);
        if (iIndexOf < 0) {
            Debug.w(SpannableStringUtils.class, clickableText + " can't index for spannableString:" + ((Object) spannableString), new Object[0]);
        }
        int iMax = Math.max(0, iIndexOf);
        spannableString.setSpan(span, iMax, iMax + clickableText.length(), 33);
        return spannableString;
    }

    public static String buildTextWithStartImage(TextView textView, int textViewWidth, String title, int imageWidth) {
        String strReplace = title.replace((char) 160, ' ');
        float fMeasureText = textView.getPaint().measureText(strReplace);
        float f = imageWidth;
        float f2 = fMeasureText + f;
        float f3 = textViewWidth;
        if (f2 < f3) {
            return strReplace;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 1; i <= strReplace.length(); i++) {
            float fMeasureText2 = textView.getPaint().measureText(strReplace.substring(arrayList.isEmpty() ? 0 : ((Integer) arrayList.get(arrayList.size() - 1)).intValue(), i));
            if (arrayList.isEmpty() && fMeasureText2 + f >= f3) {
                arrayList.add(Integer.valueOf(i));
            } else if (fMeasureText2 >= f3 && arrayList.size() < textView.getMaxLines() - 1) {
                arrayList.add(Integer.valueOf(i));
            }
            if (arrayList.size() == textView.getMaxLines()) {
                break;
            }
        }
        StringBuilder sb = new StringBuilder(strReplace);
        if (!arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                sb.insert(((Integer) it.next()).intValue() - 1, ShellUtils.COMMAND_LINE_END);
            }
        }
        return sb.toString();
    }

    public static SpannableString getTextWithImage(Context context, String text, String placeholder, int drawableResId, float textSize) {
        SpannableString spannableString = new SpannableString(text);
        int iIndexOf = text.indexOf(placeholder);
        if (iIndexOf == -1) {
            return spannableString;
        }
        int length = iIndexOf + placeholder.length();
        Drawable drawable = ContextCompat.getDrawable(context, drawableResId);
        if (drawable == null) {
            return spannableString;
        }
        int iApplyDimension = (int) TypedValue.applyDimension(0, textSize, context.getResources().getDisplayMetrics());
        drawable.setBounds(0, 0, (iApplyDimension * drawable.getIntrinsicWidth()) / drawable.getIntrinsicHeight(), iApplyDimension);
        spannableString.setSpan(new ImageSpan(drawable, 1), iIndexOf, length, 33);
        return spannableString;
    }

    public static SpannableString boldText(String prefixText, String suffixText, String boldText) {
        String str = prefixText + boldText + suffixText;
        int length = StringUtils.getLength(prefixText);
        return styleText(str, length, length + StringUtils.getLength(boldText), 1);
    }
}
