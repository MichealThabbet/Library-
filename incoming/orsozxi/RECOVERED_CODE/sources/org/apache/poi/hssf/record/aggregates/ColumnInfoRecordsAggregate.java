package org.apache.poi.hssf.record.aggregates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.poi.hssf.model.RecordStream;
import org.apache.poi.hssf.record.ColumnInfoRecord;
import org.apache.poi.hssf.record.aggregates.RecordAggregate;

/* loaded from: classes3.dex */
public final class ColumnInfoRecordsAggregate extends RecordAggregate {
    private final List records;

    private static final class CIRComparator implements Comparator {
        public static final Comparator instance = new CIRComparator();

        private CIRComparator() {
        }

        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            return compareColInfos((ColumnInfoRecord) obj, (ColumnInfoRecord) obj2);
        }

        public static int compareColInfos(ColumnInfoRecord columnInfoRecord, ColumnInfoRecord columnInfoRecord2) {
            return columnInfoRecord.getFirstColumn() - columnInfoRecord2.getFirstColumn();
        }
    }

    public ColumnInfoRecordsAggregate() {
        this.records = new ArrayList();
    }

    public ColumnInfoRecordsAggregate(RecordStream recordStream) {
        this();
        ColumnInfoRecord columnInfoRecord = null;
        boolean z = true;
        while (recordStream.peekNextClass() == ColumnInfoRecord.class) {
            ColumnInfoRecord columnInfoRecord2 = (ColumnInfoRecord) recordStream.getNext();
            this.records.add(columnInfoRecord2);
            if (columnInfoRecord != null && CIRComparator.compareColInfos(columnInfoRecord, columnInfoRecord2) > 0) {
                z = false;
            }
            columnInfoRecord = columnInfoRecord2;
        }
        if (this.records.size() < 1) {
            throw new RuntimeException("No column info records found");
        }
        if (z) {
            return;
        }
        Collections.sort(this.records, CIRComparator.instance);
    }

    public Object clone() {
        ColumnInfoRecordsAggregate columnInfoRecordsAggregate = new ColumnInfoRecordsAggregate();
        for (int i = 0; i < this.records.size(); i++) {
            columnInfoRecordsAggregate.records.add(((ColumnInfoRecord) this.records.get(i)).clone());
        }
        return columnInfoRecordsAggregate;
    }

    public void insertColumn(ColumnInfoRecord columnInfoRecord) {
        this.records.add(columnInfoRecord);
        Collections.sort(this.records, CIRComparator.instance);
    }

    private void insertColumn(int i, ColumnInfoRecord columnInfoRecord) {
        this.records.add(i, columnInfoRecord);
    }

    int getNumColumns() {
        return this.records.size();
    }

    @Override // org.apache.poi.hssf.record.aggregates.RecordAggregate
    public void visitContainedRecords(RecordAggregate.RecordVisitor recordVisitor) {
        int size = this.records.size();
        if (size < 1) {
            return;
        }
        ColumnInfoRecord columnInfoRecord = null;
        int i = 0;
        while (i < size) {
            ColumnInfoRecord columnInfoRecord2 = (ColumnInfoRecord) this.records.get(i);
            recordVisitor.visitRecord(columnInfoRecord2);
            if (columnInfoRecord != null && CIRComparator.compareColInfos(columnInfoRecord, columnInfoRecord2) > 0) {
                throw new RuntimeException("Column info records are out of order");
            }
            i++;
            columnInfoRecord = columnInfoRecord2;
        }
    }

    private int findStartOfColumnOutlineGroup(int i) {
        ColumnInfoRecord columnInfoRecord = (ColumnInfoRecord) this.records.get(i);
        int outlineLevel = columnInfoRecord.getOutlineLevel();
        while (i != 0) {
            ColumnInfoRecord columnInfoRecord2 = (ColumnInfoRecord) this.records.get(i - 1);
            if (!columnInfoRecord2.isAdjacentBefore(columnInfoRecord) || columnInfoRecord2.getOutlineLevel() < outlineLevel) {
                break;
            }
            i--;
            columnInfoRecord = columnInfoRecord2;
        }
        return i;
    }

    private int findEndOfColumnOutlineGroup(int i) {
        ColumnInfoRecord columnInfoRecord = (ColumnInfoRecord) this.records.get(i);
        int outlineLevel = columnInfoRecord.getOutlineLevel();
        while (i < this.records.size() - 1) {
            int i2 = i + 1;
            ColumnInfoRecord columnInfoRecord2 = (ColumnInfoRecord) this.records.get(i2);
            if (!columnInfoRecord.isAdjacentBefore(columnInfoRecord2) || columnInfoRecord2.getOutlineLevel() < outlineLevel) {
                break;
            }
            columnInfoRecord = columnInfoRecord2;
            i = i2;
        }
        return i;
    }

    private ColumnInfoRecord getColInfo(int i) {
        return (ColumnInfoRecord) this.records.get(i);
    }

    private boolean isColumnGroupCollapsed(int i) {
        int iFindEndOfColumnOutlineGroup = findEndOfColumnOutlineGroup(i);
        int i2 = iFindEndOfColumnOutlineGroup + 1;
        if (i2 >= this.records.size()) {
            return false;
        }
        ColumnInfoRecord colInfo = getColInfo(i2);
        if (getColInfo(iFindEndOfColumnOutlineGroup).isAdjacentBefore(colInfo)) {
            return colInfo.getCollapsed();
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean isColumnGroupHiddenByParent(int r5) {
        /*
            r4 = this;
            int r0 = r4.findEndOfColumnOutlineGroup(r5)
            java.util.List r1 = r4.records
            int r1 = r1.size()
            r2 = 0
            if (r0 >= r1) goto L26
            int r1 = r0 + 1
            org.apache.poi.hssf.record.ColumnInfoRecord r1 = r4.getColInfo(r1)
            org.apache.poi.hssf.record.ColumnInfoRecord r0 = r4.getColInfo(r0)
            boolean r0 = r0.isAdjacentBefore(r1)
            if (r0 == 0) goto L26
            int r0 = r1.getOutlineLevel()
            boolean r1 = r1.getHidden()
            goto L28
        L26:
            r0 = 0
            r1 = 0
        L28:
            int r5 = r4.findStartOfColumnOutlineGroup(r5)
            if (r5 <= 0) goto L47
            int r3 = r5 + (-1)
            org.apache.poi.hssf.record.ColumnInfoRecord r3 = r4.getColInfo(r3)
            org.apache.poi.hssf.record.ColumnInfoRecord r5 = r4.getColInfo(r5)
            boolean r5 = r3.isAdjacentBefore(r5)
            if (r5 == 0) goto L47
            int r2 = r3.getOutlineLevel()
            boolean r5 = r3.getHidden()
            goto L48
        L47:
            r5 = 0
        L48:
            if (r0 <= r2) goto L4b
            return r1
        L4b:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.poi.hssf.record.aggregates.ColumnInfoRecordsAggregate.isColumnGroupHiddenByParent(int):boolean");
    }

    public void collapseColumn(int i) {
        int iFindColInfoIdx = findColInfoIdx(i, 0);
        if (iFindColInfoIdx == -1) {
            return;
        }
        int iFindStartOfColumnOutlineGroup = findStartOfColumnOutlineGroup(iFindColInfoIdx);
        setColumn(setGroupHidden(iFindStartOfColumnOutlineGroup, getColInfo(iFindStartOfColumnOutlineGroup).getOutlineLevel(), true) + 1, null, null, null, null, Boolean.TRUE);
    }

    private int setGroupHidden(int i, int i2, boolean z) {
        ColumnInfoRecord colInfo = getColInfo(i);
        while (i < this.records.size()) {
            colInfo.setHidden(z);
            i++;
            if (i < this.records.size()) {
                ColumnInfoRecord colInfo2 = getColInfo(i);
                if (!colInfo.isAdjacentBefore(colInfo2) || colInfo2.getOutlineLevel() < i2) {
                    break;
                }
                colInfo = colInfo2;
            }
        }
        return colInfo.getLastColumn();
    }

    public void expandColumn(int i) {
        int iFindColInfoIdx = findColInfoIdx(i, 0);
        if (iFindColInfoIdx != -1 && isColumnGroupCollapsed(iFindColInfoIdx)) {
            int iFindEndOfColumnOutlineGroup = findEndOfColumnOutlineGroup(iFindColInfoIdx);
            ColumnInfoRecord colInfo = getColInfo(iFindEndOfColumnOutlineGroup);
            if (!isColumnGroupHiddenByParent(iFindColInfoIdx)) {
                int outlineLevel = colInfo.getOutlineLevel();
                for (int iFindStartOfColumnOutlineGroup = findStartOfColumnOutlineGroup(iFindColInfoIdx); iFindStartOfColumnOutlineGroup <= iFindEndOfColumnOutlineGroup; iFindStartOfColumnOutlineGroup++) {
                    ColumnInfoRecord colInfo2 = getColInfo(iFindStartOfColumnOutlineGroup);
                    if (outlineLevel == colInfo2.getOutlineLevel()) {
                        colInfo2.setHidden(false);
                    }
                }
            }
            setColumn(colInfo.getLastColumn() + 1, null, null, null, null, Boolean.FALSE);
        }
    }

    private static ColumnInfoRecord copyColInfo(ColumnInfoRecord columnInfoRecord) {
        return (ColumnInfoRecord) columnInfoRecord.clone();
    }

    public void setColumn(int i, Short sh, Integer num, Integer num2, Boolean bool, Boolean bool2) {
        ColumnInfoRecord columnInfoRecord;
        int i2 = 0;
        while (i2 < this.records.size()) {
            columnInfoRecord = (ColumnInfoRecord) this.records.get(i2);
            if (columnInfoRecord.containsColumn(i)) {
                break;
            } else if (columnInfoRecord.getFirstColumn() > i) {
                break;
            } else {
                i2++;
            }
        }
        columnInfoRecord = null;
        ColumnInfoRecord columnInfoRecord2 = columnInfoRecord;
        if (columnInfoRecord2 == null) {
            ColumnInfoRecord columnInfoRecord3 = new ColumnInfoRecord();
            columnInfoRecord3.setFirstColumn(i);
            columnInfoRecord3.setLastColumn(i);
            setColumnInfoFields(columnInfoRecord3, sh, num, num2, bool, bool2);
            insertColumn(i2, columnInfoRecord3);
            attemptMergeColInfoRecords(i2);
            return;
        }
        if ((sh != null && columnInfoRecord2.getXFIndex() != sh.shortValue()) || (num != null && columnInfoRecord2.getColumnWidth() != num.shortValue()) || (num2 != null && columnInfoRecord2.getOutlineLevel() != num2.intValue()) || (bool != null && columnInfoRecord2.getHidden() != bool.booleanValue()) || (bool2 != null && columnInfoRecord2.getCollapsed() != bool2.booleanValue())) {
            if (columnInfoRecord2.getFirstColumn() == i && columnInfoRecord2.getLastColumn() == i) {
                setColumnInfoFields(columnInfoRecord2, sh, num, num2, bool, bool2);
                attemptMergeColInfoRecords(i2);
                return;
            }
            if (columnInfoRecord2.getFirstColumn() == i || columnInfoRecord2.getLastColumn() == i) {
                if (columnInfoRecord2.getFirstColumn() == i) {
                    columnInfoRecord2.setFirstColumn(i + 1);
                } else {
                    columnInfoRecord2.setLastColumn(i - 1);
                    i2++;
                }
                ColumnInfoRecord columnInfoRecordCopyColInfo = copyColInfo(columnInfoRecord2);
                columnInfoRecordCopyColInfo.setFirstColumn(i);
                columnInfoRecordCopyColInfo.setLastColumn(i);
                setColumnInfoFields(columnInfoRecordCopyColInfo, sh, num, num2, bool, bool2);
                insertColumn(i2, columnInfoRecordCopyColInfo);
                attemptMergeColInfoRecords(i2);
                return;
            }
            ColumnInfoRecord columnInfoRecordCopyColInfo2 = copyColInfo(columnInfoRecord2);
            ColumnInfoRecord columnInfoRecordCopyColInfo3 = copyColInfo(columnInfoRecord2);
            int lastColumn = columnInfoRecord2.getLastColumn();
            columnInfoRecord2.setLastColumn(i - 1);
            columnInfoRecordCopyColInfo2.setFirstColumn(i);
            columnInfoRecordCopyColInfo2.setLastColumn(i);
            setColumnInfoFields(columnInfoRecordCopyColInfo2, sh, num, num2, bool, bool2);
            int i3 = i2 + 1;
            insertColumn(i3, columnInfoRecordCopyColInfo2);
            columnInfoRecordCopyColInfo3.setFirstColumn(i + 1);
            columnInfoRecordCopyColInfo3.setLastColumn(lastColumn);
            insertColumn(i3 + 1, columnInfoRecordCopyColInfo3);
        }
    }

    private static void setColumnInfoFields(ColumnInfoRecord columnInfoRecord, Short sh, Integer num, Integer num2, Boolean bool, Boolean bool2) {
        if (sh != null) {
            columnInfoRecord.setXFIndex(sh.shortValue());
        }
        if (num != null) {
            columnInfoRecord.setColumnWidth(num.intValue());
        }
        if (num2 != null) {
            columnInfoRecord.setOutlineLevel(num2.shortValue());
        }
        if (bool != null) {
            columnInfoRecord.setHidden(bool.booleanValue());
        }
        if (bool2 != null) {
            columnInfoRecord.setCollapsed(bool2.booleanValue());
        }
    }

    private int findColInfoIdx(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("column parameter out of range: " + i);
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("fromIdx parameter out of range: " + i2);
        }
        while (i2 < this.records.size()) {
            ColumnInfoRecord colInfo = getColInfo(i2);
            if (colInfo.containsColumn(i)) {
                return i2;
            }
            if (colInfo.getFirstColumn() > i) {
                return -1;
            }
            i2++;
        }
        return -1;
    }

    private void attemptMergeColInfoRecords(int i) {
        int size = this.records.size();
        if (i < 0 || i >= size) {
            StringBuilder sb = new StringBuilder();
            sb.append("colInfoIx ");
            sb.append(i);
            sb.append(" is out of range (0..");
            sb.append(size - 1);
            sb.append(")");
            throw new IllegalArgumentException(sb.toString());
        }
        ColumnInfoRecord colInfo = getColInfo(i);
        int i2 = i + 1;
        if (i2 < size && mergeColInfoRecords(colInfo, getColInfo(i2))) {
            this.records.remove(i2);
        }
        if (i <= 0 || !mergeColInfoRecords(getColInfo(i - 1), colInfo)) {
            return;
        }
        this.records.remove(i);
    }

    private static boolean mergeColInfoRecords(ColumnInfoRecord columnInfoRecord, ColumnInfoRecord columnInfoRecord2) {
        if (!columnInfoRecord.isAdjacentBefore(columnInfoRecord2) || !columnInfoRecord.formatMatches(columnInfoRecord2)) {
            return false;
        }
        columnInfoRecord.setLastColumn(columnInfoRecord2.getLastColumn());
        return true;
    }

    public void groupColumnRange(int i, int i2, boolean z) {
        int iMax;
        int iMin;
        int i3 = 0;
        while (i <= i2) {
            int iFindColInfoIdx = findColInfoIdx(i, i3);
            if (iFindColInfoIdx != -1) {
                int outlineLevel = getColInfo(iFindColInfoIdx).getOutlineLevel();
                iMin = Math.min(7, Math.max(0, z ? outlineLevel + 1 : outlineLevel - 1));
                iMax = Math.max(0, iFindColInfoIdx - 1);
            } else {
                iMax = i3;
                iMin = 1;
            }
            setColumn(i, null, null, Integer.valueOf(iMin), null, null);
            i++;
            i3 = iMax;
        }
    }

    public ColumnInfoRecord findColumnInfo(int i) {
        int size = this.records.size();
        for (int i2 = 0; i2 < size; i2++) {
            ColumnInfoRecord colInfo = getColInfo(i2);
            if (colInfo.containsColumn(i)) {
                return colInfo;
            }
        }
        return null;
    }

    public int getMaxOutlineLevel() {
        int size = this.records.size();
        int iMax = 0;
        for (int i = 0; i < size; i++) {
            iMax = Math.max(getColInfo(i).getOutlineLevel(), iMax);
        }
        return iMax;
    }
}
