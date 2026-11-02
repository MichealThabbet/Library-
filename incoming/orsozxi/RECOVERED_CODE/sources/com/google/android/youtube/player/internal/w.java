package com.google.android.youtube.player.internal;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import com.google.android.youtube.player.internal.d;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public final class w {

    public static final class a extends Exception {
        public a(String str) {
            super(str);
        }

        public a(String str, Throwable th) {
            super(str, th);
        }
    }

    private static IBinder a(Class<?> cls, IBinder iBinder, IBinder iBinder2, IBinder iBinder3, boolean z) throws a {
        try {
            return (IBinder) cls.getConstructor(IBinder.class, IBinder.class, IBinder.class, Boolean.TYPE).newInstance(iBinder, iBinder2, iBinder3, Boolean.valueOf(z));
        } catch (IllegalAccessException e) {
            String strValueOf = String.valueOf(cls.getName());
            throw new a(strValueOf.length() != 0 ? "Unable to call the default constructor of ".concat(strValueOf) : new String("Unable to call the default constructor of "), e);
        } catch (InstantiationException e2) {
            String strValueOf2 = String.valueOf(cls.getName());
            throw new a(strValueOf2.length() != 0 ? "Unable to instantiate the dynamic class ".concat(strValueOf2) : new String("Unable to instantiate the dynamic class "), e2);
        } catch (NoSuchMethodException e3) {
            String strValueOf3 = String.valueOf(cls.getName());
            throw new a(strValueOf3.length() != 0 ? "Could not find the right constructor for ".concat(strValueOf3) : new String("Could not find the right constructor for "), e3);
        } catch (InvocationTargetException e4) {
            String strValueOf4 = String.valueOf(cls.getName());
            throw new a(strValueOf4.length() != 0 ? "Exception thrown by invoked constructor in ".concat(strValueOf4) : new String("Exception thrown by invoked constructor in "), e4);
        }
    }

    private static IBinder a(ClassLoader classLoader, String str, IBinder iBinder, IBinder iBinder2, IBinder iBinder3, boolean z) throws a {
        try {
            return a(classLoader.loadClass(str), iBinder, iBinder2, iBinder3, z);
        } catch (ClassNotFoundException e) {
            String strValueOf = String.valueOf(str);
            throw new a(strValueOf.length() != 0 ? "Unable to find dynamic class ".concat(strValueOf) : new String("Unable to find dynamic class "), e);
        }
    }

    public static d a(Activity activity, IBinder iBinder, boolean z) throws a {
        ab.a(activity);
        ab.a(iBinder);
        Context contextB = z.b(activity);
        if (contextB != null) {
            return d.a.a(a(contextB.getClassLoader(), "com.google.android.youtube.api.jar.client.RemoteEmbeddedPlayer", v.a(contextB).asBinder(), v.a(activity).asBinder(), iBinder, z));
        }
        throw new a("Could not create remote context");
    }
}
