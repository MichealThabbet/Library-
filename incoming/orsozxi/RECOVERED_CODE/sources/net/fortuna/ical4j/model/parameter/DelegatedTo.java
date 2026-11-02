package net.fortuna.ical4j.model.parameter;

import java.net.URISyntaxException;
import net.fortuna.ical4j.model.AddressList;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.util.Strings;

/* loaded from: classes3.dex */
public class DelegatedTo extends Parameter {
    private static final long serialVersionUID = 567577003350648021L;
    private AddressList delegatees;

    @Override // net.fortuna.ical4j.model.Parameter
    protected boolean isQuotable() {
        return false;
    }

    public DelegatedTo(String str) throws URISyntaxException {
        this(new AddressList(Strings.unquote(str)));
    }

    public DelegatedTo(AddressList addressList) {
        super(Parameter.DELEGATED_TO, new Factory());
        this.delegatees = addressList;
    }

    public final AddressList getDelegatees() {
        return this.delegatees;
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return getDelegatees().toString();
    }

    public static class Factory extends Content.Factory implements ParameterFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Parameter.DELEGATED_TO);
        }

        @Override // net.fortuna.ical4j.model.ParameterFactory
        public Parameter createParameter(String str) throws URISyntaxException {
            return new DelegatedTo(str);
        }
    }
}
