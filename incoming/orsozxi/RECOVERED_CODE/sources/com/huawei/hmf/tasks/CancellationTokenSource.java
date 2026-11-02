package com.huawei.hmf.tasks;

import com.huawei.hmf.tasks.a.c;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class CancellationTokenSource {
    private c impl = new c();

    public void cancel() {
        c cVar = this.impl;
        if (cVar.c) {
            return;
        }
        synchronized (cVar.b) {
            cVar.c = true;
            Iterator<Runnable> it2 = cVar.a.iterator();
            while (it2.hasNext()) {
                it2.next().run();
            }
        }
    }

    public CancellationToken getToken() {
        return this.impl;
    }
}
