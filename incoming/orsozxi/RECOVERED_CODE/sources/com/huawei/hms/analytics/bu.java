package com.huawei.hms.analytics;

import android.text.TextUtils;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.framework.policy.IStoragePolicy;
import java.io.File;
import java.util.concurrent.TimeUnit;
import net.fortuna.ical4j.util.Dates;

/* loaded from: classes2.dex */
public final class bu implements IStoragePolicy {
    protected String lmn;

    /* renamed from: com.huawei.hms.analytics.bu$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] lmn;

        static {
            int[] iArr = new int[IStoragePolicy.PolicyType.values().length];
            lmn = iArr;
            try {
                iArr[IStoragePolicy.PolicyType.STORAGELENGTH.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                lmn[IStoragePolicy.PolicyType.NETWORK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                lmn[IStoragePolicy.PolicyType.PARAMS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                lmn[IStoragePolicy.PolicyType.STORAGECYCLY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                lmn[IStoragePolicy.PolicyType.STORAGESIZE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public bu() {
    }

    public bu(String str) {
        this.lmn = str;
    }

    @Override // com.huawei.hms.analytics.framework.policy.IStoragePolicy
    public final boolean decide(IStoragePolicy.PolicyType policyType, long j) {
        int i = AnonymousClass1.lmn[policyType.ordinal()];
        return i != 4 ? i == 5 && j >= ar.lmn().lmn.c : System.currentTimeMillis() - j > Dates.MILLIS_PER_WEEK;
    }

    @Override // com.huawei.hms.analytics.framework.policy.IStoragePolicy
    public final boolean decide(IStoragePolicy.PolicyType policyType, String str) {
        int i = AnonymousClass1.lmn[policyType.ordinal()];
        if (i == 1) {
            return new File(ar.lmn().lmn.ghi.getDatabasePath(str).getPath()).length() > 10485760;
        }
        if (i != 2) {
            if (i != 3) {
                return true;
            }
            return lmn(str) && !TextUtils.isEmpty(cy.lmn().klm());
        }
        if (!TextUtils.isEmpty(dg.cde(ar.lmn().lmn.ghi))) {
            return true;
        }
        HiLog.w("ReportPolicy", "The network is unavailable.");
        return false;
    }

    public final boolean lmn(String str) throws InterruptedException {
        Cdo cdo;
        if (dn.klm()) {
            try {
                dn.lmn().await(3L, TimeUnit.SECONDS);
            } catch (InterruptedException unused) {
                HiLog.w("ReportPolicy", "Interrupted Exception");
            }
            cdo = new Cdo(new bz(this.lmn, str));
        } else {
            dn.ikl();
            cdo = new Cdo(dn.lmn(), new bz(this.lmn, str));
        }
        return !TextUtils.isEmpty(cdo.lmn());
    }
}
