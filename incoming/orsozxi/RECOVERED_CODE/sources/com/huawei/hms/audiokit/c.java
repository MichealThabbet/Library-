package com.huawei.hms.audiokit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import net.sqlcipher.database.SQLiteDatabase;

/* loaded from: classes2.dex */
public final class c {
    public static boolean a(Context context, Intent intent) {
        if (context == null || intent == null) {
            Log.w("ActivityUtils", "Start activity, context is null or intent is null");
            return false;
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        }
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception unused) {
            Log.e("ActivityUtils", "startActivity Exception.");
            return false;
        }
    }
}
