package net.fortuna.ical4j.model.property;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.parameter.Encoding;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.util.DecoderFactory;
import net.fortuna.ical4j.util.EncoderFactory;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.util.Uris;
import net.fortuna.ical4j.validate.ParameterValidator;
import net.fortuna.ical4j.validate.ValidationException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.slf4j.LoggerFactory;

/* loaded from: classes3.dex */
public class Attach extends Property {
    private static final long serialVersionUID = 4439949507756383452L;
    private byte[] binary;
    private URI uri;

    public Attach() {
        super(Property.ATTACH, new Factory());
    }

    public Attach(ParameterList parameterList, String str) throws URISyntaxException {
        super(Property.ATTACH, parameterList, new Factory());
        setValue(str);
    }

    public Attach(byte[] bArr) {
        super(Property.ATTACH, new Factory());
        getParameters().add(Encoding.BASE64);
        getParameters().add(Value.BINARY);
        this.binary = bArr;
    }

    public Attach(ParameterList parameterList, byte[] bArr) {
        super(Property.ATTACH, parameterList, new Factory());
        this.binary = bArr;
    }

    public Attach(URI uri) {
        super(Property.ATTACH, new Factory());
        this.uri = uri;
    }

    public Attach(ParameterList parameterList, URI uri) {
        super(Property.ATTACH, parameterList, new Factory());
        this.uri = uri;
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void validate() throws ValidationException {
        ParameterValidator.assertOneOrLess(Parameter.FMTTYPE, getParameters());
        if (Value.BINARY.equals(getParameter(Parameter.VALUE))) {
            ParameterValidator.assertOne(Parameter.ENCODING, getParameters());
            if (!Encoding.BASE64.equals(getParameter(Parameter.ENCODING))) {
                throw new ValidationException("If the value type parameter is [BINARY], the inlineencoding parameter MUST be specified with the value [BASE64]");
            }
        }
    }

    public final byte[] getBinary() {
        return this.binary;
    }

    public final URI getUri() {
        return this.uri;
    }

    @Override // net.fortuna.ical4j.model.Property
    public final void setValue(String str) throws URISyntaxException {
        if (getParameter(Parameter.ENCODING) != null) {
            try {
                this.binary = DecoderFactory.getInstance().createBinaryDecoder((Encoding) getParameter(Parameter.ENCODING)).decode(str.getBytes());
                return;
            } catch (UnsupportedEncodingException e) {
                LoggerFactory.getLogger((Class<?>) Attach.class).error("Error encoding binary data", (Throwable) e);
                return;
            } catch (DecoderException e2) {
                LoggerFactory.getLogger((Class<?>) Attach.class).error("Error decoding binary data", (Throwable) e2);
                return;
            }
        }
        this.uri = Uris.create(str);
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        if (getUri() != null) {
            return Uris.decode(Strings.valueOf(getUri()));
        }
        if (getBinary() == null) {
            return null;
        }
        try {
            return new String(EncoderFactory.getInstance().createBinaryEncoder((Encoding) getParameter(Parameter.ENCODING)).encode(getBinary()));
        } catch (UnsupportedEncodingException | EncoderException e) {
            LoggerFactory.getLogger((Class<?>) Attach.class).error("Error encoding binary data", e);
            return null;
        }
    }

    public final void setBinary(byte[] bArr) {
        this.binary = bArr;
        this.uri = null;
    }

    public final void setUri(URI uri) {
        this.uri = uri;
        this.binary = null;
    }

    public static class Factory extends Content.Factory implements PropertyFactory<Property> {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Property.ATTACH);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty(ParameterList parameterList, String str) throws URISyntaxException, IOException, ParseException {
            return new Attach(parameterList, str);
        }

        @Override // net.fortuna.ical4j.model.PropertyFactory
        public Property createProperty() {
            return new Attach();
        }
    }
}
