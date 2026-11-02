package com.ibm.icu.impl.duration.impl;

import com.ibm.icu.lang.UCharacter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.message.TokenParser;

/* loaded from: classes2.dex */
public class XMLRecordWriter implements RecordWriter {
    private static final String INDENT = "    ";
    static final String NULL_NAME = "Null";
    private List<String> nameStack = new ArrayList();
    private Writer w;

    public XMLRecordWriter(Writer writer) {
        this.w = writer;
    }

    @Override // com.ibm.icu.impl.duration.impl.RecordWriter
    public boolean open(String str) throws IOException {
        newline();
        writeString("<" + str + ">");
        this.nameStack.add(str);
        return true;
    }

    @Override // com.ibm.icu.impl.duration.impl.RecordWriter
    public boolean close() throws IOException {
        int size = this.nameStack.size() - 1;
        if (size < 0) {
            return false;
        }
        String strRemove = this.nameStack.remove(size);
        newline();
        writeString("</" + strRemove + ">");
        return true;
    }

    public void flush() throws IOException {
        try {
            this.w.flush();
        } catch (IOException unused) {
        }
    }

    @Override // com.ibm.icu.impl.duration.impl.RecordWriter
    public void bool(String str, boolean z) throws IOException {
        internalString(str, String.valueOf(z));
    }

    @Override // com.ibm.icu.impl.duration.impl.RecordWriter
    public void boolArray(String str, boolean[] zArr) throws IOException {
        if (zArr != null) {
            String[] strArr = new String[zArr.length];
            for (int i = 0; i < zArr.length; i++) {
                strArr[i] = String.valueOf(zArr[i]);
            }
            stringArray(str, strArr);
        }
    }

    private static String ctos(char c) {
        return c == '<' ? "&lt;" : c == '&' ? "&amp;" : String.valueOf(c);
    }

    @Override // com.ibm.icu.impl.duration.impl.RecordWriter
    public void character(String str, char c) throws IOException {
        if (c != 65535) {
            internalString(str, ctos(c));
        }
    }

    @Override // com.ibm.icu.impl.duration.impl.RecordWriter
    public void characterArray(String str, char[] cArr) throws IOException {
        if (cArr != null) {
            String[] strArr = new String[cArr.length];
            for (int i = 0; i < cArr.length; i++) {
                char c = cArr[i];
                if (c == 65535) {
                    strArr[i] = NULL_NAME;
                } else {
                    strArr[i] = ctos(c);
                }
            }
            internalStringArray(str, strArr);
        }
    }

    @Override // com.ibm.icu.impl.duration.impl.RecordWriter
    public void namedIndex(String str, String[] strArr, int i) throws IOException {
        if (i >= 0) {
            internalString(str, strArr[i]);
        }
    }

    @Override // com.ibm.icu.impl.duration.impl.RecordWriter
    public void namedIndexArray(String str, String[] strArr, byte[] bArr) throws IOException {
        if (bArr != null) {
            String[] strArr2 = new String[bArr.length];
            for (int i = 0; i < bArr.length; i++) {
                byte b = bArr[i];
                if (b < 0) {
                    strArr2[i] = NULL_NAME;
                } else {
                    strArr2[i] = strArr[b];
                }
            }
            internalStringArray(str, strArr2);
        }
    }

    public static String normalize(String str) {
        StringBuilder sb = null;
        if (str == null) {
            return null;
        }
        boolean z = false;
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            boolean z2 = true;
            if (UCharacter.isWhitespace(cCharAt)) {
                if (sb == null && (z || cCharAt != ' ')) {
                    sb = new StringBuilder(str.substring(0, i));
                }
                if (!z) {
                    z = true;
                    cCharAt = TokenParser.SP;
                    z2 = false;
                }
            } else {
                if (cCharAt != '<' && cCharAt != '&') {
                    z2 = false;
                }
                if (z2 && sb == null) {
                    sb = new StringBuilder(str.substring(0, i));
                }
                z = false;
            }
            if (sb != null) {
                if (z2) {
                    sb.append(cCharAt == '<' ? "&lt;" : "&amp;");
                } else {
                    sb.append(cCharAt);
                }
            }
        }
        return sb != null ? sb.toString() : str;
    }

    private void internalString(String str, String str2) throws IOException {
        if (str2 != null) {
            newline();
            writeString("<" + str + ">" + str2 + "</" + str + ">");
        }
    }

    private void internalStringArray(String str, String[] strArr) throws IOException {
        if (strArr != null) {
            push(str + "List");
            for (String str2 : strArr) {
                if (str2 == null) {
                    str2 = NULL_NAME;
                }
                string(str, str2);
            }
            pop();
        }
    }

    @Override // com.ibm.icu.impl.duration.impl.RecordWriter
    public void string(String str, String str2) throws IOException {
        internalString(str, normalize(str2));
    }

    @Override // com.ibm.icu.impl.duration.impl.RecordWriter
    public void stringArray(String str, String[] strArr) throws IOException {
        if (strArr != null) {
            push(str + "List");
            for (String str2 : strArr) {
                String strNormalize = normalize(str2);
                if (strNormalize == null) {
                    strNormalize = NULL_NAME;
                }
                internalString(str, strNormalize);
            }
            pop();
        }
    }

    @Override // com.ibm.icu.impl.duration.impl.RecordWriter
    public void stringTable(String str, String[][] strArr) throws IOException {
        if (strArr != null) {
            push(str + "Table");
            for (String[] strArr2 : strArr) {
                if (strArr2 == null) {
                    internalString(str + "List", NULL_NAME);
                } else {
                    stringArray(str, strArr2);
                }
            }
            pop();
        }
    }

    private void push(String str) throws IOException {
        newline();
        writeString("<" + str + ">");
        this.nameStack.add(str);
    }

    private void pop() throws IOException {
        String strRemove = this.nameStack.remove(this.nameStack.size() - 1);
        newline();
        writeString("</" + strRemove + ">");
    }

    private void newline() throws IOException {
        writeString("\n");
        for (int i = 0; i < this.nameStack.size(); i++) {
            writeString(INDENT);
        }
    }

    private void writeString(String str) throws IOException {
        Writer writer = this.w;
        if (writer != null) {
            try {
                writer.write(str);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                this.w = null;
            }
        }
    }
}
