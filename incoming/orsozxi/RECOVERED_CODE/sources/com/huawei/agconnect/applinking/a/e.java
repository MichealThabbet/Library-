package com.huawei.agconnect.applinking.a;

import android.net.Uri;
import com.huawei.agconnect.applinking.ShortAppLinking;

/* loaded from: classes2.dex */
public class e extends ShortAppLinking {
    private Uri a;
    private Uri b;

    public e(String str, String str2) {
        if (str != null) {
            this.a = Uri.parse(str);
        }
        if (str2 != null) {
            this.b = Uri.parse(str2);
        }
    }

    @Override // com.huawei.agconnect.applinking.ShortAppLinking
    public Uri getShortUrl() {
        return this.a;
    }

    @Override // com.huawei.agconnect.applinking.ShortAppLinking
    public Uri getTestUrl() {
        return this.b;
    }
}
