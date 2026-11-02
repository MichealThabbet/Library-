package com.google.android.gms.internal.gtm;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.wrappers.Wrappers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
final class zzbb extends zzan {
    private boolean started;
    private final zzay zzxp;
    private final zzck zzxq;
    private final zzcj zzxr;
    private final zzat zzxs;
    private long zzxt;
    private final zzbs zzxu;
    private final zzbs zzxv;
    private final zzcv zzxw;
    private long zzxx;
    private boolean zzxy;

    protected zzbb(zzap zzapVar, zzar zzarVar) {
        super(zzapVar);
        Preconditions.checkNotNull(zzarVar);
        this.zzxt = Long.MIN_VALUE;
        this.zzxr = new zzcj(zzapVar);
        this.zzxp = new zzay(zzapVar);
        this.zzxq = new zzck(zzapVar);
        this.zzxs = new zzat(zzapVar);
        this.zzxw = new zzcv(zzcn());
        this.zzxu = new zzbc(this, zzapVar);
        this.zzxv = new zzbd(this, zzapVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzan
    protected final void zzaw() {
        this.zzxp.zzag();
        this.zzxq.zzag();
        this.zzxs.zzag();
    }

    final void start() {
        zzdb();
        Preconditions.checkState(!this.started, "Analytics backend already started");
        this.started = true;
        zzcq().zza(new zzbe(this));
    }

    private final boolean zzx(String str) {
        return Wrappers.packageManager(getContext()).checkCallingOrSelfPermission(str) == 0;
    }

    protected final void zzdw() {
        zzdb();
        com.google.android.gms.analytics.zzk.zzav();
        Context context = zzcm().getContext();
        if (!zzcp.zza(context)) {
            zzt("AnalyticsReceiver is not registered or is disabled. Register the receiver for reliable dispatching on non-Google Play devices. See http://goo.gl/8Rd3yj for instructions.");
        } else if (!zzcq.zze(context)) {
            zzu("AnalyticsService is not registered or is disabled. Analytics service at risk of not starting. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!CampaignTrackingReceiver.zza(context)) {
            zzt("CampaignTrackingReceiver is not registered, not exported or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.");
        }
        zzcv().zzfv();
        if (!zzx("android.permission.ACCESS_NETWORK_STATE")) {
            zzu("Missing required android.permission.ACCESS_NETWORK_STATE. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            zzeg();
        }
        if (!zzx("android.permission.INTERNET")) {
            zzu("Missing required android.permission.INTERNET. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            zzeg();
        }
        if (zzcq.zze(getContext())) {
            zzq("AnalyticsService registered in the app manifest and enabled");
        } else {
            zzt("AnalyticsService not registered in the app manifest. Hits might not be delivered reliably. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!this.zzxy && !this.zzxp.isEmpty()) {
            zzdz();
        }
        zzec();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzdx() {
        zzb((zzbw) new zzbf(this));
    }

    final void zzcl() {
        com.google.android.gms.analytics.zzk.zzav();
        this.zzxx = zzcn().currentTimeMillis();
    }

    protected final void onServiceConnected() {
        com.google.android.gms.analytics.zzk.zzav();
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        if (!zzbq.zzen()) {
            zzt("Service client disabled. Can't dispatch local hits to device AnalyticsService");
        }
        if (!this.zzxs.isConnected()) {
            zzq("Service not connected");
            return;
        }
        if (this.zzxp.isEmpty()) {
            return;
        }
        zzq("Dispatching local hits to device AnalyticsService");
        while (true) {
            try {
                List<zzcd> listZzd = this.zzxp.zzd(zzbq.zzer());
                if (listZzd.isEmpty()) {
                    zzec();
                    return;
                }
                while (!listZzd.isEmpty()) {
                    zzcd zzcdVar = listZzd.get(0);
                    if (!this.zzxs.zzb(zzcdVar)) {
                        zzec();
                        return;
                    }
                    listZzd.remove(zzcdVar);
                    try {
                        this.zzxp.zze(zzcdVar.zzfg());
                    } catch (SQLiteException e) {
                        zze("Failed to remove hit that was send for delivery", e);
                        zzee();
                        return;
                    }
                }
            } catch (SQLiteException e2) {
                zze("Failed to read hits from store", e2);
                zzee();
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzdy() {
        try {
            this.zzxp.zzdr();
            zzec();
        } catch (SQLiteException e) {
            zzd("Failed to delete stale hits", e);
        }
        this.zzxv.zzh(86400000L);
    }

    protected final void zzb(zzas zzasVar) {
        com.google.android.gms.analytics.zzk.zzav();
        zzb("Sending first hit to property", zzasVar.zzdj());
        if (zzcv().zzfw().zzj(zzbq.zzex())) {
            return;
        }
        String strZzfz = zzcv().zzfz();
        if (TextUtils.isEmpty(strZzfz)) {
            return;
        }
        zzr zzrVarZza = zzcz.zza(zzco(), strZzfz);
        zzb("Found relevant installation campaign", zzrVarZza);
        zza(zzasVar, zzrVarZza);
    }

    public final void zzg(long j) {
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        if (j < 0) {
            j = 0;
        }
        this.zzxt = j;
        zzec();
    }

    private final void zzdz() {
        if (this.zzxy || !zzbq.zzen() || this.zzxs.isConnected()) {
            return;
        }
        if (this.zzxw.zzj(zzby.zzaan.get().longValue())) {
            this.zzxw.start();
            zzq("Connecting to service");
            if (this.zzxs.connect()) {
                zzq("Connected to service");
                this.zzxw.clear();
                onServiceConnected();
            }
        }
    }

    public final long zza(zzas zzasVar, boolean z) {
        Preconditions.checkNotNull(zzasVar);
        zzdb();
        com.google.android.gms.analytics.zzk.zzav();
        try {
            try {
                this.zzxp.beginTransaction();
                zzay zzayVar = this.zzxp;
                long jZzdi = zzasVar.zzdi();
                String strZzbt = zzasVar.zzbt();
                Preconditions.checkNotEmpty(strZzbt);
                zzayVar.zzdb();
                com.google.android.gms.analytics.zzk.zzav();
                int iDelete = zzayVar.getWritableDatabase().delete("properties", "app_uid=? AND cid<>?", new String[]{String.valueOf(jZzdi), strZzbt});
                if (iDelete > 0) {
                    zzayVar.zza("Deleted property records", Integer.valueOf(iDelete));
                }
                long jZza = this.zzxp.zza(zzasVar.zzdi(), zzasVar.zzbt(), zzasVar.zzdj());
                zzasVar.zzb(1 + jZza);
                zzay zzayVar2 = this.zzxp;
                Preconditions.checkNotNull(zzasVar);
                zzayVar2.zzdb();
                com.google.android.gms.analytics.zzk.zzav();
                SQLiteDatabase writableDatabase = zzayVar2.getWritableDatabase();
                Map<String, String> mapZzdm = zzasVar.zzdm();
                Preconditions.checkNotNull(mapZzdm);
                Uri.Builder builder = new Uri.Builder();
                for (Map.Entry<String, String> entry : mapZzdm.entrySet()) {
                    builder.appendQueryParameter(entry.getKey(), entry.getValue());
                }
                String encodedQuery = builder.build().getEncodedQuery();
                if (encodedQuery == null) {
                    encodedQuery = "";
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("app_uid", Long.valueOf(zzasVar.zzdi()));
                contentValues.put("cid", zzasVar.zzbt());
                contentValues.put("tid", zzasVar.zzdj());
                contentValues.put("adid", Integer.valueOf(zzasVar.zzdk() ? 1 : 0));
                contentValues.put("hits_count", Long.valueOf(zzasVar.zzdl()));
                contentValues.put(NativeProtocol.WEB_DIALOG_PARAMS, encodedQuery);
                try {
                    if (writableDatabase.insertWithOnConflict("properties", null, contentValues, 5) == -1) {
                        zzayVar2.zzu("Failed to insert/update a property (got -1)");
                    }
                } catch (SQLiteException e) {
                    zzayVar2.zze("Error storing a property", e);
                }
                this.zzxp.setTransactionSuccessful();
                try {
                    this.zzxp.endTransaction();
                } catch (SQLiteException e2) {
                    zze("Failed to end transaction", e2);
                }
                return jZza;
            } catch (SQLiteException e3) {
                zze("Failed to update Analytics property", e3);
                try {
                    this.zzxp.endTransaction();
                } catch (SQLiteException e4) {
                    zze("Failed to end transaction", e4);
                }
                return -1L;
            }
        } finally {
        }
    }

    public final void zza(zzcd zzcdVar) {
        Pair<String, Long> pairZzgc;
        Preconditions.checkNotNull(zzcdVar);
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        if (this.zzxy) {
            zzr("Hit delivery not possible. Missing network permissions. See http://goo.gl/8Rd3yj for instructions");
        } else {
            zza("Delivering hit", zzcdVar);
        }
        if (TextUtils.isEmpty(zzcdVar.zzfl()) && (pairZzgc = zzcv().zzga().zzgc()) != null) {
            Long l = (Long) pairZzgc.second;
            String str = (String) pairZzgc.first;
            String strValueOf = String.valueOf(l);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 1 + String.valueOf(str).length());
            sb.append(strValueOf);
            sb.append(":");
            sb.append(str);
            String string = sb.toString();
            HashMap map = new HashMap(zzcdVar.zzdm());
            map.put("_m", string);
            zzcdVar = new zzcd(this, map, zzcdVar.zzfh(), zzcdVar.zzfj(), zzcdVar.zzfg(), zzcdVar.zzff(), zzcdVar.zzfi());
        }
        zzdz();
        if (this.zzxs.zzb(zzcdVar)) {
            zzr("Hit sent to the device AnalyticsService for delivery");
            return;
        }
        try {
            this.zzxp.zzc(zzcdVar);
            zzec();
        } catch (SQLiteException e) {
            zze("Delivery failed to save hit to a database", e);
            zzco().zza(zzcdVar, "deliver: failed to insert hit to database");
        }
    }

    public final void zzch() {
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        zzq("Delete all hits from local store");
        try {
            zzay zzayVar = this.zzxp;
            com.google.android.gms.analytics.zzk.zzav();
            zzayVar.zzdb();
            zzayVar.getWritableDatabase().delete("hits2", null, null);
            zzay zzayVar2 = this.zzxp;
            com.google.android.gms.analytics.zzk.zzav();
            zzayVar2.zzdb();
            zzayVar2.getWritableDatabase().delete("properties", null, null);
            zzec();
        } catch (SQLiteException e) {
            zzd("Failed to delete hits from store", e);
        }
        zzdz();
        if (this.zzxs.zzdn()) {
            zzq("Device service unavailable. Can't clear hits stored on the device service.");
        }
    }

    private final boolean zzea() {
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        zzq("Dispatching a batch of local hits");
        boolean z = !this.zzxs.isConnected();
        boolean z2 = !this.zzxq.zzfr();
        if (z && z2) {
            zzq("No network or service available. Will retry later");
            return false;
        }
        long jMax = Math.max(zzbq.zzer(), zzbq.zzes());
        ArrayList arrayList = new ArrayList();
        long jMax2 = 0;
        while (true) {
            try {
                try {
                    this.zzxp.beginTransaction();
                    arrayList.clear();
                    try {
                        List<zzcd> listZzd = this.zzxp.zzd(jMax);
                        if (listZzd.isEmpty()) {
                            zzq("Store is empty, nothing to dispatch");
                            zzee();
                            try {
                                this.zzxp.setTransactionSuccessful();
                                this.zzxp.endTransaction();
                                return false;
                            } catch (SQLiteException e) {
                                zze("Failed to commit local dispatch transaction", e);
                                zzee();
                                return false;
                            }
                        }
                        zza("Hits loaded from store. count", Integer.valueOf(listZzd.size()));
                        Iterator<zzcd> it2 = listZzd.iterator();
                        while (it2.hasNext()) {
                            if (it2.next().zzfg() == jMax2) {
                                zzd("Database contains successfully uploaded hit", Long.valueOf(jMax2), Integer.valueOf(listZzd.size()));
                                zzee();
                                try {
                                    this.zzxp.setTransactionSuccessful();
                                    this.zzxp.endTransaction();
                                    return false;
                                } catch (SQLiteException e2) {
                                    zze("Failed to commit local dispatch transaction", e2);
                                    zzee();
                                    return false;
                                }
                            }
                        }
                        if (this.zzxs.isConnected()) {
                            zzq("Service connected, sending hits to the service");
                            while (!listZzd.isEmpty()) {
                                zzcd zzcdVar = listZzd.get(0);
                                if (!this.zzxs.zzb(zzcdVar)) {
                                    break;
                                }
                                jMax2 = Math.max(jMax2, zzcdVar.zzfg());
                                listZzd.remove(zzcdVar);
                                zzb("Hit sent do device AnalyticsService for delivery", zzcdVar);
                                try {
                                    this.zzxp.zze(zzcdVar.zzfg());
                                    arrayList.add(Long.valueOf(zzcdVar.zzfg()));
                                } catch (SQLiteException e3) {
                                    zze("Failed to remove hit that was send for delivery", e3);
                                    zzee();
                                    try {
                                        this.zzxp.setTransactionSuccessful();
                                        this.zzxp.endTransaction();
                                        return false;
                                    } catch (SQLiteException e4) {
                                        zze("Failed to commit local dispatch transaction", e4);
                                        zzee();
                                        return false;
                                    }
                                }
                            }
                        }
                        if (this.zzxq.zzfr()) {
                            List<Long> listZzb = this.zzxq.zzb(listZzd);
                            Iterator<Long> it3 = listZzb.iterator();
                            while (it3.hasNext()) {
                                jMax2 = Math.max(jMax2, it3.next().longValue());
                            }
                            try {
                                this.zzxp.zza(listZzb);
                                arrayList.addAll(listZzb);
                            } catch (SQLiteException e5) {
                                zze("Failed to remove successfully uploaded hits", e5);
                                zzee();
                                try {
                                    this.zzxp.setTransactionSuccessful();
                                    this.zzxp.endTransaction();
                                    return false;
                                } catch (SQLiteException e6) {
                                    zze("Failed to commit local dispatch transaction", e6);
                                    zzee();
                                    return false;
                                }
                            }
                        }
                        if (arrayList.isEmpty()) {
                            try {
                                this.zzxp.setTransactionSuccessful();
                                this.zzxp.endTransaction();
                                return false;
                            } catch (SQLiteException e7) {
                                zze("Failed to commit local dispatch transaction", e7);
                                zzee();
                                return false;
                            }
                        }
                        try {
                            this.zzxp.setTransactionSuccessful();
                            this.zzxp.endTransaction();
                        } catch (SQLiteException e8) {
                            zze("Failed to commit local dispatch transaction", e8);
                            zzee();
                            return false;
                        }
                    } catch (SQLiteException e9) {
                        zzd("Failed to read hits from persisted store", e9);
                        zzee();
                        try {
                            this.zzxp.setTransactionSuccessful();
                            this.zzxp.endTransaction();
                            return false;
                        } catch (SQLiteException e10) {
                            zze("Failed to commit local dispatch transaction", e10);
                            zzee();
                            return false;
                        }
                    }
                } catch (Throwable th) {
                    this.zzxp.setTransactionSuccessful();
                    this.zzxp.endTransaction();
                    throw th;
                }
                this.zzxp.setTransactionSuccessful();
                this.zzxp.endTransaction();
                throw th;
            } catch (SQLiteException e11) {
                zze("Failed to commit local dispatch transaction", e11);
                zzee();
                return false;
            }
        }
    }

    public final void zzb(zzbw zzbwVar) {
        long j = this.zzxx;
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        long jZzfx = zzcv().zzfx();
        zzb("Dispatching local hits. Elapsed time since last dispatch (ms)", Long.valueOf(jZzfx != 0 ? Math.abs(zzcn().currentTimeMillis() - jZzfx) : -1L));
        zzdz();
        try {
            zzea();
            zzcv().zzfy();
            zzec();
            if (zzbwVar != null) {
                zzbwVar.zza(null);
            }
            if (this.zzxx != j) {
                this.zzxr.zzfq();
            }
        } catch (Exception e) {
            zze("Local dispatch failed", e);
            zzcv().zzfy();
            zzec();
            if (zzbwVar != null) {
                zzbwVar.zza(e);
            }
        }
    }

    public final void zzeb() {
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        zzr("Sync dispatching local hits");
        long j = this.zzxx;
        zzdz();
        try {
            zzea();
            zzcv().zzfy();
            zzec();
            if (this.zzxx != j) {
                this.zzxr.zzfq();
            }
        } catch (Exception e) {
            zze("Sync local dispatch failed", e);
            zzec();
        }
    }

    private final long zzds() {
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        try {
            return this.zzxp.zzds();
        } catch (SQLiteException e) {
            zze("Failed to get min/max hit times from local store", e);
            return 0L;
        }
    }

    public final void zzec() {
        long jMin;
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        boolean zIsConnected = true;
        if (!(!this.zzxy && zzef() > 0)) {
            this.zzxr.unregister();
            zzee();
            return;
        }
        if (this.zzxp.isEmpty()) {
            this.zzxr.unregister();
            zzee();
            return;
        }
        if (!zzby.zzaai.get().booleanValue()) {
            this.zzxr.zzfo();
            zIsConnected = this.zzxr.isConnected();
        }
        if (zIsConnected) {
            zzed();
            long jZzef = zzef();
            long jZzfx = zzcv().zzfx();
            if (jZzfx != 0) {
                jMin = jZzef - Math.abs(zzcn().currentTimeMillis() - jZzfx);
                if (jMin <= 0) {
                    jMin = Math.min(zzbq.zzep(), jZzef);
                }
            } else {
                jMin = Math.min(zzbq.zzep(), jZzef);
            }
            zza("Dispatch scheduled (ms)", Long.valueOf(jMin));
            if (this.zzxu.zzez()) {
                this.zzxu.zzi(Math.max(1L, jMin + this.zzxu.zzey()));
                return;
            } else {
                this.zzxu.zzh(jMin);
                return;
            }
        }
        zzee();
        zzed();
    }

    private final void zzed() {
        zzbv zzbvVarZzct = zzct();
        if (zzbvVarZzct.zzfc() && !zzbvVarZzct.zzez()) {
            long jZzds = zzds();
            if (jZzds == 0 || Math.abs(zzcn().currentTimeMillis() - jZzds) > zzby.zzzm.get().longValue()) {
                return;
            }
            zza("Dispatch alarm scheduled (ms)", Long.valueOf(zzbq.zzeq()));
            zzbvVarZzct.zzfd();
        }
    }

    private final void zzee() {
        if (this.zzxu.zzez()) {
            zzq("All hits dispatched or no network/service. Going to power save mode");
        }
        this.zzxu.cancel();
        zzbv zzbvVarZzct = zzct();
        if (zzbvVarZzct.zzez()) {
            zzbvVarZzct.cancel();
        }
    }

    private final long zzef() {
        long j = this.zzxt;
        if (j != Long.MIN_VALUE) {
            return j;
        }
        long jLongValue = zzby.zzzh.get().longValue();
        zzda zzdaVarZzcu = zzcu();
        zzdaVarZzcu.zzdb();
        if (!zzdaVarZzcu.zzacv) {
            return jLongValue;
        }
        zzcu().zzdb();
        return r0.zzaax * 1000;
    }

    public final void zzy(String str) {
        Preconditions.checkNotEmpty(str);
        com.google.android.gms.analytics.zzk.zzav();
        zzr zzrVarZza = zzcz.zza(zzco(), str);
        if (zzrVarZza == null) {
            zzd("Parsing failed. Ignoring invalid campaign data", str);
            return;
        }
        String strZzfz = zzcv().zzfz();
        if (str.equals(strZzfz)) {
            zzt("Ignoring duplicate install campaign");
            return;
        }
        if (!TextUtils.isEmpty(strZzfz)) {
            zzd("Ignoring multiple install campaigns. original, new", strZzfz, str);
            return;
        }
        zzcv().zzad(str);
        if (zzcv().zzfw().zzj(zzbq.zzex())) {
            zzd("Campaign received too late, ignoring", zzrVarZza);
            return;
        }
        zzb("Received installation campaign", zzrVarZza);
        Iterator<zzas> it2 = this.zzxp.zzf(0L).iterator();
        while (it2.hasNext()) {
            zza(it2.next(), zzrVarZza);
        }
    }

    private final void zza(zzas zzasVar, zzr zzrVar) {
        Preconditions.checkNotNull(zzasVar);
        Preconditions.checkNotNull(zzrVar);
        com.google.android.gms.analytics.zza zzaVar = new com.google.android.gms.analytics.zza(zzcm());
        zzaVar.zza(zzasVar.zzdj());
        zzaVar.enableAdvertisingIdCollection(zzasVar.zzdk());
        com.google.android.gms.analytics.zzg zzgVarZzac = zzaVar.zzac();
        zzz zzzVar = (zzz) zzgVarZzac.zzb(zzz.class);
        zzzVar.zzl("data");
        zzzVar.zzb(true);
        zzgVarZzac.zza(zzrVar);
        zzu zzuVar = (zzu) zzgVarZzac.zzb(zzu.class);
        zzq zzqVar = (zzq) zzgVarZzac.zzb(zzq.class);
        for (Map.Entry<String, String> entry : zzasVar.zzdm().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if ("an".equals(key)) {
                zzqVar.setAppName(value);
            } else if ("av".equals(key)) {
                zzqVar.setAppVersion(value);
            } else if ("aid".equals(key)) {
                zzqVar.setAppId(value);
            } else if ("aiid".equals(key)) {
                zzqVar.setAppInstallerId(value);
            } else if ("uid".equals(key)) {
                zzzVar.setUserId(value);
            } else {
                zzuVar.set(key, value);
            }
        }
        zzb("Sending installation campaign to", zzasVar.zzdj(), zzrVar);
        zzgVarZzac.zza(zzcv().zzfv());
        zzgVarZzac.zzam();
    }

    private final void zzeg() {
        zzdb();
        com.google.android.gms.analytics.zzk.zzav();
        this.zzxy = true;
        this.zzxs.disconnect();
        zzec();
    }
}
