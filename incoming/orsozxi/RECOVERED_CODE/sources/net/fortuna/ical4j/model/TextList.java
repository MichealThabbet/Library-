package net.fortuna.ical4j.model;

import it.piapostolos.android.pinotes.utils.ConstantsBase;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.fortuna.ical4j.util.Strings;

/* loaded from: classes3.dex */
public class TextList implements Serializable, Iterable<String> {
    private static final long serialVersionUID = -417427815871330636L;
    private List<String> texts;

    public TextList() {
        this.texts = new CopyOnWriteArrayList();
    }

    public TextList(String str) {
        this.texts = new CopyOnWriteArrayList();
        Matcher matcher = Pattern.compile("(?:\\\\.|[^\\\\,]++)+").matcher(str);
        while (matcher.find()) {
            this.texts.add(Strings.unescape(matcher.group().replace("\\\\", "\\")));
        }
    }

    public TextList(String[] strArr) {
        this.texts = Arrays.asList(strArr);
    }

    public final String toString() {
        return (String) this.texts.stream().map(new Function() { // from class: net.fortuna.ical4j.model.-$$Lambda$TextList$_mZiOL3rzeoVPqvXyviDTo4kinU
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Strings.escape((String) obj);
            }
        }).collect(Collectors.joining(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR));
    }

    public final boolean add(String str) {
        return this.texts.add(str);
    }

    public final boolean isEmpty() {
        return this.texts.isEmpty();
    }

    @Override // java.lang.Iterable
    public final Iterator<String> iterator() {
        return this.texts.iterator();
    }

    public final boolean remove(String str) {
        return this.texts.remove(str);
    }

    public final int size() {
        return this.texts.size();
    }
}
