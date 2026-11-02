package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.ICUBinary;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.MissingResourceException;

/* loaded from: classes2.dex */
public final class CollationRoot {
    private static final RuntimeException exception;
    private static final CollationTailoring rootSingleton;

    public static final CollationTailoring getRoot() {
        RuntimeException runtimeException = exception;
        if (runtimeException != null) {
            throw runtimeException;
        }
        return rootSingleton;
    }

    public static final CollationData getData() {
        return getRoot().data;
    }

    static final CollationSettings getSettings() {
        return (CollationSettings) getRoot().settings.readOnly();
    }

    static {
        CollationTailoring collationTailoring = null;
        try {
            ByteBuffer requiredData = ICUBinary.getRequiredData("coll/ucadata.icu");
            CollationTailoring collationTailoring2 = new CollationTailoring(null);
            CollationDataReader.read(null, requiredData, collationTailoring2);
            e = null;
            collationTailoring = collationTailoring2;
        } catch (IOException unused) {
            e = new MissingResourceException("IOException while reading CLDR root data", "CollationRoot", "data/icudt69b/coll/ucadata.icu");
        } catch (RuntimeException e) {
            e = e;
        }
        rootSingleton = collationTailoring;
        exception = e;
    }
}
