package com.huawei.agconnect.applinking;

import android.content.Context;
import com.huawei.hmf.tasks.Task;

/* loaded from: classes2.dex */
public interface ReferrerProvider {
    Task<String> getCustomReferrer(Context context);
}
