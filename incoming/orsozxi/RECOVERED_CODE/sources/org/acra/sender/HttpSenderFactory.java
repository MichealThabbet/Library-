package org.acra.sender;

import android.content.Context;
import org.acra.config.CoreConfiguration;
import org.acra.config.HttpSenderConfiguration;
import org.acra.plugins.HasConfigPlugin;

/* loaded from: classes3.dex */
public final class HttpSenderFactory extends HasConfigPlugin implements ReportSenderFactory {
    public HttpSenderFactory() {
        super(HttpSenderConfiguration.class);
    }

    @Override // org.acra.sender.ReportSenderFactory
    public ReportSender create(Context context, CoreConfiguration coreConfiguration) {
        return new HttpSender(coreConfiguration, null, null);
    }
}
