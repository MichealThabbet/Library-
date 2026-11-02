package com.huawei.agconnect.credential.obs;

import android.content.Context;
import com.huawei.agconnect.common.api.Logger;

/* loaded from: classes2.dex */
public class d {
    private Object a;

    public d(Context context) {
        try {
            this.a = Class.forName("com.huawei.hms.aaid.HmsInstanceId").getDeclaredMethod("getInstance", Context.class).invoke(null, context);
        } catch (Throwable unused) {
            Logger.w("HMSInstanceIdWrapper", "not find package HmsInstanceId");
        }
    }

    public boolean a() {
        return this.a != null;
    }

    public String b() {
        try {
            Logger.d("HMSInstanceIdWrapper", "call hms wrapper : getId");
            return (String) this.a.getClass().getDeclaredMethod("getId", new Class[0]).invoke(this.a, new Object[0]);
        } catch (Throwable unused) {
            Logger.e("HMSInstanceIdWrapper", "get id fail");
            return null;
        }
    }

    public long c() {
        try {
            Logger.d("HMSInstanceIdWrapper", "call hms wrapper : getCreationTime");
            return ((Long) this.a.getClass().getDeclaredMethod("getCreationTime", new Class[0]).invoke(this.a, new Object[0])).longValue();
        } catch (Throwable unused) {
            Logger.e("HMSInstanceIdWrapper", "get create time fail");
            return 0L;
        }
    }

    public void d() {
        try {
            Logger.d("HMSInstanceIdWrapper", "call hms wrapper : deleteAAID");
            this.a.getClass().getDeclaredMethod("deleteAAID", new Class[0]).invoke(this.a, new Object[0]);
        } catch (Throwable unused) {
            Logger.e("HMSInstanceIdWrapper", "delete aaid fail");
        }
    }
}
