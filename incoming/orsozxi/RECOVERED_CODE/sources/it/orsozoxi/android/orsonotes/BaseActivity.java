package it.orsozoxi.android.orsonotes;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.ViewConfiguration;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.mediarouter.media.SystemMediaRouteProvider;
import it.orsozoxi.android.orsonotes.helpers.LanguageHelper;
import it.orsozoxi.android.orsonotes.helpers.LogDelegate;
import it.orsozoxi.android.orsonotes.models.Note;
import it.orsozoxi.android.orsonotes.models.PasswordValidator;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import it.orsozoxi.android.orsonotes.utils.Navigation;
import it.orsozoxi.android.orsonotes.utils.PasswordHelper;
import it.orsozoxi.android.orsonotes.widget.ListWidgetProvider;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class BaseActivity extends AppCompatActivity {
    protected static final int TRANSITION_HORIZONTAL = 1;
    protected static final int TRANSITION_VERTICAL = 0;
    protected String navigation;
    protected String navigationTmp;
    protected SharedPreferences prefs;

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(LanguageHelper.updateLanguage(context, null));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        this.prefs = getSharedPreferences("OrsoNotes", 4);
        try {
            ViewConfiguration viewConfiguration = ViewConfiguration.get(getApplicationContext());
            Field declaredField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (declaredField != null) {
                declaredField.setAccessible(true);
                declaredField.setBoolean(viewConfiguration, false);
            }
        } catch (Exception e) {
            LogDelegate.w("Just a little issue in physical menu button management", e);
        }
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        this.navigation = this.prefs.getString("navigation", getResources().getStringArray(R.array.navigation_list_codes)[0]);
        LogDelegate.d(this.prefs.getAll().toString());
    }

    protected void showToast(CharSequence charSequence, int i) {
        if (this.prefs.getBoolean("settings_enable_info", true)) {
            Toast.makeText(getApplicationContext(), charSequence, i).show();
        }
    }

    public void requestPassword(Activity activity, List<Note> list, PasswordValidator passwordValidator) {
        boolean z = false;
        if (this.prefs.getBoolean("settings_password_access", false)) {
            passwordValidator.onPasswordValidated(PasswordValidator.Result.SUCCEED);
            return;
        }
        Iterator<Note> it2 = list.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            } else if (it2.next().isLocked().booleanValue()) {
                z = true;
                break;
            }
        }
        if (z) {
            PasswordHelper.requestPassword(activity, passwordValidator);
        } else {
            passwordValidator.onPasswordValidated(PasswordValidator.Result.SUCCEED);
        }
    }

    public boolean updateNavigation(String str) {
        if (str.equals(this.navigationTmp)) {
            return false;
        }
        if (this.navigationTmp == null && Navigation.getNavigationText().equals(str)) {
            return false;
        }
        this.prefs.edit().putString("navigation", str).apply();
        this.navigation = str;
        this.navigationTmp = null;
        return true;
    }

    private String getStringResourceByName(String str) {
        return getString(getResources().getIdentifier(str, "string", getApplicationContext().getPackageName()));
    }

    public static void notifyAppWidgets(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, (Class<?>) ListWidgetProvider.class));
        LogDelegate.d("Notifies AppWidget data changed for widgets " + Arrays.toString(appWidgetIds));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(ConstantsBase.INTENT_UPDATE_DASHCLOCK));
    }

    protected void animateTransition(FragmentTransaction fragmentTransaction, int i) {
        if (i == 1) {
            fragmentTransaction.setCustomAnimations(R.anim.fade_in_support, R.anim.fade_out_support, R.anim.fade_in_support, R.anim.fade_out_support);
        }
        if (i == 0) {
            fragmentTransaction.setCustomAnimations(R.anim.anim_in, R.anim.anim_out, R.anim.anim_in_pop, R.anim.anim_out_pop);
        }
    }

    protected void setActionBarTitle(String str) {
        TextView textView = (TextView) getWindow().findViewById(Resources.getSystem().getIdentifier("action_bar_title", "ID", SystemMediaRouteProvider.PACKAGE_NAME));
        Typeface typefaceCreateFromAsset = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        if (textView != null) {
            textView.setTypeface(typefaceCreateFromAsset);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(str);
        }
    }

    public String getNavigationTmp() {
        return this.navigationTmp;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return i == 82 || super.onKeyDown(i, keyEvent);
    }
}
