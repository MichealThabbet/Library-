package net.fortuna.ical4j.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ComponentFactory;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.model.ParameterFactoryRegistry;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.PropertyFactoryRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;

/* loaded from: classes3.dex */
public class CalendarBuilder implements Consumer<Calendar> {
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private Calendar calendar;
    private final ContentHandler contentHandler;
    private final CalendarParser parser;
    private final TimeZoneRegistry tzRegistry;

    public CalendarBuilder() {
        this.parser = CalendarParserFactory.getInstance().get();
        TimeZoneRegistry timeZoneRegistryCreateRegistry = TimeZoneRegistryFactory.getInstance().createRegistry();
        this.tzRegistry = timeZoneRegistryCreateRegistry;
        this.contentHandler = new DefaultContentHandler(this, timeZoneRegistryCreateRegistry);
    }

    public CalendarBuilder(CalendarParser calendarParser) {
        this.parser = calendarParser;
        TimeZoneRegistry timeZoneRegistryCreateRegistry = TimeZoneRegistryFactory.getInstance().createRegistry();
        this.tzRegistry = timeZoneRegistryCreateRegistry;
        this.contentHandler = new DefaultContentHandler(this, timeZoneRegistryCreateRegistry);
    }

    public CalendarBuilder(TimeZoneRegistry timeZoneRegistry) {
        this.parser = CalendarParserFactory.getInstance().get();
        this.tzRegistry = timeZoneRegistry;
        this.contentHandler = new DefaultContentHandler(this, timeZoneRegistry);
    }

    public CalendarBuilder(CalendarParser calendarParser, TimeZoneRegistry timeZoneRegistry) {
        this.parser = calendarParser;
        this.tzRegistry = timeZoneRegistry;
        this.contentHandler = new DefaultContentHandler(this, timeZoneRegistry);
    }

    @Deprecated
    public CalendarBuilder(CalendarParser calendarParser, PropertyFactoryRegistry propertyFactoryRegistry, ParameterFactoryRegistry parameterFactoryRegistry, TimeZoneRegistry timeZoneRegistry) {
        this(calendarParser, parameterFactoryRegistry, propertyFactoryRegistry, new DefaultComponentFactorySupplier(), timeZoneRegistry);
    }

    public CalendarBuilder(CalendarParser calendarParser, Supplier<List<ParameterFactory>> supplier, Supplier<List<PropertyFactory>> supplier2, Supplier<List<ComponentFactory>> supplier3, TimeZoneRegistry timeZoneRegistry) {
        this.parser = calendarParser;
        this.tzRegistry = timeZoneRegistry;
        this.contentHandler = new DefaultContentHandler(this, timeZoneRegistry, supplier, supplier2, supplier3);
    }

    @Override // java.util.function.Consumer
    public void accept(Calendar calendar) {
        this.calendar = calendar;
    }

    public Calendar build(InputStream inputStream) throws ParserException, IOException {
        return build(new InputStreamReader(inputStream, DEFAULT_CHARSET));
    }

    public Calendar build(Reader reader) throws ParserException, IOException {
        return build(new UnfoldingReader(reader));
    }

    public Calendar build(UnfoldingReader unfoldingReader) throws ParserException, IOException {
        this.parser.parse(unfoldingReader, this.contentHandler);
        return this.calendar;
    }

    public final TimeZoneRegistry getRegistry() {
        return this.tzRegistry;
    }
}
