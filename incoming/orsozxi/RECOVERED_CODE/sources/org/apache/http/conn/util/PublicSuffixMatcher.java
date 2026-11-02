package org.apache.http.conn.util;

import java.net.IDN;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.util.Args;

/* loaded from: classes3.dex */
public final class PublicSuffixMatcher {

    /* renamed from: exceptions, reason: collision with root package name */
    private final Map<String, DomainType> f34exceptions;
    private final Map<String, DomainType> rules;

    public PublicSuffixMatcher(Collection<String> collection, Collection<String> collection2) {
        this(DomainType.UNKNOWN, collection, collection2);
    }

    public PublicSuffixMatcher(DomainType domainType, Collection<String> collection, Collection<String> collection2) {
        Args.notNull(domainType, "Domain type");
        Args.notNull(collection, "Domain suffix rules");
        this.rules = new ConcurrentHashMap(collection.size());
        Iterator<String> it2 = collection.iterator();
        while (it2.hasNext()) {
            this.rules.put(it2.next(), domainType);
        }
        this.f34exceptions = new ConcurrentHashMap();
        if (collection2 != null) {
            Iterator<String> it3 = collection2.iterator();
            while (it3.hasNext()) {
                this.f34exceptions.put(it3.next(), domainType);
            }
        }
    }

    public PublicSuffixMatcher(Collection<PublicSuffixList> collection) {
        Args.notNull(collection, "Domain suffix lists");
        this.rules = new ConcurrentHashMap();
        this.f34exceptions = new ConcurrentHashMap();
        for (PublicSuffixList publicSuffixList : collection) {
            DomainType type = publicSuffixList.getType();
            Iterator<String> it2 = publicSuffixList.getRules().iterator();
            while (it2.hasNext()) {
                this.rules.put(it2.next(), type);
            }
            List<String> exceptions2 = publicSuffixList.getExceptions();
            if (exceptions2 != null) {
                Iterator<String> it3 = exceptions2.iterator();
                while (it3.hasNext()) {
                    this.f34exceptions.put(it3.next(), type);
                }
            }
        }
    }

    private static boolean hasEntry(Map<String, DomainType> map, String str, DomainType domainType) {
        DomainType domainType2;
        if (map == null || (domainType2 = map.get(str)) == null) {
            return false;
        }
        return domainType == null || domainType2.equals(domainType);
    }

    private boolean hasRule(String str, DomainType domainType) {
        return hasEntry(this.rules, str, domainType);
    }

    private boolean hasException(String str, DomainType domainType) {
        return hasEntry(this.f34exceptions, str, domainType);
    }

    public String getDomainRoot(String str) {
        return getDomainRoot(str, null);
    }

    public String getDomainRoot(String str, DomainType domainType) {
        if (str == null || str.startsWith(".")) {
            return null;
        }
        String lowerCase = str.toLowerCase(Locale.ROOT);
        String str2 = null;
        while (lowerCase != null) {
            if (!hasException(IDN.toUnicode(lowerCase), domainType)) {
                if (hasRule(IDN.toUnicode(lowerCase), domainType)) {
                    break;
                }
                int iIndexOf = lowerCase.indexOf(46);
                String strSubstring = iIndexOf != -1 ? lowerCase.substring(iIndexOf + 1) : null;
                if (strSubstring != null) {
                    if (hasRule("*." + IDN.toUnicode(strSubstring), domainType)) {
                        break;
                    }
                }
                if (iIndexOf != -1) {
                    str2 = lowerCase;
                }
                lowerCase = strSubstring;
            } else {
                return lowerCase;
            }
        }
        return str2;
    }

    public boolean matches(String str) {
        return matches(str, null);
    }

    public boolean matches(String str, DomainType domainType) {
        if (str == null) {
            return false;
        }
        if (str.startsWith(".")) {
            str = str.substring(1);
        }
        return getDomainRoot(str, domainType) == null;
    }
}
