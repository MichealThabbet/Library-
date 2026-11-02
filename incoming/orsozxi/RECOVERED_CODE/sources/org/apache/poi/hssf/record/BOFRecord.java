package org.apache.poi.hssf.record;

import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class BOFRecord extends StandardRecord {
    public static final int BUILD = 4307;
    public static final int BUILD_YEAR = 1996;
    public static final int HISTORY_MASK = 65;
    public static final int TYPE_CHART = 32;
    public static final int TYPE_EXCEL_4_MACRO = 64;
    public static final int TYPE_VB_MODULE = 6;
    public static final int TYPE_WORKBOOK = 5;
    public static final int TYPE_WORKSHEET = 16;
    public static final int TYPE_WORKSPACE_FILE = 256;
    public static final int VERSION = 1536;
    public static final short sid = 2057;
    private int field_1_version;
    private int field_2_type;
    private int field_3_build;
    private int field_4_year;
    private int field_5_history;
    private int field_6_rversion;

    @Override // org.apache.poi.hssf.record.StandardRecord
    protected int getDataSize() {
        return 16;
    }

    @Override // org.apache.poi.hssf.record.Record
    public short getSid() {
        return sid;
    }

    public BOFRecord() {
    }

    private BOFRecord(int i) {
        this.field_1_version = 1536;
        this.field_2_type = i;
        this.field_3_build = 4307;
        this.field_4_year = 1996;
        this.field_5_history = 1;
        this.field_6_rversion = 1536;
    }

    public static BOFRecord createSheetBOF() {
        return new BOFRecord(16);
    }

    public BOFRecord(RecordInputStream recordInputStream) {
        this.field_1_version = recordInputStream.readShort();
        this.field_2_type = recordInputStream.readShort();
        if (recordInputStream.remaining() >= 2) {
            this.field_3_build = recordInputStream.readShort();
        }
        if (recordInputStream.remaining() >= 2) {
            this.field_4_year = recordInputStream.readShort();
        }
        if (recordInputStream.remaining() >= 4) {
            this.field_5_history = recordInputStream.readInt();
        }
        if (recordInputStream.remaining() >= 4) {
            this.field_6_rversion = recordInputStream.readInt();
        }
    }

    public void setVersion(int i) {
        this.field_1_version = i;
    }

    public void setType(int i) {
        this.field_2_type = i;
    }

    public void setBuild(int i) {
        this.field_3_build = i;
    }

    public void setBuildYear(int i) {
        this.field_4_year = i;
    }

    public void setHistoryBitMask(int i) {
        this.field_5_history = i;
    }

    public void setRequiredVersion(int i) {
        this.field_6_rversion = i;
    }

    public int getVersion() {
        return this.field_1_version;
    }

    public int getType() {
        return this.field_2_type;
    }

    public int getBuild() {
        return this.field_3_build;
    }

    public int getBuildYear() {
        return this.field_4_year;
    }

    public int getHistoryBitMask() {
        return this.field_5_history;
    }

    public int getRequiredVersion() {
        return this.field_6_rversion;
    }

    @Override // org.apache.poi.hssf.record.Record
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[BOF RECORD]\n");
        stringBuffer.append("    .version  = ");
        stringBuffer.append(HexDump.shortToHex(getVersion()));
        stringBuffer.append("\n");
        stringBuffer.append("    .type     = ");
        stringBuffer.append(HexDump.shortToHex(getType()));
        stringBuffer.append(" (");
        stringBuffer.append(getTypeName());
        stringBuffer.append(")");
        stringBuffer.append("\n");
        stringBuffer.append("    .build    = ");
        stringBuffer.append(HexDump.shortToHex(getBuild()));
        stringBuffer.append("\n");
        stringBuffer.append("    .buildyear= ");
        stringBuffer.append(getBuildYear());
        stringBuffer.append("\n");
        stringBuffer.append("    .history  = ");
        stringBuffer.append(HexDump.intToHex(getHistoryBitMask()));
        stringBuffer.append("\n");
        stringBuffer.append("    .reqver   = ");
        stringBuffer.append(HexDump.intToHex(getRequiredVersion()));
        stringBuffer.append("\n");
        stringBuffer.append("[/BOF RECORD]\n");
        return stringBuffer.toString();
    }

    private String getTypeName() {
        int i = this.field_2_type;
        return i != 5 ? i != 6 ? i != 16 ? i != 32 ? i != 64 ? i != 256 ? "#error unknown type#" : "workspace file" : "excel 4 macro" : "chart" : "worksheet" : "vb module" : "workbook";
    }

    @Override // org.apache.poi.hssf.record.StandardRecord
    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getVersion());
        littleEndianOutput.writeShort(getType());
        littleEndianOutput.writeShort(getBuild());
        littleEndianOutput.writeShort(getBuildYear());
        littleEndianOutput.writeInt(getHistoryBitMask());
        littleEndianOutput.writeInt(getRequiredVersion());
    }

    @Override // org.apache.poi.hssf.record.Record
    public Object clone() {
        BOFRecord bOFRecord = new BOFRecord();
        bOFRecord.field_1_version = this.field_1_version;
        bOFRecord.field_2_type = this.field_2_type;
        bOFRecord.field_3_build = this.field_3_build;
        bOFRecord.field_4_year = this.field_4_year;
        bOFRecord.field_5_history = this.field_5_history;
        bOFRecord.field_6_rversion = this.field_6_rversion;
        return bOFRecord;
    }
}
