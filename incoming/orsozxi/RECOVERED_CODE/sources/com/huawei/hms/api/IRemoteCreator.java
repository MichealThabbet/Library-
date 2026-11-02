package com.huawei.hms.api;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.huawei.hms.api.config.WiseAudioPlayerConfig;
import com.huawei.hms.api.manager.WiseAudioManagerImpl;
import com.huawei.hms.api.manager.WiseAudioPlayerManager;
import com.huawei.hms.feature.dynamic.IObjectWrapper;

/* loaded from: classes2.dex */
public interface IRemoteCreator extends IInterface {

    public static class Default implements IRemoteCreator {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.api.IRemoteCreator
        public void createHwAudioManager(IObjectWrapper iObjectWrapper, WiseAudioPlayerConfig wiseAudioPlayerConfig) throws RemoteException {
        }

        @Override // com.huawei.hms.api.IRemoteCreator
        public WiseAudioManagerImpl getManager() throws RemoteException {
            return null;
        }

        @Override // com.huawei.hms.api.IRemoteCreator
        public WiseAudioPlayerManager getPlayerManager() throws RemoteException {
            return null;
        }
    }

    void createHwAudioManager(IObjectWrapper iObjectWrapper, WiseAudioPlayerConfig wiseAudioPlayerConfig) throws RemoteException;

    WiseAudioManagerImpl getManager() throws RemoteException;

    WiseAudioPlayerManager getPlayerManager() throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteCreator {
        private static final String DESCRIPTOR = "com.huawei.hms.api.IRemoteCreator";
        static final int TRANSACTION_createHwAudioManager = 1;
        static final int TRANSACTION_getManager = 3;
        static final int TRANSACTION_getPlayerManager = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRemoteCreator asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteCreator)) {
                return (IRemoteCreator) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                createHwAudioManager(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? WiseAudioPlayerConfig.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                WiseAudioPlayerManager playerManager = getPlayerManager();
                parcel2.writeNoException();
                parcel2.writeStrongBinder(playerManager != null ? playerManager.asBinder() : null);
                return true;
            }
            if (i != 3) {
                if (i == 1598968902) {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface(DESCRIPTOR);
            WiseAudioManagerImpl manager = getManager();
            parcel2.writeNoException();
            parcel2.writeStrongBinder(manager != null ? manager.asBinder() : null);
            return true;
        }

        private static class Proxy implements IRemoteCreator {
            public static IRemoteCreator sDefaultImpl;
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

            @Override // com.huawei.hms.api.IRemoteCreator
            public void createHwAudioManager(IObjectWrapper iObjectWrapper, WiseAudioPlayerConfig wiseAudioPlayerConfig) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iObjectWrapper != null ? iObjectWrapper.asBinder() : null);
                    if (wiseAudioPlayerConfig != null) {
                        parcelObtain.writeInt(1);
                        wiseAudioPlayerConfig.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().createHwAudioManager(iObjectWrapper, wiseAudioPlayerConfig);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.IRemoteCreator
            public WiseAudioPlayerManager getPlayerManager() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPlayerManager();
                    }
                    parcelObtain2.readException();
                    return WiseAudioPlayerManager.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.IRemoteCreator
            public WiseAudioManagerImpl getManager() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getManager();
                    }
                    parcelObtain2.readException();
                    return WiseAudioManagerImpl.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRemoteCreator iRemoteCreator) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iRemoteCreator == null) {
                return false;
            }
            Proxy.sDefaultImpl = iRemoteCreator;
            return true;
        }

        public static IRemoteCreator getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
