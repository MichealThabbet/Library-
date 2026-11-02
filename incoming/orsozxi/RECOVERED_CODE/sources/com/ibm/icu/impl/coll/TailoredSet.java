package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.impl.Trie2;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.CharsTrie;
import java.io.IOException;
import java.util.Iterator;

/* loaded from: classes2.dex */
public final class TailoredSet {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private CollationData baseData;
    private CollationData data;
    private String suffix;
    private UnicodeSet tailored;
    private StringBuilder unreversedPrefix = new StringBuilder();

    public TailoredSet(UnicodeSet unicodeSet) {
        this.tailored = unicodeSet;
    }

    public void forData(CollationData collationData) throws IOException {
        this.data = collationData;
        this.baseData = collationData.base;
        Iterator<Trie2.Range> it2 = this.data.trie.iterator();
        while (it2.hasNext()) {
            Trie2.Range next = it2.next();
            if (next.leadSurrogate) {
                return;
            } else {
                enumTailoredRange(next.startCodePoint, next.endCodePoint, next.value, this);
            }
        }
    }

    private void enumTailoredRange(int i, int i2, int i3, TailoredSet tailoredSet) throws IOException {
        if (i3 == 192) {
            return;
        }
        tailoredSet.handleCE32(i, i2, i3);
    }

    private void handleCE32(int i, int i2, int i3) throws IOException {
        if (Collation.isSpecialCE32(i3) && (i3 = this.data.getIndirectCE32(i3)) == 192) {
            return;
        }
        do {
            CollationData collationData = this.baseData;
            int finalCE32 = collationData.getFinalCE32(collationData.getCE32(i));
            if (!Collation.isSelfContainedCE32(i3) || !Collation.isSelfContainedCE32(finalCE32)) {
                compare(i, i3, finalCE32);
            } else if (i3 != finalCE32) {
                this.tailored.add(i);
            }
            i++;
        } while (i <= i2);
    }

    private void compare(int i, int i2, int i3) throws IOException {
        if (Collation.isPrefixCE32(i2)) {
            int iIndexFromCE32 = Collation.indexFromCE32(i2);
            CollationData collationData = this.data;
            int finalCE32 = collationData.getFinalCE32(collationData.getCE32FromContexts(iIndexFromCE32));
            if (Collation.isPrefixCE32(i3)) {
                int iIndexFromCE322 = Collation.indexFromCE32(i3);
                CollationData collationData2 = this.baseData;
                int finalCE322 = collationData2.getFinalCE32(collationData2.getCE32FromContexts(iIndexFromCE322));
                comparePrefixes(i, this.data.contexts, iIndexFromCE32 + 2, this.baseData.contexts, iIndexFromCE322 + 2);
                i3 = finalCE322;
            } else {
                CollationData collationData3 = this.data;
                addPrefixes(collationData3, i, collationData3.contexts, iIndexFromCE32 + 2);
            }
            i2 = finalCE32;
        } else if (Collation.isPrefixCE32(i3)) {
            int iIndexFromCE323 = Collation.indexFromCE32(i3);
            CollationData collationData4 = this.baseData;
            int finalCE323 = collationData4.getFinalCE32(collationData4.getCE32FromContexts(iIndexFromCE323));
            CollationData collationData5 = this.baseData;
            addPrefixes(collationData5, i, collationData5.contexts, iIndexFromCE323 + 2);
            i3 = finalCE323;
        }
        if (Collation.isContractionCE32(i2)) {
            int iIndexFromCE324 = Collation.indexFromCE32(i2);
            if ((i2 & 256) != 0) {
                i2 = 1;
            } else {
                CollationData collationData6 = this.data;
                i2 = collationData6.getFinalCE32(collationData6.getCE32FromContexts(iIndexFromCE324));
            }
            if (Collation.isContractionCE32(i3)) {
                int iIndexFromCE325 = Collation.indexFromCE32(i3);
                if ((i3 & 256) != 0) {
                    i3 = 1;
                } else {
                    CollationData collationData7 = this.baseData;
                    i3 = collationData7.getFinalCE32(collationData7.getCE32FromContexts(iIndexFromCE325));
                }
                compareContractions(i, this.data.contexts, iIndexFromCE324 + 2, this.baseData.contexts, iIndexFromCE325 + 2);
            } else {
                addContractions(i, this.data.contexts, iIndexFromCE324 + 2);
            }
        } else if (Collation.isContractionCE32(i3)) {
            int iIndexFromCE326 = Collation.indexFromCE32(i3);
            CollationData collationData8 = this.baseData;
            int finalCE324 = collationData8.getFinalCE32(collationData8.getCE32FromContexts(iIndexFromCE326));
            addContractions(i, this.baseData.contexts, iIndexFromCE326 + 2);
            i3 = finalCE324;
        }
        int iTagFromCE32 = Collation.isSpecialCE32(i2) ? Collation.tagFromCE32(i2) : -1;
        int iTagFromCE322 = Collation.isSpecialCE32(i3) ? Collation.tagFromCE32(i3) : -1;
        if (iTagFromCE322 == 14) {
            if (!Collation.isLongPrimaryCE32(i2)) {
                add(i);
                return;
            } else {
                if (Collation.primaryFromLongPrimaryCE32(i2) != Collation.getThreeBytePrimaryForOffsetData(i, this.baseData.ces[Collation.indexFromCE32(i3)])) {
                    add(i);
                    return;
                }
            }
        }
        if (iTagFromCE32 != iTagFromCE322) {
            add(i);
            return;
        }
        int i4 = 0;
        if (iTagFromCE32 == 5) {
            int iLengthFromCE32 = Collation.lengthFromCE32(i2);
            if (iLengthFromCE32 != Collation.lengthFromCE32(i3)) {
                add(i);
                return;
            }
            int iIndexFromCE327 = Collation.indexFromCE32(i2);
            int iIndexFromCE328 = Collation.indexFromCE32(i3);
            while (i4 < iLengthFromCE32) {
                if (this.data.ce32s[iIndexFromCE327 + i4] != this.baseData.ce32s[iIndexFromCE328 + i4]) {
                    add(i);
                    return;
                }
                i4++;
            }
            return;
        }
        if (iTagFromCE32 == 6) {
            int iLengthFromCE322 = Collation.lengthFromCE32(i2);
            if (iLengthFromCE322 != Collation.lengthFromCE32(i3)) {
                add(i);
                return;
            }
            int iIndexFromCE329 = Collation.indexFromCE32(i2);
            int iIndexFromCE3210 = Collation.indexFromCE32(i3);
            while (i4 < iLengthFromCE322) {
                if (this.data.ces[iIndexFromCE329 + i4] != this.baseData.ces[iIndexFromCE3210 + i4]) {
                    add(i);
                    return;
                }
                i4++;
            }
            return;
        }
        if (iTagFromCE32 != 12) {
            if (i2 != i3) {
                add(i);
                return;
            }
            return;
        }
        StringBuilder sb = new StringBuilder();
        int iDecompose = Normalizer2Impl.Hangul.decompose(i, sb);
        if (this.tailored.contains(sb.charAt(0)) || this.tailored.contains(sb.charAt(1)) || (iDecompose == 3 && this.tailored.contains(sb.charAt(2)))) {
            add(i);
        }
    }

    private void comparePrefixes(int i, CharSequence charSequence, int i2, CharSequence charSequence2, int i3) {
        Iterator<CharsTrie.Entry> itIterator2 = new CharsTrie(charSequence, i2).iterator2();
        Iterator<CharsTrie.Entry> itIterator22 = new CharsTrie(charSequence2, i3).iterator2();
        while (true) {
            String string = null;
            String string2 = null;
            CharsTrie.Entry next = null;
            CharsTrie.Entry next2 = null;
            while (true) {
                if (string == null) {
                    if (itIterator2.hasNext()) {
                        next = itIterator2.next();
                        string = next.chars.toString();
                    } else {
                        next = null;
                        string = "\uffff";
                    }
                }
                if (string2 == null) {
                    if (itIterator22.hasNext()) {
                        next2 = itIterator22.next();
                        string2 = next2.chars.toString();
                    } else {
                        next2 = null;
                        string2 = "\uffff";
                    }
                }
                if (Utility.sameObjects(string, "\uffff") && Utility.sameObjects(string2, "\uffff")) {
                    return;
                }
                int iCompareTo = string.compareTo(string2);
                if (iCompareTo < 0) {
                    addPrefix(this.data, string, i, next.value);
                    string = null;
                    next = null;
                } else if (iCompareTo > 0) {
                    addPrefix(this.baseData, string2, i, next2.value);
                    string2 = null;
                    next2 = null;
                }
            }
            setPrefix(string);
            compare(i, next.value, next2.value);
            resetPrefix();
        }
    }

    private void compareContractions(int i, CharSequence charSequence, int i2, CharSequence charSequence2, int i3) {
        Iterator<CharsTrie.Entry> itIterator2 = new CharsTrie(charSequence, i2).iterator2();
        Iterator<CharsTrie.Entry> itIterator22 = new CharsTrie(charSequence2, i3).iterator2();
        while (true) {
            String string = null;
            String string2 = null;
            CharsTrie.Entry next = null;
            CharsTrie.Entry next2 = null;
            while (true) {
                if (string == null) {
                    if (itIterator2.hasNext()) {
                        next = itIterator2.next();
                        string = next.chars.toString();
                    } else {
                        next = null;
                        string = "\uffff\uffff";
                    }
                }
                if (string2 == null) {
                    if (itIterator22.hasNext()) {
                        next2 = itIterator22.next();
                        string2 = next2.chars.toString();
                    } else {
                        next2 = null;
                        string2 = "\uffff\uffff";
                    }
                }
                if (Utility.sameObjects(string, "\uffff\uffff") && Utility.sameObjects(string2, "\uffff\uffff")) {
                    return;
                }
                int iCompareTo = string.compareTo(string2);
                if (iCompareTo < 0) {
                    addSuffix(i, string);
                    string = null;
                    next = null;
                } else if (iCompareTo > 0) {
                    addSuffix(i, string2);
                    string2 = null;
                    next2 = null;
                }
            }
            this.suffix = string;
            compare(i, next.value, next2.value);
            this.suffix = null;
        }
    }

    private void addPrefixes(CollationData collationData, int i, CharSequence charSequence, int i2) {
        Iterator<CharsTrie.Entry> itIterator2 = new CharsTrie(charSequence, i2).iterator2();
        while (itIterator2.hasNext()) {
            CharsTrie.Entry next = itIterator2.next();
            addPrefix(collationData, next.chars, i, next.value);
        }
    }

    private void addPrefix(CollationData collationData, CharSequence charSequence, int i, int i2) {
        setPrefix(charSequence);
        int finalCE32 = collationData.getFinalCE32(i2);
        if (Collation.isContractionCE32(finalCE32)) {
            addContractions(i, collationData.contexts, Collation.indexFromCE32(finalCE32) + 2);
        }
        this.tailored.add(new StringBuilder(this.unreversedPrefix.appendCodePoint(i)));
        resetPrefix();
    }

    private void addContractions(int i, CharSequence charSequence, int i2) {
        Iterator<CharsTrie.Entry> itIterator2 = new CharsTrie(charSequence, i2).iterator2();
        while (itIterator2.hasNext()) {
            addSuffix(i, itIterator2.next().chars);
        }
    }

    private void addSuffix(int i, CharSequence charSequence) {
        UnicodeSet unicodeSet = this.tailored;
        StringBuilder sbAppendCodePoint = new StringBuilder(this.unreversedPrefix).appendCodePoint(i);
        sbAppendCodePoint.append(charSequence);
        unicodeSet.add(sbAppendCodePoint);
    }

    private void add(int i) {
        if (this.unreversedPrefix.length() == 0 && this.suffix == null) {
            this.tailored.add(i);
            return;
        }
        StringBuilder sb = new StringBuilder(this.unreversedPrefix);
        sb.appendCodePoint(i);
        String str = this.suffix;
        if (str != null) {
            sb.append(str);
        }
        this.tailored.add(sb);
    }

    private void setPrefix(CharSequence charSequence) {
        this.unreversedPrefix.setLength(0);
        StringBuilder sb = this.unreversedPrefix;
        sb.append(charSequence);
        sb.reverse();
    }

    private void resetPrefix() {
        this.unreversedPrefix.setLength(0);
    }
}
