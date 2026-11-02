package net.sqlcipher.database;

import android.content.ContentValues;
import android.content.Context;
import android.os.Debug;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.app.orsozoxi.Alarm.Database.Database;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.ibm.icu.text.DateFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;
import net.sqlcipher.CrossProcessCursorWrapper;
import net.sqlcipher.Cursor;
import net.sqlcipher.CursorWrapper;
import net.sqlcipher.DatabaseErrorHandler;
import net.sqlcipher.DatabaseUtils;
import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDebug;

/* loaded from: classes3.dex */
public class SQLiteDatabase extends SQLiteClosable {
    private static final String COMMIT_SQL = "COMMIT;";
    public static final int CONFLICT_ABORT = 2;
    public static final int CONFLICT_FAIL = 3;
    public static final int CONFLICT_IGNORE = 4;
    public static final int CONFLICT_NONE = 0;
    public static final int CONFLICT_REPLACE = 5;
    public static final int CONFLICT_ROLLBACK = 1;
    public static final int CREATE_IF_NECESSARY = 268435456;
    private static final int EVENT_DB_CORRUPT = 75004;
    private static final int EVENT_DB_OPERATION = 52000;
    static final String GET_LOCK_LOG_PREFIX = "GETLOCK:";
    private static final String KEY_ENCODING = "UTF-8";
    private static final int LOCK_ACQUIRED_WARNING_THREAD_TIME_IN_MS = 100;
    private static final int LOCK_ACQUIRED_WARNING_TIME_IN_MS = 300;
    private static final int LOCK_ACQUIRED_WARNING_TIME_IN_MS_ALWAYS_PRINT = 2000;
    private static final int LOCK_WARNING_WINDOW_IN_MS = 20000;
    private static final String LOG_SLOW_QUERIES_PROPERTY = "db.log.slow_query_threshold";
    public static final int MAX_SQL_CACHE_SIZE = 250;
    private static final int MAX_WARNINGS_ON_CACHESIZE_CONDITION = 1;
    public static final String MEMORY = ":memory:";
    public static final int NO_LOCALIZED_COLLATORS = 16;
    public static final int OPEN_READONLY = 1;
    public static final int OPEN_READWRITE = 0;
    private static final int OPEN_READ_MASK = 1;
    private static final int QUERY_LOG_SQL_LENGTH = 64;
    private static final int SLEEP_AFTER_YIELD_QUANTUM = 1000;
    public static final String SQLCIPHER_ANDROID_VERSION = "3.5.9";
    public static final int SQLITE_MAX_LIKE_PATTERN_LENGTH = 50000;
    private static final String TAG = "Database";
    private int mCacheFullWarnings;
    Map<String, SQLiteCompiledSql> mCompiledQueries;
    private final DatabaseErrorHandler mErrorHandler;
    private CursorFactory mFactory;
    private int mFlags;
    private boolean mInnerTransactionIsSuccessful;
    private long mLastLockMessageTime;
    private String mLastSqlStatement;
    private final ReentrantLock mLock;
    private long mLockAcquiredThreadTime;
    private long mLockAcquiredWallTime;
    private boolean mLockingEnabled;
    private int mMaxSqlCacheSize;
    long mNativeHandle;
    private int mNumCacheHits;
    private int mNumCacheMisses;
    private String mPath;
    private String mPathForLogs;
    private WeakHashMap<SQLiteClosable, Object> mPrograms;
    private final int mSlowQueryThreshold;
    private Throwable mStackTrace;
    private final Map<String, SyncUpdateInfo> mSyncUpdateInfo;
    int mTempTableSequence;
    private String mTimeClosed;
    private String mTimeOpened;
    private boolean mTransactionIsSuccessful;
    private SQLiteTransactionListener mTransactionListener;
    private static WeakHashMap<SQLiteDatabase, Object> sActiveDatabases = new WeakHashMap<>();
    private static final String[] CONFLICT_VALUES = {"", " OR ROLLBACK ", " OR ABORT ", " OR FAIL ", " OR IGNORE ", " OR REPLACE "};
    private static final Pattern EMAIL_IN_DB_PATTERN = Pattern.compile("[\\w\\.\\-]+@[\\w\\.\\-]+");
    private static int sQueryLogTimeInMillis = 0;

    public interface CursorFactory {
        Cursor newCursor(SQLiteDatabase sQLiteDatabase, SQLiteCursorDriver sQLiteCursorDriver, String str, SQLiteQuery sQLiteQuery);
    }

    public interface LibraryLoader {
        void loadLibraries(String... strArr);
    }

    private native void dbclose();

    private native void dbopen(String str, int i);

    private native void enableSqlProfiling(String str);

    private native void enableSqlTracing(String str);

    /* JADX INFO: Access modifiers changed from: private */
    public native void key(byte[] bArr) throws SQLException;

    /* JADX INFO: Access modifiers changed from: private */
    public native void key_mutf8(char[] cArr) throws SQLException;

    private native int native_getDbLookaside();

    private native void native_key(char[] cArr) throws SQLException;

    private native void native_rawExecSQL(String str);

    private native void native_rekey(String str) throws SQLException;

    private native int native_status(int i, boolean z);

    private native void rekey(byte[] bArr) throws SQLException;

    public static native int releaseMemory();

    public static native void setICURoot(String str);

    native int lastChangeCount();

    native long lastInsertRow();

    native void native_execSQL(String str) throws SQLException;

    native void native_setLocale(String str, int i);

    public int status(int i, boolean z) {
        return native_status(i, z);
    }

    public void changePassword(String str) throws SQLException {
        if (!isOpen()) {
            throw new SQLiteException("database not open");
        }
        if (str != null) {
            byte[] bytes = getBytes(str.toCharArray());
            rekey(bytes);
            for (byte b : bytes) {
            }
        }
    }

    public void changePassword(char[] cArr) throws SQLException {
        if (!isOpen()) {
            throw new SQLiteException("database not open");
        }
        if (cArr != null) {
            byte[] bytes = getBytes(cArr);
            rekey(bytes);
            for (byte b : bytes) {
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v13, types: [java.io.FileOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r8v9 */
    private static void loadICUData(Context context, File file) throws Throwable {
        ?? r8;
        ZipInputStream zipInputStream;
        OutputStream outputStream;
        ?? fileOutputStream;
        File file2 = new File(file, "icu");
        File file3 = new File(file2, "icudt46l.dat");
        ZipInputStream zipInputStream2 = null;
        try {
            if (!file2.exists()) {
                file2.mkdirs();
            }
            if (file3.exists()) {
                outputStream = null;
            } else {
                zipInputStream = new ZipInputStream(context.getAssets().open("icudt46l.zip"));
                try {
                    try {
                        zipInputStream.getNextEntry();
                        fileOutputStream = new FileOutputStream(file3);
                    } catch (Exception e) {
                        e = e;
                    }
                } catch (Throwable th) {
                    th = th;
                    r8 = zipInputStream2;
                    zipInputStream2 = zipInputStream;
                }
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = zipInputStream.read(bArr);
                        if (i <= 0) {
                            break;
                        } else {
                            fileOutputStream.write(bArr, 0, i);
                        }
                    }
                    zipInputStream2 = zipInputStream;
                    outputStream = fileOutputStream;
                } catch (Exception e2) {
                    zipInputStream2 = fileOutputStream;
                    e = e2;
                    Log.e(TAG, "Error copying icu dat file", e);
                    if (file3.exists()) {
                        file3.delete();
                    }
                    throw new RuntimeException(e);
                } catch (Throwable th2) {
                    zipInputStream2 = zipInputStream;
                    r8 = fileOutputStream;
                    th = th2;
                    if (zipInputStream2 != null) {
                        try {
                            zipInputStream2.close();
                        } catch (IOException e3) {
                            Log.e(TAG, "Error in closing streams IO streams after expanding ICU dat file", e3);
                            throw new RuntimeException(e3);
                        }
                    }
                    if (r8 != 0) {
                        r8.flush();
                        r8.close();
                    }
                    throw th;
                }
            }
            if (zipInputStream2 != null) {
                try {
                    zipInputStream2.close();
                } catch (IOException e4) {
                    Log.e(TAG, "Error in closing streams IO streams after expanding ICU dat file", e4);
                    throw new RuntimeException(e4);
                }
            }
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        } catch (Exception e5) {
            e = e5;
            zipInputStream = null;
        } catch (Throwable th3) {
            th = th3;
            r8 = 0;
        }
    }

    public static synchronized void loadLibs(Context context) {
        loadLibs(context, context.getFilesDir());
    }

    public static synchronized void loadLibs(Context context, File file) {
        loadLibs(context, file, new LibraryLoader() { // from class: net.sqlcipher.database.SQLiteDatabase.1
            @Override // net.sqlcipher.database.SQLiteDatabase.LibraryLoader
            public void loadLibraries(String... strArr) {
                for (String str : strArr) {
                    System.loadLibrary(str);
                }
            }
        });
    }

    public static synchronized void loadLibs(Context context, LibraryLoader libraryLoader) {
        loadLibs(context, context.getFilesDir(), libraryLoader);
    }

    public static synchronized void loadLibs(Context context, File file, LibraryLoader libraryLoader) {
        libraryLoader.loadLibraries("sqlcipher");
    }

    void addSQLiteClosable(SQLiteClosable sQLiteClosable) {
        lock();
        try {
            this.mPrograms.put(sQLiteClosable, null);
        } finally {
            unlock();
        }
    }

    void removeSQLiteClosable(SQLiteClosable sQLiteClosable) {
        lock();
        try {
            this.mPrograms.remove(sQLiteClosable);
        } finally {
            unlock();
        }
    }

    @Override // net.sqlcipher.database.SQLiteClosable
    protected void onAllReferencesReleased() {
        if (isOpen()) {
            if (SQLiteDebug.DEBUG_SQL_CACHE) {
                this.mTimeClosed = getTime();
            }
            dbclose();
            synchronized (sActiveDatabases) {
                sActiveDatabases.remove(this);
            }
        }
    }

    public void setLockingEnabled(boolean z) {
        this.mLockingEnabled = z;
    }

    void onCorruption() {
        Log.e(TAG, "Calling error handler for corrupt database (detected) " + this.mPath);
        this.mErrorHandler.onCorruption(this);
    }

    void lock() {
        if (this.mLockingEnabled) {
            this.mLock.lock();
            if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING && this.mLock.getHoldCount() == 1) {
                this.mLockAcquiredWallTime = SystemClock.elapsedRealtime();
                this.mLockAcquiredThreadTime = Debug.threadCpuTimeNanos();
            }
        }
    }

    private void lockForced() {
        this.mLock.lock();
        if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING && this.mLock.getHoldCount() == 1) {
            this.mLockAcquiredWallTime = SystemClock.elapsedRealtime();
            this.mLockAcquiredThreadTime = Debug.threadCpuTimeNanos();
        }
    }

    void unlock() {
        if (this.mLockingEnabled) {
            if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING && this.mLock.getHoldCount() == 1) {
                checkLockHoldTime();
            }
            this.mLock.unlock();
        }
    }

    private void unlockForced() {
        if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING && this.mLock.getHoldCount() == 1) {
            checkLockHoldTime();
        }
        this.mLock.unlock();
    }

    private void checkLockHoldTime() {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long j = jElapsedRealtime - this.mLockAcquiredWallTime;
        if ((j >= 2000 || Log.isLoggable(TAG, 2) || jElapsedRealtime - this.mLastLockMessageTime >= 20000) && j > 300) {
            int iThreadCpuTimeNanos = (int) ((Debug.threadCpuTimeNanos() - this.mLockAcquiredThreadTime) / 1000000);
            if (iThreadCpuTimeNanos > 100 || j > 2000) {
                this.mLastLockMessageTime = jElapsedRealtime;
                String str = "lock held on " + this.mPath + " for " + j + "ms. Thread time was " + iThreadCpuTimeNanos + DateFormat.MINUTE_SECOND;
                if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING_STACK_TRACE) {
                    Log.d(TAG, str, new Exception());
                } else {
                    Log.d(TAG, str);
                }
            }
        }
    }

    public void beginTransaction() {
        beginTransactionWithListener(null);
    }

    public void beginTransactionWithListener(SQLiteTransactionListener sQLiteTransactionListener) {
        lockForced();
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        try {
            if (this.mLock.getHoldCount() > 1) {
                if (this.mInnerTransactionIsSuccessful) {
                    IllegalStateException illegalStateException = new IllegalStateException("Cannot call beginTransaction between calling setTransactionSuccessful and endTransaction");
                    Log.e(TAG, "beginTransaction() failed", illegalStateException);
                    throw illegalStateException;
                }
                return;
            }
            execSQL("BEGIN EXCLUSIVE;");
            this.mTransactionListener = sQLiteTransactionListener;
            this.mTransactionIsSuccessful = true;
            this.mInnerTransactionIsSuccessful = false;
            if (sQLiteTransactionListener != null) {
                try {
                    sQLiteTransactionListener.onBegin();
                } catch (RuntimeException e) {
                    execSQL("ROLLBACK;");
                    throw e;
                }
            }
        } catch (Throwable th) {
            unlockForced();
            throw th;
        }
    }

    public void endTransaction() {
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        if (!this.mLock.isHeldByCurrentThread()) {
            throw new IllegalStateException("no transaction pending");
        }
        try {
            if (this.mInnerTransactionIsSuccessful) {
                this.mInnerTransactionIsSuccessful = false;
            } else {
                this.mTransactionIsSuccessful = false;
            }
            if (this.mLock.getHoldCount() != 1) {
                return;
            }
            if (this.mTransactionListener != null) {
                try {
                    if (this.mTransactionIsSuccessful) {
                        this.mTransactionListener.onCommit();
                    } else {
                        this.mTransactionListener.onRollback();
                    }
                    e = null;
                } catch (RuntimeException e) {
                    e = e;
                    this.mTransactionIsSuccessful = false;
                }
            } else {
                e = null;
            }
            if (this.mTransactionIsSuccessful) {
                execSQL(COMMIT_SQL);
            } else {
                try {
                    execSQL("ROLLBACK;");
                    if (e != null) {
                        throw e;
                    }
                } catch (SQLException unused) {
                    Log.d(TAG, "exception during rollback, maybe the DB previously performed an auto-rollback");
                }
            }
        } finally {
            this.mTransactionListener = null;
            unlockForced();
        }
    }

    public void setTransactionSuccessful() {
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        if (!this.mLock.isHeldByCurrentThread()) {
            throw new IllegalStateException("no transaction pending");
        }
        if (this.mInnerTransactionIsSuccessful) {
            throw new IllegalStateException("setTransactionSuccessful may only be called once per call to beginTransaction");
        }
        this.mInnerTransactionIsSuccessful = true;
    }

    public boolean inTransaction() {
        return this.mLock.getHoldCount() > 0;
    }

    public boolean isDbLockedByCurrentThread() {
        return this.mLock.isHeldByCurrentThread();
    }

    public boolean isDbLockedByOtherThreads() {
        return !this.mLock.isHeldByCurrentThread() && this.mLock.isLocked();
    }

    @Deprecated
    public boolean yieldIfContended() {
        if (isOpen()) {
            return yieldIfContendedHelper(false, -1L);
        }
        return false;
    }

    public boolean yieldIfContendedSafely() {
        if (isOpen()) {
            return yieldIfContendedHelper(true, -1L);
        }
        return false;
    }

    public boolean yieldIfContendedSafely(long j) {
        if (isOpen()) {
            return yieldIfContendedHelper(true, j);
        }
        return false;
    }

    private boolean yieldIfContendedHelper(boolean z, long j) throws InterruptedException {
        if (this.mLock.getQueueLength() == 0) {
            this.mLockAcquiredWallTime = SystemClock.elapsedRealtime();
            this.mLockAcquiredThreadTime = Debug.threadCpuTimeNanos();
            return false;
        }
        setTransactionSuccessful();
        SQLiteTransactionListener sQLiteTransactionListener = this.mTransactionListener;
        endTransaction();
        if (z && isDbLockedByCurrentThread()) {
            throw new IllegalStateException("Db locked more than once. yielfIfContended cannot yield");
        }
        if (j > 0) {
            while (j > 0) {
                try {
                    Thread.sleep(j < 1000 ? j : 1000L);
                } catch (InterruptedException unused) {
                    Thread.interrupted();
                }
                j -= 1000;
                if (this.mLock.getQueueLength() == 0) {
                    break;
                }
            }
        }
        beginTransactionWithListener(sQLiteTransactionListener);
        return true;
    }

    public Map<String, String> getSyncedTables() {
        HashMap map;
        synchronized (this.mSyncUpdateInfo) {
            map = new HashMap();
            for (String str : this.mSyncUpdateInfo.keySet()) {
                SyncUpdateInfo syncUpdateInfo = this.mSyncUpdateInfo.get(str);
                if (syncUpdateInfo.deletedTable != null) {
                    map.put(str, syncUpdateInfo.deletedTable);
                }
            }
        }
        return map;
    }

    private static class SyncUpdateInfo {
        String deletedTable;
        String foreignKey;
        String masterTable;

        SyncUpdateInfo(String str, String str2, String str3) {
            this.masterTable = str;
            this.deletedTable = str2;
            this.foreignKey = str3;
        }
    }

    public static SQLiteDatabase openDatabase(String str, String str2, CursorFactory cursorFactory, int i) {
        return openDatabase(str, str2, cursorFactory, i, (SQLiteDatabaseHook) null);
    }

    public static SQLiteDatabase openDatabase(String str, char[] cArr, CursorFactory cursorFactory, int i) {
        return openDatabase(str, cArr, cursorFactory, i, (SQLiteDatabaseHook) null, (DatabaseErrorHandler) null);
    }

    public static SQLiteDatabase openDatabase(String str, String str2, CursorFactory cursorFactory, int i, SQLiteDatabaseHook sQLiteDatabaseHook) {
        return openDatabase(str, str2, cursorFactory, i, sQLiteDatabaseHook, (DatabaseErrorHandler) null);
    }

    public static SQLiteDatabase openDatabase(String str, char[] cArr, CursorFactory cursorFactory, int i, SQLiteDatabaseHook sQLiteDatabaseHook) {
        return openDatabase(str, cArr, cursorFactory, i, sQLiteDatabaseHook, (DatabaseErrorHandler) null);
    }

    public static SQLiteDatabase openDatabase(String str, String str2, CursorFactory cursorFactory, int i, SQLiteDatabaseHook sQLiteDatabaseHook, DatabaseErrorHandler databaseErrorHandler) {
        return openDatabase(str, str2 == null ? null : str2.toCharArray(), cursorFactory, i, sQLiteDatabaseHook, databaseErrorHandler);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0048 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static net.sqlcipher.database.SQLiteDatabase openDatabase(java.lang.String r6, char[] r7, net.sqlcipher.database.SQLiteDatabase.CursorFactory r8, int r9, net.sqlcipher.database.SQLiteDatabaseHook r10, net.sqlcipher.DatabaseErrorHandler r11) {
        /*
            if (r11 == 0) goto L3
            goto L8
        L3:
            net.sqlcipher.DefaultDatabaseErrorHandler r11 = new net.sqlcipher.DefaultDatabaseErrorHandler
            r11.<init>()
        L8:
            r0 = 0
            net.sqlcipher.database.SQLiteDatabase r1 = new net.sqlcipher.database.SQLiteDatabase     // Catch: net.sqlcipher.database.SQLiteDatabaseCorruptException -> L14
            r1.<init>(r6, r8, r9, r11)     // Catch: net.sqlcipher.database.SQLiteDatabaseCorruptException -> L14
            r1.openDatabaseInternal(r7, r10)     // Catch: net.sqlcipher.database.SQLiteDatabaseCorruptException -> L12
            goto L37
        L12:
            r2 = move-exception
            goto L16
        L14:
            r2 = move-exception
            r1 = r0
        L16:
            java.lang.String r3 = "Database"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Calling error handler for corrupt database "
            r4.append(r5)
            r4.append(r6)
            java.lang.String r4 = r4.toString()
            android.util.Log.e(r3, r4, r2)
            r11.onCorruption(r1)
            net.sqlcipher.database.SQLiteDatabase r1 = new net.sqlcipher.database.SQLiteDatabase
            r1.<init>(r6, r8, r9, r11)
            r1.openDatabaseInternal(r7, r10)
        L37:
            boolean r7 = net.sqlcipher.database.SQLiteDebug.DEBUG_SQL_STATEMENTS
            if (r7 == 0) goto L3e
            r1.enableSqlTracing(r6)
        L3e:
            boolean r7 = net.sqlcipher.database.SQLiteDebug.DEBUG_SQL_TIME
            if (r7 == 0) goto L45
            r1.enableSqlProfiling(r6)
        L45:
            java.util.WeakHashMap<net.sqlcipher.database.SQLiteDatabase, java.lang.Object> r6 = net.sqlcipher.database.SQLiteDatabase.sActiveDatabases
            monitor-enter(r6)
            java.util.WeakHashMap<net.sqlcipher.database.SQLiteDatabase, java.lang.Object> r7 = net.sqlcipher.database.SQLiteDatabase.sActiveDatabases     // Catch: java.lang.Throwable -> L4f
            r7.put(r1, r0)     // Catch: java.lang.Throwable -> L4f
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L4f
            return r1
        L4f:
            r7 = move-exception
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L4f
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: net.sqlcipher.database.SQLiteDatabase.openDatabase(java.lang.String, char[], net.sqlcipher.database.SQLiteDatabase$CursorFactory, int, net.sqlcipher.database.SQLiteDatabaseHook, net.sqlcipher.DatabaseErrorHandler):net.sqlcipher.database.SQLiteDatabase");
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, String str, CursorFactory cursorFactory, SQLiteDatabaseHook sQLiteDatabaseHook) {
        return openOrCreateDatabase(file, str, cursorFactory, sQLiteDatabaseHook, (DatabaseErrorHandler) null);
    }

    public static SQLiteDatabase openOrCreateDatabase(String str, String str2, CursorFactory cursorFactory, SQLiteDatabaseHook sQLiteDatabaseHook) {
        return openDatabase(str, str2, cursorFactory, CREATE_IF_NECESSARY, sQLiteDatabaseHook);
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, String str, CursorFactory cursorFactory, SQLiteDatabaseHook sQLiteDatabaseHook, DatabaseErrorHandler databaseErrorHandler) {
        return openOrCreateDatabase(file == null ? null : file.getPath(), str, cursorFactory, sQLiteDatabaseHook, databaseErrorHandler);
    }

    public static SQLiteDatabase openOrCreateDatabase(String str, String str2, CursorFactory cursorFactory, SQLiteDatabaseHook sQLiteDatabaseHook, DatabaseErrorHandler databaseErrorHandler) {
        return openDatabase(str, str2 == null ? null : str2.toCharArray(), cursorFactory, CREATE_IF_NECESSARY, sQLiteDatabaseHook, databaseErrorHandler);
    }

    public static SQLiteDatabase openOrCreateDatabase(String str, char[] cArr, CursorFactory cursorFactory, SQLiteDatabaseHook sQLiteDatabaseHook) {
        return openDatabase(str, cArr, cursorFactory, CREATE_IF_NECESSARY, sQLiteDatabaseHook);
    }

    public static SQLiteDatabase openOrCreateDatabase(String str, char[] cArr, CursorFactory cursorFactory, SQLiteDatabaseHook sQLiteDatabaseHook, DatabaseErrorHandler databaseErrorHandler) {
        return openDatabase(str, cArr, cursorFactory, CREATE_IF_NECESSARY, sQLiteDatabaseHook, databaseErrorHandler);
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, String str, CursorFactory cursorFactory) {
        return openOrCreateDatabase(file, str, cursorFactory, (SQLiteDatabaseHook) null);
    }

    public static SQLiteDatabase openOrCreateDatabase(String str, String str2, CursorFactory cursorFactory) {
        return openDatabase(str, str2, cursorFactory, CREATE_IF_NECESSARY, (SQLiteDatabaseHook) null);
    }

    public static SQLiteDatabase openOrCreateDatabase(String str, char[] cArr, CursorFactory cursorFactory) {
        return openDatabase(str, cArr, cursorFactory, CREATE_IF_NECESSARY, (SQLiteDatabaseHook) null);
    }

    public static SQLiteDatabase create(CursorFactory cursorFactory, String str) {
        return openDatabase(MEMORY, str == null ? null : str.toCharArray(), cursorFactory, CREATE_IF_NECESSARY);
    }

    public static SQLiteDatabase create(CursorFactory cursorFactory, char[] cArr) {
        return openDatabase(MEMORY, cArr, cursorFactory, CREATE_IF_NECESSARY);
    }

    public void close() {
        if (isOpen()) {
            lock();
            try {
                closeClosable();
                onAllReferencesReleased();
            } finally {
                unlock();
            }
        }
    }

    private void closeClosable() {
        deallocCachedSqlStatements();
        Iterator<Map.Entry<SQLiteClosable, Object>> it2 = this.mPrograms.entrySet().iterator();
        while (it2.hasNext()) {
            SQLiteClosable key = it2.next().getKey();
            if (key != null) {
                key.onAllReferencesReleasedFromContainer();
            }
        }
    }

    public int getVersion() throws Throwable {
        SQLiteStatement sQLiteStatement;
        Throwable th;
        lock();
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        try {
            sQLiteStatement = new SQLiteStatement(this, "PRAGMA user_version;");
            try {
                int iSimpleQueryForLong = (int) sQLiteStatement.simpleQueryForLong();
                sQLiteStatement.close();
                unlock();
                return iSimpleQueryForLong;
            } catch (Throwable th2) {
                th = th2;
                if (sQLiteStatement != null) {
                    sQLiteStatement.close();
                }
                unlock();
                throw th;
            }
        } catch (Throwable th3) {
            sQLiteStatement = null;
            th = th3;
        }
    }

    public void setVersion(int i) throws SQLException {
        execSQL("PRAGMA user_version = " + i);
    }

    public long getMaximumSize() throws Throwable {
        SQLiteStatement sQLiteStatement;
        Throwable th;
        lock();
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        try {
            sQLiteStatement = new SQLiteStatement(this, "PRAGMA max_page_count;");
            try {
                long jSimpleQueryForLong = sQLiteStatement.simpleQueryForLong() * getPageSize();
                sQLiteStatement.close();
                unlock();
                return jSimpleQueryForLong;
            } catch (Throwable th2) {
                th = th2;
                if (sQLiteStatement != null) {
                    sQLiteStatement.close();
                }
                unlock();
                throw th;
            }
        } catch (Throwable th3) {
            sQLiteStatement = null;
            th = th3;
        }
    }

    public long setMaximumSize(long j) throws Throwable {
        long pageSize;
        SQLiteStatement sQLiteStatement;
        lock();
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        SQLiteStatement sQLiteStatement2 = null;
        try {
            pageSize = getPageSize();
            long j2 = j / pageSize;
            if (j % pageSize != 0) {
                j2++;
            }
            sQLiteStatement = new SQLiteStatement(this, "PRAGMA max_page_count = " + j2);
        } catch (Throwable th) {
            th = th;
        }
        try {
            long jSimpleQueryForLong = sQLiteStatement.simpleQueryForLong() * pageSize;
            sQLiteStatement.close();
            unlock();
            return jSimpleQueryForLong;
        } catch (Throwable th2) {
            th = th2;
            sQLiteStatement2 = sQLiteStatement;
            if (sQLiteStatement2 != null) {
                sQLiteStatement2.close();
            }
            unlock();
            throw th;
        }
    }

    public long getPageSize() throws Throwable {
        SQLiteStatement sQLiteStatement;
        Throwable th;
        lock();
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        try {
            sQLiteStatement = new SQLiteStatement(this, "PRAGMA page_size;");
            try {
                long jSimpleQueryForLong = sQLiteStatement.simpleQueryForLong();
                sQLiteStatement.close();
                unlock();
                return jSimpleQueryForLong;
            } catch (Throwable th2) {
                th = th2;
                if (sQLiteStatement != null) {
                    sQLiteStatement.close();
                }
                unlock();
                throw th;
            }
        } catch (Throwable th3) {
            sQLiteStatement = null;
            th = th3;
        }
    }

    public void setPageSize(long j) throws SQLException {
        execSQL("PRAGMA page_size = " + j);
    }

    public void markTableSyncable(String str, String str2) {
        if (!isOpen()) {
            throw new SQLiteException("database not open");
        }
        markTableSyncable(str, Database.COLUMN_ALARM_ID, str, str2);
    }

    public void markTableSyncable(String str, String str2, String str3) {
        if (!isOpen()) {
            throw new SQLiteException("database not open");
        }
        markTableSyncable(str, str2, str3, null);
    }

    private void markTableSyncable(String str, String str2, String str3, String str4) {
        lock();
        try {
            native_execSQL("SELECT _sync_dirty FROM " + str3 + " LIMIT 0");
            native_execSQL("SELECT " + str2 + " FROM " + str + " LIMIT 0");
            unlock();
            SyncUpdateInfo syncUpdateInfo = new SyncUpdateInfo(str3, str4, str2);
            synchronized (this.mSyncUpdateInfo) {
                this.mSyncUpdateInfo.put(str, syncUpdateInfo);
            }
        } catch (Throwable th) {
            unlock();
            throw th;
        }
    }

    void rowUpdated(String str, long j) throws SQLException {
        SyncUpdateInfo syncUpdateInfo;
        synchronized (this.mSyncUpdateInfo) {
            syncUpdateInfo = this.mSyncUpdateInfo.get(str);
        }
        if (syncUpdateInfo != null) {
            execSQL("UPDATE " + syncUpdateInfo.masterTable + " SET _sync_dirty=1 WHERE _id=(SELECT " + syncUpdateInfo.foreignKey + " FROM " + str + " WHERE _id=" + j + ")");
        }
    }

    public static String findEditTable(String str) {
        if (!TextUtils.isEmpty(str)) {
            int iIndexOf = str.indexOf(32);
            int iIndexOf2 = str.indexOf(44);
            if (iIndexOf <= 0 || (iIndexOf >= iIndexOf2 && iIndexOf2 >= 0)) {
                return iIndexOf2 > 0 ? (iIndexOf2 < iIndexOf || iIndexOf < 0) ? str.substring(0, iIndexOf2) : str : str;
            }
            return str.substring(0, iIndexOf);
        }
        throw new IllegalStateException("Invalid tables");
    }

    public SQLiteStatement compileStatement(String str) throws SQLException {
        lock();
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        try {
            return new SQLiteStatement(this, str);
        } finally {
            unlock();
        }
    }

    public Cursor query(boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6) {
        return queryWithFactory(null, z, str, strArr, str2, strArr2, str3, str4, str5, str6);
    }

    public Cursor queryWithFactory(CursorFactory cursorFactory, boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6) {
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        return rawQueryWithFactory(cursorFactory, SQLiteQueryBuilder.buildQueryString(z, str, strArr, str2, str3, str4, str5, str6), strArr2, findEditTable(str));
    }

    public Cursor query(String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5) {
        return query(false, str, strArr, str2, strArr2, str3, str4, str5, null);
    }

    public Cursor query(String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6) {
        return query(false, str, strArr, str2, strArr2, str3, str4, str5, str6);
    }

    public Cursor rawQuery(String str, String[] strArr) {
        return rawQueryWithFactory(null, str, strArr, null);
    }

    /* JADX WARN: Finally extract failed */
    public Cursor rawQuery(String str, Object[] objArr) {
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        long jCurrentTimeMillis = this.mSlowQueryThreshold != -1 ? System.currentTimeMillis() : 0L;
        SQLiteDirectCursorDriver sQLiteDirectCursorDriver = new SQLiteDirectCursorDriver(this, str, null);
        try {
            Cursor cursorQuery = sQLiteDirectCursorDriver.query(this.mFactory, objArr);
            if (this.mSlowQueryThreshold != -1) {
                int count = cursorQuery != null ? cursorQuery.getCount() : -1;
                long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
                if (jCurrentTimeMillis2 >= this.mSlowQueryThreshold) {
                    Log.v(TAG, "query (" + jCurrentTimeMillis2 + " ms): " + sQLiteDirectCursorDriver.toString() + ", args are <redacted>, count is " + count);
                }
            }
            return new CrossProcessCursorWrapper(cursorQuery);
        } catch (Throwable th) {
            if (this.mSlowQueryThreshold != -1) {
                long jCurrentTimeMillis3 = System.currentTimeMillis() - jCurrentTimeMillis;
                if (jCurrentTimeMillis3 >= this.mSlowQueryThreshold) {
                    Log.v(TAG, "query (" + jCurrentTimeMillis3 + " ms): " + sQLiteDirectCursorDriver.toString() + ", args are <redacted>, count is -1");
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Finally extract failed */
    public Cursor rawQueryWithFactory(CursorFactory cursorFactory, String str, String[] strArr, String str2) {
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        long jCurrentTimeMillis = this.mSlowQueryThreshold != -1 ? System.currentTimeMillis() : 0L;
        SQLiteDirectCursorDriver sQLiteDirectCursorDriver = new SQLiteDirectCursorDriver(this, str, str2);
        if (cursorFactory == null) {
            try {
                cursorFactory = this.mFactory;
            } catch (Throwable th) {
                if (this.mSlowQueryThreshold != -1) {
                    long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
                    if (jCurrentTimeMillis2 >= this.mSlowQueryThreshold) {
                        Log.v(TAG, "query (" + jCurrentTimeMillis2 + " ms): " + sQLiteDirectCursorDriver.toString() + ", args are <redacted>, count is -1");
                    }
                }
                throw th;
            }
        }
        Cursor cursorQuery = sQLiteDirectCursorDriver.query(cursorFactory, strArr);
        if (this.mSlowQueryThreshold != -1) {
            int count = cursorQuery != null ? cursorQuery.getCount() : -1;
            long jCurrentTimeMillis3 = System.currentTimeMillis() - jCurrentTimeMillis;
            if (jCurrentTimeMillis3 >= this.mSlowQueryThreshold) {
                Log.v(TAG, "query (" + jCurrentTimeMillis3 + " ms): " + sQLiteDirectCursorDriver.toString() + ", args are <redacted>, count is " + count);
            }
        }
        return new CrossProcessCursorWrapper(cursorQuery);
    }

    public Cursor rawQuery(String str, String[] strArr, int i, int i2) {
        CursorWrapper cursorWrapper = (CursorWrapper) rawQueryWithFactory(null, str, strArr, null);
        ((SQLiteCursor) cursorWrapper.getWrappedCursor()).setLoadStyle(i, i2);
        return cursorWrapper;
    }

    public long insert(String str, String str2, ContentValues contentValues) {
        try {
            return insertWithOnConflict(str, str2, contentValues, 0);
        } catch (SQLException e) {
            Log.e(TAG, "Error inserting <redacted values> into " + str, e);
            return -1L;
        }
    }

    public long insertOrThrow(String str, String str2, ContentValues contentValues) throws SQLException {
        return insertWithOnConflict(str, str2, contentValues, 0);
    }

    public long replace(String str, String str2, ContentValues contentValues) {
        try {
            return insertWithOnConflict(str, str2, contentValues, 5);
        } catch (SQLException e) {
            Log.e(TAG, "Error inserting <redacted values> into " + str, e);
            return -1L;
        }
    }

    public long replaceOrThrow(String str, String str2, ContentValues contentValues) throws SQLException {
        return insertWithOnConflict(str, str2, contentValues, 5);
    }

    public long insertWithOnConflict(String str, String str2, ContentValues contentValues, int i) {
        Set<Map.Entry<String, Object>> setValueSet;
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        StringBuilder sb = new StringBuilder(152);
        sb.append("INSERT");
        sb.append(CONFLICT_VALUES[i]);
        sb.append(" INTO ");
        sb.append(str);
        StringBuilder sb2 = new StringBuilder(40);
        int i2 = 0;
        SQLiteProgram sQLiteProgram = null;
        if (contentValues != null && contentValues.size() > 0) {
            setValueSet = contentValues.valueSet();
            Iterator<Map.Entry<String, Object>> it2 = setValueSet.iterator();
            sb.append('(');
            boolean z = false;
            while (it2.hasNext()) {
                if (z) {
                    sb.append(", ");
                    sb2.append(", ");
                }
                sb.append(it2.next().getKey());
                sb2.append('?');
                z = true;
            }
            sb.append(')');
        } else {
            sb.append("(" + str2 + ") ");
            sb2.append("NULL");
            setValueSet = null;
        }
        sb.append(" VALUES(");
        sb.append((CharSequence) sb2);
        sb.append(");");
        lock();
        try {
            try {
                SQLiteStatement sQLiteStatementCompileStatement = compileStatement(sb.toString());
                if (setValueSet != null) {
                    int size = setValueSet.size();
                    Iterator<Map.Entry<String, Object>> it3 = setValueSet.iterator();
                    while (i2 < size) {
                        i2++;
                        DatabaseUtils.bindObjectToProgram(sQLiteStatementCompileStatement, i2, it3.next().getValue());
                    }
                }
                sQLiteStatementCompileStatement.execute();
                long jLastInsertRow = lastChangeCount() > 0 ? lastInsertRow() : -1L;
                if (jLastInsertRow == -1) {
                    Log.e(TAG, "Error inserting <redacted values> using <redacted sql> into " + str);
                } else if (Log.isLoggable(TAG, 2)) {
                    Log.v(TAG, "Inserting row " + jLastInsertRow + " from <redacted values> using <redacted sql> into " + str);
                }
                if (sQLiteStatementCompileStatement != null) {
                    sQLiteStatementCompileStatement.close();
                }
                unlock();
                return jLastInsertRow;
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption();
                throw e;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                sQLiteProgram.close();
            }
            unlock();
            throw th;
        }
    }

    public int delete(String str, String str2, String[] strArr) {
        String str3;
        lock();
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("DELETE FROM ");
                sb.append(str);
                if (TextUtils.isEmpty(str2)) {
                    str3 = "";
                } else {
                    str3 = " WHERE " + str2;
                }
                sb.append(str3);
                sQLiteStatementCompileStatement = compileStatement(sb.toString());
                if (strArr != null) {
                    int length = strArr.length;
                    int i = 0;
                    while (i < length) {
                        int i2 = i + 1;
                        DatabaseUtils.bindObjectToProgram(sQLiteStatementCompileStatement, i2, strArr[i]);
                        i = i2;
                    }
                }
                sQLiteStatementCompileStatement.execute();
                return lastChangeCount();
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption();
                throw e;
            }
        } finally {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
            unlock();
        }
    }

    public int update(String str, ContentValues contentValues, String str2, String[] strArr) {
        return updateWithOnConflict(str, contentValues, str2, strArr, 0);
    }

    public int updateWithOnConflict(String str, ContentValues contentValues, String str2, String[] strArr, int i) {
        if (contentValues == null || contentValues.size() == 0) {
            throw new IllegalArgumentException("Empty values");
        }
        StringBuilder sb = new StringBuilder(120);
        sb.append("UPDATE ");
        sb.append(CONFLICT_VALUES[i]);
        sb.append(str);
        sb.append(" SET ");
        Set<Map.Entry<String, Object>> setValueSet = contentValues.valueSet();
        Iterator<Map.Entry<String, Object>> it2 = setValueSet.iterator();
        while (it2.hasNext()) {
            sb.append(it2.next().getKey());
            sb.append("=?");
            if (it2.hasNext()) {
                sb.append(", ");
            }
        }
        if (!TextUtils.isEmpty(str2)) {
            sb.append(" WHERE ");
            sb.append(str2);
        }
        lock();
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                sQLiteStatementCompileStatement = compileStatement(sb.toString());
                int size = setValueSet.size();
                Iterator<Map.Entry<String, Object>> it3 = setValueSet.iterator();
                int i2 = 1;
                for (int i3 = 0; i3 < size; i3++) {
                    DatabaseUtils.bindObjectToProgram(sQLiteStatementCompileStatement, i2, it3.next().getValue());
                    i2++;
                }
                if (strArr != null) {
                    for (String str3 : strArr) {
                        sQLiteStatementCompileStatement.bindString(i2, str3);
                        i2++;
                    }
                }
                sQLiteStatementCompileStatement.execute();
                int iLastChangeCount = lastChangeCount();
                if (Log.isLoggable(TAG, 2)) {
                    Log.v(TAG, "Updated " + iLastChangeCount + " rows using <redacted values> and <redacted sql> for " + str);
                }
                return iLastChangeCount;
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption();
                throw e;
            } catch (SQLException e2) {
                Log.e(TAG, "Error updating <redacted values> using <redacted sql> for " + str);
                throw e2;
            }
        } finally {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
            unlock();
        }
    }

    public void execSQL(String str) throws SQLException {
        SystemClock.uptimeMillis();
        lock();
        try {
            if (!isOpen()) {
                throw new IllegalStateException("database not open");
            }
            try {
                native_execSQL(str);
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption();
                throw e;
            }
        } finally {
            unlock();
        }
    }

    public void rawExecSQL(String str) {
        SystemClock.uptimeMillis();
        lock();
        try {
            if (!isOpen()) {
                throw new IllegalStateException("database not open");
            }
            try {
                native_rawExecSQL(str);
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption();
                throw e;
            }
        } finally {
            unlock();
        }
    }

    public void execSQL(String str, Object[] objArr) throws SQLException {
        if (objArr == null) {
            throw new IllegalArgumentException("Empty bindArgs");
        }
        SystemClock.uptimeMillis();
        lock();
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                sQLiteStatementCompileStatement = compileStatement(str);
                if (objArr != null) {
                    int length = objArr.length;
                    int i = 0;
                    while (i < length) {
                        int i2 = i + 1;
                        DatabaseUtils.bindObjectToProgram(sQLiteStatementCompileStatement, i2, objArr[i]);
                        i = i2;
                    }
                }
                sQLiteStatementCompileStatement.execute();
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption();
                throw e;
            }
        } finally {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
            unlock();
        }
    }

    protected void finalize() {
        if (isOpen()) {
            Log.e(TAG, "close() was never explicitly called on database '" + this.mPath + "' ", this.mStackTrace);
            closeClosable();
            onAllReferencesReleased();
        }
    }

    public SQLiteDatabase(String str, char[] cArr, CursorFactory cursorFactory, int i) {
        this(str, cursorFactory, i, (DatabaseErrorHandler) null);
        openDatabaseInternal(cArr, null);
    }

    public SQLiteDatabase(String str, char[] cArr, CursorFactory cursorFactory, int i, SQLiteDatabaseHook sQLiteDatabaseHook) {
        this(str, cursorFactory, i, (DatabaseErrorHandler) null);
        openDatabaseInternal(cArr, sQLiteDatabaseHook);
    }

    private SQLiteDatabase(String str, CursorFactory cursorFactory, int i, DatabaseErrorHandler databaseErrorHandler) {
        this.mLock = new ReentrantLock(true);
        this.mLockAcquiredWallTime = 0L;
        this.mLockAcquiredThreadTime = 0L;
        this.mLastLockMessageTime = 0L;
        this.mLastSqlStatement = null;
        this.mNativeHandle = 0L;
        this.mTempTableSequence = 0;
        this.mPathForLogs = null;
        this.mCompiledQueries = new HashMap();
        this.mMaxSqlCacheSize = 250;
        this.mTimeOpened = null;
        this.mTimeClosed = null;
        this.mStackTrace = null;
        this.mLockingEnabled = true;
        this.mSyncUpdateInfo = new HashMap();
        if (str == null) {
            throw new IllegalArgumentException("path should not be null");
        }
        this.mFlags = i;
        this.mPath = str;
        this.mSlowQueryThreshold = -1;
        this.mStackTrace = new DatabaseObjectNotClosedException().fillInStackTrace();
        this.mFactory = cursorFactory;
        this.mPrograms = new WeakHashMap<>();
        this.mErrorHandler = databaseErrorHandler;
    }

    private void openDatabaseInternal(final char[] cArr, SQLiteDatabaseHook sQLiteDatabaseHook) {
        final byte[] bytes = getBytes(cArr);
        dbopen(this.mPath, this.mFlags);
        int i = 0;
        try {
            try {
                keyDatabase(sQLiteDatabaseHook, new Runnable() { // from class: net.sqlcipher.database.SQLiteDatabase.2
                    @Override // java.lang.Runnable
                    public void run() throws SQLException {
                        byte[] bArr = bytes;
                        if (bArr == null || bArr.length <= 0) {
                            return;
                        }
                        SQLiteDatabase.this.key(bArr);
                    }
                });
                if (bytes == null || bytes.length <= 0) {
                    return;
                }
                int length = bytes.length;
                while (i < length) {
                    byte b = bytes[i];
                    i++;
                }
            } catch (RuntimeException e) {
                if (containsNull(cArr)) {
                    keyDatabase(sQLiteDatabaseHook, new Runnable() { // from class: net.sqlcipher.database.SQLiteDatabase.3
                        @Override // java.lang.Runnable
                        public void run() throws SQLException {
                            char[] cArr2 = cArr;
                            if (cArr2 != null) {
                                SQLiteDatabase.this.key_mutf8(cArr2);
                            }
                        }
                    });
                    if (bytes != null && bytes.length > 0) {
                        rekey(bytes);
                    }
                    if (bytes == null || bytes.length <= 0) {
                        return;
                    }
                    int length2 = bytes.length;
                    while (i < length2) {
                        byte b2 = bytes[i];
                        i++;
                    }
                    return;
                }
                throw e;
            }
        } catch (Throwable th) {
            dbclose();
            if (SQLiteDebug.DEBUG_SQL_CACHE) {
                this.mTimeClosed = getTime();
            }
            if (bytes != null && bytes.length > 0) {
                int length3 = bytes.length;
                while (i < length3) {
                    byte b3 = bytes[i];
                    i++;
                }
            }
            throw th;
        }
    }

    private boolean containsNull(char[] cArr) {
        if (cArr == null || cArr.length <= 0) {
            return false;
        }
        for (char c : cArr) {
            if (c == 0) {
                return true;
            }
        }
        return false;
    }

    private void keyDatabase(SQLiteDatabaseHook sQLiteDatabaseHook, Runnable runnable) {
        if (sQLiteDatabaseHook != null) {
            sQLiteDatabaseHook.preKey(this);
        }
        if (runnable != null) {
            runnable.run();
        }
        if (sQLiteDatabaseHook != null) {
            sQLiteDatabaseHook.postKey(this);
        }
        if (SQLiteDebug.DEBUG_SQL_CACHE) {
            this.mTimeOpened = getTime();
        }
        try {
            Cursor cursorRawQuery = rawQuery("select count(*) from sqlite_master;", new String[0]);
            if (cursorRawQuery != null) {
                cursorRawQuery.moveToFirst();
                cursorRawQuery.getInt(0);
                cursorRawQuery.close();
            }
        } catch (RuntimeException e) {
            Log.e(TAG, e.getMessage(), e);
            throw e;
        }
    }

    private String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ", Locale.US).format(Long.valueOf(System.currentTimeMillis()));
    }

    public boolean isReadOnly() {
        return (this.mFlags & 1) == 1;
    }

    public boolean isOpen() {
        return this.mNativeHandle != 0;
    }

    public boolean needUpgrade(int i) {
        return i > getVersion();
    }

    public final String getPath() {
        return this.mPath;
    }

    private String getPathForLogs() {
        String str = this.mPathForLogs;
        if (str != null) {
            return str;
        }
        String str2 = this.mPath;
        if (str2 == null) {
            return null;
        }
        if (str2.indexOf(64) == -1) {
            this.mPathForLogs = this.mPath;
        } else {
            this.mPathForLogs = EMAIL_IN_DB_PATTERN.matcher(this.mPath).replaceAll("XX@YY");
        }
        return this.mPathForLogs;
    }

    public void setLocale(Locale locale) {
        lock();
        try {
            native_setLocale(locale.toString(), this.mFlags);
        } finally {
            unlock();
        }
    }

    void addToCompiledQueries(String str, SQLiteCompiledSql sQLiteCompiledSql) {
        if (this.mMaxSqlCacheSize == 0) {
            if (SQLiteDebug.DEBUG_SQL_CACHE) {
                Log.v(TAG, "|NOT adding_sql_to_cache|" + getPath() + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + str);
                return;
            }
            return;
        }
        synchronized (this.mCompiledQueries) {
            if (this.mCompiledQueries.get(str) != null) {
                return;
            }
            if (this.mCompiledQueries.size() == this.mMaxSqlCacheSize) {
                int i = this.mCacheFullWarnings + 1;
                this.mCacheFullWarnings = i;
                if (i == 1) {
                    Log.w(TAG, "Reached MAX size for compiled-sql statement cache for database " + getPath() + "; i.e., NO space for this sql statement in cache: " + str + ". Please change your sql statements to use '?' for bindargs, instead of using actual values");
                }
            } else {
                this.mCompiledQueries.put(str, sQLiteCompiledSql);
                if (SQLiteDebug.DEBUG_SQL_CACHE) {
                    Log.v(TAG, "|adding_sql_to_cache|" + getPath() + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + this.mCompiledQueries.size() + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + str);
                }
            }
        }
    }

    private void deallocCachedSqlStatements() {
        synchronized (this.mCompiledQueries) {
            Iterator<SQLiteCompiledSql> it2 = this.mCompiledQueries.values().iterator();
            while (it2.hasNext()) {
                it2.next().releaseSqlStatement();
            }
            this.mCompiledQueries.clear();
        }
    }

    SQLiteCompiledSql getCompiledStatementForSql(String str) {
        synchronized (this.mCompiledQueries) {
            if (this.mMaxSqlCacheSize == 0) {
                if (SQLiteDebug.DEBUG_SQL_CACHE) {
                    Log.v(TAG, "|cache NOT found|" + getPath());
                }
                return null;
            }
            SQLiteCompiledSql sQLiteCompiledSql = this.mCompiledQueries.get(str);
            boolean z = sQLiteCompiledSql != null;
            if (z) {
                this.mNumCacheHits++;
            } else {
                this.mNumCacheMisses++;
            }
            if (SQLiteDebug.DEBUG_SQL_CACHE) {
                Log.v(TAG, "|cache_stats|" + getPath() + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + this.mCompiledQueries.size() + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + this.mNumCacheHits + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + this.mNumCacheMisses + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + z + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + this.mTimeOpened + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + this.mTimeClosed + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + str);
            }
            return sQLiteCompiledSql;
        }
    }

    public boolean isInCompiledSqlCache(String str) {
        boolean zContainsKey;
        synchronized (this.mCompiledQueries) {
            zContainsKey = this.mCompiledQueries.containsKey(str);
        }
        return zContainsKey;
    }

    public void purgeFromCompiledSqlCache(String str) {
        synchronized (this.mCompiledQueries) {
            this.mCompiledQueries.remove(str);
        }
    }

    public void resetCompiledSqlCache() {
        synchronized (this.mCompiledQueries) {
            this.mCompiledQueries.clear();
        }
    }

    public synchronized int getMaxSqlCacheSize() {
        return this.mMaxSqlCacheSize;
    }

    public synchronized void setMaxSqlCacheSize(int i) {
        if (i > 250 || i < 0) {
            throw new IllegalStateException("expected value between 0 and 250");
        }
        if (i < this.mMaxSqlCacheSize) {
            throw new IllegalStateException("cannot set cacheSize to a value less than the value set with previous setMaxSqlCacheSize() call.");
        }
        this.mMaxSqlCacheSize = i;
    }

    static ArrayList<SQLiteDebug.DbStats> getDbStats() throws Throwable {
        String str;
        ArrayList<SQLiteDebug.DbStats> arrayList = new ArrayList<>();
        Iterator<SQLiteDatabase> it2 = getActiveDatabases().iterator();
        while (it2.hasNext()) {
            SQLiteDatabase next = it2.next();
            if (next != null && next.isOpen()) {
                int iNative_getDbLookaside = next.native_getDbLookaside();
                String path = next.getPath();
                int iLastIndexOf = path.lastIndexOf("/");
                String strSubstring = path.substring(iLastIndexOf != -1 ? iLastIndexOf + 1 : 0);
                ArrayList<Pair<String, String>> attachedDbs = getAttachedDbs(next);
                if (attachedDbs != null) {
                    for (int i = 0; i < attachedDbs.size(); i++) {
                        Pair<String, String> pair = attachedDbs.get(i);
                        long pragmaVal = getPragmaVal(next, ((String) pair.first) + ".page_count;");
                        if (i == 0) {
                            str = strSubstring;
                        } else {
                            String string = "  (attached) " + ((String) pair.first);
                            if (((String) pair.second).trim().length() > 0) {
                                int iLastIndexOf2 = ((String) pair.second).lastIndexOf("/");
                                StringBuilder sb = new StringBuilder();
                                sb.append(string);
                                sb.append(" : ");
                                sb.append(((String) pair.second).substring(iLastIndexOf2 != -1 ? iLastIndexOf2 + 1 : 0));
                                string = sb.toString();
                            }
                            str = string;
                            iNative_getDbLookaside = 0;
                        }
                        if (pragmaVal > 0) {
                            arrayList.add(new SQLiteDebug.DbStats(str, pragmaVal, next.getPageSize(), iNative_getDbLookaside));
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    private static ArrayList<SQLiteDatabase> getActiveDatabases() {
        ArrayList<SQLiteDatabase> arrayList = new ArrayList<>();
        synchronized (sActiveDatabases) {
            arrayList.addAll(sActiveDatabases.keySet());
        }
        return arrayList;
    }

    private static long getPragmaVal(SQLiteDatabase sQLiteDatabase, String str) throws Throwable {
        if (!sQLiteDatabase.isOpen()) {
            return 0L;
        }
        SQLiteStatement sQLiteStatement = null;
        try {
            SQLiteStatement sQLiteStatement2 = new SQLiteStatement(sQLiteDatabase, "PRAGMA " + str);
            try {
                long jSimpleQueryForLong = sQLiteStatement2.simpleQueryForLong();
                sQLiteStatement2.close();
                return jSimpleQueryForLong;
            } catch (Throwable th) {
                th = th;
                sQLiteStatement = sQLiteStatement2;
                if (sQLiteStatement != null) {
                    sQLiteStatement.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static ArrayList<Pair<String, String>> getAttachedDbs(SQLiteDatabase sQLiteDatabase) {
        if (!sQLiteDatabase.isOpen()) {
            return null;
        }
        ArrayList<Pair<String, String>> arrayList = new ArrayList<>();
        Cursor cursorRawQuery = sQLiteDatabase.rawQuery("pragma database_list;", (String[]) null);
        while (cursorRawQuery.moveToNext()) {
            arrayList.add(new Pair<>(cursorRawQuery.getString(1), cursorRawQuery.getString(2)));
        }
        cursorRawQuery.close();
        return arrayList;
    }

    private byte[] getBytes(char[] cArr) {
        if (cArr == null || cArr.length == 0) {
            return null;
        }
        ByteBuffer byteBufferEncode = Charset.forName("UTF-8").encode(CharBuffer.wrap(cArr));
        byte[] bArr = new byte[byteBufferEncode.limit()];
        byteBufferEncode.get(bArr);
        return bArr;
    }
}
