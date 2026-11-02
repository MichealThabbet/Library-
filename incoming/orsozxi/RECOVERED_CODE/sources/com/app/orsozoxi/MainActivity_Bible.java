package com.app.orsozoxi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import com.app.orsozoxi.Adapters.DBAdapter_bookmarkBibleNew;
import com.app.orsozoxi.Adapters.DBAdapter_bookmarkBibleOld;
import com.app.orsozoxi.Adapters.ExpandableListAdapter;
import com.app.orsozoxi.Adapters.Newbible_BibleListAdapter;
import com.app.orsozoxi.Beans.Ayat;
import com.app.orsozoxi.Beans.Bible;
import com.app.orsozoxi.Beans.WhiteBackgroundActivition;
import com.app.orsozoxi.Beans.highlightAyat;
import com.app.orsozoxi.BibleDataBase.BibleDataBase;
import com.app.orsozoxi.BibleDataBase.UseDatabaseHandler;
import com.app.orsozoxi.KholgayBeans.ApplicationDialogs;
import com.app.orsozoxi.Others.AlertDialogManager;
import com.app.orsozoxi.Others.CommonUtilities;
import com.app.orsozoxi.Others.ConnectionDetector;
import com.app.orsozoxi.Others.SavePrefs;
import com.app.orsozoxi.Others.SubINotificationFactory;
import com.app.orsozoxi.Others.Util;
import com.app.orsozoxi.Others.checksim;
import com.app.orsozoxi.ShawahedBeans.As7a7WithShwahed;
import com.app.orsozoxi.ShawahedBeans.AyahShwahed;
import com.app.orsozoxi.ShawahedBeans.BibleFunctions;
import com.app.orsozoxi.ShawahedBeans.SafrSpecial;
import com.app.orsozoxi.ShawahedBeans.SafrWithShwahed;
import com.app.orsozoxi.ShawahedBeans.Shahed;
import com.app.orsozoxi.Views.MenuDateView;
import com.app.orsozoxi.Views.NonScrollListView;
import com.github.clans.fab.FloatingActionMenu;
import com.hieupt.android.standalonescrollbar.StandaloneScrollBar;
import com.hieupt.android.standalonescrollbar.ViewExtensionsKt;
import com.hieupt.android.standalonescrollbar.view.NestedScrollView2;
import com.huawei.hms.api.bean.HwAudioPlayItem;
import com.huawei.hms.audiokit.player.callback.HwAudioConfigCallBack;
import com.huawei.hms.audiokit.player.manager.HwAudioConfigManager;
import com.huawei.hms.audiokit.player.manager.HwAudioManager;
import com.huawei.hms.audiokit.player.manager.HwAudioManagerFactory;
import com.huawei.hms.audiokit.player.manager.HwAudioPlayerConfig;
import com.huawei.hms.audiokit.player.manager.HwAudioPlayerManager;
import com.huawei.hms.audiokit.player.manager.HwAudioQueueManager;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.ibm.icu.impl.locale.LanguageTag;
import com.ibm.icu.text.DateFormat;
import com.navdrawer.SimpleSideDrawer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import net.lingala.zip4j.util.InternalZipConstants;
import net.sqlcipher.database.SQLiteDatabase;
import org.apache.http.protocol.HTTP;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Marker;

/* loaded from: classes.dex */
public class MainActivity_Bible extends BaseActivity implements View.OnClickListener {
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapterglav;
    String[] arabibleglaves;
    Spinner asksSpin;
    private ArrayList<Ayat> ayat;
    Newbible_BibleListAdapter bibleAdapter;
    private String bookName;
    private DBAdapter_bookmarkBibleNew bookmarkAdapterNew;
    private DBAdapter_bookmarkBibleOld bookmarkAdapterOld;
    String[] books;
    private ConnectionDetector cd;
    int chaptersnumbers;
    private View content;
    int counterr;
    private Context ctx;
    ArrayList<Integer> current;
    Dictionary<Integer, String> d;
    int def;
    private Typeface faceltr;
    File fileWithinMyDirHTML;
    private GestureDetector gestureDetector2;
    AdapterView.AdapterContextMenuInfo info;
    Spinner introSpin;
    List<Bible> legendList;
    ArrayList<String> levels;
    HashMap<String, ArrayList<String>> levelsMap;
    private NonScrollListView listViewFootballLegend;
    private ProgressDialog loadingDialog;
    private FloatingActionMenu mHomeFloatingActionMenu;
    private FloatingActionMenu mHomeFloatingActionMenu2;
    private HwAudioConfigManager mHwAudioConfigManager;
    private HwAudioPlayerManager mHwAudioPlayerManager;
    private HwAudioQueueManager mHwAudioQueueManager;
    private ProgressDialog mProgressDialog;
    private MediaPlayer mediaPlayer;
    private MenuDateView menuDateView;
    private MediaPlayer mp;
    Button playmusic;
    private SafrWithShwahed safr;
    private CheckBox shawahedCheckBox;
    SimpleSideDrawer slide_me;
    private ArrayList<SafrSpecial> specialSafrs;
    Spinner spinner;
    Spinner spinner2;
    TextView t;
    private int tafserBookId;
    WebView we;
    private int first = 0;
    private boolean sp1 = false;
    private boolean sp2 = false;
    boolean bookmarked = false;
    private int firsttime = 0;
    int pos = 0;
    private final int CONTEXT_MENU_ID = 1;
    int currentayah = -1;
    boolean isOld = false;
    private int bookIndex = 0;
    private int chapterIndex = 0;
    private int selectedBookIDSH = 1;
    private boolean isShawahed = false;
    private AdapterView.OnItemLongClickListener itemLongClickHandler = new AdapterView.OnItemLongClickListener() { // from class: com.app.orsozoxi.MainActivity_Bible.4
        @Override // android.widget.AdapterView.OnItemLongClickListener
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
            MainActivity_Bible.this.pos = i;
            return true;
        }
    };
    int numOfChapters = 1;
    private String downloadPath = "https://www.orsozoxi.net/app_update/bible_tafsir/";
    String bookPath = "";
    int bookPathChapters = 0;
    boolean isShareFace = false;
    String xmlString = "";
    boolean backPressed = false;

    public String chooseEngBibleCahp(int i) {
        int i2 = i + 1;
        return i2 == 1 ? "engbible1" : i2 == 2 ? "engbible2" : i2 == 3 ? "engbible3" : i2 == 4 ? "engbible4" : i2 == 5 ? "engbible5" : i2 == 6 ? "engbible6" : i2 == 7 ? "engbible7" : i2 == 8 ? "engbible8" : i2 == 9 ? "engbible9" : i2 == 10 ? "engbible10" : i2 == 11 ? "engbible11" : i2 == 12 ? "engbible12" : i2 == 13 ? "engbible13" : i2 == 14 ? "engbible14" : i2 == 15 ? "engbible15" : i2 == 16 ? "engbible16" : i2 == 17 ? "engbible17" : i2 == 18 ? "engbible18" : i2 == 19 ? "engbible19" : i2 == 20 ? "engbible20" : i2 == 21 ? "engbible21" : i2 == 22 ? "engbible22" : i2 == 23 ? "engbible23" : i2 == 24 ? "engbible24" : i2 == 25 ? "engbible25" : i2 == 26 ? "engbible26" : i2 == 27 ? "engbible27" : i2 == 28 ? "engbible28" : i2 == 29 ? "engbible29" : i2 == 30 ? "engbible30" : i2 == 31 ? "engbible31" : i2 == 32 ? "engbible32" : i2 == 33 ? "engbible33" : i2 == 34 ? "engbible34" : i2 == 35 ? "engbible35" : i2 == 36 ? "engbible36" : i2 == 37 ? "engbible37" : i2 == 38 ? "engbible38" : i2 == 39 ? "engbible39" : i2 == 40 ? "engbible40" : i2 == 41 ? "engbible41" : i2 == 42 ? "engbible42" : i2 == 43 ? "engbible43" : i2 == 44 ? "engbible44" : i2 == 45 ? "engbible45" : i2 == 46 ? "engbible46" : i2 == 47 ? "engbible47" : i2 == 48 ? "engbible48" : i2 == 49 ? "engbible49" : i2 == 50 ? "engbible50" : i2 == 51 ? "engbible51" : i2 == 52 ? "engbible52" : i2 == 53 ? "engbible53" : i2 == 54 ? "engbible54" : i2 == 55 ? "engbible55" : i2 == 56 ? "engbible56" : i2 == 57 ? "engbible57" : i2 == 58 ? "engbible58" : i2 == 59 ? "engbible59" : i2 == 60 ? "engbible60" : i2 == 61 ? "engbible61" : i2 == 62 ? "engbible62" : i2 == 63 ? "engbible63" : i2 == 64 ? "engbible64" : i2 == 65 ? "engbible65" : i2 == 66 ? "engbible66" : i2 == 67 ? "engbible67" : i2 == 68 ? "engbible68" : i2 == 69 ? "engbible69" : i2 == 70 ? "engbible70" : i2 == 71 ? "engbible71" : i2 == 72 ? "engbible72" : i2 == 73 ? "engbible73" : "";
    }

    public String chooseTafserFolder(int i) {
        return i == 0 ? "arabbible0" : i == 1 ? "arabbible1" : i == 2 ? "arabbible2" : i == 3 ? "arabbible3" : i == 4 ? "arabbible4" : i == 5 ? "arabbible5" : i == 6 ? "arabbible6" : i == 7 ? "arabbible7" : i == 8 ? "arabbible8" : i == 9 ? "arabbible9" : i == 10 ? "arabbible10" : i == 11 ? "arabbible11" : i == 12 ? "arabbible12" : i == 13 ? "arabbible13" : i == 14 ? "arabbible14" : i == 15 ? "arabbible15" : i == 16 ? "arabbible16" : i == 17 ? "arabbible17" : i == 18 ? "arabbible18" : i == 19 ? "arabbible19" : i == 20 ? "arabbible20" : i == 21 ? "arabbible21" : i == 22 ? "arabbible22" : i == 23 ? "arabbible23" : i == 24 ? "arabbible24" : i == 25 ? "arabbible25" : i == 26 ? "arabbible26" : i == 27 ? "arabbible27" : i == 28 ? "arabbible28" : i == 29 ? "arabbible29" : i == 30 ? "arabbible30" : i == 31 ? "arabbible31" : i == 32 ? "arabbible32" : i == 33 ? "arabbible33" : i == 34 ? "arabbible34" : i == 35 ? "arabbible35" : i == 36 ? "arabbible36" : i == 37 ? "arabbible37" : i == 38 ? "arabbible38" : i == 39 ? "arabbible39" : i == 40 ? "arabbible40" : i == 41 ? "arabbible41" : i == 42 ? "arabbible42" : i == 43 ? "arabbible43" : i == 44 ? "arabbible44" : i == 45 ? "arabbible45" : i == 46 ? "arabbible46" : i == 47 ? "arabbible47" : i == 48 ? "arabbible48" : i == 49 ? "arabbible49" : i == 50 ? "arabbible50" : i == 51 ? "arabbible51" : i == 52 ? "arabbible52" : i == 53 ? "arabbible53" : i == 54 ? "arabbible54" : i == 55 ? "arabbible55" : i == 56 ? "arabbible56" : i == 57 ? "arabbible57" : i == 58 ? "arabbible58" : i == 59 ? "arabbible59" : i == 60 ? "arabbible60" : i == 61 ? "arabbible61" : i == 62 ? "arabbible62" : i == 63 ? "arabbible63" : i == 64 ? "arabbible64" : i == 65 ? "arabbible65" : i == 66 ? "arabbible66" : i == 67 ? "arabbible67" : i == 68 ? "arabbible68" : i == 69 ? "arabbible69" : i == 70 ? "arabbible70" : i == 71 ? "arabbible71" : i == 72 ? "arabbible72" : "";
    }

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_bible__main);
        this.shawahedCheckBox = (CheckBox) findViewById(R.id.radio_shawahed);
        this.gestureDetector2 = new GestureDetector(new SwipeDetector());
        NestedScrollView2 nestedScrollView2 = (NestedScrollView2) findViewById(R.id.v_nested);
        ViewExtensionsKt.attachTo((StandaloneScrollBar) findViewById(R.id.scrollbar), nestedScrollView2);
        WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs((Activity) this, (Class<?>) WhiteBackgroundActivition.class).load();
        if (whiteBackgroundActivition == null || !whiteBackgroundActivition.isWhiteBackground()) {
            nestedScrollView2.setBackgroundResource(R.drawable.background);
        } else {
            this.shawahedCheckBox.setTextColor(Color.parseColor("#000000"));
        }
        Button button = (Button) findViewById(R.id.btn_play_music);
        this.playmusic = button;
        button.setVisibility(0);
        this.mp = MediaPlayer.create(getApplicationContext(), R.raw.music);
        this.playmusic.setBackgroundResource(R.drawable.btn_play);
        this.playmusic.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivity_Bible.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) throws IllegalStateException {
                if (!MainActivity_Bible.this.mp.isPlaying()) {
                    MainActivity_Bible mainActivity_Bible = MainActivity_Bible.this;
                    mainActivity_Bible.mp = mainActivity_Bible.mp = MediaPlayer.create(mainActivity_Bible.getApplicationContext(), R.raw.music);
                    MainActivity_Bible.this.playSong();
                    MainActivity_Bible.this.playmusic.setBackgroundResource(R.drawable.btn_pause);
                    return;
                }
                MainActivity_Bible.this.playmusic.setBackgroundResource(R.drawable.btn_play);
                MainActivity_Bible.this.mp.pause();
            }
        });
        Button button2 = (Button) findViewById(R.id.btn_slide);
        SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        this.slide_me = simpleSideDrawer;
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button2.setVisibility(0);
        Slider.prepareListData(this);
        TextView textView = (TextView) findViewById(R.id.tv_header_title);
        final Button button3 = (Button) findViewById(R.id.btn_goto);
        button3.setVisibility(0);
        final Intent intent = getIntent();
        boolean booleanExtra = intent.getBooleanExtra("isOld", true);
        this.isOld = booleanExtra;
        if (booleanExtra) {
            textView.setText(getString(R.string.t_old_bible));
            button3.setText(getString(R.string.t_new_bible_sig));
        } else {
            textView.setText(getString(R.string.t_new_bible));
            button3.setText(getString(R.string.t_old_bible_sig));
        }
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivity_Bible.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainActivity_Bible.this.slide_me.toggleLeftDrawer();
            }
        });
        this.ctx = this;
        new Handler().postDelayed(new Runnable() { // from class: com.app.orsozoxi.MainActivity_Bible.3
            @Override // java.lang.Runnable
            public void run() throws IOException, SQLException {
                String[] strArr;
                MainActivity_Bible.this.initFloating();
                button3.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivity_Bible.3.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        Intent intent2 = new Intent(MainActivity_Bible.this, (Class<?>) MainActivity_Bible.class);
                        if (MainActivity_Bible.this.isOld) {
                            intent2.putExtra("isOld", false);
                        } else {
                            intent2.putExtra("isOld", true);
                        }
                        MainActivity_Bible.this.startActivity(intent2);
                    }
                });
                MainActivity_Bible.this.counterr = 0;
                MainActivity_Bible.this.getResources();
                MainActivity_Bible.this.bookmarkAdapterNew = new DBAdapter_bookmarkBibleNew(MainActivity_Bible.this.ctx);
                MainActivity_Bible.this.bookmarkAdapterOld = new DBAdapter_bookmarkBibleOld(MainActivity_Bible.this.ctx);
                MainActivity_Bible.this.legendList = new ArrayList();
                MainActivity_Bible.this.getWindow().addFlags(128);
                new checksim();
                MainActivity_Bible.this.d = new Hashtable();
                MainActivity_Bible.this.levelsMap = new HashMap<>();
                MainActivity_Bible.this.levels = new ArrayList<>();
                MainActivity_Bible mainActivity_Bible = MainActivity_Bible.this;
                mainActivity_Bible.t = (TextView) mainActivity_Bible.findViewById(R.id.gcm_msg);
                MainActivity_Bible mainActivity_Bible2 = MainActivity_Bible.this;
                mainActivity_Bible2.spinner = (Spinner) mainActivity_Bible2.findViewById(R.id.spinner1);
                MainActivity_Bible mainActivity_Bible3 = MainActivity_Bible.this;
                mainActivity_Bible3.spinner2 = (Spinner) mainActivity_Bible3.findViewById(R.id.spinner2);
                MainActivity_Bible mainActivity_Bible4 = MainActivity_Bible.this;
                mainActivity_Bible4.listViewFootballLegend = (NonScrollListView) mainActivity_Bible4.findViewById(R.id.liss1);
                MainActivity_Bible.this.listViewFootballLegend.setBackgroundColor(0);
                MainActivity_Bible.this.listViewFootballLegend.setCacheColorHint(0);
                MainActivity_Bible.this.listViewFootballLegend.setDivider(null);
                try {
                    InputStream inputStreamOpen = MainActivity_Bible.this.getAssets().open("biblenames.xml");
                    byte[] bArr = new byte[inputStreamOpen.available()];
                    inputStreamOpen.read(bArr);
                    String[] strArrSplit = new String(bArr).split("\n");
                    MainActivity_Bible.this.books = new String[73];
                    MainActivity_Bible.this.d = new Hashtable();
                    for (int i = 0; i < strArrSplit.length; i++) {
                        if (i < 73) {
                            MainActivity_Bible.this.d.put(Integer.valueOf(i), strArrSplit[i]);
                            MainActivity_Bible.this.books[i] = strArrSplit[i];
                        }
                    }
                    if (MainActivity_Bible.this.isOld) {
                        strArr = new String[46];
                        for (int i2 = 0; i2 < 46; i2++) {
                            strArr[i2] = MainActivity_Bible.this.books[i2];
                        }
                    } else {
                        String[] strArr2 = new String[27];
                        for (int i3 = 0; i3 < 27; i3++) {
                            strArr2[i3] = MainActivity_Bible.this.books[i3 + 46];
                        }
                        strArr = strArr2;
                    }
                    MainActivity_Bible.this.adapter = new ArrayAdapter<>(MainActivity_Bible.this, R.layout.spinner_item_white, strArr);
                    MainActivity_Bible.this.adapter.setDropDownViewResource(R.layout.spinner_item_black);
                    MainActivity_Bible.this.spinner.setAdapter((SpinnerAdapter) MainActivity_Bible.this.adapter);
                    if (MainActivity_Bible.this.isOld) {
                        MainActivity_Bible.this.spinner.setPrompt(MainActivity_Bible.this.getString(R.string.t_old_bible));
                    } else {
                        MainActivity_Bible.this.spinner.setPrompt(MainActivity_Bible.this.getString(R.string.t_new_bible));
                    }
                } catch (IOException unused) {
                }
                MainActivity_Bible.this.specialSafrs = BibleFunctions.fillSpecialSafrs();
                MainActivity_Bible mainActivity_Bible5 = MainActivity_Bible.this;
                MainActivity_Bible mainActivity_Bible6 = MainActivity_Bible.this;
                mainActivity_Bible5.bibleAdapter = new Newbible_BibleListAdapter(mainActivity_Bible6, R.layout.row_agpya_item, mainActivity_Bible6.legendList, MainActivity_Bible.this.books, MainActivity_Bible.this.current, MainActivity_Bible.this.currentayah, MainActivity_Bible.this.safr, MainActivity_Bible.this.isShawahed, MainActivity_Bible.this.chapterIndex);
                MainActivity_Bible.this.listViewFootballLegend.setAdapter((ListAdapter) MainActivity_Bible.this.bibleAdapter);
                MainActivity_Bible.this.chaptersnumbers = 0;
                MainActivity_Bible.this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.orsozoxi.MainActivity_Bible.3.2
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i4, long j) throws IOException {
                        if (MainActivity_Bible.this.isOld && MainActivity_Bible.this.isOld && (MainActivity_Bible.this.spinner.getSelectedItemPosition() == 45 || MainActivity_Bible.this.spinner.getSelectedItemPosition() == 44 || MainActivity_Bible.this.spinner.getSelectedItemPosition() == 16 || MainActivity_Bible.this.spinner.getSelectedItemPosition() == 17 || MainActivity_Bible.this.spinner.getSelectedItemPosition() == 24 || MainActivity_Bible.this.spinner.getSelectedItemPosition() == 25 || MainActivity_Bible.this.spinner.getSelectedItemPosition() == 29)) {
                            MainActivity_Bible.this.shawahedCheckBox.setVisibility(8);
                        } else {
                            MainActivity_Bible.this.shawahedCheckBox.setVisibility(0);
                        }
                        int i5 = 1;
                        if (MainActivity_Bible.this.isShawahed) {
                            if (!MainActivity_Bible.this.isOld) {
                                MainActivity_Bible.this.bookIndex = MainActivity_Bible.this.spinner.getSelectedItemPosition() + 46;
                            } else {
                                MainActivity_Bible.this.bookIndex = MainActivity_Bible.this.spinner.getSelectedItemPosition();
                            }
                            MainActivity_Bible.this.selectedBookIDSH = MainActivity_Bible.this.bookIndex + 1;
                            MainActivity_Bible.this.getBookChapters(MainActivity_Bible.this.spinner.getSelectedItemPosition());
                            MainActivity_Bible.this.chaptersnumbers = MainActivity_Bible.this.bookPathChapters;
                            MainActivity_Bible.this.arabibleglaves = new String[MainActivity_Bible.this.chaptersnumbers];
                            MainActivity_Bible.this.arabibleglaves[0] = "1";
                            while (i5 < MainActivity_Bible.this.chaptersnumbers) {
                                String[] strArr3 = MainActivity_Bible.this.arabibleglaves;
                                StringBuilder sb = new StringBuilder();
                                sb.append("");
                                int i6 = i5 + 1;
                                sb.append(i6);
                                strArr3[i5] = sb.toString();
                                i5 = i6;
                            }
                            MainActivity_Bible.this.setspinnn(MainActivity_Bible.this.arabibleglaves);
                            MainActivity_Bible.this.chaptersnumbers = 0;
                            MainActivity_Bible.this.init();
                            return;
                        }
                        MainActivity_Bible.this.getBookChapters(MainActivity_Bible.this.spinner.getSelectedItemPosition());
                        if (!MainActivity_Bible.this.isOld) {
                            MainActivity_Bible.this.bookIndex = MainActivity_Bible.this.spinner.getSelectedItemPosition() + 46;
                        } else {
                            MainActivity_Bible.this.bookIndex = MainActivity_Bible.this.spinner.getSelectedItemPosition();
                        }
                        MainActivity_Bible.this.selectedBookIDSH = MainActivity_Bible.this.bookIndex + 1;
                        MainActivity_Bible.this.chaptersnumbers = MainActivity_Bible.this.bookPathChapters;
                        MainActivity_Bible.this.arabibleglaves = new String[MainActivity_Bible.this.chaptersnumbers];
                        MainActivity_Bible.this.arabibleglaves[0] = "1";
                        while (i5 < MainActivity_Bible.this.chaptersnumbers) {
                            String[] strArr4 = MainActivity_Bible.this.arabibleglaves;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("");
                            int i7 = i5 + 1;
                            sb2.append(i7);
                            strArr4[i5] = sb2.toString();
                            i5 = i7;
                        }
                        MainActivity_Bible.this.setspinnn(MainActivity_Bible.this.arabibleglaves);
                        MainActivity_Bible.this.chaptersnumbers = 0;
                    }
                });
                MainActivity_Bible.this.bookmarkAdapterNew.open();
                MainActivity_Bible.this.bookmarkAdapterOld.open();
                MainActivity_Bible.this.shawahedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.app.orsozoxi.MainActivity_Bible.3.3
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        int selectedItemPosition;
                        MainActivity_Bible.this.isShawahed = z;
                        if (MainActivity_Bible.this.isShawahed) {
                            if (!MainActivity_Bible.this.isOld) {
                                selectedItemPosition = MainActivity_Bible.this.spinner.getSelectedItemPosition() + 46;
                            } else {
                                selectedItemPosition = MainActivity_Bible.this.spinner.getSelectedItemPosition();
                            }
                            MainActivity_Bible.this.selectedBookIDSH = selectedItemPosition + 1;
                            MainActivity_Bible.this.init();
                            return;
                        }
                        MainActivity_Bible.this.bibleAdapter = new Newbible_BibleListAdapter(MainActivity_Bible.this, R.layout.row_agpya_item, MainActivity_Bible.this.legendList, MainActivity_Bible.this.books, MainActivity_Bible.this.current, MainActivity_Bible.this.currentayah, MainActivity_Bible.this.safr, MainActivity_Bible.this.isShawahed, MainActivity_Bible.this.chapterIndex);
                        MainActivity_Bible.this.listViewFootballLegend.setAdapter((ListAdapter) MainActivity_Bible.this.bibleAdapter);
                        MainActivity_Bible.this.bibleAdapter.notifyDataSetChanged();
                        ((NestedScrollView2) MainActivity_Bible.this.findViewById(R.id.v_nested)).scrollTo(0, 0);
                    }
                });
                if (intent.getExtras() == null || !intent.getExtras().containsKey("as7a7")) {
                    String strFetchfirst = MainActivity_Bible.this.bookmarkAdapterNew.fetchfirst();
                    String strFetchfirst2 = MainActivity_Bible.this.bookmarkAdapterOld.fetchfirst();
                    if (!MainActivity_Bible.this.isOld) {
                        if (!strFetchfirst.equals("")) {
                            System.out.println("spliterin" + strFetchfirst);
                            String[] strArrSplit2 = strFetchfirst.split("\\+");
                            MainActivity_Bible.this.bookIndex = Integer.valueOf(strArrSplit2[0]).intValue();
                            MainActivity_Bible.this.chapterIndex = Integer.valueOf(strArrSplit2[1]).intValue();
                            System.out.println("keychap" + MainActivity_Bible.this.chapterIndex);
                            MainActivity_Bible.this.spinner.setSelection(MainActivity_Bible.this.bookIndex - 46);
                            MainActivity_Bible.this.spinner2.setSelection(MainActivity_Bible.this.chapterIndex);
                            MainActivity_Bible.this.bookmarked = true;
                        }
                    } else {
                        System.out.println("spliterin" + strFetchfirst2);
                        if (!strFetchfirst2.equals("")) {
                            String[] strArrSplit3 = strFetchfirst2.split("\\+");
                            MainActivity_Bible.this.bookIndex = Integer.valueOf(strArrSplit3[0]).intValue();
                            MainActivity_Bible.this.chapterIndex = Integer.valueOf(strArrSplit3[1]).intValue();
                            System.out.println("keychap" + MainActivity_Bible.this.chapterIndex);
                            MainActivity_Bible.this.spinner.setSelection(MainActivity_Bible.this.bookIndex);
                            MainActivity_Bible.this.spinner2.setSelection(MainActivity_Bible.this.chapterIndex);
                            MainActivity_Bible.this.bookmarked = true;
                        }
                    }
                } else if (!MainActivity_Bible.this.isOld) {
                    MainActivity_Bible.this.bookIndex = intent.getIntExtra("safr", 47) - 47;
                    MainActivity_Bible.this.chapterIndex = intent.getIntExtra("as7a7", 0);
                    MainActivity_Bible.this.spinner.setSelection(intent.getIntExtra("safr", 47) - 47);
                    MainActivity_Bible.this.spinner2.setSelection(intent.getIntExtra("as7a7", 0));
                    MainActivity_Bible.this.bookmarked = true;
                } else {
                    MainActivity_Bible.this.bookIndex = intent.getIntExtra("safr", 47) - 1;
                    MainActivity_Bible.this.chapterIndex = intent.getIntExtra("as7a7", 0);
                    MainActivity_Bible.this.spinner.setSelection(intent.getIntExtra("safr", 0) - 1);
                    MainActivity_Bible.this.spinner2.setSelection(intent.getIntExtra("as7a7", 0));
                    MainActivity_Bible.this.bookmarked = true;
                }
                MainActivity_Bible.this.bookmarkAdapterNew.close();
                MainActivity_Bible.this.bookmarkAdapterOld.close();
                MainActivity_Bible.this.spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.orsozoxi.MainActivity_Bible.3.4
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i4, long j) throws IllegalStateException, IOException {
                        if (MainActivity_Bible.this.mediaPlayer != null && MainActivity_Bible.this.mediaPlayer.isPlaying()) {
                            MainActivity_Bible.this.mediaPlayer.stop();
                            MainActivity_Bible.this.mediaPlayer.reset();
                            MainActivity_Bible.this.findViewById(R.id.btn_listen).setBackgroundResource(R.drawable.btn_playheader);
                        }
                        if (MainActivity_Bible.this.ayat != null && MainActivity_Bible.this.ayat.size() > 0) {
                            for (int i5 = 0; i5 < MainActivity_Bible.this.ayat.size(); i5++) {
                                MainActivity_Bible.this.ayat.remove(i5);
                            }
                            for (int i6 = 0; i6 < MainActivity_Bible.this.current.size(); i6++) {
                                MainActivity_Bible.this.current.remove(i6);
                            }
                            MainActivity_Bible.this.bibleAdapter = new Newbible_BibleListAdapter(MainActivity_Bible.this, R.layout.row_agpya_item, MainActivity_Bible.this.legendList, MainActivity_Bible.this.books, MainActivity_Bible.this.current, -1, MainActivity_Bible.this.safr, MainActivity_Bible.this.isShawahed, MainActivity_Bible.this.spinner2.getSelectedItemPosition());
                            MainActivity_Bible.this.listViewFootballLegend.setAdapter((ListAdapter) MainActivity_Bible.this.bibleAdapter);
                            MainActivity_Bible.this.bibleAdapter.notifyDataSetChanged();
                            MainActivity_Bible.this.listViewFootballLegend.setSelection(0);
                            ((NestedScrollView2) MainActivity_Bible.this.findViewById(R.id.v_nested)).scrollTo(0, 0);
                        }
                        MainActivity_Bible.this.ayat = new ArrayList();
                        MainActivity_Bible.this.current = new ArrayList<>();
                        Log.d("bib", "chapter=" + MainActivity_Bible.this.spinner2.getSelectedItemPosition());
                        if (MainActivity_Bible.this.bookmarked) {
                            MainActivity_Bible.this.spinner2.setSelection(MainActivity_Bible.this.chapterIndex);
                            MainActivity_Bible.this.bookmarked = false;
                        } else {
                            MainActivity_Bible.this.chapterIndex = MainActivity_Bible.this.spinner2.getSelectedItemPosition();
                        }
                        MainActivity_Bible.this.updatelistOld(MainActivity_Bible.this.bookIndex, MainActivity_Bible.this.chapterIndex);
                    }
                });
                MainActivity_Bible.this.ayat = new ArrayList();
                MainActivity_Bible.this.current = new ArrayList<>();
                MainActivity_Bible.this.listViewFootballLegend.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.app.orsozoxi.MainActivity_Bible.3.5
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> adapterView, View view, int i4, long j) {
                        int itemId = (int) MainActivity_Bible.this.listViewFootballLegend.getAdapter().getItemId(i4);
                        int i5 = MainActivity_Bible.this.chapterIndex + 1;
                        MainActivity_Bible.this.currentayah = itemId + 1;
                        boolean z = false;
                        for (int i6 = 0; i6 < MainActivity_Bible.this.ayat.size(); i6++) {
                            if (((Ayat) MainActivity_Bible.this.ayat.get(i6)).getAyahNum() == MainActivity_Bible.this.currentayah) {
                                MainActivity_Bible.this.ayat.remove(i6);
                                z = true;
                            }
                        }
                        if (!z) {
                            Ayat ayat = new Ayat();
                            ayat.setAyah(MainActivity_Bible.this.legendList.get(itemId).getNick().toString());
                            ayat.setAyahNum(MainActivity_Bible.this.currentayah);
                            if (!MainActivity_Bible.this.isOld) {
                                ayat.setBookId(MainActivity_Bible.this.bookIndex);
                            } else {
                                ayat.setBookId(MainActivity_Bible.this.bookIndex);
                            }
                            ayat.setBookNum(i5);
                            MainActivity_Bible.this.ayat.add(ayat);
                            MainActivity_Bible.this.current.add(Integer.valueOf(i4));
                        } else {
                            for (int i7 = 0; i7 < MainActivity_Bible.this.current.size(); i7++) {
                                if (MainActivity_Bible.this.current.get(i7).intValue() == i4) {
                                    MainActivity_Bible.this.current.remove(i7);
                                }
                            }
                        }
                        MainActivity_Bible.this.bibleAdapter = new Newbible_BibleListAdapter(MainActivity_Bible.this, R.layout.row_agpya_item, MainActivity_Bible.this.legendList, MainActivity_Bible.this.books, MainActivity_Bible.this.current, i4, MainActivity_Bible.this.safr, MainActivity_Bible.this.isShawahed, MainActivity_Bible.this.spinner2.getSelectedItemPosition());
                        MainActivity_Bible.this.listViewFootballLegend.setAdapter((ListAdapter) MainActivity_Bible.this.bibleAdapter);
                        MainActivity_Bible.this.bibleAdapter.notifyDataSetChanged();
                        MainActivity_Bible.this.listViewFootballLegend.setSelection(i4);
                    }
                });
                MainActivity_Bible.this.listViewFootballLegend.setOnItemLongClickListener(MainActivity_Bible.this.itemLongClickHandler);
                MainActivity_Bible mainActivity_Bible7 = MainActivity_Bible.this;
                mainActivity_Bible7.introSpin = (Spinner) mainActivity_Bible7.findViewById(R.id.sp_intro);
                MainActivity_Bible mainActivity_Bible8 = MainActivity_Bible.this;
                mainActivity_Bible8.asksSpin = (Spinner) mainActivity_Bible8.findViewById(R.id.sp_asks);
                MainActivity_Bible mainActivity_Bible9 = MainActivity_Bible.this;
                ArrayAdapter arrayAdapter = new ArrayAdapter(mainActivity_Bible9, R.layout.spinner_item_white, mainActivity_Bible9.books);
                arrayAdapter.setDropDownViewResource(R.layout.spinner_item_black);
                MainActivity_Bible.this.introSpin.setAdapter((SpinnerAdapter) arrayAdapter);
                MainActivity_Bible.this.introSpin.setPrompt(MainActivity_Bible.this.getString(R.string.bible_intros));
                MainActivity_Bible.this.asksSpin.setAdapter((SpinnerAdapter) arrayAdapter);
                MainActivity_Bible.this.asksSpin.setPrompt(MainActivity_Bible.this.getString(R.string.bible_questions));
                MainActivity_Bible.this.asksSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.orsozoxi.MainActivity_Bible.3.6
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i4, long j) throws IOException {
                        Log.d("orso", "asks spin 0");
                        try {
                            InputStream inputStreamOpen2 = MainActivity_Bible.this.getAssets().open("daily/asks.xml");
                            byte[] bArr2 = new byte[inputStreamOpen2.available()];
                            inputStreamOpen2.read(bArr2);
                            String str = new String(bArr2);
                            int i5 = i4 + 1;
                            String str2 = "A+" + i5;
                            if (i5 >= 6 && i5 < 47) {
                                Toast.makeText(MainActivity_Bible.this.getApplicationContext(), R.string.no_questions, 1).show();
                                return;
                            }
                            Log.d("orso", "asks spin 1");
                            String[] strArrSplit4 = str.split("\n");
                            String str3 = "";
                            boolean z = false;
                            for (String str4 : strArrSplit4) {
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
                            if (!MainActivity_Bible.this.sp1) {
                                MainActivity_Bible.this.sp1 = true;
                                return;
                            }
                            Intent intent2 = new Intent(MainActivity_Bible.this, (Class<?>) TafserActivity.class);
                            intent2.putExtra("tafser", str3.replace("+++", ""));
                            intent2.putStringArrayListExtra("tafserList", null);
                            intent2.putExtra("title", MainActivity_Bible.this.getString(R.string.questions_about) + " " + MainActivity_Bible.this.books[i4]);
                            MainActivity_Bible.this.startActivity(intent2);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                MainActivity_Bible.this.introSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.orsozoxi.MainActivity_Bible.3.7
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i4, long j) throws IOException {
                        try {
                            InputStream inputStreamOpen2 = MainActivity_Bible.this.getAssets().open("daily/intro.xml");
                            byte[] bArr2 = new byte[inputStreamOpen2.available()];
                            inputStreamOpen2.read(bArr2);
                            String[] strArrSplit4 = new String(bArr2).split("\n");
                            String str = "M+" + (i4 + 1);
                            String str2 = "";
                            boolean z = false;
                            for (String str3 : strArrSplit4) {
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
                            if (!MainActivity_Bible.this.sp2) {
                                MainActivity_Bible.this.sp2 = true;
                                return;
                            }
                            Intent intent2 = new Intent(MainActivity_Bible.this, (Class<?>) TafserActivity.class);
                            intent2.putExtra("tafser", str2.replace("+++", ""));
                            intent2.putStringArrayListExtra("tafserList", null);
                            intent2.putExtra("title", MainActivity_Bible.this.getString(R.string.intro) + " " + MainActivity_Bible.this.books[i4]);
                            MainActivity_Bible.this.startActivity(intent2);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }, 200L);
    }

    public void setspinnn(String[] strArr) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.big_spinner_item_white, strArr);
        this.adapterglav = arrayAdapter;
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item_black);
        this.spinner2.setAdapter((SpinnerAdapter) this.adapterglav);
        this.spinner2.setPrompt(getString(R.string.biblee));
    }

    private void copyAll() {
        String str = "";
        for (int i = 0; i < this.legendList.size(); i++) {
            str = str + this.legendList.get(i).getNick() + "\n";
        }
        ((ClipboardManager) getSystemService("clipboard")).setText(str + this.books[this.bookIndex] + getString(R.string.chapter) + (this.spinner2.getSelectedItemPosition() + 1));
        Toast.makeText(getApplicationContext(), R.string.copy_done, 1).show();
    }

    public void share(int i) throws SQLException {
        boolean z;
        String string;
        ArrayList<String> arrayList;
        this.listViewFootballLegend.getAdapter().getItemId(this.pos);
        ArrayList<Ayat> arrayList2 = this.ayat;
        String str = "";
        if (arrayList2 != null && arrayList2.size() == 1) {
            str = "" + this.ayat.get(0).getAyah() + " " + this.books[this.ayat.get(0).getBookId()] + this.ayat.get(0).getAyahNum() + ":" + this.ayat.get(0).getBookNum();
        } else {
            ArrayList<Ayat> arrayList3 = this.ayat;
            if (arrayList3 != null && arrayList3.size() > 1) {
                String str2 = "";
                for (int i2 = 0; i2 < this.ayat.size(); i2++) {
                    str2 = str2 + this.ayat.get(i2).getAyah() + " ";
                }
                int size = this.ayat.size() - 1;
                while (true) {
                    if (size < 0) {
                        z = true;
                        break;
                    }
                    int i3 = size + 1;
                    if (this.ayat.size() > i3 && this.ayat.get(size).getAyahNum() + 1 != this.ayat.get(i3).getAyahNum()) {
                        z = false;
                        break;
                    }
                    size--;
                }
                if (z) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    ArrayList<Ayat> arrayList4 = this.ayat;
                    sb.append(arrayList4.get(arrayList4.size() - 1).getAyahNum());
                    sb.append(LanguageTag.SEP);
                    sb.append(this.ayat.get(0).getAyahNum());
                    string = sb.toString();
                } else {
                    if (this.ayat.size() > 1) {
                        for (int size2 = this.ayat.size() - 1; size2 >= 0; size2--) {
                            str = size2 == 0 ? str + this.ayat.get(size2).getAyahNum() : str + this.ayat.get(size2).getAyahNum() + ConstantsBase.PROPERTIES_PARAMS_SEPARATOR;
                        }
                    } else {
                        for (int size3 = this.ayat.size() - 1; size3 >= 0; size3--) {
                            str = size3 == 0 ? str + this.ayat.get(size3).getAyahNum() : str + this.ayat.get(size3).getAyahNum() + ConstantsBase.PROPERTIES_PARAMS_SEPARATOR;
                        }
                    }
                    string = str;
                }
                str = str2 + " " + this.books[this.ayat.get(0).getBookId()] + string + ":" + this.ayat.get(0).getBookNum();
            }
        }
        if (i == 2) {
            ArrayList<Ayat> arrayList5 = this.ayat;
            if (arrayList5 != null && arrayList5.size() > 0) {
                highlightAyat highlightayat = (highlightAyat) new SavePrefs((Activity) this, (Class<?>) highlightAyat.class).load();
                if (highlightayat != null) {
                    arrayList = highlightayat.getAyat();
                } else {
                    arrayList = new ArrayList<>();
                }
                for (int i4 = 0; i4 < this.ayat.size(); i4++) {
                    String nick = this.legendList.get(this.ayat.get(i4).getAyahNum() - 1).getNick();
                    boolean z2 = false;
                    for (int i5 = 0; i5 < arrayList.size(); i5++) {
                        if (arrayList.get(i5).equals(nick)) {
                            arrayList.remove(i5);
                            z2 = true;
                        }
                    }
                    if (!z2) {
                        arrayList.add(nick);
                    }
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
            }
            this.bibleAdapter.notifyDataSetChanged();
            return;
        }
        if (i != 3) {
            if (i == 7) {
                app.copyText(this, str);
                return;
            } else {
                if (i == 10) {
                    app.shareText(this, str);
                    return;
                }
                return;
            }
        }
        if (!this.isOld) {
            this.bookmarkAdapterNew.open();
            int selectedItemPosition = this.spinner.getSelectedItemPosition() + 46;
            if (this.bookmarkAdapterNew.fetchcount() == 0) {
                System.out.println("spliter_empty" + selectedItemPosition + Marker.ANY_NON_NULL_MARKER + this.spinner2.getSelectedItemPosition());
                this.bookmarkAdapterNew.insert(selectedItemPosition + Marker.ANY_NON_NULL_MARKER + this.spinner2.getSelectedItemPosition());
                this.bookmarkAdapterNew.close();
                return;
            }
            System.out.println("spliter_hasone" + selectedItemPosition + Marker.ANY_NON_NULL_MARKER + this.spinner2.getSelectedItemPosition());
            this.bookmarkAdapterNew.deleteall();
            this.bookmarkAdapterNew.insert(selectedItemPosition + Marker.ANY_NON_NULL_MARKER + this.spinner2.getSelectedItemPosition());
            this.bookmarkAdapterNew.close();
            return;
        }
        this.bookmarkAdapterOld.open();
        if (this.bookmarkAdapterOld.fetchcount() == 0) {
            System.out.println("spliter_empty" + this.spinner.getSelectedItemPosition() + Marker.ANY_NON_NULL_MARKER + this.spinner2.getSelectedItemPosition());
            this.bookmarkAdapterOld.insert(this.spinner.getSelectedItemPosition() + Marker.ANY_NON_NULL_MARKER + this.spinner2.getSelectedItemPosition());
            this.bookmarkAdapterOld.close();
            return;
        }
        System.out.println("spliter_hasone" + this.spinner.getSelectedItemPosition() + Marker.ANY_NON_NULL_MARKER + this.spinner2.getSelectedItemPosition());
        this.bookmarkAdapterOld.deleteall();
        this.bookmarkAdapterOld.insert(this.spinner.getSelectedItemPosition() + Marker.ANY_NON_NULL_MARKER + this.spinner2.getSelectedItemPosition());
        this.bookmarkAdapterOld.close();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() throws IllegalStateException {
        this.mp.stop();
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            this.mediaPlayer.stop();
            this.mediaPlayer.reset();
        }
        HwAudioPlayerManager hwAudioPlayerManager = this.mHwAudioPlayerManager;
        if (hwAudioPlayerManager != null) {
            hwAudioPlayerManager.stop();
        }
        super.onDestroy();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() throws IllegalStateException {
        super.onStop();
        this.mp.stop();
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            this.mediaPlayer.stop();
            this.mediaPlayer.reset();
        }
        HwAudioPlayerManager hwAudioPlayerManager = this.mHwAudioPlayerManager;
        if (hwAudioPlayerManager != null) {
            hwAudioPlayerManager.stop();
        }
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
            if (Math.abs(motionEvent.getY() - motionEvent2.getY()) > 250.0f) {
                return false;
            }
            if (motionEvent.getX() - motionEvent2.getX() > 120.0f && Math.abs(f) > 200.0f) {
                Log.d("log", "left");
                if (MainActivity_Bible.this.spinner2.getSelectedItemPosition() > 0 && MainActivity_Bible.this.spinner2.getCount() >= 1) {
                    if (MainActivity_Bible.this.ayat != null && MainActivity_Bible.this.ayat.size() > 0) {
                        for (int i = 0; i < MainActivity_Bible.this.ayat.size(); i++) {
                            MainActivity_Bible.this.ayat.remove(i);
                        }
                        for (int i2 = 0; i2 < MainActivity_Bible.this.current.size(); i2++) {
                            MainActivity_Bible.this.current.remove(i2);
                        }
                        MainActivity_Bible mainActivity_Bible = MainActivity_Bible.this;
                        MainActivity_Bible mainActivity_Bible2 = MainActivity_Bible.this;
                        mainActivity_Bible.bibleAdapter = new Newbible_BibleListAdapter(mainActivity_Bible2, R.layout.row_agpya_item, mainActivity_Bible2.legendList, MainActivity_Bible.this.books, MainActivity_Bible.this.current, -1, MainActivity_Bible.this.safr, MainActivity_Bible.this.isShawahed, MainActivity_Bible.this.spinner2.getSelectedItemPosition());
                        MainActivity_Bible.this.listViewFootballLegend.setAdapter((ListAdapter) MainActivity_Bible.this.bibleAdapter);
                        MainActivity_Bible.this.bibleAdapter.notifyDataSetChanged();
                        ((NestedScrollView2) MainActivity_Bible.this.findViewById(R.id.v_nested)).scrollTo(0, 0);
                        MainActivity_Bible.this.listViewFootballLegend.setSelection(0);
                    }
                    MainActivity_Bible.this.ayat = new ArrayList();
                    MainActivity_Bible.this.current = new ArrayList<>();
                    MainActivity_Bible.this.spinner.getSelectedItemPosition();
                    MainActivity_Bible.this.spinner2.setSelection(MainActivity_Bible.this.spinner2.getSelectedItemPosition() - 1);
                }
                return true;
            }
            if (motionEvent2.getX() - motionEvent.getX() <= 120.0f || Math.abs(f) <= 200.0f) {
                return false;
            }
            Log.d("log", "right");
            if (MainActivity_Bible.this.spinner2.getSelectedItemPosition() < MainActivity_Bible.this.spinner2.getCount() - 1 && MainActivity_Bible.this.spinner2.getCount() > 1) {
                if (MainActivity_Bible.this.ayat != null && MainActivity_Bible.this.ayat.size() > 0) {
                    for (int i3 = 0; i3 < MainActivity_Bible.this.ayat.size(); i3++) {
                        MainActivity_Bible.this.ayat.remove(i3);
                    }
                    for (int i4 = 0; i4 < MainActivity_Bible.this.current.size(); i4++) {
                        MainActivity_Bible.this.current.remove(i4);
                    }
                    MainActivity_Bible mainActivity_Bible3 = MainActivity_Bible.this;
                    MainActivity_Bible mainActivity_Bible4 = MainActivity_Bible.this;
                    mainActivity_Bible3.bibleAdapter = new Newbible_BibleListAdapter(mainActivity_Bible4, R.layout.row_agpya_item, mainActivity_Bible4.legendList, MainActivity_Bible.this.books, MainActivity_Bible.this.current, -1, MainActivity_Bible.this.safr, MainActivity_Bible.this.isShawahed, MainActivity_Bible.this.spinner2.getSelectedItemPosition());
                    MainActivity_Bible.this.listViewFootballLegend.setAdapter((ListAdapter) MainActivity_Bible.this.bibleAdapter);
                    MainActivity_Bible.this.bibleAdapter.notifyDataSetChanged();
                    ((NestedScrollView2) MainActivity_Bible.this.findViewById(R.id.v_nested)).scrollTo(0, 0);
                    MainActivity_Bible.this.listViewFootballLegend.setSelection(0);
                }
                MainActivity_Bible.this.ayat = new ArrayList();
                MainActivity_Bible.this.current = new ArrayList<>();
                System.out.println("next size" + MainActivity_Bible.this.ayat.size());
                MainActivity_Bible.this.spinner.getSelectedItemPosition();
                MainActivity_Bible.this.spinner2.setSelection(MainActivity_Bible.this.spinner2.getSelectedItemPosition() + 1);
            }
            return true;
        }
    }

    private String getEngBibleName() {
        int selectedItemPosition = this.spinner.getSelectedItemPosition() + 1;
        int selectedItemPosition2 = this.spinner2.getSelectedItemPosition() + 1;
        if (!this.isOld) {
            selectedItemPosition += 46;
        }
        if (selectedItemPosition == 1) {
            return "Genesis";
        }
        if (selectedItemPosition == 2) {
            return "Eexodus";
        }
        if (selectedItemPosition == 3) {
            return "Leviticus";
        }
        if (selectedItemPosition == 4) {
            return "Numbers";
        }
        if (selectedItemPosition == 5) {
            return "Deuteronomy";
        }
        if (selectedItemPosition == 6) {
            return "Joshua";
        }
        if (selectedItemPosition == 7) {
            return "Judges";
        }
        if (selectedItemPosition == 8) {
            return "Ruth";
        }
        if (selectedItemPosition == 9) {
            return "Samuel1";
        }
        if (selectedItemPosition == 10) {
            return "Samuel2";
        }
        if (selectedItemPosition == 11) {
            return "Kings1";
        }
        if (selectedItemPosition == 12) {
            return "Kings2";
        }
        if (selectedItemPosition == 13) {
            return "Chronicles1";
        }
        if (selectedItemPosition == 14) {
            return "Chronicles2";
        }
        if (selectedItemPosition == 15) {
            return "Ezra";
        }
        if (selectedItemPosition == 16) {
            return "Nehemiah";
        }
        if (selectedItemPosition == 19) {
            return "Esther";
        }
        if (selectedItemPosition == 20) {
            return "Job";
        }
        if (selectedItemPosition == 21) {
            return "Psalms";
        }
        if (selectedItemPosition == 22) {
            return "Proverbs";
        }
        if (selectedItemPosition == 23) {
            return "Ecclesiastes";
        }
        if (selectedItemPosition == 24) {
            return "Song of songs";
        }
        if (selectedItemPosition == 27) {
            return "Isaiah";
        }
        if (selectedItemPosition == 28) {
            return "Jeremiah";
        }
        if (selectedItemPosition == 29) {
            return "Lamentations";
        }
        if (selectedItemPosition == 33) {
            return "Hosea";
        }
        if (selectedItemPosition == 32) {
            return "Daniel";
        }
        if (selectedItemPosition == 31) {
            return "Ezekiel";
        }
        if (selectedItemPosition == 34) {
            return "Joel";
        }
        if (selectedItemPosition == 35) {
            return "Amos";
        }
        if (selectedItemPosition == 36) {
            return "Obadiah";
        }
        if (selectedItemPosition == 37) {
            return "Jonah";
        }
        if (selectedItemPosition == 38) {
            return "Micah";
        }
        if (selectedItemPosition == 39) {
            return "Nahum";
        }
        if (selectedItemPosition == 40) {
            return "Habakkuk";
        }
        if (selectedItemPosition == 41) {
            return "Zephaniah";
        }
        if (selectedItemPosition == 42) {
            return "Haggai";
        }
        if (selectedItemPosition == 43) {
            return "Zechariah";
        }
        if (selectedItemPosition == 44) {
            return "Malachi";
        }
        if (selectedItemPosition == 47) {
            return "Matthew";
        }
        if (selectedItemPosition == 48) {
            return "Mark";
        }
        if (selectedItemPosition == 49) {
            return "Luke";
        }
        if (selectedItemPosition == 50) {
            return "John";
        }
        if (selectedItemPosition == 51) {
            return "Acts";
        }
        if (selectedItemPosition == 52) {
            return "Romans";
        }
        if (selectedItemPosition == 53) {
            return "Corinthians1";
        }
        if (selectedItemPosition == 54) {
            return "Corinthians2";
        }
        if (selectedItemPosition == 55) {
            return "Galatians";
        }
        if (selectedItemPosition == 56) {
            return "Ephesians";
        }
        if (selectedItemPosition == 57) {
            return "Philippians";
        }
        if (selectedItemPosition == 58) {
            return "Colossians";
        }
        if (selectedItemPosition == 59) {
            return "Thessalonians1";
        }
        if (selectedItemPosition == 60) {
            return "Thessalonians2";
        }
        if (selectedItemPosition == 61) {
            return "Timothy1";
        }
        if (selectedItemPosition == 62) {
            return "Timothy2";
        }
        if (selectedItemPosition == 63) {
            return "Titus";
        }
        if (selectedItemPosition == 64) {
            return "Philemon";
        }
        if (selectedItemPosition == 65) {
            return "Hebrews";
        }
        if (selectedItemPosition == 66) {
            return "James";
        }
        if (selectedItemPosition == 67) {
            return "Peter1";
        }
        if (selectedItemPosition == 68) {
            return "Peter2";
        }
        if (selectedItemPosition == 69) {
            return "John1";
        }
        if (selectedItemPosition == 70) {
            return "John2";
        }
        if (selectedItemPosition == 71) {
            return "John3";
        }
        if (selectedItemPosition == 72) {
            return "Jude";
        }
        if (selectedItemPosition != 73) {
            return "";
        }
        return "Revelation";
    }

    private void getLink() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        String str;
        int selectedItemPosition = this.spinner.getSelectedItemPosition() + 1;
        int selectedItemPosition2 = this.spinner2.getSelectedItemPosition() + 1;
        if (!this.isOld) {
            selectedItemPosition += 46;
        }
        if (selectedItemPosition2 < 10) {
            str = "0" + selectedItemPosition2;
        } else {
            str = "" + selectedItemPosition2;
        }
        String str2 = "https://www.orsozoxi.net/bible_voice/";
        if (selectedItemPosition == 1) {
            str2 = "https://www.orsozoxi.net/bible_voice/01_genesis/genesis" + str + ".mp3";
        } else if (selectedItemPosition == 2) {
            str2 = "https://www.orsozoxi.net/bible_voice/02_exodus/exodus" + str + ".mp3";
        } else if (selectedItemPosition == 3) {
            str2 = "https://www.orsozoxi.net/bible_voice/03_leviticus/leviticus" + str + ".mp3";
        } else if (selectedItemPosition == 4) {
            str2 = "https://www.orsozoxi.net/bible_voice/04_numbers/numbers" + str + ".mp3";
        } else if (selectedItemPosition == 5) {
            str2 = "https://www.orsozoxi.net/bible_voice/05_deuteronomy/deuteronomy" + str + ".mp3";
        } else if (selectedItemPosition == 6) {
            str2 = "https://www.orsozoxi.net/bible_voice/06_joshua/joshua" + str + ".mp3";
        } else if (selectedItemPosition == 7) {
            str2 = "https://www.orsozoxi.net/bible_voice/07_judges/judges" + str + ".mp3";
        } else if (selectedItemPosition == 8) {
            str2 = "https://www.orsozoxi.net/bible_voice/08_ruth/ruth" + str + ".mp3";
        } else if (selectedItemPosition == 9) {
            str2 = "https://www.orsozoxi.net/bible_voice/09_samuel1/1samuel" + str + ".mp3";
        } else if (selectedItemPosition == 10) {
            str2 = "https://www.orsozoxi.net/bible_voice/10_samuel2/2samuel" + str + ".mp3";
        } else if (selectedItemPosition == 11) {
            str2 = "https://www.orsozoxi.net/bible_voice/11_kings1/1kings" + str + ".mp3";
        } else if (selectedItemPosition == 12) {
            str2 = "https://www.orsozoxi.net/bible_voice/12_kings2/2kings" + str + ".mp3";
        } else if (selectedItemPosition == 13) {
            str2 = "https://www.orsozoxi.net/bible_voice/13_chronicles1/1chronicles" + str + ".mp3";
        } else if (selectedItemPosition == 14) {
            str2 = "https://www.orsozoxi.net/bible_voice/14_chronicles2/2chronicles" + str + ".mp3";
        } else if (selectedItemPosition == 15) {
            str2 = "https://www.orsozoxi.net/bible_voice/15_ezra/ezra" + str + ".mp3";
        } else if (selectedItemPosition == 16) {
            str2 = "https://www.orsozoxi.net/bible_voice/16_nehemiah/nehemiah" + str + ".mp3";
        } else if (selectedItemPosition == 19) {
            str2 = "https://www.orsozoxi.net/bible_voice/19_esther/esther" + str + ".mp3";
        } else if (selectedItemPosition == 20) {
            str2 = "https://www.orsozoxi.net/bible_voice/20_job/job" + str + ".mp3";
        } else if (selectedItemPosition == 21) {
            str2 = "https://www.orsozoxi.net/bible_voice/21_psalms/psalms" + str + ".mp3";
        } else if (selectedItemPosition == 22) {
            str2 = "https://www.orsozoxi.net/bible_voice/22_proverbs/proverbs" + str + ".mp3";
        } else if (selectedItemPosition == 23) {
            str2 = "https://www.orsozoxi.net/bible_voice/23_ecclesiastes/ecclesiastes" + str + ".mp3";
        } else if (selectedItemPosition == 24) {
            str2 = "https://www.orsozoxi.net/bible_voice/24_song_of_songs/song_of_songs" + str + ".mp3";
        } else if (selectedItemPosition == 27) {
            str2 = "https://www.orsozoxi.net/bible_voice/27_isaiah/isaiah" + str + ".mp3";
        } else if (selectedItemPosition == 28) {
            str2 = "https://www.orsozoxi.net/bible_voice/28_jeremiah/jeremiah" + str + ".mp3";
        } else if (selectedItemPosition == 29) {
            str2 = "https://www.orsozoxi.net/bible_voice/29_lamentations/lamentations" + str + ".mp3";
        } else if (selectedItemPosition == 33) {
            str2 = "https://www.orsozoxi.net/bible_voice/33_hosea/hosea" + str + ".mp3";
        } else if (selectedItemPosition == 32) {
            str2 = "https://www.orsozoxi.net/bible_voice/32_daniel/daniel" + str + ".mp3";
        } else if (selectedItemPosition == 31) {
            str2 = "https://www.orsozoxi.net/bible_voice/31_ezekiel/ezekiel" + str + ".mp3";
        } else if (selectedItemPosition == 34) {
            str2 = "https://www.orsozoxi.net/bible_voice/34_joel/joel" + str + ".mp3";
        } else if (selectedItemPosition == 35) {
            str2 = "https://www.orsozoxi.net/bible_voice/35_amos/amos" + str + ".mp3";
        } else if (selectedItemPosition == 36) {
            str2 = "https://www.orsozoxi.net/bible_voice/36_obadiah/obadiah" + str + ".mp3";
        } else if (selectedItemPosition == 37) {
            str2 = "https://www.orsozoxi.net/bible_voice/37_jonah/jonah" + str + ".mp3";
        } else if (selectedItemPosition == 38) {
            str2 = "https://www.orsozoxi.net/bible_voice/38_micah/micah" + str + ".mp3";
        } else if (selectedItemPosition == 39) {
            str2 = "https://www.orsozoxi.net/bible_voice/39_nahum/nahum" + str + ".mp3";
        } else if (selectedItemPosition == 40) {
            str2 = "https://www.orsozoxi.net/bible_voice/40_habakkuk/habakkuk" + str + ".mp3";
        } else if (selectedItemPosition == 41) {
            str2 = "https://www.orsozoxi.net/bible_voice/41_zephaniah/zephaniah" + str + ".mp3";
        } else if (selectedItemPosition == 42) {
            str2 = "https://www.orsozoxi.net/bible_voice/42_haggai/haggai" + str + ".mp3";
        } else if (selectedItemPosition == 43) {
            str2 = "https://www.orsozoxi.net/bible_voice/43_zechariah/zechariah" + str + ".mp3";
        } else if (selectedItemPosition == 44) {
            str2 = "https://www.orsozoxi.net/bible_voice/44_malachi/malachi" + str + ".mp3";
        } else if (selectedItemPosition == 47) {
            str2 = "https://www.orsozoxi.net/bible_voice/47_matthew/matthew" + str + ".mp3";
        } else if (selectedItemPosition == 48) {
            str2 = "https://www.orsozoxi.net/bible_voice/48_mark/mark" + str + ".mp3";
        } else if (selectedItemPosition == 49) {
            str2 = "https://www.orsozoxi.net/bible_voice/49_luke/luke" + str + ".mp3";
        } else if (selectedItemPosition == 50) {
            str2 = "https://www.orsozoxi.net/bible_voice/50_john/john" + str + ".mp3";
        } else if (selectedItemPosition == 51) {
            str2 = "https://www.orsozoxi.net/bible_voice/51_acts/acts" + str + ".mp3";
        } else if (selectedItemPosition == 52) {
            str2 = "https://www.orsozoxi.net/bible_voice/52_romans/romans" + str + ".mp3";
        } else if (selectedItemPosition == 53) {
            str2 = "https://www.orsozoxi.net/bible_voice/53_corinthians1/1corinthians" + str + ".mp3";
        } else if (selectedItemPosition == 54) {
            str2 = "https://www.orsozoxi.net/bible_voice/54_corinthians2/2corinthians" + str + ".mp3";
        } else if (selectedItemPosition == 55) {
            str2 = "https://www.orsozoxi.net/bible_voice/55_galatians/galatians" + str + ".mp3";
        } else if (selectedItemPosition == 56) {
            str2 = "https://www.orsozoxi.net/bible_voice/56_ephesians/ephesians" + str + ".mp3";
        } else if (selectedItemPosition == 57) {
            str2 = "https://www.orsozoxi.net/bible_voice/57_philippians/philippians" + str + ".mp3";
        } else if (selectedItemPosition == 58) {
            str2 = "https://www.orsozoxi.net/bible_voice/58_colossians/colossians" + str + ".mp3";
        } else if (selectedItemPosition == 59) {
            str2 = "https://www.orsozoxi.net/bible_voice/59_thessalonians1/1thessalonians" + str + ".mp3";
        } else if (selectedItemPosition == 60) {
            str2 = "https://www.orsozoxi.net/bible_voice/60_thessalonians2/2thessalonians" + str + ".mp3";
        } else if (selectedItemPosition == 61) {
            str2 = "https://www.orsozoxi.net/bible_voice/61_timothy1/1timothy" + str + ".mp3";
        } else if (selectedItemPosition == 62) {
            str2 = "https://www.orsozoxi.net/bible_voice/62_timothy2/2timothy" + str + ".mp3";
        } else if (selectedItemPosition == 63) {
            str2 = "https://www.orsozoxi.net/bible_voice/63_titus/titus" + str + ".mp3";
        } else if (selectedItemPosition == 64) {
            str2 = "https://www.orsozoxi.net/bible_voice/64_philemon/philemon" + str + ".mp3";
        } else if (selectedItemPosition == 65) {
            str2 = "https://www.orsozoxi.net/bible_voice/65_hebrews/hebrews" + str + ".mp3";
        } else if (selectedItemPosition == 66) {
            str2 = "https://www.orsozoxi.net/bible_voice/66_james/james" + str + ".mp3";
        } else if (selectedItemPosition == 67) {
            str2 = "https://www.orsozoxi.net/bible_voice/67_peter1/1peter" + str + ".mp3";
        } else if (selectedItemPosition == 68) {
            str2 = "https://www.orsozoxi.net/bible_voice/68_peter2/2peter" + str + ".mp3";
        } else if (selectedItemPosition == 69) {
            str2 = "https://www.orsozoxi.net/bible_voice/69_john1/1john" + str + ".mp3";
        } else if (selectedItemPosition == 70) {
            str2 = "https://www.orsozoxi.net/bible_voice/70_john2/2john" + str + ".mp3";
        } else if (selectedItemPosition == 71) {
            str2 = "https://www.orsozoxi.net/bible_voice/71_john3/3john" + str + ".mp3";
        } else if (selectedItemPosition == 72) {
            str2 = "https://www.orsozoxi.net/bible_voice/72_jude/jude" + str + ".mp3";
        } else if (selectedItemPosition == 73) {
            str2 = "https://www.orsozoxi.net/bible_voice/73_revelation/revelation" + str + ".mp3";
        }
        if (selectedItemPosition == 17 || selectedItemPosition == 18 || selectedItemPosition == 25 || selectedItemPosition == 26 || selectedItemPosition == 31 || selectedItemPosition == 45 || selectedItemPosition == 46) {
            Toast.makeText(getApplicationContext(), R.string.asfar_second_not_available, 1).show();
            return;
        }
        if (checkinternet()) {
            Log.d("orso", str2);
            MediaPlayer mediaPlayer = this.mp;
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                this.mp.stop();
                this.mp.reset();
                this.playmusic.setBackgroundResource(R.drawable.btn_play);
            }
            if (Orsozoxi.isPlay_Store()) {
                MediaPlayer mediaPlayer2 = this.mediaPlayer;
                if (mediaPlayer2 == null) {
                    if (mediaPlayer2 == null) {
                        this.mediaPlayer = new MediaPlayer();
                        try {
                            findViewById(R.id.btn_listen).setBackgroundResource(R.drawable.btn_pauseheader);
                            findViewById(R.id.progress).setVisibility(0);
                            MediaPlayer mediaPlayer3 = new MediaPlayer();
                            this.mediaPlayer = mediaPlayer3;
                            mediaPlayer3.setDataSource(str2);
                            this.mediaPlayer.setAudioStreamType(3);
                            this.mediaPlayer.prepareAsync();
                            this.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.app.orsozoxi.MainActivity_Bible.6
                                @Override // android.media.MediaPlayer.OnPreparedListener
                                public void onPrepared(MediaPlayer mediaPlayer4) throws IllegalStateException {
                                    if (!MainActivity_Bible.this.backPressed) {
                                        MainActivity_Bible.this.mediaPlayer.start();
                                    }
                                    MainActivity_Bible.this.findViewById(R.id.progress).setVisibility(8);
                                }
                            });
                            return;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        } catch (IllegalArgumentException e2) {
                            e2.printStackTrace();
                            return;
                        } catch (IllegalStateException e3) {
                            e3.printStackTrace();
                            return;
                        } catch (SecurityException e4) {
                            e4.printStackTrace();
                            return;
                        }
                    }
                    return;
                }
                if (mediaPlayer2.isPlaying()) {
                    this.mediaPlayer.stop();
                    findViewById(R.id.btn_listen).setBackgroundResource(R.drawable.btn_playheader);
                    return;
                }
                findViewById(R.id.btn_listen).setBackgroundResource(R.drawable.btn_pauseheader);
                findViewById(R.id.progress).setVisibility(0);
                this.mediaPlayer = new MediaPlayer();
                try {
                    MediaPlayer mediaPlayer4 = new MediaPlayer();
                    this.mediaPlayer = mediaPlayer4;
                    mediaPlayer4.setDataSource(str2);
                    this.mediaPlayer.setAudioStreamType(3);
                    this.mediaPlayer.prepareAsync();
                    this.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.app.orsozoxi.MainActivity_Bible.5
                        @Override // android.media.MediaPlayer.OnPreparedListener
                        public void onPrepared(MediaPlayer mediaPlayer5) throws IllegalStateException {
                            if (!MainActivity_Bible.this.backPressed) {
                                MainActivity_Bible.this.mediaPlayer.start();
                            }
                            MainActivity_Bible.this.findViewById(R.id.progress).setVisibility(8);
                        }
                    });
                    return;
                } catch (IOException e5) {
                    e5.printStackTrace();
                    return;
                } catch (IllegalArgumentException e6) {
                    e6.printStackTrace();
                    return;
                } catch (IllegalStateException e7) {
                    e7.printStackTrace();
                    return;
                } catch (SecurityException e8) {
                    e8.printStackTrace();
                    return;
                }
            }
            createHwAudioManager();
            HwAudioPlayerManager hwAudioPlayerManager = this.mHwAudioPlayerManager;
            if (hwAudioPlayerManager != null) {
                if (hwAudioPlayerManager.isPlaying()) {
                    this.mHwAudioPlayerManager.stop();
                    findViewById(R.id.btn_listen).setBackgroundResource(R.drawable.btn_playheader);
                    return;
                }
                HwAudioPlayItem hwAudioPlayItem = new HwAudioPlayItem();
                hwAudioPlayItem.setAudioTitle(this.spinner.getSelectedItem().toString() + " " + this.spinner2.getSelectedItem().toString());
                hwAudioPlayItem.setAudioId(String.valueOf(str2.hashCode()));
                hwAudioPlayItem.setOnline(1);
                hwAudioPlayItem.setOnlinePath(str2);
                ArrayList arrayList = new ArrayList();
                arrayList.add(hwAudioPlayItem);
                this.mHwAudioPlayerManager.playList(arrayList, 0, 0);
                findViewById(R.id.btn_listen).setBackgroundResource(R.drawable.btn_pauseheader);
                return;
            }
            return;
        }
        new AlertDialogManager().showAlertDialog(this, "No Internet Connection", getString(R.string.no_internet), false);
    }

    public void createHwAudioManager() throws IllegalArgumentException {
        HwAudioManagerFactory.createHwAudioManager(new HwAudioPlayerConfig(this), new HwAudioConfigCallBack() { // from class: com.app.orsozoxi.MainActivity_Bible.7
            @Override // com.huawei.hms.audiokit.player.callback.HwAudioConfigCallBack
            public void onSuccess(HwAudioManager hwAudioManager) {
                try {
                    Log.i(Util.TAG, "createHwAudioManager onSuccess");
                    MainActivity_Bible.this.mHwAudioPlayerManager = hwAudioManager.getPlayerManager();
                    MainActivity_Bible.this.mHwAudioConfigManager = hwAudioManager.getConfigManager();
                    MainActivity_Bible.this.mHwAudioQueueManager = hwAudioManager.getQueueManager();
                    MainActivity_Bible.this.mHwAudioConfigManager.setNotificationFactory(new SubINotificationFactory((Application) MainActivity_Bible.this.getApplicationContext(), hwAudioManager, (Button) MainActivity_Bible.this.findViewById(R.id.btn_listen)));
                } catch (Exception unused) {
                    Log.i(Util.TAG, "player init fail");
                }
            }

            @Override // com.huawei.hms.audiokit.player.callback.HwAudioConfigCallBack
            public void onError(int i) {
                Log.w(Util.TAG, "init err:" + i);
            }
        });
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    public boolean checkinternet() {
        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
        this.cd = connectionDetector;
        return connectionDetector.isConnectingToInternet();
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

    private void manageData() {
        Log.d(RemoteMessageConst.Notification.TAG, "id=" + this.tafserBookId);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.tafser));
        builder.setMessage(getString(R.string.download_tafser_question));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() { // from class: com.app.orsozoxi.MainActivity_Bible.8
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                if (MainActivity_Bible.this.checkinternet()) {
                    MainActivity_Bible.this.mProgressDialog = new ProgressDialog(MainActivity_Bible.this);
                    MainActivity_Bible.this.mProgressDialog.setMessage(MainActivity_Bible.this.getString(R.string.download_tafser));
                    MainActivity_Bible.this.mProgressDialog.setCancelable(false);
                    MainActivity_Bible.this.mProgressDialog.show();
                    MainActivity_Bible.this.downloadHTMLFileAsync();
                    return;
                }
                Toast.makeText(MainActivity_Bible.this.getApplicationContext(), R.string.no_internet, 1).show();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: com.app.orsozoxi.MainActivity_Bible.9
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void downloadHTMLFileAsync() {
        Observable.fromCallable(new Callable() { // from class: com.app.orsozoxi.-$$Lambda$MainActivity_Bible$8wmna8MvebDglM6IXjMaS997ofc
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$downloadHTMLFileAsync$0$MainActivity_Bible();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.orsozoxi.-$$Lambda$MainActivity_Bible$o8rqx3mUdE7cBlIBhPpjbWOk4RM
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$downloadHTMLFileAsync$1$MainActivity_Bible((Boolean) obj);
            }
        });
    }

    public /* synthetic */ Boolean lambda$downloadHTMLFileAsync$0$MainActivity_Bible() throws Exception {
        File file;
        try {
            Log.d("test", this.downloadPath + this.tafserBookId + "/" + this.numOfChapters + ".xml");
            URLConnection uRLConnectionOpenConnection = new URL(this.downloadPath + this.tafserBookId + "/" + this.numOfChapters + ".xml").openConnection();
            uRLConnectionOpenConnection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            uRLConnectionOpenConnection.setRequestProperty("Expect", HTTP.EXPECT_CONTINUE);
            uRLConnectionOpenConnection.setDoOutput(true);
            uRLConnectionOpenConnection.connect();
            Log.d("orso", "HTML download");
            File file2 = new File(CommonUtilities.getStoragePath(this) + "/Orsozoxi/");
            if (!file2.exists()) {
                file2.mkdirs();
            }
            File file3 = new File(CommonUtilities.getStoragePath(this) + "/Orsozoxi/tafser/");
            if (!file3.exists()) {
                file3.mkdirs();
            }
            if (this.isOld) {
                File file4 = new File(CommonUtilities.getStoragePath(this) + "/Orsozoxi/tafser/old/");
                if (!file4.exists()) {
                    file4.mkdirs();
                }
                file = new File(CommonUtilities.getStoragePath(this) + "/Orsozoxi/tafser/old/" + this.tafserBookId);
            } else {
                file = new File(CommonUtilities.getStoragePath(this) + "/Orsozoxi/tafser/" + this.tafserBookId);
            }
            if (!file.exists()) {
                file.mkdirs();
            }
            File file5 = new File(file, this.numOfChapters + ".xml");
            this.fileWithinMyDirHTML = file5;
            if (file5.exists()) {
                this.fileWithinMyDirHTML.delete();
                this.fileWithinMyDirHTML = new File(file, this.numOfChapters + ".xml");
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

    public /* synthetic */ void lambda$downloadHTMLFileAsync$1$MainActivity_Bible(Boolean bool) throws Exception {
        File file;
        String str;
        String[] strArrSplit;
        String str2 = "";
        if (this.numOfChapters < this.spinner2.getCount()) {
            this.numOfChapters++;
            downloadHTMLFileAsync();
            return;
        }
        this.numOfChapters = 1;
        this.mProgressDialog.dismiss();
        ArrayList arrayList = new ArrayList();
        if (this.isOld) {
            file = new File(CommonUtilities.getStoragePath(this) + "/Orsozoxi/tafser/old/" + this.tafserBookId + "/" + this.ayat.get(0).getBookNum() + ".xml");
        } else {
            file = new File(CommonUtilities.getStoragePath(this) + "/Orsozoxi/tafser/" + this.tafserBookId + "/" + this.ayat.get(0).getBookNum() + ".xml");
        }
        if (file.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "utf-8"));
                str = "";
                while (true) {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        str = str + line + "\n";
                    } catch (IOException e) {
                        e = e;
                        e.printStackTrace();
                        strArrSplit = str.split("\n");
                        if (strArrSplit != null) {
                            return;
                        } else {
                            return;
                        }
                    }
                }
                Log.d("orso3", str);
            } catch (IOException e2) {
                e = e2;
                str = "";
            }
            strArrSplit = str.split("\n");
            if (strArrSplit != null || strArrSplit.length <= 0) {
                return;
            }
            String str3 = "";
            for (int i = 0; i < strArrSplit.length; i++) {
                if (strArrSplit[i].equals(Marker.ANY_NON_NULL_MARKER)) {
                    arrayList.add(str3);
                    str3 = "";
                } else {
                    str3 = str3 + strArrSplit[i] + "\n";
                }
            }
            ArrayList<String> arrayList2 = new ArrayList<>();
            for (int i2 = 0; i2 < this.ayat.size(); i2++) {
                boolean z = false;
                for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                    if (this.ayat.get(i2).getAyahNum() - 1 < arrayList.size() && arrayList2.get(i3).equals(arrayList.get(this.ayat.get(i2).getAyahNum() - 1))) {
                        z = true;
                    }
                }
                if (!z && this.ayat.get(i2).getAyahNum() - 1 < arrayList.size()) {
                    arrayList2.add((String) arrayList.get(this.ayat.get(i2).getAyahNum() - 1));
                }
            }
            for (int i4 = 0; i4 < arrayList2.size(); i4++) {
                str2 = str2 + arrayList2.get(i4) + "\n";
            }
            Intent intent = new Intent(this, (Class<?>) TafserActivity.class);
            intent.putExtra("tafser", str2);
            intent.putStringArrayListExtra("tafserList", arrayList2);
            intent.putExtra("title", getString(R.string.tafser) + " " + this.spinner.getSelectedItem().toString() + " " + this.spinner2.getSelectedItem().toString());
            startActivity(intent);
        }
    }

    public void playSong() throws IllegalStateException {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            this.mediaPlayer.stop();
            this.mediaPlayer.reset();
            findViewById(R.id.btn_listen).setBackgroundResource(R.drawable.btn_playheader);
        }
        HwAudioPlayerManager hwAudioPlayerManager = this.mHwAudioPlayerManager;
        if (hwAudioPlayerManager != null && hwAudioPlayerManager.isPlaying()) {
            this.mHwAudioPlayerManager.stop();
            findViewById(R.id.btn_listen).setBackgroundResource(R.drawable.btn_playheader);
        }
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

    public void getBookChapters(int i) throws IOException {
        InputStream inputStreamOpen;
        try {
            if (this.isOld) {
                inputStreamOpen = getAssets().open("mybible/old/OldAsfarName.txt");
            } else {
                inputStreamOpen = getAssets().open("mybible/new/NewAsfarName.txt");
            }
            byte[] bArr = new byte[inputStreamOpen.available()];
            inputStreamOpen.read(bArr);
            String[] strArrSplit = new String(bArr).split("\n");
            for (int i2 = 0; i2 < strArrSplit.length; i2++) {
                if (i2 == i) {
                    String[] strArrSplit2 = strArrSplit[i2].split("\\s{2,}");
                    this.bookPath = strArrSplit2[2];
                    this.bookPathChapters = Integer.parseInt(strArrSplit2[1]);
                }
            }
            inputStreamOpen.close();
        } catch (IOException unused) {
        }
    }

    public List<Bible> getUpdatelist(int i, int i2) throws net.sqlcipher.SQLException {
        try {
            SQLiteDatabase.loadLibs(getApplicationContext());
            new UseDatabaseHandler(this);
            UseDatabaseHandler useDatabaseHandler = UseDatabaseHandler.getInstance(this);
            useDatabaseHandler.open(app.getSqKey(this));
            List<BibleDataBase> chapter = useDatabaseHandler.getChapter(i, i2);
            useDatabaseHandler.close();
            if (chapter != null) {
                for (int i3 = 0; i3 < chapter.size(); i3++) {
                    this.legendList.add(new Bible(String.valueOf(i3), chapter.get(i3).getAyah()));
                }
            }
            return this.legendList;
        } catch (SQLException e) {
            e.printStackTrace();
            return this.legendList;
        }
    }

    public void updatelistOld(int i, int i2) throws IOException {
        InputStream inputStreamOpen;
        int i3 = this.counterr;
        if (i3 == 0) {
            this.counterr = i3 + 1;
        } else {
            this.legendList.clear();
        }
        AssetManager assets = getAssets();
        InputStream inputStream = null;
        int i4 = i2 + 1;
        try {
            try {
                String str = "";
                if (this.isOld) {
                    if (i == 0) {
                        str = "mybible/old/01-Takwen/" + i4 + ".txt";
                    } else if (i == 1) {
                        str = "mybible/old/02-Khrog/" + i4 + ".txt";
                    } else if (i == 2) {
                        str = "mybible/old/03-Laween/" + i4 + ".txt";
                    } else if (i == 3) {
                        str = "mybible/old/04-Addad/" + i4 + ".txt";
                    } else if (i == 4) {
                        str = "mybible/old/05-Tasnya/" + i4 + ".txt";
                    } else if (i == 5) {
                        str = "mybible/old/06-Yasho3/" + i4 + ".txt";
                    } else if (i == 6) {
                        str = "mybible/old/07-Kodah/" + i4 + ".txt";
                    } else if (i == 7) {
                        str = "mybible/old/08-Ra3os/" + i4 + ".txt";
                    } else if (i == 8) {
                        str = "mybible/old/09-Samuel1/" + i4 + ".txt";
                    } else if (i == 9) {
                        str = "mybible/old/10-Samuel2/" + i4 + ".txt";
                    } else if (i == 10) {
                        str = "mybible/old/11-Mlokk1/" + i4 + ".txt";
                    } else if (i == 11) {
                        str = "mybible/old/12-Mlokk2/" + i4 + ".txt";
                    } else if (i == 12) {
                        str = "mybible/old/13-AkhbarAyam1/" + i4 + ".txt";
                    } else if (i == 13) {
                        str = "mybible/old/14-AkhbarAyam2/" + i4 + ".txt";
                    } else if (i == 14) {
                        str = "mybible/old/15-Ezra/" + i4 + ".txt";
                    } else if (i == 15) {
                        str = "mybible/old/16-Na7amya/" + i4 + ".txt";
                    } else if (i == 16) {
                        str = "mybible/old/17-Tobya/" + i4 + ".txt";
                    } else if (i == 17) {
                        str = "mybible/old/18-Yahodet/" + i4 + ".txt";
                    } else if (i == 18) {
                        str = "mybible/old/19-Aster/" + i4 + ".txt";
                    } else if (i == 19) {
                        str = "mybible/old/20-Ayob/" + i4 + ".txt";
                    } else if (i == 20) {
                        str = "mybible/old/21-Psalms/" + i4 + ".txt";
                    } else if (i == 21) {
                        str = "mybible/old/22-Amsal/" + i4 + ".txt";
                    } else if (i == 22) {
                        str = "mybible/old/23-Gam3a/" + i4 + ".txt";
                    } else if (i == 23) {
                        str = "mybible/old/24-NashedElAnshad/" + i4 + ".txt";
                    } else if (i == 24) {
                        str = "mybible/old/25-7ekma/" + i4 + ".txt";
                    } else if (i == 25) {
                        str = "mybible/old/26-Yasho3EbnSira5/" + i4 + ".txt";
                    } else if (i == 26) {
                        str = "mybible/old/27-Ash3ya/" + i4 + ".txt";
                    } else if (i == 27) {
                        str = "mybible/old/28-Armya/" + i4 + ".txt";
                    } else if (i == 28) {
                        str = "mybible/old/29-MarasyArmya/" + i4 + ".txt";
                    } else if (i == 29) {
                        str = "mybible/old/30-Baroo5/" + i4 + ".txt";
                    } else if (i == 30) {
                        str = "mybible/old/31-Ezkial/" + i4 + ".txt";
                    } else if (i == 31) {
                        str = "mybible/old/32-Daniel/" + i4 + ".txt";
                    } else if (i == 32) {
                        str = "mybible/old/33-Hosa3/" + i4 + ".txt";
                    } else if (i == 33) {
                        str = "mybible/old/34-Uoyel/" + i4 + ".txt";
                    } else if (i == 34) {
                        str = "mybible/old/35-Amous/" + i4 + ".txt";
                    } else if (i == 35) {
                        str = "mybible/old/36-Obadiah/" + i4 + ".txt";
                    } else if (i == 36) {
                        str = "mybible/old/37-Younan/" + i4 + ".txt";
                    } else if (i == 37) {
                        str = "mybible/old/38-Mikha/" + i4 + ".txt";
                    } else if (i == 38) {
                        str = "mybible/old/39-Na7oum/" + i4 + ".txt";
                    } else if (i == 39) {
                        str = "mybible/old/40-Habakouk/" + i4 + ".txt";
                    } else if (i == 40) {
                        str = "mybible/old/41-Saphnia/" + i4 + ".txt";
                    } else if (i == 41) {
                        str = "mybible/old/42-Heggi/" + i4 + ".txt";
                    } else if (i == 42) {
                        str = "mybible/old/43-Zakaria/" + i4 + ".txt";
                    } else if (i == 43) {
                        str = "mybible/old/44-Malakhy/" + i4 + ".txt";
                    } else if (i == 44) {
                        str = "mybible/old/45-Makabyeen1/" + i4 + ".txt";
                    } else if (i == 45) {
                        str = "mybible/old/46-Makabyeen2/" + i4 + ".txt";
                    }
                    inputStreamOpen = assets.open(str);
                } else {
                    Log.d("bookind=", "boo=" + i);
                    if (i == 46) {
                        str = "mybible/new/47-Matta/" + i4 + ".txt";
                    } else if (i == 47) {
                        str = "mybible/new/48-Morkos/" + i4 + ".txt";
                    } else if (i == 48) {
                        str = "mybible/new/49-Loka/" + i4 + ".txt";
                    } else if (i == 49) {
                        str = "mybible/new/50-Yo7ana/" + i4 + ".txt";
                    } else if (i == 50) {
                        str = "mybible/new/51-A3mal/" + i4 + ".txt";
                    } else if (i == 51) {
                        str = "mybible/new/52-Romia/" + i4 + ".txt";
                    } else if (i == 52) {
                        str = "mybible/new/53-Koronsos1/" + i4 + ".txt";
                    } else if (i == 53) {
                        str = "mybible/new/54-Koronsos2/" + i4 + ".txt";
                    } else if (i == 54) {
                        str = "mybible/new/55-Ghalatia/" + i4 + ".txt";
                    } else if (i == 55) {
                        str = "mybible/new/56-Afasos/" + i4 + ".txt";
                    } else if (i == 56) {
                        str = "mybible/new/57-Philipy/" + i4 + ".txt";
                    } else if (i == 57) {
                        str = "mybible/new/58-Kolosy/" + i4 + ".txt";
                    } else if (i == 58) {
                        str = "mybible/new/59-Tsaloneky1/" + i4 + ".txt";
                    } else if (i == 59) {
                        str = "mybible/new/60-Tsaloneky2/" + i4 + ".txt";
                    } else if (i == 60) {
                        str = "mybible/new/61-Timosawes1/" + i4 + ".txt";
                    } else if (i == 61) {
                        str = "mybible/new/62-Timosawes2/" + i4 + ".txt";
                    } else if (i == 62) {
                        str = "mybible/new/63-Titues/" + i4 + ".txt";
                    } else if (i == 63) {
                        str = "mybible/new/64-Philimon/" + i4 + ".txt";
                    } else if (i == 64) {
                        str = "mybible/new/65-Abranyen/" + i4 + ".txt";
                    } else if (i == 65) {
                        str = "mybible/new/66-Yaquob/" + i4 + ".txt";
                    } else if (i == 66) {
                        str = "mybible/new/67-Botros1/" + i4 + ".txt";
                    } else if (i == 67) {
                        str = "mybible/new/68-Botros2/" + i4 + ".txt";
                    } else if (i == 68) {
                        str = "mybible/new/69-You7ana1/" + i4 + ".txt";
                    } else if (i == 69) {
                        str = "mybible/new/70-You7ana2/" + i4 + ".txt";
                    } else if (i == 70) {
                        str = "mybible/new/71-You7ana3/" + i4 + ".txt";
                    } else if (i == 71) {
                        str = "mybible/new/72-Yahouza/" + i4 + ".txt";
                    } else if (i == 72) {
                        str = "mybible/new/73-RoyaYou7ana/" + i4 + ".txt";
                    }
                    inputStreamOpen = assets.open(str);
                }
                inputStream = inputStreamOpen;
                String[] strArrSplit = loadTextFile(inputStream).split("\n");
                for (int i5 = 0; i5 < strArrSplit.length; i5++) {
                    Log.d("orso", "ayah=" + strArrSplit[i5]);
                    this.legendList.add(new Bible(String.valueOf(i5), strArrSplit[i5].trim()));
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (inputStream != null) {
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException unused) {
                }
            }
            Newbible_BibleListAdapter newbible_BibleListAdapter = new Newbible_BibleListAdapter(this, R.layout.activity_agpya, this.legendList, this.books, this.current, this.currentayah, this.safr, this.isShawahed, this.spinner2.getSelectedItemPosition());
            this.bibleAdapter = newbible_BibleListAdapter;
            this.listViewFootballLegend.setAdapter((ListAdapter) newbible_BibleListAdapter);
            this.bibleAdapter.notifyDataSetChanged();
            this.listViewFootballLegend.setSelection(0);
            NestedScrollView2 nestedScrollView2 = (NestedScrollView2) findViewById(R.id.v_nested);
            nestedScrollView2.scrollTo(0, 0);
            WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs((Activity) this, (Class<?>) WhiteBackgroundActivition.class).load();
            if (whiteBackgroundActivition != null) {
                if (!whiteBackgroundActivition.isWhiteBackground()) {
                    nestedScrollView2.setBackgroundResource(R.drawable.background);
                    return;
                } else {
                    this.shawahedCheckBox.setTextColor(Color.parseColor("#000000"));
                    nestedScrollView2.setBackgroundColor(Color.parseColor("#ffffff"));
                    return;
                }
            }
            nestedScrollView2.setBackgroundResource(R.drawable.background);
        } finally {
        }
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

    /* JADX INFO: Access modifiers changed from: private */
    public void init() {
        this.loadingDialog = ApplicationDialogs.showLoadingDialog(this, getString(R.string.plz_wait));
        Observable.fromCallable(new Callable() { // from class: com.app.orsozoxi.-$$Lambda$MainActivity_Bible$vI5tt788mRijDqaGW_khAZJsDow
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$init$2$MainActivity_Bible();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.orsozoxi.-$$Lambda$MainActivity_Bible$QhSRn7yD1hMoRUnBm_JHro0LKlA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$init$3$MainActivity_Bible((Boolean) obj);
            }
        });
    }

    public /* synthetic */ Boolean lambda$init$2$MainActivity_Bible() throws Exception {
        this.safr = null;
        readExcelFileType(getApplicationContext(), this.selectedBookIDSH);
        return false;
    }

    public /* synthetic */ void lambda$init$3$MainActivity_Bible(Boolean bool) throws Exception {
        ApplicationDialogs.dismissLoadingDialog(this.loadingDialog);
        Newbible_BibleListAdapter newbible_BibleListAdapter = new Newbible_BibleListAdapter(this, R.layout.row_agpya_item, this.legendList, this.books, this.current, this.currentayah, this.safr, this.isShawahed, this.spinner2.getSelectedItemPosition());
        this.bibleAdapter = newbible_BibleListAdapter;
        this.listViewFootballLegend.setAdapter((ListAdapter) newbible_BibleListAdapter);
        this.bibleAdapter.notifyDataSetChanged();
        ((NestedScrollView2) findViewById(R.id.v_nested)).scrollTo(0, 0);
    }

    private void readExcelFileType(Context context, int i) {
        int i2;
        AyahShwahed ayahShwahed;
        As7a7WithShwahed as7a7WithShwahed;
        ArrayList<AyahShwahed> arrayList;
        String str;
        String str2;
        boolean z;
        String str3;
        String str4;
        String strSubstring;
        String strSubstring2;
        int iReturnInt;
        String str5 = ":";
        String str6 = LanguageTag.SEP;
        try {
            Iterator<Row> itRowIterator = new HSSFWorkbook(new POIFSFileSystem(context.getAssets().open("shawahed/" + i + ".xls"))).getSheetAt(0).rowIterator();
            this.safr = new SafrWithShwahed();
            AyahShwahed ayahShwahed2 = new AyahShwahed();
            As7a7WithShwahed as7a7WithShwahed2 = new As7a7WithShwahed();
            new Shahed();
            ArrayList<AyahShwahed> arrayList2 = new ArrayList<>();
            ArrayList<As7a7WithShwahed> arrayList3 = new ArrayList<>();
            ArrayList<Shahed> arrayList4 = new ArrayList<>();
            String string = "";
            int i3 = -1;
            int iReturnInt2 = -1;
            boolean z2 = false;
            int i4 = -1;
            while (itRowIterator.hasNext()) {
                Iterator<Cell> itCellIterator = ((HSSFRow) itRowIterator.next()).cellIterator();
                int iReturnInt3 = i4;
                while (itCellIterator.hasNext()) {
                    HSSFCell hSSFCell = (HSSFCell) itCellIterator.next();
                    Iterator<Row> it2 = itRowIterator;
                    String strDecodeText = app.decodeText(this, "orsozoxi".getBytes(), hSSFCell.toString());
                    String str7 = str6;
                    if (hSSFCell.getColumnIndex() == 0 && !strDecodeText.toString().equals(null) && !strDecodeText.toString().equals("")) {
                        strDecodeText.toString().length();
                        if (iReturnInt2 == -1) {
                            iReturnInt2 = returnInt(strDecodeText.toString().trim());
                            as7a7WithShwahed2 = new As7a7WithShwahed();
                            as7a7WithShwahed2.setAs7a7Id(iReturnInt2);
                        } else if (iReturnInt2 != returnInt(strDecodeText.toString().trim())) {
                            iReturnInt3 = returnInt(strDecodeText.toString().trim());
                            z2 = true;
                        }
                    } else {
                        if (hSSFCell.getColumnIndex() == 1 && !strDecodeText.toString().equals(null) && !strDecodeText.toString().equals("")) {
                            if (i3 == -1) {
                                iReturnInt = returnInt(strDecodeText.toString().trim());
                                ayahShwahed2 = new AyahShwahed();
                                ayahShwahed2.setAs7a7Id(iReturnInt2);
                                ayahShwahed2.setAyahId(iReturnInt);
                                arrayList4 = new ArrayList<>();
                            } else if (i3 == returnInt(strDecodeText.toString().trim())) {
                                i2 = iReturnInt3;
                                ayahShwahed = ayahShwahed2;
                                as7a7WithShwahed = as7a7WithShwahed2;
                                arrayList = arrayList2;
                                str3 = str7;
                                str4 = str5;
                            } else {
                                iReturnInt = returnInt(strDecodeText.toString().trim());
                                ayahShwahed2.setShwahed(arrayList4);
                                arrayList2.add(ayahShwahed2);
                                ayahShwahed2 = new AyahShwahed();
                                ayahShwahed2.setAs7a7Id(iReturnInt2);
                                ayahShwahed2.setAyahId(iReturnInt);
                                arrayList4 = new ArrayList<>();
                                if (z2) {
                                    as7a7WithShwahed2.setAyat(arrayList2);
                                    ArrayList<AyahShwahed> arrayList5 = new ArrayList<>();
                                    arrayList3.add(as7a7WithShwahed2);
                                    as7a7WithShwahed2 = new As7a7WithShwahed();
                                    as7a7WithShwahed2.setAs7a7Id(iReturnInt3);
                                    i3 = iReturnInt;
                                    iReturnInt2 = iReturnInt3;
                                    arrayList = arrayList5;
                                    str3 = str7;
                                    z2 = false;
                                    str4 = str5;
                                    str6 = str3;
                                    itRowIterator = it2;
                                    str5 = str4;
                                    arrayList2 = arrayList;
                                }
                            }
                            i3 = iReturnInt;
                        } else if (hSSFCell.getColumnIndex() == 2) {
                            string = strDecodeText.toString();
                        } else {
                            if (strDecodeText.toString().equals(null) || strDecodeText.toString().equals("") || strDecodeText.toString().length() <= 0) {
                                i2 = iReturnInt3;
                                ayahShwahed = ayahShwahed2;
                                as7a7WithShwahed = as7a7WithShwahed2;
                                arrayList = arrayList2;
                            } else {
                                Shahed shahed = new Shahed();
                                shahed.setWord(string);
                                shahed.setFillShahed(strDecodeText.toString());
                                String strReplace = strDecodeText.toString().trim().replaceAll(" ", "").replace("(", "").replace(")", "");
                                i2 = iReturnInt3;
                                int i5 = 0;
                                while (true) {
                                    ayahShwahed = ayahShwahed2;
                                    if (i5 >= this.specialSafrs.size()) {
                                        as7a7WithShwahed = as7a7WithShwahed2;
                                        arrayList = arrayList2;
                                        str = strReplace;
                                        str2 = "";
                                        z = false;
                                        break;
                                    }
                                    if (strReplace == null || strReplace.equals(null)) {
                                        as7a7WithShwahed = as7a7WithShwahed2;
                                        arrayList = arrayList2;
                                    } else {
                                        if (strReplace != null) {
                                            as7a7WithShwahed = as7a7WithShwahed2;
                                            if (strReplace.length() >= this.specialSafrs.get(i5).getSpecial().length()) {
                                                arrayList = arrayList2;
                                                if (this.specialSafrs.get(i5).getSpecial().equals(strReplace.substring(0, this.specialSafrs.get(i5).getSpecial().length()))) {
                                                    shahed.setSafrId(this.specialSafrs.get(i5).getSafrId());
                                                    Log.d("newbible", strReplace);
                                                    strSubstring = strReplace.substring(0, this.specialSafrs.get(i5).getSpecial().length());
                                                    strSubstring2 = strReplace.substring(this.specialSafrs.get(i5).getSpecial().length());
                                                    break;
                                                }
                                            }
                                            if (strReplace != null && this.specialSafrs.get(i5).getSpecial2() != null && strReplace.length() >= this.specialSafrs.get(i5).getSpecial2().length() && this.specialSafrs.get(i5).getSpecial2() != null && this.specialSafrs.get(i5).getSpecial2().equals(strReplace.substring(0, this.specialSafrs.get(i5).getSpecial2().length()))) {
                                                shahed.setSafrId(this.specialSafrs.get(i5).getSafrId());
                                                Log.d("newbible", strReplace);
                                                strSubstring = strReplace.substring(0, this.specialSafrs.get(i5).getSpecial2().length());
                                                strSubstring2 = strReplace.substring(this.specialSafrs.get(i5).getSpecial2().length());
                                                break;
                                            }
                                        } else {
                                            as7a7WithShwahed = as7a7WithShwahed2;
                                        }
                                        arrayList = arrayList2;
                                        if (strReplace != null) {
                                            continue;
                                        }
                                    }
                                    i5++;
                                    ayahShwahed2 = ayahShwahed;
                                    as7a7WithShwahed2 = as7a7WithShwahed;
                                    arrayList2 = arrayList;
                                }
                                str2 = strSubstring;
                                str = strSubstring2;
                                z = true;
                                if (z) {
                                    Log.d("newbible", str);
                                    if (str.contains(str5)) {
                                        String[] strArrSplit = str.split(str5);
                                        if (strArrSplit.length != 1 && strArrSplit.length <= 2) {
                                            str3 = str7;
                                            str4 = str5;
                                            if (!strArrSplit[1].contains(str3) || (!strArrSplit[1].contains("و") && !strArrSplit[1].contains("،"))) {
                                                if (strArrSplit[1].contains(str3)) {
                                                    try {
                                                        shahed.setAs7a7Id(Integer.parseInt(strArrSplit[0]));
                                                        String[] strArrSplit2 = strArrSplit[1].split(str3);
                                                        ArrayList<Integer> arrayList6 = new ArrayList<>();
                                                        arrayList6.add(Integer.valueOf(Integer.parseInt(strArrSplit2[0])));
                                                        if (strArrSplit2.length > 1) {
                                                            arrayList6.add(Integer.valueOf(Integer.parseInt(strArrSplit2[1])));
                                                            shahed.setAyat(arrayList6);
                                                            shahed.setStatus(1);
                                                            shahed.setFillShahed(str2 + strArrSplit[0] + " :" + strArrSplit2[0] + " - " + strArrSplit2[1]);
                                                            arrayList4.add(shahed);
                                                        }
                                                    } catch (NumberFormatException unused) {
                                                    }
                                                } else if (strArrSplit[1].contains("و")) {
                                                    shahed.setAs7a7Id(Integer.parseInt(strArrSplit[0]));
                                                    String[] strArrSplit3 = strArrSplit[1].split("و");
                                                    ArrayList<Integer> arrayList7 = new ArrayList<>();
                                                    for (String str8 : strArrSplit3) {
                                                        arrayList7.add(Integer.valueOf(Integer.parseInt(str8)));
                                                    }
                                                    shahed.setAyat(arrayList7);
                                                    shahed.setStatus(2);
                                                    arrayList4.add(shahed);
                                                } else if (strArrSplit[1].contains("،")) {
                                                    shahed.setAs7a7Id(Integer.parseInt(strArrSplit[0]));
                                                    String[] strArrSplit4 = strArrSplit[1].split("،");
                                                    ArrayList<Integer> arrayList8 = new ArrayList<>();
                                                    for (String str9 : strArrSplit4) {
                                                        arrayList8.add(Integer.valueOf(Integer.parseInt(str9)));
                                                    }
                                                    shahed.setAyat(arrayList8);
                                                    shahed.setStatus(2);
                                                    arrayList4.add(shahed);
                                                } else {
                                                    try {
                                                        shahed.setAs7a7Id(Integer.parseInt(strArrSplit[0]));
                                                        ArrayList<Integer> arrayList9 = new ArrayList<>();
                                                        arrayList9.add(Integer.valueOf(Integer.parseInt(strArrSplit[1])));
                                                        shahed.setAyat(arrayList9);
                                                        shahed.setStatus(0);
                                                        arrayList4.add(shahed);
                                                    } catch (NumberFormatException unused2) {
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        str3 = str7;
                                        str4 = str5;
                                        shahed.setAs7a7Id(Integer.parseInt(str));
                                        shahed.setStatus(3);
                                    }
                                }
                                str6 = str3;
                                itRowIterator = it2;
                                str5 = str4;
                                arrayList2 = arrayList;
                            }
                            str3 = str7;
                            str4 = str5;
                        }
                        iReturnInt3 = i2;
                        ayahShwahed2 = ayahShwahed;
                        as7a7WithShwahed2 = as7a7WithShwahed;
                        str6 = str3;
                        itRowIterator = it2;
                        str5 = str4;
                        arrayList2 = arrayList;
                    }
                    arrayList = arrayList2;
                    str3 = str7;
                    str4 = str5;
                    str6 = str3;
                    itRowIterator = it2;
                    str5 = str4;
                    arrayList2 = arrayList;
                }
                i4 = iReturnInt3;
            }
            ayahShwahed2.setShwahed(arrayList4);
            arrayList2.add(ayahShwahed2);
            as7a7WithShwahed2.setAyat(arrayList2);
            new ArrayList();
            arrayList3.add(as7a7WithShwahed2);
            new As7a7WithShwahed();
            this.safr.setAs7a7at(arrayList3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readExcelFileTypeExcelBeforeEncryption(Context context, int i) {
        Iterator<Row> it2;
        int i2;
        AyahShwahed ayahShwahed;
        As7a7WithShwahed as7a7WithShwahed;
        ArrayList<AyahShwahed> arrayList;
        String str;
        String str2;
        String str3;
        boolean z;
        String strSubstring;
        String strSubstring2;
        try {
            Iterator<Row> itRowIterator = new HSSFWorkbook(new POIFSFileSystem(context.getAssets().open("shawahed/" + i + ".xls"))).getSheetAt(0).rowIterator();
            this.safr = new SafrWithShwahed();
            AyahShwahed ayahShwahed2 = new AyahShwahed();
            As7a7WithShwahed as7a7WithShwahed2 = new As7a7WithShwahed();
            new Shahed();
            ArrayList<AyahShwahed> arrayList2 = new ArrayList<>();
            ArrayList<As7a7WithShwahed> arrayList3 = new ArrayList<>();
            ArrayList<Shahed> arrayList4 = new ArrayList<>();
            String str4 = "";
            String string = "";
            int i3 = -1;
            int iReturnInt = -1;
            boolean z2 = false;
            int i4 = -1;
            while (itRowIterator.hasNext()) {
                Iterator<Cell> itCellIterator = ((HSSFRow) itRowIterator.next()).cellIterator();
                int iReturnInt2 = i4;
                while (itCellIterator.hasNext()) {
                    HSSFCell hSSFCell = (HSSFCell) itCellIterator.next();
                    if (hSSFCell.getColumnIndex() == 0) {
                        it2 = itRowIterator;
                        if (!hSSFCell.toString().equals(null) && !hSSFCell.toString().equals(str4)) {
                            hSSFCell.toString().length();
                            if (iReturnInt == -1) {
                                iReturnInt = returnInt(hSSFCell.toString().trim());
                                as7a7WithShwahed2 = new As7a7WithShwahed();
                                as7a7WithShwahed2.setAs7a7Id(iReturnInt);
                            } else if (iReturnInt != returnInt(hSSFCell.toString().trim())) {
                                iReturnInt2 = returnInt(hSSFCell.toString().trim());
                                z2 = true;
                            }
                        }
                        i2 = iReturnInt2;
                        str = str4;
                        iReturnInt2 = i2;
                        itRowIterator = it2;
                        str4 = str;
                    } else {
                        it2 = itRowIterator;
                    }
                    if (hSSFCell.getColumnIndex() == 1 && !hSSFCell.toString().equals(null) && !hSSFCell.toString().equals(str4)) {
                        if (i3 == -1) {
                            int iReturnInt3 = returnInt(hSSFCell.toString().trim());
                            AyahShwahed ayahShwahed3 = new AyahShwahed();
                            ayahShwahed3.setAs7a7Id(iReturnInt);
                            ayahShwahed3.setAyahId(iReturnInt3);
                            i2 = iReturnInt2;
                            i3 = iReturnInt3;
                            ayahShwahed2 = ayahShwahed3;
                            arrayList4 = new ArrayList<>();
                        } else if (i3 != returnInt(hSSFCell.toString().trim())) {
                            int iReturnInt4 = returnInt(hSSFCell.toString().trim());
                            ayahShwahed2.setShwahed(arrayList4);
                            arrayList2.add(ayahShwahed2);
                            ayahShwahed2 = new AyahShwahed();
                            ayahShwahed2.setAs7a7Id(iReturnInt);
                            ayahShwahed2.setAyahId(iReturnInt4);
                            arrayList4 = new ArrayList<>();
                            if (z2) {
                                as7a7WithShwahed2.setAyat(arrayList2);
                                arrayList2 = new ArrayList<>();
                                arrayList3.add(as7a7WithShwahed2);
                                as7a7WithShwahed2 = new As7a7WithShwahed();
                                as7a7WithShwahed2.setAs7a7Id(iReturnInt2);
                                iReturnInt = iReturnInt2;
                                i2 = iReturnInt;
                                i3 = iReturnInt4;
                                str = str4;
                                z2 = false;
                                iReturnInt2 = i2;
                                itRowIterator = it2;
                                str4 = str;
                            } else {
                                i2 = iReturnInt2;
                                i3 = iReturnInt4;
                            }
                        }
                        str = str4;
                        iReturnInt2 = i2;
                        itRowIterator = it2;
                        str4 = str;
                    } else if (hSSFCell.getColumnIndex() == 2) {
                        string = hSSFCell.toString();
                        i2 = iReturnInt2;
                        str = str4;
                        iReturnInt2 = i2;
                        itRowIterator = it2;
                        str4 = str;
                    } else {
                        if (!hSSFCell.toString().equals(null) && !hSSFCell.toString().equals(str4) && hSSFCell.toString().length() > 0) {
                            Shahed shahed = new Shahed();
                            shahed.setWord(string);
                            shahed.setFillShahed(hSSFCell.toString());
                            String strTrim = hSSFCell.toString().trim();
                            i2 = iReturnInt2;
                            String strReplace = strTrim.replaceAll(" ", str4).replace("(", str4).replace(")", str4);
                            ayahShwahed = ayahShwahed2;
                            int i5 = 0;
                            while (true) {
                                as7a7WithShwahed = as7a7WithShwahed2;
                                if (i5 >= this.specialSafrs.size()) {
                                    arrayList = arrayList2;
                                    str = str4;
                                    str2 = strReplace;
                                    str3 = str;
                                    z = false;
                                    break;
                                }
                                arrayList = arrayList2;
                                str = str4;
                                if (this.specialSafrs.get(i5).getSpecial().equals(strReplace.substring(0, this.specialSafrs.get(i5).getSpecial().length()))) {
                                    shahed.setSafrId(this.specialSafrs.get(i5).getSafrId());
                                    Log.d("newbible", strReplace);
                                    strSubstring = strReplace.substring(0, this.specialSafrs.get(i5).getSpecial().length());
                                    strSubstring2 = strReplace.substring(this.specialSafrs.get(i5).getSpecial().length());
                                    break;
                                }
                                if (this.specialSafrs.get(i5).getSpecial2() != null && this.specialSafrs.get(i5).getSpecial2().equals(strReplace.substring(0, this.specialSafrs.get(i5).getSpecial2().length()))) {
                                    shahed.setSafrId(this.specialSafrs.get(i5).getSafrId());
                                    Log.d("newbible", strReplace);
                                    strSubstring = strReplace.substring(0, this.specialSafrs.get(i5).getSpecial2().length());
                                    strSubstring2 = strReplace.substring(this.specialSafrs.get(i5).getSpecial2().length());
                                    break;
                                }
                                i5++;
                                as7a7WithShwahed2 = as7a7WithShwahed;
                                arrayList2 = arrayList;
                                str4 = str;
                            }
                            str3 = strSubstring;
                            str2 = strSubstring2;
                            z = true;
                            if (z) {
                                Log.d("newbible", str2);
                                if (str2.contains(":")) {
                                    String[] strArrSplit = str2.split(":");
                                    if (strArrSplit.length != 1 && strArrSplit.length <= 2 && (!strArrSplit[1].contains(LanguageTag.SEP) || (!strArrSplit[1].contains("و") && !strArrSplit[1].contains("،")))) {
                                        if (strArrSplit[1].contains(LanguageTag.SEP)) {
                                            shahed.setAs7a7Id(Integer.parseInt(strArrSplit[0]));
                                            String[] strArrSplit2 = strArrSplit[1].split(LanguageTag.SEP);
                                            ArrayList<Integer> arrayList5 = new ArrayList<>();
                                            arrayList5.add(Integer.valueOf(Integer.parseInt(strArrSplit2[0])));
                                            arrayList5.add(Integer.valueOf(Integer.parseInt(strArrSplit2[1])));
                                            shahed.setAyat(arrayList5);
                                            shahed.setStatus(1);
                                            shahed.setFillShahed(str3 + strArrSplit2[1] + LanguageTag.SEP + strArrSplit2[0] + ":" + strArrSplit[0]);
                                            arrayList4.add(shahed);
                                        } else if (strArrSplit[1].contains("و")) {
                                            shahed.setAs7a7Id(Integer.parseInt(strArrSplit[0]));
                                            String[] strArrSplit3 = strArrSplit[1].split("و");
                                            ArrayList<Integer> arrayList6 = new ArrayList<>();
                                            for (String str5 : strArrSplit3) {
                                                arrayList6.add(Integer.valueOf(Integer.parseInt(str5)));
                                            }
                                            shahed.setAyat(arrayList6);
                                            shahed.setStatus(2);
                                            arrayList4.add(shahed);
                                        } else if (strArrSplit[1].contains("،")) {
                                            shahed.setAs7a7Id(Integer.parseInt(strArrSplit[0]));
                                            String[] strArrSplit4 = strArrSplit[1].split("،");
                                            ArrayList<Integer> arrayList7 = new ArrayList<>();
                                            for (String str6 : strArrSplit4) {
                                                arrayList7.add(Integer.valueOf(Integer.parseInt(str6)));
                                            }
                                            shahed.setAyat(arrayList7);
                                            shahed.setStatus(2);
                                            arrayList4.add(shahed);
                                        } else {
                                            shahed.setAs7a7Id(Integer.parseInt(strArrSplit[0]));
                                            ArrayList<Integer> arrayList8 = new ArrayList<>();
                                            arrayList8.add(Integer.valueOf(Integer.parseInt(strArrSplit[1])));
                                            shahed.setAyat(arrayList8);
                                            shahed.setStatus(0);
                                            arrayList4.add(shahed);
                                        }
                                    }
                                } else {
                                    shahed.setAs7a7Id(Integer.parseInt(str2));
                                    shahed.setStatus(3);
                                }
                                ayahShwahed2 = ayahShwahed;
                                as7a7WithShwahed2 = as7a7WithShwahed;
                                arrayList2 = arrayList;
                            }
                            iReturnInt2 = i2;
                            itRowIterator = it2;
                            str4 = str;
                        }
                        ayahShwahed2 = ayahShwahed;
                        as7a7WithShwahed2 = as7a7WithShwahed;
                        arrayList2 = arrayList;
                        iReturnInt2 = i2;
                        itRowIterator = it2;
                        str4 = str;
                    }
                    i2 = iReturnInt2;
                    ayahShwahed = ayahShwahed2;
                    as7a7WithShwahed = as7a7WithShwahed2;
                    arrayList = arrayList2;
                    str = str4;
                    ayahShwahed2 = ayahShwahed;
                    as7a7WithShwahed2 = as7a7WithShwahed;
                    arrayList2 = arrayList;
                    iReturnInt2 = i2;
                    itRowIterator = it2;
                    str4 = str;
                }
                i4 = iReturnInt2;
            }
            ayahShwahed2.setShwahed(arrayList4);
            arrayList2.add(ayahShwahed2);
            as7a7WithShwahed2.setAyat(arrayList2);
            new ArrayList();
            arrayList3.add(as7a7WithShwahed2);
            new As7a7WithShwahed();
            this.safr.setAs7a7at(arrayList3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int returnInt(String str) {
        String strReplaceAll = str.replace("/", ".").replaceAll(" ", "");
        if (strReplaceAll.contains(".")) {
            return Integer.parseInt(strReplaceAll.substring(0, strReplaceAll.indexOf(".")));
        }
        return Integer.parseInt(strReplaceAll);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initFloating() {
        FloatingActionMenu floatingActionMenu = (FloatingActionMenu) findViewById(R.id.menu_options);
        this.mHomeFloatingActionMenu = floatingActionMenu;
        floatingActionMenu.setIconAnimated(false);
        this.mHomeFloatingActionMenu.setClosedOnTouchOutside(true);
        this.mHomeFloatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivity_Bible.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainActivity_Bible.this.mHomeFloatingActionMenu.toggle(true);
            }
        });
        FloatingActionMenu floatingActionMenu2 = (FloatingActionMenu) findViewById(R.id.menu_options2);
        this.mHomeFloatingActionMenu2 = floatingActionMenu2;
        floatingActionMenu2.setIconAnimated(false);
        this.mHomeFloatingActionMenu2.setClosedOnTouchOutside(true);
        this.mHomeFloatingActionMenu2.setOnMenuButtonClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivity_Bible.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainActivity_Bible.this.mHomeFloatingActionMenu2.toggle(true);
            }
        });
        findViewById(R.id.fb_copy).setOnClickListener(this);
        findViewById(R.id.fb_highlight).setOnClickListener(this);
        findViewById(R.id.fb_copy_all).setOnClickListener(this);
        findViewById(R.id.fb_ask).setOnClickListener(this);
        findViewById(R.id.fb_intro).setOnClickListener(this);
        findViewById(R.id.fb_save_last_read).setOnClickListener(this);
        findViewById(R.id.fb_show_colored).setOnClickListener(this);
        findViewById(R.id.fb_tafser).setOnClickListener(this);
        findViewById(R.id.fb_english).setOnClickListener(this);
        findViewById(R.id.fb_search).setOnClickListener(this);
        findViewById(R.id.fb_share).setOnClickListener(this);
        findViewById(R.id.btn_listen).setOnClickListener(this);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        FloatingActionMenu floatingActionMenu = this.mHomeFloatingActionMenu;
        if (floatingActionMenu != null && floatingActionMenu.isOpened()) {
            this.mHomeFloatingActionMenu.toggle(true);
            return;
        }
        FloatingActionMenu floatingActionMenu2 = this.mHomeFloatingActionMenu2;
        if (floatingActionMenu2 != null && floatingActionMenu2.isOpened()) {
            this.mHomeFloatingActionMenu2.toggle(true);
        } else {
            this.backPressed = true;
            super.onBackPressed();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) throws IllegalStateException, IOException, SecurityException, SQLException, IllegalArgumentException {
        switch (view.getId()) {
            case R.id.btn_listen /* 2131296439 */:
                getLink();
                break;
            case R.id.fb_ask /* 2131296646 */:
                this.mHomeFloatingActionMenu.toggle(false);
                this.asksSpin.performClick();
                break;
            case R.id.fb_copy /* 2131296647 */:
                this.mHomeFloatingActionMenu2.toggle(false);
                ArrayList<Ayat> arrayList = this.ayat;
                if (arrayList == null || arrayList.size() == 0) {
                    Toast.makeText(getApplicationContext(), R.string.long_press, 1).show();
                    break;
                } else {
                    share(7);
                    break;
                }
                break;
            case R.id.fb_copy_all /* 2131296648 */:
                this.mHomeFloatingActionMenu2.toggle(false);
                copyAll();
                break;
            case R.id.fb_english /* 2131296649 */:
                this.mHomeFloatingActionMenu.toggle(false);
                englishBible();
                break;
            case R.id.fb_highlight /* 2131296651 */:
                this.mHomeFloatingActionMenu2.toggle(false);
                ArrayList<Ayat> arrayList2 = this.ayat;
                if (arrayList2 == null || arrayList2.size() == 0) {
                    Toast.makeText(getApplicationContext(), R.string.long_press_remember, 1).show();
                    break;
                } else {
                    share(2);
                    break;
                }
                break;
            case R.id.fb_intro /* 2131296652 */:
                this.mHomeFloatingActionMenu.toggle(false);
                this.introSpin.performClick();
                break;
            case R.id.fb_save_last_read /* 2131296654 */:
                this.mHomeFloatingActionMenu.toggle(false);
                share(3);
                break;
            case R.id.fb_search /* 2131296655 */:
                this.mHomeFloatingActionMenu.toggle(false);
                startActivity(new Intent(this, (Class<?>) SearchActivity.class));
                break;
            case R.id.fb_share /* 2131296656 */:
                this.mHomeFloatingActionMenu2.toggle(false);
                ArrayList<Ayat> arrayList3 = this.ayat;
                if (arrayList3 == null || arrayList3.size() == 0) {
                    Toast.makeText(getApplicationContext(), R.string.long_press_share, 1).show();
                    break;
                } else {
                    share(10);
                    break;
                }
                break;
            case R.id.fb_show_colored /* 2131296658 */:
                this.mHomeFloatingActionMenu2.toggle(false);
                startActivity(new Intent(this, (Class<?>) ShowHighlightsActivity.class));
                break;
            case R.id.fb_tafser /* 2131296659 */:
                this.mHomeFloatingActionMenu.toggle(false);
                tafserForBible();
                break;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x026f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void tafserForBible() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 661
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.orsozoxi.MainActivity_Bible.tafserForBible():void");
    }

    private void englishBible() throws IOException {
        BufferedReader bufferedReader;
        if (this.isOld && (this.spinner.getSelectedItemPosition() == 45 || this.spinner.getSelectedItemPosition() == 44 || this.spinner.getSelectedItemPosition() == 16 || this.spinner.getSelectedItemPosition() == 17 || this.spinner.getSelectedItemPosition() == 24 || this.spinner.getSelectedItemPosition() == 25 || this.spinner.getSelectedItemPosition() == 29)) {
            Toast.makeText(getApplicationContext(), R.string.asfar_second_not_available_eng, 1).show();
            return;
        }
        new ArrayList();
        ArrayList arrayList = new ArrayList();
        try {
            if (this.isOld) {
                bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("bible/" + chooseEngBibleCahp(this.spinner.getSelectedItemPosition()) + ".xml")));
            } else {
                Log.d("orso", "bible/" + chooseEngBibleCahp(this.spinner.getSelectedItemPosition() + 46) + ".xml");
                bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("bible/" + chooseEngBibleCahp(this.spinner.getSelectedItemPosition() + 46) + ".xml")));
            }
            loop0: while (true) {
                String str = "";
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break loop0;
                    }
                    if (!line.equals("+++") && !line.contains("+++") && !line.contains("++")) {
                        str = str + line + "\n";
                    }
                }
                arrayList.add(str);
            }
            bufferedReader.close();
        } catch (IOException unused) {
        }
        Intent intent = new Intent(this, (Class<?>) TafserActivity.class);
        intent.putExtra("tafser", (String) arrayList.get(this.spinner2.getSelectedItemPosition()));
        intent.putStringArrayListExtra("tafserList", null);
        intent.putExtra("title", getEngBibleName() + " chapter " + (this.spinner2.getSelectedItemPosition() + 1));
        startActivity(intent);
    }
}
