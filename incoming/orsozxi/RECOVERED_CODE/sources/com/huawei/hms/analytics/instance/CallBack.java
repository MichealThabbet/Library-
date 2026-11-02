package com.huawei.hms.analytics.instance;

/* loaded from: classes2.dex */
public interface CallBack {
    public static final int ANALYTICS_CLOSED = -110;
    public static final int BODY_ERROR = -112;
    public static final int INIT_FAILED = -101;
    public static final int REGION_ERROR = -111;

    void onResult(int i, String str);
}
