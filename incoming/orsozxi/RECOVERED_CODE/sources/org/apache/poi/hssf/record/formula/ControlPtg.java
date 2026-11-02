package org.apache.poi.hssf.record.formula;

/* loaded from: classes3.dex */
public abstract class ControlPtg extends Ptg {
    @Override // org.apache.poi.hssf.record.formula.Ptg
    public boolean isBaseToken() {
        return true;
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public final byte getDefaultOperandClass() {
        throw new IllegalStateException("Control tokens are not classified");
    }
}
