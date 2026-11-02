package net.fortuna.ical4j.model.parameter;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;

/* loaded from: classes3.dex */
public class Email extends Parameter {
    private static final String PARAMETER_NAME = "EMAIL";
    private static final long serialVersionUID = 1;
    private final InternetAddress address;

    public Email(String str) throws AddressException {
        super(PARAMETER_NAME, new Factory());
        this.address = InternetAddress.parse(str)[0];
    }

    @Override // net.fortuna.ical4j.model.Content
    public String getValue() {
        return this.address.getAddress();
    }

    public static class Factory extends Content.Factory implements ParameterFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Email.PARAMETER_NAME);
        }

        @Override // net.fortuna.ical4j.model.ParameterFactory
        public Parameter createParameter(String str) {
            try {
                return new Email(str);
            } catch (AddressException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }
}
