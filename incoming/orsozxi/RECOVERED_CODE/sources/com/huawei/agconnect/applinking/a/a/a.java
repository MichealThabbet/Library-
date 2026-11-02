package com.huawei.agconnect.applinking.a.a;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import com.huawei.agconnect.applinking.AppLinkingException;
import com.huawei.agconnect.applinking.ReferrerProvider;
import com.huawei.agconnect.common.appinfo.SafeAppInfo;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.Tasks;
import java.util.concurrent.Callable;

/* loaded from: classes2.dex */
class a implements ReferrerProvider {
    a() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(Context context) {
        return "3BAF59A2E5331C30675FAB35FF5FFF0D116142D3D4664F1C3CB804068B40614F".equals(d.a(context, "com.huawei.appmarket"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(Context context) {
        PackageInfo packageInfoSafeGetPackageInfo = SafeAppInfo.safeGetPackageInfo(context.getPackageManager(), "com.huawei.appmarket", 16384);
        if (packageInfoSafeGetPackageInfo != null) {
            return (Build.VERSION.SDK_INT >= 28 ? packageInfoSafeGetPackageInfo.getLongVersionCode() : (long) packageInfoSafeGetPackageInfo.versionCode) >= 100400300;
        }
        return false;
    }

    @Override // com.huawei.agconnect.applinking.ReferrerProvider
    public Task<String> getCustomReferrer(final Context context) {
        return Tasks.callInBackground(new Callable<String>() { // from class: com.huawei.agconnect.applinking.a.a.a.1
            @Override // java.util.concurrent.Callable
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String call() throws Throwable {
                if (!a.this.b(context)) {
                    throw new AppLinkingException("not find allowable app gallery package", 102);
                }
                Cursor cursor = null;
                if (d.b(context, "com.huawei.appmarket") || a.this.a(context)) {
                    try {
                        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.huawei.appmarket.commondata/item/5"), null, null, new String[]{context.getPackageName()}, null);
                        if (cursorQuery != null) {
                            try {
                                cursorQuery.moveToFirst();
                                String strA = new b(cursorQuery.getString(0), Long.parseLong(cursorQuery.getString(1)), Long.parseLong(cursorQuery.getString(3)), Long.parseLong(cursorQuery.getString(2)), cursorQuery.getString(4), cursorQuery.getString(5)).a();
                                if (cursorQuery != null) {
                                    cursorQuery.close();
                                }
                                return strA;
                            } catch (Throwable th) {
                                th = th;
                                cursor = cursorQuery;
                                if (cursor != null) {
                                    cursor.close();
                                }
                                throw th;
                            }
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                }
                return null;
            }
        });
    }
}
