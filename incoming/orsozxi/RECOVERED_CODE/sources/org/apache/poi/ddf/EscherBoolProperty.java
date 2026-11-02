package org.apache.poi.ddf;

/* loaded from: classes3.dex */
public class EscherBoolProperty extends EscherSimpleProperty {
    public EscherBoolProperty(short s, int i) {
        super(s, i);
    }

    public boolean isTrue() {
        return this.propertyValue != 0;
    }

    public boolean isFalse() {
        return this.propertyValue == 0;
    }
}
