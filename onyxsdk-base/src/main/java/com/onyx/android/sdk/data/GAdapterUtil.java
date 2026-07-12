package com.onyx.android.sdk.data;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/GAdapterUtil.class */
public final class GAdapterUtil {
    private static final String a = "GAdapterUtil";
    private static HashMap<String, Integer> b;
    public static final String TAG_UNIQUE_ID = "id";
    public static final String TAG_TITLE_RESOURCE = "title_resource";
    public static final String TAG_TITLE_STRING = "title_string";
    public static final String TAG_ORIGIN_TITLE_STRING = "origin_title_string";
    public static final String TAG_SUB_TITLE_RESOURCE = "sub_title";
    public static final String TAG_SUB_TITLE_STRING = "sub_title_string";
    public static final String TAG_IMAGE_RESOURCE = "image_resource";
    public static final String TAG_IMAGE_DRAWABLE = "image_drawable";
    public static final String TAG_LAYOUT_RESOURCE = "layout_resource";
    public static final String TAG_LAYOUT_PARAMS = "layout_params";
    public static final String TAG_LAYOUT_MAPPING = "layout_mapping";
    public static final String TAG_CALLBACK_MAPPING = "callback_mapping";
    public static final String TAG_THUMBNAIL = "thumbnail";
    public static final String TAG_TYPEFACE = "typeface";
    public static final String TAG_EXTRA_ATTRIBUTE = "extra_attribute";
    public static final String TAG_IN_SELECTION = "in_selection";
    public static final String TAG_SELECTED = "selected";
    public static final String TAG_SELECTION_CHECKED_VIEW_ID = "selection_checked_view_id";
    public static final String TAG_SELECTION_UNCHECKED_VIEW_ID = "selection_unchecked_view_id";
    public static final String TAG_COVER_URL = "cover_url";
    public static final String TAG_DATA_URL = "data_url";
    public static final String TAG_ORIGIN_OBJ = "origin_obj";
    public static final String TAG_SELECTABLE = "selectable";
    public static final String TAG_TITLE_ENABLED = "title_enabled";
    public static final String TAG_SUB_TITLE_ENABLED = "sub_title_enabled";
    public static final String TAG_STATUS_LABEL_ENABLED = "status_label_enabled";
    public static final String TAG_DIVIDER_VIEW = "divider_view";
    public static final String TAG_MENU_CLOSE_AFTER_CLICK = "menu_close_after_click";
    public static final String TAG_IMAGE_BACKGROUND = "image_background";
    public static final String TAG_AUTHOR_STRING = "author";
    public static final String TAG_READING_PROGRESS = "reading_progress";
    public static final String TAG_LAST_ACCESS_TIME = "last_access_time";
    public static final String TAG_LAST_MODIFY_TIME = "last_modify_time";
    public static final String TAG_CLOUD_REFERENCE = "cloud_ref";
    public static final String TAG_PARENT_REFERENCE = "parent_ref";
    public static final String TAG_DOCUMENT_TYPE = "document_type";
    public static final String TAG_DOCUMENT_STORAGE_POSITION = "document_storage_position";
    public static final String TAG_DOCUMENT_SIZE = "document_size";
    public static final String TAG_SINGLE_METADATA = "metadata";
    public static final String TAG_DECORATION_VIEW = "decoration_view";
    public static final String FILE_TITLE = "name";
    public static final String FILE_PATH = "path";
    public static final String FILE_SIZE = "size";
    public static final String FILE_TYPE = "type";
    public static final String FILE_FILE = "file";
    public static final String FILE_DIRECTORY = "dir";
    public static final String FILE_LIBRARY = "lib";
    public static final String GO_UP_TYPE = "up";

    public static GObject createThumbnailItem(int titleResource, int subTitleResource, int imageResource, int layoutResource, Map<String, Integer> layoutMapping) {
        GObject gObject = new GObject();
        gObject.putObject(TAG_TITLE_RESOURCE, Integer.valueOf(titleResource));
        gObject.putObject(TAG_SUB_TITLE_RESOURCE, Integer.valueOf(subTitleResource));
        gObject.putObject(TAG_IMAGE_RESOURCE, Integer.valueOf(imageResource));
        gObject.putInt(TAG_LAYOUT_RESOURCE, layoutResource);
        gObject.putNonNullObject(TAG_LAYOUT_MAPPING, layoutMapping);
        return gObject;
    }

    public static GObject createTableItem(int titleResource, int subTitleResource, int imageResource, int layoutResource, Map<String, Integer> layoutMapping) {
        GObject gObject = new GObject();
        gObject.putObject(TAG_TITLE_RESOURCE, Integer.valueOf(titleResource));
        gObject.putObject(TAG_SUB_TITLE_RESOURCE, Integer.valueOf(subTitleResource));
        gObject.putObject(TAG_IMAGE_RESOURCE, Integer.valueOf(imageResource));
        gObject.putInt(TAG_LAYOUT_RESOURCE, layoutResource);
        gObject.putNonNullObject(TAG_LAYOUT_MAPPING, layoutMapping);
        return gObject;
    }

    public static int getLayoutResource(GObject object) {
        return object.getInt(TAG_LAYOUT_RESOURCE);
    }

    public static ViewGroup.LayoutParams getLayoutParams(GObject object) {
        return (ViewGroup.LayoutParams) object.getObject(TAG_LAYOUT_PARAMS);
    }

    public static Map<String, Integer> getLayoutMapping(GObject object) {
        return (Map) object.getObject(TAG_LAYOUT_MAPPING);
    }

    public static GObject objectFromFile(File file) {
        GObject gObject = new GObject();
        gObject.putString(TAG_TITLE_STRING, file.getName());
        gObject.putString("path", file.getAbsolutePath());
        gObject.putLong(FILE_SIZE, file.length());
        gObject.putString("type", "file");
        gObject.putBoolean(TAG_SELECTABLE, false);
        return gObject;
    }

    public static GObject objectFromDirectory(File file) {
        GObject gObject = new GObject();
        gObject.putString(TAG_TITLE_STRING, file.getName());
        gObject.putString("path", file.getAbsolutePath());
        gObject.putString("type", FILE_DIRECTORY);
        gObject.putBoolean(TAG_SELECTABLE, false);
        return gObject;
    }

    public static void sortByKey(GAdapter adapter, String key) {
    }

    public static boolean hasFilePath(GObject object) {
        return object.hasKey("path");
    }

    public static File getFilePath(GObject object) {
        return new File(object.getString("path"));
    }

    public static boolean isFile(GObject object) {
        return object.getString("type").equalsIgnoreCase("file");
    }

    public static boolean isGoUp(GObject object) {
        return object.getString("type").equals(GO_UP_TYPE);
    }

    public static boolean isDirectory(GObject object) {
        return object.getString("type").equalsIgnoreCase(FILE_DIRECTORY);
    }

    public static boolean isSubLibrary(GObject object) {
        return object.hasKey("type") && object.getString("type").equals(FILE_LIBRARY);
    }

    public static boolean hasThumbnail(GObject object) {
        Object object2;
        return object != null && object.hasKey("thumbnail") && (object2 = object.getObject("thumbnail")) != null && (object2 instanceof Bitmap);
    }

    public static void updateAdapterContent(GAdapter originalAdapter, GAdapter newDataAdapter, String key, ArrayList<String> valueTagList, Map<String, Integer> lookupTable, boolean isReplaceOldObject) {
        int iIntValue;
        if (isNullOrEmpty(newDataAdapter)) {
            return;
        }
        for (int i = 0; i < newDataAdapter.size(); i++) {
            GObject gObject = newDataAdapter.get(i);
            String string = gObject.getString(key);
            if (lookupTable.containsKey(string) && (iIntValue = lookupTable.get(string).intValue()) >= 0 && iIntValue < originalAdapter.size()) {
                if (isReplaceOldObject) {
                    originalAdapter.getList().set(iIntValue, gObject);
                } else {
                    GObject gObject2 = originalAdapter.getList().get(iIntValue);
                    for (String str : valueTagList) {
                        gObject2.putObject(str, gObject.getObject(str));
                    }
                    originalAdapter.getList().set(iIntValue, gObject2);
                }
            }
        }
    }

    public static int getUniqueIdAsIntegerType(GObject object) {
        return object.getInt(TAG_UNIQUE_ID);
    }

    public static String getUniqueId(GObject object) {
        return object.getString(TAG_UNIQUE_ID);
    }

    public static boolean hasCoverUrl(GObject object) {
        return object.hasKey(TAG_COVER_URL);
    }

    public static String getCoverUrl(GObject object) {
        return object.getString(TAG_COVER_URL);
    }

    public static String getDataUrl(GObject object) {
        return object.getString(TAG_DATA_URL);
    }

    public static final Object getOriginObject(GObject object) {
        return object.getObject(TAG_ORIGIN_OBJ);
    }

    public static int indexOf(List<GObject> list, GObject object, String tag) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getObject(tag).equals(object.getObject(tag))) {
                return i;
            }
        }
        return -1;
    }

    public static List<String> toFileList(GAdapter adapter) {
        return toFileList(adapter.getList());
    }

    public static boolean isNullOrEmpty(GAdapter adapter) {
        return adapter == null || adapter.getList() == null || adapter.size() <= 0;
    }

    public static boolean isEqual(GObject source, GObject target, String tag) {
        if ((source == null && target == null) || source == null || target == null) {
            return false;
        }
        Object object = source.getObject(tag);
        Object object2 = target.getObject(tag);
        if (object == null || object2 == null) {
            return false;
        }
        return object.equals(object2);
    }

    public static List<String> toFileList(List<GObject> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<GObject> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(getFilePath(it.next()).getAbsolutePath());
        }
        return arrayList;
    }

    public static List<String> toFileList(GObject object) {
        ArrayList arrayList = new ArrayList();
        if (object == null) {
            return arrayList;
        }
        arrayList.add(getFilePath(object).getAbsolutePath());
        return arrayList;
    }

    public static GObject createThumbnailItem(String titleString, String subTitleString, int imageResource, int layoutResource, Map<String, Integer> layoutMapping) {
        GObject gObject = new GObject();
        gObject.putObject(TAG_TITLE_STRING, titleString);
        gObject.putObject(TAG_SUB_TITLE_STRING, subTitleString);
        gObject.putObject(TAG_IMAGE_RESOURCE, Integer.valueOf(imageResource));
        gObject.putInt(TAG_LAYOUT_RESOURCE, layoutResource);
        gObject.putNonNullObject(TAG_LAYOUT_MAPPING, layoutMapping);
        return gObject;
    }

    public static GObject createTableItem(String title, String subTitle, int imageResource, int layoutResource, Map<String, Integer> layoutMapping) {
        GObject gObject = new GObject();
        gObject.putNonNullObject(TAG_TITLE_STRING, title);
        gObject.putNonNullObject(TAG_SUB_TITLE_STRING, subTitle);
        gObject.putObject(TAG_IMAGE_RESOURCE, Integer.valueOf(imageResource));
        gObject.putInt(TAG_LAYOUT_RESOURCE, layoutResource);
        gObject.putNonNullObject(TAG_LAYOUT_MAPPING, layoutMapping);
        return gObject;
    }

    public static GObject createTableItem(String title, String subTitle, int imageResource, int layoutResource, Map<String, Integer> layoutMapping, Map<Integer, Map<Class<?>, Object>> callbackMapping) {
        GObject gObject = new GObject();
        gObject.putNonNullObject(TAG_TITLE_STRING, title);
        gObject.putNonNullObject(TAG_SUB_TITLE_STRING, subTitle);
        gObject.putObject(TAG_IMAGE_RESOURCE, Integer.valueOf(imageResource));
        gObject.putInt(TAG_LAYOUT_RESOURCE, layoutResource);
        gObject.putNonNullObject(TAG_LAYOUT_MAPPING, layoutMapping);
        gObject.putNonNullObject(TAG_CALLBACK_MAPPING, callbackMapping);
        return gObject;
    }

    public static void updateAdapterContent(GAdapter originalAdapter, GAdapter newDataAdapter, String key, Map<String, Integer> lookupTable) {
        updateAdapterContent(originalAdapter, newDataAdapter, key, (ArrayList<String>) new ArrayList(), lookupTable, true);
    }

    public static GObject createTableItem(String title, String subTitle, int imageResource, int layoutResource) {
        GObject gObject = new GObject();
        gObject.putNonNullObject(TAG_TITLE_STRING, title);
        gObject.putNonNullObject(TAG_SUB_TITLE_STRING, subTitle);
        gObject.putNonNullObject(TAG_IMAGE_RESOURCE, Integer.valueOf(imageResource));
        gObject.putInt(TAG_LAYOUT_RESOURCE, layoutResource);
        return gObject;
    }

    public static void updateAdapterContent(GAdapter originalAdapter, GAdapter newDataAdapter, String key, String valueTag, Map<String, Integer> lookupTable, boolean isReplaceOldObject) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(valueTag);
        updateAdapterContent(originalAdapter, newDataAdapter, key, (ArrayList<String>) arrayList, lookupTable, isReplaceOldObject);
    }

    public static GObject createTableItem(String title, String subTitle, Drawable imageDrawable, int layoutResource, Map<String, Integer> layoutMapping) {
        GObject gObject = new GObject();
        gObject.putObject(TAG_TITLE_STRING, title);
        gObject.putObject(TAG_SUB_TITLE_STRING, subTitle);
        gObject.putObject(TAG_IMAGE_DRAWABLE, imageDrawable);
        gObject.putInt(TAG_LAYOUT_RESOURCE, layoutResource);
        gObject.putNonNullObject(TAG_LAYOUT_MAPPING, layoutMapping);
        return gObject;
    }

    public static GObject createTableItem(String title, String subTitle, int imageResource, int layoutResource, int selectionCheckedViewId, int selectionUncheckedViewId, Map<String, Integer> layoutMapping) {
        GObject gObject = new GObject();
        gObject.putNonNullObject(TAG_TITLE_STRING, title);
        gObject.putNonNullObject(TAG_SUB_TITLE_STRING, subTitle);
        gObject.putInt(TAG_IMAGE_RESOURCE, imageResource);
        gObject.putInt(TAG_LAYOUT_RESOURCE, layoutResource);
        gObject.putInt(TAG_SELECTION_CHECKED_VIEW_ID, selectionCheckedViewId);
        gObject.putInt(TAG_SELECTION_UNCHECKED_VIEW_ID, selectionUncheckedViewId);
        gObject.putNonNullObject(TAG_LAYOUT_MAPPING, layoutMapping);
        return gObject;
    }

    public static GObject createTableItem(String title, String subTitle, Drawable imageDrawable, int layoutResource, int selectionCheckedViewId, int selectionUncheckedViewId, Map<String, Integer> layoutMapping) {
        GObject gObject = new GObject();
        gObject.putNonNullObject(TAG_TITLE_STRING, title);
        gObject.putNonNullObject(TAG_SUB_TITLE_STRING, subTitle);
        gObject.putObject(TAG_IMAGE_DRAWABLE, imageDrawable);
        gObject.putInt(TAG_LAYOUT_RESOURCE, layoutResource);
        gObject.putInt(TAG_SELECTION_CHECKED_VIEW_ID, selectionCheckedViewId);
        gObject.putInt(TAG_SELECTION_UNCHECKED_VIEW_ID, selectionUncheckedViewId);
        gObject.putNonNullObject(TAG_LAYOUT_MAPPING, layoutMapping);
        return gObject;
    }
}
