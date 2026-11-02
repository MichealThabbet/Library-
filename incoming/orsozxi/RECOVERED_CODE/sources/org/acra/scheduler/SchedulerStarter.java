package org.acra.scheduler;

import android.content.Context;
import java.io.File;
import java.util.List;
import org.acra.ACRA;
import org.acra.config.CoreConfiguration;
import org.acra.file.ReportLocator;

/* loaded from: classes3.dex */
public class SchedulerStarter {
    private final ReportLocator locator;
    private final SenderScheduler senderScheduler;

    public SchedulerStarter(Context context, CoreConfiguration coreConfiguration) {
        this.locator = new ReportLocator(context);
        List listLoadEnabled = coreConfiguration.pluginLoader().loadEnabled(coreConfiguration, SenderSchedulerFactory.class);
        if (listLoadEnabled.isEmpty()) {
            this.senderScheduler = new DefaultSenderScheduler(context, coreConfiguration);
            return;
        }
        this.senderScheduler = ((SenderSchedulerFactory) listLoadEnabled.get(0)).create(context, coreConfiguration);
        if (listLoadEnabled.size() > 1) {
            ACRA.log.w(ACRA.LOG_TAG, "More than one SenderScheduler found. Will use only " + this.senderScheduler.getClass().getSimpleName());
        }
    }

    public void scheduleReports(File file, boolean z) {
        if (file != null) {
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Mark " + file.getName() + " as approved.");
            }
            File file2 = new File(this.locator.getApprovedFolder(), file.getName());
            if (!file.renameTo(file2)) {
                ACRA.log.w(ACRA.LOG_TAG, "Could not rename approved report from " + file + " to " + file2);
            }
        }
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "Schedule report sending");
        }
        this.senderScheduler.scheduleReportSending(z);
    }

    public SenderScheduler getSenderScheduler() {
        return this.senderScheduler;
    }
}
