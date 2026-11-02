package com.google.android.gms.analytics;

import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.internal.gtm.zzch;
import com.google.android.gms.internal.gtm.zzcz;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class HitBuilders {

    @Deprecated
    public static class AppViewBuilder extends HitBuilder<AppViewBuilder> {
        public AppViewBuilder() {
            set("&t", "screenview");
        }
    }

    public static class ScreenViewBuilder extends HitBuilder<ScreenViewBuilder> {
        public ScreenViewBuilder() {
            set("&t", "screenview");
        }
    }

    public static class EventBuilder extends HitBuilder<EventBuilder> {
        public EventBuilder() {
            set("&t", "event");
        }

        public EventBuilder(String str, String str2) {
            this();
            setCategory(str);
            setAction(str2);
        }

        public EventBuilder setCategory(String str) {
            set("&ec", str);
            return this;
        }

        public EventBuilder setAction(String str) {
            set("&ea", str);
            return this;
        }

        public EventBuilder setLabel(String str) {
            set("&el", str);
            return this;
        }

        public EventBuilder setValue(long j) {
            set("&ev", Long.toString(j));
            return this;
        }
    }

    public static class ExceptionBuilder extends HitBuilder<ExceptionBuilder> {
        public ExceptionBuilder() {
            set("&t", "exception");
        }

        public ExceptionBuilder setDescription(String str) {
            set("&exd", str);
            return this;
        }

        public ExceptionBuilder setFatal(boolean z) {
            set("&exf", zzcz.zzc(z));
            return this;
        }
    }

    @Deprecated
    public static class ItemBuilder extends HitBuilder<ItemBuilder> {
        public ItemBuilder() {
            set("&t", "item");
        }

        public ItemBuilder setTransactionId(String str) {
            set("&ti", str);
            return this;
        }

        public ItemBuilder setName(String str) {
            set("&in", str);
            return this;
        }

        public ItemBuilder setSku(String str) {
            set("&ic", str);
            return this;
        }

        public ItemBuilder setCategory(String str) {
            set("&iv", str);
            return this;
        }

        public ItemBuilder setPrice(double d) {
            set("&ip", Double.toString(d));
            return this;
        }

        public ItemBuilder setQuantity(long j) {
            set("&iq", Long.toString(j));
            return this;
        }

        public ItemBuilder setCurrencyCode(String str) {
            set("&cu", str);
            return this;
        }
    }

    public static class SocialBuilder extends HitBuilder<SocialBuilder> {
        public SocialBuilder() {
            set("&t", NotificationCompat.CATEGORY_SOCIAL);
        }

        public SocialBuilder setNetwork(String str) {
            set("&sn", str);
            return this;
        }

        public SocialBuilder setAction(String str) {
            set("&sa", str);
            return this;
        }

        public SocialBuilder setTarget(String str) {
            set("&st", str);
            return this;
        }
    }

    public static class TimingBuilder extends HitBuilder<TimingBuilder> {
        public TimingBuilder() {
            set("&t", "timing");
        }

        public TimingBuilder(String str, String str2, long j) {
            this();
            setVariable(str2);
            setValue(j);
            setCategory(str);
        }

        public TimingBuilder setVariable(String str) {
            set("&utv", str);
            return this;
        }

        public TimingBuilder setValue(long j) {
            set("&utt", Long.toString(j));
            return this;
        }

        public TimingBuilder setCategory(String str) {
            set("&utc", str);
            return this;
        }

        public TimingBuilder setLabel(String str) {
            set("&utl", str);
            return this;
        }
    }

    @Deprecated
    public static class TransactionBuilder extends HitBuilder<TransactionBuilder> {
        public TransactionBuilder() {
            set("&t", "transaction");
        }

        public TransactionBuilder setTransactionId(String str) {
            set("&ti", str);
            return this;
        }

        public TransactionBuilder setAffiliation(String str) {
            set("&ta", str);
            return this;
        }

        public TransactionBuilder setRevenue(double d) {
            set("&tr", Double.toString(d));
            return this;
        }

        public TransactionBuilder setTax(double d) {
            set("&tt", Double.toString(d));
            return this;
        }

        public TransactionBuilder setShipping(double d) {
            set("&ts", Double.toString(d));
            return this;
        }

        public TransactionBuilder setCurrencyCode(String str) {
            set("&cu", str);
            return this;
        }
    }

    public static class HitBuilder<T extends HitBuilder> {
        private ProductAction zzrx;
        private Map<String, String> map = new HashMap();
        private Map<String, List<Product>> zzry = new HashMap();
        private List<Promotion> zzrz = new ArrayList();
        private List<Product> zzsa = new ArrayList();

        protected HitBuilder() {
        }

        public T setNewSession() {
            set("&sc", "start");
            return this;
        }

        public T setNonInteraction(boolean z) {
            set("&ni", zzcz.zzc(z));
            return this;
        }

        public T setCampaignParamsFromUrl(String str) throws UnsupportedEncodingException {
            String strZzah = zzcz.zzah(str);
            if (TextUtils.isEmpty(strZzah)) {
                return this;
            }
            Map<String, String> mapZzaf = zzcz.zzaf(strZzah);
            zza("&cc", mapZzaf.get("utm_content"));
            zza("&cm", mapZzaf.get("utm_medium"));
            zza("&cn", mapZzaf.get("utm_campaign"));
            zza("&cs", mapZzaf.get("utm_source"));
            zza("&ck", mapZzaf.get("utm_term"));
            zza("&ci", mapZzaf.get("utm_id"));
            zza("&anid", mapZzaf.get("anid"));
            zza("&gclid", mapZzaf.get("gclid"));
            zza("&dclid", mapZzaf.get("dclid"));
            zza("&aclid", mapZzaf.get(FirebaseAnalytics.Param.ACLID));
            zza("&gmob_t", mapZzaf.get("gmob_t"));
            return this;
        }

        public T setCustomDimension(int i, String str) {
            set(zzd.zzd(i), str);
            return this;
        }

        public T setCustomMetric(int i, float f) {
            set(zzd.zzf(i), Float.toString(f));
            return this;
        }

        public final T set(String str, String str2) {
            if (str != null) {
                this.map.put(str, str2);
            } else {
                zzch.zzac("HitBuilder.set() called with a null paramName.");
            }
            return this;
        }

        private final T zza(String str, String str2) {
            if (str2 != null) {
                this.map.put(str, str2);
            }
            return this;
        }

        public final T setAll(Map<String, String> map) {
            if (map == null) {
                return this;
            }
            this.map.putAll(new HashMap(map));
            return this;
        }

        public Map<String, String> build() {
            HashMap map = new HashMap(this.map);
            ProductAction productAction = this.zzrx;
            if (productAction != null) {
                map.putAll(productAction.build());
            }
            Iterator<Promotion> it2 = this.zzrz.iterator();
            int i = 1;
            while (it2.hasNext()) {
                map.putAll(it2.next().zzn(zzd.zzj(i)));
                i++;
            }
            Iterator<Product> it3 = this.zzsa.iterator();
            int i2 = 1;
            while (it3.hasNext()) {
                map.putAll(it3.next().zzn(zzd.zzh(i2)));
                i2++;
            }
            int i3 = 1;
            for (Map.Entry<String, List<Product>> entry : this.zzry.entrySet()) {
                List<Product> value = entry.getValue();
                String strZzm = zzd.zzm(i3);
                int i4 = 1;
                for (Product product : value) {
                    String strValueOf = String.valueOf(strZzm);
                    String strValueOf2 = String.valueOf(zzd.zzl(i4));
                    map.putAll(product.zzn(strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf)));
                    i4++;
                }
                if (!TextUtils.isEmpty(entry.getKey())) {
                    String strValueOf3 = String.valueOf(strZzm);
                    map.put("nm".length() != 0 ? strValueOf3.concat("nm") : new String(strValueOf3), entry.getKey());
                }
                i3++;
            }
            return map;
        }

        public T setProductAction(ProductAction productAction) {
            this.zzrx = productAction;
            return this;
        }

        public T addImpression(Product product, String str) {
            if (product == null) {
                zzch.zzac("product should be non-null");
                return this;
            }
            if (str == null) {
                str = "";
            }
            if (!this.zzry.containsKey(str)) {
                this.zzry.put(str, new ArrayList());
            }
            this.zzry.get(str).add(product);
            return this;
        }

        public T addPromotion(Promotion promotion) {
            if (promotion == null) {
                zzch.zzac("promotion should be non-null");
                return this;
            }
            this.zzrz.add(promotion);
            return this;
        }

        public T setPromotionAction(String str) {
            this.map.put("&promoa", str);
            return this;
        }

        public T addProduct(Product product) {
            if (product == null) {
                zzch.zzac("product should be non-null");
                return this;
            }
            this.zzsa.add(product);
            return this;
        }

        protected T setHitType(String str) {
            set("&t", str);
            return this;
        }

        protected String get(String str) {
            return this.map.get(str);
        }
    }
}
