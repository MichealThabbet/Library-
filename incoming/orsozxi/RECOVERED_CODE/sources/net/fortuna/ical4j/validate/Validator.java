package net.fortuna.ical4j.validate;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.function.Predicate;
import org.slf4j.LoggerFactory;

/* loaded from: classes3.dex */
public interface Validator<T> extends Serializable {
    void validate(T t) throws ValidationException;

    /* renamed from: net.fortuna.ical4j.validate.Validator$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static <T> void assertFalse(Predicate<T> predicate, String str, boolean z, T t, Object... objArr) {
            if (predicate.test(t)) {
                if (z) {
                    LoggerFactory.getLogger((Class<?>) Validator.class).warn(MessageFormat.format(str, objArr));
                    return;
                }
                throw new ValidationException(str, objArr);
            }
        }
    }
}
