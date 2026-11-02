package com.app.orsozoxi.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/* loaded from: classes.dex */
public class DBAdapter_highlightAgpya {
    public static final String[] PROJECTION_ALL = {"id", "message"};
    private static final String create = "create table loginAgpya(id integer primary key autoincrement ,message text);";
    public static final String databasename = "dbexhighagpya";
    private static final int dbversion = 1;
    public static final String id = "id";
    public static final String message = "message";
    public static final String tablename = "loginAgpya";
    private final Context context;
    private SQLiteDatabase db;
    private DbHelper helper;

    public DBAdapter_highlightAgpya open() throws SQLException {
        return this;
    }

    public DBAdapter_highlightAgpya(Context context) {
        this.context = context;
        this.helper = new DbHelper(this.context);
    }

    public class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context) {
            super(context, DBAdapter_highlightAgpya.databasename, (SQLiteDatabase.CursorFactory) null, 1);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
            sQLiteDatabase.execSQL(DBAdapter_highlightAgpya.create);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) throws SQLException {
            Log.w("Login DB", "upgrading database from" + i + "to" + i2 + "which destroy all data");
            if (i == 1) {
                sQLiteDatabase.execSQL("DROP TABLE IF EXIST loginAgpya ");
            }
            onCreate(sQLiteDatabase);
        }
    }

    public void close() {
        this.helper.close();
    }

    public long insert(String str) {
        this.db = this.helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("message", str);
        return this.db.insert(tablename, null, contentValues);
    }

    public void deleteall() {
        SQLiteDatabase writableDatabase = this.helper.getWritableDatabase();
        this.db = writableDatabase;
        writableDatabase.delete(tablename, null, null);
    }

    public String fetchOne(String str) {
        SQLiteDatabase writableDatabase = this.helper.getWritableDatabase();
        this.db = writableDatabase;
        Cursor cursorRawQuery = writableDatabase.rawQuery("SELECT * FROM loginAgpya", null);
        if (!cursorRawQuery.moveToFirst()) {
            return "";
        }
        while (!cursorRawQuery.getString(1).equals(str)) {
            if (!cursorRawQuery.moveToNext()) {
                return "";
            }
        }
        return cursorRawQuery.getString(0);
    }

    public void deleteone(String str) {
        SQLiteDatabase writableDatabase = this.helper.getWritableDatabase();
        this.db = writableDatabase;
        writableDatabase.delete(tablename, "id=" + Integer.valueOf(str), null);
        this.db.close();
    }

    public Cursor fetchAll() {
        SQLiteDatabase writableDatabase = this.helper.getWritableDatabase();
        this.db = writableDatabase;
        return writableDatabase.rawQuery("SELECT * FROM loginAgpya", null);
    }
}
