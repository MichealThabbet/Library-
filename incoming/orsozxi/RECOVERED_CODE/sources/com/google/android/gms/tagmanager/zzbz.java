package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.gtm.zzpf;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes.dex */
final class zzbz extends Thread implements zzby {
    private static zzbz zzahd;
    private volatile boolean closed;
    private final LinkedBlockingQueue<Runnable> zzahb;
    private volatile boolean zzahc;
    private volatile zzcb zzahe;
    private final Context zzrm;

    static zzbz zzm(Context context) {
        if (zzahd == null) {
            zzahd = new zzbz(context);
        }
        return zzahd;
    }

    private zzbz(Context context) {
        super("GAThread");
        this.zzahb = new LinkedBlockingQueue<>();
        this.zzahc = false;
        this.closed = false;
        if (context != null) {
            this.zzrm = context.getApplicationContext();
        } else {
            this.zzrm = context;
        }
        start();
    }

    @Override // com.google.android.gms.tagmanager.zzby
    public final void zzbd(String str) {
        zzc(new zzca(this, this, System.currentTimeMillis(), str));
    }

    @Override // com.google.android.gms.tagmanager.zzby
    public final void zzc(Runnable runnable) {
        this.zzahb.add(runnable);
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() throws InterruptedException {
        while (true) {
            try {
                try {
                    Runnable runnableTake = this.zzahb.take();
                    if (!this.zzahc) {
                        runnableTake.run();
                    }
                } catch (InterruptedException e) {
                    zzdi.zzaw(e.toString());
                }
            } catch (Exception e2) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                PrintStream printStream = new PrintStream(byteArrayOutputStream);
                zzpf.zza(e2, printStream);
                printStream.flush();
                String str = new String(byteArrayOutputStream.toByteArray());
                zzdi.zzav(str.length() != 0 ? "Error on Google TagManager Thread: ".concat(str) : new String("Error on Google TagManager Thread: "));
                zzdi.zzav("Google TagManager is shutting down.");
                this.zzahc = true;
            }
        }
    }
}
