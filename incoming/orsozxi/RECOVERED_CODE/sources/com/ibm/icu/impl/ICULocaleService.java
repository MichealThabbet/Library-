package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUService;
import com.ibm.icu.util.ULocale;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.commons.io.IOUtils;

/* loaded from: classes2.dex */
public class ICULocaleService extends ICUService {
    private ULocale fallbackLocale;
    private String fallbackLocaleName;

    public ICULocaleService() {
    }

    public ICULocaleService(String str) {
        super(str);
    }

    public Object get(ULocale uLocale) {
        return get(uLocale, -1, null);
    }

    public Object get(ULocale uLocale, int i) {
        return get(uLocale, i, null);
    }

    public Object get(ULocale uLocale, ULocale[] uLocaleArr) {
        return get(uLocale, -1, uLocaleArr);
    }

    public Object get(ULocale uLocale, int i, ULocale[] uLocaleArr) {
        ICUService.Key keyCreateKey = createKey(uLocale, i);
        if (uLocaleArr == null) {
            return getKey(keyCreateKey);
        }
        String[] strArr = new String[1];
        Object key = getKey(keyCreateKey, strArr);
        if (key != null) {
            int iIndexOf = strArr[0].indexOf("/");
            if (iIndexOf >= 0) {
                strArr[0] = strArr[0].substring(iIndexOf + 1);
            }
            uLocaleArr[0] = new ULocale(strArr[0]);
        }
        return key;
    }

    public ICUService.Factory registerObject(Object obj, ULocale uLocale) {
        return registerObject(obj, uLocale, -1, true);
    }

    public ICUService.Factory registerObject(Object obj, ULocale uLocale, boolean z) {
        return registerObject(obj, uLocale, -1, z);
    }

    public ICUService.Factory registerObject(Object obj, ULocale uLocale, int i) {
        return registerObject(obj, uLocale, i, true);
    }

    public ICUService.Factory registerObject(Object obj, ULocale uLocale, int i, boolean z) {
        return registerFactory(new SimpleLocaleKeyFactory(obj, uLocale, i, z));
    }

    public Locale[] getAvailableLocales() {
        Set<String> visibleIDs = getVisibleIDs();
        Locale[] localeArr = new Locale[visibleIDs.size()];
        Iterator<String> it2 = visibleIDs.iterator();
        int i = 0;
        while (it2.hasNext()) {
            localeArr[i] = LocaleUtility.getLocaleFromName(it2.next());
            i++;
        }
        return localeArr;
    }

    public ULocale[] getAvailableULocales() {
        Set<String> visibleIDs = getVisibleIDs();
        ULocale[] uLocaleArr = new ULocale[visibleIDs.size()];
        Iterator<String> it2 = visibleIDs.iterator();
        int i = 0;
        while (it2.hasNext()) {
            uLocaleArr[i] = new ULocale(it2.next());
            i++;
        }
        return uLocaleArr;
    }

    public static class LocaleKey extends ICUService.Key {
        public static final int KIND_ANY = -1;
        private String currentID;
        private String fallbackID;
        private int kind;
        private String primaryID;
        private int varstart;

        public static LocaleKey createWithCanonicalFallback(String str, String str2) {
            return createWithCanonicalFallback(str, str2, -1);
        }

        public static LocaleKey createWithCanonicalFallback(String str, String str2, int i) {
            if (str == null) {
                return null;
            }
            return new LocaleKey(str, ULocale.getName(str), str2, i);
        }

        public static LocaleKey createWithCanonical(ULocale uLocale, String str, int i) {
            if (uLocale == null) {
                return null;
            }
            String name = uLocale.getName();
            return new LocaleKey(name, name, str, i);
        }

        protected LocaleKey(String str, String str2, String str3, int i) {
            super(str);
            this.kind = i;
            if (str2 == null || str2.equalsIgnoreCase("root")) {
                this.primaryID = "";
                this.fallbackID = null;
            } else {
                int iIndexOf = str2.indexOf(64);
                if (iIndexOf == 4 && str2.regionMatches(true, 0, "root", 0, 4)) {
                    this.primaryID = str2.substring(4);
                    this.varstart = 0;
                    this.fallbackID = null;
                } else {
                    this.primaryID = str2;
                    this.varstart = iIndexOf;
                    if (str3 == null || str2.equals(str3)) {
                        this.fallbackID = "";
                    } else {
                        this.fallbackID = str3;
                    }
                }
            }
            int i2 = this.varstart;
            this.currentID = i2 == -1 ? this.primaryID : this.primaryID.substring(0, i2);
        }

        public String prefix() {
            if (this.kind == -1) {
                return null;
            }
            return Integer.toString(kind());
        }

        public int kind() {
            return this.kind;
        }

        @Override // com.ibm.icu.impl.ICUService.Key
        public String canonicalID() {
            return this.primaryID;
        }

        @Override // com.ibm.icu.impl.ICUService.Key
        public String currentID() {
            return this.currentID;
        }

        @Override // com.ibm.icu.impl.ICUService.Key
        public String currentDescriptor() {
            String strCurrentID = currentID();
            if (strCurrentID == null) {
                return strCurrentID;
            }
            StringBuilder sb = new StringBuilder();
            if (this.kind != -1) {
                sb.append(prefix());
            }
            sb.append(IOUtils.DIR_SEPARATOR_UNIX);
            sb.append(strCurrentID);
            int i = this.varstart;
            if (i != -1) {
                String str = this.primaryID;
                sb.append(str.substring(i, str.length()));
            }
            return sb.toString();
        }

        public ULocale canonicalLocale() {
            return new ULocale(this.primaryID);
        }

        public ULocale currentLocale() {
            if (this.varstart == -1) {
                return new ULocale(this.currentID);
            }
            return new ULocale(this.currentID + this.primaryID.substring(this.varstart));
        }

        @Override // com.ibm.icu.impl.ICUService.Key
        public boolean fallback() {
            int iLastIndexOf = this.currentID.lastIndexOf(95);
            if (iLastIndexOf != -1) {
                do {
                    iLastIndexOf--;
                    if (iLastIndexOf < 0) {
                        break;
                    }
                } while (this.currentID.charAt(iLastIndexOf) == '_');
                this.currentID = this.currentID.substring(0, iLastIndexOf + 1);
                return true;
            }
            String str = this.fallbackID;
            if (str != null) {
                this.currentID = str;
                if (str.length() == 0) {
                    this.fallbackID = null;
                } else {
                    this.fallbackID = "";
                }
                return true;
            }
            this.currentID = null;
            return false;
        }

        @Override // com.ibm.icu.impl.ICUService.Key
        public boolean isFallbackOf(String str) {
            return LocaleUtility.isFallbackOf(canonicalID(), str);
        }
    }

    public static abstract class LocaleKeyFactory implements ICUService.Factory {
        public static final boolean INVISIBLE = false;
        public static final boolean VISIBLE = true;
        protected final String name;
        protected final boolean visible;

        protected Object handleCreate(ULocale uLocale, int i, ICUService iCUService) {
            return null;
        }

        protected LocaleKeyFactory(boolean z) {
            this.visible = z;
            this.name = null;
        }

        protected LocaleKeyFactory(boolean z, String str) {
            this.visible = z;
            this.name = str;
        }

        @Override // com.ibm.icu.impl.ICUService.Factory
        public Object create(ICUService.Key key, ICUService iCUService) {
            if (!handlesKey(key)) {
                return null;
            }
            LocaleKey localeKey = (LocaleKey) key;
            return handleCreate(localeKey.currentLocale(), localeKey.kind(), iCUService);
        }

        protected boolean handlesKey(ICUService.Key key) {
            if (key == null) {
                return false;
            }
            return getSupportedIDs().contains(key.currentID());
        }

        @Override // com.ibm.icu.impl.ICUService.Factory
        public void updateVisibleIDs(Map<String, ICUService.Factory> map) {
            for (String str : getSupportedIDs()) {
                if (this.visible) {
                    map.put(str, this);
                } else {
                    map.remove(str);
                }
            }
        }

        @Override // com.ibm.icu.impl.ICUService.Factory
        public String getDisplayName(String str, ULocale uLocale) {
            return uLocale == null ? str : new ULocale(str).getDisplayName(uLocale);
        }

        protected boolean isSupportedID(String str) {
            return getSupportedIDs().contains(str);
        }

        protected Set<String> getSupportedIDs() {
            return Collections.emptySet();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(super.toString());
            if (this.name != null) {
                sb.append(", name: ");
                sb.append(this.name);
            }
            sb.append(", visible: ");
            sb.append(this.visible);
            return sb.toString();
        }
    }

    public static class SimpleLocaleKeyFactory extends LocaleKeyFactory {
        private final String id;
        private final int kind;
        private final Object obj;

        public SimpleLocaleKeyFactory(Object obj, ULocale uLocale, int i, boolean z) {
            this(obj, uLocale, i, z, null);
        }

        public SimpleLocaleKeyFactory(Object obj, ULocale uLocale, int i, boolean z, String str) {
            super(z, str);
            this.obj = obj;
            this.id = uLocale.getBaseName();
            this.kind = i;
        }

        @Override // com.ibm.icu.impl.ICULocaleService.LocaleKeyFactory, com.ibm.icu.impl.ICUService.Factory
        public Object create(ICUService.Key key, ICUService iCUService) {
            if (!(key instanceof LocaleKey)) {
                return null;
            }
            LocaleKey localeKey = (LocaleKey) key;
            int i = this.kind;
            if ((i == -1 || i == localeKey.kind()) && this.id.equals(localeKey.currentID())) {
                return this.obj;
            }
            return null;
        }

        @Override // com.ibm.icu.impl.ICULocaleService.LocaleKeyFactory
        protected boolean isSupportedID(String str) {
            return this.id.equals(str);
        }

        @Override // com.ibm.icu.impl.ICULocaleService.LocaleKeyFactory, com.ibm.icu.impl.ICUService.Factory
        public void updateVisibleIDs(Map<String, ICUService.Factory> map) {
            if (this.visible) {
                map.put(this.id, this);
            } else {
                map.remove(this.id);
            }
        }

        @Override // com.ibm.icu.impl.ICULocaleService.LocaleKeyFactory
        public String toString() {
            return super.toString() + ", id: " + this.id + ", kind: " + this.kind;
        }
    }

    public static class ICUResourceBundleFactory extends LocaleKeyFactory {
        protected final String bundleName;

        public ICUResourceBundleFactory() {
            this(ICUData.ICU_BASE_NAME);
        }

        public ICUResourceBundleFactory(String str) {
            super(true);
            this.bundleName = str;
        }

        @Override // com.ibm.icu.impl.ICULocaleService.LocaleKeyFactory
        protected Set<String> getSupportedIDs() {
            return ICUResourceBundle.getFullLocaleNameSet(this.bundleName, loader());
        }

        @Override // com.ibm.icu.impl.ICULocaleService.LocaleKeyFactory, com.ibm.icu.impl.ICUService.Factory
        public void updateVisibleIDs(Map<String, ICUService.Factory> map) {
            Iterator<String> it2 = ICUResourceBundle.getAvailableLocaleNameSet(this.bundleName, loader()).iterator();
            while (it2.hasNext()) {
                map.put(it2.next(), this);
            }
        }

        @Override // com.ibm.icu.impl.ICULocaleService.LocaleKeyFactory
        protected Object handleCreate(ULocale uLocale, int i, ICUService iCUService) {
            return ICUResourceBundle.getBundleInstance(this.bundleName, uLocale, loader());
        }

        protected ClassLoader loader() {
            return ClassLoaderUtil.getClassLoader(getClass());
        }

        @Override // com.ibm.icu.impl.ICULocaleService.LocaleKeyFactory
        public String toString() {
            return super.toString() + ", bundle: " + this.bundleName;
        }
    }

    public String validateFallbackLocale() {
        ULocale uLocale = ULocale.getDefault();
        if (uLocale != this.fallbackLocale) {
            synchronized (this) {
                if (uLocale != this.fallbackLocale) {
                    this.fallbackLocaleName = uLocale.getBaseName();
                    clearServiceCache();
                    this.fallbackLocale = uLocale;
                }
            }
        }
        return this.fallbackLocaleName;
    }

    @Override // com.ibm.icu.impl.ICUService
    public ICUService.Key createKey(String str) {
        return LocaleKey.createWithCanonicalFallback(str, validateFallbackLocale());
    }

    public ICUService.Key createKey(String str, int i) {
        return LocaleKey.createWithCanonicalFallback(str, validateFallbackLocale(), i);
    }

    public ICUService.Key createKey(ULocale uLocale, int i) {
        return LocaleKey.createWithCanonical(uLocale, validateFallbackLocale(), i);
    }
}
