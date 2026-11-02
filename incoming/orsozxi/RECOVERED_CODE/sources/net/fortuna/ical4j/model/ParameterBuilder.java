package net.fortuna.ical4j.model;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.fortuna.ical4j.model.parameter.XParameter;
import net.fortuna.ical4j.util.Strings;

/* loaded from: classes3.dex */
public class ParameterBuilder extends AbstractContentBuilder {
    private List<ParameterFactory> factories = new ArrayList();
    private String name;
    private String value;

    public ParameterBuilder factories(List<ParameterFactory> list) {
        this.factories.addAll(list);
        return this;
    }

    public ParameterBuilder name(String str) {
        this.name = str.toUpperCase();
        return this;
    }

    public ParameterBuilder value(String str) {
        this.value = Strings.escapeNewline(str);
        return this;
    }

    public Parameter build() throws URISyntaxException {
        Parameter parameterCreateParameter;
        Iterator<ParameterFactory> it2 = this.factories.iterator();
        while (true) {
            if (!it2.hasNext()) {
                parameterCreateParameter = null;
                break;
            }
            ParameterFactory next = it2.next();
            if (next.supports(this.name)) {
                parameterCreateParameter = next.createParameter(this.value);
                break;
            }
        }
        if (parameterCreateParameter != null) {
            return parameterCreateParameter;
        }
        if (isExperimentalName(this.name)) {
            return new XParameter(this.name, this.value);
        }
        if (allowIllegalNames()) {
            return new XParameter(this.name, this.value);
        }
        throw new IllegalArgumentException(String.format("Unsupported parameter name: %s", this.name));
    }
}
