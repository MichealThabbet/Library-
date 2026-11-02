package net.fortuna.ical4j.util;

import net.fortuna.ical4j.model.component.VTimeZone;

/* loaded from: classes3.dex */
public interface TimeZoneCache {
    void clear();

    boolean containsId(String str);

    VTimeZone getTimezone(String str);

    boolean putIfAbsent(String str, VTimeZone vTimeZone);
}
