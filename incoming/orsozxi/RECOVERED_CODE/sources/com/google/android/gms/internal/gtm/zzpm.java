package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzpl;
import com.google.android.gms.internal.gtm.zzpm;

/* loaded from: classes.dex */
public abstract class zzpm<MessageType extends zzpl<MessageType, BuilderType>, BuilderType extends zzpm<MessageType, BuilderType>> implements zzsl {
    protected abstract BuilderType zza(MessageType messagetype);

    @Override // 
    /* renamed from: zzmx, reason: merged with bridge method [inline-methods] */
    public abstract BuilderType clone();

    @Override // com.google.android.gms.internal.gtm.zzsl
    public final /* synthetic */ zzsl zza(zzsk zzskVar) {
        if (!zzpi().getClass().isInstance(zzskVar)) {
            throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
        }
        return zza((zzpm<MessageType, BuilderType>) zzskVar);
    }
}
