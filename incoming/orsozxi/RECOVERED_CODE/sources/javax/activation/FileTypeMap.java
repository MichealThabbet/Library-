package javax.activation;

import java.io.File;

/* loaded from: classes3.dex */
public abstract class FileTypeMap {
    static /* synthetic */ Class class$javax$activation$FileTypeMap;
    private static FileTypeMap defaultMap;

    public abstract String getContentType(File file);

    public abstract String getContentType(String str);

    public static void setDefaultFileTypeMap(FileTypeMap fileTypeMap) throws Throwable {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            try {
                securityManager.checkSetFactory();
            } catch (SecurityException e) {
                Class clsClass$ = class$javax$activation$FileTypeMap;
                if (clsClass$ == null) {
                    clsClass$ = class$("javax.activation.FileTypeMap");
                    class$javax$activation$FileTypeMap = clsClass$;
                }
                if (clsClass$.getClassLoader() != fileTypeMap.getClass().getClassLoader()) {
                    throw e;
                }
            }
        }
        defaultMap = fileTypeMap;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public static FileTypeMap getDefaultFileTypeMap() {
        if (defaultMap == null) {
            defaultMap = new MimetypesFileTypeMap();
        }
        return defaultMap;
    }
}
