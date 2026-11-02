package com.app.orsozoxi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.app.orsozoxi.Adapters.ExpandableListAdapter;
import com.app.orsozoxi.Adapters.bible_BibleListAdapter;
import com.app.orsozoxi.Beans.Bible;
import com.app.orsozoxi.Beans.WhiteBackgroundActivition;
import com.app.orsozoxi.BibleDataBase.BibleDataBase;
import com.app.orsozoxi.BibleDataBase.UseDatabaseHandler;
import com.app.orsozoxi.Others.SavePrefs;
import com.app.orsozoxi.Others.checksim;
import com.app.orsozoxi.Views.MenuDateView;
import com.app.orsozoxi.Views.NonScrollListView;
import com.github.clans.fab.FloatingActionMenu;
import com.hieupt.android.standalonescrollbar.StandaloneScrollBar;
import com.hieupt.android.standalonescrollbar.ViewExtensionsKt;
import com.hieupt.android.standalonescrollbar.view.NestedScrollView2;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.util.CopticCalendar;
import com.navdrawer.SimpleSideDrawer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import net.fortuna.ical4j.model.property.RequestStatus;
import net.lingala.zip4j.util.InternalZipConstants;
import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import org.slf4j.Marker;

/* loaded from: classes.dex */
public class Katamaras extends BaseActivity implements MediaPlayer.OnCompletionListener, View.OnClickListener {
    static final int DATE_DIALOG_ID = 999;
    TextView arabic;
    bible_BibleListAdapter bibleAdapter;
    String[] books;
    int chaptersnumbers;
    private View content;
    TextView coptic;
    private Context ctx;
    Dictionary<Integer, String> d;
    Button dailysearch;
    private int dayy;
    TextView event;
    private GestureDetector gestureDetector;
    List<Bible> legendList;
    ArrayList<String> levels;
    HashMap<String, ArrayList<String>> levelsMap;
    private NonScrollListView listViewFootballLegend;
    private FloatingActionMenu mHomeFloatingActionMenu;
    private MenuDateView menuDateView;
    private int monthh;
    private MediaPlayer mp;
    Button playmusic;
    Button search;
    SimpleSideDrawer slide_me;
    Spinner spinner2;
    private int yearr;
    int book = 0;
    int chapter = 0;
    int pos = 0;
    private final int CONTEXT_MENU_ID = 1;
    String ma = "مزمور عشية";
    String ba = "إنجيل عشية";
    String mb = "مزمور باكر";
    String bb = "إنجيل باكر";
    String bo = "البولس";
    String ka = "الكاثوليكون";
    String ab = "الإبركسيس";
    String m = "مزمور الإنجيل";
    String mm = "مزمور المساء";
    String b = "الإنجيل";
    String n = "النبؤات";
    int def = 0;
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() { // from class: com.app.orsozoxi.Katamaras.7
        @Override // android.app.DatePickerDialog.OnDateSetListener
        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
            Katamaras.this.yearr = i;
            Katamaras.this.monthh = i2;
            Katamaras.this.dayy = i3;
            Calendar calendar = Calendar.getInstance();
            calendar.set(11, calendar.get(11));
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(5, Katamaras.this.dayy);
            calendar2.set(2, Katamaras.this.monthh);
            calendar2.set(1, Katamaras.this.yearr);
            calendar2.set(11, calendar.get(11));
            calendar2.set(12, calendar.get(12));
            calendar2.set(13, calendar.get(13));
            int iIntValue = Integer.valueOf(((calendar2.getTimeInMillis() - calendar.getTimeInMillis()) / 86400000) + "").intValue();
            CopticCalendar copticCalendar = new CopticCalendar();
            System.out.println("dayss" + iIntValue);
            copticCalendar.set(6, copticCalendar.get(6) + iIntValue);
            copticCalendar.set(11, calendar.get(11));
            copticCalendar.set(12, calendar.get(12));
            copticCalendar.set(13, calendar.get(13));
            Katamaras.this.legendList.clear();
            Katamaras.this.calculateEvents(copticCalendar.get(5), copticCalendar.get(2), copticCalendar.get(1), calendar2.get(5), calendar2.get(2), calendar2.get(1), Katamaras.this.dayOfWeek(calendar2.get(7)));
            System.out.println("monthh" + Katamaras.this.monthh);
            Katamaras.this.listViewFootballLegend.setSelection(0);
        }
    };
    boolean isSoomKeber = false;
    int soomKeberDayForTafser = 1;
    String bookPath = "";
    int bookPathChapters = 0;

    /* JADX INFO: Access modifiers changed from: private */
    public String dayOfWeek(int i) {
        return i == 1 ? "Sun" : i == 2 ? "Mon" : i == 3 ? "Tue" : i == 4 ? "Wed" : i == 5 ? "Thu" : i == 6 ? "Fri" : "Sat";
    }

    public String choosebook(int i) {
        return i == 0 ? "arabbible0" : i == 1 ? "arabbible1" : i == 2 ? "arabbible2" : i == 3 ? "arabbible3" : i == 4 ? "arabbible4" : i == 5 ? "arabbible5" : i == 6 ? "arabbible6" : i == 7 ? "arabbible7" : i == 8 ? "arabbible8" : i == 9 ? "arabbible9" : i == 10 ? "arabbible10" : i == 11 ? "arabbible11" : i == 12 ? "arabbible12" : i == 13 ? "arabbible13" : i == 14 ? "arabbible14" : i == 15 ? "arabbible15" : i == 16 ? "arabbible16" : i == 17 ? "arabbible17" : i == 18 ? "arabbible18" : i == 19 ? "arabbible19" : i == 20 ? "arabbible20" : i == 21 ? "arabbible21" : i == 22 ? "arabbible22" : i == 23 ? "arabbible23" : i == 24 ? "arabbible24" : i == 25 ? "arabbible25" : i == 26 ? "arabbible26" : i == 27 ? "arabbible27" : i == 28 ? "arabbible28" : i == 29 ? "arabbible29" : i == 30 ? "arabbible30" : i == 31 ? "arabbible31" : i == 32 ? "arabbible32" : i == 33 ? "arabbible33" : i == 34 ? "arabbible34" : i == 35 ? "arabbible35" : i == 36 ? "arabbible36" : i == 37 ? "arabbible37" : i == 38 ? "arabbible38" : i == 39 ? "arabbible39" : i == 40 ? "arabbible40" : i == 41 ? "arabbible41" : i == 42 ? "arabbible42" : i == 43 ? "arabbible43" : i == 44 ? "arabbible44" : i == 45 ? "arabbible45" : i == 46 ? "arabbible46" : i == 47 ? "arabbible47" : i == 48 ? "arabbible48" : i == 49 ? "arabbible49" : i == 50 ? "arabbible50" : i == 51 ? "arabbible51" : i == 52 ? "arabbible52" : i == 53 ? "arabbible53" : i == 54 ? "arabbible54" : i == 55 ? "arabbible55" : i == 56 ? "arabbible56" : i == 57 ? "arabbible57" : i == 58 ? "arabbible58" : i == 59 ? "arabbible59" : i == 60 ? "arabbible60" : i == 61 ? "arabbible61" : i == 62 ? "arabbible62" : i == 63 ? "arabbible63" : i == 64 ? "arabbible64" : i == 65 ? "arabbible65" : i == 66 ? "arabbible66" : i == 67 ? "arabbible67" : i == 68 ? "arabbible68" : i == 69 ? "arabbible69" : i == 70 ? "arabbible70" : i == 71 ? "arabbible71" : i == 72 ? "arabbible72" : "";
    }

    public String getmonth(int i) {
        return i == 1 ? "jan" : i == 2 ? "feb" : i == 3 ? "Mar" : i == 4 ? "Apr" : i == 5 ? "May" : i == 6 ? "Jun" : i == 7 ? "Jul" : i == 8 ? "Aug" : i == 9 ? "Sep" : i == 10 ? "Oct" : i == 11 ? "Nov" : i == 12 ? "Dec" : "";
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mediaPlayer) {
    }

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) throws IOException {
        super.onCreate(bundle);
        setContentView(R.layout.activity_katamars_main);
        this.ma = getString(R.string.t_mzmor_ashya);
        this.ba = getString(R.string.t_engel_ashya);
        this.mb = getString(R.string.t_mzmor_baker);
        this.bb = getString(R.string.t_engel_baker);
        this.bo = getString(R.string.t_boules);
        this.ka = getString(R.string.t_kathlekon);
        this.ab = getString(R.string.t_apraksis);
        this.m = getString(R.string.t_mzmor_engel);
        this.mm = getString(R.string.t_mzmor_night);
        this.b = getString(R.string.t_the_bible);
        this.n = getString(R.string.t_predictions);
        Button button = (Button) findViewById(R.id.btn_play_music);
        this.playmusic = button;
        button.setVisibility(0);
        this.mp = MediaPlayer.create(getApplicationContext(), R.raw.music);
        this.playmusic.setBackgroundResource(R.drawable.btn_play);
        this.playmusic.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Katamaras.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) throws IllegalStateException {
                if (!Katamaras.this.mp.isPlaying()) {
                    Katamaras katamaras = Katamaras.this;
                    katamaras.mp = katamaras.mp = MediaPlayer.create(katamaras.getApplicationContext(), R.raw.music);
                    Katamaras.this.playSong();
                    Katamaras.this.playmusic.setBackgroundResource(R.drawable.btn_pause);
                    return;
                }
                Katamaras.this.playmusic.setBackgroundResource(R.drawable.btn_play);
                Katamaras.this.mp.pause();
            }
        });
        this.ctx = this;
        getWindow().addFlags(128);
        new checksim();
        initFloating();
        this.arabic = (TextView) findViewById(R.id.tv_arabic);
        this.coptic = (TextView) findViewById(R.id.tv_coptic);
        this.event = (TextView) findViewById(R.id.tv_event);
        NestedScrollView2 nestedScrollView2 = (NestedScrollView2) findViewById(R.id.v_nested);
        ViewExtensionsKt.attachTo((StandaloneScrollBar) findViewById(R.id.scrollbar), nestedScrollView2);
        WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs((Activity) this, (Class<?>) WhiteBackgroundActivition.class).load();
        if (whiteBackgroundActivition == null || !whiteBackgroundActivition.isWhiteBackground()) {
            nestedScrollView2.setBackgroundResource(R.drawable.background);
        } else {
            this.arabic.setTextColor(Color.parseColor("#000000"));
            this.coptic.setTextColor(Color.parseColor("#000000"));
            this.event.setTextColor(Color.parseColor("#000000"));
        }
        Button button2 = (Button) findViewById(R.id.btn_slide);
        SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        this.slide_me = simpleSideDrawer;
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button2.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Katamaras.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Katamaras.this.slide_me.toggleLeftDrawer();
            }
        });
        getResources();
        this.legendList = new ArrayList();
        this.dailysearch = (Button) findViewById(R.id.dailyysea);
        this.d = new Hashtable();
        this.books = new String[73];
        this.book = 0;
        this.chapter = 0;
        this.levelsMap = new HashMap<>();
        this.levels = new ArrayList<>();
        try {
            InputStream inputStreamOpen = getAssets().open("biblenames.xml");
            byte[] bArr = new byte[inputStreamOpen.available()];
            inputStreamOpen.read(bArr);
            String[] strArrSplit = new String(bArr).split("\n");
            this.books = new String[73];
            this.d = new Hashtable();
            for (int i = 0; i < strArrSplit.length; i++) {
                if (i < 73) {
                    this.d.put(Integer.valueOf(i), strArrSplit[i]);
                    this.books[i] = strArrSplit[i];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        NonScrollListView nonScrollListView = (NonScrollListView) findViewById(R.id.liss1);
        this.listViewFootballLegend = nonScrollListView;
        nonScrollListView.setBackgroundColor(0);
        this.listViewFootballLegend.setCacheColorHint(0);
        this.listViewFootballLegend.setDivider(null);
        bible_BibleListAdapter bible_biblelistadapter = new bible_BibleListAdapter(getApplicationContext(), R.layout.row_agpya_item, this.legendList, this.books, null, -1);
        this.bibleAdapter = bible_biblelistadapter;
        this.listViewFootballLegend.setAdapter((ListAdapter) bible_biblelistadapter);
        Calendar calendar = Calendar.getInstance();
        new Date(System.currentTimeMillis()).toGMTString().split(" ");
        this.yearr = calendar.get(1);
        this.monthh = calendar.get(2);
        this.dayy = calendar.get(5);
        ShowAfter5Message();
        Calendar calendar2 = Calendar.getInstance();
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(11, calendar3.get(11));
        if (calendar3.get(11) >= 17) {
            Calendar calendar4 = Calendar.getInstance();
            calendar4.set(11, calendar2.get(11));
            CopticCalendar copticCalendar = new CopticCalendar();
            calendar4.set(6, calendar4.get(6) + 1);
            copticCalendar.set(6, copticCalendar.get(6) + 1);
            calculateEvents(copticCalendar.get(5), copticCalendar.get(2), copticCalendar.get(1), calendar4.get(5), calendar4.get(2), calendar4.get(1), dayOfWeek(calendar4.get(7)));
        } else {
            Calendar calendar5 = Calendar.getInstance();
            calendar5.set(11, calendar5.get(11));
            CopticCalendar copticCalendar2 = new CopticCalendar();
            calculateEvents(copticCalendar2.get(5), copticCalendar2.get(2), copticCalendar2.get(1), calendar5.get(5), calendar5.get(2), calendar5.get(1), dayOfWeek(calendar5.get(7)));
        }
        this.dailysearch.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Katamaras.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Katamaras.this.showDialog(999);
            }
        });
        ((Button) findViewById(R.id.dailyyseaCoptic)).setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Katamaras.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Katamaras.this.startActivity(new Intent(Katamaras.this, (Class<?>) SanksarCopticDataPickerActivity.class));
            }
        });
    }

    private void ShowAfter5Message() {
        katamarsReaders katamarsreaders = new katamarsReaders(this, false);
        if (Calendar.getInstance().get(11) < 17 || katamarsreaders.eidName.equals("kyama") || katamarsreaders.eidName.equals("gomaa_azema") || katamarsreaders.eidName.contains("sabt_farah") || katamarsreaders.eidName.equals("barmoon_gotas_lastday") || katamarsreaders.eidName.equals("barmoon_gotas_lastday_sun") || katamarsreaders.eidName.equals("eid_gotas") || katamarsreaders.eidName.equals("barmoon_melad_lastday") || katamarsreaders.eidName.equals("eid_melad")) {
            return;
        }
        new AlertDialog.Builder(this).setIcon(R.drawable.logo).setTitle(getString(R.string.alert_txt)).setMessage(getString(R.string.alert_5_msg)).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.app.orsozoxi.Katamaras.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
    }

    private void initFloating() {
        FloatingActionMenu floatingActionMenu = (FloatingActionMenu) findViewById(R.id.menu_options);
        this.mHomeFloatingActionMenu = floatingActionMenu;
        floatingActionMenu.setIconAnimated(false);
        this.mHomeFloatingActionMenu.setClosedOnTouchOutside(true);
        this.mHomeFloatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Katamaras.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Katamaras.this.isSoomKeber) {
                    Katamaras.this.findViewById(R.id.fb_tafser).setVisibility(0);
                } else {
                    Katamaras.this.findViewById(R.id.fb_tafser).setVisibility(8);
                }
                Katamaras.this.mHomeFloatingActionMenu.toggle(true);
            }
        });
        findViewById(R.id.fb_copy).setOnClickListener(this);
        findViewById(R.id.fb_share).setOnClickListener(this);
        findViewById(R.id.fb_tafser).setOnClickListener(this);
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
    public void onClick(View view) {
        this.listViewFootballLegend.getAdapter().getItemId(this.pos);
        String str = this.event.getText().toString() + "\n" + this.arabic.getText().toString() + "\n" + this.coptic.getText().toString();
        for (int i = 0; i < this.legendList.size(); i++) {
            str = str + "\n" + this.legendList.get(i).getNick();
        }
        int id = view.getId();
        if (id == R.id.fb_copy) {
            this.mHomeFloatingActionMenu.toggle(false);
            app.copyText(this, str);
            return;
        }
        if (id == R.id.fb_share) {
            this.mHomeFloatingActionMenu.toggle(false);
            app.shareText(this, str);
        } else {
            if (id != R.id.fb_tafser) {
                return;
            }
            this.mHomeFloatingActionMenu.toggle(false);
            Intent intent = new Intent(this, (Class<?>) KatamarasTafserActivity.class);
            intent.putExtra("tafserDay", this.soomKeberDayForTafser);
            intent.putExtra("title", "تفسير قطمارس الصوم");
            startActivity(intent);
        }
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

    public String calculateEvents(int i, int i2, int i3, int i4, int i5, int i6, String str) {
        int i7;
        boolean z;
        boolean z2;
        int i8;
        int i9;
        int i10;
        String string;
        int i11;
        NestedScrollView2 nestedScrollView2 = (NestedScrollView2) findViewById(R.id.v_nested);
        WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs((Activity) this, (Class<?>) WhiteBackgroundActivition.class).load();
        if (whiteBackgroundActivition == null || !whiteBackgroundActivition.isWhiteBackground()) {
            nestedScrollView2.setBackgroundResource(R.drawable.background);
        } else {
            nestedScrollView2.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        this.arabic.setText(dayOfWeekArabic(str) + " " + i4 + " " + getmonthArabic(i5 + 1) + " " + i6);
        TextView textView = this.coptic;
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append(" ");
        sb.append(getmonthCoptic(i2 + 1));
        sb.append(" ");
        sb.append(i3);
        textView.setText(sb.toString());
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, i6);
        calendar.set(2, i5);
        calendar.set(5, i4);
        Log.d("cal", "cal " + calendar.getTime().toString());
        Log.d("cal", "cal gmt " + calendar.getTime().toGMTString());
        Calendar orthodoxEaster = getOrthodoxEaster(i6);
        int i12 = orthodoxEaster.get(5);
        int i13 = orthodoxEaster.get(2);
        if (i6 == orthodoxEaster.get(1) && i5 == i13 && i4 == i12) {
            load7maseenData("1", "1");
            this.event.setText(R.string.e_kyama);
            return "read eid kyama";
        }
        Calendar orthodoxEaster2 = getOrthodoxEaster(i6);
        orthodoxEaster2.set(6, orthodoxEaster2.get(6) - 69);
        StringBuilder sb2 = new StringBuilder();
        String string2 = "";
        sb2.append("");
        sb2.append(orthodoxEaster2.getTime().toString());
        Log.d("cal", sb2.toString());
        Calendar orthodoxEaster3 = getOrthodoxEaster(i6);
        orthodoxEaster3.set(6, orthodoxEaster3.get(6) - 66);
        if (((isCalendarsEqual(calendar, orthodoxEaster2) == 0 || calendar.compareTo(orthodoxEaster2) == 1) && calendar.compareTo(orthodoxEaster3) == -1) || calendar.compareTo(orthodoxEaster3) == 0) {
            if (i2 == 5 && i == 8) {
                loadingAyam(8, 5);
                this.event.setText(R.string.e_jesus_inside_hykal);
                return "read d7'ol jesus el hykal from ayam katamars";
            }
            int i14 = (calendar.get(6) - orthodoxEaster2.get(6)) + 1;
            loadYounanData(i14 + "", RequestStatus.SUCCESS);
            if (str.equals("Thu")) {
                this.event.setText(R.string.e_fes7_younan);
            } else {
                this.event.setText(R.string.e_soom_younan);
            }
            return "read yunan we are in day=" + i14;
        }
        Calendar orthodoxEaster4 = getOrthodoxEaster(i6);
        orthodoxEaster4.set(6, orthodoxEaster4.get(6) - 57);
        Calendar orthodoxEaster5 = getOrthodoxEaster(i6);
        orthodoxEaster5.set(6, orthodoxEaster5.get(6) - 9);
        if ((isCalendarsEqual(calendar, orthodoxEaster4) == 0 || calendar.compareTo(orthodoxEaster4) == 1) && calendar.compareTo(getOrthodoxEaster(i6)) == -1) {
            int i15 = (calendar.get(6) - orthodoxEaster4.get(6)) + 1;
            if (i15 < 49 && i == 29 && i2 == 6 && calendar.compareTo(orthodoxEaster5) == -1) {
                loadingAyam(29, i2);
                this.event.setText(R.string.e_beshara);
                return "read eid el beshara from katamars el ayam";
            }
            if (i15 < 49 && i == 10 && i2 == 6) {
                loadingAyam(10, 6);
                this.event.setText(R.string.e_saleb);
                return "read eid el saleb";
            }
            if (i15 == 1) {
                loadSoomData(i15 + "", "0");
                this.event.setText(R.string.e_sabet_refa3_soom_kbier);
            } else if (i15 == 2) {
                if (i2 == 5 && i == 8) {
                    loadingAyam(8, 5);
                    Log.d("orso", "orsoعيد  دخول السيد المسيح الهيكل");
                    this.event.setText(getString(R.string.e_jesus_inside_hykal) + "\n" + getString(R.string.e_a7ad_refa3_soom_kbier));
                    return "read d7'ol jesus el hykal from ayam katamars";
                }
                this.event.setText(R.string.e_a7ad_refa3_soom_kbier);
                loadSoomData(i15 + "", "0");
            } else if (i15 == 9) {
                loadSoomData(i15 + "", "0");
                this.event.setText(R.string.e_a7ad_konoz);
            } else if (i15 == 16) {
                loadSoomData(i15 + "", "0");
                this.event.setText(R.string.e_a7ad_tagroba);
            } else if (i15 == 23) {
                loadSoomData(i15 + "", "0");
                this.event.setText(R.string.e_a7ad_ebn_dal);
            } else if (i15 == 30) {
                loadSoomData(i15 + "", "0");
                this.event.setText(R.string.e_a7ad_samrya);
            } else if (i15 == 37) {
                loadSoomData(i15 + "", "0");
                this.event.setText(R.string.e_a7ad_m7la3);
            } else if (i15 == 44) {
                loadSoomData(i15 + "", "0");
                this.event.setText(R.string.e_a7ad_mwlood_3maa);
            } else if (i15 == 49) {
                loadSoomData(i15 + "", "0");
                this.event.setText(R.string.e_gomat_7etam);
            } else if (i15 == 50) {
                loadSoomData(i15 + "", "0");
                this.event.setText(R.string.e_sabt_laazer);
            } else if (i15 == 51) {
                loadSoomData(i15 + "", "0");
                this.event.setText(R.string.e_a7ad_shaneen);
            } else if (i15 == 52) {
                loadSoomData(i15 + "", "0");
                this.event.setText(R.string.e_mon_bas7a);
            } else if (i15 == 53) {
                loadSoomData(i15 + "", "0");
                this.event.setText(R.string.e_tue_bas7a);
            } else if (i15 == 54) {
                loadSoomData(i15 + "", "0");
                this.event.setText(R.string.e_wed_bas7a);
            } else if (i15 == 55) {
                loadSoomData(i15 + "", "0");
                this.event.setText(R.string.e_khames_ahd);
            } else if (i15 == 56) {
                loadSoomData(i15 + "", "0");
                this.event.setText(R.string.e_gomaa_azema);
            } else if (i15 == 57) {
                loadSoomData(i15 + "", "0");
                this.event.setText(R.string.e_sabt_farah);
            } else {
                loadSoomData(i15 + "", "0");
                if (i15 == 3 || i15 == 10 || i15 == 17 || i15 == 24 || i15 == 31 || i15 == 38 || i15 == 45) {
                    string2 = getString(R.string.e_mon);
                } else if (i15 == 4 || i15 == 11 || i15 == 18 || i15 == 25 || i15 == 32 || i15 == 39 || i15 == 46) {
                    string2 = getString(R.string.e_tue);
                } else if (i15 == 5 || i15 == 12 || i15 == 19 || i15 == 26 || i15 == 33 || i15 == 40 || i15 == 47) {
                    string2 = getString(R.string.e_wed);
                } else if (i15 == 6 || i15 == 13 || i15 == 20 || i15 == 27 || i15 == 34 || i15 == 41 || i15 == 48) {
                    string2 = getString(R.string.e_thur);
                } else if (i15 == 7 || i15 == 14 || i15 == 21 || i15 == 28 || i15 == 35 || i15 == 42) {
                    string2 = getString(R.string.e_fri);
                } else if (i15 == 8 || i15 == 15 || i15 == 22 || i15 == 29 || i15 == 36 || i15 == 43) {
                    string2 = getString(R.string.e_sat);
                }
                if (i15 >= 3 && i15 <= 8) {
                    string2 = string2 + " " + getString(R.string.e_week1);
                } else if (i15 >= 10 && i15 <= 15) {
                    string2 = string2 + " " + getString(R.string.e_week2);
                } else if (i15 >= 17 && i15 <= 22) {
                    string2 = string2 + " " + getString(R.string.e_week3);
                } else if (i15 >= 24 && i15 <= 29) {
                    string2 = string2 + " " + getString(R.string.e_week4);
                } else if (i15 >= 31 && i15 <= 36) {
                    string2 = string2 + " " + getString(R.string.e_week5);
                } else if (i15 >= 38 && i15 <= 43) {
                    string2 = string2 + " " + getString(R.string.e_week6);
                } else if (i15 >= 45) {
                    string2 = string2 + " " + getString(R.string.e_week7);
                }
                this.event.setText(string2);
            }
            return "read soom kyama we are in day=" + i15;
        }
        Calendar orthodoxEaster6 = getOrthodoxEaster(i6);
        orthodoxEaster6.set(6, orthodoxEaster6.get(6) + 1);
        Calendar orthodoxEaster7 = getOrthodoxEaster(i6);
        orthodoxEaster7.set(6, orthodoxEaster7.get(6) + 49);
        if (((isCalendarsEqual(calendar, orthodoxEaster6) == 0 || calendar.compareTo(orthodoxEaster6) == 1) && calendar.compareTo(orthodoxEaster7) == -1) || isCalendarsEqual(calendar, orthodoxEaster7) == 0) {
            int i16 = (calendar.get(6) - orthodoxEaster6.get(6)) + 2;
            load7maseenData(i16 + "", "1");
            if (i16 == 40) {
                this.event.setText(R.string.e_sood);
            } else if (i16 == 50) {
                this.event.setText(R.string.e_helol);
            } else {
                this.event.setText(R.string.e_7maseen);
            }
            if (i16 != 51) {
                return "read from katmars el kamaseen we are in day=" + i16;
            }
        }
        if (Integer.valueOf(String.valueOf(i3).substring(2, 4)).intValue() % 4 == 0) {
            i7 = 3;
            if (i2 == 3) {
                if (i == 27) {
                    loadingAyam(28, 3);
                    this.event.setText(R.string.e_barmoon_melad);
                    return "read barmoon 28 kiahk instead of 27kiahk";
                }
                if (i == 28 || i == 29) {
                    loadingAyam(29, 3);
                    this.event.setText(R.string.e_melad);
                    return "read 29 kiahk";
                }
                if (i == 27 && str.equals("Fri")) {
                    loadingAyam(28, 3);
                    this.event.setText(R.string.e_barmoon_melad);
                    return "read barmon melad 28 kiahk";
                }
                if (i == 26 && str.equals("Fri")) {
                    loadingAyam(28, 3);
                    this.event.setText(R.string.e_barmoon_melad);
                    return "read barmon melad 28 kiahk";
                }
                if (i == 27 && str.equals("Sat")) {
                    loadingAyam(28, 3);
                    this.event.setText(R.string.e_barmoon_melad);
                    return "read barmon melad 28 kiahk";
                }
                if (i == 25 && str.equals("Fri")) {
                    loadingAyam(28, 3);
                    this.event.setText(R.string.e_barmoon_melad);
                    return "read barmon melad 28 kiahk";
                }
                if (i == 26 && str.equals("Sat")) {
                    loadingAyam(28, 3);
                    this.event.setText(R.string.e_barmoon_melad);
                    return "read barmon melad 28 kiahk";
                }
                if (i == 27 && str.equals("Sun")) {
                    loadingAyam(28, 3);
                    this.event.setText(R.string.e_barmoon_melad);
                    return "read barmon melad 28 kiahk";
                }
            }
        } else {
            i7 = 3;
        }
        if (i2 == i7 && i == 28 && str.equals("Fri")) {
            loadingAyam(28, i7);
            this.event.setText(R.string.e_barmoon_melad);
            return "read barmon melad 28 kiahk";
        }
        if (i2 == i7 && i == 27 && str.equals("Fri")) {
            loadingAyam(28, i7);
            this.event.setText(R.string.e_barmoon_melad);
            return "read barmon melad 28 kiahk";
        }
        if (i2 == i7 && i == 28 && str.equals("Sat")) {
            loadingAyam(28, i7);
            this.event.setText(R.string.e_barmoon_melad);
            return "read barmon melad 28 kiahk";
        }
        if (i2 == i7 && i == 29 && str.equals("Sun")) {
            loadingAyam(29, i7);
            this.event.setText(R.string.e_melad);
            return "read 29 kiahk";
        }
        if (i2 == i7 && i == 26 && str.equals("Fri")) {
            loadingAyam(28, i7);
            this.event.setText(R.string.e_barmoon_melad);
            return "read barmon melad 28 kiahk";
        }
        if (i2 == i7 && i == 27 && str.equals("Sat")) {
            loadingAyam(28, i7);
            this.event.setText(R.string.e_barmoon_melad);
            return "read barmon melad 28 kiahk";
        }
        if (i2 == i7 && i == 28 && str.equals("Sun")) {
            loadingAyam(28, i7);
            this.event.setText(R.string.e_barmoon_melad);
            return "read barmon melad 28 kiahk";
        }
        if (i2 == 2 || i2 == i7) {
            CopticCalendar copticCalendar = new CopticCalendar();
            copticCalendar.set(5, 1);
            copticCalendar.set(2, i7);
            copticCalendar.set(1, i3);
            if (dayOfWeek(copticCalendar.get(7)) == "Mon") {
                z = true;
                z2 = false;
            } else {
                if (dayOfWeek(copticCalendar.get(7)) == "Tue") {
                    z = false;
                    z2 = true;
                }
                z = false;
                z2 = false;
            }
        } else {
            z = false;
            z2 = false;
        }
        if (i2 == 4 && i == 9 && str.equals("Fri")) {
            loadingAyam(10, 4);
            this.event.setText(R.string.e_barmoon_gotas);
            return "read barmon eid 7'otas 10 tobak";
        }
        if (i2 == 4 && i == 10 && str.equals("Sat")) {
            this.event.setText(R.string.e_barmoon_gotas);
            loadingAyam(10, 4);
            return "read barmon eid 7'otas 10 toba";
        }
        if (i2 == 4 && i == 8 && str.equals("Fri")) {
            this.event.setText(R.string.e_barmoon_gotas);
            loadingAyam(10, 4);
            return "read barmon eid 7'otas 10 toba";
        }
        if (i2 == 4 && i == 9 && str.equals("Sat")) {
            this.event.setText(R.string.e_barmoon_gotas);
            loadingAyam(10, 4);
            return "read barmon eid 7'otas 10 toba";
        }
        if (i2 == 4 && i == 10 && str.equals("Sun")) {
            this.event.setText(R.string.e_barmoon_gotas);
            loadingAyam(10, 4);
            return "read barmon eid 7'otas 10 toba";
        }
        if (i2 == 0 && i > 16 && i < 20) {
            this.event.setText(R.string.e_saleb);
            loadingAyam(i, i2);
            return "";
        }
        if (!str.equals("Sun")) {
            if (i == 29 && i2 == 3) {
                this.event.setText(R.string.e_melad);
            } else if (i == 28 && i2 == 3) {
                this.event.setText(R.string.e_barmoon_melad);
            } else {
                if (i == 6) {
                    i8 = 4;
                    if (i2 == 4) {
                        this.event.setText(R.string.e_7etan);
                    }
                } else {
                    i8 = 4;
                }
                if (i == 11 && i2 == i8) {
                    this.event.setText(R.string.e_gotas);
                } else if (i == 10 && i2 == i8) {
                    this.event.setText(R.string.e_barmoon_gotas);
                } else if (i == 13 && i2 == i8) {
                    this.event.setText(R.string.e_ors_kana_galel);
                } else if (i == 21 && i2 == i8) {
                    this.event.setText(R.string.e_3dra_tzkar_ny7ah);
                } else if (i == 8 && i2 == 5) {
                    this.event.setText(R.string.e_jesus_inside_hykal);
                } else {
                    if (i == 10) {
                        i9 = 6;
                        if (i2 == 6) {
                            this.event.setText(R.string.e_saleb_zohor);
                        }
                    } else {
                        i9 = 6;
                    }
                    if (i == 29 && i2 == i9) {
                        this.event.setText(R.string.e_beshara);
                    } else if (i == 24 && i2 == 8) {
                        this.event.setText(R.string.e_jesus_inside_masr);
                    } else if (i == 1 && i2 == 11) {
                        this.event.setText(R.string.e_3dra_soom);
                    } else if (i == 13 && i2 == 11) {
                        this.event.setText(R.string.e_tagaly);
                    } else if (i == 16 && i2 == 11) {
                        this.event.setText(R.string.e_gasad_3dra_sood);
                    } else if (i == 1 && i2 == 0) {
                        this.event.setText(R.string.e_nayrooz);
                    } else if (i == 17 && i2 == 0) {
                        this.event.setText(R.string.e_saleb);
                    } else {
                        if (i == 16) {
                            i10 = 2;
                            if (i2 == 2) {
                                this.event.setText(R.string.e_soom_melad_start);
                            }
                        } else {
                            i10 = 2;
                        }
                        if ((i > 16 && i2 == i10) || (i < 28 && i2 == 3)) {
                            this.event.setText(R.string.e_soom_melad);
                        } else {
                            this.event.setText(R.string.e_katamars_ayam);
                        }
                    }
                }
            }
            loadingAyam(i, i2);
            return "read from katamars ayam";
        }
        if (i == 6 && i2 == 4) {
            this.event.setText(R.string.e_7etan);
            loadingAyam(6, 4);
            return "read 7'tan";
        }
        if (i == 11 && i2 == 4) {
            this.event.setText(R.string.e_gotas);
            loadingAyam(11, 4);
            return "";
        }
        if (i == 13 && i2 == 4) {
            this.event.setText(R.string.e_ors_kana_galel);
            loadingAyam(13, 4);
            return "";
        }
        if (i == 8 && i2 == 5) {
            this.event.setText(R.string.e_jesus_inside_hykal);
            loadingAyam(8, 5);
            return "read 3id";
        }
        if (i == 10 && i2 == 6) {
            this.event.setText(R.string.e_saleb_zohor);
            loadingAyam(10, 6);
            return "read 3id ";
        }
        if (i == 29 && i2 != 0 && i2 != 2 && i2 != 3 && i2 != 4) {
            this.event.setText(R.string.e_tzkar_beshara);
            loadingAyam(29, 6);
            return "read 3id ";
        }
        if (i == 24 && i2 == 8) {
            this.event.setText(R.string.e_jesus_inside_masr);
            loadingAyam(24, 8);
            return "read 3id ";
        }
        if (i == 13 && i2 == 11) {
            this.event.setText(R.string.e_tagaly);
            loadingAyam(13, 11);
            return "read 3id ";
        }
        if (i == 1 && i2 == 0) {
            this.event.setText(R.string.e_nayrooz);
            loadingAyam(1, 0);
            return "read 3id ";
        }
        if (i == 17 && i2 == 0) {
            this.event.setText(R.string.e_saleb);
            loadingAyam(17, 0);
            return "read 3id ";
        }
        if (i < 8) {
            string = getString(R.string.week1);
            i11 = 1;
        } else if (i < 15) {
            string = getString(R.string.week2);
            i11 = 2;
        } else if (i < 22) {
            string = getString(R.string.week3);
            i11 = 3;
        } else if (i < 29) {
            string = getString(R.string.week4);
            i11 = 4;
        } else {
            string = getString(R.string.week5);
            i11 = 5;
        }
        if (i2 == 0 && i == 1 && str.equals("Sun")) {
            loadingAyam(1, 0);
            return "";
        }
        if (i2 == 0 && i == 8 && str.equals("Sun")) {
            loadingA7ad(1, 0);
            return "";
        }
        if (i2 == 0 && i == 15 && str.equals("Sun")) {
            loadingA7ad(2, 0);
            return "";
        }
        if (i2 == 0 && i == 22 && str.equals("Sun")) {
            loadingA7ad(3, 0);
            return "";
        }
        if (i2 == 0 && i == 29 && str.equals("Sun")) {
            loadingA7ad(4, 0);
            return "";
        }
        if (z) {
            if (i == 30 && i2 == 2) {
                loadingA7ad(1, 3);
                this.event.setText(getString(R.string.e_katamars_a7ad) + " " + getString(R.string.week1));
                return "read from katamars el a7ad";
            }
            if (i == 7 && i2 == 3) {
                loadingA7ad(2, 3);
                this.event.setText(getString(R.string.e_katamars_a7ad) + " " + getString(R.string.week2));
                return "read from katamars el a7ad";
            }
            if (i == 14 && i2 == 3) {
                loadingA7ad(3, 3);
                this.event.setText(getString(R.string.e_katamars_a7ad) + " " + getString(R.string.week3));
                return "read from katamars el a7ad";
            }
            if (i == 21 && i2 == 3) {
                loadingA7ad(4, 3);
                this.event.setText(getString(R.string.e_katamars_a7ad) + " " + getString(R.string.week4));
                return "read from katamars el a7ad";
            }
            if (i2 == 0 && i == 1 && str.equals("Sun")) {
                loadingAyam(1, 0);
                return "";
            }
            if (i2 == 0 && i == 8 && str.equals("Sun")) {
                loadingA7ad(1, 0);
                return "";
            }
            if (i2 == 0 && i == 15 && str.equals("Sun")) {
                loadingA7ad(2, 0);
                return "";
            }
            if (i2 == 0 && i == 22 && str.equals("Sun")) {
                loadingA7ad(3, 0);
                return "";
            }
            if (i2 == 0 && i == 29 && str.equals("Sun")) {
                loadingA7ad(4, 0);
                return "";
            }
            if (i11 == 5) {
                loadingA7ad(5, i2);
            } else {
                loadingA7ad(i11, i2);
            }
            this.event.setText(getString(R.string.e_katamars_a7ad) + " " + string);
            return "read from katamars el a7ad";
        }
        if (!z2) {
            if (i2 == 0 && i == 1 && str.equals("Sun")) {
                loadingAyam(1, 0);
                return "";
            }
            if (i2 == 0 && i == 8 && str.equals("Sun")) {
                loadingA7ad(1, 0);
                return "";
            }
            if (i2 == 0 && i == 15 && str.equals("Sun")) {
                loadingA7ad(2, 0);
                return "";
            }
            if (i2 == 0 && i == 22 && str.equals("Sun")) {
                loadingA7ad(3, 0);
                return "";
            }
            if (i2 == 0 && i == 29 && str.equals("Sun")) {
                loadingA7ad(4, 0);
                return "";
            }
            if (i11 == 5) {
                loadingA7ad(5, i2);
            } else {
                loadingA7ad(i11, i2);
            }
            this.event.setText(getString(R.string.e_katamars_a7ad) + " " + string);
            return "read from katamars el a7ad";
        }
        if (i == 29 && i2 == 2) {
            loadingA7ad(1, 3);
            this.event.setText(getString(R.string.e_katamars_a7ad) + " " + getString(R.string.week1));
            return "read from katamars el a7ad";
        }
        if (i == 6 && i2 == 3) {
            loadingA7ad(2, 3);
            this.event.setText(getString(R.string.e_katamars_a7ad) + " " + getString(R.string.week2));
            return "read from katamars el a7ad";
        }
        if (i == 13 && i2 == 3) {
            loadingA7ad(3, 3);
            this.event.setText(getString(R.string.e_katamars_a7ad) + " " + getString(R.string.week3));
            return "read from katamars el a7ad";
        }
        if (i == 20 && i2 == 3) {
            loadingA7ad(4, 3);
            this.event.setText(getString(R.string.e_katamars_a7ad) + " " + getString(R.string.week4));
            return "read from katamars el a7ad";
        }
        if (i2 == 0 && i == 1 && str.equals("Sun")) {
            loadingAyam(1, 0);
            return "";
        }
        if (i2 == 0 && i == 8 && str.equals("Sun")) {
            loadingA7ad(1, 0);
            return "";
        }
        if (i2 == 0 && i == 15 && str.equals("Sun")) {
            loadingA7ad(2, 0);
            return "";
        }
        if (i2 == 0 && i == 22 && str.equals("Sun")) {
            loadingA7ad(3, 0);
            return "";
        }
        if (i2 == 0 && i == 29 && str.equals("Sun")) {
            loadingA7ad(4, 0);
            return "";
        }
        if (i11 == 5) {
            loadingA7ad(5, i2);
        } else {
            loadingA7ad(i11, i2);
        }
        this.event.setText(getString(R.string.e_katamars_a7ad) + " " + string);
        return "read from katamars el a7ad";
    }

    private int isCalendarsEqual(Calendar calendar, Calendar calendar2) {
        int iIntValue = Integer.valueOf(((calendar.getTimeInMillis() - calendar2.getTimeInMillis()) / 86400000) + "").intValue();
        if (iIntValue == 0) {
            return 0;
        }
        return iIntValue > 0 ? 1 : -1;
    }

    private void loadingAyam(int i, int i2) {
        this.isSoomKeber = false;
        findViewById(R.id.fb_tafser).setVisibility(8);
        try {
            String str = new String(app.decodeFile2(this, "orsozoxi", "katamars/KatamarsAyam/" + (i2 + 1) + "/" + i + ".orso"));
            str.replaceAll("\\+", "");
            for (String str2 : str.split("\n")) {
                this.legendList.add(new Bible("", str2.replace(Marker.ANY_NON_NULL_MARKER, "")));
            }
            this.bibleAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void loadingA7ad(int i, int i2) {
        this.isSoomKeber = false;
        findViewById(R.id.fb_tafser).setVisibility(8);
        try {
            String str = new String(app.decodeFile2(this, "orsozoxi", "katamars/KatamarsA7ad/" + (i2 + 1) + "/" + i + ".orso"));
            str.replaceAll("\\+", "");
            for (String str2 : str.split("\n")) {
                this.legendList.add(new Bible("", str2.replace(Marker.ANY_NON_NULL_MARKER, "")));
            }
            this.bibleAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:112:0x043a  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x044c A[Catch: Exception -> 0x0b8a, IOException -> 0x0b90, TryCatch #2 {IOException -> 0x0b90, Exception -> 0x0b8a, blocks: (B:3:0x000b, B:5:0x0028, B:7:0x002b, B:10:0x003d, B:13:0x004f, B:15:0x0057, B:17:0x005f, B:18:0x0064, B:20:0x0079, B:23:0x0094, B:25:0x009c, B:27:0x00a4, B:29:0x00ac, B:32:0x00b7, B:35:0x00cc, B:37:0x010b, B:39:0x0110, B:41:0x0114, B:45:0x0129, B:47:0x0136, B:52:0x0150, B:54:0x017b, B:56:0x0183, B:64:0x01bb, B:65:0x01e4, B:67:0x01ea, B:69:0x0224, B:72:0x024a, B:74:0x0250, B:76:0x0259, B:78:0x028f, B:80:0x0297, B:82:0x02c0, B:88:0x02f3, B:90:0x02fc, B:92:0x0305, B:94:0x0324, B:96:0x0328, B:98:0x032e, B:100:0x0337, B:102:0x033d, B:104:0x0343, B:224:0x09ee, B:225:0x09fb, B:227:0x0a01, B:231:0x0a1b, B:234:0x0a23, B:236:0x0a67, B:239:0x0a7f, B:242:0x0a97, B:244:0x0a9d, B:247:0x0aa5, B:248:0x0ab3, B:249:0x0ab6, B:105:0x039b, B:107:0x03b1, B:109:0x03b7, B:110:0x03f8, B:114:0x044c, B:116:0x0456, B:118:0x047f, B:119:0x04b5, B:121:0x04c2, B:123:0x04fc, B:124:0x0535, B:83:0x02cd, B:84:0x02d6, B:127:0x059a, B:129:0x05ac, B:131:0x05b2, B:133:0x05bb, B:135:0x05c5, B:137:0x05d5, B:141:0x0606, B:143:0x0613, B:145:0x0624, B:147:0x0646, B:149:0x064a, B:151:0x0650, B:153:0x0659, B:155:0x065f, B:157:0x0665, B:158:0x06ab, B:160:0x06b5, B:162:0x06bb, B:163:0x06f4, B:167:0x0736, B:169:0x0740, B:171:0x076b, B:172:0x0798, B:173:0x07c9, B:139:0x05e7, B:140:0x05f6, B:176:0x07e6, B:178:0x07f7, B:180:0x0801, B:182:0x0811, B:186:0x0833, B:188:0x083a, B:190:0x0842, B:192:0x0853, B:194:0x0877, B:196:0x087b, B:198:0x0881, B:200:0x0889, B:202:0x088f, B:204:0x0895, B:205:0x08d0, B:207:0x08d8, B:209:0x08de, B:210:0x0911, B:214:0x094d, B:216:0x0957, B:218:0x0982, B:219:0x09a9, B:220:0x09d5, B:183:0x0820, B:256:0x0b58, B:258:0x0b62, B:58:0x019e, B:60:0x01a4, B:62:0x01aa), top: B:269:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0535 A[Catch: Exception -> 0x0b8a, IOException -> 0x0b90, TryCatch #2 {IOException -> 0x0b90, Exception -> 0x0b8a, blocks: (B:3:0x000b, B:5:0x0028, B:7:0x002b, B:10:0x003d, B:13:0x004f, B:15:0x0057, B:17:0x005f, B:18:0x0064, B:20:0x0079, B:23:0x0094, B:25:0x009c, B:27:0x00a4, B:29:0x00ac, B:32:0x00b7, B:35:0x00cc, B:37:0x010b, B:39:0x0110, B:41:0x0114, B:45:0x0129, B:47:0x0136, B:52:0x0150, B:54:0x017b, B:56:0x0183, B:64:0x01bb, B:65:0x01e4, B:67:0x01ea, B:69:0x0224, B:72:0x024a, B:74:0x0250, B:76:0x0259, B:78:0x028f, B:80:0x0297, B:82:0x02c0, B:88:0x02f3, B:90:0x02fc, B:92:0x0305, B:94:0x0324, B:96:0x0328, B:98:0x032e, B:100:0x0337, B:102:0x033d, B:104:0x0343, B:224:0x09ee, B:225:0x09fb, B:227:0x0a01, B:231:0x0a1b, B:234:0x0a23, B:236:0x0a67, B:239:0x0a7f, B:242:0x0a97, B:244:0x0a9d, B:247:0x0aa5, B:248:0x0ab3, B:249:0x0ab6, B:105:0x039b, B:107:0x03b1, B:109:0x03b7, B:110:0x03f8, B:114:0x044c, B:116:0x0456, B:118:0x047f, B:119:0x04b5, B:121:0x04c2, B:123:0x04fc, B:124:0x0535, B:83:0x02cd, B:84:0x02d6, B:127:0x059a, B:129:0x05ac, B:131:0x05b2, B:133:0x05bb, B:135:0x05c5, B:137:0x05d5, B:141:0x0606, B:143:0x0613, B:145:0x0624, B:147:0x0646, B:149:0x064a, B:151:0x0650, B:153:0x0659, B:155:0x065f, B:157:0x0665, B:158:0x06ab, B:160:0x06b5, B:162:0x06bb, B:163:0x06f4, B:167:0x0736, B:169:0x0740, B:171:0x076b, B:172:0x0798, B:173:0x07c9, B:139:0x05e7, B:140:0x05f6, B:176:0x07e6, B:178:0x07f7, B:180:0x0801, B:182:0x0811, B:186:0x0833, B:188:0x083a, B:190:0x0842, B:192:0x0853, B:194:0x0877, B:196:0x087b, B:198:0x0881, B:200:0x0889, B:202:0x088f, B:204:0x0895, B:205:0x08d0, B:207:0x08d8, B:209:0x08de, B:210:0x0911, B:214:0x094d, B:216:0x0957, B:218:0x0982, B:219:0x09a9, B:220:0x09d5, B:183:0x0820, B:256:0x0b58, B:258:0x0b62, B:58:0x019e, B:60:0x01a4, B:62:0x01aa), top: B:269:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01ea A[Catch: Exception -> 0x0b8a, IOException -> 0x0b90, TryCatch #2 {IOException -> 0x0b90, Exception -> 0x0b8a, blocks: (B:3:0x000b, B:5:0x0028, B:7:0x002b, B:10:0x003d, B:13:0x004f, B:15:0x0057, B:17:0x005f, B:18:0x0064, B:20:0x0079, B:23:0x0094, B:25:0x009c, B:27:0x00a4, B:29:0x00ac, B:32:0x00b7, B:35:0x00cc, B:37:0x010b, B:39:0x0110, B:41:0x0114, B:45:0x0129, B:47:0x0136, B:52:0x0150, B:54:0x017b, B:56:0x0183, B:64:0x01bb, B:65:0x01e4, B:67:0x01ea, B:69:0x0224, B:72:0x024a, B:74:0x0250, B:76:0x0259, B:78:0x028f, B:80:0x0297, B:82:0x02c0, B:88:0x02f3, B:90:0x02fc, B:92:0x0305, B:94:0x0324, B:96:0x0328, B:98:0x032e, B:100:0x0337, B:102:0x033d, B:104:0x0343, B:224:0x09ee, B:225:0x09fb, B:227:0x0a01, B:231:0x0a1b, B:234:0x0a23, B:236:0x0a67, B:239:0x0a7f, B:242:0x0a97, B:244:0x0a9d, B:247:0x0aa5, B:248:0x0ab3, B:249:0x0ab6, B:105:0x039b, B:107:0x03b1, B:109:0x03b7, B:110:0x03f8, B:114:0x044c, B:116:0x0456, B:118:0x047f, B:119:0x04b5, B:121:0x04c2, B:123:0x04fc, B:124:0x0535, B:83:0x02cd, B:84:0x02d6, B:127:0x059a, B:129:0x05ac, B:131:0x05b2, B:133:0x05bb, B:135:0x05c5, B:137:0x05d5, B:141:0x0606, B:143:0x0613, B:145:0x0624, B:147:0x0646, B:149:0x064a, B:151:0x0650, B:153:0x0659, B:155:0x065f, B:157:0x0665, B:158:0x06ab, B:160:0x06b5, B:162:0x06bb, B:163:0x06f4, B:167:0x0736, B:169:0x0740, B:171:0x076b, B:172:0x0798, B:173:0x07c9, B:139:0x05e7, B:140:0x05f6, B:176:0x07e6, B:178:0x07f7, B:180:0x0801, B:182:0x0811, B:186:0x0833, B:188:0x083a, B:190:0x0842, B:192:0x0853, B:194:0x0877, B:196:0x087b, B:198:0x0881, B:200:0x0889, B:202:0x088f, B:204:0x0895, B:205:0x08d0, B:207:0x08d8, B:209:0x08de, B:210:0x0911, B:214:0x094d, B:216:0x0957, B:218:0x0982, B:219:0x09a9, B:220:0x09d5, B:183:0x0820, B:256:0x0b58, B:258:0x0b62, B:58:0x019e, B:60:0x01a4, B:62:0x01aa), top: B:269:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x02fc A[Catch: Exception -> 0x0b8a, IOException -> 0x0b90, TryCatch #2 {IOException -> 0x0b90, Exception -> 0x0b8a, blocks: (B:3:0x000b, B:5:0x0028, B:7:0x002b, B:10:0x003d, B:13:0x004f, B:15:0x0057, B:17:0x005f, B:18:0x0064, B:20:0x0079, B:23:0x0094, B:25:0x009c, B:27:0x00a4, B:29:0x00ac, B:32:0x00b7, B:35:0x00cc, B:37:0x010b, B:39:0x0110, B:41:0x0114, B:45:0x0129, B:47:0x0136, B:52:0x0150, B:54:0x017b, B:56:0x0183, B:64:0x01bb, B:65:0x01e4, B:67:0x01ea, B:69:0x0224, B:72:0x024a, B:74:0x0250, B:76:0x0259, B:78:0x028f, B:80:0x0297, B:82:0x02c0, B:88:0x02f3, B:90:0x02fc, B:92:0x0305, B:94:0x0324, B:96:0x0328, B:98:0x032e, B:100:0x0337, B:102:0x033d, B:104:0x0343, B:224:0x09ee, B:225:0x09fb, B:227:0x0a01, B:231:0x0a1b, B:234:0x0a23, B:236:0x0a67, B:239:0x0a7f, B:242:0x0a97, B:244:0x0a9d, B:247:0x0aa5, B:248:0x0ab3, B:249:0x0ab6, B:105:0x039b, B:107:0x03b1, B:109:0x03b7, B:110:0x03f8, B:114:0x044c, B:116:0x0456, B:118:0x047f, B:119:0x04b5, B:121:0x04c2, B:123:0x04fc, B:124:0x0535, B:83:0x02cd, B:84:0x02d6, B:127:0x059a, B:129:0x05ac, B:131:0x05b2, B:133:0x05bb, B:135:0x05c5, B:137:0x05d5, B:141:0x0606, B:143:0x0613, B:145:0x0624, B:147:0x0646, B:149:0x064a, B:151:0x0650, B:153:0x0659, B:155:0x065f, B:157:0x0665, B:158:0x06ab, B:160:0x06b5, B:162:0x06bb, B:163:0x06f4, B:167:0x0736, B:169:0x0740, B:171:0x076b, B:172:0x0798, B:173:0x07c9, B:139:0x05e7, B:140:0x05f6, B:176:0x07e6, B:178:0x07f7, B:180:0x0801, B:182:0x0811, B:186:0x0833, B:188:0x083a, B:190:0x0842, B:192:0x0853, B:194:0x0877, B:196:0x087b, B:198:0x0881, B:200:0x0889, B:202:0x088f, B:204:0x0895, B:205:0x08d0, B:207:0x08d8, B:209:0x08de, B:210:0x0911, B:214:0x094d, B:216:0x0957, B:218:0x0982, B:219:0x09a9, B:220:0x09d5, B:183:0x0820, B:256:0x0b58, B:258:0x0b62, B:58:0x019e, B:60:0x01a4, B:62:0x01aa), top: B:269:0x000b }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.util.List<com.app.orsozoxi.Beans.Bible> loadingyunan(int r43, int r44) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 2966
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.orsozoxi.Katamaras.loadingyunan(int, int):java.util.List");
    }

    /* JADX WARN: Removed duplicated region for block: B:115:0x0391 A[Catch: Exception -> 0x0c31, IOException -> 0x0c37, TryCatch #2 {IOException -> 0x0c37, Exception -> 0x0c31, blocks: (B:3:0x000f, B:5:0x002c, B:7:0x002f, B:10:0x0045, B:13:0x0058, B:15:0x0060, B:17:0x0068, B:18:0x006d, B:20:0x007f, B:23:0x009c, B:25:0x00a4, B:27:0x00ac, B:29:0x00b4, B:32:0x00bf, B:37:0x00d7, B:39:0x00e2, B:41:0x00ea, B:43:0x00f2, B:45:0x00fa, B:47:0x0102, B:49:0x010a, B:51:0x0112, B:53:0x011a, B:55:0x0122, B:57:0x012a, B:58:0x0169, B:60:0x018b, B:62:0x0190, B:64:0x01a8, B:66:0x01ad, B:68:0x01b1, B:71:0x01ca, B:73:0x01d7, B:77:0x01ed, B:79:0x021a, B:81:0x0222, B:89:0x025a, B:90:0x0279, B:92:0x027f, B:94:0x02b9, B:97:0x02df, B:99:0x02e5, B:101:0x02ee, B:103:0x0324, B:105:0x032c, B:107:0x0355, B:113:0x0388, B:115:0x0391, B:117:0x039a, B:119:0x03b7, B:121:0x03bb, B:123:0x03bf, B:125:0x03c6, B:127:0x03cc, B:129:0x03d2, B:249:0x0a92, B:250:0x0a9d, B:252:0x0aa3, B:256:0x0ab7, B:259:0x0abf, B:261:0x0b05, B:264:0x0b1f, B:267:0x0b3b, B:269:0x0b41, B:272:0x0b49, B:273:0x0b57, B:274:0x0b5a, B:130:0x042d, B:132:0x0445, B:134:0x044b, B:135:0x048c, B:139:0x04e2, B:141:0x04ec, B:143:0x0515, B:144:0x0549, B:146:0x0554, B:148:0x058e, B:149:0x05c5, B:108:0x0362, B:109:0x036b, B:153:0x062b, B:155:0x0649, B:157:0x064f, B:159:0x0658, B:161:0x0662, B:163:0x0672, B:167:0x06a3, B:169:0x06b0, B:171:0x06c1, B:173:0x06e3, B:175:0x06e7, B:177:0x06ed, B:179:0x06f6, B:181:0x06fc, B:183:0x0702, B:184:0x0748, B:186:0x0752, B:188:0x0758, B:189:0x0791, B:193:0x07d3, B:195:0x07dd, B:197:0x0808, B:198:0x0835, B:199:0x0866, B:165:0x0684, B:166:0x0693, B:202:0x0884, B:204:0x0899, B:206:0x08a3, B:208:0x08b3, B:212:0x08d5, B:214:0x08dc, B:216:0x08e4, B:218:0x08f5, B:220:0x0919, B:222:0x091d, B:224:0x0923, B:226:0x092b, B:228:0x0931, B:230:0x0937, B:231:0x0972, B:233:0x097a, B:235:0x0980, B:236:0x09b3, B:240:0x09ef, B:242:0x09f9, B:244:0x0a24, B:245:0x0a4b, B:246:0x0a77, B:209:0x08c2, B:281:0x0bfc, B:283:0x0c04, B:83:0x023d, B:85:0x0243, B:87:0x0249), top: B:295:0x000f }] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x04ce  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x04e2 A[Catch: Exception -> 0x0c31, IOException -> 0x0c37, TryCatch #2 {IOException -> 0x0c37, Exception -> 0x0c31, blocks: (B:3:0x000f, B:5:0x002c, B:7:0x002f, B:10:0x0045, B:13:0x0058, B:15:0x0060, B:17:0x0068, B:18:0x006d, B:20:0x007f, B:23:0x009c, B:25:0x00a4, B:27:0x00ac, B:29:0x00b4, B:32:0x00bf, B:37:0x00d7, B:39:0x00e2, B:41:0x00ea, B:43:0x00f2, B:45:0x00fa, B:47:0x0102, B:49:0x010a, B:51:0x0112, B:53:0x011a, B:55:0x0122, B:57:0x012a, B:58:0x0169, B:60:0x018b, B:62:0x0190, B:64:0x01a8, B:66:0x01ad, B:68:0x01b1, B:71:0x01ca, B:73:0x01d7, B:77:0x01ed, B:79:0x021a, B:81:0x0222, B:89:0x025a, B:90:0x0279, B:92:0x027f, B:94:0x02b9, B:97:0x02df, B:99:0x02e5, B:101:0x02ee, B:103:0x0324, B:105:0x032c, B:107:0x0355, B:113:0x0388, B:115:0x0391, B:117:0x039a, B:119:0x03b7, B:121:0x03bb, B:123:0x03bf, B:125:0x03c6, B:127:0x03cc, B:129:0x03d2, B:249:0x0a92, B:250:0x0a9d, B:252:0x0aa3, B:256:0x0ab7, B:259:0x0abf, B:261:0x0b05, B:264:0x0b1f, B:267:0x0b3b, B:269:0x0b41, B:272:0x0b49, B:273:0x0b57, B:274:0x0b5a, B:130:0x042d, B:132:0x0445, B:134:0x044b, B:135:0x048c, B:139:0x04e2, B:141:0x04ec, B:143:0x0515, B:144:0x0549, B:146:0x0554, B:148:0x058e, B:149:0x05c5, B:108:0x0362, B:109:0x036b, B:153:0x062b, B:155:0x0649, B:157:0x064f, B:159:0x0658, B:161:0x0662, B:163:0x0672, B:167:0x06a3, B:169:0x06b0, B:171:0x06c1, B:173:0x06e3, B:175:0x06e7, B:177:0x06ed, B:179:0x06f6, B:181:0x06fc, B:183:0x0702, B:184:0x0748, B:186:0x0752, B:188:0x0758, B:189:0x0791, B:193:0x07d3, B:195:0x07dd, B:197:0x0808, B:198:0x0835, B:199:0x0866, B:165:0x0684, B:166:0x0693, B:202:0x0884, B:204:0x0899, B:206:0x08a3, B:208:0x08b3, B:212:0x08d5, B:214:0x08dc, B:216:0x08e4, B:218:0x08f5, B:220:0x0919, B:222:0x091d, B:224:0x0923, B:226:0x092b, B:228:0x0931, B:230:0x0937, B:231:0x0972, B:233:0x097a, B:235:0x0980, B:236:0x09b3, B:240:0x09ef, B:242:0x09f9, B:244:0x0a24, B:245:0x0a4b, B:246:0x0a77, B:209:0x08c2, B:281:0x0bfc, B:283:0x0c04, B:83:0x023d, B:85:0x0243, B:87:0x0249), top: B:295:0x000f }] */
    /* JADX WARN: Removed duplicated region for block: B:149:0x05c5 A[Catch: Exception -> 0x0c31, IOException -> 0x0c37, TryCatch #2 {IOException -> 0x0c37, Exception -> 0x0c31, blocks: (B:3:0x000f, B:5:0x002c, B:7:0x002f, B:10:0x0045, B:13:0x0058, B:15:0x0060, B:17:0x0068, B:18:0x006d, B:20:0x007f, B:23:0x009c, B:25:0x00a4, B:27:0x00ac, B:29:0x00b4, B:32:0x00bf, B:37:0x00d7, B:39:0x00e2, B:41:0x00ea, B:43:0x00f2, B:45:0x00fa, B:47:0x0102, B:49:0x010a, B:51:0x0112, B:53:0x011a, B:55:0x0122, B:57:0x012a, B:58:0x0169, B:60:0x018b, B:62:0x0190, B:64:0x01a8, B:66:0x01ad, B:68:0x01b1, B:71:0x01ca, B:73:0x01d7, B:77:0x01ed, B:79:0x021a, B:81:0x0222, B:89:0x025a, B:90:0x0279, B:92:0x027f, B:94:0x02b9, B:97:0x02df, B:99:0x02e5, B:101:0x02ee, B:103:0x0324, B:105:0x032c, B:107:0x0355, B:113:0x0388, B:115:0x0391, B:117:0x039a, B:119:0x03b7, B:121:0x03bb, B:123:0x03bf, B:125:0x03c6, B:127:0x03cc, B:129:0x03d2, B:249:0x0a92, B:250:0x0a9d, B:252:0x0aa3, B:256:0x0ab7, B:259:0x0abf, B:261:0x0b05, B:264:0x0b1f, B:267:0x0b3b, B:269:0x0b41, B:272:0x0b49, B:273:0x0b57, B:274:0x0b5a, B:130:0x042d, B:132:0x0445, B:134:0x044b, B:135:0x048c, B:139:0x04e2, B:141:0x04ec, B:143:0x0515, B:144:0x0549, B:146:0x0554, B:148:0x058e, B:149:0x05c5, B:108:0x0362, B:109:0x036b, B:153:0x062b, B:155:0x0649, B:157:0x064f, B:159:0x0658, B:161:0x0662, B:163:0x0672, B:167:0x06a3, B:169:0x06b0, B:171:0x06c1, B:173:0x06e3, B:175:0x06e7, B:177:0x06ed, B:179:0x06f6, B:181:0x06fc, B:183:0x0702, B:184:0x0748, B:186:0x0752, B:188:0x0758, B:189:0x0791, B:193:0x07d3, B:195:0x07dd, B:197:0x0808, B:198:0x0835, B:199:0x0866, B:165:0x0684, B:166:0x0693, B:202:0x0884, B:204:0x0899, B:206:0x08a3, B:208:0x08b3, B:212:0x08d5, B:214:0x08dc, B:216:0x08e4, B:218:0x08f5, B:220:0x0919, B:222:0x091d, B:224:0x0923, B:226:0x092b, B:228:0x0931, B:230:0x0937, B:231:0x0972, B:233:0x097a, B:235:0x0980, B:236:0x09b3, B:240:0x09ef, B:242:0x09f9, B:244:0x0a24, B:245:0x0a4b, B:246:0x0a77, B:209:0x08c2, B:281:0x0bfc, B:283:0x0c04, B:83:0x023d, B:85:0x0243, B:87:0x0249), top: B:295:0x000f }] */
    /* JADX WARN: Removed duplicated region for block: B:252:0x0aa3 A[Catch: Exception -> 0x0c31, IOException -> 0x0c37, TRY_LEAVE, TryCatch #2 {IOException -> 0x0c37, Exception -> 0x0c31, blocks: (B:3:0x000f, B:5:0x002c, B:7:0x002f, B:10:0x0045, B:13:0x0058, B:15:0x0060, B:17:0x0068, B:18:0x006d, B:20:0x007f, B:23:0x009c, B:25:0x00a4, B:27:0x00ac, B:29:0x00b4, B:32:0x00bf, B:37:0x00d7, B:39:0x00e2, B:41:0x00ea, B:43:0x00f2, B:45:0x00fa, B:47:0x0102, B:49:0x010a, B:51:0x0112, B:53:0x011a, B:55:0x0122, B:57:0x012a, B:58:0x0169, B:60:0x018b, B:62:0x0190, B:64:0x01a8, B:66:0x01ad, B:68:0x01b1, B:71:0x01ca, B:73:0x01d7, B:77:0x01ed, B:79:0x021a, B:81:0x0222, B:89:0x025a, B:90:0x0279, B:92:0x027f, B:94:0x02b9, B:97:0x02df, B:99:0x02e5, B:101:0x02ee, B:103:0x0324, B:105:0x032c, B:107:0x0355, B:113:0x0388, B:115:0x0391, B:117:0x039a, B:119:0x03b7, B:121:0x03bb, B:123:0x03bf, B:125:0x03c6, B:127:0x03cc, B:129:0x03d2, B:249:0x0a92, B:250:0x0a9d, B:252:0x0aa3, B:256:0x0ab7, B:259:0x0abf, B:261:0x0b05, B:264:0x0b1f, B:267:0x0b3b, B:269:0x0b41, B:272:0x0b49, B:273:0x0b57, B:274:0x0b5a, B:130:0x042d, B:132:0x0445, B:134:0x044b, B:135:0x048c, B:139:0x04e2, B:141:0x04ec, B:143:0x0515, B:144:0x0549, B:146:0x0554, B:148:0x058e, B:149:0x05c5, B:108:0x0362, B:109:0x036b, B:153:0x062b, B:155:0x0649, B:157:0x064f, B:159:0x0658, B:161:0x0662, B:163:0x0672, B:167:0x06a3, B:169:0x06b0, B:171:0x06c1, B:173:0x06e3, B:175:0x06e7, B:177:0x06ed, B:179:0x06f6, B:181:0x06fc, B:183:0x0702, B:184:0x0748, B:186:0x0752, B:188:0x0758, B:189:0x0791, B:193:0x07d3, B:195:0x07dd, B:197:0x0808, B:198:0x0835, B:199:0x0866, B:165:0x0684, B:166:0x0693, B:202:0x0884, B:204:0x0899, B:206:0x08a3, B:208:0x08b3, B:212:0x08d5, B:214:0x08dc, B:216:0x08e4, B:218:0x08f5, B:220:0x0919, B:222:0x091d, B:224:0x0923, B:226:0x092b, B:228:0x0931, B:230:0x0937, B:231:0x0972, B:233:0x097a, B:235:0x0980, B:236:0x09b3, B:240:0x09ef, B:242:0x09f9, B:244:0x0a24, B:245:0x0a4b, B:246:0x0a77, B:209:0x08c2, B:281:0x0bfc, B:283:0x0c04, B:83:0x023d, B:85:0x0243, B:87:0x0249), top: B:295:0x000f }] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x027f A[Catch: Exception -> 0x0c31, IOException -> 0x0c37, TryCatch #2 {IOException -> 0x0c37, Exception -> 0x0c31, blocks: (B:3:0x000f, B:5:0x002c, B:7:0x002f, B:10:0x0045, B:13:0x0058, B:15:0x0060, B:17:0x0068, B:18:0x006d, B:20:0x007f, B:23:0x009c, B:25:0x00a4, B:27:0x00ac, B:29:0x00b4, B:32:0x00bf, B:37:0x00d7, B:39:0x00e2, B:41:0x00ea, B:43:0x00f2, B:45:0x00fa, B:47:0x0102, B:49:0x010a, B:51:0x0112, B:53:0x011a, B:55:0x0122, B:57:0x012a, B:58:0x0169, B:60:0x018b, B:62:0x0190, B:64:0x01a8, B:66:0x01ad, B:68:0x01b1, B:71:0x01ca, B:73:0x01d7, B:77:0x01ed, B:79:0x021a, B:81:0x0222, B:89:0x025a, B:90:0x0279, B:92:0x027f, B:94:0x02b9, B:97:0x02df, B:99:0x02e5, B:101:0x02ee, B:103:0x0324, B:105:0x032c, B:107:0x0355, B:113:0x0388, B:115:0x0391, B:117:0x039a, B:119:0x03b7, B:121:0x03bb, B:123:0x03bf, B:125:0x03c6, B:127:0x03cc, B:129:0x03d2, B:249:0x0a92, B:250:0x0a9d, B:252:0x0aa3, B:256:0x0ab7, B:259:0x0abf, B:261:0x0b05, B:264:0x0b1f, B:267:0x0b3b, B:269:0x0b41, B:272:0x0b49, B:273:0x0b57, B:274:0x0b5a, B:130:0x042d, B:132:0x0445, B:134:0x044b, B:135:0x048c, B:139:0x04e2, B:141:0x04ec, B:143:0x0515, B:144:0x0549, B:146:0x0554, B:148:0x058e, B:149:0x05c5, B:108:0x0362, B:109:0x036b, B:153:0x062b, B:155:0x0649, B:157:0x064f, B:159:0x0658, B:161:0x0662, B:163:0x0672, B:167:0x06a3, B:169:0x06b0, B:171:0x06c1, B:173:0x06e3, B:175:0x06e7, B:177:0x06ed, B:179:0x06f6, B:181:0x06fc, B:183:0x0702, B:184:0x0748, B:186:0x0752, B:188:0x0758, B:189:0x0791, B:193:0x07d3, B:195:0x07dd, B:197:0x0808, B:198:0x0835, B:199:0x0866, B:165:0x0684, B:166:0x0693, B:202:0x0884, B:204:0x0899, B:206:0x08a3, B:208:0x08b3, B:212:0x08d5, B:214:0x08dc, B:216:0x08e4, B:218:0x08f5, B:220:0x0919, B:222:0x091d, B:224:0x0923, B:226:0x092b, B:228:0x0931, B:230:0x0937, B:231:0x0972, B:233:0x097a, B:235:0x0980, B:236:0x09b3, B:240:0x09ef, B:242:0x09f9, B:244:0x0a24, B:245:0x0a4b, B:246:0x0a77, B:209:0x08c2, B:281:0x0bfc, B:283:0x0c04, B:83:0x023d, B:85:0x0243, B:87:0x0249), top: B:295:0x000f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.util.List<com.app.orsozoxi.Beans.Bible> loadingSoom(int r45, int r46) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 3133
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.orsozoxi.Katamaras.loadingSoom(int, int):java.util.List");
    }

    /* JADX WARN: Removed duplicated region for block: B:115:0x03a5 A[Catch: Exception -> 0x0c3d, IOException -> 0x0c43, TryCatch #2 {IOException -> 0x0c43, Exception -> 0x0c3d, blocks: (B:3:0x0023, B:5:0x0040, B:7:0x0043, B:10:0x0059, B:13:0x006c, B:15:0x0074, B:17:0x007c, B:18:0x0081, B:20:0x0093, B:23:0x00b0, B:25:0x00b8, B:27:0x00c0, B:29:0x00c8, B:32:0x00d3, B:37:0x00eb, B:39:0x00f6, B:41:0x00fe, B:43:0x0106, B:45:0x010e, B:47:0x0116, B:49:0x011e, B:51:0x0126, B:53:0x012e, B:55:0x0136, B:57:0x013e, B:58:0x017d, B:60:0x019f, B:62:0x01a4, B:64:0x01bc, B:66:0x01c1, B:68:0x01c5, B:71:0x01de, B:73:0x01eb, B:77:0x0201, B:79:0x022e, B:81:0x0236, B:89:0x026e, B:90:0x028d, B:92:0x0293, B:94:0x02cd, B:97:0x02f3, B:99:0x02f9, B:101:0x0302, B:103:0x0338, B:105:0x0340, B:107:0x0369, B:113:0x039c, B:115:0x03a5, B:117:0x03ae, B:119:0x03cb, B:121:0x03cf, B:123:0x03d3, B:125:0x03da, B:127:0x03e0, B:129:0x03e6, B:249:0x0aa1, B:250:0x0aac, B:252:0x0ab2, B:256:0x0ac6, B:259:0x0ace, B:261:0x0b14, B:264:0x0b2e, B:267:0x0b4a, B:269:0x0b50, B:272:0x0b58, B:273:0x0b66, B:274:0x0b69, B:130:0x0441, B:132:0x0459, B:134:0x045f, B:135:0x04a0, B:139:0x04f6, B:141:0x0500, B:143:0x0529, B:144:0x055d, B:146:0x0568, B:148:0x05a2, B:149:0x05d9, B:108:0x0376, B:109:0x037f, B:153:0x0640, B:155:0x065e, B:157:0x0664, B:159:0x066d, B:161:0x0677, B:163:0x0687, B:167:0x06b8, B:169:0x06c5, B:171:0x06d6, B:173:0x06f8, B:175:0x06fc, B:177:0x0702, B:179:0x070b, B:181:0x0711, B:183:0x0717, B:184:0x075d, B:186:0x0767, B:188:0x076d, B:189:0x07a6, B:193:0x07e8, B:195:0x07f2, B:197:0x081d, B:198:0x084a, B:199:0x087b, B:165:0x0699, B:166:0x06a8, B:202:0x0898, B:204:0x08a8, B:206:0x08b2, B:208:0x08c2, B:212:0x08e4, B:214:0x08eb, B:216:0x08f3, B:218:0x0904, B:220:0x0928, B:222:0x092c, B:224:0x0932, B:226:0x093a, B:228:0x0940, B:230:0x0946, B:231:0x0981, B:233:0x0989, B:235:0x098f, B:236:0x09c2, B:240:0x09fe, B:242:0x0a08, B:244:0x0a33, B:245:0x0a5a, B:246:0x0a86, B:209:0x08d1, B:281:0x0c08, B:283:0x0c10, B:83:0x0251, B:85:0x0257, B:87:0x025d), top: B:295:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x04e2  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x04f6 A[Catch: Exception -> 0x0c3d, IOException -> 0x0c43, TryCatch #2 {IOException -> 0x0c43, Exception -> 0x0c3d, blocks: (B:3:0x0023, B:5:0x0040, B:7:0x0043, B:10:0x0059, B:13:0x006c, B:15:0x0074, B:17:0x007c, B:18:0x0081, B:20:0x0093, B:23:0x00b0, B:25:0x00b8, B:27:0x00c0, B:29:0x00c8, B:32:0x00d3, B:37:0x00eb, B:39:0x00f6, B:41:0x00fe, B:43:0x0106, B:45:0x010e, B:47:0x0116, B:49:0x011e, B:51:0x0126, B:53:0x012e, B:55:0x0136, B:57:0x013e, B:58:0x017d, B:60:0x019f, B:62:0x01a4, B:64:0x01bc, B:66:0x01c1, B:68:0x01c5, B:71:0x01de, B:73:0x01eb, B:77:0x0201, B:79:0x022e, B:81:0x0236, B:89:0x026e, B:90:0x028d, B:92:0x0293, B:94:0x02cd, B:97:0x02f3, B:99:0x02f9, B:101:0x0302, B:103:0x0338, B:105:0x0340, B:107:0x0369, B:113:0x039c, B:115:0x03a5, B:117:0x03ae, B:119:0x03cb, B:121:0x03cf, B:123:0x03d3, B:125:0x03da, B:127:0x03e0, B:129:0x03e6, B:249:0x0aa1, B:250:0x0aac, B:252:0x0ab2, B:256:0x0ac6, B:259:0x0ace, B:261:0x0b14, B:264:0x0b2e, B:267:0x0b4a, B:269:0x0b50, B:272:0x0b58, B:273:0x0b66, B:274:0x0b69, B:130:0x0441, B:132:0x0459, B:134:0x045f, B:135:0x04a0, B:139:0x04f6, B:141:0x0500, B:143:0x0529, B:144:0x055d, B:146:0x0568, B:148:0x05a2, B:149:0x05d9, B:108:0x0376, B:109:0x037f, B:153:0x0640, B:155:0x065e, B:157:0x0664, B:159:0x066d, B:161:0x0677, B:163:0x0687, B:167:0x06b8, B:169:0x06c5, B:171:0x06d6, B:173:0x06f8, B:175:0x06fc, B:177:0x0702, B:179:0x070b, B:181:0x0711, B:183:0x0717, B:184:0x075d, B:186:0x0767, B:188:0x076d, B:189:0x07a6, B:193:0x07e8, B:195:0x07f2, B:197:0x081d, B:198:0x084a, B:199:0x087b, B:165:0x0699, B:166:0x06a8, B:202:0x0898, B:204:0x08a8, B:206:0x08b2, B:208:0x08c2, B:212:0x08e4, B:214:0x08eb, B:216:0x08f3, B:218:0x0904, B:220:0x0928, B:222:0x092c, B:224:0x0932, B:226:0x093a, B:228:0x0940, B:230:0x0946, B:231:0x0981, B:233:0x0989, B:235:0x098f, B:236:0x09c2, B:240:0x09fe, B:242:0x0a08, B:244:0x0a33, B:245:0x0a5a, B:246:0x0a86, B:209:0x08d1, B:281:0x0c08, B:283:0x0c10, B:83:0x0251, B:85:0x0257, B:87:0x025d), top: B:295:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:149:0x05d9 A[Catch: Exception -> 0x0c3d, IOException -> 0x0c43, TryCatch #2 {IOException -> 0x0c43, Exception -> 0x0c3d, blocks: (B:3:0x0023, B:5:0x0040, B:7:0x0043, B:10:0x0059, B:13:0x006c, B:15:0x0074, B:17:0x007c, B:18:0x0081, B:20:0x0093, B:23:0x00b0, B:25:0x00b8, B:27:0x00c0, B:29:0x00c8, B:32:0x00d3, B:37:0x00eb, B:39:0x00f6, B:41:0x00fe, B:43:0x0106, B:45:0x010e, B:47:0x0116, B:49:0x011e, B:51:0x0126, B:53:0x012e, B:55:0x0136, B:57:0x013e, B:58:0x017d, B:60:0x019f, B:62:0x01a4, B:64:0x01bc, B:66:0x01c1, B:68:0x01c5, B:71:0x01de, B:73:0x01eb, B:77:0x0201, B:79:0x022e, B:81:0x0236, B:89:0x026e, B:90:0x028d, B:92:0x0293, B:94:0x02cd, B:97:0x02f3, B:99:0x02f9, B:101:0x0302, B:103:0x0338, B:105:0x0340, B:107:0x0369, B:113:0x039c, B:115:0x03a5, B:117:0x03ae, B:119:0x03cb, B:121:0x03cf, B:123:0x03d3, B:125:0x03da, B:127:0x03e0, B:129:0x03e6, B:249:0x0aa1, B:250:0x0aac, B:252:0x0ab2, B:256:0x0ac6, B:259:0x0ace, B:261:0x0b14, B:264:0x0b2e, B:267:0x0b4a, B:269:0x0b50, B:272:0x0b58, B:273:0x0b66, B:274:0x0b69, B:130:0x0441, B:132:0x0459, B:134:0x045f, B:135:0x04a0, B:139:0x04f6, B:141:0x0500, B:143:0x0529, B:144:0x055d, B:146:0x0568, B:148:0x05a2, B:149:0x05d9, B:108:0x0376, B:109:0x037f, B:153:0x0640, B:155:0x065e, B:157:0x0664, B:159:0x066d, B:161:0x0677, B:163:0x0687, B:167:0x06b8, B:169:0x06c5, B:171:0x06d6, B:173:0x06f8, B:175:0x06fc, B:177:0x0702, B:179:0x070b, B:181:0x0711, B:183:0x0717, B:184:0x075d, B:186:0x0767, B:188:0x076d, B:189:0x07a6, B:193:0x07e8, B:195:0x07f2, B:197:0x081d, B:198:0x084a, B:199:0x087b, B:165:0x0699, B:166:0x06a8, B:202:0x0898, B:204:0x08a8, B:206:0x08b2, B:208:0x08c2, B:212:0x08e4, B:214:0x08eb, B:216:0x08f3, B:218:0x0904, B:220:0x0928, B:222:0x092c, B:224:0x0932, B:226:0x093a, B:228:0x0940, B:230:0x0946, B:231:0x0981, B:233:0x0989, B:235:0x098f, B:236:0x09c2, B:240:0x09fe, B:242:0x0a08, B:244:0x0a33, B:245:0x0a5a, B:246:0x0a86, B:209:0x08d1, B:281:0x0c08, B:283:0x0c10, B:83:0x0251, B:85:0x0257, B:87:0x025d), top: B:295:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:252:0x0ab2 A[Catch: Exception -> 0x0c3d, IOException -> 0x0c43, TRY_LEAVE, TryCatch #2 {IOException -> 0x0c43, Exception -> 0x0c3d, blocks: (B:3:0x0023, B:5:0x0040, B:7:0x0043, B:10:0x0059, B:13:0x006c, B:15:0x0074, B:17:0x007c, B:18:0x0081, B:20:0x0093, B:23:0x00b0, B:25:0x00b8, B:27:0x00c0, B:29:0x00c8, B:32:0x00d3, B:37:0x00eb, B:39:0x00f6, B:41:0x00fe, B:43:0x0106, B:45:0x010e, B:47:0x0116, B:49:0x011e, B:51:0x0126, B:53:0x012e, B:55:0x0136, B:57:0x013e, B:58:0x017d, B:60:0x019f, B:62:0x01a4, B:64:0x01bc, B:66:0x01c1, B:68:0x01c5, B:71:0x01de, B:73:0x01eb, B:77:0x0201, B:79:0x022e, B:81:0x0236, B:89:0x026e, B:90:0x028d, B:92:0x0293, B:94:0x02cd, B:97:0x02f3, B:99:0x02f9, B:101:0x0302, B:103:0x0338, B:105:0x0340, B:107:0x0369, B:113:0x039c, B:115:0x03a5, B:117:0x03ae, B:119:0x03cb, B:121:0x03cf, B:123:0x03d3, B:125:0x03da, B:127:0x03e0, B:129:0x03e6, B:249:0x0aa1, B:250:0x0aac, B:252:0x0ab2, B:256:0x0ac6, B:259:0x0ace, B:261:0x0b14, B:264:0x0b2e, B:267:0x0b4a, B:269:0x0b50, B:272:0x0b58, B:273:0x0b66, B:274:0x0b69, B:130:0x0441, B:132:0x0459, B:134:0x045f, B:135:0x04a0, B:139:0x04f6, B:141:0x0500, B:143:0x0529, B:144:0x055d, B:146:0x0568, B:148:0x05a2, B:149:0x05d9, B:108:0x0376, B:109:0x037f, B:153:0x0640, B:155:0x065e, B:157:0x0664, B:159:0x066d, B:161:0x0677, B:163:0x0687, B:167:0x06b8, B:169:0x06c5, B:171:0x06d6, B:173:0x06f8, B:175:0x06fc, B:177:0x0702, B:179:0x070b, B:181:0x0711, B:183:0x0717, B:184:0x075d, B:186:0x0767, B:188:0x076d, B:189:0x07a6, B:193:0x07e8, B:195:0x07f2, B:197:0x081d, B:198:0x084a, B:199:0x087b, B:165:0x0699, B:166:0x06a8, B:202:0x0898, B:204:0x08a8, B:206:0x08b2, B:208:0x08c2, B:212:0x08e4, B:214:0x08eb, B:216:0x08f3, B:218:0x0904, B:220:0x0928, B:222:0x092c, B:224:0x0932, B:226:0x093a, B:228:0x0940, B:230:0x0946, B:231:0x0981, B:233:0x0989, B:235:0x098f, B:236:0x09c2, B:240:0x09fe, B:242:0x0a08, B:244:0x0a33, B:245:0x0a5a, B:246:0x0a86, B:209:0x08d1, B:281:0x0c08, B:283:0x0c10, B:83:0x0251, B:85:0x0257, B:87:0x025d), top: B:295:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0293 A[Catch: Exception -> 0x0c3d, IOException -> 0x0c43, TryCatch #2 {IOException -> 0x0c43, Exception -> 0x0c3d, blocks: (B:3:0x0023, B:5:0x0040, B:7:0x0043, B:10:0x0059, B:13:0x006c, B:15:0x0074, B:17:0x007c, B:18:0x0081, B:20:0x0093, B:23:0x00b0, B:25:0x00b8, B:27:0x00c0, B:29:0x00c8, B:32:0x00d3, B:37:0x00eb, B:39:0x00f6, B:41:0x00fe, B:43:0x0106, B:45:0x010e, B:47:0x0116, B:49:0x011e, B:51:0x0126, B:53:0x012e, B:55:0x0136, B:57:0x013e, B:58:0x017d, B:60:0x019f, B:62:0x01a4, B:64:0x01bc, B:66:0x01c1, B:68:0x01c5, B:71:0x01de, B:73:0x01eb, B:77:0x0201, B:79:0x022e, B:81:0x0236, B:89:0x026e, B:90:0x028d, B:92:0x0293, B:94:0x02cd, B:97:0x02f3, B:99:0x02f9, B:101:0x0302, B:103:0x0338, B:105:0x0340, B:107:0x0369, B:113:0x039c, B:115:0x03a5, B:117:0x03ae, B:119:0x03cb, B:121:0x03cf, B:123:0x03d3, B:125:0x03da, B:127:0x03e0, B:129:0x03e6, B:249:0x0aa1, B:250:0x0aac, B:252:0x0ab2, B:256:0x0ac6, B:259:0x0ace, B:261:0x0b14, B:264:0x0b2e, B:267:0x0b4a, B:269:0x0b50, B:272:0x0b58, B:273:0x0b66, B:274:0x0b69, B:130:0x0441, B:132:0x0459, B:134:0x045f, B:135:0x04a0, B:139:0x04f6, B:141:0x0500, B:143:0x0529, B:144:0x055d, B:146:0x0568, B:148:0x05a2, B:149:0x05d9, B:108:0x0376, B:109:0x037f, B:153:0x0640, B:155:0x065e, B:157:0x0664, B:159:0x066d, B:161:0x0677, B:163:0x0687, B:167:0x06b8, B:169:0x06c5, B:171:0x06d6, B:173:0x06f8, B:175:0x06fc, B:177:0x0702, B:179:0x070b, B:181:0x0711, B:183:0x0717, B:184:0x075d, B:186:0x0767, B:188:0x076d, B:189:0x07a6, B:193:0x07e8, B:195:0x07f2, B:197:0x081d, B:198:0x084a, B:199:0x087b, B:165:0x0699, B:166:0x06a8, B:202:0x0898, B:204:0x08a8, B:206:0x08b2, B:208:0x08c2, B:212:0x08e4, B:214:0x08eb, B:216:0x08f3, B:218:0x0904, B:220:0x0928, B:222:0x092c, B:224:0x0932, B:226:0x093a, B:228:0x0940, B:230:0x0946, B:231:0x0981, B:233:0x0989, B:235:0x098f, B:236:0x09c2, B:240:0x09fe, B:242:0x0a08, B:244:0x0a33, B:245:0x0a5a, B:246:0x0a86, B:209:0x08d1, B:281:0x0c08, B:283:0x0c10, B:83:0x0251, B:85:0x0257, B:87:0x025d), top: B:295:0x0023 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.util.List<com.app.orsozoxi.Beans.Bible> loading7maseen(int r45, int r46) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 3145
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.orsozoxi.Katamaras.loading7maseen(int, int):java.util.List");
    }

    private void loadmazmor(String str, int i, List<Bible> list) {
        String str2;
        String str3 = str.split("\\+")[0];
        try {
            if (i == 0) {
                str2 = new String(app.decodeFile2(this, "orsozoxi", "katamars/mzamerSoom.orso"));
            } else {
                str2 = new String(app.decodeFile2(this, "orsozoxi", "katamars/mzamer7maseen.orso"));
            }
            String[] strArrSplit = str2.split("\n");
            str.split("\\+");
            boolean z = false;
            for (int i2 = 0; i2 < strArrSplit.length; i2++) {
                if (strArrSplit[i2].toLowerCase().equals(str.toLowerCase())) {
                    Log.d("orso2", "line" + str);
                    Log.d("orso2", "xml" + strArrSplit[i2]);
                    z = true;
                } else if (z && strArrSplit[i2].contains("@") && !strArrSplit[i2].contains("@@")) {
                    list.add(new Bible("", typeofslah(str3)));
                    list.add(new Bible("", strArrSplit[i2].replace("@", "")));
                } else if (strArrSplit[i2].contains("@@") || strArrSplit[i2].equals("@")) {
                    z = false;
                } else if (z) {
                    list.add(new Bible("", strArrSplit[i2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.app.Activity
    protected Dialog onCreateDialog(int i) {
        if (i != 999) {
            return null;
        }
        System.out.println("monthhdialog" + this.monthh + "++" + this.yearr + "==" + this.dayy);
        return new DatePickerDialog(this, this.datePickerListener, this.yearr, this.monthh, this.dayy);
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

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() throws IllegalStateException {
        this.mp.stop();
        super.onDestroy();
    }

    public String typeofslah(String str) {
        if (str.equals("ma") || str.equals("Ma")) {
            return this.ma;
        }
        if (str.equals("ba") || str.equals("Ba")) {
            return this.ba;
        }
        if (str.equals("mb") || str.equals("Mb")) {
            return this.mb;
        }
        if (str.equals("bb") || str.equals("Bb")) {
            return this.bb;
        }
        if (str.equals("bo") || str.equals("Bo")) {
            return this.bo;
        }
        if (str.equals("ka") || str.equals("Ka")) {
            return this.ka;
        }
        if (str.equals("ba") || str.equals("Ba")) {
            return this.ba;
        }
        if (str.equals("ab") || str.equals("Ab")) {
            return this.ab;
        }
        if (str.equals(DateFormat.MINUTE) || str.equals(DateFormat.NUM_MONTH)) {
            return this.m;
        }
        if (str.equals("Mm") || str.equals("mm")) {
            return this.mm;
        }
        if (str.equals("b") || str.equals("B")) {
            return this.b;
        }
        return (str.equals("n") || str.equals("N")) ? this.n : "";
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        AppLeftSlider.setCurrentActivity(this);
        if (app.getCopticDate() != null) {
            CopticCalendar copticCalendar = new CopticCalendar();
            copticCalendar.set(11, copticCalendar.get(11));
            CopticCalendar copticCalendar2 = new CopticCalendar();
            copticCalendar2.set(5, app.getCopticDate().getDay());
            copticCalendar2.set(2, app.getCopticDate().getMonth() - 1);
            copticCalendar2.set(1, app.getCopticDate().getYear());
            Calendar calendar = Calendar.getInstance();
            calendar.set(11, calendar.get(11));
            if (calendar.get(11) < 23) {
                copticCalendar2.set(11, copticCalendar.get(11) + 1);
            } else {
                copticCalendar2.set(11, copticCalendar.get(11));
            }
            copticCalendar2.set(12, copticCalendar.get(12));
            copticCalendar2.set(13, copticCalendar.get(13));
            long timeInMillis = (copticCalendar2.getTimeInMillis() - copticCalendar.getTimeInMillis()) / 86400000;
            System.out.println("dayss" + timeInMillis);
            int iIntValue = Integer.valueOf(timeInMillis + "").intValue();
            Calendar calendar2 = Calendar.getInstance();
            System.out.println("dayss" + iIntValue);
            System.out.println("dayssthatDay" + copticCalendar2.getTime().toLocaleString());
            System.out.println("daysstoday" + copticCalendar.getTime().toLocaleString());
            try {
                calendar2.setTime(new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a", Locale.getDefault()).parse(copticCalendar2.getTime().toLocaleString()));
            } catch (ParseException unused) {
                calendar2.set(6, calendar2.get(6) + iIntValue);
                calendar2.set(11, copticCalendar.get(11));
                calendar2.set(12, copticCalendar.get(12));
                calendar2.set(13, copticCalendar.get(13));
            }
            System.out.println("dayssnewCalendar" + calendar2.getTime().toLocaleString());
            app.setCopticDate(null);
            this.legendList.clear();
            calculateEvents(copticCalendar2.get(5), copticCalendar2.get(2), copticCalendar2.get(1), calendar2.get(5), calendar2.get(2), calendar2.get(1), dayOfWeek(calendar2.get(7)));
            this.listViewFootballLegend.setSelection(0);
        }
        super.onResume();
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

    public void loadinmapp(String str, int i, int i2) throws Throwable {
        System.out.println("beshooooooooo" + str);
        this.levelsMap.clear();
        this.levels.clear();
        this.levelsMap = new HashMap<>();
        this.levels = new ArrayList<>();
        String[] strArrUpdatelistOld = updatelistOld(i, i2);
        for (String str2 : strArrUpdatelistOld) {
            this.levels.add(str2);
        }
        this.levelsMap.put("0", new ArrayList<>(this.levels));
        this.levels.clear();
    }

    private void loadYounanData(final String str, final String str2) {
        this.isSoomKeber = false;
        findViewById(R.id.fb_tafser).setVisibility(8);
        findViewById(R.id.progress).setVisibility(0);
        Observable.fromCallable(new Callable() { // from class: com.app.orsozoxi.-$$Lambda$Katamaras$UwsOHLv0VN-DUuwL1MO4lyQ99JA
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$loadYounanData$0$Katamaras(str, str2);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.orsozoxi.-$$Lambda$Katamaras$IEMnFu2tukoKX8MgfsOZZf2lgXo
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$loadYounanData$1$Katamaras((List) obj);
            }
        });
    }

    public /* synthetic */ List lambda$loadYounanData$0$Katamaras(String str, String str2) throws Exception {
        return loadingyunan(Integer.valueOf(str).intValue(), Integer.valueOf(str2).intValue());
    }

    public /* synthetic */ void lambda$loadYounanData$1$Katamaras(List list) throws Exception {
        this.legendList.addAll(list);
        findViewById(R.id.progress).setVisibility(8);
        this.bibleAdapter.notifyDataSetChanged();
        this.listViewFootballLegend.setSelection(0);
    }

    private void loadSoomData(final String str, final String str2) {
        if (Integer.valueOf(str).intValue() >= 3 && Integer.valueOf(str).intValue() <= 51) {
            findViewById(R.id.fb_tafser).setVisibility(8);
            this.soomKeberDayForTafser = Integer.valueOf(str).intValue() - 2;
            this.isSoomKeber = true;
        } else {
            findViewById(R.id.fb_tafser).setVisibility(8);
            this.isSoomKeber = false;
        }
        findViewById(R.id.progress).setVisibility(0);
        Observable.fromCallable(new Callable() { // from class: com.app.orsozoxi.-$$Lambda$Katamaras$ITEFYBormJtoZTZ9KFq92TQ8MoA
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$loadSoomData$2$Katamaras(str, str2);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.orsozoxi.-$$Lambda$Katamaras$kG6ll5YeT0SIfiIm-nYnL5OVzRo
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$loadSoomData$3$Katamaras((List) obj);
            }
        });
    }

    public /* synthetic */ List lambda$loadSoomData$2$Katamaras(String str, String str2) throws Exception {
        return loadingSoom(Integer.valueOf(str).intValue(), Integer.valueOf(str2).intValue());
    }

    public /* synthetic */ void lambda$loadSoomData$3$Katamaras(List list) throws Exception {
        this.legendList.addAll(list);
        findViewById(R.id.progress).setVisibility(8);
        this.bibleAdapter.notifyDataSetChanged();
        this.listViewFootballLegend.setSelection(0);
    }

    private void load7maseenData(final String str, final String str2) {
        this.isSoomKeber = false;
        findViewById(R.id.fb_tafser).setVisibility(8);
        findViewById(R.id.progress).setVisibility(0);
        Observable.fromCallable(new Callable() { // from class: com.app.orsozoxi.-$$Lambda$Katamaras$ymf-VZ_SfTJhvQEsUQLnfB_2veg
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$load7maseenData$4$Katamaras(str, str2);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.orsozoxi.-$$Lambda$Katamaras$W5IDc694DOakBoXE1ivDnNRNlnY
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$load7maseenData$5$Katamaras((List) obj);
            }
        });
    }

    public /* synthetic */ List lambda$load7maseenData$4$Katamaras(String str, String str2) throws Exception {
        return loading7maseen(Integer.valueOf(str).intValue(), Integer.valueOf(str2).intValue());
    }

    public /* synthetic */ void lambda$load7maseenData$5$Katamaras(List list) throws Exception {
        this.legendList.addAll(list);
        findViewById(R.id.progress).setVisibility(8);
        this.bibleAdapter.notifyDataSetChanged();
        this.listViewFootballLegend.setSelection(0);
    }

    public String[] updatelist(int i, int i2) throws SQLException {
        try {
            SQLiteDatabase.loadLibs(this);
            new UseDatabaseHandler(this);
            UseDatabaseHandler useDatabaseHandler = UseDatabaseHandler.getInstance(this);
            useDatabaseHandler.open(app.getSqKey(this));
            List<BibleDataBase> chapter = useDatabaseHandler.getChapter(i, i2);
            useDatabaseHandler.close();
            if (chapter == null) {
                return null;
            }
            String[] strArr = new String[chapter.size()];
            for (int i3 = 0; i3 < chapter.size(); i3++) {
                strArr[i3] = chapter.get(i3).getAyah();
            }
            return strArr;
        } catch (android.database.SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:253:0x078d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String[] updatelistOld(int r7, int r8) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1937
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.orsozoxi.Katamaras.updatelistOld(int, int):java.lang.String[]");
    }

    public String loadTextFile(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        while (true) {
            int i = inputStream.read(bArr);
            if (i > 0) {
                byteArrayOutputStream.write(bArr, 0, i);
            } else {
                return new String(byteArrayOutputStream.toByteArray(), InternalZipConstants.CHARSET_UTF8);
            }
        }
    }
}
