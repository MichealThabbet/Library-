package net.fortuna.ical4j.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/* loaded from: classes3.dex */
public interface CalendarParser {
    void parse(InputStream inputStream, ContentHandler contentHandler) throws ParserException, IOException;

    void parse(Reader reader, ContentHandler contentHandler) throws ParserException, IOException;
}
