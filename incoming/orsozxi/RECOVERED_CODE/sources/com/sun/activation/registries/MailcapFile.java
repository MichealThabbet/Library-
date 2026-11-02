package com.sun.activation.registries;

import com.facebook.internal.ServerProtocol;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class MailcapFile {
    private static boolean addReverse = false;
    private Map type_hash = new HashMap();
    private Map fallback_hash = new HashMap();
    private Map native_commands = new HashMap();

    static {
        try {
            addReverse = Boolean.getBoolean("javax.activation.addreverse");
        } catch (Throwable unused) {
        }
    }

    public MailcapFile(String str) throws Throwable {
        if (LogSupport.isLoggable()) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("new MailcapFile: file ");
            stringBuffer.append(str);
            LogSupport.log(stringBuffer.toString());
        }
        FileReader fileReader = null;
        try {
            FileReader fileReader2 = new FileReader(str);
            try {
                parse(new BufferedReader(fileReader2));
                try {
                    fileReader2.close();
                } catch (IOException unused) {
                }
            } catch (Throwable th) {
                th = th;
                fileReader = fileReader2;
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public MailcapFile(InputStream inputStream) throws IOException {
        if (LogSupport.isLoggable()) {
            LogSupport.log("new MailcapFile: InputStream");
        }
        parse(new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1")));
    }

    public MailcapFile() {
        if (LogSupport.isLoggable()) {
            LogSupport.log("new MailcapFile: default");
        }
    }

    public Map getMailcapList(String str) {
        Map map = (Map) this.type_hash.get(str);
        int iIndexOf = str.indexOf(47) + 1;
        if (str.substring(iIndexOf).equals("*")) {
            return map;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str.substring(0, iIndexOf));
        stringBuffer.append("*");
        Map map2 = (Map) this.type_hash.get(stringBuffer.toString());
        return map2 != null ? map != null ? mergeResults(map, map2) : map2 : map;
    }

    public Map getMailcapFallbackList(String str) {
        Map map = (Map) this.fallback_hash.get(str);
        int iIndexOf = str.indexOf(47) + 1;
        if (str.substring(iIndexOf).equals("*")) {
            return map;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str.substring(0, iIndexOf));
        stringBuffer.append("*");
        Map map2 = (Map) this.fallback_hash.get(stringBuffer.toString());
        return map2 != null ? map != null ? mergeResults(map, map2) : map2 : map;
    }

    public String[] getMimeTypes() {
        HashSet hashSet = new HashSet(this.type_hash.keySet());
        hashSet.addAll(this.fallback_hash.keySet());
        hashSet.addAll(this.native_commands.keySet());
        return (String[]) hashSet.toArray(new String[hashSet.size()]);
    }

    public String[] getNativeCommands(String str) {
        List list = (List) this.native_commands.get(str.toLowerCase());
        if (list != null) {
            return (String[]) list.toArray(new String[list.size()]);
        }
        return null;
    }

    private Map mergeResults(Map map, Map map2) {
        HashMap map3 = new HashMap(map);
        for (String str : map2.keySet()) {
            List list = (List) map3.get(str);
            if (list == null) {
                map3.put(str, map2.get(str));
            } else {
                List list2 = (List) map2.get(str);
                ArrayList arrayList = new ArrayList(list);
                arrayList.addAll(list2);
                map3.put(str, arrayList);
            }
        }
        return map3;
    }

    public void appendToMailcap(String str) {
        if (LogSupport.isLoggable()) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("appendToMailcap: ");
            stringBuffer.append(str);
            LogSupport.log(stringBuffer.toString());
        }
        try {
            parse(new StringReader(str));
        } catch (IOException unused) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0052, code lost:
    
        r3 = new java.lang.StringBuffer();
        r3.append(r1);
        r3.append(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0061, code lost:
    
        parseLine(r3.toString());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void parse(java.io.Reader r7) throws java.io.IOException {
        /*
            r6 = this;
            java.io.BufferedReader r0 = new java.io.BufferedReader
            r0.<init>(r7)
            r7 = 0
        L6:
            r1 = r7
        L7:
            java.lang.String r2 = r0.readLine()
            if (r2 == 0) goto L6b
            java.lang.String r2 = r2.trim()
            r3 = 0
            char r4 = r2.charAt(r3)     // Catch: java.lang.Throwable -> L69
            r5 = 35
            if (r4 != r5) goto L1b
            goto L7
        L1b:
            int r4 = r2.length()     // Catch: java.lang.Throwable -> L69
            int r4 = r4 + (-1)
            char r4 = r2.charAt(r4)     // Catch: java.lang.Throwable -> L69
            r5 = 92
            if (r4 != r5) goto L50
            if (r1 == 0) goto L45
            java.lang.StringBuffer r4 = new java.lang.StringBuffer     // Catch: java.lang.Throwable -> L69
            r4.<init>()     // Catch: java.lang.Throwable -> L69
            r4.append(r1)     // Catch: java.lang.Throwable -> L69
            int r5 = r2.length()     // Catch: java.lang.Throwable -> L69
            int r5 = r5 + (-1)
            java.lang.String r2 = r2.substring(r3, r5)     // Catch: java.lang.Throwable -> L69
            r4.append(r2)     // Catch: java.lang.Throwable -> L69
            java.lang.String r1 = r4.toString()     // Catch: java.lang.Throwable -> L69
            goto L7
        L45:
            int r4 = r2.length()     // Catch: java.lang.Throwable -> L69
            int r4 = r4 + (-1)
            java.lang.String r1 = r2.substring(r3, r4)     // Catch: java.lang.Throwable -> L69
            goto L7
        L50:
            if (r1 == 0) goto L65
            java.lang.StringBuffer r3 = new java.lang.StringBuffer     // Catch: java.lang.Throwable -> L69
            r3.<init>()     // Catch: java.lang.Throwable -> L69
            r3.append(r1)     // Catch: java.lang.Throwable -> L69
            r3.append(r2)     // Catch: java.lang.Throwable -> L69
            java.lang.String r1 = r3.toString()     // Catch: java.lang.Throwable -> L69
            r6.parseLine(r1)     // Catch: com.sun.activation.registries.MailcapParseException -> L6 java.lang.Throwable -> L69
            goto L6
        L65:
            r6.parseLine(r2)     // Catch: java.lang.Throwable -> L69
            goto L7
        L69:
            goto L7
        L6b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sun.activation.registries.MailcapFile.parse(java.io.Reader):void");
    }

    protected void parseLine(String str) throws MailcapParseException, IOException {
        int iNextToken;
        String lowerCase;
        int iNextToken2;
        MailcapTokenizer mailcapTokenizer = new MailcapTokenizer(str);
        mailcapTokenizer.setIsAutoquoting(false);
        if (LogSupport.isLoggable()) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("parse: ");
            stringBuffer.append(str);
            LogSupport.log(stringBuffer.toString());
        }
        int iNextToken3 = mailcapTokenizer.nextToken();
        if (iNextToken3 != 2) {
            reportParseError(2, iNextToken3, mailcapTokenizer.getCurrentTokenValue());
        }
        String lowerCase2 = mailcapTokenizer.getCurrentTokenValue().toLowerCase();
        int iNextToken4 = mailcapTokenizer.nextToken();
        if (iNextToken4 != 47 && iNextToken4 != 59) {
            reportParseError(47, 59, iNextToken4, mailcapTokenizer.getCurrentTokenValue());
        }
        if (iNextToken4 == 47) {
            int iNextToken5 = mailcapTokenizer.nextToken();
            if (iNextToken5 != 2) {
                reportParseError(2, iNextToken5, mailcapTokenizer.getCurrentTokenValue());
            }
            lowerCase = mailcapTokenizer.getCurrentTokenValue().toLowerCase();
            iNextToken = mailcapTokenizer.nextToken();
        } else {
            iNextToken = iNextToken4;
            lowerCase = "*";
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(lowerCase2);
        stringBuffer2.append("/");
        stringBuffer2.append(lowerCase);
        String string = stringBuffer2.toString();
        if (LogSupport.isLoggable()) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("  Type: ");
            stringBuffer3.append(string);
            LogSupport.log(stringBuffer3.toString());
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (iNextToken != 59) {
            reportParseError(59, iNextToken, mailcapTokenizer.getCurrentTokenValue());
        }
        mailcapTokenizer.setIsAutoquoting(true);
        int iNextToken6 = mailcapTokenizer.nextToken();
        mailcapTokenizer.setIsAutoquoting(false);
        if (iNextToken6 != 2 && iNextToken6 != 59) {
            reportParseError(2, 59, iNextToken6, mailcapTokenizer.getCurrentTokenValue());
        }
        if (iNextToken6 == 2) {
            List list = (List) this.native_commands.get(string);
            if (list == null) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(str);
                this.native_commands.put(string, arrayList);
            } else {
                list.add(str);
            }
        }
        if (iNextToken6 != 59) {
            iNextToken6 = mailcapTokenizer.nextToken();
        }
        if (iNextToken6 != 59) {
            if (iNextToken6 != 5) {
                reportParseError(5, 59, iNextToken6, mailcapTokenizer.getCurrentTokenValue());
                return;
            }
            return;
        }
        boolean z = false;
        do {
            int iNextToken7 = mailcapTokenizer.nextToken();
            if (iNextToken7 != 2) {
                reportParseError(2, iNextToken7, mailcapTokenizer.getCurrentTokenValue());
            }
            String lowerCase3 = mailcapTokenizer.getCurrentTokenValue().toLowerCase();
            iNextToken2 = mailcapTokenizer.nextToken();
            if (iNextToken2 != 61 && iNextToken2 != 59 && iNextToken2 != 5) {
                reportParseError(61, 59, 5, iNextToken2, mailcapTokenizer.getCurrentTokenValue());
            }
            if (iNextToken2 == 61) {
                mailcapTokenizer.setIsAutoquoting(true);
                int iNextToken8 = mailcapTokenizer.nextToken();
                mailcapTokenizer.setIsAutoquoting(false);
                if (iNextToken8 != 2) {
                    reportParseError(2, iNextToken8, mailcapTokenizer.getCurrentTokenValue());
                }
                String currentTokenValue = mailcapTokenizer.getCurrentTokenValue();
                if (lowerCase3.startsWith("x-java-")) {
                    String strSubstring = lowerCase3.substring(7);
                    if (strSubstring.equals("fallback-entry") && currentTokenValue.equalsIgnoreCase(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE)) {
                        z = true;
                    } else {
                        if (LogSupport.isLoggable()) {
                            StringBuffer stringBuffer4 = new StringBuffer();
                            stringBuffer4.append("    Command: ");
                            stringBuffer4.append(strSubstring);
                            stringBuffer4.append(", Class: ");
                            stringBuffer4.append(currentTokenValue);
                            LogSupport.log(stringBuffer4.toString());
                        }
                        List arrayList2 = (List) linkedHashMap.get(strSubstring);
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList();
                            linkedHashMap.put(strSubstring, arrayList2);
                        }
                        if (addReverse) {
                            arrayList2.add(0, currentTokenValue);
                        } else {
                            arrayList2.add(currentTokenValue);
                        }
                    }
                }
                iNextToken2 = mailcapTokenizer.nextToken();
            }
        } while (iNextToken2 == 59);
        Map map = z ? this.fallback_hash : this.type_hash;
        Map map2 = (Map) map.get(string);
        if (map2 == null) {
            map.put(string, linkedHashMap);
            return;
        }
        if (LogSupport.isLoggable()) {
            StringBuffer stringBuffer5 = new StringBuffer();
            stringBuffer5.append("Merging commands for type ");
            stringBuffer5.append(string);
            LogSupport.log(stringBuffer5.toString());
        }
        for (String str2 : map2.keySet()) {
            List list2 = (List) map2.get(str2);
            List<String> list3 = (List) linkedHashMap.get(str2);
            if (list3 != null) {
                for (String str3 : list3) {
                    if (!list2.contains(str3)) {
                        if (addReverse) {
                            list2.add(0, str3);
                        } else {
                            list2.add(str3);
                        }
                    }
                }
            }
        }
        for (String str4 : linkedHashMap.keySet()) {
            if (!map2.containsKey(str4)) {
                map2.put(str4, (List) linkedHashMap.get(str4));
            }
        }
    }

    protected static void reportParseError(int i, int i2, String str) throws MailcapParseException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Encountered a ");
        stringBuffer.append(MailcapTokenizer.nameForToken(i2));
        stringBuffer.append(" token (");
        stringBuffer.append(str);
        stringBuffer.append(") while expecting a ");
        stringBuffer.append(MailcapTokenizer.nameForToken(i));
        stringBuffer.append(" token.");
        throw new MailcapParseException(stringBuffer.toString());
    }

    protected static void reportParseError(int i, int i2, int i3, String str) throws MailcapParseException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Encountered a ");
        stringBuffer.append(MailcapTokenizer.nameForToken(i3));
        stringBuffer.append(" token (");
        stringBuffer.append(str);
        stringBuffer.append(") while expecting a ");
        stringBuffer.append(MailcapTokenizer.nameForToken(i));
        stringBuffer.append(" or a ");
        stringBuffer.append(MailcapTokenizer.nameForToken(i2));
        stringBuffer.append(" token.");
        throw new MailcapParseException(stringBuffer.toString());
    }

    protected static void reportParseError(int i, int i2, int i3, int i4, String str) throws MailcapParseException {
        if (LogSupport.isLoggable()) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("PARSE ERROR: Encountered a ");
            stringBuffer.append(MailcapTokenizer.nameForToken(i4));
            stringBuffer.append(" token (");
            stringBuffer.append(str);
            stringBuffer.append(") while expecting a ");
            stringBuffer.append(MailcapTokenizer.nameForToken(i));
            stringBuffer.append(", a ");
            stringBuffer.append(MailcapTokenizer.nameForToken(i2));
            stringBuffer.append(", or a ");
            stringBuffer.append(MailcapTokenizer.nameForToken(i3));
            stringBuffer.append(" token.");
            LogSupport.log(stringBuffer.toString());
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Encountered a ");
        stringBuffer2.append(MailcapTokenizer.nameForToken(i4));
        stringBuffer2.append(" token (");
        stringBuffer2.append(str);
        stringBuffer2.append(") while expecting a ");
        stringBuffer2.append(MailcapTokenizer.nameForToken(i));
        stringBuffer2.append(", a ");
        stringBuffer2.append(MailcapTokenizer.nameForToken(i2));
        stringBuffer2.append(", or a ");
        stringBuffer2.append(MailcapTokenizer.nameForToken(i3));
        stringBuffer2.append(" token.");
        throw new MailcapParseException(stringBuffer2.toString());
    }
}
