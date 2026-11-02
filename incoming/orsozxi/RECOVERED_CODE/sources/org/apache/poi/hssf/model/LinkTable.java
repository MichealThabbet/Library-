package org.apache.poi.hssf.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.record.CRNCountRecord;
import org.apache.poi.hssf.record.CRNRecord;
import org.apache.poi.hssf.record.ExternSheetRecord;
import org.apache.poi.hssf.record.ExternalNameRecord;
import org.apache.poi.hssf.record.NameCommentRecord;
import org.apache.poi.hssf.record.NameRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SupBookRecord;
import org.apache.poi.hssf.record.formula.NameXPtg;

/* loaded from: classes3.dex */
final class LinkTable {
    private final List<NameRecord> _definedNames;
    private final ExternSheetRecord _externSheetRecord;
    private final ExternalBookBlock[] _externalBookBlocks;
    private final int _recordCount;
    private final WorkbookRecordList _workbookRecordList;

    private static final class CRNBlock {
        private final CRNCountRecord _countRecord;
        private final CRNRecord[] _crns;

        public CRNBlock(RecordStream recordStream) {
            CRNCountRecord cRNCountRecord = (CRNCountRecord) recordStream.getNext();
            this._countRecord = cRNCountRecord;
            int numberOfCRNs = cRNCountRecord.getNumberOfCRNs();
            CRNRecord[] cRNRecordArr = new CRNRecord[numberOfCRNs];
            for (int i = 0; i < numberOfCRNs; i++) {
                cRNRecordArr[i] = (CRNRecord) recordStream.getNext();
            }
            this._crns = cRNRecordArr;
        }

        public CRNRecord[] getCrns() {
            return (CRNRecord[]) this._crns.clone();
        }
    }

    private static final class ExternalBookBlock {
        private final CRNBlock[] _crnBlocks;
        private final SupBookRecord _externalBookRecord;
        private final ExternalNameRecord[] _externalNameRecords;

        public ExternalBookBlock(RecordStream recordStream) {
            this._externalBookRecord = (SupBookRecord) recordStream.getNext();
            ArrayList arrayList = new ArrayList();
            while (recordStream.peekNextClass() == ExternalNameRecord.class) {
                arrayList.add(recordStream.getNext());
            }
            ExternalNameRecord[] externalNameRecordArr = new ExternalNameRecord[arrayList.size()];
            this._externalNameRecords = externalNameRecordArr;
            arrayList.toArray(externalNameRecordArr);
            arrayList.clear();
            while (recordStream.peekNextClass() == CRNCountRecord.class) {
                arrayList.add(new CRNBlock(recordStream));
            }
            CRNBlock[] cRNBlockArr = new CRNBlock[arrayList.size()];
            this._crnBlocks = cRNBlockArr;
            arrayList.toArray(cRNBlockArr);
        }

        public ExternalBookBlock(int i) {
            this._externalBookRecord = SupBookRecord.createInternalReferences((short) i);
            this._externalNameRecords = new ExternalNameRecord[0];
            this._crnBlocks = new CRNBlock[0];
        }

        public SupBookRecord getExternalBookRecord() {
            return this._externalBookRecord;
        }

        public String getNameText(int i) {
            return this._externalNameRecords[i].getText();
        }

        public int getNameIx(int i) {
            return this._externalNameRecords[i].getIx();
        }

        public int getIndexOfName(String str) {
            int i = 0;
            while (true) {
                ExternalNameRecord[] externalNameRecordArr = this._externalNameRecords;
                if (i >= externalNameRecordArr.length) {
                    return -1;
                }
                if (externalNameRecordArr[i].getText().equalsIgnoreCase(str)) {
                    return i;
                }
                i++;
            }
        }
    }

    public LinkTable(List list, int i, WorkbookRecordList workbookRecordList, Map<String, NameCommentRecord> map) {
        this._workbookRecordList = workbookRecordList;
        RecordStream recordStream = new RecordStream(list, i);
        ArrayList arrayList = new ArrayList();
        while (recordStream.peekNextClass() == SupBookRecord.class) {
            arrayList.add(new ExternalBookBlock(recordStream));
        }
        ExternalBookBlock[] externalBookBlockArr = new ExternalBookBlock[arrayList.size()];
        this._externalBookBlocks = externalBookBlockArr;
        arrayList.toArray(externalBookBlockArr);
        arrayList.clear();
        if (this._externalBookBlocks.length <= 0 || recordStream.peekNextClass() != ExternSheetRecord.class) {
            this._externSheetRecord = null;
        } else {
            this._externSheetRecord = readExtSheetRecord(recordStream);
        }
        this._definedNames = new ArrayList();
        while (true) {
            Class clsPeekNextClass = recordStream.peekNextClass();
            if (clsPeekNextClass == NameRecord.class) {
                this._definedNames.add((NameRecord) recordStream.getNext());
            } else if (clsPeekNextClass == NameCommentRecord.class) {
                NameCommentRecord nameCommentRecord = (NameCommentRecord) recordStream.getNext();
                map.put(nameCommentRecord.getNameText(), nameCommentRecord);
            } else {
                this._recordCount = recordStream.getCountRead();
                this._workbookRecordList.getRecords().addAll(list.subList(i, this._recordCount + i));
                return;
            }
        }
    }

    private static ExternSheetRecord readExtSheetRecord(RecordStream recordStream) {
        ArrayList arrayList = new ArrayList(2);
        while (recordStream.peekNextClass() == ExternSheetRecord.class) {
            arrayList.add((ExternSheetRecord) recordStream.getNext());
        }
        int size = arrayList.size();
        if (size >= 1) {
            if (size == 1) {
                return (ExternSheetRecord) arrayList.get(0);
            }
            ExternSheetRecord[] externSheetRecordArr = new ExternSheetRecord[size];
            arrayList.toArray(externSheetRecordArr);
            return ExternSheetRecord.combine(externSheetRecordArr);
        }
        throw new RuntimeException("Expected an EXTERNSHEET record but got (" + recordStream.peekNextClass().getName() + ")");
    }

    public LinkTable(int i, WorkbookRecordList workbookRecordList) {
        this._workbookRecordList = workbookRecordList;
        this._definedNames = new ArrayList();
        this._externalBookBlocks = new ExternalBookBlock[]{new ExternalBookBlock(i)};
        this._externSheetRecord = new ExternSheetRecord();
        this._recordCount = 2;
        SupBookRecord externalBookRecord = this._externalBookBlocks[0].getExternalBookRecord();
        int iFindFirstRecordLocBySid = findFirstRecordLocBySid((short) 140);
        if (iFindFirstRecordLocBySid < 0) {
            throw new RuntimeException("CountryRecord not found");
        }
        int i2 = iFindFirstRecordLocBySid + 1;
        this._workbookRecordList.add(i2, this._externSheetRecord);
        this._workbookRecordList.add(i2, externalBookRecord);
    }

    public int getRecordCount() {
        return this._recordCount;
    }

    public NameRecord getSpecificBuiltinRecord(byte b, int i) {
        for (NameRecord nameRecord : this._definedNames) {
            if (nameRecord.getBuiltInName() == b && nameRecord.getSheetNumber() == i) {
                return nameRecord;
            }
        }
        return null;
    }

    public void removeBuiltinRecord(byte b, int i) {
        NameRecord specificBuiltinRecord = getSpecificBuiltinRecord(b, i);
        if (specificBuiltinRecord != null) {
            this._definedNames.remove(specificBuiltinRecord);
        }
    }

    public int getNumNames() {
        return this._definedNames.size();
    }

    public NameRecord getNameRecord(int i) {
        return this._definedNames.get(i);
    }

    public void addName(NameRecord nameRecord) {
        this._definedNames.add(nameRecord);
        int iFindFirstRecordLocBySid = findFirstRecordLocBySid((short) 23);
        if (iFindFirstRecordLocBySid == -1) {
            iFindFirstRecordLocBySid = findFirstRecordLocBySid(SupBookRecord.sid);
        }
        if (iFindFirstRecordLocBySid == -1) {
            iFindFirstRecordLocBySid = findFirstRecordLocBySid((short) 140);
        }
        this._workbookRecordList.add(iFindFirstRecordLocBySid + this._definedNames.size(), nameRecord);
    }

    public void removeName(int i) {
        this._definedNames.remove(i);
    }

    public boolean nameAlreadyExists(NameRecord nameRecord) {
        for (int numNames = getNumNames() - 1; numNames >= 0; numNames--) {
            NameRecord nameRecord2 = getNameRecord(numNames);
            if (nameRecord2 != nameRecord && isDuplicatedNames(nameRecord, nameRecord2)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDuplicatedNames(NameRecord nameRecord, NameRecord nameRecord2) {
        return nameRecord2.getNameText().equalsIgnoreCase(nameRecord.getNameText()) && isSameSheetNames(nameRecord, nameRecord2);
    }

    private static boolean isSameSheetNames(NameRecord nameRecord, NameRecord nameRecord2) {
        return nameRecord2.getSheetNumber() == nameRecord.getSheetNumber();
    }

    public String[] getExternalBookAndSheetName(int i) {
        SupBookRecord externalBookRecord = this._externalBookBlocks[this._externSheetRecord.getExtbookIndexFromRefIndex(i)].getExternalBookRecord();
        if (!externalBookRecord.isExternalReferences()) {
            return null;
        }
        int firstSheetIndexFromRefIndex = this._externSheetRecord.getFirstSheetIndexFromRefIndex(i);
        return new String[]{externalBookRecord.getURL(), firstSheetIndexFromRefIndex >= 0 ? externalBookRecord.getSheetNames()[firstSheetIndexFromRefIndex] : null};
    }

    public int getExternalSheetIndex(String str, String str2) {
        SupBookRecord externalBookRecord;
        int i = 0;
        while (true) {
            ExternalBookBlock[] externalBookBlockArr = this._externalBookBlocks;
            if (i >= externalBookBlockArr.length) {
                externalBookRecord = null;
                i = -1;
                break;
            }
            externalBookRecord = externalBookBlockArr[i].getExternalBookRecord();
            if (externalBookRecord.isExternalReferences() && str.equals(externalBookRecord.getURL())) {
                break;
            }
            i++;
        }
        if (externalBookRecord == null) {
            throw new RuntimeException("No external workbook with name '" + str + "'");
        }
        int sheetIndex = getSheetIndex(externalBookRecord.getSheetNames(), str2);
        int refIxForSheet = this._externSheetRecord.getRefIxForSheet(i, sheetIndex);
        if (refIxForSheet >= 0) {
            return refIxForSheet;
        }
        throw new RuntimeException("ExternSheetRecord does not contain combination (" + i + ", " + sheetIndex + ")");
    }

    private static int getSheetIndex(String[] strArr, String str) {
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i].equals(str)) {
                return i;
            }
        }
        throw new RuntimeException("External workbook does not contain sheet '" + str + "'");
    }

    public int getIndexToInternalSheet(int i) {
        return this._externSheetRecord.getFirstSheetIndexFromRefIndex(i);
    }

    public int getSheetIndexFromExternSheetIndex(int i) {
        if (i >= this._externSheetRecord.getNumOfRefs()) {
            return -1;
        }
        return this._externSheetRecord.getFirstSheetIndexFromRefIndex(i);
    }

    public int checkExternSheet(int i) {
        int i2 = 0;
        while (true) {
            ExternalBookBlock[] externalBookBlockArr = this._externalBookBlocks;
            if (i2 >= externalBookBlockArr.length) {
                i2 = -1;
                break;
            }
            if (externalBookBlockArr[i2].getExternalBookRecord().isInternalReferences()) {
                break;
            }
            i2++;
        }
        if (i2 < 0) {
            throw new RuntimeException("Could not find 'internal references' EXTERNALBOOK");
        }
        int refIxForSheet = this._externSheetRecord.getRefIxForSheet(i2, i);
        return refIxForSheet >= 0 ? refIxForSheet : this._externSheetRecord.addRef(i2, i, i);
    }

    private int findFirstRecordLocBySid(short s) {
        Iterator<Record> it2 = this._workbookRecordList.iterator();
        int i = 0;
        while (it2.hasNext()) {
            if (it2.next().getSid() == s) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public String resolveNameXText(int i, int i2) {
        return this._externalBookBlocks[this._externSheetRecord.getExtbookIndexFromRefIndex(i)].getNameText(i2);
    }

    public int resolveNameXIx(int i, int i2) {
        return this._externalBookBlocks[this._externSheetRecord.getExtbookIndexFromRefIndex(i)].getNameIx(i2);
    }

    public NameXPtg getNameXPtg(String str) {
        int iFindRefIndexFromExtBookIndex;
        int i = 0;
        while (true) {
            ExternalBookBlock[] externalBookBlockArr = this._externalBookBlocks;
            if (i >= externalBookBlockArr.length) {
                return null;
            }
            int indexOfName = externalBookBlockArr[i].getIndexOfName(str);
            if (indexOfName >= 0 && (iFindRefIndexFromExtBookIndex = findRefIndexFromExtBookIndex(i)) >= 0) {
                return new NameXPtg(iFindRefIndexFromExtBookIndex, indexOfName);
            }
            i++;
        }
    }

    private int findRefIndexFromExtBookIndex(int i) {
        return this._externSheetRecord.findRefIndexFromExtBookIndex(i);
    }
}
