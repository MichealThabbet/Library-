package com.app.piapostolos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.app.piapostolos.Adapters.DBAdapter_highlightAgpya;
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import com.app.piapostolos.Adapters.SanaksarAdapter;
import com.app.piapostolos.Beans.Bible;
import com.app.piapostolos.Beans.WhiteBackgroundActivition;
import com.app.piapostolos.Others.SavePrefs;
import com.app.piapostolos.Others.checksim;
import com.app.piapostolos.Views.NonScrollListView;
import com.github.clans.fab.FloatingActionMenu;
import com.hieupt.android.standalonescrollbar.StandaloneScrollBar;
import com.hieupt.android.standalonescrollbar.ViewExtensionsKt;
import com.hieupt.android.standalonescrollbar.view.NestedScrollView2;
import com.ibm.icu.util.CopticCalendar;
import com.navdrawer.SimpleSideDrawer;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class MainActivity_sanksar extends BaseActivity implements View.OnClickListener {
    static final int DATE_DIALOG_ID = 999;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    ArrayAdapter<String> adapterglav;
    SanaksarAdapter agpyaAdapter;
    int chaptersnumbers;
    CopticCalendar copticCalendar;
    int counterr;
    private Context ctx;
    int dayy;
    private GestureDetector gestureDetector2;
    List<Bible> legendList;
    private NonScrollListView listViewFootballLegend;
    private FloatingActionMenu mHomeFloatingActionMenu;
    int monthh;
    private MediaPlayer mp;
    private Button playmusic;
    ArrayList<String> sayer;
    ArrayList<String> sayerTitle;
    SimpleSideDrawer slide_me;
    Spinner spinner2;
    TextView t;
    int yearr;
    String sanksarDayTitle = "";
    int pos = 0;
    private boolean isEaster = false;
    private boolean isSood = false;
    private boolean isKamasen = false;
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() { // from class: com.app.piapostolos.MainActivity_sanksar.5
        @Override // android.app.DatePickerDialog.OnDateSetListener
        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
            MainActivity_sanksar.this.yearr = i;
            MainActivity_sanksar.this.monthh = i2;
            MainActivity_sanksar.this.dayy = i3;
            System.out.println(MainActivity_sanksar.this.dayy + " " + MainActivity_sanksar.this.monthh + " " + MainActivity_sanksar.this.yearr);
            Calendar calendar = Calendar.getInstance();
            calendar.set(11, calendar.get(11));
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(5, MainActivity_sanksar.this.dayy);
            calendar2.set(2, MainActivity_sanksar.this.monthh);
            calendar2.set(1, MainActivity_sanksar.this.yearr);
            calendar2.set(11, calendar.get(11));
            calendar2.set(12, calendar.get(12));
            calendar2.set(13, calendar.get(13));
            Log.d("orso", "thatday" + calendar2.getTime());
            Log.d("orso", "today" + calendar.getTime());
            Log.d("orso", "thatday" + calendar2.getTime().toGMTString());
            Log.d("orso", "today" + calendar.getTime().toGMTString());
            int iIntValue = Integer.valueOf(((calendar2.getTimeInMillis() - calendar.getTimeInMillis()) / 86400000) + "").intValue();
            CopticCalendar copticCalendar = new CopticCalendar();
            System.out.println("dayss" + iIntValue);
            copticCalendar.set(6, copticCalendar.get(6) + iIntValue);
            copticCalendar.set(11, calendar.get(11));
            copticCalendar.set(12, calendar.get(12));
            copticCalendar.set(13, calendar.get(13));
            int i4 = copticCalendar.get(2);
            Log.d("orso", "newCopticCalendar" + calendar.get(11));
            Log.d("orso", "newCopticCalendar" + copticCalendar.getTime());
            Log.d("orso", "newCopticCalendar" + copticCalendar.getTime().toGMTString());
            int i5 = i4 + 1;
            System.out.println(copticCalendar.get(5) + " " + i5 + " " + copticCalendar.get(1));
            String strValueOf = String.valueOf(copticCalendar.get(5));
            String strValueOf2 = String.valueOf(copticCalendar.get(1));
            int i6 = copticCalendar.get(2) + 1;
            MainActivity_sanksar.this.sanksarDayTitle = strValueOf2 + " " + MainActivity_sanksar.this.copticMonth(i6) + " " + strValueOf;
            MainActivity_sanksar mainActivity_sanksar = MainActivity_sanksar.this;
            Calendar orthodoxEaster = mainActivity_sanksar.getOrthodoxEaster(mainActivity_sanksar.yearr);
            System.out.println("orth=" + orthodoxEaster.getTime());
            System.out.println("nowcal=" + calendar2.getTime());
            if (orthodoxEaster.get(1) == calendar2.get(1) && orthodoxEaster.get(5) == calendar2.get(5) && orthodoxEaster.get(2) == calendar2.get(2)) {
                MainActivity_sanksar.this.isEaster = true;
                MainActivity_sanksar.this.ReadData(copticCalendar.get(5), i5, copticCalendar.get(1), 0);
            }
            orthodoxEaster.set(6, orthodoxEaster.get(6) + 39);
            if (orthodoxEaster.get(1) == calendar2.get(1) && orthodoxEaster.get(5) == calendar2.get(5) && orthodoxEaster.get(2) == calendar2.get(2)) {
                MainActivity_sanksar.this.isSood = true;
                MainActivity_sanksar.this.ReadData(copticCalendar.get(5), i5, copticCalendar.get(1), 1);
            }
            MainActivity_sanksar mainActivity_sanksar2 = MainActivity_sanksar.this;
            Calendar orthodoxEaster2 = mainActivity_sanksar2.getOrthodoxEaster(mainActivity_sanksar2.yearr);
            orthodoxEaster2.set(6, orthodoxEaster2.get(6) + 49);
            if (orthodoxEaster2.get(1) == calendar2.get(1) && orthodoxEaster2.get(5) == calendar2.get(5) && orthodoxEaster2.get(2) == calendar2.get(2)) {
                MainActivity_sanksar.this.isKamasen = true;
                MainActivity_sanksar.this.ReadData(copticCalendar.get(5), i5, copticCalendar.get(1), 2);
            }
            if (!MainActivity_sanksar.this.isEaster && !MainActivity_sanksar.this.isKamasen && !MainActivity_sanksar.this.isSood) {
                if (Integer.valueOf(strValueOf2.substring(2, 4)).intValue() % 4 == 0 && i5 == 4) {
                    if (copticCalendar.get(5) == 27) {
                        MainActivity_sanksar.this.ReadData(28, i5, copticCalendar.get(1), -1);
                    } else if (copticCalendar.get(5) == 29 || copticCalendar.get(5) == 28) {
                        MainActivity_sanksar.this.ReadData(29, i5, copticCalendar.get(1), -1);
                    } else {
                        MainActivity_sanksar.this.ReadData(copticCalendar.get(5), i5, copticCalendar.get(1), -1);
                    }
                } else {
                    MainActivity_sanksar.this.ReadData(copticCalendar.get(5), i5, copticCalendar.get(1), -1);
                }
            }
            MainActivity_sanksar.this.loadData();
        }
    };

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_sanksar__main);
        this.gestureDetector2 = new GestureDetector(new SwipeDetector());
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
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.MainActivity_sanksar.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainActivity_sanksar.this.slide_me.toggleLeftDrawer();
            }
        });
        new Handler().postDelayed(new Runnable() { // from class: com.app.piapostolos.MainActivity_sanksar.2
            @Override // java.lang.Runnable
            public void run() {
                MainActivity_sanksar mainActivity_sanksar = MainActivity_sanksar.this;
                mainActivity_sanksar.mp = MediaPlayer.create(mainActivity_sanksar.getApplicationContext(), R.raw.music);
                MainActivity_sanksar mainActivity_sanksar2 = MainActivity_sanksar.this;
                mainActivity_sanksar2.playmusic = (Button) mainActivity_sanksar2.findViewById(R.id.btn_play_music);
                MainActivity_sanksar.this.playmusic.setBackgroundResource(R.drawable.btn_play);
                MainActivity_sanksar.this.playmusic.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.MainActivity_sanksar.2.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) throws IllegalStateException {
                        if (MainActivity_sanksar.this.mp.isPlaying()) {
                            MainActivity_sanksar.this.playmusic.setBackgroundResource(R.drawable.btn_play);
                            MainActivity_sanksar.this.mp.pause();
                            return;
                        }
                        MainActivity_sanksar.this.mp = MainActivity_sanksar.this.mp = MediaPlayer.create(MainActivity_sanksar.this.getApplicationContext(), R.raw.music);
                        MainActivity_sanksar.this.playSong();
                        MainActivity_sanksar.this.playmusic.setBackgroundResource(R.drawable.btn_pause);
                    }
                });
                MainActivity_sanksar.this.initFloating();
                MainActivity_sanksar.this.sayer = new ArrayList<>();
                MainActivity_sanksar.this.sayerTitle = new ArrayList<>();
                MainActivity_sanksar mainActivity_sanksar3 = MainActivity_sanksar.this;
                mainActivity_sanksar3.spinner2 = (Spinner) mainActivity_sanksar3.findViewById(R.id.spinner12);
                MainActivity_sanksar mainActivity_sanksar4 = MainActivity_sanksar.this;
                mainActivity_sanksar4.setTitle(mainActivity_sanksar4.getString(R.string.t_sanksar));
                MainActivity_sanksar.this.copticCalendar = new CopticCalendar();
                MainActivity_sanksar mainActivity_sanksar5 = MainActivity_sanksar.this;
                mainActivity_sanksar5.ctx = mainActivity_sanksar5;
                MainActivity_sanksar.this.counterr = 0;
                MainActivity_sanksar.this.getWindow().addFlags(128);
                new checksim();
                MainActivity_sanksar.this.legendList = new ArrayList();
                MainActivity_sanksar mainActivity_sanksar6 = MainActivity_sanksar.this;
                mainActivity_sanksar6.t = (TextView) mainActivity_sanksar6.findViewById(R.id.gcm_msg);
                MainActivity_sanksar.this.chaptersnumbers = 0;
                MainActivity_sanksar mainActivity_sanksar7 = MainActivity_sanksar.this;
                mainActivity_sanksar7.listViewFootballLegend = (NonScrollListView) mainActivity_sanksar7.findViewById(R.id.liss1);
                MainActivity_sanksar.this.listViewFootballLegend.setBackgroundColor(0);
                MainActivity_sanksar.this.listViewFootballLegend.setDivider(null);
                MainActivity_sanksar.this.listViewFootballLegend.setClickable(false);
                MainActivity_sanksar.this.spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.piapostolos.MainActivity_sanksar.2.2
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                        MainActivity_sanksar.this.spinner2.getSelectedItemPosition();
                        MainActivity_sanksar.this.loadData();
                    }
                });
                ((Button) MainActivity_sanksar.this.findViewById(R.id.dailyyseaCoptic)).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.MainActivity_sanksar.2.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        MainActivity_sanksar.this.startActivity(new Intent(MainActivity_sanksar.this, (Class<?>) SanksarCopticDataPickerActivity.class));
                    }
                });
                ((Button) MainActivity_sanksar.this.findViewById(R.id.dailyysea)).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.MainActivity_sanksar.2.4
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        Calendar calendar = Calendar.getInstance();
                        String[] strArrSplit = new Date(System.currentTimeMillis()).toGMTString().split(" ");
                        calendar.get(5);
                        calendar.get(2);
                        String str = strArrSplit[1];
                        MainActivity_sanksar.this.yearr = calendar.get(1);
                        MainActivity_sanksar.this.monthh = calendar.get(2);
                        MainActivity_sanksar.this.dayy = calendar.get(5);
                        MainActivity_sanksar.this.showDialog(999);
                    }
                });
                MainActivity_sanksar.this.ShowAfter5Message();
                Calendar calendar = Calendar.getInstance();
                calendar.set(11, calendar.get(11));
                Log.d("orso", "now" + calendar.getTime());
                Calendar orthodoxEaster = MainActivity_sanksar.this.getOrthodoxEaster(calendar.get(1));
                if (calendar.get(11) >= 17) {
                    orthodoxEaster.set(6, orthodoxEaster.get(6) + 1);
                    MainActivity_sanksar.this.copticCalendar.set(6, MainActivity_sanksar.this.copticCalendar.get(6) + 1);
                }
                if (orthodoxEaster.get(1) == calendar.get(1) && orthodoxEaster.get(5) == calendar.get(5) && orthodoxEaster.get(2) == calendar.get(2)) {
                    MainActivity_sanksar.this.isEaster = true;
                    MainActivity_sanksar mainActivity_sanksar8 = MainActivity_sanksar.this;
                    mainActivity_sanksar8.sanksarDayTitle = mainActivity_sanksar8.copticToString(0);
                }
                orthodoxEaster.set(6, orthodoxEaster.get(6) + 39);
                if (orthodoxEaster.get(1) == calendar.get(1) && orthodoxEaster.get(5) == calendar.get(5) && orthodoxEaster.get(2) == calendar.get(2)) {
                    MainActivity_sanksar.this.isSood = true;
                    MainActivity_sanksar mainActivity_sanksar9 = MainActivity_sanksar.this;
                    mainActivity_sanksar9.sanksarDayTitle = mainActivity_sanksar9.copticToString(1);
                }
                Calendar orthodoxEaster2 = MainActivity_sanksar.this.getOrthodoxEaster(calendar.get(1));
                orthodoxEaster2.set(6, orthodoxEaster2.get(6) + 49);
                if (orthodoxEaster2.get(1) == calendar.get(1) && orthodoxEaster2.get(5) == calendar.get(5) && orthodoxEaster2.get(2) == calendar.get(2)) {
                    MainActivity_sanksar.this.isKamasen = true;
                    MainActivity_sanksar mainActivity_sanksar10 = MainActivity_sanksar.this;
                    mainActivity_sanksar10.sanksarDayTitle = mainActivity_sanksar10.copticToString(2);
                }
                if (!MainActivity_sanksar.this.isEaster && !MainActivity_sanksar.this.isKamasen && !MainActivity_sanksar.this.isSood) {
                    MainActivity_sanksar mainActivity_sanksar11 = MainActivity_sanksar.this;
                    mainActivity_sanksar11.sanksarDayTitle = mainActivity_sanksar11.copticToString(-1);
                }
                MainActivity_sanksar.this.spinner2.performClick();
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ShowAfter5Message() {
        katamarsReaders katamarsreaders = new katamarsReaders(this, false);
        if (Calendar.getInstance().get(11) < 17 || katamarsreaders.eidName.equals("kyama") || katamarsreaders.eidName.equals("gomaa_azema") || katamarsreaders.eidName.contains("sabt_farah") || katamarsreaders.eidName.equals("barmoon_gotas_lastday") || katamarsreaders.eidName.equals("barmoon_gotas_lastday_sun") || katamarsreaders.eidName.equals("eid_gotas") || katamarsreaders.eidName.equals("barmoon_melad_lastday") || katamarsreaders.eidName.equals("eid_melad")) {
            return;
        }
        new AlertDialog.Builder(this).setIcon(R.drawable.logo).setTitle(getString(R.string.alert)).setMessage(getString(R.string.alert_5_sanksar)).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.app.piapostolos.MainActivity_sanksar.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initFloating() {
        FloatingActionMenu floatingActionMenu = (FloatingActionMenu) findViewById(R.id.menu_options);
        this.mHomeFloatingActionMenu = floatingActionMenu;
        floatingActionMenu.setIconAnimated(false);
        this.mHomeFloatingActionMenu.setClosedOnTouchOutside(true);
        this.mHomeFloatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.MainActivity_sanksar.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainActivity_sanksar.this.mHomeFloatingActionMenu.toggle(true);
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
        String str = this.sanksarDayTitle;
        int id = view.getId();
        if (id == R.id.fb_copy) {
            this.mHomeFloatingActionMenu.toggle(false);
            app.copyText(this, getString(R.string.sanksar_day) + " " + str + "\n" + this.spinner2.getSelectedItem().toString() + "\n" + this.legendList.get(0).getNick());
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
        app.shareText(this, getString(R.string.sanksar_day) + " " + str + "\n" + this.spinner2.getSelectedItem().toString() + "\n" + this.legendList.get(0).getNick());
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

    public Calendar getOrthodoxEaster(int i) {
        Calendar calendar = Calendar.getInstance();
        int i2 = (((i % 19) * 19) + 15) % 30;
        int i3 = ((((((i % 4) * 2) + ((i % 7) * 4)) + (i2 * 6)) + 6) % 7) + i2 + 13;
        if (i3 > 39) {
            calendar.set(i, 4, i3 - 39);
        } else if (i3 > 9) {
            calendar.set(i, 3, i3 - 9);
        } else {
            calendar.set(i, 2, i3 + 22);
        }
        return calendar;
    }

    protected void loadData() {
        this.legendList.clear();
        this.isEaster = false;
        this.isKamasen = false;
        this.isSood = false;
        if (this.spinner2.getSelectedItemPosition() != -1 && this.spinner2.getSelectedItemPosition() < this.sayer.size()) {
            this.legendList.add(new Bible("", this.sayer.get(this.spinner2.getSelectedItemPosition())));
        }
        SanaksarAdapter sanaksarAdapter = new SanaksarAdapter(this.ctx, R.layout.row_agpya_item, this.legendList);
        this.agpyaAdapter = sanaksarAdapter;
        this.listViewFootballLegend.setAdapter((ListAdapter) sanaksarAdapter);
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

    public String copticToString(int i) {
        String strValueOf = String.valueOf(this.copticCalendar.get(5));
        String strValueOf2 = String.valueOf(this.copticCalendar.get(1));
        int i2 = this.copticCalendar.get(2) + 1;
        if (i == -1 && Integer.valueOf(strValueOf2.substring(2, 4)).intValue() % 4 == 0 && i2 == 4) {
            if (this.copticCalendar.get(5) == 27) {
                ReadData(28, i2, this.copticCalendar.get(1), i);
            } else if (this.copticCalendar.get(5) == 29 || this.copticCalendar.get(5) == 28) {
                ReadData(29, i2, this.copticCalendar.get(1), i);
            } else {
                ReadData(this.copticCalendar.get(5), i2, this.copticCalendar.get(1), i);
            }
        } else {
            ReadData(this.copticCalendar.get(5), i2, this.copticCalendar.get(1), i);
        }
        Log.d("sanksar", strValueOf + " " + copticMonth(i2) + " " + strValueOf2);
        return strValueOf2 + " " + copticMonth(i2) + " " + strValueOf;
    }

    public String copticMonth(int i) {
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

    @Override // android.app.Activity
    protected Dialog onCreateDialog(int i) {
        if (i == 999) {
            return new DatePickerDialog(this, this.datePickerListener, this.yearr, this.monthh, this.dayy);
        }
        return super.onCreateDialog(i);
    }

    public void ReadData(int i, int i2, int i3, int i4) {
        try {
            this.sayer.clear();
            this.sayerTitle.clear();
            String str = null;
            if (i4 == -1) {
                str = new String(app.decodeFile2(this, "piapostolos", "sanksar2/" + i2 + "/" + (i2 < 10 ? "0" + i2 + " (" + i + ")" : i2 + " (" + i + ")") + ".txt"));
            } else if (i4 == 0) {
                str = new String(app.decodeFile2(this, "piapostolos", "sanksar2/special/1.txt"));
            } else if (i4 == 1) {
                str = new String(app.decodeFile2(this, "piapostolos", "sanksar2/special/40.txt"));
            } else if (i4 == 2) {
                str = new String(app.decodeFile2(this, "piapostolos", "sanksar2/special/50.txt"));
            }
            String[] strArrSplit = str.split("\n");
            String str2 = "";
            boolean z = false;
            int i5 = 0;
            for (int i6 = 0; i6 < strArrSplit.length; i6++) {
                System.out.println("" + strArrSplit[i6] + "=size" + strArrSplit[i6].length());
                if (i6 == 0) {
                    str2 = str2 + strArrSplit[i6] + "\n";
                } else if (strArrSplit[i6] == null || strArrSplit[i6].equals("") || strArrSplit[i6].length() < 3 || strArrSplit[i6].equals(StringUtils.CR)) {
                    if (i5 != 0) {
                        this.sayer.add(str2);
                        str2 = "";
                    }
                    i5++;
                    z = true;
                } else if (!z) {
                    this.sayerTitle.add(strArrSplit[i6]);
                } else if (i6 == strArrSplit.length - 1) {
                    this.sayer.add(str2 + strArrSplit[i6] + "\n");
                    str2 = "";
                } else if (i5 > 0) {
                    str2 = str2 + strArrSplit[i6] + "\n";
                }
            }
            String[] strArr = new String[this.sayerTitle.size()];
            for (int i7 = 0; i7 < this.sayerTitle.size(); i7++) {
                strArr[i7] = this.sayerTitle.get(i7);
            }
            setspinnn(strArr);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void setspinnn(String[] strArr) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_white, strArr);
        this.adapterglav = arrayAdapter;
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item_black);
        this.spinner2.setAdapter((SpinnerAdapter) this.adapterglav);
        this.spinner2.setPrompt(getString(R.string.hello_world));
    }

    public int getmonthInt(String str) {
        if (str.equals("Jan")) {
            return 0;
        }
        if (str.equals("Feb")) {
            return 1;
        }
        if (str.equals("Mar")) {
            return 2;
        }
        if (str.equals("Apr")) {
            return 3;
        }
        if (str.equals("May")) {
            return 4;
        }
        if (str.equals("Jun")) {
            return 5;
        }
        if (str.equals("Jul")) {
            return 6;
        }
        if (str.equals("Aug")) {
            return 7;
        }
        if (str.equals("Sep")) {
            return 8;
        }
        if (str.equals("Oct")) {
            return 9;
        }
        if (str.equals("Nov")) {
            return 10;
        }
        return str.equals("Dec") ? 11 : 0;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        AppLeftSlider.setCurrentActivity(this);
        if (app.getCopticDate() != null) {
            this.sanksarDayTitle = app.getCopticDate().getYear() + " " + copticMonth(app.getCopticDate().getMonth()) + " " + app.getCopticDate().getDay();
            Calendar calendar = Calendar.getInstance();
            CopticCalendar copticCalendar = new CopticCalendar(app.getCopticDate().getYear(), app.getCopticDate().getMonth() + (-1), app.getCopticDate().getDay(), calendar.get(11), calendar.get(12), calendar.get(13));
            StringBuilder sb = new StringBuilder();
            sb.append("coptic time=");
            sb.append(copticCalendar.getTime());
            Log.d("orso", sb.toString());
            Log.d("orso", "coptic time=" + copticCalendar.getTime().toGMTString());
            Log.d("orso", "coptic time=" + copticCalendar.getTime().getTime());
            String[] strArrSplit = copticCalendar.getTime().toString().split(" ");
            for (String str : strArrSplit) {
                Log.d("orso", "index=" + str);
            }
            Calendar orthodoxEaster = getOrthodoxEaster(Integer.valueOf(strArrSplit[5]).intValue());
            Log.d("orso", "coptic time orth0=" + orthodoxEaster.getTime());
            Log.d("orso", "coptic time orth0=" + orthodoxEaster.getTime().toGMTString());
            Log.d("orso", "coptic time orth0=" + orthodoxEaster.getTime().getTime());
            if (orthodoxEaster.get(1) == Integer.valueOf(strArrSplit[5]).intValue() && orthodoxEaster.get(5) == Integer.valueOf(strArrSplit[2]).intValue() && orthodoxEaster.get(2) == getmonthInt(strArrSplit[1])) {
                this.isEaster = true;
                ReadData(app.getCopticDate().getDay(), app.getCopticDate().getMonth(), app.getCopticDate().getYear(), 0);
            }
            orthodoxEaster.set(6, orthodoxEaster.get(6) + 39);
            if (orthodoxEaster.get(1) == Integer.valueOf(strArrSplit[5]).intValue() && orthodoxEaster.get(5) == Integer.valueOf(strArrSplit[2]).intValue() && orthodoxEaster.get(2) == getmonthInt(strArrSplit[1])) {
                this.isSood = true;
                ReadData(app.getCopticDate().getDay(), app.getCopticDate().getMonth(), app.getCopticDate().getYear(), 1);
            }
            Calendar orthodoxEaster2 = getOrthodoxEaster(Integer.valueOf(strArrSplit[5]).intValue());
            orthodoxEaster2.set(6, orthodoxEaster2.get(6) + 49);
            if (orthodoxEaster2.get(1) == Integer.valueOf(strArrSplit[5]).intValue() && orthodoxEaster2.get(5) == Integer.valueOf(strArrSplit[2]).intValue() && orthodoxEaster2.get(2) == getmonthInt(strArrSplit[1])) {
                this.isKamasen = true;
                ReadData(app.getCopticDate().getDay(), app.getCopticDate().getMonth(), app.getCopticDate().getYear(), 2);
            }
            if (!this.isEaster && !this.isKamasen && !this.isSood) {
                int month = app.getCopticDate().getMonth();
                if (Integer.valueOf(String.valueOf(app.getCopticDate().getYear()).substring(2, 4)).intValue() % 4 == 0 && month == 4) {
                    if (app.getCopticDate().getDay() == 27) {
                        ReadData(28, month, app.getCopticDate().getYear(), -1);
                    } else if (app.getCopticDate().getDay() == 29 || app.getCopticDate().getDay() == 28) {
                        ReadData(29, month, app.getCopticDate().getYear(), -1);
                    } else {
                        ReadData(app.getCopticDate().getDay(), month, app.getCopticDate().getYear(), -1);
                    }
                } else {
                    ReadData(app.getCopticDate().getDay(), month, app.getCopticDate().getYear(), -1);
                }
            }
            loadData();
            app.setCopticDate(null);
        }
        super.onResume();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        if (app.getCopticDate() != null) {
            app.setCopticDate(null);
        }
        super.onPause();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        if (app.getCopticDate() != null) {
            app.setCopticDate(null);
        }
        super.onDestroy();
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
            if (Math.abs(motionEvent.getY() - motionEvent2.getY()) > 250.0f) {
                return false;
            }
            if (motionEvent.getX() - motionEvent2.getX() > 120.0f && Math.abs(f) > 200.0f) {
                Log.d("log", "left");
                if (MainActivity_sanksar.this.spinner2.getSelectedItemPosition() > 0 && MainActivity_sanksar.this.spinner2.getCount() >= 1) {
                    MainActivity_sanksar.this.spinner2.setSelection(MainActivity_sanksar.this.spinner2.getSelectedItemPosition() - 1);
                }
                return true;
            }
            if (motionEvent2.getX() - motionEvent.getX() <= 120.0f || Math.abs(f) <= 200.0f) {
                return false;
            }
            Log.d("log", "right");
            if (MainActivity_sanksar.this.spinner2.getSelectedItemPosition() < MainActivity_sanksar.this.spinner2.getCount() - 1 && MainActivity_sanksar.this.spinner2.getCount() > 1) {
                MainActivity_sanksar.this.spinner2.setSelection(MainActivity_sanksar.this.spinner2.getSelectedItemPosition() + 1);
            }
            return true;
        }
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
}
