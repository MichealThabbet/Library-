package com.ibm.icu.impl.locale;

import com.ibm.icu.util.Output;
import com.ibm.icu.util.UResourceBundle;
import com.ibm.icu.util.UResourceBundleIterator;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class KeyTypeData {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static Map<String, Set<String>> BCP47_KEYS;
    static Set<String> DEPRECATED_KEYS = Collections.emptySet();
    static Map<String, ValueType> VALUE_TYPES = Collections.emptyMap();
    static Map<String, Set<String>> DEPRECATED_KEY_TYPES = Collections.emptyMap();
    private static final Object[][] KEY_DATA = new Object[0][];
    private static final Map<String, KeyData> KEYMAP = new HashMap();

    private enum KeyInfoType {
        deprecated,
        valueType
    }

    private enum TypeInfoType {
        deprecated
    }

    public enum ValueType {
        single,
        multiple,
        incremental,
        any
    }

    private static abstract class SpecialTypeHandler {
        abstract boolean isWellFormed(String str);

        private SpecialTypeHandler() {
        }

        /* synthetic */ SpecialTypeHandler(AnonymousClass1 anonymousClass1) {
            this();
        }

        String canonicalize(String str) {
            return AsciiUtil.toLowerString(str);
        }
    }

    private static class CodepointsTypeHandler extends SpecialTypeHandler {
        private static final Pattern pat = Pattern.compile("[0-9a-fA-F]{4,6}(-[0-9a-fA-F]{4,6})*");

        private CodepointsTypeHandler() {
            super(null);
        }

        /* synthetic */ CodepointsTypeHandler(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // com.ibm.icu.impl.locale.KeyTypeData.SpecialTypeHandler
        boolean isWellFormed(String str) {
            return pat.matcher(str).matches();
        }
    }

    private static class ReorderCodeTypeHandler extends SpecialTypeHandler {
        private static final Pattern pat = Pattern.compile("[a-zA-Z]{3,8}(-[a-zA-Z]{3,8})*");

        private ReorderCodeTypeHandler() {
            super(null);
        }

        /* synthetic */ ReorderCodeTypeHandler(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // com.ibm.icu.impl.locale.KeyTypeData.SpecialTypeHandler
        boolean isWellFormed(String str) {
            return pat.matcher(str).matches();
        }
    }

    private static class RgKeyValueTypeHandler extends SpecialTypeHandler {
        private static final Pattern pat = Pattern.compile("([a-zA-Z]{2}|[0-9]{3})[zZ]{4}");

        private RgKeyValueTypeHandler() {
            super(null);
        }

        /* synthetic */ RgKeyValueTypeHandler(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // com.ibm.icu.impl.locale.KeyTypeData.SpecialTypeHandler
        boolean isWellFormed(String str) {
            return pat.matcher(str).matches();
        }
    }

    private static class ScriptCodeTypeHandler extends SpecialTypeHandler {
        private static final Pattern pat = Pattern.compile("[a-zA-Z]{4}(-[a-zA-Z]{4})*");

        private ScriptCodeTypeHandler() {
            super(null);
        }

        /* synthetic */ ScriptCodeTypeHandler(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // com.ibm.icu.impl.locale.KeyTypeData.SpecialTypeHandler
        boolean isWellFormed(String str) {
            return pat.matcher(str).matches();
        }
    }

    private static class SubdivisionKeyValueTypeHandler extends SpecialTypeHandler {
        private static final Pattern pat = Pattern.compile("([a-zA-Z]{2}|[0-9]{3})");

        private SubdivisionKeyValueTypeHandler() {
            super(null);
        }

        /* synthetic */ SubdivisionKeyValueTypeHandler(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // com.ibm.icu.impl.locale.KeyTypeData.SpecialTypeHandler
        boolean isWellFormed(String str) {
            return pat.matcher(str).matches();
        }
    }

    private static class PrivateUseKeyValueTypeHandler extends SpecialTypeHandler {
        private static final Pattern pat = Pattern.compile("[a-zA-Z0-9]{3,8}(-[a-zA-Z0-9]{3,8})*");

        private PrivateUseKeyValueTypeHandler() {
            super(null);
        }

        /* synthetic */ PrivateUseKeyValueTypeHandler(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // com.ibm.icu.impl.locale.KeyTypeData.SpecialTypeHandler
        boolean isWellFormed(String str) {
            return pat.matcher(str).matches();
        }
    }

    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'CODEPOINTS' uses external variables
    	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
    	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:372)
    	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:337)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:322)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
    	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
    	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
     */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    private static final class SpecialType {
        private static final /* synthetic */ SpecialType[] $VALUES;
        public static final SpecialType CODEPOINTS;
        public static final SpecialType PRIVATE_USE;
        public static final SpecialType REORDER_CODE;
        public static final SpecialType RG_KEY_VALUE;
        public static final SpecialType SCRIPT_CODE;
        public static final SpecialType SUBDIVISION_CODE;
        SpecialTypeHandler handler;

        public static SpecialType valueOf(String str) {
            return (SpecialType) Enum.valueOf(SpecialType.class, str);
        }

        public static SpecialType[] values() {
            return (SpecialType[]) $VALUES.clone();
        }

        static {
            AnonymousClass1 anonymousClass1 = null;
            CODEPOINTS = new SpecialType("CODEPOINTS", 0, new CodepointsTypeHandler(anonymousClass1));
            REORDER_CODE = new SpecialType("REORDER_CODE", 1, new ReorderCodeTypeHandler(anonymousClass1));
            RG_KEY_VALUE = new SpecialType("RG_KEY_VALUE", 2, new RgKeyValueTypeHandler(anonymousClass1));
            SCRIPT_CODE = new SpecialType("SCRIPT_CODE", 3, new ScriptCodeTypeHandler(anonymousClass1));
            SUBDIVISION_CODE = new SpecialType("SUBDIVISION_CODE", 4, new SubdivisionKeyValueTypeHandler(anonymousClass1));
            SpecialType specialType = new SpecialType("PRIVATE_USE", 5, new PrivateUseKeyValueTypeHandler(anonymousClass1));
            PRIVATE_USE = specialType;
            $VALUES = new SpecialType[]{CODEPOINTS, REORDER_CODE, RG_KEY_VALUE, SCRIPT_CODE, SUBDIVISION_CODE, specialType};
        }

        private SpecialType(String str, int i, SpecialTypeHandler specialTypeHandler) {
            this.handler = specialTypeHandler;
        }
    }

    private static class KeyData {
        String bcpId;
        String legacyId;
        EnumSet<SpecialType> specialTypes;
        Map<String, Type> typeMap;

        KeyData(String str, String str2, Map<String, Type> map, EnumSet<SpecialType> enumSet) {
            this.legacyId = str;
            this.bcpId = str2;
            this.typeMap = map;
            this.specialTypes = enumSet;
        }
    }

    private static class Type {
        String bcpId;
        String legacyId;

        Type(String str, String str2) {
            this.legacyId = str;
            this.bcpId = str2;
        }
    }

    public static String toBcpKey(String str) {
        KeyData keyData = KEYMAP.get(AsciiUtil.toLowerString(str));
        if (keyData != null) {
            return keyData.bcpId;
        }
        return null;
    }

    public static String toLegacyKey(String str) {
        KeyData keyData = KEYMAP.get(AsciiUtil.toLowerString(str));
        if (keyData != null) {
            return keyData.legacyId;
        }
        return null;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [T, java.lang.Boolean] */
    /* JADX WARN: Type inference failed for: r0v5, types: [T, java.lang.Boolean] */
    /* JADX WARN: Type inference failed for: r1v12, types: [T, java.lang.Boolean] */
    public static String toBcpType(String str, String str2, Output<Boolean> output, Output<Boolean> output2) {
        if (output != null) {
            output.value = false;
        }
        if (output2 != null) {
            output2.value = false;
        }
        String lowerString = AsciiUtil.toLowerString(str);
        String lowerString2 = AsciiUtil.toLowerString(str2);
        KeyData keyData = KEYMAP.get(lowerString);
        if (keyData == null) {
            return null;
        }
        if (output != null) {
            output.value = Boolean.TRUE;
        }
        Type type = keyData.typeMap.get(lowerString2);
        if (type != null) {
            return type.bcpId;
        }
        if (keyData.specialTypes == null) {
            return null;
        }
        Iterator it2 = keyData.specialTypes.iterator();
        while (it2.hasNext()) {
            SpecialType specialType = (SpecialType) it2.next();
            if (specialType.handler.isWellFormed(lowerString2)) {
                if (output2 != null) {
                    output2.value = true;
                }
                return specialType.handler.canonicalize(lowerString2);
            }
        }
        return null;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [T, java.lang.Boolean] */
    /* JADX WARN: Type inference failed for: r0v5, types: [T, java.lang.Boolean] */
    /* JADX WARN: Type inference failed for: r1v12, types: [T, java.lang.Boolean] */
    public static String toLegacyType(String str, String str2, Output<Boolean> output, Output<Boolean> output2) {
        if (output != null) {
            output.value = false;
        }
        if (output2 != null) {
            output2.value = false;
        }
        String lowerString = AsciiUtil.toLowerString(str);
        String lowerString2 = AsciiUtil.toLowerString(str2);
        KeyData keyData = KEYMAP.get(lowerString);
        if (keyData == null) {
            return null;
        }
        if (output != null) {
            output.value = Boolean.TRUE;
        }
        Type type = keyData.typeMap.get(lowerString2);
        if (type != null) {
            return type.legacyId;
        }
        if (keyData.specialTypes == null) {
            return null;
        }
        Iterator it2 = keyData.specialTypes.iterator();
        while (it2.hasNext()) {
            SpecialType specialType = (SpecialType) it2.next();
            if (specialType.handler.isWellFormed(lowerString2)) {
                if (output2 != null) {
                    output2.value = true;
                }
                return specialType.handler.canonicalize(lowerString2);
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00fc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static void initFromResourceBundle() throws java.util.NoSuchElementException {
        /*
            Method dump skipped, instructions count: 512
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.locale.KeyTypeData.initFromResourceBundle():void");
    }

    static {
        initFromResourceBundle();
    }

    private static void getKeyInfo(UResourceBundle uResourceBundle) throws NoSuchElementException {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        UResourceBundleIterator iterator = uResourceBundle.getIterator();
        while (iterator.hasNext()) {
            UResourceBundle next = iterator.next();
            KeyInfoType keyInfoTypeValueOf = KeyInfoType.valueOf(next.getKey());
            UResourceBundleIterator iterator2 = next.getIterator();
            while (iterator2.hasNext()) {
                UResourceBundle next2 = iterator2.next();
                String key = next2.getKey();
                String string = next2.getString();
                int i = AnonymousClass1.$SwitchMap$com$ibm$icu$impl$locale$KeyTypeData$KeyInfoType[keyInfoTypeValueOf.ordinal()];
                if (i == 1) {
                    linkedHashSet.add(key);
                } else if (i == 2) {
                    linkedHashMap.put(key, ValueType.valueOf(string));
                }
            }
        }
        DEPRECATED_KEYS = Collections.unmodifiableSet(linkedHashSet);
        VALUE_TYPES = Collections.unmodifiableMap(linkedHashMap);
    }

    private static void getTypeInfo(UResourceBundle uResourceBundle) throws NoSuchElementException {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        UResourceBundleIterator iterator = uResourceBundle.getIterator();
        while (iterator.hasNext()) {
            UResourceBundle next = iterator.next();
            TypeInfoType typeInfoTypeValueOf = TypeInfoType.valueOf(next.getKey());
            UResourceBundleIterator iterator2 = next.getIterator();
            while (iterator2.hasNext()) {
                UResourceBundle next2 = iterator2.next();
                String key = next2.getKey();
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                UResourceBundleIterator iterator3 = next2.getIterator();
                while (iterator3.hasNext()) {
                    String key2 = iterator3.next().getKey();
                    if (AnonymousClass1.$SwitchMap$com$ibm$icu$impl$locale$KeyTypeData$TypeInfoType[typeInfoTypeValueOf.ordinal()] == 1) {
                        linkedHashSet.add(key2);
                    }
                }
                linkedHashMap.put(key, Collections.unmodifiableSet(linkedHashSet));
            }
        }
        DEPRECATED_KEY_TYPES = Collections.unmodifiableMap(linkedHashMap);
    }

    /* renamed from: com.ibm.icu.impl.locale.KeyTypeData$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$impl$locale$KeyTypeData$KeyInfoType;
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$impl$locale$KeyTypeData$TypeInfoType;

        static {
            int[] iArr = new int[TypeInfoType.values().length];
            $SwitchMap$com$ibm$icu$impl$locale$KeyTypeData$TypeInfoType = iArr;
            try {
                iArr[TypeInfoType.deprecated.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            int[] iArr2 = new int[KeyInfoType.values().length];
            $SwitchMap$com$ibm$icu$impl$locale$KeyTypeData$KeyInfoType = iArr2;
            try {
                iArr2[KeyInfoType.deprecated.ordinal()] = 1;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ibm$icu$impl$locale$KeyTypeData$KeyInfoType[KeyInfoType.valueType.ordinal()] = 2;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private static void initFromTables() {
        boolean z;
        HashMap map;
        HashMap map2;
        boolean z2;
        boolean z3;
        Set hashSet;
        Object[][] objArr = KEY_DATA;
        int length = objArr.length;
        char c = 0;
        int i = 0;
        while (i < length) {
            Object[] objArr2 = objArr[i];
            String str = (String) objArr2[c];
            char c2 = 1;
            String str2 = (String) objArr2[1];
            String[][] strArr = (String[][]) objArr2[2];
            String[][] strArr2 = (String[][]) objArr2[3];
            String[][] strArr3 = (String[][]) objArr2[4];
            if (str2 == null) {
                str2 = str;
                z = true;
            } else {
                z = false;
            }
            if (strArr2 != null) {
                map = new HashMap();
                int length2 = strArr2.length;
                int i2 = 0;
                while (i2 < length2) {
                    String[] strArr4 = strArr2[i2];
                    String str3 = strArr4[c];
                    String str4 = strArr4[c2];
                    Set set = (Set) map.get(str4);
                    if (set == null) {
                        hashSet = new HashSet();
                        map.put(str4, hashSet);
                    } else {
                        hashSet = set;
                    }
                    hashSet.add(str3);
                    i2++;
                    c2 = 1;
                }
            } else {
                map = null;
            }
            if (strArr3 != null) {
                map2 = new HashMap();
                for (String[] strArr5 : strArr3) {
                    String str5 = strArr5[c];
                    String str6 = strArr5[1];
                    Set hashSet2 = (Set) map2.get(str6);
                    if (hashSet2 == null) {
                        hashSet2 = new HashSet();
                        map2.put(str6, hashSet2);
                    }
                    hashSet2.add(str5);
                }
            } else {
                map2 = null;
            }
            HashMap map3 = new HashMap();
            int length3 = strArr.length;
            int i3 = 0;
            HashSet hashSet3 = null;
            while (i3 < length3) {
                String[] strArr6 = strArr[i3];
                String str7 = strArr6[c];
                String str8 = strArr6[1];
                SpecialType[] specialTypeArrValues = SpecialType.values();
                Object[][] objArr3 = objArr;
                int length4 = specialTypeArrValues.length;
                int i4 = length;
                int i5 = 0;
                while (true) {
                    if (i5 >= length4) {
                        z2 = false;
                        break;
                    }
                    int i6 = length4;
                    SpecialType specialType = specialTypeArrValues[i5];
                    SpecialType[] specialTypeArr = specialTypeArrValues;
                    if (str7.equals(specialType.toString())) {
                        if (hashSet3 == null) {
                            hashSet3 = new HashSet();
                        }
                        hashSet3.add(specialType);
                        z2 = true;
                    } else {
                        i5++;
                        length4 = i6;
                        specialTypeArrValues = specialTypeArr;
                    }
                }
                if (!z2) {
                    if (str8 == null) {
                        str8 = str7;
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    Type type = new Type(str7, str8);
                    map3.put(AsciiUtil.toLowerString(str7), type);
                    if (!z3) {
                        map3.put(AsciiUtil.toLowerString(str8), type);
                    }
                    Set set2 = (Set) map.get(str7);
                    if (set2 != null) {
                        Iterator it2 = set2.iterator();
                        while (it2.hasNext()) {
                            map3.put(AsciiUtil.toLowerString((String) it2.next()), type);
                        }
                    }
                    Set set3 = (Set) map2.get(str8);
                    if (set3 != null) {
                        Iterator it3 = set3.iterator();
                        while (it3.hasNext()) {
                            map3.put(AsciiUtil.toLowerString((String) it3.next()), type);
                        }
                    }
                }
                i3++;
                objArr = objArr3;
                length = i4;
                c = 0;
            }
            Object[][] objArr4 = objArr;
            int i7 = length;
            KeyData keyData = new KeyData(str, str2, map3, hashSet3 != null ? EnumSet.copyOf((Collection) hashSet3) : null);
            KEYMAP.put(AsciiUtil.toLowerString(str), keyData);
            if (!z) {
                KEYMAP.put(AsciiUtil.toLowerString(str2), keyData);
            }
            i++;
            objArr = objArr4;
            length = i7;
            c = 0;
        }
    }

    public static Set<String> getBcp47Keys() {
        return BCP47_KEYS.keySet();
    }

    public static Set<String> getBcp47KeyTypes(String str) {
        return BCP47_KEYS.get(str);
    }

    public static boolean isDeprecated(String str) {
        return DEPRECATED_KEYS.contains(str);
    }

    public static boolean isDeprecated(String str, String str2) {
        Set<String> set = DEPRECATED_KEY_TYPES.get(str);
        if (set == null) {
            return false;
        }
        return set.contains(str2);
    }

    public static ValueType getValueType(String str) {
        ValueType valueType = VALUE_TYPES.get(str);
        return valueType == null ? ValueType.single : valueType;
    }
}
