package org.acra.legacy;

import android.content.Context;
import java.io.File;
import java.io.FilenameFilter;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.file.CrashReportFileNameParser;
import org.acra.file.ReportLocator;

/* loaded from: classes3.dex */
final class ReportMigrator {
    private final Context context;
    private final CrashReportFileNameParser fileNameParser = new CrashReportFileNameParser();
    private final ReportLocator reportLocator;

    ReportMigrator(Context context) {
        this.context = context;
        this.reportLocator = new ReportLocator(context);
    }

    void migrate() {
        ACRA.log.i(ACRA.LOG_TAG, "Migrating unsent ACRA reports to new file locations");
        File[] crashReportFiles = getCrashReportFiles();
        for (File file : crashReportFiles) {
            String name = file.getName();
            if (this.fileNameParser.isApproved(name)) {
                if (file.renameTo(new File(this.reportLocator.getApprovedFolder(), name)) && ACRA.DEV_LOGGING) {
                    ACRA.log.d(ACRA.LOG_TAG, "Cold not migrate unsent ACRA crash report : " + name);
                }
            } else if (file.renameTo(new File(this.reportLocator.getUnapprovedFolder(), name)) && ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Cold not migrate unsent ACRA crash report : " + name);
            }
        }
        ACRA.log.i(ACRA.LOG_TAG, "Migrated " + crashReportFiles.length + " unsent reports");
    }

    private File[] getCrashReportFiles() {
        File filesDir = this.context.getFilesDir();
        if (filesDir == null) {
            ACRA.log.w(ACRA.LOG_TAG, "Application files directory does not exist! The application may not be installed correctly. Please try reinstalling.");
            return new File[0];
        }
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "Looking for error files in " + filesDir.getAbsolutePath());
        }
        File[] fileArrListFiles = filesDir.listFiles(new FilenameFilter() { // from class: org.acra.legacy.-$$Lambda$ReportMigrator$yKiW9Ll1HFwaZ9CdauG8grunHA0
            @Override // java.io.FilenameFilter
            public final boolean accept(File file, String str) {
                return str.endsWith(ACRAConstants.REPORTFILE_EXTENSION);
            }
        });
        return fileArrListFiles == null ? new File[0] : fileArrListFiles;
    }
}
