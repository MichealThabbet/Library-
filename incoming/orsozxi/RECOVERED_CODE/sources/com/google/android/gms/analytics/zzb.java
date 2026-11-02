package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.gtm.zzaa;
import com.google.android.gms.internal.gtm.zzab;
import com.google.android.gms.internal.gtm.zzac;
import com.google.android.gms.internal.gtm.zzam;
import com.google.android.gms.internal.gtm.zzao;
import com.google.android.gms.internal.gtm.zzap;
import com.google.android.gms.internal.gtm.zzas;
import com.google.android.gms.internal.gtm.zzcd;
import com.google.android.gms.internal.gtm.zzcz;
import com.google.android.gms.internal.gtm.zzq;
import com.google.android.gms.internal.gtm.zzr;
import com.google.android.gms.internal.gtm.zzs;
import com.google.android.gms.internal.gtm.zzt;
import com.google.android.gms.internal.gtm.zzu;
import com.google.android.gms.internal.gtm.zzv;
import com.google.android.gms.internal.gtm.zzw;
import com.google.android.gms.internal.gtm.zzx;
import com.google.android.gms.internal.gtm.zzy;
import com.google.android.gms.internal.gtm.zzz;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.ContainerUtils;
import com.ibm.icu.impl.locale.LanguageTag;
import com.ibm.icu.text.DateFormat;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzb extends zzam implements zzo {
    private static DecimalFormat zzrf;
    private final zzap zzrb;
    private final String zzrg;
    private final Uri zzrh;

    public zzb(zzap zzapVar, String str) {
        this(zzapVar, str, true, false);
    }

    private zzb(zzap zzapVar, String str, boolean z, boolean z2) {
        super(zzapVar);
        Preconditions.checkNotEmpty(str);
        this.zzrb = zzapVar;
        this.zzrg = str;
        this.zzrh = zzb(str);
    }

    static Uri zzb(String str) {
        Preconditions.checkNotEmpty(str);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("uri");
        builder.authority("google-analytics.com");
        builder.path(str);
        return builder.build();
    }

    @Override // com.google.android.gms.analytics.zzo
    public final Uri zzae() {
        return this.zzrh;
    }

    @Override // com.google.android.gms.analytics.zzo
    public final void zzb(zzg zzgVar) {
        Preconditions.checkNotNull(zzgVar);
        Preconditions.checkArgument(zzgVar.zzan(), "Can't deliver not submitted measurement");
        Preconditions.checkNotMainThread("deliver should be called on worker thread");
        zzg zzgVarZzai = zzgVar.zzai();
        zzz zzzVar = (zzz) zzgVarZzai.zzb(zzz.class);
        if (TextUtils.isEmpty(zzzVar.zzbs())) {
            zzco().zza(zzc(zzgVarZzai), "Ignoring measurement without type");
            return;
        }
        if (TextUtils.isEmpty(zzzVar.zzbt())) {
            zzco().zza(zzc(zzgVarZzai), "Ignoring measurement without client id");
            return;
        }
        if (this.zzrb.zzde().getAppOptOut()) {
            return;
        }
        double dZzbz = zzzVar.zzbz();
        if (zzcz.zza(dZzbz, zzzVar.zzbt())) {
            zzb("Sampling enabled. Hit sampled out. sampling rate", Double.valueOf(dZzbz));
            return;
        }
        Map<String, String> mapZzc = zzc(zzgVarZzai);
        mapZzc.put(DateFormat.ABBR_GENERIC_TZ, "1");
        mapZzc.put("_v", zzao.zzwe);
        mapZzc.put("tid", this.zzrg);
        if (this.zzrb.zzde().isDryRunEnabled()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : mapZzc.entrySet()) {
                if (sb.length() != 0) {
                    sb.append(", ");
                }
                sb.append(entry.getKey());
                sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
                sb.append(entry.getValue());
            }
            zzc("Dry run is enabled. GoogleAnalytics would have sent", sb.toString());
            return;
        }
        HashMap map = new HashMap();
        zzcz.zzb(map, "uid", zzzVar.zzbu());
        zzq zzqVar = (zzq) zzgVar.zza(zzq.class);
        if (zzqVar != null) {
            zzcz.zzb(map, "an", zzqVar.zzaz());
            zzcz.zzb(map, "aid", zzqVar.zzbb());
            zzcz.zzb(map, "av", zzqVar.zzba());
            zzcz.zzb(map, "aiid", zzqVar.zzbc());
        }
        mapZzc.put("_s", String.valueOf(zzcs().zza(new zzas(0L, zzzVar.zzbt(), this.zzrg, !TextUtils.isEmpty(zzzVar.zzbv()), 0L, map))));
        zzcs().zza(new zzcd(zzco(), mapZzc, zzgVar.zzal(), true));
    }

    private static Map<String, String> zzc(zzg zzgVar) {
        HashMap map = new HashMap();
        zzu zzuVar = (zzu) zzgVar.zza(zzu.class);
        if (zzuVar != null) {
            for (Map.Entry<String, Object> entry : zzuVar.zzbm().entrySet()) {
                Object value = entry.getValue();
                String strValueOf = null;
                if (value != null) {
                    if (value instanceof String) {
                        String str = (String) value;
                        if (!TextUtils.isEmpty(str)) {
                            strValueOf = str;
                        }
                    } else if (value instanceof Double) {
                        Double d = (Double) value;
                        if (d.doubleValue() != 0.0d) {
                            strValueOf = zza(d.doubleValue());
                        }
                    } else if (value instanceof Boolean) {
                        if (value != Boolean.FALSE) {
                            strValueOf = "1";
                        }
                    } else {
                        strValueOf = String.valueOf(value);
                    }
                }
                if (strValueOf != null) {
                    map.put(entry.getKey(), strValueOf);
                }
            }
        }
        zzz zzzVar = (zzz) zzgVar.zza(zzz.class);
        if (zzzVar != null) {
            zza(map, "t", zzzVar.zzbs());
            zza(map, "cid", zzzVar.zzbt());
            zza(map, "uid", zzzVar.zzbu());
            zza(map, "sc", zzzVar.zzbx());
            zza(map, "sf", zzzVar.zzbz());
            zza(map, "ni", zzzVar.zzby());
            zza(map, "adid", zzzVar.zzbv());
            zza(map, "ate", zzzVar.zzbw());
        }
        zzaa zzaaVar = (zzaa) zzgVar.zza(zzaa.class);
        if (zzaaVar != null) {
            zza(map, "cd", zzaaVar.zzca());
            zza(map, "a", zzaaVar.zzcb());
            zza(map, "dr", zzaaVar.zzcc());
        }
        zzx zzxVar = (zzx) zzgVar.zza(zzx.class);
        if (zzxVar != null) {
            zza(map, "ec", zzxVar.zzbr());
            zza(map, "ea", zzxVar.getAction());
            zza(map, "el", zzxVar.getLabel());
            zza(map, "ev", zzxVar.getValue());
        }
        zzr zzrVar = (zzr) zzgVar.zza(zzr.class);
        if (zzrVar != null) {
            zza(map, "cn", zzrVar.getName());
            zza(map, "cs", zzrVar.getSource());
            zza(map, "cm", zzrVar.zzbd());
            zza(map, "ck", zzrVar.zzbe());
            zza(map, "cc", zzrVar.zzbf());
            zza(map, "ci", zzrVar.getId());
            zza(map, "anid", zzrVar.zzbg());
            zza(map, "gclid", zzrVar.zzbh());
            zza(map, "dclid", zzrVar.zzbi());
            zza(map, FirebaseAnalytics.Param.ACLID, zzrVar.zzbj());
        }
        zzy zzyVar = (zzy) zzgVar.zza(zzy.class);
        if (zzyVar != null) {
            zza(map, "exd", zzyVar.zzuq);
            zza(map, "exf", zzyVar.zzur);
        }
        zzab zzabVar = (zzab) zzgVar.zza(zzab.class);
        if (zzabVar != null) {
            zza(map, "sn", zzabVar.zzvh);
            zza(map, "sa", zzabVar.zzvi);
            zza(map, "st", zzabVar.zzvj);
        }
        zzac zzacVar = (zzac) zzgVar.zza(zzac.class);
        if (zzacVar != null) {
            zza(map, "utv", zzacVar.zzvk);
            zza(map, "utt", zzacVar.zzvl);
            zza(map, "utc", zzacVar.mCategory);
            zza(map, "utl", zzacVar.zzvm);
        }
        zzs zzsVar = (zzs) zzgVar.zza(zzs.class);
        if (zzsVar != null) {
            for (Map.Entry<Integer, String> entry2 : zzsVar.zzbk().entrySet()) {
                String strZze = zzd.zze(entry2.getKey().intValue());
                if (!TextUtils.isEmpty(strZze)) {
                    map.put(strZze, entry2.getValue());
                }
            }
        }
        zzt zztVar = (zzt) zzgVar.zza(zzt.class);
        if (zztVar != null) {
            for (Map.Entry<Integer, Double> entry3 : zztVar.zzbl().entrySet()) {
                String strZzg = zzd.zzg(entry3.getKey().intValue());
                if (!TextUtils.isEmpty(strZzg)) {
                    map.put(strZzg, zza(entry3.getValue().doubleValue()));
                }
            }
        }
        zzw zzwVar = (zzw) zzgVar.zza(zzw.class);
        if (zzwVar != null) {
            ProductAction productActionZzbn = zzwVar.zzbn();
            if (productActionZzbn != null) {
                for (Map.Entry<String, String> entry4 : productActionZzbn.build().entrySet()) {
                    if (entry4.getKey().startsWith(ContainerUtils.FIELD_DELIMITER)) {
                        map.put(entry4.getKey().substring(1), entry4.getValue());
                    } else {
                        map.put(entry4.getKey(), entry4.getValue());
                    }
                }
            }
            Iterator<Promotion> it2 = zzwVar.zzbq().iterator();
            int i = 1;
            while (it2.hasNext()) {
                map.putAll(it2.next().zzn(zzd.zzk(i)));
                i++;
            }
            Iterator<Product> it3 = zzwVar.zzbo().iterator();
            int i2 = 1;
            while (it3.hasNext()) {
                map.putAll(it3.next().zzn(zzd.zzi(i2)));
                i2++;
            }
            int i3 = 1;
            for (Map.Entry<String, List<Product>> entry5 : zzwVar.zzbp().entrySet()) {
                List<Product> value2 = entry5.getValue();
                String strZzn = zzd.zzn(i3);
                int i4 = 1;
                for (Product product : value2) {
                    String strValueOf2 = String.valueOf(strZzn);
                    String strValueOf3 = String.valueOf(zzd.zzl(i4));
                    map.putAll(product.zzn(strValueOf3.length() != 0 ? strValueOf2.concat(strValueOf3) : new String(strValueOf2)));
                    i4++;
                }
                if (!TextUtils.isEmpty(entry5.getKey())) {
                    String strValueOf4 = String.valueOf(strZzn);
                    map.put("nm".length() != 0 ? strValueOf4.concat("nm") : new String(strValueOf4), entry5.getKey());
                }
                i3++;
            }
        }
        zzv zzvVar = (zzv) zzgVar.zza(zzv.class);
        if (zzvVar != null) {
            zza(map, "ul", zzvVar.getLanguage());
            zza(map, "sd", zzvVar.zzuk);
            zza(map, "sr", zzvVar.zzul, zzvVar.zzum);
            zza(map, "vp", zzvVar.zzun, zzvVar.zzuo);
        }
        zzq zzqVar = (zzq) zzgVar.zza(zzq.class);
        if (zzqVar != null) {
            zza(map, "an", zzqVar.zzaz());
            zza(map, "aid", zzqVar.zzbb());
            zza(map, "aiid", zzqVar.zzbc());
            zza(map, "av", zzqVar.zzba());
        }
        return map;
    }

    private static void zza(Map<String, String> map, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        map.put(str, str2);
    }

    private static String zza(double d) {
        if (zzrf == null) {
            zzrf = new DecimalFormat("0.######");
        }
        return zzrf.format(d);
    }

    private static void zza(Map<String, String> map, String str, double d) {
        if (d != 0.0d) {
            map.put(str, zza(d));
        }
    }

    private static void zza(Map<String, String> map, String str, boolean z) {
        if (z) {
            map.put(str, "1");
        }
    }

    private static void zza(Map<String, String> map, String str, int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            return;
        }
        StringBuilder sb = new StringBuilder(23);
        sb.append(i);
        sb.append(LanguageTag.PRIVATEUSE);
        sb.append(i2);
        map.put(str, sb.toString());
    }
}
