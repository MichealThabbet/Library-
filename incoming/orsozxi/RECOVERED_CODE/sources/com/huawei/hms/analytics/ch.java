package com.huawei.hms.analytics;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.huawei.hms.analytics.bc;
import com.huawei.hms.analytics.core.crypto.AesCipher;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.core.storage.Event;
import com.ibm.icu.impl.locale.LanguageTag;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.fortuna.ical4j.util.Dates;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class ch implements Runnable {
    private List<Event> klm = new ArrayList();
    private Context lmn;

    public ch(Context context) {
        this.lmn = context;
    }

    private static String klm(String str) {
        if (Build.VERSION.SDK_INT < 19) {
            return str;
        }
        String strDecryptCbc = AesCipher.decryptCbc(str, bo.lmn().klm());
        if (!TextUtils.isEmpty(strDecryptCbc)) {
            return AesCipher.gcmEncrypt(strDecryptCbc, bo.lmn().klm());
        }
        HiLog.i("HistoricalDataMovement", "decryptCbc content is empty");
        return "";
    }

    private void lmn(String str) throws NumberFormatException {
        String strLmn = dl.lmn(this.lmn, str);
        long jIkl = dl.ikl(this.lmn, strLmn);
        if (jIkl == -1) {
            return;
        }
        if (jIkl > 5242880) {
            HiLog.i("HistoricalDataMovement", "sp stat file length > 5M,begin delete it");
            if (dl.ijk(this.lmn, strLmn)) {
                HiLog.i("HistoricalDataMovement", "sp stat file delete success");
                return;
            }
            return;
        }
        Map<String, ?> mapKlm = dl.klm(this.lmn, str);
        dl.ijk(this.lmn, strLmn);
        if (mapKlm.size() == 0) {
            return;
        }
        for (Map.Entry<String, ?> entry : mapKlm.entrySet()) {
            String[] strArrSplit = entry.getKey().split(LanguageTag.SEP);
            if (strArrSplit.length == 2) {
                String str2 = strArrSplit[0];
                String str3 = strArrSplit[1];
                if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
                    try {
                        lmn(str2, entry.getValue());
                    } catch (JSONException unused) {
                        HiLog.w("HistoricalDataMovement", "cache data is not json format");
                    }
                }
            }
        }
    }

    private void lmn(String str, Object obj) throws JSONException, NumberFormatException {
        if (obj instanceof String) {
            JSONArray jSONArray = new JSONArray((String) obj);
            int length = jSONArray.length();
            if (length > 5000) {
                HiLog.w("HistoricalDataMovement", "migratingData(): array size is too long");
                return;
            }
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                try {
                    if (System.currentTimeMillis() - Long.parseLong(jSONObject.getString("eventtime")) <= Dates.MILLIS_PER_WEEK) {
                        String strKlm = klm(jSONObject.getString("properties"));
                        if (TextUtils.isEmpty(strKlm)) {
                            HiLog.i("HistoricalDataMovement", "content is empty");
                        } else {
                            Event event = new Event();
                            event.formJson(jSONObject);
                            event.setServiceTag(str);
                            event.setContent(strKlm);
                            this.klm.add(event);
                        }
                    }
                } catch (NumberFormatException unused) {
                }
            }
        }
    }

    @Override // java.lang.Runnable
    public final void run() throws NumberFormatException {
        HiLog.i("HistoricalDataMovement", "handler historical data...");
        lmn("stat_v2_1");
        lmn("cached_v2_1");
        Context context = this.lmn;
        dl.ijk(context, dl.lmn(context, "hyaline_v2_1"));
        if (this.klm.size() > 0) {
            HiLog.i("HistoricalDataMovement", "insert events...");
            try {
                bc.lmn(this.lmn).lmn(this.klm);
            } catch (bc.lmn unused) {
                HiLog.e("HistoricalDataMovement", "DBException");
            }
        }
    }
}
