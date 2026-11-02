package com.ibm.icu.text;

import com.ibm.icu.impl.Norm2AllModes;
import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.lang.UCharacter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes2.dex */
public final class CanonicalIterator {
    private static boolean PROGRESS = false;
    private static final Set<String> SET_WITH_NULL_STRING;
    private static boolean SKIP_ZEROS = true;
    private transient StringBuilder buffer = new StringBuilder();
    private int[] current;
    private boolean done;
    private final Normalizer2Impl nfcImpl;
    private final Normalizer2 nfd;
    private String[][] pieces;
    private String source;

    public CanonicalIterator(String str) {
        Norm2AllModes nFCInstance = Norm2AllModes.getNFCInstance();
        this.nfd = nFCInstance.decomp;
        this.nfcImpl = nFCInstance.impl.ensureCanonIterData();
        setSource(str);
    }

    public String getSource() {
        return this.source;
    }

    public void reset() {
        this.done = false;
        int i = 0;
        while (true) {
            int[] iArr = this.current;
            if (i >= iArr.length) {
                return;
            }
            iArr[i] = 0;
            i++;
        }
    }

    public String next() {
        if (this.done) {
            return null;
        }
        this.buffer.setLength(0);
        int i = 0;
        while (true) {
            String[][] strArr = this.pieces;
            if (i >= strArr.length) {
                break;
            }
            this.buffer.append(strArr[i][this.current[i]]);
            i++;
        }
        String string = this.buffer.toString();
        int length = this.current.length - 1;
        while (true) {
            if (length < 0) {
                this.done = true;
                break;
            }
            int[] iArr = this.current;
            iArr[length] = iArr[length] + 1;
            if (iArr[length] < this.pieces[length].length) {
                break;
            }
            iArr[length] = 0;
            length--;
        }
        return string;
    }

    public void setSource(String str) {
        this.source = this.nfd.normalize(str);
        this.done = false;
        if (str.length() == 0) {
            this.pieces = new String[][]{new String[]{""}};
            this.current = new int[1];
            return;
        }
        ArrayList arrayList = new ArrayList();
        int iFindOffsetFromCodePoint = UTF16.findOffsetFromCodePoint(this.source, 1);
        int i = 0;
        while (iFindOffsetFromCodePoint < this.source.length()) {
            int iCodePointAt = this.source.codePointAt(iFindOffsetFromCodePoint);
            if (this.nfcImpl.isCanonSegmentStarter(iCodePointAt)) {
                arrayList.add(this.source.substring(i, iFindOffsetFromCodePoint));
                i = iFindOffsetFromCodePoint;
            }
            iFindOffsetFromCodePoint += Character.charCount(iCodePointAt);
        }
        arrayList.add(this.source.substring(i, iFindOffsetFromCodePoint));
        this.pieces = new String[arrayList.size()][];
        this.current = new int[arrayList.size()];
        for (int i2 = 0; i2 < this.pieces.length; i2++) {
            if (PROGRESS) {
                System.out.println("SEGMENT");
            }
            this.pieces[i2] = getEquivalents((String) arrayList.get(i2));
        }
    }

    @Deprecated
    public static void permute(String str, boolean z, Set<String> set) {
        if (str.length() <= 2 && UTF16.countCodePoint(str) <= 1) {
            set.add(str);
            return;
        }
        HashSet hashSet = new HashSet();
        int charCount = 0;
        while (charCount < str.length()) {
            int iCharAt = UTF16.charAt(str, charCount);
            if (!z || charCount == 0 || UCharacter.getCombiningClass(iCharAt) != 0) {
                hashSet.clear();
                permute(str.substring(0, charCount) + str.substring(UTF16.getCharCount(iCharAt) + charCount), z, hashSet);
                String strValueOf = UTF16.valueOf(str, charCount);
                Iterator it2 = hashSet.iterator();
                while (it2.hasNext()) {
                    set.add(strValueOf + ((String) it2.next()));
                }
            }
            charCount += UTF16.getCharCount(iCharAt);
        }
    }

    private String[] getEquivalents(String str) {
        HashSet hashSet = new HashSet();
        Set<String> equivalents2 = getEquivalents2(str);
        HashSet<String> hashSet2 = new HashSet();
        for (String str2 : equivalents2) {
            hashSet2.clear();
            permute(str2, SKIP_ZEROS, hashSet2);
            for (String str3 : hashSet2) {
                if (Normalizer.compare(str3, str, 0) == 0) {
                    if (PROGRESS) {
                        System.out.println("Adding Permutation: " + Utility.hex(str3));
                    }
                    hashSet.add(str3);
                } else if (PROGRESS) {
                    System.out.println("-Skipping Permutation: " + Utility.hex(str3));
                }
            }
        }
        String[] strArr = new String[hashSet.size()];
        hashSet.toArray(strArr);
        return strArr;
    }

    private Set<String> getEquivalents2(String str) {
        HashSet hashSet = new HashSet();
        if (PROGRESS) {
            System.out.println("Adding: " + Utility.hex(str));
        }
        hashSet.add(str);
        StringBuffer stringBuffer = new StringBuffer();
        UnicodeSet unicodeSet = new UnicodeSet();
        int iCharCount = 0;
        while (iCharCount < str.length()) {
            int iCodePointAt = str.codePointAt(iCharCount);
            if (this.nfcImpl.getCanonStartSet(iCodePointAt, unicodeSet)) {
                UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(unicodeSet);
                while (unicodeSetIterator.next()) {
                    int i = unicodeSetIterator.codepoint;
                    Set<String> setExtract = extract(i, str, iCharCount, stringBuffer);
                    if (setExtract != null) {
                        String str2 = str.substring(0, iCharCount) + UTF16.valueOf(i);
                        Iterator<String> it2 = setExtract.iterator();
                        while (it2.hasNext()) {
                            hashSet.add(str2 + it2.next());
                        }
                    }
                }
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        return hashSet;
    }

    private Set<String> extract(int i, String str, int i2, StringBuffer stringBuffer) {
        boolean z;
        if (PROGRESS) {
            System.out.println(" extract: " + Utility.hex(UTF16.valueOf(i)) + ", " + Utility.hex(str.substring(i2)));
        }
        String decomposition = this.nfcImpl.getDecomposition(i);
        if (decomposition == null) {
            decomposition = UTF16.valueOf(i);
        }
        int iCharAt = UTF16.charAt(decomposition, 0);
        int charCount = UTF16.getCharCount(iCharAt) + 0;
        stringBuffer.setLength(0);
        int charCount2 = i2;
        while (true) {
            if (charCount2 >= str.length()) {
                z = false;
                break;
            }
            int iCharAt2 = UTF16.charAt(str, charCount2);
            if (iCharAt2 == iCharAt) {
                if (PROGRESS) {
                    System.out.println("  matches: " + Utility.hex(UTF16.valueOf(iCharAt2)));
                }
                if (charCount == decomposition.length()) {
                    stringBuffer.append(str.substring(charCount2 + UTF16.getCharCount(iCharAt2)));
                    z = true;
                    break;
                }
                iCharAt = UTF16.charAt(decomposition, charCount);
                charCount += UTF16.getCharCount(iCharAt);
            } else {
                if (PROGRESS) {
                    System.out.println("  buffer: " + Utility.hex(UTF16.valueOf(iCharAt2)));
                }
                UTF16.append(stringBuffer, iCharAt2);
            }
            charCount2 += UTF16.getCharCount(iCharAt2);
        }
        if (!z) {
            return null;
        }
        if (PROGRESS) {
            System.out.println("Matches");
        }
        if (stringBuffer.length() == 0) {
            return SET_WITH_NULL_STRING;
        }
        String string = stringBuffer.toString();
        if (Normalizer.compare(UTF16.valueOf(i) + string, str.substring(i2), 0) != 0) {
            return null;
        }
        return getEquivalents2(string);
    }

    static {
        HashSet hashSet = new HashSet();
        SET_WITH_NULL_STRING = hashSet;
        hashSet.add("");
    }
}
