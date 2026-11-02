package com.huawei.hms.analytics.framework.policy;

/* loaded from: classes2.dex */
public interface IStoragePolicy {

    public enum PolicyType {
        NETWORK,
        STORAGECYCLY,
        STORAGELENGTH,
        STORAGESIZE,
        PARAMS
    }

    boolean decide(PolicyType policyType, long j);

    boolean decide(PolicyType policyType, String str);
}
