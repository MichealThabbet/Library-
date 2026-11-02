package org.apache.poi.ddf;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ddf.EscherRecord;

/* loaded from: classes3.dex */
public class DefaultEscherRecordFactory implements EscherRecordFactory {
    private static Class<?>[] escherRecordClasses;
    private static Map<Short, Constructor<? extends EscherRecord>> recordsMap;

    static {
        Class<?>[] clsArr = {EscherBSERecord.class, EscherOptRecord.class, EscherClientAnchorRecord.class, EscherDgRecord.class, EscherSpgrRecord.class, EscherSpRecord.class, EscherClientDataRecord.class, EscherDggRecord.class, EscherSplitMenuColorsRecord.class, EscherChildAnchorRecord.class, EscherTextboxRecord.class};
        escherRecordClasses = clsArr;
        recordsMap = recordsToMap(clsArr);
    }

    @Override // org.apache.poi.ddf.EscherRecordFactory
    public EscherRecord createRecord(byte[] bArr, int i) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
        EscherRecord escherBitmapBlip;
        EscherRecord.EscherRecordHeader header = EscherRecord.EscherRecordHeader.readHeader(bArr, i);
        if ((header.getOptions() & 15) == 15 && header.getRecordId() != -4083) {
            EscherContainerRecord escherContainerRecord = new EscherContainerRecord();
            escherContainerRecord.setRecordId(header.getRecordId());
            escherContainerRecord.setOptions(header.getOptions());
            return escherContainerRecord;
        }
        if (header.getRecordId() >= -4072 && header.getRecordId() <= -3817) {
            if (header.getRecordId() == -4065 || header.getRecordId() == -4067 || header.getRecordId() == -4066) {
                escherBitmapBlip = new EscherBitmapBlip();
            } else if (header.getRecordId() == -4070 || header.getRecordId() == -4069 || header.getRecordId() == -4068) {
                escherBitmapBlip = new EscherMetafileBlip();
            } else {
                escherBitmapBlip = new EscherBlipRecord();
            }
            escherBitmapBlip.setRecordId(header.getRecordId());
            escherBitmapBlip.setOptions(header.getOptions());
            return escherBitmapBlip;
        }
        Constructor<? extends EscherRecord> constructor = recordsMap.get(Short.valueOf(header.getRecordId()));
        if (constructor == null) {
            return new UnknownEscherRecord();
        }
        try {
            EscherRecord escherRecordNewInstance = constructor.newInstance(new Object[0]);
            escherRecordNewInstance.setRecordId(header.getRecordId());
            escherRecordNewInstance.setOptions(header.getOptions());
            return escherRecordNewInstance;
        } catch (Exception unused) {
            return new UnknownEscherRecord();
        }
    }

    private static Map<Short, Constructor<? extends EscherRecord>> recordsToMap(Class<?>[] clsArr) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException {
        HashMap map = new HashMap();
        Class<?>[] clsArr2 = new Class[0];
        for (Class<?> cls : clsArr) {
            try {
                try {
                    map.put(Short.valueOf(cls.getField("RECORD_ID").getShort(null)), cls.getConstructor(clsArr2));
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            } catch (IllegalAccessException e2) {
                throw new RuntimeException(e2);
            } catch (IllegalArgumentException e3) {
                throw new RuntimeException(e3);
            } catch (NoSuchFieldException e4) {
                throw new RuntimeException(e4);
            }
        }
        return map;
    }
}
