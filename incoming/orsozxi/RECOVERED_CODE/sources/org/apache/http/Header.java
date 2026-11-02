package org.apache.http;

/* loaded from: classes3.dex */
public interface Header extends NameValuePair {
    HeaderElement[] getElements() throws ParseException;
}
