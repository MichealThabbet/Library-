package org.apache.poi.hssf.record.formula;

import org.apache.poi.util.LittleEndianInput;
import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class MemErrPtg extends OperandPtg {
    private static final int SIZE = 7;
    public static final short sid = 39;
    private int field_1_reserved;
    private short field_2_subex_len;

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public byte getDefaultOperandClass() {
        return (byte) 32;
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public int getSize() {
        return 7;
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public String toFormulaString() {
        return "ERR#";
    }

    public MemErrPtg(LittleEndianInput littleEndianInput) {
        this.field_1_reserved = littleEndianInput.readInt();
        this.field_2_subex_len = littleEndianInput.readShort();
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + 39);
        littleEndianOutput.writeInt(this.field_1_reserved);
        littleEndianOutput.writeShort(this.field_2_subex_len);
    }
}
