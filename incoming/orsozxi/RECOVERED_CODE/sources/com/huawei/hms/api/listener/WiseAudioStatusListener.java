package com.huawei.hms.api.listener;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.huawei.hms.api.bean.HwAudioPlayItem;
import java.util.List;

/* loaded from: classes2.dex */
public interface WiseAudioStatusListener extends IInterface {

    public static class Default implements WiseAudioStatusListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.api.listener.WiseAudioStatusListener
        public void onBufferProgress(int i) throws RemoteException {
        }

        @Override // com.huawei.hms.api.listener.WiseAudioStatusListener
        public void onPlayCompleted(boolean z) throws RemoteException {
        }

        @Override // com.huawei.hms.api.listener.WiseAudioStatusListener
        public void onPlayError(int i, boolean z) throws RemoteException {
        }

        @Override // com.huawei.hms.api.listener.WiseAudioStatusListener
        public void onPlayProgress(long j, long j2) throws RemoteException {
        }

        @Override // com.huawei.hms.api.listener.WiseAudioStatusListener
        public void onPlayStateChange(boolean z, boolean z2) throws RemoteException {
        }

        @Override // com.huawei.hms.api.listener.WiseAudioStatusListener
        public void onQueueChanged(List<HwAudioPlayItem> list) throws RemoteException {
        }

        @Override // com.huawei.hms.api.listener.WiseAudioStatusListener
        public void onSongChange(HwAudioPlayItem hwAudioPlayItem) throws RemoteException {
        }
    }

    void onBufferProgress(int i) throws RemoteException;

    void onPlayCompleted(boolean z) throws RemoteException;

    void onPlayError(int i, boolean z) throws RemoteException;

    void onPlayProgress(long j, long j2) throws RemoteException;

    void onPlayStateChange(boolean z, boolean z2) throws RemoteException;

    void onQueueChanged(List<HwAudioPlayItem> list) throws RemoteException;

    void onSongChange(HwAudioPlayItem hwAudioPlayItem) throws RemoteException;

    public static abstract class Stub extends Binder implements WiseAudioStatusListener {
        private static final String DESCRIPTOR = "com.huawei.hms.api.listener.WiseAudioStatusListener";
        static final int TRANSACTION_onBufferProgress = 3;
        static final int TRANSACTION_onPlayCompleted = 5;
        static final int TRANSACTION_onPlayError = 6;
        static final int TRANSACTION_onPlayProgress = 4;
        static final int TRANSACTION_onPlayStateChange = 7;
        static final int TRANSACTION_onQueueChanged = 2;
        static final int TRANSACTION_onSongChange = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static WiseAudioStatusListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof WiseAudioStatusListener)) {
                return (WiseAudioStatusListener) iInterfaceQueryLocalInterface;
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
                    onSongChange(parcel.readInt() != 0 ? HwAudioPlayItem.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    onQueueChanged(parcel.createTypedArrayList(HwAudioPlayItem.CREATOR));
                    parcel2.writeNoException();
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    onBufferProgress(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    onPlayProgress(parcel.readLong(), parcel.readLong());
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    onPlayCompleted(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    onPlayError(parcel.readInt(), parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    onPlayStateChange(parcel.readInt() != 0, parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements WiseAudioStatusListener {
            public static WiseAudioStatusListener sDefaultImpl;
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

            @Override // com.huawei.hms.api.listener.WiseAudioStatusListener
            public void onSongChange(HwAudioPlayItem hwAudioPlayItem) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (hwAudioPlayItem != null) {
                        parcelObtain.writeInt(1);
                        hwAudioPlayItem.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSongChange(hwAudioPlayItem);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.listener.WiseAudioStatusListener
            public void onQueueChanged(List<HwAudioPlayItem> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onQueueChanged(list);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.listener.WiseAudioStatusListener
            public void onBufferProgress(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onBufferProgress(i);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.listener.WiseAudioStatusListener
            public void onPlayProgress(long j, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPlayProgress(j, j2);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.listener.WiseAudioStatusListener
            public void onPlayCompleted(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPlayCompleted(z);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.listener.WiseAudioStatusListener
            public void onPlayError(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(z ? 1 : 0);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPlayError(i, z);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.listener.WiseAudioStatusListener
            public void onPlayStateChange(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    int i = 1;
                    parcelObtain.writeInt(z ? 1 : 0);
                    if (!z2) {
                        i = 0;
                    }
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPlayStateChange(z, z2);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(WiseAudioStatusListener wiseAudioStatusListener) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (wiseAudioStatusListener == null) {
                return false;
            }
            Proxy.sDefaultImpl = wiseAudioStatusListener;
            return true;
        }

        public static WiseAudioStatusListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
