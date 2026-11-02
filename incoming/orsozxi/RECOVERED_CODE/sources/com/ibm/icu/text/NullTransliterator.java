package com.ibm.icu.text;

import com.ibm.icu.text.Transliterator;

/* loaded from: classes2.dex */
class NullTransliterator extends Transliterator {
    static final String SHORT_ID = "Null";
    static final String _ID = "Any-Null";

    @Override // com.ibm.icu.text.Transliterator
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
    }

    public NullTransliterator() {
        super(_ID, null);
    }

    @Override // com.ibm.icu.text.Transliterator
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean z) {
        position.start = position.limit;
    }
}
