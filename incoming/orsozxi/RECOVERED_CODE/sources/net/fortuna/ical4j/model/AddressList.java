package net.fortuna.ical4j.model;

import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.util.Uris;

/* loaded from: classes3.dex */
public class AddressList implements Serializable, Iterable<URI> {
    private static final long serialVersionUID = 81383256078213569L;
    private List<URI> addresses = new CopyOnWriteArrayList();

    public AddressList() {
    }

    public AddressList(String str) throws URISyntaxException {
        StringTokenizer stringTokenizer = new StringTokenizer(str, ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
        while (stringTokenizer.hasMoreTokens()) {
            try {
                this.addresses.add(new URI(Uris.encode(Strings.unquote(stringTokenizer.nextToken()))));
            } catch (URISyntaxException e) {
                if (!CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING)) {
                    throw e;
                }
            }
        }
    }

    public final String toString() {
        return (String) this.addresses.stream().map(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$AddressList$ChtUio2dNZhWEdebvWTubP1QECE
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Strings.quote(Uris.decode(Strings.valueOf((URI) obj)));
            }
        }).collect(Collectors.joining(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR));
    }

    public final boolean add(URI uri) {
        return this.addresses.add(uri);
    }

    public final boolean isEmpty() {
        return this.addresses.isEmpty();
    }

    @Override // java.lang.Iterable
    public final Iterator<URI> iterator() {
        return this.addresses.iterator();
    }

    public final boolean remove(URI uri) {
        return this.addresses.remove(uri);
    }

    public final int size() {
        return this.addresses.size();
    }
}
