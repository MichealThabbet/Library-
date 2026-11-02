package org.acra.util;

import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.acra.collections.BoundedLinkedList;

/* loaded from: classes3.dex */
public class StreamReader {
    private static final int INDEFINITE = -1;
    private static final int NO_LIMIT = -1;
    private Predicate<String> filter;
    private final InputStream inputStream;
    private int limit;
    private int timeout;

    public StreamReader(String str) throws FileNotFoundException {
        this(new File(str));
    }

    public StreamReader(File file) throws FileNotFoundException {
        this(new FileInputStream(file));
    }

    public StreamReader(InputStream inputStream) {
        this.limit = -1;
        this.timeout = -1;
        this.filter = null;
        this.inputStream = inputStream;
    }

    public StreamReader setLimit(int i) {
        this.limit = i;
        return this;
    }

    public StreamReader setTimeout(int i) {
        this.timeout = i;
        return this;
    }

    public StreamReader setFilter(Predicate<String> predicate) {
        this.filter = predicate;
        return this;
    }

    public String read() throws IOException {
        String fully = this.timeout == -1 ? readFully() : readWithTimeout();
        if (this.filter == null) {
            if (this.limit == -1) {
                return fully;
            }
            String[] strArrSplit = fully.split("\\r?\\n");
            int length = strArrSplit.length;
            int i = this.limit;
            return length <= i ? fully : TextUtils.join("\n", Arrays.copyOfRange(strArrSplit, strArrSplit.length - i, strArrSplit.length));
        }
        String[] strArrSplit2 = fully.split("\\r?\\n");
        List linkedList = this.limit == -1 ? new LinkedList() : new BoundedLinkedList(this.limit);
        for (String str : strArrSplit2) {
            if (this.filter.apply(str)) {
                linkedList.add(str);
            }
        }
        return TextUtils.join("\n", linkedList);
    }

    private String readFully() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(this.inputStream);
        try {
            StringWriter stringWriter = new StringWriter();
            char[] cArr = new char[8192];
            while (true) {
                int i = inputStreamReader.read(cArr);
                if (i != -1) {
                    stringWriter.write(cArr, 0, i);
                } else {
                    return stringWriter.toString();
                }
            }
        } finally {
            IOUtils.safeClose(inputStreamReader);
        }
    }

    private String readWithTimeout() throws IOException {
        long jCurrentTimeMillis = System.currentTimeMillis() + this.timeout;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[8192];
            while (true) {
                int iFillBufferUntil = fillBufferUntil(bArr, jCurrentTimeMillis);
                if (iFillBufferUntil != -1) {
                    byteArrayOutputStream.write(bArr, 0, iFillBufferUntil);
                } else {
                    return byteArrayOutputStream.toString();
                }
            }
        } finally {
            IOUtils.safeClose(this.inputStream);
        }
    }

    private int fillBufferUntil(byte[] bArr, long j) throws IOException {
        int i = 0;
        while (System.currentTimeMillis() < j && i < bArr.length) {
            InputStream inputStream = this.inputStream;
            int i2 = inputStream.read(bArr, i, Math.min(inputStream.available(), bArr.length - i));
            if (i2 == -1) {
                break;
            }
            i += i2;
        }
        return i;
    }
}
