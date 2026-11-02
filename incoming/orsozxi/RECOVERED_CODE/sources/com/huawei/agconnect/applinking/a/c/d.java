package com.huawei.agconnect.applinking.a.c;

import com.huawei.agconnect.https.annotation.Field;
import com.huawei.agconnect.https.annotation.Result;

/* loaded from: classes2.dex */
public class d {

    @Field("channel")
    @Result("channel")
    private String channel;

    @Field("medium")
    @Result("medium")
    private String medium;

    @Field("name")
    @Result("name")
    private String name;

    public String getChannel() {
        return this.channel;
    }

    public String getMedium() {
        return this.medium;
    }

    public String getName() {
        return this.name;
    }

    public void setChannel(String str) {
        this.channel = str;
    }

    public void setMedium(String str) {
        this.medium = str;
    }

    public void setName(String str) {
        this.name = str;
    }
}
