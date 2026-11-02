package it.piapostolos.android.pinotes.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.text.TextUtils;
import com.ibm.icu.impl.locale.BaseLocale;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import java.util.Locale;

/* loaded from: classes3.dex */
public class LanguageHelper {
    private LanguageHelper() {
    }

    public static Context updateLanguage(Context context, String str) {
        Locale locale;
        SharedPreferences sharedPreferences = context.getSharedPreferences("PiNotes", 4);
        String string = sharedPreferences.getString(ConstantsBase.PREF_LANG, "");
        if (TextUtils.isEmpty(string) && str == null) {
            locale = Locale.getDefault();
            sharedPreferences.edit().putString(ConstantsBase.PREF_LANG, locale.toString()).commit();
        } else if (str != null) {
            Locale locale2 = getLocale(str);
            sharedPreferences.edit().putString(ConstantsBase.PREF_LANG, str).commit();
            locale = locale2;
        } else {
            locale = !TextUtils.isEmpty(string) ? getLocale(string) : null;
        }
        return setLocale(context, locale);
    }

    private static Context setLocale(Context context, Locale locale) {
        Configuration configuration = context.getResources().getConfiguration();
        configuration.locale = locale;
        context.getResources().updateConfiguration(configuration, null);
        return context;
    }

    private static Locale getLocale(String str) {
        if (str.contains(BaseLocale.SEP)) {
            return new Locale(str.split(BaseLocale.SEP)[0], str.split(BaseLocale.SEP)[1]);
        }
        return new Locale(str);
    }

    static String getLocalizedString(Context context, String str, int i) {
        if (str.equals(getCurrentLocaleAsString(context))) {
            return context.getResources().getString(i);
        }
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(getLocale(str));
        return context.createConfigurationContext(configuration).getResources().getString(i);
    }

    public static String getCurrentLocaleAsString(Context context) {
        return getCurrentLocale(context).toString();
    }

    public static Locale getCurrentLocale(Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return context.getResources().getConfiguration().getLocales().get(0);
        }
        return context.getResources().getConfiguration().locale;
    }
}
