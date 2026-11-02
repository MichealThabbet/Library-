package net.fortuna.ical4j.model.parameter;

import java.net.URISyntaxException;
import net.fortuna.ical4j.model.AddressList;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.util.Strings;

/* loaded from: classes3.dex */
public class Member extends Parameter {
    private static final long serialVersionUID = 287348849443687499L;
    private AddressList groups;

    @Override // net.fortuna.ical4j.model.Parameter
    protected boolean isQuotable() {
        return false;
    }

    public Member(String str) throws URISyntaxException {
        this(new AddressList(Strings.unquote(str)));
    }

    public Member(AddressList addressList) {
        super(Parameter.MEMBER, new Factory());
        this.groups = addressList;
    }

    public final AddressList getGroups() {
        return this.groups;
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return getGroups().toString();
    }

    public static class Factory extends Content.Factory implements ParameterFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Parameter.MEMBER);
        }

        @Override // net.fortuna.ical4j.model.ParameterFactory
        public Parameter createParameter(String str) throws URISyntaxException {
            return new Member(str);
        }
    }
}
