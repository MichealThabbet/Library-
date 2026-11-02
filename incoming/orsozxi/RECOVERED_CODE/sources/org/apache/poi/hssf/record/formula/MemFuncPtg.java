package org.apache.poi.hssf.record.formula;

import org.apache.poi.util.LittleEndianInput;
import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class MemFuncPtg extends OperandPtg {
    public static final byte sid = 41;
    private final int field_1_len_ref_subexpression;

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public byte getDefaultOperandClass() {
        return (byte) 0;
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public int getSize() {
        return 3;
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public String toFormulaString() {
        return "";
    }

    public MemFuncPtg(LittleEndianInput littleEndianInput) {
        this(littleEndianInput.readUShort());
    }

    public MemFuncPtg(int i) {
        this.field_1_len_ref_subexpression = i;
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + sid);
        littleEndianOutput.writeShort(this.field_1_len_ref_subexpression);
    }

    public int getNumberOfOperands() {
        return this.field_1_len_ref_subexpression;
    }

    public int getLenRefSubexpression() {
        return this.field_1_len_ref_subexpression;
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer(64);
        stringBuffer.append(getClass().getName());
        stringBuffer.append(" [len=");
        stringBuffer.append(this.field_1_len_ref_subexpression);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
