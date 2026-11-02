package com.huawei.hms.analytics.provider;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.huawei.hms.analytics.abc;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.dg;
import com.huawei.hms.analytics.di;
import com.huawei.hms.analytics.dl;
import com.huawei.hms.analytics.dq;

/* loaded from: classes2.dex */
public class AnalyticsInitializeProvider extends ContentProvider {
    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Context context;
        try {
            context = getContext();
        } catch (Throwable th) {
            Log.w("InitializeProvider", "onCreate error: " + th.getMessage());
            di.lmn(getContext(), th);
        }
        if (!dq.lmn().lmn(context)) {
            return false;
        }
        if (dl.klm(context, "global_v2", "is_analytics_initialized", 0) != 1 ? false : dl.klm(context, "global_v2", "is_analytics_enabled", true)) {
            abc.lmn().lmn(context.getApplicationContext() instanceof Application ? (Application) context.getApplicationContext() : dg.lmn());
            return false;
        }
        HiLog.w("HaProvider", "init check failed");
        return false;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
