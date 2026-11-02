package org.apache.poi.hssf.record.formula;

import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public class UnknownPtg extends Ptg {
    private final int _sid;
    private short size = 1;

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public byte getDefaultOperandClass() {
        return (byte) 32;
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public boolean isBaseToken() {
        return true;
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public String toFormulaString() {
        return GrsBaseInfo.CountryCodeSource.UNKNOWN;
    }

    public UnknownPtg(int i) {
        this._sid = i;
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(this._sid);
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public int getSize() {
        return this.size;
    }
}
