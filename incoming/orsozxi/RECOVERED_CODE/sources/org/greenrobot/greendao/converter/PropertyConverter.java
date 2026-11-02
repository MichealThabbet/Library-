package org.greenrobot.greendao.converter;

/* loaded from: classes3.dex */
public interface PropertyConverter<P, D> {
    D convertToDatabaseValue(P p);

    P convertToEntityProperty(D d);
}
