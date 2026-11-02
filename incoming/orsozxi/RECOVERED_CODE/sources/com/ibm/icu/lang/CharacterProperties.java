package com.ibm.icu.lang;

import com.ibm.icu.impl.CharacterPropertiesImpl;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.CodePointMap;
import com.ibm.icu.util.CodePointTrie;
import com.ibm.icu.util.MutableCodePointTrie;

/* loaded from: classes2.dex */
public final class CharacterProperties {
    private static final UnicodeSet[] sets = new UnicodeSet[65];
    private static final CodePointMap[] maps = new CodePointMap[25];

    private CharacterProperties() {
    }

    private static UnicodeSet makeSet(int i) {
        UnicodeSet unicodeSet = new UnicodeSet();
        UnicodeSet inclusionsForProperty = CharacterPropertiesImpl.getInclusionsForProperty(i);
        int rangeCount = inclusionsForProperty.getRangeCount();
        int i2 = -1;
        for (int i3 = 0; i3 < rangeCount; i3++) {
            int rangeEnd = inclusionsForProperty.getRangeEnd(i3);
            for (int rangeStart = inclusionsForProperty.getRangeStart(i3); rangeStart <= rangeEnd; rangeStart++) {
                if (UCharacter.hasBinaryProperty(rangeStart, i)) {
                    if (i2 < 0) {
                        i2 = rangeStart;
                    }
                } else if (i2 >= 0) {
                    unicodeSet.add(i2, rangeStart - 1);
                    i2 = -1;
                }
            }
        }
        if (i2 >= 0) {
            unicodeSet.add(i2, 1114111);
        }
        return unicodeSet.freeze();
    }

    private static CodePointMap makeMap(int i) {
        CodePointTrie.Type type;
        CodePointTrie.ValueWidth valueWidth;
        int i2 = i == 4106 ? 103 : 0;
        MutableCodePointTrie mutableCodePointTrie = new MutableCodePointTrie(i2, i2);
        UnicodeSet inclusionsForProperty = CharacterPropertiesImpl.getInclusionsForProperty(i);
        int rangeCount = inclusionsForProperty.getRangeCount();
        int i3 = i2;
        int i4 = 0;
        for (int i5 = 0; i5 < rangeCount; i5++) {
            int rangeEnd = inclusionsForProperty.getRangeEnd(i5);
            for (int rangeStart = inclusionsForProperty.getRangeStart(i5); rangeStart <= rangeEnd; rangeStart++) {
                int intPropertyValue = UCharacter.getIntPropertyValue(rangeStart, i);
                if (i3 != intPropertyValue) {
                    if (i3 != i2) {
                        mutableCodePointTrie.setRange(i4, rangeStart - 1, i3);
                    }
                    i4 = rangeStart;
                    i3 = intPropertyValue;
                }
            }
        }
        if (i3 != 0) {
            mutableCodePointTrie.setRange(i4, 1114111, i3);
        }
        if (i == 4096 || i == 4101) {
            type = CodePointTrie.Type.FAST;
        } else {
            type = CodePointTrie.Type.SMALL;
        }
        int intPropertyMaxValue = UCharacter.getIntPropertyMaxValue(i);
        if (intPropertyMaxValue <= 255) {
            valueWidth = CodePointTrie.ValueWidth.BITS_8;
        } else if (intPropertyMaxValue <= 65535) {
            valueWidth = CodePointTrie.ValueWidth.BITS_16;
        } else {
            valueWidth = CodePointTrie.ValueWidth.BITS_32;
        }
        return mutableCodePointTrie.buildImmutable(type, valueWidth);
    }

    public static final UnicodeSet getBinaryPropertySet(int i) {
        UnicodeSet unicodeSet;
        if (i < 0 || 65 <= i) {
            throw new IllegalArgumentException("" + i + " is not a constant for a UProperty binary property");
        }
        synchronized (sets) {
            unicodeSet = sets[i];
            if (unicodeSet == null) {
                UnicodeSet[] unicodeSetArr = sets;
                UnicodeSet unicodeSetMakeSet = makeSet(i);
                unicodeSetArr[i] = unicodeSetMakeSet;
                unicodeSet = unicodeSetMakeSet;
            }
        }
        return unicodeSet;
    }

    public static final CodePointMap getIntPropertyMap(int i) {
        CodePointMap codePointMap;
        if (i < 4096 || 4121 <= i) {
            throw new IllegalArgumentException("" + i + " is not a constant for a UProperty int property");
        }
        synchronized (maps) {
            int i2 = i - 4096;
            codePointMap = maps[i2];
            if (codePointMap == null) {
                CodePointMap[] codePointMapArr = maps;
                CodePointMap codePointMapMakeMap = makeMap(i);
                codePointMapArr[i2] = codePointMapMakeMap;
                codePointMap = codePointMapMakeMap;
            }
        }
        return codePointMap;
    }
}
