package org.acra.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.acra.ACRA;

/* loaded from: classes3.dex */
public final class InstanceCreator {

    @FunctionalInterface
    public interface Fallback<T> {
        T get();
    }

    public <T> T create(Class<? extends T> cls, Fallback<T> fallback) {
        T t = (T) create(cls);
        return t != null ? t : fallback.get();
    }

    <T> T create(Class<? extends T> cls) {
        try {
            return cls.newInstance();
        } catch (IllegalAccessException e) {
            ACRA.log.e(ACRA.LOG_TAG, "Failed to create instance of class " + cls.getName(), e);
            return null;
        } catch (InstantiationException e2) {
            ACRA.log.e(ACRA.LOG_TAG, "Failed to create instance of class " + cls.getName(), e2);
            return null;
        }
    }

    public <T> List<T> create(Collection<Class<? extends T>> collection) {
        ArrayList arrayList = new ArrayList();
        Iterator<Class<? extends T>> it2 = collection.iterator();
        while (it2.hasNext()) {
            Object objCreate = create(it2.next());
            if (objCreate != null) {
                arrayList.add(objCreate);
            }
        }
        return arrayList;
    }
}
