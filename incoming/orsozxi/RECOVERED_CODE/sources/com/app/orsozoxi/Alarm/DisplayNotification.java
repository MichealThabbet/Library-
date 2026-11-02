package com.app.orsozoxi.Alarm;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import com.app.orsozoxi.Alarm.Services.FlyBitchJobService;

/* loaded from: classes.dex */
public class DisplayNotification extends JobService {
    @Override // android.app.job.JobService
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(JobParameters jobParameters) {
        if (jobParameters.getExtras() != null && jobParameters.getExtras().containsKey("alarm")) {
            Intent intent = new Intent();
            intent.putExtra("alarm", jobParameters.getExtras().getString("alarm"));
            FlyBitchJobService.enqueueWork(this, intent);
        }
        jobFinished(jobParameters, false);
        return true;
    }
}
