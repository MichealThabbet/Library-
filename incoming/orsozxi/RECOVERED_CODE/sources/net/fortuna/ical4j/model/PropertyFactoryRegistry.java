package net.fortuna.ical4j.model;

@Deprecated
/* loaded from: classes3.dex */
public final class PropertyFactoryRegistry extends PropertyFactoryImpl {
    private static final long serialVersionUID = 3924903719847189199L;

    public void register(String str, PropertyFactory propertyFactory) {
        registerExtendedFactory(str, propertyFactory);
    }
}
