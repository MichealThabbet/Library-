package com.huawei.agconnect.applinking;

import android.net.Uri;
import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.applinking.ShortAppLinking;
import com.huawei.agconnect.applinking.a.c;
import com.huawei.agconnect.applinking.a.f;
import com.huawei.agconnect.common.api.Logger;
import com.huawei.hmf.tasks.Task;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class AppLinking {
    private final Uri uri;

    public static final class AndroidLinkInfo {
        private final JSONObject parameters;

        public enum AndroidOpenType {
            AppGallery,
            LocalMarket,
            CustomUrl
        }

        public static final class Builder {
            private final JSONObject parameters;

            public Builder() throws JSONException {
                JSONObject jSONObject = new JSONObject();
                this.parameters = jSONObject;
                try {
                    jSONObject.putOpt("android_package_name", AGConnectInstance.getInstance().getContext().getPackageName());
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
            }

            public Builder(String str) throws JSONException {
                JSONObject jSONObject = new JSONObject();
                this.parameters = jSONObject;
                try {
                    jSONObject.putOpt("android_package_name", str);
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
            }

            public AndroidLinkInfo build() {
                return new AndroidLinkInfo(this.parameters);
            }

            public Builder setAndroidDeepLink(String str) throws JSONException {
                try {
                    this.parameters.putOpt("android_deeplink", str);
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
                return this;
            }

            public Builder setFallbackUrl(String str) throws JSONException {
                try {
                    this.parameters.putOpt("android_fallback_url", str);
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
                return this;
            }

            @Deprecated
            public Builder setMinimumVersion(int i) {
                return this;
            }

            public Builder setOpenType(AndroidOpenType androidOpenType) throws JSONException {
                try {
                    Integer num = androidOpenType == AndroidOpenType.AppGallery ? 1 : null;
                    if (androidOpenType == AndroidOpenType.LocalMarket) {
                        num = 2;
                    }
                    if (androidOpenType == AndroidOpenType.CustomUrl) {
                        num = 3;
                    }
                    this.parameters.putOpt("android_open_type", num);
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
                return this;
            }
        }

        private AndroidLinkInfo(JSONObject jSONObject) {
            this.parameters = jSONObject;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public JSONObject getParameters() {
            return this.parameters;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public static Builder newBuilder(String str) {
            return new Builder(str);
        }
    }

    public static final class Builder {
        private final JSONObject appLinkingParameters;
        private final JSONObject builderParameters;
        private final c helper;

        public Builder() {
            this(AGConnectInstance.getInstance());
        }

        public Builder(AGConnectInstance aGConnectInstance) throws JSONException {
            this.builderParameters = new JSONObject();
            this.appLinkingParameters = new JSONObject();
            this.helper = new c(aGConnectInstance);
            try {
                this.builderParameters.putOpt("parameters", this.appLinkingParameters);
                this.appLinkingParameters.putOpt("region_id", f.a(aGConnectInstance.getOptions().getRoutePolicy()));
            } catch (JSONException e) {
                Logger.e("AppLinkingSDK", "json error", e);
            }
        }

        private void checkDomainUriPrefix() {
            Object objOpt = this.builderParameters.opt("long_app_linking");
            Object objOpt2 = this.builderParameters.opt("uri_prefix");
            if (objOpt == null && objOpt2 == null) {
                throw new IllegalArgumentException("call setUriPrefix() or setLongLink() first.");
            }
        }

        public AppLinking buildAppLinking() {
            checkDomainUriPrefix();
            return new AppLinking(this.helper.b(this.builderParameters));
        }

        public Task<ShortAppLinking> buildShortAppLinking() {
            return buildShortAppLinking(ShortAppLinking.LENGTH.SHORT);
        }

        public Task<ShortAppLinking> buildShortAppLinking(ShortAppLinking.LENGTH length) throws JSONException {
            checkDomainUriPrefix();
            try {
                this.builderParameters.put("suffix", length == ShortAppLinking.LENGTH.LONG ? "LONG" : "SHORT");
            } catch (JSONException e) {
                Logger.e("AppLinkingSDK", "json error", e);
            }
            return this.helper.a(this.builderParameters);
        }

        public Builder setAndroidLinkInfo(AndroidLinkInfo androidLinkInfo) throws JSONException {
            try {
                JSONObject parameters = androidLinkInfo.getParameters();
                Iterator<String> itKeys = parameters.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    this.appLinkingParameters.putOpt(next, parameters.opt(next));
                }
            } catch (JSONException e) {
                Logger.e("AppLinkingSDK", "json error", e);
            }
            return this;
        }

        public Builder setCampaignInfo(CampaignInfo campaignInfo) throws JSONException {
            try {
                JSONObject parameters = campaignInfo.getParameters();
                Iterator<String> itKeys = parameters.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    this.appLinkingParameters.putOpt(next, parameters.opt(next));
                }
            } catch (JSONException e) {
                Logger.e("AppLinkingSDK", "json error", e);
            }
            return this;
        }

        public Builder setDeepLink(Uri uri) throws JSONException {
            try {
                this.appLinkingParameters.putOpt("deeplink", uri.toString());
            } catch (JSONException e) {
                Logger.e("AppLinkingSDK", "json error", e);
            }
            return this;
        }

        public Builder setExpireMinute(long j) throws JSONException {
            try {
                this.builderParameters.putOpt("expire_time", Long.valueOf(j * 60));
            } catch (JSONException e) {
                Logger.e("AppLinkingSDK", "json error", e);
            }
            return this;
        }

        public Builder setHarmonyOSLinkInfo(HarmonyOSLinkInfo harmonyOSLinkInfo) throws JSONException {
            try {
                JSONObject parameters = harmonyOSLinkInfo.getParameters();
                Iterator<String> itKeys = parameters.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    this.appLinkingParameters.putOpt(next, parameters.opt(next));
                }
            } catch (JSONException e) {
                Logger.e("AppLinkingSDK", "json error", e);
            }
            return this;
        }

        public Builder setIOSLinkInfo(IOSLinkInfo iOSLinkInfo) throws JSONException {
            try {
                JSONObject parameters = iOSLinkInfo.getParameters();
                Iterator<String> itKeys = parameters.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    this.appLinkingParameters.putOpt(next, parameters.opt(next));
                }
            } catch (JSONException e) {
                Logger.e("AppLinkingSDK", "json error", e);
            }
            return this;
        }

        public Builder setIsShowPreview(boolean z) throws JSONException {
            try {
                this.appLinkingParameters.putOpt("landing_page_type", Integer.valueOf(z ? 1 : 2));
            } catch (JSONException e) {
                Logger.e("AppLinkingSDK", "json error", e);
            }
            return this;
        }

        public Builder setLongLink(Uri uri) throws JSONException {
            try {
                this.builderParameters.putOpt("long_app_linking", uri.toString());
            } catch (JSONException e) {
                Logger.e("AppLinkingSDK", "json error", e);
            }
            return this;
        }

        public Builder setPreviewType(LinkingPreviewType linkingPreviewType) throws JSONException {
            try {
                Integer num = linkingPreviewType == LinkingPreviewType.AppInfo ? 1 : null;
                if (linkingPreviewType == LinkingPreviewType.SocialInfo) {
                    num = 2;
                }
                this.appLinkingParameters.putOpt("preview_type", num);
            } catch (JSONException e) {
                Logger.e("AppLinkingSDK", "json error", e);
            }
            return this;
        }

        public Builder setSocialCardInfo(SocialCardInfo socialCardInfo) throws JSONException {
            try {
                JSONObject parameters = socialCardInfo.getParameters();
                Iterator<String> itKeys = parameters.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    this.appLinkingParameters.putOpt(next, parameters.opt(next));
                }
            } catch (JSONException e) {
                Logger.e("AppLinkingSDK", "json error", e);
            }
            return this;
        }

        public Builder setUriPrefix(String str) throws JSONException {
            try {
                this.builderParameters.putOpt("uri_prefix", str);
            } catch (JSONException e) {
                Logger.e("AppLinkingSDK", "json error", e);
            }
            return this;
        }
    }

    public static final class CampaignInfo {
        private final JSONObject parameters;

        public static final class Builder {
            private final JSONObject parameters = new JSONObject();

            public CampaignInfo build() {
                return new CampaignInfo(this.parameters);
            }

            public Builder setMedium(String str) throws JSONException {
                try {
                    this.parameters.putOpt("campaign_medium", str);
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
                return this;
            }

            public Builder setName(String str) throws JSONException {
                try {
                    this.parameters.putOpt("campaign_name", str);
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
                return this;
            }

            public Builder setSource(String str) throws JSONException {
                try {
                    this.parameters.putOpt("campaign_channel", str);
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
                return this;
            }
        }

        private CampaignInfo(JSONObject jSONObject) {
            this.parameters = jSONObject;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public JSONObject getParameters() {
            return this.parameters;
        }

        public static Builder newBuilder() {
            return new Builder();
        }
    }

    public static final class HarmonyOSLinkInfo {
        private final JSONObject parameters;

        public static final class Builder {
            private final JSONObject parameters = new JSONObject();

            public HarmonyOSLinkInfo build() {
                return new HarmonyOSLinkInfo(this.parameters);
            }

            public Builder setFallbackUrl(String str) throws JSONException {
                try {
                    this.parameters.putOpt("harmonyos_fallback_url", str);
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
                return this;
            }

            public Builder setHarmonyOSDeepLink(String str) throws JSONException {
                try {
                    this.parameters.putOpt("harmonyos_deeplink", str);
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
                return this;
            }

            public Builder setHarmonyOSPackageName(String str) throws JSONException {
                try {
                    this.parameters.putOpt("harmonyos_package_name", str);
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
                return this;
            }
        }

        private HarmonyOSLinkInfo(JSONObject jSONObject) {
            this.parameters = jSONObject;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public JSONObject getParameters() {
            return this.parameters;
        }

        public static Builder newBuilder() {
            return new Builder();
        }
    }

    public static final class IOSLinkInfo {
        private final JSONObject parameters;

        public static final class Builder {
            private final JSONObject parameters = new JSONObject();

            public IOSLinkInfo build() {
                return new IOSLinkInfo(this.parameters);
            }

            public Builder setBundleId(String str) throws JSONException {
                try {
                    this.parameters.putOpt("ios_bundle_id", str);
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
                return this;
            }

            public Builder setFallbackUrl(String str) throws JSONException {
                try {
                    this.parameters.putOpt("ios_fallback_url", str);
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
                return this;
            }

            public Builder setIOSDeepLink(String str) throws JSONException {
                try {
                    this.parameters.putOpt("ios_link", str);
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
                return this;
            }

            public Builder setIPadBundleId(String str) throws JSONException {
                try {
                    this.parameters.putOpt("ipad_bundle_id", str);
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
                return this;
            }

            public Builder setIPadFallbackUrl(String str) throws JSONException {
                try {
                    this.parameters.putOpt("ipad_fallback_url", str);
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
                return this;
            }

            public Builder setITunesConnectCampaignInfo(ITunesConnectCampaignInfo iTunesConnectCampaignInfo) throws JSONException {
                try {
                    JSONObject parameters = iTunesConnectCampaignInfo.getParameters();
                    Iterator<String> itKeys = parameters.keys();
                    while (itKeys.hasNext()) {
                        String next = itKeys.next();
                        this.parameters.putOpt(next, parameters.opt(next));
                    }
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
                return this;
            }
        }

        private IOSLinkInfo(JSONObject jSONObject) {
            this.parameters = jSONObject;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public JSONObject getParameters() {
            return this.parameters;
        }

        public static Builder newBuilder() {
            return new Builder();
        }
    }

    public static final class ITunesConnectCampaignInfo {
        private final JSONObject parameters;

        public static final class Builder {
            private final JSONObject parameters = new JSONObject();

            public ITunesConnectCampaignInfo build() {
                return new ITunesConnectCampaignInfo(this.parameters);
            }

            public Builder setAffiliateToken(String str) throws JSONException {
                try {
                    this.parameters.putOpt("at", str);
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
                return this;
            }

            public Builder setCampaignToken(String str) throws JSONException {
                try {
                    this.parameters.putOpt("ct", str);
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
                return this;
            }

            public Builder setMediaType(String str) throws JSONException {
                try {
                    this.parameters.putOpt("mt", str);
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
                return this;
            }

            public Builder setProviderToken(String str) throws JSONException {
                try {
                    this.parameters.putOpt("pt", str);
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
                return this;
            }
        }

        private ITunesConnectCampaignInfo(JSONObject jSONObject) {
            this.parameters = jSONObject;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public JSONObject getParameters() {
            return this.parameters;
        }

        public static Builder newBuilder() {
            return new Builder();
        }
    }

    public enum LinkingPreviewType {
        AppInfo,
        SocialInfo
    }

    public static final class SocialCardInfo {
        private final JSONObject parameters;

        public static final class Builder {
            private final JSONObject parameters = new JSONObject();

            public SocialCardInfo build() {
                return new SocialCardInfo(this.parameters);
            }

            public Builder setDescription(String str) throws JSONException {
                try {
                    this.parameters.putOpt("social_desc", str);
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
                return this;
            }

            public Builder setImageUrl(String str) throws JSONException {
                try {
                    this.parameters.putOpt("social_image", str);
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
                return this;
            }

            public Builder setTitle(String str) throws JSONException {
                try {
                    this.parameters.putOpt("social_title", str);
                } catch (JSONException e) {
                    Logger.e("AppLinkingSDK", "json error", e);
                }
                return this;
            }
        }

        private SocialCardInfo(JSONObject jSONObject) {
            this.parameters = jSONObject;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public JSONObject getParameters() {
            return this.parameters;
        }

        public static Builder newBuilder() {
            return new Builder();
        }
    }

    private AppLinking(Uri uri) {
        this.uri = uri;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(AGConnectInstance aGConnectInstance) {
        return new Builder(aGConnectInstance);
    }

    public Uri getUri() {
        return this.uri;
    }
}
