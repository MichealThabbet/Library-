package com.huawei.agconnect.datastore.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.util.Log;
import com.huawei.agconnect.datastore.annotation.ICrypto;
import java.util.Map;

/* loaded from: classes2.dex */
public class AndroidSharedPrefUtil {
    private static AndroidSharedPrefUtil INSTANCE = new AndroidSharedPrefUtil();
    private static final String TAG = "AndroidSharedPrefUtil";
    private static Context context;

    private AndroidSharedPrefUtil() {
    }

    public static synchronized Object get(String str, String str2, Class<?> cls, Object obj, Class<?> cls2) {
        ICrypto helper = CryptoUtil.getHelper(cls2);
        SharedPreferences sharedPreferences = context.getSharedPreferences(str, 0);
        if (sharedPreferences == null) {
            Log.e(TAG, "sp is null");
            return obj;
        }
        try {
            return Integer.class.equals(cls) ? Integer.valueOf(getInt(sharedPreferences, str2, helper, obj)) : Long.class.equals(cls) ? Long.valueOf(getLong(sharedPreferences, str2, helper, obj)) : Float.class.equals(cls) ? Float.valueOf(getFloat(sharedPreferences, str2, helper, obj)) : Boolean.class.equals(cls) ? Boolean.valueOf(getBoolean(sharedPreferences, str2, helper, obj)) : String.class.equals(cls) ? getString(sharedPreferences, str2, helper, obj) : getObject(sharedPreferences, str2, helper, obj, cls);
        } catch (ClassCastException e) {
            Log.e(TAG, "save type error for key :" + str2, e);
            return obj;
        }
    }

    public static Map<String, ?> getAll(String str) {
        return context.getSharedPreferences(str, 0).getAll();
    }

    private static boolean getBoolean(SharedPreferences sharedPreferences, String str, ICrypto iCrypto, Object obj) {
        if (obj == null) {
            obj = Boolean.FALSE;
        }
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        if (iCrypto == null) {
            return sharedPreferences.getBoolean(str, zBooleanValue);
        }
        try {
            return Boolean.parseBoolean(iCrypto.decrypt(sharedPreferences.getString(str, String.valueOf(zBooleanValue))));
        } catch (NumberFormatException unused) {
            return zBooleanValue;
        }
    }

    private static float getFloat(SharedPreferences sharedPreferences, String str, ICrypto iCrypto, Object obj) {
        if (obj == null) {
            obj = Float.valueOf(Float.NaN);
        }
        float fFloatValue = ((Float) obj).floatValue();
        if (iCrypto == null) {
            return sharedPreferences.getFloat(str, fFloatValue);
        }
        try {
            return Float.parseFloat(iCrypto.decrypt(sharedPreferences.getString(str, String.valueOf(fFloatValue))));
        } catch (NumberFormatException unused) {
            return fFloatValue;
        }
    }

    private static int getInt(SharedPreferences sharedPreferences, String str, ICrypto iCrypto, Object obj) {
        if (obj == null) {
            obj = 0;
        }
        int iIntValue = ((Integer) obj).intValue();
        if (iCrypto == null) {
            return sharedPreferences.getInt(str, iIntValue);
        }
        try {
            return Integer.parseInt(iCrypto.decrypt(sharedPreferences.getString(str, String.valueOf(iIntValue))));
        } catch (NumberFormatException unused) {
            return iIntValue;
        }
    }

    private static long getLong(SharedPreferences sharedPreferences, String str, ICrypto iCrypto, Object obj) {
        if (obj == null) {
            obj = 0L;
        }
        long jLongValue = ((Long) obj).longValue();
        if (iCrypto == null) {
            return sharedPreferences.getLong(str, jLongValue);
        }
        try {
            return Long.parseLong(iCrypto.decrypt(sharedPreferences.getString(str, String.valueOf(jLongValue))));
        } catch (NumberFormatException unused) {
            return jLongValue;
        }
    }

    private static Object getObject(SharedPreferences sharedPreferences, String str, ICrypto iCrypto, Object obj, Class<?> cls) {
        StringBuilder sb;
        String str2;
        if (!Parcelable.class.isAssignableFrom(cls)) {
            Log.e(TAG, "Only Support Parcelable Object");
            return obj;
        }
        try {
            return ParcelableSerializer.deserializeFromString(getString(sharedPreferences, str, iCrypto, null), (Parcelable.Creator) cls.getField("CREATOR").get(null));
        } catch (IllegalAccessException unused) {
            sb = new StringBuilder();
            str2 = "IllegalAccessException:";
            sb.append(str2);
            sb.append(cls);
            Log.e(TAG, sb.toString());
            return obj;
        } catch (NoSuchFieldException unused2) {
            sb = new StringBuilder();
            str2 = "NoSuchFieldException:";
            sb.append(str2);
            sb.append(cls);
            Log.e(TAG, sb.toString());
            return obj;
        }
    }

    private static String getString(SharedPreferences sharedPreferences, String str, ICrypto iCrypto, Object obj) {
        if (obj == null) {
            obj = "";
        }
        String string = sharedPreferences.getString(str, (String) obj);
        return iCrypto != null ? iCrypto.decrypt(string) : string;
    }

    public static synchronized void init(Object obj) {
        if (obj instanceof Context) {
            Context applicationContext = ((Context) obj).getApplicationContext();
            if (applicationContext == null) {
                applicationContext = (Context) obj;
            }
            context = applicationContext;
        }
    }

    public static synchronized void put(String str, String str2, Class<?> cls, Object obj, Class<?> cls2) {
        ICrypto helper = CryptoUtil.getHelper(cls2);
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(str, 0).edit();
        if (obj != null && editorEdit != null) {
            if (Integer.class.equals(cls)) {
                putInt(editorEdit, helper, str2, ((Integer) obj).intValue());
            } else if (Long.class.equals(cls)) {
                putLong(editorEdit, helper, str2, ((Long) obj).longValue());
            } else if (Float.class.equals(cls)) {
                putFloat(editorEdit, helper, str2, ((Float) obj).floatValue());
            } else if (Boolean.class.equals(cls)) {
                putBoolean(editorEdit, helper, str2, ((Boolean) obj).booleanValue());
            } else if (String.class.equals(cls)) {
                putString(editorEdit, helper, str2, (String) obj);
            } else {
                putObject(editorEdit, helper, str2, obj);
            }
            editorEdit.commit();
            return;
        }
        Log.e(TAG, "value or editor is null");
    }

    private static void putBoolean(SharedPreferences.Editor editor, ICrypto iCrypto, String str, boolean z) {
        if (iCrypto != null) {
            editor.putString(str, iCrypto.encrypt(String.valueOf(z)));
        } else {
            editor.putBoolean(str, z);
        }
    }

    private static void putFloat(SharedPreferences.Editor editor, ICrypto iCrypto, String str, float f) {
        if (iCrypto != null) {
            editor.putString(str, iCrypto.encrypt(String.valueOf(f)));
        } else {
            editor.putFloat(str, f);
        }
    }

    private static void putInt(SharedPreferences.Editor editor, ICrypto iCrypto, String str, int i) {
        if (iCrypto != null) {
            editor.putString(str, iCrypto.encrypt(String.valueOf(i)));
        } else {
            editor.putInt(str, i);
        }
    }

    private static void putLong(SharedPreferences.Editor editor, ICrypto iCrypto, String str, long j) {
        if (iCrypto != null) {
            editor.putString(str, iCrypto.encrypt(String.valueOf(j)));
        } else {
            editor.putLong(str, j);
        }
    }

    private static void putObject(SharedPreferences.Editor editor, ICrypto iCrypto, String str, Object obj) {
        if (!Parcelable.class.isAssignableFrom(obj.getClass())) {
            Log.e(TAG, "Only Support Parcelable Object");
            return;
        }
        String strSerializeToString = ParcelableSerializer.serializeToString((Parcelable) obj);
        if (iCrypto != null) {
            editor.putString(str, iCrypto.encrypt(strSerializeToString));
        } else {
            editor.putString(str, strSerializeToString);
        }
    }

    private static void putString(SharedPreferences.Editor editor, ICrypto iCrypto, String str, String str2) {
        if (iCrypto != null) {
            editor.putString(str, iCrypto.encrypt(str2));
        } else {
            editor.putString(str, str2);
        }
    }

    public static void remove(String str, String str2) {
        context.getSharedPreferences(str, 0).edit().remove(str2).commit();
    }
}
