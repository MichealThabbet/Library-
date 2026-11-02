package net.fortuna.ical4j.model;

import it.piapostolos.android.pinotes.utils.ConstantsBase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.fortuna.ical4j.util.CompatibilityHints;

/* loaded from: classes3.dex */
public class WeekDayList extends ArrayList<WeekDay> implements Serializable {
    private static final long serialVersionUID = 1243262497035300445L;

    public WeekDayList() {
    }

    public WeekDayList(WeekDay... weekDayArr) {
        addAll(Arrays.asList(weekDayArr));
    }

    public WeekDayList(int i) {
        super(i);
    }

    public WeekDayList(String str) {
        boolean zIsHintEnabled = CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_OUTLOOK_COMPATIBILITY);
        StringTokenizer stringTokenizer = new StringTokenizer(str, ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
        while (stringTokenizer.hasMoreTokens()) {
            if (zIsHintEnabled) {
                add(new WeekDay(stringTokenizer.nextToken().replaceAll(" ", "")));
            } else {
                add(new WeekDay(stringTokenizer.nextToken()));
            }
        }
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        return (String) stream().map(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$TjPA4UND5-sWO_lJGDuIDX0c4Dg
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((WeekDay) obj).toString();
            }
        }).collect(Collectors.joining(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR));
    }
}
