package com.app.orsozoxi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import com.app.orsozoxi.Adapters.DBAdapter_highlightAgpya;
import com.app.orsozoxi.Adapters.ExpandableListAdapter;
import com.app.orsozoxi.Adapters.SanaksarAdapter;
import com.app.orsozoxi.Beans.Bible;
import com.app.orsozoxi.Beans.WhiteBackgroundActivition;
import com.app.orsozoxi.Others.CommonUtilities;
import com.app.orsozoxi.Others.ConnectionDetector;
import com.app.orsozoxi.Others.SavePrefs;
import com.app.orsozoxi.Others.Util;
import com.app.orsozoxi.Others.checksim;
import com.app.orsozoxi.Views.MenuDateView;
import com.app.orsozoxi.Views.NonScrollListView;
import com.facebook.share.internal.ShareConstants;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hieupt.android.standalonescrollbar.StandaloneScrollBar;
import com.hieupt.android.standalonescrollbar.ViewExtensionsKt;
import com.hieupt.android.standalonescrollbar.view.NestedScrollView2;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.navdrawer.SimpleSideDrawer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.apache.http.protocol.HTTP;
import org.slf4j.Marker;

/* loaded from: classes.dex */
public class AgpyaActivity extends BaseActivity implements View.OnClickListener {
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapterglav;
    SanaksarAdapter agpyaAdapter;
    String[] agynames;
    private ConnectionDetector cd;
    int chaptersnumbers;
    private View content;
    int counterr;
    private Context ctx;
    Dictionary<Integer, String> d;
    File fileWithinMyDirHTML;
    private GestureDetector gestureDetector;
    private GestureDetector gestureDetector2;
    AdapterView.AdapterContextMenuInfo info;
    List<Bible> legendList;
    ArrayList<String> levels;
    HashMap<String, ArrayList<String>> levelsMap;
    private NonScrollListView listViewFootballLegend;
    private FloatingActionMenu mHomeFloatingActionMenu;
    private ProgressDialog mProgressDialog;
    private MenuDateView menuDateView;
    private MediaPlayer mp;
    private Button playmusic;
    String signFilePath;
    SimpleSideDrawer slide_me;
    ArrayList<String> sp2;
    Spinner spinner;
    Spinner spinner2;
    TextView t;
    int def = 0;
    int pos = 0;

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_agpya);
        initFloating();
        this.playmusic = (Button) findViewById(R.id.btn_play_music);
        this.mp = MediaPlayer.create(getApplicationContext(), R.raw.music);
        this.playmusic.setBackgroundResource(R.drawable.btn_play);
        this.playmusic.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.AgpyaActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) throws IllegalStateException {
                if (AgpyaActivity.this.mp.isPlaying()) {
                    AgpyaActivity.this.playmusic.setBackgroundResource(R.drawable.btn_play);
                    AgpyaActivity.this.mp.pause();
                } else {
                    AgpyaActivity.this.playSong();
                    AgpyaActivity.this.playmusic.setBackgroundResource(R.drawable.btn_pause);
                }
            }
        });
        NestedScrollView2 nestedScrollView2 = (NestedScrollView2) findViewById(R.id.v_nested);
        ViewExtensionsKt.attachTo((StandaloneScrollBar) findViewById(R.id.scrollbar), nestedScrollView2);
        WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs((Activity) this, (Class<?>) WhiteBackgroundActivition.class).load();
        if (whiteBackgroundActivition == null || !whiteBackgroundActivition.isWhiteBackground()) {
            nestedScrollView2.setBackgroundResource(R.drawable.background);
        }
        Button button = (Button) findViewById(R.id.btn_slide);
        SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        this.slide_me = simpleSideDrawer;
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.AgpyaActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AgpyaActivity.this.slide_me.toggleLeftDrawer();
            }
        });
        findViewById(R.id.btn_sign_language).setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.AgpyaActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AgpyaActivity.this.signLanguageReader();
            }
        });
        new Handler().postDelayed(new Runnable() { // from class: com.app.orsozoxi.AgpyaActivity.4
            @Override // java.lang.Runnable
            public void run() {
                String str;
                int intExtra;
                String str2;
                String str3;
                int i;
                AgpyaActivity agpyaActivity = AgpyaActivity.this;
                agpyaActivity.ctx = agpyaActivity;
                AgpyaActivity.this.gestureDetector2 = new GestureDetector(new SwipeDetector());
                AgpyaActivity.this.counterr = 0;
                CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) AgpyaActivity.this.findViewById(R.id.toolbar_layout);
                Intent intent = AgpyaActivity.this.getIntent();
                AgpyaActivity agpyaActivity2 = AgpyaActivity.this;
                agpyaActivity2.spinner = (Spinner) agpyaActivity2.findViewById(R.id.spinner11);
                AgpyaActivity agpyaActivity3 = AgpyaActivity.this;
                agpyaActivity3.spinner2 = (Spinner) agpyaActivity3.findViewById(R.id.spinner12);
                AgpyaActivity.this.spinner.setPrompt(AgpyaActivity.this.getString(R.string.app_name));
                int i2 = 4;
                if (intent.getStringExtra(ShareConstants.MEDIA_TYPE).equals("agypa")) {
                    collapsingToolbarLayout.setTitle(AgpyaActivity.this.getString(R.string.title_activity_agbya));
                    i2 = 11;
                    AgpyaActivity.this.agynames = new String[11];
                    AgpyaActivity.this.spinner.setPrompt(AgpyaActivity.this.getString(R.string.agpya_prays));
                    intExtra = intent.getIntExtra(FirebaseAnalytics.Param.INDEX, -1);
                    str = "agypa.orso";
                } else {
                    if (intent.getStringExtra(ShareConstants.MEDIA_TYPE).equals("sagda")) {
                        collapsingToolbarLayout.setTitle(AgpyaActivity.this.getString(R.string.sagda_prays));
                        AgpyaActivity.this.agynames = new String[4];
                        AgpyaActivity.this.spinner.setPrompt(AgpyaActivity.this.getString(R.string.sagda_prays));
                        AgpyaActivity.this.spinner2.setVisibility(4);
                        str2 = "sagda.orso";
                    } else if (intent.getStringExtra(ShareConstants.MEDIA_TYPE).equals("lakan")) {
                        collapsingToolbarLayout.setTitle(AgpyaActivity.this.getString(R.string.lakan_prays));
                        AgpyaActivity.this.agynames = new String[4];
                        AgpyaActivity.this.spinner.setPrompt(AgpyaActivity.this.getString(R.string.lakan_prays));
                        str2 = "lakan.orso";
                    } else if (intent.getStringExtra(ShareConstants.MEDIA_TYPE).equals("kandil")) {
                        collapsingToolbarLayout.setTitle(AgpyaActivity.this.getString(R.string.kandil_prays));
                        AgpyaActivity.this.agynames = new String[7];
                        AgpyaActivity.this.spinner2.setVisibility(4);
                        AgpyaActivity.this.spinner.setPrompt(AgpyaActivity.this.getString(R.string.kandil_prays));
                        str = "kandil.orso";
                        intExtra = -1;
                        i2 = 7;
                    } else if (intent.getStringExtra(ShareConstants.MEDIA_TYPE).equals("kism")) {
                        collapsingToolbarLayout.setTitle(AgpyaActivity.this.getString(R.string.kism_prays));
                        AgpyaActivity.this.agynames = new String[39];
                        AgpyaActivity.this.spinner.setPrompt(AgpyaActivity.this.getString(R.string.kism_prays));
                        AgpyaActivity.this.spinner2.setVisibility(4);
                        str = "kism.orso";
                        intExtra = -1;
                        i2 = 39;
                    } else if (intent.getStringExtra(ShareConstants.MEDIA_TYPE).equals("taks")) {
                        collapsingToolbarLayout.setTitle(AgpyaActivity.this.getString(R.string.taks_reference));
                        AgpyaActivity.this.agynames = new String[18];
                        AgpyaActivity.this.spinner.setPrompt(AgpyaActivity.this.getString(R.string.taks_church));
                        AgpyaActivity.this.spinner2.setVisibility(4);
                        str = "taks.orso";
                        intExtra = -1;
                        i2 = 18;
                    } else {
                        str = "";
                        intExtra = -1;
                        i2 = 0;
                    }
                    str = str2;
                    intExtra = -1;
                }
                AgpyaActivity.this.getWindow().addFlags(128);
                new checksim();
                AgpyaActivity.this.legendList = new ArrayList();
                AgpyaActivity.this.d = new Hashtable();
                AgpyaActivity.this.levelsMap = new HashMap<>();
                AgpyaActivity.this.levels = new ArrayList<>();
                AgpyaActivity.this.sp2 = new ArrayList<>();
                AgpyaActivity agpyaActivity4 = AgpyaActivity.this;
                agpyaActivity4.t = (TextView) agpyaActivity4.findViewById(R.id.gcm_msg);
                System.out.println("beshooooooooooooooooooooooooo222222");
                try {
                    str3 = new String(app.decodeFile2(AgpyaActivity.this, "orsozoxi", str));
                } catch (Exception e) {
                    e.printStackTrace();
                    str3 = null;
                }
                String[] strArrSplit = str3.split("\n");
                String str4 = "";
                String str5 = str4;
                int i3 = 0;
                int i4 = 0;
                int i5 = 0;
                int i6 = 0;
                while (i3 < strArrSplit.length) {
                    if (i3 < i2) {
                        System.out.println("agypa_names" + strArrSplit[i3]);
                        AgpyaActivity.this.d.put(Integer.valueOf(i3), strArrSplit[i3]);
                        AgpyaActivity.this.agynames[i3] = strArrSplit[i3];
                    } else {
                        if (!strArrSplit[i3].equals("")) {
                            if (strArrSplit[i3].equals("++++++++++") || strArrSplit[i3].contains("++++++++++")) {
                                i = i2;
                                if (i5 == 0) {
                                    i5++;
                                } else {
                                    AgpyaActivity.this.sp2.add(str4);
                                    i4++;
                                    str4 = "";
                                    i6 = 0;
                                }
                            } else {
                                if (strArrSplit[i3].equals("+++")) {
                                    i = i2;
                                } else {
                                    i = i2;
                                    if (!strArrSplit[i3].equals("+++++") && !strArrSplit[i3].equals("+++ ") && (!strArrSplit[i3].contains("+++") || strArrSplit[i3].equals("++++++++++"))) {
                                        if (strArrSplit[i3].equals(Marker.ANY_NON_NULL_MARKER)) {
                                            System.out.println("mzmor:" + str5);
                                            AgpyaActivity.this.levels.add(str5);
                                            str5 = "";
                                        } else if (str5.equals("")) {
                                            str4 = str4 + strArrSplit[i3] + "@";
                                            str5 = str5 + strArrSplit[i3] + "@";
                                            System.out.println("title:" + str4);
                                            System.out.println("ayah:" + str5);
                                        } else {
                                            str5 = str5 + strArrSplit[i3] + "\n";
                                        }
                                    }
                                }
                                AgpyaActivity.this.levelsMap.put(i4 + Marker.ANY_NON_NULL_MARKER + i6, new ArrayList<>(AgpyaActivity.this.levels));
                                i6++;
                                AgpyaActivity.this.levels.clear();
                            }
                        }
                        i3++;
                        i2 = i;
                    }
                    i = i2;
                    i3++;
                    i2 = i;
                }
                AgpyaActivity agpyaActivity5 = AgpyaActivity.this;
                AgpyaActivity agpyaActivity6 = AgpyaActivity.this;
                agpyaActivity5.adapter = new ArrayAdapter<>(agpyaActivity6, R.layout.spinner_item_white, agpyaActivity6.agynames);
                AgpyaActivity.this.adapter.setDropDownViewResource(R.layout.spinner_item_black);
                AgpyaActivity.this.spinner.setAdapter((SpinnerAdapter) AgpyaActivity.this.adapter);
                if (intExtra == -1) {
                    AgpyaActivity.this.spinner.performClick();
                } else {
                    AgpyaActivity.this.spinner.setSelection(intExtra);
                }
                AgpyaActivity.this.chaptersnumbers = 0;
                AgpyaActivity.this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.orsozoxi.AgpyaActivity.4.1
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i7, long j) {
                        int selectedItemPosition = AgpyaActivity.this.spinner.getSelectedItemPosition();
                        AgpyaActivity.this.setBanner(selectedItemPosition);
                        Iterator<String> it2 = AgpyaActivity.this.levelsMap.keySet().iterator();
                        while (it2.hasNext()) {
                            if (selectedItemPosition == Integer.parseInt(it2.next().split("\\+")[0])) {
                                AgpyaActivity.this.chaptersnumbers++;
                            }
                        }
                        String[] strArr = new String[AgpyaActivity.this.chaptersnumbers];
                        AgpyaActivity.this.spinner.getSelectedItemPosition();
                        for (int i8 = 0; i8 < AgpyaActivity.this.sp2.size(); i8++) {
                            if (selectedItemPosition == i8) {
                                String[] strArrSplit2 = AgpyaActivity.this.sp2.get(i8).split("@");
                                for (int i9 = 0; i9 < strArrSplit2.length; i9++) {
                                    System.out.println("chaptersnumbers:" + strArrSplit2[i9]);
                                    strArr[i9] = strArrSplit2[i9];
                                }
                            }
                        }
                        AgpyaActivity.this.setspinn(strArr);
                        AgpyaActivity.this.chaptersnumbers = 0;
                    }
                });
                AgpyaActivity agpyaActivity7 = AgpyaActivity.this;
                agpyaActivity7.listViewFootballLegend = (NonScrollListView) agpyaActivity7.findViewById(R.id.liss1);
                AgpyaActivity.this.listViewFootballLegend.setBackgroundColor(0);
                AgpyaActivity.this.listViewFootballLegend.setCacheColorHint(0);
                AgpyaActivity.this.listViewFootballLegend.setDivider(null);
                AgpyaActivity.this.listViewFootballLegend.setClickable(false);
                AgpyaActivity.this.spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.orsozoxi.AgpyaActivity.4.2
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i7, long j) {
                        AgpyaActivity.this.updatelist(AgpyaActivity.this.spinner.getSelectedItemPosition(), AgpyaActivity.this.spinner2.getSelectedItemPosition());
                    }
                });
                AgpyaActivity.this.getResources();
                AgpyaActivity.this.agpyaAdapter = new SanaksarAdapter(AgpyaActivity.this.ctx, R.layout.row_agpya_item, AgpyaActivity.this.legendList);
                AgpyaActivity.this.listViewFootballLegend.setAdapter((ListAdapter) AgpyaActivity.this.agpyaAdapter);
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void signLanguageReader() {
        this.signFilePath = CommonUtilities.getInternalPath(this) + "agbeya.pdf";
        if (new File(this.signFilePath).exists()) {
            Intent intent = new Intent(this, (Class<?>) PdfViewerActivity.class);
            intent.putExtra("filepath", this.signFilePath);
            startActivity(intent);
            return;
        }
        manageData();
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 30) {
            manageData();
            return true;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                Log.v(Util.TAG, "Permission is granted");
                manageData();
                return true;
            }
            Log.v(Util.TAG, "Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 1);
            return false;
        }
        Log.v(Util.TAG, "Permission is granted");
        manageData();
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (iArr[0] == 0 && iArr[1] == 0) {
            Log.v(Util.TAG, "Permission: " + strArr[0] + "was " + iArr[0]);
            manageData();
        }
    }

    public boolean checkinternet() {
        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
        this.cd = connectionDetector;
        return connectionDetector.isConnectingToInternet();
    }

    private void manageData() {
        Log.d(RemoteMessageConst.Notification.TAG, "id=");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.agpya_prays));
        builder.setMessage(getString(R.string.download_sign_question));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() { // from class: com.app.orsozoxi.AgpyaActivity.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                if (AgpyaActivity.this.checkinternet()) {
                    AgpyaActivity.this.mProgressDialog = new ProgressDialog(AgpyaActivity.this);
                    AgpyaActivity.this.mProgressDialog.setMessage(AgpyaActivity.this.getString(R.string.download_tafser));
                    AgpyaActivity.this.mProgressDialog.setCancelable(false);
                    AgpyaActivity.this.mProgressDialog.show();
                    AgpyaActivity.this.downloadHTMLFileAsync();
                    return;
                }
                Toast.makeText(AgpyaActivity.this.getApplicationContext(), R.string.no_internet, 1).show();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: com.app.orsozoxi.AgpyaActivity.6
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void downloadHTMLFileAsync() {
        Observable.fromCallable(new Callable() { // from class: com.app.orsozoxi.-$$Lambda$AgpyaActivity$pwhGwS3ueX3kuLviH5KKgU9HB18
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$downloadHTMLFileAsync$0$AgpyaActivity();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.orsozoxi.-$$Lambda$AgpyaActivity$DpQjS3DWQwjCKVBvuZuHvWVU7hw
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$downloadHTMLFileAsync$1$AgpyaActivity((Boolean) obj);
            }
        });
    }

    public /* synthetic */ Boolean lambda$downloadHTMLFileAsync$0$AgpyaActivity() throws Exception {
        try {
            Log.d("test", "download");
            URLConnection uRLConnectionOpenConnection = new URL(CommonUtilities.SERVER_URL_AGPYA_SIGN).openConnection();
            uRLConnectionOpenConnection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            uRLConnectionOpenConnection.setRequestProperty("Expect", HTTP.EXPECT_CONTINUE);
            uRLConnectionOpenConnection.setDoOutput(true);
            uRLConnectionOpenConnection.connect();
            File file = new File(this.signFilePath);
            this.fileWithinMyDirHTML = file;
            if (file.exists()) {
                this.fileWithinMyDirHTML.delete();
                this.fileWithinMyDirHTML = new File(this.signFilePath);
            }
            FileOutputStream fileOutputStream = new FileOutputStream(this.fileWithinMyDirHTML);
            InputStream inputStream = uRLConnectionOpenConnection.getInputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int i = inputStream.read(bArr);
                if (i <= 0) {
                    break;
                }
                fileOutputStream.write(bArr, 0, i);
            }
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public /* synthetic */ void lambda$downloadHTMLFileAsync$1$AgpyaActivity(Boolean bool) throws Exception {
        this.mProgressDialog.dismiss();
        Intent intent = new Intent(this, (Class<?>) PdfViewerActivity.class);
        intent.putExtra("filepath", this.signFilePath);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBanner(int i) {
        ImageView imageView = (ImageView) findViewById(R.id.iv_banner);
        if (getIntent().getStringExtra(ShareConstants.MEDIA_TYPE).equals("agypa")) {
            switch (i) {
                case 0:
                    imageView.setBackgroundResource(R.drawable.agy_banner_baker);
                    break;
                case 1:
                    imageView.setBackgroundResource(R.drawable.agy_banner_3);
                    break;
                case 2:
                    imageView.setBackgroundResource(R.drawable.agy_banner_6);
                    break;
                case 3:
                    imageView.setBackgroundResource(R.drawable.agy_banner_9);
                    break;
                case 4:
                    imageView.setBackgroundResource(R.drawable.agy_banner_grob);
                    break;
                case 5:
                    imageView.setBackgroundResource(R.drawable.agy_banner_nom);
                    break;
                case 6:
                    imageView.setBackgroundResource(R.drawable.agy_banner_setar);
                    break;
                default:
                    imageView.setBackgroundResource(R.drawable.thumb);
                    break;
            }
        }
    }

    private void initFloating() {
        FloatingActionMenu floatingActionMenu = (FloatingActionMenu) findViewById(R.id.menu_options);
        this.mHomeFloatingActionMenu = floatingActionMenu;
        floatingActionMenu.setIconAnimated(false);
        this.mHomeFloatingActionMenu.setClosedOnTouchOutside(true);
        this.mHomeFloatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.AgpyaActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AgpyaActivity.this.mHomeFloatingActionMenu.toggle(true);
            }
        });
        findViewById(R.id.fb_copy).setOnClickListener(this);
        findViewById(R.id.fb_highlight).setOnClickListener(this);
        findViewById(R.id.fb_share).setOnClickListener(this);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.mHomeFloatingActionMenu.isOpened()) {
            this.mHomeFloatingActionMenu.toggle(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) throws SQLException {
        String str;
        String str2;
        int id = view.getId();
        if (id == R.id.fb_copy) {
            this.mHomeFloatingActionMenu.toggle(false);
            if (getIntent().getStringExtra(ShareConstants.MEDIA_TYPE).equals("agypa") || getIntent().getStringExtra(ShareConstants.MEDIA_TYPE).equals("lakan")) {
                str = this.spinner.getSelectedItem() + "\n" + this.spinner2.getSelectedItem() + "\n" + this.legendList.get(0).getNick();
            } else {
                str = this.spinner.getSelectedItem() + "\n" + this.legendList.get(0).getNick();
            }
            app.copyText(this, str);
            return;
        }
        if (id == R.id.fb_highlight) {
            this.mHomeFloatingActionMenu.toggle(false);
            highlight(0);
            return;
        }
        if (id != R.id.fb_share) {
            return;
        }
        this.mHomeFloatingActionMenu.toggle(false);
        if (getIntent().getStringExtra(ShareConstants.MEDIA_TYPE).equals("agypa") || getIntent().getStringExtra(ShareConstants.MEDIA_TYPE).equals("lakan")) {
            str2 = this.spinner.getSelectedItem() + "\n" + this.spinner2.getSelectedItem() + "\n" + this.legendList.get(0).getNick();
        } else {
            str2 = this.spinner.getSelectedItem() + "\n" + this.legendList.get(0).getNick();
        }
        app.shareText(this, str2);
    }

    private void highlight(int i) throws SQLException {
        String nick = this.legendList.get(i).getNick();
        DBAdapter_highlightAgpya dBAdapter_highlightAgpya = new DBAdapter_highlightAgpya(this.ctx);
        dBAdapter_highlightAgpya.open();
        if (!dBAdapter_highlightAgpya.fetchOne(nick).equals("")) {
            dBAdapter_highlightAgpya.deleteone(dBAdapter_highlightAgpya.fetchOne(nick));
        } else {
            dBAdapter_highlightAgpya.insert(nick);
        }
        dBAdapter_highlightAgpya.close();
        this.agpyaAdapter.notifyDataSetChanged();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        AppLeftSlider.setCurrentActivity(this);
        super.onResume();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        GestureDetector gestureDetector = this.gestureDetector2;
        if (gestureDetector == null || !gestureDetector.onTouchEvent(motionEvent)) {
            return super.dispatchTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.app.Activity
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.gestureDetector2.onTouchEvent(motionEvent);
    }

    private class SwipeDetector extends GestureDetector.SimpleOnGestureListener {
        private SwipeDetector() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (motionEvent != null && motionEvent2 != null && Math.abs(motionEvent.getY() - motionEvent2.getY()) <= 250.0f) {
                if (motionEvent.getX() - motionEvent2.getX() > 120.0f && Math.abs(f) > 200.0f) {
                    Log.d("log", "left");
                    if (AgpyaActivity.this.spinner2.getSelectedItemPosition() > 0 && AgpyaActivity.this.spinner2.getCount() >= 1) {
                        int selectedItemPosition = AgpyaActivity.this.spinner.getSelectedItemPosition();
                        int selectedItemPosition2 = AgpyaActivity.this.spinner2.getSelectedItemPosition() - 1;
                        AgpyaActivity.this.updatelist(selectedItemPosition, selectedItemPosition2);
                        AgpyaActivity.this.spinner2.setSelection(selectedItemPosition2);
                    }
                    return true;
                }
                if (motionEvent2.getX() - motionEvent.getX() > 120.0f && Math.abs(f) > 200.0f) {
                    Log.d("log", "right");
                    if (AgpyaActivity.this.spinner2.getSelectedItemPosition() < AgpyaActivity.this.spinner2.getCount() - 1 && AgpyaActivity.this.spinner2.getCount() > 1) {
                        int selectedItemPosition3 = AgpyaActivity.this.spinner.getSelectedItemPosition();
                        int selectedItemPosition4 = AgpyaActivity.this.spinner2.getSelectedItemPosition() + 1;
                        AgpyaActivity.this.updatelist(selectedItemPosition3, selectedItemPosition4);
                        AgpyaActivity.this.spinner2.setSelection(selectedItemPosition4);
                    }
                    return true;
                }
            }
            return false;
        }
    }

    public void updatelist(int i, int i2) {
        int i3 = this.counterr;
        if (i3 == 0) {
            this.counterr = i3 + 1;
        } else {
            this.legendList.clear();
        }
        String str = i + Marker.ANY_NON_NULL_MARKER + i2;
        System.out.println("yarabbb" + str);
        new ArrayList();
        String str2 = "";
        int i4 = 0;
        for (Map.Entry<String, ArrayList<String>> entry : this.levelsMap.entrySet()) {
            if (entry.getKey().equals(str)) {
                i4++;
                Iterator<String> it2 = entry.getValue().iterator();
                int i5 = 1;
                while (it2.hasNext()) {
                    String next = it2.next();
                    System.out.println("yarabbbbbb" + next);
                    if (next.contains("@")) {
                        str2 = str2 + next + "<br>";
                        this.legendList.add(new Bible(String.valueOf(i5), next.split("@")[1]));
                    } else {
                        str2 = str2 + next + "<br>";
                        this.legendList.add(new Bible(String.valueOf(i5), next));
                    }
                    i5++;
                }
            }
        }
        if (i4 == 0) {
            return;
        }
        this.agpyaAdapter.notifyDataSetChanged();
        NestedScrollView2 nestedScrollView2 = (NestedScrollView2) findViewById(R.id.v_nested);
        nestedScrollView2.scrollTo(0, 0);
        WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs((Activity) this, (Class<?>) WhiteBackgroundActivition.class).load();
        if (whiteBackgroundActivition != null) {
            if (!whiteBackgroundActivition.isWhiteBackground()) {
                nestedScrollView2.setBackgroundResource(R.drawable.background);
                return;
            } else {
                nestedScrollView2.setBackgroundColor(Color.parseColor("#ffffff"));
                return;
            }
        }
        nestedScrollView2.setBackgroundResource(R.drawable.background);
    }

    public void setspinn(String[] strArr) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_white, strArr);
        this.adapterglav = arrayAdapter;
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item_black);
        this.spinner2.setAdapter((SpinnerAdapter) this.adapterglav);
        this.spinner2.setPrompt(getString(R.string.hello_world));
    }

    public void playSong() throws IllegalStateException {
        try {
            this.mp.setLooping(true);
            this.mp.start();
            this.playmusic.setBackgroundResource(R.drawable.btn_play);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() throws IllegalStateException {
        MediaPlayer mediaPlayer = this.mp;
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            this.mp.stop();
        }
        super.onDestroy();
    }
}
