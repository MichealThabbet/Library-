package net.fortuna.ical4j.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.fortuna.ical4j.model.Component;

/* loaded from: classes3.dex */
public class IndexedComponentList<T extends Component> {
    private final ComponentList<T> EMPTY_LIST = new ComponentList<>();
    private Map<String, ComponentList<T>> index;

    public IndexedComponentList(ComponentList<T> componentList, String str) {
        HashMap map = new HashMap();
        Iterator<T> it2 = componentList.iterator();
        while (it2.hasNext()) {
            T next = it2.next();
            Iterator<T> it3 = next.getProperties(str).iterator();
            while (it3.hasNext()) {
                Property property = (Property) it3.next();
                ComponentList componentList2 = (ComponentList) map.get(property.getValue());
                if (componentList2 == null) {
                    componentList2 = new ComponentList();
                    map.put(property.getValue(), componentList2);
                }
                componentList2.add(next);
            }
        }
        this.index = Collections.unmodifiableMap(map);
    }

    public ComponentList<T> getComponents(String str) {
        ComponentList<T> componentList = this.index.get(str);
        return componentList == null ? this.EMPTY_LIST : componentList;
    }

    public T getComponent(String str) {
        ComponentList<T> components = getComponents(str);
        if (components.isEmpty()) {
            return null;
        }
        return components.get(0);
    }
}
