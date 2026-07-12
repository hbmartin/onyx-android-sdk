// 
// 

package com.onyx.android.sdk.data.sync;

import kotlin.jvm.JvmStatic;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u001e\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0004H\u0007J\u0010\u0010%\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006&" }, d2 = { "Lcom/onyx/android/sdk/data/sync/SyncRecordType;", "", "()V", "APP_DATA_RECORD_TYPE", "", "CONFIG_RECORD_TYPE", "COVER_THUMBNAIL_RECORD_TYPE", "DELETE_GROUP_TREE_RECORD_TYPE", "DELETE_LIBRARY_RECORD_TYPE", "DELETE_NOTE_RECORD_TYPE", "DELETE_SHAPE_RECORD_TYPE", "DELETE_USERDATA_RECORD_TYPE", "LIBRARY_RECORD_TYPE", "LINK_PB_FILE_RECORD_TYPE", "LOCAL_PURGE_DOC_RECORD_TYPE", "NOTE_PAGE_MODEL_RECORD_TYPE", "NOTE_RECORD_TYPE", "NOTE_TEMPLATE_RES_RECORD_TYPE", "POINT_RECORD_TYPE", "READER_BOOK_COVER_RECORD_TYPE", "READER_BOOK_RECORD_TYPE", "READER_LIBRARY_COVER_RECORD_TYPE", "RESOURCE_CONTENT_RECORD_TYPE", "RESOURCE_PB_FILE_RECORD_TYPE", "RESOURCE_RECORD_TYPE", "SHAPE_PB_FILE_RECORD_TYPE", "SHAPE_RECORD_TYPE", "SHAPE_THUMBNAIL_RECORD_TYPE", "TEMPLATE_JSON_RECORD_TYPE", "TOC_RECORD_TYPE", "UPDATE_GROUP_TREE_RECORD_TYPE", "USER_DATA_RECORD_TYPE", "VIRTUAL_DOC_PB_FILE_RECORD_TYPE", "VIRTUAL_PAGE_PB_FILE_RECORD_TYPE", "isCoverType", "", "type", "isResourceRecord", "onyxsdk-base_release" })
public final class SyncRecordType
{
    @NotNull
    public static final SyncRecordType INSTANCE;
    public static final int SHAPE_RECORD_TYPE = 0;
    public static final int POINT_RECORD_TYPE = 1;
    public static final int NOTE_RECORD_TYPE = 2;
    public static final int CONFIG_RECORD_TYPE = 3;
    public static final int RESOURCE_RECORD_TYPE = 4;
    public static final int COVER_THUMBNAIL_RECORD_TYPE = 5;
    public static final int SHAPE_THUMBNAIL_RECORD_TYPE = 6;
    public static final int APP_DATA_RECORD_TYPE = 7;
    public static final int SHAPE_PB_FILE_RECORD_TYPE = 8;
    public static final int RESOURCE_PB_FILE_RECORD_TYPE = 9;
    public static final int LIBRARY_RECORD_TYPE = 10;
    public static final int USER_DATA_RECORD_TYPE = 11;
    public static final int LINK_PB_FILE_RECORD_TYPE = 12;
    public static final int VIRTUAL_PAGE_PB_FILE_RECORD_TYPE = 13;
    public static final int VIRTUAL_DOC_PB_FILE_RECORD_TYPE = 14;
    public static final int TEMPLATE_JSON_RECORD_TYPE = 15;
    public static final int RESOURCE_CONTENT_RECORD_TYPE = 16;
    public static final int TOC_RECORD_TYPE = 17;
    public static final int NOTE_TEMPLATE_RES_RECORD_TYPE = 18;
    public static final int NOTE_PAGE_MODEL_RECORD_TYPE = 19;
    public static final int READER_BOOK_RECORD_TYPE = 20;
    public static final int READER_LIBRARY_COVER_RECORD_TYPE = 21;
    public static final int READER_BOOK_COVER_RECORD_TYPE = 22;
    public static final int DELETE_NOTE_RECORD_TYPE = 100;
    public static final int DELETE_SHAPE_RECORD_TYPE = 101;
    public static final int DELETE_LIBRARY_RECORD_TYPE = 102;
    public static final int DELETE_USERDATA_RECORD_TYPE = 103;
    public static final int LOCAL_PURGE_DOC_RECORD_TYPE = 104;
    public static final int UPDATE_GROUP_TREE_RECORD_TYPE = 200;
    public static final int DELETE_GROUP_TREE_RECORD_TYPE = 201;
    
    private SyncRecordType() {
    }
    
    @JvmStatic
    public static final boolean isResourceRecord(final int type) {
        return type == 4 || type == 5 || type == 6;
    }
    
    @JvmStatic
    public static final boolean isCoverType(final int type) {
        return type == 5;
    }
    
    static {
        INSTANCE = new SyncRecordType();
    }
}
