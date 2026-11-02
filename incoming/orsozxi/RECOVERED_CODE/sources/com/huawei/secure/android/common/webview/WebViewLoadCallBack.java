package com.huawei.secure.android.common.webview;

/* loaded from: classes2.dex */
public interface WebViewLoadCallBack {

    public enum ErrorCode {
        HTTP_URL,
        URL_NOT_IN_WHITE_LIST,
        OTHER
    }

    void onCheckError(String str, ErrorCode errorCode);
}
