package com.onyx.android.sdk.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import androidx.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/** Safe reflection helpers used throughout the device SDK. */
public class ReflectUtil {
    private static final String TAG = "ReflectUtil";

    static {
        exemptHiddenApis();
    }

    @TargetApi(28)
    private static void exemptHiddenApis() {
        if (Build.VERSION.SDK_INT < 28) {
            return;
        }
        try {
            Class<?> vmRuntime = Class.forName("dalvik.system.VMRuntime");
            Method getRuntime = vmRuntime.getDeclaredMethod("getRuntime");
            Method setExemptions = vmRuntime.getDeclaredMethod(
                    "setHiddenApiExemptions", String[].class);
            setExemptions.invoke(getRuntime.invoke(null), (Object) new String[]{"L"});
        } catch (Throwable error) {
            Log.e(TAG, "reflect bootstrap failed:", error);
        }
    }

    public static boolean getConstructorSafely(
            AtomicReference<Constructor<?>> result, Class<?> cls, Class<?>... parameterTypes) {
        if (result == null || cls == null) {
            return false;
        }
        try {
            result.set(cls.getConstructor(parameterTypes));
            return true;
        } catch (ReflectiveOperationException | SecurityException error) {
            Log.w(TAG, error);
            return false;
        }
    }

    public static Constructor<?> getConstructorSafely(Class<?> cls, Class<?>... parameterTypes) {
        AtomicReference<Constructor<?>> result = new AtomicReference<>();
        return getConstructorSafely(result, cls, parameterTypes) ? result.get() : null;
    }

    public static Class<?> classForName(String name) {
        try {
            return Class.forName(name);
        } catch (Exception error) {
            Log.e(TAG, "", error);
            return null;
        }
    }

    public static boolean getMethodSafely(
            AtomicReference<Method> result,
            Class<?> cls,
            String name,
            Class<?>... parameterTypes) {
        if (result == null || cls == null) {
            return false;
        }
        try {
            result.set(cls.getMethod(name, parameterTypes));
            return true;
        } catch (ReflectiveOperationException | SecurityException error) {
            if (Debug.getDebug()) {
                Log.w(TAG, error);
            }
            return false;
        }
    }

    public static Method getMethodSafely(Class<?> cls, String name, Class<?>... parameterTypes) {
        AtomicReference<Method> result = new AtomicReference<>();
        return getMethodSafely(result, cls, name, parameterTypes) ? result.get() : null;
    }

    public static boolean getStaticIntFieldSafely(
            AtomicReference<Integer> result, Class<?> cls, String name) {
        if (result == null || cls == null) {
            return false;
        }
        try {
            result.set(cls.getField(name).getInt(null));
            return true;
        } catch (ReflectiveOperationException | RuntimeException error) {
            Log.w(TAG, error);
            return false;
        }
    }

    public static boolean getStaticIntDeclaredFieldSafely(
            AtomicReference<Integer> result, Class<?> cls, String name) {
        if (result == null || cls == null) {
            return false;
        }
        try {
            Field field = cls.getDeclaredField(name);
            field.setAccessible(true);
            result.set(field.getInt(null));
            return true;
        } catch (ReflectiveOperationException | RuntimeException error) {
            Log.w(TAG, error);
            return false;
        }
    }

    public static int getStaticIntFieldSafely(Class<?> cls, String name) {
        AtomicReference<Integer> result = new AtomicReference<>();
        return getStaticIntFieldSafely(result, cls, name) ? getSafely(result.get()) : 0;
    }

    public static int getStaticIntDeclaredFieldSafely(Class<?> cls, String name) {
        AtomicReference<Integer> result = new AtomicReference<>();
        return getStaticIntDeclaredFieldSafely(result, cls, name) ? getSafely(result.get()) : 0;
    }

    public static Object getFieldSafely(Class<?> cls, Object instance, String name) {
        AtomicReference<Object> result = new AtomicReference<>();
        return getFieldSafely(result, cls, instance, name) ? result.get() : null;
    }

    public static boolean getFieldSafely(
            AtomicReference<Object> result, Class<?> cls, Object instance, String name) {
        if (result == null || cls == null) {
            return false;
        }
        try {
            result.set(cls.getField(name).get(instance));
            return true;
        } catch (ReflectiveOperationException | RuntimeException error) {
            Log.w(TAG, error);
            return false;
        }
    }

    public static Object getDeclaredFieldSafely(Class<?> cls, Object instance, String name) {
        AtomicReference<Object> result = new AtomicReference<>();
        return getDeclaredFieldSafely(result, cls, instance, name) ? result.get() : null;
    }

    public static boolean getDeclaredFieldSafely(
            AtomicReference<Object> result, Class<?> cls, Object instance, String name) {
        if (result == null || cls == null) {
            return false;
        }
        try {
            Field field = cls.getDeclaredField(name);
            field.setAccessible(true);
            result.set(field.get(instance));
            return true;
        } catch (ReflectiveOperationException | RuntimeException error) {
            Log.w(TAG, error);
            return false;
        }
    }

    public static boolean getStaticFieldSafely(
            AtomicReference<Object> result, Class<?> cls, String name) {
        return getFieldSafely(result, cls, null, name);
    }

    public static Object getStaticFieldSafely(Class<?> cls, String name) {
        AtomicReference<Object> result = new AtomicReference<>();
        return getStaticFieldSafely(result, cls, name) ? result.get() : null;
    }

    public static boolean constructObjectSafely(
            AtomicReference<Object> result, Constructor<?> constructor, Object... args) {
        if (result == null || constructor == null) {
            return false;
        }
        try {
            result.set(constructor.newInstance(args));
            return true;
        } catch (ReflectiveOperationException | RuntimeException error) {
            Log.w(TAG, "", error);
            return false;
        }
    }

    public static Object newInstance(Constructor<?> constructor, Object... args) {
        AtomicReference<Object> result = new AtomicReference<>();
        return constructObjectSafely(result, constructor, args) ? result.get() : null;
    }

    public static boolean invokeMethodSafely(
            AtomicReference<Object> result, Method method, Object receiver, Object... args) {
        if (result == null || method == null) {
            return false;
        }
        try {
            result.set(method.invoke(receiver, args));
            return true;
        } catch (ReflectiveOperationException | RuntimeException error) {
            Debug.w(error);
            return false;
        }
    }

    public static Object invokeMethodSafely(Method method, Object receiver, Object... args) {
        AtomicReference<Object> result = new AtomicReference<>();
        return invokeMethodSafely(result, method, receiver, args) ? result.get() : null;
    }

    public static Method getDeclaredMethodSafely(
            Class<?> cls, String name, Class<?>... parameterTypes) {
        AtomicReference<Method> result = new AtomicReference<>();
        return getDeclaredMethod(result, cls, name, parameterTypes) ? result.get() : null;
    }

    public static boolean getDeclareIntFieldSafely(
            AtomicReference<Integer> result, Class<?> cls, Object obj, String name) {
        if (result == null || cls == null) {
            return false;
        }
        try {
            Field field = cls.getDeclaredField(name);
            field.setAccessible(true);
            result.set(field.getInt(obj));
            return true;
        } catch (ReflectiveOperationException | RuntimeException error) {
            Log.w(TAG, error);
            return false;
        }
    }

    public static boolean getDeclareStringFieldSafely(
            AtomicReference<String> result, Class<?> cls, Object obj, String name) {
        if (result == null || cls == null) {
            return false;
        }
        try {
            Field field = cls.getDeclaredField(name);
            field.setAccessible(true);
            result.set((String) field.get(obj));
            return true;
        } catch (ReflectiveOperationException | RuntimeException error) {
            Log.w(TAG, error);
            return false;
        }
    }

    public static int getDeclareIntFieldSafely(String clsName, Object obj, String name) {
        return getDeclareIntFieldSafely(classForName(clsName), obj, name);
    }

    public static int getDeclareIntFieldSafely(Class<?> cls, Object obj, String name) {
        AtomicReference<Integer> result = new AtomicReference<>();
        return getDeclareIntFieldSafely(result, cls, obj, name) ? getSafely(result.get()) : 0;
    }

    public static String getDeclareStringFieldSafely(Class<?> cls, Object obj, String name) {
        AtomicReference<String> result = new AtomicReference<>();
        return getDeclareStringFieldSafely(result, cls, obj, name) ? result.get() : null;
    }

    public static boolean getStaticInnerClassDeclareIntFieldSafely(
            AtomicReference<Integer> result,
            Class<?> cls,
            String innerClsName,
            String fieldName) {
        if (result == null || cls == null) {
            return false;
        }
        try {
            for (Class<?> inner : cls.getDeclaredClasses()) {
                if (Modifier.isStatic(inner.getModifiers())
                        && inner.getSimpleName().contains(innerClsName)) {
                    Field field = inner.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    result.set(field.getInt(null));
                    return true;
                }
            }
        } catch (ReflectiveOperationException | RuntimeException error) {
            Log.w(TAG, error);
        }
        return false;
    }

    public static int getStaticInnerClassDeclareIntFieldSafely(
            String clsName, String innerClsName, String fieldName) {
        return getStaticInnerClassDeclareIntFieldSafely(
                classForName(clsName), innerClsName, fieldName);
    }

    public static int getStaticInnerClassDeclareIntFieldSafely(
            Class<?> cls, String innerClsName, String fieldName) {
        AtomicReference<Integer> result = new AtomicReference<>();
        return getStaticInnerClassDeclareIntFieldSafely(result, cls, innerClsName, fieldName)
                ? getSafely(result.get()) : 0;
    }

    public static boolean setDeclareIntFieldSafely(
            String clsName, Object obj, String fieldName, int targetValue) {
        return setDeclareIntFieldSafely(classForName(clsName), obj, fieldName, targetValue);
    }

    public static boolean setDeclareIntFieldSafely(
            Class<?> cls, Object obj, String fieldName, int targetValue) {
        if (cls == null) {
            return false;
        }
        try {
            Field field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.setInt(obj, targetValue);
            return true;
        } catch (ReflectiveOperationException | RuntimeException error) {
            Log.w(TAG, error);
            return false;
        }
    }

    public static boolean getDeclaredMethod(
            AtomicReference<Method> result,
            Class<?> cls,
            String name,
            Class<?>... parameterTypes) {
        if (result == null || cls == null) {
            return false;
        }
        try {
            Method method = cls.getDeclaredMethod(name, parameterTypes);
            method.setAccessible(true);
            result.set(method);
            return true;
        } catch (ReflectiveOperationException | RuntimeException error) {
            return false;
        }
    }

    public static Constructor<?> getDeclaredConstructorSafely(
            Class<?> cls, Class<?>... parameterTypes) {
        AtomicReference<Constructor<?>> result = new AtomicReference<>();
        return getDeclaredConstructorSafely(result, cls, parameterTypes) ? result.get() : null;
    }

    public static boolean getDeclaredConstructorSafely(
            AtomicReference<Constructor<?>> result, Class<?> cls, Class<?>... parameterTypes) {
        if (result == null || cls == null) {
            return false;
        }
        try {
            Constructor<?> constructor = cls.getDeclaredConstructor(parameterTypes);
            constructor.setAccessible(true);
            result.set(constructor);
            return true;
        } catch (ReflectiveOperationException | RuntimeException error) {
            Log.w(TAG, error);
            return false;
        }
    }

    @Nullable
    public static Object getObjectFiled(Object obj, String fieldName) {
        if (obj == null) {
            return null;
        }
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (ReflectiveOperationException | RuntimeException error) {
            Log.w(TAG, error);
            return null;
        }
    }

    public static int getSafely(Integer result) {
        return result == null ? 0 : result;
    }

    public static boolean getSafely(Boolean result) {
        return result != null && result;
    }

    public static boolean setDeclareFieldSafely(
            String clsName, Object obj, String fieldName, Object targetValue) {
        return setDeclareFieldSafely(classForName(clsName), obj, fieldName, targetValue);
    }

    public static boolean setDeclareFieldSafely(
            Class<?> cls, Object obj, String fieldName, Object targetValue) {
        if (cls == null) {
            return false;
        }
        try {
            Field field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, targetValue);
            return true;
        } catch (ReflectiveOperationException | RuntimeException error) {
            Log.w(TAG, error);
            return false;
        }
    }

    public static Map<String, String> getBeanKeyValueMap(Object bean) {
        Map<String, String> values = new HashMap<>();
        if (bean == null) {
            return values;
        }
        try {
            for (Field field : bean.getClass().getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                Object value = field.get(bean);
                if (value != null) {
                    values.put(field.getName(), value.toString());
                }
            }
        } catch (ReflectiveOperationException | RuntimeException error) {
            Log.w(TAG, error);
        }
        return values;
    }

    @Nullable
    public static Field getFieldSafely(Class<?> clazz, String name) {
        if (clazz == null) {
            return null;
        }
        try {
            return clazz.getField(name);
        } catch (NoSuchFieldException | SecurityException error) {
            Log.w(TAG, error);
            return null;
        }
    }

    @Nullable
    public static Object getObjectSafely(Object obj, Field field) {
        if (field == null) {
            return null;
        }
        try {
            field.setAccessible(true);
            return field.get(obj);
        } catch (IllegalAccessException | RuntimeException error) {
            Log.w(TAG, error);
            return null;
        }
    }

    public static int getIntSafely(Object obj, Field field) {
        if (field == null) {
            return 0;
        }
        try {
            field.setAccessible(true);
            return field.getInt(obj);
        } catch (IllegalAccessException | RuntimeException error) {
            Log.w(TAG, error);
            return 0;
        }
    }

    public static void setObjectSafely(Field field, Object obj, Object targetValue) {
        if (field == null) {
            return;
        }
        try {
            field.setAccessible(true);
            field.set(obj, targetValue);
        } catch (IllegalAccessException | RuntimeException error) {
            Log.w(TAG, error);
        }
    }
}
