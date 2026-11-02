package com.huawei.hms.audiokit.player.manager;

import android.os.RemoteException;
import android.util.Log;
import com.huawei.hms.api.IRemoteCreator;
import com.huawei.hms.api.manager.WiseAudioConfigManager;
import com.huawei.hms.api.manager.WiseAudioManagerImpl;

/* loaded from: classes2.dex */
public final class HwAudioConfigManager {
    private static final com.huawei.hms.audiokit.f<HwAudioConfigManager> a = new b();
    private WiseAudioConfigManager b;

    private HwAudioConfigManager() {
        WiseAudioManagerImpl manager;
        try {
            IRemoteCreator iRemoteCreatorC = com.huawei.hms.audiokit.b.c();
            if (iRemoteCreatorC == null || (manager = iRemoteCreatorC.getManager()) == null) {
                return;
            }
            this.b = manager.getConfigManager();
        } catch (RemoteException unused) {
            Log.e("HwAudioConfigManager", "RemoteException");
        }
    }

    /* synthetic */ HwAudioConfigManager(b bVar) {
        this();
    }

    static HwAudioConfigManager a() {
        return a.b();
    }

    public void clearPlayCache() {
        Log.i("HwAudioConfigManager", "clearPlayCache");
        try {
            if (this.b != null) {
                this.b.clearPlayCache();
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioConfigManager", "RemoteException");
        }
    }

    public void continueToDownloadIfNeed() {
        Log.i("HwAudioConfigManager", "continueToDownloadIfNeed");
        try {
            if (this.b != null) {
                this.b.continueToDownloadIfNeed();
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioConfigManager", "RemoteException");
        }
    }

    public long getPlayCacheSize() {
        Log.i("HwAudioConfigManager", "getPlayCacheSize");
        try {
            if (this.b != null) {
                return this.b.getPlayCacheSize();
            }
            return 0L;
        } catch (RemoteException unused) {
            Log.e("HwAudioConfigManager", "RemoteException");
            return 0L;
        }
    }

    public long getUsedCacheSize() {
        Log.i("HwAudioConfigManager", "getUsedCacheSize");
        try {
            if (this.b != null) {
                return this.b.getUsedCacheSize();
            }
            return 0L;
        } catch (RemoteException unused) {
            Log.e("HwAudioConfigManager", "RemoteException");
            return 0L;
        }
    }

    public void setNotificationFactory(INotificationFactory iNotificationFactory) {
        Log.i("HwAudioConfigManager", "setNotificationFactory");
        try {
            if (this.b != null) {
                this.b.setNotificationFactory(iNotificationFactory == null ? null : new h(iNotificationFactory));
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioConfigManager", "RemoteException");
        }
    }

    public void setPlayCacheSize(long j) {
        Log.i("HwAudioConfigManager", "setPlayCacheSize");
        try {
            if (this.b != null) {
                this.b.setPlayCacheSize(j);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioConfigManager", "RemoteException");
        }
    }

    public void setSaveQueue(boolean z) {
        Log.i("HwAudioConfigManager", "setSaveQueue");
        try {
            if (this.b != null) {
                this.b.setSaveQueue(z);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioConfigManager", "RemoteException");
        }
    }

    public void setSessionState(boolean z) {
        Log.i("HwAudioConfigManager", "setSessionState");
        try {
            if (this.b != null) {
                this.b.setSessionState(z);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioConfigManager", "RemoteException");
        }
    }

    public void stopDownload() {
        Log.i("HwAudioConfigManager", "stopDownload");
        try {
            if (this.b != null) {
                this.b.stopDownload();
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioConfigManager", "RemoteException");
        }
    }
}
