package com.ibm.icu.impl;

import com.ibm.icu.impl.UResource;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.util.EnumMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class StaticUnicodeSets {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Map<Key, UnicodeSet> unicodeSets;

    public enum Key {
        EMPTY,
        DEFAULT_IGNORABLES,
        STRICT_IGNORABLES,
        COMMA,
        PERIOD,
        STRICT_COMMA,
        STRICT_PERIOD,
        APOSTROPHE_SIGN,
        OTHER_GROUPING_SEPARATORS,
        ALL_SEPARATORS,
        STRICT_ALL_SEPARATORS,
        MINUS_SIGN,
        PLUS_SIGN,
        PERCENT_SIGN,
        PERMILLE_SIGN,
        INFINITY_SIGN,
        DOLLAR_SIGN,
        POUND_SIGN,
        RUPEE_SIGN,
        YEN_SIGN,
        WON_SIGN,
        DIGITS,
        DIGITS_OR_ALL_SEPARATORS,
        DIGITS_OR_STRICT_ALL_SEPARATORS
    }

    static {
        EnumMap enumMap = new EnumMap(Key.class);
        unicodeSets = enumMap;
        enumMap.put((EnumMap) Key.EMPTY, (Key) new UnicodeSet("[]").freeze());
        unicodeSets.put(Key.DEFAULT_IGNORABLES, new UnicodeSet("[[:Zs:][\\u0009][:Bidi_Control:][:Variation_Selector:]]").freeze());
        unicodeSets.put(Key.STRICT_IGNORABLES, new UnicodeSet("[[:Bidi_Control:]]").freeze());
        ((ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, ULocale.ROOT)).getAllItemsWithFallback("parse", new ParseDataSink());
        UnicodeSet unicodeSet = new UnicodeSet("[٬‘\\u0020\\u00A0\\u2000-\\u200A\\u202F\\u205F\\u3000]");
        unicodeSet.addAll(unicodeSets.get(Key.APOSTROPHE_SIGN));
        unicodeSets.put(Key.OTHER_GROUPING_SEPARATORS, unicodeSet.freeze());
        unicodeSets.put(Key.ALL_SEPARATORS, computeUnion(Key.COMMA, Key.PERIOD, Key.OTHER_GROUPING_SEPARATORS));
        unicodeSets.put(Key.STRICT_ALL_SEPARATORS, computeUnion(Key.STRICT_COMMA, Key.STRICT_PERIOD, Key.OTHER_GROUPING_SEPARATORS));
        unicodeSets.put(Key.INFINITY_SIGN, new UnicodeSet("[∞]").freeze());
        unicodeSets.put(Key.DIGITS, new UnicodeSet("[:digit:]").freeze());
        unicodeSets.put(Key.DIGITS_OR_ALL_SEPARATORS, computeUnion(Key.DIGITS, Key.ALL_SEPARATORS));
        unicodeSets.put(Key.DIGITS_OR_STRICT_ALL_SEPARATORS, computeUnion(Key.DIGITS, Key.STRICT_ALL_SEPARATORS));
    }

    public static UnicodeSet get(Key key) {
        UnicodeSet unicodeSet = unicodeSets.get(key);
        return unicodeSet == null ? UnicodeSet.EMPTY : unicodeSet;
    }

    public static Key chooseFrom(String str, Key key) {
        if (get(key).contains(str)) {
            return key;
        }
        return null;
    }

    public static Key chooseFrom(String str, Key key, Key key2) {
        return get(key).contains(str) ? key : chooseFrom(str, key2);
    }

    public static Key chooseCurrency(String str) {
        if (get(Key.DOLLAR_SIGN).contains(str)) {
            return Key.DOLLAR_SIGN;
        }
        if (get(Key.POUND_SIGN).contains(str)) {
            return Key.POUND_SIGN;
        }
        if (get(Key.RUPEE_SIGN).contains(str)) {
            return Key.RUPEE_SIGN;
        }
        if (get(Key.YEN_SIGN).contains(str)) {
            return Key.YEN_SIGN;
        }
        if (get(Key.WON_SIGN).contains(str)) {
            return Key.WON_SIGN;
        }
        return null;
    }

    private static UnicodeSet computeUnion(Key key, Key key2) {
        return new UnicodeSet().addAll(get(key)).addAll(get(key2)).freeze();
    }

    private static UnicodeSet computeUnion(Key key, Key key2, Key key3) {
        return new UnicodeSet().addAll(get(key)).addAll(get(key2)).addAll(get(key3)).freeze();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void saveSet(Key key, String str) {
        unicodeSets.put(key, new UnicodeSet(str).freeze());
    }

    static class ParseDataSink extends UResource.Sink {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        ParseDataSink() {
        }

        @Override // com.ibm.icu.impl.UResource.Sink
        public void put(UResource.Key key, UResource.Value value, boolean z) {
            UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                if (!key.contentEquals("date")) {
                    UResource.Table table2 = value.getTable();
                    for (int i2 = 0; table2.getKeyAndValue(i2, key, value); i2++) {
                        boolean zContentEquals = key.contentEquals("lenient");
                        UResource.Array array = value.getArray();
                        for (int i3 = 0; i3 < array.getSize(); i3++) {
                            array.getValue(i3, value);
                            String string = value.toString();
                            if (string.indexOf(46) != -1) {
                                StaticUnicodeSets.saveSet(zContentEquals ? Key.PERIOD : Key.STRICT_PERIOD, string);
                            } else if (string.indexOf(44) != -1) {
                                StaticUnicodeSets.saveSet(zContentEquals ? Key.COMMA : Key.STRICT_COMMA, string);
                            } else if (string.indexOf(43) != -1) {
                                StaticUnicodeSets.saveSet(Key.PLUS_SIGN, string);
                            } else if (string.indexOf(45) != -1) {
                                StaticUnicodeSets.saveSet(Key.MINUS_SIGN, string);
                            } else if (string.indexOf(36) != -1) {
                                StaticUnicodeSets.saveSet(Key.DOLLAR_SIGN, string);
                            } else if (string.indexOf(163) != -1) {
                                StaticUnicodeSets.saveSet(Key.POUND_SIGN, string);
                            } else if (string.indexOf(8377) != -1) {
                                StaticUnicodeSets.saveSet(Key.RUPEE_SIGN, string);
                            } else if (string.indexOf(165) != -1) {
                                StaticUnicodeSets.saveSet(Key.YEN_SIGN, string);
                            } else if (string.indexOf(8361) != -1) {
                                StaticUnicodeSets.saveSet(Key.WON_SIGN, string);
                            } else if (string.indexOf(37) != -1) {
                                StaticUnicodeSets.saveSet(Key.PERCENT_SIGN, string);
                            } else if (string.indexOf(8240) != -1) {
                                StaticUnicodeSets.saveSet(Key.PERMILLE_SIGN, string);
                            } else if (string.indexOf(8217) != -1) {
                                StaticUnicodeSets.saveSet(Key.APOSTROPHE_SIGN, string);
                            } else {
                                throw new AssertionError("Unknown class of parse lenients: " + string);
                            }
                        }
                    }
                }
            }
        }
    }
}
