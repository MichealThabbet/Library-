package com.sun.mail.util;

import it.piapostolos.android.pinotes.R2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.security.GeneralSecurityException;
import java.security.PrivilegedAction;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.SocketFactory;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes2.dex */
public class SocketFetcher {
    private static MailLogger logger = new MailLogger(SocketFetcher.class, "socket", "DEBUG SocketFetcher", PropUtil.getBooleanSystemProperty("mail.socket.debug", false), System.out);

    private SocketFetcher() {
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x01ea  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01fd  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0221  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0240  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.net.Socket getSocket(java.lang.String r22, int r23, java.util.Properties r24, java.lang.String r25, boolean r26) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 616
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sun.mail.util.SocketFetcher.getSocket(java.lang.String, int, java.util.Properties, java.lang.String, boolean):java.net.Socket");
    }

    public static Socket getSocket(String str, int i, Properties properties, String str2) throws IOException {
        return getSocket(str, i, properties, str2, false);
    }

    private static Socket createSocket(InetAddress inetAddress, int i, String str, int i2, int i3, int i4, Properties properties, String str2, SocketFactory socketFactory, boolean z) throws NumberFormatException, IOException {
        String property;
        String str3;
        int intProperty;
        String str4;
        int i5;
        int i6;
        Socket socketCreateSocket;
        String str5;
        SSLSocketFactory sSLSocketFactory;
        int i7;
        String str6;
        int i8;
        int i9;
        SocketFactory socketFactory2 = socketFactory;
        if (logger.isLoggable(Level.FINEST)) {
            logger.finest("create socket: prefix " + str2 + ", localaddr " + inetAddress + ", localport " + i + ", host " + str + ", port " + i2 + ", connection timeout " + i3 + ", timeout " + i4 + ", socket factory " + socketFactory2 + ", useSSL " + z);
        }
        String property2 = properties.getProperty(str2 + ".proxy.host", null);
        String property3 = properties.getProperty(str2 + ".proxy.user", null);
        String property4 = properties.getProperty(str2 + ".proxy.password", null);
        int i10 = R2.attr.telltales_velocityMode;
        if (property2 != null) {
            int iIndexOf = property2.indexOf(58);
            if (iIndexOf >= 0) {
                try {
                    i9 = Integer.parseInt(property2.substring(iIndexOf + 1));
                    i8 = 0;
                } catch (NumberFormatException unused) {
                    i8 = 0;
                    i9 = 80;
                }
                property2 = property2.substring(i8, iIndexOf);
                i7 = i9;
            } else {
                i7 = 80;
            }
            int intProperty2 = PropUtil.getIntProperty(properties, str2 + ".proxy.port", i7);
            String str7 = "Using web proxy host, port: " + property2 + ", " + intProperty2;
            if (logger.isLoggable(Level.FINER)) {
                MailLogger mailLogger = logger;
                StringBuilder sb = new StringBuilder();
                str6 = str7;
                sb.append("web proxy host ");
                sb.append(property2);
                sb.append(", port ");
                sb.append(intProperty2);
                mailLogger.finer(sb.toString());
                if (property3 != null) {
                    MailLogger mailLogger2 = logger;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("web proxy user ");
                    sb2.append(property3);
                    sb2.append(", password ");
                    sb2.append(property4 == null ? "<null>" : "<non-null>");
                    mailLogger2.finer(sb2.toString());
                }
            } else {
                str6 = str7;
            }
            str3 = property2;
            str4 = str6;
            intProperty = R2.attr.telltales_velocityMode;
            property = null;
            i5 = intProperty2;
        } else {
            property = properties.getProperty(str2 + ".socks.host", null);
            if (property != null) {
                int iIndexOf2 = property.indexOf(58);
                if (iIndexOf2 >= 0) {
                    try {
                        i10 = Integer.parseInt(property.substring(iIndexOf2 + 1));
                    } catch (NumberFormatException unused2) {
                    }
                    property = property.substring(0, iIndexOf2);
                    i6 = i10;
                } else {
                    i6 = R2.attr.telltales_velocityMode;
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str2);
                str3 = property2;
                sb3.append(".socks.port");
                intProperty = PropUtil.getIntProperty(properties, sb3.toString(), i6);
                String str8 = "Using SOCKS host, port: " + property + ", " + intProperty;
                if (logger.isLoggable(Level.FINER)) {
                    MailLogger mailLogger3 = logger;
                    StringBuilder sb4 = new StringBuilder();
                    str4 = str8;
                    sb4.append("socks host ");
                    sb4.append(property);
                    sb4.append(", port ");
                    sb4.append(intProperty);
                    mailLogger3.finer(sb4.toString());
                } else {
                    str4 = str8;
                }
            } else {
                str3 = property2;
                intProperty = R2.attr.telltales_velocityMode;
                str4 = null;
            }
            i5 = 80;
        }
        Socket socketCreateSocket2 = (socketFactory2 == null || (socketFactory2 instanceof SSLSocketFactory)) ? null : socketFactory.createSocket();
        if (socketCreateSocket2 == null) {
            if (property != null) {
                socketCreateSocket2 = new Socket(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(property, intProperty)));
            } else {
                if (PropUtil.getBooleanProperty(properties, str2 + ".usesocketchannels", false)) {
                    logger.finer("using SocketChannels");
                    socketCreateSocket2 = SocketChannel.open().socket();
                } else {
                    socketCreateSocket2 = new Socket();
                }
            }
        }
        if (i4 >= 0) {
            if (logger.isLoggable(Level.FINEST)) {
                logger.finest("set socket read timeout " + i4);
            }
            socketCreateSocket2.setSoTimeout(i4);
        }
        int intProperty3 = PropUtil.getIntProperty(properties, str2 + ".writetimeout", -1);
        if (intProperty3 != -1) {
            if (logger.isLoggable(Level.FINEST)) {
                logger.finest("set socket write timeout " + intProperty3);
            }
            socketCreateSocket = new WriteTimeoutSocket(socketCreateSocket2, intProperty3);
        } else {
            socketCreateSocket = socketCreateSocket2;
        }
        if (inetAddress != null) {
            socketCreateSocket.bind(new InetSocketAddress(inetAddress, i));
        }
        try {
            logger.finest("connecting...");
        } catch (IOException e) {
            e = e;
        }
        try {
            if (str3 != null) {
                proxyConnect(socketCreateSocket, str3, i5, property3, property4, str, i2, i3);
                str5 = str;
            } else if (i3 >= 0) {
                str5 = str;
                socketCreateSocket.connect(new InetSocketAddress(str5, i2), i3);
            } else {
                str5 = str;
                socketCreateSocket.connect(new InetSocketAddress(str5, i2));
            }
            logger.finest("success!");
            if ((z || (socketFactory2 instanceof SSLSocketFactory)) && !(socketCreateSocket instanceof SSLSocket)) {
                String property5 = properties.getProperty(str2 + ".ssl.trust");
                if (property5 != null) {
                    try {
                        MailSSLSocketFactory mailSSLSocketFactory = new MailSSLSocketFactory();
                        if (property5.equals("*")) {
                            mailSSLSocketFactory.setTrustAllHosts(true);
                        } else {
                            mailSSLSocketFactory.setTrustedHosts(property5.split("\\s+"));
                        }
                        sSLSocketFactory = mailSSLSocketFactory;
                    } catch (GeneralSecurityException e2) {
                        IOException iOException = new IOException("Can't create MailSSLSocketFactory");
                        iOException.initCause(e2);
                        throw iOException;
                    }
                } else if (socketFactory2 instanceof SSLSocketFactory) {
                    sSLSocketFactory = (SSLSocketFactory) socketFactory2;
                } else {
                    sSLSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
                }
                socketCreateSocket = sSLSocketFactory.createSocket(socketCreateSocket, str5, i2, true);
                socketFactory2 = sSLSocketFactory;
            }
            configureSSLSocket(socketCreateSocket, str5, properties, str2, socketFactory2);
            return socketCreateSocket;
        } catch (IOException e3) {
            e = e3;
            IOException iOException2 = e;
            logger.log(Level.FINEST, "connection failed", (Throwable) iOException2);
            throw new SocketConnectException(str4, iOException2, str, i2, i3);
        }
    }

    private static SocketFactory getSocketFactory(String str) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException {
        Class<?> cls = null;
        if (str == null || str.length() == 0) {
            return null;
        }
        ClassLoader contextClassLoader = getContextClassLoader();
        if (contextClassLoader != null) {
            try {
                cls = Class.forName(str, false, contextClassLoader);
            } catch (ClassNotFoundException unused) {
            }
        }
        if (cls == null) {
            cls = Class.forName(str);
        }
        return (SocketFactory) cls.getMethod("getDefault", new Class[0]).invoke(new Object(), new Object[0]);
    }

    @Deprecated
    public static Socket startTLS(Socket socket) throws IOException {
        return startTLS(socket, new Properties(), "socket");
    }

    @Deprecated
    public static Socket startTLS(Socket socket, Properties properties, String str) throws IOException {
        return startTLS(socket, socket.getInetAddress().getHostName(), properties, str);
    }

    public static Socket startTLS(Socket socket, String str, Properties properties, String str2) throws IOException {
        Object socketFactory;
        int port = socket.getPort();
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("startTLS host " + str + ", port " + port);
        }
        String str3 = "unknown socket factory";
        try {
            Object obj = properties.get(str2 + ".ssl.socketFactory");
            SSLSocketFactory sSLSocketFactory = null;
            sSLSocketFactory = null;
            if (obj instanceof SocketFactory) {
                socketFactory = (SocketFactory) obj;
                str3 = "SSL socket factory instance " + socketFactory;
            } else {
                socketFactory = null;
            }
            if (socketFactory == null) {
                String property = properties.getProperty(str2 + ".ssl.socketFactory.class");
                str3 = "SSL socket factory class " + property;
                socketFactory = getSocketFactory(property);
            }
            if (socketFactory != null && (socketFactory instanceof SSLSocketFactory)) {
                sSLSocketFactory = (SSLSocketFactory) socketFactory;
            }
            if (sSLSocketFactory == null) {
                Object obj2 = properties.get(str2 + ".socketFactory");
                if (obj2 instanceof SocketFactory) {
                    socketFactory = (SocketFactory) obj2;
                    str3 = "socket factory instance " + socketFactory;
                }
                if (socketFactory == null) {
                    String property2 = properties.getProperty(str2 + ".socketFactory.class");
                    str3 = "socket factory class " + property2;
                    socketFactory = getSocketFactory(property2);
                }
                if (socketFactory != null && (socketFactory instanceof SSLSocketFactory)) {
                    sSLSocketFactory = (SSLSocketFactory) socketFactory;
                }
            }
            SSLSocketFactory sSLSocketFactory2 = sSLSocketFactory;
            if (sSLSocketFactory == null) {
                String property3 = properties.getProperty(str2 + ".ssl.trust");
                if (property3 != null) {
                    try {
                        MailSSLSocketFactory mailSSLSocketFactory = new MailSSLSocketFactory();
                        if (property3.equals("*")) {
                            mailSSLSocketFactory.setTrustAllHosts(true);
                        } else {
                            mailSSLSocketFactory.setTrustedHosts(property3.split("\\s+"));
                        }
                        sSLSocketFactory2 = mailSSLSocketFactory;
                    } catch (GeneralSecurityException e) {
                        IOException iOException = new IOException("Can't create MailSSLSocketFactory");
                        iOException.initCause(e);
                        throw iOException;
                    }
                } else {
                    sSLSocketFactory2 = (SSLSocketFactory) SSLSocketFactory.getDefault();
                }
            }
            Socket socketCreateSocket = sSLSocketFactory2.createSocket(socket, str, port, true);
            configureSSLSocket(socketCreateSocket, str, properties, str2, sSLSocketFactory2);
            return socketCreateSocket;
        } catch (Exception e2) {
            e = e2;
            if (e instanceof InvocationTargetException) {
                Throwable targetException = ((InvocationTargetException) e).getTargetException();
                if (targetException instanceof Exception) {
                    e = (Exception) targetException;
                }
            }
            if (e instanceof IOException) {
                throw ((IOException) e);
            }
            IOException iOException2 = new IOException("Exception in startTLS using " + str3 + ": host, port: " + str + ", " + port + "; Exception: " + e);
            iOException2.initCause(e);
            throw iOException2;
        }
    }

    private static void configureSSLSocket(Socket socket, String str, Properties properties, String str2, SocketFactory socketFactory) throws IOException {
        if (socket instanceof SSLSocket) {
            SSLSocket sSLSocket = (SSLSocket) socket;
            String property = properties.getProperty(str2 + ".ssl.protocols", null);
            if (property != null) {
                sSLSocket.setEnabledProtocols(stringArray(property));
            } else {
                String[] enabledProtocols = sSLSocket.getEnabledProtocols();
                if (logger.isLoggable(Level.FINER)) {
                    logger.finer("SSL enabled protocols before " + Arrays.asList(enabledProtocols));
                }
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < enabledProtocols.length; i++) {
                    if (enabledProtocols[i] != null && !enabledProtocols[i].startsWith("SSL")) {
                        arrayList.add(enabledProtocols[i]);
                    }
                }
                sSLSocket.setEnabledProtocols((String[]) arrayList.toArray(new String[arrayList.size()]));
            }
            String property2 = properties.getProperty(str2 + ".ssl.ciphersuites", null);
            if (property2 != null) {
                sSLSocket.setEnabledCipherSuites(stringArray(property2));
            }
            if (logger.isLoggable(Level.FINER)) {
                logger.finer("SSL enabled protocols after " + Arrays.asList(sSLSocket.getEnabledProtocols()));
                logger.finer("SSL enabled ciphers after " + Arrays.asList(sSLSocket.getEnabledCipherSuites()));
            }
            sSLSocket.startHandshake();
            if (PropUtil.getBooleanProperty(properties, str2 + ".ssl.checkserveridentity", false)) {
                checkServerIdentity(str, sSLSocket);
            }
            if (!(socketFactory instanceof MailSSLSocketFactory) || ((MailSSLSocketFactory) socketFactory).isServerTrusted(str, sSLSocket)) {
                return;
            }
            throw cleanupAndThrow(sSLSocket, new IOException("Server is not trusted: " + str));
        }
    }

    private static IOException cleanupAndThrow(Socket socket, IOException iOException) {
        try {
            socket.close();
        } catch (Throwable th) {
            if (isRecoverable(th)) {
                iOException.addSuppressed(th);
            } else {
                th.addSuppressed(iOException);
                if (th instanceof Error) {
                    throw ((Error) th);
                }
                if (th instanceof RuntimeException) {
                    throw ((RuntimeException) th);
                }
                throw new RuntimeException("unexpected exception", th);
            }
        }
        return iOException;
    }

    private static boolean isRecoverable(Throwable th) {
        return (th instanceof Exception) || (th instanceof LinkageError);
    }

    private static void checkServerIdentity(String str, SSLSocket sSLSocket) throws IOException {
        try {
            Certificate[] peerCertificates = sSLSocket.getSession().getPeerCertificates();
            if (peerCertificates != null && peerCertificates.length > 0 && (peerCertificates[0] instanceof X509Certificate)) {
                if (matchCert(str, (X509Certificate) peerCertificates[0])) {
                    return;
                }
            }
            sSLSocket.close();
            throw new IOException("Can't verify identity of server: " + str);
        } catch (SSLPeerUnverifiedException e) {
            sSLSocket.close();
            IOException iOException = new IOException("Can't verify identity of server: " + str);
            iOException.initCause(e);
            throw iOException;
        }
    }

    private static boolean matchCert(String str, X509Certificate x509Certificate) throws IllegalAccessException, CertificateParsingException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("matchCert server " + str + ", cert " + x509Certificate);
        }
        try {
            Class<?> cls = Class.forName("sun.security.util.HostnameChecker");
            Object objInvoke = cls.getMethod("getInstance", Byte.TYPE).invoke(new Object(), (byte) 2);
            if (logger.isLoggable(Level.FINER)) {
                logger.finer("using sun.security.util.HostnameChecker");
            }
            try {
                cls.getMethod("match", String.class, X509Certificate.class).invoke(objInvoke, str, x509Certificate);
                return true;
            } catch (InvocationTargetException e) {
                logger.log(Level.FINER, "HostnameChecker FAIL", (Throwable) e);
                return false;
            }
        } catch (Exception e2) {
            logger.log(Level.FINER, "NO sun.security.util.HostnameChecker", (Throwable) e2);
            try {
                Collection<List<?>> subjectAlternativeNames = x509Certificate.getSubjectAlternativeNames();
                if (subjectAlternativeNames != null) {
                    boolean z = false;
                    for (List<?> list : subjectAlternativeNames) {
                        if (((Integer) list.get(0)).intValue() == 2) {
                            String str2 = (String) list.get(1);
                            if (logger.isLoggable(Level.FINER)) {
                                logger.finer("found name: " + str2);
                            }
                            if (matchServer(str, str2)) {
                                return true;
                            }
                            z = true;
                        }
                    }
                    if (z) {
                        return false;
                    }
                }
            } catch (CertificateParsingException unused) {
            }
            Matcher matcher = Pattern.compile("CN=([^,]*)").matcher(x509Certificate.getSubjectX500Principal().getName());
            return matcher.find() && matchServer(str, matcher.group(1).trim());
        }
    }

    private static boolean matchServer(String str, String str2) {
        int length;
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("match server " + str + " with " + str2);
        }
        if (str2.startsWith("*.")) {
            String strSubstring = str2.substring(2);
            return strSubstring.length() != 0 && (length = str.length() - strSubstring.length()) >= 1 && str.charAt(length + (-1)) == '.' && str.regionMatches(true, length, strSubstring, 0, strSubstring.length());
        }
        return str.equalsIgnoreCase(str2);
    }

    private static void proxyConnect(Socket socket, String str, int i, String str2, String str3, String str4, int i2, int i3) throws IOException {
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("connecting through proxy " + str + ":" + i + " to " + str4 + ":" + i2);
        }
        if (i3 >= 0) {
            socket.connect(new InetSocketAddress(str, i), i3);
        } else {
            socket.connect(new InetSocketAddress(str, i));
        }
        PrintStream printStream = new PrintStream(socket.getOutputStream(), false, StandardCharsets.UTF_8.name());
        StringBuilder sb = new StringBuilder();
        sb.append("CONNECT ");
        sb.append(str4);
        sb.append(":");
        sb.append(i2);
        sb.append(" HTTP/1.1\r\n");
        sb.append("Host: ");
        sb.append(str4);
        sb.append(":");
        sb.append(i2);
        sb.append("\r\n");
        if (str2 != null && str3 != null) {
            String str5 = new String(BASE64EncoderStream.encode((str2 + ':' + str3).getBytes(StandardCharsets.UTF_8)), StandardCharsets.US_ASCII);
            sb.append("Proxy-Authorization: Basic ");
            sb.append(str5);
            sb.append("\r\n");
        }
        sb.append("Proxy-Connection: keep-alive\r\n\r\n");
        printStream.print(sb.toString());
        printStream.flush();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        boolean z = true;
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null || line.length() == 0) {
                return;
            }
            logger.finest(line);
            if (z) {
                StringTokenizer stringTokenizer = new StringTokenizer(line);
                stringTokenizer.nextToken();
                if (!stringTokenizer.nextToken().equals("200")) {
                    try {
                        socket.close();
                    } catch (IOException unused) {
                    }
                    ConnectException connectException = new ConnectException("connection through proxy " + str + ":" + i + " to " + str4 + ":" + i2 + " failed: " + line);
                    logger.log(Level.FINE, "connect failed", (Throwable) connectException);
                    throw connectException;
                }
                z = false;
            }
        }
    }

    private static String[] stringArray(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str);
        ArrayList arrayList = new ArrayList();
        while (stringTokenizer.hasMoreTokens()) {
            arrayList.add(stringTokenizer.nextToken());
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    private static ClassLoader getContextClassLoader() {
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() { // from class: com.sun.mail.util.SocketFetcher.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.security.PrivilegedAction
            public ClassLoader run() {
                try {
                    return Thread.currentThread().getContextClassLoader();
                } catch (SecurityException unused) {
                    return null;
                }
            }
        });
    }
}
