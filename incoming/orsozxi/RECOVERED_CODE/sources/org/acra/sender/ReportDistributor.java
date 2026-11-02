package org.acra.sender;

import android.content.Context;
import android.content.pm.PackageManager;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.acra.ACRA;
import org.acra.config.CoreConfiguration;
import org.acra.config.DefaultRetryPolicy;
import org.acra.config.RetryPolicy;
import org.acra.data.CrashReportData;
import org.acra.file.CrashReportPersister;
import org.acra.util.IOUtils;
import org.acra.util.InstanceCreator;
import org.json.JSONException;

/* loaded from: classes3.dex */
final class ReportDistributor {
    private final CoreConfiguration config;
    private final Context context;
    private final List<ReportSender> reportSenders;

    ReportDistributor(Context context, CoreConfiguration coreConfiguration, List<ReportSender> list) {
        this.context = context;
        this.config = coreConfiguration;
        this.reportSenders = list;
    }

    public boolean distribute(File file) {
        ACRA.log.i(ACRA.LOG_TAG, "Sending report " + file);
        try {
            sendCrashReport(new CrashReportPersister().load(file));
            IOUtils.deleteFile(file);
            return true;
        } catch (IOException e) {
            ACRA.log.e(ACRA.LOG_TAG, "Failed to load crash report for " + file, e);
            IOUtils.deleteFile(file);
            return false;
        } catch (RuntimeException e2) {
            ACRA.log.e(ACRA.LOG_TAG, "Failed to send crash reports for " + file, e2);
            IOUtils.deleteFile(file);
            return false;
        } catch (ReportSenderException e3) {
            ACRA.log.e(ACRA.LOG_TAG, "Failed to send crash report for " + file, e3);
            return false;
        } catch (JSONException e4) {
            ACRA.log.e(ACRA.LOG_TAG, "Failed to load crash report for " + file, e4);
            IOUtils.deleteFile(file);
            return false;
        }
    }

    private void sendCrashReport(CrashReportData crashReportData) throws ReportSenderException {
        if (!isDebuggable() || this.config.sendReportsInDevMode()) {
            LinkedList linkedList = new LinkedList();
            for (ReportSender reportSender : this.reportSenders) {
                try {
                    if (ACRA.DEV_LOGGING) {
                        ACRA.log.d(ACRA.LOG_TAG, "Sending report using " + reportSender.getClass().getName());
                    }
                    reportSender.send(this.context, crashReportData);
                    if (ACRA.DEV_LOGGING) {
                        ACRA.log.d(ACRA.LOG_TAG, "Sent report using " + reportSender.getClass().getName());
                    }
                } catch (ReportSenderException e) {
                    linkedList.add(new RetryPolicy.FailedSender(reportSender, e));
                }
            }
            InstanceCreator instanceCreator = new InstanceCreator();
            if (linkedList.isEmpty()) {
                if (ACRA.DEV_LOGGING) {
                    ACRA.log.d(ACRA.LOG_TAG, "Report was sent by all senders");
                }
            } else {
                if (((RetryPolicy) instanceCreator.create(this.config.retryPolicyClass(), new InstanceCreator.Fallback() { // from class: org.acra.sender.-$$Lambda$b56CD9vHz9YzUv87HD1wOykEnVg
                    @Override // org.acra.util.InstanceCreator.Fallback
                    public final Object get() {
                        return new DefaultRetryPolicy();
                    }
                })).shouldRetrySend(this.reportSenders, linkedList)) {
                    throw new ReportSenderException("Policy marked this task as incomplete. ACRA will try to send this report again.", ((RetryPolicy.FailedSender) linkedList.get(0)).getException());
                }
                StringBuilder sb = new StringBuilder("ReportSenders of classes [");
                Iterator it2 = linkedList.iterator();
                while (it2.hasNext()) {
                    sb.append(((RetryPolicy.FailedSender) it2.next()).getSender().getClass().getName());
                    sb.append(", ");
                }
                sb.append("] failed, but Policy marked this task as complete. ACRA will not send this report again.");
                ACRA.log.w(ACRA.LOG_TAG, sb.toString());
            }
        }
    }

    private boolean isDebuggable() {
        try {
            return (this.context.getPackageManager().getApplicationInfo(this.context.getPackageName(), 0).flags & 2) > 0;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }
}
