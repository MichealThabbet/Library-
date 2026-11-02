package org.apache.poi.hssf.usermodel;

import java.io.IOException;
import org.apache.poi.hssf.record.EmbeddedObjectRefSubRecord;
import org.apache.poi.hssf.record.ObjRecord;
import org.apache.poi.hssf.record.SubRecord;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.Entry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.util.HexDump;

/* loaded from: classes3.dex */
public final class HSSFObjectData {
    private final POIFSFileSystem _poifs;
    private final ObjRecord _record;

    public HSSFObjectData(ObjRecord objRecord, POIFSFileSystem pOIFSFileSystem) {
        this._record = objRecord;
        this._poifs = pOIFSFileSystem;
    }

    public String getOLE2ClassName() {
        return findObjectRecord().getOLEClassName();
    }

    public DirectoryEntry getDirectory() throws IOException {
        String str = "MBD" + HexDump.toHex(findObjectRecord().getStreamId().intValue());
        Entry entry = this._poifs.getRoot().getEntry(str);
        if (entry instanceof DirectoryEntry) {
            return (DirectoryEntry) entry;
        }
        throw new IOException("Stream " + str + " was not an OLE2 directory");
    }

    public byte[] getObjectData() {
        return findObjectRecord().getObjectData();
    }

    public boolean hasDirectoryEntry() {
        Integer streamId = findObjectRecord().getStreamId();
        return (streamId == null || streamId.intValue() == 0) ? false : true;
    }

    protected EmbeddedObjectRefSubRecord findObjectRecord() {
        for (SubRecord subRecord : this._record.getSubRecords()) {
            if (subRecord instanceof EmbeddedObjectRefSubRecord) {
                return (EmbeddedObjectRefSubRecord) subRecord;
            }
        }
        throw new IllegalStateException("Object data does not contain a reference to an embedded object OLE2 directory");
    }
}
