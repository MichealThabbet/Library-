package org.acra.sender;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import org.acra.ACRA;
import org.acra.config.CoreConfiguration;

/* loaded from: classes3.dex */
public class LegacySenderService extends Service {
    public static final String EXTRA_ACRA_CONFIG = "acraConfig";
    public static final String EXTRA_ONLY_SEND_SILENT_REPORTS = "onlySendSilentReports";

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent.hasExtra(EXTRA_ACRA_CONFIG)) {
            final boolean booleanExtra = intent.getBooleanExtra(EXTRA_ONLY_SEND_SILENT_REPORTS, false);
            final CoreConfiguration coreConfiguration = (CoreConfiguration) intent.getSerializableExtra(EXTRA_ACRA_CONFIG);
            if (coreConfiguration == null) {
                return 3;
            }
            new Thread(new Runnable() { // from class: org.acra.sender.-$$Lambda$LegacySenderService$kX5ZrVSEQ9yv2dd3RW4qENhrpO8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onStartCommand$0$LegacySenderService(coreConfiguration, booleanExtra);
                }
            }).start();
            return 3;
        }
        if (!ACRA.DEV_LOGGING) {
            return 3;
        }
        ACRA.log.d(ACRA.LOG_TAG, "SenderService was started but no valid intent was delivered, will now quit");
        return 3;
    }

    public /* synthetic */ void lambda$onStartCommand$0$LegacySenderService(CoreConfiguration coreConfiguration, boolean z) {
        new SendingConductor(this, coreConfiguration).sendReports(z, false);
        stopSelf();
    }
}
