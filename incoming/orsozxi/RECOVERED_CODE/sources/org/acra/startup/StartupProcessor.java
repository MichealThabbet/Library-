package org.acra.startup;

import android.content.Context;
import java.util.List;
import org.acra.config.CoreConfiguration;
import org.acra.plugins.Plugin;

/* loaded from: classes3.dex */
public interface StartupProcessor extends Plugin {
    void processReports(Context context, CoreConfiguration coreConfiguration, List<Report> list);
}
