package org.apache.poi.hssf.record.formula;

import org.apache.poi.util.LittleEndianInput;
import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class RefNPtg extends Ref2DPtgBase {
    public static final byte sid = 44;

    @Override // org.apache.poi.hssf.record.formula.Ref2DPtgBase
    protected byte getSid() {
        return sid;
    }

    @Override // org.apache.poi.hssf.record.formula.Ref2DPtgBase, org.apache.poi.hssf.record.formula.Ptg
    public /* bridge */ /* synthetic */ void write(LittleEndianOutput littleEndianOutput) {
        super.write(littleEndianOutput);
    }

    public RefNPtg(LittleEndianInput littleEndianInput) {
        super(littleEndianInput);
    }
}
