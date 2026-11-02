package com.huawei.hms.analytics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.huawei.hms.analytics.efg;

/* loaded from: classes2.dex */
public interface def extends IInterface {

    public static abstract class lmn extends Binder implements def {

        /* renamed from: com.huawei.hms.analytics.def$lmn$lmn, reason: collision with other inner class name */
        static class C0050lmn implements def {
            public static def lmn;
            private IBinder klm;

            C0050lmn(IBinder iBinder) {
                this.klm = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.klm;
            }

            @Override // com.huawei.hms.analytics.def
            public final String lmn() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.huawei.hms.analytics.IHiAnalyticsService");
                    if (!this.klm.transact(2, parcelObtain, parcelObtain2, 0) && lmn.klm() != null) {
                        return lmn.klm().lmn();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.analytics.def
            public final void lmn(efg efgVar, UploadInfo uploadInfo) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.huawei.hms.analytics.IHiAnalyticsService");
                    parcelObtain.writeStrongBinder(efgVar != null ? efgVar.asBinder() : null);
                    if (uploadInfo != null) {
                        parcelObtain.writeInt(1);
                        uploadInfo.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (this.klm.transact(1, parcelObtain, parcelObtain2, 0) || lmn.klm() == null) {
                        parcelObtain2.readException();
                    } else {
                        lmn.klm().lmn(efgVar, uploadInfo);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static def klm() {
            return C0050lmn.lmn;
        }

        public static def lmn(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.huawei.hms.analytics.IHiAnalyticsService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof def)) ? new C0050lmn(iBinder) : (def) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            efg c0051lmn;
            if (i == 1) {
                parcel.enforceInterface("com.huawei.hms.analytics.IHiAnalyticsService");
                IBinder strongBinder = parcel.readStrongBinder();
                if (strongBinder == null) {
                    c0051lmn = null;
                } else {
                    IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.huawei.hms.analytics.IHiAnalyticsCallback");
                    c0051lmn = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof efg)) ? new efg.lmn.C0051lmn(strongBinder) : (efg) iInterfaceQueryLocalInterface;
                }
                lmn(c0051lmn, parcel.readInt() != 0 ? UploadInfo.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                return true;
            }
            if (i != 2) {
                if (i != 1598968902) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                parcel2.writeString("com.huawei.hms.analytics.IHiAnalyticsService");
                return true;
            }
            parcel.enforceInterface("com.huawei.hms.analytics.IHiAnalyticsService");
            String strLmn = lmn();
            parcel2.writeNoException();
            parcel2.writeString(strLmn);
            return true;
        }
    }

    String lmn();

    void lmn(efg efgVar, UploadInfo uploadInfo);
}
