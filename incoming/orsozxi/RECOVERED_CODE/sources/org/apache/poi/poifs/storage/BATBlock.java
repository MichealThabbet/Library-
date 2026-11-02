package org.apache.poi.poifs.storage;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import org.apache.poi.poifs.common.POIFSBigBlockSize;
import org.apache.poi.util.IntegerField;

/* loaded from: classes3.dex */
public final class BATBlock extends BigBlock {
    private static final byte _default_value = -1;
    private byte[] _data;
    private IntegerField[] _fields;

    @Override // org.apache.poi.poifs.storage.BigBlock, org.apache.poi.poifs.storage.BlockWritable
    public /* bridge */ /* synthetic */ void writeBlocks(OutputStream outputStream) throws IOException {
        super.writeBlocks(outputStream);
    }

    private BATBlock(POIFSBigBlockSize pOIFSBigBlockSize) {
        super(pOIFSBigBlockSize);
        int bATEntriesPerBlock = pOIFSBigBlockSize.getBATEntriesPerBlock();
        byte[] bArr = new byte[pOIFSBigBlockSize.getBigBlockSize()];
        this._data = bArr;
        Arrays.fill(bArr, (byte) -1);
        this._fields = new IntegerField[bATEntriesPerBlock];
        int i = 0;
        for (int i2 = 0; i2 < bATEntriesPerBlock; i2++) {
            this._fields[i2] = new IntegerField(i);
            i += 4;
        }
    }

    public static BATBlock[] createBATBlocks(POIFSBigBlockSize pOIFSBigBlockSize, int[] iArr) {
        BATBlock[] bATBlockArr = new BATBlock[calculateStorageRequirements(pOIFSBigBlockSize, iArr.length)];
        int length = iArr.length;
        int bATEntriesPerBlock = pOIFSBigBlockSize.getBATEntriesPerBlock();
        int i = 0;
        int i2 = 0;
        while (i < iArr.length) {
            int i3 = i2 + 1;
            bATBlockArr[i2] = new BATBlock(pOIFSBigBlockSize, iArr, i, length > bATEntriesPerBlock ? i + bATEntriesPerBlock : iArr.length);
            length -= bATEntriesPerBlock;
            i += bATEntriesPerBlock;
            i2 = i3;
        }
        return bATBlockArr;
    }

    public static BATBlock[] createXBATBlocks(POIFSBigBlockSize pOIFSBigBlockSize, int[] iArr, int i) throws ArrayIndexOutOfBoundsException {
        int iCalculateXBATStorageRequirements = calculateXBATStorageRequirements(pOIFSBigBlockSize, iArr.length);
        BATBlock[] bATBlockArr = new BATBlock[iCalculateXBATStorageRequirements];
        int length = iArr.length;
        int xBATEntriesPerBlock = pOIFSBigBlockSize.getXBATEntriesPerBlock();
        if (iCalculateXBATStorageRequirements != 0) {
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            while (i3 < iArr.length) {
                int i5 = i4 + 1;
                bATBlockArr[i4] = new BATBlock(pOIFSBigBlockSize, iArr, i3, length > xBATEntriesPerBlock ? i3 + xBATEntriesPerBlock : iArr.length);
                length -= xBATEntriesPerBlock;
                i3 += xBATEntriesPerBlock;
                i4 = i5;
            }
            while (i2 < iCalculateXBATStorageRequirements - 1) {
                bATBlockArr[i2].setXBATChain(pOIFSBigBlockSize, i + i2 + 1);
                i2++;
            }
            bATBlockArr[i2].setXBATChain(pOIFSBigBlockSize, -2);
        }
        return bATBlockArr;
    }

    public static int calculateStorageRequirements(POIFSBigBlockSize pOIFSBigBlockSize, int i) {
        return ((i + r0) - 1) / pOIFSBigBlockSize.getBATEntriesPerBlock();
    }

    public static int calculateXBATStorageRequirements(POIFSBigBlockSize pOIFSBigBlockSize, int i) {
        return ((i + r0) - 1) / pOIFSBigBlockSize.getXBATEntriesPerBlock();
    }

    private void setXBATChain(POIFSBigBlockSize pOIFSBigBlockSize, int i) throws ArrayIndexOutOfBoundsException {
        this._fields[pOIFSBigBlockSize.getXBATEntriesPerBlock()].set(i, this._data);
    }

    private BATBlock(POIFSBigBlockSize pOIFSBigBlockSize, int[] iArr, int i, int i2) throws ArrayIndexOutOfBoundsException {
        this(pOIFSBigBlockSize);
        for (int i3 = i; i3 < i2; i3++) {
            this._fields[i3 - i].set(iArr[i3], this._data);
        }
    }

    @Override // org.apache.poi.poifs.storage.BigBlock
    void writeData(OutputStream outputStream) throws IOException {
        doWriteData(outputStream, this._data);
    }
}
