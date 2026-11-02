package javax.mail;

import java.net.UnknownHostException;
import java.util.EventListener;
import java.util.Vector;
import java.util.concurrent.Executor;
import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;
import javax.mail.event.MailEvent;

/* loaded from: classes3.dex */
public abstract class Service implements AutoCloseable {
    private boolean connected = false;
    private final Vector<ConnectionListener> connectionListeners = new Vector<>();
    protected boolean debug;
    private final EventQueue q;
    protected Session session;
    protected volatile URLName url;

    protected boolean protocolConnect(String str, int i, String str2, String str3) throws MessagingException {
        return false;
    }

    protected Service(Session session, URLName uRLName) {
        String property;
        String str;
        String file;
        String password;
        int i;
        String property2;
        String property3 = null;
        this.url = null;
        this.debug = false;
        this.session = session;
        this.debug = session.getDebug();
        this.url = uRLName;
        if (this.url != null) {
            String protocol = this.url.getProtocol();
            String host = this.url.getHost();
            int port = this.url.getPort();
            property = this.url.getUsername();
            i = port;
            password = this.url.getPassword();
            file = this.url.getFile();
            str = protocol;
            property3 = host;
        } else {
            property = null;
            str = null;
            file = null;
            password = null;
            i = -1;
        }
        if (str != null) {
            if (property3 == null) {
                property3 = session.getProperty("mail." + str + ".host");
            }
            if (property == null) {
                property = session.getProperty("mail." + str + ".user");
            }
        }
        String property4 = property3 == null ? session.getProperty("mail.host") : property3;
        property = property == null ? session.getProperty("mail.user") : property;
        if (property == null) {
            try {
                property2 = System.getProperty("user.name");
            } catch (SecurityException unused) {
            }
        } else {
            property2 = property;
        }
        this.url = new URLName(str, property4, i, file, property2, password);
        String property5 = session.getProperties().getProperty("mail.event.scope", "folder");
        Executor executor = (Executor) session.getProperties().get("mail.event.executor");
        if (property5.equalsIgnoreCase("application")) {
            this.q = EventQueue.getApplicationEventQueue(executor);
        } else if (property5.equalsIgnoreCase("session")) {
            this.q = session.getEventQueue();
        } else {
            this.q = new EventQueue(executor);
        }
    }

    public void connect() throws MessagingException, UnknownHostException {
        connect(null, null, null);
    }

    public void connect(String str, String str2, String str3) throws MessagingException, UnknownHostException {
        connect(str, -1, str2, str3);
    }

    public void connect(String str, String str2) throws MessagingException, UnknownHostException {
        connect(null, str, str2);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:6|(5:8|(1:10)(1:11)|12|(1:14)(1:15)|(3:17|(1:19)(1:24)|25)(2:(0)(1:23)|25))(1:26)|(2:(1:29)|(1:31))|(1:33)|(1:35)|(2:88|37)|40|(1:53)(2:44|(1:(1:47)(2:48|(1:50)))(6:52|90|55|56|(5:92|60|61|63|(1:65))|(1:(1:(1:(2:70|71)(2:72|73))(2:74|75))(1:76))(4:77|(1:79)|80|81)))|54|90|55|56|(0)|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0115, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0116, code lost:
    
        r16 = r0;
        r0 = false;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0053 A[PHI: r0
      0x0053: PHI (r0v26 java.lang.String) = (r0v0 java.lang.String), (r0v0 java.lang.String), (r0v29 java.lang.String) binds: [B:20:0x003e, B:22:0x004a, B:18:0x0035] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x015b A[Catch: all -> 0x018a, TryCatch #3 {, blocks: (B:4:0x0005, B:6:0x000b, B:8:0x000f, B:10:0x0017, B:14:0x0025, B:17:0x002f, B:19:0x0037, B:25:0x0055, B:29:0x006d, B:31:0x008b, B:33:0x00a9, B:35:0x00b3, B:37:0x00bd, B:42:0x00c8, B:44:0x00cc, B:47:0x00ee, B:55:0x010e, B:60:0x011b, B:63:0x0122, B:65:0x012e, B:70:0x0142, B:71:0x0149, B:72:0x014a, B:73:0x0151, B:74:0x0152, B:75:0x0159, B:76:0x015a, B:77:0x015b, B:79:0x016c, B:80:0x017a, B:48:0x00f7, B:50:0x0101, B:21:0x0040, B:23:0x004c, B:83:0x0182, B:84:0x0189), top: B:94:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x011b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r15v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r15v3 */
    /* JADX WARN: Type inference failed for: r15v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public synchronized void connect(java.lang.String r18, int r19, java.lang.String r20, java.lang.String r21) throws javax.mail.MessagingException, java.net.UnknownHostException {
        /*
            Method dump skipped, instructions count: 397
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: javax.mail.Service.connect(java.lang.String, int, java.lang.String, java.lang.String):void");
    }

    public synchronized boolean isConnected() {
        return this.connected;
    }

    protected synchronized void setConnected(boolean z) {
        this.connected = z;
    }

    @Override // java.lang.AutoCloseable
    public synchronized void close() throws MessagingException {
        setConnected(false);
        notifyConnectionListeners(3);
    }

    public URLName getURLName() {
        URLName uRLName = this.url;
        return (uRLName == null || (uRLName.getPassword() == null && uRLName.getFile() == null)) ? uRLName : new URLName(uRLName.getProtocol(), uRLName.getHost(), uRLName.getPort(), null, uRLName.getUsername(), null);
    }

    protected void setURLName(URLName uRLName) {
        this.url = uRLName;
    }

    public void addConnectionListener(ConnectionListener connectionListener) {
        this.connectionListeners.addElement(connectionListener);
    }

    public void removeConnectionListener(ConnectionListener connectionListener) {
        this.connectionListeners.removeElement(connectionListener);
    }

    protected void notifyConnectionListeners(int i) {
        if (this.connectionListeners.size() > 0) {
            queueEvent(new ConnectionEvent(this, i), this.connectionListeners);
        }
        if (i == 3) {
            this.q.terminateQueue();
        }
    }

    public String toString() {
        URLName uRLName = getURLName();
        if (uRLName != null) {
            return uRLName.toString();
        }
        return super.toString();
    }

    protected void queueEvent(MailEvent mailEvent, Vector<? extends EventListener> vector) {
        this.q.enqueue(mailEvent, (Vector) vector.clone());
    }

    protected void finalize() throws Throwable {
        try {
            this.q.terminateQueue();
        } finally {
            super.finalize();
        }
    }

    Session getSession() {
        return this.session;
    }

    EventQueue getEventQueue() {
        return this.q;
    }
}
