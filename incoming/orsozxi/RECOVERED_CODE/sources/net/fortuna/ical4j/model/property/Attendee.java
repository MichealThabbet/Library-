package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.function.Consumer;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.util.Uris;
import net.fortuna.ical4j.validate.ParameterValidator;
import net.fortuna.ical4j.validate.ValidationException;

/* loaded from: classes3.dex */
public class Attendee extends Property {
    private static final long serialVersionUID = 8430929418723298803L;
    private URI calAddress;

    public Attendee() {
        super(Property.ATTENDEE, new Factory());
    }

    public Attendee(String str) throws URISyntaxException {
        super(Property.ATTENDEE, new Factory());
        setValue(str);
    }

    public Attendee(ParameterList parameterList, String str) throws URISyntaxException {
        super(Property.ATTENDEE, parameterList, new Factory());
        setValue(str);
    }

    public Attendee(URI uri) {
        super(Property.ATTENDEE, new Factory());
        this.calAddress = uri;
    }

    public Attendee(ParameterList parameterList, URI uri) {
        super(Property.ATTENDEE, parameterList, new Factory());
        this.calAddress = uri;
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void setValue(String str) throws URISyntaxException {
        this.calAddress = Uris.create(str);
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void validate() throws ValidationException {
        Arrays.asList(Parameter.CUTYPE, Parameter.MEMBER, Parameter.ROLE, Parameter.PARTSTAT, Parameter.RSVP, Parameter.DELEGATED_TO, Parameter.DELEGATED_FROM, Parameter.SENT_BY, Parameter.CN, Parameter.DIR, Parameter.LANGUAGE).forEach(new Consumer() { // from class: net.fortuna.ical4j.model.property.-$$Lambda$Attendee$b-kw45k4e2eBde4_AlhB12rxCy8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws ValidationException {
                this.f$0.lambda$validate$0$Attendee((String) obj);
            }
        });
        ParameterValidator.assertOneOrLess(Parameter.SCHEDULE_AGENT, getParameters());
        ParameterValidator.assertOneOrLess(Parameter.SCHEDULE_STATUS, getParameters());
    }

    public /* synthetic */ void lambda$validate$0$Attendee(String str) throws ValidationException {
        ParameterValidator.assertOneOrLess(str, getParameters());
    }

    public final URI getCalAddress() {
        return this.calAddress;
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return Uris.decode(Strings.valueOf(getCalAddress()));
    }

    public final void setCalAddress(URI uri) {
        this.calAddress = uri;
    }

    @Override // net.fortuna.ical4j.model.Property
    public final Property copy() {
        return new Attendee(new ParameterList(getParameters(), false), this.calAddress);
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.ATTENDEE);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new Attendee(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new Attendee();
        }
    }
}
