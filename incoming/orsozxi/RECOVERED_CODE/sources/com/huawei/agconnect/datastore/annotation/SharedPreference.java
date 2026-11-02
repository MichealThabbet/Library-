package com.huawei.agconnect.datastore.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes2.dex */
public @interface SharedPreference {
    Class<? extends ICrypto> crypto() default DefaultCrypto.class;

    String fileName();

    boolean isDynamic() default false;

    String key();
}
