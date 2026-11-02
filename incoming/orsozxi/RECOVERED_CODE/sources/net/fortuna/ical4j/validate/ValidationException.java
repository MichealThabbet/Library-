package net.fortuna.ical4j.validate;

import java.text.MessageFormat;

/* loaded from: classes3.dex */
public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = 309245291364742896L;

    public ValidationException() {
    }

    public ValidationException(String str) {
        super(str);
    }

    public ValidationException(String str, Object[] objArr) {
        super(MessageFormat.format(str, objArr));
    }
}
