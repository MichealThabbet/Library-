package org.acra.collector;

import android.content.Context;
import org.acra.config.CoreConfiguration;

/* loaded from: classes3.dex */
public interface ApplicationStartupCollector extends Collector {
    void collectApplicationStartUp(Context context, CoreConfiguration coreConfiguration);
}
