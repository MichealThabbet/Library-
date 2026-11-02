package com.ibm.icu.impl.coll;

import com.ibm.icu.util.ICUCloneNotSupportedException;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class SharedObject implements Cloneable {
    private AtomicInteger refCount = new AtomicInteger();

    public final void deleteIfZeroRefCount() {
    }

    public static final class Reference<T extends SharedObject> implements Cloneable {
        private T ref;

        public Reference(T t) {
            this.ref = t;
            if (t != null) {
                t.addRef();
            }
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public Reference<T> m23clone() {
            try {
                Reference<T> reference = (Reference) super.clone();
                T t = this.ref;
                if (t != null) {
                    t.addRef();
                }
                return reference;
            } catch (CloneNotSupportedException e) {
                throw new ICUCloneNotSupportedException(e);
            }
        }

        public T readOnly() {
            return this.ref;
        }

        public T copyOnWrite() {
            T t = this.ref;
            if (t.getRefCount() <= 1) {
                return t;
            }
            T t2 = (T) t.mo22clone();
            t.removeRef();
            this.ref = t2;
            t2.addRef();
            return t2;
        }

        public void clear() {
            T t = this.ref;
            if (t != null) {
                t.removeRef();
                this.ref = null;
            }
        }

        protected void finalize() throws Throwable {
            super.finalize();
            clear();
        }
    }

    @Override // 
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SharedObject mo22clone() {
        try {
            SharedObject sharedObject = (SharedObject) super.clone();
            sharedObject.refCount = new AtomicInteger();
            return sharedObject;
        } catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException(e);
        }
    }

    public final void addRef() {
        this.refCount.incrementAndGet();
    }

    public final void removeRef() {
        this.refCount.decrementAndGet();
    }

    public final int getRefCount() {
        return this.refCount.get();
    }
}
