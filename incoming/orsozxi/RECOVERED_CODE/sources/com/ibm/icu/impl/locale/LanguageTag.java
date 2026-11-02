package com.ibm.icu.impl.locale;

import com.ibm.icu.impl.locale.AsciiUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class LanguageTag {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean JDKIMPL = false;
    private static final Map<AsciiUtil.CaseInsensitiveKey, String[]> LEGACY = new HashMap();
    public static final String PRIVATEUSE = "x";
    public static final String PRIVUSE_VARIANT_PREFIX = "lvariant";
    public static final String SEP = "-";
    public static String UNDETERMINED = "und";
    private String _language = "";
    private String _script = "";
    private String _region = "";
    private String _privateuse = "";
    private List<String> _extlangs = Collections.emptyList();
    private List<String> _variants = Collections.emptyList();
    private List<String> _extensions = Collections.emptyList();

    static {
        String[][] strArr = {new String[]{"art-lojban", "jbo"}, new String[]{"cel-gaulish", "xtg"}, new String[]{"en-GB-oed", "en-GB-x-oed"}, new String[]{"i-ami", "ami"}, new String[]{"i-bnn", "bnn"}, new String[]{"i-default", "en-x-i-default"}, new String[]{"i-enochian", "und-x-i-enochian"}, new String[]{"i-hak", "hak"}, new String[]{"i-klingon", "tlh"}, new String[]{"i-lux", "lb"}, new String[]{"i-mingo", "see-x-i-mingo"}, new String[]{"i-navajo", "nv"}, new String[]{"i-pwn", "pwn"}, new String[]{"i-tao", "tao"}, new String[]{"i-tay", "tay"}, new String[]{"i-tsu", "tsu"}, new String[]{"no-bok", "nb"}, new String[]{"no-nyn", "nn"}, new String[]{"sgn-BE-FR", "sfb"}, new String[]{"sgn-BE-NL", "vgt"}, new String[]{"sgn-CH-DE", "sgg"}, new String[]{"zh-guoyu", "cmn"}, new String[]{"zh-hakka", "hak"}, new String[]{"zh-min", "nan-x-zh-min"}, new String[]{"zh-min-nan", "nan"}, new String[]{"zh-xiang", "hsn"}};
        for (int i = 0; i < 26; i++) {
            String[] strArr2 = strArr[i];
            LEGACY.put(new AsciiUtil.CaseInsensitiveKey(strArr2[0]), strArr2);
        }
    }

    private LanguageTag() {
    }

    public static LanguageTag parse(String str, ParseStatus parseStatus) {
        boolean z;
        StringTokenIterator stringTokenIterator;
        if (parseStatus == null) {
            parseStatus = new ParseStatus();
        } else {
            parseStatus.reset();
        }
        String[] strArr = LEGACY.get(new AsciiUtil.CaseInsensitiveKey(str));
        int iIndexOf = 2;
        while (true) {
            z = false;
            if (strArr != null || (iIndexOf = str.indexOf(45, iIndexOf + 1)) == -1) {
                break;
            }
            strArr = LEGACY.get(new AsciiUtil.CaseInsensitiveKey(str.substring(0, iIndexOf)));
        }
        if (strArr != null) {
            if (strArr[0].length() == str.length()) {
                stringTokenIterator = new StringTokenIterator(strArr[1], SEP);
            } else {
                stringTokenIterator = new StringTokenIterator(strArr[1] + str.substring(iIndexOf), SEP);
            }
            z = true;
        } else {
            stringTokenIterator = new StringTokenIterator(str, SEP);
        }
        LanguageTag languageTag = new LanguageTag();
        if (languageTag.parseLanguage(stringTokenIterator, parseStatus)) {
            if (languageTag._language.length() <= 3) {
                languageTag.parseExtlangs(stringTokenIterator, parseStatus);
            }
            languageTag.parseScript(stringTokenIterator, parseStatus);
            languageTag.parseRegion(stringTokenIterator, parseStatus);
            languageTag.parseVariants(stringTokenIterator, parseStatus);
            languageTag.parseExtensions(stringTokenIterator, parseStatus);
        }
        languageTag.parsePrivateuse(stringTokenIterator, parseStatus);
        if (z) {
            parseStatus._parseLength = str.length();
        } else if (!stringTokenIterator.isDone() && !parseStatus.isError()) {
            String strCurrent = stringTokenIterator.current();
            parseStatus._errorIndex = stringTokenIterator.currentStart();
            if (strCurrent.length() == 0) {
                parseStatus._errorMsg = "Empty subtag";
            } else {
                parseStatus._errorMsg = "Invalid subtag: " + strCurrent;
            }
        }
        return languageTag;
    }

    private boolean parseLanguage(StringTokenIterator stringTokenIterator, ParseStatus parseStatus) {
        if (stringTokenIterator.isDone() || parseStatus.isError()) {
            return false;
        }
        String strCurrent = stringTokenIterator.current();
        if (!isLanguage(strCurrent)) {
            return false;
        }
        this._language = strCurrent;
        parseStatus._parseLength = stringTokenIterator.currentEnd();
        stringTokenIterator.next();
        return true;
    }

    private boolean parseExtlangs(StringTokenIterator stringTokenIterator, ParseStatus parseStatus) {
        boolean z = false;
        if (!stringTokenIterator.isDone() && !parseStatus.isError()) {
            while (!stringTokenIterator.isDone()) {
                String strCurrent = stringTokenIterator.current();
                if (!isExtlang(strCurrent)) {
                    break;
                }
                z = true;
                if (this._extlangs.isEmpty()) {
                    this._extlangs = new ArrayList(3);
                }
                this._extlangs.add(strCurrent);
                parseStatus._parseLength = stringTokenIterator.currentEnd();
                stringTokenIterator.next();
                if (this._extlangs.size() == 3) {
                    break;
                }
            }
        }
        return z;
    }

    private boolean parseScript(StringTokenIterator stringTokenIterator, ParseStatus parseStatus) {
        if (stringTokenIterator.isDone() || parseStatus.isError()) {
            return false;
        }
        String strCurrent = stringTokenIterator.current();
        if (!isScript(strCurrent)) {
            return false;
        }
        this._script = strCurrent;
        parseStatus._parseLength = stringTokenIterator.currentEnd();
        stringTokenIterator.next();
        return true;
    }

    private boolean parseRegion(StringTokenIterator stringTokenIterator, ParseStatus parseStatus) {
        if (stringTokenIterator.isDone() || parseStatus.isError()) {
            return false;
        }
        String strCurrent = stringTokenIterator.current();
        if (!isRegion(strCurrent)) {
            return false;
        }
        this._region = strCurrent;
        parseStatus._parseLength = stringTokenIterator.currentEnd();
        stringTokenIterator.next();
        return true;
    }

    private boolean parseVariants(StringTokenIterator stringTokenIterator, ParseStatus parseStatus) {
        boolean z = false;
        if (!stringTokenIterator.isDone() && !parseStatus.isError()) {
            while (!stringTokenIterator.isDone()) {
                String strCurrent = stringTokenIterator.current();
                if (!isVariant(strCurrent)) {
                    break;
                }
                z = true;
                if (this._variants.isEmpty()) {
                    this._variants = new ArrayList(3);
                }
                String upperCase = strCurrent.toUpperCase();
                if (!this._variants.contains(upperCase)) {
                    this._variants.add(upperCase);
                }
                parseStatus._parseLength = stringTokenIterator.currentEnd();
                stringTokenIterator.next();
            }
        }
        return z;
    }

    private boolean parseExtensions(StringTokenIterator stringTokenIterator, ParseStatus parseStatus) {
        if (stringTokenIterator.isDone() || parseStatus.isError()) {
            return false;
        }
        boolean z = false;
        while (true) {
            if (!stringTokenIterator.isDone()) {
                String strCurrent = stringTokenIterator.current();
                if (!isExtensionSingleton(strCurrent)) {
                    break;
                }
                int iCurrentStart = stringTokenIterator.currentStart();
                String lowerCase = strCurrent.toLowerCase();
                StringBuilder sb = new StringBuilder(lowerCase);
                stringTokenIterator.next();
                while (!stringTokenIterator.isDone()) {
                    String strCurrent2 = stringTokenIterator.current();
                    if (!isExtensionSubtag(strCurrent2)) {
                        break;
                    }
                    sb.append(SEP);
                    sb.append(strCurrent2);
                    parseStatus._parseLength = stringTokenIterator.currentEnd();
                    stringTokenIterator.next();
                }
                if (parseStatus._parseLength <= iCurrentStart) {
                    parseStatus._errorIndex = iCurrentStart;
                    parseStatus._errorMsg = "Incomplete extension '" + lowerCase + "'";
                    break;
                }
                if (this._extensions.size() == 0) {
                    this._extensions = new ArrayList(4);
                }
                Iterator<String> it2 = this._extensions.iterator();
                boolean z2 = false;
                while (it2.hasNext()) {
                    z2 |= it2.next().charAt(0) == sb.charAt(0);
                }
                if (!z2) {
                    this._extensions.add(sb.toString());
                }
                z = true;
            } else {
                break;
            }
        }
        return z;
    }

    private boolean parsePrivateuse(StringTokenIterator stringTokenIterator, ParseStatus parseStatus) {
        if (stringTokenIterator.isDone() || parseStatus.isError()) {
            return false;
        }
        String strCurrent = stringTokenIterator.current();
        if (!isPrivateusePrefix(strCurrent)) {
            return false;
        }
        int iCurrentStart = stringTokenIterator.currentStart();
        StringBuilder sb = new StringBuilder(strCurrent);
        stringTokenIterator.next();
        while (!stringTokenIterator.isDone()) {
            String strCurrent2 = stringTokenIterator.current();
            if (!isPrivateuseSubtag(strCurrent2)) {
                break;
            }
            sb.append(SEP);
            sb.append(strCurrent2);
            parseStatus._parseLength = stringTokenIterator.currentEnd();
            stringTokenIterator.next();
        }
        if (parseStatus._parseLength <= iCurrentStart) {
            parseStatus._errorIndex = iCurrentStart;
            parseStatus._errorMsg = "Incomplete privateuse";
            return false;
        }
        this._privateuse = sb.toString();
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x00e1 A[PHI: r1
      0x00e1: PHI (r1v5 boolean) = (r1v4 boolean), (r1v9 boolean), (r1v9 boolean) binds: [B:28:0x0076, B:41:0x00aa, B:53:0x00da] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static com.ibm.icu.impl.locale.LanguageTag parseLocale(com.ibm.icu.impl.locale.BaseLocale r11, com.ibm.icu.impl.locale.LocaleExtensions r12) {
        /*
            Method dump skipped, instructions count: 382
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.locale.LanguageTag.parseLocale(com.ibm.icu.impl.locale.BaseLocale, com.ibm.icu.impl.locale.LocaleExtensions):com.ibm.icu.impl.locale.LanguageTag");
    }

    public String getLanguage() {
        return this._language;
    }

    public List<String> getExtlangs() {
        return Collections.unmodifiableList(this._extlangs);
    }

    public String getScript() {
        return this._script;
    }

    public String getRegion() {
        return this._region;
    }

    public List<String> getVariants() {
        return Collections.unmodifiableList(this._variants);
    }

    public List<String> getExtensions() {
        return Collections.unmodifiableList(this._extensions);
    }

    public String getPrivateuse() {
        return this._privateuse;
    }

    public static boolean isLanguage(String str) {
        return str.length() >= 2 && str.length() <= 8 && AsciiUtil.isAlphaString(str);
    }

    public static boolean isExtlang(String str) {
        return str.length() == 3 && AsciiUtil.isAlphaString(str);
    }

    public static boolean isScript(String str) {
        return str.length() == 4 && AsciiUtil.isAlphaString(str);
    }

    public static boolean isRegion(String str) {
        return (str.length() == 2 && AsciiUtil.isAlphaString(str)) || (str.length() == 3 && AsciiUtil.isNumericString(str));
    }

    public static boolean isVariant(String str) {
        int length = str.length();
        if (length < 5 || length > 8) {
            return length == 4 && AsciiUtil.isNumeric(str.charAt(0)) && AsciiUtil.isAlphaNumeric(str.charAt(1)) && AsciiUtil.isAlphaNumeric(str.charAt(2)) && AsciiUtil.isAlphaNumeric(str.charAt(3));
        }
        return AsciiUtil.isAlphaNumericString(str);
    }

    public static boolean isTKey(String str) {
        return str.length() == 2 && AsciiUtil.isAlpha(str.charAt(0)) && AsciiUtil.isNumeric(str.charAt(1));
    }

    public static boolean isExtensionSingleton(String str) {
        return str.length() == 1 && AsciiUtil.isAlphaNumericString(str) && !AsciiUtil.caseIgnoreMatch(PRIVATEUSE, str);
    }

    public static boolean isExtensionSingletonChar(char c) {
        return isExtensionSingleton(String.valueOf(c));
    }

    public static boolean isExtensionSubtag(String str) {
        return str.length() >= 2 && str.length() <= 8 && AsciiUtil.isAlphaNumericString(str);
    }

    public static boolean isPrivateusePrefix(String str) {
        return str.length() == 1 && AsciiUtil.caseIgnoreMatch(PRIVATEUSE, str);
    }

    public static boolean isPrivateusePrefixChar(char c) {
        return AsciiUtil.caseIgnoreMatch(PRIVATEUSE, String.valueOf(c));
    }

    public static boolean isPrivateuseSubtag(String str) {
        return str.length() >= 1 && str.length() <= 8 && AsciiUtil.isAlphaNumericString(str);
    }

    public static String canonicalizeLanguage(String str) {
        return AsciiUtil.toLowerString(str);
    }

    public static String canonicalizeExtlang(String str) {
        return AsciiUtil.toLowerString(str);
    }

    public static String canonicalizeScript(String str) {
        return AsciiUtil.toTitleString(str);
    }

    public static String canonicalizeRegion(String str) {
        return AsciiUtil.toUpperString(str);
    }

    public static String canonicalizeVariant(String str) {
        return AsciiUtil.toLowerString(str);
    }

    public static String canonicalizeExtension(String str) {
        String lowerString = AsciiUtil.toLowerString(str);
        if (lowerString.startsWith("u-")) {
            while (lowerString.endsWith("-true")) {
                lowerString = lowerString.substring(0, lowerString.length() - 5);
            }
            while (true) {
                int iIndexOf = lowerString.indexOf("-true-");
                if (iIndexOf <= 0) {
                    break;
                }
                lowerString = lowerString.substring(0, iIndexOf) + lowerString.substring(iIndexOf + 5);
            }
            while (lowerString.endsWith("-yes")) {
                lowerString = lowerString.substring(0, lowerString.length() - 4);
            }
            while (true) {
                int iIndexOf2 = lowerString.indexOf("-yes-");
                if (iIndexOf2 <= 0) {
                    break;
                }
                lowerString = lowerString.substring(0, iIndexOf2) + lowerString.substring(iIndexOf2 + 4);
            }
        }
        return lowerString;
    }

    public static String canonicalizeExtensionSingleton(String str) {
        return AsciiUtil.toLowerString(str);
    }

    public static String canonicalizeExtensionSubtag(String str) {
        return AsciiUtil.toLowerString(str);
    }

    public static String canonicalizePrivateuse(String str) {
        return AsciiUtil.toLowerString(str);
    }

    public static String canonicalizePrivateuseSubtag(String str) {
        return AsciiUtil.toLowerString(str);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this._language.length() > 0) {
            sb.append(this._language);
            for (String str : this._extlangs) {
                sb.append(SEP);
                sb.append(str);
            }
            if (this._script.length() > 0) {
                sb.append(SEP);
                sb.append(this._script);
            }
            if (this._region.length() > 0) {
                sb.append(SEP);
                sb.append(this._region);
            }
            for (String str2 : this._variants) {
                sb.append(SEP);
                sb.append(str2);
            }
            for (String str3 : this._extensions) {
                sb.append(SEP);
                sb.append(str3);
            }
        }
        if (this._privateuse.length() > 0) {
            if (sb.length() > 0) {
                sb.append(SEP);
            }
            sb.append(this._privateuse);
        }
        return sb.toString();
    }
}
