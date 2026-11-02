package net.fortuna.ical4j.agent;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.CalendarComponent;

/* loaded from: classes3.dex */
public interface UserAgent<T extends CalendarComponent> {
    Calendar add(T t);

    Calendar cancel(T... tArr);

    Calendar counter(Calendar calendar);

    Calendar declineCounter(Calendar calendar);

    Calendar delegate(Calendar calendar);

    Calendar publish(T... tArr);

    Calendar refresh(T t);

    Calendar reply(Calendar calendar);

    Calendar request(T... tArr);
}
