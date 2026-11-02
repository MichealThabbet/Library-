package com.huawei.hms.analytics.framework;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class SyncManager {
    private static final SyncManager INSTANCE = new SyncManager();
    private final Map<String, CountDownLatch> countDownLatchMap = new ConcurrentHashMap();

    public interface TaskNames {
        public static final String AAID_TASK = "aaid_task";
        public static final String BINDSERVICE_TASK = "bindService_task";
        public static final String INIT_TASK_CONNECTOR = "init_task_connector";
        public static final String INIT_TASK_OPENNESS = "init_task_openness";
        public static final String OAID_TASK = "oaid_task";
        public static final String REGION_CHANGE = "region_change";
    }

    private CountDownLatch getCountDownLatch(String str) {
        return this.countDownLatchMap.get(str);
    }

    public static SyncManager getInstance() {
        return INSTANCE;
    }

    private void removeDownLatch(String str) {
        this.countDownLatchMap.remove(str);
    }

    private void setCountDownLatch(String str, CountDownLatch countDownLatch) {
        this.countDownLatchMap.put(str, countDownLatch);
    }

    public void await(String str) throws InterruptedException {
        CountDownLatch countDownLatch = getCountDownLatch(str);
        if (countDownLatch != null) {
            countDownLatch.await();
        }
    }

    public void await(String str, long j) throws InterruptedException {
        CountDownLatch countDownLatch = getCountDownLatch(str);
        if (countDownLatch != null) {
            countDownLatch.await(j, TimeUnit.MILLISECONDS);
        }
    }

    public void countDown(String str) {
        CountDownLatch countDownLatch = getCountDownLatch(str);
        if (countDownLatch != null) {
            countDownLatch.countDown();
            removeDownLatch(str);
        }
    }

    public void start(String str) {
        if (this.countDownLatchMap.containsKey(str)) {
            return;
        }
        setCountDownLatch(str, new CountDownLatch(1));
    }
}
