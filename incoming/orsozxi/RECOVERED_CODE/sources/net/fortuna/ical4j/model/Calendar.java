package net.fortuna.ical4j.model;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Iterator;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.validate.AbstractCalendarValidatorFactory;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.Validator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/* loaded from: classes3.dex */
public class Calendar implements Serializable {
    public static final String BEGIN = "BEGIN";
    public static final String END = "END";
    public static final String VCALENDAR = "VCALENDAR";
    private static final long serialVersionUID = -1654118204678581940L;
    private final ComponentList<CalendarComponent> components;
    private final PropertyList<Property> properties;
    private final Validator<Calendar> validator;

    public Calendar() {
        this(new PropertyList(), new ComponentList());
    }

    public Calendar(ComponentList<CalendarComponent> componentList) {
        this(new PropertyList(), componentList);
    }

    public Calendar(PropertyList<Property> propertyList, ComponentList<CalendarComponent> componentList) {
        this(propertyList, componentList, AbstractCalendarValidatorFactory.getInstance().newInstance());
    }

    public Calendar(PropertyList<Property> propertyList, ComponentList<CalendarComponent> componentList, Validator<Calendar> validator) {
        this.properties = propertyList;
        this.components = componentList;
        this.validator = validator;
    }

    public Calendar(Calendar calendar) throws URISyntaxException, IOException, ParseException {
        this(new PropertyList(calendar.getProperties()), new ComponentList((ComponentList) calendar.getComponents()));
    }

    public final String toString() {
        return "BEGIN:VCALENDAR\r\n" + getProperties() + getComponents() + "END:" + VCALENDAR + "\r\n";
    }

    public final ComponentList<CalendarComponent> getComponents() {
        return this.components;
    }

    public final <C extends CalendarComponent> ComponentList<C> getComponents(String str) {
        return (ComponentList<C>) getComponents().getComponents(str);
    }

    public final CalendarComponent getComponent(String str) {
        return (CalendarComponent) getComponents().getComponent(str);
    }

    public final PropertyList<Property> getProperties() {
        return this.properties;
    }

    public final PropertyList<Property> getProperties(String str) {
        return getProperties().getProperties(str);
    }

    public final <T extends Property> T getProperty(String str) {
        return (T) getProperties().getProperty(str);
    }

    public final void validate() throws ValidationException {
        validate(true);
    }

    public void validate(boolean z) throws ValidationException {
        this.validator.validate(this);
        if (z) {
            validateProperties();
            validateComponents();
        }
    }

    private void validateProperties() throws ValidationException {
        Iterator<Property> it2 = getProperties().iterator();
        while (it2.hasNext()) {
            it2.next().validate();
        }
    }

    private void validateComponents() throws ValidationException {
        Iterator<CalendarComponent> it2 = getComponents().iterator();
        while (it2.hasNext()) {
            it2.next().validate();
        }
    }

    public final ProdId getProductId() {
        return (ProdId) getProperty(Property.PRODID);
    }

    public final Version getVersion() {
        return (Version) getProperty(Property.VERSION);
    }

    public final CalScale getCalendarScale() {
        return (CalScale) getProperty(Property.CALSCALE);
    }

    public final Method getMethod() {
        return (Method) getProperty(Property.METHOD);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Calendar) {
            Calendar calendar = (Calendar) obj;
            return new EqualsBuilder().append(getProperties(), calendar.getProperties()).append(getComponents(), calendar.getComponents()).isEquals();
        }
        return super.equals(obj);
    }

    public final int hashCode() {
        return new HashCodeBuilder().append(getProperties()).append(getComponents()).toHashCode();
    }
}
