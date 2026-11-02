package com.huawei.hms.analytics;

import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public interface am {
    public static final Pattern lmn = Pattern.compile("^[a-zA-Z0-9_-]+(\\s+[a-zA-Z0-9_-]+)*$");
    public static final Pattern klm = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*|[\\$][a-zA-Z0-9]*");
    public static final Pattern ikl = Pattern.compile("[a-zA-Z][a-zA-Z0-9_]*|[\\$][a-zA-Z0-9]*");
}
