package com.huawei.hms.analytics;

import android.os.Bundle;
import com.huawei.hms.analytics.core.crypto.RandomUtil;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.type.HAParamType;
import com.ibm.icu.impl.locale.BaseLocale;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class dj {
    private final Map<String, String> bcd;
    private String cde;
    private String def;
    private boolean efg;
    private String fgh;
    private String ghi;
    private int hij;
    public JSONObject ijk;
    public final ArrayList<JSONObject> ikl;
    public Bundle klm;
    public Bundle lmn;

    public dj(String str, Bundle bundle) throws JSONException {
        this.hij = -1;
        this.def = null;
        this.cde = "_openness_config_tag";
        this.ikl = new ArrayList<>();
        this.bcd = new HashMap();
        lmn(str);
        this.lmn = bundle;
        JSONObject jSONObjectLmn = dm.lmn(bundle);
        lmn(jSONObjectLmn, this.ghi, this.fgh, "");
        this.ikl.add(jSONObjectLmn);
    }

    public dj(String str, String str2) {
        this.hij = -1;
        this.def = null;
        this.cde = "_openness_config_tag";
        this.ikl = new ArrayList<>();
        this.bcd = new HashMap();
        this.efg = false;
        this.cde = str2;
        lmn(str);
    }

    public dj(String str, JSONObject jSONObject) throws JSONException {
        this.hij = -1;
        this.def = null;
        this.cde = "_openness_config_tag";
        this.ikl = new ArrayList<>();
        this.bcd = new HashMap();
        lmn(str);
        lmn(jSONObject, str, this.fgh, "");
        this.ikl.add(jSONObject);
    }

    public dj(String str, boolean z) {
        this.hij = -1;
        this.def = null;
        this.cde = "_openness_config_tag";
        this.ikl = new ArrayList<>();
        this.bcd = new HashMap();
        this.efg = z;
        lmn(str);
    }

    private static List<ax> ghi(Bundle bundle) {
        ArrayList arrayList = new ArrayList(bundle.size());
        for (String str : bundle.keySet()) {
            if (str != null) {
                arrayList.add(new ax(str, bundle.get(str)));
            } else {
                HiLog.w("P_CEvtHandler", "event params key is null");
            }
        }
        Collections.sort(arrayList, new df());
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Bundle hij(Bundle bundle) throws JSONException {
        if (bundle == null) {
            return new Bundle();
        }
        this.ikl.clear();
        JSONObject jSONObject = new JSONObject();
        Bundle bundle2 = new Bundle();
        Iterator<ax> it2 = ghi(bundle).iterator();
        int i = 0;
        boolean z = false;
        boolean z2 = false;
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            ax next = it2.next();
            if (i >= 2048) {
                HiLog.w("P_CEvtHandler", HiLog.ErrorCode.PE005, "The bundle size exceeds the limit.Unnecessary data is discarded.Limit size: 2048");
                break;
            }
            Object obj = next.klm;
            if (obj instanceof Bundle) {
                if (!z) {
                    this.hij++;
                    bundle2.putBundle(next.lmn, klm(next.lmn, ghi((Bundle) obj)));
                    z = true;
                    i++;
                }
            } else if (obj instanceof List) {
                if (!z2) {
                    ArrayList<?> arrayListLmn = lmn(next.lmn, (List) obj);
                    lmn(jSONObject, next.lmn, this.def);
                    bundle2.putParcelableArrayList(next.lmn, arrayListLmn);
                    z2 = true;
                    i++;
                }
            } else if (!dc.lmn(obj)) {
                HiLog.w("P_CEvtHandler", "The current data type is invalid.Key:" + next.lmn);
            } else if (klm(next.lmn)) {
                bundle2.putSerializable(next.lmn, (Serializable) obj);
                lmn(jSONObject, next.lmn, obj);
                i++;
            }
        }
        lmn(jSONObject);
        this.ikl.add(jSONObject);
        return bundle2;
    }

    private Bundle ijk(Bundle bundle) {
        JSONObject jSONObject = this.ijk;
        if (jSONObject != null) {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                try {
                    String next = itKeys.next();
                    if (!bundle.containsKey(next)) {
                        bundle.putSerializable(next, (Serializable) this.ijk.get(next));
                    }
                } catch (Exception unused) {
                    HiLog.w("P_CEvtHandler", "def event params error");
                }
            }
        }
        return bundle;
    }

    private Bundle klm(String str, List<ax> list) throws JSONException {
        Bundle bundle = new Bundle();
        JSONObject jSONObject = new JSONObject();
        int i = 0;
        for (ax axVar : list) {
            if (i >= 2048) {
                break;
            }
            if (klm(axVar.lmn)) {
                i++;
                String str2 = axVar.lmn;
                Object obj = axVar.klm;
                if (dc.lmn(obj)) {
                    bundle.putSerializable(str2, (Serializable) obj);
                    lmn(jSONObject, str2, obj);
                } else {
                    HiLog.e("P_CEvtHandler", "bundle value is error");
                }
            }
        }
        String str3 = this.fgh;
        String str4 = "$sub_" + this.ghi + BaseLocale.SEP + str + BaseLocale.SEP + this.hij;
        String strGenerateSecureRandomStr = RandomUtil.getInstance().generateSecureRandomStr(16);
        String string = UUID.randomUUID().toString();
        String str5 = "$sub_evt_rel_" + this.hij;
        this.bcd.put(str5, string);
        lmn(jSONObject, str5, string);
        lmn(jSONObject, str4, strGenerateSecureRandomStr, str3);
        this.ikl.add(jSONObject);
        return bundle;
    }

    private boolean klm(String str) {
        if (!this.efg || dc.lmn("bundleKey", str, am.klm)) {
            return true;
        }
        HiLog.w("P_CEvtHandler", "bundle key check failed! ".concat(String.valueOf(str)));
        return false;
    }

    private ArrayList<?> lmn(String str, List<?> list) {
        ArrayList<?> arrayList = new ArrayList<>();
        int size = list.size();
        ArrayList arrayList2 = null;
        int i = 0;
        for (int i2 = 0; i2 < size && i < 50; i2++) {
            Object obj = list.get(i2);
            if (obj instanceof Bundle) {
                this.hij++;
                i++;
                arrayList.add(klm(str, ghi((Bundle) obj)));
            } else if (dc.lmn(obj)) {
                i++;
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList();
                }
                arrayList.add(obj);
                arrayList2.add(obj);
            }
        }
        if (arrayList2 != null) {
            this.def = new JSONArray((Collection) arrayList2).toString();
        }
        return arrayList;
    }

    private void lmn(String str) {
        this.ghi = str;
        this.fgh = RandomUtil.getInstance().generateSecureRandomStr(16);
    }

    private void lmn(JSONObject jSONObject) throws JSONException {
        for (Map.Entry<String, String> entry : this.bcd.entrySet()) {
            lmn(jSONObject, entry.getKey(), entry.getValue());
        }
        lmn(jSONObject, this.ghi, this.fgh, "");
    }

    private static void lmn(JSONObject jSONObject, String str, Object obj) throws JSONException {
        try {
            jSONObject.put(str, obj);
        } catch (JSONException unused) {
            HiLog.w("P_CEvtHandler", "put json exception ");
        }
    }

    private void lmn(JSONObject jSONObject, String str, String str2, String str3) throws JSONException {
        lmn(jSONObject, "^eventId", str);
        lmn(jSONObject, "^id", str2);
        lmn(jSONObject, "^pid", str3);
        if ("_openness_config_tag".equals(this.cde) || "AGC_TAG".equals(this.cde)) {
            lmn(jSONObject, HAParamType.TASKID, ar.lmn().lmn.e);
            if ("$EnterScreen".equals(str) || "$ExitScreen".equals(str)) {
                return;
            }
            Bundle bundle = bcd.lmn().cde;
            lmn(jSONObject, "$PrePageId", bundle.getString("$PrevActivityClass", ""));
            lmn(jSONObject, "$CurrPageId", bundle.getString("$CurActivityClass", ""));
        }
    }

    public final void ikl(Bundle bundle) {
        this.klm = ijk(bundle);
    }

    public final boolean klm(Bundle bundle) throws JSONException {
        Bundle bundleHij = hij(bundle);
        this.lmn = bundleHij;
        return bundleHij.toString().length() <= 204800;
    }

    public final boolean lmn(Bundle bundle) throws JSONException {
        ijk(bundle);
        Bundle bundleHij = hij(bundle);
        this.lmn = bundleHij;
        return bundleHij.toString().length() <= 204800;
    }
}
