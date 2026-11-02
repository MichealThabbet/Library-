package net.fortuna.ical4j.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.function.Predicate;
import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.filter.HasPropertyRule;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.property.RecurrenceId;
import net.fortuna.ical4j.model.property.Uid;

/* loaded from: classes3.dex */
public class ComponentGroup<T extends Component> {
    private final Filter<T> componentFilter;
    private final ComponentList<T> components;

    public ComponentGroup(ComponentList<T> componentList, Uid uid) {
        this(componentList, uid, null);
    }

    public ComponentGroup(ComponentList<T> componentList, Uid uid, RecurrenceId recurrenceId) {
        Predicate hasPropertyRule;
        this.components = componentList;
        if (recurrenceId != null) {
            hasPropertyRule = new HasPropertyRule(uid).and(new HasPropertyRule(recurrenceId));
        } else {
            hasPropertyRule = new HasPropertyRule(uid);
        }
        this.componentFilter = new Filter<>(hasPropertyRule);
    }

    public ComponentList<T> getRevisions() {
        return (ComponentList) this.componentFilter.filter(this.components);
    }

    public T getLatestRevision() {
        ComponentList<T> revisions = getRevisions();
        revisions.sort(new ComponentSequenceComparator());
        Collections.reverse(revisions);
        return revisions.iterator().next();
    }

    public PeriodList calculateRecurrenceSet(Period period) {
        PeriodList periodList = new PeriodList();
        Iterator<T> it2 = getRevisions().iterator();
        while (it2.hasNext()) {
            periodList = periodList.add(it2.next().calculateRecurrenceSet(period));
        }
        return periodList;
    }
}
