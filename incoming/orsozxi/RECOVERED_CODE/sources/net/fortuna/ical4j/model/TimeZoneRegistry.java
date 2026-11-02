package net.fortuna.ical4j.model;

/* loaded from: classes3.dex */
public interface TimeZoneRegistry {
    void clear();

    TimeZone getTimeZone(String str);

    void register(TimeZone timeZone);

    void register(TimeZone timeZone, boolean z);
}
