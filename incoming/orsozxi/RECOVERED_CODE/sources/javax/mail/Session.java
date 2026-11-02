package javax.mail;

import androidx.core.app.NotificationCompat;
import com.huawei.hms.framework.common.ContainerUtils;
import com.sun.mail.util.LineInputStream;
import com.sun.mail.util.MailLogger;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.StringTokenizer;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import javax.mail.Provider;

/* loaded from: classes3.dex */
public final class Session {
    private static final String confDir;
    private static Session defaultSession;
    private final Authenticator authenticator;
    private boolean debug;
    private boolean loadedProviders;
    private MailLogger logger;
    private PrintStream out;
    private final Properties props;
    private List<Provider> providers;
    private final EventQueue q;
    private final Hashtable<URLName, PasswordAuthentication> authTable = new Hashtable<>();
    private final Map<String, Provider> providersByProtocol = new HashMap();
    private final Map<String, Provider> providersByClassName = new HashMap();
    private final Properties addressMap = new Properties();

    static {
        String str;
        try {
            str = (String) AccessController.doPrivileged(new PrivilegedAction<String>() { // from class: javax.mail.Session.1
                @Override // java.security.PrivilegedAction
                public String run() {
                    String property = System.getProperty("java.home");
                    String str2 = property + File.separator + "conf";
                    if (new File(str2).exists()) {
                        return str2 + File.separator;
                    }
                    return property + File.separator + "lib" + File.separator;
                }
            });
        } catch (Exception unused) {
            str = null;
        }
        confDir = str;
    }

    private Session(Properties properties, Authenticator authenticator) throws Throwable {
        Class<?> cls;
        this.debug = false;
        this.props = properties;
        this.authenticator = authenticator;
        if (Boolean.valueOf(properties.getProperty("mail.debug")).booleanValue()) {
            this.debug = true;
        }
        initLogger();
        this.logger.log(Level.CONFIG, "JavaMail version {0}", Version.version);
        if (authenticator != null) {
            cls = authenticator.getClass();
        } else {
            cls = getClass();
        }
        loadAddressMap(cls);
        this.q = new EventQueue((Executor) properties.get("mail.event.executor"));
    }

    private final synchronized void initLogger() {
        this.logger = new MailLogger(getClass(), "DEBUG", this.debug, getDebugOut());
    }

    public static Session getInstance(Properties properties, Authenticator authenticator) {
        return new Session(properties, authenticator);
    }

    public static Session getInstance(Properties properties) {
        return new Session(properties, null);
    }

    public static synchronized Session getDefaultInstance(Properties properties, Authenticator authenticator) {
        if (defaultSession == null) {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkSetFactory();
            }
            defaultSession = new Session(properties, authenticator);
        } else if (defaultSession.authenticator != authenticator && (defaultSession.authenticator == null || authenticator == null || defaultSession.authenticator.getClass().getClassLoader() != authenticator.getClass().getClassLoader())) {
            throw new SecurityException("Access to default session denied");
        }
        return defaultSession;
    }

    public static Session getDefaultInstance(Properties properties) {
        return getDefaultInstance(properties, null);
    }

    public synchronized void setDebug(boolean z) {
        this.debug = z;
        initLogger();
        this.logger.log(Level.CONFIG, "setDebug: JavaMail version {0}", Version.version);
    }

    public synchronized boolean getDebug() {
        return this.debug;
    }

    public synchronized void setDebugOut(PrintStream printStream) {
        this.out = printStream;
        initLogger();
    }

    public synchronized PrintStream getDebugOut() {
        if (this.out == null) {
            return System.out;
        }
        return this.out;
    }

    public synchronized Provider[] getProviders() {
        Provider[] providerArr;
        ArrayList arrayList = new ArrayList();
        boolean z = true;
        Iterator it2 = ServiceLoader.load(Provider.class).iterator();
        while (it2.hasNext()) {
            arrayList.add((Provider) it2.next());
            z = false;
        }
        if (!this.loadedProviders) {
            loadProviders(z);
        }
        if (this.providers != null) {
            arrayList.addAll(this.providers);
        }
        providerArr = new Provider[arrayList.size()];
        arrayList.toArray(providerArr);
        return providerArr;
    }

    public synchronized Provider getProvider(String str) throws NoSuchProviderException {
        Provider providerByProtocol;
        if (str != null) {
            if (str.length() > 0) {
                providerByProtocol = null;
                String property = this.props.getProperty("mail." + str + ".class");
                if (property != null) {
                    if (this.logger.isLoggable(Level.FINE)) {
                        this.logger.fine("mail." + str + ".class property exists and points to " + property);
                    }
                    providerByProtocol = getProviderByClassName(property);
                }
                if (providerByProtocol == null) {
                    providerByProtocol = getProviderByProtocol(str);
                }
                if (providerByProtocol == null) {
                    throw new NoSuchProviderException("No provider for " + str);
                }
                if (this.logger.isLoggable(Level.FINE)) {
                    this.logger.fine("getProvider() returning " + providerByProtocol.toString());
                }
            }
        }
        throw new NoSuchProviderException("Invalid protocol: null");
        return providerByProtocol;
    }

    public synchronized void setProvider(Provider provider) throws NoSuchProviderException {
        if (provider == null) {
            throw new NoSuchProviderException("Can't set null provider");
        }
        this.providersByProtocol.put(provider.getProtocol(), provider);
        this.providersByClassName.put(provider.getClassName(), provider);
        this.props.put("mail." + provider.getProtocol() + ".class", provider.getClassName());
    }

    public Store getStore() throws NoSuchProviderException {
        return getStore(getProperty("mail.store.protocol"));
    }

    public Store getStore(String str) throws NoSuchProviderException {
        return getStore(new URLName(str, null, -1, null, null, null));
    }

    public Store getStore(URLName uRLName) throws NoSuchProviderException {
        return getStore(getProvider(uRLName.getProtocol()), uRLName);
    }

    public Store getStore(Provider provider) throws NoSuchProviderException {
        return getStore(provider, null);
    }

    private Store getStore(Provider provider, URLName uRLName) throws NoSuchProviderException {
        if (provider == null || provider.getType() != Provider.Type.STORE) {
            throw new NoSuchProviderException("invalid provider");
        }
        return (Store) getService(provider, uRLName, Store.class);
    }

    public Folder getFolder(URLName uRLName) throws MessagingException {
        Store store = getStore(uRLName);
        store.connect();
        return store.getFolder(uRLName);
    }

    public Transport getTransport() throws NoSuchProviderException {
        String property = getProperty("mail.transport.protocol");
        if (property != null) {
            return getTransport(property);
        }
        String str = (String) this.addressMap.get("rfc822");
        if (str != null) {
            return getTransport(str);
        }
        return getTransport("smtp");
    }

    public Transport getTransport(String str) throws NoSuchProviderException {
        return getTransport(new URLName(str, null, -1, null, null, null));
    }

    public Transport getTransport(URLName uRLName) throws NoSuchProviderException {
        return getTransport(getProvider(uRLName.getProtocol()), uRLName);
    }

    public Transport getTransport(Provider provider) throws NoSuchProviderException {
        return getTransport(provider, null);
    }

    public Transport getTransport(Address address) throws NoSuchProviderException {
        String property = getProperty("mail.transport.protocol." + address.getType());
        if (property != null) {
            return getTransport(property);
        }
        String str = (String) this.addressMap.get(address.getType());
        if (str != null) {
            return getTransport(str);
        }
        throw new NoSuchProviderException("No provider for Address type: " + address.getType());
    }

    private Transport getTransport(Provider provider, URLName uRLName) throws NoSuchProviderException {
        if (provider == null || provider.getType() != Provider.Type.TRANSPORT) {
            throw new NoSuchProviderException("invalid provider");
        }
        return (Transport) getService(provider, uRLName, Transport.class);
    }

    private <T extends Service> T getService(Provider provider, URLName uRLName, Class<T> cls) throws NoSuchProviderException, ClassNotFoundException {
        ClassLoader classLoader;
        if (provider == null) {
            throw new NoSuchProviderException("null");
        }
        if (uRLName == null) {
            uRLName = new URLName(provider.getProtocol(), null, -1, null, null, null);
        }
        Authenticator authenticator = this.authenticator;
        if (authenticator != null) {
            classLoader = authenticator.getClass().getClassLoader();
        } else {
            classLoader = getClass().getClassLoader();
        }
        Class<?> cls2 = null;
        try {
            try {
                ClassLoader contextClassLoader = getContextClassLoader();
                if (contextClassLoader != null) {
                    try {
                        cls2 = Class.forName(provider.getClassName(), false, contextClassLoader);
                    } catch (ClassNotFoundException unused) {
                    }
                }
                if (cls2 == null || !cls.isAssignableFrom(cls2)) {
                    cls2 = Class.forName(provider.getClassName(), false, classLoader);
                }
            } catch (Exception e) {
                this.logger.log(Level.FINE, "Exception loading provider", (Throwable) e);
                throw new NoSuchProviderException(provider.getProtocol());
            }
        } catch (Exception unused2) {
            cls2 = Class.forName(provider.getClassName());
            if (!cls.isAssignableFrom(cls2)) {
                throw new ClassCastException(cls.getName() + " " + cls2.getName());
            }
        }
        if (!cls.isAssignableFrom(cls2)) {
            throw new ClassCastException(cls.getName() + " " + cls2.getName());
        }
        try {
            return cls.cast(cls2.getConstructor(Session.class, URLName.class).newInstance(this, uRLName));
        } catch (Exception e2) {
            this.logger.log(Level.FINE, "Exception loading provider", (Throwable) e2);
            throw new NoSuchProviderException(provider.getProtocol());
        }
    }

    public void setPasswordAuthentication(URLName uRLName, PasswordAuthentication passwordAuthentication) {
        if (passwordAuthentication == null) {
            this.authTable.remove(uRLName);
        } else {
            this.authTable.put(uRLName, passwordAuthentication);
        }
    }

    public PasswordAuthentication getPasswordAuthentication(URLName uRLName) {
        return this.authTable.get(uRLName);
    }

    public PasswordAuthentication requestPasswordAuthentication(InetAddress inetAddress, int i, String str, String str2, String str3) {
        Authenticator authenticator = this.authenticator;
        if (authenticator != null) {
            return authenticator.requestPasswordAuthentication(inetAddress, i, str, str2, str3);
        }
        return null;
    }

    public Properties getProperties() {
        return this.props;
    }

    public String getProperty(String str) {
        return this.props.getProperty(str);
    }

    private Provider getProviderByClassName(String str) throws Throwable {
        Provider provider = this.providersByClassName.get(str);
        if (provider != null) {
            return provider;
        }
        Iterator it2 = ServiceLoader.load(Provider.class).iterator();
        while (it2.hasNext()) {
            Provider provider2 = (Provider) it2.next();
            if (str.equals(provider2.getClassName())) {
                return provider2;
            }
        }
        if (this.loadedProviders) {
            return provider;
        }
        loadProviders(true);
        return this.providersByClassName.get(str);
    }

    private Provider getProviderByProtocol(String str) throws Throwable {
        Provider provider = this.providersByProtocol.get(str);
        if (provider != null) {
            return provider;
        }
        Iterator it2 = ServiceLoader.load(Provider.class).iterator();
        while (it2.hasNext()) {
            Provider provider2 = (Provider) it2.next();
            if (str.equals(provider2.getProtocol())) {
                return provider2;
            }
        }
        if (this.loadedProviders) {
            return provider;
        }
        loadProviders(true);
        return this.providersByProtocol.get(str);
    }

    private void loadProviders(boolean z) throws Throwable {
        Class<?> cls;
        StreamLoader streamLoader = new StreamLoader() { // from class: javax.mail.Session.2
            @Override // javax.mail.StreamLoader
            public void load(InputStream inputStream) throws IOException {
                Session.this.loadProvidersFromStream(inputStream);
            }
        };
        try {
            if (confDir != null) {
                loadFile(confDir + "javamail.providers", streamLoader);
            }
        } catch (SecurityException unused) {
        }
        Authenticator authenticator = this.authenticator;
        if (authenticator != null) {
            cls = authenticator.getClass();
        } else {
            cls = getClass();
        }
        loadAllResources("META-INF/javamail.providers", cls, streamLoader);
        loadResource("/META-INF/javamail.default.providers", cls, streamLoader, false);
        List<Provider> list = this.providers;
        if ((list == null || list.size() == 0) && z) {
            this.logger.config("failed to load any providers, using defaults");
            addProvider(new Provider(Provider.Type.STORE, "imap", "com.sun.mail.imap.IMAPStore", "Oracle", Version.version));
            addProvider(new Provider(Provider.Type.STORE, "imaps", "com.sun.mail.imap.IMAPSSLStore", "Oracle", Version.version));
            addProvider(new Provider(Provider.Type.STORE, "pop3", "com.sun.mail.pop3.POP3Store", "Oracle", Version.version));
            addProvider(new Provider(Provider.Type.STORE, "pop3s", "com.sun.mail.pop3.POP3SSLStore", "Oracle", Version.version));
            addProvider(new Provider(Provider.Type.TRANSPORT, "smtp", "com.sun.mail.smtp.SMTPTransport", "Oracle", Version.version));
            addProvider(new Provider(Provider.Type.TRANSPORT, "smtps", "com.sun.mail.smtp.SMTPSSLTransport", "Oracle", Version.version));
        }
        if (this.logger.isLoggable(Level.CONFIG)) {
            this.logger.config("Tables of loaded providers from javamail.providers");
            this.logger.config("Providers Listed By Class Name: " + this.providersByClassName.toString());
            this.logger.config("Providers Listed By Protocol: " + this.providersByProtocol.toString());
        }
        this.loadedProviders = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadProvidersFromStream(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return;
        }
        LineInputStream lineInputStream = new LineInputStream(inputStream);
        while (true) {
            String line = lineInputStream.readLine();
            if (line == null) {
                return;
            }
            if (!line.startsWith("#") && line.trim().length() != 0) {
                StringTokenizer stringTokenizer = new StringTokenizer(line, ";");
                Provider.Type type = null;
                String strSubstring = null;
                String strSubstring2 = null;
                String strSubstring3 = null;
                String strSubstring4 = null;
                while (stringTokenizer.hasMoreTokens()) {
                    String strTrim = stringTokenizer.nextToken().trim();
                    int iIndexOf = strTrim.indexOf(ContainerUtils.KEY_VALUE_DELIMITER);
                    if (strTrim.startsWith("protocol=")) {
                        strSubstring = strTrim.substring(iIndexOf + 1);
                    } else if (strTrim.startsWith("type=")) {
                        String strSubstring5 = strTrim.substring(iIndexOf + 1);
                        if (strSubstring5.equalsIgnoreCase("store")) {
                            type = Provider.Type.STORE;
                        } else if (strSubstring5.equalsIgnoreCase(NotificationCompat.CATEGORY_TRANSPORT)) {
                            type = Provider.Type.TRANSPORT;
                        }
                    } else if (strTrim.startsWith("class=")) {
                        strSubstring2 = strTrim.substring(iIndexOf + 1);
                    } else if (strTrim.startsWith("vendor=")) {
                        strSubstring3 = strTrim.substring(iIndexOf + 1);
                    } else if (strTrim.startsWith("version=")) {
                        strSubstring4 = strTrim.substring(iIndexOf + 1);
                    }
                }
                if (type == null || strSubstring == null || strSubstring2 == null || strSubstring.length() <= 0 || strSubstring2.length() <= 0) {
                    this.logger.log(Level.CONFIG, "Bad provider entry: {0}", line);
                } else {
                    addProvider(new Provider(type, strSubstring, strSubstring2, strSubstring3, strSubstring4));
                }
            }
        }
    }

    public synchronized void addProvider(Provider provider) {
        if (this.providers == null) {
            this.providers = new ArrayList();
        }
        this.providers.add(provider);
        this.providersByClassName.put(provider.getClassName(), provider);
        if (!this.providersByProtocol.containsKey(provider.getProtocol())) {
            this.providersByProtocol.put(provider.getProtocol(), provider);
        }
    }

    private void loadAddressMap(Class<?> cls) throws Throwable {
        StreamLoader streamLoader = new StreamLoader() { // from class: javax.mail.Session.3
            @Override // javax.mail.StreamLoader
            public void load(InputStream inputStream) throws IOException {
                Session.this.addressMap.load(inputStream);
            }
        };
        loadResource("/META-INF/javamail.default.address.map", cls, streamLoader, true);
        loadAllResources("META-INF/javamail.address.map", cls, streamLoader);
        try {
            if (confDir != null) {
                loadFile(confDir + "javamail.address.map", streamLoader);
            }
        } catch (SecurityException unused) {
        }
        if (this.addressMap.isEmpty()) {
            this.logger.config("failed to load address map, using defaults");
            this.addressMap.put("rfc822", "smtp");
        }
    }

    public synchronized void setProtocolForAddress(String str, String str2) {
        if (str2 == null) {
            this.addressMap.remove(str);
        } else {
            this.addressMap.put(str, str2);
        }
    }

    private void loadFile(String str, StreamLoader streamLoader) throws Throwable {
        BufferedInputStream bufferedInputStream;
        BufferedInputStream bufferedInputStream2 = null;
        try {
            try {
                try {
                    bufferedInputStream = new BufferedInputStream(new FileInputStream(str));
                } catch (Throwable th) {
                    th = th;
                }
            } catch (FileNotFoundException unused) {
            } catch (IOException e) {
                e = e;
            } catch (SecurityException e2) {
                e = e2;
            }
            try {
                streamLoader.load(bufferedInputStream);
                this.logger.log(Level.CONFIG, "successfully loaded file: {0}", str);
                bufferedInputStream.close();
            } catch (FileNotFoundException unused2) {
                bufferedInputStream2 = bufferedInputStream;
                if (bufferedInputStream2 == null) {
                    return;
                }
                bufferedInputStream2.close();
            } catch (IOException e3) {
                e = e3;
                bufferedInputStream2 = bufferedInputStream;
                if (this.logger.isLoggable(Level.CONFIG)) {
                    this.logger.log(Level.CONFIG, "not loading file: " + str, (Throwable) e);
                }
                if (bufferedInputStream2 == null) {
                    return;
                }
                bufferedInputStream2.close();
            } catch (SecurityException e4) {
                e = e4;
                bufferedInputStream2 = bufferedInputStream;
                if (this.logger.isLoggable(Level.CONFIG)) {
                    this.logger.log(Level.CONFIG, "not loading file: " + str, (Throwable) e);
                }
                if (bufferedInputStream2 == null) {
                    return;
                }
                bufferedInputStream2.close();
            } catch (Throwable th2) {
                th = th2;
                bufferedInputStream2 = bufferedInputStream;
                if (bufferedInputStream2 != null) {
                    try {
                        bufferedInputStream2.close();
                    } catch (IOException unused3) {
                    }
                }
                throw th;
            }
        } catch (IOException unused4) {
        }
    }

    private void loadResource(String str, Class<?> cls, StreamLoader streamLoader, boolean z) throws IOException {
        InputStream resourceAsStream = null;
        try {
            try {
                resourceAsStream = getResourceAsStream(cls, str);
                if (resourceAsStream != null) {
                    streamLoader.load(resourceAsStream);
                    this.logger.log(Level.CONFIG, "successfully loaded resource: {0}", str);
                } else if (z) {
                    this.logger.log(Level.WARNING, "expected resource not found: {0}", str);
                }
                if (resourceAsStream == null) {
                    return;
                }
            } catch (Throwable th) {
                if (0 != 0) {
                    try {
                        resourceAsStream.close();
                    } catch (IOException unused) {
                    }
                }
                throw th;
            }
        } catch (IOException e) {
            this.logger.log(Level.CONFIG, "Exception loading resource", (Throwable) e);
            if (0 == 0) {
                return;
            }
        } catch (SecurityException e2) {
            this.logger.log(Level.CONFIG, "Exception loading resource", (Throwable) e2);
            if (0 == 0) {
                return;
            }
        }
        try {
            resourceAsStream.close();
        } catch (IOException unused2) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0054 A[EXC_TOP_SPLITTER, PHI: r4 r6
      0x0054: PHI (r4v17 boolean) = (r4v15 boolean), (r4v18 boolean) binds: [B:52:0x0084, B:28:0x0052] A[DONT_GENERATE, DONT_INLINE]
      0x0054: PHI (r6v10 java.io.InputStream) = (r6v9 java.io.InputStream), (r6v11 java.io.InputStream) binds: [B:52:0x0084, B:28:0x0052] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0080 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:88:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void loadAllResources(java.lang.String r11, java.lang.Class<?> r12, javax.mail.StreamLoader r13) throws java.lang.Throwable {
        /*
            r10 = this;
            java.lang.String r0 = "Exception loading resource"
            r1 = 0
            java.lang.ClassLoader r2 = getContextClassLoader()     // Catch: java.lang.Exception -> L8e
            if (r2 != 0) goto Ld
            java.lang.ClassLoader r2 = r12.getClassLoader()     // Catch: java.lang.Exception -> L8e
        Ld:
            if (r2 == 0) goto L14
            java.net.URL[] r2 = getResources(r2, r11)     // Catch: java.lang.Exception -> L8e
            goto L18
        L14:
            java.net.URL[] r2 = getSystemResources(r11)     // Catch: java.lang.Exception -> L8e
        L18:
            if (r2 == 0) goto L8c
            r3 = 0
            r4 = 0
        L1c:
            int r5 = r2.length     // Catch: java.lang.Exception -> L8a
            if (r3 >= r5) goto L97
            r5 = r2[r3]     // Catch: java.lang.Exception -> L8a
            r6 = 0
            com.sun.mail.util.MailLogger r7 = r10.logger     // Catch: java.lang.Exception -> L8a
            java.util.logging.Level r8 = java.util.logging.Level.CONFIG     // Catch: java.lang.Exception -> L8a
            java.lang.String r9 = "URL {0}"
            r7.log(r8, r9, r5)     // Catch: java.lang.Exception -> L8a
            r7 = 1
            java.io.InputStream r6 = openStream(r5)     // Catch: java.lang.Throwable -> L58 java.lang.SecurityException -> L5a java.io.IOException -> L67 java.io.FileNotFoundException -> L84
            if (r6 == 0) goto L49
            r13.load(r6)     // Catch: java.lang.Throwable -> L58 java.lang.SecurityException -> L5a java.io.IOException -> L67 java.io.FileNotFoundException -> L84
            com.sun.mail.util.MailLogger r4 = r10.logger     // Catch: java.lang.Throwable -> L40 java.lang.SecurityException -> L43 java.io.IOException -> L45 java.io.FileNotFoundException -> L47
            java.util.logging.Level r8 = java.util.logging.Level.CONFIG     // Catch: java.lang.Throwable -> L40 java.lang.SecurityException -> L43 java.io.IOException -> L45 java.io.FileNotFoundException -> L47
            java.lang.String r9 = "successfully loaded resource: {0}"
            r4.log(r8, r9, r5)     // Catch: java.lang.Throwable -> L40 java.lang.SecurityException -> L43 java.io.IOException -> L45 java.io.FileNotFoundException -> L47
            r4 = 1
            goto L52
        L40:
            r2 = move-exception
            r4 = 1
            goto L7e
        L43:
            r4 = move-exception
            goto L5d
        L45:
            r4 = move-exception
            goto L6a
        L47:
            r4 = 1
            goto L84
        L49:
            com.sun.mail.util.MailLogger r7 = r10.logger     // Catch: java.lang.Throwable -> L58 java.lang.SecurityException -> L5a java.io.IOException -> L67 java.io.FileNotFoundException -> L84
            java.util.logging.Level r8 = java.util.logging.Level.CONFIG     // Catch: java.lang.Throwable -> L58 java.lang.SecurityException -> L5a java.io.IOException -> L67 java.io.FileNotFoundException -> L84
            java.lang.String r9 = "not loading resource: {0}"
            r7.log(r8, r9, r5)     // Catch: java.lang.Throwable -> L58 java.lang.SecurityException -> L5a java.io.IOException -> L67 java.io.FileNotFoundException -> L84
        L52:
            if (r6 == 0) goto L87
        L54:
            r6.close()     // Catch: java.io.IOException -> L87 java.lang.Exception -> L8a
            goto L87
        L58:
            r2 = move-exception
            goto L7e
        L5a:
            r5 = move-exception
            r7 = r4
            r4 = r5
        L5d:
            com.sun.mail.util.MailLogger r5 = r10.logger     // Catch: java.lang.Throwable -> L7c
            java.util.logging.Level r8 = java.util.logging.Level.CONFIG     // Catch: java.lang.Throwable -> L7c
            r5.log(r8, r0, r4)     // Catch: java.lang.Throwable -> L7c
            if (r6 == 0) goto L7a
            goto L73
        L67:
            r5 = move-exception
            r7 = r4
            r4 = r5
        L6a:
            com.sun.mail.util.MailLogger r5 = r10.logger     // Catch: java.lang.Throwable -> L7c
            java.util.logging.Level r8 = java.util.logging.Level.CONFIG     // Catch: java.lang.Throwable -> L7c
            r5.log(r8, r0, r4)     // Catch: java.lang.Throwable -> L7c
            if (r6 == 0) goto L7a
        L73:
            r6.close()     // Catch: java.lang.Exception -> L77 java.io.IOException -> L7a
            goto L7a
        L77:
            r2 = move-exception
            r4 = r7
            goto L90
        L7a:
            r4 = r7
            goto L87
        L7c:
            r2 = move-exception
            r4 = r7
        L7e:
            if (r6 == 0) goto L83
            r6.close()     // Catch: java.io.IOException -> L83 java.lang.Exception -> L8a
        L83:
            throw r2     // Catch: java.lang.Exception -> L8a
        L84:
            if (r6 == 0) goto L87
            goto L54
        L87:
            int r3 = r3 + 1
            goto L1c
        L8a:
            r2 = move-exception
            goto L90
        L8c:
            r4 = 0
            goto L97
        L8e:
            r2 = move-exception
            r4 = 0
        L90:
            com.sun.mail.util.MailLogger r3 = r10.logger
            java.util.logging.Level r5 = java.util.logging.Level.CONFIG
            r3.log(r5, r0, r2)
        L97:
            if (r4 != 0) goto Lad
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "/"
            r0.append(r2)
            r0.append(r11)
            java.lang.String r11 = r0.toString()
            r10.loadResource(r11, r12, r13, r1)
        Lad:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: javax.mail.Session.loadAllResources(java.lang.String, java.lang.Class, javax.mail.StreamLoader):void");
    }

    static ClassLoader getContextClassLoader() {
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() { // from class: javax.mail.Session.4
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

    private static InputStream getResourceAsStream(final Class<?> cls, final String str) throws IOException {
        try {
            return (InputStream) AccessController.doPrivileged(new PrivilegedExceptionAction<InputStream>() { // from class: javax.mail.Session.5
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.security.PrivilegedExceptionAction
                public InputStream run() throws IOException {
                    try {
                        return cls.getResourceAsStream(str);
                    } catch (RuntimeException e) {
                        IOException iOException = new IOException("ClassLoader.getResourceAsStream failed");
                        iOException.initCause(e);
                        throw iOException;
                    }
                }
            });
        } catch (PrivilegedActionException e) {
            throw ((IOException) e.getException());
        }
    }

    private static URL[] getResources(final ClassLoader classLoader, final String str) {
        return (URL[]) AccessController.doPrivileged(new PrivilegedAction<URL[]>() { // from class: javax.mail.Session.6
            @Override // java.security.PrivilegedAction
            public URL[] run() {
                URL[] urlArr = null;
                try {
                    ArrayList list = Collections.list(classLoader.getResources(str));
                    if (list.isEmpty()) {
                        return null;
                    }
                    urlArr = new URL[list.size()];
                    list.toArray(urlArr);
                    return urlArr;
                } catch (IOException | SecurityException unused) {
                    return urlArr;
                }
            }
        });
    }

    private static URL[] getSystemResources(final String str) {
        return (URL[]) AccessController.doPrivileged(new PrivilegedAction<URL[]>() { // from class: javax.mail.Session.7
            @Override // java.security.PrivilegedAction
            public URL[] run() {
                URL[] urlArr = null;
                try {
                    ArrayList list = Collections.list(ClassLoader.getSystemResources(str));
                    if (list.isEmpty()) {
                        return null;
                    }
                    urlArr = new URL[list.size()];
                    list.toArray(urlArr);
                    return urlArr;
                } catch (IOException | SecurityException unused) {
                    return urlArr;
                }
            }
        });
    }

    private static InputStream openStream(final URL url) throws IOException {
        try {
            return (InputStream) AccessController.doPrivileged(new PrivilegedExceptionAction<InputStream>() { // from class: javax.mail.Session.8
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.security.PrivilegedExceptionAction
                public InputStream run() throws IOException {
                    return url.openStream();
                }
            });
        } catch (PrivilegedActionException e) {
            throw ((IOException) e.getException());
        }
    }

    EventQueue getEventQueue() {
        return this.q;
    }
}
