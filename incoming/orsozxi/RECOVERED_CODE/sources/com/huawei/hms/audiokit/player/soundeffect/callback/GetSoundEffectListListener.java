package com.huawei.hms.audiokit.player.soundeffect.callback;

import com.huawei.hms.audiokit.soundeffect.bean.HwAudioEffectItem;
import java.util.List;

/* loaded from: classes2.dex */
public interface GetSoundEffectListListener {
    void onError(int i, String str);

    void onSuccess(List<HwAudioEffectItem> list);
}
