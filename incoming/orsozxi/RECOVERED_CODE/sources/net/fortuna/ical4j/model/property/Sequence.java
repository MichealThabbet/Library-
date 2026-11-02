package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class Sequence extends Property implements Comparable<Sequence> {
    private static final long serialVersionUID = -1606972893204822853L;
    private int sequenceNo;

    @Override // net.fortuna.ical4j.model.Property
    public void validate() throws ValidationException {
    }

    public Sequence() {
        super(Property.SEQUENCE, new Factory());
        this.sequenceNo = 0;
    }

    public Sequence(String str) {
        super(Property.SEQUENCE, new Factory());
        setValue(str);
    }

    public Sequence(ParameterList parameterList, String str) {
        super(Property.SEQUENCE, parameterList, new Factory());
        setValue(str);
    }

    public Sequence(int i) {
        super(Property.SEQUENCE, new Factory());
        this.sequenceNo = i;
    }

    public Sequence(ParameterList parameterList, int i) {
        super(Property.SEQUENCE, parameterList, new Factory());
        this.sequenceNo = i;
    }

    public final int getSequenceNo() {
        return this.sequenceNo;
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void setValue(String str) {
        this.sequenceNo = Integer.parseInt(str);
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return String.valueOf(getSequenceNo());
    }

    @Override // java.lang.Comparable
    public int compareTo(Sequence sequence) {
        return Integer.compare(getSequenceNo(), sequence.getSequenceNo());
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.SEQUENCE);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new Sequence(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new Sequence();
        }
    }
}
