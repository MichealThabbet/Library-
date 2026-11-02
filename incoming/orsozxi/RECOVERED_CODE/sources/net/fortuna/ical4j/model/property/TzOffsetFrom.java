package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.ZoneOffset;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.UtcOffset;
import net.fortuna.ical4j.model.ZoneOffsetAdapter;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class TzOffsetFrom extends Property {
    private static final long serialVersionUID = 450274263165493502L;
    private ZoneOffsetAdapter offset;

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
    }

    public TzOffsetFrom() {
        super(Property.TZOFFSETFROM, new Factory());
    }

    public TzOffsetFrom(String str) {
        super(Property.TZOFFSETFROM, new Factory());
        setValue(str);
    }

    public TzOffsetFrom(ParameterList parameterList, String str) {
        super(Property.TZOFFSETFROM, parameterList, new Factory());
        setValue(str);
    }

    @Deprecated
    public TzOffsetFrom(UtcOffset utcOffset) {
        this(ZoneOffsetAdapter.from(utcOffset));
    }

    public TzOffsetFrom(ZoneOffset zoneOffset) {
        super(Property.TZOFFSETFROM, new Factory());
        this.offset = new ZoneOffsetAdapter(zoneOffset);
    }

    @Deprecated
    public TzOffsetFrom(ParameterList parameterList, UtcOffset utcOffset) {
        this(parameterList, ZoneOffsetAdapter.from(utcOffset));
    }

    public TzOffsetFrom(ParameterList parameterList, ZoneOffset zoneOffset) {
        super(Property.TZOFFSETFROM, parameterList, new Factory());
        this.offset = new ZoneOffsetAdapter(zoneOffset);
    }

    public final ZoneOffset getOffset() {
        return this.offset.getOffset();
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void setValue(String str) {
        this.offset = new ZoneOffsetAdapter(ZoneOffset.of(str));
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        ZoneOffsetAdapter zoneOffsetAdapter = this.offset;
        return zoneOffsetAdapter != null ? zoneOffsetAdapter.toString() : "";
    }

    public final void setOffset(ZoneOffset zoneOffset) {
        this.offset = new ZoneOffsetAdapter(zoneOffset);
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.TZOFFSETFROM);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new TzOffsetFrom(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new TzOffsetFrom();
        }
    }
}
