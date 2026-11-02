package net.sqlcipher.database;

/* loaded from: classes3.dex */
public interface SQLiteTransactionListener {
    void onBegin();

    void onCommit();

    void onRollback();
}
