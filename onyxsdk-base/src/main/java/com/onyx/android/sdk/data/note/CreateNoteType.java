// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.data.note;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0004J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000f\u001a\u00020\u0004J\u000e\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u000f\u001a\u00020\u0004J\u000e\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u000f\u001a\u00020\u0004J\u000e\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u000f\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0015" }, d2 = { "Lcom/onyx/android/sdk/data/note/CreateNoteType;", "", "()V", "CREATE_FROM_CLIPBOARD", "", "CREATE_FROM_CLIPBOARD_TO_SCRIBBLE_NOTE", "CREATE_FROM_CLIPBOARD_TO_TEXT_NOTE", "DRAFT_NOTE", "IMPORT_LOCAL_FILE", "MEETING_NOTE", "QUICK_CREATE", "SCRIBBLE_NOTE", "TEXT_NOTE", "getScene", "", "type", "isCreateFromClipboardType", "", "isCreateImportType", "isCreateScribbleType", "isEnablePageLayoutType", "onyxsdk-base_release" })
public final class CreateNoteType
{
    @NotNull
    public static final CreateNoteType INSTANCE;
    @NotNull
    public static final String SCRIBBLE_NOTE = "SCRIBBLE_NOTE";
    @NotNull
    public static final String TEXT_NOTE = "TEXT_NOTE";
    @NotNull
    public static final String DRAFT_NOTE = "DRAFT_NOTE";
    @NotNull
    public static final String MEETING_NOTE = "MEETING_NOTE";
    @NotNull
    public static final String IMPORT_LOCAL_FILE = "IMPORT_LOCAL_FILE";
    @NotNull
    public static final String CREATE_FROM_CLIPBOARD = "CREATE_FROM_CLIPBOARD";
    @NotNull
    public static final String QUICK_CREATE = "QUICK_CREATE";
    @NotNull
    public static final String CREATE_FROM_CLIPBOARD_TO_SCRIBBLE_NOTE = "CREATE_FROM_CLIPBOARD_TO_SCRIBBLE_NOTE";
    @NotNull
    public static final String CREATE_FROM_CLIPBOARD_TO_TEXT_NOTE = "CREATE_FROM_CLIPBOARD_TO_TEXT_NOTE";
    
    private CreateNoteType() {
    }
    
    static {
        INSTANCE = new CreateNoteType();
    }
    
    public final int getScene(@NotNull final String type) {
        Intrinsics.checkNotNullParameter((Object)type, "type");
        switch (type.hashCode()) {
            default: {
                return 0;
            }
            case 1778407364: {
                if (!type.equals("TEXT_NOTE")) {
                    return 0;
                }
                break;
            }
            case 1604737691: {
                if (!type.equals("CREATE_FROM_CLIPBOARD_TO_TEXT_NOTE")) {
                    return 0;
                }
                break;
            }
            case 986626762: {
                if (!type.equals("IMPORT_LOCAL_FILE")) {
                    return 0;
                }
                return 3;
            }
            case 741745558: {
                if (!type.equals("MEETING_NOTE")) {
                    return 0;
                }
                return 2;
            }
            case -851138576: {
                if (!type.equals("DRAFT_NOTE")) {
                    return 0;
                }
                return 4;
            }
            case -1107283608: {
                type.equals("CREATE_FROM_CLIPBOARD_TO_SCRIBBLE_NOTE");
                return 0;
            }
        }
        return 1;
    }
    
    public final boolean isCreateScribbleType(@NotNull final String type) {
        Intrinsics.checkNotNullParameter((Object)type, "type");
        return Intrinsics.areEqual((Object)type, (Object)"SCRIBBLE_NOTE");
    }
    
    public final boolean isCreateImportType(@NotNull final String type) {
        Intrinsics.checkNotNullParameter((Object)type, "type");
        return Intrinsics.areEqual((Object)type, (Object)"IMPORT_LOCAL_FILE");
    }
    
    public final boolean isCreateFromClipboardType(@NotNull final String type) {
        Intrinsics.checkNotNullParameter((Object)type, "type");
        return Intrinsics.areEqual((Object)type, (Object)"CREATE_FROM_CLIPBOARD_TO_SCRIBBLE_NOTE") || Intrinsics.areEqual((Object)type, (Object)"CREATE_FROM_CLIPBOARD_TO_TEXT_NOTE");
    }
    
    public final boolean isEnablePageLayoutType(@NotNull final String type) {
        Intrinsics.checkNotNullParameter((Object)type, "type");
        return NoteScene.isScribbleScene(this.getScene(type));
    }
}
