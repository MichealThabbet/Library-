package com.huawei.hms.audiokit.player.manager;

import com.huawei.hms.api.errorcode.HwAudioErrorCode;
import java.util.HashMap;

/* loaded from: classes2.dex */
class i extends HashMap<Integer, Integer> {
    i() {
        put(201, Integer.valueOf(HwAudioErrorCode.ERROR_MODULE_UPGRADE_REPEATLY_INTERRUPT));
        put(202, Integer.valueOf(HwAudioErrorCode.ERROR_MODULE_UPGRADE_LATER_OR_NONETWORK));
        put(203, Integer.valueOf(HwAudioErrorCode.ERROR_MODULE_UPGRADE_CLICK_BACK));
        put(204, Integer.valueOf(HwAudioErrorCode.ERROR_MODULE_UPGRADE_CLICK_CANCEL));
        put(205, Integer.valueOf(HwAudioErrorCode.ERROR_MODULE_UPGRADE_SPACE_NOT_ENOUGH));
        put(206, Integer.valueOf(HwAudioErrorCode.ERROR_MODULE_UPGRADE_NETWORK_ERROR));
        put(207, Integer.valueOf(HwAudioErrorCode.ERROR_MODULE_UPGRADE_UNKNOWN));
        put(208, Integer.valueOf(HwAudioErrorCode.ERROR_MODULE_UPGRADE_HMS_DONE));
        put(209, Integer.valueOf(HwAudioErrorCode.ERROR_MODULE_UPGRADE_CLICK_NETWORK_SETTING));
        put(210, Integer.valueOf(HwAudioErrorCode.ERROR_MODULE_UPGRADE_DISPLAY_OK));
    }
}
