package com.app.orsozoxi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import com.app.orsozoxi.Adapters.ExpandableListAdapter;
import com.app.orsozoxi.Adapters.KholagAdapter;
import com.app.orsozoxi.Beans.SaintPrefs;
import com.app.orsozoxi.Beans.Saints;
import com.app.orsozoxi.KholgayBeans.ApplicationDialogs;
import com.app.orsozoxi.KholgayBeans.KholagyAllow;
import com.app.orsozoxi.KholgayBeans.KholagyContentLa7n;
import com.app.orsozoxi.KholgayBeans.khloagyContentItem;
import com.app.orsozoxi.KholgayBeans.kholagyIndexItem;
import com.app.orsozoxi.KholgayBeans.kholagyItem;
import com.app.orsozoxi.Others.SavePrefs;
import com.app.orsozoxi.Views.NonScrollListView;
import com.facebook.share.internal.ShareConstants;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hieupt.android.standalonescrollbar.StandaloneScrollBar;
import com.hieupt.android.standalonescrollbar.ViewExtensionsKt;
import com.hieupt.android.standalonescrollbar.view.NestedScrollView2;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.ibm.icu.impl.locale.LanguageTag;
import com.ibm.icu.util.CopticCalendar;
import com.navdrawer.SimpleSideDrawer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.concurrent.Callable;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/* loaded from: classes.dex */
public class MainActivity_psalmody_sanawy extends BaseActivity {
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private static boolean araCopAva = true;
    private static boolean arabicAva = true;
    private static boolean copAva = true;
    private static boolean english = true;
    ArrayAdapter<String> adapterglav;
    private CheckBox arabicBox;
    private CheckBox arabicCopticBox;
    private CheckBox copticBox;
    private CheckBox englishBox;
    private Typeface faceltr;
    private boolean foundArabic;
    private boolean foundArabicCoptic;
    private boolean foundCoptic;
    private boolean foundEnglish;
    private GestureDetector gestureDetector2;
    private kholagyItem items;
    private KholagAdapter kholagAdapter;
    private KholagyContentLa7n kholagyContentLa7n;
    private NonScrollListView listViewFootballLegend;
    private ProgressDialog loadingDialog;
    private PsalmodySanawyReaders mPsalmodySanawyReaders;
    private Calendar mTodayCalendar;
    private CopticCalendar mTodayCopticCalendar;
    private ArrayList<Saints> saintSavedList;
    Spinner spinner;
    int[] itemsNum = null;
    private int idAndFontColumn = 0;
    private int titleColumn = 1;
    private int subTitleColumn = 2;
    private int arabicColumn = 3;
    private int arabicCopticColumn = 4;
    private int coptiColumn = 5;
    private int subTitleSaryColumn = 6;
    private int arabicSaryColumn = 7;
    private int arabicCopticSaryColumn = 8;
    private int coptiSaryColumn = 9;
    private int englishColumn = 11;
    private String type = "";
    private String typePath = "";
    private boolean specialData = false;
    private boolean isSanaksar = false;
    String xmlString = "";

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        setContentView(R.layout.activity_psalmody_sanawy);
        this.type = getIntent().getStringExtra(ShareConstants.MEDIA_TYPE);
        ((CollapsingToolbarLayout) findViewById(R.id.toolbar_layout)).setTitle(getString(R.string.t_apsalomdy_sanawy));
        this.gestureDetector2 = new GestureDetector(new SwipeDetector());
        ViewExtensionsKt.attachTo((StandaloneScrollBar) findViewById(R.id.scrollbar), (NestedScrollView2) findViewById(R.id.v_nested));
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivity_psalmody_sanawy.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        SaintPrefs saintPrefs = (SaintPrefs) new SavePrefs((Activity) this, (Class<?>) SaintPrefs.class).load();
        if (saintPrefs != null && saintPrefs.getSaintsList() != null) {
            this.saintSavedList = saintPrefs.getSaintsList();
        }
        if (this.type.equals("today")) {
            this.typePath = "psalmodySanawy/the_year_all_index.xls";
        } else if (this.type.equals("all")) {
            this.typePath = "psalmodySanawy/" + getIntent().getStringExtra("eid_index_file_name");
        }
        KholagyAllow.setAllowSary(true);
        this.spinner = (Spinner) findViewById(R.id.spinner11);
        NonScrollListView nonScrollListView = (NonScrollListView) findViewById(R.id.liss1);
        this.listViewFootballLegend = nonScrollListView;
        nonScrollListView.setDivider(null);
        this.listViewFootballLegend.setClickable(false);
        this.gestureDetector2 = new GestureDetector(new SwipeDetector());
        init();
        final Button button2 = (Button) findViewById(R.id.btn_go_to_agpya);
        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.orsozoxi.MainActivity_psalmody_sanawy.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                String id = MainActivity_psalmody_sanawy.this.items.getKholagyIndexs().get(MainActivity_psalmody_sanawy.this.spinner.getSelectedItemPosition()).getId();
                if (id.equals("108")) {
                    button2.setVisibility(0);
                    MainActivity_psalmody_sanawy.this.listViewFootballLegend.setVisibility(8);
                    button2.setText(MainActivity_psalmody_sanawy.this.getString(R.string.agpya_bakr));
                    button2.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivity_psalmody_sanawy.2.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view2) {
                            Intent intent = new Intent(MainActivity_psalmody_sanawy.this, (Class<?>) AgpyaActivity.class);
                            intent.putExtra(ShareConstants.MEDIA_TYPE, "agypa");
                            intent.putExtra(FirebaseAnalytics.Param.INDEX, 0);
                            MainActivity_psalmody_sanawy.this.startActivity(intent);
                        }
                    });
                    return;
                }
                if (id.equals("100")) {
                    button2.setVisibility(0);
                    MainActivity_psalmody_sanawy.this.listViewFootballLegend.setVisibility(8);
                    button2.setText(MainActivity_psalmody_sanawy.this.getString(R.string.agpya_midnight));
                    button2.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivity_psalmody_sanawy.2.2
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view2) {
                            Intent intent = new Intent(MainActivity_psalmody_sanawy.this, (Class<?>) AgpyaActivity.class);
                            intent.putExtra(ShareConstants.MEDIA_TYPE, "agypa");
                            intent.putExtra(FirebaseAnalytics.Param.INDEX, 6);
                            MainActivity_psalmody_sanawy.this.startActivity(intent);
                        }
                    });
                    return;
                }
                button2.setVisibility(8);
                MainActivity_psalmody_sanawy.this.listViewFootballLegend.setVisibility(0);
                MainActivity_psalmody_sanawy.this.loadLa7n();
            }
        });
        this.arabicBox = (CheckBox) findViewById(R.id.radio_arabic_kh);
        this.copticBox = (CheckBox) findViewById(R.id.radio_coptic_kh);
        this.arabicCopticBox = (CheckBox) findViewById(R.id.radio_ara_cop_kh);
        this.englishBox = (CheckBox) findViewById(R.id.radio_english_kh);
        this.arabicBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.app.orsozoxi.MainActivity_psalmody_sanawy.3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (MainActivity_psalmody_sanawy.this.foundArabicCoptic || MainActivity_psalmody_sanawy.this.foundCoptic || MainActivity_psalmody_sanawy.this.foundEnglish || MainActivity_psalmody_sanawy.this.copticBox.isEnabled() || MainActivity_psalmody_sanawy.this.arabicCopticBox.isEnabled() || MainActivity_psalmody_sanawy.this.englishBox.isEnabled()) {
                    if (MainActivity_psalmody_sanawy.this.arabicCopticBox.isEnabled() || MainActivity_psalmody_sanawy.this.copticBox.isChecked()) {
                        if (MainActivity_psalmody_sanawy.this.copticBox.isEnabled() || MainActivity_psalmody_sanawy.this.arabicCopticBox.isEnabled()) {
                            if (MainActivity_psalmody_sanawy.copAva || MainActivity_psalmody_sanawy.araCopAva || MainActivity_psalmody_sanawy.english) {
                                boolean unused = MainActivity_psalmody_sanawy.arabicAva = z;
                                MainActivity_psalmody_sanawy.this.update();
                                return;
                            } else {
                                Toast.makeText(MainActivity_psalmody_sanawy.this.getApplicationContext(), R.string.choose_lang, 1).show();
                                MainActivity_psalmody_sanawy.this.arabicBox.setChecked(true);
                                return;
                            }
                        }
                        MainActivity_psalmody_sanawy.this.arabicBox.setChecked(true);
                        return;
                    }
                    MainActivity_psalmody_sanawy.this.arabicBox.setChecked(true);
                    return;
                }
                boolean unused2 = MainActivity_psalmody_sanawy.arabicAva = true;
                MainActivity_psalmody_sanawy.this.update();
            }
        });
        this.copticBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.app.orsozoxi.MainActivity_psalmody_sanawy.4
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (MainActivity_psalmody_sanawy.arabicAva || MainActivity_psalmody_sanawy.araCopAva || MainActivity_psalmody_sanawy.english) {
                    if (MainActivity_psalmody_sanawy.this.arabicCopticBox.isEnabled() || MainActivity_psalmody_sanawy.this.arabicBox.isChecked()) {
                        boolean unused = MainActivity_psalmody_sanawy.copAva = z;
                        MainActivity_psalmody_sanawy.this.update();
                        return;
                    } else {
                        MainActivity_psalmody_sanawy.this.copticBox.setChecked(true);
                        return;
                    }
                }
                Toast.makeText(MainActivity_psalmody_sanawy.this.getApplicationContext(), R.string.choose_lang, 1).show();
                MainActivity_psalmody_sanawy.this.copticBox.setChecked(true);
            }
        });
        this.arabicCopticBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.app.orsozoxi.MainActivity_psalmody_sanawy.5
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (MainActivity_psalmody_sanawy.copAva || MainActivity_psalmody_sanawy.arabicAva || MainActivity_psalmody_sanawy.english) {
                    boolean unused = MainActivity_psalmody_sanawy.araCopAva = z;
                    MainActivity_psalmody_sanawy.this.update();
                } else {
                    Toast.makeText(MainActivity_psalmody_sanawy.this.getApplicationContext(), R.string.choose_lang, 1).show();
                    MainActivity_psalmody_sanawy.this.arabicCopticBox.setChecked(true);
                }
            }
        });
        this.englishBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.app.orsozoxi.MainActivity_psalmody_sanawy.6
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (MainActivity_psalmody_sanawy.copAva || MainActivity_psalmody_sanawy.arabicAva || MainActivity_psalmody_sanawy.araCopAva) {
                    boolean unused = MainActivity_psalmody_sanawy.english = z;
                    MainActivity_psalmody_sanawy.this.update();
                } else {
                    Toast.makeText(MainActivity_psalmody_sanawy.this.getApplicationContext(), R.string.choose_lang, 1).show();
                    MainActivity_psalmody_sanawy.this.englishBox.setChecked(true);
                }
            }
        });
    }

    public void readData() {
        this.isSanaksar = false;
        int selectedItemPosition = this.spinner.getSelectedItemPosition();
        this.specialData = false;
        String id = this.items.getKholagyIndexs().get(selectedItemPosition).getId();
        readExcelFile(id, getApplicationContext(), id + ".xls", false);
    }

    private void init() {
        this.loadingDialog = ApplicationDialogs.showLoadingDialog(this, getString(R.string.plz_wait));
        Observable.fromCallable(new Callable() { // from class: com.app.orsozoxi.-$$Lambda$MainActivity_psalmody_sanawy$cavHvEsb0yE_NXn_TvHJ314qXuI
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$init$0$MainActivity_psalmody_sanawy();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.orsozoxi.-$$Lambda$MainActivity_psalmody_sanawy$Kpm77gRpiZRKJkwuXhHOLgrbY8M
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$init$1$MainActivity_psalmody_sanawy((Boolean) obj);
            }
        });
    }

    public /* synthetic */ Boolean lambda$init$0$MainActivity_psalmody_sanawy() throws Exception {
        this.mPsalmodySanawyReaders = new PsalmodySanawyReaders(this, true);
        getCalendarForCurrentDay(true);
        Log.d("orso", "Eiiiiiiiiiiid=" + this.mPsalmodySanawyReaders.eidName);
        if (this.type.equals("all")) {
            this.typePath = "psalmodySanawy/" + getIntent().getStringExtra("eid_index_file_name");
            getPsalmodyForAllEids(getApplicationContext());
        } else {
            getSelectedPsalmodyForDay(getApplicationContext());
        }
        return false;
    }

    public /* synthetic */ void lambda$init$1$MainActivity_psalmody_sanawy(Boolean bool) throws Exception {
        ApplicationDialogs.dismissLoadingDialog(this.loadingDialog);
        setSpinner(this.items);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadLa7n() {
        this.loadingDialog = ApplicationDialogs.showLoadingDialog(this, getString(R.string.plz_wait));
        Observable.fromCallable(new Callable() { // from class: com.app.orsozoxi.-$$Lambda$MainActivity_psalmody_sanawy$yT1WLbeGAdrHizVM6fHIuHAbMQY
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$loadLa7n$2$MainActivity_psalmody_sanawy();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.orsozoxi.-$$Lambda$MainActivity_psalmody_sanawy$JmNvgCrpe1GsfoJofW-T_NjyiKo
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$loadLa7n$3$MainActivity_psalmody_sanawy((Boolean) obj);
            }
        });
    }

    public /* synthetic */ Boolean lambda$loadLa7n$2$MainActivity_psalmody_sanawy() throws Exception {
        readData();
        return false;
    }

    public /* synthetic */ void lambda$loadLa7n$3$MainActivity_psalmody_sanawy(Boolean bool) throws Exception {
        int i = 0;
        ((NestedScrollView2) findViewById(R.id.v_nested)).scrollTo(0, 0);
        if (this.specialData) {
            KholagyContentLa7n kholagyContentLa7n = this.kholagyContentLa7n;
            if (kholagyContentLa7n != null && kholagyContentLa7n.getContent() != null && this.kholagyContentLa7n.getContent().size() > 0) {
                KholagAdapter kholagAdapter = new KholagAdapter(this, this.kholagyContentLa7n.getFontID(), this.kholagyContentLa7n.getContent(), true, true, true, true, this.specialData, this.isSanaksar);
                this.kholagAdapter = kholagAdapter;
                this.listViewFootballLegend.setAdapter((ListAdapter) kholagAdapter);
            } else {
                ArrayList<khloagyContentItem> arrayList = new ArrayList<>();
                khloagyContentItem khloagycontentitem = new khloagyContentItem();
                khloagycontentitem.setTitleOrComment(true);
                khloagycontentitem.setTitleOrComment("");
                arrayList.add(khloagycontentitem);
                this.kholagyContentLa7n.setContent(arrayList);
                KholagAdapter kholagAdapter2 = new KholagAdapter(this, this.kholagyContentLa7n.getFontID(), this.kholagyContentLa7n.getContent(), true, true, true, true, this.specialData, this.isSanaksar);
                this.kholagAdapter = kholagAdapter2;
                this.listViewFootballLegend.setAdapter((ListAdapter) kholagAdapter2);
            }
            ApplicationDialogs.dismissLoadingDialog(this.loadingDialog);
            return;
        }
        this.spinner.getSelectedItemPosition();
        KholagyContentLa7n kholagyContentLa7n2 = this.kholagyContentLa7n;
        if (kholagyContentLa7n2 != null && kholagyContentLa7n2.getContent() != null && this.kholagyContentLa7n.getContent().size() > 0) {
            ArrayList arrayList2 = new ArrayList();
            while (i < this.kholagyContentLa7n.getContent().size()) {
                if (!this.kholagyContentLa7n.getContent().get(i).isSary()) {
                    arrayList2.add(this.kholagyContentLa7n.getContent().get(i));
                }
                i++;
            }
            KholagAdapter kholagAdapter3 = new KholagAdapter(this, this.kholagyContentLa7n.getFontID(), arrayList2, arabicAva, copAva, araCopAva, english, false, false);
            this.kholagAdapter = kholagAdapter3;
            this.listViewFootballLegend.setAdapter((ListAdapter) kholagAdapter3);
        } else {
            ArrayList<khloagyContentItem> arrayList3 = new ArrayList<>();
            khloagyContentItem khloagycontentitem2 = new khloagyContentItem();
            khloagycontentitem2.setTitleOrComment(true);
            khloagycontentitem2.setTitleOrComment("");
            arrayList3.add(khloagycontentitem2);
            this.kholagyContentLa7n.setContent(arrayList3);
            ArrayList arrayList4 = new ArrayList();
            while (i < this.kholagyContentLa7n.getContent().size()) {
                if (!this.kholagyContentLa7n.getContent().get(i).isSary()) {
                    arrayList4.add(this.kholagyContentLa7n.getContent().get(i));
                }
                i++;
            }
            KholagAdapter kholagAdapter4 = new KholagAdapter(this, this.kholagyContentLa7n.getFontID(), arrayList4, arabicAva, copAva, araCopAva, english, false, false);
            this.kholagAdapter = kholagAdapter4;
            this.listViewFootballLegend.setAdapter((ListAdapter) kholagAdapter4);
        }
        ApplicationDialogs.dismissLoadingDialog(this.loadingDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void update() {
        boolean z;
        String id = this.items.getKholagyIndexs().get(this.spinner.getSelectedItemPosition()).getId();
        if (id.equals("108") || id.equals("100")) {
            return;
        }
        if (this.arabicCopticBox.isEnabled()) {
            z = false;
        } else {
            araCopAva = false;
            z = true;
        }
        ArrayList arrayList = new ArrayList();
        KholagyContentLa7n kholagyContentLa7n = this.kholagyContentLa7n;
        if (kholagyContentLa7n != null && kholagyContentLa7n.getContent() != null) {
            for (int i = 0; i < this.kholagyContentLa7n.getContent().size(); i++) {
                if (!this.kholagyContentLa7n.getContent().get(i).isSary()) {
                    arrayList.add(this.kholagyContentLa7n.getContent().get(i));
                }
            }
        }
        KholagAdapter kholagAdapter = new KholagAdapter(this, this.kholagyContentLa7n.getFontID(), arrayList, arabicAva, copAva, araCopAva, english, false, false);
        this.kholagAdapter = kholagAdapter;
        this.listViewFootballLegend.setAdapter((ListAdapter) kholagAdapter);
        if (z) {
            araCopAva = z;
        }
    }

    public void setSpinner(kholagyItem kholagyitem) {
        if (kholagyitem == null || kholagyitem.getKholagyIndexs() == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < kholagyitem.getKholagyIndexs().size(); i++) {
            arrayList.add(kholagyitem.getKholagyIndexs().get(i).getTitle());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_white, arrayList);
        this.adapterglav = arrayAdapter;
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item_black);
        this.spinner.setAdapter((SpinnerAdapter) this.adapterglav);
    }

    private void getSelectedPsalmodyForDay(Context context) {
        String strDecodeText;
        try {
            HSSFSheet sheetAt = new HSSFWorkbook(new POIFSFileSystem(context.getAssets().open(this.typePath))).getSheetAt(0);
            this.items = new kholagyItem();
            ArrayList<kholagyIndexItem> arrayList = new ArrayList<>();
            new kholagyIndexItem();
            Iterator<Row> itRowIterator = sheetAt.rowIterator();
            if (itRowIterator.hasNext()) {
                itRowIterator.next();
            }
            boolean z = false;
            int columnIndex = 0;
            while (itRowIterator.hasNext()) {
                if (!z) {
                    Iterator<Cell> itCellIterator = ((HSSFRow) itRowIterator.next()).cellIterator();
                    while (true) {
                        if (!itCellIterator.hasNext()) {
                            break;
                        }
                        HSSFCell hSSFCell = (HSSFCell) itCellIterator.next();
                        Log.d(RemoteMessageConst.Notification.TAG, "Columnndex" + hSSFCell.getColumnIndex() + "Cell Value: " + hSSFCell.toString());
                        String strDecodeText2 = app.decodeText(this, "orsozoxi".getBytes(), hSSFCell.toString());
                        if (!TextUtils.isEmpty(strDecodeText2) && this.mPsalmodySanawyReaders.eidName.toLowerCase().equals(strDecodeText2.toLowerCase())) {
                            columnIndex = hSSFCell.getColumnIndex();
                            break;
                        }
                    }
                    z = true;
                } else {
                    HSSFRow hSSFRow = (HSSFRow) itRowIterator.next();
                    if (hSSFRow.getCell(columnIndex) != null && !TextUtils.isEmpty(hSSFRow.getCell(columnIndex).toString())) {
                        String strDecodeText3 = app.decodeText(this, "orsozoxi".getBytes(), hSSFRow.getCell(columnIndex).toString());
                        if (!TextUtils.isEmpty(strDecodeText3) && strDecodeText3.contains("1")) {
                            kholagyIndexItem kholagyindexitem = new kholagyIndexItem();
                            if (hSSFRow.getCell(0) != null && !TextUtils.isEmpty(hSSFRow.getCell(0).toString()) && (strDecodeText = app.decodeText(this, "orsozoxi".getBytes(), hSSFRow.getCell(0).toString())) != null && !TextUtils.isEmpty(strDecodeText)) {
                                kholagyindexitem.setId(getIdFromFloatCell(strDecodeText));
                            }
                            kholagyindexitem.setTitle(app.decodeText(this, "orsozoxi".getBytes(), hSSFRow.getCell(1).toString()));
                            arrayList.add(kholagyindexitem);
                        }
                    }
                }
            }
            this.items.setKholagyIndexs(addSubsToIndex(addSubsToIndex(arrayList, "222", "psalmodySanawy/Saints_Doxology.xls"), "11111000000", "psalmodySanawy/Saints_Doxology.xls"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getIdFromFloatCell(String str) {
        if (str == null || TextUtils.isEmpty(str)) {
            return "";
        }
        return ((int) Float.valueOf(str).floatValue()) + "";
    }

    private void getCalendarForCurrentDay(boolean z) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, calendar.get(11));
        CopticCalendar copticCalendar = new CopticCalendar();
        if (z && calendar.get(11) >= 17) {
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(11, calendar.get(11));
            CopticCalendar copticCalendar2 = new CopticCalendar();
            calendar2.set(6, calendar2.get(6) + 1);
            copticCalendar2.set(6, copticCalendar2.get(6) + 1);
            this.mTodayCalendar = calendar2;
            this.mTodayCopticCalendar = copticCalendar2;
            return;
        }
        this.mTodayCalendar = calendar;
        this.mTodayCopticCalendar = copticCalendar;
    }

    private ArrayList<kholagyIndexItem> addSubsToIndex(ArrayList<kholagyIndexItem> arrayList, String str, String str2) {
        if (arrayList.size() == 0) {
            return arrayList;
        }
        ArrayList<kholagyIndexItem> arrayList2 = new ArrayList<>();
        int i = 0;
        int i2 = 0;
        while (i2 < arrayList.size()) {
            if (arrayList.get(i2).getId() != null) {
                int i3 = 1;
                if ((!TextUtils.isEmpty(arrayList.get(i2).getId())) & arrayList.get(i2).getId().equals(str)) {
                    try {
                        try {
                            HSSFSheet sheetAt = new HSSFWorkbook(new POIFSFileSystem(getAssets().open(str2))).getSheetAt(i);
                            this.items = new kholagyItem();
                            new ArrayList();
                            new kholagyIndexItem();
                            Iterator<Row> itRowIterator = sheetAt.rowIterator();
                            if (itRowIterator.hasNext()) {
                                itRowIterator.next();
                            }
                            if (itRowIterator.hasNext()) {
                                itRowIterator.next();
                            }
                            while (itRowIterator.hasNext()) {
                                HSSFRow hSSFRow = (HSSFRow) itRowIterator.next();
                                int i4 = 3;
                                HSSFCell cell = hSSFRow.getCell(3);
                                if (!TextUtils.isEmpty(cell.toString())) {
                                    String strDecodeText = app.decodeText(this, "orsozoxi".getBytes(), cell.toString());
                                    String idFromFloatCell = getIdFromFloatCell(app.decodeText(this, "orsozoxi".getBytes(), hSSFRow.getCell(i).toString()));
                                    String strDecodeText2 = app.decodeText(this, "orsozoxi".getBytes(), hSSFRow.getCell(i3).toString());
                                    String[] strArrSplit = app.decodeText(this, "orsozoxi".getBytes(), hSSFRow.getCell(2).toString()).replaceAll(" ", "").trim().split(LanguageTag.SEP);
                                    if (strDecodeText.toLowerCase().contains("all")) {
                                        addSaintZoxologiesToIndex(arrayList2, strDecodeText2, strArrSplit);
                                    } else if (isSaintIdInPreferences(idFromFloatCell)) {
                                        addSaintZoxologiesToIndex(arrayList2, strDecodeText2, strArrSplit);
                                    } else {
                                        while (true) {
                                            if (i4 >= 26) {
                                                break;
                                            }
                                            if (hSSFRow.getCell(i4) != null) {
                                                int i5 = i4 + 1;
                                                if (hSSFRow.getCell(i5) != null && !TextUtils.isEmpty(hSSFRow.getCell(i4).toString()) && !TextUtils.isEmpty(hSSFRow.getCell(i5).toString()) && isSaintEidDateEqualToday(app.decodeText(this, "orsozoxi".getBytes(), hSSFRow.getCell(i4).toString()), app.decodeText(this, "orsozoxi".getBytes(), hSSFRow.getCell(i5).toString()))) {
                                                    addSaintZoxologiesToIndex(arrayList2, strDecodeText2, strArrSplit);
                                                    break;
                                                }
                                            }
                                            i4 += 2;
                                        }
                                    }
                                }
                                i = 0;
                                i3 = 1;
                            }
                        } catch (Exception e) {
                            e = e;
                            e.printStackTrace();
                            i2++;
                            i = 0;
                        }
                    } catch (Exception e2) {
                        e = e2;
                    }
                }
                i2++;
                i = 0;
            }
            arrayList2.add(arrayList.get(i2));
            i2++;
            i = 0;
        }
        return arrayList2;
    }

    private boolean isSaintEidDateEqualToday(String str, String str2) {
        String idFromFloatCell = getIdFromFloatCell(str);
        String idFromFloatCell2 = getIdFromFloatCell(str2);
        if (TextUtils.isDigitsOnly(idFromFloatCell) && TextUtils.isDigitsOnly(idFromFloatCell2)) {
            return Integer.valueOf(idFromFloatCell).intValue() == this.mTodayCopticCalendar.get(5) && Integer.valueOf(idFromFloatCell2).intValue() - 1 == this.mTodayCopticCalendar.get(2);
        }
        return false;
    }

    private void addSaintZoxologiesToIndex(ArrayList<kholagyIndexItem> arrayList, String str, String[] strArr) {
        for (String str2 : strArr) {
            kholagyIndexItem kholagyindexitem = new kholagyIndexItem();
            kholagyindexitem.setId(getIdFromFloatCell(str2));
            kholagyindexitem.setTitle(str);
            arrayList.add(kholagyindexitem);
        }
    }

    private boolean isSaintIdInPreferences(String str) {
        if (this.saintSavedList == null) {
            return false;
        }
        for (int i = 0; i < this.saintSavedList.size(); i++) {
            if (this.saintSavedList.get(i).getSaintId().equals(str)) {
                return true;
            }
        }
        return false;
    }

    private void getPsalmodyForAllEids(Context context) {
        try {
            HSSFSheet sheetAt = new HSSFWorkbook(new POIFSFileSystem(context.getAssets().open(this.typePath))).getSheetAt(0);
            this.items = new kholagyItem();
            ArrayList<kholagyIndexItem> arrayList = new ArrayList<>();
            new kholagyIndexItem();
            Iterator<Row> itRowIterator = sheetAt.rowIterator();
            while (itRowIterator.hasNext()) {
                kholagyIndexItem kholagyindexitem = new kholagyIndexItem();
                Iterator<Cell> itCellIterator = ((HSSFRow) itRowIterator.next()).cellIterator();
                while (itCellIterator.hasNext()) {
                    HSSFCell hSSFCell = (HSSFCell) itCellIterator.next();
                    Log.d(RemoteMessageConst.Notification.TAG, "Columnndex" + hSSFCell.getColumnIndex() + "Cell Value: " + hSSFCell.toString());
                    String strDecodeText = app.decodeText(this, "orsozoxi".getBytes(), hSSFCell.toString());
                    if (hSSFCell.getColumnIndex() == this.idAndFontColumn && strDecodeText.toString().length() > 1) {
                        kholagyindexitem.setId(getIdFromFloatCell(strDecodeText.toString()));
                    } else if (hSSFCell.getColumnIndex() == this.titleColumn && strDecodeText.toString().length() > 1) {
                        kholagyindexitem.setTitle(strDecodeText.toString());
                    }
                }
                arrayList.add(kholagyindexitem);
            }
            this.items.setKholagyIndexs(arrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readExcelFile(String str, Context context, String str2, boolean z) {
        try {
            this.kholagyContentLa7n = new KholagyContentLa7n();
            ArrayList<khloagyContentItem> arrayList = new ArrayList<>();
            khloagyContentItem khloagycontentitem = new khloagyContentItem();
            HSSFSheet sheetAt = new HSSFWorkbook(new POIFSFileSystem(context.getAssets().open("psalmodySanawy/" + str2))).getSheetAt(0);
            this.foundCoptic = false;
            this.foundArabicCoptic = false;
            this.foundArabic = false;
            this.foundEnglish = false;
            Iterator<Row> itRowIterator = sheetAt.rowIterator();
            while (itRowIterator.hasNext()) {
                Iterator<Cell> itCellIterator = ((HSSFRow) itRowIterator.next()).cellIterator();
                boolean z2 = false;
                while (itCellIterator.hasNext()) {
                    HSSFCell hSSFCell = (HSSFCell) itCellIterator.next();
                    String strDecodeText = app.decodeText(this, "orsozoxi".getBytes(), hSSFCell.toString());
                    if (hSSFCell.getColumnIndex() == this.idAndFontColumn && hSSFCell.getRowIndex() == 0 && strDecodeText.toString().length() > 1) {
                        this.kholagyContentLa7n.setNumber(strDecodeText.toString());
                    } else if (hSSFCell.getColumnIndex() == this.idAndFontColumn && hSSFCell.getRowIndex() == 1) {
                        String string = strDecodeText.toString();
                        if (string.equals(null) || string.equals("")) {
                            this.kholagyContentLa7n.setFontID("");
                        } else {
                            this.kholagyContentLa7n.setFontID(string);
                        }
                    } else if (hSSFCell.getColumnIndex() == this.titleColumn && hSSFCell.getRowIndex() == 0 && strDecodeText.toString().length() > 1) {
                        this.kholagyContentLa7n.setTitle(strDecodeText.toString());
                    } else if (hSSFCell.getColumnIndex() == this.subTitleColumn && strDecodeText.toString().length() > 1) {
                        khloagycontentitem.setTitleOrComment(true);
                        khloagycontentitem.setTitleOrComment(strDecodeText.toString());
                        arrayList.add(khloagycontentitem);
                        khloagycontentitem = new khloagyContentItem();
                    } else if (hSSFCell.getColumnIndex() == this.subTitleColumn && strDecodeText.toString().length() > 1) {
                        khloagycontentitem.setTitleOrComment(true);
                        khloagycontentitem.setTitleOrComment(strDecodeText.toString());
                        arrayList.add(khloagycontentitem);
                        khloagycontentitem = new khloagyContentItem();
                    } else if (hSSFCell.getColumnIndex() == this.subTitleSaryColumn && strDecodeText.toString().length() > 1) {
                        khloagycontentitem.setTitleOrComment(true);
                        khloagycontentitem.setTitleOrComment(strDecodeText.toString());
                        khloagycontentitem.setSary(true);
                        arrayList.add(khloagycontentitem);
                        khloagycontentitem = new khloagyContentItem();
                    } else {
                        if (hSSFCell.getColumnIndex() == this.arabicColumn && strDecodeText.toString().length() > 1) {
                            khloagycontentitem.setArabicFound(true);
                            khloagycontentitem.setTitleOrComment(false);
                            khloagycontentitem.setSary(false);
                            khloagycontentitem.setArabicText(strDecodeText.toString());
                        } else if (hSSFCell.getColumnIndex() == this.arabicCopticColumn && strDecodeText.toString().length() > 1) {
                            Log.d("orso", "path:-" + strDecodeText.toString());
                            khloagycontentitem.setArabicCopticFound(true);
                            khloagycontentitem.setTitleOrComment(false);
                            khloagycontentitem.setSary(false);
                            khloagycontentitem.setArabicCopticText(strDecodeText.toString());
                        } else if (hSSFCell.getColumnIndex() == this.coptiColumn && strDecodeText.toString().length() > 1) {
                            khloagycontentitem.setCopticFound(true);
                            khloagycontentitem.setTitleOrComment(false);
                            khloagycontentitem.setSary(false);
                            khloagycontentitem.setCopticText(strDecodeText.toString());
                        } else if (hSSFCell.getColumnIndex() == this.arabicSaryColumn && strDecodeText.toString().length() > 1) {
                            khloagycontentitem.setArabicFound(true);
                            khloagycontentitem.setTitleOrComment(false);
                            khloagycontentitem.setSary(true);
                            khloagycontentitem.setArabicText(strDecodeText.toString());
                        } else if (hSSFCell.getColumnIndex() == this.arabicCopticSaryColumn && strDecodeText.toString().length() > 1) {
                            khloagycontentitem.setArabicCopticFound(true);
                            khloagycontentitem.setTitleOrComment(false);
                            khloagycontentitem.setSary(true);
                            khloagycontentitem.setArabicCopticText(strDecodeText.toString());
                        } else if (hSSFCell.getColumnIndex() == this.coptiSaryColumn && strDecodeText.toString().length() > 1) {
                            khloagycontentitem.setCopticFound(true);
                            khloagycontentitem.setTitleOrComment(false);
                            khloagycontentitem.setSary(true);
                            khloagycontentitem.setCopticText(strDecodeText.toString());
                        } else if (hSSFCell.getColumnIndex() == this.englishColumn && strDecodeText.toString().length() > 1) {
                            khloagycontentitem.setEnglishFound(true);
                            khloagycontentitem.setTitleOrComment(false);
                            khloagycontentitem.setSary(false);
                            khloagycontentitem.setEnglishText(strDecodeText.toString());
                        }
                        z2 = true;
                    }
                }
                if (z2) {
                    arrayList.add(khloagycontentitem);
                    khloagycontentitem = new khloagyContentItem();
                }
            }
            this.kholagyContentLa7n.setContent(arrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean listAssetFiles(String str, String str2) throws IOException {
        try {
            String[] list = getAssets().list(str);
            if (list.length <= 0) {
                return false;
            }
            boolean z = false;
            for (String str3 : list) {
                if (str3.equals(str2)) {
                    z = true;
                }
            }
            return z;
        } catch (IOException unused) {
            return false;
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        GestureDetector gestureDetector = this.gestureDetector2;
        if (gestureDetector == null || !gestureDetector.onTouchEvent(motionEvent)) {
            return super.dispatchTouchEvent(motionEvent);
        }
        return true;
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
                if (MainActivity_psalmody_sanawy.this.spinner.getSelectedItemPosition() > 0 && MainActivity_psalmody_sanawy.this.spinner.getCount() >= 1) {
                    MainActivity_psalmody_sanawy.this.spinner.setSelection(MainActivity_psalmody_sanawy.this.spinner.getSelectedItemPosition() - 1);
                }
                return true;
            }
            if (motionEvent2.getX() - motionEvent.getX() <= 120.0f || Math.abs(f) <= 200.0f) {
                return false;
            }
            Log.d("log", "right");
            if (MainActivity_psalmody_sanawy.this.spinner.getSelectedItemPosition() < MainActivity_psalmody_sanawy.this.spinner.getCount() - 1 && MainActivity_psalmody_sanawy.this.spinner.getCount() > 1) {
                MainActivity_psalmody_sanawy.this.spinner.setSelection(MainActivity_psalmody_sanawy.this.spinner.getSelectedItemPosition() + 1);
            }
            return true;
        }
    }
}
