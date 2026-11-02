package com.huawei.hms.analytics.receiver;

import android.content.Intent;
import android.os.Bundle;

/* loaded from: classes2.dex */
public class SafeIntent extends Intent {
    public SafeIntent(Intent intent) {
        super(intent == null ? new Intent() : intent);
    }

    @Override // android.content.Intent
    public String getAction() {
        try {
            String action = super.getAction();
            return action == null ? "" : action;
        } catch (Throwable unused) {
            return "";
        }
    }

    @Override // android.content.Intent
    public Bundle getExtras() {
        try {
            Bundle extras = super.getExtras();
            return extras == null ? new Bundle() : extras;
        } catch (Throwable unused) {
            return new Bundle();
        }
    }

    public final boolean lmn() {
        try {
            getStringExtra("ANYTHING");
            return false;
        } catch (Throwable unused) {
            return true;
        }
    }
}
