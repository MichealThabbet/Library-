package com.huawei.agconnect.https.adapter;

import com.huawei.agconnect.https.Adapter;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONException;

/* loaded from: classes2.dex */
public class a<Request> implements Adapter<Request, RequestBody> {
    private static final MediaType a = MediaType.parse("application/json; charset=UTF-8");

    private String a(final Field field, Request request) throws IllegalAccessException, JSONException, IllegalArgumentException {
        if (!field.isAccessible()) {
            AccessController.doPrivileged(new PrivilegedAction() { // from class: com.huawei.agconnect.https.adapter.a.1
                @Override // java.security.PrivilegedAction
                public Object run() {
                    field.setAccessible(true);
                    return null;
                }
            });
        }
        Object obj = field.get(request);
        if (obj == null) {
            return null;
        }
        return new JSONEncodeUtil(false).toJson(obj);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0028 A[Catch: JSONException -> 0x003c, IllegalAccessException -> 0x0058, PHI: r2 r3
      0x0028: PHI (r2v4 java.lang.String) = (r2v3 java.lang.String), (r2v8 java.lang.String) binds: [B:4:0x000c, B:11:0x0025] A[DONT_GENERATE, DONT_INLINE]
      0x0028: PHI (r3v2 boolean) = (r3v1 boolean), (r3v3 boolean) binds: [B:4:0x000c, B:11:0x0025] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #2 {IllegalAccessException -> 0x0058, JSONException -> 0x003c, blocks: (B:2:0x0000, B:3:0x0007, B:5:0x000e, B:7:0x0012, B:9:0x001c, B:10:0x0022, B:13:0x0028), top: B:26:0x0000 }] */
    @Override // com.huawei.agconnect.https.Adapter
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public okhttp3.RequestBody adapter(Request r10) throws java.io.IOException, java.lang.IllegalArgumentException {
        /*
            r9 = this;
            java.lang.Class r0 = r10.getClass()     // Catch: org.json.JSONException -> L3c java.lang.IllegalAccessException -> L58
            r1 = 0
            r2 = 0
            r3 = 0
        L7:
            java.lang.reflect.Field[] r4 = r0.getDeclaredFields()     // Catch: org.json.JSONException -> L3c java.lang.IllegalAccessException -> L58
            int r5 = r4.length     // Catch: org.json.JSONException -> L3c java.lang.IllegalAccessException -> L58
            if (r5 <= 0) goto L28
            int r5 = r4.length     // Catch: org.json.JSONException -> L3c java.lang.IllegalAccessException -> L58
            r6 = 0
        L10:
            if (r6 >= r5) goto L25
            r7 = r4[r6]     // Catch: org.json.JSONException -> L3c java.lang.IllegalAccessException -> L58
            java.lang.Class<com.huawei.agconnect.https.annotation.Body> r8 = com.huawei.agconnect.https.annotation.Body.class
            boolean r8 = r7.isAnnotationPresent(r8)     // Catch: org.json.JSONException -> L3c java.lang.IllegalAccessException -> L58
            if (r8 == 0) goto L22
            java.lang.String r2 = r9.a(r7, r10)     // Catch: org.json.JSONException -> L3c java.lang.IllegalAccessException -> L58
            r3 = 1
            goto L25
        L22:
            int r6 = r6 + 1
            goto L10
        L25:
            if (r3 == 0) goto L28
            goto L30
        L28:
            java.lang.Class r0 = r0.getSuperclass()     // Catch: org.json.JSONException -> L3c java.lang.IllegalAccessException -> L58
            java.lang.Class<java.lang.Object> r4 = java.lang.Object.class
            if (r0 != r4) goto L7
        L30:
            okhttp3.MediaType r10 = com.huawei.agconnect.https.adapter.a.a
            if (r2 != 0) goto L37
            java.lang.String r2 = "{}"
        L37:
            okhttp3.RequestBody r10 = okhttp3.RequestBody.create(r10, r2)
            return r10
        L3c:
            r10 = move-exception
            java.io.IOException r0 = new java.io.IOException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "catch JSONException:"
            r1.append(r2)
            java.lang.String r10 = r10.getMessage()
            r1.append(r10)
            java.lang.String r10 = r1.toString()
            r0.<init>(r10)
            throw r0
        L58:
            r10 = move-exception
            java.io.IOException r0 = new java.io.IOException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "catch IllegalAccessException:"
            r1.append(r2)
            java.lang.String r10 = r10.getMessage()
            r1.append(r10)
            java.lang.String r10 = r1.toString()
            r0.<init>(r10)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.agconnect.https.adapter.a.adapter(java.lang.Object):okhttp3.RequestBody");
    }
}
