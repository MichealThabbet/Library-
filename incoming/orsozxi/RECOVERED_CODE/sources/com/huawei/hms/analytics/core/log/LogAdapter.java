package com.huawei.hms.analytics.core.log;

/* loaded from: classes2.dex */
public interface LogAdapter {
    void init(int i, String str);

    boolean isLoggable(int i);

    void println(int i, String str, String str2);

    void println(int i, String str, String str2, String str3);
}
