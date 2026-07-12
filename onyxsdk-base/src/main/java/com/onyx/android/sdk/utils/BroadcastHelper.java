package com.onyx.android.sdk.utils;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.UserHandle;
import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.onyx.android.sdk.data.AppDataInfo;
import com.onyx.android.sdk.data.FeedbackArgsBean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BroadcastHelper {
    public static final String ONYX_BASE_ACTION_PREFIX = "onyx.action";
    public static final String ARGS_OPERATION_FLAG = "args_operation_flag";
    public static final String EAC_CLOUD_RESULT_ACTION = "com.onyx.floatingbutton.setting.eac.cloud_result";
    public static final String FLOAT_BUTTON_TOUCH_ACTION = "com.onyx.floatingbutton.touch";
    public static final String FLOAT_BUTTON_HOVER_ACTION = "com.onyx.floatingbutton.hover";
    public static final String RECEIVER_INTENT_DELETE_ACTION = "com.onyx.intent.action.delete";
    public static final String RECEIVER_INTENT_RENAME_ACTION = "com.onyx.intent.action.rename";
    public static final String RECEIVER_INTENT_COPY_ACTION = "com.onyx.intent.action.copy";
    public static final String RECEIVER_INTENT_CUT_ACTION = "com.onyx.intent.action.cut";
    public static final String RECEIVER_INTENT_SHOP_REFRESH_LIBRARY_ACTION = "com.onyx.intent.action.shop.refreshLibrary";
    public static final String SCREENSHOTS_SHOW_UI_KEY = "show_ui";
    public static final String SCREENSHOTS_ACTION = "onyx.android.intent.screenshot";
    public static final String REBOOT_ACTION = "onyx.android.intent.reboot";
    public static final String SHUTDOWN_ACTION = "onyx.android.intent.shutdown";
    public static final String SYSTEM_UI_SCREEN_SHOT_END_ACTION = "com.android.systemui.SYSTEM_UI_SCREEN_SHOT_END_ACTION";
    public static final String EAC_BRIGHTNESS_ACTION = "action.show.brightness.dialog";
    public static final String EAC_RECENT_SCREEN_ACTION = "toggle_recent_screen";
    public static final String EAC_SHOW_EINK_CENTER = "action.open.eink.center.request";
    public static final String EAC_SHOW_REFRESHMODE_DIALOG = "com.onyx.action.show.refreshmode.dialog";
    public static final String SHOW_GLOBAL_DIALOG_ACTION = "onyx.android.show.global.dialog";
    public static final String FLOAT_OPEN_APP_ACTION = "com.onyx.floatbutton.open.apps";
    public static final String FLOAT_SHOW_SYSTEM_STATUS_BAR_ACTION = "com.onyx.floatbutton.show.system.status.bar";
    public static final String FLOAT_TASK_CLEANUP_ACTION = "com.onyx.recent.task.cleanup";
    public static final String FLOAT_OPEN_APP_FREEZE_ACTION = "com.onyx.app.freeze.page";
    public static final String RELOAD_APP_ICON_ACTION = "reload.app.icon";
    public static final String FLOAT_BUTTON_ENABLE_ACTION = "com.onyx.floatingbutton.enable";
    public static final String FLOAT_BUTTON_DISABLE_ACTION = "com.onyx.floatingbutton.disable";
    public static final String FLOAT_BUTTON_MENU_STATE_CHANGED_ACTION = "com.onyx.floatingbutton.menu_state_changed";
    public static final String TOGGLE_SCROLL_BUTTON_ACTION = "com.onyx.floatingbutton.toggle_scroll_button";
    public static final String SCROLL_GESTURE_ACTION = "onyx.action.SCROLL_GESTURE";
    public static final String BRIGHTNESS_VALUE_CHANGE_ACTION = "brightness_value_change_action";
    public static final String REFRESH_MODE_CHANGED_ACTION = "com.onyx.action.REFRESH_MODE_CHANGED";
    public static final String ACTION_OPEN_BRIGHTNESS = "onyx.action.OPEN_BRIGHTNESS";
    public static final String ACTION_CLOSE_BRIGHTNESS = "onyx.action.CLOSE_BRIGHTNESS";
    public static final String ACTION_SHOW_KEYBOARD_SHORTCUTS = "onyx.action.SHOW_KEYBOARD_SHORTCUTS_ACTION";
    public static final String SET_DEVICE_OWNER_ACTION = "com.onyx.action.DPM_SET_OWNER";
    public static final String KEY_NAME = "name";
    public static final String KEY_JSON = "json";
    public static final String KEY_JSON_FILE_PATH = "json_file_path";
    public static final String KEY_PB_FILE_PATH = "pb_file_path";
    public static final String KEY_EXPORT_ALL = "export_all";
    public static final String ARGS_EAC_USER_UPDATE_DATA_TYPE_KEY = "args_eac_user_update_data_type";
    public static final String FLOAT_BUTTON_STATUS = "floatbutton_status";
    public static final String FLOAT_BUTTON_MENU_STATE = "floatbutton_menu_state";
    public static final String ACTIVITY_RESUME_ACTION = "action.activity.resume";
    public static final String ACTIVITY_PAUSE_ACTION = "action.activity.pause";
    public static final String ACTIVITY_FINISH_ACTION = "action.activity.finish";
    public static final String VIEW_EPD_UPDATE_ACTION = "action.view.epd.update";
    public static final String ACTION_EAC_CONFIG = "action.eac.config";
    public static final String INPUT_EVENT_ACTION = "action.input.event";
    public static final String SF_DEBUG_ACTION = "action.sf.debug";
    public static final String OEC_SERVICE_CONFIG_DATA_CHANGE_ACTION = "onyx.action.oec_service_data_changed";
    public static final String EAC_ITEM_CHANGED_ACTION = "com.onyx.floatingbutton.setting.eac.item.changed";
    public static final String EAC_RESET_ACTION = "com.onyx.floatingbutton.setting.eac.reset";
    public static final String EAC_CLOSE_ACTION = "com.onyx.floatingbutton.setting.eac.close";
    public static final String EAC_CLOUD_ACTION = "com.onyx.floatingbutton.setting.eac.cloud";
    public static final String FLOAT_BUTTON_START_APPLICATION_ACTION = "com.onyx.floatbutton.open.apps";
    public static final String EAC_TOP_COMPONENT_CHANGE_ACTION = "onyx.action.top.component.change";
    public static final String ONYX_SYSTEM_CONFIG_CHANGED = "com.onyx.action.system.config.changed";
    public static final String ONYX_SYSTEM_SPLIT_SCREEN_ROTATION = "com.onyx.action.system.split.screen.rotation";
    public static final String ONYX_LAUNCHER_BADGE_ACTION = "com.onyx.action.LAUNCHER_BADGE";
    public static final String ONYX_SILENT_UNINSTALL_ACTION = "com.onyx.action.ACTION_SILENT_UNINSTALL";
    public static final String ONYX_SILENT_UNINSTALL_COMPLETED_ACTION = "onyx.action.slient.uninstall.completed";
    public static final String ONYX_SETTING_ACTION = "com.onyx.action.SETTING";
    public static final String SETTING_CHILD_APP_MANAGEMENT_ACTION = "com.setting.action.CHILD_APP_MANAGEMENT";
    public static final String SETTING_CHILD_LIBRARY_MANAGEMENT_ACTION = "com.setting.action.CHILD_LIBRARY_MANAGEMENT";
    public static final String ONYX_ACTION_INPUT_POINTER_CONFIG = "onyx.action.input.pointer_config";
    public static final String ONYX_UNMOUNT_EXT_MEDIA_ACTION = "com.onyx.action.UNMOUNT_EXT_MEDIA";
    public static final String ONYX_SHOW_ROTATION_LOCK_DIALOG_ACTION = "com.onyx.action.SHOW_ROTATION_LOCK_DIALOG_ACTION";
    public static final String ONYX_NOTE_ADD_TEST_SHAPE = "com.onyx.android.note.test.shape";
    public static final String ONYX_NOTE_ADD_TEST_CUSTOM_SHAPE = "com.onyx.android.note.test.add_custom_shape";
    public static final String ONYX_NOTE_TEST_CHANGE_PAGE = "com.onyx.android.note.test.change_page";
    public static final String ONYX_NOTE_TOGGLE_DEBUG = "com.onyx.android.note.toggle.debug";
    public static final String ONYX_NOTE_CHECK_SYNC_DATA = "com.onyx.android.note.sync.check";
    public static final String ONYX_TOGGLE_PEN_TYPE_ACTION = "action.toggle.pen.type";
    public static final String ONYX_REFRESH_SCREEN_ACTION = "onyx.android.intent.action.REFRESH_SCREEN";
    public static final String ONYX_KREADER_NOTE_ADD_TEST_SHAPE = "com.onyx.kreader.test.shape";
    public static final String ONYX_KREADER_ADD_TEST_ANNOTATION = "com.onyx.kreader.test.annotation";
    public static final String ONYX_KREADER_TEST_GENERATE_EPUB = "com.onyx.kreader.test.generate.epub";
    public static final String ONYX_KREADER_ADD_TEST_BOOKMARK = "com.onyx.kreader.test.bookmark";
    public static final String ONYX_KREADER_EMBED_PDF_STATUS_ACTION = "com.onyx.kreader.action.EMBED_PDF_STATUS";
    public static final String ONYX_KREADER_DOCUMENT_SAVED_ACTION = "com.onyx.kreader.action.DOCUMENT_SAVED";
    public static final String ONYX_KREADER_DOCUMENT_ENABLE_ACTION = "com.onyx.kreader.action.DOCUMENT_ENABLE";
    public static final String ONYX_KREADER_DIRTY_NOTE_CACHE = "com.onyx.kreader.action.DIRTY_NOTE_CACHE";
    public static final String ONYX_KSYNC_APP_DEBUG = "com.onyx.android.ksync.debug";
    public static final String ONYX_KSYNC_APP_PRINT_BACKGROUND_INFO = "com.onyx.android.ksync.print.background.info";
    public static final String ONYX_KSYNC_APP_DELETE_SYNC_DATA = "com.onyx.android.ksync.delete.sync.data";
    public static final String ONYX_KSYNC_SET_TOKEN_REFRESH_TIME_DEBUG = "com.onyx.android.ksync.set_token_refresh_time_second";
    public static final String ONYX_KSYNC_SUBMIT_FEEDBACK_DEBUG = "com.onyx.android.ksync.submit.feedback";
    public static final String ONYX_KSYNC_SET_SYNC_GATEWAY_SESSION_ID_DEBUG = "com.onyx.android.ksync.set_sync_gateway_session_id";
    public static final String ONYX_SETTINGS_ACTION_REFRESH_HELP = "com.onyx.reader.settings.action.REFRESH_HELP";
    public static final String ONYX_SETTINGS_ACTION_REFRESH_SETTINGS = "com.onyx.reader.settings.action.REFRESH_SETTINGS";
    public static final String ONYX_SETTINGS_ACTON_CHECK_GOOGLE_PLAY_ENABLE = "com.onyx.settings.action.CHECK_GOOGLE_PLAY_ENABLE";
    public static final String ONYX_FINGER_TOUCH_ENABLED_CHANGE = "com.onyx.android.FINGER_TOUCH_ENABLED_CHANGE";
    public static final String ONYX_TOP_GESTURE_ENABLE = "com.onyx.action.TOP_GESTURE_ENABLE";
    public static final String ONYX_BOTTOM_GESTURE_ENABLE = "com.onyx.action.BOTTOM_GESTURE_ENABLE";
    public static final String ONYX_SIDE_GESTURE_ENABLE = "com.onyx.action.SIDE_GESTURE_ENABLE";
    public static final String ONYX_SHOW_EAC_INPUT_DIALOG = "onyx.action.SHOW_EAC_INPUT_DIALOG";
    public static final String ONYX_DEX_OPT_COMPLETED_ACTION = "onyx.action.dex.opt.completed";
    public static final String KSYNC_CHECK_PULL_POINT = "com.onyx.action.ksync.CHECK_PULL_POINT";
    public static final String KSYNC_READER_LIBRARY_CHANGED_ACTION = "com.onyx.action.ksync.reader_library_changed";
    public static final String KSYNC_READER_USER_DOC_DATA_CHANGED_ACTION = "com.onyx.action.ksync.reader_user_doc_data_changed";
    public static final String KSYNC_READER_USER_DATA_CHANGED_ACTION = "com.onyx.action.ksync.reader_user_data_changed";
    public static final String KSYNC_READER_DOWNLOAD_BOOK_SUCCESS_ACTION = "com.onyx.action.ksync.reader_download_book_success";
    public static final String KSYNC_READER_DOWNLOAD_BOOK_CANCELLED_ACTION = "com.onyx.action.ksync.reader_download_book_cancelled";
    public static final String KSYNC_READER_DOWNLOAD_BOOK_ERROR_ACTION = "com.onyx.action.ksync.reader_download_book_error";
    public static final String KSYNC_READER_DOWNLOAD_BOOK_PROGRESS_ACTION = "com.onyx.action.ksync.reader_download_book_progress";
    public static final String KSYNC_READER_UPLOAD_BOOK_CANCELLED_ACTION = "com.onyx.action.ksync.reader_upload_book_cancelled";
    public static final String KSYNC_READER_UPLOAD_BOOK_ERROR_ACTION = "com.onyx.action.ksync.reader_upload_book_error";
    public static final String KSYNC_READER_UPLOAD_BOOK_SUCCESS_ACTION = "com.onyx.action.ksync.reader_upload_book_success";
    public static final String KSYNC_READER_UPLOAD_BOOK_PROGRESS_ACTION = "com.onyx.action.ksync.reader_upload_book_progress";
    public static final String MERGED_PULL_READER_LIBRARY_ACTION = "com.onyx.action.merged.PULL_READER_LIBRARY_ACTION";
    public static final String MERGED_PULL_READER_USER_DATA_ACTION = "com.onyx.action.merged.PULL_READER_USER_DATA_ACTION";
    public static final String KSYNC_PUSH_READING_NOTE = "com.onyx.action.ksync.PUSH_READING_NOTE";
    public static final String KSYNC_ARGS_DOCUMENT_ID = "ksync_args_document_id";
    public static final String SYSTEM_FONT_HOT_RELOAD_DONE = "onyx.action.system.font.hotreload.done";
    public static final String CLEAR_ALL_LIBRARY_THUMBNAIL_ACTION = "com.onyx.action.CLEAR_ALL_LIBRARY_THUMBNAIL_ACTION";
    public static final String THUMBNAIL_DELETE_RESULT_ACTION = "com.onyx.action.THUMBNAIL_DELETE_RESULT_ACTION";
    public static final String LIBRARY_REBUILD_COMPLETE_ACTION = "com.onyx.action.REBUILD_LIBRARY_COMPLETE_ACTION";
    public static final String EAC_VIEW_ACTION = "com.onyx.SHOW_EAC_SETTINGS_VIEW_REQUEST";
    public static final String EAC_FETCH_FROM_CLOUD_ACTION = "com.onyx.EAC_FETCH_FROM_CLOUD";
    public static final String ARGS = "args";
    public static final String ARGS_DEBUG = "args_debug";
    public static final String ARGS_ENABLE = "args_enable";
    public static final String ARGS_FORCE = "args_force";
    public static final String ARGS_PKG = "args_pkg";
    public static final String ARGS_EXTRA_USER_PKG = "args_extra_user_pkg";
    public static final String ARGS_EXTRA_USER_ID = "args_extra_user_id";
    public static final String ARGS_CLASS = "args_class";
    public static final String ARGS_DRAW_COUNT = "args_draw_count";
    public static final String ARGS_TARGET_MODE = "args_target_mode";
    public static final String ARGS_VIEW_TYPE = "args_view_type";
    public static final String ARGS_STATUS = "args_status";
    public static final String ARGS_CODE = "args_code";
    public static final String ARGS_EAC_ENABLE = "args_eac_enable";
    public static final String ARGS_COMPONENT = "args_component";
    public static final String ARGS_PATH = "args_path";
    public static final String ARGS_MSG = "args_msg";
    public static final String ARGS_SHAPE_COUNT = "args_shape_count";
    public static final String ARGS_BOOKMARK_COUNT = "args_bookmark_count";
    public static final String ARGS_ANNOTATION_COUNT = "args_annotation_count";
    public static final String ARGS_DOC_ID = "args_doc_id";
    public static final String ARGS_EVENT_TYPE = "args_event_type";
    public static final String ARGS_KEY_CODE = "args_key_code";
    public static final String ARGS_EVENT_ACTION = "args_event_action";
    public static final String ARGS_CLOSED_WHEN_CONNECTED = "args_closed_when_connected";
    public static final String ARGS_ENABLE_WHEN_STARTED = "args_enable_when_started";
    public static final String ARGS_EAC_KEY = "eacArgsKey";
    public static final String ARG_ENABLE_SIDE_BAR_POSITION = "enable_sidebar_position";
    public static final String ARGS_CHECK_PASSWORD = "check_password";
    public static final String ARGS_BADGE_COUNT = "args_badge_count";
    public static final String ARGS_UNINSTALL_FOR_ALL_USER = "args_uninstall_for_all_user";
    public static final String ARGS_OWNER_COMPONENT = "args_owner_component";
    public static final String ARGS_DATA_CHANGED_TYPE = "data_changed_type";
    public static final String ARGS_SCREENSHOT_TYPE = "args_screenshot_type";
    public static final String ARGS_FROM_ROTATION = "fromRotation";
    public static final String ARGS_TO_ROTATION = "toRotation";
    public static final String ARGS_STATISTICS_DATA = "args_statistics_data";
    public static final String ARGS_SUCCEED = "args_succeed";
    public static final String ARGS_FAILED_COUNT = "args_failed_count";
    public static final String ARGS_ACTION_HEADSET_PLUG_STATE = "state";
    public static final String ARGS_TITLE = "args_title";
    public static final String ARGS_PAGE_MODE = "args_page_mode";
    public static final String ARGS_USER_ID = "args_user_id";
    public static final String ARGS_REASON = "args_reason";
    public static final String ARGS_TOAST_TIPS = "args_toast_tips";
    public static final String ARGS_SHOW_RESULT_HINT = "show_result_hint";
    public static final String ARGS_FULLSCREEN_ENABLE = "args_fullscreen_enable";
    public static final String ARGS_TYPE = "args_type";
    public static final String ARGS_ACTION = "args_action";
    public static final String ARGS_DATA = "args_data";
    public static final String ARGS_SPLIT_SCREEN_ROTATION = "split_screen_rotation";
    public static final String ARGS_VERSION = "args_version";
    public static final String ARGS_HOST_RECT = "host_rect";
    public static final int RECEIVER_NOT_EXPORTED = 0;
    public static final int RECEIVER_EXPORTED = 1;
    public static final String CLEAR_ALL_NOTIFICATIONS_REQUEST = "action.clear.all.notifications";
    public static final String ONYX_APP_ERROR_DISABLE_ACTION = "onyx.action.app_error_disable";
    public static final String ONYX_OPEN_FROZEN_APP_ACTION = "onyx.action.open_frozen_app";
    public static final String ONYX_LOCK_DEVICE_ACTION = "onyx.action.LOCK_DEVICE";
    public static final String ONYX_UNLOCK_DEVICE_ACTION = "onyx.action.UNLOCK_DEVICE";
    public static final String ONYX_DELIVER_SHARE_ACTION = "onyx.action.DELIVER_SHARE";
    public static final String ONYX_SCREENSAVER_ACTION = "onyx.action.SCREENSAVER";
    public static final String ONYX_BEFORE_GOING_TO_SLEEP = "com.onyx.intent.action.ACTION_BEFORE_GOING_TO_SLEEP";
    public static final String ONYX_SCREENSHOT_REGION_SELECT_START_ACTION = "onyx.action.screenshot.region.select.start";
    public static final String ONYX_SCREENSHOT_REGION_SELECT_END_ACTION = "onyx.action.screenshot.region.select.end";
    public static final String SCREEN_CAST_DIALOG_ACTION = "onyx.action.show.screencast.dialog";
    public static final String GESTURE_DISABLE_ACTION = "onyx.action.INTERCEPT_GESTURE";
    public static final String GESTURE_ENABLE_ACTION = "onyx.action.DO_NOT_INTERCEPT_GESTURE";
    public static final String START_FLOATING_BUTTON_ACTION = "onyx.action.START_FLOATING_BUTTON";
    public static final String TOGGLE_FLOATING_BUTTON_STATUS_ACTION = "onyx.action.TOGGLE_FLOATING_BUTTON_STATUS";
    public static final String ONYX_ROTATION_CHANGED_ACTION = "onyx.action.ROTATION_CHANGED";
    public static final String ONYX_SCROLL_SCREEN_UP_ACTION = "onyx.action.SCROLL_SCREEN_UP";
    public static final String ONYX_SCROLL_SCREEN_DOWN_ACTION = "onyx.action.SCROLL_SCREEN_DOWN";
    public static final String CTP_STATUS_CHANGE_ACTION = "ctp.status.change";
    public static final String TOGGLE_TP_TOUCH_ACTION = "onyx.action.TOGGLE_TP_TOUCH";
    public static final String ACTION_DUMP_LIBRARY_INFO = "onyx.action.DUMP_LIBRARY_INFO";
    public static final String FULL_SCREEN_CHANGE = "full_screen_change";
    public static final String ACTION_PUSH_STATISTICS = "onyx.action.action_push_statistics";
    public static final String HIDE_EAC_DIALOG_ACTION = "onyx.action.hide.eac.dialog.action";
    public static final String SHOW_CERT_DETAILS_DIALOG_ACTION = "android.onyx.intent.action.SHOW_CERT_DETAILS";
    public static final String START_LOCK_DEVICE_ACTIVITY_ACTION = "com.onyx.android.action.LOCK_DEVICE_ACTIVITY";
    public static final String ACTION_STATISTICS_WIDGET_UPDATE = "com.onyx.statisticswidget.action.UPDATE";
    public static final String ACTION_TETHER_STATE_CHANGED = "android.net.conn.TETHER_STATE_CHANGED";
    public static final String ACTION_DISABLE_KEY_COMBINATION = "onyx.action.DISABLE_KEY_COMBINATION_ACTION";
    public static final String KEYBOARD_ATTACHED_ACTION = "onyx.action.KEYBOARD_ATTACHED_ACTION";
    public static final String TOUCHPAD_ENABLE_ACTION = "onyx.action.ONYX_TOUCH_PAD_ENABLE";
    public static final String KEYBOARD_DETACHED_ACTION = "onyx.action.KEYBOARD_DETACHED_ACTION";
    public static final String ONYX_CUSTOM_DOCUMENT_COVER_UPDATE_ACTION = "onyx.action.ONYX_CUSTOM_DOCUMENT_COVER_UPDATE_ACTION";
    public static final String REQUIRE_FLOAT_BUTTON_STATUS_ACTION = "onyx.action.REQUIRE_FLOAT_BUTTON_STATUS";
    public static final String START_SCREEN_RECORD_DIALOG_ACTION = "onyx.action.show.screen.record.dialog";
    public static final String ONYX_PREVIEW_WALLPAPER_ACTION = "onyx.action.PREVIEW_WALLPAPER_ACTION";
    public static final String ONYX_WALLPAPER_CHANGED_ACTION = "onyx.action.WALLPAPER_CHANGED";
    public static final String ONYX_PREVIEW_WALLPAPER_CHANGED_ACTION = "onyx.action.PREVIEW_WALLPAPER_CHANGED_ACTION";
    public static final String OPEN_EINK_CENTER_REQUEST_ACTION = "action.open.eink.center.request";
    public static final String OPEN_SEARCH_REQUEST_ACTION = "onyx.action.openSearch";
    public static final String RESUME_FLOATING_BUTTON_ACTION = "com.onyx.floatingbutton.resume";
    public static final String PAUSE_FLOATING_BUTTON_ACTION = "com.onyx.floatingbutton.pause";
    public static final String CLOSE_STATUS_BAR_PANEL_ACTIOn = "com.onyx.action.statusbar.panels.close";
    public static final String ONYX_TOAST_TIPS_ACTION = "com.onyx.action.ONYX.TOAST.TIPS";
    public static final String ACTION_CLOCK_WIDGET_WAKEUP = "com.onyx.action.ACTION_CLOCK_WIDGET_WAKEUP";
    public static String USER_SPLIT_HANDLE_ARGS_STATUS = "args_status";
    public static String USER_SPLIT_HANDLE_ARGS_COMPONENT = "args_component";
    public static final String ACTION_DEBUG_APP_CRASH = "com.onyx.debug.app.crash";
    public static final String DISABLE_KEYS_ACTION = "com.onyx.intent.action.DISABLE_KEYS_ACTION";
    public static final String ARGS_ARRAY_LIST = "args_array_list";
    public static final String ACTIVE_PEN_ENABLE_CHANGED_ACTION = "onyx.action.ACTIVE_PEN_ENABLE_CHANGED";
    public static final String ACTIVE_PEN_ADSORPTION_STATE_CHANGED = "onyx.action.ACTIVE_PEN_ADSORPTION_STATE_CHANGED";
    public static final String ARGS_ACTIVE_PEN_ADSORPTION_STATE = "adsorption_state";
    public static final String PEN_UI_VISIBILITY_CONFIG_CHANGED_ACTION = "com.onyx.action.PEN_UI_VISIBILITY_CONFIG_CHANGED";
    public static final String ONYX_TOAST_ACTION = "com.onyx.action.ONYX.TOAST";
    public static final String ONYX_QUICK_NOTE_ACTION = "com.onyx.intent.action.QUICK_NOTE";
    public static final String SHOW_ACTIVE_PEN_SETTINGS_DIALOG_ACTION = "onyx.action.SHOW_ACTIVE_PEN_SETTINGS_DIALOG";
    public static final String ENTER_DREAM_ACTION = "onyx.action.ENTER_DREAM";
    public static final String SPLIT_SCREEN_VISIBILITY_ACTION = "onyx.action.SPLIT_SCREEN_VISIBILITY_ACTION";
    public static final String UPLOAD_EAC_THEME_ACTION = "onyx.action.UPLOAD_EAC_THEME";

    public static class App {
        public static final String APP_LOGCAT_DATA_INFO_ACTION = "onyx.action.app.LOGCAT_DATA_INFO";
        public static final String APP_ENABLE_FRAGMENTATION_DEBUG_ACTION = "onyx.action.app_ENABLE_FRAGMENTATION_DEBUG";
        public static final String APP_DUMP_DUMP_BACKGROUND_INFO_ACTION = "onyx.action.app_DUMP_BACKGROUND_INFO";
        public static final String APP_DUMP_DUMP_APP_EAC_CONFIG = "onyx.action.app_DUMP_APP_EAC_CONFIG";
        public static final String APP_DUMP_LIBRARY_WIGET_THUMBNAIL_ACTION = "onyx.action.app_DUMP_LIBRARY_WIDGET_THUMBNAIL";
        public static final String APP_DUMP_APP_INFO_ACTION = "onyx.action.DUMP_APP_INFO";
        public static final String APP_CLOUD_PROVIDER_UPLOAD_FILE_TO_ONYX_ACTION = "onyx.action.app.UPLOAD_FILE_TO_ONYX_CLOUD_PROVIDER_ACTION";
        public static final String OPEN_APP_ACTION = "onyx.action.OPEN_APP";
        public static final String EXTRA_REMOVED_FOR_ALL_USERS = "android.intent.extra.REMOVED_FOR_ALL_USERS";
        public static final String EXTRA_USER_HANDLE = "android.intent.extra.user_handle";
    }

    public static class AppMarket {
        public static final String ARGS_APP_PACKAGE_NAME = "args_app_package_name";
        public static final String ARGS_DOWNLOAD_PROGRESS = "args_download_progress";
        public static final String ARGS_DOWNLOAD_SUCCESS = "args_download_success";
        public static final String ARGS_INSTALL_SUCCESS = "args_install_success";
        public static final String ACTION_DOWNLOAD_APP = "com.onyx.intent.action.DOWNLOAD_APP";
        public static final String ACTION_APP_DOWNLOAD_PROGRESS = "com.onyx.intent.action.APP_DOWNLOAD_PROGRESS";
        public static final String ACTION_APP_DOWNLOAD_FINALLY = "com.onyx.intent.action.APP_DOWNLOAD_FINALLY";
        public static final String ACTION_APP_INSTALLING = "com.onyx.intent.action.APP_INSTALLING";
        public static final String ACTION_APP_INSTALL_FINALLY = "com.onyx.intent.action.APP_INSTALL_FINALLY";
    }

    public class BookShelf {
        public static final String KEY_NAME = "name";
        public static final String RECEIVER_INTENT_ADDBOOKSHELVES_ACTION = "com.onyx.intent.action.addbookshelves";
        public static final String RECEIVER_INTENT_COVER_LOADED_ACTION = "com.onyx.intent.action.COVER_LOADED";

        public BookShelf() {
        }
    }

    public class BooxTransfer {
        public static final String PUSH_RECORD_START_SINGLE_DOWNLOAD_REQUEST_ACTION = "onyx.action.cloud.push.RECORD_START_SINGLE_DOWNLOAD_REQUEST";
        public static final String PUSH_RECORD_CANCEL_SINGLE_DOWNLOAD_REQUEST_ACTION = "onyx.action.cloud.push.RECORD_CANCEL_SINGLE_DOWNLOAD_REQUEST";
        public static final String PUSH_RECORD_DELETE_REQUEST_ACTION = "onyx.action.cloud.PUSH_RECORD_DELETE_REQUEST";
        public static final String PUSH_RECORD_START_SINGLE_DOWNLOAD_RESPONSE_ACTION = "onyx.action.cloud.push.RECORD_START_SINGLE_DOWNLOAD_RESPONSE";
        public static final String PUSH_RECORD_CANCEL_SINGLE_DOWNLOAD_RESPONSE_ACTION = "onyx.action.cloud.push.RECORD_CANCEL_SINGLE_DOWNLOAD_RESPONSE";
        public static final String PUSH_RECORD_DOWNLOAD_PROGRESS_RESPONSE_ACTION = "onyx.action.cloud.push.RECORD_DOWNLOAD_PROGRESS_RESPONSE";
        public static final String PUSH_RECORD_DOWNLOAD_COMPLETE_RESPONSE_ACTION = "onyx.action.cloud.push.RECORD_DOWNLOAD_COMPLETE_RESPONSE";
        public static final String PUSH_RECORD_DELETE_RESPONSE_ACTION = "onyx.action.cloud.push.RECORD_DELETE_RESPONSE";
        public static final String PUSH_RECORD_REMOTE_PUSH_ACTION = "onyx.action.cloud.push.REMOTE_PUSH";
        public static final String PUSH_TRANSFER_PRODUCT_EXTRA = "push_transfer_product_extra";
        public static final String PUSH_TRANSFER_PRODUCT_ID_LIST_EXTRA = "push_transfer_product_id_list_extra";
        public static final String PUSH_TRANSFER_PRODUCT_DOWNLOAD_STATUS_EXTRA = "push_transfer_product_status_extra";
        public static final String PUSH_TRANSFER_PRODUCT_SELECTION_MODEL_MAP_EXTRA = "push_transfer_product_selection_model_map_extra";
        public static final String PUSH_TRANSFER_DEL_PRODUCT_LOCAL_FILE_EXTRA = "push_transfer_del_product_local_file_extra";
        public static final String PUSH_RECORD_OPEN_JD_BOOK_ACTION = "onyx.action.cloud.push.RECORD_OPEN_JD_BOOK";
        public static final String JD_BOOK_ID_EXTRA = "jd_book_id";
        public static final String OPEN_FILE_BY_DEFAULT_ACTIVITY_ACTION = "onyx.action.cloud.push.OPEN_FILE_BY_DEFAULT_ACTIVITY";

        public BooxTransfer() {
        }
    }

    public static class CHILD {
        public static final String ENTER_CHILD_MODE_ACTION = "onyx.action.child.ENTER";
        public static final String EXIT_CHILD_MODE_ACTION = "onyx.action.child.EXIT";
        public static final String CHILD_MODE_SETTING_ACTION = "onyx.action.child.SETTING";
        public static final String USER_USAGE_MODE_CHANGED_ACTION = "onyx.action.child.USAGE_MODE_CHANGED";
        public static final String WEB_CONTROL_RULE_SET_CHANGED_ACTION = "onyx.action.child.WEB_CONTROL_RULE_SET_CHANGED";
    }

    public class CloudStorage {
        public static final String ENABLE_FEATURE_CHANGE_ACTION = "onyx.action.cloud.storage.ENABLE_FEATURE_CHANGE";
        public static final String ENABLE_NOTIFICATION_ACTION = "onyx.action.cloud.storage.ENABLE_NOTIFICATION";

        public CloudStorage() {
        }
    }

    public static class DownloadConfig {
        public static final String CLOUD_STORAGE_DOWNLOADING_RESPONSE_ACTION = "onyx.action.cloud.storage.DOWNLOADING_RESPONSE";
        public static final String NETDISK_FILES_SAVE_METADATA = "onyx.action.cloud.storage.SAVE_METADATA";
    }

    public static class Font {
        public static final int FONT_LANG_INDEX_ALL = 0;
        public static final int FONT_LANG_INDEX_CJK = 1;
        public static final int FONT_LANG_INDEX_EN = 2;
        public static final String KEY_FONT_LANG = "font_lang";
        public static final String KEY_FONT_FILE_NAME = "font_file_name";
        public static final String REPLACE_FONT_ACTION = "onyx.action.font.replace";
        public static final String REPLACE_FONT_ACTION_NEW = "onyx.action.font.replace.system";
        public static final String RESET_DEFAULT_ACTION = "onyx.action.font.reset.default";

        public static boolean isCJKFontIndex(int index) {
            return index == 1;
        }
    }

    public static class InputKeyboard {
        public static final String ONYX_IME_STATUS_CHANGED_ACTION = "onyx.action.kime.status.changed";
        public static final String THIRD_KEYBOARD_TOUCH_REGION_CHANGED = "onyx.action.keyboard.touch.region.changed";
        public static final String ARGS_FLOATING_WINDOW_REGION = "floating_window_region";
        public static final String ARGS_THIRD_KEYBOARD_RECT = "args_rect";
        public static final String ARGS_IME_STATUS = "args_status";
        public static final int IME_STATUS_HANDWRITING_START = 0;
        public static final int IME_STATUS_HANDWRITING_END = 1;
        public static final int IME_STATUS_FLOATING_WINDOW_UPDATE = 2;
        public static final int IME_STATUS_FLOATING_WINDOW_MOVE_START = 3;
        public static final int IME_STATUS_FLOATING_WINDOW_MOVE_END = 4;
        public static final int IME_STATUS_WINDOW_SHOWN = 5;
        public static final int IME_STATUS_WINDOW_HIDDEN = 6;
    }

    public class MarketConfig {
        public static final String PACKAGE_NAME = "com.onyx.appmarket";
        public static final String SERVICE_ACTION_APP_CHECK_UPDATE = "com.onyx.appmarket_ACTION_APP_CHECK_UPDATE";

        public MarketConfig() {
        }
    }

    public static class MultiWindowConfig {
        public static final String ACTION_START_MULTI_WINDOW = "com.onyx.action.START_MULTI_WINDOW";
        public static final String ACTION_QUIT_MULTI_WINDOW = "com.onyx.action.QUIT_MULTI_WINDOW";
        public static final String ACTION_QUIT_NOTE = "com.onyx.action.QUIT_NOTE";
        public static final String ACTION_QUIT_TRANSLATION = "com.onyx.action.QUIT_TRANSLATION";
        public static final String ACTION_NEW_TRANSLATION = "com.onyx.action.NEW_TRANSLATION";
        public static final String ACTION_IN_TRANSLATION_TIPS = "com.onyx.action.IN_TRANSLATION_TIPS";
        public static final String ACTION_SWITCH_LEFT_RIGHT_WINDOW = "com.onyx.action.SWITCH_LEFT_RIGHT_WINDOW";
        public static final String ACTION_NEW_ASSISTANT = "com.onyx.action.NEW_ASSISTANT";
        public static final String ACTION_QUIT_ASSISTANT = "com.onyx.action.QUIT_ASSISTANT";
        public static final String ARGS_PRIMARY_TASK_ID = "primary_task_id";
        public static final String ARGS_PRIMARY_TASK_TO_TOP = "args_primary_task_to_top";
        public static final String ARGS_PRIMARY_TASK_CREATE_MODE = "args_primary_task_create_mode";
        public static final String ARGS_SPILT_SCREEN_LAYOUT_TYPE = "args_spilt_screen_layout_type";
        public static final int SPLIT_SCREEN_CREATE_MODE_TOP_OR_LEFT = 0;
        public static final int SPLIT_SCREEN_CREATE_MODE_BOTTOM_OR_RIGHT = 1;
        public static final int SPLIT_SCREEN_LAYOUT_LEFT_RIGHT_TYPE = 0;
        public static final int SPLIT_SCREEN_LAYOUT_TOP_BOTTOM_TYPE = 1;
        public static final String COMPAT_M_ACTION_START_ACTIVITY = "com.onyx.action.COMPAT_M_ACTION_START_ACTIVITY";
        public static final String ARGS_PRIMARY_TASK_BUNDLE = "compat_m_args_primary_task_bundle";
        public static final String ARGS_SECONDARY_TASK_BUNDLE = "compat_m_args_secondary_task_bundle";
        public static final String ARGS_START_ACTIVITY_BUNDLE = "compat_m_args_start_activity_bundle";
        public static final String ARGS_ACTION = "compat_m_args_action";
        public static final String ARGS_COMPONENT = "compat_m_args_component";
        public static final String COMPAT_M_ARGS_DATA = "compat_m_args_data";
        public static final String ARGS_FLAGS = "compat_m_args_flags";
    }

    public class OnyxDict {
        public static final String DICT_UPDATE_NEW_WORD_ACTION = "dict.action.UPDATE_NEW_WORD";
        public static final String ARGS_DELETE_NEW_WORD = "args_delete_new_word";
        public static final String ARGS_NEW_WORD = "args_new_word";
        public static final String DICT_DELETE_NEW_WORD_INFO_ACTION = "dict.action.DELETE_NEW_WORD";
        public static final String DICT_UPDATE_NEW_WORD_INFO_ACTION = "dict.action.UPDATE_NEW_WORD_INFO";
        public static final String ARGS_NEW_WORD_ID = "args_new_word_id";

        public OnyxDict() {
        }
    }

    public static class ReceiverFlags {
        public static final int RECEIVER_EXPORTED = ReflectUtil.getStaticIntFieldSafely(Context.class, "RECEIVER_EXPORTED");
        public static final int RECEIVER_NOT_EXPORTED = ReflectUtil.getStaticIntFieldSafely(Context.class, "RECEIVER_NOT_EXPORTED");
    }

    public class Settings {
        public static final String SETTINGS_MAIN = "onyx.settings.action.main";
        public static final String SETTINGS_CHILD = "onyx.settings.action.child";
        public static final String SETTINGS_WIFI_ACTION = "onyx.settings.action.wifi";
        public static final String SETTINGS_BLUETOOTH_ACTION = "onyx.settings.action.bluetooth";
        public static final String SETTINGS_APP_MANAGEMENT_ACTION = "onyx.settings.action.app.management";
        public static final String SETTINGS_LANGUAGE_ACTION = "onyx.settings.action.language";
        public static final String SETTINGS_DATE_TIME_ACTION = "onyx.settings.action.datetime";
        public static final String SETTINGS_POWER_ACTION = "onyx.settings.action.power";
        public static final String SETTINGS_POWER_MANAGER_ACTION = "onyx.settings.action.powermanager";
        public static final String SETTINGS_NETWORK_ACTION = "onyx.settings.action.network";
        public static final String SETTINGS_LIBRARY_ACTION = "onyx.settings.action.library";
        public static final String SETTINGS_APPLICATION_ACTION = "onyx.settings.action.application";
        public static final String SETTINGS_ACCOUNT_ACTION = "onyx.settings.action.account";
        public static final String SETTINGS_ONYX_ACCOUNT_ACTION = "onyx.settings.action.onyx.account";
        public static final String SETTINGS_ONYX_ACCOUNT_LIST_ACTION = "onyx.settings.action.onyx.account.list";
        public static final String SETTINGS_USER_ACCOUNT_ACTION = "onyx.settings.action.user.account";
        public static final String SETTINGS_ACCOUNT_ONLY_LOGIN_ACTION = "onyx.settings.action.only.login.account";
        public static final String SETTINGS_FIRMWARE_ACTION = "onyx.settings.action.firmware";
        public static final String SETTINGS_FEEDBACK_RECORDS_ACTION = "onyx.settings.action.feedback";
        public static final String SETTINGS_SUBMIT_FEEDBACK_ACTION = "onyx.settings.action.SUBMIT_FEEDBACK";
        public static final String SETTINGS_CHILD_MODE_ACTION = "onyx.settings.action.child_mode";
        public static final String SETTINGS_CONFIGURE_NEW_WIFI_ACTION = "com.android.systemui.CONFIGURE_NEW_WIFI_ACTION";
        public static final String SETTINGS_CONFIGURE_WORK_PROFILE_ACTION = "com.android.systemui.CONFIGURE_WORK_PROFILE_ACTION";
        public static final String SETTINGS_CONFIGURE_TABLET_WORK_PROFILE_ACTION = "com.android.systemui.CONFIGURE_TABLET_WORK_PROFILE_ACTION";
        public static final String SETTINGS_CONFIGURE_WLAN_NETWORK_SHARE_ACTION = "com.android.systemui.CONFIGURE_WLAN_NETWORK_SHARE_ACTION";
        public static final String SETTINGS_CONFIGURE_TABLET_WLAN_NETWORK_SHARE_ACTION = "com.android.systemui.CONFIGURE_TABLET_WLAN_NETWORK_SHARE_ACTION";
        public static final String SETTINGS_ACTION_VIEW_DOWNLOADS = "android.intent.action.VIEW_DOWNLOADS";
        public static final String SETTINGS_ACTION_SYSTEM_PASSWORD_SETTING = "onyx.settings.action.SYSTEM_PASSWORD_SETTING";
        public static final String SETTINGS_ACTION_NAVIGATION_BAR_SETTING = "onyx.settings.action.NAVIGATION_BAR_SETTING";
        public static final String SETTINGS_ACTION_LAUNCHER_SCREENSAVER_SETTING = "onyx.settings.action.LAUNCHER_SCREENSAVER_SETTING";
        public static final String SETTINGS_ACTION_DREAM_STYLE_SETTING = "onyx.settings.action.DREAM_STYLE_SETTING";
        public static final String SETTINGS_ACTION_OTHER_DREAM_SETTING_GUIDE = "onyx.settings.action.OTHER_SCREENSAVER_SETTING_GUIDE";
        public static final String SETTINGS_ACTION_SHUTDOWN_IMAGE_SETTING = "onyx.settings.action.SHUTDOWN_IMAGE_SETTING";
        public static final String SETTINGS_ACTION_PASSWORD_MANAGER = "onyx.settings.action.password_manager";
        public static final String SETTINGS_ACTION_CUSTOM_SIDE_KEY = "onyx.settings.action.CUSTOM_SIDE_KEY";
        public static final String SETTINGS_ACTION_SCROLL_BUTTON_SETTING_KEY = "onyx.settings.action.SCROLL_BUTTON_SETTING";
        public static final String STRING_ABOUT_DEVICE_ACTION = "onyx.settings.action.ABOUT";
        public static final String STRING_MAIL_CONFIG = "onyx.settings.action.MAIL_CONFIG";
        public static final String STRING_ACCOUNT_CONFIG = "onyx.settings.action.ACCOUNT_CONFIG";
        public static final String STRING_SERVER_SELECTION_ACTION = "onyx.settings.action.SERVER_SELECTION";
        public static final String ONYX_ACCOUNT_LOGIN_ACTIVITY_ACTION = "onyx.settings.action.ONYX_ACCOUNT_LOGIN_ACTIVITY";
        public static final String SETTINGS_MY_ANNOTATION_ACTION = "onyx.settings.action.MY_ANNOTATION";
        public static final String SETTINGS_STATISTICS_ACTION = "onyx.settings.action.STATISTICS";
        public static final String SETTINGS_QUICK_LAUNCHER_ACTION = "onyx.settings.action.QUICK_LAUNCHER";
        public static final String SETTINGS_ONYX_ACCOUNT_CLOUD_STORAGE = "onyx.settings.action.ONYX_ACCOUNT_CLOUD_STORAGE";
        public static final String SETTINGS_ONYX_ACCOUNT_INFO_CHANGED = "onyx.settings.action.ONYX_ACCOUNT_INFO_CHANGED";
        public static final String SETTINGS_ACTION_DESK_WALLPAPER = "onyx.settings.action.DESK_WALLPAPER";
        public static final String SETTINGS_ACTION_APP_NOTIFICATION_MANAGER = "onyx.settings.action.APP_NOTIFICATION_MANAGER";
        public static final String SETTINGS_ACTION_MAIL_SETTING = "onyx.settings.action.MAIL_SETTING";
        public static final String SETTINGS_DND_ACTION = "onyx.settings.action.dnd";
        public static final String SETTINGS_ACTION_WIFI_TETHER_SETTINGS = "onyx.settings.action.WIFI_TETHER_SETTINGS";
        public static final String SETTINGS_KEYBOARD_ACTION = "onyx.settings.action.PHYSICAL_KEYBOARD";
        public static final String SETTINGS_SYSTEM_SETTINGS_ACTION = "onyx.settings.action.SYSTEM_SETTINGS";
        public static final String SETTINGS_SOUND_SETTINGS_ACTION = "onyx.settings.action.SoundSettings";
        public static final String SETTINGS_PASSWORD_SECURITY_SETTINGS_ACTION = "onyx.settings.action.PASSWORD_SECURITY";
        public static final String SETTINGS_APP_FREEZE_MANAGEMENT_SETTINGS_ACTION = "onyx.settings.action.APP_FREEZE_MANAGEMENT";
        public static final String SETTINGS_CLOSE_ACCOUNT_ACTION = "onyx.settings.action.CLOSE_ACCOUNT";
        public static final String SETTINGS_ACTION_SELECT_CERT_FILE = "onyx.settings.action.SELECT_CERT_FILE";
        public static final String SETTINGS_ACTIVE_PEN_FIRMWARE_ACTION = "onyx.settings.action.ACTIVE_PEN_FIRMWARE";
        public static final String SETTINGS_ACCESSIBILITY_SETTINGS_ACTION = "onyx.settings.action.ACCESSIBILITY_SETTINGS";
        public static final String SETTINGS_PAGE_TURNER_KEY_SETTINGS_ACTION = "onyx.settings.action.PAGE_TURNER_KEY_SETTINGS";

        public Settings() {
        }
    }

    public static class ZhiShuShop {
        public static final String ACTION_SHOP_EBOOK_DETAIL = "com.onyx.zhishu.shop.EBOOK_DETAIL";
        public static final String ACTION_SHOP_LOGIN = "com.onyx.zhishu.shop.Login";
        public static final String ACTION_SHOP_AUDIO_PLAY_DETAIL = "com.onyx.zhishu.shop.AUDIO_PLAY_DETAIL";
    }

    public static void ensureRegisterReceiver(@NonNull Context context, @NonNull BroadcastReceiver receiver, @NonNull List<String> actions) {
        ensureRegisterReceiver(context, receiver, actions, ReceiverFlags.RECEIVER_EXPORTED);
    }

    public static void ensureUnregisterReceiver(@NonNull Context context, @NonNull BroadcastReceiver receiver) {
        try {
            context.getApplicationContext().unregisterReceiver(receiver);
        } catch (Exception unused) {
        }
    }

    public static void sendBroadcast(Context context, String action) {
        sendBroadcast(context, new Intent(action));
    }

    public static void sendDeleteIntent(Context context, List<String> filePathList) {
        if (CollectionUtils.isNullOrEmpty(filePathList)) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(RECEIVER_INTENT_DELETE_ACTION);
        intent.putExtra("name", JSON.toJSONString(filePathList));
        context.sendBroadcast(intent);
    }

    public static void sendAddToBookShelfIntent(Context context, ArrayList<String> srcData) {
        Intent intent = new Intent();
        intent.setAction(BookShelf.RECEIVER_INTENT_ADDBOOKSHELVES_ACTION);
        intent.putExtra("name", JSON.toJSONString(srcData));
        context.sendBroadcast(intent);
    }

    public static void sendFingerTouchEnabledBroadcast(boolean enabled) {
        Intent intent = new Intent();
        intent.setAction(ONYX_FINGER_TOUCH_ENABLED_CHANGE);
        intent.putExtra(ARGS_ENABLE, enabled);
        intent.putExtra(ARGS_PKG, ResManager.getAppContext().getPackageName());
        sendBroadcast(ResManager.getAppContext(), intent);
    }

    public static void sendStateBarEnabledBroadcast(boolean enabled) {
        Intent intent = new Intent();
        intent.setAction(ONYX_TOP_GESTURE_ENABLE);
        intent.putExtra(ARGS_ENABLE, enabled);
        sendBroadcast(ResManager.getAppContext(), intent);
    }

    public static void sendBottomGestureEnabledBroadcast(boolean enabled) {
        Intent intent = new Intent();
        intent.setAction(ONYX_BOTTOM_GESTURE_ENABLE);
        intent.putExtra(ARGS_ENABLE, enabled);
        sendBroadcast(ResManager.getAppContext(), intent);
    }

    public static void sendSideGestureEnabledBroadcast(boolean enabled) {
        Intent intent = new Intent();
        intent.setAction(ONYX_SIDE_GESTURE_ENABLE);
        intent.putExtra(ARGS_ENABLE, enabled);
        sendBroadcast(ResManager.getAppContext(), intent);
    }

    public static void sendRotationIntent(Context context, int rotation, int rotateBy) {
        Intent intent = new Intent();
        intent.setAction(RotationUtils.ACTION_ROTATION);
        intent.putExtra(RotationUtils.ARGS_ROTATION, rotation);
        intent.putExtra(RotationUtils.ARGS_ROTATE_BY, rotateBy);
        sendBroadcast(context, intent);
    }

    public static void sendStartMultiWindowIntent(Context context, int primaryTaskId, int createMode, int splitScreenType) {
        Intent intentA = a(primaryTaskId, createMode);
        intentA.putExtra(MultiWindowConfig.ARGS_SPILT_SCREEN_LAYOUT_TYPE, splitScreenType);
        sendBroadcast(context, intentA);
    }

    public static void sendReplaceSystemFontIntent(Context context, int fontLang, String fontFileName) {
        Intent intent = new Intent(Font.REPLACE_FONT_ACTION);
        intent.putExtra(Font.KEY_FONT_LANG, fontLang);
        intent.putExtra(Font.KEY_FONT_FILE_NAME, fontFileName);
        sendBroadcast(context, intent);
    }

    public static void sendReplaceSystemFontNewIntent(Context context, int fontLang, String fontPath) {
        if (StringUtils.isEquals(FontUtils.getOnyxSystemDefaultFontPath(), fontPath)) {
            sendResetDefaultFontIntent(context);
            return;
        }
        Debug.e("sendReplaceSystemFontNewIntent", "onyx.action.font.replace.system : " + fontLang + " : " + fontPath, new Object[0]);
        Intent intent = new Intent(Font.REPLACE_FONT_ACTION_NEW);
        intent.putExtra(Font.KEY_FONT_LANG, fontLang);
        intent.putExtra(ARGS_PATH, fontPath);
        sendBroadcast(context, intent);
    }

    public static void sendResetDefaultFontIntent(Context context) {
        sendBroadcast(context, new Intent(Font.RESET_DEFAULT_ACTION));
    }

    @NonNull
    private static Intent a(int primaryTaskId, int createMode) {
        Intent intent = new Intent();
        intent.setAction(MultiWindowConfig.ACTION_START_MULTI_WINDOW);
        intent.putExtra(MultiWindowConfig.ARGS_PRIMARY_TASK_ID, primaryTaskId);
        intent.putExtra(MultiWindowConfig.ARGS_PRIMARY_TASK_CREATE_MODE, createMode);
        return intent;
    }

    public static void sendQuitMultiWindowIntent(Context context, boolean primaryTaskToTop) {
        Intent intent = new Intent();
        intent.setAction(MultiWindowConfig.ACTION_QUIT_MULTI_WINDOW);
        intent.putExtra(MultiWindowConfig.ARGS_PRIMARY_TASK_TO_TOP, primaryTaskToTop);
        sendBroadcast(context, intent);
    }

    public static void sendBatchAppUninstallIntent(Context context, ArrayList<AppDataInfo> packageList) {
        ArrayList<String> arrayList = new ArrayList<>(CollectionUtils.transformData(CollectionUtils.filterItems(packageList, appDataInfo -> {
            return appDataInfo.isWork;
        }), appDataInfo2 -> {
            return appDataInfo2.packageName;
        }));
        ArrayList<String> arrayList2 = new ArrayList<>(CollectionUtils.transformData(CollectionUtils.filterItems(packageList, appDataInfo3 -> {
            return !appDataInfo3.isWork;
        }), appDataInfo4 -> {
            return appDataInfo4.packageName;
        }));
        Intent intent = new Intent(ONYX_SILENT_UNINSTALL_ACTION);
        intent.putStringArrayListExtra(ARGS_PKG, arrayList2);
        intent.putStringArrayListExtra(ARGS_EXTRA_USER_PKG, arrayList);
        UserHandle workUserProfile = DPMUtils.getWorkUserProfile(context);
        if (workUserProfile != null) {
            intent.putExtra(ARGS_EXTRA_USER_ID, DPMUtils.getIdForUser(context, workUserProfile));
        }
        intent.setPackage("com.android.packageinstaller");
        Debug.d("uninstall app : ", "base apk : " + arrayList2.size() + " , work apk : " + arrayList.size(), new Object[0]);
        context.sendBroadcast(intent);
    }

    public static void sendLauncherBadgeIntent(Context context, String launchName, int count) {
        Intent intent = new Intent(ONYX_LAUNCHER_BADGE_ACTION);
        intent.putExtra(ARGS_PKG, launchName);
        intent.putExtra(ARGS_BADGE_COUNT, count);
        context.sendBroadcast(addFlagsForAndroidO(intent));
    }

    public static Intent addFlagsForAndroidO(Intent intent) {
        return DeviceBroadcastHelper.addFlagsForAndroidO(intent);
    }

    public static void sendClearAllNotifications(Context context) {
        Intent intent = new Intent();
        intent.setAction(CLEAR_ALL_NOTIFICATIONS_REQUEST);
        sendBroadcast(context, intent);
    }

    public static void sendSetOwner(Context context, ComponentName adminComponent) {
        Intent intent = new Intent();
        intent.setAction(SET_DEVICE_OWNER_ACTION);
        intent.putExtra(ARGS_OWNER_COMPONENT, adminComponent);
        context.sendBroadcast(intent);
    }

    public static void sendStartActivityInMultiWindowCompatM(Context context, Intent srcIntent) {
        Debug.d("sendStartActivityInMultiWindowCompatM");
        Intent intent = new Intent();
        intent.setAction(MultiWindowConfig.COMPAT_M_ACTION_START_ACTIVITY);
        intent.putExtra(MultiWindowConfig.ARGS_START_ACTIVITY_BUNDLE, a(srcIntent));
        sendBroadcast(context, intent);
    }

    public static void sendUnmountExtMediaBroadcast(Context context, String filePath) {
        Intent intent = new Intent(ONYX_UNMOUNT_EXT_MEDIA_ACTION);
        intent.putExtra(ARGS_PATH, filePath);
        sendBroadcast(context, intent);
    }

    public static void sendRenameIntent(Context context, String origin, String dest) {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put(origin, dest);
        sendRenameIntent(context, arrayMap);
    }

    public static void sendCopyIntent(Context context, ArrayMap<String, String> srcData) {
        Intent intent = new Intent();
        intent.setAction(RECEIVER_INTENT_COPY_ACTION);
        intent.putExtra("name", JSON.toJSONString(srcData));
        sendBroadcast(context, intent);
    }

    public static void sendCutIntent(Context context, ArrayMap<String, String> srcData) {
        Intent intent = new Intent();
        intent.setAction(RECEIVER_INTENT_CUT_ACTION);
        intent.putExtra("name", JSON.toJSONString(srcData));
        sendBroadcast(context, intent);
    }

    public static void sendSubmitFeedbackIntent(Context context, FeedbackArgsBean argsBean) {
        Intent intent = new Intent();
        intent.setAction(DeviceReceiver.ONYX_FEEDBACK_ACTION);
        intent.putExtra("name", JSONUtils.toJson(argsBean, new JSONWriter.Feature[0]));
        sendBroadcast(context, intent);
    }

    public static void enableGesture(Context context, boolean enable) {
        sendBroadcast(context, enable ? GESTURE_ENABLE_ACTION : GESTURE_DISABLE_ACTION);
    }

    public static void checkGooglePlayEnable(Context context) {
        sendBroadcast(context, ONYX_SETTINGS_ACTON_CHECK_GOOGLE_PLAY_ENABLE);
    }

    public static void shareToThirdParty(Context context, String filePath) {
        Intent intent = new Intent(ONYX_DELIVER_SHARE_ACTION);
        intent.putExtra("file", filePath);
        sendBroadcast(context, intent);
    }

    public static void sendDownloadStatus(Context context, String action, String data) {
        sendBroadcast(context, new Intent(action).putExtra(MultiWindowConfig.COMPAT_M_ARGS_DATA, data));
    }

    public static void sendCloudStorageDownloadStatus(Context context, String data) {
        sendDownloadStatus(context, DownloadConfig.CLOUD_STORAGE_DOWNLOADING_RESPONSE_ACTION, data);
    }

    public static void sendNetDiskFileSaveMetadata(Context context, String data) {
        sendBroadcast(context, new Intent(DownloadConfig.NETDISK_FILES_SAVE_METADATA).putExtra(MultiWindowConfig.COMPAT_M_ARGS_DATA, data));
    }

    public static void sendEmbedPdfStatusIntent(Context context, String data) {
        sendDownloadStatus(context, ONYX_KREADER_EMBED_PDF_STATUS_ACTION, data);
    }

    public static boolean isValidIntent(Intent intent) {
        return intent != null && StringUtils.isNotBlank(intent.getAction());
    }

    public static void sendShowKeyboardShortcutsBroadcast(Context context) {
        sendBroadcast(context, new Intent(ACTION_SHOW_KEYBOARD_SHORTCUTS));
    }

    public static void sendDisableKeyCombinationBroadcast(Context context) {
        sendBroadcast(context, new Intent(ACTION_DISABLE_KEY_COMBINATION));
    }

    public static void sendRefreshLibraryBroadcast(Context context) {
        sendRefreshLibraryBroadcast(context, false);
    }

    public static void sendThumbnailDeleteResultIntent(Context context, String path) {
        Intent intent = new Intent(THUMBNAIL_DELETE_RESULT_ACTION);
        intent.putExtra("name", path);
        sendBroadcast(context, intent);
    }

    public static void sendScrollUpBroadcast(Context context) {
        Intent intent = new Intent(SCROLL_GESTURE_ACTION);
        intent.putExtra(ARGS_PAGE_MODE, 0);
        sendBroadcast(context, intent);
    }

    public static void sendScrollDownBroadcast(Context context) {
        Intent intent = new Intent(SCROLL_GESTURE_ACTION);
        intent.putExtra(ARGS_PAGE_MODE, 1);
        sendBroadcast(context, intent);
    }

    public static void cleanupRecentTasks(Context context, boolean cleanLock) {
        Intent intent = new Intent(FLOAT_TASK_CLEANUP_ACTION);
        intent.putExtra(ARGS_FORCE, cleanLock);
        sendBroadcast(context, intent);
    }

    public static void closeStatusBarPanel(Context context) {
        sendBroadcast(context, CLOSE_STATUS_BAR_PANEL_ACTIOn);
    }

    public static void sendBroadcastWithToastTips(Context context, String tips) {
        Intent intent = new Intent(ONYX_TOAST_TIPS_ACTION);
        intent.putExtra(ARGS_TOAST_TIPS, tips);
        sendBroadcast(context, intent);
    }

    public static void sendBroadcastWithToast(Context context, String tips) {
        Intent intent = new Intent(ONYX_TOAST_ACTION);
        intent.putExtra(ARGS_TOAST_TIPS, tips);
        sendBroadcast(context, intent);
    }

    public static void sendLibraryRebuildComplete(Context context) {
        sendBroadcast(context, LIBRARY_REBUILD_COMPLETE_ACTION);
    }

    public static void sendDisableKeysBroadcast(Context context, List<Integer> keycodes) {
        Intent intent = new Intent(DISABLE_KEYS_ACTION);
        intent.putIntegerArrayListExtra(ARGS_ARRAY_LIST, new ArrayList<>(keycodes));
        sendBroadcast(context, intent);
    }

    public static void sendJson(String action, Object object) {
        Intent intent = new Intent(action);
        String json = JSONUtils.toJson(object, new JSONWriter.Feature[0]);
        Debug.d(json);
        intent.putExtra("json", json);
        sendBroadcast(ResManager.getAppContext(), intent);
    }

    @SuppressLint({"NewApi"})
    public static void ensureRegisterReceiver(@NonNull Context context, @NonNull BroadcastReceiver receiver, @NonNull List<String> actions, int flags) {
        try {
            if (actions.size() <= 0) {
                return;
            }
            IntentFilter intentFilter = new IntentFilter();
            Iterator<String> it = actions.iterator();
            while (it.hasNext()) {
                intentFilter.addAction(it.next());
            }
            context.getApplicationContext().registerReceiver(receiver, intentFilter, flags);
        } catch (Exception unused) {
        }
    }

    public static void sendBroadcast(Context context, Intent intent) {
        DeviceBroadcastHelper.sendBroadcast(context, intent);
    }

    public static void sendRefreshLibraryBroadcast(Context context, boolean isExistsDB) {
        Intent intent = new Intent();
        intent.setAction(RECEIVER_INTENT_SHOP_REFRESH_LIBRARY_ACTION);
        intent.putExtra("args", isExistsDB);
        context.sendBroadcast(intent);
    }

    public static void sendStartMultiWindowIntent(Context context, int primaryTaskId, int createMode, Intent primaryTask, Intent secondaryTask, int splitScreenType) {
        Debug.d("sendStartMultiWindowIntent");
        Intent intentA = a(primaryTaskId, createMode);
        Bundle bundleA = a(primaryTask);
        Bundle bundleA2 = a(secondaryTask);
        intentA.putExtra(MultiWindowConfig.ARGS_PRIMARY_TASK_BUNDLE, bundleA);
        intentA.putExtra(MultiWindowConfig.ARGS_SECONDARY_TASK_BUNDLE, bundleA2);
        intentA.putExtra(MultiWindowConfig.ARGS_SPILT_SCREEN_LAYOUT_TYPE, splitScreenType);
        sendBroadcast(context, intentA);
    }

    public static void sendRenameIntent(Context context, ArrayMap<String, String> srcData) {
        Intent intent = new Intent();
        intent.setAction(RECEIVER_INTENT_RENAME_ACTION);
        intent.putExtra("name", JSON.toJSONString(srcData));
        sendBroadcast(context, intent);
    }

    public static void sendAddToBookShelfIntent(Context context, String filePath) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(filePath);
        sendAddToBookShelfIntent(context, (ArrayList<String>) arrayList);
    }

    @NonNull
    private static Bundle a(Intent srcIntent) {
        ComponentName component = srcIntent.getComponent();
        Bundle extras = srcIntent.getExtras();
        Bundle bundle = extras;
        if (extras == null) {
            bundle = bundle;
            Bundle bundle2 = new Bundle();
        }
        Bundle bundle3 = bundle;
        bundle.putParcelable(MultiWindowConfig.ARGS_COMPONENT, component);
        bundle.putString(MultiWindowConfig.ARGS_ACTION, srcIntent.getAction());
        bundle.putInt(MultiWindowConfig.ARGS_FLAGS, srcIntent.getFlags());
        bundle3.putParcelable(MultiWindowConfig.COMPAT_M_ARGS_DATA, srcIntent.getData());
        return bundle3;
    }

    public static void sendDeleteIntent(Context context, String filePath) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(filePath);
        sendDeleteIntent(context, arrayList);
    }

    public static void ensureRegisterReceiver(@NonNull Context context, @NonNull BroadcastReceiver receiver, @NonNull IntentFilter intentFilter) {
        ensureRegisterReceiver(context, receiver, intentFilter, ReceiverFlags.RECEIVER_EXPORTED);
    }

    @SuppressLint({"NewApi"})
    public static void ensureRegisterReceiver(@NonNull Context context, @NonNull BroadcastReceiver receiver, @NonNull IntentFilter intentFilter, int flags) {
        try {
            context.getApplicationContext().registerReceiver(receiver, intentFilter, flags);
        } catch (Exception unused) {
        }
    }
}
