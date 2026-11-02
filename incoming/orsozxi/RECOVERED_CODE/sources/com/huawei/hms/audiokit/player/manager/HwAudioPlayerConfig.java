package com.huawei.hms.audiokit.player.manager;

import android.content.Context;

/* loaded from: classes2.dex */
public final class HwAudioPlayerConfig {
    private int a;
    private boolean b;
    private String c;
    private String d;
    private Context e;
    private boolean f;

    public HwAudioPlayerConfig(Context context) {
        setContext(context);
    }

    public Context getContext() {
        return this.e;
    }

    public String getDebugPath() {
        return this.c;
    }

    public int getPlayCacheSize() {
        return this.a;
    }

    public String getReleasePath() {
        return this.d;
    }

    public boolean isDebugMode() {
        return this.f;
    }

    public boolean isHandleAudioFocus() {
        return this.b;
    }

    public HwAudioPlayerConfig setContext(Context context) {
        Context applicationContext = context.getApplicationContext();
        if (applicationContext != null) {
            context = applicationContext;
        }
        this.e = context;
        return this;
    }

    public HwAudioPlayerConfig setDebugMode(boolean z) {
        this.f = z;
        return this;
    }

    public HwAudioPlayerConfig setDebugPath(String str) {
        this.c = str;
        return this;
    }

    public HwAudioPlayerConfig setHandleAudioFocus(boolean z) {
        this.b = z;
        return this;
    }

    public HwAudioPlayerConfig setPlayCacheSize(int i) {
        this.a = i;
        return this;
    }

    public HwAudioPlayerConfig setReleasePath(String str) {
        this.d = str;
        return this;
    }
}
