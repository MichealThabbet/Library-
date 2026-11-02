package com.google.android.gms.tagmanager;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import java.io.IOException;

/* loaded from: classes.dex */
final class zzb implements zzd {
    private final /* synthetic */ zza zzads;

    zzb(zza zzaVar) {
        this.zzads = zzaVar;
    }

    @Override // com.google.android.gms.tagmanager.zzd
    public final AdvertisingIdClient.Info zzgv() {
        try {
            return AdvertisingIdClient.getAdvertisingIdInfo(this.zzads.zzrm);
        } catch (GooglePlayServicesNotAvailableException e) {
            this.zzads.close();
            zzdi.zzb("GooglePlayServicesNotAvailableException getting Advertising Id Info", e);
            return null;
        } catch (GooglePlayServicesRepairableException e2) {
            zzdi.zzb("GooglePlayServicesRepairableException getting Advertising Id Info", e2);
            return null;
        } catch (IOException e3) {
            zzdi.zzb("IOException getting Ad Id Info", e3);
            return null;
        } catch (IllegalStateException e4) {
            zzdi.zzb("IllegalStateException getting Advertising Id Info", e4);
            return null;
        } catch (Exception e5) {
            zzdi.zzb("Unknown exception. Could not get the Advertising Id Info.", e5);
            return null;
        }
    }
}
