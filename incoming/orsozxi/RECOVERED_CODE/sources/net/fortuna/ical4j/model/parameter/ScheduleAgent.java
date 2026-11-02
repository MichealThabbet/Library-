package net.fortuna.ical4j.model.parameter;

import java.net.URISyntaxException;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.util.Strings;

/* loaded from: classes3.dex */
public class ScheduleAgent extends Parameter {
    private static final long serialVersionUID = 4205758749959461020L;
    private String value;
    private static final String VALUE_SERVER = "SERVER";
    public static final ScheduleAgent SERVER = new ScheduleAgent(VALUE_SERVER);
    private static final String VALUE_CLIENT = "CLIENT";
    public static final ScheduleAgent CLIENT = new ScheduleAgent(VALUE_CLIENT);
    private static final String VALUE_NONE = "NONE";
    public static final ScheduleAgent NONE = new ScheduleAgent(VALUE_NONE);

    public ScheduleAgent(String str) {
        super(Parameter.SCHEDULE_AGENT, new Factory());
        this.value = Strings.unquote(str);
    }

    @Override // net.fortuna.ical4j.model.Content
    public final String getValue() {
        return this.value;
    }

    public static class Factory extends Content.Factory implements ParameterFactory {
        private static final long serialVersionUID = 1;

        public Factory() {
            super(Parameter.SCHEDULE_AGENT);
        }

        @Override // net.fortuna.ical4j.model.ParameterFactory
        public Parameter createParameter(String str) throws URISyntaxException {
            ScheduleAgent scheduleAgent = new ScheduleAgent(str);
            if (ScheduleAgent.SERVER.equals(scheduleAgent)) {
                return ScheduleAgent.SERVER;
            }
            if (ScheduleAgent.CLIENT.equals(scheduleAgent)) {
                return ScheduleAgent.CLIENT;
            }
            return ScheduleAgent.NONE.equals(scheduleAgent) ? ScheduleAgent.NONE : scheduleAgent;
        }
    }
}
