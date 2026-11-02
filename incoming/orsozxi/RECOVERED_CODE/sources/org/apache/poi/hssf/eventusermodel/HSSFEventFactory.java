package org.apache.poi.hssf.eventusermodel;

import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.RecordFactoryInputStream;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/* loaded from: classes3.dex */
public class HSSFEventFactory {
    public void processWorkbookEvents(HSSFRequest hSSFRequest, POIFSFileSystem pOIFSFileSystem) throws IOException {
        processWorkbookEvents(hSSFRequest, pOIFSFileSystem.getRoot());
    }

    public void processWorkbookEvents(HSSFRequest hSSFRequest, DirectoryNode directoryNode) throws IOException {
        processEvents(hSSFRequest, directoryNode.createDocumentInputStream("Workbook"));
    }

    public short abortableProcessWorkbookEvents(HSSFRequest hSSFRequest, POIFSFileSystem pOIFSFileSystem) throws HSSFUserException, IOException {
        return abortableProcessWorkbookEvents(hSSFRequest, pOIFSFileSystem.getRoot());
    }

    public short abortableProcessWorkbookEvents(HSSFRequest hSSFRequest, DirectoryNode directoryNode) throws HSSFUserException, IOException {
        return abortableProcessEvents(hSSFRequest, directoryNode.createDocumentInputStream("Workbook"));
    }

    public void processEvents(HSSFRequest hSSFRequest, InputStream inputStream) {
        try {
            genericProcessEvents(hSSFRequest, inputStream);
        } catch (HSSFUserException unused) {
        }
    }

    public short abortableProcessEvents(HSSFRequest hSSFRequest, InputStream inputStream) throws HSSFUserException {
        return genericProcessEvents(hSSFRequest, inputStream);
    }

    private short genericProcessEvents(HSSFRequest hSSFRequest, InputStream inputStream) throws HSSFUserException {
        short sProcessRecord = 0;
        RecordFactoryInputStream recordFactoryInputStream = new RecordFactoryInputStream(inputStream, false);
        do {
            Record recordNextRecord = recordFactoryInputStream.nextRecord();
            if (recordNextRecord == null) {
                break;
            }
            sProcessRecord = hSSFRequest.processRecord(recordNextRecord);
        } while (sProcessRecord == 0);
        return sProcessRecord;
    }
}
