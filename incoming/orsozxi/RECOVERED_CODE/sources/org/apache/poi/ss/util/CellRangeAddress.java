package org.apache.poi.ss.util;

import org.apache.poi.hssf.record.RecordInputStream;
import org.apache.poi.util.LittleEndianByteArrayOutputStream;
import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public class CellRangeAddress extends CellRangeAddressBase {
    public static final int ENCODED_SIZE = 8;

    public static int getEncodedSize(int i) {
        return i * 8;
    }

    public CellRangeAddress(int i, int i2, int i3, int i4) {
        super(i, i2, i3, i4);
    }

    public int serialize(int i, byte[] bArr) {
        serialize(new LittleEndianByteArrayOutputStream(bArr, i, 8));
        return 8;
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getFirstRow());
        littleEndianOutput.writeShort(getLastRow());
        littleEndianOutput.writeShort(getFirstColumn());
        littleEndianOutput.writeShort(getLastColumn());
    }

    public CellRangeAddress(RecordInputStream recordInputStream) {
        super(readUShortAndCheck(recordInputStream), recordInputStream.readUShort(), recordInputStream.readUShort(), recordInputStream.readUShort());
    }

    private static int readUShortAndCheck(RecordInputStream recordInputStream) {
        if (recordInputStream.remaining() < 8) {
            throw new RuntimeException("Ran out of data reading CellRangeAddress");
        }
        return recordInputStream.readUShort();
    }

    public CellRangeAddress copy() {
        return new CellRangeAddress(getFirstRow(), getLastRow(), getFirstColumn(), getLastColumn());
    }

    public String formatAsString() {
        StringBuffer stringBuffer = new StringBuffer();
        CellReference cellReference = new CellReference(getFirstRow(), getFirstColumn());
        CellReference cellReference2 = new CellReference(getLastRow(), getLastColumn());
        stringBuffer.append(cellReference.formatAsString());
        if (!cellReference.equals(cellReference2)) {
            stringBuffer.append(':');
            stringBuffer.append(cellReference2.formatAsString());
        }
        return stringBuffer.toString();
    }

    public static CellRangeAddress valueOf(String str) {
        CellReference cellReference;
        CellReference cellReference2;
        int iIndexOf = str.indexOf(":");
        if (iIndexOf == -1) {
            cellReference2 = new CellReference(str);
            cellReference = cellReference2;
        } else {
            CellReference cellReference3 = new CellReference(str.substring(0, iIndexOf));
            cellReference = new CellReference(str.substring(iIndexOf + 1));
            cellReference2 = cellReference3;
        }
        return new CellRangeAddress(cellReference2.getRow(), cellReference.getRow(), cellReference2.getCol(), cellReference.getCol());
    }
}
