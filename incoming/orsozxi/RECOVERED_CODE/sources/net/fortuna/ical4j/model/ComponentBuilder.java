package net.fortuna.ical4j.model;

import java.util.ArrayList;
import java.util.List;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.XComponent;

/* loaded from: classes3.dex */
public class ComponentBuilder<T extends Component> extends AbstractContentBuilder {
    private String name;
    private final List<ComponentFactory> factories = new ArrayList();
    private PropertyList properties = new PropertyList();
    private ComponentList subComponents = new ComponentList();

    public ComponentBuilder factories(List<ComponentFactory> list) {
        this.factories.addAll(list);
        return this;
    }

    public ComponentBuilder name(String str) {
        this.name = str.toUpperCase();
        return this;
    }

    public ComponentBuilder property(Property property) {
        this.properties.add((PropertyList) property);
        return this;
    }

    public ComponentBuilder subComponent(Component component) {
        this.subComponents.add(component);
        return this;
    }

    public T build() {
        T t = null;
        for (ComponentFactory componentFactory : this.factories) {
            if (componentFactory.supports(this.name)) {
                if (!this.subComponents.isEmpty()) {
                    t = (T) componentFactory.createComponent(this.properties, this.subComponents);
                } else {
                    t = (T) componentFactory.createComponent(this.properties);
                }
            }
        }
        if (t != null) {
            return t;
        }
        if (isExperimentalName(this.name)) {
            return new XComponent(this.name, this.properties);
        }
        if (allowIllegalNames()) {
            return new XComponent(this.name, this.properties);
        }
        throw new IllegalArgumentException("Unsupported component [" + this.name + "]");
    }
}
