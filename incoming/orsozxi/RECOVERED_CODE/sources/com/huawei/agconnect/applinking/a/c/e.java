package com.huawei.agconnect.applinking.a.c;

import com.huawei.agconnect.https.annotation.Result;

/* loaded from: classes2.dex */
public class e {

    @Result("code")
    private int code;

    @Result("msg")
    private String msg;

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public void setMsg(String str) {
        this.msg = str;
    }
}
