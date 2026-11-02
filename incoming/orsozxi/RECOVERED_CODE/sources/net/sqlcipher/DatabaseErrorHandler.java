package net.sqlcipher;

import net.sqlcipher.database.SQLiteDatabase;

/* loaded from: classes3.dex */
public interface DatabaseErrorHandler {
    void onCorruption(SQLiteDatabase sQLiteDatabase);
}
