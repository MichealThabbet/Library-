package org.acra.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import org.acra.ACRA;
import org.acra.config.CoreConfiguration;
import org.acra.file.BulkReportDeleter;
import org.acra.prefs.SharedPreferencesFactory;

@Deprecated
/* loaded from: classes3.dex */
public final class ApplicationStartupProcessor {
    private final CoreConfiguration config;
    private final Context context;
    private final BulkReportDeleter reportDeleter;

    public ApplicationStartupProcessor(Context context, CoreConfiguration coreConfiguration) {
        this.context = context;
        this.config = coreConfiguration;
        this.reportDeleter = new BulkReportDeleter(context);
    }

    public void checkReports() {
        new Thread(new Runnable() { // from class: org.acra.util.-$$Lambda$ApplicationStartupProcessor$AEStEsNPPP-hkqc3TQOmunAycU0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$checkReports$0$ApplicationStartupProcessor();
            }
        }).start();
    }

    public /* synthetic */ void lambda$checkReports$0$ApplicationStartupProcessor() {
        if (this.config.deleteOldUnsentReportsOnApplicationStart()) {
            deleteUnsentReportsFromOldAppVersion();
        }
    }

    private void deleteUnsentReportsFromOldAppVersion() {
        SharedPreferences sharedPreferencesCreate = new SharedPreferencesFactory(this.context, this.config).create();
        long j = sharedPreferencesCreate.getInt(ACRA.PREF_LAST_VERSION_NR, 0);
        int appVersion = getAppVersion();
        if (appVersion > j) {
            this.reportDeleter.deleteReports(true, 0);
            this.reportDeleter.deleteReports(false, 0);
            sharedPreferencesCreate.edit().putInt(ACRA.PREF_LAST_VERSION_NR, appVersion).apply();
        }
    }

    private int getAppVersion() {
        PackageInfo packageInfo = new PackageManagerWrapper(this.context).getPackageInfo();
        if (packageInfo == null) {
            return 0;
        }
        return packageInfo.versionCode;
    }
}
