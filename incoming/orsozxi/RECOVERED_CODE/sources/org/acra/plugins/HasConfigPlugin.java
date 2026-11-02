package org.acra.plugins;

import org.acra.config.ConfigUtils;
import org.acra.config.Configuration;
import org.acra.config.CoreConfiguration;

/* loaded from: classes3.dex */
public abstract class HasConfigPlugin implements Plugin {
    private final Class<? extends Configuration> configClass;

    public HasConfigPlugin(Class<? extends Configuration> cls) {
        this.configClass = cls;
    }

    @Override // org.acra.plugins.Plugin
    public final boolean enabled(CoreConfiguration coreConfiguration) {
        return ConfigUtils.getPluginConfiguration(coreConfiguration, this.configClass).enabled();
    }
}
