package org.apache.poi.hssf.record.formula;

/* loaded from: classes3.dex */
public abstract class OperandPtg extends Ptg implements Cloneable {
    @Override // org.apache.poi.hssf.record.formula.Ptg
    public final boolean isBaseToken() {
        return false;
    }

    public final OperandPtg copy() {
        try {
            return (OperandPtg) clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
