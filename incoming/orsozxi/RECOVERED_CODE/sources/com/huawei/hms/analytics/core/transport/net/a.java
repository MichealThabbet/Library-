package com.huawei.hms.analytics.core.transport.net;

import com.huawei.hms.analytics.core.log.HiLog;
import java.io.Closeable;
import java.io.IOException;

/* loaded from: classes2.dex */
public final class a {
    public static void a(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
                HiLog.w("StreamToolsKit", "closeQuietly(): Exception when closing the closeable!");
            }
        }
    }
}
