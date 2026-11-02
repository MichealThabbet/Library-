package org.apache.http.impl.cookie;

import java.io.IOException;
import java.io.Reader;
import org.apache.http.conn.util.PublicSuffixList;

@Deprecated
/* loaded from: classes3.dex */
public class PublicSuffixListParser {
    private final PublicSuffixFilter filter;
    private final org.apache.http.conn.util.PublicSuffixListParser parser = new org.apache.http.conn.util.PublicSuffixListParser();

    PublicSuffixListParser(PublicSuffixFilter publicSuffixFilter) {
        this.filter = publicSuffixFilter;
    }

    public void parse(Reader reader) throws IOException {
        PublicSuffixList publicSuffixList = this.parser.parse(reader);
        this.filter.setPublicSuffixes(publicSuffixList.getRules());
        this.filter.setExceptions(publicSuffixList.getExceptions());
    }
}
