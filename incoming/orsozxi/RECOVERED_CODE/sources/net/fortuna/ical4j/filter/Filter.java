package net.fortuna.ical4j.filter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import org.slf4j.LoggerFactory;

/* loaded from: classes3.dex */
public class Filter<T> {
    public static final int MATCH_ALL = 2;
    public static final int MATCH_ANY = 1;
    private List<Predicate<T>> rules;
    private int type;

    @SafeVarargs
    public Filter(Predicate<T>... predicateArr) {
        this(predicateArr, 1);
    }

    public Filter(Predicate<T>[] predicateArr, int i) {
        this.rules = Arrays.asList(predicateArr);
        this.type = i;
    }

    public final Collection<T> filter(Collection<T> collection) {
        Collection<T> arrayList;
        if (getRules() == null || getRules().length <= 0) {
            return collection;
        }
        try {
            arrayList = (Collection) collection.getClass().newInstance();
        } catch (IllegalAccessException | InstantiationException unused) {
            arrayList = new ArrayList<>();
        }
        if (this.type == 2) {
            arrayList.addAll(matchAll(collection));
        } else {
            arrayList.addAll(matchAny(collection));
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private List<T> matchAll(Collection<T> collection) {
        ArrayList arrayList = new ArrayList(collection);
        ArrayList arrayList2 = new ArrayList();
        int i = 0;
        while (i < getRules().length) {
            for (Object obj : arrayList) {
                if (getRules()[i].test(obj)) {
                    arrayList2.add(obj);
                }
            }
            i++;
            arrayList = arrayList2;
            arrayList2 = new ArrayList();
        }
        return arrayList;
    }

    private List<T> matchAny(Collection<T> collection) {
        ArrayList arrayList = new ArrayList();
        for (T t : collection) {
            int i = 0;
            while (true) {
                if (i >= getRules().length) {
                    break;
                }
                if (getRules()[i].test(t)) {
                    arrayList.add(t);
                    break;
                }
                i++;
            }
        }
        return arrayList;
    }

    public final T[] filter(T[] tArr) {
        Collection<T> collectionFilter = filter(Arrays.asList(tArr));
        try {
            return (T[]) collectionFilter.toArray((Object[]) Array.newInstance(tArr.getClass(), collectionFilter.size()));
        } catch (ArrayStoreException e) {
            LoggerFactory.getLogger((Class<?>) Filter.class).warn("Error converting to array - using default approach", (Throwable) e);
            return (T[]) collectionFilter.toArray();
        }
    }

    public final Predicate<T>[] getRules() {
        return (Predicate[]) this.rules.toArray(new Predicate[0]);
    }

    public final void setRules(Predicate<T>[] predicateArr) {
        this.rules = Arrays.asList(predicateArr);
    }
}
