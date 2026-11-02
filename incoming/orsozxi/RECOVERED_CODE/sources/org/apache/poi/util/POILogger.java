package org.apache.poi.util;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class POILogger {
    public static int DEBUG = 1;
    public static int ERROR = 7;
    public static int FATAL = 9;
    public static int INFO = 3;
    public static int WARN = 5;

    public abstract boolean check(int i);

    public abstract void initialize(String str);

    public abstract void log(int i, Object obj);

    public abstract void log(int i, Object obj, Throwable th);

    POILogger() {
    }

    public void log(int i, Object obj, Object obj2) {
        if (check(i)) {
            StringBuffer stringBuffer = new StringBuffer(32);
            stringBuffer.append(obj);
            stringBuffer.append(obj2);
            log(i, stringBuffer);
        }
    }

    public void log(int i, Object obj, Object obj2, Object obj3) {
        if (check(i)) {
            StringBuffer stringBuffer = new StringBuffer(48);
            stringBuffer.append(obj);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            log(i, stringBuffer);
        }
    }

    public void log(int i, Object obj, Object obj2, Object obj3, Object obj4) {
        if (check(i)) {
            StringBuffer stringBuffer = new StringBuffer(64);
            stringBuffer.append(obj);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            stringBuffer.append(obj4);
            log(i, stringBuffer);
        }
    }

    public void log(int i, Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        if (check(i)) {
            StringBuffer stringBuffer = new StringBuffer(80);
            stringBuffer.append(obj);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            stringBuffer.append(obj4);
            stringBuffer.append(obj5);
            log(i, stringBuffer);
        }
    }

    public void log(int i, Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        if (check(i)) {
            StringBuffer stringBuffer = new StringBuffer(96);
            stringBuffer.append(obj);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            stringBuffer.append(obj4);
            stringBuffer.append(obj5);
            stringBuffer.append(obj6);
            log(i, stringBuffer);
        }
    }

    public void log(int i, Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7) {
        if (check(i)) {
            StringBuffer stringBuffer = new StringBuffer(112);
            stringBuffer.append(obj);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            stringBuffer.append(obj4);
            stringBuffer.append(obj5);
            stringBuffer.append(obj6);
            stringBuffer.append(obj7);
            log(i, stringBuffer);
        }
    }

    public void log(int i, Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8) {
        if (check(i)) {
            StringBuffer stringBuffer = new StringBuffer(128);
            stringBuffer.append(obj);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            stringBuffer.append(obj4);
            stringBuffer.append(obj5);
            stringBuffer.append(obj6);
            stringBuffer.append(obj7);
            stringBuffer.append(obj8);
            log(i, stringBuffer);
        }
    }

    public void log(int i, Throwable th) {
        log(i, (Object) null, th);
    }

    public void log(int i, Object obj, Object obj2, Throwable th) {
        if (check(i)) {
            StringBuffer stringBuffer = new StringBuffer(32);
            stringBuffer.append(obj);
            stringBuffer.append(obj2);
            log(i, (Object) stringBuffer, th);
        }
    }

    public void log(int i, Object obj, Object obj2, Object obj3, Throwable th) {
        if (check(i)) {
            StringBuffer stringBuffer = new StringBuffer(48);
            stringBuffer.append(obj);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            log(i, (Object) stringBuffer, th);
        }
    }

    public void log(int i, Object obj, Object obj2, Object obj3, Object obj4, Throwable th) {
        if (check(i)) {
            StringBuffer stringBuffer = new StringBuffer(64);
            stringBuffer.append(obj);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            stringBuffer.append(obj4);
            log(i, (Object) stringBuffer, th);
        }
    }

    public void log(int i, Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Throwable th) {
        if (check(i)) {
            StringBuffer stringBuffer = new StringBuffer(80);
            stringBuffer.append(obj);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            stringBuffer.append(obj4);
            stringBuffer.append(obj5);
            log(i, (Object) stringBuffer, th);
        }
    }

    public void log(int i, Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Throwable th) {
        if (check(i)) {
            StringBuffer stringBuffer = new StringBuffer(96);
            stringBuffer.append(obj);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            stringBuffer.append(obj4);
            stringBuffer.append(obj5);
            stringBuffer.append(obj6);
            log(i, (Object) stringBuffer, th);
        }
    }

    public void log(int i, Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Throwable th) {
        if (check(i)) {
            StringBuffer stringBuffer = new StringBuffer(112);
            stringBuffer.append(obj);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            stringBuffer.append(obj4);
            stringBuffer.append(obj5);
            stringBuffer.append(obj6);
            stringBuffer.append(obj7);
            log(i, (Object) stringBuffer, th);
        }
    }

    public void log(int i, Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Throwable th) {
        if (check(i)) {
            StringBuffer stringBuffer = new StringBuffer(128);
            stringBuffer.append(obj);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            stringBuffer.append(obj4);
            stringBuffer.append(obj5);
            stringBuffer.append(obj6);
            stringBuffer.append(obj7);
            stringBuffer.append(obj8);
            log(i, (Object) stringBuffer, th);
        }
    }

    public void logFormatted(int i, String str, Object obj) {
        commonLogFormatted(i, str, new Object[]{obj});
    }

    public void logFormatted(int i, String str, Object obj, Object obj2) {
        commonLogFormatted(i, str, new Object[]{obj, obj2});
    }

    public void logFormatted(int i, String str, Object obj, Object obj2, Object obj3) {
        commonLogFormatted(i, str, new Object[]{obj, obj2, obj3});
    }

    public void logFormatted(int i, String str, Object obj, Object obj2, Object obj3, Object obj4) {
        commonLogFormatted(i, str, new Object[]{obj, obj2, obj3, obj4});
    }

    private void commonLogFormatted(int i, String str, Object[] objArr) {
        if (check(i)) {
            Object[] objArrFlattenArrays = flattenArrays(objArr);
            if (objArrFlattenArrays[objArrFlattenArrays.length - 1] instanceof Throwable) {
                log(i, (Object) StringUtil.format(str, objArrFlattenArrays), (Throwable) objArrFlattenArrays[objArrFlattenArrays.length - 1]);
            } else {
                log(i, StringUtil.format(str, objArrFlattenArrays));
            }
        }
    }

    private Object[] flattenArrays(Object[] objArr) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : objArr) {
            arrayList.addAll(objectToObjectArray(obj));
        }
        return arrayList.toArray(new Object[arrayList.size()]);
    }

    private List<Object> objectToObjectArray(Object obj) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        if (obj instanceof byte[]) {
            for (byte b : (byte[]) obj) {
                arrayList.add(Byte.valueOf(b));
            }
        }
        if (obj instanceof char[]) {
            char[] cArr = (char[]) obj;
            while (i < cArr.length) {
                arrayList.add(new Character(cArr[i]));
                i++;
            }
        } else if (obj instanceof short[]) {
            short[] sArr = (short[]) obj;
            while (i < sArr.length) {
                arrayList.add(Short.valueOf(sArr[i]));
                i++;
            }
        } else if (obj instanceof int[]) {
            int[] iArr = (int[]) obj;
            while (i < iArr.length) {
                arrayList.add(Integer.valueOf(iArr[i]));
                i++;
            }
        } else if (obj instanceof long[]) {
            long[] jArr = (long[]) obj;
            while (i < jArr.length) {
                arrayList.add(Long.valueOf(jArr[i]));
                i++;
            }
        } else if (obj instanceof float[]) {
            float[] fArr = (float[]) obj;
            while (i < fArr.length) {
                arrayList.add(new Float(fArr[i]));
                i++;
            }
        } else if (obj instanceof double[]) {
            double[] dArr = (double[]) obj;
            while (i < dArr.length) {
                arrayList.add(new Double(dArr[i]));
                i++;
            }
        } else if (obj instanceof Object[]) {
            Object[] objArr = (Object[]) obj;
            while (i < objArr.length) {
                arrayList.add(objArr[i]);
                i++;
            }
        } else {
            arrayList.add(obj);
        }
        return arrayList;
    }
}
