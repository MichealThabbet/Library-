package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;
import java.net.URL;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.0 */
/* loaded from: classes.dex */
final class zzhz implements Runnable {
    final /* synthetic */ zzia zza;
    private final URL zzb;
    private final String zzc;
    private final zzfs zzd;

    public zzhz(zzia zziaVar, String str, URL url, byte[] bArr, Map map, zzfs zzfsVar, byte[] bArr2) {
        this.zza = zziaVar;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(url);
        Preconditions.checkNotNull(zzfsVar);
        this.zzb = url;
        this.zzd = zzfsVar;
        this.zzc = str;
    }

    private final void zzb(final int i, final Exception exc, final byte[] bArr, final Map<String, List<String>> map) throws IllegalStateException {
        this.zza.zzs.zzav().zzh(new Runnable(this, i, exc, bArr, map) { // from class: com.google.android.gms.measurement.internal.zzhy
            private final zzhz zza;
            private final int zzb;
            private final Exception zzc;
            private final byte[] zzd;
            private final Map zze;

            {
                this.zza = this;
                this.zzb = i;
                this.zzc = exc;
                this.zzd = bArr;
                this.zze = map;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zza(this.zzb, this.zzc, this.zzd, this.zze);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0073  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void run() throws java.lang.Throwable {
        /*
            r9 = this;
            com.google.android.gms.measurement.internal.zzia r0 = r9.zza
            r0.zzaw()
            r0 = 0
            r1 = 0
            com.google.android.gms.measurement.internal.zzia r2 = r9.zza     // Catch: java.lang.Throwable -> L5e java.io.IOException -> L6c
            java.net.URL r3 = r9.zzb     // Catch: java.lang.Throwable -> L5e java.io.IOException -> L6c
            java.net.HttpURLConnection r2 = r2.zzd(r3)     // Catch: java.lang.Throwable -> L5e java.io.IOException -> L6c
            int r3 = r2.getResponseCode()     // Catch: java.lang.Throwable -> L54 java.io.IOException -> L59
            java.util.Map r4 = r2.getHeaderFields()     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L51
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L42
            r5.<init>()     // Catch: java.lang.Throwable -> L42
            java.io.InputStream r6 = r2.getInputStream()     // Catch: java.lang.Throwable -> L42
            r7 = 1024(0x400, float:1.435E-42)
            byte[] r7 = new byte[r7]     // Catch: java.lang.Throwable -> L40
        L24:
            int r8 = r6.read(r7)     // Catch: java.lang.Throwable -> L40
            if (r8 <= 0) goto L2e
            r5.write(r7, r0, r8)     // Catch: java.lang.Throwable -> L40
            goto L24
        L2e:
            byte[] r0 = r5.toByteArray()     // Catch: java.lang.Throwable -> L40
            if (r6 == 0) goto L37
            r6.close()     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4c
        L37:
            if (r2 == 0) goto L3c
            r2.disconnect()
        L3c:
            r9.zzb(r3, r1, r0, r4)
            return
        L40:
            r0 = move-exception
            goto L44
        L42:
            r0 = move-exception
            r6 = r1
        L44:
            if (r6 == 0) goto L49
            r6.close()     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4c
        L49:
            throw r0     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4c
        L4a:
            r0 = move-exception
            goto L63
        L4c:
            r0 = move-exception
            goto L71
        L4e:
            r0 = move-exception
            r4 = r1
            goto L63
        L51:
            r0 = move-exception
            r4 = r1
            goto L71
        L54:
            r3 = move-exception
            r4 = r1
            r0 = r3
            r3 = 0
            goto L63
        L59:
            r3 = move-exception
            r4 = r1
            r0 = r3
            r3 = 0
            goto L71
        L5e:
            r2 = move-exception
            r4 = r1
            r0 = r2
            r3 = 0
            r2 = r4
        L63:
            if (r2 == 0) goto L68
            r2.disconnect()
        L68:
            r9.zzb(r3, r1, r1, r4)
            throw r0
        L6c:
            r2 = move-exception
            r4 = r1
            r0 = r2
            r3 = 0
            r2 = r4
        L71:
            if (r2 == 0) goto L76
            r2.disconnect()
        L76:
            r9.zzb(r3, r0, r1, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzhz.run():void");
    }

    final /* synthetic */ void zza(int i, Exception exc, byte[] bArr, Map map) {
        this.zzd.zza(this.zzc, i, exc, bArr, map);
    }
}
