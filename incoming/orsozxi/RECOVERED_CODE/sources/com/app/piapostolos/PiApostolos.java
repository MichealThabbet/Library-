package com.app.piapostolos;

import android.content.Context;
import android.os.StrictMode;
import androidx.multidex.MultiDex;
import cat.ereza.customactivityoncrash.config.CaocConfig;
import com.app.piapostolos.Youtube.Beans.PlaylistItemDetails;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.huawei.hms.analytics.HiAnalytics;
import com.huawei.hms.analytics.HiAnalyticsInstance;
import com.huawei.hms.analytics.HiAnalyticsTools;
import com.huawei.hms.push.HmsMessaging;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import it.piapostolos.android.pinotes.PiNotes;
import it.piapostolos.android.pinotes.R2;
import org.acra.annotation.AcraCore;
import org.acra.annotation.AcraToast;

@AcraCore(buildConfigClass = it.piapostolos.android.pinotes.BuildConfig.class)
@AcraToast(resText = R2.string.crash_toast)
/* loaded from: classes.dex */
public class PiApostolos extends PiNotes {
    private static FirebaseAnalytics mFirebaseAnalytics;
    private static HiAnalyticsInstance mHiAnalyticsInstance;
    private static PlaylistItemDetails selectedPlaylistItem;

    public static boolean isPlay_Store() {
        return true;
    }

    public static HiAnalyticsInstance getHiAnalyticsInstance(Context context) {
        HiAnalyticsTools.enableLog();
        if (mHiAnalyticsInstance == null) {
            mHiAnalyticsInstance = HiAnalytics.getInstance(context);
        }
        return mHiAnalyticsInstance;
    }

    @Override // it.piapostolos.android.pinotes.PiNotes, androidx.multidex.MultiDexApplication, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    private void enableStrictMode() {
        if (isDebugBuild()) {
            StrictMode.enableDefaults();
        }
    }

    @Override // it.piapostolos.android.pinotes.PiNotes, android.app.Application
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
        FirebaseApp.initializeApp(this);
        activateFireBase(this);
        if (isPlay_Store()) {
            CaocConfig.Builder.create().backgroundMode(0).showErrorDetails(false).showRestartButton(true).trackActivities(false).logErrorOnRestart(true).errorDrawable(Integer.valueOf(R.mipmap.ic_launcher)).apply();
            FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        }
        app.updateLanguage(this, "en");
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        if (isPlay_Store()) {
            return;
        }
        getHiAnalyticsInstance(this);
        mHiAnalyticsInstance.setAnalyticsEnabled(true);
        mHiAnalyticsInstance.setAutoCollectionEnabled(true);
        mHiAnalyticsInstance.regHmsSvcEvent();
        if (!isGooglePlayServicesAvailable(this)) {
            HmsMessaging.getInstance(this).setAutoInitEnabled(true);
            HmsMessaging.getInstance(this).turnOnPush();
        } else {
            HmsMessaging.getInstance(this).setAutoInitEnabled(false);
            HmsMessaging.getInstance(this).turnOffPush();
        }
    }

    public boolean isGooglePlayServicesAvailable(Context context) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int iIsGooglePlayServicesAvailable = googleApiAvailability.isGooglePlayServicesAvailable(context);
        if (iIsGooglePlayServicesAvailable == 0) {
            return true;
        }
        googleApiAvailability.isUserResolvableError(iIsGooglePlayServicesAvailable);
        return false;
    }

    public static void initImageLoader(Context context) {
        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(context).threadPriority(3).denyCacheImageMultipleSizesInMemory().diskCacheFileNameGenerator(new Md5FileNameGenerator()).diskCacheSize(52428800).tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs().build());
    }

    public static PlaylistItemDetails getSelectedPlaylistItem() {
        return selectedPlaylistItem;
    }

    public static void activateFireBase(Context context) {
        FirebaseApp.initializeApp(context);
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics = firebaseAnalytics;
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }

    public static void setSelectedPlaylistItem(PlaylistItemDetails playlistItemDetails) {
        selectedPlaylistItem = playlistItemDetails;
    }
}
