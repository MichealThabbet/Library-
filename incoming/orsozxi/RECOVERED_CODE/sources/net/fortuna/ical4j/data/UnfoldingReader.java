package net.fortuna.ical4j.data;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.Arrays;
import net.fortuna.ical4j.util.CompatibilityHints;
import org.apache.http.message.TokenParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: classes3.dex */
public class UnfoldingReader extends PushbackReader {
    private static final char[] DEFAULT_FOLD_PATTERN_1 = {'\r', '\n', TokenParser.SP};
    private static final char[] DEFAULT_FOLD_PATTERN_2 = {'\r', '\n', '\t'};
    private static final char[] RELAXED_FOLD_PATTERN_1 = {'\n', TokenParser.SP};
    private static final char[] RELAXED_FOLD_PATTERN_2 = {'\n', '\t'};
    private char[][] buffers;
    private int linesUnfolded;
    private Logger log;
    private int maxPatternLength;
    private char[][] patterns;

    public UnfoldingReader(Reader reader) {
        this(reader, DEFAULT_FOLD_PATTERN_1.length, CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_UNFOLDING));
    }

    public UnfoldingReader(Reader reader, int i) {
        this(reader, i, CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_UNFOLDING));
    }

    public UnfoldingReader(Reader reader, boolean z) {
        this(reader, DEFAULT_FOLD_PATTERN_1.length, z);
    }

    public UnfoldingReader(Reader reader, int i, boolean z) {
        super(reader, i);
        this.log = LoggerFactory.getLogger((Class<?>) UnfoldingReader.class);
        int i2 = 0;
        this.maxPatternLength = 0;
        if (z) {
            this.patterns = new char[][]{DEFAULT_FOLD_PATTERN_1, DEFAULT_FOLD_PATTERN_2, RELAXED_FOLD_PATTERN_1, RELAXED_FOLD_PATTERN_2};
        } else {
            this.patterns = new char[][]{DEFAULT_FOLD_PATTERN_1, DEFAULT_FOLD_PATTERN_2};
        }
        this.buffers = new char[this.patterns.length][];
        while (true) {
            char[][] cArr = this.patterns;
            if (i2 >= cArr.length) {
                return;
            }
            this.buffers[i2] = new char[cArr[i2].length];
            this.maxPatternLength = Math.max(this.maxPatternLength, cArr[i2].length);
            i2++;
        }
    }

    public final int getLinesUnfolded() {
        return this.linesUnfolded;
    }

    @Override // java.io.PushbackReader, java.io.FilterReader, java.io.Reader
    public final int read() throws IOException {
        int i = super.read();
        char[][] cArr = this.patterns;
        int length = cArr.length;
        boolean z = false;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            if (i == cArr[i2][0]) {
                z = true;
                break;
            }
            i2++;
        }
        if (!z) {
            return i;
        }
        unread(i);
        unfold();
        return super.read();
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x002b, code lost:
    
        if (r2 != false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x002d, code lost:
    
        return r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x002e, code lost:
    
        unread(r9, r10, r11);
        unfold();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x003a, code lost:
    
        return super.read(r9, r10, r8.maxPatternLength);
     */
    @Override // java.io.PushbackReader, java.io.FilterReader, java.io.Reader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int read(char[] r9, int r10, int r11) throws java.io.IOException {
        /*
            r8 = this;
            int r11 = super.read(r9, r10, r11)
            char[][] r0 = r8.patterns
            int r1 = r0.length
            r2 = 0
            r3 = 0
        L9:
            if (r3 >= r1) goto L2b
            r4 = r0[r3]
            if (r11 <= 0) goto L17
            char r5 = r9[r2]
            char r6 = r4[r2]
            if (r5 != r6) goto L17
            r2 = 1
            goto L2b
        L17:
            r5 = 0
        L18:
            if (r5 >= r11) goto L28
            char r6 = r9[r5]
            char r7 = r4[r2]
            if (r6 != r7) goto L25
            int r11 = r11 - r5
            r8.unread(r9, r5, r11)
            return r5
        L25:
            int r5 = r5 + 1
            goto L18
        L28:
            int r3 = r3 + 1
            goto L9
        L2b:
            if (r2 != 0) goto L2e
            return r11
        L2e:
            r8.unread(r9, r10, r11)
            r8.unfold()
            int r11 = r8.maxPatternLength
            int r9 = super.read(r9, r10, r11)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: net.fortuna.ical4j.data.UnfoldingReader.read(char[], int, int):int");
    }

    private void unfold() throws IOException {
        boolean z;
        int i;
        do {
            z = false;
            for (int i2 = 0; i2 < this.buffers.length; i2++) {
                int i3 = 0;
                while (true) {
                    char[][] cArr = this.buffers;
                    if (i3 >= cArr[i2].length || (i = super.read(cArr[i2], i3, cArr[i2].length - i3)) < 0) {
                        break;
                    } else {
                        i3 += i;
                    }
                }
                if (i3 > 0) {
                    if (!Arrays.equals(this.patterns[i2], this.buffers[i2])) {
                        unread(this.buffers[i2], 0, i3);
                    } else {
                        if (this.log.isTraceEnabled()) {
                            this.log.trace("Unfolding...");
                        }
                        this.linesUnfolded++;
                        z = true;
                    }
                }
            }
        } while (z);
    }
}
