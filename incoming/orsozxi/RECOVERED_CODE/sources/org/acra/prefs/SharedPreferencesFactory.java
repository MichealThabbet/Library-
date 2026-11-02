package org.acra.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import org.acra.ACRA;
import org.acra.config.CoreConfiguration;

/* loaded from: classes3.dex */
public class SharedPreferencesFactory {
    private final CoreConfiguration config;
    private final Context context;

    public SharedPreferencesFactory(Context context, CoreConfiguration coreConfiguration) {
        this.context = context;
        this.config = coreConfiguration;
    }

    public static boolean shouldEnableACRA(SharedPreferences sharedPreferences) {
        try {
            return sharedPreferences.getBoolean(ACRA.PREF_ENABLE_ACRA, sharedPreferences.getBoolean(ACRA.PREF_DISABLE_ACRA, false) ? false : true);
        } catch (Exception unused) {
            return true;
        }
    }

    public SharedPreferences create() {
        if (this.context == null) {
            throw new IllegalStateException("Cannot call ACRA.getACRASharedPreferences() before ACRA.init().");
        }
        if (!"".equals(this.config.sharedPreferencesName())) {
            return this.context.getSharedPreferences(this.config.sharedPreferencesName(), 0);
        }
        return PreferenceManager.getDefaultSharedPreferences(this.context);
    }
}
