package com.huawei.hms.api.manager;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.huawei.hms.api.bean.HwAudioPlayItem;
import java.util.List;

/* loaded from: classes2.dex */
public interface WiseAudioQueueManager extends IInterface {

    public static class Default implements WiseAudioQueueManager {
        @Override // com.huawei.hms.api.manager.WiseAudioQueueManager
        public void addPlayItem(HwAudioPlayItem hwAudioPlayItem, int i) throws RemoteException {
        }

        @Override // com.huawei.hms.api.manager.WiseAudioQueueManager
        public void addPlayItemList(List<HwAudioPlayItem> list, int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.api.manager.WiseAudioQueueManager
        public List<HwAudioPlayItem> getAllPlaylist() throws RemoteException {
            return null;
        }

        @Override // com.huawei.hms.api.manager.WiseAudioQueueManager
        public int getCurrentIndex() throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.api.manager.WiseAudioQueueManager
        public HwAudioPlayItem getCurrentPlayItem() throws RemoteException {
            return null;
        }

        @Override // com.huawei.hms.api.manager.WiseAudioQueueManager
        public boolean isQueueEmpty() throws RemoteException {
            return false;
        }

        @Override // com.huawei.hms.api.manager.WiseAudioQueueManager
        public void removeListByIndex(int i) throws RemoteException {
        }

        @Override // com.huawei.hms.api.manager.WiseAudioQueueManager
        public void removeListByItem(HwAudioPlayItem hwAudioPlayItem) throws RemoteException {
        }

        @Override // com.huawei.hms.api.manager.WiseAudioQueueManager
        public void setPlaylist(List<HwAudioPlayItem> list) throws RemoteException {
        }
    }

    void addPlayItem(HwAudioPlayItem hwAudioPlayItem, int i) throws RemoteException;

    void addPlayItemList(List<HwAudioPlayItem> list, int i) throws RemoteException;

    List<HwAudioPlayItem> getAllPlaylist() throws RemoteException;

    int getCurrentIndex() throws RemoteException;

    HwAudioPlayItem getCurrentPlayItem() throws RemoteException;

    boolean isQueueEmpty() throws RemoteException;

    void removeListByIndex(int i) throws RemoteException;

    void removeListByItem(HwAudioPlayItem hwAudioPlayItem) throws RemoteException;

    void setPlaylist(List<HwAudioPlayItem> list) throws RemoteException;

    public static abstract class Stub extends Binder implements WiseAudioQueueManager {
        private static final String DESCRIPTOR = "com.huawei.hms.api.manager.WiseAudioQueueManager";
        static final int TRANSACTION_addPlayItem = 7;
        static final int TRANSACTION_addPlayItemList = 8;
        static final int TRANSACTION_getAllPlaylist = 6;
        static final int TRANSACTION_getCurrentIndex = 1;
        static final int TRANSACTION_getCurrentPlayItem = 2;
        static final int TRANSACTION_isQueueEmpty = 9;
        static final int TRANSACTION_removeListByIndex = 3;
        static final int TRANSACTION_removeListByItem = 4;
        static final int TRANSACTION_setPlaylist = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static WiseAudioQueueManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof WiseAudioQueueManager)) {
                return (WiseAudioQueueManager) iInterfaceQueryLocalInterface;
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
                    int currentIndex = getCurrentIndex();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentIndex);
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    HwAudioPlayItem currentPlayItem = getCurrentPlayItem();
                    parcel2.writeNoException();
                    if (currentPlayItem != null) {
                        parcel2.writeInt(1);
                        currentPlayItem.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    removeListByIndex(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    removeListByItem(parcel.readInt() != 0 ? HwAudioPlayItem.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    setPlaylist(parcel.createTypedArrayList(HwAudioPlayItem.CREATOR));
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    List<HwAudioPlayItem> allPlaylist = getAllPlaylist();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allPlaylist);
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    addPlayItem(parcel.readInt() != 0 ? HwAudioPlayItem.CREATOR.createFromParcel(parcel) : null, parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    addPlayItemList(parcel.createTypedArrayList(HwAudioPlayItem.CREATOR), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 9:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zIsQueueEmpty = isQueueEmpty();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsQueueEmpty ? 1 : 0);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements WiseAudioQueueManager {
            public static WiseAudioQueueManager sDefaultImpl;
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

            @Override // com.huawei.hms.api.manager.WiseAudioQueueManager
            public int getCurrentIndex() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentIndex();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioQueueManager
            public HwAudioPlayItem getCurrentPlayItem() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentPlayItem();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? HwAudioPlayItem.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioQueueManager
            public void removeListByIndex(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeListByIndex(i);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioQueueManager
            public void removeListByItem(HwAudioPlayItem hwAudioPlayItem) throws RemoteException {
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
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeListByItem(hwAudioPlayItem);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioQueueManager
            public void setPlaylist(List<HwAudioPlayItem> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPlaylist(list);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioQueueManager
            public List<HwAudioPlayItem> getAllPlaylist() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllPlaylist();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(HwAudioPlayItem.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioQueueManager
            public void addPlayItem(HwAudioPlayItem hwAudioPlayItem, int i) throws RemoteException {
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
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addPlayItem(hwAudioPlayItem, i);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioQueueManager
            public void addPlayItemList(List<HwAudioPlayItem> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addPlayItemList(list, i);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.api.manager.WiseAudioQueueManager
            public boolean isQueueEmpty() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isQueueEmpty();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(WiseAudioQueueManager wiseAudioQueueManager) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (wiseAudioQueueManager == null) {
                return false;
            }
            Proxy.sDefaultImpl = wiseAudioQueueManager;
            return true;
        }

        public static WiseAudioQueueManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
