package com.app.orsozoxi;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.app.orsozoxi.Adapters.ExpandableListAdapter;
import com.app.orsozoxi.Adapters.daily_BibleListAdapter;
import com.app.orsozoxi.Beans.Bible;
import com.app.orsozoxi.Beans.WhiteBackgroundActivition;
import com.app.orsozoxi.Beans.highlightAyat;
import com.app.orsozoxi.Others.SavePrefs;
import com.app.orsozoxi.Others.checksim;
import com.github.clans.fab.FloatingActionMenu;
import com.hieupt.android.standalonescrollbar.StandaloneScrollBar;
import com.hieupt.android.standalonescrollbar.ViewExtensionsKt;
import com.hieupt.android.standalonescrollbar.view.NestedScrollView2;
import com.ibm.icu.impl.locale.LanguageTag;
import com.ibm.icu.text.DateFormat;
import com.navdrawer.SimpleSideDrawer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import it.orsozoxi.android.orsonotes.db.DbHelper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import net.lingala.zip4j.util.InternalZipConstants;
import org.slf4j.Marker;

/* loaded from: classes.dex */
public class Daily_bible_twoyears extends BaseActivity implements MediaPlayer.OnCompletionListener, View.OnClickListener {
    static final int DATE_DIALOG_ID = 999;
    private Spinner asksSpin;
    daily_BibleListAdapter bibleAdapter;
    String[] books;
    private Context ctx;
    Dictionary<Integer, String> d;
    Button dailysearch;
    private int dayy;
    int def;
    private GestureDetector gestureDetector;
    AdapterView.AdapterContextMenuInfo info;
    private Spinner introSpin;
    List<Bible> legendList;
    ArrayList<String> levels;
    HashMap<String, ArrayList<String>> levelsMap;
    private ListView listViewFootballLegend;
    private FloatingActionMenu mHomeFloatingActionMenu;
    private int monthh;
    MediaPlayer mp;
    Button playmusic;
    Button search;
    private int yearr;
    int book = 0;
    int chapter = 0;
    int pos = 0;
    private final int CONTEXT_MENU_ID = 1;
    private boolean sp1 = false;
    private boolean sp2 = false;
    private AdapterView.OnItemLongClickListener itemLongClickHandler = new AdapterView.OnItemLongClickListener() { // from class: com.app.orsozoxi.Daily_bible_twoyears.6
        @Override // android.widget.AdapterView.OnItemLongClickListener
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
            Daily_bible_twoyears.this.pos = i;
            Daily_bible_twoyears.this.highlight();
            return true;
        }
    };
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() { // from class: com.app.orsozoxi.Daily_bible_twoyears.7
        @Override // android.app.DatePickerDialog.OnDateSetListener
        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
            Daily_bible_twoyears.this.yearr = i;
            Daily_bible_twoyears.this.monthh = i2;
            Daily_bible_twoyears.this.dayy = i3;
            Daily_bible_twoyears.this.legendList.clear();
            Daily_bible_twoyears.this.bibleAdapter.notifyDataSetChanged();
            System.out.println("monthh" + Daily_bible_twoyears.this.monthh);
            Daily_bible_twoyears daily_bible_twoyears = Daily_bible_twoyears.this;
            String str = Daily_bible_twoyears.this.dayy + "";
            Daily_bible_twoyears daily_bible_twoyears2 = Daily_bible_twoyears.this;
            daily_bible_twoyears.updateData(str, daily_bible_twoyears2.getmonth(daily_bible_twoyears2.monthh + 1), "" + Daily_bible_twoyears.this.yearr);
        }
    };
    String bookPath = "";
    int bookPathChapters = 0;

    public String choosebook(int i) {
        return i == 0 ? "arabbible0" : i == 1 ? "arabbible1" : i == 2 ? "arabbible2" : i == 3 ? "arabbible3" : i == 4 ? "arabbible4" : i == 5 ? "arabbible5" : i == 6 ? "arabbible6" : i == 7 ? "arabbible7" : i == 8 ? "arabbible8" : i == 9 ? "arabbible9" : i == 10 ? "arabbible10" : i == 11 ? "arabbible11" : i == 12 ? "arabbible12" : i == 13 ? "arabbible13" : i == 14 ? "arabbible14" : i == 15 ? "arabbible15" : i == 16 ? "arabbible16" : i == 17 ? "arabbible17" : i == 18 ? "arabbible18" : i == 19 ? "arabbible19" : i == 20 ? "arabbible20" : i == 21 ? "arabbible21" : i == 22 ? "arabbible22" : i == 23 ? "arabbible23" : i == 24 ? "arabbible24" : i == 25 ? "arabbible25" : i == 26 ? "arabbible26" : i == 27 ? "arabbible27" : i == 28 ? "arabbible28" : i == 29 ? "arabbible29" : i == 30 ? "arabbible30" : i == 31 ? "arabbible31" : i == 32 ? "arabbible32" : i == 33 ? "arabbible33" : i == 34 ? "arabbible34" : i == 35 ? "arabbible35" : i == 36 ? "arabbible36" : i == 37 ? "arabbible37" : i == 38 ? "arabbible38" : i == 39 ? "arabbible39" : i == 40 ? "arabbible40" : i == 41 ? "arabbible41" : i == 42 ? "arabbible42" : i == 43 ? "arabbible43" : i == 44 ? "arabbible44" : i == 45 ? "arabbible45" : i == 46 ? "arabbible46" : i == 47 ? "arabbible47" : i == 48 ? "arabbible48" : i == 49 ? "arabbible49" : i == 50 ? "arabbible50" : i == 51 ? "arabbible51" : i == 52 ? "arabbible52" : i == 53 ? "arabbible53" : i == 54 ? "arabbible54" : i == 55 ? "arabbible55" : i == 56 ? "arabbible56" : i == 57 ? "arabbible57" : i == 58 ? "arabbible58" : i == 59 ? "arabbible59" : i == 60 ? "arabbible60" : i == 61 ? "arabbible61" : i == 62 ? "arabbible62" : i == 63 ? "arabbible63" : i == 64 ? "arabbible64" : i == 65 ? "arabbible65" : i == 66 ? "arabbible66" : i == 67 ? "arabbible67" : i == 68 ? "arabbible68" : i == 69 ? "arabbible69" : i == 70 ? "arabbible70" : i == 71 ? "arabbible71" : i == 72 ? "arabbible72" : "";
    }

    public String getmonth(int i) {
        return i == 1 ? "jan" : i == 2 ? "feb" : i == 3 ? "mar" : i == 4 ? "apr" : i == 5 ? "may" : i == 6 ? "jun" : i == 7 ? "jul" : i == 8 ? "aug" : i == 9 ? "sep" : i == 10 ? "oct" : i == 11 ? "nov" : i == 12 ? "dec" : "";
    }

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) throws IOException {
        super.onCreate(bundle);
        setContentView(R.layout.activity_daily_bible);
        this.ctx = this;
        ((TextView) findViewById(R.id.tv_header_title)).setText(R.string.t_bible_two_years);
        NestedScrollView2 nestedScrollView2 = (NestedScrollView2) findViewById(R.id.v_nested);
        ViewExtensionsKt.attachTo((StandaloneScrollBar) findViewById(R.id.scrollbar), nestedScrollView2);
        WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs((Activity) this, (Class<?>) WhiteBackgroundActivition.class).load();
        if (whiteBackgroundActivition == null || !whiteBackgroundActivition.isWhiteBackground()) {
            nestedScrollView2.setBackgroundResource(R.drawable.background);
        }
        this.introSpin = (Spinner) findViewById(R.id.sp_intro);
        this.asksSpin = (Spinner) findViewById(R.id.sp_asks);
        initFloating();
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
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_item_white, this.books);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.introSpin.setAdapter((SpinnerAdapter) arrayAdapter);
            this.introSpin.setPrompt(getString(R.string.bible_intros));
            this.asksSpin.setAdapter((SpinnerAdapter) arrayAdapter);
            this.asksSpin.setPrompt(getString(R.string.bible_questions));
            this.asksSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.orsozoxi.Daily_bible_twoyears.1
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j) throws IOException {
                    try {
                        InputStream inputStreamOpen2 = Daily_bible_twoyears.this.getAssets().open("daily/asks.xml");
                        byte[] bArr2 = new byte[inputStreamOpen2.available()];
                        inputStreamOpen2.read(bArr2);
                        String str = new String(bArr2);
                        int i3 = i2 + 1;
                        String str2 = "A+" + i3;
                        if (i3 >= 6 && i3 < 47) {
                            Toast.makeText(Daily_bible_twoyears.this.getApplicationContext(), R.string.no_questions, 1).show();
                            return;
                        }
                        Log.d("orso", "asks spin 1");
                        String str3 = "";
                        boolean z = false;
                        for (String str4 : str.split("\n")) {
                            String strTrim = str4.trim();
                            if (str2.replace(DateFormat.NUM_MONTH, "A").toLowerCase().equals(strTrim.toLowerCase())) {
                                z = true;
                            } else if (z) {
                                str3 = str3 + strTrim + "\n";
                            }
                            if (strTrim.contains("+++") && z) {
                                break;
                            }
                        }
                        if (!Daily_bible_twoyears.this.sp1) {
                            Daily_bible_twoyears.this.sp1 = true;
                            return;
                        }
                        Intent intent = new Intent(Daily_bible_twoyears.this, (Class<?>) TafserActivity.class);
                        intent.putExtra("tafser", str3.replace("+++", ""));
                        intent.putStringArrayListExtra("tafserList", null);
                        intent.putExtra("title", "أسئله على " + Daily_bible_twoyears.this.books[i2]);
                        Daily_bible_twoyears.this.startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            this.introSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.orsozoxi.Daily_bible_twoyears.2
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j) throws IOException {
                    try {
                        InputStream inputStreamOpen2 = Daily_bible_twoyears.this.getAssets().open("daily/intro.xml");
                        byte[] bArr2 = new byte[inputStreamOpen2.available()];
                        inputStreamOpen2.read(bArr2);
                        String[] strArrSplit2 = new String(bArr2).split("\n");
                        String str = "M+" + (i2 + 1);
                        String str2 = "";
                        boolean z = false;
                        for (String str3 : strArrSplit2) {
                            String strTrim = str3.trim();
                            if (str.toLowerCase().equals(strTrim.toLowerCase())) {
                                z = true;
                            } else if (z) {
                                str2 = str2 + strTrim + "\n";
                            }
                            if (strTrim.contains("+++") && z) {
                                break;
                            }
                        }
                        if (!Daily_bible_twoyears.this.sp2) {
                            Daily_bible_twoyears.this.sp2 = true;
                            return;
                        }
                        Intent intent = new Intent(Daily_bible_twoyears.this, (Class<?>) TafserActivity.class);
                        intent.putExtra("tafser", str2.replace("+++", ""));
                        intent.putStringArrayListExtra("tafserList", null);
                        intent.putExtra("title", "مقدمة " + Daily_bible_twoyears.this.books[i2]);
                        Daily_bible_twoyears.this.startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException unused) {
        }
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Daily_bible_twoyears.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        this.mp = MediaPlayer.create(getApplicationContext(), R.raw.music);
        Button button2 = (Button) findViewById(R.id.btn_play_music);
        this.playmusic = button2;
        button2.setVisibility(0);
        this.playmusic.setBackgroundResource(R.drawable.btn_play);
        this.playmusic.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Daily_bible_twoyears.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) throws IllegalStateException {
                if (!Daily_bible_twoyears.this.mp.isPlaying()) {
                    Daily_bible_twoyears daily_bible_twoyears = Daily_bible_twoyears.this;
                    MediaPlayer mediaPlayerCreate = MediaPlayer.create(daily_bible_twoyears.getApplicationContext(), R.raw.music);
                    daily_bible_twoyears.mp = mediaPlayerCreate;
                    daily_bible_twoyears.mp = mediaPlayerCreate;
                    Daily_bible_twoyears.this.playSong();
                    Daily_bible_twoyears.this.playmusic.setBackgroundResource(R.drawable.btn_pause);
                    return;
                }
                Daily_bible_twoyears.this.playmusic.setBackgroundResource(R.drawable.btn_play);
                Daily_bible_twoyears.this.mp.stop();
            }
        });
        getWindow().addFlags(128);
        new checksim();
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
            InputStream inputStreamOpen2 = getAssets().open("biblenames.xml");
            byte[] bArr2 = new byte[inputStreamOpen2.available()];
            inputStreamOpen2.read(bArr2);
            String[] strArrSplit2 = new String(bArr2).split("\n");
            this.books = new String[73];
            this.d = new Hashtable();
            for (int i2 = 0; i2 < strArrSplit2.length; i2++) {
                if (i2 < 73) {
                    System.out.println("beshooooooooooooooooooooooooo" + strArrSplit2[i2]);
                    this.d.put(Integer.valueOf(i2), strArrSplit2[i2]);
                    this.books[i2] = strArrSplit2[i2];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ListView listView = (ListView) findViewById(R.id.liss1);
        this.listViewFootballLegend = listView;
        listView.setBackgroundColor(0);
        this.listViewFootballLegend.setCacheColorHint(0);
        this.listViewFootballLegend.setDivider(null);
        daily_BibleListAdapter daily_biblelistadapter = new daily_BibleListAdapter(getApplicationContext(), R.layout.row_agpya_item, this.legendList, this.books, null, -1);
        this.bibleAdapter = daily_biblelistadapter;
        this.listViewFootballLegend.setAdapter((ListAdapter) daily_biblelistadapter);
        this.listViewFootballLegend.setOnItemLongClickListener(this.itemLongClickHandler);
        Calendar calendar = Calendar.getInstance();
        String[] strArrSplit3 = new java.sql.Date(System.currentTimeMillis()).toGMTString().split(" ");
        int i3 = calendar.get(5);
        int i4 = calendar.get(2);
        int i5 = calendar.get(1);
        String str = strArrSplit3[1];
        updateData(i3 + "", getmonth(i4 + 1), i5 + "");
        this.yearr = calendar.get(1);
        this.monthh = calendar.get(2);
        this.dayy = calendar.get(5);
        this.dailysearch.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Daily_bible_twoyears.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Daily_bible_twoyears.this.showDialog(999);
            }
        });
    }

    public void highlight() {
        ArrayList<String> arrayList;
        String nick = this.legendList.get((int) this.listViewFootballLegend.getAdapter().getItemId(this.pos)).getNick();
        highlightAyat highlightayat = (highlightAyat) new SavePrefs((Activity) this, (Class<?>) highlightAyat.class).load();
        if (highlightayat != null) {
            arrayList = highlightayat.getAyat();
        } else {
            arrayList = new ArrayList<>();
        }
        boolean z = false;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(nick)) {
                arrayList.remove(i);
                z = true;
            }
        }
        if (!z) {
            arrayList.add(nick);
        }
        if (((highlightAyat) new SavePrefs((Activity) this, (Class<?>) highlightAyat.class).load()) != null) {
            SavePrefs savePrefs = new SavePrefs((Activity) this, (Class<?>) highlightAyat.class);
            highlightAyat highlightayat2 = new highlightAyat();
            highlightayat2.setAyat(arrayList);
            savePrefs.save(highlightayat2);
        } else {
            SavePrefs savePrefs2 = new SavePrefs((Activity) this, (Class<?>) highlightAyat.class);
            highlightAyat highlightayat3 = new highlightAyat();
            highlightayat3.setAyat(arrayList);
            savePrefs2.save(highlightayat3);
        }
        this.bibleAdapter.notifyDataSetChanged();
    }

    public List<Bible> loading(int i, String str, int i2) throws Throwable {
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        String str10;
        String str11;
        String str12;
        String str13;
        String[] strArr;
        boolean z;
        String str14;
        String str15;
        int i3;
        CharSequence charSequence;
        String str16;
        String str17;
        String str18;
        String str19;
        String str20;
        String str21;
        String str22;
        String str23;
        String str24;
        int i4;
        int i5;
        String str25;
        String str26;
        String str27;
        String str28;
        String str29;
        int i6;
        String str30;
        String str31;
        String str32;
        String str33;
        String str34;
        int i7;
        boolean z2;
        boolean z3;
        String str35;
        String str36;
        String str37;
        int i8;
        int i9;
        int i10;
        int i11;
        String str38;
        String str39;
        String str40;
        String str41;
        int i12;
        int i13;
        Iterator<String> it2;
        int i14;
        String str42;
        String[] strArr2;
        boolean z4;
        int i15;
        int i16;
        String str43;
        boolean z5;
        int i17;
        int i18;
        boolean z6;
        String str44;
        String str45;
        int i19;
        boolean z7;
        String str46;
        String str47;
        Iterator<Map.Entry<String, ArrayList<String>>> it3;
        Daily_bible_twoyears daily_bible_twoyears = this;
        int i20 = i;
        ArrayList arrayList = new ArrayList();
        int i21 = i2 % 2;
        if (i21 != 0 && str.equals("jan")) {
            str2 = "daily/jan1.xml";
        } else if (i21 != 0 && str.equals("feb")) {
            str2 = "daily/feb1.xml";
        } else if (i21 != 0 && str.equals("mar")) {
            str2 = "daily/mar1.xml";
        } else if (i21 != 0 && str.equals("apr")) {
            str2 = "daily/apr1.xml";
        } else if (i21 != 0 && str.equals("may")) {
            str2 = "daily/may1.xml";
        } else if (i21 != 0 && str.equals("jun")) {
            str2 = "daily/june1.xml";
        } else if (i21 != 0 && str.equals("jul")) {
            str2 = "daily/july1.xml";
        } else if (i21 != 0 && str.equals("aug")) {
            str2 = "daily/aug1.xml";
        } else if (i21 != 0 && str.equals("sep")) {
            str2 = "daily/sep1.xml";
        } else if (i21 != 0 && str.equals("oct")) {
            str2 = "daily/oct1.xml";
        } else if (i21 == 0 || !str.equals("nov")) {
            str2 = (i21 == 0 || !str.equals("dec")) ? "" : "daily/dec1.xml";
        } else {
            str2 = "daily/nov1.xml";
        }
        if (i21 == 0 && str.equals("jan")) {
            str2 = "daily/jan2.xml";
        } else if (i21 == 0 && str.equals("feb")) {
            str2 = "daily/feb2.xml";
        } else if (i21 == 0 && str.equals("mar")) {
            str2 = "daily/mar2.xml";
        } else if (i21 == 0 && str.equals("apr")) {
            str2 = "daily/apr2.xml";
        } else if (i21 == 0 && str.equals("may")) {
            str2 = "daily/may2.xml";
        } else if (i21 == 0 && str.equals("jun")) {
            str2 = "daily/june2.xml";
        } else if (i21 == 0 && str.equals("jul")) {
            str2 = "daily/july2.xml";
        } else if (i21 == 0 && str.equals("aug")) {
            str2 = "daily/aug2.xml";
        } else if (i21 == 0 && str.equals("sep")) {
            str2 = "daily/sep2.xml";
        } else if (i21 == 0 && str.equals("oct")) {
            str2 = "daily/oct2.xml";
        } else if (i21 == 0 && str.equals("nov")) {
            str2 = "daily/nov2.xml";
        } else if (i21 == 0 && str.equals("dec")) {
            str2 = "daily/dec2.xml";
        }
        if (!str2.equals("")) {
            try {
                InputStream inputStreamOpen = getAssets().open(str2);
                byte[] bArr = new byte[inputStreamOpen.available()];
                inputStreamOpen.read(bArr);
                String[] strArrSplit = new String(bArr).split("\n");
                System.out.println("beshooooooooooooooooooooooooolrnght" + strArrSplit.length);
                int i22 = 0;
                boolean z8 = false;
                int i23 = 0;
                int i24 = 1;
                boolean z9 = false;
                boolean z10 = false;
                while (true) {
                    int length = strArrSplit.length;
                    str3 = ":";
                    str4 = "naas";
                    str5 = "dis:";
                    str6 = "to";
                    str7 = "from:";
                    str8 = "key1";
                    str9 = "key";
                    boolean z11 = z10;
                    boolean z12 = z9;
                    str10 = "الفترة الصباحية";
                    str11 = Marker.ANY_NON_NULL_MARKER;
                    str12 = " ";
                    str13 = "+++";
                    if (i22 >= length) {
                        break;
                    }
                    String strTrim = strArrSplit[i22].replaceAll(" ", "").trim();
                    if (strTrim.equals("")) {
                        str43 = str;
                        strArr2 = strArrSplit;
                        i16 = i22;
                        z5 = z8;
                        i17 = i23;
                        i18 = i24;
                    } else {
                        strArr2 = strArrSplit;
                        if ((strTrim.contains(DateFormat.NUM_MONTH) || strTrim.contains(DateFormat.MINUTE)) && i20 == i23) {
                            if (z8) {
                                z4 = true;
                            } else {
                                arrayList.add(new Bible("", "الفترة الصباحية"));
                                z4 = true;
                                z8 = true;
                            }
                            daily_bible_twoyears.addIntroAndAsks(strTrim, arrayList, z4);
                            i15 = i20;
                            i16 = i22;
                            z10 = z11;
                            z9 = z12;
                        } else {
                            if (!strTrim.contains(Marker.ANY_NON_NULL_MARKER) && !strTrim.contains("@b")) {
                                i15 = i20;
                                i16 = i22;
                                i23 = i24;
                                z9 = z12;
                                z10 = true;
                                str43 = str;
                                i24++;
                            } else if (strTrim.contains("@b")) {
                                i15 = i20;
                                i16 = i22;
                                z10 = z11;
                                z9 = true;
                            } else if (strTrim.contains(Marker.ANY_NON_NULL_MARKER) && !strTrim.contains("+++")) {
                                String[] strArrSplit2 = strTrim.split("\\+");
                                String strReplaceAll = strArrSplit2[0].replaceAll(" ", "");
                                String strReplaceAll2 = strArrSplit2[1].replaceAll(" ", "");
                                if (strArrSplit2.length == 4) {
                                    str45 = strArrSplit2[2];
                                    PrintStream printStream = System.out;
                                    z6 = z8;
                                    StringBuilder sb = new StringBuilder();
                                    i18 = i24;
                                    sb.append("sizeee");
                                    sb.append(strArrSplit2.length);
                                    printStream.println(sb.toString());
                                    str44 = strArrSplit2[3];
                                } else {
                                    z6 = z8;
                                    i18 = i24;
                                    if (strArrSplit2.length == 3) {
                                        str45 = strArrSplit2[2];
                                        System.out.println("sizeee" + strArrSplit2.length);
                                        str44 = "";
                                    } else {
                                        str44 = "";
                                        str45 = str44;
                                    }
                                }
                                PrintStream printStream2 = System.out;
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("yarabbb");
                                sb2.append(i20);
                                sb2.append("yarab");
                                sb2.append(str);
                                i16 = i22;
                                sb2.append("dd");
                                sb2.append(i23);
                                printStream2.println(sb2.toString());
                                if (i20 == i23) {
                                    if (z12) {
                                        arrayList.add(new Bible("", "الفترة المسائية"));
                                        z7 = true;
                                        z12 = false;
                                    } else {
                                        z7 = z6;
                                    }
                                    if (z11 && !z7) {
                                        arrayList.add(new Bible("", "الفترة الصباحية"));
                                        z7 = true;
                                        z11 = false;
                                    }
                                    int i25 = Integer.parseInt(strReplaceAll) - 1;
                                    int i26 = Integer.parseInt(strReplaceAll2) - 1;
                                    String str48 = i25 + Marker.ANY_NON_NULL_MARKER + i26;
                                    PrintStream printStream3 = System.out;
                                    StringBuilder sb3 = new StringBuilder();
                                    boolean z13 = z7;
                                    sb3.append(str9);
                                    sb3.append(str48);
                                    printStream3.println(sb3.toString());
                                    if (!str45.equals("") && !str44.equals("")) {
                                        int i27 = Integer.parseInt(str45) - 1;
                                        int i28 = Integer.parseInt(str44) - 1;
                                        PrintStream printStream4 = System.out;
                                        StringBuilder sb4 = new StringBuilder();
                                        i19 = i23;
                                        sb4.append(str8);
                                        sb4.append(str48);
                                        sb4.append(str7);
                                        sb4.append(i27);
                                        sb4.append(str6);
                                        sb4.append(i28);
                                        printStream4.println(sb4.toString());
                                    } else {
                                        i19 = i23;
                                        if (!str45.equals("") && str44.equals("")) {
                                            int i29 = Integer.parseInt(str45) - 1;
                                            System.out.println(str8 + str48 + str7 + i29 + str6 + 0);
                                        } else {
                                            System.out.println("key2" + str48);
                                        }
                                    }
                                    new ArrayList();
                                    daily_bible_twoyears.loadinmapp(daily_bible_twoyears.choosebook(i25), i25, i26);
                                    Iterator<Map.Entry<String, ArrayList<String>>> it4 = daily_bible_twoyears.levelsMap.entrySet().iterator();
                                    while (it4.hasNext()) {
                                        Map.Entry<String, ArrayList<String>> next = it4.next();
                                        String key = next.getKey();
                                        PrintStream printStream5 = System.out;
                                        StringBuilder sb5 = new StringBuilder();
                                        String str49 = str5;
                                        sb5.append(str49);
                                        sb5.append(str48);
                                        sb5.append(str49);
                                        sb5.append(key);
                                        printStream5.println(sb5.toString());
                                        if (key.equals("0")) {
                                            System.out.println("found" + str48);
                                            next.getValue();
                                            int i30 = Integer.parseInt(strReplaceAll2);
                                            if (!str45.equals("") && !str44.equals("")) {
                                                str47 = str4;
                                                System.out.println(str47);
                                                StringBuilder sb6 = new StringBuilder();
                                                sb6.append(daily_bible_twoyears.books[i25]);
                                                sb6.append(str44);
                                                sb6.append(LanguageTag.SEP);
                                                sb6.append(str45);
                                                str46 = str3;
                                                sb6.append(str46);
                                                sb6.append(i30);
                                                arrayList.add(new Bible("", sb6.toString()));
                                            } else {
                                                str46 = str3;
                                                str47 = str4;
                                                if (!str45.equals("") && str44.equals("")) {
                                                    System.out.println(str47);
                                                    StringBuilder sb7 = new StringBuilder();
                                                    it3 = it4;
                                                    sb7.append(daily_bible_twoyears.books[i25]);
                                                    sb7.append(str45);
                                                    sb7.append(str46);
                                                    sb7.append(i30);
                                                    arrayList.add(new Bible("", sb7.toString()));
                                                } else {
                                                    it3 = it4;
                                                    System.out.println("kamel");
                                                    arrayList.add(new Bible("", daily_bible_twoyears.books[i25] + i30));
                                                }
                                                str4 = str47;
                                                str5 = str49;
                                                it4 = it3;
                                                str3 = str46;
                                            }
                                        } else {
                                            str46 = str3;
                                            str47 = str4;
                                        }
                                        it3 = it4;
                                        str4 = str47;
                                        str5 = str49;
                                        it4 = it3;
                                        str3 = str46;
                                    }
                                    z10 = z11;
                                    z9 = z12;
                                    z8 = z13;
                                } else {
                                    i19 = i23;
                                    z10 = z11;
                                    z9 = z12;
                                    z8 = z6;
                                }
                                i15 = i;
                                str43 = str;
                                i23 = i19;
                                i24 = i18;
                            } else {
                                str43 = str;
                                i16 = i22;
                                z5 = z8;
                                i17 = i23;
                                i18 = i24;
                                if (!strTrim.equals("+++")) {
                                    if (strTrim.contains("+++")) {
                                    }
                                }
                                PrintStream printStream6 = System.out;
                                StringBuilder sb8 = new StringBuilder();
                                sb8.append("yarabbb");
                                i15 = i;
                                sb8.append(i15);
                                sb8.append("yarab");
                                sb8.append(str43);
                                sb8.append("dd");
                                sb8.append(i17);
                                printStream6.println(sb8.toString());
                                System.out.println(DbHelper.KEY_ATTACHMENT_SIZE + arrayList.size());
                                z8 = z5;
                                i24 = i18;
                                i23 = 0;
                                z9 = false;
                                z10 = false;
                            }
                            i22 = i16 + 1;
                            i20 = i15;
                            strArrSplit = strArr2;
                        }
                        str43 = str;
                        i22 = i16 + 1;
                        i20 = i15;
                        strArrSplit = strArr2;
                    }
                    i15 = i;
                    i23 = i17;
                    z10 = z11;
                    z9 = z12;
                    z8 = z5;
                    i24 = i18;
                    i22 = i16 + 1;
                    i20 = i15;
                    strArrSplit = strArr2;
                }
                int i31 = i20;
                CharSequence charSequence2 = "@b";
                String str50 = "sizeee";
                arrayList.add(new Bible("", "+++"));
                String str51 = "yarab";
                String str52 = "yarabbb";
                String str53 = "dd";
                String[] strArr3 = strArrSplit;
                int i32 = 0;
                int i33 = 0;
                int i34 = 1;
                boolean z14 = false;
                boolean z15 = false;
                boolean z16 = false;
                while (i33 < strArr3.length) {
                    String strTrim2 = strArr3[i33].replaceAll(str12, "").trim();
                    if (strTrim2.equals("")) {
                        strArr = strArr3;
                        str14 = str10;
                        str15 = str13;
                        i3 = i34;
                        charSequence = charSequence2;
                        str16 = str50;
                        str17 = str12;
                        str18 = str11;
                        str19 = str6;
                        str20 = str7;
                        str21 = str8;
                        str27 = str9;
                        str28 = str53;
                        str29 = str52;
                        i6 = i32;
                        i5 = i33;
                        str25 = str3;
                        str26 = str4;
                    } else {
                        strArr = strArr3;
                        if ((strTrim2.contains(DateFormat.NUM_MONTH) || strTrim2.contains(DateFormat.MINUTE)) && i31 == i32) {
                            if (z16) {
                                z = false;
                            } else {
                                arrayList.add(new Bible("", str10));
                                z = false;
                                z16 = true;
                            }
                            daily_bible_twoyears.addIntroAndAsks(strTrim2, arrayList, z);
                            str14 = str10;
                            str15 = str13;
                            i3 = i34;
                            charSequence = charSequence2;
                            str16 = str50;
                            str17 = str12;
                            str18 = str11;
                            str19 = str6;
                            str20 = str7;
                            str21 = str8;
                            str22 = str53;
                            str23 = str51;
                            str24 = str52;
                        } else if (!strTrim2.contains(str11) && !strTrim2.contains(charSequence2)) {
                            i3 = i34 + 1;
                            str14 = str10;
                            str15 = str13;
                            i32 = i34;
                            charSequence = charSequence2;
                            str16 = str50;
                            str17 = str12;
                            str18 = str11;
                            str19 = str6;
                            str20 = str7;
                            str21 = str8;
                            str22 = str53;
                            str23 = str51;
                            str24 = str52;
                            z15 = true;
                        } else if (strTrim2.contains(charSequence2)) {
                            str14 = str10;
                            str15 = str13;
                            i3 = i34;
                            charSequence = charSequence2;
                            str16 = str50;
                            str17 = str12;
                            str18 = str11;
                            str19 = str6;
                            str20 = str7;
                            str21 = str8;
                            str22 = str53;
                            str23 = str51;
                            str24 = str52;
                            z14 = true;
                        } else {
                            if (strTrim2.contains(str11) && !strTrim2.contains(str13)) {
                                String[] strArrSplit3 = strTrim2.split("\\+");
                                int i35 = i32;
                                String strReplaceAll3 = strArrSplit3[0].replaceAll(str12, "");
                                i3 = i34;
                                String strReplaceAll4 = strArrSplit3[1].replaceAll(str12, "");
                                charSequence = charSequence2;
                                if (strArrSplit3.length == 4) {
                                    String str54 = strArrSplit3[2];
                                    PrintStream printStream7 = System.out;
                                    StringBuilder sb9 = new StringBuilder();
                                    sb9.append(str50);
                                    str17 = str12;
                                    sb9.append(strArrSplit3.length);
                                    printStream7.println(sb9.toString());
                                    str30 = strArrSplit3[3];
                                    str31 = str54;
                                } else {
                                    str17 = str12;
                                    str30 = "";
                                    str31 = str30;
                                }
                                String str55 = str31;
                                if (strArrSplit3.length == 3) {
                                    String str56 = strArrSplit3[2];
                                    System.out.println(str50 + strArrSplit3.length);
                                    str32 = str56;
                                } else {
                                    str32 = str55;
                                }
                                PrintStream printStream8 = System.out;
                                StringBuilder sb10 = new StringBuilder();
                                str16 = str50;
                                String str57 = str52;
                                sb10.append(str57);
                                sb10.append(i31);
                                i5 = i33;
                                String str58 = str51;
                                sb10.append(str58);
                                sb10.append(str);
                                String str59 = str53;
                                sb10.append(str59);
                                sb10.append(i35);
                                printStream8.println(sb10.toString());
                                if (i31 == i35) {
                                    if (z14) {
                                        arrayList.add(new Bible("", "الفترة المسائية"));
                                        z14 = false;
                                        z16 = true;
                                    }
                                    if (!z15 || z16) {
                                        z3 = z16;
                                    } else {
                                        arrayList.add(new Bible("", str10));
                                        z3 = true;
                                        z15 = false;
                                    }
                                    int i36 = Integer.parseInt(strReplaceAll3) - 1;
                                    boolean z17 = z3;
                                    int i37 = Integer.parseInt(strReplaceAll4) - 1;
                                    String str60 = i36 + str11 + i37;
                                    str14 = str10;
                                    PrintStream printStream9 = System.out;
                                    str34 = str59;
                                    StringBuilder sb11 = new StringBuilder();
                                    str18 = str11;
                                    String str61 = str9;
                                    sb11.append(str61);
                                    sb11.append(str60);
                                    printStream9.println(sb11.toString());
                                    if (!str32.equals("") && !str30.equals("")) {
                                        int i38 = Integer.parseInt(str32) - 1;
                                        i7 = i35;
                                        i9 = Integer.parseInt(str30) - 1;
                                        PrintStream printStream10 = System.out;
                                        StringBuilder sb12 = new StringBuilder();
                                        str33 = str13;
                                        str36 = str8;
                                        sb12.append(str36);
                                        sb12.append(str60);
                                        str37 = str61;
                                        str20 = str7;
                                        sb12.append(str20);
                                        sb12.append(i38);
                                        str35 = str6;
                                        sb12.append(str35);
                                        sb12.append(i9);
                                        printStream10.println(sb12.toString());
                                        i8 = i38;
                                    } else {
                                        str33 = str13;
                                        i7 = i35;
                                        str35 = str6;
                                        str36 = str8;
                                        str37 = str61;
                                        str20 = str7;
                                        if (!str32.equals("") && str30.equals("")) {
                                            int i39 = Integer.parseInt(str32) - 1;
                                            System.out.println(str36 + str60 + str20 + i39 + str35 + 0);
                                            i8 = i39;
                                        } else {
                                            System.out.println("key2" + str60);
                                            i8 = 0;
                                        }
                                        i9 = 0;
                                    }
                                    new ArrayList();
                                    daily_bible_twoyears.loadinmapp(daily_bible_twoyears.choosebook(i36), i36, i37);
                                    Iterator<Map.Entry<String, ArrayList<String>>> it5 = daily_bible_twoyears.levelsMap.entrySet().iterator();
                                    int i40 = i8;
                                    String str62 = "";
                                    int i41 = 1;
                                    while (it5.hasNext()) {
                                        Map.Entry<String, ArrayList<String>> next2 = it5.next();
                                        Iterator<Map.Entry<String, ArrayList<String>>> it6 = it5;
                                        String key2 = next2.getKey();
                                        int i42 = i41;
                                        PrintStream printStream11 = System.out;
                                        String str63 = str35;
                                        StringBuilder sb13 = new StringBuilder();
                                        String str64 = str36;
                                        String str65 = str5;
                                        sb13.append(str65);
                                        sb13.append(str60);
                                        sb13.append(str65);
                                        sb13.append(key2);
                                        printStream11.println(sb13.toString());
                                        if (key2.equals("0")) {
                                            System.out.println("found" + str60);
                                            ArrayList<String> value = next2.getValue();
                                            int i43 = Integer.parseInt(strReplaceAll4);
                                            if (!str32.equals("") && !str30.equals("")) {
                                                str5 = str65;
                                                String str66 = str4;
                                                System.out.println(str66);
                                                str41 = strReplaceAll4;
                                                StringBuilder sb14 = new StringBuilder();
                                                i12 = i40;
                                                sb14.append(daily_bible_twoyears.books[i36]);
                                                sb14.append(str30);
                                                sb14.append(LanguageTag.SEP);
                                                sb14.append(str32);
                                                str38 = str3;
                                                sb14.append(str38);
                                                sb14.append(i43);
                                                arrayList.add(new Bible("", sb14.toString()));
                                                str39 = str66;
                                            } else {
                                                str5 = str65;
                                                i12 = i40;
                                                str38 = str3;
                                                String str67 = str4;
                                                str41 = strReplaceAll4;
                                                if (!str32.equals("") && str30.equals("")) {
                                                    System.out.println(str67);
                                                    StringBuilder sb15 = new StringBuilder();
                                                    str39 = str67;
                                                    sb15.append(daily_bible_twoyears.books[i36]);
                                                    sb15.append(str32);
                                                    sb15.append(str38);
                                                    sb15.append(i43);
                                                    arrayList.add(new Bible("", sb15.toString()));
                                                } else {
                                                    str39 = str67;
                                                    System.out.println("kamel");
                                                    arrayList.add(new Bible("", daily_bible_twoyears.books[i36] + i43));
                                                }
                                            }
                                            Iterator<String> it7 = value.iterator();
                                            String str68 = str62;
                                            int i44 = i12;
                                            int i45 = 0;
                                            while (it7.hasNext()) {
                                                String next3 = it7.next();
                                                if (str32.equals("") || str30.equals("")) {
                                                    i13 = i36;
                                                    it2 = it7;
                                                    if (str32.equals("") || !str30.equals("")) {
                                                        PrintStream printStream12 = System.out;
                                                        StringBuilder sb16 = new StringBuilder();
                                                        i14 = i45;
                                                        str42 = str37;
                                                        sb16.append(str42);
                                                        sb16.append(next3);
                                                        printStream12.println(sb16.toString());
                                                        arrayList.add(new Bible(String.valueOf(i42), next3));
                                                        i42++;
                                                        str68 = str68 + next3 + "<br>";
                                                        str37 = str42;
                                                        i45 = i14;
                                                        i36 = i13;
                                                        it7 = it2;
                                                    } else if (i45 >= i44) {
                                                        if (i9 != -2) {
                                                            i9 = i44;
                                                        }
                                                        str68 = str68 + next3 + "<br>";
                                                        if (i44 <= i9) {
                                                            System.out.println("fromto" + i44);
                                                            i44++;
                                                            arrayList.add(new Bible(String.valueOf(i44), next3));
                                                        }
                                                        i9 = -2;
                                                    }
                                                } else {
                                                    if (i45 >= i44) {
                                                        i13 = i36;
                                                        str68 = str68 + next3 + "<br>";
                                                        if (i44 <= i9) {
                                                            PrintStream printStream13 = System.out;
                                                            StringBuilder sb17 = new StringBuilder();
                                                            it2 = it7;
                                                            sb17.append("fromto");
                                                            sb17.append(i44);
                                                            printStream13.println(sb17.toString());
                                                            i44++;
                                                            arrayList.add(new Bible(String.valueOf(i44), next3));
                                                        }
                                                    } else {
                                                        i13 = i36;
                                                    }
                                                    it2 = it7;
                                                }
                                                i14 = i45 + 1;
                                                str42 = str37;
                                                str37 = str42;
                                                i45 = i14;
                                                i36 = i13;
                                                it7 = it2;
                                            }
                                            i10 = i36;
                                            str40 = str37;
                                            i11 = i44;
                                            str62 = str68;
                                        } else {
                                            i10 = i36;
                                            str5 = str65;
                                            i11 = i40;
                                            str38 = str3;
                                            str39 = str4;
                                            str40 = str37;
                                            str41 = strReplaceAll4;
                                        }
                                        daily_bible_twoyears = this;
                                        str37 = str40;
                                        strReplaceAll4 = str41;
                                        i41 = i42;
                                        it5 = it6;
                                        str35 = str63;
                                        str36 = str64;
                                        i36 = i10;
                                        str4 = str39;
                                        str3 = str38;
                                        i40 = i11;
                                    }
                                    str19 = str35;
                                    str21 = str36;
                                    str25 = str3;
                                    str26 = str4;
                                    str27 = str37;
                                    z2 = z14;
                                    z16 = z17;
                                } else {
                                    str14 = str10;
                                    str33 = str13;
                                    str34 = str59;
                                    str18 = str11;
                                    str25 = str3;
                                    str26 = str4;
                                    str19 = str6;
                                    str20 = str7;
                                    str21 = str8;
                                    str27 = str9;
                                    i7 = i35;
                                    z2 = z14;
                                }
                                i4 = i;
                                z14 = z2;
                                i32 = i7;
                                str23 = str58;
                                str24 = str57;
                                str22 = str34;
                                str15 = str33;
                            } else {
                                str14 = str10;
                                i3 = i34;
                                charSequence = charSequence2;
                                str16 = str50;
                                str17 = str12;
                                str18 = str11;
                                str19 = str6;
                                str20 = str7;
                                str21 = str8;
                                str27 = str9;
                                str28 = str53;
                                str29 = str52;
                                i6 = i32;
                                i5 = i33;
                                str25 = str3;
                                str26 = str4;
                                str15 = str13;
                                if (strTrim2.equals(str15) || strTrim2.contains(str15)) {
                                    PrintStream printStream14 = System.out;
                                    StringBuilder sb18 = new StringBuilder();
                                    str24 = str29;
                                    sb18.append(str24);
                                    i4 = i;
                                    sb18.append(i4);
                                    str23 = str51;
                                    sb18.append(str23);
                                    str22 = str28;
                                    sb18.append(str);
                                    sb18.append(str22);
                                    sb18.append(i6);
                                    printStream14.println(sb18.toString());
                                    System.out.println(DbHelper.KEY_ATTACHMENT_SIZE + arrayList.size());
                                    i32 = 0;
                                    z14 = false;
                                    z15 = false;
                                }
                            }
                            int i46 = i5 + 1;
                            str9 = str27;
                            str52 = str24;
                            i31 = i4;
                            str51 = str23;
                            str53 = str22;
                            str7 = str20;
                            str50 = str16;
                            str4 = str26;
                            i34 = i3;
                            charSequence2 = charSequence;
                            str10 = str14;
                            str12 = str17;
                            str11 = str18;
                            str6 = str19;
                            str8 = str21;
                            str13 = str15;
                            str3 = str25;
                            daily_bible_twoyears = this;
                            i33 = i46;
                            strArr3 = strArr;
                        }
                        i4 = i31;
                        i5 = i33;
                        str25 = str3;
                        str26 = str4;
                        str27 = str9;
                        int i462 = i5 + 1;
                        str9 = str27;
                        str52 = str24;
                        i31 = i4;
                        str51 = str23;
                        str53 = str22;
                        str7 = str20;
                        str50 = str16;
                        str4 = str26;
                        i34 = i3;
                        charSequence2 = charSequence;
                        str10 = str14;
                        str12 = str17;
                        str11 = str18;
                        str6 = str19;
                        str8 = str21;
                        str13 = str15;
                        str3 = str25;
                        daily_bible_twoyears = this;
                        i33 = i462;
                        strArr3 = strArr;
                    }
                    i4 = i;
                    i32 = i6;
                    str23 = str51;
                    str24 = str29;
                    str22 = str28;
                    int i4622 = i5 + 1;
                    str9 = str27;
                    str52 = str24;
                    i31 = i4;
                    str51 = str23;
                    str53 = str22;
                    str7 = str20;
                    str50 = str16;
                    str4 = str26;
                    i34 = i3;
                    charSequence2 = charSequence;
                    str10 = str14;
                    str12 = str17;
                    str11 = str18;
                    str6 = str19;
                    str8 = str21;
                    str13 = str15;
                    str3 = str25;
                    daily_bible_twoyears = this;
                    i33 = i4622;
                    strArr3 = strArr;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    private void addIntroAndAsks(String str, List<Bible> list, boolean z) throws IOException {
        try {
            InputStream inputStreamOpen = getAssets().open("daily/intro.xml");
            byte[] bArr = new byte[inputStreamOpen.available()];
            inputStreamOpen.read(bArr);
            String[] strArrSplit = new String(bArr).split("\n");
            if (z) {
                boolean z2 = false;
                for (String str2 : strArrSplit) {
                    String strTrim = str2.trim();
                    if (str.toLowerCase().equals(strTrim.toLowerCase())) {
                        z2 = true;
                    } else if (z2) {
                        list.add(new Bible("", "مقدمة" + strTrim));
                        z2 = false;
                    }
                    if (strTrim.contains("+++") && z2) {
                        break;
                    }
                }
            } else {
                boolean z3 = false;
                for (String str3 : strArrSplit) {
                    String strTrim2 = str3.trim();
                    if (str.toLowerCase().equals(strTrim2.toLowerCase())) {
                        z3 = true;
                    } else if (z3) {
                        list.add(new Bible("", strTrim2));
                    }
                    if (!strTrim2.contains("+++") || !z3) {
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InputStream inputStreamOpen2 = getAssets().open("daily/asks.xml");
            byte[] bArr2 = new byte[inputStreamOpen2.available()];
            inputStreamOpen2.read(bArr2);
            String[] strArrSplit2 = new String(bArr2).split("\n");
            if (z) {
                boolean z4 = false;
                for (String str4 : strArrSplit2) {
                    String strTrim3 = str4.trim();
                    if (str.replace(DateFormat.NUM_MONTH, "A").toLowerCase().equals(strTrim3.toLowerCase())) {
                        z4 = true;
                    } else if (z4) {
                        list.add(new Bible("", strTrim3));
                        z4 = false;
                    }
                    if (strTrim3.contains("+++") && z4) {
                        return;
                    }
                }
                return;
            }
            boolean z5 = false;
            for (String str5 : strArrSplit2) {
                String strTrim4 = str5.trim();
                if (str.replace(DateFormat.NUM_MONTH, "A").toLowerCase().equals(strTrim4.toLowerCase())) {
                    z5 = true;
                } else if (z5) {
                    list.add(new Bible("", strTrim4));
                }
                if (strTrim4.contains("+++") && z5) {
                    return;
                }
            }
        } catch (IOException e2) {
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

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mediaPlayer) throws IllegalStateException {
        playSong();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() throws IllegalStateException {
        this.mp.stop();
        super.onDestroy();
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

    /* JADX INFO: Access modifiers changed from: private */
    public void updateData(final String str, final String str2, final String str3) {
        findViewById(R.id.progress).setVisibility(0);
        Observable.fromCallable(new Callable() { // from class: com.app.orsozoxi.-$$Lambda$Daily_bible_twoyears$sZSlDgCqw9xFGjRmNweMirhELjA
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$updateData$0$Daily_bible_twoyears(str, str2, str3);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.orsozoxi.-$$Lambda$Daily_bible_twoyears$SL0moZNKJAdoDi4I16tm_dHp9hY
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$updateData$1$Daily_bible_twoyears((List) obj);
            }
        });
    }

    public /* synthetic */ List lambda$updateData$0$Daily_bible_twoyears(String str, String str2, String str3) throws Exception {
        return loading(Integer.valueOf(str).intValue(), str2, Integer.valueOf(str3).intValue());
    }

    public /* synthetic */ void lambda$updateData$1$Daily_bible_twoyears(List list) throws Exception {
        this.legendList.addAll(list);
        findViewById(R.id.progress).setVisibility(8);
        this.bibleAdapter.notifyDataSetChanged();
        this.listViewFootballLegend.setSelection(0);
        NestedScrollView2 nestedScrollView2 = (NestedScrollView2) findViewById(R.id.v_nested);
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
        throw new UnsupportedOperationException("Method not decompiled: com.app.orsozoxi.Daily_bible_twoyears.updatelistOld(int, int):java.lang.String[]");
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

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    private void initFloating() {
        FloatingActionMenu floatingActionMenu = (FloatingActionMenu) findViewById(R.id.menu_options);
        this.mHomeFloatingActionMenu = floatingActionMenu;
        floatingActionMenu.setIconAnimated(false);
        this.mHomeFloatingActionMenu.setClosedOnTouchOutside(true);
        this.mHomeFloatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Daily_bible_twoyears.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Daily_bible_twoyears.this.mHomeFloatingActionMenu.toggle(true);
            }
        });
        findViewById(R.id.fb_copy).setOnClickListener(this);
        findViewById(R.id.fb_highlight).setOnClickListener(this);
        findViewById(R.id.fb_share).setOnClickListener(this);
        findViewById(R.id.fb_ask).setOnClickListener(this);
        findViewById(R.id.fb_intro).setOnClickListener(this);
        findViewById(R.id.fb_show_colored).setOnClickListener(this);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        FloatingActionMenu floatingActionMenu = this.mHomeFloatingActionMenu;
        if (floatingActionMenu != null && floatingActionMenu.isOpened()) {
            this.mHomeFloatingActionMenu.toggle(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int itemId = (int) this.listViewFootballLegend.getAdapter().getItemId(this.pos);
        NestedScrollView2 nestedScrollView2 = (NestedScrollView2) findViewById(R.id.v_nested);
        int i = 0;
        switch (view.getId()) {
            case R.id.fb_ask /* 2131296646 */:
                this.mHomeFloatingActionMenu.toggle(false);
                nestedScrollView2.scrollTo(0, 0);
                this.asksSpin.performClick();
                break;
            case R.id.fb_copy /* 2131296647 */:
                this.mHomeFloatingActionMenu.toggle(false);
                String nick = this.legendList.get(itemId).getNick();
                while (i < this.legendList.size()) {
                    nick = nick + "\n" + this.legendList.get(i).getNick();
                    i++;
                }
                app.copyText(this, nick);
                break;
            case R.id.fb_highlight /* 2131296651 */:
                this.mHomeFloatingActionMenu.toggle(false);
                Toast.makeText(getApplicationContext(), R.string.plz_long_press, 1).show();
                break;
            case R.id.fb_intro /* 2131296652 */:
                this.mHomeFloatingActionMenu.toggle(false);
                nestedScrollView2.scrollTo(0, 0);
                this.introSpin.performClick();
                break;
            case R.id.fb_share /* 2131296656 */:
                this.mHomeFloatingActionMenu.toggle(false);
                String str = "";
                while (i < this.legendList.size()) {
                    str = str + "\n" + this.legendList.get(i).getNick();
                    i++;
                }
                app.shareText(this, str);
                break;
            case R.id.fb_show_colored /* 2131296658 */:
                this.mHomeFloatingActionMenu.toggle(false);
                startActivity(new Intent(this, (Class<?>) ShowHighlightsActivity.class));
                break;
        }
    }
}
