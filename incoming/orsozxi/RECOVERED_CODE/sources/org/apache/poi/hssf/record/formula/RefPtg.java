package org.apache.poi.hssf.record.formula;

import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.LittleEndianInput;
import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class RefPtg extends Ref2DPtgBase {
    public static final byte sid = 36;

    @Override // org.apache.poi.hssf.record.formula.Ref2DPtgBase
    protected byte getSid() {
        return sid;
    }

    @Override // org.apache.poi.hssf.record.formula.Ref2DPtgBase, org.apache.poi.hssf.record.formula.Ptg
    public /* bridge */ /* synthetic */ void write(LittleEndianOutput littleEndianOutput) {
        super.write(littleEndianOutput);
    }

    public RefPtg(String str) {
        super(new CellReference(str));
    }

    public RefPtg(int i, int i2, boolean z, boolean z2) {
        super(i, i2, z, z2);
    }

    public RefPtg(LittleEndianInput littleEndianInput) {
        super(littleEndianInput);
    }

    public RefPtg(CellReference cellReference) {
        super(cellReference);
    }
}
