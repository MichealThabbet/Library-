package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.cloudmessaging.CloudMessage;
import com.google.android.gms.cloudmessaging.CloudMessagingReceiver;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.messaging.Constants;
import com.google.firebase.messaging.FcmBroadcastProcessor;
import com.google.firebase.messaging.MessagingAnalytics;
import java.util.concurrent.ExecutionException;

/* compiled from: com.google.firebase:firebase-messaging@@21.1.0 */
/* loaded from: classes2.dex */
public final class FirebaseInstanceIdReceiver extends CloudMessagingReceiver {
    private static Intent zzb(Context context, String str, Bundle bundle) {
        return new Intent(str).putExtras(bundle);
    }

    @Override // com.google.android.gms.cloudmessaging.CloudMessagingReceiver
    protected final int onMessageReceive(Context context, CloudMessage cloudMessage) {
        try {
            return ((Integer) Tasks.await(new FcmBroadcastProcessor(context).process(cloudMessage.getIntent()))).intValue();
        } catch (InterruptedException | ExecutionException e) {
            Log.e(Constants.TAG, "Failed to send message to service.", e);
            return 500;
        }
    }

    @Override // com.google.android.gms.cloudmessaging.CloudMessagingReceiver
    protected final void onNotificationDismissed(Context context, Bundle bundle) {
        Intent intentZzb = zzb(context, CloudMessagingReceiver.IntentActionKeys.NOTIFICATION_DISMISS, bundle);
        if (MessagingAnalytics.shouldUploadScionMetrics(intentZzb)) {
            MessagingAnalytics.logNotificationDismiss(intentZzb);
        }
    }

    @Override // com.google.android.gms.cloudmessaging.CloudMessagingReceiver
    protected final void onNotificationOpen(Context context, Bundle bundle) {
        Intent intentZzb = zzb(context, CloudMessagingReceiver.IntentActionKeys.NOTIFICATION_OPEN, bundle);
        if (MessagingAnalytics.shouldUploadScionMetrics(intentZzb)) {
            MessagingAnalytics.logNotificationOpen(intentZzb);
        }
    }
}
