package com.ibm.icu.text;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import com.ibm.icu.util.ULocale;
import java.text.CharacterIterator;

/* loaded from: classes2.dex */
final class BreakTransliterator extends Transliterator {
    static final int LETTER_OR_MARK_MASK = 510;
    private BreakIterator bi;
    private int[] boundaries;
    private int boundaryCount;
    private String insertion;

    public BreakTransliterator(String str, UnicodeFilter unicodeFilter, BreakIterator breakIterator, String str2) {
        super(str, unicodeFilter);
        this.boundaries = new int[50];
        this.boundaryCount = 0;
        this.bi = breakIterator;
        this.insertion = str2;
    }

    public BreakTransliterator(String str, UnicodeFilter unicodeFilter) {
        this(str, unicodeFilter, null, " ");
    }

    public String getInsertion() {
        return this.insertion;
    }

    public void setInsertion(String str) {
        this.insertion = str;
    }

    public BreakIterator getBreakIterator() {
        if (this.bi == null) {
            this.bi = BreakIterator.getWordInstance(new ULocale("th_TH"));
        }
        return this.bi;
    }

    public void setBreakIterator(BreakIterator breakIterator) {
        this.bi = breakIterator;
    }

    @Override // com.ibm.icu.text.Transliterator
    protected synchronized void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean z) {
        int i;
        int length = 0;
        this.boundaryCount = 0;
        getBreakIterator();
        this.bi.setText(new ReplaceableCharacterIterator(replaceable, position.start, position.limit, position.start));
        int iFirst = this.bi.first();
        while (iFirst != -1 && iFirst < position.limit) {
            if (iFirst != 0 && ((1 << UCharacter.getType(UTF16.charAt(replaceable, iFirst - 1))) & 510) != 0 && ((1 << UCharacter.getType(UTF16.charAt(replaceable, iFirst))) & 510) != 0) {
                if (this.boundaryCount >= this.boundaries.length) {
                    int[] iArr = new int[this.boundaries.length * 2];
                    System.arraycopy(this.boundaries, 0, iArr, 0, this.boundaries.length);
                    this.boundaries = iArr;
                }
                int[] iArr2 = this.boundaries;
                int i2 = this.boundaryCount;
                this.boundaryCount = i2 + 1;
                iArr2[i2] = iFirst;
            }
            iFirst = this.bi.next();
        }
        if (this.boundaryCount != 0) {
            length = this.boundaryCount * this.insertion.length();
            i = this.boundaries[this.boundaryCount - 1];
            while (this.boundaryCount > 0) {
                int[] iArr3 = this.boundaries;
                int i3 = this.boundaryCount - 1;
                this.boundaryCount = i3;
                int i4 = iArr3[i3];
                replaceable.replace(i4, i4, this.insertion);
            }
        } else {
            i = 0;
        }
        position.contextLimit += length;
        position.limit += length;
        position.start = z ? i + length : position.limit;
    }

    static void register() {
        Transliterator.registerInstance(new BreakTransliterator("Any-BreakInternal", null), false);
    }

    static final class ReplaceableCharacterIterator implements CharacterIterator {
        private int begin;
        private int end;
        private int pos;
        private Replaceable text;

        public ReplaceableCharacterIterator(Replaceable replaceable, int i, int i2, int i3) {
            if (replaceable == null) {
                throw null;
            }
            this.text = replaceable;
            if (i < 0 || i > i2 || i2 > replaceable.length()) {
                throw new IllegalArgumentException("Invalid substring range");
            }
            if (i3 < i || i3 > i2) {
                throw new IllegalArgumentException("Invalid position");
            }
            this.begin = i;
            this.end = i2;
            this.pos = i3;
        }

        public void setText(Replaceable replaceable) {
            if (replaceable == null) {
                throw null;
            }
            this.text = replaceable;
            this.begin = 0;
            this.end = replaceable.length();
            this.pos = 0;
        }

        @Override // java.text.CharacterIterator
        public char first() {
            this.pos = this.begin;
            return current();
        }

        @Override // java.text.CharacterIterator
        public char last() {
            int i = this.end;
            if (i != this.begin) {
                this.pos = i - 1;
            } else {
                this.pos = i;
            }
            return current();
        }

        @Override // java.text.CharacterIterator
        public char setIndex(int i) {
            if (i < this.begin || i > this.end) {
                throw new IllegalArgumentException("Invalid index");
            }
            this.pos = i;
            return current();
        }

        @Override // java.text.CharacterIterator
        public char current() {
            int i = this.pos;
            if (i < this.begin || i >= this.end) {
                return (char) 65535;
            }
            return this.text.charAt(i);
        }

        @Override // java.text.CharacterIterator
        public char next() {
            int i = this.pos;
            int i2 = this.end;
            if (i < i2 - 1) {
                int i3 = i + 1;
                this.pos = i3;
                return this.text.charAt(i3);
            }
            this.pos = i2;
            return (char) 65535;
        }

        @Override // java.text.CharacterIterator
        public char previous() {
            int i = this.pos;
            if (i <= this.begin) {
                return (char) 65535;
            }
            int i2 = i - 1;
            this.pos = i2;
            return this.text.charAt(i2);
        }

        @Override // java.text.CharacterIterator
        public int getBeginIndex() {
            return this.begin;
        }

        @Override // java.text.CharacterIterator
        public int getEndIndex() {
            return this.end;
        }

        @Override // java.text.CharacterIterator
        public int getIndex() {
            return this.pos;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ReplaceableCharacterIterator)) {
                return false;
            }
            ReplaceableCharacterIterator replaceableCharacterIterator = (ReplaceableCharacterIterator) obj;
            return hashCode() == replaceableCharacterIterator.hashCode() && this.text.equals(replaceableCharacterIterator.text) && this.pos == replaceableCharacterIterator.pos && this.begin == replaceableCharacterIterator.begin && this.end == replaceableCharacterIterator.end;
        }

        public int hashCode() {
            return ((this.text.hashCode() ^ this.pos) ^ this.begin) ^ this.end;
        }

        @Override // java.text.CharacterIterator
        public Object clone() {
            try {
                return (ReplaceableCharacterIterator) super.clone();
            } catch (CloneNotSupportedException unused) {
                throw new ICUCloneNotSupportedException();
            }
        }
    }

    @Override // com.ibm.icu.text.Transliterator
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        if (getFilterAsUnicodeSet(unicodeSet).size() != 0) {
            unicodeSet3.addAll(this.insertion);
        }
    }
}
