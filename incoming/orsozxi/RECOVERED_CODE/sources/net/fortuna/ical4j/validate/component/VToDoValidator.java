package net.fortuna.ical4j.validate.component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VToDo;
import net.fortuna.ical4j.validate.ComponentValidator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationRule;
import net.fortuna.ical4j.validate.Validator;

/* loaded from: classes3.dex */
public class VToDoValidator extends ComponentValidator<VToDo> {
    private final boolean alarmsAllowed;
    private final Validator<VAlarm> itipValidator;

    public VToDoValidator(List<ValidationRule> list) {
        this(list, true);
    }

    public VToDoValidator(List<ValidationRule> list, boolean z) {
        super(list);
        this.itipValidator = new ComponentValidator(Arrays.asList(new ValidationRule(ValidationRule.ValidationType.One, "ACTION", Property.TRIGGER), new ValidationRule(ValidationRule.ValidationType.OneOrLess, "DESCRIPTION", Property.DURATION, Property.REPEAT, Property.SUMMARY)));
        this.alarmsAllowed = z;
    }

    @Override // net.fortuna.ical4j.validate.ComponentValidator
    public void validate(VToDo vToDo) throws ValidationException {
        super.validate((VToDoValidator) vToDo);
        if (this.alarmsAllowed) {
            ComponentList<VAlarm> alarms = vToDo.getAlarms();
            Validator<VAlarm> validator = this.itipValidator;
            Objects.requireNonNull(validator);
            alarms.forEach(new $$Lambda$7JfRWWrEauuGDNRiuQY32dyh3o(validator));
            return;
        }
        ComponentValidator.assertNone(Component.VALARM, vToDo.getAlarms());
    }
}
