package com.huawei.hms.audiokit.player.manager;

import android.os.RemoteException;
import android.util.Log;
import com.huawei.hms.api.manager.WiseAudioManagerImpl;

/* loaded from: classes2.dex */
final class HwAudioManagerImpl implements HwAudioManager {
    private WiseAudioManagerImpl a;

    HwAudioManagerImpl() {
        try {
            this.a = com.huawei.hms.audiokit.b.c().getManager();
        } catch (RemoteException unused) {
            Log.e("WiseAudioManagerImpl", "constructor RemoteException");
        }
    }

    @Override // com.huawei.hms.audiokit.player.manager.HwAudioManager
    public void addPlayerStatusListener(HwAudioStatusListener hwAudioStatusListener) throws RemoteException {
        if (this.a != null) {
            this.a.addPlayerStatusListener(hwAudioStatusListener == null ? null : new g(hwAudioStatusListener));
        }
    }

    @Override // com.huawei.hms.audiokit.player.manager.HwAudioManager
    public HwAudioConfigManager getConfigManager() {
        return HwAudioConfigManager.a();
    }

    @Override // com.huawei.hms.audiokit.player.manager.HwAudioManager
    public HwAudioEffectManager getEffectManager() {
        return HwAudioEffectManager.a();
    }

    @Override // com.huawei.hms.audiokit.player.manager.HwAudioManager
    public HwAudioPlayerManager getPlayerManager() {
        return HwAudioPlayerManager.a();
    }

    @Override // com.huawei.hms.audiokit.player.manager.HwAudioManager
    public HwAudioQueueManager getQueueManager() {
        return HwAudioQueueManager.a();
    }

    @Override // com.huawei.hms.audiokit.player.manager.HwAudioManager
    public void removePlayerStatusListener(HwAudioStatusListener hwAudioStatusListener) throws RemoteException {
        WiseAudioManagerImpl wiseAudioManagerImpl = this.a;
        if (wiseAudioManagerImpl != null) {
            wiseAudioManagerImpl.removePlayerStatusListener(hwAudioStatusListener == null ? null : new g(hwAudioStatusListener));
        }
    }
}
