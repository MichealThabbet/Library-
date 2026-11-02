package com.huawei.hms.framework.network.grs.g;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.framework.common.StringUtils;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import org.json.JSONException;
import org.slf4j.Logger;

/* loaded from: classes2.dex */
public class b {
    private final Context a;
    private final GrsBaseInfo b;
    private final com.huawei.hms.framework.network.grs.e.a c;

    public b(Context context, com.huawei.hms.framework.network.grs.e.a aVar, GrsBaseInfo grsBaseInfo) {
        this.a = context;
        this.b = grsBaseInfo;
        this.c = aVar;
    }

    public String a(boolean z) {
        String strA;
        String str = com.huawei.hms.framework.network.grs.a.a(this.c.a().a("geoipCountryCode", ""), "geoip.countrycode").get(Logger.ROOT_LOGGER_NAME);
        com.huawei.hms.framework.common.Logger.i("GeoipCountry", "geoIpCountry is: " + str);
        String strA2 = this.c.a().a("geoipCountryCodetime", "0");
        long j = 0;
        if (!TextUtils.isEmpty(strA2) && strA2.matches("\\d+")) {
            try {
                j = Long.parseLong(strA2);
            } catch (NumberFormatException e) {
                com.huawei.hms.framework.common.Logger.w("GeoipCountry", "convert urlParamKey from String to Long catch NumberFormatException.", e);
            }
        }
        if (TextUtils.isEmpty(str) || com.huawei.hms.framework.network.grs.h.e.a(Long.valueOf(j))) {
            com.huawei.hms.framework.network.grs.g.k.c cVar = new com.huawei.hms.framework.network.grs.g.k.c(this.b, this.a);
            cVar.a("geoip.countrycode");
            com.huawei.hms.framework.network.grs.e.c cVarC = this.c.c();
            if (cVarC != null) {
                try {
                    strA = i.a(cVarC.a("services", ""), cVar.c());
                } catch (JSONException e2) {
                    com.huawei.hms.framework.common.Logger.w("GeoipCountry", "getGeoipCountry merge services occure jsonException. %s", StringUtils.anonymizeMessage(e2.getMessage()));
                    strA = null;
                }
                if (!TextUtils.isEmpty(strA)) {
                    cVarC.b("services", strA);
                }
            }
            if (z) {
                d dVarA = this.c.b().a(cVar, "geoip.countrycode", cVarC);
                if (dVarA != null) {
                    str = com.huawei.hms.framework.network.grs.a.a(dVarA.j(), "geoip.countrycode").get(Logger.ROOT_LOGGER_NAME);
                }
                com.huawei.hms.framework.common.Logger.i("GeoipCountry", "sync request to query geoip.countrycode is:" + str);
            } else {
                com.huawei.hms.framework.common.Logger.i("GeoipCountry", "async request to query geoip.countrycode");
                this.c.b().a(cVar, null, "geoip.countrycode", cVarC);
            }
        }
        return str;
    }
}
