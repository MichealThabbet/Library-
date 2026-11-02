package com.google.android.gms.internal.gtm;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/* loaded from: classes.dex */
public final class zzbh extends zzan {
    private volatile String zzut;
    private Future<String> zzyh;

    protected zzbh(zzap zzapVar) {
        super(zzapVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzan
    protected final void zzaw() {
    }

    public final String zzeh() {
        String str;
        zzdb();
        synchronized (this) {
            if (this.zzut == null) {
                this.zzyh = zzcq().zza(new zzbi(this));
            }
            if (this.zzyh != null) {
                try {
                    try {
                        this.zzut = this.zzyh.get();
                    } catch (ExecutionException e) {
                        zze("Failed to load or generate client id", e);
                        this.zzut = "0";
                    }
                } catch (InterruptedException e2) {
                    zzd("ClientId loading or generation was interrupted", e2);
                    this.zzut = "0";
                }
                if (this.zzut == null) {
                    this.zzut = "0";
                }
                zza("Loaded clientId", this.zzut);
                this.zzyh = null;
                str = this.zzut;
            } else {
                str = this.zzut;
            }
        }
        return str;
    }

    final String zzei() {
        synchronized (this) {
            this.zzut = null;
            this.zzyh = zzcq().zza(new zzbj(this));
        }
        return zzeh();
    }

    final String zzej() throws Throwable {
        String strZzd = zzd(zzcq().getContext());
        return strZzd == null ? zzek() : strZzd;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String zzek() {
        String lowerCase = UUID.randomUUID().toString().toLowerCase(Locale.US);
        try {
            return !zzb(zzcq().getContext(), lowerCase) ? "0" : lowerCase;
        } catch (Exception e) {
            zze("Error saving clientId file", e);
            return "0";
        }
    }

    /* JADX WARN: Not initialized variable reg: 3, insn: 0x0081: MOVE (r2 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:45:0x0081 */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0084 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final java.lang.String zzd(android.content.Context r9) throws java.lang.Throwable {
        /*
            r8 = this;
            java.lang.String r0 = "gaClientId"
            java.lang.String r1 = "Failed to close client id reading stream"
            java.lang.String r2 = "ClientId should be loaded from worker thread"
            com.google.android.gms.common.internal.Preconditions.checkNotMainThread(r2)
            r2 = 0
            java.io.FileInputStream r3 = r9.openFileInput(r0)     // Catch: java.lang.Throwable -> L69 java.io.IOException -> L6b java.io.FileNotFoundException -> L8d
            r4 = 36
            byte[] r5 = new byte[r4]     // Catch: java.io.IOException -> L65 java.io.FileNotFoundException -> L67 java.lang.Throwable -> L80
            r6 = 0
            int r4 = r3.read(r5, r6, r4)     // Catch: java.io.IOException -> L65 java.io.FileNotFoundException -> L67 java.lang.Throwable -> L80
            int r7 = r3.available()     // Catch: java.io.IOException -> L65 java.io.FileNotFoundException -> L67 java.lang.Throwable -> L80
            if (r7 <= 0) goto L33
            java.lang.String r4 = "clientId file seems corrupted, deleting it."
            r8.zzt(r4)     // Catch: java.io.IOException -> L65 java.io.FileNotFoundException -> L67 java.lang.Throwable -> L80
            r3.close()     // Catch: java.io.IOException -> L65 java.io.FileNotFoundException -> L67 java.lang.Throwable -> L80
            r9.deleteFile(r0)     // Catch: java.io.IOException -> L65 java.io.FileNotFoundException -> L67 java.lang.Throwable -> L80
            if (r3 == 0) goto L32
            r3.close()     // Catch: java.io.IOException -> L2e
            goto L32
        L2e:
            r9 = move-exception
            r8.zze(r1, r9)
        L32:
            return r2
        L33:
            r7 = 14
            if (r4 >= r7) goto L4d
            java.lang.String r4 = "clientId file is empty, deleting it."
            r8.zzt(r4)     // Catch: java.io.IOException -> L65 java.io.FileNotFoundException -> L67 java.lang.Throwable -> L80
            r3.close()     // Catch: java.io.IOException -> L65 java.io.FileNotFoundException -> L67 java.lang.Throwable -> L80
            r9.deleteFile(r0)     // Catch: java.io.IOException -> L65 java.io.FileNotFoundException -> L67 java.lang.Throwable -> L80
            if (r3 == 0) goto L4c
            r3.close()     // Catch: java.io.IOException -> L48
            goto L4c
        L48:
            r9 = move-exception
            r8.zze(r1, r9)
        L4c:
            return r2
        L4d:
            r3.close()     // Catch: java.io.IOException -> L65 java.io.FileNotFoundException -> L67 java.lang.Throwable -> L80
            java.lang.String r7 = new java.lang.String     // Catch: java.io.IOException -> L65 java.io.FileNotFoundException -> L67 java.lang.Throwable -> L80
            r7.<init>(r5, r6, r4)     // Catch: java.io.IOException -> L65 java.io.FileNotFoundException -> L67 java.lang.Throwable -> L80
            java.lang.String r4 = "Read client id from disk"
            r8.zza(r4, r7)     // Catch: java.io.IOException -> L65 java.io.FileNotFoundException -> L67 java.lang.Throwable -> L80
            if (r3 == 0) goto L64
            r3.close()     // Catch: java.io.IOException -> L60
            goto L64
        L60:
            r9 = move-exception
            r8.zze(r1, r9)
        L64:
            return r7
        L65:
            r4 = move-exception
            goto L6d
        L67:
            goto L8e
        L69:
            r9 = move-exception
            goto L82
        L6b:
            r4 = move-exception
            r3 = r2
        L6d:
            java.lang.String r5 = "Error reading client id file, deleting it"
            r8.zze(r5, r4)     // Catch: java.lang.Throwable -> L80
            r9.deleteFile(r0)     // Catch: java.lang.Throwable -> L80
            if (r3 == 0) goto L7f
            r3.close()     // Catch: java.io.IOException -> L7b
            goto L7f
        L7b:
            r9 = move-exception
            r8.zze(r1, r9)
        L7f:
            return r2
        L80:
            r9 = move-exception
            r2 = r3
        L82:
            if (r2 == 0) goto L8c
            r2.close()     // Catch: java.io.IOException -> L88
            goto L8c
        L88:
            r0 = move-exception
            r8.zze(r1, r0)
        L8c:
            throw r9
        L8d:
            r3 = r2
        L8e:
            if (r3 == 0) goto L98
            r3.close()     // Catch: java.io.IOException -> L94
            goto L98
        L94:
            r9 = move-exception
            r8.zze(r1, r9)
        L98:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzbh.zzd(android.content.Context):java.lang.String");
    }

    private final boolean zzb(Context context, String str) throws IOException {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotMainThread("ClientId should be saved from worker thread");
        FileOutputStream fileOutputStreamOpenFileOutput = null;
        try {
            try {
                try {
                    zza("Storing clientId", str);
                    fileOutputStreamOpenFileOutput = context.openFileOutput("gaClientId", 0);
                    fileOutputStreamOpenFileOutput.write(str.getBytes());
                    if (fileOutputStreamOpenFileOutput == null) {
                        return true;
                    }
                    try {
                        fileOutputStreamOpenFileOutput.close();
                        return true;
                    } catch (IOException e) {
                        zze("Failed to close clientId writing stream", e);
                        return true;
                    }
                } catch (FileNotFoundException e2) {
                    zze("Error creating clientId file", e2);
                    if (fileOutputStreamOpenFileOutput != null) {
                        try {
                            fileOutputStreamOpenFileOutput.close();
                        } catch (IOException e3) {
                            zze("Failed to close clientId writing stream", e3);
                        }
                    }
                    return false;
                }
            } catch (IOException e4) {
                zze("Error writing to clientId file", e4);
                if (fileOutputStreamOpenFileOutput != null) {
                    try {
                        fileOutputStreamOpenFileOutput.close();
                    } catch (IOException e5) {
                        zze("Failed to close clientId writing stream", e5);
                    }
                }
                return false;
            }
        } catch (Throwable th) {
            if (fileOutputStreamOpenFileOutput != null) {
                try {
                    fileOutputStreamOpenFileOutput.close();
                } catch (IOException e6) {
                    zze("Failed to close clientId writing stream", e6);
                }
            }
            throw th;
        }
    }
}
