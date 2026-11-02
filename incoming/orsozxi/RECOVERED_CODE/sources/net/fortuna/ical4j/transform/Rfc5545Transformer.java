package net.fortuna.ical4j.transform;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.transform.rfc5545.RuleManager;

/* loaded from: classes3.dex */
public class Rfc5545Transformer implements Transformer<Calendar> {
    @Override // net.fortuna.ical4j.transform.Transformer
    public Calendar transform(Calendar calendar) throws SecurityException {
        conformPropertiesToRfc5545(calendar.getProperties());
        Iterator<CalendarComponent> it2 = calendar.getComponents().iterator();
        while (it2.hasNext()) {
            CalendarComponent next = it2.next();
            CountableProperties.removeExceededPropertiesForComponent(next);
            conformComponentToRfc5545(next);
            conformPropertiesToRfc5545(next.getProperties());
            for (Method method : next.getClass().getDeclaredMethods()) {
                if (ComponentList.class.isAssignableFrom(method.getReturnType()) && method.getName().startsWith("get")) {
                    try {
                        for (Component component : (List) method.invoke(next, new Object[0])) {
                            conformComponentToRfc5545(component);
                            conformPropertiesToRfc5545(component.getProperties());
                        }
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return calendar;
    }

    private static void conformPropertiesToRfc5545(List<Property> list) {
        Iterator<Property> it2 = list.iterator();
        while (it2.hasNext()) {
            RuleManager.applyTo(it2.next());
        }
    }

    private static void conformComponentToRfc5545(Component component) {
        RuleManager.applyTo(component);
    }

    private enum CountableProperties {
        STATUS(Property.STATUS, 1);

        private int maxApparitionNumber;
        private String name;

        CountableProperties(String str, int i) {
            this.maxApparitionNumber = i;
            this.name = str;
        }

        /* JADX WARN: Multi-variable type inference failed */
        protected void limitApparitionsNumberIn(Component component) {
            PropertyList properties = component.getProperties(this.name);
            if (properties.size() <= this.maxApparitionNumber) {
                return;
            }
            int size = properties.size() - this.maxApparitionNumber;
            for (int i = 0; i < size; i++) {
                component.getProperties().remove((Property) properties.get(i));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void removeExceededPropertiesForComponent(Component component) {
            for (CountableProperties countableProperties : values()) {
                countableProperties.limitApparitionsNumberIn(component);
            }
        }
    }
}
