package net.fortuna.ical4j.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import net.fortuna.ical4j.model.property.XProperty;
import net.fortuna.ical4j.util.Strings;

/* loaded from: classes3.dex */
public class PropertyBuilder extends AbstractContentBuilder {
    private String name;
    private String value;
    private List<PropertyFactory> factories = new ArrayList();
    private ParameterList parameters = new ParameterList();

    public PropertyBuilder factories(List<PropertyFactory> list) {
        this.factories.addAll(list);
        return this;
    }

    public PropertyBuilder name(String str) {
        this.name = str.toUpperCase();
        return this;
    }

    public PropertyBuilder value(String str) {
        this.value = str.trim();
        return this;
    }

    public PropertyBuilder parameter(Parameter parameter) {
        this.parameters.add(parameter);
        return this;
    }

    public Property build() throws URISyntaxException, IOException, ParseException {
        Property propertyCreateProperty = null;
        for (PropertyFactory propertyFactory : this.factories) {
            if (propertyFactory.supports(this.name)) {
                propertyCreateProperty = propertyFactory.createProperty(this.parameters, this.value);
                if (propertyCreateProperty instanceof Escapable) {
                    propertyCreateProperty.setValue(Strings.unescape(this.value));
                }
            }
        }
        if (propertyCreateProperty != null) {
            return propertyCreateProperty;
        }
        if (isExperimentalName(this.name)) {
            return new XProperty(this.name, this.parameters, this.value);
        }
        if (allowIllegalNames()) {
            return new XProperty(this.name, this.parameters, this.value);
        }
        throw new IllegalArgumentException("Illegal property [" + this.name + "]");
    }
}
