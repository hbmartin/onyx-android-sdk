package com.onyx.android.sdk.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.annotation.Nullable;
import com.onyx.android.sdk.rx.RxUtils;
import io.reactivex.functions.Consumer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class WebViewUtils {
    private static final String a = "WebViewUtils";
    public static final String GET_HTML_ELEMENT_BY_TAG_NAME = "'<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'";

    public static void initWebViewConfig(Context context) {
        a(context);
    }

    @TargetApi(28)
    private static void a(Context context) {
        String strValueOf;
        if (Build.VERSION.SDK_INT >= 28) {
            String currentProcessName = ApplicationUtil.getCurrentProcessName(context);
            if (StringUtils.isNotBlank(currentProcessName)) {
                strValueOf = FileUtils.computeMD5(currentProcessName);
            } else {
                strValueOf = String.valueOf(System.currentTimeMillis());
                Debug.e("process name load fail!");
            }
            WebView.setDataDirectorySuffix(strValueOf);
            Debug.d("setDataDirectorySuffix = " + strValueOf);
        }
    }

    public static void hookWebView() {
        if (CompatibilityUtil.apiLevelCheck(23)) {
            a();
        }
    }

    public static void safelyDestroy(WebView webView) {
        if (webView == null) {
            return;
        }
        ViewParent parent = webView.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(webView);
        }
        webView.destroy();
    }

    public static Bitmap drawWebViewThumbnail(WebView webView, Bitmap thumbnail) {
        Canvas canvas = new Canvas(thumbnail);
        int scrollX = webView.getScrollX();
        int scrollY = webView.getScrollY();
        webView.scrollTo(0, 0);
        webView.draw(canvas);
        webView.scrollTo(scrollX, scrollY);
        return thumbnail;
    }

    public static Bitmap drawWebViewBitmap(WebView webView, Bitmap bitmap) {
        int width = webView.getWidth();
        int height = webView.getHeight();
        webView.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        webView.layout(0, 0, webView.getMeasuredWidth(), webView.getMeasuredHeight());
        int measuredHeight = webView.getMeasuredHeight();
        Canvas canvas = new Canvas(bitmap);
        float fMin = Math.min(bitmap.getHeight() / webView.getMeasuredHeight(), bitmap.getWidth() / webView.getMeasuredWidth());
        Matrix matrix = new Matrix();
        matrix.setScale(fMin, fMin);
        canvas.setMatrix(matrix);
        while (measuredHeight > 0) {
            measuredHeight = measuredHeight < height ? 0 : measuredHeight - height;
            canvas.save();
            canvas.clipRect(0, measuredHeight, (width - webView.getPaddingRight()) - 1, measuredHeight + height);
            webView.scrollTo(0, measuredHeight);
            webView.draw(canvas);
            canvas.restore();
        }
        return bitmap;
    }

    public static void loadJavascript(WebView webView, String interfaceName, String methodName, Object... interfaceParams) {
        StringBuilder sb = new StringBuilder("javascript:window." + interfaceName + FileUtils.FILE_EXTENSION_CHAR + methodName + "(");
        if (!ArraysUtils.isNullOrEmpty(interfaceParams)) {
            sb.append(interfaceParams[0]);
            for (int i = 1; i < interfaceParams.length; i++) {
                sb.append(LogUtils.ATTRIBUTE_SEPARATOR).append(interfaceParams[i]);
            }
        }
        sb.append(");");
        String string = sb.toString();
        Debug.d(a, "loadJs:" + string, new Object[0]);
        webView.loadUrl(string);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public static void initDefaultWebViewSetting(WebView webView) {
        WebView.setWebContentsDebuggingEnabled(Debug.getDebug());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setAllowFileAccess(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setDomStorageEnabled(true);
        try {
            WebSettings.class.getMethod("setAppCacheEnabled", boolean.class).invoke(settings, true);
        } catch (ReflectiveOperationException ignored) {
            // Removed from newer Android SDKs; DOM storage remains enabled above.
        }
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setSaveFormData(true);
        settings.setUseWideViewPort(true);
    }

    public static void printToPDF(WebView webView, String documentName, @Nullable Consumer<PrintAttributes> attributesConsumer) {
        printToPDF(webView.getContext(), webView, documentName, attributesConsumer);
    }

    public static void printToPDF(Context activityContext, WebView webView, String documentName, @Nullable Consumer<PrintAttributes> attributesConsumer) {
        PrintManager printManager = (PrintManager) activityContext.getSystemService("print");
        if (printManager == null) {
            Debug.i(a, "get PRINT_SERVICE failed", new Object[0]);
            return;
        }
        PrintAttributes printAttributesBuild = new PrintAttributes.Builder().setMediaSize(PrintAttributes.MediaSize.ISO_A4).setColorMode(2).setMinMargins(PrintAttributes.Margins.NO_MARGINS).build();
        RxUtils.acceptItemSafety(attributesConsumer, printAttributesBuild);
        if (StringUtils.isNullOrEmpty(documentName)) {
            documentName = "Document";
        }
        printManager.print(documentName, webView.createPrintDocumentAdapter(documentName), printAttributesBuild);
    }

    private static void a() {
        try {
            Class<?> cls = Class.forName("android.webkit.WebViewFactory");
            Field declaredField = cls.getDeclaredField("sProviderInstance");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(null);
            Object objInvoke = obj;
            if (obj != null) {
                return;
            }
            Method declaredMethod = cls.getDeclaredMethod("getProviderClass", new Class[0]);
            declaredMethod.setAccessible(true);
            Class cls2 = (Class) declaredMethod.invoke(cls, new Object[0]);
            Class<?> cls3 = Class.forName("android.webkit.WebViewDelegate");
            Constructor<?> declaredConstructor = cls3.getDeclaredConstructor(new Class[0]);
            declaredConstructor.setAccessible(true);
            Field declaredField2 = cls.getDeclaredField("CHROMIUM_WEBVIEW_FACTORY_METHOD");
            declaredField2.setAccessible(true);
            String str = (String) declaredField2.get(null);
            String str2 = str;
            if (str == null) {
                str2 = "create";
            }
            Method method = cls2.getMethod(str2, cls3);
            if (method != null) {
                Object[] objArr = new Object[1];
                objArr[0] = declaredConstructor.newInstance(new Object[0]);
                objInvoke = method.invoke(null, objArr);
            }
            if (objInvoke != null) {
                declaredField.set("sProviderInstance", objInvoke);
                Log.i(a, "Hook success!");
            } else {
                Log.i(a, "Hook failed!");
            }
        } catch (Throwable th) {
            Log.w(a, th);
        }
    }
}
