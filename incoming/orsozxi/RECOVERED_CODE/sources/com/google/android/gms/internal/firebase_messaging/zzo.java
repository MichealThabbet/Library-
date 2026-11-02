package com.google.android.gms.internal.firebase_messaging;

import java.io.PrintStream;

/* compiled from: com.google.firebase:firebase-messaging@@21.1.0 */
/* loaded from: classes.dex */
public final class zzo {
    static final zzi zza;

    static {
        zzi zzmVar;
        Integer num = null;
        try {
            try {
                num = (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
            } catch (Exception e) {
                System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
                e.printStackTrace(System.err);
            }
            zzmVar = (num == null || num.intValue() < 19) ? !Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic") ? new zzl() : new zzm() : new zzn();
        } catch (Throwable th) {
            PrintStream printStream = System.err;
            String name = zzm.class.getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 133);
            sb.append("An error has occurred when initializing the try-with-resources desuguring strategy. The default strategy ");
            sb.append(name);
            sb.append("will be used. The error is: ");
            printStream.println(sb.toString());
            th.printStackTrace(System.err);
            zzmVar = new zzm();
        }
        zza = zzmVar;
        if (num == null) {
            return;
        }
        num.intValue();
    }

    public static void zza(Throwable th, Throwable th2) {
        zza.zza(th, th2);
    }
}
