package com.huawei.hms.analytics;

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.i;

/* loaded from: classes2.dex */
public final class ac extends ad {
    private boolean ijk;

    protected ac(Context context, i.lmn lmnVar) {
        super(context, lmnVar, new v(context, lmnVar));
        this.ijk = false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10, types: [int] */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v2, types: [android.content.ContentProviderClient] */
    /* JADX WARN: Type inference failed for: r1v3, types: [android.content.ContentProviderClient] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9 */
    private String ijk() throws Throwable {
        ContentProviderClient contentProviderClientAcquireContentProviderClient;
        Bundle bundleCall;
        String string = "";
        ?? r1 = 0;
        r1 = 0;
        try {
            try {
                contentProviderClientAcquireContentProviderClient = this.klm.getContentResolver().acquireContentProviderClient(Uri.parse("content://cn.nubia.identity/identity"));
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception unused) {
        }
        if (contentProviderClientAcquireContentProviderClient == null) {
            if (contentProviderClientAcquireContentProviderClient != null) {
                if (Build.VERSION.SDK_INT >= 24) {
                    contentProviderClientAcquireContentProviderClient.close();
                } else {
                    contentProviderClientAcquireContentProviderClient.release();
                }
            }
            return "";
        }
        try {
            bundleCall = contentProviderClientAcquireContentProviderClient.call("getOAID", null, null);
        } catch (Exception unused2) {
            r1 = contentProviderClientAcquireContentProviderClient;
            HiLog.w("NibOaid", "contentResolver query exception");
            if (r1 != 0) {
                if (Build.VERSION.SDK_INT >= 24) {
                    r1.close();
                } else {
                    r1.release();
                }
            }
            return string;
        } catch (Throwable th2) {
            th = th2;
            r1 = contentProviderClientAcquireContentProviderClient;
            if (r1 != 0) {
                if (Build.VERSION.SDK_INT >= 24) {
                    r1.close();
                } else {
                    r1.release();
                }
            }
            throw th;
        }
        if (bundleCall == null) {
            if (contentProviderClientAcquireContentProviderClient != null) {
                if (Build.VERSION.SDK_INT >= 24) {
                    contentProviderClientAcquireContentProviderClient.close();
                } else {
                    contentProviderClientAcquireContentProviderClient.release();
                }
            }
            return "";
        }
        if (bundleCall.getInt("code", -1) == 0) {
            string = bundleCall.getString("id");
            r1 = bundleCall;
        } else {
            r1 = 1;
            this.ijk = true;
        }
        if (contentProviderClientAcquireContentProviderClient != null) {
            r1 = Build.VERSION.SDK_INT;
            if (r1 >= 24) {
                contentProviderClientAcquireContentProviderClient.close();
            } else {
                contentProviderClientAcquireContentProviderClient.release();
            }
        }
        return string;
    }

    @Override // com.huawei.hms.analytics.ad
    public final boolean klm() {
        return Build.VERSION.SDK_INT >= 29;
    }

    @Override // com.huawei.hms.analytics.ad
    public final void lmn() throws Throwable {
        String strIjk = ijk();
        if (!TextUtils.isEmpty(strIjk) || this.ijk) {
            this.lmn.lmn(strIjk, "", "oaid");
        } else {
            lmn("oaid is empty, begin get gaid");
        }
    }
}
