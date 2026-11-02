package com.huawei.agconnect.applinking.a;

import android.net.Uri;
import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.applinking.AppLinkingException;
import com.huawei.agconnect.applinking.ShortAppLinking;
import com.huawei.agconnect.applinking.a.c.g;
import com.huawei.agconnect.applinking.a.c.h;
import com.huawei.agconnect.applinking.a.c.i;
import com.huawei.agconnect.applinking.a.c.j;
import com.huawei.agconnect.applinking.a.c.k;
import com.huawei.agconnect.applinking.a.c.l;
import com.huawei.agconnect.applinking.a.c.o;
import com.huawei.agconnect.common.api.BackendService;
import com.huawei.agconnect.common.api.Logger;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskCompletionSource;
import com.huawei.hmf.tasks.TaskExecutors;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class c {
    private final AGConnectInstance a;

    public c(AGConnectInstance aGConnectInstance) {
        this.a = aGConnectInstance;
    }

    private void a(Uri.Builder builder, JSONObject jSONObject, String str) {
        Object objOpt = jSONObject.opt(str);
        if (objOpt != null) {
            builder.appendQueryParameter(str, objOpt.toString());
        }
    }

    private com.huawei.agconnect.applinking.a.c.f c(JSONObject jSONObject) {
        com.huawei.agconnect.applinking.a.c.f fVar = new com.huawei.agconnect.applinking.a.c.f();
        fVar.setSuffixLength((String) jSONObject.opt("suffix"));
        long jOptLong = jSONObject.optLong("expire_time", 63072000L);
        if (jOptLong < 300) {
            jOptLong = 300;
        }
        fVar.setExpireTime(Long.valueOf(jOptLong));
        if (jSONObject.opt("long_app_linking") != null) {
            fVar.setLongUrl((String) jSONObject.opt("long_app_linking"));
        } else if (jSONObject.optJSONObject("parameters") != null) {
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("parameters");
            if (jSONObjectOptJSONObject != null) {
                l lVar = new l();
                lVar.setUriPrefix((String) jSONObject.opt("uri_prefix"));
                lVar.setDeepLink((String) jSONObjectOptJSONObject.opt("deeplink"));
                lVar.setAndroidInfoData(d(jSONObjectOptJSONObject));
                lVar.setHarmonyOSInfoData(e(jSONObjectOptJSONObject));
                lVar.setIosInfoData(h(jSONObjectOptJSONObject));
                lVar.setSocialCardData(f(jSONObjectOptJSONObject));
                lVar.setCampaignInfoData(g(jSONObjectOptJSONObject));
                lVar.setCustomPreviewInfoData(k(jSONObjectOptJSONObject));
                lVar.setPreviewType((Integer) jSONObjectOptJSONObject.opt("preview_type"));
                lVar.setLandingPageType((Integer) jSONObjectOptJSONObject.opt("landing_page_type"));
                fVar.setLinkInfoData(lVar);
            }
        } else {
            Logger.e("AppLinkingSDK", "call setUriPrefix() or setLongLink() first.");
        }
        return fVar;
    }

    private com.huawei.agconnect.applinking.a.c.a d(JSONObject jSONObject) {
        if (jSONObject.opt("android_deeplink") == null && jSONObject.opt("android_package_name") == null) {
            return null;
        }
        com.huawei.agconnect.applinking.a.c.a aVar = new com.huawei.agconnect.applinking.a.c.a();
        aVar.setDeepLink((String) jSONObject.opt("android_deeplink"));
        aVar.setPackageName((String) jSONObject.opt("android_package_name"));
        aVar.setOpenType((Integer) jSONObject.opt("android_open_type"));
        aVar.setFallbackUrl((String) jSONObject.opt("android_fallback_url"));
        return aVar;
    }

    private i e(JSONObject jSONObject) {
        if (jSONObject.opt("harmonyos_deeplink") == null && jSONObject.opt("harmonyos_package_name") == null) {
            return null;
        }
        i iVar = new i();
        iVar.setDeepLink((String) jSONObject.opt("harmonyos_deeplink"));
        iVar.setPackageName((String) jSONObject.opt("harmonyos_package_name"));
        iVar.setFallbackUrl((String) jSONObject.opt("harmonyos_fallback_url"));
        return iVar;
    }

    private o f(JSONObject jSONObject) {
        if (jSONObject.opt("social_title") == null && jSONObject.opt("social_desc") == null && jSONObject.opt("social_image") == null) {
            return null;
        }
        o oVar = new o();
        oVar.setTitle((String) jSONObject.opt("social_title"));
        oVar.setDescription((String) jSONObject.opt("social_desc"));
        oVar.setImageUrl((String) jSONObject.opt("social_image"));
        return oVar;
    }

    private com.huawei.agconnect.applinking.a.c.d g(JSONObject jSONObject) {
        if (jSONObject.opt("campaign_name") == null && jSONObject.opt("campaign_medium") == null && jSONObject.opt("campaign_channel") == null) {
            return null;
        }
        com.huawei.agconnect.applinking.a.c.d dVar = new com.huawei.agconnect.applinking.a.c.d();
        dVar.setName((String) jSONObject.opt("campaign_name"));
        dVar.setMedium((String) jSONObject.opt("campaign_medium"));
        dVar.setChannel((String) jSONObject.opt("campaign_channel"));
        return dVar;
    }

    private j h(JSONObject jSONObject) {
        if (jSONObject.opt("ios_link") == null && jSONObject.opt("ios_bundle_id") == null && jSONObject.opt("ipad_bundle_id") == null) {
            return null;
        }
        j jVar = new j();
        jVar.setDeepLink((String) jSONObject.opt("ios_link"));
        jVar.setBundleId((String) jSONObject.opt("ios_bundle_id"));
        jVar.setFallbackUrl((String) jSONObject.opt("ios_fallback_url"));
        jVar.setiPadInfoData(i(jSONObject));
        jVar.setAppStoreCampaignData(j(jSONObject));
        return jVar;
    }

    private k i(JSONObject jSONObject) {
        if (jSONObject.opt("ipad_bundle_id") == null) {
            return null;
        }
        k kVar = new k();
        kVar.setBundleId((String) jSONObject.opt("ipad_bundle_id"));
        kVar.setFallbackUrl((String) jSONObject.opt("ipad_fallback_url"));
        return kVar;
    }

    private com.huawei.agconnect.applinking.a.c.b j(JSONObject jSONObject) {
        if (jSONObject.opt("mt") == null && jSONObject.opt("ct") == null && jSONObject.opt("pt") == null && jSONObject.opt("at") == null) {
            return null;
        }
        com.huawei.agconnect.applinking.a.c.b bVar = new com.huawei.agconnect.applinking.a.c.b();
        bVar.setMediaType((String) jSONObject.opt("mt"));
        bVar.setCampaignToken((String) jSONObject.opt("ct"));
        bVar.setProviderToken((String) jSONObject.opt("pt"));
        bVar.setAffiliateToken((String) jSONObject.opt("at"));
        return bVar;
    }

    private h k(JSONObject jSONObject) {
        if (jSONObject.opt("android_preview_url") == null && jSONObject.opt("ios_preview_url") == null) {
            return null;
        }
        h hVar = new h();
        hVar.setAndroidPreviewUrl((String) jSONObject.opt("android_preview_url"));
        hVar.setIosPreviewUrl((String) jSONObject.opt("ios_preview_url"));
        return hVar;
    }

    public Task<ShortAppLinking> a(JSONObject jSONObject) {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        BackendService.sendRequest(c(jSONObject), 1, g.class, new BackendService.Options.Builder().clientToken(true).app(this.a).build()).addOnSuccessListener(TaskExecutors.immediate(), new OnSuccessListener<g>() { // from class: com.huawei.agconnect.applinking.a.c.2
            @Override // com.huawei.hmf.tasks.OnSuccessListener
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onSuccess(g gVar) {
                if (!gVar.isSuccess()) {
                    taskCompletionSource.setException(new AppLinkingException(gVar.getRetMsg(), gVar.getRetCode()));
                } else {
                    taskCompletionSource.setResult(new e(gVar.getShortLink(), gVar.getTestLink()));
                }
            }
        }).addOnFailureListener(TaskExecutors.immediate(), new OnFailureListener() { // from class: com.huawei.agconnect.applinking.a.c.1
            @Override // com.huawei.hmf.tasks.OnFailureListener
            public void onFailure(Exception exc) {
                taskCompletionSource.setException(exc);
            }
        });
        return taskCompletionSource.getTask();
    }

    public Uri b(JSONObject jSONObject) {
        String strOptString = jSONObject.optString("long_app_linking");
        if (strOptString.length() > 0) {
            return Uri.parse(strOptString);
        }
        Uri.Builder builder = new Uri.Builder();
        Uri uri = Uri.parse(jSONObject.optString("uri_prefix"));
        builder.scheme(uri.getScheme());
        builder.authority(uri.getAuthority());
        builder.path(uri.getPath());
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("parameters");
        if (jSONObjectOptJSONObject != null) {
            a(builder, jSONObjectOptJSONObject, "deeplink");
            a(builder, jSONObjectOptJSONObject, "android_deeplink");
            a(builder, jSONObjectOptJSONObject, "android_fallback_url");
            a(builder, jSONObjectOptJSONObject, "android_open_type");
            a(builder, jSONObjectOptJSONObject, "android_package_name");
            a(builder, jSONObjectOptJSONObject, "harmonyos_deeplink");
            a(builder, jSONObjectOptJSONObject, "harmonyos_fallback_url");
            a(builder, jSONObjectOptJSONObject, "harmonyos_package_name");
            a(builder, jSONObjectOptJSONObject, "campaign_channel");
            a(builder, jSONObjectOptJSONObject, "campaign_medium");
            a(builder, jSONObjectOptJSONObject, "campaign_name");
            a(builder, jSONObjectOptJSONObject, "ios_link");
            a(builder, jSONObjectOptJSONObject, "ios_bundle_id");
            a(builder, jSONObjectOptJSONObject, "ios_fallback_url");
            a(builder, jSONObjectOptJSONObject, "ipad_bundle_id");
            a(builder, jSONObjectOptJSONObject, "ipad_fallback_url");
            a(builder, jSONObjectOptJSONObject, "at");
            a(builder, jSONObjectOptJSONObject, "pt");
            a(builder, jSONObjectOptJSONObject, "ct");
            a(builder, jSONObjectOptJSONObject, "mt");
            a(builder, jSONObjectOptJSONObject, "preview_type");
            a(builder, jSONObjectOptJSONObject, "social_desc");
            a(builder, jSONObjectOptJSONObject, "social_image");
            a(builder, jSONObjectOptJSONObject, "social_title");
            a(builder, jSONObjectOptJSONObject, "android_preview_url");
            a(builder, jSONObjectOptJSONObject, "ios_preview_url");
            a(builder, jSONObjectOptJSONObject, "landing_page_type");
            a(builder, jSONObjectOptJSONObject, "region_id");
        }
        return builder.build();
    }
}
