package org.apache.poi.poifs.storage;

import com.ibm.icu.text.DateFormat;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.POILogFactory;
import org.apache.poi.util.POILogger;

/* loaded from: classes3.dex */
public class RawDataBlock implements ListManagedBlock {
    private static POILogger log = POILogFactory.getLogger(RawDataBlock.class);
    private byte[] _data;
    private boolean _eof;
    private boolean _hasData;

    public RawDataBlock(InputStream inputStream) throws IOException {
        this(inputStream, 512);
    }

    public RawDataBlock(InputStream inputStream, int i) throws IOException {
        byte[] bArr = new byte[i];
        this._data = bArr;
        int fully = IOUtils.readFully(inputStream, bArr);
        this._hasData = fully > 0;
        if (fully == -1) {
            this._eof = true;
            return;
        }
        if (fully != i) {
            this._eof = true;
            StringBuilder sb = new StringBuilder();
            sb.append(" byte");
            sb.append(fully == 1 ? "" : DateFormat.SECOND);
            String string = sb.toString();
            log.log(POILogger.ERROR, "Unable to read entire block; " + fully + string + " read before EOF; expected " + i + " bytes. Your document was either written by software that ignores the spec, or has been truncated!");
            return;
        }
        this._eof = false;
    }

    public boolean eof() {
        return this._eof;
    }

    public boolean hasData() {
        return this._hasData;
    }

    public String toString() {
        return "RawDataBlock of size " + this._data.length;
    }

    @Override // org.apache.poi.poifs.storage.ListManagedBlock
    public byte[] getData() throws IOException {
        if (!hasData()) {
            throw new IOException("Cannot return empty data");
        }
        return this._data;
    }

    public int getBigBlockSize() {
        return this._data.length;
    }
}
