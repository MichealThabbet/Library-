package com.huawei.agconnect.applinking;

import android.net.Uri;

/* loaded from: classes2.dex */
public abstract class ResolvedLinkData {

    public enum LinkType {
        AppLinking,
        UnifiedLinking
    }

    public abstract String getCampaignMedium();

    public abstract String getCampaignName();

    public abstract String getCampaignSource();

    public abstract long getClickTimestamp();

    public abstract Uri getDeepLink();

    public abstract String getInstallSource();

    public abstract LinkType getLinkType();

    @Deprecated
    public abstract long getMinimumAppVersion();

    public abstract String getSocialDescription();

    public abstract String getSocialImageUrl();

    public abstract String getSocialTitle();
}
