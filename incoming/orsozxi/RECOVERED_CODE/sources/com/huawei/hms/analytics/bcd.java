package com.huawei.hms.analytics;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.type.HAParamType;
import java.util.HashMap;
import java.util.Map;
import net.fortuna.ical4j.util.Dates;

/* loaded from: classes2.dex */
public final class bcd {
    static bcd klm = new bcd();
    public bh def;
    public lmn hij;
    public final Map<String, lmn> lmn = new HashMap();
    public lmn ikl = new lmn("", "", "", 0);
    public lmn ijk = new lmn("", "", "", 0);
    public long ghi = 0;
    private boolean abc = false;
    boolean fgh = false;
    final Handler efg = new Handler(Looper.getMainLooper());
    public final Bundle cde = new Bundle();
    public final Bundle bcd = new Bundle();

    public static class lmn {
        public long ijk;
        public String ikl;
        public String klm;
        public String lmn;

        public lmn(String str, String str2, String str3, long j) {
            this.lmn = str;
            this.klm = str2;
            this.ikl = str3;
            this.ijk = j;
        }

        public final void lmn() {
            this.lmn = "";
            this.klm = "";
            this.ikl = "";
            this.ijk = 0L;
        }
    }

    public static bcd lmn() {
        return klm;
    }

    public final void lmn(long j) {
        HiLog.d("ActivityStatCommander", "onScreenExit with time: ".concat(String.valueOf(j)));
        lmn lmnVar = this.ikl;
        if (lmnVar == null) {
            HiLog.w("ActivityStatCommander", "onScreenEnter should be invoked first.");
            return;
        }
        long j2 = lmnVar.ijk;
        if (j2 == 0) {
            HiLog.w("ActivityStatCommander", "timestamp is 0.");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("$CurActivityName", this.ikl.lmn);
        bundle.putString("$CurActivityClass", this.ikl.klm);
        bundle.putString("$CurActivityId", this.ikl.ikl);
        bundle.putString("$PrePageId", this.cde.getString("$PrevActivityClass", ""));
        bundle.putString("$CurrPageId", this.cde.getString("$CurActivityClass", ""));
        HiLog.d("ActivityStatCommander", "onScreenExit duration cal: 1. " + j + " 2. " + j2);
        bundle.putString(HAParamType.DURATION, String.valueOf(Math.min(j - j2, Dates.MILLIS_PER_WEEK)));
        HiLog.i("ActivityStatCommander", "onScreenExit: send ScreenExit event...");
        this.def.lmn("$ExitScreen", new dj("$ExitScreen", bundle), j);
        this.fgh = false;
    }

    public final void lmn(final Activity activity, final Bundle bundle, final long j) {
        HiLog.d("ActivityStatCommander", "onScreenEnterDelayed with time: ".concat(String.valueOf(j)));
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("ActivityStatCommander", "auto collect is closed");
            return;
        }
        String canonicalName = activity.getClass().getCanonicalName();
        final lmn lmnVar = new lmn(canonicalName, canonicalName, String.valueOf(activity.getTaskId()), j);
        this.abc = true;
        this.efg.postDelayed(new Runnable() { // from class: com.huawei.hms.analytics.bcd.1
            @Override // java.lang.Runnable
            public final void run() {
                if (bcd.this.abc) {
                    HiLog.d("ActivityStatCommander", "isEnterDelayed = true, no override screen event...");
                    try {
                        bcd.this.lmn(lmnVar, bundle, j);
                    } catch (Throwable th) {
                        HiLog.w("ActivityStatCommander", "onScreenEnter error: " + th.getMessage());
                        di.lmn(th);
                    }
                }
            }
        }, 100L);
        this.lmn.clear();
        this.ijk = null;
    }

    public final void lmn(lmn lmnVar, Bundle bundle, long j) {
        Bundle bundle2;
        lmn lmnVar2;
        HiLog.d("ActivityStatCommander", "onScreenEnterSend");
        lmn lmnVar3 = this.hij;
        if (lmnVar3 == null) {
            bundle.putString("$PrevActivityName", this.ikl.lmn);
            bundle.putString("$PrevActivityClass", this.ikl.klm);
            bundle.putString("$PrevActivityId", this.ikl.ikl);
            bundle.putString("$PrePageId", this.ikl.klm);
            bundle2 = this.cde;
            lmnVar2 = this.ikl;
        } else {
            bundle.putString("$PrevActivityName", lmnVar3.lmn);
            bundle.putString("$PrevActivityClass", this.hij.klm);
            bundle.putString("$PrevActivityId", this.hij.ikl);
            bundle.putString("$PrePageId", this.hij.klm);
            bundle2 = this.cde;
            lmnVar2 = this.hij;
        }
        bundle2.putString("$PrevActivityClass", lmnVar2.klm);
        bundle.putString("$CurActivityName", lmnVar.lmn);
        bundle.putString("$CurActivityClass", lmnVar.klm);
        bundle.putString("$CurActivityId", lmnVar.ikl);
        bundle.putString("$CurrPageId", lmnVar.klm);
        this.def.lmn("$EnterScreen", new dj("$EnterScreen", bundle), j);
        this.cde.putString("$CurActivityClass", lmnVar.klm);
        this.abc = false;
        this.ikl = lmnVar;
    }
}
