package com.ibm.icu.impl.data;

import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.UTF16;
import java.io.IOException;

/* loaded from: classes2.dex */
public class TokenIterator {
    private ResourceReader reader;
    private String line = null;
    private boolean done = false;
    private StringBuffer buf = new StringBuffer();
    private int lastpos = -1;
    private int pos = -1;

    public TokenIterator(ResourceReader resourceReader) {
        this.reader = resourceReader;
    }

    public String next() throws IOException {
        if (this.done) {
            return null;
        }
        while (true) {
            if (this.line == null) {
                String lineSkippingComments = this.reader.readLineSkippingComments();
                this.line = lineSkippingComments;
                if (lineSkippingComments == null) {
                    this.done = true;
                    return null;
                }
                this.pos = 0;
            }
            this.buf.setLength(0);
            int i = this.pos;
            this.lastpos = i;
            int iNextToken = nextToken(i);
            this.pos = iNextToken;
            if (iNextToken < 0) {
                this.line = null;
            } else {
                return this.buf.toString();
            }
        }
    }

    public int getLineNumber() {
        return this.reader.getLineNumber();
    }

    public String describePosition() {
        return this.reader.describePosition() + ':' + (this.lastpos + 1);
    }

    private int nextToken(int i) {
        int iSkipWhiteSpace = PatternProps.skipWhiteSpace(this.line, i);
        if (iSkipWhiteSpace == this.line.length()) {
            return -1;
        }
        int i2 = iSkipWhiteSpace + 1;
        char cCharAt = this.line.charAt(iSkipWhiteSpace);
        if (cCharAt != '\"') {
            if (cCharAt == '#') {
                return -1;
            }
            if (cCharAt != '\'') {
                this.buf.append(cCharAt);
                cCharAt = 0;
            }
        }
        int[] iArr = null;
        while (i2 < this.line.length()) {
            char cCharAt2 = this.line.charAt(i2);
            if (cCharAt2 == '\\') {
                if (iArr == null) {
                    iArr = new int[1];
                }
                iArr[0] = i2 + 1;
                int iUnescapeAt = Utility.unescapeAt(this.line, iArr);
                if (iUnescapeAt < 0) {
                    throw new RuntimeException("Invalid escape at " + this.reader.describePosition() + ':' + i2);
                }
                UTF16.append(this.buf, iUnescapeAt);
                i2 = iArr[0];
            } else {
                if ((cCharAt != 0 && cCharAt2 == cCharAt) || (cCharAt == 0 && PatternProps.isWhiteSpace(cCharAt2))) {
                    return i2 + 1;
                }
                if (cCharAt == 0 && cCharAt2 == '#') {
                    return i2;
                }
                this.buf.append(cCharAt2);
                i2++;
            }
        }
        if (cCharAt == 0) {
            return i2;
        }
        throw new RuntimeException("Unterminated quote at " + this.reader.describePosition() + ':' + iSkipWhiteSpace);
    }
}
