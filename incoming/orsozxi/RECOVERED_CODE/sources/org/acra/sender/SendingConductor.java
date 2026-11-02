package org.acra.sender;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.acra.ACRA;
import org.acra.collections.ImmutableList;
import org.acra.config.CoreConfiguration;
import org.acra.file.CrashReportFileNameParser;
import org.acra.file.ReportLocator;
import org.acra.log.ACRALog;
import org.acra.util.InstanceCreator;
import org.acra.util.ToastSender;

/* loaded from: classes3.dex */
public class SendingConductor {
    private final CoreConfiguration config;
    private final Context context;
    private final ReportLocator locator;

    public SendingConductor(Context context, CoreConfiguration coreConfiguration) {
        this.context = context;
        this.config = coreConfiguration;
        this.locator = new ReportLocator(context);
    }

    public void sendReports(boolean z, boolean z2) {
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "About to start sending reports from SenderService");
        }
        try {
            List<ReportSender> senderInstances = getSenderInstances(z2);
            if (senderInstances.isEmpty()) {
                if (ACRA.DEV_LOGGING) {
                    ACRA.log.d(ACRA.LOG_TAG, "No ReportSenders configured - adding NullSender");
                }
                senderInstances.add(new NullSender());
            }
            File[] approvedReports = this.locator.getApprovedReports();
            ReportDistributor reportDistributor = new ReportDistributor(this.context, this.config, senderInstances);
            CrashReportFileNameParser crashReportFileNameParser = new CrashReportFileNameParser();
            int i = 0;
            boolean z3 = false;
            for (File file : approvedReports) {
                boolean z4 = !crashReportFileNameParser.isSilent(file.getName());
                if (!z || !z4) {
                    z3 |= z4;
                    if (i >= 5) {
                        break;
                    } else if (reportDistributor.distribute(file)) {
                        i++;
                    }
                }
            }
            if (z3) {
                final String strReportSendSuccessToast = i > 0 ? this.config.reportSendSuccessToast() : this.config.reportSendFailureToast();
                if (strReportSendSuccessToast != null) {
                    if (ACRA.DEV_LOGGING) {
                        ACRALog aCRALog = ACRA.log;
                        String str = ACRA.LOG_TAG;
                        StringBuilder sb = new StringBuilder();
                        sb.append("About to show ");
                        sb.append(i > 0 ? "success" : "failure");
                        sb.append(" toast");
                        aCRALog.d(str, sb.toString());
                    }
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: org.acra.sender.-$$Lambda$SendingConductor$gG-_c2nGWYRkk24ZA2Vo0qiFu74
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$sendReports$0$SendingConductor(strReportSendSuccessToast);
                        }
                    });
                }
            }
        } catch (Exception e) {
            ACRA.log.e(ACRA.LOG_TAG, "", e);
        }
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "Finished sending reports from SenderService");
        }
    }

    public /* synthetic */ void lambda$sendReports$0$SendingConductor(String str) {
        ToastSender.sendToast(this.context, str, 1);
    }

    public List<ReportSender> getSenderInstances(boolean z) {
        List listCreate;
        ImmutableList<Class<? extends ReportSenderFactory>> immutableListReportSenderFactoryClasses = this.config.reportSenderFactoryClasses();
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "config#reportSenderFactoryClasses : " + immutableListReportSenderFactoryClasses);
        }
        if (immutableListReportSenderFactoryClasses.isEmpty()) {
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Using PluginLoader to find ReportSender factories");
            }
            listCreate = this.config.pluginLoader().loadEnabled(this.config, ReportSenderFactory.class);
        } else {
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Creating reportSenderFactories for reportSenderFactory config");
            }
            listCreate = new InstanceCreator().create(immutableListReportSenderFactoryClasses);
        }
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "reportSenderFactories : " + listCreate);
        }
        ArrayList arrayList = new ArrayList();
        Iterator it2 = listCreate.iterator();
        while (it2.hasNext()) {
            ReportSender reportSenderCreate = ((ReportSenderFactory) it2.next()).create(this.context, this.config);
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Adding reportSender : " + reportSenderCreate);
            }
            if (z == reportSenderCreate.requiresForeground()) {
                arrayList.add(reportSenderCreate);
            }
        }
        return arrayList;
    }
}
