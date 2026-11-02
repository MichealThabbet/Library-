package com.huawei.hms.audiokit.player.manager;

import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface HwAudioManager {
    void addPlayerStatusListener(HwAudioStatusListener hwAudioStatusListener) throws RemoteException;

    HwAudioConfigManager getConfigManager();

    HwAudioEffectManager getEffectManager();

    HwAudioPlayerManager getPlayerManager();

    HwAudioQueueManager getQueueManager();

    void removePlayerStatusListener(HwAudioStatusListener hwAudioStatusListener) throws RemoteException;
}
