package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzqv;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
final class zzqt<FieldDescriptorType extends zzqv<FieldDescriptorType>> {
    private static final zzqt zzaxq = new zzqt(true);
    private boolean zzaut;
    private boolean zzaxp = false;
    final zztc<FieldDescriptorType, Object> zzaxo = zztc.zzbu(16);

    private zzqt() {
    }

    private zzqt(boolean z) {
        zzmi();
    }

    public static <T extends zzqv<T>> zzqt<T> zzov() {
        return zzaxq;
    }

    public final void zzmi() {
        if (this.zzaut) {
            return;
        }
        this.zzaxo.zzmi();
        this.zzaut = true;
    }

    public final boolean isImmutable() {
        return this.zzaut;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzqt) {
            return this.zzaxo.equals(((zzqt) obj).zzaxo);
        }
        return false;
    }

    public final int hashCode() {
        return this.zzaxo.hashCode();
    }

    public final Iterator<Map.Entry<FieldDescriptorType, Object>> iterator() {
        if (this.zzaxp) {
            return new zzrq(this.zzaxo.entrySet().iterator());
        }
        return this.zzaxo.entrySet().iterator();
    }

    final Iterator<Map.Entry<FieldDescriptorType, Object>> descendingIterator() {
        if (this.zzaxp) {
            return new zzrq(this.zzaxo.zzrc().iterator());
        }
        return this.zzaxo.zzrc().iterator();
    }

    private final Object zza(FieldDescriptorType fielddescriptortype) {
        Object obj = this.zzaxo.get(fielddescriptortype);
        return obj instanceof zzrn ? zzrn.zzpy() : obj;
    }

    private final void zza(FieldDescriptorType fielddescriptortype, Object obj) {
        if (fielddescriptortype.zzoz()) {
            if (!(obj instanceof List)) {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            ArrayList arrayList2 = arrayList;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList2.get(i);
                i++;
                zza(fielddescriptortype.zzox(), obj2);
            }
            obj = arrayList;
        } else {
            zza(fielddescriptortype.zzox(), obj);
        }
        if (obj instanceof zzrn) {
            this.zzaxp = true;
        }
        this.zzaxo.zza((zztc<FieldDescriptorType, Object>) fielddescriptortype, (FieldDescriptorType) obj);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:3:0x0011. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:10:0x001e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static void zza(com.google.android.gms.internal.gtm.zzug r2, java.lang.Object r3) {
        /*
            com.google.android.gms.internal.gtm.zzre.checkNotNull(r3)
            int[] r0 = com.google.android.gms.internal.gtm.zzqu.zzaxr
            com.google.android.gms.internal.gtm.zzul r2 = r2.zzrs()
            int r2 = r2.ordinal()
            r2 = r0[r2]
            r0 = 1
            r1 = 0
            switch(r2) {
                case 1: goto L41;
                case 2: goto L3e;
                case 3: goto L3b;
                case 4: goto L38;
                case 5: goto L35;
                case 6: goto L32;
                case 7: goto L29;
                case 8: goto L20;
                case 9: goto L15;
                default: goto L14;
            }
        L14:
            goto L44
        L15:
            boolean r2 = r3 instanceof com.google.android.gms.internal.gtm.zzsk
            if (r2 != 0) goto L43
            boolean r2 = r3 instanceof com.google.android.gms.internal.gtm.zzrn
            if (r2 == 0) goto L1e
            goto L43
        L1e:
            r0 = 0
            goto L43
        L20:
            boolean r2 = r3 instanceof java.lang.Integer
            if (r2 != 0) goto L43
            boolean r2 = r3 instanceof com.google.android.gms.internal.gtm.zzrf
            if (r2 == 0) goto L1e
            goto L43
        L29:
            boolean r2 = r3 instanceof com.google.android.gms.internal.gtm.zzps
            if (r2 != 0) goto L43
            boolean r2 = r3 instanceof byte[]
            if (r2 == 0) goto L1e
            goto L43
        L32:
            boolean r0 = r3 instanceof java.lang.String
            goto L43
        L35:
            boolean r0 = r3 instanceof java.lang.Boolean
            goto L43
        L38:
            boolean r0 = r3 instanceof java.lang.Double
            goto L43
        L3b:
            boolean r0 = r3 instanceof java.lang.Float
            goto L43
        L3e:
            boolean r0 = r3 instanceof java.lang.Long
            goto L43
        L41:
            boolean r0 = r3 instanceof java.lang.Integer
        L43:
            r1 = r0
        L44:
            if (r1 == 0) goto L47
            return
        L47:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r3 = "Wrong object type used with protocol message reflection."
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzqt.zza(com.google.android.gms.internal.gtm.zzug, java.lang.Object):void");
    }

    public final boolean isInitialized() {
        for (int i = 0; i < this.zzaxo.zzra(); i++) {
            if (!zzc(this.zzaxo.zzbv(i))) {
                return false;
            }
        }
        Iterator it2 = this.zzaxo.zzrb().iterator();
        while (it2.hasNext()) {
            if (!zzc((Map.Entry) it2.next())) {
                return false;
            }
        }
        return true;
    }

    private static boolean zzc(Map.Entry<FieldDescriptorType, Object> entry) {
        FieldDescriptorType key = entry.getKey();
        if (key.zzoy() == zzul.MESSAGE) {
            if (key.zzoz()) {
                Iterator it2 = ((List) entry.getValue()).iterator();
                while (it2.hasNext()) {
                    if (!((zzsk) it2.next()).isInitialized()) {
                        return false;
                    }
                }
            } else {
                Object value = entry.getValue();
                if (value instanceof zzsk) {
                    if (!((zzsk) value).isInitialized()) {
                        return false;
                    }
                } else {
                    if (value instanceof zzrn) {
                        return true;
                    }
                    throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
                }
            }
        }
        return true;
    }

    public final void zza(zzqt<FieldDescriptorType> zzqtVar) {
        for (int i = 0; i < zzqtVar.zzaxo.zzra(); i++) {
            zzd(zzqtVar.zzaxo.zzbv(i));
        }
        Iterator it2 = zzqtVar.zzaxo.zzrb().iterator();
        while (it2.hasNext()) {
            zzd((Map.Entry) it2.next());
        }
    }

    private static Object zzu(Object obj) {
        if (obj instanceof zzsq) {
            return ((zzsq) obj).zzqo();
        }
        if (!(obj instanceof byte[])) {
            return obj;
        }
        byte[] bArr = (byte[]) obj;
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    private final void zzd(Map.Entry<FieldDescriptorType, Object> entry) {
        zzsk zzskVarZzpm;
        FieldDescriptorType key = entry.getKey();
        Object value = entry.getValue();
        if (value instanceof zzrn) {
            value = zzrn.zzpy();
        }
        if (key.zzoz()) {
            Object objZza = zza((zzqt<FieldDescriptorType>) key);
            if (objZza == null) {
                objZza = new ArrayList();
            }
            Iterator it2 = ((List) value).iterator();
            while (it2.hasNext()) {
                ((List) objZza).add(zzu(it2.next()));
            }
            this.zzaxo.zza((zztc<FieldDescriptorType, Object>) key, (FieldDescriptorType) objZza);
            return;
        }
        if (key.zzoy() == zzul.MESSAGE) {
            Object objZza2 = zza((zzqt<FieldDescriptorType>) key);
            if (objZza2 == null) {
                this.zzaxo.zza((zztc<FieldDescriptorType, Object>) key, (FieldDescriptorType) zzu(value));
                return;
            }
            if (objZza2 instanceof zzsq) {
                zzskVarZzpm = key.zza((zzsq) objZza2, (zzsq) value);
            } else {
                zzskVarZzpm = key.zza(((zzsk) objZza2).zzpg(), (zzsk) value).zzpm();
            }
            this.zzaxo.zza((zztc<FieldDescriptorType, Object>) key, (FieldDescriptorType) zzskVarZzpm);
            return;
        }
        this.zzaxo.zza((zztc<FieldDescriptorType, Object>) key, (FieldDescriptorType) zzu(value));
    }

    static void zza(zzqj zzqjVar, zzug zzugVar, int i, Object obj) throws IOException {
        if (zzugVar == zzug.zzbfy) {
            zzsk zzskVar = (zzsk) obj;
            zzre.zzf(zzskVar);
            zzqjVar.zzd(i, 3);
            zzskVar.zzb(zzqjVar);
            zzqjVar.zzd(i, 4);
        }
        zzqjVar.zzd(i, zzugVar.zzrt());
        switch (zzqu.zzaws[zzugVar.ordinal()]) {
            case 1:
                zzqjVar.zzb(((Double) obj).doubleValue());
                break;
            case 2:
                zzqjVar.zza(((Float) obj).floatValue());
                break;
            case 3:
                zzqjVar.zzp(((Long) obj).longValue());
                break;
            case 4:
                zzqjVar.zzp(((Long) obj).longValue());
                break;
            case 5:
                zzqjVar.zzax(((Integer) obj).intValue());
                break;
            case 6:
                zzqjVar.zzr(((Long) obj).longValue());
                break;
            case 7:
                zzqjVar.zzba(((Integer) obj).intValue());
                break;
            case 8:
                zzqjVar.zzi(((Boolean) obj).booleanValue());
                break;
            case 9:
                ((zzsk) obj).zzb(zzqjVar);
                break;
            case 10:
                zzqjVar.zzb((zzsk) obj);
                break;
            case 11:
                if (obj instanceof zzps) {
                    zzqjVar.zza((zzps) obj);
                    break;
                } else {
                    zzqjVar.zzcz((String) obj);
                    break;
                }
            case 12:
                if (obj instanceof zzps) {
                    zzqjVar.zza((zzps) obj);
                    break;
                } else {
                    byte[] bArr = (byte[]) obj;
                    zzqjVar.zze(bArr, 0, bArr.length);
                    break;
                }
            case 13:
                zzqjVar.zzay(((Integer) obj).intValue());
                break;
            case 14:
                zzqjVar.zzba(((Integer) obj).intValue());
                break;
            case 15:
                zzqjVar.zzr(((Long) obj).longValue());
                break;
            case 16:
                zzqjVar.zzaz(((Integer) obj).intValue());
                break;
            case 17:
                zzqjVar.zzq(((Long) obj).longValue());
                break;
            case 18:
                if (obj instanceof zzrf) {
                    zzqjVar.zzax(((zzrf) obj).zzc());
                    break;
                } else {
                    zzqjVar.zzax(((Integer) obj).intValue());
                    break;
                }
        }
    }

    public final int zzow() {
        int iZze = 0;
        for (int i = 0; i < this.zzaxo.zzra(); i++) {
            iZze += zze(this.zzaxo.zzbv(i));
        }
        Iterator it2 = this.zzaxo.zzrb().iterator();
        while (it2.hasNext()) {
            iZze += zze((Map.Entry) it2.next());
        }
        return iZze;
    }

    private static int zze(Map.Entry<FieldDescriptorType, Object> entry) {
        FieldDescriptorType key = entry.getKey();
        Object value = entry.getValue();
        if (key.zzoy() == zzul.MESSAGE && !key.zzoz() && !key.zzpa()) {
            if (value instanceof zzrn) {
                return zzqj.zzb(entry.getKey().zzc(), (zzrn) value);
            }
            return zzqj.zzd(entry.getKey().zzc(), (zzsk) value);
        }
        return zzb((zzqv<?>) key, value);
    }

    static int zza(zzug zzugVar, int i, Object obj) {
        int iZzbb = zzqj.zzbb(i);
        if (zzugVar == zzug.zzbfy) {
            zzre.zzf((zzsk) obj);
            iZzbb <<= 1;
        }
        return iZzbb + zzb(zzugVar, obj);
    }

    private static int zzb(zzug zzugVar, Object obj) {
        switch (zzqu.zzaws[zzugVar.ordinal()]) {
            case 1:
                return zzqj.zzc(((Double) obj).doubleValue());
            case 2:
                return zzqj.zzb(((Float) obj).floatValue());
            case 3:
                return zzqj.zzs(((Long) obj).longValue());
            case 4:
                return zzqj.zzt(((Long) obj).longValue());
            case 5:
                return zzqj.zzbc(((Integer) obj).intValue());
            case 6:
                return zzqj.zzv(((Long) obj).longValue());
            case 7:
                return zzqj.zzbf(((Integer) obj).intValue());
            case 8:
                return zzqj.zzj(((Boolean) obj).booleanValue());
            case 9:
                return zzqj.zzd((zzsk) obj);
            case 10:
                if (obj instanceof zzrn) {
                    return zzqj.zza((zzrn) obj);
                }
                return zzqj.zzc((zzsk) obj);
            case 11:
                if (obj instanceof zzps) {
                    return zzqj.zzb((zzps) obj);
                }
                return zzqj.zzda((String) obj);
            case 12:
                if (obj instanceof zzps) {
                    return zzqj.zzb((zzps) obj);
                }
                return zzqj.zzh((byte[]) obj);
            case 13:
                return zzqj.zzbd(((Integer) obj).intValue());
            case 14:
                return zzqj.zzbg(((Integer) obj).intValue());
            case 15:
                return zzqj.zzw(((Long) obj).longValue());
            case 16:
                return zzqj.zzbe(((Integer) obj).intValue());
            case 17:
                return zzqj.zzu(((Long) obj).longValue());
            case 18:
                if (obj instanceof zzrf) {
                    return zzqj.zzbh(((zzrf) obj).zzc());
                }
                return zzqj.zzbh(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static int zzb(zzqv<?> zzqvVar, Object obj) {
        zzug zzugVarZzox = zzqvVar.zzox();
        int iZzc = zzqvVar.zzc();
        if (zzqvVar.zzoz()) {
            int iZza = 0;
            if (zzqvVar.zzpa()) {
                Iterator it2 = ((List) obj).iterator();
                while (it2.hasNext()) {
                    iZza += zzb(zzugVarZzox, it2.next());
                }
                return zzqj.zzbb(iZzc) + iZza + zzqj.zzbj(iZza);
            }
            Iterator it3 = ((List) obj).iterator();
            while (it3.hasNext()) {
                iZza += zza(zzugVarZzox, iZzc, it3.next());
            }
            return iZza;
        }
        return zza(zzugVarZzox, iZzc, obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzqt zzqtVar = new zzqt();
        for (int i = 0; i < this.zzaxo.zzra(); i++) {
            Map.Entry<K, Object> entryZzbv = this.zzaxo.zzbv(i);
            zzqtVar.zza((zzqt) entryZzbv.getKey(), entryZzbv.getValue());
        }
        Iterator it2 = this.zzaxo.zzrb().iterator();
        while (it2.hasNext()) {
            Map.Entry entry = (Map.Entry) it2.next();
            zzqtVar.zza((zzqt) entry.getKey(), entry.getValue());
        }
        zzqtVar.zzaxp = this.zzaxp;
        return zzqtVar;
    }
}
