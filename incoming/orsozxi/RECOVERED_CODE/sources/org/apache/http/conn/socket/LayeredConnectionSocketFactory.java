package org.apache.http.conn.socket;

import java.io.IOException;
import java.net.Socket;
import org.apache.http.protocol.HttpContext;

/* loaded from: classes3.dex */
public interface LayeredConnectionSocketFactory extends ConnectionSocketFactory {
    Socket createLayeredSocket(Socket socket, String str, int i, HttpContext httpContext) throws IOException;
}
