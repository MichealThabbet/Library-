package com.huawei.hms.api.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.huawei.hms.feature.dynamic.IObjectWrapper;

/* loaded from: classes2.dex */
public interface IServiceBinder extends IInterface {

    public static class Default implements IServiceBinder {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.api.service.IServiceBinder
        public IBinder onBind(IObjectWrapper iObjectWrapper) throws RemoteException {
            return null;
        }

        @Override // com.huawei.hms.api.service.IServiceBinder
        public void onConfigurationChanged(IObjectWrapper iObjectWrapper) throws RemoteException {
        }

        @Override // com.huawei.hms.api.service.IServiceBinder
        public void onCreate(IObjectWrapper iObjectWrapper) throws RemoteException {
        }

        @Override // com.huawei.hms.api.service.IServiceBinder
        public void onDestroy() throws RemoteException {
        }

        @Override // com.huawei.hms.api.service.IServiceBinder
        public void onRebind(IObjectWrapper iObjectWrapper) throws RemoteException {
        }

        @Override // com.huawei.hms.api.service.IServiceBinder
        public int onStartCommand(IObjectWrapper iObjectWrapper, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.api.service.IServiceBinder
        public void onTaskRemoved(IObjectWrapper iObjectWrapper) throws RemoteException {
        }

        @Override // com.huawei.hms.api.service.IServiceBinder
        public void onUnbind() throws RemoteException {
        }
    }

    IBinder onBind(IObjectWrapper iObjectWrapper) throws RemoteException;

    void onConfigurationChanged(IObjectWrapper iObjectWrapper) throws RemoteException;

    void onCreate(IObjectWrapper iObjectWrapper) throws RemoteException;

    void onDestroy() throws RemoteException;

    void onRebind(IObjectWrapper iObjectWrapper) throws RemoteException;

    int onStartCommand(IObjectWrapper iObjectWrapper, int i, int i2) throws RemoteException;

    void onTaskRemoved(IObjectWrapper iObjectWrapper) throws RemoteException;

    void onUnbind() throws RemoteException;

    public static abstract class Stub extends Binder implements IServiceBinder {
        private static final String DESCRIPTOR = "com.huawei.hms.api.service.IServiceBinder";
        static final int TRANSACTION_onBind = 2;
        static final int TRANSACTION_onConfigurationChanged = 7;
        static final int TRANSACTION_onCreate = 1;
        static final int TRANSACTION_onDestroy = 8;
        static final int TRANSACTION_onRebind = 3;
        static final int TRANSACTION_onStartCommand = 5;
        static final int TRANSACTION_onTaskRemoved = 6;
        static final int TRANSACTION_onUnbind = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IServiceBinder asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IServiceBinder)) {
                return (IServiceBinder) iInterfaceQueryLocalInterface;
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
                    onCreate(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    IBinder iBinderOnBind = onBind(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(iBinderOnBind);
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    onRebind(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    onUnbind();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iOnStartCommand = onStartCommand(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(iOnStartCommand);
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    onTaskRemoved(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    onConfigurationChanged(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    onDestroy();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IServiceBinder {
            public static IServiceBinder sDefaultImpl;
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

            @Override // com.huawei.hms.api.service.IServiceBinder
            public void onCreate(IObjectWrapper iObjectWrapper) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iObjectWrapper != null ? iObjectWrapper.asBinder() : null);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCreate(iObjectWrapper);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.service.IServiceBinder
            public IBinder onBind(IObjectWrapper iObjectWrapper) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iObjectWrapper != null ? iObjectWrapper.asBinder() : null);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onBind(iObjectWrapper);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.service.IServiceBinder
            public void onRebind(IObjectWrapper iObjectWrapper) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iObjectWrapper != null ? iObjectWrapper.asBinder() : null);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRebind(iObjectWrapper);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.service.IServiceBinder
            public void onUnbind() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUnbind();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.service.IServiceBinder
            public int onStartCommand(IObjectWrapper iObjectWrapper, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iObjectWrapper != null ? iObjectWrapper.asBinder() : null);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().onStartCommand(iObjectWrapper, i, i2);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.service.IServiceBinder
            public void onTaskRemoved(IObjectWrapper iObjectWrapper) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iObjectWrapper != null ? iObjectWrapper.asBinder() : null);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTaskRemoved(iObjectWrapper);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.service.IServiceBinder
            public void onConfigurationChanged(IObjectWrapper iObjectWrapper) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iObjectWrapper != null ? iObjectWrapper.asBinder() : null);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConfigurationChanged(iObjectWrapper);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.service.IServiceBinder
            public void onDestroy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDestroy();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IServiceBinder iServiceBinder) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iServiceBinder == null) {
                return false;
            }
            Proxy.sDefaultImpl = iServiceBinder;
            return true;
        }

        public static IServiceBinder getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
