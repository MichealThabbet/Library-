package org.acra.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import java.util.List;
import org.acra.ACRA;
import org.acra.builder.LastActivityManager;
import org.acra.config.CoreConfiguration;
import org.acra.sender.JobSenderService;
import org.acra.sender.LegacySenderService;
import org.acra.util.SystemServices;

/* loaded from: classes3.dex */
public final class ProcessFinisher {
    private final CoreConfiguration config;
    private final Context context;
    private final LastActivityManager lastActivityManager;

    public ProcessFinisher(Context context, CoreConfiguration coreConfiguration, LastActivityManager lastActivityManager) {
        this.context = context;
        this.config = coreConfiguration;
        this.lastActivityManager = lastActivityManager;
    }

    public void endApplication() throws SecurityException {
        stopServices();
        killProcessAndExit();
    }

    public void finishLastActivity(Thread thread) {
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "Finishing activities prior to killing the Process");
        }
        boolean z = false;
        for (final Activity activity : this.lastActivityManager.getLastActivities()) {
            boolean z2 = thread == activity.getMainLooper().getThread();
            Runnable runnable = new Runnable() { // from class: org.acra.util.-$$Lambda$ProcessFinisher$XnjEXj1db0EzhCdHKgkGX9jhpB8
                @Override // java.lang.Runnable
                public final void run() {
                    ProcessFinisher.lambda$finishLastActivity$0(activity);
                }
            };
            if (z2) {
                runnable.run();
            } else {
                activity.runOnUiThread(runnable);
                z = true;
            }
        }
        if (z) {
            this.lastActivityManager.waitForAllActivitiesDestroy(100);
        }
        this.lastActivityManager.clearLastActivities();
    }

    static /* synthetic */ void lambda$finishLastActivity$0(Activity activity) {
        activity.finish();
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "Finished " + activity.getClass());
        }
    }

    private void stopServices() throws SecurityException {
        if (this.config.stopServicesOnCrash()) {
            try {
                List<ActivityManager.RunningServiceInfo> runningServices = SystemServices.getActivityManager(this.context).getRunningServices(Integer.MAX_VALUE);
                int iMyPid = Process.myPid();
                for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
                    if (runningServiceInfo.pid == iMyPid && !LegacySenderService.class.getName().equals(runningServiceInfo.service.getClassName()) && !JobSenderService.class.getName().equals(runningServiceInfo.service.getClassName())) {
                        try {
                            Intent intent = new Intent();
                            intent.setComponent(runningServiceInfo.service);
                            this.context.stopService(intent);
                        } catch (SecurityException unused) {
                            if (ACRA.DEV_LOGGING) {
                                ACRA.log.d(ACRA.LOG_TAG, "Unable to stop Service " + runningServiceInfo.service.getClassName() + ". Permission denied");
                            }
                        }
                    }
                }
            } catch (SystemServices.ServiceNotReachedException e) {
                ACRA.log.e(ACRA.LOG_TAG, "Unable to stop services", e);
            }
        }
    }

    private void killProcessAndExit() {
        Process.killProcess(Process.myPid());
        System.exit(10);
    }
}
