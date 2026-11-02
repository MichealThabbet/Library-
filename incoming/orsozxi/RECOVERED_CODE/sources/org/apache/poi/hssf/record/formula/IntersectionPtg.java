package org.apache.poi.hssf.record.formula;

import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class IntersectionPtg extends OperationPtg {
    public static final OperationPtg instance = new IntersectionPtg();
    public static final byte sid = 15;

    @Override // org.apache.poi.hssf.record.formula.OperationPtg
    public int getNumberOfOperands() {
        return 2;
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public int getSize() {
        return 1;
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public final boolean isBaseToken() {
        return true;
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public String toFormulaString() {
        return " ";
    }

    private IntersectionPtg() {
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + 15);
    }

    @Override // org.apache.poi.hssf.record.formula.OperationPtg
    public String toFormulaString(String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(strArr[0]);
        stringBuffer.append(" ");
        stringBuffer.append(strArr[1]);
        return stringBuffer.toString();
    }
}
