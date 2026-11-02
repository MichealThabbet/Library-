package com.huawei.agconnect.https;

import com.huawei.agconnect.https.annotation.Header;
import com.huawei.agconnect.https.annotation.HeaderMap;
import com.huawei.agconnect.https.annotation.Query;
import com.huawei.agconnect.https.annotation.Url;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.InvalidParameterException;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Map;
import okhttp3.HttpUrl;
import okhttp3.Request;

/* loaded from: classes2.dex */
abstract class a<Annotation, Builder> {
    private static c a = new c();
    private static b b = new b();
    private static C0046a c = new C0046a();

    /* renamed from: com.huawei.agconnect.https.a$a, reason: collision with other inner class name */
    static class C0046a extends a<Header, Request.Builder> {
        C0046a() {
        }

        @Override // com.huawei.agconnect.https.a
        /* bridge */ /* synthetic */ Request.Builder a(Field field, Object obj, Request.Builder builder) {
            return a2(field, (Field) obj, builder);
        }

        /* renamed from: a, reason: avoid collision after fix types in other method */
        <RequestBean> Request.Builder a2(Field field, RequestBean requestbean, Request.Builder builder) throws IllegalAccessException, IllegalArgumentException {
            if (builder == null) {
                throw new IllegalArgumentException("builder cannot be null");
            }
            try {
                a.b(field);
                Object obj = field.get(requestbean);
                String strValue = ((Header) field.getAnnotation(Header.class)).value();
                if (strValue.isEmpty()) {
                    strValue = field.getName();
                }
                if (!String.class.equals(field.getType())) {
                    throw new InvalidParameterException("Header should be the annotation of String type");
                }
                String str = (String) obj;
                if (!h.a(str)) {
                    builder.addHeader(strValue, str);
                }
                return builder;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class b extends a<HeaderMap, Request.Builder> {
        b() {
        }

        @Override // com.huawei.agconnect.https.a
        /* bridge */ /* synthetic */ Request.Builder a(Field field, Object obj, Request.Builder builder) {
            return a2(field, (Field) obj, builder);
        }

        /* renamed from: a, reason: avoid collision after fix types in other method */
        <RequestBean> Request.Builder a2(Field field, RequestBean requestbean, Request.Builder builder) throws IllegalAccessException, IllegalArgumentException {
            if (builder == null) {
                throw new IllegalArgumentException("builder cannot be null");
            }
            try {
                a.b(field);
                Object obj = field.get(requestbean);
                if (!Map.class.isAssignableFrom(field.getType())) {
                    throw new InvalidParameterException("HeaderMap should be the annotation of Map type");
                }
                Map map = (Map) obj;
                if (map != null && !map.isEmpty()) {
                    for (Map.Entry entry : map.entrySet()) {
                        builder.addHeader((String) entry.getKey(), String.valueOf(entry.getValue()));
                    }
                }
                return builder;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static final class c extends a<Query, HttpUrl.Builder> {
        c() {
        }

        @Override // com.huawei.agconnect.https.a
        /* bridge */ /* synthetic */ HttpUrl.Builder a(Field field, Object obj, HttpUrl.Builder builder) {
            return a2(field, (Field) obj, builder);
        }

        /* renamed from: a, reason: avoid collision after fix types in other method */
        <RequestBean> HttpUrl.Builder a2(Field field, RequestBean requestbean, HttpUrl.Builder builder) throws IllegalAccessException, IllegalArgumentException {
            if (builder == null) {
                throw new IllegalArgumentException("builder cannot be null");
            }
            try {
                a.b(field);
                Object obj = field.get(requestbean);
                if (!String.class.equals(field.getType())) {
                    throw new InvalidParameterException("QueryHandler should be the annotation of String");
                }
                Query query = (Query) field.getAnnotation(Query.class);
                String strValue = query.value();
                boolean zEncoded = query.encoded();
                if (strValue == null || strValue.isEmpty()) {
                    strValue = field.getName();
                }
                if (obj == null) {
                    obj = "";
                }
                if (zEncoded) {
                    builder.addEncodedQueryParameter(strValue, String.valueOf(obj));
                } else {
                    builder.addQueryParameter(strValue, String.valueOf(obj));
                }
                return builder;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static final class d extends a<Url, HttpUrl.Builder> {
        d() {
        }

        @Override // com.huawei.agconnect.https.a
        /* bridge */ /* synthetic */ HttpUrl.Builder a(Field field, Object obj, HttpUrl.Builder builder) {
            return a2(field, (Field) obj, builder);
        }

        /* renamed from: a, reason: avoid collision after fix types in other method */
        <RequestBean> HttpUrl.Builder a2(Field field, RequestBean requestbean, HttpUrl.Builder builder) throws IllegalAccessException, IllegalArgumentException {
            try {
                if (!field.isAnnotationPresent(Url.class)) {
                    throw new IllegalArgumentException("field is not Url annotation");
                }
                a.b(field);
                Object obj = field.get(requestbean);
                if (!String.class.equals(field.getType())) {
                    throw new InvalidParameterException("Url should be the annotation of String");
                }
                String str = (String) obj;
                if (str == null || str.isEmpty()) {
                    throw new IllegalArgumentException("url cannot be null or empty");
                }
                return HttpUrl.parse(str).newBuilder();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    a() {
    }

    static <RequestBean> Request.Builder a(RequestBean requestbean) throws IllegalAccessException, IllegalArgumentException {
        HttpUrl.Builder builderB = b(requestbean);
        Request.Builder builder = new Request.Builder();
        Class<?> superclass = requestbean.getClass();
        do {
            Field[] declaredFields = superclass.getDeclaredFields();
            if (declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    b(field);
                    for (Annotation annotation : field.getDeclaredAnnotations()) {
                        Class<? extends Annotation> clsAnnotationType = annotation.annotationType();
                        if (Query.class.equals(clsAnnotationType)) {
                            a.a2(field, (Field) requestbean, builderB);
                        } else if (HeaderMap.class.equals(clsAnnotationType)) {
                            b.a2(field, (Field) requestbean, builder);
                        } else if (Header.class.equals(clsAnnotationType)) {
                            c.a2(field, (Field) requestbean, builder);
                        }
                    }
                }
            }
            superclass = superclass.getSuperclass();
        } while (superclass != Object.class);
        return builder.url(builderB.build());
    }

    private static <RequestBean> HttpUrl.Builder b(RequestBean requestbean) {
        ArrayList arrayList = new ArrayList(1);
        Class<?> superclass = requestbean.getClass();
        do {
            Field[] declaredFields = superclass.getDeclaredFields();
            if (declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    if (field.isAnnotationPresent(Url.class)) {
                        arrayList.add(field);
                    }
                }
            }
            superclass = superclass.getSuperclass();
        } while (superclass != Object.class);
        if (arrayList.size() == 1) {
            return new d().a2((Field) arrayList.get(0), (Field) requestbean, (HttpUrl.Builder) null);
        }
        throw new IllegalArgumentException("ONLY ONE String Field can be annotated as Url");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(final Field field) {
        if (field.isAccessible()) {
            return;
        }
        AccessController.doPrivileged(new PrivilegedAction() { // from class: com.huawei.agconnect.https.a.1
            @Override // java.security.PrivilegedAction
            public Object run() {
                field.setAccessible(true);
                return null;
            }
        });
    }

    abstract <RequestBean> Builder a(Field field, RequestBean requestbean, Builder builder);
}
