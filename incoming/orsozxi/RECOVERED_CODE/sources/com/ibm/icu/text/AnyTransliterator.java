package com.ibm.icu.text;

import com.ibm.icu.lang.UScript;
import com.ibm.icu.text.Transliterator;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
class AnyTransliterator extends Transliterator {
    static final String ANY = "Any";
    static final String LATIN_PIVOT = "-Latin;Latin-";
    static final String NULL_ID = "Null";
    static final char TARGET_SEP = '-';
    static final char VARIANT_SEP = '/';
    private ConcurrentHashMap<Integer, Transliterator> cache;
    private String target;
    private int targetScript;
    private Transliterator widthFix;

    private boolean isWide(int i) {
        return i == 5 || i == 17 || i == 18 || i == 20 || i == 22;
    }

    @Override // com.ibm.icu.text.Transliterator
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean z) {
        int i = position.start;
        int i2 = position.limit;
        ScriptRunIterator scriptRunIterator = new ScriptRunIterator(replaceable, position.contextStart, position.contextLimit);
        while (scriptRunIterator.next()) {
            if (scriptRunIterator.limit > i) {
                Transliterator transliterator = getTransliterator(scriptRunIterator.scriptCode);
                if (transliterator == null) {
                    position.start = scriptRunIterator.limit;
                } else {
                    boolean z2 = z && scriptRunIterator.limit >= i2;
                    position.start = Math.max(i, scriptRunIterator.start);
                    position.limit = Math.min(i2, scriptRunIterator.limit);
                    int i3 = position.limit;
                    transliterator.filteredTransliterate(replaceable, position, z2);
                    int i4 = position.limit - i3;
                    i2 += i4;
                    scriptRunIterator.adjustLimit(i4);
                    if (scriptRunIterator.limit >= i2) {
                        break;
                    }
                }
            }
        }
        position.limit = i2;
    }

    private AnyTransliterator(String str, String str2, String str3, int i) {
        super(str, null);
        this.widthFix = Transliterator.getInstance("[[:dt=Nar:][:dt=Wide:]] nfkd");
        this.targetScript = i;
        this.cache = new ConcurrentHashMap<>();
        this.target = str2;
        if (str3.length() > 0) {
            this.target = str2 + '/' + str3;
        }
    }

    public AnyTransliterator(String str, UnicodeFilter unicodeFilter, String str2, int i, Transliterator transliterator, ConcurrentHashMap<Integer, Transliterator> concurrentHashMap) {
        super(str, unicodeFilter);
        this.widthFix = Transliterator.getInstance("[[:dt=Nar:][:dt=Wide:]] nfkd");
        this.targetScript = i;
        this.cache = concurrentHashMap;
        this.target = str2;
    }

    private Transliterator getTransliterator(int i) {
        if (i == this.targetScript || i == -1) {
            if (isWide(this.targetScript)) {
                return null;
            }
            return this.widthFix;
        }
        Integer numValueOf = Integer.valueOf(i);
        Transliterator compoundTransliterator = this.cache.get(numValueOf);
        if (compoundTransliterator != null) {
            return compoundTransliterator;
        }
        String name = UScript.getName(i);
        try {
            compoundTransliterator = Transliterator.getInstance(name + '-' + this.target, 0);
        } catch (RuntimeException unused) {
        }
        if (compoundTransliterator == null) {
            try {
                compoundTransliterator = Transliterator.getInstance(name + LATIN_PIVOT + this.target, 0);
            } catch (RuntimeException unused2) {
            }
        }
        if (compoundTransliterator == null) {
            return !isWide(this.targetScript) ? this.widthFix : compoundTransliterator;
        }
        if (!isWide(this.targetScript)) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(this.widthFix);
            arrayList.add(compoundTransliterator);
            compoundTransliterator = new CompoundTransliterator(arrayList);
        }
        Transliterator transliteratorPutIfAbsent = this.cache.putIfAbsent(numValueOf, compoundTransliterator);
        return transliteratorPutIfAbsent != null ? transliteratorPutIfAbsent : compoundTransliterator;
    }

    static void register() {
        HashMap map = new HashMap();
        Enumeration<String> availableSources = Transliterator.getAvailableSources();
        while (availableSources.hasMoreElements()) {
            String strNextElement = availableSources.nextElement();
            if (!strNextElement.equalsIgnoreCase(ANY)) {
                Enumeration<String> availableTargets = Transliterator.getAvailableTargets(strNextElement);
                while (availableTargets.hasMoreElements()) {
                    String strNextElement2 = availableTargets.nextElement();
                    int iScriptNameToCode = scriptNameToCode(strNextElement2);
                    if (iScriptNameToCode != -1) {
                        Set hashSet = (Set) map.get(strNextElement2);
                        if (hashSet == null) {
                            hashSet = new HashSet();
                            map.put(strNextElement2, hashSet);
                        }
                        Enumeration<String> availableVariants = Transliterator.getAvailableVariants(strNextElement, strNextElement2);
                        while (availableVariants.hasMoreElements()) {
                            String strNextElement3 = availableVariants.nextElement();
                            if (!hashSet.contains(strNextElement3)) {
                                hashSet.add(strNextElement3);
                                Transliterator.registerInstance(new AnyTransliterator(TransliteratorIDParser.STVtoID(ANY, strNextElement2, strNextElement3), strNextElement2, strNextElement3, iScriptNameToCode));
                                Transliterator.registerSpecialInverse(strNextElement2, NULL_ID, false);
                            }
                        }
                    }
                }
            }
        }
    }

    private static int scriptNameToCode(String str) {
        try {
            int[] code = UScript.getCode(str);
            if (code != null) {
                return code[0];
            }
            return -1;
        } catch (MissingResourceException unused) {
            return -1;
        }
    }

    private static class ScriptRunIterator {
        public int limit;
        public int scriptCode;
        public int start;
        private Replaceable text;
        private int textLimit;
        private int textStart;

        public ScriptRunIterator(Replaceable replaceable, int i, int i2) {
            this.text = replaceable;
            this.textStart = i;
            this.textLimit = i2;
            this.limit = i;
        }

        public boolean next() {
            int script;
            this.scriptCode = -1;
            int i = this.limit;
            this.start = i;
            if (i == this.textLimit) {
                return false;
            }
            while (true) {
                int i2 = this.start;
                if (i2 <= this.textStart || !((script = UScript.getScript(this.text.char32At(i2 - 1))) == 0 || script == 1)) {
                    break;
                }
                this.start--;
            }
            while (true) {
                int i3 = this.limit;
                if (i3 >= this.textLimit) {
                    break;
                }
                int script2 = UScript.getScript(this.text.char32At(i3));
                if (script2 != 0 && script2 != 1) {
                    int i4 = this.scriptCode;
                    if (i4 == -1) {
                        this.scriptCode = script2;
                    } else if (script2 != i4) {
                        break;
                    }
                }
                this.limit++;
            }
            return true;
        }

        public void adjustLimit(int i) {
            this.limit += i;
            this.textLimit += i;
        }
    }

    public Transliterator safeClone() {
        UnicodeFilter filter = getFilter();
        return new AnyTransliterator(getID(), (filter == null || !(filter instanceof UnicodeSet)) ? filter : new UnicodeSet((UnicodeSet) filter), this.target, this.targetScript, this.widthFix, this.cache);
    }

    @Override // com.ibm.icu.text.Transliterator
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        UnicodeSet filterAsUnicodeSet = getFilterAsUnicodeSet(unicodeSet);
        unicodeSet2.addAll(filterAsUnicodeSet);
        if (filterAsUnicodeSet.size() != 0) {
            unicodeSet3.addAll(0, 1114111);
        }
    }
}
