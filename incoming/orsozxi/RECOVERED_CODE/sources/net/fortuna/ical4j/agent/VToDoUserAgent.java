package net.fortuna.ical4j.agent;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VToDo;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.transform.RequestTransformer;
import net.fortuna.ical4j.util.UidGenerator;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class VToDoUserAgent extends AbstractUserAgent<VToDo> {
    private final RequestTransformer delegateTransformer;

    public VToDoUserAgent(ProdId prodId, Organizer organizer, UidGenerator uidGenerator) {
        super(prodId, organizer, uidGenerator);
        this.delegateTransformer = new RequestTransformer(uidGenerator);
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar publish(VToDo... vToDoArr) throws ValidationException {
        Calendar calendarWrap = wrap(Method.PUBLISH, vToDoArr);
        calendarWrap.validate();
        return calendarWrap;
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar request(VToDo... vToDoArr) throws ValidationException {
        Calendar calendarWrap = wrap(Method.REQUEST, vToDoArr);
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
    public Calendar add(VToDo vToDo) throws ValidationException {
        Calendar calendarWrap = wrap(Method.ADD, vToDo);
        calendarWrap.validate();
        return calendarWrap;
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar cancel(VToDo... vToDoArr) throws ValidationException {
        Calendar calendarWrap = wrap(Method.CANCEL, vToDoArr);
        calendarWrap.validate();
        return calendarWrap;
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar refresh(VToDo vToDo) throws ValidationException {
        Calendar calendarWrap = wrap(Method.REFRESH, vToDo);
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
