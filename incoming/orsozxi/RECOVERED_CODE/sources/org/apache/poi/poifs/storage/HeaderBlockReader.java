package org.apache.poi.poifs.storage;

import com.ibm.icu.text.DateFormat;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.poifs.common.POIFSBigBlockSize;
import org.apache.poi.poifs.common.POIFSConstants;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.LittleEndian;

/* loaded from: classes3.dex */
public final class HeaderBlockReader {
    private final int _bat_count;
    private final byte[] _data;
    private final int _property_start;
    private final int _sbat_count;
    private final int _sbat_start;
    private final int _xbat_count;
    private final int _xbat_start;
    private final POIFSBigBlockSize bigBlockSize;

    public HeaderBlockReader(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[32];
        int fully = IOUtils.readFully(inputStream, bArr);
        if (fully != 32) {
            throw alertShortRead(fully, 32);
        }
        long j = LittleEndian.getLong(bArr, 0);
        if (j != HeaderBlockConstants._signature) {
            byte[] bArr2 = POIFSConstants.OOXML_FILE_HEADER;
            if (bArr[0] == bArr2[0] && bArr[1] == bArr2[1] && bArr[2] == bArr2[2] && bArr[3] == bArr2[3]) {
                throw new OfficeXmlFileException("The supplied data appears to be in the Office 2007+ XML. You are calling the part of POI that deals with OLE2 Office Documents. You need to call a different part of POI to process this data (eg XSSF instead of HSSF)");
            }
            if (((-31525197391593473L) & j) == 4503608217567241L) {
                throw new IllegalArgumentException("The supplied data appears to be in BIFF2 format.  POI only supports BIFF8 format");
            }
            throw new IOException("Invalid header signature; read " + longToHex(j) + ", expected " + longToHex(HeaderBlockConstants._signature));
        }
        byte b = bArr[30];
        if (b == 9) {
            this.bigBlockSize = POIFSConstants.SMALLER_BIG_BLOCK_SIZE_DETAILS;
        } else if (b == 12) {
            this.bigBlockSize = POIFSConstants.LARGER_BIG_BLOCK_SIZE_DETAILS;
        } else {
            throw new IOException("Unsupported blocksize  (2^" + ((int) bArr[30]) + "). Expected 2^9 or 2^12.");
        }
        byte[] bArr3 = new byte[this.bigBlockSize.getBigBlockSize()];
        this._data = bArr3;
        System.arraycopy(bArr, 0, bArr3, 0, 32);
        byte[] bArr4 = this._data;
        int fully2 = IOUtils.readFully(inputStream, bArr4, 32, bArr4.length - 32);
        if (fully + fully2 != this.bigBlockSize.getBigBlockSize()) {
            throw alertShortRead(fully2, this.bigBlockSize.getBigBlockSize());
        }
        this._bat_count = getInt(44, this._data);
        this._property_start = getInt(48, this._data);
        this._sbat_start = getInt(60, this._data);
        this._sbat_count = getInt(64, this._data);
        this._xbat_start = getInt(68, this._data);
        this._xbat_count = getInt(72, this._data);
    }

    private static int getInt(int i, byte[] bArr) {
        return LittleEndian.getInt(bArr, i);
    }

    private static String longToHex(long j) {
        return new String(HexDump.longToHex(j));
    }

    private static IOException alertShortRead(int i, int i2) {
        if (i < 0) {
            i = 0;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" byte");
        sb.append(i == 1 ? "" : DateFormat.SECOND);
        return new IOException("Unable to read entire header; " + i + sb.toString() + " read; expected " + i2 + " bytes");
    }

    public int getPropertyStart() {
        return this._property_start;
    }

    public int getSBATStart() {
        return this._sbat_start;
    }

    public int getSBATCount() {
        return this._sbat_count;
    }

    public int getBATCount() {
        return this._bat_count;
    }

    public int[] getBATArray() {
        int[] iArr = new int[109];
        int i = 76;
        for (int i2 = 0; i2 < 109; i2++) {
            iArr[i2] = LittleEndian.getInt(this._data, i);
            i += 4;
        }
        return iArr;
    }

    public int getXBATCount() {
        return this._xbat_count;
    }

    public int getXBATIndex() {
        return this._xbat_start;
    }

    public POIFSBigBlockSize getBigBlockSize() {
        return this.bigBlockSize;
    }
}
