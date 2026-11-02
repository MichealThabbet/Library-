package net.fortuna.ical4j.model.parameter;

import java.net.URISyntaxException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.util.Strings;

/* loaded from: classes3.dex */
public class PartStat extends Parameter {
    private static final String VALUE_COMPLETED = "COMPLETED";
    private static final long serialVersionUID = -7856347127343842441L;
    private String value;
    private static final String VALUE_NEEDS_ACTION = "NEEDS-ACTION";
    public static final PartStat NEEDS_ACTION = new PartStat(VALUE_NEEDS_ACTION);
    private static final String VALUE_ACCEPTED = "ACCEPTED";
    public static final PartStat ACCEPTED = new PartStat(VALUE_ACCEPTED);
    private static final String VALUE_DECLINED = "DECLINED";
    public static final PartStat DECLINED = new PartStat(VALUE_DECLINED);
    private static final String VALUE_TENTATIVE = "TENTATIVE";
    public static final PartStat TENTATIVE = new PartStat(VALUE_TENTATIVE);
    private static final String VALUE_DELEGATED = "DELEGATED";
    public static final PartStat DELEGATED = new PartStat(VALUE_DELEGATED);
    public static final PartStat COMPLETED = new PartStat("COMPLETED");
    private static final String VALUE_IN_PROCESS = "IN-PROCESS";
    public static final PartStat IN_PROCESS = new PartStat(VALUE_IN_PROCESS);

    public PartStat(String str) {
        super(Parameter.PARTSTAT, new Factory());
        this.value = Strings.unquote(str);
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return this.value;
    }

    public static class Factory extends Content.Factory implements ParameterFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Parameter.PARTSTAT);
        }

        @Override // net.fortuna.ical4j.model.ParameterFactory
        public Parameter createParameter(String str) throws URISyntaxException {
            PartStat partStat = new PartStat(str);
            if (PartStat.NEEDS_ACTION.equals(partStat)) {
                return PartStat.NEEDS_ACTION;
            }
            if (PartStat.ACCEPTED.equals(partStat)) {
                return PartStat.ACCEPTED;
            }
            if (PartStat.DECLINED.equals(partStat)) {
                return PartStat.DECLINED;
            }
            if (PartStat.TENTATIVE.equals(partStat)) {
                return PartStat.TENTATIVE;
            }
            if (PartStat.DELEGATED.equals(partStat)) {
                return PartStat.DELEGATED;
            }
            if (PartStat.COMPLETED.equals(partStat)) {
                return PartStat.COMPLETED;
            }
            return PartStat.IN_PROCESS.equals(partStat) ? PartStat.IN_PROCESS : partStat;
        }
    }
}
