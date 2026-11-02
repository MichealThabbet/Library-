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
public class Organizer extends Property {
    private static final long serialVersionUID = -5216965653165090725L;
    private URI calAddress;

    public Organizer() {
        super(Property.ORGANIZER, new Factory());
    }

    public Organizer(String str) throws URISyntaxException {
        super(Property.ORGANIZER, new Factory());
        setValue(str);
    }

    public Organizer(ParameterList parameterList, String str) throws URISyntaxException {
        super(Property.ORGANIZER, parameterList, new Factory());
        setValue(str);
    }

    public Organizer(URI uri) {
        super(Property.ORGANIZER, new Factory());
        this.calAddress = uri;
    }

    public Organizer(ParameterList parameterList, URI uri) {
        super(Property.ORGANIZER, parameterList, new Factory());
        this.calAddress = uri;
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void validate() throws ValidationException {
        Arrays.asList(Parameter.CN, Parameter.DIR, Parameter.SENT_BY, Parameter.LANGUAGE).forEach(new Consumer() { // from class: net.fortuna.ical4j.model.property.-$$Lambda$Organizer$v1rndh6GWj97F58mtDL2r9VVEmk
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws ValidationException {
                this.f$0.lambda$validate$0$Organizer((String) obj);
            }
        });
        ParameterValidator.assertOneOrLess(Parameter.SCHEDULE_STATUS, getParameters());
    }

    public /* synthetic */ void lambda$validate$0$Organizer(String str) throws ValidationException {
        ParameterValidator.assertOneOrLess(str, getParameters());
    }

    public final URI getCalAddress() {
        return this.calAddress;
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void setValue(String str) throws URISyntaxException {
        this.calAddress = Uris.create(str);
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return Uris.decode(Strings.valueOf(getCalAddress()));
    }

    public final void setCalAddress(URI uri) {
        this.calAddress = uri;
    }

    public static class Factory extends Content.Factory implements PropertyFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.ORGANIZER);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new Organizer(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new Organizer();
        }
    }
}
