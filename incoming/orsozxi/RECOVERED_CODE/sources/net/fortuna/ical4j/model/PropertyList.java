package net.fortuna.ical4j.model;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.fortuna.ical4j.model.Property;

/* loaded from: classes3.dex */
public class PropertyList<T extends Property> extends ArrayList<T> implements Serializable {
    private static final long serialVersionUID = -8875923766224921031L;

    public PropertyList() {
    }

    public PropertyList(int i) {
        super(i);
    }

    public PropertyList(PropertyList<? extends T> propertyList) throws URISyntaxException, IOException, ParseException {
        Iterator<? extends T> it2 = propertyList.iterator();
        while (it2.hasNext()) {
            add((PropertyList<T>) it2.next().copy());
        }
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        return (String) stream().map(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$PrxStJ05Qppwk4q-mBTJV4hnwQU
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Property) obj).toString();
            }
        }).collect(Collectors.joining(""));
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [R, net.fortuna.ical4j.model.Property] */
    public final <R> R getProperty(String str) {
        Iterator<T> it2 = iterator();
        while (it2.hasNext()) {
            T next = it2.next();
            if (next.getName().equalsIgnoreCase(str)) {
                return next;
            }
        }
        return null;
    }

    public final <C extends T> PropertyList<C> getProperties(String str) {
        PropertyList<C> propertyList = new PropertyList<>();
        Iterator<T> it2 = iterator();
        while (it2.hasNext()) {
            T next = it2.next();
            if (next.getName().equalsIgnoreCase(str)) {
                propertyList.add((PropertyList<C>) next);
            }
        }
        return propertyList;
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(T t) {
        return super.add((PropertyList<T>) t);
    }

    public final boolean remove(Property property) {
        return super.remove((Object) property);
    }
}
