package org.apache.http.impl.conn;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeLayeredSocketFactory;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@Deprecated
/* loaded from: classes3.dex */
public class DefaultClientConnectionOperator implements ClientConnectionOperator {
    protected final DnsResolver dnsResolver;
    private final Log log = LogFactory.getLog(getClass());
    protected final SchemeRegistry schemeRegistry;

    public DefaultClientConnectionOperator(SchemeRegistry schemeRegistry) {
        Args.notNull(schemeRegistry, "Scheme registry");
        this.schemeRegistry = schemeRegistry;
        this.dnsResolver = new SystemDefaultDnsResolver();
    }

    public DefaultClientConnectionOperator(SchemeRegistry schemeRegistry, DnsResolver dnsResolver) {
        Args.notNull(schemeRegistry, "Scheme registry");
        Args.notNull(dnsResolver, "DNS resolver");
        this.schemeRegistry = schemeRegistry;
        this.dnsResolver = dnsResolver;
    }

    @Override // org.apache.http.conn.ClientConnectionOperator
    public OperatedClientConnection createConnection() {
        return new DefaultClientConnection();
    }

    private SchemeRegistry getSchemeRegistry(HttpContext httpContext) {
        SchemeRegistry schemeRegistry = (SchemeRegistry) httpContext.getAttribute(ClientContext.SCHEME_REGISTRY);
        return schemeRegistry == null ? this.schemeRegistry : schemeRegistry;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00cb A[SYNTHETIC] */
    @Override // org.apache.http.conn.ClientConnectionOperator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void openConnection(org.apache.http.conn.OperatedClientConnection r17, org.apache.http.HttpHost r18, java.net.InetAddress r19, org.apache.http.protocol.HttpContext r20, org.apache.http.params.HttpParams r21) throws java.io.IOException {
        /*
            r16 = this;
            r1 = r16
            r2 = r17
            r3 = r18
            r4 = r19
            r5 = r20
            r6 = r21
            java.lang.String r0 = "Connection"
            org.apache.http.util.Args.notNull(r2, r0)
            java.lang.String r0 = "Target host"
            org.apache.http.util.Args.notNull(r3, r0)
            java.lang.String r0 = "HTTP parameters"
            org.apache.http.util.Args.notNull(r6, r0)
            boolean r0 = r17.isOpen()
            r7 = 1
            r0 = r0 ^ r7
            java.lang.String r8 = "Connection must not be open"
            org.apache.http.util.Asserts.check(r0, r8)
            org.apache.http.conn.scheme.SchemeRegistry r0 = r1.getSchemeRegistry(r5)
            java.lang.String r8 = r18.getSchemeName()
            org.apache.http.conn.scheme.Scheme r0 = r0.getScheme(r8)
            org.apache.http.conn.scheme.SchemeSocketFactory r8 = r0.getSchemeSocketFactory()
            java.lang.String r9 = r18.getHostName()
            java.net.InetAddress[] r9 = r1.resolveHostname(r9)
            int r10 = r18.getPort()
            int r10 = r0.resolvePort(r10)
            r11 = 0
            r12 = 0
        L48:
            int r0 = r9.length
            if (r12 >= r0) goto Ld4
            r0 = r9[r12]
            int r13 = r9.length
            int r13 = r13 - r7
            if (r12 != r13) goto L53
            r13 = 1
            goto L54
        L53:
            r13 = 0
        L54:
            java.net.Socket r14 = r8.createSocket(r6)
            r2.opening(r14, r3)
            org.apache.http.conn.HttpInetSocketAddress r15 = new org.apache.http.conn.HttpInetSocketAddress
            r15.<init>(r3, r0, r10)
            r0 = 0
            if (r4 == 0) goto L68
            java.net.InetSocketAddress r0 = new java.net.InetSocketAddress
            r0.<init>(r4, r11)
        L68:
            org.apache.commons.logging.Log r7 = r1.log
            boolean r7 = r7.isDebugEnabled()
            if (r7 == 0) goto L86
            org.apache.commons.logging.Log r7 = r1.log
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r4 = "Connecting to "
            r11.append(r4)
            r11.append(r15)
            java.lang.String r4 = r11.toString()
            r7.debug(r4)
        L86:
            java.net.Socket r0 = r8.connectSocket(r14, r15, r0, r6)     // Catch: org.apache.http.conn.ConnectTimeoutException -> L9b java.net.ConnectException -> La0
            if (r14 == r0) goto L90
            r2.opening(r0, r3)     // Catch: org.apache.http.conn.ConnectTimeoutException -> L9b java.net.ConnectException -> La0
            r14 = r0
        L90:
            r1.prepareSocket(r14, r5, r6)     // Catch: org.apache.http.conn.ConnectTimeoutException -> L9b java.net.ConnectException -> La0
            boolean r0 = r8.isSecure(r14)     // Catch: org.apache.http.conn.ConnectTimeoutException -> L9b java.net.ConnectException -> La0
            r2.openCompleted(r0, r6)     // Catch: org.apache.http.conn.ConnectTimeoutException -> L9b java.net.ConnectException -> La0
            return
        L9b:
            r0 = move-exception
            if (r13 != 0) goto L9f
            goto La3
        L9f:
            throw r0
        La0:
            r0 = move-exception
            if (r13 != 0) goto Ld3
        La3:
            org.apache.commons.logging.Log r0 = r1.log
            boolean r0 = r0.isDebugEnabled()
            if (r0 == 0) goto Lcb
            org.apache.commons.logging.Log r0 = r1.log
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r7 = "Connect to "
            r4.append(r7)
            r4.append(r15)
            java.lang.String r7 = " timed out. "
            r4.append(r7)
            java.lang.String r7 = "Connection will be retried using another IP address"
            r4.append(r7)
            java.lang.String r4 = r4.toString()
            r0.debug(r4)
        Lcb:
            int r12 = r12 + 1
            r4 = r19
            r7 = 1
            r11 = 0
            goto L48
        Ld3:
            throw r0
        Ld4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.conn.DefaultClientConnectionOperator.openConnection(org.apache.http.conn.OperatedClientConnection, org.apache.http.HttpHost, java.net.InetAddress, org.apache.http.protocol.HttpContext, org.apache.http.params.HttpParams):void");
    }

    @Override // org.apache.http.conn.ClientConnectionOperator
    public void updateSecureConnection(OperatedClientConnection operatedClientConnection, HttpHost httpHost, HttpContext httpContext, HttpParams httpParams) throws IOException {
        Args.notNull(operatedClientConnection, "Connection");
        Args.notNull(httpHost, "Target host");
        Args.notNull(httpParams, "Parameters");
        Asserts.check(operatedClientConnection.isOpen(), "Connection must be open");
        Scheme scheme = getSchemeRegistry(httpContext).getScheme(httpHost.getSchemeName());
        Asserts.check(scheme.getSchemeSocketFactory() instanceof SchemeLayeredSocketFactory, "Socket factory must implement SchemeLayeredSocketFactory");
        SchemeLayeredSocketFactory schemeLayeredSocketFactory = (SchemeLayeredSocketFactory) scheme.getSchemeSocketFactory();
        Socket socketCreateLayeredSocket = schemeLayeredSocketFactory.createLayeredSocket(operatedClientConnection.getSocket(), httpHost.getHostName(), scheme.resolvePort(httpHost.getPort()), httpParams);
        prepareSocket(socketCreateLayeredSocket, httpContext, httpParams);
        operatedClientConnection.update(socketCreateLayeredSocket, httpHost, schemeLayeredSocketFactory.isSecure(socketCreateLayeredSocket), httpParams);
    }

    protected void prepareSocket(Socket socket, HttpContext httpContext, HttpParams httpParams) throws IOException {
        socket.setTcpNoDelay(HttpConnectionParams.getTcpNoDelay(httpParams));
        socket.setSoTimeout(HttpConnectionParams.getSoTimeout(httpParams));
        int linger = HttpConnectionParams.getLinger(httpParams);
        if (linger >= 0) {
            socket.setSoLinger(linger > 0, linger);
        }
    }

    protected InetAddress[] resolveHostname(String str) throws UnknownHostException {
        return this.dnsResolver.resolve(str);
    }
}
