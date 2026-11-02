package com.huawei.agconnect.credential.obs;

import android.content.Context;

/* loaded from: classes2.dex */
public class w {
    private static final w a = new w();
    private Context b;

    private w() {
    }

    public static w a() {
        return a;
    }

    public static synchronized void a(Context context) {
        Context applicationContext = context.getApplicationContext();
        w wVar = a;
        if (applicationContext != null) {
            context = applicationContext;
        }
        wVar.b = context;
    }

    public static String c() {
        return "1C4DE4EC";
    }

    public static String d() {
        return "80fedfd8941a368fafdae46750a4d367";
    }

    public Context b() {
        return this.b;
    }
}
