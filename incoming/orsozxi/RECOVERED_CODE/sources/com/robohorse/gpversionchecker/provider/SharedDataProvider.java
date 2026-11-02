package com.robohorse.gpversionchecker.provider;

import android.content.SharedPreferences;
import com.robohorse.gpversionchecker.domain.Version;
import com.robohorse.gpversionchecker.utils.DateFormatUtils;

/* loaded from: classes2.dex */
public class SharedDataProvider {
    private static final String GPVCH_TIME = "gpvch_time";
    private static final String GPVCH_VERSION = "gpvch_version";
    private final DateFormatUtils formatUtils;
    private final SharedPreferences sharedPreferences;

    public SharedDataProvider(SharedPreferences sharedPreferences, DateFormatUtils dateFormatUtils) {
        this.sharedPreferences = sharedPreferences;
        this.formatUtils = dateFormatUtils;
    }

    public void saveCurrentDate() {
        this.sharedPreferences.edit().putLong(GPVCH_TIME, this.formatUtils.formatTodayDate().getTime()).apply();
    }

    public long provideLastCheckTime() {
        return this.sharedPreferences.getLong(GPVCH_TIME, 0L);
    }

    public void saveCurrentVersion(Version version) {
        this.sharedPreferences.edit().putString(GPVCH_VERSION, version.getNewVersionCode()).apply();
    }

    public String provideLastVersionCode() {
        return this.sharedPreferences.getString(GPVCH_VERSION, null);
    }
}
