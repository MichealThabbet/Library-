package net.fortuna.ical4j.util;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.ConstraintViolationException;
import net.fortuna.ical4j.model.IndexedComponentList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.parameter.TzId;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Uid;
import org.apache.http.protocol.HTTP;

/* loaded from: classes3.dex */
public final class Calendars {
    private Calendars() {
    }

    public static Calendar load(String str) throws ParserException, IOException {
        return new CalendarBuilder().build(Files.newInputStream(Paths.get(str, new String[0]), new OpenOption[0]));
    }

    public static Calendar load(URL url) throws ParserException, IOException {
        return new CalendarBuilder().build(url.openStream());
    }

    public static Calendar merge(Calendar calendar, Calendar calendar2) {
        Calendar calendar3 = new Calendar();
        calendar3.getProperties().addAll(calendar.getProperties());
        Iterator<Property> it2 = calendar2.getProperties().iterator();
        while (it2.hasNext()) {
            Property next = it2.next();
            if (!calendar3.getProperties().contains(next)) {
                calendar3.getProperties().add((PropertyList<Property>) next);
            }
        }
        calendar3.getComponents().addAll(calendar.getComponents());
        Iterator<CalendarComponent> it3 = calendar2.getComponents().iterator();
        while (it3.hasNext()) {
            CalendarComponent next2 = it3.next();
            if (!calendar3.getComponents().contains(next2)) {
                calendar3.getComponents().add(next2);
            }
        }
        return calendar3;
    }

    public static Calendar wrap(CalendarComponent... calendarComponentArr) {
        return new Calendar((ComponentList<CalendarComponent>) new ComponentList(Arrays.asList(calendarComponentArr)));
    }

    public static Calendar[] split(Calendar calendar) {
        if (calendar.getComponents().size() <= 1 || calendar.getComponents(Component.VTIMEZONE).size() == calendar.getComponents().size()) {
            return new Calendar[]{calendar};
        }
        IndexedComponentList indexedComponentList = new IndexedComponentList(calendar.getComponents(Component.VTIMEZONE), "TZID");
        HashMap map = new HashMap();
        Iterator<CalendarComponent> it2 = calendar.getComponents().iterator();
        while (it2.hasNext()) {
            CalendarComponent next = it2.next();
            if (!(next instanceof VTimeZone)) {
                Uid uid = (Uid) next.getProperty(Property.UID);
                Calendar calendar2 = (Calendar) map.get(uid);
                if (calendar2 == null) {
                    calendar2 = new Calendar(calendar.getProperties(), new ComponentList());
                    Iterator<Property> it3 = calendar2.getProperties(Property.METHOD).iterator();
                    while (it3.hasNext()) {
                        calendar2.getProperties().remove(it3.next());
                    }
                    map.put(uid, calendar2);
                }
                Iterator<Property> it4 = next.getProperties().iterator();
                while (it4.hasNext()) {
                    TzId tzId = (TzId) it4.next().getParameter("TZID");
                    if (tzId != null) {
                        VTimeZone vTimeZone = (VTimeZone) indexedComponentList.getComponent(tzId.getValue());
                        if (!calendar2.getComponents().contains(vTimeZone)) {
                            calendar2.getComponents().add(vTimeZone);
                        }
                    }
                }
                calendar2.getComponents().add(next);
            }
        }
        return (Calendar[]) map.values().toArray(new Calendar[0]);
    }

    public static Uid getUid(Calendar calendar) throws ConstraintViolationException {
        Iterator<CalendarComponent> it2 = calendar.getComponents().iterator();
        Uid uid = null;
        while (it2.hasNext()) {
            Iterator<T> it3 = it2.next().getProperties(Property.UID).iterator();
            while (it3.hasNext()) {
                Property property = (Property) it3.next();
                if (uid != null && !uid.equals(property)) {
                    throw new ConstraintViolationException("More than one UID found in calendar");
                }
                uid = (Uid) property;
            }
        }
        if (uid != null) {
            return uid;
        }
        throw new ConstraintViolationException("Calendar must specify a single unique identifier (UID)");
    }

    public static String getContentType(Calendar calendar, Charset charset) {
        StringBuilder sb = new StringBuilder("text/calendar");
        Method method = (Method) calendar.getProperty(Property.METHOD);
        if (method != null) {
            sb.append("; method=");
            sb.append(method.getValue());
        }
        if (charset != null) {
            sb.append(HTTP.CHARSET_PARAM);
            sb.append(charset);
        }
        return sb.toString();
    }
}
