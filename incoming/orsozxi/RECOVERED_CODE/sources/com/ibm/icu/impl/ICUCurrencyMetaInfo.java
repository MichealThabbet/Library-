package com.ibm.icu.impl;

import com.ibm.icu.text.CurrencyMetaInfo;
import com.ibm.icu.util.Currency;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import kotlin.jvm.internal.LongCompanionObject;

/* loaded from: classes2.dex */
public class ICUCurrencyMetaInfo extends CurrencyMetaInfo {
    private static final int Currency = 2;
    private static final int Date = 4;
    private static final int Everything = Integer.MAX_VALUE;
    private static final long MASK = 4294967295L;
    private static final int Region = 1;
    private static final int Tender = 8;
    private ICUResourceBundle digitInfo;
    private ICUResourceBundle regionInfo;

    private interface Collector<T> {
        void collect(String str, String str2, long j, long j2, int i, boolean z);

        int collects();

        List<T> getList();
    }

    public ICUCurrencyMetaInfo() {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) ICUResourceBundle.getBundleInstance(ICUData.ICU_CURR_BASE_NAME, "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        this.regionInfo = iCUResourceBundle.findTopLevel("CurrencyMap");
        this.digitInfo = iCUResourceBundle.findTopLevel("CurrencyMeta");
    }

    @Override // com.ibm.icu.text.CurrencyMetaInfo
    public List<CurrencyMetaInfo.CurrencyInfo> currencyInfo(CurrencyMetaInfo.CurrencyFilter currencyFilter) {
        return collect(new InfoCollector(), currencyFilter);
    }

    @Override // com.ibm.icu.text.CurrencyMetaInfo
    public List<String> currencies(CurrencyMetaInfo.CurrencyFilter currencyFilter) {
        return collect(new CurrencyCollector(), currencyFilter);
    }

    @Override // com.ibm.icu.text.CurrencyMetaInfo
    public List<String> regions(CurrencyMetaInfo.CurrencyFilter currencyFilter) {
        return collect(new RegionCollector(), currencyFilter);
    }

    @Override // com.ibm.icu.text.CurrencyMetaInfo
    public CurrencyMetaInfo.CurrencyDigits currencyDigits(String str) {
        return currencyDigits(str, Currency.CurrencyUsage.STANDARD);
    }

    @Override // com.ibm.icu.text.CurrencyMetaInfo
    public CurrencyMetaInfo.CurrencyDigits currencyDigits(String str, Currency.CurrencyUsage currencyUsage) {
        ICUResourceBundle iCUResourceBundleFindWithFallback = this.digitInfo.findWithFallback(str);
        if (iCUResourceBundleFindWithFallback == null) {
            iCUResourceBundleFindWithFallback = this.digitInfo.findWithFallback("DEFAULT");
        }
        int[] intVector = iCUResourceBundleFindWithFallback.getIntVector();
        if (currencyUsage == Currency.CurrencyUsage.CASH) {
            return new CurrencyMetaInfo.CurrencyDigits(intVector[2], intVector[3]);
        }
        if (currencyUsage == Currency.CurrencyUsage.STANDARD) {
            return new CurrencyMetaInfo.CurrencyDigits(intVector[0], intVector[1]);
        }
        return new CurrencyMetaInfo.CurrencyDigits(intVector[0], intVector[1]);
    }

    private <T> List<T> collect(Collector<T> collector, CurrencyMetaInfo.CurrencyFilter currencyFilter) {
        if (currencyFilter == null) {
            currencyFilter = CurrencyMetaInfo.CurrencyFilter.all();
        }
        int iCollects = collector.collects();
        if (currencyFilter.region != null) {
            iCollects |= 1;
        }
        if (currencyFilter.currency != null) {
            iCollects |= 2;
        }
        if (currencyFilter.from != Long.MIN_VALUE || currencyFilter.to != LongCompanionObject.MAX_VALUE) {
            iCollects |= 4;
        }
        if (currencyFilter.tenderOnly) {
            iCollects |= 8;
        }
        if (iCollects != 0) {
            if (currencyFilter.region != null) {
                ICUResourceBundle iCUResourceBundleFindWithFallback = this.regionInfo.findWithFallback(currencyFilter.region);
                if (iCUResourceBundleFindWithFallback != null) {
                    collectRegion(collector, currencyFilter, iCollects, iCUResourceBundleFindWithFallback);
                }
            } else {
                for (int i = 0; i < this.regionInfo.getSize(); i++) {
                    collectRegion(collector, currencyFilter, iCollects, this.regionInfo.at(i));
                }
            }
        }
        return collector.getList();
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0051 A[PHI: r4
      0x0051: PHI (r4v2 java.lang.String) = (r4v1 java.lang.String), (r4v17 java.lang.String), (r4v17 java.lang.String) binds: [B:13:0x0037, B:15:0x0045, B:17:0x004d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0080 A[PHI: r6 r8
      0x0080: PHI (r6v1 long) = (r6v0 long), (r6v3 long) binds: [B:20:0x005b, B:25:0x007d] A[DONT_GENERATE, DONT_INLINE]
      0x0080: PHI (r8v1 long) = (r8v0 long), (r8v3 long) binds: [B:20:0x005b, B:25:0x007d] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private <T> void collectRegion(com.ibm.icu.impl.ICUCurrencyMetaInfo.Collector<T> r19, com.ibm.icu.text.CurrencyMetaInfo.CurrencyFilter r20, int r21, com.ibm.icu.impl.ICUResourceBundle r22) {
        /*
            r18 = this;
            r0 = r18
            r1 = r20
            r2 = r21
            java.lang.String r12 = r22.getKey()
            r13 = 1
            if (r2 != r13) goto L1e
            java.lang.String r2 = r22.getKey()
            r3 = 0
            r4 = 0
            r6 = 0
            r8 = -1
            r9 = 0
            r1 = r19
            r1.collect(r2, r3, r4, r6, r8, r9)
            return
        L1e:
            r14 = 0
            r15 = 0
        L20:
            int r3 = r22.getSize()
            if (r15 >= r3) goto Lbb
            r11 = r22
            com.ibm.icu.impl.ICUResourceBundle r3 = r11.at(r15)
            int r4 = r3.getSize()
            if (r4 != 0) goto L34
            goto Lb5
        L34:
            r4 = 0
            r5 = r2 & 2
            if (r5 == 0) goto L51
            java.lang.String r4 = "id"
            com.ibm.icu.impl.ICUResourceBundle r4 = r3.at(r4)
            java.lang.String r4 = r4.getString()
            java.lang.String r5 = r1.currency
            if (r5 == 0) goto L51
            java.lang.String r5 = r1.currency
            boolean r5 = r5.equals(r4)
            if (r5 != 0) goto L51
            goto Lb5
        L51:
            r5 = r4
            r4 = r2 & 4
            r6 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r8 = -9223372036854775808
            if (r4 == 0) goto L80
            java.lang.String r4 = "from"
            com.ibm.icu.impl.ICUResourceBundle r4 = r3.at(r4)
            long r8 = r0.getDate(r4, r8, r14)
            java.lang.String r4 = "to"
            com.ibm.icu.impl.ICUResourceBundle r4 = r3.at(r4)
            long r6 = r0.getDate(r4, r6, r13)
            long r13 = r1.from
            int r4 = (r13 > r6 ? 1 : (r13 == r6 ? 0 : -1))
            if (r4 <= 0) goto L79
            goto Lb5
        L79:
            long r13 = r1.to
            int r4 = (r13 > r8 ? 1 : (r13 == r8 ? 0 : -1))
            if (r4 >= 0) goto L80
            goto Lb5
        L80:
            r16 = r6
            r6 = r8
            r8 = r16
            r4 = r2 & 8
            if (r4 == 0) goto Lac
            java.lang.String r4 = "tender"
            com.ibm.icu.impl.ICUResourceBundle r3 = r3.at(r4)
            if (r3 == 0) goto La2
            java.lang.String r3 = r3.getString()
            java.lang.String r4 = "true"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto La0
            goto La2
        La0:
            r3 = 0
            goto La3
        La2:
            r3 = 1
        La3:
            boolean r4 = r1.tenderOnly
            if (r4 == 0) goto Laa
            if (r3 != 0) goto Laa
            goto Lb5
        Laa:
            r13 = r3
            goto Lad
        Lac:
            r13 = 1
        Lad:
            r3 = r19
            r4 = r12
            r10 = r15
            r11 = r13
            r3.collect(r4, r5, r6, r8, r10, r11)
        Lb5:
            int r15 = r15 + 1
            r13 = 1
            r14 = 0
            goto L20
        Lbb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.ICUCurrencyMetaInfo.collectRegion(com.ibm.icu.impl.ICUCurrencyMetaInfo$Collector, com.ibm.icu.text.CurrencyMetaInfo$CurrencyFilter, int, com.ibm.icu.impl.ICUResourceBundle):void");
    }

    private long getDate(ICUResourceBundle iCUResourceBundle, long j, boolean z) {
        if (iCUResourceBundle == null) {
            return j;
        }
        int[] intVector = iCUResourceBundle.getIntVector();
        return (intVector[0] << 32) | (intVector[1] & 4294967295L);
    }

    private static class UniqueList<T> {
        private Set<T> seen = new HashSet();
        private List<T> list = new ArrayList();

        private UniqueList() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static <T> UniqueList<T> create() {
            return new UniqueList<>();
        }

        void add(T t) {
            if (this.seen.contains(t)) {
                return;
            }
            this.list.add(t);
            this.seen.add(t);
        }

        List<T> list() {
            return Collections.unmodifiableList(this.list);
        }
    }

    private static class InfoCollector implements Collector<CurrencyMetaInfo.CurrencyInfo> {
        private List<CurrencyMetaInfo.CurrencyInfo> result;

        @Override // com.ibm.icu.impl.ICUCurrencyMetaInfo.Collector
        public int collects() {
            return Integer.MAX_VALUE;
        }

        private InfoCollector() {
            this.result = new ArrayList();
        }

        @Override // com.ibm.icu.impl.ICUCurrencyMetaInfo.Collector
        public void collect(String str, String str2, long j, long j2, int i, boolean z) {
            this.result.add(new CurrencyMetaInfo.CurrencyInfo(str, str2, j, j2, i, z));
        }

        @Override // com.ibm.icu.impl.ICUCurrencyMetaInfo.Collector
        public List<CurrencyMetaInfo.CurrencyInfo> getList() {
            return Collections.unmodifiableList(this.result);
        }
    }

    private static class RegionCollector implements Collector<String> {
        private final UniqueList<String> result;

        @Override // com.ibm.icu.impl.ICUCurrencyMetaInfo.Collector
        public int collects() {
            return 1;
        }

        private RegionCollector() {
            this.result = UniqueList.create();
        }

        @Override // com.ibm.icu.impl.ICUCurrencyMetaInfo.Collector
        public void collect(String str, String str2, long j, long j2, int i, boolean z) {
            this.result.add(str);
        }

        @Override // com.ibm.icu.impl.ICUCurrencyMetaInfo.Collector
        public List<String> getList() {
            return this.result.list();
        }
    }

    private static class CurrencyCollector implements Collector<String> {
        private final UniqueList<String> result;

        @Override // com.ibm.icu.impl.ICUCurrencyMetaInfo.Collector
        public int collects() {
            return 2;
        }

        private CurrencyCollector() {
            this.result = UniqueList.create();
        }

        @Override // com.ibm.icu.impl.ICUCurrencyMetaInfo.Collector
        public void collect(String str, String str2, long j, long j2, int i, boolean z) {
            this.result.add(str2);
        }

        @Override // com.ibm.icu.impl.ICUCurrencyMetaInfo.Collector
        public List<String> getList() {
            return this.result.list();
        }
    }
}
