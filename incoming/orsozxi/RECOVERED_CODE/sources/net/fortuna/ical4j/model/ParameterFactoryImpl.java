package net.fortuna.ical4j.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URISyntaxException;
import java.util.ServiceLoader;
import net.fortuna.ical4j.model.parameter.XParameter;

@Deprecated
/* loaded from: classes3.dex */
public class ParameterFactoryImpl extends AbstractContentFactory<ParameterFactory> {
    private static final long serialVersionUID = -4034423507432249165L;

    protected ParameterFactoryImpl() {
        super(ServiceLoader.load(ParameterFactory.class, ParameterFactory.class.getClassLoader()));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.fortuna.ical4j.model.AbstractContentFactory
    public boolean factorySupports(ParameterFactory parameterFactory, String str) {
        return parameterFactory.supports(str);
    }

    public Parameter createParameter(String str, String str2) throws URISyntaxException {
        ParameterFactory factory = getFactory(str);
        if (factory != null) {
            return factory.createParameter(str2);
        }
        if (isExperimentalName(str) || allowIllegalNames()) {
            XParameter xParameter = new XParameter(str, str2);
            return xParameter;
        }
        throw new IllegalArgumentException(String.format("Unsupported parameter name: %s", str));
    }

    private boolean isExperimentalName(String str) {
        return str.startsWith("X-") && str.length() > 2;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.factoryLoader = ServiceLoader.load(ParameterFactory.class, ParameterFactory.class.getClassLoader());
    }
}
