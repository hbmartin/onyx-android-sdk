// 
// 

package com.onyx.android.sdk.utils;

import android.database.Cursor;
import android.content.ContentValues;
import androidx.annotation.Nullable;
import java.lang.reflect.Field;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseUtils
{
    public static final String FIELD_CURSOR_WINDOW_SIZE = "sCursorWindowSize";
    public static final String DB_SUFFIX = ".db";
    public static final int INVALID_CURSOR_WINDOW_SIZE = -1;
    public static final int LARGE_CURSOR_WINDOW_SIZE = 20971520;
    
    public static SQLiteDatabase openDatabase(final String databaseName, final int flags) {
        return SQLiteDatabase.openDatabase(ResManager.getAppContext().getDatabasePath(databaseName).getAbsolutePath() + ".db", (SQLiteDatabase.CursorFactory)null, flags);
    }
    
    public static int getDBVersion(final String databasePath) {
        final SQLiteDatabase openDatabase;
        final int version = (openDatabase = SQLiteDatabase.openDatabase(databasePath, (SQLiteDatabase.CursorFactory)null, 1)).getVersion();
        openDatabase.close();
        return version;
    }
    
    public static boolean canRestoreDB(final String src, final String dst) {
        return getDBVersion(dst) >= getDBVersion(src);
    }
    
    public static boolean canAppendDB(final String src, final String dst) {
        return getDBVersion(dst) >= getDBVersion(src);
    }
    
    public static void setCursorWindowSize(final int cursorWindowSize) {
        try {
            final Field declaredField = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            try {
                declaredField.setAccessible(true);
                declaredField.set(null, cursorWindowSize);
            }
            catch (final Exception ex) {
                Debug.w((Throwable)ex);
            }
        }
        catch (final Exception ex2) {}
    }
    
    public static int getCursorWindowSize() {
        try {
            final Field declaredField = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            declaredField.setAccessible(true);
            return declaredField.getInt(null);
        }
        catch (final Exception ex) {
            Debug.w((Throwable)ex);
            return -1;
        }
    }
    
    public static long copyDBData(final String srcDBPath, final String dstDBPath, final String table, final String dataSql, final boolean deleteDstTable) {
        return copyDBData(srcDBPath, dstDBPath, table, dataSql, deleteDstTable, null);
    }
    
    public static long copyDBData(final String srcDBPath, final String dstDBPath, final String table, final String dataSql, final boolean deleteDstTable, @Nullable final UpdateValueCallback callback) {
        return copyDBData(srcDBPath, dstDBPath, table, dataSql, deleteDstTable, callback, null);
    }
    
    public static long copyDBData(final String srcDBPath, final String dstDBPath, final String table, final String dataSql, final boolean deleteDstTable, @Nullable final UpdateValueCallback updateValueCallback, @Nullable final FilterValueCallback filterValueCallback) {
        final SQLiteDatabase openDatabase;
        final SQLiteDatabase sqLiteDatabase = openDatabase = SQLiteDatabase.openDatabase(srcDBPath, (SQLiteDatabase.CursorFactory)null, 1);
        final SQLiteDatabase openDatabase2 = SQLiteDatabase.openDatabase(dstDBPath, (SQLiteDatabase.CursorFactory)null, 0);
        if (sqLiteDatabase == null || openDatabase2 == null) {
            return -1L;
        }
        if (!checkHasTable(openDatabase, table)) {
            Debug.w((Class)DatabaseUtils.class, "table is not exist\uff1a" + table, new Object[0]);
            return -2L;
        }
        if (deleteDstTable) {
            openDatabase2.delete(table, (String)null, (String[])null);
        }
        final SQLiteDatabase sqLiteDatabase2 = openDatabase;
        int i = 0;
        final Cursor rawQuery;
        if ((rawQuery = sqLiteDatabase2.rawQuery(dataSql, (String[])null)).moveToFirst()) {
            do {
                final Cursor cursor = rawQuery;
                final ContentValues contentValues = new ContentValues();
                android.database.DatabaseUtils.cursorRowToContentValues(cursor, contentValues);
                if (updateValueCallback != null) {
                    updateValueCallback.update(contentValues);
                }
                if (filterValueCallback != null && filterValueCallback.ignore(contentValues)) {
                    continue;
                }
                openDatabase2.insertWithOnConflict(table, (String)null, contentValues, 5);
                ++i;
            } while (rawQuery.moveToNext());
        }
        final int n = i;
        final SQLiteDatabase sqLiteDatabase3 = openDatabase2;
        openDatabase.close();
        sqLiteDatabase3.close();
        Debug.d((Class)DatabaseUtils.class, "copy count: " + i + ", table: " + table + ", dataSql: " + dataSql + ", srcDBPath: " + srcDBPath + ", dstDBPath: " + dstDBPath, new Object[0]);
        return n;
    }
    
    public static long getDbDataCount(final String databasePath, final String dataSql) {
        final SQLiteDatabase openDatabase;
        final long simpleQueryForLong = (openDatabase = SQLiteDatabase.openDatabase(databasePath, (SQLiteDatabase.CursorFactory)null, 1)).compileStatement(dataSql).simpleQueryForLong();
        openDatabase.close();
        return simpleQueryForLong;
    }
    
    public static void deleteTable(final String databasePath, final String table) {
        final SQLiteDatabase openDatabase = SQLiteDatabase.openDatabase(databasePath, (SQLiteDatabase.CursorFactory)null, 0);
        openDatabase.delete(table, (String)null, (String[])null);
        openDatabase.close();
    }
    
    public static boolean checkHasTable(final SQLiteDatabase database, final String table) {
        final Cursor rawQuery = database.rawQuery(String.format("select count(*) as c from sqlite_master where type = 'table' and name ='%s'", table), (String[])null);
        boolean b = false;
        while (rawQuery.moveToNext()) {
            if (rawQuery.getInt(0) > 0) {
                b = true;
                break;
            }
        }
        final boolean b2 = b;
        rawQuery.close();
        return b2;
    }
    
    public interface FilterValueCallback
    {
        boolean ignore(final ContentValues p0);
    }
    
    public interface UpdateValueCallback
    {
        void update(final ContentValues p0);
    }
}
