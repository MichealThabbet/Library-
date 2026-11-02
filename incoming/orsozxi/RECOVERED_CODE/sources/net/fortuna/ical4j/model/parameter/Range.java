package net.fortuna.ical4j.model.parameter;

import java.net.URISyntaxException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.util.Strings;

/* loaded from: classes3.dex */
public class Range extends Parameter {
    private static final long serialVersionUID = -3057531444558393776L;
    private String value;
    private static final String VALUE_THISANDPRIOR = "THISANDPRIOR";
    public static final Range THISANDPRIOR = new Range(VALUE_THISANDPRIOR);
    private static final String VALUE_THISANDFUTURE = "THISANDFUTURE";
    public static final Range THISANDFUTURE = new Range(VALUE_THISANDFUTURE);

    public Range(String str) {
        super(Parameter.RANGE, new Factory());
        this.value = Strings.unquote(str);
        if (CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_NOTES_COMPATIBILITY) || VALUE_THISANDPRIOR.equals(this.value) || VALUE_THISANDFUTURE.equals(this.value)) {
            return;
        }
        throw new IllegalArgumentException("Invalid value [" + this.value + "]");
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return this.value;
    }

    public static class Factory extends Content.Factory implements ParameterFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Parameter.RANGE);
        }

        @Override // net.fortuna.ical4j.model.ParameterFactory
        public Parameter createParameter(String str) throws URISyntaxException {
            Range range = new Range(str);
            if (Range.THISANDFUTURE.equals(range)) {
                return Range.THISANDFUTURE;
            }
            return Range.THISANDPRIOR.equals(range) ? Range.THISANDPRIOR : range;
        }
    }
}
