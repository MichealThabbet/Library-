package org.apache.poi.hssf.record;

import it.piapostolos.android.pinotes.R2;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class UserSViewEnd extends StandardRecord {
    public static final short sid = 427;
    private byte[] _rawData;

    @Override // org.apache.poi.hssf.record.Record
    public short getSid() {
        return sid;
    }

    public UserSViewEnd(byte[] bArr) {
        this._rawData = bArr;
    }

    public UserSViewEnd(RecordInputStream recordInputStream) {
        this._rawData = recordInputStream.readRemainder();
    }

    @Override // org.apache.poi.hssf.record.StandardRecord
    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.write(this._rawData);
    }

    @Override // org.apache.poi.hssf.record.StandardRecord
    protected int getDataSize() {
        return this._rawData.length;
    }

    @Override // org.apache.poi.hssf.record.Record
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        stringBuffer.append("USERSVIEWEND");
        stringBuffer.append("] (0x");
        stringBuffer.append(Integer.toHexString(R2.attr.errorIconTintMode).toUpperCase() + ")\n");
        stringBuffer.append("  rawData=");
        stringBuffer.append(HexDump.toHex(this._rawData));
        stringBuffer.append("\n");
        stringBuffer.append("[/");
        stringBuffer.append("USERSVIEWEND");
        stringBuffer.append("]\n");
        return stringBuffer.toString();
    }

    @Override // org.apache.poi.hssf.record.Record
    public Object clone() {
        return cloneViaReserialise();
    }
}
