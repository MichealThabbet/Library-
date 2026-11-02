package net.fortuna.ical4j.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.function.Supplier;
import net.fortuna.ical4j.util.CompatibilityHints;
import org.apache.commons.lang3.Validate;

@Deprecated
/* loaded from: classes3.dex */
public abstract class AbstractContentFactory<T> implements Serializable, Supplier<List<T>> {
    private final Map<String, T> extendedFactories = new HashMap();
    protected transient ServiceLoader<T> factoryLoader;

    protected abstract boolean factorySupports(T t, String str);

    public AbstractContentFactory(ServiceLoader<T> serviceLoader) {
        this.factoryLoader = serviceLoader;
    }

    @Deprecated
    protected final void registerExtendedFactory(String str, T t) {
        this.extendedFactories.put(str, t);
    }

    protected final T getFactory(String str) {
        T next;
        Validate.notBlank(str, "Invalid factory key: [%s]", str);
        Iterator<T> it2 = this.factoryLoader.iterator();
        while (true) {
            if (!it2.hasNext()) {
                next = null;
                break;
            }
            next = it2.next();
            if (factorySupports(next, str)) {
                break;
            }
        }
        return next == null ? this.extendedFactories.get(str) : next;
    }

    protected boolean allowIllegalNames() {
        return CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING);
    }

    @Override // java.util.function.Supplier
    public List<T> get() {
        ArrayList arrayList = new ArrayList();
        Iterator<T> it2 = this.factoryLoader.iterator();
        while (it2.hasNext()) {
            arrayList.add(it2.next());
        }
        arrayList.addAll(this.extendedFactories.values());
        return arrayList;
    }
}
