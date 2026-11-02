package net.fortuna.ical4j.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ServiceLoader;
import net.fortuna.ical4j.model.property.XProperty;

@Deprecated
/* loaded from: classes3.dex */
public class PropertyFactoryImpl extends AbstractContentFactory<PropertyFactory> {
    private static final long serialVersionUID = -7174232004486979641L;

    protected PropertyFactoryImpl() {
        super(ServiceLoader.load(PropertyFactory.class, PropertyFactory.class.getClassLoader()));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.fortuna.ical4j.model.AbstractContentFactory
    public boolean factorySupports(PropertyFactory propertyFactory, String str) {
        return propertyFactory.supports(str);
    }

    public Property createProperty(String str) {
        PropertyFactory factory = getFactory(str);
        if (factory != null) {
            return factory.createProperty();
        }
        if (isExperimentalName(str)) {
            return new XProperty(str);
        }
        if (allowIllegalNames()) {
            return new XProperty(str);
        }
        throw new IllegalArgumentException("Illegal property [" + str + "]");
    }

    public Property createProperty(String str, ParameterList parameterList, String str2) throws URISyntaxException, IOException, ParseException {
        PropertyFactory factory = getFactory(str);
        if (factory != null) {
            return factory.createProperty(parameterList, str2);
        }
        if (isExperimentalName(str)) {
            return new XProperty(str, parameterList, str2);
        }
        if (allowIllegalNames()) {
            return new XProperty(str, parameterList, str2);
        }
        throw new IllegalArgumentException("Illegal property [" + str + "]");
    }

    private boolean isExperimentalName(String str) {
        return str.startsWith("X-") && str.length() > 2;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.factoryLoader = ServiceLoader.load(PropertyFactory.class, PropertyFactory.class.getClassLoader());
    }
}
