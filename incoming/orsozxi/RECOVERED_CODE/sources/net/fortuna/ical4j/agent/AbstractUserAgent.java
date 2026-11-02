package net.fortuna.ical4j.agent;

import java.util.HashMap;
import java.util.Map;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.transform.AddTransformer;
import net.fortuna.ical4j.transform.CancelTransformer;
import net.fortuna.ical4j.transform.CounterTransformer;
import net.fortuna.ical4j.transform.DeclineCounterTransformer;
import net.fortuna.ical4j.transform.PublishTransformer;
import net.fortuna.ical4j.transform.RefreshTransformer;
import net.fortuna.ical4j.transform.ReplyTransformer;
import net.fortuna.ical4j.transform.RequestTransformer;
import net.fortuna.ical4j.transform.Transformer;
import net.fortuna.ical4j.util.Calendars;
import net.fortuna.ical4j.util.UidGenerator;

/* loaded from: classes3.dex */
public abstract class AbstractUserAgent<T extends CalendarComponent> implements UserAgent<T> {
    private final ProdId prodId;
    private final Map<Method, Transformer<Calendar>> transformers;

    public AbstractUserAgent(ProdId prodId, Organizer organizer, UidGenerator uidGenerator) {
        this.prodId = prodId;
        HashMap map = new HashMap();
        this.transformers = map;
        map.put(Method.PUBLISH, new PublishTransformer(organizer, uidGenerator, true));
        this.transformers.put(Method.REQUEST, new RequestTransformer(organizer, uidGenerator));
        this.transformers.put(Method.ADD, new AddTransformer(organizer, uidGenerator));
        this.transformers.put(Method.CANCEL, new CancelTransformer(organizer, uidGenerator));
        this.transformers.put(Method.REPLY, new ReplyTransformer(uidGenerator));
        this.transformers.put(Method.REFRESH, new RefreshTransformer(uidGenerator));
        this.transformers.put(Method.COUNTER, new CounterTransformer(uidGenerator));
        this.transformers.put(Method.DECLINE_COUNTER, new DeclineCounterTransformer(organizer, uidGenerator));
    }

    @SafeVarargs
    protected final Calendar wrap(Method method, T... tArr) {
        Calendar calendarWrap = Calendars.wrap(tArr);
        calendarWrap.getProperties().add((PropertyList<Property>) this.prodId);
        calendarWrap.getProperties().add((PropertyList<Property>) Version.VERSION_2_0);
        return transform(method, calendarWrap);
    }

    protected Calendar transform(Method method, Calendar calendar) {
        this.transformers.get(method).transform(calendar);
        return calendar;
    }

    public ProdId getProdId() {
        return this.prodId;
    }
}
