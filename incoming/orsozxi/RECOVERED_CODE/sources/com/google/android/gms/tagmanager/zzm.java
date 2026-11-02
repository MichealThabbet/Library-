package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import com.facebook.internal.ServerProtocol;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
class zzm extends zzgh {
    private static final String ID = com.google.android.gms.internal.gtm.zza.ARBITRARY_PIXEL.toString();
    private static final String URL = com.google.android.gms.internal.gtm.zzb.URL.toString();
    private static final String zzadw = com.google.android.gms.internal.gtm.zzb.ADDITIONAL_PARAMS.toString();
    private static final String zzadx = com.google.android.gms.internal.gtm.zzb.UNREPEATABLE.toString();
    private static final String zzady;
    private static final Set<String> zzadz;
    private final zza zzaea;
    private final Context zzrm;

    public interface zza {
        zzbx zzgx();
    }

    public zzm(Context context) {
        this(context, new zzn(context));
    }

    private zzm(Context context, zza zzaVar) {
        super(ID, URL);
        this.zzaea = zzaVar;
        this.zzrm = context;
    }

    @Override // com.google.android.gms.tagmanager.zzgh
    public final void zzd(Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        String strZzc = map.get(zzadx) != null ? zzgj.zzc(map.get(zzadx)) : null;
        if (strZzc == null || !zzak(strZzc)) {
            Uri.Builder builderBuildUpon = Uri.parse(zzgj.zzc(map.get(URL))).buildUpon();
            com.google.android.gms.internal.gtm.zzl zzlVar = map.get(zzadw);
            if (zzlVar != null) {
                Object objZzh = zzgj.zzh(zzlVar);
                if (!(objZzh instanceof List)) {
                    String strValueOf = String.valueOf(builderBuildUpon.build().toString());
                    zzdi.zzav(strValueOf.length() != 0 ? "ArbitraryPixel: additional params not a list: not sending partial hit: ".concat(strValueOf) : new String("ArbitraryPixel: additional params not a list: not sending partial hit: "));
                    return;
                }
                for (Object obj : (List) objZzh) {
                    if (!(obj instanceof Map)) {
                        String strValueOf2 = String.valueOf(builderBuildUpon.build().toString());
                        zzdi.zzav(strValueOf2.length() != 0 ? "ArbitraryPixel: additional params contains non-map: not sending partial hit: ".concat(strValueOf2) : new String("ArbitraryPixel: additional params contains non-map: not sending partial hit: "));
                        return;
                    } else {
                        for (Map.Entry entry : ((Map) obj).entrySet()) {
                            builderBuildUpon.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
                        }
                    }
                }
            }
            String string = builderBuildUpon.build().toString();
            this.zzaea.zzgx().zzay(string);
            String strValueOf3 = String.valueOf(string);
            zzdi.zzab(strValueOf3.length() != 0 ? "ArbitraryPixel: url = ".concat(strValueOf3) : new String("ArbitraryPixel: url = "));
            if (strZzc != null) {
                synchronized (zzm.class) {
                    zzadz.add(strZzc);
                    zzft.zza(this.zzrm, zzady, strZzc, ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
                }
            }
        }
    }

    private final synchronized boolean zzak(String str) {
        if (zzadz.contains(str)) {
            return true;
        }
        if (!this.zzrm.getSharedPreferences(zzady, 0).contains(str)) {
            return false;
        }
        zzadz.add(str);
        return true;
    }

    static {
        String str = ID;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 17);
        sb.append("gtm_");
        sb.append(str);
        sb.append("_unrepeatable");
        zzady = sb.toString();
        zzadz = new HashSet();
    }
}
