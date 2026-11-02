package org.acra.scheduler;

import android.content.Context;
import org.acra.config.CoreConfiguration;
import org.acra.plugins.Plugin;

/* loaded from: classes3.dex */
public interface SenderSchedulerFactory extends Plugin {
    SenderScheduler create(Context context, CoreConfiguration coreConfiguration);
}
