package org.apache.poi.hssf.record.pivottable;

import org.apache.poi.hssf.record.RecordInputStream;
import org.apache.poi.hssf.record.StandardRecord;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndianOutput;
import org.apache.poi.util.StringUtil;

/* loaded from: classes3.dex */
public final class DataItemRecord extends StandardRecord {
    public static final short sid = 197;
    private int df;
    private int ifmt;
    private int iiftab;
    private int isxvd;
    private int isxvdData;
    private int isxvi;
    private String name;

    @Override // org.apache.poi.hssf.record.Record
    public short getSid() {
        return (short) 197;
    }

    public DataItemRecord(RecordInputStream recordInputStream) {
        this.isxvdData = recordInputStream.readUShort();
        this.iiftab = recordInputStream.readUShort();
        this.df = recordInputStream.readUShort();
        this.isxvd = recordInputStream.readUShort();
        this.isxvi = recordInputStream.readUShort();
        this.ifmt = recordInputStream.readUShort();
        this.name = recordInputStream.readString();
    }

    @Override // org.apache.poi.hssf.record.StandardRecord
    protected void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.isxvdData);
        littleEndianOutput.writeShort(this.iiftab);
        littleEndianOutput.writeShort(this.df);
        littleEndianOutput.writeShort(this.isxvd);
        littleEndianOutput.writeShort(this.isxvi);
        littleEndianOutput.writeShort(this.ifmt);
        StringUtil.writeUnicodeString(littleEndianOutput, this.name);
    }

    @Override // org.apache.poi.hssf.record.StandardRecord
    protected int getDataSize() {
        return StringUtil.getEncodedSize(this.name) + 12;
    }

    @Override // org.apache.poi.hssf.record.Record
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[SXDI]\n");
        stringBuffer.append("  .isxvdData = ");
        stringBuffer.append(HexDump.shortToHex(this.isxvdData));
        stringBuffer.append("\n");
        stringBuffer.append("  .iiftab = ");
        stringBuffer.append(HexDump.shortToHex(this.iiftab));
        stringBuffer.append("\n");
        stringBuffer.append("  .df = ");
        stringBuffer.append(HexDump.shortToHex(this.df));
        stringBuffer.append("\n");
        stringBuffer.append("  .isxvd = ");
        stringBuffer.append(HexDump.shortToHex(this.isxvd));
        stringBuffer.append("\n");
        stringBuffer.append("  .isxvi = ");
        stringBuffer.append(HexDump.shortToHex(this.isxvi));
        stringBuffer.append("\n");
        stringBuffer.append("  .ifmt = ");
        stringBuffer.append(HexDump.shortToHex(this.ifmt));
        stringBuffer.append("\n");
        stringBuffer.append("[/SXDI]\n");
        return stringBuffer.toString();
    }
}
