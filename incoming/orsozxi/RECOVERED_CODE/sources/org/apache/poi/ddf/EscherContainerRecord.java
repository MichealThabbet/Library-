package org.apache.poi.ddf;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndian;

/* loaded from: classes3.dex */
public final class EscherContainerRecord extends EscherRecord {
    public static final short BSTORE_CONTAINER = -4095;
    public static final short DGG_CONTAINER = -4096;
    public static final short DG_CONTAINER = -4094;
    public static final short SOLVER_CONTAINER = -4091;
    public static final short SPGR_CONTAINER = -4093;
    public static final short SP_CONTAINER = -4092;
    private final List<EscherRecord> _childRecords = new ArrayList();

    @Override // org.apache.poi.ddf.EscherRecord
    public int fillFields(byte[] bArr, int i, EscherRecordFactory escherRecordFactory) {
        int header = readHeader(bArr, i);
        int i2 = 8;
        int i3 = i + 8;
        while (header > 0 && i3 < bArr.length) {
            EscherRecord escherRecordCreateRecord = escherRecordFactory.createRecord(bArr, i3);
            int iFillFields = escherRecordCreateRecord.fillFields(bArr, i3, escherRecordFactory);
            i2 += iFillFields;
            i3 += iFillFields;
            header -= iFillFields;
            addChildRecord(escherRecordCreateRecord);
            if (i3 >= bArr.length && header > 0) {
                System.out.println("WARNING: " + header + " bytes remaining but no space left");
            }
        }
        return i2;
    }

    @Override // org.apache.poi.ddf.EscherRecord
    public int serialize(int i, byte[] bArr, EscherSerializationListener escherSerializationListener) {
        escherSerializationListener.beforeRecordSerialize(i, getRecordId(), this);
        LittleEndian.putShort(bArr, i, getOptions());
        LittleEndian.putShort(bArr, i + 2, getRecordId());
        Iterator<EscherRecord> it2 = this._childRecords.iterator();
        int recordSize = 0;
        while (it2.hasNext()) {
            recordSize += it2.next().getRecordSize();
        }
        LittleEndian.putInt(bArr, i + 4, recordSize);
        int iSerialize = i + 8;
        Iterator<EscherRecord> it3 = this._childRecords.iterator();
        while (it3.hasNext()) {
            iSerialize += it3.next().serialize(iSerialize, bArr, escherSerializationListener);
        }
        int i2 = iSerialize - i;
        escherSerializationListener.afterRecordSerialize(iSerialize, getRecordId(), i2, this);
        return i2;
    }

    @Override // org.apache.poi.ddf.EscherRecord
    public int getRecordSize() {
        Iterator<EscherRecord> it2 = this._childRecords.iterator();
        int recordSize = 0;
        while (it2.hasNext()) {
            recordSize += it2.next().getRecordSize();
        }
        return recordSize + 8;
    }

    public boolean hasChildOfType(short s) {
        Iterator<EscherRecord> it2 = this._childRecords.iterator();
        while (it2.hasNext()) {
            if (it2.next().getRecordId() == s) {
                return true;
            }
        }
        return false;
    }

    @Override // org.apache.poi.ddf.EscherRecord
    public EscherRecord getChild(int i) {
        return this._childRecords.get(i);
    }

    @Override // org.apache.poi.ddf.EscherRecord
    public List<EscherRecord> getChildRecords() {
        return new ArrayList(this._childRecords);
    }

    public Iterator<EscherRecord> getChildIterator() {
        return new ReadOnlyIterator(this._childRecords);
    }

    private static final class ReadOnlyIterator implements Iterator<EscherRecord> {
        private int _index = 0;
        private final List<EscherRecord> _list;

        public ReadOnlyIterator(List<EscherRecord> list) {
            this._list = list;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this._index < this._list.size();
        }

        @Override // java.util.Iterator
        public EscherRecord next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            List<EscherRecord> list = this._list;
            int i = this._index;
            this._index = i + 1;
            return list.get(i);
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override // org.apache.poi.ddf.EscherRecord
    public void setChildRecords(List<EscherRecord> list) {
        List<EscherRecord> list2 = this._childRecords;
        if (list == list2) {
            throw new IllegalStateException("Child records private data member has escaped");
        }
        list2.clear();
        this._childRecords.addAll(list);
    }

    public boolean removeChildRecord(EscherRecord escherRecord) {
        return this._childRecords.remove(escherRecord);
    }

    public List<EscherContainerRecord> getChildContainers() {
        ArrayList arrayList = new ArrayList();
        for (EscherRecord escherRecord : this._childRecords) {
            if (escherRecord instanceof EscherContainerRecord) {
                arrayList.add((EscherContainerRecord) escherRecord);
            }
        }
        return arrayList;
    }

    @Override // org.apache.poi.ddf.EscherRecord
    public String getRecordName() {
        switch (getRecordId()) {
            case -4096:
                return "DggContainer";
            case -4095:
                return "BStoreContainer";
            case -4094:
                return "DgContainer";
            case -4093:
                return "SpgrContainer";
            case -4092:
                return "SpContainer";
            case -4091:
                return "SolverContainer";
            default:
                return "Container 0x" + HexDump.toHex(getRecordId());
        }
    }

    @Override // org.apache.poi.ddf.EscherRecord
    public void display(PrintWriter printWriter, int i) {
        super.display(printWriter, i);
        Iterator<EscherRecord> it2 = this._childRecords.iterator();
        while (it2.hasNext()) {
            it2.next().display(printWriter, i + 1);
        }
    }

    public void addChildRecord(EscherRecord escherRecord) {
        this._childRecords.add(escherRecord);
    }

    public void addChildBefore(EscherRecord escherRecord, int i) {
        int i2 = 0;
        while (i2 < this._childRecords.size()) {
            if (this._childRecords.get(i2).getRecordId() == i) {
                this._childRecords.add(i2, escherRecord);
                i2++;
            }
            i2++;
        }
    }

    public String toString() {
        return toString("");
    }

    public String toString(String str) {
        String property = System.getProperty("line.separator");
        StringBuffer stringBuffer = new StringBuffer();
        if (this._childRecords.size() > 0) {
            stringBuffer.append("  children: " + property);
            int i = 0;
            for (EscherRecord escherRecord : this._childRecords) {
                String str2 = str + "   ";
                stringBuffer.append(str2 + "Child " + i + ":" + property);
                if (escherRecord instanceof EscherContainerRecord) {
                    stringBuffer.append(((EscherContainerRecord) escherRecord).toString(str2));
                } else {
                    stringBuffer.append(escherRecord.toString());
                }
                i++;
            }
        }
        return str + getClass().getName() + " (" + getRecordName() + "):" + property + str + "  isContainer: " + isContainerRecord() + property + str + "  options: 0x" + HexDump.toHex(getOptions()) + property + str + "  recordId: 0x" + HexDump.toHex(getRecordId()) + property + str + "  numchildren: " + this._childRecords.size() + property + str + stringBuffer.toString();
    }

    public EscherSpRecord getChildById(short s) {
        for (EscherRecord escherRecord : this._childRecords) {
            if (escherRecord.getRecordId() == s) {
                return (EscherSpRecord) escherRecord;
            }
        }
        return null;
    }

    public void getRecordsById(short s, List<EscherRecord> list) {
        for (EscherRecord escherRecord : this._childRecords) {
            if (escherRecord instanceof EscherContainerRecord) {
                ((EscherContainerRecord) escherRecord).getRecordsById(s, list);
            } else if (escherRecord.getRecordId() == s) {
                list.add(escherRecord);
            }
        }
    }
}
