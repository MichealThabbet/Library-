package net.fortuna.ical4j.model;

import com.facebook.internal.ServerProtocol;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalAmount;
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneOffsetTransitionRule;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Supplier;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.TimeZoneLoader;
import net.fortuna.ical4j.model.component.Daylight;
import net.fortuna.ical4j.model.component.Standard;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.RDate;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.TzId;
import net.fortuna.ical4j.model.property.TzOffsetFrom;
import net.fortuna.ical4j.model.property.TzOffsetTo;
import net.fortuna.ical4j.model.property.TzUrl;
import net.fortuna.ical4j.util.Configurator;
import net.fortuna.ical4j.util.ResourceLoader;
import net.fortuna.ical4j.util.TimeZoneCache;
import org.apache.commons.lang3.Validate;
import org.slf4j.LoggerFactory;

/* loaded from: classes3.dex */
public class TimeZoneLoader {
    private static final String DATE_TIME_TPL = "yyyyMMdd'T'HHmmss";
    private static final String DEFAULT_TZ_CACHE_IMPL = "net.fortuna.ical4j.util.JCacheTimeZoneCache";
    private static final String MESSAGE_MISSING_DEFAULT_TZ_CACHE_IMPL = "Error loading default cache implementation. Please ensure the JCache API dependency is included in the classpath, or override the cache implementation (e.g. via configuration: net.fortuna.ical4j.timezone.cache.impl=net.fortuna.ical4j.util.MapTimeZoneCache)";
    private static final Standard NO_TRANSITIONS;
    private static final String RRULE_TPL = "FREQ=YEARLY;BYMONTH=%d;BYDAY=%d%s";
    private static final Set<String> TIMEZONE_DEFINITIONS;
    private static final String TZ_CACHE_IMPL = "net.fortuna.ical4j.timezone.cache.impl";
    private static final String UPDATE_CONNECT_TIMEOUT = "net.fortuna.ical4j.timezone.update.timeout.connect";
    private static final String UPDATE_ENABLED = "net.fortuna.ical4j.timezone.update.enabled";
    private static final String UPDATE_PROXY_ENABLED = "net.fortuna.ical4j.timezone.update.proxy.enabled";
    private static final String UPDATE_PROXY_HOST = "net.fortuna.ical4j.timezone.update.proxy.host";
    private static final String UPDATE_PROXY_PORT = "net.fortuna.ical4j.timezone.update.proxy.port";
    private static final String UPDATE_PROXY_TYPE = "net.fortuna.ical4j.timezone.update.proxy.type";
    private static final String UPDATE_READ_TIMEOUT = "net.fortuna.ical4j.timezone.update.timeout.read";
    private static Proxy proxy;
    private final TimeZoneCache cache;
    private final String resourcePrefix;

    static {
        HashSet hashSet = new HashSet();
        TIMEZONE_DEFINITIONS = hashSet;
        hashSet.addAll(Arrays.asList(TimeZone.getAvailableIDs()));
        NO_TRANSITIONS = new Standard();
        TzOffsetFrom tzOffsetFrom = new TzOffsetFrom(ZoneOffset.UTC);
        TzOffsetTo tzOffsetTo = new TzOffsetTo(ZoneOffset.UTC);
        NO_TRANSITIONS.getProperties().add((PropertyList<Property>) tzOffsetFrom);
        NO_TRANSITIONS.getProperties().add((PropertyList<Property>) tzOffsetTo);
        DtStart dtStart = new DtStart();
        dtStart.setDate(new DateTime(0L));
        NO_TRANSITIONS.getProperties().add((PropertyList<Property>) dtStart);
        try {
            if (ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(Configurator.getProperty(UPDATE_PROXY_ENABLED).orElse("false"))) {
                proxy = new Proxy((Proxy.Type) Configurator.getEnumProperty(Proxy.Type.class, UPDATE_PROXY_TYPE).orElse(Proxy.Type.DIRECT), new InetSocketAddress(Configurator.getProperty(UPDATE_PROXY_HOST).orElse(""), Configurator.getIntProperty(UPDATE_PROXY_PORT).orElse(-1).intValue()));
            }
        } catch (Throwable th) {
            LoggerFactory.getLogger((Class<?>) TimeZoneLoader.class).warn("Error loading proxy server configuration: " + th.getMessage());
        }
    }

    public TimeZoneLoader(String str) {
        this(str, cacheInit());
    }

    public TimeZoneLoader(String str, TimeZoneCache timeZoneCache) {
        this.resourcePrefix = str;
        this.cache = timeZoneCache;
    }

    public VTimeZone loadVTimeZone(String str) throws ParserException, IOException, ParseException {
        Validate.notBlank(str, "Invalid TimeZone ID: [%s]", str);
        if (!this.cache.containsId(str)) {
            URL resource = ResourceLoader.getResource(this.resourcePrefix + str + ".ics");
            if (resource != null) {
                InputStream inputStreamOpenStream = resource.openStream();
                try {
                    VTimeZone vTimeZone = (VTimeZone) new CalendarBuilder().build(inputStreamOpenStream).getComponent(Component.VTIMEZONE);
                    if (!"false".equals(Configurator.getProperty(UPDATE_ENABLED).orElse(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE))) {
                        VTimeZone vTimeZoneUpdateDefinition = updateDefinition(vTimeZone);
                        if (inputStreamOpenStream != null) {
                            inputStreamOpenStream.close();
                        }
                        return vTimeZoneUpdateDefinition;
                    }
                    if (vTimeZone != null) {
                        this.cache.putIfAbsent(str, vTimeZone);
                    }
                    if (inputStreamOpenStream != null) {
                        inputStreamOpenStream.close();
                    }
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        if (inputStreamOpenStream != null) {
                            try {
                                inputStreamOpenStream.close();
                            } catch (Throwable th3) {
                                th.addSuppressed(th3);
                            }
                        }
                        throw th2;
                    }
                }
            } else {
                return generateTimezoneForId(str);
            }
        }
        return this.cache.getTimezone(str);
    }

    private VTimeZone updateDefinition(VTimeZone vTimeZone) throws ParserException, IOException {
        URLConnection uRLConnectionOpenConnection;
        Proxy proxy2;
        TzUrl timeZoneUrl = vTimeZone.getTimeZoneUrl();
        if (timeZoneUrl != null) {
            int iIntValue = Configurator.getIntProperty(UPDATE_CONNECT_TIMEOUT).orElse(0).intValue();
            int iIntValue2 = Configurator.getIntProperty(UPDATE_READ_TIMEOUT).orElse(0).intValue();
            URL url = timeZoneUrl.getUri().toURL();
            if (ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(Configurator.getProperty(UPDATE_PROXY_ENABLED).orElse("false")) && (proxy2 = proxy) != null) {
                uRLConnectionOpenConnection = url.openConnection(proxy2);
            } else {
                uRLConnectionOpenConnection = url.openConnection();
            }
            uRLConnectionOpenConnection.setConnectTimeout(iIntValue);
            uRLConnectionOpenConnection.setReadTimeout(iIntValue2);
            VTimeZone vTimeZone2 = (VTimeZone) new CalendarBuilder().build(uRLConnectionOpenConnection.getInputStream()).getComponent(Component.VTIMEZONE);
            if (vTimeZone2 != null) {
                return vTimeZone2;
            }
        }
        return vTimeZone;
    }

    private static VTimeZone generateTimezoneForId(String str) throws ParseException {
        if (!TIMEZONE_DEFINITIONS.contains(str)) {
            return null;
        }
        java.util.TimeZone timeZone = java.util.TimeZone.getTimeZone(str);
        ZoneId zoneIdOf = ZoneId.of(timeZone.getID(), ZoneId.SHORT_IDS);
        int rawOffset = timeZone.getRawOffset() / 1000;
        VTimeZone vTimeZone = new VTimeZone();
        vTimeZone.getProperties().add((PropertyList<Property>) new TzId(str));
        addTransitions(zoneIdOf, vTimeZone, rawOffset);
        addTransitionRules(zoneIdOf, rawOffset, vTimeZone);
        if (vTimeZone.getObservances() == null || vTimeZone.getObservances().isEmpty()) {
            vTimeZone.getObservances().add(NO_TRANSITIONS);
        }
        return vTimeZone;
    }

    private static void addTransitionRules(ZoneId zoneId, int i, VTimeZone vTimeZone) {
        if (!zoneId.getRules().getTransitions().isEmpty()) {
            Collections.min(zoneId.getRules().getTransitions(), Comparator.comparing(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$4mVp1UBYR9dSs1xypCxsQ1JL_-g
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((ZoneOffsetTransition) obj).getDateTimeBefore();
                }
            }));
        }
        LocalDateTime localDateTimeNow = LocalDateTime.now(zoneId);
        for (ZoneOffsetTransitionRule zoneOffsetTransitionRule : zoneId.getRules().getTransitionRules()) {
            int value = zoneOffsetTransitionRule.getMonth().getValue();
            DayOfWeek dayOfWeek = zoneOffsetTransitionRule.getDayOfWeek();
            LocalDateTime localDateTimeWith = LocalDateTime.now(zoneId).with(TemporalAdjusters.firstInMonth(dayOfWeek)).withMonth(value).with((TemporalAdjuster) zoneOffsetTransitionRule.getLocalTime());
            Month month = localDateTimeWith.getMonth();
            TreeSet treeSet = new TreeSet();
            do {
                treeSet.add(Integer.valueOf(localDateTimeWith.getDayOfMonth()));
                localDateTimeWith = localDateTimeWith.plus((TemporalAmount) java.time.Period.ofWeeks(1));
            } while (localDateTimeWith.getMonth() == month);
            Integer num = (Integer) treeSet.ceiling(Integer.valueOf(zoneOffsetTransitionRule.getDayOfMonthIndicator()));
            if (num == null) {
                num = (Integer) treeSet.last();
            }
            Iterator it2 = treeSet.iterator();
            int size = 0;
            while (it2.hasNext() && it2.next() != num) {
                size++;
            }
            if (size >= 3) {
                size -= treeSet.size();
            }
            String str = String.format(RRULE_TPL, Integer.valueOf(value), Integer.valueOf(size), dayOfWeek.name().substring(0, 2));
            try {
                TzOffsetFrom tzOffsetFrom = new TzOffsetFrom(zoneOffsetTransitionRule.getOffsetBefore());
                TzOffsetTo tzOffsetTo = new TzOffsetTo(zoneOffsetTransitionRule.getOffsetAfter());
                RRule rRule = new RRule(str);
                Component daylight = zoneOffsetTransitionRule.getOffsetAfter().getTotalSeconds() > i ? new Daylight() : new Standard();
                daylight.getProperties().add((PropertyList<Property>) tzOffsetFrom);
                daylight.getProperties().add((PropertyList<Property>) tzOffsetTo);
                daylight.getProperties().add((PropertyList<Property>) rRule);
                daylight.getProperties().add((PropertyList<Property>) new DtStart(localDateTimeNow.withMonth(zoneOffsetTransitionRule.getMonth().getValue()).withDayOfMonth(zoneOffsetTransitionRule.getDayOfMonthIndicator()).with((TemporalAdjuster) zoneOffsetTransitionRule.getDayOfWeek()).format(DateTimeFormatter.ofPattern(DATE_TIME_TPL))));
                vTimeZone.getObservances().add(daylight);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void addTransitions(ZoneId zoneId, VTimeZone vTimeZone, int i) throws ParseException {
        HashMap map = new HashMap();
        for (ZoneOffsetTransition zoneOffsetTransition : zoneId.getRules().getTransitions()) {
            ((Set) map.computeIfAbsent(ZoneOffsetKey.of(zoneOffsetTransition.getOffsetBefore(), zoneOffsetTransition.getOffsetAfter()), new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$TimeZoneLoader$WGCDQdnQ99lTyY7stK0AcHMjHEU
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return TimeZoneLoader.lambda$addTransitions$0((TimeZoneLoader.ZoneOffsetKey) obj);
                }
            })).add(zoneOffsetTransition);
        }
        for (Map.Entry entry : map.entrySet()) {
            Component daylight = ((ZoneOffsetKey) entry.getKey()).offsetAfter.getTotalSeconds() > i ? new Daylight() : new Standard();
            DtStart dtStart = new DtStart(((ZoneOffsetTransition) Collections.min((Collection) entry.getValue())).getDateTimeBefore().format(DateTimeFormatter.ofPattern(DATE_TIME_TPL)));
            TzOffsetFrom tzOffsetFrom = new TzOffsetFrom(((ZoneOffsetKey) entry.getKey()).offsetBefore);
            TzOffsetTo tzOffsetTo = new TzOffsetTo(((ZoneOffsetKey) entry.getKey()).offsetAfter);
            daylight.getProperties().add((PropertyList<Property>) dtStart);
            daylight.getProperties().add((PropertyList<Property>) tzOffsetFrom);
            daylight.getProperties().add((PropertyList<Property>) tzOffsetTo);
            Iterator it2 = ((Set) entry.getValue()).iterator();
            while (it2.hasNext()) {
                daylight.getProperties().add((PropertyList<Property>) new RDate(new ParameterList(), ((ZoneOffsetTransition) it2.next()).getDateTimeBefore().format(DateTimeFormatter.ofPattern(DATE_TIME_TPL))));
            }
            vTimeZone.getObservances().add(daylight);
        }
    }

    static /* synthetic */ Set lambda$addTransitions$0(ZoneOffsetKey zoneOffsetKey) {
        return new HashSet(1);
    }

    private static TimeZoneCache cacheInit() {
        return (TimeZoneCache) Configurator.getObjectProperty(TZ_CACHE_IMPL).orElseGet(new Supplier() { // from class: net.fortuna.ical4j.model.-$$Lambda$TimeZoneLoader$ftmso0dr088qHhNxsFZXAqZ7GqE
            @Override // java.util.function.Supplier
            public final Object get() {
                return TimeZoneLoader.lambda$cacheInit$1();
            }
        });
    }

    static /* synthetic */ TimeZoneCache lambda$cacheInit$1() {
        try {
            return (TimeZoneCache) Class.forName(DEFAULT_TZ_CACHE_IMPL).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoClassDefFoundError e) {
            throw new RuntimeException(MESSAGE_MISSING_DEFAULT_TZ_CACHE_IMPL, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ZoneOffsetKey {
        private final ZoneOffset offsetAfter;
        private final ZoneOffset offsetBefore;

        private ZoneOffsetKey(ZoneOffset zoneOffset, ZoneOffset zoneOffset2) {
            this.offsetBefore = zoneOffset;
            this.offsetAfter = zoneOffset2;
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return true;
            }
            if (!(obj instanceof ZoneOffsetKey)) {
                return false;
            }
            ZoneOffsetKey zoneOffsetKey = (ZoneOffsetKey) obj;
            return Objects.equals(this.offsetBefore, zoneOffsetKey.offsetBefore) && Objects.equals(this.offsetAfter, zoneOffsetKey.offsetAfter);
        }

        public int hashCode() {
            ZoneOffset zoneOffset = this.offsetBefore;
            int iHashCode = (zoneOffset == null ? 1 : zoneOffset.hashCode()) * 31;
            ZoneOffset zoneOffset2 = this.offsetAfter;
            return iHashCode * (zoneOffset2 != null ? zoneOffset2.hashCode() : 1);
        }

        static ZoneOffsetKey of(ZoneOffset zoneOffset, ZoneOffset zoneOffset2) {
            return new ZoneOffsetKey(zoneOffset, zoneOffset2);
        }
    }
}
