package org.apache.http.impl.bootstrap;

import com.ibm.icu.impl.locale.LanguageTag;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes3.dex */
class ThreadFactoryImpl implements ThreadFactory {
    private final AtomicLong count;
    private final ThreadGroup group;
    private final String namePrefix;

    ThreadFactoryImpl(String str, ThreadGroup threadGroup) {
        this.namePrefix = str;
        this.group = threadGroup;
        this.count = new AtomicLong();
    }

    ThreadFactoryImpl(String str) {
        this(str, null);
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        return new Thread(this.group, runnable, this.namePrefix + LanguageTag.SEP + this.count.incrementAndGet());
    }
}
