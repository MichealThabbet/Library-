package com.huawei.agconnect.applinking;

import com.huawei.agconnect.exception.AGCException;

/* loaded from: classes2.dex */
public class AppLinkingException extends AGCException {
    public static final int AG_REDIRECTION_URI_CHECK_FAILED = 204865683;
    public static final int ANDROID_DEEPLINK_URI_CHECK_FAILED = 204865619;
    public static final int ANDROID_DEEP_LINK_ILLEGAL = 204865555;
    public static final int ANDROID_FALLBACK_URL_EMPTY = 204865616;
    public static final int ANDROID_FALLBACK_URL_ILLEGAL = 204865542;
    public static final int ANDROID_FALLBACK_URL_SHOULD_EMPTY = 204865617;
    public static final int ANDROID_PACKAGENAME_EMPTY = 204865588;
    public static final int ANDROID_PACKAGENAME_NOT_PERMITTED = 204865584;
    public static final int CLICKID_URL_EMPTY = 204865632;
    public static final int CLICKID_URL_NOT_EMPTY = 204865622;
    public static final int DEEPLINK_URI_CHECK_FAILED = 204865573;
    public static final int DEEP_LINK_EMPTY = 204865589;
    public static final int DEEP_LINK_ILLEGAL = 204865540;
    public static final int DEEP_LINK_OPEN_FAIL = 106;
    public static final int DEFAULT_REDIRECTION_URI_CHECK_FAILED = 204865681;
    public static final int EXPIRE_TIME_ILLEGAL = 204865608;
    public static final int GENERATE_SHORTCODE_FAILED = 204865577;
    public static final int INTENT_DATA_NULL = 1;
    public static final int INVALID_URL = 204865536;
    public static final int IOS_BUNDLEID_EMPTY = 204865652;
    public static final int IOS_BUNDLEID_NOT_PERMITTED = 204865654;
    public static final int IOS_DEEPLINK_ILLEGAL = 204865657;
    public static final int IOS_DEEPLINK_URI_CHECK_FAILED = 204865637;
    public static final int IOS_FALLBACK_URL_ILLEGAL = 204865650;
    public static final int IOS_REDIRECTION_URI_CHECK_FAILED = 204865682;
    public static final int IPAD_BUNDLEID_EMPTY = 204865653;
    public static final int IPAD_BUNDLEID_NOT_PERMITTED = 204865655;
    public static final int IPAD_FALLBACK_URL_ILLEGAL = 204865651;
    public static final int LINK_BLOCKED = 204865605;
    public static final int LINK_EXCEED_LIMIT = 204865625;
    public static final int LINK_ILLEGAL = 204865539;
    public static final int LINK_NOT_EXIST = 204865624;
    public static final int MALICIOUS_URL = 204865648;
    public static final int NOT_FIND_CLICK_ID = 103;
    public static final int NOT_FIND_CLICK_ID_FROM_CLIPBOARD = 107;
    public static final int OPEN_TYPE_ILLEGAL = 204865633;
    public static final int OTHER_REDIRECTION_URI_CHECK_FAILED = 204865684;
    public static final int PAGE_TYPE_ILLEGAL = 204865665;
    public static final int PREVIEW_TYPE_ILLEGAL = 204865634;
    public static final int PRODUCTID_NOT_PERMITTED = 204865545;
    public static final int SAFETY_DETECT_ERROR = 204865649;
    public static final int SOCIAL_IMAGE_ILLEGAL = 204865541;
    public static final int URI_PREFIX_DELETED = 204865575;
    public static final int URI_PREFIX_NOT_FOUND = 204865576;

    public AppLinkingException(String str, int i) {
        super(str, i);
    }
}
