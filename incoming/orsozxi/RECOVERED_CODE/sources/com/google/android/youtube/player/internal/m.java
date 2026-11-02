package com.google.android.youtube.player.internal;

import android.content.Context;
import android.content.res.Resources;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes2.dex */
public final class m {
    public final String a;
    public final String b;
    public final String c;
    public final String d;
    public final String e;
    public final String f;
    public final String g;
    public final String h;
    public final String i;
    public final String j;

    public m(Context context) {
        Resources resources = context.getResources();
        Map<String, String> mapA = x.a((resources == null || resources.getConfiguration() == null || resources.getConfiguration().locale == null) ? Locale.getDefault() : resources.getConfiguration().locale);
        this.a = mapA.get("error_initializing_player");
        this.b = mapA.get("get_youtube_app_title");
        this.c = mapA.get("get_youtube_app_text");
        this.d = mapA.get("get_youtube_app_action");
        this.e = mapA.get("enable_youtube_app_title");
        this.f = mapA.get("enable_youtube_app_text");
        this.g = mapA.get("enable_youtube_app_action");
        this.h = mapA.get("update_youtube_app_title");
        this.i = mapA.get("update_youtube_app_text");
        this.j = mapA.get("update_youtube_app_action");
    }
}
