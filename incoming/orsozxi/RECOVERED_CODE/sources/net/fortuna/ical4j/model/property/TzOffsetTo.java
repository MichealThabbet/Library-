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
public class TzOffsetTo extends Property {
    private static final long serialVersionUID = 8213874575051177732L;
    private ZoneOffsetAdapter offset;

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
    }

    public TzOffsetTo() {
        super(Property.TZOFFSETTO, new Factory());
    }

    public TzOffsetTo(String str) {
        super(Property.TZOFFSETTO, new Factory());
        setValue(str);
    }

    public TzOffsetTo(ParameterList parameterList, String str) {
        super(Property.TZOFFSETTO, parameterList, new Factory());
        setValue(str);
    }

    @Deprecated
    public TzOffsetTo(UtcOffset utcOffset) {
        this(ZoneOffsetAdapter.from(utcOffset));
    }

    public TzOffsetTo(ZoneOffset zoneOffset) {
        super(Property.TZOFFSETTO, new Factory());
        this.offset = new ZoneOffsetAdapter(zoneOffset);
    }

    @Deprecated
    public TzOffsetTo(ParameterList parameterList, UtcOffset utcOffset) {
        this(parameterList, ZoneOffsetAdapter.from(utcOffset));
    }

    public TzOffsetTo(ParameterList parameterList, ZoneOffset zoneOffset) {
        super(Property.TZOFFSETTO, parameterList, new Factory());
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
            super(Property.TZOFFSETTO);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new TzOffsetTo(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new TzOffsetTo();
        }
    }
}
