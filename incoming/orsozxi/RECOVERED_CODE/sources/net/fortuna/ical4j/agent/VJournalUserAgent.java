package net.fortuna.ical4j.agent;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VJournal;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.util.UidGenerator;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class VJournalUserAgent extends AbstractUserAgent<VJournal> {
    public VJournalUserAgent(ProdId prodId, Organizer organizer, UidGenerator uidGenerator) {
        super(prodId, organizer, uidGenerator);
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar publish(VJournal... vJournalArr) throws ValidationException {
        Calendar calendarWrap = wrap(Method.PUBLISH, vJournalArr);
        calendarWrap.validate();
        return calendarWrap;
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar request(VJournal... vJournalArr) {
        throw new UnsupportedOperationException("Method [REQUEST] not supported by VJOURNAL");
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar delegate(Calendar calendar) {
        throw new UnsupportedOperationException("REQUEST delegation not supported by VJOURNAL");
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar reply(Calendar calendar) {
        throw new UnsupportedOperationException("Method [REPLY] not supported by VJOURNAL");
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar add(VJournal vJournal) throws ValidationException {
        Calendar calendarWrap = wrap(Method.ADD, vJournal);
        calendarWrap.validate();
        return calendarWrap;
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar cancel(VJournal... vJournalArr) throws ValidationException {
        Calendar calendarWrap = wrap(Method.CANCEL, vJournalArr);
        calendarWrap.validate();
        return calendarWrap;
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar refresh(VJournal vJournal) {
        throw new UnsupportedOperationException("Method [REFRESH] not supported by VJOURNAL");
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar counter(Calendar calendar) {
        throw new UnsupportedOperationException("Method [COUNTER] not supported by VJOURNAL");
    }

    @Override // net.fortuna.ical4j.agent.UserAgent
    public Calendar declineCounter(Calendar calendar) {
        throw new UnsupportedOperationException("Method [DECLINECOUNTER] not supported by VJOURNAL");
    }
}
