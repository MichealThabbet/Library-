package com.ibm.icu.impl.locale;

import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.util.BytesTrie;
import com.ibm.icu.util.LocaleMatcher;
import com.ibm.icu.util.ULocale;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.codec.language.Soundex;

/* loaded from: classes2.dex */
public class LocaleDistance {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ABOVE_THRESHOLD = 100;
    private static final boolean DEBUG_OUTPUT = false;
    private static final int DISTANCE_FRACTION_MASK = 7;
    private static final int DISTANCE_INT_SHIFT = 7;
    private static final int DISTANCE_IS_FINAL = 256;
    private static final int DISTANCE_IS_FINAL_OR_SKIP_SCRIPT = 384;
    private static final int DISTANCE_MASK = 1023;
    private static final int DISTANCE_SHIFT = 3;
    public static final int DISTANCE_SKIP_SCRIPT = 128;
    public static final int END_OF_SUBTAG = 128;
    private static final int INDEX_NEG_1 = -1024;
    private static final int INDEX_SHIFT = 10;
    public static final LocaleDistance INSTANCE = new LocaleDistance(Data.load());
    public static final int IX_DEF_LANG_DISTANCE = 0;
    public static final int IX_DEF_REGION_DISTANCE = 2;
    public static final int IX_DEF_SCRIPT_DISTANCE = 1;
    public static final int IX_LIMIT = 4;
    public static final int IX_MIN_REGION_DISTANCE = 3;
    private final int defaultDemotionPerDesiredLocale = getDistanceFloor(getBestIndexAndDistance(new LSR("en", "Latn", "US", 7), new LSR[]{new LSR("en", "Latn", "GB", 7)}, 1, shiftDistance(50), LocaleMatcher.FavorSubtag.LANGUAGE, LocaleMatcher.Direction.WITH_ONE_WAY));
    private final int defaultLanguageDistance;
    private final int defaultRegionDistance;
    private final int defaultScriptDistance;
    private final int minRegionDistance;
    private final Set<LSR> paradigmLSRs;
    private final String[] partitionArrays;
    private final byte[] regionToPartitionsIndex;
    private final BytesTrie trie;

    public static final int getDistanceFloor(int i) {
        return (i & 1023) >> 3;
    }

    public static final int getIndex(int i) {
        return i >> 10;
    }

    public static final int getShiftedDistance(int i) {
        return i & 1023;
    }

    public static final int shiftDistance(int i) {
        return i << 3;
    }

    public static final double getDistanceDouble(int i) {
        return getShiftedDistance(i) / 8.0d;
    }

    public static final class Data {
        public int[] distances;
        public Set<LSR> paradigmLSRs;
        public String[] partitionArrays;
        public byte[] regionToPartitionsIndex;
        public byte[] trie;

        public int hashCode() {
            return 1;
        }

        public Data(byte[] bArr, byte[] bArr2, String[] strArr, Set<LSR> set, int[] iArr) {
            this.trie = bArr;
            this.regionToPartitionsIndex = bArr2;
            this.partitionArrays = strArr;
            this.paradigmLSRs = set;
            this.distances = iArr;
        }

        private static UResource.Value getValue(UResource.Table table, String str, UResource.Value value) {
            if (table.findValue(str, value)) {
                return value;
            }
            throw new MissingResourceException("langInfo.res missing data", "", "match/" + str);
        }

        public static Data load() throws MissingResourceException {
            Set setEmptySet;
            UResource.Value valueWithFallback = ICUResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "langInfo", ICUResourceBundle.ICU_DATA_CLASS_LOADER, ICUResourceBundle.OpenType.DIRECT).getValueWithFallback("match");
            UResource.Table table = valueWithFallback.getTable();
            ByteBuffer binary = getValue(table, "trie", valueWithFallback).getBinary();
            byte[] bArr = new byte[binary.remaining()];
            binary.get(bArr);
            ByteBuffer binary2 = getValue(table, "regionToPartitions", valueWithFallback).getBinary();
            int iRemaining = binary2.remaining();
            byte[] bArr2 = new byte[iRemaining];
            binary2.get(bArr2);
            if (iRemaining < 1677) {
                throw new MissingResourceException("langInfo.res binary data too short", "", "match/regionToPartitions");
            }
            String[] stringArray = getValue(table, "partitions", valueWithFallback).getStringArray();
            if (table.findValue("paradigms", valueWithFallback)) {
                String[] stringArray2 = valueWithFallback.getStringArray();
                LinkedHashSet linkedHashSet = new LinkedHashSet(stringArray2.length / 3);
                for (int i = 0; i < stringArray2.length; i += 3) {
                    linkedHashSet.add(new LSR(stringArray2[i], stringArray2[i + 1], stringArray2[i + 2], 0));
                }
                setEmptySet = linkedHashSet;
            } else {
                setEmptySet = Collections.emptySet();
            }
            int[] intVector = getValue(table, "distances", valueWithFallback).getIntVector();
            if (intVector.length < 4) {
                throw new MissingResourceException("langInfo.res intvector too short", "", "match/distances");
            }
            return new Data(bArr, bArr2, stringArray, setEmptySet, intVector);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !getClass().equals(obj.getClass())) {
                return false;
            }
            Data data = (Data) obj;
            return Arrays.equals(this.trie, data.trie) && Arrays.equals(this.regionToPartitionsIndex, data.regionToPartitionsIndex) && Arrays.equals(this.partitionArrays, data.partitionArrays) && this.paradigmLSRs.equals(data.paradigmLSRs) && Arrays.equals(this.distances, data.distances);
        }
    }

    private LocaleDistance(Data data) {
        this.trie = new BytesTrie(data.trie, 0);
        this.regionToPartitionsIndex = data.regionToPartitionsIndex;
        this.partitionArrays = data.partitionArrays;
        this.paradigmLSRs = data.paradigmLSRs;
        this.defaultLanguageDistance = data.distances[0];
        this.defaultScriptDistance = data.distances[1];
        this.defaultRegionDistance = data.distances[2];
        this.minRegionDistance = data.distances[3];
    }

    public int testOnlyDistance(ULocale uLocale, ULocale uLocale2, int i, LocaleMatcher.FavorSubtag favorSubtag) {
        return getDistanceFloor(getBestIndexAndDistance(XLikelySubtags.INSTANCE.makeMaximizedLsrFrom(uLocale), new LSR[]{XLikelySubtags.INSTANCE.makeMaximizedLsrFrom(uLocale2)}, 1, shiftDistance(i), favorSubtag, LocaleMatcher.Direction.WITH_ONE_WAY));
    }

    /* JADX WARN: Code restructure failed: missing block: B:68:0x0118, code lost:
    
        if (isMatch(r13, r23, r11, r27) == false) goto L63;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int getBestIndexAndDistance(com.ibm.icu.impl.locale.LSR r23, com.ibm.icu.impl.locale.LSR[] r24, int r25, int r26, com.ibm.icu.util.LocaleMatcher.FavorSubtag r27, com.ibm.icu.util.LocaleMatcher.Direction r28) {
        /*
            Method dump skipped, instructions count: 351
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.locale.LocaleDistance.getBestIndexAndDistance(com.ibm.icu.impl.locale.LSR, com.ibm.icu.impl.locale.LSR[], int, int, com.ibm.icu.util.LocaleMatcher$FavorSubtag, com.ibm.icu.util.LocaleMatcher$Direction):int");
    }

    private boolean isMatch(LSR lsr, LSR lsr2, int i, LocaleMatcher.FavorSubtag favorSubtag) {
        return getBestIndexAndDistance(lsr, new LSR[]{lsr2}, 1, i, favorSubtag, null) >= 0;
    }

    private static final int getDesSuppScriptDistance(BytesTrie bytesTrie, long j, String str, String str2) {
        int iTrieNext = trieNext(bytesTrie, str, false);
        if (iTrieNext >= 0) {
            iTrieNext = trieNext(bytesTrie, str2, true);
        }
        if (iTrieNext >= 0) {
            return iTrieNext;
        }
        BytesTrie.Result next = bytesTrie.resetToState64(j).next(42);
        int value = str.equals(str2) ? 0 : bytesTrie.getValue();
        return next == BytesTrie.Result.FINAL_VALUE ? value | 256 : value;
    }

    private static final int getRegionPartitionsDistance(BytesTrie bytesTrie, long j, String str, String str2, int i) {
        int fallbackRegionDistance;
        int length = str.length();
        int length2 = str2.length();
        if (length == 1 && length2 == 1) {
            if (bytesTrie.next(str.charAt(0) | 128).hasNext() && bytesTrie.next(str2.charAt(0) | 128).hasValue()) {
                return bytesTrie.getValue();
            }
            return getFallbackRegionDistance(bytesTrie, j);
        }
        int i2 = 0;
        boolean z = false;
        int i3 = 0;
        while (true) {
            int i4 = i2 + 1;
            if (bytesTrie.next(str.charAt(i2) | 128).hasNext()) {
                long state64 = length2 > 1 ? bytesTrie.getState64() : 0L;
                int i5 = 0;
                while (true) {
                    int i6 = i5 + 1;
                    if (bytesTrie.next(str2.charAt(i5) | 128).hasValue()) {
                        fallbackRegionDistance = bytesTrie.getValue();
                    } else if (z) {
                        fallbackRegionDistance = 0;
                    } else {
                        fallbackRegionDistance = getFallbackRegionDistance(bytesTrie, j);
                        z = true;
                    }
                    if (fallbackRegionDistance <= i) {
                        if (i3 < fallbackRegionDistance) {
                            i3 = fallbackRegionDistance;
                        }
                        if (i6 >= length2) {
                            break;
                        }
                        bytesTrie.resetToState64(state64);
                        i5 = i6;
                    } else {
                        return fallbackRegionDistance;
                    }
                }
            } else if (!z) {
                int fallbackRegionDistance2 = getFallbackRegionDistance(bytesTrie, j);
                if (fallbackRegionDistance2 > i) {
                    return fallbackRegionDistance2;
                }
                if (i3 < fallbackRegionDistance2) {
                    i3 = fallbackRegionDistance2;
                }
                z = true;
            }
            if (i4 >= length) {
                return i3;
            }
            bytesTrie.resetToState64(j);
            i2 = i4;
        }
    }

    private static final int getFallbackRegionDistance(BytesTrie bytesTrie, long j) {
        bytesTrie.resetToState64(j).next(42);
        return bytesTrie.getValue();
    }

    private static final int trieNext(BytesTrie bytesTrie, String str, boolean z) {
        if (str.isEmpty()) {
            return -1;
        }
        int length = str.length() - 1;
        int i = 0;
        while (true) {
            char cCharAt = str.charAt(i);
            if (i < length) {
                if (!bytesTrie.next(cCharAt).hasNext()) {
                    return -1;
                }
                i++;
            } else {
                BytesTrie.Result next = bytesTrie.next(cCharAt | 128);
                if (z) {
                    if (next.hasValue()) {
                        int value = bytesTrie.getValue();
                        return next == BytesTrie.Result.FINAL_VALUE ? value | 256 : value;
                    }
                } else if (next.hasNext()) {
                    return 0;
                }
                return -1;
            }
        }
    }

    public String toString() {
        return testOnlyGetDistanceTable().toString();
    }

    private String partitionsForRegion(LSR lsr) {
        return this.partitionArrays[this.regionToPartitionsIndex[lsr.regionIndex]];
    }

    public boolean isParadigmLSR(LSR lsr) {
        Iterator<LSR> it2 = this.paradigmLSRs.iterator();
        while (it2.hasNext()) {
            if (lsr.isEquivalentTo(it2.next())) {
                return true;
            }
        }
        return false;
    }

    public int getDefaultScriptDistance() {
        return this.defaultScriptDistance;
    }

    int getDefaultRegionDistance() {
        return this.defaultRegionDistance;
    }

    public int getDefaultDemotionPerDesiredLocale() {
        return this.defaultDemotionPerDesiredLocale;
    }

    public Map<String, Integer> testOnlyGetDistanceTable() {
        TreeMap treeMap = new TreeMap();
        StringBuilder sb = new StringBuilder();
        Iterator<BytesTrie.Entry> itIterator2 = this.trie.iterator2();
        while (itIterator2.hasNext()) {
            BytesTrie.Entry next = itIterator2.next();
            sb.setLength(0);
            int iBytesLength = next.bytesLength();
            for (int i = 0; i < iBytesLength; i++) {
                byte bByteAt = next.byteAt(i);
                if (bByteAt == 42) {
                    sb.append("*-*-");
                } else if (bByteAt >= 0) {
                    sb.append((char) bByteAt);
                } else {
                    sb.append((char) (bByteAt & 127));
                    sb.append(Soundex.SILENT_MARKER);
                }
            }
            sb.setLength(sb.length() - 1);
            treeMap.put(sb.toString(), Integer.valueOf(next.value));
        }
        return treeMap;
    }

    public void testOnlyPrintDistanceTable() {
        String str;
        for (Map.Entry<String, Integer> entry : testOnlyGetDistanceTable().entrySet()) {
            int iIntValue = entry.getValue().intValue();
            if ((iIntValue & 128) != 0) {
                iIntValue &= -129;
                str = " skip script";
            } else {
                str = "";
            }
            System.out.println(entry.getKey() + '=' + iIntValue + str);
        }
    }
}
