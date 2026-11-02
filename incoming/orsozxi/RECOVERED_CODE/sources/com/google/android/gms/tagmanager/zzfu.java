package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import com.ibm.icu.impl.locale.LanguageTag;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
final class zzfu implements zzbe {
    private final String zzabp;
    private final zzfx zzalb;
    private final zzfw zzalc;
    private final Context zzrm;

    private zzfu(zzfx zzfxVar, Context context, zzfw zzfwVar) {
        this.zzalb = zzfxVar;
        this.zzrm = context.getApplicationContext();
        this.zzalc = zzfwVar;
        String str = Build.VERSION.RELEASE;
        Locale locale = Locale.getDefault();
        String string = null;
        if (locale != null && locale.getLanguage() != null && locale.getLanguage().length() != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(locale.getLanguage().toLowerCase());
            if (locale.getCountry() != null && locale.getCountry().length() != 0) {
                sb.append(LanguageTag.SEP);
                sb.append(locale.getCountry().toLowerCase());
            }
            string = sb.toString();
        }
        this.zzabp = String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", "GoogleTagManager", "4.00", str, string, Build.MODEL, Build.ID);
    }

    zzfu(Context context, zzfw zzfwVar) {
        this(new zzfv(), context, zzfwVar);
    }

    @Override // com.google.android.gms.tagmanager.zzbe
    public final boolean zzhy() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.zzrm.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        zzdi.zzab("...no network connectivity");
        return false;
    }

    @Override // com.google.android.gms.tagmanager.zzbe
    public final void zzd(List<zzbw> list) throws IOException {
        int iMin = Math.min(list.size(), 40);
        boolean z = true;
        for (int i = 0; i < iMin; i++) {
            zzbw zzbwVar = list.get(i);
            URL urlZzd = zzd(zzbwVar);
            if (urlZzd == null) {
                zzdi.zzac("No destination: discarding hit.");
                this.zzalc.zzb(zzbwVar);
            } else {
                InputStream inputStream = null;
                try {
                    HttpURLConnection httpURLConnectionZzc = this.zzalb.zzc(urlZzd);
                    if (z) {
                        try {
                            zzdn.zzn(this.zzrm);
                            z = false;
                        } catch (Throwable th) {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            httpURLConnectionZzc.disconnect();
                            throw th;
                        }
                    }
                    httpURLConnectionZzc.setRequestProperty("User-Agent", this.zzabp);
                    int responseCode = httpURLConnectionZzc.getResponseCode();
                    inputStream = httpURLConnectionZzc.getInputStream();
                    if (responseCode != 200) {
                        StringBuilder sb = new StringBuilder(25);
                        sb.append("Bad response: ");
                        sb.append(responseCode);
                        zzdi.zzac(sb.toString());
                        this.zzalc.zzc(zzbwVar);
                    } else {
                        this.zzalc.zza(zzbwVar);
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    httpURLConnectionZzc.disconnect();
                } catch (IOException e) {
                    String strValueOf = String.valueOf(e.getClass().getSimpleName());
                    zzdi.zzac(strValueOf.length() != 0 ? "Exception sending hit: ".concat(strValueOf) : new String("Exception sending hit: "));
                    zzdi.zzac(e.getMessage());
                    this.zzalc.zzc(zzbwVar);
                }
            }
        }
    }

    private static URL zzd(zzbw zzbwVar) {
        try {
            return new URL(zzbwVar.zzij());
        } catch (MalformedURLException unused) {
            zzdi.zzav("Error trying to parse the GTM url.");
            return null;
        }
    }
}
