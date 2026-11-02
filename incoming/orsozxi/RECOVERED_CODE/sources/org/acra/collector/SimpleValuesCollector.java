package org.acra.collector;

import android.content.Context;
import android.os.Build;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.config.CoreConfiguration;
import org.acra.data.CrashReportData;
import org.acra.util.Installation;

/* loaded from: classes3.dex */
public final class SimpleValuesCollector extends BaseReportFieldCollector {
    public SimpleValuesCollector() {
        super(ReportField.IS_SILENT, ReportField.REPORT_ID, ReportField.INSTALLATION_ID, ReportField.PACKAGE_NAME, ReportField.PHONE_MODEL, ReportField.ANDROID_VERSION, ReportField.BRAND, ReportField.PRODUCT, ReportField.FILE_PATH, ReportField.USER_IP);
    }

    /* renamed from: org.acra.collector.SimpleValuesCollector$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$acra$ReportField;

        static {
            int[] iArr = new int[ReportField.values().length];
            $SwitchMap$org$acra$ReportField = iArr;
            try {
                iArr[ReportField.IS_SILENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$acra$ReportField[ReportField.REPORT_ID.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$acra$ReportField[ReportField.INSTALLATION_ID.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$acra$ReportField[ReportField.PACKAGE_NAME.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$acra$ReportField[ReportField.PHONE_MODEL.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$acra$ReportField[ReportField.ANDROID_VERSION.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$acra$ReportField[ReportField.BRAND.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$acra$ReportField[ReportField.PRODUCT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$acra$ReportField[ReportField.FILE_PATH.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$acra$ReportField[ReportField.USER_IP.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    @Override // org.acra.collector.BaseReportFieldCollector
    void collect(ReportField reportField, Context context, CoreConfiguration coreConfiguration, ReportBuilder reportBuilder, CrashReportData crashReportData) throws Exception {
        switch (AnonymousClass1.$SwitchMap$org$acra$ReportField[reportField.ordinal()]) {
            case 1:
                crashReportData.put(ReportField.IS_SILENT, reportBuilder.isSendSilently());
                return;
            case 2:
                crashReportData.put(ReportField.REPORT_ID, UUID.randomUUID().toString());
                return;
            case 3:
                crashReportData.put(ReportField.INSTALLATION_ID, Installation.id(context));
                return;
            case 4:
                crashReportData.put(ReportField.PACKAGE_NAME, context.getPackageName());
                return;
            case 5:
                crashReportData.put(ReportField.PHONE_MODEL, Build.MODEL);
                return;
            case 6:
                crashReportData.put(ReportField.ANDROID_VERSION, Build.VERSION.RELEASE);
                return;
            case 7:
                crashReportData.put(ReportField.BRAND, Build.BRAND);
                return;
            case 8:
                crashReportData.put(ReportField.PRODUCT, Build.PRODUCT);
                return;
            case 9:
                crashReportData.put(ReportField.FILE_PATH, getApplicationFilePath(context));
                return;
            case 10:
                crashReportData.put(ReportField.USER_IP, getLocalIpAddress());
                return;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override // org.acra.collector.BaseReportFieldCollector
    boolean shouldCollect(Context context, CoreConfiguration coreConfiguration, ReportField reportField, ReportBuilder reportBuilder) {
        return reportField == ReportField.IS_SILENT || reportField == ReportField.REPORT_ID || super.shouldCollect(context, coreConfiguration, reportField, reportBuilder);
    }

    private String getApplicationFilePath(Context context) {
        return context.getFilesDir().getAbsolutePath();
    }

    private static String getLocalIpAddress() throws SocketException {
        StringBuilder sb = new StringBuilder();
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        boolean z = true;
        while (networkInterfaces.hasMoreElements()) {
            Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddressNextElement = inetAddresses.nextElement();
                if (!inetAddressNextElement.isLoopbackAddress()) {
                    if (!z) {
                        sb.append('\n');
                    }
                    sb.append(inetAddressNextElement.getHostAddress());
                    z = false;
                }
            }
        }
        return sb.toString();
    }
}
