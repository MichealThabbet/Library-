package org.apache.poi.hssf.record;

import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class DrawingRecord extends StandardRecord {
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final short sid = 236;
    private byte[] contd;
    private byte[] recordData;

    @Override // org.apache.poi.hssf.record.Record
    public short getSid() {
        return (short) 236;
    }

    public DrawingRecord() {
        this.recordData = EMPTY_BYTE_ARRAY;
    }

    public DrawingRecord(RecordInputStream recordInputStream) {
        this.recordData = recordInputStream.readRemainder();
    }

    public void processContinueRecord(byte[] bArr) {
        this.contd = bArr;
    }

    @Override // org.apache.poi.hssf.record.StandardRecord
    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.write(this.recordData);
    }

    @Override // org.apache.poi.hssf.record.StandardRecord
    protected int getDataSize() {
        return this.recordData.length;
    }

    public byte[] getData() {
        byte[] bArr = this.contd;
        if (bArr != null) {
            byte[] bArr2 = this.recordData;
            byte[] bArr3 = new byte[bArr2.length + bArr.length];
            System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
            byte[] bArr4 = this.contd;
            System.arraycopy(bArr4, 0, bArr3, this.recordData.length, bArr4.length);
            return bArr3;
        }
        return this.recordData;
    }

    public void setData(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("data must not be null");
        }
        this.recordData = bArr;
    }

    @Override // org.apache.poi.hssf.record.Record
    public Object clone() {
        DrawingRecord drawingRecord = new DrawingRecord();
        drawingRecord.recordData = (byte[]) this.recordData.clone();
        byte[] bArr = this.contd;
        if (bArr != null) {
            drawingRecord.contd = (byte[]) bArr.clone();
        }
        return drawingRecord;
    }
}
