package com.huawei.hms.push;

import com.huawei.hms.aaid.constant.ErrorEnum;

/* loaded from: classes2.dex */
public class BaseException extends Exception {
    public final int a;
    public final ErrorEnum b;

    public BaseException(int i) {
        ErrorEnum errorEnumFromCode = ErrorEnum.fromCode(i);
        this.b = errorEnumFromCode;
        this.a = errorEnumFromCode.getExternalCode();
    }

    public int getErrorCode() {
        return this.a;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.b.getMessage();
    }
}
