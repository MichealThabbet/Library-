package org.acra.interaction;

import android.content.Context;
import java.io.File;
import org.acra.config.CoreConfiguration;
import org.acra.plugins.Plugin;

/* loaded from: classes3.dex */
public interface ReportInteraction extends Plugin {
    boolean performInteraction(Context context, CoreConfiguration coreConfiguration, File file);
}
