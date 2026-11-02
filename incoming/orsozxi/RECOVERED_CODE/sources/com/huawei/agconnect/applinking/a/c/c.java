package com.huawei.agconnect.applinking.a.c;

import com.huawei.agconnect.https.annotation.Result;

/* loaded from: classes2.dex */
public class c {

    @Result("ret")
    private e ret;

    public e getRet() {
        return this.ret;
    }

    public int getRetCode() {
        e eVar = this.ret;
        if (eVar != null) {
            return eVar.getCode();
        }
        return -1;
    }

    public String getRetMsg() {
        e eVar = this.ret;
        return eVar != null ? eVar.getMsg() : "Connect Ret Null";
    }

    public boolean isSuccess() {
        e eVar = this.ret;
        return eVar != null && eVar.getCode() == 0;
    }

    public void setRet(e eVar) {
        this.ret = eVar;
    }
}
