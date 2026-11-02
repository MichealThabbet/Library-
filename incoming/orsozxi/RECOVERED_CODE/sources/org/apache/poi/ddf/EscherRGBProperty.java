package org.apache.poi.ddf;

/* loaded from: classes3.dex */
public class EscherRGBProperty extends EscherSimpleProperty {
    public EscherRGBProperty(short s, int i) {
        super(s, i);
    }

    public int getRgbColor() {
        return this.propertyValue;
    }

    public byte getRed() {
        return (byte) (this.propertyValue & 255);
    }

    public byte getGreen() {
        return (byte) ((this.propertyValue >> 8) & 255);
    }

    public byte getBlue() {
        return (byte) ((this.propertyValue >> 16) & 255);
    }
}
