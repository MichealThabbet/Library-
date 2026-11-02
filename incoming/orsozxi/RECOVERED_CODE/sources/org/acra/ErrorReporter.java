package org.acra;

import org.acra.scheduler.SenderScheduler;

/* loaded from: classes3.dex */
public interface ErrorReporter {
    void clearCustomData();

    String getCustomData(String str);

    SenderScheduler getReportScheduler();

    void handleException(Throwable th);

    void handleException(Throwable th, boolean z);

    void handleSilentException(Throwable th);

    String putCustomData(String str, String str2);

    String removeCustomData(String str);

    void setEnabled(boolean z);
}
