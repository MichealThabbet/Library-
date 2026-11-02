package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.util.BytesTrie;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.MissingResourceException;

/* loaded from: classes2.dex */
public final class UPropertyAliases {
    private static final int DATA_FORMAT = 1886282093;
    public static final UPropertyAliases INSTANCE;
    private static final IsAcceptable IS_ACCEPTABLE = new IsAcceptable();
    private static final int IX_BYTE_TRIES_OFFSET = 1;
    private static final int IX_NAME_GROUPS_OFFSET = 2;
    private static final int IX_RESERVED3_OFFSET = 3;
    private static final int IX_VALUE_MAPS_OFFSET = 0;
    private byte[] bytesTries;
    private String nameGroups;
    private int[] valueMaps;

    private static int asciiToLowercase(int i) {
        return (65 > i || i > 90) ? i : i + 32;
    }

    private static final class IsAcceptable implements ICUBinary.Authenticate {
        private IsAcceptable() {
        }

        @Override // com.ibm.icu.impl.ICUBinary.Authenticate
        public boolean isDataVersionAcceptable(byte[] bArr) {
            return bArr[0] == 2;
        }
    }

    static {
        try {
            INSTANCE = new UPropertyAliases();
        } catch (IOException e) {
            MissingResourceException missingResourceException = new MissingResourceException("Could not construct UPropertyAliases. Missing pnames.icu", "", "");
            missingResourceException.initCause(e);
            throw missingResourceException;
        }
    }

    private void load(ByteBuffer byteBuffer) throws IOException {
        ICUBinary.readHeader(byteBuffer, DATA_FORMAT, IS_ACCEPTABLE);
        int i = byteBuffer.getInt() / 4;
        if (i < 8) {
            throw new IOException("pnames.icu: not enough indexes");
        }
        int[] iArr = new int[i];
        iArr[0] = i * 4;
        for (int i2 = 1; i2 < i; i2++) {
            iArr[i2] = byteBuffer.getInt();
        }
        int i3 = iArr[0];
        int i4 = iArr[1];
        this.valueMaps = ICUBinary.getInts(byteBuffer, (i4 - i3) / 4, 0);
        int i5 = iArr[2];
        byte[] bArr = new byte[i5 - i4];
        this.bytesTries = bArr;
        byteBuffer.get(bArr);
        int i6 = iArr[3] - i5;
        StringBuilder sb = new StringBuilder(i6);
        for (int i7 = 0; i7 < i6; i7++) {
            sb.append((char) byteBuffer.get());
        }
        this.nameGroups = sb.toString();
    }

    private UPropertyAliases() throws IOException {
        load(ICUBinary.getRequiredData("pnames.icu"));
    }

    private int findProperty(int i) {
        int i2 = 1;
        for (int i3 = this.valueMaps[0]; i3 > 0; i3--) {
            int[] iArr = this.valueMaps;
            int i4 = iArr[i2];
            int i5 = iArr[i2 + 1];
            int i6 = i2 + 2;
            if (i < i4) {
                break;
            }
            if (i < i5) {
                return i6 + ((i - i4) * 2);
            }
            i2 = i6 + ((i5 - i4) * 2);
        }
        return 0;
    }

    private int findPropertyValueNameGroup(int i, int i2) {
        if (i == 0) {
            return 0;
        }
        int i3 = i + 1;
        int i4 = i3 + 1;
        int i5 = this.valueMaps[i3];
        if (i5 >= 16) {
            int i6 = (i5 + i4) - 16;
            int i7 = i4;
            do {
                int[] iArr = this.valueMaps;
                int i8 = iArr[i7];
                if (i2 < i8) {
                    break;
                }
                if (i2 == i8) {
                    return iArr[(i6 + i7) - i4];
                }
                i7++;
            } while (i7 < i6);
        } else {
            while (i5 > 0) {
                int[] iArr2 = this.valueMaps;
                int i9 = iArr2[i4];
                int i10 = iArr2[i4 + 1];
                int i11 = i4 + 2;
                if (i2 < i9) {
                    break;
                }
                if (i2 < i10) {
                    return iArr2[(i11 + i2) - i9];
                }
                i4 = i11 + (i10 - i9);
                i5--;
            }
        }
        return 0;
    }

    private String getName(int i, int i2) {
        int i3;
        int i4 = i + 1;
        char cCharAt = this.nameGroups.charAt(i);
        if (i2 < 0 || cCharAt <= i2) {
            throw new IllegalIcuArgumentException("Invalid property (value) name choice");
        }
        while (i2 > 0) {
            while (true) {
                i3 = i4 + 1;
                if (this.nameGroups.charAt(i4) != 0) {
                    i4 = i3;
                }
            }
            i2--;
            i4 = i3;
        }
        int i5 = i4;
        while (this.nameGroups.charAt(i5) != 0) {
            i5++;
        }
        if (i4 == i5) {
            return null;
        }
        return this.nameGroups.substring(i4, i5);
    }

    private boolean containsName(BytesTrie bytesTrie, CharSequence charSequence) {
        BytesTrie.Result next = BytesTrie.Result.NO_VALUE;
        for (int i = 0; i < charSequence.length(); i++) {
            char cCharAt = charSequence.charAt(i);
            if (cCharAt != '-' && cCharAt != '_' && cCharAt != ' ' && ('\t' > cCharAt || cCharAt > '\r')) {
                if (!next.hasNext()) {
                    return false;
                }
                next = bytesTrie.next(asciiToLowercase(cCharAt));
            }
        }
        return next.hasValue();
    }

    public String getPropertyName(int i, int i2) {
        int iFindProperty = findProperty(i);
        if (iFindProperty == 0) {
            throw new IllegalArgumentException("Invalid property enum " + i + " (0x" + Integer.toHexString(i) + ")");
        }
        return getName(this.valueMaps[iFindProperty], i2);
    }

    public String getPropertyValueName(int i, int i2, int i3) {
        int iFindProperty = findProperty(i);
        if (iFindProperty == 0) {
            throw new IllegalArgumentException("Invalid property enum " + i + " (0x" + Integer.toHexString(i) + ")");
        }
        int iFindPropertyValueNameGroup = findPropertyValueNameGroup(this.valueMaps[iFindProperty + 1], i2);
        if (iFindPropertyValueNameGroup == 0) {
            throw new IllegalArgumentException("Property " + i + " (0x" + Integer.toHexString(i) + ") does not have named values");
        }
        return getName(iFindPropertyValueNameGroup, i3);
    }

    private int getPropertyOrValueEnum(int i, CharSequence charSequence) {
        BytesTrie bytesTrie = new BytesTrie(this.bytesTries, i);
        if (containsName(bytesTrie, charSequence)) {
            return bytesTrie.getValue();
        }
        return -1;
    }

    public int getPropertyEnum(CharSequence charSequence) {
        return getPropertyOrValueEnum(0, charSequence);
    }

    public int getPropertyValueEnum(int i, CharSequence charSequence) {
        int iFindProperty = findProperty(i);
        if (iFindProperty == 0) {
            throw new IllegalArgumentException("Invalid property enum " + i + " (0x" + Integer.toHexString(i) + ")");
        }
        int[] iArr = this.valueMaps;
        int i2 = iArr[iFindProperty + 1];
        if (i2 == 0) {
            throw new IllegalArgumentException("Property " + i + " (0x" + Integer.toHexString(i) + ") does not have named values");
        }
        return getPropertyOrValueEnum(iArr[i2], charSequence);
    }

    public int getPropertyValueEnumNoThrow(int i, CharSequence charSequence) {
        int[] iArr;
        int i2;
        int iFindProperty = findProperty(i);
        if (iFindProperty == 0 || (i2 = (iArr = this.valueMaps)[iFindProperty + 1]) == 0) {
            return -1;
        }
        return getPropertyOrValueEnum(iArr[i2], charSequence);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0060 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int compare(java.lang.String r9, java.lang.String r10) {
        /*
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 0
        L5:
            int r5 = r9.length()
            r6 = 95
            r7 = 45
            r8 = 32
            if (r1 >= r5) goto L22
            char r3 = r9.charAt(r1)
            if (r3 == r8) goto L1f
            if (r3 == r7) goto L1f
            if (r3 == r6) goto L1f
            switch(r3) {
                case 9: goto L1f;
                case 10: goto L1f;
                case 11: goto L1f;
                case 12: goto L1f;
                case 13: goto L1f;
                default: goto L1e;
            }
        L1e:
            goto L22
        L1f:
            int r1 = r1 + 1
            goto L5
        L22:
            int r5 = r10.length()
            if (r2 >= r5) goto L39
            char r4 = r10.charAt(r2)
            if (r4 == r8) goto L36
            if (r4 == r7) goto L36
            if (r4 == r6) goto L36
            switch(r4) {
                case 9: goto L36;
                case 10: goto L36;
                case 11: goto L36;
                case 12: goto L36;
                case 13: goto L36;
                default: goto L35;
            }
        L35:
            goto L39
        L36:
            int r2 = r2 + 1
            goto L22
        L39:
            int r5 = r9.length()
            r6 = 1
            if (r1 != r5) goto L42
            r5 = 1
            goto L43
        L42:
            r5 = 0
        L43:
            int r7 = r10.length()
            if (r2 != r7) goto L4a
            goto L4b
        L4a:
            r6 = 0
        L4b:
            if (r5 == 0) goto L52
            if (r6 == 0) goto L50
            return r0
        L50:
            r3 = 0
            goto L55
        L52:
            if (r6 == 0) goto L55
            r4 = 0
        L55:
            int r5 = asciiToLowercase(r3)
            int r6 = asciiToLowercase(r4)
            int r5 = r5 - r6
            if (r5 == 0) goto L61
            return r5
        L61:
            int r1 = r1 + 1
            int r2 = r2 + 1
            goto L5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.UPropertyAliases.compare(java.lang.String, java.lang.String):int");
    }
}
