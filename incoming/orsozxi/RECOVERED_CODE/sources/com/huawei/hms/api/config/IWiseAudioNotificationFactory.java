package com.huawei.hms.api.config;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.huawei.hms.feature.dynamic.IObjectWrapper;

/* loaded from: classes2.dex */
public interface IWiseAudioNotificationFactory extends IInterface {

    public static class Default implements IWiseAudioNotificationFactory {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.api.config.IWiseAudioNotificationFactory
        public IObjectWrapper createNotification(NotificationConfig notificationConfig) throws RemoteException {
            return null;
        }
    }

    IObjectWrapper createNotification(NotificationConfig notificationConfig) throws RemoteException;

    public static abstract class Stub extends Binder implements IWiseAudioNotificationFactory {
        private static final String DESCRIPTOR = "com.huawei.hms.api.config.IWiseAudioNotificationFactory";
        static final int TRANSACTION_createNotification = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWiseAudioNotificationFactory asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IWiseAudioNotificationFactory)) {
                return (IWiseAudioNotificationFactory) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1) {
                if (i == 1598968902) {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface(DESCRIPTOR);
            IObjectWrapper iObjectWrapperCreateNotification = createNotification(parcel.readInt() != 0 ? NotificationConfig.CREATOR.createFromParcel(parcel) : null);
            parcel2.writeNoException();
            parcel2.writeStrongBinder(iObjectWrapperCreateNotification != null ? iObjectWrapperCreateNotification.asBinder() : null);
            return true;
        }

        private static class Proxy implements IWiseAudioNotificationFactory {
            public static IWiseAudioNotificationFactory sDefaultImpl;
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

            @Override // com.huawei.hms.api.config.IWiseAudioNotificationFactory
            public IObjectWrapper createNotification(NotificationConfig notificationConfig) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (notificationConfig != null) {
                        parcelObtain.writeInt(1);
                        notificationConfig.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createNotification(notificationConfig);
                    }
                    parcelObtain2.readException();
                    return IObjectWrapper.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IWiseAudioNotificationFactory iWiseAudioNotificationFactory) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iWiseAudioNotificationFactory == null) {
                return false;
            }
            Proxy.sDefaultImpl = iWiseAudioNotificationFactory;
            return true;
        }

        public static IWiseAudioNotificationFactory getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
