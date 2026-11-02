package com.huawei.agconnect.https.adapter;

import com.huawei.agconnect.https.annotation.Result;
import com.huawei.agconnect.https.h;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class JSONDecodeUtil {
    private static Object buildObjByType(Class<?> cls) throws JSONException, IllegalAccessException, InstantiationException {
        if (cls.isInterface()) {
            if (cls.equals(List.class)) {
                cls = ArrayList.class;
            } else if (cls.equals(Set.class)) {
                cls = HashSet.class;
            } else {
                if (!cls.equals(Map.class)) {
                    throw new JSONException("the type of " + cls + "cannot be interface");
                }
                cls = HashMap.class;
            }
        }
        return cls.newInstance();
    }

    public static <T> T getObject(String str, Class<T> cls) throws IllegalAccessException, JSONException, InstantiationException, ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        T tNewInstance = cls.newInstance();
        JSONObject jSONObject = new JSONObject(str);
        do {
            Field[] declaredFields = cls.getDeclaredFields();
            if (declaredFields.length > 0) {
                for (final Field field : declaredFields) {
                    String name = field.getName();
                    if (!jSONObject.has(name) && field.isAnnotationPresent(Result.class)) {
                        name = ((Result) field.getAnnotation(Result.class)).value();
                    }
                    if (jSONObject.has(name)) {
                        if (!field.isAccessible()) {
                            AccessController.doPrivileged(new PrivilegedAction() { // from class: com.huawei.agconnect.https.adapter.JSONDecodeUtil.1
                                @Override // java.security.PrivilegedAction
                                public Object run() {
                                    field.setAccessible(true);
                                    return null;
                                }
                            });
                        }
                        Object value = getValue(jSONObject.optString(name), field.getGenericType());
                        if (value != null) {
                            field.set(tNewInstance, value);
                        }
                    }
                }
            }
            cls = cls.getSuperclass();
        } while (cls != Object.class);
        return tNewInstance;
    }

    private static Object getValue(String str, Type type) throws JSONException, IllegalAccessException, InstantiationException, ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        if (h.a(str)) {
            return null;
        }
        Class<?> clsA = h.a(type);
        int i = 0;
        if (isCollectionType(clsA)) {
            Object objBuildObjByType = buildObjByType(clsA);
            Type type2 = ((ParameterizedType) type).getActualTypeArguments()[0];
            JSONArray jSONArray = new JSONArray(str);
            int length = jSONArray.length();
            while (i < length) {
                ((Collection) objBuildObjByType).add(getValue(jSONArray.optString(i), type2));
                i++;
            }
            return objBuildObjByType;
        }
        if (isMapType(clsA)) {
            Object objBuildObjByType2 = buildObjByType(clsA);
            Type type3 = ((ParameterizedType) type).getActualTypeArguments()[1];
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                ((Map) objBuildObjByType2).put(next, getValue(jSONObject.optString(next), type3));
            }
            return objBuildObjByType2;
        }
        if (!clsA.isArray()) {
            return getValue2(str, clsA);
        }
        Class<?> componentType = clsA.getComponentType();
        JSONArray jSONArray2 = new JSONArray(str);
        int length2 = jSONArray2.length();
        Object objNewInstance = Array.newInstance(componentType, length2);
        while (i < length2) {
            Array.set(objNewInstance, i, getValue(jSONArray2.optString(i), componentType));
            i++;
        }
        return objNewInstance;
    }

    private static Object getValue2(String str, Type type) throws IllegalAccessException, JSONException, InstantiationException {
        Object objValueOf;
        Class<?> clsA = h.a(type);
        if (isIntType(clsA)) {
            try {
                return Integer.valueOf(Integer.parseInt(str));
            } catch (NumberFormatException unused) {
                return 0;
            }
        }
        try {
            if (isDoubleType(clsA)) {
                objValueOf = Double.valueOf(Double.parseDouble(str));
            } else {
                if (!isFloat(clsA)) {
                    if (isBooleanType(clsA)) {
                        return Boolean.valueOf(Boolean.parseBoolean(str));
                    }
                    if (!isLongType(clsA)) {
                        return isStringType(clsA) ? isNull(str) ? "" : String.valueOf(str) : getObject(str, clsA);
                    }
                    try {
                        return Long.valueOf(Long.parseLong(str));
                    } catch (NumberFormatException unused2) {
                        return 0L;
                    }
                }
                objValueOf = Float.valueOf(Float.parseFloat(str));
            }
            return objValueOf;
        } catch (NumberFormatException unused3) {
            return Double.valueOf(0.0d);
        }
    }

    private static boolean isBooleanType(Class<?> cls) {
        return cls == Boolean.TYPE || cls == Boolean.class;
    }

    private static boolean isCollectionType(Class<?> cls) {
        return Collection.class.isAssignableFrom(cls);
    }

    private static boolean isDoubleType(Class<?> cls) {
        return cls == Double.TYPE || cls == Double.class;
    }

    private static boolean isFloat(Class<?> cls) {
        return cls == Float.TYPE || cls == Float.class;
    }

    private static boolean isIntType(Class<?> cls) {
        return cls == Integer.TYPE || cls == Integer.class;
    }

    private static boolean isLongType(Class<?> cls) {
        return cls == Long.TYPE || cls == Long.class;
    }

    private static boolean isMapType(Class<?> cls) {
        return Map.class.isAssignableFrom(cls);
    }

    private static boolean isNull(String str) {
        return "null".equals(str) || h.a(str);
    }

    private static boolean isStringType(Class<?> cls) {
        return cls == String.class;
    }
}
