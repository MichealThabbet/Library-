package org.apache.poi.hssf.record;

import it.piapostolos.android.pinotes.R2;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class RecalcIdRecord extends StandardRecord {
    public static final short sid = 449;
    private final int _engineId;
    private final int _reserved0;

    @Override // org.apache.poi.hssf.record.StandardRecord
    protected int getDataSize() {
        return 8;
    }

    @Override // org.apache.poi.hssf.record.Record
    public short getSid() {
        return (short) 449;
    }

    public boolean isNeeded() {
        return true;
    }

    public RecalcIdRecord(RecordInputStream recordInputStream) throws RecordFormatException {
        recordInputStream.readUShort();
        this._reserved0 = recordInputStream.readUShort();
        this._engineId = recordInputStream.readInt();
    }

    @Override // org.apache.poi.hssf.record.Record
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[RECALCID]\n");
        stringBuffer.append("    .reserved = ");
        stringBuffer.append(HexDump.shortToHex(this._reserved0));
        stringBuffer.append("\n");
        stringBuffer.append("    .engineId = ");
        stringBuffer.append(HexDump.intToHex(this._engineId));
        stringBuffer.append("\n");
        stringBuffer.append("[/RECALCID]\n");
        return stringBuffer.toString();
    }

    @Override // org.apache.poi.hssf.record.StandardRecord
    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(R2.attr.fab_addButtonColorNormal);
        littleEndianOutput.writeShort(this._reserved0);
        littleEndianOutput.writeInt(this._engineId);
    }
}
