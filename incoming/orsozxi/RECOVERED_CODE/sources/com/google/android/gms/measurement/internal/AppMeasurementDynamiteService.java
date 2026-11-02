package com.google.android.gms.measurement.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import androidx.collection.ArrayMap;
import com.app.orsozoxi.Alarm.Database.Database;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.internal.measurement.zzod;
import java.util.Map;
import net.lingala.zip4j.util.InternalZipConstants;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

/* compiled from: com.google.android.gms:play-services-measurement-sdk@@19.0.0 */
/* loaded from: classes.dex */
public class AppMeasurementDynamiteService extends com.google.android.gms.internal.measurement.zzcb {
    zzfu zza = null;
    private final Map<Integer, zzgv> zzb = new ArrayMap();

    @EnsuresNonNull({"scion"})
    private final void zzb() {
        if (this.zza == null) {
            throw new IllegalStateException("Attempting to perform action before initialize.");
        }
    }

    private final void zzc(com.google.android.gms.internal.measurement.zzcf zzcfVar, String str) {
        zzb();
        this.zza.zzl().zzad(zzcfVar, str);
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void beginAdUnitExposure(String str, long j) throws IllegalStateException, RemoteException {
        zzb();
        this.zza.zzB().zza(str, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void clearConditionalUserProperty(String str, String str2, Bundle bundle) throws IllegalStateException, RemoteException {
        zzb();
        this.zza.zzk().zzO(str, str2, bundle);
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void clearMeasurementEnabled(long j) throws IllegalStateException, RemoteException {
        zzb();
        this.zza.zzk().zzn(null);
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void endAdUnitExposure(String str, long j) throws IllegalStateException, RemoteException {
        zzb();
        this.zza.zzB().zzb(str, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void generateEventId(com.google.android.gms.internal.measurement.zzcf zzcfVar) throws RemoteException {
        zzb();
        long jZzd = this.zza.zzl().zzd();
        zzb();
        this.zza.zzl().zzae(zzcfVar, jZzd);
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void getAppInstanceId(com.google.android.gms.internal.measurement.zzcf zzcfVar) throws IllegalStateException, RemoteException {
        zzb();
        this.zza.zzav().zzh(new zzh(this, zzcfVar));
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void getCachedAppInstanceId(com.google.android.gms.internal.measurement.zzcf zzcfVar) throws RemoteException {
        zzb();
        zzc(zzcfVar, this.zza.zzk().zzD());
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void getConditionalUserProperties(String str, String str2, com.google.android.gms.internal.measurement.zzcf zzcfVar) throws IllegalStateException, RemoteException {
        zzb();
        this.zza.zzav().zzh(new zzl(this, zzcfVar, str, str2));
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void getCurrentScreenClass(com.google.android.gms.internal.measurement.zzcf zzcfVar) throws RemoteException {
        zzb();
        zzc(zzcfVar, this.zza.zzk().zzS());
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void getCurrentScreenName(com.google.android.gms.internal.measurement.zzcf zzcfVar) throws RemoteException {
        zzb();
        zzc(zzcfVar, this.zza.zzk().zzR());
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void getGmpAppId(com.google.android.gms.internal.measurement.zzcf zzcfVar) throws RemoteException {
        zzb();
        zzc(zzcfVar, this.zza.zzk().zzT());
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void getMaxUserProperties(String str, com.google.android.gms.internal.measurement.zzcf zzcfVar) throws RemoteException {
        zzb();
        this.zza.zzk().zzL(str);
        zzb();
        this.zza.zzl().zzaf(zzcfVar, 25);
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void getTestFlag(com.google.android.gms.internal.measurement.zzcf zzcfVar, int i) throws RemoteException {
        zzb();
        if (i == 0) {
            this.zza.zzl().zzad(zzcfVar, this.zza.zzk().zzj());
            return;
        }
        if (i == 1) {
            this.zza.zzl().zzae(zzcfVar, this.zza.zzk().zzk().longValue());
            return;
        }
        if (i != 2) {
            if (i == 3) {
                this.zza.zzl().zzaf(zzcfVar, this.zza.zzk().zzl().intValue());
                return;
            } else {
                if (i != 4) {
                    return;
                }
                this.zza.zzl().zzah(zzcfVar, this.zza.zzk().zzi().booleanValue());
                return;
            }
        }
        zzku zzkuVarZzl = this.zza.zzl();
        double dDoubleValue = this.zza.zzk().zzm().doubleValue();
        Bundle bundle = new Bundle();
        bundle.putDouble(InternalZipConstants.READ_MODE, dDoubleValue);
        try {
            zzcfVar.zzb(bundle);
        } catch (RemoteException e) {
            zzkuVarZzl.zzs.zzau().zze().zzb("Error returning double value to wrapper", e);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void getUserProperties(String str, String str2, boolean z, com.google.android.gms.internal.measurement.zzcf zzcfVar) throws IllegalStateException, RemoteException {
        zzb();
        this.zza.zzav().zzh(new zzj(this, zzcfVar, str, str2, z));
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void initForTests(Map map) throws RemoteException {
        zzb();
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void initialize(IObjectWrapper iObjectWrapper, com.google.android.gms.internal.measurement.zzcl zzclVar, long j) throws RemoteException {
        zzfu zzfuVar = this.zza;
        if (zzfuVar == null) {
            this.zza = zzfu.zzC((Context) Preconditions.checkNotNull((Context) ObjectWrapper.unwrap(iObjectWrapper)), zzclVar, Long.valueOf(j));
        } else {
            zzfuVar.zzau().zze().zza("Attempting to initialize multiple times");
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void isDataCollectionEnabled(com.google.android.gms.internal.measurement.zzcf zzcfVar) throws IllegalStateException, RemoteException {
        zzb();
        this.zza.zzav().zzh(new zzm(this, zzcfVar));
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void logEvent(String str, String str2, Bundle bundle, boolean z, boolean z2, long j) throws IllegalStateException, RemoteException {
        zzb();
        this.zza.zzk().zzv(str, str2, bundle, z, z2, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void logEventAndBundle(String str, String str2, Bundle bundle, com.google.android.gms.internal.measurement.zzcf zzcfVar, long j) throws IllegalStateException, RemoteException {
        zzb();
        Preconditions.checkNotEmpty(str2);
        (bundle != null ? new Bundle(bundle) : new Bundle()).putString("_o", "app");
        this.zza.zzav().zzh(new zzi(this, zzcfVar, new zzas(str2, new zzaq(bundle), "app", j), str));
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void logHealthData(int i, String str, IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, IObjectWrapper iObjectWrapper3) throws RemoteException {
        zzb();
        this.zza.zzau().zzm(i, true, false, str, iObjectWrapper == null ? null : ObjectWrapper.unwrap(iObjectWrapper), iObjectWrapper2 == null ? null : ObjectWrapper.unwrap(iObjectWrapper2), iObjectWrapper3 != null ? ObjectWrapper.unwrap(iObjectWrapper3) : null);
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void onActivityCreated(IObjectWrapper iObjectWrapper, Bundle bundle, long j) throws RemoteException {
        zzb();
        zzhv zzhvVar = this.zza.zzk().zza;
        if (zzhvVar != null) {
            this.zza.zzk().zzh();
            zzhvVar.onActivityCreated((Activity) ObjectWrapper.unwrap(iObjectWrapper), bundle);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void onActivityDestroyed(IObjectWrapper iObjectWrapper, long j) throws RemoteException {
        zzb();
        zzhv zzhvVar = this.zza.zzk().zza;
        if (zzhvVar != null) {
            this.zza.zzk().zzh();
            zzhvVar.onActivityDestroyed((Activity) ObjectWrapper.unwrap(iObjectWrapper));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void onActivityPaused(IObjectWrapper iObjectWrapper, long j) throws RemoteException {
        zzb();
        zzhv zzhvVar = this.zza.zzk().zza;
        if (zzhvVar != null) {
            this.zza.zzk().zzh();
            zzhvVar.onActivityPaused((Activity) ObjectWrapper.unwrap(iObjectWrapper));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void onActivityResumed(IObjectWrapper iObjectWrapper, long j) throws RemoteException {
        zzb();
        zzhv zzhvVar = this.zza.zzk().zza;
        if (zzhvVar != null) {
            this.zza.zzk().zzh();
            zzhvVar.onActivityResumed((Activity) ObjectWrapper.unwrap(iObjectWrapper));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void onActivitySaveInstanceState(IObjectWrapper iObjectWrapper, com.google.android.gms.internal.measurement.zzcf zzcfVar, long j) throws RemoteException {
        zzb();
        zzhv zzhvVar = this.zza.zzk().zza;
        Bundle bundle = new Bundle();
        if (zzhvVar != null) {
            this.zza.zzk().zzh();
            zzhvVar.onActivitySaveInstanceState((Activity) ObjectWrapper.unwrap(iObjectWrapper), bundle);
        }
        try {
            zzcfVar.zzb(bundle);
        } catch (RemoteException e) {
            this.zza.zzau().zze().zzb("Error returning bundle value to wrapper", e);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void onActivityStarted(IObjectWrapper iObjectWrapper, long j) throws RemoteException {
        zzb();
        if (this.zza.zzk().zza != null) {
            this.zza.zzk().zzh();
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void onActivityStopped(IObjectWrapper iObjectWrapper, long j) throws RemoteException {
        zzb();
        if (this.zza.zzk().zza != null) {
            this.zza.zzk().zzh();
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void performAction(Bundle bundle, com.google.android.gms.internal.measurement.zzcf zzcfVar, long j) throws RemoteException {
        zzb();
        zzcfVar.zzb(null);
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void registerOnMeasurementEventListener(com.google.android.gms.internal.measurement.zzci zzciVar) throws RemoteException {
        zzgv zzoVar;
        zzb();
        synchronized (this.zzb) {
            zzoVar = this.zzb.get(Integer.valueOf(zzciVar.zze()));
            if (zzoVar == null) {
                zzoVar = new zzo(this, zzciVar);
                this.zzb.put(Integer.valueOf(zzciVar.zze()), zzoVar);
            }
        }
        this.zza.zzk().zzJ(zzoVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void resetAnalyticsData(long j) throws IllegalStateException, RemoteException {
        zzb();
        this.zza.zzk().zzF(j);
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void setConditionalUserProperty(Bundle bundle, long j) throws IllegalStateException, RemoteException {
        zzb();
        if (bundle == null) {
            this.zza.zzau().zzb().zza("Conditional user property must not be null");
        } else {
            this.zza.zzk().zzN(bundle, j);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void setConsent(Bundle bundle, long j) throws RemoteException {
        zzb();
        zzhw zzhwVarZzk = this.zza.zzk();
        zzod.zzb();
        if (!zzhwVarZzk.zzs.zzc().zzn(null, zzea.zzaC) || TextUtils.isEmpty(zzhwVarZzk.zzs.zzA().zzj())) {
            zzhwVarZzk.zzo(bundle, 0, j);
        } else {
            zzhwVarZzk.zzs.zzau().zzh().zza("Using developer consent only; google app id found");
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void setConsentThirdParty(Bundle bundle, long j) throws RemoteException {
        zzb();
        this.zza.zzk().zzo(bundle, -20, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void setCurrentScreen(IObjectWrapper iObjectWrapper, String str, String str2, long j) throws IllegalStateException, RemoteException {
        zzb();
        this.zza.zzx().zzk((Activity) ObjectWrapper.unwrap(iObjectWrapper), str, str2);
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void setDataCollectionEnabled(boolean z) throws IllegalStateException, RemoteException {
        zzb();
        zzhw zzhwVarZzk = this.zza.zzk();
        zzhwVarZzk.zzb();
        zzfu zzfuVar = zzhwVarZzk.zzs;
        zzhwVarZzk.zzs.zzav().zzh(new zzgz(zzhwVarZzk, z));
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void setDefaultEventParameters(Bundle bundle) throws IllegalStateException {
        zzb();
        final zzhw zzhwVarZzk = this.zza.zzk();
        final Bundle bundle2 = bundle == null ? null : new Bundle(bundle);
        zzhwVarZzk.zzs.zzav().zzh(new Runnable(zzhwVarZzk, bundle2) { // from class: com.google.android.gms.measurement.internal.zzgx
            private final zzhw zza;
            private final Bundle zzb;

            {
                this.zza = zzhwVarZzk;
                this.zzb = bundle2;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzU(this.zzb);
            }
        });
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void setEventInterceptor(com.google.android.gms.internal.measurement.zzci zzciVar) throws IllegalStateException, RemoteException {
        zzb();
        zzn zznVar = new zzn(this, zzciVar);
        if (this.zza.zzav().zzd()) {
            this.zza.zzk().zzI(zznVar);
        } else {
            this.zza.zzav().zzh(new zzk(this, zznVar));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void setInstanceIdProvider(com.google.android.gms.internal.measurement.zzck zzckVar) throws RemoteException {
        zzb();
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void setMeasurementEnabled(boolean z, long j) throws IllegalStateException, RemoteException {
        zzb();
        this.zza.zzk().zzn(Boolean.valueOf(z));
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void setMinimumSessionDuration(long j) throws RemoteException {
        zzb();
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void setSessionTimeoutDuration(long j) throws IllegalStateException, RemoteException {
        zzb();
        zzhw zzhwVarZzk = this.zza.zzk();
        zzfu zzfuVar = zzhwVarZzk.zzs;
        zzhwVarZzk.zzs.zzav().zzh(new zzhb(zzhwVarZzk, j));
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void setUserId(String str, long j) throws IllegalStateException, RemoteException {
        zzb();
        if (this.zza.zzc().zzn(null, zzea.zzaA) && str != null && str.length() == 0) {
            this.zza.zzau().zze().zza("User ID must be non-empty");
        } else {
            this.zza.zzk().zzz(null, Database.COLUMN_ALARM_ID, str, true, j);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void setUserProperty(String str, String str2, IObjectWrapper iObjectWrapper, boolean z, long j) throws IllegalStateException, RemoteException {
        zzb();
        this.zza.zzk().zzz(str, str2, ObjectWrapper.unwrap(iObjectWrapper), z, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzcc
    public void unregisterOnMeasurementEventListener(com.google.android.gms.internal.measurement.zzci zzciVar) throws RemoteException {
        zzgv zzgvVarRemove;
        zzb();
        synchronized (this.zzb) {
            zzgvVarRemove = this.zzb.remove(Integer.valueOf(zzciVar.zze()));
        }
        if (zzgvVarRemove == null) {
            zzgvVarRemove = new zzo(this, zzciVar);
        }
        this.zza.zzk().zzK(zzgvVarRemove);
    }
}
