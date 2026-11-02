package org.acra.config;

import java.util.List;
import org.acra.config.RetryPolicy;
import org.acra.sender.ReportSender;

/* loaded from: classes3.dex */
public class DefaultRetryPolicy implements RetryPolicy {
    @Override // org.acra.config.RetryPolicy
    public boolean shouldRetrySend(List<ReportSender> list, List<RetryPolicy.FailedSender> list2) {
        return list.size() == list2.size() && !list.isEmpty();
    }
}
