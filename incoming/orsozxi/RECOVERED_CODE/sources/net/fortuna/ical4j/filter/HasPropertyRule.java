package net.fortuna.ical4j.filter;

import java.util.Iterator;
import java.util.function.Predicate;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;

/* loaded from: classes3.dex */
public class HasPropertyRule<T extends Component> implements Predicate<T> {
    private boolean matchEquals;
    private Property property;

    public HasPropertyRule(Property property) {
        this(property, false);
    }

    public HasPropertyRule(Property property, boolean z) {
        this.property = property;
        this.matchEquals = z;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Component component) {
        Iterator<T> it2 = component.getProperties(this.property.getName()).iterator();
        boolean z = false;
        while (it2.hasNext()) {
            Property property = (Property) it2.next();
            if ((this.matchEquals && this.property.equals(property)) || this.property.getValue().equals(property.getValue())) {
                z = true;
            }
        }
        return z;
    }
}
