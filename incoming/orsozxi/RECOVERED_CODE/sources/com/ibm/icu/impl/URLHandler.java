package com.ibm.icu.impl;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.commons.io.IOUtils;

/* loaded from: classes2.dex */
public abstract class URLHandler {
    private static final boolean DEBUG = ICUDebug.enabled("URLHandler");
    public static final String PROPNAME = "urlhandler.props";
    private static final Map<String, Method> handlers;

    public interface URLVisitor {
        void visit(String str);
    }

    public abstract void guide(URLVisitor uRLVisitor, boolean z, boolean z2);

    /* JADX WARN: Removed duplicated region for block: B:59:0x00c4 A[EXC_TOP_SPLITTER, PHI: r0 r1
      0x00c4: PHI (r0v4 java.io.BufferedReader) = (r0v3 java.io.BufferedReader), (r0v5 java.io.BufferedReader) binds: [B:49:0x00d4, B:41:0x00c2] A[DONT_GENERATE, DONT_INLINE]
      0x00c4: PHI (r1v4 java.util.HashMap) = (r1v2 java.util.HashMap), (r1v10 java.util.HashMap) binds: [B:49:0x00d4, B:41:0x00c2] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    static {
        /*
            Method dump skipped, instructions count: 225
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.URLHandler.<clinit>():void");
    }

    public static URLHandler get(URL url) {
        Method method;
        if (url == null) {
            return null;
        }
        String protocol = url.getProtocol();
        Map<String, Method> map = handlers;
        if (map != null && (method = map.get(protocol)) != null) {
            try {
                URLHandler uRLHandler = (URLHandler) method.invoke(null, url);
                if (uRLHandler != null) {
                    return uRLHandler;
                }
            } catch (IllegalAccessException e) {
                if (DEBUG) {
                    System.err.println(e);
                }
            } catch (IllegalArgumentException e2) {
                if (DEBUG) {
                    System.err.println(e2);
                }
            } catch (InvocationTargetException e3) {
                if (DEBUG) {
                    System.err.println(e3);
                }
            }
        }
        return getDefault(url);
    }

    protected static URLHandler getDefault(URL url) {
        URLHandler jarURLHandler;
        String protocol = url.getProtocol();
        try {
            if (protocol.equals("file")) {
                jarURLHandler = new FileURLHandler(url);
            } else {
                if (!protocol.equals("jar") && !protocol.equals("wsjar")) {
                    return null;
                }
                jarURLHandler = new JarURLHandler(url);
            }
            return jarURLHandler;
        } catch (Exception unused) {
            return null;
        }
    }

    private static class FileURLHandler extends URLHandler {
        File file;

        FileURLHandler(URL url) {
            try {
                this.file = new File(url.toURI());
            } catch (URISyntaxException unused) {
            }
            File file = this.file;
            if (file == null || !file.exists()) {
                if (URLHandler.DEBUG) {
                    System.err.println("file does not exist - " + url.toString());
                }
                throw new IllegalArgumentException();
            }
        }

        @Override // com.ibm.icu.impl.URLHandler
        public void guide(URLVisitor uRLVisitor, boolean z, boolean z2) {
            if (this.file.isDirectory()) {
                process(uRLVisitor, z, z2, "/", this.file.listFiles());
            } else {
                uRLVisitor.visit(this.file.getName());
            }
        }

        private void process(URLVisitor uRLVisitor, boolean z, boolean z2, String str, File[] fileArr) {
            if (fileArr != null) {
                for (File file : fileArr) {
                    if (!file.isDirectory()) {
                        uRLVisitor.visit(z2 ? file.getName() : str + file.getName());
                    } else if (z) {
                        process(uRLVisitor, z, z2, str + file.getName() + IOUtils.DIR_SEPARATOR_UNIX, file.listFiles());
                    }
                }
            }
        }
    }

    private static class JarURLHandler extends URLHandler {
        JarFile jarFile;
        String prefix;

        JarURLHandler(URL url) {
            String string;
            int iIndexOf;
            try {
                String path = url.getPath();
                this.prefix = path;
                int iLastIndexOf = path.lastIndexOf("!/");
                if (iLastIndexOf >= 0) {
                    this.prefix = this.prefix.substring(iLastIndexOf + 2);
                }
                if (!url.getProtocol().equals("jar") && (iIndexOf = (string = url.toString()).indexOf(":")) != -1) {
                    url = new URL("jar" + string.substring(iIndexOf));
                }
                this.jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
            } catch (Exception e) {
                if (URLHandler.DEBUG) {
                    System.err.println("icurb jar error: " + e);
                }
                throw new IllegalArgumentException("jar error: " + e.getMessage());
            }
        }

        @Override // com.ibm.icu.impl.URLHandler
        public void guide(URLVisitor uRLVisitor, boolean z, boolean z2) {
            String strSubstring;
            int iLastIndexOf;
            try {
                Enumeration<JarEntry> enumerationEntries = this.jarFile.entries();
                while (enumerationEntries.hasMoreElements()) {
                    JarEntry jarEntryNextElement = enumerationEntries.nextElement();
                    if (!jarEntryNextElement.isDirectory()) {
                        String name = jarEntryNextElement.getName();
                        if (name.startsWith(this.prefix) && ((iLastIndexOf = (strSubstring = name.substring(this.prefix.length())).lastIndexOf(47)) <= 0 || z)) {
                            if (z2 && iLastIndexOf != -1) {
                                strSubstring = strSubstring.substring(iLastIndexOf + 1);
                            }
                            uRLVisitor.visit(strSubstring);
                        }
                    }
                }
            } catch (Exception e) {
                if (URLHandler.DEBUG) {
                    System.err.println("icurb jar error: " + e);
                }
            }
        }
    }

    public void guide(URLVisitor uRLVisitor, boolean z) {
        guide(uRLVisitor, z, true);
    }
}
