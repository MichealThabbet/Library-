package com.robohorse.gpversionchecker;

import android.app.Activity;
import android.preference.PreferenceManager;
import com.robohorse.gpversionchecker.domain.CheckingStrategy;
import com.robohorse.gpversionchecker.domain.Version;
import com.robohorse.gpversionchecker.domain.VersionCheckedException;
import com.robohorse.gpversionchecker.domain.VersionInfoListener;
import com.robohorse.gpversionchecker.manager.ServiceStartManager;
import com.robohorse.gpversionchecker.manager.UIManager;
import com.robohorse.gpversionchecker.provider.SharedDataProvider;
import com.robohorse.gpversionchecker.utils.DateFormatUtils;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class GPVersionChecker {
    private static WeakReference<Activity> activityWeakReference;
    private static ServiceStartManager serviceStartManager;
    private static CheckingStrategy strategy;
    private static UIManager uiManager;
    public static boolean useLog;
    private static VersionInfoListener versionInfoListener;

    private GPVersionChecker() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void proceed() {
        Activity activity = activityWeakReference.get();
        if (activity == null) {
            throw new IllegalStateException("Activity cannot be null for GPVersionChecker context");
        }
        serviceStartManager.checkAndStartService(activity, strategy);
    }

    static void onResponseReceived(final Version version, final Throwable th) {
        Activity activity;
        WeakReference<Activity> weakReference = activityWeakReference;
        if (weakReference == null || (activity = weakReference.get()) == null || activity.isFinishing()) {
            return;
        }
        try {
            serviceStartManager.onResulted(strategy, version);
            if (versionInfoListener != null) {
                activity.runOnUiThread(new Runnable() { // from class: com.robohorse.gpversionchecker.GPVersionChecker.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (version != null && th == null) {
                            GPVersionChecker.versionInfoListener.onResulted(version);
                        } else if (th != null) {
                            GPVersionChecker.versionInfoListener.onErrorHandled(th);
                        }
                    }
                });
            } else {
                if (version == null || !version.isNeedToUpdate()) {
                    return;
                }
                uiManager.showInfoView(activity, version);
            }
        } catch (VersionCheckedException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void resetState(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
        versionInfoListener = null;
        strategy = CheckingStrategy.ALWAYS;
    }

    public static class Builder {
        public Builder(Activity activity) {
            GPVersionChecker.resetState(activity);
            UIManager unused = GPVersionChecker.uiManager = new UIManager();
            DateFormatUtils dateFormatUtils = new DateFormatUtils();
            ServiceStartManager unused2 = GPVersionChecker.serviceStartManager = new ServiceStartManager(new SharedDataProvider(PreferenceManager.getDefaultSharedPreferences(activity), dateFormatUtils), dateFormatUtils);
        }

        protected Builder(Activity activity, UIManager uIManager) {
            GPVersionChecker.resetState(activity);
            UIManager unused = GPVersionChecker.uiManager = uIManager;
        }

        public Builder setVersionInfoListener(VersionInfoListener versionInfoListener) {
            VersionInfoListener unused = GPVersionChecker.versionInfoListener = versionInfoListener;
            return this;
        }

        public Builder setLoggingEnable(boolean z) {
            GPVersionChecker.useLog = z;
            return this;
        }

        public Builder setCheckingStrategy(CheckingStrategy checkingStrategy) {
            CheckingStrategy unused = GPVersionChecker.strategy = checkingStrategy;
            return this;
        }

        public Builder create() {
            GPVersionChecker.proceed();
            return this;
        }
    }
}
