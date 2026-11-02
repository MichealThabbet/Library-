package com.ibm.icu.util;

import com.huawei.hms.framework.common.ContainerUtils;
import com.ibm.icu.impl.CacheBase;
import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.ICUResourceTableAccess;
import com.ibm.icu.impl.LocaleIDParser;
import com.ibm.icu.impl.LocaleIDs;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.impl.locale.AsciiUtil;
import com.ibm.icu.impl.locale.BaseLocale;
import com.ibm.icu.impl.locale.Extension;
import com.ibm.icu.impl.locale.InternalLocaleBuilder;
import com.ibm.icu.impl.locale.KeyTypeData;
import com.ibm.icu.impl.locale.LanguageTag;
import com.ibm.icu.impl.locale.LocaleExtensions;
import com.ibm.icu.impl.locale.LocaleSyntaxException;
import com.ibm.icu.impl.locale.ParseStatus;
import com.ibm.icu.impl.locale.UnicodeLocaleExtension;
import com.ibm.icu.lang.UScript;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.LocaleDisplayNames;
import com.ibm.icu.util.LocaleMatcher;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;
import org.apache.commons.codec.language.Soundex;
import wseemann.media.FFmpegMediaMetadataRetriever;

/* loaded from: classes2.dex */
public final class ULocale implements Serializable, Comparable<ULocale> {
    public static Type ACTUAL_LOCALE = null;
    private static final SoftCache<Locale, ULocale, Void> CACHE;
    public static final ULocale CANADA;
    public static final ULocale CANADA_FRENCH;
    private static String[][] CANONICALIZE_MAP = null;
    public static final ULocale CHINA;
    private static final Locale EMPTY_LOCALE;
    private static final String EMPTY_STRING = "";
    private static final String LANG_DIR_STRING = "root-en-es-pt-zh-ja-ko-de-fr-it-ar+he+fa+ru-nl-pl-th-tr-";
    private static final String LOCALE_ATTRIBUTE_KEY = "attribute";
    public static final ULocale PRC;
    public static final char PRIVATE_USE_EXTENSION = 'x';
    public static final ULocale ROOT;
    public static final ULocale TAIWAN;
    public static final ULocale UK;
    private static final String UNDEFINED_LANGUAGE = "und";
    private static final String UNDEFINED_REGION = "ZZ";
    private static final String UNDEFINED_SCRIPT = "Zzzz";
    private static final char UNDERSCORE = '_';
    public static final char UNICODE_LOCALE_EXTENSION = 'u';
    public static final ULocale US;
    public static Type VALID_LOCALE = null;
    private static Locale[] defaultCategoryLocales = null;
    private static ULocale[] defaultCategoryULocales = null;
    private static Locale defaultLocale = null;
    private static ULocale defaultULocale = null;
    private static Set<String> gKnownCanonicalizedCases = null;
    private static final long serialVersionUID = 3715177670352309217L;
    private volatile transient BaseLocale baseLocale;
    private volatile transient LocaleExtensions extensions;
    private volatile transient Locale locale;
    private String localeID;
    private static final Pattern UND_PATTERN = Pattern.compile("^und(?=$|[_-])", 2);
    private static CacheBase<String, String, Void> nameCache = new SoftCache<String, String, Void>() { // from class: com.ibm.icu.util.ULocale.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ibm.icu.impl.CacheBase
        public String createInstance(String str, Void r2) {
            return new LocaleIDParser(str).getName();
        }
    };
    public static final ULocale ENGLISH = new ULocale("en", Locale.ENGLISH);
    public static final ULocale FRENCH = new ULocale("fr", Locale.FRENCH);
    public static final ULocale GERMAN = new ULocale("de", Locale.GERMAN);
    public static final ULocale ITALIAN = new ULocale("it", Locale.ITALIAN);
    public static final ULocale JAPANESE = new ULocale("ja", Locale.JAPANESE);
    public static final ULocale KOREAN = new ULocale("ko", Locale.KOREAN);
    public static final ULocale CHINESE = new ULocale("zh", Locale.CHINESE);
    public static final ULocale SIMPLIFIED_CHINESE = new ULocale("zh_Hans");
    public static final ULocale TRADITIONAL_CHINESE = new ULocale("zh_Hant");
    public static final ULocale FRANCE = new ULocale("fr_FR", Locale.FRANCE);
    public static final ULocale GERMANY = new ULocale("de_DE", Locale.GERMANY);
    public static final ULocale ITALY = new ULocale("it_IT", Locale.ITALY);
    public static final ULocale JAPAN = new ULocale("ja_JP", Locale.JAPAN);
    public static final ULocale KOREA = new ULocale("ko_KR", Locale.KOREA);

    public enum AvailableType {
        DEFAULT,
        ONLY_LEGACY_ALIASES,
        WITH_LEGACY_ALIASES
    }

    public enum Category {
        DISPLAY,
        FORMAT
    }

    @Deprecated
    public enum Minimize {
        FAVOR_SCRIPT,
        FAVOR_REGION
    }

    public Object clone() {
        return this;
    }

    static {
        ULocale uLocale = new ULocale("zh_Hans_CN");
        CHINA = uLocale;
        PRC = uLocale;
        TAIWAN = new ULocale("zh_Hant_TW");
        UK = new ULocale("en_GB", Locale.UK);
        US = new ULocale("en_US", Locale.US);
        CANADA = new ULocale("en_CA", Locale.CANADA);
        CANADA_FRENCH = new ULocale("fr_CA", Locale.CANADA_FRENCH);
        EMPTY_LOCALE = new Locale("", "");
        ROOT = new ULocale("", EMPTY_LOCALE);
        CACHE = new SoftCache<Locale, ULocale, Void>() { // from class: com.ibm.icu.util.ULocale.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ibm.icu.impl.CacheBase
            public ULocale createInstance(Locale locale, Void r2) {
                return JDKLocaleHelper.toULocale(locale);
            }
        };
        int i = 0;
        CANONICALIZE_MAP = new String[][]{new String[]{"art__LOJBAN", "jbo"}, new String[]{"cel__GAULISH", "cel__GAULISH"}, new String[]{"de__1901", "de__1901"}, new String[]{"de__1906", "de__1906"}, new String[]{"en__BOONT", "en__BOONT"}, new String[]{"en__SCOUSE", "en__SCOUSE"}, new String[]{"hy__AREVELA", "hy", null, null}, new String[]{"hy__AREVMDA", "hyw", null, null}, new String[]{"sl__ROZAJ", "sl__ROZAJ"}, new String[]{"zh__GUOYU", "zh"}, new String[]{"zh__HAKKA", "hak"}, new String[]{"zh__XIANG", "hsn"}, new String[]{"zh_GAN", "gan"}, new String[]{"zh_MIN", "zh__MIN"}, new String[]{"zh_MIN_NAN", "nan"}, new String[]{"zh_WUU", "wuu"}, new String[]{"zh_YUE", "yue"}};
        defaultLocale = Locale.getDefault();
        defaultCategoryLocales = new Locale[Category.values().length];
        defaultCategoryULocales = new ULocale[Category.values().length];
        defaultULocale = forLocale(defaultLocale);
        if (JDKLocaleHelper.hasLocaleCategories()) {
            Category[] categoryArrValues = Category.values();
            int length = categoryArrValues.length;
            while (i < length) {
                Category category = categoryArrValues[i];
                int iOrdinal = category.ordinal();
                defaultCategoryLocales[iOrdinal] = JDKLocaleHelper.getDefault(category);
                defaultCategoryULocales[iOrdinal] = forLocale(defaultCategoryLocales[iOrdinal]);
                i++;
            }
        } else {
            Category[] categoryArrValues2 = Category.values();
            int length2 = categoryArrValues2.length;
            while (i < length2) {
                int iOrdinal2 = categoryArrValues2[i].ordinal();
                defaultCategoryLocales[iOrdinal2] = defaultLocale;
                defaultCategoryULocales[iOrdinal2] = defaultULocale;
                i++;
            }
        }
        gKnownCanonicalizedCases = null;
        ACTUAL_LOCALE = new Type();
        VALID_LOCALE = new Type();
    }

    private ULocale(String str, Locale locale) {
        this.localeID = str;
        this.locale = locale;
    }

    public static ULocale forLocale(Locale locale) {
        if (locale == null) {
            return null;
        }
        return CACHE.getInstance(locale, null);
    }

    public ULocale(String str) {
        this.localeID = getName(str);
    }

    public ULocale(String str, String str2) {
        this(str, str2, (String) null);
    }

    public ULocale(String str, String str2, String str3) {
        this.localeID = getName(lscvToID(str, str2, str3, ""));
    }

    public static ULocale createCanonical(String str) {
        return new ULocale(canonicalize(str), (Locale) null);
    }

    public static ULocale createCanonical(ULocale uLocale) {
        return createCanonical(uLocale.getName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String lscvToID(String str, String str2, String str3, String str4) {
        StringBuilder sb = new StringBuilder();
        if (str != null && str.length() > 0) {
            sb.append(str);
        }
        if (str2 != null && str2.length() > 0) {
            sb.append(UNDERSCORE);
            sb.append(str2);
        }
        if (str3 != null && str3.length() > 0) {
            sb.append(UNDERSCORE);
            sb.append(str3);
        }
        if (str4 != null && str4.length() > 0) {
            if (str3 == null || str3.length() == 0) {
                sb.append(UNDERSCORE);
            }
            sb.append(UNDERSCORE);
            sb.append(str4);
        }
        return sb.toString();
    }

    public Locale toLocale() {
        if (this.locale == null) {
            this.locale = JDKLocaleHelper.toLocale(this);
        }
        return this.locale;
    }

    public static ULocale getDefault() {
        synchronized (ULocale.class) {
            if (defaultULocale == null) {
                return ROOT;
            }
            Locale locale = Locale.getDefault();
            if (!defaultLocale.equals(locale)) {
                defaultLocale = locale;
                defaultULocale = forLocale(locale);
                if (!JDKLocaleHelper.hasLocaleCategories()) {
                    for (Category category : Category.values()) {
                        int iOrdinal = category.ordinal();
                        defaultCategoryLocales[iOrdinal] = locale;
                        defaultCategoryULocales[iOrdinal] = forLocale(locale);
                    }
                }
            }
            return defaultULocale;
        }
    }

    public static synchronized void setDefault(ULocale uLocale) {
        Locale locale = uLocale.toLocale();
        defaultLocale = locale;
        Locale.setDefault(locale);
        defaultULocale = uLocale;
        for (Category category : Category.values()) {
            setDefault(category, uLocale);
        }
    }

    public static ULocale getDefault(Category category) {
        synchronized (ULocale.class) {
            int iOrdinal = category.ordinal();
            if (defaultCategoryULocales[iOrdinal] == null) {
                return ROOT;
            }
            if (JDKLocaleHelper.hasLocaleCategories()) {
                Locale locale = JDKLocaleHelper.getDefault(category);
                if (!defaultCategoryLocales[iOrdinal].equals(locale)) {
                    defaultCategoryLocales[iOrdinal] = locale;
                    defaultCategoryULocales[iOrdinal] = forLocale(locale);
                }
            } else {
                Locale locale2 = Locale.getDefault();
                if (!defaultLocale.equals(locale2)) {
                    defaultLocale = locale2;
                    defaultULocale = forLocale(locale2);
                    for (Category category2 : Category.values()) {
                        int iOrdinal2 = category2.ordinal();
                        defaultCategoryLocales[iOrdinal2] = locale2;
                        defaultCategoryULocales[iOrdinal2] = forLocale(locale2);
                    }
                }
            }
            return defaultCategoryULocales[iOrdinal];
        }
    }

    public static synchronized void setDefault(Category category, ULocale uLocale) {
        Locale locale = uLocale.toLocale();
        int iOrdinal = category.ordinal();
        defaultCategoryULocales[iOrdinal] = uLocale;
        defaultCategoryLocales[iOrdinal] = locale;
        JDKLocaleHelper.setDefault(category, locale);
    }

    public int hashCode() {
        return this.localeID.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ULocale) {
            return this.localeID.equals(((ULocale) obj).localeID);
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0095  */
    @Override // java.lang.Comparable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int compareTo(com.ibm.icu.util.ULocale r9) {
        /*
            r8 = this;
            r0 = 0
            if (r8 != r9) goto L4
            return r0
        L4:
            java.lang.String r1 = r8.getLanguage()
            java.lang.String r2 = r9.getLanguage()
            int r1 = r1.compareTo(r2)
            r2 = 1
            r3 = -1
            if (r1 != 0) goto L96
            java.lang.String r1 = r8.getScript()
            java.lang.String r4 = r9.getScript()
            int r1 = r1.compareTo(r4)
            if (r1 != 0) goto L96
            java.lang.String r1 = r8.getCountry()
            java.lang.String r4 = r9.getCountry()
            int r1 = r1.compareTo(r4)
            if (r1 != 0) goto L96
            java.lang.String r1 = r8.getVariant()
            java.lang.String r4 = r9.getVariant()
            int r1 = r1.compareTo(r4)
            if (r1 != 0) goto L96
            java.util.Iterator r4 = r8.getKeywords()
            java.util.Iterator r5 = r9.getKeywords()
            if (r4 != 0) goto L4c
            if (r5 != 0) goto L95
            r1 = 0
            goto L96
        L4c:
            if (r5 != 0) goto L50
            r1 = 1
            goto L96
        L50:
            if (r1 != 0) goto L8d
            boolean r6 = r4.hasNext()
            if (r6 == 0) goto L8d
            boolean r1 = r5.hasNext()
            if (r1 != 0) goto L60
            r1 = 1
            goto L8d
        L60:
            java.lang.Object r1 = r4.next()
            java.lang.String r1 = (java.lang.String) r1
            java.lang.Object r6 = r5.next()
            java.lang.String r6 = (java.lang.String) r6
            int r7 = r1.compareTo(r6)
            if (r7 != 0) goto L8b
            java.lang.String r1 = r8.getKeywordValue(r1)
            java.lang.String r6 = r9.getKeywordValue(r6)
            if (r1 != 0) goto L82
            if (r6 != 0) goto L80
            r1 = 0
            goto L50
        L80:
            r1 = -1
            goto L50
        L82:
            if (r6 != 0) goto L86
            r1 = 1
            goto L50
        L86:
            int r1 = r1.compareTo(r6)
            goto L50
        L8b:
            r1 = r7
            goto L50
        L8d:
            if (r1 != 0) goto L96
            boolean r9 = r5.hasNext()
            if (r9 == 0) goto L96
        L95:
            r1 = -1
        L96:
            if (r1 >= 0) goto L9a
            r0 = -1
            goto L9d
        L9a:
            if (r1 <= 0) goto L9d
            r0 = 1
        L9d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.ULocale.compareTo(com.ibm.icu.util.ULocale):int");
    }

    public static ULocale[] getAvailableLocales() {
        return (ULocale[]) ICUResourceBundle.getAvailableULocales().clone();
    }

    public static Collection<ULocale> getAvailableLocalesByType(AvailableType availableType) {
        List listAsList;
        if (availableType == null) {
            throw new IllegalArgumentException();
        }
        if (availableType == AvailableType.WITH_LEGACY_ALIASES) {
            listAsList = new ArrayList();
            Collections.addAll(listAsList, ICUResourceBundle.getAvailableULocales(AvailableType.DEFAULT));
            Collections.addAll(listAsList, ICUResourceBundle.getAvailableULocales(AvailableType.ONLY_LEGACY_ALIASES));
        } else {
            listAsList = Arrays.asList(ICUResourceBundle.getAvailableULocales(availableType));
        }
        return Collections.unmodifiableList(listAsList);
    }

    public static String[] getISOCountries() {
        return LocaleIDs.getISOCountries();
    }

    public static String[] getISOLanguages() {
        return LocaleIDs.getISOLanguages();
    }

    public String getLanguage() {
        return base().getLanguage();
    }

    public static String getLanguage(String str) {
        return new LocaleIDParser(str).getLanguage();
    }

    public String getScript() {
        return base().getScript();
    }

    public static String getScript(String str) {
        return new LocaleIDParser(str).getScript();
    }

    public String getCountry() {
        return base().getRegion();
    }

    public static String getCountry(String str) {
        return new LocaleIDParser(str).getCountry();
    }

    @Deprecated
    public static String getRegionForSupplementalData(ULocale uLocale, boolean z) {
        String keywordValue = uLocale.getKeywordValue("rg");
        if (keywordValue != null && keywordValue.length() == 6) {
            String upperString = AsciiUtil.toUpperString(keywordValue);
            if (upperString.endsWith(DateFormat.ABBR_UTC_TZ)) {
                return upperString.substring(0, 2);
            }
        }
        String country = uLocale.getCountry();
        return (country.length() == 0 && z) ? addLikelySubtags(uLocale).getCountry() : country;
    }

    public String getVariant() {
        return base().getVariant();
    }

    public static String getVariant(String str) {
        return new LocaleIDParser(str).getVariant();
    }

    public static String getFallback(String str) {
        return getFallbackString(getName(str));
    }

    public ULocale getFallback() {
        if (this.localeID.length() == 0 || this.localeID.charAt(0) == '@') {
            return null;
        }
        return new ULocale(getFallbackString(this.localeID), (Locale) null);
    }

    private static String getFallbackString(String str) {
        int iIndexOf = str.indexOf(64);
        if (iIndexOf == -1) {
            iIndexOf = str.length();
        }
        int iLastIndexOf = str.lastIndexOf(95, iIndexOf);
        if (iLastIndexOf == -1) {
            iLastIndexOf = 0;
        } else {
            while (iLastIndexOf > 0 && str.charAt(iLastIndexOf - 1) == '_') {
                iLastIndexOf--;
            }
        }
        return str.substring(0, iLastIndexOf) + str.substring(iIndexOf);
    }

    public String getBaseName() {
        return getBaseName(this.localeID);
    }

    public static String getBaseName(String str) {
        return str.indexOf(64) == -1 ? str : new LocaleIDParser(str).getBaseName();
    }

    public String getName() {
        return this.localeID;
    }

    private static int getShortestSubtagLength(String str) {
        int length = str.length();
        int i = length;
        int i2 = 0;
        boolean z = true;
        for (int i3 = 0; i3 < length; i3++) {
            if (str.charAt(i3) == '_' || str.charAt(i3) == '-') {
                if (i2 != 0 && i2 < i) {
                    i = i2;
                }
                z = true;
            } else {
                if (z) {
                    i2 = 0;
                    z = false;
                }
                i2++;
            }
        }
        return i;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x002a A[PHI: r0
      0x002a: PHI (r0v2 java.lang.String) = (r0v0 java.lang.String), (r0v4 java.lang.String) binds: [B:12:0x0028, B:9:0x001f] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String getName(java.lang.String r3) {
        /*
            java.lang.String r0 = ""
            if (r3 == 0) goto L22
            java.lang.String r1 = "@"
            boolean r1 = r3.contains(r1)
            if (r1 != 0) goto L22
            int r1 = getShortestSubtagLength(r3)
            r2 = 1
            if (r1 != r2) goto L22
            com.ibm.icu.util.ULocale r0 = forLanguageTag(r3)
            java.lang.String r0 = r0.getName()
            int r1 = r0.length()
            if (r1 != 0) goto L2a
            goto L36
        L22:
            java.lang.String r1 = "root"
            boolean r1 = r1.equalsIgnoreCase(r3)
            if (r1 == 0) goto L2c
        L2a:
            r3 = r0
            goto L36
        L2c:
            java.util.regex.Pattern r1 = com.ibm.icu.util.ULocale.UND_PATTERN
            java.util.regex.Matcher r3 = r1.matcher(r3)
            java.lang.String r3 = r3.replaceFirst(r0)
        L36:
            com.ibm.icu.impl.CacheBase<java.lang.String, java.lang.String, java.lang.Void> r0 = com.ibm.icu.util.ULocale.nameCache
            r1 = 0
            java.lang.Object r3 = r0.getInstance(r3, r1)
            java.lang.String r3 = (java.lang.String) r3
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.ULocale.getName(java.lang.String):java.lang.String");
    }

    public String toString() {
        return this.localeID;
    }

    public Iterator<String> getKeywords() {
        return getKeywords(this.localeID);
    }

    public static Iterator<String> getKeywords(String str) {
        return new LocaleIDParser(str).getKeywords();
    }

    public String getKeywordValue(String str) {
        return getKeywordValue(this.localeID, str);
    }

    public static String getKeywordValue(String str, String str2) {
        return new LocaleIDParser(str).getKeywordValue(str2);
    }

    private static class AliasReplacer {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private String extensions;
        private String language;
        private String region;
        private String script;
        private List<String> variants;
        private static boolean aliasDataIsLoaded = false;
        private static Map<String, String> languageAliasMap = null;
        private static Map<String, String> scriptAliasMap = null;
        private static Map<String, List<String>> territoryAliasMap = null;
        private static Map<String, String> variantAliasMap = null;
        private static Map<String, String> subdivisionAliasMap = null;

        public AliasReplacer(String str, String str2, String str3, String str4, String str5) {
            this.language = str;
            this.script = str2;
            this.region = str3;
            if (!str4.isEmpty()) {
                this.variants = new ArrayList(Arrays.asList(str4.split(BaseLocale.SEP)));
            }
            this.extensions = str5;
        }

        public String replace() {
            String strReplaceSubdivision;
            loadAliasData();
            boolean z = false;
            boolean z2 = true;
            int i = 0;
            boolean z3 = false;
            while (true) {
                int i2 = i + 1;
                if (i > 10) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Have problem to resolve locale alias of ");
                    String str = this.language;
                    String str2 = this.script;
                    String str3 = this.region;
                    List<String> list = this.variants;
                    sb.append(ULocale.lscvToID(str, str2, str3, list != null ? Utility.joinStrings(BaseLocale.SEP, list) : ""));
                    sb.append(this.extensions);
                    throw new IllegalArgumentException(sb.toString());
                }
                if (!replaceLanguage(true, true, true) && !replaceLanguage(true, true, false) && !replaceLanguage(true, false, true) && !replaceLanguage(true, false, false) && !replaceLanguage(false, false, true) && !replaceRegion() && !replaceScript() && !replaceVariant()) {
                    if (this.extensions == null && !z3) {
                        return null;
                    }
                    String str4 = this.language;
                    String str5 = this.script;
                    String str6 = this.region;
                    List<String> list2 = this.variants;
                    String strLscvToID = ULocale.lscvToID(str4, str5, str6, list2 != null ? Utility.joinStrings(BaseLocale.SEP, list2) : "");
                    if (this.extensions != null) {
                        ULocale uLocale = new ULocale(strLscvToID + this.extensions);
                        Iterator<String> keywords = uLocale.getKeywords();
                        while (keywords != null && keywords.hasNext()) {
                            String next = keywords.next();
                            if (next.equals("rg") || next.equals("sd") || next.equals("t")) {
                                String keywordValue = uLocale.getKeywordValue(next);
                                if (next.equals("t")) {
                                    strReplaceSubdivision = replaceTransformedExtensions(keywordValue);
                                } else {
                                    strReplaceSubdivision = replaceSubdivision(keywordValue);
                                }
                                if (strReplaceSubdivision != null) {
                                    uLocale = uLocale.setKeywordValue(next, strReplaceSubdivision);
                                    z = true;
                                }
                            }
                        }
                        if (z) {
                            this.extensions = uLocale.getName().substring(uLocale.getBaseName().length());
                        } else {
                            z2 = z3;
                        }
                        strLscvToID = strLscvToID + this.extensions;
                        z3 = z2;
                    }
                    if (z3) {
                        return strLscvToID;
                    }
                    return null;
                }
                i = i2;
                z3 = true;
            }
        }

        private static synchronized void loadAliasData() {
            if (aliasDataIsLoaded) {
                return;
            }
            languageAliasMap = new HashMap();
            scriptAliasMap = new HashMap();
            territoryAliasMap = new HashMap();
            variantAliasMap = new HashMap();
            subdivisionAliasMap = new HashMap();
            UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "metadata", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("alias");
            UResourceBundle uResourceBundle2 = uResourceBundle.get(FFmpegMediaMetadataRetriever.METADATA_KEY_LANGUAGE);
            UResourceBundle uResourceBundle3 = uResourceBundle.get("script");
            UResourceBundle uResourceBundle4 = uResourceBundle.get("territory");
            UResourceBundle uResourceBundle5 = uResourceBundle.get("variant");
            UResourceBundle uResourceBundle6 = uResourceBundle.get("subdivision");
            for (int i = 0; i < uResourceBundle2.getSize(); i++) {
                UResourceBundle uResourceBundle7 = uResourceBundle2.get(i);
                String key = uResourceBundle7.getKey();
                String string = uResourceBundle7.get("replacement").getString();
                Locale locale = new Locale(key);
                if (!locale.getScript().isEmpty() || (key.startsWith(ULocale.UNDEFINED_LANGUAGE) && !locale.getCountry().isEmpty())) {
                    throw new IllegalArgumentException("key [" + key + "] in alias:language contains unsupported fields combination.");
                }
                languageAliasMap.put(key, string);
            }
            for (int i2 = 0; i2 < uResourceBundle3.getSize(); i2++) {
                UResourceBundle uResourceBundle8 = uResourceBundle3.get(i2);
                String key2 = uResourceBundle8.getKey();
                String string2 = uResourceBundle8.get("replacement").getString();
                if (key2.length() != 4) {
                    throw new IllegalArgumentException("Incorrect key [" + key2 + "] in alias:script.");
                }
                scriptAliasMap.put(key2, string2);
            }
            for (int i3 = 0; i3 < uResourceBundle4.getSize(); i3++) {
                UResourceBundle uResourceBundle9 = uResourceBundle4.get(i3);
                String key3 = uResourceBundle9.getKey();
                String string3 = uResourceBundle9.get("replacement").getString();
                if (key3.length() < 2 || key3.length() > 3) {
                    throw new IllegalArgumentException("Incorrect key [" + key3 + "] in alias:territory.");
                }
                territoryAliasMap.put(key3, new ArrayList(Arrays.asList(string3.split(" "))));
            }
            for (int i4 = 0; i4 < uResourceBundle5.getSize(); i4++) {
                UResourceBundle uResourceBundle10 = uResourceBundle5.get(i4);
                String key4 = uResourceBundle10.getKey();
                String string4 = uResourceBundle10.get("replacement").getString();
                if (key4.length() < 4 || key4.length() > 8 || (key4.length() == 4 && (key4.charAt(0) < '0' || key4.charAt(0) > '9'))) {
                    throw new IllegalArgumentException("Incorrect key [" + key4 + "] in alias:variant.");
                }
                if (string4.length() < 4 || string4.length() > 8 || (string4.length() == 4 && (string4.charAt(0) < '0' || string4.charAt(0) > '9'))) {
                    throw new IllegalArgumentException("Incorrect variant [" + string4 + "] for the key [" + key4 + "] in alias:variant.");
                }
                variantAliasMap.put(key4, string4);
            }
            for (int i5 = 0; i5 < uResourceBundle6.getSize(); i5++) {
                UResourceBundle uResourceBundle11 = uResourceBundle6.get(i5);
                String key5 = uResourceBundle11.getKey();
                String str = uResourceBundle11.get("replacement").getString().split(" ")[0];
                if (key5.length() < 3 || key5.length() > 8) {
                    throw new IllegalArgumentException("Incorrect key [" + key5 + "] in alias:territory.");
                }
                if (str.length() == 2) {
                    str = str + DateFormat.SPECIFIC_TZ;
                } else if (str.length() < 2 || str.length() > 8) {
                    throw new IllegalArgumentException("Incorrect value [" + str + "] in alias:territory.");
                }
                subdivisionAliasMap.put(key5, str);
            }
            aliasDataIsLoaded = true;
        }

        private static String generateKey(String str, String str2, String str3) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            if (str2 != null && !str2.isEmpty()) {
                sb.append(ULocale.UNDERSCORE);
                sb.append(str2);
            }
            if (str3 != null && !str3.isEmpty()) {
                sb.append(ULocale.UNDERSCORE);
                sb.append(str3);
            }
            return sb.toString();
        }

        private static String deleteOrReplace(String str, String str2, String str3) {
            if (str3 != null && !str3.isEmpty()) {
                return (str == null || str.isEmpty()) ? str3 : str;
            }
            if (str2 == null || str2.isEmpty()) {
                return str;
            }
            return null;
        }

        /* JADX WARN: Type inference failed for: r1v0 */
        /* JADX WARN: Type inference failed for: r1v1, types: [boolean] */
        /* JADX WARN: Type inference failed for: r1v10 */
        private boolean replaceLanguage(boolean z, boolean z2, boolean z3) {
            int i;
            String str;
            String str2;
            String str3;
            String strSubstring;
            int i2;
            String str4;
            ?? r1 = 0;
            if ((z2 && ((str4 = this.region) == null || str4.isEmpty())) || (z3 && this.variants == null)) {
                return false;
            }
            int i3 = 1;
            int size = z3 ? this.variants.size() : 1;
            String str5 = z ? this.language : ULocale.UNDEFINED_LANGUAGE;
            String str6 = z2 ? this.region : null;
            int i4 = 0;
            String str7 = null;
            while (i4 < size) {
                if (z3) {
                    str7 = this.variants.get(i4);
                }
                int i5 = 4;
                if (str7 != null && str7.length() < 4) {
                    str7 = null;
                }
                String str8 = languageAliasMap.get(generateKey(str5, str6, str7));
                if (str8 == null) {
                    i = size;
                } else {
                    if (str8.indexOf(95) < 0) {
                        if (str8.equals(ULocale.UNDEFINED_LANGUAGE)) {
                            str8 = this.language;
                        }
                        i = size;
                        strSubstring = null;
                        str2 = null;
                        str3 = null;
                        str = null;
                    } else {
                        String[] strArrSplit = str8.split(BaseLocale.SEP);
                        String str9 = strArrSplit[r1];
                        if (str9.equals(ULocale.UNDEFINED_LANGUAGE)) {
                            str9 = this.language;
                        }
                        int length = strArrSplit[r1].length() + i3;
                        int i6 = 1;
                        String str10 = null;
                        String str11 = null;
                        String str12 = null;
                        while (true) {
                            if (strArrSplit.length <= i6) {
                                i = size;
                                str8 = str9;
                                str = str10;
                                str2 = str11;
                                str3 = str12;
                                strSubstring = null;
                                break;
                            }
                            String str13 = strArrSplit[i6];
                            int length2 = str13.length();
                            if (i3 == length2) {
                                strSubstring = str8.substring(length);
                                i = size;
                                str8 = str9;
                                str = str10;
                                str2 = str11;
                                str3 = str12;
                                break;
                            }
                            if (length2 < 2 || length2 > 3) {
                                if (length2 >= 5 && length2 <= 8) {
                                    i2 = size;
                                } else if (length2 == i5) {
                                    i2 = size;
                                    if (str13.charAt(0) < '0' || str13.charAt(0) > '9') {
                                        str10 = str13;
                                    }
                                } else {
                                    i2 = size;
                                }
                                str12 = str13;
                            } else {
                                i2 = size;
                                str11 = str13;
                            }
                            i6++;
                            length += length2 + 1;
                            size = i2;
                            i3 = 1;
                            i5 = 4;
                        }
                    }
                    String strDeleteOrReplace = deleteOrReplace(this.script, null, str);
                    String strDeleteOrReplace2 = deleteOrReplace(this.region, str6, str2);
                    String strDeleteOrReplace3 = deleteOrReplace(str7, str7, str3);
                    if (!this.language.equals(str8) || !this.script.equals(strDeleteOrReplace) || !this.region.equals(strDeleteOrReplace2) || !Objects.equals(str7, strDeleteOrReplace3) || strSubstring != null) {
                        this.language = str8;
                        this.script = strDeleteOrReplace;
                        this.region = strDeleteOrReplace2;
                        if (str7 != null && !str7.isEmpty()) {
                            if (strDeleteOrReplace3 != null && !strDeleteOrReplace3.isEmpty()) {
                                this.variants.set(i4, strDeleteOrReplace3);
                            } else {
                                this.variants.remove(i4);
                                if (this.variants.isEmpty()) {
                                    this.variants = null;
                                }
                            }
                        }
                        if (strSubstring != null) {
                            strSubstring.isEmpty();
                        }
                        return true;
                    }
                }
                i4++;
                size = i;
                r1 = 0;
                i3 = 1;
            }
            return r1;
        }

        private boolean replaceRegion() {
            List<String> list;
            String country;
            String str = this.region;
            if (str == null || str.isEmpty() || (list = territoryAliasMap.get(this.region)) == null) {
                return false;
            }
            if (list.size() > 1) {
                country = ULocale.addLikelySubtags(new ULocale(this.language, this.script, (String) null)).getCountry();
                if (!list.contains(country)) {
                    country = list.get(0);
                }
            } else {
                country = list.get(0);
            }
            this.region = country;
            return true;
        }

        private boolean replaceScript() {
            String str;
            String str2 = this.script;
            if (str2 == null || str2.isEmpty() || (str = scriptAliasMap.get(this.script)) == null) {
                return false;
            }
            this.script = str;
            return true;
        }

        private boolean replaceVariant() {
            if (this.variants == null) {
                return false;
            }
            for (int i = 0; i < this.variants.size(); i++) {
                String str = this.variants.get(i);
                String str2 = variantAliasMap.get(str);
                if (str2 != null && !str.equals(str2)) {
                    this.variants.set(i, str2);
                    if (!str.equals("heploc")) {
                        return true;
                    }
                    this.variants.remove("hepburn");
                    if (!this.variants.isEmpty()) {
                        return true;
                    }
                    this.variants = null;
                    return true;
                }
            }
            return false;
        }

        private String replaceSubdivision(String str) {
            return subdivisionAliasMap.get(str);
        }

        private String replaceTransformedExtensions(String str) {
            StringBuilder sb = new StringBuilder();
            ArrayList<String> arrayList = new ArrayList(Arrays.asList(str.split(LanguageTag.SEP)));
            ArrayList arrayList2 = new ArrayList();
            String strSubstring = "";
            String str2 = "";
            int i = 0;
            int length = 0;
            for (String str3 : arrayList) {
                if (LanguageTag.isTKey(str3)) {
                    if (i == 0) {
                        i = length - 1;
                    }
                    if (sb.length() > 0) {
                        arrayList2.add(sb.toString());
                        sb.setLength(0);
                    }
                    sb.append(str3);
                    str2 = str3;
                } else if (i != 0) {
                    sb.append(LanguageTag.SEP);
                    sb.append(ULocale.toUnicodeLocaleType(str2, str3));
                }
                length += str3.length() + 1;
            }
            if (sb.length() > 0) {
                arrayList2.add(sb.toString());
                sb.setLength(0);
            }
            if (i > 0) {
                strSubstring = str.substring(0, i);
            } else if (arrayList2.size() == 0) {
                strSubstring = str;
            }
            if (strSubstring.length() > 0) {
                sb.append(AsciiUtil.toLowerString(ULocale.createCanonical(ULocale.forLanguageTag(str)).toLanguageTag()));
            }
            if (arrayList2.size() > 0) {
                if (sb.length() > 0) {
                    sb.append(LanguageTag.SEP);
                }
                Collections.sort(arrayList2);
                sb.append(Utility.joinStrings(LanguageTag.SEP, arrayList2));
            }
            return sb.toString();
        }
    }

    public static String canonicalize(String str) {
        String strReplace;
        boolean z = true;
        LocaleIDParser localeIDParser = new LocaleIDParser(str, true);
        String baseName = localeIDParser.getBaseName();
        if (str.equals("")) {
            return "";
        }
        int i = 0;
        while (true) {
            String[][] strArr = CANONICALIZE_MAP;
            if (i >= strArr.length) {
                z = false;
                break;
            }
            String[] strArr2 = strArr[i];
            if (strArr2[0].equals(baseName)) {
                localeIDParser.setBaseName(strArr2[1]);
                break;
            }
            i++;
        }
        if (!z && localeIDParser.getLanguage().equals("nb") && localeIDParser.getVariant().equals("NY")) {
            localeIDParser.setBaseName(lscvToID("nn", localeIDParser.getScript(), localeIDParser.getCountry(), null));
        }
        if (!isKnownCanonicalizedLocale(localeIDParser.getName()) && (strReplace = new AliasReplacer(localeIDParser.getLanguage(), localeIDParser.getScript(), localeIDParser.getCountry(), AsciiUtil.toLowerString(localeIDParser.getVariant()), localeIDParser.getName().substring(localeIDParser.getBaseName().length())).replace()) != null) {
            localeIDParser = new LocaleIDParser(strReplace);
        }
        return localeIDParser.getName();
    }

    private static synchronized boolean isKnownCanonicalizedLocale(String str) {
        if (!str.equals("c") && !str.equals("en") && !str.equals("en_US")) {
            if (gKnownCanonicalizedCases == null) {
                gKnownCanonicalizedCases = new HashSet(Arrays.asList("af", "af_ZA", "am", "am_ET", "ar", "ar_001", "as", "as_IN", "az", "az_AZ", "be", "be_BY", "bg", "bg_BG", "bn", "bn_IN", "bs", "bs_BA", "ca", "ca_ES", "cs", "cs_CZ", "cy", "cy_GB", "da", "da_DK", "de", "de_DE", "el", "el_GR", "en", "en_GB", "en_US", "es", "es_419", "es_ES", "et", "et_EE", "eu", "eu_ES", "fa", "fa_IR", "fi", "fi_FI", "fil", "fil_PH", "fr", "fr_FR", "ga", "ga_IE", "gl", "gl_ES", "gu", "gu_IN", "he", "he_IL", "hi", "hi_IN", "hr", "hr_HR", "hu", "hu_HU", "hy", "hy_AM", "id", "id_ID", "is", "is_IS", "it", "it_IT", "ja", "ja_JP", DateFormat.HOUR_GENERIC_TZ, "jv_ID", "ka", "ka_GE", "kk", "kk_KZ", "km", "km_KH", "kn", "kn_IN", "ko", "ko_KR", "ky", "ky_KG", "lo", "lo_LA", "lt", "lt_LT", "lv", "lv_LV", "mk", "mk_MK", "ml", "ml_IN", "mn", "mn_MN", "mr", "mr_IN", DateFormat.MINUTE_SECOND, "ms_MY", "my", "my_MM", "nb", "nb_NO", "ne", "ne_NP", "nl", "nl_NL", "no", "or", "or_IN", "pa", "pa_IN", "pl", "pl_PL", "ps", "ps_AF", "pt", "pt_BR", "pt_PT", "ro", "ro_RO", "ru", "ru_RU", "sd", "sd_IN", "si", "si_LK", "sk", "sk_SK", "sl", "sl_SI", "so", "so_SO", "sq", "sq_AL", "sr", "sr_Cyrl_RS", "sr_Latn", "sr_RS", "sv", "sv_SE", "sw", "sw_TZ", "ta", "ta_IN", "te", "te_IN", "th", "th_TH", "tk", "tk_TM", "tr", "tr_TR", "uk", "uk_UA", "ur", "ur_PK", "uz", "uz_UZ", "vi", "vi_VN", "yue", "yue_Hant", "yue_Hant_HK", "yue_HK", "zh", "zh_CN", "zh_Hans", "zh_Hans_CN", "zh_Hant", "zh_Hant_TW", "zh_TW", "zu", "zu_ZA"));
            }
            return gKnownCanonicalizedCases.contains(str);
        }
        return true;
    }

    public ULocale setKeywordValue(String str, String str2) {
        return new ULocale(setKeywordValue(this.localeID, str, str2), (Locale) null);
    }

    public static String setKeywordValue(String str, String str2, String str3) {
        LocaleIDParser localeIDParser = new LocaleIDParser(str);
        localeIDParser.setKeywordValue(str2, str3);
        return localeIDParser.getName();
    }

    public String getISO3Language() {
        return getISO3Language(this.localeID);
    }

    public static String getISO3Language(String str) {
        return LocaleIDs.getISO3Language(getLanguage(str));
    }

    public String getISO3Country() {
        return getISO3Country(this.localeID);
    }

    public static String getISO3Country(String str) {
        return LocaleIDs.getISO3Country(getCountry(str));
    }

    public boolean isRightToLeft() {
        int iIndexOf;
        String script = getScript();
        if (script.length() == 0) {
            String language = getLanguage();
            if (!language.isEmpty() && (iIndexOf = LANG_DIR_STRING.indexOf(language)) >= 0) {
                char cCharAt = LANG_DIR_STRING.charAt(iIndexOf + language.length());
                if (cCharAt == '+') {
                    return true;
                }
                if (cCharAt == '-') {
                    return false;
                }
            }
            script = addLikelySubtags(this).getScript();
            if (script.length() == 0) {
                return false;
            }
        }
        return UScript.isRightToLeft(UScript.getCodeFromName(script));
    }

    public String getDisplayLanguage() {
        return getDisplayLanguageInternal(this, getDefault(Category.DISPLAY), false);
    }

    public String getDisplayLanguage(ULocale uLocale) {
        return getDisplayLanguageInternal(this, uLocale, false);
    }

    public static String getDisplayLanguage(String str, String str2) {
        return getDisplayLanguageInternal(new ULocale(str), new ULocale(str2), false);
    }

    public static String getDisplayLanguage(String str, ULocale uLocale) {
        return getDisplayLanguageInternal(new ULocale(str), uLocale, false);
    }

    public String getDisplayLanguageWithDialect() {
        return getDisplayLanguageInternal(this, getDefault(Category.DISPLAY), true);
    }

    public String getDisplayLanguageWithDialect(ULocale uLocale) {
        return getDisplayLanguageInternal(this, uLocale, true);
    }

    public static String getDisplayLanguageWithDialect(String str, String str2) {
        return getDisplayLanguageInternal(new ULocale(str), new ULocale(str2), true);
    }

    public static String getDisplayLanguageWithDialect(String str, ULocale uLocale) {
        return getDisplayLanguageInternal(new ULocale(str), uLocale, true);
    }

    private static String getDisplayLanguageInternal(ULocale uLocale, ULocale uLocale2, boolean z) {
        return LocaleDisplayNames.getInstance(uLocale2).languageDisplayName(z ? uLocale.getBaseName() : uLocale.getLanguage());
    }

    public String getDisplayScript() {
        return getDisplayScriptInternal(this, getDefault(Category.DISPLAY));
    }

    @Deprecated
    public String getDisplayScriptInContext() {
        return getDisplayScriptInContextInternal(this, getDefault(Category.DISPLAY));
    }

    public String getDisplayScript(ULocale uLocale) {
        return getDisplayScriptInternal(this, uLocale);
    }

    @Deprecated
    public String getDisplayScriptInContext(ULocale uLocale) {
        return getDisplayScriptInContextInternal(this, uLocale);
    }

    public static String getDisplayScript(String str, String str2) {
        return getDisplayScriptInternal(new ULocale(str), new ULocale(str2));
    }

    @Deprecated
    public static String getDisplayScriptInContext(String str, String str2) {
        return getDisplayScriptInContextInternal(new ULocale(str), new ULocale(str2));
    }

    public static String getDisplayScript(String str, ULocale uLocale) {
        return getDisplayScriptInternal(new ULocale(str), uLocale);
    }

    @Deprecated
    public static String getDisplayScriptInContext(String str, ULocale uLocale) {
        return getDisplayScriptInContextInternal(new ULocale(str), uLocale);
    }

    private static String getDisplayScriptInternal(ULocale uLocale, ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2).scriptDisplayName(uLocale.getScript());
    }

    private static String getDisplayScriptInContextInternal(ULocale uLocale, ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2).scriptDisplayNameInContext(uLocale.getScript());
    }

    public String getDisplayCountry() {
        return getDisplayCountryInternal(this, getDefault(Category.DISPLAY));
    }

    public String getDisplayCountry(ULocale uLocale) {
        return getDisplayCountryInternal(this, uLocale);
    }

    public static String getDisplayCountry(String str, String str2) {
        return getDisplayCountryInternal(new ULocale(str), new ULocale(str2));
    }

    public static String getDisplayCountry(String str, ULocale uLocale) {
        return getDisplayCountryInternal(new ULocale(str), uLocale);
    }

    private static String getDisplayCountryInternal(ULocale uLocale, ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2).regionDisplayName(uLocale.getCountry());
    }

    public String getDisplayVariant() {
        return getDisplayVariantInternal(this, getDefault(Category.DISPLAY));
    }

    public String getDisplayVariant(ULocale uLocale) {
        return getDisplayVariantInternal(this, uLocale);
    }

    public static String getDisplayVariant(String str, String str2) {
        return getDisplayVariantInternal(new ULocale(str), new ULocale(str2));
    }

    public static String getDisplayVariant(String str, ULocale uLocale) {
        return getDisplayVariantInternal(new ULocale(str), uLocale);
    }

    private static String getDisplayVariantInternal(ULocale uLocale, ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2).variantDisplayName(uLocale.getVariant());
    }

    public static String getDisplayKeyword(String str) {
        return getDisplayKeywordInternal(str, getDefault(Category.DISPLAY));
    }

    public static String getDisplayKeyword(String str, String str2) {
        return getDisplayKeywordInternal(str, new ULocale(str2));
    }

    public static String getDisplayKeyword(String str, ULocale uLocale) {
        return getDisplayKeywordInternal(str, uLocale);
    }

    private static String getDisplayKeywordInternal(String str, ULocale uLocale) {
        return LocaleDisplayNames.getInstance(uLocale).keyDisplayName(str);
    }

    public String getDisplayKeywordValue(String str) {
        return getDisplayKeywordValueInternal(this, str, getDefault(Category.DISPLAY));
    }

    public String getDisplayKeywordValue(String str, ULocale uLocale) {
        return getDisplayKeywordValueInternal(this, str, uLocale);
    }

    public static String getDisplayKeywordValue(String str, String str2, String str3) {
        return getDisplayKeywordValueInternal(new ULocale(str), str2, new ULocale(str3));
    }

    public static String getDisplayKeywordValue(String str, String str2, ULocale uLocale) {
        return getDisplayKeywordValueInternal(new ULocale(str), str2, uLocale);
    }

    private static String getDisplayKeywordValueInternal(ULocale uLocale, String str, ULocale uLocale2) {
        String lowerString = AsciiUtil.toLowerString(str.trim());
        return LocaleDisplayNames.getInstance(uLocale2).keyValueDisplayName(lowerString, uLocale.getKeywordValue(lowerString));
    }

    public String getDisplayName() {
        return getDisplayNameInternal(this, getDefault(Category.DISPLAY));
    }

    public String getDisplayName(ULocale uLocale) {
        return getDisplayNameInternal(this, uLocale);
    }

    public static String getDisplayName(String str, String str2) {
        return getDisplayNameInternal(new ULocale(str), new ULocale(str2));
    }

    public static String getDisplayName(String str, ULocale uLocale) {
        return getDisplayNameInternal(new ULocale(str), uLocale);
    }

    private static String getDisplayNameInternal(ULocale uLocale, ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2).localeDisplayName(uLocale);
    }

    public String getDisplayNameWithDialect() {
        return getDisplayNameWithDialectInternal(this, getDefault(Category.DISPLAY));
    }

    public String getDisplayNameWithDialect(ULocale uLocale) {
        return getDisplayNameWithDialectInternal(this, uLocale);
    }

    public static String getDisplayNameWithDialect(String str, String str2) {
        return getDisplayNameWithDialectInternal(new ULocale(str), new ULocale(str2));
    }

    public static String getDisplayNameWithDialect(String str, ULocale uLocale) {
        return getDisplayNameWithDialectInternal(new ULocale(str), uLocale);
    }

    private static String getDisplayNameWithDialectInternal(ULocale uLocale, ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2, LocaleDisplayNames.DialectHandling.DIALECT_NAMES).localeDisplayName(uLocale);
    }

    public String getCharacterOrientation() {
        return ICUResourceTableAccess.getTableString(ICUData.ICU_BASE_NAME, this, "layout", "characters", "characters");
    }

    public String getLineOrientation() {
        return ICUResourceTableAccess.getTableString(ICUData.ICU_BASE_NAME, this, "layout", "lines", "lines");
    }

    public static final class Type {
        private Type() {
        }
    }

    public static ULocale acceptLanguage(String str, ULocale[] uLocaleArr, boolean[] zArr) {
        if (zArr != null) {
            zArr[0] = true;
        }
        try {
            LocalePriorityList localePriorityListBuild = LocalePriorityList.add(str).build();
            LocaleMatcher.Builder builder = LocaleMatcher.builder();
            for (ULocale uLocale : uLocaleArr) {
                builder.addSupportedULocale(uLocale);
            }
            LocaleMatcher.Result bestMatchResult = builder.build().getBestMatchResult(localePriorityListBuild);
            if (bestMatchResult.getDesiredIndex() >= 0) {
                if (zArr != null && bestMatchResult.getDesiredULocale().equals(bestMatchResult.getSupportedULocale())) {
                    zArr[0] = false;
                }
                return bestMatchResult.getSupportedULocale();
            }
        } catch (IllegalArgumentException unused) {
        }
        return null;
    }

    public static ULocale acceptLanguage(ULocale[] uLocaleArr, ULocale[] uLocaleArr2, boolean[] zArr) {
        LocaleMatcher.Result bestMatchResult;
        if (zArr != null) {
            zArr[0] = true;
        }
        LocaleMatcher.Builder builder = LocaleMatcher.builder();
        for (ULocale uLocale : uLocaleArr2) {
            builder.addSupportedULocale(uLocale);
        }
        LocaleMatcher localeMatcherBuild = builder.build();
        if (uLocaleArr.length == 1) {
            bestMatchResult = localeMatcherBuild.getBestMatchResult(uLocaleArr[0]);
        } else {
            bestMatchResult = localeMatcherBuild.getBestMatchResult(Arrays.asList(uLocaleArr));
        }
        if (bestMatchResult.getDesiredIndex() < 0) {
            return null;
        }
        if (zArr != null && bestMatchResult.getDesiredULocale().equals(bestMatchResult.getSupportedULocale())) {
            zArr[0] = false;
        }
        return bestMatchResult.getSupportedULocale();
    }

    public static ULocale acceptLanguage(String str, boolean[] zArr) {
        return acceptLanguage(str, getAvailableLocales(), zArr);
    }

    public static ULocale acceptLanguage(ULocale[] uLocaleArr, boolean[] zArr) {
        return acceptLanguage(uLocaleArr, getAvailableLocales(), zArr);
    }

    public static ULocale addLikelySubtags(ULocale uLocale) {
        String[] strArr = new String[3];
        int tagString = parseTagString(uLocale.localeID, strArr);
        String strCreateLikelySubtagsString = createLikelySubtagsString(strArr[0], strArr[1], strArr[2], tagString < uLocale.localeID.length() ? uLocale.localeID.substring(tagString) : null);
        return strCreateLikelySubtagsString == null ? uLocale : new ULocale(strCreateLikelySubtagsString);
    }

    public static ULocale minimizeSubtags(ULocale uLocale) {
        return minimizeSubtags(uLocale, Minimize.FAVOR_REGION);
    }

    @Deprecated
    public static ULocale minimizeSubtags(ULocale uLocale, Minimize minimize) {
        String[] strArr = new String[3];
        int tagString = parseTagString(uLocale.localeID, strArr);
        String str = strArr[0];
        String str2 = strArr[1];
        String str3 = strArr[2];
        String strSubstring = tagString < uLocale.localeID.length() ? uLocale.localeID.substring(tagString) : null;
        String strCreateLikelySubtagsString = createLikelySubtagsString(str, str2, str3, null);
        if (isEmptyString(strCreateLikelySubtagsString)) {
            return uLocale;
        }
        if (createLikelySubtagsString(str, null, null, null).equals(strCreateLikelySubtagsString)) {
            return new ULocale(createTagString(str, null, null, strSubstring));
        }
        if (minimize == Minimize.FAVOR_REGION) {
            if (str3.length() != 0 && createLikelySubtagsString(str, null, str3, null).equals(strCreateLikelySubtagsString)) {
                return new ULocale(createTagString(str, null, str3, strSubstring));
            }
            if (str2.length() != 0 && createLikelySubtagsString(str, str2, null, null).equals(strCreateLikelySubtagsString)) {
                return new ULocale(createTagString(str, str2, null, strSubstring));
            }
        } else {
            if (str2.length() != 0 && createLikelySubtagsString(str, str2, null, null).equals(strCreateLikelySubtagsString)) {
                return new ULocale(createTagString(str, str2, null, strSubstring));
            }
            if (str3.length() != 0 && createLikelySubtagsString(str, null, str3, null).equals(strCreateLikelySubtagsString)) {
                return new ULocale(createTagString(str, null, str3, strSubstring));
            }
        }
        return uLocale;
    }

    private static boolean isEmptyString(String str) {
        return str == null || str.length() == 0;
    }

    private static void appendTag(String str, StringBuilder sb) {
        if (sb.length() != 0) {
            sb.append(UNDERSCORE);
        }
        sb.append(str);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00a7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.String createTagString(java.lang.String r4, java.lang.String r5, java.lang.String r6, java.lang.String r7, java.lang.String r8) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            boolean r1 = isEmptyString(r4)
            if (r1 != 0) goto Lf
            appendTag(r4, r0)
            goto L1b
        Lf:
            boolean r4 = isEmptyString(r8)
            java.lang.String r1 = "und"
            if (r4 == 0) goto L1d
            appendTag(r1, r0)
        L1b:
            r4 = 0
            goto L30
        L1d:
            com.ibm.icu.impl.LocaleIDParser r4 = new com.ibm.icu.impl.LocaleIDParser
            r4.<init>(r8)
            java.lang.String r2 = r4.getLanguage()
            boolean r3 = isEmptyString(r2)
            if (r3 != 0) goto L2d
            r1 = r2
        L2d:
            appendTag(r1, r0)
        L30:
            boolean r1 = isEmptyString(r5)
            if (r1 != 0) goto L3a
            appendTag(r5, r0)
            goto L54
        L3a:
            boolean r5 = isEmptyString(r8)
            if (r5 != 0) goto L54
            if (r4 != 0) goto L47
            com.ibm.icu.impl.LocaleIDParser r4 = new com.ibm.icu.impl.LocaleIDParser
            r4.<init>(r8)
        L47:
            java.lang.String r5 = r4.getScript()
            boolean r1 = isEmptyString(r5)
            if (r1 != 0) goto L54
            appendTag(r5, r0)
        L54:
            boolean r5 = isEmptyString(r6)
            r1 = 0
            r2 = 1
            if (r5 != 0) goto L61
            appendTag(r6, r0)
        L5f:
            r4 = 1
            goto L7d
        L61:
            boolean r5 = isEmptyString(r8)
            if (r5 != 0) goto L7c
            if (r4 != 0) goto L6e
            com.ibm.icu.impl.LocaleIDParser r4 = new com.ibm.icu.impl.LocaleIDParser
            r4.<init>(r8)
        L6e:
            java.lang.String r4 = r4.getCountry()
            boolean r5 = isEmptyString(r4)
            if (r5 != 0) goto L7c
            appendTag(r4, r0)
            goto L5f
        L7c:
            r4 = 0
        L7d:
            if (r7 == 0) goto Laf
            int r5 = r7.length()
            if (r5 <= r2) goto Laf
            char r5 = r7.charAt(r1)
            r6 = 2
            r8 = 95
            if (r5 != r8) goto L96
            char r5 = r7.charAt(r2)
            if (r5 != r8) goto L97
            r1 = 2
            goto L97
        L96:
            r1 = 1
        L97:
            if (r4 == 0) goto La7
            if (r1 != r6) goto La3
            java.lang.String r4 = r7.substring(r2)
            r0.append(r4)
            goto Laf
        La3:
            r0.append(r7)
            goto Laf
        La7:
            if (r1 != r2) goto Lac
            r0.append(r8)
        Lac:
            r0.append(r7)
        Laf:
            java.lang.String r4 = r0.toString()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.ULocale.createTagString(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String):java.lang.String");
    }

    static String createTagString(String str, String str2, String str3, String str4) {
        return createTagString(str, str2, str3, str4, null);
    }

    private static int parseTagString(String str, String[] strArr) {
        LocaleIDParser localeIDParser = new LocaleIDParser(str);
        String language = localeIDParser.getLanguage();
        String script = localeIDParser.getScript();
        String country = localeIDParser.getCountry();
        if (isEmptyString(language)) {
            strArr[0] = UNDEFINED_LANGUAGE;
        } else {
            strArr[0] = language;
        }
        if (script.equals(UNDEFINED_SCRIPT)) {
            strArr[1] = "";
        } else {
            strArr[1] = script;
        }
        if (country.equals(UNDEFINED_REGION)) {
            strArr[2] = "";
        } else {
            strArr[2] = country;
        }
        String variant = localeIDParser.getVariant();
        if (!isEmptyString(variant)) {
            int iIndexOf = str.indexOf(variant);
            return iIndexOf > 0 ? iIndexOf - 1 : iIndexOf;
        }
        int iIndexOf2 = str.indexOf(64);
        return iIndexOf2 == -1 ? str.length() : iIndexOf2;
    }

    private static String lookupLikelySubtags(String str) {
        try {
            return UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "likelySubtags").getString(str);
        } catch (MissingResourceException unused) {
            return null;
        }
    }

    private static String createLikelySubtagsString(String str, String str2, String str3, String str4) {
        String strLookupLikelySubtags;
        String strLookupLikelySubtags2;
        String strLookupLikelySubtags3;
        if (!isEmptyString(str2) && !isEmptyString(str3) && (strLookupLikelySubtags3 = lookupLikelySubtags(createTagString(str, str2, str3, null))) != null) {
            return createTagString(null, null, null, str4, strLookupLikelySubtags3);
        }
        if (!isEmptyString(str2) && (strLookupLikelySubtags2 = lookupLikelySubtags(createTagString(str, str2, null, null))) != null) {
            return createTagString(null, null, str3, str4, strLookupLikelySubtags2);
        }
        if (!isEmptyString(str3) && (strLookupLikelySubtags = lookupLikelySubtags(createTagString(str, null, str3, null))) != null) {
            return createTagString(null, str2, null, str4, strLookupLikelySubtags);
        }
        String strLookupLikelySubtags4 = lookupLikelySubtags(createTagString(str, null, null, null));
        if (strLookupLikelySubtags4 != null) {
            return createTagString(null, str2, str3, str4, strLookupLikelySubtags4);
        }
        return null;
    }

    public String getExtension(char c) {
        if (!LocaleExtensions.isValidKey(c)) {
            throw new IllegalArgumentException("Invalid extension key: " + c);
        }
        return extensions().getExtensionValue(Character.valueOf(c));
    }

    public Set<Character> getExtensionKeys() {
        return extensions().getKeys();
    }

    public Set<String> getUnicodeLocaleAttributes() {
        return extensions().getUnicodeLocaleAttributes();
    }

    public String getUnicodeLocaleType(String str) {
        if (!LocaleExtensions.isValidUnicodeLocaleKey(str)) {
            throw new IllegalArgumentException("Invalid Unicode locale key: " + str);
        }
        return extensions().getUnicodeLocaleType(str);
    }

    public Set<String> getUnicodeLocaleKeys() {
        return extensions().getUnicodeLocaleKeys();
    }

    public String toLanguageTag() {
        BaseLocale baseLocaleBase = base();
        LocaleExtensions localeExtensionsExtensions = extensions();
        if (baseLocaleBase.getVariant().equalsIgnoreCase("POSIX")) {
            baseLocaleBase = BaseLocale.getInstance(baseLocaleBase.getLanguage(), baseLocaleBase.getScript(), baseLocaleBase.getRegion(), "");
            if (localeExtensionsExtensions.getUnicodeLocaleType("va") == null) {
                InternalLocaleBuilder internalLocaleBuilder = new InternalLocaleBuilder();
                try {
                    internalLocaleBuilder.setLocale(BaseLocale.ROOT, localeExtensionsExtensions);
                    internalLocaleBuilder.setUnicodeLocaleKeyword("va", "posix");
                    localeExtensionsExtensions = internalLocaleBuilder.getLocaleExtensions();
                } catch (LocaleSyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        LanguageTag locale = LanguageTag.parseLocale(baseLocaleBase, localeExtensionsExtensions);
        StringBuilder sb = new StringBuilder();
        String language = locale.getLanguage();
        if (language.length() > 0) {
            sb.append(LanguageTag.canonicalizeLanguage(language));
        }
        String script = locale.getScript();
        if (script.length() > 0) {
            sb.append(LanguageTag.SEP);
            sb.append(LanguageTag.canonicalizeScript(script));
        }
        String region = locale.getRegion();
        if (region.length() > 0) {
            sb.append(LanguageTag.SEP);
            sb.append(LanguageTag.canonicalizeRegion(region));
        }
        ArrayList arrayList = new ArrayList(locale.getVariants());
        Collections.sort(arrayList);
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            String str = (String) it2.next();
            sb.append(LanguageTag.SEP);
            sb.append(LanguageTag.canonicalizeVariant(str));
        }
        for (String str2 : locale.getExtensions()) {
            sb.append(LanguageTag.SEP);
            sb.append(LanguageTag.canonicalizeExtension(str2));
        }
        String privateuse = locale.getPrivateuse();
        if (privateuse.length() > 0) {
            if (sb.length() == 0) {
                sb.append(UNDEFINED_LANGUAGE);
            }
            sb.append(LanguageTag.SEP);
            sb.append(LanguageTag.PRIVATEUSE);
            sb.append(LanguageTag.SEP);
            sb.append(LanguageTag.canonicalizePrivateuse(privateuse));
        }
        return sb.toString();
    }

    public static ULocale forLanguageTag(String str) {
        LanguageTag languageTag = LanguageTag.parse(str, null);
        InternalLocaleBuilder internalLocaleBuilder = new InternalLocaleBuilder();
        internalLocaleBuilder.setLanguageTag(languageTag);
        return getInstance(internalLocaleBuilder.getBaseLocale(), internalLocaleBuilder.getLocaleExtensions());
    }

    public static String toUnicodeLocaleKey(String str) {
        String bcpKey = KeyTypeData.toBcpKey(str);
        return (bcpKey == null && UnicodeLocaleExtension.isKey(str)) ? AsciiUtil.toLowerString(str) : bcpKey;
    }

    public static String toUnicodeLocaleType(String str, String str2) {
        String bcpType = KeyTypeData.toBcpType(str, str2, null, null);
        return (bcpType == null && UnicodeLocaleExtension.isType(str2)) ? AsciiUtil.toLowerString(str2) : bcpType;
    }

    public static String toLegacyKey(String str) {
        String legacyKey = KeyTypeData.toLegacyKey(str);
        return (legacyKey == null && str.matches("[0-9a-zA-Z]+")) ? AsciiUtil.toLowerString(str) : legacyKey;
    }

    public static String toLegacyType(String str, String str2) {
        String legacyType = KeyTypeData.toLegacyType(str, str2, null, null);
        return (legacyType == null && str2.matches("[0-9a-zA-Z]+([_/\\-][0-9a-zA-Z]+)*")) ? AsciiUtil.toLowerString(str2) : legacyType;
    }

    public static final class Builder {
        private final InternalLocaleBuilder _locbld = new InternalLocaleBuilder();

        public Builder setLocale(ULocale uLocale) {
            try {
                this._locbld.setLocale(uLocale.base(), uLocale.extensions());
                return this;
            } catch (LocaleSyntaxException e) {
                throw new IllformedLocaleException(e.getMessage(), e.getErrorIndex());
            }
        }

        public Builder setLanguageTag(String str) {
            ParseStatus parseStatus = new ParseStatus();
            LanguageTag languageTag = LanguageTag.parse(str, parseStatus);
            if (parseStatus.isError()) {
                throw new IllformedLocaleException(parseStatus.getErrorMessage(), parseStatus.getErrorIndex());
            }
            this._locbld.setLanguageTag(languageTag);
            return this;
        }

        public Builder setLanguage(String str) {
            try {
                this._locbld.setLanguage(str);
                return this;
            } catch (LocaleSyntaxException e) {
                throw new IllformedLocaleException(e.getMessage(), e.getErrorIndex());
            }
        }

        public Builder setScript(String str) {
            try {
                this._locbld.setScript(str);
                return this;
            } catch (LocaleSyntaxException e) {
                throw new IllformedLocaleException(e.getMessage(), e.getErrorIndex());
            }
        }

        public Builder setRegion(String str) {
            try {
                this._locbld.setRegion(str);
                return this;
            } catch (LocaleSyntaxException e) {
                throw new IllformedLocaleException(e.getMessage(), e.getErrorIndex());
            }
        }

        public Builder setVariant(String str) {
            try {
                this._locbld.setVariant(str);
                return this;
            } catch (LocaleSyntaxException e) {
                throw new IllformedLocaleException(e.getMessage(), e.getErrorIndex());
            }
        }

        public Builder setExtension(char c, String str) {
            try {
                this._locbld.setExtension(c, str);
                return this;
            } catch (LocaleSyntaxException e) {
                throw new IllformedLocaleException(e.getMessage(), e.getErrorIndex());
            }
        }

        public Builder setUnicodeLocaleKeyword(String str, String str2) {
            try {
                this._locbld.setUnicodeLocaleKeyword(str, str2);
                return this;
            } catch (LocaleSyntaxException e) {
                throw new IllformedLocaleException(e.getMessage(), e.getErrorIndex());
            }
        }

        public Builder addUnicodeLocaleAttribute(String str) {
            try {
                this._locbld.addUnicodeLocaleAttribute(str);
                return this;
            } catch (LocaleSyntaxException e) {
                throw new IllformedLocaleException(e.getMessage(), e.getErrorIndex());
            }
        }

        public Builder removeUnicodeLocaleAttribute(String str) {
            try {
                this._locbld.removeUnicodeLocaleAttribute(str);
                return this;
            } catch (LocaleSyntaxException e) {
                throw new IllformedLocaleException(e.getMessage(), e.getErrorIndex());
            }
        }

        public Builder clear() {
            this._locbld.clear();
            return this;
        }

        public Builder clearExtensions() {
            this._locbld.clearExtensions();
            return this;
        }

        public ULocale build() {
            return ULocale.getInstance(this._locbld.getBaseLocale(), this._locbld.getLocaleExtensions());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ULocale getInstance(BaseLocale baseLocale, LocaleExtensions localeExtensions) {
        String strLscvToID = lscvToID(baseLocale.getLanguage(), baseLocale.getScript(), baseLocale.getRegion(), baseLocale.getVariant());
        Set<Character> keys = localeExtensions.getKeys();
        if (!keys.isEmpty()) {
            TreeMap treeMap = new TreeMap();
            for (Character ch : keys) {
                Extension extension = localeExtensions.getExtension(ch);
                if (extension instanceof UnicodeLocaleExtension) {
                    UnicodeLocaleExtension unicodeLocaleExtension = (UnicodeLocaleExtension) extension;
                    for (String str : unicodeLocaleExtension.getUnicodeLocaleKeys()) {
                        String unicodeLocaleType = unicodeLocaleExtension.getUnicodeLocaleType(str);
                        String legacyKey = toLegacyKey(str);
                        if (unicodeLocaleType.length() == 0) {
                            unicodeLocaleType = "yes";
                        }
                        String legacyType = toLegacyType(str, unicodeLocaleType);
                        if (legacyKey.equals("va") && legacyType.equals("posix") && baseLocale.getVariant().length() == 0) {
                            strLscvToID = strLscvToID + "_POSIX";
                        } else {
                            treeMap.put(legacyKey, legacyType);
                        }
                    }
                    Set<String> unicodeLocaleAttributes = unicodeLocaleExtension.getUnicodeLocaleAttributes();
                    if (unicodeLocaleAttributes.size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (String str2 : unicodeLocaleAttributes) {
                            if (sb.length() > 0) {
                                sb.append(Soundex.SILENT_MARKER);
                            }
                            sb.append(str2);
                        }
                        treeMap.put(LOCALE_ATTRIBUTE_KEY, sb.toString());
                    }
                } else {
                    treeMap.put(String.valueOf(ch), extension.getValue());
                }
            }
            if (!treeMap.isEmpty()) {
                StringBuilder sb2 = new StringBuilder(strLscvToID);
                sb2.append("@");
                boolean z = false;
                for (Map.Entry entry : treeMap.entrySet()) {
                    if (z) {
                        sb2.append(";");
                    } else {
                        z = true;
                    }
                    sb2.append((String) entry.getKey());
                    sb2.append(ContainerUtils.KEY_VALUE_DELIMITER);
                    sb2.append((String) entry.getValue());
                }
                strLscvToID = sb2.toString();
            }
        }
        return new ULocale(strLscvToID);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BaseLocale base() {
        String variant;
        String script;
        String country;
        if (this.baseLocale == null) {
            String str = "";
            if (equals(ROOT)) {
                variant = "";
                script = variant;
                country = script;
            } else {
                LocaleIDParser localeIDParser = new LocaleIDParser(this.localeID);
                String language = localeIDParser.getLanguage();
                script = localeIDParser.getScript();
                country = localeIDParser.getCountry();
                variant = localeIDParser.getVariant();
                str = language;
            }
            this.baseLocale = BaseLocale.getInstance(str, script, country, variant);
        }
        return this.baseLocale;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LocaleExtensions extensions() {
        if (this.extensions == null) {
            Iterator<String> keywords = getKeywords();
            if (keywords == null) {
                this.extensions = LocaleExtensions.EMPTY_EXTENSIONS;
            } else {
                InternalLocaleBuilder internalLocaleBuilder = new InternalLocaleBuilder();
                while (keywords.hasNext()) {
                    String next = keywords.next();
                    if (next.equals(LOCALE_ATTRIBUTE_KEY)) {
                        for (String str : getKeywordValue(next).split("[-_]")) {
                            try {
                                internalLocaleBuilder.addUnicodeLocaleAttribute(str);
                            } catch (LocaleSyntaxException unused) {
                            }
                        }
                    } else if (next.length() >= 2) {
                        String unicodeLocaleKey = toUnicodeLocaleKey(next);
                        String unicodeLocaleType = toUnicodeLocaleType(next, getKeywordValue(next));
                        if (unicodeLocaleKey != null && unicodeLocaleType != null) {
                            try {
                                internalLocaleBuilder.setUnicodeLocaleKeyword(unicodeLocaleKey, unicodeLocaleType);
                            } catch (LocaleSyntaxException unused2) {
                            }
                        }
                    } else if (next.length() == 1 && next.charAt(0) != 'u') {
                        internalLocaleBuilder.setExtension(next.charAt(0), getKeywordValue(next).replace(BaseLocale.SEP, LanguageTag.SEP));
                    }
                }
                this.extensions = internalLocaleBuilder.getLocaleExtensions();
            }
        }
        return this.extensions;
    }

    private static final class JDKLocaleHelper {
        private static Object eDISPLAY = null;
        private static Object eFORMAT = null;
        private static boolean hasLocaleCategories = false;
        private static Method mGetDefault;
        private static Method mSetDefault;

        static {
            Class<?> cls;
            try {
                Class<?>[] declaredClasses = Locale.class.getDeclaredClasses();
                int length = declaredClasses.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        cls = null;
                        break;
                    }
                    cls = declaredClasses[i];
                    if (cls.getName().equals("java.util.Locale$Category")) {
                        break;
                    } else {
                        i++;
                    }
                }
                if (cls == null) {
                    return;
                }
                mGetDefault = Locale.class.getDeclaredMethod("getDefault", cls);
                mSetDefault = Locale.class.getDeclaredMethod("setDefault", cls, Locale.class);
                Method method = cls.getMethod("name", (Class[]) null);
                for (Object obj : cls.getEnumConstants()) {
                    String str = (String) method.invoke(obj, (Object[]) null);
                    if (str.equals("DISPLAY")) {
                        eDISPLAY = obj;
                    } else if (str.equals("FORMAT")) {
                        eFORMAT = obj;
                    }
                }
                if (eDISPLAY != null && eFORMAT != null) {
                    hasLocaleCategories = true;
                }
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException unused) {
            }
        }

        private JDKLocaleHelper() {
        }

        public static boolean hasLocaleCategories() {
            return hasLocaleCategories;
        }

        public static ULocale toULocale(Locale locale) {
            TreeMap treeMap;
            TreeSet<String> treeSet;
            String language = locale.getLanguage();
            String country = locale.getCountry();
            String variant = locale.getVariant();
            String script = locale.getScript();
            Set<Character> extensionKeys = locale.getExtensionKeys();
            if (extensionKeys.isEmpty()) {
                treeMap = null;
                treeSet = null;
            } else {
                treeMap = null;
                treeSet = null;
                for (Character ch : extensionKeys) {
                    if (ch.charValue() == 'u') {
                        Set<String> unicodeLocaleAttributes = locale.getUnicodeLocaleAttributes();
                        if (!unicodeLocaleAttributes.isEmpty()) {
                            treeSet = new TreeSet();
                            Iterator<String> it2 = unicodeLocaleAttributes.iterator();
                            while (it2.hasNext()) {
                                treeSet.add(it2.next());
                            }
                        }
                        for (String str : locale.getUnicodeLocaleKeys()) {
                            String unicodeLocaleType = locale.getUnicodeLocaleType(str);
                            if (unicodeLocaleType != null) {
                                if (str.equals("va")) {
                                    variant = variant.length() == 0 ? unicodeLocaleType : unicodeLocaleType + BaseLocale.SEP + variant;
                                } else {
                                    if (treeMap == null) {
                                        treeMap = new TreeMap();
                                    }
                                    treeMap.put(str, unicodeLocaleType);
                                }
                            }
                        }
                    } else {
                        String extension = locale.getExtension(ch.charValue());
                        if (extension != null) {
                            if (treeMap == null) {
                                treeMap = new TreeMap();
                            }
                            treeMap.put(String.valueOf(ch), extension);
                        }
                    }
                }
            }
            if (language.equals("no") && country.equals("NO") && variant.equals("NY")) {
                language = "nn";
                variant = "";
            }
            StringBuilder sb = new StringBuilder(language);
            if (script.length() > 0) {
                sb.append(ULocale.UNDERSCORE);
                sb.append(script);
            }
            if (country.length() > 0) {
                sb.append(ULocale.UNDERSCORE);
                sb.append(country);
            }
            if (variant.length() > 0) {
                if (country.length() == 0) {
                    sb.append(ULocale.UNDERSCORE);
                }
                sb.append(ULocale.UNDERSCORE);
                sb.append(variant);
            }
            if (treeSet != null) {
                StringBuilder sb2 = new StringBuilder();
                for (String str2 : treeSet) {
                    if (sb2.length() != 0) {
                        sb2.append(Soundex.SILENT_MARKER);
                    }
                    sb2.append(str2);
                }
                if (treeMap == null) {
                    treeMap = new TreeMap();
                }
                treeMap.put(ULocale.LOCALE_ATTRIBUTE_KEY, sb2.toString());
            }
            if (treeMap != null) {
                sb.append('@');
                boolean z = false;
                for (Map.Entry entry : treeMap.entrySet()) {
                    String legacyKey = (String) entry.getKey();
                    String legacyType = (String) entry.getValue();
                    if (legacyKey.length() != 1) {
                        legacyKey = ULocale.toLegacyKey(legacyKey);
                        if (legacyType.length() == 0) {
                            legacyType = "yes";
                        }
                        legacyType = ULocale.toLegacyType(legacyKey, legacyType);
                    }
                    if (z) {
                        sb.append(';');
                    } else {
                        z = true;
                    }
                    sb.append(legacyKey);
                    sb.append('=');
                    sb.append(legacyType);
                }
            }
            return new ULocale(ULocale.getName(sb.toString()), locale);
        }

        public static Locale toLocale(ULocale uLocale) {
            Locale localeForLanguageTag = (uLocale.getScript().length() > 0 || uLocale.getName().contains("@")) ? Locale.forLanguageTag(AsciiUtil.toUpperString(uLocale.toLanguageTag())) : null;
            return localeForLanguageTag == null ? new Locale(uLocale.getLanguage(), uLocale.getCountry(), uLocale.getVariant()) : localeForLanguageTag;
        }

        public static Locale getDefault(Category category) {
            Object obj;
            if (hasLocaleCategories) {
                int i = AnonymousClass3.$SwitchMap$com$ibm$icu$util$ULocale$Category[category.ordinal()];
                if (i == 1) {
                    obj = eDISPLAY;
                } else {
                    obj = i != 2 ? null : eFORMAT;
                }
                if (obj != null) {
                    try {
                        return (Locale) mGetDefault.invoke(null, obj);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused) {
                    }
                }
            }
            return Locale.getDefault();
        }

        public static void setDefault(Category category, Locale locale) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            Object obj;
            if (hasLocaleCategories) {
                int i = AnonymousClass3.$SwitchMap$com$ibm$icu$util$ULocale$Category[category.ordinal()];
                if (i == 1) {
                    obj = eDISPLAY;
                } else {
                    obj = i != 2 ? null : eFORMAT;
                }
                if (obj != null) {
                    try {
                        mSetDefault.invoke(null, obj, locale);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused) {
                    }
                }
            }
        }
    }

    /* renamed from: com.ibm.icu.util.ULocale$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$util$ULocale$Category;

        static {
            int[] iArr = new int[Category.values().length];
            $SwitchMap$com$ibm$icu$util$ULocale$Category = iArr;
            try {
                iArr[Category.DISPLAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$util$ULocale$Category[Category.FORMAT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }
}
