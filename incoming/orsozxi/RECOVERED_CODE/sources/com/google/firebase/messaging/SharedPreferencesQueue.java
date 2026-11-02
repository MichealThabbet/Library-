package com.google.firebase.messaging;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-messaging@@21.1.0 */
/* loaded from: classes2.dex */
final class SharedPreferencesQueue {
    private final SharedPreferences sharedPreferences;
    private final Executor syncExecutor;
    private final ArrayDeque<String> internalQueue = new ArrayDeque<>();
    private boolean bulkOperation = false;
    private final String queueName = "topic_operation_queue";
    private final String itemSeparator = ConstantsBase.PROPERTIES_PARAMS_SEPARATOR;

    private SharedPreferencesQueue(SharedPreferences sharedPreferences, String str, String str2, Executor executor) {
        this.sharedPreferences = sharedPreferences;
        this.syncExecutor = executor;
    }

    private boolean checkAndSyncState(boolean z) {
        if (!z || this.bulkOperation) {
            return z;
        }
        syncStateAsync();
        return true;
    }

    static SharedPreferencesQueue createInstance(SharedPreferences sharedPreferences, String str, String str2, Executor executor) {
        SharedPreferencesQueue sharedPreferencesQueue = new SharedPreferencesQueue(sharedPreferences, "topic_operation_queue", ConstantsBase.PROPERTIES_PARAMS_SEPARATOR, executor);
        sharedPreferencesQueue.initQueue();
        return sharedPreferencesQueue;
    }

    private void initQueue() {
        synchronized (this.internalQueue) {
            this.internalQueue.clear();
            String string = this.sharedPreferences.getString(this.queueName, "");
            if (!TextUtils.isEmpty(string) && string.contains(this.itemSeparator)) {
                String[] strArrSplit = string.split(this.itemSeparator, -1);
                if (strArrSplit.length == 0) {
                    Log.e(Constants.TAG, "Corrupted queue. Please check the queue contents and item separator provided");
                }
                for (String str : strArrSplit) {
                    if (!TextUtils.isEmpty(str)) {
                        this.internalQueue.add(str);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: syncState, reason: merged with bridge method [inline-methods] */
    public void bridge$lambda$0$SharedPreferencesQueue() {
        synchronized (this.internalQueue) {
            this.sharedPreferences.edit().putString(this.queueName, serialize()).commit();
        }
    }

    private void syncStateAsync() {
        this.syncExecutor.execute(new Runnable(this) { // from class: com.google.firebase.messaging.SharedPreferencesQueue$$Lambda$0
            private final SharedPreferencesQueue arg$1;

            {
                this.arg$1 = this;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.arg$1.bridge$lambda$0$SharedPreferencesQueue();
            }
        });
    }

    public boolean add(String str) {
        boolean zAdd;
        if (TextUtils.isEmpty(str) || str.contains(this.itemSeparator)) {
            return false;
        }
        synchronized (this.internalQueue) {
            zAdd = this.internalQueue.add(str);
            checkAndSyncState(zAdd);
        }
        return zAdd;
    }

    public String peek() {
        String strPeek;
        synchronized (this.internalQueue) {
            strPeek = this.internalQueue.peek();
        }
        return strPeek;
    }

    public boolean remove(Object obj) {
        boolean zRemove;
        synchronized (this.internalQueue) {
            zRemove = this.internalQueue.remove(obj);
            checkAndSyncState(zRemove);
        }
        return zRemove;
    }

    public String serialize() {
        StringBuilder sb = new StringBuilder();
        Iterator<String> it2 = this.internalQueue.iterator();
        while (it2.hasNext()) {
            sb.append(it2.next());
            sb.append(this.itemSeparator);
        }
        return sb.toString();
    }
}
