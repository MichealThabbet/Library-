package com.huawei.hms.analytics;

import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.type.ReportPolicy;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class bp {
    private static bp ikl = new bp();
    private lmn ijk;
    public JSONObject lmn;
    private boolean hij = false;
    public boolean klm = false;
    private bh ghi = fgh.lmn().lmn("_openness_config_tag");

    /* renamed from: com.huawei.hms.analytics.bp$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] lmn;

        static {
            int[] iArr = new int[ReportPolicy.values().length];
            lmn = iArr;
            try {
                iArr[ReportPolicy.ON_APP_LAUNCH_POLICY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                lmn[ReportPolicy.ON_SCHEDULED_TIME_POLICY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                lmn[ReportPolicy.ON_CACHE_THRESHOLD_POLICY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                lmn[ReportPolicy.ON_MOVE_BACKGROUND_POLICY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    final class lmn extends TimerTask {
        private long ikl = -1;
        private Timer klm = new Timer();

        public lmn() {
        }

        public final boolean klm(long j) {
            long j2 = this.ikl;
            return (j2 == -1 || j2 == j) ? false : true;
        }

        public final void lmn() {
            this.klm.cancel();
            this.klm.purge();
            this.klm = null;
        }

        public final void lmn(long j) {
            this.ikl = j;
            long j2 = j * 1000;
            this.klm.schedule(this, j2, j2);
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public final void run() {
            HiLog.i("PoliceCommander", "Timer report timer running");
            if (bp.this.ghi != null) {
                bp.this.ghi.lmn();
                bp.this.ghi.klm();
            }
            fgh.lmn().klm();
        }
    }

    public static bp lmn() {
        return ikl;
    }

    public static void lmn(long j) {
        ar.lmn().lmn.c = j;
    }

    private void lmn(JSONObject jSONObject) {
        if (this.lmn != null) {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                this.lmn.remove(itKeys.next());
            }
            Iterator<String> itKeys2 = this.lmn.keys();
            if (itKeys2.hasNext()) {
                String next = itKeys2.next();
                if ("onMoveBackgroundPolicy".equals(next)) {
                    lmn(false);
                } else if ("onScheduledTime".equals(next)) {
                    this.ijk.lmn();
                    this.ijk = null;
                } else if ("onCacheThreshold".equals(next)) {
                    lmn(30L);
                }
            }
        }
        HiLog.i("PoliceCommander", "save policies : " + jSONObject.toString());
        dl.lmn(ar.lmn().lmn.ghi, "global_v2", "policies", jSONObject.toString());
    }

    private static void lmn(JSONObject jSONObject, String str, Object obj) throws JSONException {
        try {
            jSONObject.put(str, obj);
        } catch (JSONException unused) {
            HiLog.w("PoliceCommander", "jsonPut json Exception");
        }
    }

    public static void lmn(boolean z) {
        a.lmn(ar.lmn().lmn.ghi).klm = z;
    }

    public final void ikl() {
        if (this.hij) {
            return;
        }
        if (this.ghi != null) {
            this.hij = true;
            HiLog.i("PoliceCommander", "onAppLaunchPolicy onReport");
            this.ghi.lmn();
            this.ghi.klm();
        }
        fgh.lmn().klm();
    }

    public final void klm() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        this.lmn = jSONObject;
        try {
            jSONObject.put("onMoveBackgroundPolicy", "");
            this.lmn.put("onCacheThreshold", 30L);
        } catch (JSONException unused) {
            HiLog.w("PoliceCommander", "defPolicies json exception");
        }
    }

    public final void klm(long j) {
        lmn lmnVar = this.ijk;
        if (lmnVar == null) {
            lmn lmnVar2 = new lmn();
            this.ijk = lmnVar2;
            lmnVar2.lmn(j);
        } else if (lmnVar.klm(j)) {
            this.ijk.lmn();
            lmn lmnVar3 = new lmn();
            this.ijk = lmnVar3;
            lmnVar3.lmn(j);
        }
    }

    public final synchronized void lmn(Set<ReportPolicy> set) {
        if (set != null) {
            if (set.size() > 0) {
                JSONObject jSONObject = new JSONObject();
                for (ReportPolicy reportPolicy : set) {
                    int i = AnonymousClass1.lmn[reportPolicy.ordinal()];
                    boolean z = true;
                    if (i == 1) {
                        lmn(jSONObject, "onAppLaunch", "");
                        ikl();
                    } else if (i == 2) {
                        long threshold = reportPolicy.getThreshold();
                        lmn(jSONObject, "onScheduledTime", Long.valueOf(threshold));
                        if (this.lmn != null && this.lmn.optLong("onScheduledTime", -1L) == threshold) {
                            z = false;
                        }
                        if (z) {
                            klm(threshold);
                        }
                    } else if (i == 3) {
                        long threshold2 = reportPolicy.getThreshold();
                        lmn(jSONObject, "onCacheThreshold", Long.valueOf(threshold2));
                        lmn(threshold2);
                    } else if (i == 4) {
                        lmn(jSONObject, "onMoveBackgroundPolicy", "");
                        lmn(true);
                    }
                }
                lmn(jSONObject);
                this.lmn = jSONObject;
            }
        }
    }
}
