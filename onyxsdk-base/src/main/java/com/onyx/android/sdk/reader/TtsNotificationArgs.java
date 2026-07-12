package com.onyx.android.sdk.reader;

import android.os.Parcel;
import android.os.Parcelable;
import com.onyx.android.sdk.utils.DeviceUtils;
import com.onyx.android.sdk.utils.TTFFont;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/reader/TtsNotificationArgs.class */
public class TtsNotificationArgs implements Parcelable {
    public static final Parcelable.Creator<TtsNotificationArgs> CREATOR = new a();
    private boolean isSpeaking = false;
    private String docPath = TTFFont.UNKNOWN_FONT_NAME;
    private String activityName = TTFFont.UNKNOWN_FONT_NAME;
    private String coverPath = TTFFont.UNKNOWN_FONT_NAME;
    private String docTitle = TTFFont.UNKNOWN_FONT_NAME;
    private String chapterTitle = TTFFont.UNKNOWN_FONT_NAME;
    private TtsReaderPosition ttsInitialPosition = null;
    private TtsReaderPosition ttsInitialPagePosition = null;
    private TtsReaderPosition ttsSelectionEndPosition = null;
    private int pageEndPositionInt = DeviceUtils.NEVER_SLEEP;
    private boolean reconnected = false;
    private boolean chapterTiming = false;
    private int chapterEndPageNum = -1;
    private boolean fixedPageDocument = false;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.isSpeaking ? 1 : 0);
        parcel.writeString(this.docPath);
        parcel.writeString(this.activityName);
        parcel.writeString(this.coverPath);
        parcel.writeString(this.docTitle);
        parcel.writeString(this.chapterTitle);
        parcel.writeParcelable(this.ttsInitialPosition, i);
        parcel.writeParcelable(this.ttsInitialPagePosition, i);
        parcel.writeParcelable(this.ttsSelectionEndPosition, i);
        parcel.writeInt(this.pageEndPositionInt);
        parcel.writeInt(this.reconnected ? 1 : 0);
        parcel.writeInt(this.chapterTiming ? 1 : 0);
        parcel.writeInt(this.chapterEndPageNum);
        parcel.writeInt(this.fixedPageDocument ? 1 : 0);
    }

    public boolean isSpeaking() {
        return this.isSpeaking;
    }

    public void setSpeaking(boolean speaking) {
        this.isSpeaking = speaking;
    }

    public String getDocPath() {
        return this.docPath;
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    public String getActivityName() {
        return this.activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getCoverPath() {
        return this.coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getDocTitle() {
        return this.docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getChapterTitle() {
        return this.chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public TtsReaderPosition getTtsInitialPosition() {
        return this.ttsInitialPosition;
    }

    public void setTtsInitialPosition(TtsReaderPosition ttsInitialPosition) {
        this.ttsInitialPosition = ttsInitialPosition;
    }

    public TtsReaderPosition getTtsInitialPagePosition() {
        return this.ttsInitialPagePosition;
    }

    public void setTtsInitialPagePosition(TtsReaderPosition ttsInitialPagePosition) {
        this.ttsInitialPagePosition = ttsInitialPagePosition;
    }

    public TtsReaderPosition getTtsSelectionEndPosition() {
        return this.ttsSelectionEndPosition;
    }

    public void setTtsSelectionEndPosition(TtsReaderPosition ttsSelectionEndPosition) {
        this.ttsSelectionEndPosition = ttsSelectionEndPosition;
    }

    public int getPageEndPositionInt() {
        return this.pageEndPositionInt;
    }

    public void setPageEndPositionInt(int pageEndPositionInt) {
        this.pageEndPositionInt = pageEndPositionInt;
    }

    public boolean isReconnected() {
        return this.reconnected;
    }

    public void setReconnected(boolean reconnected) {
        this.reconnected = reconnected;
    }

    public boolean isChapterTiming() {
        return this.chapterTiming;
    }

    public void setChapterTiming(boolean chapterTiming) {
        this.chapterTiming = chapterTiming;
    }

    public int getChapterEndPageNum() {
        return this.chapterEndPageNum;
    }

    public void setChapterEndPageNum(int chapterEndPageNum) {
        this.chapterEndPageNum = chapterEndPageNum;
    }

    public boolean isFixedPageDocument() {
        return this.fixedPageDocument;
    }

    public void setFixedPageDocument(boolean fixedPageDocument) {
        this.fixedPageDocument = fixedPageDocument;
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/reader/TtsNotificationArgs$a.class */
    static class a implements Parcelable.Creator<TtsNotificationArgs> {
        a() {
        }

        @Override // android.os.Parcelable.Creator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public TtsNotificationArgs createFromParcel(Parcel source) {
            TtsNotificationArgs ttsNotificationArgs = new TtsNotificationArgs();
            ttsNotificationArgs.setSpeaking(source.readInt() != 0);
            ttsNotificationArgs.setDocPath(source.readString());
            ttsNotificationArgs.setActivityName(source.readString());
            ttsNotificationArgs.setCoverPath(source.readString());
            ttsNotificationArgs.setDocTitle(source.readString());
            ttsNotificationArgs.setChapterTitle(source.readString());
            ttsNotificationArgs.setTtsInitialPosition((TtsReaderPosition) source.readParcelable(TtsReaderPosition.class.getClassLoader()));
            ttsNotificationArgs.setTtsInitialPagePosition((TtsReaderPosition) source.readParcelable(TtsReaderPosition.class.getClassLoader()));
            ttsNotificationArgs.setTtsSelectionEndPosition((TtsReaderPosition) source.readParcelable(TtsReaderPosition.class.getClassLoader()));
            ttsNotificationArgs.setPageEndPositionInt(source.readInt());
            ttsNotificationArgs.setReconnected(source.readInt() != 0);
            ttsNotificationArgs.setChapterTiming(source.readInt() != 0);
            ttsNotificationArgs.setChapterEndPageNum(source.readInt());
            ttsNotificationArgs.setFixedPageDocument(source.readInt() != 0);
            return ttsNotificationArgs;
        }

        @Override // android.os.Parcelable.Creator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public TtsNotificationArgs[] newArray(int size) {
            return new TtsNotificationArgs[0];
        }
    }
}
