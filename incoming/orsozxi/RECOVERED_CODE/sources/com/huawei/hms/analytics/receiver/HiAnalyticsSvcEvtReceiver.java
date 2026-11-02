package com.huawei.hms.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hms.analytics.ar;
import com.huawei.hms.analytics.ba;
import com.huawei.hms.analytics.bh;
import com.huawei.hms.analytics.bw;
import com.huawei.hms.analytics.cc;
import com.huawei.hms.analytics.cd;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.di;
import com.huawei.hms.analytics.dj;
import com.huawei.hms.analytics.dq;
import com.huawei.hms.analytics.fgh;
import com.huawei.hms.analytics.type.HAParamType;

/* loaded from: classes2.dex */
public class HiAnalyticsSvcEvtReceiver extends BroadcastReceiver {
    private static boolean lmn(Intent intent) {
        if (intent == null) {
            HiLog.w("HiAnalyticsSvcEvtAccepter", "intent is null");
            return true;
        }
        if (intent instanceof SafeIntent) {
            HiLog.i("HiAnalyticsSvcEvtAccepter", "safe intent");
            return ((SafeIntent) intent).lmn();
        }
        try {
            intent.getStringExtra("ANYTHING");
            return false;
        } catch (Throwable unused) {
            return true;
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String str;
        HiLog.i("HiAnalyticsSvcEvtAccepter", "onReceive is running");
        try {
            boolean z = true;
            if (dq.lmn().lmn(context)) {
                if (!ar.lmn().lmn.fgh) {
                    str = "isAnalyticsEnabled is false";
                } else if (!ba.lmn()) {
                    str = "openness init failed";
                } else if (lmn(intent)) {
                    str = "hasIntentBomb";
                } else {
                    z = false;
                }
                HiLog.w("HiAnalyticsSvcEvtAccepter", str);
            }
            if (z) {
                return;
            }
            if (context == null || intent == null) {
                HiLog.e("SvcEvtReceiverHolder", "context or intent is null");
                return;
            }
            if (ar.lmn().lmn.ghi == null) {
                HiLog.w("SvcEvtReceiverHolder", "Check whether the SDK is initialized.");
                return;
            }
            SafeIntent safeIntent = new SafeIntent(intent);
            bw bwVar = new bw(safeIntent.getExtras());
            if (!"com.huawei.hms.analytics.pps.event".equals(safeIntent.getAction())) {
                HiLog.w("SvcEvtReceiverHolder", "onReceive : event is not ads");
                return;
            }
            HiLog.i("SvcEvtReceiverHolder", "onReceive : event is ads");
            bw bwVar2 = new bw(bwVar.lmn("event_detail"));
            String strKlm = bwVar2.klm("event_type");
            if (TextUtils.isEmpty(strKlm) || (!"$RequestAd".equals(strKlm) && !"$DisplayAd".equals(strKlm) && !"$ClickAd".equals(strKlm) && !"$ObtainAdAward".equals(strKlm))) {
                strKlm = "";
            }
            String strKlm2 = bwVar2.klm("package_name");
            cc ccVarLmn = cd.lmn("SvcEvtReceiverHandler#handlerADSEvt(Bundle)");
            if (!TextUtils.isEmpty(strKlm) && !TextUtils.isEmpty(strKlm2)) {
                if (!strKlm2.equals(ar.lmn().lmn.ghi.getPackageName())) {
                    HiLog.w("SvcEvtReceiverHolder", "3rd package names are not equal");
                    ccVarLmn.ijk = strKlm;
                    cd.lmn(ccVarLmn);
                    return;
                }
                Bundle bundle = new Bundle();
                String strKlm3 = bwVar2.klm("slot_id");
                String strKlm4 = bwVar2.klm("activity_name");
                String strKlm5 = bwVar2.klm("sub_type");
                if (TextUtils.isEmpty(strKlm3)) {
                    strKlm3 = "";
                }
                bundle.putString(HAParamType.MATERIALSLOT, strKlm3);
                if (TextUtils.isEmpty(strKlm4)) {
                    strKlm4 = "";
                }
                bundle.putString("$CurActivityName", strKlm4);
                bundle.putString("$EvtType", "Supplier");
                bundle.putString("$AdReqType", TextUtils.isEmpty(strKlm5) ? "" : strKlm5);
                HiLog.i("SvcEvtReceiverHolder", "start onEvent");
                bh bhVarLmn = fgh.lmn().lmn("_openness_config_tag");
                if (bhVarLmn != null) {
                    bhVarLmn.lmn(strKlm, new dj(strKlm, bundle), System.currentTimeMillis());
                }
                ccVarLmn.ijk = strKlm;
                ccVarLmn.lmn = "0";
                cd.lmn(ccVarLmn);
                return;
            }
            HiLog.w("SvcEvtReceiverHolder", "event_type or package_name is empty");
            ccVarLmn.ijk = strKlm;
            cd.lmn(ccVarLmn);
        } catch (Throwable th) {
            HiLog.w("HiAnalyticsSvcEvtAccepter", "SvcEvtReceiver onReceive error." + th.getMessage());
            di.lmn(th);
        }
    }
}
