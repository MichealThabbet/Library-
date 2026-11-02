package com.ibm.icu.text;

import com.ibm.icu.impl.UCaseProps;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.util.ULocale;

/* loaded from: classes2.dex */
class LowercaseTransliterator extends Transliterator {
    static final String _ID = "Any-Lower";
    private int caseLocale;
    private final UCaseProps csp;
    private ReplaceableContextIterator iter;
    private final ULocale locale;
    private StringBuilder result;
    SourceTargetUtility sourceTargetUtility;

    static void register() {
        Transliterator.registerFactory(_ID, new Transliterator.Factory() { // from class: com.ibm.icu.text.LowercaseTransliterator.1
            @Override // com.ibm.icu.text.Transliterator.Factory
            public Transliterator getInstance(String str) {
                return new LowercaseTransliterator(ULocale.US);
            }
        });
        Transliterator.registerSpecialInverse("Lower", "Upper", true);
    }

    public LowercaseTransliterator(ULocale uLocale) {
        super(_ID, null);
        this.sourceTargetUtility = null;
        this.locale = uLocale;
        this.csp = UCaseProps.INSTANCE;
        this.iter = new ReplaceableContextIterator();
        this.result = new StringBuilder();
        this.caseLocale = UCaseProps.getCaseLocale(this.locale);
    }

    @Override // com.ibm.icu.text.Transliterator
    protected synchronized void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean z) {
        int iReplace;
        if (this.csp == null) {
            return;
        }
        if (position.start >= position.limit) {
            return;
        }
        this.iter.setText(replaceable);
        this.result.setLength(0);
        this.iter.setIndex(position.start);
        this.iter.setLimit(position.limit);
        this.iter.setContextLimits(position.contextStart, position.contextLimit);
        while (true) {
            int iNextCaseMapCP = this.iter.nextCaseMapCP();
            if (iNextCaseMapCP >= 0) {
                int fullLower = this.csp.toFullLower(iNextCaseMapCP, this.iter, this.result, this.caseLocale);
                if (this.iter.didReachLimit() && z) {
                    position.start = this.iter.getCaseMapCPStart();
                    return;
                }
                if (fullLower >= 0) {
                    if (fullLower <= 31) {
                        iReplace = this.iter.replace(this.result.toString());
                        this.result.setLength(0);
                    } else {
                        iReplace = this.iter.replace(UTF16.valueOf(fullLower));
                    }
                    if (iReplace != 0) {
                        position.limit += iReplace;
                        position.contextLimit += iReplace;
                    }
                }
            } else {
                position.start = position.limit;
                return;
            }
        }
    }

    @Override // com.ibm.icu.text.Transliterator
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        synchronized (this) {
            if (this.sourceTargetUtility == null) {
                this.sourceTargetUtility = new SourceTargetUtility(new Transform<String, String>() { // from class: com.ibm.icu.text.LowercaseTransliterator.2
                    @Override // com.ibm.icu.text.Transform
                    public String transform(String str) {
                        return UCharacter.toLowerCase(LowercaseTransliterator.this.locale, str);
                    }
                });
            }
        }
        this.sourceTargetUtility.addSourceTargetSet(this, unicodeSet, unicodeSet2, unicodeSet3);
    }
}
