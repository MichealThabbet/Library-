package com.robohorse.gpversionchecker.domain;

/* loaded from: classes2.dex */
public interface VersionInfoListener {
    void onErrorHandled(Throwable th);

    void onResulted(Version version);
}
