package com.huawei.agconnect.datastore.core;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;

/* loaded from: classes2.dex */
class ParcelableSerializer {
    private static final String TAG = ParcelableSerializer.class.getSimpleName();

    ParcelableSerializer() {
    }

    private static <T extends Parcelable> T deserializeFromBytes(byte[] bArr, Parcelable.Creator<T> creator) {
        if (creator == null || bArr.length == 0) {
            return null;
        }
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.unmarshall(bArr, 0, bArr.length);
        parcelObtain.setDataPosition(0);
        T tCreateFromParcel = creator.createFromParcel(parcelObtain);
        parcelObtain.recycle();
        return tCreateFromParcel;
    }

    static <T extends Parcelable> T deserializeFromString(String str, Parcelable.Creator<T> creator) {
        try {
            return (T) deserializeFromBytes(Base64.decode(str, 0), creator);
        } catch (IllegalArgumentException unused) {
            Log.e(TAG, "Base64 decode error");
            return null;
        }
    }

    private static <T extends Parcelable> byte[] serializeToBytes(T t) {
        Parcel parcelObtain = Parcel.obtain();
        t.writeToParcel(parcelObtain, 0);
        byte[] bArrMarshall = parcelObtain.marshall();
        parcelObtain.recycle();
        return bArrMarshall;
    }

    static <T extends Parcelable> String serializeToString(T t) {
        return Base64.encodeToString(serializeToBytes(t), 0);
    }
}
