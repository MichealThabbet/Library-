package com.google.android.gms.internal.gtm;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import com.google.android.gms.common.internal.Preconditions;
import com.ibm.icu.text.DateFormat;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.Map;
import kotlin.text.Typography;

/* loaded from: classes.dex */
final class zzck extends zzan {
    private static final byte[] zzabr = "\n".getBytes();
    private final String zzabp;
    private final zzcv zzabq;

    zzck(zzap zzapVar) {
        super(zzapVar);
        this.zzabp = String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", "GoogleAnalytics", zzao.VERSION, Build.VERSION.RELEASE, zzcz.zza(Locale.getDefault()), Build.MODEL, Build.ID);
        this.zzabq = new zzcv(zzapVar.zzcn());
    }

    @Override // com.google.android.gms.internal.gtm.zzan
    protected final void zzaw() {
        zza("Network initialized. User agent", this.zzabp);
    }

    public final boolean zzfr() {
        NetworkInfo activeNetworkInfo;
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        try {
            activeNetworkInfo = ((ConnectivityManager) getContext().getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (SecurityException unused) {
            activeNetworkInfo = null;
        }
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        zzq("No network connectivity");
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x019e  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01b3 A[EDGE_INSN: B:75:0x01b3->B:72:0x01b3 BREAK  A[LOOP:1: B:42:0x0110->B:78:?], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.util.List<java.lang.Long> zzb(java.util.List<com.google.android.gms.internal.gtm.zzcd> r9) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 436
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzck.zzb(java.util.List):java.util.List");
    }

    private final int zza(URL url) {
        Preconditions.checkNotNull(url);
        zzb("GET request", url);
        HttpURLConnection httpURLConnectionZzb = null;
        try {
            try {
                httpURLConnectionZzb = zzb(url);
                httpURLConnectionZzb.connect();
                zza(httpURLConnectionZzb);
                int responseCode = httpURLConnectionZzb.getResponseCode();
                if (responseCode == 200) {
                    zzcs().zzcl();
                }
                zzb("GET status", Integer.valueOf(responseCode));
                if (httpURLConnectionZzb != null) {
                    httpURLConnectionZzb.disconnect();
                }
                return responseCode;
            } catch (IOException e) {
                zzd("Network GET connection error", e);
                if (httpURLConnectionZzb == null) {
                    return 0;
                }
                httpURLConnectionZzb.disconnect();
                return 0;
            }
        } catch (Throwable th) {
            if (httpURLConnectionZzb != null) {
                httpURLConnectionZzb.disconnect();
            }
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x008b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:55:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r5v0, types: [java.lang.Object, java.net.URL] */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v3, types: [java.net.HttpURLConnection] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int zza(java.net.URL r5, byte[] r6) throws java.lang.Throwable {
        /*
            r4 = this;
            java.lang.String r0 = "Error closing http post connection output stream"
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r5)
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r6)
            int r1 = r6.length
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            java.lang.String r2 = "POST bytes, url"
            r4.zzb(r2, r1, r5)
            boolean r1 = zzda()
            if (r1 == 0) goto L22
            java.lang.String r1 = new java.lang.String
            r1.<init>(r6)
            java.lang.String r2 = "Post payload\n"
            r4.zza(r2, r1)
        L22:
            r1 = 0
            android.content.Context r2 = r4.getContext()     // Catch: java.lang.Throwable -> L6d java.io.IOException -> L70
            r2.getPackageName()     // Catch: java.lang.Throwable -> L6d java.io.IOException -> L70
            java.net.HttpURLConnection r5 = r4.zzb(r5)     // Catch: java.lang.Throwable -> L6d java.io.IOException -> L70
            r2 = 1
            r5.setDoOutput(r2)     // Catch: java.io.IOException -> L6b java.lang.Throwable -> L88
            int r2 = r6.length     // Catch: java.io.IOException -> L6b java.lang.Throwable -> L88
            r5.setFixedLengthStreamingMode(r2)     // Catch: java.io.IOException -> L6b java.lang.Throwable -> L88
            r5.connect()     // Catch: java.io.IOException -> L6b java.lang.Throwable -> L88
            java.io.OutputStream r1 = r5.getOutputStream()     // Catch: java.io.IOException -> L6b java.lang.Throwable -> L88
            r1.write(r6)     // Catch: java.io.IOException -> L6b java.lang.Throwable -> L88
            r4.zza(r5)     // Catch: java.io.IOException -> L6b java.lang.Throwable -> L88
            int r6 = r5.getResponseCode()     // Catch: java.io.IOException -> L6b java.lang.Throwable -> L88
            r2 = 200(0xc8, float:2.8E-43)
            if (r6 != r2) goto L52
            com.google.android.gms.internal.gtm.zzae r2 = r4.zzcs()     // Catch: java.io.IOException -> L6b java.lang.Throwable -> L88
            r2.zzcl()     // Catch: java.io.IOException -> L6b java.lang.Throwable -> L88
        L52:
            java.lang.String r2 = "POST status"
            java.lang.Integer r3 = java.lang.Integer.valueOf(r6)     // Catch: java.io.IOException -> L6b java.lang.Throwable -> L88
            r4.zzb(r2, r3)     // Catch: java.io.IOException -> L6b java.lang.Throwable -> L88
            if (r1 == 0) goto L65
            r1.close()     // Catch: java.io.IOException -> L61
            goto L65
        L61:
            r1 = move-exception
            r4.zze(r0, r1)
        L65:
            if (r5 == 0) goto L6a
            r5.disconnect()
        L6a:
            return r6
        L6b:
            r6 = move-exception
            goto L72
        L6d:
            r6 = move-exception
            r5 = r1
            goto L89
        L70:
            r6 = move-exception
            r5 = r1
        L72:
            java.lang.String r2 = "Network POST connection error"
            r4.zzd(r2, r6)     // Catch: java.lang.Throwable -> L88
            if (r1 == 0) goto L81
            r1.close()     // Catch: java.io.IOException -> L7d
            goto L81
        L7d:
            r6 = move-exception
            r4.zze(r0, r6)
        L81:
            if (r5 == 0) goto L86
            r5.disconnect()
        L86:
            r5 = 0
            return r5
        L88:
            r6 = move-exception
        L89:
            if (r1 == 0) goto L93
            r1.close()     // Catch: java.io.IOException -> L8f
            goto L93
        L8f:
            r1 = move-exception
            r4.zze(r0, r1)
        L93:
            if (r5 == 0) goto L98
            r5.disconnect()
        L98:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzck.zza(java.net.URL, byte[]):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00e8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:67:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int zzb(java.net.URL r11, byte[] r12) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 246
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzck.zzb(java.net.URL, byte[]):int");
    }

    private final void zza(HttpURLConnection httpURLConnection) throws Throwable {
        InputStream inputStream;
        try {
            inputStream = httpURLConnection.getInputStream();
            try {
                do {
                } while (inputStream.read(new byte[1024]) > 0);
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        zze("Error closing http connection input stream", e);
                    }
                }
            } catch (Throwable th) {
                th = th;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e2) {
                        zze("Error closing http connection input stream", e2);
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            inputStream = null;
        }
    }

    private final HttpURLConnection zzb(URL url) throws IOException {
        URLConnection uRLConnectionOpenConnection = url.openConnection();
        if (!(uRLConnectionOpenConnection instanceof HttpURLConnection)) {
            throw new IOException("Failed to obtain http connection");
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
        httpURLConnection.setDefaultUseCaches(false);
        httpURLConnection.setConnectTimeout(zzby.zzaad.get().intValue());
        httpURLConnection.setReadTimeout(zzby.zzaae.get().intValue());
        httpURLConnection.setInstanceFollowRedirects(false);
        httpURLConnection.setRequestProperty("User-Agent", this.zzabp);
        httpURLConnection.setDoInput(true);
        return httpURLConnection;
    }

    private final URL zzd(zzcd zzcdVar) {
        String str;
        String strConcat;
        if (zzcdVar.zzfj()) {
            String strValueOf = String.valueOf(zzbq.zzet());
            String strValueOf2 = String.valueOf(zzbq.zzev());
            if (strValueOf2.length() != 0) {
                strConcat = strValueOf.concat(strValueOf2);
            } else {
                str = new String(strValueOf);
                strConcat = str;
            }
        } else {
            String strValueOf3 = String.valueOf(zzbq.zzeu());
            String strValueOf4 = String.valueOf(zzbq.zzev());
            if (strValueOf4.length() != 0) {
                strConcat = strValueOf3.concat(strValueOf4);
            } else {
                str = new String(strValueOf3);
                strConcat = str;
            }
        }
        try {
            return new URL(strConcat);
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    private final URL zzb(zzcd zzcdVar, String str) {
        String string;
        if (zzcdVar.zzfj()) {
            String strZzet = zzbq.zzet();
            String strZzev = zzbq.zzev();
            StringBuilder sb = new StringBuilder(String.valueOf(strZzet).length() + 1 + String.valueOf(strZzev).length() + String.valueOf(str).length());
            sb.append(strZzet);
            sb.append(strZzev);
            sb.append("?");
            sb.append(str);
            string = sb.toString();
        } else {
            String strZzeu = zzbq.zzeu();
            String strZzev2 = zzbq.zzev();
            StringBuilder sb2 = new StringBuilder(String.valueOf(strZzeu).length() + 1 + String.valueOf(strZzev2).length() + String.valueOf(str).length());
            sb2.append(strZzeu);
            sb2.append(strZzev2);
            sb2.append("?");
            sb2.append(str);
            string = sb2.toString();
        }
        try {
            return new URL(string);
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    private final URL zzfs() {
        String strValueOf = String.valueOf(zzbq.zzet());
        String strValueOf2 = String.valueOf(zzby.zzzs.get());
        try {
            return new URL(strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf));
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    final String zza(zzcd zzcdVar, boolean z) {
        String strValueOf;
        Preconditions.checkNotNull(zzcdVar);
        StringBuilder sb = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : zzcdVar.zzdm().entrySet()) {
                String key = entry.getKey();
                if (!"ht".equals(key) && !"qt".equals(key) && !"AppUID".equals(key) && !DateFormat.ABBR_SPECIFIC_TZ.equals(key) && !"_gmsv".equals(key)) {
                    zza(sb, key, entry.getValue());
                }
            }
            zza(sb, "ht", String.valueOf(zzcdVar.zzfh()));
            zza(sb, "qt", String.valueOf(zzcn().currentTimeMillis() - zzcdVar.zzfh()));
            if (z) {
                long jZzfk = zzcdVar.zzfk();
                if (jZzfk != 0) {
                    strValueOf = String.valueOf(jZzfk);
                } else {
                    strValueOf = String.valueOf(zzcdVar.zzfg());
                }
                zza(sb, DateFormat.ABBR_SPECIFIC_TZ, strValueOf);
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            zze("Failed to encode name or value", e);
            return null;
        }
    }

    private static void zza(StringBuilder sb, String str, String str2) throws UnsupportedEncodingException {
        if (sb.length() != 0) {
            sb.append(Typography.amp);
        }
        sb.append(URLEncoder.encode(str, "UTF-8"));
        sb.append('=');
        sb.append(URLEncoder.encode(str2, "UTF-8"));
    }
}
