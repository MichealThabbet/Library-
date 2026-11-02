package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes.dex */
final class zzft {
    static void zza(Context context, String str, String str2, String str3) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(str, 0).edit();
        editorEdit.putString(str2, str3);
        editorEdit.apply();
    }
}
