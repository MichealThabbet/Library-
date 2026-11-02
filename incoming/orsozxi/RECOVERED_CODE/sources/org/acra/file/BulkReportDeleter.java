package org.acra.file;

import android.content.Context;
import java.io.File;
import java.util.Arrays;
import org.acra.ACRA;

/* loaded from: classes3.dex */
public final class BulkReportDeleter {
    private final ReportLocator reportLocator;

    public BulkReportDeleter(Context context) {
        this.reportLocator = new ReportLocator(context);
    }

    public void deleteReports(boolean z, int i) {
        File[] approvedReports = z ? this.reportLocator.getApprovedReports() : this.reportLocator.getUnapprovedReports();
        Arrays.sort(approvedReports, new LastModifiedComparator());
        for (int i2 = 0; i2 < approvedReports.length - i; i2++) {
            if (!approvedReports[i2].delete()) {
                ACRA.log.w(ACRA.LOG_TAG, "Could not delete report : " + approvedReports[i2]);
            }
        }
    }
}
