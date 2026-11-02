package com.huawei.agconnect.https.adapter;

import com.huawei.agconnect.https.Adapter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import org.json.JSONException;

/* loaded from: classes2.dex */
public class d<HttpsRequest> implements Adapter<HttpsRequest, RequestBody> {
    private String a(final Field field, HttpsRequest httpsrequest) throws IllegalAccessException, JSONException, IllegalArgumentException {
        if (!field.isAccessible()) {
            AccessController.doPrivileged(new PrivilegedAction() { // from class: com.huawei.agconnect.https.adapter.d.1
                @Override // java.security.PrivilegedAction
                public Object run() {
                    field.setAccessible(true);
                    return null;
                }
            });
        }
        Class<?> type = field.getType();
        Object obj = field.get(httpsrequest);
        return d(type) ? obj == null ? "0" : String.valueOf(obj) : c(type) ? obj == null ? "0" : String.valueOf(obj) : b(type) ? obj == null ? "false" : String.valueOf(obj) : a((Class) type) ? obj == null ? "0" : String.valueOf(obj) : type == String.class ? obj == null ? "null" : String.valueOf(obj) : new JSONEncodeUtil().toJson(field.get(httpsrequest));
    }

    private void a(Field field, FormBody.Builder builder, HttpsRequest httpsrequest) {
        try {
            String strValue = ((com.huawei.agconnect.https.annotation.Field) field.getAnnotation(com.huawei.agconnect.https.annotation.Field.class)).value();
            if (strValue == null || strValue.isEmpty()) {
                strValue = field.getName();
            }
            builder.addEncoded(strValue, a(field, httpsrequest));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (JSONException e2) {
            throw new RuntimeException(e2);
        }
    }

    private static boolean a(Class cls) {
        return cls == Long.TYPE || cls == Long.class;
    }

    private static boolean b(Class cls) {
        return cls == Boolean.TYPE || cls == Boolean.class;
    }

    private static boolean c(Class cls) {
        return cls == Double.TYPE || cls == Double.class || cls == Float.TYPE || cls == Float.class;
    }

    private static boolean d(Class cls) {
        return cls == Integer.TYPE || cls == Integer.class;
    }

    public RequestBody a(HttpsRequest httpsrequest) {
        FormBody.Builder builder = new FormBody.Builder();
        Class<?> superclass = httpsrequest.getClass();
        do {
            Field[] declaredFields = superclass.getDeclaredFields();
            if (declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    if (field.isAnnotationPresent(com.huawei.agconnect.https.annotation.Field.class)) {
                        a(field, builder, httpsrequest);
                    }
                }
            }
            superclass = superclass.getSuperclass();
        } while (superclass != Object.class);
        return builder.build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.huawei.agconnect.https.Adapter
    public /* synthetic */ RequestBody adapter(Object obj) throws IOException {
        return a((d<HttpsRequest>) obj);
    }
}
