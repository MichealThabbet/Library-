package org.apache.poi.poifs.filesystem;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.LittleEndian;
import org.apache.poi.util.StringUtil;

/* loaded from: classes3.dex */
public class Ole10Native {
    public static final String OLE10_NATIVE = "\u0001Ole10Native";
    private final String command;
    private final byte[] dataBuffer;
    private final int dataSize;
    private final String fileName;
    private short flags1;
    private short flags2;
    private short flags3;
    private final String label;
    private final int totalSize;
    private byte[] unknown1;
    private byte[] unknown2;

    public static Ole10Native createFromEmbeddedOleObject(POIFSFileSystem pOIFSFileSystem) throws Ole10NativeException, IOException {
        boolean z;
        try {
            pOIFSFileSystem.getRoot().getEntry("\u0001Ole10ItemName");
            z = true;
        } catch (FileNotFoundException unused) {
            z = false;
        }
        DocumentInputStream documentInputStreamCreateDocumentInputStream = pOIFSFileSystem.createDocumentInputStream(OLE10_NATIVE);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(documentInputStreamCreateDocumentInputStream, byteArrayOutputStream);
        return new Ole10Native(byteArrayOutputStream.toByteArray(), 0, z);
    }

    public Ole10Native(byte[] bArr, int i) throws Ole10NativeException {
        this(bArr, i, false);
    }

    public Ole10Native(byte[] bArr, int i, boolean z) throws Ole10NativeException {
        if (bArr.length < i + 2) {
            throw new Ole10NativeException("data is too small");
        }
        int i2 = LittleEndian.getInt(bArr, i);
        this.totalSize = i2;
        int i3 = i + 4;
        if (z) {
            byte[] bArr2 = new byte[i2 - 4];
            this.dataBuffer = bArr2;
            System.arraycopy(bArr, 4, bArr2, 0, bArr2.length);
            this.dataSize = this.totalSize - 4;
            byte[] bArr3 = new byte[8];
            byte[] bArr4 = this.dataBuffer;
            System.arraycopy(bArr4, 0, bArr3, 0, Math.min(bArr4.length, 8));
            String str = "ole-" + HexDump.toHex(bArr3);
            this.label = str;
            this.fileName = str;
            this.command = str;
            return;
        }
        this.flags1 = LittleEndian.getShort(bArr, i3);
        int i4 = i3 + 2;
        int stringLength = getStringLength(bArr, i4);
        this.label = StringUtil.getFromCompressedUnicode(bArr, i4, stringLength - 1);
        int i5 = i4 + stringLength;
        int stringLength2 = getStringLength(bArr, i5);
        this.fileName = StringUtil.getFromCompressedUnicode(bArr, i5, stringLength2 - 1);
        int i6 = i5 + stringLength2;
        this.flags2 = LittleEndian.getShort(bArr, i6);
        int i7 = i6 + 2;
        int unsignedByte = LittleEndian.getUnsignedByte(bArr, i7);
        this.unknown1 = new byte[unsignedByte];
        this.unknown2 = new byte[3];
        int i8 = i7 + unsignedByte + 3;
        int stringLength3 = getStringLength(bArr, i8);
        this.command = StringUtil.getFromCompressedUnicode(bArr, i8, stringLength3 - 1);
        int i9 = i8 + stringLength3;
        if ((this.totalSize + 4) - i9 > 4) {
            int i10 = LittleEndian.getInt(bArr, i9);
            this.dataSize = i10;
            int i11 = i9 + 4;
            if (i10 > this.totalSize || i10 < 0) {
                throw new Ole10NativeException("Invalid Ole10Native");
            }
            byte[] bArr5 = new byte[i10];
            this.dataBuffer = bArr5;
            System.arraycopy(bArr, i11, bArr5, 0, i10);
            int i12 = i11 + this.dataSize;
            if (this.unknown1.length > 0) {
                this.flags3 = LittleEndian.getShort(bArr, i12);
                return;
            } else {
                this.flags3 = (short) 0;
                return;
            }
        }
        throw new Ole10NativeException("Invalid Ole10Native");
    }

    private static int getStringLength(byte[] bArr, int i) {
        int i2 = 0;
        while (true) {
            int i3 = i2 + i;
            if (i3 >= bArr.length || bArr[i3] == 0) {
                break;
            }
            i2++;
        }
        return i2 + 1;
    }

    public int getTotalSize() {
        return this.totalSize;
    }

    public short getFlags1() {
        return this.flags1;
    }

    public String getLabel() {
        return this.label;
    }

    public String getFileName() {
        return this.fileName;
    }

    public short getFlags2() {
        return this.flags2;
    }

    public byte[] getUnknown1() {
        return this.unknown1;
    }

    public byte[] getUnknown2() {
        return this.unknown2;
    }

    public String getCommand() {
        return this.command;
    }

    public int getDataSize() {
        return this.dataSize;
    }

    public byte[] getDataBuffer() {
        return this.dataBuffer;
    }

    public short getFlags3() {
        return this.flags3;
    }
}
