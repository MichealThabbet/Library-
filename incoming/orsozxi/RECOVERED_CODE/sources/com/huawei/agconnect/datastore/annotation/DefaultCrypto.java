package com.huawei.agconnect.datastore.annotation;

/* loaded from: classes2.dex */
public class DefaultCrypto implements ICrypto {
    @Override // com.huawei.agconnect.datastore.annotation.ICrypto
    public String decrypt(String str) {
        return str;
    }

    @Override // com.huawei.agconnect.datastore.annotation.ICrypto
    public String encrypt(String str) {
        return str;
    }
}
