package com.onyx.android.sdk.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.onyx.android.sdk.common.provider.OnyxFileProviderUtil;
import com.onyx.android.sdk.data.reader.BookInfoBean;
import com.onyx.android.sdk.reader.ReaderServiceBundle;
import java.io.File;

public class ReaderUtils {
    public static final String PACKAGE_NAME = "com.onyx.kreader";
    public static final String READER_VIEW_SERVICE_CLASS = "com.onyx.android.sdk.readerview.service.ReaderService";
    public static final String READER_HOME_ACTIVITY_CLASS = "com.onyx.kreader.ui.ReaderHomeActivity";
    public static final String READER_TAB_1_WAKE_UP_SERVICE_CLASS = "com.onyx.android.sdk.readerview.service.ReaderTab1WakeupService";
    public static final String READER_TAB_2_WAKE_UP_SERVICE_CLASS = "com.onyx.android.sdk.readerview.service.ReaderTab2WakeupService";
    public static final String READER_TAB_3_WAKE_UP_SERVICE_CLASS = "com.onyx.android.sdk.readerview.service.ReaderTab3WakeupService";
    public static final String READER_TAB_4_WAKE_UP_SERVICE_CLASS = "com.onyx.android.sdk.readerview.service.ReaderTab4WakeupService";

    public static boolean isKReader(String packageName) {
        return StringUtils.safelyEquals(PACKAGE_NAME, packageName);
    }

    public static void openFile(String filePath) {
        Intent intent = new Intent()
                .setComponent(new ComponentName(PACKAGE_NAME, READER_VIEW_SERVICE_CLASS))
                .setAction(Intent.ACTION_VIEW);
        OnyxFileProviderUtil.setIntentData(ResManager.getAppContext(), intent, new File(filePath), true);
        ReaderServiceBundle.getInstance().sendMessage(intent);
    }

    public static boolean openDocByReaderService(Context context, String path, @NonNull BookInfoBean bookInfoBean) {
        try {
            Intent intent = new Intent()
                    .setComponent(new ComponentName(PACKAGE_NAME, READER_VIEW_SERVICE_CLASS))
                    .setAction(Intent.ACTION_VIEW)
                    .putExtra("book_info", JSONUtils.toJson(bookInfoBean));
            OnyxFileProviderUtil.setIntentData(context, intent, new File(path), true);
            ReaderServiceBundle.getInstance().sendMessage(intent);
            Debug.d(ReaderUtils.class, " startReaderService, " + intent, new Object[0]);
            return true;
        } catch (Exception failure) {
            Debug.e(failure);
            return false;
        }
    }

    public static boolean openDocByReaderActivity(Context context, String path, @NonNull BookInfoBean bookInfoBean) {
        Intent intent = new Intent()
                .setComponent(new ComponentName(PACKAGE_NAME, READER_HOME_ACTIVITY_CLASS))
                .setAction(Intent.ACTION_VIEW)
                .putExtra("book_info", JSONUtils.toJson(bookInfoBean));
        OnyxFileProviderUtil.setIntentData(context, intent, new File(path), true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ActivityUtil.startActivitySafely(context, intent);
        return true;
    }
}
