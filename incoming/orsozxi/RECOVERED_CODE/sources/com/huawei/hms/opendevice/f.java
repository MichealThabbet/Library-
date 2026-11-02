package com.huawei.hms.opendevice;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.aaid.constant.ErrorEnum;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.Util;

/* compiled from: AutoInitRunnable.java */
/* loaded from: classes2.dex */
public class f implements Runnable {
    public Context a;

    public f(Context context) {
        this.a = context;
    }

    @Override // java.lang.Runnable
    public void run() throws PackageManager.NameNotFoundException {
        String token;
        ApplicationInfo applicationInfo;
        try {
            int internalCode = ErrorEnum.SUCCESS.getInternalCode();
            ApiException apiException = null;
            try {
                token = HmsInstanceId.getInstance(this.a).getToken(Util.getAppId(this.a), null);
            } catch (ApiException e) {
                e = e;
                token = null;
            }
            try {
                try {
                    HMSLog.i("AutoInit", "Push init succeed");
                    if (TextUtils.isEmpty(token)) {
                        return;
                    }
                } catch (ApiException e2) {
                    e = e2;
                    apiException = e;
                    internalCode = apiException.getStatusCode();
                    HMSLog.e("AutoInit", "new Push init failed");
                    applicationInfo = this.a.getPackageManager().getApplicationInfo(this.a.getPackageName(), 128);
                    if (applicationInfo.metaData != null) {
                    }
                    HMSLog.i("AutoInit", "push kit sdk not exists");
                    return;
                }
                applicationInfo = this.a.getPackageManager().getApplicationInfo(this.a.getPackageName(), 128);
                if (applicationInfo.metaData != null || applicationInfo.metaData.getString("com.huawei.hms.client.service.name:push") == null) {
                    HMSLog.i("AutoInit", "push kit sdk not exists");
                    return;
                }
                Intent intent = new Intent("com.huawei.push.action.MESSAGING_EVENT");
                intent.setPackage(this.a.getPackageName());
                Bundle bundle = new Bundle();
                bundle.putString("message_type", "new_token");
                bundle.putString(RemoteMessageConst.DEVICE_TOKEN, token);
                bundle.putInt("error", internalCode);
                if (apiException != null) {
                    bundle.putSerializable("exception_key", apiException);
                }
                if (new h().a(this.a, bundle, intent)) {
                    return;
                }
                HMSLog.e("AutoInit", "start service failed");
            } catch (PackageManager.NameNotFoundException unused) {
                HMSLog.i("AutoInit", "push kit sdk not exists");
            }
        } catch (Exception e3) {
            HMSLog.e("AutoInit", "Push init failed", e3);
        }
    }
}
