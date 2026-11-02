package net.fortuna.ical4j.data;

import java.text.MessageFormat;

/* loaded from: classes3.dex */
public class ParserException extends Exception {
    private static final String ERROR_MESSAGE_PATTERN = "Error at line {0}:";
    private static final long serialVersionUID = 6116644246112002214L;
    private int lineNo;

    public ParserException(int i) {
        this.lineNo = i;
    }

    public ParserException(String str, int i) {
        super(MessageFormat.format(ERROR_MESSAGE_PATTERN, Integer.valueOf(i)) + str);
        this.lineNo = i;
    }

    public ParserException(String str, int i, Throwable th) {
        super(MessageFormat.format(ERROR_MESSAGE_PATTERN, Integer.valueOf(i)) + str, th);
        this.lineNo = i;
    }

    public final int getLineNo() {
        return this.lineNo;
    }
}
