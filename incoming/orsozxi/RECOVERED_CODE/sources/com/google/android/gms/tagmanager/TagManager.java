package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.api.PendingResult;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes.dex */
public class TagManager {
    private static TagManager zzalg;
    private final DataLayer zzaed;
    private final zzal zzajg;
    private final zza zzald;
    private final zzfm zzale;
    private final ConcurrentMap<String, zzv> zzalf;
    private final Context zzrm;

    public interface zza {
        zzy zza(Context context, TagManager tagManager, Looper looper, String str, int i, zzal zzalVar);
    }

    private TagManager(Context context, zza zzaVar, DataLayer dataLayer, zzfm zzfmVar) {
        if (context == null) {
            throw new NullPointerException("context cannot be null");
        }
        this.zzrm = context.getApplicationContext();
        this.zzale = zzfmVar;
        this.zzald = zzaVar;
        this.zzalf = new ConcurrentHashMap();
        this.zzaed = dataLayer;
        dataLayer.zza(new zzga(this));
        this.zzaed.zza(new zzg(this.zzrm));
        this.zzajg = new zzal();
        this.zzrm.registerComponentCallbacks(new zzgc(this));
        com.google.android.gms.tagmanager.zza.zzf(this.zzrm);
    }

    public static TagManager getInstance(Context context) {
        TagManager tagManager;
        synchronized (TagManager.class) {
            if (zzalg == null) {
                if (context == null) {
                    zzdi.zzav("TagManager.getInstance requires non-null context.");
                    throw new NullPointerException();
                }
                zzalg = new TagManager(context, new zzgb(), new DataLayer(new zzat(context)), zzfn.zzjq());
            }
            tagManager = zzalg;
        }
        return tagManager;
    }

    public DataLayer getDataLayer() {
        return this.zzaed;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String str, int i) {
        zzy zzyVarZza = this.zzald.zza(this.zzrm, this, null, str, i, this.zzajg);
        zzyVarZza.zzhf();
        return zzyVarZza;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String str, int i, Handler handler) {
        zzy zzyVarZza = this.zzald.zza(this.zzrm, this, handler.getLooper(), str, i, this.zzajg);
        zzyVarZza.zzhf();
        return zzyVarZza;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String str, int i) {
        zzy zzyVarZza = this.zzald.zza(this.zzrm, this, null, str, i, this.zzajg);
        zzyVarZza.zzhg();
        return zzyVarZza;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String str, int i, Handler handler) {
        zzy zzyVarZza = this.zzald.zza(this.zzrm, this, handler.getLooper(), str, i, this.zzajg);
        zzyVarZza.zzhg();
        return zzyVarZza;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String str, int i) {
        zzy zzyVarZza = this.zzald.zza(this.zzrm, this, null, str, i, this.zzajg);
        zzyVarZza.zzhh();
        return zzyVarZza;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String str, int i, Handler handler) {
        zzy zzyVarZza = this.zzald.zza(this.zzrm, this, handler.getLooper(), str, i, this.zzajg);
        zzyVarZza.zzhh();
        return zzyVarZza;
    }

    public void dispatch() {
        this.zzale.dispatch();
    }

    public void setVerboseLoggingEnabled(boolean z) {
        zzdi.setLogLevel(z ? 2 : 5);
    }

    final synchronized boolean zza(Uri uri) {
        zzeh zzehVarZziy = zzeh.zziy();
        if (!zzehVarZziy.zza(uri)) {
            return false;
        }
        String containerId = zzehVarZziy.getContainerId();
        int i = zzgd.zzali[zzehVarZziy.zziz().ordinal()];
        if (i == 1) {
            zzv zzvVar = this.zzalf.get(containerId);
            if (zzvVar != null) {
                zzvVar.zzao(null);
                zzvVar.refresh();
            }
        } else if (i == 2 || i == 3) {
            for (String str : this.zzalf.keySet()) {
                zzv zzvVar2 = this.zzalf.get(str);
                if (str.equals(containerId)) {
                    zzvVar2.zzao(zzehVarZziy.zzja());
                    zzvVar2.refresh();
                } else if (zzvVar2.zzhc() != null) {
                    zzvVar2.zzao(null);
                    zzvVar2.refresh();
                }
            }
        }
        return true;
    }

    public final int zza(zzv zzvVar) {
        this.zzalf.put(zzvVar.getContainerId(), zzvVar);
        return this.zzalf.size();
    }

    public final boolean zzb(zzv zzvVar) {
        return this.zzalf.remove(zzvVar.getContainerId()) != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzbl(String str) {
        Iterator<zzv> it2 = this.zzalf.values().iterator();
        while (it2.hasNext()) {
            it2.next().zzan(str);
        }
    }
}
