package net.fortuna.ical4j.transform.rfc5545;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.ServiceLoader;
import java.util.Set;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;

/* loaded from: classes3.dex */
public class RuleManager {
    private static final Set<Rfc5545PropertyRule<? extends Property>> PROPERTY_RULES = new LinkedHashSet();
    private static final Set<Rfc5545ComponentRule<? extends Component>> COMPONENT_RULES = new LinkedHashSet();

    static {
        Iterator it2 = ServiceLoader.load(Rfc5545PropertyRule.class).iterator();
        while (it2.hasNext()) {
            Rfc5545PropertyRule<? extends Property> rfc5545PropertyRule = (Rfc5545PropertyRule) it2.next();
            if (rfc5545PropertyRule.getSupportedType() == null) {
                throw null;
            }
            PROPERTY_RULES.add(rfc5545PropertyRule);
        }
        Iterator it3 = ServiceLoader.load(Rfc5545ComponentRule.class).iterator();
        while (it3.hasNext()) {
            Rfc5545ComponentRule<? extends Component> rfc5545ComponentRule = (Rfc5545ComponentRule) it3.next();
            if (rfc5545ComponentRule.getSupportedType() == null) {
                throw null;
            }
            COMPONENT_RULES.add(rfc5545ComponentRule);
        }
    }

    public static void applyTo(Property property) {
        Iterator<Rfc5545PropertyRule<Property>> it2 = getSupportedRulesFor(property).iterator();
        while (it2.hasNext()) {
            it2.next().applyTo(property);
        }
    }

    public static void applyTo(Component component) {
        Iterator<Rfc5545ComponentRule<Component>> it2 = getSupportedRulesFor(component).iterator();
        while (it2.hasNext()) {
            it2.next().applyTo(component);
        }
    }

    private static Set<Rfc5545PropertyRule<Property>> getSupportedRulesFor(Property property) {
        if (property == null) {
            throw null;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(1);
        for (Rfc5545PropertyRule<? extends Property> rfc5545PropertyRule : PROPERTY_RULES) {
            if (rfc5545PropertyRule.getSupportedType().isInstance(property)) {
                linkedHashSet.add(rfc5545PropertyRule);
            }
        }
        return linkedHashSet;
    }

    private static Set<Rfc5545ComponentRule<Component>> getSupportedRulesFor(Component component) {
        if (component == null) {
            throw null;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(1);
        for (Rfc5545ComponentRule<? extends Component> rfc5545ComponentRule : COMPONENT_RULES) {
            if (rfc5545ComponentRule.getSupportedType().isInstance(component)) {
                linkedHashSet.add(rfc5545ComponentRule);
            }
        }
        return linkedHashSet;
    }
}
