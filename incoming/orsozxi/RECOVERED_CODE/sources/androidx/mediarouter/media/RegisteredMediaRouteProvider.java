package androidx.mediarouter.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import androidx.mediarouter.media.MediaRouteProvider;
import androidx.mediarouter.media.MediaRouter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
final class RegisteredMediaRouteProvider extends MediaRouteProvider implements ServiceConnection {
    private Connection mActiveConnection;
    private boolean mBound;
    private final ComponentName mComponentName;
    private boolean mConnectionReady;
    private final ArrayList<Controller> mControllers;
    final PrivateHandler mPrivateHandler;
    private boolean mStarted;
    static final String TAG = "MediaRouteProviderProxy";
    static final boolean DEBUG = Log.isLoggable(TAG, 3);

    public RegisteredMediaRouteProvider(Context context, ComponentName componentName) {
        super(context, new MediaRouteProvider.ProviderMetadata(componentName));
        this.mControllers = new ArrayList<>();
        this.mComponentName = componentName;
        this.mPrivateHandler = new PrivateHandler();
    }

    @Override // androidx.mediarouter.media.MediaRouteProvider
    public MediaRouteProvider.RouteController onCreateRouteController(String str) {
        if (str == null) {
            throw new IllegalArgumentException("routeId cannot be null");
        }
        return createRouteController(str, null);
    }

    @Override // androidx.mediarouter.media.MediaRouteProvider
    public MediaRouteProvider.RouteController onCreateRouteController(String str, String str2) {
        if (str == null) {
            throw new IllegalArgumentException("routeId cannot be null");
        }
        if (str2 == null) {
            throw new IllegalArgumentException("routeGroupId cannot be null");
        }
        return createRouteController(str, str2);
    }

    @Override // androidx.mediarouter.media.MediaRouteProvider
    public void onDiscoveryRequestChanged(MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest) throws RemoteException {
        if (this.mConnectionReady) {
            this.mActiveConnection.setDiscoveryRequest(mediaRouteDiscoveryRequest);
        }
        updateBinding();
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) throws RemoteException {
        if (DEBUG) {
            Log.d(TAG, this + ": Connected");
        }
        if (this.mBound) {
            disconnect();
            Messenger messenger = iBinder != null ? new Messenger(iBinder) : null;
            if (MediaRouteProviderProtocol.isValidRemoteMessenger(messenger)) {
                Connection connection = new Connection(messenger);
                if (connection.register()) {
                    this.mActiveConnection = connection;
                    return;
                } else {
                    if (DEBUG) {
                        Log.d(TAG, this + ": Registration failed");
                        return;
                    }
                    return;
                }
            }
            Log.e(TAG, this + ": Service returned invalid messenger binder");
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) throws RemoteException {
        if (DEBUG) {
            Log.d(TAG, this + ": Service disconnected");
        }
        disconnect();
    }

    public String toString() {
        return "Service connection " + this.mComponentName.flattenToShortString();
    }

    public boolean hasComponentName(String str, String str2) {
        return this.mComponentName.getPackageName().equals(str) && this.mComponentName.getClassName().equals(str2);
    }

    public void start() throws RemoteException {
        if (this.mStarted) {
            return;
        }
        if (DEBUG) {
            Log.d(TAG, this + ": Starting");
        }
        this.mStarted = true;
        updateBinding();
    }

    public void stop() throws RemoteException {
        if (this.mStarted) {
            if (DEBUG) {
                Log.d(TAG, this + ": Stopping");
            }
            this.mStarted = false;
            updateBinding();
        }
    }

    public void rebindIfDisconnected() throws RemoteException {
        if (this.mActiveConnection == null && shouldBind()) {
            unbind();
            bind();
        }
    }

    private void updateBinding() throws RemoteException {
        if (shouldBind()) {
            bind();
        } else {
            unbind();
        }
    }

    private boolean shouldBind() {
        if (this.mStarted) {
            return (getDiscoveryRequest() == null && this.mControllers.isEmpty()) ? false : true;
        }
        return false;
    }

    private void bind() {
        if (this.mBound) {
            return;
        }
        if (DEBUG) {
            Log.d(TAG, this + ": Binding");
        }
        Intent intent = new Intent("android.media.MediaRouteProviderService");
        intent.setComponent(this.mComponentName);
        try {
            boolean zBindService = getContext().bindService(intent, this, 1);
            this.mBound = zBindService;
            if (zBindService || !DEBUG) {
                return;
            }
            Log.d(TAG, this + ": Bind failed");
        } catch (SecurityException e) {
            if (DEBUG) {
                Log.d(TAG, this + ": Bind failed", e);
            }
        }
    }

    private void unbind() throws RemoteException {
        if (this.mBound) {
            if (DEBUG) {
                Log.d(TAG, this + ": Unbinding");
            }
            this.mBound = false;
            disconnect();
            getContext().unbindService(this);
        }
    }

    private MediaRouteProvider.RouteController createRouteController(String str, String str2) throws RemoteException {
        MediaRouteProviderDescriptor descriptor = getDescriptor();
        if (descriptor == null) {
            return null;
        }
        List<MediaRouteDescriptor> routes = descriptor.getRoutes();
        int size = routes.size();
        for (int i = 0; i < size; i++) {
            if (routes.get(i).getId().equals(str)) {
                Controller controller = new Controller(str, str2);
                this.mControllers.add(controller);
                if (this.mConnectionReady) {
                    controller.attachConnection(this.mActiveConnection);
                }
                updateBinding();
                return controller;
            }
        }
        return null;
    }

    void onConnectionReady(Connection connection) throws RemoteException {
        if (this.mActiveConnection == connection) {
            this.mConnectionReady = true;
            attachControllersToConnection();
            MediaRouteDiscoveryRequest discoveryRequest = getDiscoveryRequest();
            if (discoveryRequest != null) {
                this.mActiveConnection.setDiscoveryRequest(discoveryRequest);
            }
        }
    }

    void onConnectionDied(Connection connection) throws RemoteException {
        if (this.mActiveConnection == connection) {
            if (DEBUG) {
                Log.d(TAG, this + ": Service connection died");
            }
            disconnect();
        }
    }

    void onConnectionError(Connection connection, String str) throws RemoteException {
        if (this.mActiveConnection == connection) {
            if (DEBUG) {
                Log.d(TAG, this + ": Service connection error - " + str);
            }
            unbind();
        }
    }

    void onConnectionDescriptorChanged(Connection connection, MediaRouteProviderDescriptor mediaRouteProviderDescriptor) {
        if (this.mActiveConnection == connection) {
            if (DEBUG) {
                Log.d(TAG, this + ": Descriptor changed, descriptor=" + mediaRouteProviderDescriptor);
            }
            setDescriptor(mediaRouteProviderDescriptor);
        }
    }

    private void disconnect() throws RemoteException {
        if (this.mActiveConnection != null) {
            setDescriptor(null);
            this.mConnectionReady = false;
            detachControllersFromConnection();
            this.mActiveConnection.dispose();
            this.mActiveConnection = null;
        }
    }

    void onControllerReleased(Controller controller) throws RemoteException {
        this.mControllers.remove(controller);
        controller.detachConnection();
        updateBinding();
    }

    private void attachControllersToConnection() throws RemoteException {
        int size = this.mControllers.size();
        for (int i = 0; i < size; i++) {
            this.mControllers.get(i).attachConnection(this.mActiveConnection);
        }
    }

    private void detachControllersFromConnection() throws RemoteException {
        int size = this.mControllers.size();
        for (int i = 0; i < size; i++) {
            this.mControllers.get(i).detachConnection();
        }
    }

    private final class Controller extends MediaRouteProvider.RouteController {
        private Connection mConnection;
        private int mControllerId;
        private int mPendingSetVolume = -1;
        private int mPendingUpdateVolumeDelta;
        private final String mRouteGroupId;
        private final String mRouteId;
        private boolean mSelected;

        public Controller(String str, String str2) {
            this.mRouteId = str;
            this.mRouteGroupId = str2;
        }

        public void attachConnection(Connection connection) throws RemoteException {
            this.mConnection = connection;
            int iCreateRouteController = connection.createRouteController(this.mRouteId, this.mRouteGroupId);
            this.mControllerId = iCreateRouteController;
            if (this.mSelected) {
                connection.selectRoute(iCreateRouteController);
                int i = this.mPendingSetVolume;
                if (i >= 0) {
                    connection.setVolume(this.mControllerId, i);
                    this.mPendingSetVolume = -1;
                }
                int i2 = this.mPendingUpdateVolumeDelta;
                if (i2 != 0) {
                    connection.updateVolume(this.mControllerId, i2);
                    this.mPendingUpdateVolumeDelta = 0;
                }
            }
        }

        public void detachConnection() throws RemoteException {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.releaseRouteController(this.mControllerId);
                this.mConnection = null;
                this.mControllerId = 0;
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public void onRelease() throws RemoteException {
            RegisteredMediaRouteProvider.this.onControllerReleased(this);
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public void onSelect() throws RemoteException {
            this.mSelected = true;
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.selectRoute(this.mControllerId);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public void onUnselect() throws RemoteException {
            onUnselect(0);
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public void onUnselect(int i) throws RemoteException {
            this.mSelected = false;
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.unselectRoute(this.mControllerId, i);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public void onSetVolume(int i) throws RemoteException {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.setVolume(this.mControllerId, i);
            } else {
                this.mPendingSetVolume = i;
                this.mPendingUpdateVolumeDelta = 0;
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public void onUpdateVolume(int i) throws RemoteException {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.updateVolume(this.mControllerId, i);
            } else {
                this.mPendingUpdateVolumeDelta += i;
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public boolean onControlRequest(Intent intent, MediaRouter.ControlRequestCallback controlRequestCallback) {
            Connection connection = this.mConnection;
            if (connection != null) {
                return connection.sendControlRequest(this.mControllerId, intent, controlRequestCallback);
            }
            return false;
        }
    }

    private final class Connection implements IBinder.DeathRecipient {
        private int mPendingRegisterRequestId;
        private final Messenger mServiceMessenger;
        private int mServiceVersion;
        private int mNextRequestId = 1;
        private int mNextControllerId = 1;
        private final SparseArray<MediaRouter.ControlRequestCallback> mPendingCallbacks = new SparseArray<>();
        private final ReceiveHandler mReceiveHandler = new ReceiveHandler(this);
        private final Messenger mReceiveMessenger = new Messenger(this.mReceiveHandler);

        public boolean onGenericSuccess(int i) {
            return true;
        }

        public Connection(Messenger messenger) {
            this.mServiceMessenger = messenger;
        }

        public boolean register() throws RemoteException {
            int i = this.mNextRequestId;
            this.mNextRequestId = i + 1;
            this.mPendingRegisterRequestId = i;
            if (!sendRequest(1, i, 2, null, null)) {
                return false;
            }
            try {
                this.mServiceMessenger.getBinder().linkToDeath(this, 0);
                return true;
            } catch (RemoteException unused) {
                binderDied();
                return false;
            }
        }

        public void dispose() throws RemoteException {
            sendRequest(2, 0, 0, null, null);
            this.mReceiveHandler.dispose();
            this.mServiceMessenger.getBinder().unlinkToDeath(this, 0);
            RegisteredMediaRouteProvider.this.mPrivateHandler.post(new Runnable() { // from class: androidx.mediarouter.media.RegisteredMediaRouteProvider.Connection.1
                @Override // java.lang.Runnable
                public void run() {
                    Connection.this.failPendingCallbacks();
                }
            });
        }

        void failPendingCallbacks() {
            for (int i = 0; i < this.mPendingCallbacks.size(); i++) {
                this.mPendingCallbacks.valueAt(i).onError(null, null);
            }
            this.mPendingCallbacks.clear();
        }

        public boolean onGenericFailure(int i) throws RemoteException {
            if (i == this.mPendingRegisterRequestId) {
                this.mPendingRegisterRequestId = 0;
                RegisteredMediaRouteProvider.this.onConnectionError(this, "Registration failed");
            }
            MediaRouter.ControlRequestCallback controlRequestCallback = this.mPendingCallbacks.get(i);
            if (controlRequestCallback == null) {
                return true;
            }
            this.mPendingCallbacks.remove(i);
            controlRequestCallback.onError(null, null);
            return true;
        }

        public boolean onRegistered(int i, int i2, Bundle bundle) throws RemoteException {
            if (this.mServiceVersion != 0 || i != this.mPendingRegisterRequestId || i2 < 1) {
                return false;
            }
            this.mPendingRegisterRequestId = 0;
            this.mServiceVersion = i2;
            RegisteredMediaRouteProvider.this.onConnectionDescriptorChanged(this, MediaRouteProviderDescriptor.fromBundle(bundle));
            RegisteredMediaRouteProvider.this.onConnectionReady(this);
            return true;
        }

        public boolean onDescriptorChanged(Bundle bundle) {
            if (this.mServiceVersion == 0) {
                return false;
            }
            RegisteredMediaRouteProvider.this.onConnectionDescriptorChanged(this, MediaRouteProviderDescriptor.fromBundle(bundle));
            return true;
        }

        public boolean onControlRequestSucceeded(int i, Bundle bundle) {
            MediaRouter.ControlRequestCallback controlRequestCallback = this.mPendingCallbacks.get(i);
            if (controlRequestCallback == null) {
                return false;
            }
            this.mPendingCallbacks.remove(i);
            controlRequestCallback.onResult(bundle);
            return true;
        }

        public boolean onControlRequestFailed(int i, String str, Bundle bundle) {
            MediaRouter.ControlRequestCallback controlRequestCallback = this.mPendingCallbacks.get(i);
            if (controlRequestCallback == null) {
                return false;
            }
            this.mPendingCallbacks.remove(i);
            controlRequestCallback.onError(str, bundle);
            return true;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            RegisteredMediaRouteProvider.this.mPrivateHandler.post(new Runnable() { // from class: androidx.mediarouter.media.RegisteredMediaRouteProvider.Connection.2
                @Override // java.lang.Runnable
                public void run() throws RemoteException {
                    RegisteredMediaRouteProvider.this.onConnectionDied(Connection.this);
                }
            });
        }

        public int createRouteController(String str, String str2) throws RemoteException {
            int i = this.mNextControllerId;
            this.mNextControllerId = i + 1;
            Bundle bundle = new Bundle();
            bundle.putString(MediaRouteProviderProtocol.CLIENT_DATA_ROUTE_ID, str);
            bundle.putString(MediaRouteProviderProtocol.CLIENT_DATA_ROUTE_LIBRARY_GROUP, str2);
            int i2 = this.mNextRequestId;
            this.mNextRequestId = i2 + 1;
            sendRequest(3, i2, i, null, bundle);
            return i;
        }

        public void releaseRouteController(int i) throws RemoteException {
            int i2 = this.mNextRequestId;
            this.mNextRequestId = i2 + 1;
            sendRequest(4, i2, i, null, null);
        }

        public void selectRoute(int i) throws RemoteException {
            int i2 = this.mNextRequestId;
            this.mNextRequestId = i2 + 1;
            sendRequest(5, i2, i, null, null);
        }

        public void unselectRoute(int i, int i2) throws RemoteException {
            Bundle bundle = new Bundle();
            bundle.putInt(MediaRouteProviderProtocol.CLIENT_DATA_UNSELECT_REASON, i2);
            int i3 = this.mNextRequestId;
            this.mNextRequestId = i3 + 1;
            sendRequest(6, i3, i, null, bundle);
        }

        public void setVolume(int i, int i2) throws RemoteException {
            Bundle bundle = new Bundle();
            bundle.putInt(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, i2);
            int i3 = this.mNextRequestId;
            this.mNextRequestId = i3 + 1;
            sendRequest(7, i3, i, null, bundle);
        }

        public void updateVolume(int i, int i2) throws RemoteException {
            Bundle bundle = new Bundle();
            bundle.putInt(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, i2);
            int i3 = this.mNextRequestId;
            this.mNextRequestId = i3 + 1;
            sendRequest(8, i3, i, null, bundle);
        }

        public boolean sendControlRequest(int i, Intent intent, MediaRouter.ControlRequestCallback controlRequestCallback) {
            int i2 = this.mNextRequestId;
            this.mNextRequestId = i2 + 1;
            if (!sendRequest(9, i2, i, intent, null)) {
                return false;
            }
            if (controlRequestCallback == null) {
                return true;
            }
            this.mPendingCallbacks.put(i2, controlRequestCallback);
            return true;
        }

        public void setDiscoveryRequest(MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest) throws RemoteException {
            int i = this.mNextRequestId;
            this.mNextRequestId = i + 1;
            sendRequest(10, i, 0, mediaRouteDiscoveryRequest != null ? mediaRouteDiscoveryRequest.asBundle() : null, null);
        }

        private boolean sendRequest(int i, int i2, int i3, Object obj, Bundle bundle) throws RemoteException {
            Message messageObtain = Message.obtain();
            messageObtain.what = i;
            messageObtain.arg1 = i2;
            messageObtain.arg2 = i3;
            messageObtain.obj = obj;
            messageObtain.setData(bundle);
            messageObtain.replyTo = this.mReceiveMessenger;
            try {
                this.mServiceMessenger.send(messageObtain);
                return true;
            } catch (DeadObjectException unused) {
                return false;
            } catch (RemoteException e) {
                if (i == 2) {
                    return false;
                }
                Log.e(RegisteredMediaRouteProvider.TAG, "Could not send message to service.", e);
                return false;
            }
        }
    }

    private static final class PrivateHandler extends Handler {
        PrivateHandler() {
        }
    }

    private static final class ReceiveHandler extends Handler {
        private final WeakReference<Connection> mConnectionRef;

        public ReceiveHandler(Connection connection) {
            this.mConnectionRef = new WeakReference<>(connection);
        }

        public void dispose() {
            this.mConnectionRef.clear();
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Connection connection = this.mConnectionRef.get();
            if (connection == null || processMessage(connection, message.what, message.arg1, message.arg2, message.obj, message.peekData()) || !RegisteredMediaRouteProvider.DEBUG) {
                return;
            }
            Log.d(RegisteredMediaRouteProvider.TAG, "Unhandled message from server: " + message);
        }

        private boolean processMessage(Connection connection, int i, int i2, int i3, Object obj, Bundle bundle) throws RemoteException {
            if (i == 0) {
                connection.onGenericFailure(i2);
                return true;
            }
            if (i == 1) {
                connection.onGenericSuccess(i2);
                return true;
            }
            if (i == 2) {
                if (obj == null || (obj instanceof Bundle)) {
                    return connection.onRegistered(i2, i3, (Bundle) obj);
                }
                return false;
            }
            if (i == 3) {
                if (obj == null || (obj instanceof Bundle)) {
                    return connection.onControlRequestSucceeded(i2, (Bundle) obj);
                }
                return false;
            }
            if (i == 4) {
                if (obj == null || (obj instanceof Bundle)) {
                    return connection.onControlRequestFailed(i2, bundle == null ? null : bundle.getString("error"), (Bundle) obj);
                }
                return false;
            }
            if (i != 5) {
                return false;
            }
            if (obj == null || (obj instanceof Bundle)) {
                return connection.onDescriptorChanged((Bundle) obj);
            }
            return false;
        }
    }
}
