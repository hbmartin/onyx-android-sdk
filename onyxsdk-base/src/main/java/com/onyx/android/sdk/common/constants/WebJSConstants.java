// 
// 

package com.onyx.android.sdk.common.constants;

import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\n" }, d2 = { "Lcom/onyx/android/sdk/common/constants/WebJSConstants;", "", "()V", "ERROR_FOR_PAY", "", "REFRESH_MESSAGE_STATUS", "REFRESH_PROGRESS", "REFRESH_USER_SHIP", "SUCCESS_FOR_PAY", "WINDOW_ON_DATA", "onyxsdk-base_release" })
public final class WebJSConstants
{
    @NotNull
    public static final WebJSConstants INSTANCE;
    @NotNull
    public static final String REFRESH_MESSAGE_STATUS = "window.refreshMessageStatus";
    @NotNull
    public static final String REFRESH_PROGRESS = "window.refreshProgress";
    @NotNull
    public static final String REFRESH_USER_SHIP = "window.refreshUserShip";
    @NotNull
    public static final String ERROR_FOR_PAY = "window.errorForPay";
    @NotNull
    public static final String SUCCESS_FOR_PAY = "window.successForPay";
    @NotNull
    public static final String WINDOW_ON_DATA = "window.onData";
    
    private WebJSConstants() {
    }
    
    static {
        INSTANCE = new WebJSConstants();
    }
}
