package com.google.android.gms.internal.gtm;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzcf extends zzm implements zzce {
    zzcf(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.analytics.internal.IAnalyticsService");
    }

    @Override // com.google.android.gms.internal.gtm.zzce
    public final void zza(Map map, long j, String str, List<zzbk> list) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeMap(map);
        parcelObtainAndWriteInterfaceToken.writeLong(j);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeTypedList(list);
        zza(1, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.gtm.zzce
    public final void zzch() throws RemoteException {
        zza(2, obtainAndWriteInterfaceToken());
    }
}
