package com.google.android.gms.internal.gtm;

import com.google.android.gms.common.internal.Preconditions;
import com.ibm.icu.impl.locale.LanguageTag;
import java.util.Map;
import net.fortuna.ical4j.model.property.RequestStatus;

/* loaded from: classes.dex */
public class zzci extends zzan {
    private static zzci zzabl;

    public zzci(zzap zzapVar) {
        super(zzapVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzan
    protected final void zzaw() {
        synchronized (zzci.class) {
            zzabl = this;
        }
    }

    public static zzci zzfn() {
        return zzabl;
    }

    public final void zza(zzcd zzcdVar, String str) {
        String string = zzcdVar != null ? zzcdVar.toString() : "no hit data";
        String strValueOf = String.valueOf(str);
        zzd(strValueOf.length() != 0 ? "Discarding hit. ".concat(strValueOf) : new String("Discarding hit. "), string);
    }

    public final void zza(Map<String, String> map, String str) {
        String string;
        if (map != null) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (sb.length() > 0) {
                    sb.append(',');
                }
                sb.append(entry.getKey());
                sb.append('=');
                sb.append(entry.getValue());
            }
            string = sb.toString();
        } else {
            string = "no hit data";
        }
        String strValueOf = String.valueOf(str);
        zzd(strValueOf.length() != 0 ? "Discarding hit. ".concat(strValueOf) : new String("Discarding hit. "), string);
    }

    public final synchronized void zzb(int i, String str, Object obj, Object obj2, Object obj3) {
        Preconditions.checkNotNull(str);
        if (i < 0) {
            i = 0;
        }
        if (i >= 9) {
            i = 8;
        }
        char c = zzcp().zzem() ? 'C' : 'c';
        char cCharAt = "01VDIWEA?".charAt(i);
        String str2 = zzao.VERSION;
        String strZzc = zzc(str, zzd(obj), zzd(obj2), zzd(obj3));
        StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 4 + String.valueOf(strZzc).length());
        sb.append(RequestStatus.CLIENT_ERROR);
        sb.append(cCharAt);
        sb.append(c);
        sb.append(str2);
        sb.append(":");
        sb.append(strZzc);
        String string = sb.toString();
        if (string.length() > 1024) {
            string = string.substring(0, 1024);
        }
        zzcm zzcmVarZzdf = zzcm().zzdf();
        if (zzcmVarZzdf != null) {
            zzcmVarZzdf.zzga().zzae(string);
        }
    }

    private static String zzd(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Integer) {
            obj = Long.valueOf(((Integer) obj).intValue());
        }
        boolean z = obj instanceof Long;
        String str = LanguageTag.SEP;
        if (z) {
            Long l = (Long) obj;
            if (Math.abs(l.longValue()) < 100) {
                return String.valueOf(obj);
            }
            if (String.valueOf(obj).charAt(0) != '-') {
                str = "";
            }
            String strValueOf = String.valueOf(Math.abs(l.longValue()));
            return str + Math.round(Math.pow(10.0d, strValueOf.length() - 1)) + "..." + str + Math.round(Math.pow(10.0d, strValueOf.length()) - 1.0d);
        }
        if (obj instanceof Boolean) {
            return String.valueOf(obj);
        }
        return obj instanceof Throwable ? obj.getClass().getCanonicalName() : LanguageTag.SEP;
    }
}
