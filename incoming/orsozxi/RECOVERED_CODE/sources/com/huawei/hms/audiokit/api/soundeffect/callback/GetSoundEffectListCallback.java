package com.huawei.hms.audiokit.api.soundeffect.callback;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.huawei.hms.audiokit.soundeffect.bean.HwAudioEffectItem;
import java.util.List;

/* loaded from: classes2.dex */
public interface GetSoundEffectListCallback extends IInterface {

    public static class Default implements GetSoundEffectListCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.audiokit.api.soundeffect.callback.GetSoundEffectListCallback
        public void onError(int i, String str) throws RemoteException {
        }

        @Override // com.huawei.hms.audiokit.api.soundeffect.callback.GetSoundEffectListCallback
        public void onSuccess(List<HwAudioEffectItem> list) throws RemoteException {
        }
    }

    void onError(int i, String str) throws RemoteException;

    void onSuccess(List<HwAudioEffectItem> list) throws RemoteException;

    public static abstract class Stub extends Binder implements GetSoundEffectListCallback {
        private static final String DESCRIPTOR = "com.huawei.hms.audiokit.api.soundeffect.callback.GetSoundEffectListCallback";
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static GetSoundEffectListCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof GetSoundEffectListCallback)) {
                return (GetSoundEffectListCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                onSuccess(parcel.createTypedArrayList(HwAudioEffectItem.CREATOR));
                parcel2.writeNoException();
                return true;
            }
            if (i != 2) {
                if (i == 1598968902) {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface(DESCRIPTOR);
            onError(parcel.readInt(), parcel.readString());
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy implements GetSoundEffectListCallback {
            public static GetSoundEffectListCallback sDefaultImpl;
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

            @Override // com.huawei.hms.audiokit.api.soundeffect.callback.GetSoundEffectListCallback
            public void onSuccess(List<HwAudioEffectItem> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSuccess(list);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.api.soundeffect.callback.GetSoundEffectListCallback
            public void onError(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError(i, str);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(GetSoundEffectListCallback getSoundEffectListCallback) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (getSoundEffectListCallback == null) {
                return false;
            }
            Proxy.sDefaultImpl = getSoundEffectListCallback;
            return true;
        }

        public static GetSoundEffectListCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
