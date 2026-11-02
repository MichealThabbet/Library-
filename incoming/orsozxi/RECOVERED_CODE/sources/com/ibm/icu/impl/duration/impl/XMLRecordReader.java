package com.ibm.icu.impl.duration.impl;

import com.facebook.internal.ServerProtocol;
import com.ibm.icu.lang.UCharacter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class XMLRecordReader implements RecordReader {
    private boolean atTag;
    private List<String> nameStack = new ArrayList();
    private Reader r;
    private String tag;

    public XMLRecordReader(Reader reader) {
        this.r = reader;
        if (getTag().startsWith("?xml")) {
            advance();
        }
        if (getTag().startsWith("!--")) {
            advance();
        }
    }

    @Override // com.ibm.icu.impl.duration.impl.RecordReader
    public boolean open(String str) {
        if (!getTag().equals(str)) {
            return false;
        }
        this.nameStack.add(str);
        advance();
        return true;
    }

    @Override // com.ibm.icu.impl.duration.impl.RecordReader
    public boolean close() {
        int size = this.nameStack.size() - 1;
        String str = this.nameStack.get(size);
        if (!getTag().equals("/" + str)) {
            return false;
        }
        this.nameStack.remove(size);
        advance();
        return true;
    }

    @Override // com.ibm.icu.impl.duration.impl.RecordReader
    public boolean bool(String str) {
        String strString = string(str);
        if (strString != null) {
            return ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(strString);
        }
        return false;
    }

    @Override // com.ibm.icu.impl.duration.impl.RecordReader
    public boolean[] boolArray(String str) {
        String[] strArrStringArray = stringArray(str);
        if (strArrStringArray == null) {
            return null;
        }
        boolean[] zArr = new boolean[strArrStringArray.length];
        for (int i = 0; i < strArrStringArray.length; i++) {
            zArr[i] = ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(strArrStringArray[i]);
        }
        return zArr;
    }

    @Override // com.ibm.icu.impl.duration.impl.RecordReader
    public char character(String str) {
        String strString = string(str);
        if (strString != null) {
            return strString.charAt(0);
        }
        return (char) 65535;
    }

    @Override // com.ibm.icu.impl.duration.impl.RecordReader
    public char[] characterArray(String str) {
        String[] strArrStringArray = stringArray(str);
        if (strArrStringArray == null) {
            return null;
        }
        char[] cArr = new char[strArrStringArray.length];
        for (int i = 0; i < strArrStringArray.length; i++) {
            cArr[i] = strArrStringArray[i].charAt(0);
        }
        return cArr;
    }

    @Override // com.ibm.icu.impl.duration.impl.RecordReader
    public byte namedIndex(String str, String[] strArr) {
        String strString = string(str);
        if (strString == null) {
            return (byte) -1;
        }
        for (int i = 0; i < strArr.length; i++) {
            if (strString.equals(strArr[i])) {
                return (byte) i;
            }
        }
        return (byte) -1;
    }

    @Override // com.ibm.icu.impl.duration.impl.RecordReader
    public byte[] namedIndexArray(String str, String[] strArr) {
        String[] strArrStringArray = stringArray(str);
        if (strArrStringArray == null) {
            return null;
        }
        byte[] bArr = new byte[strArrStringArray.length];
        for (int i = 0; i < strArrStringArray.length; i++) {
            String str2 = strArrStringArray[i];
            int i2 = 0;
            while (true) {
                if (i2 < strArr.length) {
                    if (strArr[i2].equals(str2)) {
                        bArr[i] = (byte) i2;
                        break;
                    }
                    i2++;
                } else {
                    bArr[i] = -1;
                    break;
                }
            }
        }
        return bArr;
    }

    @Override // com.ibm.icu.impl.duration.impl.RecordReader
    public String string(String str) {
        if (!match(str)) {
            return null;
        }
        String data = readData();
        if (match("/" + str)) {
            return data;
        }
        return null;
    }

    @Override // com.ibm.icu.impl.duration.impl.RecordReader
    public String[] stringArray(String str) {
        if (match(str + "List")) {
            ArrayList arrayList = new ArrayList();
            while (true) {
                String strString = string(str);
                if (strString == null) {
                    break;
                }
                if ("Null".equals(strString)) {
                    strString = null;
                }
                arrayList.add(strString);
            }
            if (match("/" + str + "List")) {
                return (String[]) arrayList.toArray(new String[arrayList.size()]);
            }
        }
        return null;
    }

    @Override // com.ibm.icu.impl.duration.impl.RecordReader
    public String[][] stringTable(String str) {
        if (match(str + "Table")) {
            ArrayList arrayList = new ArrayList();
            while (true) {
                String[] strArrStringArray = stringArray(str);
                if (strArrStringArray == null) {
                    break;
                }
                arrayList.add(strArrStringArray);
            }
            if (match("/" + str + "Table")) {
                return (String[][]) arrayList.toArray(new String[arrayList.size()][]);
            }
        }
        return (String[][]) null;
    }

    private boolean match(String str) {
        if (!getTag().equals(str)) {
            return false;
        }
        advance();
        return true;
    }

    private String getTag() {
        if (this.tag == null) {
            this.tag = readNextTag();
        }
        return this.tag;
    }

    private void advance() {
        this.tag = null;
    }

    private String readData() {
        int i;
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        while (true) {
            i = readChar();
            if (i == -1 || i == 60) {
                break;
            }
            if (i == 38) {
                int i2 = readChar();
                if (i2 == 35) {
                    StringBuilder sb2 = new StringBuilder();
                    int i3 = 10;
                    int i4 = readChar();
                    if (i4 == 120) {
                        i3 = 16;
                        i4 = readChar();
                    }
                    while (i4 != 59 && i4 != -1) {
                        sb2.append((char) i4);
                        i4 = readChar();
                    }
                    try {
                        i = (char) Integer.parseInt(sb2.toString(), i3);
                    } catch (NumberFormatException e) {
                        System.err.println("numbuf: " + sb2.toString() + " radix: " + i3);
                        throw e;
                    }
                } else {
                    StringBuilder sb3 = new StringBuilder();
                    while (i2 != 59 && i2 != -1) {
                        sb3.append((char) i2);
                        i2 = readChar();
                    }
                    String string = sb3.toString();
                    if (string.equals("lt")) {
                        i = 60;
                    } else if (string.equals("gt")) {
                        i = 62;
                    } else if (string.equals("quot")) {
                        i = 34;
                    } else if (string.equals("apos")) {
                        i = 39;
                    } else if (string.equals("amp")) {
                        i = 38;
                    } else {
                        System.err.println("unrecognized character entity: '" + string + "'");
                    }
                }
            }
            if (!UCharacter.isWhitespace(i)) {
                z = false;
            } else if (!z) {
                i = 32;
                z = true;
            }
            sb.append((char) i);
        }
        this.atTag = i == 60;
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0031, code lost:
    
        if (r0 != 60) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0033, code lost:
    
        r5.atTag = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.lang.String readNextTag() {
        /*
            r5 = this;
        L0:
            boolean r0 = r5.atTag
            r1 = -1
            if (r0 != 0) goto L36
            int r0 = r5.readChar()
            r2 = 60
            if (r0 == r2) goto L31
            if (r0 != r1) goto L10
            goto L31
        L10:
            boolean r2 = com.ibm.icu.lang.UCharacter.isWhitespace(r0)
            if (r2 != 0) goto L0
            java.io.PrintStream r2 = java.lang.System.err
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Unexpected non-whitespace character "
            r3.append(r4)
            java.lang.String r0 = java.lang.Integer.toHexString(r0)
            r3.append(r0)
            java.lang.String r0 = r3.toString()
            r2.println(r0)
            goto L36
        L31:
            if (r0 != r2) goto L36
            r0 = 1
            r5.atTag = r0
        L36:
            boolean r0 = r5.atTag
            if (r0 == 0) goto L57
            r0 = 0
            r5.atTag = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
        L42:
            int r2 = r5.readChar()
            r3 = 62
            if (r2 == r3) goto L52
            if (r2 != r1) goto L4d
            goto L52
        L4d:
            char r2 = (char) r2
            r0.append(r2)
            goto L42
        L52:
            java.lang.String r0 = r0.toString()
            return r0
        L57:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.duration.impl.XMLRecordReader.readNextTag():java.lang.String");
    }

    int readChar() {
        try {
            return this.r.read();
        } catch (IOException unused) {
            return -1;
        }
    }
}
