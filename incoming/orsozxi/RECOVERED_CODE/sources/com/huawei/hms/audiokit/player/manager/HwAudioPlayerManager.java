package com.huawei.hms.audiokit.player.manager;

import android.os.RemoteException;
import android.util.Log;
import com.huawei.hms.api.IRemoteCreator;
import com.huawei.hms.api.bean.HwAudioPlayItem;
import com.huawei.hms.api.manager.WiseAudioManagerImpl;
import com.huawei.hms.api.manager.WiseAudioPlayerManager;
import java.util.List;

/* loaded from: classes2.dex */
public final class HwAudioPlayerManager {
    private static final com.huawei.hms.audiokit.f<HwAudioPlayerManager> a = new e();
    private WiseAudioPlayerManager b;

    private HwAudioPlayerManager() {
        WiseAudioManagerImpl manager;
        try {
            IRemoteCreator iRemoteCreatorC = com.huawei.hms.audiokit.b.c();
            if (iRemoteCreatorC == null || (manager = iRemoteCreatorC.getManager()) == null) {
                return;
            }
            this.b = manager.getPlayerManager();
        } catch (RemoteException unused) {
            Log.e("HwAudioPlayerManager", "RemoteException");
        }
    }

    /* synthetic */ HwAudioPlayerManager(e eVar) {
        this();
    }

    static HwAudioPlayerManager a() {
        return a.b();
    }

    public void cancelNotification() {
        Log.i("HwAudioPlayerManager", "cancelNotification");
        try {
            if (this.b != null) {
                this.b.cancelNotification();
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioPlayerManager", "RemoteException");
        }
    }

    public int getBufferPercent() {
        try {
            if (this.b != null) {
                return this.b.getBufferPercent();
            }
            return 0;
        } catch (RemoteException unused) {
            Log.e("HwAudioPlayerManager", "RemoteException");
            return 0;
        }
    }

    public long getDuration() {
        try {
            if (this.b != null) {
                return this.b.getDuration();
            }
            return 0L;
        } catch (RemoteException unused) {
            Log.e("HwAudioPlayerManager", "RemoteException");
            return 0L;
        }
    }

    public long getOffsetTime() {
        try {
            if (this.b != null) {
                return this.b.getOffsetTime();
            }
            return 0L;
        } catch (RemoteException unused) {
            Log.e("HwAudioPlayerManager", "RemoteException");
            return 0L;
        }
    }

    public int getPlayMode() {
        try {
            if (this.b != null) {
                return this.b.getPlayMode();
            }
            return 0;
        } catch (RemoteException unused) {
            Log.e("HwAudioPlayerManager", "RemoteException");
            return 0;
        }
    }

    public float getPlaySpeed() {
        Log.i("HwAudioPlayerManager", "getPlaySpeed");
        try {
            if (this.b != null) {
                return this.b.getPlaySpeed();
            }
            return 0.0f;
        } catch (RemoteException unused) {
            Log.e("HwAudioPlayerManager", "RemoteException");
            return 0.0f;
        }
    }

    public boolean isBuffering() {
        try {
            if (this.b != null) {
                return this.b.isBuffering();
            }
            return false;
        } catch (RemoteException unused) {
            Log.e("HwAudioPlayerManager", "RemoteException");
            return false;
        }
    }

    public boolean isPlaying() {
        try {
            if (this.b != null) {
                return this.b.isPlaying();
            }
            return false;
        } catch (RemoteException unused) {
            Log.e("HwAudioPlayerManager", "RemoteException");
            return false;
        }
    }

    public void pause() {
        Log.i("HwAudioPlayerManager", "pause");
        try {
            if (this.b != null) {
                this.b.pause();
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioPlayerManager", "RemoteException");
        }
    }

    public void play() {
        Log.i("HwAudioPlayerManager", "play");
        try {
            if (this.b != null) {
                this.b.play();
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioPlayerManager", "RemoteException");
        }
    }

    public void play(int i) {
        Log.i("HwAudioPlayerManager", "play, position: " + i);
        try {
            if (this.b != null) {
                this.b.playByPosition(i);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioPlayerManager", "RemoteException");
        }
    }

    public void play(int i, int i2) {
        Log.i("HwAudioPlayerManager", "play, position: " + i + ", offsetTime: " + i2);
        try {
            if (this.b != null) {
                this.b.playByPositionAndOffsetTime(i, i2);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioPlayerManager", "RemoteException");
        }
    }

    public void playList(List<HwAudioPlayItem> list, int i, int i2) {
        Log.i("HwAudioPlayerManager", "playList");
        try {
            if (this.b != null) {
                this.b.playList(list, i, i2);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioPlayerManager", "RemoteException");
        }
    }

    public void playNext() {
        Log.i("HwAudioPlayerManager", "playNext");
        try {
            if (this.b != null) {
                this.b.playNext();
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioPlayerManager", "RemoteException");
        }
    }

    public void playPre() {
        Log.i("HwAudioPlayerManager", "playPre");
        try {
            if (this.b != null) {
                this.b.playPre();
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioPlayerManager", "RemoteException");
        }
    }

    public void seekTo(int i) {
        Log.i("HwAudioPlayerManager", "seekTo, pos: " + i);
        try {
            if (this.b != null) {
                this.b.seekTo(i);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioPlayerManager", "RemoteException");
        }
    }

    public void setPlayMode(int i) {
        Log.i("HwAudioPlayerManager", "setPlayMode");
        try {
            if (this.b != null) {
                this.b.setPlayMode(i);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioPlayerManager", "RemoteException");
        }
    }

    public void setPlaySpeed(float f) {
        Log.i("HwAudioPlayerManager", "setPlaySpeed");
        try {
            if (this.b != null) {
                this.b.setPlaySpeed(f);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioPlayerManager", "RemoteException");
        }
    }

    public void setVolume(int i) {
        Log.i("HwAudioPlayerManager", "setVolume");
        try {
            if (this.b != null) {
                this.b.setVolume(i / 100.0f);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioPlayerManager", "RemoteException");
        }
    }

    public void stop() {
        Log.i("HwAudioPlayerManager", "stop");
        try {
            if (this.b != null) {
                this.b.stop();
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioPlayerManager", "RemoteException");
        }
    }
}
