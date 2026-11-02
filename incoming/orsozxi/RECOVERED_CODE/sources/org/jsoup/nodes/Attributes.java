package org.jsoup.nodes;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.message.TokenParser;
import org.jsoup.SerializationException;
import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;

/* loaded from: classes3.dex */
public class Attributes implements Iterable<Attribute>, Cloneable {
    private static final String[] Empty = new String[0];
    private static final String EmptyString = "";
    private static final int GrowthFactor = 2;
    private static final int InitialCapacity = 2;
    static final char InternalPrefix = '/';
    static final int NotFound = -1;
    protected static final String dataPrefix = "data-";
    String[] keys;
    private int size = 0;
    String[] vals;

    static String checkNotNull(String str) {
        return str == null ? "" : str;
    }

    public Attributes() {
        String[] strArr = Empty;
        this.keys = strArr;
        this.vals = strArr;
    }

    private void checkCapacity(int i) {
        Validate.isTrue(i >= this.size);
        int length = this.keys.length;
        if (length >= i) {
            return;
        }
        int i2 = length >= 2 ? this.size * 2 : 2;
        if (i <= i2) {
            i = i2;
        }
        this.keys = copyOf(this.keys, i);
        this.vals = copyOf(this.vals, i);
    }

    private static String[] copyOf(String[] strArr, int i) {
        String[] strArr2 = new String[i];
        System.arraycopy(strArr, 0, strArr2, 0, Math.min(strArr.length, i));
        return strArr2;
    }

    int indexOfKey(String str) {
        Validate.notNull(str);
        for (int i = 0; i < this.size; i++) {
            if (str.equals(this.keys[i])) {
                return i;
            }
        }
        return -1;
    }

    private int indexOfKeyIgnoreCase(String str) {
        Validate.notNull(str);
        for (int i = 0; i < this.size; i++) {
            if (str.equalsIgnoreCase(this.keys[i])) {
                return i;
            }
        }
        return -1;
    }

    public String get(String str) {
        int iIndexOfKey = indexOfKey(str);
        return iIndexOfKey == -1 ? "" : checkNotNull(this.vals[iIndexOfKey]);
    }

    public String getIgnoreCase(String str) {
        int iIndexOfKeyIgnoreCase = indexOfKeyIgnoreCase(str);
        return iIndexOfKeyIgnoreCase == -1 ? "" : checkNotNull(this.vals[iIndexOfKeyIgnoreCase]);
    }

    public Attributes add(String str, String str2) {
        checkCapacity(this.size + 1);
        String[] strArr = this.keys;
        int i = this.size;
        strArr[i] = str;
        this.vals[i] = str2;
        this.size = i + 1;
        return this;
    }

    public Attributes put(String str, String str2) {
        Validate.notNull(str);
        int iIndexOfKey = indexOfKey(str);
        if (iIndexOfKey != -1) {
            this.vals[iIndexOfKey] = str2;
        } else {
            add(str, str2);
        }
        return this;
    }

    void putIgnoreCase(String str, String str2) {
        int iIndexOfKeyIgnoreCase = indexOfKeyIgnoreCase(str);
        if (iIndexOfKeyIgnoreCase != -1) {
            this.vals[iIndexOfKeyIgnoreCase] = str2;
            if (this.keys[iIndexOfKeyIgnoreCase].equals(str)) {
                return;
            }
            this.keys[iIndexOfKeyIgnoreCase] = str;
            return;
        }
        add(str, str2);
    }

    public Attributes put(String str, boolean z) {
        if (z) {
            putIgnoreCase(str, null);
        } else {
            remove(str);
        }
        return this;
    }

    public Attributes put(Attribute attribute) {
        Validate.notNull(attribute);
        put(attribute.getKey(), attribute.getValue());
        attribute.parent = this;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void remove(int i) {
        Validate.isFalse(i >= this.size);
        int i2 = (this.size - i) - 1;
        if (i2 > 0) {
            String[] strArr = this.keys;
            int i3 = i + 1;
            System.arraycopy(strArr, i3, strArr, i, i2);
            String[] strArr2 = this.vals;
            System.arraycopy(strArr2, i3, strArr2, i, i2);
        }
        int i4 = this.size - 1;
        this.size = i4;
        this.keys[i4] = null;
        this.vals[i4] = null;
    }

    public void remove(String str) {
        int iIndexOfKey = indexOfKey(str);
        if (iIndexOfKey != -1) {
            remove(iIndexOfKey);
        }
    }

    public void removeIgnoreCase(String str) {
        int iIndexOfKeyIgnoreCase = indexOfKeyIgnoreCase(str);
        if (iIndexOfKeyIgnoreCase != -1) {
            remove(iIndexOfKeyIgnoreCase);
        }
    }

    public boolean hasKey(String str) {
        return indexOfKey(str) != -1;
    }

    public boolean hasKeyIgnoreCase(String str) {
        return indexOfKeyIgnoreCase(str) != -1;
    }

    public boolean hasDeclaredValueForKey(String str) {
        int iIndexOfKey = indexOfKey(str);
        return (iIndexOfKey == -1 || this.vals[iIndexOfKey] == null) ? false : true;
    }

    public boolean hasDeclaredValueForKeyIgnoreCase(String str) {
        int iIndexOfKeyIgnoreCase = indexOfKeyIgnoreCase(str);
        return (iIndexOfKeyIgnoreCase == -1 || this.vals[iIndexOfKeyIgnoreCase] == null) ? false : true;
    }

    public int size() {
        int i = 0;
        for (int i2 = 0; i2 < this.size; i2++) {
            if (!isInternalKey(this.keys[i2])) {
                i++;
            }
        }
        return i;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void addAll(Attributes attributes) {
        if (attributes.size() == 0) {
            return;
        }
        checkCapacity(this.size + attributes.size);
        Iterator<Attribute> it2 = attributes.iterator();
        while (it2.hasNext()) {
            put(it2.next());
        }
    }

    @Override // java.lang.Iterable
    public Iterator<Attribute> iterator() {
        return new Iterator<Attribute>() { // from class: org.jsoup.nodes.Attributes.1
            int i = 0;

            @Override // java.util.Iterator
            public boolean hasNext() {
                while (this.i < Attributes.this.size) {
                    Attributes attributes = Attributes.this;
                    if (!attributes.isInternalKey(attributes.keys[this.i])) {
                        break;
                    }
                    this.i++;
                }
                return this.i < Attributes.this.size;
            }

            @Override // java.util.Iterator
            public Attribute next() {
                Attribute attribute = new Attribute(Attributes.this.keys[this.i], Attributes.this.vals[this.i], Attributes.this);
                this.i++;
                return attribute;
            }

            @Override // java.util.Iterator
            public void remove() {
                Attributes attributes = Attributes.this;
                int i = this.i - 1;
                this.i = i;
                attributes.remove(i);
            }
        };
    }

    public List<Attribute> asList() {
        ArrayList arrayList = new ArrayList(this.size);
        for (int i = 0; i < this.size; i++) {
            if (!isInternalKey(this.keys[i])) {
                arrayList.add(new Attribute(this.keys[i], this.vals[i], this));
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public Map<String, String> dataset() {
        return new Dataset();
    }

    public String html() {
        StringBuilder sbBorrowBuilder = StringUtil.borrowBuilder();
        try {
            html(sbBorrowBuilder, new Document("").outputSettings());
            return StringUtil.releaseBuilder(sbBorrowBuilder);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    final void html(Appendable appendable, Document.OutputSettings outputSettings) throws IOException {
        int i = this.size;
        for (int i2 = 0; i2 < i; i2++) {
            if (!isInternalKey(this.keys[i2])) {
                String str = this.keys[i2];
                String str2 = this.vals[i2];
                appendable.append(TokenParser.SP).append(str);
                if (!Attribute.shouldCollapseAttribute(str, str2, outputSettings)) {
                    appendable.append("=\"");
                    if (str2 == null) {
                        str2 = "";
                    }
                    Entities.escape(appendable, str2, outputSettings, true, false, false);
                    appendable.append('\"');
                }
            }
        }
    }

    public String toString() {
        return html();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Attributes attributes = (Attributes) obj;
        if (this.size == attributes.size && Arrays.equals(this.keys, attributes.keys)) {
            return Arrays.equals(this.vals, attributes.vals);
        }
        return false;
    }

    public int hashCode() {
        return (((this.size * 31) + Arrays.hashCode(this.keys)) * 31) + Arrays.hashCode(this.vals);
    }

    public Attributes clone() {
        try {
            Attributes attributes = (Attributes) super.clone();
            attributes.size = this.size;
            this.keys = copyOf(this.keys, this.size);
            this.vals = copyOf(this.vals, this.size);
            return attributes;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void normalize() {
        for (int i = 0; i < this.size; i++) {
            String[] strArr = this.keys;
            strArr[i] = Normalizer.lowerCase(strArr[i]);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int deduplicate(org.jsoup.parser.ParseSettings r7) {
        /*
            r6 = this;
            boolean r0 = r6.isEmpty()
            r1 = 0
            if (r0 == 0) goto L8
            return r1
        L8:
            boolean r7 = r7.preserveAttributeCase()
            r0 = 0
        Ld:
            java.lang.String[] r2 = r6.keys
            int r2 = r2.length
            if (r1 >= r2) goto L45
            int r2 = r1 + 1
            r3 = r2
        L15:
            java.lang.String[] r4 = r6.keys
            int r5 = r4.length
            if (r3 >= r5) goto L43
            r5 = r4[r3]
            if (r5 != 0) goto L1f
            goto L43
        L1f:
            if (r7 == 0) goto L2b
            r5 = r4[r1]
            r4 = r4[r3]
            boolean r4 = r5.equals(r4)
            if (r4 != 0) goto L39
        L2b:
            if (r7 != 0) goto L40
            java.lang.String[] r4 = r6.keys
            r5 = r4[r1]
            r4 = r4[r3]
            boolean r4 = r5.equalsIgnoreCase(r4)
            if (r4 == 0) goto L40
        L39:
            int r0 = r0 + 1
            r6.remove(r3)
            int r3 = r3 + (-1)
        L40:
            int r3 = r3 + 1
            goto L15
        L43:
            r1 = r2
            goto Ld
        L45:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jsoup.nodes.Attributes.deduplicate(org.jsoup.parser.ParseSettings):int");
    }

    private static class Dataset extends AbstractMap<String, String> {
        private final Attributes attributes;

        private Dataset(Attributes attributes) {
            this.attributes = attributes;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<String, String>> entrySet() {
            return new EntrySet();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public String put(String str, String str2) {
            String strDataKey = Attributes.dataKey(str);
            String str3 = this.attributes.hasKey(strDataKey) ? this.attributes.get(strDataKey) : null;
            this.attributes.put(strDataKey, str2);
            return str3;
        }

        private class EntrySet extends AbstractSet<Map.Entry<String, String>> {
            private EntrySet() {
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<String, String>> iterator() {
                return new DatasetIterator();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                int i = 0;
                while (new DatasetIterator().hasNext()) {
                    i++;
                }
                return i;
            }
        }

        private class DatasetIterator implements Iterator<Map.Entry<String, String>> {
            private Attribute attr;
            private Iterator<Attribute> attrIter;

            private DatasetIterator() {
                this.attrIter = Dataset.this.attributes.iterator();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                while (this.attrIter.hasNext()) {
                    Attribute next = this.attrIter.next();
                    this.attr = next;
                    if (next.isDataAttribute()) {
                        return true;
                    }
                }
                return false;
            }

            @Override // java.util.Iterator
            public Map.Entry<String, String> next() {
                return new Attribute(this.attr.getKey().substring(5), this.attr.getValue());
            }

            @Override // java.util.Iterator
            public void remove() {
                Dataset.this.attributes.remove(this.attr.getKey());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String dataKey(String str) {
        return dataPrefix + str;
    }

    static String internalKey(String str) {
        return '/' + str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInternalKey(String str) {
        return str != null && str.length() > 1 && str.charAt(0) == '/';
    }
}
