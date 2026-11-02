package net.fortuna.ical4j.model;

import net.fortuna.ical4j.util.Configurator;

/* loaded from: classes3.dex */
public abstract class TimeZoneRegistryFactory {
    public static final String KEY_FACTORY_CLASS = "net.fortuna.ical4j.timezone.registry";
    private static TimeZoneRegistryFactory instance = (TimeZoneRegistryFactory) Configurator.getObjectProperty(KEY_FACTORY_CLASS).orElse(new DefaultTimeZoneRegistryFactory());

    public abstract TimeZoneRegistry createRegistry();

    public static TimeZoneRegistryFactory getInstance() {
        return instance;
    }
}
