package org.acra.collector;

import android.content.Context;
import android.content.pm.PackageInfo;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.config.CoreConfiguration;
import org.acra.data.CrashReportData;
import org.acra.util.PackageManagerWrapper;

/* loaded from: classes3.dex */
public final class PackageManagerCollector extends BaseReportFieldCollector {
    public PackageManagerCollector() {
        super(ReportField.APP_VERSION_NAME, ReportField.APP_VERSION_CODE);
    }

    @Override // org.acra.collector.BaseReportFieldCollector
    void collect(ReportField reportField, Context context, CoreConfiguration coreConfiguration, ReportBuilder reportBuilder, CrashReportData crashReportData) throws CollectorException {
        PackageInfo packageInfo = new PackageManagerWrapper(context).getPackageInfo();
        if (packageInfo == null) {
            throw new CollectorException("Failed to get package info");
        }
        int i = AnonymousClass1.$SwitchMap$org$acra$ReportField[reportField.ordinal()];
        if (i == 1) {
            crashReportData.put(ReportField.APP_VERSION_NAME, packageInfo.versionName);
        } else {
            if (i != 2) {
                return;
            }
            crashReportData.put(ReportField.APP_VERSION_CODE, packageInfo.versionCode);
        }
    }

    /* renamed from: org.acra.collector.PackageManagerCollector$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$acra$ReportField;

        static {
            int[] iArr = new int[ReportField.values().length];
            $SwitchMap$org$acra$ReportField = iArr;
            try {
                iArr[ReportField.APP_VERSION_NAME.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$acra$ReportField[ReportField.APP_VERSION_CODE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }
}
