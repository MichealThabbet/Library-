package com.google.android.gms.analytics;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.gtm.zzan;
import com.google.android.gms.internal.gtm.zzap;
import com.google.android.gms.internal.gtm.zzcg;
import com.google.android.gms.internal.gtm.zzcy;
import com.google.android.gms.internal.gtm.zzcz;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.ContainerUtils;
import com.ibm.icu.impl.locale.LanguageTag;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/* loaded from: classes.dex */
public class Tracker extends zzan {
    private boolean zztb;
    private final Map<String, String> zztc;
    private final Map<String, String> zztd;
    private final zzcg zzte;
    private final zza zztf;
    private ExceptionReporter zztg;
    private zzcy zzth;

    Tracker(zzap zzapVar, String str, zzcg zzcgVar) {
        super(zzapVar);
        this.zztc = new HashMap();
        this.zztd = new HashMap();
        if (str != null) {
            this.zztc.put("&tid", str);
        }
        this.zztc.put("useSecure", "1");
        this.zztc.put("&a", Integer.toString(new Random().nextInt(Integer.MAX_VALUE) + 1));
        this.zzte = new zzcg("tracking", zzcn());
        this.zztf = new zza(zzapVar);
    }

    class zza extends zzan implements GoogleAnalytics.zza {
        private boolean zztq;
        private int zztr;
        private long zzts;
        private boolean zztt;
        private long zztu;

        protected zza(zzap zzapVar) {
            super(zzapVar);
            this.zzts = -1L;
        }

        @Override // com.google.android.gms.internal.gtm.zzan
        protected final void zzaw() {
        }

        public final void setSessionTimeout(long j) {
            this.zzts = j;
            zzay();
        }

        public final void enableAutoActivityTracking(boolean z) {
            this.zztq = z;
            zzay();
        }

        public final synchronized boolean zzax() {
            boolean z;
            z = this.zztt;
            this.zztt = false;
            return z;
        }

        private final void zzay() {
            if (this.zzts >= 0 || this.zztq) {
                zzcr().zza(Tracker.this.zztf);
            } else {
                zzcr().zzb(Tracker.this.zztf);
            }
        }

        @Override // com.google.android.gms.analytics.GoogleAnalytics.zza
        public final void zzc(Activity activity) {
            String canonicalName;
            if (this.zztr == 0) {
                if (zzcn().elapsedRealtime() >= this.zztu + Math.max(1000L, this.zzts)) {
                    this.zztt = true;
                }
            }
            this.zztr++;
            if (this.zztq) {
                Intent intent = activity.getIntent();
                if (intent != null) {
                    Tracker.this.setCampaignParamsOnNextHit(intent.getData());
                }
                HashMap map = new HashMap();
                map.put("&t", "screenview");
                Tracker tracker = Tracker.this;
                if (tracker.zzth == null) {
                    canonicalName = activity.getClass().getCanonicalName();
                } else {
                    zzcy zzcyVar = Tracker.this.zzth;
                    canonicalName = activity.getClass().getCanonicalName();
                    String str = zzcyVar.zzacs.get(canonicalName);
                    if (str != null) {
                        canonicalName = str;
                    }
                }
                tracker.set("&cd", canonicalName);
                if (TextUtils.isEmpty((CharSequence) map.get("&dr"))) {
                    Preconditions.checkNotNull(activity);
                    Intent intent2 = activity.getIntent();
                    String str2 = null;
                    if (intent2 != null) {
                        String stringExtra = intent2.getStringExtra("android.intent.extra.REFERRER_NAME");
                        if (!TextUtils.isEmpty(stringExtra)) {
                            str2 = stringExtra;
                        }
                    }
                    if (!TextUtils.isEmpty(str2)) {
                        map.put("&dr", str2);
                    }
                }
                Tracker.this.send(map);
            }
        }

        @Override // com.google.android.gms.analytics.GoogleAnalytics.zza
        public final void zzd(Activity activity) {
            int i = this.zztr - 1;
            this.zztr = i;
            int iMax = Math.max(0, i);
            this.zztr = iMax;
            if (iMax == 0) {
                this.zztu = zzcn().elapsedRealtime();
            }
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzan
    protected final void zzaw() {
        this.zztf.zzag();
        String strZzaz = zzcu().zzaz();
        if (strZzaz != null) {
            set("&an", strZzaz);
        }
        String strZzba = zzcu().zzba();
        if (strZzba != null) {
            set("&av", strZzba);
        }
    }

    final void zza(zzcy zzcyVar) {
        zzq("Loading Tracker config values");
        this.zzth = zzcyVar;
        if (zzcyVar.zzacm != null) {
            String str = this.zzth.zzacm;
            set("&tid", str);
            zza("trackingId loaded", str);
        }
        if (this.zzth.zzacn >= 0.0d) {
            String string = Double.toString(this.zzth.zzacn);
            set("&sf", string);
            zza("Sample frequency loaded", string);
        }
        if (this.zzth.zzaco >= 0) {
            int i = this.zzth.zzaco;
            setSessionTimeout(i);
            zza("Session timeout loaded", Integer.valueOf(i));
        }
        if (this.zzth.zzacp != -1) {
            boolean z = this.zzth.zzacp == 1;
            enableAutoActivityTracking(z);
            zza("Auto activity tracking loaded", Boolean.valueOf(z));
        }
        if (this.zzth.zzacq != -1) {
            boolean z2 = this.zzth.zzacq == 1;
            if (z2) {
                set("&aip", "1");
            }
            zza("Anonymize ip loaded", Boolean.valueOf(z2));
        }
        enableExceptionReporting(this.zzth.zzacr == 1);
    }

    public void enableExceptionReporting(boolean z) {
        synchronized (this) {
            if ((this.zztg != null) == z) {
                return;
            }
            if (z) {
                ExceptionReporter exceptionReporter = new ExceptionReporter(this, Thread.getDefaultUncaughtExceptionHandler(), getContext());
                this.zztg = exceptionReporter;
                Thread.setDefaultUncaughtExceptionHandler(exceptionReporter);
                zzq("Uncaught exceptions will be reported to Google Analytics");
            } else {
                Thread.setDefaultUncaughtExceptionHandler(this.zztg.zzaf());
                zzq("Uncaught exceptions will not be reported to Google Analytics");
            }
        }
    }

    public void setSessionTimeout(long j) {
        this.zztf.setSessionTimeout(j * 1000);
    }

    public void enableAutoActivityTracking(boolean z) {
        this.zztf.enableAutoActivityTracking(z);
    }

    private static String zza(Map.Entry<String, String> entry) {
        String key = entry.getKey();
        if (key.startsWith(ContainerUtils.FIELD_DELIMITER) && key.length() >= 2) {
            return entry.getKey().substring(1);
        }
        return null;
    }

    private static void zza(Map<String, String> map, Map<String, String> map2) {
        Preconditions.checkNotNull(map2);
        if (map == null) {
            return;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String strZza = zza(entry);
            if (strZza != null) {
                map2.put(strZza, entry.getValue());
            }
        }
    }

    public void send(Map<String, String> map) {
        long jCurrentTimeMillis = zzcn().currentTimeMillis();
        if (zzcr().getAppOptOut()) {
            zzr("AppOptOut is set to true. Not sending Google Analytics hit");
            return;
        }
        boolean zIsDryRunEnabled = zzcr().isDryRunEnabled();
        HashMap map2 = new HashMap();
        zza(this.zztc, map2);
        zza(map, map2);
        int i = 1;
        boolean zZzb = zzcz.zzb(this.zztc.get("useSecure"), true);
        Map<String, String> map3 = this.zztd;
        Preconditions.checkNotNull(map2);
        if (map3 != null) {
            for (Map.Entry<String, String> entry : map3.entrySet()) {
                String strZza = zza(entry);
                if (strZza != null && !map2.containsKey(strZza)) {
                    map2.put(strZza, entry.getValue());
                }
            }
        }
        this.zztd.clear();
        String str = map2.get("t");
        if (TextUtils.isEmpty(str)) {
            zzco().zza(map2, "Missing hit type parameter");
            return;
        }
        String str2 = map2.get("tid");
        if (TextUtils.isEmpty(str2)) {
            zzco().zza(map2, "Missing tracking id parameter");
            return;
        }
        boolean z = this.zztb;
        synchronized (this) {
            if ("screenview".equalsIgnoreCase(str) || "pageview".equalsIgnoreCase(str) || "appview".equalsIgnoreCase(str) || TextUtils.isEmpty(str)) {
                int i2 = Integer.parseInt(this.zztc.get("&a")) + 1;
                if (i2 < Integer.MAX_VALUE) {
                    i = i2;
                }
                this.zztc.put("&a", Integer.toString(i));
            }
        }
        zzcq().zza(new zzp(this, map2, z, str, jCurrentTimeMillis, zIsDryRunEnabled, zZzb, str2));
    }

    public String get(String str) {
        zzdb();
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (this.zztc.containsKey(str)) {
            return this.zztc.get(str);
        }
        if (str.equals("&ul")) {
            return zzcz.zza(Locale.getDefault());
        }
        if (str.equals("&cid")) {
            return zzcw().zzeh();
        }
        if (str.equals("&sr")) {
            return zzcz().zzfb();
        }
        if (str.equals("&aid")) {
            return zzcy().zzdv().zzbb();
        }
        if (str.equals("&an")) {
            return zzcy().zzdv().zzaz();
        }
        if (str.equals("&av")) {
            return zzcy().zzdv().zzba();
        }
        if (str.equals("&aiid")) {
            return zzcy().zzdv().zzbc();
        }
        return null;
    }

    public void set(String str, String str2) {
        Preconditions.checkNotNull(str, "Key should be non-null");
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.zztc.put(str, str2);
    }

    public void setSampleRate(double d) {
        set("&sf", Double.toString(d));
    }

    public void setUseSecure(boolean z) {
        set("useSecure", zzcz.zzc(z));
    }

    public void setScreenName(String str) {
        set("&cd", str);
    }

    public void setLocation(String str) {
        set("&dl", str);
    }

    public void setReferrer(String str) {
        set("&dr", str);
    }

    public void setPage(String str) {
        set("&dp", str);
    }

    public void setHostname(String str) {
        set("&dh", str);
    }

    public void setTitle(String str) {
        set("&dt", str);
    }

    public void setLanguage(String str) {
        set("&ul", str);
    }

    public void setEncoding(String str) {
        set("&de", str);
    }

    public void setScreenColors(String str) {
        set("&sd", str);
    }

    public void setScreenResolution(int i, int i2) {
        if (i < 0 && i2 < 0) {
            zzt("Invalid width or height. The values should be non-negative.");
            return;
        }
        StringBuilder sb = new StringBuilder(23);
        sb.append(i);
        sb.append(LanguageTag.PRIVATEUSE);
        sb.append(i2);
        set("&sr", sb.toString());
    }

    public void setViewportSize(String str) {
        set("&vp", str);
    }

    public void setClientId(String str) {
        set("&cid", str);
    }

    public void setAppName(String str) {
        set("&an", str);
    }

    public void setAppId(String str) {
        set("&aid", str);
    }

    public void setAppInstallerId(String str) {
        set("&aiid", str);
    }

    public void setAppVersion(String str) {
        set("&av", str);
    }

    public void setAnonymizeIp(boolean z) {
        set("&aip", zzcz.zzc(z));
    }

    public void setCampaignParamsOnNextHit(Uri uri) {
        if (uri == null || uri.isOpaque()) {
            return;
        }
        String queryParameter = uri.getQueryParameter("referrer");
        if (TextUtils.isEmpty(queryParameter)) {
            return;
        }
        String strValueOf = String.valueOf(queryParameter);
        Uri uri2 = Uri.parse(strValueOf.length() != 0 ? "http://hostname/?".concat(strValueOf) : new String("http://hostname/?"));
        String queryParameter2 = uri2.getQueryParameter("utm_id");
        if (queryParameter2 != null) {
            this.zztd.put("&ci", queryParameter2);
        }
        String queryParameter3 = uri2.getQueryParameter("anid");
        if (queryParameter3 != null) {
            this.zztd.put("&anid", queryParameter3);
        }
        String queryParameter4 = uri2.getQueryParameter("utm_campaign");
        if (queryParameter4 != null) {
            this.zztd.put("&cn", queryParameter4);
        }
        String queryParameter5 = uri2.getQueryParameter("utm_content");
        if (queryParameter5 != null) {
            this.zztd.put("&cc", queryParameter5);
        }
        String queryParameter6 = uri2.getQueryParameter("utm_medium");
        if (queryParameter6 != null) {
            this.zztd.put("&cm", queryParameter6);
        }
        String queryParameter7 = uri2.getQueryParameter("utm_source");
        if (queryParameter7 != null) {
            this.zztd.put("&cs", queryParameter7);
        }
        String queryParameter8 = uri2.getQueryParameter("utm_term");
        if (queryParameter8 != null) {
            this.zztd.put("&ck", queryParameter8);
        }
        String queryParameter9 = uri2.getQueryParameter("dclid");
        if (queryParameter9 != null) {
            this.zztd.put("&dclid", queryParameter9);
        }
        String queryParameter10 = uri2.getQueryParameter("gclid");
        if (queryParameter10 != null) {
            this.zztd.put("&gclid", queryParameter10);
        }
        String queryParameter11 = uri2.getQueryParameter(FirebaseAnalytics.Param.ACLID);
        if (queryParameter11 != null) {
            this.zztd.put("&aclid", queryParameter11);
        }
    }

    public void enableAdvertisingIdCollection(boolean z) {
        this.zztb = z;
    }
}
