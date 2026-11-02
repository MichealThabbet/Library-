package com.ibm.icu.util;

import com.ibm.icu.util.CodePointMap;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public abstract class CodePointTrie extends CodePointMap {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ASCII_LIMIT = 128;
    private static final int BMP_INDEX_LENGTH = 1024;
    static final int CP_PER_INDEX_2_ENTRY = 512;
    private static final int ERROR_VALUE_NEG_DATA_OFFSET = 1;
    static final int FAST_DATA_BLOCK_LENGTH = 64;
    private static final int FAST_DATA_MASK = 63;
    static final int FAST_SHIFT = 6;
    private static final int HIGH_VALUE_NEG_DATA_OFFSET = 2;
    static final int INDEX_2_BLOCK_LENGTH = 32;
    static final int INDEX_2_MASK = 31;
    static final int INDEX_3_BLOCK_LENGTH = 32;
    private static final int INDEX_3_MASK = 31;
    private static final int MAX_UNICODE = 1114111;
    static final int NO_DATA_NULL_OFFSET = 1048575;
    static final int NO_INDEX3_NULL_OFFSET = 32767;
    private static final int OMITTED_BMP_INDEX_1_LENGTH = 4;
    private static final int OPTIONS_DATA_LENGTH_MASK = 61440;
    private static final int OPTIONS_DATA_NULL_OFFSET_MASK = 3840;
    private static final int OPTIONS_RESERVED_MASK = 56;
    private static final int OPTIONS_VALUE_BITS_MASK = 7;
    private static final int SHIFT_1 = 14;
    static final int SHIFT_1_2 = 5;
    private static final int SHIFT_2 = 9;
    static final int SHIFT_2_3 = 5;
    static final int SHIFT_3 = 4;
    static final int SMALL_DATA_BLOCK_LENGTH = 16;
    static final int SMALL_DATA_MASK = 15;
    private static final int SMALL_INDEX_LENGTH = 64;
    static final int SMALL_LIMIT = 4096;
    private static final int SMALL_MAX = 4095;
    private final int[] ascii;

    @Deprecated
    protected final Data data;

    @Deprecated
    protected final int dataLength;
    private final int dataNullOffset;

    @Deprecated
    protected final int highStart;
    private final char[] index;
    private final int index3NullOffset;
    private final int nullValue;

    public enum Type {
        FAST,
        SMALL
    }

    public enum ValueWidth {
        BITS_16,
        BITS_32,
        BITS_8
    }

    @Deprecated
    protected abstract int cpIndex(int i);

    public abstract Type getType();

    /* synthetic */ CodePointTrie(char[] cArr, Data data, int i, int i2, int i3, AnonymousClass1 anonymousClass1) {
        this(cArr, data, i, i2, i3);
    }

    private CodePointTrie(char[] cArr, Data data, int i, int i2, int i3) {
        this.ascii = new int[128];
        this.index = cArr;
        this.data = data;
        this.dataLength = data.getDataLength();
        this.highStart = i;
        this.index3NullOffset = i2;
        this.dataNullOffset = i3;
        for (int i4 = 0; i4 < 128; i4++) {
            this.ascii[i4] = data.getFromIndex(i4);
        }
        int i5 = this.dataLength;
        this.nullValue = data.getFromIndex(i3 >= i5 ? i5 - 2 : i3);
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x00b9 A[Catch: all -> 0x0145, TryCatch #0 {all -> 0x0145, blocks: (B:3:0x0006, B:5:0x000e, B:20:0x0038, B:23:0x0058, B:27:0x0065, B:31:0x006e, B:36:0x007e, B:46:0x0092, B:48:0x00a7, B:49:0x00a9, B:54:0x00b3, B:56:0x00b9, B:60:0x00cb, B:62:0x00d3, B:63:0x00db, B:66:0x00e6, B:67:0x00ee, B:68:0x00ef, B:70:0x00f7, B:71:0x00ff, B:74:0x010a, B:76:0x0112, B:77:0x011a, B:80:0x0125, B:81:0x012c, B:50:0x00ab, B:52:0x00af, B:53:0x00b2, B:82:0x012d, B:83:0x0134, B:84:0x0135, B:85:0x013c, B:32:0x0071, B:33:0x0078, B:34:0x0079, B:35:0x007c, B:24:0x005b, B:25:0x0062, B:26:0x0063, B:10:0x001f, B:11:0x0026, B:12:0x0027, B:17:0x0030, B:19:0x0035, B:18:0x0033, B:86:0x013d, B:87:0x0144), top: B:91:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0125 A[Catch: all -> 0x0145, TRY_ENTER, TryCatch #0 {all -> 0x0145, blocks: (B:3:0x0006, B:5:0x000e, B:20:0x0038, B:23:0x0058, B:27:0x0065, B:31:0x006e, B:36:0x007e, B:46:0x0092, B:48:0x00a7, B:49:0x00a9, B:54:0x00b3, B:56:0x00b9, B:60:0x00cb, B:62:0x00d3, B:63:0x00db, B:66:0x00e6, B:67:0x00ee, B:68:0x00ef, B:70:0x00f7, B:71:0x00ff, B:74:0x010a, B:76:0x0112, B:77:0x011a, B:80:0x0125, B:81:0x012c, B:50:0x00ab, B:52:0x00af, B:53:0x00b2, B:82:0x012d, B:83:0x0134, B:84:0x0135, B:85:0x013c, B:32:0x0071, B:33:0x0078, B:34:0x0079, B:35:0x007c, B:24:0x005b, B:25:0x0062, B:26:0x0063, B:10:0x001f, B:11:0x0026, B:12:0x0027, B:17:0x0030, B:19:0x0035, B:18:0x0033, B:86:0x013d, B:87:0x0144), top: B:91:0x0006 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static com.ibm.icu.util.CodePointTrie fromBinary(com.ibm.icu.util.CodePointTrie.Type r16, com.ibm.icu.util.CodePointTrie.ValueWidth r17, java.nio.ByteBuffer r18) {
        /*
            Method dump skipped, instructions count: 330
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.CodePointTrie.fromBinary(com.ibm.icu.util.CodePointTrie$Type, com.ibm.icu.util.CodePointTrie$ValueWidth, java.nio.ByteBuffer):com.ibm.icu.util.CodePointTrie");
    }

    /* renamed from: com.ibm.icu.util.CodePointTrie$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$util$CodePointTrie$ValueWidth;

        static {
            int[] iArr = new int[ValueWidth.values().length];
            $SwitchMap$com$ibm$icu$util$CodePointTrie$ValueWidth = iArr;
            try {
                iArr[ValueWidth.BITS_16.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$util$CodePointTrie$ValueWidth[ValueWidth.BITS_32.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ibm$icu$util$CodePointTrie$ValueWidth[ValueWidth.BITS_8.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public final ValueWidth getValueWidth() {
        return this.data.getValueWidth();
    }

    @Override // com.ibm.icu.util.CodePointMap
    public int get(int i) {
        return this.data.getFromIndex(cpIndex(i));
    }

    public final int asciiGet(int i) {
        return this.ascii[i];
    }

    private static final int maybeFilterValue(int i, int i2, int i3, CodePointMap.ValueFilter valueFilter) {
        return i == i2 ? i3 : valueFilter != null ? valueFilter.apply(i) : i;
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0156 A[EDGE_INSN: B:111:0x0156->B:87:0x0156 BREAK  A[LOOP:2: B:76:0x0133->B:84:0x014e], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0139  */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:79:0x0141 -> B:74:0x0126). Please report as a decompilation issue!!! */
    @Override // com.ibm.icu.util.CodePointMap
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean getRange(int r24, com.ibm.icu.util.CodePointMap.ValueFilter r25, com.ibm.icu.util.CodePointMap.Range r26) {
        /*
            Method dump skipped, instructions count: 396
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.CodePointTrie.getRange(int, com.ibm.icu.util.CodePointMap$ValueFilter, com.ibm.icu.util.CodePointMap$Range):boolean");
    }

    public final int toBinary(OutputStream outputStream) throws IOException {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeInt(1416784179);
            dataOutputStream.writeChar(((this.dataLength & 983040) >> 4) | ((983040 & this.dataNullOffset) >> 8) | (getType().ordinal() << 6) | getValueWidth().ordinal());
            dataOutputStream.writeChar(this.index.length);
            dataOutputStream.writeChar(this.dataLength);
            dataOutputStream.writeChar(this.index3NullOffset);
            dataOutputStream.writeChar(this.dataNullOffset);
            dataOutputStream.writeChar(this.highStart >> 9);
            for (char c : this.index) {
                dataOutputStream.writeChar(c);
            }
            return 16 + (this.index.length * 2) + this.data.write(dataOutputStream);
        } catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }

    private static abstract class Data {
        abstract int getDataLength();

        abstract int getFromIndex(int i);

        abstract ValueWidth getValueWidth();

        abstract int write(DataOutputStream dataOutputStream) throws IOException;

        private Data() {
        }

        /* synthetic */ Data(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    private static final class Data16 extends Data {
        char[] array;

        Data16(char[] cArr) {
            super(null);
            this.array = cArr;
        }

        @Override // com.ibm.icu.util.CodePointTrie.Data
        ValueWidth getValueWidth() {
            return ValueWidth.BITS_16;
        }

        @Override // com.ibm.icu.util.CodePointTrie.Data
        int getDataLength() {
            return this.array.length;
        }

        @Override // com.ibm.icu.util.CodePointTrie.Data
        int getFromIndex(int i) {
            return this.array[i];
        }

        @Override // com.ibm.icu.util.CodePointTrie.Data
        int write(DataOutputStream dataOutputStream) throws IOException {
            for (char c : this.array) {
                dataOutputStream.writeChar(c);
            }
            return this.array.length * 2;
        }
    }

    private static final class Data32 extends Data {
        int[] array;

        Data32(int[] iArr) {
            super(null);
            this.array = iArr;
        }

        @Override // com.ibm.icu.util.CodePointTrie.Data
        ValueWidth getValueWidth() {
            return ValueWidth.BITS_32;
        }

        @Override // com.ibm.icu.util.CodePointTrie.Data
        int getDataLength() {
            return this.array.length;
        }

        @Override // com.ibm.icu.util.CodePointTrie.Data
        int getFromIndex(int i) {
            return this.array[i];
        }

        @Override // com.ibm.icu.util.CodePointTrie.Data
        int write(DataOutputStream dataOutputStream) throws IOException {
            for (int i : this.array) {
                dataOutputStream.writeInt(i);
            }
            return this.array.length * 4;
        }
    }

    private static final class Data8 extends Data {
        byte[] array;

        Data8(byte[] bArr) {
            super(null);
            this.array = bArr;
        }

        @Override // com.ibm.icu.util.CodePointTrie.Data
        ValueWidth getValueWidth() {
            return ValueWidth.BITS_8;
        }

        @Override // com.ibm.icu.util.CodePointTrie.Data
        int getDataLength() {
            return this.array.length;
        }

        @Override // com.ibm.icu.util.CodePointTrie.Data
        int getFromIndex(int i) {
            return this.array[i] & 255;
        }

        @Override // com.ibm.icu.util.CodePointTrie.Data
        int write(DataOutputStream dataOutputStream) throws IOException {
            for (byte b : this.array) {
                dataOutputStream.writeByte(b);
            }
            return this.array.length;
        }
    }

    @Deprecated
    protected final int fastIndex(int i) {
        return this.index[i >> 6] + (i & 63);
    }

    @Deprecated
    protected final int smallIndex(Type type, int i) {
        if (i >= this.highStart) {
            return this.dataLength - 2;
        }
        return internalSmallIndex(type, i);
    }

    private final int internalSmallIndex(Type type, int i) {
        int i2;
        int i3 = i >> 14;
        int i4 = type == Type.FAST ? i3 + 1020 : i3 + 64;
        char[] cArr = this.index;
        char c = cArr[cArr[i4] + ((i >> 9) & 31)];
        int i5 = (i >> 4) & 31;
        if ((32768 & c) == 0) {
            i2 = cArr[c + i5];
        } else {
            int i6 = (c & 32767) + (i5 & (-8)) + (i5 >> 3);
            int i7 = i5 & 7;
            i2 = cArr[i6 + 1 + i7] | ((cArr[i6] << ((i7 * 2) + 2)) & 196608);
        }
        return i2 + (i & 15);
    }

    public static abstract class Fast extends CodePointTrie {
        public abstract int bmpGet(int i);

        public abstract int suppGet(int i);

        /* synthetic */ Fast(char[] cArr, Data data, int i, int i2, int i3, AnonymousClass1 anonymousClass1) {
            this(cArr, data, i, i2, i3);
        }

        private Fast(char[] cArr, Data data, int i, int i2, int i3) {
            super(cArr, data, i, i2, i3, null);
        }

        public static Fast fromBinary(ValueWidth valueWidth, ByteBuffer byteBuffer) {
            return (Fast) CodePointTrie.fromBinary(Type.FAST, valueWidth, byteBuffer);
        }

        @Override // com.ibm.icu.util.CodePointTrie
        public final Type getType() {
            return Type.FAST;
        }

        @Override // com.ibm.icu.util.CodePointTrie
        @Deprecated
        protected final int cpIndex(int i) {
            if (i >= 0) {
                if (i <= 65535) {
                    return fastIndex(i);
                }
                if (i <= 1114111) {
                    return smallIndex(Type.FAST, i);
                }
            }
            return this.dataLength - 1;
        }

        @Override // com.ibm.icu.util.CodePointMap
        public final CodePointMap.StringIterator stringIterator(CharSequence charSequence, int i) {
            return new FastStringIterator(this, charSequence, i, null);
        }

        private final class FastStringIterator extends CodePointMap.StringIterator {
            /* synthetic */ FastStringIterator(Fast fast, CharSequence charSequence, int i, AnonymousClass1 anonymousClass1) {
                this(charSequence, i);
            }

            private FastStringIterator(CharSequence charSequence, int i) {
                super(charSequence, i);
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x005e  */
            @Override // com.ibm.icu.util.CodePointMap.StringIterator
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public boolean next() {
                /*
                    r4 = this;
                    int r0 = r4.sIndex
                    java.lang.CharSequence r1 = r4.s
                    int r1 = r1.length()
                    if (r0 < r1) goto Lc
                    r0 = 0
                    return r0
                Lc:
                    java.lang.CharSequence r0 = r4.s
                    int r1 = r4.sIndex
                    int r2 = r1 + 1
                    r4.sIndex = r2
                    char r0 = r0.charAt(r1)
                    r4.c = r0
                    boolean r1 = java.lang.Character.isSurrogate(r0)
                    r2 = 1
                    if (r1 != 0) goto L2a
                    com.ibm.icu.util.CodePointTrie$Fast r0 = com.ibm.icu.util.CodePointTrie.Fast.this
                    int r1 = r4.c
                    int r0 = r0.fastIndex(r1)
                    goto L63
                L2a:
                    boolean r1 = com.ibm.icu.impl.Normalizer2Impl.UTF16Plus.isSurrogateLead(r0)
                    if (r1 == 0) goto L5e
                    int r1 = r4.sIndex
                    java.lang.CharSequence r3 = r4.s
                    int r3 = r3.length()
                    if (r1 >= r3) goto L5e
                    java.lang.CharSequence r1 = r4.s
                    int r3 = r4.sIndex
                    char r1 = r1.charAt(r3)
                    boolean r3 = java.lang.Character.isLowSurrogate(r1)
                    if (r3 == 0) goto L5e
                    int r3 = r4.sIndex
                    int r3 = r3 + r2
                    r4.sIndex = r3
                    int r0 = java.lang.Character.toCodePoint(r0, r1)
                    r4.c = r0
                    com.ibm.icu.util.CodePointTrie$Fast r0 = com.ibm.icu.util.CodePointTrie.Fast.this
                    com.ibm.icu.util.CodePointTrie$Type r1 = com.ibm.icu.util.CodePointTrie.Type.FAST
                    int r3 = r4.c
                    int r0 = r0.smallIndex(r1, r3)
                    goto L63
                L5e:
                    com.ibm.icu.util.CodePointTrie$Fast r0 = com.ibm.icu.util.CodePointTrie.Fast.this
                    int r0 = r0.dataLength
                    int r0 = r0 - r2
                L63:
                    com.ibm.icu.util.CodePointTrie$Fast r1 = com.ibm.icu.util.CodePointTrie.Fast.this
                    com.ibm.icu.util.CodePointTrie$Data r1 = r1.data
                    int r0 = r1.getFromIndex(r0)
                    r4.value = r0
                    return r2
                */
                throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.CodePointTrie.Fast.FastStringIterator.next():boolean");
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0052  */
            @Override // com.ibm.icu.util.CodePointMap.StringIterator
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public boolean previous() {
                /*
                    r4 = this;
                    int r0 = r4.sIndex
                    if (r0 > 0) goto L6
                    r0 = 0
                    return r0
                L6:
                    java.lang.CharSequence r0 = r4.s
                    int r1 = r4.sIndex
                    r2 = 1
                    int r1 = r1 - r2
                    r4.sIndex = r1
                    char r0 = r0.charAt(r1)
                    r4.c = r0
                    boolean r1 = java.lang.Character.isSurrogate(r0)
                    if (r1 != 0) goto L23
                    com.ibm.icu.util.CodePointTrie$Fast r0 = com.ibm.icu.util.CodePointTrie.Fast.this
                    int r1 = r4.c
                    int r0 = r0.fastIndex(r1)
                    goto L57
                L23:
                    boolean r1 = com.ibm.icu.impl.Normalizer2Impl.UTF16Plus.isSurrogateLead(r0)
                    if (r1 != 0) goto L52
                    int r1 = r4.sIndex
                    if (r1 <= 0) goto L52
                    java.lang.CharSequence r1 = r4.s
                    int r3 = r4.sIndex
                    int r3 = r3 - r2
                    char r1 = r1.charAt(r3)
                    boolean r3 = java.lang.Character.isHighSurrogate(r1)
                    if (r3 == 0) goto L52
                    int r3 = r4.sIndex
                    int r3 = r3 - r2
                    r4.sIndex = r3
                    int r0 = java.lang.Character.toCodePoint(r1, r0)
                    r4.c = r0
                    com.ibm.icu.util.CodePointTrie$Fast r0 = com.ibm.icu.util.CodePointTrie.Fast.this
                    com.ibm.icu.util.CodePointTrie$Type r1 = com.ibm.icu.util.CodePointTrie.Type.FAST
                    int r3 = r4.c
                    int r0 = r0.smallIndex(r1, r3)
                    goto L57
                L52:
                    com.ibm.icu.util.CodePointTrie$Fast r0 = com.ibm.icu.util.CodePointTrie.Fast.this
                    int r0 = r0.dataLength
                    int r0 = r0 - r2
                L57:
                    com.ibm.icu.util.CodePointTrie$Fast r1 = com.ibm.icu.util.CodePointTrie.Fast.this
                    com.ibm.icu.util.CodePointTrie$Data r1 = r1.data
                    int r0 = r1.getFromIndex(r0)
                    r4.value = r0
                    return r2
                */
                throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.CodePointTrie.Fast.FastStringIterator.previous():boolean");
            }
        }
    }

    public static abstract class Small extends CodePointTrie {
        /* synthetic */ Small(char[] cArr, Data data, int i, int i2, int i3, AnonymousClass1 anonymousClass1) {
            this(cArr, data, i, i2, i3);
        }

        private Small(char[] cArr, Data data, int i, int i2, int i3) {
            super(cArr, data, i, i2, i3, null);
        }

        public static Small fromBinary(ValueWidth valueWidth, ByteBuffer byteBuffer) {
            return (Small) CodePointTrie.fromBinary(Type.SMALL, valueWidth, byteBuffer);
        }

        @Override // com.ibm.icu.util.CodePointTrie
        public final Type getType() {
            return Type.SMALL;
        }

        @Override // com.ibm.icu.util.CodePointTrie
        @Deprecated
        protected final int cpIndex(int i) {
            if (i >= 0) {
                if (i <= 4095) {
                    return fastIndex(i);
                }
                if (i <= 1114111) {
                    return smallIndex(Type.SMALL, i);
                }
            }
            return this.dataLength - 1;
        }

        @Override // com.ibm.icu.util.CodePointMap
        public final CodePointMap.StringIterator stringIterator(CharSequence charSequence, int i) {
            return new SmallStringIterator(this, charSequence, i, null);
        }

        private final class SmallStringIterator extends CodePointMap.StringIterator {
            /* synthetic */ SmallStringIterator(Small small, CharSequence charSequence, int i, AnonymousClass1 anonymousClass1) {
                this(charSequence, i);
            }

            private SmallStringIterator(CharSequence charSequence, int i) {
                super(charSequence, i);
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x005e  */
            @Override // com.ibm.icu.util.CodePointMap.StringIterator
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public boolean next() {
                /*
                    r4 = this;
                    int r0 = r4.sIndex
                    java.lang.CharSequence r1 = r4.s
                    int r1 = r1.length()
                    if (r0 < r1) goto Lc
                    r0 = 0
                    return r0
                Lc:
                    java.lang.CharSequence r0 = r4.s
                    int r1 = r4.sIndex
                    int r2 = r1 + 1
                    r4.sIndex = r2
                    char r0 = r0.charAt(r1)
                    r4.c = r0
                    boolean r1 = java.lang.Character.isSurrogate(r0)
                    r2 = 1
                    if (r1 != 0) goto L2a
                    com.ibm.icu.util.CodePointTrie$Small r0 = com.ibm.icu.util.CodePointTrie.Small.this
                    int r1 = r4.c
                    int r0 = r0.cpIndex(r1)
                    goto L63
                L2a:
                    boolean r1 = com.ibm.icu.impl.Normalizer2Impl.UTF16Plus.isSurrogateLead(r0)
                    if (r1 == 0) goto L5e
                    int r1 = r4.sIndex
                    java.lang.CharSequence r3 = r4.s
                    int r3 = r3.length()
                    if (r1 >= r3) goto L5e
                    java.lang.CharSequence r1 = r4.s
                    int r3 = r4.sIndex
                    char r1 = r1.charAt(r3)
                    boolean r3 = java.lang.Character.isLowSurrogate(r1)
                    if (r3 == 0) goto L5e
                    int r3 = r4.sIndex
                    int r3 = r3 + r2
                    r4.sIndex = r3
                    int r0 = java.lang.Character.toCodePoint(r0, r1)
                    r4.c = r0
                    com.ibm.icu.util.CodePointTrie$Small r0 = com.ibm.icu.util.CodePointTrie.Small.this
                    com.ibm.icu.util.CodePointTrie$Type r1 = com.ibm.icu.util.CodePointTrie.Type.SMALL
                    int r3 = r4.c
                    int r0 = r0.smallIndex(r1, r3)
                    goto L63
                L5e:
                    com.ibm.icu.util.CodePointTrie$Small r0 = com.ibm.icu.util.CodePointTrie.Small.this
                    int r0 = r0.dataLength
                    int r0 = r0 - r2
                L63:
                    com.ibm.icu.util.CodePointTrie$Small r1 = com.ibm.icu.util.CodePointTrie.Small.this
                    com.ibm.icu.util.CodePointTrie$Data r1 = r1.data
                    int r0 = r1.getFromIndex(r0)
                    r4.value = r0
                    return r2
                */
                throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.CodePointTrie.Small.SmallStringIterator.next():boolean");
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0052  */
            @Override // com.ibm.icu.util.CodePointMap.StringIterator
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public boolean previous() {
                /*
                    r4 = this;
                    int r0 = r4.sIndex
                    if (r0 > 0) goto L6
                    r0 = 0
                    return r0
                L6:
                    java.lang.CharSequence r0 = r4.s
                    int r1 = r4.sIndex
                    r2 = 1
                    int r1 = r1 - r2
                    r4.sIndex = r1
                    char r0 = r0.charAt(r1)
                    r4.c = r0
                    boolean r1 = java.lang.Character.isSurrogate(r0)
                    if (r1 != 0) goto L23
                    com.ibm.icu.util.CodePointTrie$Small r0 = com.ibm.icu.util.CodePointTrie.Small.this
                    int r1 = r4.c
                    int r0 = r0.cpIndex(r1)
                    goto L57
                L23:
                    boolean r1 = com.ibm.icu.impl.Normalizer2Impl.UTF16Plus.isSurrogateLead(r0)
                    if (r1 != 0) goto L52
                    int r1 = r4.sIndex
                    if (r1 <= 0) goto L52
                    java.lang.CharSequence r1 = r4.s
                    int r3 = r4.sIndex
                    int r3 = r3 - r2
                    char r1 = r1.charAt(r3)
                    boolean r3 = java.lang.Character.isHighSurrogate(r1)
                    if (r3 == 0) goto L52
                    int r3 = r4.sIndex
                    int r3 = r3 - r2
                    r4.sIndex = r3
                    int r0 = java.lang.Character.toCodePoint(r1, r0)
                    r4.c = r0
                    com.ibm.icu.util.CodePointTrie$Small r0 = com.ibm.icu.util.CodePointTrie.Small.this
                    com.ibm.icu.util.CodePointTrie$Type r1 = com.ibm.icu.util.CodePointTrie.Type.SMALL
                    int r3 = r4.c
                    int r0 = r0.smallIndex(r1, r3)
                    goto L57
                L52:
                    com.ibm.icu.util.CodePointTrie$Small r0 = com.ibm.icu.util.CodePointTrie.Small.this
                    int r0 = r0.dataLength
                    int r0 = r0 - r2
                L57:
                    com.ibm.icu.util.CodePointTrie$Small r1 = com.ibm.icu.util.CodePointTrie.Small.this
                    com.ibm.icu.util.CodePointTrie$Data r1 = r1.data
                    int r0 = r1.getFromIndex(r0)
                    r4.value = r0
                    return r2
                */
                throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.CodePointTrie.Small.SmallStringIterator.previous():boolean");
            }
        }
    }

    public static final class Fast16 extends Fast {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final char[] dataArray;

        Fast16(char[] cArr, char[] cArr2, int i, int i2, int i3) {
            super(cArr, new Data16(cArr2), i, i2, i3, null);
            this.dataArray = cArr2;
        }

        public static Fast16 fromBinary(ByteBuffer byteBuffer) {
            return (Fast16) CodePointTrie.fromBinary(Type.FAST, ValueWidth.BITS_16, byteBuffer);
        }

        @Override // com.ibm.icu.util.CodePointTrie, com.ibm.icu.util.CodePointMap
        public final int get(int i) {
            return this.dataArray[cpIndex(i)];
        }

        @Override // com.ibm.icu.util.CodePointTrie.Fast
        public final int bmpGet(int i) {
            return this.dataArray[fastIndex(i)];
        }

        @Override // com.ibm.icu.util.CodePointTrie.Fast
        public final int suppGet(int i) {
            return this.dataArray[smallIndex(Type.FAST, i)];
        }
    }

    public static final class Fast32 extends Fast {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final int[] dataArray;

        Fast32(char[] cArr, int[] iArr, int i, int i2, int i3) {
            super(cArr, new Data32(iArr), i, i2, i3, null);
            this.dataArray = iArr;
        }

        public static Fast32 fromBinary(ByteBuffer byteBuffer) {
            return (Fast32) CodePointTrie.fromBinary(Type.FAST, ValueWidth.BITS_32, byteBuffer);
        }

        @Override // com.ibm.icu.util.CodePointTrie, com.ibm.icu.util.CodePointMap
        public final int get(int i) {
            return this.dataArray[cpIndex(i)];
        }

        @Override // com.ibm.icu.util.CodePointTrie.Fast
        public final int bmpGet(int i) {
            return this.dataArray[fastIndex(i)];
        }

        @Override // com.ibm.icu.util.CodePointTrie.Fast
        public final int suppGet(int i) {
            return this.dataArray[smallIndex(Type.FAST, i)];
        }
    }

    public static final class Fast8 extends Fast {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final byte[] dataArray;

        Fast8(char[] cArr, byte[] bArr, int i, int i2, int i3) {
            super(cArr, new Data8(bArr), i, i2, i3, null);
            this.dataArray = bArr;
        }

        public static Fast8 fromBinary(ByteBuffer byteBuffer) {
            return (Fast8) CodePointTrie.fromBinary(Type.FAST, ValueWidth.BITS_8, byteBuffer);
        }

        @Override // com.ibm.icu.util.CodePointTrie, com.ibm.icu.util.CodePointMap
        public final int get(int i) {
            return this.dataArray[cpIndex(i)] & 255;
        }

        @Override // com.ibm.icu.util.CodePointTrie.Fast
        public final int bmpGet(int i) {
            return this.dataArray[fastIndex(i)] & 255;
        }

        @Override // com.ibm.icu.util.CodePointTrie.Fast
        public final int suppGet(int i) {
            return this.dataArray[smallIndex(Type.FAST, i)] & 255;
        }
    }

    public static final class Small16 extends Small {
        Small16(char[] cArr, char[] cArr2, int i, int i2, int i3) {
            super(cArr, new Data16(cArr2), i, i2, i3, null);
        }

        public static Small16 fromBinary(ByteBuffer byteBuffer) {
            return (Small16) CodePointTrie.fromBinary(Type.SMALL, ValueWidth.BITS_16, byteBuffer);
        }
    }

    public static final class Small32 extends Small {
        Small32(char[] cArr, int[] iArr, int i, int i2, int i3) {
            super(cArr, new Data32(iArr), i, i2, i3, null);
        }

        public static Small32 fromBinary(ByteBuffer byteBuffer) {
            return (Small32) CodePointTrie.fromBinary(Type.SMALL, ValueWidth.BITS_32, byteBuffer);
        }
    }

    public static final class Small8 extends Small {
        Small8(char[] cArr, byte[] bArr, int i, int i2, int i3) {
            super(cArr, new Data8(bArr), i, i2, i3, null);
        }

        public static Small8 fromBinary(ByteBuffer byteBuffer) {
            return (Small8) CodePointTrie.fromBinary(Type.SMALL, ValueWidth.BITS_8, byteBuffer);
        }
    }
}
