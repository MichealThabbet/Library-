package javax.mail.internet;

import com.sun.mail.util.PropUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/* loaded from: classes3.dex */
public class ParameterList {
    private String lastName;
    private Map<String, Object> list;
    private Set<String> multisegmentNames;
    private Map<String, Object> slist;
    private static final boolean encodeParameters = PropUtil.getBooleanSystemProperty("mail.mime.encodeparameters", true);
    private static final boolean decodeParameters = PropUtil.getBooleanSystemProperty("mail.mime.decodeparameters", true);
    private static final boolean decodeParametersStrict = PropUtil.getBooleanSystemProperty("mail.mime.decodeparameters.strict", false);
    private static final boolean applehack = PropUtil.getBooleanSystemProperty("mail.mime.applefilenames", false);
    private static final boolean windowshack = PropUtil.getBooleanSystemProperty("mail.mime.windowsfilenames", false);
    private static final boolean parametersStrict = PropUtil.getBooleanSystemProperty("mail.mime.parameters.strict", true);
    private static final boolean splitLongParameters = PropUtil.getBooleanSystemProperty("mail.mime.splitlongparameters", true);
    private static final char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static class Value {
        String charset;
        String encodedValue;
        String value;

        private Value() {
        }
    }

    private static class LiteralValue {
        String value;

        private LiteralValue() {
        }
    }

    private static class MultiValue extends ArrayList<Object> {
        private static final long serialVersionUID = 699561094618751023L;
        String value;

        private MultiValue() {
        }
    }

    private static class ParamEnum implements Enumeration<String> {

        /* renamed from: it, reason: collision with root package name */
        private Iterator<String> f25it;

        ParamEnum(Iterator<String> it2) {
            this.f25it = it2;
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return this.f25it.hasNext();
        }

        @Override // java.util.Enumeration
        public String nextElement() {
            return this.f25it.next();
        }
    }

    public ParameterList() {
        this.list = new LinkedHashMap();
        this.lastName = null;
        if (decodeParameters) {
            this.multisegmentNames = new HashSet();
            this.slist = new HashMap();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0030, code lost:
    
        if (javax.mail.internet.ParameterList.decodeParameters == false) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0032, code lost:
    
        combineMultisegmentNames(false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0036, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:?, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public ParameterList(java.lang.String r12) throws javax.mail.internet.ParseException {
        /*
            Method dump skipped, instructions count: 366
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: javax.mail.internet.ParameterList.<init>(java.lang.String):void");
    }

    public void combineSegments() {
        if (!decodeParameters || this.multisegmentNames.size() <= 0) {
            return;
        }
        try {
            combineMultisegmentNames(true);
        } catch (ParseException unused) {
        }
    }

    private void putEncodedName(String str, String str2) throws ParseException {
        Object objExtractCharset;
        int iIndexOf = str.indexOf(42);
        if (iIndexOf < 0) {
            this.list.put(str, str2);
            return;
        }
        if (iIndexOf == str.length() - 1) {
            String strSubstring = str.substring(0, iIndexOf);
            Value valueExtractCharset = extractCharset(str2);
            try {
                valueExtractCharset.value = decodeBytes(valueExtractCharset.value, valueExtractCharset.charset);
            } catch (UnsupportedEncodingException e) {
                if (decodeParametersStrict) {
                    throw new ParseException(e.toString());
                }
            }
            this.list.put(strSubstring, valueExtractCharset);
            return;
        }
        String strSubstring2 = str.substring(0, iIndexOf);
        this.multisegmentNames.add(strSubstring2);
        this.list.put(strSubstring2, "");
        Object obj = str2;
        if (str.endsWith("*")) {
            if (str.endsWith("*0*")) {
                objExtractCharset = extractCharset(str2);
            } else {
                Object value = new Value();
                Value value2 = (Value) value;
                value2.encodedValue = str2;
                value2.value = str2;
                objExtractCharset = value;
            }
            str = str.substring(0, str.length() - 1);
            obj = objExtractCharset;
        }
        this.slist.put(str, obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0069 A[Catch: UnsupportedEncodingException -> 0x005f, all -> 0x0101, TryCatch #5 {UnsupportedEncodingException -> 0x005f, blocks: (B:21:0x005a, B:25:0x0063, B:29:0x006f, B:30:0x0076, B:27:0x0069), top: B:95:0x005a, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x006f A[Catch: UnsupportedEncodingException -> 0x005f, all -> 0x0101, TryCatch #5 {UnsupportedEncodingException -> 0x005f, blocks: (B:21:0x005a, B:25:0x0063, B:29:0x006f, B:30:0x0076, B:27:0x0069), top: B:95:0x005a, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0076 A[Catch: UnsupportedEncodingException -> 0x005f, all -> 0x0101, TRY_LEAVE, TryCatch #5 {UnsupportedEncodingException -> 0x005f, blocks: (B:21:0x005a, B:25:0x0063, B:29:0x006f, B:30:0x0076, B:27:0x0069), top: B:95:0x005a, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void combineMultisegmentNames(boolean r10) throws javax.mail.internet.ParseException {
        /*
            Method dump skipped, instructions count: 340
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: javax.mail.internet.ParameterList.combineMultisegmentNames(boolean):void");
    }

    public int size() {
        return this.list.size();
    }

    public String get(String str) {
        Object obj = this.list.get(str.trim().toLowerCase(Locale.ENGLISH));
        if (obj instanceof MultiValue) {
            return ((MultiValue) obj).value;
        }
        if (obj instanceof LiteralValue) {
            return ((LiteralValue) obj).value;
        }
        if (obj instanceof Value) {
            return ((Value) obj).value;
        }
        return (String) obj;
    }

    public void set(String str, String str2) {
        String lowerCase = str.trim().toLowerCase(Locale.ENGLISH);
        if (decodeParameters) {
            try {
                putEncodedName(lowerCase, str2);
                return;
            } catch (ParseException unused) {
                this.list.put(lowerCase, str2);
                return;
            }
        }
        this.list.put(lowerCase, str2);
    }

    public void set(String str, String str2, String str3) throws UnsupportedEncodingException {
        if (encodeParameters) {
            Value valueEncodeValue = encodeValue(str2, str3);
            if (valueEncodeValue != null) {
                this.list.put(str.trim().toLowerCase(Locale.ENGLISH), valueEncodeValue);
                return;
            } else {
                set(str, str2);
                return;
            }
        }
        set(str, str2);
    }

    void setLiteral(String str, String str2) {
        LiteralValue literalValue = new LiteralValue();
        literalValue.value = str2;
        this.list.put(str, literalValue);
    }

    public void remove(String str) {
        this.list.remove(str.trim().toLowerCase(Locale.ENGLISH));
    }

    public Enumeration<String> getNames() {
        return new ParamEnum(this.list.keySet().iterator());
    }

    public String toString() {
        return toString(0);
    }

    public String toString(int i) {
        String str;
        String str2;
        ToStringBuffer toStringBuffer = new ToStringBuffer(i);
        for (Map.Entry<String, Object> entry : this.list.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof MultiValue) {
                MultiValue multiValue = (MultiValue) value;
                String str3 = key + "*";
                for (int i2 = 0; i2 < multiValue.size(); i2++) {
                    Object obj = multiValue.get(i2);
                    if (obj instanceof Value) {
                        str = str3 + i2 + "*";
                        str2 = ((Value) obj).encodedValue;
                    } else {
                        str = str3 + i2;
                        str2 = (String) obj;
                    }
                    toStringBuffer.addNV(str, quote(str2));
                }
            } else if (value instanceof LiteralValue) {
                toStringBuffer.addNV(key, quote(((LiteralValue) value).value));
            } else if (value instanceof Value) {
                toStringBuffer.addNV(key + "*", quote(((Value) value).encodedValue));
            } else {
                String strSubstring = (String) value;
                if (strSubstring.length() > 60 && splitLongParameters && encodeParameters) {
                    String str4 = key + "*";
                    int i3 = 0;
                    while (strSubstring.length() > 60) {
                        toStringBuffer.addNV(str4 + i3, quote(strSubstring.substring(0, 60)));
                        strSubstring = strSubstring.substring(60);
                        i3++;
                    }
                    if (strSubstring.length() > 0) {
                        toStringBuffer.addNV(str4 + i3, quote(strSubstring));
                    }
                } else {
                    toStringBuffer.addNV(key, quote(strSubstring));
                }
            }
        }
        return toStringBuffer.toString();
    }

    private static class ToStringBuffer {
        private StringBuilder sb = new StringBuilder();
        private int used;

        public ToStringBuffer(int i) {
            this.used = i;
        }

        public void addNV(String str, String str2) {
            this.sb.append("; ");
            this.used += 2;
            if (this.used + str.length() + str2.length() + 1 > 76) {
                this.sb.append("\r\n\t");
                this.used = 8;
            }
            StringBuilder sb = this.sb;
            sb.append(str);
            sb.append('=');
            int length = this.used + str.length() + 1;
            this.used = length;
            if (length + str2.length() > 76) {
                String strFold = MimeUtility.fold(this.used, str2);
                this.sb.append(strFold);
                if (strFold.lastIndexOf(10) >= 0) {
                    this.used += (strFold.length() - r5) - 1;
                    return;
                } else {
                    this.used += strFold.length();
                    return;
                }
            }
            this.sb.append(str2);
            this.used += str2.length();
        }

        public String toString() {
            return this.sb.toString();
        }
    }

    private static String quote(String str) {
        return MimeUtility.quote(str, HeaderTokenizer.MIME);
    }

    private static Value encodeValue(String str, String str2) throws UnsupportedEncodingException {
        if (MimeUtility.checkAscii(str) == 1) {
            return null;
        }
        try {
            byte[] bytes = str.getBytes(MimeUtility.javaCharset(str2));
            StringBuffer stringBuffer = new StringBuffer(bytes.length + str2.length() + 2);
            stringBuffer.append(str2);
            stringBuffer.append("''");
            for (byte b : bytes) {
                char c = (char) (b & 255);
                if (c <= ' ' || c >= 127 || c == '*' || c == '\'' || c == '%' || HeaderTokenizer.MIME.indexOf(c) >= 0) {
                    stringBuffer.append('%');
                    stringBuffer.append(hex[c >> 4]);
                    stringBuffer.append(hex[c & 15]);
                } else {
                    stringBuffer.append(c);
                }
            }
            Value value = new Value();
            value.charset = str2;
            value.value = str;
            value.encodedValue = stringBuffer.toString();
            return value;
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    private static Value extractCharset(String str) throws ParseException {
        int iIndexOf;
        Value value = new Value();
        value.encodedValue = str;
        value.value = str;
        try {
            iIndexOf = str.indexOf(39);
        } catch (NumberFormatException e) {
            if (decodeParametersStrict) {
                throw new ParseException(e.toString());
            }
        } catch (StringIndexOutOfBoundsException e2) {
            if (decodeParametersStrict) {
                throw new ParseException(e2.toString());
            }
        }
        if (iIndexOf < 0) {
            if (!decodeParametersStrict) {
                return value;
            }
            throw new ParseException("Missing charset in encoded value: " + str);
        }
        String strSubstring = str.substring(0, iIndexOf);
        int iIndexOf2 = str.indexOf(39, iIndexOf + 1);
        if (iIndexOf2 < 0) {
            if (!decodeParametersStrict) {
                return value;
            }
            throw new ParseException("Missing language in encoded value: " + str);
        }
        value.value = str.substring(iIndexOf2 + 1);
        value.charset = strSubstring;
        return value;
    }

    private static String decodeBytes(String str, String str2) throws ParseException, UnsupportedEncodingException {
        byte[] bArr = new byte[str.length()];
        int i = 0;
        int i2 = 0;
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '%') {
                try {
                    cCharAt = (char) Integer.parseInt(str.substring(i + 1, i + 3), 16);
                    i += 2;
                } catch (NumberFormatException e) {
                    if (decodeParametersStrict) {
                        throw new ParseException(e.toString());
                    }
                } catch (StringIndexOutOfBoundsException e2) {
                    if (decodeParametersStrict) {
                        throw new ParseException(e2.toString());
                    }
                }
            }
            bArr[i2] = (byte) cCharAt;
            i++;
            i2++;
        }
        if (str2 != null) {
            str2 = MimeUtility.javaCharset(str2);
        }
        if (str2 == null || str2.length() == 0) {
            str2 = MimeUtility.getDefaultJavaCharset();
        }
        return new String(bArr, 0, i2, str2);
    }

    private static void decodeBytes(String str, OutputStream outputStream) throws ParseException, IOException {
        int i = 0;
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '%') {
                try {
                    cCharAt = (char) Integer.parseInt(str.substring(i + 1, i + 3), 16);
                    i += 2;
                } catch (NumberFormatException e) {
                    if (decodeParametersStrict) {
                        throw new ParseException(e.toString());
                    }
                } catch (StringIndexOutOfBoundsException e2) {
                    if (decodeParametersStrict) {
                        throw new ParseException(e2.toString());
                    }
                }
            }
            outputStream.write((byte) cCharAt);
            i++;
        }
    }
}
