package net.fortuna.ical4j.model.component;

import java.time.temporal.TemporalAmount;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentFactory;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateRange;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.PeriodList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.TemporalAmountComparator;
import net.fortuna.ical4j.model.component.VFreeBusy;
import net.fortuna.ical4j.model.parameter.FbType;
import net.fortuna.ical4j.model.property.Contact;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStamp;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Duration;
import net.fortuna.ical4j.model.property.FreeBusy;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Url;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.validate.ComponentValidator;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;

/* loaded from: classes3.dex */
public class VFreeBusy extends CalendarComponent {
    private static final long serialVersionUID = 1046534053331139832L;
    private final Map<Method, Validator> methodValidators;

    public VFreeBusy() {
        this(true);
    }

    public VFreeBusy(boolean z) {
        super(Component.VFREEBUSY);
        HashMap map = new HashMap();
        this.methodValidators = map;
        map.put(Method.PUBLISH, new ComponentValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrMore, Property.FREEBUSY), new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.DTSTART, Property.DTEND, Property.ORGANIZER, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.ATTENDEE, Property.DURATION, Property.REQUEST_STATUS))));
        this.methodValidators.put(Method.REPLY, new ComponentValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.ATTENDEE, Property.DTSTAMP, Property.DTEND, Property.DTSTART, Property.ORGANIZER, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.DURATION, Property.SEQUENCE))));
        this.methodValidators.put(Method.REQUEST, new ComponentValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrMore, Property.ATTENDEE), new ValidationRule(ValidationRule.ValidationType.One, Property.DTEND, Property.DTSTAMP, Property.DTSTART, Property.ORGANIZER, Property.UID), new ValidationRule(ValidationRule.ValidationType.None, Property.FREEBUSY, Property.DURATION, Property.REQUEST_STATUS, Property.URL))));
        if (z) {
            getProperties().add((PropertyList<Property>) new DtStamp());
        }
    }

    public VFreeBusy(PropertyList propertyList) {
        super(Component.VFREEBUSY, propertyList);
        HashMap map = new HashMap();
        this.methodValidators = map;
        map.put(Method.PUBLISH, new ComponentValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrMore, Property.FREEBUSY), new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTAMP, Property.DTSTART, Property.DTEND, Property.ORGANIZER, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.ATTENDEE, Property.DURATION, Property.REQUEST_STATUS))));
        this.methodValidators.put(Method.REPLY, new ComponentValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.ATTENDEE, Property.DTSTAMP, Property.DTEND, Property.DTSTART, Property.ORGANIZER, Property.UID), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.URL), new ValidationRule(ValidationRule.ValidationType.None, Property.DURATION, Property.SEQUENCE))));
        this.methodValidators.put(Method.REQUEST, new ComponentValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.OneOrMore, Property.ATTENDEE), new ValidationRule(ValidationRule.ValidationType.One, Property.DTEND, Property.DTSTAMP, Property.DTSTART, Property.ORGANIZER, Property.UID), new ValidationRule(ValidationRule.ValidationType.None, Property.FREEBUSY, Property.DURATION, Property.REQUEST_STATUS, Property.URL))));
    }

    public VFreeBusy(DateTime dateTime, DateTime dateTime2) {
        this();
        getProperties().add((PropertyList<Property>) new DtStart((Date) dateTime, true));
        getProperties().add((PropertyList<Property>) new DtEnd((Date) dateTime2, true));
    }

    public VFreeBusy(DateTime dateTime, DateTime dateTime2, TemporalAmount temporalAmount) {
        this();
        getProperties().add((PropertyList<Property>) new DtStart((Date) dateTime, true));
        getProperties().add((PropertyList<Property>) new DtEnd((Date) dateTime2, true));
        getProperties().add((PropertyList<Property>) new Duration(temporalAmount));
    }

    public VFreeBusy(VFreeBusy vFreeBusy, ComponentList<CalendarComponent> componentList) {
        this();
        DtStart dtStart = (DtStart) vFreeBusy.getProperty(Property.DTSTART);
        DtEnd dtEnd = (DtEnd) vFreeBusy.getProperty(Property.DTEND);
        Duration duration = (Duration) vFreeBusy.getProperty(Property.DURATION);
        getProperties().add((PropertyList<Property>) new DtStart(dtStart.getDate(), true));
        getProperties().add((PropertyList<Property>) new DtEnd(dtEnd.getDate(), true));
        if (duration != null) {
            getProperties().add((PropertyList<Property>) new Duration(duration.getDuration()));
            DateTime dateTime = new DateTime(dtStart.getDate());
            FreeBusy freeBusyBuild = new FreeTimeBuilder().start(dateTime).end(new DateTime(dtEnd.getDate())).duration(duration.getDuration()).components(componentList).build();
            if (freeBusyBuild == null || freeBusyBuild.getPeriods().isEmpty()) {
                return;
            }
            getProperties().add((PropertyList<Property>) freeBusyBuild);
            return;
        }
        DateTime dateTime2 = new DateTime(dtStart.getDate());
        FreeBusy freeBusyBuild2 = new BusyTimeBuilder().start(dateTime2).end(new DateTime(dtEnd.getDate())).components(componentList).build();
        if (freeBusyBuild2 == null || freeBusyBuild2.getPeriods().isEmpty()) {
            return;
        }
        getProperties().add((PropertyList<Property>) freeBusyBuild2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    class BusyTimeBuilder {
        private ComponentList<CalendarComponent> components;
        private DateTime end;
        private DateTime start;

        private BusyTimeBuilder() {
        }

        public BusyTimeBuilder start(DateTime dateTime) {
            this.start = dateTime;
            return this;
        }

        public BusyTimeBuilder end(DateTime dateTime) {
            this.end = dateTime;
            return this;
        }

        public BusyTimeBuilder components(ComponentList<CalendarComponent> componentList) {
            this.components = componentList;
            return this;
        }

        public FreeBusy build() {
            PeriodList consumedTime = VFreeBusy.this.getConsumedTime(this.components, this.start, this.end);
            final DateRange dateRange = new DateRange(this.start, this.end);
            consumedTime.setUtc(true);
            consumedTime.removeIf(new Predicate() { // from class: net.fortuna.ical4j.model.component.-$$Lambda$VFreeBusy$BusyTimeBuilder$Vq7N83AZ1srxLuKpVgh_bDEJ_Ms
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return VFreeBusy.BusyTimeBuilder.lambda$build$0(dateRange, (Period) obj);
                }
            });
            return new FreeBusy(consumedTime);
        }

        static /* synthetic */ boolean lambda$build$0(DateRange dateRange, Period period) {
            return !dateRange.intersects(period);
        }
    }

    private class FreeTimeBuilder {
        private ComponentList<CalendarComponent> components;
        private TemporalAmount duration;
        private DateTime end;
        private DateTime start;

        private FreeTimeBuilder() {
        }

        public FreeTimeBuilder start(DateTime dateTime) {
            this.start = dateTime;
            return this;
        }

        public FreeTimeBuilder end(DateTime dateTime) {
            this.end = dateTime;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public FreeTimeBuilder duration(TemporalAmount temporalAmount) {
            this.duration = temporalAmount;
            return this;
        }

        public FreeTimeBuilder components(ComponentList<CalendarComponent> componentList) {
            this.components = componentList;
            return this;
        }

        public FreeBusy build() {
            FreeBusy freeBusy = new FreeBusy();
            freeBusy.getParameters().add(FbType.FREE);
            PeriodList consumedTime = VFreeBusy.this.getConsumedTime(this.components, this.start, this.end);
            DateRange dateRange = new DateRange(this.start, this.end);
            DateTime dateTime = this.end;
            consumedTime.add(new Period(dateTime, dateTime));
            DateTime dateTime2 = new DateTime(this.start);
            Iterator<Period> it2 = consumedTime.iterator();
            while (it2.hasNext()) {
                Period next = it2.next();
                if (dateRange.contains(next) || (dateRange.intersects(next) && next.getStart().after(dateRange.getRangeStart()))) {
                    Duration duration = new Duration(dateTime2, next.getStart());
                    if (new TemporalAmountComparator().compare(duration.getDuration(), this.duration) >= 0) {
                        freeBusy.getPeriods().add(new Period(dateTime2, duration.getDuration()));
                    }
                }
                if (next.getEnd().after(dateTime2)) {
                    dateTime2 = next.getEnd();
                }
            }
            return freeBusy;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PeriodList getConsumedTime(ComponentList<CalendarComponent> componentList, DateTime dateTime, DateTime dateTime2) {
        PeriodList periodList = new PeriodList();
        Iterator it2 = componentList.getComponents(Component.VEVENT).iterator();
        while (it2.hasNext()) {
            periodList.addAll(((VEvent) ((Component) it2.next())).getConsumedTime(dateTime, dateTime2, false));
        }
        return periodList.normalise();
    }

    @Override // net.fortuna.ical4j.model.Component
    public final void validate(boolean z) throws ValidationException {
        if (!CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION)) {
            PropertyValidator.assertOne(Property.UID, getProperties());
            PropertyValidator.assertOne(Property.DTSTAMP, getProperties());
        }
        Arrays.asList(Property.CONTACT, Property.DTSTART, Property.DTEND, Property.DURATION, Property.DTSTAMP, Property.ORGANIZER, Property.UID, Property.URL).forEach(new Consumer() { // from class: net.fortuna.ical4j.model.component.-$$Lambda$VFreeBusy$SI2BnTOQaRC6C_0JkG7DwP4N0SI
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws ValidationException {
                this.f$0.lambda$validate$0$VFreeBusy((String) obj);
            }
        });
        Arrays.asList(Property.RRULE, Property.EXRULE, Property.RDATE, Property.EXDATE).forEach(new Consumer() { // from class: net.fortuna.ical4j.model.component.-$$Lambda$VFreeBusy$LBkv6Ok5Hd2iJIu_kLjAnBPs7h8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws ValidationException {
                this.f$0.lambda$validate$1$VFreeBusy((String) obj);
            }
        });
        DtStart dtStart = (DtStart) getProperty(Property.DTSTART);
        if (dtStart != null && !dtStart.isUtc()) {
            throw new ValidationException("DTSTART must be specified in UTC time");
        }
        DtEnd dtEnd = (DtEnd) getProperty(Property.DTEND);
        if (dtEnd != null && !dtEnd.isUtc()) {
            throw new ValidationException("DTEND must be specified in UTC time");
        }
        if (dtStart != null && dtEnd != null && !dtStart.getDate().before(dtEnd.getDate())) {
            throw new ValidationException("Property [DTEND] must be later in time than [DTSTART]");
        }
        if (z) {
            validateProperties();
        }
    }

    public /* synthetic */ void lambda$validate$0$VFreeBusy(String str) throws ValidationException {
        PropertyValidator.assertOneOrLess(str, getProperties());
    }

    public /* synthetic */ void lambda$validate$1$VFreeBusy(String str) throws ValidationException {
        PropertyValidator.assertNone(str, getProperties());
    }

    @Override // net.fortuna.ical4j.model.component.CalendarComponent
    protected Validator getValidator(Method method) {
        return this.methodValidators.get(method);
    }

    public final Contact getContact() {
        return (Contact) getProperty(Property.CONTACT);
    }

    public final DtStart getStartDate() {
        return (DtStart) getProperty(Property.DTSTART);
    }

    public final DtEnd getEndDate() {
        return (DtEnd) getProperty(Property.DTEND);
    }

    public final Duration getDuration() {
        return (Duration) getProperty(Property.DURATION);
    }

    public final DtStamp getDateStamp() {
        return (DtStamp) getProperty(Property.DTSTAMP);
    }

    public final Organizer getOrganizer() {
        return (Organizer) getProperty(Property.ORGANIZER);
    }

    public final Url getUrl() {
        return (Url) getProperty(Property.URL);
    }

    public final Uid getUid() {
        return (Uid) getProperty(Property.UID);
    }

    public static class Factory extends Content.Factory implements ComponentFactory<VFreeBusy> {
        public Factory() {
            super(Component.VFREEBUSY);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VFreeBusy createComponent() {
            return new VFreeBusy(false);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VFreeBusy createComponent(PropertyList propertyList) {
            return new VFreeBusy(propertyList);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VFreeBusy createComponent(PropertyList propertyList, ComponentList componentList) {
            throw new UnsupportedOperationException(String.format("%s does not support sub-components", Component.VFREEBUSY));
        }
    }
}
