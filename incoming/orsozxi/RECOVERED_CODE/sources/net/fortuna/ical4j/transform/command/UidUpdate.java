package net.fortuna.ical4j.transform.command;

import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.transform.Transformer;
import net.fortuna.ical4j.util.UidGenerator;

/* loaded from: classes3.dex */
public class UidUpdate implements Transformer<Component> {
    private final UidGenerator uidGenerator;

    public UidUpdate(UidGenerator uidGenerator) {
        this.uidGenerator = uidGenerator;
    }

    @Override // net.fortuna.ical4j.transform.Transformer
    public Component transform(Component component) {
        if (((Uid) component.getProperties().getProperty(Property.UID)) == null) {
            component.getProperties().add((PropertyList<Property>) this.uidGenerator.generateUid());
        }
        return component;
    }
}
