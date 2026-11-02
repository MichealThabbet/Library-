package net.fortuna.ical4j.model;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.util.CompatibilityHints;
import org.apache.commons.lang3.Validate;
import org.slf4j.LoggerFactory;

/* loaded from: classes3.dex */
public class TimeZoneRegistryImpl implements TimeZoneRegistry {
    private static final String DEFAULT_RESOURCE_PREFIX = "zoneinfo/";
    private final TimeZoneLoader timeZoneLoader;
    private Map<String, TimeZone> timezones;
    private static final Pattern TZ_ID_SUFFIX = Pattern.compile("(?<=/)[^/]*/[^/]*$");
    private static final Map<String, TimeZone> DEFAULT_TIMEZONES = new ConcurrentHashMap();
    private static final Properties ALIASES = new Properties();

    /* JADX WARN: Removed duplicated region for block: B:37:0x00d8 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0086 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    static {
        /*
            Method dump skipped, instructions count: 285
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: net.fortuna.ical4j.model.TimeZoneRegistryImpl.<clinit>():void");
    }

    public TimeZoneRegistryImpl() {
        this(DEFAULT_RESOURCE_PREFIX);
    }

    public TimeZoneRegistryImpl(String str) {
        this.timeZoneLoader = new TimeZoneLoader(str);
        this.timezones = new ConcurrentHashMap();
    }

    @Override // net.fortuna.ical4j.model.TimeZoneRegistry
    public final void register(TimeZone timeZone) {
        register(timeZone, false);
    }

    @Override // net.fortuna.ical4j.model.TimeZoneRegistry
    public final void register(TimeZone timeZone, boolean z) {
        if (z) {
            try {
                this.timezones.put(timeZone.getID(), new TimeZone(this.timeZoneLoader.loadVTimeZone(timeZone.getID())));
                return;
            } catch (IOException | ParseException | ParserException e) {
                LoggerFactory.getLogger((Class<?>) TimeZoneRegistryImpl.class).warn("Error occurred loading VTimeZone", e);
                return;
            }
        }
        this.timezones.put(timeZone.getID(), timeZone);
    }

    @Override // net.fortuna.ical4j.model.TimeZoneRegistry
    public final void clear() {
        this.timezones.clear();
    }

    @Override // net.fortuna.ical4j.model.TimeZoneRegistry
    public final TimeZone getTimeZone(String str) {
        Throwable e;
        TimeZone timeZone;
        VTimeZone vTimeZoneLoadVTimeZone;
        Validate.notBlank(str, "Invalid TimeZone ID: [%s]", str);
        TimeZone timeZone2 = this.timezones.get(str);
        if (timeZone2 == null && (timeZone2 = DEFAULT_TIMEZONES.get(str)) == null) {
            String property = ALIASES.getProperty(str);
            if (property != null) {
                return getTimeZone(property);
            }
            synchronized (DEFAULT_TIMEZONES) {
                timeZone2 = DEFAULT_TIMEZONES.get(str);
                if (timeZone2 == null) {
                    try {
                        vTimeZoneLoadVTimeZone = this.timeZoneLoader.loadVTimeZone(str);
                    } catch (IOException e2) {
                        e = e2;
                        e = e;
                        timeZone = timeZone2;
                        LoggerFactory.getLogger((Class<?>) TimeZoneRegistryImpl.class).warn("Error occurred loading VTimeZone", e);
                        timeZone2 = timeZone;
                        return timeZone2;
                    } catch (ParseException e3) {
                        e = e3;
                        e = e;
                        timeZone = timeZone2;
                        LoggerFactory.getLogger((Class<?>) TimeZoneRegistryImpl.class).warn("Error occurred loading VTimeZone", e);
                        timeZone2 = timeZone;
                        return timeZone2;
                    } catch (ParserException e4) {
                        e = e4;
                        e = e;
                        timeZone = timeZone2;
                        LoggerFactory.getLogger((Class<?>) TimeZoneRegistryImpl.class).warn("Error occurred loading VTimeZone", e);
                        timeZone2 = timeZone;
                        return timeZone2;
                    }
                    if (vTimeZoneLoadVTimeZone != null) {
                        timeZone = new TimeZone(vTimeZoneLoadVTimeZone);
                        try {
                            DEFAULT_TIMEZONES.put(timeZone.getID(), timeZone);
                        } catch (IOException e5) {
                            e = e5;
                            LoggerFactory.getLogger((Class<?>) TimeZoneRegistryImpl.class).warn("Error occurred loading VTimeZone", e);
                            timeZone2 = timeZone;
                            return timeZone2;
                        } catch (ParseException e6) {
                            e = e6;
                            LoggerFactory.getLogger((Class<?>) TimeZoneRegistryImpl.class).warn("Error occurred loading VTimeZone", e);
                            timeZone2 = timeZone;
                            return timeZone2;
                        } catch (ParserException e7) {
                            e = e7;
                            LoggerFactory.getLogger((Class<?>) TimeZoneRegistryImpl.class).warn("Error occurred loading VTimeZone", e);
                            timeZone2 = timeZone;
                            return timeZone2;
                        }
                        timeZone2 = timeZone;
                    } else if (CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING)) {
                        Matcher matcher = TZ_ID_SUFFIX.matcher(str);
                        if (matcher.find()) {
                            return getTimeZone(matcher.group());
                        }
                    }
                }
            }
        }
        return timeZone2;
    }
}
