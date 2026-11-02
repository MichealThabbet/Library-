package net.fortuna.ical4j.model;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.temporal.TemporalAmount;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.DateProperty;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Duration;
import net.fortuna.ical4j.model.property.ExDate;
import net.fortuna.ical4j.model.property.ExRule;
import net.fortuna.ical4j.model.property.RDate;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.validate.ValidationException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/* loaded from: classes3.dex */
public abstract class Component implements Serializable {
    public static final String AVAILABLE = "AVAILABLE";
    public static final String BEGIN = "BEGIN";
    public static final String END = "END";
    public static final String EXPERIMENTAL_PREFIX = "X-";
    public static final String VALARM = "VALARM";
    public static final String VAVAILABILITY = "VAVAILABILITY";
    public static final String VEVENT = "VEVENT";
    public static final String VFREEBUSY = "VFREEBUSY";
    public static final String VJOURNAL = "VJOURNAL";
    public static final String VTIMEZONE = "VTIMEZONE";
    public static final String VTODO = "VTODO";
    public static final String VVENUE = "VVENUE";
    private static final long serialVersionUID = 4943193483665822201L;
    private String name;
    private PropertyList<Property> properties;

    public abstract void validate(boolean z) throws ValidationException;

    protected Component(String str) {
        this(str, new PropertyList());
    }

    protected Component(String str, PropertyList<Property> propertyList) {
        this.name = str;
        this.properties = propertyList;
    }

    public String toString() {
        return "BEGIN:" + getName() + "\r\n" + getProperties() + "END:" + getName() + "\r\n";
    }

    public final String getName() {
        return this.name;
    }

    public final PropertyList<Property> getProperties() {
        return this.properties;
    }

    public final <C extends Property> PropertyList<C> getProperties(String str) {
        return (PropertyList<C>) getProperties().getProperties(str);
    }

    public final <T extends Property> T getProperty(String str) {
        return (T) getProperties().getProperty(str);
    }

    protected final Property getRequiredProperty(String str) throws ConstraintViolationException {
        Property property = (Property) getProperties().getProperty(str);
        if (property != null) {
            return property;
        }
        throw new ConstraintViolationException(String.format("Missing %s property", str));
    }

    public final void validate() throws ValidationException {
        validate(true);
    }

    protected final void validateProperties() throws ValidationException {
        Iterator<Property> it2 = getProperties().iterator();
        while (it2.hasNext()) {
            it2.next().validate();
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof Component) {
            Component component = (Component) obj;
            return new EqualsBuilder().append(getName(), component.getName()).append(getProperties(), component.getProperties()).isEquals();
        }
        return super.equals(obj);
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getName()).append(getProperties()).toHashCode();
    }

    public Component copy() throws URISyntaxException, IOException, ParseException {
        return new ComponentFactoryImpl().createComponent(getName(), new PropertyList(getProperties()));
    }

    public final PeriodList calculateRecurrenceSet(final Period period) {
        final TemporalAmount duration;
        Period period2;
        PeriodList periodList = new PeriodList();
        final DtStart dtStart = (DtStart) getProperty(Property.DTSTART);
        DateProperty dateProperty = (DateProperty) getProperty(Property.DTEND);
        if (dateProperty == null) {
            dateProperty = (DateProperty) getProperty(Property.DUE);
        }
        Duration duration2 = (Duration) getProperty(Property.DURATION);
        if (dtStart == null) {
            return periodList;
        }
        final Value value = (Value) dtStart.getParameter(Parameter.VALUE);
        if (dtStart.isUtc()) {
            periodList.setUtc(true);
        } else if (dtStart.getDate() instanceof DateTime) {
            periodList.setTimeZone(((DateTime) dtStart.getDate()).getTimeZone());
        }
        if (dateProperty == null && duration2 == null) {
            duration = java.time.Duration.ZERO;
        } else if (duration2 == null) {
            duration = TemporalAmountAdapter.fromDateRange(dtStart.getDate(), dateProperty.getDate()).getDuration();
        } else {
            duration = duration2.getDuration();
        }
        PropertyList properties = getProperties(Property.RDATE);
        periodList.addAll((Collection) properties.stream().filter(new Predicate() { // from class: net.fortuna.ical4j.model.-$$Lambda$Component$1EhtsS8PkrQqQborMhgpiIRHbVA
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Component.lambda$calculateRecurrenceSet$0((RDate) obj);
            }
        }).map(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$Component$vJdNrRxpYw07FSf6pUIaNzeQMw4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((RDate) obj).getPeriods();
            }
        }).flatMap(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$gQB_1jdp3yW7ohSxJFO8Pn5WkpM
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((PeriodList) obj).stream();
            }
        }).filter(new Predicate() { // from class: net.fortuna.ical4j.model.-$$Lambda$Component$8BcezB9LJYTqAb02saQL26c7lNQ
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return period.intersects((Period) obj);
            }
        }).collect(Collectors.toList()));
        periodList.addAll((Collection) properties.stream().filter(new Predicate() { // from class: net.fortuna.ical4j.model.-$$Lambda$Component$FGKzulRAwYhyrjo0i24uOzArRfk
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Component.lambda$calculateRecurrenceSet$3((RDate) obj);
            }
        }).map(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$Component$C1lDtpqyQZneEe62zp8f1BNiEdg
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((RDate) obj).getDates();
            }
        }).flatMap(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$Y-K4UADtjoNYWBLH-fad7vEA97Y
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DateList) obj).stream();
            }
        }).filter(new Predicate() { // from class: net.fortuna.ical4j.model.-$$Lambda$Component$TJ-qm7qdQQ4sjv_F1ob7mw0_bjQ
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return period.includes((Date) obj);
            }
        }).map(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$Component$l3FC3k0Yx8w0ikBHUDOlhbXukw8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Component.lambda$calculateRecurrenceSet$6(duration, (Date) obj);
            }
        }).collect(Collectors.toList()));
        periodList.addAll((Collection) properties.stream().filter(new Predicate() { // from class: net.fortuna.ical4j.model.-$$Lambda$Component$M2CfBhxPbHyoxTE9pUtoXSXRqzI
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Component.lambda$calculateRecurrenceSet$7((RDate) obj);
            }
        }).map(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$Component$9SfktM4Vs_hGrsNrgNZxt83yufI
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((RDate) obj).getDates();
            }
        }).flatMap(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$Y-K4UADtjoNYWBLH-fad7vEA97Y
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DateList) obj).stream();
            }
        }).filter(new Predicate() { // from class: net.fortuna.ical4j.model.-$$Lambda$Component$UxS4XlhZOYAqeXPI7zMAcBo2dZ4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return period.includes((Date) obj);
            }
        }).map(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$Component$OKUqnetVmSLt1r_nQq-DRCev1N8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Component.lambda$calculateRecurrenceSet$10(duration, (Date) obj);
            }
        }).collect(Collectors.toList()));
        final DateTime dateTime = new DateTime(period.getStart());
        dateTime.setTime(Date.from(period.getStart().toInstant().minus(duration)).getTime());
        PropertyList properties2 = getProperties(Property.RRULE);
        if (!properties2.isEmpty()) {
            periodList.addAll((Collection) properties2.stream().map(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$Component$SJQD5n9SOdfheM-KlfhwpkZc_IM
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((RRule) obj).getRecur().getDates(dtStart.getDate(), new Period(dateTime, period.getEnd()), value);
                }
            }).flatMap(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$Y-K4UADtjoNYWBLH-fad7vEA97Y
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((DateList) obj).stream();
                }
            }).map(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$Component$HbHeE45KnvMLf9SfSYbV_rUSc_s
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Component.lambda$calculateRecurrenceSet$12(duration, (Date) obj);
                }
            }).collect(Collectors.toList()));
        } else {
            if (dateProperty != null) {
                period2 = new Period(new DateTime(dtStart.getDate()), new DateTime(dateProperty.getDate()));
            } else {
                if (duration2 == null) {
                    duration2 = new Duration(duration);
                }
                period2 = new Period(new DateTime(dtStart.getDate()), duration2.getDuration());
            }
            if (period.intersects(period2)) {
                periodList.add(period2);
            }
        }
        final List list = (List) getProperties(Property.EXDATE).stream().map(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$Component$Jir7LErOmH9flQHrGN4coHev19s
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ExDate) obj).getDates();
            }
        }).flatMap(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$Y-K4UADtjoNYWBLH-fad7vEA97Y
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DateList) obj).stream();
            }
        }).collect(Collectors.toList());
        periodList.removeIf(new Predicate() { // from class: net.fortuna.ical4j.model.-$$Lambda$Component$AoTHijRIvnWBm7yg29-_qtzV_KI
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Component.lambda$calculateRecurrenceSet$14(list, (Period) obj);
            }
        });
        final List list2 = (List) getProperties(Property.EXRULE).stream().map(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$Component$ysQIkNNNggcf8vve7U2aE5-UDnE
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ExRule) obj).getRecur().getDates(dtStart.getDate(), period, value);
            }
        }).flatMap(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$Y-K4UADtjoNYWBLH-fad7vEA97Y
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DateList) obj).stream();
            }
        }).collect(Collectors.toList());
        periodList.removeIf(new Predicate() { // from class: net.fortuna.ical4j.model.-$$Lambda$Component$u9zqtyGcM-uJj6xq6B2EIxtCy2A
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Component.lambda$calculateRecurrenceSet$16(list2, (Period) obj);
            }
        });
        return periodList;
    }

    static /* synthetic */ boolean lambda$calculateRecurrenceSet$0(RDate rDate) {
        return rDate.getParameter(Parameter.VALUE) == Value.PERIOD;
    }

    static /* synthetic */ boolean lambda$calculateRecurrenceSet$3(RDate rDate) {
        return rDate.getParameter(Parameter.VALUE) == Value.DATE_TIME;
    }

    static /* synthetic */ Period lambda$calculateRecurrenceSet$6(TemporalAmount temporalAmount, Date date) {
        return new Period((DateTime) date, temporalAmount);
    }

    static /* synthetic */ boolean lambda$calculateRecurrenceSet$7(RDate rDate) {
        return rDate.getParameter(Parameter.VALUE) == Value.DATE;
    }

    static /* synthetic */ Period lambda$calculateRecurrenceSet$10(TemporalAmount temporalAmount, Date date) {
        return new Period(new DateTime(date), temporalAmount);
    }

    static /* synthetic */ Period lambda$calculateRecurrenceSet$12(TemporalAmount temporalAmount, Date date) {
        return new Period(new DateTime(date), temporalAmount);
    }

    static /* synthetic */ boolean lambda$calculateRecurrenceSet$14(List list, Period period) {
        return list.contains(period.getStart()) || list.contains(new Date(period.getStart()));
    }

    static /* synthetic */ boolean lambda$calculateRecurrenceSet$16(List list, Period period) {
        return list.contains(period.getStart()) || list.contains(new Date(period.getStart()));
    }
}
