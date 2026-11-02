package com.google.android.gms.internal.gtm;

import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzw extends com.google.android.gms.analytics.zzi<zzw> {
    private ProductAction zzrx;
    private final List<Product> zzsa = new ArrayList();
    private final List<Promotion> zzrz = new ArrayList();
    private final Map<String, List<Product>> zzry = new HashMap();

    public final ProductAction zzbn() {
        return this.zzrx;
    }

    public final String toString() {
        HashMap map = new HashMap();
        if (!this.zzsa.isEmpty()) {
            map.put("products", this.zzsa);
        }
        if (!this.zzrz.isEmpty()) {
            map.put("promotions", this.zzrz);
        }
        if (!this.zzry.isEmpty()) {
            map.put("impressions", this.zzry);
        }
        map.put("productAction", this.zzrx);
        return zza((Object) map);
    }

    public final List<Product> zzbo() {
        return Collections.unmodifiableList(this.zzsa);
    }

    public final Map<String, List<Product>> zzbp() {
        return this.zzry;
    }

    public final List<Promotion> zzbq() {
        return Collections.unmodifiableList(this.zzrz);
    }

    @Override // com.google.android.gms.analytics.zzi
    public final /* synthetic */ void zzb(com.google.android.gms.analytics.zzi zziVar) {
        zzw zzwVar = (zzw) zziVar;
        zzwVar.zzsa.addAll(this.zzsa);
        zzwVar.zzrz.addAll(this.zzrz);
        for (Map.Entry<String, List<Product>> entry : this.zzry.entrySet()) {
            String key = entry.getKey();
            for (Product product : entry.getValue()) {
                if (product != null) {
                    String str = key == null ? "" : key;
                    if (!zzwVar.zzry.containsKey(str)) {
                        zzwVar.zzry.put(str, new ArrayList());
                    }
                    zzwVar.zzry.get(str).add(product);
                }
            }
        }
        ProductAction productAction = this.zzrx;
        if (productAction != null) {
            zzwVar.zzrx = productAction;
        }
    }
}
