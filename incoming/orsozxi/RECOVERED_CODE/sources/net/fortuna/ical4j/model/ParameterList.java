package net.fortuna.ical4j.model;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/* loaded from: classes3.dex */
public class ParameterList implements Serializable, Iterable<Parameter> {
    private static final long serialVersionUID = -1913059830016450169L;
    private final List<Parameter> parameters;

    public ParameterList() {
        this(false);
    }

    public ParameterList(boolean z) {
        if (z) {
            this.parameters = Collections.emptyList();
        } else {
            this.parameters = new CopyOnWriteArrayList();
        }
    }

    public ParameterList(ParameterList parameterList, boolean z) {
        final CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        parameterList.forEach(new Consumer() { // from class: net.fortuna.ical4j.model.-$$Lambda$ParameterList$hUXE8Xr5AcpHJTi5y_GXMXpRbJM
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ParameterList.lambda$new$0(copyOnWriteArrayList, (Parameter) obj);
            }
        });
        if (z) {
            this.parameters = Collections.unmodifiableList(copyOnWriteArrayList);
        } else {
            this.parameters = copyOnWriteArrayList;
        }
    }

    static /* synthetic */ void lambda$new$0(List list, Parameter parameter) {
        try {
            list.add(parameter.copy());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public final String toString() {
        return !this.parameters.isEmpty() ? (String) this.parameters.stream().map(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$lcilh5p_WmWsluPgTUCjnYlNx-U
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Parameter) obj).toString();
            }
        }).collect(Collectors.joining(";", ";", "")) : "";
    }

    public final <T extends Parameter> T getParameter(String str) {
        Iterator<Parameter> it2 = this.parameters.iterator();
        while (it2.hasNext()) {
            T t = (T) it2.next();
            if (str.equalsIgnoreCase(t.getName())) {
                return t;
            }
        }
        return null;
    }

    public final ParameterList getParameters(String str) {
        ParameterList parameterList = new ParameterList();
        for (Parameter parameter : this.parameters) {
            if (parameter.getName().equalsIgnoreCase(str)) {
                parameterList.add(parameter);
            }
        }
        return parameterList;
    }

    public final boolean add(Parameter parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException("Trying to add null Parameter");
        }
        return this.parameters.add(parameter);
    }

    public final boolean replace(Parameter parameter) {
        Iterator<Parameter> it2 = getParameters(parameter.getName()).iterator();
        while (it2.hasNext()) {
            remove(it2.next());
        }
        return add(parameter);
    }

    public final boolean isEmpty() {
        return this.parameters.isEmpty();
    }

    @Override // java.lang.Iterable
    public final Iterator<Parameter> iterator() {
        return this.parameters.iterator();
    }

    public final boolean remove(Parameter parameter) {
        return this.parameters.remove(parameter);
    }

    public final void removeAll(String str) {
        this.parameters.removeAll(getParameters(str).parameters);
    }

    public final int size() {
        return this.parameters.size();
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ParameterList) {
            return Objects.equals(this.parameters, ((ParameterList) obj).parameters);
        }
        return super.equals(obj);
    }

    public final int hashCode() {
        return new HashCodeBuilder().append(this.parameters).toHashCode();
    }
}
