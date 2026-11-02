package net.fortuna.ical4j.model;

import net.fortuna.ical4j.model.Component;

/* loaded from: classes3.dex */
public interface ComponentFactory<T extends Component> {
    T createComponent();

    T createComponent(PropertyList propertyList);

    T createComponent(PropertyList propertyList, ComponentList componentList);

    boolean supports(String str);
}
