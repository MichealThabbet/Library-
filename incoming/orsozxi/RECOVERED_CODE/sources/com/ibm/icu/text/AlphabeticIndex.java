package com.ibm.icu.text;

import com.huawei.hms.framework.common.ContainerUtils;
import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.util.LocaleData;
import com.ibm.icu.util.ULocale;
import it.orsozoxi.android.orsonotes.R2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* loaded from: classes2.dex */
public final class AlphabeticIndex<V> implements Iterable<Bucket<V>> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String BASE = "\ufdd0";
    private static final char CGJ = 847;
    private static final int GC_CN_MASK = 1;
    private static final int GC_LL_MASK = 4;
    private static final int GC_LM_MASK = 16;
    private static final int GC_LO_MASK = 32;
    private static final int GC_LT_MASK = 8;
    private static final int GC_LU_MASK = 2;
    private static final int GC_L_MASK = 62;
    private static final Comparator<String> binaryCmp = new UTF16.StringComparator(true, false, 0);
    private BucketList<V> buckets;
    private RuleBasedCollator collatorExternal;
    private final RuleBasedCollator collatorOriginal;
    private final RuleBasedCollator collatorPrimaryOnly;
    private final List<String> firstCharsInScripts;
    private String inflowLabel;
    private final UnicodeSet initialLabels;
    private List<Record<V>> inputList;
    private int maxLabelCount;
    private String overflowLabel;
    private final Comparator<Record<V>> recordComparator;
    private String underflowLabel;

    public static final class ImmutableIndex<V> implements Iterable<Bucket<V>> {
        private final BucketList<V> buckets;
        private final Collator collatorPrimaryOnly;

        private ImmutableIndex(BucketList<V> bucketList, Collator collator) {
            this.buckets = bucketList;
            this.collatorPrimaryOnly = collator;
        }

        public int getBucketCount() {
            return this.buckets.getBucketCount();
        }

        public int getBucketIndex(CharSequence charSequence) {
            return this.buckets.getBucketIndex(charSequence, this.collatorPrimaryOnly);
        }

        public Bucket<V> getBucket(int i) {
            if (i < 0 || i >= this.buckets.getBucketCount()) {
                return null;
            }
            return (Bucket) ((BucketList) this.buckets).immutableVisibleList.get(i);
        }

        @Override // java.lang.Iterable
        public Iterator<Bucket<V>> iterator() {
            return this.buckets.iterator();
        }
    }

    public AlphabeticIndex(ULocale uLocale) {
        this(uLocale, null);
    }

    public AlphabeticIndex(Locale locale) {
        this(ULocale.forLocale(locale), null);
    }

    public AlphabeticIndex(RuleBasedCollator ruleBasedCollator) {
        this(null, ruleBasedCollator);
    }

    private AlphabeticIndex(ULocale uLocale, RuleBasedCollator ruleBasedCollator) {
        this.recordComparator = new Comparator<Record<V>>() { // from class: com.ibm.icu.text.AlphabeticIndex.1
            @Override // java.util.Comparator
            public int compare(Record<V> record, Record<V> record2) {
                return AlphabeticIndex.this.collatorOriginal.compare(((Record) record).name, ((Record) record2).name);
            }
        };
        this.initialLabels = new UnicodeSet();
        this.overflowLabel = "…";
        this.underflowLabel = "…";
        this.inflowLabel = "…";
        this.maxLabelCount = 99;
        ruleBasedCollator = ruleBasedCollator == null ? (RuleBasedCollator) Collator.getInstance(uLocale) : ruleBasedCollator;
        this.collatorOriginal = ruleBasedCollator;
        try {
            RuleBasedCollator ruleBasedCollatorCloneAsThawed = ruleBasedCollator.cloneAsThawed();
            this.collatorPrimaryOnly = ruleBasedCollatorCloneAsThawed;
            ruleBasedCollatorCloneAsThawed.setStrength(0);
            this.collatorPrimaryOnly.freeze();
            List<String> firstCharactersInScripts = getFirstCharactersInScripts();
            this.firstCharsInScripts = firstCharactersInScripts;
            Collections.sort(firstCharactersInScripts, this.collatorPrimaryOnly);
            while (!this.firstCharsInScripts.isEmpty()) {
                if (this.collatorPrimaryOnly.compare(this.firstCharsInScripts.get(0), "") == 0) {
                    this.firstCharsInScripts.remove(0);
                } else {
                    if (addChineseIndexCharacters() || uLocale == null) {
                        return;
                    }
                    addIndexExemplars(uLocale);
                    return;
                }
            }
            throw new IllegalArgumentException("AlphabeticIndex requires some non-ignorable script boundary strings");
        } catch (Exception e) {
            throw new IllegalStateException("Collator cannot be cloned", e);
        }
    }

    public AlphabeticIndex<V> addLabels(UnicodeSet unicodeSet) {
        this.initialLabels.addAll(unicodeSet);
        this.buckets = null;
        return this;
    }

    public AlphabeticIndex<V> addLabels(ULocale... uLocaleArr) {
        for (ULocale uLocale : uLocaleArr) {
            addIndexExemplars(uLocale);
        }
        this.buckets = null;
        return this;
    }

    public AlphabeticIndex<V> addLabels(Locale... localeArr) {
        for (Locale locale : localeArr) {
            addIndexExemplars(ULocale.forLocale(locale));
        }
        this.buckets = null;
        return this;
    }

    public AlphabeticIndex<V> setOverflowLabel(String str) {
        this.overflowLabel = str;
        this.buckets = null;
        return this;
    }

    public String getUnderflowLabel() {
        return this.underflowLabel;
    }

    public AlphabeticIndex<V> setUnderflowLabel(String str) {
        this.underflowLabel = str;
        this.buckets = null;
        return this;
    }

    public String getOverflowLabel() {
        return this.overflowLabel;
    }

    public AlphabeticIndex<V> setInflowLabel(String str) {
        this.inflowLabel = str;
        this.buckets = null;
        return this;
    }

    public String getInflowLabel() {
        return this.inflowLabel;
    }

    public int getMaxLabelCount() {
        return this.maxLabelCount;
    }

    public AlphabeticIndex<V> setMaxLabelCount(int i) {
        this.maxLabelCount = i;
        this.buckets = null;
        return this;
    }

    private List<String> initLabels() {
        boolean z;
        Normalizer2 nFKDInstance = Normalizer2.getNFKDInstance();
        ArrayList arrayList = new ArrayList();
        int i = 0;
        String str = this.firstCharsInScripts.get(0);
        List<String> list = this.firstCharsInScripts;
        String str2 = list.get(list.size() - 1);
        Iterator<String> it2 = this.initialLabels.iterator();
        while (it2.hasNext()) {
            String next = it2.next();
            if (!UTF16.hasMoreCodePointsThan(next, 1)) {
                z = false;
            } else if (next.charAt(next.length() - 1) != '*' || next.charAt(next.length() - 2) == '*') {
                z = true;
            } else {
                next = next.substring(0, next.length() - 1);
                z = false;
            }
            if (this.collatorPrimaryOnly.compare(next, str) >= 0 && this.collatorPrimaryOnly.compare(next, str2) < 0 && (!z || this.collatorPrimaryOnly.compare(next, separated(next)) != 0)) {
                int iBinarySearch = Collections.binarySearch(arrayList, next, this.collatorPrimaryOnly);
                if (iBinarySearch < 0) {
                    arrayList.add(~iBinarySearch, next);
                } else if (isOneLabelBetterThanOther(nFKDInstance, next, (String) arrayList.get(iBinarySearch))) {
                    arrayList.set(iBinarySearch, next);
                }
            }
        }
        int size = arrayList.size() - 1;
        if (size > this.maxLabelCount) {
            int i2 = -1;
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                i++;
                it3.next();
                int i3 = (this.maxLabelCount * i) / size;
                if (i3 == i2) {
                    it3.remove();
                } else {
                    i2 = i3;
                }
            }
        }
        return arrayList;
    }

    private static String fixLabel(String str) {
        if (!str.startsWith(BASE)) {
            return str;
        }
        char cCharAt = str.charAt(1);
        if (10240 < cCharAt && cCharAt <= 10495) {
            return (cCharAt - 10240) + "劃";
        }
        return str.substring(1);
    }

    private void addIndexExemplars(ULocale uLocale) {
        UnicodeSet exemplarSet = LocaleData.getExemplarSet(uLocale, 0, 2);
        if (exemplarSet != null && !exemplarSet.isEmpty()) {
            this.initialLabels.addAll(exemplarSet);
            return;
        }
        UnicodeSet unicodeSetCloneAsThawed = LocaleData.getExemplarSet(uLocale, 0, 0).cloneAsThawed();
        if (unicodeSetCloneAsThawed.containsSome(97, 122) || unicodeSetCloneAsThawed.isEmpty()) {
            unicodeSetCloneAsThawed.addAll(97, 122);
        }
        if (unicodeSetCloneAsThawed.containsSome(Normalizer2Impl.Hangul.HANGUL_BASE, Normalizer2Impl.Hangul.HANGUL_END)) {
            unicodeSetCloneAsThawed.remove(Normalizer2Impl.Hangul.HANGUL_BASE, Normalizer2Impl.Hangul.HANGUL_END).add(Normalizer2Impl.Hangul.HANGUL_BASE).add(45208).add(45796).add(46972).add(47560).add(48148).add(49324).add(50500).add(51088).add(52264).add(52852).add(53440).add(54028).add(54616);
        }
        if (unicodeSetCloneAsThawed.containsSome(R2.style.Theme_MaterialComponents_DayNight_BottomSheetDialog, R2.styleable.AppCompatTheme_actionMenuTextAppearance)) {
            UnicodeSet unicodeSet = new UnicodeSet("[ሀለሐመሠረሰሸቀቈቐቘበቨተቸኀኈነኘአከኰኸዀወዐዘዠየደዸጀገጐጘጠጨጰጸፀፈፐፘ]");
            unicodeSet.retainAll(unicodeSetCloneAsThawed);
            unicodeSetCloneAsThawed.remove(R2.style.Theme_MaterialComponents_DayNight_BottomSheetDialog, R2.styleable.AppCompatTheme_actionMenuTextAppearance).addAll(unicodeSet);
        }
        Iterator<String> it2 = unicodeSetCloneAsThawed.iterator();
        while (it2.hasNext()) {
            this.initialLabels.add(UCharacter.toUpperCase(uLocale, it2.next()));
        }
    }

    private boolean addChineseIndexCharacters() {
        UnicodeSet unicodeSet = new UnicodeSet();
        try {
            this.collatorPrimaryOnly.internalAddContractions(BASE.charAt(0), unicodeSet);
            if (unicodeSet.isEmpty()) {
                return false;
            }
            this.initialLabels.addAll(unicodeSet);
            Iterator<String> it2 = unicodeSet.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                String next = it2.next();
                char cCharAt = next.charAt(next.length() - 1);
                if ('A' <= cCharAt && cCharAt <= 'Z') {
                    this.initialLabels.add(65, 90);
                    break;
                }
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private String separated(String str) {
        StringBuilder sb = new StringBuilder();
        char cCharAt = str.charAt(0);
        sb.append(cCharAt);
        int i = 1;
        while (i < str.length()) {
            char cCharAt2 = str.charAt(i);
            if (!UCharacter.isHighSurrogate(cCharAt) || !UCharacter.isLowSurrogate(cCharAt2)) {
                sb.append(CGJ);
            }
            sb.append(cCharAt2);
            i++;
            cCharAt = cCharAt2;
        }
        return sb.toString();
    }

    public ImmutableIndex<V> buildImmutableIndex() {
        BucketList<V> bucketListCreateBucketList;
        List<Record<V>> list = this.inputList;
        if (list != null && !list.isEmpty()) {
            bucketListCreateBucketList = createBucketList();
        } else {
            if (this.buckets == null) {
                this.buckets = createBucketList();
            }
            bucketListCreateBucketList = this.buckets;
        }
        return new ImmutableIndex<>(bucketListCreateBucketList, this.collatorPrimaryOnly);
    }

    public List<String> getBucketLabels() {
        initBuckets();
        ArrayList arrayList = new ArrayList();
        Iterator<Bucket<V>> it2 = this.buckets.iterator();
        while (it2.hasNext()) {
            arrayList.add(it2.next().getLabel());
        }
        return arrayList;
    }

    public RuleBasedCollator getCollator() {
        if (this.collatorExternal == null) {
            try {
                this.collatorExternal = (RuleBasedCollator) this.collatorOriginal.clone();
            } catch (Exception e) {
                throw new IllegalStateException("Collator cannot be cloned", e);
            }
        }
        return this.collatorExternal;
    }

    public AlphabeticIndex<V> addRecord(CharSequence charSequence, V v) {
        this.buckets = null;
        if (this.inputList == null) {
            this.inputList = new ArrayList();
        }
        this.inputList.add(new Record<>(charSequence, v));
        return this;
    }

    public int getBucketIndex(CharSequence charSequence) {
        initBuckets();
        return this.buckets.getBucketIndex(charSequence, this.collatorPrimaryOnly);
    }

    public AlphabeticIndex<V> clearRecords() {
        List<Record<V>> list = this.inputList;
        if (list != null && !list.isEmpty()) {
            this.inputList.clear();
            this.buckets = null;
        }
        return this;
    }

    public int getBucketCount() {
        initBuckets();
        return this.buckets.getBucketCount();
    }

    public int getRecordCount() {
        List<Record<V>> list = this.inputList;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override // java.lang.Iterable
    public Iterator<Bucket<V>> iterator() {
        initBuckets();
        return this.buckets.iterator();
    }

    private void initBuckets() {
        Bucket bucket;
        String str;
        if (this.buckets != null) {
            return;
        }
        this.buckets = createBucketList();
        List<Record<V>> list = this.inputList;
        if (list == null || list.isEmpty()) {
            return;
        }
        Collections.sort(this.inputList, this.recordComparator);
        Iterator itFullIterator = this.buckets.fullIterator();
        Bucket bucket2 = (Bucket) itFullIterator.next();
        if (itFullIterator.hasNext()) {
            bucket = (Bucket) itFullIterator.next();
            str = bucket.lowerBoundary;
        } else {
            bucket = null;
            str = null;
        }
        for (Record<V> record : this.inputList) {
            while (str != null && this.collatorPrimaryOnly.compare(((Record) record).name, str) >= 0) {
                if (itFullIterator.hasNext()) {
                    Bucket bucket3 = (Bucket) itFullIterator.next();
                    str = bucket3.lowerBoundary;
                    Bucket bucket4 = bucket;
                    bucket = bucket3;
                    bucket2 = bucket4;
                } else {
                    bucket2 = bucket;
                    str = null;
                }
            }
            Bucket bucket5 = bucket2.displayBucket != null ? bucket2.displayBucket : bucket2;
            if (bucket5.records == null) {
                bucket5.records = new ArrayList();
            }
            bucket5.records.add(record);
        }
    }

    private static boolean isOneLabelBetterThanOther(Normalizer2 normalizer2, String str, String str2) {
        String strNormalize = normalizer2.normalize(str);
        String strNormalize2 = normalizer2.normalize(str2);
        int iCodePointCount = strNormalize.codePointCount(0, strNormalize.length()) - strNormalize2.codePointCount(0, strNormalize2.length());
        if (iCodePointCount != 0) {
            return iCodePointCount < 0;
        }
        int iCompare = binaryCmp.compare(strNormalize, strNormalize2);
        return iCompare != 0 ? iCompare < 0 : binaryCmp.compare(str, str2) < 0;
    }

    public static class Record<V> {
        private final V data;
        private final CharSequence name;

        private Record(CharSequence charSequence, V v) {
            this.name = charSequence;
            this.data = v;
        }

        public CharSequence getName() {
            return this.name;
        }

        public V getData() {
            return this.data;
        }

        public String toString() {
            return ((Object) this.name) + ContainerUtils.KEY_VALUE_DELIMITER + this.data;
        }
    }

    public static class Bucket<V> implements Iterable<Record<V>> {
        private Bucket<V> displayBucket;
        private int displayIndex;
        private final String label;
        private final LabelType labelType;
        private final String lowerBoundary;
        private List<Record<V>> records;

        public enum LabelType {
            NORMAL,
            UNDERFLOW,
            INFLOW,
            OVERFLOW
        }

        private Bucket(String str, String str2, LabelType labelType) {
            this.label = str;
            this.lowerBoundary = str2;
            this.labelType = labelType;
        }

        public String getLabel() {
            return this.label;
        }

        public LabelType getLabelType() {
            return this.labelType;
        }

        public int size() {
            List<Record<V>> list = this.records;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        @Override // java.lang.Iterable
        public Iterator<Record<V>> iterator() {
            List<Record<V>> list = this.records;
            if (list == null) {
                return Collections.emptyList().iterator();
            }
            return list.iterator();
        }

        public String toString() {
            return "{labelType=" + this.labelType + ", lowerBoundary=" + this.lowerBoundary + ", label=" + this.label + "}";
        }
    }

    private BucketList<V> createBucketList() {
        Iterator<String> it2;
        char cCharAt;
        String str;
        char cCharAt2;
        String str2;
        String str3;
        List<String> listInitLabels = initLabels();
        long variableTop = this.collatorPrimaryOnly.isAlternateHandlingShifted() ? this.collatorPrimaryOnly.getVariableTop() & 4294967295L : 0L;
        Bucket[] bucketArr = new Bucket[26];
        Bucket[] bucketArr2 = new Bucket[26];
        ArrayList arrayList = new ArrayList();
        String str4 = "";
        AnonymousClass1 anonymousClass1 = null;
        arrayList.add(new Bucket(getUnderflowLabel(), str4, Bucket.LabelType.UNDERFLOW));
        Iterator<String> it3 = listInitLabels.iterator();
        String str5 = "";
        boolean z = false;
        int i = -1;
        boolean z2 = false;
        while (true) {
            int i2 = 1;
            if (!it3.hasNext()) {
                break;
            }
            String next = it3.next();
            if (this.collatorPrimaryOnly.compare(next, str5) >= 0) {
                boolean z3 = false;
                while (true) {
                    i += i2;
                    str2 = this.firstCharsInScripts.get(i);
                    if (this.collatorPrimaryOnly.compare(next, str2) < 0) {
                        break;
                    }
                    i2 = 1;
                    z3 = true;
                }
                if (!z3 || arrayList.size() <= 1) {
                    it2 = it3;
                    str3 = str2;
                    anonymousClass1 = null;
                } else {
                    it2 = it3;
                    str3 = str2;
                    anonymousClass1 = null;
                    arrayList.add(new Bucket(getInflowLabel(), str5, Bucket.LabelType.INFLOW));
                }
                str5 = str3;
            } else {
                it2 = it3;
            }
            Bucket bucket = new Bucket(fixLabel(next), next, Bucket.LabelType.NORMAL);
            arrayList.add(bucket);
            if (next.length() == 1 && 'A' <= (cCharAt2 = next.charAt(0)) && cCharAt2 <= 'Z') {
                bucketArr[cCharAt2 - 'A'] = bucket;
            } else if (next.length() == 2 && next.startsWith(BASE) && 'A' <= (cCharAt = next.charAt(1)) && cCharAt <= 'Z') {
                bucketArr2[cCharAt - 'A'] = bucket;
                z = true;
            }
            if (next.startsWith(BASE) || !hasMultiplePrimaryWeights(this.collatorPrimaryOnly, variableTop, next) || next.endsWith("\uffff")) {
                str = str5;
            } else {
                int size = arrayList.size() - 2;
                while (true) {
                    Bucket bucket2 = (Bucket) arrayList.get(size);
                    str = str5;
                    if (bucket2.labelType == Bucket.LabelType.NORMAL) {
                        if (bucket2.displayBucket == null && !hasMultiplePrimaryWeights(this.collatorPrimaryOnly, variableTop, bucket2.lowerBoundary)) {
                            Bucket bucket3 = new Bucket(str4, next + "\uffff", Bucket.LabelType.NORMAL);
                            bucket3.displayBucket = bucket2;
                            arrayList.add(bucket3);
                            z2 = true;
                            break;
                        }
                        size--;
                        str5 = str;
                    }
                }
            }
            it3 = it2;
            str5 = str;
            anonymousClass1 = null;
        }
        if (arrayList.size() == 1) {
            return new BucketList<>(arrayList, arrayList);
        }
        arrayList.add(new Bucket(getOverflowLabel(), str5, Bucket.LabelType.OVERFLOW));
        if (z) {
            Bucket bucket4 = null;
            for (int i3 = 0; i3 < 26; i3++) {
                if (bucketArr[i3] != null) {
                    bucket4 = bucketArr[i3];
                }
                if (bucketArr2[i3] != null && bucket4 != null) {
                    bucketArr2[i3].displayBucket = bucket4;
                    z2 = true;
                }
            }
        }
        if (!z2) {
            return new BucketList<>(arrayList, arrayList);
        }
        int size2 = arrayList.size() - 1;
        Bucket bucket5 = (Bucket) arrayList.get(size2);
        while (true) {
            size2--;
            if (size2 <= 0) {
                break;
            }
            Bucket bucket6 = (Bucket) arrayList.get(size2);
            if (bucket6.displayBucket == null) {
                if (bucket6.labelType != Bucket.LabelType.INFLOW || bucket5.labelType == Bucket.LabelType.NORMAL) {
                    bucket5 = bucket6;
                } else {
                    bucket6.displayBucket = bucket5;
                }
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it4 = arrayList.iterator();
        while (it4.hasNext()) {
            Bucket bucket7 = (Bucket) it4.next();
            if (bucket7.displayBucket == null) {
                arrayList2.add(bucket7);
            }
        }
        return new BucketList<>(arrayList, arrayList2);
    }

    private static class BucketList<V> implements Iterable<Bucket<V>> {
        private final ArrayList<Bucket<V>> bucketList;
        private final List<Bucket<V>> immutableVisibleList;

        private BucketList(ArrayList<Bucket<V>> arrayList, ArrayList<Bucket<V>> arrayList2) {
            this.bucketList = arrayList;
            Iterator<Bucket<V>> it2 = arrayList2.iterator();
            int i = 0;
            while (it2.hasNext()) {
                ((Bucket) it2.next()).displayIndex = i;
                i++;
            }
            this.immutableVisibleList = Collections.unmodifiableList(arrayList2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getBucketCount() {
            return this.immutableVisibleList.size();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getBucketIndex(CharSequence charSequence, Collator collator) {
            int size = this.bucketList.size();
            int i = 0;
            while (i + 1 < size) {
                int i2 = (i + size) / 2;
                if (collator.compare(charSequence, ((Bucket) this.bucketList.get(i2)).lowerBoundary) < 0) {
                    size = i2;
                } else {
                    i = i2;
                }
            }
            Bucket<V> bucket = this.bucketList.get(i);
            if (((Bucket) bucket).displayBucket != null) {
                bucket = ((Bucket) bucket).displayBucket;
            }
            return ((Bucket) bucket).displayIndex;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Iterator<Bucket<V>> fullIterator() {
            return this.bucketList.iterator();
        }

        @Override // java.lang.Iterable
        public Iterator<Bucket<V>> iterator() {
            return this.immutableVisibleList.iterator();
        }
    }

    private static boolean hasMultiplePrimaryWeights(RuleBasedCollator ruleBasedCollator, long j, String str) throws Throwable {
        boolean z = false;
        for (long j2 : ruleBasedCollator.internalGetCEs(str)) {
            if ((j2 >>> 32) > j) {
                if (z) {
                    return true;
                }
                z = true;
            }
        }
        return false;
    }

    @Deprecated
    public List<String> getFirstCharactersInScripts() {
        ArrayList arrayList = new ArrayList(200);
        UnicodeSet unicodeSet = new UnicodeSet();
        this.collatorPrimaryOnly.internalAddContractions(64977, unicodeSet);
        if (unicodeSet.isEmpty()) {
            throw new UnsupportedOperationException("AlphabeticIndex requires script-first-primary contractions");
        }
        Iterator<String> it2 = unicodeSet.iterator();
        while (it2.hasNext()) {
            String next = it2.next();
            if (((1 << UCharacter.getType(next.codePointAt(1))) & 63) != 0) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }
}
