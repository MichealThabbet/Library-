package org.acra.collector;

import android.content.Context;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.config.CoreConfiguration;
import org.acra.data.CrashReportData;

/* loaded from: classes3.dex */
public final class TimeCollector extends BaseReportFieldCollector implements ApplicationStartupCollector {
    private Calendar appStartDate;
    private final SimpleDateFormat dateFormat;

    public TimeCollector() {
        super(ReportField.USER_APP_START_DATE, ReportField.USER_CRASH_DATE);
        this.dateFormat = new SimpleDateFormat(ACRAConstants.DATE_TIME_FORMAT_STRING, Locale.ENGLISH);
    }

    /* renamed from: org.acra.collector.TimeCollector$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$acra$ReportField;

        static {
            int[] iArr = new int[ReportField.values().length];
            $SwitchMap$org$acra$ReportField = iArr;
            try {
                iArr[ReportField.USER_APP_START_DATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$acra$ReportField[ReportField.USER_CRASH_DATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // org.acra.collector.BaseReportFieldCollector
    void collect(ReportField reportField, Context context, CoreConfiguration coreConfiguration, ReportBuilder reportBuilder, CrashReportData crashReportData) {
        Calendar gregorianCalendar;
        int i = AnonymousClass1.$SwitchMap$org$acra$ReportField[reportField.ordinal()];
        if (i == 1) {
            gregorianCalendar = this.appStartDate;
        } else if (i == 2) {
            gregorianCalendar = new GregorianCalendar();
        } else {
            throw new IllegalArgumentException();
        }
        crashReportData.put(reportField, getTimeString(gregorianCalendar));
    }

    @Override // org.acra.collector.ApplicationStartupCollector
    public void collectApplicationStartUp(Context context, CoreConfiguration coreConfiguration) {
        if (coreConfiguration.reportContent().contains(ReportField.USER_APP_START_DATE)) {
            this.appStartDate = new GregorianCalendar();
        }
    }

    @Override // org.acra.collector.BaseReportFieldCollector
    boolean shouldCollect(Context context, CoreConfiguration coreConfiguration, ReportField reportField, ReportBuilder reportBuilder) {
        return reportField == ReportField.USER_CRASH_DATE || super.shouldCollect(context, coreConfiguration, reportField, reportBuilder);
    }

    private String getTimeString(Calendar calendar) {
        return this.dateFormat.format(Long.valueOf(calendar.getTimeInMillis()));
    }
}
