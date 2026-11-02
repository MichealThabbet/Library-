package com.ibm.icu.text;

import com.ibm.icu.impl.ICUConfig;
import com.ibm.icu.lang.UScript;
import com.ibm.icu.text.DisplayContext;
import com.ibm.icu.util.ULocale;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* loaded from: classes2.dex */
public abstract class LocaleDisplayNames {
    private static final Method FACTORY_DIALECTHANDLING;
    private static final Method FACTORY_DISPLAYCONTEXT;

    public enum DialectHandling {
        STANDARD_NAMES,
        DIALECT_NAMES
    }

    public abstract DisplayContext getContext(DisplayContext.Type type);

    public abstract DialectHandling getDialectHandling();

    public abstract ULocale getLocale();

    public abstract List<UiListItem> getUiListCompareWholeItems(Set<ULocale> set, Comparator<UiListItem> comparator);

    public abstract String keyDisplayName(String str);

    public abstract String keyValueDisplayName(String str, String str2);

    public abstract String languageDisplayName(String str);

    public abstract String localeDisplayName(ULocale uLocale);

    public abstract String localeDisplayName(String str);

    public abstract String localeDisplayName(Locale locale);

    public abstract String regionDisplayName(String str);

    public abstract String scriptDisplayName(int i);

    public abstract String scriptDisplayName(String str);

    public abstract String variantDisplayName(String str);

    public static LocaleDisplayNames getInstance(ULocale uLocale) {
        return getInstance(uLocale, DialectHandling.STANDARD_NAMES);
    }

    public static LocaleDisplayNames getInstance(Locale locale) {
        return getInstance(ULocale.forLocale(locale));
    }

    public static LocaleDisplayNames getInstance(ULocale uLocale, DialectHandling dialectHandling) {
        LocaleDisplayNames localeDisplayNames;
        Method method = FACTORY_DIALECTHANDLING;
        if (method != null) {
            try {
                localeDisplayNames = (LocaleDisplayNames) method.invoke(null, uLocale, dialectHandling);
            } catch (IllegalAccessException | InvocationTargetException unused) {
            }
        } else {
            localeDisplayNames = null;
        }
        return localeDisplayNames == null ? new LastResortLocaleDisplayNames(uLocale, dialectHandling) : localeDisplayNames;
    }

    public static LocaleDisplayNames getInstance(ULocale uLocale, DisplayContext... displayContextArr) {
        LocaleDisplayNames localeDisplayNames;
        Method method = FACTORY_DISPLAYCONTEXT;
        if (method != null) {
            try {
                localeDisplayNames = (LocaleDisplayNames) method.invoke(null, uLocale, displayContextArr);
            } catch (IllegalAccessException | InvocationTargetException unused) {
            }
        } else {
            localeDisplayNames = null;
        }
        return localeDisplayNames == null ? new LastResortLocaleDisplayNames(uLocale, displayContextArr) : localeDisplayNames;
    }

    public static LocaleDisplayNames getInstance(Locale locale, DisplayContext... displayContextArr) {
        return getInstance(ULocale.forLocale(locale), displayContextArr);
    }

    @Deprecated
    public String scriptDisplayNameInContext(String str) {
        return scriptDisplayName(str);
    }

    public List<UiListItem> getUiList(Set<ULocale> set, boolean z, Comparator<Object> comparator) {
        return getUiListCompareWholeItems(set, UiListItem.getComparator(comparator, z));
    }

    public static class UiListItem {
        public final ULocale minimized;
        public final ULocale modified;
        public final String nameInDisplayLocale;
        public final String nameInSelf;

        public UiListItem(ULocale uLocale, ULocale uLocale2, String str, String str2) {
            this.minimized = uLocale;
            this.modified = uLocale2;
            this.nameInDisplayLocale = str;
            this.nameInSelf = str2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof UiListItem)) {
                return false;
            }
            UiListItem uiListItem = (UiListItem) obj;
            return this.nameInDisplayLocale.equals(uiListItem.nameInDisplayLocale) && this.nameInSelf.equals(uiListItem.nameInSelf) && this.minimized.equals(uiListItem.minimized) && this.modified.equals(uiListItem.modified);
        }

        public int hashCode() {
            return this.modified.hashCode() ^ this.nameInDisplayLocale.hashCode();
        }

        public String toString() {
            return "{" + this.minimized + ", " + this.modified + ", " + this.nameInDisplayLocale + ", " + this.nameInSelf + "}";
        }

        public static Comparator<UiListItem> getComparator(Comparator<Object> comparator, boolean z) {
            return new UiListItemComparator(comparator, z);
        }

        private static class UiListItemComparator implements Comparator<UiListItem> {
            private final Comparator<Object> collator;
            private final boolean useSelf;

            UiListItemComparator(Comparator<Object> comparator, boolean z) {
                this.collator = comparator;
                this.useSelf = z;
            }

            @Override // java.util.Comparator
            public int compare(UiListItem uiListItem, UiListItem uiListItem2) {
                int iCompare = this.useSelf ? this.collator.compare(uiListItem.nameInSelf, uiListItem2.nameInSelf) : this.collator.compare(uiListItem.nameInDisplayLocale, uiListItem2.nameInDisplayLocale);
                return iCompare != 0 ? iCompare : uiListItem.modified.compareTo(uiListItem2.modified);
            }
        }
    }

    @Deprecated
    protected LocaleDisplayNames() {
    }

    static {
        Method method;
        Method method2 = null;
        try {
            Class<?> cls = Class.forName(ICUConfig.get("com.ibm.icu.text.LocaleDisplayNames.impl", "com.ibm.icu.impl.LocaleDisplayNamesImpl"));
            try {
                method = cls.getMethod("getInstance", ULocale.class, DialectHandling.class);
            } catch (NoSuchMethodException unused) {
                method = null;
            }
            try {
                method2 = cls.getMethod("getInstance", ULocale.class, DisplayContext[].class);
            } catch (ClassNotFoundException | NoSuchMethodException unused2) {
            }
        } catch (ClassNotFoundException unused3) {
            method = null;
        }
        FACTORY_DIALECTHANDLING = method;
        FACTORY_DISPLAYCONTEXT = method2;
    }

    private static class LastResortLocaleDisplayNames extends LocaleDisplayNames {
        private DisplayContext[] contexts;
        private ULocale locale;

        @Override // com.ibm.icu.text.LocaleDisplayNames
        public String keyDisplayName(String str) {
            return str;
        }

        @Override // com.ibm.icu.text.LocaleDisplayNames
        public String keyValueDisplayName(String str, String str2) {
            return str2;
        }

        @Override // com.ibm.icu.text.LocaleDisplayNames
        public String languageDisplayName(String str) {
            return str;
        }

        @Override // com.ibm.icu.text.LocaleDisplayNames
        public String regionDisplayName(String str) {
            return str;
        }

        @Override // com.ibm.icu.text.LocaleDisplayNames
        public String scriptDisplayName(String str) {
            return str;
        }

        @Override // com.ibm.icu.text.LocaleDisplayNames
        public String variantDisplayName(String str) {
            return str;
        }

        private LastResortLocaleDisplayNames(ULocale uLocale, DialectHandling dialectHandling) {
            this.locale = uLocale;
            this.contexts = new DisplayContext[]{dialectHandling == DialectHandling.DIALECT_NAMES ? DisplayContext.DIALECT_NAMES : DisplayContext.STANDARD_NAMES};
        }

        private LastResortLocaleDisplayNames(ULocale uLocale, DisplayContext... displayContextArr) {
            this.locale = uLocale;
            DisplayContext[] displayContextArr2 = new DisplayContext[displayContextArr.length];
            this.contexts = displayContextArr2;
            System.arraycopy(displayContextArr, 0, displayContextArr2, 0, displayContextArr.length);
        }

        @Override // com.ibm.icu.text.LocaleDisplayNames
        public ULocale getLocale() {
            return this.locale;
        }

        @Override // com.ibm.icu.text.LocaleDisplayNames
        public DialectHandling getDialectHandling() {
            DialectHandling dialectHandling = DialectHandling.STANDARD_NAMES;
            for (DisplayContext displayContext : this.contexts) {
                if (displayContext.type() == DisplayContext.Type.DIALECT_HANDLING && displayContext.value() == DisplayContext.DIALECT_NAMES.ordinal()) {
                    return DialectHandling.DIALECT_NAMES;
                }
            }
            return dialectHandling;
        }

        @Override // com.ibm.icu.text.LocaleDisplayNames
        public DisplayContext getContext(DisplayContext.Type type) {
            DisplayContext displayContext = DisplayContext.STANDARD_NAMES;
            for (DisplayContext displayContext2 : this.contexts) {
                if (displayContext2.type() == type) {
                    return displayContext2;
                }
            }
            return displayContext;
        }

        @Override // com.ibm.icu.text.LocaleDisplayNames
        public String localeDisplayName(ULocale uLocale) {
            return uLocale.getName();
        }

        @Override // com.ibm.icu.text.LocaleDisplayNames
        public String localeDisplayName(Locale locale) {
            return ULocale.forLocale(locale).getName();
        }

        @Override // com.ibm.icu.text.LocaleDisplayNames
        public String localeDisplayName(String str) {
            return new ULocale(str).getName();
        }

        @Override // com.ibm.icu.text.LocaleDisplayNames
        public String scriptDisplayName(int i) {
            return UScript.getShortName(i);
        }

        @Override // com.ibm.icu.text.LocaleDisplayNames
        public List<UiListItem> getUiListCompareWholeItems(Set<ULocale> set, Comparator<UiListItem> comparator) {
            return Collections.emptyList();
        }
    }
}
