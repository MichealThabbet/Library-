package net.fortuna.ical4j.model.component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentFactory;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.property.LastModified;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.TzId;
import net.fortuna.ical4j.model.property.TzUrl;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.Validator;
import net.fortuna.ical4j.validate.component.VTimeZoneValidator;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/* loaded from: classes3.dex */
public class VTimeZone extends CalendarComponent {
    private static final long serialVersionUID = 5629679741050917815L;
    private final Validator itipValidator;
    private ComponentList<Observance> observances;

    public VTimeZone() {
        super(Component.VTIMEZONE);
        this.itipValidator = new VTimeZoneValidator(Collections.emptyList());
        this.observances = new ComponentList<>();
    }

    public VTimeZone(PropertyList propertyList) {
        super(Component.VTIMEZONE, propertyList);
        this.itipValidator = new VTimeZoneValidator(Collections.emptyList());
        this.observances = new ComponentList<>();
    }

    public VTimeZone(ComponentList<Observance> componentList) {
        super(Component.VTIMEZONE);
        this.itipValidator = new VTimeZoneValidator(Collections.emptyList());
        this.observances = componentList;
    }

    public VTimeZone(PropertyList propertyList, ComponentList<Observance> componentList) {
        super(Component.VTIMEZONE, propertyList);
        this.itipValidator = new VTimeZoneValidator(Collections.emptyList());
        this.observances = componentList;
    }

    @Override // net.fortuna.ical4j.model.Component
    public final String toString() {
        return "BEGIN:" + getName() + "\r\n" + getProperties() + this.observances + "END:" + getName() + "\r\n";
    }

    @Override // net.fortuna.ical4j.model.Component
    public final void validate(boolean z) throws ValidationException {
        PropertyValidator.assertOne("TZID", getProperties());
        PropertyValidator.assertOneOrLess(Property.LAST_MODIFIED, getProperties());
        PropertyValidator.assertOneOrLess(Property.TZURL, getProperties());
        if (getObservances().getComponent(Observance.STANDARD) == null && getObservances().getComponent(Observance.DAYLIGHT) == null) {
            throw new ValidationException("Sub-components [STANDARD,DAYLIGHT] must be specified at least once");
        }
        Iterator<Observance> it2 = getObservances().iterator();
        while (it2.hasNext()) {
            it2.next().validate(z);
        }
        if (z) {
            validateProperties();
        }
    }

    @Override // net.fortuna.ical4j.model.component.CalendarComponent
    protected Validator getValidator(Method method) {
        return this.itipValidator;
    }

    public final ComponentList<Observance> getObservances() {
        return this.observances;
    }

    public final Observance getApplicableObservance(Date date) {
        Iterator<Observance> it2 = getObservances().iterator();
        Observance observance = null;
        Date date2 = null;
        while (it2.hasNext()) {
            Observance next = it2.next();
            Date latestOnset = next.getLatestOnset(date);
            if (date2 == null || (latestOnset != null && latestOnset.after(date2))) {
                observance = next;
                date2 = latestOnset;
            }
        }
        return observance;
    }

    public final TzId getTimeZoneId() {
        return (TzId) getProperty("TZID");
    }

    public final LastModified getLastModified() {
        return (LastModified) getProperty(Property.LAST_MODIFIED);
    }

    public final TzUrl getTimeZoneUrl() {
        return (TzUrl) getProperty(Property.TZURL);
    }

    @Override // net.fortuna.ical4j.model.Component
    public boolean equals(Object obj) {
        if (obj instanceof VTimeZone) {
            return super.equals(obj) && Objects.equals(this.observances, ((VTimeZone) obj).getObservances());
        }
        return super.equals(obj);
    }

    @Override // net.fortuna.ical4j.model.Component
    public int hashCode() {
        return new HashCodeBuilder().append(getName()).append(getProperties()).append(getObservances()).toHashCode();
    }

    @Override // net.fortuna.ical4j.model.Component
    public Component copy() throws URISyntaxException, IOException, ParseException {
        VTimeZone vTimeZone = (VTimeZone) super.copy();
        vTimeZone.observances = new ComponentList<>((ComponentList) this.observances);
        return vTimeZone;
    }

    public static class Factory extends Content.Factory implements ComponentFactory<VTimeZone> {
        public Factory() {
            super(Component.VTIMEZONE);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VTimeZone createComponent() {
            return new VTimeZone();
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VTimeZone createComponent(PropertyList propertyList) {
            return new VTimeZone(propertyList);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public VTimeZone createComponent(PropertyList propertyList, ComponentList componentList) {
            return new VTimeZone(propertyList, componentList);
        }
    }
}
