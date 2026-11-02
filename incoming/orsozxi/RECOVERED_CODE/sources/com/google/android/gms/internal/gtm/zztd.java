package com.google.android.gms.internal.gtm;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [FieldDescriptorType] */
/* loaded from: classes.dex */
final class zztd<FieldDescriptorType> extends zztc<FieldDescriptorType, Object> {
    zztd(int i) {
        super(i, null);
    }

    @Override // com.google.android.gms.internal.gtm.zztc
    public final void zzmi() {
        if (!isImmutable()) {
            for (int i = 0; i < zzra(); i++) {
                Map.Entry<FieldDescriptorType, Object> entryZzbv = zzbv(i);
                if (((zzqv) entryZzbv.getKey()).zzoz()) {
                    entryZzbv.setValue(Collections.unmodifiableList((List) entryZzbv.getValue()));
                }
            }
            for (Map.Entry<FieldDescriptorType, Object> entry : zzrb()) {
                if (((zzqv) entry.getKey()).zzoz()) {
                    entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                }
            }
        }
        super.zzmi();
    }
}
