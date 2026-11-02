package com.huawei.hms.audiokit.player.manager;

import android.os.IBinder;
import android.os.RemoteException;
import com.huawei.hms.api.bean.HwAudioPlayItem;
import com.huawei.hms.api.listener.WiseAudioStatusListener;
import java.util.List;

/* loaded from: classes2.dex */
class g extends WiseAudioStatusListener.Stub {
    private HwAudioStatusListener a;

    g(HwAudioStatusListener hwAudioStatusListener) {
        this.a = hwAudioStatusListener;
    }

    @Override // com.huawei.hms.api.listener.WiseAudioStatusListener.Stub, android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    public boolean equals(Object obj) {
        HwAudioStatusListener hwAudioStatusListener = this.a;
        return hwAudioStatusListener != null ? hwAudioStatusListener.equals(obj) : super.equals(obj);
    }

    public int hashCode() {
        HwAudioStatusListener hwAudioStatusListener = this.a;
        return hwAudioStatusListener != null ? hwAudioStatusListener.hashCode() : super.hashCode();
    }

    @Override // com.huawei.hms.api.listener.WiseAudioStatusListener
    public void onBufferProgress(int i) throws RemoteException {
        this.a.onBufferProgress(i);
    }

    @Override // com.huawei.hms.api.listener.WiseAudioStatusListener
    public void onPlayCompleted(boolean z) throws RemoteException {
        this.a.onPlayCompleted(z);
    }

    @Override // com.huawei.hms.api.listener.WiseAudioStatusListener
    public void onPlayError(int i, boolean z) throws RemoteException {
        this.a.onPlayError(i, z);
    }

    @Override // com.huawei.hms.api.listener.WiseAudioStatusListener
    public void onPlayProgress(long j, long j2) throws RemoteException {
        this.a.onPlayProgress(j, j2);
    }

    @Override // com.huawei.hms.api.listener.WiseAudioStatusListener
    public void onPlayStateChange(boolean z, boolean z2) throws RemoteException {
        this.a.onPlayStateChange(z, z2);
    }

    @Override // com.huawei.hms.api.listener.WiseAudioStatusListener
    public void onQueueChanged(List<HwAudioPlayItem> list) throws RemoteException {
        this.a.onQueueChanged(list);
    }

    @Override // com.huawei.hms.api.listener.WiseAudioStatusListener
    public void onSongChange(HwAudioPlayItem hwAudioPlayItem) throws RemoteException {
        this.a.onSongChange(hwAudioPlayItem);
    }
}
