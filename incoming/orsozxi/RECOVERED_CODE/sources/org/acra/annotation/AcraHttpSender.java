package org.acra.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.acra.security.KeyStoreFactory;
import org.acra.security.NoKeyStoreFactory;
import org.acra.sender.HttpSender;

@Target({ElementType.TYPE})
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes3.dex */
public @interface AcraHttpSender {
    String basicAuthLogin() default "ACRA-NULL-STRING";

    String basicAuthPassword() default "ACRA-NULL-STRING";

    String certificatePath() default "";

    String certificateType() default "X.509";

    boolean compress() default false;

    int connectionTimeout() default 5000;

    boolean dropReportsOnTimeout() default false;

    HttpSender.Method httpMethod();

    Class<? extends KeyStoreFactory> keyStoreFactoryClass() default NoKeyStoreFactory.class;

    int resCertificate() default 0;

    int socketTimeout() default 20000;

    String uri();
}
