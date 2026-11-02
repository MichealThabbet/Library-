package com.google.firebase.messaging;

import android.content.Context;
import android.content.SharedPreferences;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-messaging@@21.1.0 */
/* loaded from: classes2.dex */
final class TopicsStore {
    private static WeakReference<TopicsStore> topicsStoreWeakReference;
    private final SharedPreferences sharedPreferences;
    private final Executor syncExecutor;
    private SharedPreferencesQueue topicOperationsQueue;

    private TopicsStore(SharedPreferences sharedPreferences, Executor executor) {
        this.syncExecutor = executor;
        this.sharedPreferences = sharedPreferences;
    }

    public static synchronized TopicsStore getInstance(Context context, Executor executor) {
        TopicsStore topicsStore = topicsStoreWeakReference != null ? topicsStoreWeakReference.get() : null;
        if (topicsStore != null) {
            return topicsStore;
        }
        TopicsStore topicsStore2 = new TopicsStore(context.getSharedPreferences("com.google.android.gms.appid", 0), executor);
        topicsStore2.initStore();
        topicsStoreWeakReference = new WeakReference<>(topicsStore2);
        return topicsStore2;
    }

    private synchronized void initStore() {
        this.topicOperationsQueue = SharedPreferencesQueue.createInstance(this.sharedPreferences, "topic_operation_queue", ConstantsBase.PROPERTIES_PARAMS_SEPARATOR, this.syncExecutor);
    }

    synchronized boolean addTopicOperation(TopicOperation topicOperation) {
        return this.topicOperationsQueue.add(topicOperation.serialize());
    }

    synchronized TopicOperation getNextTopicOperation() {
        return TopicOperation.from(this.topicOperationsQueue.peek());
    }

    synchronized boolean removeTopicOperation(TopicOperation topicOperation) {
        return this.topicOperationsQueue.remove(topicOperation.serialize());
    }
}
