package com.huawei.hms.audiokit.player.soundeffect.callback;

import com.huawei.hms.audiokit.api.soundeffect.callback.SetEffectCallback;

/* loaded from: classes2.dex */
public class SetSoundEffectCallback extends SetEffectCallback.Stub {
    private SetSoundEffectListener a;

    public SetSoundEffectCallback(SetSoundEffectListener setSoundEffectListener) {
        this.a = setSoundEffectListener;
    }

    @Override // com.huawei.hms.audiokit.api.soundeffect.callback.SetEffectCallback
    public void onError(int i, String str) {
        this.a.onError(i, str);
    }

    @Override // com.huawei.hms.audiokit.api.soundeffect.callback.SetEffectCallback
    public void onSuccess(int i) {
        this.a.onSuccess(i);
    }
}
