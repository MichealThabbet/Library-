package net.fortuna.ical4j.validate;

import java.util.ServiceLoader;

/* loaded from: classes3.dex */
public abstract class AbstractCalendarValidatorFactory {
    private static CalendarValidatorFactory instance = (CalendarValidatorFactory) ServiceLoader.load(CalendarValidatorFactory.class, DefaultCalendarValidatorFactory.class.getClassLoader()).iterator().next();

    public static CalendarValidatorFactory getInstance() {
        return instance;
    }
}
