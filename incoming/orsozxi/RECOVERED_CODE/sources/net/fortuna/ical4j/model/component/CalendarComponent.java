package net.fortuna.ical4j.model.component;

import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.validate.EmptyValidator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.Validator;

/* loaded from: classes3.dex */
public abstract class CalendarComponent extends Component {
    protected static final Validator<CalendarComponent> EMPTY_VALIDATOR = new EmptyValidator();
    private static final long serialVersionUID = -5832972592377720592L;

    protected abstract Validator<CalendarComponent> getValidator(Method method);

    public CalendarComponent(String str) {
        super(str);
    }

    public CalendarComponent(String str, PropertyList propertyList) {
        super(str, propertyList);
    }

    public final void validate(Method method) throws ValidationException {
        Validator<CalendarComponent> validator = getValidator(method);
        if (validator != null) {
            validator.validate(this);
            return;
        }
        throw new ValidationException("Unsupported method: " + method);
    }

    public final void validatePublish() throws ValidationException {
        validate(Method.PUBLISH);
    }

    public final void validateRequest() throws ValidationException {
        validate(Method.REQUEST);
    }

    public final void validateReply() throws ValidationException {
        validate(Method.REPLY);
    }

    public final void validateAdd() throws ValidationException {
        validate(Method.ADD);
    }

    public final void validateCancel() throws ValidationException {
        validate(Method.CANCEL);
    }

    public final void validateRefresh() throws ValidationException {
        validate(Method.REFRESH);
    }

    public final void validateCounter() throws ValidationException {
        validate(Method.COUNTER);
    }

    public final void validateDeclineCounter() throws ValidationException {
        validate(Method.DECLINE_COUNTER);
    }
}
