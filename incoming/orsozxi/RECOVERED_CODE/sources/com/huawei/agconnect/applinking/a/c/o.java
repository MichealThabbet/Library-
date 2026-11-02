package com.huawei.agconnect.applinking.a.c;

import com.huawei.agconnect.https.annotation.Field;
import com.huawei.agconnect.https.annotation.Result;

/* loaded from: classes2.dex */
public class o {

    @Field("desc")
    @Result("desc")
    private String description;

    @Field("imageUrl")
    @Result("imageUrl")
    private String imageUrl;

    @Field("title")
    @Result("title")
    private String title;

    public String getDescription() {
        return this.description;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setImageUrl(String str) {
        this.imageUrl = str;
    }

    public void setTitle(String str) {
        this.title = str;
    }
}
