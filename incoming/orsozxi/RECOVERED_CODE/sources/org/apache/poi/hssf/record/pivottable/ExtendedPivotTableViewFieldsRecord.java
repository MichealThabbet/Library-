package org.apache.poi.hssf.record.pivottable;

import org.apache.poi.hssf.record.RecordFormatException;
import org.apache.poi.hssf.record.RecordInputStream;
import org.apache.poi.hssf.record.StandardRecord;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndianOutput;
import org.apache.poi.util.StringUtil;

/* loaded from: classes3.dex */
public final class ExtendedPivotTableViewFieldsRecord extends StandardRecord {
    private static final int STRING_NOT_PRESENT_LEN = 65535;
    public static final short sid = 256;
    private int _citmShow;
    private int _grbit1;
    private int _grbit2;
    private int _isxdiShow;
    private int _isxdiSort;
    private int _reserved1;
    private int _reserved2;
    private String _subtotalName;

    @Override // org.apache.poi.hssf.record.Record
    public short getSid() {
        return (short) 256;
    }

    public ExtendedPivotTableViewFieldsRecord(RecordInputStream recordInputStream) throws RecordFormatException {
        this._grbit1 = recordInputStream.readInt();
        this._grbit2 = recordInputStream.readUByte();
        this._citmShow = recordInputStream.readUByte();
        this._isxdiSort = recordInputStream.readUShort();
        this._isxdiShow = recordInputStream.readUShort();
        int iRemaining = recordInputStream.remaining();
        if (iRemaining == 0) {
            this._reserved1 = 0;
            this._reserved2 = 0;
            this._subtotalName = null;
        } else {
            if (iRemaining != 10) {
                throw new RecordFormatException("Unexpected remaining size (" + recordInputStream.remaining() + ")");
            }
            int uShort = recordInputStream.readUShort();
            this._reserved1 = recordInputStream.readInt();
            this._reserved2 = recordInputStream.readInt();
            if (uShort != 65535) {
                this._subtotalName = recordInputStream.readUnicodeLEString(uShort);
            }
        }
    }

    @Override // org.apache.poi.hssf.record.StandardRecord
    protected void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeInt(this._grbit1);
        littleEndianOutput.writeByte(this._grbit2);
        littleEndianOutput.writeByte(this._citmShow);
        littleEndianOutput.writeShort(this._isxdiSort);
        littleEndianOutput.writeShort(this._isxdiShow);
        String str = this._subtotalName;
        if (str == null) {
            littleEndianOutput.writeShort(65535);
        } else {
            littleEndianOutput.writeShort(str.length());
        }
        littleEndianOutput.writeInt(this._reserved1);
        littleEndianOutput.writeInt(this._reserved2);
        String str2 = this._subtotalName;
        if (str2 != null) {
            StringUtil.putUnicodeLE(str2, littleEndianOutput);
        }
    }

    @Override // org.apache.poi.hssf.record.StandardRecord
    protected int getDataSize() {
        String str = this._subtotalName;
        return (str == null ? 0 : str.length() * 2) + 20;
    }

    @Override // org.apache.poi.hssf.record.Record
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[SXVDEX]\n");
        stringBuffer.append("    .grbit1 =");
        stringBuffer.append(HexDump.intToHex(this._grbit1));
        stringBuffer.append("\n");
        stringBuffer.append("    .grbit2 =");
        stringBuffer.append(HexDump.byteToHex(this._grbit2));
        stringBuffer.append("\n");
        stringBuffer.append("    .citmShow =");
        stringBuffer.append(HexDump.byteToHex(this._citmShow));
        stringBuffer.append("\n");
        stringBuffer.append("    .isxdiSort =");
        stringBuffer.append(HexDump.shortToHex(this._isxdiSort));
        stringBuffer.append("\n");
        stringBuffer.append("    .isxdiShow =");
        stringBuffer.append(HexDump.shortToHex(this._isxdiShow));
        stringBuffer.append("\n");
        stringBuffer.append("    .subtotalName =");
        stringBuffer.append(this._subtotalName);
        stringBuffer.append("\n");
        stringBuffer.append("[/SXVDEX]\n");
        return stringBuffer.toString();
    }
}
