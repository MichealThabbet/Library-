package org.acra.sender;

import android.content.Context;
import org.acra.config.CoreConfiguration;
import org.acra.plugins.Plugin;

/* loaded from: classes3.dex */
public interface ReportSenderFactory extends Plugin {
    ReportSender create(Context context, CoreConfiguration coreConfiguration);
}
