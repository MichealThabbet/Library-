package com.ibm.icu.impl;

import com.ibm.icu.impl.ICURWLock;
import com.ibm.icu.util.ULocale;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/* loaded from: classes2.dex */
public class ICUService extends ICUNotifier {
    private static final boolean DEBUG = ICUDebug.enabled("service");
    private Map<String, CacheEntry> cache;
    private int defaultSize;
    private LocaleRef dnref;
    private final List<Factory> factories;
    private final ICURWLock factoryLock;
    private Map<String, Factory> idcache;
    protected final String name;

    public interface Factory {
        Object create(Key key, ICUService iCUService);

        String getDisplayName(String str, ULocale uLocale);

        void updateVisibleIDs(Map<String, Factory> map);
    }

    public interface ServiceListener extends EventListener {
        void serviceChanged(ICUService iCUService);
    }

    protected Object handleDefault(Key key, String[] strArr) {
        return null;
    }

    public ICUService() {
        this.factoryLock = new ICURWLock();
        this.factories = new ArrayList();
        this.defaultSize = 0;
        this.name = "";
    }

    public ICUService(String str) {
        this.factoryLock = new ICURWLock();
        this.factories = new ArrayList();
        this.defaultSize = 0;
        this.name = str;
    }

    public static class Key {
        private final String id;

        public boolean fallback() {
            return false;
        }

        public Key(String str) {
            this.id = str;
        }

        public final String id() {
            return this.id;
        }

        public String canonicalID() {
            return this.id;
        }

        public String currentID() {
            return canonicalID();
        }

        public String currentDescriptor() {
            return "/" + currentID();
        }

        public boolean isFallbackOf(String str) {
            return canonicalID().equals(str);
        }
    }

    public static class SimpleFactory implements Factory {
        protected String id;
        protected Object instance;
        protected boolean visible;

        public SimpleFactory(Object obj, String str) {
            this(obj, str, true);
        }

        public SimpleFactory(Object obj, String str, boolean z) {
            if (obj == null || str == null) {
                throw new IllegalArgumentException("Instance or id is null");
            }
            this.instance = obj;
            this.id = str;
            this.visible = z;
        }

        @Override // com.ibm.icu.impl.ICUService.Factory
        public Object create(Key key, ICUService iCUService) {
            if (this.id.equals(key.currentID())) {
                return this.instance;
            }
            return null;
        }

        @Override // com.ibm.icu.impl.ICUService.Factory
        public void updateVisibleIDs(Map<String, Factory> map) {
            if (this.visible) {
                map.put(this.id, this);
            } else {
                map.remove(this.id);
            }
        }

        @Override // com.ibm.icu.impl.ICUService.Factory
        public String getDisplayName(String str, ULocale uLocale) {
            if (this.visible && this.id.equals(str)) {
                return str;
            }
            return null;
        }

        public String toString() {
            return super.toString() + ", id: " + this.id + ", visible: " + this.visible;
        }
    }

    public Object get(String str) {
        return getKey(createKey(str), null);
    }

    public Object get(String str, String[] strArr) {
        if (str == null) {
            throw new NullPointerException("descriptor must not be null");
        }
        return getKey(createKey(str), strArr);
    }

    public Object getKey(Key key) {
        return getKey(key, null);
    }

    public Object getKey(Key key, String[] strArr) {
        return getKey(key, strArr, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:57:0x01a8, code lost:
    
        if (r4 != null) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x01aa, code lost:
    
        r4 = new java.util.ArrayList(5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x01b0, code lost:
    
        r4.add(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x01b7, code lost:
    
        if (r17.fallback() != false) goto L89;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object getKey(com.ibm.icu.impl.ICUService.Key r17, java.lang.String[] r18, com.ibm.icu.impl.ICUService.Factory r19) {
        /*
            Method dump skipped, instructions count: 663
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.ICUService.getKey(com.ibm.icu.impl.ICUService$Key, java.lang.String[], com.ibm.icu.impl.ICUService$Factory):java.lang.Object");
    }

    private static final class CacheEntry {
        final String actualDescriptor;
        final Object service;

        CacheEntry(String str, Object obj) {
            this.actualDescriptor = str;
            this.service = obj;
        }
    }

    public Set<String> getVisibleIDs() {
        return getVisibleIDs(null);
    }

    public Set<String> getVisibleIDs(String str) {
        Set<String> setKeySet = getVisibleIDMap().keySet();
        Key keyCreateKey = createKey(str);
        if (keyCreateKey == null) {
            return setKeySet;
        }
        HashSet hashSet = new HashSet(setKeySet.size());
        for (String str2 : setKeySet) {
            if (keyCreateKey.isFallbackOf(str2)) {
                hashSet.add(str2);
            }
        }
        return hashSet;
    }

    private Map<String, Factory> getVisibleIDMap() {
        synchronized (this) {
            if (this.idcache == null) {
                try {
                    this.factoryLock.acquireRead();
                    HashMap map = new HashMap();
                    ListIterator<Factory> listIterator = this.factories.listIterator(this.factories.size());
                    while (listIterator.hasPrevious()) {
                        listIterator.previous().updateVisibleIDs(map);
                    }
                    this.idcache = Collections.unmodifiableMap(map);
                    this.factoryLock.releaseRead();
                } catch (Throwable th) {
                    this.factoryLock.releaseRead();
                    throw th;
                }
            }
        }
        return this.idcache;
    }

    public String getDisplayName(String str) {
        return getDisplayName(str, ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public String getDisplayName(String str, ULocale uLocale) {
        Map<String, Factory> visibleIDMap = getVisibleIDMap();
        Factory factory = visibleIDMap.get(str);
        if (factory != null) {
            return factory.getDisplayName(str, uLocale);
        }
        Key keyCreateKey = createKey(str);
        while (keyCreateKey.fallback()) {
            Factory factory2 = visibleIDMap.get(keyCreateKey.currentID());
            if (factory2 != null) {
                return factory2.getDisplayName(str, uLocale);
            }
        }
        return null;
    }

    public SortedMap<String, String> getDisplayNames() {
        return getDisplayNames(ULocale.getDefault(ULocale.Category.DISPLAY), null, null);
    }

    public SortedMap<String, String> getDisplayNames(ULocale uLocale) {
        return getDisplayNames(uLocale, null, null);
    }

    public SortedMap<String, String> getDisplayNames(ULocale uLocale, Comparator<Object> comparator) {
        return getDisplayNames(uLocale, comparator, null);
    }

    public SortedMap<String, String> getDisplayNames(ULocale uLocale, String str) {
        return getDisplayNames(uLocale, null, str);
    }

    public SortedMap<String, String> getDisplayNames(ULocale uLocale, Comparator<Object> comparator, String str) {
        LocaleRef localeRef = this.dnref;
        SortedMap<String, String> sortedMapUnmodifiableSortedMap = localeRef != null ? localeRef.get(uLocale, comparator) : null;
        while (sortedMapUnmodifiableSortedMap == null) {
            synchronized (this) {
                if (localeRef == this.dnref || this.dnref == null) {
                    TreeMap treeMap = new TreeMap(comparator);
                    for (Map.Entry<String, Factory> entry : getVisibleIDMap().entrySet()) {
                        String key = entry.getKey();
                        treeMap.put(entry.getValue().getDisplayName(key, uLocale), key);
                    }
                    sortedMapUnmodifiableSortedMap = Collections.unmodifiableSortedMap(treeMap);
                    this.dnref = new LocaleRef(sortedMapUnmodifiableSortedMap, uLocale, comparator);
                } else {
                    localeRef = this.dnref;
                    sortedMapUnmodifiableSortedMap = localeRef.get(uLocale, comparator);
                }
            }
        }
        Key keyCreateKey = createKey(str);
        if (keyCreateKey == null) {
            return sortedMapUnmodifiableSortedMap;
        }
        TreeMap treeMap2 = new TreeMap((SortedMap) sortedMapUnmodifiableSortedMap);
        Iterator it2 = treeMap2.entrySet().iterator();
        while (it2.hasNext()) {
            if (!keyCreateKey.isFallbackOf((String) ((Map.Entry) it2.next()).getValue())) {
                it2.remove();
            }
        }
        return treeMap2;
    }

    private static class LocaleRef {

        /* renamed from: com, reason: collision with root package name */
        private Comparator<Object> f14com;
        private SortedMap<String, String> dnCache;
        private final ULocale locale;

        LocaleRef(SortedMap<String, String> sortedMap, ULocale uLocale, Comparator<Object> comparator) {
            this.locale = uLocale;
            this.f14com = comparator;
            this.dnCache = sortedMap;
        }

        SortedMap<String, String> get(ULocale uLocale, Comparator<Object> comparator) {
            SortedMap<String, String> sortedMap = this.dnCache;
            if (sortedMap == null || !this.locale.equals(uLocale)) {
                return null;
            }
            Comparator<Object> comparator2 = this.f14com;
            if (comparator2 == comparator || (comparator2 != null && comparator2.equals(comparator))) {
                return sortedMap;
            }
            return null;
        }
    }

    public final List<Factory> factories() {
        try {
            this.factoryLock.acquireRead();
            return new ArrayList(this.factories);
        } finally {
            this.factoryLock.releaseRead();
        }
    }

    public Factory registerObject(Object obj, String str) {
        return registerObject(obj, str, true);
    }

    public Factory registerObject(Object obj, String str, boolean z) {
        return registerFactory(new SimpleFactory(obj, createKey(str).canonicalID(), z));
    }

    public final Factory registerFactory(Factory factory) {
        if (factory == null) {
            throw null;
        }
        try {
            this.factoryLock.acquireWrite();
            this.factories.add(0, factory);
            clearCaches();
            this.factoryLock.releaseWrite();
            notifyChanged();
            return factory;
        } catch (Throwable th) {
            this.factoryLock.releaseWrite();
            throw th;
        }
    }

    public final boolean unregisterFactory(Factory factory) {
        if (factory == null) {
            throw null;
        }
        boolean z = false;
        try {
            this.factoryLock.acquireWrite();
            if (this.factories.remove(factory)) {
                z = true;
                clearCaches();
            }
            if (z) {
                notifyChanged();
            }
            return z;
        } finally {
            this.factoryLock.releaseWrite();
        }
    }

    public final void reset() {
        try {
            this.factoryLock.acquireWrite();
            reInitializeFactories();
            clearCaches();
            this.factoryLock.releaseWrite();
            notifyChanged();
        } catch (Throwable th) {
            this.factoryLock.releaseWrite();
            throw th;
        }
    }

    protected void reInitializeFactories() {
        this.factories.clear();
    }

    public boolean isDefault() {
        return this.factories.size() == this.defaultSize;
    }

    protected void markDefault() {
        this.defaultSize = this.factories.size();
    }

    public Key createKey(String str) {
        if (str == null) {
            return null;
        }
        return new Key(str);
    }

    protected void clearCaches() {
        this.cache = null;
        this.idcache = null;
        this.dnref = null;
    }

    protected void clearServiceCache() {
        this.cache = null;
    }

    @Override // com.ibm.icu.impl.ICUNotifier
    protected boolean acceptsListener(EventListener eventListener) {
        return eventListener instanceof ServiceListener;
    }

    @Override // com.ibm.icu.impl.ICUNotifier
    protected void notifyListener(EventListener eventListener) {
        ((ServiceListener) eventListener).serviceChanged(this);
    }

    public String stats() {
        ICURWLock.Stats statsResetStats = this.factoryLock.resetStats();
        return statsResetStats != null ? statsResetStats.toString() : "no stats";
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return super.toString() + "{" + this.name + "}";
    }
}
