package net.fortuna.ical4j.transform;

import java.util.Iterator;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.transform.command.OrganizerUpdate;
import net.fortuna.ical4j.util.UidGenerator;

/* loaded from: classes3.dex */
public class AddTransformer extends AbstractMethodTransformer {
    private final OrganizerUpdate organizerUpdate;

    public AddTransformer(Organizer organizer, UidGenerator uidGenerator) {
        super(Method.ADD, uidGenerator, true, true);
        this.organizerUpdate = new OrganizerUpdate(organizer);
    }

    @Override // net.fortuna.ical4j.transform.AbstractMethodTransformer, net.fortuna.ical4j.transform.Transformer
    public Calendar transform(Calendar calendar) {
        Iterator<CalendarComponent> it2 = calendar.getComponents().iterator();
        while (it2.hasNext()) {
            this.organizerUpdate.transform((Component) it2.next());
        }
        return super.transform(calendar);
    }
}
