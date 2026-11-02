package org.apache.poi.hssf.record.chart;

import org.apache.poi.hssf.record.RecordInputStream;
import org.apache.poi.hssf.record.StandardRecord;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class AxisRecord extends StandardRecord {
    public static final short AXIS_TYPE_CATEGORY_OR_X_AXIS = 0;
    public static final short AXIS_TYPE_SERIES_AXIS = 2;
    public static final short AXIS_TYPE_VALUE_AXIS = 1;
    public static final short sid = 4125;
    private short field_1_axisType;
    private int field_2_reserved1;
    private int field_3_reserved2;
    private int field_4_reserved3;
    private int field_5_reserved4;

    @Override // org.apache.poi.hssf.record.StandardRecord
    protected int getDataSize() {
        return 18;
    }

    @Override // org.apache.poi.hssf.record.Record
    public short getSid() {
        return sid;
    }

    public AxisRecord() {
    }

    public AxisRecord(RecordInputStream recordInputStream) {
        this.field_1_axisType = recordInputStream.readShort();
        this.field_2_reserved1 = recordInputStream.readInt();
        this.field_3_reserved2 = recordInputStream.readInt();
        this.field_4_reserved3 = recordInputStream.readInt();
        this.field_5_reserved4 = recordInputStream.readInt();
    }

    @Override // org.apache.poi.hssf.record.Record
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[AXIS]\n");
        stringBuffer.append("    .axisType             = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getAxisType()));
        stringBuffer.append(" (");
        stringBuffer.append((int) getAxisType());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .reserved1            = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getReserved1()));
        stringBuffer.append(" (");
        stringBuffer.append(getReserved1());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .reserved2            = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getReserved2()));
        stringBuffer.append(" (");
        stringBuffer.append(getReserved2());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .reserved3            = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getReserved3()));
        stringBuffer.append(" (");
        stringBuffer.append(getReserved3());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .reserved4            = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getReserved4()));
        stringBuffer.append(" (");
        stringBuffer.append(getReserved4());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("[/AXIS]\n");
        return stringBuffer.toString();
    }

    @Override // org.apache.poi.hssf.record.StandardRecord
    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_axisType);
        littleEndianOutput.writeInt(this.field_2_reserved1);
        littleEndianOutput.writeInt(this.field_3_reserved2);
        littleEndianOutput.writeInt(this.field_4_reserved3);
        littleEndianOutput.writeInt(this.field_5_reserved4);
    }

    @Override // org.apache.poi.hssf.record.Record
    public Object clone() {
        AxisRecord axisRecord = new AxisRecord();
        axisRecord.field_1_axisType = this.field_1_axisType;
        axisRecord.field_2_reserved1 = this.field_2_reserved1;
        axisRecord.field_3_reserved2 = this.field_3_reserved2;
        axisRecord.field_4_reserved3 = this.field_4_reserved3;
        axisRecord.field_5_reserved4 = this.field_5_reserved4;
        return axisRecord;
    }

    public short getAxisType() {
        return this.field_1_axisType;
    }

    public void setAxisType(short s) {
        this.field_1_axisType = s;
    }

    public int getReserved1() {
        return this.field_2_reserved1;
    }

    public void setReserved1(int i) {
        this.field_2_reserved1 = i;
    }

    public int getReserved2() {
        return this.field_3_reserved2;
    }

    public void setReserved2(int i) {
        this.field_3_reserved2 = i;
    }

    public int getReserved3() {
        return this.field_4_reserved3;
    }

    public void setReserved3(int i) {
        this.field_4_reserved3 = i;
    }

    public int getReserved4() {
        return this.field_5_reserved4;
    }

    public void setReserved4(int i) {
        this.field_5_reserved4 = i;
    }
}
