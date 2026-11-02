package org.acra.sender;

import android.content.Context;
import org.acra.ACRA;
import org.acra.data.CrashReportData;
import org.acra.sender.ReportSender;

/* loaded from: classes3.dex */
final class NullSender implements ReportSender {
    @Override // org.acra.sender.ReportSender
    public /* synthetic */ boolean requiresForeground() {
        return ReportSender.CC.$default$requiresForeground(this);
    }

    NullSender() {
    }

    @Override // org.acra.sender.ReportSender
    public void send(Context context, CrashReportData crashReportData) {
        ACRA.log.w(ACRA.LOG_TAG, context.getPackageName() + " reports will NOT be sent - no valid ReportSender was found!");
    }
}
