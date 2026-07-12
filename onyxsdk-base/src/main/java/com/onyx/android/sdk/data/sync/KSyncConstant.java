package com.onyx.android.sdk.data.sync;

import android.os.Build.VERSION;
import com.onyx.android.sdk.api.utils.NetworkUtil;
import com.onyx.android.sdk.path.KSyncFilePaths;
import com.onyx.android.sdk.utils.FileUtils;
import com.onyx.android.sdk.utils.ResManager;
import com.onyx.android.sdk.utils.StringUtils;
import java.io.File;
import java.util.Iterator;

public class KSyncConstant {
   public static final String NOTE_TREE_DB_NAME = "NOTE_TREE";
   public static final String GROUP_TREE_DB_NAME = "GROUP_TREE";
   public static final String CALENDAR_LIBRARY_DB_NAME = "CALENDAR_TREE";
   public static final String READER_LIBRARY_DB_NAME = "READER_LIBRARY";
   public static final String CONFIG_DB_NAME = "CONFIG";
   public static final String APP_DATA_DB_NAME = "APP_DATA";
   public static final String MESSAGE_DB_NAME = "MESSAGE";
   public static final String CLOUD_CONFIG_CHANNEL = "CloudConfig";
   public static final String CLOUD_CONFIG_3288_CHANNEL = "CloudConfig-3288";
   public static final String DEVICE_CHANNEL = "DEVICE";
   public static final String FAMILY_CHANNEL = "FAMILY";
   public static final int NOTE_DOC_REPLICATE_RETRY_LIMIT_COUNT = 3;
   public static final int PULL_NOTE_SHAPE_RETRY_LIMIT_COUNT = 3;
   public static final int PUSH_NOTE_SHAPE_RETRY_LIMIT_COUNT = 3;
   public static final int COMPUTE_EMBED_SHAPE_SAVE_COUNT = 100;
   public static final int DELAY_START_REPLICATOR_TIME_MS = 1000;
   public static final int DELAY_WAIT_REPLICATOR_TIME_MS = 1000;
   public static final int PUSH_PULL_NOTE_TREE_LIMIT_COUNT_ONCE = 20;
   public static final int PUSH_PULL_READER_DATA_LIMIT_COUNT_ONCE = 500;
   public static final int OSS_QUERY_MAX_KEYS = 1000;
   public static final int CLEAR_EMBED_DATA_LIMIT_COUNT_ONCE = 1000;
   public static final int DELETE_OSS_FILE_COUNT_ONCE = 1000;
   public static final int HANDLE_PULL_DOC_COUNT_ONCE = 1000;
   public static final int HANDLE_PULL_MESSAGE_COUNT_ONCE = 10;
   public static final int REPLICATOR_RETRY_WAIT_TIME_MS = 3000;
   public static final int IMPORT_MESSAGE_COUNT_ONCE = 1000;
   public static final int SEND_MESSAGE_COUNT_ONCE = 10;
   public static final String KSYNC_SERVICE_PACKAGE_NAME = "com.onyx.android.ksync";
   public static final String PHONE_BOOX_PACKAGE_NAME = "com.boox_helper";
   public static final String KSYNC_SERVICE_CLASS_NAME = "com.onyx.android.ksync.service.KSyncService";
   public static final String KPDF_SERVICE_CLASS_NAME = "com.onyx.android.ksync.service.KPdfService";
   public static final String KNOTE_PACKAGE_NAME = "com.onyx.android.note";
   public static final String KNOTE_START_QUICKLY_ACTIVITY_NAME = "com.onyx.android.note.note.ui.CreateQuickNoteActivity";
   public static final String KNOTE_SERVICE_PACKAGE_NAME = "com.onyx.android.note.note.NoteService";
   public static final String APP_MARKET_PACKAGE_NAME = "com.onyx.appmarket";
   public static final String APP_MARKET_SERVICE_PACKAGE_NAME = "com.onyx.appmarket.service.AppCheckUpdateService";
   public static final String AI_ASSISTANT_PACKAGE_NAME = "com.onyx.aiassistant";
   public static final String AI_ASSISTANT_FLOATING_SERVICE_CLASS_NAME = "com.onyx.aiassistant.service.AIAssistantFloatingService";
   public static final String KNOTE_SCOPE_VALUE = "com.onyx.android.note";
   public static final String KREADER_SCOPE_VALUE = "com.onyx.kreader";
   public static final String KREADER_ANNOTATION_SCOPE_VALUE = "com.onyx.kreader.annotation";
   public static final String KMAIL_SCOPE_VALUE = "com.onyx.mail";
   public static final String KREADER_OSS_DIR = "reader";
   public static final String KREADER_USER_DATA_DIR = "userdata";
   public static final String KREADER_LIBRARY_DIR = "library";
   public static final String KCALENDAR_OSS_DIR = "calendar";
   public static final String KNOTE_OSS_DIR = "note";
   public static final String GROUP_NOTE = "groupNotes";
   public static final String NOTE_TREE_PB_PREFIX = "note_tree_";
   public static final String TAG_TREE_PB_PREFIX = "tag_tree_";
   public static final String COUCH_SYNC_COOKIE_DEFAULT_NAME = "SyncGatewaySession";
   public static final String KSYNC_LOCAL_POINT_DIR_NAME = "point";
   public static final String KSYNC_LOCAL_RESOURCE_DIR_NAME = "resource";
   public static final String KSYNC_LOCAL_LINK_DIR_NAME = "link";
   public static final String KSYNC_LOCAL_PAGE_DIR_NAME = "page";
   public static final String KSYNC_LOCAL_DOC_DIR_NAME = "doc";
   public static final String KSYNC_LOCAL_TOC_DIR_NAME = "toc";
   public static final String KSYNC_LOCAL_TAG_DIR_NAME = "tag";
   public static final String KSYNC_LOCAL_NOTE_DIR_NAME = "note";
   public static final String KSYNC_LOCAL_EXTRA_DIR_NAME = "extra";
   public static final String KSYNC_LOCAL_CACHE_DIR_NAME = "cache";
   public static final String KSYNC_EMBED_PDF_DIR_NAME = "pdf";
   public static final String KSYNC_VIRTUAL_DIR_NAME = "virtual";
   public static final String KSYNC_EMBED_DATA_DIR_NAME = "embed";
   public static final String KSYNC_DOCUMENT_DIR_NAME = "document";
   public static final String KSYNC_SHAPE_DIR_NAME = "shape";
   public static final String KSYNC_PB_DIR_NAME = "pb";
   public static final String KSYNC_DATA_DIR_NAME = "data";
   public static final String KSYNC_LOG_DIR_NAME = "log";
   public static final String KSYNC_NETDISK_DIR_NAME = "NetDisk";
   public static final String KSYNC_OSS_POINT_DIR_NAME = "point";
   public static final String KSYNC_OSS_RESOURCE_DIR_NAME = "resource";
   public static final String KSYNC_OSS_THUMBNAIL_DIR_NAME = "thumbnail";
   public static final String KSYNC_OSS_BOOK_DIR_NAME = "book";
   public static final String KSYNC_COUCH_DATABASE_DIR_NAME = "couch";
   public static final String KSYNC_BACKUP_DIR_NAME = "Backup";
   public static final String KSYNC_CONFIG_BACKUP_NAME = "config-backup";
   public static final String KSYNC_CONFIG_BACKUP_FILE_EXTENSION = ".cblite2.zip";
   public static final String TEMPLATE_DIR_NAME = "template";
   public static final String JSON_DIR_NAME = "json";
   public static final String TEMPLATE_SUFFIX = "template_json";
   public static final String SYNC_NOTE_DATA_PROTO_FILE = "sync_note_data_proto_file";
   public static final String KSYNC_REAL_EXTERNAL_FILES_PATH_FOR_BELOW_ANDROID11 = "data/media/0/Android/data/com.onyx.android.ksync";
   public static final String KSYNC_EMBED_HWR_TEXT_CACHE_DIR_NAME = "hwr_text";
   public static final String KSYNC_AI_ASSISTANT_DIR_NAME = "aiassistant";
   public static final String KSYNC_AI_READING_DIR_NAME = "reading";
   public static final String TEMP_ENCRYPT = "temp_encrypt_dir";
   public static final String KSYNC_RICH_TEXT_HISTORY_DIR_NAME = "history";
   public static final String KPDF_EMBED_BEAN_KEY = "KPDF_EMBED_BEAN_KEY";
   public static final int KSYNC_REPORT_REQUEST_TIME_THRESHOLD = 2000;
   public static final String COUCH_DB_FULL_NAME_SPLIT_CHAR = "-";
   public static final String COUCH_ID_SPLIT_CHAR = "#";
   public static final int EMBED_COMPUTE_SHAPE_COUNT_LIMIT_ONCE = 500;
   public static final int LOAD_EMBED_RECORD_COUNT_LIMIT_ONCE = 1000;
   public static final String EMBED_RECORD_DATABASE_NAME_SUFFIX = "_EmbedRecord";
   public static final String EMBED_DATA_DATABASE_NAME_SUFFIX = "_EmbedData";
   public static final long NOT_MIGRATE_NOTE_FILE_SIZE_VALUE = -1L;
   public static final String KSYNC_SETTING_SCOPE = "KSYNC_SETTING_SCOPE";
   public static final String PUSH_CONFIG_KEY = "PUSH_CONFIG_KEY";
   public static final float RECOMPUTE_SHAPE_RECT_SIZE_THRESHOLD = 3.0F;
   public static final String KEY_DOCUMENT_ID = "KEY_DOCUMENT_ID";
   public static final String KEY_PAGE_ID = "KEY_PAGE_ID";
   public static final String KEY_REVISION_ID = "KEY_REVISION_ID";
   public static final String KEY_SYNC_TYPE = "KEY_SYNC_TYPE";
   public static final String KEY_SYNC_STATUS = "KEY_SYNC_STATUS";
   public static final String PB_EXTRA_FILE_NAME = "extra";
   public static final String EXPORT_NOTE_EXTENSION = ".note";
   public static final int POST_DOC_PROGRESS_FILTER_TIME_MS = 1000;
   public static final int OSS_GET_OBJECT_LIST_DEFAULT_RETRY_TIMES = 2;
   public static final int MAX_RETRY_COUNT_WHEN_INTERNAL_SERVER_ERROR = 3;
   public static final long SAVE_TO_CLOUD_INTERVAL_TIME_LIMIT = 259200000L;
   public static final int MESSAGE_QUERY_LIMIT = 1000;
   public static final boolean groupSyncEnabled = false;

   public static boolean isNoteFile(String fileName) {
      return StringUtils.safelyEquals(FileUtils.getFileExtension(fileName).toLowerCase(), FileUtils.getFileExtension(".note"));
   }

   public static String getNetDiskDirPath() {
      String var10000 = netDiskDirPath();
      FileUtils.mkdirs(var10000);
      return var10000;
   }

   public static String netDiskDirPath() {
      return KSyncFilePaths.INSTANCE.getKSyncFilesPath() + File.separator + "NetDisk";
   }

   public static String getAnonymousUser() {
      String var0;
      return StringUtils.isNullOrEmpty(var0 = getRawAnonymousUser()) ? "" : var0.replaceAll(":", "");
   }

   public static String getRawAnonymousUser() {
      return NetworkUtil.getMacAddress(ResManager.getAppContext());
   }

   public static String getCloudConfigChannel() {
      return use3288CloudConfig() ? "CloudConfig-3288" : "CloudConfig";
   }

   public static boolean use3288CloudConfig() {
      return VERSION.SDK_INT <= 23;
   }

   public static String docDirFilePath(String documentId) {
      return syncDocDirFilePath() + File.separator + documentId;
   }

   public static String docDirFilePath(String dirPath, String documentId) {
      return dirPath + File.separator + documentId;
   }

   public static String syncDocDirFilePath() {
      return KSyncFilePaths.INSTANCE.getKSyncFilesPath() + File.separator + "document";
   }

   public static String getRelativePathForKSyncFile(String path) {
      if (StringUtils.isNullOrEmpty(path)) {
         return "";
      }

      Iterator var1 = KSyncFilePaths.INSTANCE.getKSyncRootDirList().iterator();

      while (var1.hasNext()) {
         String var2;
         if (StringUtils.startsWithIgnoreCase(path, var2 = (String)var1.next())) {
            return StringUtils.subStartString(path, var2.length());
         }
      }

      return "";
   }

   public static String dbFullName(String userId, String docId) {
      return userId + "-" + docId;
   }

   public static String getPointDirPath() {
      String var10000 = KSyncFilePaths.INSTANCE.getKSyncFilesPath() + File.separator + "point";
      FileUtils.mkdirs(var10000);
      return var10000;
   }

   public static String getReadingAssistantDirPath() {
      StringBuilder var10000 = new StringBuilder().append(KSyncFilePaths.INSTANCE.getKSyncFilesPath());
      String var0 = File.separator;
      String var1 = var10000.append(File.separator).append("aiassistant").append(var0).append("reading").toString();
      FileUtils.mkdirs(var1);
      return var1;
   }
}
