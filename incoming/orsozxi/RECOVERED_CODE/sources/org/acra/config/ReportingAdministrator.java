package org.acra.config;

import android.content.Context;
import org.acra.builder.LastActivityManager;
import org.acra.builder.ReportBuilder;
import org.acra.data.CrashReportData;
import org.acra.plugins.Plugin;

/* loaded from: classes3.dex */
public interface ReportingAdministrator extends Plugin {

    /* renamed from: org.acra.config.ReportingAdministrator$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void $default$notifyReportDropped(ReportingAdministrator reportingAdministrator, Context context, CoreConfiguration coreConfiguration) {
        }

        public static boolean $default$shouldFinishActivity(ReportingAdministrator reportingAdministrator, Context context, CoreConfiguration coreConfiguration, LastActivityManager lastActivityManager) {
            return true;
        }

        public static boolean $default$shouldKillApplication(ReportingAdministrator reportingAdministrator, Context context, CoreConfiguration coreConfiguration, ReportBuilder reportBuilder, CrashReportData crashReportData) {
            return true;
        }

        public static boolean $default$shouldSendReport(ReportingAdministrator reportingAdministrator, Context context, CoreConfiguration coreConfiguration, CrashReportData crashReportData) {
            return true;
        }

        public static boolean $default$shouldStartCollecting(ReportingAdministrator reportingAdministrator, Context context, CoreConfiguration coreConfiguration, ReportBuilder reportBuilder) {
            return true;
        }
    }

    void notifyReportDropped(Context context, CoreConfiguration coreConfiguration);

    boolean shouldFinishActivity(Context context, CoreConfiguration coreConfiguration, LastActivityManager lastActivityManager);

    boolean shouldKillApplication(Context context, CoreConfiguration coreConfiguration, ReportBuilder reportBuilder, CrashReportData crashReportData);

    boolean shouldSendReport(Context context, CoreConfiguration coreConfiguration, CrashReportData crashReportData);

    boolean shouldStartCollecting(Context context, CoreConfiguration coreConfiguration, ReportBuilder reportBuilder);
}
