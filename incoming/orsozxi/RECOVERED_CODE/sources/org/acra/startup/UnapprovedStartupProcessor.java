package org.acra.startup;

import android.content.Context;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.acra.config.CoreConfiguration;
import org.acra.file.LastModifiedComparator;
import org.acra.plugins.Plugin;

/* loaded from: classes3.dex */
public class UnapprovedStartupProcessor implements StartupProcessor {
    @Override // org.acra.plugins.Plugin
    public /* synthetic */ boolean enabled(CoreConfiguration coreConfiguration) {
        return Plugin.CC.$default$enabled(this, coreConfiguration);
    }

    @Override // org.acra.startup.StartupProcessor
    public void processReports(Context context, CoreConfiguration coreConfiguration, List<Report> list) {
        if (coreConfiguration.deleteUnapprovedReportsOnApplicationStart()) {
            ArrayList arrayList = new ArrayList();
            for (Report report : list) {
                if (!report.isApproved()) {
                    arrayList.add(report);
                }
            }
            if (arrayList.isEmpty()) {
                return;
            }
            final LastModifiedComparator lastModifiedComparator = new LastModifiedComparator();
            Collections.sort(arrayList, new Comparator() { // from class: org.acra.startup.-$$Lambda$UnapprovedStartupProcessor$aq1niLwqQioBhgzfTG1K-amWGeE
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return lastModifiedComparator.compare(((Report) obj).getFile(), ((Report) obj2).getFile());
                }
            });
            if (coreConfiguration.deleteUnapprovedReportsOnApplicationStart()) {
                for (int i = 0; i < arrayList.size() - 1; i++) {
                    ((Report) arrayList.get(i)).delete();
                }
            }
            ((Report) arrayList.get(arrayList.size() - 1)).approve();
        }
    }
}
