package org.apache.poi.hssf.record.chart;

import org.apache.poi.hssf.record.RecordFormatException;
import org.apache.poi.hssf.record.RecordInputStream;
import org.apache.poi.hssf.record.StandardRecord;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class ChartEndObjectRecord extends StandardRecord {
    public static final short sid = 2133;
    private short grbitFrt;
    private short iObjectKind;
    private short rt;
    private byte[] unused;

    @Override // org.apache.poi.hssf.record.StandardRecord
    protected int getDataSize() {
        return 12;
    }

    @Override // org.apache.poi.hssf.record.Record
    public short getSid() {
        return sid;
    }

    public ChartEndObjectRecord(RecordInputStream recordInputStream) throws RecordFormatException {
        this.rt = recordInputStream.readShort();
        this.grbitFrt = recordInputStream.readShort();
        this.iObjectKind = recordInputStream.readShort();
        byte[] bArr = new byte[6];
        this.unused = bArr;
        recordInputStream.readFully(bArr);
    }

    @Override // org.apache.poi.hssf.record.StandardRecord
    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.rt);
        littleEndianOutput.writeShort(this.grbitFrt);
        littleEndianOutput.writeShort(this.iObjectKind);
        littleEndianOutput.write(this.unused);
    }

    @Override // org.apache.poi.hssf.record.Record
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[ENDOBJECT]\n");
        stringBuffer.append("    .rt         =");
        stringBuffer.append(HexDump.shortToHex(this.rt));
        stringBuffer.append('\n');
        stringBuffer.append("    .grbitFrt   =");
        stringBuffer.append(HexDump.shortToHex(this.grbitFrt));
        stringBuffer.append('\n');
        stringBuffer.append("    .iObjectKind=");
        stringBuffer.append(HexDump.shortToHex(this.iObjectKind));
        stringBuffer.append('\n');
        stringBuffer.append("    .unused     =");
        stringBuffer.append(HexDump.toHex(this.unused));
        stringBuffer.append('\n');
        stringBuffer.append("[/ENDOBJECT]\n");
        return stringBuffer.toString();
    }
}
