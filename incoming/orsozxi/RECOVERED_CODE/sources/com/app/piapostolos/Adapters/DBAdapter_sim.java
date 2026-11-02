package com.app.piapostolos.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/* loaded from: classes.dex */
public class DBAdapter_sim {
    public static final String[] PROJECTION_ALL = {"id", "message"};
    private static final String create = "create table login2(id integer primary key autoincrement ,message Text);";
    public static final String databasename = "dbex2";
    private static final int dbversion = 1;
    public static final String id = "id";
    public static final String message = "message";
    public static final String tablename = "login2";
    private final Context context;
    private SQLiteDatabase db;
    private DbHelper helper;

    public DBAdapter_sim open() throws SQLException {
        return this;
    }

    public DBAdapter_sim(Context context) {
        this.context = context;
        this.helper = new DbHelper(this.context);
    }

    public class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context) {
            super(context, DBAdapter_sim.databasename, (SQLiteDatabase.CursorFactory) null, 1);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
            sQLiteDatabase.execSQL(DBAdapter_sim.create);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) throws SQLException {
            Log.w("Login DB", "upgrading database from" + i + "to" + i2 + "which destroy all data");
            if (i == 1) {
                sQLiteDatabase.execSQL("DROP TABLE IF EXIST LOGIN2 ");
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

    public Cursor fetchAll() {
        SQLiteDatabase writableDatabase = this.helper.getWritableDatabase();
        this.db = writableDatabase;
        return writableDatabase.rawQuery("SELECT * FROM LOGIN2", null);
    }
}
