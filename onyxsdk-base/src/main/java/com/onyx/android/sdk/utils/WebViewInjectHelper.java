// 
// 

package com.onyx.android.sdk.utils;

import android.webkit.ValueCallback;
import io.reactivex.Observable;
import java.util.Arrays;
import org.json.JSONObject;
import android.webkit.WebView;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import io.reactivex.ObservableEmitter;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004J\u000e\u0010\r\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000e" }, d2 = { "Lcom/onyx/android/sdk/utils/WebViewInjectHelper;", "", "()V", "FOOTER_ID", "", "INJECT_FOOTER", "REMOVE_FOOTER", "injectFooterThenObservable", "Lio/reactivex/Observable;", "", "webView", "Landroid/webkit/WebView;", "content", "removeFooter", "onyxsdk-base_release" })
public final class WebViewInjectHelper
{
    @NotNull
    public static final WebViewInjectHelper INSTANCE;
    @NotNull
    private static final String a = "webview-injected-footer";
    @NotNull
    private static final String b = "\n            (function(){\n                var id='webview-injected-footer';\n                var e=document.getElementById(id);\n                if(e) e.remove();\n                var d=document.createElement('div');\n                d.id=id;\n                d.style.cssText='margin-top:24px;padding-top:16px;border-top:1px solid #cccccc;font-size:12px;color:#666666;line-height:1.5;page-break-inside:avoid;text-align:center;';\n                d.textContent=%s;\n                if(document.body) document.body.appendChild(d);\n            })();\n        ";
    @NotNull
    private static final String c = "\n            (function(){\n                var e=document.getElementById('webview-injected-footer');\n                if(e) e.remove();\n            })();\n        ";
    
    private WebViewInjectHelper() {
    }
    
    private static final void a(final ObservableEmitter $emitter, final String $noName_0) {
        Intrinsics.checkNotNullParameter((Object)$emitter, "$emitter");
        if (!$emitter.isDisposed()) {
            $emitter.onNext((Object)Unit.INSTANCE);
            $emitter.onComplete();
        }
    }
    
    private static final void a(String $content, final WebView $webView, final ObservableEmitter emitter) {
        Intrinsics.checkNotNullParameter((Object)$content, "$content");
        Intrinsics.checkNotNullParameter((Object)$webView, "$webView");
        Intrinsics.checkNotNullParameter((Object)emitter, "emitter");
        if (emitter.isDisposed()) {
            return;
        }
        $content = JSONObject.quote($content);
        Intrinsics.checkNotNullExpressionValue((Object)($content = String.format("\n            (function(){\n                var id='webview-injected-footer';\n                var e=document.getElementById(id);\n                if(e) e.remove();\n                var d=document.createElement('div');\n                d.id=id;\n                d.style.cssText='margin-top:24px;padding-top:16px;border-top:1px solid #cccccc;font-size:12px;color:#666666;line-height:1.5;page-break-inside:avoid;text-align:center;';\n                d.textContent=%s;\n                if(document.body) document.body.appendChild(d);\n            })();\n        ", Arrays.copyOf(new Object[] { $content }, 1))), "format(this, *args)");
        $webView.evaluateJavascript($content, value -> a(emitter, value));
    }
    
    static {
        INSTANCE = new WebViewInjectHelper();
    }
    
    @NotNull
    public final Observable<Unit> injectFooterThenObservable(@NotNull final WebView webView, @NotNull final String content) {
        Intrinsics.checkNotNullParameter((Object)webView, "webView");
        Intrinsics.checkNotNullParameter((Object)content, "content");
        final Observable<Unit> create = Observable.create(emitter -> a(content, webView, emitter));
        Intrinsics.checkNotNullExpressionValue((Object)create, "create { emitter ->\n    \u2026}\n            }\n        }");
        return create;
    }
    
    public final void removeFooter(@NotNull final WebView webView) {
        Intrinsics.checkNotNullParameter((Object)webView, "webView");
        webView.evaluateJavascript("\n            (function(){\n                var e=document.getElementById('webview-injected-footer');\n                if(e) e.remove();\n            })();\n        ", (ValueCallback)null);
    }
}
