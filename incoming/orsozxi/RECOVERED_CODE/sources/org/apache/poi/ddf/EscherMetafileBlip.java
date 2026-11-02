package org.apache.poi.ddf;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.InflaterInputStream;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndian;
import org.apache.poi.util.POILogFactory;
import org.apache.poi.util.POILogger;

/* loaded from: classes3.dex */
public final class EscherMetafileBlip extends EscherBlipRecord {
    private static final int HEADER_SIZE = 8;
    public static final short RECORD_ID_EMF = -4070;
    public static final short RECORD_ID_PICT = -4068;
    public static final short RECORD_ID_WMF = -4069;
    public static final short SIGNATURE_EMF = 15680;
    public static final short SIGNATURE_PICT = 21536;
    public static final short SIGNATURE_WMF = 8544;
    private static final POILogger log = POILogFactory.getLogger(EscherMetafileBlip.class);
    private byte[] field_1_UID;
    private byte[] field_2_UID;
    private int field_2_cb;
    private int field_3_rcBounds_x1;
    private int field_3_rcBounds_x2;
    private int field_3_rcBounds_y1;
    private int field_3_rcBounds_y2;
    private int field_4_ptSize_h;
    private int field_4_ptSize_w;
    private int field_5_cbSave;
    private byte field_6_fCompression;
    private byte field_7_fFilter;
    private byte[] raw_pictureData;
    private byte[] remainingData;

    @Override // org.apache.poi.ddf.EscherBlipRecord, org.apache.poi.ddf.EscherRecord
    public int fillFields(byte[] bArr, int i, EscherRecordFactory escherRecordFactory) {
        int header = readHeader(bArr, i);
        int i2 = i + 8;
        byte[] bArr2 = new byte[16];
        this.field_1_UID = bArr2;
        System.arraycopy(bArr, i2, bArr2, 0, 16);
        int i3 = i2 + 16;
        if ((getOptions() ^ getSignature()) == 16) {
            byte[] bArr3 = new byte[16];
            this.field_2_UID = bArr3;
            System.arraycopy(bArr, i3, bArr3, 0, 16);
            i3 += 16;
        }
        this.field_2_cb = LittleEndian.getInt(bArr, i3);
        int i4 = i3 + 4;
        this.field_3_rcBounds_x1 = LittleEndian.getInt(bArr, i4);
        int i5 = i4 + 4;
        this.field_3_rcBounds_y1 = LittleEndian.getInt(bArr, i5);
        int i6 = i5 + 4;
        this.field_3_rcBounds_x2 = LittleEndian.getInt(bArr, i6);
        int i7 = i6 + 4;
        this.field_3_rcBounds_y2 = LittleEndian.getInt(bArr, i7);
        int i8 = i7 + 4;
        this.field_4_ptSize_w = LittleEndian.getInt(bArr, i8);
        int i9 = i8 + 4;
        this.field_4_ptSize_h = LittleEndian.getInt(bArr, i9);
        int i10 = i9 + 4;
        int i11 = LittleEndian.getInt(bArr, i10);
        this.field_5_cbSave = i11;
        int i12 = i10 + 4;
        this.field_6_fCompression = bArr[i12];
        int i13 = i12 + 1;
        this.field_7_fFilter = bArr[i13];
        int i14 = i13 + 1;
        byte[] bArr4 = new byte[i11];
        this.raw_pictureData = bArr4;
        System.arraycopy(bArr, i14, bArr4, 0, i11);
        int i15 = i14 + this.field_5_cbSave;
        if (this.field_6_fCompression == 0) {
            this.field_pictureData = inflatePictureData(this.raw_pictureData);
        } else {
            this.field_pictureData = this.raw_pictureData;
        }
        int i16 = (header - i15) + i + 8;
        if (i16 > 0) {
            byte[] bArr5 = new byte[i16];
            this.remainingData = bArr5;
            System.arraycopy(bArr, i15, bArr5, 0, i16);
        }
        return header + 8;
    }

    @Override // org.apache.poi.ddf.EscherBlipRecord, org.apache.poi.ddf.EscherRecord
    public int serialize(int i, byte[] bArr, EscherSerializationListener escherSerializationListener) {
        escherSerializationListener.beforeRecordSerialize(i, getRecordId(), this);
        LittleEndian.putShort(bArr, i, getOptions());
        int i2 = i + 2;
        LittleEndian.putShort(bArr, i2, getRecordId());
        int i3 = i2 + 2;
        LittleEndian.putInt(bArr, i3, getRecordSize() - 8);
        int i4 = i3 + 4;
        byte[] bArr2 = this.field_1_UID;
        System.arraycopy(bArr2, 0, bArr, i4, bArr2.length);
        int length = i4 + this.field_1_UID.length;
        if ((getOptions() ^ getSignature()) == 16) {
            byte[] bArr3 = this.field_2_UID;
            System.arraycopy(bArr3, 0, bArr, length, bArr3.length);
            length += this.field_2_UID.length;
        }
        LittleEndian.putInt(bArr, length, this.field_2_cb);
        int i5 = length + 4;
        LittleEndian.putInt(bArr, i5, this.field_3_rcBounds_x1);
        int i6 = i5 + 4;
        LittleEndian.putInt(bArr, i6, this.field_3_rcBounds_y1);
        int i7 = i6 + 4;
        LittleEndian.putInt(bArr, i7, this.field_3_rcBounds_x2);
        int i8 = i7 + 4;
        LittleEndian.putInt(bArr, i8, this.field_3_rcBounds_y2);
        int i9 = i8 + 4;
        LittleEndian.putInt(bArr, i9, this.field_4_ptSize_w);
        int i10 = i9 + 4;
        LittleEndian.putInt(bArr, i10, this.field_4_ptSize_h);
        int i11 = i10 + 4;
        LittleEndian.putInt(bArr, i11, this.field_5_cbSave);
        int i12 = i11 + 4;
        bArr[i12] = this.field_6_fCompression;
        int i13 = i12 + 1;
        bArr[i13] = this.field_7_fFilter;
        int i14 = i13 + 1;
        byte[] bArr4 = this.raw_pictureData;
        System.arraycopy(bArr4, 0, bArr, i14, bArr4.length);
        int length2 = i14 + this.raw_pictureData.length;
        byte[] bArr5 = this.remainingData;
        if (bArr5 != null) {
            System.arraycopy(bArr5, 0, bArr, length2, bArr5.length);
            int length3 = this.remainingData.length;
        }
        escherSerializationListener.afterRecordSerialize(i + getRecordSize(), getRecordId(), getRecordSize(), this);
        return getRecordSize();
    }

    private static byte[] inflatePictureData(byte[] bArr) {
        try {
            InflaterInputStream inflaterInputStream = new InflaterInputStream(new ByteArrayInputStream(bArr));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr2 = new byte[4096];
            while (true) {
                int i = inflaterInputStream.read(bArr2);
                if (i > 0) {
                    byteArrayOutputStream.write(bArr2, 0, i);
                } else {
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } catch (IOException e) {
            log.log(POILogger.WARN, (Object) "Possibly corrupt compression or non-compressed data", (Throwable) e);
            return bArr;
        }
    }

    @Override // org.apache.poi.ddf.EscherBlipRecord, org.apache.poi.ddf.EscherRecord
    public int getRecordSize() {
        int length = this.raw_pictureData.length + 58;
        byte[] bArr = this.remainingData;
        if (bArr != null) {
            length += bArr.length;
        }
        return (getOptions() ^ getSignature()) == 16 ? length + this.field_2_UID.length : length;
    }

    public byte[] getUID() {
        return this.field_1_UID;
    }

    public void setUID(byte[] bArr) {
        this.field_1_UID = bArr;
    }

    public byte[] getPrimaryUID() {
        return this.field_2_UID;
    }

    public void setPrimaryUID(byte[] bArr) {
        this.field_2_UID = bArr;
    }

    public int getUncompressedSize() {
        return this.field_2_cb;
    }

    public void setUncompressedSize(int i) {
        this.field_2_cb = i;
    }

    public Rectangle getBounds() {
        int i = this.field_3_rcBounds_x1;
        int i2 = this.field_3_rcBounds_y1;
        return new Rectangle(i, i2, this.field_3_rcBounds_x2 - i, this.field_3_rcBounds_y2 - i2);
    }

    public void setBounds(Rectangle rectangle) {
        this.field_3_rcBounds_x1 = rectangle.x;
        this.field_3_rcBounds_y1 = rectangle.y;
        this.field_3_rcBounds_x2 = rectangle.x + rectangle.width;
        this.field_3_rcBounds_y2 = rectangle.y + rectangle.height;
    }

    public Dimension getSizeEMU() {
        return new Dimension(this.field_4_ptSize_w, this.field_4_ptSize_h);
    }

    public void setSizeEMU(Dimension dimension) {
        this.field_4_ptSize_w = dimension.width;
        this.field_4_ptSize_h = dimension.height;
    }

    public int getCompressedSize() {
        return this.field_5_cbSave;
    }

    public void setCompressedSize(int i) {
        this.field_5_cbSave = i;
    }

    public boolean isCompressed() {
        return this.field_6_fCompression == 0;
    }

    public void setCompressed(boolean z) {
        this.field_6_fCompression = z ? (byte) 0 : (byte) -2;
    }

    public byte[] getRemainingData() {
        return this.remainingData;
    }

    @Override // org.apache.poi.ddf.EscherBlipRecord
    public String toString() {
        String str;
        String str2;
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(":");
        sb.append('\n');
        sb.append("  RecordId: 0x");
        sb.append(HexDump.toHex(getRecordId()));
        sb.append('\n');
        sb.append("  Options: 0x");
        sb.append(HexDump.toHex(getOptions()));
        sb.append('\n');
        sb.append("  UID: 0x");
        sb.append(HexDump.toHex(this.field_1_UID));
        sb.append('\n');
        if (this.field_2_UID == null) {
            str = "";
        } else {
            str = "  UID2: 0x" + HexDump.toHex(this.field_2_UID) + '\n';
        }
        sb.append(str);
        sb.append("  Uncompressed Size: ");
        sb.append(HexDump.toHex(this.field_2_cb));
        sb.append('\n');
        sb.append("  Bounds: ");
        sb.append(getBounds());
        sb.append('\n');
        sb.append("  Size in EMU: ");
        sb.append(getSizeEMU());
        sb.append('\n');
        sb.append("  Compressed Size: ");
        sb.append(HexDump.toHex(this.field_5_cbSave));
        sb.append('\n');
        sb.append("  Compression: ");
        sb.append(HexDump.toHex(this.field_6_fCompression));
        sb.append('\n');
        sb.append("  Filter: ");
        sb.append(HexDump.toHex(this.field_7_fFilter));
        sb.append('\n');
        sb.append("  Extra Data:");
        sb.append('\n');
        sb.append("");
        if (this.remainingData == null) {
            str2 = null;
        } else {
            str2 = "\n Remaining Data: " + HexDump.toHex(this.remainingData, 32);
        }
        sb.append(str2);
        return sb.toString();
    }

    public short getSignature() {
        switch (getRecordId()) {
            case -4070:
                return (short) 15680;
            case -4069:
                return (short) 8544;
            case -4068:
                return (short) 21536;
            default:
                log.log(POILogger.WARN, "Unknown metafile: " + ((int) getRecordId()));
                return (short) 0;
        }
    }
}
