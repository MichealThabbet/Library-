package org.apache.http.io;

import org.apache.http.HttpMessage;

/* loaded from: classes3.dex */
public interface HttpMessageWriterFactory<T extends HttpMessage> {
    HttpMessageWriter<T> create(SessionOutputBuffer sessionOutputBuffer);
}
