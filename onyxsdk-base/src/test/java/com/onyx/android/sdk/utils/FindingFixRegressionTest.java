package com.onyx.android.sdk.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import com.onyx.android.sdk.data.DictionaryQuery;
import com.onyx.android.sdk.extension.UriKt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentResolver;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 35)
public class FindingFixRegressionTest {
    @Test
    public void uriHelpersFallBackWhenOptionalColumnsAreMissing() {
        MissingColumnsProvider provider = new MissingColumnsProvider("text/plain");
        ShadowContentResolver.registerProviderInternal("missing.columns", provider);
        Context context = RuntimeEnvironment.getApplication();
        Uri uri = Uri.parse("content://missing.columns/item");

        assertEquals(0L, UriKt.getFileSize(uri, context));
        assertTrue(provider.lastCursor.closed);
        String fallbackName = UriKt.getFileName(uri, context);
        assertFalse(fallbackName.isEmpty());
        assertFalse("value".equals(fallbackName));
        assertTrue(provider.lastCursor.closed);
    }

    @Test
    public void dictionaryQueryReturnsErrorWhenStateColumnIsMissing() {
        MissingColumnsProvider provider = new MissingColumnsProvider(null);
        ShadowContentResolver.registerProviderInternal(
                "com.onyx.dict.DictionaryProvider", provider);

        DictionaryQuery result = DictionaryUtil.queryKeyWord(
                RuntimeEnvironment.getApplication(), "word");

        assertEquals(DictionaryQuery.DICT_STATE_ERROR, result.getState());
        assertTrue(provider.lastCursor.closed);
    }

    private static final class MissingColumnsProvider extends ContentProvider {
        private final String type;
        private TrackingCursor lastCursor;

        private MissingColumnsProvider(String type) {
            this.type = type;
        }

        @Override
        public boolean onCreate() {
            return true;
        }

        @Override
        public Cursor query(
                Uri uri,
                String[] projection,
                String selection,
                String[] selectionArgs,
                String sortOrder) {
            lastCursor = new TrackingCursor();
            lastCursor.addRow(new Object[] {"value"});
            return lastCursor;
        }

        @Override
        public String getType(Uri uri) {
            return type;
        }

        @Override
        public Uri insert(Uri uri, ContentValues values) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int delete(Uri uri, String selection, String[] selectionArgs) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int update(
                Uri uri,
                ContentValues values,
                String selection,
                String[] selectionArgs) {
            throw new UnsupportedOperationException();
        }
    }

    private static final class TrackingCursor extends MatrixCursor {
        private boolean closed;

        private TrackingCursor() {
            super(new String[] {"other"});
        }

        @Override
        public void close() {
            closed = true;
            super.close();
        }
    }
}
