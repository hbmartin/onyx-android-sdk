package com.onyx.android.sdk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

public class PreferenceManager {
   private static SharedPreferences a;
   private static Editor b;

   public static void init(Context context) {
      a = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
   }

   public static SharedPreferences getDefaultPrefs() {
      return a;
   }

   public static boolean getBooleanValue(Context context, int ResID, boolean defaultValue) {
      return getBooleanValue(context, context.getResources().getString(ResID), defaultValue);
   }

   public static int getIntValue(Context context, int ResID, int defaultValue) {
      return getIntValue(context, context.getResources().getString(ResID), defaultValue);
   }

   public static String getStringValue(Context context, int ResID, String defaultValue) {
      return getStringValue(context, context.getResources().getString(ResID), defaultValue);
   }

   public static boolean getBooleanValue(Context context, String key, boolean defaultValue) {
      return a.getBoolean(key, defaultValue);
   }

   public static int getIntValue(Context context, String key, int defaultValue) {
      return a.getInt(key, defaultValue);
   }

   public static String getStringValue(Context context, String key, String defaultValue) {
      return a.getString(key, defaultValue);
   }

   public static long getLongValue(Context context, String key, long defaultValue) {
      return a.getLong(key, defaultValue);
   }

   public static void setBooleanValue(Context context, int ResID, boolean value) {
      setBooleanValue(context, context.getResources().getString(ResID), value);
   }

   public static void setIntValue(Context context, int ResID, int value) {
      setIntValue(context, context.getResources().getString(ResID), value);
   }

   public static void setStringValue(Context context, int ResID, String value) {
      setStringValue(context, context.getResources().getString(ResID), value);
   }

   public static void setLongValue(Context context, int ResID, long value) {
      setLongValue(context, context.getResources().getString(ResID), value);
   }

   public static void setBooleanValue(Context context, String key, boolean value) {
      (b = a.edit()).putBoolean(key, value);
      b.apply();
   }

   public static void setIntValue(Context context, String key, int value) {
      (b = a.edit()).putInt(key, value);
      b.apply();
   }

   public static void setStringValue(Context context, String key, String value) {
      (b = a.edit()).putString(key, value);
      b.apply();
   }

   public static void setLongValue(Context context, String key, long value) {
      (b = a.edit()).putLong(key, value);
      b.apply();
   }

   public static boolean contains(String key) {
      return a.contains(key);
   }

   public static void register(OnSharedPreferenceChangeListener listener) {
      getDefaultPrefs().registerOnSharedPreferenceChangeListener(listener);
   }

   public static void unregister(OnSharedPreferenceChangeListener listener) {
      getDefaultPrefs().unregisterOnSharedPreferenceChangeListener(listener);
   }
}
