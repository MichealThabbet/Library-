package com.huawei.hms.audiokit.player.manager;

import android.os.RemoteException;
import android.util.Log;
import com.huawei.hms.api.IRemoteCreator;
import com.huawei.hms.api.bean.HwAudioPlayItem;
import com.huawei.hms.api.manager.WiseAudioManagerImpl;
import com.huawei.hms.api.manager.WiseAudioQueueManager;
import java.util.List;

/* loaded from: classes2.dex */
public final class HwAudioQueueManager {
    private static final com.huawei.hms.audiokit.f<HwAudioQueueManager> a = new f();
    private WiseAudioQueueManager b;

    private HwAudioQueueManager() {
        WiseAudioManagerImpl manager;
        try {
            IRemoteCreator iRemoteCreatorC = com.huawei.hms.audiokit.b.c();
            if (iRemoteCreatorC == null || (manager = iRemoteCreatorC.getManager()) == null) {
                return;
            }
            this.b = manager.getQueueManager();
        } catch (RemoteException unused) {
            Log.e("HwAudioQueueManager", "RemoteException");
        }
    }

    /* synthetic */ HwAudioQueueManager(f fVar) {
        this();
    }

    static HwAudioQueueManager a() {
        return a.b();
    }

    public void addPlayItem(HwAudioPlayItem hwAudioPlayItem, int i) {
        Log.i("HwAudioQueueManager", "addPlayItem");
        try {
            if (this.b != null) {
                this.b.addPlayItem(hwAudioPlayItem, i);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioQueueManager", "RemoteException");
        }
    }

    public void addPlayItemList(List<HwAudioPlayItem> list, int i) {
        Log.i("HwAudioQueueManager", "addPlayItemList");
        try {
            if (this.b != null) {
                this.b.addPlayItemList(list, i);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioQueueManager", "RemoteException");
        }
    }

    public List<HwAudioPlayItem> getAllPlaylist() {
        Log.i("HwAudioQueueManager", "getAllPlaylist");
        try {
            if (this.b != null) {
                return this.b.getAllPlaylist();
            }
            return null;
        } catch (RemoteException unused) {
            Log.e("HwAudioQueueManager", "RemoteException");
            return null;
        }
    }

    public int getCurrentIndex() {
        Log.i("HwAudioQueueManager", "getCurrentIndex");
        try {
            if (this.b != null) {
                return this.b.getCurrentIndex();
            }
            return 0;
        } catch (RemoteException unused) {
            Log.e("HwAudioQueueManager", "RemoteException");
            return 0;
        }
    }

    public HwAudioPlayItem getCurrentPlayItem() {
        Log.i("HwAudioQueueManager", "getCurrentPlayItem");
        try {
            if (this.b != null) {
                return this.b.getCurrentPlayItem();
            }
            return null;
        } catch (RemoteException unused) {
            Log.e("HwAudioQueueManager", "RemoteException");
            return null;
        }
    }

    public boolean isQueueEmpty() {
        Log.i("HwAudioQueueManager", "isQueueEmpty");
        try {
            if (this.b != null) {
                return this.b.isQueueEmpty();
            }
            return false;
        } catch (RemoteException unused) {
            Log.e("HwAudioQueueManager", "RemoteException");
            return false;
        }
    }

    public void removeListByIndex(int i) {
        Log.i("HwAudioQueueManager", "removeListByIndex, index: " + i);
        try {
            if (this.b != null) {
                this.b.removeListByIndex(i);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioQueueManager", "RemoteException");
        }
    }

    public void removeListByItem(HwAudioPlayItem hwAudioPlayItem) {
        Log.i("HwAudioQueueManager", "removeListByItem");
        try {
            if (this.b != null) {
                this.b.removeListByItem(hwAudioPlayItem);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioQueueManager", "RemoteException");
        }
    }

    public void setPlaylist(List<HwAudioPlayItem> list) {
        Log.i("HwAudioQueueManager", "setPlaylist");
        try {
            if (this.b != null) {
                this.b.setPlaylist(list);
            }
        } catch (RemoteException unused) {
            Log.e("HwAudioQueueManager", "RemoteException");
        }
    }
}
