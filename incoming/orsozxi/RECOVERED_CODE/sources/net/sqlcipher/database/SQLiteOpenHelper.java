package net.sqlcipher.database;

import android.content.Context;
import android.util.Log;
import java.io.File;
import net.sqlcipher.DatabaseErrorHandler;
import net.sqlcipher.DefaultDatabaseErrorHandler;
import net.sqlcipher.database.SQLiteDatabase;

/* loaded from: classes3.dex */
public abstract class SQLiteOpenHelper {
    private static final String TAG = SQLiteOpenHelper.class.getSimpleName();
    private final Context mContext;
    private SQLiteDatabase mDatabase;
    private final DatabaseErrorHandler mErrorHandler;
    private final SQLiteDatabase.CursorFactory mFactory;
    private final SQLiteDatabaseHook mHook;
    private boolean mIsInitializing;
    private final String mName;
    private final int mNewVersion;

    public abstract void onCreate(SQLiteDatabase sQLiteDatabase);

    public void onOpen(SQLiteDatabase sQLiteDatabase) {
    }

    public abstract void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2);

    public SQLiteOpenHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i) {
        this(context, str, cursorFactory, i, null, new DefaultDatabaseErrorHandler());
    }

    public SQLiteOpenHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i, SQLiteDatabaseHook sQLiteDatabaseHook) {
        this(context, str, cursorFactory, i, sQLiteDatabaseHook, new DefaultDatabaseErrorHandler());
    }

    public SQLiteOpenHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i, SQLiteDatabaseHook sQLiteDatabaseHook, DatabaseErrorHandler databaseErrorHandler) {
        this.mDatabase = null;
        this.mIsInitializing = false;
        if (i < 1) {
            throw new IllegalArgumentException("Version must be >= 1, was " + i);
        }
        if (databaseErrorHandler == null) {
            throw new IllegalArgumentException("DatabaseErrorHandler param value can't be null.");
        }
        this.mContext = context;
        this.mName = str;
        this.mFactory = cursorFactory;
        this.mNewVersion = i;
        this.mHook = sQLiteDatabaseHook;
        this.mErrorHandler = databaseErrorHandler;
    }

    public synchronized SQLiteDatabase getWritableDatabase(String str) {
        return getWritableDatabase(str == null ? null : str.toCharArray());
    }

    public synchronized SQLiteDatabase getWritableDatabase(char[] cArr) {
        SQLiteDatabase sQLiteDatabaseOpenOrCreateDatabase;
        if (this.mDatabase != null && this.mDatabase.isOpen() && !this.mDatabase.isReadOnly()) {
            return this.mDatabase;
        }
        if (this.mIsInitializing) {
            throw new IllegalStateException("getWritableDatabase called recursively");
        }
        if (this.mDatabase != null) {
            this.mDatabase.lock();
        }
        SQLiteDatabase sQLiteDatabase = null;
        try {
            this.mIsInitializing = true;
            if (this.mName == null) {
                sQLiteDatabaseOpenOrCreateDatabase = SQLiteDatabase.create((SQLiteDatabase.CursorFactory) null, cArr);
            } else {
                String path = this.mContext.getDatabasePath(this.mName).getPath();
                File file = new File(path);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                }
                sQLiteDatabaseOpenOrCreateDatabase = SQLiteDatabase.openOrCreateDatabase(path, cArr, this.mFactory, this.mHook, this.mErrorHandler);
            }
            sQLiteDatabase = sQLiteDatabaseOpenOrCreateDatabase;
            int version = sQLiteDatabase.getVersion();
            if (version != this.mNewVersion) {
                sQLiteDatabase.beginTransaction();
                try {
                    if (version == 0) {
                        onCreate(sQLiteDatabase);
                    } else {
                        onUpgrade(sQLiteDatabase, version, this.mNewVersion);
                    }
                    sQLiteDatabase.setVersion(this.mNewVersion);
                    sQLiteDatabase.setTransactionSuccessful();
                    sQLiteDatabase.endTransaction();
                } catch (Throwable th) {
                    sQLiteDatabase.endTransaction();
                    throw th;
                }
            }
            onOpen(sQLiteDatabase);
            this.mIsInitializing = false;
            if (this.mDatabase != null) {
                try {
                    this.mDatabase.close();
                } catch (Exception unused) {
                }
                this.mDatabase.unlock();
            }
            this.mDatabase = sQLiteDatabase;
            return sQLiteDatabase;
        } catch (Throwable th2) {
            this.mIsInitializing = false;
            if (this.mDatabase != null) {
                this.mDatabase.unlock();
            }
            if (sQLiteDatabase != null) {
                sQLiteDatabase.close();
            }
            throw th2;
        }
    }

    public synchronized SQLiteDatabase getReadableDatabase(String str) {
        return getReadableDatabase(str == null ? null : str.toCharArray());
    }

    public synchronized SQLiteDatabase getReadableDatabase(char[] cArr) {
        if (this.mDatabase != null && this.mDatabase.isOpen()) {
            return this.mDatabase;
        }
        if (this.mIsInitializing) {
            throw new IllegalStateException("getReadableDatabase called recursively");
        }
        try {
            return getWritableDatabase(cArr);
        } catch (SQLiteException e) {
            if (this.mName == null) {
                throw e;
            }
            Log.e(TAG, "Couldn't open " + this.mName + " for writing (will try read-only):", e);
            SQLiteDatabase sQLiteDatabase = null;
            try {
                this.mIsInitializing = true;
                String path = this.mContext.getDatabasePath(this.mName).getPath();
                File file = new File(path);
                File file2 = new File(this.mContext.getDatabasePath(this.mName).getParent());
                if (!file2.exists()) {
                    file2.mkdirs();
                }
                if (!file.exists()) {
                    this.mIsInitializing = false;
                    SQLiteDatabase writableDatabase = getWritableDatabase(cArr);
                    this.mIsInitializing = true;
                    writableDatabase.close();
                }
                SQLiteDatabase sQLiteDatabaseOpenDatabase = SQLiteDatabase.openDatabase(path, cArr, this.mFactory, 1);
                if (sQLiteDatabaseOpenDatabase.getVersion() != this.mNewVersion) {
                    throw new SQLiteException("Can't upgrade read-only database from version " + sQLiteDatabaseOpenDatabase.getVersion() + " to " + this.mNewVersion + ": " + path);
                }
                onOpen(sQLiteDatabaseOpenDatabase);
                Log.w(TAG, "Opened " + this.mName + " in read-only mode");
                this.mDatabase = sQLiteDatabaseOpenDatabase;
                this.mIsInitializing = false;
                if (sQLiteDatabaseOpenDatabase != null && sQLiteDatabaseOpenDatabase != sQLiteDatabaseOpenDatabase) {
                    sQLiteDatabaseOpenDatabase.close();
                }
                return sQLiteDatabaseOpenDatabase;
            } catch (Throwable th) {
                this.mIsInitializing = false;
                if (0 != 0 && null != this.mDatabase) {
                    sQLiteDatabase.close();
                }
                throw th;
            }
        }
    }

    public synchronized void close() {
        if (this.mIsInitializing) {
            throw new IllegalStateException("Closed during initialization");
        }
        if (this.mDatabase != null && this.mDatabase.isOpen()) {
            this.mDatabase.close();
            this.mDatabase = null;
        }
    }
}
