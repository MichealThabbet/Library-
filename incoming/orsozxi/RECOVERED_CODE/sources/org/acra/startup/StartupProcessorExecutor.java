package org.acra.startup;

import android.content.Context;
import android.os.Handler;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import org.acra.ACRA;
import org.acra.config.CoreConfiguration;
import org.acra.file.CrashReportFileNameParser;
import org.acra.file.ReportLocator;
import org.acra.interaction.ReportInteractionExecutor;
import org.acra.scheduler.SchedulerStarter;

/* loaded from: classes3.dex */
public class StartupProcessorExecutor {
    private final CoreConfiguration config;
    private final Context context;
    private final CrashReportFileNameParser fileNameParser = new CrashReportFileNameParser();
    private final ReportLocator reportLocator;
    private final SchedulerStarter schedulerStarter;

    public StartupProcessorExecutor(Context context, CoreConfiguration coreConfiguration, SchedulerStarter schedulerStarter) {
        this.context = context;
        this.config = coreConfiguration;
        this.reportLocator = new ReportLocator(context);
        this.schedulerStarter = schedulerStarter;
    }

    public void processReports(final boolean z) {
        final Calendar calendar = Calendar.getInstance();
        new Handler(this.context.getMainLooper()).post(new Runnable() { // from class: org.acra.startup.-$$Lambda$StartupProcessorExecutor$iVr_z6E9R3llAzazPhGmR3UkX1k
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processReports$1$StartupProcessorExecutor(calendar, z);
            }
        });
    }

    public /* synthetic */ void lambda$processReports$1$StartupProcessorExecutor(final Calendar calendar, final boolean z) {
        new Thread(new Runnable() { // from class: org.acra.startup.-$$Lambda$StartupProcessorExecutor$G9i7BXvdx8yfFTQ9dkfd2bK9Cf8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$null$0$StartupProcessorExecutor(calendar, z);
            }
        }).start();
    }

    public /* synthetic */ void lambda$null$0$StartupProcessorExecutor(Calendar calendar, boolean z) {
        ArrayList<Report> arrayList = new ArrayList();
        for (File file : this.reportLocator.getUnapprovedReports()) {
            arrayList.add(new Report(file, false));
        }
        for (File file2 : this.reportLocator.getApprovedReports()) {
            arrayList.add(new Report(file2, true));
        }
        Iterator it2 = this.config.pluginLoader().loadEnabled(this.config, StartupProcessor.class).iterator();
        while (it2.hasNext()) {
            ((StartupProcessor) it2.next()).processReports(this.context, this.config, arrayList);
        }
        boolean z2 = false;
        for (Report report : arrayList) {
            if (this.fileNameParser.getTimestamp(report.getFile().getName()).before(calendar)) {
                if (report.isDelete()) {
                    if (!report.getFile().delete()) {
                        ACRA.log.w(ACRA.LOG_TAG, "Could not delete report " + report.getFile());
                    }
                } else if (report.isApproved()) {
                    z2 = true;
                } else if (report.isApprove() && z) {
                    new ReportInteractionExecutor(this.context, this.config).performInteractions(report.getFile());
                }
            }
        }
        if (z2 && z) {
            this.schedulerStarter.scheduleReports(null, false);
        }
    }
}
