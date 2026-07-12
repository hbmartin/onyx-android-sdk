package com.onyx.android.sdk.utils;

import com.onyx.android.sdk.data.FontInfo;
import com.onyx.android.sdk.data.SortOrder;
import com.onyx.android.sdk.device.EnvironmentUtil;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FontUtils {
  public static final String TTF = "ttf";
  
  public static final String TTC = "ttc";
  
  public static final String OTF = "otf";
  
  public static final String FONTS = "fonts";
  
  public static final String CUSTOMIZE_FONTS_PATH = EnvironmentUtil.getExternalStorageDirectory() + File.separator + "fonts";
  
  public static final String SYSTEM_FONTS_PATH = "/system/fonts/";
  
  private static final String a = "OnyxCustomFont-Regular";
  
  private static String b;
  
  public static String getOnyxSystemDefaultFontId() {
    return getOnyxSystemDefaultFontPath();
  }
  
  public static String getOnyxSystemDefaultFontPath() {
    String str;
    if ((str = b) != null)
      return str; 
    for (Iterator<String> iterator = Arrays.<String>asList("/system/fonts/OnyxCustomFont-Regular.otf", "/system/fonts/OnyxCustomFont-Regular.ttf")
      
      .iterator(); iterator.hasNext();) {
      String candidate = iterator.next();
      if (FileUtils.fileCanRead(candidate))
        return b = candidate;
    } 
    return b;
  }
  
  public static String getCurrentSystemFont() {
    String str;
    if (StringUtils.isBlank(str = SystemPropertiesUtil.getCurrentFont()) || !FileUtils.fileExist(str))
      str = getOnyxSystemDefaultFontId(); 
    return str;
  }
  
  public static List<String> getCustomFontPathList() {
    ArrayList<String> result = new ArrayList<>();
    result.add(CUSTOMIZE_FONTS_PATH);
    result.addAll(getRemovableSDCardFontPathList());
    return result;
  }
  
  public static List<String> getRemovableSDCardFontPathList() {
    ArrayList<String> result = new ArrayList<>();
    List<File> directories = EnvironmentUtil.getValidRemovableSDCardDirectories();
    if (CollectionUtils.isNullOrEmpty(directories))
      return result;
    for (File file : directories)
      result.add(file.getAbsolutePath() + File.separator + "fonts");
    return result;
  }
  
  public static boolean isCustomizeFontsPath(String path) {
    return CollectionUtils.contains(getCustomFontPathList(), path);
  }
  
  public static List<FontInfo> buildFontItemAdapter(List<String> fontsFolderList, String currentFont, List<String> preferredFonts, DeviceUtils.FontType fontType) {
    FilenameFilter filenameFilter = (dir, filename) -> isFontFile(filename);
    return buildFontItemAdapter(fontsFolderList, currentFont, preferredFonts, fontType, filenameFilter);
  }
  
  public static void fontListCurrentFontHandle(List<FontInfo> fontInfoList, String currentFont) {
    if (StringUtils.isNullOrEmpty(currentFont))
      return; 
    int i = 0;
    while (i < fontInfoList.size()) {
      if (fontInfoList.get(i).getId().contains(currentFont)) {
        fontInfoList.add(0, fontInfoList.remove(i));
        break;
      } 
      i++;
    } 
  }
  
  public static FontInfo loadFontInfo(File f) {
    return loadFontInfo(new TTFFont(), new TTCFont(), f);
  }
  
  public static TTFFont.b parseTtc(TTCFont ttcFont, String filePath) {
    if (!isTtcFile(filePath))
      return null; 
    return ttcFont.parse(filePath).getFontDisplayNameAndFontType();
  }
  
  public static TTFFont.b parseTtf(TTFFont ttfFont, String filePath) {
    ttfFont.parse(filePath);
    return ttfFont.getFontDisplayNameAndFontType();
  }
  
  public static String getFontPath(String name) {
    String str;
    if (FileUtils.fileExist(str = "/system/fonts/" + name))
      return str; 
    return FileUtils.fileExist(str = CUSTOMIZE_FONTS_PATH + File.separator + name) ? str : name;
  }
  
  public static String createCustomizeTtfPath(String name) {
    return CUSTOMIZE_FONTS_PATH + File.separator + name + "." + "ttf";
  }
  
  public static void clearFontInfoCache() {
    d.clear();
  }
  
  public static Map<String, FontInfo> getFontInfoCache() {
    return d;
  }
  
  public static boolean isFontFile(String path) {
    return (isTtfFile(path) || isTtcFile(path) || isOtfFIle(path));
  }
  
  public static boolean isTtfFile(String file) {
    return StringUtils.safelyEqualsIgnoreCase(FileUtils.getFileExtension(file), "ttf");
  }
  
  public static boolean isTtcFile(String file) {
    return StringUtils.safelyEqualsIgnoreCase(FileUtils.getFileExtension(file), "ttc");
  }
  
  public static boolean isOtfFIle(String file) {
    return StringUtils.safelyEqualsIgnoreCase(FileUtils.getFileExtension(file), "otf");
  }
  
  public static boolean isSystemFont(String fontPath) {
    return StringUtils.startsWith(fontPath, "/system/fonts/");
  }
  
  public static boolean isExternalStorageFont(String fontPath) {
    return EnvironmentUtil.isFileOnExternalStorage(fontPath);
  }
  
  public static boolean isRemovableSDCardFont(String fontPath) {
    return FileUtils.isRootDirectoryRemovableSDCard(fontPath);
  }
  
  public static boolean isSerif(String fontId) {
    return StringUtils.safelyEqualsIgnoreCase(fontId, "serif");
  }
  
  public static boolean isFontExist(String fontId) {
    if (isSerif(fontId))
      return true; 
    if (isSystemFont(fontId) || isExternalStorageFont(fontId) || isRemovableSDCardFont(fontId))
      return FileUtils.fileExist(fontId); 
    return false;
  }
  
  public static boolean isCJKFont(String fontName) {
    return CollectionUtils.safelyReverseContains(c, StringUtils.toUpperCaseLocaleInsensitive(fontName));
  }
  
  public static List<FontInfo> buildFontItemAdapter(List<String> fontsFolderList, String currentFont, List<String> preferredFonts, DeviceUtils.FontType fontType, FilenameFilter fontFilter) {
    List<FontInfo> result = new ArrayList<>();
    TTFFont ttfFont = new TTFFont();
    TTCFont ttcFont = new TTCFont();
    for (String folder : fontsFolderList) {
      boolean customizeFolder = isCustomizeFontsPath(folder);
      if (fontType != DeviceUtils.FontType.CUSTOMIZE && customizeFolder) continue;
      if (fontType == DeviceUtils.FontType.CUSTOMIZE && !customizeFolder) continue;
      File[] files = new File(folder).listFiles(fontFilter);
      if (files == null) continue;
      for (File file : files) {
        if (!file.isFile() || file.isHidden()) continue;
        FontInfo info = loadFontInfo(ttfFont, ttcFont, file);
        try {
          if (FileUtils.isSymlink(file)) info.setFilePath(file.getCanonicalPath());
        } catch (IOException exception) {
          exception.printStackTrace();
        }
        if (fontType == DeviceUtils.FontType.ENGLISH && info.getFontType() != DeviceUtils.FontType.ENGLISH) continue;
        if (fontType == DeviceUtils.FontType.CJK && info.getFontType() != DeviceUtils.FontType.CJK) continue;
        boolean preferred = preferredFonts != null
                && (preferredFonts.contains(info.getName()) || preferredFonts.contains(file.getName()));
        if (preferred || info.getFontType() != DeviceUtils.FontType.ENGLISH) result.add(0, info);
        else result.add(info);
      }
    }
    result = a(result);
    a(result, fontType);
    fontListCurrentFontHandle(result, currentFont);
    return result;
  }
  
  private static List<FontInfo> a(List<FontInfo> fontInfoList) {
    LinkedHashMap<String, FontInfo> unique = new LinkedHashMap<>();
    for (FontInfo fontInfo : fontInfoList) {
      if (!unique.containsKey(fontInfo.getFilePath()) || a(fontInfo))
        unique.put(fontInfo.getFilePath(), fontInfo);
    }
    return new ArrayList<>(unique.values());
  }
  
  @Deprecated
  public static String getCurrentSystemFont(int typeIndex) {
    return getCurrentSystemFont();
  }
  
  public static FontInfo loadFontInfo(TTFFont ttfFont, TTCFont ttcFont, File f) {
    String str3 = f.getAbsolutePath();
    FontInfo fontInfo1;
    if ((fontInfo1 = d.get(str3)) != null)
      return fontInfo1; 
    String str2 = ttcFont.getFontUniqueName();
    TTFFont.b b;
    if ((b = parseTtc(ttcFont, str3)) == null) {
      b = parseTtf(ttfFont, str3);
      str2 = ttfFont.getFontUniqueNameAndFontType().a();
    } 
    String str1 = b.a();
    DeviceUtils.FontType fontType = b.b();
    FontInfo fontInfo2 = new FontInfo();
    fontInfo2.setFontType(fontType);
    if (StringUtils.isNullOrEmpty(str1))
      str1 = FileUtils.getBaseName(f); 
    fontInfo2.setFontDisplayName(str1);
    fontInfo2.setSupportCurrentLang(b.c());
    fontInfo2.setId(str3);
    fontInfo2.setFilePath(str3);
    fontInfo2.setFontUniqueName(str2);
    d.put(str3, fontInfo2);
    return fontInfo2;
  }
  
  private static final List<String> c = Arrays.asList("CJK", "JP", "KR");
  
  private static Map<String, FontInfo> d = new ConcurrentHashMap<>();
  
  private static boolean a(FontInfo fontInfo) {
    return StringUtils.safelyEquals(getOnyxSystemDefaultFontId(), fontInfo.getId());
  }
  
  private static void a(List<FontInfo> fontInfoList, DeviceUtils.FontType fontType) {
    if (CollectionUtils.isNullOrEmpty(fontInfoList) || fontType != DeviceUtils.FontType.CUSTOMIZE)
      return; 
    ArrayList<FontInfo> arrayList = new ArrayList<>();
    int i = 0;
    while (i < fontInfoList.size()) {
      if (StringUtils.isCJK(fontInfoList.get(i).getName()))
        arrayList.add(fontInfoList.remove(i--)); 
      i++;
    } 
    Collections.sort(arrayList, (f1, f2) -> ComparatorUtils.stringComparator(f1.getName(), f2.getName(), SortOrder.Asc));
    Collections.sort(fontInfoList, (f1, f2) -> ComparatorUtils.stringComparator(f1.getName(), f2.getName(), SortOrder.Asc));
    fontInfoList.addAll(arrayList);
  }
}
