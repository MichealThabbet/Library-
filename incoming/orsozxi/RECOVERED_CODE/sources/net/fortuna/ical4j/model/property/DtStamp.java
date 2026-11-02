package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Date;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;

/* loaded from: classes3.dex */
public class DtStamp extends UtcProperty implements Comparable<DtStamp> {
    private static final long serialVersionUID = 7581197869433744070L;

    public DtStamp() {
        super(Property.DTSTAMP, new Factory());
    }

    public DtStamp(String str) throws ParseException {
        this(new ParameterList(), str);
    }

    public DtStamp(ParameterList parameterList, String str) throws ParseException {
        super(Property.DTSTAMP, parameterList, new Factory());
        setValue(str);
    }

    public DtStamp(DateTime dateTime) {
        super(Property.DTSTAMP, new Factory());
        dateTime.setUtc(true);
        setDate(dateTime);
    }

    public DtStamp(ParameterList parameterList, DateTime dateTime) {
        super(Property.DTSTAMP, parameterList, new Factory());
        dateTime.setUtc(true);
        setDate(dateTime);
    }

    @Override // java.lang.Comparable
    public int compareTo(DtStamp dtStamp) {
        return getDate().compareTo((Date) dtStamp.getDate());
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.DTSTAMP);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new DtStamp(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new DtStamp();
        }
    }
}
