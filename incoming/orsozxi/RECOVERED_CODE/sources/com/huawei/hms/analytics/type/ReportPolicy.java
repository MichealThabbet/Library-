package com.huawei.hms.analytics.type;

import com.huawei.hms.analytics.core.log.HiLog;

/* loaded from: classes2.dex */
public enum ReportPolicy {
    ON_SCHEDULED_TIME_POLICY(60),
    ON_APP_LAUNCH_POLICY(-1),
    ON_MOVE_BACKGROUND_POLICY(-1),
    ON_CACHE_THRESHOLD_POLICY(30);

    private long threshold;

    /* renamed from: com.huawei.hms.analytics.type.ReportPolicy$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] lmn;

        static {
            int[] iArr = new int[ReportPolicy.values().length];
            lmn = iArr;
            try {
                iArr[ReportPolicy.ON_CACHE_THRESHOLD_POLICY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                lmn[ReportPolicy.ON_SCHEDULED_TIME_POLICY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    ReportPolicy(long j) {
        this.threshold = j;
    }

    private long checkThreshold(long j) {
        long j2;
        long j3;
        int i = AnonymousClass1.lmn[ordinal()];
        if (i == 1) {
            j2 = 30;
            j3 = 1000;
            if (j > 1000) {
                HiLog.i("PolicyToolsKit", "The number of automatically reported thresholds is too large. Use the maximum  default value.");
                return j3;
            }
            if (j >= 30) {
                return j;
            }
            HiLog.i("PolicyToolsKit", "The number of automatically reported thresholds is too small. Use the minimum  default value.");
            return j2;
        }
        if (i != 2) {
            return -1L;
        }
        j2 = 60;
        j3 = 1800;
        if (j > 1800) {
            HiLog.w("PolicyToolsKit", "The auto-reporting period is too long.Use the default value.");
            return j3;
        }
        if (j >= 60) {
            return j;
        }
        HiLog.w("PolicyToolsKit", "The auto-reporting period is too short. Use the default value.");
        return j2;
    }

    public final long getThreshold() {
        return this.threshold;
    }

    public final void setThreshold(long j) {
        this.threshold = checkThreshold(j);
    }
}
