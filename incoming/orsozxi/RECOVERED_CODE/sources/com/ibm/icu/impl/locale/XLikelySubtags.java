package com.ibm.icu.impl.locale;

import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.util.BytesTrie;
import com.ibm.icu.util.ULocale;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.TreeMap;
import org.apache.commons.codec.language.Soundex;

/* loaded from: classes2.dex */
public final class XLikelySubtags {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean DEBUG_OUTPUT = false;
    public static final XLikelySubtags INSTANCE = new XLikelySubtags(Data.load());
    private static final String PSEUDO_ACCENTS_PREFIX = "'";
    private static final String PSEUDO_BIDI_PREFIX = "+";
    private static final String PSEUDO_CRACKED_PREFIX = ",";
    public static final int SKIP_SCRIPT = 1;
    private final int defaultLsrIndex;
    private final Map<String, String> languageAliases;
    private final LSR[] lsrs;
    private final Map<String, String> regionAliases;
    private final BytesTrie trie;
    private final long[] trieFirstLetterStates = new long[26];
    private final long trieUndState;
    private final long trieUndZzzzState;

    public static final class Data {
        public final Map<String, String> languageAliases;
        public final LSR[] lsrs;
        public final Map<String, String> regionAliases;
        public final byte[] trie;

        public int hashCode() {
            return 1;
        }

        public Data(Map<String, String> map, Map<String, String> map2, byte[] bArr, LSR[] lsrArr) {
            this.languageAliases = map;
            this.regionAliases = map2;
            this.trie = bArr;
            this.lsrs = lsrArr;
        }

        private static UResource.Value getValue(UResource.Table table, String str, UResource.Value value) {
            if (table.findValue(str, value)) {
                return value;
            }
            throw new MissingResourceException("langInfo.res missing data", "", "likely/" + str);
        }

        public static Data load() throws MissingResourceException {
            Map mapEmptyMap;
            Map mapEmptyMap2;
            UResource.Value valueWithFallback = ICUResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "langInfo", ICUResourceBundle.ICU_DATA_CLASS_LOADER, ICUResourceBundle.OpenType.DIRECT).getValueWithFallback("likely");
            UResource.Table table = valueWithFallback.getTable();
            if (table.findValue("languageAliases", valueWithFallback)) {
                String[] stringArray = valueWithFallback.getStringArray();
                mapEmptyMap = new HashMap(stringArray.length / 2);
                for (int i = 0; i < stringArray.length; i += 2) {
                    mapEmptyMap.put(stringArray[i], stringArray[i + 1]);
                }
            } else {
                mapEmptyMap = Collections.emptyMap();
            }
            if (table.findValue("regionAliases", valueWithFallback)) {
                String[] stringArray2 = valueWithFallback.getStringArray();
                mapEmptyMap2 = new HashMap(stringArray2.length / 2);
                for (int i2 = 0; i2 < stringArray2.length; i2 += 2) {
                    mapEmptyMap2.put(stringArray2[i2], stringArray2[i2 + 1]);
                }
            } else {
                mapEmptyMap2 = Collections.emptyMap();
            }
            ByteBuffer binary = getValue(table, "trie", valueWithFallback).getBinary();
            byte[] bArr = new byte[binary.remaining()];
            binary.get(bArr);
            String[] stringArray3 = getValue(table, "lsrs", valueWithFallback).getStringArray();
            LSR[] lsrArr = new LSR[stringArray3.length / 3];
            int i3 = 0;
            int i4 = 0;
            while (i3 < stringArray3.length) {
                lsrArr[i4] = new LSR(stringArray3[i3], stringArray3[i3 + 1], stringArray3[i3 + 2], 0);
                i3 += 3;
                i4++;
            }
            return new Data(mapEmptyMap, mapEmptyMap2, bArr, lsrArr);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !getClass().equals(obj.getClass())) {
                return false;
            }
            Data data = (Data) obj;
            return this.languageAliases.equals(data.languageAliases) && this.regionAliases.equals(data.regionAliases) && Arrays.equals(this.trie, data.trie) && Arrays.equals(this.lsrs, data.lsrs);
        }
    }

    private XLikelySubtags(Data data) {
        this.languageAliases = data.languageAliases;
        this.regionAliases = data.regionAliases;
        this.trie = new BytesTrie(data.trie, 0);
        this.lsrs = data.lsrs;
        this.trie.next(42);
        this.trieUndState = this.trie.getState64();
        this.trie.next(42);
        this.trieUndZzzzState = this.trie.getState64();
        this.trie.next(42);
        this.defaultLsrIndex = this.trie.getValue();
        this.trie.reset();
        for (char c = 'a'; c <= 'z'; c = (char) (c + 1)) {
            if (this.trie.next(c) == BytesTrie.Result.NO_VALUE) {
                this.trieFirstLetterStates[c - 'a'] = this.trie.getState64();
            }
            this.trie.reset();
        }
    }

    public ULocale canonicalize(ULocale uLocale) {
        String language = uLocale.getLanguage();
        String str = this.languageAliases.get(language);
        String country = uLocale.getCountry();
        String str2 = this.regionAliases.get(country);
        if (str == null && str2 == null) {
            return uLocale;
        }
        if (str != null) {
            language = str;
        }
        String script = uLocale.getScript();
        if (str2 != null) {
            country = str2;
        }
        return new ULocale(language, script, country);
    }

    private static String getCanonical(Map<String, String> map, String str) {
        String str2 = map.get(str);
        return str2 == null ? str : str2;
    }

    public LSR makeMaximizedLsrFrom(ULocale uLocale) {
        if (uLocale.getName().startsWith("@x=")) {
            return new LSR(uLocale.toLanguageTag(), "", "", 7);
        }
        return makeMaximizedLsr(uLocale.getLanguage(), uLocale.getScript(), uLocale.getCountry(), uLocale.getVariant());
    }

    public LSR makeMaximizedLsrFrom(Locale locale) {
        String languageTag = locale.toLanguageTag();
        if (languageTag.startsWith("x-") || languageTag.startsWith("und-x-")) {
            return new LSR(languageTag, "", "", 7);
        }
        return makeMaximizedLsr(locale.getLanguage(), locale.getScript(), locale.getCountry(), locale.getVariant());
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x012e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private com.ibm.icu.impl.locale.LSR makeMaximizedLsr(java.lang.String r11, java.lang.String r12, java.lang.String r13, java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 374
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.locale.XLikelySubtags.makeMaximizedLsr(java.lang.String, java.lang.String, java.lang.String, java.lang.String):com.ibm.icu.impl.locale.LSR");
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00c7 A[PHI: r7
      0x00c7: PHI (r7v12 int) = (r7v7 int), (r7v6 int) binds: [B:60:0x00d4, B:55:0x00c5] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private com.ibm.icu.impl.locale.LSR maximize(java.lang.String r16, java.lang.String r17, java.lang.String r18) {
        /*
            Method dump skipped, instructions count: 272
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.locale.XLikelySubtags.maximize(java.lang.String, java.lang.String, java.lang.String):com.ibm.icu.impl.locale.LSR");
    }

    int compareLikely(LSR lsr, LSR lsr2, int i) {
        int likelyIndex;
        int likelyIndex2;
        if (!lsr.language.equals(lsr2.language)) {
            return -4;
        }
        if (!lsr.script.equals(lsr2.script)) {
            if (i < 0 || (i & 2) != 0) {
                likelyIndex2 = getLikelyIndex(lsr.language, "");
                i = likelyIndex2 << 2;
            } else {
                likelyIndex2 = i >> 2;
            }
            return lsr.script.equals(this.lsrs[likelyIndex2].script) ? i | 1 : i & (-2);
        }
        if (lsr.region.equals(lsr2.region)) {
            return i & (-2);
        }
        if (i < 0 || (i & 2) == 0) {
            likelyIndex = getLikelyIndex(lsr.language, lsr.region);
            i = (likelyIndex << 2) | 2;
        } else {
            likelyIndex = i >> 2;
        }
        return lsr.region.equals(this.lsrs[likelyIndex].region) ? i | 1 : i & (-2);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int getLikelyIndex(java.lang.String r10, java.lang.String r11) {
        /*
            r9 = this;
            java.lang.String r0 = "und"
            boolean r0 = r10.equals(r0)
            java.lang.String r1 = ""
            if (r0 == 0) goto Lc
            r10 = r1
        Lc:
            java.lang.String r0 = "Zzzz"
            boolean r0 = r11.equals(r0)
            if (r0 == 0) goto L15
            r11 = r1
        L15:
            com.ibm.icu.util.BytesTrie r0 = new com.ibm.icu.util.BytesTrie
            com.ibm.icu.util.BytesTrie r2 = r9.trie
            r0.<init>(r2)
            int r2 = r10.length()
            r3 = 2
            r4 = 1
            r5 = 0
            r7 = 0
            if (r2 < r3) goto L44
            char r2 = r10.charAt(r7)
            int r2 = r2 + (-97)
            if (r2 < 0) goto L44
            r3 = 25
            if (r2 > r3) goto L44
            long[] r3 = r9.trieFirstLetterStates
            r2 = r3[r2]
            int r8 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r8 == 0) goto L44
            com.ibm.icu.util.BytesTrie r2 = r0.resetToState64(r2)
            int r10 = trieNext(r2, r10, r4)
            goto L48
        L44:
            int r10 = trieNext(r0, r10, r7)
        L48:
            if (r10 < 0) goto L4f
            long r2 = r0.getState64()
            goto L55
        L4f:
            long r2 = r9.trieUndState
            r0.resetToState64(r2)
            r2 = r5
        L55:
            if (r10 <= 0) goto L5b
            if (r10 != r4) goto L79
            r10 = 0
            goto L79
        L5b:
            int r10 = trieNext(r0, r11, r7)
            if (r10 < 0) goto L65
            r0.getState64()
            goto L79
        L65:
            int r11 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r11 != 0) goto L6f
            long r2 = r9.trieUndZzzzState
            r0.resetToState64(r2)
            goto L79
        L6f:
            r0.resetToState64(r2)
            int r10 = trieNext(r0, r1, r7)
            r0.getState64()
        L79:
            if (r10 <= 0) goto L7c
            goto L80
        L7c:
            int r10 = trieNext(r0, r1, r7)
        L80:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.locale.XLikelySubtags.getLikelyIndex(java.lang.String, java.lang.String):int");
    }

    private static final int trieNext(BytesTrie bytesTrie, String str, int i) {
        BytesTrie.Result next;
        if (str.isEmpty()) {
            next = bytesTrie.next(42);
        } else {
            int length = str.length() - 1;
            while (true) {
                char cCharAt = str.charAt(i);
                if (i < length) {
                    if (!bytesTrie.next(cCharAt).hasNext()) {
                        return -1;
                    }
                    i++;
                } else {
                    next = bytesTrie.next(cCharAt | 128);
                    break;
                }
            }
        }
        int i2 = AnonymousClass1.$SwitchMap$com$ibm$icu$util$BytesTrie$Result[next.ordinal()];
        if (i2 == 2) {
            return 0;
        }
        if (i2 == 3) {
            return 1;
        }
        if (i2 != 4) {
            return -1;
        }
        return bytesTrie.getValue();
    }

    /* renamed from: com.ibm.icu.impl.locale.XLikelySubtags$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$util$BytesTrie$Result;

        static {
            int[] iArr = new int[BytesTrie.Result.values().length];
            $SwitchMap$com$ibm$icu$util$BytesTrie$Result = iArr;
            try {
                iArr[BytesTrie.Result.NO_MATCH.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$util$BytesTrie$Result[BytesTrie.Result.NO_VALUE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ibm$icu$util$BytesTrie$Result[BytesTrie.Result.INTERMEDIATE_VALUE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$ibm$icu$util$BytesTrie$Result[BytesTrie.Result.FINAL_VALUE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    LSR minimizeSubtags(String str, String str2, String str3, ULocale.Minimize minimize) {
        boolean z;
        LSR lsrMaximize = maximize(str, str2, str3);
        BytesTrie bytesTrie = new BytesTrie(this.trie);
        int iTrieNext = trieNext(bytesTrie, lsrMaximize.language, 0);
        if (iTrieNext == 0 && (iTrieNext = trieNext(bytesTrie, "", 0)) == 0) {
            iTrieNext = trieNext(bytesTrie, "", 0);
        }
        LSR lsr = this.lsrs[iTrieNext];
        if (!lsrMaximize.script.equals(lsr.script)) {
            z = false;
        } else {
            if (lsrMaximize.region.equals(lsr.region)) {
                return new LSR(lsrMaximize.language, "", "", 0);
            }
            if (minimize == ULocale.Minimize.FAVOR_REGION) {
                return new LSR(lsrMaximize.language, "", lsrMaximize.region, 0);
            }
            z = true;
        }
        if (maximize(str, str2, "").equals(lsrMaximize)) {
            return new LSR(lsrMaximize.language, lsrMaximize.script, "", 0);
        }
        return z ? new LSR(lsrMaximize.language, "", lsrMaximize.region, 0) : lsrMaximize;
    }

    private Map<String, LSR> getTable() {
        TreeMap treeMap = new TreeMap();
        StringBuilder sb = new StringBuilder();
        Iterator<BytesTrie.Entry> itIterator2 = this.trie.iterator2();
        while (itIterator2.hasNext()) {
            BytesTrie.Entry next = itIterator2.next();
            int i = 0;
            sb.setLength(0);
            int iBytesLength = next.bytesLength();
            while (i < iBytesLength) {
                int i2 = i + 1;
                byte bByteAt = next.byteAt(i);
                if (bByteAt == 42) {
                    sb.append("*-");
                } else if (bByteAt >= 0) {
                    sb.append((char) bByteAt);
                } else {
                    sb.append((char) (bByteAt & 127));
                    sb.append(Soundex.SILENT_MARKER);
                }
                i = i2;
            }
            sb.setLength(sb.length() - 1);
            treeMap.put(sb.toString(), this.lsrs[next.value]);
        }
        return treeMap;
    }

    public String toString() {
        return getTable().toString();
    }
}
