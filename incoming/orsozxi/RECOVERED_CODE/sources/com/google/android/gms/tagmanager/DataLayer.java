package com.google.android.gms.tagmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class DataLayer {
    public static final String EVENT_KEY = "event";
    public static final Object OBJECT_NOT_PRESENT = new Object();
    private static final String[] zzafn = "gtm.lifetime".split("\\.");
    private static final Pattern zzafo = Pattern.compile("(\\d+)\\s*([smhd]?)");
    private final ConcurrentHashMap<zzb, Integer> zzafp;
    private final Map<String, Object> zzafq;
    private final ReentrantLock zzafr;
    private final LinkedList<Map<String, Object>> zzafs;
    private final zzc zzaft;
    private final CountDownLatch zzafu;

    interface zzb {
        void zzc(Map<String, Object> map);
    }

    interface zzc {
        void zza(zzaq zzaqVar);

        void zza(List<zza> list, long j);

        void zzas(String str);
    }

    DataLayer() {
        this(new zzao());
    }

    DataLayer(zzc zzcVar) {
        this.zzaft = zzcVar;
        this.zzafp = new ConcurrentHashMap<>();
        this.zzafq = new HashMap();
        this.zzafr = new ReentrantLock();
        this.zzafs = new LinkedList<>();
        this.zzafu = new CountDownLatch(1);
        this.zzaft.zza(new zzap(this));
    }

    static final class zza {
        public final String mKey;
        public final Object mValue;

        zza(String str, Object obj) {
            this.mKey = str;
            this.mValue = obj;
        }

        public final String toString() {
            String str = this.mKey;
            String string = this.mValue.toString();
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 13 + String.valueOf(string).length());
            sb.append("Key: ");
            sb.append(str);
            sb.append(" value: ");
            sb.append(string);
            return sb.toString();
        }

        public final int hashCode() {
            return Arrays.hashCode(new Integer[]{Integer.valueOf(this.mKey.hashCode()), Integer.valueOf(this.mValue.hashCode())});
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zza)) {
                return false;
            }
            zza zzaVar = (zza) obj;
            return this.mKey.equals(zzaVar.mKey) && this.mValue.equals(zzaVar.mValue);
        }
    }

    public String toString() {
        String string;
        synchronized (this.zzafq) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> entry : this.zzafq.entrySet()) {
                sb.append(String.format("{\n\tKey: %s\n\tValue: %s\n}\n", entry.getKey(), entry.getValue()));
            }
            string = sb.toString();
        }
        return string;
    }

    public void pushEvent(String str, Map<String, Object> map) throws InterruptedException {
        HashMap map2 = new HashMap(map);
        map2.put("event", str);
        push(map2);
    }

    public void push(String str, Object obj) throws InterruptedException {
        push(zzg(str, obj));
    }

    public void push(Map<String, Object> map) throws InterruptedException {
        try {
            this.zzafu.await();
        } catch (InterruptedException unused) {
            zzdi.zzac("DataLayer.push: unexpected InterruptedException");
        }
        zze(map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zze(Map<String, Object> map) {
        Long lZzar;
        this.zzafr.lock();
        try {
            this.zzafs.offer(map);
            int i = 0;
            if (this.zzafr.getHoldCount() == 1) {
                int i2 = 0;
                do {
                    Map<String, Object> mapPoll = this.zzafs.poll();
                    if (mapPoll != null) {
                        synchronized (this.zzafq) {
                            for (String str : mapPoll.keySet()) {
                                zzb(zzg(str, mapPoll.get(str)), this.zzafq);
                            }
                        }
                        Iterator<zzb> it2 = this.zzafp.keySet().iterator();
                        while (it2.hasNext()) {
                            it2.next().zzc(mapPoll);
                        }
                        i2++;
                    }
                } while (i2 <= 500);
                this.zzafs.clear();
                throw new RuntimeException("Seems like an infinite loop of pushing to the data layer");
            }
            String[] strArr = zzafn;
            int length = strArr.length;
            Object obj = map;
            while (true) {
                lZzar = null;
                if (i >= length) {
                    break;
                }
                String str2 = strArr[i];
                if (!(obj instanceof Map)) {
                    obj = null;
                    break;
                } else {
                    obj = ((Map) obj).get(str2);
                    i++;
                }
            }
            if (obj != null) {
                lZzar = zzar(obj.toString());
            }
            if (lZzar != null) {
                ArrayList arrayList = new ArrayList();
                zza(map, "", arrayList);
                this.zzaft.zza(arrayList, lZzar.longValue());
            }
        } finally {
            this.zzafr.unlock();
        }
    }

    final void zzaq(String str) throws InterruptedException {
        push(str, null);
        this.zzaft.zzas(str);
    }

    private final void zza(Map<String, Object> map, String str, Collection<zza> collection) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String str2 = str.length() == 0 ? "" : ".";
            String key = entry.getKey();
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + str2.length() + String.valueOf(key).length());
            sb.append(str);
            sb.append(str2);
            sb.append(key);
            String string = sb.toString();
            if (entry.getValue() instanceof Map) {
                zza((Map) entry.getValue(), string, collection);
            } else if (!string.equals("gtm.lifetime")) {
                collection.add(new zza(string, entry.getValue()));
            }
        }
    }

    private static Long zzar(String str) throws NumberFormatException {
        long j;
        Matcher matcher = zzafo.matcher(str);
        if (!matcher.matches()) {
            String strValueOf = String.valueOf(str);
            zzdi.zzaw(strValueOf.length() != 0 ? "unknown _lifetime: ".concat(strValueOf) : new String("unknown _lifetime: "));
            return null;
        }
        try {
            j = Long.parseLong(matcher.group(1));
        } catch (NumberFormatException unused) {
            String strValueOf2 = String.valueOf(str);
            zzdi.zzac(strValueOf2.length() != 0 ? "illegal number in _lifetime value: ".concat(strValueOf2) : new String("illegal number in _lifetime value: "));
            j = 0;
        }
        if (j <= 0) {
            String strValueOf3 = String.valueOf(str);
            zzdi.zzaw(strValueOf3.length() != 0 ? "non-positive _lifetime: ".concat(strValueOf3) : new String("non-positive _lifetime: "));
            return null;
        }
        String strGroup = matcher.group(2);
        if (strGroup.length() == 0) {
            return Long.valueOf(j);
        }
        char cCharAt = strGroup.charAt(0);
        if (cCharAt == 'd') {
            return Long.valueOf(j * 1000 * 60 * 60 * 24);
        }
        if (cCharAt == 'h') {
            return Long.valueOf(j * 1000 * 60 * 60);
        }
        if (cCharAt == 'm') {
            return Long.valueOf(j * 1000 * 60);
        }
        if (cCharAt == 's') {
            return Long.valueOf(j * 1000);
        }
        String strValueOf4 = String.valueOf(str);
        zzdi.zzac(strValueOf4.length() != 0 ? "unknown units in _lifetime: ".concat(strValueOf4) : new String("unknown units in _lifetime: "));
        return null;
    }

    public Object get(String str) {
        synchronized (this.zzafq) {
            Object obj = this.zzafq;
            for (String str2 : str.split("\\.")) {
                if (!(obj instanceof Map)) {
                    return null;
                }
                obj = ((Map) obj).get(str2);
                if (obj == null) {
                    return null;
                }
            }
            return obj;
        }
    }

    public static Map<String, Object> mapOf(Object... objArr) {
        if (objArr.length % 2 != 0) {
            throw new IllegalArgumentException("expected even number of key-value pairs");
        }
        HashMap map = new HashMap();
        for (int i = 0; i < objArr.length; i += 2) {
            if (!(objArr[i] instanceof String)) {
                String strValueOf = String.valueOf(objArr[i]);
                StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 21);
                sb.append("key is not a string: ");
                sb.append(strValueOf);
                throw new IllegalArgumentException(sb.toString());
            }
            map.put((String) objArr[i], objArr[i + 1]);
        }
        return map;
    }

    public static List<Object> listOf(Object... objArr) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : objArr) {
            arrayList.add(obj);
        }
        return arrayList;
    }

    final void zza(zzb zzbVar) {
        this.zzafp.put(zzbVar, 0);
    }

    static Map<String, Object> zzg(String str, Object obj) {
        HashMap map = new HashMap();
        String[] strArrSplit = str.toString().split("\\.");
        int i = 0;
        HashMap map2 = map;
        while (i < strArrSplit.length - 1) {
            HashMap map3 = new HashMap();
            map2.put(strArrSplit[i], map3);
            i++;
            map2 = map3;
        }
        map2.put(strArrSplit[strArrSplit.length - 1], obj);
        return map;
    }

    private final void zzb(Map<String, Object> map, Map<String, Object> map2) {
        for (String str : map.keySet()) {
            Object obj = map.get(str);
            if (obj instanceof List) {
                if (!(map2.get(str) instanceof List)) {
                    map2.put(str, new ArrayList());
                }
                zza((List<Object>) obj, (List<Object>) map2.get(str));
            } else if (obj instanceof Map) {
                if (!(map2.get(str) instanceof Map)) {
                    map2.put(str, new HashMap());
                }
                zzb((Map) obj, (Map) map2.get(str));
            } else {
                map2.put(str, obj);
            }
        }
    }

    private final void zza(List<Object> list, List<Object> list2) {
        while (list2.size() < list.size()) {
            list2.add(null);
        }
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            if (obj instanceof List) {
                if (!(list2.get(i) instanceof List)) {
                    list2.set(i, new ArrayList());
                }
                zza((List<Object>) obj, (List<Object>) list2.get(i));
            } else if (obj instanceof Map) {
                if (!(list2.get(i) instanceof Map)) {
                    list2.set(i, new HashMap());
                }
                zzb((Map) obj, (Map) list2.get(i));
            } else if (obj != OBJECT_NOT_PRESENT) {
                list2.set(i, obj);
            }
        }
    }
}
