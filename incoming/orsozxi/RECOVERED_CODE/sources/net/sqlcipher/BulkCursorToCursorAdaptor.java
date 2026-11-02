package net.sqlcipher;

import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import com.app.piapostolos.Alarm.Database.Database;
import net.sqlcipher.AbstractCursor;

/* loaded from: classes3.dex */
public final class BulkCursorToCursorAdaptor extends AbstractWindowedCursor {
    private static final String TAG = "BulkCursor";
    private IBulkCursor mBulkCursor;
    private String[] mColumns;
    private int mCount;
    private AbstractCursor.SelfContentObserver mObserverBridge;
    private boolean mWantsAllOnMoveCalls;

    @Override // net.sqlcipher.AbstractWindowedCursor, net.sqlcipher.AbstractCursor, android.database.Cursor
    public void copyStringToBuffer(int i, CharArrayBuffer charArrayBuffer) {
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public void registerContentObserver(ContentObserver contentObserver) {
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public void unregisterContentObserver(ContentObserver contentObserver) {
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
    }

    public void set(IBulkCursor iBulkCursor) {
        this.mBulkCursor = iBulkCursor;
        try {
            this.mCount = iBulkCursor.count();
            this.mWantsAllOnMoveCalls = this.mBulkCursor.getWantsAllOnMoveCalls();
            String[] columnNames = this.mBulkCursor.getColumnNames();
            this.mColumns = columnNames;
            this.mRowIdColumnIndex = findRowIdColumnIndex(columnNames);
        } catch (RemoteException unused) {
            Log.e(TAG, "Setup failed because the remote process is dead");
        }
    }

    public void set(IBulkCursor iBulkCursor, int i, int i2) {
        this.mBulkCursor = iBulkCursor;
        this.mColumns = null;
        this.mCount = i;
        this.mRowIdColumnIndex = i2;
    }

    public static int findRowIdColumnIndex(String[] strArr) {
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            if (strArr[i].equals(Database.COLUMN_ALARM_ID)) {
                return i;
            }
        }
        return -1;
    }

    public synchronized IContentObserver getObserver() {
        if (this.mObserverBridge == null) {
            this.mObserverBridge = new AbstractCursor.SelfContentObserver(this);
        }
        return null;
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public int getCount() {
        return this.mCount;
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.CrossProcessCursor
    public boolean onMove(int i, int i2) {
        try {
            if (this.mWindow == null || i2 < this.mWindow.getStartPosition() || i2 >= this.mWindow.getStartPosition() + this.mWindow.getNumRows()) {
                this.mWindow = this.mBulkCursor.getWindow(i2);
            } else if (this.mWantsAllOnMoveCalls) {
                this.mBulkCursor.onMove(i2);
            }
            return this.mWindow != null;
        } catch (RemoteException unused) {
            Log.e(TAG, "Unable to get window because the remote process is dead");
            return false;
        }
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public void deactivate() {
        super.deactivate();
        try {
            this.mBulkCursor.deactivate();
        } catch (RemoteException unused) {
            Log.w(TAG, "Remote process exception when deactivating");
        }
        this.mWindow = null;
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        super.close();
        try {
            this.mBulkCursor.close();
        } catch (RemoteException unused) {
            Log.w(TAG, "Remote process exception when closing");
        }
        this.mWindow = null;
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public boolean requery() {
        try {
            int iRequery = this.mBulkCursor.requery(getObserver(), new CursorWindow(false));
            this.mCount = iRequery;
            if (iRequery != -1) {
                this.mPos = -1;
                this.mWindow = null;
                super.requery();
                return true;
            }
            deactivate();
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Unable to requery because the remote process exception " + e.getMessage());
            deactivate();
            return false;
        }
    }

    @Override // net.sqlcipher.AbstractCursor
    public boolean deleteRow() {
        try {
            boolean zDeleteRow = this.mBulkCursor.deleteRow(this.mPos);
            if (zDeleteRow) {
                this.mWindow = null;
                this.mCount = this.mBulkCursor.count();
                if (this.mPos < this.mCount) {
                    int i = this.mPos;
                    this.mPos = -1;
                    moveToPosition(i);
                } else {
                    this.mPos = this.mCount;
                }
                onChange(true);
            }
            return zDeleteRow;
        } catch (RemoteException unused) {
            Log.e(TAG, "Unable to delete row because the remote process is dead");
            return false;
        }
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public String[] getColumnNames() {
        if (this.mColumns == null) {
            try {
                this.mColumns = this.mBulkCursor.getColumnNames();
            } catch (RemoteException unused) {
                Log.e(TAG, "Unable to fetch column names because the remote process is dead");
                return null;
            }
        }
        return this.mColumns;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0021 A[Catch: all -> 0x0041, DONT_GENERATE, TRY_LEAVE, TryCatch #0 {, blocks: (B:9:0x0014, B:10:0x0019, B:12:0x0021, B:14:0x0023, B:16:0x002e, B:17:0x0036, B:19:0x0038, B:20:0x003f), top: B:25:0x0014, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0023 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // net.sqlcipher.AbstractCursor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean commitUpdates(java.util.Map<? extends java.lang.Long, ? extends java.util.Map<java.lang.String, java.lang.Object>> r5) {
        /*
            r4 = this;
            boolean r0 = r4.supportsUpdates()
            r1 = 0
            if (r0 != 0) goto Lf
            java.lang.String r5 = "BulkCursor"
            java.lang.String r0 = "commitUpdates not supported on this cursor, did you include the _id column?"
            android.util.Log.e(r5, r0)
            return r1
        Lf:
            java.util.HashMap<java.lang.Long, java.util.Map<java.lang.String, java.lang.Object>> r0 = r4.mUpdatedRows
            monitor-enter(r0)
            if (r5 == 0) goto L19
            java.util.HashMap<java.lang.Long, java.util.Map<java.lang.String, java.lang.Object>> r2 = r4.mUpdatedRows     // Catch: java.lang.Throwable -> L41
            r2.putAll(r5)     // Catch: java.lang.Throwable -> L41
        L19:
            java.util.HashMap<java.lang.Long, java.util.Map<java.lang.String, java.lang.Object>> r5 = r4.mUpdatedRows     // Catch: java.lang.Throwable -> L41
            int r5 = r5.size()     // Catch: java.lang.Throwable -> L41
            if (r5 > 0) goto L23
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L41
            return r1
        L23:
            net.sqlcipher.IBulkCursor r5 = r4.mBulkCursor     // Catch: android.os.RemoteException -> L38 java.lang.Throwable -> L41
            java.util.HashMap<java.lang.Long, java.util.Map<java.lang.String, java.lang.Object>> r2 = r4.mUpdatedRows     // Catch: android.os.RemoteException -> L38 java.lang.Throwable -> L41
            boolean r5 = r5.updateRows(r2)     // Catch: android.os.RemoteException -> L38 java.lang.Throwable -> L41
            r2 = 1
            if (r5 != r2) goto L36
            java.util.HashMap<java.lang.Long, java.util.Map<java.lang.String, java.lang.Object>> r3 = r4.mUpdatedRows     // Catch: android.os.RemoteException -> L38 java.lang.Throwable -> L41
            r3.clear()     // Catch: android.os.RemoteException -> L38 java.lang.Throwable -> L41
            r4.onChange(r2)     // Catch: android.os.RemoteException -> L38 java.lang.Throwable -> L41
        L36:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L41
            return r5
        L38:
            java.lang.String r5 = "BulkCursor"
            java.lang.String r2 = "Unable to commit updates because the remote process is dead"
            android.util.Log.e(r5, r2)     // Catch: java.lang.Throwable -> L41
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L41
            return r1
        L41:
            r5 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L41
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: net.sqlcipher.BulkCursorToCursorAdaptor.commitUpdates(java.util.Map):boolean");
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public Bundle getExtras() {
        try {
            return this.mBulkCursor.getExtras();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public Bundle respond(Bundle bundle) {
        try {
            return this.mBulkCursor.respond(bundle);
        } catch (RemoteException e) {
            Log.w(TAG, "respond() threw RemoteException, returning an empty bundle.", e);
            return Bundle.EMPTY;
        }
    }
}
