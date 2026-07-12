package com.onyx.android.sdk.common.provider;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import androidx.core.content.FileProvider;
import com.onyx.android.sdk.device.EnvironmentUtil;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.ResManager;
import com.onyx.android.sdk.utils.StringUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OnyxFileProviderUtil {
  private static final String a = ".onyx.fileprovider";
  
  private static final String b = "content://";
  
  private static final Map<String, String> c = new a();
  
  public static Uri getUriForFile(Context context, File file) {
    return (Build.VERSION.SDK_INT >= 24) ? 
      getUriForFile24(context, file) : 
      
      Uri.fromFile(file);
  }
  
  public static Uri getFileProviderUri(String filePath) {
    if (StringUtils.isNullOrEmpty(filePath)) {
      return null;
    }
    try {
      Uri uri = buildProviderUri(filePath, EXTERNAL_PACKAGE_SPECIFIC_PATH_PREFIXES, d);
      return uri != null ? uri : buildProviderUri(filePath, a(), c);
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    }
  }

  private static Uri buildProviderUri(String filePath, List<String> prefixes,
                                      Map<String, String> roots) {
    for (String prefix : prefixes) {
      if (!filePath.startsWith(prefix)) {
        continue;
      }
      String[] parts = filePath.substring(prefix.length()).split(File.separator, 3);
      if (parts.length < 2 || !roots.containsKey(parts[1])) {
        return null;
      }
      String relative = parts.length == 3 ? parts[2] : "";
      return Uri.parse("content://" + parts[0] + a + File.separator
              + roots.get(parts[1]) + File.separator + relative);
    }
    return null;
  }
  
  public static boolean shouldUseFileProvider(String myPackage, String path) {
    if (Build.VERSION.SDK_INT <= 29)
      return false; 
    if (StringUtils.isNullOrEmpty(path) || StringUtils.isNullOrEmpty(myPackage))
      return false; 
    for (Iterator<String> iterator2 = EXTERNAL_PACKAGE_SPECIFIC_PATH_PREFIXES.iterator(); iterator2.hasNext();) {
      String str = iterator2.next();
      if (path.startsWith(str))
        return path.startsWith(str + myPackage + File.separator) ^ true; 
    } 
    for (Iterator<String> iterator1 = a().iterator(); iterator1.hasNext();) {
      String str = iterator1.next();
      if (path.startsWith(str))
        return path.startsWith(str + myPackage + File.separator) ^ true; 
    } 
    return false;
  }
  
  private static List<String> a() {
    Context context;
    if ((context = ResManager.getAppContext()) == null)
      return Collections.emptyList(); 
    try {
      return Collections.singletonList(context.getCacheDir().getAbsolutePath().split(context.getPackageName())[0]);
    } catch (Exception exception) {
      Debug.w(exception);
      return Collections.emptyList();
    }
  }
  
  public static ParcelFileDescriptor openFileDescriptor(Context context, Uri uri, String mode) throws FileNotFoundException {
    return context.getContentResolver().openFileDescriptor(uri, mode);
  }
  
  public static ArrayList<Uri> getUriForFileList(Context context, List<File> fileList) {
    ArrayList<Uri> result = new ArrayList<>(fileList.size());
    for (File file : fileList) {
      result.add(getUriForFile(context, file));
    }
    return result;
  }
  
  public static Uri getUriForFile24(Context context, File file) {
    return FileProvider.getUriForFile(context, context.getPackageName() + ".onyx.fileprovider", file);
  }
  
  public static void setIntentDataAndType(Context context, Intent intent, String type, File file, boolean writeAble) {
    if (Build.VERSION.SDK_INT >= 24) {
      intent.setDataAndType(getUriForFile(context, file), type);
      intent.addFlags(1);
      if (writeAble)
        intent.addFlags(2); 
    } else {
      intent.setDataAndType(Uri.fromFile(file), type);
    } 
  }
  
  public static void setIntentData(Context context, Intent intent, File file, boolean writeAble) {
    if (Build.VERSION.SDK_INT >= 24) {
      intent.setData(getUriForFile(context, file));
      intent.addFlags(1);
      if (writeAble)
        intent.addFlags(2); 
    } else {
      intent.setData(Uri.fromFile(file));
    } 
  }
  
  public static void grantPermissions(Context context, Intent intent, Uri uri, boolean writeAble) {
    int i = Intent.FLAG_GRANT_READ_URI_PERMISSION;
    if (writeAble) {
      i |= Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
    }
    intent.addFlags(i);
    for (Iterator iterator = context.getPackageManager().queryIntentActivities(intent, 65536).iterator(); iterator.hasNext();)
      context.grantUriPermission(((ResolveInfo)iterator.next()).activityInfo.packageName, uri, i); 
  }
  
  public static boolean isOnyxContentSchemeFromUri(Uri uri) {
    String str;
    return (uri == null) ? false : ((StringUtils.isNotBlank(str = uri.getAuthority()) && str.endsWith(".onyx.fileprovider")));
  }
  
  static class b extends HashMap<String, String> {
    b() {
      put("cache", "external_cache_path");
      put("files", "external_file_path");
    }
  }
  
  static class a extends HashMap<String, String> {
    a() {
      put("cache", "cache");
      put("files", "files");
    }
  }
  
  public static final List<String> EXTERNAL_PACKAGE_SPECIFIC_PATH_PREFIXES = Arrays.asList(EnvironmentUtil.getExternalStorageDirectory() + "/Android/data/");
  
  private static final Map<String, String> d = new b();
}
