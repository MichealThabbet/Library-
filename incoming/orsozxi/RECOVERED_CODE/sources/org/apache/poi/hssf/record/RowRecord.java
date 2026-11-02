package org.apache.poi.hssf.record;

import org.apache.poi.util.BitField;
import org.apache.poi.util.BitFieldFactory;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class RowRecord extends StandardRecord {
    private static final int DEFAULT_HEIGHT_BIT = 32768;
    public static final int ENCODED_SIZE = 20;
    private static final int OPTION_BITS_ALWAYS_SET = 256;
    public static final short sid = 520;
    private int field_1_row_number;
    private int field_2_first_col;
    private int field_3_last_col;
    private short field_4_height;
    private short field_5_optimize;
    private short field_6_reserved;
    private int field_7_option_flags;
    private short field_8_xf_index;
    private static final BitField outlineLevel = BitFieldFactory.getInstance(7);
    private static final BitField colapsed = BitFieldFactory.getInstance(16);
    private static final BitField zeroHeight = BitFieldFactory.getInstance(32);
    private static final BitField badFontHeight = BitFieldFactory.getInstance(64);
    private static final BitField formatted = BitFieldFactory.getInstance(128);

    @Override // org.apache.poi.hssf.record.StandardRecord
    protected int getDataSize() {
        return 16;
    }

    @Override // org.apache.poi.hssf.record.Record
    public short getSid() {
        return (short) 520;
    }

    public RowRecord(int i) {
        this.field_1_row_number = i;
        this.field_4_height = (short) 255;
        this.field_5_optimize = (short) 0;
        this.field_6_reserved = (short) 0;
        this.field_7_option_flags = 256;
        this.field_8_xf_index = (short) 15;
        setEmpty();
    }

    public RowRecord(RecordInputStream recordInputStream) {
        this.field_1_row_number = recordInputStream.readUShort();
        this.field_2_first_col = recordInputStream.readShort();
        this.field_3_last_col = recordInputStream.readShort();
        this.field_4_height = recordInputStream.readShort();
        this.field_5_optimize = recordInputStream.readShort();
        this.field_6_reserved = recordInputStream.readShort();
        this.field_7_option_flags = recordInputStream.readShort();
        this.field_8_xf_index = recordInputStream.readShort();
    }

    public void setEmpty() {
        this.field_2_first_col = 0;
        this.field_3_last_col = 0;
    }

    public boolean isEmpty() {
        return (this.field_2_first_col | this.field_3_last_col) == 0;
    }

    public void setRowNumber(int i) {
        this.field_1_row_number = i;
    }

    public void setFirstCol(int i) {
        this.field_2_first_col = i;
    }

    public void setLastCol(int i) {
        this.field_3_last_col = i;
    }

    public void setHeight(short s) {
        this.field_4_height = s;
    }

    public void setOptimize(short s) {
        this.field_5_optimize = s;
    }

    public void setOutlineLevel(short s) {
        this.field_7_option_flags = outlineLevel.setValue(this.field_7_option_flags, s);
    }

    public void setColapsed(boolean z) {
        this.field_7_option_flags = colapsed.setBoolean(this.field_7_option_flags, z);
    }

    public void setZeroHeight(boolean z) {
        this.field_7_option_flags = zeroHeight.setBoolean(this.field_7_option_flags, z);
    }

    public void setBadFontHeight(boolean z) {
        this.field_7_option_flags = badFontHeight.setBoolean(this.field_7_option_flags, z);
    }

    public void setFormatted(boolean z) {
        this.field_7_option_flags = formatted.setBoolean(this.field_7_option_flags, z);
    }

    public void setXFIndex(short s) {
        this.field_8_xf_index = s;
    }

    public int getRowNumber() {
        return this.field_1_row_number;
    }

    public int getFirstCol() {
        return this.field_2_first_col;
    }

    public int getLastCol() {
        return this.field_3_last_col;
    }

    public short getHeight() {
        return this.field_4_height;
    }

    public short getOptimize() {
        return this.field_5_optimize;
    }

    public short getOptionFlags() {
        return (short) this.field_7_option_flags;
    }

    public short getOutlineLevel() {
        return (short) outlineLevel.getValue(this.field_7_option_flags);
    }

    public boolean getColapsed() {
        return colapsed.isSet(this.field_7_option_flags);
    }

    public boolean getZeroHeight() {
        return zeroHeight.isSet(this.field_7_option_flags);
    }

    public boolean getBadFontHeight() {
        return badFontHeight.isSet(this.field_7_option_flags);
    }

    public boolean getFormatted() {
        return formatted.isSet(this.field_7_option_flags);
    }

    public short getXFIndex() {
        return this.field_8_xf_index;
    }

    @Override // org.apache.poi.hssf.record.Record
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[ROW]\n");
        stringBuffer.append("    .rownumber      = ");
        stringBuffer.append(Integer.toHexString(getRowNumber()));
        stringBuffer.append("\n");
        stringBuffer.append("    .firstcol       = ");
        stringBuffer.append(HexDump.shortToHex(getFirstCol()));
        stringBuffer.append("\n");
        stringBuffer.append("    .lastcol        = ");
        stringBuffer.append(HexDump.shortToHex(getLastCol()));
        stringBuffer.append("\n");
        stringBuffer.append("    .height         = ");
        stringBuffer.append(HexDump.shortToHex(getHeight()));
        stringBuffer.append("\n");
        stringBuffer.append("    .optimize       = ");
        stringBuffer.append(HexDump.shortToHex(getOptimize()));
        stringBuffer.append("\n");
        stringBuffer.append("    .reserved       = ");
        stringBuffer.append(HexDump.shortToHex(this.field_6_reserved));
        stringBuffer.append("\n");
        stringBuffer.append("    .optionflags    = ");
        stringBuffer.append(HexDump.shortToHex(getOptionFlags()));
        stringBuffer.append("\n");
        stringBuffer.append("        .outlinelvl = ");
        stringBuffer.append(Integer.toHexString(getOutlineLevel()));
        stringBuffer.append("\n");
        stringBuffer.append("        .colapsed   = ");
        stringBuffer.append(getColapsed());
        stringBuffer.append("\n");
        stringBuffer.append("        .zeroheight = ");
        stringBuffer.append(getZeroHeight());
        stringBuffer.append("\n");
        stringBuffer.append("        .badfontheig= ");
        stringBuffer.append(getBadFontHeight());
        stringBuffer.append("\n");
        stringBuffer.append("        .formatted  = ");
        stringBuffer.append(getFormatted());
        stringBuffer.append("\n");
        stringBuffer.append("    .xfindex        = ");
        stringBuffer.append(Integer.toHexString(getXFIndex()));
        stringBuffer.append("\n");
        stringBuffer.append("[/ROW]\n");
        return stringBuffer.toString();
    }

    @Override // org.apache.poi.hssf.record.StandardRecord
    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getRowNumber());
        littleEndianOutput.writeShort(getFirstCol() == -1 ? 0 : getFirstCol());
        littleEndianOutput.writeShort(getLastCol() != -1 ? getLastCol() : 0);
        littleEndianOutput.writeShort(getHeight());
        littleEndianOutput.writeShort(getOptimize());
        littleEndianOutput.writeShort(this.field_6_reserved);
        littleEndianOutput.writeShort(getOptionFlags());
        littleEndianOutput.writeShort(getXFIndex());
    }

    @Override // org.apache.poi.hssf.record.Record
    public Object clone() {
        RowRecord rowRecord = new RowRecord(this.field_1_row_number);
        rowRecord.field_2_first_col = this.field_2_first_col;
        rowRecord.field_3_last_col = this.field_3_last_col;
        rowRecord.field_4_height = this.field_4_height;
        rowRecord.field_5_optimize = this.field_5_optimize;
        rowRecord.field_6_reserved = this.field_6_reserved;
        rowRecord.field_7_option_flags = this.field_7_option_flags;
        rowRecord.field_8_xf_index = this.field_8_xf_index;
        return rowRecord;
    }
}
