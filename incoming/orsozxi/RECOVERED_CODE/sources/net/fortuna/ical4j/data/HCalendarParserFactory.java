package net.fortuna.ical4j.data;

/* loaded from: classes3.dex */
public class HCalendarParserFactory extends CalendarParserFactory {
    @Override // java.util.function.Supplier
    public CalendarParser get() {
        return new HCalendarParser();
    }
}
