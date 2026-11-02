package com.huawei.hms.audiokit.player.manager.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.huawei.hms.api.service.IServiceBinder;
import com.huawei.hms.audiokit.b;
import com.huawei.hms.feature.dynamic.ObjectWrapper;

/* loaded from: classes2.dex */
public class MediaPlaybackSDKService extends Service {
    private IServiceBinder a;

    private void a() throws IllegalAccessException, InstantiationException {
        try {
            if (this.a != null) {
                return;
            }
            Context contextB = b.b();
            if (contextB == null) {
                Log.e("RemoteService", "remoteContext == null!");
                return;
            }
            Object objNewInstance = contextB.getClassLoader().loadClass("com.huawei.hms.audiokit.service.IServiceBinderImp").newInstance();
            if (objNewInstance instanceof IBinder) {
                this.a = IServiceBinder.Stub.asInterface((IBinder) objNewInstance);
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            Log.e("RemoteService", e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) throws IllegalAccessException, InstantiationException {
        Log.i("RemoteService", "onBind");
        a();
        IServiceBinder iServiceBinder = this.a;
        if (iServiceBinder == null) {
            return null;
        }
        try {
            return iServiceBinder.onBind(ObjectWrapper.wrap(this));
        } catch (RemoteException e) {
            Log.e("RemoteService", e.getClass().getSimpleName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) throws IllegalAccessException, InstantiationException {
        super.onConfigurationChanged(configuration);
        a();
        IServiceBinder iServiceBinder = this.a;
        if (iServiceBinder != null) {
            try {
                iServiceBinder.onConfigurationChanged(ObjectWrapper.wrap(configuration));
            } catch (RemoteException e) {
                Log.e("RemoteService", e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
    }

    @Override // android.app.Service
    public void onCreate() throws IllegalAccessException, InstantiationException {
        super.onCreate();
        a();
        IServiceBinder iServiceBinder = this.a;
        if (iServiceBinder != null) {
            try {
                iServiceBinder.onCreate(ObjectWrapper.wrap(this));
            } catch (RemoteException e) {
                Log.e("RemoteService", e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
    }

    @Override // android.app.Service
    public void onDestroy() throws IllegalAccessException, InstantiationException {
        super.onDestroy();
        a();
        IServiceBinder iServiceBinder = this.a;
        if (iServiceBinder != null) {
            try {
                iServiceBinder.onDestroy();
            } catch (RemoteException e) {
                Log.e("RemoteService", e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
    }

    @Override // android.app.Service
    public void onRebind(Intent intent) throws IllegalAccessException, InstantiationException {
        super.onRebind(intent);
        a();
        IServiceBinder iServiceBinder = this.a;
        if (iServiceBinder != null) {
            try {
                iServiceBinder.onRebind(ObjectWrapper.wrap(intent));
            } catch (RemoteException e) {
                Log.e("RemoteService", e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) throws IllegalAccessException, InstantiationException {
        super.onStartCommand(intent, i, i2);
        a();
        IServiceBinder iServiceBinder = this.a;
        if (iServiceBinder == null) {
            return 2;
        }
        try {
            return iServiceBinder.onStartCommand(ObjectWrapper.wrap(intent), i, i2);
        } catch (RemoteException e) {
            Log.e("RemoteService", e.getClass().getSimpleName() + ": " + e.getMessage());
            return 2;
        }
    }

    @Override // android.app.Service
    public void onTaskRemoved(Intent intent) throws IllegalAccessException, InstantiationException {
        super.onTaskRemoved(intent);
        a();
        IServiceBinder iServiceBinder = this.a;
        if (iServiceBinder != null) {
            try {
                iServiceBinder.onTaskRemoved(ObjectWrapper.wrap(intent));
            } catch (RemoteException e) {
                Log.e("RemoteService", e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) throws IllegalAccessException, InstantiationException {
        Log.i("RemoteService", "onUnbind");
        super.onUnbind(intent);
        a();
        IServiceBinder iServiceBinder = this.a;
        if (iServiceBinder == null) {
            return true;
        }
        try {
            iServiceBinder.onUnbind();
            return true;
        } catch (Exception e) {
            Log.e("RemoteService", "onUnbind:" + e.getMessage());
            return true;
        }
    }
}
