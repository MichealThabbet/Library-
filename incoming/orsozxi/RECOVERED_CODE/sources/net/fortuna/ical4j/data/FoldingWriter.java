package net.fortuna.ical4j.data;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import org.apache.http.message.TokenParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: classes3.dex */
public class FoldingWriter extends FilterWriter {
    private static final char[] FOLD_PATTERN = {'\r', '\n', TokenParser.SP};
    public static final int MAX_FOLD_LENGTH = 75;
    public static final int REDUCED_FOLD_LENGTH = 73;
    private final int foldLength;
    private int lineLength;
    private final Logger log;

    public FoldingWriter(Writer writer, int i) {
        super(writer);
        this.log = LoggerFactory.getLogger((Class<?>) FoldingWriter.class);
        this.foldLength = Math.min(i, 75);
    }

    public FoldingWriter(Writer writer) {
        this(writer, 73);
    }

    @Override // java.io.FilterWriter, java.io.Writer
    public final void write(int i) throws IOException {
        write(new char[]{(char) i}, 0, 1);
    }

    @Override // java.io.FilterWriter, java.io.Writer
    public final void write(char[] cArr, int i, int i2) throws IOException {
        int i3 = (i2 + i) - 1;
        while (i <= i3) {
            if (this.log.isTraceEnabled()) {
                this.log.trace("char [" + cArr[i] + "], line length [" + this.lineLength + "]");
            }
            if (this.lineLength >= this.foldLength) {
                char[] cArr2 = FOLD_PATTERN;
                super.write(cArr2, 0, cArr2.length);
                this.lineLength = 1;
            }
            super.write(cArr[i]);
            if (cArr[i] == '\r' || cArr[i] == '\n') {
                this.lineLength = 0;
            } else {
                this.lineLength++;
            }
            i++;
        }
    }

    @Override // java.io.FilterWriter, java.io.Writer
    public final void write(String str, int i, int i2) throws IOException {
        write(str.toCharArray(), i, i2);
    }
}
