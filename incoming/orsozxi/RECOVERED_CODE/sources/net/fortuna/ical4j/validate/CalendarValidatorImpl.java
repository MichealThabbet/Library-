package net.fortuna.ical4j.validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Categories;
import net.fortuna.ical4j.model.property.Color;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Image;
import net.fortuna.ical4j.model.property.LastModified;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Name;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.RefreshInterval;
import net.fortuna.ical4j.model.property.Source;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Url;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.model.property.XProperty;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.validate.ValidationRule;

/* loaded from: classes3.dex */
public class CalendarValidatorImpl implements Validator<Calendar> {
    protected final List<Class<? extends Property>> calendarProperties;
    private final List<ValidationRule> rules;

    public CalendarValidatorImpl(List<ValidationRule> list) {
        ArrayList arrayList = new ArrayList();
        this.calendarProperties = arrayList;
        this.rules = list;
        Collections.addAll(arrayList, CalScale.class, Method.class, ProdId.class, Version.class, Uid.class, LastModified.class, Url.class, RefreshInterval.class, Source.class, Color.class, Name.class, Description.class, Categories.class, Image.class);
    }

    @Override // net.fortuna.ical4j.validate.Validator
    public void validate(final Calendar calendar) throws ValidationException {
        for (ValidationRule validationRule : this.rules) {
            if (!CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION) || !validationRule.isRelaxedModeSupported()) {
                int i = AnonymousClass1.$SwitchMap$net$fortuna$ical4j$validate$ValidationRule$ValidationType[validationRule.getType().ordinal()];
                if (i == 1) {
                    validationRule.getInstances().forEach(new Consumer() { // from class: net.fortuna.ical4j.validate.-$$Lambda$CalendarValidatorImpl$yEE3TUb4mYoGiCl46QbLGoFWHT0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) throws ValidationException {
                            PropertyValidator.assertNone((String) obj, calendar.getProperties());
                        }
                    });
                } else if (i == 2) {
                    validationRule.getInstances().forEach(new Consumer() { // from class: net.fortuna.ical4j.validate.-$$Lambda$CalendarValidatorImpl$Fvjq_lSRLzxdRtNG2ygYro0cvxs
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) throws ValidationException {
                            PropertyValidator.assertOne((String) obj, calendar.getProperties());
                        }
                    });
                } else if (i == 3) {
                    validationRule.getInstances().forEach(new Consumer() { // from class: net.fortuna.ical4j.validate.-$$Lambda$CalendarValidatorImpl$ofK_M6jOW4ydPKonUqFE0By1KwE
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) throws ValidationException {
                            PropertyValidator.assertOneOrLess((String) obj, calendar.getProperties());
                        }
                    });
                } else if (i == 4) {
                    validationRule.getInstances().forEach(new Consumer() { // from class: net.fortuna.ical4j.validate.-$$Lambda$CalendarValidatorImpl$jiQLCdhwuX9griymXsHMwH2Kzf4
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) throws ValidationException {
                            PropertyValidator.assertOneOrMore((String) obj, calendar.getProperties());
                        }
                    });
                }
            }
        }
        if (!CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION) && !Version.VERSION_2_0.equals(calendar.getProperty(Property.VERSION))) {
            throw new ValidationException("Unsupported Version: " + calendar.getProperty(Property.VERSION).getValue());
        }
        if (calendar.getComponents().isEmpty()) {
            throw new ValidationException("Calendar must contain at least one component");
        }
        Iterator<Property> it2 = calendar.getProperties().iterator();
        while (it2.hasNext()) {
            final Property next = it2.next();
            boolean z = this.calendarProperties.stream().filter(new Predicate() { // from class: net.fortuna.ical4j.validate.-$$Lambda$CalendarValidatorImpl$LR6ua-9BijRYY-4kxPGyvqrM5So
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((Class) obj).isInstance(next);
                }
            }) != null;
            if (!(next instanceof XProperty) && !z) {
                throw new ValidationException("Invalid property: " + next.getName());
            }
        }
        Method method = (Method) calendar.getProperty(Property.METHOD);
        if (Method.PUBLISH.equals(method)) {
            new PublishValidator().validate(calendar);
        } else if (Method.REQUEST.equals(calendar.getProperty(Property.METHOD))) {
            new RequestValidator().validate(calendar);
        } else if (Method.REPLY.equals(calendar.getProperty(Property.METHOD))) {
            new ReplyValidator().validate(calendar);
        } else if (Method.ADD.equals(calendar.getProperty(Property.METHOD))) {
            new AddValidator().validate(calendar);
        } else if (Method.CANCEL.equals(calendar.getProperty(Property.METHOD))) {
            new CancelValidator().validate(calendar);
        } else if (Method.REFRESH.equals(calendar.getProperty(Property.METHOD))) {
            new RefreshValidator().validate(calendar);
        } else if (Method.COUNTER.equals(calendar.getProperty(Property.METHOD))) {
            new CounterValidator().validate(calendar);
        } else if (Method.DECLINE_COUNTER.equals(calendar.getProperty(Property.METHOD))) {
            new DeclineCounterValidator().validate(calendar);
        }
        if (method != null) {
            Iterator<CalendarComponent> it3 = calendar.getComponents().iterator();
            while (it3.hasNext()) {
                it3.next().validate(method);
            }
        }
    }

    /* renamed from: net.fortuna.ical4j.validate.CalendarValidatorImpl$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$fortuna$ical4j$validate$ValidationRule$ValidationType;

        static {
            int[] iArr = new int[ValidationRule.ValidationType.values().length];
            $SwitchMap$net$fortuna$ical4j$validate$ValidationRule$ValidationType = iArr;
            try {
                iArr[ValidationRule.ValidationType.None.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$fortuna$ical4j$validate$ValidationRule$ValidationType[ValidationRule.ValidationType.One.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$fortuna$ical4j$validate$ValidationRule$ValidationType[ValidationRule.ValidationType.OneOrLess.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$net$fortuna$ical4j$validate$ValidationRule$ValidationType[ValidationRule.ValidationType.OneOrMore.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static class PublishValidator implements Validator<Calendar> {
        @Override // net.fortuna.ical4j.validate.Validator
        public void validate(Calendar calendar) throws ValidationException {
            if (calendar.getComponent(Component.VEVENT) != null) {
                ComponentValidator.assertNone(Component.VFREEBUSY, calendar.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, calendar.getComponents());
                if (CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION)) {
                    return;
                }
                ComponentValidator.assertNone(Component.VTODO, calendar.getComponents());
                return;
            }
            if (calendar.getComponent(Component.VFREEBUSY) != null) {
                ComponentValidator.assertNone(Component.VTODO, calendar.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, calendar.getComponents());
                ComponentValidator.assertNone(Component.VTIMEZONE, calendar.getComponents());
                ComponentValidator.assertNone(Component.VALARM, calendar.getComponents());
                return;
            }
            if (calendar.getComponent(Component.VTODO) != null) {
                ComponentValidator.assertNone(Component.VJOURNAL, calendar.getComponents());
            }
        }
    }

    public static class RequestValidator implements Validator<Calendar> {
        @Override // net.fortuna.ical4j.validate.Validator
        public void validate(Calendar calendar) throws ValidationException {
            if (calendar.getComponent(Component.VEVENT) != null) {
                ComponentValidator.assertNone(Component.VFREEBUSY, calendar.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, calendar.getComponents());
                ComponentValidator.assertNone(Component.VTODO, calendar.getComponents());
            } else {
                if (calendar.getComponent(Component.VFREEBUSY) != null) {
                    ComponentValidator.assertNone(Component.VTODO, calendar.getComponents());
                    ComponentValidator.assertNone(Component.VJOURNAL, calendar.getComponents());
                    ComponentValidator.assertNone(Component.VTIMEZONE, calendar.getComponents());
                    ComponentValidator.assertNone(Component.VALARM, calendar.getComponents());
                    return;
                }
                if (calendar.getComponent(Component.VTODO) != null) {
                    ComponentValidator.assertNone(Component.VJOURNAL, calendar.getComponents());
                }
            }
        }
    }

    public static class ReplyValidator implements Validator<Calendar> {
        @Override // net.fortuna.ical4j.validate.Validator
        public void validate(Calendar calendar) throws ValidationException {
            if (calendar.getComponent(Component.VEVENT) != null) {
                ComponentValidator.assertOneOrLess(Component.VTIMEZONE, calendar.getComponents());
                ComponentValidator.assertNone(Component.VALARM, calendar.getComponents());
                ComponentValidator.assertNone(Component.VFREEBUSY, calendar.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, calendar.getComponents());
                ComponentValidator.assertNone(Component.VTODO, calendar.getComponents());
                return;
            }
            if (calendar.getComponent(Component.VFREEBUSY) != null) {
                ComponentValidator.assertNone(Component.VTODO, calendar.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, calendar.getComponents());
                ComponentValidator.assertNone(Component.VTIMEZONE, calendar.getComponents());
                ComponentValidator.assertNone(Component.VALARM, calendar.getComponents());
                return;
            }
            if (calendar.getComponent(Component.VTODO) != null) {
                ComponentValidator.assertOneOrLess(Component.VTIMEZONE, calendar.getComponents());
                ComponentValidator.assertNone(Component.VALARM, calendar.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, calendar.getComponents());
            }
        }
    }

    public static class AddValidator implements Validator<Calendar> {
        @Override // net.fortuna.ical4j.validate.Validator
        public void validate(Calendar calendar) throws ValidationException {
            if (calendar.getComponent(Component.VEVENT) != null) {
                ComponentValidator.assertNone(Component.VFREEBUSY, calendar.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, calendar.getComponents());
                ComponentValidator.assertNone(Component.VTODO, calendar.getComponents());
            } else if (calendar.getComponent(Component.VTODO) != null) {
                ComponentValidator.assertNone(Component.VFREEBUSY, calendar.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, calendar.getComponents());
            } else if (calendar.getComponent(Component.VJOURNAL) != null) {
                ComponentValidator.assertOneOrLess(Component.VTIMEZONE, calendar.getComponents());
                ComponentValidator.assertNone(Component.VFREEBUSY, calendar.getComponents());
            }
        }
    }

    public static class CancelValidator implements Validator<Calendar> {
        @Override // net.fortuna.ical4j.validate.Validator
        public void validate(Calendar calendar) throws ValidationException {
            if (calendar.getComponent(Component.VEVENT) != null) {
                ComponentValidator.assertNone(Component.VALARM, calendar.getComponents());
                ComponentValidator.assertNone(Component.VFREEBUSY, calendar.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, calendar.getComponents());
                ComponentValidator.assertNone(Component.VTODO, calendar.getComponents());
                return;
            }
            if (calendar.getComponent(Component.VTODO) != null) {
                ComponentValidator.assertOneOrLess(Component.VTIMEZONE, calendar.getComponents());
                ComponentValidator.assertNone(Component.VALARM, calendar.getComponents());
                ComponentValidator.assertNone(Component.VFREEBUSY, calendar.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, calendar.getComponents());
                return;
            }
            if (calendar.getComponent(Component.VJOURNAL) != null) {
                ComponentValidator.assertNone(Component.VALARM, calendar.getComponents());
                ComponentValidator.assertNone(Component.VFREEBUSY, calendar.getComponents());
            }
        }
    }

    public static class RefreshValidator implements Validator<Calendar> {
        @Override // net.fortuna.ical4j.validate.Validator
        public void validate(Calendar calendar) throws ValidationException {
            if (calendar.getComponent(Component.VEVENT) != null) {
                ComponentValidator.assertNone(Component.VALARM, calendar.getComponents());
                ComponentValidator.assertNone(Component.VFREEBUSY, calendar.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, calendar.getComponents());
                ComponentValidator.assertNone(Component.VTODO, calendar.getComponents());
                return;
            }
            if (calendar.getComponent(Component.VTODO) != null) {
                ComponentValidator.assertNone(Component.VALARM, calendar.getComponents());
                ComponentValidator.assertNone(Component.VFREEBUSY, calendar.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, calendar.getComponents());
                ComponentValidator.assertNone(Component.VTIMEZONE, calendar.getComponents());
            }
        }
    }

    public static class CounterValidator implements Validator<Calendar> {
        @Override // net.fortuna.ical4j.validate.Validator
        public void validate(Calendar calendar) throws ValidationException {
            if (calendar.getComponent(Component.VEVENT) != null) {
                ComponentValidator.assertNone(Component.VFREEBUSY, calendar.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, calendar.getComponents());
                ComponentValidator.assertNone(Component.VTODO, calendar.getComponents());
            } else if (calendar.getComponent(Component.VTODO) != null) {
                ComponentValidator.assertOneOrLess(Component.VTIMEZONE, calendar.getComponents());
                ComponentValidator.assertNone(Component.VFREEBUSY, calendar.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, calendar.getComponents());
            }
        }
    }

    public static class DeclineCounterValidator implements Validator<Calendar> {
        @Override // net.fortuna.ical4j.validate.Validator
        public void validate(Calendar calendar) throws ValidationException {
            if (calendar.getComponent(Component.VEVENT) != null) {
                ComponentValidator.assertNone(Component.VFREEBUSY, calendar.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, calendar.getComponents());
                ComponentValidator.assertNone(Component.VTODO, calendar.getComponents());
                ComponentValidator.assertNone(Component.VTIMEZONE, calendar.getComponents());
                ComponentValidator.assertNone(Component.VALARM, calendar.getComponents());
                return;
            }
            if (calendar.getComponent(Component.VTODO) != null) {
                ComponentValidator.assertNone(Component.VALARM, calendar.getComponents());
                ComponentValidator.assertNone(Component.VFREEBUSY, calendar.getComponents());
                ComponentValidator.assertNone(Component.VJOURNAL, calendar.getComponents());
            }
        }
    }
}
