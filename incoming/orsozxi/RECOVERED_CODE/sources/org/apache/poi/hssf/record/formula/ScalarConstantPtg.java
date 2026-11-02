package org.apache.poi.hssf.record.formula;

/* loaded from: classes3.dex */
public abstract class ScalarConstantPtg extends Ptg {
    @Override // org.apache.poi.hssf.record.formula.Ptg
    public final byte getDefaultOperandClass() {
        return (byte) 32;
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public final boolean isBaseToken() {
        return true;
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer(64);
        stringBuffer.append(getClass().getName());
        stringBuffer.append(" [");
        stringBuffer.append(toFormulaString());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
