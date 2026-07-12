// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.data.render;

import org.jetbrains.annotations.Nullable;
import android.graphics.Rect;
import android.graphics.Canvas;
import java.lang.reflect.Constructor;
import android.view.View;
import com.onyx.android.sdk.utils.ReflectUtil;
import kotlin.jvm.internal.Intrinsics;
import java.lang.reflect.Method;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020\u001cH\u0016J\u0010\u0010!\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020\u001cH\u0016J\b\u0010\"\u001a\u00020\u0010H\u0016J\b\u0010#\u001a\u00020$H\u0016J\b\u0010%\u001a\u00020$H\u0016J\b\u0010&\u001a\u00020'H\u0016J\b\u0010(\u001a\u00020\u0010H\u0016J\b\u0010)\u001a\u00020*H\u0016J\u0010\u0010+\u001a\u00020\u001e2\u0006\u0010,\u001a\u00020-H\u0016J\u0010\u0010.\u001a\u00020\u001e2\u0006\u0010/\u001a\u00020$H\u0016J\u0010\u00100\u001a\u00020\u001e2\u0006\u00101\u001a\u00020$H\u0016R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\b\u001a\u0012\u0012\u0002\b\u0003 \u0007*\b\u0012\u0002\b\u0003\u0018\u00010\t0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0016\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0017\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0018\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0019\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000¨\u00062" }, d2 = { "Lcom/onyx/android/sdk/data/render/ViewRenderNode;", "Lcom/onyx/android/sdk/data/render/BaseRenderNode;", "name", "", "(Ljava/lang/String;)V", "discardDisplayListMethod", "Ljava/lang/reflect/Method;", "kotlin.jvm.PlatformType", "displayListCanvasClass", "Ljava/lang/Class;", "drawRenderNodeMethod", "endRecordMethod", "getTranslationXMethod", "getTranslationYMethod", "hasDisplayListMethod", "height", "", "getName", "()Ljava/lang/String;", "renderNode", "", "renderNodeClass", "setPositionMethod", "setTranslationXMethod", "setTranslationYMethod", "startRecordMethod", "width", "beginRecording", "Landroid/graphics/Canvas;", "discardDisplayList", "", "drawRenderNode", "canvas", "endRecording", "getHeight", "getTranslationX", "", "getTranslationY", "getUniqueId", "", "getWidth", "hasDisplayList", "", "setPosition", "rect", "Landroid/graphics/Rect;", "setTranslationX", "x", "setTranslationY", "y", "onyxsdk-base_release" })
public final class ViewRenderNode implements BaseRenderNode
{
    @NotNull
    private final String a;
    @NotNull
    private Object b;
    @NotNull
    private Class<?> c;
    private final Class<?> d;
    private int e;
    private int f;
    private final Method g;
    private final Method h;
    private final Method i;
    private final Method j;
    private final Method k;
    private final Method l;
    private final Method m;
    private final Method n;
    private final Method o;
    private final Method p;
    
    public ViewRenderNode(@NotNull final String name) {
        Intrinsics.checkNotNullParameter((Object)name, "name");
        this.a = name;
        final Class classForName = ReflectUtil.classForName("android.view.RenderNode");
        Intrinsics.checkNotNullExpressionValue((Object)classForName, "classForName(\"android.view.RenderNode\")");
        this.c = classForName;
        final Class classForName2;
        final Class clazz = classForName2 = ReflectUtil.classForName("android.view.DisplayListCanvas");
        this.d = classForName2;
        final Class<?> c = this.c;
        final Class[] array = { null };
        final Class<Float> type = Float.TYPE;
        array[0] = type;
        this.g = ReflectUtil.getMethodSafely((Class)c, "setTranslationX", array);
        this.h = ReflectUtil.getMethodSafely((Class)this.c, "setTranslationY", new Class[] { type });
        this.i = ReflectUtil.getMethodSafely(clazz, "drawRenderNode", new Class[] { this.c });
        final Class<?> c2 = this.c;
        final Class[] array3;
        final Class[] array2 = array3 = new Class[4];
        final Class<Integer> type2 = Integer.TYPE;
        array2[1] = (array2[0] = type2);
        array2[3] = (array2[2] = type2);
        this.j = ReflectUtil.getMethodSafely((Class)c2, "setLeftTopRightBottom", array3);
        final Class<?> c3 = this.c;
        final Class[] array5;
        final Class[] array4 = array5 = new Class[2];
        array4[1] = (array4[0] = type2);
        this.k = ReflectUtil.getMethodSafely((Class)c3, "start", array5);
        this.l = ReflectUtil.getMethodSafely((Class)this.c, "end", new Class[] { classForName2 });
        this.m = ReflectUtil.getMethodSafely((Class)this.c, "isValid", new Class[0]);
        this.n = ReflectUtil.getMethodSafely((Class)this.c, "getTranslationX", new Class[0]);
        this.o = ReflectUtil.getMethodSafely((Class)this.c, "getTranslationY", new Class[0]);
        this.p = ReflectUtil.getMethodSafely((Class)this.c, "discardDisplayList", new Class[0]);
        final Object instance = createRenderNode(this.c, name);
        Intrinsics.checkNotNullExpressionValue(instance, "constructor.newInstance(name, null)");
        this.b = instance;
    }

    private static Object createRenderNode(Class<?> renderNodeClass, String name) {
        try {
            final Constructor<?> constructor = renderNodeClass.getDeclaredConstructor(String.class, View.class);
            Intrinsics.checkNotNullExpressionValue((Object)constructor, "renderNodeClass.getDeclaredConstructor(String::class.java, View::class.java)");
            constructor.setAccessible(true);
            return constructor.newInstance(name, null);
        } catch (ReflectiveOperationException exception) {
            return sneakyThrow(exception);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T, E extends Throwable> T sneakyThrow(Throwable failure) throws E {
        throw (E) failure;
    }
    
    @NotNull
    public final String getName() {
        return this.a;
    }
    
    @Override
    public boolean hasDisplayList() {
        final Object invokeMethodSafely;
        return (invokeMethodSafely = ReflectUtil.invokeMethodSafely(this.m, this.b, new Object[0])) instanceof Boolean && (boolean)invokeMethodSafely;
    }
    
    @Override
    public long getUniqueId() {
        final Object declaredFieldSafely;
        if ((declaredFieldSafely = ReflectUtil.getDeclaredFieldSafely((Class)this.c, this.b, "mNativeRenderNode")) instanceof Long) {
            return ((Number)declaredFieldSafely).longValue();
        }
        return 0L;
    }
    
    @Override
    public int getWidth() {
        return this.e;
    }
    
    @Override
    public int getHeight() {
        return this.f;
    }
    
    @Override
    public float getTranslationX() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(this.n, this.b, new Object[0])) instanceof Float) {
            return ((Number)invokeMethodSafely).floatValue();
        }
        return 0.0f;
    }
    
    @Override
    public float getTranslationY() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(this.o, this.b, new Object[0])) instanceof Float) {
            return ((Number)invokeMethodSafely).floatValue();
        }
        return 0.0f;
    }
    
    @Override
    public void setTranslationX(final float x) {
        ReflectUtil.invokeMethodSafely(this.g, this.b, new Object[] { x });
    }
    
    @Override
    public void setTranslationY(final float y) {
        ReflectUtil.invokeMethodSafely(this.h, this.b, new Object[] { y });
    }
    
    @Override
    public void discardDisplayList() {
        ReflectUtil.invokeMethodSafely(this.p, this.b, new Object[0]);
    }
    
    @Override
    public void drawRenderNode(@NotNull final Canvas canvas) {
        Intrinsics.checkNotNullParameter((Object)canvas, "canvas");
        ReflectUtil.invokeMethodSafely(this.i, (Object)canvas, new Object[] { this.b });
    }
    
    @Override
    public void setPosition(@NotNull final Rect rect) {
        Intrinsics.checkNotNullParameter((Object)rect, "rect");
        this.e = rect.width();
        this.f = rect.height();
        final Method j = this.j;
        final Object b = this.b;
        final Object[] array = new Object[4];
        final Object[] array3;
        final Object[] array2;
        (array2 = (array3 = array))[0] = rect.left;
        array2[1] = rect.top;
        array3[2] = rect.right;
        array[3] = rect.bottom;
        ReflectUtil.invokeMethodSafely(j, b, array);
    }
    
    @Nullable
    @Override
    public Canvas beginRecording() {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(this.k, this.b, new Object[] { this.e, this.f })) instanceof Canvas) {
            return (Canvas)invokeMethodSafely;
        }
        return null;
    }
    
    @Override
    public void endRecording(@NotNull final Canvas canvas) {
        Intrinsics.checkNotNullParameter((Object)canvas, "canvas");
        ReflectUtil.invokeMethodSafely(this.l, this.b, new Object[] { canvas });
    }
}
