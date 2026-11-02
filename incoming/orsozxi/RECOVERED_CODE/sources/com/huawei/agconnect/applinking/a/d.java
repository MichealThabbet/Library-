package com.huawei.agconnect.applinking.a;

import android.net.Uri;
import com.huawei.agconnect.applinking.ResolvedLinkData;
import com.huawei.agconnect.applinking.a.c.o;

/* loaded from: classes2.dex */
class d extends ResolvedLinkData {
    private ResolvedLinkData.LinkType a;
    private String b;
    private o c;
    private com.huawei.agconnect.applinking.a.c.d d;
    private long e;
    private String f;

    public static final class a {
        private ResolvedLinkData.LinkType a;
        private String b;
        private o c;
        private com.huawei.agconnect.applinking.a.c.d d;
        private long e;
        private String f;

        public a a(long j) {
            this.e = j;
            return this;
        }

        public a a(ResolvedLinkData.LinkType linkType) {
            this.a = linkType;
            return this;
        }

        public a a(com.huawei.agconnect.applinking.a.c.d dVar) {
            this.d = dVar;
            return this;
        }

        public a a(o oVar) {
            this.c = oVar;
            return this;
        }

        public a a(String str) {
            this.b = str;
            return this;
        }

        public d a() {
            d dVar = new d();
            dVar.a = this.a;
            dVar.b = this.b;
            dVar.c = this.c;
            dVar.d = this.d;
            dVar.e = this.e;
            dVar.f = this.f;
            return dVar;
        }

        public a b(String str) {
            this.f = str;
            return this;
        }
    }

    d() {
    }

    public static a a() {
        return new a();
    }

    @Override // com.huawei.agconnect.applinking.ResolvedLinkData
    public String getCampaignMedium() {
        com.huawei.agconnect.applinking.a.c.d dVar = this.d;
        if (dVar != null) {
            return dVar.getMedium();
        }
        return null;
    }

    @Override // com.huawei.agconnect.applinking.ResolvedLinkData
    public String getCampaignName() {
        com.huawei.agconnect.applinking.a.c.d dVar = this.d;
        if (dVar != null) {
            return dVar.getName();
        }
        return null;
    }

    @Override // com.huawei.agconnect.applinking.ResolvedLinkData
    public String getCampaignSource() {
        com.huawei.agconnect.applinking.a.c.d dVar = this.d;
        if (dVar != null) {
            return dVar.getChannel();
        }
        return null;
    }

    @Override // com.huawei.agconnect.applinking.ResolvedLinkData
    public long getClickTimestamp() {
        return this.e;
    }

    @Override // com.huawei.agconnect.applinking.ResolvedLinkData
    public Uri getDeepLink() {
        String str = this.b;
        if (str != null) {
            return Uri.parse(str);
        }
        return null;
    }

    @Override // com.huawei.agconnect.applinking.ResolvedLinkData
    public String getInstallSource() {
        return this.f;
    }

    @Override // com.huawei.agconnect.applinking.ResolvedLinkData
    public ResolvedLinkData.LinkType getLinkType() {
        return this.a;
    }

    @Override // com.huawei.agconnect.applinking.ResolvedLinkData
    public long getMinimumAppVersion() {
        return 0L;
    }

    @Override // com.huawei.agconnect.applinking.ResolvedLinkData
    public String getSocialDescription() {
        o oVar = this.c;
        if (oVar != null) {
            return oVar.getDescription();
        }
        return null;
    }

    @Override // com.huawei.agconnect.applinking.ResolvedLinkData
    public String getSocialImageUrl() {
        o oVar = this.c;
        if (oVar != null) {
            return oVar.getImageUrl();
        }
        return null;
    }

    @Override // com.huawei.agconnect.applinking.ResolvedLinkData
    public String getSocialTitle() {
        o oVar = this.c;
        if (oVar != null) {
            return oVar.getTitle();
        }
        return null;
    }
}
