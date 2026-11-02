package com.ibm.icu.impl;

import com.facebook.internal.ServerProtocol;
import com.huawei.hms.framework.common.ContainerUtils;
import com.ibm.icu.impl.ICUResourceBundleImpl;
import com.ibm.icu.impl.ICUResourceBundleReader;
import com.ibm.icu.impl.URLHandler;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import com.ibm.icu.util.UResourceBundleIterator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import org.apache.http.protocol.HTTP;

/* loaded from: classes2.dex */
public class ICUResourceBundle extends UResourceBundle {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int ALIAS = 3;
    public static final int ARRAY16 = 9;
    private static final String DEFAULT_TAG = "default";
    private static final String FULL_LOCALE_NAMES_LIST = "fullLocaleNames.lst";
    private static final char HYPHEN = '-';
    private static final String ICUDATA = "ICUDATA";
    private static final String ICU_RESOURCE_INDEX = "res_index";
    protected static final String INSTALLED_LOCALES = "InstalledLocales";
    private static final String LOCALE = "LOCALE";
    public static final String NO_INHERITANCE_MARKER = "∅∅∅";
    public static final int RES_BOGUS = -1;
    private static final char RES_PATH_SEP_CHAR = '/';
    private static final String RES_PATH_SEP_STR = "/";
    public static final int STRING_V2 = 6;
    public static final int TABLE16 = 5;
    public static final int TABLE32 = 4;
    private ICUResourceBundle container;
    protected String key;
    WholeBundle wholeBundle;
    public static final ClassLoader ICU_DATA_CLASS_LOADER = ClassLoaderUtil.getClassLoader(ICUData.class);
    private static CacheBase<String, ICUResourceBundle, Loader> BUNDLE_CACHE = new SoftCache<String, ICUResourceBundle, Loader>() { // from class: com.ibm.icu.impl.ICUResourceBundle.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ibm.icu.impl.CacheBase
        public ICUResourceBundle createInstance(String str, Loader loader) {
            return loader.load();
        }
    };
    private static final boolean DEBUG = ICUDebug.enabled("localedata");
    private static CacheBase<String, AvailEntry, ClassLoader> GET_AVAILABLE_CACHE = new SoftCache<String, AvailEntry, ClassLoader>() { // from class: com.ibm.icu.impl.ICUResourceBundle.3
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ibm.icu.impl.CacheBase
        public AvailEntry createInstance(String str, ClassLoader classLoader) {
            return new AvailEntry(str, classLoader);
        }
    };

    public enum OpenType {
        LOCALE_DEFAULT_ROOT,
        LOCALE_ROOT,
        LOCALE_ONLY,
        DIRECT
    }

    public int hashCode() {
        return 42;
    }

    protected static final class WholeBundle {
        String baseName;
        ClassLoader loader;
        String localeID;
        ICUResourceBundleReader reader;
        Set<String> topLevelKeys;
        ULocale ulocale;

        WholeBundle(String str, String str2, ClassLoader classLoader, ICUResourceBundleReader iCUResourceBundleReader) {
            this.baseName = str;
            this.localeID = str2;
            this.ulocale = new ULocale(str2);
            this.loader = classLoader;
            this.reader = iCUResourceBundleReader;
        }
    }

    private static abstract class Loader {
        abstract ICUResourceBundle load();

        private Loader() {
        }
    }

    public static final ULocale getFunctionalEquivalent(String str, ClassLoader classLoader, String str2, String str3, ULocale uLocale, boolean[] zArr, boolean z) {
        boolean z2;
        String keywordValue = uLocale.getKeywordValue(str3);
        String baseName = uLocale.getBaseName();
        ULocale uLocale2 = new ULocale(baseName);
        int i = 0;
        if (keywordValue == null || keywordValue.length() == 0 || keywordValue.equals("default")) {
            keywordValue = "";
            z2 = true;
        } else {
            z2 = false;
        }
        ICUResourceBundle parent = (ICUResourceBundle) UResourceBundle.getBundleInstance(str, uLocale2);
        if (zArr != null) {
            zArr[0] = false;
            ULocale[] uLocaleList = getAvailEntry(str, classLoader).getULocaleList(ULocale.AvailableType.DEFAULT);
            int i2 = 0;
            while (true) {
                if (i2 >= uLocaleList.length) {
                    break;
                }
                if (uLocale2.equals(uLocaleList[i2])) {
                    zArr[0] = true;
                    break;
                }
                i2++;
            }
        }
        ULocale uLocale3 = null;
        String string = null;
        int i3 = 0;
        do {
            try {
                string = ((ICUResourceBundle) parent.get(str2)).getString("default");
                if (z2) {
                    keywordValue = string;
                    z2 = false;
                }
                uLocale3 = parent.getULocale();
            } catch (MissingResourceException unused) {
            }
            if (uLocale3 == null) {
                parent = parent.getParent();
                i3++;
            }
            if (parent == null) {
                break;
            }
        } while (uLocale3 == null);
        ICUResourceBundle parent2 = (ICUResourceBundle) UResourceBundle.getBundleInstance(str, new ULocale(baseName));
        ULocale uLocale4 = null;
        int i4 = 0;
        do {
            try {
                ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) parent2.get(str2);
                iCUResourceBundle.get(keywordValue);
                uLocale4 = iCUResourceBundle.getULocale();
                if (uLocale4 != null && i4 > i3) {
                    string = iCUResourceBundle.getString("default");
                    parent2.getULocale();
                    i3 = i4;
                }
            } catch (MissingResourceException unused2) {
            }
            if (uLocale4 == null) {
                parent2 = parent2.getParent();
                i4++;
            }
            if (parent2 == null) {
                break;
            }
        } while (uLocale4 == null);
        if (uLocale4 == null && string != null && !string.equals(keywordValue)) {
            ICUResourceBundle parent3 = (ICUResourceBundle) UResourceBundle.getBundleInstance(str, new ULocale(baseName));
            String string2 = string;
            do {
                try {
                    ICUResourceBundle iCUResourceBundle2 = (ICUResourceBundle) parent3.get(str2);
                    ICUResourceBundle iCUResourceBundle3 = (ICUResourceBundle) iCUResourceBundle2.get(string);
                    uLocale4 = parent3.getULocale();
                    if (!uLocale4.getBaseName().equals(iCUResourceBundle3.getULocale().getBaseName())) {
                        uLocale4 = null;
                    }
                    if (uLocale4 != null && i > i3) {
                        string2 = iCUResourceBundle2.getString("default");
                        parent3.getULocale();
                        i3 = i;
                    }
                } catch (MissingResourceException unused3) {
                }
                if (uLocale4 == null) {
                    parent3 = parent3.getParent();
                    i++;
                }
                if (parent3 == null) {
                    break;
                }
            } while (uLocale4 == null);
            i4 = i;
            keywordValue = string;
            string = string2;
        }
        if (uLocale4 == null) {
            throw new MissingResourceException("Could not find locale containing requested or default keyword.", str, str3 + ContainerUtils.KEY_VALUE_DELIMITER + keywordValue);
        }
        if (z && string.equals(keywordValue) && i4 <= i3) {
            return uLocale4;
        }
        return new ULocale(uLocale4.getBaseName() + "@" + str3 + ContainerUtils.KEY_VALUE_DELIMITER + keywordValue);
    }

    public static final String[] getKeywordValues(String str, String str2) {
        HashSet hashSet = new HashSet();
        for (ULocale uLocale : getAvailEntry(str, ICU_DATA_CLASS_LOADER).getULocaleList(ULocale.AvailableType.DEFAULT)) {
            try {
                Enumeration<String> keys = ((ICUResourceBundle) UResourceBundle.getBundleInstance(str, uLocale).getObject(str2)).getKeys();
                while (keys.hasMoreElements()) {
                    String strNextElement = keys.nextElement();
                    if (!"default".equals(strNextElement) && !strNextElement.startsWith("private-")) {
                        hashSet.add(strNextElement);
                    }
                }
            } catch (Throwable unused) {
            }
        }
        return (String[]) hashSet.toArray(new String[0]);
    }

    public ICUResourceBundle getWithFallback(String str) throws MissingResourceException {
        ICUResourceBundle iCUResourceBundleFindResourceWithFallback = findResourceWithFallback(str, this, null);
        if (iCUResourceBundleFindResourceWithFallback == null) {
            throw new MissingResourceException("Can't find resource for bundle " + getClass().getName() + ", key " + getType(), str, getKey());
        }
        if (iCUResourceBundleFindResourceWithFallback.getType() == 0 && iCUResourceBundleFindResourceWithFallback.getString().equals(NO_INHERITANCE_MARKER)) {
            throw new MissingResourceException("Encountered NO_INHERITANCE_MARKER", str, getKey());
        }
        return iCUResourceBundleFindResourceWithFallback;
    }

    public ICUResourceBundle at(int i) {
        return (ICUResourceBundle) handleGet(i, (HashMap<String, String>) null, this);
    }

    public ICUResourceBundle at(String str) {
        if (this instanceof ICUResourceBundleImpl.ResourceTable) {
            return (ICUResourceBundle) handleGet(str, (HashMap<String, String>) null, this);
        }
        return null;
    }

    @Override // com.ibm.icu.util.UResourceBundle
    public ICUResourceBundle findTopLevel(int i) {
        return (ICUResourceBundle) super.findTopLevel(i);
    }

    @Override // com.ibm.icu.util.UResourceBundle
    public ICUResourceBundle findTopLevel(String str) {
        return (ICUResourceBundle) super.findTopLevel(str);
    }

    public ICUResourceBundle findWithFallback(String str) {
        return findResourceWithFallback(str, this, null);
    }

    public String findStringWithFallback(String str) {
        return findStringWithFallback(str, this, null);
    }

    public String getStringWithFallback(String str) throws MissingResourceException {
        String strFindStringWithFallback = findStringWithFallback(str, this, null);
        if (strFindStringWithFallback == null) {
            throw new MissingResourceException("Can't find resource for bundle " + getClass().getName() + ", key " + getType(), str, getKey());
        }
        if (strFindStringWithFallback.equals(NO_INHERITANCE_MARKER)) {
            throw new MissingResourceException("Encountered NO_INHERITANCE_MARKER", str, getKey());
        }
        return strFindStringWithFallback;
    }

    public UResource.Value getValueWithFallback(String str) throws MissingResourceException {
        ICUResourceBundle iCUResourceBundleFindResourceWithFallback;
        if (str.isEmpty()) {
            iCUResourceBundleFindResourceWithFallback = this;
        } else {
            iCUResourceBundleFindResourceWithFallback = findResourceWithFallback(str, this, null);
            if (iCUResourceBundleFindResourceWithFallback == null) {
                throw new MissingResourceException("Can't find resource for bundle " + getClass().getName() + ", key " + getType(), str, getKey());
            }
        }
        ICUResourceBundleReader.ReaderValue readerValue = new ICUResourceBundleReader.ReaderValue();
        ICUResourceBundleImpl iCUResourceBundleImpl = (ICUResourceBundleImpl) iCUResourceBundleFindResourceWithFallback;
        readerValue.reader = iCUResourceBundleImpl.wholeBundle.reader;
        readerValue.res = iCUResourceBundleImpl.getResource();
        return readerValue;
    }

    public void getAllItemsWithFallbackNoFail(String str, UResource.Sink sink) {
        try {
            getAllItemsWithFallback(str, sink);
        } catch (MissingResourceException unused) {
        }
    }

    public void getAllItemsWithFallback(String str, UResource.Sink sink) throws MissingResourceException {
        ICUResourceBundle iCUResourceBundleFindResourceWithFallback;
        int iCountPathKeys = countPathKeys(str);
        if (iCountPathKeys == 0) {
            iCUResourceBundleFindResourceWithFallback = this;
        } else {
            int resDepth = getResDepth();
            String[] strArr = new String[resDepth + iCountPathKeys];
            getResPathKeys(str, iCountPathKeys, strArr, resDepth);
            iCUResourceBundleFindResourceWithFallback = findResourceWithFallback(strArr, resDepth, this, null);
            if (iCUResourceBundleFindResourceWithFallback == null) {
                throw new MissingResourceException("Can't find resource for bundle " + getClass().getName() + ", key " + getType(), str, getKey());
            }
        }
        iCUResourceBundleFindResourceWithFallback.getAllItemsWithFallback(new UResource.Key(), new ICUResourceBundleReader.ReaderValue(), sink);
    }

    private void getAllItemsWithFallback(UResource.Key key, ICUResourceBundleReader.ReaderValue readerValue, UResource.Sink sink) {
        ICUResourceBundleImpl iCUResourceBundleImpl = (ICUResourceBundleImpl) this;
        readerValue.reader = iCUResourceBundleImpl.wholeBundle.reader;
        readerValue.res = iCUResourceBundleImpl.getResource();
        String str = this.key;
        if (str == null) {
            str = "";
        }
        key.setString(str);
        sink.put(key, readerValue, this.parent == null);
        if (this.parent != null) {
            ICUResourceBundle iCUResourceBundleFindResourceWithFallback = (ICUResourceBundle) this.parent;
            int resDepth = getResDepth();
            if (resDepth != 0) {
                String[] strArr = new String[resDepth];
                getResPathKeys(strArr, resDepth);
                iCUResourceBundleFindResourceWithFallback = findResourceWithFallback(strArr, 0, iCUResourceBundleFindResourceWithFallback, null);
            }
            if (iCUResourceBundleFindResourceWithFallback != null) {
                iCUResourceBundleFindResourceWithFallback.getAllItemsWithFallback(key, readerValue, sink);
            }
        }
    }

    public static Set<String> getAvailableLocaleNameSet(String str, ClassLoader classLoader) {
        return getAvailEntry(str, classLoader).getLocaleNameSet();
    }

    public static Set<String> getFullLocaleNameSet() {
        return getFullLocaleNameSet(ICUData.ICU_BASE_NAME, ICU_DATA_CLASS_LOADER);
    }

    public static Set<String> getFullLocaleNameSet(String str, ClassLoader classLoader) {
        return getAvailEntry(str, classLoader).getFullLocaleNameSet();
    }

    public static Set<String> getAvailableLocaleNameSet() {
        return getAvailableLocaleNameSet(ICUData.ICU_BASE_NAME, ICU_DATA_CLASS_LOADER);
    }

    public static final ULocale[] getAvailableULocales(String str, ClassLoader classLoader, ULocale.AvailableType availableType) {
        return getAvailEntry(str, classLoader).getULocaleList(availableType);
    }

    public static final ULocale[] getAvailableULocales() {
        return getAvailableULocales(ICUData.ICU_BASE_NAME, ICU_DATA_CLASS_LOADER, ULocale.AvailableType.DEFAULT);
    }

    public static final ULocale[] getAvailableULocales(ULocale.AvailableType availableType) {
        return getAvailableULocales(ICUData.ICU_BASE_NAME, ICU_DATA_CLASS_LOADER, availableType);
    }

    public static final ULocale[] getAvailableULocales(String str, ClassLoader classLoader) {
        return getAvailableULocales(str, classLoader, ULocale.AvailableType.DEFAULT);
    }

    public static final Locale[] getAvailableLocales(String str, ClassLoader classLoader, ULocale.AvailableType availableType) {
        return getAvailEntry(str, classLoader).getLocaleList(availableType);
    }

    public static final Locale[] getAvailableLocales() {
        return getAvailableLocales(ICUData.ICU_BASE_NAME, ICU_DATA_CLASS_LOADER, ULocale.AvailableType.DEFAULT);
    }

    public static final Locale[] getAvailableLocales(ULocale.AvailableType availableType) {
        return getAvailableLocales(ICUData.ICU_BASE_NAME, ICU_DATA_CLASS_LOADER, availableType);
    }

    public static final Locale[] getAvailableLocales(String str, ClassLoader classLoader) {
        return getAvailableLocales(str, classLoader, ULocale.AvailableType.DEFAULT);
    }

    public static final Locale[] getLocaleList(ULocale[] uLocaleArr) {
        ArrayList arrayList = new ArrayList(uLocaleArr.length);
        HashSet hashSet = new HashSet();
        for (ULocale uLocale : uLocaleArr) {
            Locale locale = uLocale.toLocale();
            if (!hashSet.contains(locale)) {
                arrayList.add(locale);
                hashSet.add(locale);
            }
        }
        return (Locale[]) arrayList.toArray(new Locale[arrayList.size()]);
    }

    @Override // com.ibm.icu.util.UResourceBundle, java.util.ResourceBundle
    public Locale getLocale() {
        return getULocale().toLocale();
    }

    private static final class AvailableLocalesSink extends UResource.Sink {
        EnumMap<ULocale.AvailableType, ULocale[]> output;

        public AvailableLocalesSink(EnumMap<ULocale.AvailableType, ULocale[]> enumMap) {
            this.output = enumMap;
        }

        @Override // com.ibm.icu.impl.UResource.Sink
        public void put(UResource.Key key, UResource.Value value, boolean z) {
            ULocale.AvailableType availableType;
            UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                if (key.contentEquals(ICUResourceBundle.INSTALLED_LOCALES)) {
                    availableType = ULocale.AvailableType.DEFAULT;
                } else if (key.contentEquals("AliasLocales")) {
                    availableType = ULocale.AvailableType.ONLY_LEGACY_ALIASES;
                }
                UResource.Table table2 = value.getTable();
                ULocale[] uLocaleArr = new ULocale[table2.getSize()];
                for (int i2 = 0; table2.getKeyAndValue(i2, key, value); i2++) {
                    uLocaleArr[i2] = new ULocale(key.toString());
                }
                this.output.put((EnumMap<ULocale.AvailableType, ULocale[]>) availableType, (ULocale.AvailableType) uLocaleArr);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final EnumMap<ULocale.AvailableType, ULocale[]> createULocaleList(String str, ClassLoader classLoader) throws MissingResourceException {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) UResourceBundle.instantiateBundle(str, ICU_RESOURCE_INDEX, classLoader, true);
        EnumMap<ULocale.AvailableType, ULocale[]> enumMap = new EnumMap<>(ULocale.AvailableType.class);
        iCUResourceBundle.getAllItemsWithFallback("", new AvailableLocalesSink(enumMap));
        return enumMap;
    }

    private static final void addLocaleIDsFromIndexBundle(String str, ClassLoader classLoader, Set<String> set) {
        try {
            UResourceBundleIterator iterator = ((ICUResourceBundle) ((ICUResourceBundle) UResourceBundle.instantiateBundle(str, ICU_RESOURCE_INDEX, classLoader, true)).get(INSTALLED_LOCALES)).getIterator();
            iterator.reset();
            while (iterator.hasNext()) {
                set.add(iterator.next().getKey());
            }
        } catch (MissingResourceException unused) {
            if (DEBUG) {
                System.out.println("couldn't find " + str + '/' + ICU_RESOURCE_INDEX + ".res");
                Thread.dumpStack();
            }
        }
    }

    private static final void addBundleBaseNamesFromClassLoader(final String str, final ClassLoader classLoader, final Set<String> set) {
        AccessController.doPrivileged(new PrivilegedAction<Void>() { // from class: com.ibm.icu.impl.ICUResourceBundle.2
            @Override // java.security.PrivilegedAction
            public Void run() throws IOException {
                Enumeration<URL> resources;
                try {
                    resources = classLoader.getResources(str);
                } catch (IOException e) {
                    if (ICUResourceBundle.DEBUG) {
                        System.out.println("ouch: " + e.getMessage());
                    }
                }
                if (resources == null) {
                    return null;
                }
                URLHandler.URLVisitor uRLVisitor = new URLHandler.URLVisitor() { // from class: com.ibm.icu.impl.ICUResourceBundle.2.1
                    @Override // com.ibm.icu.impl.URLHandler.URLVisitor
                    public void visit(String str2) {
                        if (str2.endsWith(".res")) {
                            set.add(str2.substring(0, str2.length() - 4));
                        }
                    }
                };
                while (resources.hasMoreElements()) {
                    URL urlNextElement = resources.nextElement();
                    URLHandler uRLHandler = URLHandler.get(urlNextElement);
                    if (uRLHandler == null) {
                        if (ICUResourceBundle.DEBUG) {
                            System.out.println("handler for " + urlNextElement + " is null");
                        }
                    } else {
                        uRLHandler.guide(uRLVisitor, false);
                    }
                }
                return null;
            }
        });
    }

    private static void addLocaleIDsFromListFile(String str, ClassLoader classLoader, Set<String> set) throws IOException {
        try {
            InputStream resourceAsStream = classLoader.getResourceAsStream(str + FULL_LOCALE_NAMES_LIST);
            if (resourceAsStream == null) {
                return;
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream, HTTP.ASCII));
            while (true) {
                try {
                    String line = bufferedReader.readLine();
                    if (line != null) {
                        if (line.length() != 0 && !line.startsWith("#")) {
                            set.add(line);
                        }
                    } else {
                        bufferedReader.close();
                        return;
                    }
                } catch (Throwable th) {
                    bufferedReader.close();
                    throw th;
                }
            }
        } catch (IOException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Set<String> createFullLocaleNameSet(String str, ClassLoader classLoader) throws IOException {
        String str2;
        String strSubstring;
        if (str.endsWith("/")) {
            str2 = str;
        } else {
            str2 = str + "/";
        }
        HashSet hashSet = new HashSet();
        if (!ICUConfig.get("com.ibm.icu.impl.ICUResourceBundle.skipRuntimeLocaleResourceScan", "false").equalsIgnoreCase(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE)) {
            addBundleBaseNamesFromClassLoader(str2, classLoader, hashSet);
            if (str.startsWith(ICUData.ICU_BASE_NAME)) {
                if (str.length() == 30) {
                    strSubstring = "";
                } else {
                    strSubstring = str.charAt(30) == '/' ? str.substring(31) : null;
                }
                if (strSubstring != null) {
                    ICUBinary.addBaseNamesInFileFolder(strSubstring, ".res", hashSet);
                }
            }
            hashSet.remove(ICU_RESOURCE_INDEX);
            Iterator it2 = hashSet.iterator();
            while (it2.hasNext()) {
                String str3 = (String) it2.next();
                if (str3.length() == 1 || str3.length() > 3) {
                    if (str3.indexOf(95) < 0) {
                        it2.remove();
                    }
                }
            }
        }
        if (hashSet.isEmpty()) {
            if (DEBUG) {
                System.out.println("unable to enumerate data files in " + str);
            }
            addLocaleIDsFromListFile(str2, classLoader, hashSet);
        }
        if (hashSet.isEmpty()) {
            addLocaleIDsFromIndexBundle(str, classLoader, hashSet);
        }
        hashSet.remove("root");
        hashSet.add(ULocale.ROOT.toString());
        return Collections.unmodifiableSet(hashSet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Set<String> createLocaleNameSet(String str, ClassLoader classLoader) {
        HashSet hashSet = new HashSet();
        addLocaleIDsFromIndexBundle(str, classLoader, hashSet);
        return Collections.unmodifiableSet(hashSet);
    }

    private static final class AvailEntry {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private volatile Set<String> fullNameSet;
        private ClassLoader loader;
        private volatile Locale[] locales;
        private volatile Set<String> nameSet;
        private String prefix;
        private volatile EnumMap<ULocale.AvailableType, ULocale[]> ulocales;

        AvailEntry(String str, ClassLoader classLoader) {
            this.prefix = str;
            this.loader = classLoader;
        }

        ULocale[] getULocaleList(ULocale.AvailableType availableType) {
            if (this.ulocales == null) {
                synchronized (this) {
                    if (this.ulocales == null) {
                        this.ulocales = ICUResourceBundle.createULocaleList(this.prefix, this.loader);
                    }
                }
            }
            return this.ulocales.get(availableType);
        }

        Locale[] getLocaleList(ULocale.AvailableType availableType) {
            if (this.locales == null) {
                getULocaleList(availableType);
                synchronized (this) {
                    if (this.locales == null) {
                        this.locales = ICUResourceBundle.getLocaleList(this.ulocales.get(availableType));
                    }
                }
            }
            return this.locales;
        }

        Set<String> getLocaleNameSet() {
            if (this.nameSet == null) {
                synchronized (this) {
                    if (this.nameSet == null) {
                        this.nameSet = ICUResourceBundle.createLocaleNameSet(this.prefix, this.loader);
                    }
                }
            }
            return this.nameSet;
        }

        Set<String> getFullLocaleNameSet() {
            if (this.fullNameSet == null) {
                synchronized (this) {
                    if (this.fullNameSet == null) {
                        this.fullNameSet = ICUResourceBundle.createFullLocaleNameSet(this.prefix, this.loader);
                    }
                }
            }
            return this.fullNameSet;
        }
    }

    private static AvailEntry getAvailEntry(String str, ClassLoader classLoader) {
        return GET_AVAILABLE_CACHE.getInstance(str, classLoader);
    }

    private static final ICUResourceBundle findResourceWithFallback(String str, UResourceBundle uResourceBundle, UResourceBundle uResourceBundle2) {
        if (str.length() == 0) {
            return null;
        }
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) uResourceBundle;
        int resDepth = iCUResourceBundle.getResDepth();
        int iCountPathKeys = countPathKeys(str);
        String[] strArr = new String[resDepth + iCountPathKeys];
        getResPathKeys(str, iCountPathKeys, strArr, resDepth);
        return findResourceWithFallback(strArr, resDepth, iCUResourceBundle, uResourceBundle2);
    }

    private static final ICUResourceBundle findResourceWithFallback(String[] strArr, int i, ICUResourceBundle iCUResourceBundle, UResourceBundle uResourceBundle) {
        if (uResourceBundle == null) {
            uResourceBundle = iCUResourceBundle;
        }
        while (true) {
            int i2 = i + 1;
            ICUResourceBundle iCUResourceBundle2 = (ICUResourceBundle) iCUResourceBundle.handleGet(strArr[i], (HashMap<String, String>) null, uResourceBundle);
            if (iCUResourceBundle2 != null) {
                if (i2 == strArr.length) {
                    return iCUResourceBundle2;
                }
                iCUResourceBundle = iCUResourceBundle2;
                i = i2;
            } else {
                int i3 = i2 - 1;
                ICUResourceBundle parent = iCUResourceBundle.getParent();
                if (parent == null) {
                    return null;
                }
                int resDepth = iCUResourceBundle.getResDepth();
                if (i3 != resDepth) {
                    String[] strArr2 = new String[(strArr.length - i3) + resDepth];
                    System.arraycopy(strArr, i3, strArr2, resDepth, strArr.length - i3);
                    strArr = strArr2;
                }
                iCUResourceBundle.getResPathKeys(strArr, resDepth);
                iCUResourceBundle = parent;
                i = 0;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x009b, code lost:
    
        r0 = r3.wholeBundle.reader;
        r4 = r3.getResDepth();
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00a3, code lost:
    
        if (r14 == r4) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00a5, code lost:
    
        r5 = new java.lang.String[(r13.length - r14) + r4];
        java.lang.System.arraycopy(r13, r14, r5, r4, r13.length - r14);
        r11 = r0;
        r0 = r3;
        r12 = r4;
        r13 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00b5, code lost:
    
        r11 = r0;
        r0 = r3;
        r12 = r4;
        r4 = r14;
     */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00c6 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x006a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0067 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static final java.lang.String findStringWithFallback(java.lang.String r16, com.ibm.icu.util.UResourceBundle r17, com.ibm.icu.util.UResourceBundle r18) {
        /*
            Method dump skipped, instructions count: 211
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.ICUResourceBundle.findStringWithFallback(java.lang.String, com.ibm.icu.util.UResourceBundle, com.ibm.icu.util.UResourceBundle):java.lang.String");
    }

    private int getResDepth() {
        ICUResourceBundle iCUResourceBundle = this.container;
        if (iCUResourceBundle == null) {
            return 0;
        }
        return iCUResourceBundle.getResDepth() + 1;
    }

    private void getResPathKeys(String[] strArr, int i) {
        ICUResourceBundle iCUResourceBundle = this;
        while (i > 0) {
            i--;
            strArr[i] = iCUResourceBundle.key;
            iCUResourceBundle = iCUResourceBundle.container;
        }
    }

    private static int countPathKeys(String str) {
        if (str.isEmpty()) {
            return 0;
        }
        int i = 1;
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (str.charAt(i2) == '/') {
                i++;
            }
        }
        return i;
    }

    private static void getResPathKeys(String str, int i, String[] strArr, int i2) {
        if (i == 0) {
            return;
        }
        if (i == 1) {
            strArr[i2] = str;
            return;
        }
        int i3 = 0;
        while (true) {
            int iIndexOf = str.indexOf(47, i3);
            int i4 = i2 + 1;
            strArr[i2] = str.substring(i3, iIndexOf);
            if (i == 2) {
                strArr[i4] = str.substring(iIndexOf + 1);
                return;
            } else {
                i3 = iIndexOf + 1;
                i--;
                i2 = i4;
            }
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ICUResourceBundle)) {
            return false;
        }
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) obj;
        return getBaseName().equals(iCUResourceBundle.getBaseName()) && getLocaleID().equals(iCUResourceBundle.getLocaleID());
    }

    public static ICUResourceBundle getBundleInstance(String str, String str2, ClassLoader classLoader, boolean z) {
        return getBundleInstance(str, str2, classLoader, z ? OpenType.DIRECT : OpenType.LOCALE_DEFAULT_ROOT);
    }

    public static ICUResourceBundle getBundleInstance(String str, ULocale uLocale, OpenType openType) {
        if (uLocale == null) {
            uLocale = ULocale.getDefault();
        }
        return getBundleInstance(str, uLocale.getBaseName(), ICU_DATA_CLASS_LOADER, openType);
    }

    public static ICUResourceBundle getBundleInstance(String str, String str2, ClassLoader classLoader, OpenType openType) {
        ICUResourceBundle iCUResourceBundleInstantiateBundle;
        if (str == null) {
            str = ICUData.ICU_BASE_NAME;
        }
        String baseName = ULocale.getBaseName(str2);
        if (openType == OpenType.LOCALE_DEFAULT_ROOT) {
            iCUResourceBundleInstantiateBundle = instantiateBundle(str, baseName, ULocale.getDefault().getBaseName(), classLoader, openType);
        } else {
            iCUResourceBundleInstantiateBundle = instantiateBundle(str, baseName, null, classLoader, openType);
        }
        if (iCUResourceBundleInstantiateBundle != null) {
            return iCUResourceBundleInstantiateBundle;
        }
        throw new MissingResourceException("Could not find the bundle " + str + "/" + baseName + ".res", "", "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean localeIDStartsWithLangSubtag(String str, String str2) {
        return str.startsWith(str2) && (str.length() == str2.length() || str.charAt(str2.length()) == '_');
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ICUResourceBundle instantiateBundle(final String str, final String str2, final String str3, final ClassLoader classLoader, final OpenType openType) {
        StringBuilder sb;
        final String fullName = ICUResourceBundleReader.getFullName(str, str2);
        char cOrdinal = (char) (openType.ordinal() + 48);
        if (openType != OpenType.LOCALE_DEFAULT_ROOT) {
            sb = new StringBuilder();
            sb.append(fullName);
            sb.append('#');
            sb.append(cOrdinal);
        } else {
            sb = new StringBuilder();
            sb.append(fullName);
            sb.append('#');
            sb.append(cOrdinal);
            sb.append('#');
            sb.append(str3);
        }
        return BUNDLE_CACHE.getInstance(sb.toString(), new Loader() { // from class: com.ibm.icu.impl.ICUResourceBundle.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.ibm.icu.impl.ICUResourceBundle.Loader
            public ICUResourceBundle load() {
                ICUResourceBundle iCUResourceBundleCreateBundle;
                if (ICUResourceBundle.DEBUG) {
                    System.out.println("Creating " + fullName);
                }
                String str4 = str.indexOf(46) == -1 ? "root" : "";
                String str5 = str2.isEmpty() ? str4 : str2;
                ICUResourceBundle iCUResourceBundleCreateBundle2 = ICUResourceBundle.createBundle(str, str5, classLoader);
                if (ICUResourceBundle.DEBUG) {
                    PrintStream printStream = System.out;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("The bundle created is: ");
                    sb2.append(iCUResourceBundleCreateBundle2);
                    sb2.append(" and openType=");
                    sb2.append(openType);
                    sb2.append(" and bundle.getNoFallback=");
                    sb2.append(iCUResourceBundleCreateBundle2 != null && iCUResourceBundleCreateBundle2.getNoFallback());
                    printStream.println(sb2.toString());
                }
                if (openType == OpenType.DIRECT) {
                    return iCUResourceBundleCreateBundle2;
                }
                if (iCUResourceBundleCreateBundle2 != null && iCUResourceBundleCreateBundle2.getNoFallback()) {
                    return iCUResourceBundleCreateBundle2;
                }
                if (iCUResourceBundleCreateBundle2 == null) {
                    int iLastIndexOf = str5.lastIndexOf(95);
                    if (iLastIndexOf != -1) {
                        iCUResourceBundleCreateBundle = ICUResourceBundle.instantiateBundle(str, str5.substring(0, iLastIndexOf), str3, classLoader, openType);
                    } else if (openType == OpenType.LOCALE_DEFAULT_ROOT && !ICUResourceBundle.localeIDStartsWithLangSubtag(str3, str5)) {
                        String str6 = str;
                        String str7 = str3;
                        iCUResourceBundleCreateBundle = ICUResourceBundle.instantiateBundle(str6, str7, str7, classLoader, openType);
                    } else {
                        if (openType == OpenType.LOCALE_ONLY || str4.isEmpty()) {
                            return iCUResourceBundleCreateBundle2;
                        }
                        iCUResourceBundleCreateBundle = ICUResourceBundle.createBundle(str, str4, classLoader);
                    }
                    return iCUResourceBundleCreateBundle;
                }
                ICUResourceBundle iCUResourceBundleInstantiateBundle = null;
                String localeID = iCUResourceBundleCreateBundle2.getLocaleID();
                int iLastIndexOf2 = localeID.lastIndexOf(95);
                String strFindString = ((ICUResourceBundleImpl.ResourceTable) iCUResourceBundleCreateBundle2).findString("%%Parent");
                if (strFindString != null) {
                    iCUResourceBundleInstantiateBundle = ICUResourceBundle.instantiateBundle(str, strFindString, str3, classLoader, openType);
                } else if (iLastIndexOf2 != -1) {
                    iCUResourceBundleInstantiateBundle = ICUResourceBundle.instantiateBundle(str, localeID.substring(0, iLastIndexOf2), str3, classLoader, openType);
                } else if (!localeID.equals(str4)) {
                    iCUResourceBundleInstantiateBundle = ICUResourceBundle.instantiateBundle(str, str4, str3, classLoader, openType);
                }
                if (iCUResourceBundleCreateBundle2.equals(iCUResourceBundleInstantiateBundle)) {
                    return iCUResourceBundleCreateBundle2;
                }
                iCUResourceBundleCreateBundle2.setParent(iCUResourceBundleInstantiateBundle);
                return iCUResourceBundleCreateBundle2;
            }
        });
    }

    ICUResourceBundle get(String str, HashMap<String, String> map, UResourceBundle uResourceBundle) {
        ICUResourceBundle parent = (ICUResourceBundle) handleGet(str, map, uResourceBundle);
        if (parent == null) {
            parent = getParent();
            if (parent != null) {
                parent = parent.get(str, map, uResourceBundle);
            }
            if (parent == null) {
                throw new MissingResourceException("Can't find resource for bundle " + ICUResourceBundleReader.getFullName(getBaseName(), getLocaleID()) + ", key " + str, getClass().getName(), str);
            }
        }
        return parent;
    }

    public static ICUResourceBundle createBundle(String str, String str2, ClassLoader classLoader) {
        ICUResourceBundleReader reader = ICUResourceBundleReader.getReader(str, str2, classLoader);
        if (reader == null) {
            return null;
        }
        return getBundle(reader, str, str2, classLoader);
    }

    @Override // com.ibm.icu.util.UResourceBundle
    protected String getLocaleID() {
        return this.wholeBundle.localeID;
    }

    @Override // com.ibm.icu.util.UResourceBundle
    protected String getBaseName() {
        return this.wholeBundle.baseName;
    }

    @Override // com.ibm.icu.util.UResourceBundle
    public ULocale getULocale() {
        return this.wholeBundle.ulocale;
    }

    public boolean isRoot() {
        return this.wholeBundle.localeID.isEmpty() || this.wholeBundle.localeID.equals("root");
    }

    @Override // com.ibm.icu.util.UResourceBundle
    public ICUResourceBundle getParent() {
        return (ICUResourceBundle) this.parent;
    }

    @Override // java.util.ResourceBundle
    protected void setParent(ResourceBundle resourceBundle) {
        this.parent = resourceBundle;
    }

    @Override // com.ibm.icu.util.UResourceBundle
    public String getKey() {
        return this.key;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getNoFallback() {
        return this.wholeBundle.reader.getNoFallback();
    }

    private static ICUResourceBundle getBundle(ICUResourceBundleReader iCUResourceBundleReader, String str, String str2, ClassLoader classLoader) {
        int rootResource = iCUResourceBundleReader.getRootResource();
        if (ICUResourceBundleReader.URES_IS_TABLE(ICUResourceBundleReader.RES_GET_TYPE(rootResource))) {
            ICUResourceBundleImpl.ResourceTable resourceTable = new ICUResourceBundleImpl.ResourceTable(new WholeBundle(str, str2, classLoader, iCUResourceBundleReader), rootResource);
            String strFindString = resourceTable.findString("%%ALIAS");
            return strFindString != null ? (ICUResourceBundle) UResourceBundle.getBundleInstance(str, strFindString) : resourceTable;
        }
        throw new IllegalStateException("Invalid format error");
    }

    protected ICUResourceBundle(WholeBundle wholeBundle) {
        this.wholeBundle = wholeBundle;
    }

    protected ICUResourceBundle(ICUResourceBundle iCUResourceBundle, String str) {
        this.key = str;
        this.wholeBundle = iCUResourceBundle.wholeBundle;
        this.container = iCUResourceBundle;
        this.parent = iCUResourceBundle.parent;
    }

    protected static ICUResourceBundle getAliasedResource(ICUResourceBundle iCUResourceBundle, String[] strArr, int i, String str, int i2, HashMap<String, String> map, UResourceBundle uResourceBundle) {
        String strSubstring;
        String strSubstring2;
        String strSubstring3;
        int iCountPathKeys;
        String[] strArr2;
        int iIndexOf;
        WholeBundle wholeBundle = iCUResourceBundle.wholeBundle;
        ClassLoader classLoader = wholeBundle.loader;
        String alias = wholeBundle.reader.getAlias(i2);
        HashMap<String, String> map2 = map == null ? new HashMap<>() : map;
        if (map2.get(alias) != null) {
            throw new IllegalArgumentException("Circular references in the resource bundles");
        }
        map2.put(alias, "");
        ICUResourceBundle iCUResourceBundleFindResourceWithFallback = null;
        if (alias.indexOf(47) == 0) {
            int iIndexOf2 = alias.indexOf(47, 1);
            int i3 = iIndexOf2 + 1;
            int iIndexOf3 = alias.indexOf(47, i3);
            strSubstring3 = alias.substring(1, iIndexOf2);
            if (iIndexOf3 < 0) {
                strSubstring = alias.substring(i3);
                strSubstring2 = null;
            } else {
                String strSubstring4 = alias.substring(i3, iIndexOf3);
                strSubstring2 = alias.substring(iIndexOf3 + 1, alias.length());
                strSubstring = strSubstring4;
            }
            if (strSubstring3.equals(ICUDATA)) {
                classLoader = ICU_DATA_CLASS_LOADER;
                strSubstring3 = ICUData.ICU_BASE_NAME;
            } else if (strSubstring3.indexOf(ICUDATA) > -1 && (iIndexOf = strSubstring3.indexOf(45)) > -1) {
                strSubstring3 = "com/ibm/icu/impl/data/icudt69b/" + strSubstring3.substring(iIndexOf + 1, strSubstring3.length());
                classLoader = ICU_DATA_CLASS_LOADER;
            }
        } else {
            int iIndexOf4 = alias.indexOf(47);
            if (iIndexOf4 != -1) {
                String strSubstring5 = alias.substring(0, iIndexOf4);
                strSubstring2 = alias.substring(iIndexOf4 + 1);
                strSubstring = strSubstring5;
            } else {
                strSubstring = alias;
                strSubstring2 = null;
            }
            strSubstring3 = wholeBundle.baseName;
        }
        if (strSubstring3.equals(LOCALE)) {
            String str2 = wholeBundle.baseName;
            String strSubstring6 = alias.substring(8, alias.length());
            ICUResourceBundle iCUResourceBundle2 = (ICUResourceBundle) uResourceBundle;
            while (true) {
                ICUResourceBundle iCUResourceBundle3 = iCUResourceBundle2.container;
                if (iCUResourceBundle3 == null) {
                    break;
                }
                iCUResourceBundle2 = iCUResourceBundle3;
            }
            iCUResourceBundleFindResourceWithFallback = findResourceWithFallback(strSubstring6, iCUResourceBundle2, null);
        } else {
            ICUResourceBundle bundleInstance = getBundleInstance(strSubstring3, strSubstring, classLoader, false);
            if (strSubstring2 != null) {
                iCountPathKeys = countPathKeys(strSubstring2);
                if (iCountPathKeys > 0) {
                    strArr2 = new String[iCountPathKeys];
                    getResPathKeys(strSubstring2, iCountPathKeys, strArr2, 0);
                } else {
                    strArr2 = strArr;
                }
            } else if (strArr != null) {
                strArr2 = strArr;
                iCountPathKeys = i;
            } else {
                int resDepth = iCUResourceBundle.getResDepth();
                int i4 = resDepth + 1;
                String[] strArr3 = new String[i4];
                iCUResourceBundle.getResPathKeys(strArr3, resDepth);
                strArr3[resDepth] = str;
                iCountPathKeys = i4;
                strArr2 = strArr3;
            }
            if (iCountPathKeys > 0) {
                iCUResourceBundleFindResourceWithFallback = bundleInstance;
                for (int i5 = 0; iCUResourceBundleFindResourceWithFallback != null && i5 < iCountPathKeys; i5++) {
                    iCUResourceBundleFindResourceWithFallback = iCUResourceBundleFindResourceWithFallback.get(strArr2[i5], map2, uResourceBundle);
                }
            }
        }
        if (iCUResourceBundleFindResourceWithFallback != null) {
            return iCUResourceBundleFindResourceWithFallback;
        }
        throw new MissingResourceException(wholeBundle.localeID, wholeBundle.baseName, str);
    }

    @Deprecated
    public final Set<String> getTopLevelKeySet() {
        return this.wholeBundle.topLevelKeys;
    }

    @Deprecated
    public final void setTopLevelKeySet(Set<String> set) {
        this.wholeBundle.topLevelKeys = set;
    }

    @Override // com.ibm.icu.util.UResourceBundle
    protected Enumeration<String> handleGetKeys() {
        return Collections.enumeration(handleKeySet());
    }

    @Override // com.ibm.icu.util.UResourceBundle
    protected boolean isTopLevelResource() {
        return this.container == null;
    }
}
