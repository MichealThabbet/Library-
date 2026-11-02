package org.apache.commons.collections4.functors;

import java.io.Serializable;
import org.apache.commons.collections4.Predicate;

/* loaded from: classes3.dex */
public final class InstanceofPredicate implements Predicate<Object>, Serializable {
    private static final long serialVersionUID = -6682656911025165584L;
    private final Class<?> iType;

    public static Predicate<Object> instanceOfPredicate(Class<?> cls) {
        if (cls == null) {
            throw new NullPointerException("The type to check instanceof must not be null");
        }
        return new InstanceofPredicate(cls);
    }

    public InstanceofPredicate(Class<?> cls) {
        this.iType = cls;
    }

    @Override // org.apache.commons.collections4.Predicate
    public boolean evaluate(Object obj) {
        return this.iType.isInstance(obj);
    }

    public Class<?> getType() {
        return this.iType;
    }
}
