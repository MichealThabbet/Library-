package net.fortuna.ical4j.data;

import java.util.function.Supplier;
import net.fortuna.ical4j.util.Configurator;

/* loaded from: classes3.dex */
public abstract class CalendarParserFactory implements Supplier<CalendarParser> {
    public static final String KEY_FACTORY_CLASS = "net.fortuna.ical4j.parser";
    private static Supplier<CalendarParser> instance = (Supplier) Configurator.getObjectProperty(KEY_FACTORY_CLASS).orElse(new Supplier() { // from class: net.fortuna.ical4j.data.-$$Lambda$CalendarParserFactory$IOv6TQzpQa8CYgXPRKKgSPKu-ls
        @Override // java.util.function.Supplier
        public final Object get() {
            return CalendarParserFactory.lambda$static$0();
        }
    });

    static /* synthetic */ CalendarParser lambda$static$0() {
        return new CalendarParserImpl();
    }

    public static Supplier<CalendarParser> getInstance() {
        return instance;
    }
}
