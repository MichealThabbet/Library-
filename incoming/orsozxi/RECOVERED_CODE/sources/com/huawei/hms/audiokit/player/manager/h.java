package com.huawei.hms.audiokit.player.manager;

import android.os.RemoteException;
import com.huawei.hms.api.config.IWiseAudioNotificationFactory;
import com.huawei.hms.api.config.NotificationConfig;
import com.huawei.hms.feature.dynamic.IObjectWrapper;
import com.huawei.hms.feature.dynamic.ObjectWrapper;

/* loaded from: classes2.dex */
class h extends IWiseAudioNotificationFactory.Stub {
    INotificationFactory a;

    public h(INotificationFactory iNotificationFactory) {
        this.a = iNotificationFactory;
    }

    @Override // com.huawei.hms.api.config.IWiseAudioNotificationFactory
    public IObjectWrapper createNotification(NotificationConfig notificationConfig) throws RemoteException {
        return ObjectWrapper.wrap(this.a.createNotification(notificationConfig));
    }
}
