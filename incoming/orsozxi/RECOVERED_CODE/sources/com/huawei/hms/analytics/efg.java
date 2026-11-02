package com.huawei.hms.analytics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import java.util.List;

/* loaded from: classes2.dex */
public interface efg extends IInterface {

    public static abstract class lmn extends Binder implements efg {

        /* renamed from: com.huawei.hms.analytics.efg$lmn$lmn, reason: collision with other inner class name */
        static class C0051lmn implements efg {
            public static efg lmn;
            private IBinder klm;

            C0051lmn(IBinder iBinder) {
                this.klm = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.klm;
            }

            @Override // com.huawei.hms.analytics.efg
            public final int lmn(List<CustomEvent> list) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.huawei.hms.analytics.IHiAnalyticsCallback");
                    parcelObtain.writeTypedList(list);
                    if (!this.klm.transact(1, parcelObtain, parcelObtain2, 0) && lmn.lmn() != null) {
                        return lmn.lmn().lmn(list);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public lmn() {
            attachInterface(this, "com.huawei.hms.analytics.IHiAnalyticsCallback");
        }

        public static efg lmn() {
            return C0051lmn.lmn;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i != 1) {
                if (i != 1598968902) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                parcel2.writeString("com.huawei.hms.analytics.IHiAnalyticsCallback");
                return true;
            }
            parcel.enforceInterface("com.huawei.hms.analytics.IHiAnalyticsCallback");
            int iLmn = lmn(parcel.createTypedArrayList(CustomEvent.CREATOR));
            parcel2.writeNoException();
            parcel2.writeInt(iLmn);
            return true;
        }
    }

    int lmn(List<CustomEvent> list);
}
