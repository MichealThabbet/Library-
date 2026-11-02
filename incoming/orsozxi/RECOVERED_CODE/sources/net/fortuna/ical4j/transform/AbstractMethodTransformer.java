package net.fortuna.ical4j.transform;

import java.util.Iterator;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.transform.command.MethodUpdate;
import net.fortuna.ical4j.transform.command.SequenceIncrement;
import net.fortuna.ical4j.transform.command.UidUpdate;
import net.fortuna.ical4j.util.UidGenerator;

/* loaded from: classes3.dex */
public abstract class AbstractMethodTransformer implements Transformer<Calendar> {
    private final boolean incrementSequence;
    private final Method method;
    private final boolean sameUid;
    private final SequenceIncrement sequenceIncrement = new SequenceIncrement();
    private final UidUpdate uidUpdate;

    public AbstractMethodTransformer(Method method, UidGenerator uidGenerator, boolean z, boolean z2) {
        this.method = method;
        this.uidUpdate = new UidUpdate(uidGenerator);
        this.incrementSequence = z2;
        this.sameUid = z;
    }

    @Override // net.fortuna.ical4j.transform.Transformer
    public Calendar transform(Calendar calendar) {
        new MethodUpdate(this.method).transform(calendar);
        Iterator<CalendarComponent> it2 = calendar.getComponents().iterator();
        Property property = null;
        while (it2.hasNext()) {
            CalendarComponent next = it2.next();
            this.uidUpdate.transform((Component) next);
            if (property == null) {
                property = next.getProperty(Property.UID);
            } else if (this.sameUid && !property.equals(next.getProperty(Property.UID))) {
                throw new IllegalArgumentException("All components must share the same non-null UID");
            }
            if (this.incrementSequence) {
                this.sequenceIncrement.transform(next);
            }
        }
        return calendar;
    }
}
