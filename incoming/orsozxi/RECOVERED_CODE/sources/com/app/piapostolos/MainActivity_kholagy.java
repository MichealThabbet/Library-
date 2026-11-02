package com.app.piapostolos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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
import com.app.piapostolos.Adapters.ExpandableListAdapter;
import com.app.piapostolos.Adapters.KholagAdapter;
import com.app.piapostolos.KholgayBeans.ApplicationDialogs;
import com.app.piapostolos.KholgayBeans.KholagyAllow;
import com.app.piapostolos.KholgayBeans.KholagyContentLa7n;
import com.app.piapostolos.KholgayBeans.KholagyConvert;
import com.app.piapostolos.KholgayBeans.khloagyContentItem;
import com.app.piapostolos.KholgayBeans.kholagyIndexItem;
import com.app.piapostolos.KholgayBeans.kholagyItem;
import com.app.piapostolos.Others.Util;
import com.app.piapostolos.Views.NonScrollListView;
import com.facebook.share.internal.ShareConstants;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.hieupt.android.standalonescrollbar.StandaloneScrollBar;
import com.hieupt.android.standalonescrollbar.ViewExtensionsKt;
import com.hieupt.android.standalonescrollbar.view.NestedScrollView2;
import com.huawei.hms.push.constant.RemoteMessageConst;
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
public class MainActivity_kholagy extends BaseActivity {
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private static boolean araCopAva = true;
    private static boolean arabicAva = true;
    private static boolean copAva = true;
    private static boolean english = true;
    private static boolean salwatSary = false;
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
    private Button gergoryBasilyConvertor;
    private GestureDetector gestureDetector2;
    private kholagyItem items;
    private katamarsReaders kat;
    private KholagAdapter kholagAdapter;
    private KholagyContentLa7n kholagyContentLa7n;
    private NonScrollListView listViewFootballLegend;
    private ProgressDialog loadingDialog;
    private CheckBox salwatSarya;
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

    @Override // com.app.piapostolos.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        setContentView(R.layout.activity_kholagy);
        this.type = getIntent().getStringExtra(ShareConstants.MEDIA_TYPE);
        this.gergoryBasilyConvertor = (Button) findViewById(R.id.btn_gergory_basily);
        ((CollapsingToolbarLayout) findViewById(R.id.toolbar_layout)).setTitle(getString(R.string.t_kholagy));
        this.gestureDetector2 = new GestureDetector(new SwipeDetector());
        ViewExtensionsKt.attachTo((StandaloneScrollBar) findViewById(R.id.scrollbar), (NestedScrollView2) findViewById(R.id.v_nested));
        ((Button) findViewById(R.id.btn_go_dallal)).setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.MainActivity_kholagy.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainActivity_kholagy.this.startActivity(new Intent(MainActivity_kholagy.this, (Class<?>) MainActivity_dallal.class));
            }
        });
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.MainActivity_kholagy.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        if (this.type.equals("basely")) {
            this.typePath = "kholagy/basely_basic.xls";
            this.gergoryBasilyConvertor.setVisibility(0);
            this.gergoryBasilyConvertor.setText(R.string.gergory);
        } else if (this.type.equals("gergory")) {
            this.typePath = "kholagy/gergory_basic.xls";
            this.gergoryBasilyConvertor.setVisibility(0);
            this.gergoryBasilyConvertor.setText(R.string.basily);
        } else if (this.type.equals("hamal")) {
            this.typePath = "kholagy/hamal_basic.xls";
            this.gergoryBasilyConvertor.setVisibility(0);
            this.gergoryBasilyConvertor.setEnabled(true);
            this.gergoryBasilyConvertor.setText(R.string.title_activity_agbya);
            this.gergoryBasilyConvertor.setBackgroundResource(R.drawable.button_enabled);
            this.gergoryBasilyConvertor.setTextColor(Color.parseColor("#ff0000"));
        } else if (this.type.equals("all")) {
            this.typePath = "kholagy/" + getIntent().getStringExtra("eid_index_file_name");
        }
        this.gergoryBasilyConvertor.setOnClickListener(new View.OnClickListener() { // from class: com.app.piapostolos.MainActivity_kholagy.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                KholagyConvert kholagyConvert = new KholagyConvert();
                if (!MainActivity_kholagy.this.type.equals("basely")) {
                    if (!MainActivity_kholagy.this.type.equals("gergory")) {
                        if (MainActivity_kholagy.this.type.equals("hamal")) {
                            Intent intent = new Intent(MainActivity_kholagy.this, (Class<?>) AgpyaActivity.class);
                            intent.putExtra(ShareConstants.MEDIA_TYPE, "agypa");
                            MainActivity_kholagy.this.startActivity(intent);
                            return;
                        }
                        return;
                    }
                    kholagyConvert.setConverttoType("basely");
                    kholagyConvert.setFilename(MainActivity_kholagy.this.items.getKholagyIndexs().get(MainActivity_kholagy.this.spinner.getSelectedItemPosition()).getId().substring(0, 4) + ".xls");
                    app.setKholagyConvert(kholagyConvert);
                    Intent intent2 = new Intent(MainActivity_kholagy.this, (Class<?>) MainActivity_kholagy.class);
                    intent2.putExtra(ShareConstants.MEDIA_TYPE, "basely");
                    MainActivity_kholagy.this.startActivity(intent2);
                    MainActivity_kholagy.this.finish();
                    return;
                }
                kholagyConvert.setConverttoType("gergory");
                kholagyConvert.setFilename(MainActivity_kholagy.this.items.getKholagyIndexs().get(MainActivity_kholagy.this.spinner.getSelectedItemPosition()).getId().substring(0, 4) + ".xls");
                app.setKholagyConvert(kholagyConvert);
                Intent intent3 = new Intent(MainActivity_kholagy.this, (Class<?>) MainActivity_kholagy.class);
                intent3.putExtra(ShareConstants.MEDIA_TYPE, "gergory");
                MainActivity_kholagy.this.startActivity(intent3);
                MainActivity_kholagy.this.finish();
            }
        });
        KholagyAllow.setAllowSary(true);
        this.spinner = (Spinner) findViewById(R.id.spinner11);
        NonScrollListView nonScrollListView = (NonScrollListView) findViewById(R.id.liss1);
        this.listViewFootballLegend = nonScrollListView;
        nonScrollListView.setDivider(null);
        this.listViewFootballLegend.setClickable(false);
        this.gestureDetector2 = new GestureDetector(new SwipeDetector());
        init();
        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.piapostolos.MainActivity_kholagy.4
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                MainActivity_kholagy.this.testAsynch();
            }
        });
        this.arabicBox = (CheckBox) findViewById(R.id.radio_arabic_kh);
        this.copticBox = (CheckBox) findViewById(R.id.radio_coptic_kh);
        this.englishBox = (CheckBox) findViewById(R.id.radio_english_kh);
        this.arabicCopticBox = (CheckBox) findViewById(R.id.radio_ara_cop_kh);
        this.salwatSarya = (CheckBox) findViewById(R.id.radio_salwat);
        this.arabicBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.app.piapostolos.MainActivity_kholagy.5
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (MainActivity_kholagy.this.foundArabicCoptic || MainActivity_kholagy.this.foundCoptic || MainActivity_kholagy.this.foundEnglish || MainActivity_kholagy.this.copticBox.isEnabled() || MainActivity_kholagy.this.arabicCopticBox.isEnabled() || MainActivity_kholagy.this.englishBox.isEnabled()) {
                    if (MainActivity_kholagy.this.arabicCopticBox.isEnabled() || MainActivity_kholagy.this.copticBox.isChecked()) {
                        if (MainActivity_kholagy.this.copticBox.isEnabled() || MainActivity_kholagy.this.arabicCopticBox.isEnabled()) {
                            if (MainActivity_kholagy.copAva || MainActivity_kholagy.araCopAva || MainActivity_kholagy.english) {
                                boolean unused = MainActivity_kholagy.arabicAva = z;
                                MainActivity_kholagy.this.update();
                                return;
                            } else {
                                Toast.makeText(MainActivity_kholagy.this.getApplicationContext(), R.string.choose_lang, 1).show();
                                MainActivity_kholagy.this.arabicBox.setChecked(true);
                                return;
                            }
                        }
                        MainActivity_kholagy.this.arabicBox.setChecked(true);
                        return;
                    }
                    MainActivity_kholagy.this.arabicBox.setChecked(true);
                    return;
                }
                boolean unused2 = MainActivity_kholagy.arabicAva = true;
                MainActivity_kholagy.this.update();
            }
        });
        this.copticBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.app.piapostolos.MainActivity_kholagy.6
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (MainActivity_kholagy.arabicAva || MainActivity_kholagy.araCopAva || MainActivity_kholagy.english) {
                    if (MainActivity_kholagy.this.arabicCopticBox.isEnabled() || MainActivity_kholagy.this.arabicBox.isChecked()) {
                        boolean unused = MainActivity_kholagy.copAva = z;
                        MainActivity_kholagy.this.update();
                        return;
                    } else {
                        MainActivity_kholagy.this.copticBox.setChecked(true);
                        return;
                    }
                }
                Toast.makeText(MainActivity_kholagy.this.getApplicationContext(), R.string.choose_lang, 1).show();
                MainActivity_kholagy.this.copticBox.setChecked(true);
            }
        });
        this.arabicCopticBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.app.piapostolos.MainActivity_kholagy.7
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (MainActivity_kholagy.copAva || MainActivity_kholagy.arabicAva || MainActivity_kholagy.english) {
                    boolean unused = MainActivity_kholagy.araCopAva = z;
                    MainActivity_kholagy.this.update();
                } else {
                    Toast.makeText(MainActivity_kholagy.this.getApplicationContext(), R.string.choose_lang, 1).show();
                    MainActivity_kholagy.this.arabicCopticBox.setChecked(true);
                }
            }
        });
        this.englishBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.app.piapostolos.MainActivity_kholagy.8
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (MainActivity_kholagy.copAva || MainActivity_kholagy.arabicAva || MainActivity_kholagy.araCopAva) {
                    boolean unused = MainActivity_kholagy.english = z;
                    MainActivity_kholagy.this.update();
                } else {
                    Toast.makeText(MainActivity_kholagy.this.getApplicationContext(), R.string.choose_lang, 1).show();
                    MainActivity_kholagy.this.englishBox.setChecked(true);
                }
            }
        });
        this.salwatSarya.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.app.piapostolos.MainActivity_kholagy.9
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                boolean unused = MainActivity_kholagy.salwatSary = z;
                MainActivity_kholagy.this.update();
            }
        });
    }

    public void readData() throws IOException {
        int i = 0;
        this.isSanaksar = false;
        int selectedItemPosition = this.spinner.getSelectedItemPosition();
        if (this.items.getKholagyIndexs().get(selectedItemPosition).getId().substring(0, 3).equals("933")) {
            SanksarReaders sanksarReaders = new SanksarReaders(this, true);
            this.kholagyContentLa7n = new KholagyContentLa7n();
            ArrayList<khloagyContentItem> arrayList = new ArrayList<>();
            while (i < sanksarReaders.sayer.size()) {
                khloagyContentItem khloagycontentitem = new khloagyContentItem();
                khloagycontentitem.setTitleOrComment(true);
                khloagycontentitem.setTitleOrComment(sanksarReaders.sayer.get(i));
                arrayList.add(khloagycontentitem);
                this.kholagyContentLa7n.setContent(arrayList);
                this.specialData = true;
                this.isSanaksar = true;
                i++;
            }
            return;
        }
        if (this.items.getKholagyIndexs().get(selectedItemPosition).getId().substring(0, 3).equals("931")) {
            this.kholagyContentLa7n = new KholagyContentLa7n();
            ArrayList<khloagyContentItem> arrayList2 = new ArrayList<>();
            while (i < this.kat.abraksisData.size()) {
                khloagyContentItem khloagycontentitem2 = new khloagyContentItem();
                khloagycontentitem2.setTitleOrComment(true);
                khloagycontentitem2.setTitleOrComment(this.kat.abraksisData.get(i));
                arrayList2.add(khloagycontentitem2);
                this.kholagyContentLa7n.setContent(arrayList2);
                this.specialData = true;
                i++;
            }
            return;
        }
        if (this.items.getKholagyIndexs().get(selectedItemPosition).getId().substring(0, 4).equals("7001")) {
            this.kholagyContentLa7n = new KholagyContentLa7n();
            ArrayList<khloagyContentItem> arrayList3 = new ArrayList<>();
            while (i < this.kat.ashyaData.size()) {
                khloagyContentItem khloagycontentitem3 = new khloagyContentItem();
                khloagycontentitem3.setTitleOrComment(true);
                khloagycontentitem3.setTitleOrComment(this.kat.ashyaData.get(i));
                arrayList3.add(khloagycontentitem3);
                this.kholagyContentLa7n.setContent(arrayList3);
                this.specialData = true;
                i++;
            }
            return;
        }
        if (this.items.getKholagyIndexs().get(selectedItemPosition).getId().substring(0, 4).equals("7002")) {
            this.kholagyContentLa7n = new KholagyContentLa7n();
            ArrayList<khloagyContentItem> arrayList4 = new ArrayList<>();
            while (i < this.kat.bakrData.size()) {
                khloagyContentItem khloagycontentitem4 = new khloagyContentItem();
                khloagycontentitem4.setTitleOrComment(true);
                khloagycontentitem4.setTitleOrComment(this.kat.bakrData.get(i));
                arrayList4.add(khloagycontentitem4);
                this.kholagyContentLa7n.setContent(arrayList4);
                this.specialData = true;
                i++;
            }
            return;
        }
        if (this.items.getKholagyIndexs().get(selectedItemPosition).getId().substring(0, 3).equals("921")) {
            this.kholagyContentLa7n = new KholagyContentLa7n();
            ArrayList<khloagyContentItem> arrayList5 = new ArrayList<>();
            while (i < this.kat.bolesData.size()) {
                khloagyContentItem khloagycontentitem5 = new khloagyContentItem();
                khloagycontentitem5.setTitleOrComment(true);
                khloagycontentitem5.setTitleOrComment(this.kat.bolesData.get(i));
                arrayList5.add(khloagycontentitem5);
                this.kholagyContentLa7n.setContent(arrayList5);
                this.specialData = true;
                i++;
            }
            return;
        }
        if (this.items.getKholagyIndexs().get(selectedItemPosition).getId().substring(0, 3).equals("937")) {
            this.kholagyContentLa7n = new KholagyContentLa7n();
            ArrayList<khloagyContentItem> arrayList6 = new ArrayList<>();
            while (i < this.kat.mzmorKodas.size()) {
                khloagyContentItem khloagycontentitem6 = new khloagyContentItem();
                khloagycontentitem6.setTitleOrComment(true);
                khloagycontentitem6.setTitleOrComment(this.kat.mzmorKodas.get(i));
                arrayList6.add(khloagycontentitem6);
                this.kholagyContentLa7n.setContent(arrayList6);
                this.specialData = true;
                i++;
            }
            return;
        }
        if (this.items.getKholagyIndexs().get(selectedItemPosition).getId().substring(0, 3).equals("940")) {
            this.kholagyContentLa7n = new KholagyContentLa7n();
            ArrayList<khloagyContentItem> arrayList7 = new ArrayList<>();
            while (i < this.kat.engelKodaswithoutMzmor.size()) {
                khloagyContentItem khloagycontentitem7 = new khloagyContentItem();
                khloagycontentitem7.setTitleOrComment(true);
                khloagycontentitem7.setTitleOrComment(this.kat.engelKodaswithoutMzmor.get(i));
                arrayList7.add(khloagycontentitem7);
                this.kholagyContentLa7n.setContent(arrayList7);
                this.specialData = true;
                i++;
            }
            return;
        }
        if (this.items.getKholagyIndexs().get(selectedItemPosition).getId().substring(0, 3).equals("925")) {
            this.kholagyContentLa7n = new KholagyContentLa7n();
            ArrayList<khloagyContentItem> arrayList8 = new ArrayList<>();
            while (i < this.kat.kaselikonData.size()) {
                khloagyContentItem khloagycontentitem8 = new khloagyContentItem();
                khloagycontentitem8.setTitleOrComment(true);
                khloagycontentitem8.setTitleOrComment(this.kat.kaselikonData.get(i));
                arrayList8.add(khloagycontentitem8);
                this.kholagyContentLa7n.setContent(arrayList8);
                this.specialData = true;
                i++;
            }
            return;
        }
        if (this.items.getKholagyIndexs().get(selectedItemPosition).getId().substring(0, 4).equals("7007")) {
            this.kholagyContentLa7n = new KholagyContentLa7n();
            ArrayList<khloagyContentItem> arrayList9 = new ArrayList<>();
            while (i < this.kat.nabwoatData.size()) {
                khloagyContentItem khloagycontentitem9 = new khloagyContentItem();
                khloagycontentitem9.setTitleOrComment(true);
                khloagycontentitem9.setTitleOrComment(this.kat.nabwoatData.get(i));
                arrayList9.add(khloagycontentitem9);
                this.kholagyContentLa7n.setContent(arrayList9);
                this.specialData = true;
                i++;
            }
            return;
        }
        this.specialData = false;
        String strSubstring = this.items.getKholagyIndexs().get(selectedItemPosition).getId().substring(0, this.items.getKholagyIndexs().get(selectedItemPosition).getId().length() - 2);
        readExcelFile(strSubstring, getApplicationContext(), strSubstring + ".xls", false);
    }

    private void init() {
        this.loadingDialog = ApplicationDialogs.showLoadingDialog(this, getString(R.string.plz_wait));
        Observable.fromCallable(new Callable() { // from class: com.app.piapostolos.-$$Lambda$MainActivity_kholagy$ls9xe1eRNjd8sksD6H4S2lYtNyE
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$init$0$MainActivity_kholagy();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.piapostolos.-$$Lambda$MainActivity_kholagy$Mfu7ZEmwf6oocAy_jokafFPgT9k
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$init$1$MainActivity_kholagy((Boolean) obj);
            }
        });
    }

    public /* synthetic */ Boolean lambda$init$0$MainActivity_kholagy() throws Exception {
        this.kat = new katamarsReaders(this, true);
        Log.d("orso", "Eiiiiiiiiiiid=" + this.kat.eidName);
        if (this.type.equals("all")) {
            this.typePath = "kholagy/" + getIntent().getStringExtra("eid_index_file_name");
        } else if (this.kat.eidName.equals("nayrooz_sun")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_nayrouz_sun";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_nayrouz_sun";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_nayrouz_sun";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("nayrooz_sun_after")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_nayrouz_sun_after";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_nayrouz_sun";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_nayrouz_sun";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("nayrooz")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_nayrouz";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_nayrouz";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_nayrouz";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("nayrooz_after")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_nayrouz_after";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_nayrouz";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_nayrouz";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("29")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_29";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_29";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_29";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("29_sun_1")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_29_sun_1";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_29_sun_1";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_29_sun_1";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("29_sun_2")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_29_sun_2";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_29_sun_2";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_29_sun_2";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("elSaleb")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_salib";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_salib";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_salib";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("elSaleb_after")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_salib_after";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_salib";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_salib";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("Kabir_Sun_refaa")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kabir_Sun_refaa";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kabir_Sun_refaa";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kabir_Sun_refaa";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("Sabt_azzr")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_sabt_laazer";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_sabt_laazer";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_sabt_laazer";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.contains("sabt_farah")) {
            Log.d(Util.TAG, "Inside=" + this.kat.eidName);
            if (Calendar.getInstance().get(11) < 17 || this.kat.eidName.equals("sabt_farah_Fri")) {
                Log.d(Util.TAG, "Inside1=" + this.kat.eidName);
                if (this.type.equals("hamal")) {
                    this.typePath = "kholagy/hamal_sabt_el_farah";
                } else if (this.type.equals("basely")) {
                    this.typePath = "kholagy/basely_sabt_el_farah";
                } else if (this.type.equals("gergory")) {
                    this.typePath = "kholagy/gergory_sabt_el_farah";
                }
                this.typePath += ".xls";
            } else {
                Log.d(Util.TAG, "Inside2=" + this.kat.eidName);
                if (this.type.equals("hamal")) {
                    this.typePath = "kholagy/hamal_kyama";
                } else if (this.type.equals("basely")) {
                    this.typePath = "kholagy/basily_kyama";
                } else if (this.type.equals("gergory")) {
                    this.typePath = "kholagy/gergory_kyama";
                }
                this.typePath += ".xls";
            }
        } else if (this.kat.eidName.equals("kyama")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_kyama";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_kyama";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_kyama";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("masr_before_soud")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Masr_before_soud";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_Masr_before_soud";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Masr_before_soud";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("before_soud")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_before_soud";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_before_soud";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_before_soud";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("soud")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_soud";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_soud";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_soud";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("jesus_inside_egypt_sun")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Masr_Sun";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Masr_sun";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Masr_sun";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("jesus_inside_egypt")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Masr_days";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Masr";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Masr";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("masr_before_helol")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Masr_before_helol";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_Masr_before_helol";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Masr_before_helol";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("before_helol")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_before_helol";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_before_helol";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_before_helol";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("helol")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_helol";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_helol";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_helol";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("eid_rosol_sun")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_3id_rosol_sun";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_3id_rosol_sun";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_3id_rosol_sun";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("eid_rosol_days")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_3id_rosol_days";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_3id_rosol_days";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_3id_rosol_days";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("rosol_sat")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_rosol_sat";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_rosol_sat";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_rosol_sat";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("rosol_sun")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_rosol_sun";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_rosol_sun";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_rosol_sun";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("rosol_days")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_rosol_days";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_rosol_days";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_rosol_days";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("rosol_sat_sun")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_rosol_sat_sun";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_rosol_sat_sun";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_rosol_sat_sun";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("rosol_others")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_rosol_others";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_rosol_others";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_rosol_others";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("rosol_soom")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_rosol";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_rosol";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_rosol";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("sanwy_sun_1")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_sanawy_sun_1";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_sanawy_sun_1";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_sanawy_sun_1";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("sanwy_sun_2")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_sanawy_sun_2";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_sanawy_sun_2";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_sanawy_sun_2";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("sanwy_sat")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_sanawy_sat";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_sanawy_sat";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_sanawy_sat";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("sanwy_sat_sun")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_sanawy_sat_sun";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_sanawy_sat_sun";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_sanawy_sat_sun";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("sanwy_days2_fetar")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_days2_Fatar";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_sanawy_others";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_sanawy_others";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("sanwy_others")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_sanawy_others";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_sanawy_others";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_sanawy_others";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("hator_soom_melad_sat")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_sanawy_sat";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_hatour_sat";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_hatour_sat";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("hator_soom_melad_sun")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_sanawy_sun_2";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_hatour_sun";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_hatour_sun";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("hator_soom_melad_others")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_sanawy_others";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basily_hatour_others";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_hatour_others";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("kiak_sat_1")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kiak_Sat_1";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kiak_Ayam_1";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kiak_Ayam_1";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("kiak_sat_2")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kiak_Sat_2";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kiak_Ayam_2";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kiak_Ayam_2";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("kiak_sat_3")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kiak_Sat_3";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kiak_Ayam_3";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kiak_Ayam_3";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("kiak_sat_4")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kiak_Sat_4";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kiak_Ayam_4";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kiak_Ayam_4";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("kiak_sun_1")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kiak_Sun_1";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kiak_Sun_1";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kiak_Sun_1";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("kiak_sun_2")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kiak_Sun_2";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kiak_Sun_2";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kiak_Sun_2";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("kiak_sun_3")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kiak_Sun_3";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kiak_Sun_3";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kiak_Sun_3";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("kiak_sun_4")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kiak_Sun_4";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kiak_Sun_4";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kiak_Sun_4";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("kiak_ayam_1")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kiak_Ayam_1";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kiak_Ayam_1";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kiak_Ayam_1";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("kiak_ayam_2")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kiak_Ayam_2";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kiak_Ayam_2";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kiak_Ayam_2";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("kiak_ayam_3")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kiak_Ayam_3";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kiak_Ayam_3";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kiak_Ayam_3";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("kiak_ayam_4")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kiak_Ayam_4";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kiak_Ayam_4";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kiak_Ayam_4";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("eid_melad")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_basic_milad";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_basic_milad";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_basic_milad";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("eid_tagali")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_tagali";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_tagali";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_tagali";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("soom_stmary_sun")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/st_mary_hamal_sanawy_sun_2";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/st_mary_basily_sanawy_sun_2";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/st_mary_gergory_sanawy_sun_2";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("soom_stmary_sat")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/st_mary_hamal_sanawy_sat";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/st_mary_basily_sanawy_sat";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/st_mary_gergory_sanawy_sat";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("soom_stmary")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/st_mary_hamal_others";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/st_mary_basily_sanawy_others";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/st_mary_gergory_sanawy_others";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("eid_melad_after")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_basic_milad_after";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_basic_milad";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_basic_milad";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("barmoon_melad")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_baramon_milad_ayam";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_basic_baramon_milad";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_basic_baramon_milad";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("barmoon_melad_sun")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_baramon_milad_sun";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_basic_baramon_milad";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_basic_baramon_milad";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("barmoon_melad_lastday")) {
            if (Calendar.getInstance().get(11) < 17) {
                if (this.type.equals("hamal")) {
                    this.typePath = "kholagy/hamal_baramon_milad_ayam";
                } else if (this.type.equals("basely")) {
                    this.typePath = "kholagy/basely_basic_baramon_milad";
                } else if (this.type.equals("gergory")) {
                    this.typePath = "kholagy/gergory_basic_baramon_milad";
                }
                this.typePath += ".xls";
            } else {
                if (this.type.equals("hamal")) {
                    this.typePath = "kholagy/hamal_basic_milad";
                } else if (this.type.equals("basely")) {
                    this.typePath = "kholagy/basely_basic_milad";
                } else if (this.type.equals("gergory")) {
                    this.typePath = "kholagy/gergory_basic_milad";
                }
                this.typePath += ".xls";
            }
        } else if (this.kat.eidName.equals("barmoon_melad_lastday_sun")) {
            if (Calendar.getInstance().get(11) < 17) {
                if (this.type.equals("hamal")) {
                    this.typePath = "kholagy/hamal_baramon_milad_sun";
                } else if (this.type.equals("basely")) {
                    this.typePath = "kholagy/basely_basic_baramon_milad";
                } else if (this.type.equals("gergory")) {
                    this.typePath = "kholagy/gergory_basic_baramon_milad";
                }
                this.typePath += ".xls";
            } else {
                if (this.type.equals("hamal")) {
                    this.typePath = "kholagy/hamal_basic_milad";
                } else if (this.type.equals("basely")) {
                    this.typePath = "kholagy/basely_basic_milad";
                } else if (this.type.equals("gergory")) {
                    this.typePath = "kholagy/gergory_basic_milad";
                }
                this.typePath += ".xls";
            }
        } else if (this.kat.eidName.equals("soom_Younah_Mon")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Younah_Mon";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Younah_days";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Younah_days";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("soom_Younah_Tue")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Younah_Tue";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Younah_days";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Younah_days";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("soom_Younah_Wed")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Younah_Wed";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Younah_days";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Younah_days";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("soom_Younah_Thu")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Younah_Thurs";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Younah_Thurs";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Younah_Thurs";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("eid_7etan")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_basic_khetan";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_basic_khetan";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_basic_milad";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("ors_kana_elgalel")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_kana_elgalil";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/gergory_kana_elgalil";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_basic_milad";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("eid_gotas")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_basic_ghetas";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_basic_ghetas";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_basic_ghetas";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("eid_gotas_second_day")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_after_ghetas";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_after_ghetas";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_after_ghetas";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("barmoon_gotas")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_baramon_ghetas_ayam";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_basic_baramon_ghetas";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_basic_baramon_ghetas";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("barmoon_gotas_sun")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_baramon_ghetas_sun";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_basic_baramon_ghetas";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_basic_baramon_ghetas";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("barmoon_gotas_lastday")) {
            if (Calendar.getInstance().get(11) < 17) {
                if (this.type.equals("hamal")) {
                    this.typePath = "kholagy/hamal_baramon_ghetas_ayam";
                } else if (this.type.equals("basely")) {
                    this.typePath = "kholagy/basely_basic_baramon_ghetas";
                } else if (this.type.equals("gergory")) {
                    this.typePath = "kholagy/gergory_basic_baramon_ghetas";
                }
                this.typePath += ".xls";
            } else {
                if (this.type.equals("hamal")) {
                    this.typePath = "kholagy/hamal_basic_ghetas";
                } else if (this.type.equals("basely")) {
                    this.typePath = "kholagy/basely_basic_ghetas";
                } else if (this.type.equals("gergory")) {
                    this.typePath = "kholagy/gergory_basic_ghetas";
                }
                this.typePath += ".xls";
            }
        } else if (this.kat.eidName.equals("barmoon_gotas_lastday_sun")) {
            if (Calendar.getInstance().get(11) < 17) {
                if (this.type.equals("hamal")) {
                    this.typePath = "kholagy/hamal_baramon_ghetas_sun";
                } else if (this.type.equals("basely")) {
                    this.typePath = "kholagy/basely_basic_baramon_ghetas";
                } else if (this.type.equals("gergory")) {
                    this.typePath = "kholagy/gergory_basic_baramon_ghetas";
                }
                this.typePath += ".xls";
            } else {
                if (this.type.equals("hamal")) {
                    this.typePath = "kholagy/hamal_basic_ghetas";
                } else if (this.type.equals("basely")) {
                    this.typePath = "kholagy/basely_basic_ghetas";
                } else if (this.type.equals("gergory")) {
                    this.typePath = "kholagy/gergory_basic_ghetas";
                }
                this.typePath += ".xls";
            }
        } else if (this.kat.eidName.equals("jesus_hakal")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_basic_Hakal";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Hakal";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Hakal";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("soom_kber_week1_sat_sun")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kabir_Sun";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kabir_Sun_1";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kabir_Sun_1";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("soom_kber_week1")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kabir_week";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kabir_week_1";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kabir_week_1";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("soom_kber_week2_sat_sun")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kabir_Sun";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kabir_Sun_2";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kabir_Sun_2";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("soom_kber_week2")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kabir_week";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kabir_week_2";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kabir_week_2";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("soom_kber_week3_sat_sun")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kabir_Sun";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kabir_Sun_3";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kabir_Sun_3";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("soom_kber_week3")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kabir_week";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kabir_week_3";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kabir_week_3";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("soom_kber_week4_sat_sun")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kabir_Sun";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kabir_Sun_4";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kabir_Sun_4";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("soom_kber_week4")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kabir_week";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kabir_week_4";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kabir_week_4";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("soom_kber_week5_sat_sun")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kabir_Sun";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kabir_Sun_5";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kabir_Sun_5";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("soom_kber_week5")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kabir_week";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kabir_week_5";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kabir_week_5";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("soom_kber_week6_sat_sun")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kabir_Sun";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kabir_Sun_6";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kabir_Sun_6";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("soom_kber_week6")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Kabir_week";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Kabir_week_6";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Kabir_week_6";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("eid_beshara_sun")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_bashara_sun";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_bashara_sun";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_bashara_sun";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("eid_beshara")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_bashara";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_bashara";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_bashara";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("gomaa_ketam")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_Khatam";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_Khatam";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_Khatam";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("a7d_shaaneen")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_shaaneen";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_shaaneen";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_shaaneen";
            }
            this.typePath += ".xls";
        } else if (this.kat.eidName.equals("7ames_el_ahd")) {
            if (this.type.equals("hamal")) {
                this.typePath = "kholagy/hamal_el_aahd";
            } else if (this.type.equals("basely")) {
                this.typePath = "kholagy/basely_el_aahd";
            } else if (this.type.equals("gergory")) {
                this.typePath = "kholagy/gergory_el_aahd";
            }
            this.typePath += ".xls";
        }
        readExcelFileType(getApplicationContext());
        return false;
    }

    public /* synthetic */ void lambda$init$1$MainActivity_kholagy(Boolean bool) throws Exception {
        ApplicationDialogs.dismissLoadingDialog(this.loadingDialog);
        setSpinner(this.items);
        if (app.getKholagyConvert() != null) {
            int i = 0;
            while (true) {
                if (i >= this.items.getKholagyIndexs().size()) {
                    break;
                }
                if ((this.items.getKholagyIndexs().get(i).getId().substring(0, 4) + ".xls").equals(app.getKholagyConvert().getFilename())) {
                    this.spinner.setSelection(i);
                    break;
                }
                i++;
            }
            app.setKholagyConvert(null);
        }
        if (this.kat.eidName.equals("a7d_shaaneen")) {
            Button button = (Button) findViewById(R.id.btn_go_dallal);
            button.setVisibility(0);
            button.setBackgroundResource(R.drawable.button_enabled);
            button.setTextColor(Color.parseColor("#ff0000"));
        } else if (this.kat.eidName.equals("7ames_el_ahd")) {
            Button button2 = (Button) findViewById(R.id.btn_go_dallal);
            button2.setVisibility(0);
            button2.setText("الدلال");
        }
        if (this.type.equals("hamal")) {
            if (this.kat.eidName.equals("eid_gotas") || this.kat.eidName.equals("kyama") || this.kat.eidName.equals("eid_melad")) {
                this.gergoryBasilyConvertor.setVisibility(8);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void testAsynch() {
        this.xmlString = "";
        this.loadingDialog = ApplicationDialogs.showLoadingDialog(this, getString(R.string.plz_wait));
        Observable.fromCallable(new Callable() { // from class: com.app.piapostolos.-$$Lambda$MainActivity_kholagy$q497JebKININw6nylyrNrhA07Cc
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$testAsynch$2$MainActivity_kholagy();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.piapostolos.-$$Lambda$MainActivity_kholagy$3dyZNYQ9mqovyWgzngXAgnNFHOI
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$testAsynch$3$MainActivity_kholagy((Boolean) obj);
            }
        });
    }

    public /* synthetic */ Boolean lambda$testAsynch$2$MainActivity_kholagy() throws Exception {
        readData();
        return false;
    }

    public /* synthetic */ void lambda$testAsynch$3$MainActivity_kholagy(Boolean bool) throws Exception {
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
        int selectedItemPosition = this.spinner.getSelectedItemPosition();
        if (this.type.equals("basely")) {
            if (listAssetFiles("kholagy/gergory", this.items.getKholagyIndexs().get(selectedItemPosition).getId().substring(0, 4) + ".xls")) {
                this.gergoryBasilyConvertor.setEnabled(true);
                this.gergoryBasilyConvertor.setBackgroundResource(R.drawable.button_enabled);
                this.gergoryBasilyConvertor.setTextColor(Color.parseColor("#ff0000"));
            } else {
                this.gergoryBasilyConvertor.setEnabled(false);
                this.gergoryBasilyConvertor.setBackgroundResource(R.drawable.button_disabled);
                this.gergoryBasilyConvertor.setTextColor(Color.parseColor("#000000"));
            }
        } else if (this.type.equals("gergory")) {
            if (listAssetFiles("kholagy/basely", this.items.getKholagyIndexs().get(selectedItemPosition).getId().substring(0, 4) + ".xls")) {
                this.gergoryBasilyConvertor.setEnabled(true);
                this.gergoryBasilyConvertor.setBackgroundResource(R.drawable.button_enabled);
                this.gergoryBasilyConvertor.setTextColor(Color.parseColor("#ff0000"));
            } else {
                this.gergoryBasilyConvertor.setEnabled(false);
                this.gergoryBasilyConvertor.setBackgroundResource(R.drawable.button_disabled);
                this.gergoryBasilyConvertor.setTextColor(Color.parseColor("#000000"));
            }
        }
        boolean z = copAva;
        boolean z2 = arabicAva;
        boolean z3 = araCopAva;
        boolean z4 = english;
        KholagyContentLa7n kholagyContentLa7n2 = this.kholagyContentLa7n;
        if (kholagyContentLa7n2 != null && kholagyContentLa7n2.getContent() != null && this.kholagyContentLa7n.getContent().size() > 0) {
            if (!salwatSary) {
                ArrayList arrayList2 = new ArrayList();
                while (i < this.kholagyContentLa7n.getContent().size()) {
                    if (!this.kholagyContentLa7n.getContent().get(i).isSary()) {
                        arrayList2.add(this.kholagyContentLa7n.getContent().get(i));
                    }
                    i++;
                }
                this.kholagAdapter = new KholagAdapter(this, this.kholagyContentLa7n.getFontID(), arrayList2, arabicAva, copAva, araCopAva, english, false, false);
            } else {
                this.kholagAdapter = new KholagAdapter(this, this.kholagyContentLa7n.getFontID(), this.kholagyContentLa7n.getContent(), z2, z, z3, z4, this.specialData, this.isSanaksar);
            }
            this.listViewFootballLegend.setAdapter((ListAdapter) this.kholagAdapter);
        } else {
            ArrayList<khloagyContentItem> arrayList3 = new ArrayList<>();
            khloagyContentItem khloagycontentitem2 = new khloagyContentItem();
            khloagycontentitem2.setTitleOrComment(true);
            khloagycontentitem2.setTitleOrComment("");
            arrayList3.add(khloagycontentitem2);
            this.kholagyContentLa7n.setContent(arrayList3);
            if (!salwatSary) {
                ArrayList arrayList4 = new ArrayList();
                while (i < this.kholagyContentLa7n.getContent().size()) {
                    if (!this.kholagyContentLa7n.getContent().get(i).isSary()) {
                        arrayList4.add(this.kholagyContentLa7n.getContent().get(i));
                    }
                    i++;
                }
                this.kholagAdapter = new KholagAdapter(this, this.kholagyContentLa7n.getFontID(), arrayList4, arabicAva, copAva, araCopAva, english, false, false);
            } else {
                this.kholagAdapter = new KholagAdapter(this, this.kholagyContentLa7n.getFontID(), this.kholagyContentLa7n.getContent(), true, true, true, true, this.specialData, this.isSanaksar);
            }
            this.listViewFootballLegend.setAdapter((ListAdapter) this.kholagAdapter);
        }
        ApplicationDialogs.dismissLoadingDialog(this.loadingDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void update() {
        boolean z;
        if (this.arabicCopticBox.isEnabled()) {
            z = false;
        } else {
            araCopAva = false;
            z = true;
        }
        if (!salwatSary) {
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
        } else {
            KholagyContentLa7n kholagyContentLa7n2 = this.kholagyContentLa7n;
            if (kholagyContentLa7n2 != null && kholagyContentLa7n2.getContent() != null) {
                KholagAdapter kholagAdapter2 = new KholagAdapter(this, this.kholagyContentLa7n.getFontID(), this.kholagyContentLa7n.getContent(), arabicAva, copAva, araCopAva, english, false, false);
                this.kholagAdapter = kholagAdapter2;
                this.listViewFootballLegend.setAdapter((ListAdapter) kholagAdapter2);
            }
        }
        if (z) {
            araCopAva = z;
        }
    }

    public void setSpinner(kholagyItem kholagyitem) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < kholagyitem.getKholagyIndexs().size(); i++) {
            arrayList.add(kholagyitem.getKholagyIndexs().get(i).getTitle());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_white, arrayList);
        this.adapterglav = arrayAdapter;
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item_black);
        this.spinner.setAdapter((SpinnerAdapter) this.adapterglav);
    }

    private void readExcelFileType(Context context) {
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
                    String strDecodeText = app.decodeText(this, "piapostolos".getBytes(), hSSFCell.toString());
                    if (hSSFCell.getColumnIndex() == this.idAndFontColumn && strDecodeText.toString().length() > 1) {
                        kholagyindexitem.setId(strDecodeText.toString());
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

    /* JADX WARN: Removed duplicated region for block: B:55:0x020c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void readExcelFile(java.lang.String r17, android.content.Context r18, java.lang.String r19, boolean r20) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1177
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.piapostolos.MainActivity_kholagy.readExcelFile(java.lang.String, android.content.Context, java.lang.String, boolean):void");
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
                if (MainActivity_kholagy.this.spinner.getSelectedItemPosition() > 0 && MainActivity_kholagy.this.spinner.getCount() >= 1) {
                    MainActivity_kholagy.this.spinner.setSelection(MainActivity_kholagy.this.spinner.getSelectedItemPosition() - 1);
                }
                return true;
            }
            if (motionEvent2.getX() - motionEvent.getX() <= 120.0f || Math.abs(f) <= 200.0f) {
                return false;
            }
            Log.d("log", "right");
            if (MainActivity_kholagy.this.spinner.getSelectedItemPosition() < MainActivity_kholagy.this.spinner.getCount() - 1 && MainActivity_kholagy.this.spinner.getCount() > 1) {
                MainActivity_kholagy.this.spinner.setSelection(MainActivity_kholagy.this.spinner.getSelectedItemPosition() + 1);
            }
            return true;
        }
    }
}
