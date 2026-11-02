package org.apache.poi.hssf.record;

import org.apache.poi.hssf.record.cont.ContinuableRecord;
import org.apache.poi.hssf.record.cont.ContinuableRecordOutput;
import org.apache.poi.util.StringUtil;

/* loaded from: classes3.dex */
public final class StringRecord extends ContinuableRecord {
    public static final short sid = 519;
    private boolean _is16bitUnicode;
    private String _text;

    @Override // org.apache.poi.hssf.record.Record
    public short getSid() {
        return (short) 519;
    }

    public StringRecord() {
    }

    public StringRecord(RecordInputStream recordInputStream) throws RecordFormatException {
        int uShort = recordInputStream.readUShort();
        boolean z = recordInputStream.readByte() != 0;
        this._is16bitUnicode = z;
        if (z) {
            this._text = recordInputStream.readUnicodeLEString(uShort);
        } else {
            this._text = recordInputStream.readCompressedUnicode(uShort);
        }
    }

    @Override // org.apache.poi.hssf.record.cont.ContinuableRecord
    protected void serialize(ContinuableRecordOutput continuableRecordOutput) {
        continuableRecordOutput.writeShort(this._text.length());
        continuableRecordOutput.writeStringData(this._text);
    }

    public String getString() {
        return this._text;
    }

    public void setString(String str) {
        this._text = str;
        this._is16bitUnicode = StringUtil.hasMultibyte(str);
    }

    @Override // org.apache.poi.hssf.record.Record
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[STRING]\n");
        stringBuffer.append("    .string            = ");
        stringBuffer.append(this._text);
        stringBuffer.append("\n");
        stringBuffer.append("[/STRING]\n");
        return stringBuffer.toString();
    }

    @Override // org.apache.poi.hssf.record.Record
    public Object clone() {
        StringRecord stringRecord = new StringRecord();
        stringRecord._is16bitUnicode = this._is16bitUnicode;
        stringRecord._text = this._text;
        return stringRecord;
    }
}
