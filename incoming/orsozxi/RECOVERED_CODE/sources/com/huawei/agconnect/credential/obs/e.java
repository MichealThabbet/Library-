package com.huawei.agconnect.credential.obs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

/* loaded from: classes2.dex */
public class e {
    protected SharedPreferences a;

    public e(Context context, String str) {
        if (context == null) {
            throw new NullPointerException("context is null!");
        }
        if (Build.VERSION.SDK_INT >= 24) {
            Context contextCreateDeviceProtectedStorageContext = context.createDeviceProtectedStorageContext();
            SharedPreferences sharedPreferences = contextCreateDeviceProtectedStorageContext.getSharedPreferences("move_to_de_records", 0);
            if (sharedPreferences.getBoolean(str, false)) {
                context = contextCreateDeviceProtectedStorageContext;
            } else if (contextCreateDeviceProtectedStorageContext.moveSharedPreferencesFrom(context, str)) {
                SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                editorEdit.putBoolean(str, true);
                editorEdit.apply();
                context = contextCreateDeviceProtectedStorageContext;
            }
        }
        this.a = context.getSharedPreferences(str, 0);
    }

    public String a(String str) {
        SharedPreferences sharedPreferences = this.a;
        return sharedPreferences != null ? sharedPreferences.getString(str, "") : "";
    }

    public void a(String str, Long l) {
        SharedPreferences.Editor editorEdit;
        SharedPreferences sharedPreferences = this.a;
        if (sharedPreferences == null || (editorEdit = sharedPreferences.edit()) == null) {
            return;
        }
        editorEdit.putLong(str, l.longValue()).commit();
    }

    public boolean a(String str, String str2) {
        SharedPreferences.Editor editorEdit;
        SharedPreferences sharedPreferences = this.a;
        if (sharedPreferences == null || (editorEdit = sharedPreferences.edit()) == null) {
            return false;
        }
        return editorEdit.putString(str, str2).commit();
    }

    public long b(String str) {
        SharedPreferences sharedPreferences = this.a;
        if (sharedPreferences != null) {
            return sharedPreferences.getLong(str, 0L);
        }
        return 0L;
    }

    public boolean c(String str) {
        SharedPreferences sharedPreferences = this.a;
        return sharedPreferences != null && sharedPreferences.contains(str);
    }

    public boolean d(String str) {
        SharedPreferences.Editor editorEdit;
        SharedPreferences sharedPreferences = this.a;
        if (sharedPreferences == null || !sharedPreferences.contains(str) || (editorEdit = this.a.edit()) == null) {
            return false;
        }
        return editorEdit.remove(str).commit();
    }
}
