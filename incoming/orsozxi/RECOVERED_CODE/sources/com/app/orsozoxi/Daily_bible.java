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
import com.app.orsozoxi.Adapters.DailyBibleOneYearListAdapter;
import com.app.orsozoxi.Adapters.ExpandableListAdapter;
import com.app.orsozoxi.Beans.Bible;
import com.app.orsozoxi.Beans.WhiteBackgroundActivition;
import com.app.orsozoxi.Beans.highlightAyat;
import com.app.orsozoxi.BibleDataBase.BibleDataBase;
import com.app.orsozoxi.BibleDataBase.UseDatabaseHandler;
import com.app.orsozoxi.Others.SavePrefs;
import com.app.orsozoxi.Others.checksim;
import com.github.clans.fab.FloatingActionMenu;
import com.hieupt.android.standalonescrollbar.StandaloneScrollBar;
import com.hieupt.android.standalonescrollbar.ViewExtensionsKt;
import com.hieupt.android.standalonescrollbar.view.NestedScrollView2;
import com.ibm.icu.text.DateFormat;
import com.navdrawer.SimpleSideDrawer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.Callable;
import net.lingala.zip4j.util.InternalZipConstants;
import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;

/* loaded from: classes.dex */
public class Daily_bible extends BaseActivity implements MediaPlayer.OnCompletionListener, View.OnClickListener {
    static final int DATE_DIALOG_ID = 999;
    private Spinner asksSpin;
    DailyBibleOneYearListAdapter bibleAdapter;
    String[] books;
    int chaptersnumbers;
    private Context ctx;
    Dictionary<Integer, String> d;
    Button dailysearch;
    private int dayy;
    private GestureDetector gestureDetector;
    AdapterView.AdapterContextMenuInfo info;
    private Spinner introSpin;
    List<Bible> legendList;
    ArrayList<String> levels;
    HashMap<String, ArrayList<String>> levelsMap;
    private ExpandableListAdapter listAdapter;
    private ListView listViewFootballLegend;
    private FloatingActionMenu mHomeFloatingActionMenu;
    private int monthh;
    MediaPlayer mp;
    Button playmusic;
    Button search;
    SimpleSideDrawer slide_me;
    Spinner spinner2;
    private int yearr;
    int book = 0;
    int chapter = 0;
    int pos = 0;
    private boolean sp1 = false;
    private boolean sp2 = false;
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() { // from class: com.app.orsozoxi.Daily_bible.7
        @Override // android.app.DatePickerDialog.OnDateSetListener
        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
            Daily_bible.this.yearr = i;
            Daily_bible.this.monthh = i2;
            Daily_bible.this.dayy = i3;
            Daily_bible.this.legendList.clear();
            Daily_bible.this.bibleAdapter.notifyDataSetChanged();
            System.out.println("monthh" + Daily_bible.this.monthh);
            Daily_bible daily_bible = Daily_bible.this;
            String str = Daily_bible.this.dayy + "";
            Daily_bible daily_bible2 = Daily_bible.this;
            daily_bible.updateData(str, daily_bible2.getmonth(daily_bible2.monthh + 1));
            Daily_bible.this.listViewFootballLegend.setSelection(0);
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
            this.asksSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.orsozoxi.Daily_bible.1
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j) throws IOException {
                    Log.d("orso", "asks spin 0");
                    try {
                        InputStream inputStreamOpen2 = Daily_bible.this.getAssets().open("daily/asks.xml");
                        byte[] bArr2 = new byte[inputStreamOpen2.available()];
                        inputStreamOpen2.read(bArr2);
                        String str = new String(bArr2);
                        int i3 = i2 + 1;
                        String str2 = "A+" + i3;
                        if (i3 >= 6 && i3 < 47) {
                            Toast.makeText(Daily_bible.this.getApplicationContext(), R.string.no_questions, 1).show();
                            return;
                        }
                        Log.d("orso", "asks spin 1");
                        String[] strArrSplit2 = str.split("\n");
                        String str3 = "";
                        boolean z = false;
                        for (String str4 : strArrSplit2) {
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
                        if (!Daily_bible.this.sp1) {
                            Daily_bible.this.sp1 = true;
                            return;
                        }
                        Intent intent = new Intent(Daily_bible.this, (Class<?>) TafserActivity.class);
                        intent.putExtra("tafser", str3.replace("+++", ""));
                        intent.putStringArrayListExtra("tafserList", null);
                        intent.putExtra("title", "أسئله على " + Daily_bible.this.books[i2]);
                        Daily_bible.this.startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            this.introSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.orsozoxi.Daily_bible.2
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j) throws IOException {
                    try {
                        InputStream inputStreamOpen2 = Daily_bible.this.getAssets().open("daily/intro.xml");
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
                        if (!Daily_bible.this.sp2) {
                            Daily_bible.this.sp2 = true;
                            return;
                        }
                        Intent intent = new Intent(Daily_bible.this, (Class<?>) TafserActivity.class);
                        intent.putExtra("tafser", str2.replace("+++", ""));
                        intent.putStringArrayListExtra("tafserList", null);
                        intent.putExtra("title", "مقدمة " + Daily_bible.this.books[i2]);
                        Daily_bible.this.startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException unused) {
        }
        new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        this.mp = MediaPlayer.create(getApplicationContext(), R.raw.music);
        Button button = (Button) findViewById(R.id.btn_play_music);
        this.playmusic = button;
        button.setVisibility(0);
        this.playmusic.setBackgroundResource(R.drawable.btn_play);
        this.playmusic.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Daily_bible.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) throws IllegalStateException {
                if (!Daily_bible.this.mp.isPlaying()) {
                    Daily_bible daily_bible = Daily_bible.this;
                    MediaPlayer mediaPlayerCreate = MediaPlayer.create(daily_bible.getApplicationContext(), R.raw.music);
                    daily_bible.mp = mediaPlayerCreate;
                    daily_bible.mp = mediaPlayerCreate;
                    Daily_bible.this.playSong();
                    Daily_bible.this.playmusic.setBackgroundResource(R.drawable.btn_pause);
                    return;
                }
                Daily_bible.this.playmusic.setBackgroundResource(R.drawable.btn_play);
                Daily_bible.this.mp.pause();
            }
        });
        Button button2 = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button2.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Daily_bible.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        getWindow().addFlags(128);
        new checksim();
        setTitle(getString(R.string.t_bible_one_year));
        ((TextView) findViewById(R.id.tv_header_title)).setText(getString(R.string.t_bible_one_year));
        getResources();
        this.legendList = new ArrayList();
        Button button3 = (Button) findViewById(R.id.dailyysea);
        this.dailysearch = button3;
        button3.setVisibility(0);
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
        DailyBibleOneYearListAdapter dailyBibleOneYearListAdapter = new DailyBibleOneYearListAdapter(getApplicationContext(), R.layout.row_agpya_item, this.legendList, this.books, null, -1);
        this.bibleAdapter = dailyBibleOneYearListAdapter;
        this.listViewFootballLegend.setAdapter((ListAdapter) dailyBibleOneYearListAdapter);
        this.listViewFootballLegend.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: com.app.orsozoxi.Daily_bible.5
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i3, long j) {
                Daily_bible.this.pos = i3;
                Daily_bible.this.highlight();
                return false;
            }
        });
        Calendar calendar = Calendar.getInstance();
        String[] strArrSplit3 = new java.sql.Date(System.currentTimeMillis()).toGMTString().split(" ");
        int i3 = calendar.get(5);
        int i4 = calendar.get(2);
        String str = strArrSplit3[1];
        updateData(i3 + "", getmonth(i4 + 1));
        this.yearr = calendar.get(1);
        this.monthh = calendar.get(2);
        this.dayy = calendar.get(5);
        this.dailysearch.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Daily_bible.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Daily_bible.this.showDialog(999);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x0221 A[Catch: IOException -> 0x010d, TryCatch #4 {IOException -> 0x010d, blocks: (B:18:0x00c3, B:21:0x00cd, B:22:0x00d2, B:35:0x018e, B:37:0x0194, B:44:0x0209, B:45:0x021b, B:47:0x0221, B:49:0x0259, B:51:0x0285, B:53:0x028b), top: B:156:0x00c3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.util.List<com.app.orsozoxi.Beans.Bible> loading(int r36, java.lang.String r37) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1935
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.orsozoxi.Daily_bible.loading(int, java.lang.String):java.util.List");
    }

    @Override // android.app.Activity
    protected Dialog onCreateDialog(int i) {
        if (i != 999) {
            return null;
        }
        System.out.println("monthhdialog" + this.monthh + "++" + this.yearr + "==" + this.dayy);
        return new DatePickerDialog(this, this.datePickerListener, this.yearr, this.monthh, this.dayy);
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
    public void updateData(final String str, final String str2) {
        findViewById(R.id.progress).setVisibility(0);
        Observable.fromCallable(new Callable() { // from class: com.app.orsozoxi.-$$Lambda$Daily_bible$JHl8RGjtOpwJxgV_BjhTylC_6pI
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$updateData$0$Daily_bible(str, str2);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.orsozoxi.-$$Lambda$Daily_bible$B3bt04P39f2aehjLWHa-X3B3ESo
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$updateData$1$Daily_bible((List) obj);
            }
        });
    }

    public /* synthetic */ List lambda$updateData$0$Daily_bible(String str, String str2) throws Exception {
        return loading(Integer.valueOf(str).intValue(), str2);
    }

    public /* synthetic */ void lambda$updateData$1$Daily_bible(List list) throws Exception {
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

    public String[] getUpdatelist(int i, int i2) throws SQLException {
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
        throw new UnsupportedOperationException("Method not decompiled: com.app.orsozoxi.Daily_bible.updatelistOld(int, int):java.lang.String[]");
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
        AppLeftSlider.setCurrentActivity(this);
        super.onResume();
    }

    private void initFloating() {
        FloatingActionMenu floatingActionMenu = (FloatingActionMenu) findViewById(R.id.menu_options);
        this.mHomeFloatingActionMenu = floatingActionMenu;
        floatingActionMenu.setIconAnimated(false);
        this.mHomeFloatingActionMenu.setClosedOnTouchOutside(true);
        this.mHomeFloatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.Daily_bible.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Daily_bible.this.mHomeFloatingActionMenu.toggle(true);
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
        if (this.mHomeFloatingActionMenu.isOpened()) {
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
