package net.fortuna.ical4j.model;

import java.util.Comparator;
import java.util.Optional;
import net.fortuna.ical4j.model.property.DtStamp;
import net.fortuna.ical4j.model.property.Sequence;

/* loaded from: classes3.dex */
public class ComponentSequenceComparator implements Comparator<Component> {
    @Override // java.util.Comparator
    public int compare(Component component, Component component2) {
        Sequence sequence = new Sequence(0);
        int iCompareTo = ((Sequence) Optional.ofNullable((Sequence) component.getProperty(Property.SEQUENCE)).orElse(sequence)).compareTo((Sequence) Optional.ofNullable((Sequence) component2.getProperty(Property.SEQUENCE)).orElse(sequence));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        DtStamp dtStamp = new DtStamp(new DateTime(0L));
        return ((DtStamp) Optional.ofNullable((DtStamp) component.getProperty(Property.DTSTAMP)).orElse(dtStamp)).compareTo((DtStamp) Optional.ofNullable((DtStamp) component2.getProperty(Property.DTSTAMP)).orElse(dtStamp));
    }
}
