package com.huawei.agconnect.credential.obs;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.common.api.AAID;
import com.huawei.agconnect.common.api.AgcCrypto;
import com.huawei.agconnect.datastore.annotation.SharedPreference;

/* loaded from: classes2.dex */
public class a implements AAID {

    @SharedPreference(crypto = AgcCrypto.class, fileName = "com.huawei.agconnect", key = "aaid")
    String aaidString;

    @Override // com.huawei.agconnect.common.api.AAID
    public String getId() {
        if (!TextUtils.isEmpty(this.aaidString)) {
            return this.aaidString;
        }
        b.a().c(this);
        if (!TextUtils.isEmpty(this.aaidString)) {
            return this.aaidString;
        }
        Context context = AGConnectInstance.getInstance().getContext();
        this.aaidString = c.a(context.getPackageName() + c.a(context));
        b.a().a(this);
        return this.aaidString;
    }
}
