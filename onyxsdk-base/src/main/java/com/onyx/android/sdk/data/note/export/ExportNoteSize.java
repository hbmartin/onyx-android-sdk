package com.onyx.android.sdk.data.note.export;

import com.onyx.android.sdk.data.note.NoteShapeType;

public enum ExportNoteSize {
    DEFAULT,
    CUSTOM,
    A3(297, 419),
    A4(NoteShapeType.SHAPE_BOOKMARK, 297),
    A5(148, NoteShapeType.SHAPE_BOOKMARK);

    private int a;
    private int b;

    ExportNoteSize() {
        this(0, 0);
    }

    public int getWidth() {
        return this.a;
    }

    public int getHeight() {
        return this.b;
    }

    ExportNoteSize(int width, int height) {
        this.a = width;
        this.b = height;
    }
}
