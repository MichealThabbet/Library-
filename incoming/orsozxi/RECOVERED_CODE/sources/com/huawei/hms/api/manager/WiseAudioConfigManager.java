package com.huawei.hms.api.manager;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.huawei.hms.api.config.IWiseAudioNotificationFactory;

/* loaded from: classes2.dex */
public interface WiseAudioConfigManager extends IInterface {

    public static class Default implements WiseAudioConfigManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.api.manager.WiseAudioConfigManager
        public void clearPlayCache() throws RemoteException {
        }

        @Override // com.huawei.hms.api.manager.WiseAudioConfigManager
        public void continueToDownloadIfNeed() throws RemoteException {
        }

        @Override // com.huawei.hms.api.manager.WiseAudioConfigManager
        public long getPlayCacheSize() throws RemoteException {
            return 0L;
        }

        @Override // com.huawei.hms.api.manager.WiseAudioConfigManager
        public long getUsedCacheSize() throws RemoteException {
            return 0L;
        }

        @Override // com.huawei.hms.api.manager.WiseAudioConfigManager
        public void setNotificationFactory(IWiseAudioNotificationFactory iWiseAudioNotificationFactory) throws RemoteException {
        }

        @Override // com.huawei.hms.api.manager.WiseAudioConfigManager
        public void setPlayCacheSize(long j) throws RemoteException {
        }

        @Override // com.huawei.hms.api.manager.WiseAudioConfigManager
        public void setSaveQueue(boolean z) throws RemoteException {
        }

        @Override // com.huawei.hms.api.manager.WiseAudioConfigManager
        public void setSessionState(boolean z) throws RemoteException {
        }

        @Override // com.huawei.hms.api.manager.WiseAudioConfigManager
        public void stopDownload() throws RemoteException {
        }
    }

    void clearPlayCache() throws RemoteException;

    void continueToDownloadIfNeed() throws RemoteException;

    long getPlayCacheSize() throws RemoteException;

    long getUsedCacheSize() throws RemoteException;

    void setNotificationFactory(IWiseAudioNotificationFactory iWiseAudioNotificationFactory) throws RemoteException;

    void setPlayCacheSize(long j) throws RemoteException;

    void setSaveQueue(boolean z) throws RemoteException;

    void setSessionState(boolean z) throws RemoteException;

    void stopDownload() throws RemoteException;

    public static abstract class Stub extends Binder implements WiseAudioConfigManager {
        private static final String DESCRIPTOR = "com.huawei.hms.api.manager.WiseAudioConfigManager";
        static final int TRANSACTION_clearPlayCache = 3;
        static final int TRANSACTION_continueToDownloadIfNeed = 8;
        static final int TRANSACTION_getPlayCacheSize = 2;
        static final int TRANSACTION_getUsedCacheSize = 4;
        static final int TRANSACTION_setNotificationFactory = 5;
        static final int TRANSACTION_setPlayCacheSize = 1;
        static final int TRANSACTION_setSaveQueue = 6;
        static final int TRANSACTION_setSessionState = 9;
        static final int TRANSACTION_stopDownload = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static WiseAudioConfigManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof WiseAudioConfigManager)) {
                return (WiseAudioConfigManager) iInterfaceQueryLocalInterface;
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
                    setPlayCacheSize(parcel.readLong());
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    long playCacheSize = getPlayCacheSize();
                    parcel2.writeNoException();
                    parcel2.writeLong(playCacheSize);
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    clearPlayCache();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    long usedCacheSize = getUsedCacheSize();
                    parcel2.writeNoException();
                    parcel2.writeLong(usedCacheSize);
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    setNotificationFactory(IWiseAudioNotificationFactory.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    setSaveQueue(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    stopDownload();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    continueToDownloadIfNeed();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    parcel.enforceInterface(DESCRIPTOR);
                    setSessionState(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements WiseAudioConfigManager {
            public static WiseAudioConfigManager sDefaultImpl;
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

            @Override // com.huawei.hms.api.manager.WiseAudioConfigManager
            public void setPlayCacheSize(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPlayCacheSize(j);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioConfigManager
            public long getPlayCacheSize() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPlayCacheSize();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioConfigManager
            public void clearPlayCache() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearPlayCache();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioConfigManager
            public long getUsedCacheSize() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUsedCacheSize();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioConfigManager
            public void setNotificationFactory(IWiseAudioNotificationFactory iWiseAudioNotificationFactory) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iWiseAudioNotificationFactory != null ? iWiseAudioNotificationFactory.asBinder() : null);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setNotificationFactory(iWiseAudioNotificationFactory);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioConfigManager
            public void setSaveQueue(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSaveQueue(z);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioConfigManager
            public void stopDownload() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopDownload();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioConfigManager
            public void continueToDownloadIfNeed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().continueToDownloadIfNeed();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioConfigManager
            public void setSessionState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSessionState(z);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(WiseAudioConfigManager wiseAudioConfigManager) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (wiseAudioConfigManager == null) {
                return false;
            }
            Proxy.sDefaultImpl = wiseAudioConfigManager;
            return true;
        }

        public static WiseAudioConfigManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
