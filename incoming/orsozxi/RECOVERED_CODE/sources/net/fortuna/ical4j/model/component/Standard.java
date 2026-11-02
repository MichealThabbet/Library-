package net.fortuna.ical4j.model.component;

import net.fortuna.ical4j.model.ComponentFactory;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.PropertyList;

/* loaded from: classes3.dex */
public class Standard extends Observance {
    private static final long serialVersionUID = -4750910013406451159L;

    public Standard() {
        super(Observance.STANDARD);
    }

    public Standard(PropertyList propertyList) {
        super(Observance.STANDARD, propertyList);
    }

    public static class Factory extends Content.Factory implements ComponentFactory<Standard> {
        public Factory() {
            super(Observance.STANDARD);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public Standard createComponent() {
            return new Standard();
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public Standard createComponent(PropertyList propertyList) {
            return new Standard(propertyList);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public Standard createComponent(PropertyList propertyList, ComponentList componentList) {
            throw new UnsupportedOperationException(String.format("%s does not support sub-components", Observance.STANDARD));
        }
    }
}
