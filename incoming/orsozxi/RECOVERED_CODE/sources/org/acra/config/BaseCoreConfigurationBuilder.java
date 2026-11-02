package org.acra.config;

import android.content.Context;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.plugins.PluginLoader;
import org.acra.plugins.ServicePluginLoader;
import org.acra.util.StubCreator;

/* loaded from: classes3.dex */
public final class BaseCoreConfigurationBuilder {
    private final Context app;
    private List<ConfigurationBuilder> configurationBuilders;
    private List<Configuration> configurations;
    private final Map<ReportField, Boolean> reportContentChanges = new EnumMap(ReportField.class);
    private PluginLoader pluginLoader = new ServicePluginLoader();

    static /* synthetic */ Object lambda$getPluginConfigurationBuilder$0(Object obj, Method method, Object[] objArr) throws Throwable {
        return obj;
    }

    BaseCoreConfigurationBuilder(Context context) {
        this.app = context;
    }

    private List<ConfigurationBuilder> configurationBuilders() {
        if (this.configurationBuilders == null) {
            List listLoad = this.pluginLoader.load(ConfigurationBuilderFactory.class);
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Found ConfigurationBuilderFactories : " + listLoad);
            }
            this.configurationBuilders = new ArrayList(listLoad.size());
            Iterator it2 = listLoad.iterator();
            while (it2.hasNext()) {
                this.configurationBuilders.add(((ConfigurationBuilderFactory) it2.next()).create(this.app));
            }
        }
        return this.configurationBuilders;
    }

    public void setPluginLoader(PluginLoader pluginLoader) {
        this.pluginLoader = pluginLoader;
    }

    PluginLoader pluginLoader() {
        return this.pluginLoader;
    }

    void preBuild() throws ACRAConfigurationException {
        this.configurations = new ArrayList();
        List<ConfigurationBuilder> listConfigurationBuilders = configurationBuilders();
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "Found ConfigurationBuilders : " + listConfigurationBuilders);
        }
        Iterator<ConfigurationBuilder> it2 = listConfigurationBuilders.iterator();
        while (it2.hasNext()) {
            this.configurations.add(it2.next().build());
        }
    }

    Set<ReportField> transformReportContent(ReportField[] reportFieldArr) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (reportFieldArr.length != 0) {
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Using custom Report Fields");
            }
            linkedHashSet.addAll(Arrays.asList(reportFieldArr));
        } else {
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Using default Report Fields");
            }
            linkedHashSet.addAll(Arrays.asList(ACRAConstants.DEFAULT_REPORT_FIELDS));
        }
        for (Map.Entry<ReportField, Boolean> entry : this.reportContentChanges.entrySet()) {
            if (entry.getValue().booleanValue()) {
                linkedHashSet.add(entry.getKey());
            } else {
                linkedHashSet.remove(entry.getKey());
            }
        }
        return linkedHashSet;
    }

    public void setReportField(ReportField reportField, boolean z) {
        this.reportContentChanges.put(reportField, Boolean.valueOf(z));
    }

    List<Configuration> pluginConfigurations() {
        return this.configurations;
    }

    public <R extends ConfigurationBuilder> R getPluginConfigurationBuilder(Class<R> cls) {
        Iterator<ConfigurationBuilder> it2 = configurationBuilders().iterator();
        while (it2.hasNext()) {
            R r = (R) it2.next();
            if (cls.isAssignableFrom(r.getClass())) {
                return r;
            }
        }
        if (cls.isInterface()) {
            ACRA.log.w(ACRA.LOG_TAG, "Couldn't find ConfigurationBuilder " + cls.getSimpleName() + ". ALL CALLS TO IT WILL BE IGNORED!");
            return (R) StubCreator.createStub(cls, new InvocationHandler() { // from class: org.acra.config.-$$Lambda$BaseCoreConfigurationBuilder$bIkqtXdpCl2MC6p3YJQnSsYOLxY
                @Override // java.lang.reflect.InvocationHandler
                public final Object invoke(Object obj, Method method, Object[] objArr) {
                    return BaseCoreConfigurationBuilder.lambda$getPluginConfigurationBuilder$0(obj, method, objArr);
                }
            });
        }
        throw new IllegalArgumentException("Class " + cls.getName() + " is not a registered ConfigurationBuilder");
    }
}
