package com.app.orsozoxi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.TextView;
import android.widget.Toast;
import com.app.orsozoxi.Adapters.ExpandableListAdapter;
import com.app.orsozoxi.ApsalmodyBeans.Apsalmody;
import com.app.orsozoxi.ApsalmodyBeans.FavouritListEntity;
import com.app.orsozoxi.ApsalmodyBeans.PsalmodyAdapter;
import com.app.orsozoxi.ApsalmodyBeans.PsalmodyEntity;
import com.app.orsozoxi.ApsalmodyBeans.psalomdyItem;
import com.app.orsozoxi.KholgayBeans.ApplicationDialogs;
import com.app.orsozoxi.Others.SavePrefs;
import com.app.orsozoxi.Others.checksim;
import com.app.orsozoxi.Views.MenuDateView;
import com.app.orsozoxi.Views.NonScrollListView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.hieupt.android.standalonescrollbar.StandaloneScrollBar;
import com.hieupt.android.standalonescrollbar.ViewExtensionsKt;
import com.hieupt.android.standalonescrollbar.view.NestedScrollView2;
import com.navdrawer.SimpleSideDrawer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Callable;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/* loaded from: classes.dex */
public class MainActivity_absalmodyaKiahk extends BaseActivity {
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private static boolean araCopAva = true;
    private static boolean arabicAva = true;
    private static boolean copAva = true;
    private ArrayAdapter<String> adapterglav;
    private CheckBox arabicBox;
    private CheckBox arabicCopticBox;
    private View content;
    private CheckBox copticBox;
    private Typeface faceltr;
    private GestureDetector gestureDetector;
    private GestureDetector gestureDetector2;
    private NonScrollListView listViewFootballLegend;
    private ProgressDialog loadingDialog;
    private MenuDateView menuDateView;
    private PsalmodyAdapter psalomdyAdapter;
    private PsalmodyEntity selectedPsalmody;
    Spinner spinner;
    int[] itemsNum = null;
    boolean isGoneNow = false;
    boolean foundCoptic = false;
    boolean foundArabicCoptic = false;
    private int arabicColumn = 4;
    private int arabicCopticColumn = 5;
    private int coptiColumn = 6;
    private int titleColumn = 1;
    private int araTitleColumn = 2;
    private int copTitleColumn = 3;

    @Override // com.app.orsozoxi.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        setContentView(R.layout.activity_main_activity_absalmodya_kiahk);
        ((CollapsingToolbarLayout) findViewById(R.id.toolbar_layout)).setTitle(getString(R.string.t_apsalomdy_kiahk));
        this.gestureDetector2 = new GestureDetector(new SwipeDetector());
        Button button = (Button) findViewById(R.id.btn_slide);
        final SimpleSideDrawer simpleSideDrawer = new SimpleSideDrawer(this);
        simpleSideDrawer.setLeftBehindContentView(R.layout.left_menu_ex);
        button.setVisibility(0);
        Slider.prepareListData(this);
        ((ExpandableListView) findViewById(R.id.lvExp)).setAdapter(new ExpandableListAdapter(this, this, Slider.listDataHeader, Slider.listDataChild));
        button.setOnClickListener(new View.OnClickListener() { // from class: com.app.orsozoxi.MainActivity_absalmodyaKiahk.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                simpleSideDrawer.toggleLeftDrawer();
            }
        });
        ViewExtensionsKt.attachTo((StandaloneScrollBar) findViewById(R.id.scrollbar), (NestedScrollView2) findViewById(R.id.v_nested));
        new Handler().postDelayed(new Runnable() { // from class: com.app.orsozoxi.MainActivity_absalmodyaKiahk.2
            @Override // java.lang.Runnable
            public void run() throws IOException {
                Apsalmody.readsheetAll(MainActivity_absalmodyaKiahk.this);
                int intExtra = MainActivity_absalmodyaKiahk.this.getIntent().getIntExtra("day", 0);
                MainActivity_absalmodyaKiahk mainActivity_absalmodyaKiahk = MainActivity_absalmodyaKiahk.this;
                mainActivity_absalmodyaKiahk.spinner = (Spinner) mainActivity_absalmodyaKiahk.findViewById(R.id.spinner11);
                if (intExtra == 0) {
                    MainActivity_absalmodyaKiahk.this.itemsNum = Apsalmody.sunday;
                    MainActivity_absalmodyaKiahk mainActivity_absalmodyaKiahk2 = MainActivity_absalmodyaKiahk.this;
                    mainActivity_absalmodyaKiahk2.setSpinner(mainActivity_absalmodyaKiahk2.itemsNum);
                } else if (intExtra == 1) {
                    MainActivity_absalmodyaKiahk.this.itemsNum = Apsalmody.monday;
                    MainActivity_absalmodyaKiahk mainActivity_absalmodyaKiahk3 = MainActivity_absalmodyaKiahk.this;
                    mainActivity_absalmodyaKiahk3.setSpinner(mainActivity_absalmodyaKiahk3.itemsNum);
                } else if (intExtra == 2) {
                    MainActivity_absalmodyaKiahk.this.itemsNum = Apsalmody.tuesday;
                    MainActivity_absalmodyaKiahk mainActivity_absalmodyaKiahk4 = MainActivity_absalmodyaKiahk.this;
                    mainActivity_absalmodyaKiahk4.setSpinner(mainActivity_absalmodyaKiahk4.itemsNum);
                } else if (intExtra == 3) {
                    MainActivity_absalmodyaKiahk.this.itemsNum = Apsalmody.wednesday;
                    MainActivity_absalmodyaKiahk mainActivity_absalmodyaKiahk5 = MainActivity_absalmodyaKiahk.this;
                    mainActivity_absalmodyaKiahk5.setSpinner(mainActivity_absalmodyaKiahk5.itemsNum);
                } else if (intExtra == 4) {
                    MainActivity_absalmodyaKiahk.this.itemsNum = Apsalmody.thursday;
                    MainActivity_absalmodyaKiahk mainActivity_absalmodyaKiahk6 = MainActivity_absalmodyaKiahk.this;
                    mainActivity_absalmodyaKiahk6.setSpinner(mainActivity_absalmodyaKiahk6.itemsNum);
                } else if (intExtra == 5) {
                    MainActivity_absalmodyaKiahk.this.itemsNum = Apsalmody.friday;
                    MainActivity_absalmodyaKiahk mainActivity_absalmodyaKiahk7 = MainActivity_absalmodyaKiahk.this;
                    mainActivity_absalmodyaKiahk7.setSpinner(mainActivity_absalmodyaKiahk7.itemsNum);
                } else if (intExtra == 6) {
                    MainActivity_absalmodyaKiahk.this.itemsNum = Apsalmody.saturday;
                    MainActivity_absalmodyaKiahk mainActivity_absalmodyaKiahk8 = MainActivity_absalmodyaKiahk.this;
                    mainActivity_absalmodyaKiahk8.setSpinner(mainActivity_absalmodyaKiahk8.itemsNum);
                } else if (intExtra == 7) {
                    Apsalmody.readsheetAllForAllpsalmody(MainActivity_absalmodyaKiahk.this);
                    MainActivity_absalmodyaKiahk.this.itemsNum = Apsalmody.allNumbersAll;
                    MainActivity_absalmodyaKiahk mainActivity_absalmodyaKiahk9 = MainActivity_absalmodyaKiahk.this;
                    mainActivity_absalmodyaKiahk9.setSpinnerAll(mainActivity_absalmodyaKiahk9.itemsNum);
                } else {
                    FavouritListEntity favouritListEntity = (FavouritListEntity) new SavePrefs((Activity) MainActivity_absalmodyaKiahk.this, (Class<?>) FavouritListEntity.class).load();
                    if (favouritListEntity != null && favouritListEntity.getFavouriteList().size() > 0) {
                        MainActivity_absalmodyaKiahk.this.itemsNum = favouritListEntity.getFavouriteList().get(intExtra - 8);
                        MainActivity_absalmodyaKiahk mainActivity_absalmodyaKiahk10 = MainActivity_absalmodyaKiahk.this;
                        mainActivity_absalmodyaKiahk10.setSpinner(mainActivity_absalmodyaKiahk10.itemsNum);
                    }
                }
                MainActivity_absalmodyaKiahk.this.spinner.performClick();
                MainActivity_absalmodyaKiahk.this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.app.orsozoxi.MainActivity_absalmodyaKiahk.2.1
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                        MainActivity_absalmodyaKiahk.this.TestAsynch();
                    }
                });
                NestedScrollView2 nestedScrollView2 = (NestedScrollView2) MainActivity_absalmodyaKiahk.this.findViewById(R.id.v_nested);
                MainActivity_absalmodyaKiahk mainActivity_absalmodyaKiahk11 = MainActivity_absalmodyaKiahk.this;
                mainActivity_absalmodyaKiahk11.listViewFootballLegend = (NonScrollListView) mainActivity_absalmodyaKiahk11.findViewById(R.id.liss1);
                MainActivity_absalmodyaKiahk.this.listViewFootballLegend.setDivider(null);
                MainActivity_absalmodyaKiahk.this.listViewFootballLegend.setClickable(false);
                nestedScrollView2.smoothScrollTo(0, 0);
                MainActivity_absalmodyaKiahk mainActivity_absalmodyaKiahk12 = MainActivity_absalmodyaKiahk.this;
                mainActivity_absalmodyaKiahk12.arabicBox = (CheckBox) mainActivity_absalmodyaKiahk12.findViewById(R.id.radio_arabic);
                MainActivity_absalmodyaKiahk mainActivity_absalmodyaKiahk13 = MainActivity_absalmodyaKiahk.this;
                mainActivity_absalmodyaKiahk13.copticBox = (CheckBox) mainActivity_absalmodyaKiahk13.findViewById(R.id.radio_coptic);
                MainActivity_absalmodyaKiahk mainActivity_absalmodyaKiahk14 = MainActivity_absalmodyaKiahk.this;
                mainActivity_absalmodyaKiahk14.arabicCopticBox = (CheckBox) mainActivity_absalmodyaKiahk14.findViewById(R.id.radio_ara_cop);
                MainActivity_absalmodyaKiahk.this.arabicBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.app.orsozoxi.MainActivity_absalmodyaKiahk.2.2
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        if (MainActivity_absalmodyaKiahk.this.foundArabicCoptic || MainActivity_absalmodyaKiahk.this.foundCoptic || MainActivity_absalmodyaKiahk.this.copticBox.isEnabled() || MainActivity_absalmodyaKiahk.this.arabicCopticBox.isEnabled()) {
                            if (MainActivity_absalmodyaKiahk.this.arabicCopticBox.isEnabled() || MainActivity_absalmodyaKiahk.this.copticBox.isChecked()) {
                                if (MainActivity_absalmodyaKiahk.this.copticBox.isEnabled() || MainActivity_absalmodyaKiahk.this.arabicCopticBox.isEnabled()) {
                                    if (MainActivity_absalmodyaKiahk.copAva || MainActivity_absalmodyaKiahk.araCopAva) {
                                        boolean unused = MainActivity_absalmodyaKiahk.arabicAva = z;
                                        MainActivity_absalmodyaKiahk.this.selectedPsalmody.setArabic(MainActivity_absalmodyaKiahk.arabicAva);
                                        MainActivity_absalmodyaKiahk.this.update();
                                        return;
                                    } else {
                                        Toast.makeText(MainActivity_absalmodyaKiahk.this.getApplicationContext(), R.string.choose_lang, 1).show();
                                        MainActivity_absalmodyaKiahk.this.arabicBox.setChecked(true);
                                        return;
                                    }
                                }
                                MainActivity_absalmodyaKiahk.this.arabicBox.setChecked(true);
                                return;
                            }
                            MainActivity_absalmodyaKiahk.this.arabicBox.setChecked(true);
                            return;
                        }
                        boolean unused2 = MainActivity_absalmodyaKiahk.arabicAva = true;
                        MainActivity_absalmodyaKiahk.this.selectedPsalmody.setArabic(MainActivity_absalmodyaKiahk.arabicAva);
                        MainActivity_absalmodyaKiahk.this.update();
                    }
                });
                MainActivity_absalmodyaKiahk.this.copticBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.app.orsozoxi.MainActivity_absalmodyaKiahk.2.3
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        if (MainActivity_absalmodyaKiahk.arabicAva || MainActivity_absalmodyaKiahk.araCopAva) {
                            if (MainActivity_absalmodyaKiahk.this.arabicCopticBox.isEnabled() || MainActivity_absalmodyaKiahk.this.arabicBox.isChecked()) {
                                boolean unused = MainActivity_absalmodyaKiahk.copAva = z;
                                MainActivity_absalmodyaKiahk.this.selectedPsalmody.setCoptic(MainActivity_absalmodyaKiahk.araCopAva);
                                MainActivity_absalmodyaKiahk.this.update();
                                return;
                            }
                            MainActivity_absalmodyaKiahk.this.copticBox.setChecked(true);
                            return;
                        }
                        Toast.makeText(MainActivity_absalmodyaKiahk.this.getApplicationContext(), R.string.choose_lang, 1).show();
                        MainActivity_absalmodyaKiahk.this.copticBox.setChecked(true);
                    }
                });
                MainActivity_absalmodyaKiahk.this.arabicCopticBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.app.orsozoxi.MainActivity_absalmodyaKiahk.2.4
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        if (MainActivity_absalmodyaKiahk.copAva || MainActivity_absalmodyaKiahk.arabicAva) {
                            boolean unused = MainActivity_absalmodyaKiahk.araCopAva = z;
                            MainActivity_absalmodyaKiahk.this.selectedPsalmody.setArabicCoptic(MainActivity_absalmodyaKiahk.araCopAva);
                            MainActivity_absalmodyaKiahk.this.update();
                        } else {
                            Toast.makeText(MainActivity_absalmodyaKiahk.this.getApplicationContext(), R.string.choose_lang, 1).show();
                            MainActivity_absalmodyaKiahk.this.arabicCopticBox.setChecked(true);
                        }
                    }
                });
            }
        }, 300L);
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
        PsalmodyAdapter psalmodyAdapter = new PsalmodyAdapter(this, this.selectedPsalmody.getContent(), arabicAva, copAva, araCopAva);
        this.psalomdyAdapter = psalmodyAdapter;
        this.listViewFootballLegend.setAdapter((ListAdapter) psalmodyAdapter);
        ((NestedScrollView2) findViewById(R.id.v_nested)).smoothScrollTo(0, 0);
        if (z) {
            araCopAva = z;
        }
    }

    public void setSpinner(int[] iArr) {
        ArrayList arrayList = new ArrayList();
        for (int i : iArr) {
            for (int i2 = 0; i2 < Apsalmody.allNumbers.length; i2++) {
                if (i == Apsalmody.allNumbers[i2]) {
                    arrayList.add(Apsalmody.allNames[i2]);
                }
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_white, arrayList);
        this.adapterglav = arrayAdapter;
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item_black);
        this.spinner.setAdapter((SpinnerAdapter) this.adapterglav);
    }

    public void setSpinnerAll(int[] iArr) {
        ArrayList arrayList = new ArrayList();
        for (int i : iArr) {
            for (int i2 = 0; i2 < Apsalmody.allNumbersAll.length; i2++) {
                if (i == Apsalmody.allNumbersAll[i2]) {
                    arrayList.add(Apsalmody.allNamesAll[i2]);
                }
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_white, arrayList);
        this.adapterglav = arrayAdapter;
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item_black);
        this.spinner.setAdapter((SpinnerAdapter) this.adapterglav);
    }

    public void readData() {
        int i = this.itemsNum[this.spinner.getSelectedItemPosition()];
        readExcelFile(getApplicationContext(), i + ".xls");
    }

    private void readExcelFile(Context context, String str) {
        try {
            this.foundCoptic = false;
            this.foundArabicCoptic = false;
            this.selectedPsalmody = new PsalmodyEntity();
            psalomdyItem psalomdyitem = new psalomdyItem();
            ArrayList<psalomdyItem> arrayList = new ArrayList<>();
            Iterator<Row> itRowIterator = new HSSFWorkbook(new POIFSFileSystem(context.getAssets().open("absalmody/" + str))).getSheetAt(0).rowIterator();
            while (itRowIterator.hasNext()) {
                Iterator<Cell> itCellIterator = ((HSSFRow) itRowIterator.next()).cellIterator();
                while (itCellIterator.hasNext()) {
                    HSSFCell hSSFCell = (HSSFCell) itCellIterator.next();
                    String strDecodeText = app.decodeText(this, "orsozoxi".getBytes(), hSSFCell.toString());
                    if (hSSFCell.getColumnIndex() == this.titleColumn) {
                        this.selectedPsalmody.setTitle(strDecodeText.toString());
                    } else if (hSSFCell.getColumnIndex() == this.araTitleColumn) {
                        Log.d("tit", "aratit" + hSSFCell.toString());
                        if (!hSSFCell.toString().equals("")) {
                            this.selectedPsalmody.setArabicTitle(strDecodeText.toString());
                        }
                    } else if (hSSFCell.getColumnIndex() == this.copTitleColumn) {
                        Log.d("tit", "aratit" + strDecodeText.toString());
                        if (!hSSFCell.toString().equals("")) {
                            this.selectedPsalmody.setCopticTitle(strDecodeText.toString());
                        }
                    } else if (hSSFCell.getColumnIndex() == this.copTitleColumn) {
                        this.selectedPsalmody.setCopticTitle(strDecodeText.toString());
                    } else if (hSSFCell.getColumnIndex() == this.arabicColumn) {
                        if (strDecodeText.toString().contains("#")) {
                            psalomdyitem.setColored(true);
                        } else {
                            psalomdyitem.setColored(false);
                        }
                        psalomdyitem.setArabicText(strDecodeText.toString());
                    } else if (hSSFCell.getColumnIndex() == this.coptiColumn) {
                        psalomdyitem.setCopticText(strDecodeText.toString());
                        if (strDecodeText != null && strDecodeText.toString().length() > 2) {
                            this.foundCoptic = true;
                        }
                        if (strDecodeText.toString().contains("#")) {
                            psalomdyitem.setColored(true);
                        } else {
                            psalomdyitem.setColored(false);
                        }
                    } else if (hSSFCell.getColumnIndex() == this.arabicCopticColumn) {
                        psalomdyitem.setArabicCopticText(strDecodeText.toString());
                        if (strDecodeText != null && strDecodeText.toString().length() > 2) {
                            this.foundArabicCoptic = true;
                        }
                    }
                }
                if (psalomdyitem.getArabicText() != null || psalomdyitem.getArabicCopticText() != null || psalomdyitem.getCopticText() != null) {
                    arrayList.add(psalomdyitem);
                    psalomdyitem = new psalomdyItem();
                }
            }
            this.selectedPsalmody.setArabic(true);
            this.selectedPsalmody.setArabicCoptic(this.foundArabicCoptic);
            this.selectedPsalmody.setCoptic(this.foundCoptic);
            this.selectedPsalmody.setContent(arrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void TestAsynch() {
        this.loadingDialog = ApplicationDialogs.showLoadingDialog(this, getString(R.string.plz_wait));
        Observable.fromCallable(new Callable() { // from class: com.app.orsozoxi.-$$Lambda$MainActivity_absalmodyaKiahk$NxQJLGbgCOmusZmp_7FZZgjHSTY
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$TestAsynch$0$MainActivity_absalmodyaKiahk();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.app.orsozoxi.-$$Lambda$MainActivity_absalmodyaKiahk$xzTEnSROQPdZKFMUtaNkabnSOPE
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$TestAsynch$1$MainActivity_absalmodyaKiahk((Boolean) obj);
            }
        });
    }

    public /* synthetic */ Boolean lambda$TestAsynch$0$MainActivity_absalmodyaKiahk() throws Exception {
        readData();
        return false;
    }

    public /* synthetic */ void lambda$TestAsynch$1$MainActivity_absalmodyaKiahk(Boolean bool) throws Exception {
        ApplicationDialogs.dismissLoadingDialog(this.loadingDialog);
        if (!this.foundCoptic) {
            this.copticBox.setEnabled(false);
        } else {
            this.copticBox.setEnabled(true);
        }
        if (!this.foundArabicCoptic) {
            this.arabicCopticBox.setEnabled(false);
        } else {
            this.arabicCopticBox.setEnabled(true);
        }
        if (!this.foundArabicCoptic && !this.foundCoptic) {
            if (!this.arabicBox.isChecked()) {
                this.arabicBox.setChecked(true);
            }
            this.arabicBox.setEnabled(false);
        } else {
            this.arabicBox.setEnabled(true);
        }
        TextView textView = (TextView) findViewById(R.id.tv_arab_title);
        TextView textView2 = (TextView) findViewById(R.id.tv_cop_title);
        checksim checksimVar = new checksim();
        textView.setTextSize(Float.valueOf(checksimVar.checkfontsize(this)).floatValue());
        textView.setTextSize(Float.valueOf(checksimVar.checkfontsize(this)).floatValue());
        this.faceltr = Typeface.createFromAsset(getAssets(), "ABRAAM_0.TTF");
        textView.setText(this.selectedPsalmody.getArabicTitle());
        textView2.setTypeface(this.faceltr);
        textView.setTypeface(this.faceltr);
        if (this.selectedPsalmody.getCopticTitle() == null || this.selectedPsalmody.getCopticTitle().equals("")) {
            textView2.setVisibility(8);
        }
        textView2.setText(this.selectedPsalmody.getCopticTitle());
        PsalmodyAdapter psalmodyAdapter = new PsalmodyAdapter(this, this.selectedPsalmody.getContent(), this.selectedPsalmody.isArabic() && arabicAva, this.selectedPsalmody.isCoptic() && copAva, this.selectedPsalmody.isArabicCoptic() && araCopAva);
        this.psalomdyAdapter = psalmodyAdapter;
        this.listViewFootballLegend.setAdapter((ListAdapter) psalmodyAdapter);
        ((NestedScrollView2) findViewById(R.id.v_nested)).smoothScrollTo(0, 0);
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
                if (MainActivity_absalmodyaKiahk.this.spinner.getSelectedItemPosition() > 0 && MainActivity_absalmodyaKiahk.this.spinner.getCount() >= 1) {
                    MainActivity_absalmodyaKiahk.this.spinner.setSelection(MainActivity_absalmodyaKiahk.this.spinner.getSelectedItemPosition() - 1);
                }
                return true;
            }
            if (motionEvent2.getX() - motionEvent.getX() <= 120.0f || Math.abs(f) <= 200.0f) {
                return false;
            }
            Log.d("log", "right");
            if (MainActivity_absalmodyaKiahk.this.spinner.getSelectedItemPosition() < MainActivity_absalmodyaKiahk.this.spinner.getCount() - 1 && MainActivity_absalmodyaKiahk.this.spinner.getCount() > 1) {
                MainActivity_absalmodyaKiahk.this.spinner.setSelection(MainActivity_absalmodyaKiahk.this.spinner.getSelectedItemPosition() + 1);
            }
            return true;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        arabicAva = true;
        copAva = true;
        araCopAva = true;
        super.onPause();
    }
}
