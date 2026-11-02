package org.acra.plugins;

import java.io.Serializable;
import java.util.List;
import org.acra.config.CoreConfiguration;

/* loaded from: classes3.dex */
public interface PluginLoader extends Serializable {
    <T extends Plugin> List<T> load(Class<T> cls);

    <T extends Plugin> List<T> loadEnabled(CoreConfiguration coreConfiguration, Class<T> cls);
}
