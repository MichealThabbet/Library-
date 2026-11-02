package com.ibm.icu.text;

import com.ibm.icu.text.Transliterator;

/* loaded from: classes2.dex */
class RemoveTransliterator extends Transliterator {
    private static final String _ID = "Any-Remove";

    static void register() {
        Transliterator.registerFactory(_ID, new Transliterator.Factory() { // from class: com.ibm.icu.text.RemoveTransliterator.1
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String str) {
                return new RemoveTransliterator();
            }
        });
        Transliterator.registerSpecialInverse("Remove", "Null", false);
    }

    public RemoveTransliterator() {
        super(_ID, null);
    }

    @Override // com.ibm.icu.text.Transliterator
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean z) {
        replaceable.replace(position.start, position.limit, "");
        int i = position.limit - position.start;
        position.contextLimit -= i;
        position.limit -= i;
    }

    @Override // com.ibm.icu.text.Transliterator
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        unicodeSet2.addAll(getFilterAsUnicodeSet(unicodeSet));
    }
}
