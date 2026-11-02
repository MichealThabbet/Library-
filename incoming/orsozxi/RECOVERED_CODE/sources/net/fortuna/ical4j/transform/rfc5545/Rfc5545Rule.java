package net.fortuna.ical4j.transform.rfc5545;

/* loaded from: classes3.dex */
public interface Rfc5545Rule<T> {
    void applyTo(T t);

    Class<T> getSupportedType();
}
