package com.huawei.hms.audiokit.player.manager;

import com.huawei.hms.api.bean.HwAudioPlayItem;
import java.util.List;

/* loaded from: classes2.dex */
public interface HwAudioStatusListener {
    void onBufferProgress(int i);

    void onPlayCompleted(boolean z);

    void onPlayError(int i, boolean z);

    void onPlayProgress(long j, long j2);

    void onPlayStateChange(boolean z, boolean z2);

    void onQueueChanged(List<HwAudioPlayItem> list);

    void onSongChange(HwAudioPlayItem hwAudioPlayItem);
}
