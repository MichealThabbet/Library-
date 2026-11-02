package com.google.android.gms.internal.gtm;

import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.huawei.hms.framework.common.ContainerUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzcd {
    private final List<zzbk> zzaaz;
    private final long zzaba;
    private final long zzabb;
    private final int zzabc;
    private final boolean zzabd;
    private final String zzabe;
    private final Map<String, String> zztc;

    public zzcd(zzam zzamVar, Map<String, String> map, long j, boolean z) {
        this(zzamVar, map, j, z, 0L, 0, null);
    }

    public zzcd(zzam zzamVar, Map<String, String> map, long j, boolean z, long j2, int i) {
        this(zzamVar, map, j, z, j2, i, null);
    }

    public zzcd(zzam zzamVar, Map<String, String> map, long j, boolean z, long j2, int i, List<zzbk> list) {
        String value;
        String strZza;
        String strZza2;
        Preconditions.checkNotNull(zzamVar);
        Preconditions.checkNotNull(map);
        this.zzabb = j;
        this.zzabd = z;
        this.zzaba = j2;
        this.zzabc = i;
        this.zzaaz = list != null ? list : Collections.emptyList();
        if (list != null) {
            for (zzbk zzbkVar : list) {
                if ("appendVersion".equals(zzbkVar.getId())) {
                    value = zzbkVar.getValue();
                    break;
                }
            }
            value = null;
        } else {
            value = null;
        }
        this.zzabe = TextUtils.isEmpty(value) ? null : value;
        HashMap map2 = new HashMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (zzc(entry.getKey()) && (strZza2 = zza(zzamVar, entry.getKey())) != null) {
                map2.put(strZza2, zzb(zzamVar, entry.getValue()));
            }
        }
        for (Map.Entry<String, String> entry2 : map.entrySet()) {
            if (!zzc(entry2.getKey()) && (strZza = zza(zzamVar, entry2.getKey())) != null) {
                map2.put(strZza, zzb(zzamVar, entry2.getValue()));
            }
        }
        if (!TextUtils.isEmpty(this.zzabe)) {
            zzcz.zzb(map2, "_v", this.zzabe);
            if (this.zzabe.equals("ma4.0.0") || this.zzabe.equals("ma4.0.1")) {
                map2.remove("adid");
            }
        }
        this.zztc = Collections.unmodifiableMap(map2);
    }

    private static boolean zzc(Object obj) {
        if (obj == null) {
            return false;
        }
        return obj.toString().startsWith(ContainerUtils.FIELD_DELIMITER);
    }

    private static String zza(zzam zzamVar, Object obj) {
        if (obj == null) {
            return null;
        }
        String string = obj.toString();
        if (string.startsWith(ContainerUtils.FIELD_DELIMITER)) {
            string = string.substring(1);
        }
        int length = string.length();
        if (length > 256) {
            string = string.substring(0, 256);
            zzamVar.zzc("Hit param name is too long and will be trimmed", Integer.valueOf(length), string);
        }
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return string;
    }

    private static String zzb(zzam zzamVar, Object obj) {
        String string = obj == null ? "" : obj.toString();
        int length = string.length();
        if (length <= 8192) {
            return string;
        }
        String strSubstring = string.substring(0, 8192);
        zzamVar.zzc("Hit param value is too long and will be trimmed", Integer.valueOf(length), strSubstring);
        return strSubstring;
    }

    public final int zzff() {
        return this.zzabc;
    }

    public final Map<String, String> zzdm() {
        return this.zztc;
    }

    public final long zzfg() {
        return this.zzaba;
    }

    public final long zzfh() {
        return this.zzabb;
    }

    public final List<zzbk> zzfi() {
        return this.zzaaz;
    }

    public final boolean zzfj() {
        return this.zzabd;
    }

    public final long zzfk() {
        return zzcz.zzag(zzd("_s", "0"));
    }

    public final String zzfl() {
        return zzd("_m", "");
    }

    private final String zzd(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkArgument(!str.startsWith(ContainerUtils.FIELD_DELIMITER), "Short param name required");
        String str3 = this.zztc.get(str);
        return str3 != null ? str3 : str2;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ht=");
        sb.append(this.zzabb);
        if (this.zzaba != 0) {
            sb.append(", dbId=");
            sb.append(this.zzaba);
        }
        if (this.zzabc != 0) {
            sb.append(", appUID=");
            sb.append(this.zzabc);
        }
        ArrayList arrayList = new ArrayList(this.zztc.keySet());
        Collections.sort(arrayList);
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            String str = (String) obj;
            sb.append(", ");
            sb.append(str);
            sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
            sb.append(this.zztc.get(str));
        }
        return sb.toString();
    }
}
