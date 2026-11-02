package com.huawei.hms.analytics.framework.f;

import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.framework.b.b;
import com.huawei.hms.analytics.framework.config.ICollectorConfig;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public final class a {
    private static a a = new a(5);
    private static a b = new a(1);
    private ThreadPoolExecutor c;

    /* renamed from: com.huawei.hms.analytics.framework.f.a$a, reason: collision with other inner class name */
    static class RunnableC0052a implements Runnable {
        private Runnable a;

        public RunnableC0052a(Runnable runnable) {
            this.a = runnable;
        }

        @Override // java.lang.Runnable
        public final void run() {
            Runnable runnable = this.a;
            if (runnable != null) {
                try {
                    runnable.run();
                } catch (Throwable th) {
                    ICollectorConfig iCollectorConfigA = b.a().a("_openness_config_tag");
                    if (iCollectorConfigA != null) {
                        iCollectorConfigA.handlerThrowable(th);
                    }
                    HiLog.e("MissionThread", "other error :" + th.getMessage());
                }
            }
        }
    }

    private a(int i) {
        this.c = new ThreadPoolExecutor(0, i, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(5000));
    }

    public static a a() {
        return a;
    }

    public static a b() {
        return b;
    }

    public final void a(Runnable runnable) {
        try {
            this.c.execute(new RunnableC0052a(runnable));
        } catch (RejectedExecutionException unused) {
            HiLog.w("MissionThread", "addToQueue() Exception has happened! From rejected execution");
        }
    }
}
