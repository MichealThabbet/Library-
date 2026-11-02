package com.huawei.agconnect.https;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public interface e {
    public static final e a = new e() { // from class: com.huawei.agconnect.https.e.1
        Executor b = new ThreadPoolExecutor(0, 8, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), new ThreadFactory() { // from class: com.huawei.agconnect.https.e.1.1
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "AGC-Https-Thread");
            }
        });

        @Override // com.huawei.agconnect.https.e
        public Executor a() {
            return this.b;
        }
    };

    Executor a();
}
