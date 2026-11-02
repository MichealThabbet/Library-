package com.google.android.gms.internal.gtm;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
final class zzsp<T> implements zzsz<T> {
    private final zzsk zzbdc;
    private final boolean zzbdd;
    private final zztr<?, ?> zzbdm;
    private final zzqq<?> zzbdn;

    private zzsp(zztr<?, ?> zztrVar, zzqq<?> zzqqVar, zzsk zzskVar) {
        this.zzbdm = zztrVar;
        this.zzbdd = zzqqVar.zze(zzskVar);
        this.zzbdn = zzqqVar;
        this.zzbdc = zzskVar;
    }

    static <T> zzsp<T> zza(zztr<?, ?> zztrVar, zzqq<?> zzqqVar, zzsk zzskVar) {
        return new zzsp<>(zztrVar, zzqqVar, zzskVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzsz
    public final T newInstance() {
        return (T) this.zzbdc.zzph().zzpl();
    }

    @Override // com.google.android.gms.internal.gtm.zzsz
    public final boolean equals(T t, T t2) {
        if (!this.zzbdm.zzag(t).equals(this.zzbdm.zzag(t2))) {
            return false;
        }
        if (this.zzbdd) {
            return this.zzbdn.zzr(t).equals(this.zzbdn.zzr(t2));
        }
        return true;
    }

    @Override // com.google.android.gms.internal.gtm.zzsz
    public final int hashCode(T t) {
        int iHashCode = this.zzbdm.zzag(t).hashCode();
        return this.zzbdd ? (iHashCode * 53) + this.zzbdn.zzr(t).hashCode() : iHashCode;
    }

    @Override // com.google.android.gms.internal.gtm.zzsz
    public final void zzd(T t, T t2) {
        zztb.zza(this.zzbdm, t, t2);
        if (this.zzbdd) {
            zztb.zza(this.zzbdn, t, t2);
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzsz
    public final void zza(T t, zzum zzumVar) throws IOException {
        Iterator it2 = this.zzbdn.zzr(t).iterator();
        while (it2.hasNext()) {
            Map.Entry entry = (Map.Entry) it2.next();
            zzqv zzqvVar = (zzqv) entry.getKey();
            if (zzqvVar.zzoy() != zzul.MESSAGE || zzqvVar.zzoz() || zzqvVar.zzpa()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            }
            if (entry instanceof zzrp) {
                zzumVar.zza(zzqvVar.zzc(), (Object) ((zzrp) entry).zzpz().zzmv());
            } else {
                zzumVar.zza(zzqvVar.zzc(), entry.getValue());
            }
        }
        zztr<?, ?> zztrVar = this.zzbdm;
        zztrVar.zzc(zztrVar.zzag(t), zzumVar);
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x0085 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:53:? A[LOOP:0: B:45:0x000c->B:53:?, LOOP_END, SYNTHETIC] */
    @Override // com.google.android.gms.internal.gtm.zzsz
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(T r11, com.google.android.gms.internal.gtm.zzsy r12, com.google.android.gms.internal.gtm.zzqp r13) throws java.io.IOException {
        /*
            r10 = this;
            com.google.android.gms.internal.gtm.zztr<?, ?> r0 = r10.zzbdm
            com.google.android.gms.internal.gtm.zzqq<?> r1 = r10.zzbdn
            java.lang.Object r2 = r0.zzah(r11)
            com.google.android.gms.internal.gtm.zzqt r3 = r1.zzs(r11)
        Lc:
            int r4 = r12.zzog()     // Catch: java.lang.Throwable -> L8e
            r5 = 2147483647(0x7fffffff, float:NaN)
            if (r4 != r5) goto L19
            r0.zzg(r11, r2)
            return
        L19:
            int r4 = r12.getTag()     // Catch: java.lang.Throwable -> L8e
            r6 = 11
            if (r4 == r6) goto L3e
            r5 = r4 & 7
            r6 = 2
            if (r5 != r6) goto L39
            com.google.android.gms.internal.gtm.zzsk r5 = r10.zzbdc     // Catch: java.lang.Throwable -> L8e
            int r4 = r4 >>> 3
            java.lang.Object r4 = r1.zza(r13, r5, r4)     // Catch: java.lang.Throwable -> L8e
            if (r4 == 0) goto L34
            r1.zza(r12, r4, r13, r3)     // Catch: java.lang.Throwable -> L8e
            goto L82
        L34:
            boolean r4 = r0.zza(r2, r12)     // Catch: java.lang.Throwable -> L8e
            goto L83
        L39:
            boolean r4 = r12.zzoh()     // Catch: java.lang.Throwable -> L8e
            goto L83
        L3e:
            r4 = 0
            r6 = 0
            r7 = r6
        L41:
            int r8 = r12.zzog()     // Catch: java.lang.Throwable -> L8e
            if (r8 == r5) goto L6f
            int r8 = r12.getTag()     // Catch: java.lang.Throwable -> L8e
            r9 = 16
            if (r8 != r9) goto L5a
            int r4 = r12.zznr()     // Catch: java.lang.Throwable -> L8e
            com.google.android.gms.internal.gtm.zzsk r6 = r10.zzbdc     // Catch: java.lang.Throwable -> L8e
            java.lang.Object r6 = r1.zza(r13, r6, r4)     // Catch: java.lang.Throwable -> L8e
            goto L41
        L5a:
            r9 = 26
            if (r8 != r9) goto L69
            if (r6 == 0) goto L64
            r1.zza(r12, r6, r13, r3)     // Catch: java.lang.Throwable -> L8e
            goto L41
        L64:
            com.google.android.gms.internal.gtm.zzps r7 = r12.zznq()     // Catch: java.lang.Throwable -> L8e
            goto L41
        L69:
            boolean r8 = r12.zzoh()     // Catch: java.lang.Throwable -> L8e
            if (r8 != 0) goto L41
        L6f:
            int r5 = r12.getTag()     // Catch: java.lang.Throwable -> L8e
            r8 = 12
            if (r5 != r8) goto L89
            if (r7 == 0) goto L82
            if (r6 == 0) goto L7f
            r1.zza(r7, r6, r13, r3)     // Catch: java.lang.Throwable -> L8e
            goto L82
        L7f:
            r0.zza(r2, r4, r7)     // Catch: java.lang.Throwable -> L8e
        L82:
            r4 = 1
        L83:
            if (r4 != 0) goto Lc
            r0.zzg(r11, r2)
            return
        L89:
            com.google.android.gms.internal.gtm.zzrk r12 = com.google.android.gms.internal.gtm.zzrk.zzps()     // Catch: java.lang.Throwable -> L8e
            throw r12     // Catch: java.lang.Throwable -> L8e
        L8e:
            r12 = move-exception
            r0.zzg(r11, r2)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzsp.zza(java.lang.Object, com.google.android.gms.internal.gtm.zzsy, com.google.android.gms.internal.gtm.zzqp):void");
    }

    @Override // com.google.android.gms.internal.gtm.zzsz
    public final void zzt(T t) {
        this.zzbdm.zzt(t);
        this.zzbdn.zzt(t);
    }

    @Override // com.google.android.gms.internal.gtm.zzsz
    public final boolean zzae(T t) {
        return this.zzbdn.zzr(t).isInitialized();
    }

    @Override // com.google.android.gms.internal.gtm.zzsz
    public final int zzad(T t) {
        zztr<?, ?> zztrVar = this.zzbdm;
        int iZzai = zztrVar.zzai(zztrVar.zzag(t)) + 0;
        return this.zzbdd ? iZzai + this.zzbdn.zzr(t).zzow() : iZzai;
    }
}
