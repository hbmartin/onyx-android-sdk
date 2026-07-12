// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import java.util.ArrayList;
import java.util.Optional;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import android.graphics.Color;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import java.io.InputStream;
import android.annotation.TargetApi;
import android.graphics.Point;
import android.view.WindowManager;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.graphics.Rect;
import java.util.Iterator;
import java.lang.ref.WeakReference;
import java.util.List;
import android.content.Context;

public class ResManager
{
    private static Context a;
    private static final List<WeakReference<Context>> b;
    
    public static void init(final Context context) {
        ResManager.a = context;
        com.onyx.android.sdk.base.utils.ResManager.INSTANCE.init(context);
    }
    
    public static void installUiContext(final Context uiContext) {
        com.onyx.android.sdk.base.utils.ResManager.INSTANCE.installUiContext(uiContext);
        final Iterator<WeakReference<Context>> iterator = ResManager.b.iterator();
        while (iterator.hasNext()) {
            final WeakReference weakReference;
            if ((weakReference = iterator.next()).get() == uiContext) {
                final List<WeakReference<Context>> b = ResManager.b;
                b.remove(weakReference);
                b.add(weakReference);
                return;
            }
        }
        ResManager.b.add(new WeakReference<Context>(uiContext));
    }
    
    public static void uninstallUiContext() {
        com.onyx.android.sdk.base.utils.ResManager.INSTANCE.uninstallUiContext();
        final Iterator<WeakReference<Context>> iterator = ResManager.b.iterator();
        while (iterator.hasNext()) {
            iterator.next().clear();
        }
        ResManager.b.clear();
    }
    
    public static void uninstallUiContext(final Context uiContext) {
        final Iterator<WeakReference<Context>> iterator = ResManager.b.iterator();
        while (iterator.hasNext()) {
            final WeakReference weakReference;
            if ((weakReference = iterator.next()).get() == uiContext) {
                weakReference.clear();
                ResManager.b.remove(weakReference);
            }
        }
    }
    
    public static Context getContext() {
        for (int i = ResManager.b.size() - 1; i >= 0; --i) {
            try {
                final WeakReference<Context> weakReference;
                if ((weakReference = CollectionUtils.getElement(ResManager.b, i)) != null) {
                    final Context context;
                    if ((context = weakReference.get()) != null) {
                        return context;
                    }
                }
            }
            catch (final Exception ex) {
                Debug.w((Class)ResManager.class, (Throwable)ex);
            }
        }
        return ResManager.a;
    }
    
    public static void dumpUIContext() {
        final List<WeakReference<Context>> b;
        if ((b = ResManager.b).size() == 0) {
            return;
        }
        final Iterator<WeakReference<Context>> iterator = b.iterator();
        while (iterator.hasNext()) {
            final WeakReference weakReference;
            if ((weakReference = iterator.next()).get() != null) {
                Debug.e((Class)ResManager.class, "reference not uninstall: " + weakReference.get().getClass().getSimpleName(), new Object[0]);
            }
        }
    }
    
    public static String getString(final int resId) {
        return getString(getContext(), resId);
    }
    
    public static String getString(final Context context, final int resId) {
        return context.getResources().getString(resId);
    }
    
    public static String getStringSafely(final int resId) {
        return getStringSafely(getContext(), resId);
    }
    
    public static String getStringSafely(final Context context, final int resId) {
        String string = "";
        if (resId > 0) {
            try {
                string = getString(context, resId);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        return string;
    }
    
    public static String getString(final String resName) {
        return getString(getIdentifier(resName, "string"));
    }
    
    public static String getStringSafely(final String resName) {
        return getStringSafely(getContext(), resName);
    }
    
    public static int getScreenArea() {
        final Rect realScreenRect = getRealScreenRect(getAppContext());
        return realScreenRect.width() * realScreenRect.height();
    }
    
    public static String getStringSafely(final Context context, final String resName) {
        final int identifier;
        if ((identifier = getIdentifier(resName, "string")) <= 0) {
            return "";
        }
        return getStringSafely(context, identifier);
    }
    
    public static String getString(final int resId, final Object... formatArgs) {
        try {
            return getContext().getResources().getString(resId, formatArgs);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
    
    public static String getStringSafely(final int resId, final Object... formatArgs) {
        try {
            return getString(resId, formatArgs);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
    
    public static Integer getInteger(final int resId) {
        return getContext().getResources().getInteger(resId);
    }
    
    public static Integer getInteger(final Context context, final int resId) {
        if (context == null) {
            return getInteger(resId);
        }
        return context.getResources().getInteger(resId);
    }
    
    public static float getFloat(final int resId) {
        final TypedValue typedValue = new TypedValue();
        getContext().getResources().getValue(resId, typedValue, true);
        return typedValue.getFloat();
    }
    
    public static String[] getStringArray(final int resId) {
        return getContext().getResources().getStringArray(resId);
    }
    
    public static String[] getStringArray(final String resName) {
        final int identifier;
        if ((identifier = getIdentifier(resName, "array")) <= 0) {
            return new String[0];
        }
        return getContext().getResources().getStringArray(identifier);
    }
    
    public static TypedArray getTypedArray(final int resId) {
        return getContext().getResources().obtainTypedArray(resId);
    }
    
    public static int getDimens(final int resId) {
        return getContext().getResources().getDimensionPixelSize(resId);
    }
    
    public static int getDimensSafely(final int resId) {
        try {
            return getDimens(resId);
        }
        catch (final Resources.NotFoundException ex) {
            return -1;
        }
    }
    
    public static int getColor(final int res) {
        return getContext().getResources().getColor(res);
    }
    
    public static ColorStateList getColorStateList(final int res) {
        return getContext().getResources().getColorStateList(res);
    }
    
    public static int[] getIntArray(final int resId) {
        return getContext().getResources().getIntArray(resId);
    }
    
    public static Integer[] getIntegerArray(final int resId) {
        final int[] intArray;
        final Integer[] array = new Integer[(intArray = getIntArray(resId)).length];
        for (int i = 0; i < intArray.length; ++i) {
            array[i] = intArray[i];
        }
        return array;
    }
    
    public static Float[] getFloatArray(final int resId) {
        final String[] stringArray;
        final Float[] array = new Float[(stringArray = getContext().getResources().getStringArray(resId)).length];
        for (int i = 0; i < stringArray.length; ++i) {
            array[i] = MathUtils.parseFloat(stringArray[i]);
        }
        return array;
    }
    
    public static String getUriOfRawName(final String rawName) {
        return "file:///android_res/raw/" + rawName;
    }
    
    public static String getUriOfAssets(final String htmlName) {
        return "file:///android_asset/" + htmlName;
    }
    
    public static float getDimension(final int resId) {
        return getContext().getResources().getDimension(resId);
    }
    
    public static int getDimensionPixelSize(final int resId) {
        return getContext().getResources().getDimensionPixelSize(resId);
    }
    
    public static float getFloatValue(final int floatDimen) {
        final TypedValue typedValue = new TypedValue();
        getResources().getValue(floatDimen, typedValue, true);
        return typedValue.getFloat();
    }
    
    public static Drawable getDrawable(final String name) {
        final int drawableResIdSafely;
        if ((drawableResIdSafely = getDrawableResIdSafely(name)) <= 0) {
            return null;
        }
        return getDrawable(drawableResIdSafely);
    }
    
    public static Drawable getDrawable(final int resId) {
        return getContext().getResources().getDrawable(resId);
    }
    
    public static int getDrawableResId(final String resName) {
        return getIdentifier(resName, "drawable");
    }
    
    public static int getDrawableResIdSafely(final String resName) {
        try {
            return getDrawableResId(resName);
        }
        catch (final Exception ex) {
            return 0;
        }
    }
    
    public static float getScreenWidth() {
        return (float)ResManager.a.getResources().getDisplayMetrics().widthPixels;
    }
    
    public static float getScreenHeight() {
        return (float)ResManager.a.getResources().getDisplayMetrics().heightPixels;
    }
    
    public static float getLongerScreenSize() {
        return Math.max(getScreenWidth(), getScreenHeight());
    }
    
    public static float getShorterScreenSize() {
        return Math.min(getScreenWidth(), getScreenHeight());
    }
    
    public static int getLongerRealScreenSize() {
        final Rect realScreenRect = getRealScreenRect(getAppContext());
        return Math.max(realScreenRect.width(), realScreenRect.height());
    }
    
    public static int getShorterRealScreenSize() {
        final Rect realScreenRect = getRealScreenRect(getAppContext());
        return Math.min(realScreenRect.width(), realScreenRect.height());
    }
    
    public static Rect getPortraitRealScreenRect() {
        return new Rect(0, 0, getShorterRealScreenSize(), getLongerRealScreenSize());
    }
    
    public static RectF getScreenRectF() {
        return new RectF(0.0f, 0.0f, getShorterScreenSize(), getLongerScreenSize());
    }
    
    @TargetApi(17)
    public static int getRealScreenHeight(final Context context) {
        final WindowManager windowManager = (WindowManager)context.getSystemService("window");
        final Point point = new Point(0, 0);
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getRealSize(point);
        }
        return point.y;
    }
    
    @TargetApi(17)
    public static Rect getRealScreenRect(final Context context) {
        final WindowManager windowManager = (WindowManager)context.getSystemService("window");
        final Point point = new Point(0, 0);
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getRealSize(point);
        }
        final Point point2 = point;
        return new Rect(0, 0, point2.x, point2.y);
    }
    
    public static int getWindowDefaultWidth() {
        return getWindowDefaultWidth(getContext());
    }
    
    public static int getWindowDefaultWidth(final Context context) {
        return getWindowDefaultSize(context).x;
    }
    
    public static int getWindowDefaultHeight() {
        return getWindowDefaultHeight(getContext());
    }
    
    public static int getWindowDefaultHeight(final Context context) {
        return getWindowDefaultSize(context).y;
    }
    
    public static boolean isLandscapeWindows() {
        return getWindowDefaultWidth() > getWindowDefaultHeight();
    }
    
    public static Point getWindowDefaultSize(final Context context) {
        final WindowManager windowManager = (WindowManager)context.getSystemService("window");
        final Point point = new Point(0, 0);
        if (windowManager != null) {
            final Point realSize = point;
            windowManager.getDefaultDisplay().getRealSize(point);
            a(context, realSize);
        }
        return point;
    }
    
    public static Rect getWindowDefaultRect() {
        final Point windowDefaultSize = getWindowDefaultSize(getContext());
        return new Rect(0, 0, windowDefaultSize.x, windowDefaultSize.y);
    }
    
    private static void a(final Context context, final Point realSize) {
        if (!DeviceUtils.isSystemInMultiWindowMode(context)) {
            return;
        }
        if (b(context, realSize)) {
            return;
        }
        final int x;
        final int y;
        if ((x = realSize.x) > (y = realSize.y)) {
            realSize.set(x / 2, y);
        }
        else {
            realSize.set(x, y / 2);
        }
    }
    
    private static boolean b(final Context context, final Point realSize) {
        if (!CompatibilityUtil.apiLevelCheck(30)) {
            return false;
        }
        final Rect bounds = ((WindowManager)context.getSystemService("window")).getCurrentWindowMetrics().getBounds();
        realSize.set(bounds.width(), bounds.height());
        return true;
    }
    
    public static float getAspectRatio() {
        return getScreenWidth() / getScreenHeight();
    }
    
    public static Context getAppContext() {
        return ResManager.a;
    }
    
    public static int getDPI() {
        return getContext().getResources().getDisplayMetrics().densityDpi;
    }
    
    public static int getIdentifier(final String name, final String type) {
        return getContext().getResources().getIdentifier(name, type, getContext().getPackageName());
    }
    
    public static int getIdentifierFromPath(final String path) {
        if (StringUtils.isNullOrEmpty(path)) {
            return 0;
        }
        if (!path.startsWith("@")) {
            Debug.e((Class)ResManager.class, "getIdentifierFromPath error path: " + path, new Object[0]);
            return 0;
        }
        final int index;
        if ((index = path.indexOf("/")) <= 0) {
            Debug.e((Class)ResManager.class, "getIdentifierFromPath error path split char: " + path, new Object[0]);
            return 0;
        }
        final int identifier;
        if ((identifier = getContext().getResources().getIdentifier(path.substring(index + 1), path.substring(1, index), getContext().getPackageName())) <= 0) {
            Debug.e((Class)ResManager.class, "getIdentifierFromPath error res id: " + path, new Object[0]);
        }
        return identifier;
    }
    
    public static int getRawIdentifier(final String name) {
        return getIdentifier(name, "raw");
    }
    
    public static InputStream openRawResource(final String name) {
        return getContext().getResources().openRawResource(getIdentifier(name, "raw"));
    }
    
    public static InputStream openRawResource(final int id) {
        return getContext().getResources().openRawResource(id);
    }
    
    public static String getQuantityString(final int resId, final int quantity) {
        return getContext().getResources().getQuantityString(resId, quantity);
    }
    
    public static String getQuantityString(final int resId, final int quantity, final Object... formatArgs) {
        return getContext().getResources().getQuantityString(resId, quantity, formatArgs);
    }
    
    public static boolean getBoolean(final int id) {
        return getContext().getResources().getBoolean(id);
    }
    
    public static Resources getResources() {
        return getContext().getResources();
    }
    
    @Nullable
    public static String getResourceEntryName(final int id) {
        if (id <= 0) {
            return null;
        }
        try {
            return getResources().getResourceEntryName(id);
        }
        catch (final Exception ex) {
            return null;
        }
    }
    
    public static int getDensityDpi() {
        return getAppContext().getResources().getDisplayMetrics().densityDpi;
    }
    
    public static float getDensity() {
        return getAppContext().getResources().getDisplayMetrics().density;
    }
    
    public static String getResourceName(final int resId) {
        if (resId == 0) {
            return "";
        }
        return getAppContext().getResources().getResourceName(resId);
    }
    
    public static boolean isDeviceDpi(final int targetDPI) {
        return getDensityDpi() == targetDPI;
    }
    
    @Nullable
    public static Drawable getDrawableForDensity(final String packageName, final int resourceId, final int density) {
        if (resourceId <= 0) {
            return null;
        }
        try {
            return getContext().getPackageManager().getResourcesForApplication(ApplicationUtil.getPackageInfoFromPackageName(getContext(), packageName).applicationInfo).getDrawableForDensity(resourceId, density);
        }
        catch (final Exception ex) {
            return null;
        }
    }
    
    public static boolean shouldColorOnTopBeLight(@ColorInt final int colorOnBottomInt) {
        return 186.0 > Color.red(colorOnBottomInt) * 0.299 + (Color.green(colorOnBottomInt) * 0.587 + Color.blue(colorOnBottomInt) * 0.114);
    }
    
    public static Document loadXmlWithDom(int xmlResId) {
        try (InputStream input = getResources().openRawResource(xmlResId)) {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static String getPackageName() {
        return (getContext() == null) ? "" : getContext().getPackageName();
    }
    
    public static float getSystemFontScale() {
        return Optional.ofNullable(getContext()).map(context -> context.getResources().getConfiguration().fontScale).orElse(1.0f);
    }
    
    static {
        b = new ArrayList<WeakReference<Context>>();
    }
    
    public static class Constants
    {
        public static final int INVALID_DIMENS = -1;
        public static final int INVALID_BACKGROUND_RES = -1;
    }
}
