package com.google.android.gms.tagmanager;

import com.huawei.hms.framework.common.ContainerUtils;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
final class zzcx extends zzbq {
    private static final String ID = com.google.android.gms.internal.gtm.zza.JOINER.toString();
    private static final String zzags = com.google.android.gms.internal.gtm.zzb.ARG0.toString();
    private static final String zzahl = com.google.android.gms.internal.gtm.zzb.ITEM_SEPARATOR.toString();
    private static final String zzahm = com.google.android.gms.internal.gtm.zzb.KEY_VALUE_SEPARATOR.toString();
    private static final String zzahn = com.google.android.gms.internal.gtm.zzb.ESCAPE.toString();

    public zzcx() {
        super(ID, zzags);
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final boolean zzgw() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final com.google.android.gms.internal.gtm.zzl zzb(Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        com.google.android.gms.internal.gtm.zzl zzlVar = map.get(zzags);
        if (zzlVar == null) {
            return zzgj.zzkc();
        }
        com.google.android.gms.internal.gtm.zzl zzlVar2 = map.get(zzahl);
        String strZzc = zzlVar2 != null ? zzgj.zzc(zzlVar2) : "";
        com.google.android.gms.internal.gtm.zzl zzlVar3 = map.get(zzahm);
        String strZzc2 = zzlVar3 != null ? zzgj.zzc(zzlVar3) : ContainerUtils.KEY_VALUE_DELIMITER;
        int i = zzcz.zzahp;
        com.google.android.gms.internal.gtm.zzl zzlVar4 = map.get(zzahn);
        HashSet hashSet = null;
        if (zzlVar4 != null) {
            String strZzc3 = zzgj.zzc(zzlVar4);
            if ("url".equals(strZzc3)) {
                i = zzcz.zzahq;
            } else if ("backslash".equals(strZzc3)) {
                i = zzcz.zzahr;
                hashSet = new HashSet();
                zza(hashSet, strZzc);
                zza(hashSet, strZzc2);
                hashSet.remove('\\');
            } else {
                String strValueOf = String.valueOf(strZzc3);
                zzdi.zzav(strValueOf.length() != 0 ? "Joiner: unsupported escape type: ".concat(strValueOf) : new String("Joiner: unsupported escape type: "));
                return zzgj.zzkc();
            }
        }
        StringBuilder sb = new StringBuilder();
        int i2 = zzlVar.type;
        if (i2 == 2) {
            com.google.android.gms.internal.gtm.zzl[] zzlVarArr = zzlVar.zzqn;
            int length = zzlVarArr.length;
            boolean z = true;
            int i3 = 0;
            while (i3 < length) {
                com.google.android.gms.internal.gtm.zzl zzlVar5 = zzlVarArr[i3];
                if (!z) {
                    sb.append(strZzc);
                }
                zza(sb, zzgj.zzc(zzlVar5), i, hashSet);
                i3++;
                z = false;
            }
        } else if (i2 == 3) {
            for (int i4 = 0; i4 < zzlVar.zzqo.length; i4++) {
                if (i4 > 0) {
                    sb.append(strZzc);
                }
                String strZzc4 = zzgj.zzc(zzlVar.zzqo[i4]);
                String strZzc5 = zzgj.zzc(zzlVar.zzqp[i4]);
                zza(sb, strZzc4, i, hashSet);
                sb.append(strZzc2);
                zza(sb, strZzc5, i, hashSet);
            }
        } else {
            zza(sb, zzgj.zzc(zzlVar), i, hashSet);
        }
        return zzgj.zzi(sb.toString());
    }

    private static void zza(Set<Character> set, String str) {
        for (int i = 0; i < str.length(); i++) {
            set.add(Character.valueOf(str.charAt(i)));
        }
    }

    /* JADX WARN: Incorrect types in method signature: (Ljava/lang/StringBuilder;Ljava/lang/String;Ljava/lang/Integer;Ljava/util/Set<Ljava/lang/Character;>;)V */
    private static void zza(StringBuilder sb, String str, int i, Set set) {
        sb.append(zza(str, i, set));
    }

    /* JADX WARN: Incorrect types in method signature: (Ljava/lang/String;Ljava/lang/Integer;Ljava/util/Set<Ljava/lang/Character;>;)Ljava/lang/String; */
    private static String zza(String str, int i, Set set) {
        int i2 = zzcy.zzaho[i - 1];
        if (i2 == 1) {
            try {
                return zzgn.zzbs(str);
            } catch (UnsupportedEncodingException e) {
                zzdi.zza("Joiner: unsupported encoding", e);
                return str;
            }
        }
        if (i2 != 2) {
            return str;
        }
        String strReplace = str.replace("\\", "\\\\");
        Iterator it2 = set.iterator();
        while (it2.hasNext()) {
            String string = ((Character) it2.next()).toString();
            String strValueOf = String.valueOf(string);
            strReplace = strReplace.replace(string, strValueOf.length() != 0 ? "\\".concat(strValueOf) : new String("\\"));
        }
        return strReplace;
    }
}
