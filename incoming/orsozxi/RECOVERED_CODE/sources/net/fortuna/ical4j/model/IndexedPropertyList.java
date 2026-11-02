package net.fortuna.ical4j.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class IndexedPropertyList {
    private static final PropertyList<Property> EMPTY_LIST = new PropertyList<>();
    private Map<String, PropertyList<Property>> index;

    public IndexedPropertyList(PropertyList<Property> propertyList, final String str) {
        final HashMap map = new HashMap();
        propertyList.forEach(new Consumer() { // from class: net.fortuna.ical4j.model.-$$Lambda$IndexedPropertyList$wBSgOYvOegsd0jUBj3LQNutUt-c
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Property property = (Property) obj;
                property.getParameters(str).forEach(new Consumer() { // from class: net.fortuna.ical4j.model.-$$Lambda$IndexedPropertyList$tGXiO0WomcVWJelKaEJ9aNYc5nc
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj2) {
                        IndexedPropertyList.lambda$new$0(map, property, (Parameter) obj2);
                    }
                });
            }
        });
        this.index = Collections.unmodifiableMap(map);
    }

    static /* synthetic */ void lambda$new$0(Map map, Property property, Parameter parameter) {
        PropertyList propertyList = (PropertyList) map.get(parameter.getValue());
        if (propertyList == null) {
            propertyList = new PropertyList();
            map.put(parameter.getValue(), propertyList);
        }
        propertyList.add((PropertyList) property);
    }

    public PropertyList<Property> getProperties(String str) {
        PropertyList<Property> propertyList = this.index.get(str);
        return propertyList == null ? EMPTY_LIST : propertyList;
    }

    public Property getProperty(String str) {
        PropertyList<Property> properties = getProperties(str);
        if (properties.isEmpty()) {
            return null;
        }
        return properties.iterator().next();
    }
}
