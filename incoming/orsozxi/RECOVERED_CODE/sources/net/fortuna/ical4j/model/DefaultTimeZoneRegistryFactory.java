package net.fortuna.ical4j.model;

/* loaded from: classes3.dex */
public class DefaultTimeZoneRegistryFactory extends TimeZoneRegistryFactory {
    @Override // net.fortuna.ical4j.model.TimeZoneRegistryFactory
    public TimeZoneRegistry createRegistry() {
        return new TimeZoneRegistryImpl();
    }
}
