package org.acra.sender;

import android.content.Context;
import org.acra.data.CrashReportData;

/* loaded from: classes3.dex */
public interface ReportSender {

    /* renamed from: org.acra.sender.ReportSender$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static boolean $default$requiresForeground(ReportSender reportSender) {
            return false;
        }
    }

    boolean requiresForeground();

    void send(Context context, CrashReportData crashReportData) throws ReportSenderException;
}
