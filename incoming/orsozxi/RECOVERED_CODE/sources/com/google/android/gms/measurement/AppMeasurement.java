package com.google.android.gms.measurement;

import android.content.Context;
import android.os.Bundle;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzcl;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.android.gms.measurement.internal.zzfu;
import com.google.android.gms.measurement.internal.zzgq;
import com.google.android.gms.measurement.internal.zzgu;
import com.google.android.gms.measurement.internal.zzgv;
import com.google.android.gms.measurement.internal.zzhx;
import com.google.android.gms.measurement.internal.zzic;
import com.google.android.gms.measurement.internal.zzkq;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.0 */
@Deprecated
/* loaded from: classes.dex */
public class AppMeasurement {
    public static final String CRASH_ORIGIN = "crash";
    public static final String FCM_ORIGIN = "fcm";
    public static final String FIAM_ORIGIN = "fiam";
    private static volatile AppMeasurement zza;
    private final zzfu zzb;
    private final zzhx zzc;

    /* compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.0 */
    public interface EventInterceptor extends zzgu {
        @Override // com.google.android.gms.measurement.internal.zzgu
        void interceptEvent(String str, String str2, Bundle bundle, long j);
    }

    /* compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.0 */
    public interface OnEventListener extends zzgv {
        @Override // com.google.android.gms.measurement.internal.zzgv
        void onEvent(String str, String str2, Bundle bundle, long j);
    }

    public AppMeasurement(zzfu zzfuVar) {
        Preconditions.checkNotNull(zzfuVar);
        this.zzb = zzfuVar;
        this.zzc = null;
    }

    @Deprecated
    public static AppMeasurement getInstance(Context context) {
        zzhx zzhxVar;
        if (zza == null) {
            synchronized (AppMeasurement.class) {
                if (zza == null) {
                    try {
                        zzhxVar = (zzhx) Class.forName("com.google.firebase.analytics.FirebaseAnalytics").getDeclaredMethod("getScionFrontendApiImplementation", Context.class, Bundle.class).invoke(null, context, null);
                    } catch (ClassNotFoundException | Exception unused) {
                        zzhxVar = null;
                    }
                    if (zzhxVar != null) {
                        zza = new AppMeasurement(zzhxVar);
                    } else {
                        zza = new AppMeasurement(zzfu.zzC(context, new zzcl(0L, 0L, true, null, null, null, null, null), null));
                    }
                }
            }
        }
        return zza;
    }

    public void beginAdUnitExposure(String str) throws IllegalStateException {
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            zzhxVar.zzl(str);
        } else {
            Preconditions.checkNotNull(this.zzb);
            this.zzb.zzB().zza(str, this.zzb.zzay().elapsedRealtime());
        }
    }

    public void clearConditionalUserProperty(String str, String str2, Bundle bundle) throws IllegalStateException {
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            zzhxVar.zzo(str, str2, bundle);
        } else {
            Preconditions.checkNotNull(this.zzb);
            this.zzb.zzk().zzO(str, str2, bundle);
        }
    }

    public void endAdUnitExposure(String str) throws IllegalStateException {
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            zzhxVar.zzm(str);
        } else {
            Preconditions.checkNotNull(this.zzb);
            this.zzb.zzB().zzb(str, this.zzb.zzay().elapsedRealtime());
        }
    }

    public long generateEventId() {
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            return zzhxVar.zzk();
        }
        Preconditions.checkNotNull(this.zzb);
        return this.zzb.zzl().zzd();
    }

    public String getAppInstanceId() {
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            return zzhxVar.zzi();
        }
        Preconditions.checkNotNull(this.zzb);
        return this.zzb.zzk().zzD();
    }

    public Boolean getBoolean() {
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            return (Boolean) zzhxVar.zzr(4);
        }
        Preconditions.checkNotNull(this.zzb);
        return this.zzb.zzk().zzi();
    }

    public List<ConditionalUserProperty> getConditionalUserProperties(String str, String str2) {
        List<Bundle> listZzP;
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            listZzP = zzhxVar.zzp(str, str2);
        } else {
            Preconditions.checkNotNull(this.zzb);
            listZzP = this.zzb.zzk().zzP(str, str2);
        }
        ArrayList arrayList = new ArrayList(listZzP == null ? 0 : listZzP.size());
        Iterator<Bundle> it2 = listZzP.iterator();
        while (it2.hasNext()) {
            arrayList.add(new ConditionalUserProperty(it2.next()));
        }
        return arrayList;
    }

    public String getCurrentScreenClass() {
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            return zzhxVar.zzh();
        }
        Preconditions.checkNotNull(this.zzb);
        return this.zzb.zzk().zzS();
    }

    public String getCurrentScreenName() {
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            return zzhxVar.zzg();
        }
        Preconditions.checkNotNull(this.zzb);
        return this.zzb.zzk().zzR();
    }

    public Double getDouble() {
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            return (Double) zzhxVar.zzr(2);
        }
        Preconditions.checkNotNull(this.zzb);
        return this.zzb.zzk().zzm();
    }

    public String getGmpAppId() {
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            return zzhxVar.zzj();
        }
        Preconditions.checkNotNull(this.zzb);
        return this.zzb.zzk().zzT();
    }

    public Integer getInteger() {
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            return (Integer) zzhxVar.zzr(3);
        }
        Preconditions.checkNotNull(this.zzb);
        return this.zzb.zzk().zzl();
    }

    public Long getLong() {
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            return (Long) zzhxVar.zzr(1);
        }
        Preconditions.checkNotNull(this.zzb);
        return this.zzb.zzk().zzk();
    }

    public int getMaxUserProperties(String str) {
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            return zzhxVar.zzq(str);
        }
        Preconditions.checkNotNull(this.zzb);
        this.zzb.zzk().zzL(str);
        return 25;
    }

    public String getString() {
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            return (String) zzhxVar.zzr(0);
        }
        Preconditions.checkNotNull(this.zzb);
        return this.zzb.zzk().zzj();
    }

    protected Map<String, Object> getUserProperties(String str, String str2, boolean z) {
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            return zzhxVar.zzc(str, str2, z);
        }
        Preconditions.checkNotNull(this.zzb);
        return this.zzb.zzk().zzQ(str, str2, z);
    }

    public void logEventInternal(String str, String str2, Bundle bundle) {
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            zzhxVar.zza(str, str2, bundle);
        } else {
            Preconditions.checkNotNull(this.zzb);
            this.zzb.zzk().zzs(str, str2, bundle);
        }
    }

    public void logEventInternalNoInterceptor(String str, String str2, Bundle bundle, long j) throws IllegalStateException {
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            zzhxVar.zzb(str, str2, bundle, j);
        } else {
            Preconditions.checkNotNull(this.zzb);
            this.zzb.zzk().zzv(str, str2, bundle, true, false, j);
        }
    }

    public void registerOnMeasurementEventListener(OnEventListener onEventListener) {
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            zzhxVar.zze(onEventListener);
        } else {
            Preconditions.checkNotNull(this.zzb);
            this.zzb.zzk().zzJ(onEventListener);
        }
    }

    public void setConditionalUserProperty(ConditionalUserProperty conditionalUserProperty) throws IllegalStateException {
        Preconditions.checkNotNull(conditionalUserProperty);
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            zzhxVar.zzn(conditionalUserProperty.zza());
        } else {
            Preconditions.checkNotNull(this.zzb);
            this.zzb.zzk().zzM(conditionalUserProperty.zza());
        }
    }

    public void setEventInterceptor(EventInterceptor eventInterceptor) {
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            zzhxVar.zzd(eventInterceptor);
        } else {
            Preconditions.checkNotNull(this.zzb);
            this.zzb.zzk().zzI(eventInterceptor);
        }
    }

    public void unregisterOnMeasurementEventListener(OnEventListener onEventListener) {
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            zzhxVar.zzf(onEventListener);
        } else {
            Preconditions.checkNotNull(this.zzb);
            this.zzb.zzk().zzK(onEventListener);
        }
    }

    public AppMeasurement(zzhx zzhxVar) {
        Preconditions.checkNotNull(zzhxVar);
        this.zzc = zzhxVar;
        this.zzb = null;
    }

    public Map<String, Object> getUserProperties(boolean z) {
        zzhx zzhxVar = this.zzc;
        if (zzhxVar != null) {
            return zzhxVar.zzc(null, null, z);
        }
        Preconditions.checkNotNull(this.zzb);
        List<zzkq> listZzC = this.zzb.zzk().zzC(z);
        ArrayMap arrayMap = new ArrayMap(listZzC.size());
        for (zzkq zzkqVar : listZzC) {
            Object objZza = zzkqVar.zza();
            if (objZza != null) {
                arrayMap.put(zzkqVar.zzb, objZza);
            }
        }
        return arrayMap;
    }

    /* compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.0 */
    public static class ConditionalUserProperty {
        public boolean mActive;
        public String mAppId;
        public long mCreationTimestamp;
        public String mExpiredEventName;
        public Bundle mExpiredEventParams;
        public String mName;
        public String mOrigin;
        public long mTimeToLive;
        public String mTimedOutEventName;
        public Bundle mTimedOutEventParams;
        public String mTriggerEventName;
        public long mTriggerTimeout;
        public String mTriggeredEventName;
        public Bundle mTriggeredEventParams;
        public long mTriggeredTimestamp;
        public Object mValue;

        public ConditionalUserProperty() {
        }

        ConditionalUserProperty(Bundle bundle) {
            Preconditions.checkNotNull(bundle);
            this.mAppId = (String) zzgq.zzb(bundle, "app_id", String.class, null);
            this.mOrigin = (String) zzgq.zzb(bundle, "origin", String.class, null);
            this.mName = (String) zzgq.zzb(bundle, "name", String.class, null);
            this.mValue = zzgq.zzb(bundle, "value", Object.class, null);
            this.mTriggerEventName = (String) zzgq.zzb(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, String.class, null);
            this.mTriggerTimeout = ((Long) zzgq.zzb(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, Long.class, 0L)).longValue();
            this.mTimedOutEventName = (String) zzgq.zzb(bundle, AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_NAME, String.class, null);
            this.mTimedOutEventParams = (Bundle) zzgq.zzb(bundle, AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_PARAMS, Bundle.class, null);
            this.mTriggeredEventName = (String) zzgq.zzb(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_NAME, String.class, null);
            this.mTriggeredEventParams = (Bundle) zzgq.zzb(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_PARAMS, Bundle.class, null);
            this.mTimeToLive = ((Long) zzgq.zzb(bundle, AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, Long.class, 0L)).longValue();
            this.mExpiredEventName = (String) zzgq.zzb(bundle, AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME, String.class, null);
            this.mExpiredEventParams = (Bundle) zzgq.zzb(bundle, AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS, Bundle.class, null);
            this.mActive = ((Boolean) zzgq.zzb(bundle, AppMeasurementSdk.ConditionalUserProperty.ACTIVE, Boolean.class, false)).booleanValue();
            this.mCreationTimestamp = ((Long) zzgq.zzb(bundle, AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, Long.class, 0L)).longValue();
            this.mTriggeredTimestamp = ((Long) zzgq.zzb(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_TIMESTAMP, Long.class, 0L)).longValue();
        }

        final Bundle zza() {
            Bundle bundle = new Bundle();
            String str = this.mAppId;
            if (str != null) {
                bundle.putString("app_id", str);
            }
            String str2 = this.mOrigin;
            if (str2 != null) {
                bundle.putString("origin", str2);
            }
            String str3 = this.mName;
            if (str3 != null) {
                bundle.putString("name", str3);
            }
            Object obj = this.mValue;
            if (obj != null) {
                zzgq.zza(bundle, obj);
            }
            String str4 = this.mTriggerEventName;
            if (str4 != null) {
                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, str4);
            }
            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, this.mTriggerTimeout);
            String str5 = this.mTimedOutEventName;
            if (str5 != null) {
                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_NAME, str5);
            }
            Bundle bundle2 = this.mTimedOutEventParams;
            if (bundle2 != null) {
                bundle.putBundle(AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_PARAMS, bundle2);
            }
            String str6 = this.mTriggeredEventName;
            if (str6 != null) {
                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_NAME, str6);
            }
            Bundle bundle3 = this.mTriggeredEventParams;
            if (bundle3 != null) {
                bundle.putBundle(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_PARAMS, bundle3);
            }
            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, this.mTimeToLive);
            String str7 = this.mExpiredEventName;
            if (str7 != null) {
                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME, str7);
            }
            Bundle bundle4 = this.mExpiredEventParams;
            if (bundle4 != null) {
                bundle.putBundle(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS, bundle4);
            }
            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, this.mCreationTimestamp);
            bundle.putBoolean(AppMeasurementSdk.ConditionalUserProperty.ACTIVE, this.mActive);
            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_TIMESTAMP, this.mTriggeredTimestamp);
            return bundle;
        }

        public ConditionalUserProperty(ConditionalUserProperty conditionalUserProperty) throws Throwable {
            Preconditions.checkNotNull(conditionalUserProperty);
            this.mAppId = conditionalUserProperty.mAppId;
            this.mOrigin = conditionalUserProperty.mOrigin;
            this.mCreationTimestamp = conditionalUserProperty.mCreationTimestamp;
            this.mName = conditionalUserProperty.mName;
            Object obj = conditionalUserProperty.mValue;
            if (obj != null) {
                Object objZzb = zzic.zzb(obj);
                this.mValue = objZzb;
                if (objZzb == null) {
                    this.mValue = conditionalUserProperty.mValue;
                }
            }
            this.mActive = conditionalUserProperty.mActive;
            this.mTriggerEventName = conditionalUserProperty.mTriggerEventName;
            this.mTriggerTimeout = conditionalUserProperty.mTriggerTimeout;
            this.mTimedOutEventName = conditionalUserProperty.mTimedOutEventName;
            Bundle bundle = conditionalUserProperty.mTimedOutEventParams;
            if (bundle != null) {
                this.mTimedOutEventParams = new Bundle(bundle);
            }
            this.mTriggeredEventName = conditionalUserProperty.mTriggeredEventName;
            Bundle bundle2 = conditionalUserProperty.mTriggeredEventParams;
            if (bundle2 != null) {
                this.mTriggeredEventParams = new Bundle(bundle2);
            }
            this.mTriggeredTimestamp = conditionalUserProperty.mTriggeredTimestamp;
            this.mTimeToLive = conditionalUserProperty.mTimeToLive;
            this.mExpiredEventName = conditionalUserProperty.mExpiredEventName;
            Bundle bundle3 = conditionalUserProperty.mExpiredEventParams;
            if (bundle3 != null) {
                this.mExpiredEventParams = new Bundle(bundle3);
            }
        }
    }
}
