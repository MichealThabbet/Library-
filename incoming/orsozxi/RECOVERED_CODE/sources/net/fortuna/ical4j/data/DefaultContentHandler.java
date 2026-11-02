package net.fortuna.ical4j.data;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.CalendarException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentBuilder;
import net.fortuna.ical4j.model.ComponentFactory;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterBuilder;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyBuilder;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.component.Available;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VAvailability;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.component.VToDo;
import net.fortuna.ical4j.model.parameter.TzId;
import net.fortuna.ical4j.model.property.DateListProperty;
import net.fortuna.ical4j.model.property.DateProperty;
import net.fortuna.ical4j.util.Constants;

/* loaded from: classes3.dex */
public class DefaultContentHandler implements ContentHandler {
    private Calendar calendar;
    private ComponentBuilder<CalendarComponent> componentBuilder;
    private final Supplier<List<ComponentFactory>> componentFactorySupplier;
    private final Consumer<Calendar> consumer;
    private final Supplier<List<ParameterFactory>> parameterFactorySupplier;
    private List<TzId> propertiesWithTzId;
    private PropertyBuilder propertyBuilder;
    private final Supplier<List<PropertyFactory>> propertyFactorySupplier;
    private ComponentBuilder<Component> subComponentBuilder;
    private final TimeZoneRegistry tzRegistry;

    public DefaultContentHandler(Consumer<Calendar> consumer, TimeZoneRegistry timeZoneRegistry) {
        this(consumer, timeZoneRegistry, new DefaultParameterFactorySupplier(), new DefaultPropertyFactorySupplier(), new DefaultComponentFactorySupplier());
    }

    public DefaultContentHandler(Consumer<Calendar> consumer, TimeZoneRegistry timeZoneRegistry, Supplier<List<ParameterFactory>> supplier, Supplier<List<PropertyFactory>> supplier2, Supplier<List<ComponentFactory>> supplier3) {
        this.consumer = consumer;
        this.tzRegistry = timeZoneRegistry;
        this.parameterFactorySupplier = supplier;
        this.propertyFactorySupplier = supplier2;
        this.componentFactorySupplier = supplier3;
    }

    @Override // net.fortuna.ical4j.data.ContentHandler
    public void startCalendar() {
        this.calendar = new Calendar();
        this.propertiesWithTzId = new ArrayList();
    }

    @Override // net.fortuna.ical4j.data.ContentHandler
    public void endCalendar() throws IOException {
        if (this.propertiesWithTzId.size() > 0 && this.tzRegistry != null) {
            Iterator<CalendarComponent> it2 = this.calendar.getComponents().iterator();
            while (it2.hasNext()) {
                CalendarComponent next = it2.next();
                resolveTimezones(next.getProperties());
                if (next instanceof VAvailability) {
                    Iterator<Available> it3 = ((VAvailability) next).getAvailable().iterator();
                    while (it3.hasNext()) {
                        resolveTimezones(it3.next().getProperties());
                    }
                } else if (next instanceof VEvent) {
                    Iterator<VAlarm> it4 = ((VEvent) next).getAlarms().iterator();
                    while (it4.hasNext()) {
                        resolveTimezones(it4.next().getProperties());
                    }
                } else if (next instanceof VToDo) {
                    Iterator<VAlarm> it5 = ((VToDo) next).getAlarms().iterator();
                    while (it5.hasNext()) {
                        resolveTimezones(it5.next().getProperties());
                    }
                }
            }
        }
        this.consumer.accept(this.calendar);
    }

    @Override // net.fortuna.ical4j.data.ContentHandler
    public void startComponent(String str) {
        if (this.componentBuilder != null) {
            ComponentBuilder<Component> componentBuilder = new ComponentBuilder<>();
            this.subComponentBuilder = componentBuilder;
            componentBuilder.factories(this.componentFactorySupplier.get()).name(str);
        } else {
            ComponentBuilder<CalendarComponent> componentBuilder2 = new ComponentBuilder<>();
            this.componentBuilder = componentBuilder2;
            componentBuilder2.factories(this.componentFactorySupplier.get()).name(str);
        }
    }

    @Override // net.fortuna.ical4j.data.ContentHandler
    public void endComponent(String str) {
        TimeZoneRegistry timeZoneRegistry;
        assertComponent(this.componentBuilder);
        ComponentBuilder<Component> componentBuilder = this.subComponentBuilder;
        if (componentBuilder != null) {
            this.componentBuilder.subComponent(componentBuilder.build());
            this.subComponentBuilder = null;
            return;
        }
        CalendarComponent calendarComponent = (CalendarComponent) this.componentBuilder.build();
        this.calendar.getComponents().add(calendarComponent);
        if ((calendarComponent instanceof VTimeZone) && (timeZoneRegistry = this.tzRegistry) != null) {
            timeZoneRegistry.register(new TimeZone((VTimeZone) calendarComponent));
        }
        this.componentBuilder = null;
    }

    @Override // net.fortuna.ical4j.data.ContentHandler
    public void startProperty(String str) {
        this.propertyBuilder = new PropertyBuilder().factories(this.propertyFactorySupplier.get()).name(str);
    }

    @Override // net.fortuna.ical4j.data.ContentHandler
    public void propertyValue(String str) {
        this.propertyBuilder.value(str);
    }

    @Override // net.fortuna.ical4j.data.ContentHandler
    public void endProperty(String str) throws URISyntaxException, IOException, ParseException {
        assertProperty(this.propertyBuilder);
        Property propertyForProperty = Constants.forProperty(this.propertyBuilder.build());
        ComponentBuilder<CalendarComponent> componentBuilder = this.componentBuilder;
        if (componentBuilder != null) {
            ComponentBuilder<Component> componentBuilder2 = this.subComponentBuilder;
            if (componentBuilder2 != null) {
                componentBuilder2.property(propertyForProperty);
                return;
            } else {
                componentBuilder.property(propertyForProperty);
                return;
            }
        }
        Calendar calendar = this.calendar;
        if (calendar != null) {
            calendar.getProperties().add((PropertyList<Property>) propertyForProperty);
        }
    }

    @Override // net.fortuna.ical4j.data.ContentHandler
    public void parameter(String str, String str2) throws URISyntaxException {
        assertProperty(this.propertyBuilder);
        Parameter parameterBuild = new ParameterBuilder().factories(this.parameterFactorySupplier.get()).name(str).value(str2).build();
        if ((parameterBuild instanceof TzId) && this.tzRegistry != null) {
            this.propertiesWithTzId.add((TzId) parameterBuild);
        }
        this.propertyBuilder.parameter(parameterBuild);
    }

    private void assertComponent(ComponentBuilder componentBuilder) {
        if (componentBuilder == null) {
            throw new CalendarException("Expected component not initialised");
        }
    }

    private void assertProperty(PropertyBuilder propertyBuilder) {
        if (propertyBuilder == null) {
            throw new CalendarException("Expected property not initialised");
        }
    }

    private void resolveTimezones(List<Property> list) throws IOException {
        for (TzId tzId : this.propertiesWithTzId) {
            TimeZone timeZone = this.tzRegistry.getTimeZone(tzId.getValue());
            if (timeZone != null) {
                for (Property property : list) {
                    if (tzId.equals(property.getParameter("TZID"))) {
                        String value = property.getValue();
                        if (property instanceof DateProperty) {
                            ((DateProperty) property).setTimeZone(timeZone);
                        } else if (property instanceof DateListProperty) {
                            ((DateListProperty) property).setTimeZone(timeZone);
                        } else {
                            throw new CalendarException("Invalid parameter: " + tzId.getName());
                        }
                        try {
                            property.setValue(value);
                        } catch (URISyntaxException | ParseException e) {
                            throw new CalendarException(e);
                        }
                    }
                }
            }
        }
    }
}
