package com.ibm.icu.util;

import com.google.zxing.client.result.ExpandedProductParsedResult;
import com.ibm.icu.impl.locale.BaseLocale;
import com.ibm.icu.text.BreakIterator;
import com.ibm.icu.text.Collator;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.NumberFormat;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.util.TimeZones;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes2.dex */
public class GlobalizationPreferences implements Freezable<GlobalizationPreferences> {
    public static final int BI_CHARACTER = 0;
    private static final int BI_LIMIT = 5;
    public static final int BI_LINE = 2;
    public static final int BI_SENTENCE = 3;
    public static final int BI_TITLE = 4;
    public static final int BI_WORD = 1;
    public static final int DF_FULL = 0;
    private static final int DF_LIMIT = 5;
    public static final int DF_LONG = 1;
    public static final int DF_MEDIUM = 2;
    public static final int DF_NONE = 4;
    public static final int DF_SHORT = 3;
    public static final int ID_CURRENCY = 7;
    public static final int ID_CURRENCY_SYMBOL = 8;
    public static final int ID_KEYWORD = 5;
    public static final int ID_KEYWORD_VALUE = 6;
    public static final int ID_LANGUAGE = 1;
    public static final int ID_LOCALE = 0;
    public static final int ID_SCRIPT = 2;
    public static final int ID_TERRITORY = 3;
    public static final int ID_TIMEZONE = 9;
    public static final int ID_VARIANT = 4;
    public static final int NF_CURRENCY = 1;
    public static final int NF_INTEGER = 4;
    private static final int NF_LIMIT = 5;
    public static final int NF_NUMBER = 0;
    public static final int NF_PERCENT = 2;
    public static final int NF_SCIENTIFIC = 3;
    private static final int TYPE_BREAKITERATOR = 5;
    private static final int TYPE_CALENDAR = 1;
    private static final int TYPE_COLLATOR = 4;
    private static final int TYPE_DATEFORMAT = 2;
    private static final int TYPE_GENERIC = 0;
    private static final int TYPE_LIMIT = 6;
    private static final int TYPE_NUMBERFORMAT = 3;
    private static final HashMap<ULocale, BitSet> available_locales = new HashMap<>();
    private static final String[][] language_territory_hack;
    private static final Map<String, String> language_territory_hack_map;
    static final String[][] territory_tzid_hack;
    static final Map<String, String> territory_tzid_hack_map;
    private BreakIterator[] breakIterators;
    private Calendar calendar;
    private Collator collator;
    private Currency currency;
    private DateFormat[][] dateFormats;
    private volatile boolean frozen;
    private List<ULocale> implicitLocales;
    private List<ULocale> locales;
    private NumberFormat[] numberFormats;
    private String territory;
    private TimeZone timezone;

    public GlobalizationPreferences() {
        reset();
    }

    public GlobalizationPreferences setLocales(List<ULocale> list) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        this.locales = processLocales(list);
        return this;
    }

    public List<ULocale> getLocales() {
        if (this.locales == null) {
            return guessLocales();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.locales);
        return arrayList;
    }

    public ULocale getLocale(int i) {
        List<ULocale> listGuessLocales = this.locales;
        if (listGuessLocales == null) {
            listGuessLocales = guessLocales();
        }
        if (i < 0 || i >= listGuessLocales.size()) {
            return null;
        }
        return listGuessLocales.get(i);
    }

    public GlobalizationPreferences setLocales(ULocale[] uLocaleArr) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        return setLocales(Arrays.asList(uLocaleArr));
    }

    public GlobalizationPreferences setLocale(ULocale uLocale) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        return setLocales(new ULocale[]{uLocale});
    }

    public GlobalizationPreferences setLocales(String str) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        this.locales = processLocales(new ArrayList(LocalePriorityList.add(str).build().getULocales()));
        return this;
    }

    public ResourceBundle getResourceBundle(String str) {
        return getResourceBundle(str, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v5, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.lang.Object, java.lang.String] */
    public ResourceBundle getResourceBundle(String str, ClassLoader classLoader) {
        List<ULocale> locales = getLocales();
        int i = 0;
        ?? name = 0;
        UResourceBundle bundleInstance = null;
        UResourceBundle uResourceBundle = null;
        while (true) {
            if (i >= locales.size()) {
                bundleInstance = uResourceBundle;
                break;
            }
            ?? string = locales.get(i).toString();
            if (name != 0 && string.equals(name)) {
                break;
            }
            if (classLoader == null) {
                try {
                    bundleInstance = UResourceBundle.getBundleInstance(str, (String) string);
                } catch (MissingResourceException unused) {
                    name = 0;
                }
            } else {
                bundleInstance = UResourceBundle.getBundleInstance(str, (String) string, classLoader);
            }
            if (bundleInstance != null) {
                name = bundleInstance.getULocale().getName();
                if (name.equals(string)) {
                    break;
                }
                if (uResourceBundle == null) {
                    uResourceBundle = bundleInstance;
                }
            } else {
                continue;
            }
            i++;
            name = name;
        }
        if (bundleInstance != null) {
            return bundleInstance;
        }
        throw new MissingResourceException("Can't find bundle for base name " + str, str, "");
    }

    public GlobalizationPreferences setTerritory(String str) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        this.territory = str;
        return this;
    }

    public String getTerritory() {
        String str = this.territory;
        return str == null ? guessTerritory() : str;
    }

    public GlobalizationPreferences setCurrency(Currency currency) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        this.currency = currency;
        return this;
    }

    public Currency getCurrency() {
        Currency currency = this.currency;
        return currency == null ? guessCurrency() : currency;
    }

    public GlobalizationPreferences setCalendar(Calendar calendar) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        this.calendar = (Calendar) calendar.clone();
        return this;
    }

    public Calendar getCalendar() {
        Calendar calendar = this.calendar;
        if (calendar == null) {
            return guessCalendar();
        }
        Calendar calendar2 = (Calendar) calendar.clone();
        calendar2.setTimeZone(getTimeZone());
        calendar2.setTimeInMillis(System.currentTimeMillis());
        return calendar2;
    }

    public GlobalizationPreferences setTimeZone(TimeZone timeZone) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        this.timezone = (TimeZone) timeZone.clone();
        return this;
    }

    public TimeZone getTimeZone() {
        TimeZone timeZone = this.timezone;
        if (timeZone == null) {
            return guessTimeZone();
        }
        return timeZone.cloneAsThawed();
    }

    public Collator getCollator() {
        Collator collator = this.collator;
        if (collator == null) {
            return guessCollator();
        }
        try {
            return (Collator) collator.clone();
        } catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException("Error in cloning collator", e);
        }
    }

    public GlobalizationPreferences setCollator(Collator collator) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        try {
            this.collator = (Collator) collator.clone();
            return this;
        } catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException("Error in cloning collator", e);
        }
    }

    public BreakIterator getBreakIterator(int i) {
        if (i < 0 || i >= 5) {
            throw new IllegalArgumentException("Illegal break iterator type");
        }
        BreakIterator[] breakIteratorArr = this.breakIterators;
        if (breakIteratorArr == null || breakIteratorArr[i] == null) {
            return guessBreakIterator(i);
        }
        return (BreakIterator) breakIteratorArr[i].clone();
    }

    public GlobalizationPreferences setBreakIterator(int i, BreakIterator breakIterator) {
        if (i < 0 || i >= 5) {
            throw new IllegalArgumentException("Illegal break iterator type");
        }
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        if (this.breakIterators == null) {
            this.breakIterators = new BreakIterator[5];
        }
        this.breakIterators[i] = (BreakIterator) breakIterator.clone();
        return this;
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x011f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0009 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String getDisplayName(java.lang.String r10, int r11) {
        /*
            Method dump skipped, instructions count: 312
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.util.GlobalizationPreferences.getDisplayName(java.lang.String, int):java.lang.String");
    }

    public GlobalizationPreferences setDateFormat(int i, int i2, DateFormat dateFormat) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        if (this.dateFormats == null) {
            this.dateFormats = (DateFormat[][]) Array.newInstance((Class<?>) DateFormat.class, 5, 5);
        }
        this.dateFormats[i][i2] = (DateFormat) dateFormat.clone();
        return this;
    }

    public DateFormat getDateFormat(int i, int i2) {
        if ((i == 4 && i2 == 4) || i < 0 || i >= 5 || i2 < 0 || i2 >= 5) {
            throw new IllegalArgumentException("Illegal date format style arguments");
        }
        DateFormat[][] dateFormatArr = this.dateFormats;
        DateFormat dateFormat = dateFormatArr != null ? dateFormatArr[i][i2] : null;
        if (dateFormat != null) {
            DateFormat dateFormat2 = (DateFormat) dateFormat.clone();
            dateFormat2.setTimeZone(getTimeZone());
            return dateFormat2;
        }
        return guessDateFormat(i, i2);
    }

    public NumberFormat getNumberFormat(int i) {
        if (i < 0 || i >= 5) {
            throw new IllegalArgumentException("Illegal number format type");
        }
        NumberFormat[] numberFormatArr = this.numberFormats;
        NumberFormat numberFormat = numberFormatArr != null ? numberFormatArr[i] : null;
        if (numberFormat != null) {
            return (NumberFormat) numberFormat.clone();
        }
        return guessNumberFormat(i);
    }

    public GlobalizationPreferences setNumberFormat(int i, NumberFormat numberFormat) {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        if (this.numberFormats == null) {
            this.numberFormats = new NumberFormat[5];
        }
        this.numberFormats[i] = (NumberFormat) numberFormat.clone();
        return this;
    }

    public GlobalizationPreferences reset() {
        if (isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        this.locales = null;
        this.territory = null;
        this.calendar = null;
        this.collator = null;
        this.breakIterators = null;
        this.timezone = null;
        this.currency = null;
        this.dateFormats = (DateFormat[][]) null;
        this.numberFormats = null;
        this.implicitLocales = null;
        return this;
    }

    protected List<ULocale> processLocales(List<ULocale> list) {
        boolean z;
        ArrayList arrayList = new ArrayList();
        Iterator<ULocale> it2 = list.iterator();
        while (true) {
            boolean z2 = false;
            if (!it2.hasNext()) {
                break;
            }
            ULocale next = it2.next();
            String language = next.getLanguage();
            String script = next.getScript();
            String country = next.getCountry();
            String variant = next.getVariant();
            for (int i = 0; i < arrayList.size(); i++) {
                ULocale uLocale = (ULocale) arrayList.get(i);
                if (uLocale.getLanguage().equals(language)) {
                    String script2 = uLocale.getScript();
                    String country2 = uLocale.getCountry();
                    String variant2 = uLocale.getVariant();
                    if (!script2.equals(script)) {
                        if (script2.length() == 0 && country2.length() == 0 && variant2.length() == 0) {
                            arrayList.add(i, next);
                        } else if (script2.length() == 0 && country2.equals(country)) {
                            arrayList.add(i, next);
                        } else if (script.length() == 0 && country.length() > 0 && country2.length() == 0) {
                            arrayList.add(i, next);
                        }
                        z2 = true;
                        break;
                    }
                    if (!country2.equals(country) && country2.length() == 0 && variant2.length() == 0) {
                        arrayList.add(i, next);
                    } else if (!variant2.equals(variant) && variant2.length() == 0) {
                        arrayList.add(i, next);
                    }
                    z2 = true;
                    break;
                }
            }
            if (!z2) {
                arrayList.add(next);
            }
        }
        int i2 = 0;
        while (i2 < arrayList.size()) {
            ULocale fallback = (ULocale) arrayList.get(i2);
            while (true) {
                fallback = fallback.getFallback();
                if (fallback == null || fallback.getLanguage().length() == 0) {
                    break;
                }
                i2++;
                arrayList.add(i2, fallback);
            }
            i2++;
        }
        int i3 = 0;
        while (i3 < arrayList.size() - 1) {
            ULocale uLocale2 = (ULocale) arrayList.get(i3);
            int i4 = i3 + 1;
            int i5 = i4;
            while (true) {
                if (i5 >= arrayList.size()) {
                    z = false;
                    break;
                }
                if (uLocale2.equals(arrayList.get(i5))) {
                    arrayList.remove(i3);
                    z = true;
                    break;
                }
                i5++;
            }
            if (!z) {
                i3 = i4;
            }
        }
        return arrayList;
    }

    protected DateFormat guessDateFormat(int i, int i2) {
        ULocale availableLocale = getAvailableLocale(2);
        if (availableLocale == null) {
            availableLocale = ULocale.ROOT;
        }
        if (i2 == 4) {
            return DateFormat.getDateInstance(getCalendar(), i, availableLocale);
        }
        if (i == 4) {
            return DateFormat.getTimeInstance(getCalendar(), i2, availableLocale);
        }
        return DateFormat.getDateTimeInstance(getCalendar(), i, i2, availableLocale);
    }

    protected NumberFormat guessNumberFormat(int i) {
        ULocale availableLocale = getAvailableLocale(3);
        if (availableLocale == null) {
            availableLocale = ULocale.ROOT;
        }
        if (i == 0) {
            return NumberFormat.getInstance(availableLocale);
        }
        if (i == 1) {
            NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(availableLocale);
            currencyInstance.setCurrency(getCurrency());
            return currencyInstance;
        }
        if (i == 2) {
            return NumberFormat.getPercentInstance(availableLocale);
        }
        if (i == 3) {
            return NumberFormat.getScientificInstance(availableLocale);
        }
        if (i == 4) {
            return NumberFormat.getIntegerInstance(availableLocale);
        }
        throw new IllegalArgumentException("Unknown number format style");
    }

    protected String guessTerritory() {
        Iterator<ULocale> it2 = getLocales().iterator();
        while (it2.hasNext()) {
            String country = it2.next().getCountry();
            if (country.length() != 0) {
                return country;
            }
        }
        ULocale locale = getLocale(0);
        String language = locale.getLanguage();
        String script = locale.getScript();
        String str = null;
        if (script.length() != 0) {
            str = language_territory_hack_map.get(language + BaseLocale.SEP + script);
        }
        if (str == null) {
            str = language_territory_hack_map.get(language);
        }
        return str == null ? "US" : str;
    }

    protected Currency guessCurrency() {
        return Currency.getInstance(new ULocale("und-" + getTerritory()));
    }

    protected List<ULocale> guessLocales() {
        if (this.implicitLocales == null) {
            ArrayList arrayList = new ArrayList(1);
            arrayList.add(ULocale.getDefault());
            this.implicitLocales = processLocales(arrayList);
        }
        return this.implicitLocales;
    }

    protected Collator guessCollator() {
        ULocale availableLocale = getAvailableLocale(4);
        if (availableLocale == null) {
            availableLocale = ULocale.ROOT;
        }
        return Collator.getInstance(availableLocale);
    }

    protected BreakIterator guessBreakIterator(int i) {
        ULocale availableLocale = getAvailableLocale(5);
        if (availableLocale == null) {
            availableLocale = ULocale.ROOT;
        }
        if (i == 0) {
            return BreakIterator.getCharacterInstance(availableLocale);
        }
        if (i == 1) {
            return BreakIterator.getWordInstance(availableLocale);
        }
        if (i == 2) {
            return BreakIterator.getLineInstance(availableLocale);
        }
        if (i == 3) {
            return BreakIterator.getSentenceInstance(availableLocale);
        }
        if (i == 4) {
            return BreakIterator.getTitleInstance(availableLocale);
        }
        throw new IllegalArgumentException("Unknown break iterator type");
    }

    protected TimeZone guessTimeZone() {
        String str = territory_tzid_hack_map.get(getTerritory());
        if (str == null) {
            String[] availableIDs = TimeZone.getAvailableIDs(getTerritory());
            if (availableIDs.length == 0) {
                str = TimeZones.GMT_ID;
            } else {
                int i = 0;
                while (i < availableIDs.length && availableIDs[i].indexOf("/") < 0) {
                    i++;
                }
                str = availableIDs[i <= availableIDs.length ? i : 0];
            }
        }
        return TimeZone.getTimeZone(str);
    }

    protected Calendar guessCalendar() {
        ULocale availableLocale = getAvailableLocale(1);
        if (availableLocale == null) {
            availableLocale = ULocale.US;
        }
        return Calendar.getInstance(getTimeZone(), availableLocale);
    }

    private ULocale getAvailableLocale(int i) {
        List<ULocale> locales = getLocales();
        for (int i2 = 0; i2 < locales.size(); i2++) {
            ULocale uLocale = locales.get(i2);
            if (isAvailableLocale(uLocale, i)) {
                return uLocale;
            }
        }
        return null;
    }

    private boolean isAvailableLocale(ULocale uLocale, int i) {
        BitSet bitSet = available_locales.get(uLocale);
        return bitSet != null && bitSet.get(i);
    }

    static {
        ULocale[] availableLocales = ULocale.getAvailableLocales();
        for (ULocale uLocale : availableLocales) {
            BitSet bitSet = new BitSet(6);
            available_locales.put(uLocale, bitSet);
            bitSet.set(0);
        }
        ULocale[] availableULocales = Calendar.getAvailableULocales();
        for (int i = 0; i < availableULocales.length; i++) {
            BitSet bitSet2 = available_locales.get(availableULocales[i]);
            if (bitSet2 == null) {
                bitSet2 = new BitSet(6);
                available_locales.put(availableLocales[i], bitSet2);
            }
            bitSet2.set(1);
        }
        ULocale[] availableULocales2 = DateFormat.getAvailableULocales();
        for (int i2 = 0; i2 < availableULocales2.length; i2++) {
            BitSet bitSet3 = available_locales.get(availableULocales2[i2]);
            if (bitSet3 == null) {
                bitSet3 = new BitSet(6);
                available_locales.put(availableLocales[i2], bitSet3);
            }
            bitSet3.set(2);
        }
        ULocale[] availableULocales3 = NumberFormat.getAvailableULocales();
        for (int i3 = 0; i3 < availableULocales3.length; i3++) {
            BitSet bitSet4 = available_locales.get(availableULocales3[i3]);
            if (bitSet4 == null) {
                bitSet4 = new BitSet(6);
                available_locales.put(availableLocales[i3], bitSet4);
            }
            bitSet4.set(3);
        }
        ULocale[] availableULocales4 = Collator.getAvailableULocales();
        for (int i4 = 0; i4 < availableULocales4.length; i4++) {
            BitSet bitSet5 = available_locales.get(availableULocales4[i4]);
            if (bitSet5 == null) {
                bitSet5 = new BitSet(6);
                available_locales.put(availableLocales[i4], bitSet5);
            }
            bitSet5.set(4);
        }
        for (ULocale uLocale2 : BreakIterator.getAvailableULocales()) {
            available_locales.get(uLocale2).set(5);
        }
        language_territory_hack_map = new HashMap();
        language_territory_hack = new String[][]{new String[]{"af", "ZA"}, new String[]{"am", "ET"}, new String[]{"ar", "SA"}, new String[]{"as", "IN"}, new String[]{"ay", "PE"}, new String[]{"az", "AZ"}, new String[]{"bal", "PK"}, new String[]{"be", "BY"}, new String[]{"bg", "BG"}, new String[]{"bn", "IN"}, new String[]{"bs", "BA"}, new String[]{"ca", "ES"}, new String[]{"ch", "MP"}, new String[]{"cpe", "SL"}, new String[]{"cs", "CZ"}, new String[]{"cy", "GB"}, new String[]{"da", "DK"}, new String[]{"de", "DE"}, new String[]{"dv", "MV"}, new String[]{"dz", "BT"}, new String[]{"el", "GR"}, new String[]{"en", "US"}, new String[]{"es", "ES"}, new String[]{"et", "EE"}, new String[]{"eu", "ES"}, new String[]{"fa", "IR"}, new String[]{"fi", "FI"}, new String[]{"fil", "PH"}, new String[]{"fj", "FJ"}, new String[]{"fo", "FO"}, new String[]{"fr", "FR"}, new String[]{"ga", "IE"}, new String[]{"gd", "GB"}, new String[]{"gl", "ES"}, new String[]{"gn", "PY"}, new String[]{"gu", "IN"}, new String[]{"gv", "GB"}, new String[]{"ha", "NG"}, new String[]{"he", "IL"}, new String[]{"hi", "IN"}, new String[]{"ho", "PG"}, new String[]{"hr", "HR"}, new String[]{"ht", "HT"}, new String[]{"hu", "HU"}, new String[]{"hy", "AM"}, new String[]{"id", "ID"}, new String[]{"is", "IS"}, new String[]{"it", "IT"}, new String[]{"ja", "JP"}, new String[]{"ka", "GE"}, new String[]{"kk", "KZ"}, new String[]{"kl", "GL"}, new String[]{"km", "KH"}, new String[]{"kn", "IN"}, new String[]{"ko", "KR"}, new String[]{"kok", "IN"}, new String[]{"ks", "IN"}, new String[]{"ku", "TR"}, new String[]{"ky", ExpandedProductParsedResult.KILOGRAM}, new String[]{"la", "VA"}, new String[]{"lb", "LU"}, new String[]{"ln", "CG"}, new String[]{"lo", "LA"}, new String[]{"lt", "LT"}, new String[]{"lv", "LV"}, new String[]{"mai", "IN"}, new String[]{"men", "GN"}, new String[]{"mg", "MG"}, new String[]{"mh", "MH"}, new String[]{"mk", "MK"}, new String[]{"ml", "IN"}, new String[]{"mn", "MN"}, new String[]{"mni", "IN"}, new String[]{"mo", "MD"}, new String[]{"mr", "IN"}, new String[]{DateFormat.MINUTE_SECOND, "MY"}, new String[]{"mt", "MT"}, new String[]{"my", "MM"}, new String[]{"na", "NR"}, new String[]{"nb", "NO"}, new String[]{"nd", "ZA"}, new String[]{"ne", "NP"}, new String[]{"niu", "NU"}, new String[]{"nl", "NL"}, new String[]{"nn", "NO"}, new String[]{"no", "NO"}, new String[]{"nr", "ZA"}, new String[]{"nso", "ZA"}, new String[]{"ny", "MW"}, new String[]{"om", "KE"}, new String[]{"or", "IN"}, new String[]{"pa", "IN"}, new String[]{"pau", "PW"}, new String[]{"pl", "PL"}, new String[]{"ps", "PK"}, new String[]{"pt", "BR"}, new String[]{"qu", "PE"}, new String[]{"rn", "BI"}, new String[]{"ro", "RO"}, new String[]{"ru", "RU"}, new String[]{InternalZipConstants.WRITE_MODE, "RW"}, new String[]{"sd", "IN"}, new String[]{"sg", "CF"}, new String[]{"si", "LK"}, new String[]{"sk", "SK"}, new String[]{"sl", "SI"}, new String[]{"sm", "WS"}, new String[]{"so", "DJ"}, new String[]{"sq", "CS"}, new String[]{"sr", "CS"}, new String[]{"ss", "ZA"}, new String[]{"st", "ZA"}, new String[]{"sv", "SE"}, new String[]{"sw", "KE"}, new String[]{"ta", "IN"}, new String[]{"te", "IN"}, new String[]{"tem", "SL"}, new String[]{"tet", "TL"}, new String[]{"th", "TH"}, new String[]{"ti", "ET"}, new String[]{"tg", "TJ"}, new String[]{"tk", "TM"}, new String[]{"tkl", "TK"}, new String[]{"tvl", "TV"}, new String[]{"tl", "PH"}, new String[]{"tn", "ZA"}, new String[]{"to", "TO"}, new String[]{"tpi", "PG"}, new String[]{"tr", "TR"}, new String[]{"ts", "ZA"}, new String[]{"uk", "UA"}, new String[]{"ur", "IN"}, new String[]{"uz", "UZ"}, new String[]{"ve", "ZA"}, new String[]{"vi", "VN"}, new String[]{"wo", "SN"}, new String[]{"xh", "ZA"}, new String[]{"zh", Parameter.CN}, new String[]{"zh_Hant", "TW"}, new String[]{"zu", "ZA"}, new String[]{"aa", "ET"}, new String[]{"byn", "ER"}, new String[]{"eo", "DE"}, new String[]{"gez", "ET"}, new String[]{"haw", "US"}, new String[]{"iu", "CA"}, new String[]{"kw", "GB"}, new String[]{"sa", "IN"}, new String[]{"sh", "HR"}, new String[]{"sid", "ET"}, new String[]{"syr", "SY"}, new String[]{"tig", "ER"}, new String[]{"tt", "RU"}, new String[]{"wal", "ET"}};
        int i5 = 0;
        while (true) {
            String[][] strArr = language_territory_hack;
            if (i5 >= strArr.length) {
                break;
            }
            language_territory_hack_map.put(strArr[i5][0], strArr[i5][1]);
            i5++;
        }
        territory_tzid_hack_map = new HashMap();
        territory_tzid_hack = new String[][]{new String[]{"AQ", "Antarctica/McMurdo"}, new String[]{"AR", "America/Buenos_Aires"}, new String[]{"AU", "Australia/Sydney"}, new String[]{"BR", "America/Sao_Paulo"}, new String[]{"CA", "America/Toronto"}, new String[]{"CD", "Africa/Kinshasa"}, new String[]{"CL", "America/Santiago"}, new String[]{Parameter.CN, "Asia/Shanghai"}, new String[]{"EC", "America/Guayaquil"}, new String[]{"ES", "Europe/Madrid"}, new String[]{"GB", "Europe/London"}, new String[]{"GL", "America/Godthab"}, new String[]{"ID", "Asia/Jakarta"}, new String[]{"ML", "Africa/Bamako"}, new String[]{"MX", "America/Mexico_City"}, new String[]{"MY", "Asia/Kuala_Lumpur"}, new String[]{"NZ", "Pacific/Auckland"}, new String[]{"PT", "Europe/Lisbon"}, new String[]{"RU", "Europe/Moscow"}, new String[]{"UA", "Europe/Kiev"}, new String[]{"US", "America/New_York"}, new String[]{"UZ", "Asia/Tashkent"}, new String[]{"PF", "Pacific/Tahiti"}, new String[]{"FM", "Pacific/Kosrae"}, new String[]{"KI", "Pacific/Tarawa"}, new String[]{"KZ", "Asia/Almaty"}, new String[]{"MH", "Pacific/Majuro"}, new String[]{"MN", "Asia/Ulaanbaatar"}, new String[]{"SJ", "Arctic/Longyearbyen"}, new String[]{"UM", "Pacific/Midway"}};
        int i6 = 0;
        while (true) {
            String[][] strArr2 = territory_tzid_hack;
            if (i6 >= strArr2.length) {
                return;
            }
            territory_tzid_hack_map.put(strArr2[i6][0], strArr2[i6][1]);
            i6++;
        }
    }

    @Override // com.ibm.icu.util.Freezable
    public boolean isFrozen() {
        return this.frozen;
    }

    @Override // com.ibm.icu.util.Freezable
    public GlobalizationPreferences freeze() {
        this.frozen = true;
        return this;
    }

    @Override // com.ibm.icu.util.Freezable
    public GlobalizationPreferences cloneAsThawed() {
        try {
            GlobalizationPreferences globalizationPreferences = (GlobalizationPreferences) clone();
            globalizationPreferences.frozen = false;
            return globalizationPreferences;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }
}
