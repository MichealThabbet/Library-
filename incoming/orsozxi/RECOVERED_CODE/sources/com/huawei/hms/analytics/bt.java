package com.huawei.hms.analytics;

import android.content.Context;
import android.os.Build;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.framework.config.CipherType;
import com.huawei.hms.analytics.framework.config.IMandatoryParameters;

/* loaded from: classes2.dex */
public final class bt implements IMandatoryParameters {
    @Override // com.huawei.hms.analytics.framework.config.IMandatoryParameters
    public final String getAppVer() {
        return ar.lmn().lmn.klm;
    }

    @Override // com.huawei.hms.analytics.framework.config.IMandatoryParameters
    public final CipherType getCipherType() {
        return Build.VERSION.SDK_INT >= 19 ? CipherType.AESGCM : CipherType.AESCBC;
    }

    @Override // com.huawei.hms.analytics.framework.config.IMandatoryParameters
    public final Context getContext() {
        return ar.lmn().lmn.ghi;
    }

    @Override // com.huawei.hms.analytics.framework.config.IMandatoryParameters
    public final String getLoadWorkKey() {
        return bo.lmn().klm();
    }

    @Override // com.huawei.hms.analytics.framework.config.IMandatoryParameters
    public final String getModel() {
        return Build.MODEL;
    }

    @Override // com.huawei.hms.analytics.framework.config.IMandatoryParameters
    public final String getPubKeyVersion(String str) {
        return ar.lmn().lmn.lmn(str);
    }

    @Override // com.huawei.hms.analytics.framework.config.IMandatoryParameters
    public final byte[] getRsaPublicKey(String str) {
        String str2 = ar.lmn().lmn.f.get(str);
        if (str2 == null || str2.isEmpty()) {
            return new byte[0];
        }
        byte[] bArr = new byte[0];
        try {
            return da.lmn(str2);
        } catch (Exception unused) {
            HiLog.e("OpennessDutyParams", "decode failed");
            return bArr;
        }
    }
}
