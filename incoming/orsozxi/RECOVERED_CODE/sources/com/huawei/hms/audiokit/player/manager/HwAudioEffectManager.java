package com.huawei.hms.audiokit.player.manager;

import android.os.RemoteException;
import android.util.Log;
import com.huawei.hms.api.IRemoteCreator;
import com.huawei.hms.api.manager.WiseAudioManagerImpl;
import com.huawei.hms.audiokit.manager.WiseAudioEffectManager;
import com.huawei.hms.audiokit.player.soundeffect.callback.GetSoundEffectCallback;
import com.huawei.hms.audiokit.player.soundeffect.callback.GetSoundEffectListCallback;
import com.huawei.hms.audiokit.player.soundeffect.callback.SetSoundEffectCallback;
import com.huawei.hms.audiokit.soundeffect.bean.HwAudioEffectItem;

/* loaded from: classes2.dex */
public final class HwAudioEffectManager {
    private static final com.huawei.hms.audiokit.f<HwAudioEffectManager> a = new c();
    private WiseAudioEffectManager b;

    private HwAudioEffectManager() {
        WiseAudioManagerImpl manager;
        try {
            IRemoteCreator iRemoteCreatorC = com.huawei.hms.audiokit.b.c();
            if (iRemoteCreatorC == null || (manager = iRemoteCreatorC.getManager()) == null) {
                return;
            }
            this.b = manager.getEffectManager();
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "HwAudioEffectManager RemoteException");
        }
    }

    /* synthetic */ HwAudioEffectManager(c cVar) {
        this();
    }

    static HwAudioEffectManager a() {
        return a.b();
    }

    public HwAudioEffectItem createEffect(String str) {
        Log.i("HwAudioEffectManager", "createEffect");
        try {
            if (this.b != null) {
                return this.b.createEffect(str);
            }
            return null;
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "createEffect RemoteException");
            return null;
        }
    }

    public HwAudioEffectItem createEmptyEffect() {
        Log.i("HwAudioEffectManager", "createEmptyEffect");
        try {
            if (this.b != null) {
                return this.b.createEmptyEffect();
            }
            return null;
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "createEmptyEffect RemoteException");
            return null;
        }
    }

    public int deleteEffect(String str) {
        Log.i("HwAudioEffectManager", "deleteEffect");
        try {
            if (this.b != null) {
                return this.b.deleteEffect(str);
            }
            return -1;
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "deleteEffect RemoteException");
            return -1;
        }
    }

    public void getChosenSoundEffect(GetSoundEffectListCallback getSoundEffectListCallback) {
        Log.i("HwAudioEffectManager", "getChosenSoundEffect");
        try {
            if (this.b != null) {
                this.b.getChosenSoundEffect(getSoundEffectListCallback);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "getChosenSoundEffect RemoteException");
        }
    }

    public void getEffect(String str, GetSoundEffectCallback getSoundEffectCallback) {
        Log.i("HwAudioEffectManager", "getEffect");
        try {
            if (this.b != null) {
                this.b.getEffect(str, getSoundEffectCallback);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "getEffect RemoteException");
        }
    }

    public HwAudioEffectItem getNowUsingSoundEffect() {
        Log.i("HwAudioEffectManager", "getNowUsingSoundEffect");
        try {
            if (this.b != null) {
                return this.b.getNowUsingSoundEffect();
            }
            return null;
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "getNowUsingSoundEffect RemoteException");
            return null;
        }
    }

    public void getOrdinarySoundEffect(GetSoundEffectListCallback getSoundEffectListCallback) {
        Log.i("HwAudioEffectManager", "getOrdinarySoundEffect");
        try {
            if (this.b != null) {
                this.b.getOrdinarySoundEffect(getSoundEffectListCallback);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "getOrdinarySoundEffect RemoteException");
        }
    }

    public String getSoundChosenActiveResId() {
        Log.i("HwAudioEffectManager", "getSoundChosenActiveResId");
        try {
            if (this.b != null) {
                return this.b.getSoundChosenActiveResId();
            }
            return null;
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "getSoundChosenActiveResId RemoteException");
            return null;
        }
    }

    public boolean getSoundChosenLightOn() {
        Log.i("HwAudioEffectManager", "getSoundChosenLightOn");
        try {
            if (this.b != null) {
                return this.b.getSoundChosenLightOn();
            }
            return false;
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "getSoundChosenLightOn RemoteException");
            return false;
        }
    }

    public String getSoundOrdinaryActiveResId() {
        Log.i("HwAudioEffectManager", "getSoundOrdinaryActiveResId");
        try {
            if (this.b != null) {
                return this.b.getSoundOrdinaryActiveResId();
            }
            return null;
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "getSoundOrdinaryActiveResId RemoteException");
            return null;
        }
    }

    public boolean getSoundOrdinaryLightOn() {
        Log.i("HwAudioEffectManager", "getSoundOrdinaryLightOn");
        try {
            if (this.b != null) {
                return this.b.getSoundOrdinaryLightOn();
            }
            return false;
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "getSoundOrdinaryLightOn RemoteException");
            return false;
        }
    }

    public int insertEffect(HwAudioEffectItem hwAudioEffectItem) {
        Log.i("HwAudioEffectManager", "insertEffect");
        try {
            if (this.b != null) {
                return this.b.insertEffect(hwAudioEffectItem);
            }
            return -1;
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "insertEffect RemoteException");
            return -1;
        }
    }

    public boolean isEffectOn() {
        Log.i("HwAudioEffectManager", "isEffectOn");
        try {
            if (this.b != null) {
                return this.b.isEffectOn();
            }
            return false;
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "isEffectOn RemoteException");
            return false;
        }
    }

    public int renameEffect(String str, String str2) {
        Log.i("HwAudioEffectManager", "renameEffect");
        try {
            if (this.b != null) {
                return this.b.renameEffect(str, str2);
            }
            return -1;
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "renameEffect RemoteException");
            return -1;
        }
    }

    public int setEQParams(int[] iArr) {
        Log.i("HwAudioEffectManager", "setEQParams");
        try {
            if (this.b != null) {
                return this.b.setEQParams(iArr);
            }
            return -1;
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "setEQParams RemoteException");
            return -1;
        }
    }

    public void setEffect(HwAudioEffectItem hwAudioEffectItem, SetSoundEffectCallback setSoundEffectCallback) {
        Log.i("HwAudioEffectManager", "setEffect");
        try {
            if (this.b != null) {
                this.b.setEffect(hwAudioEffectItem, setSoundEffectCallback);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "setEffect RemoteException");
        }
    }

    public void setEffectSwitch(boolean z) {
        Log.i("HwAudioEffectManager", "setEffectSwitch = " + z);
        try {
            if (this.b != null) {
                this.b.setEffectSwitch(z);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "setEffectSwitch RemoteException");
        }
    }

    public int setEqSuperChannelBalance(int i) {
        Log.i("HwAudioEffectManager", "setEqSuperChannelBalance");
        try {
            if (this.b != null) {
                return this.b.setEqSuperChannelBalance(i);
            }
            return -1;
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "setEqSuperChannelBalance RemoteException");
            return -1;
        }
    }

    public int setEqSuperEnvironmentalSoundEffect(int i) {
        Log.i("HwAudioEffectManager", "setEqSuperEnvironmentalSoundEffect");
        try {
            if (this.b != null) {
                return this.b.setEqSuperEnvironmentalSoundEffect(i);
            }
            return -1;
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "setEqSuperEnvironmentalSoundEffect RemoteException");
            return -1;
        }
    }

    public int setEqUltraLowStress(int i) {
        Log.i("HwAudioEffectManager", "setEqUltraLowStress");
        try {
            if (this.b != null) {
                return this.b.setEqUltraLowStress(i);
            }
            return -1;
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "setEqUltraLowStress RemoteException");
            return -1;
        }
    }

    public void setSoundChosenActiveResId(String str) {
        Log.i("HwAudioEffectManager", "setSoundChosenActiveResId");
        try {
            if (this.b != null) {
                this.b.setSoundChosenActiveResId(str);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "setSoundChosenActiveResId RemoteException");
        }
    }

    public void setSoundChosenLightOn(boolean z) {
        Log.i("HwAudioEffectManager", "getSoundChosenLightOn");
        try {
            if (this.b != null) {
                this.b.setSoundChosenLightOn(z);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "getSoundChosenLightOn RemoteException");
        }
    }

    public void setSoundOrdinaryActiveResId(String str) {
        Log.i("HwAudioEffectManager", "setSoundOrdinaryActiveResId");
        try {
            if (this.b != null) {
                this.b.setSoundOrdinaryActiveResId(str);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "setSoundOrdinaryActiveResId RemoteException");
        }
    }

    public void setSoundOrdinaryLightOn(boolean z) {
        Log.i("HwAudioEffectManager", "setSoundOrdinaryLightOn");
        try {
            if (this.b != null) {
                this.b.setSoundOrdinaryLightOn(z);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioEffectManager", "setSoundOrdinaryLightOn RemoteException");
        }
    }
}
