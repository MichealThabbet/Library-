package net.fortuna.ical4j.model.parameter;

import it.piapostolos.android.pinotes.utils.ConstantsBase;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes3.dex */
public class Display extends Parameter {
    private static final String PARAMETER_NAME = "DISPLAY";
    private static final long serialVersionUID = 1;
    private final String[] values;

    public enum Value {
        BADGE,
        GRAPHIC,
        FULLSIZE,
        THUMBNAIL
    }

    public Display(String str) {
        super(PARAMETER_NAME, new Factory());
        String[] strArrSplit = str.split(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
        for (String str2 : strArrSplit) {
            try {
                Value.valueOf(str2);
            } catch (IllegalArgumentException e) {
                if (!str2.startsWith("X-")) {
                    throw e;
                }
            }
        }
        this.values = strArrSplit;
    }

    @Override // net.fortuna.ical4j.model.Content
    public String getValue() {
        return StringUtils.join(this.values, ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
    }

    public static class Factory extends Content.Factory implements ParameterFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Display.PARAMETER_NAME);
        }

        @Override // net.fortuna.ical4j.model.ParameterFactory
        public Parameter createParameter(String str) {
            return new Display(str);
        }
    }
}
