package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.internal.gtm.zzop;
import com.google.android.gms.internal.gtm.zzor;
import com.google.android.gms.internal.gtm.zzov;
import com.google.android.gms.internal.gtm.zzoz;
import com.google.android.gms.internal.gtm.zzuv;
import com.google.android.gms.internal.gtm.zzuw;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* loaded from: classes.dex */
final class zzex implements zzah {
    private final String zzaec;
    private zzdh<zzop> zzajf;
    private final ExecutorService zzajm = com.google.android.gms.internal.gtm.zzdf.zzgp().zzr(com.google.android.gms.internal.gtm.zzdi.zzadg);
    private final Context zzrm;

    zzex(Context context, String str) {
        this.zzrm = context;
        this.zzaec = str;
    }

    @Override // com.google.android.gms.tagmanager.zzah
    public final void zza(zzdh<zzop> zzdhVar) {
        this.zzajf = zzdhVar;
    }

    @Override // com.google.android.gms.tagmanager.zzah
    public final void zzhk() {
        this.zzajm.execute(new zzey(this));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final void zzjd() {
        /*
            r4 = this;
            java.lang.String r0 = "Error closing stream for reading resource from disk"
            com.google.android.gms.tagmanager.zzdh<com.google.android.gms.internal.gtm.zzop> r1 = r4.zzajf
            if (r1 == 0) goto Lc0
            r1.zzhj()
            java.lang.String r1 = "Attempting to load resource from disk"
            com.google.android.gms.tagmanager.zzdi.zzab(r1)
            com.google.android.gms.tagmanager.zzeh r1 = com.google.android.gms.tagmanager.zzeh.zziy()
            com.google.android.gms.tagmanager.zzeh$zza r1 = r1.zziz()
            com.google.android.gms.tagmanager.zzeh$zza r2 = com.google.android.gms.tagmanager.zzeh.zza.CONTAINER
            if (r1 == r2) goto L26
            com.google.android.gms.tagmanager.zzeh r1 = com.google.android.gms.tagmanager.zzeh.zziy()
            com.google.android.gms.tagmanager.zzeh$zza r1 = r1.zziz()
            com.google.android.gms.tagmanager.zzeh$zza r2 = com.google.android.gms.tagmanager.zzeh.zza.CONTAINER_DEBUG
            if (r1 != r2) goto L3e
        L26:
            java.lang.String r1 = r4.zzaec
            com.google.android.gms.tagmanager.zzeh r2 = com.google.android.gms.tagmanager.zzeh.zziy()
            java.lang.String r2 = r2.getContainerId()
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L3e
            com.google.android.gms.tagmanager.zzdh<com.google.android.gms.internal.gtm.zzop> r0 = r4.zzajf
            int r1 = com.google.android.gms.tagmanager.zzcz.zzaht
            r0.zzs(r1)
            return
        L3e:
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.io.FileNotFoundException -> Lb3
            java.io.File r2 = r4.zzje()     // Catch: java.io.FileNotFoundException -> Lb3
            r1.<init>(r2)     // Catch: java.io.FileNotFoundException -> Lb3
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L7c java.lang.IllegalArgumentException -> L7e java.io.IOException -> L92
            r2.<init>()     // Catch: java.lang.Throwable -> L7c java.lang.IllegalArgumentException -> L7e java.io.IOException -> L92
            com.google.android.gms.internal.gtm.zzor.zza(r1, r2)     // Catch: java.lang.Throwable -> L7c java.lang.IllegalArgumentException -> L7e java.io.IOException -> L92
            byte[] r2 = r2.toByteArray()     // Catch: java.lang.Throwable -> L7c java.lang.IllegalArgumentException -> L7e java.io.IOException -> L92
            com.google.android.gms.internal.gtm.zzop r3 = new com.google.android.gms.internal.gtm.zzop     // Catch: java.lang.Throwable -> L7c java.lang.IllegalArgumentException -> L7e java.io.IOException -> L92
            r3.<init>()     // Catch: java.lang.Throwable -> L7c java.lang.IllegalArgumentException -> L7e java.io.IOException -> L92
            com.google.android.gms.internal.gtm.zzuw r2 = com.google.android.gms.internal.gtm.zzuw.zza(r3, r2)     // Catch: java.lang.Throwable -> L7c java.lang.IllegalArgumentException -> L7e java.io.IOException -> L92
            com.google.android.gms.internal.gtm.zzop r2 = (com.google.android.gms.internal.gtm.zzop) r2     // Catch: java.lang.Throwable -> L7c java.lang.IllegalArgumentException -> L7e java.io.IOException -> L92
            com.google.android.gms.internal.gtm.zzi r3 = r2.zzqk     // Catch: java.lang.Throwable -> L7c java.lang.IllegalArgumentException -> L7e java.io.IOException -> L92
            if (r3 != 0) goto L6f
            com.google.android.gms.internal.gtm.zzk r3 = r2.zzauy     // Catch: java.lang.Throwable -> L7c java.lang.IllegalArgumentException -> L7e java.io.IOException -> L92
            if (r3 == 0) goto L67
            goto L6f
        L67:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException     // Catch: java.lang.Throwable -> L7c java.lang.IllegalArgumentException -> L7e java.io.IOException -> L92
            java.lang.String r3 = "Resource and SupplementedResource are NULL."
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L7c java.lang.IllegalArgumentException -> L7e java.io.IOException -> L92
            throw r2     // Catch: java.lang.Throwable -> L7c java.lang.IllegalArgumentException -> L7e java.io.IOException -> L92
        L6f:
            com.google.android.gms.tagmanager.zzdh<com.google.android.gms.internal.gtm.zzop> r3 = r4.zzajf     // Catch: java.lang.Throwable -> L7c java.lang.IllegalArgumentException -> L7e java.io.IOException -> L92
            r3.zze(r2)     // Catch: java.lang.Throwable -> L7c java.lang.IllegalArgumentException -> L7e java.io.IOException -> L92
            r1.close()     // Catch: java.io.IOException -> L78
            goto La5
        L78:
            com.google.android.gms.tagmanager.zzdi.zzac(r0)
            goto La5
        L7c:
            r2 = move-exception
            goto Lab
        L7e:
            com.google.android.gms.tagmanager.zzdh<com.google.android.gms.internal.gtm.zzop> r2 = r4.zzajf     // Catch: java.lang.Throwable -> L7c
            int r3 = com.google.android.gms.tagmanager.zzcz.zzahu     // Catch: java.lang.Throwable -> L7c
            r2.zzs(r3)     // Catch: java.lang.Throwable -> L7c
            java.lang.String r2 = "Failed to read the resource from disk. The resource is inconsistent"
            com.google.android.gms.tagmanager.zzdi.zzac(r2)     // Catch: java.lang.Throwable -> L7c
            r1.close()     // Catch: java.io.IOException -> L8e
            goto La5
        L8e:
            com.google.android.gms.tagmanager.zzdi.zzac(r0)
            goto La5
        L92:
            com.google.android.gms.tagmanager.zzdh<com.google.android.gms.internal.gtm.zzop> r2 = r4.zzajf     // Catch: java.lang.Throwable -> L7c
            int r3 = com.google.android.gms.tagmanager.zzcz.zzahu     // Catch: java.lang.Throwable -> L7c
            r2.zzs(r3)     // Catch: java.lang.Throwable -> L7c
            java.lang.String r2 = "Failed to read the resource from disk"
            com.google.android.gms.tagmanager.zzdi.zzac(r2)     // Catch: java.lang.Throwable -> L7c
            r1.close()     // Catch: java.io.IOException -> La2
            goto La5
        La2:
            com.google.android.gms.tagmanager.zzdi.zzac(r0)
        La5:
            java.lang.String r0 = "The Disk resource was successfully read."
            com.google.android.gms.tagmanager.zzdi.zzab(r0)
            return
        Lab:
            r1.close()     // Catch: java.io.IOException -> Laf
            goto Lb2
        Laf:
            com.google.android.gms.tagmanager.zzdi.zzac(r0)
        Lb2:
            throw r2
        Lb3:
            java.lang.String r0 = "Failed to find the resource in the disk"
            com.google.android.gms.tagmanager.zzdi.zzax(r0)
            com.google.android.gms.tagmanager.zzdh<com.google.android.gms.internal.gtm.zzop> r0 = r4.zzajf
            int r1 = com.google.android.gms.tagmanager.zzcz.zzaht
            r0.zzs(r1)
            return
        Lc0:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Callback must be set before execute"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzex.zzjd():void");
    }

    @Override // com.google.android.gms.tagmanager.zzah
    public final void zza(zzop zzopVar) {
        this.zzajm.execute(new zzez(this, zzopVar));
    }

    @Override // com.google.android.gms.tagmanager.zzah
    public final zzov zzt(int i) throws Resources.NotFoundException {
        try {
            InputStream inputStreamOpenRawResource = this.zzrm.getResources().openRawResource(i);
            String resourceName = this.zzrm.getResources().getResourceName(i);
            StringBuilder sb = new StringBuilder(String.valueOf(resourceName).length() + 66);
            sb.append("Attempting to load a container from the resource ID ");
            sb.append(i);
            sb.append(" (");
            sb.append(resourceName);
            sb.append(")");
            zzdi.zzab(sb.toString());
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                zzor.zza(inputStreamOpenRawResource, byteArrayOutputStream);
                zzov zzovVarZza = zza(byteArrayOutputStream);
                if (zzovVarZza != null) {
                    zzdi.zzab("The container was successfully loaded from the resource (using JSON file format)");
                    return zzovVarZza;
                }
                return zzb(byteArrayOutputStream.toByteArray());
            } catch (IOException unused) {
                String resourceName2 = this.zzrm.getResources().getResourceName(i);
                StringBuilder sb2 = new StringBuilder(String.valueOf(resourceName2).length() + 67);
                sb2.append("Error reading the default container with resource ID ");
                sb2.append(i);
                sb2.append(" (");
                sb2.append(resourceName2);
                sb2.append(")");
                zzdi.zzac(sb2.toString());
                return null;
            }
        } catch (Resources.NotFoundException unused2) {
            StringBuilder sb3 = new StringBuilder(98);
            sb3.append("Failed to load the container. No default container resource found with the resource ID ");
            sb3.append(i);
            zzdi.zzac(sb3.toString());
            return null;
        }
    }

    private static zzov zza(ByteArrayOutputStream byteArrayOutputStream) {
        try {
            return zzda.zzbf(byteArrayOutputStream.toString("UTF-8"));
        } catch (UnsupportedEncodingException unused) {
            zzdi.zzax("Failed to convert binary resource to string for JSON parsing; the file format is not UTF-8 format.");
            return null;
        } catch (JSONException unused2) {
            zzdi.zzac("Failed to extract the container from the resource file. Resource is a UTF-8 encoded string but doesn't contain a JSON container");
            return null;
        }
    }

    private static zzov zzb(byte[] bArr) {
        try {
            zzov zzovVarZza = zzor.zza((com.google.android.gms.internal.gtm.zzi) zzuw.zza(new com.google.android.gms.internal.gtm.zzi(), bArr));
            if (zzovVarZza != null) {
                zzdi.zzab("The container was successfully loaded from the resource (using binary file)");
            }
            return zzovVarZza;
        } catch (zzoz unused) {
            zzdi.zzac("The resource file is invalid. The container from the binary file is invalid");
            return null;
        } catch (zzuv unused2) {
            zzdi.zzav("The resource file is corrupted. The container cannot be extracted from the binary file");
            return null;
        }
    }

    @Override // com.google.android.gms.common.api.Releasable
    public final synchronized void release() {
        this.zzajm.shutdown();
    }

    final boolean zzb(zzop zzopVar) {
        File fileZzje = zzje();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileZzje);
            try {
                try {
                    int iZzpe = zzopVar.zzpe();
                    byte[] bArr = new byte[iZzpe];
                    zzuw.zza(zzopVar, bArr, 0, iZzpe);
                    fileOutputStream.write(bArr);
                    try {
                        fileOutputStream.close();
                        return true;
                    } catch (IOException unused) {
                        zzdi.zzac("error closing stream for writing resource to disk");
                        return true;
                    }
                } finally {
                    try {
                        fileOutputStream.close();
                    } catch (IOException unused2) {
                        zzdi.zzac("error closing stream for writing resource to disk");
                    }
                }
            } catch (IOException unused3) {
                zzdi.zzac("Error writing resource to disk. Removing resource from disk.");
                fileZzje.delete();
                return false;
            }
        } catch (FileNotFoundException unused4) {
            zzdi.zzav("Error opening resource file for writing");
            return false;
        }
    }

    private final File zzje() {
        String strValueOf = String.valueOf(this.zzaec);
        return new File(this.zzrm.getDir("google_tagmanager", 0), strValueOf.length() != 0 ? "resource_".concat(strValueOf) : new String("resource_"));
    }
}
