package net.fortuna.ical4j.validate.component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.Observance;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.validate.ComponentValidator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;

/* loaded from: classes3.dex */
public class VTimeZoneValidator extends ComponentValidator<VTimeZone> {
    private final Validator itipValidator;

    public VTimeZoneValidator(List<ValidationRule> list) {
        super(list);
        this.itipValidator = new ComponentValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, Property.DTSTART, Property.TZOFFSETFROM, Property.TZOFFSETTO), new ValidationRule(ValidationRule.ValidationType.OneOrLess, Property.TZNAME)));
    }

    @Override // net.fortuna.ical4j.validate.ComponentValidator
    public void validate(VTimeZone vTimeZone) throws ValidationException {
        super.validate((VTimeZoneValidator) vTimeZone);
        ComponentList<Observance> observances = vTimeZone.getObservances();
        final Validator validator = this.itipValidator;
        Objects.requireNonNull(validator);
        observances.forEach(new Consumer() { // from class: net.fortuna.ical4j.validate.component.-$$Lambda$4ME-gUoA_fa5b5feflhJyuuLbNU
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws ValidationException {
                validator.validate((Observance) obj);
            }
        });
    }
}
