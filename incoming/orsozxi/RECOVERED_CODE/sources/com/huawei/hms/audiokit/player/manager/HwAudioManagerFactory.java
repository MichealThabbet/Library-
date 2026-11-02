package com.huawei.hms.audiokit.player.manager;

import android.content.Context;
import android.os.Build;
import android.os.RemoteException;
import android.util.Log;
import com.huawei.hms.api.IRemoteCreator;
import com.huawei.hms.api.config.HwAudioConfigContants;
import com.huawei.hms.api.config.WiseAudioPlayerConfig;
import com.huawei.hms.api.errorcode.HwAudioErrorCode;
import com.huawei.hms.audiokit.player.callback.HwAudioConfigCallBack;
import com.huawei.hms.feature.dynamic.ObjectWrapper;

/* loaded from: classes2.dex */
public class HwAudioManagerFactory {
    private static HwAudioConfigCallBack a;
    private static HwAudioPlayerConfig b;

    static HwAudioConfigCallBack a() {
        return a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static WiseAudioPlayerConfig b(HwAudioPlayerConfig hwAudioPlayerConfig) {
        WiseAudioPlayerConfig wiseAudioPlayerConfig = new WiseAudioPlayerConfig();
        wiseAudioPlayerConfig.setPlayCacheSize(hwAudioPlayerConfig.getPlayCacheSize()).setHandleAudioFocus(hwAudioPlayerConfig.isHandleAudioFocus()).setDebugPath(hwAudioPlayerConfig.getDebugPath()).setReleasePath(hwAudioPlayerConfig.getReleasePath()).setDebugMode(hwAudioPlayerConfig.isDebugMode()).setSdklevel(7);
        return wiseAudioPlayerConfig;
    }

    static HwAudioPlayerConfig b() {
        return b;
    }

    public static void createHwAudioManager(HwAudioPlayerConfig hwAudioPlayerConfig, HwAudioConfigCallBack hwAudioConfigCallBack) throws IllegalArgumentException {
        Log.i("HwAudioManagerFactory", "createWiseAudioManager");
        if (hwAudioPlayerConfig == null || hwAudioPlayerConfig.getContext() == null) {
            Log.e("HwAudioManagerFactory", "context is null");
            if (hwAudioConfigCallBack != null) {
                hwAudioConfigCallBack.onError(HwAudioErrorCode.ERROR_ILLEGAL_PARAM);
                return;
            }
            return;
        }
        if (Build.VERSION.SDK_INT < 19) {
            if (hwAudioConfigCallBack != null) {
                hwAudioConfigCallBack.onError(HwAudioErrorCode.ERROR_NOT_SUPPORT_SDK_VERSION);
                return;
            }
            return;
        }
        Context context = hwAudioPlayerConfig.getContext();
        if (context == null || context.getApplicationContext() == null) {
            Log.e("HwAudioManagerFactory", "context is null or applicationContext is null");
            return;
        }
        HwAudioConfigContants.setSdkLevel(7);
        Context applicationContext = context.getApplicationContext();
        hwAudioPlayerConfig.setContext(applicationContext);
        a = hwAudioConfigCallBack;
        b = hwAudioPlayerConfig;
        if (a.a(applicationContext, 50000000) != 0) {
            if (hwAudioConfigCallBack != null) {
                hwAudioConfigCallBack.onError(HwAudioErrorCode.ERROR_NOT_SUPPORT_HMS_VERSION);
            }
        } else {
            if (hwAudioConfigCallBack != null) {
                Log.i("HwAudioManagerFactory", "createHwAudioManager with callback");
                com.huawei.hms.audiokit.b.a(applicationContext, new d(hwAudioPlayerConfig, hwAudioConfigCallBack));
                return;
            }
            Log.i("HwAudioManagerFactory", "createHwAudioManager without callback");
            if (com.huawei.hms.audiokit.b.a(hwAudioPlayerConfig.getContext(), null)) {
                try {
                    IRemoteCreator iRemoteCreatorC = com.huawei.hms.audiokit.b.c();
                    if (iRemoteCreatorC != null) {
                        iRemoteCreatorC.createHwAudioManager(ObjectWrapper.wrap(hwAudioPlayerConfig.getContext()), b(hwAudioPlayerConfig));
                        new HwAudioManagerImpl();
                    }
                } catch (RemoteException unused) {
                    Log.e("HwAudioManagerFactory", "createHwAudioManager err");
                }
            }
        }
    }
}
