package net.fortuna.ical4j.agent;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.transform.RequestTransformer;
import net.fortuna.ical4j.util.UidGenerator;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class VEventUserAgent extends AbstractUserAgent<VEvent> {
    private final RequestTransformer delegateTransformer;

    public VEventUserAgent(ProdId prodId, Organizer organizer, UidGenerator uidGenerator) {
        super(prodId, organizer, uidGenerator);
        this.delegateTransformer = new RequestTransformer(uidGenerator);
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar publish(VEvent... vEventArr) throws ValidationException {
        Calendar calendarWrap = wrap(Method.PUBLISH, vEventArr);
        calendarWrap.validate();
        return calendarWrap;
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar request(VEvent... vEventArr) throws ValidationException {
        Calendar calendarWrap = wrap(Method.REQUEST, vEventArr);
        calendarWrap.validate();
        return calendarWrap;
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar delegate(Calendar calendar) throws ValidationException {
        Calendar calendarTransform = this.delegateTransformer.transform(calendar);
        calendarTransform.validate();
        return calendarTransform;
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar reply(Calendar calendar) throws ValidationException {
        Calendar calendarTransform = transform(Method.REPLY, calendar);
        calendarTransform.validate();
        return calendarTransform;
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar add(VEvent vEvent) throws ValidationException {
        Calendar calendarWrap = wrap(Method.ADD, vEvent);
        calendarWrap.validate();
        return calendarWrap;
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar cancel(VEvent... vEventArr) throws ValidationException {
        Calendar calendarWrap = wrap(Method.CANCEL, vEventArr);
        calendarWrap.validate();
        return calendarWrap;
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar refresh(VEvent vEvent) throws ValidationException {
        Calendar calendarWrap = wrap(Method.REFRESH, vEvent);
        calendarWrap.validate();
        return calendarWrap;
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar counter(Calendar calendar) throws ValidationException {
        Calendar calendarTransform = transform(Method.COUNTER, calendar);
        calendarTransform.validate();
        return calendarTransform;
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar declineCounter(Calendar calendar) throws ValidationException {
        Calendar calendarTransform = transform(Method.DECLINE_COUNTER, calendar);
        calendarTransform.validate();
        return calendarTransform;
    }
}
