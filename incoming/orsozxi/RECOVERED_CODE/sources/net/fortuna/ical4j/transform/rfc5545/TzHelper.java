package net.fortuna.ical4j.transform.rfc5545;

import com.huawei.hms.framework.common.ContainerUtils;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import net.fortuna.ical4j.model.DefaultTimeZoneRegistryFactory;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.parameter.TzId;
import net.fortuna.ical4j.model.property.DateProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: classes3.dex */
class TzHelper {
    private static final String MS_TIMEZONES_FILE = "msTimezones";
    private static final Map<String, String> MS_TIMEZONE_IDS = new HashMap();
    private static final Map<String, String> MS_TIMEZONE_NAMES = new HashMap();
    private static final TimeZoneRegistry TIMEZONE_REGISTRY = DefaultTimeZoneRegistryFactory.getInstance().createRegistry();
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) TzHelper.class);

    TzHelper() {
    }

    static {
        initMsTimezones();
    }

    private static void initMsTimezones() {
        try {
            Scanner scanner = new Scanner(TzHelper.class.getResourceAsStream(MS_TIMEZONES_FILE));
            while (scanner.hasNext()) {
                try {
                    String[] strArrSplit = scanner.nextLine().split(ContainerUtils.KEY_VALUE_DELIMITER);
                    String str = strArrSplit[1];
                    String[] strArrSplit2 = strArrSplit[0].split(";");
                    MS_TIMEZONE_NAMES.put(strArrSplit2[0], str);
                    MS_TIMEZONE_IDS.put(strArrSplit2[1], str);
                } finally {
                }
            }
            scanner.close();
        } catch (RuntimeException e) {
            LOG.error("Could not load MS timezones", (Throwable) e);
            throw new RuntimeException("Unable to load resource file msTimezones", e);
        }
    }

    static void correctTzParameterFrom(Property property) {
        if (property.getParameter("TZID") != null) {
            correctTzParameter(property, getCorrectedTimezoneFromTzParameter(property));
        }
    }

    static void correctTzParameterFrom(DateProperty dateProperty) {
        if (dateProperty.getValue() != null && dateProperty.getValue().endsWith("Z")) {
            dateProperty.getParameters().removeAll("TZID");
            return;
        }
        if (dateProperty.getParameter("TZID") != null) {
            String correctedTimezoneFromTzParameter = getCorrectedTimezoneFromTzParameter(dateProperty);
            String value = dateProperty.getValue();
            correctTzParameter(dateProperty, correctedTimezoneFromTzParameter);
            if (correctedTimezoneFromTzParameter != null) {
                dateProperty.setTimeZone(TIMEZONE_REGISTRY.getTimeZone(correctedTimezoneFromTzParameter));
                try {
                    dateProperty.setValue(value);
                    return;
                } catch (ParseException e) {
                    LOG.warn("Failed to reset property value", (Throwable) e);
                    return;
                }
            }
            dateProperty.setUtc(true);
        }
    }

    private static void correctTzParameter(Property property, String str) {
        property.getParameters().removeAll("TZID");
        if (str != null) {
            property.getParameters().add(new TzId(str));
        }
    }

    private static String getCorrectedTimezoneFromTzParameter(Property property) {
        return getCorrectedTimeZoneIdFrom(property.getParameter("TZID").getValue());
    }

    static void correctTzValueOf(net.fortuna.ical4j.model.property.TzId tzId) {
        String correctedTimeZoneIdFrom = getCorrectedTimeZoneIdFrom(tzId.getValue());
        if (correctedTimeZoneIdFrom != null) {
            tzId.setValue(correctedTimeZoneIdFrom);
        }
    }

    private static String getCorrectedTimeZoneIdFrom(String str) {
        if (str == null) {
            return null;
        }
        if (str.contains("\"")) {
            str = str.replaceAll("\"", "");
        }
        if (TIMEZONE_REGISTRY.getTimeZone(str) != null) {
            return TIMEZONE_REGISTRY.getTimeZone(str).getID();
        }
        String str2 = MS_TIMEZONE_NAMES.get(str);
        if (str2 != null) {
            return TIMEZONE_REGISTRY.getTimeZone(str2) != null ? TIMEZONE_REGISTRY.getTimeZone(str2).getID() : str2;
        }
        return MS_TIMEZONE_IDS.get(str);
    }
}
