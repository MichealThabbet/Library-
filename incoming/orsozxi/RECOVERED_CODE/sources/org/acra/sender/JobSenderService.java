package org.acra.sender;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.PersistableBundle;
import org.acra.config.CoreConfiguration;
import org.acra.util.IOUtils;

/* loaded from: classes3.dex */
public class JobSenderService extends JobService {
    @Override // android.app.job.JobService
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(final JobParameters jobParameters) {
        PersistableBundle extras = jobParameters.getExtras();
        final CoreConfiguration coreConfiguration = (CoreConfiguration) IOUtils.deserialize(CoreConfiguration.class, extras.getString(LegacySenderService.EXTRA_ACRA_CONFIG));
        final boolean z = extras.getBoolean(LegacySenderService.EXTRA_ONLY_SEND_SILENT_REPORTS);
        if (coreConfiguration == null) {
            return true;
        }
        new Thread(new Runnable() { // from class: org.acra.sender.-$$Lambda$JobSenderService$I998F0ZStMzSYVUE1etDPmDKpKQ
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onStartJob$0$JobSenderService(coreConfiguration, z, jobParameters);
            }
        }).start();
        return true;
    }

    public /* synthetic */ void lambda$onStartJob$0$JobSenderService(CoreConfiguration coreConfiguration, boolean z, JobParameters jobParameters) {
        new SendingConductor(this, coreConfiguration).sendReports(z, false);
        jobFinished(jobParameters, false);
    }
}
