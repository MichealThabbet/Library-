package org.apache.poi.hssf.record;

import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class ExtSSTRecord extends StandardRecord {
    public static final int DEFAULT_BUCKET_SIZE = 8;
    public static final int MAX_BUCKETS = 128;
    public static final short sid = 255;
    private InfoSubRecord[] _sstInfos;
    private short _stringsPerBucket;

    @Override // org.apache.poi.hssf.record.Record
    public short getSid() {
        return (short) 255;
    }

    private static final class InfoSubRecord {
        public static final int ENCODED_SIZE = 8;
        private int field_1_stream_pos;
        private int field_2_bucket_sst_offset;
        private short field_3_zero;

        public InfoSubRecord(int i, int i2) {
            this.field_1_stream_pos = i;
            this.field_2_bucket_sst_offset = i2;
        }

        public InfoSubRecord(RecordInputStream recordInputStream) {
            this.field_1_stream_pos = recordInputStream.readInt();
            this.field_2_bucket_sst_offset = recordInputStream.readShort();
            this.field_3_zero = recordInputStream.readShort();
        }

        public int getStreamPos() {
            return this.field_1_stream_pos;
        }

        public int getBucketSSTOffset() {
            return this.field_2_bucket_sst_offset;
        }

        public void serialize(LittleEndianOutput littleEndianOutput) {
            littleEndianOutput.writeInt(this.field_1_stream_pos);
            littleEndianOutput.writeShort(this.field_2_bucket_sst_offset);
            littleEndianOutput.writeShort(this.field_3_zero);
        }
    }

    public ExtSSTRecord() {
        this._stringsPerBucket = (short) 8;
        this._sstInfos = new InfoSubRecord[0];
    }

    public ExtSSTRecord(RecordInputStream recordInputStream) {
        this._stringsPerBucket = recordInputStream.readShort();
        this._sstInfos = new InfoSubRecord[recordInputStream.remaining() / 8];
        int i = 0;
        while (true) {
            InfoSubRecord[] infoSubRecordArr = this._sstInfos;
            if (i >= infoSubRecordArr.length) {
                return;
            }
            infoSubRecordArr[i] = new InfoSubRecord(recordInputStream);
            i++;
        }
    }

    public void setNumStringsPerBucket(short s) {
        this._stringsPerBucket = s;
    }

    @Override // org.apache.poi.hssf.record.Record
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[EXTSST]\n");
        stringBuffer.append("    .dsst           = ");
        stringBuffer.append(Integer.toHexString(this._stringsPerBucket));
        stringBuffer.append("\n");
        stringBuffer.append("    .numInfoRecords = ");
        stringBuffer.append(this._sstInfos.length);
        stringBuffer.append("\n");
        for (int i = 0; i < this._sstInfos.length; i++) {
            stringBuffer.append("    .inforecord     = ");
            stringBuffer.append(i);
            stringBuffer.append("\n");
            stringBuffer.append("    .streampos      = ");
            stringBuffer.append(Integer.toHexString(this._sstInfos[i].getStreamPos()));
            stringBuffer.append("\n");
            stringBuffer.append("    .sstoffset      = ");
            stringBuffer.append(Integer.toHexString(this._sstInfos[i].getBucketSSTOffset()));
            stringBuffer.append("\n");
        }
        stringBuffer.append("[/EXTSST]\n");
        return stringBuffer.toString();
    }

    @Override // org.apache.poi.hssf.record.StandardRecord
    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this._stringsPerBucket);
        int i = 0;
        while (true) {
            InfoSubRecord[] infoSubRecordArr = this._sstInfos;
            if (i >= infoSubRecordArr.length) {
                return;
            }
            infoSubRecordArr[i].serialize(littleEndianOutput);
            i++;
        }
    }

    @Override // org.apache.poi.hssf.record.StandardRecord
    protected int getDataSize() {
        return (this._sstInfos.length * 8) + 2;
    }

    public static final int getNumberOfInfoRecsForStrings(int i) {
        int i2 = i / 8;
        if (i % 8 != 0) {
            i2++;
        }
        if (i2 > 128) {
            return 128;
        }
        return i2;
    }

    public static final int getRecordSizeForStrings(int i) {
        return (getNumberOfInfoRecsForStrings(i) * 8) + 6;
    }

    public void setBucketOffsets(int[] iArr, int[] iArr2) {
        this._sstInfos = new InfoSubRecord[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            this._sstInfos[i] = new InfoSubRecord(iArr[i], iArr2[i]);
        }
    }
}
