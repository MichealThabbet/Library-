package com.huawei.agconnect.applinking.a.b;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.agconnect.applinking.a.c.d;
import com.huawei.agconnect.applinking.a.c.m;
import com.huawei.agconnect.applinking.a.c.n;
import com.huawei.agconnect.common.api.HaConnector;
import com.huawei.agconnect.common.api.Logger;
import com.huawei.hms.analytics.type.HAParamType;
import java.net.URI;

/* loaded from: classes2.dex */
public class a {
    private static String a(String str) {
        String host;
        String queryParameter;
        String queryParameter2;
        Uri uri = Uri.parse(str);
        try {
            host = URI.create(str).getHost();
        } catch (IllegalArgumentException e) {
            Logger.e("AppLinkingSDK", "create uri fail ", e);
            host = null;
        }
        try {
            queryParameter = uri.getQueryParameter("deeplink");
            queryParameter2 = uri.getQueryParameter("region_id");
        } catch (NumberFormatException | UnsupportedOperationException unused) {
        }
        if (queryParameter != null && queryParameter2 != null && host != null) {
            return "https://" + host + "?long=1";
        }
        if (host != null && uri.getPathSegments().size() == 1) {
            return "https://" + host + "/" + uri.getPathSegments().get(0);
        }
        return "";
    }

    private void a(String str, d dVar, String str2) {
        Logger.d("AppLinkingSDK", "applinking report ha event : " + str);
        Bundle bundle = new Bundle();
        if (dVar != null && !TextUtils.isEmpty(dVar.getChannel())) {
            bundle.putString(HAParamType.SOURCE, dVar.getChannel());
        }
        if (dVar != null && !TextUtils.isEmpty(dVar.getMedium())) {
            bundle.putString(HAParamType.MEDIUM, dVar.getMedium());
        }
        if (dVar != null && !TextUtils.isEmpty(dVar.getName())) {
            bundle.putString(HAParamType.PROMOTIONNAME, dVar.getName());
        }
        if (!TextUtils.isEmpty(str2)) {
            bundle.putString(HAParamType.PRODUCTID, a(str2));
        }
        HaConnector.getInstance().onEvent(str, bundle);
    }

    public void a(m mVar, n nVar) {
        d campaignInfoData;
        String shortOrLongUrl;
        String str;
        if (HaConnector.getInstance().containHaInstance()) {
            if (mVar.isFirst()) {
                if ("UnifiedLinking".equals(nVar.getLinkType())) {
                    if (nVar.getUnifiedLinkInfo() == null) {
                        return;
                    }
                    campaignInfoData = nVar.getUnifiedLinkInfo().getCampaignInfoData();
                    shortOrLongUrl = nVar.getShortOrLongUrl();
                    str = "$FirstDownloadFromAppLink";
                } else {
                    if (nVar.getLinkInfoData() == null) {
                        return;
                    }
                    campaignInfoData = nVar.getLinkInfoData().getCampaignInfoData();
                    shortOrLongUrl = nVar.getShortOrLongUrl();
                    str = "$OpenAppFromAppLinking";
                }
            } else {
                if (nVar.getLinkInfoData() == null) {
                    return;
                }
                campaignInfoData = nVar.getLinkInfoData().getCampaignInfoData();
                shortOrLongUrl = nVar.getShortOrLongUrl();
                str = "$ReOpenAppFromAppLinking";
            }
            a(str, campaignInfoData, shortOrLongUrl);
        }
    }
}
