package org.acra.interaction;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import java.io.File;
import org.acra.config.ConfigUtils;
import org.acra.config.CoreConfiguration;
import org.acra.config.ToastConfiguration;
import org.acra.plugins.HasConfigPlugin;
import org.acra.util.ToastSender;

/* loaded from: classes3.dex */
public class ToastInteraction extends HasConfigPlugin implements ReportInteraction {
    private static final int TOAST_WAIT_DURATION = 2000;

    public ToastInteraction() {
        super(ToastConfiguration.class);
    }

    @Override // org.acra.interaction.ReportInteraction
    public boolean performInteraction(Context context, CoreConfiguration coreConfiguration, File file) {
        Looper.prepare();
        ToastSender.sendToast(context, ((ToastConfiguration) ConfigUtils.getPluginConfiguration(coreConfiguration, ToastConfiguration.class)).text(), 1);
        final Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper != null) {
            new Handler(looperMyLooper).postDelayed(new Runnable() { // from class: org.acra.interaction.-$$Lambda$ToastInteraction$afVu66sGGyHAdTUTS09ziguvN2I
                @Override // java.lang.Runnable
                public final void run() {
                    ToastInteraction.lambda$performInteraction$0(looperMyLooper);
                }
            }, 2000L);
            Looper.loop();
        }
        return true;
    }

    static /* synthetic */ void lambda$performInteraction$0(Looper looper) {
        if (Build.VERSION.SDK_INT >= 18) {
            looper.quitSafely();
        } else {
            looper.quit();
        }
    }
}
