package net.fortuna.ical4j.data;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

/* loaded from: classes3.dex */
public interface ContentHandler {
    void endCalendar() throws IOException;

    void endComponent(String str);

    void endProperty(String str) throws URISyntaxException, IOException, ParseException;

    void parameter(String str, String str2) throws URISyntaxException;

    void propertyValue(String str) throws URISyntaxException, IOException, ParseException;

    void startCalendar();

    void startComponent(String str);

    void startProperty(String str);
}
