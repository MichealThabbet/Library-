package com.ibm.icu.impl;

import com.ibm.icu.impl.locale.BaseLocale;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.commons.io.IOUtils;

/* loaded from: classes2.dex */
public final class ResourceBundleWrapper extends UResourceBundle {
    private static CacheBase<String, ResourceBundleWrapper, Loader> BUNDLE_CACHE = new SoftCache<String, ResourceBundleWrapper, Loader>() { // from class: com.ibm.icu.impl.ResourceBundleWrapper.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ibm.icu.impl.CacheBase
        public ResourceBundleWrapper createInstance(String str, Loader loader) {
            return loader.load();
        }
    };
    private static final boolean DEBUG = ICUDebug.enabled("resourceBundleWrapper");
    private String baseName;
    private ResourceBundle bundle;
    private List<String> keys;
    private String localeID;

    private static abstract class Loader {
        abstract ResourceBundleWrapper load();

        private Loader() {
        }
    }

    private ResourceBundleWrapper(ResourceBundle resourceBundle) {
        this.bundle = null;
        this.localeID = null;
        this.baseName = null;
        this.keys = null;
        this.bundle = resourceBundle;
    }

    @Override // com.ibm.icu.util.UResourceBundle, java.util.ResourceBundle
    protected Object handleGetObject(String str) {
        Object object;
        ResourceBundleWrapper resourceBundleWrapper = this;
        while (true) {
            if (resourceBundleWrapper == null) {
                object = null;
                break;
            }
            try {
                object = resourceBundleWrapper.bundle.getObject(str);
                break;
            } catch (MissingResourceException unused) {
                resourceBundleWrapper = (ResourceBundleWrapper) resourceBundleWrapper.getParent();
            }
        }
        if (object != null) {
            return object;
        }
        throw new MissingResourceException("Can't find resource for bundle " + this.baseName + ", key " + str, getClass().getName(), str);
    }

    @Override // com.ibm.icu.util.UResourceBundle, java.util.ResourceBundle
    public Enumeration<String> getKeys() {
        return Collections.enumeration(this.keys);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initKeysVector() {
        this.keys = new ArrayList();
        for (ResourceBundleWrapper resourceBundleWrapper = this; resourceBundleWrapper != null; resourceBundleWrapper = (ResourceBundleWrapper) resourceBundleWrapper.getParent()) {
            Enumeration<String> keys = resourceBundleWrapper.bundle.getKeys();
            while (keys.hasMoreElements()) {
                String strNextElement = keys.nextElement();
                if (!this.keys.contains(strNextElement)) {
                    this.keys.add(strNextElement);
                }
            }
        }
    }

    @Override // com.ibm.icu.util.UResourceBundle
    protected String getLocaleID() {
        return this.localeID;
    }

    @Override // com.ibm.icu.util.UResourceBundle
    protected String getBaseName() {
        return this.bundle.getClass().getName().replace('.', IOUtils.DIR_SEPARATOR_UNIX);
    }

    @Override // com.ibm.icu.util.UResourceBundle
    public ULocale getULocale() {
        return new ULocale(this.localeID);
    }

    @Override // com.ibm.icu.util.UResourceBundle
    public UResourceBundle getParent() {
        return (UResourceBundle) this.parent;
    }

    public static ResourceBundleWrapper getBundleInstance(String str, String str2, ClassLoader classLoader, boolean z) {
        ResourceBundleWrapper resourceBundleWrapperInstantiateBundle;
        if (classLoader == null) {
            classLoader = ClassLoaderUtil.getClassLoader();
        }
        if (z) {
            resourceBundleWrapperInstantiateBundle = instantiateBundle(str, str2, null, classLoader, z);
        } else {
            resourceBundleWrapperInstantiateBundle = instantiateBundle(str, str2, ULocale.getDefault().getBaseName(), classLoader, z);
        }
        if (resourceBundleWrapperInstantiateBundle != null) {
            return resourceBundleWrapperInstantiateBundle;
        }
        throw new MissingResourceException("Could not find the bundle " + str + (str.indexOf(47) >= 0 ? "/" : BaseLocale.SEP) + str2, "", "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean localeIDStartsWithLangSubtag(String str, String str2) {
        return str.startsWith(str2) && (str.length() == str2.length() || str.charAt(str2.length()) == '_');
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ResourceBundleWrapper instantiateBundle(final String str, final String str2, final String str3, final ClassLoader classLoader, final boolean z) {
        final String str4;
        String str5;
        if (str2.isEmpty()) {
            str4 = str;
        } else {
            str4 = str + '_' + str2;
        }
        if (z) {
            str5 = str4;
        } else {
            str5 = str4 + '#' + str3;
        }
        return BUNDLE_CACHE.getInstance(str5, new Loader() { // from class: com.ibm.icu.impl.ResourceBundleWrapper.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX WARN: Can't wrap try/catch for region: R(13:0|2|(1:4)(2:5|(9:7|91|10|(2:93|12)|13|26|(6:86|29|(8:31|89|32|(2:87|34)|35|82|36|37)|(1:56)|(1:62)|63)|(1:72)(2:73|(1:75))|76)(1:8))|9|91|10|(0)|13|26|(0)|(0)(0)|76|(1:(0))) */
            /* JADX WARN: Code restructure failed: missing block: B:18:0x006a, code lost:
            
                r3 = e;
             */
            /* JADX WARN: Code restructure failed: missing block: B:19:0x006b, code lost:
            
                r8 = null;
             */
            /* JADX WARN: Code restructure failed: missing block: B:27:0x0084, code lost:
            
                r8 = null;
             */
            /* JADX WARN: Removed duplicated region for block: B:62:0x0118  */
            /* JADX WARN: Removed duplicated region for block: B:72:0x0134  */
            /* JADX WARN: Removed duplicated region for block: B:73:0x0138  */
            /* JADX WARN: Removed duplicated region for block: B:86:0x0087 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:93:0x0058 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            @Override // com.ibm.icu.impl.ResourceBundleWrapper.Loader
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public com.ibm.icu.impl.ResourceBundleWrapper load() throws java.lang.Throwable {
                /*
                    Method dump skipped, instructions count: 353
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.ResourceBundleWrapper.AnonymousClass2.load():com.ibm.icu.impl.ResourceBundleWrapper");
            }
        });
    }
}
