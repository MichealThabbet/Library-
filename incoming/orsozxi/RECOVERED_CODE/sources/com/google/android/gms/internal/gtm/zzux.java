package com.google.android.gms.internal.gtm;

import com.app.orsozoxi.Views.CustomCopticTextView;
import com.ibm.icu.impl.locale.BaseLocale;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.http.HttpHost;

/* loaded from: classes.dex */
public final class zzux {
    public static <T extends zzuw> String zzc(T t) throws SecurityException, IllegalArgumentException {
        if (t == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            zza(null, t, new StringBuffer(), stringBuffer);
            return stringBuffer.toString();
        } catch (IllegalAccessException e) {
            String strValueOf = String.valueOf(e.getMessage());
            return strValueOf.length() != 0 ? "Error printing proto: ".concat(strValueOf) : new String("Error printing proto: ");
        } catch (InvocationTargetException e2) {
            String strValueOf2 = String.valueOf(e2.getMessage());
            return strValueOf2.length() != 0 ? "Error printing proto: ".concat(strValueOf2) : new String("Error printing proto: ");
        }
    }

    private static void zza(String str, Object obj, StringBuffer stringBuffer, StringBuffer stringBuffer2) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (obj != null) {
            if (obj instanceof zzuw) {
                int length = stringBuffer.length();
                if (str != null) {
                    stringBuffer2.append(stringBuffer);
                    stringBuffer2.append(zzde(str));
                    stringBuffer2.append(" <\n");
                    stringBuffer.append(CustomCopticTextView.TWO_CHINESE_BLANK);
                }
                Class<?> cls = obj.getClass();
                for (Field field : cls.getFields()) {
                    int modifiers = field.getModifiers();
                    String name = field.getName();
                    if (!"cachedSize".equals(name) && (modifiers & 1) == 1 && (modifiers & 8) != 8 && !name.startsWith(BaseLocale.SEP) && !name.endsWith(BaseLocale.SEP)) {
                        Class<?> type = field.getType();
                        Object obj2 = field.get(obj);
                        if (type.isArray() && type.getComponentType() != Byte.TYPE) {
                            int length2 = obj2 == null ? 0 : Array.getLength(obj2);
                            for (int i = 0; i < length2; i++) {
                                zza(name, Array.get(obj2, i), stringBuffer, stringBuffer2);
                            }
                        } else {
                            zza(name, obj2, stringBuffer, stringBuffer2);
                        }
                    }
                }
                for (Method method : cls.getMethods()) {
                    String name2 = method.getName();
                    if (name2.startsWith("set")) {
                        String strSubstring = name2.substring(3);
                        try {
                            String strValueOf = String.valueOf(strSubstring);
                            if (((Boolean) cls.getMethod(strValueOf.length() != 0 ? "has".concat(strValueOf) : new String("has"), new Class[0]).invoke(obj, new Object[0])).booleanValue()) {
                                String strValueOf2 = String.valueOf(strSubstring);
                                zza(strSubstring, cls.getMethod(strValueOf2.length() != 0 ? "get".concat(strValueOf2) : new String("get"), new Class[0]).invoke(obj, new Object[0]), stringBuffer, stringBuffer2);
                            }
                        } catch (NoSuchMethodException unused) {
                        }
                    }
                }
                if (str != null) {
                    stringBuffer.setLength(length);
                    stringBuffer2.append(stringBuffer);
                    stringBuffer2.append(">\n");
                    return;
                }
                return;
            }
            String strZzde = zzde(str);
            stringBuffer2.append(stringBuffer);
            stringBuffer2.append(strZzde);
            stringBuffer2.append(": ");
            if (obj instanceof String) {
                String strConcat = (String) obj;
                if (!strConcat.startsWith(HttpHost.DEFAULT_SCHEME_NAME) && strConcat.length() > 200) {
                    strConcat = String.valueOf(strConcat.substring(0, 200)).concat("[...]");
                }
                int length3 = strConcat.length();
                StringBuilder sb = new StringBuilder(length3);
                for (int i2 = 0; i2 < length3; i2++) {
                    char cCharAt = strConcat.charAt(i2);
                    if (cCharAt < ' ' || cCharAt > '~' || cCharAt == '\"' || cCharAt == '\'') {
                        sb.append(String.format("\\u%04x", Integer.valueOf(cCharAt)));
                    } else {
                        sb.append(cCharAt);
                    }
                }
                String string = sb.toString();
                stringBuffer2.append("\"");
                stringBuffer2.append(string);
                stringBuffer2.append("\"");
            } else if (obj instanceof byte[]) {
                byte[] bArr = (byte[]) obj;
                if (bArr == null) {
                    stringBuffer2.append("\"\"");
                } else {
                    stringBuffer2.append('\"');
                    for (byte b : bArr) {
                        int i3 = b & 255;
                        if (i3 == 92 || i3 == 34) {
                            stringBuffer2.append('\\');
                            stringBuffer2.append((char) i3);
                        } else if (i3 < 32 || i3 >= 127) {
                            stringBuffer2.append(String.format("\\%03o", Integer.valueOf(i3)));
                        } else {
                            stringBuffer2.append((char) i3);
                        }
                    }
                    stringBuffer2.append('\"');
                }
            } else {
                stringBuffer2.append(obj);
            }
            stringBuffer2.append("\n");
        }
    }

    private static String zzde(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (i == 0) {
                stringBuffer.append(Character.toLowerCase(cCharAt));
            } else if (Character.isUpperCase(cCharAt)) {
                stringBuffer.append('_');
                stringBuffer.append(Character.toLowerCase(cCharAt));
            } else {
                stringBuffer.append(cCharAt);
            }
        }
        return stringBuffer.toString();
    }
}
