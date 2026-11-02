package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.tagmanager.TagManager;

/* loaded from: classes.dex */
final class zzgb implements TagManager.zza {
    zzgb() {
    }

    @Override // com.google.android.gms.tagmanager.TagManager.zza
    public final zzy zza(Context context, TagManager tagManager, Looper looper, String str, int i, zzal zzalVar) {
        return new zzy(context, tagManager, looper, str, i, zzalVar);
    }
}
