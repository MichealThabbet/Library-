package org.jsoup.select;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Element;

/* loaded from: classes3.dex */
public class Selector {
    private Selector() {
    }

    public static Elements select(String str, Element element) {
        Validate.notEmpty(str);
        return select(QueryParser.parse(str), element);
    }

    public static Elements select(Evaluator evaluator, Element element) {
        Validate.notNull(evaluator);
        Validate.notNull(element);
        return Collector.collect(evaluator, element);
    }

    public static Elements select(String str, Iterable<Element> iterable) {
        Validate.notEmpty(str);
        Validate.notNull(iterable);
        Evaluator evaluator = QueryParser.parse(str);
        ArrayList arrayList = new ArrayList();
        IdentityHashMap identityHashMap = new IdentityHashMap();
        Iterator<Element> it2 = iterable.iterator();
        while (it2.hasNext()) {
            Iterator<Element> it3 = select(evaluator, it2.next()).iterator();
            while (it3.hasNext()) {
                Element next = it3.next();
                if (!identityHashMap.containsKey(next)) {
                    arrayList.add(next);
                    identityHashMap.put(next, Boolean.TRUE);
                }
            }
        }
        return new Elements((List<Element>) arrayList);
    }

    static Elements filterOut(Collection<Element> collection, Collection<Element> collection2) {
        Elements elements = new Elements();
        for (Element element : collection) {
            boolean z = false;
            Iterator<Element> it2 = collection2.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                if (element.equals(it2.next())) {
                    z = true;
                    break;
                }
            }
            if (!z) {
                elements.add(element);
            }
        }
        return elements;
    }

    public static Element selectFirst(String str, Element element) {
        Validate.notEmpty(str);
        return Collector.findFirst(QueryParser.parse(str), element);
    }

    public static class SelectorParseException extends IllegalStateException {
        public SelectorParseException(String str, Object... objArr) {
            super(String.format(str, objArr));
        }
    }
}
