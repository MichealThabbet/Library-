package org.apache.poi.hssf.record.formula;

import org.apache.poi.hssf.usermodel.HSSFErrorConstants;
import org.apache.poi.util.LittleEndianInput;
import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class AreaErrPtg extends OperandPtg {
    public static final byte sid = 43;
    private final int unused1;
    private final int unused2;

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public byte getDefaultOperandClass() {
        return (byte) 0;
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public int getSize() {
        return 9;
    }

    public AreaErrPtg() {
        this.unused1 = 0;
        this.unused2 = 0;
    }

    public AreaErrPtg(LittleEndianInput littleEndianInput) {
        this.unused1 = littleEndianInput.readInt();
        this.unused2 = littleEndianInput.readInt();
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + sid);
        littleEndianOutput.writeInt(this.unused1);
        littleEndianOutput.writeInt(this.unused2);
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public String toFormulaString() {
        return HSSFErrorConstants.getText(23);
    }
}
