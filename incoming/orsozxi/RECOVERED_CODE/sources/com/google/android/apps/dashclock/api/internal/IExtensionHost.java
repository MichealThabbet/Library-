package com.google.android.apps.dashclock.api.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.apps.dashclock.api.ExtensionData;

/* loaded from: classes.dex */
public interface IExtensionHost extends IInterface {
    void addWatchContentUris(String[] strArr) throws RemoteException;

    void publishUpdate(ExtensionData extensionData) throws RemoteException;

    void removeAllWatchContentUris() throws RemoteException;

    void setUpdateWhenScreenOn(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IExtensionHost {
        private static final String DESCRIPTOR = "com.google.android.apps.dashclock.api.internal.IExtensionHost";
        static final int TRANSACTION_addWatchContentUris = 2;
        static final int TRANSACTION_publishUpdate = 1;
        static final int TRANSACTION_removeAllWatchContentUris = 4;
        static final int TRANSACTION_setUpdateWhenScreenOn = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IExtensionHost asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IExtensionHost)) {
                return (IExtensionHost) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                publishUpdate(parcel.readInt() != 0 ? ExtensionData.CREATOR.createFromParcel(parcel) : null);
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                addWatchContentUris(parcel.createStringArray());
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                setUpdateWhenScreenOn(parcel.readInt() != 0);
                return true;
            }
            if (i == 4) {
                parcel.enforceInterface(DESCRIPTOR);
                removeAllWatchContentUris();
                return true;
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IExtensionHost {
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

            @Override // com.google.android.apps.dashclock.api.internal.IExtensionHost
            public void publishUpdate(ExtensionData extensionData) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (extensionData != null) {
                        parcelObtain.writeInt(1);
                        extensionData.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.apps.dashclock.api.internal.IExtensionHost
            public void addWatchContentUris(String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.apps.dashclock.api.internal.IExtensionHost
            public void setUpdateWhenScreenOn(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.apps.dashclock.api.internal.IExtensionHost
            public void removeAllWatchContentUris() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
