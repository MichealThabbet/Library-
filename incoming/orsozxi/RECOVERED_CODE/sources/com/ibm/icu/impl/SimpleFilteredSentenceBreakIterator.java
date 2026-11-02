package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.text.BreakIterator;
import com.ibm.icu.text.FilteredBreakIteratorBuilder;
import com.ibm.icu.text.UCharacterIterator;
import com.ibm.icu.util.BytesTrie;
import com.ibm.icu.util.CharsTrie;
import com.ibm.icu.util.CharsTrieBuilder;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import com.ibm.icu.util.StringTrieBuilder;
import com.ibm.icu.util.ULocale;
import java.text.CharacterIterator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

/* loaded from: classes2.dex */
public class SimpleFilteredSentenceBreakIterator extends BreakIterator {
    private CharsTrie backwardsTrie;
    private BreakIterator delegate;
    private CharsTrie forwardsPartialTrie;
    private UCharacterIterator text;

    public SimpleFilteredSentenceBreakIterator(BreakIterator breakIterator, CharsTrie charsTrie, CharsTrie charsTrie2) {
        this.delegate = breakIterator;
        this.forwardsPartialTrie = charsTrie;
        this.backwardsTrie = charsTrie2;
    }

    private final void resetState() {
        this.text = UCharacterIterator.getInstance((CharacterIterator) this.delegate.getText().clone());
    }

    private final boolean breakExceptionAt(int i) {
        CharsTrie charsTrie;
        BytesTrie.Result resultNextForCodePoint;
        this.text.setIndex(i);
        this.backwardsTrie.reset();
        if (this.text.previousCodePoint() != 32) {
            this.text.nextCodePoint();
        }
        int index = -1;
        int value = -1;
        do {
            int iPreviousCodePoint = this.text.previousCodePoint();
            if (iPreviousCodePoint < 0) {
                break;
            }
            resultNextForCodePoint = this.backwardsTrie.nextForCodePoint(iPreviousCodePoint);
            if (resultNextForCodePoint.hasValue()) {
                index = this.text.getIndex();
                value = this.backwardsTrie.getValue();
            }
        } while (resultNextForCodePoint.hasNext());
        this.backwardsTrie.reset();
        if (index < 0) {
            return false;
        }
        if (value == 2) {
            return true;
        }
        if (value != 1 || (charsTrie = this.forwardsPartialTrie) == null) {
            return false;
        }
        charsTrie.reset();
        BytesTrie.Result resultNextForCodePoint2 = BytesTrie.Result.INTERMEDIATE_VALUE;
        this.text.setIndex(index);
        do {
            int iNextCodePoint = this.text.nextCodePoint();
            if (iNextCodePoint == -1) {
                break;
            }
            resultNextForCodePoint2 = this.forwardsPartialTrie.nextForCodePoint(iNextCodePoint);
        } while (resultNextForCodePoint2.hasNext());
        this.forwardsPartialTrie.reset();
        return resultNextForCodePoint2.matches();
    }

    private final int internalNext(int i) {
        if (i != -1 && this.backwardsTrie != null) {
            resetState();
            int length = this.text.getLength();
            while (i != -1 && i != length && breakExceptionAt(i)) {
                i = this.delegate.next();
            }
        }
        return i;
    }

    private final int internalPrev(int i) {
        if (i != 0 && i != -1 && this.backwardsTrie != null) {
            resetState();
            while (i != -1 && i != 0 && breakExceptionAt(i)) {
                i = this.delegate.previous();
            }
        }
        return i;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SimpleFilteredSentenceBreakIterator simpleFilteredSentenceBreakIterator = (SimpleFilteredSentenceBreakIterator) obj;
        return this.delegate.equals(simpleFilteredSentenceBreakIterator.delegate) && this.text.equals(simpleFilteredSentenceBreakIterator.text) && this.backwardsTrie.equals(simpleFilteredSentenceBreakIterator.backwardsTrie) && this.forwardsPartialTrie.equals(simpleFilteredSentenceBreakIterator.forwardsPartialTrie);
    }

    public int hashCode() {
        return (this.forwardsPartialTrie.hashCode() * 39) + (this.backwardsTrie.hashCode() * 11) + this.delegate.hashCode();
    }

    @Override // com.ibm.icu.text.BreakIterator
    public Object clone() {
        SimpleFilteredSentenceBreakIterator simpleFilteredSentenceBreakIterator = (SimpleFilteredSentenceBreakIterator) super.clone();
        try {
            if (this.delegate != null) {
                simpleFilteredSentenceBreakIterator.delegate = (BreakIterator) this.delegate.clone();
            }
            if (this.text != null) {
                simpleFilteredSentenceBreakIterator.text = (UCharacterIterator) this.text.clone();
            }
            if (this.backwardsTrie != null) {
                simpleFilteredSentenceBreakIterator.backwardsTrie = this.backwardsTrie.m26clone();
            }
            if (this.forwardsPartialTrie != null) {
                simpleFilteredSentenceBreakIterator.forwardsPartialTrie = this.forwardsPartialTrie.m26clone();
            }
            return simpleFilteredSentenceBreakIterator;
        } catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException(e);
        }
    }

    @Override // com.ibm.icu.text.BreakIterator
    public int first() {
        return this.delegate.first();
    }

    @Override // com.ibm.icu.text.BreakIterator
    public int preceding(int i) {
        return internalPrev(this.delegate.preceding(i));
    }

    @Override // com.ibm.icu.text.BreakIterator
    public int previous() {
        return internalPrev(this.delegate.previous());
    }

    @Override // com.ibm.icu.text.BreakIterator
    public int current() {
        return this.delegate.current();
    }

    @Override // com.ibm.icu.text.BreakIterator
    public boolean isBoundary(int i) {
        if (!this.delegate.isBoundary(i)) {
            return false;
        }
        if (this.backwardsTrie == null) {
            return true;
        }
        resetState();
        return !breakExceptionAt(i);
    }

    @Override // com.ibm.icu.text.BreakIterator
    public int next() {
        return internalNext(this.delegate.next());
    }

    @Override // com.ibm.icu.text.BreakIterator
    public int next(int i) {
        return internalNext(this.delegate.next(i));
    }

    @Override // com.ibm.icu.text.BreakIterator
    public int following(int i) {
        return internalNext(this.delegate.following(i));
    }

    @Override // com.ibm.icu.text.BreakIterator
    public int last() {
        return this.delegate.last();
    }

    @Override // com.ibm.icu.text.BreakIterator
    public CharacterIterator getText() {
        return this.delegate.getText();
    }

    @Override // com.ibm.icu.text.BreakIterator
    public void setText(CharacterIterator characterIterator) {
        this.delegate.setText(characterIterator);
    }

    public static class Builder extends FilteredBreakIteratorBuilder {
        static final int AddToForward = 2;
        static final int MATCH = 2;
        static final int PARTIAL = 1;
        static final int SuppressInReverse = 1;
        private HashSet<CharSequence> filterSet;

        public Builder(Locale locale) {
            this(ULocale.forLocale(locale));
        }

        public Builder(ULocale uLocale) {
            this.filterSet = new HashSet<>();
            ICUResourceBundle iCUResourceBundleFindWithFallback = ICUResourceBundle.getBundleInstance(ICUData.ICU_BRKITR_BASE_NAME, uLocale, ICUResourceBundle.OpenType.LOCALE_ROOT).findWithFallback("exceptions/SentenceBreak");
            if (iCUResourceBundleFindWithFallback != null) {
                int size = iCUResourceBundleFindWithFallback.getSize();
                for (int i = 0; i < size; i++) {
                    this.filterSet.add(((ICUResourceBundle) iCUResourceBundleFindWithFallback.get(i)).getString());
                }
            }
        }

        public Builder() {
            this.filterSet = new HashSet<>();
        }

        @Override // com.ibm.icu.text.FilteredBreakIteratorBuilder
        public boolean suppressBreakAfter(CharSequence charSequence) {
            return this.filterSet.add(charSequence);
        }

        @Override // com.ibm.icu.text.FilteredBreakIteratorBuilder
        public boolean unsuppressBreakAfter(CharSequence charSequence) {
            return this.filterSet.remove(charSequence);
        }

        @Override // com.ibm.icu.text.FilteredBreakIteratorBuilder
        public BreakIterator wrapIteratorWithFilter(BreakIterator breakIterator) {
            int i;
            if (this.filterSet.isEmpty()) {
                return breakIterator;
            }
            CharsTrieBuilder charsTrieBuilder = new CharsTrieBuilder();
            CharsTrieBuilder charsTrieBuilder2 = new CharsTrieBuilder();
            int size = this.filterSet.size();
            CharSequence[] charSequenceArr = new CharSequence[size];
            int[] iArr = new int[size];
            Iterator<CharSequence> it2 = this.filterSet.iterator();
            int i2 = 0;
            while (it2.hasNext()) {
                charSequenceArr[i2] = it2.next();
                iArr[i2] = 0;
                i2++;
            }
            int i3 = 0;
            for (int i4 = 0; i4 < size; i4++) {
                String string = charSequenceArr[i4].toString();
                int iIndexOf = string.indexOf(46);
                int i5 = -1;
                if (iIndexOf > -1 && (i = iIndexOf + 1) != string.length()) {
                    int i6 = 0;
                    int i7 = -1;
                    while (i6 < size) {
                        if (i6 != i4 && string.regionMatches(0, charSequenceArr[i6].toString(), 0, i)) {
                            if (iArr[i6] == 0) {
                                iArr[i6] = 3;
                            } else if ((iArr[i6] & 1) != 0) {
                                i7 = i6;
                            }
                        }
                        i6++;
                        i5 = -1;
                    }
                    if (i7 == i5 && iArr[i4] == 0) {
                        StringBuilder sb = new StringBuilder(string.substring(0, i));
                        sb.reverse();
                        charsTrieBuilder.add(sb, 1);
                        i3++;
                        iArr[i4] = 3;
                    }
                }
            }
            int i8 = 0;
            for (int i9 = 0; i9 < size; i9++) {
                String string2 = charSequenceArr[i9].toString();
                if (iArr[i9] == 0) {
                    charsTrieBuilder.add(new StringBuilder(string2).reverse(), 2);
                    i3++;
                } else {
                    charsTrieBuilder2.add(string2, 2);
                    i8++;
                }
            }
            return new SimpleFilteredSentenceBreakIterator(breakIterator, i8 > 0 ? charsTrieBuilder2.build(StringTrieBuilder.Option.FAST) : null, i3 > 0 ? charsTrieBuilder.build(StringTrieBuilder.Option.FAST) : null);
        }
    }
}
