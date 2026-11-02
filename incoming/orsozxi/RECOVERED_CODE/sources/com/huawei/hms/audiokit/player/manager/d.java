package com.huawei.hms.audiokit.player.manager;

import android.os.RemoteException;
import android.util.Log;
import com.huawei.hms.api.IRemoteCreator;
import com.huawei.hms.api.errorcode.HwAudioErrorCode;
import com.huawei.hms.audiokit.player.callback.HwAudioConfigCallBack;
import com.huawei.hms.feature.dynamic.ObjectWrapper;

/* loaded from: classes2.dex */
class d implements com.huawei.hms.audiokit.player.callback.a {
    final /* synthetic */ HwAudioPlayerConfig a;
    final /* synthetic */ HwAudioConfigCallBack b;

    d(HwAudioPlayerConfig hwAudioPlayerConfig, HwAudioConfigCallBack hwAudioConfigCallBack) {
        this.a = hwAudioPlayerConfig;
        this.b = hwAudioConfigCallBack;
    }

    @Override // com.huawei.hms.audiokit.player.callback.a
    public void onError(int i) {
        Log.e("HwAudioManagerFactory", "initRemote error:" + i);
        this.b.onError(i);
    }

    @Override // com.huawei.hms.audiokit.player.callback.a
    public void onSuccess() {
        try {
            IRemoteCreator iRemoteCreatorC = com.huawei.hms.audiokit.b.c();
            if (iRemoteCreatorC != null) {
                iRemoteCreatorC.createHwAudioManager(ObjectWrapper.wrap(this.a.getContext()), HwAudioManagerFactory.b(this.a));
                this.b.onSuccess(new HwAudioManagerImpl());
            } else {
                Log.w("HwAudioManagerFactory", "RemoteCreator is null");
                this.b.onError(HwAudioErrorCode.ERROR_UNKNOWN);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioManagerFactory", "createHwAudioManager err");
            this.b.onError(HwAudioErrorCode.ERROR_UNKNOWN);
        }
    }
}
