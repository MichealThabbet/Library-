package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.gtm.zzor;
import com.google.android.gms.internal.gtm.zzov;
import com.google.android.gms.internal.gtm.zzoz;
import com.google.android.gms.tagmanager.zzeh;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class Container {
    private final String zzaec;
    private final DataLayer zzaed;
    private zzfb zzaee;
    private Map<String, FunctionCallMacroCallback> zzaef;
    private Map<String, FunctionCallTagCallback> zzaeg;
    private volatile long zzaeh;
    private volatile String zzaei;
    private final Context zzrm;

    public interface FunctionCallMacroCallback {
        Object getValue(String str, Map<String, Object> map);
    }

    public interface FunctionCallTagCallback {
        void execute(String str, Map<String, Object> map);
    }

    class zza implements zzan {
        private zza() {
        }

        @Override // com.google.android.gms.tagmanager.zzan
        public final Object zza(String str, Map<String, Object> map) {
            FunctionCallMacroCallback functionCallMacroCallbackZzal = Container.this.zzal(str);
            if (functionCallMacroCallbackZzal == null) {
                return null;
            }
            return functionCallMacroCallbackZzal.getValue(str, map);
        }
    }

    class zzb implements zzan {
        private zzb() {
        }

        @Override // com.google.android.gms.tagmanager.zzan
        public final Object zza(String str, Map<String, Object> map) {
            FunctionCallTagCallback functionCallTagCallbackZzam = Container.this.zzam(str);
            if (functionCallTagCallbackZzam != null) {
                functionCallTagCallbackZzam.execute(str, map);
            }
            return zzgj.zzkb();
        }
    }

    Container(Context context, DataLayer dataLayer, String str, long j, zzov zzovVar) throws InterruptedException {
        this.zzaef = new HashMap();
        this.zzaeg = new HashMap();
        this.zzaei = "";
        this.zzrm = context;
        this.zzaed = dataLayer;
        this.zzaec = str;
        this.zzaeh = 0L;
        zza(zzovVar);
    }

    Container(Context context, DataLayer dataLayer, String str, long j, com.google.android.gms.internal.gtm.zzk zzkVar) throws InterruptedException {
        this.zzaef = new HashMap();
        this.zzaeg = new HashMap();
        this.zzaei = "";
        this.zzrm = context;
        this.zzaed = dataLayer;
        this.zzaec = str;
        this.zzaeh = j;
        com.google.android.gms.internal.gtm.zzi zziVar = zzkVar.zzqk;
        if (zziVar == null) {
            throw null;
        }
        try {
            zza(zzor.zza(zziVar));
        } catch (zzoz e) {
            String strValueOf = String.valueOf(zziVar);
            String string = e.toString();
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 46 + String.valueOf(string).length());
            sb.append("Not loading resource: ");
            sb.append(strValueOf);
            sb.append(" because it is invalid: ");
            sb.append(string);
            zzdi.zzav(sb.toString());
        }
        if (zzkVar.zzqj != null) {
            com.google.android.gms.internal.gtm.zzj[] zzjVarArr = zzkVar.zzqj;
            ArrayList arrayList = new ArrayList();
            for (com.google.android.gms.internal.gtm.zzj zzjVar : zzjVarArr) {
                arrayList.add(zzjVar);
            }
            zzhb().zze(arrayList);
        }
    }

    public String getContainerId() {
        return this.zzaec;
    }

    public boolean getBoolean(String str) {
        zzfb zzfbVarZzhb = zzhb();
        if (zzfbVarZzhb == null) {
            zzdi.zzav("getBoolean called for closed container.");
            return zzgj.zzjz().booleanValue();
        }
        try {
            return zzgj.zzg(zzfbVarZzhb.zzbj(str).getObject()).booleanValue();
        } catch (Exception e) {
            String message = e.getMessage();
            StringBuilder sb = new StringBuilder(String.valueOf(message).length() + 66);
            sb.append("Calling getBoolean() threw an exception: ");
            sb.append(message);
            sb.append(" Returning default value.");
            zzdi.zzav(sb.toString());
            return zzgj.zzjz().booleanValue();
        }
    }

    public double getDouble(String str) {
        zzfb zzfbVarZzhb = zzhb();
        if (zzfbVarZzhb == null) {
            zzdi.zzav("getDouble called for closed container.");
            return zzgj.zzjy().doubleValue();
        }
        try {
            return zzgj.zzf(zzfbVarZzhb.zzbj(str).getObject()).doubleValue();
        } catch (Exception e) {
            String message = e.getMessage();
            StringBuilder sb = new StringBuilder(String.valueOf(message).length() + 65);
            sb.append("Calling getDouble() threw an exception: ");
            sb.append(message);
            sb.append(" Returning default value.");
            zzdi.zzav(sb.toString());
            return zzgj.zzjy().doubleValue();
        }
    }

    public long getLong(String str) {
        zzfb zzfbVarZzhb = zzhb();
        if (zzfbVarZzhb == null) {
            zzdi.zzav("getLong called for closed container.");
            return zzgj.zzjx().longValue();
        }
        try {
            return zzgj.zze(zzfbVarZzhb.zzbj(str).getObject()).longValue();
        } catch (Exception e) {
            String message = e.getMessage();
            StringBuilder sb = new StringBuilder(String.valueOf(message).length() + 63);
            sb.append("Calling getLong() threw an exception: ");
            sb.append(message);
            sb.append(" Returning default value.");
            zzdi.zzav(sb.toString());
            return zzgj.zzjx().longValue();
        }
    }

    public String getString(String str) {
        zzfb zzfbVarZzhb = zzhb();
        if (zzfbVarZzhb == null) {
            zzdi.zzav("getString called for closed container.");
            return zzgj.zzkb();
        }
        try {
            return zzgj.zzc(zzfbVarZzhb.zzbj(str).getObject());
        } catch (Exception e) {
            String message = e.getMessage();
            StringBuilder sb = new StringBuilder(String.valueOf(message).length() + 65);
            sb.append("Calling getString() threw an exception: ");
            sb.append(message);
            sb.append(" Returning default value.");
            zzdi.zzav(sb.toString());
            return zzgj.zzkb();
        }
    }

    public long getLastRefreshTime() {
        return this.zzaeh;
    }

    public boolean isDefault() {
        return getLastRefreshTime() == 0;
    }

    public void registerFunctionCallMacroCallback(String str, FunctionCallMacroCallback functionCallMacroCallback) {
        if (functionCallMacroCallback == null) {
            throw new NullPointerException("Macro handler must be non-null");
        }
        synchronized (this.zzaef) {
            this.zzaef.put(str, functionCallMacroCallback);
        }
    }

    public void unregisterFunctionCallMacroCallback(String str) {
        synchronized (this.zzaef) {
            this.zzaef.remove(str);
        }
    }

    final FunctionCallMacroCallback zzal(String str) {
        FunctionCallMacroCallback functionCallMacroCallback;
        synchronized (this.zzaef) {
            functionCallMacroCallback = this.zzaef.get(str);
        }
        return functionCallMacroCallback;
    }

    public void registerFunctionCallTagCallback(String str, FunctionCallTagCallback functionCallTagCallback) {
        if (functionCallTagCallback == null) {
            throw new NullPointerException("Tag callback must be non-null");
        }
        synchronized (this.zzaeg) {
            this.zzaeg.put(str, functionCallTagCallback);
        }
    }

    public void unregisterFunctionCallTagCallback(String str) {
        synchronized (this.zzaeg) {
            this.zzaeg.remove(str);
        }
    }

    public final FunctionCallTagCallback zzam(String str) {
        FunctionCallTagCallback functionCallTagCallback;
        synchronized (this.zzaeg) {
            functionCallTagCallback = this.zzaeg.get(str);
        }
        return functionCallTagCallback;
    }

    public final void zzan(String str) {
        zzhb().zzan(str);
    }

    public final String zzha() {
        return this.zzaei;
    }

    private final void zza(zzov zzovVar) throws InterruptedException {
        this.zzaei = zzovVar.getVersion();
        zzeh.zziy().zziz().equals(zzeh.zza.CONTAINER_DEBUG);
        zza(new zzfb(this.zzrm, zzovVar, this.zzaed, new zza(), new zzb(), new zzdq()));
        if (getBoolean("_gtm.loadEventEnabled")) {
            this.zzaed.pushEvent("gtm.load", DataLayer.mapOf("gtm.id", this.zzaec));
        }
    }

    private final synchronized void zza(zzfb zzfbVar) {
        this.zzaee = zzfbVar;
    }

    private final synchronized zzfb zzhb() {
        return this.zzaee;
    }

    final void release() {
        this.zzaee = null;
    }
}
