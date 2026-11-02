package com.ibm.icu.util;

import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/* loaded from: classes2.dex */
public class Region implements Comparable<Region> {
    private static final String OUTLYING_OCEANIA_REGION_ID = "QO";
    private static final String UNKNOWN_REGION_ID = "ZZ";
    private static final String WORLD_ID = "001";
    private static ArrayList<Set<Region>> availableRegions = null;
    private static Map<Integer, Region> numericCodeMap = null;
    private static Map<String, Region> regionAliases = null;
    private static boolean regionDataIsLoaded = false;
    private static Map<String, Region> regionIDMap;
    private static ArrayList<Region> regions;
    private int code;
    private String id;
    private RegionType type;
    private Region containingRegion = null;
    private Set<Region> containedRegions = new TreeSet();
    private List<Region> preferredValues = null;

    public enum RegionType {
        UNKNOWN,
        TERRITORY,
        WORLD,
        CONTINENT,
        SUBCONTINENT,
        GROUPING,
        DEPRECATED
    }

    private Region() {
    }

    private static synchronized void loadRegionData() {
        Region region;
        if (regionDataIsLoaded) {
            return;
        }
        regionAliases = new HashMap();
        regionIDMap = new HashMap();
        numericCodeMap = new HashMap();
        availableRegions = new ArrayList<>(RegionType.values().length);
        UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "metadata", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("alias").get("territory");
        UResourceBundle bundleInstance = UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        UResourceBundle uResourceBundle2 = bundleInstance.get("codeMappings");
        UResourceBundle uResourceBundle3 = bundleInstance.get("idValidity").get("region");
        UResourceBundle uResourceBundle4 = uResourceBundle3.get("regular");
        UResourceBundle uResourceBundle5 = uResourceBundle3.get("macroregion");
        UResourceBundle uResourceBundle6 = uResourceBundle3.get("unknown");
        UResourceBundle uResourceBundle7 = bundleInstance.get("territoryContainment");
        UResourceBundle uResourceBundle8 = uResourceBundle7.get("001");
        UResourceBundle uResourceBundle9 = uResourceBundle7.get("grouping");
        List<String> listAsList = Arrays.asList(uResourceBundle8.getStringArray());
        Enumeration<String> keys = uResourceBundle9.getKeys();
        ArrayList<String> arrayList = new ArrayList();
        ArrayList<String> arrayList2 = new ArrayList();
        arrayList2.addAll(Arrays.asList(uResourceBundle4.getStringArray()));
        arrayList2.addAll(Arrays.asList(uResourceBundle5.getStringArray()));
        arrayList2.add(uResourceBundle6.getString());
        for (String str : arrayList2) {
            int iIndexOf = str.indexOf("~");
            if (iIndexOf > 0) {
                StringBuilder sb = new StringBuilder(str);
                char cCharAt = sb.charAt(iIndexOf + 1);
                sb.setLength(iIndexOf);
                int i = iIndexOf - 1;
                char cCharAt2 = sb.charAt(i);
                while (cCharAt2 <= cCharAt) {
                    arrayList.add(sb.toString());
                    cCharAt2 = (char) (cCharAt2 + 1);
                    sb.setCharAt(i, cCharAt2);
                }
            } else {
                arrayList.add(str);
            }
        }
        regions = new ArrayList<>(arrayList.size());
        for (String str2 : arrayList) {
            Region region2 = new Region();
            region2.id = str2;
            region2.type = RegionType.TERRITORY;
            regionIDMap.put(str2, region2);
            if (str2.matches("[0-9]{3}")) {
                int iIntValue = Integer.valueOf(str2).intValue();
                region2.code = iIntValue;
                numericCodeMap.put(Integer.valueOf(iIntValue), region2);
                region2.type = RegionType.SUBCONTINENT;
            } else {
                region2.code = -1;
            }
            regions.add(region2);
        }
        for (int i2 = 0; i2 < uResourceBundle.getSize(); i2++) {
            UResourceBundle uResourceBundle10 = uResourceBundle.get(i2);
            String key = uResourceBundle10.getKey();
            String string = uResourceBundle10.get("replacement").getString();
            if (regionIDMap.containsKey(string) && !regionIDMap.containsKey(key)) {
                regionAliases.put(key, regionIDMap.get(string));
            } else {
                if (regionIDMap.containsKey(key)) {
                    region = regionIDMap.get(key);
                } else {
                    Region region3 = new Region();
                    region3.id = key;
                    regionIDMap.put(key, region3);
                    if (key.matches("[0-9]{3}")) {
                        int iIntValue2 = Integer.valueOf(key).intValue();
                        region3.code = iIntValue2;
                        numericCodeMap.put(Integer.valueOf(iIntValue2), region3);
                    } else {
                        region3.code = -1;
                    }
                    regions.add(region3);
                    region = region3;
                }
                region.type = RegionType.DEPRECATED;
                List<String> listAsList2 = Arrays.asList(string.split(" "));
                region.preferredValues = new ArrayList();
                for (String str3 : listAsList2) {
                    if (regionIDMap.containsKey(str3)) {
                        region.preferredValues.add(regionIDMap.get(str3));
                    }
                }
            }
        }
        for (int i3 = 0; i3 < uResourceBundle2.getSize(); i3++) {
            UResourceBundle uResourceBundle11 = uResourceBundle2.get(i3);
            if (uResourceBundle11.getType() == 8) {
                String[] stringArray = uResourceBundle11.getStringArray();
                String str4 = stringArray[0];
                Integer numValueOf = Integer.valueOf(stringArray[1]);
                String str5 = stringArray[2];
                if (regionIDMap.containsKey(str4)) {
                    Region region4 = regionIDMap.get(str4);
                    int iIntValue3 = numValueOf.intValue();
                    region4.code = iIntValue3;
                    numericCodeMap.put(Integer.valueOf(iIntValue3), region4);
                    regionAliases.put(str5, region4);
                }
            }
        }
        if (regionIDMap.containsKey("001")) {
            regionIDMap.get("001").type = RegionType.WORLD;
        }
        if (regionIDMap.containsKey(UNKNOWN_REGION_ID)) {
            regionIDMap.get(UNKNOWN_REGION_ID).type = RegionType.UNKNOWN;
        }
        for (String str6 : listAsList) {
            if (regionIDMap.containsKey(str6)) {
                regionIDMap.get(str6).type = RegionType.CONTINENT;
            }
        }
        while (keys.hasMoreElements()) {
            String strNextElement = keys.nextElement();
            if (regionIDMap.containsKey(strNextElement)) {
                regionIDMap.get(strNextElement).type = RegionType.GROUPING;
            }
        }
        if (regionIDMap.containsKey(OUTLYING_OCEANIA_REGION_ID)) {
            regionIDMap.get(OUTLYING_OCEANIA_REGION_ID).type = RegionType.SUBCONTINENT;
        }
        for (int i4 = 0; i4 < uResourceBundle7.getSize(); i4++) {
            UResourceBundle uResourceBundle12 = uResourceBundle7.get(i4);
            String key2 = uResourceBundle12.getKey();
            if (!key2.equals("containedGroupings") && !key2.equals("deprecated") && !key2.equals("grouping")) {
                Region region5 = regionIDMap.get(key2);
                for (int i5 = 0; i5 < uResourceBundle12.getSize(); i5++) {
                    Region region6 = regionIDMap.get(uResourceBundle12.getString(i5));
                    if (region5 != null && region6 != null) {
                        region5.containedRegions.add(region6);
                        if (region5.getType() != RegionType.GROUPING) {
                            region6.containingRegion = region5;
                        }
                    }
                }
            }
        }
        for (int i6 = 0; i6 < RegionType.values().length; i6++) {
            availableRegions.add(new TreeSet());
        }
        Iterator<Region> it2 = regions.iterator();
        while (it2.hasNext()) {
            Region next = it2.next();
            Set<Region> set = availableRegions.get(next.type.ordinal());
            set.add(next);
            availableRegions.set(next.type.ordinal(), set);
        }
        regionDataIsLoaded = true;
    }

    public static Region getInstance(String str) {
        if (str == null) {
            throw null;
        }
        loadRegionData();
        Region region = regionIDMap.get(str);
        if (region == null) {
            region = regionAliases.get(str);
        }
        if (region != null) {
            return (region.type == RegionType.DEPRECATED && region.preferredValues.size() == 1) ? region.preferredValues.get(0) : region;
        }
        throw new IllegalArgumentException("Unknown region id: " + str);
    }

    public static Region getInstance(int i) {
        loadRegionData();
        Region region = numericCodeMap.get(Integer.valueOf(i));
        if (region == null) {
            region = regionAliases.get((i < 10 ? "00" : i < 100 ? "0" : "") + Integer.toString(i));
        }
        if (region != null) {
            return (region.type == RegionType.DEPRECATED && region.preferredValues.size() == 1) ? region.preferredValues.get(0) : region;
        }
        throw new IllegalArgumentException("Unknown region code: " + i);
    }

    public static Set<Region> getAvailable(RegionType regionType) {
        loadRegionData();
        return Collections.unmodifiableSet(availableRegions.get(regionType.ordinal()));
    }

    public Region getContainingRegion() {
        loadRegionData();
        return this.containingRegion;
    }

    public Region getContainingRegion(RegionType regionType) {
        loadRegionData();
        Region region = this.containingRegion;
        if (region == null) {
            return null;
        }
        if (region.type.equals(regionType)) {
            return this.containingRegion;
        }
        return this.containingRegion.getContainingRegion(regionType);
    }

    public Set<Region> getContainedRegions() {
        loadRegionData();
        return Collections.unmodifiableSet(this.containedRegions);
    }

    public Set<Region> getContainedRegions(RegionType regionType) {
        loadRegionData();
        TreeSet treeSet = new TreeSet();
        for (Region region : getContainedRegions()) {
            if (region.getType() == regionType) {
                treeSet.add(region);
            } else {
                treeSet.addAll(region.getContainedRegions(regionType));
            }
        }
        return Collections.unmodifiableSet(treeSet);
    }

    public List<Region> getPreferredValues() {
        loadRegionData();
        if (this.type == RegionType.DEPRECATED) {
            return Collections.unmodifiableList(this.preferredValues);
        }
        return null;
    }

    public boolean contains(Region region) {
        loadRegionData();
        if (this.containedRegions.contains(region)) {
            return true;
        }
        Iterator<Region> it2 = this.containedRegions.iterator();
        while (it2.hasNext()) {
            if (it2.next().contains(region)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return this.id;
    }

    public int getNumericCode() {
        return this.code;
    }

    public RegionType getType() {
        return this.type;
    }

    @Override // java.lang.Comparable
    public int compareTo(Region region) {
        return this.id.compareTo(region.id);
    }
}
