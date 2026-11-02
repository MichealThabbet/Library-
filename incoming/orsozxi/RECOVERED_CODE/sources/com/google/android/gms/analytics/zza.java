package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.gtm.zzad;
import com.google.android.gms.internal.gtm.zzap;
import com.google.android.gms.internal.gtm.zzz;
import java.util.ListIterator;

/* loaded from: classes.dex */
public class zza extends zzj<zza> {
    private final zzap zzrb;
    private boolean zzrc;

    public zza(zzap zzapVar) {
        super(zzapVar.zzcq(), zzapVar.zzcn());
        this.zzrb = zzapVar;
    }

    final zzap zzab() {
        return this.zzrb;
    }

    @Override // com.google.android.gms.analytics.zzj
    public final zzg zzac() {
        zzg zzgVarZzai = this.zzso.zzai();
        zzgVarZzai.zza(this.zzrb.zzcy().zzdv());
        zzgVarZzai.zza(this.zzrb.zzcz().zzfa());
        zzd(zzgVarZzai);
        return zzgVarZzai;
    }

    @Override // com.google.android.gms.analytics.zzj
    protected final void zza(zzg zzgVar) {
        zzz zzzVar = (zzz) zzgVar.zzb(zzz.class);
        if (TextUtils.isEmpty(zzzVar.zzbt())) {
            zzzVar.setClientId(this.zzrb.zzdh().zzeh());
        }
        if (this.zzrc && TextUtils.isEmpty(zzzVar.zzbv())) {
            zzad zzadVarZzdg = this.zzrb.zzdg();
            zzzVar.zzm(zzadVarZzdg.zzcd());
            zzzVar.zza(zzadVarZzdg.zzbw());
        }
    }

    public final void enableAdvertisingIdCollection(boolean z) {
        this.zzrc = z;
    }

    public final void zza(String str) {
        Preconditions.checkNotEmpty(str);
        Uri uriZzb = zzb.zzb(str);
        ListIterator<zzo> listIterator = this.zzso.zzak().listIterator();
        while (listIterator.hasNext()) {
            if (uriZzb.equals(listIterator.next().zzae())) {
                listIterator.remove();
            }
        }
        this.zzso.zzak().add(new zzb(this.zzrb, str));
    }
}
