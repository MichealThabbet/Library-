package net.fortuna.ical4j.model;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.fortuna.ical4j.model.Component;

/* loaded from: classes3.dex */
public class ComponentList<T extends Component> extends ArrayList<T> implements Serializable {
    private static final long serialVersionUID = 7308557606558767449L;

    public ComponentList() {
    }

    public ComponentList(int i) {
        super(i);
    }

    public ComponentList(ComponentList<? extends T> componentList) throws URISyntaxException, IOException, ParseException {
        Iterator<? extends T> it2 = componentList.iterator();
        while (it2.hasNext()) {
            add(it2.next().copy());
        }
    }

    public ComponentList(List<? extends T> list) {
        addAll(list);
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        return (String) stream().map(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$5X4vsF_Sopjf52kOxTiOWRQcqiQ
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Component) obj).toString();
            }
        }).collect(Collectors.joining(""));
    }

    public final T getComponent(String str) {
        Iterator<T> it2 = iterator();
        while (it2.hasNext()) {
            T next = it2.next();
            if (next.getName().equals(str)) {
                return next;
            }
        }
        return null;
    }

    public final <C extends T> ComponentList<C> getComponents(String str) {
        ComponentList<C> componentList = new ComponentList<>();
        Iterator<T> it2 = iterator();
        while (it2.hasNext()) {
            T next = it2.next();
            if (next.getName().equals(str)) {
                componentList.add(next);
            }
        }
        return componentList;
    }
}
