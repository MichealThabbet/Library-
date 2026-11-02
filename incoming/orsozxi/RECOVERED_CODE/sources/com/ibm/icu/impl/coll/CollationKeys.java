package com.ibm.icu.impl.coll;

/* loaded from: classes2.dex */
public final class CollationKeys {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int CASE_LOWER_FIRST_COMMON_HIGH = 13;
    private static final int CASE_LOWER_FIRST_COMMON_LOW = 1;
    private static final int CASE_LOWER_FIRST_COMMON_MAX_COUNT = 7;
    private static final int CASE_LOWER_FIRST_COMMON_MIDDLE = 7;
    private static final int CASE_UPPER_FIRST_COMMON_HIGH = 15;
    private static final int CASE_UPPER_FIRST_COMMON_LOW = 3;
    private static final int CASE_UPPER_FIRST_COMMON_MAX_COUNT = 13;
    private static final int QUAT_COMMON_HIGH = 252;
    private static final int QUAT_COMMON_LOW = 28;
    private static final int QUAT_COMMON_MAX_COUNT = 113;
    private static final int QUAT_COMMON_MIDDLE = 140;
    private static final int QUAT_SHIFTED_LIMIT_BYTE = 27;
    static final int SEC_COMMON_HIGH = 69;
    private static final int SEC_COMMON_LOW = 5;
    private static final int SEC_COMMON_MAX_COUNT = 33;
    private static final int SEC_COMMON_MIDDLE = 37;
    private static final int TER_LOWER_FIRST_COMMON_HIGH = 69;
    private static final int TER_LOWER_FIRST_COMMON_LOW = 5;
    private static final int TER_LOWER_FIRST_COMMON_MAX_COUNT = 33;
    private static final int TER_LOWER_FIRST_COMMON_MIDDLE = 37;
    private static final int TER_ONLY_COMMON_HIGH = 197;
    private static final int TER_ONLY_COMMON_LOW = 5;
    private static final int TER_ONLY_COMMON_MAX_COUNT = 97;
    private static final int TER_ONLY_COMMON_MIDDLE = 101;
    private static final int TER_UPPER_FIRST_COMMON_HIGH = 197;
    private static final int TER_UPPER_FIRST_COMMON_LOW = 133;
    private static final int TER_UPPER_FIRST_COMMON_MAX_COUNT = 33;
    private static final int TER_UPPER_FIRST_COMMON_MIDDLE = 165;
    public static final LevelCallback SIMPLE_LEVEL_FALLBACK = new LevelCallback();
    private static final int[] levelMasks = {2, 6, 22, 54, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 54};

    public static class LevelCallback {
        boolean needToWrite(int i) {
            return true;
        }
    }

    public static abstract class SortKeyByteSink {
        private int appended_ = 0;
        protected byte[] buffer_;

        protected abstract void AppendBeyondCapacity(byte[] bArr, int i, int i2, int i3);

        protected abstract boolean Resize(int i, int i2);

        public SortKeyByteSink(byte[] bArr) {
            this.buffer_ = bArr;
        }

        public void setBufferAndAppended(byte[] bArr, int i) {
            this.buffer_ = bArr;
            this.appended_ = i;
        }

        public void Append(byte[] bArr, int i) {
            if (i <= 0 || bArr == null) {
                return;
            }
            int i2 = this.appended_;
            this.appended_ = i2 + i;
            byte[] bArr2 = this.buffer_;
            if (i <= bArr2.length - i2) {
                System.arraycopy(bArr, 0, bArr2, i2, i);
            } else {
                AppendBeyondCapacity(bArr, 0, i, i2);
            }
        }

        public void Append(int i) {
            int i2 = this.appended_;
            if (i2 < this.buffer_.length || Resize(1, i2)) {
                this.buffer_[this.appended_] = (byte) i;
            }
            this.appended_++;
        }

        public int NumberOfBytesAppended() {
            return this.appended_;
        }

        public int GetRemainingCapacity() {
            return this.buffer_.length - this.appended_;
        }

        public boolean Overflowed() {
            return this.appended_ > this.buffer_.length;
        }
    }

    private static final class SortKeyLevel {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final int INITIAL_CAPACITY = 40;
        byte[] buffer = new byte[40];
        int len = 0;

        SortKeyLevel() {
        }

        boolean isEmpty() {
            return this.len == 0;
        }

        int length() {
            return this.len;
        }

        byte getAt(int i) {
            return this.buffer[i];
        }

        byte[] data() {
            return this.buffer;
        }

        void appendByte(int i) {
            if (this.len < this.buffer.length || ensureCapacity(1)) {
                byte[] bArr = this.buffer;
                int i2 = this.len;
                this.len = i2 + 1;
                bArr[i2] = (byte) i;
            }
        }

        void appendWeight16(int i) {
            byte b = (byte) (i >>> 8);
            byte b2 = (byte) i;
            int i2 = b2 == 0 ? 1 : 2;
            if (this.len + i2 <= this.buffer.length || ensureCapacity(i2)) {
                byte[] bArr = this.buffer;
                int i3 = this.len;
                int i4 = i3 + 1;
                this.len = i4;
                bArr[i3] = b;
                if (b2 != 0) {
                    this.len = i4 + 1;
                    bArr[i4] = b2;
                }
            }
        }

        void appendWeight32(long j) {
            int i = 4;
            byte[] bArr = {(byte) (j >>> 24), (byte) (j >>> 16), (byte) (j >>> 8), (byte) j};
            if (bArr[1] == 0) {
                i = 1;
            } else if (bArr[2] == 0) {
                i = 2;
            } else if (bArr[3] == 0) {
                i = 3;
            }
            if (this.len + i <= this.buffer.length || ensureCapacity(i)) {
                byte[] bArr2 = this.buffer;
                int i2 = this.len;
                int i3 = i2 + 1;
                this.len = i3;
                bArr2[i2] = bArr[0];
                if (bArr[1] != 0) {
                    int i4 = i3 + 1;
                    this.len = i4;
                    bArr2[i3] = bArr[1];
                    if (bArr[2] != 0) {
                        int i5 = i4 + 1;
                        this.len = i5;
                        bArr2[i4] = bArr[2];
                        if (bArr[3] != 0) {
                            this.len = i5 + 1;
                            bArr2[i5] = bArr[3];
                        }
                    }
                }
            }
        }

        void appendReverseWeight16(int i) {
            byte b = (byte) (i >>> 8);
            byte b2 = (byte) i;
            int i2 = b2 == 0 ? 1 : 2;
            if (this.len + i2 <= this.buffer.length || ensureCapacity(i2)) {
                if (b2 == 0) {
                    byte[] bArr = this.buffer;
                    int i3 = this.len;
                    this.len = i3 + 1;
                    bArr[i3] = b;
                    return;
                }
                byte[] bArr2 = this.buffer;
                int i4 = this.len;
                bArr2[i4] = b2;
                bArr2[i4 + 1] = b;
                this.len = i4 + 2;
            }
        }

        void appendTo(SortKeyByteSink sortKeyByteSink) {
            sortKeyByteSink.Append(this.buffer, this.len - 1);
        }

        private boolean ensureCapacity(int i) {
            int length = this.buffer.length * 2;
            int i2 = this.len + (i * 2);
            if (length < i2) {
                length = i2;
            }
            if (length < 200) {
                length = 200;
            }
            byte[] bArr = new byte[length];
            System.arraycopy(this.buffer, 0, bArr, 0, this.len);
            this.buffer = bArr;
            return true;
        }
    }

    private static SortKeyLevel getSortKeyLevel(int i, int i2) {
        if ((i & i2) != 0) {
            return new SortKeyLevel();
        }
        return null;
    }

    private CollationKeys() {
    }

    /* JADX WARN: Removed duplicated region for block: B:241:0x0398  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x03a6 A[PHI: r23
      0x03a6: PHI (r23v3 int) = (r23v2 int), (r23v9 int) binds: [B:240:0x0396, B:245:0x03a4] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:303:0x045c  */
    /* JADX WARN: Removed duplicated region for block: B:305:0x03f7 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void writeSortKeyUpToQuaternary(com.ibm.icu.impl.coll.CollationIterator r42, boolean[] r43, com.ibm.icu.impl.coll.CollationSettings r44, com.ibm.icu.impl.coll.CollationKeys.SortKeyByteSink r45, int r46, com.ibm.icu.impl.coll.CollationKeys.LevelCallback r47, boolean r48) {
        /*
            Method dump skipped, instructions count: 1137
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.CollationKeys.writeSortKeyUpToQuaternary(com.ibm.icu.impl.coll.CollationIterator, boolean[], com.ibm.icu.impl.coll.CollationSettings, com.ibm.icu.impl.coll.CollationKeys$SortKeyByteSink, int, com.ibm.icu.impl.coll.CollationKeys$LevelCallback, boolean):void");
    }
}
