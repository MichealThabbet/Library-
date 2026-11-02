package org.apache.http.client;

import java.io.IOException;
import org.apache.http.HttpResponse;

/* loaded from: classes3.dex */
public interface ResponseHandler<T> {
    T handleResponse(HttpResponse httpResponse) throws IOException;
}
