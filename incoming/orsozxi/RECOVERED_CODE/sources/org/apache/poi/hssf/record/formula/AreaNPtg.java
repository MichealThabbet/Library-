package org.apache.poi.hssf.record.formula;

import org.apache.poi.util.LittleEndianInput;

/* loaded from: classes3.dex */
public final class AreaNPtg extends Area2DPtgBase {
    public static final short sid = 45;

    @Override // org.apache.poi.hssf.record.formula.Area2DPtgBase
    protected byte getSid() {
        return (byte) 45;
    }

    public AreaNPtg(LittleEndianInput littleEndianInput) {
        super(littleEndianInput);
    }
}
