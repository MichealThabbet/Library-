package org.apache.poi.hssf.eventusermodel;

import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.model.InternalWorkbook;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.EOFRecord;
import org.apache.poi.hssf.record.ExternSheetRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.record.SupBookRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/* loaded from: classes3.dex */
public class EventWorkbookBuilder {
    public static InternalWorkbook createStubWorkbook(ExternSheetRecord[] externSheetRecordArr, BoundSheetRecord[] boundSheetRecordArr, SSTRecord sSTRecord) {
        ArrayList arrayList = new ArrayList();
        if (boundSheetRecordArr != null) {
            for (BoundSheetRecord boundSheetRecord : boundSheetRecordArr) {
                arrayList.add(boundSheetRecord);
            }
        }
        if (sSTRecord != null) {
            arrayList.add(sSTRecord);
        }
        if (externSheetRecordArr != null) {
            arrayList.add(SupBookRecord.createInternalReferences((short) externSheetRecordArr.length));
            for (ExternSheetRecord externSheetRecord : externSheetRecordArr) {
                arrayList.add(externSheetRecord);
            }
        }
        arrayList.add(EOFRecord.instance);
        return InternalWorkbook.createWorkbook(arrayList);
    }

    public static InternalWorkbook createStubWorkbook(ExternSheetRecord[] externSheetRecordArr, BoundSheetRecord[] boundSheetRecordArr) {
        return createStubWorkbook(externSheetRecordArr, boundSheetRecordArr, null);
    }

    public static class SheetRecordCollectingListener implements HSSFListener {
        private HSSFListener childListener;
        private List boundSheetRecords = new ArrayList();
        private List externSheetRecords = new ArrayList();
        private SSTRecord sstRecord = null;

        public SheetRecordCollectingListener(HSSFListener hSSFListener) {
            this.childListener = hSSFListener;
        }

        public BoundSheetRecord[] getBoundSheetRecords() {
            List list = this.boundSheetRecords;
            return (BoundSheetRecord[]) list.toArray(new BoundSheetRecord[list.size()]);
        }

        public ExternSheetRecord[] getExternSheetRecords() {
            List list = this.externSheetRecords;
            return (ExternSheetRecord[]) list.toArray(new ExternSheetRecord[list.size()]);
        }

        public SSTRecord getSSTRecord() {
            return this.sstRecord;
        }

        public HSSFWorkbook getStubHSSFWorkbook() {
            return HSSFWorkbook.create(getStubWorkbook());
        }

        public InternalWorkbook getStubWorkbook() {
            return EventWorkbookBuilder.createStubWorkbook(getExternSheetRecords(), getBoundSheetRecords(), getSSTRecord());
        }

        @Override // org.apache.poi.hssf.eventusermodel.HSSFListener
        public void processRecord(Record record) {
            processRecordInternally(record);
            this.childListener.processRecord(record);
        }

        public void processRecordInternally(Record record) {
            if (record instanceof BoundSheetRecord) {
                this.boundSheetRecords.add(record);
            } else if (record instanceof ExternSheetRecord) {
                this.externSheetRecords.add(record);
            } else if (record instanceof SSTRecord) {
                this.sstRecord = (SSTRecord) record;
            }
        }
    }
}
