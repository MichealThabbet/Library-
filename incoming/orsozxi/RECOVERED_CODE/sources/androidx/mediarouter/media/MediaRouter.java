package androidx.mediarouter.media;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import androidx.core.app.ActivityManagerCompat;
import androidx.core.hardware.display.DisplayManagerCompat;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Pair;
import androidx.media.VolumeProviderCompat;
import androidx.mediarouter.media.MediaRouteProvider;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.RegisteredMediaRouteProviderWatcher;
import androidx.mediarouter.media.RemoteControlClientCompat;
import androidx.mediarouter.media.SystemMediaRouteProvider;
import com.app.orsozoxi.Views.CustomCopticTextView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes.dex */
public final class MediaRouter {
    public static final int AVAILABILITY_FLAG_IGNORE_DEFAULT_ROUTE = 1;
    public static final int AVAILABILITY_FLAG_REQUIRE_MATCH = 2;
    public static final int CALLBACK_FLAG_FORCE_DISCOVERY = 8;
    public static final int CALLBACK_FLAG_PERFORM_ACTIVE_SCAN = 1;
    public static final int CALLBACK_FLAG_REQUEST_DISCOVERY = 4;
    public static final int CALLBACK_FLAG_UNFILTERED_EVENTS = 2;
    public static final int UNSELECT_REASON_DISCONNECTED = 1;
    public static final int UNSELECT_REASON_ROUTE_CHANGED = 3;
    public static final int UNSELECT_REASON_STOPPED = 2;
    public static final int UNSELECT_REASON_UNKNOWN = 0;
    static GlobalMediaRouter sGlobal;
    final ArrayList<CallbackRecord> mCallbackRecords = new ArrayList<>();
    final Context mContext;
    static final String TAG = "MediaRouter";
    static final boolean DEBUG = Log.isLoggable(TAG, 3);

    public static abstract class ControlRequestCallback {
        public void onError(String str, Bundle bundle) {
        }

        public void onResult(Bundle bundle) {
        }
    }

    MediaRouter(Context context) {
        this.mContext = context;
    }

    public static MediaRouter getInstance(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }
        checkCallingThread();
        if (sGlobal == null) {
            GlobalMediaRouter globalMediaRouter = new GlobalMediaRouter(context.getApplicationContext());
            sGlobal = globalMediaRouter;
            globalMediaRouter.start();
        }
        return sGlobal.getRouter(context);
    }

    public List<RouteInfo> getRoutes() {
        checkCallingThread();
        return sGlobal.getRoutes();
    }

    public List<ProviderInfo> getProviders() {
        checkCallingThread();
        return sGlobal.getProviders();
    }

    public RouteInfo getDefaultRoute() {
        checkCallingThread();
        return sGlobal.getDefaultRoute();
    }

    public RouteInfo getBluetoothRoute() {
        checkCallingThread();
        return sGlobal.getBluetoothRoute();
    }

    public RouteInfo getSelectedRoute() {
        checkCallingThread();
        return sGlobal.getSelectedRoute();
    }

    public RouteInfo updateSelectedRoute(MediaRouteSelector mediaRouteSelector) {
        if (mediaRouteSelector == null) {
            throw new IllegalArgumentException("selector must not be null");
        }
        checkCallingThread();
        if (DEBUG) {
            Log.d(TAG, "updateSelectedRoute: " + mediaRouteSelector);
        }
        RouteInfo selectedRoute = sGlobal.getSelectedRoute();
        if (selectedRoute.isDefaultOrBluetooth() || selectedRoute.matchesSelector(mediaRouteSelector)) {
            return selectedRoute;
        }
        RouteInfo routeInfoChooseFallbackRoute = sGlobal.chooseFallbackRoute();
        sGlobal.selectRoute(routeInfoChooseFallbackRoute);
        return routeInfoChooseFallbackRoute;
    }

    public void selectRoute(RouteInfo routeInfo) {
        if (routeInfo == null) {
            throw new IllegalArgumentException("route must not be null");
        }
        checkCallingThread();
        if (DEBUG) {
            Log.d(TAG, "selectRoute: " + routeInfo);
        }
        sGlobal.selectRoute(routeInfo);
    }

    public void unselect(int i) {
        if (i < 0 || i > 3) {
            throw new IllegalArgumentException("Unsupported reason to unselect route");
        }
        checkCallingThread();
        RouteInfo routeInfoChooseFallbackRoute = sGlobal.chooseFallbackRoute();
        if (sGlobal.getSelectedRoute() != routeInfoChooseFallbackRoute) {
            sGlobal.selectRoute(routeInfoChooseFallbackRoute, i);
        } else {
            GlobalMediaRouter globalMediaRouter = sGlobal;
            globalMediaRouter.selectRoute(globalMediaRouter.getDefaultRoute(), i);
        }
    }

    public boolean isRouteAvailable(MediaRouteSelector mediaRouteSelector, int i) {
        if (mediaRouteSelector == null) {
            throw new IllegalArgumentException("selector must not be null");
        }
        checkCallingThread();
        return sGlobal.isRouteAvailable(mediaRouteSelector, i);
    }

    public void addCallback(MediaRouteSelector mediaRouteSelector, Callback callback) {
        addCallback(mediaRouteSelector, callback, 0);
    }

    public void addCallback(MediaRouteSelector mediaRouteSelector, Callback callback, int i) {
        CallbackRecord callbackRecord;
        if (mediaRouteSelector == null) {
            throw new IllegalArgumentException("selector must not be null");
        }
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        checkCallingThread();
        if (DEBUG) {
            Log.d(TAG, "addCallback: selector=" + mediaRouteSelector + ", callback=" + callback + ", flags=" + Integer.toHexString(i));
        }
        int iFindCallbackRecord = findCallbackRecord(callback);
        if (iFindCallbackRecord < 0) {
            callbackRecord = new CallbackRecord(this, callback);
            this.mCallbackRecords.add(callbackRecord);
        } else {
            callbackRecord = this.mCallbackRecords.get(iFindCallbackRecord);
        }
        boolean z = false;
        boolean z2 = true;
        if (((~callbackRecord.mFlags) & i) != 0) {
            callbackRecord.mFlags |= i;
            z = true;
        }
        if (callbackRecord.mSelector.contains(mediaRouteSelector)) {
            z2 = z;
        } else {
            callbackRecord.mSelector = new MediaRouteSelector.Builder(callbackRecord.mSelector).addSelector(mediaRouteSelector).build();
        }
        if (z2) {
            sGlobal.updateDiscoveryRequest();
        }
    }

    public void removeCallback(Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        checkCallingThread();
        if (DEBUG) {
            Log.d(TAG, "removeCallback: callback=" + callback);
        }
        int iFindCallbackRecord = findCallbackRecord(callback);
        if (iFindCallbackRecord >= 0) {
            this.mCallbackRecords.remove(iFindCallbackRecord);
            sGlobal.updateDiscoveryRequest();
        }
    }

    private int findCallbackRecord(Callback callback) {
        int size = this.mCallbackRecords.size();
        for (int i = 0; i < size; i++) {
            if (this.mCallbackRecords.get(i).mCallback == callback) {
                return i;
            }
        }
        return -1;
    }

    public void addProvider(MediaRouteProvider mediaRouteProvider) {
        if (mediaRouteProvider == null) {
            throw new IllegalArgumentException("providerInstance must not be null");
        }
        checkCallingThread();
        if (DEBUG) {
            Log.d(TAG, "addProvider: " + mediaRouteProvider);
        }
        sGlobal.addProvider(mediaRouteProvider);
    }

    public void removeProvider(MediaRouteProvider mediaRouteProvider) {
        if (mediaRouteProvider == null) {
            throw new IllegalArgumentException("providerInstance must not be null");
        }
        checkCallingThread();
        if (DEBUG) {
            Log.d(TAG, "removeProvider: " + mediaRouteProvider);
        }
        sGlobal.removeProvider(mediaRouteProvider);
    }

    public void addRemoteControlClient(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("remoteControlClient must not be null");
        }
        checkCallingThread();
        if (DEBUG) {
            Log.d(TAG, "addRemoteControlClient: " + obj);
        }
        sGlobal.addRemoteControlClient(obj);
    }

    public void removeRemoteControlClient(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("remoteControlClient must not be null");
        }
        if (DEBUG) {
            Log.d(TAG, "removeRemoteControlClient: " + obj);
        }
        sGlobal.removeRemoteControlClient(obj);
    }

    public void setMediaSession(Object obj) {
        if (DEBUG) {
            Log.d(TAG, "addMediaSession: " + obj);
        }
        sGlobal.setMediaSession(obj);
    }

    public void setMediaSessionCompat(MediaSessionCompat mediaSessionCompat) {
        if (DEBUG) {
            Log.d(TAG, "addMediaSessionCompat: " + mediaSessionCompat);
        }
        sGlobal.setMediaSessionCompat(mediaSessionCompat);
    }

    public MediaSessionCompat.Token getMediaSessionToken() {
        return sGlobal.getMediaSessionToken();
    }

    static void checkCallingThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("The media router service must only be accessed on the application's main thread.");
        }
    }

    public static class RouteInfo {
        static final int CHANGE_GENERAL = 1;
        static final int CHANGE_PRESENTATION_DISPLAY = 4;
        static final int CHANGE_VOLUME = 2;
        public static final int CONNECTION_STATE_CONNECTED = 2;
        public static final int CONNECTION_STATE_CONNECTING = 1;
        public static final int CONNECTION_STATE_DISCONNECTED = 0;
        public static final int DEVICE_TYPE_BLUETOOTH = 3;
        public static final int DEVICE_TYPE_SPEAKER = 2;
        public static final int DEVICE_TYPE_TV = 1;
        public static final int DEVICE_TYPE_UNKNOWN = 0;
        public static final int PLAYBACK_TYPE_LOCAL = 0;
        public static final int PLAYBACK_TYPE_REMOTE = 1;
        public static final int PLAYBACK_VOLUME_FIXED = 0;
        public static final int PLAYBACK_VOLUME_VARIABLE = 1;
        public static final int PRESENTATION_DISPLAY_ID_NONE = -1;
        static final String SYSTEM_MEDIA_ROUTE_PROVIDER_PACKAGE_NAME = "android";
        private boolean mCanDisconnect;
        private boolean mConnecting;
        private int mConnectionState;
        private String mDescription;
        MediaRouteDescriptor mDescriptor;
        final String mDescriptorId;
        private int mDeviceType;
        boolean mEnabled;
        private Bundle mExtras;
        private Uri mIconUri;
        private String mName;
        private int mPlaybackStream;
        private int mPlaybackType;
        private Display mPresentationDisplay;
        private final ProviderInfo mProvider;
        private IntentSender mSettingsIntent;
        final String mUniqueId;
        private int mVolume;
        private int mVolumeHandling;
        private int mVolumeMax;
        private final ArrayList<IntentFilter> mControlFilters = new ArrayList<>();
        private int mPresentationDisplayId = -1;

        RouteInfo(ProviderInfo providerInfo, String str, String str2) {
            this.mProvider = providerInfo;
            this.mDescriptorId = str;
            this.mUniqueId = str2;
        }

        public ProviderInfo getProvider() {
            return this.mProvider;
        }

        public String getId() {
            return this.mUniqueId;
        }

        public String getName() {
            return this.mName;
        }

        public String getDescription() {
            return this.mDescription;
        }

        public Uri getIconUri() {
            return this.mIconUri;
        }

        public boolean isEnabled() {
            return this.mEnabled;
        }

        public boolean isConnecting() {
            return this.mConnecting;
        }

        public int getConnectionState() {
            return this.mConnectionState;
        }

        public boolean isSelected() {
            MediaRouter.checkCallingThread();
            return MediaRouter.sGlobal.getSelectedRoute() == this;
        }

        public boolean isDefault() {
            MediaRouter.checkCallingThread();
            return MediaRouter.sGlobal.getDefaultRoute() == this;
        }

        public boolean isBluetooth() {
            MediaRouter.checkCallingThread();
            return MediaRouter.sGlobal.getBluetoothRoute() == this;
        }

        public boolean isDeviceSpeaker() {
            return isDefault() && Resources.getSystem().getText(Resources.getSystem().getIdentifier("default_audio_route_name", "string", "android")).equals(this.mName);
        }

        public List<IntentFilter> getControlFilters() {
            return this.mControlFilters;
        }

        public boolean matchesSelector(MediaRouteSelector mediaRouteSelector) {
            if (mediaRouteSelector == null) {
                throw new IllegalArgumentException("selector must not be null");
            }
            MediaRouter.checkCallingThread();
            return mediaRouteSelector.matchesControlFilters(this.mControlFilters);
        }

        public boolean supportsControlCategory(String str) {
            if (str == null) {
                throw new IllegalArgumentException("category must not be null");
            }
            MediaRouter.checkCallingThread();
            int size = this.mControlFilters.size();
            for (int i = 0; i < size; i++) {
                if (this.mControlFilters.get(i).hasCategory(str)) {
                    return true;
                }
            }
            return false;
        }

        public boolean supportsControlAction(String str, String str2) {
            if (str == null) {
                throw new IllegalArgumentException("category must not be null");
            }
            if (str2 == null) {
                throw new IllegalArgumentException("action must not be null");
            }
            MediaRouter.checkCallingThread();
            int size = this.mControlFilters.size();
            for (int i = 0; i < size; i++) {
                IntentFilter intentFilter = this.mControlFilters.get(i);
                if (intentFilter.hasCategory(str) && intentFilter.hasAction(str2)) {
                    return true;
                }
            }
            return false;
        }

        public boolean supportsControlRequest(Intent intent) {
            if (intent == null) {
                throw new IllegalArgumentException("intent must not be null");
            }
            MediaRouter.checkCallingThread();
            ContentResolver contentResolver = MediaRouter.sGlobal.getContentResolver();
            int size = this.mControlFilters.size();
            for (int i = 0; i < size; i++) {
                if (this.mControlFilters.get(i).match(contentResolver, intent, true, MediaRouter.TAG) >= 0) {
                    return true;
                }
            }
            return false;
        }

        public void sendControlRequest(Intent intent, ControlRequestCallback controlRequestCallback) {
            if (intent == null) {
                throw new IllegalArgumentException("intent must not be null");
            }
            MediaRouter.checkCallingThread();
            MediaRouter.sGlobal.sendControlRequest(this, intent, controlRequestCallback);
        }

        public int getPlaybackType() {
            return this.mPlaybackType;
        }

        public int getPlaybackStream() {
            return this.mPlaybackStream;
        }

        public int getDeviceType() {
            return this.mDeviceType;
        }

        public boolean isDefaultOrBluetooth() {
            if (isDefault() || this.mDeviceType == 3) {
                return true;
            }
            return isSystemMediaRouteProvider(this) && supportsControlCategory(MediaControlIntent.CATEGORY_LIVE_AUDIO) && !supportsControlCategory(MediaControlIntent.CATEGORY_LIVE_VIDEO);
        }

        boolean isSelectable() {
            return this.mDescriptor != null && this.mEnabled;
        }

        private static boolean isSystemMediaRouteProvider(RouteInfo routeInfo) {
            return TextUtils.equals(routeInfo.getProviderInstance().getMetadata().getPackageName(), "android");
        }

        public int getVolumeHandling() {
            return this.mVolumeHandling;
        }

        public int getVolume() {
            return this.mVolume;
        }

        public int getVolumeMax() {
            return this.mVolumeMax;
        }

        public boolean canDisconnect() {
            return this.mCanDisconnect;
        }

        public void requestSetVolume(int i) {
            MediaRouter.checkCallingThread();
            MediaRouter.sGlobal.requestSetVolume(this, Math.min(this.mVolumeMax, Math.max(0, i)));
        }

        public void requestUpdateVolume(int i) {
            MediaRouter.checkCallingThread();
            if (i != 0) {
                MediaRouter.sGlobal.requestUpdateVolume(this, i);
            }
        }

        public Display getPresentationDisplay() {
            MediaRouter.checkCallingThread();
            if (this.mPresentationDisplayId >= 0 && this.mPresentationDisplay == null) {
                this.mPresentationDisplay = MediaRouter.sGlobal.getDisplay(this.mPresentationDisplayId);
            }
            return this.mPresentationDisplay;
        }

        public int getPresentationDisplayId() {
            return this.mPresentationDisplayId;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public IntentSender getSettingsIntent() {
            return this.mSettingsIntent;
        }

        public void select() {
            MediaRouter.checkCallingThread();
            MediaRouter.sGlobal.selectRoute(this);
        }

        public String toString() {
            return "MediaRouter.RouteInfo{ uniqueId=" + this.mUniqueId + ", name=" + this.mName + ", description=" + this.mDescription + ", iconUri=" + this.mIconUri + ", enabled=" + this.mEnabled + ", connecting=" + this.mConnecting + ", connectionState=" + this.mConnectionState + ", canDisconnect=" + this.mCanDisconnect + ", playbackType=" + this.mPlaybackType + ", playbackStream=" + this.mPlaybackStream + ", deviceType=" + this.mDeviceType + ", volumeHandling=" + this.mVolumeHandling + ", volume=" + this.mVolume + ", volumeMax=" + this.mVolumeMax + ", presentationDisplayId=" + this.mPresentationDisplayId + ", extras=" + this.mExtras + ", settingsIntent=" + this.mSettingsIntent + ", providerPackageName=" + this.mProvider.getPackageName() + " }";
        }

        int maybeUpdateDescriptor(MediaRouteDescriptor mediaRouteDescriptor) {
            if (this.mDescriptor != mediaRouteDescriptor) {
                return updateDescriptor(mediaRouteDescriptor);
            }
            return 0;
        }

        int updateDescriptor(MediaRouteDescriptor mediaRouteDescriptor) {
            this.mDescriptor = mediaRouteDescriptor;
            int i = 0;
            if (mediaRouteDescriptor == null) {
                return 0;
            }
            if (!ObjectsCompat.equals(this.mName, mediaRouteDescriptor.getName())) {
                this.mName = mediaRouteDescriptor.getName();
                i = 1;
            }
            if (!ObjectsCompat.equals(this.mDescription, mediaRouteDescriptor.getDescription())) {
                this.mDescription = mediaRouteDescriptor.getDescription();
                i |= 1;
            }
            if (!ObjectsCompat.equals(this.mIconUri, mediaRouteDescriptor.getIconUri())) {
                this.mIconUri = mediaRouteDescriptor.getIconUri();
                i |= 1;
            }
            if (this.mEnabled != mediaRouteDescriptor.isEnabled()) {
                this.mEnabled = mediaRouteDescriptor.isEnabled();
                i |= 1;
            }
            if (this.mConnecting != mediaRouteDescriptor.isConnecting()) {
                this.mConnecting = mediaRouteDescriptor.isConnecting();
                i |= 1;
            }
            if (this.mConnectionState != mediaRouteDescriptor.getConnectionState()) {
                this.mConnectionState = mediaRouteDescriptor.getConnectionState();
                i |= 1;
            }
            if (!this.mControlFilters.equals(mediaRouteDescriptor.getControlFilters())) {
                this.mControlFilters.clear();
                this.mControlFilters.addAll(mediaRouteDescriptor.getControlFilters());
                i |= 1;
            }
            if (this.mPlaybackType != mediaRouteDescriptor.getPlaybackType()) {
                this.mPlaybackType = mediaRouteDescriptor.getPlaybackType();
                i |= 1;
            }
            if (this.mPlaybackStream != mediaRouteDescriptor.getPlaybackStream()) {
                this.mPlaybackStream = mediaRouteDescriptor.getPlaybackStream();
                i |= 1;
            }
            if (this.mDeviceType != mediaRouteDescriptor.getDeviceType()) {
                this.mDeviceType = mediaRouteDescriptor.getDeviceType();
                i |= 1;
            }
            if (this.mVolumeHandling != mediaRouteDescriptor.getVolumeHandling()) {
                this.mVolumeHandling = mediaRouteDescriptor.getVolumeHandling();
                i |= 3;
            }
            if (this.mVolume != mediaRouteDescriptor.getVolume()) {
                this.mVolume = mediaRouteDescriptor.getVolume();
                i |= 3;
            }
            if (this.mVolumeMax != mediaRouteDescriptor.getVolumeMax()) {
                this.mVolumeMax = mediaRouteDescriptor.getVolumeMax();
                i |= 3;
            }
            if (this.mPresentationDisplayId != mediaRouteDescriptor.getPresentationDisplayId()) {
                this.mPresentationDisplayId = mediaRouteDescriptor.getPresentationDisplayId();
                this.mPresentationDisplay = null;
                i |= 5;
            }
            if (!ObjectsCompat.equals(this.mExtras, mediaRouteDescriptor.getExtras())) {
                this.mExtras = mediaRouteDescriptor.getExtras();
                i |= 1;
            }
            if (!ObjectsCompat.equals(this.mSettingsIntent, mediaRouteDescriptor.getSettingsActivity())) {
                this.mSettingsIntent = mediaRouteDescriptor.getSettingsActivity();
                i |= 1;
            }
            if (this.mCanDisconnect == mediaRouteDescriptor.canDisconnectAndKeepPlaying()) {
                return i;
            }
            this.mCanDisconnect = mediaRouteDescriptor.canDisconnectAndKeepPlaying();
            return i | 5;
        }

        String getDescriptorId() {
            return this.mDescriptorId;
        }

        public MediaRouteProvider getProviderInstance() {
            return this.mProvider.getProviderInstance();
        }
    }

    public static class RouteGroup extends RouteInfo {
        private List<RouteInfo> mRoutes;

        RouteGroup(ProviderInfo providerInfo, String str, String str2) {
            super(providerInfo, str, str2);
            this.mRoutes = new ArrayList();
        }

        public int getRouteCount() {
            return this.mRoutes.size();
        }

        public RouteInfo getRouteAt(int i) {
            return this.mRoutes.get(i);
        }

        public List<RouteInfo> getRoutes() {
            return this.mRoutes;
        }

        @Override // androidx.mediarouter.media.MediaRouter.RouteInfo
        public String toString() {
            StringBuilder sb = new StringBuilder(super.toString());
            sb.append('[');
            int size = this.mRoutes.size();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(this.mRoutes.get(i));
            }
            sb.append(']');
            return sb.toString();
        }

        @Override // androidx.mediarouter.media.MediaRouter.RouteInfo
        int maybeUpdateDescriptor(MediaRouteDescriptor mediaRouteDescriptor) {
            if (this.mDescriptor != mediaRouteDescriptor) {
                this.mDescriptor = mediaRouteDescriptor;
                if (mediaRouteDescriptor != null) {
                    List<String> groupMemberIds = mediaRouteDescriptor.getGroupMemberIds();
                    ArrayList arrayList = new ArrayList();
                    if (groupMemberIds == null) {
                        Log.w(MediaRouter.TAG, "groupMemberIds shouldn't be null.");
                        i = 1;
                    } else {
                        i = groupMemberIds.size() != this.mRoutes.size() ? 1 : 0;
                        Iterator<String> it2 = groupMemberIds.iterator();
                        while (it2.hasNext()) {
                            RouteInfo route = MediaRouter.sGlobal.getRoute(MediaRouter.sGlobal.getUniqueId(getProvider(), it2.next()));
                            if (route != null) {
                                arrayList.add(route);
                                if (i == 0 && !this.mRoutes.contains(route)) {
                                    i = 1;
                                }
                            }
                        }
                    }
                    if (i != 0) {
                        this.mRoutes = arrayList;
                    }
                }
            }
            return super.updateDescriptor(mediaRouteDescriptor) | i;
        }
    }

    public static final class ProviderInfo {
        private MediaRouteProviderDescriptor mDescriptor;
        private final MediaRouteProvider.ProviderMetadata mMetadata;
        final MediaRouteProvider mProviderInstance;
        private Resources mResources;
        private boolean mResourcesNotAvailable;
        final List<RouteInfo> mRoutes = new ArrayList();

        ProviderInfo(MediaRouteProvider mediaRouteProvider) {
            this.mProviderInstance = mediaRouteProvider;
            this.mMetadata = mediaRouteProvider.getMetadata();
        }

        public MediaRouteProvider getProviderInstance() {
            MediaRouter.checkCallingThread();
            return this.mProviderInstance;
        }

        public String getPackageName() {
            return this.mMetadata.getPackageName();
        }

        public ComponentName getComponentName() {
            return this.mMetadata.getComponentName();
        }

        public List<RouteInfo> getRoutes() {
            MediaRouter.checkCallingThread();
            return this.mRoutes;
        }

        Resources getResources() {
            if (this.mResources == null && !this.mResourcesNotAvailable) {
                String packageName = getPackageName();
                Context providerContext = MediaRouter.sGlobal.getProviderContext(packageName);
                if (providerContext != null) {
                    this.mResources = providerContext.getResources();
                } else {
                    Log.w(MediaRouter.TAG, "Unable to obtain resources for route provider package: " + packageName);
                    this.mResourcesNotAvailable = true;
                }
            }
            return this.mResources;
        }

        boolean updateDescriptor(MediaRouteProviderDescriptor mediaRouteProviderDescriptor) {
            if (this.mDescriptor == mediaRouteProviderDescriptor) {
                return false;
            }
            this.mDescriptor = mediaRouteProviderDescriptor;
            return true;
        }

        int findRouteByDescriptorId(String str) {
            int size = this.mRoutes.size();
            for (int i = 0; i < size; i++) {
                if (this.mRoutes.get(i).mDescriptorId.equals(str)) {
                    return i;
                }
            }
            return -1;
        }

        public String toString() {
            return "MediaRouter.RouteProviderInfo{ packageName=" + getPackageName() + " }";
        }
    }

    public static abstract class Callback {
        public void onProviderAdded(MediaRouter mediaRouter, ProviderInfo providerInfo) {
        }

        public void onProviderChanged(MediaRouter mediaRouter, ProviderInfo providerInfo) {
        }

        public void onProviderRemoved(MediaRouter mediaRouter, ProviderInfo providerInfo) {
        }

        public void onRouteAdded(MediaRouter mediaRouter, RouteInfo routeInfo) {
        }

        public void onRouteChanged(MediaRouter mediaRouter, RouteInfo routeInfo) {
        }

        public void onRoutePresentationDisplayChanged(MediaRouter mediaRouter, RouteInfo routeInfo) {
        }

        public void onRouteRemoved(MediaRouter mediaRouter, RouteInfo routeInfo) {
        }

        public void onRouteSelected(MediaRouter mediaRouter, RouteInfo routeInfo) {
        }

        public void onRouteUnselected(MediaRouter mediaRouter, RouteInfo routeInfo) {
        }

        public void onRouteVolumeChanged(MediaRouter mediaRouter, RouteInfo routeInfo) {
        }

        public void onRouteUnselected(MediaRouter mediaRouter, RouteInfo routeInfo, int i) {
            onRouteUnselected(mediaRouter, routeInfo);
        }
    }

    private static final class CallbackRecord {
        public final Callback mCallback;
        public int mFlags;
        public final MediaRouter mRouter;
        public MediaRouteSelector mSelector = MediaRouteSelector.EMPTY;

        public CallbackRecord(MediaRouter mediaRouter, Callback callback) {
            this.mRouter = mediaRouter;
            this.mCallback = callback;
        }

        public boolean filterRouteEvent(RouteInfo routeInfo) {
            return (this.mFlags & 2) != 0 || routeInfo.matchesSelector(this.mSelector);
        }
    }

    private static final class GlobalMediaRouter implements SystemMediaRouteProvider.SyncCallback, RegisteredMediaRouteProviderWatcher.Callback {
        final Context mApplicationContext;
        private RouteInfo mBluetoothRoute;
        private MediaSessionCompat mCompatSession;
        private RouteInfo mDefaultRoute;
        private MediaRouteDiscoveryRequest mDiscoveryRequest;
        private final DisplayManagerCompat mDisplayManager;
        private final boolean mLowRam;
        private MediaSessionRecord mMediaSession;
        MediaSessionCompat mRccMediaSession;
        private RegisteredMediaRouteProviderWatcher mRegisteredProviderWatcher;
        RouteInfo mSelectedRoute;
        private MediaRouteProvider.RouteController mSelectedRouteController;
        final SystemMediaRouteProvider mSystemProvider;
        final ArrayList<WeakReference<MediaRouter>> mRouters = new ArrayList<>();
        private final ArrayList<RouteInfo> mRoutes = new ArrayList<>();
        private final Map<Pair<String, String>, String> mUniqueIdMap = new HashMap();
        private final ArrayList<ProviderInfo> mProviders = new ArrayList<>();
        private final ArrayList<RemoteControlClientRecord> mRemoteControlClients = new ArrayList<>();
        final RemoteControlClientCompat.PlaybackInfo mPlaybackInfo = new RemoteControlClientCompat.PlaybackInfo();
        private final ProviderCallback mProviderCallback = new ProviderCallback();
        final CallbackHandler mCallbackHandler = new CallbackHandler();
        private final Map<String, MediaRouteProvider.RouteController> mRouteControllerMap = new HashMap();
        private MediaSessionCompat.OnActiveChangeListener mSessionActiveListener = new MediaSessionCompat.OnActiveChangeListener() { // from class: androidx.mediarouter.media.MediaRouter.GlobalMediaRouter.1
            @Override // android.support.v4.media.session.MediaSessionCompat.OnActiveChangeListener
            public void onActiveChanged() {
                if (GlobalMediaRouter.this.mRccMediaSession != null) {
                    if (GlobalMediaRouter.this.mRccMediaSession.isActive()) {
                        GlobalMediaRouter globalMediaRouter = GlobalMediaRouter.this;
                        globalMediaRouter.addRemoteControlClient(globalMediaRouter.mRccMediaSession.getRemoteControlClient());
                    } else {
                        GlobalMediaRouter globalMediaRouter2 = GlobalMediaRouter.this;
                        globalMediaRouter2.removeRemoteControlClient(globalMediaRouter2.mRccMediaSession.getRemoteControlClient());
                    }
                }
            }
        };

        GlobalMediaRouter(Context context) {
            this.mApplicationContext = context;
            this.mDisplayManager = DisplayManagerCompat.getInstance(context);
            this.mLowRam = ActivityManagerCompat.isLowRamDevice((ActivityManager) context.getSystemService("activity"));
            this.mSystemProvider = SystemMediaRouteProvider.obtain(context, this);
        }

        public void start() {
            addProvider(this.mSystemProvider);
            RegisteredMediaRouteProviderWatcher registeredMediaRouteProviderWatcher = new RegisteredMediaRouteProviderWatcher(this.mApplicationContext, this);
            this.mRegisteredProviderWatcher = registeredMediaRouteProviderWatcher;
            registeredMediaRouteProviderWatcher.start();
        }

        public MediaRouter getRouter(Context context) {
            int size = this.mRouters.size();
            while (true) {
                size--;
                if (size >= 0) {
                    MediaRouter mediaRouter = this.mRouters.get(size).get();
                    if (mediaRouter == null) {
                        this.mRouters.remove(size);
                    } else if (mediaRouter.mContext == context) {
                        return mediaRouter;
                    }
                } else {
                    MediaRouter mediaRouter2 = new MediaRouter(context);
                    this.mRouters.add(new WeakReference<>(mediaRouter2));
                    return mediaRouter2;
                }
            }
        }

        public ContentResolver getContentResolver() {
            return this.mApplicationContext.getContentResolver();
        }

        public Context getProviderContext(String str) {
            if (str.equals(SystemMediaRouteProvider.PACKAGE_NAME)) {
                return this.mApplicationContext;
            }
            try {
                return this.mApplicationContext.createPackageContext(str, 4);
            } catch (PackageManager.NameNotFoundException unused) {
                return null;
            }
        }

        public Display getDisplay(int i) {
            return this.mDisplayManager.getDisplay(i);
        }

        public void sendControlRequest(RouteInfo routeInfo, Intent intent, ControlRequestCallback controlRequestCallback) {
            MediaRouteProvider.RouteController routeController;
            if ((routeInfo == this.mSelectedRoute && (routeController = this.mSelectedRouteController) != null && routeController.onControlRequest(intent, controlRequestCallback)) || controlRequestCallback == null) {
                return;
            }
            controlRequestCallback.onError(null, null);
        }

        public void requestSetVolume(RouteInfo routeInfo, int i) {
            MediaRouteProvider.RouteController routeController;
            MediaRouteProvider.RouteController routeController2;
            if (routeInfo == this.mSelectedRoute && (routeController2 = this.mSelectedRouteController) != null) {
                routeController2.onSetVolume(i);
            } else {
                if (this.mRouteControllerMap.isEmpty() || (routeController = this.mRouteControllerMap.get(routeInfo.mDescriptorId)) == null) {
                    return;
                }
                routeController.onSetVolume(i);
            }
        }

        public void requestUpdateVolume(RouteInfo routeInfo, int i) {
            MediaRouteProvider.RouteController routeController;
            if (routeInfo != this.mSelectedRoute || (routeController = this.mSelectedRouteController) == null) {
                return;
            }
            routeController.onUpdateVolume(i);
        }

        public RouteInfo getRoute(String str) {
            Iterator<RouteInfo> it2 = this.mRoutes.iterator();
            while (it2.hasNext()) {
                RouteInfo next = it2.next();
                if (next.mUniqueId.equals(str)) {
                    return next;
                }
            }
            return null;
        }

        public List<RouteInfo> getRoutes() {
            return this.mRoutes;
        }

        List<ProviderInfo> getProviders() {
            return this.mProviders;
        }

        RouteInfo getDefaultRoute() {
            RouteInfo routeInfo = this.mDefaultRoute;
            if (routeInfo != null) {
                return routeInfo;
            }
            throw new IllegalStateException("There is no default route.  The media router has not yet been fully initialized.");
        }

        RouteInfo getBluetoothRoute() {
            return this.mBluetoothRoute;
        }

        RouteInfo getSelectedRoute() {
            RouteInfo routeInfo = this.mSelectedRoute;
            if (routeInfo != null) {
                return routeInfo;
            }
            throw new IllegalStateException("There is no currently selected route.  The media router has not yet been fully initialized.");
        }

        void selectRoute(RouteInfo routeInfo) {
            selectRoute(routeInfo, 3);
        }

        void selectRoute(RouteInfo routeInfo, int i) {
            if (!this.mRoutes.contains(routeInfo)) {
                Log.w(MediaRouter.TAG, "Ignoring attempt to select removed route: " + routeInfo);
                return;
            }
            if (!routeInfo.mEnabled) {
                Log.w(MediaRouter.TAG, "Ignoring attempt to select disabled route: " + routeInfo);
                return;
            }
            setSelectedRouteInternal(routeInfo, i);
        }

        public boolean isRouteAvailable(MediaRouteSelector mediaRouteSelector, int i) {
            if (mediaRouteSelector.isEmpty()) {
                return false;
            }
            if ((i & 2) == 0 && this.mLowRam) {
                return true;
            }
            int size = this.mRoutes.size();
            for (int i2 = 0; i2 < size; i2++) {
                RouteInfo routeInfo = this.mRoutes.get(i2);
                if (((i & 1) == 0 || !routeInfo.isDefaultOrBluetooth()) && routeInfo.matchesSelector(mediaRouteSelector)) {
                    return true;
                }
            }
            return false;
        }

        public void updateDiscoveryRequest() {
            MediaRouteSelector.Builder builder = new MediaRouteSelector.Builder();
            int size = this.mRouters.size();
            boolean z = false;
            boolean z2 = false;
            while (true) {
                size--;
                if (size < 0) {
                    break;
                }
                MediaRouter mediaRouter = this.mRouters.get(size).get();
                if (mediaRouter == null) {
                    this.mRouters.remove(size);
                } else {
                    int size2 = mediaRouter.mCallbackRecords.size();
                    for (int i = 0; i < size2; i++) {
                        CallbackRecord callbackRecord = mediaRouter.mCallbackRecords.get(i);
                        builder.addSelector(callbackRecord.mSelector);
                        if ((callbackRecord.mFlags & 1) != 0) {
                            z = true;
                            z2 = true;
                        }
                        if ((callbackRecord.mFlags & 4) != 0 && !this.mLowRam) {
                            z = true;
                        }
                        if ((callbackRecord.mFlags & 8) != 0) {
                            z = true;
                        }
                    }
                }
            }
            MediaRouteSelector mediaRouteSelectorBuild = z ? builder.build() : MediaRouteSelector.EMPTY;
            MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest = this.mDiscoveryRequest;
            if (mediaRouteDiscoveryRequest != null && mediaRouteDiscoveryRequest.getSelector().equals(mediaRouteSelectorBuild) && this.mDiscoveryRequest.isActiveScan() == z2) {
                return;
            }
            if (mediaRouteSelectorBuild.isEmpty() && !z2) {
                if (this.mDiscoveryRequest == null) {
                    return;
                } else {
                    this.mDiscoveryRequest = null;
                }
            } else {
                this.mDiscoveryRequest = new MediaRouteDiscoveryRequest(mediaRouteSelectorBuild, z2);
            }
            if (MediaRouter.DEBUG) {
                Log.d(MediaRouter.TAG, "Updated discovery request: " + this.mDiscoveryRequest);
            }
            if (z && !z2 && this.mLowRam) {
                Log.i(MediaRouter.TAG, "Forcing passive route discovery on a low-RAM device, system performance may be affected.  Please consider using CALLBACK_FLAG_REQUEST_DISCOVERY instead of CALLBACK_FLAG_FORCE_DISCOVERY.");
            }
            int size3 = this.mProviders.size();
            for (int i2 = 0; i2 < size3; i2++) {
                this.mProviders.get(i2).mProviderInstance.setDiscoveryRequest(this.mDiscoveryRequest);
            }
        }

        @Override // androidx.mediarouter.media.RegisteredMediaRouteProviderWatcher.Callback
        public void addProvider(MediaRouteProvider mediaRouteProvider) {
            if (findProviderInfo(mediaRouteProvider) < 0) {
                ProviderInfo providerInfo = new ProviderInfo(mediaRouteProvider);
                this.mProviders.add(providerInfo);
                if (MediaRouter.DEBUG) {
                    Log.d(MediaRouter.TAG, "Provider added: " + providerInfo);
                }
                this.mCallbackHandler.post(513, providerInfo);
                updateProviderContents(providerInfo, mediaRouteProvider.getDescriptor());
                mediaRouteProvider.setCallback(this.mProviderCallback);
                mediaRouteProvider.setDiscoveryRequest(this.mDiscoveryRequest);
            }
        }

        @Override // androidx.mediarouter.media.RegisteredMediaRouteProviderWatcher.Callback
        public void removeProvider(MediaRouteProvider mediaRouteProvider) {
            int iFindProviderInfo = findProviderInfo(mediaRouteProvider);
            if (iFindProviderInfo >= 0) {
                mediaRouteProvider.setCallback(null);
                mediaRouteProvider.setDiscoveryRequest(null);
                ProviderInfo providerInfo = this.mProviders.get(iFindProviderInfo);
                updateProviderContents(providerInfo, null);
                if (MediaRouter.DEBUG) {
                    Log.d(MediaRouter.TAG, "Provider removed: " + providerInfo);
                }
                this.mCallbackHandler.post(514, providerInfo);
                this.mProviders.remove(iFindProviderInfo);
            }
        }

        void updateProviderDescriptor(MediaRouteProvider mediaRouteProvider, MediaRouteProviderDescriptor mediaRouteProviderDescriptor) {
            int iFindProviderInfo = findProviderInfo(mediaRouteProvider);
            if (iFindProviderInfo >= 0) {
                updateProviderContents(this.mProviders.get(iFindProviderInfo), mediaRouteProviderDescriptor);
            }
        }

        private int findProviderInfo(MediaRouteProvider mediaRouteProvider) {
            int size = this.mProviders.size();
            for (int i = 0; i < size; i++) {
                if (this.mProviders.get(i).mProviderInstance == mediaRouteProvider) {
                    return i;
                }
            }
            return -1;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private void updateProviderContents(ProviderInfo providerInfo, MediaRouteProviderDescriptor mediaRouteProviderDescriptor) {
            boolean z;
            int i;
            RouteInfo routeInfo;
            if (providerInfo.updateDescriptor(mediaRouteProviderDescriptor)) {
                if (mediaRouteProviderDescriptor == null) {
                    z = false;
                    i = 0;
                } else if (mediaRouteProviderDescriptor.isValid()) {
                    List<MediaRouteDescriptor> routes = mediaRouteProviderDescriptor.getRoutes();
                    int size = routes.size();
                    ArrayList<Pair> arrayList = new ArrayList();
                    ArrayList<Pair> arrayList2 = new ArrayList();
                    int i2 = 0;
                    boolean z2 = false;
                    for (int i3 = 0; i3 < size; i3++) {
                        MediaRouteDescriptor mediaRouteDescriptor = routes.get(i3);
                        String id = mediaRouteDescriptor.getId();
                        int iFindRouteByDescriptorId = providerInfo.findRouteByDescriptorId(id);
                        boolean z3 = mediaRouteDescriptor.getGroupMemberIds() != null;
                        if (iFindRouteByDescriptorId < 0) {
                            String strAssignRouteUniqueId = assignRouteUniqueId(providerInfo, id);
                            RouteInfo routeGroup = z3 ? new RouteGroup(providerInfo, id, strAssignRouteUniqueId) : new RouteInfo(providerInfo, id, strAssignRouteUniqueId);
                            int i4 = i2 + 1;
                            providerInfo.mRoutes.add(i2, routeGroup);
                            this.mRoutes.add(routeGroup);
                            if (z3) {
                                arrayList.add(new Pair(routeGroup, mediaRouteDescriptor));
                            } else {
                                routeGroup.maybeUpdateDescriptor(mediaRouteDescriptor);
                                if (MediaRouter.DEBUG) {
                                    Log.d(MediaRouter.TAG, "Route added: " + routeGroup);
                                }
                                this.mCallbackHandler.post(257, routeGroup);
                            }
                            i2 = i4;
                        } else if (iFindRouteByDescriptorId < i2) {
                            Log.w(MediaRouter.TAG, "Ignoring route descriptor with duplicate id: " + mediaRouteDescriptor);
                        } else {
                            RouteInfo routeInfo2 = providerInfo.mRoutes.get(iFindRouteByDescriptorId);
                            if ((routeInfo2 instanceof RouteGroup) != z3) {
                                if (z3) {
                                    routeInfo = new RouteGroup(providerInfo, id, routeInfo2.getId());
                                } else {
                                    routeInfo = new RouteInfo(providerInfo, id, routeInfo2.getId());
                                }
                                routeInfo2 = routeInfo;
                                providerInfo.mRoutes.set(iFindRouteByDescriptorId, routeInfo2);
                            }
                            int i5 = i2 + 1;
                            Collections.swap(providerInfo.mRoutes, iFindRouteByDescriptorId, i2);
                            if (routeInfo2 instanceof RouteGroup) {
                                arrayList2.add(new Pair(routeInfo2, mediaRouteDescriptor));
                            } else if (updateRouteDescriptorAndNotify(routeInfo2, mediaRouteDescriptor) != 0 && routeInfo2 == this.mSelectedRoute) {
                                i2 = i5;
                                z2 = true;
                            }
                            i2 = i5;
                        }
                    }
                    for (Pair pair : arrayList) {
                        RouteInfo routeInfo3 = (RouteInfo) pair.first;
                        routeInfo3.maybeUpdateDescriptor((MediaRouteDescriptor) pair.second);
                        if (MediaRouter.DEBUG) {
                            Log.d(MediaRouter.TAG, "Route added: " + routeInfo3);
                        }
                        this.mCallbackHandler.post(257, routeInfo3);
                    }
                    boolean z4 = z2;
                    for (Pair pair2 : arrayList2) {
                        RouteInfo routeInfo4 = (RouteInfo) pair2.first;
                        if (updateRouteDescriptorAndNotify(routeInfo4, (MediaRouteDescriptor) pair2.second) != 0 && routeInfo4 == this.mSelectedRoute) {
                            z4 = true;
                        }
                    }
                    z = z4;
                    i = i2;
                } else {
                    Log.w(MediaRouter.TAG, "Ignoring invalid provider descriptor: " + mediaRouteProviderDescriptor);
                    z = false;
                    i = 0;
                }
                for (int size2 = providerInfo.mRoutes.size() - 1; size2 >= i; size2--) {
                    RouteInfo routeInfo5 = providerInfo.mRoutes.get(size2);
                    routeInfo5.maybeUpdateDescriptor(null);
                    this.mRoutes.remove(routeInfo5);
                }
                updateSelectedRouteIfNeeded(z);
                for (int size3 = providerInfo.mRoutes.size() - 1; size3 >= i; size3--) {
                    RouteInfo routeInfoRemove = providerInfo.mRoutes.remove(size3);
                    if (MediaRouter.DEBUG) {
                        Log.d(MediaRouter.TAG, "Route removed: " + routeInfoRemove);
                    }
                    this.mCallbackHandler.post(258, routeInfoRemove);
                }
                if (MediaRouter.DEBUG) {
                    Log.d(MediaRouter.TAG, "Provider changed: " + providerInfo);
                }
                this.mCallbackHandler.post(515, providerInfo);
            }
        }

        private int updateRouteDescriptorAndNotify(RouteInfo routeInfo, MediaRouteDescriptor mediaRouteDescriptor) {
            int iMaybeUpdateDescriptor = routeInfo.maybeUpdateDescriptor(mediaRouteDescriptor);
            if (iMaybeUpdateDescriptor != 0) {
                if ((iMaybeUpdateDescriptor & 1) != 0) {
                    if (MediaRouter.DEBUG) {
                        Log.d(MediaRouter.TAG, "Route changed: " + routeInfo);
                    }
                    this.mCallbackHandler.post(259, routeInfo);
                }
                if ((iMaybeUpdateDescriptor & 2) != 0) {
                    if (MediaRouter.DEBUG) {
                        Log.d(MediaRouter.TAG, "Route volume changed: " + routeInfo);
                    }
                    this.mCallbackHandler.post(260, routeInfo);
                }
                if ((iMaybeUpdateDescriptor & 4) != 0) {
                    if (MediaRouter.DEBUG) {
                        Log.d(MediaRouter.TAG, "Route presentation display changed: " + routeInfo);
                    }
                    this.mCallbackHandler.post(261, routeInfo);
                }
            }
            return iMaybeUpdateDescriptor;
        }

        private String assignRouteUniqueId(ProviderInfo providerInfo, String str) {
            String strFlattenToShortString = providerInfo.getComponentName().flattenToShortString();
            String str2 = strFlattenToShortString + ":" + str;
            if (findRouteByUniqueId(str2) < 0) {
                this.mUniqueIdMap.put(new Pair<>(strFlattenToShortString, str), str2);
                return str2;
            }
            Log.w(MediaRouter.TAG, "Either " + str + " isn't unique in " + strFlattenToShortString + " or we're trying to assign a unique ID for an already added route");
            int i = 2;
            while (true) {
                String str3 = String.format(Locale.US, "%s_%d", str2, Integer.valueOf(i));
                if (findRouteByUniqueId(str3) < 0) {
                    this.mUniqueIdMap.put(new Pair<>(strFlattenToShortString, str), str3);
                    return str3;
                }
                i++;
            }
        }

        private int findRouteByUniqueId(String str) {
            int size = this.mRoutes.size();
            for (int i = 0; i < size; i++) {
                if (this.mRoutes.get(i).mUniqueId.equals(str)) {
                    return i;
                }
            }
            return -1;
        }

        String getUniqueId(ProviderInfo providerInfo, String str) {
            return this.mUniqueIdMap.get(new Pair(providerInfo.getComponentName().flattenToShortString(), str));
        }

        void updateSelectedRouteIfNeeded(boolean z) {
            RouteInfo routeInfo = this.mDefaultRoute;
            if (routeInfo != null && !routeInfo.isSelectable()) {
                Log.i(MediaRouter.TAG, "Clearing the default route because it is no longer selectable: " + this.mDefaultRoute);
                this.mDefaultRoute = null;
            }
            if (this.mDefaultRoute == null && !this.mRoutes.isEmpty()) {
                Iterator<RouteInfo> it2 = this.mRoutes.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    RouteInfo next = it2.next();
                    if (isSystemDefaultRoute(next) && next.isSelectable()) {
                        this.mDefaultRoute = next;
                        Log.i(MediaRouter.TAG, "Found default route: " + this.mDefaultRoute);
                        break;
                    }
                }
            }
            RouteInfo routeInfo2 = this.mBluetoothRoute;
            if (routeInfo2 != null && !routeInfo2.isSelectable()) {
                Log.i(MediaRouter.TAG, "Clearing the bluetooth route because it is no longer selectable: " + this.mBluetoothRoute);
                this.mBluetoothRoute = null;
            }
            if (this.mBluetoothRoute == null && !this.mRoutes.isEmpty()) {
                Iterator<RouteInfo> it3 = this.mRoutes.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        break;
                    }
                    RouteInfo next2 = it3.next();
                    if (isSystemLiveAudioOnlyRoute(next2) && next2.isSelectable()) {
                        this.mBluetoothRoute = next2;
                        Log.i(MediaRouter.TAG, "Found bluetooth route: " + this.mBluetoothRoute);
                        break;
                    }
                }
            }
            RouteInfo routeInfo3 = this.mSelectedRoute;
            if (routeInfo3 == null || !routeInfo3.isSelectable()) {
                Log.i(MediaRouter.TAG, "Unselecting the current route because it is no longer selectable: " + this.mSelectedRoute);
                setSelectedRouteInternal(chooseFallbackRoute(), 0);
                return;
            }
            if (z) {
                RouteInfo routeInfo4 = this.mSelectedRoute;
                if (routeInfo4 instanceof RouteGroup) {
                    List<RouteInfo> routes = ((RouteGroup) routeInfo4).getRoutes();
                    HashSet hashSet = new HashSet();
                    Iterator<RouteInfo> it4 = routes.iterator();
                    while (it4.hasNext()) {
                        hashSet.add(it4.next().mDescriptorId);
                    }
                    Iterator<Map.Entry<String, MediaRouteProvider.RouteController>> it5 = this.mRouteControllerMap.entrySet().iterator();
                    while (it5.hasNext()) {
                        Map.Entry<String, MediaRouteProvider.RouteController> next3 = it5.next();
                        if (!hashSet.contains(next3.getKey())) {
                            MediaRouteProvider.RouteController value = next3.getValue();
                            value.onUnselect();
                            value.onRelease();
                            it5.remove();
                        }
                    }
                    for (RouteInfo routeInfo5 : routes) {
                        if (!this.mRouteControllerMap.containsKey(routeInfo5.mDescriptorId)) {
                            MediaRouteProvider.RouteController routeControllerOnCreateRouteController = routeInfo5.getProviderInstance().onCreateRouteController(routeInfo5.mDescriptorId, this.mSelectedRoute.mDescriptorId);
                            routeControllerOnCreateRouteController.onSelect();
                            this.mRouteControllerMap.put(routeInfo5.mDescriptorId, routeControllerOnCreateRouteController);
                        }
                    }
                }
                updatePlaybackInfoFromSelectedRoute();
            }
        }

        RouteInfo chooseFallbackRoute() {
            Iterator<RouteInfo> it2 = this.mRoutes.iterator();
            while (it2.hasNext()) {
                RouteInfo next = it2.next();
                if (next != this.mDefaultRoute && isSystemLiveAudioOnlyRoute(next) && next.isSelectable()) {
                    return next;
                }
            }
            return this.mDefaultRoute;
        }

        private boolean isSystemLiveAudioOnlyRoute(RouteInfo routeInfo) {
            return routeInfo.getProviderInstance() == this.mSystemProvider && routeInfo.supportsControlCategory(MediaControlIntent.CATEGORY_LIVE_AUDIO) && !routeInfo.supportsControlCategory(MediaControlIntent.CATEGORY_LIVE_VIDEO);
        }

        private boolean isSystemDefaultRoute(RouteInfo routeInfo) {
            return routeInfo.getProviderInstance() == this.mSystemProvider && routeInfo.mDescriptorId.equals(SystemMediaRouteProvider.DEFAULT_ROUTE_ID);
        }

        private void setSelectedRouteInternal(RouteInfo routeInfo, int i) {
            if (MediaRouter.sGlobal == null || (this.mBluetoothRoute != null && routeInfo.isDefault())) {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                StringBuilder sb = new StringBuilder();
                for (int i2 = 3; i2 < stackTrace.length; i2++) {
                    StackTraceElement stackTraceElement = stackTrace[i2];
                    sb.append(stackTraceElement.getClassName());
                    sb.append(".");
                    sb.append(stackTraceElement.getMethodName());
                    sb.append(":");
                    sb.append(stackTraceElement.getLineNumber());
                    sb.append(CustomCopticTextView.TWO_CHINESE_BLANK);
                }
                if (MediaRouter.sGlobal == null) {
                    Log.w(MediaRouter.TAG, "setSelectedRouteInternal is called while sGlobal is null: pkgName=" + this.mApplicationContext.getPackageName() + ", callers=" + sb.toString());
                } else {
                    Log.w(MediaRouter.TAG, "Default route is selected while a BT route is available: pkgName=" + this.mApplicationContext.getPackageName() + ", callers=" + sb.toString());
                }
            }
            RouteInfo routeInfo2 = this.mSelectedRoute;
            if (routeInfo2 != routeInfo) {
                if (routeInfo2 != null) {
                    if (MediaRouter.DEBUG) {
                        Log.d(MediaRouter.TAG, "Route unselected: " + this.mSelectedRoute + " reason: " + i);
                    }
                    this.mCallbackHandler.post(263, this.mSelectedRoute, i);
                    MediaRouteProvider.RouteController routeController = this.mSelectedRouteController;
                    if (routeController != null) {
                        routeController.onUnselect(i);
                        this.mSelectedRouteController.onRelease();
                        this.mSelectedRouteController = null;
                    }
                    if (!this.mRouteControllerMap.isEmpty()) {
                        for (MediaRouteProvider.RouteController routeController2 : this.mRouteControllerMap.values()) {
                            routeController2.onUnselect(i);
                            routeController2.onRelease();
                        }
                        this.mRouteControllerMap.clear();
                    }
                }
                this.mSelectedRoute = routeInfo;
                MediaRouteProvider.RouteController routeControllerOnCreateRouteController = routeInfo.getProviderInstance().onCreateRouteController(routeInfo.mDescriptorId);
                this.mSelectedRouteController = routeControllerOnCreateRouteController;
                if (routeControllerOnCreateRouteController != null) {
                    routeControllerOnCreateRouteController.onSelect();
                }
                if (MediaRouter.DEBUG) {
                    Log.d(MediaRouter.TAG, "Route selected: " + this.mSelectedRoute);
                }
                this.mCallbackHandler.post(262, this.mSelectedRoute);
                RouteInfo routeInfo3 = this.mSelectedRoute;
                if (routeInfo3 instanceof RouteGroup) {
                    List<RouteInfo> routes = ((RouteGroup) routeInfo3).getRoutes();
                    this.mRouteControllerMap.clear();
                    for (RouteInfo routeInfo4 : routes) {
                        MediaRouteProvider.RouteController routeControllerOnCreateRouteController2 = routeInfo4.getProviderInstance().onCreateRouteController(routeInfo4.mDescriptorId, this.mSelectedRoute.mDescriptorId);
                        routeControllerOnCreateRouteController2.onSelect();
                        this.mRouteControllerMap.put(routeInfo4.mDescriptorId, routeControllerOnCreateRouteController2);
                    }
                }
                updatePlaybackInfoFromSelectedRoute();
            }
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.SyncCallback
        public void onSystemRouteSelectedByDescriptorId(String str) {
            ProviderInfo providerInfo;
            int iFindRouteByDescriptorId;
            this.mCallbackHandler.removeMessages(262);
            int iFindProviderInfo = findProviderInfo(this.mSystemProvider);
            if (iFindProviderInfo < 0 || (iFindRouteByDescriptorId = (providerInfo = this.mProviders.get(iFindProviderInfo)).findRouteByDescriptorId(str)) < 0) {
                return;
            }
            providerInfo.mRoutes.get(iFindRouteByDescriptorId).select();
        }

        public void addRemoteControlClient(Object obj) {
            if (findRemoteControlClientRecord(obj) < 0) {
                this.mRemoteControlClients.add(new RemoteControlClientRecord(obj));
            }
        }

        public void removeRemoteControlClient(Object obj) {
            int iFindRemoteControlClientRecord = findRemoteControlClientRecord(obj);
            if (iFindRemoteControlClientRecord >= 0) {
                this.mRemoteControlClients.remove(iFindRemoteControlClientRecord).disconnect();
            }
        }

        public void setMediaSession(Object obj) {
            setMediaSessionRecord(obj != null ? new MediaSessionRecord(this, obj) : null);
        }

        public void setMediaSessionCompat(MediaSessionCompat mediaSessionCompat) {
            this.mCompatSession = mediaSessionCompat;
            if (Build.VERSION.SDK_INT >= 21) {
                setMediaSessionRecord(mediaSessionCompat != null ? new MediaSessionRecord(mediaSessionCompat) : null);
                return;
            }
            if (Build.VERSION.SDK_INT >= 14) {
                MediaSessionCompat mediaSessionCompat2 = this.mRccMediaSession;
                if (mediaSessionCompat2 != null) {
                    removeRemoteControlClient(mediaSessionCompat2.getRemoteControlClient());
                    this.mRccMediaSession.removeOnActiveChangeListener(this.mSessionActiveListener);
                }
                this.mRccMediaSession = mediaSessionCompat;
                if (mediaSessionCompat != null) {
                    mediaSessionCompat.addOnActiveChangeListener(this.mSessionActiveListener);
                    if (mediaSessionCompat.isActive()) {
                        addRemoteControlClient(mediaSessionCompat.getRemoteControlClient());
                    }
                }
            }
        }

        private void setMediaSessionRecord(MediaSessionRecord mediaSessionRecord) {
            MediaSessionRecord mediaSessionRecord2 = this.mMediaSession;
            if (mediaSessionRecord2 != null) {
                mediaSessionRecord2.clearVolumeHandling();
            }
            this.mMediaSession = mediaSessionRecord;
            if (mediaSessionRecord != null) {
                updatePlaybackInfoFromSelectedRoute();
            }
        }

        public MediaSessionCompat.Token getMediaSessionToken() {
            MediaSessionRecord mediaSessionRecord = this.mMediaSession;
            if (mediaSessionRecord != null) {
                return mediaSessionRecord.getToken();
            }
            MediaSessionCompat mediaSessionCompat = this.mCompatSession;
            if (mediaSessionCompat != null) {
                return mediaSessionCompat.getSessionToken();
            }
            return null;
        }

        private int findRemoteControlClientRecord(Object obj) {
            int size = this.mRemoteControlClients.size();
            for (int i = 0; i < size; i++) {
                if (this.mRemoteControlClients.get(i).getRemoteControlClient() == obj) {
                    return i;
                }
            }
            return -1;
        }

        private void updatePlaybackInfoFromSelectedRoute() {
            RouteInfo routeInfo = this.mSelectedRoute;
            if (routeInfo != null) {
                this.mPlaybackInfo.volume = routeInfo.getVolume();
                this.mPlaybackInfo.volumeMax = this.mSelectedRoute.getVolumeMax();
                this.mPlaybackInfo.volumeHandling = this.mSelectedRoute.getVolumeHandling();
                this.mPlaybackInfo.playbackStream = this.mSelectedRoute.getPlaybackStream();
                this.mPlaybackInfo.playbackType = this.mSelectedRoute.getPlaybackType();
                int size = this.mRemoteControlClients.size();
                for (int i = 0; i < size; i++) {
                    this.mRemoteControlClients.get(i).updatePlaybackInfo();
                }
                if (this.mMediaSession != null) {
                    if (this.mSelectedRoute == getDefaultRoute() || this.mSelectedRoute == getBluetoothRoute()) {
                        this.mMediaSession.clearVolumeHandling();
                        return;
                    } else {
                        this.mMediaSession.configureVolume(this.mPlaybackInfo.volumeHandling == 1 ? 2 : 0, this.mPlaybackInfo.volumeMax, this.mPlaybackInfo.volume);
                        return;
                    }
                }
                return;
            }
            MediaSessionRecord mediaSessionRecord = this.mMediaSession;
            if (mediaSessionRecord != null) {
                mediaSessionRecord.clearVolumeHandling();
            }
        }

        private final class ProviderCallback extends MediaRouteProvider.Callback {
            ProviderCallback() {
            }

            @Override // androidx.mediarouter.media.MediaRouteProvider.Callback
            public void onDescriptorChanged(MediaRouteProvider mediaRouteProvider, MediaRouteProviderDescriptor mediaRouteProviderDescriptor) {
                GlobalMediaRouter.this.updateProviderDescriptor(mediaRouteProvider, mediaRouteProviderDescriptor);
            }
        }

        private final class MediaSessionRecord {
            private int mControlType;
            private int mMaxVolume;
            private final MediaSessionCompat mMsCompat;
            private VolumeProviderCompat mVpCompat;

            MediaSessionRecord(GlobalMediaRouter globalMediaRouter, Object obj) {
                this(MediaSessionCompat.fromMediaSession(globalMediaRouter.mApplicationContext, obj));
            }

            MediaSessionRecord(MediaSessionCompat mediaSessionCompat) {
                this.mMsCompat = mediaSessionCompat;
            }

            public void configureVolume(int i, int i2, int i3) {
                if (this.mMsCompat != null) {
                    VolumeProviderCompat volumeProviderCompat = this.mVpCompat;
                    if (volumeProviderCompat != null && i == this.mControlType && i2 == this.mMaxVolume) {
                        volumeProviderCompat.setCurrentVolume(i3);
                        return;
                    }
                    VolumeProviderCompat volumeProviderCompat2 = new VolumeProviderCompat(i, i2, i3) { // from class: androidx.mediarouter.media.MediaRouter.GlobalMediaRouter.MediaSessionRecord.1
                        @Override // androidx.media.VolumeProviderCompat
                        public void onSetVolumeTo(final int i4) {
                            GlobalMediaRouter.this.mCallbackHandler.post(new Runnable() { // from class: androidx.mediarouter.media.MediaRouter.GlobalMediaRouter.MediaSessionRecord.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (GlobalMediaRouter.this.mSelectedRoute != null) {
                                        GlobalMediaRouter.this.mSelectedRoute.requestSetVolume(i4);
                                    }
                                }
                            });
                        }

                        @Override // androidx.media.VolumeProviderCompat
                        public void onAdjustVolume(final int i4) {
                            GlobalMediaRouter.this.mCallbackHandler.post(new Runnable() { // from class: androidx.mediarouter.media.MediaRouter.GlobalMediaRouter.MediaSessionRecord.1.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (GlobalMediaRouter.this.mSelectedRoute != null) {
                                        GlobalMediaRouter.this.mSelectedRoute.requestUpdateVolume(i4);
                                    }
                                }
                            });
                        }
                    };
                    this.mVpCompat = volumeProviderCompat2;
                    this.mMsCompat.setPlaybackToRemote(volumeProviderCompat2);
                }
            }

            public void clearVolumeHandling() {
                MediaSessionCompat mediaSessionCompat = this.mMsCompat;
                if (mediaSessionCompat != null) {
                    mediaSessionCompat.setPlaybackToLocal(GlobalMediaRouter.this.mPlaybackInfo.playbackStream);
                    this.mVpCompat = null;
                }
            }

            public MediaSessionCompat.Token getToken() {
                MediaSessionCompat mediaSessionCompat = this.mMsCompat;
                if (mediaSessionCompat != null) {
                    return mediaSessionCompat.getSessionToken();
                }
                return null;
            }
        }

        private final class RemoteControlClientRecord implements RemoteControlClientCompat.VolumeCallback {
            private boolean mDisconnected;
            private final RemoteControlClientCompat mRccCompat;

            public RemoteControlClientRecord(Object obj) {
                RemoteControlClientCompat remoteControlClientCompatObtain = RemoteControlClientCompat.obtain(GlobalMediaRouter.this.mApplicationContext, obj);
                this.mRccCompat = remoteControlClientCompatObtain;
                remoteControlClientCompatObtain.setVolumeCallback(this);
                updatePlaybackInfo();
            }

            public Object getRemoteControlClient() {
                return this.mRccCompat.getRemoteControlClient();
            }

            public void disconnect() {
                this.mDisconnected = true;
                this.mRccCompat.setVolumeCallback(null);
            }

            public void updatePlaybackInfo() {
                this.mRccCompat.setPlaybackInfo(GlobalMediaRouter.this.mPlaybackInfo);
            }

            @Override // androidx.mediarouter.media.RemoteControlClientCompat.VolumeCallback
            public void onVolumeSetRequest(int i) {
                if (this.mDisconnected || GlobalMediaRouter.this.mSelectedRoute == null) {
                    return;
                }
                GlobalMediaRouter.this.mSelectedRoute.requestSetVolume(i);
            }

            @Override // androidx.mediarouter.media.RemoteControlClientCompat.VolumeCallback
            public void onVolumeUpdateRequest(int i) {
                if (this.mDisconnected || GlobalMediaRouter.this.mSelectedRoute == null) {
                    return;
                }
                GlobalMediaRouter.this.mSelectedRoute.requestUpdateVolume(i);
            }
        }

        private final class CallbackHandler extends Handler {
            public static final int MSG_PROVIDER_ADDED = 513;
            public static final int MSG_PROVIDER_CHANGED = 515;
            public static final int MSG_PROVIDER_REMOVED = 514;
            public static final int MSG_ROUTE_ADDED = 257;
            public static final int MSG_ROUTE_CHANGED = 259;
            public static final int MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED = 261;
            public static final int MSG_ROUTE_REMOVED = 258;
            public static final int MSG_ROUTE_SELECTED = 262;
            public static final int MSG_ROUTE_UNSELECTED = 263;
            public static final int MSG_ROUTE_VOLUME_CHANGED = 260;
            private static final int MSG_TYPE_MASK = 65280;
            private static final int MSG_TYPE_PROVIDER = 512;
            private static final int MSG_TYPE_ROUTE = 256;
            private final ArrayList<CallbackRecord> mTempCallbackRecords = new ArrayList<>();

            CallbackHandler() {
            }

            public void post(int i, Object obj) {
                obtainMessage(i, obj).sendToTarget();
            }

            public void post(int i, Object obj, int i2) {
                Message messageObtainMessage = obtainMessage(i, obj);
                messageObtainMessage.arg1 = i2;
                messageObtainMessage.sendToTarget();
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what;
                Object obj = message.obj;
                int i2 = message.arg1;
                if (i == 259 && GlobalMediaRouter.this.getSelectedRoute().getId().equals(((RouteInfo) obj).getId())) {
                    GlobalMediaRouter.this.updateSelectedRouteIfNeeded(true);
                }
                syncWithSystemProvider(i, obj);
                try {
                    int size = GlobalMediaRouter.this.mRouters.size();
                    while (true) {
                        size--;
                        if (size < 0) {
                            break;
                        }
                        MediaRouter mediaRouter = GlobalMediaRouter.this.mRouters.get(size).get();
                        if (mediaRouter == null) {
                            GlobalMediaRouter.this.mRouters.remove(size);
                        } else {
                            this.mTempCallbackRecords.addAll(mediaRouter.mCallbackRecords);
                        }
                    }
                    int size2 = this.mTempCallbackRecords.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        invokeCallback(this.mTempCallbackRecords.get(i3), i, obj, i2);
                    }
                } finally {
                    this.mTempCallbackRecords.clear();
                }
            }

            private void syncWithSystemProvider(int i, Object obj) {
                if (i != 262) {
                    switch (i) {
                        case 257:
                            GlobalMediaRouter.this.mSystemProvider.onSyncRouteAdded((RouteInfo) obj);
                            break;
                        case 258:
                            GlobalMediaRouter.this.mSystemProvider.onSyncRouteRemoved((RouteInfo) obj);
                            break;
                        case 259:
                            GlobalMediaRouter.this.mSystemProvider.onSyncRouteChanged((RouteInfo) obj);
                            break;
                    }
                }
                GlobalMediaRouter.this.mSystemProvider.onSyncRouteSelected((RouteInfo) obj);
            }

            private void invokeCallback(CallbackRecord callbackRecord, int i, Object obj, int i2) {
                MediaRouter mediaRouter = callbackRecord.mRouter;
                Callback callback = callbackRecord.mCallback;
                int i3 = 65280 & i;
                if (i3 != 256) {
                    if (i3 != 512) {
                        return;
                    }
                    ProviderInfo providerInfo = (ProviderInfo) obj;
                    switch (i) {
                        case 513:
                            callback.onProviderAdded(mediaRouter, providerInfo);
                            break;
                        case 514:
                            callback.onProviderRemoved(mediaRouter, providerInfo);
                            break;
                        case 515:
                            callback.onProviderChanged(mediaRouter, providerInfo);
                            break;
                    }
                }
                RouteInfo routeInfo = (RouteInfo) obj;
                if (callbackRecord.filterRouteEvent(routeInfo)) {
                    switch (i) {
                        case 257:
                            callback.onRouteAdded(mediaRouter, routeInfo);
                            break;
                        case 258:
                            callback.onRouteRemoved(mediaRouter, routeInfo);
                            break;
                        case 259:
                            callback.onRouteChanged(mediaRouter, routeInfo);
                            break;
                        case 260:
                            callback.onRouteVolumeChanged(mediaRouter, routeInfo);
                            break;
                        case 261:
                            callback.onRoutePresentationDisplayChanged(mediaRouter, routeInfo);
                            break;
                        case 262:
                            callback.onRouteSelected(mediaRouter, routeInfo);
                            break;
                        case 263:
                            callback.onRouteUnselected(mediaRouter, routeInfo, i2);
                            break;
                    }
                }
            }
        }
    }
}
