package com.huawei.agconnect.applinking;

import android.net.Uri;

/* loaded from: classes2.dex */
public abstract class ShortAppLinking {

    public enum LENGTH {
        SHORT,
        LONG
    }

    public abstract Uri getShortUrl();

    public abstract Uri getTestUrl();
}
