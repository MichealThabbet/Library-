package com.sun.activation.registries;

import com.facebook.share.internal.ShareConstants;
import com.huawei.hms.framework.common.ContainerUtils;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Hashtable;
import java.util.StringTokenizer;

/* loaded from: classes2.dex */
public class MimeTypeFile {
    private String fname;
    private Hashtable type_hash;

    public MimeTypeFile(String str) throws IOException {
        this.fname = null;
        this.type_hash = new Hashtable();
        this.fname = str;
        FileReader fileReader = new FileReader(new File(this.fname));
        try {
            parse(new BufferedReader(fileReader));
        } finally {
            try {
                fileReader.close();
            } catch (IOException unused) {
            }
        }
    }

    public MimeTypeFile(InputStream inputStream) throws IOException {
        this.fname = null;
        this.type_hash = new Hashtable();
        parse(new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1")));
    }

    public MimeTypeFile() {
        this.fname = null;
        this.type_hash = new Hashtable();
    }

    public MimeTypeEntry getMimeTypeEntry(String str) {
        return (MimeTypeEntry) this.type_hash.get(str);
    }

    public String getMIMETypeString(String str) {
        MimeTypeEntry mimeTypeEntry = getMimeTypeEntry(str);
        if (mimeTypeEntry != null) {
            return mimeTypeEntry.getMIMEType();
        }
        return null;
    }

    public void appendToRegistry(String str) {
        try {
            parse(new BufferedReader(new StringReader(str)));
        } catch (IOException unused) {
        }
    }

    private void parse(BufferedReader bufferedReader) throws IOException {
        String strSubstring;
        String line;
        loop0: while (true) {
            strSubstring = null;
            while (true) {
                line = bufferedReader.readLine();
                if (line == null) {
                    break loop0;
                }
                if (strSubstring != null) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(strSubstring);
                    stringBuffer.append(line);
                    line = stringBuffer.toString();
                }
                int length = line.length();
                if (line.length() > 0) {
                    int i = length - 1;
                    if (line.charAt(i) == '\\') {
                        strSubstring = line.substring(0, i);
                    }
                }
            }
            parseEntry(line);
        }
        if (strSubstring != null) {
            parseEntry(strSubstring);
        }
    }

    private void parseEntry(String str) {
        String strTrim = str.trim();
        if (strTrim.length() == 0 || strTrim.charAt(0) == '#') {
            return;
        }
        if (strTrim.indexOf(61) > 0) {
            LineTokenizer lineTokenizer = new LineTokenizer(strTrim);
            String str2 = null;
            while (lineTokenizer.hasMoreTokens()) {
                String strNextToken = lineTokenizer.nextToken();
                String strNextToken2 = (lineTokenizer.hasMoreTokens() && lineTokenizer.nextToken().equals(ContainerUtils.KEY_VALUE_DELIMITER) && lineTokenizer.hasMoreTokens()) ? lineTokenizer.nextToken() : null;
                if (strNextToken2 == null) {
                    if (LogSupport.isLoggable()) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Bad .mime.types entry: ");
                        stringBuffer.append(strTrim);
                        LogSupport.log(stringBuffer.toString());
                        return;
                    }
                    return;
                }
                if (strNextToken.equals(ShareConstants.MEDIA_TYPE)) {
                    str2 = strNextToken2;
                } else if (strNextToken.equals("exts")) {
                    StringTokenizer stringTokenizer = new StringTokenizer(strNextToken2, ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
                    while (stringTokenizer.hasMoreTokens()) {
                        String strNextToken3 = stringTokenizer.nextToken();
                        MimeTypeEntry mimeTypeEntry = new MimeTypeEntry(str2, strNextToken3);
                        this.type_hash.put(strNextToken3, mimeTypeEntry);
                        if (LogSupport.isLoggable()) {
                            StringBuffer stringBuffer2 = new StringBuffer();
                            stringBuffer2.append("Added: ");
                            stringBuffer2.append(mimeTypeEntry.toString());
                            LogSupport.log(stringBuffer2.toString());
                        }
                    }
                }
            }
            return;
        }
        StringTokenizer stringTokenizer2 = new StringTokenizer(strTrim);
        if (stringTokenizer2.countTokens() == 0) {
            return;
        }
        String strNextToken4 = stringTokenizer2.nextToken();
        while (stringTokenizer2.hasMoreTokens()) {
            String strNextToken5 = stringTokenizer2.nextToken();
            MimeTypeEntry mimeTypeEntry2 = new MimeTypeEntry(strNextToken4, strNextToken5);
            this.type_hash.put(strNextToken5, mimeTypeEntry2);
            if (LogSupport.isLoggable()) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Added: ");
                stringBuffer3.append(mimeTypeEntry2.toString());
                LogSupport.log(stringBuffer3.toString());
            }
        }
    }
}
