package net.fortuna.ical4j.transform.recurrence;

import java.util.Collections;
import java.util.Iterator;
import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.NumberList;
import net.fortuna.ical4j.transform.Transformer;
import net.fortuna.ical4j.util.Dates;

/* loaded from: classes3.dex */
public class BySetPosRule implements Transformer<DateList> {
    private final NumberList setPosList;

    public BySetPosRule(NumberList numberList) {
        this.setPosList = numberList;
    }

    @Override // net.fortuna.ical4j.transform.Transformer
    public DateList transform(DateList dateList) {
        if (this.setPosList.isEmpty()) {
            return dateList;
        }
        Collections.sort(dateList);
        DateList dateListInstance = Dates.getDateListInstance(dateList);
        int size = dateList.size();
        Iterator<Integer> it2 = this.setPosList.iterator();
        while (it2.hasNext()) {
            int iIntValue = it2.next().intValue();
            if (iIntValue > 0 && iIntValue <= size) {
                dateListInstance.add(dateList.get(iIntValue - 1));
            } else if (iIntValue < 0 && iIntValue >= (-size)) {
                dateListInstance.add(dateList.get(iIntValue + size));
            }
        }
        return dateListInstance;
    }
}
