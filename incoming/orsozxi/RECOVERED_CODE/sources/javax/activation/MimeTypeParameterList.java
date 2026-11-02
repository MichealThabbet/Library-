package javax.activation;

import java.util.Enumeration;
import java.util.Hashtable;

/* loaded from: classes3.dex */
public class MimeTypeParameterList {
    private static final String TSPECIALS = "()<>@,;:/[]?=\\\"";
    private Hashtable parameters = new Hashtable();

    public MimeTypeParameterList() {
    }

    public MimeTypeParameterList(String str) throws MimeTypeParseException {
        parse(str);
    }

    protected void parse(String str) throws MimeTypeParseException {
        int length;
        int i;
        String strSubstring;
        if (str != null && (length = str.length()) > 0) {
            int iSkipWhiteSpace = skipWhiteSpace(str, 0);
            while (iSkipWhiteSpace < length && str.charAt(iSkipWhiteSpace) == ';') {
                int iSkipWhiteSpace2 = skipWhiteSpace(str, iSkipWhiteSpace + 1);
                if (iSkipWhiteSpace2 >= length) {
                    return;
                }
                int i2 = iSkipWhiteSpace2;
                while (i2 < length && isTokenChar(str.charAt(i2))) {
                    i2++;
                }
                String lowerCase = str.substring(iSkipWhiteSpace2, i2).toLowerCase();
                int iSkipWhiteSpace3 = skipWhiteSpace(str, i2);
                if (iSkipWhiteSpace3 >= length || str.charAt(iSkipWhiteSpace3) != '=') {
                    throw new MimeTypeParseException("Couldn't find the '=' that separates a parameter name from its value.");
                }
                int iSkipWhiteSpace4 = skipWhiteSpace(str, iSkipWhiteSpace3 + 1);
                if (iSkipWhiteSpace4 >= length) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Couldn't find a value for parameter named ");
                    stringBuffer.append(lowerCase);
                    throw new MimeTypeParseException(stringBuffer.toString());
                }
                char cCharAt = str.charAt(iSkipWhiteSpace4);
                if (cCharAt == '\"') {
                    int i3 = iSkipWhiteSpace4 + 1;
                    if (i3 >= length) {
                        throw new MimeTypeParseException("Encountered unterminated quoted parameter value.");
                    }
                    int i4 = i3;
                    while (i4 < length) {
                        cCharAt = str.charAt(i4);
                        if (cCharAt == '\"') {
                            break;
                        }
                        if (cCharAt == '\\') {
                            i4++;
                        }
                        i4++;
                    }
                    if (cCharAt != '\"') {
                        throw new MimeTypeParseException("Encountered unterminated quoted parameter value.");
                    }
                    strSubstring = unquote(str.substring(i3, i4));
                    i = i4 + 1;
                } else if (isTokenChar(cCharAt)) {
                    i = iSkipWhiteSpace4;
                    while (i < length && isTokenChar(str.charAt(i))) {
                        i++;
                    }
                    strSubstring = str.substring(iSkipWhiteSpace4, i);
                } else {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("Unexpected character encountered at index ");
                    stringBuffer2.append(iSkipWhiteSpace4);
                    throw new MimeTypeParseException(stringBuffer2.toString());
                }
                this.parameters.put(lowerCase, strSubstring);
                iSkipWhiteSpace = skipWhiteSpace(str, i);
            }
            if (iSkipWhiteSpace < length) {
                throw new MimeTypeParseException("More characters encountered in input than expected.");
            }
        }
    }

    public int size() {
        return this.parameters.size();
    }

    public boolean isEmpty() {
        return this.parameters.isEmpty();
    }

    public String get(String str) {
        return (String) this.parameters.get(str.trim().toLowerCase());
    }

    public void set(String str, String str2) {
        this.parameters.put(str.trim().toLowerCase(), str2);
    }

    public void remove(String str) {
        this.parameters.remove(str.trim().toLowerCase());
    }

    public Enumeration getNames() {
        return this.parameters.keys();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.ensureCapacity(this.parameters.size() * 16);
        Enumeration enumerationKeys = this.parameters.keys();
        while (enumerationKeys.hasMoreElements()) {
            String str = (String) enumerationKeys.nextElement();
            stringBuffer.append("; ");
            stringBuffer.append(str);
            stringBuffer.append('=');
            stringBuffer.append(quote((String) this.parameters.get(str)));
        }
        return stringBuffer.toString();
    }

    private static boolean isTokenChar(char c) {
        return c > ' ' && c < 127 && TSPECIALS.indexOf(c) < 0;
    }

    private static int skipWhiteSpace(String str, int i) {
        int length = str.length();
        while (i < length && Character.isWhitespace(str.charAt(i))) {
            i++;
        }
        return i;
    }

    private static String quote(String str) {
        int length = str.length();
        boolean z = false;
        for (int i = 0; i < length && !z; i++) {
            z = !isTokenChar(str.charAt(i));
        }
        if (!z) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.ensureCapacity((int) (length * 1.5d));
        stringBuffer.append('\"');
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '\\' || cCharAt == '\"') {
                stringBuffer.append('\\');
            }
            stringBuffer.append(cCharAt);
        }
        stringBuffer.append('\"');
        return stringBuffer.toString();
    }

    private static String unquote(String str) {
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.ensureCapacity(length);
        boolean z = false;
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (!z && cCharAt != '\\') {
                stringBuffer.append(cCharAt);
            } else if (z) {
                stringBuffer.append(cCharAt);
                z = false;
            } else {
                z = true;
            }
        }
        return stringBuffer.toString();
    }
}
