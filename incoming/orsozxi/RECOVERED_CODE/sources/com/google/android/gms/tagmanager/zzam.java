package com.google.android.gms.tagmanager;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
final class zzam extends zzbq {
    private final zzan zzafm;
    private static final String ID = com.google.android.gms.internal.gtm.zza.FUNCTION_CALL.toString();
    private static final String zzafl = com.google.android.gms.internal.gtm.zzb.FUNCTION_CALL_NAME.toString();
    private static final String zzadw = com.google.android.gms.internal.gtm.zzb.ADDITIONAL_PARAMS.toString();

    public zzam(zzan zzanVar) {
        super(ID, zzafl);
        this.zzafm = zzanVar;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final boolean zzgw() {
        return false;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final com.google.android.gms.internal.gtm.zzl zzb(Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        String strZzc = zzgj.zzc(map.get(zzafl));
        HashMap map2 = new HashMap();
        com.google.android.gms.internal.gtm.zzl zzlVar = map.get(zzadw);
        if (zzlVar != null) {
            Object objZzh = zzgj.zzh(zzlVar);
            if (!(objZzh instanceof Map)) {
                zzdi.zzac("FunctionCallMacro: expected ADDITIONAL_PARAMS to be a map.");
                return zzgj.zzkc();
            }
            for (Map.Entry entry : ((Map) objZzh).entrySet()) {
                map2.put(entry.getKey().toString(), entry.getValue());
            }
        }
        try {
            return zzgj.zzi(this.zzafm.zza(strZzc, map2));
        } catch (Exception e) {
            String message = e.getMessage();
            StringBuilder sb = new StringBuilder(String.valueOf(strZzc).length() + 34 + String.valueOf(message).length());
            sb.append("Custom macro/tag ");
            sb.append(strZzc);
            sb.append(" threw exception ");
            sb.append(message);
            zzdi.zzac(sb.toString());
            return zzgj.zzkc();
        }
    }
}
