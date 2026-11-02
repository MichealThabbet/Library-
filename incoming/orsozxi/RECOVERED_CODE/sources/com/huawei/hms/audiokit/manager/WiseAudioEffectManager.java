package com.huawei.hms.audiokit.manager;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.huawei.hms.audiokit.api.soundeffect.callback.GetEffectCallback;
import com.huawei.hms.audiokit.api.soundeffect.callback.GetSoundEffectListCallback;
import com.huawei.hms.audiokit.api.soundeffect.callback.SetEffectCallback;
import com.huawei.hms.audiokit.soundeffect.bean.HwAudioEffectItem;

/* loaded from: classes2.dex */
public interface WiseAudioEffectManager extends IInterface {

    public static class Default implements WiseAudioEffectManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public HwAudioEffectItem createEffect(String str) throws RemoteException {
            return null;
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public HwAudioEffectItem createEmptyEffect() throws RemoteException {
            return null;
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public int deleteEffect(String str) throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public void getChosenSoundEffect(GetSoundEffectListCallback getSoundEffectListCallback) throws RemoteException {
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public void getEffect(String str, GetEffectCallback getEffectCallback) throws RemoteException {
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public HwAudioEffectItem getNowUsingSoundEffect() throws RemoteException {
            return null;
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public void getOrdinarySoundEffect(GetSoundEffectListCallback getSoundEffectListCallback) throws RemoteException {
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public String getSoundChosenActiveResId() throws RemoteException {
            return null;
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public boolean getSoundChosenLightOn() throws RemoteException {
            return false;
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public String getSoundOrdinaryActiveResId() throws RemoteException {
            return null;
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public boolean getSoundOrdinaryLightOn() throws RemoteException {
            return false;
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public int insertEffect(HwAudioEffectItem hwAudioEffectItem) throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public boolean isEffectOn() throws RemoteException {
            return false;
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public int renameEffect(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public int setEQParams(int[] iArr) throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public void setEffect(HwAudioEffectItem hwAudioEffectItem, SetEffectCallback setEffectCallback) throws RemoteException {
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public void setEffectSwitch(boolean z) throws RemoteException {
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public int setEqSuperChannelBalance(int i) throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public int setEqSuperEnvironmentalSoundEffect(int i) throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public int setEqUltraLowStress(int i) throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public void setSoundChosenActiveResId(String str) throws RemoteException {
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public void setSoundChosenLightOn(boolean z) throws RemoteException {
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public void setSoundOrdinaryActiveResId(String str) throws RemoteException {
        }

        @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
        public void setSoundOrdinaryLightOn(boolean z) throws RemoteException {
        }
    }

    HwAudioEffectItem createEffect(String str) throws RemoteException;

    HwAudioEffectItem createEmptyEffect() throws RemoteException;

    int deleteEffect(String str) throws RemoteException;

    void getChosenSoundEffect(GetSoundEffectListCallback getSoundEffectListCallback) throws RemoteException;

    void getEffect(String str, GetEffectCallback getEffectCallback) throws RemoteException;

    HwAudioEffectItem getNowUsingSoundEffect() throws RemoteException;

    void getOrdinarySoundEffect(GetSoundEffectListCallback getSoundEffectListCallback) throws RemoteException;

    String getSoundChosenActiveResId() throws RemoteException;

    boolean getSoundChosenLightOn() throws RemoteException;

    String getSoundOrdinaryActiveResId() throws RemoteException;

    boolean getSoundOrdinaryLightOn() throws RemoteException;

    int insertEffect(HwAudioEffectItem hwAudioEffectItem) throws RemoteException;

    boolean isEffectOn() throws RemoteException;

    int renameEffect(String str, String str2) throws RemoteException;

    int setEQParams(int[] iArr) throws RemoteException;

    void setEffect(HwAudioEffectItem hwAudioEffectItem, SetEffectCallback setEffectCallback) throws RemoteException;

    void setEffectSwitch(boolean z) throws RemoteException;

    int setEqSuperChannelBalance(int i) throws RemoteException;

    int setEqSuperEnvironmentalSoundEffect(int i) throws RemoteException;

    int setEqUltraLowStress(int i) throws RemoteException;

    void setSoundChosenActiveResId(String str) throws RemoteException;

    void setSoundChosenLightOn(boolean z) throws RemoteException;

    void setSoundOrdinaryActiveResId(String str) throws RemoteException;

    void setSoundOrdinaryLightOn(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements WiseAudioEffectManager {
        private static final String DESCRIPTOR = "com.huawei.hms.audiokit.manager.WiseAudioEffectManager";
        static final int TRANSACTION_createEffect = 7;
        static final int TRANSACTION_createEmptyEffect = 8;
        static final int TRANSACTION_deleteEffect = 11;
        static final int TRANSACTION_getChosenSoundEffect = 5;
        static final int TRANSACTION_getEffect = 24;
        static final int TRANSACTION_getNowUsingSoundEffect = 4;
        static final int TRANSACTION_getOrdinarySoundEffect = 6;
        static final int TRANSACTION_getSoundChosenActiveResId = 17;
        static final int TRANSACTION_getSoundChosenLightOn = 20;
        static final int TRANSACTION_getSoundOrdinaryActiveResId = 19;
        static final int TRANSACTION_getSoundOrdinaryLightOn = 23;
        static final int TRANSACTION_insertEffect = 9;
        static final int TRANSACTION_isEffectOn = 1;
        static final int TRANSACTION_renameEffect = 10;
        static final int TRANSACTION_setEQParams = 12;
        static final int TRANSACTION_setEffect = 3;
        static final int TRANSACTION_setEffectSwitch = 2;
        static final int TRANSACTION_setEqSuperChannelBalance = 13;
        static final int TRANSACTION_setEqSuperEnvironmentalSoundEffect = 14;
        static final int TRANSACTION_setEqUltraLowStress = 15;
        static final int TRANSACTION_setSoundChosenActiveResId = 16;
        static final int TRANSACTION_setSoundChosenLightOn = 21;
        static final int TRANSACTION_setSoundOrdinaryActiveResId = 18;
        static final int TRANSACTION_setSoundOrdinaryLightOn = 22;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static WiseAudioEffectManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof WiseAudioEffectManager)) {
                return (WiseAudioEffectManager) iInterfaceQueryLocalInterface;
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
                    boolean zIsEffectOn = isEffectOn();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsEffectOn ? 1 : 0);
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    setEffectSwitch(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    setEffect(parcel.readInt() != 0 ? HwAudioEffectItem.CREATOR.createFromParcel(parcel) : null, SetEffectCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    HwAudioEffectItem nowUsingSoundEffect = getNowUsingSoundEffect();
                    parcel2.writeNoException();
                    if (nowUsingSoundEffect != null) {
                        parcel2.writeInt(1);
                        nowUsingSoundEffect.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    getChosenSoundEffect(GetSoundEffectListCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    getOrdinarySoundEffect(GetSoundEffectListCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    HwAudioEffectItem hwAudioEffectItemCreateEffect = createEffect(parcel.readString());
                    parcel2.writeNoException();
                    if (hwAudioEffectItemCreateEffect != null) {
                        parcel2.writeInt(1);
                        hwAudioEffectItemCreateEffect.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    HwAudioEffectItem hwAudioEffectItemCreateEmptyEffect = createEmptyEffect();
                    parcel2.writeNoException();
                    if (hwAudioEffectItemCreateEmptyEffect != null) {
                        parcel2.writeInt(1);
                        hwAudioEffectItemCreateEmptyEffect.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 9:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iInsertEffect = insertEffect(parcel.readInt() != 0 ? HwAudioEffectItem.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    parcel2.writeInt(iInsertEffect);
                    return true;
                case 10:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iRenameEffect = renameEffect(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(iRenameEffect);
                    return true;
                case 11:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iDeleteEffect = deleteEffect(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(iDeleteEffect);
                    return true;
                case 12:
                    parcel.enforceInterface(DESCRIPTOR);
                    int eQParams = setEQParams(parcel.createIntArray());
                    parcel2.writeNoException();
                    parcel2.writeInt(eQParams);
                    return true;
                case 13:
                    parcel.enforceInterface(DESCRIPTOR);
                    int eqSuperChannelBalance = setEqSuperChannelBalance(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(eqSuperChannelBalance);
                    return true;
                case 14:
                    parcel.enforceInterface(DESCRIPTOR);
                    int eqSuperEnvironmentalSoundEffect = setEqSuperEnvironmentalSoundEffect(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(eqSuperEnvironmentalSoundEffect);
                    return true;
                case 15:
                    parcel.enforceInterface(DESCRIPTOR);
                    int eqUltraLowStress = setEqUltraLowStress(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(eqUltraLowStress);
                    return true;
                case 16:
                    parcel.enforceInterface(DESCRIPTOR);
                    setSoundChosenActiveResId(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 17:
                    parcel.enforceInterface(DESCRIPTOR);
                    String soundChosenActiveResId = getSoundChosenActiveResId();
                    parcel2.writeNoException();
                    parcel2.writeString(soundChosenActiveResId);
                    return true;
                case 18:
                    parcel.enforceInterface(DESCRIPTOR);
                    setSoundOrdinaryActiveResId(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 19:
                    parcel.enforceInterface(DESCRIPTOR);
                    String soundOrdinaryActiveResId = getSoundOrdinaryActiveResId();
                    parcel2.writeNoException();
                    parcel2.writeString(soundOrdinaryActiveResId);
                    return true;
                case 20:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean soundChosenLightOn = getSoundChosenLightOn();
                    parcel2.writeNoException();
                    parcel2.writeInt(soundChosenLightOn ? 1 : 0);
                    return true;
                case 21:
                    parcel.enforceInterface(DESCRIPTOR);
                    setSoundChosenLightOn(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    parcel.enforceInterface(DESCRIPTOR);
                    setSoundOrdinaryLightOn(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean soundOrdinaryLightOn = getSoundOrdinaryLightOn();
                    parcel2.writeNoException();
                    parcel2.writeInt(soundOrdinaryLightOn ? 1 : 0);
                    return true;
                case 24:
                    parcel.enforceInterface(DESCRIPTOR);
                    getEffect(parcel.readString(), GetEffectCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements WiseAudioEffectManager {
            public static WiseAudioEffectManager sDefaultImpl;
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

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public boolean isEffectOn() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isEffectOn();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public void setEffectSwitch(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setEffectSwitch(z);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public void setEffect(HwAudioEffectItem hwAudioEffectItem, SetEffectCallback setEffectCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (hwAudioEffectItem != null) {
                        parcelObtain.writeInt(1);
                        hwAudioEffectItem.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(setEffectCallback != null ? setEffectCallback.asBinder() : null);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setEffect(hwAudioEffectItem, setEffectCallback);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public HwAudioEffectItem getNowUsingSoundEffect() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNowUsingSoundEffect();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? HwAudioEffectItem.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public void getChosenSoundEffect(GetSoundEffectListCallback getSoundEffectListCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(getSoundEffectListCallback != null ? getSoundEffectListCallback.asBinder() : null);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getChosenSoundEffect(getSoundEffectListCallback);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public void getOrdinarySoundEffect(GetSoundEffectListCallback getSoundEffectListCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(getSoundEffectListCallback != null ? getSoundEffectListCallback.asBinder() : null);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getOrdinarySoundEffect(getSoundEffectListCallback);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public HwAudioEffectItem createEffect(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createEffect(str);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? HwAudioEffectItem.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public HwAudioEffectItem createEmptyEffect() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createEmptyEffect();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? HwAudioEffectItem.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public int insertEffect(HwAudioEffectItem hwAudioEffectItem) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (hwAudioEffectItem != null) {
                        parcelObtain.writeInt(1);
                        hwAudioEffectItem.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().insertEffect(hwAudioEffectItem);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public int renameEffect(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    if (!this.mRemote.transact(10, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().renameEffect(str, str2);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public int deleteEffect(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(11, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().deleteEffect(str);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public int setEQParams(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(12, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setEQParams(iArr);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public int setEqSuperChannelBalance(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(13, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setEqSuperChannelBalance(i);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public int setEqSuperEnvironmentalSoundEffect(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(14, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setEqSuperEnvironmentalSoundEffect(i);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public int setEqUltraLowStress(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(15, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setEqUltraLowStress(i);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public void setSoundChosenActiveResId(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(16, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSoundChosenActiveResId(str);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public String getSoundChosenActiveResId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(17, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSoundChosenActiveResId();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public void setSoundOrdinaryActiveResId(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(18, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSoundOrdinaryActiveResId(str);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public String getSoundOrdinaryActiveResId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(19, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSoundOrdinaryActiveResId();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public boolean getSoundChosenLightOn() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(20, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSoundChosenLightOn();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public void setSoundChosenLightOn(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    if (!this.mRemote.transact(21, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSoundChosenLightOn(z);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public void setSoundOrdinaryLightOn(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    if (!this.mRemote.transact(22, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSoundOrdinaryLightOn(z);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public boolean getSoundOrdinaryLightOn() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(23, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSoundOrdinaryLightOn();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.audiokit.manager.WiseAudioEffectManager
            public void getEffect(String str, GetEffectCallback getEffectCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(getEffectCallback != null ? getEffectCallback.asBinder() : null);
                    if (!this.mRemote.transact(24, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getEffect(str, getEffectCallback);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(WiseAudioEffectManager wiseAudioEffectManager) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (wiseAudioEffectManager == null) {
                return false;
            }
            Proxy.sDefaultImpl = wiseAudioEffectManager;
            return true;
        }

        public static WiseAudioEffectManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
