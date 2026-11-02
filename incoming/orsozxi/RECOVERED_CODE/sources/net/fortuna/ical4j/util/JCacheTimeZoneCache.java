package net.fortuna.ical4j.util;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import net.fortuna.ical4j.model.component.VTimeZone;

/* loaded from: classes3.dex */
public class JCacheTimeZoneCache implements TimeZoneCache {
    private final Cache<String, VTimeZone> jcacheCache;

    public JCacheTimeZoneCache() {
        CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();
        Cache<String, VTimeZone> cache = cacheManager.getCache("ical4j.timezones", String.class, VTimeZone.class);
        if (cache == null) {
            synchronized (JCacheTimeZoneCache.class) {
                cache = cacheManager.getCache("ical4j.timezones", String.class, VTimeZone.class);
                if (cache == null) {
                    MutableConfiguration mutableConfiguration = new MutableConfiguration();
                    mutableConfiguration.setTypes(String.class, VTimeZone.class);
                    cache = cacheManager.createCache("ical4j.timezones", mutableConfiguration);
                }
            }
        }
        this.jcacheCache = cache;
    }

    @Override // net.fortuna.ical4j.util.TimeZoneCache
    public VTimeZone getTimezone(String str) {
        return (VTimeZone) this.jcacheCache.get(str);
    }

    @Override // net.fortuna.ical4j.util.TimeZoneCache
    public boolean putIfAbsent(String str, VTimeZone vTimeZone) {
        return this.jcacheCache.putIfAbsent(str, vTimeZone);
    }

    @Override // net.fortuna.ical4j.util.TimeZoneCache
    public boolean containsId(String str) {
        return this.jcacheCache.containsKey(str);
    }

    @Override // net.fortuna.ical4j.util.TimeZoneCache
    public void clear() {
        this.jcacheCache.clear();
    }
}
