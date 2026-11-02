package net.fortuna.ical4j.agent;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VFreeBusy;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.util.UidGenerator;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class VFreeBusyUserAgent extends AbstractUserAgent<VFreeBusy> {
    public VFreeBusyUserAgent(ProdId prodId, Organizer organizer, UidGenerator uidGenerator) {
        super(prodId, organizer, uidGenerator);
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar publish(VFreeBusy... vFreeBusyArr) throws ValidationException {
        Calendar calendarWrap = wrap(Method.PUBLISH, vFreeBusyArr);
        calendarWrap.validate();
        return calendarWrap;
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar request(VFreeBusy... vFreeBusyArr) throws ValidationException {
        Calendar calendarWrap = wrap(Method.REQUEST, vFreeBusyArr);
        calendarWrap.validate();
        return calendarWrap;
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar delegate(Calendar calendar) {
        throw new UnsupportedOperationException("REQUEST delegation not supported by VFREEBUSY");
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar reply(Calendar calendar) throws ValidationException {
        Calendar calendarTransform = transform(Method.REPLY, calendar);
        calendarTransform.validate();
        return calendarTransform;
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar add(VFreeBusy vFreeBusy) {
        throw new UnsupportedOperationException("Method [ADD] not supported by VFREEBUSY");
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar cancel(VFreeBusy... vFreeBusyArr) {
        throw new UnsupportedOperationException("Method [CANCEL] not supported by VFREEBUSY");
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar refresh(VFreeBusy vFreeBusy) {
        throw new UnsupportedOperationException("Method [REFRESH] not supported by VFREEBUSY");
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar counter(Calendar calendar) {
        throw new UnsupportedOperationException("Method [COUNTER] not supported by VFREEBUSY");
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar declineCounter(Calendar calendar) {
        throw new UnsupportedOperationException("Method [DECLINECOUNTER] not supported by VFREEBUSY");
    }
}
