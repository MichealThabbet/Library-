package org.acra.scheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import org.acra.config.CoreConfiguration;
import org.acra.sender.JobSenderService;
import org.acra.sender.LegacySenderService;
import org.acra.sender.SendingConductor;
import org.acra.util.IOUtils;

/* loaded from: classes3.dex */
public class DefaultSenderScheduler implements SenderScheduler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final CoreConfiguration config;
    private final Context context;

    protected void configureJob(JobInfo.Builder builder) {
    }

    public DefaultSenderScheduler(Context context, CoreConfiguration coreConfiguration) {
        this.context = context;
        this.config = coreConfiguration;
    }

    @Override // org.acra.scheduler.SenderScheduler
    public void scheduleReportSending(boolean z) {
        SendingConductor sendingConductor = new SendingConductor(this.context, this.config);
        if (!sendingConductor.getSenderInstances(false).isEmpty()) {
            Intent intent = new Intent();
            intent.putExtra(LegacySenderService.EXTRA_ONLY_SEND_SILENT_REPORTS, z);
            intent.putExtra(LegacySenderService.EXTRA_ACRA_CONFIG, this.config);
            if (Build.VERSION.SDK_INT >= 22) {
                JobScheduler jobScheduler = (JobScheduler) this.context.getSystemService("jobscheduler");
                PersistableBundle persistableBundle = new PersistableBundle();
                persistableBundle.putString(LegacySenderService.EXTRA_ACRA_CONFIG, IOUtils.serialize(this.config));
                persistableBundle.putBoolean(LegacySenderService.EXTRA_ONLY_SEND_SILENT_REPORTS, z);
                JobInfo.Builder extras = new JobInfo.Builder(0, new ComponentName(this.context, (Class<?>) JobSenderService.class)).setOverrideDeadline(0L).setExtras(persistableBundle);
                configureJob(extras);
                jobScheduler.schedule(extras.build());
            } else {
                intent.setComponent(new ComponentName(this.context, (Class<?>) LegacySenderService.class));
                this.context.startService(intent);
            }
        }
        if (sendingConductor.getSenderInstances(true).isEmpty()) {
            return;
        }
        sendingConductor.sendReports(z, true);
    }
}
