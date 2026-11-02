package com.huawei.hms.audiokit.player.soundeffect.callback;

import com.huawei.hms.audiokit.api.soundeffect.callback.GetSoundEffectListCallback;
import com.huawei.hms.audiokit.soundeffect.bean.HwAudioEffectItem;
import java.util.List;

/* loaded from: classes2.dex */
public class GetSoundEffectListCallback extends GetSoundEffectListCallback.Stub {
    private GetSoundEffectListListener a;

    public GetSoundEffectListCallback(GetSoundEffectListListener getSoundEffectListListener) {
        this.a = getSoundEffectListListener;
    }

    @Override // com.huawei.hms.audiokit.api.soundeffect.callback.GetSoundEffectListCallback
    public void onError(int i, String str) {
        this.a.onError(i, str);
    }

    @Override // com.huawei.hms.audiokit.api.soundeffect.callback.GetSoundEffectListCallback
    public void onSuccess(List<HwAudioEffectItem> list) {
        this.a.onSuccess(list);
    }
}
