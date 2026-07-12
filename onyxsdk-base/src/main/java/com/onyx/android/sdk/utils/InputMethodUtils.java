// 
// 

package com.onyx.android.sdk.utils;

import android.content.pm.ServiceInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.annotation.SuppressLint;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Rect;
import java.util.Iterator;
import android.provider.Settings;
import android.view.inputmethod.InputMethodInfo;
import android.app.Activity;
import android.view.inputmethod.InputMethodManager;
import android.view.View;
import android.content.Context;

public class InputMethodUtils
{
    private static final int a = 70;
    
    public static void showForcedInputKeyboard(final Context context, final View focusView) {
        if (focusView == null) {
            return;
        }
        ((InputMethodManager)context.getSystemService("input_method")).showSoftInput(focusView, 2);
    }
    
    public static void showForcedInputKeyboardForFragment(final Context context, final View focusView) {
        if (focusView.requestFocus()) {
            ((InputMethodManager)context.getSystemService("input_method")).toggleSoftInput(2, 1);
        }
    }
    
    public static void hideInputKeyboard(final Context context) {
        final View peekDecorView;
        if ((peekDecorView = ((Activity)context).getWindow().peekDecorView()) != null && peekDecorView.getWindowToken() != null) {
            ((InputMethodManager)context.getSystemService("input_method")).hideSoftInputFromWindow(peekDecorView.getWindowToken(), 0);
        }
    }
    
    public static void hideInputKeyboard(final View view) {
        if (view == null) {
            return;
        }
        final InputMethodManager inputMethodManager;
        if ((inputMethodManager = (InputMethodManager)view.getContext().getSystemService("input_method")) == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    
    public static void toggleStatus(final Context context) {
        ((InputMethodManager)context.getSystemService("input_method")).toggleSoftInput(0, 2);
    }
    
    public static boolean isShow(final Context context, final View focusView) {
        System.out.println(context.getSystemService("input_method"));
        final InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService("input_method");
        final boolean active = inputMethodManager.isActive(focusView);
        final Iterator iterator = inputMethodManager.getEnabledInputMethodList().iterator();
        while (iterator.hasNext() && !((InputMethodInfo)iterator.next()).getId().equals(Settings.Secure.getString(context.getContentResolver(), "default_input_method"))) {}
        return active;
    }
    
    public static boolean isShow(final View rootView) {
        final Rect rect;
        rootView.getWindowVisibleDisplayFrame(rect = new Rect());
        return rootView.getBottom() - rect.bottom > rootView.getResources().getDisplayMetrics().density * 70.0f;
    }
    
    public static void alwaysHideSoftInput(final Activity activity) {
        activity.getWindow().setSoftInputMode(3);
    }
    
    public static List<InputMethodInfo> getInstalledIMEList(final Context context) {
        List<InputMethodInfo> inputMethodList = new ArrayList<InputMethodInfo>();
        final InputMethodManager inputMethodManager;
        if ((inputMethodManager = (InputMethodManager)context.getSystemService("input_method")) != null) {
            inputMethodList = inputMethodManager.getInputMethodList();
        }
        else {
            Debug.e("Null InputMethodManager");
        }
        return inputMethodList;
    }
    
    @SuppressLint({ "NewApi" })
    public static List<String> getInstalledIMEPackageList(final Context context) {
        return CollectionUtils.ensureList(getInstalledIMEList(context)).stream().map(InputMethodInfo::getPackageName).collect(Collectors.toList());
    }
    
    public static boolean isHardKeyboardConnected(final Context context) {
        final Configuration configuration;
        return (configuration = context.getResources().getConfiguration()).keyboard == 2 && configuration.hardKeyboardHidden != 2;
    }
    
    public static boolean isInputMethodApp(Context context, final String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        boolean result = false;
        try {
            final ServiceInfo[] services;
            if ((services = packageManager.getPackageInfo(packageName, 516).services) != null) {
                Block_5: {
                    for (int length = services.length, i = 0; i < length; ++i) {
                        if (StringUtils.safelyEquals(services[i].permission, "android.permission.BIND_INPUT_METHOD")) {
                            break Block_5;
                        }
                    }
                    return result;
                }
                result = true;
            }
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
