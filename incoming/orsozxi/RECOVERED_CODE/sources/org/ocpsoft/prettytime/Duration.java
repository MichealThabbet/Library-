package org.ocpsoft.prettytime;

/* loaded from: classes3.dex */
public interface Duration {
    long getDelta();

    long getQuantity();

    long getQuantityRounded(int i);

    TimeUnit getUnit();

    boolean isInFuture();

    boolean isInPast();
}
