package com.huawei.hms.audiokit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import com.huawei.hms.api.IRemoteCreator;
import com.huawei.hms.api.errorcode.HwAudioErrorCode;
import com.huawei.hms.audiokit.player.manager.UpdateActivity;
import com.huawei.hms.feature.dynamic.DynamicModule;
import com.huawei.hms.support.api.entity.core.CommonCode;
import net.sqlcipher.database.SQLiteDatabase;

/* loaded from: classes2.dex */
public class b {
    private static IRemoteCreator a = null;
    private static Context b = null;
    private static boolean c = false;
    private static String d = "huawei_module_audiokit";
    private static Context f;
    private static int g;
    private static Handler e = new Handler(Looper.getMainLooper());
    private static Runnable h = new a();

    private static void a(Context context, Bundle bundle, com.huawei.hms.audiokit.player.callback.a aVar) {
        int i;
        if (bundle == null || context == null) {
            Log.w("DynamicRemoteImpl", "handleResultBundle ,err data");
            return;
        }
        int iA = d.a(bundle, com.huawei.hms.feature.dynamic.b.g);
        Log.i("DynamicRemoteImpl", "handleResultBundle,errorCode: " + iA);
        if (iA == 2) {
            Intent intent = (Intent) d.b(bundle, "resolution");
            if (intent == null) {
                Log.w("DynamicRemoteImpl", "Null intent,please check it.");
            } else {
                Log.i("DynamicRemoteImpl", "Get intent successfully.");
                Intent intent2 = new Intent(context, (Class<?>) UpdateActivity.class);
                intent2.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                Bundle bundle2 = new Bundle();
                bundle2.putParcelable(CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, intent);
                intent2.putExtras(bundle2);
                c.a(context, intent2);
            }
            if (aVar == null) {
                return;
            } else {
                i = HwAudioErrorCode.ERROR_MODULE_NEED_UPDATE;
            }
        } else {
            if (iA == 4) {
                int i2 = g;
                if (i2 < 3) {
                    g = i2 + 1;
                    e.removeCallbacks(h);
                    e.postDelayed(h, 2000L);
                    return;
                } else {
                    if (aVar != null) {
                        aVar.onError(HwAudioErrorCode.ERROR_MODULE_NOT_READY);
                    }
                    g = 0;
                    return;
                }
            }
            if (iA == 5) {
                Log.e("DynamicRemoteImpl", "The DSF is not found on this device, could not load " + d);
                if (aVar == null) {
                    return;
                } else {
                    i = HwAudioErrorCode.ERROR_MODULE_NOT_PRESET_HSF;
                }
            } else {
                Log.e("DynamicRemoteImpl", "errcode is:" + iA);
                if (aVar == null) {
                    return;
                }
                if (iA == 1) {
                    aVar.onError(HwAudioErrorCode.ERROR_MODULE_NOT_EXIST);
                }
                if (iA != 3) {
                    return;
                } else {
                    i = HwAudioErrorCode.ERROR_MODULE_INTER_ERROR;
                }
            }
        }
        aVar.onError(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0012 A[Catch: InstantiationException -> 0x0018, IllegalAccessException -> 0x001a, ClassNotFoundException -> 0x001c, LoadingException -> 0x0041, TryCatch #2 {LoadingException -> 0x0041, ClassNotFoundException -> 0x001c, IllegalAccessException -> 0x001a, InstantiationException -> 0x0018, blocks: (B:4:0x0005, B:6:0x000b, B:8:0x0014, B:7:0x0012), top: B:29:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean a(android.content.Context r4, com.huawei.hms.audiokit.player.callback.a r5) {
        /*
            java.lang.String r0 = "DynamicRemoteImpl"
            r1 = 0
            if (r4 == 0) goto L12
            android.content.Context r2 = r4.getApplicationContext()     // Catch: java.lang.InstantiationException -> L18 java.lang.IllegalAccessException -> L1a java.lang.ClassNotFoundException -> L1c com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L41
            if (r2 == 0) goto L12
            android.content.Context r2 = r4.getApplicationContext()     // Catch: java.lang.InstantiationException -> L18 java.lang.IllegalAccessException -> L1a java.lang.ClassNotFoundException -> L1c com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L41
            com.huawei.hms.audiokit.b.f = r2     // Catch: java.lang.InstantiationException -> L18 java.lang.IllegalAccessException -> L1a java.lang.ClassNotFoundException -> L1c com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L41
            goto L14
        L12:
            com.huawei.hms.audiokit.b.f = r4     // Catch: java.lang.InstantiationException -> L18 java.lang.IllegalAccessException -> L1a java.lang.ClassNotFoundException -> L1c com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L41
        L14:
            b(r4)     // Catch: java.lang.InstantiationException -> L18 java.lang.IllegalAccessException -> L1a java.lang.ClassNotFoundException -> L1c com.huawei.hms.feature.dynamic.DynamicModule.LoadingException -> L41
            goto L52
        L18:
            r4 = move-exception
            goto L1d
        L1a:
            r4 = move-exception
            goto L1d
        L1c:
            r4 = move-exception
        L1d:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.Class r3 = r4.getClass()
            java.lang.String r3 = r3.getSimpleName()
            r2.append(r3)
            java.lang.String r3 = ": "
            r2.append(r3)
            java.lang.String r4 = r4.getMessage()
            r2.append(r4)
            java.lang.String r4 = r2.toString()
            android.util.Log.w(r0, r4)
            goto L52
        L41:
            r2 = move-exception
            android.os.Bundle r3 = r2.getBundle()
            if (r3 != 0) goto L4e
            java.lang.String r4 = "Load failed, and no available bundle info."
            android.util.Log.e(r0, r4, r2)
            goto L52
        L4e:
            r1 = 1
            a(r4, r3, r5)
        L52:
            if (r5 == 0) goto L64
            boolean r4 = com.huawei.hms.audiokit.b.c
            if (r4 == 0) goto L5c
            r5.onSuccess()
            goto L64
        L5c:
            if (r1 != 0) goto L64
            r4 = 2010004(0x1eab94, float:2.816616E-39)
            r5.onError(r4)
        L64:
            boolean r4 = com.huawei.hms.audiokit.b.c
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.audiokit.b.a(android.content.Context, com.huawei.hms.audiokit.player.callback.a):boolean");
    }

    public static Context b() {
        return b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Context context) throws IllegalAccessException, InstantiationException, ClassNotFoundException, DynamicModule.LoadingException {
        if (b == null) {
            b = DynamicModule.load(context, DynamicModule.PREFER_REMOTE, d).getModuleContext();
        }
        Context context2 = b;
        if (context2 == null) {
            Log.e("DynamicRemoteImpl", "DynamicModule Load failed!");
            return;
        }
        if (c) {
            Log.i("DynamicRemoteImpl", "Already inited!");
            return;
        }
        Object objNewInstance = context2.getClassLoader().loadClass("com.huawei.hms.audiokit.dynamic.Creator").newInstance();
        if (objNewInstance instanceof IBinder) {
            a = IRemoteCreator.Stub.asInterface((IBinder) objNewInstance);
            c = true;
            g = 0;
        }
    }

    public static IRemoteCreator c() {
        return a;
    }
}
