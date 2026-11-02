package com.huawei.agconnect.common.api;

import android.text.TextUtils;
import com.huawei.agconnect.credential.obs.h;
import com.huawei.agconnect.datastore.annotation.ICrypto;
import com.huawei.secure.android.common.encrypt.aes.AesCbc;
import com.huawei.secure.android.common.util.HexUtil;
import java.io.UnsupportedEncodingException;
import javax.crypto.SecretKey;

/* loaded from: classes2.dex */
public class AgcCrypto implements ICrypto {
    private static final AgcCrypto INSTANCE = new AgcCrypto();

    public static ICrypto get() {
        return INSTANCE;
    }

    @Override // com.huawei.agconnect.datastore.annotation.ICrypto
    public String decrypt(String str) throws UnsupportedEncodingException {
        String strDecrypt = AesCbc.decrypt(str, getKeyV2());
        if (TextUtils.isEmpty(strDecrypt)) {
            strDecrypt = AesCbc.decrypt(str, getKey());
            if (TextUtils.isEmpty(strDecrypt)) {
                return str;
            }
        }
        return strDecrypt;
    }

    @Override // com.huawei.agconnect.datastore.annotation.ICrypto
    public String encrypt(String str) {
        return getKeyV2() == null ? str : AesCbc.encrypt(str, getKeyV2());
    }

    public String getKey() {
        return h.a.a();
    }

    public String getKeyV2() {
        SecretKey secretKeyB = h.a.b();
        if (secretKeyB == null) {
            return null;
        }
        return HexUtil.byteArray2HexStr(secretKeyB.getEncoded());
    }
}
