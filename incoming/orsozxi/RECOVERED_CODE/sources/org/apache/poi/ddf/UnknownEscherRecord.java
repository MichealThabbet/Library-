package org.apache.poi.ddf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndian;

/* loaded from: classes3.dex */
public final class UnknownEscherRecord extends EscherRecord {
    private static final byte[] NO_BYTES = new byte[0];
    private byte[] thedata = NO_BYTES;
    private List<EscherRecord> _childRecords = new ArrayList();

    @Override // org.apache.poi.ddf.EscherRecord
    public int fillFields(byte[] bArr, int i, EscherRecordFactory escherRecordFactory) {
        int header = readHeader(bArr, i);
        int i2 = 8;
        int i3 = i + 8;
        int length = bArr.length - i3;
        if (header > length) {
            header = length;
        }
        if (isContainerRecord()) {
            this.thedata = new byte[0];
            while (header > 0) {
                EscherRecord escherRecordCreateRecord = escherRecordFactory.createRecord(bArr, i3);
                int iFillFields = escherRecordCreateRecord.fillFields(bArr, i3, escherRecordFactory);
                i2 += iFillFields;
                i3 += iFillFields;
                header -= iFillFields;
                getChildRecords().add(escherRecordCreateRecord);
            }
            return i2;
        }
        byte[] bArr2 = new byte[header];
        this.thedata = bArr2;
        System.arraycopy(bArr, i3, bArr2, 0, header);
        return header + 8;
    }

    @Override // org.apache.poi.ddf.EscherRecord
    public int serialize(int i, byte[] bArr, EscherSerializationListener escherSerializationListener) {
        escherSerializationListener.beforeRecordSerialize(i, getRecordId(), this);
        LittleEndian.putShort(bArr, i, getOptions());
        LittleEndian.putShort(bArr, i + 2, getRecordId());
        int length = this.thedata.length;
        Iterator<EscherRecord> it2 = this._childRecords.iterator();
        while (it2.hasNext()) {
            length += it2.next().getRecordSize();
        }
        LittleEndian.putInt(bArr, i + 4, length);
        byte[] bArr2 = this.thedata;
        int i2 = i + 8;
        System.arraycopy(bArr2, 0, bArr, i2, bArr2.length);
        int length2 = i2 + this.thedata.length;
        Iterator<EscherRecord> it3 = this._childRecords.iterator();
        while (it3.hasNext()) {
            length2 += it3.next().serialize(length2, bArr, escherSerializationListener);
        }
        int i3 = length2 - i;
        escherSerializationListener.afterRecordSerialize(length2, getRecordId(), i3, this);
        return i3;
    }

    public byte[] getData() {
        return this.thedata;
    }

    @Override // org.apache.poi.ddf.EscherRecord
    public int getRecordSize() {
        return this.thedata.length + 8;
    }

    @Override // org.apache.poi.ddf.EscherRecord
    public List<EscherRecord> getChildRecords() {
        return this._childRecords;
    }

    @Override // org.apache.poi.ddf.EscherRecord
    public void setChildRecords(List<EscherRecord> list) {
        this._childRecords = list;
    }

    @Override // org.apache.poi.ddf.EscherRecord
    public Object clone() {
        return super.clone();
    }

    @Override // org.apache.poi.ddf.EscherRecord
    public String getRecordName() {
        return "Unknown 0x" + HexDump.toHex(getRecordId());
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (getChildRecords().size() > 0) {
            stringBuffer.append("  children: \n");
            Iterator<EscherRecord> it2 = this._childRecords.iterator();
            while (it2.hasNext()) {
                stringBuffer.append(it2.next().toString());
                stringBuffer.append('\n');
            }
        }
        return getClass().getName() + ":\n  isContainer: " + isContainerRecord() + "\n  options: 0x" + HexDump.toHex(getOptions()) + "\n  recordId: 0x" + HexDump.toHex(getRecordId()) + "\n  numchildren: " + getChildRecords().size() + '\n' + HexDump.toHex(this.thedata, 32) + stringBuffer.toString();
    }

    public void addChildRecord(EscherRecord escherRecord) {
        getChildRecords().add(escherRecord);
    }
}
