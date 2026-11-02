package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.internal.gtm.zzop;
import com.google.android.gms.internal.gtm.zzoq;
import com.google.android.gms.internal.gtm.zzov;
import com.google.android.gms.tagmanager.zzeh;

/* loaded from: classes.dex */
public final class zzy extends BasePendingResult<ContainerHolder> {
    private final String zzaec;
    private long zzaeh;
    private final Looper zzaek;
    private final TagManager zzaer;
    private final zzaf zzaeu;
    private final zzej zzaev;
    private final int zzaew;
    private final zzai zzaex;
    private zzah zzaey;
    private zzoq zzaez;
    private volatile zzv zzafa;
    private volatile boolean zzafb;
    private com.google.android.gms.internal.gtm.zzk zzafc;
    private String zzafd;
    private zzag zzafe;
    private zzac zzaff;
    private final Context zzrm;
    private final Clock zzsd;

    public zzy(Context context, TagManager tagManager, Looper looper, String str, int i, zzal zzalVar) {
        this(context, tagManager, looper, str, i, new zzex(context, str), new zzes(context, str, zzalVar), new zzoq(context), DefaultClock.getInstance(), new zzdg(1, 5, 900000L, 5000L, "refreshing", DefaultClock.getInstance()), new zzai(context, str));
        this.zzaez.zzcr(zzalVar.zzhq());
    }

    private zzy(Context context, TagManager tagManager, Looper looper, String str, int i, zzah zzahVar, zzag zzagVar, zzoq zzoqVar, Clock clock, zzej zzejVar, zzai zzaiVar) {
        super(looper == null ? Looper.getMainLooper() : looper);
        this.zzrm = context;
        this.zzaer = tagManager;
        this.zzaek = looper == null ? Looper.getMainLooper() : looper;
        this.zzaec = str;
        this.zzaew = i;
        this.zzaey = zzahVar;
        this.zzafe = zzagVar;
        this.zzaez = zzoqVar;
        this.zzaeu = new zzaf(this, null);
        this.zzafc = new com.google.android.gms.internal.gtm.zzk();
        this.zzsd = clock;
        this.zzaev = zzejVar;
        this.zzaex = zzaiVar;
        if (zzhi()) {
            zzao(zzeh.zziy().zzja());
        }
    }

    public final void zzhf() {
        zzov zzovVarZzt = this.zzaey.zzt(this.zzaew);
        if (zzovVarZzt != null) {
            setResult(new zzv(this.zzaer, this.zzaek, new Container(this.zzrm, this.zzaer.getDataLayer(), this.zzaec, 0L, zzovVarZzt), new zzaa(this)));
        } else {
            zzdi.zzav("Default was requested, but no default container was found");
            setResult(createFailedResult(new Status(10, "Default was requested, but no default container was found", null)));
        }
        this.zzafe = null;
        this.zzaey = null;
    }

    public final void zzhg() {
        zzd(false);
    }

    public final void zzhh() {
        zzd(true);
    }

    private final void zzd(boolean z) {
        zzz zzzVar = null;
        this.zzaey.zza(new zzad(this, zzzVar));
        this.zzafe.zza(new zzae(this, zzzVar));
        zzov zzovVarZzt = this.zzaey.zzt(this.zzaew);
        if (zzovVarZzt != null) {
            TagManager tagManager = this.zzaer;
            this.zzafa = new zzv(tagManager, this.zzaek, new Container(this.zzrm, tagManager.getDataLayer(), this.zzaec, 0L, zzovVarZzt), this.zzaeu);
        }
        this.zzaff = new zzab(this, z);
        if (zzhi()) {
            this.zzafe.zza(0L, "");
        } else {
            this.zzaey.zzhk();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final synchronized void zza(com.google.android.gms.internal.gtm.zzk zzkVar, long j, boolean z) {
        if (isReady() && this.zzafa == null) {
            return;
        }
        this.zzafc = zzkVar;
        this.zzaeh = j;
        long jZzhl = this.zzaex.zzhl();
        zzk(Math.max(0L, Math.min(jZzhl, (this.zzaeh + jZzhl) - this.zzsd.currentTimeMillis())));
        Container container = new Container(this.zzrm, this.zzaer.getDataLayer(), this.zzaec, j, zzkVar);
        if (this.zzafa == null) {
            this.zzafa = new zzv(this.zzaer, this.zzaek, container, this.zzaeu);
        } else {
            this.zzafa.zza(container);
        }
        if (!isReady() && this.zzaff.zzb(container)) {
            setResult(this.zzafa);
        }
    }

    final synchronized void zzao(String str) {
        this.zzafd = str;
        if (this.zzafe != null) {
            this.zzafe.zzap(str);
        }
    }

    final synchronized String zzhc() {
        return this.zzafd;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final synchronized void zzk(long j) {
        if (this.zzafe == null) {
            zzdi.zzac("Refresh requested, but no network load scheduler.");
        } else {
            this.zzafe.zza(j, this.zzafc.zzql);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    /* renamed from: zza, reason: merged with bridge method [inline-methods] */
    public final ContainerHolder createFailedResult(Status status) {
        if (this.zzafa != null) {
            return this.zzafa;
        }
        if (status == Status.RESULT_TIMEOUT) {
            zzdi.zzav("timer expired: setting result to failure");
        }
        return new zzv(status);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean zzhi() {
        zzeh zzehVarZziy = zzeh.zziy();
        return (zzehVarZziy.zziz() == zzeh.zza.CONTAINER || zzehVarZziy.zziz() == zzeh.zza.CONTAINER_DEBUG) && this.zzaec.equals(zzehVarZziy.getContainerId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final synchronized void zza(com.google.android.gms.internal.gtm.zzk zzkVar) {
        if (this.zzaey != null) {
            zzop zzopVar = new zzop();
            zzopVar.zzaux = this.zzaeh;
            zzopVar.zzqk = new com.google.android.gms.internal.gtm.zzi();
            zzopVar.zzauy = zzkVar;
            this.zzaey.zza(zzopVar);
        }
    }
}
