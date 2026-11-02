package com.google.android.gms.tagmanager;

import android.content.Context;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.internal.gtm.zzor;
import com.google.android.gms.internal.gtm.zzot;
import com.google.android.gms.internal.gtm.zzov;
import com.google.android.gms.internal.gtm.zzox;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.message.TokenParser;

/* loaded from: classes.dex */
final class zzfb {
    private static final zzdz<com.google.android.gms.internal.gtm.zzl> zzajp = new zzdz<>(zzgj.zzkc(), true);
    private final DataLayer zzaed;
    private final zzov zzajq;
    private final zzbo zzajr;
    private final Map<String, zzbq> zzajs;
    private final Map<String, zzbq> zzajt;
    private final Map<String, zzbq> zzaju;
    private final zzp<zzot, zzdz<com.google.android.gms.internal.gtm.zzl>> zzajv;
    private final zzp<String, zzfh> zzajw;
    private final Set<zzox> zzajx;
    private final Map<String, zzfi> zzajy;
    private volatile String zzajz;
    private int zzaka;

    public zzfb(Context context, zzov zzovVar, DataLayer dataLayer, zzan zzanVar, zzan zzanVar2, zzbo zzboVar) {
        if (zzovVar == null) {
            throw new NullPointerException("resource cannot be null");
        }
        this.zzajq = zzovVar;
        this.zzajx = new HashSet(zzovVar.zzls());
        this.zzaed = dataLayer;
        this.zzajr = zzboVar;
        zzfc zzfcVar = new zzfc(this);
        new zzq();
        this.zzajv = zzq.zza(1048576, zzfcVar);
        zzfd zzfdVar = new zzfd(this);
        new zzq();
        this.zzajw = zzq.zza(1048576, zzfdVar);
        this.zzajs = new HashMap();
        zzb(new zzm(context));
        zzb(new zzam(zzanVar2));
        zzb(new zzaz(dataLayer));
        zzb(new zzgk(context, dataLayer));
        this.zzajt = new HashMap();
        zzc(new zzak());
        zzc(new zzbl());
        zzc(new zzbm());
        zzc(new zzbs());
        zzc(new zzbt());
        zzc(new zzde());
        zzc(new zzdf());
        zzc(new zzel());
        zzc(new zzfy());
        this.zzaju = new HashMap();
        zza(new zze(context));
        zza(new zzf(context));
        zza(new zzh(context));
        zza(new zzi(context));
        zza(new zzj(context));
        zza(new zzk(context));
        zza(new zzl(context));
        zza(new zzt());
        zza(new zzaj(this.zzajq.getVersion()));
        zza(new zzam(zzanVar));
        zza(new zzas(dataLayer));
        zza(new zzbc(context));
        zza(new zzbd());
        zza(new zzbk());
        zza(new zzbp(this));
        zza(new zzbu());
        zza(new zzbv());
        zza(new zzcv(context));
        zza(new zzcx());
        zza(new zzdd());
        zza(new zzdk());
        zza(new zzdm(context));
        zza(new zzea());
        zza(new zzee());
        zza(new zzei());
        zza(new zzek());
        zza(new zzem(context));
        zza(new zzfj());
        zza(new zzfk());
        zza(new zzge());
        zza(new zzgl());
        this.zzajy = new HashMap();
        for (zzox zzoxVar : this.zzajx) {
            for (int i = 0; i < zzoxVar.zzmq().size(); i++) {
                zzot zzotVar = zzoxVar.zzmq().get(i);
                zzfi zzfiVarZzb = zzb(this.zzajy, zza(zzotVar));
                zzfiVarZzb.zza(zzoxVar);
                zzfiVarZzb.zza(zzoxVar, zzotVar);
                zzfiVarZzb.zza(zzoxVar, AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN);
            }
            for (int i2 = 0; i2 < zzoxVar.zzmr().size(); i2++) {
                zzot zzotVar2 = zzoxVar.zzmr().get(i2);
                zzfi zzfiVarZzb2 = zzb(this.zzajy, zza(zzotVar2));
                zzfiVarZzb2.zza(zzoxVar);
                zzfiVarZzb2.zzb(zzoxVar, zzotVar2);
                zzfiVarZzb2.zzb(zzoxVar, AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN);
            }
        }
        for (Map.Entry<String, List<zzot>> entry : this.zzajq.zzmo().entrySet()) {
            for (zzot zzotVar3 : entry.getValue()) {
                if (!zzgj.zzg(zzotVar3.zzlu().get(com.google.android.gms.internal.gtm.zzb.NOT_DEFAULT_MACRO.toString())).booleanValue()) {
                    zzb(this.zzajy, entry.getKey()).zzb(zzotVar3);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x010a A[Catch: all -> 0x0180, TryCatch #0 {, blocks: (B:4:0x0003, B:5:0x0007, B:7:0x000d, B:9:0x0017, B:12:0x0023, B:14:0x0029, B:15:0x002f, B:17:0x0038, B:18:0x0044, B:21:0x004d, B:23:0x0057, B:26:0x0085, B:27:0x0088, B:24:0x0080, B:28:0x008b, B:30:0x0093, B:32:0x009b, B:58:0x0156, B:33:0x00a2, B:37:0x00bb, B:40:0x00cb, B:42:0x00d3, B:47:0x00f1, B:49:0x010a, B:51:0x0112, B:52:0x012e, B:54:0x0138, B:55:0x0148, B:56:0x014d, B:46:0x00df, B:57:0x0151, B:36:0x00b0, B:59:0x015a), top: B:66:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final synchronized void zze(java.util.List<com.google.android.gms.internal.gtm.zzj> r19) {
        /*
            Method dump skipped, instructions count: 387
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzfb.zze(java.util.List):void");
    }

    public final synchronized void zzan(String str) {
        zzbk(str);
        zzar zzarVarZzid = this.zzajr.zzba(str).zzid();
        Iterator<zzot> it2 = zza(this.zzajx, new HashSet(), new zzff(this), zzarVarZzid.zzhs()).getObject().iterator();
        while (it2.hasNext()) {
            zza(this.zzajs, it2.next(), new HashSet(), zzarVarZzid.zzhr());
        }
        zzbk(null);
    }

    public final zzdz<com.google.android.gms.internal.gtm.zzl> zzbj(String str) {
        this.zzaka = 0;
        return zza(str, new HashSet(), this.zzajr.zzaz(str).zzic());
    }

    private final synchronized void zzbk(String str) {
        this.zzajz = str;
    }

    final synchronized String zzjf() {
        return this.zzajz;
    }

    private static zzfi zzb(Map<String, zzfi> map, String str) {
        zzfi zzfiVar = map.get(str);
        if (zzfiVar != null) {
            return zzfiVar;
        }
        zzfi zzfiVar2 = new zzfi();
        map.put(str, zzfiVar2);
        return zzfiVar2;
    }

    private final zzdz<Set<zzot>> zza(Set<zzox> set, Set<String> set2, zzfg zzfgVar, zzfa zzfaVar) {
        boolean z;
        boolean z2;
        zzdz zzdzVar;
        Set<zzot> hashSet = new HashSet<>();
        Set<zzot> hashSet2 = new HashSet<>();
        while (true) {
            for (zzox zzoxVar : set) {
                zzeq zzeqVarZzis = zzfaVar.zzis();
                Iterator<zzot> it2 = zzoxVar.zzlx().iterator();
                while (true) {
                    while (true) {
                        if (it2.hasNext()) {
                            zzdz<Boolean> zzdzVarZza = zza(it2.next(), set2, zzeqVarZzis.zzim());
                            if (zzdzVarZza.getObject().booleanValue()) {
                                zzgj.zzi(false);
                                zzdzVar = new zzdz(false, zzdzVarZza.zziu());
                                break;
                            }
                            z2 = z2 && zzdzVarZza.zziu();
                        } else {
                            Iterator<zzot> it3 = zzoxVar.zzlw().iterator();
                            while (true) {
                                if (it3.hasNext()) {
                                    zzdz<Boolean> zzdzVarZza2 = zza(it3.next(), set2, zzeqVarZzis.zzin());
                                    if (!zzdzVarZza2.getObject().booleanValue()) {
                                        zzgj.zzi(false);
                                        zzdzVar = new zzdz(false, zzdzVarZza2.zziu());
                                        break;
                                    }
                                    z2 = z2 && zzdzVarZza2.zziu();
                                } else {
                                    zzgj.zzi(true);
                                    zzdzVar = new zzdz(true, z2);
                                    break;
                                }
                            }
                        }
                    }
                }
                if (((Boolean) zzdzVar.getObject()).booleanValue()) {
                    zzfgVar.zza(zzoxVar, hashSet, hashSet2, zzeqVarZzis);
                }
                z = z && zzdzVar.zziu();
            }
            hashSet.removeAll(hashSet2);
            zzfaVar.zzb(hashSet);
            return new zzdz<>(hashSet, z);
        }
    }

    private static String zza(zzot zzotVar) {
        return zzgj.zzc(zzotVar.zzlu().get(com.google.android.gms.internal.gtm.zzb.INSTANCE_NAME.toString()));
    }

    private static void zza(Map<String, zzbq> map, zzbq zzbqVar) {
        if (map.containsKey(zzbqVar.zzif())) {
            String strValueOf = String.valueOf(zzbqVar.zzif());
            throw new IllegalArgumentException(strValueOf.length() != 0 ? "Duplicate function type name: ".concat(strValueOf) : new String("Duplicate function type name: "));
        }
        map.put(zzbqVar.zzif(), zzbqVar);
    }

    private final void zza(zzbq zzbqVar) {
        zza(this.zzaju, zzbqVar);
    }

    private final void zzb(zzbq zzbqVar) {
        zza(this.zzajs, zzbqVar);
    }

    private final void zzc(zzbq zzbqVar) {
        zza(this.zzajt, zzbqVar);
    }

    private final zzdz<Boolean> zza(zzot zzotVar, Set<String> set, zzen zzenVar) {
        zzdz<com.google.android.gms.internal.gtm.zzl> zzdzVarZza = zza(this.zzajt, zzotVar, set, zzenVar);
        Boolean boolZzg = zzgj.zzg(zzdzVarZza.getObject());
        zzenVar.zza(zzgj.zzi(boolZzg));
        return new zzdz<>(boolZzg, zzdzVarZza.zziu());
    }

    private final String zzjg() {
        if (this.zzaka <= 1) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toString(this.zzaka));
        for (int i = 2; i < this.zzaka; i++) {
            sb.append(TokenParser.SP);
        }
        sb.append(": ");
        return sb.toString();
    }

    private final zzdz<com.google.android.gms.internal.gtm.zzl> zza(String str, Set<String> set, zzdl zzdlVar) throws InterruptedException {
        zzot next;
        this.zzaka++;
        zzfh zzfhVar = this.zzajw.get(str);
        if (zzfhVar != null) {
            this.zzajr.zzie();
            zza(zzfhVar.zzji(), set);
            this.zzaka--;
            return zzfhVar.zzjh();
        }
        zzfi zzfiVar = this.zzajy.get(str);
        if (zzfiVar == null) {
            String strZzjg = zzjg();
            StringBuilder sb = new StringBuilder(String.valueOf(strZzjg).length() + 15 + String.valueOf(str).length());
            sb.append(strZzjg);
            sb.append("Invalid macro: ");
            sb.append(str);
            zzdi.zzav(sb.toString());
            this.zzaka--;
            return zzajp;
        }
        zzdz<Set<zzot>> zzdzVarZza = zza(zzfiVar.zzjj(), set, new zzfe(this, zzfiVar.zzjk(), zzfiVar.zzjl(), zzfiVar.zzjn(), zzfiVar.zzjm()), zzdlVar.zzhs());
        if (zzdzVarZza.getObject().isEmpty()) {
            next = zzfiVar.zzjo();
        } else {
            if (zzdzVarZza.getObject().size() > 1) {
                String strZzjg2 = zzjg();
                StringBuilder sb2 = new StringBuilder(String.valueOf(strZzjg2).length() + 37 + String.valueOf(str).length());
                sb2.append(strZzjg2);
                sb2.append("Multiple macros active for macroName ");
                sb2.append(str);
                zzdi.zzac(sb2.toString());
            }
            next = zzdzVarZza.getObject().iterator().next();
        }
        if (next == null) {
            this.zzaka--;
            return zzajp;
        }
        zzdz<com.google.android.gms.internal.gtm.zzl> zzdzVarZza2 = zza(this.zzaju, next, set, zzdlVar.zzil());
        boolean z = zzdzVarZza.zziu() && zzdzVarZza2.zziu();
        zzdz<com.google.android.gms.internal.gtm.zzl> zzdzVar = zzajp;
        if (zzdzVarZza2 != zzdzVar) {
            zzdzVar = new zzdz<>(zzdzVarZza2.getObject(), z);
        }
        com.google.android.gms.internal.gtm.zzl zzlVarZzji = next.zzji();
        if (zzdzVar.zziu()) {
            this.zzajw.zza(str, new zzfh(zzdzVar, zzlVarZzji));
        }
        zza(zzlVarZzji, set);
        this.zzaka--;
        return zzdzVar;
    }

    private final void zza(com.google.android.gms.internal.gtm.zzl zzlVar, Set<String> set) throws InterruptedException {
        zzdz<com.google.android.gms.internal.gtm.zzl> zzdzVarZza;
        if (zzlVar == null || (zzdzVarZza = zza(zzlVar, set, new zzdx())) == zzajp) {
            return;
        }
        Object objZzh = zzgj.zzh(zzdzVarZza.getObject());
        if (objZzh instanceof Map) {
            this.zzaed.push((Map) objZzh);
            return;
        }
        if (objZzh instanceof List) {
            for (Object obj : (List) objZzh) {
                if (obj instanceof Map) {
                    this.zzaed.push((Map) obj);
                } else {
                    zzdi.zzac("pushAfterEvaluate: value not a Map");
                }
            }
            return;
        }
        zzdi.zzac("pushAfterEvaluate: value not a Map or List");
    }

    private final zzdz<com.google.android.gms.internal.gtm.zzl> zza(com.google.android.gms.internal.gtm.zzl zzlVar, Set<String> set, zzgm zzgmVar) {
        if (!zzlVar.zzqw) {
            return new zzdz<>(zzlVar, true);
        }
        int i = zzlVar.type;
        if (i == 2) {
            com.google.android.gms.internal.gtm.zzl zzlVarZzk = zzor.zzk(zzlVar);
            zzlVarZzk.zzqn = new com.google.android.gms.internal.gtm.zzl[zzlVar.zzqn.length];
            for (int i2 = 0; i2 < zzlVar.zzqn.length; i2++) {
                zzdz<com.google.android.gms.internal.gtm.zzl> zzdzVarZza = zza(zzlVar.zzqn[i2], set, zzgmVar.zzv(i2));
                zzdz<com.google.android.gms.internal.gtm.zzl> zzdzVar = zzajp;
                if (zzdzVarZza == zzdzVar) {
                    return zzdzVar;
                }
                zzlVarZzk.zzqn[i2] = zzdzVarZza.getObject();
            }
            return new zzdz<>(zzlVarZzk, false);
        }
        if (i == 3) {
            com.google.android.gms.internal.gtm.zzl zzlVarZzk2 = zzor.zzk(zzlVar);
            if (zzlVar.zzqo.length != zzlVar.zzqp.length) {
                String strValueOf = String.valueOf(zzlVar.toString());
                zzdi.zzav(strValueOf.length() != 0 ? "Invalid serving value: ".concat(strValueOf) : new String("Invalid serving value: "));
                return zzajp;
            }
            zzlVarZzk2.zzqo = new com.google.android.gms.internal.gtm.zzl[zzlVar.zzqo.length];
            zzlVarZzk2.zzqp = new com.google.android.gms.internal.gtm.zzl[zzlVar.zzqo.length];
            for (int i3 = 0; i3 < zzlVar.zzqo.length; i3++) {
                zzdz<com.google.android.gms.internal.gtm.zzl> zzdzVarZza2 = zza(zzlVar.zzqo[i3], set, zzgmVar.zzw(i3));
                zzdz<com.google.android.gms.internal.gtm.zzl> zzdzVarZza3 = zza(zzlVar.zzqp[i3], set, zzgmVar.zzx(i3));
                zzdz<com.google.android.gms.internal.gtm.zzl> zzdzVar2 = zzajp;
                if (zzdzVarZza2 == zzdzVar2 || zzdzVarZza3 == zzdzVar2) {
                    return zzajp;
                }
                zzlVarZzk2.zzqo[i3] = zzdzVarZza2.getObject();
                zzlVarZzk2.zzqp[i3] = zzdzVarZza3.getObject();
            }
            return new zzdz<>(zzlVarZzk2, false);
        }
        if (i == 4) {
            if (set.contains(zzlVar.zzqq)) {
                String str = zzlVar.zzqq;
                String string = set.toString();
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 79 + String.valueOf(string).length());
                sb.append("Macro cycle detected.  Current macro reference: ");
                sb.append(str);
                sb.append(".  Previous macro references: ");
                sb.append(string);
                sb.append(".");
                zzdi.zzav(sb.toString());
                return zzajp;
            }
            set.add(zzlVar.zzqq);
            zzdz<com.google.android.gms.internal.gtm.zzl> zzdzVarZza4 = zzgn.zza(zza(zzlVar.zzqq, set, zzgmVar.zzit()), zzlVar.zzqv);
            set.remove(zzlVar.zzqq);
            return zzdzVarZza4;
        }
        if (i == 7) {
            com.google.android.gms.internal.gtm.zzl zzlVarZzk3 = zzor.zzk(zzlVar);
            zzlVarZzk3.zzqu = new com.google.android.gms.internal.gtm.zzl[zzlVar.zzqu.length];
            for (int i4 = 0; i4 < zzlVar.zzqu.length; i4++) {
                zzdz<com.google.android.gms.internal.gtm.zzl> zzdzVarZza5 = zza(zzlVar.zzqu[i4], set, zzgmVar.zzy(i4));
                zzdz<com.google.android.gms.internal.gtm.zzl> zzdzVar3 = zzajp;
                if (zzdzVarZza5 == zzdzVar3) {
                    return zzdzVar3;
                }
                zzlVarZzk3.zzqu[i4] = zzdzVarZza5.getObject();
            }
            return new zzdz<>(zzlVarZzk3, false);
        }
        int i5 = zzlVar.type;
        StringBuilder sb2 = new StringBuilder(25);
        sb2.append("Unknown type: ");
        sb2.append(i5);
        zzdi.zzav(sb2.toString());
        return zzajp;
    }

    private final zzdz<com.google.android.gms.internal.gtm.zzl> zza(Map<String, zzbq> map, zzot zzotVar, Set<String> set, zzen zzenVar) {
        com.google.android.gms.internal.gtm.zzl zzlVar = zzotVar.zzlu().get(com.google.android.gms.internal.gtm.zzb.FUNCTION.toString());
        if (zzlVar == null) {
            zzdi.zzav("No function id in properties");
            return zzajp;
        }
        String str = zzlVar.zzqr;
        zzbq zzbqVar = map.get(str);
        if (zzbqVar == null) {
            zzdi.zzav(String.valueOf(str).concat(" has no backing implementation."));
            return zzajp;
        }
        zzdz<com.google.android.gms.internal.gtm.zzl> zzdzVar = this.zzajv.get(zzotVar);
        if (zzdzVar != null) {
            this.zzajr.zzie();
            return zzdzVar;
        }
        HashMap map2 = new HashMap();
        boolean z = true;
        for (Map.Entry<String, com.google.android.gms.internal.gtm.zzl> entry : zzotVar.zzlu().entrySet()) {
            zzdz<com.google.android.gms.internal.gtm.zzl> zzdzVarZza = zza(entry.getValue(), set, zzenVar.zzbg(entry.getKey()).zzb(entry.getValue()));
            zzdz<com.google.android.gms.internal.gtm.zzl> zzdzVar2 = zzajp;
            if (zzdzVarZza == zzdzVar2) {
                return zzdzVar2;
            }
            if (zzdzVarZza.zziu()) {
                zzotVar.zza(entry.getKey(), zzdzVarZza.getObject());
            } else {
                z = false;
            }
            map2.put(entry.getKey(), zzdzVarZza.getObject());
        }
        if (!zzbqVar.zza(map2.keySet())) {
            String strValueOf = String.valueOf(zzbqVar.zzig());
            String strValueOf2 = String.valueOf(map2.keySet());
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 43 + String.valueOf(strValueOf).length() + String.valueOf(strValueOf2).length());
            sb.append("Incorrect keys for function ");
            sb.append(str);
            sb.append(" required ");
            sb.append(strValueOf);
            sb.append(" had ");
            sb.append(strValueOf2);
            zzdi.zzav(sb.toString());
            return zzajp;
        }
        boolean z2 = z && zzbqVar.zzgw();
        zzdz<com.google.android.gms.internal.gtm.zzl> zzdzVar3 = new zzdz<>(zzbqVar.zzb(map2), z2);
        if (z2) {
            this.zzajv.zza(zzotVar, zzdzVar3);
        }
        zzenVar.zza(zzdzVar3.getObject());
        return zzdzVar3;
    }
}
