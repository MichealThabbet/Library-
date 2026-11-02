package net.fortuna.ical4j.transform;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.util.UidGenerator;

/* loaded from: classes3.dex */
public class CounterTransformer extends AbstractMethodTransformer {
    public CounterTransformer(UidGenerator uidGenerator) {
        super(Method.COUNTER, uidGenerator, true, false);
    }

    @Override // net.fortuna.ical4j.transform.AbstractMethodTransformer, net.fortuna.ical4j.transform.Transformer
    public Calendar transform(Calendar calendar) {
        if (!Method.REQUEST.equals(calendar.getMethod())) {
            throw new IllegalArgumentException("Expecting REQUEST method in source");
        }
        return super.transform(calendar);
    }
}
