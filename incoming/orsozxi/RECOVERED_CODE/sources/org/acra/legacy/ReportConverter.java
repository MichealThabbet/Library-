package org.acra.legacy;

import android.content.Context;
import com.facebook.internal.ServerProtocol;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.data.CrashReportData;
import org.acra.file.CrashReportPersister;
import org.acra.file.ReportLocator;
import org.acra.util.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
class ReportConverter {
    private static final int CONTINUE = 3;
    private static final int IGNORE = 5;
    private static final int KEY_DONE = 4;
    private static final int NONE = 0;
    private static final int SLASH = 1;
    private static final int UNICODE = 2;
    private final Context context;

    ReportConverter(Context context) {
        this.context = context;
    }

    void convert() throws Throwable {
        BufferedInputStream bufferedInputStream;
        Exception e;
        ACRA.log.i(ACRA.LOG_TAG, "Converting unsent ACRA reports to json");
        ReportLocator reportLocator = new ReportLocator(this.context);
        CrashReportPersister crashReportPersister = new CrashReportPersister();
        ArrayList<File> arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(reportLocator.getUnapprovedReports()));
        arrayList.addAll(Arrays.asList(reportLocator.getApprovedReports()));
        int i = 0;
        for (File file : arrayList) {
            BufferedInputStream bufferedInputStream2 = null;
            try {
                bufferedInputStream = new BufferedInputStream(new FileInputStream(file), 8192);
                try {
                    try {
                        CrashReportData crashReportDataLegacyLoad = legacyLoad(new InputStreamReader(bufferedInputStream, "ISO8859-1"));
                        if (crashReportDataLegacyLoad.containsKey(ReportField.REPORT_ID) && crashReportDataLegacyLoad.containsKey(ReportField.USER_CRASH_DATE)) {
                            crashReportPersister.store(crashReportDataLegacyLoad, file);
                            i++;
                        } else {
                            IOUtils.deleteFile(file);
                        }
                    } catch (Throwable th) {
                        th = th;
                        bufferedInputStream2 = bufferedInputStream;
                        IOUtils.safeClose(bufferedInputStream2);
                        throw th;
                    }
                } catch (Exception e2) {
                    e = e2;
                    try {
                        crashReportPersister.load(file);
                        if (ACRA.DEV_LOGGING) {
                            ACRA.log.d(ACRA.LOG_TAG, "Tried to convert already converted report file " + file.getPath() + ". Ignoring");
                        }
                    } catch (Exception unused) {
                        ACRA.log.w(ACRA.LOG_TAG, "Unable to read report file " + file.getPath() + ". Deleting", e);
                        IOUtils.deleteFile(file);
                    }
                    IOUtils.safeClose(bufferedInputStream);
                }
            } catch (Exception e3) {
                bufferedInputStream = null;
                e = e3;
            } catch (Throwable th2) {
                th = th2;
                IOUtils.safeClose(bufferedInputStream2);
                throw th;
            }
            IOUtils.safeClose(bufferedInputStream);
        }
        ACRA.log.i(ACRA.LOG_TAG, "Converted " + i + " unsent reports");
    }

    /* JADX WARN: Removed duplicated region for block: B:110:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x00b2 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:164:0x015c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:191:0x00f8 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00e9 A[PHI: r7
      0x00e9: PHI (r7v7 char) = (r7v1 char), (r7v1 char), (r7v9 char) binds: [B:124:0x015d, B:127:0x0162, B:71:0x00e8] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private synchronized org.acra.data.CrashReportData legacyLoad(java.io.Reader r19) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 425
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.acra.legacy.ReportConverter.legacyLoad(java.io.Reader):org.acra.data.CrashReportData");
    }

    private void putKeyValue(CrashReportData crashReportData, String str, String str2) {
        try {
            try {
                crashReportData.put(str, new JSONObject(str2));
            } catch (NumberFormatException unused) {
                char c = 65535;
                int iHashCode = str2.hashCode();
                if (iHashCode != 3569038) {
                    if (iHashCode == 97196323 && str2.equals("false")) {
                        c = 1;
                    }
                } else if (str2.equals(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE)) {
                    c = 0;
                }
                if (c == 0) {
                    crashReportData.put(str, true);
                } else if (c == 1) {
                    crashReportData.put(str, false);
                } else {
                    crashReportData.put(str, str2);
                }
            }
        } catch (JSONException unused2) {
            crashReportData.put(str, Double.valueOf(str2).doubleValue());
        }
    }
}
