package net.fortuna.ical4j.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.fortuna.ical4j.model.component.VTimeZone;

/* loaded from: classes3.dex */
public class MapTimeZoneCache implements TimeZoneCache {
    private final Map<String, VTimeZone> mapCache = new ConcurrentHashMap();

    @Override // net.fortuna.ical4j.util.TimeZoneCache
    public VTimeZone getTimezone(String str) {
        return this.mapCache.get(str);
    }

    @Override // net.fortuna.ical4j.util.TimeZoneCache
    public boolean putIfAbsent(String str, VTimeZone vTimeZone) {
        if (this.mapCache.get(str) != null) {
            return false;
        }
        this.mapCache.put(str, vTimeZone);
        return true;
    }

    @Override // net.fortuna.ical4j.util.TimeZoneCache
    public boolean containsId(String str) {
        return this.mapCache.containsKey(str);
    }

    @Override // net.fortuna.ical4j.util.TimeZoneCache
    public void clear() {
        this.mapCache.clear();
    }
}
