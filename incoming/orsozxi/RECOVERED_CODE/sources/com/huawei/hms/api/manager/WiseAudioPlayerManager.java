package com.huawei.hms.api.manager;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.huawei.hms.api.bean.HwAudioPlayItem;
import java.util.List;

/* loaded from: classes2.dex */
public interface WiseAudioPlayerManager extends IInterface {

    public static class Default implements WiseAudioPlayerManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
        public void cancelNotification() throws RemoteException {
        }

        @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
        public int getBufferPercent() throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
        public long getDuration() throws RemoteException {
            return 0L;
        }

        @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
        public long getOffsetTime() throws RemoteException {
            return 0L;
        }

        @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
        public int getPlayMode() throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
        public float getPlaySpeed() throws RemoteException {
            return 0.0f;
        }

        @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
        public boolean isBuffering() throws RemoteException {
            return false;
        }

        @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
        public boolean isPlaying() throws RemoteException {
            return false;
        }

        @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
        public void pause() throws RemoteException {
        }

        @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
        public void play() throws RemoteException {
        }

        @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
        public void playByPosition(int i) throws RemoteException {
        }

        @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
        public void playByPositionAndOffsetTime(int i, int i2) throws RemoteException {
        }

        @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
        public void playList(List<HwAudioPlayItem> list, int i, int i2) throws RemoteException {
        }

        @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
        public void playNext() throws RemoteException {
        }

        @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
        public void playPre() throws RemoteException {
        }

        @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
        public void seekTo(int i) throws RemoteException {
        }

        @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
        public void setPlayMode(int i) throws RemoteException {
        }

        @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
        public void setPlaySpeed(float f) throws RemoteException {
        }

        @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
        public void setVolume(float f) throws RemoteException {
        }

        @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
        public void stop() throws RemoteException {
        }
    }

    void cancelNotification() throws RemoteException;

    int getBufferPercent() throws RemoteException;

    long getDuration() throws RemoteException;

    long getOffsetTime() throws RemoteException;

    int getPlayMode() throws RemoteException;

    float getPlaySpeed() throws RemoteException;

    boolean isBuffering() throws RemoteException;

    boolean isPlaying() throws RemoteException;

    void pause() throws RemoteException;

    void play() throws RemoteException;

    void playByPosition(int i) throws RemoteException;

    void playByPositionAndOffsetTime(int i, int i2) throws RemoteException;

    void playList(List<HwAudioPlayItem> list, int i, int i2) throws RemoteException;

    void playNext() throws RemoteException;

    void playPre() throws RemoteException;

    void seekTo(int i) throws RemoteException;

    void setPlayMode(int i) throws RemoteException;

    void setPlaySpeed(float f) throws RemoteException;

    void setVolume(float f) throws RemoteException;

    void stop() throws RemoteException;

    public static abstract class Stub extends Binder implements WiseAudioPlayerManager {
        private static final String DESCRIPTOR = "com.huawei.hms.api.manager.WiseAudioPlayerManager";
        static final int TRANSACTION_cancelNotification = 18;
        static final int TRANSACTION_getBufferPercent = 16;
        static final int TRANSACTION_getDuration = 17;
        static final int TRANSACTION_getOffsetTime = 15;
        static final int TRANSACTION_getPlayMode = 14;
        static final int TRANSACTION_getPlaySpeed = 20;
        static final int TRANSACTION_isBuffering = 10;
        static final int TRANSACTION_isPlaying = 9;
        static final int TRANSACTION_pause = 5;
        static final int TRANSACTION_play = 2;
        static final int TRANSACTION_playByPosition = 3;
        static final int TRANSACTION_playByPositionAndOffsetTime = 4;
        static final int TRANSACTION_playList = 1;
        static final int TRANSACTION_playNext = 8;
        static final int TRANSACTION_playPre = 7;
        static final int TRANSACTION_seekTo = 11;
        static final int TRANSACTION_setPlayMode = 12;
        static final int TRANSACTION_setPlaySpeed = 19;
        static final int TRANSACTION_setVolume = 13;
        static final int TRANSACTION_stop = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static WiseAudioPlayerManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof WiseAudioPlayerManager)) {
                return (WiseAudioPlayerManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    playList(parcel.createTypedArrayList(HwAudioPlayItem.CREATOR), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    play();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    playByPosition(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    playByPositionAndOffsetTime(parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    pause();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    stop();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    playPre();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    playNext();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zIsPlaying = isPlaying();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsPlaying ? 1 : 0);
                    return true;
                case 10:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zIsBuffering = isBuffering();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsBuffering ? 1 : 0);
                    return true;
                case 11:
                    parcel.enforceInterface(DESCRIPTOR);
                    seekTo(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 12:
                    parcel.enforceInterface(DESCRIPTOR);
                    setPlayMode(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 13:
                    parcel.enforceInterface(DESCRIPTOR);
                    setVolume(parcel.readFloat());
                    parcel2.writeNoException();
                    return true;
                case 14:
                    parcel.enforceInterface(DESCRIPTOR);
                    int playMode = getPlayMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(playMode);
                    return true;
                case 15:
                    parcel.enforceInterface(DESCRIPTOR);
                    long offsetTime = getOffsetTime();
                    parcel2.writeNoException();
                    parcel2.writeLong(offsetTime);
                    return true;
                case 16:
                    parcel.enforceInterface(DESCRIPTOR);
                    int bufferPercent = getBufferPercent();
                    parcel2.writeNoException();
                    parcel2.writeInt(bufferPercent);
                    return true;
                case 17:
                    parcel.enforceInterface(DESCRIPTOR);
                    long duration = getDuration();
                    parcel2.writeNoException();
                    parcel2.writeLong(duration);
                    return true;
                case 18:
                    parcel.enforceInterface(DESCRIPTOR);
                    cancelNotification();
                    parcel2.writeNoException();
                    return true;
                case 19:
                    parcel.enforceInterface(DESCRIPTOR);
                    setPlaySpeed(parcel.readFloat());
                    parcel2.writeNoException();
                    return true;
                case 20:
                    parcel.enforceInterface(DESCRIPTOR);
                    float playSpeed = getPlaySpeed();
                    parcel2.writeNoException();
                    parcel2.writeFloat(playSpeed);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements WiseAudioPlayerManager {
            public static WiseAudioPlayerManager sDefaultImpl;
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
            public void playList(List<HwAudioPlayItem> list, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().playList(list, i, i2);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
            public void play() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().play();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
            public void playByPosition(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().playByPosition(i);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
            public void playByPositionAndOffsetTime(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().playByPositionAndOffsetTime(i, i2);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
            public void pause() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().pause();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
            public void stop() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stop();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
            public void playPre() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().playPre();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
            public void playNext() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().playNext();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
            public boolean isPlaying() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isPlaying();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
            public boolean isBuffering() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(10, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isBuffering();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
            public void seekTo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(11, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().seekTo(i);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
            public void setPlayMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(12, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPlayMode(i);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
            public void setVolume(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    if (!this.mRemote.transact(13, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVolume(f);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
            public int getPlayMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(14, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPlayMode();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
            public long getOffsetTime() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getOffsetTime();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
            public int getBufferPercent() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(16, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBufferPercent();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
            public long getDuration() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(17, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDuration();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
            public void cancelNotification() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(18, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelNotification();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
            public void setPlaySpeed(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    if (!this.mRemote.transact(19, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPlaySpeed(f);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioPlayerManager
            public float getPlaySpeed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(20, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPlaySpeed();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(WiseAudioPlayerManager wiseAudioPlayerManager) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (wiseAudioPlayerManager == null) {
                return false;
            }
            Proxy.sDefaultImpl = wiseAudioPlayerManager;
            return true;
        }

        public static WiseAudioPlayerManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
