package com.huawei.hms.analytics;

import com.huawei.hms.analytics.core.log.HiLog;

/* loaded from: classes2.dex */
public class HiAnalyticsTools {
    public static void enableLog() {
        enableLog(3);
    }

    public static void enableLog(int i) {
        HiLog.init(i, "HiAnalyticsSDK");
    }
}
