package it.orsozoxi.android.orsonotes.models;

/* loaded from: classes3.dex */
public interface PasswordValidator {

    public enum Result {
        SUCCEED,
        FAIL,
        RESTORE
    }

    void onPasswordValidated(Result result);
}
