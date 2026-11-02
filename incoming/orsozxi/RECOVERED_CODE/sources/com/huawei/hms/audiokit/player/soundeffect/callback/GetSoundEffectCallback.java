package com.huawei.hms.audiokit.player.soundeffect.callback;

import com.huawei.hms.audiokit.api.soundeffect.callback.GetEffectCallback;
import com.huawei.hms.audiokit.soundeffect.bean.HwAudioEffectItem;

/* loaded from: classes2.dex */
public class GetSoundEffectCallback extends GetEffectCallback.Stub {
    private GetSoundEffectListener a;

    public GetSoundEffectCallback(GetSoundEffectListener getSoundEffectListener) {
        this.a = getSoundEffectListener;
    }

    @Override // com.huawei.hms.audiokit.api.soundeffect.callback.GetEffectCallback
    public void onError(int i, String str) {
        this.a.onError(i, str);
    }

    @Override // com.huawei.hms.audiokit.api.soundeffect.callback.GetEffectCallback
    public void onSuccess(HwAudioEffectItem hwAudioEffectItem) {
        this.a.onSuccess(hwAudioEffectItem);
    }
}
