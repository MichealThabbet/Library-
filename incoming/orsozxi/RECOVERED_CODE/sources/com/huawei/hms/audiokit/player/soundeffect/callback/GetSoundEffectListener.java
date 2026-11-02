package com.huawei.hms.audiokit.player.soundeffect.callback;

import com.huawei.hms.audiokit.soundeffect.bean.HwAudioEffectItem;

/* loaded from: classes2.dex */
public interface GetSoundEffectListener {
    void onError(int i, String str);

    void onSuccess(HwAudioEffectItem hwAudioEffectItem);
}
