package net.fortuna.ical4j.model.parameter;

import java.net.URISyntaxException;
import net.fortuna.ical4j.model.AddressList;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.util.Strings;

/* loaded from: classes3.dex */
public class DelegatedFrom extends Parameter {
    private static final long serialVersionUID = -795956139235258568L;
    private AddressList delegators;

    @Override // net.fortuna.ical4j.model.Parameter
    protected boolean isQuotable() {
        return false;
    }

    public DelegatedFrom(String str) throws URISyntaxException {
        this(new AddressList(Strings.unquote(str)));
    }

    public DelegatedFrom(AddressList addressList) {
        super(Parameter.DELEGATED_FROM, new Factory());
        this.delegators = addressList;
    }

    public final AddressList getDelegators() {
        return this.delegators;
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return getDelegators().toString();
    }

    public static class Factory extends Content.Factory implements ParameterFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Parameter.DELEGATED_FROM);
        }

        @Override // net.fortuna.ical4j.model.ParameterFactory
        public Parameter createParameter(String str) throws URISyntaxException {
            return new DelegatedFrom(str);
        }
    }
}
