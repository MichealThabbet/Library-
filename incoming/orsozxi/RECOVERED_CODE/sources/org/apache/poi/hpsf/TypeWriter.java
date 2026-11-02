package org.apache.poi.hpsf;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.poi.util.LittleEndian;

/* loaded from: classes3.dex */
public class TypeWriter {
    public static int writeToStream(OutputStream outputStream, short s) throws IOException {
        byte[] bArr = new byte[2];
        LittleEndian.putShort(bArr, 0, s);
        outputStream.write(bArr, 0, 2);
        return 2;
    }

    public static int writeToStream(OutputStream outputStream, int i) throws IOException {
        byte[] bArr = new byte[4];
        LittleEndian.putInt(bArr, 0, i);
        outputStream.write(bArr, 0, 4);
        return 4;
    }

    public static int writeToStream(OutputStream outputStream, long j) throws IOException {
        byte[] bArr = new byte[8];
        LittleEndian.putLong(bArr, 0, j);
        outputStream.write(bArr, 0, 8);
        return 8;
    }

    public static void writeUShortToStream(OutputStream outputStream, int i) throws IOException {
        if (((-65536) & i) != 0) {
            throw new IllegalPropertySetDataException("Value " + i + " cannot be represented by 2 bytes.");
        }
        writeToStream(outputStream, (short) i);
    }

    public static int writeUIntToStream(OutputStream outputStream, long j) throws IOException {
        long j2 = j & (-4294967296L);
        if (j2 != 0 && j2 != -4294967296L) {
            throw new IllegalPropertySetDataException("Value " + j + " cannot be represented by 4 bytes.");
        }
        return writeToStream(outputStream, (int) j);
    }

    public static int writeToStream(OutputStream outputStream, ClassID classID) throws IOException {
        byte[] bArr = new byte[16];
        classID.write(bArr, 0);
        outputStream.write(bArr, 0, 16);
        return 16;
    }

    public static void writeToStream(OutputStream outputStream, Property[] propertyArr, int i) throws IOException, UnsupportedVariantTypeException {
        if (propertyArr == null) {
            return;
        }
        for (Property property : propertyArr) {
            writeUIntToStream(outputStream, property.getID());
            writeUIntToStream(outputStream, r2.getSize());
        }
        for (Property property2 : propertyArr) {
            writeUIntToStream(outputStream, property2.getType());
            VariantSupport.write(outputStream, (int) r2, property2.getValue(), i);
        }
    }

    public static int writeToStream(OutputStream outputStream, double d) throws IOException {
        byte[] bArr = new byte[8];
        LittleEndian.putDouble(bArr, 0, d);
        outputStream.write(bArr, 0, 8);
        return 8;
    }
}
