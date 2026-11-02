package net.fortuna.ical4j.model;

import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/* loaded from: classes3.dex */
public class LocationTypeList implements Serializable, Iterable<String> {
    private static final long serialVersionUID = -9181735547604179160L;
    private List<String> locationTypes = new CopyOnWriteArrayList();

    public LocationTypeList() {
    }

    public LocationTypeList(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
        while (stringTokenizer.hasMoreTokens()) {
            this.locationTypes.add(stringTokenizer.nextToken());
        }
    }

    public final String toString() {
        return (String) this.locationTypes.stream().collect(Collectors.joining(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR));
    }

    public final boolean add(String str) {
        return this.locationTypes.add(str);
    }

    public final boolean isEmpty() {
        return this.locationTypes.isEmpty();
    }

    @Override // java.lang.Iterable
    public final Iterator<String> iterator() {
        return this.locationTypes.iterator();
    }

    public final boolean remove(String str) {
        return this.locationTypes.remove(str);
    }

    public final int size() {
        return this.locationTypes.size();
    }
}
