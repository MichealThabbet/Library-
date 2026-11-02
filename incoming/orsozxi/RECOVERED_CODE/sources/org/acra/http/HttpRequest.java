package org.acra.http;

import java.io.IOException;
import java.net.URL;

/* loaded from: classes3.dex */
public interface HttpRequest<T> {
    void send(URL url, T t) throws IOException;
}
