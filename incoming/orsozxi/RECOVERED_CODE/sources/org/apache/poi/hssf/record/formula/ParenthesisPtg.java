package org.apache.poi.hssf.record.formula;

import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class ParenthesisPtg extends ControlPtg {
    private static final int SIZE = 1;
    public static final ControlPtg instance = new ParenthesisPtg();
    public static final byte sid = 21;

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public int getSize() {
        return 1;
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public String toFormulaString() {
        return "()";
    }

    private ParenthesisPtg() {
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + 21);
    }

    public String toFormulaString(String[] strArr) {
        return "(" + strArr[0] + ")";
    }
}
