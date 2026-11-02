package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import org.apache.http.message.TokenParser;

/* loaded from: classes2.dex */
public final class CollationLoader {
    private static volatile String rootRules;

    private CollationLoader() {
    }

    private static void loadRootRules() {
        if (rootRules != null) {
            return;
        }
        synchronized (CollationLoader.class) {
            if (rootRules == null) {
                rootRules = UResourceBundle.getBundleInstance(ICUData.ICU_COLLATION_BASE_NAME, ULocale.ROOT).getString("UCARules");
            }
        }
    }

    public static String getRootRules() {
        loadRootRules();
        return rootRules;
    }

    private static final class ASCII {
        private ASCII() {
        }

        static String toLowerCase(String str) {
            int i = 0;
            while (i < str.length()) {
                char cCharAt = str.charAt(i);
                if ('A' > cCharAt || cCharAt > 'Z') {
                    i++;
                } else {
                    StringBuilder sb = new StringBuilder(str.length());
                    sb.append((CharSequence) str, 0, i);
                    sb.append((char) (cCharAt + TokenParser.SP));
                    while (true) {
                        i++;
                        if (i < str.length()) {
                            char cCharAt2 = str.charAt(i);
                            if ('A' <= cCharAt2 && cCharAt2 <= 'Z') {
                                cCharAt2 = (char) (cCharAt2 + TokenParser.SP);
                            }
                            sb.append(cCharAt2);
                        } else {
                            return sb.toString();
                        }
                    }
                }
            }
            return str;
        }
    }

    static String loadRules(ULocale uLocale, String str) {
        return ((ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_COLLATION_BASE_NAME, uLocale)).getWithFallback("collations/" + ASCII.toLowerCase(str)).getString("Sequence");
    }

    private static final UResourceBundle findWithFallback(UResourceBundle uResourceBundle, String str) {
        return ((ICUResourceBundle) uResourceBundle).findWithFallback(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00b1  */
    /* JADX WARN: Type inference failed for: r12v1, types: [T, com.ibm.icu.util.ULocale] */
    /* JADX WARN: Type inference failed for: r12v2, types: [T, com.ibm.icu.util.ULocale] */
    /* JADX WARN: Type inference failed for: r1v3, types: [T, com.ibm.icu.util.ULocale] */
    /* JADX WARN: Type inference failed for: r4v2, types: [T, com.ibm.icu.util.ULocale, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static com.ibm.icu.impl.coll.CollationTailoring loadTailoring(com.ibm.icu.util.ULocale r12, com.ibm.icu.util.Output<com.ibm.icu.util.ULocale> r13) {
        /*
            Method dump skipped, instructions count: 310
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.coll.CollationLoader.loadTailoring(com.ibm.icu.util.ULocale, com.ibm.icu.util.Output):com.ibm.icu.impl.coll.CollationTailoring");
    }
}
