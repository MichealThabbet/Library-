package org.ocpsoft.prettytime.units;

import java.io.Serializable;
import java.util.Comparator;
import org.ocpsoft.prettytime.TimeUnit;

/* loaded from: classes3.dex */
public class TimeUnitComparator implements Comparator<TimeUnit>, Serializable {
    private static final long serialVersionUID = 1;

    @Override // java.util.Comparator
    public int compare(TimeUnit timeUnit, TimeUnit timeUnit2) {
        if (timeUnit.getMillisPerUnit() < timeUnit2.getMillisPerUnit()) {
            return -1;
        }
        return timeUnit.getMillisPerUnit() > timeUnit2.getMillisPerUnit() ? 1 : 0;
    }
}
