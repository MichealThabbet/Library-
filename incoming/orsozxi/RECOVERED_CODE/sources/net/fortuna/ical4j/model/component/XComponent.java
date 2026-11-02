package net.fortuna.ical4j.model.component;

import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.Validator;

/* loaded from: classes3.dex */
public class XComponent extends CalendarComponent {
    private static final long serialVersionUID = -3622674849097714927L;

    public XComponent(String str) {
        super(str);
    }

    public XComponent(String str, PropertyList propertyList) {
        super(str, propertyList);
    }

    @Override // net.fortuna.ical4j.model.Component
    public final void validate(boolean z) throws ValidationException {
        if (!CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION) && !getName().startsWith("X-")) {
            throw new ValidationException("Experimental components must have the following prefix: X-");
        }
        if (z) {
            validateProperties();
        }
    }

    @Override // net.fortuna.ical4j.model.component.CalendarComponent
    protected Validator getValidator(Method method) {
        return EMPTY_VALIDATOR;
    }
}
