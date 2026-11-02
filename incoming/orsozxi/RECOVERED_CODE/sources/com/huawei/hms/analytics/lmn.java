package com.huawei.hms.analytics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes2.dex */
public interface lmn extends IInterface {

    /* renamed from: com.huawei.hms.analytics.lmn$lmn, reason: collision with other inner class name */
    public static abstract class AbstractBinderC0053lmn extends Binder implements lmn {

        /* renamed from: com.huawei.hms.analytics.lmn$lmn$lmn, reason: collision with other inner class name */
        static class C0054lmn implements lmn {
            public static lmn lmn;
            private IBinder klm;

            C0054lmn(IBinder iBinder) {
                this.klm = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.klm;
            }

            @Override // com.huawei.hms.analytics.lmn
            public final String lmn() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.huawei.android.hms.ppskit.IPPSChannelInfoService");
                    if (!this.klm.transact(1, parcelObtain, parcelObtain2, 0) && AbstractBinderC0053lmn.klm() != null) {
                        return AbstractBinderC0053lmn.klm().lmn();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static lmn klm() {
            return C0054lmn.lmn;
        }

        public static lmn lmn(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.huawei.android.hms.ppskit.IPPSChannelInfoService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof lmn)) ? new C0054lmn(iBinder) : (lmn) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i != 1) {
                if (i != 1598968902) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                parcel2.writeString("com.huawei.android.hms.ppskit.IPPSChannelInfoService");
                return true;
            }
            parcel.enforceInterface("com.huawei.android.hms.ppskit.IPPSChannelInfoService");
            String strLmn = lmn();
            parcel2.writeNoException();
            parcel2.writeString(strLmn);
            return true;
        }
    }

    String lmn();
}
