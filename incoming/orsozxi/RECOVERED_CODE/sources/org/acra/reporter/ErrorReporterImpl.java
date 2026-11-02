package org.acra.reporter;

import android.app.Application;
import android.content.SharedPreferences;
import java.lang.Thread;
import java.util.HashMap;
import java.util.Map;
import org.acra.ACRA;
import org.acra.ErrorReporter;
import org.acra.builder.LastActivityManager;
import org.acra.builder.ReportBuilder;
import org.acra.builder.ReportExecutor;
import org.acra.config.CoreConfiguration;
import org.acra.data.CrashReportDataFactory;
import org.acra.log.ACRALog;
import org.acra.prefs.SharedPreferencesFactory;
import org.acra.scheduler.SchedulerStarter;
import org.acra.scheduler.SenderScheduler;
import org.acra.startup.StartupProcessorExecutor;
import org.acra.util.ApplicationStartupProcessor;
import org.acra.util.InstanceCreator;
import org.acra.util.ProcessFinisher;

/* loaded from: classes3.dex */
public class ErrorReporterImpl implements Thread.UncaughtExceptionHandler, SharedPreferences.OnSharedPreferenceChangeListener, ErrorReporter {
    private final Application context;
    private final Map<String, String> customData = new HashMap();
    private final Thread.UncaughtExceptionHandler defaultExceptionHandler;
    private final ReportExecutor reportExecutor;
    private final SchedulerStarter schedulerStarter;
    private final boolean supportedAndroidVersion;

    public ErrorReporterImpl(Application application, CoreConfiguration coreConfiguration, boolean z, boolean z2, boolean z3) {
        this.context = application;
        this.supportedAndroidVersion = z2;
        CrashReportDataFactory crashReportDataFactory = new CrashReportDataFactory(application, coreConfiguration);
        crashReportDataFactory.collectStartUp();
        this.defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        LastActivityManager lastActivityManager = new LastActivityManager(this.context);
        new InstanceCreator();
        ProcessFinisher processFinisher = new ProcessFinisher(application, coreConfiguration, lastActivityManager);
        SchedulerStarter schedulerStarter = new SchedulerStarter(application, coreConfiguration);
        this.schedulerStarter = schedulerStarter;
        ReportExecutor reportExecutor = new ReportExecutor(application, coreConfiguration, crashReportDataFactory, this.defaultExceptionHandler, processFinisher, schedulerStarter, lastActivityManager);
        this.reportExecutor = reportExecutor;
        reportExecutor.setEnabled(z);
        if (z3) {
            new StartupProcessorExecutor(application, coreConfiguration, this.schedulerStarter).processReports(z);
            new ApplicationStartupProcessor(application, coreConfiguration).checkReports();
        }
    }

    @Override // org.acra.ErrorReporter
    public String putCustomData(String str, String str2) {
        return this.customData.put(str, str2);
    }

    @Override // org.acra.ErrorReporter
    public String removeCustomData(String str) {
        return this.customData.remove(str);
    }

    @Override // org.acra.ErrorReporter
    public void clearCustomData() {
        this.customData.clear();
    }

    @Override // org.acra.ErrorReporter
    public String getCustomData(String str) {
        return this.customData.get(str);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        if (!this.reportExecutor.isEnabled()) {
            this.reportExecutor.handReportToDefaultExceptionHandler(thread, th);
            return;
        }
        try {
            ACRA.log.e(ACRA.LOG_TAG, "ACRA caught a " + th.getClass().getSimpleName() + " for " + this.context.getPackageName(), th);
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Building report");
            }
            new ReportBuilder().uncaughtExceptionThread(thread).exception(th).customData(this.customData).endApplication().build(this.reportExecutor);
        } catch (Exception e) {
            ACRA.log.e(ACRA.LOG_TAG, "ACRA failed to capture the error - handing off to native error reporter", e);
            this.reportExecutor.handReportToDefaultExceptionHandler(thread, th);
        }
    }

    @Override // org.acra.ErrorReporter
    public void handleSilentException(Throwable th) {
        new ReportBuilder().exception(th).customData(this.customData).sendSilently().build(this.reportExecutor);
    }

    @Override // org.acra.ErrorReporter
    public void setEnabled(boolean z) {
        if (this.supportedAndroidVersion) {
            ACRALog aCRALog = ACRA.log;
            String str = ACRA.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("ACRA is ");
            sb.append(z ? "enabled" : "disabled");
            sb.append(" for ");
            sb.append(this.context.getPackageName());
            aCRALog.i(str, sb.toString());
            this.reportExecutor.setEnabled(z);
            return;
        }
        ACRA.log.w(ACRA.LOG_TAG, "ACRA requires ICS or greater. ACRA is disabled and will NOT catch crashes or send messages.");
    }

    @Override // org.acra.ErrorReporter
    public void handleException(Throwable th, boolean z) {
        ReportBuilder reportBuilder = new ReportBuilder();
        reportBuilder.exception(th).customData(this.customData);
        if (z) {
            reportBuilder.endApplication();
        }
        reportBuilder.build(this.reportExecutor);
    }

    @Override // org.acra.ErrorReporter
    public void handleException(Throwable th) {
        handleException(th, false);
    }

    @Override // org.acra.ErrorReporter
    public SenderScheduler getReportScheduler() {
        return this.schedulerStarter.getSenderScheduler();
    }

    @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
        if (ACRA.PREF_DISABLE_ACRA.equals(str) || ACRA.PREF_ENABLE_ACRA.equals(str)) {
            setEnabled(SharedPreferencesFactory.shouldEnableACRA(sharedPreferences));
        }
    }

    public void unregister() {
        Thread.setDefaultUncaughtExceptionHandler(this.defaultExceptionHandler);
    }
}
