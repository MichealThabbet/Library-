package org.acra.collector;

import android.content.Context;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.collector.Collector;
import org.acra.config.CoreConfiguration;
import org.acra.data.CrashReportData;
import org.acra.plugins.Plugin;

/* loaded from: classes3.dex */
abstract class BaseReportFieldCollector implements Collector {
    private final ReportField[] reportFields;

    abstract void collect(ReportField reportField, Context context, CoreConfiguration coreConfiguration, ReportBuilder reportBuilder, CrashReportData crashReportData) throws Exception;

    @Override // org.acra.plugins.Plugin
    public /* synthetic */ boolean enabled(CoreConfiguration coreConfiguration) {
        return Plugin.CC.$default$enabled(this, coreConfiguration);
    }

    @Override // org.acra.collector.Collector
    public /* synthetic */ Collector.Order getOrder() {
        return Collector.Order.NORMAL;
    }

    BaseReportFieldCollector(ReportField reportField, ReportField... reportFieldArr) {
        ReportField[] reportFieldArr2 = new ReportField[reportFieldArr.length + 1];
        this.reportFields = reportFieldArr2;
        reportFieldArr2[0] = reportField;
        if (reportFieldArr.length > 0) {
            System.arraycopy(reportFieldArr, 0, reportFieldArr2, 1, reportFieldArr.length);
        }
    }

    boolean shouldCollect(Context context, CoreConfiguration coreConfiguration, ReportField reportField, ReportBuilder reportBuilder) {
        return coreConfiguration.reportContent().contains(reportField);
    }

    @Override // org.acra.collector.Collector
    public final void collect(Context context, CoreConfiguration coreConfiguration, ReportBuilder reportBuilder, CrashReportData crashReportData) throws CollectorException {
        for (ReportField reportField : this.reportFields) {
            try {
                if (shouldCollect(context, coreConfiguration, reportField, reportBuilder)) {
                    collect(reportField, context, coreConfiguration, reportBuilder, crashReportData);
                }
            } catch (Exception e) {
                crashReportData.put(reportField, (String) null);
                throw new CollectorException("Error while retrieving " + reportField.name() + " data:" + e.getMessage(), e);
            }
        }
    }
}
