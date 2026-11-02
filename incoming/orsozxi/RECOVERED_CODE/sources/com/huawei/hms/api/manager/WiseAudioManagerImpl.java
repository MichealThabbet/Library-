package com.huawei.hms.api.manager;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.huawei.hms.api.listener.WiseAudioStatusListener;
import com.huawei.hms.api.manager.WiseAudioConfigManager;
import com.huawei.hms.api.manager.WiseAudioPlayerManager;
import com.huawei.hms.api.manager.WiseAudioQueueManager;
import com.huawei.hms.audiokit.manager.WiseAudioEffectManager;

/* loaded from: classes2.dex */
public interface WiseAudioManagerImpl extends IInterface {

    public static class Default implements WiseAudioManagerImpl {
        @Override // com.huawei.hms.api.manager.WiseAudioManagerImpl
        public void addPlayerStatusListener(WiseAudioStatusListener wiseAudioStatusListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.api.manager.WiseAudioManagerImpl
        public WiseAudioConfigManager getConfigManager() throws RemoteException {
            return null;
        }

        @Override // com.huawei.hms.api.manager.WiseAudioManagerImpl
        public WiseAudioEffectManager getEffectManager() throws RemoteException {
            return null;
        }

        @Override // com.huawei.hms.api.manager.WiseAudioManagerImpl
        public WiseAudioPlayerManager getPlayerManager() throws RemoteException {
            return null;
        }

        @Override // com.huawei.hms.api.manager.WiseAudioManagerImpl
        public WiseAudioQueueManager getQueueManager() throws RemoteException {
            return null;
        }

        @Override // com.huawei.hms.api.manager.WiseAudioManagerImpl
        public void removePlayerStatusListener(WiseAudioStatusListener wiseAudioStatusListener) throws RemoteException {
        }
    }

    void addPlayerStatusListener(WiseAudioStatusListener wiseAudioStatusListener) throws RemoteException;

    WiseAudioConfigManager getConfigManager() throws RemoteException;

    WiseAudioEffectManager getEffectManager() throws RemoteException;

    WiseAudioPlayerManager getPlayerManager() throws RemoteException;

    WiseAudioQueueManager getQueueManager() throws RemoteException;

    void removePlayerStatusListener(WiseAudioStatusListener wiseAudioStatusListener) throws RemoteException;

    public static abstract class Stub extends Binder implements WiseAudioManagerImpl {
        private static final String DESCRIPTOR = "com.huawei.hms.api.manager.WiseAudioManagerImpl";
        static final int TRANSACTION_addPlayerStatusListener = 1;
        static final int TRANSACTION_getConfigManager = 3;
        static final int TRANSACTION_getEffectManager = 6;
        static final int TRANSACTION_getPlayerManager = 4;
        static final int TRANSACTION_getQueueManager = 5;
        static final int TRANSACTION_removePlayerStatusListener = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static WiseAudioManagerImpl asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof WiseAudioManagerImpl)) {
                return (WiseAudioManagerImpl) iInterfaceQueryLocalInterface;
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
                    addPlayerStatusListener(WiseAudioStatusListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    removePlayerStatusListener(WiseAudioStatusListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    WiseAudioConfigManager configManager = getConfigManager();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(configManager != null ? configManager.asBinder() : null);
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    WiseAudioPlayerManager playerManager = getPlayerManager();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(playerManager != null ? playerManager.asBinder() : null);
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    WiseAudioQueueManager queueManager = getQueueManager();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(queueManager != null ? queueManager.asBinder() : null);
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    WiseAudioEffectManager effectManager = getEffectManager();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(effectManager != null ? effectManager.asBinder() : null);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements WiseAudioManagerImpl {
            public static WiseAudioManagerImpl sDefaultImpl;
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

            @Override // com.huawei.hms.api.manager.WiseAudioManagerImpl
            public void addPlayerStatusListener(WiseAudioStatusListener wiseAudioStatusListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(wiseAudioStatusListener != null ? wiseAudioStatusListener.asBinder() : null);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addPlayerStatusListener(wiseAudioStatusListener);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioManagerImpl
            public void removePlayerStatusListener(WiseAudioStatusListener wiseAudioStatusListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(wiseAudioStatusListener != null ? wiseAudioStatusListener.asBinder() : null);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removePlayerStatusListener(wiseAudioStatusListener);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioManagerImpl
            public WiseAudioConfigManager getConfigManager() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getConfigManager();
                    }
                    parcelObtain2.readException();
                    return WiseAudioConfigManager.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioManagerImpl
            public WiseAudioPlayerManager getPlayerManager() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPlayerManager();
                    }
                    parcelObtain2.readException();
                    return WiseAudioPlayerManager.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioManagerImpl
            public WiseAudioQueueManager getQueueManager() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getQueueManager();
                    }
                    parcelObtain2.readException();
                    return WiseAudioQueueManager.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioManagerImpl
            public WiseAudioEffectManager getEffectManager() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEffectManager();
                    }
                    parcelObtain2.readException();
                    return WiseAudioEffectManager.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(WiseAudioManagerImpl wiseAudioManagerImpl) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (wiseAudioManagerImpl == null) {
                return false;
            }
            Proxy.sDefaultImpl = wiseAudioManagerImpl;
            return true;
        }

        public static WiseAudioManagerImpl getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
