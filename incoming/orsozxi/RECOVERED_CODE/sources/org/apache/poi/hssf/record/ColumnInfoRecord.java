package org.apache.poi.hssf.record;

import it.piapostolos.android.pinotes.R2;
import org.apache.poi.util.BitField;
import org.apache.poi.util.BitFieldFactory;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class ColumnInfoRecord extends StandardRecord {
    public static final short sid = 125;
    private int _colWidth;
    private int _firstCol;
    private int _lastCol;
    private int _options;
    private int _xfIndex;
    private int field_6_reserved;
    private static final BitField hidden = BitFieldFactory.getInstance(1);
    private static final BitField outlevel = BitFieldFactory.getInstance(R2.dimen.md_notitle_vertical_padding_more);
    private static final BitField collapsed = BitFieldFactory.getInstance(4096);

    @Override // org.apache.poi.hssf.record.StandardRecord
    protected int getDataSize() {
        return 12;
    }

    @Override // org.apache.poi.hssf.record.Record
    public short getSid() {
        return (short) 125;
    }

    public ColumnInfoRecord() {
        setColumnWidth(R2.drawable.ic_keyboard_black_24dp);
        this._options = 2;
        this._xfIndex = 15;
        this.field_6_reserved = 2;
    }

    public ColumnInfoRecord(RecordInputStream recordInputStream) {
        this._firstCol = recordInputStream.readUShort();
        this._lastCol = recordInputStream.readUShort();
        this._colWidth = recordInputStream.readUShort();
        this._xfIndex = recordInputStream.readUShort();
        this._options = recordInputStream.readUShort();
        int iRemaining = recordInputStream.remaining();
        if (iRemaining == 0) {
            this.field_6_reserved = 0;
            return;
        }
        if (iRemaining == 1) {
            this.field_6_reserved = recordInputStream.readByte();
            return;
        }
        if (iRemaining == 2) {
            this.field_6_reserved = recordInputStream.readUShort();
            return;
        }
        throw new RuntimeException("Unusual record size remaining=(" + recordInputStream.remaining() + ")");
    }

    public void setFirstColumn(int i) {
        this._firstCol = i;
    }

    public void setLastColumn(int i) {
        this._lastCol = i;
    }

    public void setColumnWidth(int i) {
        this._colWidth = i;
    }

    public void setXFIndex(int i) {
        this._xfIndex = i;
    }

    public void setHidden(boolean z) {
        this._options = hidden.setBoolean(this._options, z);
    }

    public void setOutlineLevel(int i) {
        this._options = outlevel.setValue(this._options, i);
    }

    public void setCollapsed(boolean z) {
        this._options = collapsed.setBoolean(this._options, z);
    }

    public int getFirstColumn() {
        return this._firstCol;
    }

    public int getLastColumn() {
        return this._lastCol;
    }

    public int getColumnWidth() {
        return this._colWidth;
    }

    public int getXFIndex() {
        return this._xfIndex;
    }

    public boolean getHidden() {
        return hidden.isSet(this._options);
    }

    public int getOutlineLevel() {
        return outlevel.getValue(this._options);
    }

    public boolean getCollapsed() {
        return collapsed.isSet(this._options);
    }

    public boolean containsColumn(int i) {
        return this._firstCol <= i && i <= this._lastCol;
    }

    public boolean isAdjacentBefore(ColumnInfoRecord columnInfoRecord) {
        return this._lastCol == columnInfoRecord._firstCol - 1;
    }

    public boolean formatMatches(ColumnInfoRecord columnInfoRecord) {
        return this._xfIndex == columnInfoRecord._xfIndex && this._options == columnInfoRecord._options && this._colWidth == columnInfoRecord._colWidth;
    }

    @Override // org.apache.poi.hssf.record.StandardRecord
    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getFirstColumn());
        littleEndianOutput.writeShort(getLastColumn());
        littleEndianOutput.writeShort(getColumnWidth());
        littleEndianOutput.writeShort(getXFIndex());
        littleEndianOutput.writeShort(this._options);
        littleEndianOutput.writeShort(this.field_6_reserved);
    }

    @Override // org.apache.poi.hssf.record.Record
    public String toString() {
        return "[COLINFO]\n  colfirst = " + getFirstColumn() + "\n  collast  = " + getLastColumn() + "\n  colwidth = " + getColumnWidth() + "\n  xfindex  = " + getXFIndex() + "\n  options  = " + HexDump.shortToHex(this._options) + "\n    hidden   = " + getHidden() + "\n    olevel   = " + getOutlineLevel() + "\n    collapsed= " + getCollapsed() + "\n[/COLINFO]\n";
    }

    @Override // org.apache.poi.hssf.record.Record
    public Object clone() {
        ColumnInfoRecord columnInfoRecord = new ColumnInfoRecord();
        columnInfoRecord._firstCol = this._firstCol;
        columnInfoRecord._lastCol = this._lastCol;
        columnInfoRecord._colWidth = this._colWidth;
        columnInfoRecord._xfIndex = this._xfIndex;
        columnInfoRecord._options = this._options;
        columnInfoRecord.field_6_reserved = this.field_6_reserved;
        return columnInfoRecord;
    }
}
