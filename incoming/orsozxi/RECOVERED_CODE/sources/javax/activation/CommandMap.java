package javax.activation;

/* loaded from: classes3.dex */
public abstract class CommandMap {
    static /* synthetic */ Class class$javax$activation$CommandMap;
    private static CommandMap defaultCommandMap;

    public abstract DataContentHandler createDataContentHandler(String str);

    public abstract CommandInfo[] getAllCommands(String str);

    public abstract CommandInfo getCommand(String str, String str2);

    public String[] getMimeTypes() {
        return null;
    }

    public abstract CommandInfo[] getPreferredCommands(String str);

    public static CommandMap getDefaultCommandMap() {
        if (defaultCommandMap == null) {
            defaultCommandMap = new MailcapCommandMap();
        }
        return defaultCommandMap;
    }

    public static void setDefaultCommandMap(CommandMap commandMap) throws Throwable {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            try {
                securityManager.checkSetFactory();
            } catch (SecurityException e) {
                Class clsClass$ = class$javax$activation$CommandMap;
                if (clsClass$ == null) {
                    clsClass$ = class$("javax.activation.CommandMap");
                    class$javax$activation$CommandMap = clsClass$;
                }
                if (clsClass$.getClassLoader() != commandMap.getClass().getClassLoader()) {
                    throw e;
                }
            }
        }
        defaultCommandMap = commandMap;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public CommandInfo[] getPreferredCommands(String str, DataSource dataSource) {
        return getPreferredCommands(str);
    }

    public CommandInfo[] getAllCommands(String str, DataSource dataSource) {
        return getAllCommands(str);
    }

    public CommandInfo getCommand(String str, String str2, DataSource dataSource) {
        return getCommand(str, str2);
    }

    public DataContentHandler createDataContentHandler(String str, DataSource dataSource) {
        return createDataContentHandler(str);
    }
}
