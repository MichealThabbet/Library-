package net.fortuna.ical4j.validate;

import java.util.Arrays;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.validate.ValidationRule;

/* loaded from: classes3.dex */
public class DefaultCalendarValidatorFactory implements CalendarValidatorFactory {
    @Override // net.fortuna.ical4j.validate.CalendarValidatorFactory
    public Validator<Calendar> newInstance() {
        return new CalendarValidatorImpl(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.PRODID, Property.VERSION), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.CALSCALE, Property.METHOD)));
    }
}
