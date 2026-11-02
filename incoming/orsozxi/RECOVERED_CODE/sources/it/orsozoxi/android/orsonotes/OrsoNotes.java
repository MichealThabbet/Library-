package it.orsozoxi.android.orsonotes;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import androidx.multidex.MultiDexApplication;
import it.orsozoxi.android.orsonotes.helpers.LanguageHelper;

/* loaded from: classes3.dex */
public class OrsoNotes extends MultiDexApplication {
    private static Context mContext;
    static SharedPreferences prefs;

    public static boolean isDebugBuild() {
        return false;
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static SharedPreferences getSharedPreferences() {
        return getAppContext().getSharedPreferences("OrsoNotes", 4);
    }

    @Override // androidx.multidex.MultiDexApplication, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        prefs = getSharedPreferences("OrsoNotes", 4);
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        LanguageHelper.updateLanguage(this, "en");
    }
}
