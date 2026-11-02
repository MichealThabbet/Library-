package com.huawei.hms.analytics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.analytics.type.HALogConfig;
import com.huawei.hms.analytics.type.ReportPolicy;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public interface bi {
    void bcd(String str);

    void cde(String str);

    void def(String str);

    void efg(String str);

    void efg(boolean z);

    Task<String> fgh();

    void fgh(String str);

    void fgh(boolean z);

    void ghi();

    void ghi(String str);

    void ghi(boolean z);

    void hij(String str);

    void hij(boolean z);

    boolean hij();

    void ijk(long j);

    void ijk(String str);

    void ijk(boolean z);

    boolean ijk();

    void ikl(long j);

    void klm(String str, String str2);

    Map<String, String> lmn(boolean z);

    void lmn(Activity activity, String str, String str2);

    void lmn(Intent intent);

    void lmn(Bundle bundle);

    void lmn(HALogConfig hALogConfig, String str);

    void lmn(String str, Bundle bundle);

    void lmn(String str, String str2);

    void lmn(String str, boolean z);

    void lmn(Set<ReportPolicy> set);
}
