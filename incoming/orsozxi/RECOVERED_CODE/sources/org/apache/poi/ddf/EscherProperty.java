package org.apache.poi.ddf;

import kotlin.jvm.internal.ShortCompanionObject;

/* loaded from: classes3.dex */
public abstract class EscherProperty {
    private short _id;

    public int getPropertySize() {
        return 6;
    }

    public abstract int serializeComplexPart(byte[] bArr, int i);

    public abstract int serializeSimplePart(byte[] bArr, int i);

    public EscherProperty(short s) {
        this._id = s;
    }

    public EscherProperty(short s, boolean z, boolean z2) {
        this._id = (short) (s + (z ? ShortCompanionObject.MIN_VALUE : (short) 0) + (z2 ? 16384 : 0));
    }

    public short getId() {
        return this._id;
    }

    public short getPropertyNumber() {
        return (short) (this._id & 16383);
    }

    public boolean isComplex() {
        return (this._id & ShortCompanionObject.MIN_VALUE) != 0;
    }

    public boolean isBlipId() {
        return (this._id & 16384) != 0;
    }

    public String getName() {
        return EscherProperties.getPropertyName(this._id);
    }
}
