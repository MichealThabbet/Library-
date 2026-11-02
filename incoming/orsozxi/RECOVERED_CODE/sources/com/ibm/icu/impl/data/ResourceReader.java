package com.ibm.icu.impl.data;

import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.PatternProps;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/* loaded from: classes2.dex */
public class ResourceReader implements Closeable {
    private String encoding;
    private int lineNo;
    private BufferedReader reader;
    private String resourceName;
    private Class<?> root;

    public ResourceReader(String str, String str2) throws UnsupportedEncodingException {
        this((Class<?>) ICUData.class, "data/" + str, str2);
    }

    public ResourceReader(String str) {
        this((Class<?>) ICUData.class, "data/" + str);
    }

    public ResourceReader(Class<?> cls, String str, String str2) throws UnsupportedEncodingException {
        this.reader = null;
        this.root = cls;
        this.resourceName = str;
        this.encoding = str2;
        this.lineNo = -1;
        _reset();
    }

    public ResourceReader(InputStream inputStream, String str, String str2) {
        this.reader = null;
        this.root = null;
        this.resourceName = str;
        this.encoding = str2;
        this.lineNo = -1;
        try {
            this.reader = new BufferedReader(str2 == null ? new InputStreamReader(inputStream) : new InputStreamReader(inputStream, str2));
            this.lineNo = 0;
        } catch (UnsupportedEncodingException unused) {
        }
    }

    public ResourceReader(InputStream inputStream, String str) {
        this(inputStream, str, (String) null);
    }

    public ResourceReader(Class<?> cls, String str) {
        this.reader = null;
        this.root = cls;
        this.resourceName = str;
        this.encoding = null;
        this.lineNo = -1;
        try {
            _reset();
        } catch (UnsupportedEncodingException unused) {
        }
    }

    public String readLine() throws IOException {
        int i = this.lineNo;
        if (i == 0) {
            this.lineNo = i + 1;
            String line = this.reader.readLine();
            return line != null ? (line.charAt(0) == 65519 || line.charAt(0) == 65279) ? line.substring(1) : line : line;
        }
        this.lineNo = i + 1;
        return this.reader.readLine();
    }

    public String readLineSkippingComments(boolean z) throws IOException {
        while (true) {
            String line = readLine();
            if (line == null) {
                return line;
            }
            int iSkipWhiteSpace = PatternProps.skipWhiteSpace(line, 0);
            if (iSkipWhiteSpace != line.length() && line.charAt(iSkipWhiteSpace) != '#') {
                return z ? line.substring(iSkipWhiteSpace) : line;
            }
        }
    }

    public String readLineSkippingComments() throws IOException {
        return readLineSkippingComments(false);
    }

    public int getLineNumber() {
        return this.lineNo;
    }

    public String describePosition() {
        return this.resourceName + ':' + this.lineNo;
    }

    public void reset() {
        try {
            _reset();
        } catch (UnsupportedEncodingException unused) {
        }
    }

    private void _reset() throws UnsupportedEncodingException {
        try {
            close();
        } catch (IOException unused) {
        }
        if (this.lineNo == 0) {
            return;
        }
        InputStream stream = ICUData.getStream(this.root, this.resourceName);
        if (stream == null) {
            throw new IllegalArgumentException("Can't open " + this.resourceName);
        }
        this.reader = new BufferedReader(this.encoding == null ? new InputStreamReader(stream) : new InputStreamReader(stream, this.encoding));
        this.lineNo = 0;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        BufferedReader bufferedReader = this.reader;
        if (bufferedReader != null) {
            bufferedReader.close();
            this.reader = null;
        }
    }
}
