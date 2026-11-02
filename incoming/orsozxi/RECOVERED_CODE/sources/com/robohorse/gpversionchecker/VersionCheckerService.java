package com.robohorse.gpversionchecker;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.robohorse.gpversionchecker.debug.ALog;
import com.robohorse.gpversionchecker.domain.Version;
import com.robohorse.gpversionchecker.utils.DataParser;
import java.io.IOException;
import java.util.Locale;
import org.jsoup.Jsoup;

/* loaded from: classes2.dex */
public class VersionCheckerService extends IntentService {
    private static final int CONNECTION_TIMEOUT = 30000;
    private static final String REFERRER = "http://www.google.com";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";

    public VersionCheckerService() {
        super("GPVersionChecker");
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        Version versionObtainDataFromGooglePlay = obtainDataFromGooglePlay();
        if (versionObtainDataFromGooglePlay != null) {
            ALog.d("Response received: " + versionObtainDataFromGooglePlay.toString());
            GPVersionChecker.onResponseReceived(versionObtainDataFromGooglePlay, null);
        }
        stopSelf();
    }

    private Version obtainDataFromGooglePlay() {
        try {
            String str = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
            Version versionObtainDataFromGooglePlayWithException = obtainDataFromGooglePlayWithException(true, str);
            return new Version.Builder(obtainDataFromGooglePlayWithException(false, str)).setNeedToUpdate(Integer.parseInt(DataParser.replaceNonDigits(versionObtainDataFromGooglePlayWithException.getNewVersionCode())) > Integer.parseInt(DataParser.replaceNonDigits(str))).setNewVersionCode(versionObtainDataFromGooglePlayWithException.getNewVersionCode()).build();
        } catch (Throwable th) {
            th.printStackTrace();
            GPVersionChecker.onResponseReceived(null, th);
            return null;
        }
    }

    private Version obtainDataFromGooglePlayWithException(boolean z, String str) throws IOException, NumberFormatException {
        Context applicationContext = getApplicationContext();
        String packageName = applicationContext.getPackageName();
        String str2 = applicationContext.getString(R.string.gpvch_google_play_url) + packageName + "&hl=" + (z ? Locale.ENGLISH : Locale.getDefault()).getLanguage();
        ALog.d("request params: package - " + packageName + ", current app version: " + str);
        return new DataParser().parse(Jsoup.connect(str2).timeout(CONNECTION_TIMEOUT).userAgent(USER_AGENT).referrer(REFERRER).get(), str2);
    }
}
