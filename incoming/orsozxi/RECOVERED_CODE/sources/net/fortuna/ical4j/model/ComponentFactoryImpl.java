package net.fortuna.ical4j.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ServiceLoader;
import net.fortuna.ical4j.model.component.XComponent;

@Deprecated
/* loaded from: classes3.dex */
public final class ComponentFactoryImpl extends AbstractContentFactory<ComponentFactory> {
    public ComponentFactoryImpl() {
        super(ServiceLoader.load(ComponentFactory.class, ComponentFactory.class.getClassLoader()));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.fortuna.ical4j.model.AbstractContentFactory
    public boolean factorySupports(ComponentFactory componentFactory, String str) {
        return componentFactory.supports(str);
    }

    public <T extends Component> T createComponent(String str) {
        ComponentFactory factory = getFactory(str);
        if (factory != null) {
            return (T) factory.createComponent();
        }
        if (isExperimentalName(str) || allowIllegalNames()) {
            XComponent xComponent = new XComponent(str);
            return xComponent;
        }
        throw new IllegalArgumentException("Unsupported component [" + str + "]");
    }

    public <T extends Component> T createComponent(String str, PropertyList propertyList) {
        ComponentFactory factory = getFactory(str);
        if (factory != null) {
            return (T) factory.createComponent(propertyList);
        }
        if (isExperimentalName(str) || allowIllegalNames()) {
            XComponent xComponent = new XComponent(str, propertyList);
            return xComponent;
        }
        throw new IllegalArgumentException("Unsupported component [" + str + "]");
    }

    public <T extends Component> T createComponent(String str, PropertyList propertyList, ComponentList<? extends Component> componentList) {
        ComponentFactory factory = getFactory(str);
        if (factory != null) {
            return (T) factory.createComponent(propertyList, componentList);
        }
        throw new IllegalArgumentException("Unsupported component [" + str + "]");
    }

    private boolean isExperimentalName(String str) {
        return str.startsWith("X-") && str.length() > 2;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.factoryLoader = ServiceLoader.load(ComponentFactory.class, ComponentFactory.class.getClassLoader());
    }
}
