package net.fortuna.ical4j.model.component;

import net.fortuna.ical4j.model.ComponentFactory;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.PropertyList;

/* loaded from: classes3.dex */
public class Daylight extends Observance {
    private static final long serialVersionUID = -2494710612002978763L;

    public Daylight() {
        super(Observance.DAYLIGHT);
    }

    public Daylight(PropertyList propertyList) {
        super(Observance.DAYLIGHT, propertyList);
    }

    public static class Factory extends Content.Factory implements ComponentFactory<Daylight> {
        public Factory() {
            super(Observance.DAYLIGHT);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public Daylight createComponent() {
            return new Daylight();
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public Daylight createComponent(PropertyList propertyList) {
            return new Daylight(propertyList);
        }

        @Override // net.fortuna.ical4j.model.ComponentFactory
        public Daylight createComponent(PropertyList propertyList, ComponentList componentList) {
            throw new UnsupportedOperationException(String.format("%s does not support sub-components", Observance.DAYLIGHT));
        }
    }
}
