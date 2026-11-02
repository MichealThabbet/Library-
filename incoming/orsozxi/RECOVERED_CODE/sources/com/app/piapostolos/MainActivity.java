package com.app.piapostolos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import com.app.piapostolos.Alarm.Alarm;
import com.app.piapostolos.Alarm.Database.Database;
import com.app.piapostolos.Alarm.Services.AlarmServiceBroadcastReciever;
import com.app.piapostolos.Beans.DailyMsg;
import com.app.piapostolos.Beans.RandomDaily;
import com.app.piapostolos.BibleDataBase.UseDatabaseHandler;
import com.app.piapostolos.Others.FileExcel;
import com.app.piapostolos.Others.SavePrefs;
import com.app.piapostolos.Views.CircleImageView;
import com.app.piapostolos.Views.CircleLayout;
import com.app.piapostolos.Views.SnowFlakesLayout;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.github.clans.fab.FloatingActionMenu;
import com.ibm.icu.util.CopticCalendar;
import com.kobakei.ratethisapp.RateThisApp;
import com.navdrawer.SimpleSideDrawer;
import com.robohorse.gpversionchecker.GPVersionChecker;
import com.robohorse.gpversionchecker.domain.CheckingStrategy;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import it.orsozoxi.android.orsonotes.NotesMainActivity;
import it.orsozoxi.android.orsonotes.R2;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.Callable;
import net.sqlcipher.database.SQLiteDatabase;
import org.apache.http.protocol.HTTP;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/* loaded from: classes.dex */
public class MainActivity extends BaseActivity implements CircleLayout.OnItemSelectedListener, CircleLayout.OnItemClickListener, CircleLayout.OnRotationFinishedListener, CircleLayout.OnCenterClickListener, View.OnClickListener {
    CallbackManager mCallbackManager;
    private FloatingActionMenu mHomeFloatingActionMenu;
    private TextView selectedTextView;
    private SnowFlakesLayout snowFlakesLayout;
    boolean isShowCanta = false;
    String msg = "";

    private String dayOfWeek(int i) {
        return i == 1 ? "Sun" : i == 2 ? "Mon" : i == 3 ? "Tue" : i == 4 ? "Wed" : i == 5 ? "Thu" : i == 6 ? "Fri" : "Sat";
    }

    public String getmonth(int i) {
        return i == 1 ? "jan" : i == 2 ? "feb" : i == 3 ? "Mar" : i == 4 ? "Apr" : i == 5 ? "May" : i == 6 ? "Jun" : i == 7 ? "Jul" : i == 8 ? "Aug" : i == 9 ? "Sep" : i == 10 ? "Oct" : i == 11 ? "Nov" : i == 12 ? "Dec" : "";
    }

    @Override // com.app.piapostolos.Views.CircleLayout.OnCenterClickListener
    public void onCenterClick() {
    }

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) throws IOException {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.tv_header_title)).setText(R.string.app_name);
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.MainActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        findViewById(R.id.iv_daily_message).setVisibility(0);
        findViewById(R.id.iv_daily_message).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.MainActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainActivity.this.manageDailyMessage();
            }
        });
        TextView textView = (TextView) findViewById(R.id.tv_melady);
        TextView textView2 = (TextView) findViewById(R.id.tv_cop);
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, calendar.get(11));
        CopticCalendar copticCalendar = new CopticCalendar();
        textView.setText(dayOfWeekArabic(dayOfWeek(calendar.get(7))) + " " + calendar.get(5) + " " + getmonthArabic(calendar.get(2) + 1) + " " + calendar.get(1));
        StringBuilder sb = new StringBuilder();
        sb.append(copticCalendar.get(5));
        sb.append(" ");
        sb.append(getmonthCoptic(copticCalendar.get(2) + 1));
        sb.append(" ");
        sb.append(copticCalendar.get(1));
        textView2.setText(sb.toString());
        initView();
        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, R2.color.abc_tint_btn_checkable);
        } else {
            checkFirstTimeRandom();
        }
        if (PiApostolos.isPlay_Store()) {
            new GPVersionChecker.Builder(this).setCheckingStrategy(CheckingStrategy.ONE_PER_DAY).create();
        }
        FacebookSdk.sdkInitialize(getApplicationContext());
        initFloating();
        initChristmas();
        if (PiApostolos.isPlay_Store()) {
            RateThisApp.init(new RateThisApp.Config(3, 5));
        }
        copyDatabasesTask();
    }

    private void initChristmas() {
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(2);
        int i2 = calendar.get(5);
        if (i == 11 && i2 >= 25) {
            this.isShowCanta = true;
        } else if (i == 0 && i2 < 6) {
            this.isShowCanta = true;
        } else {
            this.isShowCanta = false;
        }
        if (this.isShowCanta) {
            findViewById(R.id.iv_iceman).setVisibility(0);
            findViewById(R.id.iv_santa).setVisibility(0);
            SnowFlakesLayout snowFlakesLayout = (SnowFlakesLayout) findViewById(R.id.snowflakelayout);
            this.snowFlakesLayout = snowFlakesLayout;
            snowFlakesLayout.init();
            this.snowFlakesLayout.setWholeAnimateTiming(3000000);
            this.snowFlakesLayout.setAnimateDuration(10000);
            this.snowFlakesLayout.setGenerateSnowTiming(500);
            this.snowFlakesLayout.setRandomSnowSizeRange(40, 1);
            this.snowFlakesLayout.setImageResourceID(R.drawable.snow_flakes_pic);
            this.snowFlakesLayout.setEnableRandomCurving(true);
            this.snowFlakesLayout.setEnableAlphaFade(true);
            this.snowFlakesLayout.startSnowing();
            this.snowFlakesLayout.setVisibility(0);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        SnowFlakesLayout snowFlakesLayout = this.snowFlakesLayout;
        if (snowFlakesLayout == null || !this.isShowCanta) {
            return;
        }
        snowFlakesLayout.stopSnowing();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        if (PiApostolos.isPlay_Store()) {
            RateThisApp.onStart(this);
            RateThisApp.showRateDialogIfNeeded(this);
        }
    }

    private void copyDatabasesTask() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.plz_wait));
        progressDialog.show();
        progressDialog.setCancelable(false);
        Observable.fromCallable(new Callable() { // from class: com.app.piapostolos.-$$Lambda$MainActivity$XvbGKyCILhfMZ3CyoL1pqLoxeG8
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$copyDatabasesTask$0$MainActivity();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.piapostolos.-$$Lambda$MainActivity$kf2bpXSCKsNotKh6XMznFiEcJkg
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                MainActivity.lambda$copyDatabasesTask$1(progressDialog, (Boolean) obj);
            }
        });
    }

    public /* synthetic */ Boolean lambda$copyDatabasesTask$0$MainActivity() throws Exception {
        copyDataBases();
        return false;
    }

    static /* synthetic */ void lambda$copyDatabasesTask$1(ProgressDialog progressDialog, Boolean bool) throws Exception {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void copyDataBases() {
        SQLiteDatabase.loadLibs(this);
        new UseDatabaseHandler(this);
        try {
            UseDatabaseHandler.getInstance(this).CopyDataBaseFromAsset(getApplicationInfo().dataDir + "/database_/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void postStartUseOrsozoxi() {
        this.mCallbackManager = CallbackManager.Factory.create();
        ShareDialog shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(this.mCallbackManager, new FacebookCallback<Sharer.Result>() { // from class: com.app.piapostolos.MainActivity.3
            @Override // com.facebook.FacebookCallback
            public void onSuccess(Sharer.Result result) {
                Log.d("Orso post", "success");
            }

            @Override // com.facebook.FacebookCallback
            public void onError(FacebookException facebookException) {
                Log.d("Orso post", "error");
            }

            @Override // com.facebook.FacebookCallback
            public void onCancel() {
                Log.d("Orso post", "cancel");
            }
        });
        shareDialog.show(new ShareLinkContent.Builder().setContentUrl(Uri.parse("https://fb.me/895190050590896")).setContentTitle(getString(R.string.app_name)).setImageUrl(Uri.parse("https://www.orsozoxi.net/wp-content/uploads/2015/06/Icon-40@3x.png")).setContentDescription("Now, I 'm using PiApostolos + Katamaras App").build());
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) throws IOException {
        super.onActivityResult(i, i2, intent);
        if (i == 1234) {
            if (Build.VERSION.SDK_INT < 23 || !Settings.canDrawOverlays(this)) {
                return;
            }
            checkFirstTimeRandom();
            return;
        }
        if (i2 == 0 || intent == null) {
            return;
        }
        this.mCallbackManager.onActivityResult(i, i2, intent);
    }

    private void getKeyHash() throws NoSuchAlgorithmException {
        try {
            for (Signature signature : getPackageManager().getPackageInfo(BuildConfig.APPLICATION_ID, 64).signatures) {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(messageDigest.digest(), 0));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException unused) {
        }
    }

    private void initView() {
        CircleLayout circleLayout = (CircleLayout) findViewById(R.id.main_circle_layout);
        circleLayout.setOnItemSelectedListener(this);
        circleLayout.setOnItemClickListener(this);
        circleLayout.setOnRotationFinishedListener(this);
        circleLayout.setOnCenterClickListener(this);
        TextView textView = (TextView) findViewById(R.id.main_selected_textView);
        this.selectedTextView = textView;
        textView.setText(((CircleImageView) circleLayout.getSelectedItem()).getName());
    }

    private void initFloating() {
        FloatingActionMenu floatingActionMenu = (FloatingActionMenu) findViewById(R.id.menu_options);
        this.mHomeFloatingActionMenu = floatingActionMenu;
        floatingActionMenu.setIconAnimated(false);
        this.mHomeFloatingActionMenu.setClosedOnTouchOutside(true);
        this.mHomeFloatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.MainActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainActivity.this.mHomeFloatingActionMenu.toggle(true);
            }
        });
        findViewById(R.id.fb_about).setOnClickListener(this);
        findViewById(R.id.fb_share).setOnClickListener(this);
        findViewById(R.id.fb_follow).setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.fb_about) {
            this.mHomeFloatingActionMenu.toggle(false);
            startActivity(new Intent(this, (Class<?>) MainActivity_About.class));
            return;
        }
        if (id == R.id.fb_follow) {
            this.mHomeFloatingActionMenu.toggle(false);
            new Handler().postDelayed(new Runnable() { // from class: com.app.piapostolos.MainActivity.5
                @Override // java.lang.Runnable
                public void run() {
                    MainActivity.this.startActivity(new Intent(MainActivity.this, (Class<?>) Follow_options.class));
                }
            }, 500L);
        } else {
            if (id != R.id.fb_share) {
                return;
            }
            this.mHomeFloatingActionMenu.toggle(false);
            app.shareText(this, getString(R.string.app_name) + "\nhttps://play.google.com/store/apps/details?id=com.app.piapostolos");
        }
    }

    @Override // com.app.piapostolos.Views.CircleLayout.OnRotationFinishedListener
    public void onRotationFinished(View view, String str) {
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f, view.getWidth() / 2, view.getHeight() / 2);
        rotateAnimation.setDuration(250L);
        view.startAnimation(rotateAnimation);
        findViewById(R.id.Relative_home_main).setBackgroundResource(R.drawable.back_selected);
    }

    @Override // com.app.piapostolos.Views.CircleLayout.OnItemClickListener
    public void onItemClick(View view, String str) {
        switch (view.getId()) {
            case R.id.main_biblee /* 2131296823 */:
                new Handler().postDelayed(new Runnable() { // from class: com.app.piapostolos.MainActivity.6
                    @Override // java.lang.Runnable
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, (Class<?>) Bible_options.class);
                        intent.putExtra("type2", "bib");
                        MainActivity.this.startActivity(intent);
                        MainActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.empty);
                    }
                }, 500L);
                break;
            case R.id.main_gwhara /* 2131296825 */:
                new Handler().postDelayed(new Runnable() { // from class: com.app.piapostolos.MainActivity.8
                    @Override // java.lang.Runnable
                    public void run() {
                        MainActivity.this.startActivity(new Intent(MainActivity.this, (Class<?>) gawhara_options.class));
                        MainActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.empty);
                    }
                }, 500L);
                break;
            case R.id.main_mangalyaa /* 2131296826 */:
                new Handler().postDelayed(new Runnable() { // from class: com.app.piapostolos.MainActivity.7
                    @Override // java.lang.Runnable
                    public void run() {
                        MainActivity.this.startActivity(new Intent(MainActivity.this, (Class<?>) mangalya_options.class));
                        MainActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.empty);
                    }
                }, 500L);
                break;
            case R.id.main_media /* 2131296827 */:
                new Handler().postDelayed(new Runnable() { // from class: com.app.piapostolos.MainActivity.9
                    @Override // java.lang.Runnable
                    public void run() {
                        MainActivity.this.startActivity(new Intent(MainActivity.this, (Class<?>) Media_options.class));
                        MainActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.empty);
                    }
                }, 500L);
                break;
            case R.id.main_messagess /* 2131296828 */:
                startActivity(new Intent(this, (Class<?>) TabMessagesActivity.class));
                break;
            case R.id.main_note_book /* 2131296829 */:
                new Handler().postDelayed(new Runnable() { // from class: com.app.piapostolos.MainActivity.10
                    @Override // java.lang.Runnable
                    public void run() {
                        MainActivity.this.startActivity(new Intent(MainActivity.this, (Class<?>) NotesMainActivity.class));
                    }
                }, 500L);
                break;
        }
    }

    @Override // com.app.piapostolos.Views.CircleLayout.OnItemSelectedListener
    public void onItemSelected(View view, String str) {
        this.selectedTextView.setText(str);
        findViewById(R.id.main_biblee).setSelected(false);
        findViewById(R.id.main_gwhara).setSelected(false);
        findViewById(R.id.main_mangalyaa).setSelected(false);
        findViewById(R.id.main_media).setSelected(false);
        findViewById(R.id.main_note_book).setSelected(false);
        findViewById(R.id.main_messagess).setSelected(false);
        view.getId();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        AppLeftSlider.setCurrentActivity(this);
        SnowFlakesLayout snowFlakesLayout = this.snowFlakesLayout;
        if (snowFlakesLayout != null && this.isShowCanta) {
            snowFlakesLayout.startSnowing();
        }
        super.onResume();
    }

    private void checkFirstTimeRandom() throws IOException {
        RandomDaily randomDaily = (RandomDaily) new SavePrefs((Activity) this, (Class<?>) RandomDaily.class).load();
        if (randomDaily == null) {
            RingtoneManager ringtoneManager = new RingtoneManager((Activity) this);
            ringtoneManager.setType(4);
            Cursor cursor = ringtoneManager.getCursor();
            String[] strArr = new String[cursor.getCount() + 1];
            strArr[0] = getString(R.string.silent);
            String[] strArr2 = new String[cursor.getCount() + 1];
            strArr2[0] = "";
            if (cursor.moveToFirst()) {
                do {
                    strArr[cursor.getPosition() + 1] = ringtoneManager.getRingtone(cursor.getPosition()).getTitle(this);
                    strArr2[cursor.getPosition() + 1] = ringtoneManager.getRingtoneUri(cursor.getPosition()).toString();
                } while (cursor.moveToNext());
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(11, 11);
            calendar.set(12, 0);
            Alarm alarm = new Alarm();
            alarm.setAlarmActive(true);
            alarm.setAlarmName(getString(R.string.s_daily_msg));
            alarm.setAlarmTime(calendar);
            alarm.setAlarmTonePath(strArr2[0]);
            alarm.setVibrate(true);
            alarm.setDays(new Alarm.Day[]{Alarm.Day.MONDAY, Alarm.Day.TUESDAY, Alarm.Day.WEDNESDAY, Alarm.Day.THURSDAY, Alarm.Day.FRIDAY, Alarm.Day.SATURDAY, Alarm.Day.SUNDAY});
            Database.init(getApplicationContext());
            Database.create(alarm);
            callMathAlarmScheduleService();
            SavePrefs savePrefs = new SavePrefs((Activity) this, (Class<?>) RandomDaily.class);
            RandomDaily randomDaily2 = new RandomDaily();
            randomDaily2.setHours(11);
            randomDaily2.setMinutes(0);
            savePrefs.save(randomDaily2);
            return;
        }
        if (randomDaily.getHours() == 11) {
            Database.init(getApplicationContext());
            Database.deleteAll();
            SavePrefs savePrefs2 = new SavePrefs((Activity) this, (Class<?>) RandomDaily.class);
            RandomDaily randomDaily3 = new RandomDaily();
            randomDaily3.setHours(12);
            randomDaily3.setMinutes(0);
            savePrefs2.save(randomDaily3);
            RingtoneManager ringtoneManager2 = new RingtoneManager((Activity) this);
            ringtoneManager2.setType(4);
            Cursor cursor2 = ringtoneManager2.getCursor();
            String[] strArr3 = new String[cursor2.getCount() + 1];
            strArr3[0] = getString(R.string.silent);
            String[] strArr4 = new String[cursor2.getCount() + 1];
            strArr4[0] = "";
            if (cursor2.moveToFirst()) {
                do {
                    strArr3[cursor2.getPosition() + 1] = ringtoneManager2.getRingtone(cursor2.getPosition()).getTitle(this);
                    strArr4[cursor2.getPosition() + 1] = ringtoneManager2.getRingtoneUri(cursor2.getPosition()).toString();
                } while (cursor2.moveToNext());
            }
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTimeInMillis(System.currentTimeMillis());
            calendar2.set(11, 11);
            calendar2.set(12, 0);
            Alarm alarm2 = new Alarm();
            alarm2.setAlarmActive(true);
            alarm2.setAlarmName(getString(R.string.s_daily_msg));
            alarm2.setAlarmTime(calendar2);
            alarm2.setAlarmTonePath(strArr4[0]);
            alarm2.setVibrate(true);
            alarm2.setDays(new Alarm.Day[]{Alarm.Day.MONDAY, Alarm.Day.TUESDAY, Alarm.Day.WEDNESDAY, Alarm.Day.THURSDAY, Alarm.Day.FRIDAY, Alarm.Day.SATURDAY, Alarm.Day.SUNDAY});
            Database.init(getApplicationContext());
            Database.create(alarm2);
            callMathAlarmScheduleService();
        }
    }

    protected void callMathAlarmScheduleService() {
        sendBroadcast(new Intent(this, (Class<?>) AlarmServiceBroadcastReciever.class), null);
    }

    public String getmonthArabic(int i) {
        if (i == 1) {
            return getString(R.string.m_1);
        }
        if (i == 2) {
            return getString(R.string.m_2);
        }
        if (i == 3) {
            return getString(R.string.m_3);
        }
        if (i == 4) {
            return getString(R.string.m_4);
        }
        if (i == 5) {
            return getString(R.string.m_5);
        }
        if (i == 6) {
            return getString(R.string.m_6);
        }
        if (i == 7) {
            return getString(R.string.m_7);
        }
        if (i == 8) {
            return getString(R.string.m_8);
        }
        if (i == 9) {
            return getString(R.string.m_9);
        }
        if (i == 10) {
            return getString(R.string.m_10);
        }
        if (i == 11) {
            return getString(R.string.m_11);
        }
        return i == 12 ? getString(R.string.m_12) : "";
    }

    public String getmonthCoptic(int i) {
        if (i == 1) {
            return getString(R.string.c_1);
        }
        if (i == 2) {
            return getString(R.string.c_2);
        }
        if (i == 3) {
            return getString(R.string.c_3);
        }
        if (i == 4) {
            return getString(R.string.c_4);
        }
        if (i == 5) {
            return getString(R.string.c_5);
        }
        if (i == 6) {
            return getString(R.string.c_6);
        }
        if (i == 7) {
            return getString(R.string.c_7);
        }
        if (i == 8) {
            return getString(R.string.c_8);
        }
        if (i == 9) {
            return getString(R.string.c_9);
        }
        if (i == 10) {
            return getString(R.string.c_10);
        }
        if (i == 11) {
            return getString(R.string.c_11);
        }
        if (i == 12) {
            return getString(R.string.c_12);
        }
        return i == 13 ? getString(R.string.c_13) : "";
    }

    private String dayOfWeekArabic(String str) {
        if (str.equals("Sun")) {
            return getString(R.string.sun);
        }
        if (str.equals("Mon")) {
            return getString(R.string.mon);
        }
        if (str.equals("Tue")) {
            return getString(R.string.tues);
        }
        if (str.equals("Wed")) {
            return getString(R.string.wed);
        }
        if (str.equals("Thu")) {
            return getString(R.string.thurs);
        }
        if (str.equals("Fri")) {
            return getString(R.string.fri);
        }
        return getString(R.string.satur);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.mHomeFloatingActionMenu.isOpened()) {
            this.mHomeFloatingActionMenu.toggle(true);
        } else {
            closeAlert();
        }
    }

    public void closeAlert() {
        new AlertDialog.Builder(this).setIcon(R.drawable.logo).setTitle(getString(R.string.leave_app)).setMessage(getString(R.string.leave_app_question)).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.app.piapostolos.MainActivity.11
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.finish();
            }
        }).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void manageDailyMessage() {
        SavePrefs<DailyMsg> savePrefs = new SavePrefs<>((Activity) this, (Class<?>) DailyMsg.class);
        DailyMsg dailyMsgLoad = savePrefs.load();
        if (dailyMsgLoad == null) {
            String excelFileAndReturnRandom = readExcelFileAndReturnRandom();
            this.msg = excelFileAndReturnRandom;
            setLastMsg(excelFileAndReturnRandom, savePrefs);
        } else {
            Calendar calendar = Calendar.getInstance();
            new Date();
            try {
                Date date = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa").parse(dailyMsgLoad.getDateStr());
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(date);
                boolean z = true;
                if (calendar.get(6) != calendar2.get(6) || calendar.get(1) != calendar2.get(1)) {
                    z = false;
                }
                if (z) {
                    this.msg = dailyMsgLoad.getMessage();
                } else {
                    String excelFileAndReturnRandom2 = readExcelFileAndReturnRandom();
                    this.msg = excelFileAndReturnRandom2;
                    setLastMsg(excelFileAndReturnRandom2, savePrefs);
                }
            } catch (ParseException e) {
                String excelFileAndReturnRandom3 = readExcelFileAndReturnRandom();
                this.msg = excelFileAndReturnRandom3;
                setLastMsg(excelFileAndReturnRandom3, savePrefs);
                e.printStackTrace();
            }
        }
        if (this.msg.equals("")) {
            String excelFileAndReturnRandom4 = readExcelFileAndReturnRandom();
            this.msg = excelFileAndReturnRandom4;
            setLastMsg(excelFileAndReturnRandom4, savePrefs);
        }
        new AlertDialog.Builder(this).setIcon(R.drawable.logo).setTitle(getString(R.string.s_daily_msg)).setMessage(this.msg).setPositiveButton(getString(R.string.fb_share), new DialogInterface.OnClickListener() { // from class: com.app.piapostolos.MainActivity.13
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.TEXT", MainActivity.this.msg + "\n\n" + MainActivity.this.getString(R.string.message_from_piapostolos) + "\nhttps://www.orsozoxi.net/");
                intent.setType(HTTP.PLAIN_TEXT_TYPE);
                MainActivity mainActivity = MainActivity.this;
                mainActivity.startActivity(Intent.createChooser(intent, mainActivity.getString(R.string.app_name)).setFlags(268468224));
            }
        }).setNegativeButton(getString(R.string.done), new DialogInterface.OnClickListener() { // from class: com.app.piapostolos.MainActivity.12
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }

    private void setLastMsg(String str, SavePrefs<DailyMsg> savePrefs) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
        DailyMsg dailyMsg = new DailyMsg();
        dailyMsg.setDateStr(simpleDateFormat.format(calendar.getTime()));
        dailyMsg.setMessage(str);
        savePrefs.save(dailyMsg);
    }

    public static boolean isExternalStorageReadOnly() {
        return "mounted_ro".equals(Environment.getExternalStorageState());
    }

    public static boolean isExternalStorageAvailable() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    private String readExcelFileAndReturnRandom() {
        if (isExternalStorageAvailable() && !isExternalStorageReadOnly()) {
            ArrayList arrayList = new ArrayList();
            try {
                int i = 0;
                Iterator<Row> itRowIterator = new HSSFWorkbook(new POIFSFileSystem(getAssets().open("random.xls"))).getSheetAt(0).rowIterator();
                new FileExcel();
                ArrayList<String> arrayList2 = new ArrayList<>();
                while (itRowIterator.hasNext()) {
                    FileExcel fileExcel = new FileExcel();
                    fileExcel.setRow(i);
                    ArrayList<Integer> arrayList3 = new ArrayList<>();
                    Iterator<Cell> itCellIterator = ((HSSFRow) itRowIterator.next()).cellIterator();
                    while (itCellIterator.hasNext()) {
                        HSSFCell hSSFCell = (HSSFCell) itCellIterator.next();
                        arrayList2.add(hSSFCell.toString());
                        arrayList3.add(Integer.valueOf(hSSFCell.getColumnIndex()));
                    }
                    fileExcel.setCellPos(arrayList3);
                    fileExcel.setCell(arrayList2);
                    arrayList.add(fileExcel);
                    i++;
                }
                return arrayList2.get(new Random().nextInt(arrayList2.size()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
